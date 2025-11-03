<!--
程式目的：財產查詢
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH001F02_1">
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
obj.setUserID(user.getUserID());
obj.setOrganID(user.getOrganID());

String checkVerify = obj.queryVerify();
String checkfundType = "";

//Excel下載暫定使用 web.xml 中filestoreLocation的設定位置
String downloadFilePath = application.getInitParameter("filestoreLocation") + "/untch001f08_Sample.xls";

if("120".equals(obj.getDifferenceKind())){
	checkfundType = uctls.getCountForFundType(obj.getEnterOrg());	
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){
		obj.setQueryAllFlag("true"); 
	}
	
}else if ("queryOne".equals(obj.getState())) {	
	obj = obj._execQueryOneforDetail();	
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.execQueryAll();

	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
		
		}else{
			if("".equals(obj.getSerialNo()) && "".equals(obj.getLotNo())){
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
			}	
			
			obj._execQueryOneforDetail();
		}
	}

}

String checkDeprType = "";
if("110".equals(obj.getDifferenceKind())){			checkDeprType = "officialDeprMethod";
}else if("111".equals(obj.getDifferenceKind())){	checkDeprType = "escrowDeprMethod";
}else if("120".equals(obj.getDifferenceKind())){	checkDeprType = "fundDeprMethod";
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
uch.setPropertyNoType(obj.getPropertyNo());
String tabs = "";

uch._setupViewType(uch._query);
obj.setIsAddProof(uch._query);
tabs = uch._createTabData(uch._CH_ADD, 1);
obj.setQuerySelect("detail");

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
<script type="text/javascript" src="../../js/function.js" ></script>
<script type="text/javascript" src="../../js/tablesoft.js" ></script>
<script type="text/javascript" src="../../js/sList2.js" ></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
	var insertDefault;	//二維陣列, 新增時, 設定預設值
	var checkVerify = '<%=checkVerify%>';

	insertDefault = new Array();

	function checkField(){
		var alertStr="";
			
		if (form1.state.value=="queryAll") {
			alertStr += checkQuery();
		} else if (form1.state.value=="insert" || form1.state.value=="update") {	
			alertStr = '財產查詢暫不開放新增或修改功能';
		}
		if (alertStr.length!=0) { 
			alert(alertStr); 
			return false; 
		}
		
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
		form1.serialNoE.value = serialNoE;
		form1.lotNo.value = lotNo;	
		form1.state.value="queryOne";
		beforeSubmit();
		form1.submit();		
	}

	function checkURL(surl){
		columnTrim(form1.caseNo);
		if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
			alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	//	}else if(surl!='../ch/untch001f01.jsp' && form1.propertyNo.value==""){
	//		alert("請先執行查詢!");
		}else{
			form1.state.value="queryAll";
			form1.mainPage.value=form1.currentPage.value;
			form1.mainEnterOrg.value=form1.queryone_enterOrg.value;
			form1.mainOwnerShip.value=form1.queryone_ownership.value;
			form1.mainCaseNo.value=form1.queryone_caseNo.value;
			form1.mainDifferenceKind.value=form1.queryone_differenceKind.value;		
			form1.currentPage.value=1;	
			form1.action = surl;
			beforeSubmit();
			form1.submit();		
		}
	}

	function init(){
		// TODO
		initQ_Form('<%=obj.getQuerySelect()%>');
		
		if (isObj(document.all.item("picture")) && (parse(document.all.item("picture").value)).length>0) setFormItem("btn_pictureDownload","O");	
		if(form1.propertyNo.value != ''){	changePropertyNo(form1.propertyNo.value);	
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
		
		setFormItem("btn01,btn02,btn03","O");
		
		if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
			setFormField(new Array("insert,update","delete,btn01,btn03"),"R");
		}	
		if (checkVerify == "Y") {
			setFormItem("insert,update,delete,btn01,btn03", "R");
		}	
		
		
		setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert","H");
		
		
		autoListContainerRowClick();
	}

	function autoListContainerRowClick() {
		if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
				&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
			var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value + form1.differenceKind.value + form1.propertyNo.value + form1.serialNo.value + form1.serialNo.value + form1.lotNo.value;
			listContainerRowClick(key);
		}
	}

	/**
	 * 遮蔽所有資料區塊
	 */
	function initFormDisabled(){
		$("#div_spec").hide();
		$("#div_licensePlate").hide();	
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
		$("#div_accDetail").hide();
	}

	/**
	 * 根據財產大類顯示區塊
	 */
	function changePropertyNo(propertyNo){
		initFormDisabled();
		
		var checkStr1 = form1.propertyNo.value.substring(0,1);
		var checkStr3 = form1.propertyNo.value.substring(0,3);
		
		setFormItem("originalAmount","O");
		//setFormItem("originalUnit","O");
		form1.originalUnit.disabled= false;
		
		$("#div_escrow").show();
		if("111" == checkStr3){ //RF
			$("#div_rfArea").show();
			$("#div_amount").show();
			$("#div_move").show();
			$("#div_depr").show();
			$("#div_depr2").show();
			$("#div_pic").show();
			if(form1.originalAmount.value == ""){   // 其實土改不需要這個欄位 但不知道是誰堅持要在畫面上顯示1 然後唯讀
				form1.originalAmount.value = "1";
			}
			setFormItem("originalAmount","R");
			//setFormItem("originalUnit","R");
			form1.originalUnit.disabled= true;
			$("#div_material").show();
			$("#div_propertyUnit").show();
			
			$("#div_limitYear").show();	
			$("#div_accDetail").show();
			
		}else if("1" == checkStr1){ //LA
			$("#div_laSignNo").show();
			$("#div_laArea").show();
			$("#div_laArea_start").show();
			$("#div_amount").show();
			$("#div_move").show();
			$("#div_manageOrg").show();
			$("#div_pic").show();
			setFormItem("originalAmount","R");
			$("#div_material").show();
			$("#div_propertyUnit").show();
			$("#div_limitYear").show();
			
		}else if("2" == checkStr1){ //BU
			$("#div_buSignNo").show();
			$("#div_door").show();
			$("#div_buArea").show();
			$("#div_amount").show();
			$("#div_move").show();
			$("#div_depr").show();
			$("#div_depr2").show();
			$("#div_manageOrg").show();
			$("#div_pic").show();
			setFormItem("originalAmount","R");
			//setFormItem("originalUnit","R");
			form1.originalUnit.disabled= true;
			$("#div_material").show();
			$("#div_propertyUnit").show();
			
			$("#div_limitYear").show();	
			$("#div_accDetail").show();
		}else if("3" == checkStr1 || "4" == checkStr1 || "5" == checkStr1){ //MP
			$("#div_spec").show();
			$("#div_licensePlate").show();
			$("#div_amount").show();
			$("#div_depr").show();
			$("#div_depr2").show();
			$("#div_pic").show();
			$("#div_move").show();
			//問題單1315
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
			$("#div_limitYear").show();	
			$("#div_accDetail").show();
		}else if("8" == checkStr1){ //RT
			$("#div_proofDoc").show();
			$("#div_amount").show();	
	
			form1.originalUnit.disabled= true;
			setFormItem("originalAmount","R");
			
			$("#div_material").show();
			$("#div_propertyUnit").show();
			$("#div_limitYear").show();
			// 問題單1010  公務類折舊新增
			$("#div_depr").show();
			$("#div_depr2").show();
			$("#div_accDetail").show();
		}else if("9" == checkStr1){ //VP
			$("#div_security").show();
			$("#div_material").show();
			$("#div_propertyUnit").show();
			$("#div_limitYear").show();
			// 客戶758 要求股票要可以登打保管資料
			$("#div_move").show();
			
			// 問題單1691 有價證券顯示現帳
            $("#div_accDetail").show();
            $("#dtlNetValueRO").hide(); //有價證券無淨值
			
			
			//setFormItem("originalUnit","R");
			form1.originalUnit.disabled= true;
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

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">	
<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
<input type="hidden" name="userOrganID" value="<%=user.getOrganID()%>">
<input type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>"><!--存置地點使用  -->


<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<table class="queryTable"  border="1">
	<% request.setAttribute("UNTCH001Q",obj);%>
	<jsp:include page="untch001q02_1.jsp" />
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
				[<input class="field_QRO" type="text" name="enterOrgName" size="40" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
				&nbsp;&nbsp;&nbsp;
				權屬：
				<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
				<select class="field_QRO" type="select" name="ownershipName">
					<%=util.View.getOnwerOption(obj.getOwnership())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				<input class="field_QRO" type="hidden" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>">
				
				<div style="display:none">
					<input type="text" class="field" name="proofNo" value="<%=obj.getProofNo()%>">
				</div>
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
				<%=uctls.getPopProperty("field_PRO","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"&isLookup=Y")%>
				分號：
				[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]				
				<input class="field_PRO" type="hidden" name="serialNoS" size="7" maxlength="7" value="<%=obj.getSerialNoS()%>">
				&nbsp;&nbsp;&nbsp;
				<input class="field_PRO" type="hidden" name="serialNoE" size="7" maxlength="7" value="<%=obj.getSerialNoE()%>">
				<input class="field_PRO" type="hidden" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">
				<br>
				<font color="red">*</font>購置日期
				<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>" >
				&nbsp;&nbsp;&nbsp;
				入帳日期
				<input class="field_RO" type="text" name=enterDate size="7" maxlength="7" value="<%=obj.getEnterDate()%>" >
				&nbsp;&nbsp;&nbsp;
				財產別名
				<input class="field_RO" type="text"  name="propertyName1" value="<%=obj.getPropertyName1() %>" size="30" maxlength="30">
				<br>
				原財產編號：
				[<input class="field_PRO" type="text" name="oldPropertyNo" size="11" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]
				&nbsp;&nbsp;&nbsp;
				原財產分號：
				[<input class="field_PRO" type="text" name="oldSerialNo" size="20" maxlength="20" value="<%=obj.getOldSerialNo()%>">]
			</td>
		</tr>
		<tr>
		  <td class="td_form"><font color="red">*</font>財產來源：</td>
		  <td colspan="3" class="td_form_white">
		  	<%=uctls.getSourceKind("field_RO","sourceKind",obj.getSourceKind(),obj.getSourceKindName())%>
		  	<font color="red">*</font>
		  	財產取得日期：<%=util.View.getPopCalndar("field_RO","sourceDate",obj.getSourceDate())%> 　
			文號：<input class="field_RO" type="text" name="sourceDoc" size="25" maxlength="40" value="<%=obj.getSourceDoc()%>">
		  </td>	
		</tr>
		
		<tr style="display:none">
	      <td class="td_form">增加原因：</td>
	      <td class="td_form_white" colspan="3">
	      	<%=uctls.getCause("field_RO","cause",obj.getCause(),obj.getCauseName(),"1,4")%>
	      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          	其它說明：
			<input class="field_RO" type="text" name="cause1" size="12" maxlength="12" value="<%=obj.getCause1() %>" >	      		
	      </td>
		</tr>
		
		<tr>
			<td class="td_form"><font color="red">*</font>財產性質：</td>
			<td colspan="3" class="td_form_white">
				<%=util.View._getSelectHTML("field_RO", "propertyKind", obj.getPropertyKind(),"PKD") %>
				&nbsp;&nbsp;&nbsp;
				基金財產
				<%=util.View._getSelectHTML_withEnterOrg("field_RO", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>珍貴財產：
				<select class="field_RO" type="select" name="valuable">
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
					[<input class="field_RO" type="text" name="otherMaterial" size="25" maxlength="25" value="<%=obj.getOtherMaterial()%>">]
					<br/>
				</span>
				單位：
				<span id="div_propertyUnit">
					[<input class="field_RO" type="text" name="propertyUnit" size="8" maxlength="25" value="<%=obj.getPropertyUnit()%>">]
				</span>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span id="div_otherPropertyUnit">
					其他單位：
					[<input class="field_RO" type="text" name="otherPropertyUnit" size="8" maxlength="25" value="<%=obj.getOtherPropertyUnit() %>">]
					<br/>
				</span>
				原使用年限：
				<span id="div_limitYear">					
	        		[<input class="field_RO" type="text" name="originalLimitYear" size="3" maxlength="3" value="<%=obj.getOriginalLimitYear()%>">]
	        		&nbsp;&nbsp;&nbsp;&nbsp;
	        		使用年限(月)：
	        		[<input class="field_RO" type="text" name="otherLimitYear" size="5" maxlength="5" value="<%=obj.getOtherLimitYear()%>">]
	        	</span>
			</td>			
		</tr>
		<tr id="div_spec">
			<td class="td_form" >廠牌型式：</td>
			<td class="td_form_white" colspan="3">
				型式
				<input class="field_RO" type="text" id="specification" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification() %>">
				&nbsp;&nbsp;&nbsp;&nbsp;
				廠牌
				<input class="field_RO" type="text" id="nameplate" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate() %>">
				<br>
				廠商：
				<input class="field_RO" type="hidden" name="storeNo" size="5" maxlength="10" value="<%=obj.getStoreNo()%>">
				[<input class="field_RO" type="text" name="storeNoName" size="20" maxlength="50" value="<%=obj.getStoreNoName()%>">]
				&nbsp;&nbsp;&nbsp;	
				品名：
				<input class="field_RO"  type="text" name="articleName"  value="<%=obj.getArticleName()%>" size="20" maxlength="10">
			</td>
		</tr>		
			
		<tr id="div_licensePlate">
			<td class="td_form">牌照號碼規格<br>/序號規格：</td>
			<td class="td_form_white" colspan="3">
				<input class="field_RO" type="text" name="licensePlate" size="30" maxlength="30" value="<%=obj.getLicensePlate()%>">
				&nbsp;&nbsp;　　　　　
				用途及其他：
				<input class="field_RO" type="text" name="purpose" size="40" maxlength="100" value="<%=obj.getPurpose()%>">
			</td>
		</tr>
		<tr id="div_laSignNo">
			<td class="td_form">土地標示：</td>
			<td class="td_form_white">
				<select class="field_RO" type="select" name="laSignNo1" >
					<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getLaSignNo1())%>
				</select>　
				<select class="field_RO" type="select" name="laSignNo2" >
					<% // TODO  啥小? %>
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
				<select class="field_RO" type="select" name="buSignNo1" onChange="changeSignNo1('buSignNo1','buSignNo2','buSignNo3','');refreshTime();">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno", obj.getBuSignNo1())%>
				</select>　
				<select class="field_RO" type="select" name="buSignNo2" onChange="changeSignNo2('buSignNo1','buSignNo2','buSignNo3','');refreshTime();">
					
				</select>　		
				<select class="field_RO" type="select" name="buSignNo3">
					
				</select>　
			</td>
			<td class="td_form_white" colspan="2">
				建號：		
				<input class="field_RO" type="text" name="buSignNo4" size="5" maxlength="5" value="<%=obj.getBuSignNo4() %>" > - 
				<input class="field_RO" type="text" name="buSignNo5" size="3" maxlength="3" value="<%=obj.getBuSignNo5() %>" >
			</td>	
		</tr>
		<tr id="div_door">
			<td class="queryTDLable">門牌：</td>		
			<td class="queryTDInput" colspan="3">
				<select class="field_RO" type="select" name="doorPlate1" >
					<%=util.View.getOption("select signNo ,signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getDoorPlate1())%>
				</select>
				<select class="field_RO" type="select" name="doorPlate2" >
					
				</select>
				<span style="display:none"><select class="field_RO" type="select" name="doorPlate3"></select></span>
				
	        	<input class="field_RO" type="text" name="doorPlatevillage1"   size="5" maxlength="5" value="<%=obj.getDoorPlatevillage1() %>" />
				<select class="field_RO" type="select" name="doorPlatevillage2" onchange="" onblur="setDoorplateAll()">
	          		<%=util.View.getTextOption("1;村;2;里;",obj.getDoorPlatevillage2())%>
	        	</select>
				<input class="field_RO" type="text" name="doorplateRd1"   size="15" maxlength="20" value="<%=obj.getDoorplateRd1() %>" />
				<select class="field_RO" type="select" name="doorplateRd2" >
	          		<%=util.View.getTextOption("1;路;2;街;3;大道",obj.getDoorplateRd2())%>
	        	</select>
				&nbsp;
				<input class="field_RO" type="text" name="doorplateSec"   size="3"  maxlength="4"  value="<%=obj.getDoorplateSec() %>"   />段
			  	<input class="field_RO" type="text" name="doorplateLn"    size="2"  maxlength="3"  value="<%=obj.getDoorplateLn() %>"     />巷
			  	<input class="field_RO" type="text" name="doorplateAly"   size="2"  maxlength="3"  value="<%=obj.getDoorplateAly() %>"    />弄
			  	<input class="field_RO" type="text" name="doorplateNo"   size="2"  maxlength="3"  value="<%=obj.getDoorplateNo() %>"     />號
			  	<input class="field_RO" type="text" name="doorplateFloor1" size="2"  maxlength="3"  value="<%=obj.getDoorplateFloor1() %>" />樓
			  	<input class="field_RO" type="text" name="doorplateFloor2" size="15"  maxlength="25"  value="<%=obj.getDoorplateFloor2() %>"  /><br>
			  	總稱：
			  	[<input class="field_RO" type="text" name="doorPlate4" size="50"  maxlength="50"  value="<%=obj.getDoorPlate4() %>" />]		  			
			</td>
		</tr>    
		 	
		<tr id="div_laArea">
			<td class="td_form">土地面積：</td>
			<td class="td_form_white" colspan="3">
				<font id="div_laArea_start" color="red">*</font>
				總面積：
				<input class="field_RO"  type="text" name="areaLa"  value="<%=obj.getAreaLa() %>" size="12" maxlength="10" >			
				<br>
				權利分子：
				<input class="field_RO" type="text" name="holdNumeLa" size="9" maxlength="10" value="<%=obj.getHoldNumeLa() %>" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				權利分母：
				<input class="field_RO" type="text" name="holdDenoLa" size="9" maxlength="10" value="<%=obj.getHoldDenoLa() %>" >
				&nbsp;&nbsp;
				權利範圍面積(㎡)：
				[<input class="field_RO" type="text" name="holdAreaLa" size="9" maxlength="10" value="<%=obj.getHoldAreaLa() %>">]
			</td>
		</tr>
		<tr id="div_buArea">
			<td class="td_form">建物面積：</td>
			<td colspan="3" class="td_form_white">
				<font color="red">*</font>主面積(㎡)：
				<input class="field_RO" type="text" name="cArea" size="9" maxlength="10" value="<%=obj.getCArea() %>" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				附屬面積：
				<input class="field_RO" type="text" name="sArea" size="9" maxlength="10" value="<%=obj.getSArea() %>" >
	　			&nbsp;
				總面積：
				[<input class="field_RO" type="text" name="areaBu" size="12" value="<%=obj.getAreaBu() %>">]<br>
				權利分子：
				<input class="field_RO" type="text" name="holdNumeBu" size="9" maxlength="10" value="<%=obj.getHoldNumeBu() %>" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				權利分母：
				<input class="field_RO" type="text" name="holdDenoBu" size="9" maxlength="10" value="<%=obj.getHoldDenoBu() %>">
				&nbsp;&nbsp;
				權利範圍面積(㎡)：
				[<input class="field_RO" type="text" name="holdAreaBu" size="9" maxlength="10" value="<%=obj.getHoldAreaBu() %>">]
			</td>
		</tr>
		<tr>
			<td class="td_form">經費來源：</td>
			<td class="td_form_white" colspan="3"> 
				<table  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<select class="field_RO" type="select" name="fundsSource" >
								<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							其它說明：
							<input class="field_RO" type="text" name="fundsSource1" size="12" maxlength="12" value="<%=obj.getFundsSource1()%>" >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							會計科目：
							<input class="field_RO" type="text" name="accountingTitle" size="10" maxlength="20" value="<%=obj.getAccountingTitle()%>">
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr id="div_rfArea">
			<td class="td_form">土地改良物：</td>
	  	    <td colspan="3" class="td_form_white">
	  	    	<font color="red">*</font>原始計量數：
	      		<input class="field_RO"  type="text"  name="originalMeasure" value="<%=obj.getOriginalMeasure() %>" size="15" maxlength="15">
	      		&nbsp;&nbsp;&nbsp;
	      		<input class="field_RO" type="hidden" name="propertyUnitRf" size="10" maxlength="10" value="<%=obj.getPropertyUnitRf() %>">
			</td>
		</tr>
		<tr id="div_amount">
			<td class="td_form">原始數量價值：</td>
			<td class="td_form_white" colspan="3">
				<font color="red">*</font>數量(股數)：
				<input class="field_RO" type="text" name="originalAmount" size="10" maxlength="10" value="<%=obj.getOriginalAmount() %>">
				&nbsp;&nbsp;&nbsp;
				單價：
				<input class="field_RO" type="text" name="originalUnit" size="10" maxlength="10" value="<%=obj.getOriginalUnit() %>" >
				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>總價：
				<input class="field_RO" type="text" name="originalBV" size="10" maxlength="10" value="<%=obj.getOriginalBV() %>" >
				<br>
				會計科目：
				<input class="field_RO" type="text"  name="accountingTitle" value="<%=obj.getAccountingTitle() %>" size="20">
				&nbsp;&nbsp;&nbsp;
				帳務摘要：
				<input class="field_RO" type="text" name="originalNote" value="<%=obj.getOriginalNote() %>" size="20">
			</td>
		</tr>	
		<tr id="div_move">
			<td class="td_form">保管使用資料：</td>
			<td class="td_form_white" colspan="3">
				保管單位：
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno", 
				                                                       "field_RO", "form1", "keepUnit", "form1.useUnit.value = this.value;form1.useUnit.onchange();queryforDeprUnit();", 
				                                                       "keepUnitQuickly", "", obj.getKeepUnit()) %>
				&nbsp;&nbsp;&nbsp;保管人：
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
				                                                       "field_RO", "form1", "keeper", "form1.userno.value = this.value;form1.userno.onchange();", 
				                                                       "keeperQuickly", "", obj.getKeeper()) %>
				<br>
				使用單位：
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                         "field_RO", "form1", "useUnit", "useUnitQuickly", obj.getUseUnit()) %>
				&nbsp;&nbsp;&nbsp;使用人：
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                         "field_RO", "form1", "userno", "usernoQuickly", obj.getUserNo()) %>
		        <br/>
				使用註記：
				<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20" maxlength="100">		        
		        <br>
		        <div style="display:none">
				<%=util.View.getPopCalndar("field_RO","moveDate",obj.getMoveDate())%>
				</div>
				存置地點：
				<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>" >
				[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
				&nbsp;&nbsp;&nbsp;		
				存置地點說明：
				<input class="field_RO" type="text" name="place" size="45" maxlength="45" value="<%=obj.getPlace() %>">
			</td>
		</tr>
		<tr id='div_accDetail'>
			<td class='td_form'>帳面資料：</td>
			<td class='td_form_white' colspan='3'>
				原始入帳數量：
				[<input class="field_PRO" type="text" name="dtlOriginalAmountRO" size="11" maxlength="11" value="<%=obj.getDtlOriginalAmountRO()%>">]
				&nbsp;&nbsp;&nbsp;
				原始入帳價值：
				[<input class="field_PRO" type="text" name="dtlOriginalBVRO" size="11" maxlength="11" value="<%=obj.getDtlOriginalBVRO()%>">]
				<br>
				數量：
				[<input class="field_PRO" type="text" name="dtlBookAmountRO" size="11" maxlength="11" value="<%=obj.getDtlBookAmountRO()%>">]
				&nbsp;&nbsp;&nbsp;
				原值：
				[<input class="field_PRO" type="text" name="dtlBookValueRO" size="11" maxlength="11" value="<%=obj.getDtlBookValueRO()%>">]
				&nbsp;&nbsp;&nbsp;
				<span id="dtlNetValueRO" >
				帳面價值(淨值)：
				[<input class="field_PRO" type="text" name="dtlNetValueRO" size="11" maxlength="11" value="<%=obj.getDtlNetValueRO()%>">]
				</span>
			</td>
		</tr>
		<tr id="div_depr">
			<td class="td_form">原始折舊資料：</td>
			<td class="td_form_white" colspan="3">
				<font color="red">*</font>折舊方法：
				<%=util.View._getSelectHTML_withFunction("field_RO", "originalDeprMethod", obj.getOriginalDeprMethod(),"DEP", "") %>
				&nbsp;&nbsp;
				<input class="field_RO" type="checkbox" name="cb_originalBuildFeeCB" size="10" maxlength="10"  <%=("Y".equals(obj.getOriginalBuildFeeCB())? "checked" : "")%>>
				<input class="field_RO" type="hidden" name="originalBuildFeeCB" size="10" maxlength="10" value="<%=obj.getOriginalBuildFeeCB()%>">
				屬公共設施建設費：
				&nbsp;&nbsp;
				<input class="field_RO" type="checkbox" name="cb_originalDeprUnitCB" size="10" maxlength="10" <%=("Y".equals(obj.getOriginalDeprUnitCB())?"checked":"")%>>
				<input class="field_QRO" type="hidden" name="originalDeprUnitCB" size="10" maxlength="10" value="<%=obj.getOriginalDeprUnitCB()%>">
				部門別依比例分攤：
				<br>
				園區別：
				<select class="field_RO" type="select" name="originalDeprPark" >
					<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprPark())%>
				</select>
				<input type="hidden" class="field_QRO" name="deprPark" value="<%=obj.getOriginalDeprPark()%>">
				&nbsp;&nbsp;&nbsp;
				部門別：
				<select class="field_RO" type="select" id="originalDeprUnit" name="originalDeprUnit" >
					<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit())%>
				</select>
				<input type="hidden" class="field_RO" name="originalDeprUnittemp" value="">
				&nbsp;&nbsp;&nbsp;
				部門別單位：
				<select class="field_RO" type="select" name="originalDeprUnit1">
					<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit1())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				會計科目：
				<select class="field_RO" type="select" name="originalDeprAccounts">
					<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprAccounts())%>
				</select>
				<br>
				殘值：
				<input class="field_RO" type="text" id="originalScrapValue" name="originalScrapValue" size="10" maxlength="10" value="<%=obj.getOriginalScrapValue()%>" >
				&nbsp;&nbsp;
				應攤提折舊總額：
				<input class="field_RO" type="text" name="originalDeprAmount" size="20" maxlength="15" value="<%=obj.getOriginalDeprAmount()%>" >
				<br>
				累計折舊
				[<input class="field_RO" type="text" name="originalAccumDepr" size="20" maxlength="15" value="<%=obj.getOriginalAccumDepr()%>">]
				&nbsp;&nbsp;
				攤提壽月
				<input class="field_RO" type="text" name="originalApportionMonth" size="10" maxlength="3" value="<%=obj.getOriginalApportionMonth()%>" >
				月提折舊金額
				[<input class="field_RO" type="text" name="originalMonthDepr" size="10" value="<%=obj.getOriginalMonthDepr()%>">]
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
			<input class="field_RO" type="checkbox" id="buildFeeCB" name="buildFeeCB" size="10" maxlength="10"  <%=("Y".equals(obj.getBuildFeeCB())? "checked" : "")%>>
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field_RO" type="checkbox" id="deprUnitCB" name="deprUnitCB" size="10" maxlength="10"  <%=("Y".equals(obj.getDeprUnitCB())? "checked" : "")%>>
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
			<select class=field_RO type="select" name="deprUnit1">
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
			[<input class="field_RO" type="text" name="deprAmount" size="20" maxlength="15" value="<%=obj.getDeprAmount()%>">]
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="accumDepr" size="20" maxlength="15" value="<%=obj.getAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			[<input class="field_RO" type="text" name="apportionMonth" size="10" maxlength="3" value="<%=obj.getApportionMonth()%>" >]
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
<!-- 	<tr id="div_pic"> -->
<!-- 	  <td class="td_form">附屬設備檔：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field_RO","picture",obj.getPicture())%></td> --%>
<!-- 		</tr> -->
		<tr id="div_escrow">
			<td class="td_form" >代管資產：</td>
			<td class="td_form_white" colspan="3">
				原始總價：
				<input class="field_RO" type="text" name="escrowOriValue" size="20" maxlength="20" value="<%=obj.getEscrowOriValue() %>">	
				&nbsp;&nbsp;&nbsp;&nbsp;			
				代管日前累計折舊：
				<input class="field_RO" type="text" name="escrowOriAccumDepr" size="20" maxlength="20" value="<%=obj.getEscrowOriAccumDepr() %>">
			</td>
		</tr>	
		<tr id="div_security">
			<td class="td_form" >有價證券：</td>
			<td class="td_form_white" colspan="3">
				發行法人名稱：
				<input class="field_RO" type="text" name="securityName" size="20" maxlength="20" value="<%=obj.getSecurityName() %>">				
			</td>
		</tr>	
		<tr id="div_proofDoc">
			<td class="td_form" >權利：</td>
			<td class="td_form_white" colspan="3">
