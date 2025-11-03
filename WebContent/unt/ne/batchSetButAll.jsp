<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE003F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("batchSetButAll".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.batchSetButAll();
}

%>
<html>
<head>
<title>批次設定輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
function changeKeep(){
	getKeepUnit(form1.tempEnterOrg, form1.setUseUnit, form1.setKeepUnit.value);	
	getKeeper(form1.tempEnterOrg, form1.setUseUnit, form1.setUser, form1.setKeeper.value);
}

function clickBatchSetButAll(){
	var alertStr="";
	alertStr += checkDate(form1.setMoveDate,"移動日期");
	//alertStr += checkEmpty(form1.setMoveDate,"移動日期");
	alertStr += checkEmpty(form1.setKeepUnit,"保管單位");
	alertStr += checkEmpty(form1.setKeeper,"保管人");
	alertStr += checkEmpty(form1.setUseUnit,"使用單位");
	alertStr += checkEmpty(form1.setUser,"使用人");
	if(alertStr.length==0){ 
		if(confirm("您確定批次設定列表上全部的批號明細?")){
			document.all('state').value='batchSetButAll';
			form1.submit();
			//window.close()
		}
	}else{
		alert(alertStr); return false; 
	}
}

function init() {
	if (document.all('state').value=='updateSuccess') {
		alert("修改成功!");
		opener.queryOne(form1.enterOrg.value,form1.ownership.value,form1.propertyNo.value,"<%=obj.getSerialNo()%>",form1.lotNo.value);
		window.close();
	}
}

