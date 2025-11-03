<!--
程式目的：土地合併分割作業－合併減損單明細
程式代號：untla029f
程式日期：0940906
程式作者：carey
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA029F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA029F)obj.queryOne();	
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="untla027q.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("signNo1", "E000000"),
	new Array("reduceDate","<%=obj.getMergeDivisionDate()%>"),
	new Array("closing","N")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.signNo1,"土地標示－縣市");
		alertStr += checkEmpty(form1.signNo2,"土地標示－鄉鎮市區");
		alertStr += checkEmpty(form1.signNo3,"土地標示－地段");
		alertStr += checkEmpty(form1.signNo4,"土地標示－地號母號");
		alertStr += checkEmpty(form1.signNo5,"土地標示－地號子號");
		alertStr += checkEmpty(form1.cause,"減損原因");
		alertStr += checkEmpty(form1.reduceDate,"減損日期");
		alertStr += checkDate(form1.reduceDate,"減損日期");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		alertStr += checkEmpty(form1.taxCredit,"抵繳遺產稅");
		alertStr += checkEmpty(form1.grass,"新草衙");
		alertStr += checkEmpty(form1.area,"整筆面積(㎡)");
		alertStr += checkFloat(form1.area,"整筆面積(㎡)",9,2);
		alertStr += checkEmpty(form1.holdNume,"權利範圍－分子");
		alertStr += checkInt(form1.holdNume,"權利範圍－分子");
		alertStr += checkEmpty(form1.holdDeno,"權利範圍－分母");
		alertStr += checkInt(form1.holdDeno,"權利範圍－分母");
		alertStr += checkEmpty(form1.holdArea,"權利範圍面積(㎡)");
		alertStr += checkFloat(form1.holdArea,"權利範圍面積(㎡)",9,2);
		alertStr += checkEmpty(form1.bookUnit,"帳面金額－單價");
		//alertStr += checkInt(form1.bookUnit,"帳面金額－單價");
		alertStr += checkFloat(form1.bookUnit,"帳面金額－單價",13,2);
		alertStr += checkEmpty(form1.bookValue,"帳面金額－總價");
		alertStr += checkInt(form1.bookValue,"帳面金額－總價");
		alertStr += checkDate(form1.bulletinDate,"當期公告日期");
		//alertStr += checkInt(form1.valueUnit,"當期公告地價");
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	document.all.item("spanQueryAll").style.display="none";
	if(parse(form1.reduceCaseNo.value).length<=0){
		setFormItem("insert", "R");
	}else if(parse(form1.reduceCaseNo.value).length>0 && form1.state.value!="insertError" && form1.state.value!="updateError"){
		setFormItem("insert", "O");
	}
	if ("<%=obj.getMergeDivisionVerify()%>"=="Y") {
		setFormItem("insert,update,delete,batchInsertBut", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}	
}

function checkValue(){
	var checkCaseNo = form1.caseNo.value;
	var checkPropertyNo = form1.propertyNo.value;
	var checkSerialNo = form1.serialNo.value;
	var checkSignNo1 = form1.signNo1.value;
	var checkSignNo2 = form1.signNo2.value;
	var checkSignNo3 = form1.signNo3.value;
	var checkSignNo4 = form1.signNo4.value;
	var checkSignNo5 = form1.signNo5.value;
if(checkPropertyNo.length>0 && checkSerialNo.length>0 && checkSignNo1.length>0 && checkSignNo2.length>0 && checkSignNo3.length>0 && checkSignNo4.length>0 && checkSignNo5.length>0){

}else if(checkPropertyNo.length>0 || checkSerialNo.length>0){
	setFormItem("signNo1,signNo2,signNo3,signNo4,signNo5","R");
}else if(checkSignNo1.length>0 || checkSignNo2.length>0 || checkSignNo3.length>0 || checkSignNo4.length>0 || checkSignNo5.length>0){
	setFormItem("propertyNo,serialNo","R");
}

	if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.propertyNo.value="";
		form1.propertyNoName.value="";
		form1.serialNo.value="";
		form1.bulletinDate.value="";
		form1.valueUnit.value="";
	}
	if(form1.check.value=="" && checkSignNo1.length!=0 && checkSignNo2.length!=0 && checkSignNo3.length!=0 && checkSignNo4.length!=0 && checkSignNo5.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.signNo4.value="";
		form1.signNo5.value="";
		form1.bulletinDate.value="";
		form1.valueUnit.value="";
	}
}

