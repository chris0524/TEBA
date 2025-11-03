<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String queryData = Common.checkGet(request.getParameter("queryData"));
//String[] resultArray=result.split("&");
String sql = "";
String data = "";

//String[] checks = resultArray[1].split("=");

//if(checks.length == 2){
if(!"".equals(enterOrg) && !"".equals(queryData)){
	TDlib_Simple.com.src.DBServerTools dbt = new TDlib_Simple.com.src.DBServerTools();
	dbt._setDatabase(new Database());
		
	//sql = "select deprunit from UNTMP_KEEPUNIT where enterorg = '" + resultArray[0].split("=")[1] + "' and unitno = '" + resultArray[1].split("=")[1] + "'";
	sql = "select deprunit from UNTMP_KEEPUNIT where enterorg = " + Common.sqlChar(Common.esapi(enterOrg)) + " and unitno = " + Common.sqlChar(Common.esapi(queryData));
	
	data = dbt._execSQL_asString(sql);
	
	if(data == null){
		data = "";
	}
}
String resultStr = "";

resultStr = "{\"defaultValue\":\"" + data + "\"}";

response.getWriter().write(resultStr);
%>

