<!--
程式目的：動產保管使用異動作業－明細資料
程式代號：untmp010f
程式日期：0941019
程式作者：carey
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP010F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP010F)obj.queryOne();	
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
String tabs = uch._createTabData(uch._MP_MOVE, 3);
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/unitProperty.js"></script>
<script language="javascript" src="../../js/getUNTMPMovable.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untmp009f.jsp";
		} else {
			form1.action = "untmp010f.jsp";
		}
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.lotNo,"財產批號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.buyDate,"購置日期");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		alertStr += checkEmpty(form1.bookAmount,"帳面數量");
		alertStr += checkEmpty(form1.bookValue,"帳面總價");
		alertStr += checkEmpty(form1.oldKeepUnit,"原保管單位");
		alertStr += checkEmpty(form1.oldKeeper,"原保管人");
		alertStr += checkEmpty(form1.oldUseUnit,"原使用單位");
		alertStr += checkEmpty(form1.oldUserNo,"原使用人");
		//alertStr += checkEmpty(form1.newMoveDate,"新移動日期");
		alertStr += checkDate(form1.newMoveDate,"新移動日期");
		alertStr += checkEmpty(form1.newKeepUnit,"新保管單位");
		alertStr += checkEmpty(form1.newKeeper,"新保管人");
		alertStr += checkEmpty(form1.newUseUnit,"新使用單位");
		alertStr += checkEmpty(form1.newUserNo,"新使用人");
		alertStr += checkEmpty(form1.useYear,"已使用年數");
		alertStr += checkEmpty(form1.useMonth,"已使用月數");
		alertStr += checkLen(form1.notes,"備註",250);
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

function checkURL(surl){
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		form1.action=surl;
		form1.state.value="queryOne";
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	var caseNo = parse(form1.caseNo.value);
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	if (form1.verify.value=="Y"){
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
}

function getData(){
	//已使用年數 = (系統日期 - 購置日期) 之年數
	form1.useYear.value = parseInt((getDateDiff("m", form1.buyDate , form1.getToday))/12) ;
	
	//已使用月數 = (系統日期 - 購置日期) ★/12 之餘額月數
	form1.useMonth.value = (getDateDiff("m", form1.buyDate , form1.getToday))%12;
}

function checkValue(){
	var checkCaseNo = form1.caseNo.value;
	var checkPropertyNo = form1.propertyNo.value;
	var checkSerialNo = form1.serialNo.value;
	if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.propertyNo.value="";
		form1.propertyNoName.value="";
		form1.serialNo.value="";
	}else{
		if(parse(form1.keepUnit.value).length>0 || parse(form1.keeper.value).length>0){
			form1.oldMoveDate.value=form1.moveDate.value;
			getKeepUnit(form1.tempEnterOrg, form1.oldKeepUnit, form1.keepUnit.value);
			getKeeper(form1.tempEnterOrg, form1.oldKeepUnit, form1.oldKeeper, form1.keeper.value ,'N');
			getKeepUnit(form1.tempEnterOrg, form1.oldUseUnit, form1.useUnit.value);
			getKeeper(form1.tempEnterOrg, form1.oldUseUnit, form1.oldUserNo, form1.userNo.value ,'N');
			form1.oldPlace.value=form1.place.value;
		}
	}
}

function changeUse(){
    changeBureau(form1.tempEnterOrg,form1.newUseBureau,form1.newKeepBureau.value);
	getKeepUnit(form1.tempEnterOrg, form1.newUseUnit, form1.newKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.newUseUnit, form1.newUserNo, form1.newKeeper.value ,'Y');
}

