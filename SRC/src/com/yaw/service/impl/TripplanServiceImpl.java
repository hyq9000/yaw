package com.yaw.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.business.ActionType;
import com.yaw.business.Points;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.RTripplanEscort;
import com.yaw.entity.Tripplan;
import com.yaw.service.EscortInfoService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.RTripplanEscortService;
import com.yaw.service.TripplanService;

/**
 * 
 * 类型描述:旅游记录服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class TripplanServiceImpl extends DaoHibernateImpl<Tripplan> implements
		TripplanService {
	RTripplanEscortService rTripplanEscortService;
	MemberAccountService memberAccountService;
	EscortInfoService escortInfoService;
	@Override
	/**
	 * 发布邀约计划,同时增加积分;
	 */
	@Points(action=ActionType.POINTS_PUBLISH_TRIPPLAN)
	public void add(Tripplan entity)  throws Exception{
		super.add(entity);		
	}

	@Override
	public List<Map> queryTripplanList(Paging paging)  throws Exception {
		String ql="SELECT TOURIST_NICKNAME,MA_GRADE,MA_HEAD_ICON,yaw_tripplan.* "
				+ "FROM yaw_tripplan,yaw_member_account,yaw_tourist_info WHERE "
				+ "TOURIST_MID=MA_LOGIN_NAME AND MA_LOGIN_NAME=TRIPPLAN_MID "
				+ "ORDER BY tripplanOrderWeight DESC";
		return super.query(ql,paging);
	}

	@Override
	public List<Map<MemberAccount, EscortInfo>> getAllRecommend(int tripplanId)	throws Exception {
		List<RTripplanEscort> rlist=rTripplanEscortService.getTheRelationOfTripplan(tripplanId);
		if(rlist!=null){	
			/*
			 * 取得所有会员ID,封装到数组
			 */
			String[] memberIds=new String[rlist.size()];
			for(int i=0;i<rlist.size();i++){
				memberIds[i]=rlist.get(i).getRteMid();
			}
			
			/*
			 * 根据会员ID数组,按序取得会员及伴游对象集;
			 */
			List<MemberAccount> memberList=memberAccountService.getMemberListByIds(memberIds);
			List<EscortInfo> escortList=escortInfoService.getEscortInfoListByIds(memberIds);
			
			/*
			 * 将会员及伴游封装成List<Map<MemberAccount, EscortInfo>>对象
			 */
			List<Map<MemberAccount, EscortInfo>> rs=new ArrayList<Map<MemberAccount,EscortInfo>>();
			for(int i=0;i<memberList.size();i++){
				Map tmp=new HashMap();
				tmp.put(memberList.get(i), escortList.get(i));
				rs.add(tmp);
			}
			return rs;
		}else
			return null;
	}

	@Override
	public List<Tripplan> simpleSearch(char wantSex, String destinaion,
			String depart) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tripplan> advanceSearch(String[] propertyName, int[] opflags,
			Object[] values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tripplan> queryNewPublish() throws Exception {
		String ql="FROM Tripplan ORDER BY tripplanPublishTime DESC";
		return super.query(ql, new Paging(6,1));
	}

	@Override
	public List<Tripplan> queryHomePage3Line() throws Exception {
		String ql="FROM Tripplan ORDER BY tripplanOrderWeight DESC";
		return super.query(ql, new Paging(3,1));
	}

	@Override
	public List<Tripplan> getMemberTripplanList(String memberId, Paging page)
			throws Exception {
		String ql="FROM Tripplan WHERE tripplanMid=? ORDER BY tripplanPublishTime DESC";
		return super.query(ql, page,memberId);
	}
}
