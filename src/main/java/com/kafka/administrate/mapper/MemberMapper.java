package com.kafka.administrate.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kafka.administrate.model.member.User;

@Mapper
public interface MemberMapper {

	public Boolean checkId(String id);

	public Boolean signUpUser(User user);

	public boolean signIn(User user);
	
}
