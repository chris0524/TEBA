<!--
程式目的：土地改良物減少作業－減損單明細
程式代號：untrf009f
程式日期：0941007
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rf.UNTRF009F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rf.UNTRF009F)obj.queryOne();	
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
String tabs = uch._createTabData(uch._RF_REDUCE, 3);
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
<script language="javascript" src="../../js/getUNTRFAttachment.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>")	
);

//當增加原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.cause.value=="99"){
		form1.cause1.readOnly = false;
		form1.cause1.style.backgroundColor=editbg;
		form1.newEnterOrg.value = "";
		form1.newEnterOrgName.value = "";
		form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
	}else if(form1.cause.value=="01" || form1.cause.value=="07"||form1.cause.value=="08"){
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
		form1.transferDate.style.backgroundColor=editbg;
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
	}else{
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
		form1.newEnterOrg.value = "";
		form1.newEnterOrgName.value = "";
		form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
	}
	
	//需否呈報市府核定
	if((form1.cause.value!="01" && form1.cause.value!="08" && form1.cause.value!="90")){
		if (( form1.bookValue.value >= 2000000 ) || ( getToday() < form1.permitReduceDate.value ) ) {
			form1.submitCityGov.value = "Y";		
		}
	}else{
		form1.submitCityGov.value = "N";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untrf008f.jsp";
		} else {
			form1.action = "untrf009f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.cause,"減損原因");
		alertStr += checkLen(form1.notes, "備註", 250);		
		if (form1.cause.value=="99"){
			alertStr += checkEmpty(form1.cause1,"減損原因-其他說明");
		}
		
		if ((form1.cause.value=="01")||(form1.cause.value=="07")||(form1.cause.value=="08")){
			alertStr += checkEmpty(form1.newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.transferDate,"交接日期");
		}
		
		if ((form1.cause.value=="02")||(form1.cause.value=="03")){
			alertStr += checkEmpty(form1.lawBasis,"法令依據");
		}
		
		alertStr += checkDate(form1.transferDate,"交接日期");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,propertyNo,serialNo,ownership){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untrf008f.jsp"){	
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
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function gountrf010f(){
	var prop="";
	var iwidth=screen.availWidth;
	var iheight=screen.availHeight;
	var windowTop=0;
	var windowLeft=0;
	prop=prop+"width="+iwidth+",";
	prop=prop+"height="+iheight+",";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"resizable=yes";
	
	var enterOrg = form1.enterOrg.value;
	var ownership = form1.ownership.value;
	var reduceDate = form1.reduceDate.value;
	var verify = form1.verify.value;
	var caseNo = form1.caseNo.value;
	beforeSubmit();
	returnWindow=window.open("untrf010f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&reduceDate="+reduceDate+"&verify="+verify+"&caseNo="+caseNo,"aha",prop);	
}

//取得已使用年數、累計折舊、殘餘價值、需否呈報本府核定等資料
//untrf009.jsp 和 untrf010f.java 都有用到
function getdata(){
	//已使用年數 = (系統日期 - 建築日期) 之年數
	form1.useYear.value = parseInt((getDateDiff("m", form1.buildDate , form1.getToday)) / 12) ;
	
	//已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
	form1.useMonth.value = (getDateDiff("m", form1.buildDate , form1.getToday)) % 12 ;

	//累計折舊、殘餘價值
	var deprMethod = form1.deprMethod.value;

	if (deprMethod == "01") {
		form1.accumdepr1.value = "0";
		form1.scrapValue1.value = "0";
	} else if (deprMethod == "05")  {
		form1.accumdepr1.value = form1.bookValue.value;
		form1.scrapValue1.value = "0";
	} else if (deprMethod == "02" || deprMethod == "03" ||deprMethod == "04") {
		var accumdepr1;
		if ( form1.getToday.value.substring(0,5) > form1.apportionEndYM.value ) {
			accumdepr1 = form1.deprAmount.value  ;
		} else {
			//accumdepr1 = form1.accumDepr.value + ( (getDateDiff("m",form1.getToday,form1.accumDeprYm.value) - 1) * form1.monthDepr.value ) ;
			accumdepr1 = ((parseInt(form1.useYear.value) * 12) + parseInt(form1.useMonth.value)) * parseInt(form1.monthDepr.value);
		}
		form1.accumdepr1.value = accumdepr1;
		form1.scrapValue1.value = form1.bookValue.value - accumdepr1 ;
	}
	
	//需否呈報市府核定
	if((form1.cause.value!="01" && form1.cause.value!="08"  && form1.cause.value!="90")){
		if (( form1.bookValue.value >= 2000000 ) || ( getToday() < form1.permitReduceDate.value ) ) {
			form1.submitCityGov.value = "Y";		
		}
	}else{
		form1.submitCityGov.value = "N";
	}
}

function init(){
	if (form1.verify.value=="Y") {
		setFormItem("insert,delete,batchInsertBut", "R");
	}	
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
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
	}
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">

<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input class="field_QRO" type="HIDDEN" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
<TABLE STYLE="width:100%" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTRF008Q",obj);%>
	<jsp:include page="untrf008q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="14%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
		　　&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		　　&nbsp;&nbsp;電腦單號：
			[<input type="text" name="caseNo" class="field_QRO" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font> 財產編號：</td>
		<td class="td_form_white" colspan="3">
	  		<input class="field_P" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','111');getUNTRFAttachment(); getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();"> 
	  		<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyName','111')" value="..." title="財產編號輔助視窗">
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
	   		　分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getUNTRFAttachment(); getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();">
		<br>
			別名：
			[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="7" maxlength="7" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan = "3">
			日期：[<input type="text" name="reduceDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>	
			&nbsp;&nbsp;&nbsp;  月結：
			<select class="field_QRO" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		<br>	
			需否呈報本府核定：
				<select class="field_RO" type="select" name="submitCityGov">
					<%=util.View.getYNOption(obj.getSubmitCityGov())%>
				</select>
		<br>
			<font color="red">*</font>減損原因：
			<select class="field" type="select" name="cause" onchange="changecause()">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getCause())%>
			</select> 
				&nbsp;　其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		<br>
			 &nbsp; 法令依據：<input class="field" type="text" name="lawBasis" size="51" maxlength="60" value="<%=obj.getLawBasis()%>">
		<br>
			報廢(損)後處理情形：<input class="field" type="text" name="reducedeal" size="50" maxlength="60" value="<%=obj.getReducedeal()%>">
		<br>
			接管機關：
			<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>
			&nbsp;　　　　　　　交接日期：
			<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		<br>
			已使用年月：
			[<input class="field_RO" type="text" name="useYear" size="10" value="<%=obj.getUseYear()%>">]年
			[<input class="field_RO" type="text" name="useMonth" size="10" value="<%=obj.getUseMonth()%>">]個月
			&nbsp;　　可報廢日期：[<input type="text" name="permitReduceDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getPermitReduceDate()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">建築日期：</td>
		<td class="td_form_white" colspan="3">
			[<input type="text" name="buildDate" class="field_RO"  size="6" maxlength="7" value="<%=obj.getBuildDate()%>">]
			&nbsp;&nbsp;　　　　　　　　財產取得日期：[<input type="text" name="sourceDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			&nbsp;　　　　　　　　　　基金財產：
			<select class="field_RO" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">珍貴財產：</td>
		<td class="td_form_white" colspan="3">		
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			&nbsp;　　　　　　　　　抵繳遺產稅：	
			<select class="field_RO" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
			計量數：[<input class="field_RO" type="text" name="measure" size="9" maxlength="9" value="<%=obj.getMeasure()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
		<br>
			帳面價值：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
			　會計科目：[<input class="field_RO" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法：
			<select name="deprMethod" class="field_RO" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
			</select>
		<br>
			應攤提折舊總額：[<input class="field_RO" type="text" name="deprAmount" size="15" value="<%=obj.getDeprAmount()%>">]
			　　　月提折舊金額：[<input class="field_RO" type="text" name="monthDepr" size="15" value="<%=obj.getMonthDepr()%>">]
		<br>
			攤提年限截止年月：[<input class="field_RO" type="text" name="apportionEndYM" size="5" value="<%=obj.getApportionEndYM()%>">]
		<br>
			累計折舊調整年月：[<input class="field_RO" type="text" name="accumDeprYM" size="5" value="<%=obj.getAccumDeprYM()%>">]
			　　　　　累計折舊調整金額：[<input class="field_RO" type="text" name="accumDepr" size="15" value="<%=obj.getAccumDepr()%>">]
		<br>
			累計折舊：[<input class="field_RO" type="text" name="accumdepr1" size="15" value="<%=obj.getAccumdepr1()%>">]
			殘餘價值：[<input class="field_RO" type="text" name="scrapValue1" size="15" value="<%=obj.getScrapValue1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
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
	<span id="spanID">
		<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountrf010f();">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">財產性質</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">帳面價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">報府核定</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">帳務摘要</a></th>
	</thead> 
	<tbody id="listTBODY">	
	<%
	boolean primaryArray[] = {true,false,false,true,true,false,false,false,false,false,false,true,false};
	boolean displayArray[] = {false,true,true,true,true,true,true,true,true,true,false,false,true};
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
			setFormItem("ownership,verify","R");
			setFormItem("cause1,btn_newEnterOrg,transferDate,btn_transferDate,batchInsertBut", "R");
			break;
		case "insertError":
			setFormItem("ownership,verify","R");
			setFormItem("cause1,btn_newEnterOrg,transferDate,btn_transferDate,batchInsertBut", "R");
			break;
	
		//更新時要做的動作
		case "update":
			if(form1.verify.value=="Y"){
				setFormItem("propertyNo,btn_propertyNo,propertyName,serialNo,cause,cause1,newEnterOrg,btn_newEnterOrg,transferDate,btn_transferDate,bookNotes,notes","R");
			}else{
				setFormItem("cause,cause1,newEnterOrg,btn_newEnterOrg,transferDate,btn_transferDate,bookNotes,notes","O");
				if (form1.cause.value=="99"){
					form1.cause1.readOnly = false;
					form1.cause1.style.backgroundColor=editbg;
					form1.newEnterOrgName.value = "";
					form1.transferDate.value = "";
					form1.transferDate.style.backgroundColor=readbg;
					setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
				}else if(form1.cause.value=="01" || form1.cause.value=="07"){
					setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
					form1.transferDate.style.backgroundColor=editbg;
					form1.cause1.value="";
					form1.cause1.style.backgroundColor=readbg;
					form1.cause1.readOnly = true;
				}else{
					form1.cause1.value="";
					form1.cause1.style.backgroundColor=readbg;
					form1.cause1.readOnly = true;
					form1.newEnterOrgName.value = "";
					form1.transferDate.value = "";
					form1.transferDate.style.backgroundColor=readbg;
					setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
				}
			}
			break;
		case "updateError":
			if(form1.verify.value=="Y"){
				setFormItem("propertyNo,btn_propertyNo,propertyName,serialNo,cause,cause1,newEnterOrg,btn_newEnterOrg,transferDate,btn_transferDate,bookNotes,notes","R");
			}else{
				setFormItem("cause,cause1,newEnterOrg,btn_newEnterOrg,transferDate,btn_transferDate,bookNotes,notes","O");
				if (form1.cause.value=="99"){
					form1.cause1.readOnly = false;
					form1.cause1.style.backgroundColor=editbg;
					form1.newEnterOrgName.value = "";
					form1.transferDate.value = "";
					form1.transferDate.style.backgroundColor=readbg;
					setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
				}else if(form1.cause.value=="01" || form1.cause.value=="07"){
					setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
					form1.transferDate.style.backgroundColor=editbg;
					form1.cause1.value="";
					form1.cause1.style.backgroundColor=readbg;
					form1.cause1.readOnly = true;
				}else{
					form1.cause1.value="";
					form1.cause1.style.backgroundColor=readbg;
					form1.cause1.readOnly = true;
					form1.newEnterOrgName.value = "";
					form1.transferDate.value = "";
					form1.transferDate.style.backgroundColor=readbg;
					setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
				}
			}
			break;
		//取消時要執行的動作
		case "clear":
			if(form1.verify.value=="Y"){
				setFormItem("batchInsertBut","R");
			}else{
				setFormItem("batchInsertBut","O");
			}
			break;
		case "clearError":
			if(form1.verify.value=="Y"){
				setFormItem("batchInsertBut","R");
			}else{
				setFormItem("batchInsertBut","O");
			}
			break;

		//確定時要執行的動作
		case "confirm":
			if(form1.verify.value=="Y"){
				setFormItem("batchInsertBut","R");
			}else{
				setFormItem("batchInsertBut","O");
			}
			break;
		case "confirmError":
			if(form1.verify.value=="Y"){
				setFormItem("batchInsertBut","R");
			}else{
				setFormItem("batchInsertBut","O");
			}
			break;
	}
}
</script>
</body>
</html>
