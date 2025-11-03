<!--
程式目的：財產增加單及主檔維護_共同基本資料
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH001F02">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>
<%

obj.setProgID(Common.checkGet(request.getParameter("progID")));
if("".equals(Common.checkGet(obj.getEnterOrg()))){
	obj.setEnterOrg(user.getOrganID());
}
obj.setRoleid(user.getRoleid());
obj.setQ_enterOrg(obj.getEnterOrg());
obj.setKeeperno(user.getKeeperno());
obj.setUnitID(user.getUnitID());
obj.setOrganID(user.getOrganID());

// String checkVerify = obj.queryVerify();
String checkfundType = "";

//Excel下載暫定使用 web.xml 中filestoreLocation的設定位置
String downloadFilePath2 = application.getInitParameter("filestoreLocation") + "/land_Sample.xls";
String downloadFilePath4 = application.getInitParameter("filestoreLocation") + "/building_Sample.xls";
String downloadFilePath5 = application.getInitParameter("filestoreLocation") + "/movable_Sample.xls";

if("120".equals(obj.getDifferenceKind())){
	checkfundType = uctls.getCountForFundType(obj.getEnterOrg());	
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }	
}else if ("queryOne".equals(obj.getState())) {	
	obj = obj._execQueryOneforDetail();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {	
	if (!obj.isMerge()) {
		obj._execInsertforDetail();									
	} else {
		obj.setStateInsertError();
		obj.setErrorMsg("此為土地合併分割案件,請於土地分割案件維護作業進行新增");
	}
	
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {	
	if (!obj.isMerge()) {
		obj._execUpdateforDetail();
		String checkStr = obj.getPropertyNo().substring(0,1);
		if("3".equals(checkStr) || "4".equals(checkStr) || "5".equals(checkStr)){
			obj.updateSerialNoforUNTMP_Movable();
		}
	} else {
		obj.setStateUpdateError();
		obj.setErrorMsg("此為土地合併分割案件,請於土地分割案件維護作業進行修改");
	}

}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {	
	if (!obj.isMerge()) {
		obj._execDeleteforDetail();
		obj.setPropertyNo("");
		obj.setSerialNo("");
		obj.setLotNo("");	
	} else {
		obj.setStateDeleteError();
		obj.setErrorMsg("此為土地合併分割案件,請於土地分割案件維護作業進行刪除");
	}
}
if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.execQueryAll();

	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
		
		}else{
			if("".equals(obj.getSerialNo()) && "".equals(obj.getLotNo())){
				if ("true".equals(obj.getIsAddProof())) {
					obj.setEnterOrg(((String[])objList.get(0))[0]);
					obj.setOwnership(((String[])objList.get(0))[1]);
					obj.setCaseNo(((String[])objList.get(0))[2]);
					obj.setDifferenceKind(((String[])objList.get(0))[3]);
					obj.setPropertyNo(((String[])objList.get(0))[6]);
					obj.setSerialNo(((String[])objList.get(0))[7]);
					obj.setSerialNoS(((String[])objList.get(0))[7]);
					obj.setSerialNoE(((String[])objList.get(0))[8]);
					obj.setLotNo(((String[])objList.get(0))[9]);	
					obj.setEnterOrgName(((String[])objList.get(0))[16]);
					obj.setEngineeringNo(((String[])objList.get(0))[17]);
				} else {
					obj.setEnterOrg(((String[])objList.get(0))[1]);
					obj.setOwnership(((String[])objList.get(0))[2]);
					obj.setCaseNo(((String[])objList.get(0))[3]);
					obj.setDifferenceKind(((String[])objList.get(0))[4]);
					obj.setPropertyNo(((String[])objList.get(0))[7]);
					obj.setSerialNo(((String[])objList.get(0))[8]);
					obj.setSerialNoS(((String[])objList.get(0))[8]);
					obj.setSerialNoE(((String[])objList.get(0))[9]);
					obj.setLotNo(((String[])objList.get(0))[10]);	
					obj.setEnterOrgName(((String[])objList.get(0))[17]);
					obj.setEngineeringNo(((String[])objList.get(0))[18]);
				}
			}				
			obj._execQueryOneforDetail();
		}
	}

}
String checkVerify = obj.queryVerify();
String checkDeprType = "";
if("110".equals(obj.getDifferenceKind())){			checkDeprType = "officialDeprMethod";
}else if("111".equals(obj.getDifferenceKind())){	checkDeprType = "escrowDeprMethod";
//問題單1768 DifferenceKind為地方財產112 參考基金
}else if("112".equals(obj.getDifferenceKind())){	checkDeprType = "fundDeprMethod";
}else if("120".equals(obj.getDifferenceKind())){	checkDeprType = "fundDeprMethod";
}else if("113".equals(obj.getDifferenceKind())){	checkDeprType = "deferredDeprMethod";
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
uch.setPropertyNoType(obj.getPropertyNo());
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._CH_ADD, 2);
}else{
	uch._setupViewType(uch._queryOrMaintenance);
	tabs = uch._createTabData(uch._CH_ADD, 1);
	obj.setQuerySelect("detail");
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
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js?v=1" ></script>
<script type="text/javascript" src="../../js/function.js?v=1" ></script>
<script type="text/javascript" src="../../js/tablesoft.js" ></script>
<script type="text/javascript" src="../../js/sList2.js" ></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
var checkVerify = '<%=checkVerify%>';

insertDefault = new Array(	
		new Array("dataState","1"),
		new Array("dataStateName","1"),
		new Array("valuable","N"),	
		new Array("manageOrg","<%=user.getOrganID()%>"),
		new Array("manageOrgName","<%=user.getOrganName()%>"),
		new Array("propertyKind","<%=uctls._getDefaultValue(user.getOrganID(), "propertyKind")%>"),
		new Array("originalDeprMethod","<%=uctls._getDefaultValue(user.getOrganID(), checkDeprType)%>"),
		new Array("engineeringNo","<%=obj.getEngineeringNo()%>"),
		new Array("engineeringNoName","<%=obj.getEngineeringNoName()%>"),
		new Array("fundType","<%=checkfundType%>"),
		new Array("sourceKind","02"),
		new Array("sourceKindName","購置"),
		new Array("originalaccumdepr1","0"),
		new Array("originalaccumdepr2","0")
		
);

function checkField(){
	var alertStr="";
	
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {	form1.action = "untch001f01.jsp";
		} else {									form1.action = "untch001f02.jsp";
		}		
	}else if(form1.state.value=="insert"||form1.state.value=="update"){	
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.originalDeprMethod,"折舊方法");
		
		if (form1.differenceKind.value == "120"){	
			alertStr += checkEmpty(form1.fundType,"基金財產");
		}
		
		
		if(alertStr.length == 0){
			
			var checkStr1 = form1.propertyNo.value.substring(0,1);
			var checkStr3 = form1.propertyNo.value.substring(0,3);
			alertStr += checkEmpty(form1.buyDate,"購置日期");
			alertStr += checkDate(form1.buyDate,"購置日期");			
			alertStr += checkEmpty(form1.sourceDate,"財產取得日期");
			alertStr += checkDate(form1.sourceDate,"財產取得日期");
			alertStr += checkEmpty(form1.sourceKind,"財產來源");			
			alertStr += checkEmpty(form1.propertyKind,"財產性質");
			alertStr += checkEmpty(form1.originalBV,"總價");
			alertStr += checkEmpty(form1.originalPlace1, "存置地點");
			alertStr += checkEmpty(form1.originalPlace1Name, "存置地點");
				
			if("111" == checkStr3){	
				
				alertStr += checkEmpty(form1.originalMeasure,"原始計量數");
				alertStr += checkEmpty(form1.originalDeprMethod,"折舊方法");
				alertStr += checkQuotation(form1.propertyName1,"財產編號：財產別名");
				alertStr += checkQuotation(form1.sourceDoc,"財產來源：文號");
				alertStr += checkQuotation(form1.fundsSource1,"經費來源：其它說明");
				alertStr += checkQuotation(form1.accountingTitle,"經費來源：會計科目");
				alertStr += checkQuotation(form1.originalMeasure,"土地改良物：原始計量數");
				alertStr += checkQuotation(form1.originalBV,"總價");
				
				alertStr += checkQuotation(form1.originalNote,"原始數量價值：帳務摘要");
				alertStr += checkQuotation(form1.originalUserNote,"保管使用資料：使用註記");
				alertStr += checkQuotation(form1.originalPlace,"保管使用資料：存置地點說明");
				alertStr += checkQuotation(form1.originalaccumdepr1,"原始折舊資料：以前年度累計折舊");
				alertStr += checkQuotation(form1.originalaccumdepr2,"原始折舊資料：本年度累計折舊");
				alertStr += checkQuotation(form1.escrowOriValue,"代管資產：原始總價");
				alertStr += checkQuotation(form1.escrowOriAccumDepr,"代管資產：代管日前累計折舊");
				alertStr += checkQuotation(form1.notes,"備註");
				
				if(form1.material.value == ''){
					alertStr += checkEmpty(form1.otherMaterial,"其他主要材質");
				}
				if(form1.propertyUnit.value == ''){
					alertStr += checkEmpty(form1.otherPropertyUnit,"其他單位");
				}
// 				alertStr += checkEmpty(form1.otherLimitYear,"調整後年限(月)");
			}else if("1" == checkStr1){
				alertStr += checkEmpty(form1.laSignNo1,"土地標示");
				alertStr += checkEmpty(form1.laSignNo2,"土地標示");
				alertStr += checkEmpty(form1.laSignNo3,"土地標示");
				alertStr += checkEmpty(form1.laSignNo4,"土地標示地號");
				alertStr += checkEmpty(form1.laSignNo5,"土地標示地號");
				alertStr += checkEmpty(form1.originalUnit,"單價");
				alertStr += checkEmpty(form1.originalAreaLa,"土地總面積");
				
				alertStr += checkQuotation(form1.propertyName1,"財產編號：財產別名");
				alertStr += checkQuotation(form1.sourceDoc,"財產來源：文號");
				alertStr += checkQuotation(form1.laSignNo4,"地號");
				alertStr += checkQuotation(form1.laSignNo5,"地號");
				alertStr += checkQuotation(form1.originalAreaLa,"總面積");
				alertStr += checkQuotation(form1.originalHoldNumeLa,"權利分子");
				alertStr += checkQuotation(form1.originalHoldDenoLa,"權利分母");
				alertStr += checkQuotation(form1.fundsSource1,"經費來源：其它說明");
				alertStr += checkQuotation(form1.accountingTitle,"經費來源：會計科目");
				alertStr += checkQuotation(form1.originalBV,"總價");
				alertStr += checkQuotation(form1.originalNote,"原始數量價值：帳務摘要");
				alertStr += checkQuotation(form1.originalUserNote,"保管使用資料：使用註記");
				alertStr += checkQuotation(form1.originalPlace,"保管使用資料：存置地點說明");
				alertStr += checkQuotation(form1.escrowOriValue,"原始總價");
				alertStr += checkQuotation(form1.escrowOriAccumDepr,"代管日前累計折舊");
				alertStr += checkQuotation(form1.notes,"備註");
				
			}else if("2" == checkStr1){
				
				if(checkStr3 == '202'){
					
				}else{
					//alertStr += checkEmpty(form1.buSignNo1,"建物標示");
					//alertStr += checkEmpty(form1.buSignNo2,"建物標示");
					//alertStr += checkEmpty(form1.buSignNo3,"建物標示");
					//alertStr += checkEmpty(form1.buSignNo4,"建物標示建號");
					//alertStr += checkEmpty(form1.buSignNo5,"建物標示建號");
				}
				
				alertStr += checkFloat(form1.originalSArea,"建物主面積");
				alertStr += checkEmpty(form1.originalSArea,"建物主面積");
				alertStr += checkFloat(form1.originalCArea,"建物附屬面積");
				alertStr += checkEmpty(form1.originalCArea,"建物附屬面積");
				alertStr += checkEmpty(form1.originalAreaBu,"建物總面積");
				alertStr += checkEmpty(form1.originalDeprMethod,"折舊方法");
				
				alertStr += checkQuotation(form1.propertyName1,"財產編號：財產別名");
				alertStr += checkQuotation(form1.sourceDoc,"財產來源：文號");
				alertStr += checkQuotation(form1.buSignNo4,"建號");
				alertStr += checkQuotation(form1.buSignNo5,"建號");
				alertStr += checkQuotation(form1.doorPlatevillage1,"里");
				alertStr += checkQuotation(form1.doorplateRd1,"路街大道");
				alertStr += checkQuotation(form1.doorplateSec,"段");
				alertStr += checkQuotation(form1.doorplateLn,"巷");
				alertStr += checkQuotation(form1.doorplateAly,"弄");
				alertStr += checkQuotation(form1.doorplateNo,"號");
				alertStr += checkQuotation(form1.doorplateFloor1,"樓");
				alertStr += checkQuotation(form1.doorplateFloor2,"樓後面的欄位");
				alertStr += checkQuotation(form1.originalCArea,"主面積");
				alertStr += checkQuotation(form1.originalSArea,"附屬面積");
				alertStr += checkQuotation(form1.originalHoldNumeBu,"權利分子");
				alertStr += checkQuotation(form1.originalHoldDenoBu,"權利分母");
				alertStr += checkQuotation(form1.originalNote,"原始數量價值：帳務摘要");
				alertStr += checkQuotation(form1.originalUserNote,"保管使用資料：使用註記");
				alertStr += checkQuotation(form1.originalPlace,"保管使用資料：存置地點說明");
				alertStr += checkQuotation(form1.originalaccumdepr1,"原始折舊資料：以前年度累計折舊");
				alertStr += checkQuotation(form1.originalaccumdepr2,"原始折舊資料：本年度累計折舊");
				alertStr += checkQuotation(form1.escrowOriValue,"代管資產：原始總價");
				alertStr += checkQuotation(form1.escrowOriAccumDepr,"代管資產：代管日前累計折舊");
				alertStr += checkQuotation(form1.notes,"備註");

				if(form1.material.value == ''){
					alertStr += checkEmpty(form1.otherMaterial,"其他主要材質");
				}				
				if(form1.propertyUnit.value == ''){					
					alertStr += checkEmpty(form1.otherPropertyUnit,"其他單位");
				}
				
				//問題單2314 當總價、殘值、 應攤提折舊總額、以前年度累計折舊、本年度累計折舊皆有值的情況，要判斷 總價 = 殘值 +  應攤提折舊總額 + 以前年度累計折舊 + 本年度累計折舊
				if (form1.cb_selfdepr.checked == true) {
					if (form1.originalBV.value != '' 
						&& form1.originalScrapValue.value != '' 
						&& form1.originalDeprAmount.value != '' 
						&& form1.originalaccumdepr1.value != '' 
						&& form1.originalaccumdepr2.value != '') {
						if (parseInt(form1.originalBV.value) != parseInt(form1.originalScrapValue.value) + parseInt(form1.originalDeprAmount.value) + parseInt(form1.originalaccumdepr1.value) + parseInt(form1.originalaccumdepr2.value)) {
							alertStr += "總價必須等於殘值、 應攤提折舊總額、以前年度累計折舊、本年度累計折舊的加總";
						}
					}
				}
				
// 				alertStr += checkEmpty(form1.otherLimitYear,"調整後年限(月)");
			}else if("3" == checkStr1 || "4" == checkStr1 || "5" == checkStr1){
				alertStr += checkEmpty(form1.originalAmount,"數量");
				alertStr += checkEmpty(form1.originalDeprMethod,"折舊方法");
				
				alertStr += checkQuotation(form1.propertyName1,"財產編號：財產別名");
				alertStr += checkQuotation(form1.sourceDoc,"財產來源：文號");
				alertStr += checkQuotation(form1.otherMaterial,"其他主要材質");
				alertStr += checkQuotation(form1.otherPropertyUnit,"其他單位");
				alertStr += checkEmpty(form1.specification,"型式");
				alertStr += checkQuotation(form1.specification,"型式");
				alertStr += checkEmpty(form1.nameplate,"廠牌");
				alertStr += checkQuotation(form1.nameplate,"廠牌");
				alertStr += checkQuotation(form1.articleName,"品名");
				alertStr += checkQuotation(form1.fundsSource1,"經費來源：其它說明");
				alertStr += checkQuotation(form1.accountingTitle,"經費來源：會計科目");
				alertStr += checkQuotation(form1.originalAmount,"原始數量價值：數量");
				alertStr += checkQuotation(form1.originalNote,"原始數量價值：帳務摘要");
				alertStr += checkQuotation(form1.originalaccumdepr1,"原始折舊資料：以前年度累計折舊");
				alertStr += checkQuotation(form1.originalaccumdepr2,"原始折舊資料：本年度累計折舊");
				alertStr += checkQuotation(form1.escrowOriValue,"代管資產：原始總價");
				alertStr += checkQuotation(form1.escrowOriAccumDepr,"代管資產：代管日前累計折舊");
				alertStr += checkQuotation(form1.notes,"備註");

				if(form1.material.value == ''){
					alertStr += checkEmpty(form1.otherMaterial,"其他主要材質");
				}
				if(form1.propertyUnit.value == ''){
					alertStr += checkEmpty(form1.otherPropertyUnit,"其他單位");
				}
// 				alertStr += checkEmpty(form1.otherLimitYear,"調整後年限(月)");
				
				if(checkStr3 == '503'){
				}else{
					var transferGroup = ['28', '29'];
					alertStr += checkEmpty(form1.originalUnit,"單價");	
					//104.12.17 數量<1，檢查總價>=10000
					if(form1.originalAmount.value<1 && form1.originalBV.value<10000 && !transferGroup.includes(form1.sourceKind.value)) {
						alertStr += "總價必須大於等於10000";
						form1.originalBV.style.backgroundColor = errorbg;
					}else if(form1.originalUnit.value<10000 && !transferGroup.includes(form1.sourceKind.value)) {
						alertStr += "單價必須大於等於10000";
						form1.originalUnit.style.backgroundColor = errorbg;
					}
				}
				
				//20210208 如果數量*單價 != 總價 (除不盡之情況)
				if (form1.originalAmount.value * form1.originalUnit.value != form1.originalBV.value) {
					alertStr += "『該筆明細有數量*單價不等於總價的情形，若有無法整除者請另建財產明細』";
				}
				
				//問題單2314 當總價、殘值、 應攤提折舊總額、以前年度累計折舊、本年度累計折舊皆有值的情況，要判斷 總價 = 殘值 +  應攤提折舊總額 + 以前年度累計折舊 + 本年度累計折舊
				if (form1.cb_selfdepr.checked == true) {
					if (form1.originalBV.value != '' 
						&& form1.originalScrapValue.value != '' 
						&& form1.originalDeprAmount.value != '' 
						&& form1.originalaccumdepr1.value != '' 
						&& form1.originalaccumdepr2.value != '') {
						if (parseInt(form1.originalBV.value) != parseInt(form1.originalScrapValue.value) + parseInt(form1.originalDeprAmount.value) + parseInt(form1.originalaccumdepr1.value) + parseInt(form1.originalaccumdepr2.value)) {
							alertStr += "總價必須等於殘值、 應攤提折舊總額、以前年度累計折舊、本年度累計折舊的加總";
						}
					}
				}
			}else if("8" == checkStr1){
				alertStr += checkEmpty(form1.originalAmount,"數量");
				//alertStr += checkEmpty(form1.proofDoc1,"憑證字號"); 2015/01/21測試不需畢填
				
				alertStr += checkQuotation(form1.propertyName1,"財產編號：財產別名");
				alertStr += checkQuotation(form1.sourceDoc,"財產來源：文號");
				alertStr += checkQuotation(form1.fundsSource1,"經費來源：其它說明");
				alertStr += checkQuotation(form1.accountingTitle,"經費來源：會計科目");
				alertStr += checkQuotation(form1.originalNote,"原始數量價值：帳務摘要");
				alertStr += checkQuotation(form1.originalUserNote,"保管使用資料：使用註記");
				alertStr += checkQuotation(form1.originalPlace,"保管使用資料：存置地點說明");
				alertStr += checkQuotation(form1.originalaccumdepr1,"原始折舊資料：以前年度累計折舊");
				alertStr += checkQuotation(form1.originalaccumdepr2,"原始折舊資料：本年度累計折舊");
				alertStr += checkQuotation(form1.escrowOriValue,"代管資產：原始總價");
				alertStr += checkQuotation(form1.escrowOriAccumDepr,"代管資產：代管日前累計折舊");
				alertStr += checkQuotation(form1.proofDoc1,"權利：憑證字號");
				alertStr += checkQuotation(form1.notes,"備註");
				
			}else if("9" == checkStr1){
				alertStr += checkEmpty(form1.originalAmount,"數量");
				
				alertStr += checkQuotation(form1.propertyName1,"財產編號：財產別名");
				alertStr += checkQuotation(form1.sourceDoc,"財產來源：文號");
				alertStr += checkQuotation(form1.fundsSource1,"經費來源：其它說明");
				alertStr += checkQuotation(form1.accountingTitle,"經費來源：會計科目");
				alertStr += checkQuotation(form1.originalNote,"原始數量價值：帳務摘要");
				alertStr += checkQuotation(form1.originalUserNote,"保管使用資料：使用註記");
				alertStr += checkQuotation(form1.originalPlace,"保管使用資料：存置地點說明");
				alertStr += checkQuotation(form1.escrowOriValue,"代管資產：原始總價");
				alertStr += checkQuotation(form1.escrowOriAccumDepr,"代管資產：代管日前累計折舊");
				alertStr += checkQuotation(form1.securityName,"有價證券：發行法人名稱");
				alertStr += checkQuotation(form1.notes,"備註");
				// 客戶758  不再強制要求登打
				//alertStr += checkEmpty(form1.securityName,"發行法人名稱");
			}
			
			
			if(form1.originalDeprMethod.value == '02'){
				if("111" == checkStr3 || "2" == checkStr1 ||
					"3" == checkStr1 || "4" == checkStr1 || "5" == checkStr1) {
				    // 1755 公務類非必填
				    // 問題單1770:科技部 不檢核園區別、部門別、部門別單位、會計科目
				    if("<%=user.getOrganID()%>" !== "<%=TCGHCommon.getSYSCODEName("01", "ETO")%>" && form1.differenceKind.value !== '110') {
	                    alertStr += checkEmpty(form1.originalDeprPark,"園區別");
	                    if($("input[name='cb_originalDeprUnitCB']").attr('checked') == 'checked'){
	                        
	                    }else{
	                        alertStr += checkEmpty(form1.originalDeprUnit,"部門別");
	                        alertStr += checkEmpty(form1.originalDeprUnit1,"部門別單位");
	                        alertStr += checkEmpty(form1.originalDeprAccounts,"會計科目");                  
	                    }
				    }
                    // 因為有輸入的 以前年度折舊 跟 本年度折舊, 而應攤提折舊總額會是扣掉該兩個值的結果,  因此必須改用算的 originalbv - 殘值  來當作應攤提折舊總額
                    var _oridepramt = parseFloat(form1.originalBV.value) + parseFloat(form1.scrapValue.value);
                    if (parseFloat(form1.originalAccumDepr.value) > _oridepramt) {
                        alertStr += "原始折舊資料-累計折舊不可大於應攤提折舊總額\n"
                    }
                }
			} else if (form1.originalDeprMethod.value == '03' && form1.differenceKind.value == "110"&&  form1.otherLimitYear.value>0) {
				// TODO
				// var oriAmountIsInt = form1.originalAmount.value === parseInt(form1.originalAmount.value, 10);
				var oriAmountIsInt = parseFloat(form1.originalAmount.value) <= 1
				
				var originalbookvalue;
				if (!isNaN(form1.originalUnit.value) && !oriAmountIsInt)  { //originalUnit 有資料 且 入帳數量為整數
					originalbookvalue = form1.originalUnit.value;
				} else {
					originalbookvalue = form1.originalBV.value;
				}
				
				if (!isNaN(form1.originalScrapValue.value) && !isNaN(originalbookvalue)) {
					if (parseFloat(form1.originalScrapValue.value) > parseFloat(originalbookvalue)) {
						alertStr += "殘值不可大於原始入帳單價(總價)\r\n";
					}
					
					var limitScrapVal = Math.round(originalbookvalue * 0.01);
					if (checkStr1 !== "8" && form1.originalScrapValue.value < limitScrapVal) {
						alertStr += "殘值預設為定額(成本之1%，四捨五入至整數)；或由業管單位自行估計後提供給財管人員登載，但不可低於上開金額。"
						                + "折耗及攤銷性資產(如經濟動物或權利)殘值預設為0。\r\n";
					}
				}

				// 因為有輸入的 以前年度折舊 跟 本年度折舊, 而應攤提折舊總額會是扣掉該兩個值的結果,  因此必須改用算的 originalbv - 殘值  來當作應攤提折舊總額
				var _oridepramt = parseFloat(form1.originalBV.value) + parseFloat(form1.scrapValue.value);
				if (parseFloat(form1.originalAccumDepr.value) > _oridepramt) {
					alertStr += "原始折舊資料-累計折舊不可大於應攤提折舊總額\n"
				}
			}
		}
		
		//問題單2032，因問題單2019之檢核漏掉排除土地、有價證券財產，故於此調整
		//一併調整原檢核使用年限(月)，將其整併至此
		if(form1.valuable.value != "Y"){
			var checkStr1 = form1.propertyNo.value.substring(0,1);
			var checkStr3 = form1.propertyNo.value.substring(0,3);
			if(checkStr3 == "111" || checkStr1 == "2" || checkStr1 == "3" || checkStr1 == "4" || checkStr1 == "5" || checkStr1 == "8"){
					alertStr += checkEmpty(form1.originalLimitYear,"原使用年限");
					alertStr += checkEmpty(form1.otherLimitYear,"使用年限(月)");
				if(form1.originalLimitYear.value != "" && form1.otherLimitYear.value != ""){ 
					//檢核原使用年限(年)與使用年限(月)是否相等
					if (form1.originalLimitYear.value * 12 != form1.otherLimitYear.value){
						if(!confirm("原使用年限不等於使用年限(月)。\n\n您確定要儲存?")){
							if(alertStr.length!=0){ alert(alertStr); return false; }
							return false;
						}
					}
				}
			}
		}
	}
	
	if (form1.individualSetDepr.value != '') {
		alertStr += checkEmpty(form1.verifyYM, "入帳年月");
		alertStr += checkYYYMM(form1.verifyYM, "入帳年月");
	}
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	
	beforeSubmit();
}


