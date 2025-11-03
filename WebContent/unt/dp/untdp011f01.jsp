<!--
程式目的： 財產折舊資料調整-基本資料
程式代號：untdp011f01
程式日期：103/08/30
程式作者： Mike Kao
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP011F01">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("UpdateMainFile".equals(obj.getState())) {
	obj.UpdateMainFile();
}

obj.setQ_enterOrg(user.getOrganID());
obj.setEnterOrg(user.getOrganID());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj.queryOne();	
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
		if("deleteSuccess".equals(obj.getState())){			
		}else{ 
			if("".equals(obj.getDifferenceKind()) ||
					"".equals(obj.getPropertyNo()) ||
					"".equals(obj.getSerialNo()) ||
					"".equals(obj.getSerialNo1()) ||
					"".equals(obj.getOwnership())){				
				obj.setDifferenceKind(((String[])objList.get(0))[0]);
				obj.setPropertyNo(((String[])objList.get(0))[2]);
				obj.setSerialNo(((String[])objList.get(0))[3]);
				obj.setSerialNo1(((String[])objList.get(0))[13]);
				obj.setOwnership(((String[])objList.get(0))[14]);
			}
			
			obj.queryOne();
		}
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
		new Array("ownership","1")
);



function checkField(){
	var alertStr="";
	if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"||form1.state.value=="UpdateMainFile"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");	
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.oldDeprMethod,"調整前－折舊方法");
		alertStr += checkEmpty(form1.newDeprMethod,"調整後－折舊方法");
		if(document.all.newDeprUnitCB.checked == true){
			//若「新折舊－部門別依比例分攤(newDeprUnitCB)」為「Y:是」，下列欄位設為RO並清除資料
			form1.newDeprUnit.value= "";
			form1.newDeprUnit1.value= "";
			form1.newDeprAccounts.value= "";
		}else if (form1.differenceKind.value == "120") { 
			//若「財產區分別(differenceKind)」為「120:公務用_作業基金」且「新折舊－部門別依比例分攤(newDeprUnitCB)」為「N:否」，下列欄位設為必填
			alertStr += checkEmpty(form1.newDeprUnit,"新折舊－部門別");
			alertStr += checkEmpty(form1.newDeprUnit1,"新折舊－部門別單位");
			alertStr += checkEmpty(form1.newDeprAccounts,"新折舊－會計科目");
		}		
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
	return true;
}

function queryOne(differencekind,propertyno,serialno,serialno1,ownership){
	form1.state.value="queryOne";
	form1.differenceKind.value=differencekind;
	form1.propertyNo.value=propertyno;
	form1.serialNo.value=serialno;
	form1.serialNo1.value=serialno1;
	form1.ownership.value=ownership;
	beforeSubmit();
	form1.submit();
}



function checkURL(surl){
	if (form1.serialNo1.value != "") {
		form1.adjustserialNo1.value = form1.serialNo1.value;
	}
	form1.mainPage.value=form1.currentPage.value;
	form1.currentPage.value=1;
	
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}

function init(){
	setDisplayItem("spanNextInsert","H");
	if (<%=obj.getUpdateMaster().equals("Y")%>){
		setFormItem("update", "R");
		setFormItem("delete", "R");
		setFormItem("UpdateMainFile", "R");
	}
	
	autoListContainerRowClick();
}

function autoListContainerRowClick() {
	if (form1.ownership.value !== '' && form1.differenceKind.value !== '' && form1.propertyNo.value !== '' 
			&& form1.serialNo.value !== '' && form1.serialNo1.value !== '') {
		var key = form1.differenceKind.value + form1.propertyNo.value + form1.serialNo.value + form1.serialNo1.value + form1.ownership.value;
		listContainerRowClick(key);
	}
}

function checkNewDeprUnitCB(){
	if(form1.canChgDeprUnitCB.value == "N" && <%="N".equals(user.getIsAdminManager())%>){
		alert("已有折舊紀錄，不可調整比例分攤");
		$("input[name='newDeprUnitCB']").attr('checked',<%=("Y".equals(obj.getNewDeprUnitCB())?true:false)%>);
	}
}

