<!--
*<br>程式目的：非消耗品保管人清冊報表檔 
*<br>程式代號：untne030r
*<br>撰寫日期：103/09/18
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE030R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setQ_ownership("1");

String keeperName = "";
if("1".equals(user.getRoleid())){
	keeperName = View.getLookupField("select keepername from UNTMP_KEEPER where enterorg = " + Common.sqlChar(Common.esapi(user.getOrganID())) + " and keeperno = " + Common.sqlChar(Common.esapi(user.getKeeperno())));
}
%>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
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
	alertStr += checkEmpty(form1.q_ownership,"權屬");

	var err1=checkAllValue( [form1.q_propertyNoS,form1.q_propertyNoE] ,["財產編號－起","財產編號－訖"] );
	var err2=checkAtLeastOne( [form1.q_keepUnitQuickly, form1.q_keeperQuickly, form1.q_useunitQuickly, form1.q_userQuickly]
														,["保管單位","保管人","使用單位","使用人"] );
	
	if(err1 != "" && err2 !=""){
		alertStr += "財產編號起－訖、保管單位、保管人、使用單位、使用人 \n須任一組都有值!";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;
	//beforeSubmit();	
    //form1.submit();
}

function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "03"){ 
		document.all.q_fundType.disabled=false;
	}else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function alteredEnterOrg(){
	changeKeepUnit(form1.q_enterOrg, form1.q_keepUnit, '');
	changeKeepUnit(form1.q_enterOrg, form1.q_useunit, '');
	getKeeper(form1.q_enterOrg, form1.q_keeper, '','N');
	getKeeper(form1.q_enterOrg, form1.q_user, '','N');
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_propertyKind.value='';
	changeSelect();
}

function go(koko){
    var i =koko;
	if(i == "2" || i == "1"){
	form1.q_fundType.disabled=true;
	form1.q_fundType.value = "" ;
	}else{
	form1.q_fundType.disabled=false;
	}
}

function init() {
	var dcc99 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useunitQuickly, form1.q_useunit, true, false);
	var dcc98 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userQuickly, form1.q_user, true, false);
	if ('<%=user.getRoleid()%>' == '1'){	
		form1.btn_Keeper.disabled = true;
		readbg="#EEEEEE";
		setFormItem("q_keeperQuickly","R");
	}	
	form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
}
</script>

</head>
<body topmargin="10" onLoad="init();go('1');">
<form id="form1" name="form1" method="post" action="untne030p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="80%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<!--隱藏欄位===========================================================-->
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="q_dataState" value="1">
<input type="hidden" name="q_verify" value="Y">
<input type="hidden" name="userName" value="<%=user.getUserName()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">	
<input type="hidden" name="objPath" >
<input class="field_QRO" type="hidden" name="q_valuable" value="N">
<!--==================================================================-->
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">物品保管人清冊<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=alteredEnterOrg();')" value="..." title="機關輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td>
	</tr>
	<tr>	
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", obj.getQ_keepUnit()) %>
				<input class="field" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.q_enterOrg.value,'q_keepUnit')" value="..." title="單位輔助視窗" >
				<td class="queryTDLable">保管人：</td>
				<td class="queryTDInput" >
				<%if("1".equals(user.getRoleid())){ %>
				<input type="hidden" name="q_keeper" value="<%=user.getKeeperno()%>">
				<input class="field_RO" type="text" name="q_keeperQuickly" value="<%=user.getUserName()%>" size="10">							
				<input class="field_RO" type="button" name="btn_Keeper" onclick="popUnitMan(form1.q_enterOrg.value,'q_keeper')" value="..." title="人員輔助視窗">
				<%}else{%>
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID()  + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getQ_keeper()) %>
				<input class="field" type="button" name="btn_Keeper" onclick="popUnitMan(form1.q_enterOrg.value,'q_keeper')" value="..." title="人員輔助視窗">
				<%}%>				
		</td>
	</tr>	
	<tr>
	    <td class="queryTDLable">使用單位  ：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", 
				                                                       "field_Q", "form1", "q_useunit", "q_useunitQuickly", obj.getQ_useunit()) %>
			<input class="field" type="button" name="btn_useunit" onclick="popUnitNo(form1.organID.value,'q_useunit')" value="..." title="單位輔助視窗">
		<td class="queryTDLable">使用人：</td>
		<td class="queryTDInput" >
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID()  + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_user", "q_userQuickly", obj.getQ_user()) %>
			<input class="field" type="button" name="btn_User" onclick="popUnitMan(form1.q_enterOrg.value,'q_user')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">		 
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="18" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]
			<br>訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="18" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput" colspan="3">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>
		</td>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_propertyName1" size="30" maxlength="30" value="<%= obj.getQ_propertyName1() %>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_fundType">
				<%=util.View.getOption("select a.codeID, a.codeName from SYSCA_Code a , SYSCA_FUNDORGAN b where a.codeKindID='FUD' and a.codeid=b.fundno"  , obj.getQ_fundType())%>	
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput">
				<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1()%>" onchange="queryPlaceName('q_enterOrg','q_place1');">
				<input class="field_Q" type="button" name="btn_place1" onclick="popPlace(form1.q_enterOrg.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
				[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name()%>">]
		</td>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place" size="30" maxlength="30" value="<%= obj.getQ_place() %>">
		</td>		
	</tr>
	
	<tr>
		<td class="queryTDLable">列印方式：</td>
		<td class="queryTDInput" colspan="3">
			 <input class="field_Q" type="radio" name="q_print" size="7"  value="1" onclick="go('1');" CHECKED >全部
			&nbsp;&nbsp;
			 <input class="field_Q" type="radio" name="q_print" size="7"  value="2" onclick="go('2');" >非基金
			&nbsp;&nbsp;
			 <input class="field_Q" type="radio" name="q_print" size="7"  value="3" onclick="go('3');">基金
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
</td></tr>
</table>	
</form>
</body>
</html>
