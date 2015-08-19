package com.yaw.action;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TouristActionTest {

	@Test
	public void testGetAllRecommend() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("pid", "1");
		Common.postToUrl("getAllRecommend", parameter,"F07B80062969E2551C61CF01156C94AB","tourist");
	}
	
	
	@Test
	public void testPublishTripPlan(){
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("tp.escortMid", "爱吹NB的兔子");
		parameter.put("tp.tripplanDay", "3");
		parameter.put("tp.tripplanDepartAddr", "长沙1");		
		parameter.put("tp.tripplanDepartTime", "2015-8-22");
		parameter.put("tp.tripplanDestination", "北京");
		parameter.put("tp.tripplanMoneyModel", "2");
		parameter.put("tp.tripplanOrderWeight", "175");
		parameter.put("tp.tripplanOther", "无不良嗜好");
		parameter.put("tp.tripplanTitle", "北京三日AA游6");
		parameter.put("tp.tripplanType", "2");
		parameter.put("tp.tripplanWantAge", "21");
		parameter.put("tp.tripplanWantPersons", "2");
		parameter.put("tp.tripplanWantSex", "女");
		Common.postToUrl("publishTripPlan", parameter,"DDE6B7D6D12B68006E191E907616AAC3","tourist");
	}
}
