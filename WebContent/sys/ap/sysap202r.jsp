<!--
*<br>程式目的：保固維護記錄表
*<br>程式代號：sysap202r
*<br>撰寫日期：0950918
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP202R">
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
	var strCheck="";
	alertStr += checkEmpty(form1.m_date,"維護日期");
	alertStr += checkDate(form1.m_date,"維護日期");
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

</script>
</head>
<body topmargin="10" onload="">

<form id="form1" name="form1" method="post" action="sysap202p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">保固維護記錄表<font color="red">(A4直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" width="20%">維護日期：</td>
		<td class="queryTDInput" width="30%">
			<%=util.View.getPopCalndar("field_Q","m_date",obj.getM_date())%>
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
</body>
</html>
