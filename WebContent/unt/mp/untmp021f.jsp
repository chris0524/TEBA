<!--
程式目的：動產廢品處理作業－處理單資料
程式代號：untmp021f
程式日期：0941201
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP021F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP021F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("approveAll".equals(obj.getState())) {
	obj.approveAll();
}else if ("approveRe".equals(obj.getState())) {
	obj.approveRe();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("".equals(obj.getEnterOrg()) ||  "".equals(obj.getCaseNo1())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setCaseNo1(((String[])objList.get(0))[2]);
		}
		obj = (unt.mp.UNTMP021F)obj.queryOne();		
	}
}

String queryType1 = "", queryType2 = "";
if ("dealDetail".equals(obj.getQuerySelect())) queryType2 = "checked=\"CHECKED\"";	
else if("dealProof".equals(obj.getQuerySelect())) queryType1 = "checked=\"CHECKED\"";

System.out.println("user.getOrganID()="+user.getOrganID());
System.out.println("user.getOrganName()="+user.getOrganName());

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._MP_DEAL, 1);
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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>

<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","1"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("writeUnit","<%=user.getUnitID()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"D5")%>"),
	new Array("verify","N")
	
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untmp021f.jsp";
		} else {
			form1.action = "untmp022f.jsp";
		}
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofYear,"處理單編號－年");
		alertStr += checkEmpty(form1.proofDoc,"處理單編號－字");
		//alertStr += checkEmpty(form1.dealDate,"處理日期");
		alertStr += checkDate(form1.dealDate,"處理日期");
		alertStr += checkEmpty(form1.verify,"審核");
		alertStr += checkLen(form1.notes, "備註", 250);
		if(form1.reduceDeal.value=="03"){
			alertStr += checkEmpty(form1.shiftOrg,"轉撥單位");
		}
		if(form1.reduceDeal.value=="01"){
			alertStr += checkEmpty(form1.realizeValue,"變賣總金額");
			alertStr += checkInt(form1.realizeValue,"變賣總金額");
			if(parseInt(form1.realizeValue.value)<=0){
				alertStr +="變賣總金額必須大於0!\n";
			}
		}

		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,caseNo1){
	form1.enterOrg.value=enterOrg;
	form1.caseNo1.value=caseNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){	
	if ((form1.verify.value=="Y") || (form1.state.value=="updateError")) {
		setFormItem("update,delete", "R");
	}else{
		setFormItem("update,delete", "O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("update,delete,approveAll,verifyRe,untmp024r", "R");
	}
	
	if(form1.cannotVerify.value=="Y"){
        form1.verifyRe.disabled=true;
	}else if(form1.cannotVerify.value=="N"){
	    form1.verifyRe.disabled=false;
	}
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
		opener.form1.submit();	
		if (isObj(window.aha)) window.aha.close();
	}	

	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 財產管理 > > 動產廢品處理作業";
}

function changeSelect(){
	//當「廢品處理方式」為「轉撥」時
	if(form1.reduceDeal.value=="03"){
		form1.btn_shiftOrg.disabled=false;
		form1.realizeValue.value="";
	//當「廢品處理方式」為「變賣」時
	}else if(form1.reduceDeal.value=="01"){
		form1.realizeValue.disabled=false;	
		form1.shiftOrg.value="";
		form1.shiftOrgName.value="";
	}else{
		form1.btn_shiftOrg.disabled=true;
		form1.realizeValue.disabled=true;
		form1.shiftOrg.value="";
		form1.realizeValue.value="";
		form1.shiftOrgName.value="";
	}
}

function clickApproveAll(){
	if(confirm("您確定要將列表中未審核的處理單全部審核?")){
		document.all('state').value='approveAll';
		form1.checkEnterOrg.value="<%=user.getOrganID()%>";
		beforeSubmit();
		form1.submit();
	}
}

function clickApproveRe(){
	if(confirm("您確定是否回復審核?")){
		document.all('state').value='approveRe';
		beforeSubmit();
		form1.submit();
	}
}	

