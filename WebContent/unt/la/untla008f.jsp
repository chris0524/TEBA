
<!--
程式目的：土地主檔資料維護－稅籍資料
程式代號：untla008f
程式日期：0940912
程式作者：novia
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
clive.chang 0941219	Debug & Modify for Testing and autual running..
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA008F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA008F)obj.queryOne();	
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
<script language="javascript" src="untla001q.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("taxYear", getToday().substring(0,3))
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	} else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.taxYear,"年度");
		alertStr += checkEmpty(form1.taxKind,"稅別");
		if (parse(form1.taxYear.value).length>0) alertStr += checkYYY(form1.taxYear, "年度");		
		if (parse(form1.taxAmount.value).length>0) {
			alertStr += checkInt(form1.taxAmount,"稅額");
			if (!parseInt(form1.taxAmount.value)>0) alertStr += "稅額若有資料, 必須>0!\n";
		}
		alertStr += checkLen(form1.taxState, "減免稅狀況", 50);
		alertStr += checkLen(form1.notes, "備註", 250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,serialNo,taxYear){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.taxYear.value=taxYear;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}


function init() {
	setFormItem("queryAll","R");
	document.all.item("spanQueryAll").style.display="none";	
	if (form1.dataState.value=="2" || form1.enterOrg.value!="<%=user.getOrganID()%>") {
		setFormItem("insert,update,delete,clear,confirm","R");
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--Query區============================================================-->
<div id="queryContainer" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA001Q",obj);%>
	<jsp:include page="untla001q.jsp" />
</div>

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<!--td ID=t1 CLASS="tab_border2" HEIGHT="25" style="cursor:auto">增加單資料</td-->		
		<td ID=t1 CLASS="tab_border2"  HEIGHT="25"><a href="#" onClick="return checkURL('untla001f.jsp');">增加單資料</a></td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla002f.jsp');">基本資料</a></td>
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla003f.jsp');">管理資料</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla004f.jsp');">地上物</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla005f.jsp');">公告地價</a></td>
		<td ID=t6 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla006f.jsp');">公告現值</a></td>				
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla007f.jsp');">現場勘查</a></td>
		<td ID=t8 CLASS="tab_border1"><a href="#">稅籍資料</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line1"></td>			
	</tr>
</TABLE>


<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form"><font color="red">*</font>年度：</td>
		<td class="td_form_white"  colspan="3">
			<input class="field_P" type="text" name="taxYear" size="5" maxlength="3" value="<%=obj.getTaxYear()%>">

			　<font color="red">*</font>稅別：<select class="field" type="select" name="taxKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='TKD' ", obj.getTaxKind())%>
			</select>		</td>		
	</tr>
	<tr>
		<td class="td_form">稅額：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="taxAmount" size="15" maxlength="13" value="<%=obj.getTaxAmount()%>">
			<input class="field_QRO" type="HIDDEN" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">減免稅狀況：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="taxState" size="50" maxlength="100" value="<%=obj.getTaxState()%>">		</td>		
	</tr>	
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea>		</td>
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">年度</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">稅別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">稅額</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">減免稅狀況</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,false,false,false};
	boolean displayArray[] = {false,false,false,false,true,true,true,true};
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
