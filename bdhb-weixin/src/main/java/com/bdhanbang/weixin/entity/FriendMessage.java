package com.bdhanbang.weixin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class FriendMessage implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	private String fromUserId;
	private String toUserId;
	private String content;
	private LocalDateTime sendTime;
    private Short         isSend;
    private Short         isDel;
    private Short         isBack;
    
    
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getSendTime() {
		return sendTime;
	}
	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}
	public Short getIsSend() {
		return isSend;
	}
	public void setIsSend(Short isSend) {
		this.isSend = isSend;
	}
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	public Short getIsBack() {
		return isBack;
	}
	public void setIsBack(Short isBack) {
		this.isBack = isBack;
	}

}
