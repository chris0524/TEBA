<!--
程式目的：單位財產資料轉入作業－檢視轉入結果
程式代號：untup002f
程式日期：0950710
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.up.UNTUP002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setEnterOrg(user.getOrganID());
obj.setEnterOrgName(user.getOrganName());

String btnQuery = util.Common.get(request.getParameter("btnQuery"));
if (!"".equals(btnQuery)) {
	if (!"".equals(obj.getQ_enterOrg())) obj.setEnterOrg(obj.getQ_enterOrg());
	if (!"".equals(obj.getQ_enterOrgName())) obj.setEnterOrgName(obj.getQ_enterOrgName());	
}
%>
<html>
<head>
<title>單位財產資料轉入作業－檢視轉入結果</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript">
function checkField(){
	if (isObj(form1.q_enterOrg) && form1.q_enterOrg.value!="") return true;
	else return false;
}

function queryDetail(enterOrg, upType) {
	if (upType!=null && upType.length==2) {
		form1.action="untup003f.jsp?upType="+upType;
		form1.submit();
	}
}
</script>

</head>

<body topmargin="0">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table width="98%">
<tr><td class="bg">
	<table class="table_form" width="100%" height="100%">
	<tr>
        <td class="td_form" colspan="2"><div align="center">單位財產資料轉入作業－檢視暫存檔</div></td>
	</tr>
<%
if ("Y".equals(user.getIsAdminManager()) || "Y".equals(user.getIsOrganManager())) {
%>	
	<tr>
		<td width="20%" class="td_form"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getEnterOrg().equals("")?user.getOrganID():obj.getEnterOrg(),obj.getEnterOrgName().equals("")?user.getOrganName():obj.getEnterOrgName())%>　　<input class="toolbar_default" followpk="false" type="submit" name="btnQuery" value="查詢" >
		</td>
	</tr>	
<%
}
%>    
</table>
</td></tr>
<tr><td class="bg">
<div id="listContainer" style="height:360px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">轉入類別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">轉入日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">合格筆數</a></th>
	    <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">錯誤筆數</a></th>
	    <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">上傳者</a></th>
	</tr>		
	</thead>
	<tbody id="listTBODY">
		<%=obj.getUNTUP_List(obj.getEnterOrg())%>
	</tbody>
</table>
</div>
</td></tr>
</table>
<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
<input type="hidden" name="enterOrgName" value="<%=obj.getEnterOrgName()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
</form>
</body>
</html>
