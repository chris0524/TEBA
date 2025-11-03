<!--
程式目的：有價證券增減值作業－增加股份
程式代號：untvp007f
程式日期：0941014
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP007F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.vp.UNTVP007F)obj.queryOne();	
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

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._VP_ADJUST, 2);
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
<script language="javascript" src="untvp006q.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("enterOrg","<%=obj.getEnterOrg()%>"),
	new Array("ownership","<%=obj.getOwnership()%>"),
	new Array("caseNo","<%=obj.getCaseNo()%>"),
	new Array("propertyNo","<%=obj.getPropertyNo()%>"),
	new Array("serialNo","<%=obj.getSerialNo()%>"),
	new Array("adjustType","1"),
	new Array("oldBookUA","0"),
	new Array("oldBookSheet","0"),
	new Array("oldBookAmount","0"),
	new Array("oldBookUV","0"),
	new Array("oldBookValue","0")
);
function checkField(){
	//var checkAdjustBookUA = parseInt(form1.adjustBookUA.value);
	//var checkAdjustBookSheet = parseInt(form1.adjustBookSheet.value);
	var checkAdjustBookUV = parseInt(form1.adjustBookUV.value);
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		//form1.newBookUA.value = form1.adjustBookUA.value;
		//form1.newBookSheet.value = form1.adjustBookSheet.value;
		form1.newBookAmount.value = form1.adjustBookAmount.value;
		form1.newBookUV.value = form1.adjustBookUV.value;
		form1.newBookValue.value = form1.adjustBookValue.value;
		form1.newProofS.value = form1.adjustProofS.value;
		form1.newProofE.value = form1.adjustProofE.value;
		alertStr += checkFloat(form1.oldBookUA,"原每張股數",7,0);
		alertStr += checkFloat(form1.oldBookSheet,"原張數",5,0);
		alertStr += checkFloat(form1.oldBookAmount,"原總股數",12,0);
		alertStr += checkFloat(form1.oldBookUV,"原每股單價",5,0);
		alertStr += checkFloat(form1.oldBookValue,"原總價",5,0);
		//alertStr += checkEmpty(form1.adjustBookUA,"增加每張股數");
		//alertStr += checkFloat(form1.adjustBookUA,"增加每張股數",7,0);
		//alertStr += checkEmpty(form1.adjustBookSheet,"增加張數");
		//alertStr += checkFloat(form1.adjustBookSheet,"增加張數",5,0);
		alertStr += checkFloat(form1.adjustBookAmount,"增加總股數",12,0);
		alertStr += checkEmpty(form1.adjustBookUV,"增加每股單價");
		alertStr += checkFloat(form1.adjustBookUV,"增加每股單價",5,0);
		alertStr += checkFloat(form1.adjustBookValue,"增加總價",15,0);
		//alertStr += checkEmpty(form1.adjustProofS,"增加證明書編號起");
		//alertStr += checkEmpty(form1.adjustProofE,"增加證明書編號訖");
		alertStr += checkLen(form1.notes, "備註", 250);
		//增加每張股數
		/*if (checkAdjustBookUA<=0){
			form1.adjustBookUA.style.backgroundColor=errorbg;
			alertStr +="增加每張股數必須 > 0 !\n";
		}else
			form1.adjustBookUA.style.backgroundColor="";
		//增加張數
		if (checkAdjustBookSheet<=0){
			form1.adjustBookSheet.style.backgroundColor=errorbg;
			alertStr +="增加張數必須 > 0 !\n";
		}else
			form1.adjustBookSheet.style.backgroundColor="";*/
		//增加每股單價
		if (checkAdjustBookUV<0){
			form1.adjustBookUV.style.backgroundColor=errorbg;
			alertStr +="增加每股單價必須 >= 0 !\n";
		}else
			form1.adjustBookUV.style.backgroundColor="";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	document.all.item("spanQueryAll").style.display="none";
	<!-- 查出的資料若其增減值已審核，均不允許新增、修改、刪除各標籤資料 -->
	if (<%=obj.getVerify().equals("Y")%>){
		setFormItem("insert,update,delete,clear,confirm", "R");
	}
	if (form1.state.value=="queryOne" && form1.enterOrg.value!=<%="\""+user.getOrganID()+"\""%>) {		
		setFormItem("insert,update,delete,clear,confirm", "R");
	}
}

