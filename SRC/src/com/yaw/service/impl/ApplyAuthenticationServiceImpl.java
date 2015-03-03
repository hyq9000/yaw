package com.yaw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
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
			memberAccountService.setAuthentication(member, type);
			if(type==ApplyAuthenticationService.TYPE_JOIN_CLUB){
				EscortInfo escortInfo=escortInfoService.getById(memberId);
				escortInfo.setEscortClubMember((byte)1);
				escortInfoService.update(escortInfo);
			}
		}
		
		/*1：视频认证
		2：身份认证
		3：导游认证
		4：健康认证
		5：加入伴游俱乐部申请
		
		1位：代表邮箱认证与否
		2位：代表手机认证与否
		3位：代表视频认证与否
		4位：代表健康认证与否
		5位：代表身份认证与否
		6位：代表导游认证与否*/
	}

	@Override
	public void submitAuthentication(String memberId,byte authenticationType)throws Exception {
		ApplyAuthentication applyAuthentication=new ApplyAuthentication();
		applyAuthentication.setAuthAuditTime(new Date());
		applyAuthentication.setAuthMid(memberId);
		applyAuthentication.setAuthType(authenticationType);	
		this.add(applyAuthentication);
	}

}
