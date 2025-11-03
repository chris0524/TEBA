<!--
程式目的：有價證券主檔資料維護-增加單資料
程式代號：untvp001f
程式日期：0940920
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP001F">
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
	obj = (unt.vp.UNTVP001F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	obj = (unt.vp.UNTVP001F)obj.queryOne();
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._VP_ADD, 3);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._VP_ADD, 2);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._VP_ADD, 2);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._VP_ADD, 2);
}else if("untch001f02extend01".equals(obj.getProgID())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._LA_ADD, 2);
}else if("untch001f02extend02".equals(obj.getProgID())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._LA_ADD, 2);
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
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("writeUnit","<%=user.getUnitName()%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"E1")%>"),
	//new Array("enterDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("ownership","1"),
	new Array("dataState","1"),
	new Array("verify","N"),
	//new Array("originalSheet","0"),
	new Array("originalAmount","0"),
	new Array("originalBV","0"),
	//new Array("bookSheet","0"),
	new Array("bookAmount","0"),
	new Array("bookValue","0"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate", <%=util.Datetime.getYYYMMDD()%>)
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
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.propertyNo,"財產編號");		
		
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.enterDate,"若要入帳，入帳日期");
		}
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.securityName,"發行法人名稱");
		alertStr += checkYYYMM(form1.securityTime,"發行法人設立年月");
		alertStr += checkEmpty(form1.originalAmount,"原始總股數");
		alertStr += checkFloat(form1.originalAmount,"原始總股數",10,0);		
		alertStr += checkEmpty(form1.originalBV,"原始總價");
		alertStr += checkFloat(form1.originalAmount,"原始總價",15,0);		
		alertStr += checkEmpty(form1.bookAmount,"總股數");
		alertStr += checkFloat(form1.bookAmount,"總股數",10,0);		
		alertStr += checkEmpty(form1.bookValue,"總價");
		alertStr += checkFloat(form1.bookValue,"總價",15,0);	
		if(parse(form1.capitalStock.value).length>0){
		var capitalStockCheck = form1.capitalStock.value;
			alertStr += checkInt(form1.capitalStock,"發行股份總數");
			if(capitalStockCheck<=0){
				alertStr += "發行股份總數必須大於0\n";
			}
		}
		alertStr += checkLen(form1.notes, "備註", 250);
		form1.bookValue.value =  form1.originalBV.value;
		if(form1.cause.value=="99") alertStr += checkEmpty(form1.cause1,"其他說明");
		else form1.cause1.value = "";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,serialNo){
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
function changeSelect(){
	//當增加原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "99") form1.cause1.readOnly = false;
	else{
		form1.cause1.readOnly = true;
		form1.cause1.value="";
	}
	//財產性質為「01:事業用」時，須控制「基金財產」必須有資料
	if(form1.propertyKind.value == "01") document.all.fundType.disabled=false;
	else{
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
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		
		if (surl=="../ch/untch001f02.jsp"){
			if('<%=obj.getIsAddProof()%>' != 'true'){
				form1.state.value="queryAll";
			}
		} else {
	        form1.state.value = "queryAll";
		}
		if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
			form1.mainPage1.value="";
			form1.currentPage.value=form1.mainPage.value;
		}
			
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.action=surl+"?initDtl=Y";
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	//若是增加單資料的資料狀態已減損則不可修改、刪除該筆增加單資料
//	if (form1.dataState.value=="2"){
//		setFormItem("update,delete", "R");
//	}
	//查出的「增加單資料」若「已入帳」或「已月結」，均不允許刪除該筆增加單資料
//	if (form1.verify.value=="Y" || form1.closing.value=="Y"){
//		setFormItem("delete", "R");
//	}
//	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
//		setFormItem("update,delete","R");
//	}
	
	setDisplayItem("spanQueryAll,spanInsert,spanNextInsert","H");
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}	
	
	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeepUnitQuickly, form1.originalKeepUnit, form1.originalUseUnitQuickly, form1.originalUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeeperQuickly, form1.originalKeeper, form1.originalUserQuickly, form1.originalUser, true, false);
	var dcc3 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keepUnitQuickly, form1.keepUnit, form1.useUnitQuickly, form1.useUnit, true, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keeperQuickly, form1.keeper, form1.userNoQuickly, form1.userNo, true, false);
	
	if(form1.verify.value === 'Y') {
        setFormItem("update,delete", "R");
    }
}

function goUntvp004r(){
	var url = "untvp004p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo.value + "&q_caseNoE="+form1.caseNo.value +
		"&q_kind=4";
	window.open(url);
}

