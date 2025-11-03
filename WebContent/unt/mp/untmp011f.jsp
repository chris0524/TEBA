<!--
程式目的：動產保管使用異動作業－現有資料明細新增明細資料
程式代號：untmp011f
程式日期：0950220
程式作者：Cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP011F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP011F)obj.queryOne();	
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
<title>動產保管使用異動作業現有資料明細新增輔助視窗</title>
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkInsertSelect();
		alertStr += checkEmpty(form1.q_newKeepUnit,"新保管單位");
		alertStr += checkEmpty(form1.q_newKeeper,"新保管人");
		alertStr += checkEmpty(form1.q_newUseUnit,"新使用單位");
		alertStr += checkEmpty(form1.q_newUserNo,"新使用人");
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

function changeUse(){
	getKeepUnit(form1.tempEnterOrg, form1.q_newUseUnit, form1.q_newKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.q_newUseUnit, form1.q_newUserNo, form1.q_newKeeper.value);
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');initC();showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位============================================================-->
<input class="field_QRO" type="hidden" name="q_enterOrg" value="<%=obj.getQ_enterOrg()%>">
<input class="field_QRO" type="hidden" name="q_dataState" value="1">
<input class="field_QRO" type="hidden" name="q_ownership" value="<%=obj.getQ_ownership()%>">			
<input class="field_QRO" type="hidden" name="q_verify" value="<%=obj.getQ_verify()%>">
<input class="field_QRO" type="hidden" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="tempEnterOrg" value="<%=obj.getQ_enterOrg()%>">
<input class="field_QRO" type="HIDDEN" name="enterOrg" value="<%=obj.getEnterOrg()%>">
<input class="field_QRO" type="HIDDEN" name="ownership" value="<%=obj.getOwnership()%>">
<input class="field_QRO" type="HIDDEN" name="caseNo" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="HIDDEN" name="verify" value="<%=obj.getVerify()%>"> 
<!--批次設定區============================================================-->
<div id="gountmp011f" style="position:absolute;z-index: 25;left:0;top :0;width:550px;height:150px;display:none">
	<iframe id="gountmp011fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	<tr> 
		<td class="queryTDLable">新移動資料：</td>
		<td class="queryTDInput">
			移動日期：[<input class="field_QRO" type="text" name="q_newMoveDate" size="7" maxlength="7" value="<%=obj.getQ_newMoveDate()%>">]
		<br>
			<div style="display:none">	
		          保管處別：
			<select class="field_Q" type="select" name="q_newKeepBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_newKeepBureau,form1.q_newKeepUnit,form1.q_newKeeper,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", obj.getQ_newKeepBureau())%>			
			</select>
		<br>
			</div>
			<font color="red">*</font>保管單位：
			<select class="field_Q" type="select" name="q_newKeepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_newKeeper, '');">
              	<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getQ_newKeepUnit()) %>
            </select>	
		<br>
			<font color="red">*</font>保管人：
			<select class="field_Q" type="select" name="q_newKeeper" onChange="changeUse();">
              <script>getKeeper(form1.q_enterOrg, form1.q_newKeepUnit, form1.q_newKeeper, '<%=obj.getQ_newKeeper()%>');;</script>
            </select>     
        <br>
        	<div style="display:none">	
                                使用處別：
			<select class="field_Q" type="select" name="q_newUseBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_newUseBureau,form1.q_newUseUnit,form1.q_newUserNo,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", obj.getQ_newUseBureau())%>			
			</select>      
		<br>
			</div>
			<font color="red">*</font>使用單位：
			<select class="field_Q" type="select" name="q_newUseUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_newUserNo, '');">
              	<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getQ_newUseUnit()) %>
            </select>
        <br>
			<font color="red">*</font>使用人：
			<select class="field_Q" type="select" name="q_newUserNo">
				<script>getKeeper(form1.q_enterOrg, form1.q_newUseUnit, form1.q_newUserNo, '<%=obj.getQ_newUserNo()%>');;</script>
			</select>
		<br>
			存置地點：<input class="field_Q" type="text" name="q_newPlace" size="25" maxlength="25" value="<%=obj.getQ_newPlace()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountmp011f');initC();">
		</td>
	</tr>
	</table>
</div>
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:300px;display:none">
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
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
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
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
	</tr>
	<tr>
	    <td class="queryTDLable">保管處別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_keepBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_keepBureau,form1.q_keepUnit,form1.q_keeper,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", obj.getQ_keepBureau())%>			
			</select>
		</td>
		<td class="queryTDLable">使用處別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_useBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_useBureau,form1.q_useUnit,form1.q_userNo,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", obj.getQ_useBureau())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.tempEnterOrg, this, form1.q_keeper, '');">
  				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getQ_keepUnit()) %>			
			</select>
		</td>
	    <td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_useUnit" onChange="getKeeper(form1.tempEnterOrg, this, form1.q_userNo, '');">
  				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", obj.getQ_useUnit())%>			
			</select>
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable"><div align="right">保管人：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_keeper">
            <script>getKeeper(form1.tempEnterOrg, form1.q_keepUnit, form1.q_keeper, '<%=obj.getQ_keeper()%>');;</script>
          </select>
        </td>
	    <td class="queryTDLable"><div align="right">使用人：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_userNo">
            <script>getKeeper(form1.tempEnterOrg, form1.q_useUnit, form1.q_userNo, '<%=obj.getQ_userNo()%>');</script>
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
		</td>
	</tr>
	<tr>
		<td class="td_form">預留殘值：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="scrapValue" size="15" maxlength = "15" value="<%=obj.getScrapValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">廠牌：</td>
		<td class="td_form_white" colspan="3">		
			[<input class="field_RO" type="text" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate()%>" >]
			　　　　型式：[<input class="field_RO" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">]　
		</td>
	</tr>	
	<tr>
		<td class="td_form">購置日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;&nbsp;　　　　　　　　取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質： </td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			&nbsp;&nbsp;&nbsp;　基金財產：
			<select class="field_RO" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
			&nbsp;　珍貴財產：
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>					
		</td>
	</tr> 
	<tr>
		<td class="td_form">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">] 
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
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="批次設定" onClick="queryShow('gountmp011f');">&nbsp;|
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
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"',"+rowArray[2]+",'"+rowArray[3]+"','"+rowArray[4]+"')\"";
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
