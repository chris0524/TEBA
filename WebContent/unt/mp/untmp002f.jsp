<!--
程式目的：動產資料維護-批號資料
程式代號：untmp002f
程式日期：0940928
程式作者：Cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP002_1F">
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
	obj = (unt.mp.UNTMP002_1F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();	
	obj = (unt.mp.UNTMP002_1F)obj.queryOne();	
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._MP_ADD, 3);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._MP_ADD, 2);
}else if("untch001f02extend01".equals(obj.getProgID())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._MP_ADD, 2);
}else if("untch001f02extend02".equals(obj.getProgID())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._MP_ADD, 2);
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
<script type="text/javascript" src="../../js/validate.js" charset="UTF-8"></script>
<script type="text/javascript" src="../../js/function.js" charset="UTF-8"></script>
<script type="text/javascript" src="../../js/tablesoft.js" charset="UTF-8"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("valuable","N"),
	new Array("deprMethod","01"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>")
);

function checkField(){

	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untmp001f.jsp";
		} else {
			form1.action = "untch001f02.jsp";
			form1.caseNo.value = "";
		}
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		if((form1.state.value=="insert") || (form1.state.value=="insertError")){
			alertStr += checkDate(form1.originalMoveDate,"原始移動日期");
			alertStr += checkEmpty(form1.originalKeepUnit,"原始保管單位");
			alertStr += checkEmpty(form1.originalKeeper,"原始保管人");
			alertStr += checkEmpty(form1.originalUseUnit,"原始使用單位");
			alertStr += checkEmpty(form1.originalUser,"原始使用人");	
		}

		alertStr += checkDate(form1.sourceDate,"財產取得日期");
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"動產增加單-電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");		
		alertStr += checkEmpty(form1.buyDate,"購置日期");				
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		alertStr += checkEmpty(form1.originalAmount,"原始入帳－數量");
		alertStr += checkEmpty(form1.originalBV,"原始入帳－總價");
		alertStr += checkEmpty(form1.originalDeprMethod,"折舊方法");

		alertStr += checkDate(form1.buyDate,"購置日期");	
		alertStr += checkFloat(form1.originalAmount,"原始入帳－數量",8,8);
		alertStr += checkInt(form1.originalBV,"原始入帳－總價");
		alertStr += checkFloat(form1.bookAmount,"帳面數量",8,8);
		alertStr += checkInt(form1.bookValue,"帳面金額－總價");
		alertStr += checkDate(form1.approveDate,"受贈同意函日期");
		alertStr += checkEmpty(form1.sourceKind, "財產種類");
		alertStr += checkEmpty(form1.sourceDate, "財產取得日期");
		
		alertStr += checkLen(form1.notes, "備註", 250);
		//若「增加原因」為「受贈(04)」時，「受贈同意函日期、受贈同意函文號」必須有資料
		if(form1.cause.value=="04"){
			alertStr += checkEmpty(form1.approveDate,"受贈同意函日期");
			alertStr += checkEmpty(form1.approveDoc,"受贈同意函文號");
		}else{
		form1.approveDate.value = "";
		form1.approveDoc.value = "";
		}
		
		//原始入帳-數量必須大於0
		if (parseInt(form1.originalAmount.value)<=0){
			form1.originalAmount.style.backgroundColor=errorbg;
			alertStr +="原始入帳-數量必須 > 0 !\n";
		}else
			form1.originalAmount.style.backgroundColor="";

		//當財產性質非「01:公務用」時，「基金財產」不可有資料
		if(form1.propertyKind.value != "01") {
			form1.fundType.value = "";
			setFormItem("fundType","R");
		}

		//當增加原因選「其他」時，開放其他說明欄位且必須有資料
		if(form1.cause.value=="99") alertStr += checkEmpty(form1.cause1,"其他說明");
		else form1.cause1.value = "";

		//當經費來源選「其他補助款」時，開放其他說明欄位且必須有資料
		if(form1.fundsSource.value=="03") alertStr += checkEmpty(form1.fundsSource1,"經費來源-其他說明");
		else form1.fundsSource1.value = "";

		//若財產編號非503開頭者須控制此欄位為必填欄位
		var checkPropertyNo = form1.propertyNo.value;
		var originalUnit = 0;
		if(checkPropertyNo.substring(0,3) != "503"){
			if(parse(form1.originalUnit.value).length>0){
				originalUnit = parseInt(form1.originalUnit.value);
			}

/*			
			if(form1.valuable.value=="N"){
				if(form1.cause.value != '10' && originalUnit<10000 && parse(form1.fundType.value).length == 0){
					alertStr +=" 若非珍貴財產，且「增加原因」亦非「資本型租賃」，且「基金財產」無資料，須控制原始入帳－單價必須 ≧ 10,000 ! \n";
				}else if(form1.cause.value == '10' && originalUnit<0){
					alertStr +=" 若非珍貴財產，且「增加原因」為「資本型租賃」，須控制原始入帳－單價必須 > 0 ! \n";
				}else if(parse(form1.fundType.value).length > 0 && originalUnit<0){
					alertStr +=" 若非珍貴財產，且「基金財產」有資料，須控制原始入帳－單價必須 > 0 ! \n";
				}
			}else if(form1.valuable.value=="Y"){
				if(originalUnit<0){
					alertStr +=" 若為珍貴財產，須控制原始入帳－單價必須 ≧ 0 ! \n";
				}
			}
*/			
		}
	
		//「主要材質material」與「其他主要材質otherMaterial」只能其中一個有資料，且不可同時都無資料
		if ((AllTrim(form1.material).length<=0) && (AllTrim(form1.otherMaterial).length<=0) ){
			alertStr +="「主要材質」與「其他主要材質」，不可同時都無資料 ! \n";
		}
		alertStr += checkEmpty(form1.otherMaterial,"其他主要材質");	

		//「單位propertyUnit」與「其他單位otherPropertyUnit」只能其中一個有資料，且不可同時都無資料
		if ((AllTrim(form1.propertyUnit).length<=0) && (AllTrim(form1.otherPropertyUnit).length<=0) ){
			alertStr +="「單位」與「其他單位」，不可同時都無資料 ! \n";
		}
		alertStr += checkEmpty(form1.otherPropertyUnit,"其他單位");
	
		//「使用年限limitYear」與「調整後年限(月)otherLimitYear」只能其中一個有資料，且不可同時都無資料
		if ((AllTrim(form1.originalLimitYear).length<=0) && (AllTrim(form1.otherLimitYear).length<=0) ){
			alertStr +="「使用年限」與「調整後年限(月)」，不可同時都無資料 ! \n";
		}else if (AllTrim(form1.originalLimitYear).length>0){
			if (AllTrim(form1.otherLimitYear).length>0 && form1.otherLimitYear.value!="0"){
				//form1.otherLimitYear.value="";
				//alertStr +="「使用年限」與「調整後年限(月)」只能其中一個有資料 ! \n";
			}
		}else if (AllTrim(form1.otherLimitYear).length>0){
			if (AllTrim(form1.originalLimitYear).length>0){
				//alertStr +="「使用年限」與「調整後年限(月)」只能其中一個有資料 ! \n";
				//form1.otherLimitYear.value="";
			}else
			alertStr += checkInt(form1.otherLimitYear,"調整後年限(月)");	
			if (parseInt(form1.otherLimitYear.value,10)<2){
				alert('列為財產之耐用年限應大於等於2年');
				return false;
			}
		}

		//折舊計算
		changeDeprMethod()
		if (form1.state.value=="update"){
			//修改「原始入帳－數量 or 原始入帳－單價 or原始入帳－總價」
			form1.checkOriginalAmount.value = "<%=obj.getOriginalAmount()%>";
			form1.checkOriginalUnit.value = "<%=obj.getOriginalUnit()%>";
			form1.checkOriginalBV.value = "<%=obj.getOriginalBV()%>";
			form1.checkDeprMethod.value = "<%=obj.getDeprMethod()%>";
			//if (form1.checkOriginalAmount.value != form1.originalAmount.value || form1.checkOriginalUnit.value != form1.originalUnit.value || form1.checkOriginalBV.value != form1.originalBV.value ){
			if (form1.checkOriginalAmount.value != form1.originalAmount.value ){
				alertStr += checkEmpty(form1.originalKeepUnit,"原始保管單位");
				alertStr += checkEmpty(form1.originalKeeper,"原始保管人");
				alertStr += checkEmpty(form1.originalUseUnit,"原始使用單位");
				alertStr += checkEmpty(form1.originalUser,"原始使用人");	
			}
		}
	}
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();	
}

