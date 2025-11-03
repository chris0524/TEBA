<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="head.jsp" %>
<jsp:useBean id="obj" scope="request" class="util.General">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String popUnitMan = Common.checkGet(request.getParameter("popUnitMan"));
String popUnitMan2 = Common.checkGet(request.getParameter("popUnitMan2"));
String popUnitMan3 = Common.checkGet(request.getParameter("popUnitMan3"));
String popUnitMan4 = Common.checkGet(request.getParameter("popUnitMan4"));
String queryValue = Common.checkGet(request.getParameter("queryValue"));

String q_ID = Common.checkGet(request.getParameter("q_ID"));
String q_Name = Common.checkGet(request.getParameter("q_Name"));
String q_Incumbencyyn = Common.checkGet(request.getParameter("q_Incumbencyyn"));
String strList = "";
String sql = " select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a  " +
		" left outer join SYSAP_EMP b on a.enterorg = b.organid and a.keeperno = b.empid " +
		" where a.enterorg like '" + Common.esapi(queryValue) + "%'" ;

if("".equals(Common.get(q_ID))){
}else{					sql += " and a.keeperno = '" + Common.get(Common.esapi(q_ID)) +"'";
}
if("".equals(Common.get(q_Name))){
}else{					sql += " and a.keepername like N'%" + Common.get(Common.esapi(q_Name)) +"%'";
}
if("".equals(Common.get(q_Incumbencyyn))){
}else{					
	if ("N".equals(q_Incumbencyyn)) {
		sql += " and ISNULL(a.incumbencyyn,'') = '" + Common.get(Common.esapi(q_Incumbencyyn)) +"'";
	} else {
		sql += " and ISNULL(a.incumbencyyn,'') <> 'N'";
	}
						
}

sql += " order by enterorg, case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername)";

strList = obj.getQueryList(sql, false);
%>
<html>
<head>
<title>保管人員輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>
<script language="javascript" src="../js/tablesoft.js"></script>
<script language="javascript">
function selectProperty(popUnitMan, popUnitManName,popUnitMan2,popUnitMan3,popUnitMan4){
	var popUnitManElement = opener.document.all.item("<%=popUnitMan%>");
	if (isObj(popUnitManElement)) {		
		popUnitManElement.value = popUnitMan;
		if (isObj(popUnitManElement.onchange)) {
			try {
				popUnitManElement.onchange();
			} catch (e) {
			}
		}
	}
	
	var popUnitManElement2 = opener.document.all.item("<%=popUnitMan2%>");
	if (isObj(popUnitManElement2)) {		
		popUnitManElement2.value = popUnitMan;		
		if (isObj(popUnitManElement2.onchange)) {
			try {
				popUnitManElement2.onchange();
			} catch (e) {
			}
		}
	}
	if (isObj(opener.document.all.item("<%=popUnitMan3%>"))) {		
		opener.document.all.item("<%=popUnitMan3%>").value = popUnitMan;		
	}
	if (isObj(opener.document.all.item("<%=popUnitMan4%>"))) {		
		opener.document.all.item("<%=popUnitMan4%>").value = popUnitMan;		
	}
	opener.keeperChange();
	
	window.close();
}

function init() {
	
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3" onLoad="init();">
<form id="form1" name="form1" method="post">
<input type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="popUnitMan" value="<%=popUnitMan%>">
<input type="hidden" name="popUnitMan2" value="<%=popUnitMan2%>">

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:90px">
	<table class="table_form" width="100%" height="100%">	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>人員代碼：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_ID" size="11" maxlength="11" value="<%=Common.get(q_ID)%>">
		</td>		
	</tr>	
	
	<tr>		
		<td class="td_form">&nbsp;<font color="red">*</font>人員名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="q_Name" size="20" maxlength="30" value="<%=Common.get(q_Name)%>"><font color="red">※可輸入關鍵字查詢</font>
		</td>		
	</tr>
	<tr>		
		<td class="td_form">帳號狀態：</td>
		<td class="td_form_white">
			<select name='q_Incumbencyyn'>
				<option value=""  <%=("".equals(q_Incumbencyyn)?"selected='selected'":"")%>>請選擇</option>
				<option value="Y" <%=("Y".equals(q_Incumbencyyn)?"selected='selected'":"")%>>啟用</option>
				<option value="N" <%=("N".equals(q_Incumbencyyn)?"selected='selected'":"")%>>停用</option>
			</select>
		</td>		
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
	<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="window.close()">
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:300px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">人員代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">人員名稱</a></th>
	</tr>		
	</thead>
	<tbody id="listTBODY">
		<%=strList%>
	</tbody>
</table>
</div>
</td></tr>
</table>	
</form>
</body>
</html>
