package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_member_focus database table.
 * 
 */
@Entity
@Table(name="yaw_member_focus")
public class MemberFocus implements Serializable {
	private static final long serialVersionUID = 1L;
	private int focusId;
	private String focusBefocusMid;
	private int focusCount;
	private Date focusDate;
	private String focusMid;
	private byte focusType;

	public MemberFocus() {
	}

	@Column(name="FOCUS_TYPE")
	public byte getFocusType() {
		return focusType;
	}


	public void setFocusType(byte focusType) {
		this.focusType = focusType;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FOCUS_ID")
	public int getFocusId() {
		return this.focusId;
	}

	public void setFocusId(int focusId) {
		this.focusId = focusId;
	}


	@Column(name="FOCUS_BEFOCUS_MID")
	public String getFocusBefocusMid() {
		return this.focusBefocusMid;
	}

	public void setFocusBefocusMid(String focusBefocusMid) {
		this.focusBefocusMid = focusBefocusMid;
	}


	@Column(name="FOCUS_COUNT")
	public int getFocusCount() {
		return this.focusCount;
	}

	public void setFocusCount(int focusCount) {
		this.focusCount = focusCount;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FOCUS_DATE")
	public Date getFocusDate() {
		return this.focusDate;
	}

	public void setFocusDate(Date focusDate) {
		this.focusDate = focusDate;
	}


	@Column(name="FOCUS_MID")
	public String getFocusMid() {
		return this.focusMid;
	}

	public void setFocusMid(String focusMid) {
		this.focusMid = focusMid;
	}

}