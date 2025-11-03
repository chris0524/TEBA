<!--
*<br>程式目的：下載財產盤點資料
*<br>程式代號：untpd004f
*<br>撰寫日期：1030910
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%

if ("exportAll".equals(obj.getState())) {
	obj.exportAll();
}

%>

<%@page import="util.Datetime"%>
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
<script type="text/javascript">


function exportPopup(){
	if (form1.state.value=="exportAll") {
	    var url = "../../downloadFileSimple?fileName=" + form1.excelFileName.value;
		moshe=window.open('','MyWindow','scrollbars=1, resizable=yes, status=no, toolbar=no,menubar=no,width=400,height=150');
		moshe.document.write('<html>');
		moshe.document.write('<head>');
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
		moshe.document.write('<title>.:: 資料輸出 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="0" marginwidth="0" marginheight="0">\n');
		moshe.document.write('<div align=center><br><h>資料輸出成功!</h><br><br></div>');    
		moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">下載財產盤點資料</a><br><br>');	
		moshe.document.write('</body></html>');	
		form1.state.value="init";	
	}
}


function checkField(){
	var alertStr="";
//	if(form1.state.value=="queryAll"){
//		alertStr += checkQuery();
//
//	}	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
	
}

function clickExportAll() {
	var alertStr = "";
	
	if(form1.closingdate.value == "" ) {
		alertStr += "截止日期欄位不允許空白!\n";
	}		
	
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} 
	form1.state.value="exportAll";	
	form1.submit();
}

</script>
</head>
<body topmargin="10" onLoad="exportPopup();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1"  method="post" onSubmit="return checkField();">
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">		
<input type="hidden" name="excelFileName" value="<%=obj.getExcelFileName()%>">	


			
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="queryTDLable1" colspan="4" style="text-align:center">下傳財產盤點資料</td>
	</tr>
		<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput" colspan="2">
			<%=util.View.getPopOrgan("field_RO","enterOrg",user.getOrganID(),user.getOrganName())%>
		</td>
	</tr>
		
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="2">
			<select class="field" type="select" name="keepunitS">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ",obj.getKeepunitS())%>
			</select>	
			(起)&nbsp;~&nbsp;
			<select class="field" type="select" name="keepunitE">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ",obj.getKeepunitE())%>
			</select>	
			(訖)
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管人：</td>
		<td class="queryTDInput">
				<select class="field" type="select" name="keeper">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getKeeper())%>
				</select>
				<input class="field" type="button" name="btn_keeper" onclick="popUnitMan(form1.organID.value,'keeper')" value="..." title="人員輔助視窗">
			<br>
		</td>
	</tr>		
	<tr>
		<td class="queryTDLable"><font color="red">*</font>截止日期：</td>
		<td class="queryTDInput">
			<%=util.View.getPopCalndar("field_Q","closingdate","")%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="3" style="text-align:center;">
			<input class="toolbar_default" type="button" followPK="false" name="exportAll" value="輸出Excel檔"  onClick="clickExportAll();">
		</td>
	</tr>
    
	</table>
</td></tr></table>	
				
</form>
</body>
</html>
