<!--
程式目的：非消耗品主檔資料維護－增加單資料
程式代號：untne001f
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setRoleid(user.getRoleid());

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){
	obj.setQ_enterOrg(user.getOrganID());
}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){
	obj.setQ_enterOrgName(user.getOrganName());
}
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE001F)obj.queryOne();
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
		   if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
	}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
	obj.setCaseNo("");
	obj.setOwnership("");
	obj.setDifferenceKind("");
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("approveAll".equals(obj.getState())) {
	// obj.approveAll();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	// 客戶734 這邊不清空來回切換增加單跟基本資料 會是第一次查詢第一筆或上一筆手動點擊明細的資料
	obj.setSerialNo("");
	obj.setLotNo("");	
	if (!objList.isEmpty()) {
		if("".equals(obj.getQueryone_enterOrg()) ||  "".equals(obj.getQueryone_caseNo())){
			obj.setEnterOrg(((String[])objList.get(0))[0]);
			obj.setCaseNo(((String[])objList.get(0))[5]);
		}
		obj = (unt.ne.UNTNE001F)obj.queryOne();
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"G1")%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("writeUnit","<%=user.getUnitID()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("ownership","1"),
	new Array("verify","N"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>")
);

function getEnterDate() {
	if (form1.verify.value=="Y") {
		if (form1.summonsDate.value == ""){
			form1.summonsDate.value = "<%=util.Datetime.getYYYMMDD()%>";
		}
		if (form1.enterDate.value == ""){
			form1.enterDate.value = "<%=util.Datetime.getYYYMMDD()%>";
		}
		setFormItem("enterDate,btn_enterDate,summonsDate,btn_summonsDate,summonsNo", "O");
	} else {
		form1.enterDate.value = "";
		form1.summonsDate.value = "";
		form1.summonsNo.value = "";
		setFormItem("enterDate,btn_enterDate,summonsDate,btn_summonsDate,summonsNo", "R");
	}
}

function checkField(){


	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untne001f.jsp";
		} else if (document.all.querySelect[2].checked) {
			form1.action = "untne003f.jsp";
		} else {
			form1.action = "untne002f.jsp";
			form1.caseNo.value = "";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"增加單編號－字");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.enterDate,"若要入帳，入帳日期");
		}
		if (form1.verify.value=="N") {
          if((form1.enterDate.value).length>0){
             alert("入帳為否，入帳日期不能輸入值。");
             return false;
          } 
		}

		var today = '<%=util.Datetime.getYYYMMDD()%>';
		if(form1.writeDate.value > today){
			alertStr += "填單日期不可大於今日";
		}
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkLen(form1.notes, "備註", 250);
		
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
	if(confirm("您確定要將列表中未入帳的增加單全部入帳?")){
		document.all('state').value='approveAll';
		form1.checkEnterOrg.value="<%=user.getOrganID()%>";
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
		form1.state.value="queryAll";	
		if (document.all.querySelect[0].checked || document.all.querySelect[2].checked || form1.state.value=="insertSuccess") {
			surl=surl+"?initDtl=Y";
			if (document.all.querySelect[2].checked) {
				form1.state.value = "queryOne";	
				}
		}else if(document.all.querySelect[1].checked){
			if (form1.caseNo.value!="" && form1.ownership.value!="" && form1.enterOrg.value!="" && form1.lotNo.value!="") 
				form1.state.value = "queryAll";		
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
		setFormItem("print1,print2,print3", "R");
	}
	
		//setFormItem("update,delete", "R");
		//查出的「單據資料」若「已入帳」，僅供財管人員修改，均不允許刪除單據資料
	if (form1.verify.value == "Y") {
		if (<%=!user.getGroupID().contains("sys")%>) {
			setFormItem("update", "R");
		}
		setFormItem("delete", "R");
	} 
		
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("update,delete,approveAll","R");
	}

	if(<%=checkDataCount%>){
		$("select[name='verify']").attr('class','field_P');
	}else{
		$("select[name='verify']").attr('class','field');
	}
	
	form1.enterDateTemp.value = form1.enterDate.value;
	
	//此行要對照primaryArray所設定的pk值為何
	listContainerRowClick(form1.enterOrg.value + form1.caseNo.value);

	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 物品管理 > > 非消耗品增加單維護";
	
	//問題單2313，groupID非sys01身分別的使用者不顯示刪除按鈕
	if (form1.groupID.value != 'sys01' ) {
		setDisplayItem('delete','H');
	}
}

