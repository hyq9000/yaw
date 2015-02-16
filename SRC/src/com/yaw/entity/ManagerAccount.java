package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the yaw_manager_account database table.
 * 
 */
@Entity
@Table(name = "yaw_manager_account")
public class ManagerAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mngLoginIp;
	private int mngLoginLength;
	private String mngLoginName;
	private Date mngLoginTime;
	private String mngName;
	private String mngPassword;
	private byte mngStatus;
	private byte mngOnline;
	private byte mngType;

	public ManagerAccount() {
	}

	@Column(name = "MNG_ONLINE")
	public byte getMngOnline() {
		return mngOnline;
	}

	public void setMngOnline(byte mngOnline) {
		this.mngOnline = mngOnline;
	}

	@Column(name = "MNG_LOGIN_IP")
	public String getMngLoginIp() {
		return this.mngLoginIp;
	}

	public void setMngLoginIp(String mngLoginIp) {
		this.mngLoginIp = mngLoginIp;
	}

	@Column(name = "MNG_LOGIN_LENGTH")
	public int getMngLoginLength() {
		return this.mngLoginLength;
	}

	public void setMngLoginLength(int mngLoginLength) {
		this.mngLoginLength = mngLoginLength;
	}

	@Id
	@Column(name = "MNG_LOGIN_NAME")
	public String getMngLoginName() {
		return this.mngLoginName;
	}

	public void setMngLoginName(String mngLoginName) {
		this.mngLoginName = mngLoginName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MNG_LOGIN_TIME")
	public Date getMngLoginTime() {
		return this.mngLoginTime;
	}

	public void setMngLoginTime(Date mngLoginTime) {
		this.mngLoginTime = mngLoginTime;
	}

	@Column(name = "MNG_NAME")
	public String getMngName() {
		return this.mngName;
	}

	public void setMngName(String mngName) {
		this.mngName = mngName;
	}

	@Column(name = "MNG_PASSWORD")
	public String getMngPassword() {
		return this.mngPassword;
	}

	public void setMngPassword(String mngPassword) {
		this.mngPassword = mngPassword;
	}

	@Column(name = "MNG_STATUS")
	public byte getMngStatus() {
		return this.mngStatus;
	}

	public void setMngStatus(byte mngStatus) {
		this.mngStatus = mngStatus;
	}

	@Column(name = "MNG_TYPE")
	public byte getMngType() {
		return this.mngType;
	}

	public void setMngType(byte mngType) {
		this.mngType = mngType;
	}

}