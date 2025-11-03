<!--
程式目的：物品保管使用異動作業－明細資料
程式代號：untne009f
程式日期：0950222
程式作者：Cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE009F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	if (!"".equals(obj.getUNTNE008F_currentPage())) {
		obj.setPREPAGE_currentPage(obj.getUNTNE008F_currentPage());
		obj.setUNTNE008F_currentPage("");
		obj.setCurrentPage(1);
	}

if("".equals(Common.checkGet(obj.getRoleid()))){
	obj.setRoleid(user.getRoleid());
}
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE009F)obj.queryOne();	
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
		if("".equals(obj.getSerialNo())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setOwnership(((String[])objList.get(0))[2]);
			obj.setCaseSerialNo(((String[])objList.get(0))[5]);
			obj.setCaseNo(((String[])objList.get(0))[4]);
			obj.setDifferenceKind(((String[])objList.get(0))[22]);
			obj.setPropertyNo(((String[])objList.get(0))[6]);
			obj.setLotNo(((String[])objList.get(0))[23]);
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/unitProperty.js"></script>
<script language="javascript" src="../../js/getUNTNENonexp.js?v=1.0"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untne008f.jsp";
		} else {
			form1.action = "untne009f.jsp";
		}
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		//alertStr += checkEmpty(form1.ownership,"權屬");
		//alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"物品編號");
		alertStr += checkEmpty(form1.lotNo,"物品批號");
		alertStr += checkEmpty(form1.serialNo,"物品分號");
		alertStr += checkEmpty(form1.buyDate,"購置日期");
		alertStr += checkEmpty(form1.verify,"異動單確認");
		alertStr += checkEmpty(form1.propertyKind,"物品性質");
		//alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		alertStr += checkEmpty(form1.bookAmount,"帳面數量");
		alertStr += checkEmpty(form1.bookValue,"帳面總價");
		//alertStr += checkEmpty(form1.oldKeepUnit,"原保管單位");
		//alertStr += checkEmpty(form1.oldKeeper,"原保管人");
		//alertStr += checkEmpty(form1.oldUseUnit,"原使用單位");
		//alertStr += checkEmpty(form1.oldUserNo,"原使用人");
		//alertStr += checkEmpty(form1.newMoveDate,"新移動日期");
		alertStr += checkEmpty(form1.newPlace1Name,"存置地點");
		alertStr += checkEmpty(form1.newPlace1,"存置地點");
		alertStr += checkDate(form1.newMoveDate,"新移動日期");
		alertStr += checkEmpty(form1.newKeepUnit,"新保管單位");
		alertStr += checkEmpty(form1.newKeeper,"新保管人");
		alertStr += checkEmpty(form1.newUseUnit,"新使用單位");
		alertStr += checkEmpty(form1.newUserNo,"新使用人");
		alertStr += checkEmpty(form1.useYear,"已使用年數");
		alertStr += checkEmpty(form1.useMonth,"已使用月數");
		alertStr += checkLen(form1.notes,"備註",250);
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
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		form1.action=surl;
		form1.state.value="queryOne";
		form1.UNTNE008F_currentPage.value = form1.PREPAGE_currentPage.value;
		form1.currentPage.value=form1.mainPage.value;
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;		
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	var caseNo = parse(form1.caseNo.value);
	
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	
	if (form1.verify.value == "Y") {
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
	
	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.newKeepUnitQuickly, form1.newKeepUnit, form1.newUseUnitQuickly, form1.newUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.newKeeperQuickly, form1.newKeeper, form1.newUserNoQuickly, form1.newUserNo, true, false);

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value+ form1.propertyNo.value + form1.serialNo.value + form1.differenceKind.value ;
		listContainerRowClick(key);
	}
}

//設定「現有資料明細新增鈕」狀態
function setBatchInsertBut() {
	//「登入者所屬機關(SYSAP_Emp.organid)」與「移動單入帳機關(UNTMP_MoveProof.enterOrg)」資料一致,且該移動單尚未入帳時才能按「現有資料明細新增鈕」
	if ("<%=user.getOrganID()%>" == form1.enterOrg.value && form1.verify.value == "N") {
		setFormItem("batchInsertBut","O");
	} else {
		setFormItem("batchInsertBut","R");
	}
}

