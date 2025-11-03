<!--
程式目的：財產增減值單基本資料
程式代號：untch003f02
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
Jim.Lin    1050429     新增增減值資料修改按鈕
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH003F02">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setKeeperno(user.getKeeperno());
obj.setUnitID(user.getUnitID());
obj.setOrganID(user.getOrganID());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	
}else if ("queryOne".equals(obj.getState())) {	obj._execQueryOneforDetail();
}else if ("insert".equals(obj.getState())) {	obj._execInsertforDetail();
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("update".equals(obj.getState())) {	obj._execUpdateforDetail();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("delete".equals(obj.getState())) {	obj._execDeleteforDetail();
												obj.setPropertyNo("");
												obj.setSerialNo("");
												obj.setLotNo("");
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.execQueryAll();

	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
			
		}else{
			if("".equals(obj.getSerialNo()) && "".equals(obj.getLotNo())){		
				obj.setEnterOrg(((String[])objList.get(0))[0]);
				obj.setOwnership(((String[])objList.get(0))[1]);
				obj.setCaseNo(((String[])objList.get(0))[2]);
				obj.setDifferenceKind(((String[])objList.get(0))[3]);
				obj.setPropertyNo(((String[])objList.get(0))[5]);
				obj.setSerialNo(((String[])objList.get(0))[6]);
				obj.setLotNo(((String[])objList.get(0))[7]);					
			}
			obj._execQueryOneforDetail();
		}
	}
}


unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
uch.setPropertyNoType(obj.getPropertyNo());
String tabs = uch._createTabData(uch._CH_ADJUST, 2);
%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js" ></script>
<script type="text/javascript" src="../../js/function.js?v=1" ></script>
<script type="text/javascript" src="../../js/tablesoft.js" ></script>
<script type="text/javascript" src="../../js/sList2.js" ></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("adjustLaArea","0"),
	new Array("adjustLaHoldArea","0"),
	new Array("adjustBuCArea","0"),
	new Array("adjustBuSArea","0"),
	new Array("adjustBuArea","0"),
	new Array("adjustBuHoldArea","0"),
	new Array("adjustBookValue","0"),
	new Array("adjustBookUnit","0"),
	new Array("adjustMeasure","0"),
	new Array("addbookvalue","0"),
	new Array("reducebookvalue","0"),
	new Array("oldAccumDepr","0"),
	new Array("addNetValue","0"),
	new Array("reduceAccumDepr","0"),
	new Array("reduceNetValue","0"),
	new Array("newBookValue","0"),
	//new Array("newAccumDepr","0"),
	new Array("newNetValue","0"),	
	new Array("bookAmount","0"),
	new Array("newMeasure","0"),
	new Array("addMeasure","0"),
	new Array("reduceMeasure","0"),
	new Array("addBookAmount","0"),
	new Array("reduceBookAmount","0"),
	new Array("newBookAmount","0")
	//new Array("newDeprMethod","01")	
);

function checkField(){
	try {
		var alertStr="";
		if(form1.state.value=="queryAll"){
			alertStr += checkQuery();
			if (document.all.querySelect[0].checked) {
				form1.action = "untch003f01.jsp";
			} else {
				form1.action = "untch003f02.jsp";
			}
		}else if (form1.state.value=="insert"||form1.state.value=="update"){
						
			alertStr += checkEmpty(form1.propertyNo,"財產編號");
			alertStr += checkEmpty(form1.serialNo,"財產分號");		
			alertStr += checkEmpty(form1.cause,"增減值原因");
			
			if(form1.newDeprMethod.value == '02'){
				alertStr += checkEmpty(form1.newDeprPark,"園區別");
				
				if($("input[name='cb_newDeprUnitCB']").attr('checked') == 'checked'){
					
				}else{
					alertStr += checkEmpty(form1.newDeprUnit,"部門別");
					alertStr += checkEmpty(form1.newDeprUnit1,"部門別單位");
					alertStr += checkEmpty(form1.newDeprAccounts,"會計科目");					
				}
			}
			
			var checkStr1 = form1.propertyNo.value.substring(0,1);
			var checkStr3 = form1.propertyNo.value.substring(0,3);
			
			if(checkStr3 == '111'){
				alertStr += checkQuotation(form1.notes,"備註");
			}else if(checkStr1 == '1'){
				alertStr += checkQuotation(form1.notes,"備註");
			}else if(checkStr1 == '2'){
				alertStr += checkQuotation(form1.notes,"備註");
			}else if(checkStr1 == '3' || checkStr1 == '4' || checkStr1 == '5'){
				alertStr += checkQuotation(form1.notes,"備註");
			}else if(checkStr1 == '8'){
				alertStr += checkQuotation(form1.notes,"備註");
			}else if(checkStr1 == '9'){			
				alertStr += checkQuotation(form1.notes,"備註");
			}
			
	/*		
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
	*/		
			// Revision: 7882 2015年5月7日 下午 04:05:42 anthony.wang  將整建說明欄位殺掉
			//if(form1.cause.value == "31"){
	        //    alertStr+= checkEmpty(form1.cause2,"「增減值原因」為「整建」選項時\n「整建說明」"); 
	        //}
	//        if(form1.cause.value == "3"){
	//            alertStr+= checkEmpty(form1.cause1,"「增減值原因」為「其它」選項時\n「其它說明」");
	//        }  	 
	
		
		
		
		}
	} catch (e) {
		alertStr += "欄位檢核異常";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; } 
	beforeSubmit(); 
}

function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,serialNo,lotNo){ 
	form1.enterOrg.value=enterOrg; 
	form1.ownership.value=ownership; 
	form1.caseNo.value=caseNo; 
	form1.differenceKind.value=differenceKind;
	form1.propertyNo.value=propertyNo; 
	form1.serialNo.value=serialNo;
	form1.lotNo.value=lotNo; 
	form1.state.value="queryOne"; 
	beforeSubmit(); 
	form1.submit();  
} 

function sonSumbit(ownership, differenceKind) {
	form1.ownership.value=ownership; 
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryAll";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(surl!='../ch/untch003f01.jsp' && form1.propertyNo.value==""){
		alert("請先執行查詢!");
	}else{
		form1.currentPage.value=form1.mainPage.value;
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.state.value="queryAll";	
		form1.action = surl;
		beforeSubmit();
		form1.submit();		
	}
}


