package com.kafka.administrate.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kafka.administrate.service.AdminService;

@Controller
public class PageController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/")
	public String indexPage(Model model) throws InterruptedException, ExecutionException {
		
		ListTopicsResult result = adminService.topicList();
		model.addAttribute("topicList", result.names().get());
	
		return "index";
	}
}
