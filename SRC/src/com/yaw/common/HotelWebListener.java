package com.yaw.common;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils; 
import com.common.listeners.ApplicationListener;

public class HotelWebListener extends ApplicationListener{
	
	@Override
	public void doExtends(ServletContext arg0) {
		ApplicationContext ac=WebApplicationContextUtils.getWebApplicationContext(arg0);
		
	}

	@Override
	public void initSystemData() { 
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {		
		super.contextDestroyed(sce);	
	}


}
