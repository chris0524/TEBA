<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common"/>
<%
String enterOrg = request.getParameter("enterOrg");
String ownership = request.getParameter("ownership");
String caseNo = request.getParameter("caseNo");
String propertyNo = request.getParameter("propertyNo");
String serialNo = request.getParameter("serialNo");
String sSQL = "";
String check="";
sSQL =  " select a.serialNo, b.propertyUnit, b.material, b.limitYear, a.otherPropertyUnit, a.otherMaterial, a.otherLimitYear, "+
		" a.lotNo, a.propertyName1 , a.buyDate ,a.reduceDate, "+
		" a.propertyKind , a.fundType , a.valuable , a.accountingTitle ,"+
		" isnull(a.oldBookAmount,'0') as oldBookAmount, isnull(a.oldBookUnit,'0') as oldBookUnit, isnull(a.oldBookValue,'0') as oldBookValue, isnull(a.adjustBookAmount,'0') as adjustBookAmount, isnull(a.adjustBookValue,'0') as adjustBookValue, isnull(a.newBookAmount,'0') as newBookAmount, isnull(a.newBookValue,'0') as newBookValue, "+
		" a.articleName , a.nameplate , a.specification , a.sourceDate ,"+
		" a.licensePlate , a.moveDate , a.keepUnit , a.keeper , a.useUnit ,a.userNo ,a.place , "+
		"(select c.unitName from UNTMP_KeepUnit c where a.enterOrg=c.enterOrg and a.keepUnit=c.unitNo) as keepUnitName, "+
		"(select d.unitName from UNTMP_KeepUnit d where a.enterOrg=d.enterOrg and a.useUnit=d.unitNo) as useUnitName, "+
		"(select e.keeperName from UNTMP_Keeper e where a.enterOrg=e.enterOrg and a.keepUnit=e.keeperno and a.keeper=e.keeperNo) as keeperName, "+		
		"(select f.keeperName from UNTMP_Keeper f where a.enterOrg=f.enterOrg and a.useUnit=f.keeperno and a.userNo=f.keeperNo) as userName, "+		
		" a.useYear, a.useMonth,  "+
		" a.oldPropertyNo , a.oldSerialNo "+
		" from untmp_ReduceDetail a, SYSPK_PropertyCode b "+
		" where a.propertyNo = b.propertyNo and b.propertyType='1'"+
		" and a.verify='Y' and a.dealCaseNo is null and a.cause!='01' "+
		" and a.enterOrg 	= " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and a.ownership 	= " + Common.sqlChar(Common.esapi(ownership)) +
		" and a.caseNo 	    = " + Common.sqlChar(Common.esapi(caseNo)) +
		" and a.propertyNo 	= " + Common.sqlChar(Common.esapi(propertyNo)) +
		" and a.serialNo 	= " + Common.sqlChar(Common.esapi(Common.formatFrontZero(Common.get(serialNo),7))) +
		" " ;
