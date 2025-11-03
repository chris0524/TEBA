<!--
程式目的：權利主檔資料維護-標的資料
程式代號：untrt002f
程式日期：0940930
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT002F">
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
	obj = (unt.rt.UNTRT002F)obj.queryOne();	
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
	tabs = uch._createTabData(uch._RT_ADD, 4);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._RT_ADD, 3);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._RT_ADD, 3);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._RT_ADD, 3);
}else if("untch001f02extend01".equals(obj.getProgID())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._RT_ADD, 3);
}else if("untch001f02extend02".equals(obj.getProgID())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._RT_ADD, 3);
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

insertDefault = new Array(
	new Array("dataState", "1"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		//查詢時為增加單查詢或是標的資料查詢
		if (document.all.querySelect[0].checked) {
			form1.action = "untrt001f.jsp";
		} else {
			form1.action = "untrt002f.jsp";
		}

	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			alertStr += checkEmpty(form1.signNo1,"土地標示代碼－縣市");			
			alertStr += checkEmpty(form1.signNo2,"土地標示代碼－鄉鎮市區");
			alertStr += checkEmpty(form1.signNo3,"土地標示代碼－地段");
			alertStr += checkEmpty(form1.signNo4,"土地標示代碼－地號(母號)");
			alertStr += checkEmpty(form1.signNo5,"土地標示代碼－地號(子號)");		
		}
		
		alertStr += checkEmpty(form1.area,"整筆面積(㎡)");
		alertStr += checkFloat(form1.area,"整筆面積(㎡)",9,2);
		alertStr += checkEmpty(form1.setArea,"設定面積(㎡)");
		alertStr += checkFloat(form1.setArea,"設定面積(㎡)",10,2);
		alertStr += checkEmpty(form1.holdNume,"權利範圍－分子");
		alertStr += checkFloat(form1.holdNume,"權利範圍－分子",10,0);
		alertStr += checkEmpty(form1.holdDeno,"權利範圍－分母");
		alertStr += checkFloat(form1.holdDeno,"權利範圍－分母",10,0);
		alertStr += checkEmpty(form1.rightHoldNume,"他項權利範圍－分子");
		alertStr += checkFloat(form1.rightHoldNume,"他項權利範圍－分子",10,0);
		alertStr += checkEmpty(form1.rightHoldDeno,"他項權利範圍－分母");
		alertStr += checkFloat(form1.rightHoldDeno,"他項權利範圍－分母",10,0);		
		alertStr += checkEmpty(form1.setObligor,"設定義務人");
		alertStr += checkLen(form1.notes, "備註", 250);
		//他項權利範圍－分子
		if (parseInt(form1.holdNume.value)<=0){
			form1.holdNume.style.backgroundColor=errorbg;
			alertStr +="他項權利範圍－分子必須 > 0 !\n";
		}else
			form1.holdNume.style.backgroundColor="";
		if(AllTrim(form1.holdNume).length>0 && AllTrim(form1.holdDeno).length>0){
			var holdNume = parseFloat(form1.holdNume.value);
			var holdDeno = parseFloat(form1.holdDeno.value);
			if(holdNume > holdDeno)
				alertStr += "他項權利範圍－分子不可大於他項權利範圍－分母！\n";
		}		
		//他項權利範圍－分母
		if (parseInt(form1.holdDeno.value)<=0){
			form1.holdDeno.style.backgroundColor=errorbg;
			alertStr +="他項權利範圍－分母必須 > 0 !\n";
		}else
			form1.holdDeno.style.backgroundColor="";
		//整筆面積
		if (parseInt(form1.area.value)<=0){
			form1.area.style.backgroundColor=errorbg;
			alertStr +="整筆面積必須 > 0 !\n";
		}else
			form1.area.style.backgroundColor="";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untrt001f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.propertyNo,"財產編號");
				alertStr += checkEmpty(form1.serialNo,"財產分號");			
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"財產編號");
			alertStr += checkEmpty(form1.serialNo,"財產分號");			
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		
		if (surl=="../ch/untch001f02.jsp"){
			if('<%=obj.getIsAddProof()%>' != 'true'){
				form1.state.value="queryAll";
			}
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
}

function init(){
	var serialNo = parse(form1.serialNo.value);
	var propertyNo = parse(form1.propertyNo.value);
	var ownership = parse(form1.ownership.value);
	var enterOrg = parse(form1.enterOrg.value);
	if (serialNo.length<=0 || propertyNo.length<=0 || ownership.length<=0 || enterOrg.length<=0){
		setFormItem("insert", "R");
	}else{
		setFormItem("insert", "O");
	}

	if (form1.state.value=="queryOne" && form1.enterOrg.value!=<%="\""+user.getOrganID()+"\""%>) {		
		setFormItem("insert,update,delete,clear,confirm", "R");
	}  

	if(form1.verify.value=="Y" || form1.closing.value=="Y")  setFormItem("insert,delete","R");
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}
}

function checkHoldArea(){
	if(AllTrim(form1.area).length>0 && AllTrim(form1.holdNume).length>0  && AllTrim(form1.holdDeno).length>0){
		form1.holdArea.value = roundp((parseFloat(form1.area.value) * parseFloat(form1.holdNume.value) / parseFloat(form1.holdDeno.value)),2,"Y") ;
		form1.setArea.value = roundp((parseFloat(form1.holdArea.value) * parseFloat(form1.rightHoldNume.value) / parseFloat(form1.rightHoldDeno.value)),2,"Y") ;
	}else{
		form1.holdArea.value = 0;
		form1.setArea.value = 0;
	}	
}