function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,serialNo,serialNoE,lotNo){
	form1.enterOrg.value = enterOrg;
	form1.ownership.value = ownership;
	form1.caseNo.value = caseNo;
	form1.differenceKind.value = differenceKind;
	form1.propertyNo.value = propertyNo;
	form1.serialNo.value = serialNo;
	form1.serialNoS.value = serialNo;
	if (serialNoE.indexOf("-") != -1) {
		serialNoE = serialNoE.split("-")[1];
	}	
	form1.serialNoE.value = serialNoE;
	form1.lotNo.value = lotNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();		
}
function defaultDU(){
	if($("#cb_originalBuildFeeCB").attr('checked') == 'checked'){
	for (var i = 0 ; i < form1.originalDeprUnit.options.length ; i++) {
		if(form1.originalDeprUnit.options[i].text=="出租資產" && form1.originalDeprUnit.selectedOptions[0].text !== "消防隊"){
			form1.originalDeprUnit.value=form1.originalDeprUnit.options[i].value ;
		}
	}
	}
}
function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(surl!='../ch/untch001f01.jsp' && form1.propertyNo.value==""){
		alert("請先執行查詢!");
	}else{
		form1.state.value="queryAll";	
		form1.action = surl;
		//1314 initDtl不知道是做啥控制的, 不過原先untmp002f在checkURL時都加上去了, 那我就無條件先加了吧
		var check1 = form1.propertyNo.value.substring(0,1);
		if("345".indexOf(check1)>-1){
			form1.action = surl+"?initDtl=Y";
		}
		if(surl=='../ch/untch001f01.jsp') {
			form1.mainPage1.value="";
			form1.currentPage.value=form1.mainPage.value;
		} else {
			form1.mainPage1.value=form1.currentPage.value;
			form1.currentPage.value=1;
		}
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		beforeSubmit();
		form1.submit();		
	}
}

