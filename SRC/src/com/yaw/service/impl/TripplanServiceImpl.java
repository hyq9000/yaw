package com.yaw.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.dbutil.Dao;
import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.business.PointsActionType;
import com.yaw.business.Points;
import com.yaw.business.UnShelve;
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
	@Points(action=PointsActionType.POINTS_PUBLISH_TRIPPLAN)
	public void add(Tripplan entity)  throws Exception{
		super.add(entity);		
	}

	@Override
	@UnShelve(property="TRIPPLAN_MID",type=Map.class)
	public List<Map> queryTripplanList(Paging paging)  throws Exception {
		String sql="SELECT TOURIST_NICKNAME,MA_GRADE,MA_HEAD_ICON,"
				+ "TRIPPLAN_ID,TRIPPLAN_MID,TRIPPLAN_STATUS,TRIPPLAN_TITLE,TRIPPLAN_MONEY_MODEL,"
				+ "TRIPPLAN_TYPE,TRIPPLAN_PUBLISH_TIME,TRIPPLAN_DEPART_TIME,TRIPPLAN_DEPART_ADDR,"
				+ "TRIPPLAN_DESTINATION,TRIPPLAN_DAY,TRIPPLAN_WANT_SEX,TRIPPLAN_WANT_AGE,TRIPPLAN_WANT_PERSONS,"
				+ "TRIPPLAN_OTHER,TRIPPLAN_ORDER_WEIGHT,TRIPPLAN_BEFOCUS_COUNT "
				+ "FROM yaw_tripplan,yaw_member_account,yaw_tourist_info WHERE "
				+ "TOURIST_MID=MA_LOGIN_NAME AND MA_LOGIN_NAME=TRIPPLAN_MID "
				+ "ORDER BY TRIPPLAN_ORDER_WEIGHT DESC";
		return super.executeQuery(sql,paging);
	}

	@Override
	public List<Map<String, Object>> getAllRecommend(int tripplanId)	throws Exception {
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
			List<Map<String, Object>> rs=new ArrayList<Map<String, Object>>();
			for(int i=0;i<memberList.size();i++){
				Map tmp=new HashMap();
				/*屏蔽一些数据*/
				memberList.get(i).setMaPassword("");
				
				tmp.put("member",memberList.get(i));
				tmp.put("escort", escortList.get(i));
				rs.add(tmp);
			}
			return rs;
		}else
			return null;
	}

	@Override
	@UnShelve(property="tripplanMid",type=Tripplan.class)
	public List<Tripplan> simpleSearch(char wantSex, String destinaion,
			String depart,Paging paging) throws Exception {
		String ql="from Tripplan where tripplanWantSex=? AND tripplanDepartTime<=? and tripplanDestination=? order by tripplanOrderWeight DESC";
		super.query(ql, paging,wantSex,depart,destinaion);
		return null;
	}

	@Override
	@UnShelve(property="tripplanMid",type=Tripplan.class)
	public List<Tripplan> advanceSearch(String[] propertyName, int[] opflags,
			Object[] values,Paging paging) throws Exception {
		
		
		if(propertyName==null || propertyName.length==0 
				|| values==null ||values.length==0 
				|| opflags==null || opflags.length==0){				
			throw new Exception("条件、参数、值个数不能为空!");
		}else{
			//为多个条件生成默认的逻辑与操作码放到数组中
			int [] conditionFlag=new int[propertyName.length-1];
			for(int i=0;i<conditionFlag.length;i++)
				conditionFlag[i]=Dao.CONDITION_AND;
			
			if(propertyName.length!=values.length || propertyName.length!=opflags.length){
				throw new Exception("查询条件名、条件值、操作符个数不彼配!");
			}else{
				String hql="FROM EscortInfo WHERE ";
				String where="";
				where+=propertyName[0]+" = ? ";
				for(int i=0;i<opflags.length;i++){
					where+="AND "+propertyName[i+1]+"= ? ";
				}
				hql+=where+" ORDER BY tripplanOrderWeight DESC";		
				return super.query(hql, paging,values);
			}
		}
	}

	@Override
	@UnShelve(property="tripplanMid",type=Tripplan.class)
	public List<Tripplan> queryNewPublish() throws Exception {
		String ql="FROM Tripplan ORDER BY tripplanPublishTime DESC";
		return super.query(ql, new Paging(6,1));
	}

	@Override
	@UnShelve(property="tripplanMid",type=Tripplan.class)	
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

	public void setrTripplanEscortService(
			RTripplanEscortService rTripplanEscortService) {
		this.rTripplanEscortService = rTripplanEscortService;
	}

	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}
	
	
}
