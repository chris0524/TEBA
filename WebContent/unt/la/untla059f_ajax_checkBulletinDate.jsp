
<%@page import="util.Database"%>
<%@page import="java.sql.*,util.Common"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
/*
String result=request.getReader().readLine();

String[] resultArray=result.split("&");
*/
String bulletinDate = Common.checkGet(request.getParameter("bulletinDate"));

Database db=new Database();
ResultSet rs;
String sql;
String resultStr="";

try{
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<	      需修改處		>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	sql="select count(*) as bulletinDate from UNTLA_BulletinDate where 1=1" +
			" and bulletinKind = '1'" +
			"";
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<	  其他不需修改	>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/*
	for(int i=0;i<resultArray.length;i++){
		sql += " and "+util.Common.esapi(resultArray[i].replace("%5B%5D","").replace("=","='"))+"'";
	}
	*/
	if(!"".equals(bulletinDate)){
		sql += " and bulletinDate = " + Common.sqlChar(bulletinDate);
	}
	rs=db.querySQL(sql);

	ResultSetMetaData rsmd = rs.getMetaData();
	
	resultStr="{";
	
	if(rs.next()){
		int ColumnCount=rsmd.getColumnCount();
		for(int i=1;i<=ColumnCount;i++){
			resultStr += rsmd.getColumnName(i)+":\""+util.Common.get(rs.getString(i))+"\"";
			if(i==ColumnCount){
				
			}else{
				resultStr += ",";	
			}
		}		
	}
	resultStr+="}";
	
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}


response.getWriter().write(resultStr);
%>
