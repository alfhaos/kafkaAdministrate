package com.kafka.administrate.controller;

import java.util.List;
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
		
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("userSeesionData");

	    model.addAttribute("user",loginUser);
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
		
		// 현재 세션 가져오기
		HttpSession session = request.getSession();

		// 세션 값 지우기
		session.invalidate();
		
		return "/page/member/login";
	}
	@GetMapping("/page/chat")
	public String chat(Model model) {
		
	    HttpSession session = request.getSession();
	    User loginUser = (User) session.getAttribute("userSeesionData");
		int result = memberService.countChatRoom(loginUser);
		List<User> chatUserLst = memberService.memberChatLst(loginUser);
		
		model.addAttribute("countChatRoom",result);
		model.addAttribute("chatUserLst", chatUserLst);
		
		return "/page/member/chat";
	}
	@GetMapping("/page/myPage")
	public String myPage(Model model) {
		
		User loginUser = (User) request.getSession().getAttribute("userSeesionData");
	    model.addAttribute("user",loginUser);
	    
		return "/page/member/myPage";
	}
	
}
