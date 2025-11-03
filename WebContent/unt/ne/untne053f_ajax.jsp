
<%@page import="util.Database"%>
<%@page import="java.sql.*,util.Common"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
/*
String result=request.getReader().readLine();

String[] resultArray=result.split("&");
*/
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));

Database db=new Database();
ResultSet rs;
String sql;
String resultStr="";

try{
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<	      需修改處		>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	sql="select propertyUnit, material, limitYear from SYSPK_PropertyCode2 " + 
			" where 1=1" +
			" and propertyType = '1'";
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<	  其他不需修改	>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/*
	for(int i=0;i<resultArray.length;i++){
		sql += " and "+util.Common.esapi(resultArray[i].replace("%5B%5D","").replace("=","='"))+"'";
	}
	*/
	if(!"".equals(enterOrg)){
		sql += " and enterorg = " + Common.sqlChar(enterOrg);
	}
	if(!"".equals(propertyNo)){
		sql += " and ownership = " + Common.sqlChar(propertyNo);
	}
	
	rs=db.querySQL(sql);
	
	ResultSetMetaData rsmd = rs.getMetaData();
	
	resultStr="{";
	
	if(rs.next()){
		int ColumnCount=rsmd.getColumnCount();
		for(int i=1;i<=ColumnCount;i++){
			resultStr += rsmd.getColumnName(i)+":\""+util.Common.checkGet(rs.getString(i))+"\"";
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
