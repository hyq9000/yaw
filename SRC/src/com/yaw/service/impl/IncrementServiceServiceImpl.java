package com.yaw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.yaw.common.BusinessException;
import com.yaw.entity.IncrementService;
import com.yaw.service.IncrementServiceService;

/**
 * 类型描述:增值服务与会员关系服务实现
 * </br>创建时期: 2014年12月18日
 * @author hyq
 */
public class IncrementServiceServiceImpl extends
		DaoHibernateImpl<IncrementService> implements IncrementServiceService {

	static Map<Integer,IncrementService> cache;//缓存全部增值服务对象
	private void _init() throws Exception{
		if(cache==null){
			List<IncrementService> list=super.getAll();
			cache=new HashMap<Integer, IncrementService>();
			if(list!=null){
				for(IncrementService is : list){
					cache.put(is.getIncserviceId(), is);
				}
			}
		}
	}
	
	@Override
	public String getServiceName(int serviceId) throws Exception {
		this._init();
		IncrementService is=cache.get(serviceId);
		if(is!=null)
			return is.getIncserviceName();
		else
			throw new BusinessException("指定的增值服务项不存在!");
	}

	@Override
	public int getServicePrice(int serviceId) throws Exception {
		this._init();
		IncrementService is=cache.get(serviceId);
		if( is!=null)
			return is.getIncservicePrice();
		else
			throw new BusinessException("指定的增值服务项不存在!");
	}

	@Override
	public int getServiceDayLength(int serviceId) throws Exception {
		this._init();
		IncrementService is=cache.get(serviceId);
		if( is!=null)
			return is.getIncserviceSuitDay();
		else
			throw new BusinessException("指定的增值服务项不存在!");
	}

	@Override
	public int getYeacoinServiceId()throws Exception {
		this._init();
		for(Map.Entry<Integer,IncrementService> is :cache.entrySet()){
			if(is.getValue().getIncserviceName().equals("约啊币"))
				return is.getValue().getIncserviceId();
		}
		return -1;
	}

	@Override
	public String getYeacoinServiceName() {
		return "约啊币";
	}

	
}
