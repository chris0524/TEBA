<!--
程式目的：非消耗品型式廠牌資料修正
程式代號：untdu004f
程式日期：0951004
程式作者：sam.hsueh
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.du.UNTDU004F)obj.queryOne();	
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
	//new Array("ownership","1")	
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
	}

	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,lotNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.lotNo.value=lotNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}
function init(){
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:800px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="3">
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput" colspan="3">
			<input type="hidden" name="q_enterOrg" value="<%=obj.getQ_enterOrg()%>" >
	    [
	      <input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName()%>">
	  	]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"6")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"6")%>
	  </td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
	    <td class="queryTDInput">
	  		起 <input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp; 
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
	   </td>
	</tr>
	<tr>
		<td class="queryTDLable">原財產分號：</td>
	    <td class="queryTDInput">
	    	 起 <input class="field_Q" type="text" name="q_oldSerialNoS" size="7" maxlength="7" value="<%=obj.getQ_oldSerialNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_oldSerialNoE" size="7" maxlength="7" value="<%=obj.getQ_oldSerialNoE()%>">
    </td>
	</tr>
	<tr>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyName1" size="40" maxlength="40" value="<%=obj.getQ_propertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">廠牌：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_nameplate" size="40" maxlength="60" value="<%=obj.getQ_nameplate()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">型式：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_specification" size="40" maxlength="80" value="<%=obj.getQ_specification()%>">
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
		<td class="td_form_white" colspan="3">
			<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >
	    [<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]&nbsp;&nbsp;&nbsp;
      	權屬：
      <select class="field_P" type="select" name="ownership">
        <%=util.View.getOnwerOption(obj.getOwnership())%>
      </select>
      
      </td>
      
	</tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyNo" size="11" maxlength="11" value="<%=obj.getPropertyNo()%>">&nbsp;&nbsp;&nbsp;	
			財產分號：起 <input class="field_P" type="text" name="serialNoS" size="7" maxlength="7" value="<%=obj.getSerialNoS()%>">&nbsp;~&nbsp;
                                                     訖<input class="field_P" type="text" name="serialNoE" size="7" maxlength="7" value="<%=obj.getSerialNoE()%>">&nbsp;&nbsp;&nbsp;
           	 財產批號：<input class="field_P" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">                                         
		</td>
	</tr>
	
	<tr>
		<td class="td_form">原財產編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="oldPropertyNo" size="11" maxlength="11" value="<%=obj.getOldPropertyNo()%>">&nbsp;&nbsp;&nbsp;
			起 <input class="field_P" type="text" name="oldSerialNoS" size="7" maxlength="7" value="<%=obj.getOldSerialNoS()%>">&nbsp;~&nbsp; 
			訖<input class="field_P" type="text" name="oldSerialNoE" size="7" maxlength="7" value="<%=obj.getOldSerialNoE()%>">
		</td>
	</tr>
	
	<tr>
		<td class="td_form">財產別名：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyName1" size="40" maxlength="40" value="<%=obj.getPropertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field_P","enterDate",obj.getEnterDate())%>&nbsp;&nbsp;&nbsp; 
			購置日期：<%=util.View.getPopCalndar("field_P","buyDate",obj.getBuyDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">資料狀態：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_P" type="select" name="dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">原始入帳：</td>
		<td class="td_form_white" colspan="3">
			數量：<input class="field_P" type="text" name="originalAmount" size="7" maxlength="7" value="<%=obj.getOriginalAmount()%>">&nbsp;&nbsp;&nbsp;
			單價：<input class="field_P" type="text" name="originalUnit" size="13" maxlength="13" value="<%=obj.getOriginalUnit()%>">&nbsp;&nbsp;&nbsp;
			總價：<input class="field_P" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">帳面金額：</td>
		<td class="td_form_white" colspan="3">
			數量：<input class="field_P" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">&nbsp;&nbsp;&nbsp;
			總價：<input class="field_P" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">廠牌：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="nameplate" size="60" maxlength="60" value="<%=obj.getNameplate()%>">
		</td>
	</tr>
	<tr>	
		<td class="td_form">型式：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="specification" size="60" maxlength="80" value="<%=obj.getSpecification()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white" colspan="3">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號起-訖</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">原財產分號起-訖</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">廠牌</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">型式</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,false,false,false,false};
	boolean displayArray[] = {false,false,true,false,true,true,true,true};
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
		case "update":
			form1.ownership.value="<%=obj.getOwnership()%>";
			break;		
	}
	return true;
}
</script>
</body>
</html>



