package com.yaw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the yaw_escort_info database table.
 * 
 */
@Entity
@Table(name="yaw_escort_info")
public class EscortInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String escortAttractive;
	private Date escortBirthday;
	private byte escortBody;
	private byte escortClubMember;
	private int escortDriveYear;
	private byte escortExp;
	private String escortFacePic;
	private byte escortFeel;
	private int escortHeight;
	private byte escortImage;
	private byte escortJob;
	private String escortLanguage;
	private String escortLiveAddr;
	private String escortLove;
	private String escortMid;
	private String escortName;
	private String escortNickName;
	private String escortPhone;
	private int escortPrice;
	private String escortQq;
	private String escortRecommend;
	private String escortSex;
	private String escortTripAddr;
	private String escortTryto;
	private byte escortType;
	private String escortWechat;
	private byte escortWeight;
	private int escortOrderWeight;

	public EscortInfo() {
	}
	
	@Column(name="ESCORT_ORDER_WEIGHT")
	public int getEscortOrderWeight() {
		return escortOrderWeight;
	}


	public void setEscortOrderWeight(int escortOrderWeight) {
		this.escortOrderWeight = escortOrderWeight;
	}




	@Column(name="ESCORT_NICK_NAME")
	public String getEscortNickName() {
		return escortNickName;
	}


	public void setEscortNickName(String escortNickName) {
		this.escortNickName = escortNickName;
	}


	@Column(name="ESCORT_ATTRACTIVE")
	public String getEscortAttractive() {
		return this.escortAttractive;
	}

	public void setEscortAttractive(String escortAttractive) {
		this.escortAttractive = escortAttractive;
	}


	@Column(name="ESCORT_BODY")
	public byte getEscortBody() {
		return this.escortBody;
	}

	public void setEscortBody(byte escortBody) {
		this.escortBody = escortBody;
	}


	@Column(name="ESCORT_CLUB_MEMBER")
	public byte getEscortClubMember() {
		return this.escortClubMember;
	}

	public void setEscortClubMember(byte escortClubMember) {
		this.escortClubMember = escortClubMember;
	}


	@Column(name="ESCORT_DRIVE_YEAR")
	public int getEscortDriveYear() {
		return this.escortDriveYear;
	}

	public void setEscortDriveYear(int escortDriveYear) {
		this.escortDriveYear = escortDriveYear;
	}


	@Column(name="ESCORT_EXP")
	public byte getEscortExp() {
		return this.escortExp;
	}

	public void setEscortExp(byte escortExp) {
		this.escortExp = escortExp;
	}


	@Column(name="ESCORT_FACE_PIC")
	public String getEscortFacePic() {
		return this.escortFacePic;
	}

	public void setEscortFacePic(String escortFacePic) {
		this.escortFacePic = escortFacePic;
	}


	@Column(name="ESCORT_FEEL")
	public byte getEscortFeel() {
		return this.escortFeel;
	}

	public void setEscortFeel(byte escortFeel) {
		this.escortFeel = escortFeel;
	}


	@Column(name="ESCORT_HEIGHT")
	public int getEscortHeight() {
		return this.escortHeight;
	}

	public void setEscortHeight(int escortHeight) {
		this.escortHeight = escortHeight;
	}


	@Column(name="ESCORT_IMAGE")
	public byte getEscortImage() {
		return this.escortImage;
	}

	public void setEscortImage(byte escortImage) {
		this.escortImage = escortImage;
	}


	@Column(name="ESCORT_JOB")
	public byte getEscortJob() {
		return this.escortJob;
	}

	public void setEscortJob(byte escortJob) {
		this.escortJob = escortJob;
	}


	@Column(name="ESCORT_LANGUAGE")
	public String getEscortLanguage() {
		return this.escortLanguage;
	}

	public void setEscortLanguage(String escortLanguage) {
		this.escortLanguage = escortLanguage;
	}


	@Column(name="ESCORT_LIVE_ADDR")
	public String getEscortLiveAddr() {
		return this.escortLiveAddr;
	}

	public void setEscortLiveAddr(String escortLiveAddr) {
		this.escortLiveAddr = escortLiveAddr;
	}


	@Column(name="ESCORT_LOVE")
	public String getEscortLove() {
		return this.escortLove;
	}

	public void setEscortLove(String escortLove) {
		this.escortLove = escortLove;
	}

	@Id
	@Column(name="ESCORT_MID")
	public String getEscortMid() {
		return this.escortMid;
	}

	public void setEscortMid(String escortMid) {
		this.escortMid = escortMid;
	}


	@Column(name="ESCORT_NAME")
	public String getEscortName() {
		return this.escortName;
	}

	public void setEscortName(String escortName) {
		this.escortName = escortName;
	}


	@Column(name="ESCORT_PHONE")
	public String getEscortPhone() {
		return this.escortPhone;
	}

	public void setEscortPhone(String escortPhone) {
		this.escortPhone = escortPhone;
	}


	@Column(name="ESCORT_PRICE")
	public int getEscortPrice() {
		return this.escortPrice;
	}

	public void setEscortPrice(int escortPrice) {
		this.escortPrice = escortPrice;
	}


	@Column(name="ESCORT_QQ")
	public String getEscortQq() {
		return this.escortQq;
	}

	public void setEscortQq(String escortQq) {
		this.escortQq = escortQq;
	}


	@Column(name="ESCORT_RECOMMEND")
	public String getEscortRecommend() {
		return this.escortRecommend;
	}

	public void setEscortRecommend(String escortRecommend) {
		this.escortRecommend = escortRecommend;
	}


	@Column(name="ESCORT_SEX")
	public String getEscortSex() {
		return this.escortSex;
	}

	public void setEscortSex(String escortSex) {
		this.escortSex = escortSex;
	}


	@Column(name="ESCORT_TRIP_ADDR")
	public String getEscortTripAddr() {
		return this.escortTripAddr;
	}

	public void setEscortTripAddr(String escortTripAddr) {
		this.escortTripAddr = escortTripAddr;
	}


	@Column(name="ESCORT_TRYTO")
	public String getEscortTryto() {
		return this.escortTryto;
	}

	public void setEscortTryto(String escortTryto) {
		this.escortTryto = escortTryto;
	}


	@Column(name="ESCORT_TYPE")
	public byte getEscortType() {
		return this.escortType;
	}

	public void setEscortType(byte escortType) {
		this.escortType = escortType;
	}


	@Column(name="ESCORT_WECHAT")
	public String getEscortWechat() {
		return this.escortWechat;
	}

	public void setEscortWechat(String escortWechat) {
		this.escortWechat = escortWechat;
	}


	@Column(name="ESCORT_WEIGHT")
	public byte getEscortWeight() {
		return this.escortWeight;
	}

	public void setEscortWeight(byte escortWeight) {
		this.escortWeight = escortWeight;
	}
	
	@Column(name="ESCORT_BIRTHDAY")
	public Date getEscortBirthday() {
		return escortBirthday;
	}
	public void setEscortBirthday(Date escortBirthday) {
		this.escortBirthday = escortBirthday;
	}

}