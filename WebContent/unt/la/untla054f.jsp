<!--
程式目的：土地合併分割重測重劃作業－案件資料
程式代號：untla054f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA054F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String message = "";
if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA054F) obj.queryOne();
}else if ("insert".equals(obj.getState())) {	
	if("Y".equals(obj.getVerify())){
		message = obj.execVerifyCheck();
		if("".equals(message)){	
			obj.insert();
			obj.execVerifyProduce();
		}
	}else{
		obj.insert();
	}
}else if ("update".equals(obj.getState())) {
		if("Y".equals(obj.getVerify())){
			message = obj.execVerifyCheck();
			if("".equals(message)){	
				obj.update();	
				obj.execVerifyProduce();
			}
		}else{
			obj.update();
		}
}else if ("delete".equals(obj.getState())) {
	obj.delete();
	obj.clearAllDataForView();
	obj.setCaseNo("");
	obj.setCaseNo_Merge("");
	obj.setCaseNo_Reduce("");
	obj.setCaseNo_Add("");
	
}else if ("approveRe".equals(obj.getState())) {
	if(obj.checkUpdateError()){
		obj.execCheckCanApproveRe();
		obj = (unt.la.UNTLA054F)obj.queryOne();	
	}else{
		obj.setQueryAllFlag("true");
	}
	
}else if ("insertError".equals(obj.getState()) || "updateError".equals(obj.getState()) || "deleteError".equals(obj.getState())){
	obj.clearAllDataForView();

}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){
		obj.setQueryAllFlag("true"); 
	}
}else if("init".equals(obj.getState())) {
	obj.clearAllDataForView();
}else{
	obj.setQueryAllFlag("true");	
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if(!objList.isEmpty() && !obj.getState().contains("queryOne")) {
		obj.setEnterOrg(((String[])objList.get(0))[0]);
		obj.setOwnership(((String[])objList.get(0))[1]);
		obj.setDifferenceKind(((String[])objList.get(0))[3]);
		obj.setCaseNo(((String[])objList.get(0))[5]);
		obj.setCaseNo_Merge(((String[])objList.get(0))[5]);
		obj.setCaseNo_Add(((String[])objList.get(0))[10]);
		obj.setCaseNo_Reduce(((String[])objList.get(0))[11]);
		obj = (unt.la.UNTLA054F)obj.queryOne();			
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
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var message = '<%=message%>';

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","1"),
	new Array("verify","N")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkEmpty(form1.cause,"原因");
		alertStr += checkEmpty(form1.verify,"入帳");
		
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.approveDate,"核准日期");
		
		alertStr += checkLen(form1.notes,"備註",250);

		if(form1.verify.value=='Y'){
			alertStr += checkEmpty(form1.enterDate,"入帳日期");			
			alertStr += checkEmpty(form1.approveOrg,"核准機關");
			alertStr += checkEmpty(form1.approveDate,"核准日期");
			alertStr += checkEmpty(form1.approveDoc,"核准文號");
		}	
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,differenceKind,caseNo){
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.caseNo_Merge.value=caseNo;
	form1.enterOrg.value=enterOrg;
	form1.differenceKind.value=differenceKind;		
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){	
	
	form1.q_enterOrg.value="<%=user.getOrganID()%>";
	form1.q_enterOrgName.value="<%=user.getOrganName()%>";

	setDisplayItem("spanNextInsert", "H");
	setFormItem("update,delete,verifyRe","R");
	
	//查出的資料若其「案件資料」已入帳
	if(form1.verify.value=='Y'){
		setFormItem("verifyRe","O");
		setFormItem("update,delete","R");
		form1.alreadyVerify.value="Y";
	}else{
		setFormItem("update,delete","O");
		setFormItem("verifyRe","R");
		form1.alreadyVerify.value="N";
	}

	//非登入者所屬機關所登錄的資料
	if(form1.organID.value==form1.enterOrg.value){		
		form1.enterorgNotConfirm.value="N";
	}else{
		form1.enterorgNotConfirm.value="Y";
		setFormItem("update,delete,verifyRe","R");	
	}

	//若沒有選擇任何資料
	if(form1.caseNo.value==''){			
		setFormItem("update,delete,verifyRe","R");		
	}
	
	if(form1.isOrganManager.value=='Y'){		checkCanQueryWhat="isOrganManager";				
	}else if(form1.isAdminManager.value=='Y'){	checkCanQueryWhat="isAdminManager";
	}else{										checkCanQueryWhat="";
	}

	if (message!=''){alert(message);message='';}
	
	if(<%=checkDataCount%>){		
		$("select[name='verify']").attr('class','field_P');
	}else{
		$("select[name='verify']").attr('class','field');
	}
	
	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 財產管理 > > 土地合併分割作業";
}


function checkURL(surl){
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
		
	}else{
		if(surl=='untla056f.jsp' && form1.caseNo_Reduce.value==''){	alert('請先新增一筆減損單！！');return false;}	
		if(surl=='untla058f.jsp' && form1.caseNo_Add.value==''){	alert('請先新增一筆增加單！！');return false;}
		if(surl=='untla055f.jsp' ){
			form1.action=surl+"?cause="+form1.cause.value+"&initDtl=Y";}
		else{
				form1.action=surl+"?initDtl=Y";
			}
		form1.state.value="queryAll";	
				
		beforeSubmit();
		form1.submit();
	}
}

