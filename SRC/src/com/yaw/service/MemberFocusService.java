package com.yaw.service;

import java.util.Map;

import com.common.dbutil.Dao;
import com.common.utils.EntityService;
import com.yaw.entity.MemberFocus;

/**
 * 
 * 类型描述:会员.照片,约请计划关注详情服务接口,
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface MemberFocusService extends EntityService<MemberFocus> {
	/**关注会员*/
	byte FOCUS_TYPE_MEMBER=0;
	/**关注照片*/
	byte FOCUS_TYPE_PHOTO=1;
	/**关注约请计划 */
	byte FOCUS_TYPE_TRIPPLAN=2;
	
	/**
	 * 取得指定会员的关注总数,这个操作被调用频率会很高,所以该会员关注数需要缓存;
	 * @param memberId 会员id
	 * @return 
	 */
	int getMemberFocusCount(String memberId) throws Exception;
	
	/**
	 * 关注人与被关注人不是同一人时,更新关注数及流水记录生成关注流水记录
	 * @param focusMemberId 关注会员ID
	 * @param befocusMemberId 被关注会员ID
	 * @param focusType 关注类型
	 * @throws Exception
	 */
	void gernateFocusRecord(String focusMemberId,String befocusMemberId,byte focusType) throws Exception;
	
		
	/*
	 * 取得指定关注与被关注会员的关注记录
	 * @param befoucusMemberId 被关注会员的ID
	 * @param focusMemberId 关注会员ID 如果为null，则意为匿名；
	 * @return 有则返回对象，否则返回null
	 
	MemberFocus getMemberFocusByBefoucusMemberId(String befoucusMemberId,String focusMemberid) throws Exception;*/
}
