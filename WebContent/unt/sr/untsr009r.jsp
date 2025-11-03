<!--
*<br>程式目的：國產署交換媒體檔 - 目錄總表
*<br>程式代號：untsr009r
*<br>撰寫日期：103.09.30
*<br>程式作者：Kang Da
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR009R">
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
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
function checkField() {
	var alertStr = "";
	alertStr += checkEmpty(form1.q_enterOrg, "入帳機關");
	alertStr += checkEmpty(form1.q_differenceKind, "財產區分別");
	alertStr += checkEmpty(form1.q_isorganmanager, "列印彙總表");
	alertStr += checkEmpty(form1.q_reportYear, "年度");
	if(form1.q_reportYear.value != ""){
		alertStr += checkYYY(form1.q_reportYear, "年度");
	}
	
	if(alertStr.length != 0){ 
		alert(alertStr); return false; 
	}
	
	return true;
}

function init(){
	
}

function addAnyChar(o, l, val){
	var sv = o.value;
	var sk = sv.length;
	if(sk != 0){
		for(var i=0; i<(l - sk); i++){
			sv = val + sv;
		}
	}
	o.value = sv;
}

$(function(){
	if(<%=!"Y".equals(Common.get(user.getIsAdminManager()))%>){
		$("#TR001").hide();
	}
});
</script>
</head>
<body topmargin="10" onload="init();closebar();">
<form id="form1" name="form1" method="post" action="untsr009p.jsp" onSubmit="return checkField();" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">國產署交換媒體檔 - 目錄總表</td>
	</tr>
	<tr id="TR001">
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q", "q_enterOrg", user.getOrganID(), user.getOrganName(), "N")%>
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			
			<input type="hidden" name="q_ownership" value="1">
		    <input type="hidden" name="q_verify" value="Y">
		    <input type="hidden" name="q_dataState" value="1">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_differenceKind">
 				<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid = 'DFK' and codeid in ('110','120') order by codeid", obj.getQ_differenceKind())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>列印彙總表：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_isorganmanager">
				<%=util.View.getYNOption(obj.getQ_isorganmanager().equals("")?"N":obj.getQ_isorganmanager())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="addAnyChar(this, 3, '0')">
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
