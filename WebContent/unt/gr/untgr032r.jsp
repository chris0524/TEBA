<!--
*<br>程式目的：操作紀錄表
*<br>程式代號：untgr032r
*<br>撰寫日期：110/05/20
*<br>程式作者：Kasper.Lee
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR032R">
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
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField() {
	var alertStr="";
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

</script>
</head>
<body topmargin="10">
<form id="form1" name="form1" method="post" action="untgr032p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<input class="field_P" type="hidden" name="userName" value="<%=user.getUserName()%>">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">操作紀錄表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
        <td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
        <td class="queryTDInput">
            <%=util.View.getPopOrgan("field_Q", "q_enterorg", obj.getQ_enterorg().equals("") ? user.getOrganID() : obj.getQ_enterorg(), obj.getQ_enterorgName().equals("") ? user.getOrganName() : obj.getQ_enterorgName(), "N")%>
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">操作日期：</td>
        <td class="queryTDInput">
			<%=util.View.getPopCalndar("field_Q", "q_changeDateS", "") %>&nbsp; ~ &nbsp;
			<%=util.View.getPopCalndar("field_Q", "q_changeDateE", "") %>
        </td>
    </tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
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
