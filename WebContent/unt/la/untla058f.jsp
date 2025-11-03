<!--
程式目的：土地合併分割作業－增加單明細
程式代號：untla058f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA058F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
//Excel下載暫定使用 web.xml 中filestoreLocation的設定位置
String downloadFilePath = application.getInitParameter("filestoreLocation") + "/untch006f01_Sample.xls";


obj.setParameterValue();

obj.setEnterOrgName(obj.getEnterOrgNameFromDB(obj.getParameterData()));

if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA058F)obj.queryOne();	
}else if ("insert".equals(obj.getState())) {
	obj.insert();
	
}else if ("update".equals(obj.getState())) {
	obj.update();
	
}else if ("delete".equals(obj.getState())) {
	obj.delete();	
	obj.clearAllDataForView();
	obj.setPropertyNo("");
	obj.setPropertyNo_Add("");
	obj.setSerialNo("");
	obj.setSerialNo_Add("");
	
}else if ("insertError".equals(obj.getState()) || "updateError".equals(obj.getState()) || "deleteError".equals(obj.getState())){
	obj.clearAllDataForView();
	
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if("init".equals(obj.getState())) {
	
}else{
	obj.setQueryAllFlag("true");
}

if ("true".equals(obj.getQueryAllFlag())){
	obj.clearAllDataForView();
	obj.setCaseNo(obj.getCaseNo_Add());
	objList = obj.queryAll();
	if(objList.isEmpty()){
		obj.queryCause();		
	}else{
		obj = (unt.la.UNTLA058F)obj.queryOne();
		if("".equals(obj.getCause())) {
			obj.queryCause();
		}
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../ch/untch.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("manageOrg","<%=user.getOrganID()%>"),
	new Array("manageOrgName","<%=user.getOrganName()%>"),
	new Array("verify","N"),
	new Array("valuable","N"),
	new Array("taxCredit","N"),
	new Array("nonProof","Y")	
);

function checkField(){
	form1.propertyNo.value = form1.propertyNo_Add.value;
	form1.serialNo.value = form1.serialNo_Add.value;
	
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"土地增加單-電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.signNo1,"土地標示代碼－縣市");
		alertStr += checkEmpty(form1.signNo2,"土地標示代碼－鄉鎮市區");
		alertStr += checkEmpty(form1.signNo3,"土地標示代碼－地段");
		alertStr += checkEmpty(form1.signNo4,"土地標示代碼－地號(母號)");
		alertStr += checkEmpty(form1.signNo5,"土地標示代碼－地號(子號)");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		//alertStr += checkEmpty(form1.taxCredit,"抵繳遺產稅");

		//alertStr += checkEmpty(form1.sourceKind,"財產來源");
		//alertStr += checkEmpty(form1.ownershipCause,"所有權登記原因");
		alertStr += checkEmpty(form1.ownershipDate,"所有權登記日期");
		//alertStr += checkEmpty(form1.useSeparate,"使用分區");

		alertStr += checkEmpty(form1.originalArea,"原始整筆面積(㎡)");
		alertStr += checkFloat(form1.originalArea,"原始整筆面積(㎡)",9,2);
				if(parseInt(form1.originalArea.value,10)<0){
						alertStr += "原始整筆面積(㎡)必須大於0\n";
							form1.originalArea.style.backgroundColor = errorbg;
					}else{	form1.originalArea.style.backgroundColor = "";}	
			
			alertStr += checkEmpty(form1.originalHoldNume,"原始權利範圍－分子");
			alertStr += checkInt(form1.originalHoldNume,"原始權利範圍－分子");
				if(parseInt(form1.originalHoldNume.value,10)<=0){
						alertStr += "原始權利範圍－分子必須大於等於0\n";
							form1.originalHoldNume.style.backgroundColor = errorbg;
				}else if(parseInt(form1.originalHoldNume.value,10) > parseInt(form1.originalHoldDeno.value,10)){
						alertStr += "原始權利範圍－分子不可大於原始權利範圍－分母\n";
							form1.originalHoldNume.style.backgroundColor = errorbg;
				}else{	form1.originalHoldNume.style.backgroundColor = "";}
				
			alertStr += checkEmpty(form1.originalHoldDeno,"原始權利範圍－分母");
			alertStr += checkInt(form1.originalHoldDeno,"原始權利範圍－分母");
				if(parseInt(form1.originalHoldDeno.value,10)<0){
						alertStr += "原始權利範圍－分母必須大於0\n";
							form1.originalHoldDeno.style.backgroundColor = errorbg;
					}else{	form1.originalHoldDeno.style.backgroundColor = "";}
			
			alertStr += checkFloat(form1.originalHoldArea,"原始權利範圍面積(㎡)",9,2);
		
		alertStr += checkFloat(form1.area,"整筆面積(㎡)",9,2);
				if(parseInt(form1.area.value,10)<0){
					alertStr += "整筆面積(㎡)必須大於0\n";
						form1.area.style.backgroundColor = errorbg;
				}else{	form1.area.style.backgroundColor = "";}	
			
			alertStr += checkInt(form1.holdNume,"權利範圍－分子");
				if(parseInt(form1.holdNume.value,10)<=0){
						alertStr += "權利範圍－分子必須大於等於0\n";
							form1.holdNume.style.backgroundColor = errorbg;
					}else{	form1.holdNume.style.backgroundColor = "";}
			
				if(parseInt(form1.holdNume.value,10) > parseInt(form1.holdDeno.value,10)){
						alertStr += "權利範圍－分子不可大於權利範圍－分母\n";
							form1.holdNume.style.backgroundColor = errorbg;
					}else{	form1.holdNume.style.backgroundColor = "";}
				
			alertStr += checkInt(form1.holdDeno,"權利範圍－分母");
				if(parseInt(form1.holdDeno.value,10)<0){
						alertStr += "權利範圍－分母必須大於0\n";
							form1.holdDeno.style.backgroundColor = errorbg;
					}else{	form1.holdDeno.style.backgroundColor = "";}
			
			alertStr += checkFloat(form1.holdArea,"權利範圍面積(㎡)",9,2);

		alertStr += checkYYYMM(form1.originalDate,"原始入帳－公告年月");
		alertStr += checkEmpty(form1.originalUnit,"原始入帳－單價");
		alertStr += checkFloat(form1.originalUnit,"原始入帳－單價",13,2);
			if(parseInt(form1.originalUnit.value,10)<0){
				alertStr += "原始入帳－單價必須大於0\n";
					form1.originalUnit.style.backgroundColor = errorbg;
			}else{	form1.originalUnit.style.backgroundColor = "";}
			
		alertStr += checkInt(form1.originalBV,"原始入帳－總價");
			if(parseInt(form1.originalBV.value,10)<=0){
				alertStr += "原始入帳－總價必須大於等於0\n";
					form1.originalBV.style.backgroundColor = errorbg;
			}else{	form1.originalBV.style.backgroundColor = "";}
		
		alertStr += checkFloat(form1.bookUnit,"帳面金額－單價",13,2);
			if(parseInt(form1.bookUnit.value,10)<0){
				alertStr += "原始入帳－單價必須大於0\n";
					form1.bookUnit.style.backgroundColor = errorbg;
			}else{	form1.bookUnit.style.backgroundColor = "";}
			
		alertStr += checkInt(form1.bookValue,"帳面金額－總價");		
			if(parseInt(form1.bookValue.value,10)<=0){
				alertStr += "原始入帳－總價必須大於等於0\n";
					form1.bookValue.style.backgroundColor = errorbg;
			}else{	form1.bookValue.style.backgroundColor = "";}
		
		//alertStr += checkDate(form1.ownershipDate,"所有權登記日期");
		//alertStr += checkEmpty(form1.nonProof,"權狀");
		alertStr += checkLen(form1.notes,"備註",250);
		
		//「財產性質」非「01:公務用」選項時，「基金財產」不可有資料；
		//「財產性質」為「01:公務用」選項時，「基金財產」可選也可以不選。
		if(form1.propertyKind.value!="01") {			form1.fundType.value ="";form1.fundType.style.backgroundColor = "";}
	
		//若「使用分區」為「A」開頭時，「編定使用種類」必須有資料
		if((form1.useSeparate.value).substr(0,1)=="A"){ alertStr += checkEmpty(form1.useKind,"編定使用種類");
		}else{ 											form1.useKind.value ="";form1.useKind.style.backgroundColor = "";}
		
		//若「權狀」為「Y:有權狀」，「權狀字號」必須有資料
		if(form1.nonProof.value=="Y"){ 	alertStr += checkEmpty(form1.proofDoc,"權狀字號");
		}else { 						form1.proofDoc.value ="";form1.proofDoc.style.backgroundColor = ""; }

		//控制「經費來源」非「其他補助款」選項時，「經費來源－其他說明」不可有資料
		//若「經費來源」為「其他補助款」選項時，「經費來源－其他說明」必須有資料。
		if(form1.fundsSource.value=="03"){ 	alertStr += checkEmpty(form1.fundsSource1,"經費來源－其他說明");
		}else { 							form1.fundsSource1.value ="";form1.fundsSource1.style.backgroundColor = ""; }
		
	}
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	form1.signNo.value = form1.signNo3.value + form1.signNo4.value + form1.signNo5.value;
	beforeSubmit();
}

function queryOne(enterOrg,differenceKind,ownership,caseNo,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo_Add.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.differenceKind.value=differenceKind;

	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	if(form1.cause.value == "413"){
		setFormItem("dataSelect","R");
		setFormItem("btn02,btn03","O");
	}else{
		setFormItem("dataSelect","O");
		setFormItem("btn02,btn03","R");
	}
	
	setDisplayItem("spanInsert,spanQueryAll,spanNextInsert", "H");
	setFormItem("insert,update,delete","R");
	
	
	//若沒有選擇任何資料
	if(form1.propertyNo.value==''){				setFormItem("update,delete","R");
												setFormItem("insert","O");
	}else{										setFormItem("insert,update,delete","O");}
	
	
	//非登入者所屬機關所登錄的資料
	if(form1.enterorgNotConfirm.value=="Y"){	setFormItem("insert,update,delete,dataSelect","R");}
	//查出的資料若其「案件資料」已入帳
	if(form1.alreadyVerify.value=="Y"){			setFormItem("insert,update,delete,dataSelect","R");}
	//若尚未新增增加單案件
	if(form1.caseNo_Add.value==''){				setFormItem("insert,update,delete,dataSelect","R");}												
	
	changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');
	changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');
	
	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeepUnitQuickly, form1.originalKeepUnit, form1.originalUseUnitQuickly, form1.originalUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeeperQuickly, form1.originalKeeper, form1.originalUserQuickly, form1.originalUser, true, false);
	var dcc3 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keepUnitQuickly, form1.keepUnit, form1.useUnitQuickly, form1.useUnit, true, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keeperQuickly, form1.keeper, form1.userNoQuickly, form1.userNo, true, false);
}


