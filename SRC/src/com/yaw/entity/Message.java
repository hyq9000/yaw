package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_message database table.
 * 
 */
@Entity
@Table(name="yaw_message")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private int msgId;
	private String msgContent;
	private byte msgIsSystem;
	private String msgMid;
	private String msgReplayMid;
	private String msgReplyContent;
	private Date msgReplyTime;
	private byte msgStatus;
	private Date msgTime;

	public Message() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MSG_ID")
	public int getMsgId() {
		return this.msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	@Column(name="MSG_REPLAY_MID")
	public String getMsgReplayMid() {
		return msgReplayMid;
	}


	public void setMsgReplayMid(String msgReplayMid) {
		this.msgReplayMid = msgReplayMid;
	}


	@Column(name="MSG_CONTENT")
	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}


	@Column(name="MSG_IS_SYSTEM")
	public byte getMsgIsSystem() {
		return this.msgIsSystem;
	}

	public void setMsgIsSystem(byte msgIsSystem) {
		this.msgIsSystem = msgIsSystem;
	}


	@Column(name="MSG_MID")
	public String getMsgMid() {
		return this.msgMid;
	}

	public void setMsgMid(String msgMid) {
		this.msgMid = msgMid;
	}


	@Column(name="MSG_REPLY_CONTENT")
	public String getMsgReplyContent() {
		return this.msgReplyContent;
	}

	public void setMsgReplyContent(String msgReplyContent) {
		this.msgReplyContent = msgReplyContent;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MSG_REPLY_TIME")
	public Date getMsgReplyTime() {
		return this.msgReplyTime;
	}

	public void setMsgReplyTime(Date msgReplyTime) {
		this.msgReplyTime = msgReplyTime;
	}


	@Column(name="MSG_STATUS")
	public byte getMsgStatus() {
		return this.msgStatus;
	}

	public void setMsgStatus(byte msgStatus) {
		this.msgStatus = msgStatus;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MSG_TIME")
	public Date getMsgTime() {
		return this.msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}

}