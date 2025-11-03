 
<!--
程式目的：建物減少作業－減損單明細
程式代號：untbu016f
程式日期：0940929
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH004F02">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%
obj.setKeeperno(user.getKeeperno());
obj.setUnitID(user.getUnitID());
obj.setOrganID(user.getOrganID());


if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	
}else if ("queryOne".equals(obj.getState())) {	obj._execQueryOneforDetail();
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	if (!obj.isMerge()) {
		obj._execInsertforDetail();
		if ("insertSuccess".equals(obj.getState())) {
			obj.setQueryAllFlag("true");
		}	
	} else {
		obj.setStateInsertError();
		obj.setErrorMsg("此為土地合併分割案件,請於土地分割案件維護作業進行新增");
	}
	
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {	
	if (!obj.isMerge()) {
		obj._execUpdateforDetail();
		if ("updateSuccess".equals(obj.getState())) {
			obj.setQueryAllFlag("true");
		}
	} else {
		obj.setStateUpdateError();
		obj.setErrorMsg("此為土地合併分割案件,請於土地分割案件維護作業進行修改");
	}
}else if ("delete".equals(obj.getState())  || "deleteError".equals(obj.getState())) {
	if (!obj.isMerge()) {
		obj._execDeleteforDetail();
		obj.setPropertyNo("");
		obj.setSerialNo("");
		obj.setLotNo("");
		if ("deleteSuccess".equals(obj.getState())) {
			obj.setQueryAllFlag("true");
		}
	} else {
		obj.setStateDeleteError();
		obj.setErrorMsg("此為土地合併分割案件,請於土地分割案件維護作業進行刪除");
	}
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
				obj.setDifferenceKind(((String[])objList.get(0))[4]);
				obj.setPropertyNo(((String[])objList.get(0))[5]);
				obj.setSerialNo(((String[])objList.get(0))[6]);
			}
			obj._execQueryOneforDetail();
		}
	}
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._CH_REDUCE, 2);
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js?v=1.1"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/getUNTBUBuilding.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untch004f01.jsp";
		} else {
			form1.action = "untch004f02.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		//alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkLen(form1.notes, "備註", 250);
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");		
		alertStr += checkEmpty(form1.cause,"減損原因");
		
		
		var checkStr1 = form1.propertyNo.value.substring(0,1);
		
		alertStr += checkQuotation(form1.notes,"備註");
		
		if(checkStr1 == '3' || checkStr1 == '4' || checkStr1 == '5'){
			alertStr += checkEmpty(form1.adjustBookAmount,"減少數量");
			alertStr += checkEmpty(form1.adjustBookValue,"減少帳面價值(淨值)");		
		}

		if(form1.cause.value == "201" || form1.cause.value == "206" || form1.cause.value == "205"){
			//alertStr += checkEmpty(form1.newEnterOrgName,"接管機關");	//20150121測試不需畢填
			//alertStr += checkEmpty(form1.transferDate,"交接日期");		//20150121測試不需畢填
		}	
		
		//問題單2108特殊車輛報廢時檢核
		var alertStr2 = "";
		var usdDate = getDateDiff("y", form1.buyDate.value, getToday());
		alertStr2 += checkInsertProperty(usdDate, form1.propertyNo.value, form1.serialNo.value);
		if (alertStr2.length!=0){
			alert(alertStr2);
		} 
	} 
	
	if(alertStr.length!=0){ alert(alertStr); return false; } 
	beforeSubmit(); 
}
function queryOne(enterOrg,ownership,caseNo,differenceKind,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg; 
	form1.ownership.value=ownership; 
	form1.caseNo.value=caseNo; 
	form1.differenceKind.value=differenceKind;
	form1.propertyNo.value=propertyNo; 
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne"; 
	beforeSubmit(); 
	form1.submit();  
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(surl!='../ch/untch004f01.jsp' && form1.propertyNo.value==""){
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


function gountch004f04(){
	var cause1 = encodeURI(form1.cause1.value);
	window.open("../../unt/ch/untch004f03.jsp?caseNo="+form1.caseNo.value+"&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&differenceKind="+form1.differenceKind.value+"&cause="+form1.cause.value+"&cause1="+cause1);
	
}

function init(){
	initQ_Form("Detail");
	
	if(form1.propertyNo.value != ''){	changePropertyNo(form1.propertyNo.value);
	}else{								initFormDisabled();
	}
	
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete", "R");
	}	
	if ( (document.all.querySelect[1].checked && form1.propertyNo.value=="") || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {
		setFormItem("insert,update,delete,batchInsertBut","R");
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
	//>>>>>>>>>>>>>>>>>>>>>>>
	
	//setFormItem("batchInsertBut","O");

	changecause(false);
	
	if(isObj(document.all.item("oldDeprMethod") && document.all.item("oldDeprMethod").value == "01")){
		document.all.item("newDeprMethod").className = "field_RO";
		document.all.item("cb_newBuildFeeCB").className = "field_RO";
		document.all.item("cb_newDeprUnitCB").className = "field_RO";
		document.all.item("select_newDeprPark").className = "field_RO";
		document.all.item("newDeprUnit").className = "field_RO";
		document.all.item("newDeprUnit1").className = "field_RO";
		document.all.item("newDeprAccounts").className = "field_RO";
		document.all.item("newScrapValue").className = "field_RO";
		document.all.item("newDeprAmount").className = "field_RO";
		document.all.item("newApportionMonth").className = "field_RO";
	}

	if($('input[name="cause"]').val() == '419'){
		$(':button[name="btn_newEnterOrg"]').attr('class','field');	
	}

	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.ownership.value !== '' && form1.caseNo.value !== '' 
			&& form1.differenceKind.value !== '' && form1.propertyNo.value !== '' && form1.serialNo.value !== '') {
		var key = form1.enterOrg.value + form1.ownership.value + form1.caseNo.value + form1.differenceKind.value + form1.propertyNo.value + form1.serialNo.value;
		listContainerRowClick(key);
	}
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
	
	var checkStr1 = form1.propertyNo.value.substring(0,1);
	var checkStr3 = form1.propertyNo.value.substring(0,3);
	
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


function checkDeprMethod(value){
	form1.newDeprAmount.value = "";
	form1.newAccumDepr.value = "";
	form1.newMonthDepr.value = "";
	form1.newMonthDepr1.value = "";
	form1.newApportionMonth.value = "";
	form1.newApportionEndYM.value = "";
	if(form1.state.value == 'insert' || form1.state.value == 'update'){
		setFormItem("cb_newBuildFeeCB,cb_newDeprUnitCB,newDeprUnit1,newDeprUnit,newDeprAccounts,newDeprAmount,newApportionMonth,newAccumDepr,newMonthDepr,newMonthDepr1,select_newDeprPark,newScrapValue","O");
	}
	
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
		form1.newApportionMonth.value = "";
		form1.newMonthDepr.value = "";
		form1.newMonthDepr1.value = "";
		form1.newApportionMonth.value = "";
		form1.newApportionEndYM.value = "";
		setFormItem("cb_newBuildFeeCB,cb_newDeprUnitCB,newDeprUnit1,newBuildFeeCB,newDeprUnitCB,select_newDeprPark,deprPark,newDeprUnit,newDeprAccounts,newDeprAmount,newAccumDepr,newApportionMonth,newMonthDepr,newMonthDepr1,newScrapValue","R");
	}else if(form1.newDeprMethod.value == '02'){
		
		if($("input[name='cb_newDeprUnitCB']").attr('checked') == 'checked'){			
			form1.newDeprUnit.value = "";
			form1.newDeprAccounts.value = "";
			
			setFormItem("newDeprUnit,newDeprUnit1,newDeprAccounts,newDeprAmount,newApportionMonth,newAccumDepr,newMonthDepr,newMonthDepr1","R");
		}else{
			if(form1.newScrapValue.value == ''){
				form1.newScrapValue.value = "0";
			}
			form1.newDeprAmount.value = form1.originalBV.value;
	
			var MonthStr;
			if(value == ''){
				var limityear = 0;
				
				if(form1.limitYear.value != ''){	limityear = form1.limitYear.value;
				}else{								limityear = form1.otherLimitYear.value;
				}

				limityear = parseInt(limityear) * 12;
				form1.newApportionMonth.value = limityear;
				MonthStr = limityear;
			}else{
				MonthStr = value;
			}
			
			form1.newAccumDepr.value = "0";
			form1.newMonthDepr.value = Math.floor((parseInt(form1.newDeprAmount.value) + parseInt(form1.newScrapValue.value))/ MonthStr);
			form1.newMonthDepr1.value = (parseInt(form1.newDeprAmount.value) - parseInt(form1.newMonthDepr.value) * (MonthStr - 1));

			if(form1.buyDate.value == ''){
				
			}else{				
				var YYY = parseInt(form1.buyDate.value.substring(0,3),10) + parseInt((MonthStr/12),10);
				var MM = parseInt(form1.buyDate.value.substring(3,5),10) + parseInt((MonthStr%12),10);

				if((MM/12) > 1){
					YYY = parseInt(YYY,10) + 1;
					MM = MM%12;
				}
				
				form1.newApportionEndYM.value = frontZero(YYY,3) + frontZero(MM,2);
			}
		}		
	}
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
		form1.reduceBookAmount.value = "0";
		form1.newBookAmount.value = parseInt(form1.oldBookAmount.value) + parseInt(form1.addBookAmount.value); 
	}else if(name == 'reduceBookAmount'){
		form1.addBookAmount.value = "0";
		form1.newBookAmount.value = parseInt(form1.oldBookAmount.value) - parseInt(form1.reduceBookAmount.value);
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
	comment.proofType = "ReduceProof";
	
	if(type == 'serialNo'){
		comment.propertyNo = $("input[name='propertyNo']").val();
		comment.serialNo = $("input[name='serialNo']").val();
	}
	
	if(comment.propertyNo == "" && comment.serialNo == ""){
		
	}else{
			
		$.post('queryADDProofData.jsp',
			comment,
			function(data){
				//將回傳資料寫入
				data=eval('('+data+')');
	
				if(data['hasData'] == 'N'){
					$("input[name='serialNo']").val('');
					alert('找不到此筆財產資料！！');
				}else{
					var checkReduceCause = true;
					
					var checkPropertyNostr01 = form1.propertyNo.value.substring(0,1);
					var checkPropertyNostr03 = form1.propertyNo.value.substring(0,3);
					
					if(checkPropertyNostr03 == '111' || checkPropertyNostr01 == '2' ||
							checkPropertyNostr01 == '3' || checkPropertyNostr01 == '4' || 
							checkPropertyNostr01 == '5'){
						
						var buyDatetemp = data['buyDate'];	//購置日期
						var limityeartemp = data['limityear'];	//使用年限
						var limityeartempM = limityeartemp * 12;	//使用年限(月)
						var deprMethodtemp = data['oldDeprMethod'];	//折舊方法
						var sDate = buyDatetemp; 
						var eDate = '<%=util.Datetime.getYYYMMDD()%>';
						var lastyear = getDateDiff("m",sDate,eDate);	//剩餘年限(月)
						
						//若「*減損單資料」頁籤之減損原因為「已達年限不堪使用(214)」，應控制只能帶入「已達年限」之資料。
						if(form1.cause.value == '214'){
							//不必折舊：剩餘年限(月)  >= 使用年限(月)
							if(deprMethodtemp == '01'){
								if(lastyear >= limityeartempM){
									
								}else{
									checkReduceCause = false;
								}
							//折舊：剩餘年限(月) > 使用年限(月)	
							}else{
								if(lastyear > limityeartempM){
									
								}else{
									checkReduceCause = false;
								}
							}
						//若「*減損單資料」頁籤之減損原因為「未達年限不堪使用(215)」，應控制只能帶入「未達年限」之資料。
						}else if(form1.cause.value == '215'){
							//不必折舊：系統日 < (購置日期＋年限)	
							if(deprMethodtemp == '01'){
								if(lastyear < limityeartempM){
									
								}else{
									checkReduceCause = false;
								}
							//折舊：系統年月 <= (購置年月＋年限)
							}else{
								if(lastyear <= limityeartempM){
									
								}else{
									checkReduceCause = false;
								}
							}
						}
					}
					
					if(checkReduceCause){
						$("input[name='propertyName1']").val(data['propertyName1']);
						$("input[name='oldPropertyNo']").val(data['oldPropertyNo']);
						$("input[name='oldSerialNo']").val(data['oldSerialNo']);
						$("input[name='laArea']").val(data['oldLaArea']);
						$("input[name='laHoldNume']").val(data['oldLaHoldNume']);
						$("input[name='laHoldDeno']").val(data['oldLaHoldDeno']);
						$("input[name='laHoldArea']").val(data['oldLaHoldArea']);
						$("input[name='buCArea']").val(data['oldBuCArea']);
						$("input[name='buSArea']").val(data['oldBuSArea']);
						$("input[name='buArea']").val(data['oldBuArea']);
						$("input[name='buHoldNume']").val(data['oldBuHoldNume']);
						$("input[name='buHoldDeno']").val(data['oldBuHoldDeno']);
						$("input[name='buHoldArea']").val(data['oldBuHoldArea']);
						$("input[name='measure']").val(data['oldMeasure']);
						$("input[name='sourceDate']").val(data['sourceDate']);
						$("input[name='originalBV']").val(data['originalBV']);
						$("input[name='buyDate']").val(data['buyDate']);
						$("input[name='bookNotes']").val(data['bookNotes']);
						
						$("input[name='bookAmount']").val(data['bookAmount']);
						$("input[name='bookUnit']").val(data['bookUnit']);
						$("input[name='bookValue']").val(data['bookValue']);
						$("input[name='netUnit']").val(data['netUnit']);
						$("input[name='netValue']").val(data['netValue']);
						
						$("input[name='oldBookAmount']").val(data['oldBookAmount']);
						$("input[name='oldBookUnit']").val(data['oldBookUnit']);
						$("input[name='oldBookValue']").val(data['oldBookValue']);
						$("input[name='oldAccumDepr']").val(data['oldAccumDepr']);
						$("input[name='oldNetUnit']").val(data['oldNetUnit']);
						$("input[name='oldNetValue']").val(data['oldNetValue']);
						/*
						//帳務資料: 減少資料 - 應取明細檔之數量 非主檔數量
						$("input[name='adjustBookAmount']").val(data['adjustBookAmount']);
						$("input[name='adjustBookUnit']").val(data['adjustBookUnit']);
						$("input[name='adjustBookValue']").val(data['adjustBookValue']);
						$("input[name='adjustAccumDepr']").val(data['adjustAccumDepr']);
						$("input[name='adjustNetUnit']").val(data['adjustNetUnit']);
						$("input[name='adjustNetValue']").val(data['adjustNetValue']);
						*/
						
						$("input[name='adjustBookAmount']").val(data['oldBookAmount']);
						$("input[name='adjustBookUnit']").val(data['oldBookUnit']);
						$("input[name='adjustBookValue']").val(data['oldBookValue']);
						$("input[name='adjustAccumDepr']").val(data['oldAccumDepr']);
						$("input[name='adjustNetUnit']").val(data['oldNetUnit']);
						$("input[name='adjustNetValue']").val(data['oldNetValue']);
						
						$("input[name='newBookAmount']").val("0");
						$("input[name='newBookUnit']").val("0");
						$("input[name='newBookValue']").val("0");
						$("input[name='newNetUnit']").val("0");
						$("input[name='newNetValue']").val("0");
						
						$("select[name='keepUnit']").val(data['keepUnit']);
						$("select[name='keeper']").val(data['keeper']);
						$("select[name='useUnit']").val(data['useUnit']);
						$("select[name='userNo']").val(data['userNo']);
						$("input[name='userNote']").val(data['userNote']);
						$("input[name='place1']").val(data['place1']);
						$("input[name='place1name']").val(data['place1name']);
						$("input[name='place']").val(data['place']);
						$("select[name='oldDeprMethod']").val(data['oldDeprMethod']);
						$("input[name='oldBuildFeeCB']").val(data['oldBuildFeeCB']);
						$("input[name='oldDeprUnitCB']").val(data['oldDeprUnitCB']);
						$("select[name='oldDeprPark']").val(data['oldDeprPark']);
						$("select[name='oldDeprUnit']").val(data['oldDeprUnit']);
						$("select[name='oldDeprUnit1']").val(data['oldDeprUnit1']);
						$("select[name='oldDeprAccounts']").val(data['oldDeprAccounts']);
						$("input[name='oldScrapValue']").val(data['oldScrapValue']);
						$("input[name='oldDeprAmount']").val(data['oldDeprAmount']);
						
						$("input[name='oldApportionMonth']").val(data['oldApportionMonth']);
						$("input[name='oldMonthDepr']").val(data['oldMonthDepr']);
						$("input[name='oldMonthDepr1']").val(data['oldMonthDepr1']);
						$("input[name='oldApportionEndYM']").val(data['oldApportionEndYM']);
						$("select[name='newDeprMethod']").val(data['oldDeprMethod']);
						$("input[name='newBuildFeeCB']").val(data['oldBuildFeeCB']);
						$("input[name='newDeprUnitCB']").val(data['oldDeprUnitCB']);
						$("input[name='newDeprPark']").val(data['oldDeprPark']);
						$("select[name='select_newDeprPark']").val(data['oldDeprPark']);
						$("select[name='newDeprUnit']").val(data['oldDeprUnit']);
						$("select[name='newDeprUnit1']").val(data['oldDeprUnit1']);
						$("select[name='newDeprAccounts']").val(data['oldDeprAccounts']);
						$("input[name='newScrapValue']").val(data['oldScrapValue']);
						$("input[name='newDeprAmount']").val(data['oldDeprAmount']);
						$("input[name='newAccumDepr']").val(data['oldAccumDepr']);
						$("input[name='newApportionMonth']").val(data['oldApportionMonth']);
						$("input[name='newMonthDepr']").val(data['oldMonthDepr']);
						$("input[name='newMonthDepr1']").val(data['oldMonthDepr1']);
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
						changeSignNo1('laSignNo1','laSignNo2','laSignNo3',data['laSignNo2']);			
						$("select[name='laSignNo3']").val(data['laSignNo3']);
						changeSignNo2('laSignNo1','laSignNo2','laSignNo3',data['laSignNo3']);
						$("input[name='laSignNo4']").val(data['laSignNo4']);
						$("input[name='laSignNo5']").val(data['laSignNo5']);
						$("select[name='buSignNo1']").val(data['buSignNo1']);
						$("select[name='buSignNo2']").val(data['buSignNo2']);
						changeSignNo1('buSignNo1','buSignNo2','buSignNo3',data['buSignNo2']);
						$("select[name='buSignNo3']").val(data['buSignNo3']);
						changeSignNo2('buSignNo1','buSignNo2','buSignNo3',data['buSignNo3']);
						$("input[name='buSignNo4']").val(data['buSignNo4']);
						$("input[name='buSignNo5']").val(data['buSignNo5']);			
						$("select[name='propertyKind']").val(data['propertyKind']);
						$("select[name='fundType']").val(data['fundType']);
						$("select[name='valuable']").val(data['valuable']);
						$("select[name='taxCredit']").val(data['taxCredit']);
						$("input[name='lotNo']").val(data['lotNo']);

						$("input[name='nameplate']").val(data['nameplate']);
						$("input[name='specification']").val(data['specification']);
						$("input[name='enterDate']").val(data['enterDate']);
						$("input[name='useYear']").val(data['useYear']);
						$("input[name='useMonth']").val(data['useMonth']);
						
						execChangePropertyNo(data['propertyNo']);
					}else{
						$("input[name='serialNo']").val('');
						if(form1.cause.value == '214'){
							alert('此筆財產未達年限，請重新確認！！');
						}else{
							alert('此筆財產已達年限，請重新確認！！');
						}
					}					
				}
			});	
	}
}

function changeAdjustUnit(colName){
	
	form1.newBookAmount.value = parseInt(form1.oldBookAmount.value) - parseInt(form1.adjustBookAmount.value);
	form1.newBookValue.value = parseInt(form1.oldBookValue.value) - parseInt(form1.adjustBookValue.value);
	form1.newAccumDepr.value = parseInt(form1.oldAccumDepr.value) - parseInt(form1.adjustAccumDepr.value);
	form1.newNetValue.value = parseInt(form1.oldNetValue.value) - parseInt(form1.adjustNetValue.value);
	form1.newNetUnit.value = parseInt(form1.oldNetUnit.value) - parseInt(form1.adjustNetUnit.value);
	
	if(form1.newNetUnit.value == 'NaN'){
		form1.newNetUnit.value = '0';
	}
	
}

function getCauseName(popCause,popCauseName){
	//傳送JSON
	var comment={};	
	comment.codeid = document.all.item("cause").value;
	comment.codecond1_1 = "2";
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

/**
 * initCause2 : 是否初始化【報損報廢原因】
 */
function changecause(initCause2){
	if (form1.cause.value=="499"){
		form1.cause1.readOnly = false;
		form1.cause1.style.backgroundColor=editbg;
		
	//	form1.newEnterOrgName.value = "";
		form1.newEnterOrg.value = "";
	//	form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
	}else if(form1.cause.value=="201" || form1.cause.value=="206" || form1.cause.value=="205"){
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "O");
		setFormItem("returnPlace,cause2,scrapValue2", "R");
		form1.transferDate.style.backgroundColor=editbg;
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
	}else{
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
	//	form1.newEnterOrgName.value = "";
		form1.newEnterOrg.value = "";
	//	form1.transferDate.value = "";
		form1.transferDate.style.backgroundColor=readbg;
		setFormItem("btn_newEnterOrg,transferDate,btn_transferDate", "R");
		setFormItem("returnPlace,cause2,scrapValue2", "O");
	}
	
	if((form1.cause.value=="208" || form1.cause.value=="214") && initCause2){
		form1.cause2.value = "逾最低年限，不堪使用，修復不具經濟價值"; 
	}
	
	if(form1.cause.value!="201" && form1.cause.value!="206" && form1.cause.value!="205"){
		if(isObj(document.all.item("btn_newEnterOrg"))){
			document.all.item("btn_newEnterOrg").className = "field_RO";
		}
		if(isObj(document.all.item("btn_transferDate"))){
			document.all.item("btn_transferDate").className = "field_RO";
		}
	}
}

function sonSubmit(){
	beforeSubmit();
	form1.submit();	
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input class="field_QRO" type="HIDDEN" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:900px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<% request.setAttribute("UNTCH004Q",obj);%>
	<jsp:include page="untch004q.jsp" />
	</table>
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="14%" colspan="2">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
		　　&nbsp;&nbsp;權屬：
			<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		　　<input type="hidden" name="caseNo" class="field_QRO"  size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="14%" colspan="2">財產區分別：</td>
		<td class="td_form_white" colspan="3">		
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>
			&nbsp;&nbsp;&nbsp;&nbsp;
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo() %>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="14%" colspan="2">工程編號：</td>
		<td class="td_form_white" colspan="3">		
			[<input class="field_QRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo() %>">]
			[<input class="field_QRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName() %>">]
		</td>
	</tr>
	
	
	<tr>
		<td class="td_form" colspan="2"><font color="red">*</font> 財產編號：</td>
		<td colspan="3" class="td_form_white">
	  		<%=new unt.ch.UNTCH_Tools().getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"")%>
	   		分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getFrontZero(this.name,7);queryADDProofData('serialNo');">
	   		<input class="field" type="hidden" name="lotNo" value="<%=obj.getLotNo()%>">
		<br>
		別名：
			[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
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
			<select class="field_RO" type="select" name="laSignNo3" >
				
			</select>　
		</td>
		<td class="td_form_white">
			地號：		
			[<input class="field_RO" type="text" name="laSignNo4" size="4" maxlength="4" value="<%=obj.getLaSignNo4() %>" onchange="getFrontZero(this.name,4);">] -
			[<input class="field_RO" type="text" name="laSignNo5" size="4" maxlength="4" value="<%=obj.getLaSignNo5() %>" onchange="getFrontZero(this.name,4);">]
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
			<select class="field_RO" type="select" name="buSignNo3">
				
			</select>　
		</td>
		<td class="td_form_white">
			建號：		
			[<input class="field_RO" type="text" name="buSignNo4" size="5" maxlength="5" value="<%=obj.getBuSignNo4() %>" onchange="getFrontZero(this.name,5);">] - 
			[<input class="field_RO" type="text" name="buSignNo5" size="3" maxlength="3" value="<%=obj.getBuSignNo5() %>" onchange="getFrontZero(this.name,3);">]
		</td>	
	</tr>
	<tr>
		<td class="td_form" colspan="2">減損：</td>
		<td class="td_form_white" colspan = "3">
			日期：[<input type="text" name="reduceDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;　入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			 &nbsp;&nbsp;&nbsp;
		<br>
			<font color="red">*</font>減損原因：
				<%=uctls.getReduceCause("field_QRO","cause",obj.getCause(),obj.getCauseName(),"2,4","changecause(true);")%>
				&nbsp;其他說明：<input class="field_QRO" type="text" name="cause1" size="30" maxlength="20" value="<%=obj.getCause1()%>">
		<br>
<!--			<font color="red">*</font>-->接管機關：<%=util.View.getPopOrgan("field_Q","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>
			&nbsp;&nbsp;<!--<font color="red">*</font>-->交接日期：<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
			<br>
			報損報廢原因
			<input type="text" class="field" name="cause2" value="<%=obj.getCause2() %>" size="40">
			&nbsp;&nbsp;
			繳存地點
			<input type="text" class="field" name="returnPlace" value="<%=obj.getReturnPlace() %>" size="30">
		</td>
	</tr>
	
	<tr name="div_mp">
		<td class="td_form" width="10%" rowspan="4">帳務資料：</td>
		<td class="td_form" width="10%">帳務摘要：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
			</td>
	</tr>
	<tr name="div_mp">
		<td class="td_form" width="10%">調整前：</td>
		<td class="td_form_white" colspan="3">
			數量：
			[<input type="text" class="field_RO" name="oldBookAmount" value="<%=obj.getOldBookAmount() %>" size="10" >]
			&nbsp;&nbsp;
			原值：
			[<input type="text" class="field_RO" name="oldBookValue" value="<%=obj.getOldBookValue() %>" size="10" >]
			<input type="hidden" class="field_RO" name="oldBookUnit" value="<%=obj.getOldBookUnit() %>" size="10" >
			&nbsp;&nbsp;
			累計折舊：
			[<input type="text" class="field_RO" name="oldAccumDepr" value="<%=obj.getOldAccumDepr() %>" size="10" >]		
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="oldNetValue" value="<%=obj.getOldNetValue() %>" size="10" >]
			<br>
			單價：
			[<input type="text" class="field_RO" name="oldNetUnit" value="<%=obj.getOldNetUnit() %>" size="10" >]
		</td>
	</tr>
	<tr name="div_mp">
		<td class="td_form" width="10%">減少：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>數量：
			<input type="text" class="field" name="adjustBookAmount" value="<%=obj.getAdjustBookAmount() %>" size="10" onchange="changeAdjustUnit(this.name);">
			&nbsp;&nbsp;
			<font color="red">*</font>原值：
			<input type="text" class="field" name="adjustBookValue" value="<%=obj.getAdjustBookValue() %>" size="10" onchange="changeAdjustUnit(this.name);">
			<input type="hidden" class="field" name="adjustBookUnit" value="<%=obj.getAdjustBookUnit() %>" size="10" onchange="changeAdjustUnit(this.name);">
			&nbsp;&nbsp;
			累計折舊：
			[<input type="text" class="field_RO" name="adjustAccumDepr" value="<%=obj.getAdjustAccumDepr() %>" size="10" >]		
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="adjustNetValue" value="<%=obj.getAdjustNetValue() %>" size="10" >]
			<br>
			單價：
			[<input type="text" class="field_RO" name="adjustNetUnit" value="<%=obj.getAdjustNetUnit() %>" size="10" >]
		</td>
	</tr>
	<tr name="div_mp">
		<td class="td_form" width="10%">調整後：</td>
		<td class="td_form_white" colspan="3">
			數量：
			[<input type="text" class="field_RO" name="newBookAmount" value="<%=obj.getNewBookAmount() %>" size="10" >]
			&nbsp;&nbsp;
			原值：
			[<input type="text" class="field_RO" name="newBookValue" value="<%=obj.getNewBookValue() %>" size="10" >]
			<input type="hidden" class="field_RO" name="newBookUnit" value="<%=obj.getNewBookUnit() %>" size="10" >
			&nbsp;&nbsp;
			累計折舊：
			[<input type="text" class="field_RO" name="newAccumDepr" value="" size="10" >]		
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="newNetValue" value="<%=obj.getNewNetValue() %>" size="10" >]
			<br>
			單價：
			[<input type="text" class="field_RO" name="newNetUnit" value="<%=obj.getNewNetUnit() %>" size="10" >]
		</td>
	</tr>
	
	<tr name="div_rt">
		<td class="td_form" width="10%" rowspan="2">帳務資料：</td>
		<td class="td_form" width="10%">調整前：</td>
		<td class="td_form_white" colspan="3">
			數量：
			[<input type="text" class="field_RO" name="oldBookAmount1" value="1" size="10" >]
			&nbsp;&nbsp;
			原值：
			[<input type="text" class="field_RO" name="oldBookValue1" value="<%=obj.getBookValue() %>" size="10" >]
			&nbsp;&nbsp;
			累計折舊：
			[<input type="text" class="field_RO" name="oldAccumDepr1" value="<%=obj.getOldAccumDepr() %>" size="10" >]		
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="oldNetValue1" value="<%=obj.getNetValue() %>" size="10" >]
		</td>
	</tr>
	<tr name="div_rt">
		<td class="td_form" width="10%">調整後：</td>
		<td class="td_form_white" colspan="3">
			數量：
			[<input type="text" class="field_RO" name="newBookAmount1" value="0" size="10" >]
			&nbsp;&nbsp;
			原值：
			[<input type="text" class="field_RO" name="newBookValue1" value="0" size="10" >]
			&nbsp;&nbsp;
			帳面價值(淨值)：
			[<input type="text" class="field_RO" name="newNetValue1" value="0" size="10" >]
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
			&nbsp;&nbsp;&nbsp;
			調整後年限(月)：
			[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
			<br>
			已使用年月：
			[<input class="field_RO" type="text" name="useYear" size="10" value="<%=obj.getUseYear()%>">]年
			[<input class="field_RO" type="text" name="useMonth" size="10" value="<%=obj.getUseMonth()%>">]個月
		</td>
	</tr>
	<tr name="div_bu">
	  <td class="td_form" colspan="2">門牌：</td>
	  <td class="td_form_white" colspan="3">
	  	[<input name="doorPlate4" type="text" class="field_RO" value="<%=obj.getDoorPlate4()%>" size="30">]
	  </td>
	</tr>
	<tr name="div_common">
		<td class="td_form" colspan="2">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]
			&nbsp;&nbsp;
			購置日期：
			[<input type="text" name="buyDate" class="field_RO"  size="6" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;&nbsp;
			財產取得日期：[<input type="text" name="sourceDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
		</td>
	</tr>	
	<tr name="div_mp">
		<td class="td_form" colspan="2">廠牌型式：</td>
		<td class="td_form_white" colspan="3">		
			品名：[<input class="field_RO" type="text" name="articleName" size="20" value="<%=obj.getArticleName()%>">]　
			廠牌：[<input class="field_RO" type="text" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate()%>" >]
		<br>
			型式：[<input class="field_RO" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">]　
			牌照號碼規格/序號規格：[<input class="field_RO" type="text" name="licensePlate" size="30" maxlength="30" value="<%=obj.getLicensePlate()%>">]
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" colspan="2">財產性質：</td>
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
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			　　　　　　　抵繳遺產稅：	
			<select class="field_RO" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>			
		</td>
	</tr>
	<tr name="div_common">
		<td class="td_form" colspan="2">權狀字號：</td>
		<td class="td_form_white" colspan="3">
		[<input class="field_RO" type="text" name="proofDoc" size="18" maxlength="20" value="<%=obj.getProofDoc()%>">]
		</td>
	</tr>
	<tr name="div_la">
		<td class="td_form" colspan="2">土地面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：[<input class="field_RO" type="text" name="laArea" size="9" maxlength="9" value="<%=obj.getLaArea()%>">]
			
		<br>
			權利分子：[<input class="field_RO" type="text" name="laHoldNume" size="10" maxlength="10" value="<%=obj.getLaHoldNume()%>">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="laHoldDeno" size="10" maxlength="10" value="<%=obj.getLaHoldDeno()%>">]
			&nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="laHoldArea" size="9" maxlength="9" value="<%=obj.getLaHoldArea()%>">]
		</td>
	</tr>
	<tr name="div_bu">
		<td class="td_form" colspan="2">建物面積：</td>
		<td class="td_form_white" colspan="3">		
			主面積(㎡)：[<input class="field_RO" type="text" name="buCArea" size="9" maxlength="9" value="<%=obj.getBuCArea()%>">]
			&nbsp;附屬面積(㎡)：[<input class="field_RO" type="text" name="buSArea" size="9" maxlength="9" value="<%=obj.getSArea()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			總面積(㎡)：[<input class="field_RO" type="text" name="buArea" size="9" maxlength="9" value="<%=obj.getBuArea()%>">]
		<br>
			權利分子：[<input class="field_RO" type="text" name="buHoldNume" size="10" maxlength="10" value="<%=obj.getBuHoldNume()%>">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="buHoldDeno" size="10" maxlength="10" value="<%=obj.getBuHoldDeno()%>">]
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="buHoldArea" size="9" maxlength="9" value="<%=obj.getBuHoldArea()%>">]
		</td>
	</tr>	
	<tr name="div_rf">
		<td class="td_form" colspan="2">土地改良物：</td>
  	    <td colspan="3" class="td_form_white">
  	    	計量數：
  	    	[<input name="measure" type="text" class="field_RO" onChange="changeArea();" value="<%=obj.getMeasure() %>" size="15" maxlength="15">]      		
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
	<tr name="div_la">
		<td class="td_form" colspan="2">當期公告日期：</td>
		<td class="td_form_white" colspan="3">
			<input type="text" class="field_RO" name="bulletinDate" value="<%=obj.getBulletinDate() %>" size="7">
			&nbsp;&nbsp;
			當期公告地價：
			<input type="text" class="field_RO" name="valueUnit" value="<%=obj.getValueUnit() %>" size="10">				
		</td>
	</tr>
	<tr name="div_la">
	  <td class="td_form" colspan="2">使用分區：</td>
	  <td colspan="3" class="td_form_white"><select class="field_RO" type="select" name="useSeparate" onChange="changeSelect();">
              <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", "")%>
            </select>　編定使用種類：<select class="field_RO" type="select" name="useKind" onChange="changeSelect();">
              <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", "")%>
            </select></td>
	  </tr>
	<tr name="div_olddeprmethod">
			<td class="td_form" colspan="2">調整前折舊資料：</td>
			<td class="td_form_white" colspan="3">
				折舊方法
				<%=util.View._getSelectHTML_withFunction("field_RO", "oldDeprMethod", obj.getOldDeprMethod(),"DEP", "checkDeprMethod('');") %>
				&nbsp;&nbsp;
				<input class="field_RO" type="checkbox" name="cb_oldBuildFeeCB" size="10" maxlength="10" onclick="checkOriginalBuildFeeCB();" <%=("Y".equals(obj.getOldBuildFeeCB())?"checked":"")%>>
				<input class="field" type="hidden" name="oldBuildFeeCB" size="10" maxlength="10" value="<%=obj.getOldBuildFeeCB()%>">
				屬公共設施建設費
				&nbsp;&nbsp;
				<input class="field_RO" type="checkbox" name="cb_oldDeprUnitCB" size="10" maxlength="10" onclick="checkOriginalDeprUnitCB();checkDeprMethod('');" <%=("Y".equals(obj.getOldDeprUnitCB())?"checked":"")%>>
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
				月提折舊金額（最後一個月）
				[<input class="field_RO" type="text" name="oldMonthDepr1" size="10" value="<%=obj.getOldMonthDepr1()%>">]
			</td>
	</tr>			
	<tr name="div_newdeprmethod">
		<td class="td_form" colspan="2">調整後折舊資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>折舊方法
			<%=util.View._getSelectHTML_withFunction("field", "newDeprMethod", obj.getNewDeprMethod(),"DEP", "checkDeprMethod('');") %>
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="cb_newBuildFeeCB" size="10" maxlength="10" onclick="checkNewBuildFeeCB();" <%=("Y".equals(obj.getNewBuildFeeCB())?"checked":"")%>>
			<input class="field" type="hidden" name="newBuildFeeCB" size="10" maxlength="10" value="<%=obj.getNewBuildFeeCB()%>">
			屬公共設施建設費
			&nbsp;&nbsp;
			<input class="field" type="checkbox" name="cb_newDeprUnitCB" size="10" maxlength="10" onclick="checkNewDeprUnitCB();checkDeprMethod('');" <%=("Y".equals(obj.getNewDeprUnitCB())?"checked":"")%>>
			<input class="field" type="hidden" name="newDeprUnitCB" size="10" maxlength="10" value="<%=obj.getNewDeprUnitCB()%>">
			部門別依比例分攤
			<br>
			園區別
			<select class="field" type="select" name="select_newDeprPark" onchange="form1.newDeprPark.value = this.value;">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprPark())%>
			</select>
			<input type="hidden" class="field_QRO" name="newDeprPark" value="<%=obj.getNewDeprPark()%>">
			<input type="hidden" class="field_QRO" name="deprPark" value="<%=obj.getNewDeprPark()%>">
			&nbsp;&nbsp;&nbsp;
			部門別
			<select class="field" type="select" name="newDeprUnit" onchange="">
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
			<input class="field" type="text" name="newScrapValue" size="10" maxlength="10" value="<%=obj.getNewScrapValue()%>" onchange="checkDeprMethod('');">
			&nbsp;&nbsp;
			應攤提折舊總額
			<input class="field" type="text" name="newDeprAmount" size="20" maxlength="15" value="<%=obj.getNewDeprAmount()%>" >
			<br>
			累計折舊
			[<input class="field_RO" type="text" name="newAccumDepr" size="20" maxlength="15" value="<%=obj.getNewAccumDepr()%>">]
			&nbsp;&nbsp;
			攤提壽月
			<input class="field" type="text" name="newApportionMonth" size="10" maxlength="3" value="<%=obj.getNewApportionMonth()%>" onChange="checkDeprMethod(this.value);">
			月提折舊金額
			[<input class="field_RO" type="text" name="newMonthDepr" size="10" value="<%=obj.getNewMonthDepr()%>">]
			<br>
			月提折舊金額（最後一個月）
			[<input class="field_RO" type="text" name="newMonthDepr1" size="10" value="<%=obj.getNewMonthDepr1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" colspan="2">備註：</td>
		<td class="td_form_white" colspan="4">
			<textarea class="field" name="notes" cols="25" rows="4" ><%=obj.getNotes()%></textarea>
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
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">

	<input type="hidden" name="securityName" value="<%=obj.getSecurityName()%>">
	<input type="hidden" name="bookAmount" value="<%=obj.getBookAmount()%>">
	<input type="hidden" name="bookValue" value="<%=obj.getBookValue()%>">
	<input type="hidden" name="bookUnit" value="<%=obj.getBookUnit()%>">
	<input type="hidden" name="netValue" value="<%=obj.getNetValue()%>">
	<input type="hidden" name="netUnit" value="<%=obj.getNetValue()%>">
	
	<input type="hidden" class="field_Q" name="p_proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="p_proofDoc" value="<%=obj.getProofDoc()%>">
	<input type="hidden" class="field_Q" name="p_proofNo" value="<%=obj.getProofNo()%>">
	<input type="hidden" class="field_Q" name="p_summonsDate" value="<%=obj.getSummonsDate()%>">
	<input type="hidden" class="field_Q" name="proofYear" value="<%=obj.getProofYear()%>">
	<input type="hidden" class="field_Q" name="proofNo" value="<%=obj.getProofNo()%>">
	
	<input type="hidden" name="newApportionEndYM" value="">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="true" name="batchInsertBut" value="現有資料明細新增" onClick="gountch004f04();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">序號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">型式廠牌(或土地建物標示)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">原值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">帳務摘要</a></th>
	</thead> 
	<tbody id="listTBODY">	
	<%
	boolean primaryArray[] = {true,true,true,false,true,true,true,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,true,false,true,true,true,true,true,true,true,true,true,true};
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
			checkDeprMethod('');
			if(form1.cause.value == '208' || form1.cause.value == '214'){
				form1.cause2.value = "逾最低年限，不堪使用，修復不具經濟價值";
			}
			
			break;				
		case "update":
			checkDeprMethod('');
			break;
	}
}
</script>
</body>
</html>
