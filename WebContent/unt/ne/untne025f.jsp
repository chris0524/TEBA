<!--
程式目的：物品增減值作業－增減值單明細
程式代號：untne025f
程式日期：0941121
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE025F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE025F)obj.queryOne();	
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
		if("".equals(obj.getSerialNo())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setOwnership(((String[])objList.get(0))[2]);
			obj.setCaseSerialNo(((String[])objList.get(0))[5]);
			obj.setCaseNo(((String[])objList.get(0))[6]);
			obj.setDifferenceKind(((String[])objList.get(0))[17]);
			obj.setPropertyNo(((String[])objList.get(0))[7]);
			obj.setSerialNo(((String[])objList.get(0))[9]);
		}	
		obj.queryOne();
	}
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
<script language="javascript" src="../../js/getUNTNENonexp.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untne024f.jsp";
		} else {
			form1.action = "untne025f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		//alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		alertStr += checkEmpty(form1.ownership,"權屬");
		//alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"物品編號");
		alertStr += checkEmpty(form1.serialNo,"物品分號");
		alertStr += checkEmpty(form1.cause,"增減值原因");
		//alertStr += checkEmpty(form1.adjustBookValue,"增減價值");
		//alertStr += checkInt(form1.adjustBookValue,"增減價值");
		alertStr += checkEmpty(form1.addBookValue,"增加價值");
		alertStr += checkEmpty(form1.reduceBookValue,"減少價值");
		alertStr += checkInt(form1.addBookValue,"增加價值");
		alertStr += checkInt(form1.reduceBookValue,"減少價值");
		alertStr += checkLen(form1.notes, "備註", 250);	
		alertStr += checkQuotation(form1.notes,"備註");
		var reduceBookValue = parseInt(form1.reduceBookValue.value);
		var addBookValue = parseInt(form1.addBookValue.value);
		var oldBookValue = parseInt(form1.oldBookValue.value);
//		if ((form1.adjustType.value=="2")&&( form1.valuable.value == "N" )&&(adjustBookValue >= oldBookValue)){ 
//			alertStr += "增減別為減少時，增減價值必須小於原帳面金額─總價。\n"; 
//		}

//		if ((form1.adjustType.value=="2")&&( form1.valuable.value == "Y" )&& (adjustBookValue > oldBookValue)){ 
//			alertStr += "增減別為減少時，增減價值必須小於等於原帳面金額─總價。\n"; 
//		}


//			if((form1.fundType.value != "")&&(reduceBookValue > oldBookValue)){
//				alertStr += "增減別為減少，基金類別不為空時，增減價值必須小於等於原帳面金額─總價。\n"; 
//			}else if ((form1.valuable.value=="Y")&&(reduceBookValue > oldBookValue)){
//				alertStr += "增減別為減少，為珍貴財產時，增減價值必須小於等於原帳面金額─總價。\n";
//			}else if(reduceBookValue > oldBookValue){
//				alertStr += "增減別為減少時，增減價值必須小於原帳面金額─總價。\n";
//			}
		if((form1.fundType.value =="")&&(form1.valuable.value!="Y")&&(form1.newBookValue.value <= 0)){
			alertStr += "原帳面金額─總價必須大於1";
		}
		if( addBookValue<0 || reduceBookValue <0){
			alertStr += "增減價值必須大於等於0。\n";
		}
		changeAdjustUnit();
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
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untne024f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.propertyNo,"物品編號");
				alertStr += checkEmpty(form1.serialNo,"物品分號");			
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"物品編號");
			alertStr += checkEmpty(form1.serialNo,"物品分號");			
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		//alertStr += checkEmpty(form1.caseNo,"電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		
		form1.currentPage.value=form1.mainPage.value;
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function gountne026f(){
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
	var differenceKind = form1.differenceKind.value; 
	beforeSubmit();
	returnWindow=window.open("untne026f.jsp?enterOrg="+enterOrg+"&ownership="+ownership+"&caseNo="+caseNo+"&adjustDate="+adjustDate+"&differenceKind="+differenceKind,"aha",prop);	
}


