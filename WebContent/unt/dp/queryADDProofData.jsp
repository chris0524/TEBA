<%@ page contentType="text/html;charset=UTF-8" import="util.*,java.sql.*"%>
<%

String result=request.getReader().readLine();

String[] resultArray=result.split("&");

String enterOrg = resultArray[0].split("=")[1];
String ownership = resultArray[1].split("=")[1];
String differenceKind = resultArray[2].split("=")[1];

String[] strs = null;
String propertyNo = "";
strs = resultArray[3].split("=");
if(strs.length > 1){
	propertyNo = strs[1];	
}

String serialNo = "";
strs = resultArray[4].split("=");
if(strs.length > 1){
	serialNo = strs[1];	
}



/*
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String differenceKind = Common.checkGet(request.getParameter("differenceKind"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String laSignNo = Common.checkGet(request.getParameter("laSignNo"));
String buSignNo = Common.checkGet(request.getParameter("buSignNo"));
*/

String data = "";
String sql = "";

sql = "select " +

	  " deprmethod," +
	  " buildfeecb," +
	  " deprunitcb," +
	  " deprpark," +
	  " (select deprparkname from SYSCA_DEPRPARK z where z.enterorg=a.enterorg and z.deprparkno=a.deprpark) as deprparkname,"+
	  " deprunit," +
	  " (select deprunitname from SYSCA_DEPRUNIT z where z.enterorg=a.enterorg and z.deprunitno=a.deprunit) as deprunitname,"+
	  " deprunit1," +
	  " (select deprunit1name from SYSCA_DEPRUNIT1 z where z.enterorg=a.enterorg and z.deprunit1no=a.deprunit1) as deprunit1name,"+	  
	  " depraccounts," +
	  " (select depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg=a.enterorg and z.depraccountsno=a.depraccounts) as depraccountsname,"+	  
	  " scrapvalue," +
	  " depramount," +
	  " accumdepr," +
	  " apportionmonth," +
	  " monthdepr, " +
	  " monthdepr1, " +
	  " apportionendym, "+
	  " bookvalue,"+
	  " netvalue,"+
	  " '' as lotno, "+
	  " propertyname1 " + 
	  " from UNTBU_BUILDING a where 1=1" + 
	  " and enterorg = '" + Common.esapi(enterOrg) + "' and ownership = '" + Common.esapi(ownership) + "' and differenceKind = '" + Common.esapi(differenceKind) + "' and verify = 'Y'" + 
	  ("".equals(propertyNo)?"":" and propertyno = '" + Common.esapi(propertyNo) + "'") + 
	  ("".equals(serialNo)?"":" and serialno = '" + Common.esapi(serialNo) + "'") +
	  
	  " union " +
	  " select " +
	  
	  " deprmethod," +
	  " buildfeecb," +
	  " deprunitcb," +
	  " deprpark," +
	  " (select deprparkname from SYSCA_DEPRPARK z where z.enterorg=a.enterorg and z.deprparkno=a.deprpark) as deprparkname,"+
	  " deprunit," +
	  " (select deprunitname from SYSCA_DEPRUNIT z where z.enterorg=a.enterorg and z.deprunitno=a.deprunit) as deprunitname,"+
	  " deprunit1," +
	  " (select deprunit1name from SYSCA_DEPRUNIT1 z where z.enterorg=a.enterorg and z.deprunit1no=a.deprunit1) as deprunit1name,"+	
	  " depraccounts," +
	  " (select depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg=a.enterorg and z.depraccountsno=a.depraccounts) as depraccountsname,"+
	  " scrapvalue," +
	  " depramount," +
	  " accumdepr," +
	  " apportionmonth," +
	  " monthdepr, " +
	  " monthdepr1, " +
	  " apportionendym, " +
	  " bookvalue,"+
	  " netvalue,"+	  
	  " lotno, "+
	  " a.propertyname1 " + 
			" from UNTMP_MOVABLEDETAIL a where 1=1" + 
			" and enterorg = '" + Common.esapi(enterOrg) + "' and ownership = '" + Common.esapi(ownership) + "' and differenceKind = '" + Common.esapi(differenceKind) + "' and verify = 'Y'" + 
			("".equals(propertyNo)?"":" and propertyno = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and serialno = '" + Common.esapi(serialNo) + "'") +
	  " union " +		
	  " select " +
	  
	  " deprmethod," +
	  " buildfeecb," +
	  " deprunitcb," +
	  " deprpark," +
	  " (select deprparkname from SYSCA_DEPRPARK z where z.enterorg=a.enterorg and z.deprparkno=a.deprpark) as deprparkname,"+	  
	  " deprunit," +
	  " (select deprunitname from SYSCA_DEPRUNIT z where z.enterorg=a.enterorg and z.deprunitno=a.deprunit) as deprunitname,"+
	  " deprunit1," +
	  " (select deprunit1name from SYSCA_DEPRUNIT1 z where z.enterorg=a.enterorg and z.deprunit1no=a.deprunit1) as deprunit1name,"+		  
	  " depraccounts," +
	  " (select depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg=a.enterorg and z.depraccountsno=a.depraccounts) as depraccountsname,"+
	  " scrapvalue," +
	  " depramount," +
	  " accumdepr," +
	  " apportionmonth," +
	  " monthdepr, " +
	  " monthdepr1, " +
	  " apportionendym, " +
	  " bookvalue,"+
	  " netvalue,"+		  
	  " '' as lotno, "+
	  " propertyname1 " + 
			" from UNTRF_ATTACHMENT a where 1=1" + 
			" and enterorg = '" + Common.esapi(enterOrg) + "' and ownership = '" + Common.esapi(ownership) + "' and differenceKind = '" + Common.esapi(differenceKind) + "' and verify = 'Y'" + 
			("".equals(propertyNo)?"":" and propertyno = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and serialno = '" + Common.esapi(serialNo) + "'") +			
		"" +
		" union " +
		" select " + 
		" deprmethod," +
		  " buildfeecb," +
		  " deprunitcb," +
		  " deprpark," +
		  " (select deprparkname from SYSCA_DEPRPARK z where z.enterorg=a.enterorg and z.deprparkno=a.deprpark) as deprparkname,"+	  
		  " deprunit," +
		  " (select deprunitname from SYSCA_DEPRUNIT z where z.enterorg=a.enterorg and z.deprunitno=a.deprunit) as deprunitname,"+
		  " deprunit1," +
		  " (select deprunit1name from SYSCA_DEPRUNIT1 z where z.enterorg=a.enterorg and z.deprunit1no=a.deprunit1) as deprunit1name,"+		  
		  " depraccounts," +
		  " (select depraccountsname from SYSCA_DEPRACCOUNTS z where z.enterorg=a.enterorg and z.depraccountsno=a.depraccounts) as depraccountsname,"+
		  " scrapvalue," +
		  " depramount," +
		  " accumdepr," +
		  " apportionmonth," +
		  " monthdepr, " +
		  " monthdepr1, " +
		  " apportionendym, " +
		  " bookvalue,"+
		  " netvalue,"+		  
		  " '' as lotno, "+
		  " propertyname1 " + 
		 " from UNTRT_ADDPROOF a where 1=1 " +
		 " and enterorg = '" + Common.esapi(enterOrg) + "' and ownership = '" + Common.esapi(ownership) + "' and differenceKind = '" + Common.esapi(differenceKind) + "' and verify = 'Y'" + 
			("".equals(propertyNo)?"":" and propertyno = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and serialno = '" + Common.esapi(serialNo) + "'") +			
		"";

