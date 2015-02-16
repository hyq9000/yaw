package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_tourist_info database table.
 * 
 */
@Entity
@Table(name="yaw_tourist_info")
public class TouristInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date touristBirthday;
	private String touristImage;
	private byte touristLikeBody;
	private byte touristLikeFeel;
	private int touristLikeHeight;
	private byte touristLikeImage;
	private byte touristLikeWeight;
	private String touristLiveAddr;
	private String touristLove;
	private String touristMessage;
	private String touristMid;
	private int touristMostPrice;
	private String touristName;
	private String touristNickname;
	private String touristPhone;
	private String touristQq;
	private String touristSex;
	private String touristTryto;
	private String touristWechat;
	private int touristOrderWeight;

	public TouristInfo() {
	}

	@Column(name="TOURIST_ORDER_WEIGHT")
	public int getTouristOrderWeight() {
		return touristOrderWeight;
	}


	public void setTouristOrderWeight(int touristOrderWeight) {
		this.touristOrderWeight = touristOrderWeight;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="TOURIST_BIRTHDAY")
	public Date getTouristBirthday() {
		return this.touristBirthday;
	}

	public void setTouristBirthday(Date touristBirthday) {
		this.touristBirthday = touristBirthday;
	}


	@Column(name="TOURIST_IMAGE")
	public String getTouristImage() {
		return this.touristImage;
	}

	public void setTouristImage(String touristImage) {
		this.touristImage = touristImage;
	}


	@Column(name="TOURIST_LIKE_BODY")
	public byte getTouristLikeBody() {
		return this.touristLikeBody;
	}

	public void setTouristLikeBody(byte touristLikeBody) {
		this.touristLikeBody = touristLikeBody;
	}


	@Column(name="TOURIST_LIKE_FEEL")
	public byte getTouristLikeFeel() {
		return this.touristLikeFeel;
	}

	public void setTouristLikeFeel(byte touristLikeFeel) {
		this.touristLikeFeel = touristLikeFeel;
	}


	@Column(name="TOURIST_LIKE_HEIGHT")
	public int getTouristLikeHeight() {
		return this.touristLikeHeight;
	}

	public void setTouristLikeHeight(int touristLikeHeight) {
		this.touristLikeHeight = touristLikeHeight;
	}


	@Column(name="TOURIST_LIKE_IMAGE")
	public byte getTouristLikeImage() {
		return this.touristLikeImage;
	}

	public void setTouristLikeImage(byte touristLikeImage) {
		this.touristLikeImage = touristLikeImage;
	}


	@Column(name="TOURIST_LIKE_WEIGHT")
	public byte getTouristLikeWeight() {
		return this.touristLikeWeight;
	}

	public void setTouristLikeWeight(byte touristLikeWeight) {
		this.touristLikeWeight = touristLikeWeight;
	}


	@Column(name="TOURIST_LIVE_ADDR")
	public String getTouristLiveAddr() {
		return this.touristLiveAddr;
	}

	public void setTouristLiveAddr(String touristLiveAddr) {
		this.touristLiveAddr = touristLiveAddr;
	}


	@Column(name="TOURIST_LOVE")
	public String getTouristLove() {
		return this.touristLove;
	}

	public void setTouristLove(String touristLove) {
		this.touristLove = touristLove;
	}


	@Column(name="TOURIST_MESSAGE")
	public String getTouristMessage() {
		return this.touristMessage;
	}

	public void setTouristMessage(String touristMessage) {
		this.touristMessage = touristMessage;
	}

	@Id
	@Column(name="TOURIST_MID")
	public String getTouristMid() {
		return this.touristMid;
	}

	public void setTouristMid(String touristMid) {
		this.touristMid = touristMid;
	}


	@Column(name="TOURIST_MOST_PRICE")
	public int getTouristMostPrice() {
		return this.touristMostPrice;
	}

	public void setTouristMostPrice(int touristMostPrice) {
		this.touristMostPrice = touristMostPrice;
	}


	@Column(name="TOURIST_NAME")
	public String getTouristName() {
		return this.touristName;
	}

	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}


	@Column(name="TOURIST_NICKNAME")
	public String getTouristNickname() {
		return this.touristNickname;
	}

	public void setTouristNickname(String touristNickname) {
		this.touristNickname = touristNickname;
	}


	@Column(name="TOURIST_PHONE")
	public String getTouristPhone() {
		return this.touristPhone;
	}

	public void setTouristPhone(String touristPhone) {
		this.touristPhone = touristPhone;
	}


	@Column(name="TOURIST_QQ")
	public String getTouristQq() {
		return this.touristQq;
	}

	public void setTouristQq(String touristQq) {
		this.touristQq = touristQq;
	}


	@Column(name="TOURIST_SEX")
	public String getTouristSex() {
		return this.touristSex;
	}

	public void setTouristSex(String touristSex) {
		this.touristSex = touristSex;
	}


	@Column(name="TOURIST_TRYTO")
	public String getTouristTryto() {
		return this.touristTryto;
	}

	public void setTouristTryto(String touristTryto) {
		this.touristTryto = touristTryto;
	}


	@Column(name="TOURIST_WECHAT")
	public String getTouristWechat() {
		return this.touristWechat;
	}

	public void setTouristWechat(String touristWechat) {
		this.touristWechat = touristWechat;
	}

}