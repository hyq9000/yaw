package com.yaw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.common.sendmail.SendMail;
import com.yaw.business.PointsActionType;
import com.yaw.business.Points;
import com.yaw.business.UnShelve;
import com.yaw.common.ApplicationConfig;
import com.common.utils.BusinessException;
import com.yaw.common.BusinessServiceImpl;
import com.yaw.common.SystemServiceImpl;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Order;
import com.yaw.entity.TouristInfo;
import com.yaw.service.EscortInfoService;
import com.yaw.service.IncrementServiceService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.OrderService;
import com.yaw.service.TouristInfoService;

/**
 * 类型描述: 会员帐号实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class MemberAccountServiceImpl extends DaoHibernateImpl<MemberAccount>
		implements MemberAccountService {	
	private  final static String LOOKFOR_PASSWORD="/action/memberAccount!resetPassword.action";
	private EscortInfoService escortInfoService;
	private 	TouristInfoService touristInfoService;
	private OrderService orderService;
	private IncrementServiceService incrementServiceService;
	
	@Points(action=PointsActionType.POINTS_LOGIN,index=0)
	@Override
	public MemberAccount login(String loginName, String password,String loginIp) throws Exception{
		String pwd2Md5= DigestUtils.md5Hex(password+ApplicationConfig.SCRET_KEY);
		List<MemberAccount> list=super.query("FROM MemberAccount WHERE maLoginName=? AND maPassword=? AND (maStatus=0 OR maStatus=2 OR maStatus=3)",
				loginName,pwd2Md5);
		MemberAccount user=list!=null && list.size()>0?list.get(0):null;
		if(user!=null){
			user.setMaOnline((byte)1);
			user.setMaLoginTime(new Date());
			/*
			 * 如果IP有变化,则更新IP及置空IP所在地名称,以便后台线程更新IP所在地名称
			 */
			if(!loginIp.trim().equals(user.getMaLoginIp().trim())){
				user.setMaLoginIp(loginIp);
				user.setMaIpAddr(null);
			}
			return user;
		}else
			return null;
	}

	@Override
	public void logout(MemberAccount member)throws Exception {
		//修改状态
		member.setMaOnline(MemberAccountService.OFFLINE);
		long timeLen=System.currentTimeMillis()-member.getMaLoginTime().getTime();
		//设置在线时长
		member.setMaOnlineLong((int)timeLen/1000);
		super.update(member);
	}

	@Override
	public void sendEmailForLookforPassword(String email) throws Exception {
		String md5Email=DigestUtils.md5Hex(email+ApplicationConfig.SCRET_KEY);
		/*
		 * 将email md5后,附到链接的url后;以标识用户;
		 */
		SendMail.sendMail("约啊网-找回密码", "邮件地址:http://www.yueawang.com"+LOOKFOR_PASSWORD+"?v="+md5Email, email);		
	}

	@Override
	public void updatePassword(MemberAccount member,String oldPassword, String newPassword) throws Exception{
		//对密码做MD5处理
		String oldPwd2Md5= DigestUtils.md5Hex(oldPassword+ApplicationConfig.SCRET_KEY),
				newPassword2Md5= DigestUtils.md5Hex(newPassword+ApplicationConfig.SCRET_KEY);
		//验证原密码无误,则修改成新密码
		if(member.getMaPassword().equals(oldPwd2Md5)){
			member.setMaPassword(newPassword2Md5);
			super.update(member);
		}else
			throw new BusinessException("原密码不正确!",-10);		
	}

	@Override
	public void resetPassword(String email, String newPassword) throws Exception{
		String 	newPassword2Md5= DigestUtils.md5Hex(newPassword+ApplicationConfig.SCRET_KEY);
		int rs=super.executeUpdate("update yaw_member_account set ma_password=? where ma_email=? and (ma_status=0 OR ma_status=2 OR ma_status=3)",
				newPassword2Md5,email);
		if(rs<1){
			throw new BusinessException("帐号存在异常!");
		}
	}

	@Override
	public void upgradeVip(MemberAccount member, byte vipGrade) throws Exception {
		member.setMaGrade(vipGrade);
		super.update(member);	
	}

	@Override
	public void rechargeMoney(MemberAccount member, int money) throws Exception {
		member.setMaYaCoin(member.getMaYaCoin()+money);
		super.update(member);
	}

	@Override
	public void authentication(MemberAccount member,byte authenticateType) throws Exception {
		member.setMaAuthenticated((byte)(member.getMaAuthenticated() | authenticateType));
		super.update(member);
	}

	@Override
	public void pauseMakeFriend(MemberAccount member) throws Exception {
		member.setMaMfStatus(MAKFRIEND_OFF);
		super.update(member);
	}

	@Override
	public MemberAccount regist(String loginName, String password, char sex,
			byte memberType,String ip)throws Exception {
		MemberAccount member=new MemberAccount();
		member.setMaLoginName(loginName);
		member.setMaPassword(DigestUtils.md5Hex(password+ApplicationConfig.SCRET_KEY));
		member.setMaType(memberType);
		member.setMaLoginTime(new Date());
		member.setMaOnline(ONLINE);
		member.setMaLoginIp(ip);
		member.setMaMfStatus(MAKEFRIEND_ON);
		
		//加入商业逻辑:送约啊币
		member.setMaYaCoin(BusinessServiceImpl.getRegistPresentYacoin());			
		super.add(member);
		
		//生成基本信息表数据
		if(memberType==TYPE_ESCORT){
			EscortInfo escortInfo=new EscortInfo();
			escortInfo.setEscortMid(loginName);
			escortInfo.setEscortSex(sex+"");
			escortInfo.setEscortOrderWeight(0);
			escortInfoService.add(escortInfo);
		}else{
			TouristInfo touristInfo=new TouristInfo();
			touristInfo.setTouristMid(loginName);
			touristInfo.setTouristSex(sex+"");
			touristInfo.setTouristOrderWeight(0);
			touristInfoService.add(touristInfo);
		}
		return member;
	}

	@Override
	public void yueaCoinConsume(String memberId, int serviceId, int coinCount) throws Exception{
		MemberAccount member=super.getById(memberId);
		int balance=member.getMaYaCoin();
		balance-=coinCount;
		if(balance>=0){
			member.setMaYaCoin(balance);
			
			//生成一个消费认购订单
			Order order=new Order();
			order.setOrderCount(coinCount);
			order.setOrderMid(memberId);
			order.setOrderNo(SystemServiceImpl.generateOrderNo());
			order.setOrderIncserviceId(serviceId);
			order.setOrderPayMode(OrderService.PAYMODE_YUEACION);
			order.setOrderIncserviceName(incrementServiceService.getServiceName(serviceId));
			order.setOrderStatus(OrderService.STATUS_PAY_YES);
			order.setOrderSubmitTime(new Date());
			order.setOrderTotalMoney(incrementServiceService.getServicePrice(serviceId)*coinCount);
			//提交订单
			orderService.add(order);
			
		}else
			throw new BusinessException("约啊币余额不足!");		
	}

	@Override
	public boolean checkEmai(String email) throws Exception{
		String sql="select ma_login_Name from yaw_member_account where ma_email=?";
		List list=super.executeQuery(sql, email);
		return list!=null && list.size()>0;
	}

	@Override
	@UnShelve(property="maLoginName",type=MemberAccount.class)
	public List<MemberAccount> queryNewRegist(int topCount,byte memberType)
			throws Exception {		
			String ql="FROM MemberAccount WHERE maType=? ORDER BY maRegistTime DESC";
			return super.query(ql,new Paging(topCount, 1),memberType);			 
	}

	@Override
	public boolean checkMemberName(String memberName) throws Exception {
		String sql="select ma_login_Name from yaw_member_account where ma_login_name=?";
		List list=super.executeQuery(sql, memberName);
		return list!=null && list.size()>0;
	}

	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setIncrementServiceService(
			IncrementServiceService incrementServiceService) {
		this.incrementServiceService = incrementServiceService;
	}

	public void setTouristInfoService(TouristInfoService touristInfoService) {
		this.touristInfoService = touristInfoService;
	}

	@Override
	public List<Map> statisticsQueryMember(Paging paging,String fieldName, String opFlag,Object... value)
			throws Exception {
		String tmp="";
		if(value!=null && value.length>1){
			tmp=" and "+fieldName+opFlag+"? and ?";
		}else if(value!=null && value.length==1){
			tmp=" and "+fieldName+opFlag+"?";
		}
			
		String sql="SELECT ESCORT_NICK_NAME AS nickName,ESCORT_NAME AS NAME, YEAR(CURDATE())- YEAR(ESCORT_BIRTHDAY) AS age,"
				+ "ESCORT_SEX AS sex,MA_LOGIN_TIME AS loginTime,MA_IP_ADDR AS ip,MA_ONLINE AS online,"
				+ "MA_STATUS AS mstatus,MA_GRADE AS grade, MA_AUTHENTICATED auth,MA_TYPE AS mtype,MA_REGIST_TIME registTime "
				+ "FROM YAW_MEMBER_ACCOUNT,yaw_escort_info WHERE ESCORT_MID=MA_LOGIN_NAME"+tmp
				+ " union "
				+ "SELECT TOURIST_NICKNAMES AS nickName,TOURIST_NAME AS NAME, YEAR(CURDATE())- YEAR(TOURIST_BIRTHDAY) AS age,"
				+ "TOURIST_SEX AS sex,MA_LOGIN_TIME AS loginTime,MA_IP_ADDR AS ip,MA_ONLINE AS online,"
				+ "MA_STATUS AS mstatus,MA_GRADE AS grade, MA_AUTHENTICATED auth,MA_TYPE AS mtype,MA_REGIST_TIME registTime "
				+ "FROM YAW_MEMBER_ACCOUNT,YAW_TOURIST_INFO WHERE TOURIST_MID=MA_LOGIN_NAME"+tmp;
		return this.executeQuery(sql,paging,value);
	}
	
	@Override
	public List<MemberAccount> getMemberListByIds(String... memberIds)
			throws Exception {
		String ids="";
		for(int i=0;i<memberIds.length;ids+="?"+",");
		ids=ids.substring(0,ids.length()-1);
		String ql="from MemberAccount where maLoginName in("+ids+") order by maLoginName ";
		return this.query(ql, memberIds);
	}

	@Override
	public void setAuthentication(MemberAccount member, byte type) throws  Exception{
		byte authenticationStatus=member.getMaAuthenticated();
		byte newAuthenticationStatus=0;
		switch(type){
			case MemberAccountService.AUTHENTICATE_EMAIL:
				newAuthenticationStatus=(byte)(authenticationStatus|1);
				break;
			case MemberAccountService.AUTHENTICATE_GUIDE:
				newAuthenticationStatus=(byte)(authenticationStatus|32);
				break;
			case MemberAccountService.AUTHENTICATE_HEALTH:
				newAuthenticationStatus=(byte)(authenticationStatus|8);
				break;
			case MemberAccountService.AUTHENTICATE_IDENTITY:
				newAuthenticationStatus=(byte)(authenticationStatus|16);
				break;
			case MemberAccountService.AUTHENTICATE_PHONE:
				newAuthenticationStatus=(byte)(authenticationStatus|2);
				break;
			case MemberAccountService.AUTHENTICATE_VIDO:
				newAuthenticationStatus=(byte)(authenticationStatus|4);
				break;
		}
		member.setMaAuthenticated(newAuthenticationStatus);
		this.update(member);	
	}

	@Override
	public List getAllMakeFriendOffMemeberId() throws  Exception{
		String sql="select MA_LOGIN_NAME from YAW_MEMBER_ACCOUNT where A_MF_STATUS=0";
		return super.executeQuery(sql);
	}
}
