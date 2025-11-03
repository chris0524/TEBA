<!--
程式目的：物品增減值作業－增減值單資料
程式代號：untne024f
程式日期：0941121
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE024F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE024F)obj.queryOne();	
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
	String verify = obj.getVerify();
    String oldverify = obj.queryOldVerify();
    // 入帳時檢查
    if("N".equals(oldverify) && "Y".equals(verify)){
		if(obj.checkUpdateError()){
			obj.update();
			if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
		}
	}else if(oldverify.equals(verify)){
	    obj.update();
	    if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
	}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("approveAll".equals(obj.getState())) {
	obj.approveAll();
}else if ("approveRe".equals(obj.getState())) {
	if(obj.checReVerifyError()){
	obj.approveRe();
	}
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if("".equals(obj.getQueryone_enterOrg()) || "".equals(obj.getQueryone_caseNo())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setCaseNo(((String[])objList.get(0))[4]);
			obj.setDifferenceKind(((String[])objList.get(0))[9]);
		}
		obj = (unt.ne.UNTNE024F)obj.queryOne();		
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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownerShip","1"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	//new Array("adjustDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("verify","N"),
	new Array("writeUnit","<%=user.getUnitID()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"G3")%>")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untne024f.jsp";
		} else {
			form1.action = "untne025f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"增減值單編號－字");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.adjustDate,"若要入帳，入帳日期");
		}
		if (form1.verify.value=="N") {
          if((form1.adjustDate.value).length>0){
             alert("入帳為否，入帳日期不能輸入值。");
             return false;
             } 
		}
		
		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
		
		alertStr += checkDate(form1.adjustDate,"入帳日期");
		alertStr += checkDate(form1.approveDate,"核准日期");
		alertStr += checkLen(form1.notes, "備註", 250);
		if(form1.approveOrg.value=="02" || form1.approveOrg.value=="03" || form1.approveOrg.value=="04"|| form1.approveOrg.value=="05"|| form1.approveOrg.value=="06"){
			alertStr += checkEmpty(form1.approveDate,"核准日期");
			alertStr += checkEmpty(form1.approveDoc,"核准文號");
		}		
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
	if(confirm("您確定要將列表中未入帳的增減值單全部入帳?")){
		document.all('state').value='approveAll';
		beforeSubmit();
		form1.submit();
	}
}

function clickApproveRe(){
	if(confirm("您確定是否回復審核入帳?")){
		document.all('state').value='approveRe';
		beforeSubmit();
		form1.submit();
	}
}	

