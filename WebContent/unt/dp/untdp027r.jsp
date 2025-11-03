<!--
程式目的：轉帳憑證黏存單(權利) 
程式代號：untdp027r
程式日期：111/10/12
程式作者：chris.lin
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP027R">
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript">
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_deprYM,"計算折舊年月");
	alertStr += checkYYYMM(form1.q_deprYM,"計算折舊年月");
	if(alertStr.length!=0){ 
		alert(alertStr);
		return false; 
	}
	beforeSubmit();	
    return true;
}

function check_reset() {
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
}
function checkID(){
	if(form1.isAdminManager.value=='Y'){
	
		document.getElementById('tr1').style.display="table-row";
	}	   
}
</script>

</head>
<body topmargin="10" onload="checkID();" >

<form id="form1"  name="form1" method="post" action="untdp027p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable" cellspacing="0" cellpadding="0" border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">轉帳憑證黏存單(權利)<font color="red">(A4 直式)</font></td>
	</tr>
	<tr id="tr1"  style="display:none;">
		<td class="queryTDLable"><font color="red">*</font>入帳機關 ：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		</td>
	</tr>
    <tr>
		<td class="queryTDLable" width="30%"><font color="red">*</font>折舊年月：</td>
		<td class="queryTDInput">
			<br>
			<input type="text" class="field_Q" name="q_deprYM"  size="5" maxlength="5">				
			<br>
			<font color="red">例如：103年 2月，則輸入10302</font>
		</td>
	</tr>
    <tr >
      <td class="queryTDLable"><font color="red">*</font>財產區分別 ：</td>
      <td class="queryTDInput">
      	<select class="field_QRO" type="select" name="q_differenceKind" disabled="disabled">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,"110")%>
        </select>      
      </td>
    </tr>
    <tr >
		<td class="queryTDLable"><font color="red"></font>換頁方式：</td>
		<td class="queryTDInput">
			<select class="field" type="select" name="q_changePage" >
				<%=util.View.getTextOption("1;園區別;",obj.getQ_changePage())%>
			</select>
		</td>
	</tr>
    <tr>
    <td class="queryTDLable">列印簽核欄位 ：</td>
    <td class="queryTDInput">
    <input type="checkbox" value="1" name="q_printSign"></td>
    </tr>
    
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
			<input type="hidden" name="q_verify" value="Y">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
