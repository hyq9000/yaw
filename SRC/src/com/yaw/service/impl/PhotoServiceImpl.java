package com.yaw.service.impl;

import java.io.Serializable;
import java.util.List;

import com.common.dbutil.Dao;
import com.common.dbutil.DaoHibernateImpl;
import com.common.dbutil.Paging;
import com.yaw.entity.EscortInfo;
import com.yaw.entity.MemberAccount;
import com.yaw.entity.Photo;
import com.yaw.service.MemberAccountService;
import com.yaw.service.PhotoService;

/**
 * 会员相片服务实现类
 * </br>Date 2014年12月17日
 * @author hyq
 */
public class PhotoServiceImpl extends DaoHibernateImpl<Photo> implements
		PhotoService {
	private MemberAccountService memberAccountService;
	private EscortInfoServiceImpl escortInfoService;
	private TouristInfoServiceImpl touristInfoService;
	@Override
	public List<Photo> getPhotoList(String memberId, Paging paging)throws Exception {
		List<Photo> list=this.queryByProperty("photoMid",Dao.OP_EQUALS, memberId,paging) ;
		return list;
	}
	
	@Override
	/**
	 * 保存相片记录到库,并如果该会员是第一次上传相册,则修改会员用户的信息完善详情及信息完善百分比;
	 * @param photo 相册对象,除主键ID值不设置,其他值需要完整;
	 * @return
	 */
	public void add(Photo photo) throws Exception {
		//新增相片到库
		super.add(photo);		
	
		//当用户传相片到相册时,修改信息完整度情况;
		switch(photo.getPhotoType()){
			case PhotoService.TYPE_ART:
			case PhotoService.TYPE_LIVE:
			case PhotoService.TYPE_NORMAL:
			case PhotoService.TYPE_SIMPLEFACE:{				
				String memberId=photo.getPhotoMid();	
				MemberAccount member=memberAccountService.getById(memberId);
				int	completedDetail =member.getMaCompletedInfo();
				byte completedPercent=member.getMaCompletedPercent();
				//改变当前会员的信息完整度值:看BusinessServiceImpl.generateCompletedDetail及generateCompletedPercent
				completedDetail=completedDetail | 0x00002000;
				completedPercent+=10;//个人相册
				member.setMaCompletedInfo(completedDetail);
				member.setMaCompletedPercent(completedPercent);
				memberAccountService.update(member);	
				break;
			}
		}			
	}

	public void setMemberAccountService(MemberAccountService memberAccountService) {
		this.memberAccountService = memberAccountService;
	}

	public void setEscortInfoService(EscortInfoServiceImpl escortInfoService) {
		this.escortInfoService = escortInfoService;
	}

	public void setTouristInfoService(TouristInfoServiceImpl touristInfoService) {
		this.touristInfoService = touristInfoService;
	}

	
	
	
}
