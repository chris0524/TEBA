<!--
程式目的：廠商資料
程式代號：sysca005f
程式日期：0950321
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setQ_enterOrg(user.getOrganID());
obj.setEnterOrg(user.getOrganID());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA005F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}

objList = obj.queryAll();

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
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.storeNo,"廠商代碼");	
		alertStr += checkAlphaInt(form1.storeNo,"廠商代碼");
		alertStr += checkEmpty(form1.storeName,"廠商名稱");
		if (form1.companyID.value!="") {
			alertStr += checkCompID(form1.companyID,"統一編號");
		}
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,storeNo){
	form1.enterOrg.value=enterOrg;
	form1.storeNo.value=storeNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">所屬機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"")%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">廠商代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_storeNo" size="10" maxlength="10" value="<%=obj.getQ_storeNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">廠商名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_storeName" size="10" maxlength="10" value="<%=obj.getQ_storeName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">連絡電話一：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_tel1" size="20" maxlength="20" value="<%=obj.getQ_tel1()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">連絡電話二：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_tel2" size="20" maxlength="20" value="<%=obj.getQ_tel2()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">連絡人：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_seller" size="10" maxlength="10" value="<%=obj.getQ_seller()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">傳真號碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_fax" size="20" maxlength="20" value="<%=obj.getQ_fax()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">備註：</td>
		<td class="queryTDInput">
			<input class="field_Q" name="q_notes" type="text" size="20" value="<%=obj.getQ_notes()%>">
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
		<td class="td_form"><font color="red">*</font>所屬機關：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName(),"")%>		
		</td>
		<td class="td_form"><font color="red">*</font>廠商代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="storeNo" size="10" maxlength="10" value="<%=obj.getStoreNo()%>"></td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>廠商名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="storeName" size="20" maxlength="25" value="<%=obj.getStoreName()%>">		</td>
		<td class="td_form">統一編號：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="companyID" size="10" maxlength="8" value="<%=obj.getCompanyID()%>">		</td>
	</tr>
	<tr>
	  <td class="td_form">連絡人：</td>
	  <td class="td_form_white">
			<input class="field" type="text" name="seller" size="10" maxlength="10" value="<%=obj.getSeller()%>">		</td>
	  <td class="td_form">傳真號碼：</td>
	  <td class="td_form_white">
			<input class="field" type="text" name="fax" size="20" maxlength="20" value="<%=obj.getFax()%>">		</td>
	</tr>
	<tr>
	  <td class="td_form">連絡電話一：</td>
	  <td class="td_form_white">
			<input class="field" type="text" name="tel1" size="20" maxlength="20" value="<%=obj.getTel1()%>">		</td>
	  <td class="td_form">連絡電話二：</td>
	  <td class="td_form_white">
			<input class="field" type="text" name="tel2" size="20" maxlength="20" value="<%=obj.getTel2()%>">		</td>
	</tr>
	
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
			<textarea class="field" name="notes" cols="24" rows="4"><%=obj.getNotes()%></textarea>		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white"> [
		  <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
		  /
		  <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
		  ] </td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="true">
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">廠商代碼</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">廠商名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">連絡電話</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">連絡人</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">傳真號碼</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,false,false,false};
	boolean displayArray[] = {false,true,true,true,true,true};
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