function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,lotNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.lotNo.value=lotNo;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untmp001f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.propertyNo,"財產編號");
				alertStr += checkEmpty(form1.serialNoS,"財產分號");			
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"財產編號");
			alertStr += checkEmpty(form1.serialNoS,"財產分號");			
		}
		
			surl=surl+"?initDtl=Y";

			if (form1.caseNo.value!="" && form1.ownership.value!="" && form1.enterOrg.value!="" && form1.lotNo.value!="") 
				form1.state.value = "queryOne";		
		
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		
		if (surl=="../ch/untch001f02.jsp"){
			if('<%=obj.getIsAddProof()%>' != 'true'){
				form1.state.value="queryAll";
			}
		}
		
		form1.action = surl;
		form1.currentPage.value=form1.mainPage.value;		
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		beforeSubmit();
		form1.submit();
	}
}

function changeSelect(){
	//當增加原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "99") {
		form1.cause1.readOnly = false;
		form1.cause1.disabled = false;
	} else { 
		form1.cause1.value=""; 
		form1.cause1.readOnly = true; 
		form1.cause1.disabled = true; 
	}

	//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
	if(form1.cause.value == "04"){
		form1.approveDate.readOnly = false;
		form1.btn_approveDate.disabled=false;
		form1.approveDoc.readOnly = false;
	}else{
		form1.approveDate.value="";
		form1.approveDoc.value="";
		form1.approveDate.readOnly = true;
		form1.btn_approveDate.disabled=true;
		form1.approveDoc.readOnly = true;
	}
	
	//財產性質為「01:公務用」時，「基金財產」可選也可以不選。
	if(form1.propertyKind.value == "01") {
		document.all.fundType.disabled=false;
	}else{
		document.all.fundType.disabled=true;
		form1.fundType.value="";
	}
}

