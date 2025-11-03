<!--
*<br>程式目的：移動單報表檔 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP002R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String q_proofDoc = Common.checkGet(request.getParameter("q_proofDoc"));
	//if(!"".equals(q_proofDoc)){
	//	obj.setQ_proofDoc(q_proofDoc);
	//}
	//else{
	//	obj.setQ_proofDoc(new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D4"));
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
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">

function checkField(){
	var alertStr="";
		alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
		alertStr += checkDate(form1.q_writeDateS,"填單日期－起");
		alertStr += checkDate(form1.q_writeDateE,"填單日期－訖");
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
<body topmargin="10" onLoad="init();closebar();">
<form id="form1" name="form1" method="post" action="untrp002p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">
        	財產移動單<font color="red">(A4 橫式)</font>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
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
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="10" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onchange="getFrontZero(this.name,7);">		
		</td>
	</tr>
	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>聯數類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" name="q_kind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PFK' order by codeID ", new unt.ch.UNTCH_Tools()._getDefaultValue(user.getOrganID(), "proofKindPT"))%>
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
			<input type="hidden" name="userID" value="<%=user.getUserID()%>">
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
