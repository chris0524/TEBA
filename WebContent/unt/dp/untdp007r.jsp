<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP007R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%


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
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function deprUnitOnChange(){
	queryDeprUnitDataforDeprUnit1('q_enterOrg','q_deprUnit','q_deprUnit1');
	queryDeprUnitDataforDeprAccounts('q_enterOrg','q_deprUnit','q_deprAccounts')
}

function init(){
	queryDeprUnitDataforDeprUnit1('q_enterOrg','q_deprUnit','q_deprUnit1',form1.q_deprUnit1.value);
	queryDeprUnitDataforDeprAccounts('q_enterOrg','q_deprUnit','q_deprAccounts',form1.q_deprAccounts.value)
}

function checkField(){
	var alertStr="";
	if ("1" == $('input:radio:checked[name="q_kind"]').val()) { //1.重新產製  2.列印上次產製的資料
		alertStr += checkEmpty(form1.q_accountsYM,"帳務日期");
		alertStr += checkEmpty(form1.q_budgetYMS,"預估年月起");
		alertStr += checkEmpty(form1.q_budgetYME,"預估年月訖");
		alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
		// alertStr += checkEmpty(form1.q_deprUnit,"部門別");
		// alertStr += checkEmpty(form1.q_deprAccounts,"會計科目");
		alertStr += checkYYYMM(form1.q_accountsYM,"帳務日期");
		alertStr += checkYYYMM(form1.q_budgetYMS,"預估年月起");
		alertStr += checkYYYMM(form1.q_budgetYMS,"預估年月訖");
		alertStr += checkYYYMMSE(form1.q_budgetYMS,form1.q_budgetYME,"預估年月");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function q_kindChange(q_kind){
	if (q_kind == "2") {
		document.getElementById("tr2").style.display="none";
		document.getElementById("tr3").style.display="none";
		document.getElementById("tr4").style.display="none";
		document.getElementById("tr5").style.display="none";
	} else {
		document.getElementById("tr2").style.display="block";
		document.getElementById("tr3").style.display="block";
		document.getElementById("tr4").style.display="block";
		document.getElementById("tr5").style.display="block";
	}
}
function checkID(){
	if(form1.isAdminManager.value=='Y'){
		document.getElementById('tr1').style.display="";
	}
}

</script>
</head>

<body topmargin="0" onLoad="checkID();whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" action="untdp007p.jsp" method="post" onSubmit="return checkField()" target="_blank">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">

	
</td></tr></table>

<!--Query區============================================================-->

<!--頁籤區============================================================-->

<!--Form區============================================================-->
<table width="100%"  cellspacing="0" cellpadding="0" align="center">
<tr><td class="bg">
	<div>
	<table class="table_form" width="100%" height="100%">
		<tr>
			<td colspan='2' align="center" bgcolor="#cccccc">
				<input class="field_Q" type="radio" name="q_kind" value="1" checked="checked" onclick="q_kindChange(this.value)"> 重新產製 
			</td>
			<td colspan='2' align="center" bgcolor="#cccccc">
				<input class="field_Q" type="radio" name="q_kind" value="2" onclick="q_kindChange(this.value)"> 列印上次產製的資料
			</td>
		</tr>
		<tr id="tr1" style="display:none">
			<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
			<td class="queryTDInput" colspan='3'>
				<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
				[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
				<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=changeAll2();')" value="..." title="機關輔助視窗">
				<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
				<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
				<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
				<input type="hidden" name="userID" value="<%=user.getUserID()%>">
				<input type="hidden" name="userName" value="<%=user.getUserName()%>">
				<input type="hidden" name="q_verify" value="Y">
				<input type="hidden" name="q_dataState" value="1">
				<input type="hidden" name="q_deprMethod" value="01">
				<input type="hidden" name="q_noDeprSet" value="N">
			</td>
		</tr>
		<tr id="tr2">
			<td class="queryTDLable"><font color="red">*</font>帳務日期：</td>
			<td class="queryTDInput">
				<input type="text" class="field_Q" name="q_accountsYM" size="5" maxlength="5">
			</td>
			<td class="queryTDLable"><font color="red">*</font>預估年月：</td>
			<td class="queryTDInput" colspan="3">
				<input type="text" class="field_Q" name="q_budgetYMS" size="5" maxlength="5">
				至
				<input type="text" class="field_Q" name="q_budgetYME" size="5" maxlength="5"><font color="red">例如：103年 2月，則輸入10302</font>
			</td>
		</tr>
		<tr id="tr3">
			<td class="queryTDLable">財產大類：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_propertyType">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PTT' and codeid in ('2','3','4','5','6') ",obj.getQ_propertyType())%>
				</select>
			</td>
			<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_differenceKind">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' and codeid in ('120','111','110') " ,obj.getQ_differenceKind())%>
				</select>
			</td>
		</tr>
		<tr id="tr4">
			<td class="queryTDLable">園區別：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_deprPark">
					<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where enterOrg='"+ Common.esapi(user.getOrganID()) +"'", "")%>
				</select>
				</td>
			<td class="queryTDLable">部門別：</td>
			<td class="queryTDInput">			
				<select class="field_Q" type="select" name="q_deprUnit" onchange="deprUnitOnChange();">
					<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where enterOrg='"+ Common.esapi(user.getOrganID()) +"'","")%>
				</select>
			</td>
		</tr>
		<tr id="tr5">
			<td class="queryTDLable">部門別單位：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_deprUnit1">
					<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where enterOrg='"+ Common.esapi(user.getOrganID()) +"'","")%>
				</select>
			</td>
			<td class="queryTDLable">會計科目：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_deprAccounts">
					<%=util.View.getOption("select  a.depraccountsno, a.depraccountsname from SYSCA_DEPRACCOUNTS a  where a.enterorg='"+ Common.esapi(user.getOrganID()) +"'","")%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="queryTDLable"><font color="red">*</font>報表類別：</td>
			<td class="queryTDInput">
				<input class="field_Q" type="radio" name="q_report" value="2" checked="checked"> 財產明細 &nbsp; &nbsp;
				<input class="field_Q" type="radio" name="q_report" value="1"> 財產大類
			</td>
		</tr>
		<tr>
			<td class="queryTDInput" colspan="6" style="text-align:center;">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
				<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			</td>
		</tr>				
	</table>
	</div>
</td></tr>


<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;display:none;">
	<jsp:include page="../../home/toolbar.jsp" />	
</td></tr>

<!--List區============================================================-->

</table>
</form>
</body>
</html>
