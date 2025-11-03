<!--
程式目的：土地主檔資料維護－基本資料
程式代號：untla002f
程式日期：0941219
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
clive.chang 0941219	Debug & Modify for Testing and autual running..
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*" %>
<jsp:useBean id="user" scope="session" class="util.User"/>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="obj2" scope="request" class="unt.ch.UNTCH001F02">
	<jsp:setProperty name="obj2" property="*"/>
</jsp:useBean>
<!-- 保留第一頁查詢條件與頁數使用 -->
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%

String gis = Common.checkGet(request.getParameter("gis"));
if ("".equals(user.getUserID())){
	if (gis!=null && gis.equals("Y")) {
		user.setUserID("gis");
		//user.setOrganID("GIS0000001");		
	} else {		
		out.println("<script language=\"javascript\">");
		out.println("var prop='';");
		out.println("prop=prop+'scroll:yes;status:no;help:no;';");
		out.println("prop=prop+'dialogWidth:300px;';");
		out.println("prop=prop+'dialogHeight:120px';");
		out.println("window.showModalDialog('../../home/sessionTimeout.jsp',window,prop);");
		out.println("top.top.location.href='../../index.jsp';");
		out.println("</script>");
	}
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA002F)obj.queryOne();
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	obj = (unt.la.UNTLA002F)obj.queryOne();
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
unt.ch.UNTCH001_tab_QUERY uch_QUERY = new unt.ch.UNTCH001_tab_QUERY();
uch.setPropertyNoType(obj.getPropertyNo());
uch_QUERY.setPropertyNoType(obj.getPropertyNo());
String tabs = "";

