<!--
*<br>程式目的：公有房屋及附著物拆除改建(報廢)查核報告表查詢檔 
*<br>程式代號：untrf023r
*<br>撰寫日期：95/12/29
*<br>程式作者：Jim
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rf.UNTRF023R">
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

<script language="javascript">

function checkField(){

    var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkEmpty(form1.q_propertyNo1,"財產編號");
	alertStr += checkEmpty(form1.q_serialNo,"財產分號");
	if(alertStr.length!=0){ 
	     alert(alertStr);
	     return false;
     }
	return true;
}
</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untrf023p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center"><font></font>建築改良物報廢查核報告表<font color="red">(A3 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="q_verify" value="Y">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr> 
		<td class="queryTDLable"><font color="red">*</font>財產編號：</td>
		<td class="queryTDInput">
			<%=util.View.getPopProperty("field_Q","q_propertyNo1",obj.getQ_propertyNo1(),obj.getQ_propertyNoSName(),"111")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產分號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_serialNo" size="7" maxlength="7" value="<%=obj.getQ_serialNo()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">法令依據：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="lawBasis" size="50" maxlength="60" value="<%=obj.getLawBasis()%>">
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
