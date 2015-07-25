package com.yaw.service;

import java.util.List;
import java.util.Map;

import com.common.dbutil.Dao;
import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.Order;

/**
 * 订单服务接口
 * @author hyq
 */
public interface OrderService extends EntityService<Order> {
	
	/**支付平台*/
	byte PAYMODE_PLATEFORM=1;
	/**柜台汇款*/
	byte PAYMODE_WINDOW=2;
	/**网银转帐*/
	byte PAYMODE_NETBANK=3;
	/**约啊币付*/
	byte PAYMODE_YUEACION=0;
	/**还未付款*/
	byte PAYMODE_PAY_NO=-1;
	
	/**待付款*/
	byte STATUS_PAY_NO=0;
	/**已付款*/
	byte STATUS_PAY_YES=1;
	/**已取消*/
	byte STATUS_CANCEL=2;
	/**已过期*/
	byte STATUS_TIMEOUT=3;
	/**已删除*/
	byte STATUS_DELETED=4;
	/**已受理*/
	byte STATUS_HANDLED=5;
	
	
	/**
	 * 系统服务下单:存储下单记录并及时发送短信,提醒客服作充值处理(前期就刷新管理后台的页面,或通知管理后台的APP)
	 * 客服可以有多个电话,系统从中随机选一个发信息
	 * @param order 订单对象
	 */ 	
	void submitOrder(Order order) throws Exception;
	
	
	/**
	 * 确认已经完成付款
	 * @param orderId 订单流水号
	 * @param isOk 是否已经完成付款
	 */
	void comfirmPaied(String orderId,boolean isOk) throws Exception;
	
	/**
	 * 管理员受理会员升级订单,更新Vip会员等级;
	 */
	void upgradeVip(String managerId,String memberId,String billId,byte vipGrade) throws Exception;
	
	/**
	 * 会员约啊币充值
	 * @param memberId 会员id
	 * @param billId 订单号
	 * @param count 充值数(元)
	 */
	void rechargeMoney(String managerId, String memberId,String billId,int count) throws Exception;
	
	
	/**
	 * 分页查取待处理的未付(已付)订单
	 * @Param status 订单状态码 STATUS_PAY开头的常量值 
	 * @param paging 分页对象
	 * @return
	 */
	List<Map> queryWaitforHandleOrderList(byte status,Paging paging) throws Exception;
}
