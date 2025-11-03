<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%

String planDate = Common.checkGet(request.getParameter("planDate"));
String sql = "";

if (planDate!=null) {
	sql =" select checkDate from PUBPC_CheckOrg " +
		 " where planDate = '" + Common.esapi(planDate) + "'" +
		 " order by checkDate desc ";
}

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		

	ResultSet rs = db.querySQL(sql);
	while (rs.next()){
		strXML.append("<record checkDate=\""+Common.checkGet(rs.getString(1))+"\" /> ");
	}			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
