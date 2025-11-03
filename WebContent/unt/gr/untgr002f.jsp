<!--
程式目的：公用財產異動計畫資料維護
程式代號：untgr002f
程式日期：0950302
程式作者：Cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.gr.UNTGR002F)obj.queryOne();	
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
	new Array("enterOrg", "<%=user.getOrganID()%>"),
	new Array("enterOrgName", "<%=user.getOrganName()%>")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.planYear,"年度");
		alertStr += checkYYY(form1.planYear,"年度");
		alertStr += checkEmpty(form1.dataType,"區分");
		if(parse(form1.addAmount.value).length>0){alertStr += checkFloat(form1.addAmount,"增加數量",9,2);}
		if(parse(form1.reduceAmount.value).length>0){alertStr += checkFloat(form1.reduceAmount,"減少數量",9,2);}
		if(parse(form1.addValue.value).length>0){alertStr += checkInt(form1.addValue,"增加價值");}
		if(parse(form1.reduceValue.value).length>0){alertStr += checkInt(form1.reduceValue,"減少價值");}
		alertStr += checkLen(form1.notes, "備註", 10);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,planYear,dataType){
	form1.enterOrg.value=enterOrg;
	form1.planYear.value=planYear;
	form1.dataType.value=dataType;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init() {
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("update,delete","R");
	}
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
		<td class="queryTDLable">年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_planYear" size="3" maxlength="3" value="<%=obj.getQ_planYear()%>">
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
		<td class="td_form" width="15%"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopOrgan("field_P","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName())%>
			&nbsp;　　　　　<font color="red">*</font>年度：<input class="field_P" type="text" name="planYear" size="3" maxlength="3" value="<%=obj.getPlanYear()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>區分：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_P" type="select" name="dataType">
				<%=util.View.getTextOption("1;土地購置;2;房屋建築;3;其他建築;4;機械設備;5;交通與運輸設備;6;資訊設備;7;其它設備;8;投資及其他",obj.getDataType())%>
			</select>
			&nbsp;　　　　　　　　　　單位：<input class="field" type="text" name="dataUnit" size="8" maxlength="4" value="<%=obj.getDataUnit()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">增加：</td>
		<td class="td_form_white" colspan="3">
			數量：<input class="field" type="text" name="addAmount" size="9" maxlength="9" value="<%=obj.getAddAmount()%>">
			&nbsp;　　　　　　　　　　價值：<input class="field" type="text" name="addValue" size="15" maxlength="15" value="<%=obj.getAddValue()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">減少：</td>
		<td class="td_form_white" colspan="3">
			數量：<input class="field" type="text" name="reduceAmount" size="9" maxlength="9" value="<%=obj.getReduceAmount()%>">
			&nbsp;　　　　　　　　　　價值：<input class="field" type="text" name="reduceValue" size="15" maxlength="15" value="<%=obj.getReduceValue()%>">
		</td>
	</tr>
	<tr>
		<td rowspan="2" class="td_form" >備註：</td>
		<td class="td_form_white" >	
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="texta" name="editID" size="10" value="<%=obj.getEditID()%>">/
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">年度</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">區分</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增加數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">增加價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">減少數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">減少價值</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,true,true,true,false,false,false,false,false};
	boolean displayArray[] = {true,false,true,false,true,true,true,true,true};
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



