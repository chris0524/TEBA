<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common"/>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String propertyNoName = Common.isoToUTF8(Common.checkGet(request.getParameter("propertyNoName")));
String serialNo = Common.formatFrontZero(Common.checkGet(request.getParameter("serialNo")),7);
String sSQL = "";
String check="";
sSQL =  " select a.lotNo, a.propertyName1 , a.enterDate , a.buyDate , "+
		" a.propertyKind , a.fundType , a.valuable , a.accountingTitle ,"+
		" b.bookAmount , b.originalBV , b.bookValue ,"+
		" a.articleName , a.nameplate , a.specification , a.sourceDate ,"+
		" b.licensePlate , b.moveDate , b.keepUnit , b.keeper , b.useUnit , "+
		" b.userNo , b.place , a.deprMethod , b.scrapValue , b.deprAmount , "+
		" b.apportionYear , b.monthDepr , a.useEndYM , a.apportionEndYM , "+
		" b.accumDeprYM , b.accumDepr , a.permitReduceDate ,"+
		" b.oldPropertyNo , b.oldSerialNo, a.computerType, "+
		" a.OriginalUnit , b.bookValue, c.propertyName, c.material, a.otherMaterial, c.propertyUnit, a.otherPropertyUnit, c.limitYear, a.otherLimitYear, "+
		" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.keepUnit=f.unitNo) as keepUnitName, "+
		" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.useUnit=f.unitNo) as useUnitName, "+
		" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.keepUnit=f.unitNo and b.keeper=f.keeperNo) as keeperName, "+		
		" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.useUnit=f.unitNo and b.userNo=f.keeperNo) as userName "+		
		" from untmp_movable a , untmp_movableDetail b , syspk_propertycode c "+
		" where a.dataState='1' and a.verify='Y' and b.dataState='1' and b.verify='Y' "+
		" and a.enterOrg = b.enterOrg "+
		" and a.ownership = b.ownership"+
		" and a.propertyno = b.propertyno "+
		" and a.lotno = b.lotno "+
		" and a.propertyno = c.propertyno(+) " +
		" and b.enterOrg 	= " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and b.ownership 	= " + Common.sqlChar(Common.esapi(ownership)) +
		" and b.propertyNo 	= " + Common.sqlChar(Common.esapi(propertyNo)) +
		" and b.serialNo 	= " + Common.sqlChar(Common.esapi(Common.formatFrontZero(Common.get(serialNo),7))) +
		" and a.enterOrg = b.enterOrg "+
		" and a.ownership = b.ownership"+
		" and a.propertyno = b.propertyno "+
		" and a.lotno = b.lotno "+		
		" " ;
	StringBuffer strXML = new StringBuffer();
	//System.out.println(sSQL);
	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	strXML.append("<ResultSet>");
	Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		check="Y";
		strXML.append("<record \n" + 
					" propertyNo=\""+objCommon.get(propertyNo) + "\""+
					" propertyNoName=\""+objCommon.get(Common.checkGet(rs.getString("propertyName"))) + "\""+				
					" serialNo=\""+objCommon.get(serialNo) + "\""+
					" lotNo=\""+objCommon.get(Common.checkGet(rs.getString("lotNo"))) + "\"\n"+
					" material=\""+objCommon.get(Common.checkGet(rs.getString("material"))) + "\"\n"+
					" otherMaterial=\""+objCommon.get(Common.checkGet(rs.getString("otherMaterial"))) + "\"\n"+
					" propertyUnit=\""+objCommon.get(Common.checkGet(rs.getString("propertyUnit"))) + "\"\n"+
					" otherPropertyUnit=\""+objCommon.get(Common.checkGet(rs.getString("otherPropertyUnit"))) + "\"\n"+
					" limitYear=\""+objCommon.get(Common.checkGet(rs.getString("limitYear"))) + "\"\n"+
					" otherLimitYear=\""+objCommon.get(Common.checkGet(rs.getString("otherLimitYear"))) + "\"\n"+
					" propertyName1=\""+objCommon.get(Common.checkGet(rs.getString("propertyName1"))) + "\"\n"+
					" enterDate=\""+objCommon.get(Common.checkGet(rs.getString("enterDate"))) + "\"\n"+
					" buyDate=\""+objCommon.get(Common.checkGet(rs.getString("buyDate"))) + "\"\n"+
					" propertyKind=\""+objCommon.get(Common.checkGet(rs.getString("propertyKind"))) + "\"\n"+
					" fundType=\""+objCommon.get(Common.checkGet(rs.getString("fundType"))) + "\"\n"+
					" valuable=\""+objCommon.get(Common.checkGet(rs.getString("valuable"))) + "\"\n"+
					" accountingTitle=\""+objCommon.get(Common.checkGet(rs.getString("accountingTitle"))) + "\"\n"+
					" originalBV=\""+objCommon.get(Common.checkGet(rs.getString("originalBV"))) + "\"\n"+
					" bookAmount=\""+objCommon.get(Common.checkGet(rs.getString("bookAmount"))) + "\"\n"+
					" bookValue=\""+objCommon.get(Common.checkGet(rs.getString("bookValue"))) + "\"\n"+
					" articleName=\""+objCommon.get(Common.checkGet(rs.getString("articleName"))) + "\"\n"+
					" nameplate=\""+objCommon.get(Common.checkGet(rs.getString("nameplate"))) + "\"\n");
			
		if(objCommon.get(rs.getString("specification")).equals("")){
			strXML.append(" specification=\""+""+ "\"\n");	
		}else{
			strXML.append(" specification=\""+objCommon.get(Common.checkGet(rs.getString("specification"))).replaceAll("&","%26") + "\"\n");
		}
		
		strXML.append(			
					" sourceDate=\""+objCommon.get(Common.checkGet(rs.getString("sourceDate"))) + "\"\n"+
					" licensePlate=\""+objCommon.get(Common.checkGet(rs.getString("licensePlate"))) + "\"\n"+
					" moveDate=\""+objCommon.get(Common.checkGet(rs.getString("moveDate"))) + "\"\n"+
					" keepUnit=\""+objCommon.get(Common.checkGet(rs.getString("keepUnit"))) + "\"\n"+
					" keepUnitName=\""+objCommon.get(Common.checkGet(rs.getString("keepUnitName"))) + "\"\n"+
					" keeper=\""+objCommon.get(Common.checkGet(rs.getString("keeper"))) + "\"\n"+

					" keeperName=\""+objCommon.get(Common.checkGet(rs.getString("keeperName"))) + "\"\n"+
					" useUnit=\""+objCommon.get(Common.checkGet(rs.getString("useUnit"))) + "\"\n"+
					" useUnitName=\""+objCommon.get(Common.checkGet(rs.getString("useUnitName"))) + "\"\n"+					
					" userNo=\""+objCommon.get(Common.checkGet(rs.getString("userNo"))) + "\"\n"+
					" userName=\""+objCommon.get(Common.checkGet(rs.getString("userName"))) + "\"\n"+
					
					" place=\""+objCommon.get(Common.checkGet(rs.getString("place"))) + "\"\n"+
					" deprMethod=\""+objCommon.get(Common.checkGet(rs.getString("deprMethod"))) + "\"\n"+
					" scrapValue=\""+objCommon.get(Common.checkGet(rs.getString("scrapValue"))) + "\"\n"+
					" deprAmount=\""+objCommon.get(Common.checkGet(rs.getString("deprAmount"))) + "\"\n"+
					" apportionYear=\""+objCommon.get(Common.checkGet(rs.getString("apportionYear"))) + "\"\n"+
					" monthDepr=\""+objCommon.get(Common.checkGet(rs.getString("monthDepr"))) + "\"\n"+
					" useEndYM=\""+objCommon.get(Common.checkGet(rs.getString("useEndYM"))) + "\"\n"+
					" apportionEndYM=\""+objCommon.get(Common.checkGet(rs.getString("apportionEndYM"))) + "\"\n"+
					" accumDeprYM=\""+objCommon.get(Common.checkGet(rs.getString("accumDeprYM"))) + "\"\n"+
					" accumDepr=\""+objCommon.get(Common.checkGet(rs.getString("accumDepr"))) + "\"\n"+
					
					" permitReduceDate=\""+objCommon.get(Common.checkGet(rs.getString("permitReduceDate"))) + "\"\n"+
					" oldPropertyNo=\""+objCommon.get(Common.checkGet(rs.getString("oldPropertyNo"))) + "\"\n"+
					" oldSerialNo=\""+objCommon.get(Common.checkGet(rs.getString("oldSerialNo"))) + "\"\n"+
					" OriginalUnit=\""+objCommon.get(Common.checkGet(rs.getString("OriginalUnit"))) + "\"\n"+
					" computerType=\""+objCommon.get(Common.checkGet(rs.getString("computerType"))) + "\"\n"+
					" check=\""+"Y"+ "\""+
					" /> ");
	} 
	
	//System.out.println("=> "+strXML);
	
	if(check==""){
		strXML.append("<record \n" + 
					" propertyNo=\""+objCommon.get(propertyNo) + "\""+
					" propertyNoName=\""+objCommon.get(propertyNoName) + "\""+				
					" serialNo=\""+objCommon.get(serialNo) + "\""+
					" lotNo=\""+""+ "\"\n"+
					" material=\""+""+ "\"\n"+
					" otherMaterial=\""+""+ "\"\n"+
					" propertyUnit=\""+""+ "\"\n"+
					" otherPropertyUnit=\""+""+ "\"\n"+
					" limitYear=\""+""+ "\"\n"+
					" otherLimitYear=\""+""+ "\"\n"+
					" propertyName1=\""+""+ "\"\n"+
					" enterDate=\""+""+ "\"\n"+
					" buyDate=\""+""+ "\"\n"+
					" propertyKind=\""+""+ "\"\n"+
					" fundType=\""+""+ "\"\n"+
					" valuable=\""+""+ "\"\n"+
					" accountingTitle=\""+""+ "\"\n"+
					" originalBV=\""+""+ "\"\n"+
					" bookAmount=\""+""+ "\"\n"+
					" bookValue=\""+""+ "\"\n"+
					" articleName=\""+""+ "\"\n"+
					" nameplate=\""+""+ "\"\n"+
					" specification=\""+""+ "\"\n"+
					" sourceDate=\""+""+ "\"\n"+
					" licensePlate=\""+""+ "\"\n"+
					" moveDate=\""+""+ "\"\n"+
					" keepUnit=\""+""+ "\"\n"+
					" keepUnitName=\""+""+ "\"\n"+
					" keeper=\""+""+ "\"\n"+
					" keeperName=\""+""+ "\"\n"+
					" useUnit=\""+""+ "\"\n"+
					" useUnitName=\""+""+ "\"\n"+
					" userNo=\""+""+ "\"\n"+
					" userName=\""+""+ "\"\n"+
					" place=\""+""+ "\"\n"+
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
					" oldPropertyNo=\""+""+ "\"\n"+
					" oldSerialNo=\""+""+ "\"\n"+
					" OriginalUnit=\""+""+ "\"\n"+
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