function checkURL(surl){
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{			
		if (document.all.querySelect[0].checked || form1.state.value=="insertSuccess") {
			surl=surl+"?initDtl=Y";
			form1.state.value="queryAll";
		}
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
	if(parse(form1.enterOrgName.value).length<=0){
		setFormItem("print1", "R");
	}
	var arrField = new Array("update","delete");
	if (form1.verify.value=="Y") {
		setFormItem("update,delete", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("update,delete,approveAll,verifyRe","R");
	}
	if(form1.cannotVerify.value == "Y")
	{
		setFormItem("verifyRe,verify","R");
	}
	form1.adjustDateTemp.value = form1.adjustDate.value;

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
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 物品管理 > > 非消耗品增減值單維護";
	
	if (form1.bookvalueFlag.value == "Y") {
		document.getElementById("hint").innerHTML = "單據內包含減值後原值為0之項目，請檢視是否變更為辦理減損。";
	}
	
	//問題單2347，groupID非sys01身分別的使用者不顯示刪除按鈕
	if (form1.groupID.value != 'sys01' ) {
		setDisplayItem('delete','H');
	}
}

function changeApproveOrg(){
	var approveOrg = parse(form1.approveOrg.value);
	if(approveOrg.length<=0){
		setFormItem("approveDate,btn_approveDate,approveDoc", "R");
		form1.approveDate.value="";
		form1.approveDoc.value="";
	}else{
		setFormItem("approveDate,btn_approveDate,approveDoc", "O");
	}
}

function getAdjustDate() {
	if (form1.verify.value=="Y") {
		if (form1.adjustDate.value == ""){
			form1.adjustDate.value = "<%=util.Datetime.getYYYMMDD()%>";
			setFormItem("adjustDate,btn_adjustDate,summonsDate,btn_summonsDate,summonsNo","O");
		}
		if (form1.summonsDate.value == ""){
			form1.summonsDate.value = "<%=util.Datetime.getYYYMMDD()%>";
		}		
	} else {
		form1.adjustDate.value = "";
		form1.summonsDate.value = "";
		form1.summonsNo.value = "";
		setFormItem("adjustDate,btn_adjustDate,summonsDate,btn_summonsDate,summonsNo","R");
	}
}

function loadUntne027r(){
	var url = "untne027r.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE=" + form1.caseNo.value 
			+ "&q_proofYear=" + form1.proofYear.value 
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_proofNoS=" + form1.proofNo.value 
			+ "&q_proofNoE=" + form1.proofNo.value 
			+ "&q_kind=4";
	window.open(url);
}

function loadUntne016r(){
	var url = "untne016r.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value
			+ "&q_ownership=" + form1.ownership.value
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_caseNoS=" + form1.caseNo.value
			+ "&q_caseNoE=" + form1.caseNo.value
			+ "&q_proofYear=" + form1.proofYear.value 
			+ "&q_proofNoS=" + form1.proofNo.value 
			+ "&q_proofNoE=" + form1.proofNo.value 
			+ "&q_kind=4";
	window.open(url);
}
function resetVerify(){
	var alertStr ="";
	if (form1.adjustDate.value != ""){
		alertStr = checkDate(form1.adjustDate,"入帳日期");
		if(alertStr.length!=0){ 
			$("select[name='verify']").val("N")
			$("input[name='summonsDate']").val("");
			alert(alertStr); 
			return false; 
		}else{
			$("select[name='verify']").val("Y");
			$("input[name='summonsDate']").val(form1.adjustDate.value);
		}
		
	}else{
		$("select[name='verify']").val("N");
		$("input[name='summonsDate']").val("");
	}
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
	<input type="hidden" name="cannotVerify" value="<%=user.getCannotVerify()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
	<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="objPath" >
	<input type="hidden" name="bookvalueFlag" value="<%=obj.getBookvalueFlag()%>">
</td></tr></table>

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">*增減值單資料</td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne025f.jsp');"><font color="red">*</font>增減值單明細資料</a></td>	
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>	
	</tr>
</TABLE>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1000px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE024Q",obj);%>
	<jsp:include page="untne024q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
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
		&nbsp;　　　<font color="red">*</font>物品區分別：<%=util.View._getSelectHTML("field_P", "differenceKind", obj.getDifferenceKind(),"DFK") %>
		</td>
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
		<!-- 　物品管理單位編號：-->
		<input class="field" type="hidden" name="manageNo" size="15" maxlength="10" value="<%=obj.getManageNo()%>">
       	</td>
	</tr>
	<tr style="display:none">
		<td class="td_form" width="15%">電腦單號：</td>
		<td class="td_form_white" colspan="3">
		    <table width="100%" height="100%" border="0">
              <tr>
                <td width="29%">[
                  <input class="field_PRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]</td>
                <td width="18%" align="right"> 案名： </td>
                <td><input class="field" type="text" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>"></td>
              </tr>
            </table>
			</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>增減值單編號：</td>
		<td class="td_form_white" colspan="3">
			<table width="100%" height="100%" border="0">
              <tr>
                <td width="55%">
                 <input class="field" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
					年
					<input class="field" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
					字	第 
					<input class="field_P" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 號
					&nbsp;
					<font id="hint" color="red"></font>
				</td>
              </tr>
            </table>
		</td> 
	</tr>
	<tr>
		<td class="td_form" width="15%">核准機關：</td>
		<td class="td_form_white" colspan="3">
            <select class="field" type="select" name="approveOrg" onChange="changeApproveOrg();">
              <%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ",obj.getApproveOrg())%>
            </select>
			　核准日期：<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
			　核准文號：<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
		</td>
	</tr>
	<tr>
  	    <td class="td_form" width="15%"><font color="red">*</font>入帳：</td>
    	<td colspan="3" class="td_form_white"><select class="field" type="select" name="verify" onChange="getAdjustDate();">
        	<%=util.View.getYNOption(obj.getVerify())%>
			</select>&nbsp;　　　入帳日期：
				<%=util.View.getPopCalndar2("field","verify","adjustDate","summonsDate",obj.getAdjustDate())%>
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
		<td width="15%" class="td_form" >備註：</td>
		<td class="td_form_white" >	
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea></td>
		<td width="15%" class="td_form"style="display:none;">異動人員/日期：</td>
		<td class="td_form_white"style="display:none;">
			[<input class="field_RO" type="texta" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />

	<span id="spanVerifyRe">
		<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復入帳" onClick="clickApproveRe();">&nbsp;|	
	</span>
	<br>|&nbsp;
	<span id="spanUntne027r">
		<input class="toolbar_default" type="button" followPK="true" name="untne027r" value="列印增減值單" onClick="loadUntne027r();">&nbsp;|
	</span>	
	<!--  
	<span id="spanUntne016r">
		<input class="toolbar_default" type="button" followPK="true" name="untne016r" value="列印毀損報廢單" onClick="loadUntne016r();">&nbsp;|
	</span>
	-->
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">增減值單號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,true,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,false,true,true,true,true,false};
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
		//刪除之前多出現一道確認訊息
       	case "delete":
		if(!confirm("刪除此增減值單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
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
			setFormItem("verify,adjustDate,btn_adjustDate,approveDate,btn_approveDate,approveDoc,summonsDate,btn_summonsDate,summonsNo","R");
			break;
		//更新時要做的動作
		case "update":
			//若「核准機關」無資料
			var approveOrg = parse(form1.approveOrg.value);
			if(approveOrg.length<=0){
				setFormItem("approveDate,btn_approveDate,approveDoc", "R");
			}else{
				setFormItem("approveDate,btn_approveDate,approveDoc", "O");
			}
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
				setFormItem("verifyRe,adjustDate,btn_adjustDate,summonsDate,btn_summonsDate,summonsNo","R");
			}
			break;
		//取消時要做的動作
		case "clear":
			setFormItem("print1", "R");
			setFormItem("approveAll","O");
			if (form1.verify.value=="N") {
				setFormItem("verifyRe","R");
			}
			break;
		//確定時要做的動作
		case "confirm":
			setFormItem("approveAll,print1", "O");
			break;			
	}
}
</script>
</body>
</html>
