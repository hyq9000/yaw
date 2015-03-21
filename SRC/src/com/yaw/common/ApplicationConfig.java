package com.yaw.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.common.log.ExceptionLogger;


/**
 * 平台业务参数配置类，是配置文件在内存的一个映射；
 * </br>时间：2015-3-15
 * @author hyq
 */
public class ApplicationConfig {
	private static ApplicationConfig _instance;
	private Properties pro=new Properties();
	
	private String scretKey;
	/**
	 * 构造时，将配置数据读取到对象；
	 */
	private ApplicationConfig(){
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("/config.properties");
		try {
			pro.load(in);
		} catch (IOException e) {
			ExceptionLogger.writeLog(ExceptionLogger.ERROR,"配置文件[/config.properties]未找到!",e,this.getClass());
		}
	}
	
	public static ApplicationConfig getInstance(){
		if(_instance==null)
			_instance=new ApplicationConfig();
		return _instance;
	}
	
	public String getScretKey(){
		return pro.getProperty("scret.key");
	}
	
	public static final String AUTHENTICATION_PHONE_TEXTssss="啊网,验证码:";
}