function checkRadioBox(){
	if(document.all.newBuildFeeCB.checked == true){
		form1.newBuildFeeCB.value= "Y";
	}else{
		form1.newBuildFeeCB.value= "N";
	}
	
	if($("input[name='newDeprUnitCB']").attr('checked') == 'checked'){
		$("input[name='newDeprUnitCB']").val('Y');
		//若「新折舊－部門別依比例分攤(newDeprUnitCB)」為「Y:是」，下列欄位設為RO並清除資料
		form1.newDeprUnit.value= "";
		form1.newDeprUnit1.value= "";
		form1.newDeprAccounts.value= "";
		setFormItem("newDeprUnit", "R");
		setFormItem("newDeprUnit1", "R");
		setFormItem("newDeprAccounts", "R");
	}else{
		//若「新折舊－部門別依比例分攤(newDeprUnitCB)」為「N:否」，下列欄位設為必填
		$("input[name='newDeprUnitCB']").val('N');
		setFormItem("newDeprUnit", "O");
		setFormItem("newDeprUnit1", "O");
		setFormItem("newDeprAccounts", "O");			
	}
	
}
function changePropertyNo(propertyNo){}

function queryADDProofData(type){
	//傳送JSON
	var comment={};
	comment.enterOrg = $("input[name='enterOrg']").val();
	comment.ownership = $("select[name='ownership']").val();
	comment.differenceKind = $("select[name='differenceKind']").val();
	comment.propertyNo = "";
	comment.serialNo = "";
	comment.proofType = "untdp011f01";
	
	if(type == 'serialNo'){
		comment.propertyNo = $("input[name='propertyNo']").val();
		comment.serialNo = $("input[name='serialNo']").val();
		
	}
	
	if(comment.propertyNo == "" && comment.serialNo == "" && comment.ownership == "" & comment.differenceKind == ""){
		
	}else{			
		$.post('queryADDProofData.jsp',
			comment,
			function(data){
				//將回傳資料寫入
				//alert(data);
				data=eval('('+data+')');
	
				if(data['hasData'] == 'N'){
					$("input[name='serialNo']").val('');
					alert('找不到此筆財產資料！！');
				}else{					
						$("select[name='oldDeprMethod']").val(data['deprmethod']);
						if(data['buildfeecb'] == 'Y'){
							$("input[name='oldBuildFeeCB']:checkbox").prop("checked",true);
							$("input[name='oldBuildFeeCB']").val("Y");
						}
						if(data['deprunitcb'] == 'Y'){
							$("input[name='oldDeprUnitCB']:checkbox").prop("checked",true);
							$("input[name='oldDeprUnitCB']").val("Y");
						}
						$("input[name='oldDeprPark']").val(data['deprpark']);
						$("input[name='oldDeprParkName']").val(data['deprparkname']);
						$("input[name='oldDeprUnit']").val(data['deprunit']);
						$("input[name='oldDeprUnitName']").val(data['deprunitname']);
						$("input[name='oldDeprUnit1']").val(data['deprunit1']);
						$("input[name='oldDeprUnit1Name']").val(data['deprunit1name']);						
						$("input[name='oldDeprAccounts']").val(data['depraccounts']);
						$("input[name='oldDeprAccountsName']").val(data['depraccountsname']);
						$("input[name='oldScrapValue']").val(data['scrapvalue']);
						$("input[name='oldDeprAmount']").val(data['depramount']);
						$("input[name='oldAccumDepr']").val(data['accumdepr']);
						$("input[name='oldApportionMonth']").val(data['apportionmonth']);
						$("input[name='oldMonthDepr']").val(data['monthdepr']);
						$("input[name='oldMonthDepr1']").val(data['monthdepr1']);
						$("input[name='oldApportionEndYM']").val(data['apportionendym']);
						
						$("select[name='newDeprMethod']").val(data['deprmethod']);
						if(data['buildfeecb'] == 'Y'){
							$("input[name='newBuildFeeCB']:checkbox").prop("checked",true);
							$("input[name='newBuildFeeCB']").val("Y");
						}
						if(data['deprunitcb'] == 'Y'){
							$("input[name='newDeprUnitCB']:checkbox").prop("checked",true);
							$("input[name='newDeprUnitCB']").val("Y");
						}
						$("select[name='newDeprPark']").val(data['deprpark']);
						$("select[name='newDeprUnit']").val(data['deprunit']);
						$("select[name='newDeprUnit1']").val(data['deprunit1']);
						$("select[name='newDeprAccounts']").val(data['depraccounts']);
						$("input[name='newScrapValue']").val(data['scrapvalue']);
						$("input[name='newDeprAmount']").val(data['depramount']);
						$("input[name='newAccumDepr']").val(data['accumdepr']);
						$("input[name='newApportionMonth']").val(data['apportionmonth']);
						$("input[name='newMonthDepr']").val(data['monthdepr']);
						$("input[name='newMonthDepr1']").val(data['monthdepr1']);
						$("input[name='newApportionEndYM']").val(data['apportionendym']);

						$("input[name='lotNo']").val(data['lotno']);
						$("input[name='bookValue']").val(data['bookvalue']);
						$("input[name='netValue']").val(data['netvalue']);
						$("input[name='propertyName1']").val(data['propertyname1']);
						
						changeDeprByDifferencekind();
				}
			});	
	}
}

