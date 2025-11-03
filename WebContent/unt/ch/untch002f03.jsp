<!--
程式目的：移動單明細資料-現有資料新增
程式代號：untch002f03
程式日期：
程式作者：Kang Da
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH002F03">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String caseNo = Common.checkGet(request.getParameter("caseNo"));
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));

obj.setCaseNo(caseNo);
obj.setEnterOrg(enterOrg);
obj.setOwnership(ownership);

if ("queryAll".equals(obj.getState()) || "queryAllError".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}

if("insertSuccess".equals(obj.getState())){
	obj.setQ_newKeepUnit("");
	obj.setQ_newKeeper("");
	obj.setQ_newUseUnit("");
	obj.setQ_newUserNo("");
	obj.setQ_newUserNote("");
	obj.setQ_newPlace1("");
	obj.setQ_newPlace1Name("");
	obj.setQ_newPlace("");
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<title>保管使用異動作業現有資料明細新增輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js?v=1.0"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkInsertSelect();
		//alertStr += checkEmpty(form1.q_newKeepUnit,"新保管單位");
		//alertStr += checkEmpty(form1.q_newKeeper,"新保管人");
		//alertStr += checkEmpty(form1.q_newUseUnit,"新使用單位");
		//alertStr += checkEmpty(form1.q_newUserNo,"新使用人");
		if (form1.q_IsFix.checked == true){
			alertStr += checkEmpty(form1.q_newPlace1, "存置地點");
			//alertStr += checkEmpty(form1.q_newPlace1Name, "存置地點說明");
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo){
	
}

function init(){	
	if (document.all('state').value=='insertSuccess') {	
		opener.form1.state.value = "queryAll";
		opener.sonSubmit();
		window.close();
	}
	
	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListHidden", "H");
	
	changeSignNo1('q_laSignNo1','q_laSignNo2','q_laSignNo3','<%=obj.getQ_laSignNo2()%>');
	changeSignNo2('q_laSignNo1','q_laSignNo2','q_laSignNo3','<%=obj.getQ_laSignNo3()%>');
	changeSignNo1('q_buSignNo1','q_buSignNo2','q_buSignNo3','<%=obj.getQ_buSignNo2()%>');
	changeSignNo2('q_buSignNo1','q_buSignNo2','q_buSignNo3','<%=obj.getQ_buSignNo3()%>');
	q_IsFixonchange();
	
	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, false, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, false, false);
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
function q_IsFixonchange() {
	if (form1.q_IsFix.checked == true) {
		form1.q_newUserNote.disabled = false;
		form1.q_newPlace1.disabled = false;
		form1.btn_q_newPlace1.disabled = false;
		form1.q_newPlace1Name.disabled = false;
		form1.q_newPlace.disabled = false;
	} else {
		form1.q_newUserNote.disabled = true;
		form1.q_newPlace1.disabled = true;
		form1.btn_q_newPlace1.disabled = true;
		form1.q_newPlace1Name.disabled = true;
		form1.q_newPlace.disabled = true;
	}
	if (form1.q_IsFix_newKeepUnit.checked == true) {
		form1.q_newKeepUnit.disabled = false;
		form1.btn_q_newKeepUnit.disabled = false;
		form1.q_newKeepUnitQuickly.disabled = false;
	} else {
		form1.q_newKeepUnit.value = "";
		form1.q_newKeepUnit.disabled = true;
		form1.q_newKeepUnitQuickly.value = "";
		form1.q_newKeepUnitQuickly.disabled = true;
		form1.btn_q_newKeepUnit.disabled = true;
	}
	if (form1.q_IsFix_newKeeper.checked == true) {
		form1.q_newKeeper.disabled = false;
		form1.q_newKeeperQuickly.disabled = false;
		form1.btn_q_newKeeper.disabled = false;
	} else {
		form1.q_newKeeper.value = "";
		form1.q_newKeeper.disabled = true;
		form1.q_newKeeperQuickly.value = "";
		form1.q_newKeeperQuickly.disabled = true;
		form1.btn_q_newKeeper.disabled = true;
	}
	if (form1.q_IsFix_newUseUnit.checked == true) {
		form1.q_newUseUnit.disabled = false;
		form1.q_newUseUnitQuickly.disabled = false;	
		form1.btn_q_newUseUnit.disabled = false;
	} else {
		form1.q_newUseUnit.value = "";
		form1.q_newUseUnit.disabled = true;
		form1.q_newUseUnitQuickly.value = "";
		form1.q_newUseUnitQuickly.disabled = true;		
		form1.btn_q_newUseUnit.disabled = true;
	}
	if (form1.q_IsFix_newUserNo.checked == true) {
		form1.q_newUserNo.disabled = false;
		form1.q_newUserNoQuickly.disabled = false;
		form1.btn_q_newUserNo.disabled = false;
	} else {
		form1.q_newUserNo.value = "";
		form1.q_newUserNo.disabled = true;
		form1.q_newUserNoQuickly.value = "";
		form1.q_newUserNoQuickly.disabled = true;		
		form1.btn_q_newUserNo.disabled = true;
	}
}

function queryPlaceName1(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../ch/queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');
			if (data['placeName'] == "") {
				$("input[name='" + queryPlace + "Name']").val('');
				$("input[name='" + queryPlace + "']").val('');
				alert("不存在的存置地點");
			} else {
				$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			}

			
		});	
}

