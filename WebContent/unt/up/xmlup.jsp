<%@ page contentType="application/xml;charset=UTF-8"
	import="java.sql.*,util.Common, util.Database"%>
<%
String enterOrg = Common.get(request.getParameter("enterOrg"));
String sTable = Common.get(request.getParameter("sTable"));
String textSerialNo = Common.get(request.getParameter("textSerialNo"));

if (!"".equals(enterOrg) && !"".equals(sTable) && !"".equals(textSerialNo)) {
	Database db = new Database();
	StringBuffer strXML = new StringBuffer();
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");	
	strXML.append("<ResultSet>");		
	try {
		ResultSet rs = db.querySQL(" select textSerialNo, isErr, errorColumn, editID, editDate from " + Common.esapi(sTable) + " where enterOrg="+Common.sqlChar(Common.esapi(enterOrg)) + " and textSerialNo="+Common.sqlChar(Common.esapi(textSerialNo)));
		if (rs.next()) {
			strXML.append("<record " + 
					" textSerialNo=\""+rs.getString("textSerialNo")+ "\""+
					" isErr=\""+rs.getString("isErr")+ "\""+
					" errorColumn=\""+rs.getString("errorColumn").replaceAll("<","&lt;").replaceAll(">","&gt;")+"\""+
					" editID=\""+rs.getString("editID")+"\""+
					" editDate=\""+rs.getString("editDate")+"\""+					
			" /> ");			
		}
	} finally {
		db.closeAll();
	}
	strXML.append("</ResultSet>");	
	out.write(strXML.toString());	
}
%>
