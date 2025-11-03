<%@ page contentType="application/xml;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="../../home/head.jsp" %>
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
String propertyNoName="";
String serialNo="";
String signNo1="";
String signNo2="";
String signNo3="";
String signNo4="";
String signNo5="";

sSQL = "select a.*"
		+ " , (select z.propertyName from SYSPK_PropertyCode z where a.propertyNo = z.propertyNo) as propertyName";
		

String sqlStr =	" , (case a.propertyKind when 01 then '公務用' when '02' then '公共用' when '03' then '事業用' else '非公用' end) as propertykindName" +
				" , (select z.codeName from SYSCA_CODE z where a.fundType = z.codeid and z.codekindid ='FUD') as fundTypeName";


propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String checkStr1 = propertyNo.substring(0,1);
String checkStr3 = propertyNo.substring(0,3);

if("111".equals(checkStr3)){
	sSQL += sqlStr
			+ " from"
			+ " UNTRF_ATTACHMENT a";
	
}else if("1".equals(checkStr1)){
	sSQL += sqlStr
			+ " from"
			+ " UNTLA_LAND a";
	
}else if("2".equals(checkStr1)){
	sSQL += sqlStr
			+ " from" +
			" UNTBU_BUILDING a";
				
}else if("3".equals(checkStr1) || "4".equals(checkStr1) || "5".equals(checkStr1)){
	sSQL += sqlStr	
		+ " ,(select z.originalKeepUnit from UNTMP_MOVABLEDetail z where a.enterorg = z.enterorg and a.ownership = z.ownership and a.propertyno = z.propertyno and a.lotno = z.lotno) as originalKeepUnit"
			 	+ " ,(select z.originalKeeper from UNTMP_MOVABLEDetail z where a.enterorg = z.enterorg and a.ownership = z.ownership and a.propertyno = z.propertyno and a.lotno = z.lotno) as originalKeeper"
				+ " ,(select z.originalUseUnit from UNTMP_MOVABLEDetail z where a.enterorg = z.enterorg and a.ownership = z.ownership and a.propertyno = z.propertyno and a.lotno = z.lotno) as originalUseUnit"
				+ " ,(select z.originalUser from UNTMP_MOVABLEDetail z where a.enterorg = z.enterorg and a.ownership = z.ownership and a.propertyno = z.propertyno and a.lotno = z.lotno) as originalUser"
				+ " from"
				+ " UNTMP_MOVABLE a";
	
}else if("8".equals(checkStr1)){													
	sSQL += " from" +
			" UNTRT_ADDDETAIL a";
	
}else if("9".equals(checkStr1)){	
	sSQL += " from" +
			" UNTVP_SHARE a";
	
}

	sSQL += " where 1 = 1 " +		
		" and a.enterOrg = " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and a.ownership 	= " + Common.sqlChar(Common.esapi(ownership)) +	
		"";

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

if (!"".equals(Common.get(closing))) sSQL += " and a.closing=" + Common.sqlChar(Common.esapi(closing));
if (!"".equals(Common.get(proofVerify))) sSQL += " and a.proofVerify=" + Common.sqlChar(Common.esapi(proofVerify));

