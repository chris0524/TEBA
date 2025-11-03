<!--
*<br>程式目的：財物標準分類核定表
*<br>程式代號：syspk06r
*<br>撰寫日期：96/3/27
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.pk.SYSPK006R">
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
var readbg="#EEEEEE";
var editbg="#FFFFFF";
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_highestLevelDateB,"行政院回文日期起");
	alertStr += checkEmpty(form1.q_highestLevelDateE,"行政院回文日期訖");
	alertStr += checkDate(form1.q_highestLevelDateB,"行政院回文日期起");
	alertStr += checkDate(form1.q_highestLevelDateE,"行政院回文日期訖");
	alertStr += checkDate(form1.q_applyDateB,"申請日期起");
	alertStr += checkDate(form1.q_applyDateE,"申請日期訖");
	if(alertStr.length!=0){ alert(alertStr); return false; }
}
</script>
</head>
<body topmargin="10">

<form id="form1" name="form1" method="post" action="syspk006p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產標準分類核定表<font color="red">(A4直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>行政院回文日期起：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_highestLevelDateB",obj.getQ_highestLevelDateB())%>
			訖<%=util.View.getPopCalndar("field_Q","q_highestLevelDateE",obj.getQ_highestLevelDateE())%>
		</td>
	</tr>	
	<tr>
	  	<td class="queryTDLable">行政院是否同意：</td>
	  	<td class="queryTDInput">
			<select class="field" type="select" name="q_highestLevelIsAgree">
				<%=util.View.getYNOption(obj.getQ_highestLevelIsAgree())%>
			</select>		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">申請日期起：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_applyDateB",obj.getQ_applyDateB())%>
			訖<%=util.View.getPopCalndar("field_Q","q_applyDateE",obj.getQ_applyDateE())%>
		</td>
	</tr>	
	<tr>
	  	<td class="queryTDLable">市府是否同意：</td>
	  	<td class="queryTDInput">
			<select class="field" type="select" name="q_cityIsAgree">
				<%=util.View.getYNOption(obj.getQ_cityIsAgree())%>
			</select>		
		</td>
	</tr>
	<tr>
	  	<td class="queryTDLable">是否結案：</td>
	  	<td class="queryTDInput">
			<select class="field" type="select" name="q_isClose">
				<%=util.View.getYNOption(obj.getQ_isClose())%>
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
