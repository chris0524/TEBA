<%@ page contentType="text/html;charset=UTF-8" import="util.*,java.sql.*"%>
<%
//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

//String[] codeids = resultArray[0].split("=");

String codeid = Common.checkGet(request.getParameter("codeid"));
String codecond1_1 = Common.checkGet(request.getParameter("codecond1_1"));
String codecond1_2 = Common.checkGet(request.getParameter("codecond1_2"));

StringBuilder resultStr = new StringBuilder();

if(!"".equals(codeid)){
	String data = "";
	String sql = "";
		
	sql = "select codename from SYSCA_CODE where codekindid = 'CAA' " +
					" and codeid = '" + Common.esapi(codeid) + "'" +
					" and codecon1 in (" + Common.esapi(codecond1_1) + "," + Common.esapi(codecond1_2) + ")";
	
	
	Database db = new Database();
	ResultSet rs = null;
	resultStr.append("{");

	try{		
		rs = db.querySQL(sql);
		if(rs.next()){
			resultStr.append("\"").append("codename").append("\":\"").append(Common.checkGet(rs.getString("codename"))).append("\"");
		}
		rs.close();

	}catch(Exception e){
		e.printStackTrace();
	}finally{
		db.closeAll();
	}
}else{
	resultStr.append("{\"").append("codename").append("\":\"").append("").append("\"");
}


resultStr.append("}");

response.getWriter().write(resultStr.toString());
%>