function loadUntne005r(){
var url = "untne005r.jsp?"
		+ "organID=" + form1.organID.value 
		+ "&q_enterOrg=" + form1.enterOrg.value 
		+ "&q_ownership=" + form1.ownership.value 
		+ "&q_caseNoS=" + form1.caseNo.value 
		+ "&q_caseNoE=" + form1.caseNo.value 
		+ "&q_proofYear=" + form1.proofYear.value 
		+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
		+ "&q_proofNoS=" + form1.proofNo.value 
		+ "&q_proofNoE=" + form1.proofNo.value 
		+ "&q_kind=4"
		+ "&q_detail=Y";

window.open(url);
}


function loadUntne006r(){
	var url = "untne006r.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE="+form1.caseNo.value 
			+ "&q_proofYear=" + form1.proofYear.value 
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value) 
			+ "&q_proofNoS=" + form1.proofNo.value 
			+ "&q_proofNoE=" + form1.proofNo.value 
			+ "&q_workKind=a";
	window.open(url);
}

function loadUntne051r(){
	var url = "untne051r01.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE="+form1.caseNo.value
			+ "&q_proofYear=" + form1.proofYear.value 
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_proofNoS=" + form1.proofNo.value 
			+ "&q_proofNoE=" + form1.proofNo.value 
			+ "&q_workKind=a";
	window.open(url);
}

