<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("q_enterOrg"));
String Bureau = Common.checkGet(request.getParameter("q_Bureau"));

String sql ="";
	sql  = " select unitNo, unitName from UNTMP_KeepUnit ";
	sql += " where 1 = 1 ";
	sql += " and enterOrg like '" + Common.esapi(enterOrg) + "%'";
	sql += " and Bureau like '" + Common.esapi(Bureau) + "%'";	
	sql += " order by enterOrg, unitNo";
//System.out.println("/*xmlQ_enterOrg.jsp*/"+ sql);
		
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		

	ResultSet rs = db.querySQL(sql);
	while (rs.next()){
		strXML.append("<record unitNo=\""+Common.checkGet(rs.getString(1))+"\" unitName=\""+Common.checkGet(rs.getString(2))+"\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
