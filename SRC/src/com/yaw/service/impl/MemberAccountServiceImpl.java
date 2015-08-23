package com.yaw.service.impl;

import java.util.Calendar;
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
import com.yaw.entity.RIncserviceMember;
import com.yaw.entity.TouristInfo;
import com.yaw.service.EscortInfoService;
import com.yaw.service.IncrementServiceService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.OrderService;
import com.yaw.service.RIncserviceMemberService;
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
	private TouristInfoService touristInfoService;
	private OrderService orderService;
	private IncrementServiceService incrementServiceService;
	private RIncserviceMemberService rincService;
	
	@Points(action=PointsActionType.POINTS_LOGIN,index=0)
	@Override
	public MemberAccount login(String loginName, String password,String loginIp) throws Exception{
		String pwd2Md5= DigestUtils.md5Hex(password+ApplicationConfig.getInstance().getScretKey());
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
			this.update(user);
			
			//因用户的在线状态变更，及时同时排名权重的值
			this.updateOrderWeigth(user);
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
		
		//因用户的在线状态变更，及时同时排名权重的值
		this.updateOrderWeigth(member);
	}

	@Override
	public void sendEmailForLookforPassword(String email) throws Exception {
		String md5Email=DigestUtils.md5Hex(email+ApplicationConfig.getInstance().getScretKey());
		/*
		 * 将email md5后,附到链接的url后;以标识用户;
		 */
		SendMail.sendMail("约啊网-找回密码", "邮件地址:http://www.yueawang.com"+LOOKFOR_PASSWORD+"?v="+md5Email, email);		
	}

	@Override
	public void updatePassword(MemberAccount member,String oldPassword, String newPassword) throws Exception{
		//对密码做MD5处理
		String oldPwd2Md5= DigestUtils.md5Hex(oldPassword+ApplicationConfig.getInstance().getScretKey()),
				newPassword2Md5= DigestUtils.md5Hex(newPassword+ApplicationConfig.getInstance().getScretKey());
		//验证原密码无误,则修改成新密码
		if(member.getMaPassword().equals(oldPwd2Md5)){
			member.setMaPassword(newPassword2Md5);
			super.update(member);
		}else
			throw new BusinessException("原密码不正确!",-10);		
	}

	@Override
	public void resetPassword(String email, String newPassword) throws Exception{
		String 	newPassword2Md5= DigestUtils.md5Hex(newPassword+ApplicationConfig.getInstance().getScretKey());
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
		
		if(this.checkMemberName(loginName))
			throw new BusinessException("该用户名已经存在！");
		
		MemberAccount member=new MemberAccount();
		member.setMaLoginName(loginName);
		member.setMaPassword(DigestUtils.md5Hex(password+ApplicationConfig.getInstance().getScretKey()));
		member.setMaType(memberType);
		member.setMaLoginTime(new Date());
		member.setMaOnline(ONLINE);
		member.setMaLoginIp(ip);
		member.setMaMfStatus(MAKEFRIEND_ON);
		member.setMaRegistTime(new Date());
		
		//加入商业逻辑:送约啊币
		member.setMaYaCoin(BusinessServiceImpl.getRegistPresentYacoin());			
		super.add(member);
		
		//生成基本信息表数据
		if(memberType==TYPE_ESCORT){
			EscortInfo escortInfo=new EscortInfo();
			escortInfo.setEscortNickName(loginName);//呢称原始值为登陆名
			escortInfo.setEscortMid(loginName);
			escortInfo.setEscortSex(sex+"");
			escortInfo.setEscortOrderWeight(0);
			escortInfoService.add(escortInfo);
		}else{
			TouristInfo touristInfo=new TouristInfo();
			touristInfo.setTouristNickname(loginName);//呢称原始值为登陆名
			touristInfo.setTouristMid(loginName);
			touristInfo.setTouristSex(sex+"");
			touristInfo.setTouristOrderWeight(0);
			touristInfoService.add(touristInfo);
		}
		return member;
	}

	@Override
	public void yueaCoinConsume(String memberId,String billId) throws Exception{
		MemberAccount member=super.getById(memberId);
		int balance=member.getMaYaCoin();
		Order order=orderService.getById(billId);
		if(order==null){
			throw new BusinessException("订单："+billId+"没有找到");
		}
		balance-=order.getOrderTotalMoney();
		if(balance>=0){
			//修改余额
			member.setMaYaCoin(balance);
			this.update(member);
			
			//修改订单的支付及处理状态
			order.setOrderPayMode(OrderService.PAYMODE_YUEACION);
			order.setOrderStatus(OrderService.STATUS_PAY_YES);
			order.setOrderPayTime(new Date());
			order.setOrderPayOrg("约啊网");
			orderService.update(order);
			
			/*
			 * 关联用户与增值服务项关系
			 */
			rincService.bindIncToMember(billId,order.getOrderIncserviceId(), memberId);
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

	public void setRincService(RIncserviceMemberService rincService) {
		this.rincService = rincService;
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
		
		if(value!=null && value.length>1 && value[0]!=null && value[1]!=null){
			tmp=" and "+fieldName+opFlag+"? and ?";
		}else if(value!=null && value.length>0 && value[0]!=null){
			tmp=" and "+fieldName+opFlag+"?";
		}
			
		String sql="SELECT ESCORT_NICK_NAME AS nickName,ESCORT_NAME AS NAME, YEAR(CURDATE())- YEAR(ESCORT_BIRTHDAY) AS age,"
				+ "ESCORT_SEX AS sex,MA_LOGIN_TIME AS loginTime,MA_IP_ADDR AS ip,MA_ONLINE AS online,"
				+ "MA_STATUS AS mstatus,MA_GRADE AS grade, MA_AUTHENTICATED auth,MA_TYPE AS mtype,MA_REGIST_TIME registTime "
				+ "FROM YAW_MEMBER_ACCOUNT,yaw_escort_info WHERE ESCORT_MID=MA_LOGIN_NAME"+tmp
				+ " union "
				+ "SELECT TOURIST_NICKNAME AS nickName,TOURIST_NAME AS NAME, YEAR(CURDATE())- YEAR(TOURIST_BIRTHDAY) AS age,"
				+ "TOURIST_SEX AS sex,MA_LOGIN_TIME AS loginTime,MA_IP_ADDR AS ip,MA_ONLINE AS online,"
				+ "MA_STATUS AS mstatus,MA_GRADE AS grade, MA_AUTHENTICATED auth,MA_TYPE AS mtype,MA_REGIST_TIME registTime "
				+ "FROM YAW_MEMBER_ACCOUNT,YAW_TOURIST_INFO WHERE TOURIST_MID=MA_LOGIN_NAME"+tmp;
		if(value!=null && value.length>1 && value[0]!=null && value[1]!=null)
			return this.executeQuery(sql,paging,value[0],value[1],value[0],value[1]);
		else if(value!=null && value.length>0 && value[0]!=null)
			return this.executeQuery(sql,paging,value[0],value[0]);
		else
			return null;
	}
	
	@Override
	public List<MemberAccount> getMemberListByIds(String... memberIds)
			throws Exception {
		String ids="";
		for(int i=0;i<memberIds.length;i++,ids+="?"+",");
		ids=ids.substring(0,ids.length()-1);
		String ql="from MemberAccount where maLoginName in("+ids+") order by maLoginName ";
		return this.query(ql, memberIds);
	}

	/*@Override
	public void saveAuthentication(MemberAccount member, byte type) throws  Exception{
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
			case MemberAccountService.AUTHENTICATE_QQ:
				newAuthenticationStatus=(byte)(authenticationStatus|64);
				break;
			case MemberAccountService.AUTHENTICATE_WEIXIN:
				newAuthenticationStatus=(byte)(authenticationStatus|128);
				break;
		}
		member.setMaAuthenticated(newAuthenticationStatus);
		this.update(member);	
	}*/

	@Override
	public List getAllMakeFriendOffMemeberId() throws  Exception{
		String sql="select MA_LOGIN_NAME from YAW_MEMBER_ACCOUNT where A_MF_STATUS=0";
		return super.executeQuery(sql);
	}
	
	@Override
	public void updateOrderWeigth(MemberAccount member)	throws Exception {
		//修改用户的权重值；
		int orderWeight=BusinessServiceImpl.generateOrderWeight(member.getMaGrade(),
				member.getMaSincerity(), 
				member.getMaPoints(), 
				member.getMaCompletedPercent(),
				member.getMaOnline()==1);		
		member.setMaOrderWeight(orderWeight);	
		this.update(member);

		String sql="";
		if(member.getMaType()==MemberAccountService.TYPE_ESCORT)
			sql="update YAW_ESCORT_INFO set ESCORT_ORDER_WEIGHT=? where ESCORT_MID=?";
		else if(member.getMaType()==MemberAccountService.TYPE_TOURIST)
			sql="update YAW_TOURIST_INFO set TOURIST_ORDER_WEIGHT=? where TOURIST_MID=?";
		this.executeUpdate(sql, orderWeight,member.getMaLoginName());
	}
}
