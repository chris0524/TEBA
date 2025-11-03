
<!--
程式目的：財產編號維護 - 需擁有系統管理者權限
程式代號：syspk007f
程式日期：0950607
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.pk.SYSPK007F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
//if (user.getIsAdminManager().equals("Y")) {
	if ("queryAll".equals(obj.getState())) {
		if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	}else if ("queryOne".equals(obj.getState())) {
		obj = (sys.pk.SYSPK007F)obj.queryOne();	
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
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkAlphaInt(form1.propertyNo,"財產編號");		
		alertStr += checkEmpty(form1.propertyType,"財產型態");
		alertStr += checkEmpty(form1.propertyName,"名稱");
		if((form1.propertyNo.value >= 10000000000 && form1.propertyNo.value<=11100000000) || form1.propertyNo.value >= 20000000000 && form1.propertyNo.value<=30000000000) {
			alertStr += checkEmpty(form1.propertynoCount, "土地或建物的財產編號子數位數")
		}
		if (form1.limitYear.value!="") alertStr += checkInt(form1.limitYear,"限用年度");
	}

	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg, propertyNo, propertyType, propertyName, propertyUnit, material, limitYear, memo, editID, editDate, propertynoCount){
	form1.propertyNo.value=propertyNo;
	form1.propertynoCount.value = propertynoCount;
	form1.propertyType.value=propertyType;
	form1.propertyName.value=propertyName;	
	form1.propertyUnit.value=propertyUnit;	
	if (isObj(form1.otherPropertyUnit)) {
		form1.otherPropertyUnit.value=propertyUnit;	
	}
	form1.material.value=material;
	if (isObj(form1.otherMaterial)) {
		form1.otherMaterial.value=material;
	}
	
	form1.limitYear.value=limitYear;	
	form1.memo.value=memo;
	form1.editID.value=editID;
	form1.editDate.value=editDate;	
	if (form1.propertyNo.value!="" && form1.propertyName.value!="" && form1.propertyType.value!="") {
		form1.state.value="queryOneSuccess";
		whatButtonFireEvent('queryOneSuccess');			
	}	
	//form1.state.value="queryOne";
	//beforeSubmit();
	//form1.submit();
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:130px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable"  width="30%">財產型態：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyType" style="width:150px">
				<%=util.View.getTextOption("0;類項目節;1;財產編號",obj.getQ_propertyType())%>
			</select>	
		</td>
	</tr>		

	<tr>
		<td class="queryTDLable"  width="30%">財產編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyNo" size="10" maxlength="11" value="<%=obj.getQ_propertyNo()%>">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"  width="30%">名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyName" size="20" maxlength="50" value="<%=obj.getQ_propertyName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>">
			<font color="red" style = "padding-left:90px">*</font>子號位數：
			<select class="field" type="select" name="propertynoCount" style="width:150px" >
				<%=util.View.getTextOption("2;2碼;3;3碼",obj.getPropertynoCount())%>
			</select>
			<font color="red">財產編號為數字結尾201020100-07 請選擇2碼； 若財產編號為字母結尾  20102010-01A 請選擇3碼</font>
	
		</td>

	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產型態：</td>
		<td class="td_form_white">
			<select class="field_P" type="select" name="propertyType" style="width:150px">
				<%=util.View.getTextOption("0;類項目節;1;財產編號",obj.getPropertyType())%>
			</select>
		</td>
	</tr>	

	<tr>
		<td class="td_form"><font color="red">*</font>名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="propertyName" size="50" maxlength="50" value="<%=obj.getPropertyName()%>">		</td>
	</tr>
	<tr>
		<td class="td_form">單位：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="propertyUnit" size="20" maxlength="20" value="<%=obj.getPropertyUnit()%>">		　
			材質：
			<input class="field" type="text" name="material" size="20" maxlength="20" value="<%=obj.getMaterial()%>">　
			最低使用年限：
			<input class="field" type="text" name="limitYear" size="3" maxlength="3" value="<%=obj.getLimitYear()%>"></td>
	    </tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="memo" size="50" maxlength="50" value="<%=obj.getMemo()%>">		</td>
	</tr>
	<tr>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
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
		<th class="listTH" >&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">單位</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">材質</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">限用年度</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,true,true,true,true,true};
	boolean displayArray[] = {false,true,false,true,true,true,true,false,false,false,false};
	String alignArray[] = {"center","left","center","left","center","center","center","center","center","center","center"};	
	out.write(util.View.getQuerylist(primaryArray,displayArray,alignArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>
<%
//} else {
//	out.println("<br><br><br><p align=center>對不起，您沒有足夠的權限執行此功能，若有問題，請洽系統管理者或承辦人員！<br><br></p>");		
//}
%>
