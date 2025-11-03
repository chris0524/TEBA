<!--
程式目的：土地減損明細修改資料作業
程式代號：untdu018f
程式日期：0970711
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU018F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.du.UNTDU018F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.enterOrgName,"入帳機關名稱");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
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

function init(){
	setDisplayItem('spanInsert,spanDelete,spanNextInsert,spanQueryAll','H');
}

function checkURL(surl){
	form1.state.value = "queryAll";
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">土地減少作業-減損單明細資料</td>
	</tr>
	<tr>
		<td class="tab_line1"></td>
	</tr>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr><td>
		<input type="hidden" name="q_chengClass" size="10" maxlength="10" value="<%=obj.getQ_chengClass()%>">
		<input type="hidden" name="q_enterOrg" value="<%=obj.getQ_enterOrg()%>">
		<input type="hidden" name="q_enterOrgName" value="<%=obj.getQ_enterOrgName()%>">
		<input type="hidden" name="q_ownership" value="<%=obj.getQ_ownership()%>">
		<input type="hidden" name="q_caseNo" value="<%=obj.getQ_caseNo()%>">
		<input type="hidden" name="q_lotNo" value="<%=obj.getQ_lotNo()%>">
		<input type="hidden" name="q_propertyNo" value="<%=obj.getQ_propertyNo()%>">
		<input type="hidden" name="q_serialNo" value="<%=obj.getQ_serialNo()%>">
	</td></tr>
	</table>
</div>

<!--頁籤區=============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="10%"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" width="32%" colspan="3">
			<input class="field_Q" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			權屬：
			<select class="field_P" type="select" name="ownershipshow">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			<input class="field" type="hidden" name="ownership" size="1" maxlength="1" value="<%=obj.getOwnership()%>">
			電腦單號：[<input class="field_RO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
			<br>
			財產編號：
			[<input class="field_RO" type="text" name="propertyNo" size="11" maxlength="11" value="<%=obj.getPropertyNo()%>">]
			財產分號：[<input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">別名：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			原財產編號：<input class="field" type="text" name="oldPropertyNo" size="7" maxlength="11" value="<%=obj.getOldPropertyNo()%>">&nbsp;
			原分號：<input class="field" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white" colspan="3">
			<select  type="select" class="field" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>
			<select  type="select" class="field" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>
			<select  type="select" class="field" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>
			地號：
			<input class="field" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>">&nbsp;
			-
			<input class="field" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan = "3">
		日期：
		<input class="field" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">&nbsp;&nbsp;  
		入帳：
		<select class="field" type="select" name="verify">
			<%=util.View.getYNOption(obj.getVerify())%>
		</select>&nbsp;&nbsp;&nbsp;
		月結：
		<select class="field" type="select" name="closing">
			<%=util.View.getYNOption(obj.getClosing())%>
		</select>
	<br>	
		<font color="red">*</font>減損原因：
		<select class="field" type="select" name="cause" onchange="changecause()">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' and codecon1 in ('2','3','4') ", obj.getCause())%>
		</select>&nbsp;
		其他說明：<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
	<br>
		接管機關：
		<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>&nbsp;　
		交接日期：
		<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">	
			<select class="field" type="select" name="propertyKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			基金財產：
			<select class="field" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">珍貴財產：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			抵繳遺產稅：
			<select class="field" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>
			新草衙：
			<select class="field" type="select" name="grass">
				<%=util.View.getYNOption(obj.getGrass())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">使用情形：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="useState">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UST' ", obj.getUseState())%>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			地目：
			<select class="field" type="select" name="field">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
			</select>
		<br>
			取得日期：
			<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
			權狀字號：
			<input class="field" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：
			<input class="field" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">
		<br>
			權利分子：
			<input class="field" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>">&nbsp;
			權利分母：
			<input class="field" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>">&nbsp;
			權利範圍面積(㎡)：
			<input class="field" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">		
			帳面單價：
			<input class="field" type="text" name="bookUnit" size="13" maxlength="16" value="<%=obj.getBookUnit()%>">&nbsp;&nbsp;
			帳面總價：
			<input class="field" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">
		<br>
			帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">&nbsp;&nbsp;
			會計科目：<input class="field" type="text" name="accountingTitle" size="10" maxlength="10" value="<%=obj.getAccountingTitle()%>">
		<br>
			當期公告日期：
			<input class="field" type="text" name="bulletinDate" size="7" maxlength="7" value="<%=obj.getBulletinDate()%>">&nbsp;
			當期公告地價：
			<input class="field" type="text" name="valueUnit" size="13" maxlength="13" value="<%=obj.getValueUnit()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">使用分區：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="useSeparate">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
			</select>
			編定使用種類：
			<select class="field" type="select" name="useKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getUseKind())%>
			</select>
		</td>
	</tr>

	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
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
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="backup">
	<input class="toolbar_default" type="button" name="backup" value="回上一層" onClick="checkURL('untdu012f.jsp');">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">電腦單號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,true,true,true};//傳值
	boolean displayArray[] = {false,true,false,true,true,true,true};//show list
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



