<!--
*<br>程式目的：採購財務物價調查表  
*<br>程式代號：untgr031r
*<br>撰寫日期：103/08/26
*<br>程式作者：Jerry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR031R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setQ_ownership("1");
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
<script language="javascript" src="../../unt/mp/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
	alertStr += checkEmpty(form1.q_enterDate,"帳務年月");
	alertStr += checkYYYMM(form1.q_enterDate,"帳務年月");
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function changeAll(){
	changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
	changeKeepUnit(form1.q_enterOrg, '', form1.q_keepUnit,'');
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
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
	
		document.getElementById('td1').style.display="";
		document.getElementById('td2').style.display="block";
	}	   
}

</script>
</head>
<body topmargin="10" onload="checkID();">
<form id="form1" name="form1" method="post" action="untgr031p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable" cellspacing="0" cellpadding="0" border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">採購財物物價調查表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td id="td1" class="queryTDLable" style="display:none"><font color="red">*</font>入帳機關：</td>
		<td id="td2" class="queryTDInput" style="display:none">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input class="field_Q" type="hidden" name="q_verify" value="Y">
			<input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
			<input type="hidden" name="q_verify" value="Y"/>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_P", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td> 
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>帳務年月：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_enterDate" size="5" maxlength="5" value="<%=obj.getQ_enterDate()%>">
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
		</td>
	</tr>
	</table>
	<!-- 截止日期： -->
	<input class="field_QRO" type="hidden" name="q_reportDate" size="7" maxlength="7" value="<%=util.Datetime.getYYYMMDD()%>" >
</td></tr></table>	
</form>
</body>
</html>
