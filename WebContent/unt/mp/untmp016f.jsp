<!--
程式目的：動產減少作業－現有資料明細新增明細資料
程式代號：untmp016f
程式日期：0941019
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP016F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP016F)obj.queryOne();	
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
<title>動產減少作業現有資料明細新增輔助視窗</title>
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
<script language="javascript" src="../../js/getUNTMPMovable.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

//當增加原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.q_cause.value=="99"){
		form1.q_cause1.readOnly = false;
	}else{
		form1.q_cause1.value="";
		form1.q_cause1.style.backgroundColor="";
		form1.q_cause1.readOnly = true;
	}
	if ((form1.q_cause.value=="01")||(form1.q_cause.value=="07")||(form1.q_cause.value=="08")){
		form1.returnPlace.value="";
		form1.cause2.value="";
		form1.dealSuggestion.value="";
		form1.scrapValue2.value="";
		setFormItem("returnPlace,cause2,dealSuggestion,scrapValue2","R");
		setFormItem("q_newEnterOrg,btn_q_newEnterOrg,q_transferDate,btn_q_transferDate","O");

	}else{
		form1.q_newEnterOrg.value="";
		form1.q_newEnterOrgName.value="";
		form1.q_transferDate.value="";
		setFormItem("q_newEnterOrg,btn_q_newEnterOrg,q_transferDate,btn_q_transferDate","R");
		setFormItem("returnPlace,cause2,dealSuggestion,scrapValue2","O");
		form1.q_transferDate.style.backgroundColor="";
	}
	
	if(form1.q_cause.value=="02"){
	    form1.cause2.value="逾最低年限，不堪使用。";
	}else{
	    form1.cause2.value="";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkInsertSelect();
		alertStr += checkEmpty(form1.q_cause,"減損原因");
		if (form1.q_cause.value=="99"){
			alertStr += checkEmpty(form1.q_cause1,"減損原因-其他說明");
		}
		
		if ((form1.q_cause.value=="01")||(form1.q_cause.value=="07")||(form1.q_cause.value=="08")){
			alertStr += checkEmpty(form1.q_newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.q_transferDate,"交接日期");
		}
		alertStr += checkDate(form1.q_transferDate,"交接日期");
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

function initC(){
	document.all.item("spaninsert").style.display="none";
	document.all.item("spanupdate").style.display="none";
	document.all.item("spandelete").style.display="none";
	document.all.item("spanclear").style.display="none";
	document.all.item("spanconfirm").style.display="none";
	document.all.item("spannextInsert").style.display="none";
	setFormItem("queryAll","O");
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
		opener.form1.submit();
		if (isObj(window.aha)) window.aha.close();
	}
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

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');initC();showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" value="<%=obj.getQ_enterOrg()%>">
<input class="field_QRO" type="hidden" name="q_dataState" size="1" value="1">
<input class="field_QRO" type="hidden" name="q_ownership" size="1" value="<%=obj.getQ_ownership()%>">			
<input class="field_QRO" type="hidden" name="q_caseNo" size="10" value="<%=obj.getQ_caseNo()%>">
<input class="field_QRO" type="hidden" name="q_reduceDate" size="10" value="<%=obj.getQ_reduceDate()%>">			
<input class="field_QRO" type="hidden" name="q_verify" size="10" value="<%=obj.getQ_verify()%>">
<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
<input class="field_QRO" type="hidden" name="caseNo" size="24" maxlength="24" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="hidden" name="reduceDate" size="10" maxlength="10" value="<%=obj.getReduceDate()%>"> 
<input class="field_QRO" type="hidden" name="verify" size="10" maxlength="10" value="<%=obj.getVerify()%>"> 
<!--批次設定區============================================================-->
<div id="gountmp016f" style="position:absolute;z-index: 25;left:0;top :0;width:700px;height:150px;display:none">
	<iframe id="gountmp016fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	<tr> 
		<td class="queryTDLable"><font color="red">*</font>減損原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause" onchange="changecause()">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC'  ", obj.getQ_cause())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">其它說明：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_cause1" size="20" maxlength="20" value="<%=obj.getQ_cause1()%>" readOnly=true>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">接管機關：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="q_newEnterOrg" size="10" maxlength="10" value="<%=obj.getQ_newEnterOrg()%>">
			[<input class="field_QRO" type="text" name="q_newEnterOrgName" size="20" maxlength="50" value="<%=obj.getQ_newEnterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_newEnterOrg" onclick="popOrgan('q_newEnterOrg','q_newEnterOrgName','Y')" value="..." title="機關輔助視窗" disabled=true>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">交接日期：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_transferDate" size="7" maxlength="7" value="<%=obj.getQ_transferDate()%>" readOnly=true>
			<input class="field_Q" type="button" name="btn_q_transferDate" onclick="popCalndar('q_transferDate')" value="..." title="萬年曆輔助視窗" disabled=true>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">報廢或失竊原因：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="cause2" size="14" maxlength="10" value="<%=obj.getCause2()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">擬予處理意見：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="dealSuggestion" size="40" maxlength="20" value="<%=obj.getDealSuggestion()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">預估殘值總價：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="scrapValue2" size="15" maxlength="15" value="<%=obj.getScrapValue2()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">繳存地點：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="returnPlace" size="20" maxlength="25" value="<%=obj.getReturnPlace()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountmp016f');initC();">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
		<td class="queryTDLable" width="13%">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"3,4,5")%>&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable" >財產分號：</td> 
		<td class="queryTDInput">	
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">購置日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_buyDateS",obj.getQ_buyDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",obj.getQ_buyDateE())%>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput" width="35%">
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
		<td class="queryTDLable">可報廢日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_permitReduceDateS",obj.getQ_permitReduceDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_permitReduceDateE",obj.getQ_permitReduceDateE())%>
		</td>
	</tr>
	<tr>
		<div style="display:none">	
	    <td class="queryTDLable">保管處別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_keepBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_keepBureau,form1.q_keepUnit,form1.q_keeper,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + obj.getQ_enterOrg() + "%' order by bureau ", obj.getQ_keepBureau())%>			
			</select>
		</td>
		</div>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');">
            <%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", obj.getQ_keepBureau())%>			
			</select>
		</td>
		<td class="queryTDLable"><div align="right">保管人：</div></td>
	    <td class="queryTDInput" colspan="3"><select class="field_Q" type="select" name="q_keeper">
            <script>
            getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '<%=obj.getQ_keeper()%>');</script>
          </select>
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
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
	  		[<input class="field_QRO" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>">]
	   		&nbsp;分號：[<input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
	 		&nbsp;名稱：[<input class="field_RO" type="text" name="propertyName" size="16" maxlength="50" value="<%=obj.getPropertyName()%>">]
	   		&nbsp;&nbsp;&nbsp;&nbsp;批號：[<input class="field_RO" type="text" name="lotNo" size="7" value="<%=obj.getLotNo()%>">]
	   	<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" value="<%=obj.getPropertyName1()%>">]	
			&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]
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
			&nbsp;&nbsp;可報廢日期：[<input class="field_RO" type="text" name="permitReduceDate" size="7" maxlength="7" value="<%=obj.getPermitReduceDate()%>">]
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
		<td class="td_form">財產性質： </td>
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
				[<%=util.View.getYNOption(obj.getValuable())%>]
			</select>					
			　&nbsp;&nbsp;&nbsp;　　　　會計科目：[<input class="field_RO" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">]
		</td>
	</tr> 
	<tr>
		<td class="td_form">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">] 
			&nbsp;&nbsp;　單價：[<input class="field_RO type="text" name="originalUnit" size="13" maxlength="13" value="<%=obj.getOriginalUnit()%>">] 
			　總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">] 
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
		</td>
	</tr>
	</table>
	</div>
</td>
</tr>

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
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="批次設定" onClick="queryShow('gountmp016f');">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產性質</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">存置地點</a></th>
		
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {true,true,true,true,true,false,false,false,false,false};
	//boolean displayArray[] = {false,false,false,true,true,true,true,true,true,true};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	
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
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[4]+"')\"";
			sbHEML.append(" <tr class='listTR'>\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			sbHEML.append(" <td class='listTD'><a href='#' " + tempJS + ">"+rowArray[3]+"</a></td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[6]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[8]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[9]+"</td></tr>\n ");
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
