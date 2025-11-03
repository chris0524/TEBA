<!--
程式目的：物品增減值作業－明細資料附屬設備
程式代號：untne035f
程式日期：0950104
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE035F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE035F)obj.queryOne();	
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
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
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
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
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

function checkURL(surl){
	var alertStr = "";
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untne024f.jsp"){			
			form1.state.value="queryOne";
		} else if (surl=="untne025f.jsp") {
			if (document.all.querySelect[0].checked) surl=surl+"?initDtl=Y";
			if (document.all.querySelect[1].checked) surl=surl+"?initDtl=Y";
			form1.state.value="queryOne";
		} else {
			alertStr += checkEmpty(form1.enterOrg,"入帳機關");
			alertStr += checkEmpty(form1.ownership,"權屬");
			alertStr += checkEmpty(form1.propertyNo,"物品編號－類項目節");
			alertStr += checkEmpty(form1.serialNo,"物品編號－類項目節");
			alertStr += checkEmpty(form1.caseNo,"增加單-電腦單號");
			form1.state.value="queryAll";
			if(alertStr.length!=0){ 
				alert("請先執行查詢"); 
				return false;
			}
		}
		form1.action=surl;		
		beforeSubmit();
		form1.submit();			
	}
}
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" ><a href="#" onClick="return checkURL('untne024f.jsp');">增減值單資料</a></td>	
		<td ID=t2 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untne025f.jsp');">增減值單明細資料</a></td>	
		<td ID=t3 CLASS="tab_border1" >明細資料附屬設備</td>	
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
		<td class="tab_line1"></td>	
	</tr>
</TABLE>
<!--隱藏欄位===========================================================-->
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<input class="field" type="hidden" name="verify" size="10" maxlength="10" value="<%=obj.getVerify()%>">
<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="hidden" name="closing" size="10" maxlength="10" value="<%=obj.getClosing()%>">
<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
<input class="field_QRO" type="hidden" name="ownership" size="1" maxlength="1" value="<%=obj.getOwnership()%>">
<input class="field_QRO" type="hidden" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>">
<input class="field_QRO" type="hidden" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">
<input class="field_QRO" type="hidden" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE024Q",obj);%>
	<jsp:include page="untne024q.jsp" />
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
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			  <textarea size="25" rownum="10" name="notes" cols="30" rows="4" class="field"><%=obj.getNotes()%></textarea>
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
	<input type="hidden" name="filestoreLocation" value="<%=application.getInitParameter("filestoreLocation")%>">
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



