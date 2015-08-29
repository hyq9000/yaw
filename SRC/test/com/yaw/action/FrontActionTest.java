package com.yaw.action;


import java.io.IOException;
import java.security.Policy.Parameters;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import com.common.web.HttpUtils;

import junit.framework.TestCase;

public class FrontActionTest extends TestCase {	
	
	public void testGetEscortInfoDetail() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("mid", "mng");
		Common.postToUrl("getEscortInfoDetail", parameter,null,"front");
	}

	public void testGetTouristInfoDetail() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("mid", "爱吹NB的兔子");
		Common.postToUrl("getTouristInfoDetail", parameter,null,"front");
	}

	public void testGetPhotoList() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("mid", "mng");
		parameter.put("pn","1");
		parameter.put("psize","10");
		Common.postToUrl("getPhotoList", parameter,null,"front");
	}

	public void testEscortSimpleSearch() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("sex", "女");
		parameter.put("city","长沙");
		parameter.put("online","on");
		Common.postToUrl("escortSimpleSearch", parameter,null,"front");
	}

	public void testEscortAdvanceSearch() {
		
	}

	public void testEscortQueryByCity() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("city","武汉");
		parameter.put("pn","1");
		Common.postToUrl("escortQueryByCity", parameter,null,"front");
	
	}

	public void testEscortQuery8ByCity() {
		Map<String, String> parameter=new HashMap<String, String>();		
		parameter.put("city","武汉");
		Common.postToUrl("escortQuery8ByCity", parameter,null,"front");
	}

	public void testEscortFindHomePageHeadline() {
		Map<String, String> parameter=new HashMap<String, String>();		
		Common.postToUrl("escortFindHomePageHeadline", parameter,null,"front");
	}

	public void testEscort8Recommend() {
		Map<String, String> parameter=new HashMap<String, String>();		
		Common.postToUrl("escort8Recommend", parameter,null,"front");
	}

	public void testEscortQuery8NewRegist() {
		Common.postToUrl("escortQuery8NewRegist", null,null,"front");
	}

	public void testEscortQuery8BySex() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("sex", "男");
		Common.postToUrl("escortQuery8BySex", parameter,null,"front");
	}

	public void testEscortQueryBySex() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("sex", "女");
		Common.postToUrl("escortQueryBySex", parameter,null,"front");
	}

	public void testEscortQuery8ByImage() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("image", "0");
		Common.postToUrl("escortQuery8ByImage", parameter,null,"front");
	}

	public void testEscortQueryByImage() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("image", "0");
		parameter.put("pn", "1");
		Common.postToUrl("escortQueryByImage", parameter,null,"front");
	}

	public void testEscortQuery8ByFeel() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("feel", "0");
		Common.postToUrl("escortQuery8ByFeel", parameter,null,"front");
	}

	public void testEscortQueryByFeel() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("feel", "0");
		parameter.put("pn", "1");
		Common.postToUrl("escortQueryByFeel", parameter,null,"front");
	}

	public void testQuery8NewRegist() {
		Map<String, String> parameter=new HashMap<String, String>();
		Common.postToUrl("query8NewRegist", parameter,null,"front");
	}

	public void testQuery16NewRegist() {
		Map<String, String> parameter=new HashMap<String, String>();
		Common.postToUrl("query16NewRegist", parameter,null,"front");
	}

	public void testQuery16RecommendTourist() {
		Map<String, String> parameter=new HashMap<String, String>();
		Common.postToUrl("query16RecommendTourist", parameter,null,"front");
	}

	public void testQueryHisTripplanList() {
		Map<String, String> parameter=new HashMap<String, String>();
		parameter.put("pn","1");
		parameter.put("mid","爱吹NB的兔子");		
		Common.postToUrl("queryHisTripplanList", parameter,null,"front");
	}

	public void testQuery16Tourist() {
		Map<String, String> parameter=new HashMap<String, String>();	
		Common.postToUrl("query16Tourist", parameter,null,"front");
	}

	public void testQueryTripplanList() {
		Map<String, String> parameter=new HashMap<String, String>();	
		parameter.put("pn","1");
		Common.postToUrl("queryTripplanList", parameter,null,"front");
	}

	public void testSimpleSearchTripplan() {
		fail("Not yet implemented");
	}

	public void testAdvanceSearchTripplan() {
		fail("Not yet implemented");
	}

	public void testQueryNewPublishTripplan() {
		fail("Not yet implemented");
	}

	public void testQueryHomePage3Line() {
		fail("Not yet implemented");
	}

	public void testGetMemberTripplanList() {
		fail("Not yet implemented");
	}

	public void testGetPhotoDetail() {
		fail("Not yet implemented");
	}

	public void testGetTripplanDetail() {
		fail("Not yet implemented");
	}

	public void testSetEscortInfoService() {
		fail("Not yet implemented");
	}

	public void testSetMemberService() {
		fail("Not yet implemented");
	}

	public void testSetFocusService() {
		fail("Not yet implemented");
	}

	public void testSetTouristInfoService() {
		fail("Not yet implemented");
	}

	public void testSetTripplanService() {
		fail("Not yet implemented");
	}

}
