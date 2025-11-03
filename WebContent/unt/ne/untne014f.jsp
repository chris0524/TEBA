<!--
程式目的：物品減少作業－減損單明細
程式代號：untne014f
程式日期：0941114
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE014F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE014F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	try{
		obj.insert();
	}catch(Exception e){
		obj.setErrorMsg(e.getMessage());
	}
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	try{
		obj.update();
	}catch(Exception e){
		obj.setErrorMsg(e.getMessage());
	}
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
} else if ("updateNewData".equals(obj.getState())) {
	obj.updateNewData();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
} else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	try{
		obj.delete();
	}catch(Exception e){
		obj.setErrorMsg(e.getMessage());
	}
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("".equals(obj.getSerialNo())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setOwnership(((String[])objList.get(0))[2]);
			obj.setCaseSerialNo(((String[])objList.get(0))[5]);
			obj.setCaseNo(((String[])objList.get(0))[4]);
			obj.setDifferenceKind(((String[])objList.get(0))[17]);
			obj.setPropertyNo(((String[])objList.get(0))[6]);
			obj.setSerialNo(((String[])objList.get(0))[8]);
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
<script language="javascript" src="../../js/getUNTNENonexp.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>")	
);

//當增加原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.cause.value=="499"){
		form1.cause1.readOnly = false;
		form1.cause1.style.backgroundColor=editbg;
		form1.newEnterOrgName.value = "";
		form1.newEnterOrg.value = "";
		form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
	}else if(form1.cause.value=="201" || form1.cause.value=="205"|| form1.cause.value=="206"){
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
		setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "R");
		form1.transferDate.style.backgroundColor=editbg;
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
	}else{
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
		form1.newEnterOrgName.value = "";
		form1.newEnterOrg.value = "";
		form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
		setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "O");
	}
	
	if(form1.cause.value=="02"){
	    form1.cause2.value="已逾最低年限，不堪使用";
	}else{
	    form1.cause2.value="";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untne013f.jsp";
		} else {
			form1.action = "untne014f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		//alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"物品編號");
		alertStr += checkEmpty(form1.serialNo,"物品分號");
		alertStr += checkEmpty(form1.cause,"減損原因");
		alertStr += checkLen(form1.notes, "備註", 250);	
		alertStr += checkQuotation(form1.notes,"備註");
		if (form1.cause.value=="99"){
			alertStr += checkEmpty(form1.cause1,"減損原因-其他說明");
		}
		
		if ((form1.cause.value=="01")||(form1.cause.value=="07")||(form1.cause.value=="08")){
			alertStr += checkEmpty(form1.newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.transferDate,"交接日期");
		}
		
		alertStr += checkDate(form1.transferDate,"交接日期");
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.buyDate,"購置日期");
		
		alertStr += checkEmpty(form1.adjustBookAmount,"減少面積數量");
		alertStr += checkEmpty(form1.adjustBookValue,"減少面積金額─總價");
		
		if (parseFloat(form1.adjustBookAmount.value) <= 0) alertStr += "減少帳面數量必須大於0\n";
		if (parseInt(form1.adjustBookValue.value) < 0) alertStr += "減少帳面金額─總價必須大於等於0\n";
		
		if (form1.oldBookAmount.value=="1") {
			if ((form1.adjustBookAmount.value != form1.oldBookAmount.value)||(form1.adjustBookValue.value != form1.oldBookValue.value)){
				alertStr += "原帳面數量為1，則減少帳面數量與金額須與原來的一致\n";
			}
		}
		
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}


