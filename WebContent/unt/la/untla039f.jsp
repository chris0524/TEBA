<!--
程式目的：土地所有權狀審核作業
程式代號：untla039f
程式日期：0940822
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA039F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA039F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("approveAll".equals(obj.getState())) {
	obj.approveAll(1);
}else if ("unApproveAll".equals(obj.getState())) {
	obj.approveAll(0);
}else if ("approveOne".equals(obj.getState())) {
	obj.approveOne(1);
}else if ("unApproveOne".equals(obj.getState())) {
	obj.approveOne(0);
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
<link rel="stylesheet" href="../../js/default.css?1=2dsddsssss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if (form1.state.value=="queryAll"||form1.state.value=="queryOne") {
		alertStr += checkEmpty(form1.q_ownership,"權屬");
		alertStr += checkEmpty(form1.q_signNo1,"土地標示");
		alertStr += checkEmpty(form1.q_signNo2,"土地標示");
	} else if (form1.state.value=="approveOne"||form1.state.value=="unApproveOne") {
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");		
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		form1.editID.value = "<%=user.getUserID()%>";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function clickApproveAll(){
	if(confirm("您確定要審核列表上的全部土地權狀?")){
		document.all('state').value='approveAll';
		form1.editID.value = "<%=user.getUserID()%>";		
		form1.submit();
	}

}

function clickUnApproveAll(){
	if(confirm("您確定要取消審核列表上的全部土地權狀?")){
		document.all('state').value='unApproveAll';
		form1.editID.value = "<%=user.getUserID()%>";				
		form1.submit();
	}

}

function clickApproveOne(){
	if(confirm("您確定要審核此筆土地權狀?")){
		document.all('state').value='approveOne';		
		form1.editID.value = "<%=user.getUserID()%>";		
		form1.submit();
	}
}

function clickUnApproveOne(){
	if(confirm("您確定要取消審核此筆土地權狀?")){
		document.all('state').value='unApproveOne';
		form1.submit();
	}
}


function init(){
	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListPrint,spanListHidden","H");
	if (form1.state.value=="queryOneSuccess") {
		setFormItem("approveOne,unApproveOne","O");
	} else {
		setFormItem("approveOne,unApproveOne","R");
	}	
	if (form1.state.value=="logout") {	
		alert("對不起! 遺失重要資訊, 請重新登入!");	
		window.top.location.href = "../../index.jsp";
	}	
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:600px;height:150px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
			</select>
			&nbsp;地號：
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=obj.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=obj.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td colspan="3" class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>			
			　權狀審核：<select class="field_Q" type="select" name="q_proofVerify">
			<%=util.View.getYNOption(obj.getQ_proofVerify())%>
			</select>			
			<input class="field_Q" type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>" >			
		</td>
	  </tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
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
          <td class="td_form" width="88">是否審核：</td>
          <td class="td_form_white">
              [<input name="proofVerifyName" type="text" class="field_RO" value="<%=obj.getProofVerifyName()%>" size="12">]
<input type="hidden" name="proofVerify" value="<%=obj.getProofVerify()%>">
              <input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" ></td>
          <td align="right" class="td_form">權屬：</td>
          <td class="td_form_white">
              [<input name="ownershipName" type="text" class="field_RO" value="<%=obj.getOwnershipName()%>" size="12">]
<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">          
    </td>
        </tr>
        <tr>
          <td class="td_form">財產編號：</td>
          <td colspan="3" class="td_form_white">[<input name="propertyNo" type="text" class="field_RO" value="<%=obj.getPropertyNo()%>" size="15">]　 分號：[<input name="serialNo" type="text" class="field_RO" value="<%=obj.getSerialNo()%>" size="7">] 　 財產名稱：[<input name="propertyName" type="text" class="field_RO" value="<%=obj.getPropertyName()%>" size="20">]<br>
    別名： [<input name="propertyName1" type="text" class="field_RO" value="<%=obj.getPropertyName1()%>" size="30">]</td>
        </tr>
        <tr>
          <td class="td_form">土地標示：</td>
          <td colspan="3" class="td_form_white"><!-- 代碼：[
            <input name="signNo" type="text" class="field_RO" value="<%=obj.getSignNo()%>" size="15">
              ]　名稱： -->[<input class="field_RO" type="text" size="70" name="signName" value="<%=obj.getSignName()%>">]
          </td>
        </tr>
        <tr>
          <td class="td_form">權狀資料：</td>
          <td colspan="3" class="td_form_white">所有權登記日期：[<input name="ownershipDate" type="text" class="field_RO" value="<%=obj.getOwnerShipDate()%>" size="12">]　權狀字號 ：[<input name="proofDoc" type="text" class="field_RO" id="proofDoc" value="<%=obj.getProofDoc()%>">]</td>
        </tr>
        <tr>
          <td class="td_form">地目：</td>
          <td class="td_form_white">[<input class="field_RO" type="text" name="fieldName" value="<%=obj.getFieldName()%>"><input name="filed" type="hidden" class="field_RO" value="<%=obj.getField()%>" size="12">]
		  </td><td class="td_form">等則：</td>
          <td class="td_form_white">[<input class="field_RO" type="text" name="landRule" value="<%=obj.getLandRule()%>">]</td>
        </tr>
        <tr>
          <td class="td_form">面積資訊：</td>
          <td class="td_form_white" colspan="3">整筆面積(㎡)：[<input class="field_RO" type="text" name="area" size="12" maxlength="12" value="<%=obj.getArea()%>">] <br>
      權利分子：[<input class="field_RO" type="text" name="holdNume" size="9" maxlength="10" value="<%=obj.getHoldNume()%>">] &nbsp;權利分母：[<input class="field_RO" type="text" name="holdDeno" size="9" maxlength="10" value="<%=obj.getHoldDeno()%>" >] &nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]</td>
        </tr>
        <tr>
          <td class="td_form">異動資訊：</td>
          <td class="td_form_white" colspan="3">人員/日期：[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/ <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
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
<span id="btnMaintain1">
<input class="toolbar_default" type="button" followPK="false" name="approveOne" value="審　核" onClick="clickApproveOne();">&nbsp;|
</span>
<span id="btnMaintain2">
<input class="toolbar_default" type="button" followPK="false" name="unApproveOne" value="取消審核" onClick="clickUnApproveOne();">&nbsp;|
</span>
<span id="btnMaintain3">
<input class="toolbar_default" type="button" followPK="false" name="approveAll" value="全部審核" onClick="clickApproveAll();">&nbsp;|
</span>
<span id="btnMaintain4">
<input class="toolbar_default" type="button" followPK="false" name="unApproveAll" value="全部取消審核"  onClick="clickUnApproveAll();">&nbsp;|
</span>
<span id="btnReadOnly1">
<input class="toolbar_default" type="button" followPK="false" name="listHidden1" value="列表隱藏" onclick="listToHidden(this,'formContainer','listContainer')">&nbsp;|
</span>
</td>
</tr>


<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權狀審核</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">權狀字號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">整筆面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">權利分子/分母</a></th>
		</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,true,false,false,false,false};
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
			if (form1.q_signNo1.value=="") {
				getSelectedValue(form1.q_signNo1,"E000000");
				changeSignNo1('q_signNo1','q_signNo2','q_signNo3','E000000');
			}
			if (form1.q_proofVerify.value=="") form1.q_proofVerify.value="N";
			break;
	}
	return true;
}
</script>
</body>
</html>

