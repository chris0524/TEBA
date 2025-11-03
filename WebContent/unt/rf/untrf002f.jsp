<!--
程式目的：土地改良物主檔資料維護--基本資料
程式代號：untrf002f
程式日期：0940923
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rf.UNTRF002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rf.UNTRF002F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	obj = (unt.rf.UNTRF002F)obj.queryOne();	
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._RF_ADD, 3);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._RF_ADD, 2);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._RF_ADD, 2);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._RF_ADD, 2);
}else if("untch001f02extend01".equals(obj.getProgID())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._RF_ADD, 2);
}else if("untch001f02extend02".equals(obj.getProgID())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._RF_ADD, 2);
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>

<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("valuable","N"),
	new Array("taxCredit","N"),	
	new Array("useUnit","<%=user.getOrganID()%>"),
	new Array("useUnitName","<%=user.getOrganName()%>"),		
	new Array("accumDepr","0"),
	new Array("deprMethod", "01"),
	new Array("isAccumDepr","N")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[1].checked) {
			form1.action = "untrf002f.jsp";
		} else {
			form1.action = "untch001f02.jsp";
		}
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"土地增加單-電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");		
		columnTrim(form1.originalLimitYear);
		if (parseInt(form1.otherLimitYear.value)<=0) {
			alertStr += "請輸入調整後年限(月)(必須>0)!\n";
		}
	
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.reduceDate,"減損日期")
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		alertStr += checkEmpty(form1.taxCredit,"抵繳遺產稅");
		alertStr += checkEmpty(form1.originalMeasure,"原始計量數");
		if (form1.propertyUnit.value=="座" || form1.propertyUnit.value=="套" || form1.propertyUnit.value=="條") {
			form1.originalMeasure.value = "1";
		} else {
			if (parse(form1.originalMeasure.value).length>0) alertStr += checkFloat(form1.originalMeasure,"原始計量數", 7, 2);		
		}

		alertStr += checkEmpty(form1.originalBV,"原始入帳價值");
		alertStr += checkInt(form1.originalBV,"原始入帳價值");
		alertStr += checkInt(form1.bookValue,"帳面價值");
		alertStr += checkEmpty(form1.deprMethod,"折舊方法");
/*		
		alertStr += checkInt(form1.scrapValue,"預留殘值");
		alertStr += checkInt(form1.deprAmount,"應攤提折舊總額");
		alertStr += checkInt(form1.apportionYear,"攤提年限");
		alertStr += checkInt(form1.monthDepr,"月攤提折舊總額");
		alertStr += checkDate(form1.sourceDate, "財產取得日期");
*/		
		alertStr += checkLen(form1.notes, "備註", 250);
		alertStr += checkEmpty(form1.sourceKind, "財產種類");
		alertStr += checkEmpty(form1.sourceDate, "財產取得日期");
	
/*		
		if (form1.valuable.value=="N") {
			if (!parseInt(form1.originalBV.value)>0) {
				alertStr += "原始入帳價值必須大於0\n";
				form1.originalBV.style.backgroundColor = errorbg;
			} else {
				form1.originalBV.style.backgroundColor = "";
			}
		} else {
			getSelectedValue(form1.valuable, "Y");
			if (parseInt(form1.originalBV.value)>=0) {
				form1.originalBV.style.backgroundColor = "";			
			} else {
				alertStr += "原始入帳價值必須大於或等於0\n";
				form1.originalBV.style.backgroundColor = errorbg;
			}
		}
*/		
		
		//if(form1.propertyKind.value=="03") alertStr += checkEmpty(form1.fundType,"基金財產");
		//else { form1.fundType.options[0].selected=true; form1.fundType.style.backgroundColor = ""; }
		
	   //if(form1.propertyKind.value=="01"){ form1.fundType.disabled = false;form1.fundType.options[0].selected=true;}
	   //else {  form1.fundType.disabled = true; }
			
		if(form1.cause.value=="99") alertStr += checkEmpty(form1.cause1,"其他說明");
		else { form1.cause1.value = ""; form1.cause1.style.backgroundColor = ""; }
		
		if(form1.fundsSource.value == "03") alertStr += checkEmpty(form1.fundsSource1,"其他說明");
		else { form1.fundsSource1.value = ""; form1.fundsSource1.style.backgroundColor = ""; }

		if(form1.verify.value=="N"){		
			//changeArea();	
			form1.bookValue.value =  form1.originalBV.value;
		}
		
		
/*		
		if (parseInt(form1.deprMethod.value)>1 && parseInt(form1.deprMethod.value)<5) {		
			if (form1.isAccumDepr.value=="Y") {
				if (parseFloat(form1.scrapValue.value)<0 || parseFloat(form1.bookValue.value)<parseFloat(form1.scrapValue.value))
				alertStr += "帳面帳面應>=預留殘值>=0, 請重新輸入!\n";
				
				if (form1.accumDeprYM.value<getDateAdd("m", -1, form1.buildDate.value.substring(0,5)+"01").substring(0,5))
				alertStr += "累計折舊調整年月應>=建築日期之年月-1個月, 請重新輸入!\n";
				
				if (form1.accumDeprYM.value>form1.apportionEndYM.value)
				alertStr += "累計折舊調整年月應<=攤提年限截止年月, 請重新輸入!\n"
				
				if (form1.deprMethod.value=="03" || form1.deprMethod.value=="04") {
					if (parseInt(form1.deprAmount.value)<parseInt(form1.accumDepr.value) || form1.accumDepr.value<0) 
					alertStr += "應攤提折舊總額應>=累計折舊調整金額>=0, 請重新輸入!\n";															
				}
			} else {
				if (parseFloat(form1.scrapValue.value)<0 || parseFloat(form1.bookValue.value)<parseFloat(form1.scrapValue.value))
				alertStr += "帳面價值應>=預留殘值>=0, 請重新輸入!\n";			
				
				if (form1.accumDeprYM.value<getDateAdd("m", -1, form1.buildDate.value.substring(0,5)+"01").substring(0,5))
				alertStr += "累計折舊調整年月應>=建築日期之年月-1個月, 請重新輸入!\n";
				
				if (form1.accumDeprYM.value>form1.apportionEndYM.value)
				alertStr += "累計折舊調整年月應<=攤提年限截止年月, 請重新輸入!\n"	
				
				if (form1.deprMethod.value=="03" || form1.deprMethod.value=="04") {
					if ((parseInt(form1.accumDepr.value)<0) || (parseInt(form1.bookValue.value)<parseInt(form1.accumDepr.value)))
					alertStr += "帳面價值應>=累計折舊調整>=0, 請重新輸入!\n";			
				}			
				if (form1.deprMethod.value=="02") {
					if ((parseInt(form1.accumDepr.value)<0) || (parseInt(form1.accumDepr.value)>(parseFloat(form1.bookValue.value)-parseFloat(form1.scrapValue.value))))
					alertStr += "(帳面價值應-預留殘值)應>=累計折舊調整>=0, 請重新輸入!\n";
				}			
			}
		}
*/		
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();	
}