function checkBookValue(){
	form1.bookValue.value = form1.originalBV.value;
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
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
		<td class="td_form" width="15%">標的次序：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]
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
			<input class="field" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onchange="getFrontZero(this.name,4);"> -
			<input class="field" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onchange="getFrontZero(this.name,4);">	  
		<br>
			用途：<input class="field" type="text" name="landPurpose" size="60" maxlength="28" value="<%=obj.getLandPurpose()%>">
	  </td>
	</tr>
	<tr>
		<td class="td_form">建物資料：</td>
		<td class="td_form_white" colspan="3">
			房屋門牌：<input class="field" type="text" name="doorPlate" size="40" maxlength="50" value="<%=obj.getDoorPlate()%>">
			&nbsp;　面積(㎡)：<input class="field" type="text" name="buildingArea" size="9" maxlength="9" value="<%=obj.getBuildingArea()%>">
		<br>
			所有人：<input class="field" type="text" name="buildingOwner" size="15" maxlength="13" value="<%=obj.getBuildingOwner()%>">
			&nbsp;　用途：<input class="field" type="text" name="buildingPurpose" size="40" maxlength="28" value="<%=obj.getBuildingPurpose()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">範圍：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>整筆面積(㎡)：<input class="field" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>" onFocusOut="checkHoldArea();">
			&nbsp;　　　　　<font color="red">*</font>權利範圍：
			<input class="field" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>" onFocusOut="checkHoldArea();">
			/
			<input class="field" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>" onFocusOut="checkHoldArea();">
		<br>
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;
			<font color="red">*</font>他項權利範圍：
			<input class="field" type="text" name="rightHoldNume" size="10" maxlength="10" value="<%=obj.getRightHoldNume()%>" onFocusOut="checkHoldArea();">
			/
			<input class="field" type="text" name="rightHoldDeno" size="10" maxlength="10" value="<%=obj.getRightHoldDeno()%>" onFocusOut="checkHoldArea();">
			&nbsp;　　&nbsp;
			<br>
			設定面積(㎡)：[<input class="field_RO" type="text" name="setArea" size="9" maxlength="9" value="<%=obj.getSetArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">登記次序：</td>
		<td class="td_form_white" colspan="3">
			他項登記次序：<input class="field" type="text" name="registerNo1" size="8" maxlength="8" value="<%=obj.getRegisterNo1()%>">
			&nbsp;&nbsp;權利標的：
			<select class="field" type="select" name="rightKind" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RKD' ", obj.getRightKind())%>
			</select>
			&nbsp;&nbsp;標的登記次序：<input class="field" type="text" name="registerNo2" size="4" maxlength="4" value="<%=obj.getRegisterNo2()%>">
		<br>
			設定權利範圍：<input class="field" type="text" name="setRightScope" size="30" maxlength="50" value="<%=obj.getSetRightScope()%>">
			&nbsp;&nbsp;<font color="red">*</font>設定義務人：<input class="field" type="text" name="setObligor" size="15" maxlength="13" value="<%=obj.getSetObligor()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">其他事項(財產卡備註)：</td>
		<td class="td_form_white" colspan="3">
			<input type="text" class="field" name="notes1" value="<%=obj.getNotes1() %>" size="30">
		</td>
	</tr>
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form"style="display:none">異動人員/日期：</td>
		<td class="td_form_white"style="display:none">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
	<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">
	<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
	<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
	<input type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" name="verify" value="<%=obj.getVerify()%>">
	<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
	
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">標的次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">設定面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">設定義務人</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true};
	boolean displayArray[] = {false,false,false,false,true,true,true,true,true,true,false,false,false,false,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language='javascript'>
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[0].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
/*			if (parse(form1.q_signNo1.value).length<=0) {
				form1.q_signNo1.value="E000000";
				changeSignNo1("q_signNo1","q_signNo2","q_signNo3","E000000");
			}
*/			
			break;			
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");

	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			changeSignNo1("signNo1","signNo2","signNo3","E000000");
			break;
		//更新時要做的動作
		case "update":
			if(form1.verify.value=="Y" || form1.closing.value=="Y"){
			document.all.signNo1.disabled=true;
			document.all.signNo2.disabled=true;
			document.all.signNo3.disabled=true;
			document.getElementById("signNo4").readOnly=true;
			document.getElementById("signNo5").readOnly=true;
			document.getElementById("area").readOnly=true;
			document.getElementById("holdNume").readOnly=true;
			document.getElementById("holdDeno").readOnly=true;
			document.getElementById("holdArea").readOnly=true;
			document.getElementById("setArea").readOnly=true;
			document.getElementById("originalBV").readOnly=true;
			document.getElementById("setObligor").readOnly=true;
			}
			break;
		case "updateError":
			if(form1.verify.value=="Y" || form1.closing.value=="Y"){
			document.all.signNo1.disabled=true;
			document.all.signNo2.disabled=true;
			document.all.signNo3.disabled=true;
			document.getElementById("signNo4").readOnly=true;
			document.getElementById("signNo5").readOnly=true;
			document.getElementById("area").readOnly=true;
			document.getElementById("holdNume").readOnly=true;
			document.getElementById("holdDeno").readOnly=true;
			document.getElementById("holdArea").readOnly=true;
			document.getElementById("setArea").readOnly=true;
			document.getElementById("originalBV").readOnly=true;
			document.getElementById("setObligor").readOnly=true;
			}
			break;
	}
}
</script>
</body>
</html>



