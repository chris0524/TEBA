<!--
程式目的：非消品盤點資料維護
程式代號: untpd012f
程式日期：103/09/11
程式作者：Mike Kao
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD012F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
//預設權屬為國有
// obj.setQ_ownership("1");
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.pd.UNTPD012F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","1")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){

		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.closingDate,"資料截止日期");
		alertStr += checkEmpty(form1.buyDate,"購置日期");
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkEmpty(form1.propertyNo,"物品編號");
		alertStr += checkEmpty(form1.serialNo,"物品分號");
		alertStr += checkEmpty(form1.bookAmount,"帳列數量");
		alertStr += checkEmpty(form1.bookValue,"總價");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function changeSelect(){
	//盤點異常 才可輸入異常原因
	if(form1.checkResult.value == "2") {
		setFormItem("oddsCause","O");
	}else{
		setFormItem("oddsCause","R");
		form1.oddsCause.value="";
	}
	
	//物品性質為「03:事業用」時，須控制「基金物品」必須有資料
	if(form1.propertyKind.value == "03") document.all.fundType.disabled=false;
	else{
		document.all.fundType.disabled=true;
		form1.fundType.value="";
	}
}

function init(){
		setDisplayItem("spanInsert");
		var dcc99 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keepunitQuickly, form1.q_keepunit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
		var dcc98 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
		var dcc97 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keepUnitQuickly, form1.keepUnit, form1.useUnitQuickly, form1.useUnit, true, false);
		var dcc96 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keeperQuickly, form1.keeper, form1.userNoQuickly, form1.userNo, true, false);

}

function queryOne(enterorg,serialNo1){
	form1.enterOrg.value = enterorg;
	form1.serialNo1.value = serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}


function chkresult(){
	if( form1.bookAmount.value=='' || form1.actualAmount.value=='' ){
		form1.checkResult.value='';
		changeSelect()
	}else if(form1.bookAmount.value==form1.actualAmount.value){
		form1.checkResult.value='1';
		changeSelect()
	}else{
		form1.checkResult.value='2';
		changeSelect()
	}
}

function onupdata(){
	if(form1.state.value=="update" && form1.serialNo.value!=''){
		setFormItem("closingDate,ownership,btn_closingDate,bookAmount,bookUnit,bookValue,checkResult,propertyKind,fundType,propertyNo,btn_propertyNo,buyDate,btn_buyDate,propertyName1,scrappedNote,labelNote,moveNote,placeNote,oldPropertyNo,oldSerialNo,specification,nameplate,otherMaterial,otherPropertyUnit,otherLimitYear,keepUnit,keeper,useUnit,userNo,place","R");
		changeSelect();
	}else if(form1.state.value=="insert" || (form1.state.value=="update" && form1.serialNo.value=='')){
		setFormItem("closingDate,btn_closingDate,bookAmount,checkResult,fundType,btn_propertyNo,oldPropertyNo,oldSerialNo","R");	
		changeSelect();
	}
}
//-----報表連結區----------------------
//產生盤點資料
function untpd013f(){
	form1.state.value="queryAll";
	form1.q_enterOrg.value="<%=user.getOrganID()%>";
	form1.q_enterOrgName.value="<%=user.getOrganName()%>";
	
	var prop="";
	var windowHight=window.screen.height;
	var windowWidth=window.screen.width;
	var windowTop=(document.body.clientHeight-100)/2;
	var windowLeft=(document.body.clientWidth-800)/2+225;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width="+windowWidth+",";
	prop=prop+"height="+windowHight+",";
	prop=prop+"top="+windowTop+",";
	var url = "untpd013f.jsp?" + "q_enterOrg=" + "<%=user.getOrganID()%>";
	linkpd002f=window.open(url,'MyWindow',prop);
}
//產生明細表
function untpd014r(){
	if(form1.q_enterOrg.value == "" || form1.q_enterOrg.value == null ){
		var url="untpd014r.jsp?q_enterOrg=" + "<%=user.getOrganID()%>" ;
	}else{
		var url="untpd014r.jsp?q_enterOrg=" + form1.q_enterOrg.value;
	}
	untpd014f = window.open(url);
}
function untpd018r(){
	if(form1.q_enterOrg.value == "" || form1.q_enterOrg.value == null ){
		var url="untpd018r.jsp?q_enterOrg=" + "<%=user.getOrganID()%>" ;
	}else{
		var url="untpd018r.jsp?q_enterOrg=" + form1.q_enterOrg.value;
	}
	untpd018f = window.open(url);
}

