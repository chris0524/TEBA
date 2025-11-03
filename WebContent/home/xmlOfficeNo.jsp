<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
//程式目的：依據[縣市代碼],取得[地政事務所]的代碼&名稱

String queryValue = Common.checkGet(request.getParameter("queryValue"));
String sSQL = "";

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");

Database db = new Database();
try {		
	
	sSQL = " select officeNo,officeName from SYSOT_Office where countyNo='"+ Common.esapi(queryValue) +"' order by officeNo ";
	
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record");
		strXML.append(" optValue=\"").append(Common.get(Common.checkGet(rs.getString(1))) ).append( "\"");
		strXML.append(" optText=\"").append(Common.get(Common.checkGet(rs.getString(2))) ).append( "\"");		
		strXML.append(" /> ");
	} 
	rs.close();
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