function getData(){
	//已使用年數 = (系統日期 - 購置日期) 之年數
	form1.useYear.value = parseInt((getDateDiff("m", form1.buyDate , form1.getToday))/12) ;
	
	//已使用月數 = (系統日期 - 購置日期) ★/12 之餘額月數
	form1.useMonth.value = (getDateDiff("m", form1.buyDate , form1.getToday))%12;
}

function checkValue(){
	var checkCaseNo = form1.caseNo.value;			//電腦單號
	var checkPropertyNo = form1.propertyNo.value;	//物品編號
	var checkSerialNo = form1.serialNo.value;		//物品分號
	
	if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.propertyNo.value = "";
		form1.propertyNoName.value = "";
		form1.serialNo.value = "";
	}else{
		if(parse(form1.keepUnit.value).length>0 || parse(form1.keeper.value).length>0){


			
			form1.oldMoveDate.value=form1.moveDate.value;
			form1.oldKeepUnit.value=form1.keepUnit.value;
			form1.oldKeeper.value=form1.keeper.value;
			form1.oldUseUnit.value=form1.useUnit.value;
			form1.oldUserNo.value=form1.userNo.value;
			form1.oldUserNote.value=form1.userNote.value;
			form1.oldPlace1.value=form1.place1.value;
			
			//getKeepUnit(form1.tempEnterOrg, form1.oldKeepUnit, form1.keepUnit.value);
			//getKeeper(form1.tempEnterOrg, form1.oldKeeper, form1.keeper.value ,'N');
			//getKeepUnit(form1.tempEnterOrg, form1.oldUseUnit, form1.useUnit.value);
			//getKeeper(form1.tempEnterOrg, form1.oldUserNo, form1.userNo.value,'N');
	
			form1.oldPlace.value=form1.place.value;

			// 客戶 605 
			form1.newKeepUnit.value=form1.keepUnit.value;
			form1.newKeepUnitQuickly.value =form1.keepUnit.value; 
			form1.newKeeper.value=form1.keeper.value;
			form1.newKeeperQuickly.value=form1.keeper.value;
			form1.newUseUnit.value=form1.useUnit.value;
			form1.newUseUnitQuickly.value=form1.useUnit.value;
			form1.newUserNo.value=form1.userNo.value;
			form1.newUserNoQuickly.value=form1.userNo.value;
			form1.newUserNote.value=form1.userNote.value;
			form1.newPlace1.value=form1.place1.value;
			form1.newPlace.value=form1.place.value;
			form1.newPlace1Name.value = form1.oldPlaceName.value;
		}
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

function changeUse(){
	form1.newUseBureau.value = form1.newKeepBureau.value;
	
	getKeepUnit(form1.tempEnterOrg, form1.newUseUnit, form1.newKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.newUseUnit, form1.newUserNo, form1.newKeeper.value);
}

function gountne010f() {
	var prop = "";
	
/*	var windowTop = (document.body.clientHeight-80)/2+25;
	var windowLeft = (document.body.clientWidth-800)/2+250;
	var windowWidth = 775;
	var windowheight = 475; */
	

	var enterOrg = form1.enterOrg.value;
	var ownership = form1.ownership.value;
	var caseNo = form1.caseNo.value;
	//var newMoveDate = form1.newMoveDate.value;
	//var differenceKind = form1.differenceKind.value;
	var verify = form1.verify.value;
	//beforeSubmit();
	//returnWindow=window.open("untne010f.jsp?enterOrg="+enterOrg+"&tempEnterOrg="+enterOrg+"&ownership="+ownership+"&caseNo="+caseNo+"&verify="+verify+"&differenceKind="+differenceKind,"aha",prop);
	//問題單1761重新宣告新的變數 ，避免呼叫到function.js裡的returnWindow的方法導致untne010f的視窗被關閉。
	var returnWindow1=window.open("untne010f.jsp?enterOrg="+enterOrg+"&tempEnterOrg="+enterOrg+"&ownership="+ownership+"&caseNo="+caseNo+"&verify="+verify,"aha",prop);
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
					form1.buyDate.value =  json[i].buyDate;
					form1.propertyKind.value =  json[i].propertyKind;
					form1.fundType.value =  json[i].fundType;
					form1.valuable.value =  json[i].valuable;
					form1.bookValue.value =  json[i].bookValue;
					form1.bookUnit.value =  json[i].bookValue;
					form1.bookAmount.value = json[i].bookAmount;
					form1.specification.value =  json[i].specification;
					form1.nameplate.value =  json[i].nameplate;
					form1.sourceDate.value =  json[i].sourceDate;
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
					form1.oldPlaceName.value =  json[i].oldPlaceName;
				}
			}
		}
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untne008f.jsp');"><font color="red">*</font>移動單資料</a></td>
		<td ID=t2 CLASS="tab_border1">*移動單明細資料</td>		
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>">
<input class="field_QRO" type="hidden" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
<input type="hidden" name="keepUnit" value="">
<input type="hidden" name="keeper" value="">
<input type="hidden" name="useUnit" value="">
<input type="hidden" name="userNo" value="">
<input type="hidden" name="place" value="">
<input type="hidden" name="userNote" value="">
<input type="hidden" name="place1" value="">
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input type="hidden" name="verify" value="<%=obj.getVerify()%>">
<input type="hidden" name="proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" name="proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" name="proofNo" value="<%=obj.getProofNo()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE008Q",obj);%>
	<jsp:include page="untne008q.jsp" />
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
			<select class="field" type="select" name="differenceKind" onchange="changeDifferenceKind();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,obj.getDifferenceKind())%>
			</select>	
			&nbsp;&nbsp;&nbsp;		
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo()%>">]
       	</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">購置日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　　　
			異動單確認：
			[<select class="field_QRO" type="select" name="verify1">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>]&nbsp;&nbsp;&nbsp;　　　
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>物品編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','','NE');getNonexp();getData();checkValue();"> 
			<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','6&isLookup=N')" value="..." title="物品編號輔助視窗">			
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			&nbsp;
			分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>"  onChange="getNonexp();getData();checkValue();">
			&nbsp;&nbsp;　　　批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
		<br>
			別名：<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			&nbsp;&nbsp;原物品編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="12" maxlength="12" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="18" maxlength="30" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">原移動資料：</td>
		<td class="td_form_white" colspan="3">
			移動日期：[<input class="field_RO" type="text" name="oldMoveDate" size="7" maxlength="7" value="<%=obj.getOldMoveDate()%>">]
		<br>
			保管單位：
			<select class="field_RO" type="select" name="oldKeepUnit">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getOldKeepUnit())%>
			</select>
			保管人：
			<select class="field_RO" type="select" name="oldKeeper">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getOldKeeper())%>
	        </select>
        <br>
			使用單位：
			<select class="field_RO" type="select" name="oldUseUnit">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getOldUseUnit())%>
			</select>
			使用人：   
			<select class="field_RO" type="select" name="oldUserNo">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getOldUserNo())%>
	        </select>
			&nbsp;&nbsp;&nbsp;
			使用註記
			[<input type="text" class="field_RO" name="oldUserNote" value="<%=obj.getOldUserNote() %>" size="20">]
		<br>
			存置地點：
			[<input class="field_RO" type="text" name="oldPlace1" size="10" maxlength="10" value="<%=obj.getOldPlace1()%>">]
			[<input class="field_RO" type="text" name="oldPlaceName" size="20" maxlength="20" value="<%=obj.getOldPlaceName()%>">]
		<br>
			存置地點說明：
			[<input class="field_RO" type="text" name="oldPlace" size="30" maxlength="30" value="<%=obj.getOldPlace()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">新移動資料：</td>
		<td class="td_form_white" colspan="3">
			移動日期：[<input class="field_QRO" type="text" name="newMoveDate" size="7" maxlength="7" value="<%=obj.getNewMoveDate()%>">]
		<br>
			<font color="red">*</font>保管單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "newKeepUnit", "form1.newUseUnit.value = this.value;", 
			                                                       "newKeepUnitQuickly", "", obj.getNewKeepUnit()) %>
			<input class="field" type="button" name="btn_newKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'newKeepUnit')" value="..." title="單位輔助視窗">
			<font color="red">*</font>保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                        "field", "form1", "newKeeper", "",
			                                                        "newKeeperQuickly", "", obj.getNewKeeper()) %>
            <input class="field" type="button" name="btn_newKeeper" onclick="popUnitMan(form1.organID.value,'newKeeper')" value="..." title="人員輔助視窗">
		<br>
			<font color="red">*</font>使用單位：
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       	"field", "form1", "newUseUnit", "newUseUnitQuickly", obj.getNewUseUnit()) %>
            <input class="field" type="button" name="btn_newUseUnit" onclick="popUnitNo(form1.organID.value,'newUseUnit')" value="..." title="單位輔助視窗">
			<font color="red">*</font>使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                        "field", "form1", "newUserNo", "newUserNoQuickly", obj.getNewUserNo()) %>
			<input class="field" type="button" name="btn_newUserNo" onclick="popUnitMan(form1.organID.value,'newUserNo')" value="..." title="人員輔助視窗">
			&nbsp;&nbsp;&nbsp;
			使用註記
			<input type="text" class="field" name="newUserNote" value="<%=obj.getNewUserNote() %>" size="20">
		<br>
			存置地點：
			<input class="field_Q" type="text" name="newPlace1" size="10" maxlength="10" value="<%=obj.getNewPlace1()%>" onchange="queryPlaceName1('enterOrg','newPlace1');">
		<input class="field_Q" type="button" name="btn_q_place1" onclick="popPlace(form1.organID.value,'newPlace1','newPlace1Name')" value="..." title="存置地點輔助視窗">
		[<input class="field_RO" type="text" name="newPlace1Name" size="20" maxlength="20" value="<%=obj.getNewPlace1Name()%>">]	
		<br>
			存置地點說明：
			<input class="field" type="text" name="newPlace" size="30" maxlength="30" value="<%=obj.getNewPlace() %>">
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
			&nbsp;&nbsp;&nbsp;　其他單位：
			[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>">] 
		<br>
	  		使用年限： 
	  		[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="7" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;　調整後年限(月)：
			[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form" width="15%">已使用年數：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="useYear" size="3" maxlength="3" value="<%=obj.getUseYear()%>">]
			年
			[<input class="field_RO" type="text" name="useMonth" size="2" maxlength="2" value="<%=obj.getUseMonth()%>">]
			個月
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">廠牌：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate()%>">]
			型式：[<input class="field_RO" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">物品性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			&nbsp;&nbsp;　　　基金物品：
			<select class="field_RO" type="select" name="fundType">
				<%=util.View.getOption("select a.codeID, a.codeName from SYSCA_Code a , SYSCA_FUNDORGAN b where a.codeKindID='FUD' and a.codeid=b.fundno"  , obj.getFundType())%>
			</select>
			<!-- &nbsp;&nbsp;珍貴財產： -->
			<input class="field_RO" type="hidden" name="valuable" value="N">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">帳面資料：</td>
		<td class="td_form_white" colspan="3">
			取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
			&nbsp;&nbsp;　　　　帳面數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">]
		<br>
			帳面單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="13" value="<%=obj.getBookUnit()%>">]
			&nbsp;&nbsp;　帳面總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		</td>
	</tr>
	<tr>
	  <td class="td_form" width="15%">備註：</td>
	  <td class="td_form_white">
	  	<textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
	  <td class="td_form"style="display:none;">異動人員/日期：</td>
	  <td class="td_form_white"style="display:none;">
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
	<input type="hidden" name="UNTNE008F_currentPage" value="<%= obj.getUNTNE008F_currentPage()%>" >
	<input type="hidden" name="PREPAGE_currentPage" value="<%= obj.getPREPAGE_currentPage()%>" >				
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
		<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="現有資料明細新增" onClick="gountne010f();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">新移動日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">新保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">新保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">新存置地點</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false};
	boolean displayArray[] = {false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,true,false,true,false,true,true,false,false};
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
			//setFormItem("batchInsertBut","R");
			break;
		case "insertError":
			//setFormItem("batchInsertBut","R");
			break;
	
		//更新時要做的動作
		case "update":
			//setFormItem("batchInsertBut","R");
			break;
		case "updateError":
			//setFormItem("batchInsertBut","R");
			break;
	
		//取消時要執行的動作
		case "clear":
			//setFormItem("batchInsertBut","O");
			break;
		case "clearError":
			//setFormItem("batchInsertBut","O");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("batchInsertBut","O");
			break;
		case "confirmError":
			setFormItem("batchInsertBut","O");
			break;
	
	}
}
</script>
</body>
</html>



