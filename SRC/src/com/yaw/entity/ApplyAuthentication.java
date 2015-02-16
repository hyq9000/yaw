package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_apply_authentication database table.
 * 
 */
@Entity
@Table(name="yaw_apply_authentication")
public class ApplyAuthentication implements Serializable {
	private static final long serialVersionUID = 1L;
	private int authId;
	private Date authAuditTime;
	private Date authDate;
	private String authMngId;
	private String authReson;
	private byte authResult;
	private byte authType;
	private String authMid;

	public ApplyAuthentication() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AUTH_ID")
	public int getAuthId() {
		return this.authId;
	}

	public void setAuthId(int authId) {
		this.authId = authId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="AUTH_AUDIT_TIME")
	public Date getAuthAuditTime() {
		return this.authAuditTime;
	}

	public void setAuthAuditTime(Date authAuditTime) {
		this.authAuditTime = authAuditTime;
	}

	@Column(name="AUTH_MID")
	public String getAuthMid() {
		return authMid;
	}


	public void setAuthMid(String authMid) {
		this.authMid = authMid;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="AUTH_DATE")
	public Date getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}


	@Column(name="AUTH_MNG_ID")
	public String getAuthMngId() {
		return this.authMngId;
	}

	public void setAuthMngId(String authMngId) {
		this.authMngId = authMngId;
	}


	@Column(name="AUTH_RESON")
	public String getAuthReson() {
		return this.authReson;
	}

	public void setAuthReson(String authReson) {
		this.authReson = authReson;
	}


	@Column(name="AUTH_RESULT")
	public byte getAuthResult() {
		return this.authResult;
	}

	public void setAuthResult(byte authResult) {
		this.authResult = authResult;
	}


	@Column(name="AUTH_TYPE")
	public byte getAuthType() {
		return this.authType;
	}

	public void setAuthType(byte authType) {
		this.authType = authType;
	}

}