function loadUntne061r() {
	var url = "untne061r.jsp?"
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_proofYear=" + form1.proofYear.value 
			+ "&q_proofDoc=" + encodeURIComponent(form1.proofDoc.value)
			+ "&q_proofNo=" + form1.proofNo.value;

	window.open(url);
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
</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td >
	<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
	<input type="hidden" name="lotNo" value="<%=obj.getLotNo()%>">
	<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
	<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">
	<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
	<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">	
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">	
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="objPath" >
</td></tr></table>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE001Q",obj);%>
	<jsp:include page="untne001q.jsp" />
</div>

<!--頁籤區=============================================================-->
<TABLE style="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">*增加單資料</td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untne002f.jsp');"><font color="red">*</font>基本資料</a></td>		
		<td ID=t3 CLASS="tab_border2">物品明細</td>
		<td ID=t4 CLASS="tab_border2">附屬設備</td>
		<td ID=t5 CLASS="tab_border2">附屬設備明細</td>
		<td ID=t5 CLASS="tab_border2">帳務資料</td>
		<td ID=t5 CLASS="tab_border2">移動紀錄</td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
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
			<input class="field_RO" type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
			<input class="field_P" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type=text name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]</td>
	</tr>
		<tr>
		<td class="td_form" width="15%"><font color="red">*</font>權屬：</td>
		<td class="td_form_white" colspan="3">		
		<select class="field_P" type="select" name="ownership">
		<%=util.View.getOnwerOption(obj.getOwnership())%>			
		</select>
		&nbsp;　　　<font color="red">*</font>物品區分別： <%=util.View._getSelectHTML("field_P", "differenceKind", obj.getDifferenceKind(),"DFK",null," and codeID<>'500' ") %>	
		</td>
	</tr>	
	<tr>
		<td class="td_form" width="15%">填單日期：</td>
		<td colspan="3" class="td_form_white">
	   <%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
	         填造單位： <select class="field" type="select" name="writeUnit">
		<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "'",obj.getWriteUnit())%>
		</select>
		<input class="field_Q" type="button" name="btn_q_writeUnit" onclick="popUnitNo(form1.organID.value,'writeUnit')" value="..." title="單位輔助視窗">
		<!-- 　物品管理單位編號：-->
		<input class="field" type="hidden" name="manageNo" size="15" maxlength="10" value="<%=obj.getManageNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>增加單編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			[<input class="field_QRO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>">] 號
			&nbsp;&nbsp;　　
		</td>
	</tr>
    <tr>
  	    <td class="td_form" width="15%"><font color="red">*</font>入帳：</td>
    	<td colspan="3" class="td_form_white"><select class="field" type="select" name="verify" onChange="getEnterDate();">
        	<%=util.View.getYNOption(obj.getVerify())%>
			</select> &nbsp;&nbsp;　　　入帳日期：<%=util.View.getPopCalndar2("field","verify","enterDate","summonsDate",obj.getEnterDate())%>
			<input type="hidden" class="field_QRO" name="enterDateTemp" value="<%=obj.getEnterDateTemp()%>" size="7">              
      	</td>
    </tr>
	<tr style="display:none">
		<td class="td_form" width="15%">電腦單號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>">]
			　　　　　　　　案名：
			<input class="field" type="text" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
		</td>
	</tr>
	  <tr>
		<td class="td_form">傳票日期：</td>
		<td colspan="3" class="td_form_white" >
			<%=util.View.getPopCalndar("field","summonsDate",obj.getSummonsDate())%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			傳票號數：
			<input class="field" type="text" name="summonsNo" size="50" maxlength="50" value="<%=obj.getSummonsNo()%>">              
			
		</td>		
	</tr>
	<tr>
	    <td class="td_form" width="15%">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		  </td>
		<td class="td_form" style="display:none">異動人員/日期：</td>
		<td class="td_form_white" style="display:none">
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
	<br>|&nbsp;
	<span id="spanUntne005r">
		<input class="toolbar_default" type="button" followPK="true" name="untne005r" value="列印增加單" onClick="loadUntne005r();">&nbsp;|
	</span>
	<span id="spanUntne006r">
		<input class="toolbar_default" type="button" followPK="true" name="untne006r" value="列印登記卡" onClick="loadUntne006r();">&nbsp;|
	</span>
	<span id="spanUntne000r">
		<input class="toolbar_default" type="button" followPK="true" name="untne000r" value="列印標籤" onClick="loadUntne051r();">&nbsp;|
	</span>
	<span id="spanUntne061r">
		<input class="toolbar_default" type="button" followPK="true" name="untne061r" value="列印折舊沖回明細表" onClick="loadUntne061r();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">增加單編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳日期</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,false,true,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,false,false,true,true,true};
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
		if(!confirm("刪除此增加單，將一併刪除其關聯的資料：\n基本資料、\n物品明細、\n附屬設備、\n附屬設備明細。\n\n您確定要刪除?"))
			return false;
			hasBeenConfirmed = true;
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[2].checked==true) {} 
			else form1.querySelect[1].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				
//				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
//				changeBureau(form1.q_enterOrg, form1.q_keepBureau, '');
//				changeBureau(form1.q_enterOrg, form1.q_useBureau, '');
//				changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'');
//				changeKeepUnit(form1.q_enterOrg, form1.q_useBureau, form1.q_useUnit,'');
			}
			break;				
	}
	return true;
}
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
			setFormItem("verify,enterDate,btn_enterDate,summonsDate,btn_summonsDate,summonsNo","R");
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
				setAllReadonly();
				setFormItem("verify,enterDate,btn_enterDate,summonsDate,btn_summonsDate,summonsNo","O");
			} else {
				setFormItem("enterDate,btn_enterDate,summonsDate,btn_summonsDate,summonsNo","R");
			}
			
			break;					
	}
}

</script>
</body>
</html>



