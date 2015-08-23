package com.yaw.service;
import java.util.List;
import java.util.Map;

import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.ApplyAuthentication;

/**
 * 类型描述:客服审核认证服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface ApplyAuthenticationService extends EntityService<ApplyAuthentication> {
	/**：视频认证*/
	byte TYPE_VIDO= 3;
	/**身份认证*/
	byte TYPE_IDDENTIFY=5;
	/**导游认证*/
	byte TYPE_GUIDE=6;
	/**健康认证*/
	byte TYPE_HEALTH=4;
	/**加入伴游俱乐部申请*/
	byte TYPE_JOIN_CLUB=9;		
	/**QQ认证*/
	byte TYPE_QQ=7;
	/**微信认证*/
	byte TYPE_WEIXIN=8;	
	/**
	 * 视频认证受理,分页查取待处理的视频认证;
	 * 用户点了视频验证,就会生成一个认证申请记录,客服线下完成	QQ认证后,则可打开本方法的结果,在线上作一下受理操作,改变用户的认证状态;
	 * @param paging
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getAuthenticationList(Paging paging) throws Exception;
	
	/**
	 * 提交认证申请;产生一条认证申请记录;
	 * @param memberId
	 * @param authenticationType  ApplyAuthenticationService.TYPE_开头的常量值
	 */
	void submitAuthentication(String memberId,byte authenticationType) throws Exception;
	
	/**
	 * 受理会员提交的认证申请案
	 * @param manageId 申述管理员ID
	 * @param applyAutheticationId 受理申请ID
	 * @param isPass 审核通过否
	 * @param nopassReason 未通过原因
	 */
	void handleAuthentication(String manageId,int applyAutheticationId,boolean isPass,String nopassReason)throws Exception;
	
	
	/**
	 * 分页查取已处理（未处理）认证申请
	 * @Param isHandled 是否已处理  true：已处理
	 * @param paging 分页对象
	 * @return
	 */
	List<Map> queryAutheticationList(boolean isHandled,Paging paging) throws Exception;
}
