package com.kafka.administrate.service;

import java.util.ArrayList;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
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

}
