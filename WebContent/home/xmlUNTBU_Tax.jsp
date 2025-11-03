<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
//程式目的：取得建物稅籍資料

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String bulletinDate = Common.checkGet(request.getParameter("bulletinDate")); 
String taxYear = Datetime.getYYYMMDD().substring(0,3);
String sSQL = "";


StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
if (Common.get(bulletinDate).length()>2) {
	taxYear = bulletinDate.substring(0,3);
}
Database db = new Database();
try {		
	sSQL = " select a.taxYear, a.taxKind, a.taxAmount, a.taxPrice, a.taxState from UNTBU_Tax a "+
			" where a.enterOrg =  " + Common.sqlChar(Common.esapi(enterOrg)) +
			" and a.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
			" and a.propertyNo= " + Common.sqlChar(Common.esapi(propertyNo)) +
			" and a.serialNo= " + Common.sqlChar(Common.esapi(serialNo)) +
			" and a.taxYear <= " + Common.sqlChar(Common.esapi(taxYear)) +
			"";	
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record taxYear=\"").append(Common.get(Common.checkGet(rs.getString("taxYear"))) ).append( "\"").append(
		" taxAmount=\"").append(Common.get(Common.checkGet(rs.getString("taxAmount"))) ).append( "\"").append(
		" taxPrice=\"").append(Common.get(Common.checkGet(rs.getString("taxPrice"))) ).append( "\"").append(
		" taxState=\"").append(Common.get(Common.checkGet(rs.getString("taxState"))) ).append( "\"").append(
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
