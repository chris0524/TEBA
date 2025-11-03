<!--
*<br>程式目的：使用者操作記錄
*<br>程式代號：
*<br>撰寫日期：104/12/29
*<br>程式作者：Claus
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.rp.SYSRP002R">
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript"  src="../../js/function.js"></script>
<script type="text/javascript"  src="../../js/tablesoft.js"></script>
<script type="text/javascript" >

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){

}

function init(){
	
}
</script>

</head>
<body topmargin="10" onLoad="init();">
<form id="form1" name="form1" method="post" action="sysrp002p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_self">
<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">

</td></tr></table>
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">使用者操作記錄 <font color="red"></font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">異動日期：</div></td>
		<td class="queryTDInput" colspan="4">
			起<%=util.View.getPopCalndar("field_Q","q_editDateS",obj.getQ_editDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_editDateE",obj.getQ_editDateE())%>
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
