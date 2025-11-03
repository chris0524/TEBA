<%@page import="java.net.URLDecoder"%>
<%@page import="org.json.simple.*"%>
<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String propertyNoName = Common.isoToUTF8(URLDecoder.decode(Common.checkGet(request.getParameter("propertyNoName")), "UTF-8"));
String serialNo = Common.formatFrontZero(Common.checkGet(request.getParameter("serialNo")),7);
String differenceKind = Common.checkGet(request.getParameter("differenceKind"));
String sSQL = "";
String check="";

sSQL =  " select a.lotNo, b.propertyName1 , a.enterDate , a.buyDate , "+
		" a.propertyKind , a.fundType , a.valuable , a.accountingTitle ,"+
		" b.bookAmount , b.originalBV , b.bookValue ,"+
		" a.articleName , a.nameplate , a.specification , a.sourceDate ,"+
		" b.licensePlate , b.moveDate , b.keepUnit , b.keeper , b.useUnit , "+
		" b.userNo , b.place,"+
		" b.oldPropertyNo , b.oldSerialNo, "+
		" a.differenceKind,a.caseSerialNo,b.userNote,b.place1,"+
		" a.OriginalUnit , b.bookValue, c.propertyName, c.material, a.otherMaterial, c.propertyUnit, a.otherPropertyUnit, c.limitYear, a.otherLimitYear, "+
		" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.keepUnit=f.unitNo) as keepUnitName, "+
		" (select f.unitName from UNTMP_KeepUnit f where b.enterOrg=f.enterOrg and b.useUnit=f.unitNo) as useUnitName, "+
		" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.keeper=f.keeperNo) as keeperName, "+		
		" (select f.keeperName from UNTMP_Keeper f where b.enterOrg=f.enterOrg and b.userNo=f.keeperNo) as userName, "+
		" (select f.placeName from SYSCA_Place f where b.enterOrg=f.enterOrg and b.place1=f.placeNo) as oldPlaceName "+
		" from untne_Nonexp a , untne_nonexpDetail b , syspk_propertycode2 c " + "\n" +
		" where a.dataState='1' and a.verify='Y' and b.dataState='1' and b.verify='Y' " + 
		" and a.enterOrg = b.enterOrg "+
		" and a.ownership = b.ownership"+
		" and a.propertyno = b.propertyno "+
		" and a.lotno = b.lotno "+
		" and a.differenceKind = b.differenceKind "+
		" and a.enterOrg = c.enterOrg "+
		" and a.propertyno = c.propertyno " +
		" and b.enterOrg 	= " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and b.ownership 	= " + Common.sqlChar(Common.esapi(ownership)) +
		" and b.propertyNo 	= " + Common.sqlChar(Common.esapi(propertyNo)) +
		" and b.serialNo 	= " + Common.sqlChar(Common.esapi(Common.formatFrontZero(Common.get(serialNo),7))) +
		" and b.differenceKind 	= " + Common.sqlChar(Common.esapi(differenceKind)) +
		" " ;
		
	//System.out.println(sSQL);
// 	StringBuffer strXML = new StringBuffer();
// 	strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
// 	strXML.append("<ResultSet>");
	Database db = new Database();
