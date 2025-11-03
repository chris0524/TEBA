<!--
程式目的：減損單維護
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH004F01">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%
obj.setQuerySelect("proof");

obj.setRoleid(user.getRoleid());
obj.setEnterOrg(user.getOrganID());

String table = "UNTLA_REDUCEPROOF";
String addTable = "UNTLA_ADDPROOF";

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	
}else if ("queryOne".equals(obj.getState())) {
	obj._execQueryOne();
	obj.setQueryone_enterOrg(obj.getEnterOrg());
	obj.setQueryone_ownership(obj.getOwnership());
	obj.setQueryone_caseNo(obj.getCaseNo());
	obj.setQueryone_differenceKind(obj.getDifferenceKind());
}else if ("insert".equals(obj.getState())) {
	obj._execInsert(table);			
	if ("insertSuccess".equals(obj.getState())) {
		obj.setQueryAllFlag("true");
		obj.setI_enterOrg(obj.getEnterOrg());
		obj.setI_ownership(obj.getOwnership());
		obj.setI_caseNo(obj.getCaseNo());
		obj.setI_differenceKind(obj.getDifferenceKind());
	}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	if (!obj.isMerge()) { 
		if(obj.checkUpdateError() && obj.checkMonthDepr2() && obj.checkNewDataForVerify()
			&& obj.check503isNewest("verify") && obj.checkProofVerifyYN("Y") && obj.checkDetailCanVerify("Y")) {
			obj._execUpdate(table);
			if("Y".equals(obj.getVerify())){
				obj._updateMOVABLEDETAIL("update");	
				obj._updateMOVABLE("update");
			}
			if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
			obj.checkMonthDepr1();
		}
	} else {
		obj.setStateUpdateError();
		obj.setErrorMsg("此案件為合併分割案,請於[土地合併分割作業]進行修改");
	}
	
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	if (!obj.isMerge()) {
		obj._execDelete(table);
		obj.setCaseNo("");
		obj.setOwnership("");
		obj.setDifferenceKind("");
		if ("deleteSuccess".equals(obj.getState())) {
			obj.setQueryAllFlag("true");
		}
	} else {
		obj.setStateDeleteError();
		obj.setErrorMsg("此案件為合併分割案,請於[土地合併分割作業]進行刪除");
	}
}else if ("approveRe".equals(obj.getState())) {
	if(obj.checReVerifyError() && obj.check503ExistOtherProof() && obj.check503isNewest("reVerify") &&
			obj.checkProofVerifyYN("N") && obj.checkDetailCanVerify("N")){
		obj.setReVerify("Y");
		obj._execUpdate(table);
		if("Y".equals(obj.getVerify())) {
			obj._updateMOVABLEDETAIL("approveRe");
			obj._updateMOVABLE("approveRe");
		}
		obj.setReVerify("");	
		obj.setReduceDate("");												
		if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
	}
}else if ("updateNewData".equals(obj.getState())) {
	obj.updateNewData();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
} else if ("transferAdd".equals(obj.getState())) {
	obj.transfer(addTable);
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
			
		}else{
			if("".equals(obj.getQueryone_caseNo())){
				obj.setEnterOrg(((String[])objList.get(0))[0]);
				obj.setOwnership(((String[])objList.get(0))[1]);
				obj.setDifferenceKind(((String[])objList.get(0))[3]);
				obj.setCaseNo(((String[])objList.get(0))[5]);
			}
			obj._execQueryOne();	
		}
	}
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._CH_REDUCE, 1);

boolean checkDataCount = obj.checkDataCountIsZero(obj.getEnterOrg(), obj.getCaseNo());
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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","<%=new unt.ch.UNTCH_Tools()._getDefaultValue(user.getOrganID(), "ownership")%>"),
	new Array("writeUnit","<%=user.getUnitID()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("proofDoc","<%=new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D2")%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("verify","N")	
);
//<!-- 檢查區，譬如：日期、空白... -->
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untch004f01.jsp";
		} else {
			form1.action = "untch004f02.jsp";
		}
	} else if (form1.state.value=="insert"||form1.state.value=="update"){
	
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"增加單編號－字");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkDate(form1.reduceDate,"入帳日期");
		alertStr += checkEmpty(form1.cause,"減損原因");
		alertStr += checkDate(form1.preenterdate,"預計移撥入帳日期");
		alertStr += checkDate(form1.summonsDate,"傳票日期");
		
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.reduceDate,"若要入帳，入帳日期");
		}

		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
			
		alertStr += checkLen(form1.notes, "備註", 250);
		form1.action = "untch004f01.jsp";
	}
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();	
}


