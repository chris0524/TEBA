<!--
程式目的：財產增加單及主檔維護
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH001F10">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%
obj.setQuerySelect("proof");
obj.setIsAddProof("true");

String p_engineeringNo = Common.checkGet(request.getParameter("engineeringNo"));
String p_engineeringNoName = uctls._getEngineeringNoName(obj.getEnterOrg(), p_engineeringNo);

if(p_engineeringNo == null){p_engineeringNo = "";}
if(p_engineeringNoName == null){p_engineeringNoName = "";}

if ("init".equals(obj.getState())) {
	obj.setEngineeringNo("");
}

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}


String table = "UNTLA_ADDPROOF";
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
	if(obj.checkUpdateError()){
		obj._execUpdate(table); 
		if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}											
	}
	
}else if ("delete".equals(obj.getState())) {	obj._execDelete(table);
												obj.setCaseNo("");
												obj.setOwnership("");
												obj.setDifferenceKind("");
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	
	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
			
		}else{ 
			if("".equals(obj.getQueryone_enterOrg()) ||
				"".equals(obj.getQueryone_ownership()) ||
				"".equals(obj.getQueryone_caseNo()) ||
				"".equals(obj.getQueryone_differenceKind())){
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
String tabs = uch._createTabData(uch._CH_ADD, 1);

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
	new Array("proofDoc","<%=new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D1")%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("verify","N"),
	new Array("engineeringNo","<%=p_engineeringNo%>"),
	new Array("engineeringNoName","<%=p_engineeringNoName%>"),	
	new Array("writeUnit", "<%=user.getUnitID()%>")	
);

function getEnterDate() {
	if (form1.verify.value=="Y") {
		if (form1.enterDate.value==""){
			form1.enterDate.value='<%=util.Datetime.getYYYMMDD()%>';
		}
		if (form1.summonsDate.value==""){ 
			form1.summonsDate.value='<%=util.Datetime.getYYYMMDD()%>';
		}
	}else{
		form1.enterDate.value="";
		form1.summonsDate.value="";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untch001f10.jsp";
		} else {
			form1.action = "untch001f09.jsp";
		}
	} else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.verify,"入帳");		
		alertStr += checkDate(form1.enterDate,"入帳日期");		
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.enterDate,"若要入帳，入帳日期");
						
		}

		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
		
		alertStr += checkLen(form1.notes, "備註", 250);
		form1.action = "untch001f10.jsp";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	
	beforeSubmit();	
}

function queryOne(enterOrg,ownership,differenceKind,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.differenceKind.value=differenceKind;
	form1.caseNo.value=caseNo;
	form1.state.value="queryOne";
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
		beforeSubmit();
		form1.submit();
	}
	
}

function init(){
	initQ_Form("proof");
	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormField("insert,update,delete", "R");
	}
	if (form1.verify.value=="Y") {
		setFormItem("ownership,differenceKind,caseName,writeDate,writeUnit,manageNo,proofYear,proofDoc,proofNo,summonsNo,enterDate,btn_enterDate,notes", "R");
		setFormItem("delete", "R");
	}	
	
	setFormItem("printAddProof,printPropertyNoCard,printLabel,printEngPropertyNo", "O");
	
	if(<%=checkDataCount%>){
		$("select[name='ownership']").attr('class','field');
		$("select[name='differenceKind']").attr('class','field');
		$("select[name='verify']").attr('class','field_P');
	}else{
		$("select[name='ownership']").attr('class','field_P');
		$("select[name='differenceKind']").attr('class','field_P');
		$("select[name='verify']").attr('class','field');
	}
	
	form1.enterDateTemp.value = form1.enterDate.value;

	//此行要對照primaryArray所設定的pk值為何
	listContainerRowClick(form1.enterOrg.value + form1.ownership.value + form1.differenceKind.value + form1.caseNo.value);
}

