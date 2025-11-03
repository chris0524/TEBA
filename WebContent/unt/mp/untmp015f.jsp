<!--
程式目的：動產減少作業－減損單明細
程式代號：untmp015f
程式日期：0941018
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP015F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP015F)obj.queryOne();	
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
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/getUNTMPMovable.js"></script>
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
		form1.newEnterOrgName.value = "";
		form1.newEnterOrg.value = "";
		form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
	}else if(form1.cause.value=="01" || form1.cause.value=="07" || form1.cause.value=="08"){
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
		setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "R");
		form1.transferDate.style.backgroundColor=editbg;
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
	}else{
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
		form1.newEnterOrgName.value = "";
		form1.newEnterOrg.value = "";
		form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
		setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "O");
	}
	
	if(( form1.adjustBookValue.value >= 15000000 ) || (( getToday() < form1.permitReduceDate.value ) && (form1.cause.value!="01" && form1.cause.value!="08" && form1.cause.value!="90"))){
		form1.submitCityGov.value = "Y"; 
	}else if(form1.cause.value == "03" || form1.cause.value == "04"){
		form1.submitCityGov.value = "Y";	
	}else{
		form1.submitCityGov.value = "N"; 
	}
	
	if(form1.cause.value=="02"){
	    var str = "已逾最低年限，不堪使用";
	    form1.cause2.value = str;
	}else{
	    form1.cause2.value="";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untmp014f.jsp";
		} else {
			form1.action = "untmp015f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.cause,"減損原因");
		if(form1.cause.value == '02'){
			alertStr += checkEmpty(form1.cause2,"報廢或失竊原因");
		}
		alertStr += checkLen(form1.notes, "備註", 250);		
		if (form1.cause.value=="99"){
			alertStr += checkEmpty(form1.cause1,"減損原因-其他說明");
		}
		
		if ((form1.cause.value=="01")||(form1.cause.value=="07")||(form1.cause.value=="08")){
			alertStr += checkEmpty(form1.newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.transferDate,"交接日期");
		}
		
		alertStr += checkDate(form1.transferDate,"交接日期");
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.buyDate,"購置日期");
		
		alertStr += checkEmpty(form1.adjustBookAmount,"減少面積數量");
		alertStr += checkEmpty(form1.adjustBookValue,"減少面積金額─總價");
		
		if (parseInt(form1.adjustBookAmount.value) < 1) alertStr += "減少帳面數量必須大於等於1\n";
		if (parseInt(form1.adjustBookValue.value) < 0) alertStr += "減少帳面金額─總價必須大於等於0\n";
		
		if (form1.oldBookAmount.value=="1") {
			if ((form1.adjustBookAmount.value != form1.oldBookAmount.value)||(form1.adjustBookValue.value != form1.oldBookValue.value)){
				alertStr += "原帳面數量為1，則減少帳面數量與金額須與原來的一致\n";
			}
		}
		
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
	var alertStr = "";
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untmp014f.jsp"){	
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

function gountmp016f(){
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
	var reduceDate = form1.reduceDate.value;
	var verify = form1.verify.value;
	beforeSubmit();
	returnWindow=window.open("untmp016f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_reduceDate="+reduceDate+"&q_verify="+verify,"aha",prop);	
}

