<!--
程式目的：
程式代號：untpd011r.jsp
程式日期：
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD011F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	if("upDataPdresult".equals(obj.getState())){
		obj.upDataPdresult();
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

<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.checkDateS,"盤點日期起");
	alertStr += checkEmpty(form1.checkDateE,"盤點日期訖");
	alertStr += checkDate(form1.checkDateS,"盤點日期起");
	alertStr += checkDate(form1.checkDateE,"盤點日期訖");
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
		form1.state.value='upDataPdresult';
		return true;
	}
}
function init(){
	if(form1.state.value=='upDataPdresultSuccess'){
		window.close();
	}
	form1.enterOrgName.value="<%=obj.getOrgName()%>";
}
</script>
</head>

<body topmargin="0" onLoad="showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
<!-- -----------------------隱藏欄位區-------------------------- -->
	<input type="hidden" name="state" value="<%=obj.getState()%>">
<!-- --------------------------------------------------------- -->
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">更新動產主檔資料</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">盤點日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","checkDateS",obj.getCheckDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","checkDateE",obj.getCheckDateE())%>
		</td>		
	</tr> 
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" onClick="form1.submit()">
		</td>
	</tr>
</table>
</td></tr>
</table>
</form>
</body>
</html>



