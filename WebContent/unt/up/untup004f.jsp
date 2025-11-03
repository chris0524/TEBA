<!--
*<br>程式目的：財產編號批次修正作業 
*<br>程式代號：untup004f
*<br>撰寫日期：95/02/14
*<br>程式作者：stephen
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.up.UNTUP004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>





<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>

<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField() {
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.oldPropertyNo,"原財產編號");	
	alertStr += checkEmpty(form1.newPropertyNo,"新財產編號");		

	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function init() {
	//form1.q_ownership.value="1";
	//form1.q_enterOrg.value="<%=user.getOrganID()%>";
	//form1.q_enterOrgName.value="<%=user.getOrganName()%>";
}

</script>
</head>
<body topmargin="10" onload="init();">
<form id="form1" name="form1" method="post" action="untup004f.jsp" onSubmit="return checkField()" target="_blank">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
<table class="bg" width="80%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center"></font>財產編號批次修正作業</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>原財產編號：</td>		
		<td class="td_form_white" colspan="3">		
			<%=util.View.getPopProperty("field_Q","oldPropertyNo",obj.getOldPropertyNo(),obj.getOldPropertyNoName(),"")%>
		</td>
	</tr>	
	<tr>
		<td class="td_form"><font color="red">*</font>新財產編號：</td>		
		<td class="td_form_white" colspan="3">		
			<%=util.View.getPopProperty("field_Q","newPropertyNo",obj.getNewPropertyNo(),obj.getNewPropertyNoName(),"")%>
		</td>
	</tr>		
    <tr>	
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="批次修正" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
