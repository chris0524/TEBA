<!--
*<br>程式目的：明細序號修正作業
*<br>程式代號：sysap301r
*<br>撰寫日期：104.05.14
*<br>程式作者：CHLin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP301F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String result = "";
	if (user.getIsAdminManager().equals("Y")) {	
		String actionType = Common.get(request.getParameter("actionType"));
		System.out.println("ActionType:" + actionType);
		if ("doFix".equals(actionType)) {
			result = obj.doFix();
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

<script language="javascript">

	function checkField() {
		var alertStr = '';
		alertStr += checkEmpty(form1.targetTable,"修正對象");
		alertStr += checkEmpty(form1.isExecUpdate,"是否更新資料");
		if(alertStr.length!=0){ alert(alertStr); return false; }
		form1.actionType.value = 'doFix';
		return true;
	}
	
	function init() {
		form1.actionType = '';
	}

</script>
</head>
<body topmargin="10" onload="init()">

<form id="form1" name="form1" method="post" action="sysap301f.jsp" onSubmit="return checkField()" >
	<input type="hidden" name="actionType" >
	<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
		<table class="queryTable"  border="1">
		<tr>
	        <td class="td_form" colspan="4" style="text-align:center">序號(CaseSerialNo)修正作業</td>
		</tr>
		<tr>
			<td class="td_form">修正對象：</td> 
			<td class="td_form_white" colspan="3">
				<select class="field_Q" type="select" name="targetTable">
					<%= View.getTextOption("UNTNE_MOVEDETAIL;非消移動單明細;UNTNE_REDUCEDETAIL;非消減損單明細", obj.getTargetTable()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_form">是否更新資料：</td>
			<td class="td_form_white" colspan="3">
				<select class="field_Q" type="select" name="isExecUpdate">
				　　<%=util.View.getYNOption(obj.getIsExecUpdate())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="queryTDInput" colspan="4" style="text-align:center;">
				<input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
				<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			</td>
		</tr>
		</table>
	</td></tr></table>	
</form>
<div><%=result %></div>
</body>
</html>
<%
	}
%>
