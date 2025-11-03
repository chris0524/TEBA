<!--
程式目的：基本代碼管理
程式代號：sysca011f
撰寫日期：98/10/5
程式作者：Timtoy.Tsai
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj"  scope="request" class="sys.ca.SYSCA011F">
    <jsp:setProperty name='obj' property='*'/>
</jsp:useBean>
<jsp:useBean id="objList"  scope="page" class="java.util.ArrayList"/>
        
<%

//if ("queryAll".equals(obj.getState())) {
//	if ("false".equals(obj.getQueryAllFlag())){	obj.setQueryAllFlag("true"); }
//}else if ("queryOne".equals(obj.getState())) {
//	obj = (sys.ca.SYSCA011F)obj.queryOne();	
//}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
//	obj.insert();	
//}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
//	obj.update();
//}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
//	obj.delete();
//}
//if ("true".equals(obj.getQueryAllFlag())){
//	objList = obj.queryAll();		
//}
if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.InsertSQL();
}

%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
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
		alertStr += checkEmpty(form1.oldOrganID,"舊機關代碼名稱");
		alertStr += checkEmpty(form1.newOrganID,"新機關代碼名稱");
	}		
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(){
}

function change(){
	if(form1.oldOrganID.value != "" && form1.newOrganID.value != "" ){
		form1.state.value = "insert";
	}
}

</script>

</head>
<body topmargin="5" onLoad="showErrorMsg('<%=obj.getErrorMsg()%>');">

<form id="form1" name="form1" method="post" action="sysca011f.jsp" onSubmit="return checkField()">
<table width="70%" cellspacing="0" cellpadding="0" align="center">

<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
        <td class="td_form" width="25%">&nbsp;<font color="red">*</font>舊機關代碼名稱：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field","oldOrganID",obj.getOldOrganID(),obj.getOldOrganIDName(),"Y")%>
		</td>
	</tr>
	<tr>
        <td class="td_form" >&nbsp;<font color="red">*</font>新機關代碼名稱：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field","newOrganID",obj.getNewOrganID(),obj.getNewOrganIDName(),"Y")%>
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
	<input class="toolbar_default" followPK="false" type="submit" name="insert" value="確        定" onclick="change();">
	<input class="toolbar_default" followPK="false" type="reset" name="cannel"  value="取        消">
</td></tr>

</table>	
</form>

</body>
</html>
