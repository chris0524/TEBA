
<%@page import="unt.dp.UNTDP023R"%><%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../home/head.jsp" %>
<%@ page import="org.json.simple.*"%>
<%
//如果沒有設定驗證filter，請自行加權限驗證，否則請勿使用本程式..

response.addHeader("Pragma", "No-cache");
response.addHeader("Cache-Control", "no-cache");
String q_deprYM = Common.checkGet(request.getParameter("q_deprYM"));
try {	
	UNTDP023R obj = new UNTDP023R();
	JSONArray dsField = new JSONArray();
	String message = obj.checkTableExist(q_deprYM);		
	if (!"".equals(message)) {
		out.write(message);
	}			
} catch (Exception e) {
	e.printStackTrace();
}
%>


