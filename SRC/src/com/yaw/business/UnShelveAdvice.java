package com.yaw.business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.BeanUtils;

import com.common.cache.ICache;
import com.common.log.ExceptionLogger;
import com.yaw.common.ApplicationCacheMapImpl;
import com.yaw.common.BusinessConstants;
import com.yaw.service.MemberAccountService;

/**
 * 类型描述：一个环绕通知，用以从已查出结果集合中，移除“交友状态”为0的会员的相关记录后，再返回；
 * 实现思路：
 * 	<li>将所有“交友状态”为0的会员id取出，缓存起来cache，
 *  <li>cache中的会员ID与查询结果集的每个记录的会员ID彼配，
 *  <li>从结果中移出彼配置了的相关记录；
 *  <li>如果交友状态变更，则应刷新缓存；
 * </br>时间：2015-3-18
 * @author hyq
 */
public class UnShelveAdvice implements MethodInterceptor {
	private ICache cache=ApplicationCacheMapImpl.getIntance();
	private MemberAccountService memberAccountService;
	private List<String> makeFriedOffList;//交友状态为0的所有会员ID
	
	
	
	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public  UnShelveAdvice(){
		makeFriedOffList=(List)cache.get(BusinessConstants.KEY_MAKE_FRIEND_OFF);
		if(makeFriedOffList==null){
			try {
				//将所有下架会员ID加入到缓存
				List list=memberAccountService.getAllMakeFriendOffMemeberId();
				cache.put(BusinessConstants.KEY_MAKE_FRIEND_OFF, list);
			} catch (Exception e) {
				ExceptionLogger.writeLog(e,UnShelveAdvice.class);
			}
		}			
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object result=invocation.proceed();
		UnShelve an=invocation.getMethod().getAnnotation(UnShelve.class);
		
		if(result==null || an==null)
			return result;
		
		Class clazz=an.type();
		String propertyName=an.property();
		/*
		 * 如果查询结果是List，才进行操作，否则直接跳过
		 */
		if(result instanceof List){
			List resultList=(List)result;
			/*
			 * 根据集合中的类型及属性名称，取出该记录中为“会员ID”的值,
			 * 如果该“会员ID”的值在makeFriedOffList中有，则将
			 * 该“会员ID”对应的记录从resultList中移除；
			 */
			Iterator it=resultList.iterator();
			while(it.hasNext()){
				Object tmp=it.next();
				/*
				 * 找到“会员ID”的值
				 */
				String memberId=null;
				if(clazz.getName().equals("Map")){
					Map map=(Map)tmp;
					memberId=(String)map.get(propertyName);
				}else{
					try {
						memberId=BeanUtils.getProperty(tmp, propertyName);
					} catch (NoSuchMethodException e) {
						//如果没有该方法，则继续
						continue;
					}
				}
				/*
				 * 存在于makeFriedOffList，则从结果集中（resultList） 移除
				 */
				if(makeFriedOffList!=null && makeFriedOffList.size()>0 && memberId!=null && !memberId.equals("")){
					if(makeFriedOffList.contains(memberId)){
						it.remove();
					}
				}
			}
		}
		//无论有无移隔操作，都返回结果；
		return result;
	}

}
