<!--
程式目的：物品保管使用異動作業－移動單資料
程式代號：untne008f
程式日期：0950221
程式作者：Cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE008F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	if (!"".equals(obj.getUNTNE008F_currentPage())) {
		obj.setCurrentPage(Integer.parseInt(obj.getUNTNE008F_currentPage()));
		obj.setUNTNE008F_currentPage("");
	}

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE008F)obj.queryOne();	
	obj.setQueryone_enterOrg(obj.getEnterOrg());
	obj.setQueryone_ownership(obj.getOwnership());
	obj.setQueryone_caseNo(obj.getCaseNo());
	obj.setQueryone_differenceKind(obj.getDifferenceKind());
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
	if ("insertSuccess".equals(obj.getState())) {
		obj.setQueryAllFlag("true");
		obj.setI_enterOrg(obj.getEnterOrg());
		obj.setI_ownership(obj.getOwnership());
		obj.setI_caseNo(obj.getCaseNo());
		obj.setI_differenceKind(obj.getDifferenceKind());
	}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	if(obj.checkUpdateError()){
	    obj.update();
	}
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("approveAll".equals(obj.getState())) {
	obj.approveAll();
}else if ("approveRe".equals(obj.getState())) {
	if(obj.checkReVerifyError()){
		obj.approveRe();
	}
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("".equals(obj.getQueryone_enterOrg()) || "".equals(obj.getQueryone_ownership()) || "".equals(obj.getQueryone_caseNo())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setOwnership(((String[])objList.get(0))[1]);
			obj.setCaseNo(((String[])objList.get(0))[4]);
		}
		obj = (unt.ne.UNTNE008F)obj.queryOne();		
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
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","1"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("writeUnit","<%=user.getUnitID()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"G4")%>"),
	new Array("verify","N")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untne008f.jsp";
		} else {
			form1.action = "untne009f.jsp";
		}
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"移動單編號－字");
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.moveDate,"若要入帳，入帳日期");
		}
		if (form1.verify.value=="N") {
          if((form1.moveDate.value).length>0){
             alert("異動單確認為否，入帳日期不能輸入值。");
             return false;
             } 
		}
		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
		alertStr += checkDate(form1.moveDate,"移動日期");
		alertStr += checkEmpty(form1.verify,"異動單確認");
		alertStr += checkLen(form1.notes,"備註",250);
		changeMoveDate();
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.caseNo.value=caseNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function clickApproveAll(){
	if(confirm("您確定要將列表中未入帳的移動單全部入帳?")){
		document.all('state').value='approveAll';
		beforeSubmit();
		form1.submit();
	}
}

function clickApproveRe(){
	if(confirm("您確定是否回復審核入帳?")){
		if(confirm("請您再次確定是否回復審核入帳?")){
			document.all('state').value='approveRe';
			beforeSubmit();
			form1.submit();
		}
	}
}	

function checkURL(surl){
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		form1.state.value="queryAll";
		if (document.all.querySelect[0].checked || form1.state.value=="insertSuccess") {
			surl=surl+"?initDtl=Y";
			document.all.querySelect[0].checked=true;
		}
		form1.UNTNE008F_currentPage.value = form1.currentPage.value;
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
	var arrField = new Array("update","delete");
	if (form1.verify.value=="Y"){
		setFormItem("update,delete","R");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormField("update,delete,approveAll,verifyRe","R");
	}
	if(form1.cannotVerify.value=="Y"){
        form1.verifyRe.disabled=true;
	}

	
	//changeQ_UnitByBureau(this.name);
	form1.moveDateTemp.value = form1.moveDate.value;

	if ('<%=user.getRoleid()%>' == '3'){
		form1.verifyRe.disabled = false;
		setFormItem("verifyRe", "O");
	}else if (form1.isAdminManager.value == 'Y'){
		form1.verifyRe.disabled = false;
		setFormItem("verifyRe", "O");
	}else{
		form1.verifyRe.disabled = true;
		setFormItem("verifyRe", "R");
	}
	
	if(form1.verify.value=="N"){
		setFormItem("verifyRe","R");
	}
	
	//此行要對照primaryArray所設定的pk值為何
	listContainerRowClick(form1.enterOrg.value + form1.caseNo.value);

	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 物品管理 > > 非消耗品移動單維護";
	
	//問題單2347，groupID非sys01身分別的使用者不顯示刪除按鈕
	if (form1.groupID.value != 'sys01' ) {
		setDisplayItem('delete','H');
	}
}


function changeMoveDate(){
	form1.newMoveDate.value=form1.moveDate.value;
}

function gountne011r(){
	var url = "untne011r.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			//+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE=" + form1.caseNo.value 
			+ "&q_proofYear=" + form1.proofYear.value 
			+ "&q_proofNoS=" + form1.proofNo.value 
			+ "&q_proofNoE=" + form1.proofNo.value 
			+ "&q_kind=4";
	window.open(url);
}

//保管人移交清冊
function gountne012r(){
	var url = "untne012r.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE="+form1.caseNo.value ;
	window.open(url);
}

