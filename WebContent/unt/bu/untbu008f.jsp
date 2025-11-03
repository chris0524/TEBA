<!--
程式目的：建物主檔資料維護--附屬設備
程式代號：untbu008f
程式日期：0940919
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU008F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.bu.UNTBU008F)obj.queryOne();	
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
String tabs = uch._createTabData(uch._BU_ADD, 9);
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
<script language="javascript" src="untbu001q.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	} else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.equipmentName, "名稱");
		if (parse(form1.equipmentName.value).length>0) alertStr += checkLen(form1.equipmentName, "名稱", 20);
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkDate(form1.buyDate,"購置日期");
		alertStr += checkEmpty(form1.equipmentAmount,"數量");
		//alertStr += checkEmpty(form1.unitPrice,"單價");
		columnTrim(form1.equipmentAmount);
		columnTrim(form1.unitPrice);
		if (form1.equipmentAmount.value.length!=0 && form1.unitPrice.value.length!=0) {
			alertStr += checkInt(form1.equipmentAmount,"數量");		
			alertStr += checkInt(form1.unitPrice,"單價");
			if (parseInt(form1.equipmentAmount.value)<=0 || parseInt(form1.unitPrice.value)<=0) {	
				alertStr += "數量與單價必須>0, 重新輸入!\n";
				form1.totalValue.value = 0;
			}
		}
		alertStr += checkLen(form1.equipmentUnit, "單位", 4);		
		alertStr += checkLen(form1.notes, "備註", 250);
		calTotal();
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
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


function calTotal() {
	if (parseInt(form1.equipmentAmount.value)>0 && parseInt(form1.unitPrice.value)>0) {	
		form1.totalValue.value = parseInt(form1.equipmentAmount.value) * parseInt(form1.unitPrice.value);
	} else form1.totalValue.value = "";
}

function init() {
	setFormItem("queryAll","R");
	document.all.item("spanQueryAll").style.display="none";	
	if (form1.dataState.value=="2" || form1.enterOrg.value!="<%=user.getOrganID()%>") {
		setFormItem("insert,update,delete,clear,confirm","R");
	}
}

function checkURL(surl){
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTBU001Q",obj);%>
	<jsp:include page="untbu001q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">次序：</td>
		<td class="td_form_white">
			[<input class="field_PRO" type="text" name="serialNo1" size="4" maxlength="3" value="<%=obj.getSerialNo1()%>">]
</td>
		<td class="td_form">購置日期：</td>
		<td class="td_form_white"><%=util.View.getPopCalndar("field","buyDate",obj.getBuyDate())%> </td>
	</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>名稱：</td>
	  <td colspan="3" class="td_form_white"><input class="field" type="text" name="equipmentName" size="40" maxlength="40" value="<%=obj.getEquipmentName()%>">
      </td>
	  </tr>
	<tr>
	  <td class="td_form">價格資料：</td>
	  <td colspan="3" class="td_form_white">
	  <font color="red">*</font>數量：<input class="field" type="text" name="equipmentAmount" size="4" maxlength="4" value="<%=obj.getEquipmentAmount()%>" onChange="calTotal();" onBlur="calTotal();">
	  　單位：
	  <input class="field" type="text" name="equipmentUnit" size="8" maxlength="8" value="<%=obj.getEquipmentUnit()%>">
	  　單價：
	  <input class="field" type="text" name="unitPrice" size="10" maxlength="13" value="<%=obj.getUnitPrice()%>" onChange="calTotal();" onBlur="calTotal();">
	　總價：[<input class="field_RO" type="text" name="totalValue" size="13" maxlength="15" value="<%=obj.getTotalValue()%>">]
</td>	  
	  </tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white"><textarea name="notes" cols="25" rows="5" class="field"><%=obj.getNotes()%></textarea></td>
	  <td class="td_form">異動人員/日期：</td>
	  <td class="td_form_white"> [
          <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
  /
  <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
  ] </td>
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
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
	<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
	<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">
	<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">	
	<input type="hidden" name="dataState" value="<%=obj.getDataState()%>">	
	<input type="hidden" name="propertyKind" value="<%=obj.getPropertyKind()%>">
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
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,true,true,true,true,true};
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