function changeDeprByDifferencekind() {
	// 問題單1114
    if (form1.differenceKind.value == "110") {
        setFormItem("newBuildFeeCB,newDeprUnitCB,newDeprPark,newDeprUnit,newDeprUnit1,newDeprAccounts","R");
    }
}

function changenewMonthDepr(){
		if(form1.newApportionMonth.value >=1 ){
			form1.newMonthDepr.value = Math.round( (parseInt(form1.newDeprAmount.value)-parseInt(form1.newScrapValue.value)-parseInt(form1.newAccumDepr.value))/parseInt(form1.newApportionMonth.value) );
			if(form1.newApportionMonth.value ==1 ){
				form1.newMonthDepr1.value=Math.round( ( parseInt(form1.newDeprAmount.value)-parseInt(form1.newScrapValue.value)-parseInt(form1.newAccumDepr.value)));				
			}else{
				form1.newMonthDepr1.value=Math.round( ( parseInt(form1.newDeprAmount.value)-parseInt(form1.newScrapValue.value)-parseInt(form1.newAccumDepr.value))) - Math.round(parseInt(form1.newMonthDepr.value)*parseInt(form1.newApportionMonth.value-1));
			}
			if(getDateAdd('m', parseInt(form1.newApportionMonth.value-form1.oldApportionMonth.value), form1.oldApportionEndYM.value+"01").length==7){
				form1.newApportionEndYM.value =getDateAdd('m', parseInt(form1.newApportionMonth.value-form1.oldApportionMonth.value), form1.oldApportionEndYM.value+"01").substring(0,5);
			}			
		}else{
			alert('攤提壽月必須大於1!!');
			form1.newApportionMonth.value=form1.oldApportionMonth.value;
		}
}

