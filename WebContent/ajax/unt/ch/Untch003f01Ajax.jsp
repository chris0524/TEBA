<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.json.simple.*"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="util.Common"%>
<%@ page import="util.Database"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="util.Datetime"%>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH003F01">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%

String action = Common.checkGet(request.getParameter("action"));
String enterorg = Common.checkGet(request.getParameter("enterorg"));
String caseno = Common.checkGet(request.getParameter("caseno"));

Map<String, Object> data = null;
Database db = new Database();
ResultSet rs = null;

boolean isSuccess = false;
JSONObject jObj = new JSONObject();
try {		
	if ("checkIsNewData".equals(action)) {
		jObj.put("data", obj.checkIsNewData());
		isSuccess = true;
	} else {
		jObj.put("msg", "未知的action");
	}
	
} catch (Exception e) {
	e.printStackTrace();
	jObj.put("msg", "發生異常" + e.getMessage());
} finally {
	jObj.put("isSuccess", isSuccess);
	out.write(jObj.toString());
	
	if (rs != null) {
		try {
			rs.close();
			rs = null;
		} catch (Exception e) {
			//ignore
		}
	}
	db.closeAll();
}
%>
