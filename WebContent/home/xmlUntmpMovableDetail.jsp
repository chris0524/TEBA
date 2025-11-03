<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common"/>
<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String sSQL = "";

sSQL = "select b.propertyNo," + "\n" +
		" b.serialNo," + "\n" +
		" b.lotNo," + "\n" +
		" c.propertyName," + "\n" +
		" a.propertyName1," + "\n" +
		" a.otherLimitYear," + "\n" +
		" a.buyDate," + "\n" +
		" a.propertyKind," + "\n" +
		" a.fundType," + "\n" +
		" a.valuable," + "\n" +
		" b.bookAmount," + "\n" +
		" decode(a.originalUnit,null,null,b.bookValue) bookUnit," + "\n" +
		" b.bookValue," + "\n" +
		" a.nameplate," + "\n" +
		" a.specification," + "\n" +
		" a.sourceDate," + "\n" +
		" b.moveDate oldMoveDate," + "\n" +
		" b.keepUnit oldKeepUnit," + "\n" +
		" b.keeper oldKeeper," + "\n" +
		" b.useUnit oldUseUnit," + "\n" +
		" b.userNo oldUserNo," + "\n" +
		" b.place oldPlace," + "\n" +
		" (to_char(sysdate,'YYYY')-1911) - substr(buyDate,1,3) useYear," + "\n" +
		" MOD( MONTHS_BETWEEN( to_date(substr(to_char(sysdate,'YYYYMMDD'),1,6)||'01','YYYYMMDD') , to_date(substr(buyDate,1,5)||'01'+19110000,'YYYYMMDD') ) ,12 ) useMonth," + "\n" +
		" b.scrapValue," + "\n" +
		" b.oldPropertyNo," + "\n" +
		" b.oldSerialNo, " + "\n" +
		" a.computerType " + "\n" +
	" from UNTMP_Movable a, UNTMP_MovableDetail b, SYSPK_PropertyCode c " + "\n" +
	" where 1 = 1 " + "\n" +
		" and b.propertyNo	= c.propertyNo " + "\n" +
		" and b.enterOrg 	= " + Common.sqlChar(Common.esapi(enterOrg)) + "\n" +
		" and b.ownership 	= " + Common.sqlChar(Common.esapi(ownership)) + "\n" +
		" and b.propertyNo 	= " + Common.sqlChar(Common.esapi(propertyNo)) + "\n" +
		" and b.serialNo 	= " + Common.sqlChar(Common.esapi(objCommon.formatFrontZero(serialNo,7))) + "\n" +
		" and b.enterOrg	= a.enterOrg " + "\n" +
		" and b.ownership	= a.ownership " + "\n" +
		" and b.propertyNo	= a.propertyNo " + "\n" +
		" and b.lotNo	    = a.lotNo " + "\n" +
		" ";

//System.out.println(sSQL);

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record propertyNo=\""+objCommon.get(Common.checkGet(rs.getString("propertyNo"))) + "\""+
		" serialNo=\""+objCommon.get(Common.checkGet(rs.getString("serialNo"))) + "\""+
		" lotNo=\""+objCommon.get(Common.checkGet(rs.getString("lotNo"))) + "\""+
		" propertyName=\""+objCommon.get(Common.checkGet(rs.getString("propertyName"))) + "\""+
		" propertyName1=\""+objCommon.get(Common.checkGet(rs.getString("propertyName1"))) + "\""+
		" otherLimitYear=\""+objCommon.get(Common.checkGet(rs.getString("otherLimitYear"))) + "\""+
		" buyDate=\""+objCommon.get(Common.checkGet(rs.getString("buyDate"))) + "\""+
		" propertyKind=\""+objCommon.get(Common.checkGet(rs.getString("propertyKind"))) + "\""+
		" fundType=\""+objCommon.get(Common.checkGet(rs.getString("fundType"))) + "\""+
		" valuable=\""+objCommon.get(Common.checkGet(rs.getString("valuable"))) + "\""+
		" bookAmount=\""+objCommon.get(Common.checkGet(rs.getString("bookAmount"))) + "\""+
		" bookUnit=\""+objCommon.get(Common.checkGet(rs.getString("bookUnit"))) + "\""+
		" bookValue=\""+objCommon.get(Common.checkGet(rs.getString("bookValue"))) + "\""+
		" nameplate=\""+objCommon.get(Common.checkGet(rs.getString("nameplate"))) + "\""+
		" specification=\""+objCommon.get(Common.checkGet(rs.getString("specification"))) + "\""+
		" sourceDate=\""+objCommon.get(Common.checkGet(rs.getString("sourceDate"))) + "\""+
		" oldMoveDate=\""+objCommon.get(Common.checkGet(rs.getString("oldMoveDate"))) + "\""+
		" oldKeepUnit=\""+objCommon.get(Common.checkGet(rs.getString("oldKeepUnit"))) + "\""+
		" oldKeeper=\""+objCommon.get(Common.checkGet(rs.getString("oldKeeper"))) + "\""+
		" oldUseUnit=\""+objCommon.get(Common.checkGet(rs.getString("oldUseUnit"))) + "\""+
		" oldUserNo=\""+objCommon.get(Common.checkGet(rs.getString("oldUserNo"))) + "\""+
		" oldPlace=\""+objCommon.get(Common.checkGet(rs.getString("oldPlace"))) + "\""+
		" useYear=\""+objCommon.get(Common.checkGet(rs.getString("useYear"))) + "\""+
		" useMonth=\""+objCommon.get(Common.checkGet(rs.getString("useMonth"))) + "\""+
		" scrapValue=\""+objCommon.get(Common.checkGet(rs.getString("scrapValue"))) + "\""+
		" oldPropertyNo=\""+objCommon.get(Common.checkGet(rs.getString("oldPropertyNo"))) + "\""+
		" oldSerialNo=\""+objCommon.get(Common.checkGet(rs.getString("oldSerialNo"))) + "\""+
		" computerType=\""+objCommon.get(Common.checkGet(rs.getString("computerType"))) + "\""+
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
