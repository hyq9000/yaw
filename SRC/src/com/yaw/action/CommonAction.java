package com.yaw.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.common.dbutil.Paging;
import com.common.log.ExceptionLogger;
import com.common.web.Struts2Action;
import com.common.web.WebContextUtil;
import com.yaw.common.BusinessServiceImpl;
import com.yaw.common.WebUtils;
import com.yaw.entity.ApplyAuthentication;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Photo;
import com.yaw.entity.ReportSuggest;
import com.yaw.service.ApplyAuthenticationService;
import com.yaw.service.EscortInfoService;
import com.yaw.service.MemberAccountService;
import com.yaw.service.MessageService;
import com.yaw.service.OrderService;
import com.yaw.service.PhotoService;
import com.yaw.service.ReportSuggestService;
import com.yaw.service.TagRecordService;

/**
 * 类型描述:暴露的一些伴游,游客,管理员的公用的WebApi接口及实现
 * </br>创建时期: 2014年12月26日
 * @author hyq
 */
public class CommonAction extends Struts2Action {
	MemberAccountService memberAccountService;
	ReportSuggestService reportSuggestService;
	PhotoService photoService;
	EscortInfoService escortInfoService;
	TagRecordService tagRecordService;
	OrderService orderService;
	MessageService messageService;
	ApplyAuthenticationService applyAuthenticationService;

	
	private File image; //struts文件上传后临时文件名
	private String imageContextType;
	
	/*
	 * 提交视频/身份/健康/导游/qq/微信等人工认证申请,并及时通知后台处理;
	 * @param atype :认证类型1,2,3,4,5,6;ApplyAuthenticationService之TYPE开头的常量
	 
	public String submitAuthentication(){
		try {
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			String atype=request.getParameter("atype");
			byte authenticationType=Byte.parseByte(atype);
			applyAuthenticationService.submitAuthentication(user.getMaLoginName(), authenticationType);
			out.print(WebUtils.responseCode(1));
		} catch (NumberFormatException  ne){
			out.print(WebUtils.responseInputCheckError("认证类型码不正确!"));
		}catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}	
		return null;
	}*/
	
