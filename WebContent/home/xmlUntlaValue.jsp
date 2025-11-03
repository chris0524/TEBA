<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String originalDate = Common.checkGet(request.getParameter("originalDate")); 
String originalBasis = Common.checkGet(request.getParameter("originalBasis")); 
String originalUnit = Common.checkGet(request.getParameter("originalUnit")); 
String sSQL = "";
String sSQL1 = "";
String check= "";
sSQL = "select x.suitDateS, x.valueUnit	"+
		" from UNTLA_Value x                                    "+
		" where x.enterOrg =  " + Common.sqlChar(Common.esapi(enterOrg)) +
		"	 and x.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
		"	 and x.propertyNo= " + Common.sqlChar(Common.esapi(propertyNo)) +
		"	 and x.serialNo= " + Common.sqlChar(Common.esapi(serialNo)) +
		"   and x.bulletindate =                                "+
		"   (select max(a.bulletindate) from UNTLA_Value a      "+
		"     where a.enterOrg = x.enterOrg                     "+
		"      and x.propertyNo =a.propertyNo                   "+
		"      and x.ownership = a.ownership                    "+
		"      and x.serialNo = a.serialNo                      "+
		"   )                                                   ";
sSQL1 = "select suitDateS from UNTLA_BulletinDate " +
		"where bulletinkind='1' and bulletinDate=" + Common.sqlChar(Common.esapi(originalDate)) +
		"";
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		check="Y";
		strXML.append("<record bulletinDate=\""+Common.checkGet(rs.getString("suitDateS")) + "\""+
		" valueUnit=\""+Common.checkGet(rs.getString("valueUnit")) + "\""+
		" /> ");
	} 
	if(check==""){
		if(originalBasis.equals("1")){
			rs = db.querySQL(sSQL1);
				if(rs.next()){
					strXML.append("<record bulletinDate=\""+Common.checkGet(rs.getString("suitDateS")) + "\""+
					" valueUnit=\""+originalUnit+ "\"\n"+
					" /> ");
				}else{
					strXML.append("<record bulletinDate=\""+""+ "\"\n"+
					" valueUnit=\""+originalUnit+ "\"\n"+
					" /> ");
				}
		}else{
			strXML.append("<record "+
			" bulletinDate=\""+""+ "\"\n"+
			" valueUnit=\""+""+ "\"\n"+
			" /> ");
		}
	}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
