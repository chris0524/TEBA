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
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU045F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.du.UNTDU045F)obj.queryOne();	
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
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.q_unitNo,"單位");
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.keeperNo,"使用人代碼");	
		alertStr += checkAlphaInt(form1.keeperNo,"使用人代碼");		
		alertStr += checkEmpty(form1.keeperName,"使用人姓名稱");
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
function queryOne(enterOrg,unitNo,keeperNo){
	form1.enterOrg.value=enterOrg;
	form1.unitNo.value=unitNo;
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
}
function chengOrg(){
	changeQ_enterOrg(form1.q_enterOrg.value ,'q_unitNo' ,'');
}
function changeQ_enterOrg(q_enterOrg ,q_No ,selectValue){
	var obj2 = document.all.item(q_No);
	obj2.options.length=0;
	var oOption = document.createElement("Option");	
	obj2.options.add(oOption);
	oOption.innerText = "請選擇";
	oOption.value = "";		
	var queryValue=form1.q_enterOrg.value;	
	if (queryValue!=""){
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;			
		if(xmlDoc.load("xmlQ_enterOrg.jsp?q_enterOrg="+queryValue)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
				unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = unitName;
				oOption.value = unitNo;		
		      	if(unitNo == selectValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
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
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
	      <input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
		  [<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName()%>">]
		  <input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=chengOrg()')" value="..." title="機關輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_unitNo">
				<script>changeQ_enterOrg(form1.q_enterOrg.value,'q_unitNo','<%=obj.getQ_unitNo()%>');</script>
			</select>
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
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">單位代碼：</td>
		<td class="td_form_white">
		 [<input class="field_QRO" type="text" name="unitNo" size="10" maxlength="10" value="<%=obj.getUnitNo()%>">]</td>
	    <td class="td_form">單位名稱：</td>
	    <td class="td_form_white">
	    [<input type="text" class="field_QRO" name="unitName" value="<%=obj.getUnitName()%>" size="20">]
	    </td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>使用人代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="keeperNo" size="10" maxlength="10" value="<%=obj.getKeeperNo()%>">
		</td>
	    <td class="td_form"><font color="red">*</font>使用人姓名：</td>
	    <td class="td_form_white" colsapn="3">
			<input class="field" type="text" name="keeperName" size="10" maxlength="10" value="<%=obj.getKeeperName()%>">		
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>是否現任：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="incumbencyYN">
				<%=util.View.getYNOption(obj.getIncumbencyYN())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="5"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
		[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	    /<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
	    ]
	    </td>
	</tr>
	</table>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer"  style="width: 100%; height: 240px;	overflow: auto;	scrollbar-base-color:#eeeeee;">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">使用人代碼</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">使用人姓名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">是否現任</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">備註</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,true};
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



