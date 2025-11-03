<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common"/>
<%
String itemNo = Common.checkGet(request.getParameter("itemNo"));
String sSQL = "";

sSQL = "select detailNo, detailName from PUBPC_CheckDetail where itemNo=" + objCommon.sqlChar(Common.esapi(itemNo)) +
	" order by detailNo ";

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record detailNo=\""+Common.checkGet(rs.getString("detailNo"))+"\"" +
		 " detailName=\"" + Common.checkGet(rs.getString("detailName")) + "\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