</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3" onLoad="init();">
<form id="form1" name="form1" method="post">
<table width="100%" cellspacing="0" cellpadding="0">
<tr style="display:none"><td>
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="enterOrg" value="<%=Common.checkGet(request.getParameter("enterOrg"))%>">
<input type="hidden" name="ownership" value="<%=Common.checkGet(request.getParameter("ownership"))%>">
<input type="hidden" name="propertyNo" value="<%=Common.checkGet(request.getParameter("propertyNo"))%>">
<input type="hidden" name="lotNo" value="<%=Common.checkGet(request.getParameter("lotNo"))%>">
<!--Query區============================================================-->
<input type="hidden" name="q_enterOrg" value="<%=Common.checkGet(request.getParameter("q_enterOrg"))%>">
<input type="hidden" name="q_ownership" value="<%=Common.checkGet(request.getParameter("q_ownership"))%>">
<input type="hidden" name="q_caseNoS" value="<%=Common.checkGet(request.getParameter("q_caseNoS"))%>">
<input type="hidden" name="q_caseNoE" value="<%=Common.checkGet(request.getParameter("q_caseNoE"))%>">
<input type="hidden" name="q_verify" value="<%=Common.checkGet(request.getParameter("q_verify"))%>">
<input type="hidden" name="q_closing" value="<%=Common.checkGet(request.getParameter("q_closing"))%>">
<input type="hidden" name="q_caseName" value="<%=Common.checkGet(request.getParameter("q_caseName"))%>">
<input type="hidden" name="q_dataState" value="<%=Common.checkGet(request.getParameter("q_dataState"))%>">
<input type="hidden" name="q_propertyKind" value="<%=Common.checkGet(request.getParameter("q_propertyKind"))%>">
<input type="hidden" name="q_cause" value="<%=Common.checkGet(request.getParameter("q_cause"))%>">
<input type="hidden" name="q_fundType" value="<%=Common.checkGet(request.getParameter("q_fundType"))%>">
<input type="hidden" name="q_valuable" value="<%=Common.checkGet(request.getParameter("q_valuable"))%>">
<input type="hidden" name="q_enterDateS" value="<%=Common.checkGet(request.getParameter("q_enterDateS"))%>">
<input type="hidden" name="q_enterDateE" value="<%=Common.checkGet(request.getParameter("q_enterDateE"))%>">
<input type="hidden" name="q_writeDateS" value="<%=Common.checkGet(request.getParameter("q_writeDateS"))%>">
<input type="hidden" name="q_writeDateE" value="<%=Common.checkGet(request.getParameter("q_writeDateE"))%>">
<input type="hidden" name="q_proofDoc" value="<%=Common.checkGet(request.getParameter("q_proofDoc"))%>">
<input type="hidden" name="q_proofNoS" value="<%=Common.checkGet(request.getParameter("q_proofNoS"))%>">
<input type="hidden" name="q_proofNoE" value="<%=Common.checkGet(request.getParameter("q_proofNoE"))%>">
<input type="hidden" name="q_propertyNoS" value="<%=Common.checkGet(request.getParameter("q_propertyNoS"))%>">
<input type="hidden" name="q_propertyNoE" value="<%=Common.checkGet(request.getParameter("q_propertyNoE"))%>">
<input type="hidden" name="q_serialNoS" value="<%=Common.checkGet(request.getParameter("q_serialNoS"))%>">
<input type="hidden" name="q_serialNoE" value="<%=Common.checkGet(request.getParameter("q_serialNoE"))%>">
<input type="hidden" name="q_lotNo" value="<%=Common.checkGet(request.getParameter("q_lotNo"))%>">
<input type="hidden" name="q_keepUnit" value="<%=Common.checkGet(request.getParameter("q_keepUnit"))%>">
<input type="hidden" name="q_keeper" value="<%=Common.checkGet(request.getParameter("q_keeper"))%>">
<input type="hidden" name="q_useUnit" value="<%=Common.checkGet(request.getParameter("q_useUnit"))%>">
<input type="hidden" name="q_userNo" value="<%=Common.checkGet(request.getParameter("q_userNo"))%>">
</td></tr>
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer">
	<table class="table_form" width="100%" height="50%">	
	<input class="field_RO" type="hidden" name="tempEnterOrg" size="10" maxlength="10" value="<%=user.getOrganID()%>">
	<tr>
		<td class="td_form">移動日期：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","setMoveDate",obj.getSetMoveDate())%>
		</td>
		<td class="td_form">存置地點：</td>
		<td class="td_form_white">
			<input name="setPlace" type="text" class="field" value="<%=obj.getSetPlace()%>" size="25" maxlength="25">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>保管單位：</td>
		<td class="td_form_white">
		<select class="field" type="select" name="setKeepUnit" onChange="getKeeper(form1.tempEnterOrg, this, form1.setKeeper, '');changeKeep();">
  			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getSetKeepUnit())%>			
		</select>	
		<br>	
		保管人：
		<select class="field" type="select" name="setKeeper" onChange="changeKeep();">
        	<script>getKeeper(form1.tempEnterOrg, form1.setKeepUnit, form1.setKeeper, '<%=obj.getSetKeeper()%>');</script>			
        </select>
		</td>
		<td class="td_form"><font color="red">*</font>使用單位：</td>
		<td class="td_form_white">
		<select class="field" type="select" name="setUseUnit" onChange="getKeeper(form1.tempEnterOrg, this, form1.setUser, '');">
  			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getSetUseUnit()) %>					
        </select>
		<br>
		使用人：
		  <select class="field" type="select" name="setUser">
          	<script>getKeeper(form1.tempEnterOrg, form1.setUseUnit, form1.setUser, '<%=obj.getSetUser()%>');</script>					  
          </select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input type="hidden" name="state" value="<%=obj.getState()%>">
			<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
			<input type="hidden" name="userID" value="<%=user.getUserID()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
			<input class="toolbar_default" followPK="false" type="button" name="batchSetButSubmit" value="確　　定" onClick="clickBatchSetButAll();" >
			<input class="toolbar_default" followPK="false" type="button" name="batchSetButCannel" value="取　　消" onClick="window.close()">
		</td>
	</tr>
	</table>
	</div>
</td></tr>

</table>	
</form>
</body>
</html>
