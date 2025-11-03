<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%

String disType = Common.checkGet(request.getParameter("disType"));
String disNo = Common.checkGet(request.getParameter("disNo"));
String disName = Common.checkGet(request.getParameter("disName"));

String sSQL = "";
String check="";

if (disType.equals("4")){
	sSQL = "select a.* "+
		" from GRASA_Discount a " +
		" where 1 = 1 " +
			" and a.disType 	 = " + Common.sqlChar(Common.esapi(disType)) +
			" and a.disName  = " + Common.sqlChar(Common.esapi(disName)) +
			" ";
	StringBuffer strXML = new StringBuffer();
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");
	Database db = new Database();
	try {		
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()){
			check="Y";
			strXML.append("<record disNo=\""+Common.get(Common.checkGet(rs.getString("disNo"))) + "\""+
			" disName=\""+Common.get(Common.checkGet(rs.getString("disName"))) + "\""+			
			" disValue1=\""+Common.checkGet(Common.get(""+rs.getFloat("disValue1"))) + "\""+				
			" disValue2=\""+Common.checkGet(Common.get(""+rs.getFloat("disValue2"))) + "\""+
			" notes=\""+Common.get(Common.checkGet(rs.getString("notes"))) + "\""+
			" check=\""+"Y"+ "\""+
			" /> ");
		} 
		if(check==""){
				strXML.append("<record " +
					" disNo=\""+""+ "\"\n"+
					" disName=\""+""+ "\"\n"+
					" disValue1=\""+""+ "\"\n"+
					" disValue2=\""+""+ "\"\n"+
					" notes=\""+""+ "\"\n"+
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
}
%>
