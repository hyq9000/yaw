package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_member_account database table.
 * 
 */
@Entity
@Table(name="yaw_member_account")
public class MemberAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	private String maLoginName;
	private byte maAuthenticated;
	private int maYaCoin;
	private byte maCompletedPercent;
	private int maFocusCount;
	private int maGrade;
	private String maIpAddr;
	private String maLoginIp;
	private Date maLoginTime;
	private int maOnlineLong;
	private int maOrderWeight;
	private String maPassword;
	private int maPoints;
	private Date maRegistTime;
	private String maServiceId;
	private byte maSincerity;
	private byte maOnline;
	private byte maStatus;
	private String maTags;
	private byte maType;
	private String maEmail;
	private String maPopularizeId;
	private byte maMfStatus=1;
	private int maCompletedInfo;
	
	
	@Column(name="MA_POPULARIZE_ID")
	public String getMaPopularizeId() {
		return maPopularizeId;
	}

	public void setMaPopularizeId(String maPopularizeId) {
		this.maPopularizeId = maPopularizeId;
	}

	@Column(name="MA_EMAIL")
	public String getMaEmail() {
		return maEmail;
	}

	public void setMaEmail(String maEmail) {
		this.maEmail = maEmail;
	}

	
	@Column(name="MA_MF_STATUS")
	public byte getMaMfStatus() {
		return maMfStatus;
	}

	public void setMaMfStatus(byte maMfStatus) {
		this.maMfStatus = maMfStatus;
	}

	public MemberAccount() {
	}

	@Column(name="MA_ONLINE")
	public byte getMaOnline() {
		return maOnline;
	}

	public void setMaOnline(byte maOnline) {
		this.maOnline = maOnline;
	}

	@Column(name="MA_COMPLETED_INFO")
	public int getMaCompletedInfo() {
		return maCompletedInfo;
	}

	public void setMaCompletedInfo(int maCompletedInfo) {
		this.maCompletedInfo = maCompletedInfo;
	}

	@Id
	@Column(name="MA_LOGIN_NAME")
	public String getMaLoginName() {
		return this.maLoginName;
	}

	public void setMaLoginName(String maLoginName) {
		this.maLoginName = maLoginName;
	}

	@Column(name="MA_AUTHENTICATED")
	public byte getMaAuthenticated() {
		return this.maAuthenticated;
	}

	public void setMaAuthenticated(byte maAuthenticated) {
		this.maAuthenticated = maAuthenticated;
	}

	@Column(name="MA_YA_COIN")
	public int getMaYaCoin() {
		return this.maYaCoin;
	}

	public void setMaYaCoin(int maYaCoin) {
		this.maYaCoin = maYaCoin;
	}


	@Column(name="MA_COMPLETED_PERCENT")
	public byte getMaCompletedPercent() {
		return this.maCompletedPercent;
	}

	public void setMaCompletedPercent(byte maCompletedPercent) {
		this.maCompletedPercent = maCompletedPercent;
	}


	@Column(name="MA_FOCUS_COUNT")
	public int getMaFocusCount() {
		return this.maFocusCount;
	}

	public void setMaFocusCount(int maFocusCount) {
		this.maFocusCount = maFocusCount;
	}


	@Column(name="MA_GRADE")
	public int getMaGrade() {
		return this.maGrade;
	}

	public void setMaGrade(int maGrade) {
		this.maGrade = maGrade;
	}


	@Column(name="MA_IP_ADDR")
	public String getMaIpAddr() {
		return this.maIpAddr;
	}

	public void setMaIpAddr(String maIpAddr) {
		this.maIpAddr = maIpAddr;
	}


	@Column(name="MA_LOGIN_IP")
	public String getMaLoginIp() {
		return this.maLoginIp;
	}

	public void setMaLoginIp(String maLoginIp) {
		this.maLoginIp = maLoginIp;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MA_LOGIN_TIME")
	public Date getMaLoginTime() {
		return this.maLoginTime;
	}

	public void setMaLoginTime(Date maLoginTime) {
		this.maLoginTime = maLoginTime;
	}


	@Column(name="MA_ONLINE_LONG")
	public int getMaOnlineLong() {
		return this.maOnlineLong;
	}

	public void setMaOnlineLong(int maOnlineLong) {
		this.maOnlineLong = maOnlineLong;
	}


	@Column(name="MA_ORDER_WEIGHT")
	public int getMaOrderWeight() {
		return this.maOrderWeight;
	}

	public void setMaOrderWeight(int maOrderWeight) {
		this.maOrderWeight = maOrderWeight;
	}


	@Column(name="MA_PASSWORD")
	public String getMaPassword() {
		return this.maPassword;
	}

	public void setMaPassword(String maPassword) {
		this.maPassword = maPassword;
	}


	@Column(name="MA_POINTS")
	public int getMaPoints() {
		return this.maPoints;
	}

	public void setMaPoints(int maPoints) {
		this.maPoints = maPoints;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MA_REGIST_TIME")
	public Date getMaRegistTime() {
		return this.maRegistTime;
	}

	public void setMaRegistTime(Date maRegistTime) {
		this.maRegistTime = maRegistTime;
	}


	@Column(name="MA_SERVICE_ID")
	public String getMaServiceId() {
		return this.maServiceId;
	}

	public void setMaServiceId(String maServiceId) {
		this.maServiceId = maServiceId;
	}


	@Column(name="MA_SINCERITY")
	public byte getMaSincerity() {
		return this.maSincerity;
	}

	public void setMaSincerity(byte maSincerity) {
		this.maSincerity = maSincerity;
	}


	@Column(name="MA_STATUS")
	public byte getMaStatus() {
		return this.maStatus;
	}

	public void setMaStatus(byte maStatus) {
		this.maStatus = maStatus;
	}


	@Column(name="MA_TAGS")
	public String getMaTags() {
		return this.maTags;
	}

	public void setMaTags(String maTags) {
		this.maTags = maTags;
	}


	@Column(name="MA_TYPE")
	public byte getMaType() {
		return this.maType;
	}

	public void setMaType(byte maType) {
		this.maType = maType;
	}

}