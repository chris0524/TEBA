<!--
*<br>程式目的：報廢財產清冊明細
*<br>程式代號：untrp011r
*<br>撰寫日期：110/03/10
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP011R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
	if (obj.getState().equals("export")) {
		obj.exportAll();		
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_reduceDateS,"減損日期起");
	alertStr += checkEmpty(form1.q_reduceDateE,"減損日期迄");
	alertStr += checkEmpty(form1.q_year,"處理年次");
	alertStr += checkEmpty(form1.q_doc,"處理年次");
	
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}
	form1.state.value = "export";
	return true;
}

function exportPopup(){
    var url = "../../downloadFileSimple?fileName=" + encodeURI(form1.excelFileName.value) + "&time=" + new Date().getTime();
	moshe=window.open('','MyWindow','scrollbars=1, resizable=yes, status=no, toolbar=no,menubar=no,width=400,height=150');
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 資料輸出 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="0" marginwidth="0" marginheight="0">\n');
	moshe.document.write('<div align=center><br><h>資料輸出成功!</h><br><br></div>');    
	moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">報廢財產清冊明細</a><br><br>');	
	moshe.document.write('</body></html>');	
	form1.state.value="queryAllSuccess";	
}

function init(){
	if (form1.state.value == "exportAll") {	
		exportPopup();
	} else if(form1.state.value == "exportfail"){
		alert("產製清冊錯誤，請洽詢相關工程人員");
	}	
}

</script>

</head>
<body topmargin="10" onLoad="init();" >

<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="excelFileName" value="<%=obj.getExcelFileName()%>">
<input type="hidden" name="state" value="<%=obj.getState()%>">			
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">報廢財產清冊明細 </td>
	</tr>
	<tr>
		<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName())%>
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
	  <td class="queryTDLable"><font color="red">*</font>減損日期：</td>
	  <td class="queryTDInput">
	  		起<%=util.View.getPopCalndar("field_Q","q_reduceDateS",obj.getQ_reduceDateS())%>&nbsp;~&nbsp;
	  		訖<%=util.View.getPopCalndar("field_Q","q_reduceDateE",obj.getQ_reduceDateE())%> 
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>處理年次：</td>
		<td class="queryTDInput">
	    	<input class="field_Q" type="text" name="q_year" size="3" maxlength="3" value="<%=obj.getQ_year()%>">年		
			<input class="field_Q" type="text" name="q_doc" size="2" maxlength="2" value="<%=obj.getQ_doc()%>">次
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
