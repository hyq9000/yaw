package com.yaw.service.impl;


import com.common.dbutil.DaoHibernateImpl;
import com.yaw.entity.PhotoThumbnail;
import com.yaw.service.PhotoThumbnailService;

/**
 * @deprecated 这个不用了;图片缩略采用方案:图片上传后,生成几种规格保存,在文件名后加上规格就行了;
 * 类型描述:缩略图服务实现
 * </br>创建时期: 2015年1月6日
 * @author hyq
 */
public class PhotoThumbnailServiceImpl extends DaoHibernateImpl<PhotoThumbnail> implements
		PhotoThumbnailService {

	@Override
	public String getDemensionThumnbnail(byte demensionType, int nativePicId)
			throws Exception {
		return null;
	}

	@Override
	public void generateThumbnail(byte demesionType, byte[] nativePic,
			String path) throws Exception {		
	}	

}
