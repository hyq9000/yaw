package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_report_suggest database table.
 * 
 */
@Entity
@Table(name="yaw_report_suggest")
public class ReportSuggest implements Serializable {
	private static final long serialVersionUID = 1L;
	private int rsId;
	private String rsBereportMid;
	private String rsContent;
	private Date rsDate;
	private byte rsHandled;
	private String rsReportMid;
	private byte rsType;

	public ReportSuggest() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RS_ID")
	public int getRsId() {
		return this.rsId;
	}

	public void setRsId(int rsId) {
		this.rsId = rsId;
	}


	@Column(name="RS_BEREPORT_MID")
	public String getRsBereportMid() {
		return this.rsBereportMid;
	}

	public void setRsBereportMid(String rsBereportMid) {
		this.rsBereportMid = rsBereportMid;
	}


	@Column(name="RS_CONTENT")
	public String getRsContent() {
		return this.rsContent;
	}

	public void setRsContent(String rsContent) {
		this.rsContent = rsContent;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RS_DATE")
	public Date getRsDate() {
		return this.rsDate;
	}

	public void setRsDate(Date rsDate) {
		this.rsDate = rsDate;
	}


	@Column(name="RS_HANDLED")
	public byte getRsHandled() {
		return this.rsHandled;
	}

	public void setRsHandled(byte rsHandled) {
		this.rsHandled = rsHandled;
	}


	@Column(name="RS_REPORT_MID")
	public String getRsReportMid() {
		return this.rsReportMid;
	}

	public void setRsReportMid(String rsReportMid) {
		this.rsReportMid = rsReportMid;
	}


	@Column(name="RS_TYPE")
	public byte getRsType() {
		return this.rsType;
	}

	public void setRsType(byte rsType) {
		this.rsType = rsType;
	}

}