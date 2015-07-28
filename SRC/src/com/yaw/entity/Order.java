package com.yaw.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the yaw_order database table.
 * 
 */
@Entity
@Table(name="yaw_order")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private String orderNo;
	private int orderCount;
	private int orderIncserviceId;
	private String orderIncserviceName;
	private String orderMid;
	private byte orderPayMode;
	private String orderPayOrg;
	private byte orderStatus;
	private Date orderSubmitTime;
	private Date orderPayTime;
	private Date orderHandleTime;
	private int orderTotalMoney;
	private int orderPrice;
	private int orderMngId;

	public Order(){
	}	
	
	@Column(name="ORDER_MNG_ID")
	public int getOrderMngId() {
		return orderMngId;
	}


	public void setOrderMngId(int orderMngId) {
		this.orderMngId = orderMngId;
	}

	
	@Column(name="ORDER_PRICE")
	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Column(name="ORDER_HANDLE_TIME")
	public Date getOrderHandleTime() {
		return orderHandleTime;
	}



	public void setOrderHandleTime(Date orderHandleTime) {
		this.orderHandleTime = orderHandleTime;
	}



	@Column(name="ORDER_PAY_TIME")
	public Date getOrderPayTime() {
		return orderPayTime;
	}



	public void setOrderPayTime(Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}



	@Id
	@Column(name="ORDER_NO")
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	@Column(name="ORDER_COUNT")
	public int getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}


	@Column(name="ORDER_INCSERVICE_ID")
	public int getOrderIncserviceId() {
		return this.orderIncserviceId;
	}

	public void setOrderIncserviceId(int orderIncserviceId) {
		this.orderIncserviceId = orderIncserviceId;
	}


	@Column(name="ORDER_INCSERVICE_NAME")
	public String getOrderIncserviceName() {
		return this.orderIncserviceName;
	}

	public void setOrderIncserviceName(String orderIncserviceName) {
		this.orderIncserviceName = orderIncserviceName;
	}


	@Column(name="ORDER_MID")
	public String getOrderMid() {
		return this.orderMid;
	}

	public void setOrderMid(String orderMid) {
		this.orderMid = orderMid;
	}


	@Column(name="ORDER_PAY_MODE")
	public byte getOrderPayMode() {
		return this.orderPayMode;
	}

	public void setOrderPayMode(byte orderPayMode) {
		this.orderPayMode = orderPayMode;
	}


	@Column(name="ORDER_PAY_ORG")
	public String getOrderPayOrg() {
		return this.orderPayOrg;
	}

	public void setOrderPayOrg(String orderPayOrg) {
		this.orderPayOrg = orderPayOrg;
	}


	@Column(name="ORDER_STATUS")
	public byte getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(byte orderStatus) {
		this.orderStatus = orderStatus;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ORDER_SUBMIT_TIME")
	public Date getOrderSubmitTime() {
		return this.orderSubmitTime;
	}

	public void setOrderSubmitTime(Date orderSubmitTime) {
		this.orderSubmitTime = orderSubmitTime;
	}


	@Column(name="ORDER_TOTAL_MONEY")
	public int getOrderTotalMoney() {
		return this.orderTotalMoney;
	}

	public void setOrderTotalMoney(int orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
	}

}