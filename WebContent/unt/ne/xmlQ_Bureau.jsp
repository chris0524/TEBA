<%@ page contentType="application/xml;charset=UTF-8"	import="java.sql.*,util.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("q_enterOrg"));

String sql ="";
sql=" select BUREAU, BUREAUNAME from UNTMP_Bureau "+
	" where ENTERORG like '" + Common.esapi(enterOrg) +
	"%' order by ENTERORG, BUREAU";
//System.out.println("/*xmlQ_Bureau.jsp*/"+ sql);
		
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
