<!--
程式目的：動產保管使用異動作業－移動單資料
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH002F01">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	// 當有先前紀錄的currentPage時 , 則使用先前紀錄的currentPage
	if (!"".equals(obj.getUNTCH002F01_currentPage())) {
		obj.setCurrentPage(Integer.parseInt(obj.getUNTCH002F01_currentPage()));
		obj.setUNTCH002F01_currentPage("");
	}

obj.setQuerySelect("proof");

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())) {
		obj.setQueryAllFlag("true");
	}
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ch.UNTCH002F01)obj.queryOne();
	obj.setQueryone_enterOrg(obj.getEnterOrg());
	obj.setQueryone_ownership(obj.getOwnership());
	obj.setQueryone_caseNo(obj.getCaseNo());
	obj.setQueryone_differenceKind(obj.getDifferenceKind());
}else if ("insert".equals(obj.getState())) {
	obj.insert();
	if ("insertSuccess".equals(obj.getState())) {
		obj.setQueryAllFlag("true");
		obj.setI_enterOrg(obj.getEnterOrg());
		obj.setI_ownership(obj.getOwnership());
		obj.setI_caseNo(obj.getCaseNo());
		obj.setI_differenceKind(obj.getDifferenceKind());
	}
}else if ("update".equals(obj.getState())) {
	if(obj.checkUpdateError()){
		obj.update();
		if ("updateSuccess".equals(obj.getState())) {
			obj.setQueryAllFlag("true");
		}
	}
		
}else if ("delete".equals(obj.getState())) {
	obj.delete();
	obj.setCaseNo("");
	obj.setOwnership("");
	obj.setDifferenceKind("");
	if ("deleteSuccess".equals(obj.getState())) {	
		obj.setQueryAllFlag("true");
	}
}else if ("approveRe".equals(obj.getState())) {	
	if(obj.checkReVerifyError()){
		obj.setReVerify("Y");
		obj.update();
		obj.setReVerify("");
		obj.setMoveDate("");
	}
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	
	if (!objList.isEmpty()) {
		if("deleteSuccess".equals(obj.getState())){
			
		}else{ 
			if("".equals(obj.getQueryone_enterOrg()) || "".equals(obj.getQueryone_ownership()) || "".equals(obj.getQueryone_caseNo())){
				obj.setEnterOrg(((String[])objList.get(0))[0]);
				obj.setOwnership(((String[])objList.get(0))[1]);
				obj.setCaseNo(((String[])objList.get(0))[4]);
			}
			obj = (unt.ch.UNTCH002F01)obj.queryOne();
		}
	}
}

