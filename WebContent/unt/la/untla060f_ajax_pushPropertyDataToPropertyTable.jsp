
<%@page import="util.Database"%>
<%@page import="java.sql.*"%>
<%@ page import="util.Common" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
/*
String result=request.getReader().readLine();

String[] resultArray=result.split("&");

String[] checkArray=resultArray[0].split("=");
String[] propertyNoArray=resultArray[1].split("=");
String[] serialNoArray=resultArray[2].split("=");
String[] edit=resultArray[3].split("=");
*/
String sql;

String type = Common.checkGet(request.getParameter("type"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
String editID = Common.checkGet(request.getParameter("editID"));
/*
if("checked".equals(checkArray[1])){
	sql="insert into PROPERTYTABLE_"+edit[1].toString()+" (propertyno, serialno, checkflag)Values('" + Common.esapi(propertyNoArray[1]) + "','" + Common.esapi(serialNoArray[1]) + "', null)";
}else if("Updatechecked".equals(checkArray[1])){
	sql="update PROPERTYTABLE_"+edit[1].toString()+" set checkflag='Y' where propertyno = '" + Common.esapi(propertyNoArray[1]) + "' and serialno = '" + Common.esapi(serialNoArray[1]) + "'";
}else{
	sql="delete from PROPERTYTABLE_"+edit[1].toString()+" where propertyno = '" + Common.esapi(propertyNoArray[1]) + "' and serialno = '" + Common.esapi(serialNoArray[1]) + "'";
}
*/
if("checked".equals(type)){
	sql="insert into PROPERTYTABLE_"+editID+" (propertyno, serialno, checkflag)Values('" + Common.esapi(propertyNo) + "','" + Common.esapi(serialNo) + "', null)";
}else if("Updatechecked".equals(type)){
	sql="update PROPERTYTABLE_"+editID+" set checkflag='Y' where propertyno = '" + Common.esapi(propertyNo) + "' and serialno = '" + Common.esapi(serialNo) + "'";
}else{
	sql="delete from PROPERTYTABLE_"+editID+" where propertyno = '" + Common.esapi(propertyNo) + "' and serialno = '" + Common.esapi(serialNo) + "'";
}
Database db=new Database();
try{
	db.excuteSQL(sql);
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}

%>
