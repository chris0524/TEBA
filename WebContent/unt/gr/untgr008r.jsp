<!--
*<br>程式目的：國有財產增減結存表 
*<br>程式代號：untgr008r
*<br>撰寫日期：0950308
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR009R1">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setQ_ownership("2");
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
	alertStr += checkDate(form1.q_enterDateS,"異動日期起");
	alertStr += checkDate(form1.q_enterDateE,"異動日期訖");
	alertStr += checkYYY(form1.q_reportYear,"年度");
	if(form1.q_reportType.value=="3"){
		alertStr += checkEmpty(form1.q_enterDateS,"異動日期起");
		alertStr += checkEmpty(form1.q_enterDateE,"異動日期訖");
	}else if(form1.q_reportType.value=="2"){
		alertStr += checkEmpty(form1.q_reportYear,"年度");
	}else if(form1.q_reportType.value=="1"){
		alertStr += checkEmpty(form1.q_periodType,"期別");
		alertStr += checkEmpty(form1.q_reportYear,"年度");	
	}
	form1.isorganmanagerT.value=form1.q_isorganmanager.value;
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function changeReportType(){
	if(form1.q_reportType.value=="3"){
		setFormItem("q_enterDateS,q_enterDateE,btn_q_enterDateS,btn_q_enterDateE","O");
		setFormItem("q_reportYear,q_periodType","R");
		form1.q_reportYear.value = "";
		form1.q_periodType.value = "";
	}else if(form1.q_reportType.value=="2"){
		setFormItem("q_enterDateS,q_enterDateE,btn_q_enterDateS,btn_q_enterDateE,q_periodType","R");
		setFormItem("q_reportYear","O");
		form1.q_periodType.value = "";
		if(parse(form1.q_reportYear.value).length>0){
			form1.q_enterDateS.value = form1.q_reportYear.value+"0101";
			form1.q_enterDateE.value = form1.q_reportYear.value+"1231";
		}else{
			form1.q_enterDateS.value = "";
			form1.q_enterDateE.value = "";
		}
	}else if(form1.q_reportType.value=="1"){
		setFormItem("q_enterDateS,q_enterDateE,btn_q_enterDateS,btn_q_enterDateE","R");
		setFormItem("q_reportYear,q_periodType","O");
		if(parse(form1.q_reportYear.value).length>0 && parse(form1.q_periodType.value).length>0){
			if(form1.q_periodType.value=="1"){
				form1.q_enterDateS.value = form1.q_reportYear.value+"0101";
				form1.q_enterDateE.value = form1.q_reportYear.value+"0630";
			}else if(form1.q_periodType.value=="2"){
				form1.q_enterDateS.value = form1.q_reportYear.value+"0701";
				form1.q_enterDateE.value = form1.q_reportYear.value+"1231";
			}
		}else{
			form1.q_enterDateS.value = "";
			form1.q_enterDateE.value = "";
		}
	}
}

function changeOwnership(){
	if(form1.q_ownership.value == "1"){
		form1.q_reportType.value="2";
	}else if(form1.q_ownership.value == "2"){
		form1.q_reportType.value="1";
	}
	changeReportType();
}

function alteredEnterOrg(){

}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_ownership.value = '2';
	changeOwnership();
	form1.q_reportType.value = '1';
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
<body topmargin="10" onLoad="check_reset();">

<form id="form1" name="form1" method="post" action="untgr008p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>

	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isorganmanagerT" value="">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input class="field_Q" type="hidden" name="q_valueable" value="N">
    <input class="field_Q" type="hidden" name="q_verify" value="Y">
    <input class="field_Q" type="hidden" name="q_ownership" value="2">
    <input class="field_Q" type="hidden" name="q_propertyKind" value="99">
    <input class="field_Q" type="hidden" name="q_fundType" value="99">		    
    <input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
    
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">國有財產增減結存表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
<!-- 		
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=alteredEnterOrg();")%>
-->
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">上層機關彙總表：</td>
		<td class="queryTDInput">		
			<select class="field_Q" type="select" name="q_isorganmanager">
	            <option value='Y'>是</option>
				<option value='N' selected>否</option>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportType" onChange="changeReportType();">
	            <option value='1' selected>半年報</option>
	            <option value='2' >年報</option>
	            <option value='3'>其他</option>	            
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="addChar(this,3);changeReportType();">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">期別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_periodType" onChange="changeReportType();">
				<%=util.View.getTextOption("1;半年報(上半年);2;半年報(下半年)",obj.getQ_periodType())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">異動日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
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
