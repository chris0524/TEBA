<!--
程式目的：土地合併分割作業－合併增加單明細
程式代號：untla031f
程式日期：0940926
程式作者：carey
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA031F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA031F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
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
<script language="javascript" src="../../js/unitProperty.js"></script>
<script language="javascript" src="untla027q.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("closing","N"),
	new Array("nonProof","N"),
	new Array("proofVerify","N"),
	new Array("manageOrg","<%=user.getOrganID()%>"),
	new Array("manageOrgName","<%=user.getOrganName()%>"),
	new Array("enterDate","<%=obj.getMergeDivisionDate()%>"),
	new Array("originalHoldNume","1"),
	new Array("originalHoldDeno","1"),	
	new Array("nonProof","Y")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"土地增加單-電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號－類項目節");
		alertStr += checkEmpty(form1.signNo1,"土地標示代碼－縣市");
		alertStr += checkEmpty(form1.signNo2,"土地標示代碼－鄉鎮市區");
		alertStr += checkEmpty(form1.signNo3,"土地標示代碼－地段");
		alertStr += checkEmpty(form1.signNo4,"土地標示代碼－地號(母號)");
		alertStr += checkEmpty(form1.signNo5,"土地標示代碼－地號(子號)");
		alertStr += checkEmpty(form1.cause,"增加原因");
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.reduceDate,"減損日期");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		alertStr += checkEmpty(form1.taxCredit,"抵繳遺產稅");
		alertStr += checkEmpty(form1.grass,"新草衙");
		alertStr += checkEmpty(form1.originalArea,"原始整筆面積(㎡)");
		alertStr += checkFloat(form1.originalArea,"原始整筆面積(㎡)",9,2);
		alertStr += checkEmpty(form1.originalHoldNume,"原始權利範圍－分子");
		alertStr += checkInt(form1.originalHoldNume,"原始權利範圍－分子");
		alertStr += checkEmpty(form1.originalHoldDeno,"原始權利範圍－分母");
		alertStr += checkInt(form1.originalHoldDeno,"原始權利範圍－分母");
		alertStr += checkFloat(form1.originalHoldArea,"原始權利範圍面積(㎡)",9,2);
		alertStr += checkFloat(form1.area,"整筆面積(㎡)",9,2);
		alertStr += checkInt(form1.holdNume,"權利範圍－分子");
		alertStr += checkInt(form1.holdDeno,"權利範圍－分母");
		alertStr += checkFloat(form1.holdArea,"權利範圍面積(㎡)",9,2);		
		alertStr += checkYYYMM(form1.originalDate,"原始入帳－公告年月");
		alertStr += checkEmpty(form1.originalUnit,"原始入帳－單價");
		//alertStr += checkInt(form1.originalUnit,"原始入帳－單價");
		alertStr += checkFloat(form1.originalUnit,"原始入帳－單價",13,2);
		if(parseInt(form1.originalBV.value)<=0){
			alertStr += "原始入帳－總價必須大於0\n";
			form1.originalBV.style.backgroundColor = errorbg;
		}else{
			form1.originalBV.style.backgroundColor = "";
		}
		alertStr += checkInt(form1.originalBV,"原始入帳－總價");
		//alertStr += checkInt(form1.bookUnit,"帳面金額－單價");
		alertStr += checkFloat(form1.bookUnit,"帳面金額－單價",13,2);
		alertStr += checkInt(form1.bookValue,"帳面金額－總價");
		alertStr += checkDate(form1.ownershipDate,"所有權登記日期");
		alertStr += checkEmpty(form1.nonProof,"權狀");
		alertStr += checkLen(form1.notes,"備註",250);
		if(form1.propertyKind.value=="03") alertStr += checkEmpty(form1.fundType,"基金財產");
		else form1.fundType.value = "";
		if(form1.cause.value=="499") alertStr += checkEmpty(form1.cause1,"其他說明");
		else form1.cause1.value = "";
		//若「使用分區」為「A」開頭時，「編定使用種類」必須有資料
		if((form1.useSeparate.value).substr(0,1)=="A") alertStr += checkEmpty(form1.useKind,"編定使用種類");
		else form1.useKind.value ="";
		//若「權狀」為「Y:有權狀」，「權狀字號」必須有資料
		if(form1.nonProof.value=="Y") alertStr += checkEmpty(form1.proofDoc,"權狀字號");
		else { form1.proofDoc.value =""; form1.proofDoc.style.backgroundColor = ""; }
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}


