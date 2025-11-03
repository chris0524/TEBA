<!--
*<br>程式目的：國有財產作價撥充（價購）一覽表
*<br>程式代號：untrp012r
*<br>撰寫日期：111/06/06
*<br>程式作者：Kasper.Lee
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP012R">
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function init () {
	if (<%=!user.getGroupID().contains("sys")%>) {
		document.querySelector('[name="btn_q_enterorg"]').disabled = true;
	}
}

function checkField() {
	var alertStr="";
	alertStr += checkEmpty(form1.q_proofYear,"增加單編號-年");
	alertStr += checkEmpty(form1.q_proofDoc,"增加單編號-字");
	alertStr += checkEmpty(form1.q_proofNo,"增加單編號-號");
	if(alertStr.length!=0){ alert(alertStr); return false; }
}


</script>
</head>
<body topmargin="10" onLoad="init();">
<form id="form1" name="form1" method="post" action="untrp012p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<input class="field_P" type="hidden" name="userName" value="<%=user.getUserName()%>">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">作價撥充（價購）<font color="red"></font></td>
	</tr>
	<tr>
        <td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
        <td class="queryTDInput">
            <%=util.View.getPopOrgan("field_Q", "q_enterorg", obj.getQ_enterorg().equals("") ? user.getOrganID() : obj.getQ_enterorg(), obj.getQ_enterorgName().equals("") ? user.getOrganName() : obj.getQ_enterorgName(), "N")%>
        </td>
    </tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>增加單編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_proofYear" size="3" maxlength="3" value="<%=obj.getQ_proofYear()%>">年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			<input class="field_Q" type="text" name="q_proofNo" size="10" maxlength="20" value="<%=obj.getQ_proofNo()%>" onchange="getFrontZero(this.name,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<option value='PDF' selected>PDF</option>
				<option value='XLS' >EXCEL</option>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
