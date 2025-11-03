<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
//程式目的：取得建物稅籍資料

String tranType = Common.checkGet(request.getParameter("tranType"));
String sSQL = "";
String returnString = "";
//StringBuffer strXML = new StringBuffer();
//strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//strXML.append("<ResultSet>");

Database db = new Database();
try {		
	sSQL = "( select a.bulletinDate"+
		    " from UNTLA_BULLETINDATE a" +
			" where '1'=" + Common.sqlChar(Common.esapi(tranType)) +
			" and a.bulletinKind='1')"+
			" union ( select a.bulletinDate"+
    		" from UNTLA_BulletinDate a"+
    		" where '2'=" + Common.sqlChar(Common.esapi(tranType)) +
     		" and a.bulletinKind='2')"+
     		" union ( select a.bulletinDate"+
    		" from UNTLA_BulletinDate a,UNTLA_BulletinDate b"+
    		" where '3'=" + Common.sqlChar(Common.esapi(tranType)) +
     		" and a.bulletinKind='1'"+
     		" and b.bulletinKind='2'"+
     		" and a.bulletinDate=b.bulletinDate) "+
			" order by bulletinDate desc"+
			"";		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
//		strXML.append("<record bulletinDate=\"").append(Common.get(Datetime.changeTaiwanYYMM(rs.getString("bulletinDate"),"1")) ).append( "\"").append(
//		" /> ");
		returnString += Common.get(Datetime.changeTaiwanYYMM(rs.getString("bulletinDate"),"1")) + "/";
	} 
	rs.close();
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	
	if(!"".equals(returnString)){
		returnString = returnString.substring(0,returnString.length()-1);
	}
//strXML.append("</ResultSet>");	
//out.write(strXML.toString());
out.write(returnString);
%>
