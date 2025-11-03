<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="ref.ap.REFAP002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if ("".equals(obj.getQ_orgID())) {
	obj.setQ_orgID(user.getOrganID());
	obj.setQ_orgIDName(user.getOrganName());
}
%>
<html>
<head>
<title>議員質詢或建決議案辦理情形</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<style>
.linetable{
	font-family:"標楷體"; 
	font-size: 15px; 
	border-top-width:1px;
	border-top-style:solid;
	border-top-color:#000000;
	border-left-width:1px;
	border-left-style:solid;
	border-left-color:#000000;	
}
.linetd{
	border-bottom-width:1px;
	border-bottom-style:solid;
	border-bottom-color:#000000;
	border-right-width:1px;
	border-right-style:solid;
	border-right-color:#000000;	
	text-valign:top;
	padding:5px 5px 5px 5px; 
}
.title{
	font-family:"標楷體"; 
	font-size: 19px; 
	font-weight:bolder; 
}
</style>
</head>
<body>
<table class="linetable" width="975px" border="0" cellspacing="0" cellpadding="0" >
	<tr>
		<td class="linetd" colspan="6" style="height:60px"><span class="title">議員質詢或建決議案事項辦理情形彙整表</span></td>
	</tr>
	<tr  class="title" style="background=lightyellow">
		<td class="linetd" width="10%">議員名稱</td>
		<td class="linetd" width="22%">質詢或建決議案事項</td>
		<td class="linetd" width="10%">辦理機關</td>
		<td class="linetd" >辦理情形</td>
		<td class="linetd" width="10%">提案日期</td>
		<td class="linetd" width="10%">備註</td>
	</tr>
	<%=obj.getQueryAll()%>
</table> 
</body>
</html>