function changePropertyNo(propertyNo){}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位============================================================-->
<input class="field_QRO" type="hidden" name="q_enterOrg" value="<%=obj.getQ_enterOrg()%>">
<input class="field_QRO" type="hidden" name="q_dataState" value="1">
<input class="field_QRO" type="hidden" name="q_ownership" value="<%=obj.getQ_ownership()%>">			
<input class="field_QRO" type="hidden" name="q_verify" value="<%=obj.getQ_verify()%>">
<input class="field_QRO" type="hidden" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
 
<!--批次設定區============================================================-->
<div id="gountmp011f" style="position:absolute;z-index: 25;left:0;top :0;width:650px;height:150px;display:none">
	<iframe id="gountmp011fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	<tr> 
		<td class="queryTDLable">新移動資料：</td>
		<td class="queryTDInput">
			<input type="checkbox" class="field_Q" name="q_IsFix_newKeepUnit" value="Y" size="20" onclick="q_IsFixonchange()">保管單位：
			<input type="text" class="field_Q" size="5" name="q_newKeepUnitQuickly" value="" onchange="form1.q_newKeepUnit.value = this.value;form1.q_newUseUnitQuickly.value = this.value;form1.q_newUseUnit.value=this.value;q_IsFixonchange();">
			<select class="field_Q" type="select" name="q_newKeepUnit" onchange="form1.q_newKeepUnitQuickly.value=this.value;form1.q_newUseUnitQuickly.value=this.value;form1.q_newUseUnit.value = this.value;queryforDeprUnit();q_IsFixonchange();">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(obj.getEnterOrg()) + "' order by unitno ", obj.getQ_newKeepUnit())%>
			</select>
			<input class="field_Q" type="button" name="btn_q_newKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'q_newKeepUnit','q_newUseUnit','q_newKeepUnitQuickly')" value="..." title="單位輔助視窗">
		<br>
			<input type="checkbox" class="field_Q" name="q_IsFix_newKeeper" value="Y" size="20" onclick="q_IsFixonchange()">保管人：
			<input type="text" class="field_Q" size="6" name="q_newKeeperQuickly" value="" onchange="form1.q_newKeeper.value = this.value;form1.q_newUserNoQuickly.value = this.value;form1.q_newUserNo.value = this.value;q_IsFixonchange();">
			<select class="field_Q" type="select" name="q_newKeeper" onchange="form1.q_newKeeperQuickly.value = this.value;form1.q_newUserNoQuickly.value = this.value;form1.q_newUserNo.value = this.value;q_IsFixonchange();">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(obj.getEnterOrg()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_newKeeper())%>
			</select>
			<input class="field_Q" type="button" name="btn_q_newKeeper" onclick="popUnitMan(form1.enterOrg.value,'q_newKeeper','q_newUserNo','q_newKeeperQuickly')" value="..." title="人員輔助視窗">
		<br>
			<input type="checkbox" class="field_Q" name="q_IsFix_newUseUnit" value="Y" size="20" onclick="q_IsFixonchange()">使用單位：
			<input type="text" class="field_Q" size="5" name="q_newUseUnitQuickly" value="" onchange="form1.q_newUseUnit.value = this.value;form1.q_newKeepUnitQuickly.value=this.value;form1.q_newKeepUnit.value = this.value;q_IsFixonchange();">
			<select class="field_Q" type="select" name="q_newUseUnit" onchange="form1.q_newUseUnitQuickly.value = this.value;q_IsFixonchange();">
  				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", obj.getQ_newUseUnit())%>			
			</select>
			<input class="field_Q" type="button" name="btn_q_newUseUnit" onclick="popUnitNo(form1.enterOrg.value,'q_newKeepUnit','q_newUseUnitQuickly')" value="..." title="單位輔助視窗">
		<br>
			<input type="checkbox" class="field_Q" name="q_IsFix_newUserNo" value="Y" size="20" onclick="q_IsFixonchange()">使用人：
			<input type="text" class="field_Q" size="6" name="q_newUserNoQuickly" value="" onchange="form1.q_newUserNo.value = this.value;form1.q_newKeeperQuickly.value = this.value;form1.q_newKeeper.value = this.value;q_IsFixonchange();">
			<select class="field_Q" type="select" name="q_newUserNo" onchange="form1.q_newUserNoQuickly.value = this.value;q_IsFixonchange();">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_newUserNo())%>
			</select>
			<input class="field_Q" type="button" name="btn_q_newUserNo" onclick="popUnitMan(form1.enterOrg.value,'q_newKeeper','q_newUserNoQuickly')" value="..." title="人員輔助視窗">
		<br>
			存置地點是否修改 <input type="checkbox" class="field_Q" name="q_IsFix" value="Y" size="20" onclick="q_IsFixonchange()">
		<br>
			使用註記：
			<input type="text" class="field_Q" name="q_newUserNote" value="<%=obj.getQ_newUserNote()%>" size="20">
		<br>
			存置地點：
			<input class="field_Q" type="text" name="q_newPlace1" size="10" maxlength="10" value="<%=obj.getQ_newPlace1() %>" onchange="queryPlaceName1('enterOrg','q_newPlace1');">
			<input class="field_Q" type="button" name="btn_q_newPlace1" onclick="popPlace(form1.enterOrg.value,'q_newPlace1','q_newPlace1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_newPlace1Name" size="20" maxlength="20" value="<%=obj.getQ_newPlace1Name() %>">]
		<br>
			存置地點說明：
			<input type="text" class="field_Q" name="q_newPlace" value="<%=obj.getQ_newPlace()%>" size="30">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountmp011f');">
		</td>
	</tr>
	</table>
