<!--
*<br>程式目的：市有財產行政區域分佈統計表
*<br>程式代號：sysst004r
*<br>撰寫日期：95/06/27
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="obj" scope="request" class="sys.st.SYSST004R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if ("exportAll".equals(obj.getState())) {
	obj.setFilestoreLocation(this.getServletContext().getRealPath("/sys/st/templates/sysst004r.xls"));
	obj.genExcelFile();
	response.sendRedirect("../../downloadFileSimple?fileName=" + obj.getExcelFileName());
}	
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
	form1.state.value = "exportAll";
	form1.excelFileName.value = "";
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
}
</script>
</head>
<body topmargin="10" onload="init();">

<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">市有不動產行政區域分佈統計表<font color="red">(A4橫式)</font></td>
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
		<td class="queryTDLable">月結：</td>
		<td class="queryTDInput">
			<select type="select" class="field_Q" name="closingYM">
				<%=util.View.getYNOption(obj.getClosingYM())%>
			</select>

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
