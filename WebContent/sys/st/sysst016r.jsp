<!--
程式目的：資訊設備現況調查歷年資料查詢
程式代號：sysst016r
程式日期：0960704
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.st.SYSST016R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setEditID(user.getUserID());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}
if ("true".equals(obj.getQueryAllFlag())){
	obj.setFileName(this.getServletContext().getRealPath("\\sqlfile\\sys\\st"));
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
		//alertStr += checkQuery();
		alertStr += checkEmpty(form1.q_enterOrg,"單位");
		alertStr += checkEmpty(form1.q_year,"年度");
		alertStr += checkYYY(form1.q_year,"年度");
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(editID){
	//form1.editID.value=editID;
	//form1.state.value="queryOne";
	//beforeSubmit();
	//form1.submit();
}
function init(){
  	document.all.item("listPrint").disabled = true;
	setDisplayItem('spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,btnMaintain,btnMaintain1,btnMaintain2,btnMaintain3,approveAll,spanListHidden,spanListPrint','H');
}
function sysst016(){
	if (""==form1.q_year.value){
		alert("請先執行查詢");
	}else{
	var url = "sysst016p.jsp?q_year=" + form1.q_year.value + "&editID=" + form1.editID.value +"&q_enterOrg=" + form1.q_enterOrg.value;
	window.open(url);
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:100px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" width="20%">單位：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N')" value="..." title="機關輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>年度：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_year" size="15" maxlength="15" value="<%=obj.getQ_year()%>">
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

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="listPrint">
	<input class="toolbar_default" type="button" followPK="true" name="listPrint" value="報表列印" disabled="true" onClick="sysst016()">&nbsp;|
	<span>
</td></tr>

<!--Form區============================================================-->
<tr><td class="queryTDLable" style="text-align:Left">
機關人數：<%=obj.get_people_number()%>人
		<input class="field_P" type="hidden" name="editID" size="20" maxlength="20" value="<%=obj.getEditID()%>">
		
		<!-- 單位：<input class="field_P" type="text" name="enterOrgem" size="20" maxlength="20" value="<%=obj.getQ_enterOrgName()%>"> -->
		<!-- 年度：<input class="field_P" type="text" name="qyear" size="20" maxlength="20" value="<%=obj.getQ_year()%>"> -->
		<input class="field" type="hidden" name="propertyNo" size="15" maxlength="15" value="<%=obj.getPropertyNo()%>">
		<input class="field" type="hidden" name="propertyName" size="100" maxlength="100" value="<%=obj.getPropertyName()%>">
		<input class="field" type="hidden" name="limitYear" size="3" maxlength="3" value="<%=obj.getLimitYear()%>">
		<input class="field" type="hidden" name="propertyUnit" size="50" maxlength="50" value="<%=obj.getPropertyUnit()%>">
		<input class="field" type="hidden" name="subTotal" size="15" maxlength="15" value="<%=obj.getSubTotal()%>">
		<input class="field" type="hidden" name="amount1" size="15" maxlength="15" value="<%=obj.getAmount1()%>">
		<input class="field" type="hidden" name="amount2" size="15" maxlength="15" value="<%=obj.getAmount2()%>">
		<input class="field" type="hidden" name="amount3" size="15" maxlength="15" value="<%=obj.getAmount3()%>">
		<input class="field" type="hidden" name="amount4" size="15" maxlength="15" value="<%=obj.getAmount4()%>">
		<input class="field" type="hidden" name="amount5" size="15" maxlength="15" value="<%=obj.getAmount5()%>">
		<input class="field" type="hidden" name="amount6" size="15" maxlength="15" value="<%=obj.getAmount6()%>">
</td></tr>

<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer" style="width: 100%;	height: 277px;">
<table class="table_form" width="100%" cellspacing="0" cellpadding="100%">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>-->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">使用年限</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">小計</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">前一年</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">前二年</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">前三年</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">前四年</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">前五年</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">其他年度</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,true,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script>
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
		case "insertError":
		case "queryAll":
			setFormItem("listPrint","O");
				break;
			}			
}
</script>
</body>
</html>