function queryOne(enterOrg,ownership,differenceKind,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function clickApproveRe(){
	form1.reduceDate.value="";
	form1.state.value="approveRe";
	beforeSubmit();
	form1.submit();
}	

function updateNewData() {
	if(confirm("確定執行[帶入最新資料]嗎")){
		form1.state.value="updateNewData";
		beforeSubmit();
		form1.submit();
	}
}

function checkNewData() {
	if(form1.verify.value=="N" && <%=obj.checkNewData()%>){
		alert("減損明細資料非最新帳面淨值，請先執行[帶入最新資料]");
		return false;
	}
	return true;
}


function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else{
		form1.state.value="queryAll";
		form1.mainPage.value=form1.currentPage.value;
		form1.mainEnterOrg.value=form1.queryone_enterOrg.value;
		form1.mainOwnerShip.value=form1.queryone_ownership.value;
		form1.mainCaseNo.value=form1.queryone_caseNo.value;
		form1.mainDifferenceKind.value=form1.queryone_differenceKind.value;
		form1.currentPage.value=1;
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}

}

function init(){
	initQ_Form("proof");
	
	changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo2()%>');
	changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo3()%>');
	changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo2()%>');
	changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo3()%>');
	
	if(form1.verify.value=='Y'){	
		setFormItem("update,delete,NewData", "R");
	}else{							
		setFormItem("update,delete,NewData", "O");
	}
	
	setFormItem("print01,print02,print03,print04", "O");
	
	if(<%=checkDataCount%>){
		$("select[name='verify']").attr('class','field_P');
	}else{
		$("select[name='verify']").attr('class','field');
	}
	
	form1.reduceDateTemp.value = form1.reduceDate.value;
	form1.oldpreenterdate.value = form1.preenterdate.value;
	
	setFormItem("print01,print02,print04", "R");
	
	//若減損原因為「未達年限不堪使用(215)」，控制不可列印已達年限報廢單
	if(form1.cause.value == '215'){
	}else{
		setFormItem("print01", "O");
	}
	//減損原因非屬災害毀損(209)、末達年限(215)或遺失(210)…等財產，不得列印毀損報廢單
	//反之，應控制僅能列印毀損報廢單、減損單
	if(form1.cause.value == '209' || form1.cause.value == '215' || form1.cause.value == '210'){
		setFormItem("print02,print04", "O");
	}
	
	// 問題單880: 列印財產毀損報廢單請開放不論作廢原因
	setFormItem("print02", "O");
	
	//非屬撥出財產(201)，不得列印撥出單，反之，應控制僅能列印撥出單、減損單
	if(form1.cause.value == '201'){
		setFormItem("print04", "O");
	}
	//若登入者角色為保管人(SYSAP_EMP.roleid ='1')，
	//則依該筆資料之「入帳機關」取「SYSCA_ORGARGU.printreduceproof」判斷
	//保管人是否可列印減損單
	if('<%=obj.checkIfCanPrint()%>' == 'true'){
		setFormItem("print04", "O");
	}
	
	getCauseName('cause','causeName');
	
	// 回復入帳控制
	// 問題單2299 增加公務轉基金控制
	if (form1.verify.value=='Y' && (form1.isAdminManager.value == 'Y' || '<%=user.getRoleid()%>' == '3')) {
		setFormItem("verifyRe", "O");
		setFormItem("transferAdd", "O");
	} else {
		setFormItem("verifyRe", "R");
		setFormItem("transferAdd", "R");
	}
	
	if (form1.oldpreenterdate.value.length > 0) {
		setFormItem("verifyRe", "R");
	}
	
	//此行要對照primaryArray所設定的pk值為何
	listContainerRowClick(form1.enterOrg.value + form1.ownership.value + form1.differenceKind.value + form1.caseNo.value);
	
	
	lockDifferenceKindIfHasDetailData();
	
	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 財產管理 > > 財產減損單維護";
	
	//問題單2347，groupID非sys01身分別的使用者不顯示刪除按鈕
	if (form1.groupID.value != 'sys01' ) {
		setDisplayItem('delete','H');
	}
}

