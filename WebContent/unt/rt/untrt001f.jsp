<!--
程式目的：權利主檔資料維護-增加單資料
程式代號：untrt001f
程式日期：0940929
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rt.UNTRT001F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	obj = (unt.rt.UNTRT001F)obj.queryOne();	
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._RT_ADD, 3);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._RT_ADD, 2);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._RT_ADD, 2);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._RT_ADD, 2);
}else{
	uch._setupViewType(uch._queryOrMaintenance);
	tabs = uch._createTabData(uch._RT_ADD, 2);
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
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("writeUnit","<%=user.getUnitName()%>"),
	new Array("ownership","1"),
	new Array("dataState","1"),
	new Array("verify","N"),
	new Array("OriginalBV","0"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>")
);

function getEnterDate() {
	if (form1.verify.value=="Y") {
		if (form1.enterDate.value=="") form1.enterDate.value="<%=util.Datetime.getYYYMMDD()%>";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		//查詢時為增加單查詢或是標的資料查詢
		if (document.all.querySelect[0].checked) {
			form1.action = "untrt001f.jsp";
		} else {
			form1.action = "untrt002f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.propertyNo,"財產編號");		

		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.enterDate,"若要入帳，入帳日期");
		}

		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.buyDate,"購置日期");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.buyDate,"購置日期");
		alertStr += checkDate(form1.sourceDate,"財產來源－取得日期");
		alertStr += checkFloat(form1.originalBV,"原始設定價值",15,0);
		alertStr += checkFloat(form1.bookValue,"帳面設定價值",15,0);
		alertStr += checkDate(form1.registerDate,"登記日期");
		alertStr += checkDate(form1.payDate,"清償時間");
		alertStr += checkFloat(form1.interest,"利息",15,0);
		alertStr += checkLen(form1.notes, "備註", 250);

		//當財產性質選非「01:公務用」時，「基金財產」不可有資料
		if(form1.propertyKind.value != "01") {
			form1.fundType.value = "";
			setFormItem("fundType","R");
		}
		//當增加原因選「其他」時，開放其他說明欄位且必須有資料
		if(form1.cause.value=="99") alertStr += checkEmpty(form1.cause1,"其他說明");
		else form1.cause1.value = "";
		
		//若權利為「股份,其他財產權利」開放原始設定價值
		var checkPropertyNo = form1.propertyNo.value;
		if(checkPropertyNo.substring(0,3) == "801" || checkPropertyNo.substring(0,3) == "806"){
			alertStr += checkEmpty(form1.originalBV,"原始設定價值");
			if (parseInt(form1.originalBV.value)<=0){
				form1.originalBV.style.backgroundColor=errorbg;
				alertStr +="原始設定價值必須 > 0 !\n";
			}else
				form1.originalBV.style.backgroundColor="";
		}
		getEnterDate();
	}
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

function changeBookValue(){
	form1.bookValue.value = form1.originalBV.value;
}

function changeSelect(){
	//若權利為「股份,其他財產權利」開放原始設定價值
	var checkPropertyNo = form1.propertyNo.value;
	if(checkPropertyNo.substring(0,3) == "801" || checkPropertyNo.substring(0,3) == "806"){
		setFormItem("originalBV","O");
	}else{
		setFormItem("originalBV","R");		
		form1.originalBV.value="0";
	}
	//當增加原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "99") form1.cause1.readOnly = false;
	else{
		form1.cause1.readOnly = true;
		form1.cause1.value="";
	}
	//財產性質為「01:公務用」時，「基金財產」才可選。
	if(form1.propertyKind.value == "01") {
		document.all.fundType.disabled=false;
	}else{
		document.all.fundType.disabled=true;
		form1.fundType.value="";
	}
	
}

function clickApproveAll(){
	if(confirm("您確定要將列表中未入帳的增加單全部入帳?")){
		document.all('state').value='approveAll';
		beforeSubmit();
		form1.submit();
	}
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	//若權利為「股份,其他財產權利」不可點選標的資料
	var checkPropertyNo = form1.propertyNo.value;
/*	
	if(checkPropertyNo.substring(0,3) == "801" || checkPropertyNo.substring(0,3) == "806"){
		alert("權利為「股份,其他財產權利」不可點選標的資料!");
		form1.state.value="queryOne";
	}else if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
*/		

		if (surl=="../ch/untch001f02.jsp"){
			if('<%=obj.getIsAddProof()%>' != 'true'){
				form1.state.value="queryAll";
			}
		}
		if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
			form1.mainPage1.value="";
			form1.currentPage.value=form1.mainPage.value;
		}
			
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;

		form1.state.value="queryAll";
		form1.action = surl;
		beforeSubmit();
		form1.submit();
//	}
}

