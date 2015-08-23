package com.yaw.service;

import java.util.List;
import java.util.Map;

import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.business.PointsActionType;
import com.yaw.business.Points;
import com.yaw.entity.MemberAccount;
/**
 * 类型描述:定义对会员帐号的一系统操作服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface MemberAccountService extends  EntityService<MemberAccount> {
	
	/**在线*/
	byte ONLINE=1;
	/**离线*/
	byte OFFLINE=0;	
	/**普通会员*/
	byte GRADE_GENERAL=0;
	/**蓝钻*/
	byte GRADE_BLUE_DIAMOND=IncrementServiceService.VIP_GRADE_BLUE_DIAMOND;
	/**黄钻*/
	byte GRADE_YELLOW_DIAMOND=IncrementServiceService.VIP_GRADE_YELLOW_DIAMOND;
	/**皇冠*/
	byte GRADE_IMPERIAL_CROWN=IncrementServiceService.VIP_GRADE_IMPERIAL_CROWN;
	
	/**游客*/
	byte TYPE_TOURIST=0;
	/**伴游*/
	byte TYPE_ESCORT=1;
	/**交友状态*/
	byte MAKEFRIEND_ON=1;
	byte MAKFRIEND_OFF=0;
	
	/**认证类型码:视频*/
	byte AUTHENTICATE_VIDO=3;
	/**认证类型码:邮箱*/
	byte AUTHENTICATE_EMAIL=1;
	/**认证类型码:手机*/
	byte AUTHENTICATE_PHONE=2;
	/**认证类型码:身份证*/
	byte AUTHENTICATE_IDENTITY=5;
	/**认证类型码:导游*/
	byte AUTHENTICATE_GUIDE=6;
	/**认证类型码:健康*/
	byte AUTHENTICATE_HEALTH=4;
	/**认证类型码:QQ*/
	byte AUTHENTICATE_QQ=7;
	/**认证类型码:WEIWIN*/
	byte AUTHENTICATE_WEIXIN=8;
	
	
	
	/*
	 * 10:游客蓝钻
	   11：伴游蓝钻
	   20：伴游黄钻
	   30：游客皇冠
	   31：伴游皇冠
	   4:     置顶
	   5：  首页头条
	   6：  公开联系方式
	   7：  查看联系方式
	  */
	/**
	 * 检测email是否已经注册
	 * @param email 
	 * @return true:已经注册了,false:还没有注册
	 */
	boolean checkEmai(String email)throws Exception;
	
	/**
	 * 检测用户名是否已经注册
	 * @param email 
	 * @return true:已经注册了,false:还没有注册
	 */
	boolean checkMemberName(String memberName)throws Exception;
	
	/**
	 * 登陆验证;如果验证成功,则返回完整帐号对象,完成以下工作
	 * 同时要计算活跃积分数;
	 * @param loginName
	 * @param password
	 * @return 
	 */
	MemberAccount login(String loginName,String password,
			String loginIp) throws Exception;
	
	/**
	 * 注销;
	 * @param member
	 */
	void logout(MemberAccount member) throws Exception;
	
	/**
	 * 找回密码:发一封邮件到指定的邮箱中去,邮箱内容是一个重置密码的链接;
	 * @param email
	 */
	void sendEmailForLookforPassword(String email) throws Exception;
	
	/**
	 * 如果原密码正确,则修改成新密码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	void updatePassword(MemberAccount member,String oldPassword,
			String newPassword)	throws Exception;
	/**
	 * 重置密码:如果给定的邮箱正确,且当前邮箱对应的帐号没有被锁定,则重置密码;
	 * @param email 
	 * @param newPassword
	 */
	void resetPassword(String email,String newPassword) throws Exception;
	
	/**
	 * 升级会员等级
	 * @param member 会员对象
	 * @param vipGrade 会员等级:可以是MemberAccountService.GRADE_开头的这几个常量值
	 */
	void upgradeVip(MemberAccount member,byte vipGrade) throws Exception;
	
	/**
	 * 给会员充值,一元人民币换成一个"约啊币";
	 * @param member 会员对象
	 * @param money 元
	 */
	void rechargeMoney(MemberAccount member,int money) throws Exception;
	
	/**
	 * 诚信验证,把认证状态更改一下;
	 * @param member 完整洁的会员对象
	 * @param authenticateType 认证类型码,可以是MemberAccountService.AUTHENTICATE_开头的常量值;
	 */
	void authentication(MemberAccount member,byte authenticateType) throws Exception;
	

	/**
	 * 暂停交友,该状态的会员会从面页下架
	 * @param member 
	 */
	void pauseMakeFriend(MemberAccount member) throws Exception;
	
	/**
	 * 注册会员
	 * @param loginName 登陆名
	 * @param password 密码
	 * @param Sex 性别
	 * @param memberType 可以是 TYPE_TOURIST,TYPE_ESCORT
	 * @return
	 */
	MemberAccount regist(String loginName,String password,char Sex,byte memberType,String ip)throws Exception;
	/**
	 * 约啊币支支付,支付成功后，将服务绑定给用户
	 * @param memberId 会员帐号
	 * @param billId  常量
	 */
	void yueaCoinConsume(String memberId,String billId)throws Exception;
	
	/**
	 * 查取最新注册的前topCount位会员,按注册时间倒序排序;
	 * @param topCount  前几名
	 * @param memberType 会员类型码  可以是MemberAccountService.TYPE_开头的常量值
	 * @return 
	 */
	public List<MemberAccount> queryNewRegist(int topCount,byte memberType) throws Exception;
	
	
	/**
	 * 统计查询管理员:支撑后台的对会员用户的管理操作;
	 * @param fieldName 要查询的表字段名
	 * @param opFlag sql操作符
	 * @param value 对应字段的值,如果是区间,则有两个值
	 * @return
	 * @throws Exception
	 */
	public List<Map> statisticsQueryMember(Paging paging,String fieldName,String opFlag,Object ... value)throws  Exception;
	
	
	/**
	 * 查取给定会员帐号的会员对象集,根据会员ID升序排序
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public List<MemberAccount> getMemberListByIds(String ... memberIds)throws  Exception;
	
	/**
	 * 保存指定会员的认证类型；
	 * @param member 会员对象
	 * @param type 认证类型码:MemberAccountService.AUTHENTICATE_开头的常量
	 */
	//public void saveAuthentication(MemberAccount member,byte type)throws  Exception;
	
	/**
	 * 查取所有交友状态为0的会员ID；
	 * @return 没有则返回null;
	 */
	public List getAllMakeFriendOffMemeberId()throws  Exception;
	
	
	/**
	 * 修改会员的排名权重值,权重值本应设计在yaw_member_account表中，但为了方便查询，
	 * 在对应的伴游表（yaw_escort_info)与(yaw_tourist_info)表中也冗余了一份；
	 * @param member 会员对象
	 */
	public void updateOrderWeigth(MemberAccount member)throws  Exception;
}