//SQLtransfer slf = new SQLtransfer();
//System.out.println(slf._transSQLFormat("-- queryADDProofData -- "+sql.toString()));
StringBuilder resultStr = new StringBuilder();
unt.ch.UNTCH_Tools ut = new unt.ch.UNTCH_Tools();
Database db = new Database();
ResultSet rs = null;
try{
	
	resultStr.append("{");
	rs = db.querySQL(sql);
	if(rs.next()){		
		resultStr.append("\"").append("hasData").append("\":\"").append("Y").append("\",");
		
		resultStr.append("\"").append("deprmethod").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprmethod")))).append("\",");
		resultStr.append("\"").append("buildfeecb").append("\":\"").append(Common.esapi(Common.get(rs.getString("buildfeecb")))).append("\",");
		resultStr.append("\"").append("deprunitcb").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprunitcb")))).append("\",");
		resultStr.append("\"").append("deprpark").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprpark")))).append("\",");
		resultStr.append("\"").append("deprparkname").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprparkname")))).append("\",");
		resultStr.append("\"").append("deprunit").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprunit")))).append("\",");
		resultStr.append("\"").append("deprunitname").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprunitname")))).append("\",");
		resultStr.append("\"").append("deprunit1").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprunit1")))).append("\",");
		resultStr.append("\"").append("deprunit1name").append("\":\"").append(Common.esapi(Common.get(rs.getString("deprunit1name")))).append("\",");		
		resultStr.append("\"").append("depraccounts").append("\":\"").append(Common.esapi(Common.get(rs.getString("depraccounts")))).append("\",");
		resultStr.append("\"").append("depraccountsname").append("\":\"").append(Common.esapi(Common.get(rs.getString("depraccountsname")))).append("\",");
		resultStr.append("\"").append("scrapvalue").append("\":\"").append(Common.esapi(Common.get(rs.getString("scrapvalue")))).append("\",");
		resultStr.append("\"").append("depramount").append("\":\"").append(Common.esapi(Common.get(rs.getString("depramount")))).append("\",");
		resultStr.append("\"").append("accumdepr").append("\":\"").append(Common.esapi(Common.get(rs.getString("accumdepr")))).append("\",");
		resultStr.append("\"").append("apportionmonth").append("\":\"").append(Common.esapi(Common.get(rs.getString("apportionmonth")))).append("\",");
		resultStr.append("\"").append("monthdepr").append("\":\"").append(Common.esapi(Common.get(rs.getString("monthdepr")))).append("\",");
		resultStr.append("\"").append("monthdepr1").append("\":\"").append(Common.esapi(Common.get(rs.getString("monthdepr1")))).append("\",");
		resultStr.append("\"").append("apportionendym").append("\":\"").append(Common.esapi(Common.get(Datetime.changeTaiwanYYMM(rs.getString("apportionendym"),"1")))).append("\",");
		resultStr.append("\"").append("bookvalue").append("\":\"").append(Common.esapi(Common.get(rs.getString("bookvalue")))).append("\",");
		resultStr.append("\"").append("netvalue").append("\":\"").append(Common.esapi(Common.get(rs.getString("netvalue")))).append("\",");
		resultStr.append("\"").append("lotno").append("\":\"").append(Common.esapi(Common.get(rs.getString("lotno")))).append("\", ");
		resultStr.append("\"").append("propertyname1").append("\":\"").append(Common.esapi(Common.get(rs.getString("propertyname1")))).append("\" ");
		

	}else{
		resultStr.append("\"").append("hasData").append("\":\"").append("N").append("\"");
	}
	rs.close();
	resultStr.append("}");
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}

response.getWriter().write(resultStr.toString());
%>

