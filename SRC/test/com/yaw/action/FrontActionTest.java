package com.yaw.action;


import java.io.IOException;
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
		
	}

	public void testEscortSimpleSearch() {
		
	}

	public void testEscortAdvanceSearch() {
		
	}

	public void testEscortQueryByCity() {
		fail("Not yet implemented");
	}

	public void testEscortQuery8ByCity() {
		fail("Not yet implemented");
	}

	public void testEscortFindHomePageHeadline() {
		fail("Not yet implemented");
	}

	public void testEscort8Recommend() {
		fail("Not yet implemented");
	}

	public void testEscortQuery8NewRegist() {
		fail("Not yet implemented");
	}

	public void testEscortQuery8BySex() {
		fail("Not yet implemented");
	}

	public void testEscortQueryBySex() {
		fail("Not yet implemented");
	}

	public void testEscortQuery8ByImage() {
		fail("Not yet implemented");
	}

	public void testEscortQueryByImage() {
		fail("Not yet implemented");
	}

	public void testEscortQuery8ByFeel() {
		fail("Not yet implemented");
	}

	public void testEscortQueryByFeel() {
		fail("Not yet implemented");
	}

	public void testQuery8NewRegist() {
		fail("Not yet implemented");
	}

	public void testQuery16NewRegist() {
		fail("Not yet implemented");
	}

	public void testQuery16RecommendTourist() {
		fail("Not yet implemented");
	}

	public void testQueryHisTripplanList() {
		fail("Not yet implemented");
	}

	public void testQuery16Tourist() {
		fail("Not yet implemented");
	}

	public void testQueryTripplanList() {
		fail("Not yet implemented");
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