function init(){
	if (isObj(document.all.item("picture")) && (parse(document.all.item("picture").value)).length>0) setFormItem("btn_pictureDownload","O");
	setFormItem("verify", "R");
	var caseNo = parse(form1.caseNo.value);
	if (caseNo.length<=0){
		setFormItem("insert", "R");
	}else{
		setFormItem("insert", "O");
	}	
	//若是查出的「批號資料」之「資料狀態」若為「已減損」，均不允許修改、刪除該筆批號資料
	if (<%=obj.getDataState().equals("2")%>){
		setFormItem("update,delete", "R");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete","R");
	}
	

	setDisplayItem("spanQueryAll,spanInsert,spanNextInsert","H");
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}else if(form1.progID.value == 'untch001f02extend02'){
		setDisplayItem("spanDelete","H");		
	}
	$("#tr_cause").hide();
	setDisplayItem("spanDelete","H");		
}

function changeOriginalAmount(){
	var originalUnit = 0;
	if(parse(form1.originalUnit.value).length>0){
		originalUnit = parseInt(form1.originalUnit.value);
	}
	var originalAmount = parseInt(form1.originalAmount.value);
	if(parse(form1.originalAmount.value).length>0){
		form1.bookAmount.value = parseInt(form1.originalAmount.value);
	}else{
		form1.bookAmount.value = "";
	}
	if(AllTrim(form1.originalUnit).length>0){
		if(parse(form1.originalUnit.value).length>0 && parse(form1.originalAmount.value).length>0){
			var originalBV = originalUnit*originalAmount;
		}else if(parse(form1.originalUnit.value).length<=0 && parse(form1.originalBV.value).length>0){
			var originalBV = form1.originalBV.value;
		}else{
			var originalBV = "";
		}
		form1.originalBV.value = originalBV;
	}
	form1.bookValue.value = form1.originalBV.value;
}

function changeOriginalUnit(){
	var checkPropertyNo = form1.propertyNo.value;
	var originalUnit = 0;
	if(parse(form1.originalUnit.value).length>0){
		originalUnit = parseInt(form1.originalUnit.value);
	}
	var originalAmount = parseInt(form1.originalAmount.value);
	var originalBV ="";
	if(parse(form1.originalUnit.value).length>0 && parse(form1.originalAmount.value).length>0){
		originalBV = originalUnit*originalAmount;
	}else if(parse(form1.originalUnit.value).length<=0 && parse(form1.originalBV.value).length>0){
		originalBV = form1.originalBV.value;
	}

	form1.originalBV.value = originalBV;
	if(parse(form1.originalAmount.value).length>0){
		form1.bookAmount.value = parseInt(form1.originalAmount.value);
	}else{
		form1.bookAmount.value = "";
	}
	form1.bookValue.value = form1.originalBV.value;
}

function changeOriginalBV(){
	if(AllTrim(form1.originalUnit).length>0){
		changeOriginalUnit();
	}else{
		form1.bookValue.value = form1.originalBV.value;
	}
}

function changeKeep1(){
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}