function loadUntrp001r(){
	var url = "../rp/untrp001r.jsp?"
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

function loadUntmp051r01(){
	var url = "../mp/untmp051r01.jsp?"
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

function changePropertyNo(propertyNo){}

function resetVerify(){
	var alertStr ="";
	if (form1.enterDate.value != ""){
		alertStr = checkDate(form1.enterDate,"入帳日期");
		if(alertStr.length!=0){ 
			$("select[name='verify']").val("N")
			$("input[name='summonsDate']").val("");
			alert(alertStr); 
			return false; 
		}else{
			$("select[name='verify']").val("Y");
			$("input[name='summonsDate']").val(form1.enterDate.value);
		}
		
	}else{
		$("select[name='verify']").val("N");
		$("input[name='summonsDate']").val("");
	}
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

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
	<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
	<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
</td></tr></table>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q",obj);%>
	<jsp:include page="untch001q.jsp" />
</div>

<!--頁籤區============================================================-->
<table STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">	
	<%=tabs%>	
</table>

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">入帳機關：</td>
		<td colspan="3" class="td_form_white">		
			<input class="field_QRO" type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;&nbsp;&nbsp;
			<font color="red">*</font>權屬：
			<select class="field" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;
			<font color="red">*</font>財產區分別：
			<%=util.View._getSelectHTML("field", "differenceKind", obj.getDifferenceKind(),"DFK") %>
			
			<input class="field_QRO" type="hidden" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>"> 
			<input class="field_QRO" type="hidden" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">			
		</td>
	</tr>
	
			
	<tr>
		<td class="td_form">填單日期：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
			&nbsp;&nbsp;
			填造單位：
			<select class="field" type="select" name="writeUnit" >
				<%=util.View.getOption("select  unitno, unitname from UNTMP_KEEPUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno", obj.getWriteUnit())%>
			</select>
			<input class="field" type="button" name="btn_writeUnit" onclick="popUnitNo(form1.enterOrg.value,'writeUnit')" value="..." title="單位輔助視窗">
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <!-- 
		  財產管理單位編號：
		   -->
		  <input class="field" type="hidden" name="manageNo" size="15" maxlength="10" value="<%=obj.getManageNo()%>"></td>
		</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>增加單編號：</td>
		<td colspan="3" class="td_form_white" >
			<input class="field_Q" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field_Q" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_QRO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 
			號  		
			</td>
		</tr>
        <tr>
          <td class="td_form">入帳：</td>
          <td colspan="3" class="td_form_white">
			<select class="field" type="select" name="verify" onChange="getEnterDate();">
                <%=util.View.getYNOption(obj.getVerify())%>
              </select> &nbsp;&nbsp;入帳日期：<%=util.View.getPopCalndar2("field","verify","enterDate","summonsDate",obj.getEnterDate())%>              
				<input type="hidden" class="field_QRO" name="closingYM" value="<%=obj.getClosingYM()%>" size="7">
				<input type="hidden" class="field_QRO" name="enterDateTemp" value="<%=obj.getEnterDateTemp()%>" size="7">
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
        		[<input class="field_QRO" type="text" name="engineeringNo" size="10" maxlength="11" value="<%=obj.getEngineeringNo() %>" onBlur="">]
				[<input class="field_QRO" type="text" name="engineeringNoName" size="20" maxlength="50" value="<%=obj.getEngineeringNoName() %>">]
        	</td>
        </tr>
        
        
        		
	
	<tr>
	  <td class="td_form">備註：</td>
	  <td class="td_form_white">
	  	<textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>      
	  </td>
	  <td class="td_form" style="display:none;">異動人員/日期：</td>
	  <td class="td_form_white" style="display:none;">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	    /
	    <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />
	
	<span id="btnUntch012r">
		<br>&nbsp;|
		<input class="toolbar_default" type="button" followPK="true" name="printAddProof" value="列印增加單" onClick="loadUntrp001r();">&nbsp;|
	</span>
	<span id="x1">
		<input class="toolbar_default" type="button" followPK="true" name="printLabel" value="列印標籤" onClick="loadUntmp051r01();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">增加單編號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳日期</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,true,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,false,true,false,true,true,false,true,true,false};
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
			if (!confirm("刪除此增加單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
			return false;			
			hasBeenConfirmed = true;
			break;		
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[1].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			}
			break;			
	}
	return true;
};

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
		case "insertError":
			setFormItem("verify","R");
			break;
		case "update":
			if (form1.verify.value=="Y") {
				setAllReadonly();
				form1.verify.style.backgroundColor=editbg;
				setFormItem("verify,enterDate,btn_enterDate","O");
			}
			if ('<%=user.getRoleid()%>' == '3'){
				setFormItem("verify","O");
			}else{
				setFormItem("verify","R");
			}			
			if ('<%=user.getIsAdminManager()%>' == 'Y'){
				setFormItem("verify","O");
			}
			
			break;	
	}
};
</script>
</body>
</html>



