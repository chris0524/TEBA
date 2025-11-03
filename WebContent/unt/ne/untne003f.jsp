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
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE003F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE003F)obj.queryOne();	
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
		if("".equals(obj.getSerialNo())){
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
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";

	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.differenceKind.value=differenceKind;
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
		if (surl=="untne001f.jsp"){
			form1.mainPage1.value="";
			form1.currentPage.value=form1.mainPage.value;
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
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.action = surl;
		
		beforeSubmit();
		form1.submit();
	}

}

function init(){
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
	// 若是查出的「批號明細」之「資料狀態」若為「已減損」，均不允許修改該批號明細 
	if (<%=obj.getDataState().equals("2")%>){
		setFormItem("update", "R")
	} else {
		//查出的「批號明細」之「資料狀態」若為「現存」，且「已入帳」或「已月結」，僅供財管人員修改
		if (<%=obj.getVerify().equals("Y")%>) {
			if (<%=!user.getGroupID().contains("sys")%>) {
				setFormItem("update", "R");
			}
		} 
	}
	
	if((<%=obj.getInitDtl().equals("Y") || "".equals(obj.getQuerySelect())%>) && form1.verify.value=="N" ){
		setFormItem("openUpdate", "O");
	}else{
		setFormItem("openUpdate", "O");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,clear,confirm,batchSetButAll", "R")
	}
	if (<%=obj.getVerify().equals("Y")%>){
		setFormItem("originalKeepUnit,originalKeeper,originalUseUnit,originalUser,keepUnit,keeper,useUnit,userNo,btn_q_originalKeepUnit,btn_q_originalKeeper,btn_q_originalUseUnit,btn_q_originalUser,btn_q_keepUnit,btn_q_keeper,btn_q_useUnit,btn_q_userNo", "R")
		document.all.item("spanopenUpdate").style.display="none";
		} else if (<%=obj.getVerify().equals("N")%>) {
		setFormItem("originalKeepUnit,originalKeeper,originalUseUnit,originalUser,keepUnit,keeper,useUnit,userNo,btn_q_originalKeepUnit,btn_q_originalKeeper,btn_q_originalUseUnit,btn_q_originalUser,btn_q_keepUnit,btn_q_keeper,btn_q_useUnit,btn_q_userNo", "R")
		}

	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeepUnitQuickly, form1.originalKeepUnit, form1.originalUseUnitQuickly, form1.originalUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeeperQuickly, form1.originalKeeper, form1.originalUserQuickly, form1.originalUser, true, false);
	var dcc3 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keepUnitQuickly, form1.keepUnit, form1.useUnitQuickly, form1.useUnit, true, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keeperQuickly, form1.keeper, form1.userNoQuickly, form1.userNo, true, false);

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== ''
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.propertyNo.value + form1.serialNo.value + form1.differenceKind.value;
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

function chOriginalPlace1(){
	if(form1.originalPlace1.value!=form1.place1.value){
		form1.place1.value = form1.originalPlace1.value;
		queryPlaceName('enterOrg','place1');
	}
}

