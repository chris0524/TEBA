<!--
程式目的：動產主檔資料維護－批號明細附屬設備
程式代號：untmp005f
程式日期：0941025
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02_2">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP005F)obj.queryOne();	
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
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._MP_ADD, 6);
}else{
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate", getToday())
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkDate(form1.buyDate,"購置日期");
		alertStr += checkEmpty(form1.equipmentName,"名稱");
		alertStr += checkEmpty(form1.equipmentAmount,"數量");
		alertStr += checkFloat(form1.equipmentAmount,"數量",4,0);
		//alertStr += checkEmpty(form1.unitPrice,"單價");
		if(parse(form1.unitPrice.value).length>0){
			alertStr += checkFloat(form1.unitPrice,"單價",13,0);
		}
		//alertStr += checkFloat(form1.totalValue,"總價",15,0);
		alertStr += checkEmpty(form1.dataState,"資料狀態");
		alertStr += checkLen(form1.notes, "備註", 250);
		//數量必須 > 0
		var equipmentAmount = parseInt(form1.equipmentAmount.value);
		var unitPrice = parseInt(form1.unitPrice.value);
			if(equipmentAmount<=0){
				form1.equipmentAmount.style.backgroundColor=errorbg;
				alertStr +=" 數量必須 > 0 ! \n";
			}else form1.equipmentAmount.style.backgroundColor="";
		//單價必須 > 0
			if(unitPrice<=0){
				form1.unitPrice.style.backgroundColor=errorbg;
				alertStr +=" 單價必須 > 0 ! \n";
			}else form1.unitPrice.style.backgroundColor="";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	if((form1.state.value=="update") || (form1.state.value=="updateError")){			
		if(form1.dataState.value=="2"){
			if(confirm("「資料狀態」為「已減損」，是否確定修改?")){
				beforeSubmit();
			}else{
				return false;
			}
		}
	}else{
		beforeSubmit();
	}
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	document.all.item("spanQueryAll").style.display="none";
// 	 查出的「批號明細」之「資料狀態」若為「已減損」，不允許新增、修改、刪除該批號明細的「附屬設備」
	if (form1.checkDataState.value=="2"){
		setFormItem("insert,update,delete,clear,confirm", "R")
	}
	
// 	查出的「批號資料」若是「已月結」，均允許新增、修改、刪除批號資料 
	if (form1.checkClosing.value=="N"){
		setFormItem("insert,update,delete,clear,confirm", "R")
	}
	if (form1.enterOrg.value!=<%="\""+user.getOrganID()+"\""%>) {		
		setFormItem("insert,update,delete,clear,confirm", "R")
	}
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
}

function checkURL(surl){
	if (surl=="../ch/untch001f02.jsp"){
		if('<%=obj.getIsAddProof()%>' != 'true'){
			form1.state.value="queryAll";
		}
	}else {
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
	
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}

function changeTotal(){
	var equipmentAmount = parseInt(form1.equipmentAmount.value);
	var unitPrice = parseInt(form1.unitPrice.value);
	if(equipmentAmount!=0 && unitPrice!=0){
		var totalValue = parseInt(equipmentAmount)*parseInt(unitPrice);
		form1.totalValue.value = totalValue;
	}else form1.totalValue.value = 0;
	if(AllTrim(form1.equipmentAmount).length<=0 || AllTrim(form1.unitPrice).length<=0){
		form1.totalValue.value="";
	}
}
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input class="field" type="hidden" name="checkClosing" size="10" maxlength="10" value="<%=obj.getClosing()%>">
<input class="field" type="hidden" name="checkDataState" size="10" maxlength="10" value="<%=obj.getCheckDataState()%>">
<input class="field" type="hidden" name="verify" size="10" maxlength="10" value="<%=obj.getVerify()%>">
<input class="field" type="hidden" name="closing" size="10" maxlength="10" value="<%=obj.getClosing()%>">
<input type="hidden" name="serialNoS" size="10" maxlength="10" value="<%=obj.getSerialNoS()%>">
<input type="hidden" name="serialNoE" size="10" maxlength="10" value="<%=obj.getSerialNoE()%>">
<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
<!-- 入帳機關：-->
<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
<!-- 權屬：-->
<input class="field_QRO" type="hidden" name="ownership" size="1" maxlength="1" value="<%=obj.getOwnership()%>">
<!-- 財產編號：-->
<input class="field_QRO" type="hidden" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>">
<!-- 財產批號：-->
<input class="field_QRO" type="hidden" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">
<!-- 財產分號：-->
<input class="field_QRO" type="hidden" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">
<input class="field_QRO" type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">

<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02_2.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">次序：</td>
		<td class="td_form_white">
			[<input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]
		</td>
		<td class="td_form">購置日期：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","buyDate",obj.getBuyDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>名稱：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="equipmentName" size="40" maxlength="20" value="<%=obj.getEquipmentName()%>">
			&nbsp;&nbsp;&nbsp;<font color="red">*</font>資料狀態：
			<select class="field" type="select" name="dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
			</select>		
		</td>
	</tr>
	<tr>
		<td class="td_form">價格資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>數量：<input class="field" type="text" name="equipmentAmount" size="4" maxlength="4" value="<%=obj.getEquipmentAmount()%>" onFocusOut="changeTotal();">
			&nbsp;單位：<input class="field" type="text" name="equipmentUnit" size="8" maxlength="8" value="<%=obj.getEquipmentUnit()%>">
			&nbsp;單價：<input class="field" type="text" name="unitPrice" size="13" maxlength="13" value="<%=obj.getUnitPrice()%>" onFocusOut="changeTotal();">
			&nbsp;總價：[<input class="field_RO" type="text" name="totalValue" size="15" maxlength="15" value="<%=obj.getTotalValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" >來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="">
			　　<%=util.View.getTextOption("1;測試;2;測試;","")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" >廠牌型式：</td>
		<td class="td_form_white" colspan="3">
			廠牌：
			<input class="field" type="text" id="nameplate" name="nameplate" size="40" maxlength="40" value="">
			&nbsp;&nbsp;&nbsp;&nbsp;
			型式：
			<input class="field" type="text" id="specification" name="specification" size="40" maxlength="40" value="">		
			
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white"colspan="3">
			  <textarea size="25" rownum="10" name="notes" cols="30" rows="4" class="field"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form"style="display:none">異動人員/日期：</td>
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">附屬設備次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">單價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">資料狀態</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



