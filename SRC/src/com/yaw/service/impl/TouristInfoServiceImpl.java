package com.yaw.service.impl;
import java.util.List;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.common.BusinessServiceImpl;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.TouristInfo;
import com.yaw.entity.Tripplan;
import com.yaw.service.TouristInfoService;

/**
 * 类型描述:游客基本资料服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class TouristInfoServiceImpl extends DaoHibernateImpl<TouristInfo>
		implements TouristInfoService {

	@Override
	public List<TouristInfo> queryTouristOrder(int n) throws Exception {
		String ql="from TouristInfo order by touristWeight desc";
		return super.query(ql, new Paging(n, 1), null);		
	}

	
	@Override
	public void setHeadPhoto(TouristInfo touristInfo, MemberAccount member, String url)
			throws Exception {
		/*
		 * 如果这是第一次设置形象照料,则修改资料完整度及完整详情 
		 */
		if(touristInfo.getTouristImage()==null){
			this.updateCompleted(touristInfo);		
		}	
		touristInfo.setTouristImage(url);
		super.update(touristInfo);
		
		/*
		 * 保存头像URl
		 */
		String headUrl=BusinessServiceImpl.generateHeadUrl(url);
		String sql="update YAW_MEMBER_ACCOUNT set MA_HEAD_ICON=? where MA_LOGIN_NAME=?";
		this.executeUpdate(sql, headUrl,member.getMaLoginName());	
		//将生成的HEAD头像URL写到(同步）对象中去；
		member.setMaHeadIcon(headUrl);
	}
	
	/**
	 * 封装会员资料完整度更新逻辑,
	 * @param escortInfo 伴游对象
	 * @param member 会员对象
	 */
	private void updateCompleted(TouristInfo touristInfo) throws Exception {
		String sql="select * from YAW_MEMBER_ACCOUNT where MA_LOGIN_NAME=?";
		List<MemberAccount> list=this.executeQuery(sql,touristInfo);
		if(list!=null && list.size()>0){
			MemberAccount member=list.get(0);
			/*
			 * 资料完善度更新
			 */		
			int percent=BusinessServiceImpl.generateCompletedPercent(member, touristInfo);
			int info=BusinessServiceImpl.generateCompletedDetail(member, touristInfo);	
			sql="update YAW_MEMBER_ACCOUNT set MA_COMPLETED_PERCENT=?,MA_COMPLETED_INFO=? where MA_LOGIN_NAME=?";
			this.executeUpdate(sql, percent,info,touristInfo.getTouristMid());
		}
		
	}
	

}
