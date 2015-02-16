package com.yaw.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the yaw_r_tripplan_escort database table.
 * 
 */
@Entity
@Table(name="yaw_r_tripplan_escort")
public class RTripplanEscort implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rteId;
	private byte rteAutoMatch;
	private String rteMid;
	private String rteRecommend;
	private int rteTripplanId;
	private Timestamp rteTime;

	public RTripplanEscort() {
	}

	@Column(name="RTE_TIME")
	public Timestamp getRteTime() {
		return rteTime;
	}


	public void setRteTime(Timestamp rteTime) {
		this.rteTime = rteTime;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RTE_ID")
	public int getRteId() {
		return this.rteId;
	}

	public void setRteId(int rteId) {
		this.rteId = rteId;
	}


	@Column(name="RTE_AUTO_MATCH")
	public byte getRteAutoMatch() {
		return this.rteAutoMatch;
	}

	public void setRteAutoMatch(byte rteAutoMatch) {
		this.rteAutoMatch = rteAutoMatch;
	}


	@Column(name="RTE_MID")
	public String getRteMid() {
		return this.rteMid;
	}

	public void setRteMid(String rteMid) {
		this.rteMid = rteMid;
	}


	@Column(name="RTE_RECOMMEND")
	public String getRteRecommend() {
		return this.rteRecommend;
	}

	public void setRteRecommend(String rteRecommend) {
		this.rteRecommend = rteRecommend;
	}


	@Column(name="RTE_TRIPPLAN_ID")
	public int getRteTripplanId() {
		return this.rteTripplanId;
	}

	public void setRteTripplanId(int rteTripplanId) {
		this.rteTripplanId = rteTripplanId;
	}

}