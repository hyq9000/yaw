package com.yaw.action;

import com.common.beanutil.BeanUtil;
import com.common.log.ExceptionLogger;
import com.common.web.Struts2Action;
import com.common.web.WebContextUtil;
import com.common.web.WebUtils;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.service.ApplyAuthenticationService;
import com.yaw.service.EscortInfoService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.RTripplanEscortService;

/**
 * 
 * 类型描述: 提供伴游会员的后台管理逻辑的相关WEB接口的定义及实现;
 * </br>创建时期: 2014年12月26日
 * @author hyq
 */
public class EscortAction extends Struts2Action {
	private EscortInfo escortInfo;
	private EscortInfoService escortInfoService;
	private MemberAccountService memberAccountService;
	private ApplyAuthenticationService applyAuthenticationService;
	private RTripplanEscortService rTripplanEscortService;
	
	
	public EscortAction(){
		ExceptionLogger.writeLog(ExceptionLogger.DEBUG, "create EscortAction", null, this.getClass());
	}

	/**
	 * 发自荐信给指定的计划,会触发积分计数:).
	 * @param tid 计划ID
	 * @param recommend 自荐信
	 */
	public String  recommendTo(){
		try {
			String recommend=request.getParameter("recommend");
			String tid=request.getParameter("tid");
			int tipint=Integer.parseInt(tid);
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			
			rTripplanEscortService.recommendTo(user.getMaLoginName(),recommend,tipint);
			out.print(WebUtils.responseCode(1));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("约请计划ID格式不正确!"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 完成第一步基本资料
	 * @param escortInfoId 伴游ID
	 * @param nick 呢称
	 * @param name 姓名
	 * @param love 爱好
	 * @param liveAddr 居住地
	 * @param language 语言能力
	 * @param birthday 生日
	 * @param driverYear 驾龄
	 * @param job 职业
	 */
	public String completeBaseData(){	
		try {
			ExceptionLogger.writeLog(ExceptionLogger.DEBUG,request.getParameterMap().toString(),null,this.getClass());	
			EscortInfo escortInfoTmp=(EscortInfo)session.getAttribute(MemberAccountAction.SESSION_KEY_BASIC_INFO);
			BeanUtil.mergeProperty(escortInfo, escortInfoTmp);
			escortInfoService.update(escortInfo);
			session.setAttribute(MemberAccountAction.SESSION_KEY_BASIC_INFO,escortInfo);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {		
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 完成第二步形体资料
	 * @param escortInfo.escortInfoId 伴游ID
	 * @param escortInfo.escortHeight 身高
	 * @param escortInfo.escortWeight 体重
	 * @param escortInfo.escortBody 形体自评
	 * @param escortInfo.escortImage 形象自评
	 * @param escortInfo.escortFeel 气质自评
	 * @return 一个json对象:
	 * {
	 *  code:{1:结果正常 ,0:修改不成功,-1:未知系统异常,-2:会话超时}
	 * }
	 */
	public String completeBodyData(){
		return this.completeBaseData();
	}
	
	/**
	 * 完成第三步伴游资料
	 * @param escortInfo.escortMid 伴游ID
	 * @param escortInfo.escortType 伴游类型
	 * @param escortInfo.escortTryto 愿意尝试交往类型
	 * @param escortInfo.escortPrice 每天费用
	 * @param escortInfo.escortTripAddr 愿去目的地
	 * @param escortInfo.escortExp 伴游经验类型
	 * @param escortInfo.escortAttractive 最吸引人特点
	 * @param escortInfo.escortRecommend 自荐信
	 * @return 一个json对象:
	 * {
	 *  code:{1:结果正常 ,0:修改不成功,-1:未知系统异常,-2:会话超时}
	 * }
	 */
	public String complteEscortData( ){
		return this.completeBaseData();
	}
	
	/**
	 * 完成第四步联系方式
	 * @param escortInfo.escortMid 伴游ID
	 * @param escortInfo.escortPhone 电话
	 * @param email 
	 * @param escortInfo.escortQq
	 * @param escortInfo.escortWeixin 微信
	 * @return 一个json对象:
	 * {
	 *  code:{1:结果正常 ,0:修改不成功,-1:未知系统异常,-2:会话超时}
	 * }
	 */
	public String completeContactData(){
		String email=request.getParameter("email");
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);		
		if(email!=null && !email.trim().equals("")){
			user.setMaEmail(email);
			try {
				memberAccountService.update(user);
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}
		return this.completeBaseData();
	}
	
	
	/**
	 * 申请加入伴游俱乐部
	 * @return  {code:1充值成功,-1服务器异常,-3,老密码输入不正确,-14:输入验证不正确}
	 */
	public String applyEscortClub(){
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);				
		try {
			applyAuthenticationService.submitAuthentication(user.getMaLoginName(),ApplyAuthenticationService.TYPE_JOIN_CLUB);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	

	public void setrTripplanEscortService(
			RTripplanEscortService rTripplanEscortService) {
		this.rTripplanEscortService = rTripplanEscortService;
	}

	public void setEscortInfo(EscortInfo escortInfo) {
		this.escortInfo = escortInfo;
	}
	

	public EscortInfo getEscortInfo() {
		return escortInfo;
	}

	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}

	

	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public void setApplyAuthenticationService(
			ApplyAuthenticationService applyAuthenticationService) {
		this.applyAuthenticationService = applyAuthenticationService;
	}
	
}
