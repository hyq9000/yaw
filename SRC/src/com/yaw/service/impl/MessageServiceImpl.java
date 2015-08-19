package com.yaw.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.business.PointsActionType;
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
	@Points(action=PointsActionType.POINTS_MESSAGE,index=0)
	public void messageTo(String memberId, String toMemberId, String content)
			throws Exception {
		Message msg=new Message();
		msg.setMsgContent(content);
		msg.setMsgIsSystem((byte)0);
		msg.setMsgMid(memberId);
		msg.setMsgTime(new Date());
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
		if(msgList!=null && msgList.size()>0)
			return getMessage(memberType, msgList,false);
		else
			return null;
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
		String sql="";
		//将多个会员号串成一个字符串,用","隔开;
		String mids="'";
		if(isReplay){
			for(int i=0;i<msgList.size()-1;mids+=msgList.get(i++).getMsgReplayMid()+"','");
		}else{
			for(int i=0;i<msgList.size()-1;mids+=msgList.get(i++).getMsgMid()+"','");		
		}
		mids=mids.substring(0,mids.length()-2);
		
		/*
		 * 查取留言会员的帐号相关信息
		 */
		sql="select MA_AUTHENTICATED,MA_GRADE,MA_ONLINE,MA_HEAD_ICON from YAW_MEMBER_ACCOUNT  WHERE MA_LOGIN_NAME in ("+mids+")";
		List<Map> memberList=this.executeQuery(sql);
		/*
		 * 查取留言会员的基本相关信息;如果我是伴游,则查游客的基本信息,我是游客,则查伴游的基本信息;
		 */
		if(memberType==MemberAccountService.TYPE_TOURIST)
			sql="select ESCORT_NICK_NAME as nickName,ESCORT_SEX as sex,ESCORT_LIVE_ADDR as addr,ESCORT_BIRTHDAY as birthday FROM yaw_escort_info where ESCORT_MID in ("+mids+")";	
		else
			sql="select TOURIST_NICKNAME as nickName,TOURIST_SEX as sex,TOURIST_LIVE_ADDR as addr,TOURIST_BIRTHDAY as birthday FROM  yaw_tourist_info where TOURIST_MID in ("+mids+")";	
		List<Map> basicInfoList=this.executeQuery(sql);
		
		/*
		 * 将多个查询结果按行组织成所需求格式的结构;最终将所有行放List中去
		 */
		List data=new ArrayList();
		for(int i=0;i<msgList.size()-1;i++){
			Message msg=msgList.get(i);
			Map msgMap=WebUtils.generateMapData(new String[]{"content","time","status","replyTime","replyContent"},
					new Object[]{msg.getMsgContent(),
					msg.getMsgTime(),
					msg.getMsgStatus(),
					msg.getMsgReplyTime(),
					msg.getMsgReplyContent()});
			Map memberMap=WebUtils.generateMapData(new String[]{"authenticated","grade",
					"isOnline","headIcon","nickName","sex","addr","birthday"},
					new Object[]{
						memberList.get(i).get("MA_AUTHENTICATED"),
						memberList.get(i).get("MA_GRADE"),
						memberList.get(i).get("MA_ONLINE"),
						memberList.get(i).get("MA_HEAD_ICON"),
						basicInfoList.get(i).get("nickName"), 
						basicInfoList.get(i).get("sex"),
						basicInfoList.get(i).get("addr"), 
						basicInfoList.get(i).get("birthday")});	
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
		if(msgList!=null && msgList.size()>0)
			return getMessage(memberType, msgList,true);
		else 
			return null;
	}

	@Override
	public List<Map> allUnreplyMessage(byte memberType,String memberId, Paging paging)
			throws Exception {
		String ql="from Message where msgMid=? and msgStatus=0 order by msg_Reply_Time desc";
		List<Message> msgList= this.query(ql, paging,memberId);
		if(msgList!=null && msgList.size()>0)
			return getMessage(memberType, msgList,true);
		else
			return null;			
	}

	@Override
	@Points(action=PointsActionType.POINTS_REPLAY,index=0)
	public void replay(int messageId, String content) throws Exception {
		Message msg=super.getById(messageId);
		msg.setMsgReplyContent(content);
		msg.setMsgReplyTime(new Date());
		msg.setMsgStatus(MessageService.STATUS_REPPLY);
		super.update(msg);
	}

	@Override
	public int getNewMessageCount(String memberId) throws Exception {
		String sql="select count(*) as c from yaw_message where  MSG_REPLAY_MID=? and MSG_STATUS=0";
		List list=this.executeQuery(sql,memberId);
		if(list!=null && list.size()>0){
			Object c= ((Map)list.get(0)).get("c");
			return ((Number)c).intValue();
		}else
			return 0;
	}

	@Override
	public int getNewReplayCount(String memberId) throws Exception {
		String sql="select count(*) as c from yaw_message where  msg_Mid=? and msg_Status=1 ";
		List list=this.executeQuery(sql,memberId);
		if(list!=null && list.size()>0){
			Object c= ((Map)list.get(0)).get("c");
			return ((Number)c).intValue();
		}else
			return 0;
	}

	@Override
	public int getUnReplayCount(String memberId) throws Exception {
		String sql="select count(*) as c from yaw_message where  msg_Mid=? and msg_Status=0";
		List list=this.executeQuery(sql,memberId);
		if(list!=null && list.size()>0){
			Object c= ((Map)list.get(0)).get("c");
			return ((Number)c).intValue();
		}else
			return 0;
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
		msg.setMsgContent(content);
		msg.setMsgTime(new Date());
		msg.setMsgStatus(MessageService.STATUS_NO_REPLY);
		super.update(msg);
	}

}
