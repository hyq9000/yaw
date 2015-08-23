package com.yaw.action;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.common.web.HttpUtils;

public class MemberAccountActionTest {
	
	private static void toUrl(String fun,Map<String,String> parameters,String sessionId){
		sessionId=sessionId!=null?";jsessionid="+sessionId:"";
		HttpResponse response=HttpUtils.get("http://localhost:8080/yaw/act/member!"+fun+".action"+sessionId,parameters);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(response.getEntity());
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckMemberName() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("mn", "admin");
		HttpResponse response=HttpUtils.get("http://localhost:8080/yaw/act/member!checkMemberName.action",parameter);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testResetPassword() {

	}

	@Test
	public void testRetrievePasswordSendEmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckEmail() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("email", "admin@qq.com");
		HttpResponse response=HttpUtils.get("http://localhost:8080/yaw/act/member!checkEmail.action",parameter);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLogin() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("loginName", "admin");//小朋友
		parameter.put("pwd", "123456");
		HttpResponse response=HttpUtils.post("http://localhost:8080/yaw/act/member!login.action",parameter);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(response);
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLogout() {
		HttpResponse response=HttpUtils.post("http://localhost:8080/yaw/act/member!logout.action;jsessionid=81786F85A0EE3B6DF74708155D0AF71A",null);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(response);
			assertEquals("{\"code\":1}",rs);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLookforPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePassword() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("old", "123456");
		parameter.put("new", "111111");
		HttpResponse response=HttpUtils.post("http://localhost:8080/yaw/act/member!updatePassword.action;jsessionid=A0B1AE8599967C4F7EEB50B8431B4372",parameter);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPauseMakeFriend() {
		toUrl("pauseMakeFriend", null,"2696B89190A31C05D2C688BA76702669");
	}

	@Test
	public void testRegist() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("ln", "男人与女人");
		parameter.put("sex", "男");
		parameter.put("pwd", "123456");
		parameter.put("mt", "0");
		HttpResponse response=HttpUtils.post("http://localhost:8080/yaw/act/member!regist.action",parameter);
		try {
			String rs=EntityUtils.toString(response.getEntity());
			System.out.println(rs);
			assertEquals("{\"code\":1}",rs);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testYueaCoinConsume() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("sid", "1");
		parameter.put("billId", "20150822050101459");
		toUrl("yueaCoinConsume", parameter, "28A2A814DDEA1283224EFE02C8682F93");
	}

	@Test
	public void testApplyUpgradeVip() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("vg", "4");
		toUrl("applyUpgradeVip", parameter, "28A2A814DDEA1283224EFE02C8682F93");
	}

	@Test
	public void testApplyRechargeMoney() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("money", "1000");
		toUrl("applyRechargeMoney", parameter, "28A2A814DDEA1283224EFE02C8682F93");
	}
	@Test
	public void testaAplyIncrementService(){
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("sid", "3");
		toUrl("applyIncrementService", parameter, "9CBC5FC441F13028422B620B44F1E300");
	}
	

	@Test
	public void testApplyAuthentication() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("authType", "6");
		toUrl("applyAuthentication", parameter, "4758220586F17BC1F1D926B6AAA7CBEB");
	}

	@Test
	public void testSendEmailAuthenticationLink() {
		fail("Not yet implemented");
	}

	@Test
	public void testHandleEmailAuthentication() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendMobileAuthenticationCaptch() {
		fail("Not yet implemented");
	}

	@Test
	public void testHandleMobileAuthentication() {
		fail("Not yet implemented");
	}

}
