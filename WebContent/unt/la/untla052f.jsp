<!--
程式目的：土地主檔資料維護－土地使用權
程式代號：untla052f
程式日期：0980702
程式作者：jerry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的

--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA052F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA052F)obj.queryOne();	
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


unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
uch.setPropertyNoType(obj.getPropertyNo());
uch_QUERY.setPropertyNoType(obj.getPropertyNo());
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._LA_ADD, 9);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._LA_ADD, 8);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._LA_ADD, 8);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._LA_ADD, 8);
}else{
	uch._setupViewType(uch._queryOrMaintenance);
	tabs = uch._createTabData(uch._LA_ADD, 8);
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
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkDate(form1.useDateS,"權利起始期間");
		alertStr += checkDate(form1.useDateE,"權利終止期間");
		alertStr += checkInt(form1.floor1,"核定地上樓層數");
		alertStr += checkInt(form1.floor2,"核定地下樓層數");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	if (surl=="../ch/untch001f02.jsp"){
		if('<%=obj.getIsAddProof()%>' != 'true'){
			form1.state.value="queryAll";
		}
	} else {
		form1.state.value="queryAll";
	}
	if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
		form1.mainPage1.value="";
		form1.currentPage.value=form1.mainPage.value;
	}
	
	form1.action=surl;	
	form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
	form1.queryone_ownership.value=form1.mainOwnerShip.value;
	form1.queryone_caseNo.value=form1.mainCaseNo.value;
	form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
	beforeSubmit();
	form1.submit();
}

