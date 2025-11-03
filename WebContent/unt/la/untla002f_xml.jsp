<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String enterOrg = Common.get(request.getParameter("enterOrg"));
String ownership = Common.get(request.getParameter("ownership"));
String propertyNo = Common.get(request.getParameter("propertyNo"));
String serialNo = Common.formatFrontZero(Common.get(request.getParameter("serialNo")),7);

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	StringBuffer sbSQL = new StringBuffer().append(" select b.organSName,a.enterOrg, a.ownership, a.caseNo, " ).append(
		"a.propertyNo, a.serialNo, (select c.propertyName from SYSPK_PropertyCode c where a.propertyNo = c.propertyNo) as propertyName, a.propertyName1, a.signNo , " ).append(
		"a.doorplate, a.cause, a.cause1, a.enterDate, a.dataState, a.reduceDate, " ).append(
		"a.reduceCause, a.reduceCause1, a.verify, a.closing, a.propertyKind, " ).append(
		"a.fundType, a.valuable, a.taxCredit, a.grass, a.originalArea, " ).append(
		"a.originalHoldNume, a.originalHoldDeno, a.originalHoldArea, a.area, " ).append(
		"a.holdNume, a.holdDeno, a.holdArea, a.originalBasis, a.originalDate, " ).append(
		"a.originalUnit, a.originalBV, a.originalNote, a.accountingTitle, " ).append(
		"a.bookUnit, a.bookValue, a.fundsSource, a.useSeparate, a.useKind, " ).append(
		"a.oriUseSeparate, a.oriUseKind, a.ownershipDate, a.ownershipCause, " ).append(
		"a.nonProof, a.proofDoc, a.proofVerify, a.ownershipNote, a.field, " ).append(
		"a.landRule, a.sourceKind, a.sourceDate, a.sourceDoc, a.oldOwner, " ).append(
		"a.manageOrg, (select d.organSName from SYSCA_Organ d where a.manageOrg = d.organID) as manageOrgName, a.useState, a.proceedDateS, a.proceedDateE, a.proceedType, " ).append(
		"a.appraiseDate, a.notes1, a.notes, a.oldPropertyNo, a.oldSerialNo, (select x.propertyName from SYSPK_PropertyCode x where a.oldpropertyNo = x.propertyNo ) as oldpropertyName, " ).append(
		" a.editID, a.editDate, a.editTime  ").append( 
		" from UNTLA_Land a, SYSCA_Organ b where 1=1 " ).append(
		" and a.enterOrg = " ).append( Common.sqlChar(Common.esapi(enterOrg)) ).append(
		" and a.ownership = " ).append( Common.sqlChar(Common.esapi(ownership)) ).append(
		" and a.propertyNo = " ).append( Common.sqlChar(Common.esapi(propertyNo)) ).append(
		" and a.serialNo = " ).append( Common.sqlChar(Common.esapi(serialNo)) ).append(
		" and b.organID = a.enterOrg" ).append(
		"");	
	//System.out.println(sbSQL.toString());
	ResultSet rs = db.querySQL(sbSQL.toString());
	if (rs.next()){
		strXML.append("<record ").append(
		" caseNo=\"").append(Common.get(rs.getString("caseNo")) ).append( "\"").append(				
		" enterOrg=\"").append(Common.get(rs.getString("enterOrg")) ).append( "\"").append(					
		" enterOrgName=\"").append(Common.get(rs.getString("organSName")) ).append( "\"").append(					
		" ownership=\"").append(Common.get(rs.getString("ownership")) ).append( "\"").append(									
		" verify=\"").append(Common.get(rs.getString("verify")) ).append( "\"").append(									
		" closing=\"").append(Common.get(rs.getString("closing")) ).append( "\"").append(													
		" enterDate=\"").append(Common.get(rs.getString("enterDate")) ).append( "\"").append(				
		" dataState=\"").append(Common.get(rs.getString("dataState")) ).append( "\"").append(	
		" propertyNo=\"").append(Common.get(rs.getString("propertyNo")) ).append( "\"").append(	
		" propertyNoName=\"").append(Common.get(rs.getString("propertyName")) ).append( "\"").append(	
		" serialNo=\"").append(Common.get(rs.getString("serialNo")) ).append( "\"").append(	
		" propertyName1=\"").append(Common.get(rs.getString("propertyName1")) ).append( "\"").append(	
		" oldPropertyNo=\"").append(Common.get(rs.getString("oldPropertyNo")) ).append( "\"").append(	
		" oldSerialNo=\"").append(Common.get(rs.getString("oldSerialNo")) ).append( "\"").append(	
		" oriUseSeparate=\"").append(Common.get(rs.getString("oriUseSeparate")) ).append( "\"").append(	
		" oriUseKind=\"").append(Common.get(rs.getString("oriUseKind")) ).append( "\"").append(	
		" signNo=\"").append(Common.get(rs.getString("signNo")) ).append( "\"").append(	
		" doorplate=\"").append(Common.get(rs.getString("doorplate")) ).append( "\"").append(	
		" grass=\"").append(Common.get(rs.getString("grass")) ).append( "\"").append(	
		" propertyKind=\"").append(Common.get(rs.getString("propertyKind")) ).append( "\"").append(	
		" fundType=\"").append(Common.get(rs.getString("fundType")) ).append( "\"").append(	
		" valuable=\"").append(Common.get(rs.getString("valuable")) ).append( "\"").append(	
		" taxCredit=\"").append(Common.get(rs.getString("taxCredit")) ).append( "\"").append(	
		" cause=\"").append(Common.get(rs.getString("cause")) ).append( "\"").append(	
		" cause1=\"").append(Common.get(rs.getString("cause1")) ).append( "\"").append(	
		" fundsSource=\"").append(Common.get(rs.getString("fundsSource")) ).append( "\"").append(	
		" accountingTitle=\"").append(Common.get(rs.getString("accountingTitle")) ).append( "\"").append(	
		" sourceKind=\"").append(Common.get(rs.getString("sourceKind")) ).append( "\"").append(	
		" sourceDate=\"").append(Common.get(rs.getString("sourceDate")) ).append( "\"").append(	
		" sourceDoc=\"").append(Common.get(rs.getString("sourceDoc")) ).append( "\"").append(	
		" oldOwner=\"").append(Common.get(rs.getString("oldOwner")) ).append( "\"").append(	
		" manageOrg=\"").append(Common.get(rs.getString("manageOrg")) ).append( "\"").append(	
		" manageOrgName=\"").append(Common.get(rs.getString("manageOrgName")) ).append( "\"").append(
		" useState=\"").append(Common.get(rs.getString("useState")) ).append( "\"").append(	
		" originalArea=\"").append(Common.get(rs.getString("originalArea")) ).append( "\"").append(	
		" originalHoldNume=\"").append(Common.get(rs.getString("originalHoldNume")) ).append( "\"").append(	
		" originalHoldDeno=\"").append(Common.get(rs.getString("originalHoldDeno")) ).append( "\"").append(	
		" originalHoldArea=\"").append(Common.get(rs.getString("originalHoldArea")) ).append( "\"").append(	
		" area=\"").append(Common.get(rs.getString("area")) ).append( "\"").append(	
		" holdNume=\"").append(Common.get(rs.getString("holdNume")) ).append( "\"").append(	
		" holdDeno=\"").append(Common.get(rs.getString("holdDeno")) ).append( "\"").append(	
		" holdArea=\"").append(Common.get(rs.getString("holdArea")) ).append( "\"").append(	
		" originalBasis=\"").append(Common.get(rs.getString("originalBasis")) ).append( "\"").append(	
		" originalDate=\"").append(Common.get(rs.getString("originalDate")) ).append( "\"").append(	
		" originalUnit=\"").append(Common.get(rs.getString("originalUnit")) ).append( "\"").append(	
		" originalBV=\"").append(Common.get(rs.getString("originalBV")) ).append( "\"").append(	
		" originalNote=\"").append(Common.get(rs.getString("originalNote")) ).append( "\"").append(	
		" bookUnit=\"").append(Common.get(rs.getString("bookUnit")) ).append( "\"").append(	
		" bookValue=\"").append(Common.get(rs.getString("bookValue")) ).append( "\"").append(	
		" ownershipDate=\"").append(Common.get(rs.getString("ownershipDate")) ).append( "\"").append(	
		" ownershipCause=\"").append(Common.get(rs.getString("ownershipCause")) ).append( "\"").append(	
		" nonProof=\"").append(Common.get(rs.getString("nonProof")) ).append( "\"").append(	
		" proofDoc=\"").append(Common.get(rs.getString("proofDoc")) ).append( "\"").append(	
		" proofVerify=\"").append(Common.get(rs.getString("proofVerify")) ).append( "\"").append(	
		" ownershipNote=\"").append(Common.get(rs.getString("ownershipNote")) ).append( "\"").append(	
		" useSeparate=\"").append(Common.get(rs.getString("useSeparate")) ).append( "\"").append(	
		" useKind=\"").append(Common.get(rs.getString("useKind")) ).append( "\"").append(	
		" field=\"").append(Common.get(rs.getString("field")) ).append( "\"").append(	
		" landRule=\"").append(Common.get(rs.getString("landRule")) ).append( "\"").append(	
		" reduceDate=\"").append(Common.get(rs.getString("reduceDate")) ).append( "\"").append(	
		" reduceCause=\"").append(Common.get(rs.getString("reduceCause")) ).append( "\"").append(	
		" reduceCause1=\"").append(Common.get(rs.getString("reduceCause1")) ).append( "\"").append(	
		" appraiseDate=\"").append(Common.get(rs.getString("appraiseDate")) ).append( "\"").append(	
		" proceedType=\"").append(Common.get(rs.getString("proceedType")) ).append( "\"").append(	
		" proceedDateS=\"").append(Common.get(rs.getString("proceedDateS")) ).append( "\"").append(	
		" proceedDateE=\"").append(Common.get(rs.getString("proceedDateE")) ).append( "\"").append(	
		" notes1=\"").append(Common.get(rs.getString("notes1")) ).append( "\"").append(	
		" notes=\"").append(Common.get(rs.getString("notes")) ).append( "\"").append(	
		" editID=\"").append(Common.get(rs.getString("editID")) ).append( "\"").append(	
		" editDate=\"").append(Common.get(rs.getString("editDate")) ).append( "\"").append(
		" /> ");				
		
	}
	rs.close();
		
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
