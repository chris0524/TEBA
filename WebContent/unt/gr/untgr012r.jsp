<!--
*<br>程式目的：市有財產目錄(營業基金及非營業循環基金用)  
*<br>程式代號：untgr012r
*<br>撰寫日期：95/05/15
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR013R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setQ_ownership("1");
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var readbg="#EEEEEE";
var editbg="#FFFFFF";
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_reportType,"報表類別");	
	alertStr += checkDate(form1.q_reportDate,"移交日期");	
	alertStr += checkYYY(form1.q_reportYear,"年度");			
	if(form1.q_reportType.value == "1"){
		alertStr += checkEmpty(form1.q_reportYear,"年度");
	}else if(form1.q_reportType.value == "2"){
		alertStr += checkEmpty(form1.q_reportDate,"移交日期");	
	}
	form1.isorganmanagerT.value=form1.q_isorganmanager.value;
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function changeReportType(){
	if(form1.q_reportType.value == "1"){
		setFormItem("q_reportDate,btn_q_reportDate","R");
		setFormItem("q_reportYear","O");
		form1.q_reportDate.value = "";
		form1.q_enterDateS.value = form1.q_reportYear.value + "0101";
		form1.q_enterDateE.value = form1.q_reportYear.value + "1231";
	}else if(form1.q_reportType.value == "2"){
		setFormItem("q_reportYear","R");
		setFormItem("q_reportDate,btn_q_reportDate","O");
		form1.q_reportYear.value = "";
		form1.q_enterDateS.value = form1.q_reportDate.value.substr(0,3)+"0101";
		form1.q_enterDateE.value = form1.q_reportDate.value;
	}
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_reportType.value='1';
	changeReportType();
	init();
}

function init(){
	if(form1.isOrganManager.value=="Y"){
		setFormItem("q_isorganmanager", "O");
	}else{
		setFormItem("q_isorganmanager", "R");
	}
	//form1.q_isorganmanager.value=form1.isOrganManager.value;
}
</script>
</head>
<body topmargin="10" onload="check_reset();">
<form id="form1" name="form1" method="post" action="untgr013p.jsp"  onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產目錄(營業基金及非營業循環基金用)<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isorganmanagerT" value="">		
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
		    <input class="field_Q" type="hidden" name="q_verify" value="Y">
		    <input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
		    <input class="field_Q" type="hidden" name="q_enterDateS" value="<%=obj.getQ_enterDateS()%>">
		    <input class="field_Q" type="hidden" name="q_enterDateE" value="<%=obj.getQ_enterDateE()%>">
		    <input class="field_Q" type="hidden" name="q_valueable" value="N">
		    <input class="field_Q" type="hidden" name="q_propertyKind" value="01">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
			&nbsp;　　　　上層機關彙總表：
			<select class="field_Q" type="select" name="q_isorganmanager">
	            <option value='Y'>是</option>
				<option value='N' selected>否</option>
			</select>			
		</td>
	</tr>
	<!--  <tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getTextOption("00;公用;01;公務用;02;公共用;03;事業用;04;非公用;",obj.getQ_propertyKind())%>
			</select>
		</td>
	</tr>
	-->
	<tr>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportType" onChange="changeReportType();">
	            <option value='1'>年報</option>
	            <option value='2'>移交清冊</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="changeReportType();">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">移交日期：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportDate" size="7" maxlength="7" value="<%=obj.getQ_reportDate()%>" onChange="changeReportType();">
			<input class="field_Q" type="button" name="btn_q_reportDate" onclick="popCalndar('q_reportDate');changeReportType();" value="..." title="萬年曆輔助視窗">
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