function changeSelect(){
/*
	//當增加原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "499")
		form1.cause1.readOnly = false;
	else form1.cause1.readOnly = true;
*/
	//當權狀選「Y」時，權狀字號欄位必須有資料	
	if(form1.nonProof.value == "N") { form1.proofDoc.value=""; form1.proofDoc.readOnly = true; form1.proofDoc.style.backgroundColor = "";}
	else form1.proofDoc.readOnly = false;
	//若「使用分區」為「A」開頭時，「編定使用種類」必須有資料
	if((form1.useSeparate.value).substr(0,1) == "A")
		form1.useKind.disabled = false;
	else form1.useKind.disabled = true;			 

	form1.oriUseSeparate.valeue = form1.useSeparate.valeue;
	form1.oriUseKind.valeue = form1.useKind.valeue;
}
//更新面積,金額等資料
function changeArea(check){
	var holdArea;
	if(parse(form1.originalArea.value).length>0 && parse(form1.originalHoldNume.value).length>0 && parse(form1.originalHoldDeno.value).length>0){
		holdArea = roundp((form1.originalArea.value * form1.originalHoldNume.value / form1.originalHoldDeno.value),2,"Y") ;
		form1.area.value = form1.originalArea.value;
		form1.holdNume.value = form1.originalHoldNume.value;
		form1.holdDeno.value = form1.originalHoldDeno.value;
		form1.holdArea.value = parseFloat(holdArea);
		form1.originalHoldArea.value = parseFloat(holdArea);
	}else{
		form1.area.value = "";
		form1.holdNume.value = "";
		form1.holdDeno.value = "";
		form1.holdArea.value = "";
		form1.originalHoldArea.value = "";
	}
	//帳面金額 = 原始入帳金額
	
	if (parse(form1.originalHoldArea.value).length>0 && (check=="originalUnit" || check=="originalArea" || check=="originalHoldNume" ||check=="originalHoldDeno")){
		form1.originalBV.value = Math.round(form1.originalUnit.value * form1.originalHoldArea.value);
		form1.bookUnit.value = form1.originalUnit.value;
		form1.bookValue.value = form1.originalBV.value;
	}
	if (parse(form1.originalHoldArea.value).length>0 && check=="addFour"){		
		form1.originalBV.value = Math.round(form1.originalUnit.value * form1.originalHoldArea.value * 1.4);
		form1.bookUnit.value = form1.originalUnit.value;
		form1.bookValue.value = form1.originalBV.value;
	}		
	if (parse(form1.originalHoldArea.value).length>0 && check=="originalBV"){
		form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalHoldArea.value),2,"Y");
		form1.bookUnit.value = form1.originalUnit.value;
		form1.bookValue.value = form1.originalBV.value;
	}
	
	//if(parse(form1.originalUnit.value).length>0 && parse(form1.originalHoldArea.value).length>0){
		//form1.originalBV.value = parseInt(roundp(form1.originalUnit.value * form1.originalHoldArea.value,0,"Y"));
		//form1.bookUnit.value = parseInt(form1.originalUnit.value);
		//form1.bookValue.value = parseInt(form1.originalBV.value);
	//}else{
		//form1.originalBV.value = "";
		//form1.bookUnit.value = "";
		//form1.bookValue.value = "";
	//}
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.addCaseNo.value=caseNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	document.all.item("spanQueryAll").style.display="none";
	if(parse(form1.addCaseNo.value).length<=0){
		setFormItem("insert", "R");
	}else{
		setFormItem("insert", "O");
	}
	if ("<%=obj.getMergeDivisionVerify()%>"=="Y") {
		setFormItem("insert,update,delete", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete","R");
	}	
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untla027f.jsp');">案件資料</a></td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla028f.jsp');">合併<br>減損單</a></td>		
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla029f.jsp');">合併<br>減損單明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla030f.jsp');">合併<br>增加單</a></td>
		<td ID=t5 CLASS="tab_border1">合併<br>增加單明細</td>
		<td ID=t6 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla032f.jsp');">合併增加單<br>管理資料</a></td>
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla034f.jsp');">分割<br>減損單</a></td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla035f.jsp');">分割<br>減損單明細</a></td>
		<td ID=t9 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla036f.jsp');">分割<br>增加單</a></td>
		<td ID=t10 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla037f.jsp');">分割<br>增加單明細</a></td>
		<td ID=t11 CLASS="tab_border2">分割增加單<br>管理資料</td>
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
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>		
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="hidden" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
<input class="field_QRO" type="hidden" name="mergeDivision" size="10" maxlength="10" value="<%=obj.getMergeDivision()%>">
<input class="field_QRO" type="hidden" name="mergeReduce" size="10" maxlength="10" value="<%=obj.getMergeReduce()%>">
<input class="field_QRO" type="hidden" name="mergeAdd" size="10" maxlength="10" value="<%=obj.getMergeAdd()%>">
<input class="field_QRO" type="hidden" name="divisionReduce" size="10" maxlength="10" value="<%=obj.getDivisionReduce()%>">
<input class="field_QRO" type="hidden" name="divisionAdd" size="10" maxlength="10" value="<%=obj.getDivisionAdd()%>">
<input class="field_QRO" type="hidden" name="mergeDivisionDate" size="7" maxlength="7" value="<%=obj.getMergeDivisionDate()%>">
<input class="field_QRO" type="hidden" name="approveOrg" value="<%=obj.getApproveOrg()%>">
<input class="field_QRO" type="hidden" name="approveDate" value="<%=obj.getApproveDate()%>">
<input class="field_QRO" type="hidden" name="approveDoc" size="38" maxlength="20" value="<%=obj.getApproveDoc()%>">
<input type="hidden" name="check" value="">
<!-- 
<input type="hidden" name="originalBasis" value="">
 -->