function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untne013f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.propertyNo,"物品編號");
				alertStr += checkEmpty(form1.serialNo,"物品分號");			
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"物品編號");
			alertStr += checkEmpty(form1.serialNo,"物品分號");			
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		//alertStr += checkEmpty(form1.ownership,"權屬");
		//alertStr += checkEmpty(form1.caseNo,"電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		
		form1.currentPage.value=form1.mainPage.value;
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function gountne015f(){
	var prop="";
	//var windowTop=(document.body.clientHeight-80)/2+25;
	//var windowLeft=(document.body.clientWidth-800)/2+250;
	//prop=prop+"width=775,height=475,";
	
	window.open("../../unt/ne/untne015f.jsp?caseNo="+form1.caseNo.value+"&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&reduceDate="+form1.reduceDate.value+"&verify="+form1.verify.value+"&differenceKind="+form1.differenceKind.value);
	
	
// 	var iwidth=screen.availWidth;
// 	var iheight=screen.availHeight;
// 	var windowTop=0;
// 	var windowLeft=0;
// 	prop=prop+"width="+iwidth+",";
// 	prop=prop+"height="+iheight+",";
	
// 	prop=prop+"top="+windowTop+",";
// 	prop=prop+"left="+windowLeft+",";
// 	prop=prop+"scrollbars=yes";
// 	var enterOrg = form1.enterOrg.value;
// 	var ownership = form1.ownership.value;
// 	var caseNo = form1.caseNo.value;
// 	var reduceDate = form1.reduceDate.value;
// 	var verify = form1.verify.value;
// 	var differenceKind = form1.differenceKind.value;
// 	beforeSubmit();
// 	//區分別鎖定
// 	//setFormItem("differenceKind","R");
// 	returnWindow=window.open("untne015f.jsp?enterOrg="+enterOrg+"&ownership="+ownership+"&caseNo="+caseNo+"&reduceDate="+reduceDate+"&verify="+verify+"&differenceKind="+differenceKind,"aha",prop);	
}

//取得已使用年數、累計折舊、殘餘價值、需否呈報市府核定等資料
//untne014.jsp 和 untne015f.java 都有用到
function getdata(){
	//已使用年數 = (系統日期 - 購置日期) 之年數
	form1.useYear.value = parseInt((getDateDiff("m", form1.buyDate , form1.getToday))/12) ;
	
	//已使用月數 = (系統日期 - 購置日期) ★/12 之餘額月數
	form1.useMonth.value = (getDateDiff("m", form1.buyDate , form1.getToday))%12;
	
	if(form1.cause.value == '214' && (getDateDiff("m", form1.buyDate , form1.getToday)) != form1.otherLimitYear.value){
		alert('此筆財產未達年限，請重新確認！！');
	}

}

function updateNewData() {
	if(confirm("確定執行[帶入最新資料]嗎")){
		form1.state.value="updateNewData";
		beforeSubmit();
		form1.submit();
	}
}

function getNewBookData() { 
	form1.newBookAmount.value = parseFloat(form1.oldBookAmount.value) - parseFloat(form1.adjustBookAmount.value) ;
	form1.newBookValue.value = parseFloat(form1.oldBookValue.value) - parseFloat(form1.adjustBookValue.value) ;
	form1.newBookUnit.value = parseFloat(form1.oldBookUnit.value) - parseFloat(form1.adjustBookUnit.value) ;
} 

function getDefault(){
	form1.adjustBookAmount.value = form1.oldBookAmount.value;
	form1.adjustBookValue.value = form1.oldBookValue.value;
	form1.adjustBookUnit.value = form1.oldBookUnit.value;
}

function init(){
	var arrField = new Array("update","delete");
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete,batchInsertBut,NewData", "R");
	}	
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value+ form1.propertyNo.value + form1.serialNo.value + form1.differenceKind.value ;
		listContainerRowClick(key);
	}
}

function checkValue(){
	var checkCaseNo = form1.caseNo.value;
	var checkPropertyNo = form1.propertyNo.value;
	var checkSerialNo = form1.serialNo.value;
	if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.propertyNo.value="";
		form1.propertyNoName.value="";
		form1.serialNo.value="";
		form1.oldBookUnit.value="";
	}else{
		form1.newBookAmount.value=parseFloat(form1.oldBookAmount.value)-parseFloat(form1.adjustBookAmount.value);
		form1.newBookValue.value=parseInt(form1.oldBookValue.value)-parseInt(form1.adjustBookValue.value);
	}
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
function changeDifferenceKind(){
	$("input[name='propertyNo']").val('');
	$("input[name='serialNo']").val('');
	
	if($("select[name='differenceKind']").val() == ''){
		setFormItem("propertyNO,btn_propertyNo,serialNo","R");		
	}else{
		setFormItem("propertyNO,btn_propertyNo,serialNo","O");
	}
}
function changePropertyNo(propertyNo){}