if("true".equals(obj.getIsAddProof())){
	tabs = uch._createTabData(uch._LA_ADD, 3);
}else if("query".equals(obj.getIsAddProof())){
	uch_QUERY._setupViewType(uch_QUERY._queryOrMaintenance);
	tabs = uch_QUERY._createTabData(uch_QUERY._LA_ADD, 2);
}else if("_query".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._LA_ADD, 2);
}else if("_maintenance".equals(obj.getIsAddProof())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._LA_ADD, 2);
}else if("untch001f02extend01".equals(obj.getProgID())){
	uch._setupViewType(uch._query);
	tabs = uch._createTabData(uch._LA_ADD, 2);
}else if("untch001f02extend02".equals(obj.getProgID())){
	uch._setupViewType(uch._maintenance);
	tabs = uch._createTabData(uch._LA_ADD, 2);
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
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("dataState","1"),
	new Array("valuable","N"),
	new Array("taxCredit","N"),
	new Array("nonProof","Y"),
	new Array("proofVerify","N"),
	new Array("originalHoldNume", "1"),
	new Array("originalHoldDeno", "1"),	
	new Array("holdNume", "1"),
	new Array("holdDeno", "1"),	
	new Array("manageOrg","<%=user.getOrganID()%>"),
	new Array("manageOrgName","<%=user.getOrganName()%>")
	
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[1].checked) {
			form1.action = "untla002f.jsp";
		} else {
			form1.action = "untla001f.jsp";
		}		
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"土地增加單-電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號－類項目節");
		//alertStr += checkEmpty(form1.propertyName1,"財產別名");
		alertStr += checkEmpty(form1.signNo1,"土地標示代碼－縣市");
		alertStr += checkEmpty(form1.signNo2,"土地標示代碼－鄉鎮市區");
		alertStr += checkEmpty(form1.signNo3,"土地標示代碼－地段");
		alertStr += checkEmpty(form1.signNo4,"土地標示代碼－地號(母號)");
		alertStr += checkEmpty(form1.signNo5,"土地標示代碼－地號(子號)");
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.reduceDate,"減損日期");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		alertStr += checkEmpty(form1.taxCredit,"抵繳遺產稅");
		alertStr += checkEmpty(form1.originalArea,"原始總面積(㎡)");
		alertStr += checkFloat(form1.originalArea,"原始總面積(㎡)",7,2);
		alertStr += checkEmpty(form1.originalHoldNume,"原始權利範圍－分子");
		alertStr += checkInt(form1.originalHoldNume,"原始權利範圍－分子");
		alertStr += checkEmpty(form1.originalHoldDeno,"原始權利範圍－分母");
		alertStr += checkInt(form1.originalHoldDeno,"原始權利範圍－分母");
		alertStr += checkFloat(form1.originalHoldArea,"原始權利範圍面積(㎡)",7,2);
		alertStr += checkFloat(form1.area,"整筆面積(㎡)",7,2);
		alertStr += checkInt(form1.holdNume,"權利範圍－分子");
		alertStr += checkInt(form1.holdDeno,"權利範圍－分母");
		alertStr += checkFloat(form1.holdArea,"權利範圍面積(㎡)",7,2);	
		if (!(parseFloat(form1.originalArea.value)>=0)) {
			alertStr += "原始總面積必須大於或等於0\n";
			form1.originalArea.style.backgroundColor = errorbg;
		} else form1.originalArea.style.backgroundColor = "";
		
		if (!(parseInt(form1.originalHoldNume.value)>=0)) {
			alertStr += "原始權利範圍－分子必須大於或等於0\n";
			form1.originalHoldNume.style.backgroundColor = errorbg;
		} else { form1.originalHoldNume.style.backgroundColor = ""; }
		if (!(parseInt(form1.originalHoldDeno.value)>0)) {
			alertStr += "原始權利範圍－分母必須大於或等於0\n";
			form1.originalHoldDeno.style.backgroundColor = errorbg;			
		} else { form1.originalHoldDeno.style.backgroundColor = ""; }
		if (parseInt(form1.originalHoldDeno.value)<parseInt(form1.originalHoldNume.value)) {
			alertStr += "原始權利範圍－分子不可大於分母\n";
			form1.originalHoldNume.style.backgroundColor = errorbg;
			form1.originalHoldDeno.style.backgroundColor = errorbg;
		} else {
			form1.originalHoldNume.style.backgroundColor = "";
			form1.originalHoldDeno.style.backgroundColor = "";
		}		
		alertStr += checkEmpty(form1.originalBasis,"依據");
		alertStr += checkYYYMM(form1.originalDate,"原始入帳－公告年月");
		alertStr += checkEmpty(form1.originalUnit,"原始入帳－單價");
		alertStr += checkFloat(form1.originalUnit,"原始入帳－單價",13,2);
		//alertStr += checkInt(form1.originalUnit,"原始入帳－單價");
		alertStr += checkInt(form1.originalBV,"原始入帳－總價");
		alertStr += checkFloat(form1.bookUnit,"帳面金額－單價",13,2);
		//alertStr += checkInt(form1.bookUnit,"帳面金額－單價");
		alertStr += checkInt(form1.bookValue,"帳面金額－總價");
	
		if (form1.valuable.value=="N") {
			if (parseInt(form1.originalUnit.value)>=0) {
				form1.originalUnit.style.backgroundColor = "";
			} else {
				alertStr += "原始入帳－單價必須大於或等於0\n";
				form1.originalUnit.style.backgroundColor = errorbg;			
			}
		
			if (parseInt(form1.originalBV.value)>=0) {
				form1.originalBV.style.backgroundColor = "";
			} else {
				alertStr += "原始入帳－總價必須大於或等於0\n";
				form1.originalBV.style.backgroundColor = errorbg;
			}
		} else {
			getSelectedValue(form1.valuable, "Y");
			if (parseInt(form1.originalUnit.value)>=0) {
				form1.originalUnit.style.backgroundColor = "";			
			} else {
				alertStr += "原始入帳－單價必須大於或等於0\n";
				form1.originalUnit.style.backgroundColor = errorbg;
			}			
			
			if (parseInt(form1.originalBV.value)>=0) {
				form1.originalBV.style.backgroundColor = "";			
			} else {
				alertStr += "原始入帳－總價必須大於或等於0\n";
				form1.originalBV.style.backgroundColor = errorbg;
			}
		}				
		alertStr += checkDate(form1.sourceDate,"財產取得日期");
		alertStr += checkDate(form1.ownershipDate,"所有權登記日期");
		alertStr += checkEmpty(form1.nonProof,"權狀免繕狀");
		if (parse(form1.landRule.value).length>0) alertStr += checkInt(form1.landRule,"等則");
		alertStr += checkLen(form1.notes, "備註", 250);		

		/*if(form1.propertyKind.value=="01") {
			alertStr += checkEmpty(form1.fundType,"基金財產");
		} else { 
			form1.fundType.options[0].selected=true; 
			form1.fundType.style.backgroundColor = ""; 
		}*/
		
		if(form1.cause.value=="499") {
			alertStr += checkEmpty(form1.cause1,"其他說明");
		} else { 
			form1.cause1.value = ""; 
			form1.cause1.style.backgroundColor = ""; 
		}
		
		if(form1.fundsSource.value == "03") alertStr += checkEmpty(form1.fundsSource1,"其他說明");
		else { form1.fundsSource1.value = ""; form1.fundsSource1.style.backgroundColor = ""; }
		
		//若「權狀」為「Y:有權狀」，「權狀字號」必須有資料
		if(form1.nonProof.value=="Y") alertStr += checkEmpty(form1.proofDoc,"權狀字號");
		else { form1.proofDoc.value =""; form1.proofDoc.style.backgroundColor = ""; }
		
		alertStr += checkLen(form1.ownershipNote, "其他登記事項", 250);
		
		if((form1.useSeparate.value).substr(0,1)=="A"  && form1.useSeparate.value != "AJ")	alertStr += checkEmpty(form1.useKind,"使用地類別");
		else { form1.useKind.value =""; form1.useKind.backgroundColor = ""; }

		alertStr += checkEmpty(form1.sourceKind,"財產來源－種類");
		alertStr += checkEmpty(form1.sourceDate,"財產來源－取得日期");
		alertStr += checkEmpty(form1.ownershipDate,"所有權登記日期");
		alertStr += checkEmpty(form1.ownershipCause,"所有權登記原因");
		alertStr += checkEmpty(form1.useSeparate,"使用分區");

		changeArea();

	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function changeSelect(){
	if (form1.state.value=="insert" || form1.state.value=="insertError" || form1.state.value=="update" || form1.state.value=="updateError") {	
		//當增加原因選「其他」時，開放其他說明欄位	
		if(form1.cause.value == "499") form1.cause1.readOnly = false;
		else { form1.cause1.value=""; form1.cause1.readOnly = true; }
		
		//當經費來源選「其他補助款」時，開放其他說明欄位	
		if(form1.fundsSource.value == "03") form1.fundsSource1.readOnly = false;
		else { form1.fundsSource1.value=""; form1.fundsSource1.readOnly = true; }

		//當權狀選「Y」時，權狀字號欄位必須有資料	
		if(form1.nonProof.value == "N") { form1.proofDoc.value=""; form1.proofDoc.readOnly = true; form1.proofDoc.style.backgroundColor = "";}
		else form1.proofDoc.readOnly = false;
		
		//財產性質為「03:事業用」時，須控制「基金財產」必須有資料	
		//if(form1.propertyKind.value == "03") form1.fundType.disabled = false;
		//else { form1.fundType.options[0].selected=true; form1.fundType.disabled = true; }
		
		//若「財產性質」為「01:公務用」選項時，「基金財產」必須有資料
		if(form1.propertyKind.value == "01")	form1.fundType.disabled = false;
		else { form1.fundType.options[0].selected=true; form1.fundType.disabled = true; }		
		
		//若「使用分區」為「A」開頭時，「使用地類別」必須有資料
		if((form1.useSeparate.value).substr(0,1) == "A" && form1.useSeparate.value != "AJ") form1.useKind.disabled = false;
		else { form1.useKind.disabled = true; form1.useKind.value=""; form1.useKind.style.backgroundColor="";}
	}
	/**
	//當增加原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "499")
		form1.cause1.readOnly = false;
	else form1.cause1.readOnly = true;
	//當權狀免繕狀選「Y」時，開放權狀字號欄位
	if(form1.nonProof.value == "N")
		form1.proofDoc.readOnly = true;
	else form1.proofDoc.readOnly = false;
	//財產性質為「03:事業用」時，須控制「基金財產」必須有資料
	if(form1.propertyKind.value == "03")
		form1.fundType.disabled = false;
	else form1.fundType.disabled = true;		
	//若「使用分區」為「A」開頭時，「編定使用種類」必須有資料
	if((form1.useSeparate.value).substr(0,1) == "A")
		form1.useKind.disabled = false;
	else form1.useKind.disabled = true;			 
	**/
	form1.oriUseSeparate.valeue = form1.useSeparate.value;
	form1.oriUseKind.valeue = form1.useKind.valeue;
}
//更新面積,金額等資料
function changeArea(check){
	var holdArea;		
	form1.originalHoldNume.value = Math.round(form1.originalHoldNume.value);
	form1.originalHoldDeno.value = Math.round(form1.originalHoldDeno.value);	
	holdArea = roundp((form1.originalArea.value * form1.originalHoldNume.value / form1.originalHoldDeno.value),'2',"Y") ;
	
	if(holdArea==null)	holdArea=0;
	if(holdArea=="")	holdArea=0;
	form1.originalHoldArea.value = holdArea;	
	//帳面金額 = 原始入帳金額
	
	if (parse(form1.originalHoldArea.value).length>0 && (check=="originalUnit" || check =="originalArea" || check=="originalHoldNume" || check=="originalHoldDeno")){
		form1.originalUnit.value = Math.round(form1.originalUnit.value*100)/100;
		form1.originalBV.value = Math.round(Math.round((form1.originalUnit.value * form1.originalHoldArea.value)*10)/10);
	}

	//parse(form1.originalUnit.value)>0 &&
	if (parse(form1.originalHoldArea.value).length>0 && check=="addFour"){		
		form1.originalBV.value = Math.round(form1.originalUnit.value * form1.originalHoldArea.value * 1.4);
		form1.originalUnit.value = Math.round((form1.originalBV.value / form1.originalHoldArea.value)*100)/100;
		form1.originalBasis.value = '3';
	}	
	if (parse(form1.originalHoldArea.value).length>0 && check=="originalBV"){
		form1.originalUnit.value = roundp((form1.originalBV.value / form1.originalHoldArea.value),2,"Y");
	}

	if (form1.verify.value=="Y") {} else {	
		form1.area.value = form1.originalArea.value;
		form1.holdNume.value = form1.originalHoldNume.value;
		form1.holdDeno.value = form1.originalHoldDeno.value;
		form1.holdArea.value = holdArea;
		
		form1.bookUnit.value = form1.originalUnit.value;
		form1.bookValue.value = form1.originalBV.value;			
	}		

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
	if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="../ch/untch001f02.jsp"){
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.propertyNo,"財產編號－類項目節");
				alertStr += checkEmpty(form1.serialNo,"財產編號－類項目節");			
			}
			
			if('<%=obj.getIsAddProof()%>' != 'true'){
				form1.state.value="queryAll";
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"財產編號－類項目節");
			alertStr += checkEmpty(form1.serialNo,"財產編號－類項目節");			
		}
		
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

