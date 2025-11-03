<!--
*<br>程式目的：財產結存統計表 
*<br>程式代號：pubgr016r
*<br>撰寫日期：95/03/16
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="pub.gr.PUBGR016R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
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
var mt = ["31", "30", "29", "28"];
var se = ["0331", "0630", "0930", "1231"];

function checkField() {
	var alertStr = "";
	alertStr += checkEmpty(form1.q_enterOrg, "入帳機關");
	alertStr += checkEmpty(form1.q_ownership, "權屬");
	alertStr += checkEmpty(form1.q_differenceKind, "財產區分別");
	alertStr += checkEmpty(form1.q_valuable, "珍貴財產");
	alertStr += checkEmpty(form1.q_isorganmanager, "列印彙總表");
	alertStr += checkEmpty(form1.q_reportType, "報表類別");
	
	alertStr += checkEmpty(form1.q_reportYear, "年度");
	if(form1.q_reportYear.value != ""){
		alertStr += checkYYY(form1.q_reportYear, "年度");
	}
	
	if(form1.q_reportType.value == "1"){
		alertStr += checkEmpty(form1.q_reportMM, "月份");
		if(form1.q_reportMM.value != ""){
			alertStr += checkMM(form1.q_reportMM, "月份");
		}
		
		form1.q_reportSeason.value = "";
		form1.q_reportSeason.style.backgroundColor = "";
	}else if(form1.q_reportType.value == "2"){
		alertStr += checkEmpty(form1.q_reportSeason, "季別");
		
		form1.q_reportMM.value = "";
		form1.q_reportMM.style.backgroundColor = "";
	}else{
		form1.q_reportMM.value = "";
		form1.q_reportMM.style.backgroundColor = "";
		
		form1.q_reportSeason.value = "";
		form1.q_reportSeason.style.backgroundColor = "";
	}
	
	if(alertStr.length != 0){ 
		alert(alertStr); return false; 
	}
	
	// 組合結存日期
	if(form1.q_reportType.value == "1"){
		var index = 0;
		if(form1.q_reportMM.value == "02"){
			var cy = parseInt(form1.q_reportYear.value);
			var wy = cy + 1911;
			
			var s = wy % 4;
			if(s == 0){
				index = 2;
			}else{
				index = 3;
			}
		}else{
			if("01,03,05,07,08,10,12".indexOf(form1.q_reportMM.value) != -1){
				index = 0;
			}else{
				index = 1;
			}
		}
		
		form1.q_enterDateE.value = form1.q_reportYear.value + form1.q_reportMM.value + mt[index];
	}else if(form1.q_reportType.value == "2"){
		form1.q_enterDateE.value = form1.q_reportYear.value + 
								   se[parseInt(form1.q_reportSeason.value)-1];
	}
	
	if(form1.q_enterDateE.value == ""){
		alert("選取資料異常，請重新操作 !");
		return false;
	}
	
	return true;
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

function chReportType(){
	var s = true;
	var t = true;
	
	if(form1.q_reportType.value == "1"){
		s = true;
		t = false;
	}else if(form1.q_reportType.value == "2"){
		s = false;
		t = true;
	}
	
	$("input[name=q_reportMM],select[name=q_reportSeason]").each(function(){
		if($(this).attr("type") == "select"){
			$(this).val("").attr("disabled", s);
		}else{
			$(this).val("").attr("readonly", t);
		}
	});
	
	form1.q_enterDateE.value = "";
	form1.q_reportMM.style.backgroundColor = "";
	form1.q_reportSeason.style.backgroundColor = "";
}

$(function(){
	if(<%=!"Y".equals(Common.get(user.getIsAdminManager()))%>){
		$("#TR001").hide();
	}
	
	chReportType();
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
<form id="form1" name="form1" method="post" action="pubgr016p.jsp" onSubmit="return checkField();" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產結存統計表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr id="TR001">
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q", "q_enterOrg", user.getOrganID(), user.getOrganName(), "N")%>
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			
		    <input type="hidden" name="q_enterDateE">
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
		<td class="queryTDLable"><font color="red">*</font>珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable().equals("")?"N":obj.getQ_valuable())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>列印彙總表：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_isorganmanager">
				<%=util.View.getYNOption(obj.getQ_isorganmanager().equals("")?"N":obj.getQ_isorganmanager())%>
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
		<td class="queryTDLable"><font color="red">*</font>報表類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportType" onChange="chReportType();">
				<%=util.View.getTextOption("1;月報;2;季報", obj.getQ_reportType())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="addAnyChar(this, 3, '0');">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">月份：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportMM" size="2" maxlength="2" value="<%=obj.getQ_reportMM()%>" onChange="addAnyChar(this, 2, '0');">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">季別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportSeason">
				<%=util.View.getTextOption("1;第一季;2;第二季;3;第三季;4;第四季", obj.getQ_reportSeason())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表格式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="reportType" name="reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" onClick="setTimeout('chReportType()',150)">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
