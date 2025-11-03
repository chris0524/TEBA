<%@ page contentType="application/xml;charset=UTF-8"	import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%

String q_propertyNo = Common.get(Common.checkGet(request.getParameter("q_propertyNo")));
String preWord = Common.get(Common.checkGet(request.getParameter("preWord")));
String q_enterOrg = Common.get(Common.checkGet(request.getParameter("q_enterOrg")));

if ("".equals(q_enterOrg)) {
	q_enterOrg = user.getOrganID();
}

String sql=" select a.propertyNo, a.propertyName, a.propertyType, a.propertyUnit, a.material, rtrim(ltrim(a.limitYear)) as limitYear " +
	" from SYSPK_PropertyCode a where a.enterOrg='000000000A' ";

if (!"".equals(q_propertyNo) && q_propertyNo.length()>0) {
	if (q_propertyNo.substring(0,1).equals("6")) {
		sql = " select a.propertyNo, a.propertyName, a.propertyType, a.propertyUnit, a.material, rtrim(ltrim(a.limitYear)) as limitYear " +
			" from SYSPK_PropertyCode2 a where a.enterOrg='"+Common.esapi(q_enterOrg)+"'";
	} else if (q_propertyNo.length()>2 && q_propertyNo.substring(0,3).equals("503")||q_propertyNo.substring(0,3).equals("504")||q_propertyNo.substring(0,3).equals("505")) {
		sql = " select a.propertyNo, a.propertyName, a.propertyType, a.propertyUnit, a.material, rtrim(ltrim(a.limitYear)) as limitYear " +
		" from SYSPK_PropertyCode a where a.enterOrg in ('000000000A', '"+Common.esapi(q_enterOrg)+"') ";		
	}
}

if (Common.get(preWord).length()>0 && Common.get(preWord).indexOf(",")>0) {
	String[] arrPreWord = preWord.split(",");
	boolean sFlag = true;
	String strSQL1="";
	for (int i=0; i<arrPreWord.length; i++) {
		if ("1".equals(arrPreWord[i])) {
			strSQL1=" and a.propertyNo not like '111%' ";
		}
		if (Double.parseDouble(arrPreWord[i])>=503){
			sFlag = false;
		}
	}
	if (sFlag) {
		sql+=" and a.propertyType='1' ";				
	}
	sql += strSQL1 + " and substring(a.propertyNo,1,1) in (" + Common.get(Common.esapi(preWord)) + ")";
} else {
	if (Double.parseDouble("0"+preWord)<503){
		sql+=" and a.propertyType='1' ";
	}
	
	if ("1".equals(preWord)){
		sql+=" and a.propertyNo not like '111%' ";
	}		
	sql+=" and a.propertyNo like '" + Common.get(Common.esapi(preWord)) +"%' ";	
}

if (!"".equals(Common.get(q_propertyNo)) && Common.get(q_propertyNo).length()>0) {
	sql+=" and a.propertyNo = '" + Common.get(Common.esapi(q_propertyNo)) +"' ";
	StringBuffer strXML = new StringBuffer();
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");	
	Database db = new Database();
	try {		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){			
			strXML.append("<record " + 
					" propertyNo=\""+Common.get(Common.checkGet(rs.getString("propertyNo"))) + "\""+
					" propertyName=\""+Common.get(Common.checkGet(rs.getString("propertyName"))) + "\""+				
					" propertyType=\""+Common.get(Common.checkGet(rs.getString("propertyType"))) + "\""+
					" propertyUnit=\""+Common.get(Common.checkGet(rs.getString("propertyUnit"))) + "\""+
					" material=\""+Common.get(Common.checkGet(rs.getString("material"))) + "\""+					
					" limitYear=\""+Common.checkGet(rs.getString("limitYear"))==null||"".equals(Common.checkGet(rs.getString("limitYear")))?"":(Integer.parseInt(Common.checkGet(rs.getString("limitYear"))+"")) + "\""+				

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
