package com.yaw.service;

import com.common.utils.EntityService;
import com.yaw.entity.PhotoThumbnail;

/**
 * 图片缩略图服务接口
 * 类型描述:
 * </br>创建时期: 2015年1月6日
 * @author hyq
 */
public interface PhotoThumbnailService extends EntityService<PhotoThumbnail> {
	
	/**
	 * 取得原生图的指定规格的缩略图URL
	 * @param demensionType 规则类型码
	 * @param nativePicId 原生图ID
	 * @return url
	 */
	String getDemensionThumnbnail(byte demensionType,int nativePicId) throws Exception;
	
	/**
	 * 根据指定规格类型生成原生图的缩略图,并保存图片到指定(path)路径,最后保存该缩略图相关信息到数据库
	 * @param demesionType 规格类型码
	 * @param nativePic 原生图片
	 */
	void generateThumbnail(byte demesionType,byte[] nativePic,String path)throws Exception;

}
