<!--
*<br>程式目的：繳納清冊 
*<br>程式代號：untte003r
*<br>撰寫日期：94/11/24
*<br>程式作者：unique.chiang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.te.UNTTE003R">
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
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.taxYear,"年度");
	/*alertStr += checkDate(form1.q_writeDateE,"填單日期-訖");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");*/
	if (alertStr.length!=0) { 
		alert(alertStr); 
		return false; 
	}
	else {
    	form1.action="untte003p.jsp";
		return true;
	}
}
</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untvp004p.jsp" onSubmit="return checkField()" target="_blank">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">繳納清冊<font color="red">(A4 橫式)</font></td>
	</tr>	
	<tr>
		<td class="td_form" >管理機關：</td>
		<td class="td_form_white" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>年度：</td>
		<td class="td_form_white">
			<%obj.setTaxYear(util.Datetime.getYYY());%>		
			<input class="field" type="text" name="taxYear" size="3" maxlength="3" value="<%=obj.getTaxYear()%>">
		</td>		
	</tr>	
	<tr>
		<td class="td_form">稅別：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="taxKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='TKD' ", obj.getTaxKind())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
