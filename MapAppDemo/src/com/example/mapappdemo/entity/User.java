package com.example.mapappdemo.entity;

import java.io.Serializable;

public class User implements Serializable{
	
	private String userId;
	private String userName;
	private String passWord;
	private String name;
	private String current;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public User(String userId, String userName, String passWord, String name,
			String current) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;
		this.name = name;
		this.current = current;
	}



	public String getCurrent() {
		return current;
	}



	public void setCurrent(String current) {
		this.current = current;
	}



	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
