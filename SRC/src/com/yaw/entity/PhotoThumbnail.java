package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the yaw_photo_thumbnail database table.
 * @Deprecated 此类已经作废，用其他才替代方案 2015-6-28 17:35
 */
@Entity
@Table(name="yaw_photo_thumbnail")
public class PhotoThumbnail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int thumbId;
	private int photoId;
	private byte thumbSize;
	private String thumbUrl;

	public PhotoThumbnail() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="THUMB_ID")
	public int getThumbId() {
		return this.thumbId;
	}

	public void setThumbId(int thumbId) {
		this.thumbId = thumbId;
	}


	@Column(name="PHOTO_ID")
	public int getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}


	@Column(name="THUMB_SIZE")
	public byte getThumbSize() {
		return this.thumbSize;
	}

	public void setThumbSize(byte thumbSize) {
		this.thumbSize = thumbSize;
	}


	@Column(name="THUMB_URL")
	public String getThumbUrl() {
		return this.thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

}