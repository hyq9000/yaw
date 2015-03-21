package com.yaw.common;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.common.cache.ApplicationCache;
import com.common.cache.CacheFlushBack;
import com.common.cache.ICache;
import com.common.dbutil.Paging;

/**
 * 系统业务数据的缓存类
 * @author hyq
 */
public class ApplicationCacheMapImpl implements ICache{
	private ConcurrentHashMap<Serializable,Object> cache=new ConcurrentHashMap<Serializable, Object>();
	private static ApplicationCacheMapImpl _instance;
	private ApplicationCacheMapImpl(){}
	/**
	 * 单例模式获得到类的唯一对象
	 * @return
	 */
	public static ApplicationCacheMapImpl getIntance(){
		if(_instance==null)
			return new ApplicationCacheMapImpl();
		return _instance;
	}
	@Override
	public Object get(Serializable key) {
		return cache.get(key);
	}

	@Override
	public void remove(Serializable key) {
		cache.remove(key);
	}

	@Override
	public void put(Serializable key, Object value) {
		cache.put(key, value);
	}
}
