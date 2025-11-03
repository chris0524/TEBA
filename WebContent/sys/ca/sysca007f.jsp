<!--
程式目的：土地公告年月適用期間資料維護
程式代號：sysca007f
程式日期：0950619
程式作者：amanda
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA007F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA007F)obj.queryOne();	
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
	//	alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.bulletinKind,"公告類別");
		alertStr += checkEmpty(form1.bulletinDate,"公告年月");
		alertStr += checkEmpty(form1.suitDateS,"適用期間－起");
		alertStr += checkEmpty(form1.suitDateE,"適用期間－訖");
		alertStr += checkYYYMM(form1.bulletinDate,"公告年月");

	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(bulletinKind,bulletinDate){
	form1.bulletinKind.value=bulletinKind;
	form1.bulletinDate.value=bulletinDate;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:300px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" width="20%">公告類別：</td>
		<td class="queryTDInput" width="30%">
			<select class="field_Q" type="select" name="q_bulletinKind">
			<%=util.View.getTextOption("1;公告地價;2;公告現值", obj.getQ_bulletinKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">公告年月：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_bulletinDate" size="5" maxlength="5" value="<%=obj.getQ_bulletinDate()%>">
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
		<td class="td_form" width="22%"><font color="red">*</font>公告類別：</td>
		<td class="td_form_white">
			<select class="field_P" type="select" name="bulletinKind" >
			<%=util.View.getTextOption("1;公告地價;2;公告現值", obj.getBulletinKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>公告年月：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="bulletinDate" size="5" maxlength="5" value="<%=obj.getBulletinDate()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red" >*</font>適用期間：</td>
		<td class="td_form_white">
			起<%=util.View.getPopCalndar("field","suitDateS",obj.getSuitDateS())%>&nbsp;&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field","suitDateE",obj.getSuitDateE())%>
		</td>
		
	</tr>
	<tr>
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
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">公告類別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">公告年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">適用期間－起</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">適用期間－訖</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,false};
	boolean displayArray[] = {false,true,true,true,true};
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