function goUntvp005r(){
	var url = "untvp005p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo.value + "&q_caseNoE="+form1.caseNo.value ;
	window.open(url);
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
  <%=tabs %>
</TABLE>
<input type="hidden" name="checkVerify" value="<%=obj.getVerify()%>">
<input type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
<!--Query區============================================================-->
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer2" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame2"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
</div>
<!-- 保留第一頁查詢條件與頁數使用 -->
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="18%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　　權屬：
			<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			&nbsp;　　
			<input class="field_PRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
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
			<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"9")%>
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
		<td class="td_form"><font color="red">*</font>購置日期：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View.getPopCalndar("field","buyDate",obj.getBuyDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">增加原因：</td>
		<td class="td_form_white" colspan="3">
			<%=uctls.getCause("field","cause",obj.getCause(),obj.getCauseName(),"1,4")%>
			&nbsp;　　　其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		    &nbsp;&nbsp; 內容：<input class="field" type="text" name="securityMeat" size="20" maxlength="20" value="<%=obj.getSecurityMeat()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">發行法人資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>名稱：<input class="field" type="text" name="securityName" size="29" maxlength="15" value="<%=obj.getSecurityName()%>">
			　　　　設立年月：<input class="field" type="text" name="securityTime" size="5" maxlength="5" value="<%=obj.getSecurityTime()%>">
		<br>
			核准登記機關：<%=util.View.getPopOrgan("field","securityOrg",obj.getSecurityOrg(),obj.getSecurityOrgName(),"Y")%>
			&nbsp;&nbsp;&nbsp;核准登記字號：<input class="field" type="text" name="securityDoc" size="15" maxlength="20" value="<%=obj.getSecurityDoc()%>">
		<br>
			事業項目：<input class="field" type="text" name="securityItem" size="20" maxlength="25" value="<%=obj.getSecurityItem()%>">
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　　
		<br>
		          會計科目：<input class="field" type="text" name="accountingTitle" size="33" maxlength="40" value="<%=obj.getAccountingTitle()%>">
			&nbsp;&nbsp;&nbsp;&nbsp;
			公司資本額：
			<input class="field" type="text" name="capital" size="20" maxlength="20" value="<%=obj.getCapital()%>">
		<br>
			地址：<input class="field" type="text" name="securityAddr" size="37" maxlength="50" value="<%=obj.getSecurityAddr()%>">
			&nbsp;&nbsp;發行股份總數：<input class="field" type="text" name="capitalStock" size="10" maxlength="15" value="<%=obj.getCapitalStock()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">股份資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>原始總股數：<input class="field" type="text" name="originalAmount" size="10" maxlength="12" value="<%=obj.getOriginalAmount()%>">
			&nbsp;　　　　　　總股數：[<input class="field_RO" type="text" name="bookAmount" size="10" maxlength="12" value="<%=obj.getBookAmount()%>">]
		<br>	
			<font color="red">*</font>原始總價：<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">
			&nbsp;&nbsp;&nbsp;　　　　　總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		<br>	
			原始入帳摘要：<input class="field" type="text" name="originalNote" size="20" maxlength="20" value="<%=obj.getOriginalNote()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">原始移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalKeepUnit", "originalKeepUnitQuickly", obj.getOriginalKeepUnit()) %>
			<input class="field_Q" type="button" name="btn_originalKeepUnit" onclick="popUnitNo(form1.organID.value,'originalKeepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalKeeper", "originalKeeperQuickly", obj.getOriginalKeeper()) %>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'originalKeeper')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalUseUnit", "originalUseUnitQuickly", obj.getOriginalUseUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'originalUseUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalUser", "originalUserQuickly", obj.getOriginalUser()) %>
	        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,originalUser)" value="..." title="人員輔助視窗">
	        <br/>
			使用註記
			<input type="text" class="field" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20">
	        <br>
			移動日期
	         <%=util.View.getPopCalndar("field","originalMoveDate",obj.getOriginalMoveDate())%>
	         <br>
	         存置地點
			<input class="field" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1() %>">
			<input class="field" type="button" name="btn_originalPlace1" onclick="popPlace('originalPlace')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name() %>">]
			<br>		
			存置地點說明
			<input class="field" type="text" name="originalPlace" size="30" maxlength="30" value="<%=obj.getOriginalPlace() %>">
		</td>
	</tr>
	<tr>
		<td class="td_form">目前移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "keepUnit", "keepUnitQuickly", obj.getKeepUnit()) %>
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
	        <br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "useUnit", "useUnitQuickly", obj.getUseUnit()) %>
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "userNo", "userNoQuickly", obj.getUserNo()) %>
	        <br/>
			使用註記
			[<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">]
	        <br>
			移動日期
			[<input class="field_RO" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate() %>">]
			<br>
			存置地點
			[<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>">]
			[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
			<br>		
			存置地點說明
			[<input class="field_RO" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace() %>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">減損資料：</td>
		<td class="td_form_white" colspan="3">
			減損日期：[<input class="field_RO" type="text" name="reduceDate" size="15" maxlength="7" value="<%=obj.getReduceDate()%>">  ] <br>
			減損原因：<select class="field_RO" type="select" name="reduceCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getReduceCause())%>
			</select>　
			其他說明：[ <input class="field_RO" type="text" name="reduceCause1" size="20" maxlength="20" value="<%=obj.getReduceCause1()%>">]			
		</td>
	</tr>
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form"style="display:none;">異動人員/日期：</td>
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">增加原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">發行法人名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">總股數</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">總價</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,false,false,true,true,true,true,true,true,true,true};
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
		if(!confirm("刪除此增加單，將一併刪除其關聯的股份資料、資本額更動記錄!"))
			return false;
			hasBeenConfirmed = true;
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (parse(form1.q_checkQuery).length<=0)form1.q_checkQuery.value="Y";
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
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly = true;
			}else form1.cause1.readOnly = false;
			//財產性質為「03:事業用」時，須控制「基金財產」必須有資料
			if(form1.propertyKind.value != "03"){
				document.all.fundType.disabled=true;	
			}else document.all.fundType.disabled=false;	
			setFormItem("verify","R");
			break;
		case "insertError":
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly = true;
			}else form1.cause1.readOnly = false;
			//財產性質為「03:事業用」時，須控制「基金財產」必須有資料
			if(form1.propertyKind.value != "03"){
				document.all.fundType.disabled=true;	
			}else document.all.fundType.disabled=false;	
			setFormItem("verify","R");
			break;
		//更新時要做的動作
		case "update":
			if(form1.verify.value=="Y"){
				document.getElementById("writeUnit").readOnly=true;
				document.all.btn_writeDate.disabled=true;
				document.getElementById("writeDate").readOnly=true;
				document.getElementById("proofDoc").readOnly=true;
				document.getElementById("manageNo").readOnly=true;
				document.getElementById("summonsNo").readOnly=true;
				document.all.cause.disabled=true;
				document.getElementById("cause1").readOnly=true;
				//document.all.btn_enterDate.disabled=true;
				//document.getElementById("enterDate").readOnly=true;
				document.all.propertyKind.disabled=true;
				document.all.fundType.disabled=true;
				document.getElementById("securityName").readOnly=true;
				document.getElementById("securityAddr").readOnly=true;
				document.getElementById("securityItem").readOnly=true;
				document.getElementById("securityTime").readOnly=true;
				document.all.btn_securityOrg.disabled=true;
				document.getElementById("securityOrg").readOnly=true;
				document.getElementById("securityDoc").readOnly=true;
			}
			
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				form1.cause1.readOnly = true;
			}else form1.cause1.readOnly = false;
			//財產性質為「03:事業用」時，須控制「基金財產」必須有資料
			if(form1.propertyKind.value != "03"){
				document.all.fundType.disabled=true;
			}else document.all.fundType.disabled=false;
			break;
		case "updateError":
			if(form1.verify.value=="Y"){
			document.getElementById("writeUnit").readOnly=true;
			document.all.btn_writeDate.disabled=true;
			document.getElementById("writeDate").readOnly=true;
			document.getElementById("proofDoc").readOnly=true;
			document.getElementById("manageNo").readOnly=true;
			document.getElementById("summonsNo").readOnly=true;
			document.all.cause.disabled=true;
			document.getElementById("cause1").readOnly=true;
			//document.all.btn_enterDate.disabled=true;
			//document.getElementById("enterDate").readOnly=true;
			document.all.propertyKind.disabled=true;
			document.all.fundType.disabled=true;
			document.getElementById("securityName").readOnly=true;
			document.getElementById("securityAddr").readOnly=true;
			document.getElementById("securityItem").readOnly=true;
			document.getElementById("securityTime").readOnly=true;
			document.all.btn_securityOrg.disabled=true;
			document.getElementById("securityOrg").readOnly=true;
			document.getElementById("securityDoc").readOnly=true;
			}
			
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value !="99"){
				form1.cause1.readOnly = true;
			}else form1.cause1.readOnly = false;
			//財產性質為「03:事業用」時，須控制「基金財產」必須有資料
			if(form1.propertyKind.value != "03"){
				document.all.fundType.disabled=true;
			}else document.all.fundType.disabled=false;
			break;
	}
}
</script>
</body>
</html>



