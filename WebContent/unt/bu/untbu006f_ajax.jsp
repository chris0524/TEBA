
<%@page import="util.Database"%>
<%@page import="java.sql.*, util.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%

String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String signNo = Common.checkGet(request.getParameter("signNo"));

//String result=request.getReader().readLine();

//String[] resultArray=result.split("&");

Database db=new Database();
ResultSet rs;
String sql;
String resultStr="";

try{
	sql="select"+
		" a.area, a.holdnume, a.holddeno, a.holdarea, a.manageOrg,"+
		" (select organsname from sysca_organ where organid=a.manageOrg) as manageOrgName,"+
		" case a.ownership"+
		" 	when '2'"+
		" 	then '中華民國'"+
		" 	when '3'"+
		" 	then '彰化縣'"+
		" 	else ''"+
		" end as owner,"+
		" a.useseparate, a.usekind, a.field, a.landRule, a.notes1"+
		" from untla_land a where 1=1"+
		" and enterorg = " + Common.sqlChar(Common.esapi(enterOrg)) +
		" and signno = " + Common.sqlChar(Common.esapi(signNo));
		
	rs=db.querySQL(sql);
	
	if(rs.next()){
		resultStr="{";
		
		resultStr+="area:\""+Common.checkGet(rs.getString("area"))+"\",";
		resultStr+="holdnume:\""+Common.checkGet(rs.getString("holdnume"))+"\",";
		resultStr+="holddeno:\""+Common.checkGet(rs.getString("holddeno"))+"\",";
		resultStr+="holdarea:\""+Common.checkGet(rs.getString("holdarea"))+"\",";
		resultStr+="manageOrg:\""+Common.checkGet(rs.getString("manageOrg"))+"\",";
		resultStr+="manageOrgName:\""+Common.checkGet(rs.getString("manageOrgName"))+"\",";
		resultStr+="owner:\""+Common.checkGet(rs.getString("owner"))+"\",";
		resultStr+="useseparate:\""+Common.checkGet(rs.getString("useseparate"))+"\",";
		resultStr+="usekind:\""+Common.checkGet(rs.getString("usekind"))+"\",";
		resultStr+="field:\""+Common.checkGet(rs.getString("field"))+"\",";
		resultStr+="landRule:\""+Common.checkGet(rs.getString("landRule"))+"\",";
		resultStr+="notes1:\""+Common.checkGet(rs.getString("notes1"))+"\"";
		
		resultStr+="}";
	}
	
	
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}


response.getWriter().write(resultStr);
%>
