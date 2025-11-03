<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%
//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

//String[] checks = resultArray[1].split("=");

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String engineeringno = Common.checkGet(request.getParameter("engineeringno"));
		
String placeName = "";

if(!"".equals(engineeringno)){
	String sql = "select engineeringname from UNTEG_ENGINEERINGCASE where enterorg = " + Common.sqlChar(enterOrg) 
				+ " and engineeringno = " + Common.sqlChar(Common.esapi(engineeringno));
	
	TDlib_Simple.com.src.DBServerTools dbt = new TDlib_Simple.com.src.DBServerTools();
	dbt._setDatabase(new Database());
	placeName = dbt._execSQL_asString(sql);

	if(placeName == null){
		placeName = "";
	}
}

String resultStr = "";

resultStr = "{\"engineeringName\":\"" + placeName + "\"}";

response.getWriter().write(resultStr);
%>

