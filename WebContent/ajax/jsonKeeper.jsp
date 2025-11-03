<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ page import="org.json.simple.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String incumbencyYN=Common.checkGet(request.getParameter("incumbencyYN"));
String sSQL = "";

String wheresql="";
if(!"N".equals(incumbencyYN)){
	wheresql = " and incumbencyYN = 'Y' ";
}
if (!"".equals(enterOrg)) {
	sSQL = "select enterOrg, keeperNo, keeperName from UNTMP_Keeper where enterOrg=" + Common.sqlChar(enterOrg) +
			wheresql +
			" order by keeperName, keeperNo ";
	Database db = new Database();
	try {
		JSONArray dsField = new JSONArray(); 
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()){
			JSONObject item = new JSONObject();
			item.put("enterOrg", Common.checkGet(rs.getString("enterOrg")));
			item.put("keeperNo", Common.checkGet(rs.getString("keeperNo")));
			item.put("keeperName", Common.checkGet(rs.getString("keeperName")));
			dsField.add(item);
			//strXML.append("<record keeperNo=\""+rs.getString("keeperNo")+"\"" +
			// " keeperName=\"" + rs.getString("keeperName") + "\" /> ");
		}		
		out.write(dsField.toString());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}
%>

