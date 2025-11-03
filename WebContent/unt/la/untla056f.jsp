<!--
程式目的：土地合併分割重測重劃作業－減損單明細
程式代號：untla056f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA056F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA056F)obj.queryOne();	
}else if ("insert".equals(obj.getState())) {
	obj.insert();
	
}else if ("update".equals(obj.getState())) {
	obj.update();
	
}else if ("delete".equals(obj.getState())) {
	obj.delete();	
	obj.clearAllDataForView();
	obj.setPropertyNo("");
	obj.setPropertyNo_Reduce("");
	obj.setSerialNo("");
	obj.setSerialNo_Reduce("");
	
}else if ("insertError".equals(obj.getState()) || "updateError".equals(obj.getState()) || "deleteError".equals(obj.getState())){
	obj.clearAllDataForView();
	obj.setPropertyNo("");
	obj.setPropertyNo_Reduce("");
	obj.setSerialNo("");
	obj.setSerialNo_Reduce("");
	
}else if ("insertBatchSuccess".equals(obj.getState())) {
	obj.execUpdateto_Untla_ReduceDetail_byBatch();
	obj.setState("init");
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if("init".equals(obj.getState())) {
	
}else{
	obj.setQueryAllFlag("true");
}

if ("true".equals(obj.getQueryAllFlag())){
	obj.clearAllDataForView();
	obj.setCaseNo(obj.getCaseNo_Reduce());
	objList = obj.queryAll();
	
	if(!objList.isEmpty() && "".equals(obj.getPropertyNo()) && "".equals(obj.getSerialNo())){
		obj.setEnterOrg(((String[])objList.get(0))[0]);
		obj.setOwnership(((String[])objList.get(0))[3]);
		obj.setDifferenceKind(((String[])objList.get(0))[5]);
		obj.setPropertyNo(((String[])objList.get(0))[6]);
		obj.setSerialNo(((String[])objList.get(0))[7]);
	}
	
	obj = (unt.la.UNTLA056F)obj.queryOne();

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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
		new Array("verify","N")
	);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){

		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.signNo1,"土地標示－縣市");
		alertStr += checkEmpty(form1.signNo2,"土地標示－鄉鎮市區");
		alertStr += checkEmpty(form1.signNo3,"土地標示－地段");
		alertStr += checkEmpty(form1.signNo4,"土地標示－地號母號");
		alertStr += checkEmpty(form1.signNo5,"土地標示－地號子號");
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.reduceCause,"減損原因");
		//alertStr += checkDate(form1.reduceDate,"減損日期");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");
		alertStr += checkEmpty(form1.valuable,"珍貴財產註記");
		//alertStr += checkEmpty(form1.taxCredit,"抵繳遺產稅");
		alertStr += checkEmpty(form1.area,"整筆面積(㎡)");
		alertStr += checkFloat(form1.area,"整筆面積(㎡)",9,2);
		alertStr += checkEmpty(form1.holdNume,"權利範圍－分子");
		alertStr += checkInt(form1.holdNume,"權利範圍－分子");
		alertStr += checkEmpty(form1.holdDeno,"權利範圍－分母");
		alertStr += checkInt(form1.holdDeno,"權利範圍－分母");
		alertStr += checkEmpty(form1.holdArea,"權利範圍面積(㎡)");
		alertStr += checkFloat(form1.holdArea,"權利範圍面積(㎡)",9,2);
		alertStr += checkEmpty(form1.netUnit,"帳面金額－單價");
		alertStr += checkFloat(form1.netUnit,"帳面金額－單價",13,2);
		alertStr += checkEmpty(form1.netValue,"帳面金額－總價");
		alertStr += checkInt(form1.netValue,"帳面金額－總價");
		alertStr += checkDate(form1.bulletinDate,"當期公告日期");
		alertStr += checkLen(form1.notes,"備註",250);
		alertStr += checkDate(form1.transferDate,"交接日期");

		//若「減損原因」為「撥出(移交)、贈與」，需控制「接管機關、交接日期」必須有資料，
		//		    否則「接管機關、交接日期」不可有資料。
		if(form1.reduceCause.value=='201' || form1.reduceCause.value=='206'){
			alertStr += checkEmpty(form1.newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.transferDate,"交接日期");			
		}else{
			alertStr += checkMustEmpty(form1.newEnterOrg,"接管機關");
			alertStr += checkMustEmpty(form1.transferDate,"交接日期");
		}
	}
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	form1.signNo.value = form1.signNo3.value + form1.signNo4.value + form1.signNo5.value;
	beforeSubmit();
}
function queryOne(enterOrg,caseNo,ownership,differenceKind,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo_Reduce.value=caseNo;
	form1.caseNo.value=caseNo;
	form1.differenceKind.value=differenceKind;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;

	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	//getQueryCaseData();
	
	setDisplayItem("spanQueryAll,spanNextInsert", "H");
	setFormItem("insert,update,delete,batchInsertBut","R");

	//若沒有選擇任何資料
	if(form1.propertyNo.value==''){				setFormItem("update,delete","R");
												setFormItem("insert,batchInsertBut","O");
	}else{										setFormItem("insert,update,delete,batchInsertBut","O");}
	
	//非登入者所屬機關所登錄的資料
	if(form1.enterorgNotConfirm.value=="Y"){	setFormItem("insert,update,delete,batchInsertBut","R");}
	//查出的資料若其「案件資料」已入帳
	if(form1.alreadyVerify.value=="Y"){			setFormItem("insert,update,delete,batchInsertBut","R");}
	//若尚未新增減損單案件
	if(form1.caseNo_Reduce.value==''){			setFormItem("insert,update,delete,batchInsertBut","R");}

}


