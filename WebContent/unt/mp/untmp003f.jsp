<!--
程式目的：動產主檔資料維護－接收撥入動產資料
程式代號：untmp003f
程式日期：0941026
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP003F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("untmp003f".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.untmp003f();
}
%>

<%
String popEnterOrg = request.getParameter("enterOrg");
String popEnterOrgName = request.getParameter("enterOrgName");
String popOwnership = request.getParameter("ownership");
String popCaseNo = request.getParameter("addProofCaseNo");
String popEnterDate = request.getParameter("enterDate");
%>

<html>
<head>
<title>接收撥入動產資料輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">

function changeKeep1(){
	changeKeepUnit(form1.tempEnterOrg, form1.originalKeepBureau, form1.originalKeepUnit,'');
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	changeBureau(form1.tempEnterOrg, form1.originalUseBureau, form1.originalKeepBureau.value);
	changeKeepUnit(form1.tempEnterOrg, form1.originalUseBureau, form1.originalUseUnit, '');
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}

function changeKeep2(){
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	changeKeepUnit(form1.tempEnterOrg, form1.originalUseBureau, form1.originalUseUnit, form1.originalKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}

function clickUntmp003f(){
		var alertStr="";
		alertStr += checkEmpty(form1.oldEnterOrgName,"撥出機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"撥出機關電腦單號");
		alertStr += checkEmpty(form1.cause,"增加原因");
		alertStr += checkDate(form1.originalMoveDate,"原始移動日期");
		alertStr += checkEmpty(form1.originalMoveDate,"原始移動日期");
		alertStr += checkEmpty(form1.originalKeepUnit,"原始保管單位");
		alertStr += checkEmpty(form1.originalKeeper,"原始保管人");
		alertStr += checkEmpty(form1.originalUseUnit,"原始使用單位");
		alertStr += checkEmpty(form1.originalUser,"原始使用人");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");

	if(alertStr.length==0){ 
			document.all('state').value='untmp003f';
			form1.submit();
	}else{
		alert(alertStr); return false; 
	}
}

function init() {
	if (document.all('state').value=='updateSuccess') {
		alert("新增成功!");
		opener.form1.submit();	
		window.close();
	}else if (document.all('state').value=='updateError') {
		alert("查無資料!");
	}
}

function changeSelect(){
	if(form1.propertyKind.value == "01"){
		form1.fundType.disabled=false;
	}else{
		form1.fundType.value = "";
		form1.fundType.disabled=true;
	}
	
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3" onLoad="init();">
<form id="form1" name="form1" method="post">
<table width="100%" cellspacing="0" cellpadding="0">
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="newEnterOrg" value="<%=Common.get(popEnterOrg)%>">
<input type="hidden" name="addProofCaseNo" value="<%=Common.get(popCaseNo)%>">
<input type="hidden" name="enterDate" value="<%=Common.get(popEnterDate)%>">
<!--Form區============================================================-->
<tr><td class="bg" >
	<table class="table_form" width="100%" height="100%">
	
	<tr>
		<td colspan="4" class="tab_border2" height="25">撥出機關資料</td>		
	</tr>
		
	<tr>
  		<td class="td_form" ><font color="red">*</font>撥出機關：</td>
  		<td class="td_form_white" >
			<%=util.View.getPopOrgan("field","oldEnterOrg",obj.getOldEnterOrg(),obj.getOldEnterOrgName(),"Y")%> 
		</td>
  		<td class="td_form">權屬：</td>
  		<td class="td_form_white">
  			<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>">
  			<select class="field_RO" type="select" name="ownershipName" disabled="true">
      			<%=util.View.getOnwerOption(Common.get(popOwnership))%>
    		</select>
  		</td>
  	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>撥出機關電腦單號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		</td>
	</tr>

	<tr>
		<td colspan="4" class="tab_border2" height="25">撥入機關資料</td>		
	</tr>
	<tr>
		<td class="td_form">增加原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="cause">
				<option value='03' >撥入</option>
			</select>
		</td>
	</tr>
	<tr>
  		<td class="td_form" ><font color="red">*</font>財產性質：</td>
  		<td class="td_form_white">		
  			<select class="field" type="select" name="propertyKind" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getPropertyKind())%>
			</select>
		</td>
		<td class="td_form" >基金財產：</td>
  		<td class="td_form_white">		
			<select class="field" type="select" name="fundType" disabled="true">
				<script>changeEnterOrg_FundType(form1.newEnterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
  	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>原始移動日期：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","originalMoveDate",obj.getOriginalMoveDate())%>
		</td>
		<td class="td_form">原始存置地點：</td>
		<td class="td_form_white">
			<input name="originalPlace" type="text" class="field" value="<%=obj.getOriginalPlace()%>" size="25" maxlength="25">
		</td>
	</tr>
	<tr>
		<td class="td_form">保管使用：</td>
		<td class="td_form_white" colspan="3">
			<div style="display:none">	
			原始保管處別：																  
			<select class="field" type="select" name="originalKeepBureau" onChange="changeKeep1();">
				<%=util.View.getOption("select bureau, bureauname from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by enterOrg, bureau ", obj.getOriginalKeepBureau())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			</div>
			<font color="red">*</font>原始保管單位：
			<select class="field" type="select" name="originalKeepUnit" onChange="changeKeep2();">
	  			<script>                                                            
	  				changeKeepUnit(form1.tempEnterOrg, form1.originalKeepBureau, form1.originalKeepUnit,'<%=obj.getOriginalKeepUnit()%>');
	        	</script>	
			</select>		
			　&nbsp;&nbsp;&nbsp;<font color="red">*</font>原始保管人：
			<select class="field" type="select" name="originalKeeper" onChange="getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '<%=obj.getOriginalUser()%>');getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, form1.originalKeeper.value);">
	        	<script>getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '<%=obj.getOriginalKeeper()%>');</script>			
	        </select>
	    <br>
	    	<div style="display:none">	
	    	原始使用處別：
	    	<select class="field" type="select" name="originalUseBureau" onChange="changeKeepUnit(form1.tempEnterOrg, form1.originalUseBureau, form1.originalUseUnit,'');">
				<script>changeBureau(form1.tempEnterOrg, form1.originalUseBureau, '<%=obj.getOriginalUseBureau()%>');</script>
			</select>
			&nbsp;&nbsp;&nbsp;
			</div>
			<font color="red">*</font>原始使用單位：
			<select class="field" type="select" name="originalUseUnit" onChange="getKeeper(form1.tempEnterOrg, this, form1.originalUser, '');">
	        	<script>                   
	        		changeKeepUnit(form1.tempEnterOrg, form1.originalUseBureau, form1.originalUseUnit,'<%=obj.getOriginalUseUnit()%>');
	        	</script>
	        </select>
			　&nbsp;&nbsp;&nbsp;<font color="red">*</font>原始使用人：
			<select class="field" type="select" name="originalUser">
	          	<script>getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '<%=obj.getOriginalUser()%>');</script>					  
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
			<input class="toolbar_default" followPK="false" type="button" name="batchSetButSubmit" value="確　　定" onClick="clickUntmp003f();" >
			<input class="toolbar_default" followPK="false" type="button" name="batchSetButCannel" value="取　　消" onClick="window.close()">
		</td>
	</tr>
	</table>
</td></tr>
</table>	
</form>
</body>
</html>
