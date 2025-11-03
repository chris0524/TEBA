<!--
程式目的： 建物毀損報廢減值單
程式代號：untrf025r
程式日期：097/05/22
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU038R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
}
</script>

</head>
<body topmargin="10">
<form id="form1" name="form1" method="post" action="untbu038p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
<table class="queryTable"  border="1">
<!--=============================================================================================================-->
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<!--=============================================================================================================-->
	<tr>
        <td class="td_form" colspan="4" style="text-align:center"> 建物毀損報廢減值單</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>管理機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
            <option value='1' >市有</option>
            <option value='2' >國有</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">
			?
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
		<td class="queryTDLable">增減值單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>"> 字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);"> 號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>聯數類別：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="1">第一聯
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="2">第二聯
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="3">第三聯
			<input class="field_Q" type="radio" name="q_kind" size="7"  value="0" CHECKED>全部
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" />
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" />
		</td>
	</tr>
<!--=============================================================================================================-->
</table>
</td></tr>
</table>
</form>
</body>
</html>