function init() {
	setFormItem("queryAll","R");
	document.all.item("spanQueryAll").style.display="none";
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer2" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame2"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
</div>
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">使用次序：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]
		</td>
		<td class="td_form">權利起始期間：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","useDateS",obj.getUseDateS())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">權利終止期間：</td>
		<td class="td_form_white">
			<%=util.View.getPopCalndar("field","useDateE",obj.getUseDateE())%>
		</td>
		<td class="td_form">證明文號：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="proofDoc" size="50" maxlength="50" value="<%=obj.getProofDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">核定建物構造：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="stuff">
			<%=util.View.getOption("select codeid,codename from sysca_code where codekindid='STB'", obj.getStuff())%>
			</select>
		</td>
		<td class="td_form">核定地上樓層數：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="floor1" size="3" maxlength="3" value="<%=obj.getFloor1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">核定地下樓層數：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="floor2" size="3" maxlength="3" value="<%=obj.getFloor2()%>">
		</td>
		<td class="td_form">核定建物基層面積：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="baseArea" size="7" maxlength="7" value="<%=obj.getBaseArea()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">核定使用土地面積：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="useLandArea" size="7" maxlength="7" value="<%=obj.getUseLandArea()%>">
		</td>
		<td class="td_form">核定建物面積：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="buildingArea" size="7" maxlength="7" value="<%=obj.getBuildingArea()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">土地使用權利金：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="useLandPrice" size="14" maxlength="14" value="<%=obj.getUseLandPrice()%>">
		</td>
		<td class="td_form">核定用途：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="purpose">
			<%=util.View.getOption("select codeid,codename from sysca_code where codekindid='APA'", obj.getPurpose())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">原建物構造：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="oldStuff">
			<%=util.View.getOption("select codeid,codename from sysca_code where codekindid='STB'", obj.getOldStuff())%>
			</select>
		</td>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="24" rows="4"><%=obj.getNotes()%></textarea>
		</td>
	</tr>
	<tr style="display:none">
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
		<td class="td_form">&nbsp;</td>
		<td class="td_form_white">&nbsp;</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">	
	<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">	
	<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">	
	<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
	<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
	
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">	
	
	
	<input type="hidden" name="q_propertyNoS" value="<%=Common.get(request.getParameter("q_propertyNoS")) %>" >
	<input type="hidden" name="q_propertyNoE" value="<%=Common.get(request.getParameter("q_propertyNoE")) %>" >		
	<input type="hidden" name="q_serialNoS" value="<%=Common.get(request.getParameter("q_serialNoS")) %>" >
	<input type="hidden" name="q_serialNoE" value="<%=Common.get(request.getParameter("q_serialNoE")) %>" >
	<input type="hidden" name="q_dataState" value="<%=Common.get(request.getParameter("q_dataState")) %>" >
	<input type="hidden" name="q_differenceKind" value="<%=Common.get(request.getParameter("q_differenceKind")) %>" >
	<input type="hidden" name="q_verify" value="<%=Common.get(request.getParameter("q_verify")) %>" >
	<input type="hidden" name="q_ownership" value="<%=Common.get(request.getParameter("q_ownership")) %>" >
	<input type="hidden" name="q_differenceKind_detail" value="<%=Common.get(request.getParameter("q_differenceKind_detail")) %>" >
	<input type="hidden" name="q_propertyName1" value="<%=Common.get(request.getParameter("q_propertyName1")) %>" >
	<input type="hidden" name="q_originalUserNote" value="<%=Common.get(request.getParameter("q_originalUserNote")) %>" >
	<input type="hidden" name="q_keepUnit" value="<%=Common.get(request.getParameter("q_keepUnit")) %>" >
	<input type="hidden" name="q_keeper" value="<%=Common.get(request.getParameter("q_keeper")) %>" >
	<input type="hidden" name="q_useUnit" value="<%=Common.get(request.getParameter("q_useUnit")) %>" >
	<input type="hidden" name="q_userNo" value="<%=Common.get(request.getParameter("q_userNo")) %>" >
	<input type="hidden" name="q_place1" value="<%=Common.get(request.getParameter("q_place1")) %>" >
	<input type="hidden" name="q_place1Name" value="<%=Common.get(request.getParameter("q_place1Name")) %>" >
	<input type="hidden" name="q_place" value="<%=Common.get(request.getParameter("q_place")) %>" >
	<input type="hidden" name="q_engineeringNo" value="<%=Common.get(request.getParameter("q_engineeringNo")) %>" >
	<input type="hidden" name="q_engineeringNoName" value="<%=Common.get(request.getParameter("q_engineeringNoName")) %>" >
	<input type="hidden" name="q_originalBuildFeeCB" value="<%=Common.get(request.getParameter("q_originalBuildFeeCB")) %>" >
	<input type="hidden" name="q_originalDeprUnitCB" value="<%=Common.get(request.getParameter("q_originalDeprUnitCB")) %>" >
	<input type="hidden" name="q_signLaNo1" value="<%=Common.get(request.getParameter("q_signLaNo1")) %>" >
	<input type="hidden" name="q_signLaNo2" value="<%=Common.get(request.getParameter("q_signLaNo2")) %>" >
	<input type="hidden" name="q_signLaNo3" value="<%=Common.get(request.getParameter("q_signLaNo3")) %>" >
	<input type="hidden" name="q_propertyKind" value="<%=Common.get(request.getParameter("q_propertyKind")) %>" >
	<input type="hidden" name="q_fundType" value="<%=Common.get(request.getParameter("q_fundType")) %>" >
	<input type="hidden" name="q_valuable" value="<%=Common.get(request.getParameter("q_valuable")) %>" >
	<input type="hidden" name="q_taxCredit" value="<%=Common.get(request.getParameter("q_taxCredit")) %>" >
	<input type="hidden" name="q_writeDateS" value="<%=Common.get(request.getParameter("q_writeDateS")) %>" >
	<input type="hidden" name="q_writeDateE" value="<%=Common.get(request.getParameter("q_writeDateE")) %>" >
	<input type="hidden" name="q_caseName" value="<%=Common.get(request.getParameter("q_caseName")) %>" >
	<input type="hidden" name="q_proofYear" value="<%=Common.get(request.getParameter("q_proofYear")) %>" >
	<input type="hidden" name="q_proofDoc" value="<%=Common.get(request.getParameter("q_proofDoc")) %>" >
	<input type="hidden" name="q_proofNoS" value="<%=Common.get(request.getParameter("q_proofNoS")) %>" >
	<input type="hidden" name="q_proofNoE" value="<%=Common.get(request.getParameter("q_proofNoE")) %>" >
	<input type="hidden" name="q_enterDateS" value="<%=Common.get(request.getParameter("q_enterDateS")) %>" >
	<input type="hidden" name="q_enterDateE" value="<%=Common.get(request.getParameter("q_enterDateE")) %>" >
	<input type="hidden" name="q_sourceDateS" value="<%=Common.get(request.getParameter("q_sourceDateS")) %>" >
	<input type="hidden" name="q_sourceDateE" value="<%=Common.get(request.getParameter("q_sourceDateE")) %>" >
	<input type="hidden" name="q_oldPropertyNoS" value="<%=Common.get(request.getParameter("q_oldPropertyNoS")) %>" >
	<input type="hidden" name="q_oldPropertyNoE" value="<%=Common.get(request.getParameter("q_oldPropertyNoE")) %>" >
	<input type="hidden" name="q_oldSerialNoS" value="<%=Common.get(request.getParameter("q_oldSerialNoS")) %>" >
	<input type="hidden" name="q_oldSerialNoE" value="<%=Common.get(request.getParameter("q_oldSerialNoE")) %>" >
	<input type="hidden" name="q_oldlotNoS" value="<%=Common.get(request.getParameter("q_oldlotNoS")) %>" >
	<input type="hidden" name="q_oldlotNoE" value="<%=Common.get(request.getParameter("q_oldlotNoE")) %>" >
	<input type="hidden" name="q_specification" value="<%=Common.get(request.getParameter("q_specification")) %>" >
	<input type="hidden" name="q_nameplate" value="<%=Common.get(request.getParameter("q_nameplate")) %>" >
	
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">使用次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權利起始期間</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">權利終止期間</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">核定建物構造</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">核定建物基層面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">核定使用土地面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">核定建物面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">土地使用權利金</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,true,true,true,false,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