function checkURL(surl){
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		columnTrim(form1.caseNo);
			
		form1.action=surl+"?initDtl=Y";

		form1.state.value="queryAll";
		
		beforeSubmit();
		form1.submit();
	}
}


function getQueryCaseData(){
	var comment={};	
	comment.enterOrg = form1.enterOrg.value;	
	comment.ownership = form1.ownership.value;
	comment.caseNo = form1.caseNo_Merge.value;
	comment.differencekind = form1.differenceKind.value;
	
	//傳送JSON
	$.post('untla056f_ajax.jsp',
		comment,
		function(data){
			//將回傳資料寫入		
			data=eval('('+data+')');

			$("select[name='reduceCause']").val(data['cause']);
			$("input[name='reduceCause1']").val(data['cause1']);
			
		});
}

function getQueryData_From_UNTLA_ReduceDetail_By_PropertyNo(){
	var comment={};
	var queryflag='';
	
	if(form1.propertyNo_Reduce.value!='' && form1.serialNo_Reduce.value!=''){
		comment.enterOrg = form1.enterOrg.value;	
		comment.ownership = form1.ownership.value;
		comment.differencekind = form1.differenceKind.value;
		comment.propertyNo = form1.propertyNo_Reduce.value;
		comment.serialNo = form1.serialNo_Reduce.value;
		queryflag='propertyNo';
				
	}else{
		if(form1.signNo3.value!='' && form1.signNo5.value!=''){
			form1.signNo.value = form1.signNo3.value + form1.signNo4.value + form1.signNo5.value;
			comment.enterOrg = form1.enterOrg.value;	
			comment.ownership = form1.ownership.value;
			comment.differencekind = form1.differenceKind.value;
			comment.signNo = form1.signNo.value;	
			queryflag='signNo';	
		} 
	}

	if(queryflag!=''){
		//傳送JSON
		$.post('untla056f_ajax_getDataFromPropertyNo.jsp',
			comment,
			function(data){
				//將回傳資料寫入		
				data=eval('('+data+')');

				if(data['retureStr']=='NoResultFind'){
					alert('資料不存在，請重新輸入!!');
					
					$("input[name='propertyNo']").val('');
					$("input[name='propertyName']").val('');
					$("input[name='serialNo']").val('');					
					$("select[name='propertyKind']").val('');
					$("input[name='propertyName1']").val('');						
					$("input[name='signNo']").val('');
					$("select[name='signNo1']").val('');
					$("select[name='signNo2']").val('');
					$("select[name='signNo3']").val('');
					$("input[name='signNo4']").val('');
					$("input[name='signNo5']").val('');					
					$("select[name='fundType']").val('');
					$("select[name='valuable']").val('');
					$("select[name='taxCredit']").val('');
					$("input[name='area']").val('');
					$("select[name='useState1']").val('');
					$("input[name='holdNume']").val('');
					$("input[name='holdDeno']").val('');
					$("input[name='holdArea']").val('');
					$("input[name='accountingTitle']").val('');
					$("input[name='bookUnit']").val('');
					$("input[name='bookValue']").val('');
					$("input[name='netUnit']").val('');
					$("input[name='netValue']").val('');
					$("select[name='useSeparate']").val('');
					$("select[name='useKind']").val('');
					$("input[name='proofDoc']").val('');
					$("select[name='field']").val('');
					$("input[name='sourceDate']").val('');
					$("input[name='oldPropertyNo']").val('');
					$("input[name='oldSerialNo']").val('');
					$("input[name='bulletinDate']").val('');
					$("input[name='valueUnit']").val('');
						
				}else{	
							
					if(queryflag=='signNo'){						
						$("input[name='propertyNo']").val(data['propertyno']);
						$("input[name='serialNo']").val(data['serialno']);
						$("input[name='propertyNo_Reduce']").val(data['propertyno']);
						$("input[name='serialNo_Reduce']").val(data['serialno']);
					}
					$("select[name='propertyKind']").val(data['propertykind']);
					$("input[name='propertyName']").val(data['propertyname']);
					$("input[name='propertyName1']").val(data['propertyname1']);
	
					if(queryflag=='propertyNo'){
						$("input[name='signNo']").val(data['signno']);
						$("select[name='signNo1']").val(data['signno1']);
						changeSignNo1('signNo1','signNo2','signNo3',data['signno2']);
						changeSignNo2('signNo1','signNo2','signNo3',data['signno3']);
						$("input[name='signNo4']").val(data['signno4']);
						$("input[name='signNo5']").val(data['signno5']);
					}
					$("select[name='fundType']").val(data['fundtype']);
					$("select[name='valuable']").val(data['valuable']);
					$("select[name='taxCredit']").val(data['taxcredit']);
					$("input[name='area']").val(data['area']);
					$("select[name='useState1']").val(data['usestate1']);
					$("input[name='holdNume']").val(data['holdnume']);
					$("input[name='holdDeno']").val(data['holddeno']);
					$("input[name='holdArea']").val(data['holdarea']);			
					$("input[name='accountingTitle']").val(data['accountingtitle']);
					$("input[name='bookUnit']").val(data['bookunit']);
					$("input[name='bookValue']").val(data['bookvalue']);
					$("input[name='netUnit']").val(data['netunit']);
					$("input[name='netValue']").val(data['netvalue']);
					$("select[name='useSeparate']").val(data['useseparate']);
					$("select[name='useKind']").val(data['usekind']);
					$("input[name='proofDoc']").val(data['proofdoc']);
					$("select[name='field']").val(data['field']);
					$("input[name='sourceDate']").val(data['sourcedate']);
					$("input[name='oldPropertyNo']").val(data['oldpropertyno']);
					$("input[name='oldSerialNo']").val(data['oldserialno']);
					$("input[name='bulletinDate']").val(data['bulletindate']);
					$("input[name='valueUnit']").val(data['valueunit']);

					$("input[name='propertyNo']").val($("input[name='propertyNo_Reduce']").val());
					$("input[name='serialNo']").val($("input[name='serialNo_Reduce']").val());
					
					$("select[name='keepUnit']").val(data['keepunit']);
					$("select[name='keeper']").val(data['keeper']);
					$("select[name='useUnit']").val(data['useunit']);
					$("select[name='userNo']").val(data['userno']);
					$("input[name='userNote']").val(data['usernote']);
					$("input[name='place1']").val(data['place1']);
					$("input[name='place1Name']").val(data['place1name']);
					$("input[name='place']").val(data['place']);
					$("input[name='buyDate']").val(data['buydate']);

					getProperty('propertyNo_Reduce','propertyNo_ReduceName','1');

				}
			});
	}
}



