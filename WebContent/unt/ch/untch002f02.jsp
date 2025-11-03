<!--
程式目的：動產保管使用異動作業－明細資料
程式代號：untmp010f
程式日期：0941019
程式作者：carey
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH002F02">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%

	// 當切換至此頁面時 若有UNTCH002F01紀錄的currentPage 則轉存至PREPAGE_currentPage
	if (!"".equals(obj.getUNTCH002F01_currentPage())) {
		obj.setPREPAGE_currentPage(obj.getUNTCH002F01_currentPage());
		obj.setUNTCH002F01_currentPage("");
		obj.setCurrentPage(1);
	}

if("".equals(Common.checkGet(obj.getRoleid()))){
	obj.setRoleid(user.getRoleid());
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ch.UNTCH002F02)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();	
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}			
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
	obj.setPropertyNo("");
	obj.setSerialNo("");
	obj.setLotNo("");
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	
	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
			
		}else{
			if("".equals(obj.getSerialNo()) && "".equals(obj.getLotNo())){
				obj.setEnterOrg(((String[])objList.get(0))[0]);
				obj.setOwnership(((String[])objList.get(0))[1]);
				obj.setCaseNo(((String[])objList.get(0))[2]);
				obj.setDifferenceKind(((String[])objList.get(0))[4]);
				obj.setPropertyNo(((String[])objList.get(0))[5]);
				obj.setSerialNo(((String[])objList.get(0))[7]);
			}
			obj = (unt.ch.UNTCH002F02)obj.queryOne();
		}
	}
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._CH_MOVE, 2);

%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/unitProperty.js"></script>
<script type="text/javascript" src="../../js/getUNTMPMovable.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("valuable","N"),	
	new Array("ownership","<%=uctls._getDefaultValue(user.getOrganID(), "ownership")%>"),
	new Array("propertyKind","<%=uctls._getDefaultValue(user.getOrganID(), "propertyKind") %>"),
	new Array("newDeprMethod","01")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untch002f01.jsp";
		} else {
			form1.action = "untch002f02.jsp";
		}
	} else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.newPlace1, "存置地點");
		alertStr += checkEmpty(form1.newPlace1Name, "存置地點");
		
		if(form1.newDeprMethod.value == '02'){
			alertStr += checkEmpty(form1.newDeprPark,"園區別");
			
			if($("input[name='newDeprUnitCB']").attr('checked') == 'checked'){
				
			}else{
				alertStr += checkEmpty(form1.newDeprUnit,"部門別");
				alertStr += checkEmpty(form1.newDeprUnit1,"部門別單位");
				alertStr += checkEmpty(form1.newDeprAccounts,"會計科目");					
			}
		}
		
		form1.action = "untch002f02.jsp";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();	
}

function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,serialNo){
	form1.enterOrg.value = enterOrg;
	form1.ownership.value = ownership;
	form1.caseNo.value = caseNo;
	form1.differenceKind.value = differenceKind;
	form1.propertyNo.value = propertyNo;
	form1.serialNo.value = serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(surl!='../ch/untch002f01.jsp' && form1.propertyNo.value==""){
		alert("請先執行查詢!");
	}else{
		form1.currentPage.value=form1.mainPage.value;
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.UNTCH002F01_currentPage.value = form1.PREPAGE_currentPage.value;       
		form1.state.value="queryAll";
		form1.action = surl;
		beforeSubmit();
		form1.submit();		
	}
}

function init(){
	initQ_Form("Detail");
	
	if(form1.propertyNo.value != ''){	changePropertyNo(form1.propertyNo.value);	
	}else{								initFormDisabled();
	}	
	
	//1040417 因為客戶端效能關係，調整以下程式
	//<<<<<<<<<<<<<<<<<<<<<<<
	if('<%=obj.getLaSignNo1()%>' == ''){		
	}else{
		changeSignNo1('laSignNo1','laSignNo2','laSignNo3','<%=obj.getLaSignNo2()%>');
		
		if('<%=obj.getLaSignNo2()%>' == ''){		
		}else{
			changeSignNo2('laSignNo1','laSignNo2','laSignNo3','<%=obj.getLaSignNo3()%>');
		}			
	}
	if('<%=obj.getBuSignNo1()%>' == ''){		
	}else{
		changeSignNo1('buSignNo1','buSignNo2','buSignNo3','<%=obj.getBuSignNo2()%>');
		
		if('<%=obj.getBuSignNo2()%>' == ''){		
		}else{
			changeSignNo2('buSignNo1','buSignNo2','buSignNo3','<%=obj.getBuSignNo3()%>');
		}			
	}
	//>>>>>>>>>>>>>>>>>>>>>>>
	
	
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	if (form1.verify.value=="Y"){
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}

//	setFormItem("batchInsertBut","O");	

	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.newKeepUnitQuickly, form1.newKeepUnit, form1.newUseUnitQuickly, form1.newUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.newKeeperQuickly, form1.newKeeper, form1.newUserNoQuickly, form1.newUserNo, true, false);

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value + form1.differenceKind.value + form1.propertyNo.value + form1.serialNo.value;
		listContainerRowClick(key);
	}
}

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

