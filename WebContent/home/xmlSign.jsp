<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%
//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

String sql = "";
//String[] checkStr = (resultArray[0].split("="));
String signNo1 = Common.checkGet(request.getParameter("signNo1"));
String signNo2 = Common.checkGet(request.getParameter("signNo2"));
if(!signNo1.equals("")){	
	sql=" select signNo, signName from SYSCA_Sign " +
		" where signNo like '" + Common.esapi(signNo1.substring(0,1)) + "__0000'" +
		" and signNo not like '" + Common.esapi(signNo1.substring(0,1)) + "000000'";
}else if(!signNo2.equals("")){
	sql=" select signNo, signName from SYSCA_Sign " +
		" where signNo like '" + Common.esapi(signNo2.substring(0,3))+ "____'" +
		" and signNo not like '" + Common.esapi(signNo2.substring(0,3)) + "0000'";
}

Database db = new Database();
StringBuilder resultStr = new StringBuilder();
try{
	ResultSet rs = db.querySQL(sql);
	while(rs.next()){
		if(resultStr.length() == 0){
			
		}else{
			resultStr.append(",");
		}
		
		resultStr.append("{").append(
				"\"signNo\":\"").append(Common.checkGet(rs.getString("signNo"))).append("\",").append(
				"\"signName\":\"").append(Common.checkGet(rs.getString("signName"))).append("\"").append(
				"}");
	}
	rs.close();
} catch (Exception e) {
	//e.printStackTrace();
} finally {
	db.closeAll();
}

response.getWriter().write("[" + resultStr.toString() + "]");

%>

