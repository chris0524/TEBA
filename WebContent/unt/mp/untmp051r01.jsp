<!--
*<br>程式目的：動產標籤報表檔 
*<br>程式代號：untmp051r
*<br>撰寫日期：94/11/26
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP051R01">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>
<%
	if("".equals(obj.getQ_ownership())){
		obj.setQ_ownership(uctls._getDefaultValue(user.getOrganID(), "ownership"));
	}
	
 //   obj.setQ_proofDoc(new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D1"));
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

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkDate(form1.q_enterDateS,"入帳日期-起");
	alertStr += checkDate(form1.q_enterDateE,"入帳日期-訖");
	alertStr += checkDate(form1.q_writeDateS,"填單日期-起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期-訖");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");
	
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
		form1.action="untmp051p01.jsp";
		return true;
	}
}

function changeAll(){
	changeKeepUnit(form1.q_enterOrg, form1.originalKeepUnit, '');
	changeKeepUnit(form1.q_enterOrg, form1.originalUseUnit, '');
	getKeeper(form1.q_enterOrg, form1.originalKeeper, '','N');
	getKeeper(form1.q_enterOrg, form1.originalUser, '','N');
}

	function init() {
		var q_workKind = '<%=Common.get(request.getParameter("q_workKind"))%>';
		if (q_workKind != '') {
			form1.q_workKind.value =	q_workKind;
		}
		if (form1.q_workKind.value == 'n') {
			$('#q_caseSerialNo').show();	
		} else {
			$('#q_caseSerialNo').hide();	
		}
		
		var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.originalKeepUnitQuickly, form1.originalKeepUnit, form1.originalUseUnitQuickly, form1.originalUseUnit, false, false);
		var dcc2 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.originalKeeperQuickly, form1.originalKeeper, form1.originalUserQuickly, form1.originalUser, false, false);
		
	}
	
	function changeWorkKind() {
		if (form1.q_workKind.value == 'n') {
			$('#q_caseSerialNo').show();
		} else {
			$('#q_caseSerialNo').hide();
		}
	}

</script>

</head>
<body topmargin="10" onload="init()">

<form id="form1" name="form1" method="post" onReset="form1.q_enterOrg.value='<%=user.getOrganID()%>';changeAll();" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">		
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<input type="hidden" name="state" value="<%=obj.getState()%>">
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">動產標籤(配合標籤機)</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>單據種類：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_workKind" onChange="changeWorkKind()">
				<option value='a' selected>增加單</option>
				<!-- <option value='j'>增減值單</option> -->
				<option value='n'>移動單</option>
				<option value='d'>現存</option>
			</select>
		</td>
	</tr>	
	<tr>
	<td class="queryTDLable" ><font color="red">*</font>入帳機關：</td>
        <td class="queryTDInput">
	  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption("".equals(obj.getQ_ownership())?"3":obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
   		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">			
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput" >
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>	
		<td class="queryTDLable">購置日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_buyDateS",obj.getQ_buyDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",obj.getQ_buyDateE())%> 
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput" colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%> 
		</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">填單編號：</td>
		<td class="queryTDInput"  colspan="3">		
		    <input class="field_Q" type="text" name="q_proofYear" size="15" maxlength="20" value="<%=obj.getQ_proofYear()%>">年
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
		
	</tr>
	<tr id="q_caseSerialNo">
		<td class="queryTDLable">移動單明細資料序號：</td>
		<td class="queryTDInput"  colspan="3">
			起<input class="field_Q" type="text" name="q_caseSerialNoS" size="10" maxlength="20" value="<%=obj.getQ_caseSerialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseSerialNoE" size="10" maxlength="20" value="<%=obj.getQ_caseSerialNoE()%>" onChange="addChar(this ,7);">號
		</td>
		
	</tr>
	<tr>
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_P", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>
		</td>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput">
			<input type="text" class="field_Q" name="q_propertyName1" value="<%=obj.getQ_propertyName1()%>" size="30" maxlength="30">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field_Q", "form1", "originalKeepUnit", "originalKeepUnitQuickly", obj.getOriginalKeepUnit()) %>
				<input class="field_Q" type="button" name="btn_originalKeepUnit" onclick="popUnitNo(form1.q_enterOrg.value,'originalKeepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人：
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID()  + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "originalKeeper", "originalKeeperQuickly", obj.getOriginalKeeper()) %>
		        <input class="field_Q" type="button" name="btn_originalKeeper" onclick="popUnitMan(form1.q_enterOrg.value,'originalKeeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
				                                                       "field_Q", "form1", "originalUseUnit", "originalUseUnitQuickly", obj.getOriginalUseUnit()) %>
				<input class="field_Q" type="button" name="btn_originalUseUnit" onclick="popUnitNo(form1.q_enterOrg.value,'originalUseUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人：
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID()  + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
				                                                       "field_Q", "form1", "originalUser", "originalUserQuickly", obj.getOriginalUser()) %>
		        <input class="field_Q" type="button" name="btn_originalUser" onclick="popUnitMan(form1.q_enterOrg.value,'originalUser')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
		        使用註記
				<input type="text" class="field" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20">
	</tr>
	<tr>
		<td class="td_form">存置地點：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1() %>" onchange="queryPlaceName('q_enterOrg', 'originalPlace1')">
			<input class="field" type="button" name="btn_originalPlace1" onclick="popPlace(form1.q_enterOrg.value,'originalPlace1','originalPlace1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name() %>">]	
	</tr>
	<tr>
		<td class="td_form">存置地點說明：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="originalPlace" size="30" maxlength="30" value="<%=obj.getOriginalPlace() %>">
	</tr>
	<tr>
		<td class="td_form">資料狀態：</td>
		<td colspan="3" class="td_form_white">
			<select class="field_Q" type="select" name="q_dataState">
				<option value=''>請選擇</option><option value='1' selected>現存</option><option value='2' >已減損</option>
			</select>
	</tr>
	<tr>
		<td class="queryTDLable">選擇標籤內容：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" name="q_printType"  >
				<option value='0'>保管單位</option>
				<option value='1'>存置地點</option>
				<option value='2'>保管人</option>
				<option value='3'>保管單位+存置地點</option>
				<option value='4' selected>保管單位+保管人</option>
				<option value='5'>存置地點+保管人</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">選擇排序條件1：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="orderby1">
			    <option value='0' selected >請選擇</option>
				<option value='1'>財產編號</option>
				<option value='2'>保管人</option>
				<option value='3'>保管單位</option>
				<option value='4'>存置地點</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">選擇排序條件2：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="orderby2">
			    <option value='0'>請選擇</option>
				<option value='1'>財產編號</option>
				<option value='2'>保管人</option>
				<option value='3'>保管單位</option>
				<option value='4'>存置地點</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">排序列印：</td>
	    <td class="queryTDInput" colspan="3">
	   		<select class="field_Q" type="select" name="q_printSerial">
	    		<option value='1'>正序列印</option>
				<option value='2'>反序列印</option>
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
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
