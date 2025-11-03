<%@ page contentType="text/html;charset=UTF-8" import="util.*,java.sql.*;"%>
<%

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));

String data = "";
String sql = "";
String deprunitno = "";
	
sql = "select deprunitno from SYSCA_DEPRUNIT where deprunitname like '出租資產' " +
				" and enterorg = '" + Common.esapi(enterOrg) + "'";

StringBuilder resultStr = new StringBuilder();
Database db = new Database();
ResultSet rs = null;
try{
	resultStr.append("{");
	rs = db.querySQL(sql);
	if(rs.next()){
		deprunitno = Common.checkGet(rs.getString("deprunitno"));		
	}
	rs.close();
	resultStr.append("\"deprunitno\":\"").append(deprunitno).append("\"");
	resultStr.append(",");
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}



sql = "select depraccountsno," +
				"(SELECT depraccountsname FROM SYSCA_DEPRACCOUNTS z where z.depraccountsno = a.depraccountsno and z.enterorg = a.enterorg) AS depraccountsnoName" +
				" from SYSCA_DEPRUNITACCOUNTS a where 1=1" +
				" and enterorg = '" + Common.esapi(enterOrg) + "'" +
				" and deprunitno = '" + Common.esapi(deprunitno) + "'";

try{
	db = new Database();

	rs = db.querySQL(sql);
	resultStr.append("\"value\":\"");
	StringBuilder stb = new StringBuilder();
	while(rs.next()){
		if (stb.length() != 0){
			stb.append(":;:");
		}
		stb.append(Common.checkGet(rs.getString("depraccountsno")));
		
		stb.append(":<:");
		
		stb.append(Common.checkGet(rs.getString("depraccountsnoName")));
	}
	rs.close();
	resultStr.append(stb.toString()).append("\"}");
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}

response.getWriter().write(resultStr.toString());
%>

