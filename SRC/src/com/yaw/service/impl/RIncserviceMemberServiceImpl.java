package com.yaw.service.impl;

import java.util.Calendar;
import java.util.Date;

import com.common.dbutil.DaoHibernateImpl;
import com.yaw.entity.RIncserviceMember;
import com.yaw.service.IncrementServiceService;
import com.yaw.service.RIncserviceMemberService;

/**
 * 类型描述: 增值服务与会员关系服务实现
 * </br>创建时期: 2014年12月18日
 * @author hyq
 */
public class RIncserviceMemberServiceImpl extends
		DaoHibernateImpl<RIncserviceMember> implements RIncserviceMemberService {
	private IncrementServiceService incrementServiceService;
	@Override
	public void bindIncToMember(String orderyNo,int serviceId, String memberId)	throws Exception {
		/*
		 * 关联用户与增值服务项关系
		 */
		RIncserviceMember rim=new RIncserviceMember();
		int days=incrementServiceService.getServiceDayLength(serviceId);
		rim.setRimIncserviceId(serviceId);
		rim.setRimLength(days);
		rim.setRimMid(memberId);
		rim.setRimStart(new Date());
		rim.setRimOrderNO(orderyNo);
		
		
		/*算出自今日起，加上服务时长后的服务结止时间*/
		Calendar today=Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, days);
		
		rim.setRimEnd(today.getTime());
		super.add(rim);	
	}
	
	public void setIncrementServiceService(
			IncrementServiceService incrementServiceService) {
		this.incrementServiceService = incrementServiceService;
	}
		
}
