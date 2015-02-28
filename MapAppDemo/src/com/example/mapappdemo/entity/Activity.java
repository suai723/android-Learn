package com.example.mapappdemo.entity;

import java.io.Serializable;

public class Activity implements Serializable{
	private String actName;
	private String rowId;
	private String actId;
	private String addr;
	private String loc;
	private String startTime;
	private String endTime;
	private String personNum;
	private String desc;
	private Person persons;
	private String hostUserName;
	private String hostUserId;
	private String startDay;
	private String state;
	
	public Activity() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Activity(String actName, String rowId, String actId, String addr,
			String loc, String startTime, String endTime, String personNum,
			String desc, Person persons, String hostUserName,
			String hostUserId, String startDay, String state) {
		super();
		this.actName = actName;
		this.rowId = rowId;
		this.actId = actId;
		this.addr = addr;
		this.loc = loc;
		this.startTime = startTime;
		this.endTime = endTime;
		this.personNum = personNum;
		this.desc = desc;
		this.persons = persons;
		this.hostUserName = hostUserName;
		this.hostUserId = hostUserId;
		this.startDay = startDay;
		this.state = state;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getActName() {
		return actName;
	}


	public void setActName(String actName) {
		this.actName = actName;
	}


	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getHostUserName() {
		return hostUserName;
	}


	public void setHostUserName(String hostUserName) {
		this.hostUserName = hostUserName;
	}


	public String getHostUserId() {
		return hostUserId;
	}


	public void setHostUserId(String hostUserId) {
		this.hostUserId = hostUserId;
	}


	public Person getPersons() {
		return persons;
	}


	public void setPersons(Person persons) {
		this.persons = persons;
	}


	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPersonNum() {
		return personNum;
	}
	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
