package com.yaw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.business.PointsActionType;
import com.yaw.business.Points;
import com.yaw.entity.ReportSuggest;
import com.yaw.service.ReportSuggestService;

/**
 * 类型描述:举报建议服务实现
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class ReportSuggestServiceImpl extends DaoHibernateImpl<ReportSuggest>
		implements ReportSuggestService {
	
	@Override
	@Points(action=PointsActionType.POINTS_REPORT,index=0) 
	public void report(String memberId, String reportedMemberId, String content,byte reportTypeCode)throws Exception{
		ReportSuggest rs=new ReportSuggest();
		rs.setRsContent(content);
		rs.setRsDate(new Date());
		rs.setRsHandled(ReportSuggestService.RESULT_NO_HANDLE);
		rs.setRsType(reportTypeCode);
		rs.setRsBereportMid(reportedMemberId);
		rs.setRsReportMid(memberId);
		this.add(rs);
	}
	
	@Points(action=PointsActionType.POINTS_SUGGEST,index=0)
	@Override
	public void suggest(String memberId, String content)throws Exception {
		ReportSuggest rs=new ReportSuggest();
		rs.setRsContent(content);
		rs.setRsDate(new Date());
		rs.setRsHandled((byte)0);
		rs.setRsType(ReportSuggestService.TYPE_SUGGEST);
		rs.setRsReportMid(memberId);
		this.add(rs);			
	}

	@Override
	public List<Map<String, Object>> reportSuggestHandle(Paging paging)
			throws Exception {
		String sql="SELECT RS_REPORT_MID,RS_BEREPORT_MID,RS_DATE,MA_AUTHENTICATED,"
				+ "RS_TYPERS_CONTENT FROM yaw_member_account,yaw_report_suggest"
				+ "WHERE RS_HANDLED=0 AND RS_REPORT_MID=MA_LOGIN_NAME";
		return this.executeQuery(sql,paging);
		
	}
	

	
	
}