function doSpecialField() {
	var arrField = new Array("otherLimitYear","signNo1","signNo2","signNo3",
		"signNo4","signNo5","cause","cause1","propertyKind","fundType","valuable",
		"taxCredit","originalArea","originalCArea","originalSArea","originalHoldNume","originalHoldDeno",
		"nonProof","proofDoc",
//		"originalBasis",
		"originalDate","btn_originalDate","originalUnit","originalBV","originalNote");

	var arrField01 = new Array("useSeparate","useKind");
	
	if (form1.verify.value=="Y") {
		setFormField(arrField,"R");
		setFormItem("addFour","R");
		if (form1.useSeparate.value!=""){
			setFormField(arrField01,"R");
		}
	}
}
function checkSpecialField() {
	if(form1.dataState.value == "1"){
		var arrField = new Array("cause","cause1","nonProof","proofDoc","sourceKind","sourceDate","sourceDoc");
		if (form1.verify.value=="Y") {
			setFormField(arrField,"O");
		}
	}
}

function init(){
	if (isObj(document.all.item("picture")) && (parse(document.all.item("picture").value)).length>0) setFormItem("btn_pictureDownload","O");
	//欲新增、修改增加單資料須至「土地合併分割作業」進行
	if(form1.mergeDivision.value!=""){
		setFormField(new Array("insert","update"),"R");
	}

	setDisplayItem("spanQueryAll,spanInsert,spanNextInsert","H");
		
	//==========================================
	var caseNo = parse(form1.caseNo.value);
	if ((caseNo.length<=0) || (form1.enterOrg.value!=<%="\""+user.getOrganID()+"\""%>)) {
		setFormItem("insert,update,delete,clear,confirm","R");
	}		
	changeSelect();	
	if (form1.verify.value=="Y")  {
		setFormItem("insert,delete","R");
	}
	if (form1.verify.value=="Y") {
		setFormItem("allInsert","R");
	}
	if (form1.dataState.value=="2")  {
		setFormItem("insert,update,delete","R");
	}
	
	//給GIS系統用的參數，目的是要去除GIS傳過來的QueryString
	if ("<%=gis%>"=="Y") {
		form1.action = "untla002f.jsp";
		//beforeSubmit();
		form1.submit();	
	}
	if (form1.verify.value=="Y") {
		setFormItem("addFour","R");
	}
	
	//登入者所屬機關(SYSAP_Emp.organid)與增加單入帳機關(UNTLA_AddProof.enterOrg)相同,
	//且該增加單尚未入帳時才能按"接收土地撥入"
	/*if("<%=user.getOrganID()%>" == "<%=obj.getEnterOrg()%>" && "<%=obj.getDataState()%>" == "") {
		form1.allInsert.disabled = true;
	} else {
		form1.allInsert.disabled = false;
	}	*/
	
	if(form1.progID.value == 'untch001f02extend01'){
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
	}

	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeepUnitQuickly, form1.originalKeepUnit, form1.originalUseUnitQuickly, form1.originalUseUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.originalKeeperQuickly, form1.originalKeeper, form1.originalUserQuickly, form1.originalUser, true, false);
	var dcc3 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keepUnitQuickly, form1.keepUnit, form1.useUnitQuickly, form1.useUnit, true, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.enterOrg, form1.keeperQuickly, form1.keeper, form1.userNoQuickly, form1.userNo, true, false);
	
	if ('<%=obj.queryVerify()%>' == "Y") {
        setFormItem("update", "R");
    }
}

