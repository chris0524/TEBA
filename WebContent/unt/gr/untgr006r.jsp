<!--
*<br>程式目的：市有財產增減表 
*<br>程式代號：untgr006r
*<br>撰寫日期：0950313
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR007R">
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
	alertStr += checkMM(form1.q_reportMonth,"月份");
	if(form1.q_reportType.value=="4"){
		alertStr += checkEmpty(form1.q_enterDateS,"異動日期起");
		alertStr += checkEmpty(form1.q_enterDateE,"異動日期訖");
	}else if(form1.q_reportType.value=="3"){
		alertStr += checkEmpty(form1.q_reportYear,"年度");
		alertStr += checkEmpty(form1.q_periodType,"期別");
	}else if(form1.q_reportType.value=="2"){
		alertStr += checkEmpty(form1.q_reportYear,"年度");	
		alertStr += checkEmpty(form1.q_reportSeason,"季別");
	}else if(form1.q_reportType.value=="1"){
		alertStr += checkEmpty(form1.q_reportYear,"年度");	
		alertStr += checkEmpty(form1.q_reportMonth,"月份");
	}
	form1.isorganmanagerT.value=form1.q_isorganmanager.value;
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function changeReportType(){
	if(form1.q_reportType.value=="4"){
		setFormItem("q_enterDateS,q_enterDateE,btn_q_enterDateS,btn_q_enterDateE","O");
		setFormItem("q_reportYear,q_periodType,q_reportMonth,q_reportSeason","R");
		form1.q_reportYear.value = "";
		form1.q_periodType.value = "";
		form1.q_reportMonth.value = "";
		form1.q_reportSeason.value = "";
	}else if(form1.q_reportType.value=="3"){
		setFormItem("q_enterDateS,q_enterDateE,btn_q_enterDateS,btn_q_enterDateE,q_reportMonth,q_reportSeason","R");
		setFormItem("q_reportYear,q_periodType","O");
		form1.q_reportMonth.value = "";
		form1.q_reportSeason.value = "";
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
	}else if(form1.q_reportType.value=="2"){
		setFormItem("q_reportMonth,q_enterDateS,q_enterDateE,btn_q_enterDateS,btn_q_enterDateE,q_reportMonth,q_periodType","R");
		setFormItem("q_reportYear,q_reportSeason","O");
		form1.q_reportMonth.value = "";
		form1.q_periodType.value = "";
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
		setFormItem("q_enterDateS,q_enterDateE,btn_q_enterDateS,btn_q_enterDateE,q_periodType,q_reportSeason","R");
		setFormItem("q_reportYear,q_reportMonth","O");
		form1.q_reportSeason.value = "";
		form1.q_periodType.value = "";
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

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_reportType.value = '1';
	changeReportType();
}

function init(){
	if(form1.isOrganManager.value=="Y"){
		setFormItem("q_isorganmanager", "O");
	}else{
		setFormItem("q_isorganmanager", "R");
	}
}
</script>
</head>
<body topmargin="10" onload="check_reset();init();">

<form id="form1" name="form1" method="post" action="untgr007p.jsp" onReset="check_reset();init();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產增減表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isorganmanagerT" value="">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input class="field_Q" type="hidden" name="q_valueable" value="N">
		    <input class="field_Q" type="hidden" name="q_verify" value="Y">
		    <input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption("".equals(obj.getQ_ownership())?"3":obj.getQ_ownership())%>			
			</select>
			&nbsp;　　　　上層機關彙總表：
			<select class="field_Q" type="select" name="q_isorganmanager">
	            <option value='Y'>是</option>
				<option value='N' selected>否</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>查詢種類：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind1">
            <option value='1'>財產</option>
            <option value='2'>物品</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getTextOption("00;公用;01;公務用;02;公共用;03;事業用;04;非公用;",obj.getQ_propertyKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
			<br>
			<input id="P1" class="field_Q" type="radio" name="q_fundTypeKind" value="99" checked>全部&nbsp;
		    <input id="P2" class="field_Q" type="radio" name="q_fundTypeKind" value="1" >基金財產&nbsp;
		    <input id="P3" class="field_Q" type="radio" name="q_fundTypeKind" value="2" >一般財產
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportType" onChange="changeReportType();">
	            <option value='1'>月報</option>
	            <option value='2'>季報</option>
	            <option value='3'>半年報</option>
	            <option value='4'>其他</option>	            			
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
		<td class="queryTDLable">月份：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportMonth" size="2" maxlength="2" value="<%=obj.getQ_reportMonth()%>" onChange="changeReportType();">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">季別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportSeason" onChange="changeReportType();">
				<%=util.View.getTextOption("1;第一季;2;第二季;3;第三季;4;第四季",obj.getQ_reportSeason())%>
			</select>
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
