<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH001F03">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02_1">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ch.UNTCH001F03)obj.queryOne();	
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


unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";
String checkStr3 = obj.getPropertyNo().substring(0,3);
String checkStr1 = obj.getPropertyNo().substring(0,1);
if("111".equals(checkStr3)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._RF_ADD, 6);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._RF_ADD, 5);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._RF_ADD, 5);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._RF_ADD, 5);
	}else{									uch._setupViewType(uch._queryOrMaintenance);
											tabs = uch._createTabData(uch._RF_ADD, 5);
	}
}else if("2".equals(checkStr1)){
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._BU_ADD, 10);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._BU_ADD, 9);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._BU_ADD, 9);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._BU_ADD, 9);
	}else{									uch._setupViewType(uch._queryOrMaintenance);
											tabs = uch._createTabData(uch._BU_ADD, 9);
	}
}else if("3".equals(checkStr1) || "4".equals(checkStr1) || "5".equals(checkStr1)){	
	if("true".equals(obj.getIsAddProof())){	tabs = uch._createTabData(uch._MP_ADD, 7);
	}else if("query".equals(obj.getIsAddProof())){
											uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
											tabs = uch_QUERY._createTabData(uch_QUERY._MP_ADD, 6);
	}else if("_query".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._MP_ADD, 3);
	}else if("_maintenance".equals(obj.getIsAddProof())){
											uch._setupViewType(uch._maintenance);
											tabs = uch._createTabData(uch._MP_ADD, 3);
	}else{									uch._setupViewType(uch._query);
											tabs = uch._createTabData(uch._MP_ADD, 3);
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
<script type="text/javascript" src="../../js/validate.js" ></script>
<script type="text/javascript" src="../../js/function.js" ></script>
<script type="text/javascript" src="../../js/tablesoft.js" ></script>
<script type="text/javascript" src="../../js/sList2.js" ></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("isHistory","N")
);

function checkField(){
	var alertStr="";
	
	alertStr += checkEmpty(form1.deprUnit,"部門別");	
	alertStr += checkEmpty(form1.deprAccounts,"會計科目");	
	alertStr += checkEmpty(form1.deprPercent,"分攤百分比");	
	
	if(alertStr.length!=0){ alert(alertStr); return false; }

	var checkStr;
	if(form1.state.value == "insert"){
		checkStr = (parseInt(form1.deprPercent.value) + parseInt(checkDeprPercent_All));
	}else{
		checkStr = (parseInt(form1.deprPercent.value) + parseInt(checkDeprPercent_queryOne));
	}
	
	if(checkStr > 100){
		form1.deprPercent.style.backgroundColor=errorbg;
		alert("分攤百分比總和必須小於等於１００");
		return false;
	}else{
		form1.deprPercent.style.backgroundColor="";
	}
	
	beforeSubmit();
}


function queryOne(enterOrg,ownership,differenceKind,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value = enterOrg;
	form1.ownership.value = ownership;
	form1.differenceKind.value = differenceKind;
	form1.propertyNo.value = propertyNo;
	form1.serialNo.value = serialNo;
	form1.serialNo1.value = serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();		
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else{
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
	}
}

function init(){	
	setDisplayItem("spanQueryAll", "H");
	
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete", "R");
	}else if(form1.originalDeprMethod.value != "01"){
		if(form1.originalDeprUnitCB.value == "Y"){
			setFormItem("insert,update,delete", "O");
		}else{
			setFormItem("insert,update,delete", "R");
		}
	}else{
		setFormItem("insert,update,delete", "R");
	}
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
}

function checkDeprPercent(){
	var alertStr="";	
	alertStr += checkInt(form1.deprPercent,"分攤百分比");	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	
	if(parseInt(form1.deprPercent.value) <= 0){		
		form1.deprPercent.value = "";
		form1.deprPercent.style.backgroundColor=errorbg;
		alert("分攤百分比必須大於０");
	}else{
		form1.deprPercent.style.backgroundColor="";
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<table class="queryTable"  border="1">
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02_1.jsp" />
	</table>
</div>

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<tr>
			<td class="td_form">次序：</td>
			<td class="td_form_white" colspan="3">
				[<input class="field_QRO" type="text" name="serialNo1" size="10" maxlength="10" value="<%=obj.getSerialNo1()%>" >]
			</td>
		</tr>
		<tr>
			<td class="td_form">園區別：</td>
			<td class="td_form_white" colspan="3">
				<select class="field_QRO" type="select" name="deprPark">
					<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>部門別：
				<select class="field" type="select" name="deprUnit">
					<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>部門別單位：
				<select class="field" type="select" name="deprUnit1">
					<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit1())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>會計科目：
				<select class="field" type="select" name="deprAccounts">
					<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprAccounts())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_form"><font color="red">*</font>分攤百分比(%)：</td>
			<td class="td_form_white" colspan="3">
				<input type="text" class="field" name="deprPercent" value="<%=obj.getDeprPercent() %>" size="10" maxlength="10" onchange="checkDeprPercent();">
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>歷史資料：
				<select class="field" type="select" name="isHistory">
				　　<%=util.View.getYNOption(obj.getIsHistory())%>
				</select>
			</td>
		</tr>	
		
		
		
		<tr>
		  <td class="td_form">備註：</td>
		  <td class="td_form_white"><textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>      </td>
		  <td class="td_form">異動人員/日期：</td>
		  <td class="td_form_white">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
		    /
		    <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
		</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">		
	<jsp:include page="../../home/toolbar.jsp" />
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">	
	<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
	<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>" >
	<input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>" >
	<input class="field_QRO" type="hidden" name="differenceKind" size="10" maxlength="10" value="<%=obj.getDifferenceKind()%>" >
	<input class="field_QRO" type="hidden" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>" >
	<input class="field_QRO" type="hidden" name="serialNo" size="10" maxlength="10" value="<%=obj.getSerialNo()%>" >	
	<input class="field_QRO" type="hidden" name="verify" size="10" maxlength="10" value="<%=obj.getVerify()%>" >
	<input class="field_QRO" type="hidden" name="lotNo" value="<%=obj.getLotNo() %>">
	<input class="field_QRO" type="hidden" name="originalDeprMethod" size="10" maxlength="10" value="<%=obj.getOriginalDeprMethod()%>" >
	<input class="field_QRO" type="hidden" name="originalDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOriginalDeprUnitCB()%>" >
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
	<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
	<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
	
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">園區別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">部門別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">會計科目</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">分攤百分比(％)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">歷史資料</a></th>		
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,false,true,true,true,true,true,true};
	String	alignArray[]   = {"","","","","","","","","","",""};
	out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>

<script type="text/javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
			form1.serialNo1.value = "";			
			break;
	}
}
</script>
</body>
</html>



