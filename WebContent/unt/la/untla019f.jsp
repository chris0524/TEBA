<!-- 
程式目的：土地增減值作業－增減值單明細
程式代號：untla019f
程式日期：0940909
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA019F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA019F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("valueUnitClass".equals(obj.getState())) {
	obj.doValueUnitClass();
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}


unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._LA_ADJUST, 2);
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("adjustArea","0"),
	new Array("adjustHoldArea","0"),
	new Array("adjustBookUnit","0"),
	new Array("adjustBookValue","0")
);

//當增減值原因選「3.其他」時，開放其他說明欄位
//當增減值原因選「1.公告地價調整」、「2.資產重估調整」時，「新整筆面積、新權利範圍─分子、分母」，皆不可以開放
function changecause(){

    if (form1.cause.value=="4"){
	    setFormItem("newBookUnit","O");
	    setFormItem("newBookValue","O");
	    //form1.newBookValue.readOnly = true;
	    //form1.newBookValue.value="";
    }else if (form1.cause.value=="3"){
	    setFormItem("newBookUnit","O");
	    setFormItem("newBookValue","O");
	    
	}else{
		setFormItem("newBookUnit","R");
	}
	
	if (form1.cause.value=="3"){
		form1.newBookUnit.value="";
			
		form1.cause1.style.backgroundColor=editbg;
		form1.cause1.readOnly = false;
		form1.newArea.style.backgroundColor=editbg;
		form1.newArea.readOnly = false;
		form1.newHoldNume.style.backgroundColor=editbg;
		form1.newHoldNume.readOnly = false;
		form1.newHoldDeno.style.backgroundColor=editbg;
		form1.newHoldDeno.readOnly = false;
		form1.newArea.value="";
		form1.newHoldNume.value="";
		form1.newHoldDeno.value="";
		form1.newHoldArea.value="";
		
		//form1.newBookUnit.style.backgroundColor=editbg;
		//form1.newBookUnit.readOnly = false;
		form1.newBookValue.style.backgroundColor=editbg;
		form1.newBookValue.readOnly = false;
		
		//form1.newBookUnit.value=form1.oldBookUnit.value;
		form1.newBookValue.value=form1.oldBookValue.value;
	}else if(form1.cause.value=="4"){
	    form1.newArea.style.backgroundColor=editbg;
		form1.newArea.readOnly = false;
		form1.newHoldNume.style.backgroundColor=editbg;
		form1.newHoldNume.readOnly = false;
		form1.newHoldDeno.style.backgroundColor=editbg;
		form1.newHoldDeno.readOnly = false;
		form1.newArea.value="";
		form1.newHoldNume.value="";
		form1.newHoldDeno.value="";
		form1.newHoldArea.value="";
	}else{
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
		form1.newArea.style.backgroundColor=readbg;
		form1.newArea.readOnly = true;
		form1.newArea.value=form1.oldArea.value;
		form1.newHoldNume.style.backgroundColor=readbg;
		form1.newHoldNume.readOnly = true;
		form1.newHoldNume.value=form1.oldHoldNume.value;
		form1.newHoldDeno.style.backgroundColor=readbg;
		form1.newHoldDeno.readOnly = true;	
		form1.newHoldDeno.value=form1.oldHoldDeno.value;
		form1.newHoldArea.value=form1.oldHoldArea.value;
		
		//form1.newBookUnit.style.backgroundColor=editbg;
		form1.newBookUnit.readOnly = false;
		//form1.newBookValue.style.backgroundColor=editbg;
		//form1.newBookValue.readOnly = true;
		
		getBookData();
		getAreaData();
	}

}

