<!--
*<br>程式目的：財產資料清查作業
*<br>程式代號：
*<br>撰寫日期：104/12/3
*<br>程式作者：Kang Da
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.rp.SYSRP001R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript"  src="../../js/function.js"></script>
<script type="text/javascript"  src="../../js/tablesoft.js"></script>
<script type="text/javascript"  src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" >

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	
	var alertStr="";var as = "";
	alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
	if ($('[name="q_enterorgs"]:checked').length == 0) {
		alertStr += "至少勾選一個機關";
	}
	if (alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}
}

function init(){
	
}
</script>

</head>
<body topmargin="10" onLoad="init();">
<form id="form1" name="form1" method="post" action="sysrp001p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_self">
<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">

</td></tr></table>
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產資料清查作業 <font color="red"></font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_differenceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' and codeID in ('110','111','120','112')","")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>機關：</td>
		<td class="queryTDInput">
			<input type="checkbox" name="q_enterorgs" value="41113078">災防中心<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("05", "ETO")%>">竹科實中<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("07", "ETO")%>">南科實中<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("08", "ETO")%>">屏科實中<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("09", "ETO")%>">嘉科實中<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("06", "ETO")%>">中科實中<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("01", "ETO")%>">科技部<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("02", "ETO")%>">竹科管理局<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("03", "ETO")%>">中科管理局<br>
			<input type="checkbox" name="q_enterorgs" value="<%=TCGHCommon.getSYSCODEName("04", "ETO")%>">南科管理局<br>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