function init(){
	var checkStr1 = form1.propertyNo.value.substring(0,1);
	
	//110.02.02 隱藏目前折舊
	$('#div_depr2').hide();
	
	initQ_Form('<%=obj.getQuerySelect()%>');
	
	if (isObj(document.all.item("picture")) && (parse(document.all.item("picture").value)).length>0) setFormItem("btn_pictureDownload","O");	
	if(form1.propertyNo.value != ''){	changePropertyNo(form1.propertyNo.value, "init");	
	}else{								initFormDisabled();
	}	
	
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
	
	if('<%=obj.getDoorPlate2()%>' == ""){
		$("select[name='doorPlate2']").append($("<option></option>").attr("value", "").text("請選擇"));	
	}else{

		changeSignNo1('doorPlate1','doorPlate2','doorPlate3','<%=obj.getDoorPlate2()%>');	
	}
	
	//>>>>>>>>>>>>>>>>>>>>>>>
	
	setFormItem("btn01,btn02,btn03,btn04,btn05","O");
	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormField(new Array("insert,update","delete,btn01,btn03"),"R");
	}	
	if (checkVerify == "Y") {
		setFormItem("insert,update,delete,btn01,btn03", "R");
	}	
	
	if(form1.originalDeprMethod.value == '02'){
		checkDeprMethod('');
	}
	
	if(form1.isAddProof.value == 'true'){
		$("#q_title_tr").show();
	}else{
		$("#q_title_tr").hide();
	}
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,span_btn01,span_btn02,span_btn03,span_btn04,span_btn05,spanNextInsert","H");
	}else if(form1.progID.value == 'untch001f02extend02'){
		setDisplayItem("spanInsert,spanDelete,span_btn01,span_btn02,span_btn03,span_btn04,span_btn05","H");
		setFormItem("update", "O");
	}
	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeepUnitQuickly, form1.originalKeepUnit, form1.originalUseUnitQuickly, form1.originalUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeeperQuickly, form1.originalKeeper, form1.originalUserQuickly, form1.originalUser, true, false);	

	var movableGroup = ['2', '3', '4', '5'];
	var transferGroup = ['28', '29'];
	//問題單2314 動產的財產來源為接管或撥入時，顯示個別填寫折舊資料
	if (transferGroup.includes(form1.sourceKind.value) && movableGroup.includes(checkStr1)) {
		document.getElementById("selfdeprText").style.display = "";
		document.getElementById("cb_selfdepr").style.display = "";
	} else {
		document.getElementById("selfdeprText").style.display = "none";
		document.getElementById("cb_selfdepr").style.display = "none";
	}

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNoS.value !== ''
				&& form1.serialNoE.value !== '') {
		var temp = "";
		if (form1.serialNoS.value != form1.serialNoE.value && form1.serialNoE.value.indexOf("-") == -1) {
			temp = form1.serialNoS.value+"-";
		}
		var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value + form1.differenceKind.value + form1.propertyNo.value + form1.serialNoS.value + temp + form1.serialNoE.value + form1.lotNo.value;
		listContainerRowClick(key);
	}
}

/**
 * 替折舊資料欄位增加連動性
 */
function syncDeprData() {
	form1.deprMethod.value = form1.originalDeprMethod.value;
		
	if($("#cb_originalBuildFeeCB").attr('checked') == 'checked'){		
		form1.cb_buildFeeCB.checked = true;
		form1.originalBuildFeeCB.value="Y";
	} else {		
		form1.cb_buildFeeCB.checked = false;
		form1.originalBuildFeeCB.value="N";
	}
	form1.buildFeeCB.value = form1.originalBuildFeeCB.value;
	
    
	if($("#cb_originalDeprUnitCB").attr('checked') == 'checked'){
		form1.cb_deprUnitCB.checked = true;
		form1.originalDeprUnitCB.value="Y";
	} else {		
		form1.cb_deprUnitCB.checked = false;
		form1.originalDeprUnitCB.value="N";
	}
	form1.deprUnitCB.value = form1.originalDeprUnitCB.value;
	
	form1.deprPark.value = form1.originalDeprPark.value;
	form1.deprUnit.value = form1.originalDeprUnit.value;
	form1.deprUnit1.value = form1.originalDeprUnit1.value;
	form1.deprAccounts.value = form1.originalDeprAccounts.value;
	form1.accumDepr.value = form1.originalAccumDepr.value;
} 

function myQueryDeprUnitData(){
	if($("input[name='originalDeprUnittemp']").val() != ''){
		$("select[name='originalDeprUnit']").val($("input[name='originalDeprUnittemp']").val());
		$("input[name='originalDeprUnittemp']").val('');
	}
	syncDeprData();
	myQueryDeprUnitDataforDeprUnit1();
	myQueryDeprUnitDataforDeprAccounts();
}

function myQueryDeprUnitDataforDeprUnit1(){
	//if($("select[name='originalDeprUnit1']").val() == ''){
		if($("select[name='originalDeprUnit']").val() != ''){		
			//傳送JSON		
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("originalDeprUnit").value;
			comment.queryType = "deprUnit1";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');
	
					$("select[name='originalDeprUnit1']").val(data['defaultValue']);
					syncDeprData();
				});
		}
	//}
}

function myQueryDeprUnitDataforDeprAccounts(){
	//if($("select[name='originalDeprAccounts']").val() == ''){
		if($("select[name='originalDeprUnit']").val() != ''){	
			//傳送JSON
			var comment={};	
			comment.enterOrg = document.all.item("enterOrg").value;
			comment.queryData = document.all.item("originalDeprUnit").value;
			comment.queryType = "deprAccounts";
			
			$.post('queryDeprUnitData.jsp',
				comment,
				function(data){
					//將回傳資料寫入
					data=eval('('+data+')');
		
					$("select[name='originalDeprAccounts']").val(data['defaultValue']);
					syncDeprData();
				});
		}
	//}
}


function btn01_event(){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-300;
	prop=prop+"width=1200,height=800,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=yes";
	window.open("untch001f07.jsp?enterorg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&caseno="+form1.caseNo.value+"&differenceKind="+form1.differenceKind.value,"",prop);
}

function btn02_event(){
	openDownloadWindow('','<%=downloadFilePath2%>');
}

function btn04_event(){
	openDownloadWindow('','<%=downloadFilePath4%>');
}

function btn05_event(){
	openDownloadWindow('','<%=downloadFilePath5%>');
}

function btn03_event(){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=750,height=200,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=yes";
	window.open("untch001f08.jsp?enterorg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&caseno="+form1.caseNo.value+"&differenceKind="+form1.differenceKind.value+"&engineeringNo="+form1.engineeringNo.value,"",prop);
}


function initFormDisabled(){
	$("#div_spec").hide();
	$("#div_laSignNo").hide();
	$("#div_buSignNo").hide();
	$("#div_door").hide();
	$("#div_laArea").hide();
	$("#div_laArea_start").hide();	
	$("#div_buArea").hide();
	$("#div_rfArea").hide();
	$("#div_amount").hide();
	$("#div_move").hide();
	$("#div_depr").hide();
	$("#div_depr2").hide();
	$("#div_escrow").hide();
	$("#div_pic").hide();
	$("#div_security").hide();
	$("#div_proofDoc").hide();
	$("#div_manageOrg").hide();
	
	$("#div_material").hide();
	$("#div_otherMaterial").hide();
	$("#div_propertyUnit").hide();
	$("#div_otherPropertyUnit").hide();
	$("#div_limitYear").hide();
}