function gountla014f(){
	var prop="";
	var iwidth=screen.availWidth;
	var iheight=screen.availHeight*2/3;
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
	var differencekind = form1.differenceKind.value;
	var reduceDate = form1.reduceDate.value;
	var verify = form1.verify.value;
	var cause = form1.reduceCause.value;
	beforeSubmit();
	returnWindow=window.open("untla014f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_differencekind="+differencekind+"&q_reduceDate="+reduceDate+"&q_verify="+verify+"&q_cause="+cause+"&q_jsp=untla056f","aha",prop);	
}

function batchInsertBtn_event(){
	window.open("../../unt/ch/untch004f03.jsp?caseNo="+form1.caseNo.value+"&enterOrg="+form1.enterOrg.value+"&ownership="+form1.ownership.value+"&differenceKind="+form1.differenceKind.value+"&cause="+form1.reduceCause.value+"&cause1="+form1.reduceCause1.value+"&dataSource=untla056f");
}

function changePropertyNo(){
	
}
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla054f.jsp');"><font color="red">*</font>案件資料</a></td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla055f.jsp');"><font color="red">*</font>減損單</a></td>
		<td ID=t2 CLASS="tab_border1" HEIGHT="25">*減損單明細</td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla057f.jsp');"><font color="red">*</font>增加單</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla058f.jsp');"><font color="red">*</font>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2">增加單管理資料</td>
		<td ID=t7 CLASS="tab_border2">增加單地上物資料</td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla066f.jsp');">調整前後對應資料</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>		
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">

