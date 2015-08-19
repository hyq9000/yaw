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
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("tid", "1");
		parameter.put("recommend", "我是一只小小鸟，想要飞呀飞飞，却怎么也飞不高");
		postToUrl("recommendTo", parameter,"54A4318D6A243AD722D9756AE5A2DF8D");
	}

	@Test
	public void testCompleteBaseData() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("escortInfo.escortMid", "mng");
		parameter.put("escortInfo.escortJob", "2");
		parameter.put("escortInfo.escortLanguage", "中文,英文");		
		parameter.put("escortInfo.escortNickName", "测试帐号");
		parameter.put("escortInfo.escortName", "张小丰");
		parameter.put("escortInfo.escortLove", "足球,音乐,运动");
		parameter.put("escortInfo.escortLiveAddr", "武汉");
		parameter.put("escortInfo.escortBirthday", "1994-12-28");
		parameter.put("escortInfo.escortDriveYear", "2");
		postToUrl("completeBodyData", parameter,"4CBE61CD5189DED0F2508DF08364D0A2");
	}

	@Test
	public void testCompleteBodyData() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("escortInfo.escortMid", "mng");
		parameter.put("escortInfo.escortHeight", "170");
		parameter.put("escortInfo.escortWeight", "60");		
		parameter.put("escortInfo.escortBody", "2");
		parameter.put("escortInfo.escortImage", "2");
		parameter.put("escortInfo.escortFeel", "2");
		postToUrl("completeBodyData", parameter,"4CBE61CD5189DED0F2508DF08364D0A2");	
	}

	@Test
	public void testComplteEscortData() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("escortInfo.escortMid", "mng");
		parameter.put("escortInfo.escortType", "1");
		parameter.put("escortInfo.escortTryto", "长沙,武汉");		
		parameter.put("escortInfo.escortPrice", "3000");
		parameter.put("escortInfo.escortTripAddr", "长沙,武汉");
		parameter.put("escortInfo.escortExp", "1");
		parameter.put("escortInfo.escortAttractive", "有点美");
		parameter.put("escortInfo.escortRecommend", "自我推荐的赎回");
		postToUrl("complteEscortData", parameter,"4CBE61CD5189DED0F2508DF08364D0A2");
	}

	@Test
	public void testCompleteContactData() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("escortInfo.escortMid", "mng");
		parameter.put("escortInfo.escortPhone", "13378017836");
		parameter.put("email", "13378917838@189.com");		
		parameter.put("escortInfo.escortWechat", "12145878");
		parameter.put("escortInfo.escortQq", "21617198");
		postToUrl("completeContactData", parameter,"4CBE61CD5189DED0F2508DF08364D0A2");
	}

	@Test
	public void testApplyEscortClub() {
		toUrl("applyEscortClub", null,"F906AE2201C7F6FAFB043B577FD9CC83");
	}

}
