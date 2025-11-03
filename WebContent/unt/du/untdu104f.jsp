<!--
程式目的：財產編號修正－動產
程式代號：untdu102f
程式日期：0990112
程式作者：Timtoy.Tsai
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU104F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	//obj = (unt.du.UNTDU104F)obj.queryOne();
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	//obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	//obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	//obj.delete();
}else if ("updateBatch".equals(obj.getState())) {
	if(obj.check()){
		obj.updateBatch();
	}
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){ 
	var alertStr="";
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
		alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.q_propertyNo,"財產編號");
	}else if(form1.state.value=="updateBatch"){
		if(parse(form1.q_newPropertyNo.value).length <= 0) { 
			alertStr += "注意：新的財產編號不可為空！\n";
		}else if(parse(form1.propertyNo.value).length > 0){
			if(form1.propertyNo.value.substring(0,1) != form1.q_newPropertyNo.value.substring(0,1)){
				alertStr += "注意：財產編號不可跨類變更！謝謝！\n";
			}
		}
	}

	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,enterOrgName,ownership,propertyNo,propertyNoName,serialNoS,serialNoE,lotNo){
	form1.enterOrg.value=enterOrg;
	form1.enterOrgName.value=enterOrgName;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.propertyNoName.value=propertyNoName;
	form1.serialNoS.value=serialNoS;
	form1.serialNoE.value=serialNoE;
	form1.lotNo.value=lotNo;
}

function init(){
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanClear").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	document.all.item("spanListPrint").style.display="none";
	document.all.item("spanListHidden").style.display="none";
}

function checkInsertSelect() {
	var sFlag = false;
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "sKeySet" && e.checked==true) sFlag = true;	    
	}
	if (sFlag) return "";
	else return "您尚未勾選任何資料，若無資料可供勾選，請重新查詢！\n";
}

function show_window(windowName){ 
	if (parse(checkInsertSelect()).length > 0) {alert(checkInsertSelect()); return false;}
	
	var winObj=document.all.item(windowName);
	var objHeight= winObj.style.height;
	var objWidth= winObj.style.width;
	objHeight= objHeight.substring(0,objHeight.length-2);
	objWidth= objWidth.substring(0,objWidth.length-2);
	winObj.style.top=(document.body.clientHeight-Number(objHeight)-80)/2;
	winObj.style.left=(document.body.clientWidth-Number(objWidth))/2;	
	winObj.style.display="block";
	form1.state.value = "updateBatch";
}
function hidden_window(windowName){
	var winObj=document.all.item(windowName);		
	winObj.style.display="none";
	form1.state.value = "init";
}
</script>
</head>

<body topmargin="0" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--批次更新============================================================-->
<div id="batchContainer" style="position:absolute;z-index: 25;left:0;top :0;width:480px;height:100px;display:none">
	<div class="queryTitle">批次更新視窗</div>
	<table class="queryTable"  border="1">
		<tr>
			<td class="queryTDLable" width="30%" height="5%">新的財產編號：</td>
			<td class="queryTDInput" width="70%" height="5%">
				<input class="field_Q" type="text" name="q_newPropertyNo" size="10" maxlength="10" value="<%=obj.getQ_newPropertyNo()%>" onBlur="getProperty('q_newPropertyNo','q_newPropertyNoName','3,4,5','Y');">
				<input class="field_Q" type="button" name="btn_q_propertyNo" onclick="popProperty('q_newPropertyNo','q_newPropertyNoName','3,4,5')" value="..." title="財產編號輔助視窗">
				[<input class="field_QRO" type="text" name="q_newPropertyNoName" size="20" maxlength="50" value="<%=obj.getQ_newPropertyNoName()%>">]
			</td>
		</tr>
		<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="updateSubmit" value="確　　定" />
			<input class="toolbar_default" followPK="false" type="button" name="updateCannel" value="取　　消" onClick="hidden_window('batchContainer')" />
		</td>
	</tr>
	</table>
</div>
<!--Query區============================================================-->
<div id="queryContainer" style="width:480px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="3">
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
[<input class="field_QRO" type="text" name="q_enterOrgName" size="26" maxlength="50" value="<%=obj.getQ_enterOrgName()%>">]
<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N')" value="..." title="機關輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" >	
	  	    <input class="field_Q" type="text" name="q_propertyNo" size="11" maxlength="11" value="<%=obj.getQ_propertyNo()%>">
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
		<td class="td_form" width="22%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >
		    [<input class="field_PRO" type="text" name="enterOrgName" size="26" maxlength="50" value="<%=obj.getEnterOrgName()%>">]&nbsp;&nbsp;&nbsp;
	      	權屬：
	        <select class="field_PRO" type="select" name="ownership">
	        	<%=util.View.getOnwerOption(obj.getOwnership())%>
	        </select>
        </td>
	</tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"3,4,5")%>&nbsp;&nbsp;&nbsp;
			批號：<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">財產分號：</td>
		<td class="td_form_white" colspan="3">&nbsp;
			<input class="field_RO" type="text" name="serialNoS" size="7" maxlength="7" value="<%=obj.getSerialNoS()%>">
			&nbsp;&nbsp;－&nbsp;&nbsp;
            <input class="field_RO" type="text" name="serialNoE" size="7" maxlength="7" value="<%=obj.getSerialNoE()%>">
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
	<span id="span_update_batch">
		<input class="toolbar_default" type="button" followPK="false" name="btn_update_batch" value="批次更新" onClick="show_window('batchContainer')">&nbsp;|
	</span>
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
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" ></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產分號（起）</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">財產分號（訖）</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">財產批號</a></th>	
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,true,true,true,true,true};
	boolean displayArray[] = {false,true,true,false,true,true,true,true,true};
	out.write(obj.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script type="text/javascript">
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



