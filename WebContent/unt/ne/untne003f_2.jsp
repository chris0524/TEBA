<!--
程式目的：物品主檔資料維護－批號明細
程式代號：untne003f
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE003F_2">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setQ_enterOrg(user.getOrganID());
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE003F_2)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("".equals(obj.getSerialNo())&& "".equals(obj.getLotNo())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setOwnership(((String[])objList.get(0))[3]);
			obj.setCaseNo(((String[])objList.get(0))[16]);
			obj.setDifferenceKind(((String[])objList.get(0))[15]);
			obj.setPropertyNo(((String[])objList.get(0))[5]);
			obj.setSerialNo(((String[])objList.get(0))[6]);
			obj.setLotNo(((String[])objList.get(0))[14]);			
			
		}
		obj.queryOne();
	}
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";

	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,lotNo,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.differenceKind.value=differenceKind;
	form1.lotNo.value=lotNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function changeSelect(){
	//當減損原因選「其他」時，開放其他說明欄位
	if(form1.reduceCause.value == "99") form1.reduceCause1.readOnly = false;
	else { 
		form1.reduceCause1.value=""; 
		form1.reduceCause1.readOnly = true; 
	}
}


//可報廢不用
function changeKeep(){
	getKeepUnit(form1.tempEnterOrg, form1.originalUseUnit, form1.originalKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, form1.originalKeeper.value,'N');
	getKeepUnit(form1.tempEnterOrg, form1.keepUnit, form1.originalKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.keepUnit, form1.keeper, form1.originalKeeper.value);
	getKeepUnit(form1.tempEnterOrg, form1.useUnit, form1.originalUseUnit.value);
	getKeeper(form1.tempEnterOrg, form1.useUnit, form1.userNo, form1.originalUser.value);
}
//可報廢不用
function changeUse(){
	getKeepUnit(form1.tempEnterOrg, form1.useUnit, form1.originalUseUnit.value);
	getKeeper(form1.tempEnterOrg, form1.useUnit, form1.userNo, form1.originalUser.value);
}

function checkURL(surl){
	var alertStr = "";
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untne001f.jsp" || surl=="untne002f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[2].checked) {		
				alertStr += checkEmpty(form1.propertyNo,"物品編號");
				alertStr += checkEmpty(form1.serialNo,"物品分號");			
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"物品編號");
			alertStr += checkEmpty(form1.serialNo,"物品分號");			
		}
		if (document.all.querySelect[2].checked || document.all.querySelect[0].checked) {
			surl=surl+"?initDtl=Y";
		}	
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		alertStr += checkEmpty(form1.ownership,"權屬");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		form1.action = surl;
		form1.mainPage.value=form1.currentPage.value;
		form1.mainEnterOrg.value=form1.queryone_enterOrg.value;
		form1.mainOwnerShip.value=form1.queryone_ownership.value;
		form1.mainCaseNo.value=form1.queryone_caseNo.value;
		form1.mainDifferenceKind.value=form1.queryone_differenceKind.value;	
		form1.currentPage.value=1;		
		beforeSubmit();
		form1.submit();
	}

}

function init(){
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
	
	if (<%=obj.getDataState().equals("2")%>){
		setFormItem("update", "R")
	}

	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,clear,confirm,batchSetButAll", "R")
	}
	if (<%=obj.getVerify().equals("Y")%>){
		setFormItem("originalKeepUnit,originalKeeper,originalUseUnit,originalUser,keepUnit,keeper,useUnit,userNo", "R")
		}
	else if (<%=obj.getVerify().equals("N")%>){
		setFormItem("originalKeepUnit,originalKeeper,originalUseUnit,originalUser", "R")
		}
	setDisplayItem("spanInsert,spanNextInsert","H");

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.differenceKind.value !== '' 
			&& form1.propertyNo.value !== '' && form1.serialNo.value !== '' && form1.lotNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.propertyNo.value + form1.serialNo.value + form1.lotNo.value + form1.differenceKind.value;
		listContainerRowClick(key);
	}
}