function changePropertyNo(propertyNo, actionType){
	initFormDisabled();
	
	var checkStr1 = form1.propertyNo.value.substring(0,1);
	var checkStr3 = form1.propertyNo.value.substring(0,3);
	
	//問題單2111如果是初始載入不帶預設值
	if (actionType != "init") {
		defaultValue(checkStr1);
	}
	
	setFormItem("originalAmount","O");
	//setFormItem("originalUnit","O");
	form1.originalUnit.disabled= false;
	
	$("#div_escrow").show();
	
	if("111" == checkStr3){
		$("#div_rfArea").show();
		$("#div_amount").show();
		$("#div_move").show();
		$("#div_depr").show();
// 		$("#div_depr2").show();
		$("#div_pic").show();
		if(form1.originalAmount.value == ""){ // 其實土改不需要這個欄位 但不知道是誰堅持要在畫面上顯示1 然後唯讀
			form1.originalAmount.value = "1";
		}
		setFormItem("originalAmount","R");
		//setFormItem("originalUnit","R");
		form1.originalUnit.disabled= true;
		$("#div_material").show();
		$("#div_propertyUnit").show();
		$("#div_otherMaterial").show();
		$("#div_otherPropertyUnit").show();
		
		//if($("input[name='limitYear']").val() == ''){
		//	$("#div_otherLimitYear").show();
		//}else{
			$("#div_limitYear").show();	
		//}
		
		
	}else if("1" == checkStr1){
		$("#div_laSignNo").show();
		$("#div_laArea").show();
		$("#div_laArea_start").show();
		$("#div_amount").show();
		$("#div_move").show();
		$("#div_manageOrg").show();
		$("#div_pic").show();
		if(form1.originalAmount.value == ""){
			form1.originalAmount.value = "1";
		}
		
		setFormItem("originalAmount","R");
		
		if(form1.originalHoldNumeLa.value == ""){
			form1.originalHoldNumeLa.value = "1";
		}

		if(form1.originalHoldDenoLa.value == ""){
			form1.originalHoldDenoLa.value = "1";	
		}
		
		$("#div_material").show();
		$("#div_propertyUnit").show();
		$("#div_limitYear").show();
		
	}else if("2" == checkStr1){
		$("#div_buSignNo").show();
		$("#div_door").show();
		$("#div_buArea").show();
		$("#div_amount").show();
		$("#div_move").show();
		$("#div_depr").show();
// 		$("#div_depr2").show();
		$("#div_manageOrg").show();
		$("#div_pic").show();
		if(form1.originalAmount.value == ""){
			form1.originalAmount.value = "1";
		}
		
		setFormItem("originalAmount","R");
		//setFormItem("originalUnit","R");
		if (form1.cb_selfdepr.checked == false) {
			form1.originalUnit.disabled= true;
		}
		if(form1.originalSArea.value == ""){
			form1.originalSArea.value = "0";	
		}
		if(form1.originalHoldNumeBu.value == ""){
			form1.originalHoldNumeBu.value = "1";
		}
		if(form1.originalHoldDenoBu.value == ""){
			form1.originalHoldDenoBu.value = "1";
		}
		
		$("#div_material").show();
		$("#div_propertyUnit").show();
		
		//if($("input[name='limitYear']").val() == ''){
		//	$("#div_otherLimitYear").show();
		//}else{
			$("#div_limitYear").show();	
		//}
		
	}else if("3" == checkStr1 || "4" == checkStr1 || "5" == checkStr1){
		$("#div_spec").show();
		$("#div_amount").show();
		$("#div_depr").show();
// 		$("#div_depr2").show();
		$("#div_pic").show();
		if(form1.state.value == 'insert'){
			$("#div_move").show();			
		} else {
			$("#div_move").hide();
		}
		
		//if($("input[name='material']").val() == ''){
			$("#div_otherMaterial").show();
		//}else{
			$("#div_material").show();	
		//}
		//if($("input[name='propertyUnit']").val() == ''){
			$("#div_otherPropertyUnit").show();
		//}else{
			$("#div_propertyUnit").show();	
		//}
		//if($("input[name='limitYear']").val() == ''){
		//	$("#div_otherLimitYear").show();
		//}else{
			$("#div_limitYear").show();	
		//}
		
	}else if("8" == checkStr1){
		$("#div_proofDoc").show();
		$("#div_amount").show();	

		if(form1.originalAmount.value == ""){
			form1.originalAmount.value = "1";
		}
		form1.originalUnit.disabled= true;
		setFormItem("originalAmount","R");
		
		$("#div_material").show();
		$("#div_propertyUnit").show();
		$("#div_limitYear").show();
		// 問題單1010  公務類折舊新增
		$("#div_depr").show();
		$("#div_move").show();
// 		$("#div_depr2").show();
	}else if("9" == checkStr1){
		$("#div_security").show();
		$("#div_amount").show();
		
		$("#div_material").show();
		$("#div_propertyUnit").show();
		$("#div_limitYear").show();
		// 客戶758 要求股票要可以登打保管資料
		$("#div_move").show();
		
		//setFormItem("originalUnit","R");
		form1.originalUnit.disabled= true;
	}
	
}	
//更改財產編號changePropertyNo時自動帶入當前登入帳號之保管單位和保管人
function defaultValue(checkStr1){
	if (checkStr1 != "8") {
		if(form1.originalKeepUnit.value==""){
			form1.originalKeepUnit.value="<%=user.getUnitID()%>";	
		}
	    if(form1.originalUseUnit.value==""){
	    	form1.originalUseUnit.value=form1.originalKeepUnit.value;
	    }
		if(form1.originalKeeper.value==""){
			form1.originalKeeper.value="<%=user.getKeeperno()%>";
		}
		if(form1.originalUser.value==""){
            form1.originalUser.value=form1.originalKeeper.value;
		}
	}
}

function setDoorplateAll(){
	var s = "";
	
	if($("select[name='doorPlate1'] option:selected").val() != ''){
		s += $("select[name='doorPlate1'] option:selected").text();
	}
	if($("select[name='doorPlate2'] option:selected").val() != ''){
		s += $("select[name='doorPlate2'] option:selected").text();
	}
	if($("select[name='doorPlate3'] option:selected").val() != ''){
		s += $("select[name='doorPlate3'] option:selected").text();
	}
	if($("input[name='doorPlatevillage1']").val() != ''){
		s += $("input[name='doorPlatevillage1']").val();
		if($("select[name='doorPlatevillage2'] option:selected").val() != ''){
			s += $("select[name='doorPlatevillage2'] option:selected").text();
		}
	}	
	if($("input[name='doorplateRd1']").val() != ''){
		s += $("input[name='doorplateRd1']").val();
		if($("select[name='doorplateRd2'] option:selected").val() != ''){
			s += $("select[name='doorplateRd2'] option:selected").text();
		}
	}
	
	if($("input[name='doorplateSec']").val() != ''){
		s += $("input[name='doorplateSec']").val() + "段";
	}
	if($("input[name='doorplateLn']").val() != ''){
		s += $("input[name='doorplateLn']").val() + "巷";
	}
	if($("input[name='doorplateAly']").val() != ''){
		s += $("input[name='doorplateAly']").val() + "弄";
	}
	if($("input[name='doorplateNo']").val() != ''){
		s += $("input[name='doorplateNo']").val() + "號";
	}
	if($("input[name='doorplateFloor1']").val() != ''){
		s += $("input[name='doorplateFloor1']").val() + "樓";
	}
	if($("input[name='doorplateFloor2']").val() != ''){
		s += $("input[name='doorplateFloor2']").val();
	}
	
	$("input[name='doorPlate4']").val(s);
	
}


//更新面積資料
function changeArea(check){
	var holdAreaLa;
	form1.originalHoldNumeLa.value = Math.round(form1.originalHoldNumeLa.value);
	form1.originalHoldDenoLa.value = Math.round(form1.originalHoldDenoLa.value);	
	holdAreaLa = roundp((form1.originalAreaLa.value * form1.originalHoldNumeLa.value / form1.originalHoldDenoLa.value),'2',"Y") ;
	if(holdAreaLa==null)	holdAreaLa=0;
	if(holdAreaLa=="")		holdAreaLa=0;
	form1.originalHoldAreaLa.value = holdAreaLa;	
	
	form1.originalCArea.value = roundp(form1.originalCArea.value,'2','Y');
	form1.originalSArea.value = roundp(form1.originalSArea.value,'2','Y');
	var holdAreaBu;
	form1.originalAreaBu.value = roundp(parseFloat(form1.originalCArea.value) + parseFloat("0"+form1.originalSArea.value), '2', 'Y');
	form1.originalHoldNumeBu.value = Math.round(form1.originalHoldNumeBu.value);
	form1.originalHoldDenoBu.value = Math.round(form1.originalHoldDenoBu.value);
	holdAreaBu = roundp((parseFloat(form1.originalAreaBu.value) * parseFloat(form1.originalHoldNumeBu.value) / parseFloat(form1.originalHoldDenoBu.value)),'2',"Y") ;
	form1.originalHoldAreaBu.value = holdAreaBu;
}

function checkDeprMethod(value){
	if (form1.cb_selfdepr.checked == true) {
		calDeprBySelfDepr();
	} else {
		form1.originalDeprAmount.value = "";
		form1.originalScrapValue.value = "";
		form1.originalAccumDepr.value = "";
		form1.originalMonthDepr.value = "";
		form1.originalMonthDepr1.value = "";
		form1.originalApportionMonth.value = "";
		form1.originalApportionEndYM.value = "";
		
		if(form1.state.value == 'insert' || form1.state.value == 'update'){
			setFormItem("cb_originalBuildFeeCB,originalaccumdepr1,originalaccumdepr2","O");
			setFormItem("cb_originalDeprUnitCB,originalDeprPark,originalDeprUnit,originalDeprUnit1,originalDeprAccounts","O");
		}
		
		if(form1.originalDeprMethod.value == '01'){ // 不必折舊
			form1.cb_originalBuildFeeCB.value = "";
			form1.cb_originalDeprUnitCB.value = "";
			form1.originalDeprUnit1.value = "";
			form1.originalBuildFeeCB.value = "";
			form1.originalDeprUnitCB.value = "";
			form1.originalDeprPark.value = "";
			form1.originalDeprUnit.value = "";
			form1.originalDeprAccounts.value = "";
			form1.originalScrapValue.value = "";
			form1.originalDeprAmount.value = "";
			form1.originalaccumdepr1.value = "";
			form1.originalaccumdepr2.value = "";
			form1.originalAccumDepr.value = "";
			form1.originalApportionMonth.value = "";
			form1.originalMonthDepr.value = "";
			form1.originalMonthDepr1.value = "";
			form1.originalApportionEndYM.value = "";
			setFormItem("cb_originalBuildFeeCB,cb_originalDeprUnitCB,originalDeprUnit1,originalBuildFeeCB,originalDeprUnitCB,originalDeprPark,deprPark,originalDeprUnit,originalDeprAccounts,originalDeprAmount,originalaccumdepr1,originalaccumdepr2,originalApportionMonth,originalMonthDepr,originalScrapValue","R");
		
		}else if(form1.originalDeprMethod.value == '02'){ // 直線不留殘值
			if($("#cb_originalDeprUnitCB").attr('checked') == 'checked'){			
				form1.originalDeprUnit.value = "";
				form1.originalDeprAccounts.value = "";
			}
			
			if(form1.buyDate.value == ''){
				
			}else{		
				if(form1.originalScrapValue.value == ''){
					form1.originalScrapValue.value = "0";
				}
				// 問題單 1055  有些財產大類(土改, 建物 無法輸入單價 就要抓總價)
				// TODO 
				if (form1.originalUnit.disabled  || parseFloat(form1.originalAmount.value) <= 1) {
					form1.originalDeprAmount.value = form1.originalBV.value;
				} else {
					form1.originalDeprAmount.value = form1.originalUnit.value;	
				}
				
				
				//應攤提折舊總額
				var MonthStr;
				if(value == ''){
					var limityear = 0;
					//if(form1.originalLimitYear.value != ''){	limityear = form1.originalLimitYear.value;
					//}else{										limityear = form1.otherLimitYear.value;
					//}		
					//limityear = form1.originalLimitYear.value;
					//limityear = parseInt(limityear) * 12;
					limityear = form1.otherLimitYear.value;
					form1.originalApportionMonth.value = limityear;
					if(isNaN(form1.originalApportionMonth.value))	form1.originalApportionMonth.value = "";				
					MonthStr = limityear;
				}else{
					MonthStr = value;
				}
				
				var accumDepr1 = isRealNaN(form1.originalaccumdepr1.value) ? 0 : parseFloat(form1.originalaccumdepr1.value); // 本年度累計折舊
				var accumDepr2 = isRealNaN(form1.originalaccumdepr2.value) ? 0 : parseFloat(form1.originalaccumdepr2.value); // 以前年度累計折舊
				form1.originalAccumDepr.value = accumDepr1 + accumDepr2;
				// 應攤提總額 = 原價 - 累折
				form1.originalDeprAmount.value = parseFloat(form1.originalDeprAmount.value) - parseFloat(form1.originalAccumDepr.value);
				
				//月提折舊金額
				form1.originalMonthDepr.value = Math.floor((parseInt(form1.originalDeprAmount.value) - parseInt(form1.originalScrapValue.value))/ MonthStr);
				if(isNaN(form1.originalMonthDepr.value))	form1.originalMonthDepr.value = "";
				
				//月提折舊金額（最後一個月）
				form1.originalMonthDepr1.value = (parseInt(form1.originalDeprAmount.value) - parseInt(form1.originalMonthDepr.value) * (MonthStr - 1)); 
				if(isNaN(form1.originalMonthDepr1.value))	form1.originalMonthDepr1.value = "";
				
				//攤提年限截止年月
				var YYY = parseInt(form1.buyDate.value.substring(0,3),10) + parseInt((MonthStr/12),10);
				var MM = parseInt(form1.buyDate.value.substring(3,5),10) + parseInt((MonthStr%12),10);

				if((MM/12) > 1){
					YYY = parseInt(YYY,10) + 1;
					MM = MM%12;
				}
					
				form1.originalApportionEndYM.value = frontZero(YYY,3) + frontZero(MM,2);
				if(isNaN(form1.originalApportionEndYM.value))	form1.originalApportionEndYM.value = "";
			}
		} else if(form1.originalDeprMethod.value == '03'){ // 直線平均法-留殘值
			if($("#cb_originalDeprUnitCB").attr('checked') == 'checked'){			
				form1.originalDeprUnit.value = "";
				form1.originalDeprAccounts.value = "";
			}
			
			if(form1.buyDate.value !== ''){
				var originalbookvalue;
				
				if (form1.originalUnit.disabled || parseFloat(form1.originalAmount.value) <= 1) {
					originalbookvalue = form1.originalBV.value;
				} else {
					originalbookvalue = form1.originalUnit.value;
				}
				var oLimityear = form1.otherLimitYear.value; //TODO value似乎原本是當作使用年限傳入..但幾乎都沒傳
				
				if (!isNaN(originalbookvalue) && !isNaN(oLimityear) && oLimityear > 0) {
					var checkStr1 = form1.propertyNo.value.substring(0,1);
					if (checkStr1 === '8') {
						form1.originalScrapValue.value = "0";
					} else {
						form1.originalScrapValue.value = Math.round(originalbookvalue * 0.01); // 預設殘值為 總價 * 1% 四捨五入
					}
					
					var accumDepr1 = isRealNaN(form1.originalaccumdepr1.value) ? 0 : parseFloat(form1.originalaccumdepr1.value); // 本年度累計折舊
					var accumDepr2 = isRealNaN(form1.originalaccumdepr2.value) ? 0 : parseFloat(form1.originalaccumdepr2.value); // 以前年度累計折舊
					form1.originalAccumDepr.value = accumDepr1 + accumDepr2;
					// 應攤提總額 = 原值-殘值-累折
					form1.originalDeprAmount.value = originalbookvalue - form1.originalScrapValue.value - (accumDepr1 + accumDepr2); //原始-應攤提折舊總額
					
					form1.originalApportionMonth.value = oLimityear; // 原始-攤提壽月
					// 原始-月提金額
					form1.originalMonthDepr.value = Math.floor((parseInt(form1.originalDeprAmount.value))/ oLimityear);
					if(isNaN(form1.originalMonthDepr.value)) {
						form1.originalMonthDepr.value = "";
					}
					// 原始-月提金額(最後一個月)
					form1.originalMonthDepr1.value = (parseInt(form1.originalDeprAmount.value) - parseInt(form1.originalMonthDepr.value) * (oLimityear - 1));
					if (isNaN(form1.originalMonthDepr1.value)) {
						form1.originalMonthDepr1.value = "";
					}
					//原始-截止年月
					var YYY = parseInt(form1.buyDate.value.substring(0,3),10) + parseInt((oLimityear/12),10);
					var MM = parseInt(form1.buyDate.value.substring(3,5),10) + parseInt((oLimityear%12),10);

					if((MM/12) > 1){
						YYY = parseInt(YYY,10) + 1;
						MM = MM%12;
					}
					form1.originalApportionEndYM.value = frontZero(YYY,3) + frontZero(MM,2);
					if(isNaN(form1.originalApportionEndYM.value))	form1.originalApportionEndYM.value = "";
				}
			}
		}
		
		if (form1.state.value != 'init' && form1.individualSetDepr.value != '') {
			calDepr();
		}
		
		syncDeprData();
	}
}

