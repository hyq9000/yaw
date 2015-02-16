package com.yaw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.common.dbutil.DaoHibernateImpl;
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
			List<IncrementService> list=super.executeQuery("from IncrementService");
			cache=new HashMap<Integer, IncrementService>();
			for(IncrementService is : list){
				cache.put(is.getIncserviceId(), is);
			}
		}
	}
	
	@Override
	public String getServiceName(int serviceId) throws Exception {
		this._init();
		IncrementService is=cache.get(serviceId);
		return is==null?null:is.getIncserviceName();
	}

	@Override
	public int getServicePrice(int serviceId) throws Exception {
		this._init();
		IncrementService is=cache.get(serviceId);
		return is==null?null:is.getIncservicePrice();
	}

	@Override
	public int getServiceDayLength(int serviceId) throws Exception {
		this._init();
		IncrementService is=cache.get(serviceId);
		return is==null?null:is.getIncserviceSuitDay();
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
