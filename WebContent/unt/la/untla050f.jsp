<!--
程式目的：土地接收作業
程式代號：untla050f
程式日期：2008/05/28
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA050F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	obj.setEnterOrg(user.getOrganID());
	if ("allInsert".equals(obj.getState())) {
		obj.allInsert();
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"撥出機關");
	alertStr += checkEmpty(form1.q_ownership,"撥出機關權屬");
	alertStr += checkEmpty(form1.q_caseNo,"撥出機關電腦單號");
	alertStr += checkEmpty(form1.ownership,"接收機關權屬");
	alertStr += checkEmpty(form1.propertyNo,"接收財產編號");
	alertStr += checkEmpty(form1.propertyKind,"財產性質");
	//if(form1.propertyKind.value == '03'){
	//	alertStr += checkEmpty(form1.fundType,"基金財產");
	//}
	alertStr += checkEmpty(form1.valuable,"珍貴財產");
	alertStr += checkEmpty(form1.sourceDate,"取得財產日期");
	alertStr += checkEmpty(form1.sourceDoc,"取得財產文號");
	alertStr += checkDate(form1.sourceDate,"取得財產日期");			
	if(alertStr.length!=0){
		alert(alertStr); 
		return false; 
	}else{
		form1.state.value='allInsert';
	}
}

function init(){
	if(form1.state.value == 'allInsert'){
		alert('新增完成');
		//opener.form1.state.value='queryAll'
		opener.form1.submit();
		window.close();
	}else{
	changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');				
		if(form1.manageOrg.value == ''){
			form1.manageOrg.value = form1.enterOrg.value;
			form1.manageOrgName.value = form1.enterOrgName.value;
			form1.valuable.value='N';
		}
		form1.ownership_s.disabled = true;
	}
}

function changeSelect() {
	//財產性質為「03:事業用」時，須控制「基金財產」必須有資料	
	//if(form1.propertyKind.value == "03") form1.fundType.disabled = false;
	//else { form1.fundType.options[0].selected=true; form1.fundType.disabled = true; }

	//財產性質為「01:公務用」時，須控制「基金財產」才可選擇
	if(form1.propertyKind.value == "01") form1.fundType.disabled = false;
	else { form1.fundType.options[0].selected=true; form1.fundType.disabled = true; }
}
</script>
</head>

<body topmargin="0" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--Query區============================================================-->

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer"  style="width: 100%;	height: 350px;">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="queryTDLable" style="text-align:Left" colspan="4">
			撥出機關資料
		</td>
	</tr>
	<tr>
		<td class="td_form" width="20%"><font color="red">*</font>撥出機關：</td>
		<td class="td_form_white" width="30%" >
			<%=util.View.getPopOrgan2("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName())%>
		</td>
		<td class="td_form" width="20%"><font color="red">*</font>撥出機關權屬：</td>
		<td class="td_form_white" width="30%">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="20%"><font color="red">*</font>撥出機關電腦單號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="q_caseNo" size="10" maxlength="10" value="<%=obj.getQ_caseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" style="text-align:Left" colspan="4">
			接收機關資料
		</td>
	</tr>
	<tr>
		<td class="td_form" width="20%">接收機關：</td>
		<td class="td_form_white" width="32%">
			<input class="field_Q" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=user.getOrganID()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=user.getOrganName()%>">]
		</td>
		<td class="td_form" width="20%">接收機關權屬：</td>
		<td class="td_form_white">
			<select class="field_PRO" type="select" name="ownership_s">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			<input class="field_Q" type="hidden" name="ownership" size="1" maxlength="1" value="<%=obj.getOwnership()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="20%">接收機關電腦單號：</td>
		<td class="td_form_white">
			[<input class="field_QRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
		<td class="td_form" width="20%">管理機關：</td>
        <td class="td_form_white">
			<%=util.View.getPopOrgan("field_Q","manageOrg",obj.getManageOrg(),obj.getManageOrgName())%>
        </td>
	</tr>
	<tr>
		<td class="td_form" width="20%"><font color="red">*</font>財產編號：</td>
        <td class="td_form_white" colspan="3">
			<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"1")%> 
        </td>
	</tr>
	<tr>
		<td class="td_form" width="20%"><font color="red">*</font>財產性質：</td>
        <td class="td_form_white">
        	<select class="field" type="select" name="propertyKind" onChange="changeSelect()">
			<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
            </select>
        </td>
        <td class="td_form" width="20%">基金財產：</td>
        <td class="td_form_white">
        	<select class="field" type="select" name="fundType" onChange="">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD' ", obj.getFundType())%>
            </select>
        </td>
	</tr>
	<tr>
		<td class="td_form" width="20%"><font color="red">*</font>珍貴財產：</td>
        <td class="td_form_white" colspan="3">
        	<select class="field" type="select" name="valuable" onChange="">
			<%=util.View.getYNOption(obj.getTaxCredit())%>
            </select></td>
	</tr>
	<tr>
		<td class="td_form" width="20%"><font color="red">*</font>財產取得日期：</td>
        <td class="td_form_white" >
			<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%> 
        </td>
        <td class="td_form" width="20%"><font color="red">*</font>財產取得文號：</td>
        <td class="td_form_white">
			<input class="field" type="text" name="sourceDoc" size="18" maxlength="20" value="<%=obj.getSourceDoc()%>">
        </td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="reset"  name="queryCannel" value="取　　消">
		</td>
	</tr>
	</table>
	</div>
</td></tr>
<!-- ================隱藏欄位==================================================================== -->
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	
<!--Toolbar區===================================================================================-->

<!--List區======================================================================================-->
</table>
</form>
</body>
</html>



