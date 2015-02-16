package com.yaw.service.impl;

import java.util.Date;

import com.common.dbutil.DaoHibernateImpl;
import com.yaw.business.ActionType;
import com.yaw.business.Points;
import com.yaw.entity.TagRecord;
import com.yaw.service.TagRecordService;

/**
 * 类型描述:会员被贴标服务接口实现
 * </br>创建时期: 2015年1月4日
 * @author hyq
 */
public class TagRecordServiceImpl extends DaoHibernateImpl<TagRecord> implements
		TagRecordService {

	@Override
	@Points(action=ActionType.POINTS_TAG,index=0)
	public void tagsToMember(String memberId, String taggedMemberId,
			String... tags) throws Exception{
		/*
		 * 将多个tag连成一个字符串,用","隔开"
		 */
		String tagStr="";
		for(int i=0;i<tags.length;tagStr+=tags[i++]+",");
		tagStr.substring(0,tagStr.length()-1);
		
		TagRecord tagRecord=new TagRecord();
		tagRecord.setTagContent(tagStr);
		tagRecord.setTagDate(new Date());
		tagRecord.setTagMid(memberId);
		tagRecord.setTagTagedMid(taggedMemberId);
		this.add(tagRecord);	
	}
}
