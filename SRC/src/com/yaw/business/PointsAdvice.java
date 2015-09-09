package com.yaw.business;


import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts2.ServletActionContext;

import com.common.web.WebContextUtil;
import com.yaw.common.BusinessServiceImpl;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.service.EscortInfoService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.TouristInfoService;

/**
 * 类型描述:积分逻辑通知,根据具体织入业务方法中去;
 * </br>创建时期: 2014年12月24日
 * @author hyq
 */
public class PointsAdvice implements MethodInterceptor {
	MemberAccountService memberAccountService;
	
	//TODO:这个算积分的通知每次都会执行两次；
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {		
		System.out.println("11111111111111111111111111");
		Object rs=invocation.proceed();
		Points an=invocation.getMethod().getAnnotation(Points.class);

		int points=generatePoints(an.action());
		MemberAccount user=null;
		/*计积分*/
		if(an.index()!=-1)			
			user=memberAccountService.getById((String)invocation.getArguments()[an.index()]);
		else{
			HttpServletRequest request=ServletActionContext.getRequest();
			user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(request.getSession());
		}	
		if(user!=null){
			//增加积分数
			user.setMaPoints(user.getMaPoints()+points);	
			memberAccountService.update(user);		
			
			//更新用户及详情表中的“排序权重值"
			memberAccountService.updateOrderWeigth(user);
		}
		return rs;		
	}
	
	/**
	 * 根据用户当前的操作，生成对应的积分数 
	 * @return 应积分数
	 */
	private int generatePoints(PointsActionType actionType) {	
		if(actionType==PointsActionType.POINTS_LOGIN)
			return 2;
		if(actionType==PointsActionType.POINTS_PHOTO)
			return 5;
		if(actionType==PointsActionType.POINTS_REPLAY)
			return 2;
		if(actionType==PointsActionType.POINTS_REPORT)
			return 10;
		if(actionType==PointsActionType.POINTS_SUGGEST)
			return 5;
		if(actionType==PointsActionType.POINTS_SUGGEST_REJECTED)
			return 50;
		if(actionType==PointsActionType.POINTS_BEFOCUS)
			return 1;
		if(actionType==PointsActionType.POINTS_BEFOCUS_PHOTO)
			return 1;
		if(actionType==PointsActionType.POINTS_MESSAGE)
			return 2;
		if(actionType==PointsActionType.POINTS_PUBLISH_TRIPPLAN)
			return 5;
		if(actionType==PointsActionType.POINTS_TAG)
			return 2;
		if(actionType==PointsActionType.POINTS_RECOMMEND)
			return 2;
		return 0;
	}
	
	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}
}