function checkURL(surl){
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消！！");
	}else{
		if(surl=='untla062f.jsp' || surl=='untla064f.jsp'){		
			if(form1.propertyNo.value=='' && form1.serialNo.value==''){
				alert('請先點選一筆資料！！');
				return false;
			}else{
				form1.state.value="init";
			}
		}else{
			form1.state.value="queryAll";
		}
					
		form1.action=surl+"?initDtl=Y";
		beforeSubmit();
		form1.submit();
	}
}

function changeArea(check){
	
	var holdAreaTemp;
	form1.originalHoldNume.value = Math.round(form1.originalHoldNume.value);
	form1.originalHoldDeno.value = Math.round(form1.originalHoldDeno.value);

	//「原始整筆面積×原始權利範圍」四捨五入取至小數第2位
	holdAreaTemp = roundp((form1.originalArea.value * (form1.originalHoldNume.value / form1.originalHoldDeno.value) ),'2',"Y") ;
	if(holdAreaTemp==null || holdAreaTemp==""){	holdAreaTemp=0;}
	form1.originalHoldArea.value = holdAreaTemp;
	
	if (parse(form1.originalHoldArea.value).length > 0 && 
				(check=="originalUnit" || 
				 check=="originalArea" || 
				 check=="originalHoldNume" || 
				 check=="originalHoldDeno")){
		form1.originalBV.value = Math.round(form1.originalUnit.value * form1.originalHoldArea.value);
	}	

	//現值加四成
	if (parse(form1.originalHoldArea.value).length > 0 && check=="addFour"){		
		form1.originalBV.value = Math.round(form1.originalUnit.value * form1.originalHoldArea.value * 1.4);
	}
	
	//原始入帳(單價)＝原始入帳(總價)÷原始權利範圍面積，四捨五入取至小數第2位
	if (parse(form1.originalHoldArea.value).length > 0 && check=="originalBV"){
		form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalHoldArea.value),2,"Y");
	}
	
	form1.area.value 	  = form1.originalArea.value;
	form1.holdNume.value  = form1.originalHoldNume.value;
	form1.holdDeno.value  = form1.originalHoldDeno.value; 
	form1.holdArea.value  = form1.originalHoldArea.value;		
	form1.bookUnit.value  = form1.originalUnit.value;
	form1.bookValue.value = form1.originalBV.value;
	form1.netUnit.value = form1.bookUnit.value;
	form1.netValue.value = form1.bookValue.value;  		
			
}