function frontZero(valueStr,itemp){
	var nv = valueStr.toString();
	var stemp="";
	if(nv!=""){
		itemp -= nv.length;
		stemp = "";		
		for (var i=0;i<itemp;i++){
			stemp+="0";
		}		
		stemp += nv;
	}
	return stemp;
}

function getSourceKindName(popSourceKind,popSourceKindName){
	var checkStr1 = form1.propertyNo.value.substring(0, 1);
	var movableGroup = ['2', '3', '4', '5'];
	var transferGroup = ['28', '29'];
	
	//傳送JSON
	var comment={};	
	comment.codeid = document.all.item("sourceKind").value;
	
	$.post('querySourceKind.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');
			if(typeof(data['codename']) == 'undefined'){
				$("input[name='sourceKind']").val('');
			}else{
				$("input[name='sourceKindName']").val(data['codename']);	
			}
		});
	
	if (movableGroup.includes(checkStr1) && transferGroup.includes(comment.codeid)) {
		document.getElementById("selfdeprText").style.display = "";
		document.getElementById("cb_selfdepr").style.display = "";
	} else {
		document.getElementById("selfdeprText").style.display = "none";
		document.getElementById("cb_selfdepr").style.display = "none";
		form1.selfdepr.value = "N";
		form1.cb_selfdepr.checked = false;
	}
	
	checkDeprMethod('');
}

function getCauseName(popCause,popCauseName){
	//傳送JSON
	var comment={};	
	comment.codeid = document.all.item("cause").value;
	comment.codecond1_1 = "1";
	comment.codecond1_2 = "4";
	
	$.post('queryCause.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			if(typeof(data['codename']) == 'undefined'){
				$("input[name='cause']").val('');
			}else{
				$("input[name='causeName']").val(data['codename']);	
			}
			
		});
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

function popCalndar(dateField,js,sY,sM){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=320px,height=250,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";	
	closeReturnWindow();
	returnWindow=window.open(getVirtualPath() + 'home/calendar.jsp?dateField=' + dateField + '&sY='+sY+'&sM='+sM+'&js='+js,'popCalendar',prop);		
}

function reCalculate(){
	form1.sourceDate.value = form1.buyDate.value;
	checkDeprMethod('');
	
}
function calValue(name){
	if(isNaN(form1.originalAmount.value) || isNaN(form1.originalUnit.value) || isNaN(form1.originalBV.value)){
		switch(name){
		case 'originalAmount':	
			alert(" 數量(股數) 必須是數字 !");
			form1.originalAmount.value = '';
			break;
		case 'originalUnit': 
			alert(" 單價 必須是數字 !"); 
			form1.originalUnit.value = '';
			break;
		case 'originalBV': 
			alert(" 總價 必須是數字 !"); 
			form1.originalBV.value = '';
			break;
		}		
	}else if(form1.originalAmount.value<0 || form1.originalUnit.value<0 || form1.originalBV.value<0){
		switch(name){
		case 'originalAmount':	
			alert(" 數量(股數) 必須大於0 !"); 
			form1.originalAmount.value = '';
			break;
		case 'originalUnit': 
			alert(" 單價 必須大於0 !"); 
			form1.originalUnit.value = '';
			break;
		case 'originalBV': 
			alert(" 總價 必須大於0 !"); 
			form1.originalBV.value = '';
			break;
		}		
	}else{
		var checkStr1 = form1.propertyNo.value.substring(0,1);
		var checkStr3 = form1.propertyNo.value.substring(0,3);
		
		//土地
		if(checkStr3 != '111' && checkStr1 == '1'){
			//輸入單價	權利面積 * 單價 四捨五入至元
			if(name == 'originalUnit'){
				form1.originalUnit.value = roundp(form1.originalUnit.value,'2',"Y") ;
				form1.originalBV.value = roundp((form1.originalHoldAreaLa.value * form1.originalUnit.value),'0',"Y").replace(".","");
				
			//輸入總價	總價 /權利面積  四捨五入至元
			}else if(name == 'originalBV'){
				form1.originalBV.value = roundp(form1.originalBV.value,'0',"Y").replace(".","");
				form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalHoldAreaLa.value),'2',"Y");
				
			}else if(form1.originalUnit.value != ''){
				form1.originalBV.value = roundp((form1.originalHoldAreaLa.value * form1.originalUnit.value),'0',"Y").replace(".","");
			}
		//建物
		} else if (checkStr1 == '2') {
			//輸入單價時	數量 * 單價 = 總價
			if(name == 'originalUnit'){
				form1.originalBV.value = roundp((form1.originalAmount.value * form1.originalUnit.value),'0',"Y").replace(".","");
			//輸入總價	總價 /權利面積  四捨五入至元
			}else if(name == 'originalBV'){
				form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalAmount.value),'0',"Y").replace(".","");
			}
		//動產
		}else if(checkStr1 == '3' || checkStr1 == '4' || checkStr1 == '5'){	
			var transferGroup = ['28', '29'];
			
			//輸入單價時	數量 * 單價 = 總價
			if(name == 'originalUnit'){
				var errorMessage = '';	
			    if(form1.originalUnit.value.indexOf(".")>=0){
			    	errorMessage = '單價不得輸入小數點';
			    }			    
			  	//財產_財編503 為圖書類不需判斷單價小於一萬
			  	//撥入及接管不須判斷單價小於一萬
				if (form1.originalUnit.value < 10000 && form1.propertyNo.value.substring(0,3) != '503' && !transferGroup.includes(form1.sourceKind.value)) {
					errorMessage = '單價必須大於等於10000';
				}				
				//104.12.17 當單價有值，數量>=1或總價為空時，才自動計算總價
				if (form1.originalUnit.value && (form1.originalAmount.value >= 1 || !(form1.originalBV.value))) {
					form1.originalBV.value = roundp((form1.originalAmount.value * form1.originalUnit.value),'0',"Y").replace(".","");
				}				
				if(errorMessage == ''){
					form1.originalUnit.style.backgroundColor = "";					
				}else{
					form1.originalUnit.value = '';
					form1.originalUnit.style.backgroundColor = errorbg;
					alert(errorMessage);
				}
			//輸入總價
			} else if(name == 'originalBV') {				
				form1.originalBV.value = roundp(form1.originalBV.value,'0',"Y").replace(".","");
				//104.12.17 當總價有值，數量>=1或是單價為空時，才自動計算單價
				if (form1.originalBV.value && (form1.originalAmount.value >= 1 || !(form1.originalUnit.value))) {
					form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalAmount.value),'0',"Y").replace(".","");
				}
				var errorMessage = '';
				//財產_財編503 為圖書類不需判斷單價小於一萬
				//撥入及接管不須判斷單價小於一萬
				if(form1.originalBV.value < 10000 && form1.propertyNo.value.substring(0,3) != '503' && !transferGroup.includes(form1.sourceKind.value)){
					errorMessage = '總價必須大於等於10000';
				}
				if(errorMessage == ''){
					form1.originalBV.style.backgroundColor = "";
				}else{
					form1.originalBV.value = '';
					form1.originalBV.style.backgroundColor = errorbg;
					alert(errorMessage);
				}
			//輸入數量
			}else if(name == 'originalAmount'){
				var errorMessage = '';
				// 104.12.17 當數量>1且單價有值時計算總價
				if (form1.originalAmount.value >= 1 && form1.originalUnit.value) {
					form1.originalBV.value = roundp(form1.originalUnit.value * form1.originalAmount.value,'0',"Y").replace(".","");
					if(form1.originalAmount.value.indexOf(".")>0){
						errorMessage = '數量大於1時，必須為整數';
						form1.originalAmount.style.backgroundColor = errorbg;
						form1.originalAmount.value='';
						alert(errorMessage);
					}					
				}else if(form1.originalAmount.value>0){
					//總價有值且單價為空時計算單價
					if(form1.originalBV.value && !(form1.originalUnit.value)){
						form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalAmount.value),'0',"Y").replace(".","");
					}
					//單價有值且總價為空時計算總價
					if(form1.originalUnit.value && !(form1.originalBV.value)){
						form1.originalBV.value = roundp(form1.originalUnit.value * form1.originalAmount.value,'0',"Y").replace(".","");
					}
				}
				if(errorMessage == ''){
					form1.originalBV.style.backgroundColor = "";
					form1.originalUnit.style.backgroundColor = "";
					form1.originalAmount.style.backgroundColor = "";
				}
			}
		}else if(checkStr1 == '8'){
			if(name == 'originalUnit'){
				form1.originalBV.value = roundp((form1.originalAmount.value * form1.originalUnit.value),'0',"Y").replace(".","");	
			}else{
				if(form1.originalAmount.value != ''){
				//	form1.originalUnit.value = roundp((parseInt(form1.originalBV.value) / parseInt(form1.originalAmount.value)),'2',"Y");
				}
			}			
		}else if(checkStr1 == '9'){
			
		}else{
			form1.originalBV.value = roundp(form1.originalBV.value,'0',"Y").replace(".","");
			form1.originalUnit.value = roundp(form1.originalUnit.value,'0',"Y").replace(".","");
		}
	}		
}


//***************************************************************************
function selectOriginalBuildFeeCB(){
	if($("#cb_originalBuildFeeCB").attr('checked') == 'checked'){
		queryDeprUnitDataforBuildFeeCB();
		$("select[name='originalDeprUnit']").attr('disabled', true);
	}else{
		$("select[name='originalDeprUnit']").attr('disabled', false);
	}
}

function queryDeprUnitDataforBuildFeeCB(){
	//傳送JSON		
	var comment={};	
	comment.enterOrg = document.all.item("enterOrg").value;
	
	$.post('queryBuildFeeCB.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("select[name='originalDeprUnit']").val(data['deprunitno']);
						
			var options = data['value'].split(":;:");
			
			$("select[name='originalDeprAccounts'] option").remove();

			$("select[name='originalDeprAccounts']").append($("<option></option>").attr("value", "").text("請選擇"));
			
			for (var i = 0; i < options.length; i++){
				
				var option = options[i].split(":<:");
				
				$("select[name='originalDeprAccounts']").append($("<option></option>").attr("value", option[0]).text(option[1]));	
				
			}
			
			syncDeprData();
			
		});
}

