package com.yaw.service.impl;
import java.util.List;

import com.common.dbutil.DaoHibernateImpl;
import com.yaw.business.PointsActionType;
import com.yaw.business.Points;
import com.yaw.entity.RTripplanEscort;
import com.yaw.service.RTripplanEscortService;

/**
 * 类型描述:旅游计划与伴游关系服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class RTripplanEscortServiceImpl extends
		DaoHibernateImpl<RTripplanEscort> implements RTripplanEscortService {

	@Override
	@Points(action=PointsActionType.POINTS_RECOMMEND,index=0)
	public void recommendTo(String memberId,String recommendCntext, int tripplanId) throws Exception {
		RTripplanEscort rte=new RTripplanEscort();
		rte.setRteAutoMatch((byte)0);
		rte.setRteMid(memberId);
		rte.setRteRecommend(recommendCntext);
		rte.setRteTripplanId(tripplanId);
		this.add(rte);
	}

	@Override
	public List<RTripplanEscort> getTheRelationOfTripplan(int tripplanId)
			throws Exception {
		String ql="from RTripplanEscort where rteTripplanId=? order by rteTime desc";
		return super.query(ql, tripplanId);
	}
	
	
	
	
}
