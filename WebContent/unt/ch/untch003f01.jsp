<!--
程式目的：財產增減值單維護
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH003F01">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%
obj.setQuerySelect("proof");

String table = "UNTLA_ADJUSTPROOF";
obj.setReVerify("N");

String isEngineering = Common.checkGet(request.getParameter("isEngineering"));

if("Y".equals(isEngineering) && "init".equals(obj.getState())){
	obj.setQ_verify("N");
	obj.setEnterOrg(user.getOrganID());
	obj.setQ_enterOrg(user.getOrganID());
	obj.setEngineeringNo(obj.getQ_engineeringNo());
	obj.setEngineeringNoName(obj.getQ_engineeringNoName());
	//objList = obj.queryAll();
	obj.setQueryAllFlag("true");
}

String eg_ownership = Common.checkGet(request.getParameter("eg_ownership"));
String eg_differenceKind = Common.checkGet(request.getParameter("eg_differenceKind"));

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())) {
		obj.setQueryAllFlag("true");
	}
}else if ("queryOne".equals(obj.getState())) {
	obj._execQueryOne();
	obj.setQueryone_enterOrg(obj.getEnterOrg());
	obj.setQueryone_ownership(obj.getOwnership());
	obj.setQueryone_caseNo(obj.getCaseNo());
	obj.setQueryone_differenceKind(obj.getDifferenceKind());
}else if ("insert".equals(obj.getState())) {
	obj._execInsert(table);
	if ("insertSuccess".equals(obj.getState())) {
		obj.setQueryAllFlag("true");
		obj.setI_enterOrg(obj.getEnterOrg());
		obj.setI_ownership(obj.getOwnership());
		obj.setI_caseNo(obj.getCaseNo());
		obj.setI_differenceKind(obj.getDifferenceKind());
	}
}else if ("update".equals(obj.getState())) {
	String verify = obj.getVerify();
	String oldverify = obj.queryOldVerify();
	if ("N".equals(oldverify) && "Y".equals(verify) && obj.checkProofVerifyYN("Y")) {
		// 確保 只有入帳 才跑這邊
		if(obj.checkUpdateError() && obj.checkMonthDepr2()){
			obj.updateNewData();		//取的最新資料
			if(obj.checkDetailCanVerify("Y")) {
				obj._execUpdate(table);		//修改增減值單相關欄位
				obj._updateBUILDING();		//修改建物主檔
				obj._updateATTACHMENT();	//修改土改主檔
				obj._updateMOVABLE();		//修改動產主檔
				obj._updateManage();        //修改土地及建物管理資料
				obj.updateRT();						// 修改權利主檔的折舊欄位
				obj.updateUntchDeprpercent("doVerify");	// 修改折舊比例分攤
				obj.setErrorMsg("修改完成，已取得最新資料");			
				if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
				
				obj.checkMonthDepr1();
			}
		}
	} else if (oldverify.equals(verify)) {
		// 非入帳 的 單據資料修改
		obj._execUpdate(table);
	}
}else if ("delete".equals(obj.getState())) {
	obj._execDelete(table);
	obj.setCaseNo("");
	obj.setOwnership("");
	obj.setDifferenceKind("");
	if ("deleteSuccess".equals(obj.getState())) {
		obj.setQueryAllFlag("true");
	}
}else if ("approveRe".equals(obj.getState())) {
	obj.setReVerify("Y");
	if(obj.checReVerifyError() && obj.checkDetailCanVerify("N")){
		obj.setVerify("N");
		obj._execUpdate(table);
		obj._updateBUILDING();
		obj._updateATTACHMENT();
		obj._updateMOVABLE();
		obj._updateManage();        //修改土地及建物管理資料
		obj.updateUntchDeprpercent("reVerify");	// 修改折舊比例分攤
		obj.setAdjustDate("");
		if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
	}
	obj.setReVerify("");
}else if ("updateNewData".equals(obj.getState())) {
	obj.updateNewData();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	
	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
			 
		}else{ 
			if("".equals(obj.getQueryone_caseNo())){
				obj.setEnterOrg(((String[])objList.get(0))[0]);
				obj.setOwnership(((String[])objList.get(0))[1]);
				obj.setDifferenceKind(((String[])objList.get(0))[3]);
				obj.setCaseNo(((String[])objList.get(0))[5]);
			}
			obj._execQueryOne();
		}
	}
}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._CH_ADJUST, 1);

