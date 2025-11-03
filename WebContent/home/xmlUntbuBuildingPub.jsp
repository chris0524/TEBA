<%@ page contentType="application/xml;charset=UTF-8"
	import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common" />
<%
String sType = Common.checkGet(request.getParameter("sType"));
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String sdataState = Common.checkGet(request.getParameter("sdataState"));
String spropertyKind = Common.checkGet(request.getParameter("spropertyKind"));
String signNo = "";
String sSQL = "";
String check="";

String propertyName="";

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
	" a.buildStyle," + 
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
	" a.floor2," + 
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
	" a.proceedDateS," + 
	" a.proceedDateE," + 
	" a.proceedType," + 
	" a.appraiseDate," + 
	" a.notes," + 
	" a.oldPropertyNo," + 
	" a.oldSerialNo" +
	" from UNTBU_Building a, SYSPK_PropertyCode b, SYSCA_Sign c, SYSCA_Organ d where 1=1 and a.dataState='1' and a.verify='Y' " + 
	" and a.propertyNo=b.propertyNo and substr(a.signNo,1,7)=c.signNo and a.enterOrg=d.organID " +
	" and a.enterOrg = " + Common.sqlChar(Common.esapi(enterOrg)) +
	" and a.ownership = " + Common.sqlChar(Common.esapi(ownership)) +
	" and a.dataState='"+Common.esapi(sdataState)+"'" +
	" and a.propertyKind<>'"+Common.esapi(spropertyKind)+"'";

	if ("PN".equals(sType)) {
		propertyNo = Common.get(Common.checkGet(request.getParameter("propertyNo")));
		propertyName = Common.isoToUTF8(Common.checkGet(request.getParameter("propertyName")));
		serialNo = Common.formatFrontZero(Common.checkGet(request.getParameter("serialNo")),7);
		sSQL+=" and a.propertyNo='" + Common.esapi(propertyNo) + "' and a.serialNo='" + Common.esapi(objCommon.formatFrontZero(serialNo,7)) + "'";
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
			s4 = objCommon.formatFrontZero(signNo4,5);
		} else {
			s4 = objCommon.formatFrontZero("0",5);
		}
		if (signNo5!=null && signNo5.trim().length() > 0 ) {
			s5 = objCommon.formatFrontZero(signNo5,3);
		} else {
			s5 = objCommon.formatFrontZero("0",3);
		}
		signNo = s1 + s2 + s3 + s4 + s5;
		sSQL+=" and a.signNo='" + Common.esapi(signNo) + "'" +
			  " and a.VERIFY = 'Y'";;
	}

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
					" enterOrg=\""+objCommon.get(Common.checkGet(rs.getString("enterOrg"))) + "\""+
					" enterOrgName=\""+objCommon.get(Common.checkGet(rs.getString("enterOrgName"))) + "\""+				
					" ownership=\""+objCommon.get(Common.checkGet(rs.getString("ownership"))) + "\""+
					" caseNo=\""+objCommon.get(Common.checkGet(rs.getString("caseNo"))) + "\""+
					" propertyNo=\""+objCommon.get(Common.checkGet(rs.getString("propertyNo"))) + "\""+
					" propertyName=\""+objCommon.get(Common.checkGet(rs.getString("propertyName"))) + "\""+	
					" material=\""+objCommon.get(Common.checkGet(rs.getString("material"))) + "\""+	
					" propertyUnit=\""+objCommon.get(Common.checkGet(rs.getString("propertyUnit"))) + "\""+	
					" limitYear=\""+objCommon.get(Common.checkGet(rs.getString("limitYear"))) + "\""+	
					" serialNo=\""+objCommon.get(Common.checkGet(rs.getString("serialNo"))) + "\""+
					" otherLimitYear=\""+objCommon.get(Common.checkGet(rs.getString("otherLimitYear"))) + "\""+
					" propertyName1=\""+objCommon.get(Common.checkGet(rs.getString("propertyName1"))) + "\""+
					" signNo=\""+objCommon.get(Common.checkGet(rs.getString("signNo"))) + "\""+
					" signName=\""+objCommon.get(Common.checkGet(rs.getString("signName"))) + "\""+				
					" doorPlate1=\""+objCommon.get(Common.checkGet(rs.getString("doorPlate1"))) + "\""+
					" doorPlate2=\""+objCommon.get(Common.checkGet(rs.getString("doorPlate2"))) + "\""+
					" doorPlate3=\""+objCommon.get(Common.checkGet(rs.getString("doorPlate3"))) + "\""+
					" doorPlate4=\""+objCommon.get(Common.checkGet(rs.getString("doorPlate4"))) + "\""+
					" buildStyle=\""+objCommon.get(Common.checkGet(rs.getString("buildStyle"))) + "\""+
					" cause=\""+objCommon.get(Common.checkGet(rs.getString("cause"))) + "\""+
					" cause1=\""+objCommon.get(Common.checkGet(rs.getString("cause1"))) + "\""+
					" enterDate=\""+objCommon.get(Common.checkGet(rs.getString("enterDate"))) + "\""+
					" dataState=\""+objCommon.get(Common.checkGet(rs.getString("dataState"))) + "\""+
					" reduceDate=\""+objCommon.get(Common.checkGet(rs.getString("reduceDate"))) + "\""+
					" reduceCause=\""+objCommon.get(Common.checkGet(rs.getString("reduceCause"))) + "\""+
					" reduceCause1=\""+objCommon.get(Common.checkGet(rs.getString("reduceCause1"))) + "\""+
					" verify=\""+objCommon.get(Common.checkGet(rs.getString("verify"))) + "\""+
					" closing=\""+objCommon.get(Common.checkGet(rs.getString("closing"))) + "\""+
					" propertyKind=\""+objCommon.get(Common.checkGet(rs.getString("propertyKind"))) + "\""+
					" fundType=\""+objCommon.get(Common.checkGet(rs.getString("fundType"))) + "\""+
					" valuable=\""+objCommon.get(Common.checkGet(rs.getString("valuable"))) + "\""+
					" taxCredit=\""+objCommon.get(Common.checkGet(rs.getString("taxCredit"))) + "\""+
					" originalCArea=\""+objCommon.get(Common.checkGet(rs.getString("originalCArea"))) + "\""+
					" originalSArea=\""+objCommon.get(Common.checkGet(rs.getString("originalSArea"))) + "\""+
					" originalArea=\""+objCommon.get(Common.checkGet(rs.getString("originalArea"))) + "\""+
					" originalHoldNume=\""+objCommon.get(Common.checkGet(rs.getString("originalHoldNume"))) + "\""+
					" originalHoldDeno=\""+objCommon.get(Common.checkGet(rs.getString("originalHoldDeno"))) + "\""+
					" originalHoldArea=\""+objCommon.get(Common.checkGet(rs.getString("originalHoldArea"))) + "\""+
					" CArea=\""+objCommon.get(Common.checkGet(rs.getString("CArea")))+ "\""+
					" SArea=\""+objCommon.get(Common.checkGet(rs.getString("SArea"))) + "\""+
					" area=\""+objCommon.get(Common.checkGet(rs.getString("area"))) + "\""+
					" holdNume=\""+objCommon.get(Common.checkGet(rs.getString("holdNume"))) + "\""+
					" holdDeno=\""+objCommon.get(Common.checkGet(rs.getString("holdDeno"))) + "\""+
					" holdArea=\""+objCommon.get(Common.checkGet(rs.getString("holdArea"))) + "\""+
					" originalBV=\""+objCommon.get(Common.checkGet(rs.getString("originalBV"))) + "\""+
					" originalNote=\""+objCommon.get(Common.checkGet(rs.getString("originalNote"))) + "\""+
					" accountingTitle=\""+objCommon.get(Common.checkGet(rs.getString("accountingTitle"))) + "\""+
					" bookValue=\""+objCommon.get(Common.checkGet(rs.getString("bookValue"))) + "\""+
					" fundsSource=\""+objCommon.get(Common.checkGet(rs.getString("fundsSource"))) + "\""+
					" ownershipDate=\""+objCommon.get(Common.checkGet(rs.getString("ownershipDate"))) + "\""+
					" ownershipCause=\""+objCommon.get(Common.checkGet(rs.getString("ownershipCause"))) + "\""+
					" nonProof=\""+objCommon.get(Common.checkGet(rs.getString("nonProof"))) + "\""+
					" proofDoc=\""+objCommon.get(Common.checkGet(rs.getString("proofDoc"))) + "\""+
					" proofVerify=\""+objCommon.get(Common.checkGet(rs.getString("proofVerify"))) + "\""+
					" buildDate=\""+objCommon.get(Common.checkGet(rs.getString("buildDate"))) + "\""+
					" floor1=\""+objCommon.get(Common.checkGet(rs.getString("floor1"))) + "\""+
					" floor2=\""+objCommon.get(Common.checkGet(rs.getString("floor2"))) + "\""+
					" stuff=\""+objCommon.get(Common.checkGet(rs.getString("stuff"))) + "\""+
					" ownershipNote=\""+objCommon.get(Common.checkGet(rs.getString("ownershipNote"))) + "\""+
					" sourceKind=\""+objCommon.get(Common.checkGet(rs.getString("sourceKind"))) + "\""+
					" sourceDate=\""+objCommon.get(Common.checkGet(rs.getString("sourceDate"))) + "\""+
					" sourceDoc=\""+objCommon.get(Common.checkGet(rs.getString("sourceDoc"))) + "\""+
					" manageOrg=\""+objCommon.get(Common.checkGet(rs.getString("manageOrg"))) + "\""+
					" damageDate=\""+objCommon.get(Common.checkGet(rs.getString("damageDate"))) + "\""+
					" damageExpire=\""+objCommon.get(Common.checkGet(rs.getString("damageExpire"))) + "\""+
					" damageMark=\""+objCommon.get(Common.checkGet(rs.getString("damageMark"))) + "\""+
					" deprMethod=\""+objCommon.get(Common.checkGet(rs.getString("deprMethod"))) + "\""+
					" scrapValue=\""+objCommon.get(Common.checkGet(rs.getString("scrapValue"))) + "\""+
					" deprAmount=\""+objCommon.get(Common.checkGet(rs.getString("deprAmount"))) + "\""+
					" apportionYear=\""+objCommon.get(Common.checkGet(rs.getString("apportionYear"))) + "\""+
					" monthDepr=\""+objCommon.get(Common.checkGet(rs.getString("monthDepr"))) + "\""+
					" useEndYM=\""+objCommon.get(Common.checkGet(rs.getString("useEndYM"))) + "\""+
					" apportionEndYM=\""+objCommon.get(Common.checkGet(rs.getString("apportionEndYM"))) + "\""+
					" accumDeprYM=\""+objCommon.get(Common.checkGet(rs.getString("accumDeprYM"))) + "\""+
					" accumDepr=\""+objCommon.get(Common.checkGet(rs.getString("accumDepr"))) + "\""+
					" permitReduceDate=\""+objCommon.get(Common.checkGet(rs.getString("permitReduceDate"))) + "\""+
					" useState=\""+objCommon.get(Common.checkGet(rs.getString("useState"))) + "\""+
					" proceedDateS=\""+objCommon.get(Common.checkGet(rs.getString("proceedDateS"))) + "\""+
					" proceedDateE=\""+objCommon.get(Common.checkGet(rs.getString("proceedDateE"))) + "\""+
					" proceedType=\""+objCommon.get(Common.checkGet(rs.getString("proceedType"))) + "\""+
					" appraiseDate=\""+objCommon.get(Common.checkGet(rs.getString("appraiseDate"))) + "\""+
					" notes=\""+objCommon.get(Common.checkGet(rs.getString("notes"))) + "\""+
					" oldPropertyNo=\""+objCommon.get(Common.checkGet(rs.getString("oldPropertyNo"))) + "\""+
					" oldSerialNo=\""+objCommon.get(Common.checkGet(rs.getString("oldSerialNo"))) + "\""+
					" check=\""+"Y"+ "\""+
					" /> ");
		} 
		if(check==""){
			if ("PN".equals(sType)) {
				strXML.append("<record " + 
						" propertyNo=\""+objCommon.get(Common.checkGet(propertyNo)) + "\""+
						" propertyName=\""+objCommon.get(Common.checkGet(propertyName)) + "\""+				
						" material=\""+""+ "\"\n"+
						" propertyUnit=\""+""+ "\"\n"+
						" limitYear=\""+""+ "\"\n"+
						" serialNo=\""+objCommon.get(Common.checkGet(serialNo)) + "\""+
						" otherLimitYear=\""+""+ "\"\n"+
						" propertyName1=\""+""+ "\"\n"+
						" signNo=\""+""+ "\"\n"+
						" signName=\""+""+ "\"\n"+
						" doorPlate1=\""+""+ "\"\n"+
						" doorPlate2=\""+""+ "\"\n"+
						" doorPlate3=\""+""+ "\"\n"+
						" doorPlate4=\""+""+ "\"\n"+
						" buildStyle=\""+""+ "\"\n"+
						" cause=\""+""+ "\"\n"+
						" cause1=\""+""+ "\"\n"+
						" enterDate=\""+""+ "\"\n"+
						" dataState=\""+""+ "\"\n"+
						" reduceDate=\""+""+ "\"\n"+
						" reduceCause=\""+""+ "\"\n"+
						" reduceCause1=\""+""+ "\"\n"+
						" verify=\""+""+ "\"\n"+
						" closing=\""+""+ "\"\n"+
						" propertyKind=\""+""+ "\"\n"+
						" fundType=\""+""+ "\"\n"+
						" valuable=\""+""+ "\"\n"+
						" taxCredit=\""+""+ "\"\n"+
						" originalCArea=\""+""+ "\"\n"+
						" originalSArea=\""+""+ "\"\n"+
						" originalArea=\""+""+ "\"\n"+
						" originalHoldNume=\""+""+ "\"\n"+
						" originalHoldDeno=\""+""+ "\"\n"+
						" originalHoldArea=\""+""+ "\"\n"+
						" CArea=\""+""+ "\"\n"+
						" SArea=\""+""+ "\"\n"+
						" area=\""+""+ "\"\n"+
						" holdNume=\""+""+ "\"\n"+
						" holdDeno=\""+""+ "\"\n"+
						" holdArea=\""+""+ "\"\n"+
						" originalBV=\""+""+ "\"\n"+
						" originalNote=\""+""+ "\"\n"+
						" accountingTitle=\""+""+ "\"\n"+
						" bookValue=\""+""+ "\"\n"+
						" fundsSource=\""+""+ "\"\n"+
						" ownershipDate=\""+""+ "\"\n"+
						" ownershipCause=\""+""+ "\"\n"+
						" nonProof=\""+""+ "\"\n"+
						" proofDoc=\""+""+ "\"\n"+
						" proofVerify=\""+""+ "\"\n"+
						" buildDate=\""+""+ "\"\n"+
						" floor1=\""+""+ "\"\n"+
						" floor2=\""+""+ "\"\n"+
						" stuff=\""+""+ "\"\n"+
						" ownershipNote=\""+""+ "\"\n"+
						" sourceKind=\""+""+ "\"\n"+
						" sourceDate=\""+""+ "\"\n"+
						" sourceDoc=\""+""+ "\"\n"+
						" manageOrg=\""+""+ "\"\n"+
						" damageDate=\""+""+ "\"\n"+
						" damageExpire=\""+""+ "\"\n"+
						" damageMark=\""+""+ "\"\n"+
						" deprMethod=\""+""+ "\"\n"+
						" scrapValue=\""+""+ "\"\n"+
						" deprAmount=\""+""+ "\"\n"+
						" apportionYear=\""+""+ "\"\n"+
						" monthDepr=\""+""+ "\"\n"+
						" useEndYM=\""+""+ "\"\n"+
						" apportionEndYM=\""+""+ "\"\n"+
						" accumDeprYM=\""+""+ "\"\n"+
						" accumDepr=\""+""+ "\"\n"+
						" permitReduceDate=\""+""+ "\"\n"+
						" useState=\""+""+ "\"\n"+
						" proceedDateS=\""+""+ "\"\n"+
						" proceedDateE=\""+""+ "\"\n"+
						" proceedType=\""+""+ "\"\n"+
						" appraiseDate=\""+""+ "\"\n"+
						" notes=\""+""+ "\"\n"+
						" oldPropertyNo=\""+""+ "\"\n"+
						" oldSerialNo=\""+""+ "\"\n"+
						" check=\""+""+ "\"\n"+
						" /> ");
			}else{
				strXML.append("<record " + 
						" propertyNo=\""+""+ "\"\n"+
						" propertyName=\""+""+ "\"\n"+
						" material=\""+""+ "\"\n"+
						" propertyUnit=\""+""+ "\"\n"+
						" limitYear=\""+""+ "\"\n"+
						" serialNo=\""+""+ "\"\n"+
						" otherLimitYear=\""+""+ "\"\n"+
						" propertyName1=\""+""+ "\"\n"+
						" signNo=\""+objCommon.get(Common.checkGet(signNo)) + "\""+
						" signName=\""+""+ "\"\n"+
						" doorPlate1=\""+""+ "\"\n"+
						" doorPlate2=\""+""+ "\"\n"+
						" doorPlate3=\""+""+ "\"\n"+
						" doorPlate4=\""+""+ "\"\n"+
						" buildStyle=\""+""+ "\"\n"+
						" cause=\""+""+ "\"\n"+
						" cause1=\""+""+ "\"\n"+
						" enterDate=\""+""+ "\"\n"+
						" dataState=\""+""+ "\"\n"+
						" reduceDate=\""+""+ "\"\n"+
						" reduceCause=\""+""+ "\"\n"+
						" reduceCause1=\""+""+ "\"\n"+
						" verify=\""+""+ "\"\n"+
						" closing=\""+""+ "\"\n"+
						" propertyKind=\""+""+ "\"\n"+
						" fundType=\""+""+ "\"\n"+
						" valuable=\""+""+ "\"\n"+
						" taxCredit=\""+""+ "\"\n"+
						" originalCArea=\""+""+ "\"\n"+
						" originalSArea=\""+""+ "\"\n"+
						" originalArea=\""+""+ "\"\n"+
						" originalHoldNume=\""+""+ "\"\n"+
						" originalHoldDeno=\""+""+ "\"\n"+
						" originalHoldArea=\""+""+ "\"\n"+
						" CArea=\""+""+ "\"\n"+
						" SArea=\""+""+ "\"\n"+
						" area=\""+""+ "\"\n"+
						" holdNume=\""+""+ "\"\n"+
						" holdDeno=\""+""+ "\"\n"+
						" holdArea=\""+""+ "\"\n"+
						" originalBV=\""+""+ "\"\n"+
						" originalNote=\""+""+ "\"\n"+
						" accountingTitle=\""+""+ "\"\n"+
						" bookValue=\""+""+ "\"\n"+
						" fundsSource=\""+""+ "\"\n"+
						" ownershipDate=\""+""+ "\"\n"+
						" ownershipCause=\""+""+ "\"\n"+
						" nonProof=\""+""+ "\"\n"+
						" proofDoc=\""+""+ "\"\n"+
						" proofVerify=\""+""+ "\"\n"+
						" buildDate=\""+""+ "\"\n"+
						" floor1=\""+""+ "\"\n"+
						" floor2=\""+""+ "\"\n"+
						" stuff=\""+""+ "\"\n"+
						" ownershipNote=\""+""+ "\"\n"+
						" sourceKind=\""+""+ "\"\n"+
						" sourceDate=\""+""+ "\"\n"+
						" sourceDoc=\""+""+ "\"\n"+
						" manageOrg=\""+""+ "\"\n"+
						" damageDate=\""+""+ "\"\n"+
						" damageExpire=\""+""+ "\"\n"+
						" damageMark=\""+""+ "\"\n"+
						" deprMethod=\""+""+ "\"\n"+
						" scrapValue=\""+""+ "\"\n"+
						" deprAmount=\""+""+ "\"\n"+
						" apportionYear=\""+""+ "\"\n"+
						" monthDepr=\""+""+ "\"\n"+
						" useEndYM=\""+""+ "\"\n"+
						" apportionEndYM=\""+""+ "\"\n"+
						" accumDeprYM=\""+""+ "\"\n"+
						" accumDepr=\""+""+ "\"\n"+
						" permitReduceDate=\""+""+ "\"\n"+
						" useState=\""+""+ "\"\n"+
						" proceedDateS=\""+""+ "\"\n"+
						" proceedDateE=\""+""+ "\"\n"+
						" proceedType=\""+""+ "\"\n"+
						" appraiseDate=\""+""+ "\"\n"+
						" notes=\""+""+ "\"\n"+
						" oldPropertyNo=\""+""+ "\"\n"+
						" oldSerialNo=\""+""+ "\"\n"+
						" check=\""+""+ "\"\n"+
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
