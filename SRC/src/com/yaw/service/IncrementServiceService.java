package com.yaw.service;

import java.util.List;

import com.common.utils.EntityService;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.IncrementService;

/**
 * 类型描述:增值服务与会员关系服务接口;
 * <<因这个服务接口调用的频率非常高,所以需要提供一个缓存机制,确保对数据库的查询只进行一次,其他调用请求读缓存;
 * 一旦有更新则刷新这个缓存>>;
 * </br>创建时期: 2014年12月18日
 * @author hyq
 */
public interface IncrementServiceService extends
		EntityService<IncrementService> {
	/**城市置顶*/
	int TOP_CITY=11;
	/**搜索置顶*/
	int TOP_SEARCH=12;
	/**分类置顶*/
	int TOP_CATEGORY=13;

	/**
	 * 根据增值服务Id返回会员等级名称
	 * @param serviceId 服务ID
	 * @return 会员等级名
	 */
	String getServiceName(int serviceId)throws Exception;	
	
	/**
	 * 根据增值服务返回增值服务价格
	 * @param serviceId 增值服务Id
	 * @return 会员等级价格(元)
	 */
	int getServicePrice(int serviceId)throws Exception;
	
	/**
	 * 根据增值服务返回增值服务的单位服务时长(天)
	 * @param serviceId 增值服务Id
	 * @return 会员等级价格(元)
	 */
	int getServiceDayLength(int serviceId)throws Exception;
	
	/**
	 * 取得约啊币在增值服务表上的ID值
	 * @return
	 */
	int getYeacoinServiceId()throws Exception;
	/**
	 * 取得约啊币在增值服务表上的服务名称;
	 * @return
	 */
	String getYeacoinServiceName()throws Exception;
}
