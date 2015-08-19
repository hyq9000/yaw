package com.yaw.service;

import java.util.List;
import java.util.Map;

import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.Message;

/**
 * 类型描述:留言记录服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface MessageService extends EntityService<Message> {
	 
	/**
	 * 留言未回复
	 */
	byte STATUS_NO_REPLY=0;
	/**
	 * 留言已回复
	 */
	byte STATUS_REPPLY=1;
	/**
	 * 留言不理睬
	 */
	byte STATUS_IGNOORE=2;
	
	/**
	 * 我留言给她,计积分
	 * @param memberId 我的会员帐号
	 * @param toMemberId 她的会员帐号
	 * @param content 留言内容
	 * @throws Exception
	 */
	void messageTo(String memberId,String toMemberId,String content) throws Exception;
	
	/**
	 * 分页查取得指定会员的所有新留言,按时间倒序排序
	 * @param memberType 会员类型
	 * @param memberId 会员ID
	 * @param paging 分页对象
	 * @return
	 */
	List<Map> allNewMessage(byte memberType,String memberId,Paging paging)throws Exception;
	/**
	 * 分页查取得指定会员所有新回复,按时间倒序排序
	 * @param memberId 会员ID
	 * @param paging 分页对象
	 * @return
	 */
	List<Map> allNewReplyMessage(byte memberType,String memberId,Paging paging)throws Exception;
	/**
	 * 分页查取得指定会员的所有未回复留言,按时间倒序排序
	 * @param memberId 会员ID
	 * @param paging 分页对象
	 * @return
	 */
	List<Map> allUnreplyMessage(byte memberType,String memberId,Paging paging)throws Exception;
	/**
	 * 留言回复
	 * @param messageId 留言ID
	 * @param content 留言内容
	 */
	void replay(int messageId,String content)throws Exception;
	
	/**
	 * 读取我的新留言的数
	 * @param memberId 会员ID
	 */
	int getNewMessageCount(String memberId)throws Exception;
	
	/**
	 * 取得我的新回复的总数
	 * @param memberId 会员ID
	 */
	int getNewReplayCount(String memberId)throws Exception;
	
	/**
	 * 取我的未回复的留言数
	 * @param memberId 会员ID
	 * @return
	 */
	int getUnReplayCount(String memberId)throws Exception;	
	
	/**
	 * 不理睬该留言
	 * @param messageId 留言ID
	 */
	void ignore(int messageId)throws Exception;
	
	/**
	 * 当留言被不于以回复时，重新发起留言,改变当前留言状态，内容及时间
	 * @param messageId 留 言ID
	 * @param content 再留言新内容
	 */
	void reMessaged(int messageId,String content)throws Exception;
}
