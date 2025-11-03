<!--
*<br>程式目的：物品增減結存表
*<br>程式代號：untgr009r2
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR009R2">
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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var readbg="#EEEEEE";
var editbg="#FFFFFF";
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField() {
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
	alertStr += checkEmpty(form1.q_reportType,"報表類別");
	alertStr += checkDate(form1.q_enterDateS,"異動日期起");
	alertStr += checkDate(form1.q_enterDateE,"異動日期訖");	
	alertStr += checkYYY(form1.q_reportYear,"年度");	
	alertStr += checkMM(form1.q_reportMonth,"月份");

	if(form1.q_reportType.value=="2"){
		alertStr += checkEmpty(form1.q_reportYear,"年度");	
		alertStr += checkEmpty(form1.q_reportSeason,"季別");
	}else if(form1.q_reportType.value=="1"){
		alertStr += checkEmpty(form1.q_reportYear,"年度");	
		alertStr += checkEmpty(form1.q_reportMonth,"月份");
	}
	form1.isorganmanagerT.value=form1.isOrganManager.value;
	if(alertStr.length!=0){ alert(alertStr); return false; }
	
	$("#q_isorganmanager").val($("#q_isorganmanager2").val());
}

function changeReportType(){
	if(form1.q_reportType.value=="2"){
		setFormItem("q_reportMonth,q_enterDateS,q_enterDateE,q_reportMonth","R");
		setFormItem("q_reportYear,q_reportSeason","O");
		form1.q_reportMonth.value = "";
		if(parse(form1.q_reportYear.value).length>0 && parse(form1.q_reportSeason.value).length>0){
			if(form1.q_reportSeason.value=="1"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"0101";
				form1.q_enterDateE.value = form1.q_reportYear.value +"0331";
			}else if(form1.q_reportSeason.value=="2"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"0401";
				form1.q_enterDateE.value = form1.q_reportYear.value +"0630";
			}else if(form1.q_reportSeason.value=="3"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"0701";
				form1.q_enterDateE.value = form1.q_reportYear.value +"0930";
			}else if(form1.q_reportSeason.value=="4"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"1001";
				form1.q_enterDateE.value = form1.q_reportYear.value +"1231";
			}
		}else{
			form1.q_enterDateS.value = "";
			form1.q_enterDateE.value = "";			
		}
	}else if(form1.q_reportType.value=="1"){
		setFormItem("q_enterDateS,q_enterDateE,q_reportSeason","R");
		setFormItem("q_reportYear,q_reportMonth","O");
		form1.q_reportSeason.value = "";
		if(parse(form1.q_reportYear.value).length>0 && parse(form1.q_reportMonth.value).length>0){
			form1.q_enterDateS.value = form1.q_reportYear.value + form1.q_reportMonth.value + "01";
			var nextMonth = getDateAdd("m",1,form1.q_reportYear.value + form1.q_reportMonth.value + "01");
			form1.q_enterDateE.value = getDateAdd("d",-1,nextMonth);
		}else{
			form1.q_enterDateS.value = "";
			form1.q_enterDateE.value = "";
		}
	}
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	form1.q_reportType.value = '1';
	changeReportType();
}

function init(){
	if(form1.isOrganManager.value=="Y"){
		setFormItem("isOrganManager", "O");
	}else{
		setFormItem("isOrganManager", "R");
	}
}

function checkID(){
	if(form1.isAdminManager.value=='Y'){
		document.getElementById('tr1').style.display="";
	}
}

function YearPadLeft(q_reportYear) {
	if (q_reportYear.value != "") {
		q_reportYear.value = padLeft(q_reportYear.value,3);
	}
}

function MonthPadLeft(q_reportMonth) {
	if (q_reportMonth.value != "") {
		q_reportMonth.value = padLeft(q_reportMonth.value,2);
	}
}

/* 左邊補0 */
function padLeft(str, len) {
	str = '' + str;
	if (str.length >= len) {
		return str;
	} else {
		return padLeft("0" + str, len);
	}
}

function checkTableExist(){
	var yyy = form1.q_reportYear.value;
	var mm = form1.q_reportMonth.value;
	if(yyy.length==3 && mm.length==2){
		var x = getRemoteData('../../ajax/jsonUntgr009rMsg.jsp?q_deprYM='+yyy+mm);	
		if (x!=null && x.length!=0) {
			alert(x);
		}
	}
}

function checkDifferenceKind(){
	if("<%=TCGHCommon.getSYSCODEName("02", "ETO")%>"=='<%=user.getOrganID()%>'){
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
	}else{
		return;
	}
}
</script>
</head>
<body topmargin="10" onload="checkID();check_reset();init();">
<form id="form1" name="form1" method="post" action="untgr009p2.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank" autocomplete="off">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
		<td class="td_form" colspan="4" style="text-align:center">物品增減結存表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr id="tr1" style="display:none">
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" colspan="2">
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N')" value="..." title="機關輔助視窗">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isorganmanagerT" value="">
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
			<input type="hidden" name="q_verify" value="Y">
			<input type="hidden" name="q_valuable" value="N">
			<input type="hidden" name="q_dataState" value="1">
			<input class="field_Q" type="hidden" name="q_enterDateS" value="">
			<input class="field_Q" type="hidden" name="q_enterDateE" value="">
			<input class="field_P" type="hidden" name="userName" value="<%=user.getUserName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput" colspan="2">
			<select class="field" type="select" name="q_ownership">
				
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
			&nbsp;　　　　列印彙總表：
			<input type="hidden" id="q_isorganmanager" name="q_isorganmanager" />
			<select class="field_Q" type="select" id="q_isorganmanager2" name="q_isorganmanager2">
				<option value='Y'>是</option>
				<option value='N' selected>否</option>
			</select>
			&nbsp;&nbsp;&nbsp;
			<span id = "P1" style="display:none;">
			<input class="field_Q" type="radio" name="q_enterorgKind1" value="1" ><font color="red">及所屬</font>
			<input class="field_Q" type="radio" name="q_enterorgKind1" value="2" ><font color="red">作業基金</font>
			</span>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="q_differenceKind" onchange="checkDifferenceKind();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' and codeID in ('110','111','120','112','500')","")%>
			</select>
		</td>
	</tr>

	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類別：</td>
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="q_reportType" onChange="changeReportType();">
				<option value='1' >月報</option>
				<option value='2' >季報</option>
			</select>
		</td>	
	</tr>
	
	<tr>
		<td class="queryTDLable">年度：</td>
		<td class="queryTDInput" colspan="2">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="YearPadLeft(this);changeReportType();" onblur="//checkTableExist();">
		</td>
	</tr>		
	<tr>
		<td class="queryTDLable">月份：</td>
		<td class="queryTDInput" colspan="2">
			<input class="field_Q" type="text" name="q_reportMonth" size="2" maxlength="2" value="<%=obj.getQ_reportMonth()%>" onChange="MonthPadLeft(this);changeReportType();" onblur="//checkTableExist();">
		</td>
	</tr>
	
	<tr>
		<td class="queryTDLable">季別：</td>
		<td class="queryTDInput" colspan="2"><select class="field_Q" type="select" name="q_reportSeason" onChange="changeReportType();">
			<%=util.View.getTextOption("1;第一季;2;第二季;3;第三季;4;第四季", obj.getQ_reportSeason())%>
		</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="reportType" name="reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
			</select>
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
