package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_tag_record database table.
 * 
 */
@Entity
@Table(name="yaw_tag_record")
public class TagRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private int tagId;
	private String tagContent;
	private Date tagDate;
	private String tagMid;
	private String tagTagedMid;

	public TagRecord() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TAG_ID")
	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}


	@Column(name="TAG_CONTENT")
	public String getTagContent() {
		return this.tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TAG_DATE")
	public Date getTagDate() {
		return this.tagDate;
	}

	public void setTagDate(Date tagDate) {
		this.tagDate = tagDate;
	}


	@Column(name="TAG_MID")
	public String getTagMid() {
		return this.tagMid;
	}

	public void setTagMid(String tagMid) {
		this.tagMid = tagMid;
	}


	@Column(name="TAG_TAGED_MID")
	public String getTagTagedMid() {
		return this.tagTagedMid;
	}

	public void setTagTagedMid(String tagTagedMid) {
		this.tagTagedMid = tagTagedMid;
	}

}