function getMoveDate() {
	if (form1.verify.value=="Y") {
		if (form1.moveDate.value == ""){
			form1.moveDate.value = "<%=util.Datetime.getYYYMMDD()%>";
		}
		setFormItem("moveDate,btn_moveDate","O");
	} else {
		form1.moveDate.value = "";
		setFormItem("moveDate,btn_moveDate","R");
	}
}
function openManWindow(){

	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=750,height=500,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../sys/ca/sysca004f.jsp","",prop);
}
function openUnitWindow(){

	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=750,height=500,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../sys/ca/sysca003f.jsp","",prop);
}

function loadUntne051r(){
	var url = "untne051r01.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE="+form1.caseNo.value
			+ "&q_proofYear=" + form1.proofYear.value 
			//+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_proofNoS=" + form1.proofNo.value 
			+ "&q_proofNoE=" + form1.proofNo.value 
			+ "&q_workKind=n";
	window.open(url);
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
	<input type="hidden" name="newMoveDate" value="<%=obj.getNewMoveDate()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="cannotVerify" value="<%=user.getCannotVerify()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
	<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" name="UNTNE008F_currentPage" value="<%=obj.getUNTNE008F_currentPage()%>">	
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="objPath" >
</td></tr></table>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1000px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE008Q",obj);%>
	<jsp:include page="untne008q.jsp" />
</div>

<!--標籤區============================================================-->
<table style="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">*移動單資料</td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne009f.jsp');"><font color="red">*</font>移動單明細資料</a></td>		
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
	</tr>
</table>

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr style="display:none">
		<td class="td_form" width="15%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
		<input class="field_P" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
		[<input class="field_PRO" type=text name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]</td>
	</tr>
		<tr>
		<td class="td_form" width="15%"><font color="red">*</font>權屬：</td>
		<td class="td_form_white" colspan="3">		
		<select class="field_P" type="select" name="ownership">
		<%=util.View.getOnwerOption(obj.getOwnership())%>			
		</select>
		&nbsp;　　
		</td>
	</tr>	
	<tr style="display:none">
		<td class="td_form" width="15%">電腦單號：</td>
		<td colspan="3" class="td_form_white">
			<p>[<input class="field_PRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
			　　　　　　 &nbsp;&nbsp;案名：
			  <input class="field" type="text" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
			</p>			</td>
		</tr>
	<tr>
		<td class="td_form" width="15%">填單日期：</td>
		<td colspan="3" class="td_form_white">
		　<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
		填造單位：
		<select class="field" type="select" name="writeUnit">
		<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "'",obj.getWriteUnit())%>
		</select>
		<input class="field_Q" type="button" name="btn_q_writeUnit" onclick="popUnitNo(form1.organID.value,'writeUnit')" value="..." title="單位輔助視窗">
		</td>
		</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>移動單編號：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_RO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 號
		</td>
	</tr>
	<tr>
  	    <td class="td_form" width="15%"><font color="red">*</font>異動單確認：</td>
    	<td colspan="3" class="td_form_white">
    		<select class="field" type="select" name="verify" onChange="getMoveDate();">
        		<%=util.View.getYNOption(obj.getVerify())%>
			</select> &nbsp;　　　移動日期：
				<input class="field" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>" onChange="changeMoveDate();">
				<input class="field" type="button" name="btn_moveDate" onclick="popCalndar('moveDate');changeMoveDate();" value="..." title="萬年曆輔助視窗">
      			<input class="field" type="hidden" name="moveDateTemp" size="7" maxlength="7" value="<%=obj.getMoveDateTemp()%>">
      	</td>
    </tr>
	<tr>
		<td class="td_form" width="15%">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		</td>
	    <td class="td_form" style="display:none">異動人員/日期：</td>
		<td class="td_form_white" style="display:none">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
		</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanVerifyRe">
		<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復確認" disabled="true" onClick="clickApproveRe();">&nbsp;|	
	</span>
	<br>|&nbsp;
	<span id="spanUntne011r">
		<input class="toolbar_default" type="button" followPK="true" name="untne011r" value="列印移動單" disabled="true" onClick="gountne011r();">&nbsp;|
	</span>
	<span id="spanUntne000r">
		<input class="toolbar_default" type="button" followPK="true" name="untne000r" value="列印標籤" onClick="loadUntne051r();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">移動日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">確認</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,true,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,false,false,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
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
			setFormItem("verify,moveDate,btn_moveDate","R");
			break;
		//更新時要做的動作
		case "update":			
			if ('<%=user.getRoleid()%>' == '3'){
				setFormItem("verify","O");
			}else{
				setFormItem("verify","R");
			}			
			if ('<%=user.getIsAdminManager()%>' == 'Y'){
				setFormItem("verify","O");
			}
			if(form1.verify.value=="Y"){
				// 入帳後 僅可透過回復入帳 做單據資料異動
				setAllReadonly();
			} else {
				setFormItem("verifyRe,moveDate,btn_moveDate","R");
			}
			break;
		case "clear":
			if (form1.verify.value=="N") {
				setFormItem("verifyRe","R");
			}
			break;
	}
}
</script>
</body>
</html>



