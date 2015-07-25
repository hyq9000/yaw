package com.yaw.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import com.common.freemark.FreeMarkUtil;
import com.common.log.ExceptionLogger;
import com.common.sendmail.SendMail;
import com.common.tools.sms.ShortMessageService;
import com.common.web.Struts2Action;
import com.common.web.WebContextUtil;
import com.yaw.common.ApplicationCacheMapImpl;
import com.yaw.common.ApplicationConfig;
import com.yaw.common.BusinessConstants;
import com.common.utils.BusinessException;
import com.yaw.common.SystemServiceImpl;
import com.common.web.WebUtils;
import com.yaw.entity.ApplyAuthentication;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Order;
import com.yaw.service.ApplyAuthenticationService;
import com.yaw.service.EscortInfoService;
import com.yaw.service.IncrementServiceService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.OrderService;
import com.yaw.service.PhotoService;
import com.yaw.service.TouristInfoService;

/**
 *
 * 类型描述: 提供与会员帐号相关的WEB api接口定义及实现
 * </br>创建时期: 2015年1月3日
 * @author hyq
 */
public class MemberAccountAction extends Struts2Action {
	private OrderService orderService;
	private MemberAccountService memberAccountService;
	private IncrementServiceService incrementServiceService;
	private ApplyAuthenticationService applyAuthenticationService;	
	private ShortMessageService sms;
	//重置密码之URL;
	protected  final static String URl_RESET_PASSWORD="/backresetpwd.html",
			//伴游后台管理主页
			URL_BACK_ESCORT_MAIN="/back/escortMain.html",
			//游客后台管理主页
			URL_BACK_TOURIST_MAIN="/back/touristMain.html",
			//存储会员找回密码时所提交的email址;
			SESSION_KEY_LOOKFOR_PASSWORD_EMAIL="SESSION_KEY_LOOKFOR_PASSWORD_EMAIL",
			//存储会员邮箱验证时所提交内容的key
			SESSION_KEY_EMAIL_AUTHENTICATION="SESSION_KEY_EMAIL_AUTHENTICATION",
			SESSION_KEY_MOBILE_AUTHENTICATION="SESSION_KEY_EMAIL_AUTHENTICATION";
	/**
	 * 会话中存储会员基本信息对象的key;
	 */
	protected static final String SESSION_KEY_BASIC_INFO="BASIC_INFO_OJBECT";
	/**
	 * 验证用户名是否可用
	 * @param mn:用户名
	 * @return 一个json对象:
	 * {
	 *  code:1(已经存在) ,0:(不存在),-1:未知系统异常,-3:用户名格式有问题
	 * }
	 */
	public String checkMemberName(){
		String mn=request.getParameter("mn");
		if(mn!=null && !mn.trim().equals("")) {
			try {
				boolean rs= memberAccountService.checkMemberName(mn);
				out.print(WebUtils.responseCode(rs?1:0));
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}else{
			out.print(WebUtils.responseCode(-3));
		}
		return null;			
	}
	
	/**
	 * 找回、重置密码（当邮箱验证没问题，通过邮箱里的连接跳到重置密码页，发送重置请求；此方法即是处理该重置请求）；
	 * @param newpwd新密码
	 * @return json对象 {code:1(重置成功) ,0:(用户名不存在),-1:未知系统异常 }
	 */
	public String resetPassword(){
		String pwd=request.getParameter("newpwd");
		String email=(String)session.getAttribute(SESSION_KEY_LOOKFOR_PASSWORD_EMAIL);
		/*
		 * 如果session中有该用户的提请重置密码记录,则重置密码,否则响应错误;
		 */
		if(email==null|| email.trim().equals(""))
			out.print(WebUtils.responseCode(0));
		else{
			try {
				memberAccountService.resetPassword(email, pwd);
				out.print(WebUtils.responseCode(1));
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}
		return null;
	}
	
	/**
	 * 点忘记密码时,要求填写正确的email,系统发送重置密码邮箱验证连接;并保留该重置连接的"有效时间"为(同会话时长)30分钟;
	 * @param email 
	 * @param captche 验证码
	 * @return
	 * {
	 *  code:1:结果正常 ,-1:未知系统异常,-4,验证码不正确,-3邮箱不正确
	 * 	data:{url:"url",连接到进入邮箱的地址 }
	 * }
	 */
	public String retrievePasswordSendEmail(){
		String email=request.getParameter("email");
		String captcha=request.getParameter("captcha");
		String sessionCaptcha=(String)session.getAttribute("captcha");
		try {
			if(captcha==null || captcha.trim().equals("") 
					|| !captcha.equals(sessionCaptcha)){
				out.print(WebUtils.responseError( "验证码填写不正确!",-4));
				return null;
			}
			//如果邮箱填写不正确或邮箱未注册,则响应错误;
			if(email==null || email.trim().equals("")
					|| !memberAccountService.checkEmai(email)){
				out.print(WebUtils.responseError( "邮箱填写不正确!",-3));
			}else{			
				/*
				 * 将会员email md5后,email本身为值,放到application中;当用户收到邮件后面点过来的时候,
				 * 再以此验证用户;
				 */
				String md5Email=DigestUtils.md5Hex(email+ApplicationConfig.getInstance().getScretKey());
				application.setAttribute(md5Email,email);
				memberAccountService.sendEmailForLookforPassword(email);		
			}
		}catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	
	/**
	 * 检测email是否已经注册
	 * @param email 
	 * @return json对象 {code:1(已经存在) ,0:(不存在),-1:未知系统异常,-3:用户名格式有问题}
	 */
	public String checkEmail(){
		String email=request.getParameter("email");
		if(email!=null && !email.trim().equals("")) {
			try {
				boolean rs= memberAccountService.checkEmai(email);
				out.print(WebUtils.responseCode(rs?1:0));
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}else{
			out.print(WebUtils.responseCode(-3));
		}
		return null;	
	}
	
	
	private EscortInfoService escortInfoService;
	private TouristInfoService touristInfoService;
	
	/**
	 * 登陆验证;如果验证成功,则返回完整帐号对象,完成以下工作
	 * 同时要计算活跃积分数;
	 * @param loginName
	 * @param pwd
	 * @param captcha 验证码
	 * @return json 对象
	 * {
	 * 	code:1:登陆成功,-1:服务器异常,0:用户名或密码不存在;-4,验证码不正确,-3用户名或密码为空
	 *  data:{url:'url',登陆成功,要跳转的url;}
	 * }
	 */
	public String login(){
		String loginName=request.getParameter("loginName");
		String password=request.getParameter("pwd");
		String captcha=request.getParameter("captcha");
		String ip=request.getRemoteAddr();
		String sessionCaptcha=(String)session.getAttribute("captcha");
		
		if("pro".equalsIgnoreCase(ApplicationConfig.getInstance().getProperty("app.mod")) &&
				(captcha==null || captcha.trim().equals("") || !captcha.equalsIgnoreCase(sessionCaptcha))){
			out.print(WebUtils.responseError( "验证码填写不正确!",-4));		
		}else if(loginName==null || password==null 
					|| loginName.trim().equals("")|| password.trim().equals(""))
				out.print(WebUtils.responseError( "用户名或密码不能为空!",-3));
		else{
			try {
				MemberAccount user=memberAccountService.login(loginName, password, ip);
				//如果用户登陆成功,则把帐号对象及用户基本信息对象放到会话中去,同时返回要跳转的URL
				if(user!=null){
					WebContextUtil.getIntstance(request).setCurrentUser(session, user);
					Map data=new HashMap();
					data.put("url", "/back/main.html");
					//根据会员,查到对应的基本信息对象,并将该对象也放到session中去;
					Object baseInfo=null;
					if(user.getMaType()==MemberAccountService.TYPE_ESCORT )
						baseInfo=escortInfoService.getById(loginName);
					else
						baseInfo=touristInfoService.getById(loginName);
					session.setAttribute(SESSION_KEY_BASIC_INFO, baseInfo);
					
					out.print(WebUtils.responseData(1, data));
				}else{
					out.print(WebUtils.responseError( "用户名或密码不正确!",0));
				}
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}				
		}				
		return null;
	}
	
	/**
	 * 注销;
	 * @param member
	 * @return json对象
	 * {
	 * 	  code:1:注销成功,-1:系统异常;
	 * 	  data:{url:"注销后跳转的url"}
	 * }
	 */
	public String logout(){
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);		
		try {
			if(user!=null){
				memberAccountService.logout(user);
				session.invalidate();
			}
			Map data=new HashMap();
			data.put("url", "/index.html");
			out.write(WebUtils.responseData(1, data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 找回密码:发一封邮件到指定的邮箱中去,邮箱内容是一个重置密码的链接;响应该链接的请求;
	 * @param v 一个附在邮件链接后面的、被md5了的email字符串
	 */
	public String lookforPassword(){
		String md5Email=request.getParameter("v");
		if(md5Email!=null && !md5Email.trim().equals("")){
			try {
				String email=(String)application.getAttribute(md5Email);
				/*
				 * 如application中的有会员找回密码时提请的email相关值,则移到session中去,
				 * 并清除application中的值,跳转到重置密码页,
				 */
				if(email!=null && !email.trim().equals("")){
					session.setAttribute(SESSION_KEY_LOOKFOR_PASSWORD_EMAIL, email);					
					application.removeAttribute(md5Email);
					request.getRequestDispatcher(URl_RESET_PASSWORD).forward(request, response);
				}
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}else{
			out.print(WebUtils.responseError("email不正确!", -3));
		}
		return null;
	}
	
	/**
	 * 如果原密码正确,则修改成新密码
	 * @param old 原密码
	 * @param new 新密码
	 * @return json对象
	 * {code:1修改成功,-1服务器异常,-3,老密码输入不正确,-14:输入验证不正确}
	 */
	public String updatePassword(){
		String oldPassword=request.getParameter("old");
		String newPassword=request.getParameter("new");
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		if(oldPassword!=null && newPassword!=null 
				&& !oldPassword.trim().equals("") && !newPassword.trim().equals("")){
			try {
				memberAccountService.updatePassword(user, oldPassword, newPassword);
				out.print(WebUtils.responseCode(1));
			} catch (BusinessException be) {
				ExceptionLogger.writeLog(be, this);
				out.print(WebUtils.responseError(be.getMessage(), -3));
			}catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}else{
			out.print(WebUtils.responseError("新密码或老密码为空!", -4));
		}		
		return null;
	}
	
	
	/**
	 * 暂停交友,该状态的会员会从面页下架 
	 * @return 响应json对象
	 * {code:1:暂停成功,-1:服务器异常 }
	 */
	public String pauseMakeFriend(){
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		try {
			memberAccountService.pauseMakeFriend(user);
			
			//更新业务缓存对应的数据
			List midList=(List)ApplicationCacheMapImpl.getIntance().get(BusinessConstants.KEY_MAKE_FRIEND_OFF);
			if(midList==null){
				midList=new ArrayList();
				ApplicationCacheMapImpl.getIntance().put(BusinessConstants.KEY_MAKE_FRIEND_OFF,midList);
			}
			midList.add(user.getMaLoginName());
			
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 注册会员,成功后跳到控制后台,引导完成资料登记;
	 * @param ln 登陆名 ,建议使用邮箱名
	 * @param pwd 密码
	 * @param sex 性别 (男|女)
	 * @param  mt 会员类型:0为游客,1为伴游
	 * @return {
	 * 	code:1注册成功 ,-1服务器异常;
	 * 	data:{url:'登陆成功后跳转的url'}
	 * }
	 */
	public String regist(){
		String loginName=request.getParameter("ln");
		String password=request.getParameter("pwd");
		String sex =request.getParameter("sex");
		String memberType=request.getParameter("mt");
		String ip=request.getRemoteAddr();
		byte memberTypeValue=Byte.parseByte(memberType);
		try {
			MemberAccount user=memberAccountService.regist(loginName, password, sex.charAt(0), memberTypeValue, ip);
			/*
			 * 根据会员类型,确定会员注册成功后要跳转的URL,交给webUtils去产生格式的响应字符串
			 */
			Map data=WebUtils.generateMapData("url",memberTypeValue==0?URL_BACK_TOURIST_MAIN:URL_BACK_ESCORT_MAIN);
			out.print(WebUtils.responseData(1, data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 约啊币消费,生成一个服务认购订单,并即时修改付款状态,同时修改帐号约啊币余额;
	 * @param sid 增值服务id
	 * @param cc 币数
	 * @return {code:1,约啊币消费正常,-1,服务器异常,-2会话超时}
	 */
	public String yueaCoinConsume(){
		String serviceId=request.getParameter("sid");
		String coinCount=request.getParameter("cc");
		int intServiceId=Integer.parseInt(serviceId);
		int intCoinCount=Integer.parseInt(coinCount);
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		try {
			memberAccountService.yueaCoinConsume(user.getMaLoginName(), intServiceId, intCoinCount);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	
	/**
	 * 提交会员等级升级,由会员提请升级请求,生成下单、付款记录,通知后台处理;
	 * @param vg 会员等级所对应的增值服务的ID:
	 * @return {code:1,约啊币消费正常,-1,服务器异常,-2会话超时}
	 */
	public String applyUpgradeVip(){
		String grade=request.getParameter("vg");
		byte serviceId=Byte.parseByte(grade);
		
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		try {
			//生成一个消费认购订单
			Order order=new Order();
			order.setOrderCount(1);
			order.setOrderMid(user.getMaLoginName());
			order.setOrderNo(SystemServiceImpl.generateOrderNo());
			order.setOrderPayMode(OrderService.PAYMODE_PAY_NO);
			order.setOrderIncserviceId(serviceId);
			order.setOrderIncserviceName(incrementServiceService.getServiceName(serviceId));
			order.setOrderStatus(OrderService.STATUS_PAY_NO);
			order.setOrderSubmitTime(new Date());
			order.setOrderTotalMoney(incrementServiceService.getServicePrice(serviceId));
			//提交请求
			orderService.submitOrder(order);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 提交"会员充值"订单,一元人民币换成一个"约啊币";由会员提请求充值请求,生成下单、付款记录,通知后台处理;
	 * @param money 元
	 * @return json对象
	 * {code:1充值成功,-1服务器异常,-3,老密码输入不正确,-14:输入验证不正确}
	 */
	public String applyRechargeMoney(){
		String money=request.getParameter("money");
		int intMoney=Integer.parseInt(money);
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		try {
			int serviceId=incrementServiceService.getYeacoinServiceId();
			//生成一个消费认购订单
			Order order=new Order();
			order.setOrderCount(intMoney/incrementServiceService.getServicePrice(serviceId));
			order.setOrderMid(user.getMaLoginName());
			order.setOrderNo(SystemServiceImpl.generateOrderNo());
			order.setOrderPayMode(OrderService.PAYMODE_PAY_NO);
			order.setOrderIncserviceId(serviceId);
			order.setOrderIncserviceName(incrementServiceService.getYeacoinServiceName());
			order.setOrderStatus(OrderService.STATUS_PAY_NO);
			order.setOrderSubmitTime(new Date());
			order.setOrderTotalMoney(intMoney);
			//提交请求
			orderService.submitOrder(order);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 由会员提交各类人工认证申请,并生成申请认证记录,通知后台管理员处理;
	 * @param authType  认证类型码;描述如下:1：视频认证2：身份认证3：导游认证4：健康认证5：加入伴游俱乐部申请,6,QQ认证,7,微信认证
	 * @return  {code:1充值成功,-1服务器异常,-3,老密码输入不正确,-14:输入验证不正确}
	 */
	public String applyAuthentication(){
		String authType=request.getParameter("authType");
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);					
		try {
			byte byteAuthType=Byte.parseByte(authType);	
			applyAuthenticationService.submitAuthentication(user.getMaLoginName(),byteAuthType);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 发送认证连接到用户注册邮箱:根据随机码,发送一个验证链接到用户注册的邮箱中.
	 * @param email
	 * @return {code:1,-1,-2}
	 */
	public String sendEmailAuthenticationLink(){
		String email=request.getParameter("email");
		MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
		
		String userEmail=user.getMaEmail();
		String userLoginName=user.getMaLoginName();
		/*
		 * 将用户注册邮箱加密后作为key，邮箱明文作为值，存储到session中去，
		 */
		String codeKey=DigestUtils.md5Hex(userEmail+ApplicationConfig.getInstance().getScretKey());
		session.setAttribute(codeKey, userEmail);
		
		/*
		 * 用freemark模块技术，向用户邮箱发送”点回“链接（加密后的邮箱址作为“点回“链接的参数）
		 */
		Map<String,Object> data=new Hashtable<String,Object>();
		data.put("userName", user.getMaLoginName());
		data.put("code",codeKey);
		String path=request.getRealPath("/template/emailAuthentication.ftl");
		String emailBody=FreeMarkUtil.getContent(path, data);
		try {
			SendMail.sendMail("约啊网邮箱认证", emailBody,email);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 当用户从邮箱中点击邮箱认证连接过来时,验证该用户邮箱是否是用户填写的邮箱,没有问题，则设置该用户邮箱认证状态为已经认证；
	 * @param 验证码
	 * @return {code:1,-1,-2}
	 */
	public String handleEmailAuthentication(){
		try {
			String key=request.getParameterNames().nextElement().toString();
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			String token=(String)session.getAttribute(key);
			if(token!=null && token.equals(user.getMaEmail())){
				this.memberAccountService.saveAuthentication(user, memberAccountService.AUTHENTICATE_EMAIL);
				session.removeAttribute(key);
				out.print(WebUtils.responseCode(1));
			}else
				out.print(WebUtils.responseError("邮箱行为异常！", -21));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 提请手机认证:发指定验证码到指定手机
	 * @param photo 手机号
	 * @return {code :1,-1,-2}
	 */
	public String sendMobileAuthenticationCaptch(){
		try {
			String phone=request.getParameter("phone");
			String code="";
			for(int i=0;i<6;i++)
				code+=Math.floor(Math.random()*10);
			//将生成的手机验证码放到session;
			session.setAttribute(SESSION_KEY_MOBILE_AUTHENTICATION,code);
			sms.sendMessage(phone,code);
			out.print(WebUtils.responseCode(1));
		}  catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}

	/**
	 * 处理用户输入收到的手机验证码,提交的认证请求;
	 * @param captch 手机验证码
	 * @return{code:1:认证成功,-1:服务器异常,-5:验证码不正确}
	 * 
	 */
	public String handleMobileAuthentication(){
		try {
			String incaptch=request.getParameter("captch");
			String captch=(String)session.getAttribute(SESSION_KEY_MOBILE_AUTHENTICATION);
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			/*
			 * 如果提交的验证码无误,则设置该用户的手机认证状态;
			 */
			if(captch!=null && captch.equals(incaptch)){
				memberAccountService.saveAuthentication(user, MemberAccountService.AUTHENTICATE_PHONE);
				out.print(WebUtils.responseCode(1));
			}else{
				out.print(WebUtils.responseError("验证码不正确", -5));
			}
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public void setIncrementServiceService(
			IncrementServiceService incrementServiceService) {
		this.incrementServiceService = incrementServiceService;
	}

	public void setApplyAuthenticationService(
			ApplyAuthenticationService applyAuthenticationService) {
		this.applyAuthenticationService = applyAuthenticationService;
	}

	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}

	public void setTouristInfoService(TouristInfoService touristInfoService) {
		this.touristInfoService = touristInfoService;
	}
}
