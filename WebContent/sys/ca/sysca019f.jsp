<!--
程式目的：存置地點代碼維護
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA019F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setQ_enterOrg(user.getOrganID());
obj.setEnterOrg(user.getOrganID());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA019F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
//if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
//}
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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("isStop","N"),
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>")	
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.placeNo,"存置地點代碼");
		alertStr += checkEmpty(form1.placeName,"存置地點名稱");
		alertStr += checkEmpty(form1.isStop,"是否停用");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,placeNo){
	form1.enterOrg.value=enterOrg;
	form1.placeNo.value=placeNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	if('<%=user.getIsAdminManager()%>' == 'Y'){
		$("tr[name='tr_enterOrg']").show();
	}else{
		$("tr[name='tr_enterOrg']").hide();
	}	
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:250px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr name="tr_enterOrg">
		<td class="queryTDLable">所屬機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"")%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">存置地點代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_placeNo" size="10" maxlength="10" value="<%=obj.getQ_placeNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_placeName" size="20" maxlength="20" value="<%=obj.getQ_placeName()%>">
		</td>
	</tr>
    <tr>        
        <td class="queryTDLable">是否停用：</td>
        <td class="queryTDInput">
            <select class="field_Q" type="select" name="q_isStop">
            <%=util.View.getYNOption(obj.getQ_isStop())%>
            </select>
        </td>       
    </tr>	
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input type="hidden" name="q_enterOrg" value="<%=obj.getEnterOrg()%>">
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
	<tr name="tr_enterOrg">
		<td class="td_form"><font color="red">*</font>所屬機關：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopOrgan("field_RO","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName(),"")%>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>存置地點代碼：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="placeNo" size="10" maxlength="10" value="<%=obj.getPlaceNo()%>">
		</td>
		<td class="td_form"><font color="red">*</font>存置地點名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="placeName" size="20" maxlength="20" value="<%=obj.getPlaceName()%>">
		</td>
	</tr>
    <tr>        
        <td class="td_form"><font color="red">*</font>是否停用：</td>
        <td class="td_form_white">
            <select class="field" type="select" name="isStop" >
            <%=util.View.getYNOption(obj.getIsStop())%>
            </select>        </td>        
    </tr>	
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white" colspane="3">
			<textarea class="field" name="notes" cols="40" rows="4"><%=obj.getNotes()%></textarea>
		</td>
	</tr>
	<tr style='display:none'>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
			<input class="field_RO" type="hidden" name="editTime" size="6" maxlength="6" value="<%=obj.getEditTime()%>">
		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">存置地點代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">存置地點名稱</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false};
	boolean displayArray[] = {false,true,true};
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



