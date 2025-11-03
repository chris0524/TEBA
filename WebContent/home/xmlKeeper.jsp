<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String incumbencyYN=Common.checkGet(request.getParameter("incumbencyYN"));
String sSQL = "";

String wheresql="";
if(!"N".equals(incumbencyYN)){
	wheresql = " and incumbencyYN = 'Y' ";
}

sSQL = "select enterOrg, keeperNo, keeperName from UNTMP_Keeper where enterOrg=" + Common.sqlChar(Common.esapi(enterOrg)) +
	wheresql +
	" order by keeperName, keeperNo ";

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record keeperNo=\""+Common.checkGet(rs.getString("keeperNo"))+"\"" +
		 " keeperName=\"" + Common.checkGet(rs.getString("keeperName")) + "\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
