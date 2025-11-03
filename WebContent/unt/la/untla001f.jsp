<!--
程式目的：土地主檔資料維護－增加單資料
程式代號：untla001f
程式日期：0940818
程式作者：novia
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
clive.chang 0941219	Debug & Modify for Testing and autual running..
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

String message = "";
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA001F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	if("Y".equals(obj.getVerify())){
		message = obj.execVerifyCheck();
		if("".equals(message)){	
			obj.insert();		
		}
	}else{
		obj.insert();
	}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	if("Y".equals(obj.getVerify())){
		message = obj.execVerifyCheck();
		if("".equals(message)){	
			obj.update();		
		}
	}else{
		obj.update();
	}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("approveAll".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.approveAll();		
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var message = '<%=message%>';

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("writeUnit","<%=user.getUnitName()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"A1")%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("ownership",""),
	new Array("verify","N"),
	new Array("closing","N")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untla001f.jsp";
		} else {
			form1.action = "untla002f.jsp";
		}		
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"增加單編號－字");
		alertStr += checkEmpty(form1.verify,"入帳");
		alertStr += checkDate(form1.enterDate,"入帳日期");				
		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.enterDate,"若要入帳，入帳日期");
		}
		alertStr += checkEmpty(form1.closing,"月結");
		alertStr += checkLen(form1.notes, "備註", 250);
		form1.action = "untla001f.jsp";		
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

function getEnterDate() {
	if (form1.verify.value=="Y") {
		if (form1.enterDate.value=="") form1.enterDate.value=getToday();
	}
}

function goAddProof(){
	var url = "";
	if (form1.ownership.value=="2") url = "untla010p.jsp";
	else url = "untla009p.jsp";	
	url += "?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo.value + "&q_caseNoE="+form1.caseNo.value +
		"&q_kind=4";
	window.open(url);
}

function goAddProofCard(){
	var url = "untla011p.jsp?organID="+form1.organID.value+"&q_enterOrg=" + form1.enterOrg.value + "&q_ownership=" + form1.ownership.value +
		"&q_caseNoS=" + form1.caseNo.value + "&q_caseNoE="+form1.caseNo.value;
	window.open(url);
}

