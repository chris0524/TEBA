 
<!--
程式目的：建物減少作業－減損單明細
程式代號：untbu016f
程式日期：0940929
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU016F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.bu.UNTBU016F)obj.queryOne();	
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
String tabs = uch._createTabData(uch._BU_REDUCE, 3);
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
<script language="javascript" src="../../js/getUNTBUBuilding.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
//當增加原因選「其他」時，開放其他說明欄位
function changecause(){
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

	if((form1.cause.value!="01" && form1.cause.value!="08" && form1.cause.value!="90")){
		if (( form1.bookValue.value >= 2000000 ) || ( getToday() < form1.permitReduceDate.value ) ) {
			form1.submitCityGov.value = "Y";		
		}
	}else{
		form1.submitCityGov.value = "N";
	}
	
	//if(form1.cause.value=="02"||form1.cause.value=="03"){
//	    alert("「減損原因」為「報廢、災害毀損」時，「法令依據」為必填。");
	//}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untbu015f.jsp";
		} else {
			form1.action = "untbu016f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
	  	alertStr += checkLen(form1.notes, "備註", 250);
/**
		if ((form1.signNo1.value!="")||(form1.signNo2.value!="")||(form1.signNo3.value!="")||(form1.signNo4.value!="")||(form1.signNo5.value!="")){
			//alertStr += "建物標示代碼若要填寫，請填寫完整, 否則請不要填寫!"
			alertStr += checkEmpty(form1.signNo1,"建物標示代碼－縣市");			
			alertStr += checkEmpty(form1.signNo2,"建物標示代碼－鄉鎮市區");
			alertStr += checkEmpty(form1.signNo3,"建物標示代碼－地段");
			alertStr += checkEmpty(form1.signNo4,"建物標示代碼－地號(母號)");
			alertStr += checkEmpty(form1.signNo5,"建物標示代碼－地號(子號)");		
		}	
**/			
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		
		alertStr += checkEmpty(form1.cause,"減損原因");
		
		if (form1.cause.value=="99"){
			alertStr += checkEmpty(form1.cause1,"減損原因-其他說明");
		}
		
		if ((form1.cause.value=="01")||(form1.cause.value=="07")){
			alertStr += checkEmpty(form1.newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.transferDate,"交接日期");
		}
		
		alertStr += checkDate(form1.transferDate,"交接日期");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
	
	if(form1.cause.value=="02"||form1.cause.value=="03"){
	    alertStr+="「減損原因」為「報廢、災害毀損」時，「法令依據」為必填。";
	}
	
}
function queryOne(enterOrg,ownership,propertyNo,serialNo){
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
		if (surl=="untbu015f.jsp"){	
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


function gountbu017f(){
	var prop="";
	//var windowTop=(document.body.clientHeight-80)/2+25;
	//var windowLeft=(document.body.clientWidth-800)/2+250;
	
	var iwidth=screen.availWidth;
	var iheight=screen.availHeight;
	var windowTop=0;
	var windowLeft=0;
	
	//prop=prop+"width=775,height=475,";
	//prop=prop+"top="+windowTop+",";
	//prop=prop+"left="+windowLeft+",";
	//prop=prop+"scrollbars=no";
	
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
	returnWindow=window.open("untbu017f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_reduceDate="+reduceDate+"&q_verify="+verify,"aha",prop);	
}

//取得已使用年數、累計折舊、殘餘價值、需否呈報市府核定等資料
//untbu016.jsp 和 untbu017f.java 都有用到
function getdata(){
	//已使用年數 = (系統日期 - 建築日期) 之年數
	form1.useYear.value = Math.floor(getDateDiff("m", form1.buildDate , form1.getToday)/12) ;
	
	//已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
	form1.useMonth.value = (getDateDiff("m", form1.buildDate , form1.getToday)) % 12 ;
	
	//累計折舊、殘餘價值
	var deprMethod = form1.deprMethod ;
	if (deprMethod == "1") {
		form1.accumDepr1.value = "0";
		form1.scrapValue1.value = "0";
	} else if (deprMethod == "5")  {
		form1.accumDepr1.value = form1.bookValue.value;
		form1.scrapValue1.value = "0";
	} else if (deprMethod >= "2" && deprMethod <= "4") {
		var accumDepr1;
		if ( form1.getToday.value.substring(0,5) > form1.apportionEndYM.value ) {
			accumDepr1 = form1.deprAmount.value  ;
		} else {
			accumDepr1 = form1.accumDepr.value + ( (getDateDiff("m",form1.getToday,form1.accumDeprYm.value) - 1) * form1.monthDepr.value ) ;
		}
		
		form1.accumDepr1.value = accumDepr1;
		form1.scrapValue1.value = form1.bookValue.value - accumDepr1 ;
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
	var arrField = new Array("update","delete");
	if (form1.verify.value=="Y") {
		setFormItem("insert,delete", "R");
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
var checkSignNo1 = form1.signNo1.value;
var checkSignNo2 = form1.signNo2.value;
var checkSignNo3 = form1.signNo3.value;
var checkSignNo4 = form1.signNo4.value;
var checkSignNo5 = form1.signNo5.value;

	if(checkPropertyNo.length>0 && checkSerialNo.length>0 && checkSignNo1.length>0 && checkSignNo2.length>0 && checkSignNo3.length>0 && checkSignNo4.length>0 && checkSignNo5.length>0){
	
	}else if(checkPropertyNo.length>0 || checkSerialNo.length>0){
		setFormItem("signNo1,signNo2,signNo3,signNo4,signNo5","R");
	}else if(checkSignNo1.length>0 || checkSignNo2.length>0 || checkSignNo3.length>0 || checkSignNo4.length>0 || checkSignNo5.length>0){
		setFormItem("propertyNo,serialNo","R");
	}

	if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.propertyNo.value="";
		form1.propertyNoName.value="";
		form1.serialNo.value="";
	}
	if(form1.check.value=="" && checkSignNo1.length!=0 && checkSignNo2.length!=0 && checkSignNo3.length!=0 && checkSignNo4.length!=0 && checkSignNo5.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.signNo4.value="";
		form1.signNo5.value="";
	}
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function checklawBasis(){
    if(form1.cause.value=="02" || form1.cause.value=="03"){
        if(form1.lawBasis.value=="" || form1.lawBasis.value.length<=0){
            alert("「減損原因」為「報廢、災害毀損」時，「法令依據」為必填。");
        }
    }
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input class="field_QRO" type="HIDDEN" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=obj.getQueryTab1()%>>
			&nbsp;<font color="green">查詢減損單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="detail" <%=obj.getQueryTab2()%>>
			&nbsp;<font color="green">查詢減損單明細資料</font>
		</td>
	</tr>				
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起 <input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖 <input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=obj.getQ_caseNoE()%>">			
		</td>
		<td class="queryTDLable">減損日期：</td>
		<td class="queryTDInput">
			起 <%=util.View.getPopCalndar("field_Q","q_reduceDateS",obj.getQ_reduceDateS())%>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_reduceDateE",obj.getQ_reduceDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"2")%>&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"2")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >財產分號：</td>
		<td class="queryTDInput" >
			起 <input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖 <input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">核准機關：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_approveOrg">
			<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ", "")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
			</select>
			　建號：
			<input class="field_Q" type="text" name="q_signNo4" size="5" maxlength="5" value="<%=obj.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="3" maxlength="3" value="<%=obj.getQ_signNo5()%>">		
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(obj.getQ_verify())%>
			</select>					
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>				
	</tr>		
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
				<%=util.View.getYNOption(obj.getQ_taxCredit())%>
			</select>		
		</td>					
	</tr>
	<tr>
		<td class="queryTDLable">減損原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause">
			<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getQ_cause())%>
			</select>
		</td>		
		<td class="queryTDLable">接管機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_newEnterOrg",obj.getQ_newEnterOrg(),obj.getQ_newEnterOrgName(),"Y")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起 <%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=obj.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">減損單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>"> 字
			起 <input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖 <input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);"> 號
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
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="14%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
		　　&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		　　&nbsp;&nbsp;電腦單號：
			[<input type="text" name="caseNo" class="field_QRO"  size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td width="120" class="td_form"><font color="red">*</font> 財產編號：</td>
		<td colspan="3" class="td_form_white">
	  		<input class="field_P" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','2');getUNTBUBuilding('PN'); getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();"> 
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
	   		分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getUNTBUBuilding('PN'); getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();">
		<br>
		別名：
			[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>	 
	</tr>
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="material" size="20" value="<%=obj.getMaterial()%>">]
			&nbsp;&nbsp;&nbsp;　　　單位：[<input class="field_RO" type="text" name="propertyUnit" size="7" maxlength="7" value="<%=obj.getPropertyUnit()%>">]
		<br>
			使用年限： [<input class="field_RO" type="text" name="limitYear" size="7" maxlength="7" value="<%=obj.getLimitYear()%>">]
			　調整後年限(月)：[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
			　可報廢日期：[<input type="text" name="permitReduceDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getPermitReduceDate()%>">]
		<br>
			已使用年月：
			[<input class="field_RO" type="text" name="useYear" size="10" value="<%=obj.getUseYear()%>">]年
			[<input class="field_RO" type="text" name="useMonth" size="10" value="<%=obj.getUseMonth()%>">]個月
		</td>
	</tr>
	<tr>
	  <td width="120" class="td_form">建物標示：</td>
	  <td colspan="3" class="td_form_white">
	  	<select  type="select" class="field_P" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getUNTBUBuilding('SN'); getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();">
        	<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
      	</select>
        <select  type="select" class="field_P" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getUNTBUBuilding('SN'); getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();">
          <script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
        </select>
        <select  type="select" class="field_P" name="signNo3">
          <script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
        </select>
		　建號：
		<input class="field_P" type="text" name="signNo4" size="5" maxlength="5" value="<%=obj.getSignNo4()%>" onChange="getUNTBUBuilding('SN');getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();">
		&nbsp;-
		<input class="field_P" type="text" name="signNo5" size="3" maxlength="3" value="<%=obj.getSignNo5()%>" onChange="getUNTBUBuilding('SN');getdata();form1.reduceDate.value='<%=obj.getReduceDate()%>';checkValue();">
	  </td>
	</tr>
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan = "3">
			日期：[<input type="text" name="reduceDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;　入帳：
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
				<select class="field" type="select" name="cause" onchange="changecause();checklawBasis()">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getCause())%>
				</select>
				&nbsp;其他說明：<input class="field" type="text" name="cause1" size="30" maxlength="20" value="<%=obj.getCause1()%>">
		<br>
		          法令依據：<input class="field" type="text" name="lawBasis" size="60" maxlength="60" value="<%=obj.getLawBasis()%>" onblur="checklawBasis()">
		<br>
			報廢(損)後處理情形：<input class="field" type="text" name="reducedeal" size="50" maxlength="60" value="<%=obj.getReducedeal()%>">
		<br>
			<font color="red">*</font>接管機關：<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>
			&nbsp;&nbsp;<font color="red">*</font>交接日期：<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		</td>
	</tr>
	<tr>
	  <td class="td_form">門牌：</td>
	  <td class="td_form_white" colspan="3">
	  	<select class="field_RO" type="select" name="doorPlate1" onChange="changeAddr1('doorPlate1','doorPlate2','doorPlate3','');">
        	<%=util.View.getOption("select addrID, addrName from SYSCA_Addr where addrKind = '1' order by seqno",obj.getDoorPlate1())%>
	    </select>
	    <select class="field_RO" type="select" name="doorPlate2" onChange="changeAddr2('doorPlate1','doorPlate2','doorPlate3','');">
	    	<script>changeAddr1('doorPlate1','doorPlate2','doorPlate3','<%=obj.getDoorPlate2()%>');</script>
	    </select>
	    <select class="field_RO" type="select" name="doorPlate3">
	    	<script>changeAddr2('doorPlate1','doorPlate2','doorPlate3','<%=obj.getDoorPlate3()%>');</script>
	    </select>
	    　[<input name="doorPlate4" type="text" class="field_RO" value="<%=obj.getDoorPlate4()%>" size="30">]
	  </td>
	</tr>
	<tr>
		<td class="td_form">建築日期：</td>
		<td class="td_form_white" colspan="3">
			[<input type="text" name="buildDate" class="field_RO"  size="6" maxlength="7" value="<%=obj.getBuildDate()%>">]
			&nbsp;　　　　　　財產取得日期：[<input type="text" name="sourceDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			　　　　　　　　基金財產：
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
			　　　　　　　抵繳遺產稅：	
			<select class="field_RO" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="td_form">權狀字號：</td>
		<td class="td_form_white" colspan="3">
		[<input class="field_RO" type="text" name="proofDoc" size="18" maxlength="20" value="<%=obj.getProofDoc()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">面積：</td>
		<td class="td_form_white" colspan="3">		
			主面積(㎡)：[<input class="field_RO" type="text" name="CArea" size="9" maxlength="9" value="<%=obj.getCArea()%>">]
			&nbsp;附屬面積(㎡)：[<input class="field_RO" type="text" name="SArea" size="9" maxlength="9" value="<%=obj.getSArea()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">]
		<br>
			權利分子：[<input class="field_RO" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>">]
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
			帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
			　　　　　　　　帳面價值：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		<br>
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
			殘餘價值：
				[<input class="field_RO" type="text" name="scrapValue1" size="10" value="<%=obj.getScrapValue1()%>">]
			&nbsp;&nbsp;&nbsp;　　　　　　
			應攤提折舊總額：[<input class="field_RO" type="text" name="deprAmount" size="10" value="<%=obj.getDeprAmount()%>">]
		<br>
			攤提年限截止年月：[<input class="field_RO" type="text" name="apportionEndYM" size="10" value="<%=obj.getApportionEndYM()%>">]
			&nbsp;　　　　月提折舊金額：[<input class="field_RO" type="text" name="monthDepr" size="10" value="<%=obj.getMonthDepr()%>">]
		<br>
			累計折舊調整年月：[<input class="field_RO" type="text" name="accumDeprYM" size="10" value="<%=obj.getAccumDeprYM()%>">]
			&nbsp;　　累計折舊調整金額：[<input class="field_RO" type="text" name="accumDepr" size="10" value="<%=obj.getAccumDepr()%>">]
		<br>
			累計折舊：[<input class="field_RO" type="text" name="accumDepr1" size="10" value="<%=obj.getAccumdepr1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="25" rows="4" ><%=obj.getNotes()%></textarea>
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
	<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountbu017f();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">建物標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產性質</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">權利範圍面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">帳面金額</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">報府核定</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">帳務摘要</a></th>
	</thead> 
	<tbody id="listTBODY">	
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,true,false,false,true,true,true,true,true,false,true,true};
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
			setFormItem("signNo1,signNo2,signNo3,signNo4,signNo5,batchInsertBut","R");
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
			setFormItem("signNo1,signNo2,signNo3,signNo4,signNo5,batchInsertBut","R");
			if(form1.verify.value=="Y"){
				setFormItem("propertyNo,btn_propertyNo,propertyName,serialNo,cause,cause1,newEnterOrg,btn_newEnterOrg,newEnterOrgName,transferDate,btn_transferDate,bookNotes,notes","R");
			}else{
				setFormItem("cause,cause1,newEnterOrg,btn_newEnterOrg,newEnterOrgName,transferDate,btn_transferDate,bookNotes,notes","O");
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
