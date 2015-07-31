package com.yaw.action;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.common.web.HttpUtils;

public class EscortActionTest {
	private static void toUrl(String fun,Map<String,String> parameters,String sessionId){
		sessionId=sessionId!=null&& !sessionId.equals("")?";jsessionid="+sessionId:"";
		HttpResponse response=HttpUtils.get("http://localhost:8080/yaw/act/escort!"+fun+".action"+sessionId,parameters);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(response);
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void postToUrl(String fun,Map<String,String> parameters,String sessionId){
		sessionId=sessionId!=null&& !sessionId.equals("")?";jsessionid="+sessionId:"";
		HttpResponse response=HttpUtils.post("http://localhost:8080/yaw/act/escort!"+fun+".action"+sessionId,parameters);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(response);
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testRecommendTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompleteBaseData() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompleteBodyData() {
		fail("Not yet implemented");
	}

	@Test
	public void testComplteEscortData() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompleteContactData() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("escortInfo.escortMid", "admin");
		parameter.put("escortInfo.escortPhone", "13378017838");
		parameter.put("email", "13378917838@189.com");		
		//parameter.put("escortInfo.escortWechat", "12145878");
		parameter.put("escortInfo.escortQq", "21617198");
		postToUrl("completeContactData", parameter,"432F8D455CEF04A67D90A11BA442E484");
	}

	@Test
	public void testApplyEscortClub() {
		toUrl("applyEscortClub", null,"F906AE2201C7F6FAFB043B577FD9CC83");
	}

}