function run_untla050f(){
	var prop="";
	var windowTop=(document.body.clientHeight-100)/2;
	var windowLeft=(document.body.clientWidth-800)/2;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width=850,height=380,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	var url = "untla050f.jsp?" + "caseNo=" + "<%=obj.getCaseNo()%>" + "&ownership=" + "<%=obj.getOwnership()%>";
	linkuntla050f=window.open(url,'MyWindow',prop);
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
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
<!-- 保留第一頁查詢條件與頁數使用 -->
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	  <tr>
        <td class="td_form" width="15%">入帳機關：</td>
        <td class="td_form_white" colspan="3">
        	<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
        	<input class="field_QRO" type="HIDDEN" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
    		[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">] 　&nbsp;&nbsp;
		權屬： <select class="field_QRO" type="select" name="ownershipName" >
             	<%=util.View.getOnwerOption(obj.getOwnership())%>
			 </select>
	<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
    　&nbsp;&nbsp;電腦單號：[<input class="field_QRO" type="text" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>">] </td>
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
        <td class="td_form_white" colspan="3"><%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"1")%> 　
        分號：[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">] <br>
    別名：<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
    　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 　
	原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">] </td>
	    </tr>
	    
	<tr>
      <td class="td_form"><font color="red">*</font>財產性質：</td>
      <td colspan="3" class="td_form_white"><table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="105"><select class="field" type="select" name="propertyKind" onChange="changeSelect();">
                <%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
            </select></td>
            <td>基金財產：
                <%=util.View._getSelectHTML_withEnterOrg("field", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
            </td>
          </tr>
      </table></td>
	  </tr>
	<tr>
      <td class="td_form"><font color="red">*</font>珍貴財產：</td>
      <td colspan="3" class="td_form_white"><table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="105"><select class="field" type="select" name="valuable">
                <%=util.View.getYNOption(obj.getValuable())%>
            </select></td>
            <td width="114" align="right">抵繳遺產稅：</td>
            <td><select class="field" type="select" name="taxCredit">
                <%=util.View.getYNOption(obj.getTaxCredit())%>
              </select>
            </td>
          </tr>
      </table></td>
	  </tr>
	    
	<tr>
      <td class="td_form">增加原因：</td>
      <td colspan="3" class="td_form_white"><table  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="105"><select class="field" type="select" name="cause"  onChange="changeSelect();">
                <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA'", obj.getCause())%>
            </select></td>
            <td width="114" align="right">其它說明：</td>
            <td><input class="field" type="text" name="cause1" size="12" maxlength="12" value="<%=obj.getCause1()%>" readonly="true"></td>
          </tr>
      </table></td>
	  </tr>
	  <input type="hidden" class="field" name="oriUseSeparate" value="<%=obj.getOriUseSeparate()%>"><!--原始使用分區-->
	<input type="hidden" class="field" name="oriUseKind" value="<%=obj.getOriUseKind()%>"><!--原始使用地類別-->
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>　
			<select class="field" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>　		
			<select class="field" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>　
			地號：		
			<input class="field" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>"> -
			<input class="field" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>">
		</td>	
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
    　<font color="red">*</font>財產取得日期：<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%> 　財產取得文號：
    <input class="field" type="text" name="sourceDoc" size="18" maxlength="20" value="<%=obj.getSourceDoc()%>"></td>
	  </tr>	  		
	<tr>
		<td class="td_form"><font color="red">*</font>原始面積：</td>
		<td class="td_form_white" colspan="3">
			總面積：
			<input name="originalArea" type="text" class="field" value="<%=obj.getOriginalArea()%>" size="12" maxlength="10" onChange="changeArea('originalArea');">			
			<br>
權利分子：<input class="field" type="text" name="originalHoldNume" size="9" maxlength="10" value="<%=obj.getOriginalHoldNume()%>" onChange="changeArea('originalHoldNume');">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;權利分母：<input class="field" type="text" name="originalHoldDeno" size="9" maxlength="10" value="<%=obj.getOriginalHoldDeno()%>" onChange="changeArea('originalHoldDeno');">
&nbsp;&nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="originalHoldArea" size="9" maxlength="10" value="<%=obj.getOriginalHoldArea()%>">]</td>
	</tr>
	  
	<tr>
		<td class="td_form">原始入帳：</td>
		<td class="td_form_white" colspan="3">
			依據：
			<select class="field" type="select" name="originalBasis" onchange="if(this.value=='3'){form1.originalDate.value='';}">
				<%=util.View.getTextOption("1;公告地價;2;公告現值;3;取得價格",obj.getOriginalBasis())%>							
			</select> 公告年月：<input class="field" type="text" name="originalDate" size="5" maxlength="5" value="<%=obj.getOriginalDate()%>"> <input class="field" type="button" name="btn_originalDate" onclick="if (parseInt(form1.originalBasis.value)<3) popBulletinDate('originalDate',form1.originalBasis.value,'Y'); else alert('依公告地價或是公告現值時,方需輸入公告年月');" value="..." title="公告年月輔助視窗">&nbsp;&nbsp;&nbsp;
			<input class="toolbar_default" type="button" name="addFour" value="現值加四成" onClick="changeArea('addFour');">
			<br>		
			單價：
			<input class="field" type="text" name="originalUnit" size="13" maxlength="16" value="<%=obj.getOriginalUnit()%>" onChange="changeArea('originalUnit');">
			<font color="red">*</font>總價：<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>" onChange="changeArea('originalBV');">
			<!-- 總價：[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">] -->
			摘要：<input class="field" type="text" name="originalNote" size="20" maxlength="20" value="<%=obj.getOriginalNote()%>">
			</td>	
	</tr>
	  
	<tr>
		<td class="td_form">目前面積：</td>
		<td class="td_form_white" colspan="3">
			總面積：[<input class="field_RO" type="text" name="area" size="12" maxlength="10" value="<%=obj.getArea()%>" onChange="changeArea();">] <br>
權利分子：[<input class="field_RO" type="text" name="holdNume" size="9" maxlength="10" value="<%=obj.getHoldNume()%>" onChange="changeArea();">] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
權利分母：[<input class="field_RO" type="text" name="holdDeno" size="9" maxlength="10" value="<%=obj.getHoldDeno()%>" onChange="changeArea();" >] 
權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="10" value="<%=obj.getHoldArea()%>" onChange="changeArea();">]</td>
	</tr>
	<tr>
		<td class="td_form">原值：</td>
		<td colspan="3" class="td_form_white">
			單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="16" value="<%=obj.getBookUnit()%>">]	　
			  總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]		</td>
		</tr>		
	<tr>
		<td class="td_form">帳面價值(淨值)：</td>
		<td colspan="3" class="td_form_white">
			單價：[<input class="field_RO" type="text" name="netUnit" size="13" maxlength="16" value="<%=obj.getNetUnit()%>">]	　
			  總價：[<input class="field_RO" type="text" name="netValue" size="15" maxlength="15" value="<%=obj.getNetValue()%>">]		
		</td>
	</tr>
	
	
	    
	    
	
	<tr>
	  <td class="td_form">街路名：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="doorplate" size="50" maxlength="50" value="<%=obj.getDoorplate()%>">　
		</td>
	  </tr>
	<tr>
      <td class="td_form">經費來源：</td>
      <td colspan="3" class="td_form_white">
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
	<tr>
	  <td class="td_form">原有人：</td>
	  <td class="td_form_white"><input class="field" type="text" name="oldOwner" size="30" maxlength="30" value="<%=obj.getOldOwner()%>">
      </td>
      <td class="td_form">土地現況：</td>
      <td class="td_form_white">
      	<select class="field" type="select" name="useState">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UST' ", obj.getUseState())%>
		</select>
      </td>
	</tr>
	<tr>
      <td class="td_form">管理機關：</td>
      <td class="td_form_white"><%=util.View.getPopOrgan("field","manageOrg",obj.getManageOrg(),obj.getManageOrgName(),"Y")%> </td>
      <td class="td_form">地上物情形：</td>
      <td class="td_form_white">
      	<select class="field" type="select" name="useState1">
			<%=util.View.getTextOption("01;01空置;02;02建物;03;03農作;04;04其他", obj.getUseState1())%>
		</select>
      </td>
	</tr>      
	<tr>
      <td class="td_form"><font color="red">*</font>權狀資料：</td>
      <td colspan="3" class="td_form_white">
			所有權登記日期：
			<%=util.View.getPopCalndar("field","ownershipDate",obj.getOwnershipDate())%> 　
			所有權登記原因：
			<select class="field" type="select" name="ownershipCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='OCA' ", obj.getOwnershipCause())%>
			</select>
          	<br>
          	權狀：
          	<select class="field" type="select" name="nonProof" onChange="changeSelect();">
				<%=util.View.getTextOption("Y;有;N;無",obj.getNonProof())%>
          	</select>
    　			權狀字號：
    		<input class="field" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">
    		<br>
    		其他登記事項：
    		<textarea name="ownershipNote" cols="58" rows="1" class="field"><%=obj.getOwnershipNote()%></textarea>
      </td>
	  </tr>	
	<tr>
	  <td class="td_form">使用分區：</td>
	  <td colspan="3" class="td_form_white"><select class="field" type="select" name="useSeparate" onChange="changeSelect();">
              <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
            </select>　使用地類別：<select class="field" type="select" name="useKind" onChange="changeSelect();">
              <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getUseKind())%>
            </select></td>
	  </tr>
	<tr>
	  <td class="td_form">地目：</td>
	  <td colspan="3" class="td_form_white"><select class="field" type="select" name="field">
            <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
          </select>　等則：<input class="field" type="text" name="landRule" size="2" maxlength="2" value="<%=obj.getLandRule()%>"></td>
		</tr>
	<tr>
		<td class="td_form">原始移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalKeepUnit", "form1.originalUseUnit.value = this.value; form1.originalUseUnit.onchange();", 
			                                                       "originalKeepUnitQuickly", "", obj.getOriginalKeepUnit()) %>
			<input class="field_Q" type="button" name="btn_originalKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'originalKeepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalKeeper", "form1.originalUser.value = this.value;form1.originalUser.onchange();",
			                                                       "originalKeeperQuickly", "", obj.getOriginalKeeper()) %>
	        <input class="field_Q" type="button" name="btn_originalKeeper" onclick="popUnitMan(form1.enterOrg.value,'originalKeeper')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field", "form1", "originalUseUnit", "originalUseUnitQuickly", obj.getOriginalUseUnit()) %>
			<input class="field_Q" type="button" name="btn_originalUseUnit" onclick="popUnitNo(form1.enterOrg.value,'originalUseUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "originalUser", "originalUserQuickly", obj.getOriginalUser()) %>
	        <input class="field_Q" type="button" name="btn_originalUser" onclick="popUnitMan(form1.enterOrg.value,'originalUser')" value="..." title="人員輔助視窗">
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
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", 
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
<!-- 	問題單1781 隱藏上傳附件的功能 -->
<!-- 	<tr> -->
<!-- 	 <td class="td_form">附屬設備檔：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","picture",obj.getPicture())%></td> --%>
<!--       </tr> -->
	<tr>
      <td class="td_form">資產重估日：</td>
      <td colspan="3" class="td_form_white">[<input class="field_RO" type="text" name="appraiseDate" size="9" maxlength="7" value="<%=obj.getAppraiseDate()%>">] </td>
	  </tr>
	<tr>
	  <td class="td_form">其他事項：</td>
	  <td colspan="3" class="td_form_white"><input class="field" type="text" name="notes1" size="50" maxlength="60" value="<%=obj.getNotes1()%>"></td>
	  </tr>
	<tr>
      <td class="td_form">減損資料：</td>
      <td colspan="3" class="td_form_white"> 
			減損日期：
			[<input class="field_RO" type="text" name="reduceDate" size="15" maxlength="7" value="<%=obj.getReduceDate()%>">] <br>
			減損原因：
    		<select class="field_RO" type="select" name="reduceCause">
      			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' ", obj.getReduceCause())%>
    		</select>
    　 			其他說明：
    		[<input class="field_RO" type="text" name="reduceCause1" size="20" maxlength="20" value="<%=obj.getReduceCause1()%>">] </td>
	  </tr>
	<tr>
      <td class="td_form">備註：</td>
      <td class="td_form_white"colspan="3"><textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
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
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增加原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">持分面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">原值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">帳面價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,false,true,true,true,true,true,true,true,true};
	String	alignString[]  = {"","","","","","","","","","","","right","right"};
	out.write(util.View.getQuerylist(primaryArray,displayArray,alignString,objList,obj.getQueryAllFlag()));
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
			if (!confirm("刪除此增加單，將一併刪除其關聯的資料：\n\n　01. 土地基本資料。\n　02. 管理資料。\n　03. 地上物。\n　04. 公告地價。\n　05. 公告現值。\n　06. 他項權利。\n　07. 土地使用權。　\n\n您確定要刪除?"))
			return false;			
			hasBeenConfirmed = true;        
			break;			
		case "queryAll":
			if (form1.querySelect[0].checked==true || form1.querySelect[1].checked==true) {} 
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
			//將土地或建物標示的縣市欄位設為高雄市, 不過記得要在insertDefault裡加入一個 new Array("signNo1", "E000000")
			changeSignNo1('signNo1','signNo2','signNo3','E000000');
						
			break;
			
		case "insertError":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType',form1.fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');				
			changeSelect();
			break;				
			
		//更新時要做的動作
		case "update":	
			break;
		case "updateError":			
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType',form1.fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');		
			doSpecialField();
			changeSelect();
			checkSpecialField();			
			break;			
		case "clear":
			init();
			break;			
	}
}
</script>
</body>
</html>



