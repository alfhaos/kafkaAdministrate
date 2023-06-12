package com.kafka.administrate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kafka.administrate.model.member.User;

@Mapper
public interface MemberMapper {

	//아이디 중복확인
	public Boolean checkId(String id);

	//회원가입
	public Boolean signUpUser(User user);

	//로그인
	public User signIn(User user);

	//채팅 목록 확인
	public int countChatRoom(User loginUser);

	//채팅리스트
	public List<User> memberChatLst(User loginUser);
	
}
