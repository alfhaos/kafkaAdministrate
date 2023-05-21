package com.kafka.administrate.model.member;

import java.util.Date;

public class User {
	
	String id;
	String pwd;
	Date uDate;
	String name;
	String phoneNumber;
	String authority;
	String profileId;
	
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
	
	public Date getuDate() {
		return uDate;
	}
	public void setuDate(Date uDate) {
		this.uDate = uDate;
	}
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
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
	public User(String id, String pwd, String name, String phoneNumber, String profileId) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.profileId = profileId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", pwd=" + pwd + ", uDate=" + uDate + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", authority=" + authority + ", chkId=" + chkId + "]";
	}
	
}
