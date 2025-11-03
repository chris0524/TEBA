<!--
*<br>程式目的：物品移動單查詢檔 
*<br>程式代號：untne011r
*<br>撰寫日期：95/02/22
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE011R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
String q_proofDoc = Common.checkGet(request.getParameter("q_proofDoc"));
if(!"".equals(q_proofDoc)){
	obj.setQ_proofDoc(q_proofDoc);
}

obj.setQ_ownership("1");
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
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("kind","4")
//);
function checkField(){
    var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkDate(form1.q_writeDateS,"填單日期－起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期－訖");
	alertStr += checkEmpty(form1.q_kind,"聯數類別");

	var columns = [form1.q_caseNoS,form1.q_caseNoE,form1.q_writeDateS,form1.q_writeDateE,form1.q_proofYear];
	var names = ["電腦單號－起","電腦單號－訖","填單日期－起","填單日期－訖","編號年"];
	alertStr += checkAtLeastOne(columns,names);
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;	
}

function init(){
	if(form1.q_proofYear.value == ""){
		form1.q_proofYear.value = <%= Datetime.getYYY() %>;	
	}	
}
</script>
</head>
<body topmargin="10" onload="init();">

<form id="form1" name="form1" method="post" action="untne011p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">物品移動單<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="userID" value="<%=user.getUserID()%>">
			<!-- <input type="hidden" name="q_ownership" value="1"> -->
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>權屬：</div></td>
 		<td class="queryTDInput" colspan="3">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">移動單編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_proofYear" size="3" maxlength="3" value="<%=obj.getQ_proofYear()%>">年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號	
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>聯數類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" name="q_kind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PFK' order by codeID ", new unt.ch.UNTCH_Tools()._getDefaultValue(user.getOrganID(), "proofKindNE"))%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">單據是否列印備註：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_note">
			　　<%=util.View.getYNOption(new unt.ch.UNTCH_Tools()._getDefaultValue(user.getOrganID(), "proofNotes"))%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<font color="red">核完章後請務必至「物品保管使用異動作業」進行入帳!!</font>
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
