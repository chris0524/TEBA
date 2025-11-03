<!--
程式目的：保管使用單位
程式代號：sysca003f
程式日期：0950320
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA003F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA003F)obj.queryOne();	
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
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
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
		//alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.unitNo,"單位代碼");		
		alertStr += checkAlphaInt(form1.unitNo,"單位代碼");		
		alertStr += checkEmpty(form1.unitName,"單位名稱");
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,unitNo){
	form1.enterOrg.value=enterOrg;
	form1.unitNo.value=unitNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	//alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.unitNo,"單位代碼");
	if (form1.state.value=="insert" || form1.state.value=="insertError" || form1.state.value=="update" || form1.state.value=="updateError") {	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		} else {
			form1.state.value="queryAll";		
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}

	//if(form1.state.value=="delete"){
	//	alert("刪除此保管使用單位，將一併刪除其關聯的保管使用人"); 
	//}

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
<div id="queryContainer" style="width:400px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<% request.setAttribute("qBean",obj);%>
	<jsp:include page="sysca003q.jsp" />
</div>


<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">	
	<tr name="tr_enterOrg">
		<td class="td_form"><font color="red">*</font>所屬機關：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName(),"")%>
		</td>	
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>單位代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="unitNo" size="10" maxlength="10" value="<%=obj.getUnitNo()%>"></td>	
	</tr>		
	<tr>
	  <td class="td_form"><font color="red">*</font>單位名稱：</td>
	  <td class="td_form_white">
		<input class="field" type="text" name="unitName" size="30" maxlength="30" value="<%=obj.getUnitName()%>">		</td>
	</tr>		
	<tr>
	  <td class="td_form">預設部門別：</td>
	  <td class="td_form_white">		
			<select class="field" type="select" name="deprUnit">
			　　<%=util.View.getOption("select deprUnitNo, deprUnitName from SYSCA_DeprUnit where enterorg = '" + obj.getEnterOrg() + "'", obj.getDeprUnit())%>
			</select>
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
			<input class="field" name="notes" type="text" size="50" maxlength="100" value="<%=obj.getNotes()%>"></td>
	</tr>
	<tr style='display:none'>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">[
		  <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
		  /
		  <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
		  ]</td>	
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">單位代碼</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">單位名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">預設部門別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">備註</a></th>		
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,false,false};
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