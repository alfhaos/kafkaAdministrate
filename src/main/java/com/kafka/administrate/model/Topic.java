package com.kafka.administrate.model;

public class Topic {

	String topicName;
	int replicationFactor;
	int partition;
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public int getReplicationFactor() {
		return replicationFactor;
	}
	public void setReplicationFactor(int replicationFactor) {
		this.replicationFactor = replicationFactor;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	
}
