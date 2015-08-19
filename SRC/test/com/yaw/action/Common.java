package com.yaw.action;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.common.web.HttpUtils;

public class Common {
	public static void toUrl(String fun,Map<String,String> parameters,String sessionId,String action){
		sessionId=sessionId!=null&& !sessionId.equals("")?";jsessionid="+sessionId:"";
		HttpResponse response=HttpUtils.get("http://localhost:8080/yaw/act/"+action+"!"+fun+".action"+sessionId,parameters);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(response);
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void postToUrl(String fun,Map<String,String> parameters,String sessionId,String action){
		sessionId=sessionId!=null&& !sessionId.equals("")?";jsessionid="+sessionId:"";
		HttpResponse response=HttpUtils.post("http://localhost:8080/yaw/act/"+action+"!"+fun+".action"+sessionId,parameters);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(response);
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