function clickApproveRe(){
	if(confirm("您確定是否回復入帳?")){
		document.all('state').value='approveRe';
		beforeSubmit();
		form1.submit();
	}
}	

function getEnterDate() {
	if (form1.verify.value=="Y") {
		if (form1.enterDate.value=="") form1.enterDate.value='<%=util.Datetime.getYYYMMDD()%>';
	}else{
		form1.enterDate.value="";
	}
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">*案件資料</td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla055f.jsp');"><font color="red">*</font>減損單</a></td>
		<td ID=t3 CLASS="tab_border2"><font color="red">*</font>減損單明細</td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla057f.jsp');"><font color="red">*</font>增加單</a></td>
		<td ID=t5 CLASS="tab_border2"><font color="red">*</font>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2">增加單管理資料</td>
		<td ID=t7 CLASS="tab_border2">增加單地上物資料</td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla066f.jsp');">調整前後對應資料</a></td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>					
	</tr>
</TABLE>
<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">

<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="hidden" name="divisionReduce" value="<%=obj.getDivisionReduce()%>">
<input class="field_QRO" type="hidden" name="divisionAdd" value="<%=obj.getDivisionAdd()%>">

<input class="field_QRO" type="hidden" name="propertyNo_Reduce" value="<%=obj.getPropertyNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="propertyNo_Add" value="<%=obj.getPropertyNo_Add()%>">
<input class="field_QRO" type="hidden" name="serialNo_Reduce" value="<%=obj.getSerialNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="serialNo_Add" value="<%=obj.getSerialNo_Add()%>">

<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
<input type="hidden" name="saveLog" value="Y">
<input type="hidden" name="objPath" >

<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA054Q",obj);%>
	<jsp:include page="untla054q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="5%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]&nbsp;&nbsp;&nbsp;&nbsp;　
			<font color="red">*</font>
			權屬：
			<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<font color="red">*</font>財產區分別：
			<%=util.View._getSelectHTML("field_P", "differenceKind", obj.getDifferenceKind(),"DFK") %>
		</td>
	</tr>
	<tr>
		<td class="td_form">案號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="caseNo_Merge" size="10" maxlength="10" value="<%=obj.getCaseNo_Merge()%>">]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<!-- 	案名：　　-->
			<input class="field" type="hidden" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
		</td>
	</tr>	
	<tr>
  	    <td class="td_form"><font color="red">*</font>入帳：</td>
    	<td colspan="3" class="td_form_white">
    		<select class="field" type="select" name="verify" onchange="getEnterDate();">
    			<%=util.View.getYNOption(obj.getVerify())%>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			入帳日期：
			<input class="field" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>" onChange="changeEnterDate();">
			<input class="field" type="button" name="btn_enterDate" onclick="popCalndar('enterDate');changeEnterDate();" value="..." title="萬年曆輔助視窗">&nbsp;&nbsp;&nbsp;&nbsp;
      	</td>
    </tr>
	<tr>
		<td class="td_form">核准機關：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" name="approveOrg" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='APO' ", obj.getApproveOrg())%>
			</select>&nbsp;&nbsp;　
			核准日期：
			<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>&nbsp;&nbsp;&nbsp;
			核准文號：
			<input class="field" type="text" name="approveDoc" size="30" maxlength="20" value="<%=obj.getApproveDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" id="cause"name="cause" type="select" onChange="if(this.value=='499'){form1.cause1.disabled=false;}else{form1.cause1.disabled=true;};">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' and codeid in ('401','402','403','407','413')", obj.getCause())%>
			</select>	
			&nbsp;　
			其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>" disabled="true">
		</td>
	</tr>	
    <tr>
		<td class="td_form">電腦單號：</td>
		<td class="td_form_white" colspan="3">
			減損單：
			[<input class="field_RO" type="text" name="caseNo_Reduce" size="10" maxlength="10" value="<%=obj.getCaseNo_Reduce()%>">]&nbsp;
			增加單：
			[<input class="field_RO" type="text" name="caseNo_Add" size="10" maxlength="10" value="<%=obj.getCaseNo_Add()%>">]
		</td>
	</tr>
	<tr>
		<td  rowspan="3" class="td_form" >備註：</td>
		<td width="30%" class="td_form_white" >	
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td width="20%" class="td_form"style="display:none;">異動人員/日期：</td>
		<td class="td_form_white"style="display:none;">
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
	
	<span id="span_verifyRe">
		<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復入帳" disabled="true" onClick="clickApproveRe();">&nbsp;|	
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">案號</a></th>
	    <th style="display:none;"class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">案名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,true,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,false,true,true,false,true,true,true,false,false};
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
		if(!confirm("刪除此合併分割案件，將一併刪除其關聯的\n土地減損單、\n土地減損單明細檔、\n土地增加單、\n土地主檔、\n土地管理檔、\n土地地上物檔、\n土地公告地價檔。\n\n您確定要刪除?"))			
			return false;
			hasBeenConfirmed = true;
			break;
	
	}
	return true;
};
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":			
			$("select[name='verify']").attr('disabled',true);
			break;
	}
	return true;	
};
</script>
</body>
</html>