boolean checkDataCount = obj.checkDataCountIsZero(obj.getEnterOrg(), obj.getCaseNo());

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._CH_MOVE, 1);
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
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
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
	new Array("proofDoc","<%=new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D4")%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("verify","N")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untch002f01.jsp";
		} else {
			form1.action = "untch002f02.jsp";
		}
	} else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.verify,"異動單確認");
		alertStr += checkDate(form1.moveDate,"入帳日期");
		
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.moveDate,"若要入帳，入帳日期");
		}

		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
		
		alertStr += checkLen(form1.notes, "備註", 250);
		form1.action = "untch002f01.jsp";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();	
}
function queryOne(enterOrg,ownership,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function clickApproveRe(){
	if (confirm("是否要回復確認?")) {
		form1.moveDate.value="";
		form1.state.value="approveRe";
		beforeSubmit();
		form1.submit();
	}
}	

function checkURL(surl){
	columnTrim(form1.caseNo);
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else{
		form1.UNTCH002F01_currentPage.value = form1.currentPage.value;
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
	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormField("insert,update,delete", "R");
	}
	if(<%=checkDataCount%>){
		$("select[name='ownership']").attr('class','field');
		$("select[name='verify']").attr('class','field_P');
	}else{
		$("select[name='ownership']").attr('class','field_P');
		$("select[name='verify']").attr('class','field');
	}
	
	form1.moveDateTemp.value = form1.moveDate.value;
		
	if ('<%=user.getRoleid()%>' == '3'){
		setFormItem("verifyRe", "O");
	}else if (form1.isAdminManager.value == 'Y'){
		setFormItem("verifyRe", "O");
	}else{
		setFormItem("verifyRe", "R");
	}

	if(form1.verify.value=='Y'){	
		setFormItem("update,delete", "R");		
	}else{							
		setFormItem("update,delete", "O");
		setFormItem("verifyRe","R");
	}
	
	//此行要對照primaryArray所設定的pk值為何
	listContainerRowClick(form1.enterOrg.value + form1.ownership.value + form1.caseNo.value);

	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 財產管理 > > 財產移動單維護";
	
	//問題單2347，groupID非sys01身分別的使用者不顯示刪除按鈕
	if (form1.groupID.value != 'sys01' ) {
		setDisplayItem('delete','H');
	}
}

function loadUntrp002r(){
	var url = "../rp/untrp002r.jsp?"
		+ "organID=" + form1.organID.value
		+ "&q_enterOrg=" + form1.enterOrg.value 
		+ "&q_ownership=" + form1.ownership.value
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
		+ "&q_caseNoS=" + form1.caseNo.value
		+ "&q_caseNoE="+form1.caseNo.value
		+ "&q_proofYear=" + form1.proofYear.value
		+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
		+ "&q_proofNoS=" + form1.proofNo.value
		+ "&q_proofNoE=" + form1.proofNo.value
		+ "&q_kind=4"
		+ "&q_workKind=n";
	
	window.open(url);
}

function getMoveDate() {
	if (form1.verify.value=="Y") {
		if (form1.moveDate.value=="") form1.moveDate.value='<%=util.Datetime.getYYYMMDD()%>';
		setFormItem("moveDate,btn_moveDate","O");
	}else{
		form1.moveDate.value="";
		setFormItem("moveDate,btn_moveDate","R");
	}
}


</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<!--隱藏欄位===========================================================-->
<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1000px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH002Q",obj);%>
	<jsp:include page="untch002q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="5%">入帳機關：</td>
		<td colspan="3" class="td_form_white">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
					    <font color="red">　*</font>權屬：
			<select class="field_P" type="select" name="ownership">
			  <%=util.View.getOnwerOption(obj.getOwnership())%>
			  </select>
			  
			  <input class="field_QRO" type="hidden" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
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
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>移動單編號：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_RO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 
			號  		
		</td>
	</tr>
	<tr>
  	    <td class="td_form"><font color="red">*</font>異動單確認：</td>
    	<td colspan="3" class="td_form_white">
    		<select class="field" type="select" name="verify" onChange="getMoveDate();">
        		<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			 &nbsp;　　　移動日期：
			<input class="field" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>">
			<input class="field" type="button" name="btn_moveDate" onclick="popCalndar('moveDate');" value="..." title="萬年曆輔助視窗">
			<input class="field" type="hidden" name="moveDateTemp" size="7" maxlength="7" value="<%=obj.getMoveDateTemp()%>">          	
      	</td>
    </tr>	
	<tr>
		<td class="td_form">備註：</td>
		<td width="33%" class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
	</tr>	
	<tr style='display:none'>
	    <td width="20%" class="td_form"><span class="td_form">異動人員/日期：</span></td>
	    <td width="30%" class="td_form_white">[
	      <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	      /
          <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
          ] </td>
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
	<input type="hidden" name="cannotVerify" value="<%=user.getCannotVerify()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" name="UNTCH002F01_currentPage" value="<%=obj.getUNTCH002F01_currentPage()%>">
	<input type='hidden' name='q_userRole' value='<%= user.getRoleid()%>'>	
	<input type='hidden' name='q_keeperno' value='<%=user.getKeeperno() %>'>
	<input type='hidden' name='q_unitID' value='<%=user.getUnitID() %>'>
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="objPath" >
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanVerifyRe">
		<input class="toolbar_default" type="button" name="verifyRe" value="回復確認" onClick="clickApproveRe();">&nbsp;|
	</span>
	
	<span id="x1">
		<input class="toolbar_default" type="button" followPK="true" name="printLabel" value="列印移動單" onClick="loadUntrp002r();">&nbsp;|
		<input class="toolbar_default" type="button" followPK="true" name="printLabe2" value="列印標籤" onClick="loadUntmp051r01();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">移動單編號</a></th>
<!--    <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">電腦單號</a></th>-->		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">移動日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">確認</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,false,true,false,false,false};
	boolean displayArray[] = {false,false,true,true,false,true,true,true};
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
			if (!confirm("刪除此移動單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
			return false;			
			hasBeenConfirmed = true;
			break;		
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[0].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			}
			break;			
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			setFormItem("verify,moveDate,btn_moveDate","R");
			break;
		case "insertError":
			setFormItem("verify,moveDate,btn_moveDate","R");
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
				setFormItem("verifyRe,moveDate,btn_moveDate","R");
			}
		    break;
	}
}
</script>
</body>
</html>



