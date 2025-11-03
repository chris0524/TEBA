<!--
*<br>程式目的：財產保管人清冊查詢檔 
*<br>程式代號：untmp010r
*<br>撰寫日期：103/08/12
*<br>程式作者：Anthony.Wang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rp.UNTRP010R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%
obj.setQ_ownership("1");

String keeperName = "";
if("1".equals(user.getRoleid())){
	keeperName = View.getLookupField("select keepername from UNTMP_KEEPER where enterorg = " + Common.sqlChar(Common.esapi(user.getOrganID())) + " and keeperno = " + Common.sqlChar(Common.esapi(user.getKeeperno())));
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
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("q_signNo1","S000000")
//);
function checkField(){
    //return true;
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	//alertStr += checkEmpty(form1.q_ownership,"權屬");

	var err1=checkAllValue( [form1.q_propertyNoS,form1.q_propertyNoE] ,["財產編號－起","財產編號－訖"] );
	var err2=checkAtLeastOne( [form1.keepunitQuickly, form1.keeperQuickly, form1.useunitQuickly, form1.userQuickly]
														,["保管單位","保管人","使用單位","使用人"] );
	
	if(err1 != "" && err2 !=""){
		alertStr += "財產編號起－訖、保管單位、保管人、使用單位、使用人 \n須任一組都有值!";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
	return true;
	//beforeSubmit();	
    //form1.submit();
}

function changeAll(){
	changeKeepUnit(form1.q_enterOrg, form1.keepunit, '');
	changeKeepUnit(form1.q_enterOrg, form1.useunit, '');
	getKeeper(form1.q_enterOrg, form1.keeper, '','N');
	getKeeper(form1.q_enterOrg, form1.user, '','N');
}

function init() {
	var dcc3 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.keepunitQuickly, form1.keepunit, form1.useunitQuickly, form1.useunit, true, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.keeperQuickly, form1.keeper, form1.userQuickly, form1.user, true, false);
	if ('<%=user.getRoleid()%>' == '1'){		
		form1.btn_Keeper.disabled = true;		
		readbg="#EEEEEE";
		setFormItem("keeperQuickly","R");
	}
	form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
}

</script>

</head>
<body topmargin="10" onLoad="init();">

<form id="form1" name="form1" method="post" action="untrp010p.jsp" onReset="form1.q_enterOrg.value='<%=user.getOrganID()%>';changeAll();" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="userName" value="<%=user.getUserName()%>">
<input type="hidden" name="q_dataState" value="1">
<input type="hidden" name="q_verify" value="Y">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">	
<input type="hidden" name="objPath" >
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
		<td class="td_form" colspan="4" style="text-align:center">財產保管人清冊<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" colspan='3'>
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=changeAll();')" value="..." title="機關輔助視窗">
			
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput" colspan='3'>
			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput" colspan='3'>
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產大類：</td>
		<td class="queryTDInput" colspan='3'>
			<select class="field" type="select" name="q_propertyType">
				<%=util.View.getTextOption("1;土地;2;建物;3;土地改良;4;動產;5;有價證券;6;權利增加;","")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	 
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">			
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%><br>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput" colspan="3">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
		<select class="field" type="select" name="q_propertyKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,"")%>
		</select>
		</td>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyName1" size="30" maxlength="30" value="">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan='3'>
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", 
			                                                       "field", "form1", "keepunit", "keepunitQuickly", "") %>
				<input class="field" type="button" name="btn_keepunit" onclick="popUnitNo(form1.organID.value,'keepunit')" value="..." title="單位輔助視窗" >
				&nbsp;&nbsp;&nbsp;保管人：
				<%if("1".equals(user.getRoleid())){ %>
				<input class="field_RO" type="text" name="keeperQuickly" value="<%=keeperName%>" size="10">
				<input type="hidden" name="keeper" value="<%=user.getKeeperno()%>">
				<input class="field_RO" type="button" name="btn_Keeper" onclick="popUnitMan(form1.organID.value,'keeper')" value="..." title="人員輔助視窗">
				<%}else{ %>
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                         "field", "form1", "keeper", "keeperQuickly", "") %>
				<input class="field" type="button" name="btn_Keeper" onclick="popUnitMan(form1.organID.value,'keeper')" value="..." title="人員輔助視窗">
				<%} %>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">使用單位  ：</td>
		<td class="queryTDInput" colspan='3'>
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", 
			                                                       "field", "form1", "useunit", "useunitQuickly", "") %>
			<input class="field" type="button" name="btn_useunit" onclick="popUnitNo(form1.organID.value,'useunit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
				                                                       "field", "form1", "user", "userQuickly", "") %>
			<input class="field" type="button" name="btn_User" onclick="popUnitMan(form1.organID.value,'user')" value="..." title="人員輔助視窗">

		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="" onchange="queryPlaceName('q_enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_place1" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="15" maxlength="20" value="">]
		</td>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place" size="30" maxlength="30" value="">
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">工程編號：</td>
		<td class="queryTDInput" colspan='3'>
			<input class="field_Q" type="text" name="engineering" size="20" maxlength="20" value="" onBlur="">
			<input class="field_P" type="button" name="btn_engineering" onclick="popEngineering(form1.organID.value,'engineering','engineeringName');" value="..." title="工程編號輔助視窗">
			[<input class="field_PRO" type="text" name="engineeringName" size="30" maxlength="50" value="">]
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="reportType" name="reportType">
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
</td></tr></table>	
</form>
</body>
</html>
