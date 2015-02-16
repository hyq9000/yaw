package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the yaw_club_apply database table.
 * 
 */
@Entity
@Table(name="yaw_club_apply")
public class ClubApply implements Serializable {
	private static final long serialVersionUID = 1L;
	private int clubapplyId;
	private Date clubapplyDate;
	private String clubapplyMid;

	public ClubApply() {
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CLUBAPPLY_ID")
	public int getClubapplyId() {
		return this.clubapplyId;
	}

	public void setClubapplyId(int clubapplyId) {
		this.clubapplyId = clubapplyId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CLUBAPPLY_DATE")
	public Date getClubapplyDate() {
		return this.clubapplyDate;
	}

	public void setClubapplyDate(Date clubapplyDate) {
		this.clubapplyDate = clubapplyDate;
	}


	@Column(name="CLUBAPPLY_MID")
	public String getClubapplyMid() {
		return this.clubapplyMid;
	}

	public void setClubapplyMid(String clubapplyMid) {
		this.clubapplyMid = clubapplyMid;
	}

}