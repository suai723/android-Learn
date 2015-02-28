package com.example.mapappdemo.entity;

import java.io.Serializable;

public class Person implements Serializable{
	
	private String rowId;
	private String actId;
	private String personId;
	private String name;
	private String currentLoc;
	private String arriveState;
	private String arriveTime;
	private String leaveState;
	private String reasonDesc;
	private String host;
	private String userName;
	private String userId;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public Person(String rowId, String actId, String personId, String name,
			String currentLoc, String arriveState, String arriveTime,
			String leaveState, String reasonDesc, String host, String userName,
			String userId) {
		super();
		this.rowId = rowId;
		this.actId = actId;
		this.personId = personId;
		this.name = name;
		this.currentLoc = currentLoc;
		this.arriveState = arriveState;
		this.arriveTime = arriveTime;
		this.leaveState = leaveState;
		this.reasonDesc = reasonDesc;
		this.host = host;
		this.userName = userName;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentLoc() {
		return currentLoc;
	}

	public void setCurrentLoc(String currentLoc) {
		this.currentLoc = currentLoc;
	}

	public String getArriveState() {
		return arriveState;
	}

	public void setArriveState(String arriveState) {
		this.arriveState = arriveState;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getLeaveState() {
		return leaveState;
	}

	public void setLeaveState(String leaveState) {
		this.leaveState = leaveState;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
}
