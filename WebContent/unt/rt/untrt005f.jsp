<!--
程式目的：權利減少作業-減損單資料
程式代號：untrt005f
程式日期：0941004
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rt.UNTRT005F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("approveAll".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.approveAll();
}else if ("approveRe".equals(obj.getState())) {
	obj.approveRe();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
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
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("writeUnit","<%=user.getUnitName()%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"F2")%>"),
	new Array("ownership","1"),
	new Array("verify","N"),
	//new Array("reduceDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("closing","N")	
);

function getReduceDate() {
	if (form1.verify.value=="Y") {
		if (form1.reduceDate.value=="") form1.reduceDate.value="<%=util.Datetime.getYYYMMDD()%>";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		//查詢時為增加單查詢或是標的資料查詢
		if (document.all.querySelect[0].checked) {
			form1.action = "untrt005f.jsp";
		} else {
			form1.action = "untrt006f.jsp";
		}
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"減損單編號－字");
		alertStr += checkEmpty(form1.cause,"減損原因");
		//alertStr += checkEmpty(form1.reduceDate,"入帳日期");
		alertStr += checkDate(form1.reduceDate,"入帳日期");
		alertStr += checkLen(form1.notes, "備註", 250);
		if(form1.propertyNo.value=="" || form1.serialNo.value=="") alertStr+="請選取增加單資料!\n";
		//當增加原因選「其他」時，開放其他說明欄位且必須有資料
		if(form1.cause.value=="99") alertStr += checkEmpty(form1.cause1,"其他說明");
		else form1.cause1.value = "";
		getReduceDate();
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

function changePropertyNo(){
	//若權利為「股份,其他財產權利」開放原始設定價值
	var checkPropertyNo = form1.propertyNo.value;
	if(checkPropertyNo.substring(0,3) == "801" || checkPropertyNo.substring(0,3) == "806"){
		form1.reduceBookValue.value = 0;
		form1.oldBookValue.value = 0;
		form1.newBookValue.value = 0;
	}else{
		form1.reduceBookValue.value = 0;
		form1.oldBookValue.value = "<%=obj.getBookValue()%>";
		form1.newBookValue.value = form1.oldBookValue.value;
	}
}

function changeSelect(){
	//當減損原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "99") form1.cause1.readOnly = false;
	else{
		form1.cause1.readOnly = true;
		form1.cause1.value="";
	}
}

function clickApproveAll(){
	if(confirm("您確定要將列表中未入帳的減損單全部入帳?")){
		document.all('state').value='approveAll';
		beforeSubmit();
		form1.submit();
	}
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	//若權利為「股份,其他財產權利」不可點選減損單明細資料
	var checkPropertyNo = form1.propertyNo.value;
	if(checkPropertyNo.substring(0,3) == "801" || checkPropertyNo.substring(0,3) == "806"){
		alert("權利為「股份,其他財產權利」不可點選減損單明細資料!");
		form1.state.value="queryOne";
	}else if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{		
		form1.state.value="queryAll";
		if (document.all.querySelect[0].checked) {
			surl=surl+"?initDtl=Y";
		} else {
			if (form1.enterOrg.value!="" && form1.ownership.value!="" && form1.caseNo.value!="" && form1.propertyNo.value!="" && form1.serialNo.value!="" && form1.serialNo1.value!="") form1.state.value = "queryOne";		
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	<!-- 查出的「減損單資料」若「已入帳」，均不允許修改、刪除該筆減損單資料 -->
	if (form1.verify.value=="Y"){
		setFormItem("update,delete", "R");
	}
	<!-- 非登入者所屬機關所登錄的資料，均不允許新增、修改、刪除各標籤資料 -->
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("update,delete,approveAll,verifyRe","R");
	}
}
function clickAddProof(){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+100;
	prop=prop+"width=600,height=380,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popUntrtAddProof.jsp","",prop);
}

function goUntrt007r(){
	var url = "untrt007p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo.value + "&q_caseNoE="+form1.caseNo.value +
		"&q_kind=4";
	window.open(url);
}

function clickApproveRe(){
	if(confirm("您確定是否回復入帳?")){
		document.all('state').value='approveRe';
		beforeSubmit();
		form1.submit();
	}
}

function loadUntrt007r(){
	var url = "untrt007p.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value
			+ "&q_caseNoS=" + form1.caseNo.value
			+ "&q_caseNoE="+form1.caseNo.value
			+ "&q_kind=4";
	window.open(url);
}
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="serialNo1" value="<%=obj.getSerialNo1()%>">
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTRT005Q",obj);%>
	<jsp:include page="untrt005q.jsp" />
</div>
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
  <tr>
    <td ID=t1 CLASS="tab_border1" HEIGHT="25">減損單資料</td>
    <td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untrt006f.jsp');">減損單明細</a></td>
  </tr>
  <tr>
    <td class="tab_line1"></td>
    <td class="tab_line2"></td>
  </tr>
</TABLE>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" colspan="4" style="text-align:center;">
			<!-- 因應增加單資料內的欄位多出的兩個隱藏欄位 -->
			<input class="field" type="hidden" name="adjustBookValue" size="15" maxlength="15" value="">
			<input class="field" type="hidden" name="adjustType" size="15" maxlength="15" value="">
			<input class="toolbar_default" type="button" name="queryAddProof" value="增加單資料挑選" onClick="clickAddProof();changePropertyNo();" disabled=true>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="16%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
			<input class="field_P" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;　權屬：
			<select class="field_PRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			&nbsp;　填單日期：<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">電腦單號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
			&nbsp;&nbsp;&nbsp;　　　　減損案名：<input class="field" type="text" name="caseName" size="40" maxlength="25" value="<%=obj.getCaseName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">填造單位：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="writeUnit" size="15" maxlength="15" value="<%=obj.getWriteUnit()%>">
			&nbsp;&nbsp;&nbsp;　　　　財產管理單位編號：<input class="field" type="text" name="manageNo" size="10" maxlength="10" value="<%=obj.getManageNo()%>">
		</td>
	</tr>
	<tr>
		<td width="15%" class="td_form"><font color="red">*</font>減損單編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">
			字第 [ <input class="field_RO" type="text" name="proofNo" size="10" maxlength="10" value="<%=obj.getProofNo()%>"> ] 號
			&nbsp;　傳票號數：<input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>">
		</td>
	</tr>
	<tr>
  	    <td class="td_form"><font color="red">*</font>入帳：</td>
    	<td colspan="3" class="td_form_white"><select class="field" type="select" name="verify" onChange="getReduceDate();">
        	<%=util.View.getYNOption(obj.getVerify())%>
			</select> &nbsp;　　　入帳日期：<%=util.View.getPopCalndar("field","reduceDate",obj.getReduceDate())%>
          	&nbsp;&nbsp;　　　　月結：
          	<select class="field_RO" type="select" name="closing">
            	<%=util.View.getYNOption(obj.getClosing())%>
          	</select>
      	</td>
    </tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>" onChange="changePropertyNo();">]
			&nbsp;　分號：[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
			&nbsp;　名稱：[<input class="field_RO" type="text" name="propertyNoName" size="25" maxlength="50" value="<%=obj.getPropertyNoName()%>" onChange="changePropertyNo();">]
		<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="15" maxlength="20" value="<%=obj.getPropertyName1()%>">]
			&nbsp;　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;　原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind" onChange="changeSelect();">
			<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
			</select>
			&nbsp;&nbsp;　　　　　　　　　基金財產：
			<select class="field_RO" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>減損原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="cause" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC'", obj.getCause())%>
			</select>
			&nbsp;&nbsp;　　　　　　　　　其他說明：<input class="field" type="text" name="cause1" size="10" maxlength="20" value="<%=obj.getCause1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">範圍：</td>
		<td class="td_form_white" colspan="3">
			他項權利範圍：
			[<input class="field_RO" type="text" name="holdNume1" size="10" maxlength="10" value="<%=obj.getHoldNume1()%>">
			/
			<input class="field_RO" type="text" name="holdDeno2" size="10" maxlength="10" value="<%=obj.getHoldDeno2()%>">]
			&nbsp;　帳務摘要：<input class="field" type="text" name="bookNotes" size="19" maxlength="20" value="<%=obj.getBookNotes()%>">
		<br>
			原帳面設定價值：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">]
			&nbsp;&nbsp;減少帳面設定價值：[<input class="field_RO" type="text" name="reduceBookValue" size="15" maxlength="15" value="<%=obj.getReduceBookValue()%>">]
		<br>
			新帳面設定價值：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">]
		<br>
			登記日期：[<input class="field_RO" type="text" name="registerDate" size="7" maxlength="7" value="<%=obj.getRegisterDate()%>">]
			&nbsp;&nbsp;　　　　　　　　　　　登記原因：
			<select class="field_RO" type="select" name="registerCause" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RCA' ", obj.getRegisterCause())%>
			</select>
		<br>
			設定起訖日期：[<input class="field_RO" type="text" name="setPeriod" size="20" maxlength="20" value="<%=obj.getSetPeriod()%>">]
			　　　　設定人：[<input class="field_RO" type="text" name="setPerson" size="15" maxlength="15" value="<%=obj.getSetPerson()%>">]
		<br>
			共同權利人：[<input class="field_RO" type="text" name="commonObligee" size="15" maxlength="15" value="<%=obj.getCommonObligee()%>">]
			&nbsp;&nbsp;　　　　　　清償時間：[<input class="field_RO" type="text" name="payDate" size="7" maxlength="7" value="<%=obj.getPayDate()%>">]
		<br>
			利息：[<input class="field_RO" type="text" name="interest" size="15" maxlength="15" value="<%=obj.getInterest()%>">]
			&nbsp;　　　地租：[<input class="field_RO" type="text" name="rent" size="33" maxlength="30" value="<%=obj.getRent()%>">]
		<br>
			其他事由：[<input class="field_RO" type="text" name="notes1" size="60" maxlength="60" value="<%=obj.getNotes1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="sourceKind" disabled="true">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKE' ", obj.getSourceKind())%>
			</select>
			&nbsp;　　證明書字號：[<input class="field_RO" type="text" name="proofDoc1" size="25" maxlength="40" value="<%=obj.getProofDoc1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">購置日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
			&nbsp;&nbsp;&nbsp;　　內容：[<input class="field_RO" type="text" name="meat" size="31" maxlength="30" value="<%=obj.getMeat()%>">]
		</td>
	</tr>
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</tr>
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
	<span id="spanUntrt007r">
		<input class="toolbar_default" type="button" followPK="true" name="untrt007r" value="列印減損單" onClick="loadUntrt007r();">&nbsp;|
	</span>	
	<span id="spanVerifyRe">
		<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復入帳" disabled="true" onClick="clickApproveRe();">&nbsp;|	
	</span>

	
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">減損日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,false,false};
	boolean displayArray[] = {false,true,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language='javascript'>
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		//刪除之前多出現一道確認訊息
       	case "delete":
		if(!confirm("刪除此減損單，將一併刪除其關聯的減損單明細!"))
			return false;
			hasBeenConfirmed = true;
			break;
			
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
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly=true;
			}	
			setFormItem("queryAddProof","O");
			setFormItem("verify","R");
			break;
		case "insertError":
			changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly=true;
			}	
			setFormItem("queryAddProof","O");
			setFormItem("verify","R");			
			break;
		//更新時要做的動作
		case "update":
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value == "99"){
				form1.cause1.readOnly = false;
			}else
				form1.cause1.readOnly = true;
			break;
		case "updateError":
			//當增加原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value == "99"){
				form1.cause1.readOnly = false;
			}else
				form1.cause1.readOnly = true;
			break;
		//取消時要執行的動作
		case "clear":
			setFormItem("queryAddProof,print1","R");
			break;
		case "clearError":
			setFormItem("queryAddProof,print1","R");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("queryAddProof","R");
			break;
		case "confirmError":
			setFormItem("queryAddProof","R");
			break;			
	}
}
</script>
</body>
</html>



