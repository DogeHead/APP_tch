package com.example.app.model;

import com.google.gson.JsonElement;

public class CommonJsonResult {
	private String success;
	private String time;
	private String msg;
	private JsonElement content;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public JsonElement getContent() {
		return content;
	}
	public void setContent(JsonElement content) {
		this.content = content;
	}
	
	

}
