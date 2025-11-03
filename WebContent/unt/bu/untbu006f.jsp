<!--
程式目的：建物主檔資料維護--基地資料
程式代號：untbu006f
程式日期：0940919
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->


<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU006F">
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
	obj = (unt.bu.UNTBU006F)obj.queryOne();	
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
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._BU_ADD, 7);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._BU_ADD, 6);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._BU_ADD, 6);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._BU_ADD, 6);
}else{
	uch._setupViewType(uch._queryOrMaintenance);
	tabs = uch._createTabData(uch._BU_ADD, 6);
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	} else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.signNo1,"土地標示代碼－縣市");
		alertStr += checkEmpty(form1.signNo2,"土地標示代碼－鄉鎮市區");
		alertStr += checkEmpty(form1.signNo3,"土地標示代碼－地段");
		alertStr += checkEmpty(form1.signNo4,"土地標示代碼－地號(母號)");
		alertStr += checkEmpty(form1.signNo5,"土地標示代碼－地號(子號)");	
				
		columnTrim(form1.landRule);
		columnTrim(form1.area);
		columnTrim(form1.holdNume);
		columnTrim(form1.holdDeno);
		if (form1.area.value.length!=0) {
			alertStr += checkFloat(form1.area,"整筆面積(㎡)",7,2);
			if (parseFloat(form1.area.value)<=0) alertStr += "整筆面積(㎡)若有資料，必須大於0!\n";
		}
		if (form1.holdNume.value.length!=0 && form1.holdDeno.value.length!=0) {			
			alertStr += checkInt(form1.holdNume,"權利範圍－分子");
			alertStr += checkInt(form1.holdDeno,"權利範圍－分母");		
			if (parseInt(form1.holdNume.value)<=0) alertStr += "權利範圍－分子若有資料，必須大於0!\n";
			if (parseInt(form1.holdDeno.value)<=0) alertStr += "權利範圍－分母若有資料，必須大於0!\n";			
			if (parseInt(form1.holdDeno.value)<parseInt(form1.holdNume.value)) alertStr += "權利範圍－分子不能大於分母!\n";
		}
		calHoldArea();
	
		if (form1.landRule.value.length!=0) {
			alertStr += checkInt(form1.landRule,"等則");
			if (form1.landRule.value.length==1) {
				form1.landRule.value = "0" + form1.landRule.value;
			}
		}
		
		alertStr += checkLen(form1.owner, "基地所有人", 15);
		alertStr += checkLen(form1.notes1, "其他事項", 30);
		alertStr += checkLen(form1.notes, "備註", 250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.differenceKind.value=differenceKind;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function calHoldArea() {
	form1.area.value = roundp(form1.area.value,'2','Y');
	if (parseFloat(form1.area.value)>0 && parseFloat(form1.holdNume.value)>0 && parseFloat(form1.holdDeno.value)>0 ) {	
		form1.holdArea.value = roundp((form1.area.value * form1.holdNume.value / form1.holdDeno.value),2,"Y") ;				
	} else form1.holdArea.value = "";
}


function init() {
	setFormItem("queryAll","R");
	document.all.item("spanQueryAll").style.display="none";	
	if (form1.dataState.value=="2" || form1.enterOrg.value!="<%=user.getOrganID()%>") {
		setFormItem("insert,update,delete,clear,confirm","R");
	}
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
}

function changeSelect() {
	if((form1.useSeparate.value).substr(0,1) == "A"){
		form1.useKind.disabled = false;
		alertStr += checkEmpty(form1.useKind,"編定使用種類)");
	}else { 
		form1.useKind.disabled = true; 
		form1.useKind.value = "";
		//form1.useKind.style.backgroundColor="";
	}
}


function getSignNoData(){
	var commentEnterOrg=[];
	var commentSignNo=[];

	commentEnterOrg.push(form1.enterOrg.value);	
	commentSignNo.push(form1.signNo3.value + form1.signNo4.value + form1.signNo5.value);
	
	var comment={};	
	comment.enterOrg=commentEnterOrg;
	comment.signNo=commentSignNo;
	
	//傳送JSON
	$.post('untbu006f_ajax.jsp',
		comment,
		function(data){
			//將回傳資料寫入		
			data=eval('('+data+')');

			$("input[name='area']").val(data['area']);			
			$("input[name='holdNume']").val(data['holdnume']);
			$("input[name='holdDeno']").val(data['holddeno']);
			$("input[name='holdArea']").val(data['holdarea']);
			$("input[name='manageOrg']").val(data['manageOrg']);
			$("input[name='manageOrgName']").val(data['manageOrgName']);
			$("input[name='owner']").val(data['owner']);
			form1.useSeparate.value=data['useseparate'];
			form1.useKind.value=data['usekind'];
			form1.field.value=data['field'];
			$("input[name='landRule']").val(data['landRule']);
			$("input[name='notes1']").val(data['notes1']);
			
		});

}

function checkURL(surl){
	if (surl=="../ch/untch001f02.jsp"){
		if('<%=obj.getIsAddProof()%>' != 'true'){
			form1.state.value="queryAll";
		}
	}else {
		form1.state.value="queryAll";
	}
	if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
		form1.mainPage1.value="";
		form1.currentPage.value=form1.mainPage.value;
	}
	form1.action = surl;
	form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
	form1.queryone_ownership.value=form1.mainOwnerShip.value;
	form1.queryone_caseNo.value=form1.mainCaseNo.value;
	form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--Query區============================================================-->
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer2" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame2"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">基地次序：</td>
		<td class="td_form_white">
			[ <input class="field_PRO" type="text" name="serialNo1" size="4" maxlength="3" value="<%=obj.getSerialNo1()%>"> ]
		</td>
		<td class="td_form"><font color="red">*</font>基地權屬：</td>
		<td class="td_form_white"><select class="field" type="select" id="ownership1" name="ownership1">
            <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='OWN' ", obj.getOwnership1())%>
          </select>
        </td>
	</tr>
	<tr>
	  <td class="td_form"><font color="#FF0000">*</font>土地標示：</td>
	  <td colspan="3" class="td_form_white">
			<select class="field" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>　
			<select class="field" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>　		
			<select class="field" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>			　
			地號：
			<input class="field" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onchange="getFrontZero(this.name,4);"> -
			<input class="field" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onchange="getFrontZero(this.name,4);getSignNoData();">	  
	  </td>
	  </tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>面積資訊：</td>
	  <td colspan="3" class="td_form_white">整筆面積(㎡)：
	    <input class="field" type="text" name="area" size="13" maxlength="13" value="<%=obj.getArea()%>" onChange="calHoldArea();" onBlur="calHoldArea();">
	    <br>
	    權利分子：
	      <input class="field" type="text" name="holdNume" size="8" maxlength="10" value="<%=obj.getHoldNume()%>" onChange="calHoldArea();" onBlur="calHoldArea();">　
	      權利分母：
	      <input class="field" type="text" name="holdDeno" size="8" maxlength="10" value="<%=obj.getHoldDeno()%>" onChange="calHoldArea();" onBlur="calHoldArea();">
	    權利範圍面積(㎡)：[<input name="holdArea" type="text" class="field_RO" value="<%=obj.getHoldArea()%>" size="10">]</td>
	  </tr>
	<tr>
	  <td class="td_form">管理機關：</td>
	  <td class="td_form_white"><%=util.View.getPopOrgan("field","manageOrg",obj.getManageOrg(),obj.getManageOrgName(),"Y")%> </td>
		<td class="td_form">基地所有人：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="owner" size="20" maxlength="30" value="<%=obj.getOwner()%>">
		</td>
	</tr>
	<tr>
	  <td class="td_form">使用分區：</td>
	  <td class="td_form_white"><select class="field" type="select" name="useSeparate" onchange="changeSelect();">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
        </select>
      </td>
	  <td class="td_form">編定使用種類：</td>
	  <td class="td_form_white"><select class="field" type="select" name="useKind" disabled="true">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getUseKind())%>
        </select>
      </td>
	</tr>
	<tr>
	  <td class="td_form">地目：</td>
	  <td class="td_form_white"><select class="field" type="select" name="field">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
        </select>
      </td>
	  <td class="td_form">等則：</td>
	  <td class="td_form_white"><input class="field" type="text" name="landRule" size="5" maxlength="2" value="<%=obj.getLandRule()%>">
      </td>
	</tr>
	<tr>
	  <td class="td_form">其他事項：</td>
	  <td class="td_form_white" colspan="3">
	  	<input class="field" type="text" name="notes1" size="20" maxlength="60" value="<%=obj.getNotes1()%>">
      </td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white" colspan="3"><textarea name="notes" cols="25" rows="4" class="field"><%=obj.getNotes()%></textarea></td>
	  <td class="td_form"style="display:none">異動人員/日期：</td>
	  <td class="td_form_white"style="display:none"> [
          <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
  /
  <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
  ] </td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
	<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
	<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">
	<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">	
	<input type="hidden" name="dataState" value="<%=obj.getDataState()%>">	
	<input type="hidden" name="propertyKind" value="<%=obj.getPropertyKind()%>">
	<input type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">	
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
	<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
	<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">	

	
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">基地次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">基地權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">基地所有人</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,false,false,false,true};
	boolean displayArray[] = {false,false,false,false,true,true,true,true,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script type="text/javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
			
			form1.useKind.disabled = true; 
			
			sSignNo = form1.oSignNo.value;
			if (parse(sSignNo).length>14) {
				if (isObj(form1.signNo1)) {
					getSelectedValue(form1.signNo1, sSignNo.substring(0,1)+"000000");
				}
				if (isObj(form1.signNo2)) {
					changeSignNo1('signNo1','signNo2','signNo3',sSignNo.substring(0,3)+'0000');					
				}
				if (isObj(form1.signNo3)) {
					changeSignNo2('signNo1','signNo2','signNo3',sSignNo.substring(0,7))						
				}
				/**		
				if (isObj(form1.signNo4)) {		
					form1.signNo4.value=sSignNo.substring(7,12);
				}										
				if (isObj(form1.signNo5)) {
					form1.signNo5.value=sSignNo.substring(12,15);
				}
				**/
			}
			break;	
	}
}
</script>
</body>
</html>



