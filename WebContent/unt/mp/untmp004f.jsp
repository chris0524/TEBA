<!--
程式目的：動產主檔資料維護－批號明細
程式代號：untmp004f
程式日期：0941021
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP004F)obj.queryOne();	
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
		
		}else{
			if("".equals(obj.getSerialNo())){
				obj.setEnterOrg(((String[])objList.get(0))[0]);
				obj.setOwnership(((String[])objList.get(0))[1]);
				obj.setCaseNo(((String[])objList.get(0))[2]);
				obj.setDifferenceKind(((String[])objList.get(0))[3]);
				obj.setPropertyNo(((String[])objList.get(0))[5]);
				obj.setSerialNo(((String[])objList.get(0))[6]);
				obj.setLotNo(((String[])objList.get(0))[7]);
			}	
			obj = (unt.mp.UNTMP004F)obj.queryOne();
		}
	}
		
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._MP_ADD, 3);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._MP_ADD, 3);
}else{
	uch._setupViewType(uch._queryOrMaintenance);
	tabs = uch._createTabData(uch._MP_ADD, 3);
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
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function queryPlaceName1(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../ch/queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');
			if (data['placeName'] == "") {
				$("input[name='" + queryPlace + "Name']").val('');
				$("input[name='" + queryPlace + "']").val('');
				alert("不存在的存置地點");
			} else {
				$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			}

			
		});	
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untmp001f.jsp";
		} else if (document.all.querySelect[2].checked) {
			form1.action = "untmp004f.jsp";
		} else {
			form1.action = "untmp002f.jsp";
			form1.caseNo.value = "";
		}
	}else if (form1.state.value=="update" || (form1.state.value=="updateError")){
		alertStr += checkDate(form1.originalMoveDate,"原始移動日期");
		//alertStr += checkEmpty(form1.originalKeepUnit,"原始保管單位");
		//alertStr += checkEmpty(form1.originalKeeper,"原始保管人");
		//alertStr += checkEmpty(form1.originalUseUnit,"原始使用單位");
		//alertStr += checkEmpty(form1.originalUser,"原始使用人");
		alertStr += checkEmpty(form1.originalPlace1, "存置地點");
		alertStr += checkEmpty(form1.originalPlace1Name, "存置地點");
			
		alertStr += checkFloat(form1.scrapValue,"預留殘值",15,0);
		alertStr += checkFloat(form1.accumDepr,"累計折舊調整",15,0);
		alertStr += checkFloat(form1.deprAmount,"應攤提折舊總額",15,0);

		//alertStr += checkFloat(form1.monthDepr,"月提折舊金額",15,0);
		//當減損原因選「其他」時，開放其他說明欄位且必須有資料
		if(form1.reduceCause.value=="99") alertStr += checkEmpty(form1.reduceCause1,"其他說明");
		else form1.reduceCause1.value = "";
		//未入帳時原始移動地點與目前移動地點需相同
		if(form1.verify.value=="N"){
			changePlace();
			changeMoveDate();

/*
			//=========================================
			var accumDeprYM = form1.accumDeprYM.value;
			var deprMethod = form1.deprMethod.value;
			var scrapValue = form1.scrapValue.value;
			var bookValue = form1.bookValue.value;
			var accumDepr = form1.accumDepr.value;
			if (deprMethod=="02" || deprMethod=="03" ||deprMethod=="04"){
				if(accumDeprYM < (getDateAdd("m", -1,form1.buyDate).substring(0,5))){
					alertStr += "累計折舊調整年月必須 ≧ 「購置日期」之年月－1個月( "+(getDateAdd("m", -1,form1.buyDate).substring(0,5))+" )!\n";
					//document.all.accumDeprYM.focus();
				}
				if(parseInt(scrapValue)<0 || parseInt(scrapValue)>parseInt(bookValue)){
					alertStr += " 0 ≦ 預留殘值 ≦ 帳面金額總價 !\n";
					//document.all.scrapValue.focus();
				}
				if(parseInt(accumDepr)<0 || parseInt(accumDepr)>parseInt(bookValue)){
					alertStr += " 0 ≦ 累計折舊調整 ≦ 帳面金額總價 !\n";
					//document.all.accumDepr.focus();
				}
			}
*/			
		}
		
//		checkDeprMethod();
		if(form1.monthDepr.value.length>0){
			if(form1.monthDepr.value<0){
				alertStr += "月提折舊金額必須>0!\n";
			}
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,serialNo,lotNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.differenceKind.value=differenceKind;
	form1.propertyNo.value=propertyNo;
	form1.lotNo.value=lotNo;
	form1.serialNo.value=serialNo;
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

function changeKeep1(){
	changeKeepUnit(form1.tempEnterOrg, '', form1.originalKeepUnit,'');
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	changeKeepUnit(form1.tempEnterOrg, '', form1.originalUseUnit, '');
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}

function changeKeep2(){
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	changeKeepUnit(form1.tempEnterOrg, '', form1.originalUseUnit, form1.originalKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}


function changeKeep3(){	
	getKeepUnit(form1.tempEnterOrg, form1.keepUnit, form1.originalKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.keepUnit, form1.keeper, form1.originalKeeper.value ,'N');
	getKeepUnit(form1.tempEnterOrg, form1.useUnit, form1.originalUseUnit.value);
	getKeeper(form1.tempEnterOrg, form1.useUnit, form1.userNo, form1.originalUser.value ,'N');
}


function checkURL(surl){
	var alertStr = "";
	
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
	if (surl=="../ch/untch001f02.jsp"){
		if('<%=obj.getIsAddProof()%>' != 'true'){
			form1.state.value="queryAll";
		}
	} else {
        form1.state.value="queryAll";
        alertStr += checkEmpty(form1.propertyNo,"財產編號");
        alertStr += checkEmpty(form1.serialNo,"財產分號");          
    }

    alertStr += checkEmpty(form1.enterOrg,"入帳機關");
    alertStr += checkEmpty(form1.ownership,"權屬");
    if(alertStr.length!=0){
        alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
        return false;
    }
	if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
		form1.mainPage1.value="";
		form1.currentPage.value=form1.mainPage.value;
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
	/** 若是查出的「批號明細」之「資料狀態」若為「已減損」，均不允許修改該批號明細  **/
	if (<%=obj.getDataState().equals("2")%>){
		setFormItem("update", "R");
	}
	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,clear,confirm,batchSetButAll,openUpdate", "R");
	}

	if((<%=obj.getInitDtl().equals("Y") || "".equals(obj.getQuerySelect())%>) && form1.verify.value=="N"){
		setFormItem("openUpdate", "O");
	}else{
		setFormItem("openUpdate", "R");
		setFormItem("btn_originalKeepUnit", "R");
		setFormItem("btn_originalKeeper", "R");
		setFormItem("btn_originalUseUnit", "R");
		setFormItem("btn_originalUser", "R");
	}
	
	setDisplayItem("spanQueryAll,spanInsert,spanNextInsert","H");
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanopenUpdate","H");
	} else if(form1.progID.value == 'untch001f02extend02') {
		setDisplayItem("spanopenUpdate","H");
	}
	
	if ('<%=obj.queryVerify()%>' == "Y") {
        setFormItem("update", "R");
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
		rowValue += "&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&propertyNo="+form1.propertyNo.value+"&lotNo="+form1.lotNo.value;
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
		var q_closing = parse(form1.q_closing.value);
		if(q_closing.length>0){
			rowValue +="&q_closing="+form1.q_closing.value;
		}else{
			rowValue +="&q_closing=";
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

function checkDeprMethod(){
	var alertStr="";
	var buyDate = (form1.buyDate.value).substring(0,5);
	var accumDeprYM = form1.accumDeprYM.value;
	var deprMethod = form1.deprMethod.value;
	var scrapValue = parseInt(form1.scrapValue.value);
	var bookValue = parseInt(form1.bookValue.value);
	var accumDepr = parseInt(form1.accumDepr.value);
	var apportionEndYM = form1.apportionEndYM.value;
	var apportionEndY = apportionEndYM.substring(0,3);
	var apportionEndM = apportionEndYM.substring(3,5);
	var accumDeprY = accumDeprYM.substring(0,3);
	var accumDeprM = accumDeprYM.substring(3,5);
	var year = apportionEndY-accumDeprY;
	var month = apportionEndM-accumDeprM;
	month = (year*12)+month;
	alertStr += checkYYYMM(form1.accumDeprYM,"累計折舊調整年月\n");
	if (deprMethod=="02" || deprMethod=="03" ||deprMethod=="04"){
		if(accumDeprYM < (getDateAdd("m", -1,form1.buyDate).substring(0,5))){
			alertStr += "累計折舊調整年月必須 ≧ 「購置日期」之年月－1個月( "+(getDateAdd("m", -1,form1.buyDate).substring(0,5))+" )!\n";
			document.all.accumDeprYM.focus();
		}
		if(parseInt(scrapValue)<0 || parseInt(scrapValue)>parseInt(bookValue)){
			alertStr += " 0 ≦ 預留殘值 ≦ 帳面金額總價 !\n";
			document.all.scrapValue.focus();
		}
		if(parseInt(accumDepr)<0 || parseInt(accumDepr)>parseInt(bookValue)){
			alertStr += " 0 ≦ 累計折舊調整 ≦ 帳面金額總價 !\n";
			document.all.accumDepr.focus();
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	var deprAmount='0';
	var monthDepr='0';
	switch (form1.deprMethod.value) {
		case "02" :	
			//deprAmount = parseInt(form1.deprAmount.value) - parseInt(scrapValue);
			deprAmount = parseInt(form1.deprAmount.value);
			monthDepr = Math.round((parseInt(deprAmount)-parseInt(accumDepr))/parseInt(month));
			break;
		case "03" :	
			deprAmount = parseInt(form1.deprAmount.value);
			monthDepr = Math.round((parseInt(deprAmount)-parseInt(accumDepr))/parseInt(month));
			break;
		case "04" :	
			deprAmount = parseInt(form1.deprAmount.value);
			monthDepr = Math.round((parseInt(deprAmount)-parseInt(accumDepr))/parseInt(month));
			break;
	}
	form1.deprAmount.value = deprAmount;
	form1.monthDepr.value = monthDepr;
}

function changeDamageDate(){
	if(parse(form1.damageDate.value).length>0){
		form1.damageExpire.value = getDateAdd("m",6,form1.damageDate.value);;
	}else{
		form1.damageExpire.value = "";
	}
}

function openBatchUpdateWindow() {
	var alertStr = "";	
	var prop="";
	var windowTop=(document.body.clientHeight-1000)/2+25;
	var windowLeft=(document.body.clientWidth-1300)/2+250;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width=1300,height=700,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	alertStr += checkEmpty(form1.lotNo,"財產批號");
	alertStr += checkEmpty(form1.differenceKind,"財產區分別");
	popWin = window.open('untmp004f_batch.jsp?enterOrg='+form1.enterOrg.value+
		'&ownership='+form1.ownership.value+'&propertyNo='+form1.propertyNo.value+
		"&lotNo="+form1.lotNo.value+"&differenceKind="+form1.differenceKind.value,'MyWindow',prop);
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input type="hidden" name="buyDate" value="<%=obj.getBuyDate()%>">
<input type="hidden" name="deprMethod" value="<%=obj.getDeprMethod()%>">
<input class="field_QRO" type="hidden" name="serialNoS" size="10" maxlength="10" value="<%=obj.getSerialNoS()%>">
<input class="field_QRO" type="hidden" name="serialNoE" size="10" maxlength="10" value="<%=obj.getSerialNoE()%>">
<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
<!--Query區============================================================-->
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<table class="queryTable"  border="1">
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
	</table>	
</div>
<!-- 保留第一頁查詢條件與頁數使用 -->
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="17%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>		
			　&nbsp;&nbsp;電腦單號：
			[<input class="field_QRO" type="text" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>">]			
		</td>
	</tr>
	<tr>
		<td class="td_form">財產區分別：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
       	</td>
	</tr>
	<tr>
		<td class="td_form">工程編號：</td>
		<td colspan="3" class="td_form_white">
       		[<input class="field_PRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo()%>" onBlur="">]
			[<input class="field_PRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName()%>">]
       	</td>
    </tr>
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]
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
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopProperty("field_QRO","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"3,4,5&isLookup=Y")%>
			&nbsp;分號：
			[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
			&nbsp;批號：
			[<input class="field_QRO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
		<br>
			原財產編號：
			[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原財產分號：
			[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		<br>
			別名：
			<input class="field" type="text" name="propertyName1" size="30" maxlength="30" value="<%=obj.getPropertyName1()%>">
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
			&nbsp;&nbsp;　　　　　用途及其他：<input class="field" type="text" name="purpose" size="40" maxlength="100" value="<%=obj.getPurpose()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">帳面資料：</td>
		<td class="td_form_white" colspan="3">
			原始入帳數量：[<input class="field_RO" type="text" name="originalAmount" size="7" maxlength="7" value="<%=obj.getOriginalAmount()%>">]
			&nbsp;　　　　　　 原始入帳價值：
			[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">]
		<br>		
			數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">]
			&nbsp;　　　　　　　　 原值：
			[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
			&nbsp;&nbsp;&nbsp;
			帳面價值(淨值)：[<input class="field_RO" type="text" name="netValue" size="15" maxlength="15" value="<%=obj.getNetValue()%>" onChange="changeArea();">]
		</td>
	</tr>
	<tr>
		<td class="td_form">盤點資料：</td>
		<td class="td_form_white" colspan="3">
			條碼：
			[<input class="field_RO" type="text" name="barCode" size="30" maxlength="30" value="<%=obj.getBarCode()%>">]			
		</td>
	</tr>
		<tr>
			<td class="td_form">原始移動資料：</td>
			<td class="td_form_white" colspan="3">
				保管單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                         "field_RO", "form1", "originalKeepUnit", "originalKeepUnitQuickly", obj.getOriginalKeepUnit()) %>
				<input class="field_RO" type="button" name="btn_originalKeepUnit" onclick="popUnitNo('<%=user.getOrganID() %>','originalKeepUnit','originalUseUnit','keepUnit','useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_RO", "form1", "originalKeeper", "originalKeeperQuickly",  obj.getOriginalKeeper()) %>
				<input class="field_RO" type="button" name="btn_originalKeeper" onclick="popUnitMan('<%=user.getOrganID() %>','originalKeeper','originalUser','keeper','userNo')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field_RO", "form1", "originalUseUnit", "originalUseUnitQuickly",  obj.getOriginalUseUnit()) %>
				<input class="field_RO" type="button" name="btn_originalUseUnit" onclick="popUnitNo('<%=user.getOrganID() %>','originalUseUnit','useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                        "field_RO", "form1", "originalUser", "form1.userNo.value = this.value;form1.userNo.onchange();", 
			                                                        "originalUserQuickly", "", obj.getOriginalUser()) %>
				<input class="field_RO" type="button" name="btn_originalUser" onclick="popUnitMan('<%=user.getOrganID() %>','originalUser','userNo')" value="..." title="人員輔助視窗">
				<br/>
				使用註記
				<input type="text" class="field" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20" maxlength="100">
				<br>
				移動日期：
				<%=util.View.getPopCalndar("field","originalMoveDate",obj.getOriginalMoveDate())%>
				<br>
				存置地點：
				<input class="field" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1() %>" onchange="queryPlaceName1('enterOrg','originalPlace1');">
				<input class="field" type="button" name="btn_place1" onclick="popPlace('<%=user.getOrganID() %>','originalplace1','originalplace1Name')" value="..." title="存置地點輔助視窗">
				<input class="field" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name() %>">
				<br>		
				存置地點說明：
				<input class="field" type="text" name="originalPlace" size="45" maxlength="45" value="<%=obj.getOriginalPlace() %>" >
			</td>
		</tr>
		<tr>
			<td class="td_form">目前移動資料：</td>
			<td class="td_form_white" colspan="3">
				保管單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field_RO", "form1", "keepUnit", "keepUnitQuickly", obj.getKeepUnit()) %>
				<!--<input class="field_RO" type="button" name="btn_keepUnit" onclick="popUnitNo('keepUnit')" value="..." title="單位輔助視窗">-->
				&nbsp;&nbsp;&nbsp;保管人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_RO", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
				<!--<input class="field_RO" type="button" name="btn_keeper" onclick="popUnitMan('keeper')" value="..." title="人員輔助視窗">-->
				<br>
				使用單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field_RO", "form1", "useUnit", "useUnitQuickly", obj.getUseUnit()) %>
				<!--<input class="field_RO" type="button" name="btn_useUnit" onclick="popUnitNo('useUnit')" value="..." title="單位輔助視窗">-->
				&nbsp;&nbsp;&nbsp;使用人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_RO", "form1", "userNo", "userNoQuickly", obj.getUserNo()) %>
				<!--<input class="field_RO" type="button" name="btn_userNo" onclick="popUnitMan('userNo')" value="..." title="人員輔助視窗">-->
				<br/>
				使用註記
				[<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">]
				<br>
				移動日期：
				[<input type="text" class="field_RO" name="moveDate" value="<%=obj.getMoveDate() %>" size="7"><%--<%=util.View.getPopCalndar("field_RO","moveDate",obj.getMoveDate())%>--%>]
				<br>
				存置地點：
				[<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>" onchange="queryPlaceName('enterOrg','place1');">]
				[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
				<br>		
				存置地點說明：
				[<input class="field_RO" type="text" name="place" size="45" maxlength="45" value="<%=obj.getPlace() %>" >]
			</td>
	</tr>
	<tr>
		<td class="td_form">原始折舊資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>折舊方法：
			<select class="field_PRO" type="select" name="originalDeprMethod">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getOriginalDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field_PRO" type="checkbox" name="cb_originalBuildFeeCB" size="10" maxlength="10"  <%=("Y".equals(obj.getOriginalBuildFeeCB())? "checked" : "")%>>
			<input class="field_PRO" type="hidden" name="originalBuildFeeCB" size="10" maxlength="10" value="<%=obj.getOriginalBuildFeeCB()%>">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_PRO" type="checkbox" name="cb_originalDeprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getOriginalDeprUnitCB())?"checked":"")%>>
			<input class="field_PRO" type="hidden" name="originalDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOriginalDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別：
			<select class="field_PRO" type="select" name="originalDeprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別：
			<select class="field_PRO" type="select" name="originalDeprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_PRO" type="select" name="originalDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目：
			<select class="field_PRO" type="select" name="originalDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprAccounts())%>
			</select>
			<br>
			殘值：
			<input class="field_PRO" type="text" id="originalScrapValue" name="originalScrapValue" size="10" maxlength="10" value="<%=obj.getOriginalScrapValue()%>">
			&nbsp;&nbsp;
			應攤提折舊總額：
			<input class="field_PRO" type="text" name="originalDeprAmount" size="20" maxlength="15" value="<%=obj.getOriginalDeprAmount()%>" onChange="changeArea();">
			<br>
			累計折舊
			[<input class="field_PRO" type="text" name="originalAccumDepr" size="20" maxlength="15" value="<%=obj.getOriginalAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			<input class="field_PRO" type="text" name="originalApportionMonth" size="10" maxlength="3" value="<%=obj.getOriginalApportionMonth()%>" onChange="changeArea();">
			月提折舊金額
			[<input class="field_PRO" type="text" name="originalMonthDepr" size="10" value="<%=obj.getOriginalMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_PRO" type="text" name="originalMonthDepr1" size="10" value="<%=obj.getOriginalMonthDepr1()%>">]			
			攤提年限截止年月
			[<input class="field_PRO" type="text" name="originalApportionEndYM" size="10" value="<%=obj.getOriginalApportionEndYM()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">目前折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法：
			<select class="field_PRO" type="select" name="deprMethod">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field_PRO" type="checkbox" name="cb_buildFeeCB" size="10" maxlength="10" <%=("Y".equals(obj.getBuildFeeCB())?"checked":"")%>>
			<input class="field_PRO" type="hidden" name="buildFeeCB" size="10" maxlength="10" value="<%=obj.getBuildFeeCB()%>">
			屬公共設施建設費：
			&nbsp;&nbsp;
			<input class="field_PRO" type="checkbox" name="cb_deprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getDeprUnitCB())?"checked":"")%>>
			<input class="field_PRO" type="hidden" name="deprUnitCB" size="10" maxlength="10" value="<%=obj.getDeprUnitCB()%>">
			部門別依比例分攤：
			<br>
			園區別：
			<select class="field_PRO" type="select" name="deprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別：
			<select class="field_PRO" type="select" name="deprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_PRO" type="select" name="deprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目：
			<select class="field_PRO" type="select" name="deprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprAccounts())%>
			</select>
			<br>
			殘值：
			[<input class="field_PRO" type="text" id="originalScrapValue" name="scrapValue" size="10" maxlength="10" value="<%=obj.getScrapValue()%>">]
			&nbsp;&nbsp;
			應攤提折舊總額：
			[<input class="field_PRO" type="text" name="deprAmount" size="20" maxlength="15" value="<%=obj.getDeprAmount()%>" onChange="changeArea();">]
			<br>
			累計折舊
			[<input class="field_PRO" type="text" name="accumDepr" size="20" maxlength="15" value="<%=obj.getAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_PRO" type="text" name="apportionMonth" size="10" maxlength="3" value="<%=obj.getApportionMonth()%>" onChange="changeArea();">]
			月提折舊金額
			[<input class="field_PRO" type="text" name="monthDepr" size="10" value="<%=obj.getMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_PRO" type="text" name="monthDepr1" size="10" value="<%=obj.getMonthDepr1()%>">]			
			攤提年限截止年月
			[<input class="field_PRO" type="text" name="apportionEndYM" size="10" value="<%=obj.getApportionEndYM()%>">]
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
			　其它說明：[<input class="field_RO" type="text" name="reduceCause1" size="15" maxlength="20" value="<%=obj.getReduceCause1()%>" readonly="true">]
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
	  	<textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
	  <td class="td_form"style="display:none">異動人員/日期：
	  <td class="td_form_white"style="display:none">
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
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">	
	
	<input type="hidden" name="q_propertyNoS" value="<%=Common.get(request.getParameter("q_propertyNoS")) %>" >
	<input type="hidden" name="q_propertyNoE" value="<%=Common.get(request.getParameter("q_propertyNoE")) %>" >		
	<input type="hidden" name="q_serialNoS" value="<%=Common.get(request.getParameter("q_serialNoS")) %>" >
	<input type="hidden" name="q_serialNoE" value="<%=Common.get(request.getParameter("q_serialNoE")) %>" >
	<input type="hidden" name="q_dataState" value="<%=Common.get(request.getParameter("q_dataState")) %>" >
	<input type="hidden" name="q_differenceKind" value="<%=Common.get(request.getParameter("q_differenceKind")) %>" >
	<input type="hidden" name="q_verify" value="<%=Common.get(request.getParameter("q_verify")) %>" >
	<input type="hidden" name="q_ownership" value="<%=Common.get(request.getParameter("q_ownership")) %>" >
	<input type="hidden" name="q_differenceKind_detail" value="<%=Common.get(request.getParameter("q_differenceKind_detail")) %>" >
	<input type="hidden" name="q_propertyName1" value="<%=Common.get(request.getParameter("q_propertyName1")) %>" >
	<input type="hidden" name="q_originalUserNote" value="<%=Common.get(request.getParameter("q_originalUserNote")) %>" >
	<input type="hidden" name="q_keepUnit" value="<%=Common.get(request.getParameter("q_keepUnit")) %>" >
	<input type="hidden" name="q_keeper" value="<%=Common.get(request.getParameter("q_keeper")) %>" >
	<input type="hidden" name="q_useUnit" value="<%=Common.get(request.getParameter("q_useUnit")) %>" >
	<input type="hidden" name="q_userNo" value="<%=Common.get(request.getParameter("q_userNo")) %>" >
	<input type="hidden" name="q_place1" value="<%=Common.get(request.getParameter("q_place1")) %>" >
	<input type="hidden" name="q_place1Name" value="<%=Common.get(request.getParameter("q_place1Name")) %>" >
	<input type="hidden" name="q_place" value="<%=Common.get(request.getParameter("q_place")) %>" >
	<input type="hidden" name="q_engineeringNo" value="<%=Common.get(request.getParameter("q_engineeringNo")) %>" >
	<input type="hidden" name="q_engineeringNoName" value="<%=Common.get(request.getParameter("q_engineeringNoName")) %>" >
	<input type="hidden" name="q_originalBuildFeeCB" value="<%=Common.get(request.getParameter("q_originalBuildFeeCB")) %>" >
	<input type="hidden" name="q_originalDeprUnitCB" value="<%=Common.get(request.getParameter("q_originalDeprUnitCB")) %>" >
	<input type="hidden" name="q_signLaNo1" value="<%=Common.get(request.getParameter("q_signLaNo1")) %>" >
	<input type="hidden" name="q_signLaNo2" value="<%=Common.get(request.getParameter("q_signLaNo2")) %>" >
	<input type="hidden" name="q_signLaNo3" value="<%=Common.get(request.getParameter("q_signLaNo3")) %>" >
	<input type="hidden" name="q_propertyKind" value="<%=Common.get(request.getParameter("q_propertyKind")) %>" >
	<input type="hidden" name="q_fundType" value="<%=Common.get(request.getParameter("q_fundType")) %>" >
	<input type="hidden" name="q_valuable" value="<%=Common.get(request.getParameter("q_valuable")) %>" >
	<input type="hidden" name="q_taxCredit" value="<%=Common.get(request.getParameter("q_taxCredit")) %>" >
	<input type="hidden" name="q_writeDateS" value="<%=Common.get(request.getParameter("q_writeDateS")) %>" >
	<input type="hidden" name="q_writeDateE" value="<%=Common.get(request.getParameter("q_writeDateE")) %>" >
	<input type="hidden" name="q_caseName" value="<%=Common.get(request.getParameter("q_caseName")) %>" >
	<input type="hidden" name="q_proofYear" value="<%=Common.get(request.getParameter("q_proofYear")) %>" >
	<input type="hidden" name="q_proofDoc" value="<%=Common.get(request.getParameter("q_proofDoc")) %>" >
	<input type="hidden" name="q_proofNoS" value="<%=Common.get(request.getParameter("q_proofNoS")) %>" >
	<input type="hidden" name="q_proofNoE" value="<%=Common.get(request.getParameter("q_proofNoE")) %>" >
	<input type="hidden" name="q_enterDateS" value="<%=Common.get(request.getParameter("q_enterDateS")) %>" >
	<input type="hidden" name="q_enterDateE" value="<%=Common.get(request.getParameter("q_enterDateE")) %>" >
	<input type="hidden" name="q_sourceDateS" value="<%=Common.get(request.getParameter("q_sourceDateS")) %>" >
	<input type="hidden" name="q_sourceDateE" value="<%=Common.get(request.getParameter("q_sourceDateE")) %>" >
	<input type="hidden" name="q_oldPropertyNoS" value="<%=Common.get(request.getParameter("q_oldPropertyNoS")) %>" >
	<input type="hidden" name="q_oldPropertyNoE" value="<%=Common.get(request.getParameter("q_oldPropertyNoE")) %>" >
	<input type="hidden" name="q_oldSerialNoS" value="<%=Common.get(request.getParameter("q_oldSerialNoS")) %>" >
	<input type="hidden" name="q_oldSerialNoE" value="<%=Common.get(request.getParameter("q_oldSerialNoE")) %>" >
	<input type="hidden" name="q_oldlotNoS" value="<%=Common.get(request.getParameter("q_oldlotNoS")) %>" >
	<input type="hidden" name="q_oldlotNoE" value="<%=Common.get(request.getParameter("q_oldlotNoE")) %>" >
	<input type="hidden" name="q_specification" value="<%=Common.get(request.getParameter("q_specification")) %>" >
	<input type="hidden" name="q_nameplate" value="<%=Common.get(request.getParameter("q_nameplate")) %>" >
	
	<span id="spanopenUpdate">
		|&nbsp;&nbsp;<input class="toolbar_default" type="button" followPK="false" name="openUpdate" value="保管資料修改" onClick="openBatchUpdateWindow();">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">保管單位</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">原值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">帳面價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,false,true,true,true,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,true,true,true,false,true,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script type="text/javascript">
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
				//changeKeepUnit(form1.q_enterOrg, '', form1.q_keepUnit,'');
				//changeKeepUnit(form1.q_enterOrg, '', form1.q_useUnit,'');
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
			if(form1.verify.value=="Y" || form1.closing.value=="Y"){
				setFormItem("btn_originalKeepUnit,btn_originalKeeper,btn_originalUseUnit,btn_originalUser,originalKeepUnit,originalKeeper,originalUseUnit,originalUser,originalPlace", "R")			
			}
			break;
		case "updateError":
			//當減損原因不是「其他」時，鎖住其他說明欄位
			if(form1.reduceCause.value !="99"){
				form1.reduceCause1.readOnly = true;
			}	
			if(form1.verify.value=="Y" || form1.closing.value=="Y"){
				setFormItem("btn_originalKeepUnit,btn_originalKeeper,btn_originalUseUnit,btn_originalUser,originalKeepUnit,originalKeeper,originalUseUnit,originalUser,originalPlace", "R")			
			}			
			break;
	}
}
</script>
</body>
</html>



