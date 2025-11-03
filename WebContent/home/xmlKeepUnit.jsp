<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String sSQL = "";

sSQL = "select unitNo, unitName from UNTMP_KeepUnit where enterOrg=" + Common.sqlChar(Common.esapi(enterOrg)) +
	" order by unitNo ";
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
//System.out.println(sSQL);
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record unitNo=\""+Common.checkGet(rs.getString("unitNo"))+"\"" +
		 " unitName=\"" + Common.checkGet(rs.getString("unitName")) + "\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