<input class="field_QRO" type="hidden" name="mergeDivisionVerify" value="<%=obj.getMergeDivisionVerify()%>">
<input class="field_QRO" type="hidden" name="reduceCaseNo" value="<%=obj.getReduceCaseNo()%>">
<input class="field_QRO" type="hidden" name="reduceCaseNo1" value="<%=obj.getReduceCaseNo1()%>">
<input class="field_QRO" type="hidden" name="addCaseNo1" value="<%=obj.getAddCaseNo1()%>">
<input type="hidden" class="field" name="oriUseSeparate" value="<%=obj.getOriUseSeparate()%>"><!--原始使用分區-->
<input type="hidden" class="field" name="oriUseKind" value="<%=obj.getOriUseKind()%>"><!--原始編定使用種類-->	
<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA027Q",obj);%>
	<jsp:include page="untla027q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" colspan="4" style="text-align:center;">
			<input class="toolbar_default" type="button" name="btn_mergeReduce" onclick="popUntlaReduceDetail(form1.enterOrg.value,form1.ownership.value,form1.reduceCaseNo.value);" value="合併減損單明細挑選" title="土地減損單明細輔助視窗" disabled=true>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="14%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　　權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>			
			&nbsp;&nbsp;&nbsp;　電腦單號：
			[<input class="field_QRO" type="text" name="addCaseNo" size="15" maxlength="10" value="<%=obj.getAddCaseNo()%>">]
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
			　 月結：
			<select class="field_RO" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		</td>		
	</tr>	
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"1")%>
			　分號：[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
		<br>
			別名：<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			&nbsp;　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_P" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>
			<select class="field_P" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>		
			<select class="field_P" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>	
			　地號：	
			<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>">
			&nbsp;-
			<input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">街路名：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="doorplate" size="30" maxlength="40" value="<%=obj.getDoorplate()%>">
			　　　新草衙：
			<select class="field_RO" type="select" name="grass">
				<%=util.View.getYNOption(obj.getGrass())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind" onChange="changeSelect();">
			<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
			</select>
			&nbsp;　　　　　　　　　基金財產：
			<select class="field_RO" type="select" name="fundType" disabled="true">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">珍貴財產：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			&nbsp;　　　　　　　　抵繳遺產稅：
			<select class="field_RO" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>	
		</td>
	</tr>
	<tr>
		<td class="td_form">增加原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_QRO" type="select" name="cause" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' and codeCon1 in ('1','3','4')", obj.getCause())%>
			</select>
			&nbsp;&nbsp;　　　　　　　其它說明：[<input class="field_QRO" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>" readonly="true">]
		</td>
	</tr>
	<tr>
		<td class="td_form">經費來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="fundsSource">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
			</select>
			&nbsp;&nbsp;　　　　　　　會計科目：<input class="field" type="text" name="accountingTitle" size="10" maxlength="10" value="<%=obj.getAccountingTitle()%>">
		</td>		
	</tr>	
	<tr>
		<td class="td_form">財產來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="sourceKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKD' ", obj.getSourceKind())%>
			</select>
			&nbsp;&nbsp;財產取得日期：<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
			&nbsp;&nbsp;財產取得文號：<input class="field" type="text" name="sourceDoc" size="20" maxlength="20" value="<%=obj.getSourceDoc()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">原有人：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="oldOwner" size="30" maxlength="30" value="<%=obj.getOldOwner()%>">
		</td>		
	</tr>
	<tr>
		<td class="td_form">管理機關：</td>
		<td class="td_form_white" colspan="3">
				<%=util.View.getPopOrgan("field","manageOrg",obj.getManageOrg(),obj.getManageOrgName(),"Y")%>
			&nbsp;&nbsp;使用情形：
			<select class="field_RO" type="select" name="useState">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UST' ", obj.getUseState())%>
			</select>
		</td>	
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>原始面積：</td>
		<td class="td_form_white" colspan="3">
			總面積：<input class="field" type="text" name="originalArea" size="9" maxlength="9" value="<%=obj.getOriginalArea()%>" onChange="changeArea('originalArea');">
		<br>
			權利分子：<input class="field" type="text" name="originalHoldNume" size="10" maxlength="10" value="<%=obj.getOriginalHoldNume()%>" onChange="changeArea('originalHoldNume');">
			&nbsp;&nbsp;權利分母：<input class="field" type="text" name="originalHoldDeno" size="10" maxlength="10" value="<%=obj.getOriginalHoldDeno()%>" onChange="changeArea('originalHoldDeno');">
			&nbsp;&nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="originalHoldArea" size="9" maxlength="9" value="<%=obj.getOriginalHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">目前面積：</td>
		<td class="td_form_white" colspan="3">
			總面積：[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">]
		<br>
			權利分子：[<input class="field_RO" type="text" name="holdNume" size="9" maxlength="10" value="<%=obj.getHoldNume()%>">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="holdDeno" size="9" maxlength="10" value="<%=obj.getHoldDeno()%>">]
			&nbsp;&nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
		</td>
	</tr>		
	<tr>
		<td class="td_form">原始入帳：</td>
		<td class="td_form_white" colspan="3">
			依據：
			<select class="field" type="select" name="originalBasis">
				<%=util.View.getTextOption("1;公告地價;2;公告現值;3;取得價格",obj.getOriginalBasis())%>							
			</select>
			&nbsp;&nbsp;&nbsp;　　公告年月：<input class="field" type="text" name="originalDate" size="5" maxlength="5" value="<%=obj.getOriginalDate()%>">&nbsp;&nbsp;&nbsp;
			<input class="toolbar_default" type="button" name="addFour" value="現值加四成" onClick="changeArea('addFour');">
		<br>			
			<font color="red">*</font>單價：<input class="field" type="text" name="originalUnit" size="13" maxlength="16" value="<%=obj.getOriginalUnit()%>" onChange="changeArea('originalUnit');">
			&nbsp;　總價：<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" onChange="changeArea('originalBV');">
			&nbsp;　摘要：<input class="field" type="text" name="originalNote" size="15" maxlength="15" value="<%=obj.getOriginalNote()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">帳面金額：</td>
		<td class="td_form_white" colspan="3">
			單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="16" value="<%=obj.getBookUnit()%>">]
			&nbsp;　總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		</td>		
	</tr>
	<tr>
		<td class="td_form">權狀資料：</td>
		<td class="td_form_white" colspan="3">
			所有權登記日期：<%=util.View.getPopCalndar("field","ownershipDate",obj.getOwnershipDate())%>
			&nbsp;&nbsp;所有權登記原因：
			<select class="field" type="select" name="ownershipCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='OCA' ", obj.getOwnershipCause())%>
			</select>
		<br>
			<font color="red">*</font>權狀：
			<select class="field" type="select" name="nonProof" onChange="changeSelect();">
				<%=util.View.getTextOption("Y;有;N;無",obj.getNonProof())%>
			</select>				
			&nbsp;&nbsp;　權狀字號：<input class="field" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">
			&nbsp;&nbsp;　權狀審核：
			<select class="field_RO" type="select" name="proofVerify">
				<%=util.View.getYNOption(obj.getProofVerify())%>
			</select>	
		<br>	
			其他登記事項：
			<textarea class="field" type="text" name="ownershipNote" cols="58" rows="1"><%=obj.getOwnershipNote()%></textarea>
		</td>
	</tr>
	<tr>
		<td class="td_form">使用分區：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="useSeparate" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
			</select>
			&nbsp;&nbsp;&nbsp;　　　編定使用種類：
			<select class="field" type="select" name="useKind" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getUseKind())%>			
			</select>
		</td>			
	</tr>
	<tr>
		<td class="td_form">地目：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="field">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
			</select>
			&nbsp;　　　　　　　　　等則：<input class="field" type="text" name="landRule" size="2" maxlength="2" value="<%=obj.getLandRule()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">減損資料：</td>
		<td class="td_form_white" colspan="3">
			減損日期：[<input class="field_RO" type="text" name="reduceDate" size="15" maxlength="7" value="<%=obj.getReduceDate()%>">]
		<br>
			減損原因：
			<select class="field_RO" type="select" name="reduceCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' ", obj.getReduceCause())%>
			</select>		
			&nbsp;&nbsp;　　其他說明：[<input class="field_RO" type="text" name="reduceCause1" size="20" maxlength="20" value="<%=obj.getReduceCause1()%>">]
		</td>		
	</tr>	
	<tr>
		<td class="td_form">資產重估日：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="appraiseDate" size="7" maxlength="7" value="<%=obj.getAppraiseDate()%>">]
		</td>								
	</tr>
	<tr>	
		<td class="td_form">處分作業：</td>
		<td class="td_form_white" colspan="3">
			方式：
			<select class="field_RO" type="select" name="proceedType">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PRO' ", obj.getProceedType())%>
			</select>
			&nbsp;　　日期(起)：[<input class="field_RO" type="text" name="proceedDateS" size="7" maxlength="7" value="<%=obj.getProceedDateS()%>">]&nbsp;~&nbsp;
			             (訖)：[<input class="field_RO" type="text" name="proceedDateE" size="7" maxlength="7" value="<%=obj.getProceedDateE()%>">]
		</td>					
	</tr>	
	<tr>			
		<td class="td_form">其他事項：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="notes1" size="38" maxlength="60" value="<%=obj.getNotes1()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form" width="22%">備註：</td>
		<td class="td_form_white" width="30%">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form" width="25%">異動人員/日期：</td>
		<td class="td_form_white">
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
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增加原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">財產性質</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,false,true,true,false,false,false,false,true};
	boolean displayArray[] = {false,false,true,true,false,false,true,true,true,true,false};
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
		if(!confirm("刪除此筆土地，將一併刪除其關聯的管理資料。\n\n您確定要刪除?"))
			return false;
			hasBeenConfirmed = true;
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
			setFormItem("btn_mergeReduce","O");
			setFormItem("btn_propertyNo,propertyNo,signNo1,signNo2,signNo3,signNo4,signNo5","R");
			break;
		case "insertError":
			setFormItem("btn_mergeReduce","O");
			setFormItem("btn_propertyNo,propertyNo,signNo1,signNo2,signNo3,signNo4,signNo5","R");
			break;
			
		//取消時要執行的動作
		case "clear":
			setFormItem("btn_mergeReduce","R");
			break;
		case "clearError":
			setFormItem("btn_mergeReduce","R");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("btn_mergeReduce","R");
			break;
		case "confirmError":
			setFormItem("btn_mergeReduce","R");
			break;
	}
}
</script>
</body>
</html>



