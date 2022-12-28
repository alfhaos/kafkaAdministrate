package com.kafka.administrate.service;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;

import com.kafka.administrate.model.Topic;

public interface AdminService {

	void createTopic(Topic topic);

	ListTopicsResult topicList();

	void deleteTopic(Topic topic);

	TopicDescription topicDetail(Topic topic) throws InterruptedException, ExecutionException;

	void modifyPartition(Topic topic);

}
