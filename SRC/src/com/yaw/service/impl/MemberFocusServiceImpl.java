package com.yaw.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.cache.CacheFlushBack;
import com.common.dbutil.DaoHibernateImpl;
import com.common.log.ExceptionLogger;
import com.yaw.entity.MemberFocus;
import com.yaw.service.MemberFocusService;

/**
 * 
 * 类型描述:会员被关注信息服务实现 </br>Date 2014年12月17日
 * 关注有会员关注,该关注会有一个关注关系表记录详情;
 * 还有照片,约请计划的关注;这两个关注数没有详情,只有最终总数,
 * 这些业务干脆都在写在这个类里算了;
 * @author hyq
 */
public class MemberFocusServiceImpl extends DaoHibernateImpl<MemberFocus>
		implements MemberFocusService,CacheFlushBack {

	/**
	 * 类型描述： 缓存用户关注数的数据结构	 * 
	 * </br>时间：2015年8月19日
	 * @author hyq
	 */
	class FucusValue{
		public int newValue; //未与数据库同步的新值
		public int oldValue; //已与数据库同步的值
		public FucusValue(int newValue,int oldValue){
			this.newValue=newValue;
			this.oldValue=oldValue;
		}
	}
	
	private static Map<String, FucusValue> memberFocusCache;// 会员关注数缓存

	/**初始化缓存*/
	private void _init() throws Exception{
		if(memberFocusCache==null){
			memberFocusCache=new HashMap<String, FucusValue>();
			List<Map<String,Integer>> list=this.executeQuery("select MA_LOGIN_NAME,MA_FOCUS_COUNT from yaw_member_account");		
			for(Map<String,Integer> map : list){
				memberFocusCache.put(map.keySet().iterator().next(), new FucusValue(map.values().iterator().next(),map.values().iterator().next()));
			}
		}
	}
	/**
	 * 递增关注数
	 * @param memberId
	 * @throws Exception
	 */
	private void _inc(String memberId) throws Exception{
		_init();
		FucusValue num=memberFocusCache.get(memberId);
		if(num==null){
			num=new FucusValue(1,1);
			memberFocusCache.put(memberId, num);
		}
		num.newValue++;
		memberFocusCache.put(memberId, num);
	}

	@Override
	public int getMemberFocusCount(String memberId) throws Exception {
		this._init();
		return memberFocusCache.get(memberId).newValue;
	}
	
	@Override
	public void gernateFocusRecord(String focusMemberId,String befocusMemberId,byte focusType) throws Exception {		
		//关注人与被关注人不是同一人时,更新关注数及流水记录
		if(!befocusMemberId.equals(focusMemberId)){
			_inc(befocusMemberId);
			
			MemberFocus focus=this.getMemberFocusByBefoucusMemberId(befocusMemberId,focusMemberId,focusType);
			if(focus==null){
				focus=new MemberFocus();
				focus.setFocusBefocusMid(befocusMemberId);						
				focus.setFocusCount(1);
				focus.setFocusMid(focusMemberId);
				focus.setFocusType(focusType);
			}else{					
				focus.setFocusCount(focus.getFocusCount()+1);
				focus.setFocusDate(new Date());
			}
			focus.setFocusDate(new Date());
			super.add(focus);
		}
	}

	private MemberFocus getMemberFocusByBefoucusMemberId(String befoucusMemberId,String focusMemberId,byte focusType)  throws Exception{
		String tmp=focusMemberId==null?"focusMid is null":"focusMid=?";
		String ql="from MemberFocus where focusBefocusMid=? and focusType=? and "+tmp;		
		List<MemberFocus> list=focusMemberId==null?super.query(ql, befoucusMemberId,focusType)
				:super.query(ql, befoucusMemberId,focusType,focusMemberId);
		return list!=null && list.size()>0?list.get(0):null;
	}
	
	@Override
	public int flushToDB(){
		//TODO:待优化，批量执行大量修改的SQL语句 
		try {
			int i=0;
			if(memberFocusCache!=null && memberFocusCache.size()>0){
				for(Map.Entry<String,FucusValue> en:memberFocusCache.entrySet()){	
					if(en.getValue().oldValue!=en.getValue().newValue){
						this.executeUpdate("update yaw_member_account set MA_FOCUS_COUNT=? where MA_LOGIN_NAME=?" , en.getValue().newValue,en.getKey());
						/*
						 * 更新后，设置同步了的值
						 */
						 en.getValue().oldValue=en.getValue().newValue;
						i++;
					}
				}
			}
			return i;
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			return 0;
		}
	}
	

}
