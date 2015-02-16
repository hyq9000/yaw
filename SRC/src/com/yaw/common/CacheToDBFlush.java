package com.yaw.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时将缓存中的数据刷到数据库去; 类型描述: </br>创建时期: 2015年2月11日
 * TODO: 缓存刷新器;
 * @author hyq
 */
public class CacheToDBFlush extends Thread {
	Map<String,Map> caches= new HashMap<String, Map>();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
