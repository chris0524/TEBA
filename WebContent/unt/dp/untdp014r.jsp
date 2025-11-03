<!--
*<br>
*<br>
*<br>
*<br>
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP014R">
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("q_signNo1","S000000")
//);
function checkField(){
    //return true;
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_deprYM,"計算折舊年月");
	alertStr += checkYYYMM(form1.q_deprYM,"計算折舊年月");
	alertStr += checkEmpty(form1.q_groupType,"統計方式");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	//beforeSubmit();	
    //form1.submit();
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
}
function checkID(){
	if(form1.isAdminManager.value=='Y'){
	
		document.getElementById('tr1').style.display="table-row";
	}	   
}
</script>

</head>
<body topmargin="10" onload="checkID();" >

<form id="form1"  name="form1" method="post" action="untdp014p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable" cellspacing="0" cellpadding="0" border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產折舊攤提月報總表(基金財產)<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr id="tr1" style="display:none">
		<td class="queryTDLable"><font color="red">*</font>入帳機關 ：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=alteredEnterOrg();")%>
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
    <tr style="display:none">
      <td class="queryTDLable"><font color="red">*</font>財產區分別 ：</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_differenceKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,"120")%>
        </select>      </td>
    </tr>
	<tr>
		<td class='queryTDLable'><font color="red">*</font>統計方式：</td>
		<td class='queryTDInput'>
			<select class='field_Q' name='q_groupType'>
				<%=View.getTextOption("A;部門別;B;部門別+部門別單位;C;部門別+會計科目", "") %>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>	
	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="q_verify" value="Y">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
