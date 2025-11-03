<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.formatFrontZero(Common.checkGet(request.getParameter("serialNo")),7);

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	StringBuffer sbSQL = new StringBuffer();
	
	sbSQL.append(" select b.organSName,a.enterOrg, a.ownership, a.caseNo, " ).append(
		"a.propertyNo, a.serialNo, c.propertyName, c.propertyType, c.propertyUnit, " ).append(
		"c.material, c.limitYear, a.propertyName1, a.signNo , " ).append(
		"a.doorplate1, a.doorplate2, a.doorplate3, a.doorplate4, " ).append(
		"a.cause, a.cause1, a.enterDate, a.dataState, a.reduceDate, " ).append(
		"a.reduceCause, a.reduceCause1, a.verify, a.closing, a.propertyKind, " ).append(
		"a.fundType, a.valuable, a.taxCredit, TO_CHAR(a.originalArea,'999999999.99') as originalArea, " ).append(
		"a.originalHoldNume, a.originalHoldDeno, TO_CHAR(a.originalHoldArea,'999999999.99') as originalHoldArea, TO_CHAR(a.area,'999999999.99') as area, " ).append(
		"a.holdNume, a.holdDeno, TO_CHAR(a.holdArea,'999999999.99') as holdArea,  " ).append(
		"a.originalBV, a.originalNote, a.accountingTitle, " ).append(
		"a.bookValue, a.fundsSource, a.ownershipDate, a.ownershipCause, " ).append(
		"a.nonProof, a.proofDoc, a.proofVerify, a.ownershipNote, " ).append(
		"a.sourceKind, a.sourceDate, a.sourceDoc, " ).append(
		"a.manageOrg, d.organSName as manageOrgName, a.useState, a.proceedDateS, a.proceedDateE, a.proceedType, " ).append(
		"a.appraiseDate, a.notes, a.oldPropertyNo, a.oldSerialNo, (select x.propertyName from SYSPK_PropertyCode x where a.oldpropertyNo = x.propertyNo ) as oldpropertyName, " ).append(
		" a.otherLimitYear, a.buildStyle, a.originalCArea, a.originalSArea, " ).append(
		" a.CArea, a.SArea, a.buildDate, a.floor1, a.floor2, a.stuff, " ).append(
		" a.damageDate, a.damageExpire, decode(a.damageMark, '1', 'checked') as damageMark, a.deprMethod, a.scrapValue, " ).append(
		" a.deprAmount, a.apportionYear, a.monthDepr, a.useEndYM, a.apportionEndYM, " ).append(
		" a.accumDeprYM, a.isAccumDepr, a.accumDepr, a.permitReduceDate, " ).append(			
		" a.editID, a.editDate, a.editTime  ").append(
		" from UNTBU_Building a, SYSCA_Organ b , SYSPK_PropertyCode c, SYSCA_Organ d where 1=1 " ).append(
		" and a.enterOrg = " ).append( Common.sqlChar(Common.esapi(enterOrg)) ).append(
		" and a.ownership = " ).append( Common.sqlChar(Common.esapi(ownership)) ).append(
		" and a.propertyNo = " ).append( Common.sqlChar(Common.esapi(propertyNo)) ).append(
		" and a.serialNo = " ).append( Common.sqlChar(Common.esapi(serialNo)) ).append(
		" and b.organID = a.enterOrg" ).append(
		" and a.propertyNo = c.propertyNo " ).append(
		" and a.manageOrg = d.organID (+) " );
	
	//System.out.println("========untbu002f_xml.jsp AJAX SQL======");	
	//System.out.println(sbSQL.toString());	
	
	ResultSet rs = db.querySQL(sbSQL.toString());
	if (rs.next()){
		strXML.append("<record ").append(
				" enterOrg=\"").append(Common.checkGet(rs.getString("enterOrg")) ).append( "\"").append(	
				" enterOrgName=\"").append(Common.checkGet(rs.getString("organSName")) ).append( "\"").append(	
				" ownership=\"").append(Common.checkGet(rs.getString("ownership")) ).append( "\"").append(	
				" caseNo=\"").append(Common.checkGet(rs.getString("caseNo")) ).append( "\"").append(	
				" enterDate=\"").append(Common.checkGet(rs.getString("enterDate")) ).append( "\"").append(	
				" dataState=\"").append(Common.checkGet(rs.getString("dataState")) ).append( "\"").append(	
				" verify=\"").append(Common.checkGet(rs.getString("verify")) ).append( "\"").append(	
				" closing=\"").append(Common.checkGet(rs.getString("closing")) ).append( "\"").append(	
				" propertyNo=\"").append(Common.checkGet(rs.getString("propertyNo")) ).append( "\"").append(	
				" propertyNoName=\"").append(Common.checkGet(rs.getString("propertyName")) ).append( "\"").append(	
				" serialNo=\"").append(Common.checkGet(rs.getString("serialNo")) ).append( "\"").append(
				" propertyName1=\"").append(Common.checkGet(rs.getString("propertyName1")).replaceAll("&","&amp;") ).append( "\"").append(	
				" oldPropertyNo=\"").append(Common.checkGet(rs.getString("oldPropertyNo")) ).append( "\"").append(	
				" oldSerialNo=\"").append(Common.checkGet(rs.getString("oldSerialNo")) ).append( "\"").append(	
				" material=\"").append(Common.checkGet(rs.getString("material")) ).append( "\"").append(	
				" propertyUnit=\"").append(Common.checkGet(rs.getString("propertyUnit")) ).append( "\"").append(	
				" limitYear=\"").append(Common.checkGet(rs.getString("limitYear")) ).append( "\"").append(	
				" otherLimitYear=\"").append(Common.checkGet(rs.getString("otherLimitYear")) ).append( "\"").append(	
				" permitReduceDate=\"").append(Common.checkGet(rs.getString("permitReduceDate")) ).append( "\"").append(	
				" signNo=\"").append(Common.checkGet(rs.getString("signNo")) ).append( "\"").append(	
				" doorPlate1=\"").append(Common.checkGet(rs.getString("doorPlate1")) ).append( "\"").append(	
				" doorPlate2=\"").append(Common.checkGet(rs.getString("doorPlate2")) ).append( "\"").append(	
				" doorPlate3=\"").append(Common.checkGet(rs.getString("doorPlate3")) ).append( "\"").append(	
				" doorPlate4=\"").append(Common.checkGet(rs.getString("doorPlate4")) ).append( "\"").append(	
				" floor1=\"").append(Common.checkGet(rs.getString("floor1")) ).append( "\"").append(	
				" floor2=\"").append(Common.checkGet(rs.getString("floor2")) ).append( "\"").append(	
				" stuff=\"").append(Common.checkGet(rs.getString("stuff")) ).append( "\"").append(	
				" buildStyle=\"").append(Common.checkGet(rs.getString("buildStyle")) ).append( "\"").append(	
				" buildDate=\"").append(Common.checkGet(rs.getString("buildDate")) ).append( "\"").append(	
				" propertyKind=\"").append(Common.checkGet(rs.getString("propertyKind")) ).append( "\"").append(	
				" fundType=\"").append(Common.checkGet(rs.getString("fundType")) ).append( "\"").append(	
				" valuable=\"").append(Common.checkGet(rs.getString("valuable")) ).append( "\"").append(	
				" taxCredit=\"").append(Common.checkGet(rs.getString("taxCredit")) ).append( "\"").append(	
				" cause=\"").append(Common.checkGet(rs.getString("cause")) ).append( "\"").append(	
				" cause1=\"").append(Common.checkGet(rs.getString("cause1")) ).append( "\"").append(	
				" fundsSource=\"").append(Common.checkGet(rs.getString("fundsSource")) ).append( "\"").append(	
				" accountingTitle=\"").append(Common.checkGet(rs.getString("accountingTitle")) ).append( "\"").append(	
				" sourceKind=\"").append(Common.checkGet(rs.getString("sourceKind")) ).append( "\"").append(	
				" sourceDate=\"").append(Common.checkGet(rs.getString("sourceDate")) ).append( "\"").append(	
				" sourceDoc=\"").append(Common.checkGet(rs.getString("sourceDoc")) ).append( "\"").append(	
				" manageOrg=\"").append(Common.checkGet(rs.getString("manageOrg")) ).append( "\"").append(	
				" manageOrgName=\"").append(Common.checkGet(rs.getString("manageOrgName")) ).append( "\"").append(	
				" useState=\"").append(Common.checkGet(rs.getString("useState")) ).append( "\"").append(	
				" originalCArea=\"").append(Common.checkGet(rs.getString("originalCArea")) ).append( "\"").append(	
				" originalSArea=\"").append(Common.checkGet(rs.getString("originalSArea")) ).append( "\"").append(	
				" originalArea=\"").append(Common.checkGet(rs.getString("originalArea")) ).append( "\"").append(	
				" originalHoldNume=\"").append(Common.checkGet(rs.getString("originalHoldNume")) ).append( "\"").append(	
				" originalHoldDeno=\"").append(Common.checkGet(rs.getString("originalHoldDeno")) ).append( "\"").append(	
				" originalHoldArea=\"").append(Common.checkGet(rs.getString("originalHoldArea")) ).append( "\"").append(	
				" CArea=\"").append(Common.checkGet(rs.getString("CArea")) ).append( "\"").append(	
				" SArea=\"").append(Common.checkGet(rs.getString("SArea")) ).append( "\"").append(	
				" area=\"").append(Common.checkGet(rs.getString("area")) ).append( "\"").append(	
				" holdNume=\"").append(Common.checkGet(rs.getString("holdNume")) ).append( "\"").append(	
				" holdDeno=\"").append(Common.checkGet(rs.getString("holdDeno")) ).append( "\"").append(	
				" holdArea=\"").append(Common.checkGet(rs.getString("holdArea")) ).append( "\"").append(	
				" originalBV=\"").append(Common.checkGet(rs.getString("originalBV")) ).append( "\"").append(	
				" originalNote=\"").append(Common.checkGet(rs.getString("originalNote")) ).append( "\"").append(	
				" bookValue=\"").append(Common.checkGet(rs.getString("bookValue")) ).append( "\"").append(	
				" ownershipDate=\"").append(Common.checkGet(rs.getString("ownershipDate")) ).append( "\"").append(	
				" ownershipCause=\"").append(Common.checkGet(rs.getString("ownershipCause")) ).append( "\"").append(	
				" nonProof=\"").append(Common.checkGet(rs.getString("nonProof")) ).append( "\"").append(	
				" proofDoc=\"").append(Common.checkGet(rs.getString("proofDoc")) ).append( "\"").append(	
				" proofVerify=\"").append(Common.checkGet(rs.getString("proofVerify")) ).append( "\"").append(	
				" ownershipNote=\"").append(Common.checkGet(rs.getString("ownershipNote")) ).append( "\"").append(	
				" damageDate=\"").append(Common.checkGet(rs.getString("damageDate")) ).append( "\"").append(	
				" damageExpire=\"").append(Common.checkGet(rs.getString("damageExpire")) ).append( "\"").append(	
				" reduceDate=\"").append(Common.checkGet(rs.getString("reduceDate")) ).append( "\"").append(	
				" reduceCause=\"").append(Common.checkGet(rs.getString("reduceCause")) ).append( "\"").append(	
				" reduceCause1=\"").append(Common.checkGet(rs.getString("reduceCause1")) ).append( "\"").append(	
				" deprMethod=\"").append(Common.checkGet(rs.getString("deprMethod")) ).append( "\"").append(	
				" isAccumDepr=\"").append(Common.checkGet(rs.getString("isAccumDepr")) ).append( "\"").append(	
				" scrapValue=\"").append(Common.checkGet(rs.getString("scrapValue")) ).append( "\"").append(	
				" deprAmount=\"").append(Common.checkGet(rs.getString("deprAmount")) ).append( "\"").append(	
				" apportionYear=\"").append(Common.checkGet(rs.getString("apportionYear")) ).append( "\"").append(	
				" monthDepr=\"").append(Common.checkGet(rs.getString("monthDepr")) ).append( "\"").append(	
				" useEndYM=\"").append(Common.checkGet(rs.getString("useEndYM")) ).append( "\"").append(	
				" apportionEndYM=\"").append(Common.checkGet(rs.getString("apportionEndYM")) ).append( "\"").append(	
				" accumDeprYM=\"").append(Common.checkGet(rs.getString("accumDeprYM")) ).append( "\"").append(	
				" accumDepr=\"").append(Common.checkGet(rs.getString("accumDepr")) ).append( "\"").append(	
				" appraiseDate=\"").append(Common.checkGet(rs.getString("appraiseDate")) ).append( "\"").append(	
				" proceedType=\"").append(Common.checkGet(rs.getString("proceedType")) ).append( "\"").append(	
				" proceedDateS=\"").append(Common.checkGet(rs.getString("proceedDateS")) ).append( "\"").append(	
				" proceedDateE=\"").append(Common.checkGet(rs.getString("proceedDateE")) ).append( "\"").append(	
				" notes=\"").append(Common.checkGet(rs.getString("notes")).replace("&","&amp;") ).append( "\"").append(	
				" editID=\"").append(Common.checkGet(rs.getString("editID")) ).append( "\"").append(	
				" editDate=\"").append(Common.checkGet(rs.getString("editDate")) ).append( "\"").append(	
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