function getNonexp() {
	var alertStr = "";
	alertStr += checkEmpty(form1.enterOrg, "入帳機關");
	alertStr += checkEmpty(form1.ownership, "權屬");
	alertStr += checkEmpty(form1.propertyNo, "財產編號");
	alertStr += checkEmpty(form1.serialNo, "財產分號");
	if (form1.propertyNo.value == "" || form1.serialNo.value == "") {
		form1.propertyNo.style.backgroundColor = "";
		form1.serialNo.style.backgroundColor = "";
	}
		
	if (alertStr.length != 0) {
		return false;
	} else {
		var senterOrg = form1.enterOrg.value;
		var sownership=form1.ownership.value;
		var sPropertyNo = form1.propertyNo.value;
		var sPropertyName = form1.propertyNoName.value;
		var sSerialNo = form1.serialNo.value;
		var sDifferenceKind = form1.differenceKind.value;
		var params = "";	
 
		params = "1&enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+sPropertyNo+"&propertyNoName="+encodeURIComponent(sPropertyName)+"&serialNo="+sSerialNo+"&differenceKind="+sDifferenceKind;

		//傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
		var randomnumber = Math.floor(Math.random() * 1000);
		params += "&randomnumber=" + randomnumber;
		var x = getRemoteData(getVirtualPath() + 'home/xmlUNTNENonexp.jsp', params);
		if (x != null && x.length > 0) {
			var json = jQuery.parseJSON(x); //因jQuery.parseJSON()可兼容ie, chrome, edge
			if (json != null && json.length) {
				for(var i = 0; i < json.length; i++) {
					form1.check.value = json[i].check;
					form1.propertyNo.value =  json[i].propertyNo;
					form1.propertyNoName.value =  json[i].propertyNoName;
					form1.serialNo.value =  json[i].serialNo;
					form1.lotNo.value =  json[i].lotNo;
					form1.material.value =  json[i].material;
					form1.otherMaterial.value =  json[i].otherMaterial;
					form1.propertyUnit.value =  json[i].propertyUnit;
					form1.otherPropertyUnit.value =  json[i].otherPropertyUnit;
					form1.limitYear.value =  json[i].limitYear;
					form1.otherLimitYear.value =  json[i].otherLimitYear;
					form1.propertyName1.value =  json[i].propertyName1;
					form1.enterDate.value =  json[i].enterDate;
					form1.buyDate.value =  json[i].buyDate;
					form1.propertyKind.value =  json[i].propertyKind;
					form1.fundType.value =  json[i].fundType;
					form1.valuable.value =  json[i].valuable;
					form1.accountingTitle.value =  json[i].accountingTitle;
					form1.oldBookValue.value =  json[i].bookValue;
					form1.oldBookUnit.value =  json[i].bookValue;
					form1.oldBookAmount.value = json[i].bookAmount;
					form1.articleName.value =  json[i].articleName;
					form1.specification.value =  json[i].specification;
					form1.nameplate.value =  json[i].nameplate;
					form1.sourceDate.value =  json[i].sourceDate;
					form1.licensePlate.value =  json[i].licensePlate;
					form1.moveDate.value =  json[i].moveDate;
					form1.keepUnit.value =  json[i].keepUnit;
					form1.keeper.value = json[i].keeper;
					form1.useUnit.value =  json[i].useUnit;
					form1.userNo.value =  json[i].userNo;
					form1.place.value =  json[i].place;
					form1.oldPropertyNo.value =  json[i].oldPropertyNo;
					form1.oldSerialNo.value =  json[i].oldSerialNo;
					form1.differenceKind.value =  json[i].differenceKind;
					form1.caseSerialNo.value =  json[i].caseSerialNo;
					form1.userNote.value =  json[i].userNote;
					form1.place1.value =  json[i].place1;
				}
			}
		}
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" ><a href="#" onClick="return checkURL('untne013f.jsp');"><font color="red">*</font>減損單資料</a></td>	
		<td ID=t2 CLASS="tab_border1" HEIGHT="25">*減損單明細資料</td>	
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>	
	</tr>
</TABLE>
<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="HIDDEN" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
<input class="field" type="hidden" name="valuable" value="N">
<input type="hidden" name="proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" name="proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" name="proofNo" value="<%=obj.getProofNo()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE013Q",obj);%>
	<jsp:include page="untne013q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr style="display:none">
		<td class="td_form" width="15%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
	 </tr>
		<tr>
		<td class="td_form" width="15%">權屬：</td>
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
		<td class="td_form" width="15%">物品區分別：</td>
		<td colspan="3" class="td_form_white">						
			<!-- 104.12.28 問題單1267 區分別恢復為唯讀，修正回減損單與減損明細的區分別需相同 -->
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
			&nbsp;&nbsp;&nbsp;		
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo()%>">]
       	</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font> 物品編號：</td>
		<td colspan="3" class="td_form_white">
	  		<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','','NE');getNonexp();getDefault();form1.reduceDate.value='<%=obj.getReduceDate()%>';getdata();checkValue();"> 
	  		<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','6&isLookup=N')" value="..." title="物品編號輔助視窗">	
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" value="<%=obj.getPropertyNoName()%>">]
	   		&nbsp;&nbsp;&nbsp;
	   		分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getNonexp();getDefault();form1.reduceDate.value='<%=obj.getReduceDate()%>';getdata();checkValue();">
	   		批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
	   	<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		　原物品編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="20" maxlength="30" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">主要材質：</td>
		<td class="td_form_white" colspan="3">
			主要材質：
			[<input class="field_RO" type="text" name="material" size="20" value="<%=obj.getMaterial()%>">]
			其他主要材質：
			[<input class="field_RO" type="text" name="otherMaterial" size="25" maxlength="50" value="<%=obj.getOtherMaterial()%>">]
		<br>
			單位：
			[<input class="field_RO" type="text" name="propertyUnit" size="7" maxlength="7" value="<%=obj.getPropertyUnit()%>">]　 　  　　
			&nbsp;&nbsp;&nbsp;其他單位：
			[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>">] 
		<br>
	  		使用年限： 
	  		[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="7" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;調整後年限(月)：
			[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">已使用年月：</td>
		<td class="td_form_white" colspan = "3">
			[<input class="field_RO" type="text" name="useYear" size="5" maxlength="3" value="<%=obj.getUseYear()%>">]年
			[<input class="field_RO" type="text" name="useMonth" size="3" maxlength="2" value="<%=obj.getUseMonth()%>">]個月
		</td>	
	</tr>
	<tr>
		<td class="td_form" width="15%">減損：</td>
		<td class="td_form_white" colspan = "3">
			減損日期：[<input type="text" name="reduceDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;&nbsp;　　　　　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
		<br>	
			<font color="red">*</font>減損原因：
				<%=util.View.getCause("field","cause",obj.getCause(),obj.getCauseName(),"2,4","changecause();")%>
			&nbsp;&nbsp;其他說明：<input class="field" type="text" name="cause1" size="30" maxlength="120" value="<%=obj.getCause1()%>">
		<br>
			接管機關：<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>
			　　交接日期：<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		<br>
			報損報廢原因
			<input type="text" class="field" name="cause2" value="<%=obj.getCause2() %>" size="10">
			&nbsp;&nbsp;
			繳存地點
			<input type="text" class="field" name="returnPlace" value="<%=obj.getReturnPlace() %>" size="30">
		</td>	
	</tr>
	<tr>
		<td class="td_form" width="15%">廠牌型式：</td>
		<td class="td_form_white" colspan="3">		
			品名：[<input class="field_RO" type="text" name="articleName" size="20" value="<%=obj.getArticleName()%>">]　
			廠牌：[<input class="field_RO" type="text" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate()%>" >]
		<br>
			型式：[<input class="field_RO" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">]　
			牌照號碼規格/序號規格：[<input class="field_RO" type="text" name="licensePlate" size="30" maxlength="30" value="<%=obj.getLicensePlate()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form" width="15%">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]
			&nbsp;&nbsp;　　　　　
			購置日期：[<input class="field_QRO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;&nbsp;　　　　　
			取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">物品性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			&nbsp;&nbsp;&nbsp;　　　　　基金物品：
			<%=util.View._getSelectHTML_withEnterOrg("field", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">會計科目：</td>
		<td class="td_form_white" colspan="3">		
			[<input class="field_RO" type="text" name="accountingTitle" size="40" maxlength="20" value="<%=obj.getAccountingTitle()%>">]		
<!-- 		珍貴物品：
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>	 -->
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="oldBookAmount" size="7" maxlength="7" value="<%=obj.getOldBookAmount()%>">] 
			&nbsp;&nbsp;　
			單價：[<input class="field_RO type="text" name="oldBookUnit" size="13" maxlength="13" value="<%=obj.getOldBookUnit()%>">] 
			總價：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">] 
		</td>
	</tr>	
	<tr>
		<td class="td_form" width="15%">減少帳面資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>數量：<input class="field" type="text" name="adjustBookAmount" size="7" maxlength="7" value="<%=obj.getAdjustBookAmount()%>" onChange="getNewBookData();" >
			<font color="red">*</font>單價：<input class="field" type="text" name="adjustBookUnit" size="15" maxlength="15" value="<%=obj.getAdjustBookUnit()%>" onChange="getNewBookData();">
			<font color="red">*</font>總價：<input class="field" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>" onChange="getNewBookData();">
			&nbsp;&nbsp;&nbsp;帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
		</td>
	</tr>	
	<tr>
		<td class="td_form" width="15%">新帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="newBookAmount" size="7" maxlength="7" value="<%=obj.getNewBookAmount()%>" >]
			&nbsp;&nbsp;　單價：[<input class="field_RO" type="text" name="newBookUnit" size="15" maxlength="15" value="<%=obj.getNewBookUnit()%>">]
			&nbsp;&nbsp;　總價：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">]
		</td>
	</tr>	
	
	<tr>
		<td class="td_form" width="15%">移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<select class="field_RO" type="select" name="keepUnit" onchange="form1.useUnit.value = this.value">
			　　<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getKeepUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;保管人
			<select class="field_RO" type="select" name="keeper" onchange="form1.userNo.value = this.value">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getKeeper())%>
			</select>
			<br>
			使用單位
			<select class="field_RO" type="select" name="useUnit" onchange="form1.keepUnit.value = this.value">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getUseUnit())%>
			</select>	
			&nbsp;&nbsp;&nbsp;使用人
			<select class="field_RO" type="select" name="userNo" onchange="form1.keeper.value = this.value">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getUserNo())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			使用註記：
			[<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">]
			<br>
			移動日期：
			[<input class="field_RO" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>">]
			<br>
	                    存置地點
			[<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>" onchange="queryPlaceName('enterOrg','place1');">]
			[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name()%>">]
			<br>		
			存置地點說明
			[<input class="field_RO" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">]		
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form"style="display:none;">異動人員/日期：</td>
		<td class="td_form_white"style="display:none;">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
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
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountne015f();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="true" name="NewData" value=帶入最新資料 disabled="true" onClick="updateNewData();">&nbsp;|
	</span>
</td></tr>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">保管人</a></th>				
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">減少數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">減少價值</a></th>
	</thead> 
	<tbody id="listTBODY">	
	<%
	boolean primaryArray[] = {true,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,false,true};
	boolean displayArray[] = {false,false,false,false,false,true,true,true,true,true,true,false,true,true,false,false,false,false,false};
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
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[0].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
			break;				
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");
	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			changeDifferenceKind();
		case "insertError":
			setFormItem("ownership,verify,cause1,transferDate,btn_transferDate,btn_newEnterOrg","R");
			setFormItem("batchInsertBut","R");
			setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "O");
			form1.newEnterOrg.value = "";
			break;
	
		//更新時要做的動作
		case "update":
		case "updateError":
			if (form1.cause.value=="499"){
				form1.cause1.readOnly = false;
				form1.cause1.style.backgroundColor=editbg;
				form1.newEnterOrgName.value = "";
				form1.newEnterOrg.value = "";
				form1.transferDate.value = "";
				form1.transferDate.style.backgroundColor=readbg;
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
			}else if(form1.cause.value=="201" || form1.cause.value=="205" || form1.cause.value=="206"){
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
				setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "R");
				form1.transferDate.style.backgroundColor=editbg;
				form1.cause1.value="";
				form1.cause1.style.backgroundColor=readbg;
				form1.cause1.readOnly = true;
			}else{
				form1.cause1.value="";
				form1.cause1.style.backgroundColor=readbg;
				form1.cause1.readOnly = true;
				form1.newEnterOrgName.value = "";
				form1.newEnterOrg.value = "";
				form1.transferDate.value = "";
				form1.transferDate.style.backgroundColor=readbg;
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
				setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "O");
			}
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
		//取消時要執行的動作
		case "clear":
		case "clearError":
			setFormItem("batchInsertBut","O");
			break;

		//確定時要執行的動作
		case "confirm":
		case "confirmError":
			setFormItem("batchInsertBut","O");
			break;
	}
}
</script>
</body>
</html>
