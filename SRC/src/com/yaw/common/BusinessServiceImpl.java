package com.yaw.common;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.TouristInfo;
import com.yaw.service.MemberAccountService;
import com.yaw.service.PhotoService;

/**
 * 类型描述:将系统中一些业务规则，如积分规则，完整度规则，读意度规则等业务规则抽象、提取出来, 封装到该类的方法中； 
 * </br>Date 2014年12月17日 
 * @author hyq
 */
public class BusinessServiceImpl {	
	
	/*积分动作类型
	public  final static int POINTS_LOGINs = 0, //积分-如登陆(2)
			POINTS_PHOTO = 1,//上传相片(5)
			POINTS_REPLAY=2,//回复(2)
			POINTS_REPORT=3,//举报有效(10)
			POINTS_SUGGEST=4,// 建议（5)
			POINTS_SUGGEST_REJECTED=5,//建议采纳（50）
			POINTS_BEFOCUS=6,//被关注(1)
			POINTS_BEFOCUS_PHOTO=7,//照片被关注（1)
			POINTS_MESSAGE=8,//留言(2)
			POINTS_PUBLISH_TRIPPLAN=9;//发布邀约计划(5)
	*/
	
	
	/**
	 * 会员的推广链接URL的前辍值;
	 */
	public final static String POPULARIZE_PRE_URL="http://www.yueaw.com/popularize/user?";
	
	/**
	 * 根据多个指标算法,生成会员的排名权重;当有增值服务,读信值,会员等级,积分值,资料完整度的值有更新时,
	 * 就同步更新其排名权重;增值服务不算在排名因子中;
	 * @param sincerity 诚信值
	 * @param grade 会员等级  MemberAccount.GRADE_开头的那几个常值
	 * @param poits 积分值        活跃度,是第一重要的
	 * @param completePercent 资料完善度
	 **/
	public static int generateOrderWeight(byte grade,int sincerity, int poits, int completePercent,boolean isOnline ) {
		/*
		 * 各指标的值按其权重,计算出最后的总排名权重值:
		 * 会员等级权重:5, 会员等级所:普通10,蓝钻50,黄钻:70,皇冠:100
		 * 诚意度权重:2,  诚意认证:email:30,手机50,视频:100,身份证认证:100,健康:100
		 * 积分值权重:2: 
		 * 资料完善度权重:1
		 * 在线否:+5分;
		 */
		int gradeValue=0;
		switch(grade){
			case MemberAccountService.GRADE_GENERAL:gradeValue= 10;break;
			case MemberAccountService.GRADE_BLUE_DIAMOND:gradeValue= 50;break;
			case MemberAccountService.GRADE_YELLOW_DIAMOND:gradeValue=70;break;
			case MemberAccountService.GRADE_IMPERIAL_CROWN:gradeValue= 100;break;
			default:break;
		}
		return (int)(gradeValue*0.5+sincerity*0.2+poits*0.2+completePercent*0.1+(isOnline?5:0));
	}

	/**
	 * 方法功能描述：根据用户的认证，更新用户的诚意指数值
	 * @param typeCode MemberAccountService.AUTHENTICATE_开头的值
	 * @param member 会员帐户对象
	 * @param basicInfo 可以是EscortInfo或TouristInfo对象
	 * @return 返回认证所对应的诚意指数
	 */
	public static int getSincerity(byte typeCode,MemberAccount member){
		/*
		 *  2手机验证：+50            
		 *  1邮箱验证：+30            
		 *  3视频认证：+100           
		 *  5身份验证：+100          
		 *  4健康认证：+100
		 *  6导游认证：+100
		 *  7qq认证：+50,
		 *  8微信认证：+50,
		 *  9俱乐部认证：+200
		 *  实现思路：如果未级验证过，则总分数减去对应的值；
		 */
		int tmp=780;
		boolean isAuthenticated=isAuthenticated(member, typeCode);
		switch(typeCode){
			case 1:
				if(!isAuthenticated)
					tmp-=30;
				break;
			case 2:
			case 7:
			case 8:
				if(isAuthenticated)
					tmp-=50;
				break;
			case 3:
			case 4:
			case 5:
			case 6:
				if(isAuthenticated)
					tmp-=100;
				break;
			case 9:
				if(isAuthenticated)
					tmp-=200;
				break;
		}
		return tmp;
	}
	
