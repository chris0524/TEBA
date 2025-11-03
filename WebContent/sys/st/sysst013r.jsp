<!--
*<br>程式目的：非公用房地租/償金徵收情形表
*<br>程式代號：sysst013r
*<br>撰寫日期：95/08/02
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.st.SYSST013R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if ("exportAll".equals(obj.getState())) {
	obj.setFilestoreLocation(this.getServletContext().getRealPath("/sys/st/templates/sysst013r.xls"));
	obj.genExcelFile();
	response.sendRedirect("../../downloadFileSimple?fileName=" + obj.getExcelFileName());
}	

//util.View.getPopCalndar("filed_Q", "closingDate", obj.getClosingDate())
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

function checkField() {
	var alertStr = "";
	alertStr += checkYYY(form1.q_year,"年度");
	alertStr += checkEmpty(form1.q_year,"年度");
	if (alertStr.length!=0) { 
		alert(alertStr); return false; 
	} else {
		form1.state.value = "exportAll";
		form1.excelFileName.value = "";	
		return true;
	}
}

function exportPopup(){
    var url = "../../downloadFileSimple?fileName=" + form1.excelFileName.value;
    window.open(url);
    /**
    var moshe;
	moshe=window.open('','MyWindow','scrollbars=1, resizable=yes, status=no, toolbar=no,menubar=no,width=400,height=150');
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 資料輸出 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="0" marginwidth="0" marginheight="0">\n');
	moshe.document.write('<div align=center><br><h>資料輸出成功!</h><br><br></div>');    
	moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">市有財產用途別分類統計表</a><br><br>');	
	moshe.document.write('</body></html>');	
	form1.state.value="queryAllSuccess";	
	**/
}

function init() {
	if (form1.state.value=="exportSuccess") {	
		exportPopup();
	}
	
	//if (form1.closingDate.value=="") form1.closingDate.value = getToday();
	if (form1.enterOrg.value=="" || form1.enterOrgName.value=="") {
		form1.enterOrg.value = "<%=user.getOrganID()%>";
		form1.enterOrgName.value = "<%=user.getOrganName()%>";		
	}
	
	if (form1.q_year.value=="") form1.q_year.value=getToday().substring(0,3);
}
</script>
</head>
<body topmargin="10" onload="init();">

<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">非公用房地租/償金徵收情形統計表<font color="red">(A4直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable">管理機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName())%>			
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="ownership" value="1">
		    [<input class="field_QRO" type="text" name="ownershipName" value="市有" size="5">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
		    <input class="field_Q" type="hidden" name="verify" value="Y">
		    [<input class="field_QRO" type="text" name="verifyName" value="是" size="5">]
		</td>
	</tr>
	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>年度：</td>
		<td class="queryTDInput">
		    <input type="text" class="field_Q" name="q_year" value="<%=obj.getQ_year()%>" size="5" maxlength="3">
		</td>
	</tr>	
	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input type="hidden" name="state" value="<%=obj.getState()%>">
			<input type="hidden" name="excelFileName" value="<%=obj.getExcelFileName()%>">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >　
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