function gountmp011f(){
	var prop="";
	//var windowTop=(document.body.clientHeight-80)/2+25;
	//var windowLeft=(document.body.clientWidth-800)/2+250;
	//prop=prop+"width=775,height=475,";
	
	var iwidth=screen.availWidth;
	var iheight=screen.availHeight;
	var windowTop=0;
	var windowLeft=0;
	prop=prop+"width="+iwidth+",";
	prop=prop+"height="+iheight+",";
	
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	var enterOrg = form1.enterOrg.value;
	var ownership = form1.ownership.value;
	var caseNo = form1.caseNo.value;
	var newMoveDate = form1.newMoveDate.value;
	var verify = form1.verify.value;
	beforeSubmit();
	returnWindow=window.open("untmp011f.jsp?q_enterOrg="+enterOrg+"&tempEnterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_newMoveDate="+newMoveDate+"&q_verify="+verify,"aha",prop);	
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>">
<input class="field_QRO" type="hidden" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
<input type="hidden" name="keepUnit" value="">
<input type="hidden" name="keeper" value="">
<input type="hidden" name="useUnit" value="">
<input type="hidden" name="userNo" value="">
<input type="hidden" name="place" value="">
<input type="hidden" name="tempEnterOrg" value="<%=obj.getEnterOrg()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTMP009Q",obj);%>
	<jsp:include page="untmp009q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
			<select class="field_QRO" type="select" name="ownership">
			<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;電腦單號：
			[<input class="field_QRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">購置日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;&nbsp;　　　　　　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;　　　月結：
			<select class="field_QRO" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','3,4,5');getUNTMPMovable();getData();checkValue();"> 
			<input class="field_P" type="hidden" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','3,4,5&isLookup=N')" value="..." title="財產編號輔助視窗">			
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			&nbsp;
			分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>"  onChange="getUNTMPMovable();getData();checkValue();">
			&nbsp;&nbsp;　　　批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
		<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]
			&nbsp;&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3"> 
			[<input class="field_RO" type="text" name="material" size="20" value="<%=obj.getMaterial()%>">]
			　其他主要材質：
			[<input class="field_RO" type="text" name="otherMaterial" size="25" maxlength="50" value="<%=obj.getOtherMaterial()%>">]
		<br>
			單位：
			[<input class="field_RO" type="text" name="propertyUnit" size="7" maxlength="7" value="<%=obj.getPropertyUnit()%>">]　 　  　　
			&nbsp;&nbsp;&nbsp;　其他單位：
			[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>">] 
		<br>
	  		使用年限： 
	  		[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="7" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;　調整後年限(月)：
			[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">已使用年數：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="useYear" size="3" maxlength="3" value="<%=obj.getUseYear()%>">]
			年
			[<input class="field_RO" type="text" name="useMonth" size="2" maxlength="2" value="<%=obj.getUseMonth()%>">]
			個月
			&nbsp;&nbsp;　　　　　預留殘值：[<input class="field_RO" type="text" name="scrapValue" size="15" maxlength="15" value="<%=obj.getScrapValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">廠牌：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate()%>">]
			　　　　　型式：[<input class="field_RO" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			&nbsp;&nbsp;基金財產：
			<select class="field_RO" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
			&nbsp;&nbsp;珍貴財產：
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">帳面資料：</td>
		<td class="td_form_white" colspan="3">
			取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
			&nbsp;&nbsp;　　　　帳面數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">]
		<br>
			帳面單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="13" value="<%=obj.getBookUnit()%>">]
			&nbsp;&nbsp;　帳面總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">原移動資料：</td>
		<td class="td_form_white" colspan="3">
			移動日期：[<input class="field_RO" type="text" name="oldMoveDate" size="7" maxlength="7" value="<%=obj.getOldMoveDate()%>">]
		<br>
			保管單位：
			<select class="field_RO" type="select" name="oldKeepUnit" onChange="getKeeper(form1.enterOrg, this, form1.oldKeeper, '' ,'N');">
              <%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + ("".equals(obj.getEnterOrg())?user.getOrganID():obj.getEnterOrg()) + "' order by unitno ", obj.getOldKeepUnit()) %>
            </select>
			&nbsp;&nbsp;&nbsp;　　保管人：
			<select class="field_RO" type="select" name="oldKeeper">
              <script>getKeeper(form1.enterOrg, form1.oldKeepUnit, form1.oldKeeper, '<%=obj.getOldKeeper()%>', 'N');</script>
            </select>
        <br>
			使用單位：
			<select class="field_RO" type="select" name="oldUseUnit" onChange="getKeeper(form1.enterOrg, this, form1.oldUseUnit, '' ,'N');">
              <%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + ("".equals(obj.getEnterOrg())?user.getOrganID():obj.getEnterOrg()) + "' order by unitno ", obj.getOldUseUnit()) %>
            </select>
			&nbsp;&nbsp;&nbsp;　　使用人：
			<select class="field_RO" type="select" name="oldUserNo">
				<script>getKeeper(form1.enterOrg, form1.oldUseUnit, form1.oldUserNo, '<%=obj.getOldUserNo()%>' ,'N');</script>
			</select>
		<br>
			存置地點：[<input class="field_RO" type="text" name="oldPlace" size="40" maxlength="50" value="<%=obj.getOldPlace()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">新移動資料：</td>
		<td class="td_form_white" colspan="3">
			移動日期：[<input class="field_QRO" type="text" name="newMoveDate" size="7" maxlength="7" value="<%=obj.getNewMoveDate()%>">]
		<br>
			<div style="display:none">	
		           保管處別：
			<select class="field" type="select" name="newKeepBureau" onChange="getKeepUnit2(form1.enterOrg,form1.newKeepBureau,form1.newKeepUnit,form1.newKeeper,'');changeUse();">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", obj.getNewKeepBureau())%>			
            </select>
            </div>
			<font color="red">*</font>保管單位：
			<select class="field" type="select" name="newKeepUnit" onChange="getKeeper(form1.enterOrg, this, form1.newKeeper, 'Y');changeUse();">
              <%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + ("".equals(obj.getEnterOrg())?user.getOrganID():obj.getEnterOrg()) + "' order by unitno ", obj.getNewKeepUnit()) %>
            </select>
			&nbsp;&nbsp;&nbsp;　<font color="red">*</font>保管人：
			<select class="field" type="select" name="newKeeper" onChange="changeUse();">
              <script>getKeeper(form1.enterOrg, form1.newKeepUnit, form1.newKeeper, '<%=obj.getNewKeeper()%>','Y');</script>
            </select>
		<br>
			<div style="display:none">	
		           使用處別：
			<select class="field" type="select" name="newUseBureau" onChange="getKeepUnit2(form1.enterOrg,form1.newUseBureau,form1.newUseUnit,form1.newUserNo,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", obj.getNewUseBureau())%>			
            </select>
            </div>
			<font color="red">*</font>使用單位：
			<select class="field" type="select" name="newUseUnit" onChange="getKeeper(form1.enterOrg, this, form1.newUserNo, 'Y');">
              	<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + ("".equals(obj.getEnterOrg())?user.getOrganID():obj.getEnterOrg()) + "' order by unitno ", obj.getNewUseUnit()) %>
            </select>
			&nbsp;&nbsp;&nbsp;　<font color="red">*</font>使用人：
			<select class="field" type="select" name="newUserNo">
				<script>getKeeper(form1.enterOrg, form1.newUseUnit, form1.newUserNo, '<%=obj.getNewUserNo()%>','Y');</script>
			</select>
		<br>
			存置地點：<input class="field" type="text" name="newPlace" size="40" maxlength="25" value="<%=obj.getNewPlace()%>">
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
	  	<textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="現有資料明細新增" onClick="gountmp011f();">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th> -->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產性質</a></th>
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">原保管單位</a></th>-->
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">原保管人</a></th>-->
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">原存置地點</a></th>-->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">新移動日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">新保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">新保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">新存置地點</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,false,true,false,false,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,false,false,true,true,true,false,true,false,false,false,false,false,true,false,true,false,true,true};
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
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[0].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
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
			setFormItem("batchInsertBut","R");
			break;
		case "insertError":
			setFormItem("batchInsertBut","R");
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("batchInsertBut","R");
			break;
		case "updateError":
			setFormItem("batchInsertBut","R");
			break;
	
		//取消時要執行的動作
		case "clear":
			setFormItem("batchInsertBut","O");
			break;
		case "clearError":
			setFormItem("batchInsertBut","O");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("batchInsertBut","O");
			break;
		case "confirmError":
			setFormItem("batchInsertBut","O");
			break;
	}
}
</script>
</body>
</html>



