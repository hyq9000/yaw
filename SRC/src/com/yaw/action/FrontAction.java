package com.yaw.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.common.web.WebUtils;
import com.common.dbutil.Paging;
import com.common.log.ExceptionLogger;
import com.common.web.Struts2Action;
import com.common.web.WebContextUtil;
import com.yaw.common.BusinessServiceImpl;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.MemberFocus;
import com.yaw.entity.Photo;
import com.yaw.entity.TouristInfo;
import com.yaw.entity.Tripplan;
import com.yaw.service.EscortInfoService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.MemberFocusService;
import com.yaw.service.PhotoService;
import com.yaw.service.TouristInfoService;
import com.yaw.service.TripplanService;

/** 
 * 类型描述:暴露出的未登陆前端业务功能的接口及实现
 * </br>创建时期: 2014年12月26日
 * @author hyq
 */
public class FrontAction extends Struts2Action{	
	private EscortInfoService escortInfoService; 
	private MemberAccountService memberService;
	private MemberFocusService focusService;
	private TouristInfoService touristInfoService;
	private TripplanService tripplanService;
	private PhotoService photoService;
	
	/**会话中存储当前登陆伴游对象的KEY*/
	private static final String SESSION_ESCORT_INFO="ESCORT_INF";
	
	/**
	 * 查看伴游的详情
	 * @param mid 会员id
	 * @return 返回json对象 
	 * {	
	 * 	code:1成功,-1服务器异常
	 * 	data:{
	 * 	  memberId:'会员帐号',
	 * 	  escort:{
	 * 		 String escortAttractive;
			 Date escortBirthday;
			 byte escortBody;
			 byte escortClubMember;
			 int escortDriveYear;
			 byte escortExp;
			 String escortFacePic;
			 byte escortFeel;
			 int escortHeight;
			 byte escortImage;
			 byte escortJob;
			 String escortLanguage;
			 String escortLiveAddr;
			 String escortLove;
			 String escortName;
			 String escortNickName;
			 String escortPhone;
			 int escortPrice;
			 String escortQq;
			 String escortRecommend;
			 String escortSex;
			 String escortTripAddr;
			 String escortTryto;
			 byte escortType;
			 String escortWechat;
			 byte escortWeight;
	 * 	  },
	 *    member:{  		 
			 byte maAuthenticated;
			 int maFocusCount;
			 int maGrade;
			 String maIpAddr;
			 String maLoginIp;
			 int maPoints;
			 byte maSincerity;
			 byte maOnline;
			 byte maStatus;
			 String maTags;
			 String maEmail;
			 byte maMfStatus=1;
			 int maCompletedInfo;
	 *    }
	 *  }
	 * }
	 */
	public String getEscortInfoDetail(){
		String mid=request.getParameter("mid");
		if(mid!=null && !mid.trim().equals("")){
			try {
				MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(request.getSession());
				String userId=user==null?null:user.getMaLoginName();
				MemberAccount befocusMember=memberService.getById(mid);				
				if(befocusMember==null)
					return null;

				EscortInfo escortInfo=escortInfoService.getById(mid);
				if(escortInfo==null)
					return null;
				/*
				 * 不是自己看自己详情情况下,会员关注度加1 
				 */
				if(!mid.equals(user.getMaLoginName())){
					//生成关注流水
					focusService.gernateFocusRecord(userId, befocusMember.getMaLoginName(), MemberFocusService.FOCUS_TYPE_MEMBER);
				}
				Map memberMap=new HashMap();
				WebUtils.objectPutToMap(memberMap, befocusMember,"maAuthenticated","maFocusCount","maGrade",
						"maIpAddr", "maLoginIp","maPoints","maSincerity","maOnline","maStatus",
						"maTags","maEmail","maMfStatus=1","maCompletedInfo");
				Map escortMap=new HashMap();
				WebUtils.objectPutToMapEx(escortMap, escortInfo,"escortOrderWeight","escortClubMember","escortMid");
				Map data=new HashMap();
				data.put("account", befocusMember);
				data.put("escort", escortMap);
				data.put("memberId", befocusMember.getMaLoginName());				
				
				out.print(WebUtils.responseData(1, data));
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}else{
			out.println(WebUtils.responseInputCheckError("会员ID不正确!"));
		}
		return null;
	}
	
	/**
	 * 查看游客的详情
	 * @param mid 会员id
	 * @return 返回json对象 
	 * {	
	 * 	code:1成功,-1服务器异常
	 * 	data:{
	 * 		tourist:{
	 * 		 Date touristBirthday;
			 String touristImage;
			 byte touristLikeBody;
			 byte touristLikeFeel;
			 int touristLikeHeight;
			 byte touristLikeImage;
			 byte touristLikeWeight;
			 String touristLiveAddr;
			 String touristLove;
			 String touristMessage;
			 int touristMostPrice;
			 String touristName;
			 String touristNickname;
			 String touristPhone;
			 String touristQq;
			 String touristSex;
			 String touristTryto;
			 String touristWechat;
	 * 		},
	 *  	member:{
	 *  		byte maAuthenticated;
			 	int maFocusCount;
				 int maGrade;
				 String maIpAddr;
				 String maLoginIp;
				 int maPoints;
				 byte maSincerity;
				 byte maOnline;
				 byte maStatus;
				 String maTags;
				 String maEmail;
				 byte maMfStatus=1;
				 int maCompletedInfo;
	 *  	},
	 *  	tripplan:[{
	 *  	
	 *  	},...]
	 * }
	 */
	public String getTouristInfoDetail(){
		String mid=request.getParameter("mid");
		if(mid!=null && !mid.trim().equals("")){
			try {
				MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(request.getSession());
				String userId=user==null?null:user.getMaLoginName();
				MemberAccount befocusMember=memberService.getById(mid);	
				if(befocusMember==null)
					return null;

				TouristInfo touristInfo=touristInfoService.getById(mid);
				if(touristInfo==null)
					return null;
				/*
				 * 不是自己看自己详情情况下,会员关注度加1 
				 */
				if(!mid.equals(user.getMaLoginName())){
					//生成关注流水
					focusService.gernateFocusRecord(userId, befocusMember.getMaLoginName(), MemberFocusService.FOCUS_TYPE_MEMBER);
				}
				
				Map memberMap=new HashMap();
				WebUtils.objectPutToMap(memberMap, befocusMember,"maAuthenticated","maFocusCount","maGrade",
						"maIpAddr", "maLoginIp","maPoints","maSincerity","maOnline","maStatus",
						"maTags","maEmail","maMfStatus=1","maCompletedInfo");
				Map escortMap=new HashMap();
				WebUtils.objectPutToMapEx(escortMap, touristInfo,"touristOrderWeight","touristMid");
				Map data=new HashMap();
				data.put("member", befocusMember);
				data.put("tourist", escortMap);
				data.put("memberId", befocusMember.getMaLoginName());
				
				List<Tripplan> tripplanList=tripplanService.getMemberTripplanList(mid, new Paging(5,1));
				//TODO:简化约请计划前端显示的数据
				data.put("tripplan", tripplanList);
				out.print(WebUtils.responseData(1, data));
			} catch (Exception e) {
				long errorLogId=ExceptionLogger.writeLog(e, this);
				out.print(WebUtils.responseServerException(errorLogId));
			}
		}else{
			out.println(WebUtils.responseInputCheckError("会员ID不正确!"));
		}
		return null;
	}
	
	/**
	 * 分页查看会员的列表规格的相片集合
	 * @param mid 登陆ID
	 * @param pn 页数
	 * @param psize 每页行数
	 * @return 返回指定页的Photo对象集合{
	 * 	code:n数据行数,-1:服务器异常
	 *  data:[{},...]
	 * }
	 */
	public String getPhotoList(){
		String memberId=request.getParameter("mid");
		String pageNoStr=request.getParameter("pn");
		String pageSizeStr=request.getParameter("psize");
		int pageNo=Integer.parseInt(pageNoStr);
		int pageSize=Integer.parseInt(pageSizeStr);
		try {
			List<Photo> list=photoService.getPhotoList(memberId, new Paging(pageSize,pageNo));
			out.print(WebUtils.responseData(list.size(),list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	
	
	/**
	 * 简单查询伴游资料 
	 * @param sex 性别
	 * @param city 居住城市
	 * @param isOnline 是否在线 true为在
	 */
	public String escortSimpleSearch(){
		String sex=request.getParameter("sex");
		String city=request.getParameter("city");
		String online=request.getParameter("online");
		char csex=sex==null||sex.trim().equals("")?'女':sex.charAt(0);		
		boolean on=online!=null&& online.trim().equals("on")?true:false;
		try {
			List data=escortInfoService.simpleSearch(csex, city, on);
			out.print(WebUtils.responseData(data.size(), data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 高级查询伴游
	 * 说明:将多个" 属性  = 值 " 纵向划分到三个集合中;
	 * @param propertyName Tripplan的有效属性名集
	 * @param opflags 条件比较操作符符
	 * @param values 对应属性名的值集
	 */
	public String escortAdvanceSearch(){
		//TODO escortAdvanceSearch
		return null;
	}
	
	/**
	 * 分页查取指定城市的所有伴游对象的集合,该集合是经过权重算法排序了的;
	 * @param city
	 * @param paging 分页对象
	 */
	public String escortQueryByCity(){
		String city=request.getParameter("city");
		String pn=request.getParameter("pn");
		int pageNo=pn==null||pn.trim().equals("")?1:Integer.parseInt(pn);		
		try {
			List data=escortInfoService.queryByProperty("escortLiveAddr", city, new Paging(8, pageNo));
			out.print(WebUtils.responseData(data.size(), data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 查取指定城市的综合权重排名前8的伴游,这里边要排除享受"头版头条"的伴游,但包括城市置顶的;
	 * @param city 城市名
	 * @return
	 */
	public String  escortQuery8ByCity(){
		String city=request.getParameter("city");
		try {
			List data=escortInfoService.query8ByProperty("escortLiveAddr", city);
			out.print(WebUtils.responseData(data.size(), data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 找取享受"头版头条"伴游对象
	 * @return 
	 * {
	 * 	 	data:{photo:url,age:12,msg:'自荐信'} 		
	 * }
	 * @throws Exception
	 */
	public String escortFindHomePageHeadline(){
		try {
			EscortInfo escort=escortInfoService.findHomePageHeadline();
			Map data=new HashMap();
			data.put("facePic", escort.getEscortFacePic());
			data.put("age", new Date().getYear()-escort.getEscortBirthday().getYear());
			data.put("msg", escort.getEscortRecommend());
			out.print(WebUtils.responseData(1, data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 首页推荐8位排名前8的伴游
	 * @return
	 */
	public String escort8Recommend(){		
		try {
			List<EscortInfo> list=escortInfoService.query8();
			out.print(WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}	
		return null;
	}
	
	/**
	 * 查取最新注册的8位伴游;
	 */
	public String escortQuery8NewRegist(){
		try {
			List<MemberAccount> list=memberService.queryNewRegist(8,MemberAccountService.TYPE_ESCORT);
			out.print( WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print( WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 根据性别,分别查取综合排名的前8名
	 * @param sex
	 */
	public String escortQuery8BySex(){
		String sex=request.getParameter("sex");
		try {
			List list=escortInfoService.query8ByProperty("escortSex", sex);
			out.print(WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}	
	
	/**
	 * 分页查取指定性别的伴游对象;
	 * @param sex
	 */
	public String escortQueryBySex(){
		String sex=request.getParameter("sex");
		String pn=request.getParameter("pn");
		int pageNo=pn==null||pn.trim().equals("")?1:Integer.parseInt(pn);		
		try {
			List list=escortInfoService.queryByProperty("escortSex", sex, new Paging(8, pageNo));
			out.print(WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 查取指定形象的综合排名前8的伴游
	 * @param imageType 形象类型,可以是XXX 常量
	 * @return
	 * @throws Exception
	 */
	public String escortQuery8ByImage( ){
		String image=request.getParameter("image");
		try {
			List list=escortInfoService.query8ByProperty("escortImage", image);
			out.print(WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	/**
	 * 分页查取指定形象的综合排名的所有伴游
	 * @param imageType 形象类型,可以是XXX 常量
	 * @param paging 分页对象
	 * @return
	 * @throws Exception
	 */
	public String escortQueryByImage(){
		String image=request.getParameter("image");
		String pn=request.getParameter("pn");
		int pageNo=pn==null||pn.trim().equals("")?1:Integer.parseInt(pn);		
		try {
			List list=escortInfoService.queryByProperty("escortImage", image, new Paging(8, pageNo));
			out.print(WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	
	/**
	 * 查取指定气质的综合排名前8的伴游
	 * @param imageType 形象类型,可以是XXX 常量
	 * @return
	 * @throws Exception
	 */
	public String escortQuery8ByFeel(){
		String feel=request.getParameter("feel");
		try {
			List list=escortInfoService.query8ByProperty("escortFeel", feel);
			out.print(WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	/**
	 * 分页查取指定气质的综合排名的所有伴游
	 * @param imageType 形象类型,可以是XXX 常量
	 * @param paging 分页对象
	 * @return
	 * @throws Exception
	 */
	public String escortQueryByFeel( ){
		String feel=request.getParameter("feel");
		String pn=request.getParameter("pn");
		int pageNo=pn==null||pn.trim().equals("")?1:Integer.parseInt(pn);		
		try {
			List list=escortInfoService.queryByProperty("escortFeel", feel, new Paging(8, pageNo));
			out.print(WebUtils.responseData(list!=null ?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}	
	

	/**
	 * 查取最新注册的8位游客小资料;
	 * @return 返加一个 "昵称"为KEY,"头像规格形象照的URL"为值的MAP结构;
	 */
	public String query8NewRegist(){
		try {
			List list=memberService.queryNewRegist(8,MemberAccountService.TYPE_TOURIST);
			out.print(WebUtils.responseData(list!=null ?list.size():0,list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 *  查取最新注册的16位游客小资料;
	 *  retrun 返加一个 "昵称"为KEY,"头像规格形象照的URL"为值的MAP结构;
	 */
	public String query16NewRegist(){
		try {
			List list=memberService.queryNewRegist(16,MemberAccountService.TYPE_TOURIST);
			out.print(WebUtils.responseData(list!=null ?list.size():0,list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 推荐综合排名的前16名游客小资料
	 * @return 返加一个 "昵称"为KEY,"头像规格形象照的URL"为值的MAP结构;
	 */
	public String query16RecommendTourist(){
		try {
			List list= touristInfoService.queryTouristOrder(16);
			out.print(WebUtils.responseData(list!=null ?list.size():0,list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 游客详情中,分页(每页5行)查看该游客的所有邀约计划;
	 * @param memberId
	 * @return
	 */
	public String queryHisTripplanList(){
		String memberId=request.getParameter("mid");
		String pnstr=request.getParameter("pn");
		int pn=pnstr==null ||pnstr.trim().equals("")?1:Integer.parseInt(pnstr);
		try {
			List list=tripplanService.getMemberTripplanList(memberId, new Paging(5,pn));
			out.print(WebUtils.responseData(list!=null ?list.size():0,list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 查取综合排名前n名推荐游客
	 * @param n 前几名
	 * @return 返加一个 "昵称"为KEY,"头像规格形象照的URL"为值的MAP结构;
	 */
	public String  query16Tourist(){
		try {
			List<TouristInfo> list=touristInfoService.queryTouristOrder(16);
			List<Map> data=new ArrayList<Map>();
			//根据每个会员的形象照URL设置成对应的头像照URL；
			for(TouristInfo touristInfo : list){
				String imageUrl=touristInfo.getTouristImage();
				String headUrl="";
				if(imageUrl==null || imageUrl.trim().equals(""))
					headUrl=PhotoService.DEFAULT_HEAD_IMAGE;
				else{
					headUrl=BusinessServiceImpl.generateHeadUrl(imageUrl);
				}
				Map map=WebUtils.generateMapData(touristInfo.getTouristNickname(),headUrl);
				data.add(map);				
			}
			out.print(WebUtils.responseData(list!=null ?list.size():0,list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 约请计划中心:分页查取最新的综合方式排名的约请计划列表(第页8行)
	 * @param pn 分页号
	 * @return 返回如下格式的json{
	 * 	code:n,
	 *  data:[
	 *  { 	head:"会员头像url",
	 *  	grade:2(会员等级),
	 *  	nikeName:'妮称',
	 *  	约请计划其他各字段...
	 * 	},...]
	 * }
	 */
	public String queryTripplanList(){
		String pn=request.getParameter("pn");
		int pageNo=Integer.parseInt(pn);
		try {
			List<Map> list=tripplanService.queryTripplanList(new Paging(8, pageNo));
			out.print(WebUtils.responseData(list!=null?list.size():0, list));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	

	
	
	/**
	 * 简单查询邀约计划资料
	 * @param wantSex 要求性别
	 * @param destination 旅游目的地
	 * @param depart 出发地
	 */
	public String simpleSearchTripplan(){
		//TODO simpleSearchTripplan();
		return null;
	}
	
	/**
	 * 高级查询邀约计划
	 * 说明:将多个" 属性  = 值 " 纵向划分到三个集合中;
	 * @param propertyName Tripplan的有效属性名集
	 * @param opflags 条件比较操作符符
	 * @param values 对应属性名的值集
	 */
	public String advanceSearchTripplan(){
		//TODO :advanceSearchTripplan()
		return null;
	}
	
	/**
	 * 查取首页中最新发布的6条约请计划;
	 * @return 返回一个json对象,字段如下:
	 * {
	 * 	code:n
	 * 	data:[{
	 * 		tripplanTitle:'约请计划标题',
	 * 		tripplanPublishTime:'发布时间'},...]
	 * }
	 */
	public String queryNewPublishTripplan(){
		try{
			List<Tripplan> list=tripplanService.queryNewPublish();
			List data=new ArrayList();
			for(Tripplan tripplan :list){
				Map map=WebUtils.generateMapData(new String[]{"tripplanTitle","tripplanPublishTime"},
						new Object[]{tripplan.getTripplanTitle(),
						tripplan.getTripplanPublishTime().toLocaleString()}
				);
				data.add(map);
			}
			out.print(WebUtils.responseData(data!=null?data.size():0, data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 查询出首页右上角处排名综合排名前三的邀约计划
	 * @return 返回一个json对象,字段如下:
	 * {
	 * 	code:n
	 * 	data:[{
	 * 		tripplanTitle:'约请计划标题',
	 * 		tripplanPublishTime:'发布时间'},...]
	 */
	public String queryHomePage3Line(){
		try{
			List<Tripplan> list=tripplanService.queryHomePage3Line();
			List data=new ArrayList();
			for(Tripplan tripplan :list){
				Map map=WebUtils.generateMapData(new String[]{"tripplanTitle","tripplanPublishTime"},
						new Object[]{tripplan.getTripplanTitle(),
						tripplan.getTripplanPublishTime().toLocaleString()}
				);
				data.add(map);
			}
			out.print(WebUtils.responseData(data!=null?data.size():0, data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 分页取得给定游客的所有(每页5条)约请计划,这个结果是放游客详情处
	 * @param mid 会员ID 
	 * @param pn 分页号
	 * @return 返回一个json对象,字段如下:
	 * {
	 * 	code:n
	 * 	data:[{
	 * 		tripplanTitle:'约请计划标题',
	 * 		tripplanPublishTime:'发布时间',
	 * 		tripplanStatus:"状态"},...]
	 * }
	 */
	public String getMemberTripplanList(){
		String mid=request.getParameter("mid");
		String pn=request.getParameter("pn");
		int pageNo=Integer.parseInt(pn);
		try{
			List<Tripplan> list=tripplanService.getMemberTripplanList(mid,new Paging(5,pageNo));
			List data=new ArrayList();
			for(Tripplan tripplan :list){
				String statusText="";
				switch(tripplan.getTripplanStatus()){
					case 0:statusText="约请中";break;
					case 1:statusText="已完成";break;
					case 2:statusText="已过期";break;
					case 3:statusText="已取消";
				}
				Map map=WebUtils.generateMapData(new String[]{"tripplanTitle","tripplanPublishTime","tripplanStatus"},
						new Object[]{tripplan.getTripplanTitle(),
						tripplan.getTripplanPublishTime().toLocaleString(),
						statusText}
				);
				data.add(map);
			}
			out.print(WebUtils.responseData(data!=null?data.size():0, data));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 查看相片大图，该图的关注加1
	 * @param photoId 相片ID
	 * @return Photo对象的JSON
	 */
	public String getPhotoDetail(){
		try {
			String pid=request.getParameter("photoId");
			int photoId=Integer.parseInt(pid);
			Photo photo=photoService.getById(photoId);
			/*
			 * 关注加1
			 */
			photo.setPhotoFocusCount(photo.getPhotoFocusCount()+1);
			photoService.update(photo);
			
			out.print(WebUtils.responseData(photo));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("相片号数据不正确!"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	/**
	 * 查看约请计划详情，该约请计划关注加1；
	 * @param tid 约请计划ID
	 * @return
	 */
	public String getTripplanDetail(){
		try {
			String tid=request.getParameter("tid");
			int tripplanId=Integer.parseInt(tid);
			Tripplan tripplan=tripplanService.getById(tripplanId);
			/*
			 * 关注加1
			 */
			tripplan.setTripplanFocusCount(tripplan.getTripplanFocusCount()+1);
			tripplanService.update(tripplan);
			
			out.print(WebUtils.responseData(tripplan));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("约请计划号数据不正确!"));
		} catch (Exception e) {
			long errorLogId=ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException(errorLogId));
		}
		return null;
	}
	
	
	/**
	 * 根据给定的伴游帐号及对象,返回描述该伴游的关键词及关键词呈现方式;
	 * @param member 会员对象
	 * @param escortInfo 伴游对象
	 */
	private String getEscortKeyword(MemberAccount member,EscortInfo escortInfo){
		String sexV=escortInfo.getEscortSex(),//性别				
				addrV=escortInfo.getEscortLiveAddr(),//居住地
				attractiveV=escortInfo.getEscortAttractive(),//最迷人的是
				loveV=escortInfo.getEscortLove();//爱好
		byte  bodyV=escortInfo.getEscortBody(),//体型
				feelV=escortInfo.getEscortFeel(),//气质
				qzV=escortInfo.getEscortImage();//形象
		int ageV=escortInfo.getEscortBirthday().getYear(),	//生日年
			heightV=escortInfo.getEscortHeight(),//身高
			weightV=escortInfo.getEscortWeight();	//体重
		
		return "";
	}
	
	
	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}

	public void setMemberService(MemberAccountService memberService) {
		this.memberService = memberService;
	}

	public void setFocusService(MemberFocusService focusService) {
		this.focusService = focusService;
	}

	public void setTouristInfoService(TouristInfoService touristInfoService) {
		this.touristInfoService = touristInfoService;
	}

	public void setTripplanService(TripplanService tripplanService) {
		this.tripplanService = tripplanService;
	}	
	
}