	/**
	 * 生成并返回指定会员所对应的认证码；
	 * @param member 会员对象
	 * @param type 认证类型码:MemberAccountService.AUTHENTICATE_开头的常量
	 */
	public static byte  generateAuthenticationCode(MemberAccount member, byte type) throws  Exception{
		byte authenticationStatus=member.getMaAuthenticated();
		byte newAuthenticationStatus=0;
		switch(type){
			case MemberAccountService.AUTHENTICATE_EMAIL:
				newAuthenticationStatus=(byte)(authenticationStatus|1);
				break;
			case MemberAccountService.AUTHENTICATE_GUIDE:
				newAuthenticationStatus=(byte)(authenticationStatus|32);
				break;
			case MemberAccountService.AUTHENTICATE_HEALTH:
				newAuthenticationStatus=(byte)(authenticationStatus|8);
				break;
			case MemberAccountService.AUTHENTICATE_IDENTITY:
				newAuthenticationStatus=(byte)(authenticationStatus|16);
				break;
			case MemberAccountService.AUTHENTICATE_PHONE:
				newAuthenticationStatus=(byte)(authenticationStatus|2);
				break;
			case MemberAccountService.AUTHENTICATE_VIDO:
				newAuthenticationStatus=(byte)(authenticationStatus|4);
				break;
			case MemberAccountService.AUTHENTICATE_QQ:
				newAuthenticationStatus=(byte)(authenticationStatus|64);
				break;
			case MemberAccountService.AUTHENTICATE_WEIXIN:
				newAuthenticationStatus=(byte)(authenticationStatus|128);
				break;
			default:return authenticationStatus;
		}
		return 	newAuthenticationStatus;
	}
	
	/**
	 * 返回该用户是否已经作了某项认证，
	 * @param member 会员对象
	 * @param type 认证类型码:MemberAccountService.AUTHENTICATE_开头的常量
	 * @return 已认证 true，未认证false
	 */
	public static boolean  isAuthenticated(MemberAccount member, byte type){
		byte authenticationStatus=member.getMaAuthenticated();		
		byte result= (byte)(authenticationStatus&type);
		return result==type;
	}
	
