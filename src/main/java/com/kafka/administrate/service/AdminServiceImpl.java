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
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ElectLeadersResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.ElectionType;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.ConfigResource;
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
	
	private Consumer<String, String> c;
	
	@Override
	public void createTopic(Topic topic) {
		ArrayList<NewTopic> list = new ArrayList<NewTopic>();
		list.add(new NewTopic(topic.getTopicName(), topic.getPartition(), (short) topic.getReplicationFactor()));
		
		adminClient.createTopics(list);
	}

	@Override
	public ListTopicsResult topicList() {
		
		return adminClient.listTopics();
		
	}

	@Override
	public void deleteTopic(Topic topic) {
	
		adminClient.deleteTopics(Collections.singleton(topic.getTopicName()));
		
	}
	@Override
	public TopicDescription topicDetail(Topic topic) throws InterruptedException, ExecutionException {
		
		DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton(topic.getTopicName()));
		Map<String, KafkaFuture<TopicDescription>> map = result.topicNameValues();
		KafkaFuture<TopicDescription> kafkaFuture = map.get(topic.getTopicName());
		
		return kafkaFuture.get();

	}

	@Override
	public void modifyPartition(Topic topic) {
		Map<String, NewPartitions> newPartitionSet = new HashMap<>();
		
		newPartitionSet.put(topic.getTopicName(), NewPartitions.increaseTo(topic.getPartition()));
		
		adminClient.createPartitions(newPartitionSet);
		
	}

	@Override
	public void changeLeader(Topic topic) {
		topic.setTopicName("asd");
		TopicPartition tp = new TopicPartition(topic.getTopicName(), 0);
		Set<TopicPartition> partitions = new HashSet<TopicPartition>();
		partitions.add(tp);

		ElectLeadersResult electLeaders = adminClient.electLeaders(ElectionType.UNCLEAN, partitions);
	}

	@Override
	public Map<String, String> leaderBroker(String server) {
		
		String bootStrapServer;
		Map<String, String> brokerConfigResult = new HashMap<>();
		Properties configs = new Properties();
		
		if(server.equals("1")) {
			bootStrapServer = Servers.Server1;
		} else if(server.equals("2")) {
			bootStrapServer = Servers.Server2;
		} else {
			bootStrapServer = Servers.Server3;
		}
		
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		
		AdminClient admin = AdminClient.create(configs);
		
		try {	

			for(Node node :  admin.describeCluster().nodes().get()) {
				ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
				
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
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			admin.close();			
		}
		return brokerConfigResult;
	}

}
