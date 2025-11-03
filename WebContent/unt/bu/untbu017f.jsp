 
<!--
程式目的：建物減少作業－現有資料明細新增明細資料
程式代號：untbu017f
程式日期：0940930
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU017F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.bu.UNTBU017F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<title>建物減少作業現有資料明細新增輔助視窗</title>
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
<script language="javascript" src="../../js/getUNTBUBuilding.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

//當減損原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.q_cause.value=="99"){
		form1.q_cause1.readOnly = false;
	}else{
		form1.q_cause1.value="";
		form1.q_cause1.style.backgroundColor="";
		form1.q_cause1.readOnly = true;
	}
	if (form1.q_cause.value=="01" || form1.q_cause.value=="07" || form1.q_cause.value=="08"){
		setFormItem("btn_q_newEnterOrg,btn_q_transferDate,q_transferDate","O");
	}else{
		setFormItem("btn_q_newEnterOrg,btn_q_transferDate,q_transferDate","R");
		form1.q_newEnterOrg.value="";
		form1.q_newEnterOrgName.value="";
		form1.q_transferDate.value="";
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
		if ((form1.q_cause.value=="01")||(form1.q_cause.value=="07") || (form1.q_cause.value=="08")){
			alertStr += checkEmpty(form1.q_newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.q_transferDate,"交接日期");
		}
		
		if(form1.q_cause.value=="03" || form1.q_cause.value=="02"){
	        alertStr += checkEmpty(form1.q_lawBasis,"法令依據");
	    }
		
		alertStr += checkDate(form1.q_transferDate,"交接日期");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
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

function initC(){
	document.all.item("spaninsert").style.display="none";
	document.all.item("spanupdate").style.display="none";
	document.all.item("spandelete").style.display="none";
	document.all.item("spanclear").style.display="none";
	document.all.item("spanconfirm").style.display="none";
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
<input type="hidden" name="q_verify" size="1" maxlength="1" value="Y">
<input type="hidden" name="q_dataState" size="1" maxlength="1" value="1">
<!--批次設定區============================================================-->
<div id="gountbu017f" style="position:absolute;z-index: 25;left:0;top :0;width:350px;height:200px;display:none">
	<iframe id="gountbu017fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
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
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_cause1" size="20" maxlength="20" value="<%=obj.getQ_cause1()%>" readOnly=true>
		</td>
	</tr>
	
	<tr>
		<td class="queryTDLable">法令依據：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_lawBasis" size="24" maxlength="24" value="<%=obj.getQ_lawBasis()%>">
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
			<input class="field_Q" type="text" name="q_transferDate" size="7" maxlength="7" value="<%=obj.getQ_transferDate()%>">
			<input class="field_Q" type="button" name="btn_q_transferDate" onclick="popCalndar('q_transferDate')" value="..." title="萬年曆輔助視窗" disabled=true>
		</td>
	</tr>	 
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountbu017f');initC();">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:770px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
		<td class="queryTDLable" width="14%">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"2")%>&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"2")%>
			<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
			<input class="field_QRO" type="hidden" name="q_ownership" size="1" maxlength="1" value="<%=obj.getQ_ownership()%>">
			<input class="field_QRO" type="hidden" name="q_caseNo" size="10" maxlength="10" value="<%=obj.getQ_caseNo()%>">
			<input class="field_QRO" type="hidden" name="q_reduceDate" size="7" maxlength="7" value="<%=obj.getQ_reduceDate()%>">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable" >財產分號：</td> 
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
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
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput" width="36%">
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
		<td class="queryTDLable">可報廢日期：</td>
		<td class="queryTDInput" colspan="3">
			起 <%=util.View.getPopCalndar("field_Q","q_permitReduceDateS",obj.getQ_permitReduceDateS())%>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_permitReduceDateE",obj.getQ_permitReduceDateE())%>
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
	<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
	<input class="field_QRO" type="HIDDEN" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
	<input class="field_QRO" type="HIDDEN" name="caseNo" size="24" maxlength="24" value="<%=obj.getCaseNo()%>">
	<tr>
		<td width="14%" class="td_form">財產編號：</td>
		<td colspan="3" class="td_form_white">
	  		[<input class="field_QRO" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>">]
	   		&nbsp;分號：[<input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
	 		&nbsp;名稱：[<input class="field_RO" type="text" name="propertyName" size="16" maxlength="50" value="<%=obj.getPropertyName()%>">]
	<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
			&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
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
		</td>
	</tr>
	<tr>
	  <td width="120" class="td_form">建物標示：</td>
	  <td colspan="3" class="td_form_white">
	  	<select  type="select" class="field_RO" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getUNTBUBuilding('SN');">
        	<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
      	</select>
        <select  type="select" class="field_RO" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getUNTBUBuilding('SN');">
          <script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
        </select> 
        <select  type="select" class="field_RO" name="signNo3">
          <script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
        </select>
		　建號：
		[<input class="field_RO" type="text" name="signNo4" size="5" maxlength="5" value="<%=obj.getSignNo4()%>" onBlur="getUNTBUBuilding('SN');getValue();">]
		&nbsp;-
		[<input class="field_RO" type="text" name="signNo5" size="3" maxlength="3" value="<%=obj.getSignNo5()%>" onBlur="getUNTBUBuilding('SN');getValue();">]
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
			帳面金額總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
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
			應攤提折舊總額：[<input class="field_RO" type="text" name="deprAmount" size="10" value="<%=obj.getDeprAmount()%>">]
		<br>
			攤提年限截止年月：[<input class="field_RO" type="text" name="apportionEndYM" size="10" value="<%=obj.getApportionEndYM()%>">]
			&nbsp;　　　　月提折舊金額：[<input class="field_RO" type="text" name="monthDepr" size="10" value="<%=obj.getMonthDepr()%>">]
		<br>
			累計折舊調整年月：[<input class="field_RO" type="text" name="accumDeprYM" size="10" value="<%=obj.getAccumDeprYM()%>">]
			&nbsp;　　累計折舊調整金額：[<input class="field_RO" type="text" name="accumDepr" size="10" value="<%=obj.getAccumDepr()%>">]
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
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="批次設定" onClick="queryShow('gountbu017f');">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">建物標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">權利範圍面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">帳面金額─總價</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {true,true,true,true,false,false,false,false,false};
	//boolean displayArray[] = {false,false,true,true,true,true,true,true,true};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	
		int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[9];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";			
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";
			sbHEML.append(" <tr class='listTR' >\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			sbHEML.append(" <td class='listTD'><a href='#' " + tempJS + ">"+rowArray[2]+"</a></td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[3]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[6]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");
		}
	}
	out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</form>
</body>
</html>
