<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%
///String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

String sql = "";
//String q_enterOrg = resultArray[0].split("=")[1];
//String q_deprUnit = resultArray[1].split("=")[1];
String q_enterOrg = Common.checkGet(request.getParameter("q_enterOrg"));
String q_deprUnit = Common.checkGet(request.getParameter("q_deprUnit"));


sql=" select a.deprunit1no, a.deprunit1name from SYSCA_DEPRUNIT1 a,SYSCA_DEPRUNITCOMPARISON b " +
" where a.enterorg=b.enterorg " +
" and a.deprunit1no = b.deprunit1no" +
" and b.enterorg = '" + Common.esapi(q_enterOrg) + "'" +
" and b.deprunitno = '" + Common.esapi(q_deprUnit) + "'";

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
				"\"deprunit1no\":\"").append(Common.checkGet(rs.getString("deprunit1no"))).append("\",").append(
				"\"deprunit1name\":\"").append(Common.checkGet(rs.getString("deprunit1name"))).append("\"").append(
				"}");
	}
	rs.close();
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}

response.getWriter().write("[" + resultStr.toString() + "]");

%>

