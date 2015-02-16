package com.yaw.service;

import java.util.List;

import com.common.dbutil.Dao;
import com.common.utils.EntityService;
import com.yaw.entity.RTripplanEscort;

/**
 * 
 * 类型描述:旅游计划与伴游关系服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface RTripplanEscortService extends EntityService<RTripplanEscort> {
	
	/**
	 * 发了自荐信会触发积分计数:).
	 * @param recommend
	 */
	void recommendTo(String memberId,String recommendCntext, int tripplanId) throws Exception;
	
	/**
	 * 取得与指定约请计划的所有推荐关联的RTripplanEscort对象集
	 * @param tripplanId 约请计划ID
	 * @return
	 * @throws Exception
	 */
	List<RTripplanEscort> getTheRelationOfTripplan(int tripplanId) throws Exception;
}
