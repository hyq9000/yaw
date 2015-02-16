package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the yaw_increment_service database table.
 * 
 */
@Entity
@Table(name="yaw_increment_service")
public class IncrementService implements Serializable {
	private static final long serialVersionUID = 1L;
	private int incserviceId;
	private String incserviceName;
	private int incservicePrice;
	private int incserviceSuitDay;

	public IncrementService() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="INCSERVICE_ID")
	public int getIncserviceId() {
		return this.incserviceId;
	}

	public void setIncserviceId(int incserviceId) {
		this.incserviceId = incserviceId;
	}


	@Column(name="INCSERVICE_NAME")
	public String getIncserviceName() {
		return this.incserviceName;
	}

	public void setIncserviceName(String incserviceName) {
		this.incserviceName = incserviceName;
	}


	@Column(name="INCSERVICE_PRICE")
	public int getIncservicePrice() {
		return this.incservicePrice;
	}

	public void setIncservicePrice(int incservicePrice) {
		this.incservicePrice = incservicePrice;
	}


	@Column(name="INCSERVICE_SUIT_DAY")
	public int getIncserviceSuitDay() {
		return this.incserviceSuitDay;
	}

	public void setIncserviceSuitDay(int incserviceSuitDay) {
		this.incserviceSuitDay = incserviceSuitDay;
	}

}