//取得已使用年數、累計折舊、殘餘價值、需否呈報市府核定等資料
//untmp015.jsp 和 untmp016f.java 都有用到
function getdata(){
	//已使用年數 = (系統日期 - 購置日期) 之年數
	form1.useYear.value = parseInt((getDateDiff("m", form1.buyDate , form1.getToday))/12) ;
	
	//已使用月數 = (系統日期 - 購置日期) ★/12 之餘額月數
	form1.useMonth.value = (getDateDiff("m", form1.buyDate , form1.getToday))%12;
	
	//累計折舊、殘餘價值
	var deprMethod = form1.deprMethod.value ;
	if (parseInt(deprMethod) == 1) {
		//alert("deprMethod data is 1");
		form1.accumDepr1.value = "0";
		form1.scrapValue1.value = "0";
	} else if (parseInt(deprMethod) == 5)  {
		//alert("deprMethod data is 5");
		form1.accumDepr1.value = form1.adjustBookValue.value;
		form1.scrapValue1.value = "0";
	} else if (parseInt(deprMethod) >= 2 && parseInt(deprMethod) <= 4) {
		//alert("deprMethod data is big then 2 less then 4 ");
		var accumDepr1;
		var months="";
		var accumDeprYM_1="";
		if ( form1.getToday.value.substring(0,5) > form1.apportionEndYM.value) {
			accumDepr1 = parseInt(form1.deprAmount.value)  ;
		} else {
			accumDepr1 = parseInt(form1.accumDepr.value) + (parseInt(getDateDiff("m",getDateAdd("m",-1,form1.accumDeprYM.value+"01"),(form1.getToday.value.substring(0,5)+"01"))) * parseInt(form1.monthDepr.value)) ;
		}
		form1.accumDepr1.value = accumDepr1;
		form1.scrapValue1.value = parseInt(form1.adjustBookValue.value) - parseInt(accumDepr1) ;
	}
	//需否呈報市府核定
	//if (form1.cause.value=="01" ||form1.cause.value=="03" || form1.cause.value=="04"){
		//form1.submitCityGov.value = "Y";
	//}else 
	//if (( parseInt(form1.adjustBookValue.value) > 15000000 ) || ( form1.getToday.value < form1.permitReduceDate.value )) {
		    //form1.submitCityGov.value = "Y";
	//}else {
		//form1.submitCityGov.value = "N";
	//}
}

function getNewBookData() { 
	form1.newBookAmount.value = parseFloat(form1.oldBookAmount.value) - parseFloat(form1.adjustBookAmount.value) ;
	form1.newBookValue.value = parseFloat(form1.oldBookValue.value) - parseFloat(form1.adjustBookValue.value) ;
} 

function getDefault(){
	form1.adjustBookAmount.value = form1.oldBookAmount.value;
	form1.adjustBookValue.value = form1.oldBookValue.value;
}

function init(){
	var arrField = new Array("update","delete");
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete,batchInsertBut", "R");
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
	}else{
		form1.newBookAmount.value=parseInt(form1.oldBookAmount.value)-parseInt(form1.adjustBookAmount.value);
		form1.newBookValue.value=parseInt(form1.oldBookValue.value)-parseInt(form1.adjustBookValue.value);
	}
}


