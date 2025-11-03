<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));

String sSQL = "";
String check="";

sSQL = "select a.* "+
	" from GRASA_Case a " +
	" where 1 = 1 " +
		" and a.enterOrg 	 = " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and a.ownership  = " + Common.sqlChar(Common.esapi(ownership)) +
		" and a.propertyNo = " + Common.sqlChar(Common.esapi(propertyNo)) + 
		" and a.serialNo   = " + Common.sqlChar(Common.esapi(Common.formatFrontZero(serialNo,7))) +		
		" ";
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		check="Y";
		strXML.append("<record caseNo=\""+Common.get(Common.checkGet(rs.getString("caseNo"))) + "\""+
		" payType=\""+Common.get(Common.checkGet(rs.getString("payType"))) + "\""+				
		" payDateS=\""+Common.get(Common.checkGet(rs.getString("payDateS"))) + "\""+
		" payDateE=\""+Common.get(Common.checkGet(rs.getString("payDateE"))) + "\""+
		" receiveValue=\""+Common.get(Common.checkGet(rs.getString("receiveValue"))) + "\""+
		" holdArea=\""+Common.get(Common.checkGet(rs.getString("holdArea"))) + "\""+
		" holdArea1=\""+Common.get(Common.checkGet(rs.getString("holdArea1"))) + "\""+
		" holdArea2=\""+Common.get(Common.checkGet(rs.getString("holdArea2"))) + "\""+
		" check=\""+"Y"+ "\""+
		" /> ");
	} 
	if(check==""){
			strXML.append("<record " +
				" caseNo=\""+""+ "\"\n"+
				" payType=\""+""+ "\"\n"+
				" payDateS=\""+""+ "\"\n"+
				" payDateE=\""+""+ "\"\n"+
				" receiveValue=\""+""+ "\"\n"+
				" holdArea=\""+""+ "\"\n"+
				" holdArea1=\""+""+ "\"\n"+
				" holdArea2=\""+""+ "\"\n"+
				" check=\""+""+ "\"\n"+
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
