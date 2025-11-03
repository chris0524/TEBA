<!--
程式目的：動產增減值作業-增減值單明細資料
程式代號：
程式日期：0970711
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU035F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.du.UNTDU035F)obj.queryOne();	
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
function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	
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
	<tr><td ID=t1 CLASS="tab_border1" HEIGHT="25">動產增減值作業-增減值單明細資料</td></tr>
</TABLE>
<!--頁籤區=============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" ><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_Q" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			權屬：
			<select class="field_P" type="select" name="ownershipshow">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			<input class="field" type="hidden" name="ownership" size="1" maxlength="1" value="<%=obj.getOwnership()%>">
			電腦單號：[<input class="field_RO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
			<br>
			財產編號：
			[<input class="field_RO" type="text" name="propertyNo" size="11" maxlength="11" value="<%=obj.getPropertyNo()%>">]
			財產分號：[<input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">增減值日期：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="adjustDate" size="7" maxlength="7" value="<%=obj.getAdjustDate()%>">&nbsp;
			入帳：
			<select class="field" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>&nbsp;&nbsp;&nbsp;　　　　
			月結：
			<select class="field" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>別名：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			原財產編號：
			<input class="field" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">
			原分號：
			<input class="field" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">其他主要材質：</td>
		<td class="td_form_white" colspan="3"> 
			<input class="field" type="text" name="otherMaterial" size="25" maxlength="50" value="<%=obj.getOtherMaterial()%>">
		<br>
			其他單位：
			<input class="field" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>"> 
		<br>
			調整後年限(月)：
			<input class="field" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			基金財產：
			<select class="field" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
			珍貴財產：
			<select class="field" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">帳面資料：</td>
		<td class="td_form_white" colspan="3">		
			取得日期：
			<input class="field" type="text" name="sourceDate" size="10" maxlength="7" value="<%=obj.getSourceDate()%>">&nbsp;　
			帳面數量：
			<input class="field" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">
		<br>
			<font color="red">*</font>增減別：
			<select class="field" type="select" name="adjustType">
				<option value="">請選擇</option>
				<option value="1" >增加</option>
				<option value="2" >減少</option>
				<script>getSelectedValue(form1.adjustType,"<%=obj.getAdjustType()%>");</script>
			</select>			
			&nbsp;<font color="red">*</font>增減值原因：
			<input class="field" type="text" name="cause" size="6" maxlength="12" value="<%=obj.getCause()%>">
			　帳務摘要：
			<input class="field" type="text" name="bookNotes" size="18" maxlength="20" value="<%=obj.getBookNotes()%>">
		<br>
			原價值：
			<input class="field" type="text" name="oldBookValue" size="12" maxlength="15" value="<%=obj.getOldBookValue()%>">
			<font color="red">*</font>增減價值：
			<input class="field" type="text" name="adjustBookValue" size="12" maxlength="15" value="<%=obj.getAdjustBookValue()%>">
			新價值：
			<input class="field" type="text" name="newBookValue" size="12" maxlength="15" value="<%=obj.getNewBookValue()%>">
		<br>
			原單價：
			<input class="field" type="text" name="oldBookUnit" size="13" maxlength="13" value="<%=obj.getOldbookUnit()%>">
			增減單價：
			<input class="field" type="text" name="adjustBookUnit" size="15" maxlength="15" value="<%=obj.getAdjustBookUnit()%>">
			新單價：
			<input class="field" type="text" name="newBookUnit" size="13" maxlength="13" value="<%=obj.getNewBookUnit()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">存置地點：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="place" size="25" value="<%=obj.getPlace()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">電腦單號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,true};//傳值
	boolean displayArray[] = {false,true,false,true,true,true,true};//show list
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



