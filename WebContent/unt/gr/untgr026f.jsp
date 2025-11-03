<!--
程式目的：非消耗品月結作業
程式代號：untgr026f
程式日期：0951016
程式作者：Cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR026F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.gr.UNTGR026F)obj.queryOne();	
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
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.closingYM,"月結年月");
		alertStr += checkYYYMM(form1.closingYM,"月結年月");
	}
	changeYM();
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,closingYM){
	form1.enterOrg.value=enterOrg;
	form1.closingYM.value=closingYM;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init() {
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanDelete").style.display="none";
}

function changeYM(){
	form1.closingYMTemp.value = form1.closingYM.value+"01";
	form1.YM.value = getDateAdd("m", -1,form1.closingYMTemp).substring(0,5);
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:100px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" width="20%">入帳機關：</td>
		<td class="queryTDInput" width="30%">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">月結年月：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_closingYM" size="5" maxlength="5" value="<%=obj.getQ_closingYM()%>">
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
		<td class="td_form" width="18%">入帳機關：</td>
		<td class="td_form_white">
			<input class="field_P" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			<input class="field_P" type="hidden" name="btn_enterOrg" onclick="popOrgan('enterOrg','enterOrgName','N')" value="..." title="機關輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>月結年月：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="closingYM" size="5" maxlength="5" value="<%=obj.getClosingYM()%>" onChange="changeYM();">
			<font color="red">(例：09501)</font>
			<input class="field" type="hidden" name="YM" value="<%=obj.getYM()%>">
			<input class="field" type="hidden" name="closingYMTemp" value="">			
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">月結年月</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,true,true};
	boolean displayArray[] = {true,false,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "queryAll":
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			}
			break;			
	}
	return true;
}
</script>
</body>
</html>



