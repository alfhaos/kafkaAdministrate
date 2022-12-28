package com.kafka.administrate.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.administrate.model.Topic;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminClient adminClient;
	
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

}
