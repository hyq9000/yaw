package com.yaw.common;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.common.cache.ApplicationCache;
import com.common.cache.CacheFlushBack;
import com.common.dbutil.Paging;

/**
 * 系统业务数据的缓存类
 * @author hyq
 *
 */
public class ApplicationCacheImpl implements ApplicationCache{
	private ConcurrentHashMap<String,Object> cache=new ConcurrentHashMap<String, Object>();
	
	@Override
	public Object get(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(Serializable key, Paging paging) {
		throw new Exception();
	}

	@Override
	public void put(Serializable key, Object value, int cacheType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void put(Serializable key, Object value, Paging paging) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Serializable key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getPagingCache(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putPagingCache(Serializable key, Integer totalCount){
		
	}

}
