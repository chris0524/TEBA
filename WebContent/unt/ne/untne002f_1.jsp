<!--
程式目的：非消耗品主檔資料維護－批號資料
程式代號：untne002f
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE002F_1">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%

obj.setRoleid(user.getRoleid());
try {
	if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
	if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
	if ("queryAll".equals(obj.getState())) {
		if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	}else if ("queryOne".equals(obj.getState())) {
		obj = (unt.ne.UNTNE002F_1)obj.queryOne();	
	}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
		obj.insert();
		if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
	}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
		obj.update();
		if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
	}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
		obj.delete();
		if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
	}
	if ("true".equals(obj.getQueryAllFlag())){
		objList = obj.queryAll();
		if (!objList.isEmpty()) {
			if("".equals(obj.getSerialNo()) && "".equals(obj.getLotNo())){	
				obj.setEnterOrg(((String[])objList.get(0))[14]);
				obj.setOwnership(((String[])objList.get(0))[15]);
				obj.setCaseNo(((String[])objList.get(0))[17]);
				obj.setDifferenceKind(((String[])objList.get(0))[16]);
				obj.setPropertyNo(((String[])objList.get(0))[3]);
				obj.setSerialNo(((String[])objList.get(0))[13]);
				obj.setSerialNoS(((String[])objList.get(0))[18]);
				obj.setSerialNoE(((String[])objList.get(0))[19]);
				obj.setLotNo(((String[])objList.get(0))[13]);	
				obj.setEnterOrgName(((String[])objList.get(0))[11]);
			}
			obj.queryOne();
		}
	}
} catch(Exception e) {
	
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("valuable","N"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("propertyKind","<%=uctls._getDefaultValue(user.getOrganID(), "propertyKind")%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		
			form1.action = "untne002f_1.jsp";
			form1.caseNo.value = "";
		
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		if(form1.state.value=="insert" || form1.state.value=="insertError"){
			//alertStr += checkDate(form1.originalMoveDate,"原始移動日期");
			//alertStr += checkEmpty(form1.originalKeepUnit,"原始保管單位");
			//alertStr += checkEmpty(form1.originalKeeper,"原始保管人");
			//alertStr += checkEmpty(form1.originalUseUnit,"原始使用單位");
			//alertStr += checkEmpty(form1.originalUser,"原始使用人");	
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		//alertStr += checkEmpty(form1.ownership,"權屬");
		//alertStr += checkEmpty(form1.cause,"增加原因");
		alertStr += checkDate(form1.approveDate,"受贈同意函日期");
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkEmpty(form1.buyDate,"購置日期");
		alertStr += checkDate(form1.buyDate,"購置日期");
		alertStr += checkEmpty(form1.propertyKind,"物品性質");
		//alertStr += checkEmpty(form1.valuable,"珍貴物品註記");
		alertStr += checkEmpty(form1.originalAmount,"原始入帳－數量");
		alertStr += checkInt(form1.originalAmount,"原始入帳－數量");
		if(parse(form1.originalUnit.value).length>0){
			alertStr += checkInt(form1.originalUnit,"原始入帳－單價");
		}
		alertStr += checkEmpty(form1.originalUnit,"原始入帳－單價");
		alertStr += checkInt(form1.originalUnit,"原始入帳－單價");
		//alertStr += checkEmpty(form1.originalBV,"原始入帳－總價");
		//alertStr += checkInt(form1.originalBV,"原始入帳－總價");
		alertStr += checkInt(form1.bookAmount,"帳面數量");
		alertStr += checkInt(form1.bookValue,"帳面金額－總價");
		alertStr += checkInt(form1.grantValue,"補助金額");
		alertStr += checkDate(form1.sourceDate,"物品來源－取得日期");
		alertStr += checkLen(form1.notes, "備註", 250);

		alertStr += checkEmpty(form1.sourceKind, "物品來源");
		alertStr += checkEmpty(form1.sourceDate, "物品取得日期");
		
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

		// 0≦「原始入帳－單價」＜10,000
		var originalUnit = parseInt(form1.originalUnit.value);
			//alertStr += checkEmpty(form1.originalUnit,"原始入帳-單價");
				if(originalUnit>=10000){
					alertStr +=" 原始入帳－單價必須 < 10,000 ! \n";
				}
				if(originalUnit<0){
					alertStr +=" 原始入帳－單價必須 ≧ 0 ! \n";
				}

		//「主要材質material」與「其他主要材質otherMaterial」只能其中一個有資料，且不可同時都無資料
		if ((AllTrim(form1.material).length<=0) && (AllTrim(form1.otherMaterial).length<=0) ){
			alertStr +="「主要材質」與「其他主要材質」，不可同時都無資料 ! \n";
		/*}else if (AllTrim(form1.material).length>0){
			if (AllTrim(form1.otherMaterial).length>0){
				form1.otherMaterial.value="";
				alertStr +="「主要材質」與「其他主要材質」只能其中一個有資料 ! \n";
			}
		}else if (AllTrim(form1.otherMaterial).length>0){
			if (AllTrim(form1.material).length>0){
				alertStr +="「主要材質」與「其他主要材質」只能其中一個有資料 ! \n";
				form1.otherMaterial.value="";
			}*/
		}

		//「單位propertyUnit」與「其他單位otherPropertyUnit」只能其中一個有資料，且不可同時都無資料
		if ((AllTrim(form1.propertyUnit).length<=0) && (AllTrim(form1.otherPropertyUnit).length<=0) ){
			alertStr +="「單位」與「其他單位」，不可同時都無資料 ! \n";
			/*}else if (AllTrim(form1.propertyUnit).length>0){
			if (AllTrim(form1.otherPropertyUnit).length>0){
				form1.otherPropertyUnit.value="";
				alertStr +="「單位」與「其他單位」只能其中一個有資料 ! \n";
			}
		}else if (AllTrim(form1.otherPropertyUnit).length>0){
			if (AllTrim(form1.propertyUnit).length>0){
				alertStr +="「單位」與「其他單位」只能其中一個有資料 ! \n";
				form1.otherPropertyUnit.value="";
			}*/
		}

		//使用年限limitYear」與「調整後年限(月)otherLimitYear」只能其中一個有資料，且不可同時都無資料
		if ((AllTrim(form1.limitYear).length<=0) && (AllTrim(form1.otherLimitYear).length<=0) ){
			alertStr +="「使用年限」與「調整後年限(月)」，不可同時都無資料 ! \n";
			/*}else if (AllTrim(form1.limitYear).length>0){
			if (AllTrim(form1.otherLimitYear).length>0 && form1.otherLimitYear.value!="0"){
				form1.otherLimitYear.value="";
				alertStr +="「使用年限」與「調整後年限(月)」只能其中一個有資料 ! \n";
			}
		}else if (AllTrim(form1.otherLimitYear).length>0){
			if (AllTrim(form1.limitYear).length>0){
				alertStr +="「使用年限」與「調整後年限(月)」只能其中一個有資料 ! \n";
				form1.otherLimitYear.value="";
			}else
			alertStr += checkInt(form1.otherLimitYear,"調整後年限(月)");	*/
		}
		
		if (form1.state.value=="update" || form1.state.value=="updateError"){
			//修改「原始入帳－數量 or 原始入帳－單價 or原始入帳－總價」
			form1.checkOriginalAmount.value = "<%=obj.getOriginalAmount()%>";
			form1.checkOriginalUnit.value = "<%=obj.getOriginalUnit()%>";
			form1.checkOriginalBV.value = "<%=obj.getOriginalBV()%>";
			if (form1.checkOriginalAmount.value != form1.originalAmount.value || form1.checkOriginalUnit.value != form1.originalUnit.value || form1.checkOriginalBV.value != form1.originalBV.value ){
				//alertStr += checkEmpty(form1.originalKeepUnit,"原始保管單位");
				//alertStr += checkEmpty(form1.originalKeeper,"原始保管人");
				//alertStr += checkEmpty(form1.originalUseUnit,"原始使用單位");
				//alertStr += checkEmpty(form1.originalUser,"原始使用人");	
			}
		}
		
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(propertyNo,lotNo,enterOrg,ownership,differenceKind){
	form1.propertyNo.value=propertyNo;
	form1.lotNo.value=lotNo;
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";

	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
		return false;
	}else{
		
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"物品編號");
			alertStr += checkEmpty(form1.serialNoS,"物品分號");			
		
		
			surl=surl+"?initDtl=Y";
		
			if (form1.caseNo.value!="" && form1.ownership.value!="" && form1.enterOrg.value!="" && form1.lotNo.value!=""){ 
				form1.state.value = "queryOne";		
			}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		//alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}

		
}

function changeSelect(){
	//當增加原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "99") form1.cause1.readOnly = false;
	else { form1.cause1.value=""; form1.cause1.readOnly = true; }

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
	
	//物品性質為「01:公務用」時，須控制「基金物品」
	if(form1.propertyKind.value == "01") document.all.fundType.disabled=false;
	else{
		document.all.fundType.disabled=true;
		form1.fundType.value="";
	}
}

function init(){
    setFormItem("verify", "R")
	
	var caseNo = parse(form1.caseNo.value);
	if (caseNo.length<=0){
		setFormItem("insert", "R")
	}else{
		setFormItem("insert", "O")
	}
	<!-- 查出的「增加單資料」若「已月結」或「已入帳」，不允許新增、刪除該筆增加單的批號資料 -->
	if ( <%=obj.getCheckVerify().equals("Y")%>){
		setFormItem("insert,delete", "R")
	}
	<!-- 若是查出的「批號資料」之「資料狀態」若為「已減損」，均不允許修改、刪除該筆批號資料 -->
	if (<%=obj.getDataState().equals("2")%>){
		setFormItem("update,delete", "R")
	}
	<!-- 查出的「批號資料」若「已入帳」或「已月結」，均不允許刪除批號資料 -->
	if (<%=obj.getVerify().equals("Y")%>){
		setFormItem("insert,delete", "R")
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {	
		setFormItem("insert,update,delete,clear,confirm", "R")
	}
	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {
		$('#containNonexp').attr('disabled',true);
	}else if(<%=obj.getVerify().equals("Y")%> ){
		$('#containNonexp').attr('disabled',true);
	}else{
		$('#containNonexp').attr('disabled',false);
	}
	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,span_btn01,span_btn02,span_btn03,spanNextInsert","H");
	
}

function changeOriginalAmount(){
	var originalUnit = parseInt(form1.originalUnit.value);
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
	var originalUnit = parseInt(form1.originalUnit.value);
	var originalAmount = parseInt(form1.originalAmount.value);
	var originalBV = "";
	if(parse(form1.originalUnit.value).length>0 && parse(form1.originalAmount.value).length>0){
		originalBV = originalUnit*originalAmount;
	}else if(parse(form1.originalUnit.value).length<=0 && parse(form1.originalBV.value).length>0){
		originalBV = form1.originalBV.value;
	}

	// 0≦「原始入帳－單價」＜10,000
			if(originalUnit>=10000){
				alert(" 原始入帳－單價必須 < 10,000 !");
			}
			if(originalUnit<0){
				alert(" 原始入帳－單價必須 ≧ 0 !");
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
	}else
		form1.bookValue.value = form1.originalBV.value;
}


function changeDate(){
	var limitYear = form1.limitYear.value;
	var otherLimitYear = form1.otherLimitYear.value;
	
	if(AllTrim(form1.limitYear).length>0){
		setFormItem("otherLimitYear", "R")
		form1.otherLimitYear.value="";
		form1.otherLimitYear.style.backgroundColor="";
	}else setFormItem("otherLimitYear", "O")
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
}

function changeOriginal(){
	//document.all.btn_originalMoveDate.disabled=false;
	//form1.originalMoveDate.readOnly = false;
	//document.all.originalKeepUnit.disabled=false;
	//document.all.originalKeeper.disabled=false;
	//document.all.originalUseUnit.disabled=false;
	//document.all.originalUser.disabled=false;
	//document.all.originalPlace.disabled=false;
	//form1.originalPlace.readOnly = false;
	//form1.originalMoveDate.style.backgroundColor="";
	//form1.originalKeepUnit.style.backgroundColor="";
	//form1.originalKeeper.style.backgroundColor="";
	//form1.originalUseUnit.style.backgroundColor="";
	//form1.originalUser.style.backgroundColor="";
	//form1.originalPlace.style.backgroundColor="";
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


function execContainNonexp(){
	if(form1.enterOrg.value=='' || form1.ownership.value=='' || form1.caseNo.value=='' ){
		alert("請先查詢案件！！");
		form1.enterOrgName.style.backgroundColor=errorbg;
		form1.caseNo.style.backgroundColor=errorbg;
		return false;
	}else{
		form1.enterOrgName.style.backgroundColor='';
		form1.caseNo.style.backgroundColor='';
	}
	
	var prop="";
	var windowTop=(document.body.clientHeight)/2-100;
	var windowLeft=(document.body.clientWidth)/2-400;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no, menubar=no,";
	prop=prop+"width=1000,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	var condition = "?enterOrg="+form1.enterOrg.value+
					"&ownership="+form1.ownership.value+
					"&caseNo="+form1.caseNo.value+
					"&enterDate="+form1.enterDate.value;
	
	window.open("untne053f.jsp" + condition,'MyWindow',prop);
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
function changePropertyNo(propertyNo){}

function popCalndar(dateField,js,sY,sM){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=320px,height=250,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";	
	closeReturnWindow();
	returnWindow=window.open(getVirtualPath() + 'home/calendar.jsp?dateField=' + dateField + '&sY='+sY+'&sM='+sM+'&js='+js,'popCalendar',prop);		
}
function getSourceKindName(popSourceKind,popSourceKindName){
	//傳送JSON
	var comment={};	
	comment.codeid = document.all.item("sourceKind").value;
	
	$.post('../ch/querySourceKind.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');
			
			if(typeof(data['codename']) == 'undefined'){
				$("input[name='sourceKind']").val('');
			}else{
				$("input[name='sourceKindName']").val(data['codename']);	
			}
		});
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
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>		
		<td ID=t2 CLASS="tab_border1">*基本資料</td>		
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne003f_1.jsp');">物品明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne034f_1.jsp');">附屬設備</a></td>
		<td ID=t5 CLASS="tab_border2">附屬設備明細</td>
		<td ID=t6 CLASS="tab_border2">帳務資料</td>
		<td ID=t7 CLASS="tab_border2">移動紀錄</td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>			
	</tr>
</TABLE>
<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input type="hidden" name="checkOriginalAmount" value="">
<input type="hidden" name="checkOriginalUnit" value="">
<input type="hidden" name="checkOriginalBV" value="">
<input type="hidden" name="addProofCaseNo" value="">
<!--Query區============================================================-->
<div id="queryContainer" style="width:950px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE001Q",obj);%>
	<jsp:include page="untne002q_1.jsp" />
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
			 <!--   電腦單號：-->
			<input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">物品區分別：</td>
		<td colspan="3" class="td_form_white">
		 <%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
			&nbsp;&nbsp;&nbsp;		
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo()%>">]
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
			<select class="field" type="select" name="propertyKind" onChange="changeSelect();">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getPropertyKind())%>
			</select>
			　　　基金物品：
			<%=util.View._getSelectHTML_withEnterOrg("field", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
<!-- 		　<font color="red">*</font>珍貴物品：
			<select class="field" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
-->			<input class="field" type="hidden" name="valuable" value="N" >
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>物品編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>" onchange="getProperty('propertyNo','propertyNoName','','NE')">
			<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','6&isLookup=Y&js=changeDate()')" value="..." title="財產編號輔助視窗">
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			　 　別名：
			<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
		<br>
			分號：
			起[<input class="field_PRO" type="text" name="serialNoS" size="7" maxlength="7" value="<%=obj.getSerialNoS()%>">]&nbsp;~&nbsp;
			訖[<input class="field_PRO" type="text" name="serialNoE" size="7" maxlength="7" value="<%=obj.getSerialNoE()%>">]
			批號：[<input class="field_PRO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">	]
			<br>
			原物品編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="12" maxlength="12" value="<%=obj.getOldPropertyNo()%>">]&nbsp;
			原物品分號：
			起[<input class="field_RO" type="text" name="oldSerialNoS" size="7" maxlength="30" value="<%=obj.getOldSerialNoS()%>">]&nbsp;~&nbsp;
			訖[<input class="field_RO" type="text" name="oldSerialNoE" size="7" maxlength="30" value="<%=obj.getOldSerialNoE()%>">]
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
        	[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="3" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;調整後年限(月)：
        	<input class="field" type="text" name="otherLimitYear" size="8" maxlength="3" value="<%=obj.getOtherLimitYear()%>" onChange="changeDate();">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>購置日期：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>" onChange="form1.sourceDate.value = this.value;">
			<input class="field" type="button" name="btn_buyDate" onclick="popCalndar('buyDate','form1.sourceDate.value = opener.form1.buyDate.value')" value="..." title="萬年曆輔助視窗">			
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
				<input class="field" type="hidden" name="storeNo" size="5" maxlength="10" value="<%=obj.getStoreNo()%>">
				[<input class="field_RO" type="text" name="storeNoName" size="20" maxlength="50" value="<%=obj.getStoreNoName()%>">]
				<input class="field" type="button" name="btn_storeNo" onclick="popStore('storeNo','storeNoName')" value="..." title="廠商輔助視窗">
				<input class="field" type="button" name="clear_storeNo" onclick="clearStoreNo();" value="清除">
		</td>
	</tr>
	<tr style="display:none">
		<td class="td_form">增加原因：</td>
		<td class="td_form_white" colspan="3">
		<%=util.View.getCause("field","cause",obj.getCause(),obj.getCauseName(),"1,4","")%>
		　　其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>" readonly="true">
		</tr>
		<tr>
		<td class="td_form">受贈同意函日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
		　受贈同意函文號：
			<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">經費來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="fundsSource" onChange="changeFundsSource();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
			</select>
			<br>
		　其他說明：
			<input class="field" type="text" name="fundsSource1" size="20" maxlength="20" value="<%=obj.getFundsSource1()%>" readonly="true">
		    補助金額：
			<input name="grantValue" type="text" class="field" value="<%=obj.getGrantValue()%>" size="15" maxlength="15">
		&nbsp;會計科目：
			<input class="field" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>物品來源：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getSourceKind("field","sourceKind",obj.getSourceKind(),obj.getSourceKindName())%>
			<font color="red">*</font>&nbsp;&nbsp;&nbsp;物品取得日期：
			<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　物品取得文號：
            <input class="field" type="text" name="sourceDoc" size="15" maxlength="20" value="<%=obj.getSourceDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
		<font color="red">*</font>原始入帳數量：
			<input class="field" type="text" name="originalAmount" size="15" maxlength="7" value="<%=obj.getOriginalAmount()%>" onChange="changeOriginalAmount();changeOriginal();">
		<br>
			<font color="red">*</font>原始入帳單價：
			<input class="field" type="text" name="originalUnit" size="15" maxlength="13" value="<%=obj.getOriginalUnit()%>" onChange="changeOriginalUnit();changeOriginal();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原始入帳總價：
	  		<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" onChange="changeOriginalBV();changeOriginal();">
	  	<br>
			原始入帳摘要：
			<input class="field" type="text" name="originalNote" size="30" maxlength="15" value="<%=obj.getOriginalNote()%>">
		<br>
		帳面數量：
			[<input class="field_RO" type="text" name="bookAmount" size="15" maxlength="7" value="<%=obj.getBookAmount()%>" onChange="changeArea();">]
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;帳面金額總價：
			[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="13" value="<%=obj.getBookValue()%>" onChange="changeArea();">]
		</td>
	</tr>
	
	<tr id="div_use" style="display:none">
		<td class="td_form">保管使用資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
				<select class="field_Q" type="select" name="keepUnit" onchange="form1.useUnit.value = this.value">
				　　<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getKeepUnit())%>
				</select>
				<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'keepUnit','useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<select class="field_Q" type="select" name="keeper" onchange="form1.userNo.value = this.value">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getKeeper())%>
	            </select>
		        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'keeper','userNo')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<select class="field_Q" type="select" name="useUnit" onchange="form1.keepUnit.value = this.value">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getUseUnit())%>
			    </select>	
				<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'useUnit','keepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<select class="field_Q" type="select" name="userNo" onchange="form1.keeper.value = this.value">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getUserNo())%>
	            </select>
		        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'userNo','keeper')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
			          使用註記：
			    <input type="text" class="field" name="userNote" value="<%=obj.getUserNote() %>" size="20">
		        <br>
				存置地點
				<input class="field_Q" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>" onchange="queryPlaceName('enterOrg','place1');">
				<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'place1','place1Name')" value="..." title="存置地點輔助視窗">
			    [<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name()%>">]
				<br>		
				存置地點說明
				<input class="field_Q" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">		
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white"><textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
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
<!--		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">序號</a></th>-->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">分號起-訖</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">總價</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false, false,false,true,false,false,false,false,false,false,false,false,false,true,true,true,true,false,false,false};
	boolean displayArray[] = {true,true,false,true,true,true,false,true,false,true,true,false,false,false,false,false,false,false,false,false};
	out.write(util.View.getQuerylistWithRowNum(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
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
		//刪除之前多出現一道確認訊息
       	case "delete":
		if(!confirm("刪除此筆物品批號，將一併刪除其關聯的批號明細、批號附屬設備、批號明細附屬設備!"))
			return false;
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			
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
		//新增時要執行的動作
		case "insert":
			form1.storeNo.value="";
			form1.storeNoName.value="";
			$("#div_use").show();
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				setFormItem("cause1", "R");
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate", "R");
			}	


			//當經費來源不是「其他補助款」時，鎖住其他說明欄位
			if(form1.fundsSource.value != "03"){
				setFormItem("fundsSource1","R");	
			}	
			
			// 新增時，預設基金財產為不可選
			setFormItem("fundType","R");
			
			// 新增時此區塊的所有內容才顯示
			//isDisplay.style.display="";
			
			break;
		case "insertError":
			form1.storeNo.value="";
			form1.storeNoName.value="";
			$("#div_use").show();
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				setFormItem("cause1", "R");
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate", "R");
			}	

			//當經費來源不是「其他補助款」時，鎖住其他說明欄位
			if(form1.fundsSource.value != "03"){
				setFormItem("fundsSource1","R");	
			}	
			
			break;
		//更新時要做的動作
		case "update":
			if(AllTrim(form1.limitYear).length>0) setFormItem("otherLimitYear", "R")
			else setFormItem("otherLimitYear", "O")
			if(AllTrim(form1.material).length>0) setFormItem("otherMaterial", "R")
			else setFormItem("otherMaterial", "O")
			if(AllTrim(form1.propertyUnit).length>0) setFormItem("otherPropertyUnit", "R")
			else setFormItem("otherPropertyUnit", "O")		
			if(form1.verify.value=="Y" ){
				setFormItem("btn_buyDate,buyDate,propertyKind,fundType,originalAmount,originalUnit,originalBV,originalNote","R");
			}
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				setFormItem("cause1", "R");
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate", "R");
			}	

			// 更新時，當財產性質非「01:公務用」時，不可選 & 不可有資料
			if(form1.propertyKind.value != "01") {
				form1.fundType.value="";
				setFormItem("fundType","R");
			}

			//當經費來源不是「其他補助款」時，鎖住其他說明欄位
			if(form1.fundsSource.value != "03"){
				setFormItem("fundsSource1","R");	
			}	
			
			break;
		case "updateError":
			if(AllTrim(form1.limitYear).length>0) setFormItem("otherLimitYear", "R")
			else setFormItem("otherLimitYear", "O")
			if(AllTrim(form1.material).length>0) setFormItem("otherMaterial", "R")
			else setFormItem("otherMaterial", "O")
			if(AllTrim(form1.propertyUnit).length>0) setFormItem("otherPropertyUnit", "R")
			else setFormItem("otherPropertyUnit", "O")		
			if(form1.verify.value=="Y" ){
				//setFormItem("otherPropertyUnit,otherLimitYear,cause,cause1,btn_buyDate,buyDate,propertyKind,fundType,originalAmount,originalUnit,originalBV,originalNote,nameplate,specification,sourceKind,btn_sourceDate,sourceDate,sourceDoc","R");
				setFormItem("btn_buyDate,buyDate,propertyKind,fundType,originalAmount,originalUnit,originalBV,originalNote","R");				
			}
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				setFormItem("cause1", "R");
			}	

			//若「增加原因」為「受贈」時，「受贈同意函日期、受贈同意函文號」必須有資料
			if(form1.cause.value != "04"){
				setFormItem("approveDate,approveDoc,btn_approveDate", "R");
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