<input class="field_QRO" type="hidden" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">

<input class="field_QRO" type="hidden" name="caseNo_Add" value="<%=obj.getCaseNo_Add()%>">
<input class="field_QRO" type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
<input class="field_QRO" type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">

<input class="field_QRO" type="hidden" name="caseNo_Add" value="<%=obj.getCaseNo_Add()%>">
<input class="field_QRO" type="hidden" name="propertyNo_Add" value="<%=obj.getPropertyNo_Add()%>">
<input class="field_QRO" type="hidden" name="serialNo_Add" value="<%=obj.getSerialNo_Add()%>">

<input class="field_QRO" type="hidden" name="caseName" size="30" maxlength="25">

<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">

<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA054Q",obj);%>
	<jsp:include page="untla054q.jsp" />
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopProperty("field_P","propertyNo_Reduce",obj.getPropertyNo_Reduce(),obj.getProperty_ReduceName(),"1")%>
			分號： <input class="field_P" type="text" name="serialNo_Reduce" size="7" maxlength="7" value="<%=obj.getSerialNo_Reduce()%>" onChange="getFrontZero(this.name, 7);getQueryData_From_UNTLA_ReduceDetail_By_PropertyNo();">
			<br>　　
			別名：　　[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]
			<br>
			原財產編號：　 [<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]　　　　&nbsp;&nbsp;&nbsp;
			原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]			
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white" width="50%" colspan="2">
			<input type="hidden" name="signNo" value="<%=obj.getSignNo()%>">
			<select class="field_RO" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>
			<select class="field_RO" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>		
			<select class="field_RO" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>
		</td>
		<td class="td_form_white">
			地號：			　
			[<input class="field_RO" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onChange="getFrontZero(this.name, 4);">]
			&nbsp;-&nbsp;
			[<input class="field_RO" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onChange="getFrontZero(this.name, 4);getQueryData_From_UNTLA_ReduceDetail_By_PropertyNo();">]		
		</td>
	</tr>		
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan="3">
			日期：
			[<input class="field_QRO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]&nbsp;&nbsp;　
			入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>&nbsp;&nbsp;&nbsp;　
			<br>
          	<font color="red">*</font>
          	減損原因：&nbsp;　
			<select class="field_QRO" type="select" name="reduceCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' ", obj.getReduceCause())%>
			</select>
			&nbsp;&nbsp;
			其他說明：　&nbsp;
			[<input class="field_QRO" type="text" name="reduceCause1" size="20" maxlength="20" value="<%=obj.getReduceCause1()%>">]
			<br>
			接管機關：
			<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName())%>　　　　&nbsp;&nbsp;
			交接日期：
			<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
			帳面單價：[<input class="field_RO" type="text" name="netUnit" size="13" maxlength="16" value="<%=obj.getNetUnit()%>">]&nbsp;&nbsp;			　　　　
			帳面總價：[<input class="field_RO" type="text" name="netValue" size="15" maxlength="15" value="<%=obj.getNetValue()%>">]
			<input class="field_RO" type="hidden" name="bookUnit" size="13" maxlength="16" value="<%=obj.getBookUnit()%>">			　　　　
			<input class="field_RO" type="hidden" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">
			<br>
			帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">&nbsp;&nbsp;　　
			會計科目：[<input class="field_RO" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">]
			<br>
			當期公告日期：<input class="field" type="text" name="bulletinDate" size="7" maxlength="7" value="<%=obj.getBulletinDate()%>">&nbsp;&nbsp;　　　　
			當期公告地價：<input class="field" type="text" name="valueUnit" size=""13,0"" maxlength=""13,0"" value="<%=obj.getValueUnit()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="5%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;
			權屬： 
			<select class="field_QRO" type="select" name="ownership" id="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>	
			&nbsp;&nbsp;&nbsp;&nbsp;		
			<!--  電腦單號：-->
			<input class="field_QRO" type="hidden" name="caseNo_Reduce" size="10" maxlength="10" value="<%=obj.getCaseNo_Reduce()%>">			
		</td>
	</tr>
	<tr>
		<td class="td_form">案號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="caseNo_Merge" size="10" maxlength="10" value="<%=obj.getCaseNo_Merge()%>">]			　　　　　&nbsp;&nbsp;&nbsp;
			批次： 
			[<input class="field_QRO" type="text" name="mergeDivisionBatch" size="10" maxlength="10" value="<%=obj.getMergeDivisionBatch()%>">]					
		</td>
	</tr>
	<tr>
		<td class="td_form">財產區分別：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>
			&nbsp;　　　&nbsp;
			序號：
			[<input class="field_RO" type="text" name="caseSerialNo" size="10" maxlength="50" value="<%=obj.getCaseSerialNo() %>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">工程編號：</td>
		<td colspan="3" class="td_form_white">
			[<input class="field_QRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo() %>" onBlur="">]
			[<input class="field_QRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName() %>">]
       	</td>
	</tr>	
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;
			購置日期：
			<%=util.View.getPopCalndar("field_RO","buyDate",obj.getBuyDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;
			財產取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]			
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產性質：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View._getSelectHTML("field_RO", "propertyKind", obj.getPropertyKind(),"PKD") %>
			基金財產：
			<%=util.View._getSelectHTML_withEnterOrg("field_RO", "fundType", obj.getFundType(),"FUD", obj.getEnterOrg()) %>
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
		<td class="td_form">地上物情形：</td>
		<td class="td_form_white" colspan="3">
			地上物情形：
			<select class="field_RO" type="select" name="useState1">
				<%=util.View.getTextOption("01;空置;02;建物;03;農作;04;其他;",obj.getUseState1())%>
			</select>&nbsp;&nbsp;　　　　　　&nbsp;
			地目：
			<select class="field_RO" type="select" name="field">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
			</select>
			<br>
			權狀字號：[<input class="field_RO" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">]			
		</td>
	</tr>
	<tr>
		<td class="td_form">面積：</td>
		<td class="td_form_white" colspan="3">
			整筆面積(㎡)：	
			[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">]
			<br>
			權利分子：[<input class="field_RO" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>">]&nbsp;&nbsp;/&nbsp;
			權利分母：[<input class="field_RO" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>">]
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">使用分區：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="useSeparate">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
			</select>&nbsp;			　　　　　　
			編定使用種類：
			<select class="field_RO" type="select" name="useKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getUseKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">移動資料：</td>
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
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" +Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getUseUnit())%>
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
			[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
			<br>		
			存置地點說明：
			[<input class="field_RO" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace() %>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white" width="30%" >
			<textarea class="field_RO" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form"style="display:none;">異動人員/日期：</td>
		<td class="td_form_white"style="display:none;">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
		<input class="toolbar_default" type="button" followPK="false" name="batchInsertBtn" value="現有資料明細新增" onClick="batchInsertBtn_event();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">地上物情形</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">權利範圍面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">帳面金額總價</a></th>
		
	</thead>
	<tbody id="listTBODY">
	<%
		boolean primaryArray[] = {true,true,false,true,false,true,true,true,false,false,false,false,false};
		boolean displayArray[] = {false,false,true,false,true,false,true,true,true,true,true,true,true};
		out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