function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,serialNo){
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
	if (form1.state.value=="insert" || form1.state.value=="insertError" || form1.state.value=="update" || form1.state.value=="updateError") {	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="../ch/untch001f02.jsp"){
			if('<%=obj.getIsAddProof()%>' != 'true'){
				form1.state.value="queryAll";
			}
		} else {
            form1.state.value="queryAll";
            alertStr += checkEmpty(form1.propertyNo,"財產編號－類項目節");
            alertStr += checkEmpty(form1.serialNo,"財產編號－類項目節");         
        }
        alertStr += checkEmpty(form1.enterOrg,"入帳機關");
        alertStr += checkEmpty(form1.ownership,"權屬");
        alertStr += checkEmpty(form1.caseNo,"增加單-電腦單號");
        if(alertStr.length!=0){ 
            alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
            return false;
        }
    	if(surl=='../ch/untch001f01.jsp' || surl=='../ch/untch001f02_1.jsp' || surl=='../ch/untch001f02_2.jsp') {
    		form1.mainPage1.value="";
    		form1.currentPage.value=form1.mainPage.value;
    	}
    	
    	form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
    	form1.queryone_ownership.value=form1.mainOwnerShip.value;
    	form1.queryone_caseNo.value=form1.mainCaseNo.value;
    	form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function changeSelect(){
	if (form1.state.value=="insert" || form1.state.value=="insertError" || form1.state.value=="update" || form1.state.value=="updateError") {	
		//當增加原因選「其他」時，開放其他說明欄位
		if(form1.cause.value == "99") form1.cause1.readOnly = false;
		else { form1.cause1.value=""; form1.cause1.readOnly = true; }
		
		//當經費來源選「其他補助款」時，開放其他說明欄位	
		if(form1.fundsSource.value == "03") form1.fundsSource1.readOnly = false;
		else { form1.fundsSource1.value=""; form1.fundsSource1.readOnly = true; }
		
		//財產性質為「03:事業用」時，須控制「基金財產」必須有資料	
		//if(form1.propertyKind.value == "03") form1.fundType.disabled = false;
		//else { form1.fundType.options[0].selected=true; form1.fundType.disabled = true; }			
		//財產性質為非「01:公務用」時，須控制「基金財產」不能有資料	
	   if(form1.propertyKind.value=="01"){ form1.fundType.disabled = false;form1.fundType.options[0].selected=true;}
	   else {  form1.fundType.disabled = true; }
	    
		//form1.fundType.options[0].selected=true;
		//if (form1.isAccumDepr.value=="N") setFormItem("scrapValue,accumDeprYM,accumDepr","R");
		//else if (form1.isAccumDepr.value=="Y") setFormItem("scrapValue,accumDeprYM,accumDepr","O");			

		if (form1.isAccumDepr.value=="N") setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","R");
		else if (form1.isAccumDepr.value=="Y") {
			if (form1.deprMethod.value=="02" || form1.deprMethod.value=="03") setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","O");
			else if (form1.deprMethod.value=="04") {
				setFormItem("scrapValue","R");
				setFormItem("accumDeprYM,accumDepr,apportionYear,deprAmount","O");
			} else {
				setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","R");
			}
		}		
	}
}


function changeDeprMethod(){	
	var apportionYear = parseInt(form1.apportionYear.value);	
	var tempBuildDate = "";
	if (parse(form1.buildDate.value).length==7) tempBuildDate = form1.buildDate.value.substring(0,5)+"01";
	if (form1.isAccumDepr.value=="Y") {
		var intMonth;		
		if (form1.accumDeprYM.value.length!=5) form1.accumDeprYM.value = getDateAdd("m", -1, tempBuildDate).substring(0,5);
		//form1.buildDate.value.substring(0,5);		

		switch (form1.deprMethod.value) {
			case "02" :
				if(form1.deprAmount.value.length<=0){
					form1.deprAmount.value = parseInt(form1.bookValue.value) - parseInt(form1.scrapValue.value);
				}else{
					form1.deprAmount.value = parseInt(form1.deprAmount.value);
				}
				form1.apportionEndYM.value = getDateAdd("m", parseInt(form1.apportionYear.value)*12-1,tempBuildDate).substring(0,5);
				form1.tempField1.value = form1.apportionEndYM.value+"01";
				form1.tempField2.value = form1.accumDeprYM.value+"01";
				intMonth = getDateDiff("m",form1.tempField2,form1.tempField1);
				form1.monthDepr.value = Math.round((parseInt(form1.deprAmount.value)-parseInt(form1.accumDepr.value))/parseInt(intMonth));
				break;
				
			case "03" :
				if(form1.deprAmount.value.length<=0){
					form1.deprAmount.value = form1.bookValue.value;
				}else{
					form1.deprAmount.value = form1.deprAmount.value;
				}
				//原程式輸入多少就加1
				//form1.apportionYear.value = parseInt(apportionYear)+1;
				//更改!依使用者輸入多少即多少
				form1.apportionYear.value = parseInt(apportionYear);
				form1.apportionEndYM.value = getDateAdd("m", parseInt(form1.apportionYear.value+1)*12-1,tempBuildDate).substring(0,5);
				form1.tempField1.value = form1.apportionEndYM.value+"01";
				form1.tempField2.value = form1.accumDeprYM.value+"01";
				intMonth = getDateDiff("m",form1.tempField2,form1.tempField1);
				form1.monthDepr.value = Math.round((parseInt(form1.deprAmount.value)-parseInt(form1.accumDepr.value))/parseInt(intMonth));				
				break;
				
			case "04" ://真線法,不留殘值
				//應提列折舊金額
				if(form1.deprAmount.value.length<=0){
					form1.deprAmount.value = form1.bookValue.value;
				}else{
					form1.deprAmount.value = form1.deprAmount.value;
				}
				//原程式輸入多少就加1更改!依使用者輸入多少即多少
				//form1.apportionYear.value = parseInt(apportionYear)+1;
				
				//攤提年限
				form1.apportionYear.value = parseInt(apportionYear);
				
				//攤提年限截止年月
				form1.apportionEndYM.value = getDateAdd("m", parseInt(form1.apportionYear.value)*12-1,tempBuildDate).substring(0,5);
				
				//折舊起年度
				form1.tempField2.value = form1.accumDeprYM.value+"01";//取累計折舊調整年月
				
				//折舊年度訖    
				form1.tempField1.value = form1.apportionEndYM.value+"01";//取攤提年限截止年月
				alert("起"+form1.tempField2.value+"訖"+form1.tempField1.value);
				
				intMonth = getDateDiff("m",form1.tempField2,form1.tempField1);
				alert("糸統"+intMonth);	

				//intM
				onth = monthdiff(form1.tempField2.value,form1.tempField1.value);		
				//alert("自已"+intMonth);
				
				
				//月提折舊金額
				//form1.monthDepr.value = Math.round((parseInt(form1.deprAmount.value)-parseInt(form1.accumDepr.value))/parseInt(intMonth));
				if(form1.apportionYear.value.length >=0 ){
					form1.monthDepr.value = Math.round((parseInt(form1.deprAmount.value ) / ( parseInt(form1.apportionYear.value) * 12 )));
				}else if(form1.originalLimitYear.value.length >= 0){
					form1.monthDepr.value = Math.round((parseInt(form1.deprAmount.value ) / ( parseInt(form1.originalLimitYear.value) * 12 )));
				}
				break;
						
			case "05" :
				form1.scrapValue.value = 0;
				form1.deprAmount.value = "";
				form1.apportionYear.value = "";
				form1.monthDepr.value = "";
				form1.apportionEndYM.value = "";
				form1.accumDeprYM.value = "";
				form1.accumDepr.value = "";						
				break;
				
			default : 
				form1.scrapValue.value = "";
				form1.deprAmount.value = "";
				form1.apportionYear.value = "";
				form1.monthDepr.value = "";
				form1.apportionEndYM.value = "";
				form1.accumDeprYM.value = "";
				form1.accumDepr.value = "";	
				break;
		}
	} else {
		switch (form1.deprMethod.value) {
			case "02" :
				form1.scrapValue.value = Math.round(parseInt(form1.bookValue.value)/(parseInt(apportionYear)+1));
				if(form1.deprAmount.value.length<=0){
					form1.deprAmount.value = parseInt(form1.bookValue.value) - parseInt(form1.scrapValue.value);
				}else{
					form1.deprAmount.value = parseInt(form1.deprAmount.value);
				}
				form1.monthDepr.value = Math.round(parseInt(form1.deprAmount.value)/parseInt(apportionYear)/12);
				form1.apportionEndYM.value = getDateAdd("m", parseInt(apportionYear)*12-1,tempBuildDate).substring(0,5);
				form1.accumDeprYM.value = getDateAdd("m", -1, tempBuildDate).substring(0,5);
				//form1.buildDate.value.substring(0,5);
				form1.accumDepr.value = 0;			
				break;
				
			case "03" :
				form1.scrapValue.value = Math.round(parseInt(form1.bookValue.value)/(parseInt(apportionYear)+1));
				if(form1.deprAmount.value.length<=0){
					form1.deprAmount.value = form1.bookValue.value;
				}else{
					form1.deprAmount.value = form1.deprAmount.value;
				}
				form1.apportionYear.value = parseInt(apportionYear)+1;
				form1.monthDepr.value = Math.round(parseInt(form1.deprAmount.value)/(parseInt(apportionYear)+1)/12);
				form1.apportionEndYM.value = getDateAdd("m", (apportionYear+1)*12-1,tempBuildDate).substring(0,5);
				form1.accumDeprYM.value = getDateAdd("m", -1, tempBuildDate).substring(0,5);
				form1.accumDepr.value = 0;				
				break;
				
			case "04" :
				form1.scrapValue.value = 0;
				if(form1.deprAmount.value.length<=0){
					form1.deprAmount.value = form1.bookValue.value;
				}else{
					form1.deprAmount.value = form1.deprAmount.value;
				}
				form1.apportionYear.value = parseInt(apportionYear)+1;
				form1.monthDepr.value = Math.round(parseInt(form1.deprAmount.value)/(parseInt(apportionYear)+1)/12);
				form1.apportionEndYM.value = getDateAdd("m", parseInt(form1.apportionYear.value)*12-1,tempBuildDate).substring(0,5);
				form1.accumDeprYM.value = getDateAdd("m", -1, tempBuildDate).substring(0,5);
				form1.accumDepr.value = 0;				
				break;
				
			case "05" :
				form1.scrapValue.value = 0;
				form1.deprAmount.value = "";
				form1.apportionYear.value = "";
				form1.monthDepr.value = "";
				form1.apportionEndYM.value = "";
				form1.accumDeprYM.value = "";
				form1.accumDepr.value = "";						
				break;
				
			default : 
				form1.scrapValue.value = "";
				form1.deprAmount.value = "";
				form1.apportionYear.value = "";
				form1.monthDepr.value = "";
				form1.apportionEndYM.value = "";
				form1.accumDeprYM.value = "";
				form1.accumDepr.value = "";	
				break;					
		}
	}

}
//取得期間月份...有誤
function monthdiff(dateS,dateE){
	var dateSE=0;
	dateSE=(dateE.substring(0,3)-dateS.substring(0,3)) * 12;
	dateSE=dateSE + ((12-dateS.substring(3,5))) + parseInt(dateE.substring(3,5));
	return dateSE;
}
//更新面積,金額等資料在這啦
function changeArea(){
	var apportionYear;	
	columnTrim(form1.originalLimitYear);
	columnTrim(form1.otherLimitYear);	
	if (parseInt(form1.originalLimitYear.value)>0) {
		//form1.otherLimitYear.value = "";
		if(form1.apportionYear.value.length<=0){
			form1.apportionYear.value = parseInt(form1.originalLimitYear.value);
			apportionYear = parseInt(form1.originalLimitYear.value);
		}else{
			form1.apportionYear.value = form1.apportionYear.value;
			apportionYear = form1.apportionYear.value;
		}
	} else if (parseInt(form1.otherLimitYear.value)>0) {
		if(form1.apportionYear.value.length<=0){
			form1.apportionYear.value = parseInt(form1.otherLimitYear.value);
			apportionYear = parseInt(form1.otherLimitYear.value);
		}else{
			form1.apportionYear.value = form1.apportionYear.value;
			apportionYear = form1.apportionYear.value;
		}
	} else { alert("請輸入調整後年限(月)"); return false; };
	
	if (form1.propertyUnit.value=="座" || form1.propertyUnit.value=="套" || form1.propertyUnit.value=="條") {
		form1.originalMeasure.value = "1";
	}	
	form1.originalBV.value = Math.round(form1.originalBV.value);	
	//帳面價值 = 原始入帳金額
	form1.measure.value = form1.originalMeasure.value;
	if (form1.verify.value=="Y") {} else {	
		form1.bookValue.value = form1.originalBV.value;	
	}
	form1.damageExpire.value = getDateAdd("m",6,form1.damageDate);
	form1.useEndYM.value = getDateAdd("m", parseInt(apportionYear)*12-1,getDateAdd("m",-1,form1.buildDate)).substring(0,5);	
	
	form1.permitReduceDate.value = getDateAdd("y",parseInt(form1.originalLimitYear.value),form1.buildDate);
	//form1.permitReduceDate.value = getDateAdd("y",parseInt(apportionYear),form1.buildDate); 0121
	
	//if (form1.isAccumDepr.value=="N") setFormItem("scrapValue,accumDeprYM,accumDepr","R");
	//else if (form1.isAccumDepr.value=="Y") setFormItem("scrapValue,accumDeprYM,accumDepr","O");	
	
	if (form1.isAccumDepr.value=="N") setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","R");
	else if (form1.isAccumDepr.value=="Y") {
		if (form1.deprMethod.value=="02" || form1.deprMethod.value=="03") setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","O");
		else if (form1.deprMethod.value=="04") {
			setFormItem("scrapValue","R");
			setFormItem("accumDeprYM,accumDepr,apportionYear,deprAmount","O");
		} else {
			setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","R");
		}
	}		
	changeDeprMethod();	
}

function changeUseUnit() {
	if (parse(form1.useUnit.value).length>0) {
		form1.useUnit1.value = "";
		setFormItem("useUnit1","R");
	} else {
		setFormItem("useUnit1","O");
	}
}

function clearUseUnit() {
	if (parse(form1.useUnit.value).length>0) {
		form1.useUnit.value = "";
		form1.useUnitName.value = "";
	}
}

//[入帳]及[月結]欄位狀態控制 
function doSpecialField() {
	var arrField = new Array( "otherLimitYear","buildDate","cause","cause1",
							  "propertyKind","fundType","valuable","taxCredit","nonProof",
							  "proofDoc",
							  "originalBV","originalNote","deprMethod"
							 );	
	if (form1.verify.value=="Y") {
		setFormField(arrField,"R");
		setFormItem("otherLimitYear,buildDate,btn_buildDate,cause,cause1,propertyKind,fundType,valuable,taxCredit,nonProof,proofDoc,sourceKind,sourceDate,btn_sourceDate,sourceDoc,originalBV,originalNote,deprMethod,originalMeasure","R");
	}
}

function checkSpecialField() {
	if(form1.dataState.value == "1"){
		var arrField = new Array("cause","cause1","sourceKind","sourceDate","sourceDoc","otherLimitYear");
		if (form1.verify.value=="Y") {
			setFormField(arrField,"O");
			
		}
	}
}

function init(){
	if (isObj(document.all.item("picture")) && (parse(document.all.item("picture").value)).length>0) setFormItem("btn_pictureDownload","O");
//	var caseNo = parse(form1.caseNo.value);
//	if ((caseNo.length<=0) || (form1.enterOrg.value!=<%="\""+user.getOrganID()+"\""%>)) {
//		setFormItem("insert,update,delete,clear,confirm","R");
//	}	
//	changeSelect();	
//	if (form1.verify.value=="Y" || form1.closing.value=="Y")  {
//		setFormItem("insert,delete","R");
//	}
//	if (form1.dataState.value=="2")  {
//		setFormItem("insert,update,delete","R");
//	}	

	setDisplayItem("spanQueryAll,spanInsert,spanNextInsert","H");
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}

	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeepUnitQuickly, form1.originalKeepUnit, form1.originalUseUnitQuickly, form1.originalUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeeperQuickly, form1.originalKeeper, form1.originalUserQuickly, form1.originalUser, true, false);
	var dcc3 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keepUnitQuickly, form1.keepUnit, form1.useUnitQuickly, form1.useUnit, true, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keeperQuickly, form1.keeper, form1.userNoQuickly, form1.userNo, true, false);
	
	// 暫時不開放修改跟刪除, 因此頁面有太多bug , 僅當作跳到其他頁籤的跳板
	setDisplayItem("spanUpdate,spanDelete","H");
}