	/**
	 * 图片上传到服务器,并生成多个规格的图片保存，返回一个生成的URL,该方法与具体业务无关,只是把图片(按约定规格、格式)放到指定地方,
	 * 并在数据库Photo表生成一行数据
	 * @param image 上传后的临时文件路径
	 * @param imageFileName 文件名
	 * @param imageContentType 文件后辍名
	 * @param imageType 相片类型 (0,1,2,3,4,5,6,7)
	 * @param title 相片描述
	 * @return 返回该图存数据库后的URL
	 */
	private   String uploadPhoto_(String memberId) throws Exception{	
		/*
		 * 取得相关参数值
		 */
	
		String title=request.getParameter("title");
		String type=request.getParameter("imageType");		
		byte imageType=Byte.parseByte(type);
		
		/*
		 * 读取上传过来的临时文件,并写到系统上传文件夹中
		 */
		FileInputStream input=new FileInputStream(image);
		String path=request.getRealPath(application.getInitParameter("upload"));
		String fileName=System.currentTimeMillis()+"."+imageContextType;
		FileOutputStream fout=new FileOutputStream(path+"/"+fileName);
		byte[] buffer=new byte[1024*1024];
		while(input.read(buffer)!=-1);
		fout.write(buffer);
		fout.flush();
		fout.close();
		input.close();
		
		/*
		 * TODO：生成多个规则的图文件，按命名规则保存到硬盘
		 */
		
		/*
		 * 写相片数据行,并设置会员相关值(信息完整度);
		 */
		Photo photo=new Photo();
		photo.setPhotoFocusCount(0);
		photo.setPhotoType(imageType);
		photo.setPhotoFormat(imageContextType);
		photo.setPhotoMid(memberId);
		photo.setPhotoTitle(title);
		photo.setPhotoUrl(application.getInitParameter("upload")+"/"+fileName);
		photo.setPhotoStatus(PhotoService.STATUS_UNCERTIFICATED);
		photoService.add(photo);	
		
		return photo.getPhotoUrl();
	}
	
	
	/**
	 * 伴游上传相片到个人相册
	 * @title 相片标题
	 * @return {code:1,data:{url:"url"}}
	 */
	public String uploadPhoto(){
		try {
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			String url=uploadPhoto_(user.getMaLoginName());
			//响应请求
			out.print(WebUtils.responseData(WebUtils.generateMapData("url", url)));
		}catch (NumberFormatException  ne){
			out.print(WebUtils.responseError("相片类型码不正确!",-4));
		}catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}		
		return null;
	}
	
	
	
	
	/**
	 * 上传形象照:将已经上传了的图片的URL,赋值给该伴游的形象照字段;
	 * @param image 上传后的临时文件路径
	 * @param imageFileName 文件名
	 * @param imageContentType 文件后辍名
	 * @param imageType 相片类型
	 * @param imageTitle 相片描述
	 * @return {code:1,data:{url:"url"}}
	 */
	public String setHeadPhoto(){		
		try {
			EscortInfo escortInfo=(EscortInfo)session.getAttribute(MemberAccountAction.SESSION_KEY_BASIC_INFO);
			String url=uploadPhoto_(escortInfo.getEscortMid());
			escortInfoService.setHeadPhoto(escortInfo, url);
			//响应请求
			out.print(WebUtils.responseData(WebUtils.generateMapData("url", url)));
		}catch (NumberFormatException  ne){
			out.print(WebUtils.responseError("相片类型码不正确!",-4));
		}catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}		
		return null;
	}
	

	/**
	 * 举报会员,并获得积分计数
	 * @param rmid 被举报会员号
	 * @param rt 举报类型码
	 * @param content 举报内容
	 * @return {code:1}
	 */
	public String  report(){
		//http://localhost:8088/yaw/action/common!report.action?rmid=1&content=12&rt=1
		String rmid=request.getParameter("rmid");
		String content=request.getParameter("content");
		String rt=request.getParameter("rt");
		try {
			/*保存举报到库*/
			byte reportType=Byte.parseByte(rt);
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			reportSuggestService.report(user.getMaLoginName(), rmid, content,reportType);			
			WebUtils.responseCode(1);
		} catch (NumberFormatException e) {
			WebUtils.responseError("举报类型不正确", -3);
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			WebUtils.responseServerException();
		}
		return null;
	}
	/**
	 * 提意见建议,并获得积分计数
	 * @param content 意见内容
	 * @return{code:1}
	 */
	public String suggest(){
		String content=request.getParameter("content");
		try {
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			reportSuggestService.suggest(user.getMaLoginName(), content);
			
			WebUtils.responseCode(1);
		} catch (NumberFormatException e) {
			WebUtils.responseInputCheckError("举报类型不正确");
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			WebUtils.responseServerException();
		}
		return null;
	}
	
	/**
	 * 给会员贴标评价,每一个贴标生成一行贴标记录; 贴标会有积分;
	 * @param mid 贴标会员ID
	 * @param taggedMemberId 被贴标会员ID
	 * @param tag[] 多个标签值
	 * @return {code:1}
	 */
	public String tagsToMember(){
		try {
			String mid=request.getParameter("mid");
			String[] tags=request.getParameterValues("tags");
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			tagRecordService.tagsToMember(user.getMaLoginName(), mid, tags);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			WebUtils.responseServerException();
		}
		return null;
	}
	
	
	
	/**
	 * 确认已经完成付款
	 * @param oid 订单流水号
	 * @param isOk 是否已经完成付款
	 * @return {code:1}
	 */
	public String comfirmPaied() {
		try {
			String orderId=request.getParameter("oid");
			String isOk =request.getParameter("isOk");
			boolean yn=Boolean.parseBoolean(isOk);
			orderService.comfirmPaied(orderId, yn);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			WebUtils.responseServerException();
		}
		return null;
	}
	
	/**
	 * 我留言给她
	 * @param toMid 她的会员帐号
	 * @param content 留言内容
	 * @throws Exception
	 */
	public String messageTo() {
		try {
			String toMid=request.getParameter("toMid");
			String content=request.getParameter("content");
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			
			messageService.messageTo(user.getMaLoginName(), toMid, content);
			out.print(WebUtils.responseCode(1));
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			WebUtils.responseServerException();
		}
		return null;
	}
	
	/**
	 * 分页查取得所有新留言,按时间倒序排序
	 * @param mid 会员ID
	 * @param pn 分页数
	 * @return {code:n,data:[
	 * 	{
	 * 		member:{
	 * 			maHeadIcon:"url",
	 * 			maLoginName:''
	 * 			maGrade
	 * 		},
	 * 		msg:{
	 * 			
	 * 		}
	 *  },...]
	 * }
	 */
	public String allNewMessage(){
		try {
			String mid=request.getParameter("mid");
			String pn=request.getParameter("pn");
			int pageNo=Integer.parseInt(pn);
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			List<Map> list=messageService.allNewMessage(user.getMaType(),mid, new Paging(6,pageNo));
			out.print(WebUtils.responseData(list!=null?list.size():0, list));
		} catch (NumberFormatException e) {
			WebUtils.responseInputCheckError("页号(pn)不是一个数字");
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());WebUtils.responseServerException();
		}
		return null;
	}
	/**
	 * 分页查取得所有新回复,按时间倒序排序
	 * @param memberId 会员ID
	 * @param paging 分页对象
	 * @return
	 */
	public String allNewReplyMessage(){
		try {
			String mid=request.getParameter("mid");
			String pn=request.getParameter("pn");
			int pageNo=Integer.parseInt(pn);
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			List<Map> list=messageService.allNewReplyMessage(user.getMaType(),mid, new Paging(6,pageNo));
			out.print(WebUtils.responseData(list!=null?list.size():0, list));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseInputCheckError("页号(pn)不是一个数字"));
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}
	/**
	 * 分页查取得所有未回复留言,按时间倒序排序
	 * @param memberId 会员ID
	 * @param paging 分页对象
	 * @return{code:1,data:[{}...]}
	 */
	public String allUnreplyMessage(){
		try {
			String mid=request.getParameter("mid");
			String pn=request.getParameter("pn");
			int pageNo=Integer.parseInt(pn);
			MemberAccount user=(MemberAccount)WebContextUtil.getIntstance(request).getCurrentUser(session);
			List<Map> list=messageService.allUnreplyMessage(user.getMaType(),mid, new Paging(6,pageNo));
			out.print(WebUtils.responseData(list!=null?list.size():0, list));
		} catch (NumberFormatException e) {
			WebUtils.responseInputCheckError("页号(pn)不是一个数字");
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}
	/**
	 * 留言回复
	 * @param msgid 留言ID
	 * @param content 留言内容
	 * @return {code:1|-3}
	 */
	public String replay(){
		try {
			String msgId=request.getParameter("msgid");
			int messageId=Integer.parseInt(msgId);
			String content=request.getParameter("content");
			messageService.replay(messageId, content);
			out.print(WebUtils.responseCode(1));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseError("留言ID不正确", -3));
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}
	
	/**
	 * 读取我的新留言的数
	 * @param mid 会员ID
	 * @return {code:n}
	 */
	public String getNewMessageCount(){
		try {
			String mid=request.getParameter("mid");
			int rs=messageService.getNewMessageCount(mid);
			out.print(WebUtils.responseCode(rs));
		}catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}
	
	/**
	 * 取我的新回复的总数
	 * @param memberId 会员ID
	 */
	public String getNewReplayCount(){
		try {
			String mid=request.getParameter("mid");
			int rs=messageService.getNewReplayCount(mid);
			out.print(WebUtils.responseCode(rs));
		}catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}
	
	/**
	 * 取我的未回复的留言数
	 * @param memberId 会员ID
	 * @return
	 */
	public String getunReplayCount(){
		try {
			String mid=request.getParameter("mid");
			int rs=messageService.getunReplayCount(mid);
			out.print(WebUtils.responseCode(rs));
		}catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}	
	
	/**
	 * 不理睬该留言
	 * @param msgid 留言ID
	 */
	public String ignore(){
		try {
			String msgid=request.getParameter("msgid");
			int messageId=Integer.parseInt(msgid);
			messageService.ignore(messageId);
			out.print(WebUtils.responseCode(1));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseError("留言ID不正确", -3));
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}
	
	/**
	 * 重新发起留言,改变当前留言状态及时间
	 * @param msgid 留 言ID
	 * @param content 再留言新内容
	 */
	public String reMessaged(){
		
		try {
			String msgid=request.getParameter("msgid");
			String content=request.getParameter("content");
			int messageId=Integer.parseInt(msgid);
			messageService.reMessaged(messageId, content);
			out.print(WebUtils.responseCode(1));
		} catch (NumberFormatException e) {
			out.print(WebUtils.responseError("留言ID不正确", -3));
		} catch (Exception e) {
			ExceptionLogger.writeLog(e, this);
			out.print(WebUtils.responseServerException());
		}
		return null;
	}

	
	

	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}


	public void setReportSuggestService(ReportSuggestService reportSuggestService) {
		this.reportSuggestService = reportSuggestService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}


	public void setEscortInfoService(EscortInfoService escortInfoService) {
		this.escortInfoService = escortInfoService;
	}


	public void setTagRecordService(TagRecordService tagRecordService) {
		this.tagRecordService = tagRecordService;
	}


	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}


	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}


	public void setImage(File image) {
		this.image = image;
	}


	public void setImageContextType(String imageContextType) {
		this.imageContextType = imageContextType;
	}
	
}
