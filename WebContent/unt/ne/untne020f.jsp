<!--
程式目的：物品廢品處理作業－處理單資料
程式代號：untne020f
程式日期：0941212
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE020F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE020F)obj.queryOne();	
	obj.setQueryone_enterOrg(obj.getEnterOrg());
	obj.setQueryone_caseNo1(obj.getCaseNo1());
	obj.setQueryone_differenceKind(obj.getDifferenceKind());
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
	if ("insertSuccess".equals(obj.getState())) {
		obj.setQueryAllFlag("true");
		obj.setI_enterOrg(obj.getEnterOrg());
		obj.setI_caseNo1(obj.getCaseNo1());
		obj.setI_differenceKind(obj.getDifferenceKind());
	}
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
		if("".equals(obj.getQueryone_enterOrg()) ||  "".equals(obj.getQueryone_caseNo1())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setCaseNo1(((String[])objList.get(0))[2]);
		}
		obj = (unt.ne.UNTNE020F)obj.queryOne();		
	}
}

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
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","1"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("writeUnit","<%=user.getUnitID()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"G5")%>"),
	//new Array("dealDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("verify","N"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate", getToday())
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untne020f.jsp";
		} else {
			form1.action = "untne021f.jsp";
		}
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		//alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"處理單編號－字");
		//alertStr += checkEmpty(form1.dealDate,"處理日期");
		alertStr += checkDate(form1.dealDate,"處理日期");

		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
		
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
	if(parse(form1.enterOrgName.value).length<=0){
		setFormItem("print1", "R");
	}
	if ((form1.verify.value=="Y") || (form1.state.value=="updateError")) {
		setFormItem("update,delete", "R");
	}else{
		setFormItem("update,delete", "O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("update,delete,approveAll,verifyRe,untne023r", "R");
	}
	if (form1.cannotVerify.value == "Y")
	{
		setFormItem("verifyRe", "R");
	}
	if(form1.verify.value=="N"){
		setFormItem("verifyRe","R");
	}

	//此行要對照primaryArray所設定的pk值為何
	listContainerRowClick(form1.enterOrg.value + form1.caseNo1.value);
	
	if(<%=checkDataCount%>){		
		$("select[name='verify']").attr('class','field_P');
	}else{
		$("select[name='verify']").attr('class','field');
	}
	
	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 物品管理 > > 非消耗品廢品處理作業";
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


function getDealDate() {
	if (form1.verify.value=="Y") {
		if (form1.dealDate.value=="") form1.dealDate.value="<%=util.Datetime.getYYYMMDD()%>";
	}
	else if(form1.verify.value=="N") {
		form1.dealDate.value="";
		}
}
function loadUntne023r(){
	var url = "untne023r.jsp?"
			+ "organID=" + form1.organID.value 
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo1.value 
			+ "&q_caseNoE=" + form1.caseNo1.value 
			+ "&q_proofYear=" + form1.proofYear.value 
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value) 
			+ "&q_proofNoS=" + form1.proofNo.value 
			+ "&q_proofNoE=" + form1.proofNo.value ;
		

	window.open(url);
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位===========================================================-->
<input type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="checkDealDate" value="<%=obj.getDealDate()%>">
<input type="hidden" name="checkVerify" value="<%=obj.getVerify()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
<input class="field_P" type="hidden" name="ownership" value="1" > 
<input type="hidden" name="isUntne020f" value="Y">
<input type="hidden" name="saveLog" value="Y">
<input type="hidden" name="objPath" >
<!--Query區============================================================-->
<div id="queryContainer" style="width:900px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE020Q",obj);%>
	<jsp:include page="untne020q.jsp" />
</div>
<!--頁籤區=============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">*處理單資料</td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne021f.jsp');"><font color="red">*</font>處理單資料明細</a></td>		
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
	<tr style="display:none">
		<td class="td_form" width="15%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_P" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type=text name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>物品區分別：</td>
		<td colspan="3" class="td_form_white">
		<%=util.View._getSelectHTML("field", "differenceKind", obj.getDifferenceKind(),"DFK") %>	
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>填單日期：</td>
		<td class="td_form_white" colspan="3">		
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
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>處理單編號：</td>
		<td colspan="3" class="td_form_white" >
                <input class="field" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_P" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>"> 號  
		</td>		
	</tr>
	<tr style="display:none">
		<td class="td_form" width="15%">電腦單號：</td>
		<td class="td_form_white" colspan="3">
		    <table width="100%" height="100%" border="0">
              <tr>
                <td width="29%">[
                  <input class="field_PRO" type="text" name="caseNo1" size="10" maxlength="10" value="<%=obj.getCaseNo1()%>">]</td>
                <td width="18%" align="right"> 案名： </td>
                <td><input class="field" type="text" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>"></td>
              </tr>
            </table>
			</td>
	</tr>

	<tr>
		<td class="td_form" width="15%">處理日期：</td>
		<td colspan="3" class="td_form_white">
			<%=util.View.getPopCalndar("field","dealDate",obj.getDealDate())%>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		審核：<select class="field" type="select" name="verify" onChange="getDealDate();">
			<%=util.View.getYNOption(obj.getVerify())%>
        </select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">處理方式：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="reduceDeal" onChange="changeSelect();">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", obj.getReduceDeal())%>
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
	  <td class="td_form" width="15%">備註：</td>
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
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanVerifyRe">
		<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復審核" disabled="true" onClick="clickApproveRe();">&nbsp;|
	</span>
	<span id="spanUntne023r">
		<input class="toolbar_default" type="button" followPK="true" name="untne023r" value="列印物品廢品清冊" onClick="loadUntne023r();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">處理單編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">處理日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">處理方式</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">變賣總金額</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">轉撥單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">審核</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,true,true,true,true,true,true,true,false,false,false};
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
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");

	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
				form1.btn_shiftOrg.disabled=true;
				form1.shiftOrg.value="";
				form1.realizeValue.disabled=true;	
				//setFormItem("verify","R");
				$("select[name='verify']").attr('disabled',true);
			break;
		case "insertError":
				form1.btn_shiftOrg.disabled=true;
				form1.shiftOrg.value="";
				form1.realizeValue.disabled=true;	
				//setFormItem("verify","R");
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
			if(form1.verify.value=="Y"){
				setFormItem("verify","R");
			}else{
				setFormItem("verify","O");
			}
			break;
		//取消時要做的動作
		case "clear":
			setFormItem("print1", "R");
			setFormItem("approveAll","O");
			break;
		//確定時要做的動作
		case "confirm":
			setFormItem("approveAll,print1", "O");
			break;			
	}
};
</script>
</body>
</html>



