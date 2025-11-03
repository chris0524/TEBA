<!--
程式目的：物品減少作業－現有資料明細新增明細資料
程式代號：untne015f
程式日期：0941121
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE015F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String caseNo = request.getParameter("caseNo");
String enterOrg = request.getParameter("enterOrg");
String ownership = request.getParameter("ownership");
String differenceKind = request.getParameter("differenceKind");

obj.setQ_caseNo(caseNo);
obj.setQ_enterOrg(enterOrg);
obj.setQ_ownership(ownership);
obj.setQ_differenceKind(differenceKind);	

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE015F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if("insertSuccess".equals(obj.getState())){
	obj.setQ_cause("");
	obj.setQ_cause1("");
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<title>物品減少作業現有資料明細新增輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js?v=1.0"></script>
<script language="javascript" src="../../js/getUNTNENonexp.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

//當增加原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.q_cause.value=="499"){
		form1.q_cause1.readOnly = false;
		form1.q_cause1.style.backgroundColor=editbg;
		form1.q_newEnterOrg.value = "";
		form1.q_transferDate.value = "";
		form1.q_transferDate.style.backgroundColor=readbg;
		setFormItem("btn_q_newEnterOrg,q_transferDate,btn_q_transferDate", "R");
	}else if (form1.q_cause.value=="201" || form1.q_cause.value=="205"|| form1.q_cause.value=="206"){
		form1.q_transferDate.style.backgroundColor=editbg;
		form1.q_cause1.value="";
		form1.q_cause1.style.backgroundColor=readbg;
		form1.q_cause1.readOnly = true;
		setFormItem("q_newEnterOrg,btn_q_newEnterOrg,q_transferDate,btn_q_transferDate","O");
	}else{
		form1.q_cause1.value="";
		form1.q_cause1.style.backgroundColor=readbg;
		form1.q_cause1.readOnly = true;
		form1.q_newEnterOrg.value = "";
		form1.q_transferDate.value = "";
		form1.q_transferDate.style.backgroundColor=readbg;
		setFormItem("q_newEnterOrg,btn_q_newEnterOrg,q_transferDate,btn_q_transferDate","R");
	}
	
	if(form1.q_cause.value=="208" || form1.q_cause.value=="214"){
	    form1.q_cause2.value="已逾最低年限，不堪使用";
	}else{
	    form1.q_cause2.value="";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkInsertSelect();
		alertStr += checkEmpty(form1.q_cause,"減損原因");
		if (form1.q_cause.value=="99"){
			alertStr += checkEmpty(form1.q_cause1,"減損原因-其他說明");
		}
		
		if ((form1.q_cause.value=="01")||(form1.q_cause.value=="07")||(form1.q_cause.value=="08")){
			alertStr += checkEmpty(form1.q_newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.q_transferDate,"交接日期");
		}
		alertStr += checkDate(form1.q_transferDate,"交接日期");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function initC(){
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanClear").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
	
	document.all.item("spanListPrint").style.display="none";
	document.all.item("spanListHidden").style.display="none";
	
	setFormItem("queryAll","O");

	if(form1.state.value=="queryAllSuccess"){
		document.all.item("batchInsertBut").disabled = false;
	}else{
		document.all.item("batchInsertBut").disabled = true;
	}
	
	queryPlaceName('q_enterOrg','q_place1');
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
		opener.form1.submit();
		if (isObj(window.aha)) window.aha.close();
	}

	var dcc99 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
	var dcc98 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
}

