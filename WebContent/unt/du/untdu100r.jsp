<!--
程式目的：保管使用人之單位變更
程式代號：untdu100r
程式日期：0981210
程式作者：Timtoy.Tsai
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU100R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>


<%

obj.setEnterOrg(user.getOrganID());
obj.setEnterOrgName(user.getOrganName());

if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.change();
}

%>

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
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	form1.state.value="update";
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		//alertStr += checkEmpty(form1.enterOrgName,"入帳機關");
		alertStr += checkEmpty(form1.originalKeepUnit,"原保管單位");
		alertStr += checkEmpty(form1.originalKeeper,"原保管人");
		alertStr += checkEmpty(form1.newKeepUnit,"新保管單位");
		if(alertStr.length!=0){ alert(alertStr); return false; }
		return true;	
	}
}
function init(){
	document.getElementById("span_toolbar").style.display = "none";
}
</script>
</head>

<body topmargin="10" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="16%"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" >	
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>" style="text-align:center; ">]
		</td>
	</tr>		
	<tr id="isDisplay" >
		<td class="td_form">保管使用：</td>
		<td class="td_form_white" colspan="3">
		<font color="red">*</font>原保管單位：
		<select class="field_Q" type="select" name="originalKeepUnit" onChange="getKeeper(form1.enterOrg, form1.originalKeepUnit, form1.originalKeeper, '');">
  			<script>                                                          
  				changeKeepUnit(form1.enterOrg, form1.originalKeepBureau, form1.originalKeepUnit,'<%=obj.getOriginalKeepUnit()%>');
        	</script>	
		</select>		
		　&nbsp;&nbsp;&nbsp;<font color="red">*</font>原保管人：
		<select class="field_Q" type="select" name="originalKeeper" >
        	<script>getKeeper(form1.enterOrg, form1.originalKeepUnit, form1.originalKeeper, '<%=obj.getOriginalKeeper()%>');</script>			
        </select>
    	</br>
    	</br>
    	<font color="red">*</font>新保管單位：
		<select class="field_Q" type="select" name="newKeepUnit" onChange="getKeeper(form1.enterOrg,form1.newKeepUnit, form1.newKeeper, '');">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getNewKeepUnit())%>			
		</select>	
		　&nbsp;&nbsp;&nbsp;<font color="white">*</font>新保管人：
		<select class="field_Q" type="select" name="newKeeper" disabled="true">
        	<script>getKeeper(form1.enterOrg, form1.newKeepUnit, form1.newKeeper, '');</script>			
        </select>
		</td>
	</tr>
	</table>
	</div>
</td></tr>
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<span id="span_toolbar">
		<jsp:include page="../../home/toolbar.jsp" />
	</span> |&nbsp;
	<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >&nbsp;|&nbsp;
	<input class="toolbar_default" followPK="false" type="reset"  name="queryCannel" value="取　　消" >&nbsp;|
</td></tr>
</table>
</form>
</body>
</html>

