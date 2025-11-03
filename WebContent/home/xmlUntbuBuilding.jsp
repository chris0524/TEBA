<%@ page contentType="application/xml;charset=UTF-8"	import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<%
String sType = Common.checkGet(request.getParameter("sType"));
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String dataState = Common.checkGet(request.getParameter("dataState"));
String verify = Common.checkGet(request.getParameter("verify"));
String closing = Common.checkGet(request.getParameter("closing"));
String proofVerify = Common.checkGet(request.getParameter("proofVerify"));
String signNo = "";
String sSQL = "";
String check="";
String propertyNo="";
String propertyName="";
String serialNo="";
String signNo1="";
String signNo2="";
String signNo3="";
String signNo4="";
String signNo5="";
	sSQL = " select a.enterOrg, d.organSName as enterOrgName, a.ownership, a.caseNo," + 
	" a.propertyNo," + 
	" b.propertyName," +
	" b.material," +
	" b.propertyUnit," +
	" b.limitYear," +
	" a.serialNo," + 
	" a.otherLimitYear," + 
	" a.propertyName1," + 
	" a.signNo," + 
	" c.signName," +
	" a.doorPlate1," + 
	" a.doorPlate2," + 
	" a.doorPlate3," + 
	" a.doorPlate4," + 
	" e.addrName||f.addrName||g.addrName||a.doorPlate4 as doorPlateName,"+
	" a.buildStyle, l.codeName as buildStyleName, " + 
	" a.cause," + 
	" a.cause1," + 
	" a.enterDate," + 
	" a.dataState," + 
	" a.reduceDate," + 
	" a.reduceCause," + 
	" a.reduceCause1," + 
	" a.verify," + 
	" a.closing," + 
	" a.propertyKind," + 
	" a.fundType," + 
	" a.valuable," + 
	" a.taxCredit," + 
	" a.originalCArea," + 
	" a.originalSArea," + 
	" a.originalArea," + 
	" a.originalHoldNume," + 
	" a.originalHoldDeno," + 
	" a.originalHoldArea," + 
	" a.CArea," + 
	" a.SArea," + 
	" a.area," + 
	" a.holdNume," + 
	" a.holdDeno," + 
	" a.holdArea," + 
	" a.originalBV," + 
	" a.originalNote," + 
	" a.accountingTitle," + 
	" a.bookValue," + 
	" a.fundsSource," + 
	" a.ownershipDate," + 
	" a.ownershipCause," + 
	" a.nonProof," + 
	" a.proofDoc," + 
	" a.proofVerify," + 
	" a.buildDate," + 
	" a.floor1," + 
	" i.codeName as floor1Name,"+
	" a.floor2," + 
	" j.codeName as floor2Name,"+
	" a.stuff," + 
	" a.ownershipNote," + 
	" a.sourceKind," + 
	" a.sourceDate," + 
	" a.sourceDoc," + 
	" a.manageOrg," + 
	" a.damageDate," + 
	" a.damageExpire," + 
	" a.damageMark," + 
	" a.deprMethod," + 
	" a.scrapValue," + 
	" a.deprAmount," + 
	" a.apportionYear," + 
	" a.monthDepr," + 
	" a.useEndYM," + 
	" a.apportionEndYM," + 
	" a.accumDeprYM," + 
	" a.accumDepr," + 
	" a.permitReduceDate," + 
	" a.useState," + 
	" h.codeName as useStateName," + 
	" a.proceedDateS," + 
	" a.proceedDateE," + 
	" a.proceedType," + 
	" k.codeName as proceedTypeName, " +
	" a.appraiseDate," + 
	" a.notes," + 
	" a.oldPropertyNo," + 
	" a.oldSerialNo" +
	" from UNTBU_Building a, SYSPK_PropertyCode b, SYSCA_Sign c, SYSCA_Organ d, " +
	" SYSCA_Addr e, SYSCA_Addr f, SYSCA_Addr g, SYSCA_Code h, "+
	" SYSCA_Code i, SYSCA_Code j, SYSCA_Code k, SYSCA_Code l " +
	" where 1=1 ";			
	if ("PN".equals(sType)) {
		propertyNo = Common.get(Common.checkGet(request.getParameter("propertyNo")));
		propertyName = Common.isoToUTF8(Common.checkGet(request.getParameter("propertyName")));
		serialNo = Common.formatFrontZero(Common.get(Common.checkGet(request.getParameter("serialNo"))),7);
		sSQL+=" and a.propertyNo='" + Common.esapi(propertyNo) + "' and a.serialNo='" + Common.esapi(Common.formatFrontZero(serialNo,7)) + "'";
	} else {
		signNo1 = Common.get(Common.checkGet(request.getParameter("sNo1")));
		signNo2 = Common.get(Common.checkGet(request.getParameter("sNo2")));
		signNo3 = Common.get(Common.checkGet(request.getParameter("sNo3")));
		signNo4 = Common.formatFrontZero(Common.get(Common.checkGet(request.getParameter("sNo4"))),5);
		signNo5 = Common.formatFrontZero(Common.get(Common.checkGet(request.getParameter("sNo5"))),3);
		String s1, s2, s3, s4="", s5="";	
		s1 = signNo1.substring(0,1);
		s2 = signNo2.substring(1,3);
		s3 = signNo3.substring(3,7);
		if (signNo4!=null && signNo4.trim().length() > 0 ) {
			s4 = Common.formatFrontZero(signNo4,5);
		} else {
			s4 = Common.formatFrontZero("0",5);
		}
		if (signNo5!=null && signNo5.trim().length() > 0 ) {
			s5 = Common.formatFrontZero(signNo5,3);
		} else {
			s5 = Common.formatFrontZero("0",3);
		}
		signNo = s1 + s2 + s3 + s4 + s5;
		sSQL+=" and a.signNo='" + Common.esapi(signNo) + "'";
	}
	if (!"".equals(Common.get(dataState))) sSQL += " and a.dataState=" + Common.sqlChar(Common.esapi(dataState));
	else sSQL += " and a.dataState='1' ";
	if (!"".equals(Common.get(verify))) sSQL += " and a.verify=" + Common.sqlChar(Common.esapi(verify));
	else sSQL += " and a.verify='Y' ";
	if (!"".equals(Common.get(closing))) sSQL += " and a.closing=" + Common.sqlChar(Common.esapi(closing));
	if (!"".equals(Common.get(proofVerify))) sSQL += " and a.proofVerify=" + Common.sqlChar(Common.esapi(proofVerify));		
	
	sSQL+=" and a.propertyNo=b.propertyNo and substr(a.signNo,1,7)=c.signNo(+) and a.enterOrg=d.organID " +
	" and a.doorPlate1=e.addrID(+) and a.doorPlate2=f.addrID(+) " +
	" and a.doorPlate3=g.addrID(+) " +	
	" and h.codeKindID(+)='UST' and a.useState=h.codeID(+) " +		
	" and i.codeKindID(+)='FLA' and a.floor1=i.codeID(+) " +		
	" and j.codeKindID(+)='FLB' and a.floor2=j.codeID(+) " +			
	" and k.codeKindID(+)='PRO' and a.proceedType=k.codeID(+) " +	
	" and l.codeKindID(+)='BST' and a.buildStyle=l.codeID(+) " +		
	" and a.enterOrg = " + Common.sqlChar(Common.esapi(enterOrg)) +
	" and a.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
	" ";	

	StringBuffer strXML = new StringBuffer(1500);
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");	
	Database db = new Database();
	try {
		ResultSet rs = db.querySQL(sSQL);
		while (rs.next()){
			check="Y";
			strXML.append("<record " ).append( 
					" enterOrg=\"").append(Common.get(Common.checkGet(rs.getString("enterOrg"))) ).append( "\"").append(
					" enterOrgName=\"").append(Common.get(Common.checkGet(rs.getString("enterOrgName"))) ).append( "\"").append(				
					" ownership=\"").append(Common.get(Common.checkGet(rs.getString("ownership"))) ).append( "\"").append(
					" caseNo=\"").append(Common.get(Common.checkGet(rs.getString("caseNo"))) ).append( "\"").append(
					" propertyNo=\"").append(Common.get(Common.checkGet(rs.getString("propertyNo"))) ).append( "\"").append(		
					" propertyNoName=\"").append(Common.get(Common.checkGet(rs.getString("propertyName"))) ).append( "\"").append(						
					" propertyName=\"").append(Common.get(Common.checkGet(rs.getString("propertyName"))) ).append( "\"").append(	
					" material=\"").append(Common.get(Common.checkGet(rs.getString("material"))) ).append( "\"").append(	
					" propertyUnit=\"").append(Common.get(Common.checkGet(rs.getString("propertyUnit"))) ).append( "\"").append(	
					" limitYear=\"").append(Common.get(Common.checkGet(rs.getString("limitYear"))) ).append( "\"").append(	
					" serialNo=\"").append(Common.get(Common.checkGet(rs.getString("serialNo"))) ).append( "\"").append(
					" otherLimitYear=\"").append(Common.get(Common.checkGet(rs.getString("otherLimitYear"))) ).append( "\"").append(
					" propertyName1=\"").append(Common.get(Common.checkGet(rs.getString("propertyName1"))) ).append( "\"").append(
					" signNo=\"").append(Common.get(Common.checkGet(rs.getString("signNo"))) ).append( "\"").append(
					" signName=\"").append(Common.get(Common.checkGet(rs.getString("signName"))) ).append( "\"").append(				
					" doorPlate1=\"").append(Common.get(Common.checkGet(rs.getString("doorPlate1"))) ).append( "\"").append(
					" doorPlate2=\"").append(Common.get(Common.checkGet(rs.getString("doorPlate2"))) ).append( "\"").append(
					" doorPlate3=\"").append(Common.get(Common.checkGet(rs.getString("doorPlate3"))) ).append( "\"").append(
					" doorPlate4=\"").append(Common.get(Common.checkGet(rs.getString("doorPlate4"))) ).append( "\"").append(
					" doorPlateName=\"").append(Common.get(Common.checkGet(rs.getString("doorPlateName"))) ).append( "\"").append(
					" buildStyle=\"").append(Common.get(Common.checkGet(rs.getString("buildStyle"))) ).append( "\"").append(
					" buildStyleName=\"").append(Common.get(Common.checkGet(rs.getString("buildStyleName"))) ).append( "\"").append(
					" cause=\"").append(Common.get(Common.checkGet(rs.getString("cause"))) ).append( "\"").append(
					" cause1=\"").append(Common.get(Common.checkGet(rs.getString("cause1"))) ).append( "\"").append(
					" enterDate=\"").append(Common.get(Common.checkGet(rs.getString("enterDate"))) ).append( "\"").append(
					" dataState=\"").append(Common.get(Common.checkGet(rs.getString("dataState"))) ).append( "\"").append(
					" reduceDate=\"").append(Common.get(Common.checkGet(rs.getString("reduceDate"))) ).append( "\"").append(
					" reduceCause=\"").append(Common.get(Common.checkGet(rs.getString("reduceCause"))) ).append( "\"").append(
					" reduceCause1=\"").append(Common.get(Common.checkGet(rs.getString("reduceCause1"))) ).append( "\"").append(
					" verify=\"").append(Common.get(Common.checkGet(rs.getString("verify"))) ).append( "\"").append(
					" closing=\"").append(Common.get(Common.checkGet(rs.getString("closing"))) ).append( "\"").append(
					" propertyKind=\"").append(Common.get(Common.checkGet(rs.getString("propertyKind"))) ).append( "\"").append(
					" fundType=\"").append(Common.get(Common.checkGet(rs.getString("fundType"))) ).append( "\"").append(
					" valuable=\"").append(Common.get(Common.checkGet(rs.getString("valuable"))) ).append( "\"").append(
					" taxCredit=\"").append(Common.get(Common.checkGet(rs.getString("taxCredit"))) ).append( "\"").append(
					" originalCArea=\"").append(Common.get(Common.checkGet(rs.getString("originalCArea"))) ).append( "\"").append(
					" originalSArea=\"").append(Common.get(Common.checkGet(rs.getString("originalSArea"))) ).append( "\"").append(
					" originalArea=\"").append(Common.get(Common.checkGet(rs.getString("originalArea"))) ).append( "\"").append(
					" originalHoldNume=\"").append(Common.get(Common.checkGet(rs.getString("originalHoldNume"))) ).append( "\"").append(
					" originalHoldDeno=\"").append(Common.get(Common.checkGet(rs.getString("originalHoldDeno"))) ).append( "\"").append(
					" originalHoldArea=\"").append(Common.get(Common.checkGet(rs.getString("originalHoldArea"))) ).append( "\"").append(
					" CArea=\"").append(Common.get(Common.checkGet(rs.getString("CArea"))) ).append( "\"").append(
					" SArea=\"").append(Common.get(Common.checkGet(rs.getString("SArea"))) ).append( "\"").append(
					" area=\"").append(Common.get(Common.checkGet(rs.getString("area"))) ).append( "\"").append(
					" holdNume=\"").append(Common.get(Common.checkGet(rs.getString("holdNume"))) ).append( "\"").append(
					" holdDeno=\"").append(Common.get(Common.checkGet(rs.getString("holdDeno"))) ).append( "\"").append(
					" holdArea=\"").append(Common.get(Common.checkGet(rs.getString("holdArea"))) ).append( "\"").append(
					" originalBV=\"").append(Common.get(Common.checkGet(rs.getString("originalBV"))) ).append( "\"").append(
					" originalNote=\"").append(Common.get(Common.checkGet(rs.getString("originalNote"))) ).append( "\"").append(
					" accountingTitle=\"").append(Common.get(Common.checkGet(rs.getString("accountingTitle"))) ).append( "\"").append(
					" bookValue=\"").append(Common.get(Common.checkGet(rs.getString("bookValue"))) ).append( "\"").append(
					" fundsSource=\"").append(Common.get(Common.checkGet(rs.getString("fundsSource"))) ).append( "\"").append(
					" ownershipDate=\"").append(Common.get(Common.checkGet(rs.getString("ownershipDate"))) ).append( "\"").append(
					" ownershipCause=\"").append(Common.get(Common.checkGet(rs.getString("ownershipCause"))) ).append( "\"").append(
					" nonProof=\"").append(Common.get(Common.checkGet(rs.getString("nonProof"))) ).append( "\"").append(
					" proofDoc=\"").append(Common.get(Common.checkGet(rs.getString("proofDoc"))) ).append( "\"").append(
					" proofVerify=\"").append(Common.get(Common.checkGet(rs.getString("proofVerify"))) ).append( "\"").append(
					" buildDate=\"").append(Common.get(Common.checkGet(rs.getString("buildDate"))) ).append( "\"").append(
					" floor1=\"").append(Common.get(Common.checkGet(rs.getString("floor1"))) ).append( "\"").append(
					" floor1Name=\"").append(Common.get(Common.checkGet(rs.getString("floor1Name"))) ).append( "\"").append(
					" floor2=\"").append(Common.get(Common.checkGet(rs.getString("floor2"))) ).append( "\"").append(
					" floor2Name=\"").append(Common.get(Common.checkGet(rs.getString("floor2Name"))) ).append( "\"").append(
					" stuff=\"").append(Common.get(Common.checkGet(rs.getString("stuff"))) ).append( "\"").append(
					" ownershipNote=\"").append(Common.get(Common.checkGet(rs.getString("ownershipNote"))) ).append( "\"").append(
					" sourceKind=\"").append(Common.get(Common.checkGet(rs.getString("sourceKind"))) ).append( "\"").append(
					" sourceDate=\"").append(Common.get(Common.checkGet(rs.getString("sourceDate"))) ).append( "\"").append(
					" sourceDoc=\"").append(Common.get(Common.checkGet(rs.getString("sourceDoc"))) ).append( "\"").append(
					" manageOrg=\"").append(Common.get(Common.checkGet(rs.getString("manageOrg"))) ).append( "\"").append(
					" damageDate=\"").append(Common.get(Common.checkGet(rs.getString("damageDate"))) ).append( "\"").append(
					" damageExpire=\"").append(Common.get(Common.checkGet(rs.getString("damageExpire"))) ).append( "\"").append(
					" damageMark=\"").append(Common.get(Common.checkGet(rs.getString("damageMark"))) ).append( "\"").append(
					" deprMethod=\"").append(Common.get(Common.checkGet(rs.getString("deprMethod"))) ).append( "\"").append(
					" scrapValue=\"").append(Common.get(Common.checkGet(rs.getString("scrapValue"))) ).append( "\"").append(
					" deprAmount=\"").append(Common.get(Common.checkGet(rs.getString("deprAmount"))) ).append( "\"").append(
					" apportionYear=\"").append(Common.get(Common.checkGet(rs.getString("apportionYear"))) ).append( "\"").append(
					" monthDepr=\"").append(Common.get(Common.checkGet(rs.getString("monthDepr"))) ).append( "\"").append(
					" useEndYM=\"").append(Common.get(Common.checkGet(rs.getString("useEndYM"))) ).append( "\"").append(
					" apportionEndYM=\"").append(Common.get(Common.checkGet(rs.getString("apportionEndYM"))) ).append( "\"").append(
					" accumDeprYM=\"").append(Common.get(Common.checkGet(rs.getString("accumDeprYM"))) ).append( "\"").append(
					" accumDepr=\"").append(Common.get(Common.checkGet(rs.getString("accumDepr"))) ).append( "\"").append(
					" permitReduceDate=\"").append(Common.get(Common.checkGet(rs.getString("permitReduceDate"))) ).append( "\"").append(
					" useState=\"").append(Common.get(Common.checkGet(rs.getString("useState"))) ).append( "\"").append(
					" useStateName=\"").append(Common.get(Common.checkGet(rs.getString("useStateName"))) ).append( "\"").append(
					" proceedDateS=\"").append(Common.get(Common.checkGet(rs.getString("proceedDateS"))) ).append( "\"").append(
					" proceedDateE=\"").append(Common.get(Common.checkGet(rs.getString("proceedDateE"))) ).append( "\"").append(
					" proceedType=\"").append(Common.get(Common.checkGet(rs.getString("proceedType"))) ).append( "\"").append(
					" proceedTypeName=\"").append(Common.get(Common.checkGet(rs.getString("proceedTypeName"))) ).append( "\"").append(
					" appraiseDate=\"").append(Common.get(Common.checkGet(rs.getString("appraiseDate"))) ).append( "\"").append(
					" notes=\"").append(Common.get(Common.checkGet(rs.getString("notes"))) ).append( "\"").append(
					" oldPropertyNo=\"").append(Common.get(Common.checkGet(rs.getString("oldPropertyNo"))) ).append( "\"").append(
					" oldSerialNo=\"").append(Common.get(Common.checkGet(rs.getString("oldSerialNo"))) ).append( "\"").append(
					" check=\"").append("Y").append( "\"").append(
					" /> ");
		} 
		if(check==""){
			if ("PN".equals(sType)) {
				strXML.append("<record " ).append( 
						" propertyNo=\"").append(Common.get(Common.checkGet(propertyNo)) ).append( "\"").append(
						" propertyNoName=\"").append(Common.get(Common.checkGet(propertyName)) ).append( "\"").append(
						" propertyName=\"").append(Common.get(Common.checkGet(propertyName)) ).append( "\"").append(				
						" material=\"").append("").append( "\"\n").append(
						" propertyUnit=\"").append("").append( "\"\n").append(
						" limitYear=\"").append("").append( "\"\n").append(
						" serialNo=\"").append(Common.get(serialNo) ).append( "\"").append(
						" otherLimitYear=\"").append("").append( "\"\n").append(
						" propertyName1=\"").append("").append( "\"\n").append(
						" signNo=\"").append("").append( "\"\n").append(
						" signName=\"").append("").append( "\"\n").append(
						" doorPlate1=\"").append("").append( "\"\n").append(
						" doorPlate2=\"").append("").append( "\"\n").append(
						" doorPlate3=\"").append("").append( "\"\n").append(
						" doorPlate4=\"").append("").append( "\"\n").append(
						" doorPlateName=\"").append("").append( "\"\n").append(
						" buildStyle=\"").append("").append( "\"\n").append(
						" buildStyleName=\"").append("").append( "\"\n").append(
						" cause=\"").append("").append( "\"\n").append(
						" cause1=\"").append("").append( "\"\n").append(
						" enterDate=\"").append("").append( "\"\n").append(
						" dataState=\"").append("").append( "\"\n").append(
						" reduceDate=\"").append("").append( "\"\n").append(
						" reduceCause=\"").append("").append( "\"\n").append(
						" reduceCause1=\"").append("").append( "\"\n").append(
						" verify=\"").append("").append( "\"\n").append(
						" closing=\"").append("").append( "\"\n").append(
						" propertyKind=\"").append("").append( "\"\n").append(
						" fundType=\"").append("").append( "\"\n").append(
						" valuable=\"").append("").append( "\"\n").append(
						" taxCredit=\"").append("").append( "\"\n").append(
						" originalCArea=\"").append("").append( "\"\n").append(
						" originalSArea=\"").append("").append( "\"\n").append(
						" originalArea=\"").append("").append( "\"\n").append(
						" originalHoldNume=\"").append("").append( "\"\n").append(
						" originalHoldDeno=\"").append("").append( "\"\n").append(
						" originalHoldArea=\"").append("").append( "\"\n").append(
						" CArea=\"").append("").append( "\"\n").append(
						" SArea=\"").append("").append( "\"\n").append(
						" area=\"").append("").append( "\"\n").append(
						" holdNume=\"").append("").append( "\"\n").append(
						" holdDeno=\"").append("").append( "\"\n").append(
						" holdArea=\"").append("").append( "\"\n").append(
						" originalBV=\"").append("").append( "\"\n").append(
						" originalNote=\"").append("").append( "\"\n").append(
						" accountingTitle=\"").append("").append( "\"\n").append(
						" bookValue=\"").append("").append( "\"\n").append(
						" fundsSource=\"").append("").append( "\"\n").append(
						" ownershipDate=\"").append("").append( "\"\n").append(
						" ownershipCause=\"").append("").append( "\"\n").append(
						" nonProof=\"").append("").append( "\"\n").append(
						" proofDoc=\"").append("").append( "\"\n").append(
						" proofVerify=\"").append("").append( "\"\n").append(
						" buildDate=\"").append("").append( "\"\n").append(
						" floor1=\"").append("").append( "\"\n").append(
						" floor1Name=\"").append("").append( "\"\n").append(
						" floor2=\"").append("").append( "\"\n").append(
						" floor2Name=\"").append("").append( "\"\n").append(
						" stuff=\"").append("").append( "\"\n").append(
						" ownershipNote=\"").append("").append( "\"\n").append(
						" sourceKind=\"").append("").append( "\"\n").append(
						" sourceDate=\"").append("").append( "\"\n").append(
						" sourceDoc=\"").append("").append( "\"\n").append(
						" manageOrg=\"").append("").append( "\"\n").append(
						" damageDate=\"").append("").append( "\"\n").append(
						" damageExpire=\"").append("").append( "\"\n").append(
						" damageMark=\"").append("").append( "\"\n").append(
						" deprMethod=\"").append("").append( "\"\n").append(
						" scrapValue=\"").append("").append( "\"\n").append(
						" deprAmount=\"").append("").append( "\"\n").append(
						" apportionYear=\"").append("").append( "\"\n").append(
						" monthDepr=\"").append("").append( "\"\n").append(
						" useEndYM=\"").append("").append( "\"\n").append(
						" apportionEndYM=\"").append("").append( "\"\n").append(
						" accumDeprYM=\"").append("").append( "\"\n").append(
						" accumDepr=\"").append("").append( "\"\n").append(
						" permitReduceDate=\"").append("").append( "\"\n").append(
						" useState=\"").append("").append( "\"\n").append(
						" useStateName=\"").append("").append( "\"\n").append(
						" proceedDateS=\"").append("").append( "\"\n").append(
						" proceedDateE=\"").append("").append( "\"\n").append(
						" proceedType=\"").append("").append( "\"\n").append(
						" proceedTypeName=\"").append("").append( "\"\n").append(
						" appraiseDate=\"").append("").append( "\"\n").append(
						" notes=\"").append("").append( "\"\n").append(
						" oldPropertyNo=\"").append("").append( "\"\n").append(
						" oldSerialNo=\"").append("").append( "\"\n").append(
						" check=\"").append("").append( "\"\n").append(
						" /> ");
			}else{
				strXML.append("<record " ).append( 
						" propertyNo=\"").append("").append( "\"\n").append(
						" propertyNoName=\"").append("").append( "\"\n").append(
						" propertyName=\"").append("").append( "\"\n").append(
						" material=\"").append("").append( "\"\n").append(
						" propertyUnit=\"").append("").append( "\"\n").append(
						" limitYear=\"").append("").append( "\"\n").append(
						" serialNo=\"").append("").append( "\"\n").append(
						" otherLimitYear=\"").append("").append( "\"\n").append(
						" propertyName1=\"").append("").append( "\"\n").append(
						" signNo=\"").append(Common.get(signNo) ).append( "\"").append(
						" signName=\"").append("").append( "\"\n").append(
						" doorPlate1=\"").append("").append( "\"\n").append(
						" doorPlate2=\"").append("").append( "\"\n").append(
						" doorPlate3=\"").append("").append( "\"\n").append(
						" doorPlate4=\"").append("").append( "\"\n").append(
						" doorPlateName=\"").append("").append( "\"\n").append(
						" buildStyle=\"").append("").append( "\"\n").append(
						" buildStyleName=\"").append("").append( "\"\n").append(
						" cause=\"").append("").append( "\"\n").append(
						" cause1=\"").append("").append( "\"\n").append(
						" enterDate=\"").append("").append( "\"\n").append(
						" dataState=\"").append("").append( "\"\n").append(
						" reduceDate=\"").append("").append( "\"\n").append(
						" reduceCause=\"").append("").append( "\"\n").append(
						" reduceCause1=\"").append("").append( "\"\n").append(
						" verify=\"").append("").append( "\"\n").append(
						" closing=\"").append("").append( "\"\n").append(
						" propertyKind=\"").append("").append( "\"\n").append(
						" fundType=\"").append("").append( "\"\n").append(
						" valuable=\"").append("").append( "\"\n").append(
						" taxCredit=\"").append("").append( "\"\n").append(
						" originalCArea=\"").append("").append( "\"\n").append(
						" originalSArea=\"").append("").append( "\"\n").append(
						" originalArea=\"").append("").append( "\"\n").append(
						" originalHoldNume=\"").append("").append( "\"\n").append(
						" originalHoldDeno=\"").append("").append( "\"\n").append(
						" originalHoldArea=\"").append("").append( "\"\n").append(
						" CArea=\"").append("").append( "\"\n").append(
						" SArea=\"").append("").append( "\"\n").append(
						" area=\"").append("").append( "\"\n").append(
						" holdNume=\"").append("").append( "\"\n").append(
						" holdDeno=\"").append("").append( "\"\n").append(
						" holdArea=\"").append("").append( "\"\n").append(
						" originalBV=\"").append("").append( "\"\n").append(
						" originalNote=\"").append("").append( "\"\n").append(
						" accountingTitle=\"").append("").append( "\"\n").append(
						" bookValue=\"").append("").append( "\"\n").append(
						" fundsSource=\"").append("").append( "\"\n").append(
						" ownershipDate=\"").append("").append( "\"\n").append(
						" ownershipCause=\"").append("").append( "\"\n").append(
						" nonProof=\"").append("").append( "\"\n").append(
						" proofDoc=\"").append("").append( "\"\n").append(
						" proofVerify=\"").append("").append( "\"\n").append(
						" buildDate=\"").append("").append( "\"\n").append(
						" floor1=\"").append("").append( "\"\n").append(
						" floor1Name=\"").append("").append( "\"\n").append(
						" floor2=\"").append("").append( "\"\n").append(
						" floor2Name=\"").append("").append( "\"\n").append(
						" stuff=\"").append("").append( "\"\n").append(
						" ownershipNote=\"").append("").append( "\"\n").append(
						" sourceKind=\"").append("").append( "\"\n").append(
						" sourceDate=\"").append("").append( "\"\n").append(
						" sourceDoc=\"").append("").append( "\"\n").append(
						" manageOrg=\"").append("").append( "\"\n").append(
						" damageDate=\"").append("").append( "\"\n").append(
						" damageExpire=\"").append("").append( "\"\n").append(
						" damageMark=\"").append("").append( "\"\n").append(
						" deprMethod=\"").append("").append( "\"\n").append(
						" scrapValue=\"").append("").append( "\"\n").append(
						" deprAmount=\"").append("").append( "\"\n").append(
						" apportionYear=\"").append("").append( "\"\n").append(
						" monthDepr=\"").append("").append( "\"\n").append(
						" useEndYM=\"").append("").append( "\"\n").append(
						" apportionEndYM=\"").append("").append( "\"\n").append(
						" accumDeprYM=\"").append("").append( "\"\n").append(
						" accumDepr=\"").append("").append( "\"\n").append(
						" permitReduceDate=\"").append("").append( "\"\n").append(
						" useState=\"").append("").append( "\"\n").append(
						" useStateName=\"").append("").append( "\"\n").append(
						" proceedDateS=\"").append("").append( "\"\n").append(
						" proceedDateE=\"").append("").append( "\"\n").append(
						" proceedType=\"").append("").append( "\"\n").append(
						" proceedTypeName=\"").append("").append( "\"\n").append(
						" appraiseDate=\"").append("").append( "\"\n").append(
						" notes=\"").append("").append( "\"\n").append(
						" oldPropertyNo=\"").append("").append( "\"\n").append(
						" oldSerialNo=\"").append("").append( "\"\n").append(
						" check=\"").append("").append( "\"\n").append(
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
