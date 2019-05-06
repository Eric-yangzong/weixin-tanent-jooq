package com.bdhanbang.weixin.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Chat {
	
	public static class SendMessage{

		private String sendId; //发送人id
		private String toId;//接受者id
		private String avatar;
		private String type;
		private String content;
		private Long timestamp;
		private Boolean mine;
		private String username;
		
		public String getSendId() {
			return sendId;
		}
		public void setSendId(String sendId) {
			this.sendId = sendId;
		}
		public String getToId() {
			return toId;
		}
		public void setToId(String toId) {
			this.toId = toId;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Long timestamp) {
			this.timestamp = timestamp;
		}
		
		public Boolean getMine() {
			return mine;
		}
		public void setMine(Boolean mine) {
			this.mine = mine;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
		
		@Override
		public String toString() {
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				return mapper.writeValueAsString(this);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
			
		}

	}
	
	public static class To{

		String id="";
		String type="";
		String avatar=""; 
		String username="";
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

	}

	public static class Mine{
		
		String id="";
		String type="";
		String avatar="";
		String content="";
		String username="";
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
	}
	
	private To to= new To();
	
	private Mine mine = new Mine();

	public To getTo() {
		return to;
	}

	public void setTo(To to) {
		this.to = to;
	}

	public Mine getMine() {
		return mine;
	}

	public void setMine(Mine mine) {
		this.mine = mine;
	}
	
	

}
