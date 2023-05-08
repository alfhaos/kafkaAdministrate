package com.kafka.administrate.model.member;

import lombok.Getter;
import lombok.Setter;


public class User {
	
	String id;
	String pwd;
	String name;
	String phoneNumber;
	boolean chkId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isChkId() {
		return chkId;
	}
	public void setChkId(boolean chkId) {
		this.chkId = chkId;
	}
	public User() {
		super();
	}
	public User(String id) {
		super();
		this.id = id;
	}
	public User(String id, String pwd) {
		super();
		this.id = id;
		this.pwd = pwd;
	}
	public User(String id, String pwd, boolean chkId) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.chkId = chkId;
	}
	public User(String id, String pwd, String name, String phoneNumber) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
}