function clearStoreNo(){
	form1.storeNo.value="";
	form1.storeNoName.value="";
}

function checkvalueable(){
	if($("select[name='valuable'] option:selected").val() == 'Y'){
		form1.originalDeprMethod.value = '01';
	}
	if($("select[name='valuable'] option:selected").val() == 'N'){
		form1.originalDeprMethod.value = '';
	}	
	checkDeprMethod('');
	myQueryDeprUnitData();
}

function chgIndividualSetDepr() {
	if (form1.individualSetDepr.value != '') {
		setFormItem("verifyYM", "O");
		if (form1.verifyYM.value != '') {
			calDepr();		
		}
	} else {
		form1.verifyYM.value = "";
		setFormItem("verifyYM", "R");
		checkDeprMethod('');
	}
}

function chgVerifyYM() {
	var alertStr = "";
	alertStr += checkEmpty(form1.verifyYM, "入帳年月");
	alertStr += checkYYYMM(form1.verifyYM, "入帳年月");
	
	if(alertStr.length != 0) { 
		alert(alertStr); 
		form1.verifyYM.value = "";
		return false; 
	}
	
	calDepr();
}

function calDepr() {
	var apportionEndYM = form1.originalApportionEndYM.value;
	var verifyYM = form1.verifyYM.value;
	if (verifyYM == "" || verifyYM == null) {
		return;
	}
	var apportionMonth = (apportionEndYM.substring(0, 3) - verifyYM.substring(0, 3)) * 12 + (apportionEndYM.substring(3, 5) - verifyYM.substring(3, 5));
	if (form1.individualSetDepr.value == 'A') {
		apportionMonth = apportionMonth + 1;
	}
	
	//攤提壽月
	if (apportionMonth > 0) {
		form1.originalApportionMonth.value = apportionMonth;
		//月提折舊金額
		form1.originalMonthDepr.value = Math.floor(parseInt(form1.originalDeprAmount.value) / apportionMonth);
		if (isNaN(form1.originalMonthDepr.value)) {
			form1.originalMonthDepr.value = "";
		}
		
		//月提折舊金額（最後一個月）
		form1.originalMonthDepr1.value = (parseInt(form1.originalDeprAmount.value) - parseInt(form1.originalMonthDepr.value) * (apportionMonth - 1)); 
		if (isNaN(form1.originalMonthDepr1.value)) {
			form1.originalMonthDepr1.value = "";
		}
	} else {
		form1.originalApportionMonth.value = 0;
		form1.originalMonthDepr.value = 0;
		form1.originalMonthDepr1.value = 0;
	}
}

function checkSelfDepr() {
	if ($("#cb_selfdepr").attr('checked') == 'checked') {
		document.getElementById("originalScrapValue").readOnly = false;
		document.getElementById("originalDeprAmount").readOnly = false;
		form1.cb_selfdepr.checked = true;
		form1.selfdepr.value="Y";
		form1.originalUnit.disabled = false;
	} else {		
		document.getElementById("originalScrapValue").readOnly = true;
		document.getElementById("originalDeprAmount").readOnly = true;
		form1.cb_selfdepr.checked = false;
		form1.selfdepr.value="N";
		form1.originalUnit.disabled = true;
	}
	checkDeprMethod('');
}

