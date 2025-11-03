
<%@page import="util.Database"%>
<%@page import="java.sql.*,util.Common"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA003F">
</jsp:useBean>
<%
/*
String result=request.getReader().readLine();

String[] resultArray = result.split("&");

String enterOrg = resultArray[0].split("=")[1];
String ownership = resultArray[1].split("=")[1];
String propertyNo = resultArray[2].split("=")[1];
String serialNo = resultArray[3].split("=")[1];
String NowUseArea = resultArray[4].split("=")[1];
String state = resultArray[5].split("=")[1];
*/

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String NowUseArea = Common.checkGet(request.getParameter("useArea"));
String state = Common.checkGet(request.getParameter("state"));

Database db = new Database();
ResultSet rs;
String sql = "";
double useArea = 0;
double totalArea = 0;
String OVERTOTALAREA = "";

try{
			
		sql =" select nvl(sum(useArea)" + (("insert".equals(state.toLowerCase()))?"+"+NowUseArea:"") + 
				" ,0) as useArea from UNTLA_Manage where 1=1" + "\n" +
			" and enterOrg = '" + util.Common.esapi(enterOrg) + "'" +
			" and ownership = '" + util.Common.esapi(ownership) + "'" +
			" and propertyNo = '" + util.Common.esapi(propertyNo) + "'" +
			" and serialNo = '" + util.Common.esapi(serialNo)+ "'" +
			" and nvl(useDateS,'0000000')<= TO_CHAR(SYSDATE,'YYYYMMDD')-'19110000' " + "\n" +
			" and nvl(useDateE,'9999999')>= TO_CHAR(SYSDATE,'YYYYMMDD')-'19110000' ";
			
	rs = db.querySQL(sql);
	if (rs.next()){
		useArea = rs.getDouble("useArea");
	}
		
	sql =" select nvl(area,0) as area from UNTLA_Land where 1=1" + "\n" +
			" and enterOrg = '" + util.Common.esapi(enterOrg) + "'" +
			" and ownership = '" + util.Common.esapi(ownership) + "'" +
			" and propertyNo = '" + util.Common.esapi(propertyNo) + "'" +
			" and serialNo = '" + util.Common.esapi(serialNo)+ "'" ;

	rs = db.querySQL(sql);
	if (rs.next()){
		totalArea = rs.getDouble("area");
	}

	if((totalArea - useArea) < 0){
		OVERTOTALAREA = "true";
	}else{
		OVERTOTALAREA = "false";
	}
	
}catch(Exception e){
	
}finally{
	db.closeAll();
}

String resultStr="{OVERTOTALAREA:" + OVERTOTALAREA + "}";

response.getWriter().write(resultStr);
%>
