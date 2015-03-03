package com.yaw.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.common.dbutil.DaoHibernateImpl;
import com.yaw.common.ApplicationConfig;
import com.yaw.entity.ManagerAccount;
import com.yaw.service.ManagerAccountService;

/**
 * 
 * 类型描述:管理员帐号服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class ManagerAccountServiceImpl extends DaoHibernateImpl<ManagerAccount>
		implements ManagerAccountService {

	@Override
	public ManagerAccount login(String loginName, String password,
			String loginIp) throws Exception {
		String ql="from ManagerAccount where mngLoginName=? and mngPassword=?";
		List<ManagerAccount> list=this.query(ql, loginName,DigestUtils.md5Hex(password+ApplicationConfig.SCRET_KEY));
		ManagerAccount user= list!=null&& list.size()>0?list.get(0):null;
		
		if(user!=null){
			user.setMngLoginIp(loginIp);
			user.setMngLoginTime(new Date());
			user.setMngOnline(ONLINE);
			this.update(user);
		}
		return user;
	}

	@Override
	public void logout(ManagerAccount member) throws Exception {
		long cur=new Date().getTime();
		member.setMngLoginLength((int)(cur-member.getMngLoginTime().getTime()/1000));
		member.setMngOnline(OFFLINE);
		this.update(member);
	}


}