function clickApproveAll(){
	if(confirm("您確定要將列表中未入帳的增加單全部入帳?")){
		document.all('state').value='approveAll';
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
		if (form1.querySelect[0].checked || form1.state.value=="insertSuccess") {
			surl=surl+"?initDtl=Y";
			form1.state.value="queryAll";			
		} else {
			if (form1.propertyNo.value!="" && form1.serialNo.value!="") form1.state.value = "queryOne";
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	var arrField = new Array("update","delete"); 
	//欲修改、刪除增加單資料須至「土地合併分割作業」進行
	if(form1.mergeDivision.value!=""){
		setFormField(new Array("update","delete"),"R");
	}
	//==========================================
	if (form1.enterOrg.value!="<%=user.getOrganID()%>" || form1.closing.value=="Y") {		
		setFormField(new Array("update","delete"),"R");
	}
	if (form1.verify.value=="Y") {
		setFormItem("delete", "R");
	}	
	if (message!=''){alert(message);message='';}
		
}

function loadUntla009r(){
	var url = "untla009p.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value
			+ "&q_caseNoS=" + form1.caseNo.value
			+ "&q_caseNoE="+form1.caseNo.value
			+ "&q_kind=4";
	window.open(url);
}

function loadUntla011r(){
	var url = "untla011p.jsp?"
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
<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">增加單資料</td>
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla002f.jsp');">基本資料</a></td>		
		<td ID=t3 CLASS="tab_border2">管理資料</td>
		<td ID=t4 CLASS="tab_border2">地上物</td>		
		<td ID=t5 CLASS="tab_border2">公告地價</td>
		<td ID=t6 CLASS="tab_border2">公告現值</td>
		<td ID=t7 CLASS="tab_border2">他項權利</td>
		<td ID=t8 CLASS="tab_border2">土地使用權</td>
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
<!--Query區============================================================-->
<div id="queryContainer" style="width:746px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA001Q",obj);%>
	<jsp:include page="untla001q.jsp" />
<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">	
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	  <table class="table_form" width="100%" height="100%">
        <tr>
          <td class="td_form">入帳機關：</td>
          <td colspan="3" class="td_form_white"><input class="field_RO" type="hidden" name="oldVerify" value="<%=obj.getOldVerify()%>" >
              <input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
      		[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">] 
      		&nbsp;&nbsp;&nbsp;<font color="red">*</font>權屬：
		    <select class="field_P" type="select" name="ownership">
		      <%=util.View.getOnwerOption(obj.getOwnership())%>
		    </select>
			&nbsp;&nbsp;填單日期：<%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%></td>
        </tr>
        <tr>
          <td class="td_form">電腦單號：</td>
          <td colspan="3" class="td_form_white"> [
              <input class="field_PRO" type="text" name="caseNo" size="15" maxlength="10" value="<%=obj.getCaseNo()%>">
              ] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;案名：
              <input class="field" type="text" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>">
          </td>
        </tr>
        <tr>
          <td class="td_form">填造單位：</td>
          <td colspan="3" class="td_form_white"><input class="field" type="text" name="writeUnit" size="20" maxlength="15" value="<%=obj.getWriteUnit()%>">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;財產管理單位編號：
      <input class="field" type="text" name="manageNo" size="15" maxlength="10" value="<%=obj.getManageNo()%>"></td>
        </tr>
        <tr>
          <td class="td_form"><font color="red">*</font>增加單編號：</td>
          <td colspan="3" class="td_form_white" ><input class="field" type="text" name="proofDoc" size="15" maxlength="10" value="<%=obj.getProofDoc()%>">
      字 第 [
        <input class="field_RO" type="text" name="proofNo" size="10" maxlength="10" value="<%=obj.getProofNo()%>">
        ] 號 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;傳票號數：
        <input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>"></td>
        </tr>
        <tr>
          <td class="td_form"><font color="red">*</font>入帳：</td>
          <td colspan="3" class="td_form_white"><select class="field" type="select" name="verify" onChange="getEnterDate();">
                <%=util.View.getYNOption(obj.getVerify())%>
              </select> &nbsp;&nbsp;入帳日期：<%=util.View.getPopCalndar("field","enterDate",obj.getEnterDate())%>              
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月結：
      <select class="field_RO" type="select" name="closing">
        <%=util.View.getYNOption(obj.getClosing())%>
      </select>
          </td>
        </tr>
        <tr>
          <td class="td_form">合併分割案號：</td>
          <td colspan="3" class="td_form_white">[
            <input class="field_RO" type="text" name="mergeDivision" size="15" maxlength="8" value="<%=obj.getMergeDivision()%>">
            ] </td>
          </tr>
        <tr>
          <td class="td_form">備註：</td>
          <td class="td_form_white"><textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
          </td>
          <td class="td_form">異動人員/日期：</td>
          <td class="td_form_white">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
      /
      <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
        </tr>
      </table>
	  </div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanUntla009r">
		<input class="toolbar_default" type="button" followPK="true" name="untla009r" value="列印增加單" onClick="loadUntla009r();">&nbsp;|
	</span>
	<span id="spanUntla011r">
		<input class="toolbar_default" type="button" followPK="true" name="untla009r" value="列印財產卡" onClick="loadUntla011r();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">電腦單號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">案名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">月結</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,true,true,true,true,false};
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
			//if (!confirm("刪除此增加單，將一併刪除其關聯的資料：建物基本資料、管理資料、使用人資料檔、樓層資料、附屬建物、基地資料、共同資料、附屬設備、折舊記錄、現場勘查、稅籍資料。您確定要刪除?"))
			if (!confirm("刪除此增加單，將一併刪除其關聯的資料：\n\n　01. 土地基本資料。\n　02. 管理資料。\n　03.地上物。\n　04. 公告地價。\n　05. 公告現值。\n　06. 他項權利。\n　07. 土地使用權。 \n\n您確定要刪除?"))
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
			changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType',form1.q_fundType.value,'<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');			
			break;			
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
		case "insertError":
			setFormItem("verify,closing","R");
			break;
		case "update":
			if (form1.verify.value=="Y") {
				setAllReadonly();
				form1.verify.style.backgroundColor=editbg;
				//form1.verify.disabled = false;
				setFormItem("verify,enterDate,btn_enterDate","O");
			}					
			break;	
	}
}
</script>

</body>
</html>