function gountne027f(){

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
	var differenceKind = form1.differenceKind.value; 
	beforeSubmit();
	returnWindow=window.open("untne027f.jsp?enterOrg="+enterOrg+"&ownership="+ownership+"&caseNo="+caseNo+"&differenceKind="+differenceKind,"aha",prop);
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
	if (form1.enterOrg.value!="<%=user.getOrganID()%>" && form1.verify.value=="Y") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
	if(form1.cannotVerify.value == "Y")
	{
		setFormItem("verifyRe,verify","R");
	}

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value+ form1.propertyNo.value + form1.serialNo.value + form1.differenceKind.value ;
		listContainerRowClick(key);
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
		changeAdjustUnit();
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
function changeAdjustUnit(colName){
	if(form1.addBookValue.value == ""){
		form1.addBookValue.value = "0";
		
	}
	if(form1.reduceBookValue.value == ""){
		form1.reduceBookValue.value = "0";
		
	}
	form1.newBookValue.value=parseInt(form1.oldBookValue.value) + parseInt(form1.addBookValue.value)-parseInt(form1.reduceBookValue.value);		
	if(colName == 'addBookValue'){
			
			form1.newBookValue.value = parseInt(form1.oldBookValue.value) + parseInt(form1.addBookValue.value)-parseInt(form1.reduceBookValue.value);
			
		}else if(colName == 'reduceBookValue'){				
			
			form1.newBookValue.value = parseInt(form1.oldBookValue.value) +parseInt(form1.addBookValue.value)- parseInt(form1.reduceBookValue.value);
		
		}
		
		checkIsNan();
	
}
function checkIsNan(){
	
	if(isNaN(form1.oldBookValue.value) || form1.oldBookValue.value==""){				form1.oldBookValue.value = "0";}
	if(isNaN(form1.addBookValue.value) || form1.addBookValue.value==""){				form1.addBookValue.value = "0";}		
	if(isNaN(form1.reduceBookValue.value) || form1.reduceBookValue.value==""){			form1.reduceBookValue.value = "0";}
	if(isNaN(form1.newBookValue.value) || form1.newBookValue.value==""){				form1.newBookValue.value = "0";}
}
function popSysca_Code(popID,popIDName,typeName,codekindid,condition){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	
	// popSysca_Code.jsp有解碼
	typeName = encodeURI(typeName);
	returnWindow=window.open("../../home/popSysca_Code.jsp?popID="+popID+"&popIDName="+popIDName+"&typeName="+typeName+"&codekindid="+codekindid+"&condition="+condition,"",prop);
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
function queryPlaceName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../ch/queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			
		});	
}