function onClickUpdateMainFile(){
	if(!confirm("是否要將資料更新至主檔?")){
		return false;
	}
	if(checkField()){
		form1.state.value = "UpdateMainFile";
		form1.updateMaster.value = "Y";
		form1.submit();
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
	//if($("select[name='newDeprUnit1']").val() == ''){
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
	//}
}
function queryDeprUnitDataforDeprAccounts(){
	//if($("select[name='newDeprAccounts']").val() == ''){
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
	//}
}
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()" >
<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="deprUnit" value="<%=obj.getNewDeprUnit()%>">	
	<input type="hidden" name="canChgDeprUnitCB" value="<%=obj.getCanChgDeprUnitCB()%>">	
	<input type="hidden" name="adjustserialNo1" >
</td></tr></table>
<!--Query區============================================================-->
<div id="queryContainer" style="width:600px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
			<tr>
				<td class="queryTDLable" >入帳機關：</td>
		        <td class="queryTDInput">
			  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
			    </td>		
			</tr>
			<tr>
			  <td class="queryTDLable" >財產區分別：</td>
			  <td class="queryTDInput" >
			  	<select class="field_Q" type="select" name="q_differenceKind">
				    <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getQ_differenceKind())%>
				</select>		  	 
			  </td>
			</tr>
			<tr>
			  <td class="queryTDLable" >財產編號：</td>
			  <td class="queryTDInput" >
			  	起：
				<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>
				<br>
				訖：
				<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
			  </td>
			</tr>
			<tr>
			  <td class="queryTDLable" >財產分號：</td>
			  <td class="queryTDInput" >
			  	起：
			  	<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">
			  	&nbsp;~&nbsp;
				訖：
			  	<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">		  	 
			  </td>
			</tr>
			<tr>
			  <td class="queryTDLable" >財產別名：</td>
			  <td class="queryTDInput" >
			  	<input type="text" class="field_Q" name="q_propertyNoName1" value="<%=obj.getQ_propertyNoName1() %>" size="30" maxlength="40">		  	 
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

<!--頁籤區============================================================-->
<table STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">	
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">基本資料</td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untdp011f02.jsp');">折舊比例</a></td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
	</tr>	