	/**
	 * 生成伴游会员基本信息详情,(在用户保存资料,上传相片时触发)
	 * @return 返回完成度的详情的整型值
	 */
	public static int generateCompletedDetail(MemberAccount member,EscortInfo escortInfo) {
		/* 
		 * 完成度详情是由一个32位int值的二进制来描述,每一位的0,1代表该项是否填写完整;
		 * 格式如下:
		 * 00000000 0nmlkji hgfedcba 87654321
		 * 1呢称，2生日，3性别，4居住地，5自荐信，6驾龄,7体型自评，8愿偿试交友类型、9个人爱好，a伴游类型、b伴游经验,c职业信息，d形象信息，e个人相册，f形象照、gQQ、h手机、i邮箱，j微信,k最吸引人特点,l愿去目的地,m语言能力,n姓名
		 */
		 int tmp=0,
			completedDetail=0,	//完善详情情况整形值
		 	completedPercent=0; //完善度
		 
		 completedDetail=completedDetail | (escortInfo.getEscortNickName()!=null?0x00000001:0);
		 completedDetail=completedDetail | (escortInfo.getEscortBirthday()!=null?0x00000002:0);
		 completedDetail=completedDetail | (escortInfo.getEscortSex()!=null?0x00000004:0);
		 completedDetail=completedDetail | (escortInfo.getEscortLiveAddr()!=null?0x00000008:0);
		 completedDetail=completedDetail | (escortInfo.getEscortRecommend()!=null?0x00000010:0);
		 completedDetail=completedDetail | (escortInfo.getEscortDriveYear()>0?0x000100020:0);
		 completedDetail=completedDetail | (escortInfo.getEscortBody()!=0?0x00000040:0);
		 completedDetail=completedDetail | (escortInfo.getEscortTryto()!=null?0x000000080:0); 
		 completedDetail=completedDetail | (escortInfo.getEscortLove()!=null?0x00000100:0);
		 completedDetail=completedDetail | (escortInfo.getEscortType()>0?0x000000200:0); 
		 completedDetail=completedDetail | (escortInfo.getEscortExp()>0?0x00000400:0);
		 completedDetail=completedDetail | (escortInfo.getEscortJob()>0?0x00000800:0); 
		 completedDetail=completedDetail | (escortInfo.getEscortImage()>0?0x00001000:0);
		 /*
		  * 个人相册只要上传过一次,则设置为已有相册,在这里就不做操作了;
		  * completedDetail=completedDetail | (escortInfo.getEscortNickName()!=null?0x00002000:0);
		  */
		 completedDetail=completedDetail | (escortInfo.getEscortFacePic()!=null?0x00004000:0);
		 completedDetail=completedDetail | (escortInfo.getEscortQq()!=null?0x00008000:0);
		 completedDetail=completedDetail | (escortInfo.getEscortPhone()!=null?0x00010000:0);
		 completedDetail=completedDetail | (member.getMaEmail()!=null?0x00020000:0);
		 completedDetail=completedDetail | (escortInfo.getEscortWechat()!=null?0x00040000:0);
		 completedDetail=completedDetail | (escortInfo.getEscortAttractive()!=null?0x00080000:0);
		 completedDetail=completedDetail | (escortInfo.getEscortTripAddr()!=null?0x00100000:0);
		 completedDetail=completedDetail | (escortInfo.getEscortLanguage()!=null?0x00200000:0);
		 completedDetail=completedDetail | (escortInfo.getEscortName()!=null?0x00400000:0);		
		return completedDetail;
	}
	
	
	/**
	 * 生成伴游会员基本信息详情的百分比值,(在用户保存资料,上传相片时触发)
	 * @return 返回完成度的详情的整型值
	 */
	public static int generateCompletedPercent(MemberAccount member,EscortInfo escortInfo) {
		
		 int tmp=0,
		 	completedPercent=0; //完善度
		 
		 /*
		  * 计算出基本资料完善度的值;总共100分
		  * 基本资料(20):呢称1，姓名1,生日5，性别2，居住地4,个人爱好1,职业信息4，拿驾照年号(1),语言能力(1)
		  * 自荐信(10):
		  * 伴游相关(15):愿意偿试(2)、伴游类型(5)、伴游经验(5),愿去地(3)
		  * 自我评价(20):形体自评（4),高(3),重(2),形象自评(3),气质自评(3)，最吸引人特征(5)
		  *	个人相册(10):每张2分,>=5满分;
		  * 形象照(10)、
		  * 联系方式:QQ(5)，手机(5)、邮箱(2),微信(3)
		  */		 
		 completedPercent+= escortInfo.getEscortNickName()!=null?1:0;
		 completedPercent+=escortInfo.getEscortBirthday()!=null?5:0;
		 completedPercent+=escortInfo.getEscortSex()!=null?3:0;
		 completedPercent+=escortInfo.getEscortLiveAddr()!=null?4:0;
		 completedPercent+=escortInfo.getEscortRecommend()!=null?10:0;
		 completedPercent+=escortInfo.getEscortDriveYear()>0?1:0;
		 completedPercent+=escortInfo.getEscortBody()!=0?5:0;
		 completedPercent+=escortInfo.getEscortTryto()!=null?2:0; 
		 completedPercent+=escortInfo.getEscortLove()!=null?1:0;
		 completedPercent+=escortInfo.getEscortType()>0?5:0; 
		 completedPercent+=escortInfo.getEscortExp()>0?5:0;
		 completedPercent+=escortInfo.getEscortJob()>0?4:0; 
		 completedPercent+=escortInfo.getEscortImage()>0?3:0;
		 /*
		  * 个人相册只要上传过一次,则设置为已有相册,在这里就不做操作了;
		  * completedPercent+=escortInfo.getEscortNickName()!=null?10:0;//个人相册
		  */
		 completedPercent+=escortInfo.getEscortFacePic()!=null?10:0;
		 completedPercent+=escortInfo.getEscortQq()!=null?5:0;
		 completedPercent+=escortInfo.getEscortPhone()!=null?5:0;
		 completedPercent+=member.getMaEmail()!=null?2:0;
		 completedPercent+=escortInfo.getEscortWechat()!=null?3:0;
		 completedPercent+=escortInfo.getEscortAttractive()!=null?5:0;
		 completedPercent+=escortInfo.getEscortTripAddr()!=null?3:0;
		 completedPercent+=escortInfo.getEscortLanguage()!=null?1:0;
		 completedPercent+=escortInfo.getEscortName()!=null?1:0;
		return completedPercent;
	}
	
	
	/**
	 * 生成游客会员基本信息完成度百分比,(在用户保存资料,上传相片时触发)
	 * @return 
	 */
	public static int generateCompletedPercent(MemberAccount member,TouristInfo touristInfo) {
		 /*
		  * 计算出基本资料完善度的值;总共100分
		  * 基本资料(20):呢称1，姓名1,生日5，性别2，居住地4,个人爱好1
		  * 心情寄语(10):
		  * 伴游偏好(35):愿意偿试(5)、喜欢体型(5),喜欢气质(5),喜欢形象(5),愿意出最高价(元/天)(5),希望高度(5),希望体重(5)
		  *	邀请计划(10):
		  * 形象照(10)、
		  * 联系方式:QQ(5)，手机(5)、邮箱(2),微信(3)
		  */	
		int completedPercent=0;
		 completedPercent+=touristInfo.getTouristNickname()!=null?1:0;
		 completedPercent+=touristInfo.getTouristBirthday()!=null?5:0;
		 completedPercent+=touristInfo.getTouristSex()!=null?2:0;
		 completedPercent+=touristInfo.getTouristLiveAddr()!=null?4:0;
		 completedPercent+=touristInfo.getTouristMessage()!=null?10:0;
		 completedPercent+=touristInfo.getTouristMessage()!=null?0x000100020:0;//邀约计划;
		 completedPercent+=touristInfo.getTouristQq()!=null?5:0;
		 completedPercent+=touristInfo.getTouristPhone()!=null?5:0; 
		 completedPercent+=member.getMaEmail()!=null?2:0;
		 completedPercent+=touristInfo.getTouristWechat()!=null?3:0; 
		 completedPercent+=touristInfo.getTouristImage()!=null?10:0;
		 completedPercent+=touristInfo.getTouristMostPrice()>0?5:0; 
		 completedPercent+=touristInfo.getTouristLikeBody()>0?5:0;
		 completedPercent+=touristInfo.getTouristLikeImage()>0?5:0;
		 completedPercent+=touristInfo.getTouristLikeFeel()>0?5:0;
		 completedPercent+=touristInfo.getTouristLove()!=null?5:0;
		 completedPercent=touristInfo.getTouristName()!=null?1:0;
		 completedPercent+=touristInfo.getTouristLikeHeight()>0?5:0;
		 completedPercent+=touristInfo.getTouristLikeWeight()>0?5:0;
		return completedPercent;
	}
	
