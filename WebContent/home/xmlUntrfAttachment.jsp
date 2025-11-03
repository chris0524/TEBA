<%@ page contentType="application/xml;charset=UTF-8"
	import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common" />
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String propertyNoName = Common.checkGet(Common.isoToUTF8(request.getParameter("propertyNoName")));
String serialNo = Common.checkGet(Common.formatFrontZero(request.getParameter("serialNo"),7));
String sSQL = "";
String check="";
	sSQL = " select a.enterOrg, a.ownership, a.caseNo," + 
			" a.propertyName1," + 
			" a.serialNo," + 
			" a.reduceDate," + 
			" a.verify," + 
			" a.propertyKind," + 
			" a.fundType," + 
			" a.valuable," + 
			" a.taxCredit," + 
			" a.measure," + 
			" a.accountingTitle," + 
			" a.bookValue," + 
			" a.buildDate," + 
			" a.originalBV," +
			" a.sourceDate," + 
			" a.deprMethod," + 
			" a.deprAmount," + 
			" a.monthDepr," + 
			" a.apportionEndYM," + 
			" a.accumDeprYM," + 
			" a.accumDepr," + 
			" a.permitReduceDate," + 
			" a.oldPropertyNo," + 
			" a.oldSerialNo," +
			" b.propertyUnit,"+
			" b.propertyName,"+
			" a.propertyNo"+
			" from UNTRF_Attachment a, SYSPK_PropertyCode b where 1=1 " + 
			" and a.propertyNo=b.propertyNo and a.dataState='1' and a.verify='Y' " +
			" and a.enterOrg = " + Common.sqlChar(Common.esapi(enterOrg)) +
			" and a.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
			" ";
	sSQL+=" and a.propertyNo='" + Common.esapi(propertyNo) + "' and a.serialNo='" + Common.esapi(objCommon.formatFrontZero(serialNo,7)) + "'";
	//System.out.println(sSQL);
	StringBuffer strXML = new StringBuffer();
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");	
	Database db = new Database();
	try {		
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()){
			check="Y";
			strXML.append("<record " + 
							" propertyNo=\""+objCommon.get(Common.checkGet(rs.getString("propertyNo"))) + "\""+
							" propertyNoName=\""+objCommon.get(Common.checkGet(rs.getString("propertyName"))) + "\""+
							" propertyName1=\""+objCommon.get(Common.checkGet(rs.getString("propertyName1"))) + "\""+
							" serialNo=\""+objCommon.get(Common.checkGet(rs.getString("serialNo"))) + "\""+
							" propertyKind=\""+objCommon.get(Common.checkGet(rs.getString("propertyKind"))) + "\""+
							" fundType=\""+objCommon.get(Common.checkGet(rs.getString("fundType"))) + "\""+
							" valuable=\""+objCommon.get(Common.checkGet(rs.getString("valuable"))) + "\""+
							" taxCredit=\""+objCommon.get(Common.checkGet(rs.getString("taxCredit"))) + "\""+
							" measure=\""+objCommon.get(Common.checkGet(rs.getString("measure"))) + "\""+
							" accountingTitle=\""+objCommon.get(Common.checkGet(rs.getString("accountingTitle"))) + "\""+
							" bookValue=\""+objCommon.get(Common.checkGet(rs.getString("bookValue"))) + "\""+
							" buildDate=\""+objCommon.get(Common.checkGet(rs.getString("buildDate"))) + "\""+
							" originalBV=\""+objCommon.get(Common.checkGet(rs.getString("originalBV"))) + "\""+
							" sourceDate=\""+objCommon.get(Common.checkGet(rs.getString("sourceDate"))) + "\""+
							" deprMethod=\""+objCommon.get(Common.checkGet(rs.getString("deprMethod"))) + "\""+
							" deprAmount=\""+objCommon.get(Common.checkGet(rs.getString("deprAmount"))) + "\""+
							" monthDepr=\""+objCommon.get(Common.checkGet(rs.getString("monthDepr"))) + "\""+
							" apportionEndYM=\""+objCommon.get(Common.checkGet(rs.getString("apportionEndYM"))) + "\""+
							" accumDeprYM=\""+objCommon.get(Common.checkGet(rs.getString("accumDeprYM"))) + "\""+
							" accumDepr=\""+objCommon.get(Common.checkGet(rs.getString("accumDepr"))) + "\""+
							" permitReduceDate=\""+objCommon.get(Common.checkGet(rs.getString("permitReduceDate"))) + "\""+
							" oldPropertyNo=\""+objCommon.get(Common.checkGet(rs.getString("oldPropertyNo"))) + "\""+
							" oldSerialNo=\""+objCommon.get(Common.checkGet(rs.getString("oldSerialNo"))) + "\""+
							" propertyUnit=\""+objCommon.get(Common.checkGet(rs.getString("propertyUnit"))) + "\""+
							" check=\""+"Y"+ "\""+
					" /> ");
		} 
	if(check==""){
		strXML.append("<record \n" + 
					" propertyNo=\""+objCommon.get(propertyNo) + "\""+
					" propertyNoName=\""+objCommon.get(propertyNoName) + "\""+				
					" serialNo=\""+objCommon.get(serialNo) + "\""+
					" propertyName1=\""+""+ "\"\n"+
					" propertyKind=\""+""+ "\"\n"+
					" fundType=\""+""+ "\"\n"+
					" valuable=\""+""+ "\"\n"+
					" taxCredit=\""+""+ "\"\n"+
					" measure=\""+""+ "\"\n"+
					" accountingTitle=\""+""+ "\"\n"+
					" bookValue=\""+""+ "\"\n"+
					" buildDate=\""+""+ "\"\n"+
					" originalBV=\""+""+ "\"\n"+
					" sourceDate=\""+""+ "\"\n"+
					" deprMethod=\""+""+ "\"\n"+
					" deprAmount=\""+""+ "\"\n"+
					" monthDepr=\""+""+ "\"\n"+
					" apportionEndYM=\""+""+ "\"\n"+
					" accumDeprYM=\""+""+ "\"\n"+
					" accumDepr=\""+""+ "\"\n"+
					" permitReduceDate=\""+""+ "\"\n"+
					" oldPropertyNo=\""+""+ "\"\n"+
					" oldSerialNo=\""+""+ "\"\n"+
					" propertyUnit=\""+""+ "\"\n"+
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
