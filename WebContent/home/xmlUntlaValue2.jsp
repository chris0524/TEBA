<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
//程式目的：取得公告地價

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String bulletinDate = Common.checkGet(request.getParameter("bulletinDate"));
String sSQL = "";

sSQL += " select a.bulletinDate, a.valueUnit, a.suitDateS, a.suitDateE from UNTLA_Value a where 1=1 " +
	" and a.enterOrg = " + Common.sqlChar(Common.esapi(enterOrg)) +
	" and a.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
	" and a.propertyNo = " + Common.sqlChar(Common.esapi(propertyNo)) +
	" and a.serialNo = " + Common.sqlChar(Common.esapi(serialNo)) +
	"";

if (!"".equals(bulletinDate)) sSQL += " and a.bulletinDate=" + Common.sqlChar(bulletinDate);
else sSQL += " and a.bulletinDate = (select max(b.bulletinDate) from UNTLA_Value b where 1=1 " +
		" and b.enterOrg = " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and b.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
		" and b.propertyNo = " + Common.sqlChar(Common.esapi(propertyNo)) +
		" and b.serialNo = " + Common.sqlChar(Common.esapi(serialNo)) +						
		")";


StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record bulletinDate=\""+Common.checkGet(rs.getString("bulletinDate")) + "\""+
		" valueUnit=\""+Common.checkGet(rs.getString("valueUnit")) + "\""+
		" suitDateS=\""+Common.checkGet(rs.getString("suitDateS")) + "\""+
		" suitDateE=\""+Common.checkGet(rs.getString("suitDateE")) + "\""+		
		" /> ");
	}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
