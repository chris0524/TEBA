<!--
程式目的：動產盤存報告表
程式代號：untpd003r.jsp
程式日期：
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD009R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
		return true;
	}
}

</script>
</head>

<body topmargin="0" onLoad="">
<form id="form1" name="form1" method="post" action="untpd009p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="45%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">動產盤存報告表</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="q_propertyKind">
			<%=util.View.getTextOption("00;公用;01;公務用;02;公共用;03;事業用;04;非公用", obj.getQ_propertyKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">換頁方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_chengPageStyle">
			<option value='1' >保管單位</option>
			<option value='2' >財產類別</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
</table>
</td></tr>
</table>
</form>
</body>
</html>



