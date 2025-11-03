<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%
//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

String sql = "";
//String q_enterOrg = resultArray[0].split("=")[1];
//String q_deprUnit = resultArray[1].split("=")[1];

String q_enterOrg = Common.checkGet(request.getParameter("q_enterOrg"));
String q_deprUnit = Common.checkGet(request.getParameter("q_deprUnit"));
if (!"".equals(q_enterOrg) && Validate.checkAlphaInt(q_enterOrg) && q_enterOrg.length()<20) {

	sql=" select a.depraccountsno, a.depraccountsname from SYSCA_DEPRACCOUNTS a,SYSCA_DEPRUNITACCOUNTS b " +
	" where a.enterorg=b.enterorg " +
	" and a.depraccountsno = b.depraccountsno" +
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
					"\"depraccountsno\":\"").append(Common.checkGet(rs.getString("depraccountsno"))).append("\",").append(
					"\"depraccountsname\":\"").append(Common.checkGet(rs.getString("depraccountsname"))).append("\"").append(
					"}");
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}

	response.getWriter().write("[" + resultStr.toString() + "]");
}


%>

