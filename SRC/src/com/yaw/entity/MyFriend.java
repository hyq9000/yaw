package com.yaw.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the yaw_my_friend database table.
 * 
 */
@Entity
@Table(name="yaw_my_friend")
public class MyFriend implements Serializable {
	private static final long serialVersionUID = 1L;
	private int friendId;
	private String friendFriendId;
	private String friendMyId;

	public MyFriend() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FRIEND_ID")
	public int getFriendId() {
		return this.friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}


	@Column(name="FRIEND_FRIEND_ID")
	public String getFriendFriendId() {
		return this.friendFriendId;
	}

	public void setFriendFriendId(String friendFriendId) {
		this.friendFriendId = friendFriendId;
	}


	@Column(name="FRIEND_MY_ID")
	public String getFriendMyId() {
		return this.friendMyId;
	}

	public void setFriendMyId(String friendMyId) {
		this.friendMyId = friendMyId;
	}

}