<!--
*<br>程式目的：行政院函知新增項目清冊
*<br>程式代號：syspk003r
*<br>撰寫日期：
*<br>程式作者：shan(修)
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.pk.SYSPK003R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
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

	function checkField(){
    	var alertStr="";
    	alertStr += checkEmpty(form1.q_highestLevelDateS,"行政院副本函日期(起)");
    	alertStr += checkEmpty(form1.q_highestLevelDateE,"行政院副本函日期(訖)");
		alertStr += checkDate(form1.q_highestLevelDateS,"行政院副本函日期(起)");
		alertStr += checkDate(form1.q_highestLevelDateE,"行政院副本函日期(訖)");
		if(alertStr.length!=0){ alert(alertStr); return false; }
			return true;

	}

</script>
</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="syspk003p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center"></font>行政院函知新增項目清冊<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>行政院副本函日期</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_highestLevelDateS",obj.getQ_highestLevelDateS())%>
			訖<%=util.View.getPopCalndar("field_Q","q_highestLevelDateE",obj.getQ_highestLevelDateE())%>
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
