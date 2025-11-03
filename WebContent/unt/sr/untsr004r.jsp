<!--
*<br>程式目的：國有財產目錄總表 
*<br>程式代號：untsr004r
*<br>撰寫日期：
*<br>程式作者：YuanRen
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR004R">
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
<script language="javascript" src="../../js/TJS.js"></script>
<script language="javascript" src="../../js/getAutoCheckColumn.js"></script>
<script language="javascript">
var readbg="#EEEEEE";
var editbg="#FFFFFF";
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_reportYear,"年度");
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

//列印報表按鈕
function printReportChang(str){
	if(checkField()==false){
		
	}else{
		var url = "untsr004p.jsp?processType="+str+"&q_reportYear="+form1.q_reportYear.value;
		window.open(url);
	}
}
</script>

</head>
<body topmargin="10" onload="init();">
<form id="form1" name="form1" method="post" action="untsr004p.jsp" onReset="init();" onSubmit="return checkField();" >
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">國有財產目錄總表<font color="blue" size="2">(A4橫式)</font></td>
	</tr>
	
	<tr>
		<td class="queryTDLable" width="30%"><font color="red">*</font>年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="doInt(this.name,'年度');doFrontZero(this.name,3);">			
			<font color="red">(YYY)</font>
		</td>
	</tr>
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">報表列印</td>
	</tr>
	
	<tr>
		<td class="queryTDInput" style="text-align:center" colspan="2">
			<input class="toolbar_default" followPK="false" type="button" name="doProcess01" value="            列印國有財產增減結存表             " onclick="printReportChang('ALL');">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" style="text-align:center" colspan="2">
			<input class="toolbar_default" followPK="false" type="button" name="doProcess02" value="   列印國有財產增減結存表(珍貴財產)  " onclick="printReportChang('VALUABLE');">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" style="text-align:center" colspan="2">
			<input class="toolbar_default" followPK="false" type="button" name="doProcess03" value="列印國有財產增減結存表 (非珍貴財產)" onclick="printReportChang('UNVALUABLE');">
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isorganmanagerT" value="">		
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="q_ownership" value="1">
			<input type="hidden" name="q_valueable" value="N">
		    <input type="hidden" name="q_verify" value="Y">
		    <input type="hidden" name="editID" value="<%=user.getUserID()%>">
		    
		    <input type="hidden" name="q_enterDateS" value="<%=obj.getQ_enterDateS()%>">
		    <input type="hidden" name="q_enterDateE" value="<%=obj.getQ_enterDateE()%>">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
