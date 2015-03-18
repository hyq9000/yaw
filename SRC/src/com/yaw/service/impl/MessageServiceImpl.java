package com.yaw.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.business.ActionType;
import com.yaw.business.Points;
import com.common.web.WebUtils;
import com.yaw.entity.Message;
import com.yaw.service.MemberAccountService;
import com.yaw.service.MessageService;

/**
 * 
 * 类型描述:留言信息服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class MessageServiceImpl extends DaoHibernateImpl<Message> implements
		MessageService {

	@Override
	@Points(action=ActionType.POINTS_MESSAGE,index=0)
	public void messageTo(String memberId, String toMemberId, String content)
			throws Exception {
		Message msg=new Message();
		msg.setMsgContent(content);
		msg.setMsgIsSystem((byte)0);
		msg.setMsgMid(memberId);
		msg.setMsgStatus(MessageService.STATUS_NO_REPLY);
		msg.setMsgReplayMid(toMemberId);
		this.add(msg);		
	}

	@Override
	/*
	 * 该操作比较费用资源,上层调用尽量不要那么频繁;
	 * (non-Javadoc)
	 * @see com.yaw.service.MessageService#allNewMessage(byte, java.lang.String, com.common.dbutil.Paging)
	 */
	public List<Map> allNewMessage(byte memberType,String memberId, Paging paging)
			throws Exception {
		String ql="from Message where msgReplayMid=? and msgStatus=0  order by msgTime desc";
		List<Message> msgList= this.query(ql, paging,memberId);
		return getMessage(memberType, msgList,false);
	}

	/**
	 * 
	 * @param memberType 会员类型
	 * @param msgList 留言对象集合
	 * @param isReplay 是查回复
	 * @return
	 * @throws Exception
	 */
	private List<Map> getMessage(byte memberType, List<Message> msgList,boolean isReplay)
			throws Exception {
		String ql;
		//将多个会员号串成一个字符串,用","隔开;
		String mids="'";
		if(isReplay){
			for(int i=0;i<msgList.size();mids+=msgList.get(i++).getMsgMid()+"','");	
		}else{
			for(int i=0;i<msgList.size();mids+=msgList.get(i++).getMsgReplayMid()+"','");	
		}
		mids.substring(0,mids.length()-2);
		
		/*
		 * 查取留言会员的帐号相关信息
		 */
		ql="select MA_AUTHENTICATED,MA_GRADE,MA_ONLINE,MA_HEAD_ICON from YAW_MEMBER_ACCOUNT in ("+mids+")";
		List<Map> memberList=this.executeQuery(ql);
		/*
		 * 查取留言会员的基本相关信息;如果我是伴游,则查游客的基本信息,我是游客,则查伴游的基本信息;
		 */
		if(memberType==MemberAccountService.TYPE_TOURIST)
			ql="select ESCORT_NICK_NAME as c1,ESCORT_SEX as c2,ESCORT_LIVE_ADDR as c3,ESCORT_BIRTHDAY as c4 where ESCORT_MID in ("+mids+")";
		else
			ql="select TOURIST_NICKNAME as c1,TOURIST_SEX as c2,TOURIST_LIVE_ADDR as c3,TOURIST_BIRTHDAY as c4 where TOURIST_MID in ("+mids+")";		
		List<Map> basicInfoList=this.executeQuery(ql);
		
		/*
		 * 将多个查询结果按行组织成所需求格式的结构;最终将所有行放List中去
		 */
		List data=new ArrayList();
		for(int i=0;i<msgList.size();i++){
			Message msg=msgList.get(i);
			Map msgMap=WebUtils.generateMapData(new String[]{"content,time,status,replyTime,replyContent"},
					new Object[]{msg.getMsgContent(),msg.getMsgTime(),msg.getMsgStatus()});
			Map memberMap=WebUtils.generateMapData(new String[]{"MA_AUTHENTICATED","MA_GRADE",
					"MA_ONLINE","MA_HEAD_ICON","c1","c2","c3","c4"},
					new Object[]{memberList.get(i).get("MA_AUTHENTICATED"),
						memberList.get(i).get("MA_GRADE"),
						memberList.get(i).get("MA_ONLINE"),
						memberList.get(i).get("MA_HEAD_ICON"),
						basicInfoList.get(i).get("c1"), 
						basicInfoList.get(i).get("c2"),
						basicInfoList.get(i).get("c13"), 
						basicInfoList.get(i).get("c4")});	
			/*
			 * 将所有查询结果组织成所需求格式的map结构,并放到List中去 
			 */
			Map row=new HashMap();
			row.put("member",memberMap);
			row.put("msg", msgMap);
			data.add(row);			
		}		
		return data;
	}

	@Override
	public List<Map> allNewReplyMessage(byte memberType,String memberId, Paging paging)
			throws Exception {
		String ql="from Message where msgMid=? and msgStatus=1 order by msg_Reply_Time desc";
		List<Message> msgList= this.query(ql, paging,memberId);
		return getMessage(memberType, msgList,true);
	}

	@Override
	public List<Map> allUnreplyMessage(byte memberType,String memberId, Paging paging)
			throws Exception {
		String ql="from Message where msgMid=? and msgStatus=0 order by msg_Reply_Time desc";
		List<Message> msgList= this.query(ql, paging,memberId);
		return getMessage(memberType, msgList,true);
	}

	@Override
	public void replay(int messageId, String content) throws Exception {
		Message msg=super.getById(messageId);
		msg.setMsgReplyContent(content);
		msg.setMsgReplyTime(new Date());
		msg.setMsgStatus(MessageService.STATUS_REPPLY);
		super.update(msg);
	}

	@Override
	public int getNewMessageCount(String memberId) throws Exception {
		String sql="select count(*) from yaw_message where  MSG_MID=? and MSG_STATUS=1";
		List list=this.executeQuery(sql);
		return list!=null && list.size()>0 ? ((Number)list.get(0)).intValue():0;
	}

	@Override
	public int getNewReplayCount(String memberId) throws Exception {
		String sql="select count(*) from yaw_message where  msg_Mid=? and msg_Status=1 ";
		List list=this.executeQuery(sql);
		return list!=null && list.size()>0 ? ((Number)list.get(0)).intValue():0;
	}

	@Override
	public int getunReplayCount(String memberId) throws Exception {
		String sql="select count(*) from yaw_message where  msg_Mid=? and msg_Status=0";
		List list=this.executeQuery(sql);
		return list!=null && list.size()>0 ? ((Number)list.get(0)).intValue():0;
	}

	@Override
	public void ignore(int messageId) throws Exception {
		Message msg=super.getById(messageId);
		msg.setMsgReplyTime(new Date());
		msg.setMsgStatus(MessageService.STATUS_IGNOORE);
		super.update(msg);
	}

	@Override
	public void reMessaged(int messageId,String content) throws Exception {
		Message msg=super.getById(messageId);
		msg.setMsgReplyContent(content);
		msg.setMsgReplyTime(new Date());
		super.update(msg);
	}

}