try {		
	JSONObject item = new JSONObject();
	JSONArray dsField = new JSONArray();
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()) {
		item.put("check", "Y");
		item.put("propertyNo", Common.checkGet(propertyNo));
		item.put("propertyNoName", Common.checkGet(rs.getString("propertyName")));
		item.put("serialNo", Common.checkGet(serialNo));
		item.put("lotNo", Common.checkGet(rs.getString("lotNo")));
		item.put("material", Common.checkGet(rs.getString("material")));
		item.put("otherMaterial", Common.checkGet(rs.getString("otherMaterial")));
		item.put("propertyUnit", Common.checkGet(rs.getString("propertyUnit")));
		item.put("otherPropertyUnit", Common.checkGet(rs.getString("otherPropertyUnit")));
		item.put("limitYear", Common.checkGet(rs.getString("limitYear")));
		item.put("otherLimitYear", Common.checkGet(rs.getString("otherLimitYear")));
		item.put("propertyName1", Common.checkGet(rs.getString("propertyName1")));
		item.put("enterDate", Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("enterDate"),"1")));
		item.put("buyDate", Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"),"1")));
		item.put("propertyKind", Common.checkGet(rs.getString("propertyKind")));
		item.put("fundType", Common.checkGet(rs.getString("fundType")));
		item.put("valuable", Common.checkGet(rs.getString("valuable")));
		item.put("accountingTitle", Common.checkGet(rs.getString("accountingTitle")));
		item.put("originalBV", Common.checkGet(rs.getString("originalBV")));
		item.put("bookAmount", Common.checkGet(rs.getString("bookAmount")));
		item.put("bookValue", Common.checkGet(rs.getString("bookValue")));
		item.put("articleName", Common.checkGet(rs.getString("articleName")));
		item.put("nameplate", Common.checkGet(rs.getString("nameplate")));
		item.put("specification", Common.checkGet(rs.getString("specification")));
		item.put("sourceDate", Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"),"1")));
		item.put("licensePlate", Common.checkGet(rs.getString("licensePlate")));
		item.put("moveDate", Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("moveDate"),"1")));
		item.put("keepUnit", Common.checkGet(rs.getString("keepUnit")));
		item.put("keepUnitName", Common.checkGet(rs.getString("keepUnitName")));
		item.put("keeper", Common.checkGet(rs.getString("keeper")));
		item.put("keeperName", Common.checkGet(rs.getString("keeperName")));
		item.put("useUnit", Common.checkGet(rs.getString("useUnit")));
		item.put("useUnitName", Common.checkGet(rs.getString("useUnitName")));
		item.put("userNo", Common.checkGet(rs.getString("userNo")));
		item.put("userName", Common.checkGet(rs.getString("userName")));
		item.put("place", Common.checkGet(rs.getString("place")));
		item.put("oldPropertyNo", Common.checkGet(rs.getString("oldPropertyNo")));
		item.put("oldSerialNo", Common.checkGet(rs.getString("oldSerialNo")));
		item.put("OriginalUnit", Common.checkGet(rs.getString("OriginalUnit")));
		item.put("differenceKind", Common.checkGet(rs.getString("differenceKind")));
		item.put("caseSerialNo", Common.checkGet(rs.getString("caseSerialNo")));
		item.put("userNote", Common.checkGet(rs.getString("userNote")));
		item.put("place1", Common.checkGet(rs.getString("place1")));
		item.put("oldPlaceName", Common.checkGet(rs.getString("oldPlaceName")));
		dsField.add(item);
		
		/** 停用
		check="Y";
		strXML.append("<record \n" + 
					" propertyNo=\""+Common.checkGet(propertyNo) + "\""+
					" propertyNoName=\""+Common.checkGet(rs.getString("propertyName")) + "\""+				
					" serialNo=\""+Common.checkGet(serialNo) + "\""+
					" lotNo=\""+Common.checkGet(rs.getString("lotNo")) + "\"\n"+
					" material=\""+Common.checkGet(rs.getString("material")) + "\"\n"+
					" otherMaterial=\""+Common.checkGet(rs.getString("otherMaterial")) + "\"\n"+
					" propertyUnit=\""+Common.checkGet(rs.getString("propertyUnit")) + "\"\n"+
					" otherPropertyUnit=\""+Common.checkGet(rs.getString("otherPropertyUnit")) + "\"\n"+
					" limitYear=\""+Common.checkGet(rs.getString("limitYear")) + "\"\n"+
					" otherLimitYear=\""+Common.checkGet(rs.getString("otherLimitYear")) + "\"\n"+
					" propertyName1=\""+Common.checkGet(rs.getString("propertyName1")) + "\"\n"+
					" enterDate=\""+Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("enterDate"),"1")) + "\"\n"+
					" buyDate=\""+Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"),"1")) + "\"\n"+
					" propertyKind=\""+Common.checkGet(rs.getString("propertyKind")) + "\"\n"+
					" fundType=\""+Common.checkGet(rs.getString("fundType")) + "\"\n"+
					" valuable=\""+Common.checkGet(rs.getString("valuable")) + "\"\n"+
					" accountingTitle=\""+Common.checkGet(rs.getString("accountingTitle")) + "\"\n"+
					" originalBV=\""+Common.checkGet(rs.getString("originalBV")) + "\"\n"+
					" bookAmount=\""+Common.checkGet(rs.getString("bookAmount")) + "\"\n"+
					" bookValue=\""+Common.checkGet(rs.getString("bookValue")) + "\"\n"+
					" articleName=\""+Common.checkGet(rs.getString("articleName")) + "\"\n"+
					" nameplate=\""+Common.checkGet(rs.getString("nameplate")) + "\"\n"+
					//" specification=\""+Common.checkGet(rs.getString("specification")) + "\"\n"+
					" specification=\""+Common.checkGet(rs.getString("specification")).replaceAll("\"","") + "\"\n"+
					" sourceDate=\""+Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"),"1")) + "\"\n"+
					" licensePlate=\""+Common.checkGet(rs.getString("licensePlate")) + "\"\n"+
					" moveDate=\""+Common.checkGet(Datetime.changeTaiwanYYMMDD(rs.getString("moveDate"),"1")) + "\"\n"+
					" keepUnit=\""+Common.checkGet(rs.getString("keepUnit")) + "\"\n"+
					" keepUnitName=\""+Common.checkGet(rs.getString("keepUnitName")) + "\"\n"+
					" keeper=\""+Common.checkGet(rs.getString("keeper")) + "\"\n"+
					" keeperName=\""+Common.checkGet(rs.getString("keeperName")) + "\"\n"+
					" useUnit=\""+Common.checkGet(rs.getString("useUnit")) + "\"\n"+
					" useUnitName=\""+Common.checkGet(rs.getString("useUnitName")) + "\"\n"+
					" userNo=\""+Common.checkGet(rs.getString("userNo")) + "\"\n"+
					" userName=\""+Common.checkGet(rs.getString("userName")) + "\"\n"+
					" place=\""+Common.checkGet(rs.getString("place")) + "\"\n"+
					" oldPropertyNo=\""+Common.checkGet(rs.getString("oldPropertyNo")) + "\"\n"+
					" oldSerialNo=\""+Common.checkGet(rs.getString("oldSerialNo")) + "\"\n"+
					" OriginalUnit=\""+Common.checkGet(rs.getString("OriginalUnit")) + "\"\n"+
					" differenceKind=\""+Common.checkGet(rs.getString("differenceKind")) + "\"\n"+
					" caseSerialNo=\""+Common.checkGet(rs.getString("caseSerialNo")) + "\"\n"+
					" userNote=\""+Common.checkGet(rs.getString("userNote")) + "\"\n"+
					" place1=\""+Common.checkGet(rs.getString("place1")) + "\"\n"+
					" oldPlaceName=\""+Common.checkGet(rs.getString("oldPlaceName")) + "\"\n"+
					" check=\""+"Y"+ "\""+
					" /> ");
		*/
	}
	
	/** 停用
	if(check==""){
		strXML.append("<record \n" + 
					" propertyNo=\""+Common.checkGet(propertyNo) + "\""+
					" propertyNoName=\""+Common.checkGet(propertyNoName) + "\""+				
					" serialNo=\""+Common.checkGet(serialNo) + "\""+
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
					" oldPropertyNo=\""+""+ "\"\n"+
					" oldSerialNo=\""+""+ "\"\n"+
					" OriginalUnit=\""+""+ "\"\n"+
					" differenceKind=\""+""+ "\"\n"+
					" caseSerialNo=\""+""+ "\"\n"+
					" userNote=\""+""+ "\"\n"+
					" place1=\""+""+ "\"\n"+
					" oldPlaceName=\""+""+ "\"\n"+
					" check=\""+""+ "\"\n"+
					" /> ");
	}
	*/
	out.write(dsField.toString());
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

// strXML.append("</ResultSet>");
//System.out.println(strXML.toString());
//strXML暫停使用
// out.write(strXML.toString());
%>
