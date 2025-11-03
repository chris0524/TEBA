<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
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
String differenceKind = "";
String propertyNo="";
String propertyNoName="";
String serialNo="";
String signNo1="";
String signNo2="";
String signNo3="";
String signNo4="";
String signNo5="";

sSQL = "select a.propertyNo," +
		" b.propertyName," +
		" a.propertyName1," +
		" a.serialNo," +
		" a.dataState," +
		" case a.dataState when 1 then '現存' when '2' then '已減損' else '' end as dataStateName," +
		" a.oldPropertyNo," +
		" a.oldSerialNo," +
		" a.propertyKind," +
		" case a.propertyKind when 01 then '公務用' when '02' then '公共用' when '03' then '事業用' else '非公用' end  as propertykindName," +
		" a.fundType," +
		" (select f.codeName from SYSCA_CODE f where a.fundType = f.codeid and f.codekindid ='FUD') as fundTypeName, "+
		" a.valuable," +
		" a.taxCredit," +
		//" a.grass," +
		//" case a.grass when 'Y' then '是' when 'N' then '否' else '' end as grassName," +
		" a.originalBV," +
		" a.area," +
		" a.holdNume," +
		" a.holdDeno," +
		" a.holdArea," +
		" a.accountingTitle," +
		" a.bookUnit," +
		" a.bookValue," +
		" a.useSeparate," +
		" (select e.codeName from SYSCA_CODE e where a.useSeparate = e.codeid and e.codekindid ='SEP') as useSeparateName, "+
		" a.useKind," +
		" (select d.codeName from SYSCA_CODE d where a.useKind = d.codeid and d.codekindid ='UKD') as useKindName, "+
		" a.proofDoc," +
		" a.ownershipDate," +
		" a.field," +
		" (select c.codeName from SYSCA_CODE c where a.fundType = c.codeid and c.codekindid ='FIE') as fieldName, "+
		" a.sourceDate," +
		" a.useState," +
		" case a.useState1 when '01' then '空置' when '02' then '建物' when '03' then '農作' when '04' then '其他' end as useState1Name," +
		" a.useState1,"+
		" (select g.codeName from SYSCA_CODE g where a.useState = g.codeid and g.codekindid ='UST') as useStateName, "+
		" a.originalBasis," +
		" a.originalDate," +
		" a.originalUnit," +
		" a.signNo, " +
		//" a.proceedType, " +
		//" h.codeName as proceedTypeName,"+
		//" a.proceedDateS, " +
		//" a.proceedDateE, " +
		" a.doorplate "+
	" from UNTLA_LAND a, SYSPK_PropertyCode b " +
	" where 1 = 1 " +
		" and a.propertyNo	= b.propertyNo " +
		" and a.enterOrg 	= " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and a.ownership 	= " + Common.sqlChar(Common.esapi(ownership));
		//" and a.field=c.codeid  and c.codekindid ='FIE'" +
		//" and a.useKind=d.codeid  and d.codekindid ='UKD'" +
		//" and a.useSeparate=e.codeid  and e.codekindid ='SEP'" +
		//" and a.fundType=f.codeid  and f.codekindid ='FUD'" +
		//" and a.useState=g.codeid  and g.codekindid ='UST'";
		//" and a.proceedType=h.codeid  and h.codekindid ='PRO'" +	
		

if (!"".equals(Common.get(dataState))) {
	sSQL += " and a.dataState=" + Common.sqlChar(Common.esapi(dataState));
}else{
	sSQL += " and a.dataState='1' ";
}
if (!"".equals(Common.get(verify))) {
	sSQL += " and a.verify=" + Common.sqlChar(Common.esapi(verify));
}else{
	sSQL += " and a.verify='Y' ";
}

//if (!"".equals(Common.get(closing))) sSQL += " and a.closing=" + Common.sqlChar(closing);
if (!"".equals(Common.get(proofVerify))) sSQL += " and a.proofVerify=" + Common.sqlChar(Common.esapi(proofVerify));

