<!--
程式目的：市議員資料維護
程式代號：refap001f
程式日期：0950521
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="ref.ap.REFAP001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (ref.ap.REFAP001F)obj.queryOne();	
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
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkAlphaInt(form1.seqID,"議員代碼");	
		alertStr += checkEmpty(form1.seqID,"議員代碼");
		alertStr += checkEmpty(form1.councilmanName,"議員名稱");
		alertStr += checkEmpty(form1.councilmanName,"議員名稱");
		alertStr += checkEmpty(form1.isNow,"是否本屆");
		alertStr += checkLen(form1.memo,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(seqID, councilmanName, isNow, memo, editID, editDate, editTime){
	form1.seqID.value=seqID;
	form1.councilmanName.value = councilmanName;
	form1.isNow.value=isNow;
	form1.memo.value=memo
	form1.editID.value=editID;
	form1.editDate.value=editDate;	
	if (form1.seqID.value!="" && form1.councilmanName.value!="") {
		form1.state.value="queryOneSuccess";
		whatButtonFireEvent('queryOneSuccess');	
	}
	//form1.state.value="queryOne";
	//beforeSubmit();
	//form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:250px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">議員代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_seqID" size="10" maxlength="10" value="<%=obj.getQ_seqID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">議員名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_councilmanName" size="20" maxlength="50" value="<%=obj.getQ_councilmanName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">是否本屆：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_isNow">
			<%=util.View.getYNOption(obj.getQ_isNow())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">備註：</td>
		<td class="queryTDInput">
			<input type="text" class="field_Q" name="q_memo" size="30" value="<%=obj.getQ_memo()%>">
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
<tr><td class="bg"><div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form"><font color="red">*</font>議員代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="seqID" size="10" maxlength="10" value="<%=obj.getSeqID()%>">		</td>
		</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>議員名稱：</td>
	  <td class="td_form_white">
			<input class="field" type="text" name="councilmanName" size="20" maxlength="50" value="<%=obj.getCouncilmanName()%>">		</td>
	  </tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>是否本屆：</td>
	  <td class="td_form_white">
			<select class="field" type="select" name="isNow">
			<%=util.View.getYNOption(obj.getIsNow())%>
			</select>		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
			<input name="memo" type="text" class="field" value="<%=obj.getMemo()%>" size="50">		</td>
	</tr>
	<tr>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white"> [
		  <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
		  /
		  <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
		  ] </td>
		</tr>
	</table>
</div></td>
</tr>

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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">議員代碼</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">議員名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">本屆</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">備註</a></th>		
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,true,true,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,true,false,false};
	out.write(util.View.getPageQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



