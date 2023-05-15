package com.kafka.administrate.controller;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kafka.administrate.model.Topic;
import com.kafka.administrate.model.member.User;
import com.kafka.administrate.service.AdminService;
import com.kafka.administrate.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private HttpServletRequest request;

	@GetMapping("/")
	public String indexPage(Model model) throws InterruptedException, ExecutionException {
		
		ListTopicsResult result = adminService.topicList();
		model.addAttribute("topicList", result.names().get());
	
		return "/index";
	}
	
	@GetMapping("/page/topicDetail")
	public String detailPage(Topic topic, Model model) throws InterruptedException, ExecutionException {
		
		TopicDescription topicDescription = adminService.topicDetail(topic);

		model.addAttribute("partitions", topicDescription.partitions());
		model.addAttribute("topicName", topicDescription.name());
		return "page/topicDetail";
	}
	
	@GetMapping("/page/leaderBrokerDetail")
	public String leaderBrokerPage(String broker, Model model) {
		
		Map<String,String> brokerConfig = adminService.leaderBroker(broker);
		model.addAttribute("brokerConfig",brokerConfig);
		
		return "page/brokerDetail";
	}
	@GetMapping("/page/member/signUp")
	public String leaderBrokerPage() {
		
		return "page/member/signUp";
	}
	
	@GetMapping("/page/member/signIn")
	public String test(Model model)  throws InterruptedException, ExecutionException {
		
		return "/page/member/login";
	}
	@GetMapping("/page/chat")
	public String chat(Model model) {
		
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("userSeesionData");
		int result = memberService.countChatRoom(loginUser);
		
		model.addAttribute("countChatRoom",result);
		return "/page/member/chat";
	}
	
}
