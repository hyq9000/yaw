package com.yaw.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类型描述:将一些非业务规划的系统公共功能抽象出来,封装到各方法中来
 * </br>创建时期: 2014年12月24日
 * @author hyq
 */
public class SystemServiceImpl {
	/**
	 * 定时执行扫描会员的"最近一次登陆IP所在地",如果为空,则根据"最近登陆IP"调用三方API,取得其所在城市位置,补善该字段的值
	 */
	public void scanLoginIp(){
		
	}
	/**
	 * 自动生成会员订购服务的订单号,格式:
	 * 产品(服务)代号-年月日时分秒毫秒(3位）如 LZ-20140908120945109
	 * @return 生成一个规则格式的订单号
	 */
	public static String generateOrderNo(){
		Date date=new Date();
		SimpleDateFormat formater=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String orderNo=formater.format(date);
		return orderNo;
	}
	
	/**
	 * 根据给定的原生图片,生成指定百分比的缩略图
	 * @param percent 百分之1-100
	 * @return 返回生成的缩略图文件;
	 */
	public static byte[] generatePictureThumb(int percent,byte[] nativerPicture){
		return null;
	}
	
}
