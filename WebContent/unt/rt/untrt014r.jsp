<!--
*<br>程式目的：他項權利證明書備查簿報表檔 
*<br>程式代號：untrt014r
*<br>撰寫日期：94/11/23
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT014R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>

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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "03") document.all.q_fundType.disabled=false;
	else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function check_reset(){
	form1.q_propertyKind.value='';
	changeSelect();
}

</script>

</head>
<body topmargin="10" >
<form id="form1" name="form1" method="post" action="untrt014p.jsp" onReset="check_reset();" target="xyz">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
<table class="bg" width="102%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">他項權利證明書備查簿 <font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable">領狀情形：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_drawState">
				<option value='' selected>請選擇</option><option value='1'>有領狀資料</option><option value='2'>無領狀資料</option>
			</select>
		</td>
		<!--入帳機關：-->
			<input type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>">
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<option value=''>請選擇</option><option value='1' selected>市有</option><option value='2'>國有</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
		<td class="queryTDLable">增加單審核：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(obj.getQ_verify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput" colspan="3">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"8")%>&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"8")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput"> 
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<!--資料狀態：-->
			<input type="hidden" name="q_dataState" value="1">
		<!--權狀免繕狀：-->
			<input type="hidden" name="q_nonProof" value="N">
		<td class="queryTDLable">證明書審核：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_proofVerify">
			<%=util.View.getYNOption(obj.getQ_proofVerify())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType" disabled=true>
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">領狀人：</td>
		<td class="queryTDInput">
			<input name="q_drawName" type="text" class="field_Q" value="<%=obj.getQ_drawName()%>" size="20" maxlength="20">
		</td>
		<td class="queryTDLable">領狀日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_drawDateS",obj.getQ_drawDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_drawDateE",obj.getQ_drawDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">領狀原因：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_drawCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DCA' ", obj.getQ_drawCause())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
