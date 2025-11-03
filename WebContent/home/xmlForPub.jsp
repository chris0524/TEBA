<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<jsp:useBean id="objCommon" scope="page" class="util.Common"/>
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

sSQL = "select a.propertyNo," +
		" b.propertyName," +
		" a.propertyName1," +
		" a.serialNo," +
		" a.dataState," +
		" decode(a.dataState,1,'現存','2','已減損','') as dataStateName," +
		" a.oldPropertyNo," +
		" a.oldSerialNo," +
		" a.propertyKind," +
		" decode(a.propertyKind,01,'公務用','02','公共用','03','事業用','非公用') as propertykindName," +
		" a.fundType," +
		" f.codeName as fundTypeName," +
		" a.valuable," +
		" a.taxCredit," +
		" a.grass," +
		" a.originalBV," +
		" a.area," +
		" a.holdNume," +
		" a.holdDeno," +
		" a.holdArea," +
		" a.accountingTitle," +
		" a.bookUnit," +
		" a.bookValue," +
		" a.useSeparate," +
		" e.codeName as useSeparateName," +
		" a.useKind," +
		" d.codeName as useKindName," +
		" a.proofDoc," +
		" a.field," +
		" c.codeName as fieldName," +
		" a.sourceDate," +
		" a.useState," +
		" g.codeName as useStateName," +
		" a.originalBasis," +
		" a.originalDate," +
		" a.originalUnit," +
		" a.signNo " +
	" from UNTLA_LAND a, SYSPK_PropertyCode b, SYSCA_CODE c, SYSCA_CODE d, SYSCA_CODE e, SYSCA_CODE f, SYSCA_CODE g " +
	" where 1 = 1 " +
		" and a.propertyNo	= b.propertyNo " +
		" and a.enterOrg 	= " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and a.ownership 	= " + Common.sqlChar(Common.esapi(ownership)) +
		" and a.field=c.codeid (+) and c.codekindid (+)='FIE'" +
		" and a.useKind=d.codeid (+) and d.codekindid (+)='UKD'" +
		" and a.useSeparate=e.codeid (+) and e.codekindid (+)='SEP'" +
		" and a.fundType=f.codeid (+) and f.codekindid (+)='FUD'" +
		" and a.useState=g.codeid (+) and g.codekindid (+)='UST'" +
		" and a.dataState='"+Common.esapi(sdataState)+"'"+
		" and a.propertyKind<>'"+Common.esapi(spropertyKind)+"'";

if ("PN".equals(sType)) {
	sSQL+=" and a.propertyNo='" + Common.esapi(propertyNo) + "' and a.serialNo='" + Common.esapi(objCommon.formatFrontZero(serialNo,7)) + "'";
} else {
	String signNo1 = Common.checkGet(request.getParameter("sNo1"));
	String signNo2 = Common.checkGet(request.getParameter("sNo2"));
	String signNo3 = Common.checkGet(request.getParameter("sNo3"));
	String signNo4 = Common.checkGet(request.getParameter("sNo4"));
	String signNo5 = Common.checkGet(request.getParameter("sNo5"));
	String s1, s2, s3, s4="", s5="";	
	s1 = signNo1.substring(0,1);
	s2 = signNo2.substring(1,3);
	s3 = signNo3.substring(3,7);
	if (signNo5!=null && signNo4.trim().length() > 0 ) {
		s4 = objCommon.formatFrontZero(signNo4,4);
	} else {
		s4 = objCommon.formatFrontZero("0",4);
	}
	if (signNo5!=null && signNo5.trim().length() > 0 ) {
		s5 = objCommon.formatFrontZero(signNo5,4);
	} else {
		s5 = objCommon.formatFrontZero("0",4);
	}
	signNo = s1 + s2 + s3 + s4 + s5;

	sSQL+=" and a.signNo='" + Common.esapi(signNo) + "'" +
	      " and a.VERIFY = 'Y'";
}

//objFileOut.Foo(sSQL);