/**
 * 客戶717 若有detail 則不給修改
 */
function lockDifferenceKindIfHasDetailData() {
	var sendData = {};
	sendData.enterorg = form1.enterOrg.value;
	sendData.ownership = form1.ownership.value;
	sendData.caseno = form1.caseNo.value;
	sendData.differencekind = form1.differenceKind.value;
	$.post('queryReduceDetailCount.jsp', sendData,
			function(data){
				//將回傳資料寫入
				data=eval('('+data+')');
				if (data.length > 0) {
					form1.differenceKind.disable = true;
				} else {
					form1.differenceKind.disable = false;
				}
	});
}

function getReduceDate() {
	if (form1.verify.value=="Y") {
		if (form1.reduceDate.value=="") form1.reduceDate.value='<%=util.Datetime.getYYYMMDD()%>';
		if (form1.summonsDate.value=="") form1.summonsDate.value='<%=util.Datetime.getYYYMMDD()%>';
		setFormItem("reduceDate,btn_reduceDate,preenterdate,btn_preenterdate,summonsDate,btn_summonsDate,summonsNo","O");
	}else{
		form1.reduceDate.value="";
		form1.summonsDate.value="";
		form1.summonsNo.value = "";
		setFormItem("reduceDate,btn_reduceDate,summonsDate,btn_summonsDate,summonsNo","R");
	}
}

function loadUntrp08r(){
	if(checkNewData()){
		var url = "../rp/untrp008r.jsp?"
				+ "organID=" + form1.organID.value
				+ "&q_enterOrg=" + form1.enterOrg.value 
				+ "&q_ownership=" + form1.ownership.value
				+ "&q_differenceKind=" + form1.differenceKind.value		
				+ "&q_caseNoS=" + form1.caseNo.value
				+ "&q_caseNoE="+form1.caseNo.value
				+ "&q_proofYear=" + form1.proofYear.value
				+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
				+ "&q_proofNoS=" + form1.proofNo.value
				+ "&q_proofNoE=" + form1.proofNo.value
				+ "&q_kind=4";
		window.open(url);
	}
}

function loadUntrp006r(){
	if(checkNewData()){
		var url = "../rp/untrp006r.jsp?"
				+ "organID=" + form1.organID.value
				+ "&q_enterOrg=" + form1.enterOrg.value 
				+ "&q_ownership=" + form1.ownership.value
				+ "&q_differenceKind=" + form1.differenceKind.value		
				+ "&q_caseNoS=" + form1.caseNo.value
				+ "&q_caseNoE="+form1.caseNo.value
				+ "&q_proofYear=" + form1.proofYear.value
				+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
				+ "&q_proofNoS=" + form1.proofNo.value
				+ "&q_proofNoE=" + form1.proofNo.value
				+ "&q_kind=4";
		window.open(url);
	}
}

function loadUntrp005r(){
	if(checkNewData()){
		var url = "../rp/untrp005r.jsp?"
				+ "organID=" + form1.organID.value
				+ "&q_enterOrg=" + form1.enterOrg.value 
				+ "&q_ownership=" + form1.ownership.value
				+ "&q_differenceKind=" + form1.differenceKind.value		
				+ "&q_caseNoS=" + form1.caseNo.value
				+ "&q_caseNoE="+form1.caseNo.value
				+ "&q_proofYear=" + form1.proofYear.value
				+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
				+ "&q_proofNoS=" + form1.proofNo.value
				+ "&q_proofNoE=" + form1.proofNo.value
				+ "&q_kind=4";
		window.open(url);
	}
}

function loadUntrp007r(){
	if(checkNewData()){
		var url = "../rp/untrp007r.jsp?"
				+ "organID=" + form1.organID.value
				+ "&q_enterOrg=" + form1.enterOrg.value 
				+ "&q_ownership=" + form1.ownership.value
				+ "&q_differenceKind=" + form1.differenceKind.value		
				+ "&q_caseNoS=" + form1.caseNo.value
				+ "&q_caseNoE="+form1.caseNo.value
				+ "&q_proofYear=" + form1.proofYear.value
				+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
				+ "&q_proofNoS=" + form1.proofNo.value
				+ "&q_proofNoE=" + form1.proofNo.value
				+ "&q_kind=4";
		window.open(url);
	}
}

