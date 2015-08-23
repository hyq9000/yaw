package com.yaw.service;

import java.util.Calendar;
import java.util.Date;

import com.common.utils.EntityService;
import com.yaw.entity.RIncserviceMember;

/**
 * 
 * 类型描述:增值服务与会员关系服务接口
 * </br>创建时期: 2014年12月18日
 * @author hyq
 */
public interface RIncserviceMemberService extends
		EntityService<RIncserviceMember> {
	/**11：城市置顶
	12：搜索置顶
	13：分类置顶
	2：公开联系方式
	3：首页头条*/
	byte SERVICE_CITY_TOP=11,
		SERVICE_SEARCH_TOP=12,
		SERVICE_CLASS_TOP=13,
		SERVICE_PUBLIC_CONTACT=2,
		SERVICE_HOMEPAGE_LINE=3;
	
	/**
	 * 方法功能描述：当用户实际付款成功后，将服务绑定到用户，成功后，该用户的增值服务即即刻生效
	 * @param orderNo 订单号
	 * @param serviceId 增值服务id
	 * @param memberId 用户登陆名
	 * @throws Exception
	 */
	public void bindIncToMember(String orderyNo,int serviceId,String memberId) throws Exception;
}