function getAreaData() {
	if(form1.propertyNo.value.substring(0,1) == '1'){
		
		form1.newLaArea.value = Math.round(parseFloat(form1.newLaArea.value,10) * 100) / 100;
		form1.newLaHoldArea.value = Math.round(parseFloat(form1.newLaArea.value,10) * 100 * parseFloat(form1.newLaHoldNume.value,10) / parseFloat(form1.newLaHoldDeno.value,10)) / 100;
		
		form1.adjustLaArea.value = Math.round(parseFloat(form1.newLaArea.value,10) * 100 - parseFloat(form1.oldLaArea.value,10) * 100) / 100;
		form1.adjustLaHoldArea.value = Math.round(parseFloat(form1.newLaHoldArea.value,10) * 100 - parseFloat(form1.oldLaHoldArea.value,10) * 100) / 100;
		
	}else{
		if(form1.adjustBuCArea.value == ''){	form1.adjustBuCArea.value = "0";}
		if(form1.adjustBuSArea.value == ''){	form1.adjustBuSArea.value = "0";}
		form1.adjustBuArea.value = Math.round(parseFloat(form1.adjustBuCArea.value,10) * 100 + parseFloat(form1.adjustBuSArea.value,10) * 100 ) / 100;
		
		form1.newBuCArea.value = Math.round((parseFloat(form1.oldBuCArea.value,10) + parseFloat(form1.adjustBuCArea.value,10))* 100) / 100;
		form1.newBuSArea.value = Math.round((parseFloat(form1.oldBuSArea.value,10) + parseFloat(form1.adjustBuSArea.value,10))* 100) / 100;
		
		form1.newBuArea.value = Math.round((parseFloat(form1.oldBuArea.value,10) + parseFloat(form1.adjustBuArea.value,10)) * 100) / 100;
		form1.newBuHoldArea.value = (parseFloat(form1.newBuArea.value,10) * parseFloat(form1.newBuHoldNume.value,10) / parseFloat(form1.newBuHoldDeno.value,10)).toFixed(2);
		
		var newHoldArea = parseFloat(form1.newBuArea.value,10) * parseFloat(form1.newBuHoldNume.value,10) / parseFloat(form1.newBuHoldDeno.value,10);
		form1.adjustBuHoldArea.value = (newHoldArea.toFixed(2) - parseFloat(form1.oldBuHoldArea.value,10)).toFixed(2);
		
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
	initQ_Form("Detail");
	if(form1.propertyNo.value != ''){
		changePropertyNo(form1.propertyNo.value);
		processAccumDepr(); // for queryOne
	}else{								
		initFormDisabled();
	}
	
	form1.oldcause.value="0";
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
	
	setFormItem("btnBatch,btnValue","O");
	
	//1040417 因為客戶端效能關係，調整以下程式
	//<<<<<<<<<<<<<<<<<<<<<<<
	if('<%=obj.getLaSignNo1()%>' == ''){		
	}else{
		changeSignNo1('laSignNo1','laSignNo2','laSignNo3','<%=obj.getLaSignNo2()%>');
		
		if('<%=obj.getLaSignNo2()%>' == ''){		
		}else{
			changeSignNo2('laSignNo1','laSignNo2','laSignNo3','<%=obj.getLaSignNo3()%>');
		}			
	}
	if('<%=obj.getBuSignNo1()%>' == ''){		
	}else{
		changeSignNo1('buSignNo1','buSignNo2','buSignNo3','<%=obj.getBuSignNo2()%>');
		
		if('<%=obj.getBuSignNo2()%>' == ''){		
		}else{
			changeSignNo2('buSignNo1','buSignNo2','buSignNo3','<%=obj.getBuSignNo3()%>');
		}			
	}
	if('<%=obj.getQ_signLaNo1()%>' == ''){		
	}else{
		changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo2()%>');
		
		if('<%=obj.getQ_signLaNo2()%>' == ''){		
		}else{
			changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo3()%>');
		}			
	}
	if('<%=obj.getQ_signBuNo1()%>' == ''){		
	}else{
		changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo2()%>');
		
		if('<%=obj.getQ_signBuNo2()%>' == ''){		
		}else{
			changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo3()%>');
		}			
	}

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value + form1.differenceKind.value + form1.propertyNo.value + form1.serialNo.value + form1.lotNo.value;
		listContainerRowClick(key);
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

function gountch003f03(){
	var urlParms = "caseNo="+form1.caseNo.value
	                       + "&enterOrg="+form1.enterOrg.value
	                       + "&ownership="+form1.ownership.value
	                       + "&differenceKind="+form1.differenceKind.value
	                       + "&engineeringNo=" + form1.engineeringNo.value;
	window.open("../../unt/ch/untch003f03.jsp?" + urlParms);
}

function gountla021f(){
	window.open("../la/untla021f.jsp?caseNo="+form1.caseNo.value+"&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&differenceKind="+form1.differenceKind.value);		
}

function changeAdjustUnit(colName){
	checkIsNan();
	
	if(form1.addBookValue.value == ""){
		form1.addBookValue.value = "0";
		form1.addNetValue.value = "0";
		
	}else if(form1.reduceBookValue.value == ""){
		form1.reduceBookValue.value = "0";
		form1.reduceNetValue.value = "0";
		
	}else{	
		/*
		if(colName == 'addBookValue'){
			form1.addNetValue.value = form1.addBookValue.value;
			
			form1.newBookValue.value = parseInt(form1.oldBookValue.value) + parseInt(form1.addBookValue.value);
			form1.newAccumDeprTemp.value = parseInt(form1.oldAccumDeprTemp.value) - parseInt(form1.reduceAccumDepr.value);
			form1.newNetValue.value = parseInt(form1.oldBookValue.value) + parseInt(form1.addNetValue.value);
			
		}else if(colName == 'reduceBookValue'){
			//form1.reduceAccumDepr.value = roundp((parseInt(form1.reduceBookValue.value) / parseInt(form1.oldBookValue.value)) * parseInt(form1.reduceBookValue.value),0,"Y");
			form1.reduceAccumDepr.value = parseInt(form1.reduceBookValue.value) / parseInt(form1.oldBookValue.value) * parseInt(form1.oldAccumDeprTemp.value);
			form1.reduceNetValue.value = parseInt(form1.reduceBookValue.value) - parseInt(form1.reduceNetValue.value);
			
			form1.newBookValue.value = parseInt(form1.oldBookValue.value) - parseInt(form1.reduceBookValue.value);
			form1.newAccumDeprTemp.value = parseInt(form1.oldAccumDeprTemp.value) - parseInt(form1.reduceAccumDepr.value);
			form1.newNetValue.value = parseInt(form1.oldBookValue.value) - parseInt(form1.reduceNetValue.value);
		}		
		*/
	}
	
	form1.addNetValue.value = form1.addBookValue.value;
	if (form1.oldScrapValue.value == form1.oldNetValue.value) {
	    form1.reduceAccumDepr.value = form1.reduceBookValue.value;
	}else {
	    form1.reduceAccumDepr.value = Math.round(parseInt(form1.reduceBookValue.value) / parseInt(form1.oldBookValue.value) * parseInt(form1.oldAccumDeprTemp.value));
	}
	checkIsNan();
	
	form1.reduceNetValue.value = parseInt(form1.reduceBookValue.value) - parseInt(form1.reduceAccumDepr.value);
	form1.newBookValue.value = parseInt(form1.oldBookValue.value) + parseInt(form1.addBookValue.value) - parseInt(form1.reduceBookValue.value);
	form1.newAccumDeprTemp.value = parseInt(form1.oldAccumDeprTemp.value) - parseInt(form1.reduceAccumDepr.value);
	form1.newAccumDepr.value = form1.newAccumDeprTemp.value;
	form1.newNetValue.value = parseInt(form1.oldNetValue.value) + parseInt(form1.addNetValue.value) - parseInt(form1.reduceNetValue.value);
	
	checkIsNan();
	checkDeprMethod();
}

function changeOtherLimitYear() {
	if (form1.addLimitYear.value == '') {
		form1.addLimitYear.value = 0;
	}
	if (form1.overLimitYear.value == '') {
		form1.overLimitYear.value = 0;
	}
	if (form1.reduceLimitYear.value == '') {
		form1.reduceLimitYear.value = 0;
	}
	form1.newOtherLimitYear.value = parseInt(form1.otherLimitYear.value) + parseInt(form1.addLimitYear.value) + parseInt(form1.overLimitYear.value) - parseInt(form1.reduceLimitYear.value);
}

function checkIsNan(){
	if(isNaN(form1.oldBookValue.value) || form1.oldBookValue.value==""){				form1.oldBookValue.value = "0";}
	if(isNaN(form1.oldAccumDeprTemp.value) || form1.oldAccumDeprTemp.value==""){		form1.oldAccumDeprTemp.value = "0";}
	if(isNaN(form1.oldNetValue.value) || form1.oldNetValue.value==""){					form1.oldNetValue.value = "0";}
	if(isNaN(form1.addBookValue.value) || form1.addBookValue.value==""){				form1.addBookValue.value = "0";}
	if(isNaN(form1.addNetValue.value) || form1.addNetValue.value==""){					form1.addNetValue.value = "0";}		
	if(isNaN(form1.reduceBookValue.value) || form1.reduceBookValue.value==""){			form1.reduceBookValue.value = "0";}
	if(isNaN(form1.reduceAccumDepr.value) || form1.reduceAccumDepr.value==""){			form1.reduceAccumDepr.value = "0";}
	if(isNaN(form1.reduceNetValue.value) || form1.reduceNetValue.value==""){			form1.reduceNetValue.value = "0";}
	if(isNaN(form1.newBookValue.value) || form1.newBookValue.value==""){				form1.newBookValue.value = "0";}
	if(isNaN(form1.newAccumDeprTemp.value) || form1.newAccumDeprTemp.value==""){		form1.newAccumDeprTemp.value = "0";}
	if(isNaN(form1.newNetValue.value) || form1.newNetValue.value==""){					form1.newNetValue.value = "0";}
}

function adjustMeasure_Event(colName){
	if(colName == 'addMeasure'){
		form1.reduceMeasure.value = '0';
		form1.newMeasure.value = Math.round(parseFloat(form1.oldMeasure.value,10) * 100 + parseFloat(form1.addMeasure.value,10) * 100) / 100;
	}
	if(colName == 'reduceMeasure'){
		form1.addMeasure.value = '0';
		form1.newMeasure.value = Math.round(parseFloat(form1.oldMeasure.value,10) * 100 - parseFloat(form1.reduceMeasure.value,10) * 100) / 100;
	}	
}

function queryADDProofData(type){
	//傳送JSON
	var comment={};
	comment.enterOrg = $("input[name='enterOrg']").val();
	comment.ownership = $("select[name='ownership']").val();
	comment.differenceKind = $("select[name='differenceKind']").val();
	comment.propertyNo = "";
	comment.serialNo = "";
	comment.laSignNo = "";
	comment.buSignNo = "";
	comment.proofType = "AdjustProof";
	
	if(type == 'serialNo'){
		comment.propertyNo = $("input[name='propertyNo']").val();
		comment.serialNo = $("input[name='serialNo']").val();
		
	}else if(type == 'laSignNo'){
		if($("select[name='laSignNo3']").val() != '' && $("input[name='laSignNo4']").val() != '' && $("input[name='laSignNo5']").val() != ''){
			comment.laSignNo = $("select[name='laSignNo3']").val() + $("input[name='laSignNo4']").val() + $("input[name='laSignNo5']").val();
		}	
	}else if(type == 'buSignNo'){
		if($("select[name='buSignNo3']").val() != '' && $("input[name='buSignNo4']").val() != '' && $("input[name='buSignNo5']").val() != ''){
			comment.buSignNo = $("select[name='buSignNo3']").val() + $("input[name='buSignNo4']").val() + $("input[name='buSignNo5']").val();		
		}	
	}
	
	if(comment.propertyNo == "" && comment.serialNo == "" && comment.laSignNo == "" & comment.buSignNo == ""){
		
	}else{
		$.post('queryADDProofData.jsp',
			comment,
			function(data){
				//將回傳資料寫入
				data=eval('('+data+')');
	
				if(data['hasData'] == 'N'){
					alert('找不到此筆財產資料！！');
				}else{
					$("input[name='propertyNo']").val(data['propertyNo']);
					$("input[name='serialNo']").val(data['serialNo']);
					$("input[name='propertyName1']").val(data['propertyName1']);
					$("input[name='oldPropertyNo']").val(data['oldPropertyNo']);
					$("input[name='oldSerialNo']").val(data['oldSerialNo']);
					$("input[name='oldLaArea']").val(data['oldLaArea']);
					$("input[name='oldLaHoldNume']").val(data['oldLaHoldNume']);
					$("input[name='oldLaHoldDeno']").val(data['oldLaHoldDeno']);
					$("input[name='oldLaHoldArea']").val(data['oldLaHoldArea']);
					$("input[name='newLaArea']").val(data['oldLaArea']);
					$("input[name='newLaHoldNume']").val(data['oldLaHoldNume']);
					$("input[name='newLaHoldDeno']").val(data['oldLaHoldDeno']);
					$("input[name='newLaHoldArea']").val(data['oldLaHoldArea']);
					$("input[name='oldBuCArea']").val(data['oldBuCArea']);
					$("input[name='oldBuSArea']").val(data['oldBuSArea']);
					$("input[name='oldBuArea']").val(data['oldBuArea']);
					$("input[name='oldBuHoldNume']").val(data['oldBuHoldNume']);
					$("input[name='oldBuHoldDeno']").val(data['oldBuHoldDeno']);
					$("input[name='oldBuHoldArea']").val(data['oldBuHoldArea']);
					$("input[name='newBuCArea']").val(data['oldBuCArea']);
					$("input[name='newBuSArea']").val(data['oldBuSArea']);
					$("input[name='newBuArea']").val(data['oldBuArea']);
					$("input[name='newBuHoldNume']").val(data['oldBuHoldNume']);
					$("input[name='newBuHoldDeno']").val(data['oldBuHoldDeno']);
					$("input[name='newBuHoldArea']").val(data['oldBuHoldArea']);
					$("input[name='oldMeasure']").val(data['oldMeasure']);
					$("input[name='oldBookAmount']").val(data['oldBookAmount']);
					$("input[name='bookAmount']").val(data['oldBookAmount']);
					$("input[name='sourceDate']").val(data['sourceDate']);
					$("input[name='originalBV']").val(data['originalBV']);
					$("input[name='buyDate']").val(data['buyDate']);
					$("input[name='bookNotes']").val(data['bookNotes']);
					$("input[name='oldBookValue']").val(data['oldBookValue']);
					$("input[name='newBookValue']").val(data['oldBookValue']);
					$("input[name='oldAccumDepr']").val(data['oldAccumDepr']);
					$("input[name='newAccumDepr']").val(data['oldAccumDepr']);
					$("input[name='oldAccumDeprTemp']").val(data['oldAccumDepr']);
					$("input[name='oldNetValue']").val(parseInt(data['oldBookValue']) - parseInt(data['oldAccumDepr']));
					$("input[name='newNetValue']").val($("input[name='oldNetValue']").val());
					$("select[name='keepUnit']").val(data['keepUnit']);
					$("select[name='keeper']").val(data['keeper']);
					$("select[name='useUnit']").val(data['useUnit']);
					$("select[name='userNo']").val(data['userNo']);
					$("input[name='userNote']").val(data['userNote']);
					$("input[name='place1']").val(data['place1']);
					$("input[name='place1name']").val(data['place1name']);
					$("input[name='place']").val(data['place']);
					$("select[name='oldDeprMethod']").val(data['oldDeprMethod']);
					$("select[name='newDeprMethod']").val(data['oldDeprMethod']);
					$("input[name='oldBuildFeeCB']").val(data['oldBuildFeeCB']);
					$("input[name='newBuildFeeCB']").val(data['oldBuildFeeCB']);
					if(data['oldBuildFeeCB'] == "Y"){
						$("input[name='cb_oldBuildFeeCB']").attr("checked",true);
						$("input[name='cb_newBuildFeeCB']").attr("checked",true);
					}else{						
						$("input[name='cb_oldBuildFeeCB']").attr("checked",false);
						$("input[name='cb_newBuildFeeCB']").attr("checked",false);
					}
					$("input[name='oldDeprUnitCB']").val(data['oldDeprUnitCB']);
					$("input[name='newDeprUnitCB']").val(data['oldDeprUnitCB']);
					if(data['oldDeprUnitCB'] == "Y"){
						$("input[name='cb_oldDeprUnitCB']").attr("checked",true);
						$("input[name='cb_newDeprUnitCB']").attr("checked",true);
					}else{
						$("input[name='cb_oldDeprUnitCB']").attr("checked",false);
						$("input[name='cb_newDeprUnitCB']").attr("checked",false);
					}
					$("select[name='oldDeprPark']").val(data['oldDeprPark']);
					$("select[name='newDeprPark']").val(data['oldDeprPark']);
					$("select[name='oldDeprUnit']").val(data['oldDeprUnit']);
					$("select[name='newDeprUnit']").val(data['oldDeprUnit']);
					$("select[name='oldDeprUnit1']").val(data['oldDeprUnit1']);
					$("select[name='newDeprUnit1']").val(data['oldDeprUnit1']);
					$("select[name='oldDeprAccounts']").val(data['oldDeprAccounts']);
					$("select[name='newDeprAccounts']").val(data['oldDeprAccounts']);
					$("input[name='oldScrapValue']").val(data['oldScrapValue']);
					$("input[name='newScrapValue']").val(data['oldScrapValue']);
					$("input[name='oldDeprAmount']").val(data['oldDeprAmount']);
					$("input[name='newDeprAmount']").val(data['oldDeprAmount']);
					$("input[name='oldAccumDepr']").val(data['oldAccumDepr']);
					$("input[name='oldApportionMonth']").val(data['oldApportionMonth']);
					$("input[name='newApportionMonth']").val(data['newApportionMonth']);
					$("input[name='oldMonthDepr']").val(data['oldMonthDepr']);
					$("input[name='newMonthDepr']").val(data['oldMonthDepr']);
					$("input[name='oldMonthDepr1']").val(data['oldMonthDepr1']);
					$("input[name='newMonthDepr1']").val(data['oldMonthDepr1']);
					$("input[name='oldApportionEndYM']").val(data['oldApportionEndYM']);
					$("input[name='newApportionEndYM']").val(data['oldApportionEndYM']);
					$("input[name='material']").val(data['material']);
					$("input[name='propertyUnit']").val(data['propertyunit']);
					$("input[name='limitYear']").val(data['limityear']);
					$("input[name='otherMaterial']").val(data['otherMaterial']);
					$("input[name='otherPropertyUnit']").val(data['otherPropertyUnit']);
					$("input[name='otherLimitYear']").val(data['otherLimitYear']);
					$("input[name='securityName']").val(data['securityName']);
					$("select[name='laSignNo1']").val(data['laSignNo1']);
					$("select[name='laSignNo2']").val(data['laSignNo2']);
					$("select[name='laSignNo3']").val(data['laSignNo3']);
					$("input[name='laSignNo4']").val(data['laSignNo4']);
					$("input[name='laSignNo5']").val(data['laSignNo5']);
					$("select[name='buSignNo1']").val(data['buSignNo1']);
					$("select[name='buSignNo2']").val(data['buSignNo2']);
					$("select[name='buSignNo3']").val(data['buSignNo3']);
					$("input[name='buSignNo4']").val(data['buSignNo4']);
					$("input[name='buSignNo5']").val(data['buSignNo5']);
					changeSignNo1('laSignNo1','laSignNo2','laSignNo3',data['laSignNo2']);			
					changeSignNo1('buSignNo1','buSignNo2','buSignNo3',data['buSignNo2']);
					changeSignNo2('laSignNo1','laSignNo2','laSignNo3',data['laSignNo3']);
					changeSignNo2('buSignNo1','buSignNo2','buSignNo3',data['buSignNo3']);
					
					$("select[name='propertyKind']").val(data['propertyKind']);
					$("select[name='fundType']").val(data['fundType']);
					$("select[name='valuable']").val(data['valuable']);
					$("select[name='taxCredit']").val(data['taxCredit']);
					$("input[name='lotNo']").val(data['lotNo']);
					$("input[name='newBookAmount']").val(data['newBookAmount']);
					$("input[name='closing1ym']").val(data['closing1ym']);
					
					execChangePropertyNo(data['propertyNo']);
					
					checkIsNan();
					checkDeprMethod();
					
					if (data['newApportionMonth'] == 0) {
						alert("提醒您，此財產已達使用年限!");
					}
				}
			});
	}
}

/**
 * 處理 reduceAccumDepr1 + reduceAccumDepr2
 * @param queryAccumDepr2 : 是否要查詢 reduceAccumDepr2
 */
function processAccumDepr() {
	 // 如果是不累折
	 if(form1.oldDeprMethod.value == '01'){
		 form1.reduceAccumDepr1.value = '0';
		 form1.reduceAccumDepr2.value = '0';
		 return;
	 }
	 
	 // 如果以前年度欄位是空值 再去查詢上一年度的  不然就當作查過了
	 if (form1.reduceAccumDepr2.value === '') {
		// 以前年度累計折舊reduceAccumDepr2= UNTDP_MonthDepr deprYM上一年度12月. newAccumDepr*(減少原值/調整前原值) ，四捨五入取至整數
		var aObj = ajaxQuery("ajax/unt/dp/UntdpMonthdeprAjax.jsp", 
												{"action" : "getDataOfEndOfLastYear", 
												  "enterorg" : form1.enterOrg.value,
												  "ownership" : form1.ownership.value,
												  "differencekind" : form1.differenceKind.value,
												  "propertyno" : form1.propertyNo.value,
												  "serialno" : form1.serialNo.value
												  }, false);
		if (aObj.isSuccess) {
			var  reduceAccumDepr2 = Math.round(parseFloat(form1.reduceBookValue.value) / parseFloat(form1.oldBookValue.value) * parseFloat(aObj.data.newaccumdepr));
			form1.reduceAccumDepr2.value = reduceAccumDepr2;
		} else {
			form1.reduceAccumDepr2.value = "0";
		}
	 } 
	
	// 本年度累計折舊reduceAccumDepr1=reduceAccumDepr- reduceAccumDepr2
	 var radv2 = isRealNaN(form1.reduceAccumDepr2.value) ? 0 : parseFloat(form1.reduceAccumDepr2.value);
	form1.reduceAccumDepr1.value = parseFloat(form1.reduceAccumDepr.value) - radv2;
}

function initFormDisabled(){
	$("tr[name='div_la_com']").show();
	$("tr[name='div_bu_com']").show();
	$("tr[name='div_la']").hide();
	$("tr[name='div_bu']").hide();
	$("tr[name='div_rf']").hide();
	$("tr[name='div_mp']").hide();
	$("tr[name='div_vp']").hide();
	$("tr[name='div_rt']").hide();
	$("tr[name='div_olddeprmethod']").hide();
	$("tr[name='div_newdeprmethod']").hide();
	$("tr[name='div_common']").hide();	
	
}

function changePropertyNo(propertyNo){	
	execChangePropertyNo(form1.propertyNo.value);	
}

function execChangePropertyNo(propertyNo){
	initFormDisabled();
	
	var checkStr1 = propertyNo.substring(0,1);
	var checkStr3 = propertyNo.substring(0,3);
	
	$("tr[name='div_common']").show();	
	$("tr[name='div_la_com']").hide();
	$("tr[name='div_bu_com']").hide();
	
	if("111" == checkStr3){
		$("tr[name='div_rf']").show();
		$("tr[name='div_olddeprmethod']").show();
		$("tr[name='div_newdeprmethod']").show();
	}else if("1" == checkStr1){
		$("tr[name='div_la']").show();
		$("tr[name='div_la_com']").show();	
	}else if("2" == checkStr1){
		$("tr[name='div_bu']").show();
		$("tr[name='div_bu_com']").show();	
		$("tr[name='div_olddeprmethod']").show();
		$("tr[name='div_newdeprmethod']").show();
	}else if("3" == checkStr1 || "4" == checkStr1 || "5" == checkStr1){
		$("tr[name='div_mp']").show();
		$("tr[name='div_olddeprmethod']").show();
		$("tr[name='div_newdeprmethod']").show();
	}else if("8" == checkStr1){
		$("tr[name='div_rt']").show();
        $("tr[name='div_olddeprmethod']").show();
        $("tr[name='div_newdeprmethod']").show();
	}else if("9" == checkStr1){
		$("tr[name='div_vp']").show();		
	}
	
}	

function queryDeprUnitData(){
	if($("input[name='newDeprUnittemp']").val() != ''){
		$("select[name='newDeprUnit']").val($("input[name='newDeprUnittemp']").val());
		$("input[name='newDeprUnittemp']").val('');
	}
	queryDeprUnitDataforDeprUnit1();
	queryDeprUnitDataforDeprAccounts();
}

function queryDeprUnitDataforDeprUnit1(){
	if($("select[name='newDeprUnit1']").val() == ''){
		if($("select[name='newDeprUnit']").val() != ''){
			
			//傳送JSON		
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("newDeprUnit").value;
			comment.queryType = "deprUnit1";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');

					$("select[name='newDeprUnit1']").val(data['defaultValue']);
			
				});
		}
	}
}
function queryDeprUnitDataforDeprAccounts(){
	if($("select[name='newDeprAccounts']").val() == ''){
		if($("select[name='newDeprUnit']").val() != ''){	
			//傳送JSON
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("newDeprUnit").value;
			comment.queryType = "deprAccounts";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');
		
					$("select[name='newDeprAccounts']").val(data['defaultValue']);
					
				});
		}
	}
}

