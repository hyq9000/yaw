package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_tripplan database table.
 * 
 */
@Entity
@Table(name="yaw_tripplan")
public class Tripplan implements Serializable {
	private static final long serialVersionUID = 1L;
	private int tripplanId;
	private byte tripplanDay;
	private byte tripplanStatus;
	private String tripplanDepartAddr;
	private Date tripplanDepartTime;
	private String tripplanDestination;
	private String tripplanMid;
	private byte tripplanMoneyModel;
	private int tripplanOrderWeight;
	private String tripplanOther;
	private Date tripplanPublishTime;
	private String tripplanTitle;
	private String tripplanType;
	private byte tripplanWantAge;
	private byte tripplanWantPersons;
	private String tripplanWantSex;
	private int tripplanBefocusCount;

	public Tripplan() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TRIPPLAN_ID")
	public int getTripplanId() {
		return this.tripplanId;
	}

	@Column(name="TRIPPLAN_STATUS")	
	public byte getTripplanStatus() {
		return tripplanStatus;
	}


	public void setTripplanStatus(byte tripplanStatus) {
		this.tripplanStatus = tripplanStatus;
	}


	public void setTripplanId(int tripplanId) {
		this.tripplanId = tripplanId;
	}

	@Column(name="TRIPPLAN_BEFOCUS_COUNT")
	public int getTripplanFocusCount() {
		return tripplanBefocusCount;
	}


	public void setTripplanFocusCount(int tripplanBefocusCount) {
		this.tripplanBefocusCount = tripplanBefocusCount;
	}


	@Column(name="TRIPPLAN_DAY")
	public byte getTripplanDay() {
		return this.tripplanDay;
	}

	public void setTripplanDay(byte tripplanDay) {
		this.tripplanDay = tripplanDay;
	}


	@Column(name="TRIPPLAN_DEPART_ADDR")
	public String getTripplanDepartAddr() {
		return this.tripplanDepartAddr;
	}

	public void setTripplanDepartAddr(String tripplanDepartAddr) {
		this.tripplanDepartAddr = tripplanDepartAddr;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRIPPLAN_DEPART_TIME")
	public Date getTripplanDepartTime() {
		return this.tripplanDepartTime;
	}

	public void setTripplanDepartTime(Date tripplanDepartTime) {
		this.tripplanDepartTime = tripplanDepartTime;
	}


	@Column(name="TRIPPLAN_DESTINATION")
	public String getTripplanDestination() {
		return this.tripplanDestination;
	}

	public void setTripplanDestination(String tripplanDestination) {
		this.tripplanDestination = tripplanDestination;
	}


	@Column(name="TRIPPLAN_MID")
	public String getTripplanMid() {
		return this.tripplanMid;
	}

	public void setTripplanMid(String tripplanMid) {
		this.tripplanMid = tripplanMid;
	}


	@Column(name="TRIPPLAN_MONEY_MODEL")
	public byte getTripplanMoneyModel() {
		return this.tripplanMoneyModel;
	}

	public void setTripplanMoneyModel(byte tripplanMoneyModel) {
		this.tripplanMoneyModel = tripplanMoneyModel;
	}


	@Column(name="TRIPPLAN_ORDER_WEIGHT")
	public int getTripplanOrderWeight() {
		return this.tripplanOrderWeight;
	}

	public void setTripplanOrderWeight(int tripplanOrderWeight) {
		this.tripplanOrderWeight = tripplanOrderWeight;
	}


	@Column(name="TRIPPLAN_OTHER")
	public String getTripplanOther() {
		return this.tripplanOther;
	}

	public void setTripplanOther(String tripplanOther) {
		this.tripplanOther = tripplanOther;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRIPPLAN_PUBLISH_TIME")
	public Date getTripplanPublishTime() {
		return this.tripplanPublishTime;
	}

	public void setTripplanPublishTime(Date tripplanPublishTime) {
		this.tripplanPublishTime = tripplanPublishTime;
	}


	@Column(name="TRIPPLAN_TITLE")
	public String getTripplanTitle() {
		return this.tripplanTitle;
	}

	public void setTripplanTitle(String tripplanTitle) {
		this.tripplanTitle = tripplanTitle;
	}


	@Column(name="TRIPPLAN_TYPE")
	public String getTripplanType() {
		return this.tripplanType;
	}

	public void setTripplanType(String tripplanType) {
		this.tripplanType = tripplanType;
	}


	@Column(name="TRIPPLAN_WANT_AGE")
	public byte getTripplanWantAge() {
		return this.tripplanWantAge;
	}

	public void setTripplanWantAge(byte tripplanWantAge) {
		this.tripplanWantAge = tripplanWantAge;
	}


	@Column(name="TRIPPLAN_WANT_PERSONS")
	public byte getTripplanWantPersons() {
		return this.tripplanWantPersons;
	}

	public void setTripplanWantPersons(byte tripplanWantPersons) {
		this.tripplanWantPersons = tripplanWantPersons;
	}


	@Column(name="TRIPPLAN_WANT_SEX")
	public String getTripplanWantSex() {
		return this.tripplanWantSex;
	}

	public void setTripplanWantSex(String tripplanWantSex) {
		this.tripplanWantSex = tripplanWantSex;
	}

}