</div>
<!--Query區============================================================-->
<div id="queryContainer" style="width:1000px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
		<td class="queryTDLable" width="13%">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable" >財產分號：</td> 
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onchange="getFrontZero(this.name,7);">
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput" width="32%">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", obj.getQ_fundType(),"FUD", obj.getEnterOrg()) %>
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
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.enterOrg.value,'q_keepUnit')" value="..." title="單位輔助視窗">
		</td>
	    <td class="queryTDLable"><div align="right">保管人：</div></td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(obj.getEnterOrg()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getQ_keeper()) %>
			<input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.enterOrg.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
		
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly", obj.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.enterOrg.value,'q_useUnit')" value="..." title="單位輔助視窗">
		</td>
		<td class="queryTDLable"><div align="right">使用人：</div></td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(obj.getEnterOrg()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", obj.getQ_userNo()) %>
			<input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.enterOrg.value,'q_userNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>	
	<tr>
	    <td class="queryTDLable"><div align="right">使用註記：</div></td>
	    <td class="queryTDInput">
	    	<input type="text" class="field_Q" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20" maxlength="20">
        </td>
	    <td class="queryTDLable"><div align="right">存置地點：</div></td>
	    <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1() %>" onchange="queryPlaceName('enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place1" onclick="popPlace(form1.enterOrg.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name() %>">]
        </td>
	</tr>		
	<tr>
	    <td class="queryTDLable"><div align="right">存置地點說明：</div></td>
	    <td class="queryTDInput" colspan="3">
            <input type="text" class="field_Q" name="q_place" value="<%=obj.getQ_place() %>" size="40">
        </td>
	</tr>	
	<tr>
		<td class="queryTDLable" width="14%">財產區分別：</td>
		<td class="queryTDInput" colspan="3">		
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>
			&nbsp;&nbsp;&nbsp;&nbsp;
			財產別名
			<input class="field_Q" type="text" name="q_propertyName1" size="30" maxlength="50" value="<%=obj.getQ_propertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" width="14%">工程編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_engineeringNo" size="10" maxlength="11" value="<%=obj.getQ_engineeringNo()%>" onchange="queryEngineeringName('q_enterOrg','q_engineeringNo');">
			<input class="field_Q" type="button" name="btn_engineeringNo" onclick="popEngineering(form1.organID.value,'q_engineeringNo','q_engineeringNoName');" value="..." title="工程編號輔助視窗">
			[<input class="field_PRO" type="text" name="q_engineeringNoName" size="20" maxlength="50" value="<%=obj.getQ_engineeringNoName()%>">]			
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_laSignNo1" onChange="changeSignNo1('q_laSignNo1','q_laSignNo2','q_laSignNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getQ_laSignNo1())%>
			</select>　
			<select class="field_Q" type="select" name="q_laSignNo2" onChange="changeSignNo2('q_laSignNo1','q_laSignNo2','q_laSignNo3','');">
				
			</select>　		
			<select class="field_Q" type="select" name="q_laSignNo3">
				
			</select>　
		
			地號：		
			<input class="field_Q" type="text" name="q_laSignNo4" size="4" maxlength="4" value="<%=obj.getQ_laSignNo4() %>" onchange="getFrontZero(this.name,4);"> -
			<input class="field_Q" type="text" name="q_laSignNo5" size="4" maxlength="4" value="<%=obj.getQ_laSignNo5() %>" onchange="getFrontZero(this.name,4);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_buSignNo1" onChange="changeSignNo1('q_buSignNo1','q_buSignNo2','q_buSignNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getQ_buSignNo1())%>
			</select>　
			<select class="field_Q" type="select" name="q_buSignNo2" onChange="changeSignNo2('q_buSignNo1','q_buSignNo2','q_buSignNo3','');">
				
			</select>　		
			<select class="field_Q" type="select" name="q_buSignNo3">
				
			</select>　
		
			建號：		
			<input class="field_Q" type="text" name="q_buSignNo4" size="5" maxlength="5" value="<%=obj.getQ_buSignNo4() %>" onchange="getFrontZero(this.name,5);"> - 
			<input class="field_Q" type="text" name="q_buSignNo5" size="3" maxlength="3" value="<%=obj.getQ_buSignNo5() %>" onchange="getFrontZero(this.name,3);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="3">
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=obj.getQ_proofYear()%>" onchange="getFrontZero(this.name,3);">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onchange="getFrontZero(this.name,7);">號
		</td>	
	<tr>
	<tr>
		<td class="queryTDLable">移動單編號：</td>
		<td class="queryTDInput"  colspan="3">
			<input class="field_Q" type="text" name="q_moveProofYear" size="3" value="<%=obj.getQ_moveProofYear()%>" onchange="getFrontZero(this.name,3);">
			年
			<input class="field_Q" type="text" name="q_moveProofDoc" size="10" maxlength="20" value="<%=obj.getQ_moveProofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_moveProofNoS" size="10" maxlength="20" value="<%=obj.getQ_moveProofNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_moveProofNoE" size="10" maxlength="20" value="<%=obj.getQ_moveProofNoE()%>" onchange="getFrontZero(this.name,7);">號
		</td>	
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
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="originalKeepUnit" value="">			
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="queryShow('gountmp011f');">&nbsp;|
	</span>
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td>
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">原分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">未入帳</a></th>
		
	</thead>
	<tbody id="listTBODY">
	<%
	
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
				tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"',"+rowArray[2]+",'"+rowArray[3]+"','"+rowArray[4]+"')\"";
				sbHEML.append(" <tr class='listTR'>\n");			
				sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
				String chkproofdesc = Common.checkGet(rowArray[11]);
				if (!"".equals(chkproofdesc)) {
					sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_QRO' disabled name='dis_sKeySet' value=\"\" >\n");
				} else {
					sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
				}
				
				sbHEML.append(" <td class='listTD'><a href='#' " + tempJS + ">"+Common.checkGet(rowArray[3])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[4])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[5])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[6])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[7])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[8])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[9])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[10])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[11])+"</td></tr>\n ");
			}
		}
		out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>
