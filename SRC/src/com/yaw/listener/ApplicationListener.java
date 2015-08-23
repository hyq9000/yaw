package com.yaw.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.common.cache.CacheFlushBack;
import com.common.log.ExceptionLogger;
import com.yaw.common.ApplicationConfig;
import com.yaw.service.MemberFocusService;

public class ApplicationListener implements ServletContextListener {
	
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		;
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*
		 * 读取任务定时时长(分钟)
		 */
		String intervalStr=ApplicationConfig.getInstance().getProperty("task.focus");
		int tmp=3600;
		if(intervalStr!=null){
			tmp=Integer.parseInt(intervalStr.trim());
		}
		final int interval=tmp;
		
		ApplicationContext ac=WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		final CacheFlushBack cb=ac.getBean("focusService", CacheFlushBack.class);
		
		/*
		 * 每隔interval秒，执行一次用户关注数 刷回到数据库的定时操作；
		 */
		new Thread(new Runnable() {			
			@Override
			public void run() {	
				while(true){
					try {
						Thread.currentThread().sleep(interval*1000*60);
					} catch (InterruptedException e) {;}
					
					int rs=cb.flushToDB();
					ExceptionLogger.writeLog(ExceptionLogger.INFO, "成功刷回数据库"+rs+"条数据", null, this.getClass());
				}
			}
		}).start();
	}

}
