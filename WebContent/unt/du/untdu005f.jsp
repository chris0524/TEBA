<!--
程式目的：
程式代號：
程式日期：
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("mpUPcomputerType".equals(obj.getState())) {
	obj.mpUPcomputerType();
}
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
<script language="javascript" src="../../js/sList2.js"></script>

<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_lotNoS,"批號");
	alertStr += checkEmpty(form1.q_lotNoE,"批號");
	alertStr += checkEmpty(form1.q_computerType,"細項");
	alertStr += checkEmpty(form1.q_propertyNo,"財產編號")
	if(alertStr.length!=0){ 
		alert(alertStr);
	 	return false; 
	 }else{
		document.all('state').value='mpUPcomputerType';
	 }
}
function init(){
	form1.q_enterOrg.value="<%=user.getOrganID()%>";
	form1.q_enterOrgName.value="<%=user.getOrganName()%>";
}
</script>
</head>

<body topmargin="0" onLoad="init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<input type="hidden" name="state" value="<%=obj.getState()%>">

<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">動產電腦設備增補資料</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_RO","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_QRO" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>	
		</td>
	</tr>
	<!--tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_caseNo" size="12" maxlength="10" value="<%=obj.getQ_caseNo()%>">
		</td>
	</tr-->
		<tr>
		<td class="queryTDLable">財產編號：</td>
		<td class="queryTDInput">
			<input class="field_P" type="text" name="q_propertyNo" size="10" maxlength="11" value="<%=obj.getQ_propertyNo()%>" onChange="getProperty('q_propertyNo','q_propertyNoName','3,4,5');selectComputerType( form1.q_computerType, this ,'' );">
			<input class="field_P" type="button" name="btn_q_propertyNo" onclick="popProperty('q_propertyNo','q_propertyNoName','3,4,5')" value="..." title="財產編號輔助視窗">
			[<input class="field_PRO" type="text" name="q_propertyNoName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoName()%>">]
		<!--[<input class="field_QRO" type="text" name="q_propertyNo" size="10" maxlength="11" value="314010103">-->
		<!--<input class="field_QRO" type="text" name="q_propertyNoName" size="20" maxlength="50" value="個人電腦">]-->
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">批號：</td>
		<td class="queryTDInput">
			起：<input class="field_Q" type="text" name="q_lotNoS" size="7" maxlength="7" value="<%=obj.getQ_lotNoS()%>">&nbsp;~&nbsp;
			訖：<input class="field_Q" type="text" name="q_lotNoE" size="7" maxlength="7" value="<%=obj.getQ_lotNoE()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">細項：</td>
		<td class="queryTDInput">
			<select class="field" type="select" name="q_computerType" >
			<!--%=util.View.getTextOption("01;桌上型;02;攜帶型;03;掌上型", obj.getQ_computerType())%-->
			<script>selectComputerType( form1.q_computerType, form1.q_propertyNo.value ,'<%=obj.getQ_computerType()%>' );</script>
			</select>
		</td>
	</tr>
	<td class="queryTDInput" colspan="4" style="text-align:center;">
		<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確        定" >
		<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
	</td>
</table>
</table>	
</form>
</body>
</html>



