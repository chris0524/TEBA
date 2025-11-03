
<!--
程式目的：動產增減值作業－增減值單明細
程式代號：untmp026f
程式日期：0941110
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp"%>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP026F">
	<jsp:setProperty name="obj" property="*" />
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList" />

<%
	if ("queryAll".equals(obj.getState())) {
		if ("false".equals(obj.getQueryAllFlag())) {
			obj.setQueryAllFlag("true");
		}
	} else if ("queryOne".equals(obj.getState())) {
		obj = (unt.mp.UNTMP026F) obj.queryOne();
	} else if ("insert".equals(obj.getState())
			|| "insertError".equals(obj.getState())) {
		obj.insert();
	} else if ("update".equals(obj.getState())
			|| "updateError".equals(obj.getState())) {
		obj.update();
	} else if ("delete".equals(obj.getState())
			|| "deleteError".equals(obj.getState())) {
		obj.delete();
	}
	if ("true".equals(obj.getQueryAllFlag())) {
		objList = obj.queryAll();
	}
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/getUNTMPMovable.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untmp025f.jsp";
		} else {
			form1.action = "untmp026f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.cause,"增減值原因");
		alertStr += checkEmpty(form1.adjustType,"增減別");
		alertStr += checkEmpty(form1.adjustBookValue,"增減價值");
		alertStr += checkInt(form1.adjustBookValue,"增減價值");
		alertStr += checkLen(form1.notes, "備註", 250);	
		var adjustBookValue = parseInt(form1.adjustBookValue.value);
		var oldBookValue = parseInt(form1.oldBookValue.value);
		//if ((form1.adjustType.value=="2")&&( form1.valuable.value == "N" )&&(adjustBookValue >= oldBookValue)){ 
		//	alertStr += "增減別為減少時，增減價值必須小於原帳面金額─總價。\n"; 
		//}

		//if ((form1.adjustType.value=="2")&&( form1.valuable.value == "Y" )&& (adjustBookValue > oldBookValue)){ 
		//	alertStr += "增減別為減少時，增減價值必須小於等於原帳面金額─總價。\n"; 
		//}

		if(form1.adjustType.value=="2"){
		    if(((form1.fundType.value != "") || (form1.fundType.value != null)) && (parseInt(form1.newBookValue.value) > parseInt(form1.oldBookValue.value)) ){
		        alertStr += "增減別為減少,又為基金類別時，增減帳面金額─總價必須小於等於原帳面金額─總價\n";
		    }else if((form1.valuable.value == "Y") && (parseInt(form1.newBookValue.value) > parseInt(form1.oldBookValue.value)) ){
		        alertStr += "增減別為減少,又為珍貴財產時，增減帳面金額─總價必須小於等於原帳面金額─總價\n";
		    }else if(parseInt(form1.newBookValue.value) >= parseInt(form1.oldBookValue.value)){
		        alertStr += "增減別為減少時,增減帳面金額─總價必須小於原帳面金額─總價\n";
		    }
		    
		    if(form1.fundType.value == "" && (form1.valuable.value == "N" || form1.valuable.value == "")  && (parseInt(form1.newBookValue.value)<=0)){
			    alertStr += "增減別為減少,又不為珍貴財產或基金類別時,新價值必須大於0\n";
		    }else if((form1.fundType.value != "" || form1.valuable.value == "Y") && (parseInt(form1.newBookValue.value)<0)){
		        alertStr += "增減別為減少又為珍貴財產或基金類別時,新價值必須大等於0\n";
		    }
		}
		
		getBookValueData();
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
		if (surl=="untmp025f.jsp"){	
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

function gountmp027f(){
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
	var adjustDate = form1.adjustDate.value; 
	beforeSubmit();
	returnWindow=window.open("untmp027f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_adjustDate="+adjustDate,"aha",prop);	
}

function getBookValueData() { 
	if ( form1.adjustType.value == "1" ) {
		if(parse(form1.oldBookValue.value).length>0 && parse(form1.adjustBookValue.value).length>0){
			form1.newBookValue.value = parseFloat(form1.oldBookValue.value) + parseFloat(form1.adjustBookValue.value) ;
		}else{
			form1.newBookValue.value = "";
		}
	} else {
		if(parse(form1.oldBookValue.value).length>0 && parse(form1.adjustBookValue.value).length>0){
			form1.newBookValue.value = parseFloat(form1.oldBookValue.value) - parseFloat(form1.adjustBookValue.value) ;
		}else{
			form1.newBookValue.value = "";
		}
	}
} 

function getDefault(){
    form1.adjustBookUnit.value = form1.adjustBookValue.value;
	if (form1.tOriginalUnit.value != "") {
		//form1.adjustBookUnit.value = form1.adjustBookValue.value;		
		form1.newBookUnit.value = form1.newBookValue.value;
	}
}

function init(){
	var arrField = new Array("update","delete");
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete", "R");
	}	
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
	if(form1.verify.value=="Y"){
		setFormItem("batchInsertBut","R");
	}else{
	    setFormItem("batchInsertBut","O");
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
		checkBookUnit();
	}
}

function checkBookUnit(){
	if(parse(form1.tOriginalUnit.value).length>0){
		form1.oldBookUnit.value = form1.bookValue.value;
		form1.adjustBookUnit.value = form1.adjustBookValue.value;
		form1.newBookUnit.value = form1.newBookValue.value;
	}else{
		form1.oldBookUnit.value = "";
		form1.adjustBookUnit.value = "";
		form1.newBookUnit.value = "";
	}
}
</script>
</head>
<body topmargin="0"	onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post"	onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="check" value="">
	<input type="hidden" name="tOriginalUnit" value="<%=obj.getTOriginalUnit()%>">
	<input type="hidden" name="bookValue" value="<%=obj.getBookValue()%>">
	<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
</td></tr></table>

<TABLE STYLE="width: 100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2"><a href="#" onClick="return checkURL('untmp025f.jsp');">增減值單資料</a></td>
		<td ID=t2 CLASS="tab_border1" HEIGHT="25">增減值單明細資料</td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
	</tr>
</TABLE>

<!--Query區============================================================-->
<div id="queryContainer" style="width: 760px; height: 400px; display: none">
	<iframe	id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<% request.setAttribute("UNTMP025Q", obj);	%> 
	<jsp:include page="untmp025q.jsp" />
	<input type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
</div>

<table width="100%" cellspacing="0" cellpadding="0">
	<!--Form區============================================================-->
	<tr>
		<td class="bg">
		<div id="formContainer">
		<table class="table_form" width="100%" height="100%">
			<tr>
				<td class="td_form" width="17%">入帳機關：</td>
				<td class="td_form_white" colspan="3">
					[<input class="field_QRO" type="text" name="enterOrgName" size="20"	value="<%=obj.getEnterOrgName()%>">]
					 &nbsp; &nbsp;&nbsp;
					權屬：
					<select class="field_QRO" type="select" name="ownership">
						<%=util.View.getOnwerOption(obj.getOwnership())%>
					</select>
					
					 &nbsp; &nbsp;&nbsp;
					電腦單號： 
					[<input class="field_QRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
				</td>
			</tr>
			<tr>
				<td class="td_form">增減值日期：</td>
				<td class="td_form_white" colspan="3">
					[<input type="text" name="adjustDate" class="field_QRO" size="7" maxlength="7" value="<%=obj.getAdjustDate()%>">] &nbsp; 
					入帳：
					<select class="field_QRO" type="select" name="verify">
						<%=util.View.getYNOption(obj.getVerify())%>
					</select>
					&nbsp;&nbsp;&nbsp; 
					月結： 
					<select class="field_QRO" type="select"	name="closing">
						<%=util.View.getYNOption(obj.getClosing())%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_form" width="120"><font color="red">*</font>財產編號：</td>
				<td colspan="3" class="td_form_white">
					<input class="field_P" type="text" name="propertyNo" size="12" maxlength="11" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','3,4,5');getUNTMPMovable();getDefault();checkValue();">
					<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyName','3,4,5')" value="..." title="財產編號輔助視窗"> 
					[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">] 
					分號：
					<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getUNTMPMovable();getDefault();checkValue();">
					批號：
					[<input class="field_RO" type="text" name="lotNo" size="10" value="<%=obj.getLotNo()%>">] <br>
					別名： 
					[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]&nbsp;
					原財產編號：
					[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">]&nbsp;
					原分號：
					[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
				</td>
			</tr>
			<tr>
				<td class="td_form">主要材質：</td>
				<td class="td_form_white" colspan="3">
					[<input class="field_RO" type="text" name="material" size="20" value="<%=obj.getMaterial()%>">] 
					其他主要材質： 
					[<input class="field_RO" type="text" name="otherMaterial" size="25"	maxlength="50" value="<%=obj.getOtherMaterial()%>">]
					<br>
					單位：
					[<input class="field_RO" type="text" name="propertyUnit" size="7" maxlength="7" value="<%=obj.getPropertyUnit()%>">]
					&nbsp;&nbsp;&nbsp;
					其他單位： 
					[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>">]
					<br>
					使用年限： 
					[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="7" value="<%=obj.getLimitYear()%>">]
					&nbsp;&nbsp;&nbsp;
					調整後年限(月)：
					[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
				</td>
			</tr>
			<tr>
				<td class="td_form">財產性質：</td>
				<td class="td_form_white" colspan="3">
					<select class="field_RO" type="select" name="propertyKind">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getPropertyKind())%>
					</select>
					基金財產：
					<select class="field_RO" type="select" name="fundType">
						<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
					</select>
					珍貴財產：
					<select class="field_RO" type="select" name="valuable">
						<%=util.View.getYNOption(obj.getValuable())%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_form">帳面資料：</td>
				<td class="td_form_white" colspan="3">取得日期：[<input type="text"
					name="sourceDate" class="field_QRO" size="10" maxlength="7"
					value="<%=obj.getSourceDate()%>">] &nbsp; 帳面數量：[<input
					class="field_RO" type="text" name="bookAmount" size="7"
					maxlength="7" value="<%=obj.getBookAmount()%>">] <br>
				<font color="red">*</font>增減別： <select class="field" type="select"
					name="adjustType" onChange="getBookValueData();checkBookUnit();">
					<option value="">請選擇</option>
					<option value="1">增加</option>
					<option value="2">減少</option>
					<script>getSelectedValue(form1.adjustType,"<%=obj.getAdjustType()%>");</script>
				</select> &nbsp;<font color="red">*</font>增減值原因： <input class="field"
					type="text" name="cause" size="6" maxlength="12"
					value="<%=obj.getCause()%>"> 帳務摘要：<input class="field"
					type="text" name="bookNotes" size="18" maxlength="20"
					value="<%=obj.getBookNotes()%>"> <br>
				原價值：[<input class="field_RO" type="text" name="oldBookValue"
					size="12" maxlength="15" value="<%=obj.getOldBookValue()%>">]
				&nbsp;<font color="red">*</font>增減價值：<input class="field"
					type="text" name="adjustBookValue" size="12" maxlength="15"
					value="<%=obj.getAdjustBookValue()%>"
					onChange="getBookValueData();checkBookUnit();"> &nbsp;新價值：[<input
					class="field_RO" type="text" name="newBookValue" size="12"
					maxlength="15" value="<%=obj.getNewBookValue()%>">] <!--原單價：--><input
					class="field_RO" type="hidden" name="oldBookUnit" size="13"
					maxlength="13" value="<%=obj.getOldBookUnit()%>"> <!--增減單價：--><input
					class="field_RO" type="text" name="adjustBookUnit" size="15"
					maxlength="15" value="<%=obj.getAdjustBookUnit()%>"
					onChange="getBookValueData();"> <!--新單價：--><input
					class="field_RO" type="hidden" name="newBookUnit" size="13"
					maxlength="13" value="<%=obj.getNewBookUnit()%>"></td>
			</tr>
			<tr>
				<td class="td_form">移動資料：</td>
				<td class="td_form_white" colspan="3">保管單位：[<input
					class="field_RO" type="text" name="keepUnitName" size="30"
					value="<%=obj.getKeepUnitName()%>">] <input
					class="field_RO" type="hidden" name="keepUnit"
					value="<%=obj.getKeepUnit()%>"> &nbsp; 保管人：[<input
					class="field_RO" type="text" name="keeperName" size="10"
					value="<%=obj.getKeeperName()%>">] <input class="field_RO"
					type="hidden" name="keeper" value="<%=obj.getKeeper()%>"> <br>
				使用單位：[<input class="field_RO" type="text" name="useUnitName"
					size="30" value="<%=obj.getUseUnitName()%>">] <input
					class="field_RO" type="hidden" name="useUnit"
					value="<%=obj.getUseUnit()%>"> &nbsp; 使用人：[<input
					class="field_RO" type="text" name="userName" size="10"
					value="<%=obj.getUserName()%>">] <input class="field_RO"
					type="hidden" name="userNo" value="<%=obj.getUserNo()%>"> <br>
				存置地點：[<input class="field_RO" type="text" name="place" size="25"
					value="<%=obj.getPlace()%>">]</td>
			</tr>
			<tr>
				<td class="td_form">備註：</td>
				<td class="td_form_white">
					<textarea class="field" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
				</td>
				<td class="td_form">異動人員/日期：</td>
				<td class="td_form_white">
					[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
					/<input	class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
				</td>
		</table>
		</div>
		</td>
	</tr>
	<!--Toolbar區============================================================-->
	<tr>
		<td class="bg" style="text-align: center">
			<input type="hidden" name="state" value="<%=obj.getState()%>"> 
			<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
			<input type="hidden" name="userID" value="<%=user.getUserID()%>">
			<jsp:include page="../../home/toolbar.jsp" /> 
			<span id="spanBatchInsertBut"> 
				<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountmp027f();">&nbsp;| 
			</span>
		</td>
	</tr>
	<!--List區============================================================-->
	<tr>
		<td class="bg">
		<div id="listContainer">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEAD">
				<tr>
					<th class="listTH"><a class="text_link_w">NO.</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">增減值原因</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳日期</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">增減別</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">增減帳面價值</a></th>
				</tr>
			</thead>
			<tbody id="listTBODY">
			<% 
				boolean primaryArray[] = {true,false,true,false,true,true,false,true,false,false,false,false,false,false,false,false};
				boolean displayArray[] = {false,true,false,true,false,false,true,true,true,true,false,false,false,true,true,false,false};
				out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
			%>
			</tbody>
		</table>
		</div>
		</td>
	</tr>
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
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "insertError":
			setFormItem("ownership,verify","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "updateError":
			setFormItem("ownership,verify","R");
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
