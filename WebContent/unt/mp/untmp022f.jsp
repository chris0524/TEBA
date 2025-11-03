<!--
程式目的：動產廢品處理作業－處理單明細
程式代號：untmp022f
程式日期：0941205
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP022F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP022F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if ("".equals(obj.getSerialNo())) {
			obj.setEnterOrg(((String[])objList.get(0))[10]);
			obj.setOwnership(((String[])objList.get(0))[11]);
			obj.setCaseNo(((String[])objList.get(0))[13]);
			obj.setPropertyNo(((String[])objList.get(0))[14]);
			obj.setSerialNo(((String[])objList.get(0))[15]);
			obj.setDifferenceKind(((String[])objList.get(0))[16]);
		}
		
		obj = (unt.mp.UNTMP022F)obj.queryOne();		
	}
}

String queryType1 = "", queryType2 = "";
if ("dealDetail".equals(obj.getQuerySelect())) queryType2 = "checked=\"CHECKED\"";	
else if("dealProof".equals(obj.getQuerySelect())) queryType1 = "checked=\"CHECKED\"";

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._MP_DEAL, 2);
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
<script language="javascript" src="getUntmpReduceDetail.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array();
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untmp021f.jsp";
		} else {
			form1.action = "untmp022f.jsp";
		}
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkEmpty(form1.caseNo,"減損單電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.ownership,"權屬");
		
		alertStr += checkLen(form1.notes, "備註", 250);
		if(parse(form1.realizeValue1.value).length>0){
			alertStr += checkInt(form1.realizeValue1,"變賣金額");
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untmp021f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.caseNo,"檢損單號");
				alertStr += checkEmpty(form1.propertyNo,"財產編號");
				alertStr += checkEmpty(form1.serialNo,"財產分號");			
			}
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo1,"動產處理單-電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}


function init(){
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete,clear,confirm,untmp023f", "R");
	}
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("untmp023f","R");
	}else{
		setFormItem("untmp023f","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,clear,confirm,untmp023f", "R");
	}	
	setDisplayItem("spanInsert","H");

	var dcc3 = new DataCouplingCtrlPlus(form1.organID, form1.keepUnitQuickly, form1.keepUnit, form1.useUnitQuickly, form1.useUnit, true, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.organID, form1.keeperQuickly, form1.keeper, form1.userNoQuickly, form1.userNo, true, false);
}

function checkValue(){
	var checkCaseNo = form1.caseNo.value;
	var checkPropertyNo = form1.propertyNo.value;
	var checkSerialNo = form1.serialNo.value;
	//if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
	//	alert("資料不存在，請重新輸入!!");
	//	form1.propertyNo.value="";
	//	form1.propertyNoName.value="";
	//	form1.serialNo.value="";
	//	form1.caseNo.value="";
	//}
}

function propertyNoFounction(){
	getUntmpReduceDetail();
	checkValue();
}

function clickUntmp023f(){
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
	var enterOrg="<%=obj.getEnterOrg()%>";
	var ownership="<%=obj.getOwnership()%>";
	var caseNo1="<%=obj.getCaseNo1()%>";
	var dealDate="<%=obj.getDealDate()%>";
	var verify="<%=obj.getVerify()%>";
	var differenceKind="<%=obj.getDifferenceKind()%>";
	beforeSubmit();
	returnWindow=window.open("untmp023f.jsp?enterOrg="+enterOrg+"&ownership="+ownership+"&dealDate="+dealDate+"&caseNo1="+caseNo1+"&verify="+verify+"&differenceKind="+differenceKind,"aha",prop);
}

