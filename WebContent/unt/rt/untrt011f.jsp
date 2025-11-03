<!--
程式目的：他項權利證明書審核作業
程式代號：untrt011f
程式日期：0941018
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT011F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rt.UNTRT011F)obj.queryOne();	
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
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
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
	if(confirm("您確定要審核列表上的全部權利證明書?")){
		//getSelectedValue(form1.q_proofVerify,"Y");
		//alert(form1.q_proofVerify.value);
		setAllReadonlyClear();
		document.all('state').value='approveAll';
		form1.editID.value = "<%=user.getUserID()%>";				
		form1.submit();
	}

}

function clickUnApproveAll(){
	if(confirm("您確定要取消審核列表上的全部權利證明書?")){
		//getSelectedValue(form1.q_proofVerify,"N");	
		//alert(form1.q_proofVerify.value);	
		setAllReadonlyClear();	
		document.all('state').value='unApproveAll';
		form1.editID.value = "<%=user.getUserID()%>";	
		form1.submit();
	}
}

function clickApproveOne(){
	if(confirm("您確定要審核此筆權利證明書?")){
		document.all('state').value='approveOne';	
		form1.editID.value = "<%=user.getUserID()%>";		
		form1.submit();
	}
}

function clickUnApproveOne(){
	if(confirm("您確定要取消審核此筆權利證明書?")){
		document.all('state').value='unApproveOne';
		form1.editID.value = "<%=user.getUserID()%>";		
		form1.submit();
	}
}


function init(){
	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListPrint,spanListHidden","H");	
	/**
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanClear").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	if (isObj(document.all.item("spanListPrint"))) document.all.item("spanListPrint").style.display="none";
	**/
	if (form1.state.value=="logout") {	
		alert("對不起! 遺失重要資訊, 請重新登入!");	
		window.top.location.href = "../../index.jsp";
	}	
	if (form1.state.value=="queryOneSuccess") {
		setFormItem("approveOne,unApproveOne","O");
	} else {
		setFormItem("approveOne,unApproveOne","R");
	}	
}
</script>
</head>

<body topmargin="0" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td class="queryTDInput">
			<%=util.View.getPopProperty("field_Q","q_propertyNo",obj.getQ_propertyNo(),obj.getQ_propertyName(),"8")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">證明書審核：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_proofVerify">
			<%=util.View.getYNOption(obj.getQ_proofVerify())%>
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
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">證明書審核：</td>
		<td class="td_form_white">
			[<input name="proofVerify" type="text" class="field_RO" value="<%=obj.getProofVerify()%>" size="10">] 　證明書字號：[<input class="field_RO" type="text" name="proofDoc1" size="20" maxlength="20" value="<%=obj.getProofDoc1()%>">]</td>
		</tr>
	<tr>
	  <td class="td_form">入帳機關：</td>
	  <td class="td_form_white">[<input class="field_RO" type="text" name="enterOrgName" size="20" value="<%=obj.getEnterOrgName()%>">] 　權屬：[<input class="field_RO" type="text" name="ownershipName" size="10" maxlength="10" value="<%=obj.getOwnershipName()%>">]</td>
	  </tr>
	<tr>
	  <td class="td_form">財產編號：</td>
	  <td class="td_form_white">[<input name="propertyNo" type="text" class="field_RO" value="<%=obj.getPropertyNo()%>" size="15">]　 財產名稱：[<input name="propertyName" type="text" class="field_RO" value="<%=obj.getPropertyName()%>" size="20">] 　分號：[<input name="serialNo" type="text" class="field_RO" value="<%=obj.getSerialNo()%>" size="7">] <br>
	    別名：[<input name="propertyName1" type="text" class="field_RO" value="<%=obj.getPropertyName1()%>" size="30">]</td>
	  </tr>
	
	<tr>
		<td class="td_form">權利範圍：</td>
		<td class="td_form_white">[<input class="field_RO" type="text" name="holdNume1" size="5" maxlength="5" value="<%=obj.getHoldNume1()%>">╱<input class="field_RO" type="text" name="holdDeno2" size="5" maxlength="5" value="<%=obj.getHoldDeno2()%>">] 　權利價值：[<input name="originalBV" type="text" class="field_RO" value="<%=obj.getOriginalBV()%>" size="20" maxlength="20">] </td>
		</tr>
	<tr>
	  <td class="td_form">存續期間：</td>
	  <td class="td_form_white">[<input class="field_RO" type="text" name="setPeriod" size="13" maxlength="14" value="<%=obj.getSetPeriod()%>">] 　地租：[<input class="field_RO" type="text" name="rent" size="20" maxlength="20" value="<%=obj.getRent()%>">] </td>
		</tr>
	<tr>
	  <td class="td_form">異動人員/日期：</td>
	  <td class="td_form_white">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	      /
          <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
		</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >
	<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="approveOne" value="審　核" onClick="clickApproveOne();"> | <input class="toolbar_default" type="button" followPK="false" name="unApproveOne" value="取消審核" onClick="clickUnApproveOne();"> | <input class="toolbar_default" type="button" followPK="false" name="approveAll" value="全部審核" onClick="clickApproveAll();"> |
	<input class="toolbar_default" type="button" followPK="false" name="unApproveAll" value="全部取消審核"  onClick="clickUnApproveAll();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="listHidden1" value="列表隱藏" onclick="listToHidden(this,'formContainer','listContainer')">&nbsp;|	
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">證明書審核</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">證明書字號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">權利價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,false,false,false,false};
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



