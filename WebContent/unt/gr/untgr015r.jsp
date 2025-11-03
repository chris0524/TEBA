<!--
*<br>程式目的：財產檢查單  
*<br>程式代號：untgr015r
*<br>撰寫日期：95/05/16
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.gr.UNTGR015R">
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
	if(alertStr.length!=0){ alert(alertStr); return false; }
	form1.q_keeper.disabled = false;
}

function changeAll(){
	changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
	changeKeepUnit(form1.q_enterOrg, '', form1.q_keepUnit,'');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	document.getElementById("P1").checked=true;
	changeKind();
// 	alteredEnterOrg();
}

function changeQPropertyNo(){
	if(parse(form1.q_propertyNoS.value).length>0){
		if(form1.q_propertyNoS.value.substr(0,3)=='111'){
			getProperty('q_propertyNoS','q_propertyNoSName',form1.q_propertyNoS.value.substr(0,3));
		}else{
			getProperty('q_propertyNoS','q_propertyNoSName',form1.q_propertyNoS.value.substr(0,1));
		}
	}else{
		form1.q_propertyNoSName.value = "";
	}
	if(parse(form1.q_propertyNoE.value).length>0){
		if(form1.q_propertyNoE.value.substr(0,3)=='111'){
			getProperty('q_propertyNoE','q_propertyNoEName',form1.q_propertyNoE.value.substr(0,3));
		}else{
			getProperty('q_propertyNoE','q_propertyNoEName',form1.q_propertyNoE.value.substr(0,1));
		}
	}else{
		form1.q_propertyNoEName.value = "";
	}
}

function changeKind() {
	if(document.getElementById("P2").checked==true){
		form1.propertyNumber.value="6";
	}else if(document.getElementById("P1").checked==true){
		form1.propertyNumber.value="";
	}
}

function init() {	
	if ('<%=user.getRoleid()%>' == '1'){
		var mySelect = form1.q_keeper[0];
		mySelect.remove(0);
		form1.q_keeper.disabled = true;
		form1.btn_Keeper.disabled = true;
		
	}
}

</script>
</head>
<body topmargin="10" >
<form id="form1" name="form1" method="post" action="untgr015p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">財產/物品檢查單<font color="red">(A4橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input class="field_Q" type="hidden" name="q_verify" value="Y">
			<input class="field_Q" type="hidden" name="q_dataState" value="1">
			<input class="field_Q" type="hidden" name="propertyNumber" value="">
			<input class="field_P" type="hidden" name="userName" value="<%=user.getUserName()%>">
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
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_differenceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' and codeID in ('110','120','112')" ,"")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>查詢種類：</td>
		<td class="queryTDInput">
		    <input id="P1" class="field_Q" type="radio" name="q_propertyKind1" value="1" checked onClick="changeKind();">財產
		    <input id="P2" class="field_Q" type="radio" name="q_propertyKind1" value="2" onClick="changeKind();">物品
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產編號：</td>
	    <td class="queryTDInput">
	    	起<input class="field_Q" type="text" name="q_propertyNoS" size="10" maxlength="11"	value="<%=obj.getQ_propertyNoS()%>" onBlur="changeQPropertyNo();">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName',form1.propertyNumber.value)" value="..."	title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">] 

		<br>			
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="10" maxlength="11"	value="<%=obj.getQ_propertyNoE()%>" onBlur="changeQPropertyNo();">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName',form1.propertyNumber.value)" value="..."	title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">] 

		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan='3'>
				<select class="field" type="select" name="q_keepUnit" onchange="form1.useunit.value = this.value;queryforDeprUnit();">
					<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ","")%>
				</select>
				<input class="field" type="button" name="btn_keepunit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人：
				<%if("1".equals(user.getRoleid())){ %>
					<select class="field" type="select" name="q_keeper" onchange="form1.user.value = this.value;">
						<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.keeperno where 1=1 and enterorg = '" + user.getOrganID() + "'  and a.keeperno = '" + user.getKeeperno() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",user.getKeeperno())%>
					</select>
				<%}else{ %>
					<select class="field" type="select" name="q_keeper" onchange="form1.user.value = this.value;">
						<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_keeper())%>
					</select>
				<% }%>
				<input class="field" type="button" name="btn_Keeper" onclick="popUnitMan(form1.organID.value,'q_keeper','q_user')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">使用單位  ：</td>
		<td class="queryTDInput" colspan='3'>
			<select class="field" type="select" name="q_useUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ","")%>
			</select>
			<input class="field" type="button" name="btn_useunit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<select class="field" type="select" name="q_user">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_user())%>
			</select>
			<input class="field" type="button" name="btn_User" onclick="popUnitMan(form1.organID.value,'q_user')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">使用註記：</td>
		<td class="queryTDInput">
			<input type="text" class="field_Q" name="q_userNote" value="" size="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="" onchange="queryPlaceName('enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_place1" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="15" maxlength="20" value="">]
		</td>
	
	</tr>
	<tr>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place" size="30" maxlength="30" value="">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput">
			<input type="text" class="field_Q" name="q_propertyName1" value="" size="20">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>換頁方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_pageWay">
				<option value='1'>保管人</option>
				<option value='2'>區分別-財產大類</option>
				<option value='3'>保管單位+保管人</option>
				<option value='4'>保管單位+財產編號</option>
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
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
