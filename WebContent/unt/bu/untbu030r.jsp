<!--
*<br>程式目的：建物明細清冊查詢檔 
*<br>程式代號：untbu030r
*<br>撰寫日期：94/12/14
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU030R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if("".equals(obj.getQ_ownership())){
	String defaultOWA = TCGHCommon.getOwnerShipDefault(user.getOrganID());
	obj.setQ_ownership(defaultOWA);
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("q_signNo1","S000000")
//);

function checkField(){
	var alertStr="";

	alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
	if(alertStr.length!=0){ alert(alertStr); return false; }
	
    //beforeSubmit();	
    //form1.submit();
}

function checkinput(){
	if(form1.q_dataType.value=="3"){
		form1.q_enterDateS.value="";
		form1.q_enterDateE.value="";
		form1.q_enterDateS.disabled = true;
		form1.btn_q_enterDateS.disabled = true;
		form1.q_enterDateE.disabled = true;
		form1.btn_q_enterDateE.disabled = true;
		form1.q_balanceDate.disabled = false;
		form1.btn_q_balanceDate.disabled = false;
	}else{
		form1.q_balanceDate.value="";
		form1.q_enterDateS.disabled = false;
		form1.btn_q_enterDateS.disabled = false;
		form1.q_enterDateE.disabled = false;
		form1.btn_q_enterDateE.disabled = false;
		form1.q_balanceDate.disabled = true;
		form1.btn_q_balanceDate.disabled = true;
	}
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_dataType.value='3';
	checkinput();
}

</script>

</head>
<body topmargin="10" >

<form id="form1" name="form1" method="post" action="untbu030p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">建物明細清冊<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=alteredEnterOrg();")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_P", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>
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
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
			<%=util.View.getYNOption(obj.getQ_taxCredit())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			
			<input type="hidden" name="q_balanceDate" value="<%=util.Datetime.getYYYMMDD()%>">
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="userID" value="<%=user.getUserID()%>">
			<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
			<input type="hidden" name="q_verify" value="Y">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
<script language="javascript">
checkinput();
</script>
</body>
</html>
