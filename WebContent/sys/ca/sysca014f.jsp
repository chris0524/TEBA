<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA014F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setEnterOrg(user.getOrganID());
obj.setEnterOrgName(user.getOrganName());

if ("update".equals(obj.getState())) {
	obj.updateDate();
}else{
	obj.queryData();
}
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="1"/>
<meta http-equiv="pragma" content="no-cache"/>
<title>機關參數設定</title>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	form1.state.value="update";
	beforeSubmit();
}

function queryOne(propertyNo, propertyName, propertyType, propertyUnit, material, limitYear, memo, editID, editDate){

}

function init() {

}

 
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_RO","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName(),"")%>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="40%">權屬預設：</td>
		<td class="td_form_white" colspan="3">			
			<select class="field_Q" type="select" name="ownership">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='OWA'", obj.getOwnership())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="td_form" width="40%">財產性質預設：</td>
		<td class="td_form_white" colspan="3">			
			<select class="field_Q" type="select" name="propertyKind">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD'", obj.getPropertyKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="40%">財產單據聯數預設：</td>
		<td class="td_form_white" colspan="3">			
			<select class="field_Q" type="select" name="proofKindPT">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PFK'", obj.getProofKindPT())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="td_form">物品單據聯數預設：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="proofKindNE">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PFK'", obj.getProofKindNE())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">基金財產預設折舊方法：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="fundDeprMethod">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP'", obj.getFundDeprMethod())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">代管資產預設折舊方法：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="escrowDeprMethod">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP'", obj.getEscrowDeprMethod())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">公務財產預設折舊方法：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="officialDeprMethod">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP'", obj.getOfficialDeprMethod())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">遞延借項預設折舊方法：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="deferredDeprMethod">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP'", obj.getDeferredDeprMethod())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">地方財產預設折舊方法：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="localDeprMethod">
			　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP'", obj.getLocalDeprMethod())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">單據是否列印備註資料：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="proofNotes">
			　　<%=util.View.getYNOption(obj.getProofNotes())%>
			</select>
		</td>
	</tr>
	<% if ("Y".equalsIgnoreCase(user.getIsAdminManager())) { %>
	<tr>
		<td class="td_form">已達年限財產折舊方法：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="reachedYearCalMethod">
			　　<%=util.View.getTextOption("1;不補提;2;自動提列", obj.getReachedYearCalMethod())%>
			</select>
		</td>
	</tr>
	<% } %>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
		</td>
	</tr>
	</table>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;display:none;">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->

</table>
</form>
</body>
</html>
