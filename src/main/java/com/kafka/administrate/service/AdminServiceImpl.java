package com.kafka.administrate.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.AlterConfigsResult;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.ElectionType;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.administrate.model.BrokerModel;
import com.kafka.administrate.model.Servers;
import com.kafka.administrate.model.Topic;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminClient adminClient;
	
	@Autowired
	private BrokerModel bm;
	
	@Override
	public void createTopic(Topic topic) {
		ArrayList<NewTopic> list = new ArrayList<NewTopic>();
		//List에 토픽추가
		list.add(new NewTopic(topic.getTopicName(), topic.getPartition(), (short) topic.getReplicationFactor()));
		
		//토픽생성
		adminClient.createTopics(list);
	}

	@Override
	public ListTopicsResult topicList() {
		
		//토픽목록 반환
		return adminClient.listTopics();
		
	}

	@Override
	public void deleteTopic(Topic topic) {
		
		//토픽삭제
		adminClient.deleteTopics(Collections.singleton(topic.getTopicName()));
		
	}
	@Override
	public TopicDescription topicDetail(Topic topic) throws InterruptedException, ExecutionException {
		
		//토픽명으로 토픽조회
		DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton(topic.getTopicName()));
		Map<String, KafkaFuture<TopicDescription>> map = result.topicNameValues();
		KafkaFuture<TopicDescription> kafkaFuture = map.get(topic.getTopicName());
		
		/*
		//토픽정보 반환
		TopicDescription topicDescription = kafkaFuture.get();
		List<TopicPartitionInfo> PartitionInfoLst =topicDescription.partitions();
		
		// rep,isr 포맷
		String strTemp = "";
		
		for(int i = 0 ; i < PartitionInfoLst.size() ; i++) {
			TopicPartitionInfo partitionInfo = PartitionInfoLst.get(i);
			List<Node> nodeLst = partitionInfo.replicas();
			
			for (Node node : nodeLst) {
				String nodeId = node.idString();
				if(!(nodeId.isEmpty())) {
					strTemp += nodeId + ",";
				} else {
					strTemp = strTemp.substring(0, strTemp.length() -1);
				}

			}
			
		}
		*/
		
		//토픽정보 반환
		return kafkaFuture.get();

	}

	@Override
	public void modifyPartition(Topic topic) {
		//콜렉션생성
		Map<String, NewPartitions> newPartitionSet = new HashMap<>();
		
		//파티션변경
		newPartitionSet.put(topic.getTopicName(), NewPartitions.increaseTo(topic.getPartition()));
		
		adminClient.createPartitions(newPartitionSet);
		
	}

	@Override
	public void changeLeader(Topic topic) throws InterruptedException, ExecutionException {
		
		DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton(topic.getTopicName()));
		Map<String, KafkaFuture<TopicDescription>> map = result.topicNameValues();
		KafkaFuture<TopicDescription> kafkaFuture = map.get(topic.getTopicName());
		List<TopicPartitionInfo> partitions = kafkaFuture.get().partitions();
		Set<TopicPartition> setTopicPartitions = new HashSet<TopicPartition>();
		
		for (TopicPartitionInfo topicPartitionInfo : partitions) {
			TopicPartition tp = new TopicPartition(topic.getTopicName(), topicPartitionInfo.partition());
			setTopicPartitions.add(tp);
			adminClient.electLeaders(ElectionType.PREFERRED, setTopicPartitions);
			
		}
		
		TopicPartition tp = new TopicPartition(topic.getTopicName(), 3);		

	}

	@Override
	public Map<String, String> leaderBroker(String server) {
		
		//서버주소 선언
		String bootStrapServer;
		Map<String, String> brokerConfigResult = new HashMap<>();
		Properties configs = new Properties();
		String brokerId = "0";
		
		if("9091:9091".equals(server)) {
			bootStrapServer = Servers.Server1;
			brokerId = "1";
			
		} else if("9092:9092".equals(server)) {
			bootStrapServer = Servers.Server2;
			brokerId = "2";
		} else {
			bootStrapServer = Servers.Server3;
			brokerId = "3";
		}
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		
		//입력 받은 서버 주소로 adminClient 생성
		AdminClient admin = AdminClient.create(configs);
		
		try {	
			ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, brokerId);
			
			DescribeConfigsResult describeConfigs = admin.describeConfigs(Collections.singleton(cr));

			// 여기서 문자열 포함 하면 맵으로 넣어서 가져올건지 고민 해야됨...
			describeConfigs.all().get().forEach((broker, config) -> {
				Collection<ConfigEntry> result = config.entries();
				for (ConfigEntry configEntry : result) {
					if(bm.brokerConfig().contains(configEntry.name())) {
						brokerConfigResult.put(configEntry.name(), configEntry.value());
					}
				}
			});

			
		} catch (Exception e) {
			// TODO: handle exception
			
		} finally {
			admin.close();			
		}
		return brokerConfigResult;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void modifyReplicas(Topic topic) throws InterruptedException, ExecutionException {
		
		String topicName = topic.getTopicName();
		String replicationFactor = Integer.toString(topic.getReplicationFactor());

        
        // Topic Config 조회
		ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, topicName);
	    DescribeConfigsResult describeConfigsResult = adminClient.describeConfigs(Collections.singleton(configResource));
	    Config config = describeConfigsResult.all().get().get(configResource);
	    
        // Config 수정
        ConfigEntry configEntry = new ConfigEntry("min.insync.replicas", replicationFactor);
        Config newConfig = new Config(Collections.singleton(configEntry));
        Map<ConfigResource, Config> updateConfigs = Collections.singletonMap(configResource, newConfig);
        adminClient.alterConfigs(updateConfigs).all().get();

	}

}
