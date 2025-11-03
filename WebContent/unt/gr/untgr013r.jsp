<!--
*<br>程式目的：財產目錄(特種基金用) 
*<br>程式代號：untgr013r
*<br>撰寫日期：103.10.02
*<br>程式作者：Kang Da
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR013R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<% String downloadFilePath = application.getInitParameter("filestoreLocation") + "/106財產目錄_基金.xls"; %>
<% String downloadFilePath1 = application.getInitParameter("filestoreLocation") + "/107財產目錄_基金.xls"; %>
<% String downloadFilePath2 = application.getInitParameter("filestoreLocation") + "/108財產目錄_基金.xls"; %>
<% String downloadFilePath3 = application.getInitParameter("filestoreLocation") + "/109財產目錄_基金.xls"; %>
<% String downloadFilePath4 = application.getInitParameter("filestoreLocation") + "/110財產目錄_基金.xls"; %>

<%
if("".equals(obj.getQ_ownership())){
	String defaultOWA = TCGHCommon.getOwnerShipDefault(user.getOrganID());
	obj.setQ_ownership(defaultOWA);
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
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">

var readbg="#EEEEEE";
var editbg="#FFFFFF";

function checkField() {
	
	$("#q_isorganmanager").val($("#q_isorganmanager2").val());
	
	var alertStr = "";
	alertStr += checkEmpty(form1.q_enterOrg, "入帳機關");
	alertStr += checkEmpty(form1.q_ownership, "權屬");
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

function btn02_event(){
	if (form1.q_reportYear.value == '106' && form1.q_differenceKind.value =='120' && form1.q_enterOrg.value =='A42010000G'){
		openDownloadWindow('','<%=downloadFilePath%>');
		return false;
	} else if (form1.q_reportYear.value == '107' && form1.q_differenceKind.value =='120' && form1.q_enterOrg.value =='A42010000G'){
		openDownloadWindow('','<%=downloadFilePath1%>');
		return false;
	} else if (form1.q_reportYear.value == '108' && form1.q_differenceKind.value =='120' && form1.q_enterOrg.value =='A42010000G'){
		openDownloadWindow('','<%=downloadFilePath2%>');
		return false;
	} else if (form1.q_reportYear.value == '109' && form1.q_differenceKind.value =='120' && form1.q_enterOrg.value =='A42010000G'){
		openDownloadWindow('','<%=downloadFilePath3%>');
		return false;
	} else if (form1.q_reportYear.value == '110' && form1.q_differenceKind.value =='120' && form1.q_enterOrg.value =='A42010000G'){
		openDownloadWindow('','<%=downloadFilePath4%>');
		return false;
	} 
	
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

function checkDifferenceKind(){
	if('<%=TCGHCommon.getSYSCODEName("02", "ETO")%>' == '<%=user.getOrganID()%>'){
		var reportType = form1.q_differenceKind.value;
	    if(reportType === '120') {
	    	document.getElementById("P1").style.display= "";
	    	setFormItem("q_isorganmanager2", "R");
	    	form1.q_isorganmanager2.value = 'N';
	    }else {
	    	document.getElementById("P1").style.display= "none"; 
	    	setFormItem("q_isorganmanager2", "O");
	    	//選擇其他財產區分別後，清空radiobutton選項
	    	$("input[type=radio]").attr("checked",false);
	    }
	} else if ('<%=TCGHCommon.getSYSCODEName("01", "ETO")%>' == '<%=user.getOrganID()%>') {
    	document.getElementById("P1").style.display= "";
    	setFormItem("q_isorganmanager2", "R");
    	form1.q_isorganmanager2.value = 'N';
	} else {
		return;
	}
}
</script>
</head>
<body topmargin="10" onload="closebar();">
<form id="form1" name="form1" method="post" action="untgr013p.jsp" onSubmit="return checkField();" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產目錄(特種基金用)<font color="red">(A4橫式)</font></td>
	</tr>
	<tr id="TR001">
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q", "q_enterOrg", user.getOrganID(), user.getOrganName(), "N")%>
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			
		    <input type="hidden" name="q_valuable" value="N">
		    <input type="hidden" name="q_verify" value="Y">
		    <input type="hidden" name="q_dataState" value="1">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_differenceKind" onchange="checkDifferenceKind();">
 				<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid = 'DFK' and codeid in ('110','111','120','112') order by codeid", obj.getQ_differenceKind())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>列印彙總表：</td>
		<td class="queryTDInput">
			<input type="hidden" id="q_isorganmanager" name="q_isorganmanager" />
			<select class="field_Q" type="select" id="q_isorganmanager2" name="q_isorganmanager2">
				<option value='Y'>是</option>
				<option value='N' selected>否</option>
			</select>
			&nbsp;&nbsp;&nbsp;
			<span id = "P1" style="display:none;">
			<input class="field_Q" type="radio" name="q_enterorgKind1" value="1" ><font color="red">及所屬</font>
			<input class="field_Q" type="radio" name="q_enterorgKind1" value="2" ><font color="red">作業基金</font>
			<input class="field_Q" type="radio" name="q_enterorgKind1" value="3" ><font color="red">含科技辦公室</font>
			</span>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="addAnyChar(this, 3, '0');">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onclick="return btn02_event();" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
