<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%
//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

String resultStr = "";
String placeName = "";

//String[] checks = resultArray[1].split("=");

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String place = Common.checkGet(
		  request.getParameter("place") != null
		    ? request.getParameter("place")
		    : request.getParameter("originalPlace1")
		);

if(!"".equals(place)){

	String sql = "select (case when isstop ='Y' then placename + '(已停用)' else placename end) from SYSCA_PLACE where enterorg = " + Common.sqlChar(enterOrg)
					+ " and placeno = " + Common.sqlChar(Common.esapi(place));
	TDlib_Simple.com.src.DBServerTools dbt = new TDlib_Simple.com.src.DBServerTools();
	dbt._setDatabase(new Database());
	placeName = dbt._execSQL_asString(sql);
	System.out.println("******placeName****" + placeName);
	if(placeName == null){
		placeName = "";
	}
}

resultStr = "{\"placeName\":\"" + placeName + "\"}";

response.getWriter().write(resultStr);
%>