function execDataSelect(){
	var url = "";
	if (form1.cause.value=="402" || form1.cause.value=="403"){ 			
		url = "untla059f.jsp";
	}else if (form1.cause.value=="401" || form1.cause.value === "407"){								
		url = "untla060f.jsp";
	}	
	
	var prop="";
	var windowTop=(document.body.clientHeight)/2-100;
	var windowLeft=(document.body.clientWidth)/2-400;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no, menubar=no,";
	prop=prop+"width=1100,height=400,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	var condition="?enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&differenceKind="+form1.differenceKind.value+"&caseNo_Merge="+form1.caseNo_Merge.value
					+"&caseNo_Reduce="+form1.caseNo_Reduce.value+"&caseNo_Add="+form1.caseNo_Add.value+"&propertyNo_Add="+form1.propertyNo_Add.value+"&serialNo_Add="+form1.serialNo_Add.value;

	window.open(url + condition,'MyWindow',prop);
}

function btn02_event(){
	openDownloadWindow('','<%=downloadFilePath%>');
}

function btn03_event(){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=750,height=200,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=yes";
	window.open("../ch/untch006f01.jsp?enterorg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&caseno="+form1.caseNo.value+"&differenceKind="+form1.differenceKind.value+"&caseNo_Reduce="+form1.caseNo_Reduce.value+"&caseNo_Merge="+form1.caseNo_Merge.value,"",prop);
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField();">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla054f.jsp');"><font color="red">*</font>案件資料</a></td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla055f.jsp');"><font color="red">*</font>減損單</a></td>
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla056f.jsp');"><font color="red">*</font>減損單明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla057f.jsp');"><font color="red">*</font>增加單</a></td>
		<td ID=t5 CLASS="tab_border1" HEIGHT="25">*增加單明細</td>
		<td ID=t6 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla062f.jsp');">增加單管理資料</a></td>
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla064f.jsp');">增加單地上物資料</a></td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla066f.jsp');">調整前後對應資料</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>		
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">