function changeMoveDate(){
	form1.moveDate.value = form1.originalMoveDate.value;
}

function changePlace(){
	form1.place.value = form1.originalPlace.value;
}

function clickBatchSetButAll(){
	var rowValue = "";
	var prop="";
	var windowTop=(document.body.clientHeight-80)/2+180;
	var windowLeft=(document.body.clientWidth-600)/2+180;
	prop=prop+"width=720,height=150,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	document.all.update.disabled=true;
	if (document.all.querySelect[1].checked || document.all.querySelect[0].checked || <%="".equals(obj.getQuerySelect())%>) {
		rowValue = "?initDtl=Y";
		rowValue += "&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&propertyNo="+form1.propertyNo.value+"&differenceKind="+form1.differenceKind.value;
	}else{
		rowValue = "?1=1";
		rowValue +="&querySelect=<%=obj.getQuerySelect()%>";
		var q_enterOrg = parse(form1.q_enterOrg.value);
		if(q_enterOrg.length>0){
			rowValue +="&q_enterOrg="+form1.q_enterOrg.value;
		}else{
			rowValue +="&q_enterOrg=";
		}
		var q_ownership = parse(form1.q_ownership.value);
		if(q_ownership.length>0){
			rowValue +="&q_ownership="+form1.q_ownership.value;
		}else{
			rowValue +="&q_ownership=";
		}
		var q_caseNoS = parse(form1.q_caseNoS.value);
		if(q_caseNoS.length>0){
			rowValue +="&q_caseNoS="+form1.q_caseNoS.value;
		}else{
			rowValue +="&q_caseNoS=";
		}
		var q_caseNoE = parse(form1.q_caseNoE.value);
		if(q_caseNoE.length>0){
			rowValue +="&q_caseNoE="+form1.q_caseNoE.value;
		}else{
			rowValue +="&q_caseNoE=";
		}
		var q_verify = parse(form1.q_verify.value);
		if(q_verify.length>0){
			rowValue +="&q_verify="+form1.q_verify.value;
		}else{
			rowValue +="&q_verify=";
		}
		var q_caseName = parse(form1.q_caseName.value);
		if(q_caseName.length>0){
			rowValue +="&q_caseName="+form1.q_caseName.value;
		}else{
			rowValue +="&q_caseName=";
		}
		var q_dataState = parse(form1.q_dataState.value);
		if(q_dataState.length>0){
			rowValue +="&q_dataState="+form1.q_dataState.value;
		}else{
			rowValue +="&q_dataState=";
		}
		var q_propertyKind = parse(form1.q_propertyKind.value);
		if(q_propertyKind.length>0){
			rowValue +="&q_propertyKind="+form1.q_propertyKind.value;
		}else{
			rowValue +="&q_propertyKind=";
		}
		var q_cause = parse(form1.q_cause.value);
		if(q_cause.length>0){
			rowValue +="&q_cause="+form1.q_cause.value;
		}else{
			rowValue +="&q_cause=";
		}
		var q_fundType = parse(form1.q_fundType.value);
		if(q_fundType.length>0){
			rowValue +="&q_fundType="+form1.q_fundType.value;
		}else{
			rowValue +="&q_fundType=";
		}
		var q_valuable = parse(form1.q_valuable.value);
		if(q_valuable.length>0){
			rowValue +="&q_valuable="+form1.q_valuable.value;
		}else{
			rowValue +="&q_valuable=";
		}
		var q_enterDateS = parse(form1.q_enterDateS.value);
		if(q_enterDateS.length>0){
			rowValue +="&q_enterDateS="+form1.q_enterDateS.value;
		}else{
			rowValue +="&q_enterDateS=";
		}
		var q_enterDateE = parse(form1.q_enterDateE.value);
		if(q_enterDateE.length>0){
			rowValue +="&q_enterDateE="+form1.q_enterDateE.value;
		}else{
			rowValue +="&q_enterDateE=";
		}
		var q_writeDateS = parse(form1.q_writeDateS.value);
		if(q_writeDateS.length>0){
			rowValue +="&q_writeDateS="+form1.q_writeDateS.value;
		}else{
			rowValue +="&q_writeDateS=";
		}
		var q_writeDateE = parse(form1.q_writeDateE.value);
		if(q_writeDateE.length>0){
			rowValue +="&q_writeDateE="+form1.q_writeDateE.value;
		}else{
			rowValue +="&q_writeDateE=";
		}
		var q_proofDoc = parse(form1.q_proofDoc.value);
		if(q_proofDoc.length>0){
			rowValue +="&q_proofDoc="+form1.q_proofDoc.value;
		}else{
			rowValue +="&q_proofDoc=";
		}
		var q_proofNoS = parse(form1.q_proofNoS.value);
		if(q_proofNoS.length>0){
			rowValue +="&q_proofNoS="+form1.q_proofNoS.value;
		}else{
			rowValue +="&q_proofNoS=";
		}
		var q_proofNoE = parse(form1.q_proofNoE.value);
		if(q_proofNoE.length>0){
			rowValue +="&q_proofNoE="+form1.q_proofNoE.value;
		}else{
			rowValue +="&q_proofNoE=";
		}
		var q_propertyNoS = parse(form1.q_propertyNoS.value);
		if(q_propertyNoS.length>0){
			rowValue +="&q_propertyNoS="+form1.q_propertyNoS.value;
		}else{
			rowValue +="&q_propertyNoS=";
		}
		var q_propertyNoE = parse(form1.q_propertyNoE.value);
		if(q_propertyNoE.length>0){
			rowValue +="&q_propertyNoE="+form1.q_propertyNoE.value;
		}else{
			rowValue +="&q_propertyNoE=";
		}
		var q_serialNoS = parse(form1.q_serialNoS.value);
		if(q_serialNoS.length>0){
			rowValue +="&q_serialNoS="+form1.q_serialNoS.value;
		}else{
			rowValue +="&q_serialNoS=";
		}
		var q_serialNoE = parse(form1.q_serialNoE.value);
		if(q_serialNoE.length>0){
			rowValue +="&q_serialNoE="+form1.q_serialNoE.value;
		}else{
			rowValue +="&q_serialNoE=";
		}
		var q_lotNo = parse(form1.q_lotNo.value);
		if(q_lotNo.length>0){
			rowValue +="&q_lotNo="+form1.q_lotNo.value;
		}else{
			rowValue +="&q_lotNo=";
		}
		var q_keepUnit = parse(form1.q_keepUnit.value);
		if(q_keepUnit.length>0){
			rowValue +="&q_keepUnit="+form1.q_keepUnit.value;
		}else{
			rowValue +="&q_keepUnit=";
		}
		var q_keeper = parse(form1.q_keeper.value);
		if(q_keeper.length>0){
			rowValue +="&q_keeper="+form1.q_keeper.value;
		}else{
			rowValue +="&q_keeper=";
		}
		var q_useUnit = parse(form1.q_useUnit.value);
		if(q_useUnit.length>0){
			rowValue +="&q_useUnit="+form1.q_useUnit.value;
		}else{
			rowValue +="&q_useUnit=";
		}
		var q_userNo = parse(form1.q_userNo.value);
		if(q_userNo.length>0){
			rowValue +="&q_userNo="+form1.q_userNo.value;
		}else{
			rowValue +="&q_userNo=";
		}
	}
	returnWindow=window.open("batchSetButAll.jsp"+rowValue,"",prop);
}
function changeDamageDate(){

}