function alteredEnterOrg(){
	//changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
function changeDifferenceKind(){
	$("input[name='propertyNo']").val('');
	$("input[name='serialNo']").val('');
	
	if($("select[name='differenceKind']").val() == ''){
		setFormItem("propertyNO,btn_propertyNo,serialNo","R");		
	}else{
		setFormItem("propertyNO,btn_propertyNo,serialNo","O");
	}
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
<input type="hidden" name="check" value="">
<!--Query區============================================================-->
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDInput" colspan="4">
	  <input name="querySelect" type="radio" class="field_Q" value="dealProof" <%=queryType1%>>
      &nbsp;<font color="green">查詢處理單資料</font>&nbsp;&nbsp;&nbsp;	
	  <input name="querySelect" type="radio" class="field_Q" value="dealDetail" <%=queryType2%>>
      &nbsp;<font color="green">查詢處理單明細資料</font>
	  </td>
	</tr>
	<tr>
      <td class="queryTDLable" >入帳機關：</td>
      <td class="queryTDInput"colspan="3" ><%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N")%> </td>
	</tr>
	<tr>
	  <td class="queryTDLable">廢品處理日期：</td>
	  <td class="queryTDInput" colspan="3">
			  起<%=util.View.getPopCalndar("field_Q","q_dealDateS",obj.getQ_dealDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_dealDateE",obj.getQ_dealDateE())%>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"3,4,5")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>		
		<td class="queryTDLable">審核：</td>
		<td class="queryTDInput">
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
			<%=util.View._getSelectHTML_withEnterOrg("field", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
		</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
	</tr>	
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput"colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%> 
		</td>		
		<td class="queryTDLable"style="display:none;">處理案名：</td>
		<td class="queryTDInput"style="display:none;">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=obj.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">處理單編號：</td>
		<td class="queryTDInput" colspan="3">	
		<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=obj.getQ_proofYear()%>">
			年	
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">處理方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reduceDeal">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", obj.getQ_reduceDeal())%>
			</select>
		</td>
		<td class="queryTDLable">轉撥單位：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_shiftOrg",obj.getQ_shiftOrg(),obj.getQ_shiftOrgName(),"Y")%>
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
		<td class="td_form" width="17%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">
			]
			<font color="red">*</font>權屬：
				<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
					
			<input class="field_QRO" type="hidden" name="caseNo1" size="10" maxlength="10" value="<%=obj.getCaseNo1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產區分別：</td>
		<td colspan="3" class="td_form_white">
			<select class="field_QRO" type="select" name="differenceKind" onchange="changeDifferenceKind();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,obj.getDifferenceKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">處理日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="dealDate" size="15" maxlength="7" value="<%=obj.getDealDate()%>">]
			&nbsp;&nbsp;&nbsp;　　　　　　　　審核：
				<select class="field_QRO" type="select" name="verify">
  					<%=util.View.getYNOption(obj.getVerify())%>
				</select>
		<br>
			變賣金額：<input class="field" type="text" name="realizeValue1" size="15" maxlength="15" value="<%=obj.getRealizeValue1()%>">
			&nbsp;&nbsp;　　繳存地點：<input class="field" type="text" name="returnPlace" size="25" maxlength="25" value="<%=obj.getReturnPlace()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">財產資料：</td>
		<td class="td_form_white" colspan="3">
			<!--  減損單電腦單號：--><input class="field_P" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>" onChange="getUntmpReduceDetail();checkValue();" >
			<font color="red">*</font>財產編號：
			<input class="field_P" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','3,4,5&isLookup=Y&js=propertyNoFounction();')">
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			<font color="red">*</font>
			分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getUntmpReduceDetail();checkValue();">
			<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">	] 
			財產批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">] 
			<br>
			原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;&nbsp;&nbsp;
			原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
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
		</td>
	</tr>
	<tr>
		<td class="td_form">已使用年月：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="useYear" size="10" value="<%=obj.getUseYear()%>">]年
			[<input class="field_RO" type="text" name="useMonth" size="10" value="<%=obj.getUseMonth()%>">]個月			 
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
			[<input class="field_RO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">] 
			購置日期：[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">] 
			取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">] 
			減損日期：[<input class="field_RO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>	
			　基金財產：
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", obj.getQ_fundType(),"FUD", obj.getQ_enterOrg()) %>
        </td>
	</tr>
	<tr>
		<td class="td_form">珍貴財產：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			　會計科目：
			[<input class="field_RO" type="text" name="accountingTitle" size="20" value="<%=obj.getAccountingTitle()%>">]
	</tr>
	<tr>
		<td class="td_form">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="oldBookAmount" size="7" maxlength="7" value="<%=obj.getOldBookAmount()%>">] 
			　單價：[<input class="field_RO type="text" name="oldBookUnit" size="13" maxlength="13" value="<%=obj.getOldBookUnit()%>">] 
			　總價：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form">減少帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="adjustBookAmount" size="7" maxlength="7" value="<%=obj.getAdjustBookAmount()%>">] 
			　總價：[<input class="field_RO" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form">新帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="newBookAmount" size="7" maxlength="7" value="<%=obj.getNewBookAmount()%>">] 
			　總價：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form">移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "keepUnit", "keepUnitQuickly", obj.getKeepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
		    <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'keeper')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "useUnit", "useUnitQuickly", obj.getUseUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "userNo", "userNoQuickly", obj.getUserNo()) %>
		    <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'userNo')" value="..." title="人員輔助視窗">
		    &nbsp;&nbsp;&nbsp;
			使用註記：
			<input type="text" class="field" name="userNote" value="<%=obj.getUserNote() %>" size="20">
			<br>
			移動日期：<input class="field_Q" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>">
	        <br>
	                    存置地點
			<input class="field_Q" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>" onchange="queryPlaceName('enterOrg','place1');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'place1','placeName')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="placeName" size="20" maxlength="20" value="<%=obj.getPlaceName()%>">]
			<br>		
			存置地點說明
			<input class="field_Q" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">		
		</td>
	</tr>
	<tr>
		<td class="td_form">折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法
			<select name="deprMethod" class="field_RO" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" name="buildFeeCB" size="10" maxlength="10" value="<%=obj.getBuildFeeCB()%>" <%=("Y".equals(obj.getBuildFeeCB())?"checked":"")%> >
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" name="cb_deprUnitCB" size="10" maxlength="10"   <%=("Y".equals(obj.getDeprUnitCB())?"checked":"")%>>
       		<input class="field_RO" type="hidden" name="deprUnitCB" size="10" maxlength="10" value="<%=obj.getDeprUnitCB()%>" >	
			部門別依比例分攤
			<br>
			園區別
			<select class="field_RO" type="select" name="deprPark">
			<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field_RO" type="select" name="deprUnit">
			<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field_RO" type="select" name="deprAccounts">
			<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprAccounts())%>
			</select>
			<br>
			殘值
			[<input class="field_RO" type="text" id="scrapValue" name="scrapValue" size="10" maxlength="10" value="<%=obj.getScrapValue()%>">]
			&nbsp;&nbsp;
			應攤提折舊總額
			[<input class="field_RO" type="text" name="deprAmount" size="20" maxlength="15" value="<%=obj.getDeprAmount() %>" onChange="changeArea();">]
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="accumDepr" size="20" maxlength="15" value="<%=obj.getAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="apportionMonth" size="10" maxlength="3" value="<%=obj.getApportionMonth()%>">]
			月提折舊金額
			[<input class="field_RO" type="text" name="monthDepr" size="10" value="<%=obj.getMonthDepr()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white"><textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea></td>
	  	<td class="td_form"style="display:none;">異動人員/日期：</td>
	  	<td class="td_form_white"style="display:none;">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</tr>	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="filestoreLocation" value="<%=application.getInitParameter("filestoreLocation")%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanUntmp023f">
	<input class="toolbar_default" type="button" followPK="false" name="untmp023f" value="現有資料明細新增" onClick="clickUntmp023f();">&nbsp;|
	</span>
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">處理日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">減少數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">減少總價</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,false,true,true,false,true,true,true,true};
	boolean displayArray[] = {true,true,false,false,true,true,true,true,true,true,false,false,false,false,false,false,false};
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
			setFormItem("verify","R");
			setFormItem("untmp023f","R");
			break;
		case "insertError":
			setFormItem("ownership,verify","R");
			setFormItem("untmp023f","R");
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify","R");
			setFormItem("untmp023f","R");
			break;
		case "updateError":
			setFormItem("ownership,verify","R");
			setFormItem("untmp023f","R");
			break;
	
		//取消時要執行的動作
		case "clear":
			setFormItem("untmp023f","O");
			break;
		case "clearError":
			setFormItem("untmp023f","O");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("untmp023f","O");
			break;
		case "confirmError":
			setFormItem("untmp023f","O");
			break;
	}
}
</script>
</body>
</html>