function getNonexp() {
	var alertStr = "";
	alertStr += checkEmpty(form1.enterOrg, "入帳機關");
	alertStr += checkEmpty(form1.ownership, "權屬");
	alertStr += checkEmpty(form1.propertyNo, "財產編號");
	alertStr += checkEmpty(form1.serialNo, "財產分號");
	if (form1.propertyNo.value == "" || form1.serialNo.value == "") {
		form1.propertyNo.style.backgroundColor = "";
		form1.serialNo.style.backgroundColor = "";
	}
		
	if (alertStr.length != 0) {
		return false;
	} else {
		var senterOrg = form1.enterOrg.value;
		var sownership=form1.ownership.value;
		var sPropertyNo = form1.propertyNo.value;
		var sPropertyName = form1.propertyNoName.value;
		var sSerialNo = form1.serialNo.value;
		var sDifferenceKind = form1.differenceKind.value;
		var params = "";	
 
		params = "1&enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+sPropertyNo+"&propertyNoName="+encodeURIComponent(sPropertyName)+"&serialNo="+sSerialNo+"&differenceKind="+sDifferenceKind;

		//傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
		var randomnumber = Math.floor(Math.random() * 1000);
		params += "&randomnumber=" + randomnumber;
		var x = getRemoteData(getVirtualPath() + 'home/xmlUNTNENonexp.jsp', params);
		if (x != null && x.length > 0) {
			var json = jQuery.parseJSON(x); //因jQuery.parseJSON()可兼容ie, chrome, edge
			if (json != null && json.length) {
				for(var i = 0; i < json.length; i++) {
					form1.check.value = json[i].check;
					form1.propertyNo.value =  json[i].propertyNo;
					form1.propertyNoName.value =  json[i].propertyNoName;
					form1.serialNo.value =  json[i].serialNo;
					form1.lotNo.value =  json[i].lotNo;
					form1.material.value =  json[i].material;
					form1.otherMaterial.value =  json[i].otherMaterial;
					form1.propertyUnit.value =  json[i].propertyUnit;
					form1.otherPropertyUnit.value =  json[i].otherPropertyUnit;
					form1.limitYear.value =  json[i].limitYear;
					form1.otherLimitYear.value =  json[i].otherLimitYear;
					form1.propertyName1.value =  json[i].propertyName1;
					form1.buyDate.value =  json[i].buyDate;
					form1.propertyKind.value =  json[i].propertyKind;
					form1.fundType.value =  json[i].fundType;
					form1.valuable.value =  json[i].valuable;
					form1.oldBookValue.value =  json[i].bookValue;
					form1.bookAmount.value =  json[i].bookAmount;
					form1.sourceDate.value =  json[i].sourceDate;
					form1.keepUnit.value =  json[i].keepUnit;
					form1.keeper.value = json[i].keeper;
					form1.useUnit.value =  json[i].useUnit;
					form1.userNo.value =  json[i].userNo;
					form1.place.value =  json[i].place;
					form1.oldPropertyNo.value =  json[i].oldPropertyNo;
					form1.oldSerialNo.value =  json[i].oldSerialNo;
					form1.caseSerialNo.value =  json[i].caseSerialNo;
					form1.userNote.value =  json[i].userNote;
					form1.place1.value =  json[i].place1;
				}
			}
		}
	}
}

</script>  
</head> 
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" ><a href="#" onClick="return checkURL('untne024f.jsp');"><font color="red">*</font>增減值單資料</a></td>	
		<td ID=t2 CLASS="tab_border1" HEIGHT="25">*增減值單明細資料</td>	
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>	
	</tr>
