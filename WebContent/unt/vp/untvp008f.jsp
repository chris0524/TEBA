<!--
程式目的：有價證券增減值作業－減少股份
程式代號：untvp008f
程式日期：0941017
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP008F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.vp.UNTVP008F)obj.queryOne();	
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
	new Array("adjustType","2")
);

function checkField(){
	var checkAdjustBookSheet = parseInt(form1.adjustBookSheet.value);
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.serialNo1,"股份次序");
		//alertStr += checkFloat(form1.oldBookUA,"原每張股數",7,0);
		//alertStr += checkFloat(form1.oldBookSheet,"原張數",5,0);
		alertStr += checkFloat(form1.oldBookAmount,"原總股數",12,0);
		alertStr += checkFloat(form1.oldBookUV,"原每股單價",5,0);
		alertStr += checkFloat(form1.oldBookValue,"原總價",15,0);
		//alertStr += checkFloat(form1.adjustBookUA,"減少每張股數",7,0);
		//alertStr += checkEmpty(form1.adjustBookSheet,"減少張數");
		//alertStr += checkFloat(form1.adjustBookSheet,"減少張數",5,0);
		alertStr += checkFloat(form1.adjustBookAmount,"減少總股數",12,0);
		alertStr += checkFloat(form1.adjustBookUV,"減少每股單價",5,0);
		alertStr += checkFloat(form1.adjustBookValue,"減少總價",15,0);
		//alertStr += checkEmpty(form1.adjustProofS,"減少證明書編號起");
		//alertStr += checkEmpty(form1.adjustProofE,"減少證明書編號訖");
		//alertStr += checkFloat(form1.newBookUA,"新每張股數",7,0);
		//alertStr += checkFloat(form1.newBookSheet,"新張數",5,0);
		alertStr += checkFloat(form1.newBookAmount,"新總股數",12,0);
		alertStr += checkFloat(form1.newBookUV,"新每股單價",5,0);
		alertStr += checkFloat(form1.newBookValue,"新總價",15,0);
		alertStr += checkLen(form1.notes, "備註", 250);
		//減少張數
		/*if (checkAdjustBookSheet<=0){
			form1.adjustBookSheet.style.backgroundColor=errorbg;
			alertStr +="減少張數必須 > 0 !\n";
		}else
			form1.adjustBookSheet.style.backgroundColor="";
		//計算減少總股數,減少總價,新張數,新總股數,新總價
			var oldBookAmount = parseInt(form1.oldBookAmount.value);
			var oldBookValue = parseInt(form1.oldBookValue.value);
			//var oldBookSheet = parseInt(form1.oldBookSheet.value);
			var adjustBookUA = parseInt(form1.adjustBookUA.value);
			var adjustBookSheet = parseInt(form1.adjustBookSheet.value);
			var adjustBookAmount = adjustBookUA * adjustBookSheet;
			var adjustBookUV = parseInt(form1.adjustBookUV.value);
			var adjustBookValue = adjustBookAmount * adjustBookUV;
			var newBookSheet = adjustBookSheet;
			var newBookAmount = oldBookAmount - adjustBookAmount;
			var newBookValue = oldBookValue - adjustBookValue;
			//減少張數不可大於原張數
				if(adjustBookSheet>oldBookSheet){
					alertStr +="減少張數不可大於原張數!\n";
					document.all.adjustBookSheet.focus();
					form1.adjustBookSheet.style.backgroundColor=errorbg;
				}else{
					form1.adjustBookSheet.style.backgroundColor="";
					form1.adjustBookAmount.value = adjustBookAmount ;
					form1.adjustBookValue.value = adjustBookValue;
					form1.newBookSheet.value = newBookSheet;
					form1.newBookAmount.value = newBookAmount;
					form1.newBookValue.value = newBookValue;
				//}*/
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
	var oldBookAmount = parseInt(form1.oldBookAmount.value);
	var oldBookValue = parseInt(form1.oldBookValue.value);
	var oldBookUV = parseInt(form1.oldBookUV.value);
	
	var adjustBookAmount = form1.adjustBookAmount.value;
	var adjustBookUV = parseInt(form1.adjustBookUV.value);
	var adjustBookValue =parseInt( adjustBookAmount * adjustBookUV);

	var newBookAmount = oldBookAmount - adjustBookAmount;
	var newBookValue = oldBookValue - adjustBookValue;
	//減少張數不可大於原張數
	if(form1.serialNo1.value==""){
		alert("請先選擇股份次序!\n");
		document.all.queryShare.focus();
	}else{

			form1.adjustBookAmount.value = adjustBookAmount ;
			form1.adjustBookValue.value = adjustBookValue;

			form1.newBookAmount.value = newBookAmount;
			form1.newBookValue.value = newBookValue;
		}
	}



