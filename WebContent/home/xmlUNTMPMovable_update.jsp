<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
//程式目的：取得建物稅籍資料

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String sSQL = "";


StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");

Database db = new Database();
try {		
	sSQL = " select a.originalBV, a.bookValue from UNTMP_Movable a "+
			" where a.enterOrg =  " + Common.sqlChar(Common.esapi(enterOrg)) +
			" and a.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
			" and a.propertyNo= " + Common.sqlChar(Common.esapi(propertyNo)) +
			" and a.serialNoS= " + Common.sqlChar(Common.esapi(serialNo)) +
			"";	
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record originalBV=\"").append(Common.get(Common.checkGet(rs.getString("originalBV"))) ).append( "\"").append(
		" bookValue=\"").append(Common.get(Common.checkGet(rs.getString("bookValue"))) ).append( "\"").append(
		" /> ");
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