	/**
	 * 生成游客会员基本信息完成度详情,(在用户保存资料,上传相片时触发)
	 * @return 
	 */
	public static int generateCompletedDetail(MemberAccount member,TouristInfo touristInfo) {
		/* 
		 * 完成度详情是由一个32位int值的二进制来描述,每一位的0,1代表该项是否填写完整;
		 * 格式如下:
		 * 00000000 0000000 hgfedcba 87654321
		 * 1呢称，2生日，3性别，4居住地，5心情寄语，6约请计划，7QQ、8手机、9邮箱，a微信,b形像照,c愿意给最高伴游费(元/天),d喜欢什么体型的伴游,e喜欢什么形象的伴游,f喜欢什么气质的伴游,h爱好,i姓名,j希望身高,k希望体重,l希望交往类型
		 */
		int completedDetail=0;
		 completedDetail=completedDetail | (touristInfo.getTouristNickname()!=null?0x00000001:0);
		 completedDetail=completedDetail | (touristInfo.getTouristBirthday()!=null?0x00000002:0);
		 completedDetail=completedDetail | (touristInfo.getTouristSex()!=null?0x00000004:0);
		 completedDetail=completedDetail | (touristInfo.getTouristLiveAddr()!=null?0x00000008:0);
		 completedDetail=completedDetail | (touristInfo.getTouristMessage()!=null?0x00000010:0);
		 completedDetail=completedDetail | (touristInfo.getTouristMessage()!=null?0x000100020:0);//邀约计划;
		 completedDetail=completedDetail | (touristInfo.getTouristQq()!=null?0x00000040:0);
		 completedDetail=completedDetail | (touristInfo.getTouristPhone()!=null?0x000000080:0); 
		 completedDetail=completedDetail | (member.getMaEmail()!=null?0x00000100:0);
		 completedDetail=completedDetail | (touristInfo.getTouristWechat()!=null?0x000000200:0); 
		 completedDetail=completedDetail | (touristInfo.getTouristImage()!=null?0x00000400:0);
		 completedDetail=completedDetail | (touristInfo.getTouristMostPrice()>0?0x00000800:0); 
		 completedDetail=completedDetail | (touristInfo.getTouristLikeBody()>0?0x00001000:0);
		 completedDetail=completedDetail | (touristInfo.getTouristLikeImage()>0?0x00002000:0);
		 completedDetail=completedDetail | (touristInfo.getTouristLikeFeel()>0?0x00004000:0);
		 completedDetail=completedDetail | (touristInfo.getTouristLove()!=null?0x00008000:0);
		 completedDetail=completedDetail | (touristInfo.getTouristName()!=null?0x00010000:0);	
		 completedDetail=completedDetail | (touristInfo.getTouristLikeHeight()>0?0x00020000:0);
		 completedDetail=completedDetail | (touristInfo.getTouristLikeWeight()>0?0x00040000:0);
		 completedDetail=completedDetail | (touristInfo.getTouristTryto()!=null?0x00080000:0);	
		return completedDetail;
	}
	
	
	
