package com.yaw.service;

import java.util.List;
import java.util.Map;

import com.common.dbutil.Dao;
import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Tripplan;

/**
 * 类型描述:邀约计划服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface TripplanService extends EntityService<Tripplan> {
	/**
	 * 分页查取综合方式排名的约请计划列表 
	 * @param paging 分页对象
	 * @return map结构
	 * { 	MA_HEAD_ICON:"会员头像url",
	 *  	MA_GRADE:2(会员等级),
	 *  	TOURIST_NICKNAME:'妮称',
	 *  	约请计划其他各字段...
	 * 	}
	 */
	List<Map> queryTripplanList(Paging paging) throws Exception;
	

	/**
	 * 取得该邀约计划的所有伴游对象及对应的会员帐号对象,并将关系表"邀约计划伴游关注表(YAW_R_TRIPPLAN_ESCORT)"的
	 * 自荐信值设置"伴游对象.自荐信"字段,该对象是一次性使用,不能被执久化;
	 * @param tripplanId  邀约计划ID
	 * @return 所有关联到该邀约计划的伴游的map集合map结构：{
	 * 	member:MemberAccount对象
	 *  escort:EscortInfo对象
	 * }
	 */
	List<Map<String, Object>> getAllRecommend(int tripplanId) throws Exception;
	
	/**
	 * 简单查询邀约计划资料
	 * @param wantSex 要求性别
	 * @param destination 旅游目的地
	 * @param depart 出发地
	 */
	List<Tripplan> simpleSearch(char wantSex,String destinaion,String depart,Paging paging) throws Exception;
	
	/**
	 * 高级查询邀约计划
	 * 说明:将多个" 属性  = 值 " 纵向划分到三个集合中;
	 * @param propertyName Tripplan的有效属性名集
	 * @param opflags 条件比较操作符符
	 * @param values 对应属性名的值集
	 */
	List<Tripplan> advanceSearch(String[] propertyName,int[] opflags,Object[] values,Paging paging) throws Exception;
	
	/**
	 * 查取最新发布的6条约请计划.按时间倒序排
	 */
	List<Tripplan> queryNewPublish() throws Exception;
	
	/**
	 * 查询出首页排名综合排名前三的邀约计划
	 */
	List<Tripplan> queryHomePage3Line() throws Exception;
	
	/**
	 * 分页取得给定游客的所有(每页5条)约请计划,这个方面是给游客详情用的
	 * @param memberId 会员ID
	 * @param paging 分页对象
	 * @return
	 */
	List<Tripplan> getMemberTripplanList(String memberId,Paging page)throws Exception;
	
	
}
