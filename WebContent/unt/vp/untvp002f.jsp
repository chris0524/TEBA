<!--
程式目的：有價證券主檔資料維護-股份資料
程式代號：untvp002f
程式日期：0940927
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.vp.UNTVP002F)obj.queryOne();	
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


unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._VP_ADD, 4);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._VP_ADD, 3);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._VP_ADD, 3);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._VP_ADD, 3);
}else if("untch001f02extend01".equals(obj.getProgID())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._VP_ADD, 3);
}else if("untch001f02extend02".equals(obj.getProgID())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._VP_ADD, 3);
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("dataState","1")
);

function checkField(){
	//if( form1.originalUA.value == '' && form1.originalSheet.value == '') {
	//	form1.originalUA.value = '0';
	//	form1.originalSheet.value = '0';
	//}
	//var checkOriginalUA = parseInt(form1.originalUA.value);
	//var checkOriginalSheet = parseInt(form1.originalSheet.value);
	//var checkOriginalUV = parseInt(form1.originalUV.value);
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.reduceDate,"減損日期");
		//alertStr += checkEmpty(form1.originalUA,"原始每張股數");
		//alertStr += checkFloat(form1.originalUA,"原始每張股數",7,0);
		//alertStr += checkEmpty(form1.originalSheet,"原始張數");
		//alertStr += checkFloat(form1.originalSheet,"原始張數",5,0);
		alertStr += checkEmpty(form1.originalAmount,"原始總股數");
		alertStr += checkFloat(form1.originalAmount,"原始總股數",12,0);
		//alertStr += checkEmpty(form1.originalUV,"原始每股單價");
		alertStr += checkFloat(form1.originalUV,"原始每股單價",5,0);
		alertStr += checkFloat(form1.originalBV,"原始總價",15,0);
		//alertStr += checkEmpty(form1.originalProofS,"原始證明書編號起");
		//alertStr += checkEmpty(form1.originalProofE,"原始證明書編號訖");
		//alertStr += checkFloat(form1.bookUnitAmount,"每張股數",7,0);
		//alertStr += checkFloat(form1.bookSheet,"張數",5,0);
		//alertStr += checkFloat(form1.bookAmount,"總股數",12,0);
		alertStr += checkFloat(form1.bookUnitValue,"每股單價",5,0);
		//alertStr += checkFloat(form1.bookValue,"總價",15,0);
		alertStr += checkLen(form1.notes, "備註", 250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,differenceKind,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.differenceKind.value=differenceKind;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	document.all.item("spanQueryAll").style.display="none";
	if (form1.state.value=="queryOne" && form1.enterOrg.value!=<%="\""+user.getOrganID()+"\""%>) {		
		setFormItem("insert,update,delete,clear,confirm", "R");
	}
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
}
function amountBV(){

	form1.bookValue.value = form1.bookAmount.value * form1.bookUnitValue.value;
	
}
function checkURL(surl){
	if (surl=="../ch/untch001f02.jsp"){
		if('<%=obj.getIsAddProof()%>' != 'true'){
			form1.state.value="queryAll";
		}
	} else {
		form1.state.value="queryAll";
	}
	if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
		form1.mainPage1.value="";
		form1.currentPage.value=form1.mainPage.value;
	}
	form1.action = surl;
	form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
	form1.queryone_ownership.value=form1.mainOwnerShip.value;
	form1.queryone_caseNo.value=form1.mainCaseNo.value;
	form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
	beforeSubmit();
	form1.submit();
}
function proofSE(){
	form1.proofS.value = form1.originalProofS.value;
	form1.proofE.value = form1.originalProofE.value;
}
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--Query區============================================================-->
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer2" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame2"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<!--入帳機關：-->
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
		<!--權屬：-->
			<input class="field_QRO" type="hidden" name="ownership" size="1" maxlength="1" value="<%=obj.getOwnership()%>">
			
			<input class="field_QRO" type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">			
		<!--財產編號：-->
			<input class="field_QRO" type="hidden" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>">
		<!--財產分號：-->
			<input class="field_QRO" type="hidden" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">
	<tr>
		<td class="td_form" width="15%">股份次序：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]			
		</td>
	</tr>
	<tr>
		<td class="td_form">股數：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>總股數：
				<input class="field" type="text" name="bookAmount" size="10" maxlength="12" value="<%=obj.getBookAmount()%>" onFocusOut="amountBV();">			
		<br>
			<font color="red">*</font>每股單價：
				<input class="field" type="text" name="bookUnitValue" size="5" maxlength="5" value="<%=obj.getBookUnitValue()%>" onFocusOut="amountBV();">
			&nbsp;&nbsp;&nbsp;　　　　總價：
				[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		<br>
			證明書編號：
				起<input class="field" type="text" name="proofS" size="10" maxlength="15" value="<%=obj.getProofS()%>" onFocusOut="proofSE();">&nbsp;~&nbsp;
				訖<input class="field" type="text" name="proofE" size="10" maxlength="15" value="<%=obj.getProofE()%>" onFocusOut="proofSE();">				
		</td>
	</tr>
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form"style="display:none;">異動人員/日期：</td>
		<td class="td_form_white"style="display:none;">
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" name="verify" value="<%=obj.getVerify()%>">
	<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">			
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
	<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
	<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
	
	<jsp:include page="../../home/toolbar.jsp" />
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">股份次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">總股數</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">每股單價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">證明書編號起訖</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,false,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr></table></form>
</body>
</html>



