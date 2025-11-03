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
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU022F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.bu.UNTBU022F)obj.queryOne();	
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
String tabs = uch._createTabData(uch._BU_ADJUST, 2);
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
insertDefault = new Array(
	new Array("adjustCArea","0"),
	new Array("adjustSArea","0"),
	new Array("adjustArea","0"),
	new Array("adjustHoldArea","0"),
	new Array("adjustBookValue","0")
);
function changecause(){
//當增減值原因選「3.其他」時，開放其他說明欄位
    if (form1.cause.value=="2"){
		form1.cause2.disabled = false;
	}else{
		form1.cause2.disabled = true;
	}
	if (form1.cause.value=="3"){
		form1.cause1.disabled = false;
	}else{
		form1.cause1.disabled = true;
	}
//當增減值原因選「1.資產重估調整」時，「增減主建物面積、增減附屬建物面積、新權利範圍─分子、分母」，皆不可以開放
	if (form1.cause.value=="1"){
		form1.adjustCArea.value="0";
		form1.adjustCArea.style.backgroundColor=readbg;
		form1.adjustCArea.readOnly = true;
		form1.adjustSArea.value="0";
		form1.adjustSArea.style.backgroundColor=readbg;
		form1.adjustSArea.readOnly = true;
		//form1.newHoldNume.value="";
		form1.newHoldNume.style.backgroundColor=readbg;
		form1.newHoldNume.readOnly = true;
		//form1.newHoldDeno.value="";
		form1.newHoldDeno.style.backgroundColor=readbg;
		form1.newHoldDeno.readOnly = true;
	}else{
		form1.adjustCArea.style.backgroundColor=editbg;
		form1.adjustCArea.readOnly = false;
		form1.adjustSArea.style.backgroundColor=editbg;
		form1.adjustSArea.readOnly = false;
		form1.newHoldNume.style.backgroundColor=editbg;
		form1.newHoldNume.readOnly = false;
		form1.newHoldDeno.style.backgroundColor=editbg;
		form1.newHoldDeno.readOnly = false;
	}
	//當增減值原因由 2 or 3 改成 1 時,則預設增減的各欄位 = 0 ; new 的各欄位 = old
	//if (!( form1.cause.value=="1" && form1.oldcause.value == "1" )){
	if (( form1.cause.value=="1") && (!form1.oldcause.value == "1" )&& (!form1.oldcase.value == "0")){
		form1.adjustCArea.value = "0";
		form1.adjustSArea.value = "0";
		form1.adjustArea.value = "0";
		form1.adjustHoldArea.value = "0";
		form1.adjustBookValue.value = "0";
		
		form1.newCArea.value = form1.oldCArea.value;
		form1.newSArea.value = form1.oldSArea.value;
		form1.newArea.value = form1.oldArea.value;
		form1.newHoldNume.value = form1.oldHoldNume.value;
		form1.newHoldDeno.value = form1.oldHoldDeno.value;
		form1.newHoldArea.value = form1.oldHoldArea.value;
		form1.newBookValue.value = form1.oldBookValue.value;
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untbu021f.jsp";
		} else {
			form1.action = "untbu022f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkLen(form1.notes, "備註", 250);
/**		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			form1.signNo1.style.backgroundColor=errorbg;
			form1.signNo2.style.backgroundColor=errorbg;
			form1.signNo3.style.backgroundColor=errorbg;
			form1.signNo4.style.backgroundColor=errorbg;
			form1.signNo5.style.backgroundColor=errorbg;
			alertStr += "建物標示代碼不得為空白!\n";
		} 	
**/				
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.cause,"增減值原因");
		
		//當增減值原因選「1.資產重估調整」時，「新帳面金額─總價 不可小於 原始入帳金額─總價」
		if ((form1.cause.value=="1")&&(parseInt(form1.newBookValue.value) < parseInt(form1.originalBV.value) )){
			alertStr += "新帳面金額─總價不可低於原始入帳─總價\n"; 
		}
		//if ((form1.adjustType.value=="2")&&(parseInt(form1.oldCArea.value)==0)&&(parseInt(form1.adjustCArea.value)!=0)){
		//	alertStr += "增減別為減少時，若「原主建物面積」為0，則「增減主建物面積」必須為0。\n"; 
		//}else if ((form1.adjustType.value=="2")&&(parseInt(form1.oldCArea.value)!=0)&&( parseFloat(form1.adjustCArea.value) >= parseFloat(form1.oldCArea.value) )){
		//	alertStr += "增減別為減少時，增減主建物面積必須小於原主建物面積\n"; 
		//}
		
		//if ((form1.adjustType.value=="2")&&( parseFloat(form1.adjustSArea.value) > parseFloat(form1.oldSArea.value) )){ 
		//	alertStr += "增減別為減少時，增減附屬建物面積必須小於等於原附屬建物面積\n"; 
		//}
		
		//if ((form1.adjustType.value=="2")&&( form1.valuable.value == "N" )&&( parseInt(form1.adjustBookValue.value) >= parseInt(form1.oldBookValue.value) )){ 
			//alertStr += "增減別為減少時，增減帳面金額─總價必須小於原帳面金額─總價\n";    
		//}
		
		//if ((form1.adjustType.value=="2")&&( form1.valuable.value == "Y" )&&( parseInt(form1.adjustBookValue.value) > parseInt(form1.oldBookValue.value) )){ 
		//	alertStr += "增減別為減少時，增減帳面金額─總價必須小於等於原帳面金額─總價\n"; 
		//}

		if(form1.adjustType.value=="2"){
		    if(((form1.fundType.value != "") || (form1.fundType.value != null)) && (parseInt(form1.newBookValue.value) > parseInt(form1.oldBookValue.value)) ){
		        alertStr += "增減別為減少,又為基金類別時，增減帳面金額─總價必須小於等於原帳面金額─總價\n";
		    }else if((form1.valuable.value == "Y") && (parseInt(form1.newBookValue.value) > parseInt(form1.oldBookValue.value)) ){
		        alertStr += "增減別為減少,又為珍貴財產時，增減帳面金額─總價必須小於等於原帳面金額─總價\n";
		    }else if(parseInt(form1.newBookValue.value) > parseInt(form1.oldBookValue.value)){
		        
		        alertStr += "增減別為減少時,增減帳面金額─總價必須小於原帳面金額─總價\n";
		    }

		    if(form1.fundType.value == "" && (form1.valuable.value == "N" || form1.valuable.value == "")  && (parseInt(form1.newBookValue.value)<=0)){
			    alertStr += "增減別為減少,又不為珍貴財產或基金類別時,新價值必須大於0\n";
		    }else if((form1.fundType.value != "" || form1.valuable.value == "Y") && (parseInt(form1.newBookValue.value)<0)){
		        alertStr += "增減別為減少又為珍貴財產或基金類別時,新價值必須大等於0\n";
		    }
		}
		
		
		alertStr += checkEmpty(form1.adjustType,"增減別");
		alertStr += checkEmpty(form1.adjustCArea,"增減主建物面積(㎡)"); 
		alertStr += checkFloat(form1.adjustCArea,"增減主建物面積(㎡)",9,2); 
		alertStr += checkEmpty(form1.adjustSArea,"增減附屬建物面積(㎡)"); 
		alertStr += checkFloat(form1.adjustSArea,"增減附屬建物面積(㎡)",9,2); 
		alertStr += checkEmpty(form1.adjustBookValue,"增減帳面金額─金額"); 
		
		if ( parseInt(form1.newHoldNume.value) <= 0 ) alertStr += "新權利範圍─分子不可小於等於0\n";
		if ( parseInt(form1.newHoldDeno.value) <= 0 ) alertStr += "新權利範圍─分母不可小於等於0\n";
		
		if ( parseInt(form1.newHoldNume.value) > parseInt(form1.newHoldDeno.value) ) alertStr += "新權利範圍─分子不可大於新權利範圍─分母\n";
		
		alertStr += checkEmpty(form1.newHoldNume,"新權利範圍─分子"); 
		alertStr += checkInt(form1.newHoldNume,"新權利範圍─分子"); 
		alertStr += checkEmpty(form1.newHoldDeno,"新權利範圍─分母"); 
		alertStr += checkInt(form1.newHoldDeno,"新權利範圍─分母");
		
		if(form1.cause.value == "2"){
            alertStr+= checkEmpty(form1.cause2,"「增減值原因」為「整建」選項時\n「整建說明」"); 
        }
        if(form1.cause.value == "3"){
            alertStr+= checkEmpty(form1.cause1,"「增減值原因」為「其它」選項時\n「其它說明」");
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
		if (surl=="untbu021f.jsp"){	
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

function gountbu023f(){
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
	var adjustDate = form1.adjustDate.value; 
	beforeSubmit();
	returnWindow=window.open("untbu023f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_adjustDate="+adjustDate,"aha",prop);	
}

function getAreaData() { 
	form1.adjustArea.value = roundp(parseFloat(form1.adjustCArea.value)+parseFloat(form1.adjustSArea.value),2,"Y" );
	if ( form1.adjustType.value == "1" ) {
		form1.newCArea.value = roundp(parseFloat(form1.oldCArea.value) + parseFloat(form1.adjustCArea.value),2,"Y") ;
		form1.newSArea.value = roundp(parseFloat(form1.oldSArea.value) + parseFloat(form1.adjustSArea.value),2,"Y") ;
		form1.newArea.value = roundp(parseFloat(form1.oldArea.value) + parseFloat(form1.adjustArea.value),2,"Y") ;
		form1.newBookValue.value = roundp(parseFloat(form1.oldBookValue.value) + parseFloat(form1.adjustBookValue.value),0,"Y") ;
		form1.newHoldArea.value = roundp(( form1.newArea.value * ( form1.newHoldNume.value / form1.newHoldDeno.value )),2,"Y" );
		form1.adjustHoldArea.value = roundp(parseFloat(form1.newHoldArea.value) - parseFloat(form1.oldHoldArea.value),2,"Y") ;
	} else {
		form1.newCArea.value = roundp(parseFloat(form1.oldCArea.value) - parseFloat(form1.adjustCArea.value),2,"Y") ;
		form1.newSArea.value = roundp(parseFloat(form1.oldSArea.value) - parseFloat(form1.adjustSArea.value),2,"Y") ;
		form1.newArea.value = roundp(parseFloat(form1.oldArea.value) - parseFloat(form1.adjustArea.value),2,"Y") ;
		form1.newBookValue.value = roundp(parseFloat(form1.oldBookValue.value) - parseFloat(form1.adjustBookValue.value),0,"Y") ;
		form1.newHoldArea.value = roundp(( form1.newArea.value * ( form1.newHoldNume.value / form1.newHoldDeno.value )),2,"Y" );
		form1.adjustHoldArea.value = roundp(parseFloat(form1.oldHoldArea.value) - parseFloat(form1.newHoldArea.value),2,"Y") ;
	}
} 

function getDefault(){
if(form1.propertyNo.value!="" && form1.serialNo.value!=""){
	form1.oldCArea.value = form1.CArea.value;
	form1.oldSArea.value = form1.SArea.value;
	form1.oldArea.value = form1.area.value;
	form1.oldHoldNume.value = form1.holdNume.value;
	form1.oldHoldDeno.value = form1.holdDeno.value;
	form1.oldHoldArea.value = form1.holdArea.value;
	form1.oldBookValue.value = form1.bookValue.value;
	
	form1.newCArea.value = form1.oldCArea.value;
	form1.newSArea.value = form1.oldSArea.value;
	form1.newArea.value = form1.oldArea.value;
	form1.newHoldNume.value = form1.oldHoldNume.value;
	form1.newHoldDeno.value = form1.oldHoldDeno.value;
	form1.newHoldArea.value = form1.oldHoldArea.value;
	form1.newBookValue.value = form1.oldBookValue.value; 
	}
}

function init(){
	form1.oldcause.value="0";
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

if(form1.propertyNo.value!="" && form1.serialNo.value!=""){
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
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function checkData( v ){
	if(eval(v.value) < 0 ) {
		v.value = "0";
		alert("增減數值不得小於零");
	}
}

</script>  
</head> 
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="check" value="">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>

<!--Query區============================================================-->
<div id="queryContainer" style="width:770px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=obj.getQueryTab1()%>>
			&nbsp;<font color="green">查詢增減值單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="detail" <%=obj.getQueryTab2()%>>
			&nbsp;<font color="green">查詢增減值單明細資料</font>
		</td>
	</tr>				
	<tr>
		<td class="queryTDLable" width="16%">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
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
		<td class="queryTDLable">增減值日期：</td>
		<td class="queryTDInput">
			起 <%=util.View.getPopCalndar("field_Q","q_adjustDateS",obj.getQ_adjustDateS())%>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_adjustDateE",obj.getQ_adjustDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"2")%>
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
		<td class="queryTDLable">增減值原因：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_cause" >
				<%=util.View.getTextOption("1;資產重估調整;2;整建;3;其它",obj.getQ_cause())%>
			</select>			
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
		<td class="queryTDLable">增減值單編號：</td>
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
		<input class="field_QRO" type="HIDDEN" name="oldcause" size="1" value="<%=obj.getCause()%>">
		<input class="field_QRO" type="HIDDEN" name="CArea" size="20" value="<%=obj.getCArea()%>">
		<input class="field_QRO" type="HIDDEN" name="SArea" size="20" value="<%=obj.getSArea()%>">
		<input class="field_QRO" type="HIDDEN" name="area" size="20" value="<%=obj.getArea()%>">
		<input class="field_QRO" type="HIDDEN" name="holdNume" size="20" value="<%=obj.getHoldNume()%>">
		<input class="field_QRO" type="HIDDEN" name="holdDeno" size="20" value="<%=obj.getHoldDeno()%>">
		<input class="field_QRO" type="HIDDEN" name="holdArea" size="20" value="<%=obj.getHoldArea()%>">
		<input class="field_QRO" type="HIDDEN" name="bookValue" size="20" value="<%=obj.getBookValue()%>">
		<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" value="<%=obj.getEnterOrg()%>">
	<tr>
		<td class="td_form" width="16%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" value="<%=obj.getEnterOrgName()%>">]
		&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		　　　　&nbsp;&nbsp;電腦單號：
			[<input type="text" name="caseNo" class="field_QRO" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">工程編號：</td>
		<td colspan="3" class="td_form_white">
       		[<input class="field_PRO" type="text" name="engineering" size="10" maxlength="11" value="" onBlur="">]
			[<input class="field_PRO" type="text" name="engineeringName" size="20" maxlength="50" value="">]
			&nbsp;&nbsp;&nbsp;&nbsp;
			序號
			<input class="field" type="text" name="" size="10" maxlength="50" value="">
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
	  		<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','2');getUNTBUBuilding('PN');getDefault();checkValue();"> 
	 		<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyName','2')" value="..." title="財產編號輔助視窗">
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
	   		分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getUNTBUBuilding('PN');getDefault();checkValue();">
	   	<br>
		別名：
			[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>	 
	</tr>	
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="material" size="20" value="">] 
			其他主要材質： 
			[<input class="field_RO" type="text" name="otherMaterial" size="25"	maxlength="50" value="">]
			<br>
			單位：
			[<input class="field_RO" type="text" name="propertyUnit" size="7" maxlength="7" value="">]
			&nbsp;&nbsp;&nbsp;
			其他單位： 
			[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="">]
			<br>
			使用年限： 
			[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="7" value="">]
			&nbsp;&nbsp;&nbsp;
			調整後年限(月)：
			[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="">]
		</td>
	</tr>
	<tr>
		<td class="td_form">土地標示：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="signLaNo1" onChange="changeSignNo1('signLaNo1','signLaNo2','signLaNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", "")%>
			</select>
			<select class="field" type="select" name="signLaNo2" onChange="changeSignNo2('signLaNo1','signLaNo2','signLaNo3','');">
				<script>changeSignNo1('signLaNo1','signLaNo2','signLaNo3', "");</script>
			</select>
			<select class="field" type="select" name="signLaNo3">
				<script>changeSignNo2('signLaNo1','signLaNo2','signLaNo3', "");</script>
			</select>　
			地號：		
			<input class="field" type="text" name="signLaNo4" size="4" maxlength="4" value="<%=obj.getSignLaNo4() %>"> -
			<input class="field" type="text" name="signLaNo5" size="4" maxlength="4" value="<%=obj.getSignLaNo5() %>">
		</td>	
	</tr>
	<tr>
	  <td class="td_form">建物標示：</td>
	  <td colspan="3" class="td_form_white">
	  	<select  type="select" class="field_P" name="signBuNo1" onChange="changeSignNo1('signBuNo1','signBuNo2','signBuNo3','');getUNTBUBuilding('SN');getDefault();checkValue();">
        	<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignBuNo1())%>
      	</select>
        <select  type="select" class="field_P" name="signBuNo2" onChange="changeSignNo2('signBuNo1','signBuNo2','signBuNo3','');getUNTBUBuilding('SN');getDefault();checkValue();">
          <script>changeSignNo1('signBuNo1','signBuNo2','signBuNo3','<%=obj.getSignBuNo2()%>');</script>
        </select>
        <select  type="select" class="field_P" name="signBuNo3">
          <script>changeSignNo2('signBuNo1','signBuNo2','signBuNo3','<%=obj.getSignBuNo3()%>');</script>
        </select>
		　建號：
		<input class="field_P" type="text" name="signBuNo4" size="5" maxlength="5" value="<%=obj.getSignBuNo4()%>" onChange="getUNTBUBuilding('SN');getDefault();checkValue();">
		&nbsp;-
		<input class="field_P" type="text" name="signBuNo4" size="3" maxlength="3" value="<%=obj.getSignBuNo5()%>" onChange="getUNTBUBuilding('SN');getDefault();checkValue();">
	  </td>
	</tr>
	<tr>
		<td class="td_form">財產性質： </td>
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
		<td class="td_form"><font color="red">*</font>增減值原因：</td>
		<td class="td_form_white" colspan = "3">
			<select class="field" type="select" name="cause" onchange="changecause();">
				<%=util.View.getTextOption("1;資產重估調整;2;整建;3;其它",obj.getCause())%>
			</select>
			&nbsp;整建說明：<input class="field" type="text" name="cause2" size="20" maxlength="20" value="<%=obj.getCause2()%>">
			&nbsp;其他說明：<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
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
		<td class="td_form">原面積：</td>
		<td class="td_form_white" colspan="3">	
			主面積(㎡)：[<input class="field_RO" type="text" name="oldCArea" size="9" maxlength="9" value="<%=obj.getOldCArea()%>" onChange="getAreaData();">]
			&nbsp;附屬面積(㎡)：[<input class="field_RO" type="text" name="oldSArea" size="9" maxlength="9" value="<%=obj.getOldSArea()%>" onChange="getAreaData();">]
			&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="oldArea" size="9" maxlength="9" value="<%=obj.getOldArea()%>" onChange="getAreaData();">]
		<br>
			權利分子：[<input class="field_RO" type="text" name="oldHoldNume" size="10" maxlength="10" value="<%=obj.getOldHoldNume()%>" onChange="getAreaData();">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="oldHoldDeno" size="10" maxlength="10" value="<%=obj.getOldHoldDeno()%>" onChange="getAreaData();">]
			&nbsp;　權利面積(㎡)：[<input class="field_RO" type="text" name="oldHoldArea" size="9" maxlength="9" value="<%=obj.getOldHoldArea()%>" onChange="getAreaData();">]
		</td>
	</tr>
	<tr>
		<td class="td_form">增減面積：</td>
		<td class="td_form_white" colspan="3">		
			<font color="red">*</font>主面積(㎡)：
			<input class="field" type="text" name="adjustCArea" size="9" maxlength="9" value="<%=obj.getAdjustCArea()%>" onChange="checkData(this);getAreaData();" >
			<font color="red">*</font>附屬面積(㎡)：
			<input class="field" type="text" name="adjustSArea" size="9" maxlength="9" value="<%=obj.getAdjustSArea()%>"  onChange="checkData(this);getAreaData();" >
			&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="adjustArea" size="9" maxlength="9" value="<%=obj.getAdjustArea()%>" onChange="getAreaData();" >]
		<br>
			權利面積(㎡)：[<input class="field_RO" type="text" name="adjustHoldArea" size="9" maxlength="9" value="<%=obj.getAdjustHoldArea()%>" onChange="getAreaData();" >]
		</td>
	</tr>
	<tr>
		<td class="td_form">新面積：</td>
		<td class="td_form_white" colspan="3">		
			主面積(㎡)：[<input class="field_RO" type="text" name="newCArea" size="9" maxlength="9" value="<%=obj.getNewCArea()%>" onChange="getAreaData();">]
			&nbsp;附屬面積(㎡)：[<input class="field_RO" type="text" name="newSArea" size="9" maxlength="9" value="<%=obj.getNewSArea()%>" onChange="getAreaData();">]
			&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="newArea" size="9" maxlength="9" value="<%=obj.getNewArea()%>" onChange="getAreaData();" >]
		<br>
			<font color="red">*</font>權利分子：
			<input class="field" type="text" name="newHoldNume" size="10" maxlength="10" value="<%=obj.getNewHoldNume()%>" onChange="getAreaData();" > 
			&nbsp;<font color="red">*</font>權利分母：
			<input class="field" type="text" name="newHoldDeno" size="10" maxlength="10" value="<%=obj.getNewHoldDeno()%>" onChange="getAreaData();" >
			　權利面積(㎡)：[<input class="field_RO" type="text" name="newHoldArea" size="9" maxlength="9" value="<%=obj.getNewHoldArea()%>" onChange="getAreaData();">]
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
			財產取得日期：
			[<input type="text" name="sourceDate" class="field_QRO"  size="10" maxlength="7" value="<%=obj.getSourceDate()%>">]
			<br>
			原始入帳價值：[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">]
			&nbsp;　　　
			原帳面價值：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">]
		<br>
			<font color="red">*</font>
			增減帳面價值：<input class="field" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>" onChange="checkData(this);getAreaData();">
			&nbsp;
			帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
		<br>
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
	<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountbu023f();">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱/土地建物標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增減值原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">增減數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">增減總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">帳務摘要</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,true,false,false,false,true,false,true,false,false,false,false,false,true,true,true,false,false,true};
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
			setFormItem("batchInsertBut","R");
			form1.cause2.disabled = true;
			form1.cause1.disabled = true;
			
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "insertError":
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			form1.cause2.disabled = true;
			form1.cause1.disabled = true;
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			
			if(form1.cause.value == "3"){
			    form1.cause1.disabled = false;
			}else{
			    form1.cause1.disabled = true;
			}
			
			if(form1.cause.value == "2"){
			    form1.cause2.disabled = false;	
			}else{
			    form1.cause2.disabled = true;
			}
			
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "updateError":
			setFormItem("ownership,verify","R");
			setFormItem("batchInsertBut","R");
			
			if(form1.cause.value == "3"){
			    form1.cause1.disabled = false;
			}else{
			    form1.cause1.disabled = true;
			}
			
			if(form1.cause.value == "2"){
			    form1.cause2.disabled = false;
			}else{
			    form1.cause2.disabled = true;
			}
			
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
	
		//取消時要執行的動作
		case "clear":
			setFormItem("batchInsertBut","O");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "clearError":
			setFormItem("batchInsertBut","O");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("batchInsertBut","O");
			if(form1.verify.value=="Y"){
		        setFormItem("batchInsertBut","R");
	        }else{
	            setFormItem("batchInsertBut","O");
	        }
			break;
		case "confirmError":
			setFormItem("batchInsertBut","O");
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