function clickShare(){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-650)/2+250;
	prop=prop+"width=600,height=350,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	var enterOrg="<%=obj.getEnterOrg()%>";
	var enterOrgName="<%=obj.getEnterOrgName()%>";
	var ownership="<%=obj.getOwnership()%>";
	var propertyNo="<%=obj.getPropertyNo()%>";
	var propertyNoName="<%=obj.getPropertyNoName()%>";
	var serialNo="<%=obj.getSerialNo()%>";
	returnWindow=window.open("popUntvpShare.jsp?enterOrg="+enterOrg+"&enterOrgName="+enterOrgName+"&ownership="+ownership+"&propertyNo="+propertyNo+"&propertyNoName="+propertyNoName+"&serialNo="+serialNo+"&js=changeNumber();","",prop);
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
  <tr>
    <td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untvp006f.jsp');">增減值單資料</a></td>
    <td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untvp007f.jsp');">增加股份</a></td>
    <td ID=t3 CLASS="tab_border1">減少股份</td>
  </tr>
  <tr>
    <td class="tab_line2"></td>
    <td class="tab_line2"></td>
    <td class="tab_line1"></td>
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
	<tr>
		<td class="td_form"><font color="red">*</font>股份次序：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]
			<input class="field_P" type="button" name="queryShare" value="..." onClick="clickShare();">
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　原股份次序：
			[<input class="field_RO" type="text" name="oldSerialNo1" size="7" maxlength="7" value="<%=obj.getOldSerialNo1()%>">]
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
		</td>
	</tr>
	<tr>
		<td class="td_form">原股份資料：</td>
		<td class="td_form_white" colspan="3">
			總股數：<input class="field" type="text" name="oldBookAmount" size="10" maxlength="12" value="<%=obj.getOldBookAmount()%>">
		<br>
			每股單價：[<input class="field_RO" type="text" name="oldBookUV" size="5" maxlength="5" value="<%=obj.getOldBookUV()%>">]
			&nbsp;　　　總價：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">]
		<br>
			證明書編號：
			起 [<input class="field_RO" type="text" name="oldProofS" size="10" maxlength="15" value="<%=obj.getOldProofS()%>">]&nbsp;~&nbsp;
			訖 [<input class="field_RO" type="text" name="oldProofE" size="10" maxlength="15" value="<%=obj.getOldProofE()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">減少股份資料：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="adjustBookUA" size="5" maxlength="7" value="<%=obj.getAdjustBookUA()%>">
			<input class="field" type="hidden" name="adjustBookSheet" size="5" maxlength="5" value="<%=obj.getAdjustBookSheet()%>">
	 總股數：<input class="field" type="text" name="adjustBookAmount" size="10" maxlength="12" value="<%=obj.getAdjustBookAmount()%>" onChange="changeNumber();">
		<br>
			每股單價：[<input class="field_RO" type="text" name="adjustBookUV" size="5" maxlength="5" value="<%=obj.getAdjustBookUV()%>">]
			&nbsp;　　　總價：[<input class="field_RO" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>">]
		<br>
			證明書編號：
			起 <input class="field" type="text" name="adjustProofS" size="10" maxlength="15" value="<%=obj.getAdjustProofS()%>">&nbsp;~&nbsp;
			訖 <input class="field" type="text" name="adjustProofE" size="10" maxlength="15" value="<%=obj.getAdjustProofE()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">新股份資料：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="newBookUA" size="5" maxlength="7" value="<%=obj.getNewBookUA()%>">
			<input class="field_RO" type="hidden" name="newBookSheet" size="5" maxlength="5" value="<%=obj.getNewBookSheet()%>">
	 總股數：[<input class="field_RO" type="text" name="newBookAmount" size="10" maxlength="12" value="<%=obj.getNewBookAmount()%>">]
		<br>
			每股單價：[<input class="field_RO" type="text" name="newBookUV" size="5" maxlength="5" value="<%=obj.getNewBookUV()%>">]
			&nbsp;　　　總價：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">]
		<br>
			證明書編號：
			起 <input class="field" type="text" name="newProofS" size="10" maxlength="15" value="<%=obj.getNewProofS()%>">&nbsp;~&nbsp;
			訖 <input class="field" type="text" name="newProofE" size="10" maxlength="15" value="<%=obj.getNewProofE()%>">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">減少總股數</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">減少每股單價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">減少總價</a></th>
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