function checkDeprMethod(){
	changeOtherLimitYear();
	form1.newMonthDepr.value = "";
	form1.newMonthDepr1.value = "";
	form1.newApportionEndYM.value = "";
	if(form1.state.value == 'insert' || form1.state.value == 'update'){
		setFormItem("cb_newBuildFeeCB,cb_newDeprUnitCB,newDeprUnit1,newDeprUnit,newDeprAccounts,newDeprAmount,newApportionMonth,newAccumDepr,newMonthDepr,newMonthDepr1,newDeprPark,newScrapValue","O");
	}
	// 問題單1114
	if (form1.differenceKind.value == "110") {
        setFormItem("newDeprMethod,cb_newBuildFeeCB,cb_newDeprUnitCB,newDeprPark,newDeprUnit,newDeprUnit1,newDeprAccounts","R");
    }
	
	// 計算 本年度累折 以及 以前年度累折
	processAccumDepr();
	
	if(form1.newDeprMethod.value == '01'){
		form1.cb_newBuildFeeCB.value = "";
		form1.cb_newDeprUnitCB.value = "";
		form1.newDeprUnit1.value = "";
		form1.newBuildFeeCB.value = "";
		form1.newDeprUnitCB.value = "";
		form1.newDeprPark.value = "";
		form1.deprPark.value = "";
		form1.newDeprUnit.value = "";
		form1.newDeprAccounts.value = "";
		form1.newScrapValue.value = "";
		form1.newDeprAmount.value = "";
		form1.newAccumDepr.value = "";
		form1.newMonthDepr.value = "";
		form1.newMonthDepr1.value = "";
		form1.newApportionMonth.value = "";
		form1.newApportionEndYM.value = "";
		setFormItem("cb_newBuildFeeCB,cb_newDeprUnitCB,newDeprUnit1,newBuildFeeCB,newDeprUnitCB,newDeprPark,deprPark,newDeprUnit,newDeprAccounts,newDeprAmount,newAccumDepr,newApportionMonth,newMonthDepr,newMonthDepr1,newScrapValue","R");
	}else if(form1.newDeprMethod.value == '02' || form1.newDeprMethod.value == '03'){
		
		if($("input[name='cb_newDeprUnitCB']").attr('checked') == 'checked'){			
			form1.newDeprUnit.value = "";
			form1.newDeprAccounts.value = "";
			
			setFormItem("newDeprUnit,newDeprUnit1,newDeprAccounts,newDeprAmount,newApportionMonth,newAccumDepr,newMonthDepr,newMonthDepr1","R");
		}else{
			var newOtherLimitYear = form1.newOtherLimitYear.value;
			
			//計算截止年月
			if(form1.buyDate.value == ''){
				
			}else{
				var YYY = parseInt(form1.buyDate.value.substring(0,3),10) + parseInt((newOtherLimitYear/12),10);
				var MM = parseInt(form1.buyDate.value.substring(3,5),10) + parseInt((newOtherLimitYear%12),10);
				if((MM/12) > 1){
					YYY = parseInt(YYY,10) + 1;
					MM = MM%12;
				}
				
				form1.newApportionEndYM.value = frontZero(YYY,3) + frontZero(MM,2);
			}
			//殘值若為空以0計算
			if(form1.newScrapValue.value == ''){
				form1.newScrapValue.value = "0";
			}
			if(form1.overLimitYear.value == ''){
				form1.overLimitYear.value = "0";
			}
			//計算攤提壽月
			var newApportionMonth;
			if (form1.closing1ym.value === '201512' && form1.differenceKind.value === '110') {
				newApportionMonth = (isNaN(form1.oldApportionMonth.value)  ? 0 : parseInt(form1.oldApportionMonth.value))
													   + (isNaN(form1.addLimitYear.value)  ? 0 : parseInt(form1.addLimitYear.value))
													   -  (isNaN(form1.reduceLimitYear.value)  ? 0 : parseInt(form1.reduceLimitYear.value));   
			} else {
				var year = parseInt(form1.newApportionEndYM.value.substring(0,3)) + 1911; 
				var sDT = form1.closing1ym.value.substring(0,4) + "/" + form1.closing1ym.value.substring(4,6) + "/01";
				var eDT = year + "/" + form1.newApportionEndYM.value.substring(3,5) + "/01";
				newApportionMonth = getMct(sDT,eDT,"/");//攤提壽月
			}
			
			if (parseInt(newApportionMonth) <= 0) {
				newApportionMonth = 0;
			}
			form1.newApportionMonth.value = newApportionMonth;
			form1.newDeprAmount.value = form1.newNetValue.value - form1.newScrapValue.value;
			if (newApportionMonth == 0) {
				form1.newMonthDepr.value = 0;
				form1.newMonthDepr1.value = 0;
			} else {
				form1.newMonthDepr.value = Math.floor(parseInt(form1.newDeprAmount.value)/ newApportionMonth);
				form1.newMonthDepr1.value = parseInt(form1.newDeprAmount.value) - parseInt(form1.newMonthDepr.value) * (newApportionMonth - 1);
			}
		}		
	}
}