<input class="field_QRO" type="hidden" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">

<input class="field_QRO" type="hidden" name="caseNo_Reduce" value="<%=obj.getCaseNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="propertyNo_Reduce" value="<%=obj.getPropertyNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="serialNo_Reduce" value="<%=obj.getSerialNo_Reduce()%>">

<input class="field_QRO" type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
<input class="field_QRO" type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">

<input class="field_QRO" type="hidden" name="caseName" size="30" maxlength="25">

<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	
<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA054Q",obj);%>
	<jsp:include page="untla054q.jsp" />
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="5%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			權屬：
			<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
			<select class="field_QRO" type="select" name="ownershipSelect">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;
			<!--  電腦單號：-->
			<input class="field_QRO" type="hidden" name="caseNo_Add" size="10" maxlength="10" value="<%=obj.getCaseNo_Add()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">案號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="caseNo_Merge" size="10" maxlength="10" value="<%=obj.getCaseNo_Merge()%>">]
			&nbsp;　　　　　　&nbsp;&nbsp;
			批次： 
			[<input class="field_QRO" type="text" name="mergeDivisionBatch" size="10" maxlength="10" value="<%=obj.getMergeDivisionBatch()%>">]					
		</td>
	</tr>
	<tr>
		<td class="td_form">財產區分別：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
			&nbsp;&nbsp;&nbsp;
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo() %>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">工程編號：</td>
		<td colspan="3" class="td_form_white">
			[<input class="field_QRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo() %>" onBlur="">]
			[<input class="field_QRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName() %>">]
       	</td>
	</tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>財產編號：
			<%=util.View.getPopProperty("field_P","propertyNo_Add",obj.getPropertyNo_Add(),obj.getProperty_AddName(),"1")%>
			&nbsp;&nbsp;
			分號： [<input class="field_PRO" type="text" name="serialNo_Add" size="7" maxlength="7" value="<%=obj.getSerialNo_Add()%>" onChange="getFrontZero(this.name, 7);">]
			<br>
			別名： [<input class="field_PRO" type="text" name="" size="30" value="">]
			<br>
			原財產編號： &nbsp;&nbsp;&nbsp;&nbsp;[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
			　　　　&nbsp;&nbsp;
			原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]			
		</td>
	</tr>
	
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white" colspan="2">
			<input class="field" type="hidden" name="signNo" value="<%=obj.getSignNo() %>">
			<select class="field" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getSignNo1())%>
			</select>　
			<select class="field" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				
			</select>　		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select class="field" type="select" name="signNo3">
				
			</select>　
		</td>
		<td class="td_form_white" colspan="2">
			地號：		
			<input class="field" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4() %>" onchange="getFrontZero(this.name,4);"> -
			<input class="field" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5() %>" onchange="getFrontZero(this.name,4);">
		</td>
	</tr>	
	<tr>
		<td class="td_form">購置日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field","buyDate",obj.getBuyDate())%>
		</td>		
	</tr>				
	<tr>
		<td class="td_form">街路名：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="doorplate" size="58" maxlength="100" value="<%=obj.getDoorplate()%>">
		</td>		
	</tr>	
	<tr>
		<td class="td_form">增加：</td>
		<td class="td_form_white" colspan="3">
			入帳日期：
			[<input class="field_QRO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]
			&nbsp;&nbsp;　
			入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>　
          	<br>
          	增加原因：&nbsp;　
			<select class="field_QRO" type="select" name="cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' ", obj.getCause())%>
			</select>
			　　&nbsp;&nbsp;
			資料狀態：
			<select class="field_QRO" type="select" name="dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
			</select>
          	
			<br>
			其他說明：　&nbsp;
			[<input class="field_QRO" type="text" name="cause1" size="40" maxlength="40" value="<%=obj.getCause1()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan="3">
			入帳日期：
			[<input class="field_QRO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;　
			<br>
          	減損原因：&nbsp;　
			<select class="field_QRO" type="select" name="reduceCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' ", obj.getReduceCause())%>
			</select>
			<br>
			其他說明：　&nbsp;
			[<input class="field_QRO" type="text" name="reduceCause1" size="40" maxlength="40" value="<%=obj.getReduceCause1()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form"><font color="red">*</font>財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind" onchange="if(this.value!='01'){form1.fundType.value='';form1.fundType.disabled=true;}else{form1.fundType.disabled=false;};">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			　　　　　　　　　　　　&nbsp;&nbsp;&nbsp;
			基金財產：
			<select class="field_RO" type="select" name="fundType" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD' ", obj.getFundType())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>珍貴財產：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			　　　　　　　　　　　　&nbsp;&nbsp;&nbsp;
			抵繳遺產稅：			
			<select class="field" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>			
			
			
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>原始面積：</td>
		<td class="td_form_white" colspan="3">
			整筆面積：<input class="field" type="text" name="originalArea" size="9" maxlength="9" value="<%=obj.getOriginalArea()%>" onchange="changeArea(this.name);">
			<br>
			權利分子：<input class="field" type="text" name="originalHoldNume" size="10" maxlength="10" value="<%=obj.getOriginalHoldNume()%>" onchange="changeArea(this.name);">
			&nbsp;&nbsp;/&nbsp;
			權利分母：<input class="field" type="text" name="originalHoldDeno" size="10" maxlength="10" value="<%=obj.getOriginalHoldDeno()%>" onchange="changeArea(this.name);">
			&nbsp;&nbsp;&nbsp;
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="originalHoldArea" size="9" maxlength="9" value="<%=obj.getOriginalHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">目前面積：</td>
		<td class="td_form_white" colspan="3">
			整筆面積：[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">]
			<br>
			權利分子：[<input class="field_RO" type="text" name="holdNume" size="9" maxlength="10" value="<%=obj.getHoldNume()%>">]
			&nbsp;&nbsp;/&nbsp;
			權利分母：[<input class="field_RO" type="text" name="holdDeno" size="9" maxlength="10" value="<%=obj.getHoldDeno()%>">]
			&nbsp;&nbsp;&nbsp;
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">原始入帳：</td>
		<td class="td_form_white" colspan="3">
			依據：
			<select class="field" type="select" name="originalBasis">
				<%=util.View.getTextOption("1;公告地價;2;公告現值;3;取得價格",obj.getOriginalBasis())%>							
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;　　
			 公告年月：
			<input class="field" type="text" name="originalDate" size="5" maxlength="5" value="<%=obj.getOriginalDate()%>"> 
			<input class="field" type="button" name="btn_originalDate" onclick="if (parseInt(form1.originalBasis.value,10)<3) popBulletinDate('originalDate',form1.originalBasis.value,'Y'); else alert('依公告地價或是公告現值時,方需輸入公告年月');" value="..." title="公告年月輔助視窗">&nbsp;&nbsp;&nbsp;
			<input class="toolbar_default" type="button" name="addFour" value="現值加四成" onClick="changeArea(this.name);">
			<br>			
			單價：&nbsp;<input class="field" type="text" name="originalUnit" size="13" maxlength="16" value="<%=obj.getOriginalUnit()%>" onchange="changeArea(this.name);">
			　　&nbsp;　
			<font color="red">*</font>
			總價：&nbsp;<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" onchange="changeArea(this.name);">
			<br>
			摘要：&nbsp;<input class="field" type="text" name="originalNote" size="43" maxlength="40" value="<%=obj.getOriginalNote()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">經費來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="fundsSource" onchange="if(this.value=='03'){form1.fundsSource1.disabled=false}else{form1.fundsSource1.disabled=true};">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			其它說明：<input class="field" type="text" name="fundsSource1" size="40" maxlength="40" value="<%=obj.getFundsSource1()%>" disabled="true">
			&nbsp;&nbsp;&nbsp;
			會計科目：&nbsp;<input class="field" type="text" name="accountingTitle" size="10" maxlength="10" value="<%=obj.getAccountingTitle()%>">			
		</td>		
	</tr>		
	<tr>
		<td class="td_form">原值：</td>
		<td class="td_form_white" colspan="3">
			單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="16" value="<%=obj.getBookUnit()%>">]
			&nbsp;&nbsp;&nbsp;　
			總價：[<input class="field_RO" type="text" name="bookValue" size="14" maxlength="15" value="<%=obj.getBookValue()%>">]
		</td>		
	</tr>
	<tr>
		<td class="td_form">帳面價值(淨值)：</td>
		<td class="td_form_white" colspan="3">
			單價：[<input class="field_RO" type="text" name="netUnit" size="13" maxlength="16" value="<%=obj.getNetUnit()%>">]
			&nbsp;&nbsp;&nbsp;　
			總價：[<input class="field_RO" type="text" name="netValue" size="14" maxlength="15" value="<%=obj.getNetValue()%>">]
		</td>		
	</tr>
	<tr>
		<td class="td_form">使用分區：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="useSeparate" onchange="if((this.value).substr(0,1)=='A'){form1.useKind.disabled=false; }else{form1.useKind.value='';form1.oriUseKind.value='';form1.useKind.disabled=true; }; form1.oriUseSeparate.value=this.value; ">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
			</select>	　
			　　　
			編定使用種類：
			<select class="field_RO" type="select" name="useKind"  onchange="form1.oriUseKind.value=this.value;">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getUseKind())%>			
			</select>
			
			<input class="field" type="hidden" name="oriUseSeparate" value="<%=obj.getOriUseSeparate()%>">
			<input class="field" type="hidden" name="oriUseKind" value="<%=obj.getOriUseKind()%>">
			
		</td>			
	</tr>
	<tr>
		<td class="td_form">權狀資料：</td>
		<td class="td_form_white" colspan="3">
			權狀：
			<select class="field" type="select" name="nonProof" onchange="if(this.value=='Y'){form1.proofDoc.disabled=false;}else{form1.proofDoc.value='';form1.proofDoc.disabled=true;}">
				<%=util.View.getTextOption("Y;有;N;無;",obj.getNonProof())%>
			</select>				
			　&nbsp;&nbsp;　
			權狀字號：<input class="field" type="text" name="proofDoc" size="23" maxlength="20" value="<%=obj.getProofDoc()%>">				
			<br>	
			所有權登記日期：
			<%=util.View.getPopCalndar("field","ownershipDate",obj.getOwnershipDate())%>
			<br>
			所有權登記原因：
			<select class="field" type="select" name="ownershipCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='OCA' ", obj.getOwnershipCause())%>
			</select>
			<br>
			其他登記事項：
			<textarea class="field" type="text" name="ownershipNote" cols="41" rows="2"><%=obj.getOwnershipNote()%></textarea>
		</td>
	</tr>
	<tr>
		<td class="td_form">地上物情形：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="useState1">
				<%=util.View.getTextOption("01;空置;02;建物;03;農作;04;其他;",obj.getUseState1())%>
			</select>
			　　&nbsp;
			地目：
			<select class="field" type="select" name="field">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			等則：
			<input class="field" type="text" name="landRule" size="2" maxlength="2" value="<%=obj.getLandRule()%>" onchange="getFrontZero(this.name, 2);">
		</td>	
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="sourceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKD' ", obj.getSourceKind())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			<font color="red">*</font>
			取得日期：
			<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
			&nbsp;&nbsp;&nbsp;
			取得文號：
			<input class="field" type="text" name="sourceDoc" size="20" maxlength="20" value="<%=obj.getSourceDoc()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">原有人：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="oldOwner" size="31" maxlength="30" value="<%=obj.getOldOwner()%>">
		</td>		
	</tr>
	<tr>
		<td class="td_form">管理機關：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopOrgan("field","manageOrg",obj.getManageOrg(),obj.getManageOrgName(),"Y")%>
			　&nbsp;
			土地現況：
			<select class="field_RO" type="select" name="useState">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UST' ", obj.getUseState())%>
			</select>
			<br>
			　　　　　　　　　　　　&nbsp;
			資產重估日期：
			<input type="text" class="field" name="appraiseDate" value="<%=obj.getAppraiseDate()%>" size="7" maxlength="7">
		</td>	
	</tr>
	<tr>			
		<td class="td_form">其他事項：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="notes1" size="60" maxlength="60" value="<%=obj.getNotes1()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">原始移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalKeepUnit", "originalKeepUnitQuickly",  obj.getOriginalKeepUnit()) %>
			<input class="field" type="button" name="btn_originalKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'originalKeepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalKeeper", "originalKeeperQuickly", obj.getOriginalKeeper()) %>
	        <input class="field" type="button" name="btn_originalKeeper" onclick="popUnitMan(form1.enterOrg.value,'originalKeeper')" value="..." title="人員輔助視窗">
			<br>
			使用單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalUseUnit", "originalUseUnitQuickly", obj.getOriginalUseUnit()) %>
			<input class="field" type="button" name="btn_originalUseUnit" onclick="popUnitNo(form1.enterOrg.value,'originalUseUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalUser", "originalUserQuickly", obj.getOriginalUser()) %>
	        <input class="field" type="button" name="btn_originalUser" onclick="popUnitMan(form1.enterOrg.value,'originalUser')" value="..." title="人員輔助視窗">
	        <br/>
			使用註記：
			<input type="text" class="field" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20">		        
	        <br>
			存置地點：
			<input class="field" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1() %>" onchange="queryPlaceName('enterOrg','originalPlace1');">
			<input class="field" type="button" name="btn_originalPlace1" onclick="popPlace(form1.enterOrg.value,'originalPlace1','originalPlace1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name() %>">]
			<br>		
			存置地點說明：
			<input class="field" type="text" name="originalPlace" size="30" maxlength="30" value="<%=obj.getOriginalPlace() %>">
		</td>
	</tr>
	<tr>
		<td class="td_form">目前移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "keepUnit", "keepUnitQuickly",  obj.getKeepUnit()) %>
			<input class="field" type="button" name="btn_keepUnit" onclick="popUnitNo(form1.enterOrg.value,'keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
			<input class="field" type="button" name="btn_keeper" onclick="popUnitMan(form1.enterOrg.value,'keeper','userNo')" value="..." title="人員輔助視窗">
	        <br>
			使用單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "useUnit", "useUnitQuickly",  obj.getUseUnit()) %>
			<input class="field" type="button" name="btn_useUnit" onclick="popUnitNo(form1.enterOrg.value,'useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "userNo", "userNoQuickly", obj.getUserNo()) %>
			<input class="field" type="button" name="btn_userNo" onclick="popUnitMan(form1.enterOrg.value,'userNo')" value="..." title="人員輔助視窗">
	        <br/>
			使用註記：
			[<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">]		        
	        <br>
			存置地點：
			<input class="field" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>" >
			[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
			<br>		
			存置地點說明：
			[<input class="field_RO" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace() %>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" >備註：</td>
		<td class="td_form_white" width="30%" colspan="4">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form" width="20%"style="display:none;">異動人員/日期：</td>
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
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="dataSelect" value="合併分割資料挑選" onclick="execDataSelect();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="btn02" value="地籍整理Excel格式下載" onclick="btn02_event();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="btn03" value="地籍整理Excel匯入" onclick="btn03_event();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增加原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">持分面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">原價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">帳面價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,false,true,true,true,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,true,false,true,false,false,false,false,true,true,true,true,true,true};
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
       	case "delete":
       	//刪除之前多出現一道確認訊息
			if(!confirm("刪除此筆土地，將一併刪除其關聯的管理資料、地上物、公告地價。\n\n您確定要刪除?"))
				return false;
			hasBeenConfirmed = true;
			break;
	}
	return true;
};

</script>
</body>
</html>



