package com.yaw.action;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.common.web.HttpUtils;

public class OperatingManageActionTest {


	private static void toUrl(String fun,Map<String,String> parameters,String sessionId){
		sessionId=sessionId!=null&& !sessionId.equals("")?";jsessionid="+sessionId:"";
		HttpResponse response=HttpUtils.get("http://localhost:8080/yaw/act/mng!"+fun+".action"+sessionId,parameters);
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
	public void testLogin() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("name", "admin");
		parameter.put("pwd", "123456");
		toUrl("login", parameter,null);
	}

	@Test
	public void testLogout() {
		Map<String, String> parameter=new HashMap<String, String>();
		toUrl("logout", parameter,"EEDD5F478DC2398B11AF6F6AB697BD8C");
	}

	@Test
	public void testhandleUpgradeVip() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("mid", "admin");
		parameter.put("grade", "2");
		parameter.put("billId", "20150724102650270");
		toUrl("handleUpgradeVip", parameter,"99AC9D35B5DFFCDFBC03F52AB3546212");
	}

	@Test
	public void testHandleRechargeMoney() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("mid", "mng");
		parameter.put("billId", "20150821021926703");
		toUrl("handleRechargeMoney", parameter,"21135BB6B17D52C12728D0AFC059E424");
	}

	@Test
	public void testHandleOrder() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("mid", "admin");
		parameter.put("billId", "20150822045759709");
		toUrl("handleOrder", parameter,"6CFA2457993CCCC58824A6205E17C4B5");
	}

	
	@Test
	public void testHandleAuthentication() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("reason", "可以");
		parameter.put("ispass", "false");
		parameter.put("applyId", "2");
		toUrl("handleAuthentication", parameter,"EEDD5F478DC2398B11AF6F6AB697BD8C");
	}	
	
	@Test
	public void testHandleApplyEscortClub() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("reason", "ok");
		parameter.put("ispass", "true");
		parameter.put("applyId", "5");
		toUrl("handleApplyEscortClub", parameter,"149A3BF87E09C0A3CF544EBBABBD4A94");
	}

	@Test
	public void testQueryWaitforHandleUnpaiedOrderList() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("pn", "1");
		toUrl("queryWaitforHandleUnpaiedOrderList", parameter,"6EFDC6D345B693FDD023AB6352100B4F");
	}

	@Test
	public void testQueryWaitforHandlePaiedOrderList() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("pn", "1");
		toUrl("queryWaitforHandlePaiedOrderList", parameter,"6EFDC6D345B693FDD023AB6352100B4F");
	}

	@Test
	public void testStatisticsQueryMemeber() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("pn", "1");
		parameter.put("fn", "MA_LOGIN_NAME");
		parameter.put("op", "=");
		parameter.put("val1", "admin");	
		toUrl("statisticsQueryMemeber", parameter,"FE1DA684DC4B10806F73F8A2E7ADA501");
	}

}
