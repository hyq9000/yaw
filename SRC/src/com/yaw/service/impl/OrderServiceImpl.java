package com.yaw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.common.utils.ShortMessageService;
import com.common.utils.BusinessException;
import com.yaw.common.ApplicationConfig;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Order;
import com.yaw.service.MemberAccountService;
import com.yaw.service.OrderService;

public class OrderServiceImpl extends DaoHibernateImpl<Order> implements
		OrderService {
	MemberAccountService memberAccountService;
	//短信服务
	ShortMessageService sms;
	//客服电话,用逗号隔开的多个号码;由spring注入
	String servicePhones;
	


	@Override
	public void submitOrder(Order order) throws Exception {
		this.add(order);
		String phones[]=servicePhones.split(",");
		int randomIndex=0;
		if(phones!=null && phones.length>0){
			randomIndex=(int)(Math.random()*phones.length);	
			//发短信通知
			if("pro".equalsIgnoreCase(ApplicationConfig.getInstance().getProperty("app.mod")))
				sms.sendMessage(phones[randomIndex], "充值通知:xxx");
		}else
			throw new BusinessException("客服手机未配置,无法发送短信!",-10);		
	}

	@Override
	public void comfirmPaied(String memberId,String orderId, boolean isOk,byte payMode,String payOrg) throws Exception {
		Order order=this.getById(orderId);
		if(order.getOrderMid().equals(memberId)){
			order.setOrderStatus(STATUS_PAY_YES);
			order.setOrderPayMode(payMode);
			order.setOrderPayOrg(payOrg);
			order.setOrderPayTime(new Date());
			this.update(order);
		}else
			throw new BusinessException("用户身份数据非法!");
	}
	
	@Override
	public void upgradeVip(String managerId,String memberId, String billId, byte vipGrade)
			throws Exception {	
		
		Order order=this.getById(billId);
		order.setOrderStatus(OrderService.STATUS_HANDLED);
		order.setOrderHandleTime(new Date());
		this.update(order);		
		
		MemberAccount member=memberAccountService.getById(memberId);
		//会员级别就是增值服务项中的ID值；
		member.setMaGrade(order.getOrderIncserviceId());
		memberAccountService.update(member);
	}
	
	@Override
	public void rechargeMoney(String managerId,String memberId, String billId)
			throws Exception {		
		/*
		 * 修改订单状态,及受理时间
		 */
		Order order=this.getById(billId);
		order.setOrderStatus(OrderService.STATUS_HANDLED);
		order.setOrderHandleTime(new Date());
		this.update(order);		
		
		/*
		 * 修改会员的约啊币余额
		 */
		MemberAccount member=memberAccountService.getById(memberId);
		member.setMaYaCoin(member.getMaYaCoin()+order.getOrderCount());
		memberAccountService.update(member);
	}

	@Override
	public List<Map> queryWaitforHandleOrderList(byte status, Paging paging) throws Exception {
		String sql="select ORDER_INCSERVICE_NAME as serviceName,ESCORT_PHONE as phone,"
				 +" ESCORT_QQ as qq,ORDER_SUBMIT_TIME as date,MA_GRADE as grade,MA_TYPE as type,"
				+ "ORDER_PAY_MODE as payModel, ORDER_TOTAL_MONEY as money  from YAW_MEMBER_ACCOUNT,"
				+ "YAW_ORDER,YAW_ESCORT_INFO where ORDER_MID=MA_LOGIN_NAME and ESCORT_MID=ORDER_MID"
				+ " and ORDER_STATUS=? and MA_TYPE="+MemberAccountService.TYPE_ESCORT
				+ " union "
				+ " select ORDER_INCSERVICE_NAME as serviceName,TOURIST_PHONE as phone,"
				 +" TOURIST_QQ as qq,ORDER_SUBMIT_TIME as date,MA_GRADE as grade,MA_TYPE as type,"
				+ "ORDER_PAY_MODE as payModel, ORDER_TOTAL_MONEY as money  from YAW_MEMBER_ACCOUNT,"
				+ "YAW_ORDER,YAW_TOURIST_INFO where ORDER_MID=MA_LOGIN_NAME and TOURIST_MID=ORDER_MID"
				+ " and ORDER_STATUS=? and MA_TYPE="+MemberAccountService.TYPE_TOURIST;
				
		return this.executeQuery(sql, paging, status,status);
	}
	
	
	public void setServicerPhones(String servicePhones) {
		this.servicePhones = servicePhones;
	}

	public void setSms(ShortMessageService sms) {
		this.sms = sms;
	}

	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public void setServicePhones(String servicePhones) {
		this.servicePhones = servicePhones;
	}
}
