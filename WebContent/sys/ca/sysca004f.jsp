<!--
程式目的：保管使用人資料
程式代號：sysca004f
程式日期：0950321
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA004F)obj.queryOne();	
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
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("incumbencyYN","Y")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.keeperNo,"保管使用人代碼");	
		alertStr += checkAlphaInt(form1.keeperNo,"保管使用人代碼");		
		alertStr += checkEmpty(form1.keeperName,"保管使用人姓名");
		alertStr += checkEmpty(form1.incumbencyYN,"是否現任");
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){
		 alert(alertStr); 
		 return false; 
	}else{
		form1.keeperNo.disabled = false ;
	}
	beforeSubmit();
}
function queryOne(enterOrg,keeperNo){
	form1.enterOrg.value=enterOrg;
	form1.keeperNo.value=keeperNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if (form1.state.value=="insert" || form1.state.value=="insertError" || form1.state.value=="update" || form1.state.value=="updateError") {	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{		
		form1.state.value="queryOne";				
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function init() {
//	if(form1.state.value != "insert"){
//		form1.keeperNo.disabled = true ;
//		form1.keeperName.disabled = true ;
//	}
	
	//document.all.item("spanUpdate").style.display = "none";
	
	if('<%=user.getIsAdminManager()%>' == 'Y'){
		$("tr[name='tr_enterOrg']").show();
	}else{
		$("tr[name='tr_enterOrg']").hide();
	}	
}

//停用確認使用者在該機關是否還有財產 	 	 
function checkKeeper(){	 	 
    var incumbencyYN = form1.incumbencyYN.value;	 	 
    var keeperno = form1.keeperNo.value;	 	 
    var organID = form1.enterOrg.value;	 	 
    if (incumbencyYN == "N") {	 	 
        var queryValue = "?keeperno=" + keeperno + "&organID=" + organID +"&_=" + (new Date()).getTime();	 	 
        var xmlDoc = document.createElement("xml");	 	 
        xmlDoc.async = false;	 	 
        if (xmlDoc.load("../../home/xmlCheckProperty.jsp" + queryValue)) {	 	 
            var matchStr = xmlDoc.documentElement.childNodes.item(0).getAttribute("matchStr");	 	 
            if (matchStr != "") {	 	 
                alert(matchStr);                	 	 
            }	 	 
        }	 	 
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
	<jsp:include page="sysca004q.jsp" />
</div>


<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr name='tr_enterOrg'>
		<td class="td_form"><font color="red">*</font>所屬機關：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopOrgan("field","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName(),"")%>
		</td>	
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>保管使用人代碼：</td>
		<td class="td_form_white">
		 <input class="field_P" type="text" name="keeperNo" size="10" maxlength="10" value="<%=obj.getKeeperNo()%>"></td>
	    <td class="td_form"><font color="red">*</font>保管使用人姓名：</td>
	    <td class="td_form_white">
			<input class="field" type="text" name="keeperName" size="20" maxlength="20" value="<%=obj.getKeeperName()%>">		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>是否現任：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="incumbencyYN" onchange="checkKeeper()">
				<%=util.View.getYNOption(obj.getIncumbencyYN())%>
			</select>
		</td>
	</tr>
	<tr style='display:none'>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="5"><%=obj.getNotes()%></textarea>		</td>
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">保管使用人代碼</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">保管使用人姓名</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">是否現任</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">備註</a></th>
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