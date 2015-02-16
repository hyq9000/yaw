package com.yaw.service.impl;
import java.util.List;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
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


}
