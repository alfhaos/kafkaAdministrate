package com.kafka.administrate.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;


@EnableKafka
@Configuration
public class KafkaAdminConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String BOOTSTRAP_SERVERS;

	@Bean
	public AdminClient adminClient(){
		Properties properties = new Properties();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		
		return AdminClient.create(properties);
	}
	
}