/**
 * 两个日期相减得到月数差 格式: yyyy-MM-dd
 * @param  beginTime  开始日期
 * @param  endTime   结束日期
 * @param  sign   年月日分隔符:-     * */
function getMct(beginTime,endTime,sign){
	var beginDate = beginTime.split(sign);//拆分开始日期的年月日
	var endDate = endTime.split(sign);//拆分开始日期的年月日
	var bMonth = parseInt(beginDate[0],10) * 12 + parseInt(beginDate[1],10);//得到开始日期的月数
	var eMonth = parseInt(endDate[0],10) * 12 + parseInt(endDate[1],10);//得到结束日期的月数
	var totalMonth = eMonth - bMonth;//获取月数
	return totalMonth;
} 
function frontZero(valueStr,itemp){
	var nv = valueStr.toString();
	var stemp="";
	if(nv!=""){
		itemp -= nv.length;
		for (var i=0;i<itemp;i++){
			stemp+="0";
		}		
		stemp += nv;
	}
	return stemp;
}

function checkVPBookAmount(name){
	if(name == 'addBookAmount'){
		
		form1.newBookAmount.value = parseInt(form1.oldBookAmount.value) + parseInt(form1.addBookAmount.value)- parseInt(form1.reduceBookAmount.value); 
	}else if(name == 'reduceBookAmount'){
		
		form1.newBookAmount.value = parseInt(form1.oldBookAmount.value)  + parseInt(form1.addBookAmount.value)- parseInt(form1.reduceBookAmount.value);
	}
}

