package com.yaw.service.impl;
import java.util.List;
import java.util.Map;

import com.common.dbutil.Dao;
import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.common.utils.BusinessException;
import com.yaw.business.UnShelve;
import com.yaw.common.BusinessServiceImpl;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.service.EscortInfoService;
import com.yaw.service.RIncserviceMemberService;

/**
 * 类型描述: 伴游的基本资料服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class EscortInfoServiceImpl extends DaoHibernateImpl<EscortInfo> implements
		EscortInfoService {
	
	@Override
	public void setHeadPhoto(EscortInfo escortInfo,MemberAccount member,  String url) throws Exception {		
		/*
		 * 如果这是第一次设置形象照料,则修改资料完整度及完整详情 
		 */
		if(escortInfo.getEscortFacePic()==null){
			this.updateCompleted(escortInfo);		
		}	
		escortInfo.setEscortFacePic(url);
		super.update(escortInfo);

		/*
		 * 保存头像URl
		 */
		String headUrl=BusinessServiceImpl.generateHeadUrl(url);
		String sql="update YAW_MEMBER_ACCOUNT set MA_HEAD_ICON=? where MA_LOGIN_NAME=?";
		this.executeUpdate(sql, headUrl,member.getMaLoginName());
		//将生成的HEAD头像URL写到(同步）对象中去；
		member.setMaHeadIcon(headUrl);		
	}

	@Override
	public void update(EscortInfo escortInfo) throws Exception {
		if(escortInfo!=null || escortInfo.getEscortMid()!=null){
			super.update(escortInfo);
			updateCompleted(escortInfo);
		}		
	}
	
	
	/**
	 * 封装会员资料完整度更新逻辑,
	 * @param escortInfo 伴游对象
	 * @param member 会员对象
	 */
	private void updateCompleted(EscortInfo escortInfo) throws Exception {
		String sql="select * from YAW_MEMBER_ACCOUNT where MA_LOGIN_NAME=?";
		List<MemberAccount> list=this.executeQuery(sql, escortInfo.getEscortMid());
		if(list!=null && list.size()>0){
			/*
			 * 因MemberAccountService与EscortInfoService存在相互引用，帮在这里手工封装对象
			 */
			MemberAccount member=new MemberAccount();
			Map tmp=(Map)list.get(0);
			member.setMaEmail((String)tmp.get("MA_EMAIL"));	
			member.setMaCompletedInfo((Integer)tmp.get("MA_COMPLETED_INFO"));
			member.setMaCompletedPercent((Byte)tmp.get("MA_COMPLETED_PERCENT"));
			/*
			 * 资料完善度更新
			 */		
			int percent=BusinessServiceImpl.generateCompletedPercent(member, escortInfo);
			int info=BusinessServiceImpl.generateCompletedDetail(member, escortInfo);	
			
			if(percent!=member.getMaCompletedPercent() || info!=member.getMaCompletedInfo()){
				sql="update YAW_MEMBER_ACCOUNT set MA_COMPLETED_PERCENT=?,MA_COMPLETED_INFO=? where MA_LOGIN_NAME=?";
				this.executeUpdate(sql, percent,info,escortInfo.getEscortMid());
			}
		}
		
	}
	
	@Override
	public void auditApplyEscortClub(String memberId) throws Exception {
		EscortInfo escortInfo =super.getById(memberId);
		if(escortInfo==null)
			throw new BusinessException("找不到指定ID的伴游!");
		escortInfo.setEscortClubMember((byte)1);
		super.update(escortInfo);		
	}

	@Override
	@UnShelve(property="escortMid",type=EscortInfo.class)
	public List<EscortInfo> simpleSearch(char sex, String city, boolean isOnline,Paging paging)
			throws Exception {
		String ql="FROM EscortInfo WHERE escortSex=? AND escortLiveAddr=? AND escortMid in("
				+ " SELECT o.maLoginName FROM MemberAccount as o WHERE  o.maOnline=?) "
				+ " ORDER BY escortOrderWeight";		
		return super.query(ql, paging,sex+"",city,(byte)(isOnline?1:0));		
	}

	@Override
	@UnShelve(property="escortMid",type=EscortInfo.class)
	public List<EscortInfo> advanceSearch(String[] propertyNames, int[] opFlags,
			Object[] values,Paging paging) throws Exception {
		//为多个条件生成默认的逻辑与操作码放到数组中		
		if(propertyNames==null || propertyNames.length==0 
				|| values==null ||values.length==0 
				|| opFlags==null || opFlags.length==0){			
			throw new Exception("条件、参数、值个数不能为空!");
		}else{
			int [] conditionFlag=new int[propertyNames.length-1];
			for(int i=0;i<conditionFlag.length;conditionFlag[i++]=Dao.CONDITION_AND);
			
			if(propertyNames.length!=values.length || propertyNames.length!=opFlags.length){
				throw new Exception("查询条件名、条件值、操作符个数不彼配!");
			}else{
				String hql="FROM EscortInfo WHERE ";
				String where="";
				where+=propertyNames[0]+" = ? ";
				for(int i=0;i<opFlags.length;i++){
					where+="AND "+propertyNames[i+1]+"= ? ";
				}
				hql+=where+" ORDER BY escortOrderWeight DESC";		
				return super.query(hql, paging,values);
			}
		}
	}


	@Override
	@UnShelve(property="escortMid",type=EscortInfo.class)
	public EscortInfo findHomePageHeadline() throws Exception {
		String ql="FROM EscortInfo WHERE escortMid in(select o.rimMid from RIncserviceMember as o where rimIncserviceId=?) ";
		List<EscortInfo> list=super.query(ql, (int)RIncserviceMemberService.SERVICE_HOMEPAGE_LINE);
		return list!=null&& list.size()>0?list.get(0):null;
	}

	@Override
	@UnShelve(property="escortMid",type=EscortInfo.class)
	public List<EscortInfo> query8ByProperty(String propertyName,Object value ) throws Exception {		
		if(propertyName!=null && !propertyName.trim().equals("") && value!=null){
			String ql="FROM EscortInfo where "+propertyName+" =? ORDER BY escortOrderWeight desc limit 8";
			return  super.query(ql,value);
		}else
			throw new  BusinessException("属性名称或属性值不正确");		
	}

	@Override
	@UnShelve(property="escortMid",type=EscortInfo.class)
	public List<EscortInfo> queryByProperty(String propertyName,Object value , Paging paging)
			throws Exception {
		if(propertyName!=null && !propertyName.trim().equals("") && value!=null){
			String ql="FROM EscortInfo where "+propertyName+" =? ORDER BY escortOrderWeight desc";
			return  super.query(ql,paging,value);
		}else
			throw new  BusinessException("属性名称或属性值不正确");
	}
	
	@Override
	@UnShelve(property="escortMid",type=EscortInfo.class)
	public List<EscortInfo> query8() throws Exception {
		String ql="FROM EscortInfo  ORDER BY escortOrderWeight desc";
		return  super.query(ql,new Paging(8,1),null);
	}
	
	@Override
	public List<EscortInfo> getEscortInfoListByIds(String... memberIds)
			throws Exception {
		String ids="";
		for(int i=0;i<memberIds.length;i++,ids+="?"+",");
		ids=ids.substring(0,ids.length()-1);
		String ql="from EscortInfo where escortMid in("+ids+") order by escortMid";
		return this.query(ql, memberIds);
	}
	
}
