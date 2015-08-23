package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the yaw_photo database table.
 * 
 */
@Entity
@Table(name="yaw_photo")
public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int photoId;
	private int photoFocusCount;
	private String photoFormat;
	private String photoMid;
	private byte photoStatus;
	private String photoTitle;
	private int photoType;
	private String photoUrl;

	public Photo() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PHOTO_ID")
	public int getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}


	@Column(name="PHOTO_FOCUS_COUNT")
	public int getPhotoFocusCount() {
		return this.photoFocusCount;
	}

	public void setPhotoFocusCount(int photoFocusCount) {
		this.photoFocusCount = photoFocusCount;
	}


	@Column(name="PHOTO_FORMAT")
	public String getPhotoFormat() {
		return this.photoFormat;
	}

	public void setPhotoFormat(String photoFormat) {
		this.photoFormat = photoFormat;
	}


	@Column(name="PHOTO_MID")
	public String getPhotoMid() {
		return this.photoMid;
	}

	public void setPhotoMid(String photoMid) {
		this.photoMid = photoMid;
	}


	@Column(name="PHOTO_STATUS")
	public byte getPhotoStatus() {
		return this.photoStatus;
	}

	public void setPhotoStatus(byte photoStatus) {
		this.photoStatus = photoStatus;
	}


	@Column(name="PHOTO_TITLE")
	public String getPhotoTitle() {
		return this.photoTitle;
	}

	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}


	@Column(name="PHOTO_TYPE")
	public int getPhotoType() {
		return this.photoType;
	}

	public void setPhotoType(int photoType) {
		this.photoType = photoType;
	}


	@Column(name="PHOTO_URL")
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

}