function getProperty(popPropertyNo,popPropertyName,preWord,isCls){
	var alertStr = "";
	var arrPreWord;
	var isLookup = false;
	var q_enterOrg = "";
	if (parse(preWord).length>0) arrPreWord = preWord.split("&");
	if (arrPreWord.length>1 && arrPreWord[1]=="isLookup=Y") isLookup = true;
	if (parse(document.all.item(popPropertyNo).value).length>0) {
		if (isObj(document.all.item(popPropertyNo))) {
			var obj = document.all.item(popPropertyNo);
			if (obj.className=="field_Q") {
				if ((isObj(document.all.item("q_enterOrg"))) && (document.all.item("q_enterOrg").value!="")) {
					q_enterOrg = document.all.item("q_enterOrg").value;
				}
			} else {
				if ((isObj(document.all.item("enterOrg"))) && (document.all.item("enterOrg").value!="")) {
					q_enterOrg = document.all.item("enterOrg").value;
				}			
			}
		}
		
		if (q_enterOrg.length>0) q_enterOrg = "&q_enterOrg="+q_enterOrg;
		
		var i = 0;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;
		if(xmlDoc.load("../../home/xmlProperty.jsp?q_propertyNo="+document.all.item(popPropertyNo).value+"&preWord="+preWord+q_enterOrg)) {
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				if (isObj(document.all.item(popPropertyName))) {
					document.all.item(popPropertyName).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");	
				}						
				if (isLookup) {
					if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyType");
					if (isObj(document.all.item("propertyUnit"))) document.all.item("propertyUnit").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
					if (isObj(document.all.item("material"))) document.all.item("material").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("material");
					if (isObj(document.all.item("limitYear"))) document.all.item("limitYear").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear");
					if (isObj(document.all.item("otherLimitYear"))){
						if (parseInt(xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear"))>0) {
								document.all.item("otherLimitYear").value="";
								document.all.item("otherLimitYear").readOnly=true;
						} else {
							document.all.item("limitYear").value="";
							document.all.item("otherLimitYear").readOnly=false;
						}
					}
				}	
			}
			if (isCls!="N") {
				if (i==0) {					
					//if (isObj(document.all.item(popPropertyNo))) document.all.item(popPropertyNo).value="";	
					if (isObj(document.all.item(popPropertyName))) document.all.item(popPropertyName).value="";	
					if (isLookup) {
						if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value="";
						if (isObj(document.all.item("propertyUnit"))) document.all.item("propertyUnit").value="";
						if (isObj(document.all.item("material"))) document.all.item("material").value="";
						if (isObj(document.all.item("limitYear"))) document.all.item("limitYear").value="";
						if (isObj(document.all.item("otherLimitYear"))) document.all.item("otherLimitYear").readOnly=false;
					}
				}
			}
		}
	} else { 
		if (isCls!="N") {	
			document.all.item(popPropertyNo).value="";
			document.all.item(popPropertyName).value="";
		}
	}
	if (arrPreWord.length>2 && arrPreWord[2]!="") eval(arrPreWord[2].substring(arrPreWord[2].indexOf("=")+1));
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">

<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input class="field_QRO" type="HIDDEN" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
<TABLE STYLE="width:100%" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" ><a href="#" onClick="return checkURL('untmp014f.jsp');">減損單資料</a></td>	
		<td ID=t2 CLASS="tab_border1" HEIGHT="25">減損單明細資料</td>	
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>	
	</tr>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTMP014Q",obj);%>
	<jsp:include page="untmp014q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="17%">入帳機關：
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
		　　&nbsp;　權屬：<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		　　電腦單號：[<input type="text" name="caseNo" class="field_QRO" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td width="120" class="td_form"><font color="red">*</font> 財產編號：</td>
		<td colspan="3" class="td_form_white">
	  		<input class="field_P" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','3,4,5');getUNTMPMovable();getDefault();form1.reduceDate.value='<%=obj.getReduceDate()%>';getNewBookData();getdata();checkValue();"> 
	  		<input class="field_P" type="hidden" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyName','3,4,5')" value="..." title="財產編號輔助視窗">
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" value="<%=obj.getPropertyNoName()%>">]
	   		&nbsp;&nbsp;&nbsp;分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getUNTMPMovable();getDefault();form1.reduceDate.value='<%=obj.getReduceDate()%>';getNewBookData();getdata();checkValue();">
	   		　　批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
	   	<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="7" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]
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
			&nbsp;&nbsp;&nbsp;其他單位：
			[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>">] 
		<br>
	  		使用年限： 
	  		[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="7" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;調整後年限(月)：
			[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]

			<input class="field" type="hidden" name="computerType" size="10" maxlength="10" value="<%=obj.getComputerType()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">已使用年月：</td>
		<td class="td_form_white" colspan = "3">
			[<input class="field_RO" type="text" name="useYear" size="5" maxlength="3" value="<%=obj.getUseYear()%>">]年
			[<input class="field_RO" type="text" name="useMonth" size="3" maxlength="2" value="<%=obj.getUseMonth()%>">]個月
			&nbsp;&nbsp;　　可報廢日期：[<input class="field_RO" type="text" name="permitReduceDate" size="7" maxlength="7" value="<%=obj.getPermitReduceDate()%>">]
		</td>	
	</tr>
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan = "3">
			日期：[<input type="text" name="reduceDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;&nbsp;　　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;　　　月結：
			<select class="field_QRO" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		<br>	
			<font color="red">*</font>減損原因：
			<select class="field" type="select" name="cause" onchange="changecause()">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getCause())%>
			</select> 
			&nbsp;&nbsp;其他說明：<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		<br>
			接管機關：<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>
			　　交接日期：<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		<br>
			繳存地點：<input class="field" type="text" name="returnPlace" size="20" maxlength="25" value="<%=obj.getReturnPlace()%>">
			&nbsp;需否呈報本府核定：
			<select class="field_QRO" type="select" name="submitCityGov">
				<%=util.View.getYNOption(obj.getSubmitCityGov())%>
			</select>
		<br>
			報廢或失竊原因：<input class="field" type="text" name="cause2" size="22" maxlength="10" value="<%=obj.getCause2()%>">
			&nbsp;　　預估殘值總價：<input class="field" type="text" name="scrapValue2" size="15" maxlength="15" value="<%=obj.getScrapValue2()%>">
		<br>
			擬予處理意見：<input class="field" type="text" name="dealSuggestion" size="40" maxlength="20" value="<%=obj.getDealSuggestion()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">廠牌型式：</td>
		<td class="td_form_white" colspan="3">		
			品名：[<input class="field_RO" type="text" name="articleName" size="20" value="<%=obj.getArticleName()%>">]　
			廠牌：[<input class="field_RO" type="text" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate()%>" >]
		<br>
			型式：[<input class="field_RO" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">]　
			牌照號碼規格：[<input class="field_RO" type="text" name="licensePlate" size="30" maxlength="30" value="<%=obj.getLicensePlate()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]
			&nbsp;&nbsp;　　　　　購置日期：[<input class="field_QRO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;&nbsp;　　　　　取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			&nbsp;&nbsp;&nbsp;　　　　　基金財產：
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
			　&nbsp;&nbsp;&nbsp;　　　　會計科目：[<input class="field_RO" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="oldBookAmount" size="7" maxlength="7" value="<%=obj.getOldBookAmount()%>">] 
			&nbsp;&nbsp;單價：[<input class="field_RO type="text" name="oldBookUnit" size="13" maxlength="13" value="<%=obj.getOldBookUnit()%>">] 
			　總價：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">] 
		</td>
	</tr>	
	<tr>
		<td class="td_form">減少帳面資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>數量：<input class="field" type="text" name="adjustBookAmount" size="7" maxlength="7" value="<%=obj.getAdjustBookAmount()%>" onChange="getNewBookData();" >
			 <font color="red">*</font>總價：<input class="field" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>" onChange="getNewBookData();">
			&nbsp;&nbsp;&nbsp;帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
		</td>
	</tr>	
	<tr>
		<td class="td_form">新帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="newBookAmount" size="7" maxlength="7" value="<%=obj.getNewBookAmount()%>" >]
			&nbsp;&nbsp;總價：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">移動資料：</td>
		<td class="td_form_white" colspan="3">
			日期：[<input class="field_RO" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>">] 
		<br>
			保管單位：[<input class="field_RO" type="text" name="keepUnitName" size="30" maxlength="30" value="<%=obj.getKeepUnitName()%>">] 
					<input class="field_RO" type="hidden" name="keepUnit" value="<%=obj.getKeepUnit()%>">
			　保管人：[<input class="field_RO" type="text" name="keeperName" size="10" maxlength="10" value="<%=obj.getKeeperName()%>">] 
					<input class="field_RO" type="hidden" name="keeper" value="<%=obj.getKeeper()%>">
		<br>
			使用單位：[<input class="field_RO" type="text" name="useUnitName" size="30" maxlength="30" value="<%=obj.getUseUnitName()%>">]		
					<input class="field_RO" type="hidden" name="useUnit" value="<%=obj.getUseUnit()%>">
			　使用人：[<input class="field_RO" type="text" name="userName" size="10" maxlength="10" value="<%=obj.getUserName()%>">]
					<input class="field_RO" type="hidden" name="userNo" value="<%=obj.getUserNo()%>">
		<br>
			存置地點：[<input class="field_RO" type="text" name="place" size="25" maxlength="25" value="<%=obj.getPlace()%>">] 
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
			預留殘值：[<input class="field_RO" type="text" name="scrapValue" size="15" maxlength = "15" value="<%=obj.getScrapValue()%>">]
			　　應攤提折舊總額：[<input class="field_RO" type="text" name="deprAmount" size="15" maxlength="15" value="<%=obj.getDeprAmount()%>">]
		<br>
			攤提年限：[<input class="field_RO" type="text" name="apportionYear" size="3" maxlength="3" value="<%=obj.getApportionYear()%>">]
			　　　　　　　　　月提折舊金額：[<input class="field_RO" type="text" name="monthDepr" size="15" maxlength="15" value="<%=obj.getMonthDepr()%>">]
		<br>
			使用年限截止年月：	[<input class="field_RO" type="text" name="useEndYM" size="6" maxlength="5" value="<%=obj.getUseEndYM()%>">]
			　  &nbsp;攤提年限截止年月：[<input class="field_RO" type="text" name="apportionEndYM" size="5" maxlength="5" value="<%=obj.getApportionEndYM()%>">]
		<br>
			累計折舊調整年月：	[<input class="field_RO" type="text" name="accumDeprYM" size="6" maxlength="5" value="<%=obj.getAccumDeprYM()%>">]
			　&nbsp;&nbsp;累計折舊調整金額：[<input class="field_RO" type="text" name="accumDepr" size="15" maxlength="15" value="<%=obj.getAccumDepr()%>">]
		<br>
			累計折舊：[<input class="field_RO" type="text" name="accumDepr1" size="15" maxlength="15" value="<%=obj.getAccumDepr1()%>">]
			　　　　　殘餘價值：[<input class="field_RO" type="text" name="scrapValue1" size="15" maxlength="15" value="<%=obj.getScrapValue1()%>">]
		</td>
	</tr>
	<tr>		
		<td class="td_form">廢品處理資料：</td>
		<td class="td_form_white" colspan="3">
			案號：[<input class="field_RO" type="text" name="dealCaseNo" size="10" value="<%=obj.getDealCaseNo()%>">]
			&nbsp;&nbsp;　　　　　　　　　　　日期：[<input class="field_RO" type="text" name="dealDate" size="7" value="<%=obj.getDealDate()%>">]
		<br>
			方式：
			<select class="field_RO" type="select" name="reducedeal">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", obj.getReduceDeal())%>
			</select>
			&nbsp;　　　　　　　　　　　變賣金額：[<input class="field_RO" type="text" name="realizeValue" size="15" value="<%=obj.getRealizeValue()%>">]
		<br>
			轉撥單位：
			<input class="field_RO" type="hidden" name="shiftOrg" size="10" maxlength="10" value="<%=obj.getShiftOrg()%>" >
			[<input class="field_RO" type="text" name="shiftOrgName" size="20" maxlength="50" value="<%=obj.getShiftOrgName()%>">]
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
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="現有資料明細新增" onClick="gountmp016f();">&nbsp;|
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
		<!--<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>-->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">保管人</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">減少數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">減少價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">報府核定</a></th>
	</thead> 
	<tbody id="listTBODY">	
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,false,true,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,false,false,true,true,true,true,true,false,true,true,false,false,false,true,false};
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
			setFormItem("ownership,verify,cause1,transferDate,btn_transferDate,closing,submitCityGov","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "R");
			form1.newEnterOrg.value = "";
			break;
		case "insertError":
			setFormItem("ownership,verify,cause1,transferDate,btn_transferDate,closing,submitCityGov","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "R");
			form1.newEnterOrg.value = "";
			break;
	
		//更新時要做的動作
		case "update":
			if (form1.cause.value=="99"){
				form1.cause1.readOnly = false;
				form1.cause1.style.backgroundColor=editbg;
				//form1.newEnterOrgName.value = "";
				//form1.newEnterOrg.value = "";
				//form1.transferDate.value = "";
				form1.transferDate.style.backgroundColor=readbg;
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
			}else if(form1.cause.value=="01" || form1.cause.value=="07" || form1.cause.value=="08"){
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
				setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "R");
				form1.transferDate.style.backgroundColor=editbg;
				form1.cause1.value="";
				form1.cause1.style.backgroundColor=readbg;
				form1.cause1.readOnly = true;
			}else{
				form1.cause1.value="";
				form1.cause1.style.backgroundColor=readbg;
				form1.cause1.readOnly = true;
				//form1.newEnterOrgName.value = "";
				//form1.newEnterOrg.value = "";
				//form1.transferDate.value = "";
				form1.transferDate.style.backgroundColor=readbg;
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
				setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "O");
			}
			setFormItem("ownership,verify,closing,submitCityGov","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "updateError":
			if (form1.cause.value=="99"){
				form1.cause1.readOnly = false;
				form1.cause1.style.backgroundColor=editbg;
				form1.newEnterOrgName.value = "";
				form1.newEnterOrg.value = "";
				form1.transferDate.value = "";
				form1.transferDate.style.backgroundColor=readbg;
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
			}else if(form1.cause.value=="01" || form1.cause.value=="07" || form1.cause.value=="08"){
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
				setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "R");
				form1.transferDate.style.backgroundColor=editbg;
				form1.cause1.value="";
				form1.cause1.style.backgroundColor=readbg;
				form1.cause1.readOnly = true;
			}else{
				form1.cause1.value="";
				form1.cause1.style.backgroundColor=readbg;
				form1.cause1.readOnly = true;
				form1.newEnterOrgName.value = "";
				form1.newEnterOrg.value = "";
				form1.transferDate.value = "";
				form1.transferDate.style.backgroundColor=readbg;
				setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
				setFormItem("returnPlace,cause2,scrapValue2,dealSuggestion", "O");
			}
			setFormItem("ownership,verify,closing,submitCityGov","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
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
