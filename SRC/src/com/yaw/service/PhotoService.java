package com.yaw.service;

import java.util.List;

import com.common.dbutil.Dao;
import com.common.dbutil.Paging;
import com.common.utils.EntityService;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Photo;

/**
 * 会员相片服务接口
 * </br>Date 2014年12月17日
 * @author hyq
 */
public interface PhotoService extends EntityService<Photo> {
	/**原图规格*/
	byte DEMENSION_NATIVED=0;
	/**列表规格*/
	byte DEMENSION_LIST=1;
	/**头像规格*/
	byte DEMENSION_HEAD=2;
	
	/** 列表规格宽值*/
	int DEMENSION_LIST_WIDTH=222; //根据UI原型来确定的。
	/** 列表规格高值*/
	int DEMENSION_LIST_HEIGHT=138;
	
	/** 头像规格宽值*/
	int DEMENSION_HEAD_WIDTH=48;
	/** 头像规格高值*/
	int DEMENSION_HEAD_HEIGHT=48;
	/** 头像规格文件名后辍*/
	String DEMENSION_HEAD_SUFFIX="-HEAD";
	/** 列表规格文件名后辍*/
	String DEMENSION_LIST_SUFFIX="-LIST";
	
	/**普通照*/
	byte TYPE_NORMAL=0;
	/**:生活照*/
	byte TYPE_LIVE=1;
	/**::素颜照*/
	byte TYPE_SIMPLEFACE=2;
	/**::艺术照*/
	byte TYPE_ART=3;
	/**::身份证*/
	byte TYPE_ID=4;
	/**::健康证*/
	byte TYPE_HEALTH=5;
	/**::导游证*/
	byte TYPE_GUIDER=6;
	/**::形像照*/
	byte TYPE_IMAGE=7;
	
	/**照片已认证*/
	byte STATUS_CERTIFICATED=1;
	/**照片未认证*/
	byte STATUS_UNCERTIFICATED=0;
	/**
	 * 当未设置头像时，则系统采用的默认头像图文件名
	 */
	String DEFAULT_HEAD_IMAGE="/resources/images/head.png";
	/**
	 * 分页查看会员的列表规格尺寸的相片集合
	 * @param memberId 登陆ID
	 * @param paging 分页对象
	 * @return 返回指定页的Photo对象
	 */
	List<Photo> getPhotoList(String memberId,Paging paging)throws Exception;
	
}