function changeNumber(){
	//var adjustBookUA = parseInt(form1.adjustBookUA.value);
	//var adjustBookSheet = parseInt(form1.adjustBookSheet.value);
	//var adjustBookAmount = adjustBookUA * adjustBookSheet;
	var adjustBookAmount = parseInt(form1.adjustBookAmount.value);
	var adjustBookUV = parseInt(form1.adjustBookUV.value);
	var adjustBookValue = adjustBookAmount * adjustBookUV;
	if(AllTrim(form1.adjustBookUV).length<=0){
		form1.adjustBookAmount.value ="" ;
		form1.adjustBookValue.value = "" ;
	}else{
		form1.adjustBookAmount.value = adjustBookAmount ;
		form1.adjustBookValue.value = adjustBookValue;
	}
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
  <tr>
    <td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untvp006f.jsp');">增減值單資料</td>
    <td ID=t2 CLASS="tab_border1">增加股份</td>
    <td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untvp008f.jsp');">減少股份</a></td>
  </tr>
  <tr>
    <td class="tab_line2"></td>
    <td class="tab_line1"></td>
    <td class="tab_line2"></td>
  </tr>
</TABLE>
<input class="field" type="hidden" name="verify" size="1" maxlength="1" value="<%=obj.getVerify()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTVP006Q",obj);%>
	<jsp:include page="untvp006q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<!-- 入帳機關 -->
			<input class="field_P" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			<input class="field_P" type="hidden" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">
		<!-- 權屬 -->
			<input class="field_P" type="hidden" name="ownership" size="1" maxlength="1" value="<%=obj.getOwnership()%>">
		<!-- 電腦單號 -->
			<input class="field_P" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		<!-- 財產編號 -->
			<input class="field_P" type="hidden" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>">
			<input class="field_P" type="hidden" name="propertyNoName" size="10" maxlength="10" value="<%=obj.getPropertyNoName()%>">
		<!-- 財產分號 -->
			<input class="field_P" type="hidden" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">
		<!-- 增減別 -->
			<input class="field" type="hidden" name="adjustType" size="1" maxlength="1" value="<%=obj.getAdjustType()%>">
		<!-- 原每張股數 -->
			<input class="field" type="hidden" name="oldBookUA" size="5" maxlength="7" value="<%=obj.getOldBookUA()%>">
		<!-- 原張數 -->
			<input class="field" type="hidden" name="oldBookSheet" size="5" maxlength="5" value="<%=obj.getOldBookSheet()%>">
		<!-- 原總股數 -->
			<input class="field" type="hidden" name="oldBookAmount" size="10" maxlength="12" value="<%=obj.getOldBookAmount()%>">
		<!-- 原每股單價 -->
			<input class="field" type="hidden" name="oldBookUV" size="5" maxlength="5" value="<%=obj.getOldBookUV()%>">
		<!-- 原總價 -->
			<input class="field" type="hidden" name="oldBookValue" size="5" maxlength="5" value="<%=obj.getOldBookValue()%>">
		<!-- 新每張股數 -->
			<input class="field" type="hidden" name="newBookUA" size="5" maxlength="7" value="<%=obj.getNewBookUA()%>">
		<!-- 新張數 -->
			<input class="field" type="hidden" name="newBookSheet" size="5" maxlength="5" value="<%=obj.getNewBookSheet()%>">
		<!-- 新總股數 -->
			<input class="field" type="hidden" name="newBookAmount" size="10" maxlength="12" value="<%=obj.getNewBookAmount()%>">
		<!-- 新每股單價 -->
			<input class="field" type="hidden" name="newBookUV" size="5" maxlength="5" value="<%=obj.getNewBookUV()%>">
		<!-- 新總價 -->
			<input class="field" type="hidden" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">
		<!-- 新證明書編號起 -->
			<input class="field" type="hidden" name="newProofS" size="10" maxlength="15" value="<%=obj.getNewProofS()%>">
		<!-- 新證明書編號訖 -->
			<input class="field" type="hidden" name="newProofE" size="10" maxlength="15" value="<%=obj.getNewProofE()%>">
	<tr>
		<td class="td_form">股份次序：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]
			&nbsp;&nbsp;&nbsp;　　　　　　原股份次序：
			[<input class="field_RO" type="text" name="oldSerialNo1" size="7" maxlength="7" value="<%=obj.getOldSerialNo1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">增加股份資料：</td>
		<td class="td_form_white" colspan="3">
			總股數：<input class="field" type="text" name="adjustBookAmount" size="10" maxlength="12" value="<%=obj.getAdjustBookAmount()%>">
		<br>
			<font color="red">*</font>每股單價：<input class="field" type="text" name="adjustBookUV" size="5" maxlength="5" value="<%=obj.getAdjustBookUV()%>"  onFocusOut="changeNumber();">
			&nbsp;&nbsp;&nbsp;　　　總價：[<input class="field_RO" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>">]
		<br>
			證明書編號：
			起<input class="field" type="text" name="adjustProofS" size="10" maxlength="15" value="<%=obj.getAdjustProofS()%>">&nbsp;~&nbsp;
			訖<input class="field" type="text" name="adjustProofE" size="10" maxlength="15" value="<%=obj.getAdjustProofE()%>">
		</td>
	</tr>
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
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

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">股份次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">增加總股數</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">增加每股單價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增加總價</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false};
	boolean displayArray[] = {false,false,false,false,false,true,true,true,true};
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