//下載盤點資料
function untpd015f(){
	var prop="";
	var windowHight=window.screen.height;
	var windowWidth=window.screen.width;
	var windowTop=(document.body.clientHeight-100)/2;
	var windowLeft=(document.body.clientWidth-800)/2+225;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width="+windowWidth+",";
	prop=prop+"height="+windowHight+",";
	prop=prop+"top="+windowTop+",";
	var url = "untpd015f.jsp?" + "enterOrg=" + "<%=user.getOrganID()%>";
	linkpd005f=window.open(url,'MyWindow',prop);
}

//上傳盤點資料
function untpd016f(){
	var prop="";
	var windowHight=window.screen.height;
	var windowWidth=window.screen.width;
	var windowTop=(document.body.clientHeight-100)/2;
	var windowLeft=(document.body.clientWidth-800)/2+225;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width="+windowWidth+",";
	prop=prop+"height="+windowHight+",";
	prop=prop+"top="+windowTop+",";
	var url = "untpd016f.jsp?" + "enterOrg=" + "<%=user.getOrganID()%>";
	linkpd005f=window.open(url,'MyWindow',prop);
}

//更新盤點日期
function untpd022f(){
	var prop="";
	var windowHight=window.screen.height;
	var windowWidth=window.screen.width;
	var windowTop=(document.body.clientHeight-100)/2;
	var windowLeft=(document.body.clientWidth-800)/2+225;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width="+windowWidth+",";
	prop=prop+"height="+windowHight+",";
	prop=prop+"top="+windowTop+",";
	var url = "untpd022f.jsp?" + "enterOrg=" + "<%=user.getOrganID()%>";
	linkpd011f=window.open(url,'MyWindow',prop);
}

