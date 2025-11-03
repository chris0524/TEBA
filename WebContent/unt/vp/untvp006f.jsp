<!--
程式目的：有價證券增減值作業－增減值單資料
程式代號：untvp006f
程式日期：0941013
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP006F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.vp.UNTVP006F)obj.queryOne();	
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
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"E2")%>"),
	new Array("ownership","1"),
	new Array("verify","N"),
	//new Array("adjustDate","<%=util.Datetime.getYYYMMDD()%>"),
	//new Array("adjustBookSheet","0"),
	new Array("adjustBookAmount","0"),
	new Array("adjustBookValue","0"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("closing","N")	
);

function getAdjustDate() {
	if (form1.verify.value=="Y") {
		if (form1.adjustDate.value=="") form1.adjustDate.value="<%=util.Datetime.getYYYMMDD()%>";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		if(form1.propertyNo.value=="" || form1.serialNo.value=="") alertStr+="請選取增加單資料!\n";
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"增減值單編號－字");
		alertStr += checkEmpty(form1.cause,"增減值原因");
		//alertStr += checkEmpty(form1.adjustDate,"入帳日期");
		alertStr += checkDate(form1.adjustDate,"入帳日期");
		//alertStr += checkFloat(form1.oldBookSheet,"原總張數",5,0);
		alertStr += checkFloat(form1.oldBookAmount,"原總股數",10,0);
		alertStr += checkFloat(form1.oldBookValue,"原總價",15,0);
		//alertStr += checkFloat(form1.adjustBookSheet,"增減總張數",5,0);
		alertStr += checkFloat(form1.adjustBookAmount,"增減總股數",10,0);
		alertStr += checkFloat(form1.adjustBookValue,"增減總價",15,0);
		//alertStr += checkFloat(form1.newBookSheet,"新總張數",5,0);
		alertStr += checkFloat(form1.newBookAmount,"新總股數",10,0);
		alertStr += checkFloat(form1.newBookValue,"新總價",15,0);
		alertStr += checkLen(form1.notes, "備註", 250);
		//當增減值原因選「其他」時，開放其他說明欄位且必須有資料
		if(form1.cause.value=="99") alertStr += checkEmpty(form1.cause1,"其他說明");
		else form1.cause1.value = "";
		getAdjustDate();
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
function changeSelect(){
	//當增減值原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "99") form1.cause1.readOnly = false;
	else{
		form1.cause1.readOnly = true;
		form1.cause1.value="";
	}
}

function checkURL(surl){
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{		
		form1.action=surl+"?initDtl=Y";
		form1.state.value="queryAll";
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	<!-- 查出的資料若其增減值已審核，均不允許修改、刪除該筆增減值單資料 -->
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
	var windowLeft=(document.body.clientWidth-600)/2+250;
	prop=prop+"width=600,height=380,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("popUntvpAddProof.jsp","",prop);
}

function clickApproveAll(){
	if(confirm("您確定要將列表中未入帳的增減值單全部入帳?")){
		document.all('state').value='approveAll';
		beforeSubmit();
		form1.submit();
	}
}

function goUntvp009r(){
	var url = "untvp009p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
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

function loadUntvp009r(){
	var url = "untvp009p.jsp?"
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
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTVP006Q",obj);%>
	<jsp:include page="untvp006q.jsp" />
</div>
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
  <tr>
    <td ID=t1 CLASS="tab_border1" HEIGHT="25">增減值單資料</td>
    <td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untvp007f.jsp');">增加股份</a></td>
    <td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untvp008f.jsp');">減少股份</a></td>
  </tr>
  <tr>
    <td class="tab_line1"></td>
    <td class="tab_line2"></td>
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
			<input class="toolbar_default" type="button" name="queryAddProof" value="增加單資料挑選" onClick="clickAddProof();" disabled=true>
			<input class="field_RO" type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
			<input class="field_P" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="18%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　<font color="red">*</font>權屬：
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
			&nbsp;&nbsp;&nbsp;　　　　　　案名：<input class="field" type="text" name="caseName" size="35" maxlength="25" value="<%=obj.getCaseName()%>">
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
		<td class="td_form"><font color="red">*</font>增減值單編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">
			字第 [ <input class="field_RO" type="text" name="proofNo" size="10" maxlength="10" value="<%=obj.getProofNo()%>"> ] 號
			&nbsp;　傳票號數：<input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>">
		</td>
	</tr>
	<tr>
  	    <td class="td_form"><font color="red">*</font>入帳：</td>
    	<td colspan="3" class="td_form_white"><select class="field" type="select" name="verify" onChange="getAdjustDate();">
        	<%=util.View.getYNOption(obj.getVerify())%>
			</select> &nbsp;　　　入帳日期：<%=util.View.getPopCalndar("field","adjustDate",obj.getAdjustDate())%>
          	&nbsp;&nbsp;　　　　月結：
          	<select class="field_RO" type="select" name="closing">
            	<%=util.View.getYNOption(obj.getClosing())%>
          	</select>
      	</td>
    </tr>	
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
	  		[<input class="field_PRO" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>">]
	   		&nbsp;　分號：[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
	 		&nbsp;　名稱：[<input class="field_RO" type="text" name="propertyNoName" size="25" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
		<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="15" maxlength="20" value="<%=obj.getPropertyName1()%>">]
			&nbsp;　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;　原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="propertyKind">
			<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
			</select>
			&nbsp;&nbsp;　　　　　　　　　基金財產：
			<select class="field_RO" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>增減值原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="cause" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAD'", obj.getCause())%>
			</select>
			&nbsp;&nbsp;　　　　　　　　　其他說明：<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">發行法人資料：</td>
		<td class="td_form_white" colspan="3">
			名稱：[<input class="field_RO" type="text" name="securityName" size="29" maxlength="15" value="<%=obj.getSecurityName()%>">]
			&nbsp;&nbsp;　　設立年月：[<input class="field_RO" type="text" name="securityTime" size="5" maxlength="5" value="<%=obj.getSecurityTime()%>">]
		<br>
			核准登記機關：<%=util.View.getPopOrgan("field_RO","securityOrg",obj.getSecurityOrg(),obj.getSecurityOrgName(),"Y")%>
			&nbsp;&nbsp;&nbsp;核准登記字號：[<input class="field_RO" type="text" name="securityDoc" size="15" maxlength="20" value="<%=obj.getSecurityDoc()%>">]
		<br>
			事業項目：[<input class="field_RO" type="text" name="securityItem" size="20" maxlength="25" value="<%=obj.getSecurityItem()%>">]
			&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　內容：[<input class="field_RO" type="text" name="securityMeat" size="15" maxlength="15" value="<%=obj.getSecurityMeat()%>">]
		<br>
			地址：[<input class="field_RO" type="text" name="securityAddr" size="50" maxlength="50" value="<%=obj.getSecurityAddr()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">股份資料：</td>
		<td class="td_form_white" colspan="3">
			
			原總股數：[<input class="field_RO" type="text" name="oldBookAmount" size="9" maxlength="12" value="<%=obj.getOldBookAmount()%>">]
			&nbsp;增減總股數：[<input class="field_RO" type="text" name="adjustBookAmountName" size="10" maxlength="12" value="<%=obj.getAdjustBookAmountName()%>">]
			<input class="field_RO" type="hidden" name="adjustBookAmount" size="10" maxlength="12" value="<%=obj.getAdjustBookAmount()%>">
			&nbsp;&nbsp;新總股數：[<input class="field_RO" type="text" name="newBookAmount" size="9" maxlength="12" value="<%=obj.getNewBookAmount()%>">]
		<br>
			原總價：[<input class="field_RO" type="text" name="oldBookValue" size="11" maxlength="15" value="<%=obj.getOldBookValue()%>">]
			&nbsp;　增減總價：[<input class="field_RO" type="text" name="adjustBookValueName" size="13" value="<%=obj.getAdjustBookValueName()%>">]
			<input class="field_RO" type="hidden" name="adjustBookValue" size="13" maxlength="15" value="<%=obj.getAdjustBookValue()%>">
			新總價：[<input class="field_RO" type="text" name="newBookValue" size="10" maxlength="15" value="<%=obj.getNewBookValue()%>">]
		<br>
			帳務摘要：<input class="field" type="text" name="bookNotes" size="30" maxlength="20" value="<%=obj.getBookNotes()%>">

		</td>
	</tr>
	<tr>
		<td class="td_form">保管單位：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="keepUnit">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '"+user.getOrganID()+"' order by unitno ", obj.getKeepUnit())%>
			</select>
			&nbsp;　　存放地點：[<input class="field_RO" type="text" name="place" size="30" maxlength="15" value="<%=obj.getPlace()%>">]
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
	<span id="spanUntvp009r">
		<input class="toolbar_default" type="button" followPK="true" name="untvp009r" value="列印增減值單" onClick="loadUntvp009r();">&nbsp;|
	</span>
	<span id = "spanVerifyRe">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">增減值原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">增減值日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">發行法人名稱</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,false,false,};
	boolean displayArray[] = {false,true,false,false,false,false,true,true,true,true,false,true,false,false,false,false,false,false,false,false,false,};
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
		if(!confirm("刪除此增減值單，將一併刪除其關聯的增加股份、減少股份!"))
			return false;
			hasBeenConfirmed = true;
			break;
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (parse(form1.q_checkQuery).length<=0)form1.q_checkQuery.value="Y";		
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
			//當增減值原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly=true;
			}else form1.cause1.readOnly=false;
			setFormItem("queryAddProof","O");
			setFormItem("verify","R");
			break;
		case "insertError":
			//當增減值原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value != "99"){
				form1.cause1.readOnly=true;
			}else form1.cause1.readOnly=false;
			setFormItem("queryAddProof","O");
			setFormItem("verify","R");
			break;
			
		//更新時要做的動作
		case "update":
			//當增減值原因不是「其他」時，鎖住其他說明欄位
			if(form1.cause.value == "99"){
				form1.cause1.readOnly = false;
			}else
				form1.cause1.readOnly = true;
			break;
		case "updateError":
			//當增減值原因不是「其他」時，鎖住其他說明欄位
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