function checkField(){
	var alertStr="";
	var changeItemStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untla018f.jsp";
		} else {
			form1.action = "untla019f.jsp";
		}
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
	
		if (form1.oldArea.value != form1.newArea.value ){changeItemStr += "整筆面積、" ;}
		if (form1.oldHoldNume.value != form1.newHoldNume.value ){changeItemStr += "權利分子、" ;}
		if (form1.oldHoldDeno.value != form1.newHoldDeno.value ){changeItemStr += "權利分母、" ;}
		if (form1.oldHoldArea.value != form1.newHoldArea.value ){changeItemStr += "權利面積、" ;}
		if (form1.oldBookUnit.value != form1.newBookUnit.value ){changeItemStr += "帳面單價、" ;}
		if (form1.oldBookValue.value != form1.newBookValue.value ){changeItemStr += "帳面總價" ;}
		form1.changeItem.value = changeItemStr;	
	
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkLen(form1.notes, "備註", 250);
		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			form1.signNo1.style.backgroundColor=errorbg;
			form1.signNo2.style.backgroundColor=errorbg;
			form1.signNo3.style.backgroundColor=errorbg;
			form1.signNo4.style.backgroundColor=errorbg;
			form1.signNo5.style.backgroundColor=errorbg;
			alertStr += "土地標示代碼不得為空白!\n";	
		} 			
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		
		alertStr += checkEmpty(form1.cause,"增減值原因");
		if (form1.cause.value=="3"){
			alertStr += checkEmpty(form1.cause1,"增減值原因-其他說明");
			if( form1.newArea.value == "0"){
				alertStr += "新整筆面積(㎡)不得為0";
			}
			if( form1.newHoldNume.value == "0"){
				alertStr += "新權利範圍─分子不得為0";
			}
			if( form1.newHoldDeno.value == "0"){
				alertStr += "新權利範圍─分母不得為0";
			}
		}else if(form1.cause.value=="1" || form1.cause.value=="2"){
			alertStr += checkEmpty(form1.bulletinDate,"公告年月");
		}
		
		if ((form1.cause.value=="2")&&(parseInt(form1.newBookValue.value) <= parseInt(form1.originalBV.value) )){ 
			alertStr += "新帳面總價不可低於原始入帳總價\n"; 
		} 
		
		alertStr += checkYYYMM(form1.bulletinDate,"公告年月"); 
		alertStr += checkDate(form1.adjustDate,"增減值日期"); 
		alertStr += checkEmpty(form1.newArea,"新整筆面積(㎡)"); 
		alertStr += checkFloat(form1.newArea,"新整筆面積(㎡)",9,2); 
		alertStr += checkEmpty(form1.newHoldNume,"新權利範圍─分子"); 
		alertStr += checkInt(form1.newHoldNume,"新權利範圍─分子"); 
		alertStr += checkEmpty(form1.newHoldDeno,"新權利範圍─分母"); 
		alertStr += checkInt(form1.newHoldDeno,"新權利範圍─分母"); 
		alertStr += checkEmpty(form1.newBookUnit,"新帳面單價"); 
		alertStr += checkFloat(form1.newBookUnit,"新帳面單價",13,2);
		//alertStr += checkInt(form1.newBookUnit,"新帳面單價");	 
		
		if (form1.cause.value=="1"){
			getBookData(); 
			getAreaData();
		}
		if (form1.cause.value=="2"){
			getBookData(); 
			getAreaData();
		}
		alertStr += checkFloat(form1.newHoldArea,"新權利範圍面積(㎡)",9,2); 
		//增減值同向檢查 
		if (( 
				(parseFloat(form1.oldArea.value) >= parseFloat(form1.newArea.value)) && 
				(parseFloat(form1.oldHoldArea.value) >= parseFloat(form1.newHoldArea.value)) && 
				(parseInt(form1.oldBookValue.value) >= parseInt(form1.newBookValue.value))
			) || 
			( 
				(parseFloat(form1.oldArea.value) <= parseFloat(form1.newArea.value)) && 
				(parseFloat(form1.oldHoldArea.value) <= parseFloat(form1.newHoldArea.value)) && 
				(parseInt(form1.oldBookValue.value) <= parseInt(form1.newBookValue.value))
			) ) { 
			alertStr = alertStr; 
		} else { 
			alertStr += "整筆面積、權利範圍面積、帳面單價，增減值不同向\n"; 
		}	
	} 
	if(alertStr.length!=0){ alert(alertStr); return false; } 
	beforeSubmit(); 
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,caseNo){ 
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
		if (surl=="untla018f.jsp"){	
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

function getAreaData() { 
	var holdArea; 
	holdArea = roundp((parseFloat(form1.newArea.value) * parseFloat(form1.newHoldNume.value) / parseFloat(form1.newHoldDeno.value)),2 ,"Y"); 
	form1.newHoldArea.value = holdArea; 
	form1.adjustArea.value = roundp((parseFloat(form1.newArea.value) - parseFloat(form1.oldArea.value)),2,"Y"); 
	form1.adjustHoldArea.value = roundp(( holdArea - parseFloat(form1.oldHoldArea.value)),2,"Y");
	if (form1.cause.value=="3"){
		if(form1.newHoldArea.value != ''){
			form1.newBookValue.value = form1.oldBookValue.value;
			form1.newBookUnit.value = roundp((parseInt(form1.newBookValue.value) / holdArea) ,2,"Y");
			form1.adjustBookValue.value = Math.round(form1.newBookValue.value - form1.oldBookValue.value);
			form1.adjustBookUnit.value = roundp((form1.newBookUnit.value - form1.oldBookUnit.value ),2,"Y");
		}
	}else if (form1.cause.value == "4"){
		if(form1.newHoldArea.value != ''){
			form1.newBookValue.value = form1.oldBookValue.value;
			form1.newBookUnit.value = roundp((parseInt(form1.newBookValue.value) / holdArea) ,2,"Y"); 
			form1.adjustBookValue.value = Math.round(form1.newBookValue.value - form1.oldBookValue.value);
			form1.adjustBookUnit.value = roundp((form1.newBookUnit.value - form1.oldBookUnit.value ),2,"Y");
		}					
	}	
} 

function getBookData(){ 
	var bookValue;		 
	bookValue = roundp((form1.newHoldArea.value * form1.newBookUnit.value),2,"Y");
	form1.newBookUnit.value = roundp(form1.newBookUnit.value,2,"Y");
	if (form1.cause.value=="3"){
		form1.newBookValue.value = Math.round(form1.newBookUnit.value * form1.newHoldArea.value );
		form1.adjustBookUnit.value = roundp((form1.newBookUnit.value - form1.oldBookUnit.value ),2,"Y"); 
		form1.adjustBookValue.value = Math.round(bookValue - form1.oldBookValue.value); 
	
	}else if (form1.cause.value=="4"){
		form1.newBookValue.value = Math.round(form1.newBookUnit.value * form1.newHoldArea.value );
		form1.adjustBookUnit.value = roundp((form1.newBookUnit.value - form1.oldBookUnit.value ),2,"Y");
		form1.adjustBookValue.value = Math.round(bookValue - form1.oldBookValue.value);
	}else{
		form1.newBookValue.value = Math.round(bookValue);
		form1.adjustBookUnit.value = roundp((form1.newBookUnit.value - form1.oldBookUnit.value ),2,"Y"); 
		form1.adjustBookValue.value = Math.round(bookValue - form1.oldBookValue.value); 
//		form1.adjustBookValue.value = roundp((bookValue - form1.oldBookValue.value ),2,"Y");
	}	
} 
function getBookData_1(){
	var bookUnit;
	bookUnit = roundp(form1.newBookValue.value / form1.newHoldArea.value);
	if(!form1.newHoldArea.value == '0' || !form1.newHoldArea.value == null ){
		bookUnit = roundp((form1.newBookValue.value/form1.newHoldArea.value),2,"Y");
	
		form1.newBookUnit.value = bookUnit; 
		form1.adjustBookUnit.value = roundp((form1.newBookUnit.value - form1.oldBookUnit.value ),2,"Y");
		form1.adjustBookValue.value = Math.round(form1.newBookValue.value - form1.oldBookValue.value); 
		//form1.adjustBookValue.value = roundp((form1.newBookValue.value - form1.oldBookValue.value ),2,"Y");
	}	 

} 
 
function init(){
	var arrField = new Array("update","delete");
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete", "R");
	}	
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut,batchAdjust1,batchAdjust2","R");
	}else{
		setFormItem("batchInsertBut,batchAdjust1,batchAdjust2","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {	
		setFormItem("insert,update,delete,batchInsertBut,batchAdjust1,batchAdjust2","R");
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
	
	form1.newBookUnit.style.backgroundColor=editbg;
	form1.newBookUnit.readOnly = true;
	form1.newBookValue.style.backgroundColor=editbg;
	form1.newBookValue.readOnly = true;
	
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
			setFormItem("signNo1,signNo2,signNo3,signNo4,signNo5","O");
		}else{
			form1.oldArea.value=roundp(form1.oldArea.value,2,"Y");
			form1.oldHoldArea.value=roundp(form1.oldHoldArea.value,2,"Y");
		}
		if(form1.check.value=="" && checkSignNo1.length!=0 && checkSignNo2.length!=0 && checkSignNo3.length!=0 && checkSignNo4.length!=0 && checkSignNo5.length!=0){
			alert("資料不存在，請重新輸入!!");
			form1.signNo4.value="";
			form1.signNo5.value="";
			setFormItem("propertyNo,serialNo","O");
		}else{
			form1.oldArea.value=roundp(form1.oldArea.value,2,"Y");
			form1.oldHoldArea.value=roundp(form1.oldHoldArea.value,2,"Y");
		}
}

function runValueUnit(){
	var alertStr = "";
	
	alertStr += checkEmpty(form1.q_bulletindate,"公告地價年度");
	if(alertStr.length!=0){
		alert(alertStr); 
		return false; 
	}else{
		form1.state.value = "valueUnitClass";
		beforeSubmit(); 
		form1.submit();
	}
	
}

//*********************************************
//呼叫調整用視窗函示
//*********************************************
function doChangeData(){
	var prop = "";    
	var str = "caseNo="+form1.caseNo.value+"&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value;
	
    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    prop=prop+'width=800,';
    prop=prop+'height=500';
  	window.open("untla021f.jsp?"+str,"exp",prop);
}
</script>  
</head> 

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位區============================================================-->
<div style="display:none"><table><tr><td >
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">		
	
<input type="hidden" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
</td></tr></table></div>

<!-- ==========Query區=========== -->
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<input type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
	<% request.setAttribute("UNTLA018Q",obj);%>
	<jsp:include page="untla018q.jsp" />
</div>

<!-- ==========公告地價調整視窗=========== -->
<div id="winValueUnit" style="position:absolute;z-index: 25;left:0;top :0;width:300px;height:80px;display:none">
	<iframe id="winValueUnitFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">公告地價調整視窗</div>
	<table class="queryTable"  border="1">
	<tr> 
		<td class="queryTDLable" width="45%">公告地價年度：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_bulletindate">
			 <%=util.View.getOption("select distinct bulletindate as a ,bulletindate as b from untla_value where enterorg='" + Common.esapi(obj.getEnterOrg()) + "' and ownership ='" + Common.esapi(obj.getOwnership()) + "' order by bulletindate desc" ,"1")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="button" name="querySubmit" value="確    定" onClick="runValueUnit();">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('winValueUnit');">
		</td>
	</tr>
	</table>
</div>

<!-- ==========頁籤區=========== -->
<table STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</table>

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="19%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;　　　　電腦單號：
				[<input type="text" name="caseNo" class="field_QRO" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">增減值日期：</td>
		<td class="td_form_white" colspan = "3">
			[<input type="text" name="adjustDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getAdjustDate()%>">]
			&nbsp;&nbsp;　
			入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;  
			月結：
			<select class="field_QRO" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		</td>	
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font> 財產編號：</td>
		<td colspan="3" class="td_form_white">
	  		<input class="field_P" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','1');getPropertyNo('PN');checkValue();"> 
	  		<input class="field_P" type="hidden" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyName','1')" value="..." title="財產編號輔助視窗">
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
	   		分號：
	   		<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getPropertyNo('PN');checkValue();">
	   	<br>
			別名：
			[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
			&nbsp;
			原財產編號：
			[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;
			原分號：
			[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">* </font>土地標示：</td>
		<td colspan="3" class="td_form_white">
			<select type="select" class="field_P" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getPropertyNo('SN');checkValue();">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>
			<select type="select" class="field_P" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getPropertyNo('SN');checkValue();">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>
			<select type="select" class="field_P" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>
			地號：
			<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onChange="getPropertyNo('SN');checkValue();">
			&nbsp;-
			<input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onChange="getPropertyNo('SN');checkValue();">
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD'", obj.getPropertyKind())%>
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
		<td class="td_form"><font color="red">*</font>增減值原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="cause" onchange="changecause()">
				<%=util.View.getTextOption("1;公告地價調整;2;資產重估調整;3;其它;4;面積調整",obj.getCause())%>
			</select>
			&nbsp;　　
			其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
			&nbsp;　
			公告年月：
			<input class="field" type="text" name="bulletinDate" size="5" maxlength="5" value="<%=obj.getBulletinDate()%>">
			<input class="field" type="button" name="btn_bulletinDate" onclick="popBulletinDate('bulletinDate','1','Y');" value="..." title="公告年月輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="td_form">原面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：
			[<input class="field_RO" type="text" name="oldArea" size="9" maxlength="9" value="<%=obj.getOldArea()%>">]
		<br>
			權利分子：
			[<input class="field_RO" type="text" name="oldHoldNume" size="10" maxlength="10" value="<%=obj.getOldHoldNume()%>">]
			&nbsp;
			權利分母：
			[<input class="field_RO" type="text" name="oldHoldDeno" size="10" maxlength="10" value="<%=obj.getOldHoldDeno()%>">]
			&nbsp;　
			權利面積(㎡)：
			[<input class="field_RO" type="text" name="oldHoldArea" size="9" maxlength="9" value="<%=obj.getOldHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">新面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：<input class="field" type="text" name="newArea" size="9" maxlength="9" value="<%=obj.getNewArea()%>" onChange="getAreaData();" > 
		<br>
			權利分子：<input class="field" type="text" name="newHoldNume" size="11" maxlength="10" value="<%=obj.getNewHoldNume()%>" onChange="getAreaData();" > 
			&nbsp;權利分母：<input class="field" type="text" name="newHoldDeno" size="10" maxlength="10" value="<%=obj.getNewHoldDeno()%>" onChange="getAreaData();" > 
			&nbsp;&nbsp;&nbsp;&nbsp;　權利面積(㎡)：[<input class="field_RO" type="text" name="newHoldArea" size="9" maxlength="9" value="<%=obj.getNewHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">增減面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：[<input class="field_RO" type="text" name="adjustArea" size="9" maxlength="9" value="<%=obj.getAdjustArea()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　　　　　
			權利面積(㎡)：[<input class="field_RO" type="text" name="adjustHoldArea" size="9" maxlength="9" value="<%=obj.getAdjustHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan = "3">
			財產取得日期：
			[<input type="text" name="sourceDate" class="field_QRO"  size="10" maxlength="7" value="<%=obj.getSourceDate()%>">]
		<br>
			原始入帳總價：
			[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">]
		<br>
			原帳面單價：
			[<input class="field_RO" type="text" name="oldBookUnit" size="13" maxlength="16" value="<%=obj.getOldBookUnit()%>">]
			&nbsp; &nbsp;　　　
			原帳面總價：
			[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">]
		<br>
			新帳面單價：
			<input class="field" type="text" name="newBookUnit" size="13" maxlength="16" value="<%=obj.getNewBookUnit()%>"  onChange="getBookData();" >
			新帳面總價：
			<input class="field" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>" onChange="getBookData_1();">
		<br>
			增減帳面單價：
			[<input class="field_RO" type="text" name="adjustBookUnit" size="13" maxlength="16" value="<%=obj.getAdjustBookUnit()%>">]
			&nbsp; &nbsp;　
			增減帳面總價：
			[<input class="field_RO" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>">]
		<br>
			帳務摘要：
			<input class="field" type="text" name="bookNotes" size="23" maxlength="20" value="<%=obj.getBookNotes()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">變更事項：</td>
		<td class="td_form_white" colspan="3">	
			[<input class="field_RO" type="text" name="changeItem" size="60" maxlength="30" value="<%=obj.getChangeItem()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">其它事項：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field" type="text" name="notes1" size="60" maxlength="60" value="<%=obj.getNotes1()%>">
			<input class="field" type="hidden" name="useSeparate" size="10" maxlength="10" value="<%=obj.getUseSeparate()%>">
			<input class="field" type="hidden" name="getUseKind" size="10" maxlength="10" value="<%=obj.getUseKind()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white" width="35%">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form" width="22%">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanValueUnit">
<!-- 	
		<input class="toolbar_default" type="button" followPK="false" name="buttonValueUnit" value="公告地價調整" onClick="queryShow('winValueUnit');">&nbsp;|
-->	
		<input class="toolbar_default" type="button" followPK="false" name="buttonValueUnit" value="公告地價調整" onClick="doChangeData();">&nbsp;|
		
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增減值原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">增減權利面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">增減總價</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,false,false,false,false,false,false,false,false,false,false,true};
	boolean displayArray[] = {false,true,false,true,false,false,true,true,false,false,false,false,true,false,false,true,false};
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
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true){
			}else{
				form1.querySelect[0].checked=true;
			}
			
			if (parse(form1.q_enterOrg.value).length<=0){
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
			break;				
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	
	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			setFormItem("newArea,newHoldNume,newHoldDeno,cause1","R");

			break;
		case "insertError":
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			setFormItem("newArea,newHoldNume,newHoldDeno,cause1","R");
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			if(form1.cause.value=="3"){
				setFormItem("newArea,newHoldNume,newHoldDeno","O");
			}else if(form1.cause.value=="4"){
	           setFormItem("newArea,newHoldNume,newHoldDeno","O");
			}else{
				setFormItem("newArea,newHoldNume,newHoldDeno,cause1","R");
			}
			
			if (form1.cause.value=="1" || form1.cause.value=="4"){
			    setFormItem("newBookUnit","O");
			}else{
				setFormItem("newBookUnit","R");
			}
			
			if (form1.cause.value=="3"){
				form1.newBookValue.style.backgroundColor=editbg;
				form1.newBookValue.readOnly = false;
			}else if (form1.cause.value=="4"){
				form1.newBookValue.style.backgroundColor=editbg;
				form1.newBookValue.readOnly = false;
				
			}else{

				form1.newBookValue.style.backgroundColor=editbg;
				form1.newBookValue.readOnly = true;
			}
			break;
		case "updateError":
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			if(form1.cause.value=="3"){
				setFormItem("newArea,newHoldNume,newHoldDeno","O");
			}else if(form1.cause.value=="4"){
	            setFormItem("newArea,newHoldNume,newHoldDeno","O");
			}else{
				setFormItem("newArea,newHoldNume,newHoldDeno,cause1","R");
			}
			
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
	
	if(form1.caseNo.value == "" || form1.verify.value == "Y"){
		setFormItem("buttonValueUnit","R");
	}else{
		setFormItem("buttonValueUnit","O");
	}
}
</script>
</body>
</html>
