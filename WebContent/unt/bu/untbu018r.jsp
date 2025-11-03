<!--
*<br>程式目的：建物移交清冊查詢檔 
*<br>程式代號：untbu018r
*<br>撰寫日期：94/11/9
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU018R">
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
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
    var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	alertStr += checkDate(form1.q_writeDateS,"填單日期－起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期－訖");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;	
}
</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untbu018p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">建物移交清冊<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>

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
		<td class="queryTDLable">減損單編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">排序方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="orderby">
			　　<%=util.View.getTextOption("1;存置地點;2;財產編號;",obj.getOrderBy())%>
			</select>		
		</td>
	</tr>
	

	<tr>
		<td class="queryTDLable"><font color="red">*</font>聯數類別：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="1">第一聯
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="2">第二聯
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="3">第三聯
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="4" CHECKED>全部
		</td>
	</tr>

	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
