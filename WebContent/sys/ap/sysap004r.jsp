<!--
*<br>程式目的：各機關學校財產管理人員清冊
*<br>程式代號：sysap004r
*<br>撰寫日期：96/01/08
*<br>程式作者：Jim Chou
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP004R">
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
		
}
</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="sysap004p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center"><font></font>各機關學校財產管理人員清冊<font color="red">(A4 直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" colspan="1"><font color="red">*</font>列印選擇：</td>
		<td class="queryTDInput" colspan="1">
			<select class="field_Q" type="select" name="q_type" value="<>">
				<option value='1'>一般財產管理人員</option><%=obj.getQ_type()%>
				<option value='2'>KOC財產管理人員</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			<input type="hidden" name="isstop" value="N">
	        <input type="hidden" name="organid" value="<%=user.getOrganID()%>">
	        <input type="hidden" name="groupid" value="<%=user.getGroupID()%>">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
