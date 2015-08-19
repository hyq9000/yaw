package com.yaw.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.common.log.ExceptionLogger;
import com.common.web.Struts2Action;
import com.common.web.WebContextUtil;
import com.common.web.WebUtils;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Tripplan;
import com.yaw.service.TripplanService;

/**
 * 类型描述:提供游客会员后台管理逻辑的接口定义及实现;
 * </br>创建时期: 2014年12月26日
 * @author hyq
 */
public class TouristAction extends Struts2Action {
	
	private TripplanService tripplanService;
	private Tripplan tp;
	
	/**
	 * TODO 方法功能描述：完善游客的基本资料
	 * @return
	 */
	public String completeBaseData(){
		
		return null;
	}
	
	/**
	 * TODO 方法功能描述： 完善游客的伴游偏爱数据
	 * @return
	 */
	public String completeEscortPredilection(){
		return null;
	}
	
	
	/**
	 * TODO 方法功能描述： 完善游客的伴游偏爱数据
	 * @return
	 */
	public String completeContactData(){
		return null;
	}
	
	/**
	 * 取得该邀约计划的所有伴游对象及对应的会员帐号对象,并将关系表"邀约计划伴游关注表(YAW_R_TRIPPLAN_ESCORT)"的
	 * 自荐信值设置"伴游对象.自荐信"字段,该对象是一次性使用,不能被执久化;
	 * @param pid  邀约计划ID
	 * @return 	返回	{
	 * 	code:n,
	 *  data:[{
	 * 		member:MemberAccount对象
	 *  	escort:EscortInfo对象
	 * 	},...]
	 * }
	 */
	public String getAllRecommend(){
		try {
			String pid=request.getParameter("pid");
			int tripplanId=Integer.parseInt(pid);
			List<Map<String,Object>> data=tripplanService.getAllRecommend(tripplanId);
			out.print(WebUtils.responseData(data));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("页号格式不正确!"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 方法功能描述：发布邀请计划
	 * @param tripplanDay 行程天数
	 * @param tripplanDepartAddr 始发地
	 * @param tripplanDepartTime 始发时间
	 * @param tripplanDestination 目的地
	 * @param tripplanMoneyModel 费用报酬方式 0：费用全包，有酬金 1：费用全包，无酬金 2：费用AA制，结伴旅游\n
	 * @param tripplanOrderWeight 体重
	 * @param tripplanOther 其他要求
	 * @param tripplanTitle 约请计划标题
	 * @param tripplanType 	伴游类型： 0：纯陪玩 1：景区游 2：城市游
	 * @param tripplanWantAge 要求年龄
	 * @param tripplanWantPersons  人数
	 * @param tripplanWantSex 要示性别
	 * @return json对象{
	 * 	code: 1 :成功,0:失败,-1：系统异常
	 * }
	 */
	public String publishTripPlan(){
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		try {
			if(tp!=null&& user!=null){
				tp.setTripplanPublishTime(new Date());
				tp.setTripplanMid(user.getMaLoginName());
				tp.setTripplanStatus((byte)0);
				tripplanService.add(tp);
				out.print(WebUtils.responseCode(1));
			}else{
				out.print(WebUtils.responseError("数据提交不正确或用户未登陆", 0));
			}			
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

	public Tripplan getTp() {
		return tp;
	}

	public void setTp(Tripplan tp) {
		this.tp = tp;
	}
	
	
}
