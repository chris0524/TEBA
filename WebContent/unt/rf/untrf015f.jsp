<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rf.UNTRF015F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rf.UNTRF015F)obj.queryOne();	
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
String tabs = uch._createTabData(uch._RF_ADJUST, 2);
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

function changecause(){
//當增減值原因選「3.其他」時，開放其他說明欄位
	if (form1.cause.value=="3"){
		form1.cause1.style.backgroundColor=editbg;
		form1.cause1.readOnly = false;
	}else{
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
	}
//當增減值原因選「2.整建」時，開放整建說明欄位
    if (form1.cause.value=="2"){
	   	form1.cause2.style.backgroundColor=editbg;
		form1.cause2.readOnly = false;
	}else{
		form1.cause2.value="";
		form1.cause2.style.backgroundColor=readbg;
		form1.cause2.readOnly = true;
	}
//當增減值原因選「1.資產重估調整」時，「增減計量數」，皆不可以開放
	if (form1.cause.value=="1"){
		form1.adjustMeasure.value="0";
		form1.adjustMeasure.style.backgroundColor=readbg;
		form1.adjustMeasure.readOnly = true;
	}else{
		form1.adjustMeasure.style.backgroundColor=editbg;
		form1.adjustMeasure.readOnly = false;
	}
	//當增減值原因由 2 or 3 改成 1 時,則預設增減的各欄位 = 0 ; new 的各欄位 = old
	if (!( form1.cause.value=="1" && form1.oldcause.value == "1" )){
		form1.adjustMeasure.value = "0";
		form1.adjustBookValue.value = "0";
		
		form1.newMeasure.value = form1.oldMeasure.value;
		form1.newBookValue.value = form1.oldBookValue.value;
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untrf014f.jsp";
		} else {
			form1.action = "untrf015f.jsp";
		}
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.cause,"增減值原因");
		alertStr += checkLen(form1.notes, "備註", 250);
		if (form1.cause.value=="3")	alertStr += checkEmpty(form1.cause1,"增減值原因-其他說明");

		//當增減值原因選「1.資產重估調整」時，「新帳面金額─總價 不可小於 原始入帳金額─總價」
		if ((form1.cause.value=="1")&&(parseInt(form1.newBookValue.value) < parseInt(form1.originalBV.value) )){
			alertStr += "新帳面金額─總價不可低於原始入帳─總價。\n"; 
		}
		//if ((form1.adjustType.value=="2")&&(parseInt(form1.oldMeasure.value)==0)&&(parseInt(form1.adjustMeasure.value)!=0)){
			//alertStr += "增減別為減少時，若「原計量數」為0，則「增減計量數」必須為0。\n"; 
		//}else if ((form1.adjustType.value=="2")&&(parseInt(form1.oldMeasure.value)!=0)&&( parseInt(form1.adjustMeasure.value) >= parseInt(form1.oldMeasure.value) )){
			//alertStr += "增減別為減少時，增減計量數必須小於原計量數。\n"; 
		//}
		 
		//if ((form1.adjustType.value=="2")&&( form1.valuable.value == "N" )&&( parseInt(form1.adjustBookValue.value) >= parseInt(form1.oldBookValue.value) )){ 
		//	alertStr += "增減別為減少時，增減帳面金額─總價必須小於原帳面金額─總價。\n"; 
		//}

		//if ((form1.adjustType.value=="2")&&( form1.valuable.value == "Y" )&&( parseInt(form1.adjustBookValue.value) > parseInt(form1.oldBookValue.value) )){ 
		//	alertStr += "增減別為減少時，增減帳面金額─總價必須小於等於原帳面金額─總價。\n"; 
		//}
		
		if(form1.adjustType.value=="2"){
		    if(((form1.fundType.value != "") || (form1.fundType.value != null)) && (parseInt(form1.newBookValue.value) > parseInt(form1.oldBookValue.value)) ){
		        alertStr += "增減別為減少,又為基金類別時，增減帳面金額─總價必須小於等於原帳面金額─總價\n";
		    }else if((form1.valuable.value == "Y") && (parseInt(form1.newBookValue.value) > parseInt(form1.oldBookValue.value)) ){
		        alertStr += "增減別為減少,又為珍貴財產時，增減帳面金額─總價必須小於等於原帳面金額─總價\n";
		    }else if(parseInt(form1.adjustBookValue.value) >= parseInt(form1.oldBookValue.value)){  	
		        alertStr += form1.adjustBookValue.value+"增減別為減少時,增減帳面金額─總價必須小於原帳面金額─總價\n"+form1.oldBookValue.value;
		    }

		    if(form1.fundType.value == "" && (form1.valuable.value == "N" || form1.valuable.value == "")  && (parseInt(form1.newBookValue.value)<=0)){
			        alertStr += "增減別為減少,又不為珍貴財產或基金類別時,新價值必須大於0\n";
		    }else if((form1.fundType.value != "" || form1.valuable.value == "Y") && (parseInt(form1.newBookValue.value)<0)){
		        alertStr += "增減別為減少又為珍貴財產或基金類別時,新價值必須大等於0\n";
		    }
		}
		
        
		alertStr += checkEmpty(form1.adjustType,"增減別");
		alertStr += checkEmpty(form1.adjustMeasure,"增減計量數"); 
		alertStr += checkFloat(form1.adjustMeasure,"增減計量數",9,2); 
	
		if ((form1.propertyUnit.value=="座")||(form1.propertyUnit.value=="套")||(form1.propertyUnit.value=="條")) {
			if (form1.newMeasure.value!="1"){
				alertStr += "若財產單位為「座、套、條」時，新計量數欄位須固定為1\n"; 
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
		if (surl=="untrf014f.jsp"){	
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

function gountrf016f(){
	var prop="";
	var windowTop=(document.body.clientHeight-80)/2+25;
	var windowLeft=(document.body.clientWidth-800)/2+250;
	prop=prop+"width=775,height=475,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	var enterOrg = form1.enterOrg.value; 
	var ownership = form1.ownership.value; 
	var caseNo = form1.caseNo.value; 
	var adjustDate = form1.adjustDate.value; 
	beforeSubmit();
	returnWindow=window.open("untrf016f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_adjustDate="+adjustDate,"aha",prop);	
}

function getMeasureData() {

	if ( form1.adjustType.value == "1" ) {
		if (form1.adjustMeasure.value !=""){
			form1.newMeasure.value = parseFloat(form1.oldMeasure.value) + parseFloat(form1.adjustMeasure.value) ;
		}else{
			form1.newMeasure.value = parseFloat(form1.oldMeasure.value) ;
		}
		if (form1.adjustBookValue.value !=""){
			form1.newBookValue.value = parseFloat(form1.oldBookValue.value) + parseFloat(form1.adjustBookValue.value) ;
		}else{
			form1.newBookValue.value = parseFloat(form1.oldBookValue.value) ;
		}
	} 
	if ( form1.adjustType.value == "2" ) {
		if (form1.adjustMeasure.value !=""){
			form1.newMeasure.value = parseFloat(form1.oldMeasure.value) - parseFloat(form1.adjustMeasure.value) ;
		}else{
			form1.newMeasure.value = parseFloat(form1.oldMeasure.value) ;
		}
		if (form1.adjustBookValue.value !=""){
			form1.newBookValue.value = parseFloat(form1.oldBookValue.value) - parseFloat(form1.adjustBookValue.value) ;
		}else{
			form1.newBookValue.value = parseFloat(form1.oldBookValue.value) ;
		}
	}
} 

function getDefault(){
	form1.oldMeasure.value = form1.measure.value;
	form1.oldBookValue.value = form1.bookValue.value;
	
	form1.newMeasure.value = form1.oldMeasure.value;
	form1.newBookValue.value = form1.oldBookValue.value; 
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
		form1.oldPropertyUnit.value=form1.propertyUnit.value;
		form1.adjustPropertyUnit.value=form1.propertyUnit.value;
		form1.newPropertyUnit.value=form1.propertyUnit.value;
	}
}

function checkData( v ){
	if(eval(v.value) < 0 ) {
		v.value = "0";
		alert("增減數值不得小於零");
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
	returnWindow=window.open("untrf016f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_adjustDate="+adjustDate,"aha",prop);	
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
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
	<% request.setAttribute("UNTRF014Q",obj);%>
	<jsp:include page="untrf014q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<input class="field_QRO" type="HIDDEN" name="oldcause" size="1" value="<%=obj.getCause()%>">
	<input class="field_QRO" type=HIDDEN name="propertyUnit" size="10" value="<%=obj.getPropertyUnit()%>">
	<input class="field_QRO" type="HIDDEN" name="measure" size="20" value="<%=obj.getMeasure()%>">
	<input class="field_QRO" type="HIDDEN" name="bookValue" size="20" value="<%=obj.getBookValue()%>">
	<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" value="<%=obj.getEnterOrg()%>">
	<tr>
		<td class="td_form" width="17%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;　　權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			　　　電腦單號：
			[<input type="text" name="caseNo" class="field_QRO" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">增減值日期：</td>
		<td class="td_form_white" colspan = "3">
			[<input type="text" name="adjustDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getAdjustDate()%>">]
			&nbsp;&nbsp;　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;  月結：
			<select class="field_QRO" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td colspan="3" class="td_form_white">
	  		<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','111');getUNTRFAttachment();getDefault();checkValue();"> 
	  		<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyName','111')" value="..." title="財產編號輔助視窗">
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
	   		&nbsp;&nbsp;<font color="red">*</font>分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getUNTRFAttachment();getDefault();checkValue();">
	   	<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
			&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
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
		<td class="td_form">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			財產取得日期：
			[<input type="text" name="sourceDate" class="field_QRO"  size="10" maxlength="7" value="<%=obj.getSourceDate()%>">]
			<br>
			原始入帳價值：[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">]
		<br>
			原計量數：[<input class="field_RO" type="text" name="oldMeasure" size="9" maxlength="9" value="<%=obj.getOldMeasure()%>">
					<input class="field_RO" type=text name="oldPropertyUnit" size="10" value="<%=obj.getPropertyUnit()%>">]
			原帳面價值：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">增減帳面資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>增減別：
			<select class="field" type="select" name="adjustType" onChange="getMeasureData();" >
				<option value="">請選擇</option>
				<option value="1" >增加</option>
				<option value="2" >減少</option>
				<script>getSelectedValue(form1.adjustType,"<%=obj.getAdjustType()%>");</script>
			</select>			
			帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
		<br>
			<font color="red">*</font>增減值原因：
			<select class="field" type="select" name="cause" onchange="changecause();">
				<%=util.View.getTextOption("1;資產重估調整;2;整建;3;其它",obj.getCause())%>
			</select>
			整建說明：<input class="field" type="text" name="cause2" size="20" maxlength="20" value="<%=obj.getCause2()%>">
			其他說明：<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		<br>
			<font color="red">*</font>
			增減計量數：<input class="field" type="text" name="adjustMeasure" size="9" maxlength="9" value="<%=obj.getAdjustMeasure()%>" onBlur="checkData(this);getMeasureData();" >
					  <input class="field_RO" type=text name="adjustPropertyUnit" size="10" value="<%=obj.getPropertyUnit()%>">
			&nbsp;　<font color="red">*</font>
			增減帳面價值：<input class="field" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>" onChange="checkData(this);getMeasureData();">
		</td>
	</tr>	
	<tr>
		<td class="td_form">新帳面資料：</td>
		<td class="td_form_white" colspan="3">	
		新計量數：[<input class="field_RO" type="text" name="newMeasure" size="9" maxlength="9" value="<%=obj.getNewMeasure()%>">
				<input class="field_RO" type=text name="newPropertyUnit" size="10" value="<%=obj.getPropertyUnit()%>">]
		新帳面價值：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">]
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
	<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountmp027f();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">增減值原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">增減別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">增減計量數</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">單位</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">增減帳面價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">帳務摘要</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,true,false,true,true,true,false,true,false,false,false,false,false,true,true,true,true,false,false,true};
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
			setFormItem("ownership,verify,cause1","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "insertError":
			setFormItem("ownership,verify,cause1","R");
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
			if(form1.cause.value=="3"){setFormItem("cause1","O");}
			else{setFormItem("cause1","R");}
			
			break;
		case "updateError":
			setFormItem("ownership,verify","R");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			if(form1.cause.value=="3"){setFormItem("cause1","O");}
			else{setFormItem("cause1","R");}
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