if ("PN".equals(sType)) {
	propertyNo = Common.checkGet(request.getParameter("propertyNo"));
	propertyNoName = Common.checkGet(request.getParameter("propertyNoName"));
	serialNo = Common.formatFrontZero(Common.checkGet(request.getParameter("serialNo")),7);
	sSQL+=" and a.propertyNo='" + Common.esapi(propertyNo) + "'";
	
	if("3".equals(checkStr1) || "4".equals(checkStr1) || "5".equals(checkStr1)){
		sSQL+=" and a.serialNoS='" + Common.esapi(Common.formatFrontZero(serialNo,7)) + "'";
	}else{
		sSQL+=" and a.serialNo='" + Common.esapi(Common.formatFrontZero(serialNo,7)) + "'";
	}
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
		strXML.append("<record propertyNo=\"").append(Common.checkGet(rs.getString("propertyNo")) ).append( "\"").append(
			" propertyName=\"").append(Common.checkGet(rs.getString("propertyName")) ).append( "\"").append(
			"");
		
		if("111".equals(checkStr3)){
			strXML.append(" propertyName1=\"").append(Common.checkGet(rs.getString("propertyName1")) ).append( "\"");
			strXML.append(" propertyKind=\"").append( Common.checkGet(rs.getString("propertyKind")) ).append( "\"");
			strXML.append(" propertykindName=\"").append( Common.checkGet(rs.getString("propertykindName")) ).append( "\"");
			strXML.append(" oldPropertyNo=\"").append(Common.checkGet(rs.getString("oldpropertyNo")) ).append( "\"");
			strXML.append(" sourceDate=\"").append( Common.checkGet(rs.getString("sourceDate")) ).append( "\"");
			strXML.append(" fundType=\"").append( Common.checkGet(rs.getString("fundType")) ).append( "\"");
			strXML.append(" fundTypeName=\"").append( Common.checkGet(rs.getString("fundTypeName")) ).append( "\"");
			strXML.append(" valuable=\"").append( Common.checkGet(rs.getString("valuable")) ).append( "\"");
			strXML.append(" originalBV=\"").append( Common.checkGet(rs.getString("originalBV")) ).append( "\"");				
			strXML.append(" bookValue=\"").append( Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			strXML.append(" oldBookValue=\"").append(Common.checkGet(rs.getString("originalBV")) ).append( "\"");
			
			strXML.append(" serialNo=\"").append(Common.checkGet(rs.getString("serialNo")) ).append( "\"");
			strXML.append(" measure=\"").append( Common.checkGet(rs.getString("measure")) ).append( "\"");			
			strXML.append(" taxCredit=\"").append( Common.checkGet(rs.getString("taxCredit")) ).append( "\"");
			strXML.append(" oldSerialNo=\"").append(Common.checkGet(rs.getString("oldserialNo")) ).append( "\"");
			strXML.append(" buildDate=\"").append(Common.checkGet(rs.getString("buildDate")) ).append( "\"");
			strXML.append(" permitReduceDate=\"").append(Common.checkGet(rs.getString("permitReduceDate")) ).append( "\"");
			
		}else if("1".equals(checkStr1)){
			strXML.append(" propertyName1=\"").append(Common.checkGet(rs.getString("propertyName1")) ).append( "\"");
			strXML.append(" propertyKind=\"").append( Common.checkGet(rs.getString("propertyKind")) ).append( "\"");
			strXML.append(" propertykindName=\"").append( Common.checkGet(rs.getString("propertykindName")) ).append( "\"");
			strXML.append(" oldPropertyNo=\"").append(Common.checkGet(rs.getString("oldpropertyNo")) ).append( "\"");
			strXML.append(" sourceDate=\"").append( Common.checkGet(rs.getString("sourceDate")) ).append( "\"");
			strXML.append(" fundType=\"").append( Common.checkGet(rs.getString("fundType")) ).append( "\"");
			strXML.append(" fundTypeName=\"").append( Common.checkGet(rs.getString("fundTypeName")) ).append( "\"");
			strXML.append(" valuable=\"").append( Common.checkGet(rs.getString("valuable")) ).append( "\"");
			strXML.append(" originalBV=\"").append( Common.checkGet(rs.getString("originalBV")) ).append( "\"");				
			strXML.append(" bookValue=\"").append( Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			
			strXML.append(" serialNo=\"").append(Common.checkGet(rs.getString("serialNo")) ).append( "\"");
			strXML.append(" bookUnit=\"").append( Common.checkGet(rs.getString("bookUnit")) ).append( "\"");
			strXML.append(" signNo=\"" ).append( Common.checkGet(rs.getString("signNo")) ).append( "\"");	
			strXML.append(" area=\"").append( Common.checkGet(rs.getString("area")) ).append( "\"");
			strXML.append(" holdNume=\"").append( Common.checkGet(rs.getString("holdNume")) ).append( "\"");
			strXML.append(" holdDeno=\"").append( Common.checkGet(rs.getString("holdDeno")) ).append( "\"");
			strXML.append(" holdArea=\"").append( Common.checkGet(rs.getString("holdArea")) ).append( "\"");
			strXML.append(" taxCredit=\"").append( Common.checkGet(rs.getString("taxCredit")) ).append( "\"");
			strXML.append(" oldSerialNo=\"").append(Common.checkGet(rs.getString("oldserialNo")) ).append( "\"");
			strXML.append(" oldBookValue=\"").append(Common.checkGet(rs.getString("originalBV")) ).append( "\"");
			
			strXML.append(" proofDoc=\"").append(Common.checkGet(rs.getString("proofDoc")) ).append( "\"");
			
			
		}else if("2".equals(checkStr1)){			
			strXML.append(" propertyName1=\"").append(Common.checkGet(rs.getString("propertyName1")) ).append( "\"");
			strXML.append(" propertyKind=\"").append( Common.checkGet(rs.getString("propertyKind")) ).append( "\"");
			strXML.append(" propertykindName=\"").append( Common.checkGet(rs.getString("propertykindName")) ).append( "\"");
			strXML.append(" oldPropertyNo=\"").append(Common.checkGet(rs.getString("oldpropertyNo")) ).append( "\"");
			strXML.append(" sourceDate=\"").append( Common.checkGet(rs.getString("sourceDate")) ).append( "\"");
			strXML.append(" fundType=\"").append( Common.checkGet(rs.getString("fundType")) ).append( "\"");
			strXML.append(" fundTypeName=\"").append( Common.checkGet(rs.getString("fundTypeName")) ).append( "\"");
			strXML.append(" valuable=\"").append( Common.checkGet(rs.getString("valuable")) ).append( "\"");
			strXML.append(" originalBV=\"").append( Common.checkGet(rs.getString("originalBV")) ).append( "\"");				
			strXML.append(" bookValue=\"").append( Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			strXML.append(" buildDate=\"").append(Common.checkGet(rs.getString("buildDate")) ).append( "\"");
			strXML.append(" oldBookValue=\"").append(Common.checkGet(rs.getString("originalBV")) ).append( "\"");
			strXML.append(" permitReduceDate=\"").append(Common.checkGet(rs.getString("permitReduceDate")) ).append( "\"");
			
			
			strXML.append(" serialNo=\"").append(Common.checkGet(rs.getString("serialNo")) ).append( "\"");
			strXML.append(" signNo=\"" ).append( Common.checkGet(rs.getString("signNo")) ).append( "\"");
			strXML.append(" carea=\"" ).append( Common.checkGet(rs.getString("carea")) ).append( "\"");
			strXML.append(" sarea=\"" ).append( Common.checkGet(rs.getString("sarea")) ).append( "\"");
			strXML.append(" area=\"").append( Common.checkGet(rs.getString("area")) ).append( "\"");
			strXML.append(" holdNume=\"").append( Common.checkGet(rs.getString("holdNume")) ).append( "\"");
			strXML.append(" holdDeno=\"").append( Common.checkGet(rs.getString("holdDeno")) ).append( "\"");
			strXML.append(" holdArea=\"").append( Common.checkGet(rs.getString("holdArea")) ).append( "\"");
			strXML.append(" taxCredit=\"").append( Common.checkGet(rs.getString("taxCredit")) ).append( "\"");
			strXML.append(" oldSerialNo=\"").append(Common.checkGet(rs.getString("oldserialNo")) ).append( "\"");
			
		}else if("3".equals(checkStr1) || "4".equals(checkStr1) || "5".equals(checkStr1)){
			strXML.append(" propertyName1=\"").append(Common.checkGet(rs.getString("propertyName1")) ).append( "\"");
			strXML.append(" propertyKind=\"").append( Common.checkGet(rs.getString("propertyKind")) ).append( "\"");
			strXML.append(" propertykindName=\"").append( Common.checkGet(rs.getString("propertykindName")) ).append( "\"");
			strXML.append(" oldPropertyNo=\"").append(Common.checkGet(rs.getString("oldpropertyNo")) ).append( "\"");
			strXML.append(" sourceDate=\"").append( Common.checkGet(rs.getString("sourceDate")) ).append( "\"");
			strXML.append(" fundType=\"").append( Common.checkGet(rs.getString("fundType")) ).append( "\"");
			strXML.append(" fundTypeName=\"").append( Common.checkGet(rs.getString("fundTypeName")) ).append( "\"");
			strXML.append(" valuable=\"").append( Common.checkGet(rs.getString("valuable")) ).append( "\"");
			strXML.append(" originalBV=\"").append( Common.checkGet(rs.getString("originalBV")) ).append( "\"");				
			strXML.append(" bookValue=\"").append( Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			
			strXML.append(" serialNo=\"").append(Common.checkGet(rs.getString("serialNoS")) ).append( "\"");
			strXML.append(" oldSerialNo=\"").append(Common.checkGet(rs.getString("oldserialNoS")) ).append( "\"");
			strXML.append(" lotNo=\"").append(Common.checkGet(rs.getString("lotNo")) ).append( "\"");
			
			strXML.append(" originalKeepUnit=\"").append(Common.checkGet(rs.getString("originalKeepUnit")) ).append( "\"");
			strXML.append(" originalKeeper=\"").append(Common.checkGet(rs.getString("originalKeeper")) ).append( "\"");
			strXML.append(" originalUseUnit=\"").append(Common.checkGet(rs.getString("originalUseUnit")) ).append( "\"");
			strXML.append(" originalUser=\"").append(Common.checkGet(rs.getString("originalUser")) ).append( "\"");
			strXML.append(" enterDate=\"").append(Common.checkGet(rs.getString("enterDate")) ).append( "\"");
			strXML.append(" buyDate=\"").append(Common.checkGet(rs.getString("buyDate")) ).append( "\"");
			strXML.append(" oldBookAmount=\"").append(Common.checkGet(rs.getString("bookAmount")) ).append( "\"");
			strXML.append(" adjustBookValue=\"").append( Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			strXML.append(" buildDate=\"").append("" ).append( "\"");
			strXML.append(" proofDoc=\"").append("" ).append( "\"");
			strXML.append(" oldBookUnit=\"").append("" ).append( "\"");
			strXML.append(" newBookUnit=\"").append("" ).append( "\"");
			strXML.append(" newBookAmount=\"").append(Common.checkGet(rs.getString("bookAmount")) ).append( "\"");
			strXML.append(" oldBookValue=\"").append(Common.checkGet(rs.getString("originalBV")) ).append( "\"");
			strXML.append(" permitReduceDate=\"").append(Common.checkGet(rs.getString("permitReduceDate")) ).append( "\"");
			strXML.append(" useEndYM=\"").append(Common.checkGet(rs.getString("useEndYM")) ).append( "\"");
			
		}else if("8".equals(checkStr1)){
			strXML.append(" serialNo=\"").append(Common.checkGet(rs.getString("serialNo")) ).append( "\"");
			strXML.append(" propertyName1=\"").append("" ).append( "\"");
			strXML.append(" oldPropertyNo=\"").append("" ).append( "\"");
			strXML.append(" oldSerialNo=\"").append(Common.checkGet(rs.getString("oldSerialNo1")) ).append( "\"");
			
			strXML.append(" sourceDate=\"").append("" ).append( "\"");
			strXML.append(" originalBV=\"").append(Common.checkGet(rs.getString("originalBV")) ).append( "\"");
			
			strXML.append(" oldBookValue=\"").append(Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			strXML.append(" newBookValue=\"").append(Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			
			
		}else if("9".equals(checkStr1)){
			strXML.append(" serialNo=\"").append(Common.checkGet(rs.getString("serialNo")) ).append( "\"");
			strXML.append(" propertyName1=\"").append("" ).append( "\"");
			strXML.append(" oldPropertyNo=\"").append("" ).append( "\"");
			strXML.append(" oldSerialNo=\"").append(Common.checkGet(rs.getString("oldSerialNo1")) ).append( "\"");
			
			strXML.append(" sourceDate=\"").append("" ).append( "\"");
			strXML.append(" originalBV=\"").append(Common.checkGet(rs.getString("originalBV")) ).append( "\"");
	
			strXML.append(" oldBookValue=\"").append(Common.checkGet(rs.getString("bookValue")) ).append( "\"");
			strXML.append(" newBookValue=\"").append(Common.checkGet(rs.getString("bookValue")) ).append( "\"");
		}
		
		
		
		
			
		strXML.append(" /> ");
	} 
			
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	

strXML.append("</ResultSet>");	
out.write(strXML.toString());
%>
