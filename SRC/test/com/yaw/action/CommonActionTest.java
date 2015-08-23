package com.yaw.action;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CommonActionTest {

	@Test
	public void testSubmitAuthentication() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("atype", "3");
		Common.postToUrl("submitAuthentication", parameter,"7A04CF76AB37BDD26B647F49A37369A5","common");
	}

	@Test
	public void testUploadPhoto() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetHeadPhoto() {
		fail("Not yet implemented");
	}

	@Test
	public void testReport() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("rmid", "小朋友");
		parameter.put("content", "试一下举报可用否：）");
		parameter.put("rt", "0");
		Common.postToUrl("report", parameter,"7BB1237F437B55479289D2EF8B0C8D50","common");
	}

	@Test
	public void testSuggest() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("content", "试一下提建议可用否：）");
		Common.postToUrl("suggest", parameter,"7BB1237F437B55479289D2EF8B0C8D50","common");
	}

	@Test
	public void testTagsToMember() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("mid", "admin");
		parameter.put("tags", "小美女");
		Common.postToUrl("tagsToMember", parameter,"C5656F6B711918BF8247EED5AFA4CF49","common");
	}

	@Test
	public void testComfirmPaied() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("oid", "20150822045759709");
		parameter.put("isOk", "1");
		parameter.put("pm", "2");
		parameter.put("po", "中信业银行");
		Common.postToUrl("comfirmPaied", parameter,"28A2A814DDEA1283224EFE02C8682F93","common");
	}

	@Test
	public void testMessageTo() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("toMid", "小朋友");
		parameter.put("content", "听说你要出去了？！:)");
		Common.postToUrl("messageTo", parameter,"04AC1DBA3609BFD611F8B6DBB49C9809","common");
	}

	@Test
	public void testAllNewMessage() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("pn", "1");
		Common.postToUrl("allNewMessage", parameter,"3CE4A64810C2B32D74ECD6B68F2BDCDB","common");
	}

	@Test
	public void testAllNewReplyMessage() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("pn", "1");
		Common.postToUrl("allNewReplyMessage", parameter,"415487016B7ABEA8732B724BAFF01797","common");
	}

	@Test
	public void testAllUnreplyMessage() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("pn", "1");
		Common.postToUrl("allUnreplyMessage", parameter,"415487016B7ABEA8732B724BAFF01797","common");
	}

	@Test
	public void testReplay() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("msgid", "1");
		parameter.put("content", "是的，怎么的，你也要一起去?");
		Common.postToUrl("replay", parameter,"3CE6682A9BA834F76558AD74199B2C06","common");
	}
	
	

	@Test
	public void testGetNewMessageCount() {		
		Common.postToUrl("getNewMessageCount", null,"E3382839CDD1A2A85351CC7A400F5EFD","common");
	}

	@Test
	public void testGetNewReplayCount() {
		Common.postToUrl("getNewReplayCount", null,"C406563B592D2E036AAF3B8031AD5FE1","common");
	}

	@Test
	public void testGetUnReplayCount() {
		Common.postToUrl("getUnReplayCount", null,"CA069B8C8E495DFAAF33EBA9F5A1114C","common");
	}

	@Test
	public void testIgnore() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("msgid", "2");
		Common.postToUrl("ignore", parameter,"34994EFD672FFD79A9F310BB1D5F3AFE","common");
	}

	@Test
	public void testReMessaged() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("msgid", "2");
		parameter.put("content", "再试一下看看");
		Common.postToUrl("reMessaged", parameter,"34994EFD672FFD79A9F310BB1D5F3AFE","common");
	}

}
