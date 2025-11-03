
<%@page import="unt.ch.UNTCH_Tools"%>
<%@page import="util.Database"%>
<%@page import="java.sql.*,util.Common"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
/*
String result=request.getReader().readLine();

String[] resultArray=result.split("&");
*/

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String signNo = Common.checkGet(request.getParameter("signNo"));
String differencekind = Common.checkGet(request.getParameter("differencekind"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));

Database db=new Database();
ResultSet rs;
String sql;
String resultStr="";

try{
	
	sql=" select top 1"+
			" a.propertyno," +
			" a.serialno," +
			" a.propertykind," +
			" (select z.propertyname from SYSPK_PropertyCode z where z.propertyno=a.propertyno) as propertyname,"+			
			" a.propertyname1," +
			" a.signno," +
			" substring(a.signno,1,1) + '000000' as signno1," +
			" substring(a.signno,1,3) + '0000' as signno2," +
			" substring(a.signno,1,7) as signno3," +
			" substring(a.signno,8,4) as signno4," +
			" substring(a.signno,12,4) as signno5," +
			" a.fundtype," +
			" a.valuable," +
			" a.taxcredit," +
			" a.area," +
			" a.usestate1," +
			" a.holdnume," +
			" a.holddeno," +
			" a.holdarea," +
			" a.accountingtitle," +
			" a.bookunit," +
			" a.bookvalue," +
			" a.netunit," +
			" a.netvalue," +
			" a.useseparate," +
			" a.usekind," +
			" a.proofdoc," +
			" a.field," +
			" a.sourcedate," +
			" a.keepunit," + 
			" a.keeper," +
			" a.useunit," +
			" a.userno," + 
			" a.usernote," +
			" a.place1," +
			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name," +
			" a.place," +
			" a.buydate," +
			" a.oldPropertyno as oldpropertyno," +
			" a.oldSerialno as oldserialno," +
			" (case when b.suitDateS is null then" +
				" case a.originalBasis" +
				" when '1'" +
				" then (select z.suitDateS from UNTLA_BulletinDate z where z.bulletinKind='1' and z.bulletinDate=a.originalDate)" +
				" end" +
				" else b.suitDateS end) as bulletindate," +
			" (case when b.valueUnit is null then " +
				" case a.originalBasis" +
				" when '1'" +
				" then a.originalUnit" +
				" end" +
				" else b.valueUnit end) as valueunit" +
		" from untla_land a" +
				" left join untla_value b on b.enterorg = a.enterorg and b.ownership = a.ownership and b.differencekind = a.differencekind and b.propertyno = a.propertyno and b.serialno = a.serialno" +
		" where 1=1" +
			" and a.datastate = '1'";
	/*
	String[] strTemp;
	
	for(int i=0;i<resultArray.length;i++){
		strTemp = resultArray[i].split("=");

		sql += " and a." + util.Common.esapi(strTemp[0]) + " = '" + util.Common.esapi(strTemp[1]) + "'";
	}
	*/
	
	if(!"".equals(enterOrg)){
		sql += " and a.enterorg = " + Common.sqlChar(enterOrg);
	}
	if(!"".equals(ownership)){
		sql += " and a.ownership = " + Common.sqlChar(ownership);
	}
	if(!"".equals(signNo)){
		sql += " and a.signno = " + Common.sqlChar(signNo);
	}
	if(!"".equals(differencekind)){
		sql += " and a.differencekind = " + Common.sqlChar(differencekind);
	}
	if(!"".equals(propertyNo)){
		sql += " and a.propertyno = " + Common.sqlChar(propertyNo);
	}
	if(!"".equals(serialNo)){
		sql += " and a.serialno = " + Common.sqlChar(serialNo);
	}
	
	sql += " order by b.bulletindate desc";

	rs=db.querySQL(sql);

	ResultSetMetaData rsmd = rs.getMetaData();
	
	resultStr="{";
	
	if(rs.next()){
		
		int ColumnCount=rsmd.getColumnCount();
		for(int i=1;i<=ColumnCount;i++){
			
			if("sourcedate".equals(rsmd.getColumnName(i)) || "buydate".equals(rsmd.getColumnName(i))){
				UNTCH_Tools ut = new UNTCH_Tools();
				resultStr += rsmd.getColumnName(i)+":\"" + ut._transToROC_Year(util.Common.checkGet(rs.getString(i))) + "\"";
			}else{
				resultStr += rsmd.getColumnName(i)+":\""+util.Common.checkGet(rs.getString(i))+"\"";
			}
			if(i==ColumnCount){
				
			}else{
				resultStr += ",";	
			}
		}	
		resultStr += ",retureStr:\"\"";
	}else{
		resultStr += "retureStr:\"NoResultFind\"";
	}
	resultStr+="}";
	
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}

response.getWriter().write(resultStr);
%>