function getCauseName(popCause,popCauseName){
	//傳送JSON
	var comment={};	
	comment.codeid = document.all.item("cause").value;
	comment.codecond1_1 = "2";
	comment.codecond1_2 = "4";
	
	$.post('queryCause.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');
			if(typeof(data['codename']) == 'undefined'){
				$("input[name='cause']").val('');
			}else{
				$("input[name='causeName']").val(data['codename']);	
			}
		});
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

function changecause(){}

function verifyOnChange() {
}

function preenterdaeOnchange() {
	// TODO
	var state = form1.state.value;
	var preDate = form1.preenterdate.value;
	if (preDate === "") {
		form1.summonsDate.value = "";
		if (state === "update") {
			form1.verify.disabled = false;
			form1.reduceDate.disabled = false;
			form1.btn_reduceDate.disabled = false;
		}
		$(".preenterchilds").hide();
	} else {
		form1.summonsDate.value = preDate;
		setFormItem("summonsDate,btn_summonsDate,summonsNo", "O");
		if (state === "update") {
			form1.verify.value = "N";
			form1.reduceDate.value = "";
			form1.verify.disabled = true;
			form1.reduceDate.disabled = true;
			form1.btn_reduceDate.disabled = true;
		}
		$(".preenterchilds").show();
	}
}

