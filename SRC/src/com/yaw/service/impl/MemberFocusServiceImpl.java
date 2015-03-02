package com.yaw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
		implements MemberFocusService {

	private Map<String, Integer> memberFocusCache;// 会员关注数缓存

	/**初始化缓存*/
	private void _init() throws Exception{
		if(memberFocusCache==null){
			List<Map<String,Integer>> list=this.executeQuery("select MA_LOGIN_NAME,maFocusCount from yaw_member_account");		
			for(Map<String,Integer> map : list){
				memberFocusCache.put(map.keySet().iterator().next(), map.values().iterator().next());
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
		Integer num=memberFocusCache.get(memberId);
		if(num==null)
			memberFocusCache.put(memberId, 1);
		memberFocusCache.put(memberId, num+1);
	}

	@Override
	public int getMemberFocusCount(String memberId) throws Exception {
		this._init();
		return memberFocusCache.get(memberId);
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
		String ql="from MemberFocus where focusBefocusMid=? and FOCUS_TYPE=? and "+tmp;		
		List<MemberFocus> list=focusMemberId==null?super.query(ql, befoucusMemberId,focusType)
				:super.query(ql, befoucusMemberId,focusType,focusMemberId);
		return list!=null && list.size()>0?list.get(0):null;
	}
	
	//TODO 到下半夜的时候将缓存数据刷到数据库中去
	public void flushToDB(){
		try {
			for(Map.Entry<String,Integer> en:memberFocusCache.entrySet()){
				this.executeUpdate("update yaw_member_account set maFocusCount=? where MA_LOGIN_NAME=?" , en.getValue(),en.getKey());
			}
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
		}
	}
	

}