if ("PN".equals(sType)) {
	differenceKind = Common.checkGet(request.getParameter("differenceKind"));
	propertyNo = Common.checkGet(request.getParameter("propertyNo"));
	propertyNoName = Common.isoToUTF8(Common.checkGet(request.getParameter("propertyNoName")));
	serialNo = Common.formatFrontZero(Common.checkGet(request.getParameter("serialNo")),7);
	sSQL+=" and a.differenceKind='" + Common.esapi(differenceKind) + "' and a.propertyNo='" + Common.esapi(propertyNo) + "' and a.serialNo='" +Common.esapi(Common.formatFrontZero(serialNo,7)) + "'";
}else{
	signNo1 = Common.checkGet(request.getParameter("sNo1"));
	signNo2 = Common.checkGet(request.getParameter("sNo2"));
	signNo3 = Common.checkGet(request.getParameter("sNo3"));
	signNo4 = Common.formatFrontZero(Common.checkGet(request.getParameter("sNo4")),4);
	signNo5 = Common.formatFrontZero(Common.checkGet(request.getParameter("sNo5")),4);
	String s1, s2, s3, s4="", s5="";	
	s1 = signNo1.substring(0,1);
	s2 = signNo2.substring(1,3);
	s3 = signNo3.substring(3,7);
	if (signNo5!=null && signNo4.trim().length() > 0 ) {
		s4 = Common.formatFrontZero(signNo4,4);
	} else {
		s4 = Common.formatFrontZero("0",4);
	}
	if (signNo5!=null && signNo5.trim().length() > 0 ) {
		s5 = Common.formatFrontZero(signNo5,4);
	} else {
		s5 = Common.formatFrontZero("0",4);
	}
	signNo = s1 + s2 + s3 + s4 + s5;

	sSQL+=" and a.signNo='" + Common.esapi(signNo) + "'";
}