function gountch002f03(){
	
	window.open("../../unt/ch/untch002f03.jsp?caseNo="+form1.caseNo.value+"&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value);
}

function initFormDisabled(){
	$("#div_spec").hide();
	$("#div_laSignNo").hide();
	$("#div_buSignNo").hide();
	$("#div_laArea").hide();
	$("#div_buArea").hide();
	$("#div_rfArea").hide();
	$("tr[name='div_common']").hide();
	$("tr[name='div_depr']").hide();	
}

function changePropertyNo(propertyNo){
	initFormDisabled();
	
	var checkStr1 = form1.propertyNo.value.substring(0,1);
	var checkStr3 = form1.propertyNo.value.substring(0,3);
	
	$("tr[name='div_common']").show();
	
	if("111" == checkStr3){
		$("#div_rfArea").show();
		$("tr[name='div_depr']").show();
		
	}else if("1" == checkStr1){
		$("#div_laSignNo").show();
		$("#div_laArea").show();
		
	}else if("2" == checkStr1){
		$("#div_buSignNo").show();
		$("#div_buArea").show();
		$("tr[name='div_depr']").show();
		
	}else if("3" == checkStr1 || "4" == checkStr1 || "5" == checkStr1){
		$("#div_spec").show();
		$("tr[name='div_depr']").show();
		
	}else if("8" == checkStr1){
		$("tr[name='div_depr']").show();
	}else if("9" == checkStr1){
				
	}
}	

function queryADDProofData(type){
	//傳送JSON
	var comment={};
	comment.enterOrg = $("input[name='enterOrg']").val();
	comment.ownership = $("select[name='ownership']").val();
	comment.differenceKind = $("select[name='differenceKind']").val();
	comment.propertyNo = "";
	comment.serialNo = "";
	comment.laSignNo = "";
	comment.buSignNo = "";
	comment.proofType = "moveProof";
	
	if(type == 'serialNo'){
		comment.propertyNo = $("input[name='propertyNo']").val();
		comment.serialNo = $("input[name='serialNo']").val();
		
	}else if(type == 'laSignNo'){
		if($("select[name='laSignNo3']").val() != '' && $("input[name='laSignNo4']").val() != '' && $("input[name='laSignNo5']").val() != ''){
			comment.laSignNo = $("select[name='laSignNo3']").val() + $("input[name='laSignNo4']").val() + $("input[name='laSignNo5']").val();
		}	
	}else if(type == 'buSignNo'){
		if($("select[name='buSignNo3']").val() != '' && $("input[name='buSignNo4']").val() != '' && $("input[name='buSignNo5']").val() != ''){
			comment.buSignNo = $("select[name='buSignNo3']").val() + $("input[name='buSignNo4']").val() + $("input[name='buSignNo5']").val();		
		}	
	}
	
	if(comment.propertyNo == "" && comment.serialNo == "" && comment.differenceKind == "" && comment.laSignNo == "" & comment.buSignNo == ""){
		
	}else{
		$.post('queryADDProofData.jsp',
			comment,
			function(data){
				//將回傳資料寫入
				data=eval('('+data+')');
	
				if(data['hasData'] == 'N'){
					alert('找不到此筆財產資料！！');
				}else{
					$("input[name='propertyNo']").val(data['propertyNo']);
					$("input[name='serialNo']").val(data['serialNo']);
					$("input[name='propertyName1']").val(data['propertyName1']);
					$("input[name='oldPropertyNo']").val(data['oldPropertyNo']);
					$("input[name='oldSerialNo']").val(data['oldSerialNo']);
					$("input[name='areaLA']").val(data['oldLaArea']);
					$("input[name='holdNumeLA']").val(data['oldLaHoldNume']);
					$("input[name='holdDenoLA']").val(data['oldLaHoldDeno']);
					$("input[name='holdAreaLA']").val(data['oldLaHoldArea']);
					$("input[name='CAreaBU']").val(data['oldBuCArea']);
					$("input[name='SAreaBU']").val(data['oldBuSArea']);
					$("input[name='areaBU']").val(data['oldBuArea']);
					$("input[name='holdNumeBU']").val(data['oldBuHoldNume']);
					$("input[name='holdDenoBU']").val(data['oldBuHoldDeno']);
					$("input[name='hldAreaBU']").val(data['oldBuHoldArea']);
					$("input[name='measure']").val(data['oldMeasure']);
					$("input[name='sourceDate']").val(data['sourceDate']);
					$("input[name='originalBV']").val(data['originalBV']);
					$("input[name='buyDate']").val(data['buyDate']);
					$("input[name='bookNotes']").val(data['bookNotes']);
					
					var check1 = data['propertyNo'].substring(0,1);
					if(check1 == '3' || check1 == '4' || check1 == '5'){
						$("input[name='bookAmount']").val(data['oldBookAmount']);
						$("input[name='bookUnit']").val(data['oldBookUnit']);
						$("input[name='bookValue']").val(data['oldBookValue']);
						$("input[name='netUnit']").val(data['oldNetUnit']);
						$("input[name='netValue']").val(data['oldNetValue']);
					}else if(check1 == '8' || check1 == '9'){
						$("input[name='bookAmount']").val(data['bookAmount']);
						$("input[name='netValue']").val(data['bookValue']);
						$("input[name='bookValue']").val(data['bookValue']);
					}else{
						$("input[name='bookAmount']").val(data['bookAmount']);						
						$("input[name='bookUnit']").val(data['bookUnit']);
						$("input[name='bookValue']").val(data['bookValue']);
						$("input[name='netUnit']").val(data['netUnit']);
						$("input[name='netValue']").val(data['netValue']);							
					}
					
					$("select[name='oldKeepUnit']").val(data['keepUnit']);
					$("select[name='oldKeeper']").val(data['keeper']);
					$("select[name='oldUseUnit']").val(data['useUnit']);
					$("select[name='oldUserNo']").val(data['userNo']);
					$("input[name='oldUserNote']").val(data['userNote']);
					$("input[name='oldPlace1']").val(data['place1']);
					$("input[name='oldPlace1Name']").val(data['place1name']);
					$("input[name='oldPlace']").val(data['place']);
					$("select[name='newKeepUnit']").val(data['keepUnit']);
					$("input[name='newKeepUnitQuickly']").val(data['keepUnit']);
					$("select[name='newKeeper']").val(data['keeper']);
					$("input[name='newKeeperQuickly']").val(data['keeper']);
					$("select[name='newUseUnit']").val(data['useUnit']);
					$("input[name='newUseUnitQuickly']").val(data['useUnit']);
					$("select[name='newUserNo']").val(data['userNo']);
					$("input[name='newUserNoQuickly']").val(data['userNo']);
					$("input[name='newUserNote']").val(data['userNote']);
					$("input[name='newPlace1']").val(data['place1']);
					$("input[name='newPlace1Name']").val(data['place1name']);
					$("input[name='newPlace']").val(data['place']);

					$("input[name='oldBV']").val(data['oldBV']);
					$("select[name='oldDeprMethod']").val(data['oldDeprMethod']);
					$("input[name='oldBuildFeeCB']").val(data['oldBuildFeeCB']);
					$("input[name='oldDeprUnitCB']").val(data['oldDeprUnitCB']);
					$("select[name='oldDeprPark']").val(data['oldDeprPark']);
					$("select[name='oldDeprUnit']").val(data['oldDeprUnit']);
					$("select[name='oldDeprUnit1']").val(data['oldDeprUnit1']);
					$("select[name='oldDeprAccounts']").val(data['oldDeprAccounts']);
					$("input[name='oldScrapValue']").val(data['oldScrapValue']);
					$("input[name='oldDeprAmount']").val(data['oldDeprAmount']);
					$("input[name='oldAccumDepr']").val(data['oldAccumDepr']);
					$("input[name='oldApportionMonth']").val(data['oldApportionMonth']);
					$("input[name='oldMonthDepr']").val(data['oldMonthDepr']);
					$("input[name='oldMonthDepr1']").val(data['oldMonthDepr1']);
					$("input[name='oldApportionEndYM']").val(data['oldApportionEndYM']);
					
					$("select[name='newDeprMethod']").val(data['oldDeprMethod']);
					$("input[name='newBuildFeeCB']").val(data['oldBuildFeeCB']);
					$("input[name='newDeprUnitCB']").val(data['oldDeprUnitCB']);
					$("select[name='newDeprPark']").val(data['oldDeprPark']);
					$("input[name='newDeprPark']").val(data['oldDeprPark']);
					$("select[name='newDeprUnit']").val(data['oldDeprUnit']);
					$("select[name='newDeprUnit1']").val(data['oldDeprUnit1']);
					$("select[name='newDeprAccounts']").val(data['oldDeprAccounts']);
					$("input[name='newScrapValue']").val(data['oldScrapValue']);
					$("input[name='newDeprAmount']").val(data['oldDeprAmount']);
					$("input[name='newAccumDepr']").val(data['oldAccumDepr']);
					$("input[name='newApportionMonth']").val(data['oldApportionMonth']);
					$("input[name='newMonthDepr']").val(data['oldMonthDepr']);
					$("input[name='newMonthDepr1']").val(data['oldMonthDepr1']);
					$("input[name='newApportionEndYM']").val(data['oldApportionEndYM']);
					
					$("input[name='material']").val(data['material']);
					$("input[name='propertyUnit']").val(data['propertyunit']);
					$("input[name='limitYear']").val(data['limityear']);
					$("input[name='otherMaterial']").val(data['otherMaterial']);

					$("input[name='useYear']").val(data['useYear']);
					$("input[name='useMonth']").val(data['useMonth']);
					
					$("input[name='otherPropertyUnit']").val(data['otherPropertyUnit']);
					$("input[name='otherLimitYear']").val(data['otherLimitYear']);
					$("input[name='securityName']").val(data['securityName']);			
					$("select[name='laSignNo1']").val(data['laSignNo1']);
					$("select[name='laSignNo2']").val(data['laSignNo2']);
					if($("select[name='laSignNo2']").val() != null){
						changeSignNo1('laSignNo1','laSignNo2','laSignNo3',data['laSignNo2']);
					}
					$("select[name='laSignNo3']").val(data['laSignNo3']);
					if($("select[name='laSignNo3']").val() != null){
						changeSignNo2('laSignNo1','laSignNo2','laSignNo3',data['laSignNo3']);
					}
					$("input[name='laSignNo4']").val(data['laSignNo4']);
					$("input[name='laSignNo5']").val(data['laSignNo5']);
					$("select[name='buSignNo1']").val(data['buSignNo1']);
					$("select[name='buSignNo2']").val(data['buSignNo2']);
					if($("select[name='buSignNo2']").val() != null){
						changeSignNo1('buSignNo1','buSignNo2','buSignNo3',data['buSignNo2']);
					}
					$("select[name='buSignNo3']").val(data['buSignNo3']);
					if($("select[name='buSignNo3']").val() != null){
						changeSignNo2('buSignNo1','buSignNo2','buSignNo3',data['buSignNo2']);
					}
					$("input[name='buSignNo4']").val(data['buSignNo4']);
					$("input[name='buSignNo5']").val(data['buSignNo5']);			
					$("select[name='propertyKind']").val(data['propertyKind']);
					$("select[name='fundType']").val(data['fundType']);
					$("select[name='valuable']").val(data['valuable']);
					$("select[name='taxCredit']").val(data['taxCredit']);
					$("input[name='lotNo']").val(data['lotNo']);
					$("input[name='nameplate']").val(data['nameplate']);
					$("input[name='specification']").val(data['specification']);
					changePropertyNo(data['propertyNo']);
				}
			});	
		
	}
}

function changeDifferenceKind(){
	$("input[name='propertyNo']").val('');
	$("input[name='serialNo']").val('');
	
	if($("select[name='differenceKind']").val() == ''){
		setFormItem("propertyNO,btn_propertyNo,serialNo","R");		
	}else{
		setFormItem("propertyNO,btn_propertyNo,serialNo","O");
	}
}

function checkDeprMethod(value){

	if(form1.newDeprMethod.value == '01'){
		form1.newDeprUnit1.value = "";
		form1.newBuildFeeCB.value = "";
		form1.newDeprUnitCB.value = "";
		form1.newDeprPark.value = "";
		form1.deprPark.value = "";
		form1.newDeprUnit.value = "";
		form1.newDeprAccounts.value = "";
		form1.newScrapValue.value = "";
		form1.newDeprAmount.value = "";
		form1.newAccumDepr.value = "";
		form1.newMonthDepr.value = "";
		form1.newApportionMonth.value = "";
		form1.newApportionEndYM.value = "";
		setFormItem("newDeprUnit1,newBuildFeeCB,newDeprUnitCB,newDeprPark,deprPark,newDeprUnit,newDeprAccounts,newDeprAmount,newAccumDepr,newApportionMonth,newMonthDepr,newScrapValue","R");
	}else if(form1.newDeprMethod.value == '02'){
		form1.newDeprUnit1.value = form1.oldDeprUnit1.value;
		form1.newBuildFeeCB.checked = form1.cb_oldBuildFeeCB.checked;
		form1.newDeprUnitCB.checked = form1.cb_oldDeprUnitCB.checked;
		form1.newDeprPark.value = form1.oldDeprPark.value;
		form1.newDeprUnit.value = form1.oldDeprUnit.value;
		form1.newDeprAccounts.value = form1.oldDeprAccounts.value;
		form1.newScrapValue.value = form1.oldScrapValue.value;
		form1.newDeprAmount.value = form1.oldDeprAmount.value;
		form1.newAccumDepr.value = form1.oldAccumDepr.value;
		form1.newMonthDepr.value = form1.oldMonthDepr.value;
		form1.newApportionMonth.value = form1.oldApportionMonth.value;
		form1.newApportionEndYM.value = form1.oldApportionEndYM.value;
		setFormItem("newDeprUnit1,newBuildFeeCB,newDeprUnitCB,newDeprPark,deprPark,newDeprUnit,newDeprAccounts,newDeprAmount,newAccumDepr,newApportionMonth,newMonthDepr,newScrapValue","O");
	}
}

function frontZero(valueStr,itemp){
	var nv = valueStr.toString();
	var stemp="";
	if(nv!=""){
		itemp -= nv.length;
		stemp = "";		
		for (var i=0;i<itemp;i++){
			stemp+="0";
		}		
		stemp += nv;
	}
	return stemp;
}

function queryDeprUnitData(){
	if($("input[name='newDeprUnittemp']").val() != ''){
		$("select[name='newDeprUnit']").val($("input[name='newDeprUnittemp']").val());
		$("input[name='newDeprUnittemp']").val('');
	}
	queryDeprUnitDataforDeprUnit1();
	queryDeprUnitDataforDeprAccounts();
}

function queryDeprUnitDataforDeprUnit1(){
	if($("select[name='newDeprUnit1']").val() == ''){
		if($("select[name='newDeprUnit']").val() != ''){
			
			//傳送JSON		
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("newDeprUnit").value;
			comment.queryType = "deprUnit1";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');

					$("select[name='newDeprUnit1']").val(data['defaultValue']);
			
				});
		}
	}
}
function queryDeprUnitDataforDeprAccounts(){
	if($("select[name='newDeprAccounts']").val() == ''){
		if($("select[name='newDeprUnit']").val() != ''){	
			//傳送JSON
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("newDeprUnit").value;
			comment.queryType = "deprAccounts";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');
		
					$("select[name='newDeprAccounts']").val(data['defaultValue']);
					
				});
		}
	}
}

