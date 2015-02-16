package com.yaw.service;

import com.common.dbutil.Dao;
import com.common.utils.EntityService;
import com.yaw.entity.ManagerAccount;
import com.yaw.entity.MemberAccount;

/**
 * 
 * 类型描述:管理员帐号服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface ManagerAccountService extends EntityService<ManagerAccount> {
	/**在线*/
	byte ONLINE=1;
	/**离线*/
	byte OFFLINE=0;
	/**锁止*/
	byte STATUS_LOCK=2;
	/**正常*/
	byte STATUS_NORMAL=1;
	
	/**
	 * 登陆验证;如果验证成功,则返回完整帐号对象,完成以下工作
	 * 同时要计算活跃积分数;
	 * @param loginName
	 * @param password
	 * @return 
	 */
	ManagerAccount login(String loginName,String password,
			String loginIp) throws Exception;
	
	/**
	 * 注销;
	 * @param member
	 */
	void logout(ManagerAccount member) throws Exception;	
}
