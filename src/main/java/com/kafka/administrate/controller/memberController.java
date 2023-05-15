package com.kafka.administrate.controller;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.administrate.model.member.User;
import com.kafka.administrate.service.MemberService;
import com.kafka.administrate.utils.PasswordUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class memberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/checkId")
	public boolean checkUserId(@RequestParam Map<String, Object> param, Model model) throws InterruptedException, ExecutionException {
		String id = (String) param.get("id");
		
		boolean result = memberService.checkId(id);
		
		return result;
	}
	
	@PostMapping("/signUp")
	public boolean signUpUser(@RequestParam Map<String, Object> param, Model model) throws InterruptedException, ExecutionException {
		String id = (String) param.get("id");
		String pwd = PasswordUtils.encryptPassword((String) param.get("pwd"));
		String name = (String) param.get("name");
		String phoneNumber = (String) param.get("phoneNumber");
		
		User user = new User(id,pwd,name,phoneNumber);
		
		boolean result = memberService.signUpUser(user);
		
		return result;
	}
	@GetMapping("/signIn")
	public boolean signIn(@RequestParam Map<String, Object> param, Model model,HttpSession session,
		HttpServletRequest request, HttpServletResponse response) throws InterruptedException, ExecutionException {
		String id = (String) param.get("id");
		String pwd = PasswordUtils.encryptPassword((String) param.get("pwd"));

		User user = new User(id,pwd);
		
		boolean result = memberService.signIn(user);
		
		if(result) {
		    // 세션값 설정
			session.setAttribute("userSeesionData", user);
			
			// 세션 유지시간 3분
		    session.setMaxInactiveInterval(30*60);
		}
		
		return result;
	}
}
