package com.yaw.action;

import java.util.List;
import com.common.log.ExceptionLogger;
import com.common.web.Struts2Action;
import com.common.web.WebUtils;
import com.yaw.service.TripplanService;

/**
 * 类型描述:暴露给游客会员后台管理逻辑的接口定义及实现;
 * </br>创建时期: 2014年12月26日
 * @author hyq
 */
public class TouristAction extends Struts2Action {
	
	private TripplanService tripplanService;
	/**
	 * 取得该邀约计划的所有伴游对象及对应的会员帐号对象,并将关系表"邀约计划伴游关注表(YAW_R_TRIPPLAN_ESCORT)"的
	 * 自荐信值设置"伴游对象.自荐信"字段,该对象是一次性使用,不能被执久化;
	 * @param pid  邀约计划ID
	 * @return 	返回	{
	 * 	code:n,
	 *  data:List<Map<MemberAccount,EscortInfo>>的JSON结构
	 * }
	 */
	public String getAllRecommend(){
		try {
			String pid=request.getParameter("pid");
			int tripplanId=Integer.parseInt(pid);
			List data=tripplanService.getAllRecommend(tripplanId);
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("页号格式不正确!"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	public void setTripplanService(TripplanService tripplanService) {
		this.tripplanService = tripplanService;
	}
	
	
}
