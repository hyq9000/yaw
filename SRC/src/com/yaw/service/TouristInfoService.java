package com.yaw.service;

import java.util.List;
import java.util.Map;

import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.TouristInfo;
import com.yaw.entity.Tripplan;

/**
 * 类型描述:游客基本资料服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface TouristInfoService extends EntityService<TouristInfo> {	
	/**
	 * 查取综合排名前n名推荐游客
	 * @return 返加一个 "昵称"为KEY,"头像规格形象照的URL"为值的MAP结构;
	 */
	List<TouristInfo> queryTouristOrder(int n)throws Exception;
	
}
