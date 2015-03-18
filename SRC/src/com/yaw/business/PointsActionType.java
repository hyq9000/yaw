package com.yaw.business;

/**
 * 
 * 类型描述:能产生积分的动作类型
 * </br>创建时期: 2015年1月6日
 * @author hyq
 */
public enum PointsActionType {
	POINTS_LOGIN , //积分-如登陆(2)
	POINTS_PHOTO,//上传相片(5)
	POINTS_REPLAY,//回复(2)
	POINTS_REPORT,//举报有效(10)
	POINTS_SUGGEST,// 建议（5)
	POINTS_SUGGEST_REJECTED,//建议采纳（50）
	POINTS_BEFOCUS,//被关注(1)
	POINTS_BEFOCUS_PHOTO,//照片被关注（1)
	POINTS_MESSAGE,//留言(2)
	POINTS_PUBLISH_TRIPPLAN,//发布邀约计划(5)
	POINTS_TAG, //贴标(2)
	POINTS_RECOMMEND//自荐信
}

