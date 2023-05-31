package com.kafka.administrate.service;

import com.kafka.administrate.model.member.User;

public interface MemberService {
	
	//유저 아이디 확인
	boolean checkId(String id);

	boolean mergeUser(User user);

	User signIn(User user);

	int countChatRoom(User loginUser);

}