function checkboxValue(name){
	if(document.getElementsByName(name)[0].checked == true){
		document.getElementsByName(name)[0].value="Y";
	}else{
		document.getElementsByName(name)[0].value="N";
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:900px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTPD012Q",obj);%>
	<jsp:include page="untpd012q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="16%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
		<%=util.View.getPopOrgan("field_RO","enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
		<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
		&nbsp;&nbsp;<font color="red">*</font>資料截止日期：　　
		<%=util.View.getPopCalndar("field","closingDate",obj.getClosingDate())%>
		&nbsp;&nbsp;　
	    <font color="red">*</font>權屬：
		<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
		</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="16%">盤點序號：</td>
		<td class="td_form_white" colspan="3">	
		[<input class="field_PRO" type="text" name="serialNo1" size="10" maxlength="7" value="<%=obj.getSerialNo1()%>">]&nbsp;&nbsp;
	條碼：<input class="field" type="text" name="barCode" size="25" maxlength="24" value="<%=obj.getBarCode()%>">
	</tr>
	<tr>
		<td class="td_form" width="16%">盤點結果：</td>
		<td class="td_form_white" colspan="3">
		<font color="red">*</font>帳列數量：<input class="field" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>" onChange="chkresult();">&nbsp;&nbsp;
		盤點數量：<input class="field" type="text" name="actualAmount" size="7" maxlength="7" value="<%=obj.getActualAmount()%>" onChange="chkresult();">
		<br>	
		單價：<input class="field_P" type="text" name="bookUnit" size="15" maxlength="13" value="<%=obj.getBookUnit()%>">&nbsp;　&nbsp;
		<font color="red">*</font>總價：
		<input class="field_P" type="text" name="bookValue" size="18" maxlength="15" value="<%=obj.getBookValue()%>">
		<br>
		盤點結果：
		<select class="field" type="select" name="checkResult" onChange="changeSelect();">
			<%=util.View.getTextOption("1;盤點正常;2;盤點異常",obj.getCheckResult())%>			
		</select>
		&nbsp;　&nbsp;&nbsp;
		異常原因：<input class="field" type="text" name="oddsCause" size="30" maxlength="16" value="<%=obj.getOddsCause()%>">
		<br>
		<% if("Y".equals(obj.getScrappedNote())){ %>
			<input type="checkbox" class="field" name="scrappedNote" value="" onClick ="checkboxValue('scrappedNote');" checked>
		<%} else{ %>
			<input type="checkbox" class="field" name="scrappedNote" value="" onClick ="checkboxValue('scrappedNote');" >
		<%} %>
		報廢註記		
		&nbsp;&nbsp;&nbsp;
		<% if("Y".equals(obj.getLabelNote())){ %>
			<input type="checkbox" class="field" name="labelNote" value="" onClick ="checkboxValue('labelNote');" checked>
		<%} else{ %>
			<input type="checkbox" class="field" name="labelNote" value="" onClick ="checkboxValue('labelNote');" >
		<%} %>
		補印標籤註記
		&nbsp;&nbsp;&nbsp;
		<% if("Y".equals(obj.getMoveNote())){ %>
			<input type="checkbox" class="field" name="moveNote" value="" onClick ="checkboxValue('moveNote');" checked>
		<%} else{ %>
			<input type="checkbox" class="field" name="moveNote" value="" onClick ="checkboxValue('moveNote');" >
		<%} %>
		移動註記
		<br>
		<% if("Y".equals(obj.getPlaceNote())){ %>
			<input type="checkbox" class="field" name="placeNote" value="" onClick ="checkboxValue('placeNote');" checked>
		<%} else{ %>
			<input type="checkbox" class="field" name="placeNote" value="" onClick ="checkboxValue('placeNote');" >
		<%} %>
		存置地點註記
		&nbsp;&nbsp;&nbsp;
		存置地點
			<input class="field" type="text" name="placeDetail1" size="10" maxlength="10" value="<%=obj.getPlaceDetail1()%>">
			<input class="field" type="button" name="btn_q_placeDetail1" onclick="popPlace('placeDetail1')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="placeDetail1Name" size="20" maxlength="20" value="<%=obj.getPlaceDetail1Name()%>">]
		&nbsp;&nbsp;&nbsp;	
		存置地點說明
			<input class="field" type="text" name="placeDetail" size="30" maxlength="30" value="<%=obj.getPlaceDetail()%>">
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產區分別：</td>
		<td class="td_form_white" colspan="3">
		<select class="field_P" type="select" name="differenceKind">
		　　<%=util.View.getTextOption("110;公務用-公務類;120;公務用-作業基金;",obj.getDifferenceKind())%>
		</select>
		&nbsp;　&nbsp;
		物品性質：
		<select class="field_P" type="select" name="propertyKind"  onChange="changeSelect();">
			<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
		</select>
		&nbsp;　&nbsp;
		基金物品：
		<select class="field_P" type="select" name="fundType" disabled="true">
		</select>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>物品編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','','NE')">
			<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','6&isLookup=Y')" value="..." title="物品編號輔助視窗">
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
		&nbsp;&nbsp;
		<font color="red">*</font>物品分號：
		<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>"><br>
	購置日期：<input class="field_P" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">
			<input class="field_P" type="button" name="btn_buyDate" onclick="popCalndar('buyDate');" value="..." title="萬年曆輔助視窗">
	取得日期：<input class="field_P" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">
			<input class="field_P" type="button" name="btn_buyDate" onclick="popCalndar('sourceDate');" value="..." title="萬年曆輔助視窗"> <br>		
	物品名稱：<input class="field_P" type="text" name="propertyName" size="20" maxlength="20" value="<%=obj.getPropertyName()%> ">	
	物品別名：<input class="field_P" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>"> <br>
	原物品編號：<input class="field_P" type="text" name="oldPropertyNo" size="20" maxlength="20" value="<%=obj.getOldPropertyNo()%>">
	原物品分號：<input class="field_P" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%> ">
		</td>
	</tr>
	<tr>
		<td class="td_form">
	型式：</td>
		<td class="td_form_white" colspan="3">	
		<input name="specification" type="text" class="field_P" value="<%=obj.getSpecification()%>" size="40" maxlength="40">
		&nbsp;&nbsp;&nbsp;
	廠牌：
		<input name="nameplate" type="text" class="field_P" value="<%=obj.getNameplate()%>" size="40" maxlength="40">　
		</td>
	</tr>
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3">
			主要材質：<input class="field_P" type="text" name="material" size="25" value="<%=obj.getMaterial()%>">&nbsp;&nbsp;&nbsp;　　
			單位：<input class="field_P" type="text" name="propertyUnit" size="25" maxlength="25" value="<%=obj.getPropertyUnit()%>">&nbsp;&nbsp;&nbsp;
			使用年限：<input class="field_P" type="text" name="limitYear" size="8" maxlength="3" value="<%=obj.getLimitYear()%>"><br>
			已使用年數：<input class="field_P" type="text" name="useyear" size="8" maxlength="3" value="<%=obj.getUseyear()%> " onChange="changeDate();">
		</td>
	</tr>
	<tr>
		<td class="td_form">移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID())+ "' order by unitno ", 
			                                                       "field_P", "form1", "keepUnit", "keepUnitQuickly", obj.getKeepUnit()) %>
			<input class="field_P" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'keepUnit','useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" +Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_P", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
	        <input class="field_P" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'keeper','userNo')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID())+ "' order by unitno ", 
			                                                       "field_P", "form1", "useUnit", "useUnitQuickly", obj.getUseUnit()) %>
			<input class="field_P" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" +Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_P", "form1", "userNo", "userNoQuickly", obj.getUserNo()) %>
	        <input class="field_P" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'userNo')" value="..." title="人員輔助視窗">
	       <br>
			使用註記
			<input type="text" class="field_P" name="usernote" value="<%=obj.getUsernote()%>" size="20">
	        <br>
			存置地點
			<input class="field_P" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>">
			<input class="field_P" type="button" name="btn_q_place" onclick="popPlace('place1')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="place1name" size="20" maxlength="20" value="<%=obj.getPlace1name()%>">]
			<br>		
			存置地點說明
			<input class="field_P" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white"><textarea class="field_P" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
	  <td class="td_form">異動人員/日期：</td>
	  <td class="td_form_white">
	  	[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
	  </td>
	</tr>	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<jsp:include page="../../home/toolbar.jsp" />
	<br />
 
	<span id="spanListPrint">
	|&nbsp;<input class="toolbar_default" type="button" followPK="false" name="uptpd0013f" value="產生盤點資料" onClick="untpd013f();">&nbsp;|
	 &nbsp;<input class="toolbar_default" type="button" followPK="false" name="link_uptpd0014f" value="盤點清冊" onClick="untpd014r();">&nbsp;|
	 <input class="toolbar_default" type="hidden" followPK="false" name="uptpd015f" value="下載盤點資料" onClick="untpd015f()">
	 <input class="toolbar_default" type="hidden" followPK="false" name="uptpd016f" value="上傳盤點資料" onClick="untpd016f();">
	 &nbsp;<input class="toolbar_default" type="button" followPK="false" name="link_uptpd0020f" value="盤存表" onClick="untpd018r();">&nbsp;|
	 </span>
		 
</td></tr>
<!--List區============================================================-->

<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>

<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">帳列數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">盤點數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">盤點結果</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,true,true,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
       	case "delete"://刪除之前多出現一道確認訊息
			break;
		case "queryAll"://做查詢時,將某些欄位填入預設值
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			break;				
	}
	return true;
}
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
//	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");
	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			//新增時,帳列數量固定為0
			form1.bookAmount.value="0";
			setFormItem("bookAmount,oddsCause","R");
			
			if(form1.propertyKind.value != "03"){//物品性質為「03:事業用」時，須控制「基金物品」必須有資料
				setFormItem("fundType","R");
			}	

			form1.state.value="insert";
			beforeSubmit();
			break;
		case "insertError":
			//新增時,帳列數量固定為0
			form1.bookAmount.value="0";
			setFormItem("bookAmount,oddsCause","R");
			
			if(form1.propertyKind.value != "03"){//物品性質為「03:事業用」時，須控制「基金物品」必須有資料
				setFormItem("fundType","R");
			}	
			break;
		case "update"://更新時要做的動作
			onupdata();
			break;
		case "updateError":
			break;
		case "clear":
			//init();
			break;
		}
	}
</script>
</body>
</html>



