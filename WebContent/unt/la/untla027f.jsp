<!--
程式目的：土地合併分割作業－案件資料
程式代號：untla027f
程式日期：0940823
程式作者：carey
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA027F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String pp = "";
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA027F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("approveAll".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.approveAll();
}else if ("changeDivision".equals(obj.getState()) ) {
	pp =obj.changeDivision();
}else if ("changeDivisionRe".equals(obj.getState()) ) {
	pp =obj.changeDivisionRe();
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
	new Array("enterDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("mergeDivisionDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("ownership","1"),
	new Array("closing","N"),
	new Array("verify","N")
);
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.cause,"分割原因");
		alertStr += checkEmpty(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkDate(form1.approveDate,"核准日期");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkLen(form1.notes,"備註",250);
		if(form1.cause.value=="499") alertStr += checkEmpty(form1.cause1,"其他說明");
		else form1.cause1.value = "";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function changeSelect(){
	//當合併分割原因選「其他」時，開放其他說明欄位
	if(form1.cause.value == "499")
		form1.cause1.readOnly = false;
	else form1.cause1.readOnly = true;
}

function queryOne(enterOrg,ownership,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function gonpbgr018r(){
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else{
		if (form1.checkApprove.value=="Y"){
			var url = "/tbek/npb/gr/npbgr018p.jsp?q_caseNo="+form1.caseNo.value+"&q_kind=3";
			window.open(url);
		}else{
			alert("必須分割異動通過");
		}	
	}
}

function clickApproveAll(){
	if(confirm("您確定是否入帳列表上全部增加單?")){
		document.all('state').value='approveAll';
		beforeSubmit();
		form1.submit();
	}

}
function clickChangeDivision(){
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if(form1.verify.value=="Y"){
		alert("本案已入帳 , 請回復入帳後 , 再分割異動 !");
	}else if(form1.divisionReduce.value==""){
		alert("本案尚未分割 , 請至分割增加單明細做分割 !");
	}else{
		document.all('state').value='changeDivision';	
		beforeSubmit();
		form1.submit();		
	}
}

function clickChangeDivisionRe(){
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if(form1.verify.value=="Y"){
		alert("本案已入帳 , 請回復入帳後 , 再回復分割異動 !");
	}else{
		document.all('state').value='changeDivisionRe';	
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


function checkURL(surl){
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{	
		form1.mergeDivision.value = form1.caseNo.value;
		form1.reduceCaseNo.value = form1.mergeReduce.value;
		form1.addCaseNo.value = form1.mergeAdd.value;
		form1.reduceCaseNo1.value = form1.divisionReduce.value;
		form1.addCaseNo1.value = form1.divisionAdd.value;
		form1.action=surl+"?initDtl=Y";
		form1.action+="&approveOrg="+form1.approveOrg.value;
		form1.action+="&approveDate="+form1.approveDate.value;
		form1.action+="&approveDoc="+form1.approveDoc.value;
		if(surl=="untla027f.jsp" || surl=="untla028f.jsp" || surl=="untla030f.jsp" || 
			surl=="untla034f.jsp" || surl=="untla036f.jsp"){//案件資料、合併減損單、合併增加單、分割減損單、分割增加單
			form1.state.value="queryOne";
		}else{//合併減損單明細、合併增加單明細、合併增加單管理資料、分割減損單明細、分割增加單明細、分割增加單管理資料
			form1.state.value="queryAll";
		}
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	if (form1.mergeDivisionVerify.value=="Y") {
		setFormItem("update,delete", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("update,delete,allVerifyBut,verifyRe","R");
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

function changeEnterDate(){
	form1.mergeDivisionDate.value = form1.enterDate.value;
}

function changeVerify(){
	form1.mergeDivisionVerify.value = form1.verify.value;
}
function showMsg(error){
	var msg=error;
	if(msg !=null && msg.length!=0){
		alert(msg);
	}
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">案件資料</td>
<!-- 	<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla028f.jsp');">合併<br>減損單</a></td>		
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla029f.jsp');">合併<br>減損單明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla030f.jsp');">合併<br>增加單</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla031f.jsp');">合併<br>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2">合併增加單<br>管理資料</td>
 -->		
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla034f.jsp');">分割<br>減損單</a></td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla035f.jsp');">分割<br>減損單明細</a></td>
		<td ID=t9 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla036f.jsp');">分割<br>增加單</a></td>
		<td ID=t10 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla037f.jsp');">分割<br>增加單明細</a></td>
		<td ID=t11 CLASS="tab_border2">分割增加單<br>管理資料</td>
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
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>		
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input type="hidden" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
<input type="hidden" name="checkEnterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="reduceCaseNo" value="<%=obj.getReduceCaseNo()%>">
<input type="hidden" name="addCaseNo" value="<%=obj.getAddCaseNo()%>">
<input type="hidden" name="reduceCaseNo1" value="<%=obj.getReduceCaseNo1()%>">
<input type="hidden" name="addCaseNo1" value="<%=obj.getAddCaseNo1()%>">
<input type="hidden" name="mergeDivisionVerify" value="<%=obj.getVerify()%>">
<input type="hidden" name="showMsg" value="<%=obj.getShowMsg()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:100px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA027Q",obj);%>
	<jsp:include page="untla027q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
			<input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　　　<font color="red">*</font>權屬：
			<select class="field_P" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">分割案號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
			&nbsp;&nbsp;&nbsp;　　　　　　　　案名：<input class="field" type="text" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>入帳日期：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>" onChange="changeEnterDate();">
			<input class="field" type="button" name="btn_enterDate" onclick="popCalndar('enterDate');changeEnterDate();" value="..." title="萬年曆輔助視窗">
			<input class="field_QRO" type="hidden" name="mergeDivisionDate" size="7" maxlength="7" value="<%=obj.getMergeDivisionDate()%>">
		</td>
	</tr>
	
	<tr>
  	    <td class="td_form"><font color="red">*</font>入帳：</td>
    	<td colspan="3" class="td_form_white"><select class="field" type="select" name="verify">
        	<%=util.View.getYNOption(obj.getVerify())%>
			</select> &nbsp;&nbsp;
          	月結：
          	<select class="field_RO" type="select" name="closing">
            	<%=util.View.getYNOption(obj.getClosing())%>
          	</select> &nbsp;&nbsp;
          	分割異動： 
          	<select class="field_RO" type="select" name="checkApprove">
            	<%=util.View.getYNOption(obj.getCheckApprove())%>
          	</select>
      	</td>
    </tr>
	<tr>
		<td class="td_form">核准機關：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="approveOrg" onChange="changeApproveOrg();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='APO' ", obj.getApproveOrg())%>
			</select>
			&nbsp;　核准日期：<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
			&nbsp;　核准文號：<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>分割原因：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="cause" onChange="changeSelect();">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' and codeCon1='4'", obj.getCause())%>
			</select>
			&nbsp;　　其他說明：<input class="field" type="text" name="cause1" size="44" maxlength="20" value="<%=obj.getCause1()%>">
		</td>
	</tr>	
	<tr>
		<td class="td_form">電腦單號：</td>
		<td class="td_form_white" colspan="3">
						分割減損：[<input class="field_RO" type="text" name="divisionReduce" size="10" maxlength="10" value="<%=obj.getDivisionReduce()%>">]
			&nbsp;　　分割增加：[<input class="field_RO" type="text" name="divisionAdd" size="10" maxlength="10" value="<%=obj.getDivisionAdd()%>">]
		</td>
	</tr>
	<tr>
		<td width="20%" rowspan="3" class="td_form" >備註：</td>
		<td width="30%" class="td_form_white" >	
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td width="20%" class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="texta" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</tr>
	</table>
	</div>
</td></tr>
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">

<input type="hidden" name="mergeReduce" value="<%=obj.getMergeReduce()%>">
<input type="hidden" name="mergeAdd" value="<%=obj.getMergeAdd()%>">

	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
	<jsp:include page="../../home/toolbar.jsp" />
	<!-- <input class="toolbar_default" type="button" followPK="false" name="approveAll" value="全部入帳" onClick="clickApproveAll();">&nbsp;|-->
	<span id="spanUntla027f">
	<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復入帳" disabled="true" onClick="clickApproveRe();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="changeDivision" value="分割異動" onClick="clickChangeDivision();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="changeDivisionRe" value="回復分割異動" onClick="clickChangeDivisionRe();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="changeCloseYN" value="異動變更事項紀錄表" onClick="gonpbgr018r();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">案號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">案名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">合併分割日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,false,false,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,false,true,true,true,false,false,false,false,false};
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
		if(!confirm("刪除此合併分割案件，將一併刪除其關聯的\n土地減損單、\n土地減損單明細檔、\n土地增加單、\n土地主檔、\n土地管理檔。\n\n您確定要刪除?"))
			return false;
			hasBeenConfirmed = true;
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (parse(form1.q_checkQuery).length<=0)form1.q_checkQuery.value="Y";
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
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
				setFormItem("approveDate,btn_approveDate,approveDoc,verify", "R");
			break;
		case "insertError":
				setFormItem("approveDate,btn_approveDate,approveDoc,verify", "R");
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
			break;
		case "updateError":
			//若「核准機關」無資料
			var approveOrg = parse(form1.approveOrg.value);
			if(approveOrg.length<=0){
				setFormItem("approveDate,btn_approveDate,approveDoc", "R");
			}else{
				setFormItem("approveDate,btn_approveDate,approveDoc", "O");
			}
			break;
	}
}
</script>

</body>
</html>