System.out.println("--AJAXsSQL--" + sSQL);
StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		check="Y";
		strXML.append("<record \n" + 
		" serialNo=\""+objCommon.get(rs.getString("serialNo")) + "\"\n"+
		" lotNo=\""+objCommon.get(rs.getString("lotNo")) + "\"\n"+
		" propertyUnit=\""+objCommon.get(rs.getString("propertyUnit")) + "\"\n"+
		" material=\""+objCommon.get(rs.getString("material")) + "\"\n"+		
		" limitYear=\""+objCommon.get(rs.getString("limitYear")) + "\"\n"+
		" otherPropertyUnit=\""+objCommon.get(rs.getString("otherPropertyUnit")) + "\"\n"+
		" otherMaterial=\""+objCommon.get(rs.getString("otherMaterial")) + "\"\n"+
		" otherLimitYear=\""+objCommon.get(rs.getString("otherLimitYear")) + "\"\n"+
		" propertyName1=\""+objCommon.get(rs.getString("propertyName1")) + "\"\n"+
		" buyDate=\""+objCommon.get(rs.getString("buyDate")) + "\"\n"+
		" reduceDate=\""+objCommon.get(rs.getString("reduceDate")) + "\"\n"+
		" propertyKind=\""+objCommon.get(rs.getString("propertyKind")) + "\"\n"+
		" fundType=\""+objCommon.get(rs.getString("fundType")) + "\"\n"+
		" valuable=\""+objCommon.get(rs.getString("valuable")) + "\"\n"+
		" accountingTitle=\""+objCommon.get(rs.getString("accountingTitle")) + "\"\n"+
		" oldBookAmount=\""+objCommon.get(rs.getString("oldBookAmount")) + "\"\n"+
		" oldBookUnit=\""+objCommon.get(rs.getString("oldBookUnit")) + "\"\n"+
		" oldBookValue=\""+objCommon.get(rs.getString("oldBookValue")) + "\"\n"+
		" adjustBookAmount=\""+objCommon.get(rs.getString("adjustBookAmount")) + "\"\n"+
		" adjustBookValue=\""+objCommon.get(rs.getString("adjustBookValue")) + "\"\n"+
		" newBookAmount=\""+objCommon.get(rs.getString("newBookAmount")) + "\"\n"+
		" newBookValue=\""+objCommon.get(rs.getString("newBookValue")) + "\"\n"+
		" articleName=\""+objCommon.get(rs.getString("articleName")) + "\"\n"+
		" nameplate=\""+objCommon.get(rs.getString("nameplate")) + "\"\n"+
		" specification=\""+objCommon.get(rs.getString("specification")) + "\"\n"+
		" sourceDate=\""+objCommon.get(rs.getString("sourceDate")) + "\"\n"+
		" licensePlate=\""+objCommon.get(rs.getString("licensePlate")) + "\"\n"+
		" moveDate=\""+objCommon.get(rs.getString("moveDate")) + "\"\n"+
		" keepUnit=\""+objCommon.get(rs.getString("keepUnit")) + "\"\n"+
		" keepUnitName=\""+objCommon.get(rs.getString("keepUnitName")) + "\"\n"+
		" keeper=\""+objCommon.get(rs.getString("keeper")) + "\"\n"+
		" keeperName=\""+objCommon.get(rs.getString("keeperName")) + "\"\n"+
		" useUnit=\""+objCommon.get(rs.getString("useUnit")) + "\"\n"+
		" useUnitName=\""+objCommon.get(rs.getString("useUnitName")) + "\"\n"+
		" userNo=\""+objCommon.get(rs.getString("userNo")) + "\"\n"+
		" userName=\""+objCommon.get(rs.getString("userName")) + "\"\n"+
		" place=\""+objCommon.get(rs.getString("place")) + "\"\n"+
		" useYear=\""+objCommon.get(rs.getString("useYear")) + "\"\n"+
		" useMonth=\""+objCommon.get(rs.getString("useMonth")) + "\"\n"+
		" oldPropertyNo=\""+objCommon.get(rs.getString("oldPropertyNo")) + "\"\n"+
		" oldSerialNo=\""+objCommon.get(rs.getString("oldSerialNo")) + "\"\n"+
		" check=\""+"Y"+ "\"\n"+
		" /> ");
	}
	if(check==""){
		strXML.append("<record \n" + 
		" serialNo=\""+serialNo+ "\"\n"+
		" lotNo=\""+""+ "\"\n"+
		" propertyUnit=\""+""+ "\"\n"+
		" material=\""+""+ "\"\n"+		
		" limitYear=\""+""+ "\"\n"+
		" otherPropertyUnit=\""+""+ "\"\n"+
		" otherMaterial=\""+""+ "\"\n"+
		" otherLimitYear=\""+""+ "\"\n"+
		" propertyName1=\""+""+ "\"\n"+
		" buyDate=\""+""+ "\"\n"+
		" reduceDate=\""+""+ "\"\n"+
		" propertyKind=\""+""+ "\"\n"+
		" fundType=\""+""+ "\"\n"+
		" valuable=\""+""+ "\"\n"+
		" accountingTitle=\""+""+ "\"\n"+
		" oldBookAmount=\""+""+ "\"\n"+
		" oldBookUnit=\""+""+ "\"\n"+
		" oldBookValue=\""+""+ "\"\n"+
		" adjustBookAmount=\""+""+ "\"\n"+
		" adjustBookValue=\""+""+ "\"\n"+
		" newBookAmount=\""+""+ "\"\n"+
		" newBookValue=\""+""+ "\"\n"+
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
		" useYear=\""+""+ "\"\n"+
		" useMonth=\""+""+ "\"\n"+
		" oldPropertyNo=\""+""+ "\"\n"+
		" oldSerialNo=\""+""+ "\"\n"+
		" check=\""+""+ "\"\n"+
		" /> ");
	}
	//System.out.println("--println Check--"+strXML);
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
