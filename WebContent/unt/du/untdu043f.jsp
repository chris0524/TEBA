<!--
程式目的：非消耗品資料維護-基本資料
程式代號：untdu012f
程式日期：0970711
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU043F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.du.UNTDU043F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
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
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.enterOrgName,"入帳機關名稱");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo,propertyNo,lotNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.lotNo.value=lotNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	setDisplayItem('spanInsert,spanDelete,spanNextInsert,spanQueryAll','H');
}

function checkURL(surl){
	form1.state.value = "queryAll";
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr><td>
		<input type="hidden" name="q_chengClass" size="10" maxlength="10" value="<%=obj.getQ_chengClass()%>">
		<input type="hidden" name="q_enterOrg" value="<%=obj.getQ_enterOrg()%>">
		<input type="hidden" name="q_enterOrgName" value="<%=obj.getQ_enterOrgName()%>">
		<input type="hidden" name="q_ownership" value="<%=obj.getQ_ownership()%>">
		<input type="hidden" name="q_caseNo" value="<%=obj.getQ_caseNo()%>">
		<input type="hidden" name="q_lotNo" value="<%=obj.getQ_lotNo()%>">
		<input type="hidden" name="q_propertyNo" value="<%=obj.getQ_propertyNo()%>">
		<input type="hidden" name="q_serialNo" value="<%=obj.getQ_serialNo()%>">
	</td></tr>
	</table>
</div>

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr><td ID=t1 CLASS="tab_border1" HEIGHT="25">非消耗品資料維護-基本資料</td></tr>
</TABLE>
<!--頁籤區=============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="10%"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" width="32%" colspan="3">
			<input class="field_Q" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			權屬：
			<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			電腦單號：
			[<input class="field_RO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
			<br>
			財產編號：
			[<input class="field_RO" type="text" name="propertyNo" size="11" maxlength="11" value="<%=obj.getPropertyNo()%>">]
			批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field","enterDate",obj.getEnterDate())%>
			資料狀態：
			<select class="field" type="select" name="dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
			</select>
			入帳：
			<select class="field" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			月結：
			<select class="field" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>物品性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind">
				<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
			</select>
			基金物品：
			<select class="field" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
			<input class="field" type="hidden" name="valuable" value="N" >
		</td>
	</tr>
	<tr>
		<td class="td_form">別名：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">其他主要材質：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="otherMaterial" size="25" maxlength="25" value="<%=obj.getOtherMaterial()%>">
		<br>
			其他單位：
			<input class="field" type="text" name="otherPropertyUnit" size="25" maxlength="25" value="<%=obj.getOtherPropertyUnit()%>">
		<br>
			調整後年限(月)：
			<input class="field" type="text" name="otherLimitYear" size="8" maxlength="3" value="<%=obj.getOtherLimitYear()%>" onChange="changeDate();">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>購置日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field","buyDate",obj.getBuyDate())%>
			可報廢日期：
			<%=util.View.getPopCalndar("field","permitReduceDate",obj.getPermitReduceDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">廠牌型式：</td>
		<td class="td_form_white" colspan="3">
			品名：
			<input name="articleName" type="text" class="field" value="<%=obj.getArticleName()%>" size="20" maxlength="10">&nbsp;&nbsp;　&nbsp;　
			型式：
			<input name="specification" type="text" class="field" value="<%=obj.getSpecification()%>" size="40" maxlength="40">　
		<br>
			廠牌：
			<input name="nameplate" type="text" class="field" value="<%=obj.getNameplate()%>" size="40" maxlength="40">&nbsp;&nbsp;　&nbsp;
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>增加原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAB'", obj.getCause())%>
			</select>
			其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>" readonly="true">
		<br>
			受贈同意函日期：
			<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
			受贈同意函文號：
			<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">經費來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="fundsSource">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
			</select>
			其他說明：
			<input class="field" type="text" name="fundsSource1" size="20" maxlength="20" value="<%=obj.getFundsSource1()%>" readonly="true">
		<br>
			補助金額：
			<input class="field" type="text" name="grantValue" size="15" maxlength="15" value="<%=obj.getGrantValue()%>" >
			會計科目：
			<input class="field" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">物品來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="sourceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKC' ", obj.getSourceKind())%>
			</select>&nbsp;&nbsp;&nbsp;
			物品取得日期：
			<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
		<br>
			物品取得文號：
			<input class="field" type="text" name="sourceDoc" size="15" maxlength="20" value="<%=obj.getSourceDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>原始入帳數量：
			<input class="field" type="text" name="originalAmount" size="15" maxlength="7" value="<%=obj.getOriginalAmount()%>">
		<br>
			原始入帳單價：
			<input class="field" type="text" name="originalUnit" size="15" maxlength="13" value="<%=obj.getOriginalUnit()%>">
			<font color="red">*</font>原始入帳總價：
			<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" >
		<br>
			原始入帳摘要：
			<input class="field" type="text" name="originalNote" size="30" maxlength="15" value="<%=obj.getOriginalNote()%>">
		<br>
			帳面數量：
			<input class="field" type="text" name="bookAmount" size="15" maxlength="7" value="<%=obj.getBookAmount()%>">
			帳面金額總價：
			<input class="field" type="text" name="bookValue" size="15" maxlength="13" value="<%=obj.getBookValue()%>">
		</td>
	</tr>
	<tr>
	<td class="td_form">備註：</td>
		<td class="td_form_white"><textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
	<td class="td_form">異動人員/日期：</td>
	<td class="td_form_white">
		[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
	</td>
</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">

	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="backup">
	<input class="toolbar_default" type="button" name="backup" value="回上一層" onClick="checkURL('untdu012f.jsp');">&nbsp;|
	</span>
</td></tr>

<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">入帳機關名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">電腦單號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">財產分號起</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">財產分號訖</a></th>
		
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,true,false,false};
	boolean displayArray[] = {false,true,false,true,true,true,false,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