function calDeprBySelfDepr() {
	if ($("#cb_originalDeprUnitCB").attr('checked') == 'checked') {			
		form1.originalDeprUnit.value = "";
		form1.originalDeprAccounts.value = "";
	}
	
	var apportionEndYM = form1.originalApportionEndYM.value;
	var verifyYM = form1.verifyYM.value;
	if (verifyYM == "" || verifyYM == null) {
		return;
	}
	var apportionMonth = (apportionEndYM.substring(0, 3) - verifyYM.substring(0, 3)) * 12 + (apportionEndYM.substring(3, 5) - verifyYM.substring(3, 5));
	if (form1.individualSetDepr.value == 'A') {
		apportionMonth = apportionMonth + 1;
	}
	
	if (form1.buyDate.value !== '') {
		var originalbookvalue;
		
		if (form1.originalUnit.disabled || parseFloat(form1.originalAmount.value) <= 1) {
			originalbookvalue = form1.originalBV.value;
		} else {
			originalbookvalue = form1.originalUnit.value;
		}
		var oLimityear = form1.otherLimitYear.value; //TODO value似乎原本是當作使用年限傳入..但幾乎都沒傳
		
		if (!isNaN(originalbookvalue) && !isNaN(oLimityear) && oLimityear > 0) {
			var checkStr1 = form1.propertyNo.value.substring(0,1);
			var accumDepr1 = isRealNaN(form1.originalaccumdepr1.value) ? 0 : parseFloat(form1.originalaccumdepr1.value); // 本年度累計折舊
			var accumDepr2 = isRealNaN(form1.originalaccumdepr2.value) ? 0 : parseFloat(form1.originalaccumdepr2.value); // 以前年度累計折舊
			form1.originalAccumDepr.value = accumDepr1 + accumDepr2;
			
			form1.originalApportionMonth.value = oLimityear; // 原始-攤提壽月
			// 原始-月提金額
			form1.originalMonthDepr.value = Math.floor((parseInt(form1.originalDeprAmount.value))/ oLimityear);
			if(isNaN(form1.originalMonthDepr.value)) {
				form1.originalMonthDepr.value = "";
			}
			// 原始-月提金額(最後一個月)
			form1.originalMonthDepr1.value = (parseInt(form1.originalDeprAmount.value) - parseInt(form1.originalMonthDepr.value) * (oLimityear - 1));
			if (isNaN(form1.originalMonthDepr1.value)) {
				form1.originalMonthDepr1.value = "";
			}
			//原始-截止年月
			var YYY = parseInt(form1.buyDate.value.substring(0,3),10) + parseInt((oLimityear/12),10);
			var MM = parseInt(form1.buyDate.value.substring(3,5),10) + parseInt((oLimityear%12),10);

			if((MM/12) > 1){
				YYY = parseInt(YYY,10) + 1;
				MM = MM%12;
			}
			form1.originalApportionEndYM.value = frontZero(YYY,3) + frontZero(MM,2);
			if(isNaN(form1.originalApportionEndYM.value))	form1.originalApportionEndYM.value = "";
		}
	}
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">	
<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="userOrganID" value="<%=user.getOrganID()%>">
<input type="hidden" class="field" name="proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" class="field" name="proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" class="field" name="proofNo" value="<%=obj.getProofNo()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<table class="queryTable"  border="1">
	<% request.setAttribute("UNTCH001Q",obj);%>
	<jsp:include page="untch001q02.jsp" />
	</table>
</div>

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<tr>
			<td class="td_form" width="15%">入帳機關：</td>
			<td class="td_form_white" colspan="3">
				<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
				[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
				&nbsp;&nbsp;&nbsp;
				權屬：
				<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
				<select class="field_QRO" type="select" name="ownershipName">
					<%=util.View.getOnwerOption(obj.getOwnership())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				
				<input class="field_QRO" type="hidden" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>">
			</td>
		</tr>
		<tr>
			<td class="td_form">財產區分別：</td>
			<td colspan="3" class="td_form_white">
				<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>
				&nbsp;&nbsp;&nbsp;		
				序號：
				[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo() %>">]
        	</td>
        </tr>
		<tr>
			<td class="td_form">工程編號：</td>
			<td colspan="3" class="td_form_white">
        		[<input class="field_QRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo() %>">]
				[<input class="field_QRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName() %>">]			
        	</td>
        </tr>
		<tr>
			<td class="td_form"><font color="red">*</font>財產編號：</td>
			<td class="td_form_white" colspan="3">
				<%=uctls.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"&isLookup=Y,fromAdd=Y")%>
				分號(起)：
				[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]				
				<input class="field_PRO" type="hidden" name="serialNoS" size="7" maxlength="7" value="<%=obj.getSerialNoS()%>">
				&nbsp;&nbsp;&nbsp;
				(訖)：
				[<input class="field_PRO" type="text" name="serialNoE" size="7" maxlength="7" value="<%=obj.getSerialNoE()%>">]
				<input class="field_PRO" type="hidden" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">
				<br>
				<font color="red">*</font>購置日期
				<input class="field" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>" onchange="form1.sourceDate.value = this.value;checkDeprMethod('');">
				<input class="field" type="button" name="btn_buyDate" onclick="popCalndar('buyDate','reCalculate()')" value="..." title="萬年曆輔助視窗">				
				&nbsp;&nbsp;&nbsp;
				財產別名
				<input type="text" class="field" name="propertyName1" value="<%=obj.getPropertyName1() %>" size="30" maxlength="30">
			</td>
		</tr>
		<tr>
		  <td class="td_form"><font color="red">*</font>財產來源：</td>
		  <td colspan="3" class="td_form_white">
		  	<%=uctls.getSourceKind("field","sourceKind",obj.getSourceKind(),obj.getSourceKindName())%>
		  	<font color="red">*</font>財產取得日期：<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%> 　
			文號：<input class="field" type="text" name="sourceDoc" size="25" maxlength="40" value="<%=obj.getSourceDoc()%>">
		  </td>	
		</tr>
		
		<tr style="display:none">
	      <td class="td_form">增加原因：</td>
	      <td class="td_form_white" colspan="3">
	      	<%=uctls.getCause("field","cause",obj.getCause(),obj.getCauseName(),"1,4")%>
	      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          	其它說明：
			<input class="field" type="text" name="cause1" size="12" maxlength="12" value="<%=obj.getCause1() %>" readonly="true">	      		
	      </td>
		</tr>
		
		<tr>
			<td class="td_form"><font color="red">*</font>財產性質：</td>
			<td colspan="3" class="td_form_white">
				<%=util.View._getSelectHTML("field", "propertyKind", obj.getPropertyKind(),"PKD") %>
				&nbsp;&nbsp;&nbsp;
				基金財產
				<%=util.View._getSelectHTML_withEnterOrg("field", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>珍貴財產：
				<select class="field" type="select" name="valuable" onchange="checkvalueable();">
				　　<%=util.View.getTextOption("Y;是;N;否;",obj.getValuable())%>
				</select>				                
        	</td>
        </tr>	
	        	
		<tr>
			<td class="td_form">主要材質：</td>
			<td class="td_form_white" colspan="3">
				主要材質：
				<span id="div_material">
					[<input class="field_RO" type="text" name="material" size="25" value="<%=obj.getMaterial()%>">]
				</span>			
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="div_otherMaterial">
				其他主要材質：
					<input class="field" type="text" name="otherMaterial" size="25" maxlength="25" value="<%=obj.getOtherMaterial()%>">
				<br/>
				</span>
				<span id="div_propertyUnit">
				單位：
					[<input class="field_RO" type="text" name="propertyUnit" size="8" maxlength="25" value="<%=obj.getPropertyUnit()%>">]
				</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="div_otherPropertyUnit">
				其他單位：
					<input class="field" type="text" name="otherPropertyUnit" size="8" maxlength="25" value="<%=obj.getOtherPropertyUnit() %>">
				<br/>
				</span>
				<span id="div_limitYear">
				<font color="red">*</font>原使用年限：
	        		<input class="field" type="text" name="originalLimitYear" size="3" maxlength="3" 
	        		              value="<%=obj.getOriginalLimitYear()%>" 
	        		              onchange='form1.otherLimitYear.value = this.value * 12; checkDeprMethod("");syncDeprData(); '>
	        		 &nbsp;&nbsp;&nbsp;&nbsp;
	        		<font color="red">*</font>使用年限(月)：
	        		<input class="field" type="text" name="otherLimitYear" size="5" maxlength="5" value="<%=obj.getOtherLimitYear()%>"
	        		              onchange='checkDeprMethod("");syncDeprData(); '>
	        	</span>
			</td>			
		</tr>
		
		<tr id="div_spec">
			<td class="td_form" >廠牌型式：</td>
			<td class="td_form_white" colspan="3">
			<font color="red">*</font>型式
				<input class="field" type="text" id="specification" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification() %>">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>廠牌
				<input class="field" type="text" id="nameplate" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate() %>">
				&nbsp;&nbsp;&nbsp;<font color="red">若無廠牌型式請填「無」</font>
				<br>
				廠商：
				<input class="field" type="hidden" name="storeNo" size="5" maxlength="10" value="<%=obj.getStoreNo()%>">
				[<input class="field_RO" type="text" name="storeNoName" size="20" maxlength="50" value="<%=obj.getStoreNoName()%>">]
				<input class="field" type="button" name="btn_storeNo" onclick="popStore('storeNo','storeNoName')" value="..." title="廠商輔助視窗">
				<input class="field" type="button" name="clear_storeNo" onclick="clearStoreNo();" value="清除">
				&nbsp;&nbsp;&nbsp;	
				品名：
				<input name="articleName" type="text" class="field" value="<%=obj.getArticleName()%>" size="20" maxlength="10">
			</td>
		</tr>		
		<tr id="div_laSignNo">
			<td class="td_form"><font color="red">*</font>土地標示：</td>
			<td class="td_form_white">
				<select class="field" type="select" name="laSignNo1" onChange="changeSignNo1('laSignNo1','laSignNo2','laSignNo3','');refreshTime();">
					<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getLaSignNo1())%>
				</select>　
				<select class="field" type="select" name="laSignNo2" onChange="changeSignNo2('laSignNo1','laSignNo2','laSignNo3','');refreshTime();">
					
				</select>　		
				<select class="field" type="select" name="laSignNo3">
					
				</select>　
			</td>
			<td class="td_form_white" colspan="2">
				地號：		
				<input class="field" type="text" name="laSignNo4" size="4" maxlength="4" value="<%=obj.getLaSignNo4() %>" onchange="getFrontZero(this.name,4);"> -
				<input class="field" type="text" name="laSignNo5" size="4" maxlength="4" value="<%=obj.getLaSignNo5() %>" onchange="getFrontZero(this.name,4);">
			</td>	
		</tr>			
		<tr id="div_buSignNo">
			<td class="td_form">建物標示：</td>
			<td class="td_form_white">
				<select class="field" type="select" name="buSignNo1" onChange="changeSignNo1('buSignNo1','buSignNo2','buSignNo3','');refreshTime();">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getBuSignNo1())%>
				</select>　
				<select class="field" type="select" name="buSignNo2" onChange="changeSignNo2('buSignNo1','buSignNo2','buSignNo3','');refreshTime();">
					
				</select>　		
				<select class="field" type="select" name="buSignNo3">
					
				</select>　
			</td>
			<td class="td_form_white" colspan="2">
				建號：		
				<input class="field" type="text" name="buSignNo4" size="5" maxlength="5" value="<%=obj.getBuSignNo4() %>" onchange="getFrontZero(this.name,5);"> - 
				<input class="field" type="text" name="buSignNo5" size="3" maxlength="3" value="<%=obj.getBuSignNo5() %>" onchange="getFrontZero(this.name,3);">
			</td>	
		</tr>
		<tr id="div_door">
			<td class="queryTDLable">門牌：</td>		
			<td class="queryTDInput" colspan="3">
				<select class="field" type="select" name="doorPlate1" onChange="changeSignNo1('doorPlate1','doorPlate2','doorPlate3','');" onblur="setDoorplateAll()">
					<%=util.View.getOption("select signNo ,signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getDoorPlate1())%>
				</select>
				<select class="field" type="select" name="doorPlate2" onChange="changeSignNo2('doorPlate1','doorPlate2','doorPlate3','');" onblur="setDoorplateAll()">
					
				</select>
				<span style="display:none"><select class="field" type="select" name="doorPlate3"></select></span>
				
	        	<input class="field" type="text" name="doorPlatevillage1"   size="5" maxlength="5" value="<%=obj.getDoorPlatevillage1() %>" onblur="setDoorplateAll()"/>
				<select class="field" type="select" name="doorPlatevillage2" onchange="" onblur="setDoorplateAll()">
	          		<%=util.View.getTextOption("1;村;2;里;",obj.getDoorPlatevillage2())%>
	        	</select>
				<input class="field" type="text" name="doorplateRd1"   size="15" maxlength="20" value="<%=obj.getDoorplateRd1() %>" onblur="setDoorplateAll()"/>
				<select class="field" type="select" name="doorplateRd2" onchange="" onblur="setDoorplateAll()">
	          		<%=util.View.getTextOption("1;路;2;街;3;大道",obj.getDoorplateRd2())%>
	        	</select>
				&nbsp;
				<input class="field" type="text" name="doorplateSec"   size="3"  maxlength="4"  value="<%=obj.getDoorplateSec() %>"   onblur="setDoorplateAll()"  />段
			  	<input class="field" type="text" name="doorplateLn"    size="2"  maxlength="3"  value="<%=obj.getDoorplateLn() %>"    onblur="setDoorplateAll()"  />巷
			  	<input class="field" type="text" name="doorplateAly"   size="2"  maxlength="3"  value="<%=obj.getDoorplateAly() %>"   onblur="setDoorplateAll()"  />弄
			  	<input class="field" type="text" name="doorplateNo"   size="2"  maxlength="3"  value="<%=obj.getDoorplateNo() %>"   onblur="setDoorplateAll()"  />號
			  	<input class="field" type="text" name="doorplateFloor1" size="2"  maxlength="3"  value="<%=obj.getDoorplateFloor1() %>" onblur="setDoorplateAll()" />樓
			  	<input class="field" type="text" name="doorplateFloor2" size="15"  maxlength="25"  value="<%=obj.getDoorplateFloor2() %>" onblur="setDoorplateAll()" /><br>
			  	總稱：
			  	[<input class="field_RO" type="text" name="doorPlate4" size="50"  maxlength="50"  value="<%=obj.getDoorPlate4() %>" />]		  			
			</td>
		</tr>    
		 	
		<tr id="div_laArea">
			<td class="td_form">土地原始面積：</td>
			<td class="td_form_white" colspan="3">
				<font id="div_laArea_start" color="red">*</font>
				總面積：
				<input name="originalAreaLa" type="text" class="field" value="<%=obj.getOriginalAreaLa() %>" size="12" maxlength="10" onChange="changeArea('originalAreaLa');calValue('');">			
				<br>
				權利分子：
				<input class="field" type="text" name="originalHoldNumeLa" size="9" maxlength="10" value="<%=obj.getOriginalHoldNumeLa() %>" onChange="changeArea('originalHoldNumeLa');calValue('');">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				權利分母：
				<input class="field" type="text" name="originalHoldDenoLa" size="9" maxlength="10" value="<%=obj.getOriginalHoldDenoLa() %>" onChange="changeArea('originalHoldDenoLa');calValue('');">
				&nbsp;&nbsp;
				權利範圍面積(㎡)：
				[<input class="field_RO" type="text" name="originalHoldAreaLa" size="9" maxlength="10" value="<%=obj.getOriginalHoldAreaLa() %>">]
			</td>
		</tr>
		<tr id="div_buArea">
			<td class="td_form">建物原始面積：</td>
			<td colspan="3" class="td_form_white">
				<font color="red">*</font>主面積(㎡)：
				<input class="field" type="text" name="originalCArea" size="9" maxlength="10" value="<%=obj.getOriginalCArea() %>" onChange="changeArea('originalCArea');calValue('');">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				附屬面積：
				<input class="field" type="text" name="originalSArea" size="9" maxlength="10" value="<%=obj.getOriginalSArea() %>" onChange="changeArea('originalSArea');calValue('');">
	　			&nbsp;
				總面積：
				[<input class="field_RO" type="text" name="originalAreaBu" size="12" value="<%=obj.getOriginalAreaBu() %>">]<br>
				權利分子：
				<input class="field" type="text" name="originalHoldNumeBu" size="9" maxlength="10" value="<%=obj.getOriginalHoldNumeBu() %>" onChange="changeArea('originalHoldNumeBu');calValue('');">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				權利分母：
				<input class="field" type="text" name="originalHoldDenoBu" size="9" maxlength="10" value="<%=obj.getOriginalHoldDenoBu() %>" onChange="changeArea('originalHoldDenoBu');calValue('');">
				&nbsp;&nbsp;
				權利範圍面積(㎡)：
				[<input class="field_RO" type="text" name="originalHoldAreaBu" size="9" maxlength="10" value="<%=obj.getOriginalHoldAreaBu() %>">]
			</td>
		</tr>
		<tr>
			<td class="td_form">經費來源：</td>
			<td class="td_form_white" colspan="3"> 
				<table  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<select class="field" type="select" name="fundsSource" onChange="changeSelect();">
								<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							其它說明：
							<input class="field" type="text" name="fundsSource1" size="12" maxlength="12" value="<%=obj.getFundsSource1()%>" readonly="true">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							會計科目：
							<input class="field" type="text" name="accountingTitle" size="10" maxlength="20" value="<%=obj.getAccountingTitle()%>">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr id="div_rfArea">
			<td class="td_form">土地改良物：</td>
	  	    <td colspan="3" class="td_form_white">
	  	    	<font color="red">*</font>原始計量數：
	      		<input name="originalMeasure" type="text" class="field" onChange="changeArea();" value="<%=obj.getOriginalMeasure() %>" size="15" maxlength="15">
	      		&nbsp;&nbsp;&nbsp;
	      		<input class="field_RO" type="hidden" name="propertyUnitRf" size="10" maxlength="10" value="<%=obj.getPropertyUnitRf() %>">
			</td>
		</tr>
		<tr id="div_amount">
			<td class="td_form">原始數量價值：</td>
			<td class="td_form_white" colspan="3">
				<font color="red">*</font>數量(股數)：
				<input class="field" type="text" name="originalAmount" size="10" maxlength="10" value="<%=obj.getOriginalAmount() %>" onchange="calValue(this.name);">
				&nbsp;&nbsp;&nbsp;
				單價：
				<input class="field" type="text" name="originalUnit" size="10" maxlength="10" value="<%=obj.getOriginalUnit() %>" onchange="calValue(this.name);checkDeprMethod('');">
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>總價：
				<input class="field" type="text" name="originalBV" size="10" maxlength="10" value="<%=obj.getOriginalBV() %>" onchange="calValue(this.name);checkDeprMethod('');">
				<br>
				
				帳務摘要：
				<input type="text" class="field" name="originalNote" value="<%=obj.getOriginalNote() %>" size="20">
			</td>
		</tr>	
		<tr id="div_move">
			<td class="td_form">保管使用資料：</td>
			<td class="td_form_white" colspan="3">
				保管單位：
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitname collate chinese_Taiwan_Stroke_CS_AS, unitno ", 
			                                                       "field", "form1", "originalKeepUnit", "form1.originalUseUnit.value = this.value;queryforDeprUnit();", 
			                                                       "originalKeepUnitQuickly", "", obj.getOriginalKeepUnit()) %>
				<input class="field" type="button" name="btn_originalKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'originalKeepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人：
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                        "field", "form1", "originalKeeper", "form1.originalUser.value = this.value;form1.originalUser.onchange();",
			                                                        "originalKeeperQuickly", "", obj.getOriginalKeeper()) %>
		        <input class="field" type="button" name="btn_originalKeeper" onclick="popUnitMan(form1.enterOrg.value,'originalKeeper')" value="..." title="人員輔助視窗">
				<br>
				使用單位：
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitname collate chinese_Taiwan_Stroke_CS_AS, unitno ", 
			                                                       "field", "form1", "originalUseUnit", "originalUseUnitQuickly", obj.getOriginalUseUnit()) %>
				<input class="field" type="button" name="btn_originalUseUnit" onclick="popUnitNo(form1.enterOrg.value,'originalUseUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人：
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                        "field", "form1", "originalUser", "originalUserQuickly", obj.getOriginalUser()) %>
		        <input class="field" type="button" name="btn_originalUser" onclick="popUnitMan(form1.enterOrg.value,'originalUser')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
				使用註記：
				<input type="text" class="field" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20" maxlength="100">		        
		        <br>
		        <div style="display:none">
				<%=util.View.getPopCalndar("field","originalMoveDate",obj.getOriginalMoveDate())%>
				</div>
				<font color="red">*</font>存置地點：
				<input class="field" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1() %>" onchange="queryPlaceName('enterOrg','originalPlace1');">
				<input class="field" type="button" name="btn_originalPlace1" onclick="popPlace(form1.enterOrg.value,'originalPlace1','originalPlace1Name')" value="..." title="存置地點輔助視窗">
				[<input class="field_RO" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name() %>">]
				&nbsp;&nbsp;&nbsp;		
				存置地點說明：
				<input class="field" type="text" name="originalPlace" size="30" maxlength="30" value="<%=obj.getOriginalPlace() %>">
			</td>
		</tr>
		<tr id="div_depr">
			<td class="td_form">原始折舊資料：</td>
			<td class="td_form_white" colspan="3">
				<font color="red">*</font>折舊方法：
				<%=util.View._getSelectHTML_withFunction("field", "originalDeprMethod", obj.getOriginalDeprMethod(),"DEP", "checkDeprMethod('');myQueryDeprUnitData();") %>
				&nbsp;&nbsp;
				<input class="field" type="checkbox" name="cb_originalBuildFeeCB" id="cb_originalBuildFeeCB" size="10" maxlength="10" onChange="defaultDU();"onclick="checkOriginalBuildFeeCB();selectOriginalBuildFeeCB();syncDeprData();" <%=("Y".equals(obj.getOriginalBuildFeeCB())?"checked":"")%>>
				<input class="field"  type="hidden"  name="originalBuildFeeCB" size="10" maxlength="10" value="<%=obj.getOriginalBuildFeeCB()%>">
				屬公共設施建設費：
				&nbsp;&nbsp;
				<input class="field" type="checkbox" name="cb_originalDeprUnitCB" id="cb_originalDeprUnitCB" size="10" maxlength="10" onclick="checkOriginalDeprUnitCB();checkDeprMethod('');syncDeprData();" <%=("Y".equals(obj.getOriginalDeprUnitCB())?"checked":"")%>>
				<input class="field"  type="hidden"  name="originalDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOriginalDeprUnitCB()%>">
				部門別依比例分攤：
				<input class="field" type="checkbox" name="cb_selfdepr" id="cb_selfdepr" size="10" onclick="checkSelfDepr();" style="display:none" <%=("Y".equals(obj.getSelfdepr())?"checked":"")%>>
				<input class="field"  type="hidden"  name="selfdepr" size="10" maxlength="10" value="<%=obj.getSelfdepr()%>">
				<font id="selfdeprText" style="display:none">
        			個別填寫折舊資料
    			</font>
				<br>
				園區別：
				<select class="field" type="select" name="originalDeprPark" onchange="syncDeprData();">
					<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprPark())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				部門別：
				<select class="field" type="select" id="originalDeprUnit" name="originalDeprUnit" onchange="myQueryDeprUnitData();">
					<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit())%>
				</select>
				<input type="hidden" class="field" name="originalDeprUnittemp" value="">
				&nbsp;&nbsp;&nbsp;
				部門別單位：
				<select class="field" type="select" name="originalDeprUnit1" onchange='syncDeprData();'>
					<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit1())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				會計科目：
				<select class="field" type="select" name="originalDeprAccounts" onchange='syncDeprData();'>
					<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprAccounts())%>
				</select>
				<br>
				殘值：
				<input class="field_RO" type="text" id="originalScrapValue" name="originalScrapValue" size="10" maxlength="10" value="<%=obj.getOriginalScrapValue()%>" onchange="checkDeprMethod('');">
				&nbsp;&nbsp;
				應攤提折舊總額：
				<input class="field_RO" type="text" id="originalDeprAmount" name="originalDeprAmount" size="20" maxlength="15" value="<%=obj.getOriginalDeprAmount()%>" onChange="checkDeprMethod('');">
				<br/>
				以前年度累計折舊：
				<input class="field" type="text" name="originalaccumdepr1" size="20" maxlength="15" value="<%=obj.getOriginalaccumdepr1() %>" onchange="checkDeprMethod('');syncDeprData();">
				本年度累計折舊：
				<input class="field" type="text" name="originalaccumdepr2" size="20" maxlength="15" value="<%=obj.getOriginalaccumdepr2() %>" onchange="checkDeprMethod('');syncDeprData();">
				自行設定累計折舊：
				<%=util.View._getSelectHTML_withFunction("field", "individualSetDepr", obj.getIndividualSetDepr(), "ISD", "chgIndividualSetDepr()") %>
				<br>
				累計折舊
				[<input class="field_RO" type="text" name="originalAccumDepr" size="20" maxlength="15" value="<%=obj.getOriginalAccumDepr()%>">]
				&nbsp;&nbsp;
				攤提壽月
				<input class="field_RO" type="text" name="originalApportionMonth" size="10" maxlength="3" value="<%=obj.getOriginalApportionMonth()%>" onChange="changeArea();checkDeprMethod(this.value);">
				月提折舊金額
				[<input class="field_RO" type="text" name="originalMonthDepr" size="10" value="<%=obj.getOriginalMonthDepr()%>">]
				入帳年月：
				<input class="field" type="text" name="verifyYM" size="7" maxlength="5" value="<%=obj.getVerifyYM() %>" onkeyup = "value=value.replace(/[^\d]/g,'')" onchange="chgVerifyYM();">
				(ex.11101)
				<br>
				月提折舊金額（最後一個月）
				[<input class="field_RO" type="text" name="originalMonthDepr1" size="10" value="<%=obj.getOriginalMonthDepr1()%>">]
				攤提年限截止年月
				[<input class="field_RO" type="text" name="originalApportionEndYM" size="10" value="<%=obj.getOriginalApportionEndYM()%>">]
			</td>
		</tr>
	<tr id="div_depr2">
		<td class="td_form">目前折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法
			<select name="deprMethod" class="field_RO" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
			</select>
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="cb_buildFeeCB" name="cb_buildFeeCB" size="10" maxlength="10" <%=("Y".equals(obj.getBuildFeeCB())?"checked":"")%> >
			<input class="field"  type="hidden"  name="buildFeeCB" size="10" maxlength="10" value="<%=obj.getBuildFeeCB()%>">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="cb_deprUnitCB" name="cb_deprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getDeprUnitCB())?"checked":"")%> >
			<input class="field"  type="hidden"  name="deprUnitCB" size="10" maxlength="10" value="<%=obj.getDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別
			<select class="field_RO" type="select" name="deprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field_RO" type="select" name="deprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別單位：
			<select class="field_RO" type="select" name="deprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprUnit1())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			會計科目
			<select class="field_RO" type="select" name="deprAccounts">
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
<!-- 	問題單1781 隱藏上傳附件的功能	 -->
<!-- 	<tr id="div_pic"> -->
<!-- 	  <td class="td_form">附屬設備檔：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","picture",obj.getPicture())%></td> --%>
<!-- 		</tr> -->
		<tr id="div_escrow">
			<td class="td_form" >代管資產：</td>
			<td class="td_form_white" colspan="3">
				原始總價：
				<input class="field" type="text" name="escrowOriValue" size="20" maxlength="20" value="<%=obj.getEscrowOriValue() %>">	
				&nbsp;&nbsp;&nbsp;&nbsp;			
				代管日前累計折舊：
				<input class="field" type="text" name="escrowOriAccumDepr" size="20" maxlength="20" value="<%=obj.getEscrowOriAccumDepr() %>">
			</td>
		</tr>	
		<tr id="div_security">
			<td class="td_form" >有價證券：</td>
			<td class="td_form_white" colspan="3">
				發行法人名稱：
				<input class="field" type="text" name="securityName" size="20" maxlength="20" value="<%=obj.getSecurityName() %>">				
			</td>
		</tr>	
		<tr id="div_proofDoc">
			<td class="td_form" >權利：</td>
			<td class="td_form_white" colspan="3">