function gountla014f(){
	var prop="";
	var windowTop=(document.body.clientHeight-80)/2+25;
	var windowLeft=(document.body.clientWidth-800)/2+250;
	prop=prop+"width=775,height=475,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	var enterOrg = form1.enterOrg.value;
	var ownership = form1.ownership.value;
	var caseNo = form1.caseNo.value;
	var reduceDate = form1.reduceDate.value;
	var verify = form1.verify.value;
	var q_cause = form1.cause.value;
	beforeSubmit();
	returnWindow=window.open("untla014f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_reduceDate="+reduceDate+"&q_verify="+verify+"&q_cause="+q_cause,"aha",prop);	
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
		<td ID=t3 CLASS="tab_border1">合併<br>減損單明細</td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla030f.jsp');">合併<br>增加單</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla031f.jsp');">合併<br>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2">合併增加單<br>管理資料</td>
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla034f.jsp');">分割<br>減損單</a></td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla035f.jsp');">分割<br>減損單明細</a></td>
		<td ID=t9 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla036f.jsp');">分割<br>增加單</a></td>
		<td ID=t10 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla037f.jsp');">分割<br>增加單明細</a></td>
		<td ID=t11 CLASS="tab_border2">分割增加單<br>管理資料</td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
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
<input class="field_QRO" type="hidden" name="caseName" value="<%=obj.getCaseName()%>">
<input class="field_QRO" type="hidden" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
<input class="field_QRO" type="hidden" name="mergeReduce" value="<%=obj.getMergeReduce()%>">
<input class="field_QRO" type="hidden" name="mergeAdd" value="<%=obj.getMergeAdd()%>">
<input class="field_QRO" type="hidden" name="divisionReduce" value="<%=obj.getDivisionReduce()%>">
<input class="field_QRO" type="hidden" name="divisionAdd" value="<%=obj.getDivisionAdd()%>">
<input class="field_QRO" type="hidden" name="mergeDivisionDate" value="<%=obj.getMergeDivisionDate()%>">
<input class="field_QRO" type="hidden" name="approveOrg" value="<%=obj.getApproveOrg()%>">
<input class="field_QRO" type="hidden" name="approveDate" value="<%=obj.getApproveDate()%>">
<input class="field_QRO" type="hidden" name="approveDoc" value="<%=obj.getApproveDoc()%>">
<input type="hidden" name="check" value="">
<input type="hidden" name="originalDate" value="">
<input type="hidden" name="originalBasis" value="">
<input type="hidden" name="originalUnit" value="">
<input class="field_QRO" type="hidden" name="mergeDivisionVerify" value="<%=obj.getMergeDivisionVerify()%>">
<input class="field_QRO" type="hidden" name="addCaseNo" value="<%=obj.getAddCaseNo()%>">
<input class="field_QRO" type="hidden" name="reduceCaseNo1" value="<%=obj.getReduceCaseNo1()%>">
<input class="field_QRO" type="hidden" name="addCaseNo1" value="<%=obj.getAddCaseNo1()%>">
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
		<td class="td_form"  width="14%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　　權屬：
			<select class="field_QRO" type="select" name="ownership">
			<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;&nbsp;　電腦單號：[<input class="field_QRO" type="text" name="reduceCaseNo" size="10" maxlength="10" value="<%=obj.getReduceCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','1');getPropertyNo('PN');getValue();checkValue();"> 
			<input class="field_P" type="hidden" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','1')" value="..." title="財產編號輔助視窗">			
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			　分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getPropertyNo('PN');getValue();checkValue();">
		<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]
			&nbsp;　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_P" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getPropertyNo('SN');getValue();checkValue()">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>
			<select class="field_P" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getPropertyNo('SN');getValue();checkValue()">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>		
			<select class="field_P" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>
			　地號：
			<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onChange="getPropertyNo('SN');getValue();checkValue()">
			&nbsp;-
			<input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onChange="getPropertyNo('SN');getValue();checkValue()">		
		</td>
	</tr>		
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan="3">
			日期：[<input class="field_QRO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;　　　　　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月結：
          	<select class="field_RO" type="select" name="closing">
            	<%=util.View.getYNOption(obj.getClosing())%>
          	</select>
		<br>
			減損原因：
			<select class="field_QRO" type="select" name="cause">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' ", obj.getCause())%>
			</select>
			&nbsp;&nbsp;&nbsp;其他說明：[<input class="field_QRO" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			　　　　　　　　基金財產：
			<select class="field_RO" type="select" name="fundType">
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
			　　　　　　　抵繳遺產稅：
			<select class="field_RO" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>
			　　　新草衙：
			<select class="field_RO" type="select" name="grass">
				<%=util.View.getYNOption(obj.getGrass())%>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="td_form">使用情形：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="useState">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UST' ", obj.getUseState())%>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　　地目：
			<select class="field_RO" type="select" name="field">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
			</select>
		<br>
			取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
			&nbsp;&nbsp;　　權狀字號：[<input class="field_RO" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">面積：</td>
		<td class="td_form_white" colspan="3">
			整筆面積(㎡)：	[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">]
		<br>
			權利分子：[<input class="field_RO" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>">]
			&nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="5">
			帳面單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="16" value="<%=obj.getBookUnit()%>">]
			&nbsp;&nbsp;　　　　　帳面總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		<br>
			帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
			&nbsp;&nbsp;　　會計科目：[<input class="field_RO" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">]
		<br>
			當期公告日期：[<input class="field_RO" type="text" name="bulletinDate" size="7" maxlength="7" value="<%=obj.getBulletinDate()%>">]
			&nbsp;　　　　當期公告地價：[<input class="field_RO" type="text" name="valueUnit" size=""13,0"" maxlength=""13,0"" value="<%=obj.getValueUnit()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">使用分區：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="useSeparate">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
			</select>
			　　　　　　編定使用種類：
			<select class="field_RO" type="select" name="useKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getUseKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field_QRO" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form">異動人員/日期：</td>
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
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="hidden" followPK="false" name="batchInsertBut" value="現有資料明細新增" onClick="gountla014f();">
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產性質</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">權利範圍面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">帳面金額總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">使用情形</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,false,true,true,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,true,false,false,false,false,true,false,true,false,true,true,true,false,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");

	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			//將土地或建物標示的縣市欄位設為高雄市, 不過記得要在insertDefault裡加入一個 new Array("signNo1", "E000000")
			changeSignNo1('signNo1','signNo2','signNo3','E000000');
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
		case "insertError":
			//將土地或建物標示的縣市欄位設為高雄市, 不過記得要在insertDefault裡加入一個 new Array("signNo1", "E000000")
			changeSignNo1('signNo1','signNo2','signNo3','E000000');
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
		case "updateError":
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
	
		//取消時要執行的動作
		case "clear":
			setFormItem("batchInsertBut","O");
			break;
		case "clearError":
			setFormItem("batchInsertBut","O");
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



