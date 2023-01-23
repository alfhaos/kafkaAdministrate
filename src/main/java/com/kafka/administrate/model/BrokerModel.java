package com.kafka.administrate.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class BrokerModel {

	private static final String brokerId = "broker.id";
	private static final String zkpCnn = "zookeeper.connect";
	private static final String zkpSessionTm = "zookeeper.session.timeout.ms";
	private static final String reqTm = "request.timeout.ms";
	private static final String repSkTm = "replica.socket.timeout.ms";
	private static final String minInsRep = "min.insync.replicas";
	private static final String gpMaxSize = "group.max.size";
	
	@Bean
	public List<String> brokerConfig(){
		List<String> configNameList = new ArrayList<String>();
		configNameList.add(brokerId);
		configNameList.add(zkpCnn);
		configNameList.add(zkpSessionTm);
		configNameList.add(reqTm);
		configNameList.add(repSkTm);
		configNameList.add(minInsRep);
		configNameList.add(gpMaxSize);
		
		return configNameList;
	}
	
}
