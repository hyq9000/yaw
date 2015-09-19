package com.yaw.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.common.log.ExceptionLogger;
import com.common.utils.BusinessException;
import com.yaw.entity.MemberAccount;

/**
 * 爬取当前主流的成功能注册个人数据入库；
 * </br>时间：2015年9月14日
 * @author hyq
 */
public class DataRobot {
	
	public static void main(String[] args){	
		apanyou();
	}

	/*
	 * 一个网站的模板
	 */
	private static void apanyou() {
		List<String> users=new ArrayList<String>();
		String url="http://www.ftoul.com";
		for(;;){
			//String id=users.get(url);
			try {					
				Document doc = Jsoup.connect(url).get();
				String name=doc.select(".amount").first().html();
				/*TODO:爬取每位用户相关属性的值*/
				ExceptionLogger.writeLog(ExceptionLogger.INFO,name, null, DataRobot.class);
				/*TODO:保存入库*/
				MemberAccount member=new MemberAccount();
				member.setMaLoginName(name);
				//.....
				
				
				/*TODO：邮件通知 用户名及密码，提示常更新资料*/
			} catch (IOException e) {
				ExceptionLogger.writeLog(ExceptionLogger.INFO, "用户["+""+"]不存在",e,DataRobot.class);
			}
		}
	}
}
