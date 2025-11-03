<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../home/head.jsp" %>
<%@ page import="org.json.simple.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String sql ="";
//String sql =" select bureau, bureauname from UNTMP_Bureau "+
//		" where enterOrg like '" + enterOrg + "%'  order by enterOrg" ;
Database db = new Database();
try {		
	java.sql.ResultSet rs = db.querySQL(sql);
	JSONArray dsField = new JSONArray();
	while (rs.next()){
		JSONObject item=new JSONObject();
		item.put("unitNo", Common.checkGet(rs.getString(1)));
		item.put("unitName", Common.checkGet(rs.getString(2)));
		dsField.add(item);	
	}	
	out.write(dsField.toString());
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}
%>