boolean checkDataCount = obj.checkDataCountIsZero(obj.getEnterOrg(), obj.getCaseNo());
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

insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","<%=new unt.ch.UNTCH_Tools()._getDefaultValue(user.getOrganID(), "ownership")%>"),
	new Array("writeUnit","<%=user.getUnitID()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("proofDoc","<%=new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D3")%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("verify","N")	,
	new Array("engineeringNo","<%=obj.getQ_engineeringNo()%>"),
	new Array("engineeringNoName","<%=obj.getQ_engineeringNoName()%>")
);



function getVerifyDate() {
	if (form1.verify.value=="Y") {
		if (form1.adjustDate.value=="") form1.adjustDate.value='<%=util.Datetime.getYYYMMDD()%>';
		if (form1.summonsDate.value=="") form1.summonsDate.value='<%=util.Datetime.getYYYMMDD()%>';
		setFormItem("adjustDate,btn_adjustDate,summonsDate,btn_summonsDate,summonsNo","O");
	}else{
		form1.adjustDate.value='';
		form1.summonsDate.value='';
		form1.summonsNo.value = "";
		setFormItem("adjustDate,btn_adjustDate,summonsDate,btn_summonsDate,summonsNo","R");
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untch003f01.jsp";
		} else {
			form1.action = "untch003f02.jsp";
		}
	} else if (form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");		
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"增加單編號－字");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkDate(form1.adjustDate,"入帳日期");

		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
			
		alertStr += checkLen(form1.notes, "備註", 250);
		
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.adjustDate,"若要入帳，入帳日期");

			if (alertStr.length == 0) {
				var aObj = ajaxQuery("ajax/unt/ch/Untch003f01Ajax.jsp", 
										{"action" : "checkIsNewData", 
										  "enterOrg" : form1.enterOrg.value,
										  "caseNo" : form1.caseNo.value
										  }, false);
				if (aObj.isSuccess && aObj.data !== true) {
					alertStr += "增減值明細資料非最新帳面淨值，請先執行[帶入最新資料]\n"
				}
			}
		}
		form1.action = "untch003f01.jsp";
	}

	if(alertStr.length!=0){ alert(alertStr); return false; }
	//if (form1.writeDate.value.substring(0, 5) != form1.adjustDate.value.substring(0, 5) && form1.verify.value == 'Y') {
		//if (window.confirm('增減值年月與填單年月不為同一年月，是否取得增減值最新資訊？') == true) {
			//return true;
		//} else {
			//return false;
		//}
	//}
	
	beforeSubmit();	
}