<!--				<font color="red">*</font>憑證字號： 2015/01/21測試不需畢填-->
				憑證字號：
				<input class="field" type="text" name="proofDoc1" size="20" maxlength="20" value="<%=obj.getProofDoc1() %>">				
			</td>
		</tr>
		<tr id="div_manageOrg">
			<td class="td_form">土地、建物：</td>
			<td class="td_form_white" colspan="3">
				管理機關：
				<%=util.View.getPopOrgan("field","manageOrg",obj.getManageOrg(),obj.getManageOrgName(),"N")%> 				
			</td>
		</tr>	
		<tr>
			<td class="td_form">資料狀態：</td>
			<td class="td_form_white" colspan="3">				
				<input type="hidden" class="field" name="dataState" value="<%=obj.getDataState()%>" size="11" maxlength="11">
				<select class="field_RO" type="select" name="dataStateName">
					<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
				</select>
			</td>
		</tr>
		
		
		
		<tr>
		  <td class="td_form">備註：</td>
		  <td class="td_form_white" colspan="3">
		  	<textarea class="field" type="text" name="notes" cols="50" rows="4"><%=obj.getNotes()%></textarea>
		  </td>
		</tr>
		<tr style="display:none">
		  <td class="td_form">異動人員/日期：</td>
		  <td class="td_form_white">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
		    /
		    <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
		</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">		
	<jsp:include page="../../home/toolbar.jsp" />
	
	<span id="span_btn01">
		<input class="toolbar_default" type="button" followPK="true" name="btn01" value="接收撥入資料" onclick="btn01_event();">&nbsp;|
	</span>
	<span id="span_btn02">
		<input class="toolbar_default" type="button" followPK="true" name="btn02" value="Excel格式下載(土地)" onclick="btn02_event();">&nbsp;|
	</span>
	<span id="span_btn04">
		<input class="toolbar_default" type="button" followPK="true" name="btn04" value="Excel格式下載(建物)" onclick="btn04_event();">&nbsp;|
	</span>
	<span id="span_btn05">
		<input class="toolbar_default" type="button" followPK="true" name="btn05" value="Excel格式下載(動產)" onclick="btn05_event();">&nbsp;|
	</span>
	<span id="span_btn03">
		<input class="toolbar_default" type="button" followPK="true" name="btn03" value="Excel匯入" onclick="btn03_event();">&nbsp;|
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
		<%if("true".equals(obj.getIsAddProof())){%>
			<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
		<%} %>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
<!--		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產分號(訖)</a></th>-->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">取得日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">型式廠牌(或土地建物標示)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">總價</a></th>
		<%if("true".equals(obj.getIsAddProof())){%>
		<%}else{ %>
			<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">使用年限</a></th>
		<%} %>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">資料狀態</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	if("true".equals(obj.getIsAddProof())){
		boolean primaryArray[] = {true,true,true,true,false,false,true,true,true,true,false,false,false,false,false,false,false,false};
		boolean displayArray[] = {false,false,false,false,true,true,true,false,true,false,true,true,true,true,false,true,false,false};
		String alignArray[]   = {"","","","","center","center","center","center","center","center","center","center","right","center","center","center","",""};		
		out.write(util.View.getQuerylist2(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	}else{
		boolean primaryArray[] = {false,true,true,true,true,false,false,true,true,true,true,false,false,false,false,false,false,false};
		boolean displayArray[] = {true,false,false,false,false,false,true,true,false,true,false,true,true,true,true,true,true,false};
		String alignArray[]   = {"","","","","center","center","center","center","center","center","center","center","right","center","center","center",""};
		out.write(util.View.getQuerylistWithRowNum(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	}
	
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
			form1.listContainerActiveRowId.value = "";

			if ("<%=checkfundType%>" == ''){	setFormItem("fundType", "R");
			}else{								setFormItem("fundType", "O");
			}

			setFormItem("verifyYM", "R");

			checkDeprMethod('');
			
			$("#div_material").hide();
			$("#div_otherMaterial").hide();
			$("#div_propertyUnit").hide();
			$("#div_otherPropertyUnit").hide();
			$("#div_limitYear").hide();
			
			var movableGroup = ['2', '3', '4', '5'];
			var transferGroup = ['28', '29'];
			//問題單2314 動產的財產來源為接管或撥入時，顯示個別填寫折舊資料
			if (transferGroup.includes(form1.sourceKind.value) && movableGroup.includes(checkStr1)) {
				document.getElementById("selfdeprText").style.display = "";
				document.getElementById("cb_selfdepr").style.display = "";
			} else {
				document.getElementById("selfdeprText").style.display = "none";
				document.getElementById("cb_selfdepr").style.display = "none";
			}
			
			break;				
		case "update":
			initFormDisabled();

			if ("<%=checkfundType%>" == ''){	setFormItem("fundType", "R");
			}else{								setFormItem("fundType", "O");
			}
			
			if (form1.individualSetDepr.value == '') {
				setFormItem("verifyYM", "R");
			}
			
			checkDeprMethod('');
			changePropertyNo(form1.propertyNo.value);
			
			if(form1.progID.value == 'untch001f02extend01'){
				
			}else if(form1.progID.value == 'untch001f02extend02'){
				setFormItem("originalKeepUnit,btn_originalKeepUnit,originalKeeper,btn_originalKeeper,originalUseUnit,btn_originalUseUnit,originalUser,btn_originalUser,originalUserNote,originalMoveDate,btn_originalMoveDate,originalPlace1,btn_originalPlace1,originalPlace,propertyKind,valuable,buyDate,btn_buyDate,originalUnit,originalBV", "R");
			}
			
			
			if (form1.selfdepr.value == "Y") {
				document.getElementById("originalScrapValue").readOnly = false;
				document.getElementById("originalDeprAmount").readOnly = false;
			} else {		
				document.getElementById("originalScrapValue").readOnly = true;
				document.getElementById("originalDeprAmount").readOnly = true;
			}
			
			break;
		case "clear":
			form1.btn01.disabled = false;
			form1.btn02.disabled = false;
			form1.btn03.disabled = false;
			form1.btn04.disabled = false;
			form1.btn05.disabled = false;
			break;
	}
};

</script>
</body>
</html>



