<!--
程式目的：地價稅課稅明細上傳資料年度-批次刪除資料
程式代號：untte004update1
程式日期：0960905
程式作者：blair
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="util.Common" %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.te.UNTTE004UPDATE">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	String lndYear=Common.checkGet(request.getParameter("lndYear"));
	//System.out.println("lndYear:"+lndYear);
	obj.setLndYear(lndYear);
	obj.delLndData();
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
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.lndYYY,"課稅年度");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function init(){
		
}

function clickUPload(){
	var alertStr="";
		alertStr += checkYYY(form1.lndYear,"課稅年度");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	
}

</script>
</head>

<body topmargin="0" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">


<table class="bg" width="80%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
    <tr>
        <td class="td_form" colspan="4" style="text-align:center">批次刪除地價稅課稅明細資料</td>
	</tr>
	<tr>
        <td class="td_form">資料年度：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="lndYear" size="5" maxlength="5" >YYY，例:096
		</td>		
	</tr>
	<tr>	
		 <td class="queryTDInput" colspan="2" style="text-align:center;">   
		 <input class="toolbar_default" followPK="false" type="submit" name="upload" value="確　　定" onClick="clickUPload();">
		 <input class="toolbar_default" followPK="false" type="button" name="cancel" value="取　　消" onClick="window.close();">
		 </td>
	</tr>
</td></tr>
</table>
</form>
</body>
</html>



