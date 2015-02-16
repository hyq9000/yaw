package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_popularize_record database table.
 * 
 */
@Entity
@Table(name="yaw_popularize_record")
public class PopularizeRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private int popularizeId;
	private Date popularizeEffectiveTime;
	private String popularizeIp;
	private byte popularizeIsRegisted;
	private String popularizeMid;
	private String popularizeShareto;
	private byte popularizeType;
	private String popularizeUrl;

	public PopularizeRecord() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="POPULARIZE_ID")
	public int getPopularizeId() {
		return this.popularizeId;
	}

	public void setPopularizeId(int popularizeId) {
		this.popularizeId = popularizeId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="POPULARIZE_EFFECTIVE_TIME")
	public Date getPopularizeEffectiveTime() {
		return this.popularizeEffectiveTime;
	}

	public void setPopularizeEffectiveTime(Date popularizeEffectiveTime) {
		this.popularizeEffectiveTime = popularizeEffectiveTime;
	}


	@Column(name="POPULARIZE_IP")
	public String getPopularizeIp() {
		return this.popularizeIp;
	}

	public void setPopularizeIp(String popularizeIp) {
		this.popularizeIp = popularizeIp;
	}


	@Column(name="POPULARIZE_IS_REGISTED")
	public byte getPopularizeIsRegisted() {
		return this.popularizeIsRegisted;
	}

	public void setPopularizeIsRegisted(byte popularizeIsRegisted) {
		this.popularizeIsRegisted = popularizeIsRegisted;
	}


	@Column(name="POPULARIZE_MID")
	public String getPopularizeMid() {
		return this.popularizeMid;
	}

	public void setPopularizeMid(String popularizeMid) {
		this.popularizeMid = popularizeMid;
	}


	@Column(name="POPULARIZE_SHARETO")
	public String getPopularizeShareto() {
		return this.popularizeShareto;
	}

	public void setPopularizeShareto(String popularizeShareto) {
		this.popularizeShareto = popularizeShareto;
	}


	@Column(name="POPULARIZE_TYPE")
	public byte getPopularizeType() {
		return this.popularizeType;
	}

	public void setPopularizeType(byte popularizeType) {
		this.popularizeType = popularizeType;
	}


	@Column(name="POPULARIZE_URL")
	public String getPopularizeUrl() {
		return this.popularizeUrl;
	}

	public void setPopularizeUrl(String popularizeUrl) {
		this.popularizeUrl = popularizeUrl;
	}

}