<!--
*<br>程式目的：動產廢品處理清冊報表檔 
*<br>程式代號：untmp024r
*<br>撰寫日期：94/12/6
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP024R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
 //  obj.setQ_proofDoc(new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D5"));
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkDate(form1.q_dealDateS,"廢品處理日期-起");
	alertStr += checkDate(form1.q_dealDateE,"廢品處理日期-訖");
	alertStr += checkDate(form1.q_writeDateS,"填單日期-起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期-訖");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
    	form1.action="untmp024p.jsp";
		return true;
	}
}

</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untmp024p.jsp" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">		
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">動產廢品處理清冊 <font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName())%>
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
	  <td class="queryTDLable">處理日期：</td>
	  <td class="queryTDInput">
	  		起<%=util.View.getPopCalndar("field_Q","q_dealDateS",obj.getQ_dealDateS())%>&nbsp;~&nbsp;
	  		訖<%=util.View.getPopCalndar("field_Q","q_dealDateE",obj.getQ_dealDateE())%> 
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
		<td class="queryTDLable">處理方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reduceDeal">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", obj.getQ_reduceDeal())%>
			</select>
		</td>
	</tr>		
	<tr>
		<td class="queryTDLable">處理單編號：</td>
		<td class="queryTDInput">
	    	<input class="field_Q" type="text" name="q_proofYear" size="3" maxlength="3" value="<%=obj.getQ_proofYear()%>">年		
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
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
	</table>
</td></tr></table>	
</form>
</body>
</html>