	/**
	 * 根据给定的会员对象，采用MD5算法，得出该会员的推广ID值,该值在用户注册成功后,就不会再变;
	 * @param member 会员对象
	 * @return 32位推广ID的值
	 */
	public static String getPopularizeMemberId(MemberAccount member){
		//会员登陆名及会员类型组合
		String str=DigestUtils.md5Hex(member.getMaLoginName()+member.getMaType()+ApplicationConfig.getInstance().getScretKey());
		return str;
	}
	
	/**
	 * 会员注册即送约啊币(10个)
	 */
	public static int getRegistPresentYacoin(){
		return 10;
	}
	
		
	/**
	 * 根据会员形象照的URL生成头像图标的URL：规则就是 "/xxxxx/xxxx/xxxxxx/adgabc.png"的字符串改成
     * "/xxxxx/xxxx/xxxxxx/adgabc-HEAD.png"格式
	 * @param imageUrl 形象照的URL；
	 * @return 
	 */
	public static String generateHeadUrl(String imageUrl){
		/*
		 * 将"/xxxxx/xxxx/xxxxxx/adgabc.png"的字符串改成
		 * "/xxxxx/xxxx/xxxxxx/adgabc-HEAD.png"格式
		 */
		int tmp=imageUrl.lastIndexOf(".");
		String pre=imageUrl.substring(0, tmp);
		String suf=imageUrl.substring(tmp);
		return pre+PhotoService.DEMENSION_HEAD_SUFFIX+suf;
	}
	
	
	
	/**
	 * 该操作应设计成"通知",再"织入"到原来业务处;
	 * 根据给定的已经按照料"排名权重"排序了的集合,再将享受"置顶"服务的会员拉到前面来;
	 * @param escortList 已经按照料"排名权重"排序的集合
	 * @param 置顶类型别 可以是 IncrementServiceService.TOP_CITY,IncrementServiceService.TOP_SEARCH,IncrementServiceService.TOP_CATEGORY
	 * @return "置顶"好了的集合
	 */
	List<EscortInfo> toppedList(List<EscortInfo> escortList,int topType) throws Exception{
		return null;
	}
	/**
	 * 业务数据需要缓存起来
	 * 取得所有正在享受"公开联系方式"服务的伴游集合
	 * @return
	 */
	List<EscortInfo> publicContact()throws Exception{
		return null;
	}
	
	/**
	 * (业务数据需要缓存)
	 * 获得正在享受"头版头条"服务的伴游集合
	 * @return
	 */
	List<EscortInfo> homepageHeadline(){
		return null;
	}

}
