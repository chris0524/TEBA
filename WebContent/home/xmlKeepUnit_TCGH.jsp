<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));

String sql = " select unitNo, unitName from UNTMP_KeepUnit "+
				" where enterOrg like '" + Common.esapi(enterOrg) + "%'" +
				" order by enterOrg, unitNo";

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