<!--				<font color="red">*</font>憑證字號： 2015/01/21測試不需畢填-->
				憑證字號：
				<input class="field_RO" type="text" name="proofDoc1" size="20" maxlength="20" value="<%=obj.getProofDoc1() %>">				
			</td>
		</tr>
		<tr id="div_manageOrg">
			<td class="td_form">土地、建物：</td>
			<td class="td_form_white" colspan="3">
				管理機關：
				<%=util.View.getPopOrgan("field_RO","manageOrg",obj.getManageOrg(),obj.getManageOrgName(),"N")%> 				
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
		  	<textarea class="field" type="field_RO" name="notes" cols="50" rows="4"><%=obj.getNotes()%></textarea>
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
<!--		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產分號(訖)</a></th>-->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">型式廠牌(或土地建物標示)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">取得日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">使用年限(月)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">資料狀態</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">不折舊設定</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
		boolean primaryArray[] = {false,true,true,true,true,false,false,true,true,true,true,false,false,false,false,false,false,false,false};
		boolean displayArray[] = {true,false,false,false,false,false,true,true,false,true,false,true,true,true,true,true,true,true,false};
		String alignArray[]   = {"","","","","center","center","center","center","center","center","center","center","right","center","center","center","center",""};
		out.write(util.View.getQuerylistWithRowNum(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>