//System.out.println("sSQL----"+sSQL);
StringBuffer strXML = new StringBuffer(1000);
strXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
strXML.append("<ResultSet>");
Database db = new Database();
try {			
	ResultSet rs = db.querySQL(sSQL);
	while (rs.next()){
		check="Y";
		strXML.append("<record propertyNo=\"").append(Common.get(Common.checkGet(rs.getString("propertyNo"))) ).append( "\"").append(
		" propertyName=\"").append(Common.get(Common.checkGet(rs.getString("propertyName"))) ).append( "\"").append(				
		" propertyNoName=\"").append(Common.get(Common.checkGet(rs.getString("propertyName"))) ).append( "\"").append(
		" propertyName1=\"").append(Common.get(Common.checkGet(rs.getString("propertyName1"))) ).append( "\"").append(
		" serialNo=\"").append(Common.get(Common.checkGet(rs.getString("serialNo"))) ).append( "\"").append(
		" dataState=\"").append(Common.get(Common.checkGet(rs.getString("dataState"))) ).append( "\"").append(
		" dataStateName=\"").append(Common.get(Common.checkGet(rs.getString("dataStateName"))) ).append( "\"").append(
		" oldPropertyNo=\"").append(Common.get(Common.checkGet(rs.getString("oldPropertyNo"))) ).append( "\"").append(
		" oldSerialNo=\"").append( Common.get(Common.checkGet(rs.getString("oldSerialNo"))) ).append( "\"").append(
		" propertyKind=\"").append( Common.get(Common.checkGet(rs.getString("propertyKind"))) ).append( "\"").append(
		" propertykindName=\"").append( Common.get(Common.checkGet(rs.getString("propertykindName"))) ).append( "\"").append(
		" fundType=\"").append( Common.get(Common.checkGet(rs.getString("fundType"))) ).append( "\"").append(
		" fundTypeName=\"").append( Common.get(Common.checkGet(rs.getString("fundTypeName"))) ).append( "\"").append(
		" valuable=\"").append( Common.get(Common.checkGet(rs.getString("valuable"))) ).append( "\"").append(
		" taxCredit=\"").append( Common.get(Common.checkGet(rs.getString("taxCredit"))) ).append( "\"").append(
		//" grass=\"").append( Common.get(rs.getString("grass")) ).append( "\"").append(
		//" grassName=\"").append( Common.get(rs.getString("grassName")) ).append( "\"").append(
		" originalBV=\"").append( Common.get(Common.checkGet(rs.getString("originalBV"))) ).append( "\"").append(
		" area=\"").append( Common.get(Common.checkGet(rs.getString("area"))) ).append( "\"").append(
		" holdNume=\"").append( Common.get(Common.checkGet(rs.getString("holdNume"))) ).append( "\"").append(
		" holdDeno=\"").append( Common.get(Common.checkGet(rs.getString("holdDeno"))) ).append( "\"").append(
		" holdArea=\"").append( Common.get(Common.checkGet(rs.getString("holdArea"))) ).append( "\"").append(
		" accountingTitle=\"").append( Common.get(Common.checkGet(rs.getString("accountingTitle"))) ).append( "\"").append(
		" bookUnit=\"").append( Common.get(Common.checkGet(rs.getString("bookUnit"))) ).append( "\"").append(
		" bookValue=\"").append( Common.get(Common.checkGet(rs.getString("bookValue"))) ).append( "\"").append(
		" useSeparate=\"" ).append( Common.get(Common.checkGet(rs.getString("useSeparate"))) ).append( "\"").append(
		" useSeparateName=\"" ).append( Common.get(Common.checkGet(rs.getString("useSeparateName"))) ).append( "\"").append(
		" useKind=\"" ).append( Common.get(Common.checkGet(rs.getString("useKind"))) ).append( "\"").append(
		" useKindName=\"" ).append( Common.get(Common.checkGet(rs.getString("useKindName"))) ).append( "\"").append(
		" proofDoc=\"" ).append( Common.get(Common.checkGet(rs.getString("proofDoc"))) ).append( "\"").append(
		" ownershipDate=\"" ).append( Common.get(Common.checkGet(rs.getString("ownershipDate"))) ).append( "\"").append(
		" field=\"" ).append( Common.get(Common.checkGet(rs.getString("field"))) ).append( "\"").append(
		" fieldName=\"" ).append( Common.get(Common.checkGet(rs.getString("fieldName"))) ).append( "\"").append(
		" sourceDate=\"" ).append( Common.get(Common.checkGet(rs.getString("sourceDate"))) ).append( "\"").append(
		" useState=\"" ).append( Common.get(Common.checkGet(rs.getString("useState"))) ).append( "\"").append(
		" useState1Name=\"" ).append( Common.get(Common.checkGet(rs.getString("useState1Name"))) ).append( "\"").append(
		" useState1=\"" ).append( Common.get(Common.checkGet(rs.getString("useState1"))) ).append( "\"").append(
		" useStateName=\"" ).append( Common.get(Common.checkGet(rs.getString("useStateName"))) ).append( "\"").append(
		" originalBasis=\"" ).append( Common.get(Common.checkGet(rs.getString("originalBasis"))) ).append( "\"").append(
		" originalDate=\"" ).append( Common.get(Common.checkGet(rs.getString("originalDate"))) ).append( "\"").append(
		" originalUnit=\"" ).append( Common.get(Common.checkGet(rs.getString("originalUnit"))) ).append( "\"").append(
		" signNo=\"" ).append( Common.get(Common.checkGet(rs.getString("signNo"))) ).append( "\"").append(
		//" proceedType=\"" ).append( Common.get(rs.getString("proceedType")) ).append( "\"").append(
		//" proceedTypeName=\"" ).append( Common.get(rs.getString("proceedTypeName")) ).append( "\"").append(
		//" proceedDateS=\"" ).append( Common.get(rs.getString("proceedDateS")) ).append( "\"").append(
		//" proceedDateE=\"" ).append( Common.get(rs.getString("proceedDateE")) ).append( "\"").append(		
		" doorplate=\"" ).append( Common.get(Common.checkGet(rs.getString("doorplate"))) ).append( "\"").append(				
		" check=\"").append("Y").append( "\"").append(
		" /> ");
	} 
	if(check==""){
		if ("PN".equals(sType)) {
			strXML.append("<record " ).append(
				" propertyNo=\"").append(Common.get(Common.checkGet(propertyNo)) ).append( "\"").append(
				" propertyName=\"").append(Common.get(Common.checkGet(propertyNoName)) ).append( "\"").append(
				" propertyNoName=\"").append(Common.get(Common.checkGet(propertyNoName)) ).append( "\"").append(
				" serialNo=\"").append(Common.get(Common.checkGet(serialNo)) ).append( "\"").append(
				" propertyName1=\"").append("").append( "\"\n").append(
				" dataState=\"").append("").append( "\"\n").append(
				" dataStateName=\"").append("").append( "\"\n").append(
				" oldPropertyNo=\"").append("").append( "\"\n").append(
				" oldSerialNo=\"").append("").append( "\"\n").append(
				" propertyKind=\"").append("").append( "\"\n").append(
				" propertykindName=\"").append("").append( "\"\n").append(
				" fundType=\"").append("").append( "\"\n").append(
				" fundTypeName=\"").append("").append( "\"\n").append(
				" valuable=\"").append("").append( "\"\n").append(
				" taxCredit=\"").append("").append( "\"\n").append(
				//" grass=\"").append("").append( "\"\n").append(
				//" grassName=\"").append("").append( "\"\n").append(
				" originalBV=\"").append("").append( "\"\n").append(
				" area=\"").append("").append( "\"\n").append(
				" holdNume=\"").append("").append( "\"\n").append(
				" holdDeno=\"").append("").append( "\"\n").append(
				" holdArea=\"").append("").append( "\"\n").append(
				" accountingTitle=\"").append("").append( "\"\n").append(
				" bookUnit=\"").append("").append( "\"\n").append(
				" bookValue=\"").append("").append( "\"\n").append(
				" useSeparate=\"").append("").append( "\"\n").append(
				" useSeparateName=\"").append("").append( "\"\n").append(
				" useKind=\"").append("").append( "\"\n").append(
				" useKindName=\"").append("").append( "\"\n").append(
				" proofDoc=\"").append("").append( "\"\n").append(
				" ownershipDate=\"").append("").append( "\"\n").append(
				" field=\"").append("").append( "\"\n").append(
				" fieldName=\"").append("").append( "\"\n").append(
				" sourceDate=\"").append("").append( "\"\n").append(
				" useState=\"").append("").append( "\"\n").append(
				" useState1Name=\"").append("").append( "\"\n").append(
				" useState1=\"").append("").append( "\"\n").append(
				" useStateName=\"").append("").append( "\"\n").append(
				" originalBasis=\"").append("").append( "\"\n").append(
				" originalDate=\"").append("").append( "\"\n").append(
				" originalUnit=\"").append("").append( "\"\n").append(
				" signNo=\"").append("").append( "\"\n").append(
				//" proceedType=\"").append("").append( "\"\n").append(
				//" proceedTypeName=\"").append("").append( "\"\n").append(
				//" proceedDateS=\"").append("").append( "\"\n").append(
				//" proceedDateE=\"").append("").append( "\"\n").append(
				" doorplate=\"").append("").append( "\"\n").append(
				" check=\"").append("").append( "\"\n").append(
				" /> ");
		}else{
			strXML.append("<record " ).append(
				" propertyNo=\"").append("").append( "\"\n").append(
				" propertyName=\"").append("").append( "\"\n").append(
				" propertyNoName=\"").append("").append( "\"\n").append(
				" serialNo=\"").append("").append( "\"\n").append(
				" propertyName1=\"").append("").append( "\"\n").append(
				" dataState=\"").append("").append( "\"\n").append(
				" dataStateName=\"").append("").append( "\"\n").append(
				" oldPropertyNo=\"").append("").append( "\"\n").append(
				" oldSerialNo=\"").append("").append( "\"\n").append(
				" propertyKind=\"").append("").append( "\"\n").append(
				" propertykindName=\"").append("").append( "\"\n").append(
				" fundType=\"").append("").append( "\"\n").append(
				" fundTypeName=\"").append("").append( "\"\n").append(
				" valuable=\"").append("").append( "\"\n").append(
				" taxCredit=\"").append("").append( "\"\n").append(
				//" grass=\"").append("").append( "\"\n").append(
				//" grassName=\"").append("").append( "\"\n").append(
				" originalBV=\"").append("").append( "\"\n").append(
				" area=\"").append("").append( "\"\n").append(
				" holdNume=\"").append("").append( "\"\n").append(
				" holdDeno=\"").append("").append( "\"\n").append(
				" holdArea=\"").append("").append( "\"\n").append(
				" accountingTitle=\"").append("").append( "\"\n").append(
				" bookUnit=\"").append("").append( "\"\n").append(
				" bookValue=\"").append("").append( "\"\n").append(
				" useSeparate=\"").append("").append( "\"\n").append(
				" useSeparateName=\"").append("").append( "\"\n").append(
				" useKind=\"").append("").append( "\"\n").append(
				" useKindName=\"").append("").append( "\"\n").append(
				" proofDoc=\"").append("").append( "\"\n").append(
				" ownershipDate=\"").append("").append( "\"\n").append(
				" field=\"").append("").append( "\"\n").append(
				" fieldName=\"").append("").append( "\"\n").append(
				" sourceDate=\"").append("").append( "\"\n").append(
				" useState=\"").append("").append( "\"\n").append(
				" useState1Name=\"").append("").append( "\"\n").append(
				" useState1=\"").append("").append( "\"\n").append(
				" useStateName=\"").append("").append( "\"\n").append(
				" originalBasis=\"").append("").append( "\"\n").append(
				" originalDate=\"").append("").append( "\"\n").append(
				" originalUnit=\"").append("").append( "\"\n").append(
				" signNo=\"").append(Common.get(signNo)).append( "\"\n").append(
				//" proceedType=\"").append("").append( "\"\n").append(
				//" proceedTypeName=\"").append("").append( "\"\n").append(
				//" proceedDateS=\"").append("").append( "\"\n").append(
				//" proceedDateE=\"").append("").append( "\"\n").append(				
				" doorplate=\"").append("").append( "\"\n").append(
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
