package com.kafka.administrate.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;

import com.kafka.administrate.model.Topic;

public interface AdminService {

	//토픽생성
	void createTopic(Topic topic);

	//토픽목록
	ListTopicsResult topicList();

	//토픽삭제
	void deleteTopic(Topic topic);

	//토픽조회
	TopicDescription topicDetail(Topic topic) throws InterruptedException, ExecutionException;

	//파티션병경
	void modifyPartition(Topic topic);

	//리더변경
	void changeLeader(Topic topic) throws InterruptedException, ExecutionException;

	//리더브로커
	Map<String, String> leaderBroker(String broker);

	//Replicas 변경
	void modifyReplicas(Topic topic) throws InterruptedException, ExecutionException;

}
