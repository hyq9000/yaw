package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_r_incservice_member database table.
 * 
 */
@Entity
@Table(name="yaw_r_incservice_member")
public class RIncserviceMember implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rimId;
	private Date rimEnd;
	private int rimIncserviceId;
	private int rimLength;
	private String rimMid;
	private Date rimStart;
	private String rimOrderNO;

	public RIncserviceMember() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RIM_ID")
	public int getRimId() {
		return this.rimId;
	}

	public void setRimId(int rimId) {
		this.rimId = rimId;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="RIM_END")
	public Date getRimEnd() {
		return this.rimEnd;
	}

	public void setRimEnd(Date rimEnd) {
		this.rimEnd = rimEnd;
	}


	@Column(name="RIM_INCSERVICE_ID")
	public int getRimIncserviceId() {
		return this.rimIncserviceId;
	}

	public void setRimIncserviceId(int rimIncserviceId) {
		this.rimIncserviceId = rimIncserviceId;
	}


	@Column(name="RIM_LENGTH")
	public int getRimLength() {
		return this.rimLength;
	}

	public void setRimLength(int rimLength) {
		this.rimLength = rimLength;
	}


	@Column(name="RIM_MID")
	public String getRimMid() {
		return this.rimMid;
	}

	public void setRimMid(String rimMid) {
		this.rimMid = rimMid;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="RIM_START")
	public Date getRimStart() {
		return this.rimStart;
	}

	public void setRimStart(Date rimStart) {
		this.rimStart = rimStart;
	}

	@Column(name="RIM_ORDER_NO")
	public String getRimOrderNO() {
		return rimOrderNO;
	}


	public void setRimOrderNO(String rimOrderNO) {
		this.rimOrderNO = rimOrderNO;
	}

	
}