function queryOne(enterOrg,ownership,differenceKind,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function clickApproveRe(){
	if (confirm("是否要回復入帳?")) {
		form1.adjustDate.value='';
		form1.state.value="approveRe";
		beforeSubmit();
		form1.submit();
	}
}

function updateNewData() {
	form1.state.value="updateNewData";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else{
		form1.state.value="queryAll";
		form1.action = surl;
		form1.mainPage.value=form1.currentPage.value;
		form1.mainEnterOrg.value=form1.queryone_enterOrg.value;
		form1.mainOwnerShip.value=form1.queryone_ownership.value;
		form1.mainCaseNo.value=form1.queryone_caseNo.value;
		form1.mainDifferenceKind.value=form1.queryone_differenceKind.value;
		form1.currentPage.value=1;			
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	initQ_Form("proof");
	
	changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo2()%>');
	changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo3()%>');
	changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo2()%>');
	changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo3()%>');
	
	if(form1.verify.value=='Y'){	setFormItem("update,delete", "R");		
	}else{							setFormItem("update,delete", "O");
	}
	
	setFormItem("printAddProof", "O");
	setFormItem("print02", "O");
	
	if(<%=checkDataCount%>){
		$("select[name='verify']").attr('class','field_P');
	}else{
		$("select[name='verify']").attr('class','field');
	}
	
	form1.adjustDateTemp.value = form1.adjustDate.value;

	if ('<%=user.getRoleid()%>' == '3'){
		if (form1.verify.value == 'Y') {
			form1.verifyRe.disabled = false;
			setFormItem("verifyRe", "O");
			form1.NewData.disabled = true;
			setFormItem("NewData", "R");
		} else {
			form1.verifyRe.disabled = true;
			setFormItem("verifyRe", "R");
			form1.NewData.disabled = false;
			setFormItem("NewData", "O");
		}
	}else if (form1.isAdminManager.value == 'Y'){
		if (form1.verify.value == 'Y') {
			form1.verifyRe.disabled = false;
			setFormItem("verifyRe", "O");
			form1.NewData.disabled = true;
			setFormItem("NewData", "R");
		} else {
			form1.verifyRe.disabled = true;
			setFormItem("verifyRe", "R");
			form1.NewData.disabled = false;
			setFormItem("NewData", "O");
		}
	}else{
		form1.verifyRe.disabled = true;
		setFormItem("verifyRe", "R");
		form1.NewData.disabled = true;
		setFormItem("NewData", "R");
	}
	//此行要對照primaryArray所設定的pk值為何
	listContainerRowClick(form1.enterOrg.value + form1.ownership.value + form1.differenceKind.value + form1.caseNo.value);

	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 財產管理 > > 財產增減值單維護";
	
	if (form1.bookvalueFlag.value == "Y") {
		document.getElementById("hint").innerHTML = "單據內包含減值後原值為0之項目，請檢視是否變更為辦理減損。";
	}
	
	//問題單2347，groupID非sys01身分別的使用者不顯示刪除按鈕
	if (form1.groupID.value != 'sys01' ) {
		setDisplayItem('delete','H');
	}
}

function loadUntrp004r(){
	if (checkIsNewData()) {
		var url = "../rp/untrp004r.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value
			+ "&q_differenceKind=" + form1.differenceKind.value		
			+ "&q_caseNoS=" + form1.caseNo.value
			+ "&q_caseNoE="+form1.caseNo.value
			+ "&q_proofYear=" + form1.proofYear.value
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_proofNoS=" + form1.proofNo.value
			+ "&q_proofNoE=" + form1.proofNo.value
			+ "&q_kind=4";
		window.open(url);
	}
}

function loadUntrp005r(){
	var url = "../rp/untrp005r2.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value
			+ "&q_differenceKind=" + form1.differenceKind.value		
			+ "&q_caseNoS=" + form1.caseNo.value
			+ "&q_caseNoE="+form1.caseNo.value
			+ "&q_proofYear=" + form1.proofYear.value
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_proofNoS=" + form1.proofNo.value
			+ "&q_proofNoE=" + form1.proofNo.value
			+ "&q_kind=4";
	window.open(url);
}

	function checkIsNewData() {
		if(form1.verify.value=="N"){
			var aObj = ajaxQuery("ajax/unt/ch/Untch003f01Ajax.jsp", 
												{"action" : "checkIsNewData", 
												  "enterOrg" : form1.enterOrg.value,
												  "caseNo" : form1.caseNo.value
												  }, false);
			if (aObj.isSuccess && aObj.data === true) {
				return true;
			} else {
				if (aObj.isSuccess && aObj.data === false) {
					alert("增減值明細資料非最新帳面淨值，請先執行[帶入最新資料]");
				}
				return false;
			}
		} else {
			return true;
		}
	}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
	<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="objPath" >
	<input type="hidden" name="bookvalueFlag" value="<%=obj.getBookvalueFlag()%>">

</td></tr></table>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1000px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<% request.setAttribute("UNTCH003Q",obj);%>
	<jsp:include page="untch003q.jsp" />
</div>

<!--頁籤區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0 valign="top">
	<%=tabs %>
</table>

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form"width="5%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;
			<font color="red">*</font>權屬：
			<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;
			<font color="red">*</font>財產區分別：
			<%=util.View._getSelectHTML("field_P", "differenceKind", obj.getDifferenceKind(),"DFK") %>
			<input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
			<input class="field_QRO" type="hidden" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">			
		</td>
	</tr>		
	<tr>
		<td class="td_form" >填單日期：</td>
		<td class="td_form_white" colspan="3">		        	
			<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
			&nbsp;&nbsp;                
			填造單位：
			<select class="field" type="select" name="writeUnit" >
				<%=util.View.getOption("select  unitno, unitname from UNTMP_KEEPUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno", obj.getWriteUnit())%>
			</select>
			<input class="field" type="button" name="btn_writeUnit" onclick="popUnitNo(form1.enterOrg.value,'writeUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;
			<!-- 
			財產管理單位編號：
			 --> 
            <input class="field" type="hidden" name="manageNo" size="15" maxlength="10" value="<%=obj.getManageNo()%>">
		</td>
	</tr>
	
	<tr>
		<td class="td_form"><font color="red">*</font>增減值單編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_Q" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field_Q" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_QRO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 號
			&nbsp;
			<font id="hint" color="red"></font>
		</td>
	</tr>
	
	<tr>
		<td class="td_form">核准機關：</td>
		<td class="td_form_white" colspan="3">
            <select class="field" type="select" name="approveOrg" onChange="changeApproveOrg();">
              <%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ",obj.getApproveOrg())%>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			核准日期：<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
                                核准文號：<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
        </td>
	</tr>
	<tr>
		<td class="td_form" >入帳：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="verify" onChange="getVerifyDate();">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;                
			入帳日期：
			<%=util.View.getPopCalndar("field","adjustDate",obj.getAdjustDate())%>
			<input type="hidden" class="field_QRO" name="adjustDateTemp" value="<%=obj.getAdjustDateTemp()%>" size="7"> 			
		</td>
	</tr>
	<tr>
		<td class="td_form">傳票日期：</td>
		<td colspan="3" class="td_form_white" >
		<%=util.View.getPopCalndar("field","summonsDate",obj.getSummonsDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			傳票號數：
            <input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>">
			
		</td>		
	</tr>
	<tr>
		<td class="td_form">工程編號：</td>
		<td colspan="3" class="td_form_white">
			[<input class="field_RO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo() %>">]
			[<input class="field_RO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName()%>">]
		</td>
	</tr>
	<tr>
		<td  rowspan="3" class="td_form" >備註：</td>
		<td width="30%" class="td_form_white" >
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>		</td>
		<td width="21%" class="td_form"style="display:none;">異動人員/日期：</td>
		<td class="td_form_white"style="display:none;">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=user.getUserID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="btnUntch012r">
		<br>&nbsp;|
		<input class="toolbar_default" type="button" followPK="true" name="printAddProof" value="列印增減值單" onClick="loadUntrp004r();">&nbsp;|
	</span>
	<span id="spanPrint02">
		<input class="toolbar_default" type="button" followPK="true" name="print02" value="列印毀損報廢單" onClick="loadUntrp005r();">&nbsp;|
	</span>
	
	<span id="btn_verifyRe">
	<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復入帳" disabled="true" onClick="clickApproveRe();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="true" name="NewData" value=帶入最新資料 disabled="true" onClick="updateNewData();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">增減單編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳</a></th>				
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,true,false,false,false,false};
	boolean displayArray[] = {false,false,true,false,true,false,true,true,true,true};
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
        case "delete":
			//if (!confirm("刪除此增加單，將一併刪除其關聯的資料：建物基本資料、管理資料、使用人資料檔、樓層資料、附屬建物、基地資料、共同資料、附屬設備、折舊記錄、現場勘查、稅藉資料。您確定要刪除?"))
			if (!confirm("刪除此增減值單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
			return false;			
			hasBeenConfirmed = true;
			break;		
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
			if(form1.engineeringNo.value != ''){				
				if('<%=eg_ownership%>' != ''){
					form1.ownership.value = '<%=eg_ownership%>';	
				}
				if('<%=eg_differenceKind%>' != ''){
					form1.differenceKind.value = '<%=eg_differenceKind%>';	
				}
			}
			setFormItem("verify,adjustDate,btn_adjustDate,verifyRe,summonsDate,btn_summonsDate,summonsNo","R");
			break;	
		case "update":
			if ('<%=user.getRoleid()%>' == '3'){
				setFormItem("verify","O");
			}else{
				setFormItem("verify","R");
			}			
			if ('<%=user.getIsAdminManager()%>' == 'Y'){
				setFormItem("verify","O");
			}
			if (form1.verify.value=="Y") {
				// 入帳後 僅可透過回復入帳 做單據資料異動
				setAllReadonly();
			} else {
				setFormItem("verifyRe,adjustDate,btn_adjustDate,summonsDate,btn_summonsDate,summonsNo","R");
			}
			break;
		case "clear":
			if(form1.engineeringNo.value != ''){
				if('<%=eg_ownership%>' != ''){
					form1.ownership.value = '<%=eg_ownership%>';	
				}
				if('<%=eg_differenceKind%>' != ''){
					form1.differenceKind.value = '<%=eg_differenceKind%>';	
				}
			}
			if (form1.verify.value=="N") {
				setFormItem("verifyRe","R");
			}
			break;
	}
}
</script>
</body>
</html>



