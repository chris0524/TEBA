<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ page import="org.json.simple.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));

String sql = " select unitNo, unitName from UNTMP_KeepUnit "+
				" where enterOrg like '" + enterOrg + "%'" +
				" order by enterOrg, unitNo";
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sql);
	JSONArray dsField = new JSONArray();
	while (rs.next()){
		JSONObject item = new JSONObject();
		item.put("unitNo", Common.checkGet(rs.getString(1)));
		item.put("unitName", Common.checkGet(rs.getString(2)));
		dsField.add(item);	
		//strXML.append("<record unitNo=\""+rs.getString(1)+"\" unitName=\""+rs.getString(2)+"\" /> ");
	}			
	out.write(dsField.toString());
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	
%>
