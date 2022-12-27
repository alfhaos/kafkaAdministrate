package com.kafka.administrate.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
