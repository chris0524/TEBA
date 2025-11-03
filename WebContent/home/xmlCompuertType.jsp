<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String sSQL = "";

sSQL = " select codeID ,codeName from SYSCA_Code where 1=1 and codekindid='PCT' and codecon1 = " + Common.sqlChar(Common.esapi(propertyNo)) + " order by codeID ";

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record codeID=\""+Common.checkGet(rs.getString("codeID"))+"\"" +
		 " codeName=\"" + Common.checkGet(rs.getString("codeName")) + "\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