function transfer() {
	if (confirm("確定執行[公務轉基金]嗎")) {
		form1.state.value="transferAdd";
		beforeSubmit();
		form1.submit();
	}
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">	
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
<input type="hidden" name="addcaseno" value="<%=obj.getAddcaseno()%>">
<input type="hidden" name="saveLog" value="Y">
<input type="hidden" name="objPath" >

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>

<!--Query區============================================================-->
<div id="queryContainer" style="width:900px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH004Q",obj);%>
	<jsp:include page="untch004q.jsp" />
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	
	<tr>
		<td class="td_form"width="5%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
           	<input class="field_PRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;
			<font color="red">*</font>權屬：
            <select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
            &nbsp;&nbsp;
            <font color="red">*</font>財產區分別：
			<%=util.View._getSelectHTML("field_P", "differenceKind", obj.getDifferenceKind(),"DFK") %>
			<input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">			
            <input class="field_QRO" type="hidden" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" >填單日期：</td>
		<td colspan="3" class="td_form_white">
		    <%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
		    &nbsp;&nbsp;
			填造單位：
			<select class="field" type="select" name="writeUnit" >
				<%=util.View.getOption("select  unitno, unitname from UNTMP_KEEPUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno", obj.getWriteUnit())%>
			</select>
			<input class="field" type="button" name="btn_writeUnit" onclick="popUnitNo(form1.enterOrg.value,'writeUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 
			財產管理單位編號：
			 -->
			<input class="field" type="hidden" name="manageNo" size="15" maxlength="10" value="<%=obj.getManageNo()%>">
		</td>
      </tr>
	<tr>
		<td class="td_form"><font color="red">*</font>減損單編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_Q" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field_Q" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_QRO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 號
		</td>
	</tr>
	
	<tr>
		<td class="td_form"><font color="red">*</font>減損原因：</td>
		<td class="td_form_white" colspan = "3">
			<%=uctls.getReduceCause("field","cause",obj.getCause(),obj.getCauseName(),"2,4","")%>
			&nbsp;其他說明：<input class="field" type="text" name="cause1" size="30" maxlength="20" value="<%=obj.getCause1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">報廢拆除原因：</td>
		<td class="td_form_white"  colspan="3">
			<input class="field" type="text" name="unusableCause" size="45" maxlength="50" value="<%=obj.getUnusableCause()%>">
			&nbsp;拆除日期：<%=util.View.getPopCalndar("field","demolishDate",obj.getDemolishDate())%>
		</td> 
	</tr>
	<tr>
		<td class="td_form">核准機關：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="approveOrg" onChange="changeApproveOrg();">
            	<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ",obj.getApproveOrg())%>
            </select>
				&nbsp;&nbsp;&nbsp;核准日期：<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
				&nbsp;&nbsp;&nbsp;核准文號：<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
	</tr>
	<tr>
		<td class="td_form" >入帳：</td>
		<td colspan="3" class="td_form_white">            			
	       	<select class="field" type="select" name="verify" onChange="verifyOnChange();getReduceDate();">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;
	       	入帳日期：
			<%=util.View.getPopCalndar("field","reduceDate",obj.getReduceDate())%>
			<input type="hidden" class="field_QRO" name="reduceDateTemp" value="<%=obj.getReduceDateTemp()%>" size="7">
			&nbsp;&nbsp;&nbsp;
			預計移撥入帳日期：
			<%=util.View.getPopCalndar("field","preenterdate",obj.getPreenterdate(), "preenterdaeOnchange()")%>
			<input type="hidden" class="field_QRO" name="oldpreenterdate" value="<%=obj.getReduceDateTemp()%>"  >
			<font color="red" class="preenterchilds" style="display:none;">※減損單於移撥日期當月折舊提列並完成月結後自動入帳，不需再執行入帳</font>
		</td>
      </tr>  
      <tr>
		<td class="td_form">傳票日期：</td>
		<td colspan="3" class="td_form_white" >
			<%=util.View.getPopCalndar("field","summonsDate",obj.getSummonsDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			傳票號數：
			<input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>">              
			
		</td>		
	</tr>
	<tr>
		<td  rowspan="2" class="td_form" >備註：</td>
		<td width="30%" class="td_form_white" >	
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>		</td>
		<td width="20%" class="td_form"style="display:none;">異動人員/日期：</td>
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
	<jsp:include page="../../home/toolbar.jsp" />
	
	<span id="spanTransferAdd">
		<br>&nbsp;|
		<input class="toolbar_default" type="button" followPK="true" name="transferAdd" value="公務轉基金" onClick="transfer();">&nbsp;|
	</span>
	<span id="spanVerifyRe">
	<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復入帳" disabled="true" onClick="this.disabled=true;clickApproveRe();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="true" name="NewData" value=帶入最新資料 disabled="true" onClick="updateNewData();">&nbsp;|
	</span>
	
	<span id="spanPrint01">
		<input class="toolbar_default" type="button" followPK="true" name="print01" value="列印已達年限報廢單" onClick="loadUntrp006r();">&nbsp;|
	</span>
	<span id="spanPrint02">
		<input class="toolbar_default" type="button" followPK="true" name="print02" value="列印毀損報廢單" onClick="loadUntrp005r();">&nbsp;|
	</span>
	<span id="spanPrint03">
		<input class="toolbar_default" type="button" followPK="true" name="print03" value="列印撥出單" onClick="loadUntrp007r();">&nbsp;|
	</span>
	<span id="spanPrint04">
		<input class="toolbar_default" type="button" followPK="true" name="print04" value="列印減損單" onClick="loadUntrp08r();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">減損單編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">減損日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,true,false,false,false,false};
	boolean displayArray[] = {false,false,true,false,true,false,true,true,true,true};
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
			if (!confirm("刪除此減損單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
			return false;			
			hasBeenConfirmed = true;
			break;					
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	if (form1.preenterdate.value.length > 0) {
		$(".preenterchilds").show();
	} else {
		$(".preenterchilds").hide();
	}
	
	switch (buttonName)	{
		case "insert":
			setFormItem("verify,reduceDate,btn_reduceDate,summonsDate,btn_summonsDate,summonsNo","R");
			break;
		case "update":
			if (form1.verify.value=="Y") {
				// 入帳後 僅可透過回復入帳 做單據資料異動
				setAllReadonly();
			} else {
				var isRole3 = '<%=user.getRoleid()%>' == '3';
				var isAdmin = '<%=user.getIsAdminManager()%>' == 'Y';
				
				setFormItem("cause","R");
				setFormItem("btn_cause","R");
				//setFormItem("cause1","R");
				
				if (isRole3){
					setFormItem("verify","O");
				}else{
					setFormItem("verify","R");
				}			
				if (isAdmin){
					setFormItem("verify","O");
				}
				if (form1.preenterdate.value.length > 0) {
					setFormItem("verify,reduceDate,btn_reduceDate","R");
					setFormItem("summonsDate,btn_summonsDate,summonsNo", "O");
				} else {
					setFormItem("reduceDate,btn_reduceDate,summonsDate,btn_summonsDate,summonsNo,transferAdd","R");	
				}		
			}
			break;
		case "clear":
			if (form1.verify.value == "Y") {
				setFormItem("transferAdd", "O");
			} else {
				setFormItem("transferAdd", "R");		
			}
			break;
	}
}
</script>
</body>
</html>
