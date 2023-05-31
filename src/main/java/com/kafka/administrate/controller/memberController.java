package com.kafka.administrate.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	@Autowired
	private HttpServletRequest request;
	
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
		
		User user = new User(id,pwd,name,phoneNumber,"Default-Profile");
		
		boolean result = memberService.mergeUser(user);
		
		return result;
	}
	@GetMapping("/signIn")
	public boolean signIn(@RequestParam Map<String, Object> param, Model model,HttpSession session,
		HttpServletRequest request, HttpServletResponse response) throws InterruptedException, ExecutionException {
		String id = (String) param.get("id");
		String pwd = PasswordUtils.encryptPassword((String) param.get("pwd"));

		User user = new User(id,pwd);
		boolean result = false;
		user = memberService.signIn(user);
		if(user != null) {
		    // 세션값 설정
			session.setAttribute("userSeesionData", user);
			// 세션 유지시간 3분
		    session.setMaxInactiveInterval(30*60);
		    result = true;
		}
		
		return result;
	}
	@PostMapping("/mergeUser")
	public User updateUser(@RequestParam Map<String, Object> param,HttpServletRequest request, HttpSession session) throws Exception {

		String id = (String) param.get("id");
		String pwd = (String) param.get("pwd");
		String name = (String) param.get("name");
		String phoneNumber = (String) param.get("phoneNumber");
		
		User updateUser = new User(id,pwd,name,phoneNumber);
		//업데이트 결과
		boolean result = memberService.mergeUser(updateUser);
		
		updateUser = memberService.signIn(updateUser);
		// 세션 속성제거
		session.removeAttribute("userSeesionData");
		// 세션값 설정
		session.setAttribute("userSeesionData", updateUser);
		// 세션 유지시간 3분
	    session.setMaxInactiveInterval(30*60);
		
		return updateUser;
	}
	@PostMapping("/mergeUserFile")
	public User updateUser(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpSession session) throws Exception {

		//파일 생성 및 파일명 반환
		String fileName = profileUpload(file);
		//업데이트 유저 객체 생성
		User updateUser = new User(request.getParameter("userId"),request.getParameter("pwd"),request.getParameter("name"),request.getParameter("phoneNumber"),fileName);
		//업데이트 결과
		boolean result = memberService.mergeUser(updateUser);
		
		updateUser = memberService.signIn(updateUser);
		// 세션 속성제거
		session.removeAttribute("userSeesionData");
		// 세션값 설정
		session.setAttribute("userSeesionData", updateUser);
		// 세션 유지시간 3분
	    session.setMaxInactiveInterval(30*60);
		
		return updateUser;
	}
	
	public String profileUpload(MultipartFile file) throws Exception {
		
		User loginUser = (User) request.getSession().getAttribute("userSeesionData");
		String storageDirectory = "C:\\workSpace\\profile";
		String fileName = file.getOriginalFilename();
		
		// 파일확장자 인덱스
		int extensionIndex = fileName.lastIndexOf(".");
		String[] divFileName = fileName.split("[.]");
		String filePath = storageDirectory + File.separator;

		try {
		  if(extensionIndex >= 0) {
			  // 파일 저장 성공
			  String extension = fileName.substring(extensionIndex + 1);
			  fileName = divFileName[0] + "_" + loginUser.getId() +"."+extension;
			  file.transferTo(new File(filePath + fileName));
			  
		  } else {
			  // 파일 확장자명이 없을경우
			  throw new Exception("파일 확장자명이 부적합합니다.");
		  }

		} catch (IOException e) {
		  // 파일 저장 실패
			throw new IOException("파일 생성 실패");
		}
		
		return fileName;
	}
}
