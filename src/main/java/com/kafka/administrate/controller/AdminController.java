package com.kafka.administrate.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.administrate.model.Topic;
import com.kafka.administrate.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("createTopic")
	public void createTopic(Topic topic) {
		
		adminService.createTopic(topic);
		
	}
	
	@PostMapping("deleteTopic")
	public void deleteTopic(Topic topic) {
		
		adminService.deleteTopic(topic);
		
	}
	
	@PostMapping("modifyPartition")
	public void modifyPartition(Topic topic) {
		
		adminService.modifyPartition(topic);
		
	}
	
	@GetMapping("changeLeader")
	public void changeLeader(Topic topic) throws InterruptedException, ExecutionException {
		
		adminService.changeLeader(topic);
		
	}
	
	@GetMapping("leaderBroker")
	public void leaderBroker(String broker) {
		
		adminService.leaderBroker(broker);
	}
	@PostMapping("modifyReplicas")
	public void modifyReplicas(Topic topic) throws InterruptedException, ExecutionException {
		
		adminService.modifyReplicas(topic);
		
	}
}