function init(){
	setDisplayItem("spanQueryAll,spanInsert,spanNextInsert","H");
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
	
	// 暫時不開放修改跟刪除, 因此頁面有太多bug , 僅當作跳到其他頁籤的跳板
	// spanUpdate,
	setDisplayItem("spanDelete","H");
	
	if(form1.verify.value === 'Y') {
	    setFormItem("update", "R");
	}
	
}

function goUntrt003r(){
	var url = "untrt003p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo.value + "&q_caseNoE="+form1.caseNo.value +
		"&q_kind=4";
	window.open(url);
}

function goUntrt004r(){
	var url = "untrt004p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo.value + "&q_caseNoE="+form1.caseNo.value ;
	window.open(url);
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<!--Query區============================================================-->
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer2" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame2"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
</div>
<!-- 保留第一頁查詢條件與頁數使用 -->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="16%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
			<input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　　<font color="red">*</font>權屬：
			<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			&nbsp;　　電腦單號：
			[<input class="field_RO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
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
       		[<input class="field_PRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo()%>">]
			[<input class="field_PRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName()%>">]			
       	</td>
       </tr>
    <tr>
  	    <td class="td_form">入帳：</td>
    	<td colspan="3" class="td_form_white">
    		<select class="field_RO" type="select" name="verify" onChange="getEnterDate();">
        		<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;　入帳日期：
			[<input class="field_RO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]              
			&nbsp;　資料狀態：
			<select class="field_RO" type="select" name="dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>			
			</select>			
      	</td>
    </tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"8&isLookup=N&js=changeSelect()")%>		
			　　分號：[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]			
		<br>
			別名：<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			&nbsp;&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD'", obj.getPropertyKind())%>
			</select>
			&nbsp;　　　　基金財產：
			<select class="field" type="select" name="fundType" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD'", obj.getFundType())%>
			</select>    
		</td>
	</tr>
	
	<tr>
		<td class="td_form">憑證字號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="proofDoc1" size="20" maxlength="20" value="<%=obj.getProofDoc1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">增加原因：</td>
		<td class="td_form_white" colspan="3">
			<%=uctls.getCause("field","cause",obj.getCause(),obj.getCauseName(),"1,4")%>
			&nbsp;　　　其他說明：
			<input class="field" type="text" name="cause1" size="15" maxlength="20" value="<%=obj.getCause1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="sourceKind" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKD' ", obj.getSourceKind())%>
			</select>
			&nbsp;　<font color="red">*</font>財產取得日期：<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
			　財產取得文號：<input class="field" type="text" name="sourceDoc" size="15" maxlength="20" value="<%=obj.getSourceDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>購置日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field","buyDate",obj.getBuyDate())%>
			&nbsp;&nbsp;&nbsp;　　內容：<input class="field" type="text" name="meat" size="30" maxlength="30" value="<%=obj.getMeat()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">設定資料：</td>
		<td class="td_form_white" colspan="3">
			原始入帳摘要：<input class="field" type="text" name="originalNote" size="15" maxlength="20" value="<%=obj.getOriginalNote()%>">
		<br>
			原始設定價值：<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" onFocusOut="changeBookValue();">
			&nbsp;　帳面設定價值：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		<br>
			登記日期：<%=util.View.getPopCalndar("field","registerDate",obj.getRegisterDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　登記原因：
			<select class="field" type="select" name="registerCause" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RCA' ", obj.getRegisterCause())%>
			</select>
		<br>
			設定起訖日期：<input class="field" type="text" name="setPeriod" size="15" maxlength="20" value="<%=obj.getSetPeriod()%>">
			&nbsp;　　　　設定人：<input class="field" type="text" name="setPerson" size="15" maxlength="15" value="<%=obj.getSetPerson()%>">
		<br>
			共同權利人：<input class="field" type="text" name="commonObligee" size="15" maxlength="15" value="<%=obj.getCommonObligee()%>">
			&nbsp;　　　　清償時間：<%=util.View.getPopCalndar("field","payDate",obj.getPayDate())%>
		<br>
			利息：<input class="field" type="text" name="interest" size="15" maxlength="15" value="<%=obj.getInterest()%>">
			&nbsp;&nbsp;　　　　　地租：<input class="field" type="text" name="rent" size="30" maxlength="30" value="<%=obj.getRent()%>">
		<br>
			其他事由：<input class="field" type="text" name="notes1" size="60" maxlength="60" value="<%=obj.getNotes1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">原始折舊資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>折舊方法
			<select name="originalDeprMethod" class="field_RO" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getOriginalDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="originalBuildFeeCB" name="originalBuildFeeCB" size="10" maxlength="10">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" name="cb_originalDeprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getOriginalDeprUnitCB())?"checked":"")%>>
			<input class="field_RO" type="hidden" name="originalDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOriginalDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別
			<select class="field_RO" type="select" name="originalDeprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field_RO" type="select" name="originalDeprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_RO" type="select" name="originalDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field_RO" type="select" name="originalDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprAccounts())%>
			</select>
			<br>
			殘值
			<input class="field_RO" type="text" id="originalScrapValue" name="originalScrapValue" size="10" maxlength="10" value="<%=obj.getOriginalScrapValue()%>">
			&nbsp;&nbsp;
			應攤提折舊總額
			<input class="field_RO" type="text" name="originalDeprAmount" size="20" maxlength="15" value="<%=obj.getOriginalDeprAmount()%>">
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="originalAccumDepr" size="20" maxlength="15" value="<%=obj.getOriginalAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			<input class="field_RO" type="text" name="originalApportionMonth" size="10" maxlength="3" value="<%=obj.getOriginalApportionMonth()%>">
			月提折舊金額
			[<input class="field_RO" type="text" name="originalMonthDepr" size="10" value="<%=obj.getOriginalMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="originalMonthDepr1" size="10" value="<%=obj.getOriginalMonthDepr1()%>">]
			攤提年限截止年月
			[<input class="field_RO" type="text" name="originalApportionEndYM" size="10" value="<%=obj.getOriginalApportionEndYM()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">目前折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法
			<select name="deprMethod" class="field_RO" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="buildFeeCB" name="buildFeeCB" size="10" maxlength="10">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="deprUnitCB" name="deprUnitCB" size="10" maxlength="10">
			部門別依比例分攤
			<br>
			園區別
			<select class="field_RO" type="select" name="deprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field_RO" type="select" name="deprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_RO" type="select" name="deprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field_RO" type="select" name="deprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprAccounts())%>
			</select>
			<br>
			殘值
			[<input class="field_RO" type="text" name="scrapValue" size="10" maxlength="10" value="<%=obj.getScrapValue()%>">]
			&nbsp;&nbsp;
			應攤提折舊總額
			[<input class="field_RO" type="text" name="deprAmount" size="20" maxlength="15" value="<%=obj.getDeprAmount()%>">]
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="accumDepr" size="20" maxlength="15" value="<%=obj.getAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="apportionMonth" size="10" maxlength="3" value="<%=obj.getApportionMonth()%>" >]
			月提折舊金額
			[<input class="field_RO" type="text" name="monthDepr" size="10" value="<%=obj.getMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="monthDepr1" size="10" value="<%=obj.getMonthDepr1()%>">]			
			攤提年限截止年月
			[<input class="field_RO" type="text" name="apportionEndYM" size="10" value="<%=obj.getApportionEndYM()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">減損資料：</td>
		<td class="td_form_white" colspan="3">
			減損日期：[<input class="field_RO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
		<br>
			減損原因：
			<select class="field_RO" type="select" name="reduceCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA'", obj.getReduceCause())%>
			</select>
			&nbsp;　其他說明：[<input class="field_RO" type="text" name="reduceCause1" size="10" maxlength="20" value="<%=obj.getReduceCause1()%>">]
		</td>
	</tr>	
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form" style="display:none">異動人員/日期：</td>
		<td class="td_form_white"style="display:none">
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
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
	<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
	<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
	
	
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
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">管理機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">電腦單號</a></th>
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產編號</a></th>-->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">增加原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">入帳</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">月結</a></th>
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',13,false);" href="#">財產性質</a></th>-->
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true};
	boolean displayArray[] = {true,true,true,false,true,true,true,true,true,true,true,false,false,false,false,false,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language='javascript'>
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		//刪除之前多出現一道確認訊息
       	case "delete":
		if(!confirm("刪除此增加單，將一併刪除其關聯的標的資料!"))
			return false;
			hasBeenConfirmed = true;
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[0].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
/*			if (parse(form1.q_signNo1.value).length<=0) {
				form1.q_signNo1.value="E000000";
				changeSignNo1("q_signNo1","q_signNo2","q_signNo3","E000000");
			}
*/
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
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly = true;
			}	
			//財產性質為「01:公務用」時，「基金財產」才可選。
			if(form1.propertyKind.value == "01") {
				document.all.fundType.disabled=false;
			}else{
				document.all.fundType.disabled=true;
				form1.fundType.value="";
			}
			//若權利為「股份,其他財產權利」開放原始設定價值
			var checkPropertyNo = form1.propertyNo.value;
			if(checkPropertyNo.substring(0,3) != "801" || checkPropertyNo.substring(0,3) != "806"){
				setFormItem("originalBV","R");
			}
			
			setFormItem("verify","R");
			break;
		case "insertError":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly = true;
			}	
			//財產性質為「01:公務用」時，「基金財產」才可選。
			if(form1.propertyKind.value == "01") {
				document.all.fundType.disabled=false;
			}else{
				document.all.fundType.disabled=true;
				form1.fundType.value="";
			}	
			//若權利為「股份,其他財產權利」開放原始設定價值
			var checkPropertyNo = form1.propertyNo.value;
			if(checkPropertyNo.substring(0,3) != "801" || checkPropertyNo.substring(0,3) != "806"){
				setFormItem("originalBV","R");
			}
			
			setFormItem("verify","R");
			break;
		//更新時要做的動作
		case "update":
			if(form1.verify.value=="Y"){
				document.getElementById("writeUnit").readOnly=true;
				document.getElementById("proofDoc").readOnly=true;
				document.getElementById("manageNo").readOnly=true;
				document.getElementById("summonsNo").readOnly=true;
				document.all.cause.disabled=true;
				document.getElementById("cause1").readOnly=true;
				//document.all.btn_enterDate.disabled=true;
				//document.getElementById("enterDate").readOnly=true;
				document.all.propertyKind.disabled=true;
				document.all.fundType.disabled=true;
				document.all.btn_buyDate.disabled=true;
				document.getElementById("buyDate").readOnly=true;
				document.all.sourceKind.disabled=true;
				document.all.btn_sourceDate.disabled=true;
				document.getElementById("sourceDate").readOnly=true;
				document.getElementById("sourceDoc").readOnly=true;
				document.getElementById("meat").readOnly=true;
				document.getElementById("proofDoc1").readOnly=true;
				setFormItem("originalBV","R");			
				document.getElementById("registerCause").readOnly=true;
				document.all.btn_registerDate.disabled=true;
				document.getElementById("registerDate").readOnly=true;
				document.getElementById("setPeriod").readOnly=true;
				document.getElementById("commonObligee").readOnly=true;
				document.getElementById("setPerson").readOnly=true;
				document.all.btn_payDate.disabled=true;
				document.getElementById("payDate").readOnly=true;
				document.getElementById("interest").readOnly=true;
				document.getElementById("rent").readOnly=true;
			}
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				form1.cause1.readOnly = true;
			}	
			//財產性質為「01:公務用」時，「基金財產」才可選。
			if(form1.propertyKind.value == "01") {
				document.all.fundType.disabled=false;
			}else{
				document.all.fundType.disabled=true;
				form1.fundType.value="";
			}	
			//若權利為「股份,其他財產權利」開放原始設定價值
			var checkPropertyNo = form1.propertyNo.value;
			if(checkPropertyNo.substring(0,3) != "801" || checkPropertyNo.substring(0,3) != "806"){
				setFormItem("originalBV","R");
			}
			
			break;
		case "updateError":
			if(form1.verify.value=="Y"){
			document.getElementById("writeUnit").readOnly=true;
			document.getElementById("proofDoc").readOnly=true;
			document.getElementById("manageNo").readOnly=true;
			document.getElementById("summonsNo").readOnly=true;
			document.all.cause.disabled=true;
			document.getElementById("cause1").readOnly=true;
			//document.all.btn_enterDate.disabled=true;
			//document.getElementById("enterDate").readOnly=true;
			document.all.propertyKind.disabled=true;
			document.all.fundType.disabled=true;
			document.all.btn_buyDate.disabled=true;
			document.getElementById("buyDate").readOnly=true;
			document.all.sourceKind.disabled=true;
			document.all.btn_sourceDate.disabled=true;
			document.getElementById("sourceDate").readOnly=true;
			document.getElementById("sourceDoc").readOnly=true;
			document.getElementById("meat").readOnly=true;
			document.getElementById("proofDoc1").readOnly=true;
			setFormItem("originalBV","R");			
			document.getElementById("registerCause").readOnly=true;
			document.all.btn_registerDate.disabled=true;
			document.getElementById("registerDate").readOnly=true;
			document.getElementById("setPeriod").readOnly=true;
			document.getElementById("commonObligee").readOnly=true;
			document.getElementById("setPerson").readOnly=true;
			document.all.btn_payDate.disabled=true;
			document.getElementById("payDate").readOnly=true;
			document.getElementById("interest").readOnly=true;
			document.getElementById("rent").readOnly=true;
			}
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				form1.cause1.readOnly = true;
			}	
			//財產性質為「01:公務用」時，「基金財產」才可選。
			if(form1.propertyKind.value == "01") {
				document.all.fundType.disabled=false;
			}else{
				document.all.fundType.disabled=true;
				form1.fundType.value="";
			}
			//若權利為「股份,其他財產權利」開放原始設定價值
			var checkPropertyNo = form1.propertyNo.value;
			if(checkPropertyNo.substring(0,3) != "801" || checkPropertyNo.substring(0,3) != "806"){
				setFormItem("originalBV","R");
			}
			
			break;
	}
}
</script>
</body>
</html>



