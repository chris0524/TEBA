<!--
程式目的：地址代碼維護
程式代號：sysca009f
程式日期：0980623
程式作者：Chu-Hung
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA009F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA009F)obj.queryOne();
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.addrKind,"層級分類");
		alertStr += checkEmpty(form1.addrID,"地址代碼");
		alertStr += checkEmpty(form1.cod2_Desc,"地址全名");
		alertStr += checkEmpty(form1.addrName,"地址名稱");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(addrID){
	form1.addrID.value=addrID;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:510px;height:260px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" width="8%">層級分類：</td>
		<td class="queryTDInput" width="30%">
			<select class="field_Q" type="select" name="q_addrKind">
			<%=util.View.getAddkind(obj.getQ_addrKind()) %>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">地址代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_addrID" size="10" maxlength="10" value="<%=obj.getQ_addrID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">地址全名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_cod2_Desc" size="40" maxlength="40" value="<%=obj.getQ_cod2_Desc()%>">
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
		<td class="td_form" width="15%"><font color="red">*</font>層級分類：</td>
		<td class="td_form_white" width="15%">
			<select class="field" type="select" name="addrKind">
			<%=util.View.getAddkind(obj.getAddrKind()) %>
			</select>
		</td>
		<td class="td_form"><font color="red">*</font>地址代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="addrID" size="10" maxlength="10" value="<%=obj.getAddrID()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>地址全名：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="cod2_Desc" size="40" maxlength="40" value="<%=obj.getCod2_Desc()%>">
		</td>
		<td class="td_form"><font color="red">*</font>地址名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="addrName" size="20" maxlength="20" value="<%=obj.getAddrName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">郵遞區號：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="zipCode" size="10" maxlength="10" value="<%=obj.getZipCode()%>">
		</td>
		<td class="td_form">排序：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="seqNo" size="10" maxlength="10" value="<%=obj.getSeqNo()%>">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">層級分類</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">地址代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">地址全名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">地址名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">郵遞區號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">排序</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,true,false,false,false,false};
	boolean displayArray[] = {true,true,true,true,true,true};
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



