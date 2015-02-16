package com.yaw.service;

import com.common.utils.EntityService;
import com.yaw.entity.TagRecord;

/**
 * 会员贴标记录服务接口
 * 类型描述:
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface TagRecordService extends EntityService<TagRecord> {
	/**
	 *  给会员贴标,每一个贴标生成一行贴标记录;贴标可计积分
	 * @param memberId 贴标会员ID
	 * @param taggedMemberId 被贴标会员ID
	 * @param tag[] 多个标签值
	 */
	void tagsToMember(String memberId,String taggedMemberId,String... tag)throws Exception;
	
	
}