</TABLE>
<!--隱藏欄位===========================================================-->
<input type="hidden" name="check" value="">
<input class="field_QRO" type="hidden" name="tOriginalUnit" size="10" value="<%=obj.getTOriginalUnit()%>">
<input class="field_QRO" type="hidden" name="bookValue" size="20" value="<%=obj.getBookValue()%>">
<input class="field_RO" type="hidden" name="valuable" value="<%=obj.getValuable()%>">
<input type="hidden" name="proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" name="proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" name="proofNo" value="<%=obj.getProofNo()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE024Q",obj);%>
	<jsp:include page="untne024q.jsp" />
	<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<tr style="display:none">
		<td class="td_form" width="15%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
	 </tr>
		<tr>
		<td class="td_form" width="15%">權屬：</td>
		<td class="td_form_white" colspan="3">		
			<select class="field_QRO" type="select" name="ownershipName">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
			 <!--  電腦單號：-->
			<input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">物品區分別：</td>
		<td colspan="3" class="td_form_white">
			<select class="field_QRO" type="select" name="differenceKind" onchange="changeDifferenceKind();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,obj.getDifferenceKind())%>
			</select>		
			&nbsp;&nbsp;&nbsp;		
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo()%>">]
       	</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input type="text" name="adjustDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getAdjustDate()%>">]
			&nbsp;　　　　　　　　　　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>	
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font> 物品編號：</td>
		<td colspan="3" class="td_form_white">
	  		<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','','NE');getNonexp();checkValue();"> 
	  		<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','6&isLookup=N')" value="..." title="物品編號輔助視窗">
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
	   		分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getNonexp();checkValue();">
	   		批號：[<input class="field_RO" type="text" name="lotNo" size="10" value="<%=obj.getLotNo()%>">]
	   	<br>
			別名：
			[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		&nbsp;原物品編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="12" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">主要材質：</td>
		<td class="td_form_white" colspan="3">
			主要材質： 
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
		<td class="td_form" width="15%">物品性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			　基金物品：
			<select class="field_RO" type="select" name="fundType">
				<%=util.View.getOption("select a.codeID, a.codeName from SYSCA_Code a , SYSCA_FUNDORGAN b where a.codeKindID='FUD' and a.codeid=b.fundno"  , obj.getFundType())%>
			</select>
<!-- 			　珍貴物品：
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
-->			
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">帳面資料：</td>
		<td class="td_form_white" colspan="3">		
			取得日期：[<input type="text" name="sourceDate" class="field_QRO"  size="10" maxlength="7" value="<%=obj.getSourceDate()%>">]
			&nbsp;　
			購置日期：[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;　
			帳面數量：[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">]
			&nbsp;<font color="red">*</font>增減值原因：
			<%=util.View._getSelectHTML("field_P", "cause", obj.getCause(),"AJC") %>
			　帳務摘要：<input class="field" type="text" name="bookNotes" size="18" maxlength="20" value="<%=obj.getBookNotes()%>">
		<br>
			原價值：[<input class="field_RO" type="text" name="oldBookValue" size="12" maxlength="15" value="<%=obj.getOldBookValue()%>">]
			&nbsp;增加價值：
			<input class="field" type="text" name="addBookValue" size="12" maxlength="15" value="<%=obj.getAddBookValue()%>" onchange="changeAdjustUnit(this.name);"">
			&nbsp;減少價值：
			<input class="field" type="text" name="reduceBookValue" size="12" maxlength="15" value="<%=obj.getReduceBookValue()%>" onchange="changeAdjustUnit(this.name);">
			&nbsp;新價值：[<input class="field_RO" type="text" name="newBookValue" size="12" maxlength="15" value="<%=obj.getNewBookValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_RO", "form1", "keepUnit", "keepUnitQuickly", obj.getKeepUnit()) %>
			<input class="field_RO" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_RO", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
		    <input class="field_RO" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'keeper')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
			                                                       "field_RO", "form1", "useUnit", "useUnitQuickly",  obj.getUseUnit()) %>
			<input class="field_RO" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_RO", "form1", "userNo", "userNoQuickly",obj.getUserNo()) %>
		    <input class="field_RO" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'userNo')" value="..." title="人員輔助視窗">
		    <br/>
			使用註記：
			<input type="text" class="field" name="userNote" value="<%=obj.getUserNote() %>" size="20">
			<br>
	                    存置地點
			<input class="field_Q" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>" onchange="queryPlaceName('enterOrg','place1');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'place1','place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name()%>">]
			<br>		
			存置地點說明
			<input class="field_Q" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">		
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form"style="display:none;">異動人員/日期：</td>
		<td class="td_form_white"style="display:none;">
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
	<input type="hidden" name="cannotVerify" value="<%=user.getCannotVerify()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">		
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountne026f();">&nbsp;|
	</span>
	<span id="batch">
	<input class="toolbar_default" type="button" followPK="false" name="batchOpen" value="增減值資料修改" onClick="gountne027f();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">序號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">增減值原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">增加價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">減少價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,false,false,true,true,false,true,false,false,false,false,false,false,false,true};
	boolean displayArray[] = {false,false,false,false,true,true,false,true,true,true,true,true,false,false,true,true,false,false};
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
			changeDifferenceKind();
			//setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
		case "insertError":
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			break;
		case "updateError":
			setFormItem("ownership,verify","R");
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
