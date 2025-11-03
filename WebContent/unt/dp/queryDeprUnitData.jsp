<%@ page contentType="text/html;charset=UTF-8" import="util.*,java.sql.ResultSet"%>
<%
//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String queryData = Common.checkGet(request.getParameter("queryData"));
String queryType = Common.checkGet(request.getParameter("queryType"));

String sql = "";

//TDlib_Simple.com.src.DBServerTools dbt = new TDlib_Simple.com.src.DBServerTools();
//dbt._setDatabase(new Database());

Database db = null;
ResultSet rs = null;

String count = "";
String data = "";
String countStr = "";

//String checkStr = resultArray[2].split("=")[1];
if(queryType.equals("deprUnit1")){
	try{
		sql = "select count(*) from SYSCA_DEPRUNITCOMPARISON where enterorg = '" + Common.esapi(enterOrg) 
				+ "' and deprUnitNo = '" + Common.esapi(queryData) + "'";
	
		db = new Database();

		rs = db.querySQL(sql);
		if(rs.next()){
			count = rs.getString(1);
		}
		
		if("1".equals(count)){
			countStr = "TOP 1";
		}
		
		sql = "select " + countStr + " DeprUnit1No from SYSCA_DEPRUNITCOMPARISON where enterorg = '" + Common.esapi(enterOrg) 
				+ "' and deprUnitNo = '" + Common.esapi(queryData) + "'";
		
		rs = db.querySQL(sql);
		if(rs.next()){
			data = Common.checkGet(rs.getString(1));
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
	
}else if(queryType.equals("deprAccounts")){
	try{
		sql = "select count(*) from SYSCA_DEPRUNITACCOUNTS where enterorg = '" + Common.esapi(enterOrg) 
				+ "' and deprUnitNo = '" + Common.esapi(queryData) + "'";
		
		db = new Database();
		
		rs = db.querySQL(sql);
		if(rs.next()){
			count = rs.getString(1);
		}
		
		if("1".equals(count)){
			countStr = "TOP 1";
		}
		
		sql = "select " + countStr + " deprAccountsNo from SYSCA_DEPRUNITACCOUNTS where enterorg = '" + Common.esapi(enterOrg) 
				+ "' and deprUnitNo = '" + Common.esapi(queryData) + "'";
		
		rs = db.querySQL(sql);
		if(rs.next()){
			data = Common.checkGet(rs.getString(1));
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
}

String resultStr = "";

resultStr = "{\"defaultValue\":\"" + data + "\"}";

response.getWriter().write(resultStr);
%>

