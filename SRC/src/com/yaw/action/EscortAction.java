package com.yaw.action;

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
	private MemberAccountService memberService;
	private ApplyAuthenticationService applyAuthenticationService;
	private RTripplanEscortService rTripplanEscortService;

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
	 * @param sex 性别
	 * @param name 姓名
	 * @param love 爱好
	 * @param liveAddr 居住地
	 * @param language 语言能力
	 * @param birthday 生日
	 * @param job 职业
	 */
	public String completeBaseData(){	
		try {
			escortInfoService.update(escortInfo);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {		
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 完成第二步形体资料
	 * @param escortInfoId 伴游ID
	 * @param height 身高
	 * @param weight 体重
	 * @param body 形体自评
	 * @param image 形象自评
	 * @param feel 气质自评
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
	 * @param escortInfoId 伴游ID
	 * @param escortType 伴游类型
	 * @param tryto 愿意尝试交往类型
	 * @param price 每天费用
	 * @param tripAddr 愿去目的地
	 * @param escortExp 伴游经验类型
	 * @param attractive 最吸引人特点
	 * @param recommend 自荐信
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
	 * @param escortInfoId 伴游ID
	 * @param phone 电话
	 * @param email 
	 * @param qq
	 * @param weixin 微信
	 * @return 一个json对象:
	 * {
	 *  code:{1:结果正常 ,0:修改不成功,-1:未知系统异常,-2:会话超时}
	 * }
	 */
	public String completeContactData(){
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

	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}

	public void setMemberService(MemberAccountService memberService) {
		this.memberService = memberService;
	}

	public void setApplyAuthenticationService(
			ApplyAuthenticationService applyAuthenticationService) {
		this.applyAuthenticationService = applyAuthenticationService;
	}
	
}
