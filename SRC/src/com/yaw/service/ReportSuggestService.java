package com.yaw.service;

import java.util.List;
import java.util.Map;

import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.ReportSuggest;

/**
 * 类型描述:举报\建议服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface ReportSuggestService extends EntityService<ReportSuggest>{
	/**骗财骗物*/
	byte TYPE_CHEAT=0;
	/**不是本人*/
	byte TYPE_NO_SELF=1;
	/**年龄不符*/
	byte TYPE_AGE_FAKE=2;
	/**资料不实*/
	byte TYPE_DATA_FAKE=3;
	/** 建议意见 */
	byte TYPE_SUGGEST=4;
	
	/**未处理*/
	byte RESULT_NO_HANDLE=0;
	/**:已受理/已采纳*/
	byte RESULT_HANDLED=1;//
	/**忽略*/
	byte RESULT_IGNORE=2;//
	/**
	 * 举报会员,并获得积分计数
	 * @param memberId
	 * @param reportedMemberId
	 * @param content
	 */
	void report(String memberId,String reportedMemberId,String content,byte reportTypeCode)throws Exception;
	/**
	 * 提意见建议,并获得积分计数
	 * @param memberId
	 * @param content
	 */
	void suggest(String memberId,String content)throws Exception;
	
	/**
	 * 举报建议受理,分页查取待处理的举报及建议;
	 * @param paging
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> reportSuggestHandle(Paging paging) throws Exception;
	
}
