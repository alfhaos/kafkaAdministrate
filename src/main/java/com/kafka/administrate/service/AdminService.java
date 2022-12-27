package com.kafka.administrate.service;

import org.apache.kafka.clients.admin.ListTopicsResult;

import com.kafka.administrate.model.Topic;

public interface AdminService {

	void createTopic(Topic topic);

	ListTopicsResult topicList();

}