function checkInsertSelect() {
	var sFlag = false;
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "sKeySet" && e.checked==true) sFlag = true;	    
	}
	if (sFlag) return "";
	else return "您尚未勾選任何資料，若無資料可供勾選，請重新查詢！\n";
}
function popSysca_Code(popID,popIDName,typeName,codekindid,condition){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	
	// popSysca_Code.jsp有解碼
	typeName = encodeURI(typeName);
	returnWindow=window.open("../../home/popSysca_Code.jsp?popID="+popID+"&popIDName="+popIDName+"&typeName="+typeName+"&codekindid="+codekindid+"&condition="+condition,"",prop);
}
function queryPlaceName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../ch/queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			
		});	
}

	function initForGountne015f() {
		if (form1.q_cause2.value === '') {
			form1.q_cause2.value = "逾最低年限，不堪使用，修復不具經濟價值";			
		}
	} 
	
	function getCauseName(popCause,popCauseName){
		//傳送JSON
		var comment={};	
		comment.codeid = document.all.item("q_cause").value;
		comment.codecond1_1 = "2";
		comment.codecond1_2 = "4";
		
		$.post('queryCause.jsp',
			comment,
			function(data){
				//將回傳資料寫入
				data=eval('('+data+')');

				if(typeof(data['codename']) == 'undefined'){
					$("input[name='q_cause']").val('');
				}else{
					$("input[name='q_causeName']").val(data['codename']);	
				}
				
			});
	}
 	
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');initC();showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" value="<%=obj.getQ_enterOrg()%>">
<input class="field_QRO" type="hidden" name="q_dataState" size="1" value="1">
<input class="field_QRO" type="hidden" name="q_ownership" size="1" value="<%=obj.getQ_ownership()%>">		
<input class="field_QRO" type="hidden" name="q_caseNo" size="10" value="<%=obj.getQ_caseNo()%>">
<input class="field_QRO" type="hidden" name="q_reduceDate" size="10" value="<%=obj.getQ_reduceDate()%>">			
<input class="field_QRO" type="hidden" name="q_verify" size="10" value="<%=obj.getQ_verify()%>">
<input class="field_QRO" type="hidden" name="q_valuable" value="N">
<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
<input class="field_QRO" type="hidden" name="caseNo" size="24" maxlength="24" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="hidden" name="reduceDate" size="10" maxlength="10" value="<%=obj.getReduceDate()%>"> 
<input class="field_QRO" type="hidden" name="verify" size="10" maxlength="10" value="<%=obj.getVerify()%>"> 
<!--批次設定區============================================================-->
<div id="gountne015f" style="position:absolute;z-index: 25;left:0;top :0;width:500px;height:150px;display:none">
	<iframe id="gountne015fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	<tr> 
		<td class="queryTDLable"><font color="red">*</font>減損原因：</td>
		<td class="queryTDInput">
			<!-- <%=util.View.getCause("field_Q","q_cause",obj.getCause(),obj.getCauseName(),"2,4","changecause();")%> -->
			<input class="field_Q" type="text" name="q_cause" size="3" maxlength="3" value="<%=obj.getCause()%>" onChange="getCauseName('q_cause','q_causeName');changecause();">
			<input class="field_Q" type="button" name="btn_q_cause" onclick="popSysca_Code('q_cause','q_causeName','增加原因','CAA','2,4');" value="..." title="輔助視窗 " onblur="changecause();">
			[<input class="field_QRO" type="text" name="q_causeName" size="20" maxlength="50" value="<%=obj.getCauseName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">其它說明：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_cause1" size="22" maxlength="20" value="<%=obj.getQ_cause1()%>" readOnly=true>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">接管機關：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="q_newEnterOrg" size="10" maxlength="10" value="<%=obj.getQ_newEnterOrg()%>">
			[<input class="field_QRO" type="text" name="q_newEnterOrgName" size="20" maxlength="50" value="<%=obj.getQ_newEnterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_newEnterOrg" onclick="popOrgan('q_newEnterOrg','q_newEnterOrgName','Y')" value="..." title="機關輔助視窗" disabled=true>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">交接日期：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_transferDate" size="7" maxlength="7" value="<%=obj.getQ_transferDate()%>" readOnly=true>
			<input class="field_Q" type="button" name="btn_q_transferDate" onclick="popCalndar('q_transferDate')" value="..." title="萬年曆輔助視窗" disabled=true>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >報損報廢原因：</td> 
		<td class="queryTDInput">	
			<input class="field_Q" type="text" name="q_cause2" size="45" maxlength="22" value="<%=obj.getQ_cause2()%>">
		</td>	
	</tr>	 
	<tr>
		<td class="queryTDLable" >繳存地點：</td> 
		<td class="queryTDInput">	
				<input class="field_Q" type="text" name="q_returnPlace" size="22" maxlength="25" value="<%=obj.getQ_returnPlace()%>">
		</td>	
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountne015f');initC();">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
		<td class="queryTDLable" width="14%">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable" >物品分號：</td> 
		<td class="queryTDInput">	
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
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput" width="35%">
        <%=util.View._getSelectHTML("field_Q", "q_propertyKind", obj.getQ_propertyKind(),"PKD") %>		
		</td>	
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
				<%=util.View._getSelectHTML("field_Q", "q_fundType", obj.getQ_fundType(),"FUD") %>
		</td>				
	</tr>
	<tr>
		<td class="queryTDLable">物品區分別：</td>
		<td class="queryTDInput">						
			<!-- 104.12.28 問題單1267 區分別恢復為唯讀，修正回減損單與減損明細的區分別需相同 -->
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
		</td>
		<td class="queryTDLable">物品別名：</td>
		<td class="queryTDInput"> 
			<input type="text" class="field_Q" name="q_propertyName1" value="<%=obj.getPropertyName1() %>" size="20">
		</td>
	</tr>
		<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<input type="hidden" name="tempEnterOrg" value="<%=obj.getQ_enterOrg()%>">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly",  obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getQ_keeper()) %>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly", obj.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", obj.getUserNo()) %>
	        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
	        &nbsp;&nbsp;&nbsp;
			使用註記：
			<input type="text" class="field" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1()%>" onchange="queryPlaceName('q_enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name()%>">]		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput" colspan="3">			
			<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="initForGountne015f();queryShow('gountne015f');">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td>
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">原分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">物品別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">未入帳</a></th>
		
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {true,true,true,true,true,false,false,false,false,false};
	//boolean displayArray[] = {false,false,false,true,true,true,true,true,true,true};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	
		int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[10];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";			
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[4]+"')\"";
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"',"+rowArray[2]+",'"+rowArray[3]+"','"+rowArray[4]+"','"+rowArray[6]+"')\"";
			sbHEML.append(" <tr class='listTR'>\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			
			String chkproofdesc = Common.checkGet(rowArray[12]);
			if (!"".equals(chkproofdesc)) {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_QRO' disabled name='dis_sKeySet' value=\"\" >\n");
			} else {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[6]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			}
			
			
			sbHEML.append(" <td class='listTD'>"+rowArray[3]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[8]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[9]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[10]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[11]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[12]+"</td></tr>\n ");
		}
	}
	out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
<script>

</script>
</form>
</body>
</html>
