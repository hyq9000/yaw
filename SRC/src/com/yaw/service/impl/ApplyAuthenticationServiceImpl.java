package com.yaw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.common.BusinessServiceImpl;
import com.yaw.entity.ApplyAuthentication;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.service.ApplyAuthenticationService;
import com.yaw.service.EscortInfoService;
import com.yaw.service.MemberAccountService;

/**
 * 类型描述:客服审核认证服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class ApplyAuthenticationServiceImpl extends
		DaoHibernateImpl<ApplyAuthentication> implements
		ApplyAuthenticationService {

	MemberAccountService memberAccountService;
	EscortInfoService escortInfoService;
	@Override
	public List<Map<String, Object>> getAuthenticationList(Paging paging)
			throws Exception {
		return null;
	}

	@Override
	public void handleAuthentication(String managerId, int applyAutheticationId,
			boolean isPass, String nopassReason) throws Exception {
		//改变当前申请记录相关属性
		ApplyAuthentication aa=this.getById(applyAutheticationId);
		aa.setAuthAuditTime(new Date());
		aa.setAuthMngId(managerId);
		aa.setAuthReson(nopassReason);
		aa.setAuthResult((byte)(isPass?1:0));
		this.update(aa);
		
		/*
		 * 如果是通过审核,则更新会员及基本资料相关状态;
		 */
		if(isPass){
			/*
			 * 改变被申请会员的相关认证状态;
			 */
			String memberId=aa.getAuthMid();
			byte type=aa.getAuthType();
			MemberAccount member=memberAccountService.getById(memberId);
			
			//更新认证码
			byte authenticationCode=BusinessServiceImpl.generateAuthenticationCode(member,aa.getAuthType());				
			member.setMaAuthenticated(authenticationCode);
			//手机认证后，增加诚意指数
			member.setMaSincerity(BusinessServiceImpl.getSincerity(MemberAccountService.AUTHENTICATE_PHONE,member));			
			memberAccountService.update(member);
			
			//因诚意度变更，则须同步更新用户的排名权重				 				
			memberAccountService.updateOrderWeigth(member);
		}
	}

	@Override
	public void submitAuthentication(String memberId,byte authenticationType)throws Exception {
		ApplyAuthentication applyAuthentication=new ApplyAuthentication();
		applyAuthentication.setAuthDate(new Date());
		applyAuthentication.setAuthMid(memberId);
		applyAuthentication.setAuthType(authenticationType);	
		this.add(applyAuthentication);
	}

	@Override
	public List<Map> queryAutheticationList(boolean isHandled, Paging paging)
			throws Exception {
		// TODO 分页查取已处理（未处理）认证申请
		String sql="SELECT AUTH_MID,AUTH_DATE,ESCORT_NICK_NAME,AUTH_TYPE,AUTH_RESULT,AUTH_RESON,AUTH_AUDIT_TIME,AUTH_MNG_ID "
				+ " FROM  yaw_apply_authentication,yaw_escort_info WHERE yaw_apply_authentication.AUTH_MID=yaw_escort_info.ESCORT_MID "
				+ " AND AUTH_RESULT=? ORDER BY AUTH_AUDIT_TIME DESC";
		return this.executeQuery(sql, paging,(byte)(isHandled?1:0));
		//return null;//asdfasdfa action层还要加上对应的功能；测试也要测试
	}
	
	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}

}
