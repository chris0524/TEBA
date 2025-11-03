<%@ page contentType="text/html;charset=UTF-8" import="util.*,java.sql.*"%>
<%
//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

//String codeid = resultArray[0].split("=")[1];
String codeid = Common.checkGet(request.getParameter("codeid"));

String data = "";
String sql = "";
	
sql = "select codename from SYSCA_CODE where codekindid = 'SKD' " +
				" and codeid = '" + Common.esapi(codeid) + "'";

StringBuilder resultStr = new StringBuilder();
Database db = new Database();
ResultSet rs = null;
try{
	resultStr.append("{");
	rs = db.querySQL(sql);
	if(rs.next()){
		resultStr.append("\"").append("codename").append("\":\"").append(Common.checkGet(rs.getString("codename"))).append("\"");
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

