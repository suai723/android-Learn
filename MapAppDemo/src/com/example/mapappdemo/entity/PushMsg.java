package com.example.mapappdemo.entity;

import java.io.Serializable;

public class PushMsg implements Serializable{
	private String type;
	private String title;
	private String content;
	private String level;
	
	public PushMsg(String type, String title, String content, String level) {
		super();
		this.type = type;
		this.title = title;
		this.content = content;
		this.level = level;
	}

	public PushMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	
}
