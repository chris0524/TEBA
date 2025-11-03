<!--
*<br>程式目的：市有財產歷年價值統計表
*<br>程式代號：sysst002r
*<br>撰寫日期：95/07/04
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="obj" scope="request" class="sys.st.SYSST002R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if ("exportAll".equals(obj.getState())) {
	obj.setFilestoreLocation(this.getServletContext().getRealPath("/sys/st/templates/sysst002r.xls"));
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
	var alertStr = "";
	alertStr += checkEmpty(form1.d1, "基準月日");	
	if (form1.y1.value!="" && form1.y1.value.length<3) {
		for (var i=form1.y1.value.length; i<4; i++) form1.y1.value = "0" + form1.y1.value;
	}
	if (form1.y2.value!="" && form1.y2.value.length<3) {
		for (var i=form1.y2.value.length; i<4; i++) form1.y2.value = "0" + form1.y2.value;
	}
	alertStr += checkYYY(form1.y1, "年度起");
	alertStr += checkYYY(form1.y2, "年度訖");
	alertStr += checkEmpty(form1.y1, "年度起");
	alertStr += checkEmpty(form1.y2, "年度訖");
	alertStr += checkDate(form1.y1.value+form1.d1.value, "年度起+基準月日");	
	alertStr += checkDate(form1.y2.value+form1.d1.value, "年度訖+基準月日");
	if ((parseInt(form1.y2.value)-parseInt(form1.y1.value))>10) alertStr += "查詢期間已超過10年，請縮小範圍!\n";
	if (alertStr.length!=0) { alert(alertStr); return false; }	
	form1.state.value = "exportAll";
	form1.excelFileName.value = "";
}

function exportPopup(){
    var url = "../../downloadFileSimple?fileName=" + form1.excelFileName.value;
    window.open(url);
}

function init() {
	if (form1.state.value=="exportSuccess") {	
		exportPopup();
	}
	if (form1.d1.value=="") form1.d1.value = "0630";	
	if (form1.y1.value=="") form1.y1.value = "094";
	if (form1.y2.value=="") form1.y2.value = getToday().substring(0,3);
}
</script>
</head>
<body topmargin="10" onLoad="init();">

<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">市有財產歷年價值統計表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
	<td class="queryTDLable"><font color="red">*</font>基準月日：</td>
	<td class="queryTDInput"><input class="field_Q" type="text" name="d1" size="5", maxlength="4" value=""></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>起訖年度：</td>
		<td class="queryTDInput">
			  起<input name="y1" class="field_Q" type="text" size="4" maxlength="3">&nbsp;~&nbsp;
			  訖<input name="y2" class="field_Q" type="text" size="4" maxlength="3">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="ownership" value="1">
		    [<input class="field_QRO" type="text" name="ownershipName" value="市有" size="5">]		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
		    <input class="field_Q" type="hidden" name="verify" value="Y">
		    [<input class="field_QRO" type="text" name="verifyName" value="是" size="5">]		</td>
	</tr>
	<tr>
		<td class="queryTDLable">月結：</td>
		<td class="queryTDInput">
			<select type="select" class="field_Q" name="closingYM">
				<%=util.View.getYNOption(obj.getClosingYM())%>
			</select>		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input type="hidden" name="state" value="<%=obj.getState()%>">
			<input type="hidden" name="excelFileName" value="<%=obj.getExcelFileName()%>">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >　
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
