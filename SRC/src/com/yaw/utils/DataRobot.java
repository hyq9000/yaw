package com.yaw.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.common.log.ExceptionLogger;
import com.common.utils.BusinessException;

/**
 * 爬取当前主流的成功能注册个人数据入库；
 * </br>时间：2015年9月14日
 * @author hyq
 */
public class DataRobot {
	
	public static void main(String[] args){
	
		List<String> users=new ArrayList<String>();
		String url="";
		for(int i=0;i<users.size();i++){
			String id=users.get(i);
			try {					
				Document doc = Jsoup.connect(url+id).get();
				String cont=doc.select("#").first().text();
				/*
				 * TODO:爬取每位用户相关属性的值
				 */
			
			} catch (IOException e) {
				ExceptionLogger.writeLog(ExceptionLogger.INFO, "用户["+id+"]不存在",e,DataRobot.class);
			}
		}
	}
}