function popSysca_Code(popID,popIDName,typeName,codekindid,condition){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	
	// popSysca_Code.jsp有解碼
	typeName = encodeURI(typeName);
	returnWindow=window.open("../../home/popSysca_Code.jsp?popID="+popID+"&popIDName="+popIDName+"&typeName="+typeName+"&codekindid="+codekindid+"&condition="+condition,"",prop);
}

function openBatchUpdateWindow() {
	var alertStr = "";	
	var prop="";
	var windowTop=(document.body.clientHeight-1000)/2+25;
	var windowLeft=(document.body.clientWidth-1300)/2+150;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width=1300,height=700,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
//	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
//	alertStr += checkEmpty(form1.ownership,"權屬");
//	alertStr += checkEmpty(form1.propertyNo,"財產編號");
//	alertStr += checkEmpty(form1.lotNo,"財產批號");
	
	popWin = window.open('untne003f_batch.jsp?enterOrg='+form1.enterOrg.value+
		'&ownership='+form1.ownership.value+'&propertyNo='+form1.propertyNo.value+
		"&differenceKind="+form1.differenceKind.value+"&lotNo="+form1.lotNo.value,'MyWindow',prop);
}
function queryPlaceName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../ch/queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			
		});	
}

function clearStoreNo(){
	form1.storeNo.value="";
	form1.storeNoName.value="";
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>		
		<td ID=t3 CLASS="tab_border1">*物品明細</td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne004f_2.jsp');">附屬設備明細</a></td>
		<td ID=t6 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne057f_2.jsp');">帳務資料</a></td>
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne058f_2.jsp');">移動紀錄</a></td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>				
	</tr>
</TABLE>
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input type="hidden" name="buyDate" value="<%=obj.getBuyDate()%>">
<input type="hidden" name="serialNoS" size="10" maxlength="10" value="<%=obj.getSerialNoS()%>">
<input type="hidden" name="serialNoE" size="10" maxlength="10" value="<%=obj.getSerialNoE()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE001Q",obj);%>
	<jsp:include page="untne003q_1.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr style="display:none">
		<td class="td_form" width="18%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
	 </tr>
		<tr>
		<td class="td_form" width="18%">權屬：</td>
		<td class="td_form_white" colspan="3">		
			<select class="field_QRO" type="select" name="ownershipName">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
			  <!--  電腦單號：-->
			<input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">物品區分別：</td>
		<td colspan="3" class="td_form_white">
		 <%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
       	</td>
	</tr>
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterDate" size="10" maxlength="7" value="<%=obj.getEnterDate()%>">]
			　資料狀態：
			<select class="field_RO" type="select" name="dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
			</select>		
			　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>物品性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_QRO" type="select" name="propertyKind" onChange="changeSelect();">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getPropertyKind())%>
			</select>
			　　　基金物品：
			<%=util.View._getSelectHTML_withEnterOrg("field_QRO", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
			<input class="field" type="hidden" name="valuable" value="N" >
		</td>
	</tr>
	<tr>
		<td class="td_form">物品編號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','','NE')">]
			[<input class="field_QRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			&nbsp;分號：
			[<input class="field_QRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
			&nbsp;批號：
			[<input class="field_QRO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
		<br>
			原物品編號：
			[<input class="field_RO" type="text" name="oldPropertyNo" size="12" maxlength="12" value="<%=obj.getOldPropertyNo()%>">]
			　　原分號：
			[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		<br>
			別名：
			<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="material" size="25" value="<%=obj.getMaterial()%>">]
			&nbsp;&nbsp;　其他主要材質：[<input class="field_RO" type="text" name="otherMaterial" size="25" maxlength="25" value="<%=obj.getOtherMaterial()%>">]
		<br>
			單位：[<input class="field_RO" type="text" name="propertyUnit" size="25" maxlength="25" value="<%=obj.getPropertyUnit()%>">]
			&nbsp;&nbsp;其他單位：[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="25" value="<%=obj.getOtherPropertyUnit()%>">]
		<br>	
			原使用年限：[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="3" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　調整後年限(月)：[<input class="field_RO" type="text" name="otherLimitYear" size="8" maxlength="3" value="<%=obj.getOtherLimitYear()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>購置日期：</td>
		<td class="td_form_white" colspan="3">		
			[<input class="field_QRO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>" onChange="form1.sourceDate.value = this.value;">]
						
		</td>
	</tr>
	<tr>
		<td class="td_form">廠牌型式：</td>
		<td class="td_form_white" colspan="3">		
			品名：
				<input name="articleName" type="text" class="field" value="<%=obj.getArticleName()%>" size="20" maxlength="10">
			&nbsp;&nbsp;　&nbsp;　型式：
				<input name="specification" type="text" class="field" value="<%=obj.getSpecification()%>" size="40" maxlength="40">
		<br>
			廠牌：
				<input name="nameplate" type="text" class="field" value="<%=obj.getNameplate()%>" size="40" maxlength="40">　
			&nbsp;&nbsp;　&nbsp;廠商：
				<input class="field_QRO" type="hidden" name="storeNo" size="5" maxlength="10" value="<%=obj.getStoreNo()%>">
				<input class="field_RO" type="text" name="storeNoName" size="20" maxlength="50" value="<%=obj.getStoreNoName()%>">
				<input class="field" type="button" name="btn_storeNo" onclick="popStore('storeNo','storeNoName')" value="..." title="廠商輔助視窗">
				<input class="field" type="button" name="clear_storeNo" onclick="clearStoreNo();" value="清除">
		</td>
	</tr>
	<tr style="display:none">
		<td class="td_form">增加原因：</td>
		<td class="td_form_white" colspan="3">
		<%=util.View.getCause("field_QRO","cause",obj.getCause(),obj.getCauseName(),"1,4","")%>
		　　其他說明：
			<input class="field_QRO" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>" readonly="true">
		</tr>
		<tr>
		<td class="td_form">受贈同意函日期：</td>
		<td class="td_form_white" colspan="3">
			[<%=util.View.getPopCalndar("field_QRO","approveDate",obj.getApproveDate())%>]
		　受贈同意函文號：
			[<input class="field_QRO" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">經費來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_QRO" type="select" name="fundsSource" onChange="changeFundsSource();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
			</select>
			<br>
		　其他說明：
			[<input class="field_QRO" type="text" name="fundsSource1" size="20" maxlength="20" value="<%=obj.getFundsSource1()%>" readonly="true">]
		    補助金額：
			[<input name="grantValue" type="text" class="field_QRO" value="<%=obj.getGrantValue()%>" size="15" maxlength="15">]
		&nbsp;會計科目：
			[<input class="field_QRO" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>物品來源：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getSourceKind("field_QRO","sourceKind",obj.getSourceKind(),obj.getSourceKindName())%>
			<font color="red">*</font>&nbsp;&nbsp;&nbsp;物品取得日期：
			[<%=util.View.getPopCalndar("field_QRO","sourceDate",obj.getSourceDate())%>]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　物品取得文號：
            [<input class="field_QRO" type="text" name="sourceDoc" size="15" maxlength="20" value="<%=obj.getSourceDoc()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">牌照號碼規格/序號規格：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="text" name="licensePlate" size="30" maxlength="30" value="<%=obj.getLicensePlate()%>">
			&nbsp;&nbsp;　　　　　用途：<input class="field_QRO" type="text" name="purpose" size="30" maxlength="50" value="<%=obj.getPurpose()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">帳面資料：</td>
		<td class="td_form_white" colspan="3">
			原始數量：[<input class="field_RO" type="text" name="originalAmount" size="7" maxlength="7" value="<%=obj.getOriginalAmount()%>">]
			&nbsp;　　　　　　 原始價值：
			[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">]
		<br>		
			數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">]
			&nbsp;　　　　　　　　 價值：
			[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">盤點資料：</td>
		<td class="td_form_white" colspan="3">
			條碼：
			[<input class="field_RO" type="text" name="barCode" size="24" maxlength="24" value="<%=obj.getBarCode()%>">]		
		</td>
	</tr>	
	<tr>
		<td class="td_form">目前保管使用資料：</td>
	    <td class="td_form_white" colspan="3">
			保管單位
				<select class="field_QRO" type="select" name="keepUnit" onchange="form1.useUnit.value = this.value">
				　　<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getKeepUnit())%>
				</select>
				<input class="field_QRO" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'keepUnit','useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<select class="field_QRO" type="select" name="keeper" onchange="form1.userNo.value = this.value">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getKeeper())%>
	            </select>
		        <input class="field_QRO" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'keeper','userNo')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<select class="field_QRO" type="select" name="useUnit" onchange="form1.keepUnit.value = this.value">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getUseUnit())%>
			    </select>	
				<input class="field_QRO" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'useUnit','keepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<select class="field_QRO" type="select" name="userNo" onchange="form1.keeper.value = this.value">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getUserNo())%>
	            </select>
		        <input class="field_QRO" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'userNo','keeper')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
			          使用註記：
			    <input type="text" class="field" name="userNote" value="<%=obj.getUserNote() %>" size="20">
		        <br>
				存置地點
				<input class="field" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>" onchange="queryPlaceName('enterOrg','place1');">
				<input class="field" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'place1','place1Name')" value="..." title="存置地點輔助視窗">
			    [<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name()%>">]
				<br>		
				存置地點說明
				<input class="field" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">		
		</td>
	</tr>
	<tr>
		<td class="td_form">其他事項：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="notes1" size="60" maxlength="60" value="<%=obj.getNotes1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">減損日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			　減損原因：
			<select class="field_RO" type="select" name="reduceCause" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC'", obj.getReduceCause())%>
			</select>
			　其它說明：[<input class="field_RO" type="text" name="reduceCause1" size="10" maxlength="12" value="<%=obj.getReduceCause1()%>" readonly="true">]
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
	  	<textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
	  <td class="td_form" style="display:none">異動人員/日期：</td>
	  <td class="td_form_white" style="display:none">
	  	[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
	  </td>
	</tr>	
	</table>
	</div>
</td></tr>
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="filestoreLocation" value="<%=application.getInitParameter("filestoreLocation")%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">	
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">保管單位</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">價值</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,true,false,true,true,false,false,false,false,false,false,false,true,true,false};
	boolean displayArray[] = {false,true,false,false,false,true,true,true,true,true,true,true,true,false,false,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[2].checked==true) {} 
			else form1.querySelect[1].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			
//				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
//				changeBureau(form1.q_enterOrg, form1.q_keepBureau, '');
//				changeBureau(form1.q_enterOrg, form1.q_useBureau, '');
				changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'');
				changeKeepUnit(form1.q_enterOrg, form1.q_useBureau, form1.q_useUnit,'');
			}
			break;				
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");

	switch (buttonName)	{
		//更新時要做的動作
		case "update":
			//當減損原因不是「其他」時，鎖住其他說明欄位
			if(form1.reduceCause.value !="99"){
				form1.reduceCause1.readOnly = true;
			}	
			if(form1.verify.value=="Y"){
				setFormItem("originalKeepBureau,originalKeepUnit,originalKeeper,originalUseBureau,originalUseUnit,originalUser,originalPlace", "R")			
			}			
			break;
		case "updateError":
			//當減損原因不是「其他」時，鎖住其他說明欄位
			if(form1.reduceCause.value !="99"){
				form1.reduceCause1.readOnly = true;
			}	
			if(form1.verify.value=="Y" ){
				setFormItem("originalKeepUnit,originalKeeper,originalUseUnit,originalUser,originalPlace", "R")
			}			
			break;
	}
}
</script>
</body>
</html>