function sonSubmit(){
	beforeSubmit();
	form1.submit();	
}
function serialNo_onchange(thisname) {
	getFrontZero(thisname,7);
	queryADDProofData('serialNo');
	//setTimeout(function(){  
		//checkDeprMethod('');
	//},2000);

}

function updatedeprcbClick() {
	if($("input[name='updatedeprcb']").attr('checked') == 'checked'){
		form1.newDeprMethod.disabled = false;
		form1.newBuildFeeCB.disabled = false;
		form1.newDeprUnitCB.disabled = false;
		form1.newDeprPark.disabled = false;
		form1.newDeprUnit.disabled = false;
		form1.newDeprUnit1.disabled = false;
		form1.newDeprAccounts.disabled = false;
	} else {
		form1.newDeprMethod.disabled = true;
		form1.newBuildFeeCB.disabled = true;
		form1.newDeprUnitCB.disabled = true;
		form1.newDeprPark.disabled = true;
		form1.newDeprUnit.disabled = true;
		form1.newDeprUnit1.disabled = true;
		form1.newDeprAccounts.disabled = true;
	}
}

function confirmData(){
	var q="q=1&enterOrg="+form1.tempEnterOrg.value+"&serialNo="+form1.serialNo.value+
		  "&ownership=" +form1.ownership.value+"&propertyNo=" +form1.propertyNo.value+"&differenceKind=" +form1.differenceKind.value;
	var ajaxuri="untch002f02_jason.jsp";
	
	if(form1.tempEnterOrg.value!="" && form1.serialNo.value!="" && form1.ownership.value!="" && form1.propertyNo.value!="" && form1.differenceKind.value!=""){
		var x=getRemoteData(ajaxuri,q);
		
		var sign = eval('(' + x + ')');
	
		if(sign['detail']==null || sign['detail']==""){
			setDisplayItem("spanConfirm","S");			
			return false;
		} else {
			setDisplayItem("spanConfirm","H");			
			alert('該筆財產 已減損!!');			
		}
	}else{
		setDisplayItem("spanConfirm","S");		
		return false;
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input type="hidden" name="UNTCH002F01_currentPage" value="<%=obj.getUNTCH002F01_currentPage()%>">
<input type="hidden" name="PREPAGE_currentPage" value="<%=obj.getPREPAGE_currentPage()%>">
<input type='hidden' name='q_userRole' value='<%= user.getRoleid()%>'>
<input type='hidden' name='q_keeperno' value='<%=user.getKeeperno() %>'>
<input type='hidden' name='q_unitID' value='<%=user.getUnitID() %>'>
<!--Query區============================================================-->
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH002Q",obj);%>
	<jsp:include page="untch002q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">權屬：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			<input class="field_QRO" type="hidden" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">
			
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			<input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="14%"><font color="red">*</font>財產區分別：</td>
		<td class="td_form_white" colspan="3">
			<%=obj._getDifferenceKindHTML("field", "differenceKind", obj.getDifferenceKind(),"DFK") %>
			&nbsp;&nbsp;&nbsp;&nbsp;
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo() %>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">購置日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;&nbsp;　　　　　　　異動單確認：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
			<%=uctls.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"")%>
			&nbsp;
			分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onchange="serialNo_onchange(this.name);confirmData();">
			&nbsp;&nbsp;　　　批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
		<br>
			別名：<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			&nbsp;&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form">原移動資料：</td>
		<td class="td_form_white" colspan="3">
			移動日期：[<input class="field_RO" type="text" name="oldMoveDate" size="7" maxlength="7" value="<%=obj.getOldMoveDate()%>">]
		<br>
			保管單位：
			<select class="field_RO" type="select" name="oldKeepUnit" onchange="form1.oldUseUnit.value = this.value;queryforDeprUnit();">
				<%=util.View.getOption("select unitno, unitno + unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getOldKeepUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;　　保管人：
			<select class="field_RO" type="select" name="oldKeeper" onchange="form1.oldUser.value = this.value;">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getOldKeeper())%>
			</select>
		<br>
			使用單位：
			<select class="field_RO" type="select" name="oldUseUnit">
				<%=util.View.getOption("select unitno, unitno + unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getOldUseUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;　　使用人：
			<select class="field_RO" type="select" name="oldUserNo" onchange="form1.oldUser.value = this.value;">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getOldUserNo())%>
			</select>
			&nbsp;&nbsp;
			使用註記：
			[<input type="text" class="field_RO" name="oldUserNote" value="<%=obj.getOldUserNote() %>" size="20">]
		<br>
			存置地點：
			[<input class="field_RO" type="text" name="oldPlace1" size="10" maxlength="10" value="<%=obj.getOldPlace1() %>">]
			[<input class="field_RO" type="text" name="oldPlace1Name" size="20" maxlength="20" value="<%=obj.getOldPlace1Name() %>">]
		<br>			
			存置地點說明：
			[<input class="field_RO" type="text" name="oldPlace" size="30" maxlength="30" value="<%=obj.getOldPlace() %>">]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form">新移動資料：</td>
		<td class="td_form_white" colspan="3">
			移動日期：[<input class="field_QRO" type="text" name="newMoveDate" size="7" maxlength="7" value="<%=obj.getNewMoveDate()%>">]
		<br>
			<font color="red">*</font>保管單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "newKeepUnit", "form1.newUseUnit.value = this.value;", 
			                                                       "newKeepUnitQuickly", "", obj.getNewKeepUnit()) %>
			<input class="field" type="button" name="btn_newKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'newKeepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;　<font color="red">*</font>保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                        "field", "form1", "newKeeper", "form1.newUser.value = this.value;form1.newUser.onchange();",
			                                                        "newKeeperQuickly", "", obj.getNewKeeper()) %>
			<input class="field" type="button" name="btn_newKeeper" onclick="popUnitMan(form1.enterOrg.value,'newKeeper','newUserNo')" value="..." title="人員輔助視窗">
		<br>
			<font color="red">*</font>使用單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       	"field", "form1", "newUseUnit", "newUseUnitQuickly", obj.getNewUseUnit()) %>
			<input class="field" type="button" name="btn_newUseUnit" onclick="popUnitNo(form1.enterOrg.value,'newUseUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;　<font color="red">*</font>使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                        "field", "form1", "newUserNo", "newUserNoQuickly", obj.getNewUserNo()) %>
			<input class="field" type="button" name="btn_newUserNo" onclick="popUnitMan(form1.enterOrg.value,'newUserNo')" value="..." title="人員輔助視窗">
			&nbsp;&nbsp;
			使用註記：
			<input type="text" class="field" name="newUserNote" value="<%=obj.getNewUserNote() %>" size="20">
		<br>
			存置地點：
			<input class="field" type="text" name="newPlace1" size="10" maxlength="10" value="<%=obj.getNewPlace1() %>" onchange="queryPlaceName1('enterOrg','newPlace1');">
			<input class="field" type="button" name="btn_newPlace1" onclick="popPlace(form1.enterOrg.value,'newPlace1','newPlace1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="newPlace1Name" size="20" maxlength="20" value="<%=obj.getNewPlace1Name() %>">]
			<br>
			存置地點說明：
			<input class="field" type="text" name="newPlace" size="45" maxlength="45" value="<%=obj.getNewPlace() %>">
		</td>
	</tr>
	<tr name="div_depr">
		<td class="td_form">調整前折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法
			<%=util.View._getSelectHTML_withFunction("field_RO", "oldDeprMethod", obj.getOldDeprMethod(),"DEP", "checkDeprMethod('');queryDeprUnitData();") %>
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" name="cb_oldBuildFeeCB" size="10" maxlength="10" onclick="checkOriginalBuildFeeCB();" <%=("Y".equals(obj.getOldBuildFeeCB())?"checked":"")%>>
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" name="cb_oldDeprUnitCB" size="10" maxlength="10" onclick="checkOriginalDeprUnitCB();checkDeprMethod('');" <%=("Y".equals(obj.getOldDeprUnitCB())?"checked":"")%>>
			部門別依比例分攤
			<br>
			園區別
			<select class="field_RO" type="select" name="oldDeprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field_RO" type="select" name="oldDeprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_RO" type="select" name="oldDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field_RO" type="select" name="oldDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprAccounts())%>
			</select>
			<br>
			殘值
			[<input class="field_RO" type="text" name="oldScrapValue" size="10" maxlength="10" value="<%=obj.getOldScrapValue()%>">]
			&nbsp;&nbsp;
			應攤提折舊總額
			[<input class="field_RO" type="text" name="oldDeprAmount" size="20" maxlength="15" value="<%=obj.getOldDeprAmount()%>">]
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="oldAccumDepr" size="20" maxlength="15" value="<%=obj.getOldAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="oldApportionMonth" size="10" maxlength="3" value="<%=obj.getOldApportionMonth()%>">]
			月提折舊金額
			[<input class="field_RO" type="text" name="oldMonthDepr" size="10" value="<%=obj.getOldMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="oldMonthDepr1" size="10" value="<%=obj.getOldMonthDepr1()%>">]
			攤提年限截止年月
			[<input class="field_RO" type="text" name="oldApportionEndYM" size="10" value="<%=obj.getOldApportionEndYM()%>">]
		</td>
	</tr>
	<tr name="div_depr">
		<td class="td_form">調整後折舊資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>折舊方法
			<%=util.View._getSelectHTML_withFunction("field_RO", "newDeprMethod", obj.getNewDeprMethod(),"DEP", "checkDeprMethod('');queryDeprUnitData();") %>
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="updatedeprcb" size="10" maxlength="10" value="Y" <%=("Y".equals(obj.getUpdatedeprcb())?"checked":"")%> onclick="updatedeprcbClick();" >
			是否異動折舊資料
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="newBuildFeeCB" size="10" maxlength="10" value="Y" <%=("Y".equals(obj.getNewBuildFeeCB())?"checked":"")%>>
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="newDeprUnitCB" size="10" maxlength="10" value="Y" <%=("Y".equals(obj.getNewDeprUnitCB())?"checked":"")%>>
			部門別依比例分攤
			<br>
			園區別
			<select class="field_RO" type="select" name="newDeprPark" onchange="form1.newDeprPark.value = this.value;">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprPark())%>
			</select>
			<input type="hidden" class="field_QRO" name="deprPark" value="<%=obj.getNewDeprPark()%>">
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field_RO" type="select" name="newDeprUnit" onchange="queryDeprUnitData();">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprUnit())%>
			</select>
			<input type="hidden" class="field" name="newDeprUnittemp" value="">
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_RO" type="select" name="newDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field_RO" type="select" name="newDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprAccounts())%>
			</select>
			<br>
			殘值
			[<input class="field_RO" type="text" name="newScrapValue" size="10" maxlength="10" value="<%=obj.getNewScrapValue()%>" onchange="checkDeprMethod('');">]
			&nbsp;&nbsp;
			應攤提折舊總額
			[<input class="field_RO" type="text" name="newDeprAmount" size="20" maxlength="15" value="<%=obj.getNewDeprAmount()%>" onChange="changeArea();">]
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="newAccumDepr" size="20" maxlength="15" value="<%=obj.getNewAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="newApportionMonth" size="10" maxlength="3" value="<%=obj.getNewApportionMonth()%>" onChange="changeArea();checkDeprMethod(this.value);">]
			月提折舊金額
			[<input class="field_RO" type="text" name="newMonthDepr" size="10" value="<%=obj.getNewMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="newMonthDepr1" size="10" value="<%=obj.getNewMonthDepr1()%>">]
			攤提年限截止年月
			[<input class="field_RO" type="text" name="newApportionEndYM" size="10" value="<%=obj.getNewApportionEndYM()%>">]
		</td>
	</tr>
	<tr id="div_laSignNo">
		<td class="td_form">土地標示：</td>
		<td class="td_form_white">
			<select class="field_RO" type="select" name="laSignNo1" onChange="changeSignNo1('laSignNo1','laSignNo2','laSignNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getLaSignNo1())%>
			</select>　
			<select class="field_RO" type="select" name="laSignNo2" onChange="changeSignNo2('laSignNo1','laSignNo2','laSignNo3','');">
				
			</select>　		
			<select class="field_RO" type="select" name="laSignNo3">
				
			</select>　
		</td>
		<td class="td_form_white" colspan="2">
			地號：		
			<input class="field_RO" type="text" name="laSignNo4" size="4" maxlength="4" value="<%=obj.getLaSignNo4() %>" onchange="getFrontZero(this.name,4);"> -
			<input class="field_RO" type="text" name="laSignNo5" size="4" maxlength="4" value="<%=obj.getLaSignNo5() %>" onchange="getFrontZero(this.name,4);">
		</td>
	</tr>	
	<tr id="div_buSignNo">
		<td class="td_form">建物標示：</td>
		<td class="td_form_white">
			<select class="field_RO" type="select" name="buSignNo1" onChange="changeSignNo1('buSignNo1','buSignNo2','buSignNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getBuSignNo1())%>
			</select>　
			<select class="field_RO" type="select" name="buSignNo2" onChange="changeSignNo2('buSignNo1','buSignNo2','buSignNo3','');">
				
			</select>　		
			<select class="field_RO" type="select" name="buSignNo3">
				
			</select>　
		</td>
		<td class="td_form_white" colspan="2">
			建號：		
			<input class="field_RO" type="text" name="buSignNo4" size="5" maxlength="5" value="<%=obj.getBuSignNo4() %>" onchange="getFrontZero(this.name,5);"> - 
			<input class="field_RO" type="text" name="buSignNo5" size="3" maxlength="3" value="<%=obj.getBuSignNo5() %>" onchange="getFrontZero(this.name,3);">
		</td>
	</tr>
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3">
			主要材質：
			[<input class="field_RO" type="text" name="material" size="25" value="<%=obj.getMaterial()%>">]
			&nbsp;　　其他主要材質：
			[<input class="field_RO" type="text" name="otherMaterial" size="25" maxlength="25" value="<%=obj.getOtherMaterial()%>">]
		<br>
			單位：
			[<input class="field_RO" type="text" name="propertyUnit" size="25" maxlength="25" value="<%=obj.getPropertyUnit()%>">]
			　其他單位：
			[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="25" value="<%=obj.getOtherPropertyUnit() %>">]
		<br>
	  		使用年限： 
        	[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="3" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;調整後年限(月)：
        	[<input class="field_RO" type="text" name="otherLimitYear" size="8" maxlength="3" value="<%=obj.getOtherLimitYear()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">已使用年數：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="useYear" size="3" maxlength="3" value="<%=obj.getUseYear()%>">]
			年
			[<input class="field_RO" type="text" name="useMonth" size="2" maxlength="2" value="<%=obj.getUseMonth()%>">]
			個月
		</td>
	</tr>
	<tr id="div_spec">
		<td class="td_form">廠牌型式：</td>
		<td class="td_form_white" colspan="3">
			型式
			<input class="field" type="text" id="specification" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification() %>">
			&nbsp;&nbsp;&nbsp;&nbsp;
			廠牌
			<input class="field" type="text" id="nameplate" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate() %>">
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View._getSelectHTML("field_RO", "propertyKind", obj.getPropertyKind(),"PKD") %>
			&nbsp;&nbsp;基金財產：
			<%=util.View._getSelectHTML_withEnterOrg("field_RO", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
			&nbsp;&nbsp;珍貴財產：
			<select class="field_RO" type="select" name="valuable">
			　　<%=util.View.getTextOption("Y;是;N;否;",obj.getValuable())%>
			</select>    
			
		</td>
	</tr>	
	<tr id="div_laArea">
		<td class="td_form">土地面積：</td>
		<td class="td_form_white" colspan="3">
			總面積：
			[<input name="areaLA" type="text" class="field_RO" value="<%=obj.getAreaLA() %>" size="12" maxlength="10" onChange="changeArea('areaLA');">]			
			<br>
			權利分子：[<input class="field_RO" type="text" name="holdNumeLA" size="9" maxlength="10" value="<%=obj.getHoldNumeLA() %>" onChange="changeArea('holdNumeLA');">]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			權利分母：[<input class="field_RO" type="text" name="holdDenoLA" size="9" maxlength="10" value="<%=obj.getHoldDenoLA() %>" onChange="changeArea('holdDenoLA');">]
			&nbsp;&nbsp;
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdAreaLA" size="9" maxlength="10" value="<%=obj.getHoldAreaLA() %>">]
		</td>
	</tr>
	<tr id="div_buArea">
		<td class="td_form">建物面積：</td>
		<td colspan="3" class="td_form_white">
			主面積(㎡)：[<input class="field_RO" type="text" name="CAreaBU" size="9" maxlength="10" value="<%=obj.getCAreaBU() %>" onChange="changeArea();">]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			附屬面積：[<input class="field_RO" type="text" name="SAreaBU" size="9" maxlength="10" value="<%=obj.getSAreaBU() %>" onChange="changeArea();">]
　			&nbsp;
			總面積：[<input class="field_RO" type="text" name="areaBU" size="12" value="<%=obj.getAreaBU() %>">]<br>
			權利分子：[<input class="field_RO" type="text" name="holdNumeBU" size="9" maxlength="10" value="<%=obj.getHoldNumeBU() %>" onChange="changeArea();">]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			權利分母：[<input class="field_RO" type="text" name="holdDenoBU" size="9" maxlength="10" value="<%=obj.getHoldDenoBU() %>" onChange="changeArea();">]
			&nbsp;&nbsp;
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdAreaBU" size="9" maxlength="10" value="<%=obj.getHoldAreaBU() %>">]
		</td>
	</tr>
	<tr id="div_rfArea">
		<td class="td_form">土地改良物：</td>
  	    <td colspan="3" class="td_form_white">
  	    	計量數：
      		[<input name="measure" type="text" class="field_RO" onChange="changeArea();" value="<%=obj.getMeasure() %>" size="15" maxlength="15">]
      		&nbsp;&nbsp;&nbsp;
      		單位
			[<input class="field_RO" type="text" name="propertyUnit" size="10" maxlength="10" value="">]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form">帳面資料：</td>
		<td class="td_form_white" colspan="3">
			取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
			&nbsp;&nbsp;　　　　帳面數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">]
		<br>
			原值單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="13" value="<%=obj.getBookUnit()%>">]
			&nbsp;&nbsp;　原值總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		<br>
			淨值單價：[<input class="field_RO" type="text" name="netUnit" size="13" maxlength="13" value="<%=obj.getNetUnit()%>">]
			&nbsp;&nbsp;　淨值總價：[<input class="field_RO" type="text" name="netValue" size="15" maxlength="15" value="<%=obj.getNetValue()%>">]
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
	  	<textarea class="field" type="text" name="notes" cols="50" rows="4"><%=obj.getNotes()%></textarea>
	  </td>
	 </tr>
	 <tr style='display:none'>
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" name="proofYear" value="<%=obj.getProofYear()%>">	
	<input type="hidden" name="proofDoc" value="<%=obj.getProofDoc()%>">	
	<input type="hidden" name="proofNo" value="<%=obj.getProofNo()%>">	
			
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="現有資料明細新增" onClick="gountch002f03();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">新移動日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">新保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">新保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">新存置地點</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,false,true,true,false,true,false,false,false,false,};
	boolean displayArray[] = {false,false,false,true,false,true,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>

<script type="text/javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
			changeDifferenceKind();
			
			initFormDisabled();
			updatedeprcbClick();
			//checkDeprMethod('');
			break;				
		case "update":
			//checkDeprMethod('');
			changePropertyNo(form1.propertyNo.value);
			updatedeprcbClick();
			break;
	}
}

</script>
</body>
</html>