function changeMoveDate(){
	form1.moveDate.value = form1.originalMoveDate.value;
}

function changePlace(){
	form1.place.value = form1.originalPlace.value;
}

function changeKeep1(){
	changeKeepUnit(form1.tempEnterOrg, form1.originalKeepBureau, form1.originalKeepUnit,'');
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	changeBureau(form1.tempEnterOrg, form1.originalUseBureau, form1.originalKeepBureau.value);
	changeKeepUnit(form1.tempEnterOrg, form1.originalUseBureau, form1.originalUseUnit, '');
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}

function changeKeep2(){
	getKeeper(form1.tempEnterOrg, form1.originalKeepUnit, form1.originalKeeper, '');
	changeKeepUnit(form1.tempEnterOrg, form1.originalUseBureau, form1.originalUseUnit, form1.originalKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.originalUseUnit, form1.originalUser, '');
}


function changeKeep3(){	
	getKeepUnit(form1.tempEnterOrg, form1.keepUnit, form1.originalKeepUnit.value);
	getKeeper(form1.tempEnterOrg, form1.keepUnit, form1.keeper, form1.originalKeeper.value ,'N');
	getKeepUnit(form1.tempEnterOrg, form1.userUnit, form1.originalUseUnit.value);
	getKeeper(form1.tempEnterOrg, form1.userUnit, form1.userNo, form1.originalUser.value ,'N');
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField();">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--Query區============================================================-->
<!-- 保留第一頁查詢條件與頁數使用 -->
<div id="queryContainer2" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame2"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<% request.setAttribute("UNTCH001Q",obj2);%>
	<jsp:include page="../ch/untch001q02.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<tr>
        <td class="td_form">入帳機關：</td>
        <td class="td_form_white" colspan="3"><input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
        	[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">] 　&nbsp;&nbsp;權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>&nbsp;&nbsp;
    　			電腦單號： [<input class="field_QRO" type="text" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>">] </td>
		</tr>
		<tr>
			<td class="td_form">財產區分別：</td>
			<td colspan="3" class="td_form_white">
				<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
				&nbsp;&nbsp;&nbsp;		
				序號：
				[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo()%>">]
	       	</td>
		</tr>
		<tr>
			<td class="td_form">工程編號：</td>
			<td colspan="3" class="td_form_white">
	       		[<input class="field_PRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo()%>" onBlur="">]
				[<input class="field_PRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName()%>">]
	       	</td>
	    </tr>
	  	<tr>
        <td class="td_form">入帳日期：</td>
	        <td class="td_form_white" colspan="3">[<input class="field_QRO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]　資料狀態：
				<select class="field_RO" type="select" name="dataState">
	              	<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
	            </select>
	            　 		入帳：
	            	<select class="field_QRO" type="select" name="verify">
	              		<%=util.View.getYNOption(obj.getVerify())%>
	            	</select>
			</td>
		</tr>
		<tr>
        <td class="td_form"><font color="red">*</font>財產編號：</td>
        <td class="td_form_white" colspan="3">
        	<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"111&isLookup=Y")%> 　
        	分號：[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]<br>
    		別名： <input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
    　			原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">]
    　			原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
    	</td>
	    </tr>
		<tr>
	  	<td class="td_form">建築日期：</td>
	  		<td colspan="3" class="td_form_white">
	  			<input class="field" type="text" name="buildDate" size="7" maxlength="7" value="<%=obj.getBuildDate()%>" onChange="changeArea();"> <input class="field" type="button" name="btn_buildDate" onclick="popCalndar('buildDate','changeArea()')" value="..." title="萬年曆輔助視窗">
	  			
	  		</td>
		</tr>	
		<tr>
      		<td class="td_form"><font color="red">*</font>財產性質：</td>
      		<td colspan="3" class="td_form_white">
            	<select class="field" type="select" name="propertyKind" onChange="changeSelect();">
                	<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
            	</select>
            	基金財產：
                  <select class="field" type="select" name="fundType" >
                   changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType',form1.fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
                  </select>
            </td>
		</tr>
		<tr>
			<td class="td_form"><font color="red">*</font>珍貴財產：</td>
      		<td colspan="3" class="td_form_white">
				<select class="field" type="select" name="valuable">
	                <%=util.View.getYNOption(obj.getValuable())%>
	            </select>
	            抵繳遺產稅：
	            <select class="field" type="select" name="taxCredit">
	                <%=util.View.getYNOption(obj.getTaxCredit())%>
	            </select>
     		</td>
		</tr>
		<tr style="display:none;">
		<td class="td_form">增加原因：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="cause" onChange="changeSelect();">
                <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA'", obj.getCause())%>
            </select>
			其它說明：
            <input class="field" type="text" name="cause1" size="12" maxlength="12" value="<%=obj.getCause1()%>" readonly="true">
		</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>購置日期：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View.getPopCalndar("field","buyDate",obj.getBuyDate())%>
		</td>
	</tr>
	  	
	<tr>
      <td class="td_form"><font color="red">*</font>財產來源：</td>
      <td colspan="3" class="td_form_white"><select class="field" type="select" name="sourceKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKD' ", obj.getSourceKind())%>
        </select>
    　		<font color="red">*</font>財產取得日期：<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%> 　文號：<input class="field" type="text" name="sourceDoc" size="18" maxlength="20" value="<%=obj.getSourceDoc()%>"></td>
	  </tr>
	  
	<tr>
      <td class="td_form"><font color="red">*</font>計量數：</td>
      <td colspan="3" class="td_form_white">
      原始：<input name="originalMeasure" type="text" class="field" onChange="changeArea();" value="<%=obj.getOriginalMeasure()%>" size="15" maxlength="15">
      　目前：[<input name="measure" type="text" class="field_RO" value="<%=obj.getMeasure()%>" size="15" maxlength="15">]
      　單位：[<input class="field_RO" type="text" name="propertyUnit" size="7" value="<%=obj.getPropertyUnit()%>">]
	</td>
      </tr>
	<tr>
	  <td class="td_form">價值：</td>
	  <td colspan="3" class="td_form_white">
	  <font color="red">*</font>原始入帳價值：
	  <input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" onChange="changeArea();">
	  　原始入帳摘要：
	    <input class="field" type="text" name="originalNote" size="25" maxlength="15" value="<%=obj.getOriginalNote()%>">
	  <br>
		原值：
		[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>" onChange="changeArea();">]
		&nbsp;&nbsp;&nbsp;	  
		帳面價值(淨值)：
		[<input class="field_RO" type="text" name="netValue" size="15" maxlength="15" value="<%=obj.getNetValue() %>" >]
	  </td>
	</tr>
	
	
	  <tr>
       	  <td class="td_form">主要材質：</td>
	        <td class="td_form_white" colspan="3">
	        	主要材質：
	        	[<input class="field_RO" type="text" name="material" size="40" value="<%=obj.getMaterial()%>">] 　<br>
	   			 使用年限： [<input class="field_RO" type="text" name="originalLimitYear" size="7" maxlength="7" value="<%=obj.getOriginalLimitYear()%>">] 　
	   			 調整後年限(月)： <input class="field" type="text" name="otherLimitYear" size="7" maxlength="7" value="<%=obj.getOtherLimitYear()%>">
	    　		</td>
	    </tr>
		<tr>
			<td class="td_form">經費來源：</td>
			<td colspan="3" class="td_form_white">
				<select class="field" type="select" name="fundsSource" onChange="changeSelect();">
                	<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
            	</select>
            	<br/>
			其它說明：	
            <input class="field" type="text" name="fundsSource1" size="12" maxlength="12" value="<%=obj.getFundsSource1()%>" readonly="true">
 			會計科目：
            <input class="field" type="text" name="accountingTitle" size="10" maxlength="20" value="<%=obj.getAccountingTitle()%>">
		</tr>
	
	<tr>
      <td class="td_form">資產重估日：</td>
      <td colspan="3" class="td_form_white">[<input class="field_RO" type="text" name="appraiseDate" size="9" maxlength="7" value="<%=obj.getAppraiseDate()%>">]</td>
	  </tr>
	
	<tr>
		<td class="td_form">原始移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalKeepUnit", "originalKeepUnitQuickly", obj.getOriginalKeepUnit()) %>
			<input class="field" type="button" name="btn_originalKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'originalKeepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalKeeper", "originalKeeperQuickly", obj.getOriginalKeeper()) %>
	        <input class="field" type="button" name="btn_originalKeeper" onclick="popUnitMan(form1.enterOrg.value,'originalKeeper')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalUseUnit", "originalUseUnitQuickly", obj.getOriginalUseUnit()) %>
			<input class="field" type="button" name="btn_originalUseUnit" onclick="popUnitNo(form1.enterOrg.value,'originalUseUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalUser", "originalUserQuickly", obj.getOriginalUser()) %>
	        <input class="field" type="button" name="btn_originalUser" onclick="popUnitMan(form1.enterOrg.value,'originalUser')" value="..." title="人員輔助視窗">
	        <br/>
			使用註記
			<input type="text" class="field" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20">
	        <br>
			移動日期
	        <%=util.View.getPopCalndar("field","originalMoveDate",obj.getOriginalMoveDate())%>
	        <br>
			存置地點
			<input class="field" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1() %>">
			<input class="field" type="button" name="btn_originalPlace1" onclick="popPlace('originalPlace')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name() %>">]
			<br>		
			存置地點說明
			<input class="field" type="text" name="originalPlace" size="30" maxlength="30" value="<%=obj.getOriginalPlace() %>">
		</td>
	</tr>
	<tr>
		<td class="td_form">目前移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "keepUnit", "keepUnitQuickly", obj.getKeepUnit()) %>
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "keeper", "keeperQuickly", obj.getKeeper()) %>
	        <br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "useUnit", "useUnitQuickly", obj.getUseUnit()) %>
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "userNo", "userNoQuickly", obj.getUserNo()) %>
	        <br/>
			使用註記
			[<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">]
	        <br>
			移動日期
			[<input class="field_RO" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate() %>">]
			<br>
			存置地點
			[<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>">]
			[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
			<br>		
			存置地點說明
			[<input class="field_RO" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace() %>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">原始折舊資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>折舊方法
			<select name="originalDeprMethod" class="field" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getOriginalDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field" type="checkbox" id="originalBuildFeeCB" name="originalBuildFeeCB" size="10" maxlength="10" <%=("Y".equals(obj.getOriginalBuildFeeCB())?"checked":"")%>>
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_PRO" type="checkbox" name="cb_originalDeprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getOriginalDeprUnitCB())?"checked":"")%>>
			<input class="field_PRO" type="hidden" name="originalDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOriginalDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別
			<select class="field" type="select" name="originalDeprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field" type="select" name="originalDeprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field" type="select" name="originalDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field" type="select" name="originalDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprAccounts())%>
			</select>
			<br>
			殘值
			<input class="field" type="text" id="originalScrapValue" name="originalScrapValue" size="10" maxlength="10" value="<%=obj.getOriginalScrapValue()%>">
			&nbsp;&nbsp;
			應攤提折舊總額
			<input class="field" type="text" name="originalDeprAmount" size="20" maxlength="15" value="<%=obj.getOriginalDeprAmount()%>" onChange="changeArea();">
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="originalAccumDepr" size="20" maxlength="15" value="<%=obj.getOriginalAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			<input class="field" type="text" name="originalApportionMonth" size="10" maxlength="3" value="<%=obj.getOriginalApportionMonth()%>" onChange="changeArea();">
			月提折舊金額
			[<input class="field_RO" type="text" name="originalMonthDepr" size="10" value="<%=obj.getOriginalMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="originalMonthDepr1" size="10" value="<%=obj.getOriginalMonthDepr1()%>">]
			攤提年限截止年月
			[<input class="field_RO" type="text" name="originalApportionEndYM" size="10" value="<%=obj.getOriginalApportionEndYM()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">目前折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法
			<select name="deprMethod" class="field_RO" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="buildFeeCB" name="buildFeeCB" size="10" maxlength="10" <%=("Y".equals(obj.getBuildFeeCB())?"checked":"")%>>
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="deprUnitCB" name="deprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getDeprUnitCB())?"checked":"")%>>
			部門別依比例分攤
			<br>
			園區別
			<select class="field" type="select" name="deprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field" type="select" name="deprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field" type="select" name="deprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field" type="select" name="deprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprAccounts())%>
			</select>
			<br>
			殘值
			[<input class="field_RO" type="text" name="scrapValue" size="10" maxlength="10" value="<%=obj.getScrapValue()%>">]
			&nbsp;&nbsp;
			應攤提折舊總額
			[<input class="field_RO" type="text" name="deprAmount" size="20" maxlength="15" value="<%=obj.getDeprAmount()%>" onChange="changeArea();">]
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="accumDepr" size="20" maxlength="15" value="<%=obj.getAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="apportionMonth" size="10" maxlength="3" value="<%=obj.getApportionMonth()%>" onChange="changeArea();">]
			月提折舊金額
			[<input class="field_RO" type="text" name="monthDepr" size="10" value="<%=obj.getMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="monthDepr1" size="10" value="<%=obj.getMonthDepr1()%>">]			
			攤提年限截止年月
			[<input class="field_RO" type="text" name="apportionEndYM" size="10" value="<%=obj.getApportionEndYM()%>">]
	</td>
	</tr>
<!-- 	問題單1781 隱藏上傳附件的功能 -->
<!-- 	<tr> -->
<!-- 	 <td class="td_form">附屬設備檔：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","picture",obj.getPicture())%></td> --%>
<!--       </tr> -->
	<tr>
		<td class="td_form" >代管資產：</td>
		<td class="td_form_white" colspan="3">
			原始總價：
			<input class="field" type="text" name="escrowOriValue" size="20" value="<%=obj.getEscrowOriValue() %>">	
			&nbsp;&nbsp;&nbsp;&nbsp;			
			代管日前累計折舊：
			<input class="field" type="text" name="escrowOriAccumDepr" size="20" value="<%=obj.getEscrowOriAccumDepr() %>">
		</td>
	</tr>
	<tr>
      <td class="td_form">減損資料：</td>
      <td colspan="3" class="td_form_white">減損日期：[<input class="field_RO" type="text" name="reduceDate" size="15" maxlength="7" value="<%=obj.getReduceDate()%>">] <br>
    減損原因： <select class="field_RO" type="select" name="reduceCause">
      <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getReduceCause())%>
    </select>
    　 其他說明：[<input class="field_RO" type="text" name="reduceCause1" size="20" maxlength="20" value="<%=obj.getReduceCause1()%>">] </td>
	  </tr>
	<tr>
      <td class="td_form">備註：</td>
      <td class="td_form_white" colspan="3"><textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
      <td class="td_form"style="display:none">異動人員/日期：</td>
      <td class="td_form_white"style="display:none">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
          /
          <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
	  </tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="tempField2" value="<%=obj.getAccumDepr()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
	<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
	<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">		

	
	<input type="hidden" name="q_propertyNoS" value="<%=Common.get(request.getParameter("q_propertyNoS")) %>" >
	<input type="hidden" name="q_propertyNoE" value="<%=Common.get(request.getParameter("q_propertyNoE")) %>" >		
	<input type="hidden" name="q_serialNoS" value="<%=Common.get(request.getParameter("q_serialNoS")) %>" >
	<input type="hidden" name="q_serialNoE" value="<%=Common.get(request.getParameter("q_serialNoE")) %>" >
	<input type="hidden" name="q_dataState" value="<%=Common.get(request.getParameter("q_dataState")) %>" >
	<input type="hidden" name="q_differenceKind" value="<%=Common.get(request.getParameter("q_differenceKind")) %>" >
	<input type="hidden" name="q_verify" value="<%=Common.get(request.getParameter("q_verify")) %>" >
	<input type="hidden" name="q_ownership" value="<%=Common.get(request.getParameter("q_ownership")) %>" >
	<input type="hidden" name="q_differenceKind_detail" value="<%=Common.get(request.getParameter("q_differenceKind_detail")) %>" >
	<input type="hidden" name="q_propertyName1" value="<%=Common.get(request.getParameter("q_propertyName1")) %>" >
	<input type="hidden" name="q_originalUserNote" value="<%=Common.get(request.getParameter("q_originalUserNote")) %>" >
	<input type="hidden" name="q_keepUnit" value="<%=Common.get(request.getParameter("q_keepUnit")) %>" >
	<input type="hidden" name="q_keeper" value="<%=Common.get(request.getParameter("q_keeper")) %>" >
	<input type="hidden" name="q_useUnit" value="<%=Common.get(request.getParameter("q_useUnit")) %>" >
	<input type="hidden" name="q_userNo" value="<%=Common.get(request.getParameter("q_userNo")) %>" >
	<input type="hidden" name="q_place1" value="<%=Common.get(request.getParameter("q_place1")) %>" >
	<input type="hidden" name="q_place1Name" value="<%=Common.get(request.getParameter("q_place1Name")) %>" >
	<input type="hidden" name="q_place" value="<%=Common.get(request.getParameter("q_place")) %>" >
	<input type="hidden" name="q_engineeringNo" value="<%=Common.get(request.getParameter("q_engineeringNo")) %>" >
	<input type="hidden" name="q_engineeringNoName" value="<%=Common.get(request.getParameter("q_engineeringNoName")) %>" >
	<input type="hidden" name="q_originalBuildFeeCB" value="<%=Common.get(request.getParameter("q_originalBuildFeeCB")) %>" >
	<input type="hidden" name="q_originalDeprUnitCB" value="<%=Common.get(request.getParameter("q_originalDeprUnitCB")) %>" >
	<input type="hidden" name="q_signLaNo1" value="<%=Common.get(request.getParameter("q_signLaNo1")) %>" >
	<input type="hidden" name="q_signLaNo2" value="<%=Common.get(request.getParameter("q_signLaNo2")) %>" >
	<input type="hidden" name="q_signLaNo3" value="<%=Common.get(request.getParameter("q_signLaNo3")) %>" >
	<input type="hidden" name="q_propertyKind" value="<%=Common.get(request.getParameter("q_propertyKind")) %>" >
	<input type="hidden" name="q_fundType" value="<%=Common.get(request.getParameter("q_fundType")) %>" >
	<input type="hidden" name="q_valuable" value="<%=Common.get(request.getParameter("q_valuable")) %>" >
	<input type="hidden" name="q_taxCredit" value="<%=Common.get(request.getParameter("q_taxCredit")) %>" >
	<input type="hidden" name="q_writeDateS" value="<%=Common.get(request.getParameter("q_writeDateS")) %>" >
	<input type="hidden" name="q_writeDateE" value="<%=Common.get(request.getParameter("q_writeDateE")) %>" >
	<input type="hidden" name="q_caseName" value="<%=Common.get(request.getParameter("q_caseName")) %>" >
	<input type="hidden" name="q_proofYear" value="<%=Common.get(request.getParameter("q_proofYear")) %>" >
	<input type="hidden" name="q_proofDoc" value="<%=Common.get(request.getParameter("q_proofDoc")) %>" >
	<input type="hidden" name="q_proofNoS" value="<%=Common.get(request.getParameter("q_proofNoS")) %>" >
	<input type="hidden" name="q_proofNoE" value="<%=Common.get(request.getParameter("q_proofNoE")) %>" >
	<input type="hidden" name="q_enterDateS" value="<%=Common.get(request.getParameter("q_enterDateS")) %>" >
	<input type="hidden" name="q_enterDateE" value="<%=Common.get(request.getParameter("q_enterDateE")) %>" >
	<input type="hidden" name="q_sourceDateS" value="<%=Common.get(request.getParameter("q_sourceDateS")) %>" >
	<input type="hidden" name="q_sourceDateE" value="<%=Common.get(request.getParameter("q_sourceDateE")) %>" >
	<input type="hidden" name="q_oldPropertyNoS" value="<%=Common.get(request.getParameter("q_oldPropertyNoS")) %>" >
	<input type="hidden" name="q_oldPropertyNoE" value="<%=Common.get(request.getParameter("q_oldPropertyNoE")) %>" >
	<input type="hidden" name="q_oldSerialNoS" value="<%=Common.get(request.getParameter("q_oldSerialNoS")) %>" >
	<input type="hidden" name="q_oldSerialNoE" value="<%=Common.get(request.getParameter("q_oldSerialNoE")) %>" >
	<input type="hidden" name="q_oldlotNoS" value="<%=Common.get(request.getParameter("q_oldlotNoS")) %>" >
	<input type="hidden" name="q_oldlotNoE" value="<%=Common.get(request.getParameter("q_oldlotNoE")) %>" >
	<input type="hidden" name="q_specification" value="<%=Common.get(request.getParameter("q_specification")) %>" >
	<input type="hidden" name="q_nameplate" value="<%=Common.get(request.getParameter("q_nameplate")) %>" >
	
	<jsp:include page="../../home/toolbar.jsp" />
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">增加原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">原值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">帳面價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,false,false,true,true,true,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script type="text/javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		//刪除之前多出現一道確認訊息
        case "delete":
			if (!confirm("刪除此土地改良基本資料，將一併刪除其關聯的資料：\n\n 01. 土地改良基本資料、\n 02. 基地資料、\n 03. 管理資料、\n04. 附屬設備、\n 05. 現場勘查。\n\n您確定要刪除?")) {
				return false;
			}
			hasBeenConfirmed = true;			
			break;		
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[1].checked=true;		
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			}
			changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType',form1.q_fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');			
			break;				
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			changeSelect();				
			setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","R");		
			break;				

		case "insertError":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			changeSelect();				
			setFormItem("scrapValue,accumDeprYM,accumDepr,apportionYear,deprAmount","R");		
			break;				
			
		//更新時要做的動作
		case "update":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType',form1.fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			changeSelect();						
			if (form1.verify.value=="Y") {
				doSpecialField();
							
			}	
			checkSpecialField();	
			break;		
		case "updateError":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType',form1.fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			changeSelect();				
			if (form1.verify.value=="Y") {
				doSpecialField();
							
			}	
			checkSpecialField();
			break;			
	}
}
</script>
</body>
</html>



