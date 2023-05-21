package com.kafka.administrate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.administrate.mapper.MemberMapper;

import com.kafka.administrate.model.member.User;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public boolean checkId(String id) {
		// TODO Auto-generated method stub
		return memberMapper.checkId(id);
	}

	@Override
	public boolean signUpUser(User user) {
		// TODO Auto-generated method stub
		return memberMapper.signUpUser(user);
	}

	@Override
	public User signIn(User user) {
		// TODO Auto-generated method stub
		return memberMapper.signIn(user);
	}

	@Override
	public int countChatRoom(User loginUser) {
		// TODO Auto-generated method stub
		return memberMapper.countChatRoom(loginUser);
	}

}
