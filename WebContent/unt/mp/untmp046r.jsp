<!--
程式目的：資訊設備明細清冊
程式代號：untmp046r
程式日期：
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP046R">
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
	alertStr += checkEmpty(form1.q_propertyNo,"財產編號");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	if( form1.q_dataType.value == '3' ){
		alertStr += checkEmpty(form1.q_endDate,"結存日期");
		alertStr += checkDate(form1.q_endDate,"結存日期");
	}else if( form1.q_dataType.value == '1' || form1.q_dataType.value == '2' ){
		alertStr += checkEmpty(form1.q_chengDateS,"異動日期-起 ");
		alertStr += checkEmpty(form1.q_chengDateE,"異動日期-訖 ");
		alertStr += checkDate(form1.q_chengDateS,"異動日期-起 ");
		alertStr += checkDate(form1.q_chengDateE,"異動日期-訖 ");
	}
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
		return true;
	}
}

function init(){
	form1.q_ownership.value='1';
	form1.q_chengDateS.disabled=true; 
	form1.btn_q_chengDateS.disabled=true;
	form1.q_chengDateE.disabled=true;
	form1.btn_q_chengDateE.disabled=true;
}

function managerDate(){
	if(form1.q_dataType.value == '3'){
		form1.q_chengDateS.value=''; 
		form1.q_chengDateE.value='';
		form1.q_endDate.disabled=false; 
		form1.btn_q_endDate.disabled=false;
		form1.q_chengDateS.disabled=true;
		form1.btn_q_chengDateS.disabled=true;
		form1.q_chengDateE.disabled=true;
		form1.btn_q_chengDateE.disabled=true;
	}else{
		form1.q_endDate.value='';
		form1.q_endDate.disabled=true; 
		form1.q_chengDateS.disabled=false; 
		form1.q_chengDateE.disabled=false;
		form1.btn_q_chengDateS.disabled=false;
		form1.btn_q_chengDateE.disabled=false;
		form1.btn_q_endDate.disabled=true;
	}
}
</script>
</head>

<body topmargin="0" onLoad="init();">
<form id="form1" name="form1" action="untmp046p.jsp"  method="post" onSubmit="return checkField();" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">資訊設備明細清冊</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>資料類別：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_dataType" onChange="managerDate()">
 				<option value='1'>增加數</option>
 				<option value='2'>減少數</option>
				<option value='3' selected>結存數</option>
 			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">異動日期：</div></td>
 		<td class="queryTDInput">
			起：<%=util.View.getPopCalndar("field_Q","q_chengDateS",obj.getQ_chengDateS())%>&nbsp;~&nbsp;
		           訖：<%=util.View.getPopCalndar("field_Q","q_chengDateE",obj.getQ_chengDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">結存日期：</div></td>
 		<td class="queryTDInput">
 			<%=util.View.getPopCalndar("field_Q","q_endDate",obj.getQ_endDate())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>入帳機關：</div></td>
 		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName())%>
		</td>
	</tr>
		<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>財產編號：</div></td>
 		<td class="queryTDInput">
			<%=util.View.getPopProperty("field_Q","q_propertyNo",obj.getQ_propertyNo(),obj.getQ_propertyNoName(),"3,4,5")%>
		</td>
	</tr>

	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>權屬：</div></td>
 		<td class="queryTDInput">
 		<select class="field_Q" type="select" name="q_ownership">
 			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
		</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>分頁模式：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_chengPagType" >
 				<option value='1'>依細項分頁</option>
 				<option value='2'>依保管單位分頁</option>
 				<option value='3'>依細項及保管單位分頁</option>
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



