package com.yaw.service;

import java.util.List;

import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;

/**
 * 类型描述: 伴游的基本资料服务,所有前台的查询结果中应该实现以下几点:
 * <li>根据综合排名权重来排名先后;
 * <li>只显示用户交友状态为1的资料;
 *  </br>Date 2014年12月17日 
 * @author hyq
 */
public interface EscortInfoService extends EntityService<EscortInfo> {

	int IMAGE_KA=0;//TODO:可爱 还有其他的,包括气质,到时候再酌商一下;
	
	/**
	 * 上传形象照:将已经上传了的图片的URL,赋值给该伴游的形象照字段;
	 * 并同步将该自动生成，保存会员的头像图标URL（head）属性；
	 * @param memberId
	 * @param url
	 */
	void setHeadPhoto(EscortInfo escortInfo,MemberAccount member, String url)throws Exception;
	 
	/** 
	 * 审批加入伴游俱乐部申请
	 * @param escortInfoId 伴游ID
	 * @throws Exception 如果用户没有作视频认证,抛出BussinessException;
	 */	  
	void auditApplyEscortClub(String memberId) throws Exception;
	
	
	/**
	 * 简单查询伴游资料 
	 * @param sex 性别
	 * @param city 居住城市
	 * @param isOnline 是否在线 true为在
	 */
	List<EscortInfo> simpleSearch(char sex,String city,boolean isOnline)throws Exception;
	
	/**
	 * 高级查询伴游
	 * 说明:将多个" 属性  = 值 " 纵向划分到三个集合中;
	 * @param propertyName Tripplan的有效属性名集
	 * @param opflags 条件比较操作符符
	 * @param values 对应属性名的值集
	 */
	List<EscortInfo>  advanceSearch(String[] propertyName,int[] opflags,Object[] values,Paging paging)throws Exception;

	/**
	 * 找取享受"头版头条"伴游对象
	 * @return
	 * @throws Exception
	 */
	EscortInfo findHomePageHeadline()throws Exception;
	
	
	/**
	 * 查取指定气质的综合排名前8的伴游
	 * @param imageType 形象类型,可以是XXX 常量
	 * @return
	 * @throws Exception
	 */
	
	List<EscortInfo> query8ByProperty(String propertyName,Object value )throws Exception;
	/**
	 * 分页查取指定气质的综合排名的所有伴游
	 * @param imageType 形象类型,可以是XXX 常量
	 * @param paging 分页对象
	 * @return
	 * @throws Exception
	 */
	List<EscortInfo> queryByProperty(String propertyName,Object value ,Paging paging )throws Exception;
	
	/**
	 * 查询综合排名前8的伴游
	 * @return
	 * @throws Exception
	 */
	List<EscortInfo> query8()throws Exception;
	
	/**
	 * 查取给定会员帐号的伴游对象集,,根据会员ID升序排序
	 * @param memberIds
	 * @return
	 * @throws Exception
	 */
	public List<EscortInfo> getEscortInfoListByIds(String ... memberIds)throws  Exception;
}