function changeKeep2(){
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	//getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}

function changeDate(){
	var limitYear = form1.originalLimitYear.value;
	var otherLimitYear = form1.otherLimitYear.value;
	if(AllTrim(form1.originalLimitYear).length>0){
		form1.useEndYM.value = getDateAdd("m", parseInt(limitYear)*12-1,form1.buyDate).substring(0,5);	
		form1.permitReduceDate.value = getDateAdd("y",parseInt(limitYear),form1.buyDate);
	}else if(AllTrim(form1.otherLimitYear).length>0){
		form1.useEndYM.value = getDateAdd("m", parseInt(otherLimitYear)*12-1,form1.buyDate).substring(0,5);	
		form1.permitReduceDate.value = getDateAdd("y",parseInt(otherLimitYear),form1.buyDate);
	}else{
		form1.useEndYM.value = "";
		form1.permitReduceDate.value = "";
	}
	
	
	if(AllTrim(form1.originalLimitYear).length>0){
		setFormItem("otherLimitYear", "R")
		form1.otherLimitYear.value="";
		form1.otherLimitYear.style.backgroundColor="";
	}else setFormItem("otherLimitYear", "O")
	
	/* 問題單 1044 無論如何都要可以修改
	if(AllTrim(form1.material).length>0){
		setFormItem("otherMaterial", "R")
		form1.otherMaterial.value="";
		form1.otherMaterial.style.backgroundColor="";
	}else setFormItem("otherMaterial", "O")
	if(AllTrim(form1.propertyUnit).length>0){
		setFormItem("otherPropertyUnit", "R")
		form1.otherPropertyUnit.value="";
		form1.otherPropertyUnit.style.backgroundColor="";
	}else setFormItem("otherPropertyUnit", "O")
	*/
}

function changeDeprMethod(){
}

function changeOriginal(){
	document.all.btn_originalMoveDate.disabled=false;
	form1.originalMoveDate.readOnly = false;
	document.all.originalKeepUnit.disabled=false;
	document.all.originalKeeper.disabled=false;
	document.all.originalUseUnit.disabled=false;
	document.all.originalUser.disabled=false;
	document.all.originalPlace.disabled=false;
	form1.originalPlace.readOnly = false;
	form1.originalMoveDate.style.backgroundColor="";
	form1.originalKeepUnit.style.backgroundColor="";
	form1.originalKeeper.style.backgroundColor="";
	form1.originalUseUnit.style.backgroundColor="";
	form1.originalUser.style.backgroundColor="";
	form1.originalPlace.style.backgroundColor="";
}

function clearStoreNo(){
	form1.storeNo.value="";
	form1.storeNoName.value="";
}

function changeFundsSource(){
	//當經費來源選「其他補助款」時，開放其他說明欄位
	if(form1.fundsSource.value == "03") form1.fundsSource1.readOnly = false;
	else { form1.fundsSource1.value=""; form1.fundsSource1.readOnly = true; }
}

//判斷若取得日期為空字串,將購買日期設為預設值
function buyTOsource(){
	var sourceValue = form1.buyDate.value;
	if (form1.sourceDate.value == 0){
		form1.sourceDate.value = sourceValue;
	}
}

//輸入「購置日期」後，「原始移動日期」預設和「購置日期」一樣
function equalOriginalMoveDate(){
		form1.originalMoveDate.value = form1.buyDate.value;
}


//*********************************************
//函數功能：財產編號輔助輸入,若preWord除了前置詞若尚有isLookup=Y的參則傳回SYSPK_PropertyCode的所有欄位值,否則只傳回No及Name
//參　　數：popPropertyNo財產編號欄位; popPropertyName財產名稱欄位; preWord 財產編號前置詞
//使用方式:(1)('propertyNo','propertyName','1')
//(2) ('propertyNo','propertyName','1,2,3&isLookup=Y')
//*********************************************
function getProperty(popPropertyNo,popPropertyName,preWord,isCls){
	var alertStr = "";
	var arrPreWord;
	var isLookup = false;
	var q_enterOrg = "";
	if (parse(preWord).length>0) arrPreWord = preWord.split("&");
	if (arrPreWord.length>1 && arrPreWord[1]=="isLookup=Y") isLookup = true;
	if (parse(document.all.item(popPropertyNo).value).length>0) {
		
		if (isObj(document.all.item(popPropertyNo))) {
			var obj = document.all.item(popPropertyNo);
			if (obj.className=="field_Q") {
				if ((isObj(document.all.item("q_enterOrg"))) && (document.all.item("q_enterOrg").value!="")) {
					q_enterOrg = document.all.item("q_enterOrg").value;
				}
			} else {
				if ((isObj(document.all.item("enterOrg"))) && (document.all.item("enterOrg").value!="")) {
					q_enterOrg = document.all.item("enterOrg").value;
				}			
			}
		}
		if (q_enterOrg.length>0) q_enterOrg = "&q_enterOrg="+q_enterOrg;
		
		var i = 0;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;	
		var randomnumber=Math.floor(Math.random()*10000000000000)+new Date().getTime();		
		if(xmlDoc.load("../../home/xmlProperty.jsp?q_propertyNo="+document.all.item(popPropertyNo).value+"&preWord="+preWord+q_enterOrg+"&abc="+randomnumber)) {
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				if (isObj(document.all.item(popPropertyName))) {
					document.all.item(popPropertyName).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");	
				}						
				if (isLookup) {
					if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyType");
					if (isObj(document.all.item("propertyUnit"))) document.all.item("propertyUnit").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
					if (isObj(document.all.item("material"))) document.all.item("material").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("material");
					if (isObj(document.all.item("limitYear"))) document.all.item("limitYear").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear");
					if (isObj(document.all.item("otherLimitYear"))){
						if(xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear")==""){
							document.all.item("limitYear").value="";
							document.all.item("otherLimitYear").readOnly=false;
						}else{
							if (parseInt(xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear"))>=0) {
									document.all.item("otherLimitYear").value="";
									document.all.item("otherLimitYear").readOnly=true;
							} else {
								document.all.item("limitYear").value="";
								document.all.item("otherLimitYear").readOnly=false;
							}
						}
					}
				}	
			}
			if (isCls!="N") {
				if (i==0) {
					if (isObj(document.all.item(popPropertyNo))) document.all.item(popPropertyNo).value="";	
					if (isObj(document.all.item(popPropertyName))) document.all.item(popPropertyName).value="";	
					if (isLookup) {
						if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value="";
						if (isObj(document.all.item("propertyUnit"))) document.all.item("propertyUnit").value="";
						if (isObj(document.all.item("material"))) document.all.item("material").value="";
						if (isObj(document.all.item("limitYear"))) document.all.item("limitYear").value="";
						if (isObj(document.all.item("otherLimitYear"))) document.all.item("otherLimitYear").readOnly=false;
					}
				}
			}
		}
	} else { 
		if (isCls!="N") {	
			document.all.item(popPropertyNo).value="";
			document.all.item(popPropertyName).value="";
		}
	}
	if (arrPreWord.length>2 && arrPreWord[2]!="") eval(arrPreWord[2].substring(arrPreWord[2].indexOf("=")+1));
}

//function popStore(queryValue,popStoreNo){
//	var prop="";
//	prop=prop+"width=630,height=470,";
//	prop=prop+"top=150,";
//	prop=prop+"left=300,";
//	prop=prop+"scrollbars=no";
//	returnWindow=window.open("../../home/popStoreNo.jsp?queryValue="+queryValue+"&popStoreNo="+popStoreNo,"",prop);
//}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input type="hidden" name="deprAmount" value="<%=obj.getDeprAmount()%>">
<input type="hidden" name="monthDepr" value="<%=obj.getMonthDepr()%>">
<input type="hidden" name="accumDepr" value="<%=obj.getAccumDepr()%>">
<input type="hidden" name="checkOriginalAmount" value="">
<input type="hidden" name="checkOriginalUnit" value="">
<input type="hidden" name="checkOriginalBV" value="">
<input type="hidden" name="checkDeprMethod" value="">
<input type="hidden" name="addProofCaseNo" value="">
<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<select class="field_QRO" type="select" name="ownershipName">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>			
			<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
			&nbsp;&nbsp;&nbsp;&nbsp;電腦單號：
			[<input class="field_QRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產區分別：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
			&nbsp;&nbsp;&nbsp;		
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo()%>">]
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
		<td class="td_form"><font color="red">*</font>財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind" onChange="changeSelect();">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getPropertyKind())%>
			</select>
			　基金財產：
			<select class="field" type="select" name="fundType" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD'", obj.getFundType())%>
			</select>
			　<font color="red">*</font>珍貴財產：
			<select class="field" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
						<!--%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNo(),"3,4,5&isLookup=Y&js=changeDate()")%-->
			<input class="field_P" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','3,4,5&isLookup=Y&js=changeDate()');">
			<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','3,4,5&isLookup=Y&js=changeDate()')" value="..." title="財產編號輔助視窗">
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			<br>
			別名：
			<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			<br>
			分號：
			起[<input class="field_PRO" type="text" name="serialNoS" size="7" maxlength="7" value="<%=obj.getSerialNoS()%>">]&nbsp;~&nbsp;
			訖[<input class="field_PRO" type="text" name="serialNoE" size="7" maxlength="7" value="<%=obj.getSerialNoE()%>">]
			批號：[<input class="field_PRO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
			<br>
			原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]&nbsp;　　　　　
			原財產分號：起[<input class="field_RO" type="text" name="oldSerialNoS" size="7" maxlength="7" value="<%=obj.getOldSerialNoS()%>">]&nbsp;~&nbsp;
					         訖[<input class="field_RO" type="text" name="oldSerialNoE" size="7" maxlength="7" value="<%=obj.getOldSerialNoE()%>">]
		</td>
	</tr>
	
	<tr>
		<td class="td_form"><font color="red">*</font>購置日期：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>" onChange="changeDate();changeDeprMethod();buyTOsource();">
			<input class="field" type="button" name="btn_buyDate" onclick="popCalndar('buyDate');changeDate();buyTOsource();equalOriginalMoveDate();" value="..." title="萬年曆輔助視窗">			
		</td>
	</tr>
	
	<tr id="tr_cause">
		<td class="td_form">增加原因：</td>
		<td class="td_form_white" colspan="3">
			增加原因：
			<select class="field" type="select" name="cause" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA'", obj.getCause())%>
			</select>
		　　其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>" readonly="true" disabled="true">
		<br>
		受贈同意函日期：
			<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
		　受贈同意函文號：
			<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
		</td>
	</tr>
	
	<tr>
		<td class="td_form"><font color="red">*</font>財產來源：</td>
		<td class="td_form_white" colspan="4">
			<select class="field" type="select" name="sourceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKD' ", obj.getSourceKind())%>
			</select>
			<font color="red">*</font>&nbsp;&nbsp;&nbsp;財產取得日期：
			<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　財產取得文號：
            <input class="field" type="text" name="sourceDoc" size="15" maxlength="20" value="<%=obj.getSourceDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
		<font color="red">*</font>原始入帳數量：
			<input class="field_RO" type="text" name="originalAmount" size="15" maxlength="7" value="<%=obj.getOriginalAmount()%>" onChange="changeOriginalAmount();changeOriginal();changeDeprMethod();">
		<br>
			原始入帳單價：
			<input class="field_RO" type="text" name="originalUnit" size="15" maxlength="13" value="<%=obj.getOriginalUnit()%>" onChange="changeOriginalUnit();changeOriginal();changeDeprMethod();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>原始入帳總價：
	  		<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" onChange="changeOriginalBV();changeOriginal();changeDeprMethod();">
	  	<br>
			原始入帳摘要：
			<input class="field_RO" type="text" name="originalNote" size="30" maxlength="20" value="<%=obj.getOriginalNote()%>">
		<br>
		帳面數量：
			[<input class="field_RO" type="text" name="bookAmount" size="15" maxlength="7" value="<%=obj.getBookAmount()%>" onChange="changeArea();">]
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原值：
			[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="13" value="<%=obj.getBookValue()%>" onChange="changeArea();">]
			&nbsp;&nbsp;&nbsp;
			帳面價值(淨值)：[<input class="field_RO" type="text" name="netValue" size="15" maxlength="15" value="<%=obj.getNetValue()%>" onChange="changeArea();">]
		</td>
	</tr>
	
		
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3">
			主要材質：
			[<input class="field_RO" type="text" name="material" size="25" value="<%=obj.getMaterial()%>">]
			&nbsp;　　其他主要材質：
			<input class="field" type="text" name="otherMaterial" size="25" maxlength="25" value="<%=obj.getOtherMaterial()%>">
		<br>
			單位：
			[<input class="field_RO" type="text" name="propertyUnit" size="25" maxlength="25" value="<%=obj.getPropertyUnit()%>">]
			　其他單位：
			<input class="field" type="text" name="otherPropertyUnit" size="25" maxlength="25" value="<%=obj.getOtherPropertyUnit()%>">
		<br>
	  		使用年限： 
        	[<input class="field_RO" type="text" name="originalLimitYear" size="8" maxlength="3" value="<%=obj.getOriginalLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;調整後年限(月)：
        	<input class="field" type="text" name="otherLimitYear" size="8" maxlength="3" value="<%=obj.getOtherLimitYear()%>" onChange="changeDate();">
		</td>			
	</tr>
	<tr>
		<td class="td_form">廠牌型式：</td>
		<td class="td_form_white" colspan="3">		
			品名：
				<input name="articleName" type="text" class="field" value="<%=obj.getArticleName()%>" size="20" maxlength="10">　
			&nbsp;&nbsp;　&nbsp;　
			型式：
				<input name="specification" type="text" class="field" value="<%=obj.getSpecification()%>" size="40" maxlength="40">　
		<br>
			廠牌：
				<input name="nameplate" type="text" class="field" value="<%=obj.getNameplate()%>" size="40" maxlength="40">　
			&nbsp;&nbsp;　&nbsp;　廠商：
				<input class="field" type="hidden" name="storeNo" size="5" maxlength="10" value="<%=obj.getStoreNo()%>">
				[<input class="field_RO" type="text" name="storeNoName" size="20" maxlength="50" value="<%=obj.getStoreNoName()%>">]
				<input class="field" type="button" name="btn_storeNo" onclick="popStore('storeNo','storeNoName')" value="..." title="廠商輔助視窗"> 
				<input class="field" type="button" name="clear_storeNo" onclick="clearStoreNo();" value="清除">
		</td>
	</tr>	
	<tr>
		<td class="td_form">經費來源：</td>
		<td class="td_form_white">
			經費來源：
			<select class="field" type="select" name="fundsSource" onChange="changeFundsSource();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
			</select>
	    <br>
		　其他說明：
			<input class="field" type="text" name="fundsSource1" size="20" maxlength="20" value="<%=obj.getFundsSource1()%>" readonly="true">&nbsp;&nbsp;
		      補助金額：
			<input name="grantValue" type="text" class="field" value="<%=obj.getGrantValue()%>" size="15" maxlength="15">
		　會計科目：
			<input class="field" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">原始折舊資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>折舊方法
			<select name="originalDeprMethod" class="field" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getOriginalDeprMethod())%>
			</select>
			<input name="deprMethod" class="field" type="hidden" value="<%=obj.getDeprMethod()%>">
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="cb_originalBuildFeeCB" size="10" maxlength="10"  <%=("Y".equals(obj.getOriginalBuildFeeCB())? "checked" : "")%>>
			<input class="field" type="hidden" name="originalBuildFeeCB" size="10" maxlength="10" value="<%=obj.getOriginalBuildFeeCB()%>">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="cb_originalDeprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getOriginalDeprUnitCB())?"checked":"")%>>
			<input class="field" type="hidden" name="originalDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOriginalDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別
			<select class="field" type="select" name="originalDeprPark">
			　　<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field" type="select" name="originalDeprUnit">
			　　<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field" type="select" name="originalDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field" type="select" name="originalDeprAccounts">
			　　<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprAccounts())%>
			</select>
			<br>
			殘值
			<input class="field" type="text" name="originalScrapValue" size="10" maxlength="10" value="<%=obj.getOriginalScrapValue()%>">
			&nbsp;&nbsp;
			應攤提折舊總額
			<input class="field" type="text" name="originalDeprAmount" size="20" maxlength="15" value="<%=obj.getOriginalDeprAmount()%>" onChange="changeArea();">
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="originalAccumDepr" size="20" maxlength="15" value="<%=obj.getOriginalAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			<input class="field" type="text" name="originalApportionMonth" size="10" maxlength="3" value="<%=obj.getOriginalApportionMonth()%>" onChange="changeArea();">
			月提折舊金額
			[<input class="field_RO" type="text" name="originalMonthDepr" size="10" value="<%=obj.getOriginalMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="originalMonthDepr1" size="10" value="<%=obj.getOriginalMonthDepr1()%>">]
			攤提年限截止年月
			[<input class="field_RO" type="text" name="originalApportionEndYM" size="10" value="<%=obj.getOriginalApportionEndYM()%>">]
		</td>
	</tr>
<!-- 	問題單1781 隱藏上傳附件的功能 -->
<!-- 	<tr> -->
<!-- 	  <td class="td_form">附屬設備檔：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","picture",obj.getPicture())%></td> --%>
<!--       </tr>	 -->
	<tr>
		<td class="td_form" >代管資產：</td>
		<td class="td_form_white" colspan="3">
			原始總價：
			<input class="field" type="text" name="escrowOriValue" size="20" value="<%=obj.getEscrowOriValue() %>">
			&nbsp;&nbsp;&nbsp;&nbsp;			
			代管日前累計折舊：
			<input class="field" type="text" name="escrowOriAccumDepr" size="20" value="<%=obj.getEscrowOriAccumDepr() %>">
		</td>
	</tr>	
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white"><textarea class="field"  name="notes" id="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
	  <td class="td_form" style="display:none">異動人員/日期：</td>
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
	
	<jsp:include page="../../home/toolbar.jsp" />
	
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer2" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame2"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
</div>
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">分號起-訖</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<%
		if("untch001f02extend02".equals(obj.getProgID())){

		}else{	
			out.write("<th class=\"listTH\"><a class=\"text_link_w\" onclick=\"return sortTable('listTBODY',5,false);\" href=\"#\">原因</a></th>");
		}
		%>		
		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">總價</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	if("untch001f02extend02".equals(obj.getProgID())){
		boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false,false,false};
		boolean displayArray[] = {false,false,false,false,false,false,true,true,true,true,true,true,true};
		out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	}else{
		boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false,false,false,false};
		boolean displayArray[] = {false,false,false,false,false,false,true,true,true,true,true,true,true,true};
		out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	}
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
		//刪除之前多出現一道確認訊息
       	case "delete":
		if(!confirm("刪除此筆動產批號，將一併刪除其關聯的批號明細、批號附屬設備、批號明細附屬設備!"))
			return false;
			hasBeenConfirmed = true;
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[2].checked==true) {} 
			else form1.querySelect[1].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
				
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
			form1.storeNo.value="";
			form1.storeNoName.value="";
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				setFormItem("cause1","R");	
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate","R");
			}	
			
			//當經費來源不是「其他補助款」時，鎖住其他說明欄位
			if(form1.fundsSource.value != "03"){
				setFormItem("fundsSource1","R");	
			}	
			
			// 新增時，預設基金財產為不可選
			setFormItem("fundType","R");
			
			// 新增時此區塊的所有內容才顯示
			isDisplay.style.display="";

			break;
		case "insertError":
			form1.storeNo.value="";
			form1.storeNoName.value="";
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				setFormItem("cause1","R");	
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate","R");
			}	
			
			//當經費來源不是「其他補助款」時，鎖住其他說明欄位
			if(form1.fundsSource.value != "03"){
				setFormItem("fundsSource1","R");	
			}	
			
			break;
		//更新時要做的動作
		case "update":
			if(AllTrim(form1.originalLimitYear).length>0) setFormItem("otherLimitYear", "R")
			else setFormItem("otherLimitYear", "O")
			// 問題單1044 其他材質與單位 無論如何都要可以修改
			//if(AllTrim(form1.material).length>0) setFormItem("otherMaterial", "R")
			//else setFormItem("otherMaterial", "O")
			//if(AllTrim(form1.propertyUnit).length>0) setFormItem("otherPropertyUnit", "R")
			//else setFormItem("otherPropertyUnit", "O")	
			if(form1.verify.value=="Y"){				
				//setFormItem("otherPropertyUnit,otherLimitYear,cause,cause1,btn_buyDate,buyDate,propertyKind,fundType,valuable,originalAmount,originalUnit,originalBV,originalNote,nameplate,specification,sourceKind,btn_sourceDate,sourceDate,sourceDoc,deprMethod","R");
				setFormItem("btn_buyDate,buyDate,propertyKind,fundType,valuable,originalAmount,originalUnit,originalBV,originalNote,deprMethod","R");				
			}
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				setFormItem("cause1","R");	
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate","R");
			}	

			//當經費來源不是「其他補助款」時，鎖住其他說明欄位
			if(form1.fundsSource.value != "03"){
				setFormItem("fundsSource1","R");	
			}	
			
			// 更新時，當財產性質非「01:公務用」時，不可選 & 不可有資料
			if(form1.propertyKind.value != "01") {
				form1.fundType.value="";
				setFormItem("fundType","R");
			}
			
			//503類,已有單價時!!不得在修改
			//if(form1.propertyNo.value.substring(0,3) == "503"){
			//		setFormItem("originalUnit", "R");
			//}
			break;
			
		case "updateError":
			if(AllTrim(form1.originalLimitYear).length>0) setFormItem("otherLimitYear", "R")
			else setFormItem("otherLimitYear", "O")
			//if(AllTrim(form1.material).length>0) setFormItem("otherMaterial", "R")
			//else setFormItem("otherMaterial", "O")
			//if(AllTrim(form1.propertyUnit).length>0) setFormItem("otherPropertyUnit", "R")
			//else setFormItem("otherPropertyUnit", "O")			
			if(form1.verify.value=="Y"){				
				setFormItem("btn_buyDate,buyDate,propertyKind,fundType,valuable,originalAmount,originalUnit,originalBV,originalNote,deprMethod","R");
			}
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				setFormItem("cause1","R");	
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate","R");
			}		

			//當經費來源不是「其他補助款」時，鎖住其他說明欄位
			if(form1.fundsSource.value != "03"){
				setFormItem("fundsSource1","R");	
			}	
			
			break;
		case "clear":
			init();
			break;
	}
}
</script>
</body>
</html>