</table>

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<tr>
			<td class="td_form"><font color="red">*</font>入帳機關：</td>
			<td class="td_form_white" colspan="3">
			 <%=util.View.getPopOrgan("field_Q","enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>

				&nbsp;&nbsp;&nbsp;
				<font color="red">*</font>權屬：
				<select class="field" type="select" name="ownership">
					<%=util.View.getOnwerOption(obj.getOwnership())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_form"><font color="red">*</font>財產區分別：</td>
			<td colspan="3" class="td_form_white">
				<select class="field" type="select" name="differenceKind">
				　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getDifferenceKind()) %>
				</select>
        	</td>
        </tr>
		<tr>
			<td class="td_form"><font color="red">*</font>財產編號：</td>
			<td class="td_form_white" colspan="3">
				<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"")%> 
				<font color="red">*</font>分號：
				<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getFrontZero(this.name,7);queryADDProofData('serialNo');">				
				&nbsp;&nbsp;&nbsp;
				財產別名
				[<input type="text" class="field" name="propertyName1" value="<%=obj.getPropertyName1() %>" size="30" maxlength="30">]
			</td>
		</tr>
		<tr>
			<td class="td_form">調整次序：</td>
			<td class="td_form_white" colspan="3">
				[<input type="text" class="field_RO" name="serialNo1" value="<%=obj.getSerialNo1() %>" size="4" maxlength="3">]
				&nbsp;&nbsp;
				財產批號
				[<input type="text" class="field_RO" name="lotNo" value="<%=obj.getLotNo() %>" size="7" maxlength="7">]
				&nbsp;&nbsp;
				已更新主檔
				<input class="field_RO" type="checkbox" id="" name="updateMaster" size="1" maxlength="1" value="Y" onClick="checkRadioBox();" <%=("Y".equals(obj.getUpdateMaster())?"checked":"")%> >
			</td>
		</tr>
		<tr>
			<td class="td_form">原值：</td>
			<td class="td_form_white" colspan="3">
				[<input class="field_RO" type="text" name="bookValue" size="16" maxlength="15" value="<%=obj.getBookValue() %>" >]
				&nbsp;&nbsp;
				帳面價值(淨值)
				[<input class="field_RO" type="text" name="netValue" size="16" maxlength="15" value="<%=obj.getNetValue() %>" >]
			</td>
		</tr>
		<tr>
			<td class="td_form">調整前折舊資料：</td>
			<td class="td_form_white" colspan="3">
				<font color="red">*</font>折舊方法
				<select name="oldDeprMethod" class="field_RO" type="select">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getOldDeprMethod())%>
				</select>
				&nbsp;&nbsp;
				<input class="field_RO" type="checkbox" id="" name="oldBuildFeeCB" size="1" maxlength="1" value="Y" onClick="checkRadioBox();" <%=("Y".equals(obj.getOldBuildFeeCB())?"checked":"")%> >
				屬公共設施建設費				
				&nbsp;&nbsp;
				<input class="field_RO" type="checkbox" id="" name="oldDeprUnitCB" size="1" maxlength="1" value="Y" onClick="checkRadioBox();" <%=("Y".equals(obj.getOldDeprUnitCB())?"checked":"")%> >
				部門別依比例分攤				
				<br>
				園區別&nbsp;&nbsp;&nbsp;&nbsp;
				[<input type="text" class="field_RO" name="oldDeprPark" value="<%=obj.getOldDeprPark() %>" size="10" maxlength="10">]
				[<input type="text" class="field_RO" name="oldDeprParkName" value="<%=obj.getOldDeprParkName() %>" size="20">]
				&nbsp;&nbsp;
				部門別
				[<input type="text" class="field_RO" name="oldDeprUnit" value="<%=obj.getOldDeprUnit() %>" size="10" maxlength="10">]
				[<input type="text" class="field_RO" name="oldDeprUnitName" value="<%=obj.getOldDeprUnitName() %>" size="20">]				
				部門別單位
				[<input type="text" class="field_RO" name="oldDeprUnit1" value="<%=obj.getOldDeprUnit1() %>" size="10" maxlength="10">]
				[<input type="text" class="field_RO" name="oldDeprUnit1Name" value="<%=obj.getOldDeprUnit1Name() %>" size="20">]			
				<br>
				會計科目
				[<input type="text" class="field_RO" name="oldDeprAccounts" value="<%=obj.getOldDeprAccounts() %>" size="10" maxlength="10">]
				[<input type="text" class="field_RO" name="oldDeprAccountsName" value="<%=obj.getOldDeprAccountsName() %>" size="20">]
				<br>
				殘值
				[<input class="field_RO" type="text" id="oldScrapValue" name="oldScrapValue" value="<%=obj.getOldScrapValue() %>" size="15" maxlength="15">]
				&nbsp;&nbsp;
				應攤提折舊總額
				[<input class="field_RO" type="text" name="oldDeprAmount" size="16" maxlength="15" value="<%=obj.getOldDeprAmount() %>" >]
				<br>
				累計折舊
				[<input class="field_RO" type="text" name="oldAccumDepr" size="16" maxlength="15" value="<%=obj.getOldAccumDepr() %>">]
				&nbsp;&nbsp;
				攤提壽月
				[<input class="field_RO" type="text" name="oldApportionMonth" size="5" maxlength="4" value="<%=obj.getOldApportionMonth() %>" >]
				月提折舊金額
				[<input class="field_RO" type="text" name="oldMonthDepr" size="16"  maxlength="15" value="<%=obj.getOldMonthDepr() %>">]
				<br>
				月提折舊金額(最後一個月)
				[<input class="field_RO" type="text" name="oldMonthDepr1" size="16" maxlength="15" value="<%=obj.getOldMonthDepr1() %>">]
				攤提年限截止年月
				[<input class="field_RO" type="text" name="oldApportionEndYM" size="16" maxlength="15" value="<%=obj.getOldApportionEndYM() %>">]
			</td>
		</tr>		
		<tr>
			<td class="td_form">調整後折舊資料：</td>
			<td class="td_form_white" colspan="3">
				<font color="red">*</font>折舊方法
				<select name="newDeprMethod" class="field_RO" type="select">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getNewDeprMethod())%>
				</select>
				&nbsp;&nbsp;
				<input class="field" type="checkbox" name="newBuildFeeCB" size="1" maxlength="1" value="Y" onClick="checkRadioBox();" <%=("Y".equals(obj.getNewBuildFeeCB())?"checked":"")%> >
				屬公共設施建設費
				&nbsp;&nbsp;
				<input class="field" type="checkbox" name="newDeprUnitCB" size="1" maxlength="1" value="Y" onClick="checkNewDeprUnitCB();checkRadioBox();" <%=("Y".equals(obj.getNewDeprUnitCB())?"checked":"")%> >
				部門別依比例分攤
				<br>
				園區別
				<select class="field" type="select" name="newDeprPark">
				　　		<%=util.View.getOption("select deprparkno, deprparkno+'-'+deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getNewDeprPark())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				部門別
				<select class="field" type="select" name="newDeprUnit" onchange="queryDeprUnitData();">
				　　<%=util.View.getOption("select deprunitno, deprunitno+'-'+deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ",obj.getNewDeprUnit())%>
				</select>
				<input type="hidden" class="field" name="newDeprUnittemp" value="">
				&nbsp;&nbsp;&nbsp;
				部門別單位
				<select class="field" type="select" name="newDeprUnit1">
					<%=util.View.getOption("select deprunit1no, deprunit1no+'-'+deprunit1name from SYSCA_DEPRUNIT1 where enterorg='" + obj.getEnterOrg() + "' order by deprunit1no", obj.getNewDeprUnit1())%>
			    </select>
			
				&nbsp;&nbsp;&nbsp;
				會計科目
				<select class="field" type="select" name="newDeprAccounts">
				　　<%=util.View.getOption("select depraccountsno, depraccountsno+'-'+depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ",obj.getNewDeprAccounts())%>
				</select>
				<br>
				殘值
				[<input class="field_RO" type="text" name="newScrapValue" value="<%=obj.getNewScrapValue() %>" size="16" maxlength="15">]
				&nbsp;&nbsp;
				應攤提折舊總額
				[<input class="field_RO" type="text" name="newDeprAmount" size="16" maxlength="15" value="<%=obj.getNewDeprAmount() %>" >]
				<br>
				累計折舊
				[<input class="field_RO" type="text" name="newAccumDepr" size="16" maxlength="15" value="<%=obj.getNewAccumDepr() %>">]
				&nbsp;&nbsp;
				攤提壽月
				<input class="field" type="text" name="newApportionMonth" size="5" maxlength="4" value="<%=obj.getNewApportionMonth() %>" onChange="changenewMonthDepr();">
				月提折舊金額
				[<input class="field_RO" type="text" name="newMonthDepr" size="16" maxlength="15" value="<%=obj.getNewMonthDepr() %>">]
				<br>
				月提折舊金額(最後一個月)
				[<input class="field_RO" type="text" name="newMonthDepr1" size="16" maxlength="15" value="<%=obj.getNewMonthDepr1() %>">]
				攤提年限截止年月
				[<input class="field_RO" type="text" name="newApportionEndYM" size="16" maxlength="15" value="<%=obj.getNewApportionEndYM() %>">]
				
			</td>
		</tr>
		<tr>
			<td class="td_form">備註：</td>
			<td class="td_form_white"><textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
			<td class="td_form">異動人員/日期：</td>
			<td class="td_form_white">
				[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
			</td>
		</tr>	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;">
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="UpdateMainFile" value="更新主檔" onClick="onClickUpdateMainFile()">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">攤提壽月(原)</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">攤提壽月(新)</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">部門別(原)</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">部門別(新)</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">會計科目(原)</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">會計科目(新)</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">依比例分攤(原)</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">依比例分攤(新)</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,true,false,false,false,false,false,false,false,false,false,true,true};
	boolean displayArray[] = {false,true,true,true,true,true,true,true,true,true,true,true,true,false,false};
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
		form1.updateMaster.checked = false;
	case "update":
		setFormItem("UpdateMainFile","R");
		checkRadioBox();
		break;	
	}
}
</script>
</body>
</html>