function checkURL(surl){
	columnTrim(form1.caseNo1);
	if(form1.caseNo1.value==""){
		alert("請先執行查詢!");
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{		
		if (document.all.querySelect[0].checked || form1.state.value=="insertSuccess") {
			surl=surl+"?initDtl=Y";
			form1.state.value="queryAll";	
		} else {
			if (form1.caseNo.value!="" && form1.propertyNo.value!="" && form1.serialNo.value!="" ) 
				form1.state.value = "queryOne";		
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function goUntmp024r(printType){
	if(printType == 'print1'){
		var url = "untmp024p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
			"&q_caseNoS=" + form1.caseNo1.value + "&q_caseNoE="+form1.caseNo1.value ;
		window.open(url);
	}else if(printType == 'print2'){
		var url = "untmp048p.jsp?q_enterOrg=" + form1.enterOrg.value 
				+ "&q_ownership=" + form1.ownership.value 
				+ "&q_caseNoS=" + form1.caseNo1.value 
				+ "&q_caseNoE="+form1.caseNo1.value 
				+ "&q_kind=4";
		window.open(url);
	}
}
function loadUntmp024r(){
	var url = "untmp024r.jsp?"
			+ "organID=" + form1.organID.value 
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo1.value 
			+ "&q_caseNoE="+form1.caseNo1.value 
			+ "&q_proofYear="+form1.proofYear.value 
			+ "&q_proofDoc="+encodeURIComponent(form1.proofDoc.value)
			+ "&q_proofNoS="+form1.proofNo.value 
			+ "&q_proofNoE="+form1.proofNo.value ;
		

	window.open(url);
}

function alteredEnterOrg(){
	//changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function getDealDate() {
	if (form1.verify.value=="Y") {
		if (form1.dealDate.value=="") form1.dealDate.value="<%=util.Datetime.getYYYMMDD()%>";
	}
	else if (form1.verify.value=="N"){
		form1.dealDate.value="";
	}
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="checkDealDate" value="<%=obj.getDealDate()%>">
<input type="hidden" name="checkVerify" value="<%=obj.getVerify()%>">
<input class="field_P" type="hidden" name="ownership" value="1" >
<!--Query區============================================================-->
<div id="queryContainer" style="width:950px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDInput" colspan="4">
	  <input name="querySelect" type="radio" class="field_Q" value="dealProof" <%=queryType1%>>
      &nbsp;<font color="green">查詢處理單資料</font>&nbsp;&nbsp;&nbsp;	
	  <input name="querySelect" type="radio" class="field_Q" value="dealDetail" <%=queryType2%>>
      &nbsp;<font color="green">查詢處理單明細資料</font>
	  </td>
	</tr>
	<tr>
      <td class="queryTDLable" >入帳機關：</td>
      <td class="queryTDInput" colspan="3" ><%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N")%> </td>
	</tr>
	<tr>
	  <td class="queryTDLable">廢品處理日期：</td>
	  <td class="queryTDInput" colspan="3">
			  起<%=util.View.getPopCalndar("field_Q","q_dealDateS",obj.getQ_dealDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_dealDateE",obj.getQ_dealDateE())%>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"3,4,5")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>		
		<td class="queryTDLable">審核：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(obj.getQ_verify())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", obj.getQ_fundType(),"FUD", obj.getQ_enterOrg()) %>
		</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>	
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput"colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%> 
		</td>		
		<td class="queryTDLable"style="display:none;">處理案名：</td>
		<td class="queryTDInput"style="display:none;">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=obj.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">處理單編號：</td>
		<td class="queryTDInput" colspan="3">		
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=obj.getQ_proofYear()%>">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">處理方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reduceDeal">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", obj.getQ_reduceDeal())%>
			</select>
		</td>
		<td class="queryTDLable">轉撥單位：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_shiftOrg",obj.getQ_shiftOrg(),obj.getQ_shiftOrgName(),"Y")%>
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
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">入帳機關：</td>
		<td colspan="3" class="td_form_white">		
			<input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;財產區分別：
						<%=util.View._getSelectHTML("field_P", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
				
			<input class="field_PRO" type="hidden" name="caseNo1" size="15" maxlength="10" value="<%=obj.getCaseNo1()%>">
			<input class="field" type="hidden" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"></font>填單日期：</td>
		<td colspan="3" class="td_form_white">
		<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
		填造單位：<select class="field" type="select" name="writeUnit" >
				<%=util.View.getOption("select  unitno, unitname from UNTMP_KEEPUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno", obj.getWriteUnit())%>
			</select>
			<input class="field" type="button" name="btn_writeUnit" onclick="popUnitNo(form1.enterOrg.value,'writeUnit')" value="..." title="單位輔助視窗">
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
			<!--   財產管理單位編號： --><input class="field" type="hidden" name="manageNo" size="15" maxlength="10" value="<%=obj.getManageNo()%>"></td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>處理單編號：</td>
		<td colspan="3" class="td_form_white" >
			<input class="field" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_PRO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 號  		
	</tr>
	<tr>
		<td class="td_form">處理日期：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View.getPopCalndar("field","dealDate",obj.getDealDate())%>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		審核：<select class="field" type="select" name="verify" onChange="getDealDate();">
			<%=util.View.getYNOption(obj.getVerify())%>
        </select>
		</td>
	</tr>
	<tr>
		<td class="td_form">處理方式：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="reduceDeal" onChange="changeSelect();">
			<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid='RDL' ", obj.getReduceDeal())%>
			</select>
		&nbsp;
		變賣總金額：
			<input class="field_PRO" type="text" name="realizeValue" size="12" maxlength="15" value="<%=obj.getRealizeValue()%>">
		&nbsp;
		轉撥單位：
			<%=util.View.getPopOrgan("field","shiftOrg",obj.getShiftOrg(),obj.getShiftOrgName(),"Y")%>
		</td>
	</tr>
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white"><textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>      </td>
	  <td class="td_form"style="display:none;">異動人員/日期：</td>
	  <td class="td_form_white"style="display:none;"> [
	      <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	    /
	    <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
	    ] </td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="filestoreLocation" value="<%=application.getInitParameter("filestoreLocation")%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<input type="hidden" name="cannotVerify" value="<%=user.getCannotVerify()%>">
	<input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="isUntmp021f" value="Y">
	<input type="hidden" name="objPath" >
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanVerifyRe">
	<input class="toolbar_default" type="button" name="verifyRe" value="回復審核" onClick="clickApproveRe();">&nbsp;|
	</span>
	<span id="spanUntmp024r">
		<input class="toolbar_default" type="button" followPK="true" name="untmp024r" value="列印動產廢品清冊" onClick="loadUntmp024r();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">處理單編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">處理日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">處理方式</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">變賣總金額</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">轉撥單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">審核</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {true,true,false,true,true,true,true,true,true,true,false,false,false,false};
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
		if(!confirm("刪除此處理單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
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
				//changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
			break;				
	}
	return true;
};

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");

	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
				form1.btn_shiftOrg.disabled=true;
				form1.shiftOrg.value="";
				form1.realizeValue.disabled=true;	
				form1.verify.disabled=true;
				form1.verify.value="N";
	            if(form1.cannotVerify.value=="Y"){
                    form1.verifyRe.disabled=true;
	            }else if(form1.cannotVerify.value=="N"){
	                form1.verifyRe.disabled=false;
	            }
			break;
		case "insertError":
				form1.btn_shiftOrg.disabled=true;
				form1.shiftOrg.value="";
				form1.verify.disabled=true;
				form1.realizeValue.disabled=true;	
				if(form1.cannotVerify.value=="Y"){
                    form1.verifyRe.disabled=true;
	            }else if(form1.cannotVerify.value=="N"){
	                form1.verifyRe.disabled=false;
	            }
			break;
		//更新時要做的動作
		case "update":
			//當「廢品處理方式」為「轉撥」時
			if(form1.reduceDeal.value=="03"){
				form1.btn_shiftOrg.disabled=false;
			//當「廢品處理方式」為「變賣」時
			}else if(form1.reduceDeal.value=="01"){
				form1.realizeValue.disabled=false;	
			}else{
				form1.btn_shiftOrg.disabled=true;
				form1.realizeValue.disabled=true;
			}
			if(form1.cannotVerify.value=="Y"){
                    form1.verifyRe.disabled=true;
	            }else if(form1.cannotVerify.value=="N"){
	                form1.verifyRe.disabled=false;
	            }
			if(form1.verify.value=="Y"){
				setFormItem("verify","R");
			}else{
				setFormItem("verify","O");
			}
			break;
		case "updateError":
			//當「廢品處理方式」為「轉撥」時
			if(form1.reduceDeal.value=="03"){
				form1.btn_shiftOrg.disabled=false;
			//當「廢品處理方式」為「變賣」時
			}else if(form1.reduceDeal.value=="01"){
				form1.realizeValue.disabled=false;	
			}else{
				form1.btn_shiftOrg.disabled=true;
				form1.realizeValue.disabled=true;
			}
			if(form1.cannotVerify.value=="Y"){
                    form1.verifyRe.disabled=true;
	            }else if(form1.cannotVerify.value=="N"){
	                form1.verifyRe.disabled=false;
	            }
			if(form1.verify.value=="Y"){
				setFormItem("verify","R");
			}else{
				setFormItem("verify","O");
			}
			break;
	}
}
</script>
</body>
</html>



