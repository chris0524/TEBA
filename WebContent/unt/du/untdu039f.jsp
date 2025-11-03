<!--
程式目的：動產減少作業 -減少單明細資料
程式代號：
程式日期：0970711
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU039F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.du.UNTDU039F)obj.queryOne();	
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
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
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

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr><td ID=t1 CLASS="tab_border1" HEIGHT="25">動產減少作業 -減少單明細資料</td></tr>
</TABLE>
<!--頁籤區=============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form"><font color="red">*</font>入帳機關：</td>
		<td class="td_form_white" colspan="3">
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
		　	原財產編號：
			<input class="field" type="text" name="oldPropertyNo" size="7" maxlength="11" value="<%=obj.getOldPropertyNo()%>">
			原分號：
			<input class="field" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">其他主要材質：</td>
		<td class="td_form_white" colspan="3"> 
			<input class="field" type="text" name="otherMaterial" size="25" maxlength="50" value="<%=obj.getOtherMaterial()%>">
		<br>
			其他單位：
			<input class="field" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>"> 
		<br>
			調整後年限(月)：
			<input class="field" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">已使用年月：</td>
		<td class="td_form_white" colspan = "3">
			<input class="field" type="text" name="useYear" size="5" maxlength="3" value="<%=obj.getUseYear()%>">年
			<input class="field" type="text" name="useMonth" size="3" maxlength="2" value="<%=obj.getUseMonth()%>">個月
			可報廢日期：
			<%=util.View.getPopCalndar("field","permitReduceDate",obj.getPermitReduceDate())%>
		</td>	
	</tr>
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan = "3">
			日期：
			<%=util.View.getPopCalndar("field","reduceDate",obj.getReduceDate())%>
			入帳：
			<select class="field" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			月結：
			<select class="field" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		<br>	
			<font color="red">*</font>減損原因：
			<select class="field" type="select" name="cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getCause())%>
			</select> 
			其他說明：
			<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		<br>
			接管機關：
			<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>
			交接日期：
			<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		<br>
			繳存地點：
			<input class="field" type="text" name="returnPlace" size="20" maxlength="25" value="<%=obj.getReturnPlace()%>">
			需否呈報市府核定：
			<select class="field" type="select" name="submitCityGov">
				<%=util.View.getYNOption(obj.getSubmitCityGov())%>
			</select>
		<br>
			報廢或失竊原因：
			<input class="field" type="text" name="cause2" size="14" maxlength="10" value="<%=obj.getCause2()%>">
			預估殘值總價：
			<input class="field" type="text" name="scrapValue2" size="15" maxlength="15" value="<%=obj.getScrapValue2()%>">
		<br>
			擬予處理意見：
			<input class="field" type="text" name="dealSuggestion" size="40" maxlength="20" value="<%=obj.getDealSuggestion()%>">
		</td>	
	</tr>
	<tr>
		<td class="td_form">廠牌型式：</td>
		<td class="td_form_white" colspan="3">		
			品名：
			<input class="field" type="text" name="articleName" size="20" value="<%=obj.getArticleName()%>">　
			廠牌：
			<input class="field" type="text" name="nameplate" size="20" value="<%=obj.getNameplate()%>" >
		<br>
			型式：
			<input class="field" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">　
			牌照號碼規格：
			<input class="field" type="text" name="licensePlate" size="40" maxlength="40" value="<%=obj.getLicensePlate()%>">
		</td>
	</tr>	
	<tr>
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field","enterDate",obj.getEnterDate())%>
			購置日期：
			<input class="field" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">
			取得日期：
			<input class="field" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">
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
			會計科目：
			<input class="field" type="text" name="accountingTitle" size="20" maxlength="20" value="<%=obj.getAccountingTitle()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：
			<input class="field" type="text" name="oldBookAmount" size="7" maxlength="7" value="<%=obj.getOldBookAmount()%>"> 
			單價：
			<input class="field type="text" name="oldBookUnit" size="13" maxlength="13" value="<%=obj.getOldBookUnit()%>">
			總價：
			<input class="field" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">減少帳面資料：</td>
		<td class="td_form_white" colspan="3">
			<font color="red">*</font>數量：
			<input class="field" type="text" name="adjustBookAmount" size="7" maxlength="7" value="<%=obj.getAdjustBookAmount()%>">
			<font color="red">*</font>總價：
			<input class="field" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>">
			帳務摘要：
			<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">新帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：
			<input class="field" type="text" name="newBookAmount" size="7" maxlength="7" value="<%=obj.getNewBookAmount()%>" >
			總價：
			<input class="field" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">
		</td>
	</tr>
<tr>
		<td class="td_form">移動資料：</td>
		<td class="td_form_white" colspan="3">
			日期：
			<%=util.View.getPopCalndar("field","moveDate",obj.getMoveDate())%>
			存置地點：
			<input class="field" type="text" name="place" size="25" maxlength="25" value="<%=obj.getPlace()%>"> 
		</td>
	</tr>	
	<tr>
		<td class="td_form">折舊資料：</td>
		<td class="td_form_white" colspan="3">
			折舊方法：
			<select class="field" name="deprMethod" type="select">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
			</select>
		<br>
			預留殘值：
			<input class="field" type="text" name="scrapValue" size="15" maxlength = "15" value="<%=obj.getScrapValue()%>">
			應攤提折舊總額：
			<input class="field" type="text" name="deprAmount" size="15" maxlength="15" value="<%=obj.getDeprAmount()%>">
		<br>
			攤提年限：
			<input class="field" type="text" name="apportionYear" size="3" maxlength="3" value="<%=obj.getApportionYear()%>">
			月提折舊金額：
			<input class="field" type="text" name="monthDepr" size="15" maxlength="15" value="<%=obj.getMonthDepr()%>">
		<br>
			使用年限截止年月：
			<input class="field" type="text" name="useEndYM" size="6" maxlength="5" value="<%=obj.getUseEndYM()%>">
			攤提年限截止年月：
			<input class="field" type="text" name="apportionEndYM" size="5" maxlength="5" value="<%=obj.getApportionEndYM()%>">
		<br>
			累計折舊調整年月：
			<input class="field" type="text" name="accumDeprYM" size="6" maxlength="5" value="<%=obj.getAccumDeprYM()%>">
			累計折舊調整金額：
			<input class="field" type="text" name="accumDepr" size="15" maxlength="15" value="<%=obj.getAccumDepr()%>">
		<br>
			累計折舊：
			<input class="field" type="text" name="accumDepr1" size="15" maxlength="15" value="<%=obj.getAccumdepr1()%>">
			殘餘價值：
			<input class="field" type="text" name="scrapValue1" size="15" maxlength="15" value="<%=obj.getScrapValue1()%>">
		</td>
	</tr>
	<tr>		
		<td class="td_form">廢品處理資料：</td>
		<td class="td_form_white" colspan="3">
			案號：
			<input class="field" type="text" name="dealCaseNo" size="10" value="<%=obj.getDealCaseNo()%>">
			日期：
			<input class="field" type="text" name="dealDate" size="7" value="<%=obj.getDealDate()%>">
		<br>
			方式：
			<select class="field" type="select" name="reducedeal">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", obj.getReduceDeal())%>
			</select>
			變賣金額：
			<input class="field" type="text" name="realizeValue" size="15" value="<%=obj.getRealizeValue()%>">
		<br>
			轉撥單位：
			<input class="field" type="hidden" name="shiftOrg" size="10" maxlength="10" value="<%=obj.getShiftOrg()%>" >
			<input class="field" type="text" name="shiftOrgName" size="20" maxlength="50" value="<%=obj.getShiftOrgName()%>">
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