StringBuffer strXML = new StringBuffer();
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {		
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		strXML.append("<record propertyNo=\""+objCommon.get(Common.checkGet(rs.getString("propertyNo"))) + "\""+
		" propertyName=\""+objCommon.get(Common.checkGet(rs.getString("propertyName"))) + "\""+
		" propertyName1=\""+objCommon.get(Common.checkGet(rs.getString("propertyName1"))) + "\""+
		" serialNo=\""+objCommon.get(Common.checkGet(rs.getString("serialNo"))) + "\""+
		" dataState=\""+objCommon.get(Common.checkGet(rs.getString("dataState"))) + "\""+
		" dataStateName=\""+objCommon.get(Common.checkGet(rs.getString("dataStateName"))) + "\""+
		" oldPropertyNo=\""+objCommon.get(Common.checkGet(rs.getString("oldPropertyNo"))) + "\""+
		" oldSerialNo=\""+ objCommon.get(Common.checkGet(rs.getString("oldSerialNo"))) + "\""+
		" propertyKind=\""+ objCommon.get(Common.checkGet(rs.getString("propertyKind"))) + "\""+
		" propertykindName=\""+ objCommon.get(Common.checkGet(rs.getString("propertykindName"))) + "\""+
		" fundType=\""+ objCommon.get(Common.checkGet(rs.getString("fundType"))) + "\""+
		" fundTypeName=\""+ objCommon.get(Common.checkGet(rs.getString("fundTypeName"))) + "\""+
		" valuable=\""+ objCommon.get(Common.checkGet(rs.getString("valuable"))) + "\""+
		" taxCredit=\""+ objCommon.get(Common.checkGet(rs.getString("taxCredit"))) + "\""+
		" grass=\""+ objCommon.get(Common.checkGet(rs.getString("grass"))) + "\""+
		" originalBV=\""+ objCommon.get(Common.checkGet(rs.getString("originalBV"))) + "\""+
		" area=\""+ objCommon.get(Common.checkGet(rs.getString("area"))) + "\""+
		" holdNume=\""+ objCommon.get(Common.checkGet(rs.getString("holdNume"))) + "\""+
		" holdDeno=\""+ objCommon.get(Common.checkGet(rs.getString("holdDeno"))) + "\""+
		" holdArea=\""+ objCommon.get(Common.checkGet(rs.getString("holdArea"))) + "\""+
		" accountingTitle=\""+ objCommon.get(Common.checkGet(rs.getString("accountingTitle"))) + "\""+
		" bookUnit=\""+ objCommon.get(Common.checkGet(rs.getString("bookUnit"))) + "\""+
		" bookValue=\""+ objCommon.get(Common.checkGet(rs.getString("bookValue"))) + "\""+
		" useSeparate=\"" + objCommon.get(Common.checkGet(rs.getString("useSeparate"))) + "\""+
		" useSeparateName=\"" + objCommon.get(Common.checkGet(rs.getString("useSeparateName"))) + "\""+
		" useKind=\"" + objCommon.get(Common.checkGet(rs.getString("useKind"))) + "\""+
		" useKindName=\"" + objCommon.get(Common.checkGet(rs.getString("useKindName"))) + "\""+
		" proofDoc=\"" + objCommon.get(Common.checkGet(rs.getString("proofDoc"))) + "\""+
		" field=\"" + objCommon.get(Common.checkGet(rs.getString("field"))) + "\""+
		" fieldName=\"" + objCommon.get(Common.checkGet(rs.getString("fieldName"))) + "\""+
		" sourceDate=\"" + objCommon.get(Common.checkGet(rs.getString("sourceDate"))) + "\""+
		" useState=\"" + objCommon.get(Common.checkGet(rs.getString("useState"))) + "\""+
		" useStateName=\"" + objCommon.get(Common.checkGet(rs.getString("useStateName"))) + "\""+
		" originalBasis=\"" + objCommon.get(Common.checkGet(rs.getString("originalBasis"))) + "\""+
		" originalDate=\"" + objCommon.get(Common.checkGet(rs.getString("originalDate"))) + "\""+
		" originalUnit=\"" + objCommon.get(Common.checkGet(rs.getString("originalUnit"))) + "\""+
		" signNo=\"" + objCommon.get(Common.checkGet(rs.getString("signNo"))) + "\""+
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
