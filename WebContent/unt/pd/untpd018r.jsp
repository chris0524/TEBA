<!--
*<br>程式目的：非消耗品盤存表 
*<br>程式代號：untpd018r
*<br>撰寫日期：103/09/01
*<br>程式作者：Jerry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD018R">
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
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.q_enterOrg.value == "" ) {
		alertStr += "入帳機關欄位不允許空白!\n";
	}	
	if(form1.q_differencekind.value == "" ) {
		alertStr += "財產區分別欄位不允許空白!\n";
	}
	if(form1.closingDate.value == "" ) {
		alertStr += "資料截止日期欄位不允許空白!\n";
	}

	
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} 
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
	var dcc99 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, null, null, false, false);
}

</script>
</head>
<body topmargin="10" onload="checkID();">
<form id="form1" name="form1" method="post" action="untpd018p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable" cellspacing="0" cellpadding="0" border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">非消耗品盤存表<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td id="td1" class="queryTDLable" style="display:none"><font color="red">*</font>入帳機關：</td>
		<td id="td2" class="queryTDInput" style="display:none">
			<%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input class="field_Q" type="hidden" name="q_verify" value="Y">
			<input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
		<td class="queryTDInput">
			 <select class="field_Q" type="select" name="q_differencekind" >
         		 <%=util.View.getTextOption("110;公務用-公務類;120;公務用-作業基金;",obj.getQ_differencekind())%>
        	 </select> 
		</td>
	</tr>
	<tr>
		<td nowrap class="queryTDLable">財產編號：</td>
		<td nowrap colspan="3" class="queryTDInput">
			起<input class="field_Q" type="text" name="q_propertyNoS" size="10" maxlength="11" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','6')">
		 	<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%= obj.getQ_propertyNoSName() %>">]&nbsp;~&nbsp;
			<br>
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="10" maxlength="11" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','6')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%= obj.getQ_propertyNoEName() %>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" +user.getOrganID()+ "' order by unitno ", 
			                                                       "field", "form1", "q_keepUnit", "q_keepUnitQuickly",  obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">查詢狀態：</td>
			<td class="queryTDInput" >
				<select class="field_Q" type="select" name="q_status">
				　　<%=util.View.getTextOption("1;已盤;2;未盤;",obj.getQ_status())%>
				</select>
			</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>資料截止日期：</td>
		<td class="queryTDInput">
		<%=util.View.getPopCalndar("field","closingDate",obj.getClosingDate())%>
		&nbsp;&nbsp;　
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
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
	<!-- 截止日期： -->
	<input class="field_QRO" type="hidden" name="q_reportDate" size="7" maxlength="7" value="<%=util.Datetime.getYYYMMDD()%>" >
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
</td></tr></table>	
</form>
</body>
</html>