function chOriginalPlace(){	
	form1.place.value = form1.originalPlace.value;
}


	
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untne001f.jsp');"><font color="red">*</font>增加單資料</a></td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne002f.jsp');"><font color="red">*</font>基本資料</a></td>		
		<td ID=t3 CLASS="tab_border1">*物品明細</td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne034f.jsp');">附屬設備</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne004f.jsp');">附屬設備明細</a></td>
		<td ID=t6 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne057f.jsp');">帳務資料</a></td>
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne058f.jsp');">移動紀錄</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
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
	<jsp:include page="untne001q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr style="display:none">
		<td class="td_form" width="96">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
	 </tr>
		<tr>
		<td class="td_form" width="16%">權屬：</td>
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
		<td class="td_form">物品編號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','','NE')">]
			[<input class="field_QRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			&nbsp;分號：
			[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
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
			使用年限：[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="3" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　調整後年限(月)：[<input class="field_RO" type="text" name="otherLimitYear" size="8" maxlength="3" value="<%=obj.getOtherLimitYear()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">牌照號碼規格/序號規格：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="licensePlate" size="30" maxlength="30" value="<%=obj.getLicensePlate()%>">
			&nbsp;&nbsp;　　　　　用途：<input class="field" type="text" name="purpose" size="30" maxlength="50" value="<%=obj.getPurpose()%>">
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
		<td class="td_form">原始保管使用資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "originalKeepUnit", "originalKeepUnitQuickly", obj.getOriginalKeepUnit()) %>
				<input class="field_Q" type="button" name="btn_q_originalKeepUnit" onclick="popUnitNo(form1.organID.value,'originalKeepUnit','keepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "originalKeeper", "originalKeeperQuickly", obj.getOriginalKeeper()) %>
		        <input class="field_Q" type="button" name="btn_q_originalKeeper" onclick="popUnitMan(form1.organID.value,'originalKeeper','keeper')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "originalUseUnit", "originalUseUnitQuickly", obj.getOriginalUseUnit()) %>
				<input class="field_Q" type="button" name="btn_q_originalUseUnit" onclick="popUnitNo(form1.organID.value,'originalUseUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "originalUser", "originalUserQuickly", obj.getOriginalUser()) %>
		        <input class="field_Q" type="button" name="btn_q_originalUser" onclick="popUnitMan(form1.organID.value,'originalUser')" value="..." title="人員輔助視窗">
		        <br/>
			          使用註記：
			    <input type="text" class="field" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20">
		        <br>
				存置地點
				<input class="field_Q" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1()%>" onchange="queryPlaceName('enterOrg','originalPlace1');chOriginalPlace1();">
				<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'originalPlace1','originalPlace1Name');" value="..." title="存置地點輔助視窗">
			    [<input class="field_RO" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name()%>">]
				<br>		
				存置地點說明
				<input class="field_Q" type="text" name="originalPlace" size="30" maxlength="30" value="<%=obj.getOriginalPlace()%>" onchange="chOriginalPlace();">		
		</td>
	</tr>
	<tr>
		<td class="td_form">目前保管使用資料：</td>
	    <td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_RO", "form1", "keepUnit", "keepUnitQuickly", obj.getKeepUnit()) %>
				<input class="field_RO" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'keepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_RO", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
		        <input class="field_RO" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'keeper')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_RO", "form1", "useUnit", "useUnitQuickly", obj.getUseUnit()) %>
				<input class="field_RO" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_RO", "form1", "userNo", "userNoQuickly", obj.getUserNo()) %>
		        <input class="field_RO" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'userNo')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
			          使用註記：
			    <input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">
		        <br>
				存置地點
				<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>" onchange="queryPlaceName('enterOrg','place1');">
				<input class="field_RO" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'place1','place1Name');" value="..." title="存置地點輔助視窗">
			    [<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name()%>">]
				<br>		
				存置地點說明
				<input class="field_RO" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">		
		</td>
	</tr>
	<tr>
		<td class="td_form">其他事項：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="notes1" size="60" maxlength="60" value="<%=obj.getNotes1()%>">
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
	<span id="spanopenUpdate">
		<input class="toolbar_default" type="button" followPK="false" name="openUpdate" value="保管資料修改" onClick="openBatchUpdateWindow();">&nbsp;|
	</span>
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
	boolean primaryArray[] = {true,false,false,true,false,true,true,false,false,false,false,false,false,false,false,true,false};
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
			
				//changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
				//changeBureau(form1.q_enterOrg, form1.q_keepBureau, '');
				//changeBureau(form1.q_enterOrg, form1.q_useBureau, '');
				//changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'');
				//changeKeepUnit(form1.q_enterOrg, form1.q_useBureau, form1.q_useUnit,'');
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
				setFormItem("keepUnit,useUnit,userNo,keeper,originalKeepUnit,originalKeeper,originalUseUnit,originalUser,originalPlace,btn_q_originalKeepUnit,btn_q_originalKeeper,btn_q_originalUseUnit,btn_q_originalUser,btn_q_keepUnit,btn_q_keeper,btn_q_useUnit,btn_q_userNo", "R")
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
		case "clear":
				setFormItem("keepUnit,useUnit,userNo,keeper,originalKeepUnit,originalKeeper,originalUseUnit,originalUser,originalPlace,btn_q_originalKeepUnit,btn_q_originalKeeper,btn_q_originalUseUnit,btn_q_originalUser,btn_q_keepUnit,btn_q_keeper,btn_q_useUnit,btn_q_userNo", "R")
			
	}
};

$(function(){
	$('input[name="originalKeepUnitQuickly"]').bind('change',function(){
		var temp = $('input[name="originalKeepUnitQuickly"]').val();
		$('select[name="keepUnit"]').val(temp);
		$('input[name="keepUnitQuickly"]').val(temp);		
		$('select[name="useUnit"]').val(temp);
		$('input[name="useUnitQuickly"]').val(temp);
	});	
	$('select[name="originalKeepUnit"]').bind('change',function(){
		var temp = $('select[name="originalKeepUnit"] :selected').val();
		$('select[name="keepUnit"]').val(temp);
		$('input[name="keepUnitQuickly"]').val(temp);		
		$('select[name="useUnit"]').val(temp);
		$('input[name="useUnitQuickly"]').val(temp);
	});	
	$('input[name="originalKeeperQuickly"]').bind('change',function(){
		var temp = $('input[name="originalKeeperQuickly"]').val();
		$('select[name="keeper"]').val(temp);
		$('input[name="keeperQuickly"]').val(temp);		
		$('select[name="userNo"]').val(temp);
		$('input[name="userNoQuickly"]').val(temp);
	});			
	$('select[name="originalKeeper"]').bind('change',function(){
		var temp = $('select[name="originalKeeper"] :selected').val();
		$('select[name="keeper"]').val(temp);
		$('input[name="keeperQuickly"]').val(temp);		
		$('select[name="userNo"]').val(temp);
		$('input[name="userNoQuickly"]').val(temp);
	});
	$('select[name="originalUseUnit"]').bind('change',function(){
		var temp = $('select[name="originalUseUnit"] :selected').val();
		$('select[name="useUnit"]').val(temp);
		$('input[name="useUnitQuickly"]').val(temp);
	});
	$('select[name="originalUser"]').bind('change',function(){
		var temp = $('select[name="originalUser"] :selected').val();
		$('select[name="userNo"]').val(temp);
		$('input[name="userNoQuickly"]').val(temp);
	});
});

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
</script>
</body>
</html>