function checkNewBuildFeeCB(){	
	if($("input[name='cb_newBuildFeeCB']").attr('checked') == 'checked'){
		$("input[name='newBuildFeeCB']").val('Y');
	}else{
		$("input[name='newBuildFeeCB']").val('N');
	}
}

function checkNewDeprUnitCB(){
	if($("input[name='cb_newDeprUnitCB']").attr('checked') == 'checked'){
		$("input[name='newDeprUnitCB']").val('Y');
	}else{
		$("input[name='newDeprUnitCB']").val('N');
	}
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

function gountch003f05(){
	var urlParms = "caseNo="+form1.caseNo.value
    + "&enterOrg="+form1.enterOrg.value
    + "&ownership="+form1.ownership.value
    + "&differenceKind="+form1.differenceKind.value
    + "&engineeringNo=" + form1.engineeringNo.value;
window.open("../../unt/ch/untch003f05.jsp?" + urlParms);
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
<div id="queryContainer" style="width:900px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<% request.setAttribute("UNTCH003Q",obj);%>
	<jsp:include page="untch003q.jsp" />
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	
	<tr>
		<td>
			<input class="field_QRO" type="hidden" name="oldcause" size="1" value="<%=obj.getCause()%>">
			<input class="field_QRO" type="hidden" name="CArea" size="20" value="<%=obj.getCArea()%>">
			<input class="field_QRO" type="hidden" name="SArea" size="20" value="<%=obj.getSArea()%>">
			<input class="field_QRO" type="hidden" name="area" size="20" value="<%=obj.getArea()%>">
			<input class="field_QRO" type="hidden" name="holdNume" size="20" value="<%=obj.getHoldNume()%>">
			<input class="field_QRO" type="hidden" name="holdDeno" size="20" value="<%=obj.getHoldDeno()%>">
			<input class="field_QRO" type="hidden" name="holdArea" size="20" value="<%=obj.getHoldArea()%>">
			<input class="field_QRO" type="hidden" name="bookValue" size="20" value="<%=obj.getBookValue()%>">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" value="<%=obj.getEnterOrg()%>">
			
			<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
			<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
			<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
			<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
			<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
			<input type="hidden" name="proofYear" value="<%=obj.getProofYear()%>">
			<input type="hidden" name="proofDoc" value="<%=obj.getProofDoc()%>">
			<input type="hidden" name="proofNo" value="<%=obj.getProofNo()%>">
			<input class="field_RO" type="hidden" name="bookAmount" size="9" maxlength="9" value="<%=obj.getBookAmount()%>">
		</td>
	</tr>
				
	<tr>
		<td class="td_form" colspan="2">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" value="<%=obj.getEnterOrgName()%>">]
		&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			<input type="hidden" name="caseNo" class="field_QRO" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" colspan="2">財產區分別：</td>
		<td class="td_form_white" colspan="3" >
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
			&nbsp;&nbsp;&nbsp;		
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo() %>">]
       	</td>
	</tr>
	<tr>
		<td class="td_form" colspan="2">工程編號：</td>
		<td colspan="3" class="td_form_white">
       		[<input class="field_QRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo() %>">]
			[<input class="field_QRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName() %>">]			
       	</td>
       </tr>
	<tr>
		<td class="td_form" colspan="2">增減值日期：</td>
		<td class="td_form_white" colspan = "3">
			[<input type="text" name="adjustDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getAdjustDate()%>">]
			&nbsp;&nbsp;　　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
		</td>	
	</tr>
	<tr>
		<td class="td_form" colspan="2"><font color="red">*</font>財產編號：</td>
		<td colspan="3" class="td_form_white">			
	  		<%=new unt.ch.UNTCH_Tools().getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"")%>
	   		分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onchange="getFrontZero(this.name,7);queryADDProofData('serialNo');">
	   		<input class="field" type="hidden" name="lotNo" value="<%=obj.getLotNo()%>">
	   	<br>
		別名：
			[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>	 
	</tr>
	<tr>
		<td class="td_form" colspan="2"><font color="red">*</font>增減值原因：</td>
		<td class="td_form_white" colspan = "3">
			<%=util.View._getSelectHTML("field_P", "cause", obj.getCause(),"AJC") %>
			<input class="field_P" type="button" name="btn_cause" onclick="popSysca_Code('cause','causeName','增減值原因','AJC','');" value="..." title="輔助視窗">
			&nbsp;其他說明：<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		</td>
	</tr>
	
	<tr name="div_la_com">
		<td class="td_form" colspan="2">土地標示：</td>
		<td class="td_form_white" colspan="2">
			<select class="field_RO" type="select" name="laSignNo1" onChange="changeSignNo1('laSignNo1','laSignNo2','laSignNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getLaSignNo1())%>
			</select>　
			<select class="field_RO" type="select" name="laSignNo2" onChange="changeSignNo2('laSignNo1','laSignNo2','laSignNo3','');">
				
			</select>　		
			<select class="field_RO" type="select" name="laSignNo3" onchange="queryADDProofData('laSignNo');">
				
			</select>　
		</td>
		<td class="td_form_white">
			地號：		
			<input class="field_RO" type="text" name="laSignNo4" size="4" maxlength="4" value="<%=obj.getLaSignNo4() %>" onchange="getFrontZero(this.name,4);queryADDProofData('laSignNo');"> -
			<input class="field_RO" type="text" name="laSignNo5" size="4" maxlength="4" value="<%=obj.getLaSignNo5() %>" onchange="getFrontZero(this.name,4);queryADDProofData('laSignNo');">
		</td>
	</tr>
	<tr name="div_bu_com">
		<td class="td_form" colspan="2">建物標示：</td>
		<td class="td_form_white" colspan="2">
			<select class="field_RO" type="select" name="buSignNo1" onChange="changeSignNo1('buSignNo1','buSignNo2','buSignNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getBuSignNo1())%>
			</select>　
			<select class="field_RO" type="select" name="buSignNo2" onChange="changeSignNo2('buSignNo1','buSignNo2','buSignNo3','');">
				
			</select>　		
			<select class="field_RO" type="select" name="buSignNo3" onchange="queryADDProofData('buSignNo');">
				
			</select>　
		</td>
		<td class="td_form_white">
			建號：		
			<input class="field_RO" type="text" name="buSignNo4" size="5" maxlength="5" value="<%=obj.getBuSignNo4() %>" onchange="getFrontZero(this.name,5);queryADDProofData('buSignNo');"> - 
			<input class="field_RO" type="text" name="buSignNo5" size="3" maxlength="3" value="<%=obj.getBuSignNo5() %>" onchange="getFrontZero(this.name,3);queryADDProofData('buSignNo');">
		</td>	
	</tr>	
	<tr name="div_la">
		<td class="td_form" width="5%" rowspan="3">土地：</td>
		<td class="td_form" width="10%">原面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：
			[<input class="field_RO" type="text" name="oldLaArea" size="9" maxlength="9" value="<%=obj.getOldLaArea()%>">]
		<br>
			權利分子：
			[<input class="field_RO" type="text" name="oldLaHoldNume" size="10" maxlength="10" value="<%=obj.getOldLaHoldNume()%>">]
			&nbsp;
			權利分母：
			[<input class="field_RO" type="text" name="oldLaHoldDeno" size="10" maxlength="10" value="<%=obj.getOldLaHoldDeno()%>">]
			&nbsp;　
			權利面積(㎡)：
			[<input class="field_RO" type="text" name="oldLaHoldArea" size="9" maxlength="9" value="<%=obj.getOldLaHoldArea()%>">]
		</td>
	</tr>
	<tr name="div_la">
		<td class="td_form" >新面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：<input class="field" type="text" name="newLaArea" size="9" maxlength="9" value="<%=obj.getNewLaArea()%>" onChange="getAreaData();" > 
		<br>
			權利分子：<input class="field" type="text" name="newLaHoldNume" size="11" maxlength="10" value="<%=obj.getNewLaHoldNume()%>" onChange="getAreaData();" > 
			&nbsp;權利分母：<input class="field" type="text" name="newLaHoldDeno" size="10" maxlength="10" value="<%=obj.getNewLaHoldDeno()%>" onChange="getAreaData();" > 
			&nbsp;&nbsp;&nbsp;&nbsp;　權利面積(㎡)：[<input class="field_RO" type="text" name="newLaHoldArea" size="9" maxlength="9" value="<%=obj.getNewLaHoldArea()%>">]
		</td>
	</tr>
	<tr name="div_la">
		<td class="td_form">增減面積：</td>
		<td class="td_form_white" colspan="3">
			整筆面積(㎡)：[<input class="field_RO" type="text" name="adjustLaArea" size="9" maxlength="9" value="<%=obj.getAdjustLaArea()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　　　　　
			權利面積(㎡)：[<input class="field_RO" type="text" name="adjustLaHoldArea" size="9" maxlength="9" value="<%=obj.getAdjustLaHoldArea()%>">]
		</td>
	</tr>
	<tr name="div_bu">
		<td class="td_form" width="10%" rowspan="3">建物：</td>
		<td class="td_form">原面積：</td>
		<td class="td_form_white" colspan="3">	
			主面積(㎡)：[<input class="field_RO" type="text" name="oldBuCArea" size="9" maxlength="9" value="<%=obj.getOldBuCArea()%>" onChange="getAreaData();">]
			&nbsp;附屬面積(㎡)：[<input class="field_RO" type="text" name="oldBuSArea" size="9" maxlength="9" value="<%=obj.getOldBuSArea()%>" onChange="getAreaData();">]
			&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="oldBuArea" size="9" maxlength="9" value="<%=obj.getOldBuArea()%>" onChange="getAreaData();">]
		<br>
			權利分子：[<input class="field_RO" type="text" name="oldBuHoldNume" size="10" maxlength="10" value="<%=obj.getOldBuHoldNume()%>" onChange="getAreaData();">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="oldBuHoldDeno" size="10" maxlength="10" value="<%=obj.getOldBuHoldDeno()%>" onChange="getAreaData();">]
			&nbsp;　權利面積(㎡)：[<input class="field_RO" type="text" name="oldBuHoldArea" size="9" maxlength="9" value="<%=obj.getOldBuHoldArea()%>" onChange="getAreaData();">]
		</td>
	</tr>
	<tr name="div_bu">
		<td class="td_form">增減面積：</td>
		<td class="td_form_white" colspan="3">		
			<font color="red">*</font>主面積(㎡)：
			<input class="field" type="text" name="adjustBuCArea" size="9" maxlength="9" value="<%=obj.getAdjustBuCArea()%>" onChange="getAreaData();" >
			<font color="red">*</font>附屬面積(㎡)：
			<input class="field" type="text" name="adjustBuSArea" size="9" maxlength="9" value="<%=obj.getAdjustBuSArea()%>"  onChange="getAreaData();" >
			&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="adjustBuArea" size="9" maxlength="9" value="<%=obj.getAdjustBuArea()%>" onChange="getAreaData();" >]
		<br>
			權利面積(㎡)：[<input class="field_RO" type="text" name="adjustBuHoldArea" size="9" maxlength="9" value="<%=obj.getAdjustBuHoldArea()%>" onChange="getAreaData();" >]
		</td>
	</tr>
	<tr name="div_bu">
		<td class="td_form">新面積：</td>
		<td class="td_form_white" colspan="3">		
			主面積(㎡)：[<input class="field_RO" type="text" name="newBuCArea" size="9" maxlength="9" value="<%=obj.getNewBuCArea()%>" onChange="getAreaData();">]
			&nbsp;附屬面積(㎡)：[<input class="field_RO" type="text" name="newBuSArea" size="9" maxlength="9" value="<%=obj.getNewBuSArea()%>" onChange="getAreaData();">]
			&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="newBuArea" size="9" maxlength="9" value="<%=obj.getNewBuArea()%>" onChange="getAreaData();" >]
		<br>
			<font color="red">*</font>權利分子：
			<input class="field" type="text" name="newBuHoldNume" size="10" maxlength="10" value="<%=obj.getNewBuHoldNume()%>" onChange="getAreaData();" > 
			&nbsp;<font color="red">*</font>權利分母：
			<input class="field" type="text" name="newBuHoldDeno" size="10" maxlength="10" value="<%=obj.getNewBuHoldDeno()%>" onChange="getAreaData();" >
			　權利面積(㎡)：[<input class="field_RO" type="text" name="newBuHoldArea" size="9" maxlength="9" value="<%=obj.getNewBuHoldArea()%>" onChange="getAreaData();">]
		</td>
	</tr>
	<tr name="div_rf">
		<td class="td_form" colspan="2">土地改良物計量數：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="adjustMeasure" size="9" maxlength="9" value="<%=obj.getAdjustMeasure()%>">
			調整前：[<input class="field_RO" type="text" name="oldMeasure" size="9" maxlength="9" value="<%=obj.getOldMeasure()%>">]
			&nbsp;&nbsp;
			增加：<input class="field" type="text" name="addMeasure" size="9" maxlength="9" value="<%=obj.getAddMeasure() %>" onchange="adjustMeasure_Event(this.name);">
			&nbsp;&nbsp;
			減少：<input class="field" type="text" name="reduceMeasure" size="9" maxlength="9" value="<%=obj.getReduceMeasure() %>" onchange="adjustMeasure_Event(this.name);">
			&nbsp;&nbsp;
			調整後：[<input class="field_RO" type="text" name="newMeasure" size="9" maxlength="9" value="<%=obj.getNewMeasure()%>">]
		</td>
	</tr>
	
	<tr name="div_vp">
		<td class="td_form" colspan="2">有價證券股數：</td>
		<td class="td_form_white" colspan="3">
			調整前：[<input class="field_RO" type="text" name="oldBookAmount" size="9" maxlength="9" value="<%=obj.getOldBookAmount()%>">]
			&nbsp;&nbsp;
			增加：<input class="field" type="text" name="addBookAmount" size="9" maxlength="9" value="<%=obj.getAddBookAmount()%>" onchange="checkVPBookAmount(this.name);">
			&nbsp;&nbsp;
			減少：<input class="field" type="text" name="reduceBookAmount" size="9" maxlength="9" value="<%=obj.getReduceBookAmount()%>" onchange="checkVPBookAmount(this.name);">
			&nbsp;&nbsp;
			調整後：[<input class="field_RO" type="text" name="newBookAmount" size="9" maxlength="9" value="<%=obj.getNewBookAmount()%>">]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" width="10%" rowspan="6">帳務資料：</td>		
		<td class="td_form" >財產取得日期：</td>
		<td class="td_form_white" colspan="3">			
			[<input type="text" name="sourceDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
			&nbsp;&nbsp;
			原始入帳價值：[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">]
			&nbsp;&nbsp;
			購置日期：
			[<input type="text" name="buyDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" width="10%">帳務摘要：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" width="10%">調整前：</td>
		<td class="td_form_white" colspan="3">
			使用月數：
			[<input type="text" class="field_RO" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
			原值：
			[<input type="text" class="field_RO" name="oldBookValue" value="<%=obj.getOldBookValue() %>" size="10" >]
			&nbsp;&nbsp;
			累計折舊：
			[<input type="text" class="field_RO" name="oldAccumDeprTemp" value="<%=obj.getOldAccumDepr() %>" size="10" >]		
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="oldNetValue" value="<%=obj.getOldNetValue() %>" size="10" >]			
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" width="10%">增加：</td>
		<td class="td_form_white" colspan="3">
			使用月數：
			<input type="text" class="field" name="addLimitYear" value="<%=obj.getAddLimitYear() %>" size="10" onchange="changeOtherLimitYear();checkDeprMethod();" onblur="changeOtherLimitYear();checkDeprMethod();">
			&nbsp;&nbsp;
			超過使用年限月數： <font color="red"> <%=obj.getOverLimitYear2() %> 個月</font> 
			<input type="text" class="field" name="overLimitYear" value="<%=obj.getOverLimitYear() %>" size="10" onchange="changeOtherLimitYear();checkDeprMethod();" onblur="changeOtherLimitYear();checkDeprMethod();">
			#註1 &nbsp;&nbsp;
			原值：
			<input type="text" class="field" name="addBookValue" value="<%=obj.getAddBookValue() %>" size="10" onchange="changeAdjustUnit(this.name);" onblur="changeAdjustUnit(this.name);">
			 <br> 
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="addNetValue" value="<%=obj.getAddNetValue() %>" size="10">]
			<br> 
			<font color="red">註1:如本次增值會增加使用月數，則依顯示之紅色月數填入欄位</font> 
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" width="10%">減少：</td>
		<td class="td_form_white" colspan="3">
			使用月數：
			<input type="text" class="field" name="reduceLimitYear" value="<%=obj.getReduceLimitYear() %>" size="10" onchange="changeOtherLimitYear();checkDeprMethod();" onblur="changeOtherLimitYear();checkDeprMethod();">
			&nbsp;&nbsp;
			原值：
			<input type="text" class="field" name="reduceBookValue" value="<%=obj.getReduceBookValue() %>" size="10"  onchange="changeAdjustUnit(this.name);" onblur="changeAdjustUnit(this.name);">
			&nbsp;&nbsp;&nbsp;&nbsp;
			本年度累計折舊：
			[<input type="text" class="field_RO" name="reduceAccumDepr1" value="" size="10" >]
			以前年度累計折舊：
			[<input type="text" class="field_RO" name="reduceAccumDepr2" value="" size="10" >]
			累計折舊：
			[<input type="text" class="field_RO" name="reduceAccumDepr" value="<%=obj.getReduceAccumDepr() %>" size="10" >]		
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="reduceNetValue" value="<%=obj.getReduceNetValue() %>" size="10" >]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" width="10%">調整後：</td>
		<td class="td_form_white" colspan="3">
			使用月數：
			[<input class="field_RO" type="text" name="newOtherLimitYear" size="10" maxlength="10" value="<%=obj.getNewOtherLimitYear()%>">]
			原值：
			[<input type="text" class="field_RO" name="newBookValue" value="<%=obj.getNewBookValue() %>" size="10" >]
			&nbsp;&nbsp;
			累計折舊：
			[<input type="text" class="field_RO" name="newAccumDeprTemp" value="<%=obj.getNewAccumDepr() %>" size="10" >]		
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="newNetValue" value="<%=obj.getNewNetValue() %>" size="10" >]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" colspan="2">移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位：
			<select class="field_RO" type="select" name="keepUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getKeepUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;保管人：
			<select class="field_RO" type="select" name="keeper">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getKeeper())%>
			</select>
			<br>
			使用單位：
			<select class="field_RO" type="select" name="useUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getUseUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;使用人：
			<select class="field_RO" type="select" name="userNo">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getUserNo())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			使用註記：
			[<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">]		        
	        <br>
			存置地點：
			[<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>" >]
			[<input class="field_RO" type="text" name="place1name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
			<br>		
			存置地點說明：
			[<input class="field_RO" type="text" name="place" size="45" maxlength="45" value="<%=obj.getPlace() %>">]
		</td>
	</tr>	
	
	<tr name="div_olddeprmethod">
		<td class="td_form" colspan="2">調整前折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法
			<%=util.View._getSelectHTML_withFunction("field_RO", "oldDeprMethod", obj.getOldDeprMethod(),"DEP", "checkDeprMethod();queryDeprUnitData();") %>
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" name="cb_oldBuildFeeCB" size="10" maxlength="10" onclick="checkOriginalBuildFeeCB();" <%=("Y".equals(obj.getOldBuildFeeCB())?"checked":"")%>>
			<input class="field" type="hidden" name="oldBuildFeeCB" size="10" maxlength="10" value="<%=obj.getOldBuildFeeCB()%>">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" name="cb_oldDeprUnitCB" size="10" maxlength="10" onclick="checkOriginalDeprUnitCB();checkDeprMethod();" <%=("Y".equals(obj.getOldDeprUnitCB())?"checked":"")%>>
			<input class="field" type="hidden" name="oldDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOldDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別
			<select class="field_RO" type="select" name="oldDeprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field_RO" type="select" name="oldDeprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprUnit())%>
			</select>
			<input type="hidden" class="field" name="originalDeprUnittemp" value="">
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_RO" type="select" name="oldDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field_RO" type="select" name="oldDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOldDeprAccounts())%>
			</select>
			<br>
			殘值
			[<input class="field_RO" type="text" name="oldScrapValue" size="10" maxlength="10" value="<%=obj.getOldScrapValue()%>">]
			&nbsp;&nbsp;
			應攤提折舊總額
			[<input class="field_RO" type="text" name="oldDeprAmount" size="20" maxlength="15" value="<%=obj.getOldDeprAmount()%>">]
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="oldAccumDepr" size="20" maxlength="15" value="<%=obj.getOldAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="oldApportionMonth" size="10" maxlength="3" value="<%=obj.getOldApportionMonth()%>">]
			月提折舊金額
			[<input class="field_RO" type="text" name="oldMonthDepr" size="10" value="<%=obj.getOldMonthDepr()%>">]
			<br>
			月提折舊金額(最後一個月)
			[<input class="field_RO" type="text" name="oldMonthDepr1" size="10" value="<%=obj.getOldMonthDepr1()%>">]
			攤提年限截止年月
			[<input class="field_RO" type="text" name="oldApportionEndYM" size="10" value="<%=obj.getOldApportionEndYM()%>">]
		</td>
	</tr>
	
	<tr name="div_newdeprmethod">
		<td class="td_form" colspan="2">調整後折舊資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>折舊方法
			<%=util.View._getSelectHTML_withFunction("field", "newDeprMethod", obj.getNewDeprMethod(),"DEP", "checkDeprMethod();queryDeprUnitData();") %>
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="cb_newBuildFeeCB" size="10" maxlength="10" onclick="checkNewBuildFeeCB();" <%=("Y".equals(obj.getNewBuildFeeCB())?"checked":"")%>>
			<input class="field" type="hidden" name="newBuildFeeCB" size="10" maxlength="10" value="<%=obj.getNewBuildFeeCB()%>">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="cb_newDeprUnitCB" size="10" maxlength="10" onclick="checkNewDeprUnitCB();checkDeprMethod();" <%=("Y".equals(obj.getNewDeprUnitCB())?"checked":"")%>>
			<input class="field" type="hidden" name="newDeprUnitCB" size="10" maxlength="10" value="<%=obj.getNewDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別
			<select class="field" type="select" name="newDeprPark" onchange="form1.newDeprPark.value = this.value;">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprPark())%>
			</select>
			<input type="hidden" class="field_QRO" name="deprPark" value="<%=obj.getNewDeprPark()%>">
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field" type="select" name="newDeprUnit" onchange="queryDeprUnitData();">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprUnit())%>
			</select>
			<input type="hidden" class="field" name="newDeprUnittemp" value="">
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field" type="select" name="newDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;			
			會計科目
			<select class="field" type="select" name="newDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprAccounts())%>
			</select>
			<br>
			殘值
			<input class="field_RO" type="text" name="newScrapValue" size="10" maxlength="10" value="<%=obj.getNewScrapValue()%>" onchange="checkDeprMethod();">
			&nbsp;&nbsp;
			應攤提折舊總額
			<input class="field_RO" type="text" name="newDeprAmount" size="20" maxlength="15" value="<%=obj.getNewDeprAmount()%>" >
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="newAccumDepr" size="20" maxlength="15" value="<%=obj.getNewAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="newApportionMonth" size="10" maxlength="3" value="<%=obj.getNewApportionMonth()%>">]
			月提折舊金額
			[<input class="field_RO" type="text" name="newMonthDepr" size="10" value="<%=obj.getNewMonthDepr()%>">]
			<br>
			月提折舊金額(最後一個月)
			[<input class="field_RO" type="text" name="newMonthDepr1" size="10" value="<%=obj.getNewMonthDepr1()%>">]			
			攤提年限截止年月
			[<input class="field_RO" type="text" name="newApportionEndYM" size="10" value="<%=obj.getNewApportionEndYM()%>">]
		</td>
	</tr>	
	<tr name="div_common">
		<td class="td_form" colspan="2">主要材質：</td>
		<td class="td_form_white" colspan="3">
			主要材質：
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
		</td>
	</tr>
	<tr name="div_rt">
		<td class="td_form" colspan="2">有價證券：</td>
		<td class="td_form_white" colspan="3">
			發行法人名稱：
			[<input class="field_RO" type="text" name="securityName" size="20" maxlength="20" value="<%=obj.getSecurityName() %>">]				
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" colspan="2">財產性質： </td>
		<td class="td_form_white" colspan="3">
			<%=util.View._getSelectHTML("field_RO", "propertyKind", obj.getPropertyKind(),"PKD") %>
			　　　　　　　　基金財產：
			<%=util.View._getSelectHTML_withEnterOrg("field_RO", "fundType", obj.getFundType(),"FUD",obj.getEnterOrg()) %>			
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" colspan="2">珍貴財產：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="valuable">
			　　<%=util.View.getTextOption("Y;是;N;否;",obj.getValuable())%>
			</select>		
			　　　　　　　抵繳遺產稅：	
			<select class="field_RO" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" colspan="2">備註：</td>
		<td class="td_form_white"colspan="4">
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
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
	<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
	<input type="hidden" name="closing1ym" value="<%=obj.getClosing1ym()%>">
	
			
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatch">
		<input class="toolbar_default" type="button" followPK="true" name="btnBatch" value="現有資料明細新增" onclick="gountch003f03();">&nbsp;|
	</span>
	<span id="batch">
	<input class="toolbar_default" type="button" followPK="false" name="batchOpen" value="增減值資料修改" onClick="gountch003f05();">&nbsp;|
	</span>
	<span id="spanValue">
		<input class="toolbar_default" type="button" followPK="true" name="btnValue" value="土地公告地價調整" onclick="gountla021f();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">土地建物標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">增減值原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">增加價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">減少價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,false,true,true,true,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,true,true,true,false,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>

<script type="text/javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
			initFormDisabled();
			checkDeprMethod();
			break;				
		case "update":
			checkDeprMethod();
			break;
	}
}

</script>
</body>
</html>
