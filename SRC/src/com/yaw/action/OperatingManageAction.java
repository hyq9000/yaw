package com.yaw.action;

import java.util.List;

import com.common.dbutil.Paging;
import com.common.log.ExceptionLogger;
import com.common.utils.BusinessException;
import com.common.web.Struts2Action;
import com.common.web.WebContextUtil;
import com.common.web.WebUtils;
import com.yaw.common.ApplicationConfig;
import com.yaw.entity.ApplyAuthentication;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.ManagerAccount;
import com.yaw.entity.Order;
import com.yaw.service.ApplyAuthenticationService;
import com.yaw.service.EscortInfoService;
import com.yaw.service.ManagerAccountService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.OrderService;

/**
 * 类型描述:提供运营支撑后台管理模块的相关web接口定义及实现
 * </br>创建时期: 2014年12月26日
 * @author hyq
 */
public class OperatingManageAction extends Struts2Action {
	ManagerAccountService managerAccountService;
	MemberAccountService memberAccountService;
	OrderService orderService;
	ApplyAuthenticationService applyAuthenticationService;
	EscortInfoService escortInfoService; 
	//管理员管理后台主页
	private final static String URL_BACK_MAIN="/back/main.html";
	//管理员登陆页
	private final static String URL_BACK_LOGOUT="/managerLogin.html";
	/**
	 * 登陆验证;如果验证成功,则返回完整帐号对象,完成以下工作
	 * 同时要计算活跃积分数;
	 * @param ln
	 * @param pwd
	 * @return {
	 * 	code:1,-1,
	 *  data:{url:'登陆成功后的url'}
	 * }
	 */
	public String login(){
		String loginName=request.getParameter("name");
		String password=request.getParameter("pwd");
		String captcha=request.getParameter("captcha");
		String sessionCaptcha=(String)(null);//session.getAttribute("captcha");
		if("pro".equalsIgnoreCase(ApplicationConfig.getInstance().getProperty("app.mod")) 
				&&!captcha.equalsIgnoreCase(sessionCaptcha)){
			out.print(WebUtils.responseError("验证码不正确", -3));
		}else{
			String ip=request.getRemoteAddr();
			try {
				ManagerAccount manager=managerAccountService.login(loginName, password, ip);
				if(manager!=null){
					WebContextUtil.getIntstance(request).setCurrentUser(session, manager);
					out.print(WebUtils.responseData(1, WebUtils.generateMapData("url", URL_BACK_MAIN)));
				}else{
					out.print(WebUtils.responseError("用户名或密码输入不正确",0));
				}
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(-1));
			}
		}
		return null;
	}
	
	

	/**
	 * 注销
	 * @return {
	 * 	code:1,-1,
	 *  data:{url:'登陆成功后的url'}
	 * }
	 */
	public String logout(){
		try {
			ManagerAccount manager=(ManagerAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			if(manager!=null){
				session.invalidate();		
				managerAccountService.logout(manager);
			}
			out.print(WebUtils.responseData(1, WebUtils.generateMapData("url", URL_BACK_LOGOUT)));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}

	
	/**
	 * 受理已付款的会员等级升级订单
	 * @param mid 会员id
	 * @param billId 订单号
	 * @param grade 会员等级:可以是MemberAccountService.GRADE_开头的这几个常量值
	 */
	public String handleUpgradeVip(){
		String mid=request.getParameter("mid");
		String grade=request.getParameter("grade");	
		String billId=request.getParameter("billId");
		ManagerAccount user=(ManagerAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		try {
			byte gradeInt=Byte.parseByte(grade);			
			orderService.upgradeVip(user.getMngLoginName(),mid, billId, gradeInt);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 方法功能描述：授理付款成功的订单
	 * @param billId 订单ID
	 * @return
	 */
	public String handleOrder(){
		String orderId=request.getParameter("billId");
		ManagerAccount user=(ManagerAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		if(orderId==null || orderId.isEmpty()){
			out.print(WebUtils.responseInputCheckError("订单号不正确"));
		}else{
			try {
				orderService.handleOrder(user.getMngLoginName(), orderId);		
				out.print(WebUtils.responseCode(1));
			} catch (BusinessException e) {
				out.print(WebUtils.responseInputCheckError(e.getMessage()));
			}catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}
		return null;
	}
	
	
	/**
	 * 受理已经付款的公员充值订单；
	 * 给会员手工充值,一元人民币换成一个"约啊币";该请求由后台管理员,对充值的付款确认后,通过后台管理程序来完成;
	 * @param mid 会员id
	 * @param money 元
	 * @param billId 订单id;
	 * @return json对象
	 * {code:1充值成功,-1服务器异常,-14:输入验证不正确}
	 */
	public String handleRechargeMoney(){
		String mid=request.getParameter("mid");
		String billId=request.getParameter("billId");
		ManagerAccount user=(ManagerAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		try {		
			orderService.rechargeMoney(user.getMngLoginName(),mid, billId);
			out.print(WebUtils.responseCode(1));
		}catch(NumberFormatException ne){
			out.print(WebUtils.responseInputCheckError("充值数不是数字"));
		}catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 认证审核受理,把认证状态更改一下;
	 * @param applyId 认证申请ID
	 * @Param ispass 是否通过 0，未通，1：通过
	 * @param reason 未通过原因
	 */
	public String handleAuthentication(){
		try {
			ManagerAccount user=(ManagerAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			String reason=request.getParameter("reason");
			String ispassStr=request.getParameter("ispass");
			boolean ispass=Boolean.parseBoolean(ispassStr);
			String aid=request.getParameter("applyId");
			int applyId=Integer.parseInt(aid);
			applyAuthenticationService.handleAuthentication(user.getMngLoginName(), applyId,ispass,reason);
			out.print(WebUtils.responseCode(1));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("认证申请号格式不正确"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 受理加入伴游俱乐部认证申请
	 * @param applyId 认证申请ID
	 * @Param ispass 是否通过
	 * @param reason 未通过原因
	 */
	public String handleApplyEscortClub() {
		try {
			ManagerAccount user=(ManagerAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			String reason=request.getParameter("reason");
			String ispassStr=request.getParameter("ispass");
			boolean ispass=Boolean.parseBoolean(ispassStr);
			String aid=request.getParameter("applyId");
			int applyId=Integer.parseInt(aid);
			
			applyAuthenticationService.handleAuthentication(user.getMngLoginName(), applyId,ispass,reason);
			/*
			 * 完善资料
			 */
			ApplyAuthentication aa=applyAuthenticationService.getById(applyId);
			if(aa.getAuthType()==ApplyAuthenticationService.TYPE_JOIN_CLUB){
				EscortInfo escortInfo=escortInfoService.getById(aa.getAuthMid());
				escortInfo.setEscortClubMember((byte)1);
				escortInfoService.update(escortInfo);
			}
			out.print(WebUtils.responseCode(1));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("认证申请号格式不正确"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 订单受理,分页查取待处理的未付款的订单
	 * @param pn 页数
	 * @return 格式如原型所述
	 */
	public String queryWaitforHandleUnpaiedOrderList(){
		String pn=request.getParameter("pn");
		int pageNo=Integer.parseInt(pn);
		return this.queryWaitforHandleOrderList(OrderService.STATUS_PAY_NO,pageNo);
	}

	/**
	 * 查出等待处理的订单列表
	 * @param pn 页数
	 * @param status 订单状态
	 * @return
	 */
	public String queryWaitforHandleOrderList(byte status,int pageNo) {
		try {			
			List data=orderService.queryWaitforHandleOrderList(status, new Paging(15, pageNo));
			out.print(WebUtils.responseData(data!=null?data.size():0, data));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("分页数不正确"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	
	/**
	 * 订单受理,分页查取待处理的已经付款的订单
	 * @param paging
	 * @return 格式如原型所述
	 */
	public String queryWaitforHandlePaiedOrderList(){
		String pn=request.getParameter("pn");
		int pageNo=Integer.parseInt(pn);
		return this.queryWaitforHandleOrderList(OrderService.STATUS_PAY_YES,pageNo);
	}
	
	
	/**
	 * 分页查询待处理的认证申请
	 * @param paging
	 * @return 格式如原型所述
	 */
	public String queryWaitforHandledAuthetication(){
		try {
			String pn=request.getParameter("pn");
			int pageNo=Integer.parseInt(pn);
			List data=applyAuthenticationService.queryAutheticationList(false,new Paging(15, pageNo));
			out.print(WebUtils.responseData(data!=null?data.size():0, data));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("分页数不正确"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 分页查询已处理的认证申请
	 * @param paging
	 * @return 格式如原型所述
	 */
	public String queryAlreadyHandledAuthetication(){
		try {	
			String pn=request.getParameter("pn");
			int pageNo=Integer.parseInt(pn);
			List data=applyAuthenticationService.queryAutheticationList(true,new Paging(15, pageNo));
			out.print(WebUtils.responseData(data!=null?data.size():0, data));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("分页数不正确"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 后台支撑->会员统计查询管理
	 * @param pn 分页号
	 * @param fn 查询条件之字段名称
	 * @param op 操作符 =,>,<,<>,between,like
	 * @param val1,val2 查询条件字段对应的值，如操作为between ，则为两个值 
	 * @return 返回同原型结构的json数据组对象
	 */
	public String statisticsQueryMemeber(){
		try {
			String pn=request.getParameter("pn");
			String fieldName=request.getParameter("fn");
			String opFlag=request.getParameter("op");
			String value1=request.getParameter("val1");
			String value2=request.getParameter("val2");
			int pageNo=Integer.parseInt(pn);
			List data=memberAccountService.statisticsQueryMember(new Paging(15, pageNo),fieldName,opFlag,value1,value2);
			out.print( WebUtils.responseData(data!=null?data.size():0, data));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("分页数不正确"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	public void setManagerAccountService(ManagerAccountService managerAccountService) {
		this.managerAccountService = managerAccountService;
	}

	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setApplyAuthenticationService(
			ApplyAuthenticationService applyAuthenticationService) {
		this.applyAuthenticationService = applyAuthenticationService;
	}



	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}
}
