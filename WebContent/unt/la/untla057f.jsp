<!--
程式目的：土地合併分割重測重劃作業－增加單
程式代號：untla057f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" import="util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA057F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA057F)obj.queryOne();	
}else if ("insert".equals(obj.getState())) {
	obj.insert();
	
}else if ("update".equals(obj.getState())) {
	obj.update();
	
}else if ("delete".equals(obj.getState())) {
	obj.delete();	
	obj.clearAllDataForView();
	obj.setCaseNo("");
	obj.setCaseNo_Add("");
	
}else if ("insertError".equals(obj.getState()) || "updateError".equals(obj.getState()) || "deleteError".equals(obj.getState())){
	obj.clearAllDataForView();
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if("init".equals(obj.getState())) {
	
}else{
	obj.setQueryAllFlag("true");
}

if ("true".equals(obj.getQueryAllFlag())){
	obj.clearAllDataForView();	
	obj.setCaseNo(obj.getCaseNo_Add());
	objList = obj.queryAll();
	obj = (unt.la.UNTLA057F)obj.queryOne();	
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("writeUnit","<%=user.getUnitName()%>"),
	new Array("proofYear","<%=util.Datetime.getYYY()%>"),
	new Array("proofDoc","<%=new unt.ch.UNTCH_Tools()._getProofDoc(user.getOrganID(),"D1")%>"),
	new Array("verify","N")	
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"增加單編號－字");
		alertStr += checkDate(form1.enterDate,"入帳日期");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkLen(form1.notes,"備註",250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,caseNo,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo_Add.value=caseNo;
	form1.differenceKind.value=differenceKind;
	
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function init(){
	getQueryCaseData();
	
	setDisplayItem("spanQueryAll,spanNextInsert", "H");
	
	//每個合併分割案只能有1筆「減損單」資料
	if(form1.caseNo_Add.value==''){				setFormItem("insert","O");	
												$("input[name='printAddProof']").attr('disabled', true);	
												$("input[name='printAddPropertyCard']").attr('disabled', true);
	}else{										setFormItem("insert","R");	
												$("input[name='printAddProof']").attr('disabled', false);	
												$("input[name='printAddPropertyCard']").attr('disabled', false);
	}
	
	//查出的資料若其「案件資料」已入帳
	if(form1.alreadyVerify.value=="Y"){			setFormItem("insert,update,delete","R");
	}else{										setFormItem("update,delete","O");}

	//非登入者所屬機關所登錄的資料
	if(form1.enterorgNotConfirm.value=="Y"){	setFormItem("insert,update,delete","R");}

	//若沒有選擇任何資料
	if(form1.caseNo.value==''){					setFormItem("update,delete","R");}
}


function checkURL(surl){

	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		
		if(surl=="untla058f.jsp"){
			if (form1.proofNo.value==""){
				alert("請先新增增加單後再點選增加單明細!");
			}else{
				columnTrim(form1.caseNo);
				
				form1.action=surl+"?initDtl=Y";

				form1.state.value="queryAll";
				
				beforeSubmit();
				form1.submit();				
			}		
		}else{
			columnTrim(form1.caseNo);
			
			form1.action=surl+"?initDtl=Y";

			form1.state.value="queryAll";
			
			beforeSubmit();
			form1.submit();
		}
	}
}


function getQueryCaseData(){
	var comment={};	
	comment.enterOrg = form1.enterOrg.value;	
	comment.ownership = form1.ownership.value;
	comment.caseNo = form1.caseNo_Merge.value;

	
	//傳送JSON
	$.post('untla057f_ajax.jsp',
		comment,
		function(data){
			//將回傳資料寫入		
			data=eval('('+data+')');

			$("input[name='caseName']").val(data['casename']);
			$("input[name='enterDate']").val(getToday(data['enterdate']));
			$("input[name='notes']").val(data['notes']);
			
		});
}

function goAddProof(){
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

function goAddProofCard(){
	var url = "untla011p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo_Add.value + "&q_caseNoE="+form1.caseNo_Add.value;
	window.open(url);
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--標籤區============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla054f.jsp');"><font color="red">*</font>案件資料</a></td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla055f.jsp');"><font color="red">*</font>減損單</a></td>
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla056f.jsp');"><font color="red">*</font>減損單明細</a></td>
		<td ID=t4 CLASS="tab_border1" HEIGHT="25">*增加單</td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla058f.jsp');"><font color="red">*</font>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2">增加單管理資料</td>
		<td ID=t7 CLASS="tab_border2">增加單地上物資料</td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla066f.jsp');">調整前後對應資料</a></td>
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>	
		<td class="tab_line2"></td>			
		<td class="tab_line2"></td>		
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">

<input class="field_QRO" type="hidden" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">

<input class="field_QRO" type="hidden" name="caseNo_Reduce" value="<%=obj.getCaseNo_Reduce()%>">
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
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]&nbsp;&nbsp;&nbsp;
			權屬：　&nbsp;&nbsp;&nbsp;&nbsp;
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			財產區分別：
			<%=util.View._getSelectHTML("field_QRO", "differenceKind", obj.getDifferenceKind(),"DFK") %>
		</td>
	</tr>
	<tr>
		<td class="td_form">案號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="caseNo_Merge" size="10" maxlength="10" value="<%=obj.getCaseNo_Merge()%>">]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　　　　　
		<!--  	案名：　　-->
			<input class="field_QRO" type="hidden" name="caseName" size="25" maxlength="25" value="<%=obj.getCaseName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">填單日期：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="caseNo_Add" size="10" maxlength="10" value="<%=obj.getCaseNo_Add()%>">
			<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>　
			填造單位：
			<select class="field" type="select" name="writeUnit" >
				<%=util.View.getOption("select  unitno, unitname from UNTMP_KEEPUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno", obj.getWriteUnit())%>
			</select>
			<input class="field" type="button" name="btn_writeUnit" onclick="popUnitNo(form1.enterOrg.value,'writeUnit')" value="..." title="單位輔助視窗">
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>增加單編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="proofYear" size="3" value="<%=obj.getProofYear()%>">
			年
			<input class="field" type="text" name="proofDoc" size="10" maxlength="10" value="<%=obj.getProofDoc()%>">
			字	第 
			<input class="field_RO" type="text" name="proofNo" size="7" maxlength="7" value="<%=obj.getProofNo()%>">
			號			
		</td>
	</tr>
	<tr>
		<td class="td_form">財產管理單位編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="manageNo" size="30" maxlength="10" value="<%=obj.getManageNo()%>">&nbsp;　　　
			傳票號數：
			<input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>">
		</td>
	</tr>	
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]&nbsp;&nbsp;
			入帳：　　
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
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
		<td class="td_form">總金額：</td>
		<td colspan="3" class="td_form_white">
       		<input class="field" type="text" name="sumBookValue" size="15" maxlength="15" value="<%=obj.getSumBookValue()%>">
       		<input type="hidden" name="oldSumBookValue" value="<%=obj.getOldSumBookValue()%>">
       		<input type="hidden" name="sumHoldArea" value="<%=obj.getSumHoldArea()%>">
       	</td>
    </tr>    
	<tr>
		<td rowspan="3" class="td_form" >備註：</td>
		<td width="30%" class="td_form_white" >	
			<textarea class="field_QRO" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
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
	<input class="toolbar_default" type="button" followPK="false" name="printAddProof" value="列印增加單" disabled="true" onClick="goAddProof();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="printAddPropertyCard" value="列印財產卡" disabled="true" onClick="goAddProofCard();">&nbsp;|		
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
	    <th class="listTH"style="display:none;"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">電腦單號</a></th>	
		<th class="listTH"style="display:none;"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">案名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,false,false,false,false,true};
	boolean displayArray[] = {false,true,false,true,true,false,false,true,true,false};
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
		if(!confirm("刪除此增加單，將一併刪除其關聯的土地主檔、土地管理檔、土地地上物檔。\n您確定要刪除?"))
			return false;
			hasBeenConfirmed = true;			
			break;
	}
	return true;
}
</script>
</body>
</html>
