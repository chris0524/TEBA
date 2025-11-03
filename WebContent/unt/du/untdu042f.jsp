<!--
程式目的：土地改良物主檔-基本資料
程式代號：
程式日期：0970711
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU042F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.du.UNTDU042F)obj.queryOne();	
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
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">土地改良物主檔-基本資料</td>
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
		<td class="td_form" ><font color="red">*</font>入帳機關：</td>
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
		<td class="td_form">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">　
			資料狀態：
			<select class="field" type="select" name="dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getDataState())%>
			</select>
			入帳：
			<select class="field" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			月結：
			<select class="field" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>別名：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
			原財產編號：
			<input class="field" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">
			原分號：
			<input class="field" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">主要材質：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="material" size="40" value="<%=obj.getMaterial()%>">]
		<br>
			使用年限：
			[<input class="field_RO" type="text" name="limitYear" size="7" maxlength="7" value="<%=obj.getLimitYear()%>">]
			調整後年限(月)：
			<input class="field" type="text" name="otherLimitYear" size="7" maxlength="7" value="<%=obj.getOtherLimitYear()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>建築日期：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="buildDate" size="7" maxlength="7" value="<%=obj.getBuildDate()%>">
			<input class="field" type="button" name="btn_buildDate" onclick="popCalndar('buildDate','changeArea()')" value="..." title="萬年曆輔助視窗">
			可報廢日期：
			<input name="permitReduceDate" type="text" class="field" value="<%=obj.getPermitReduceDate()%>" size="7">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產性質：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="propertyKind">
				<%=util.View.getTextOption("01;公務用;02;公共用;03;事業用;04;非公用", obj.getPropertyKind())%>
			</select>
			基金財產：
			<select class="field_R" type="select" name="fundType">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD' ", obj.getFundType())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>珍貴財產：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			<font color="red">*</font>抵繳遺產稅：
			<select class="field" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>增加原因：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="cause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAB'", obj.getCause())%>
			</select>
			其它說明：
			<input class="field" type="text" name="cause1" size="12" maxlength="12" value="<%=obj.getCause1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">經費來源：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="fundsSource">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getFundsSource())%>
			</select>
			其它說明：
			<input class="field" type="text" name="fundsSource1" size="12" maxlength="12" value="<%=obj.getFundsSource1()%>">
			會計科目：
			<input class="field" type="text" name="accountingTitle" size="10" maxlength="20" value="<%=obj.getAccountingTitle()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">財產來源：</td>
		<td colspan="3" class="td_form_white">
			<select class="field" type="select" name="sourceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKB' ", obj.getSourceKind())%>
			</select>
			財產取得日期：
			<%=util.View.getPopCalndar("field","sourceDate",obj.getSourceDate())%>
			文號：
			<input class="field" type="text" name="sourceDoc" size="18" maxlength="20" value="<%=obj.getSourceDoc()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">使用單位：</td>
			<td colspan="3" class="td_form_white">
			<%=util.View.getPopOrgan("field","useUnit",obj.getUseUnit(),obj.getUseUnitName(), "Y")%>
			非機關：
			<input class="field" type="text" name="useUnit1" size="20" maxlength="30" value="<%=obj.getUseUnit1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>計量數：</td>
		<td colspan="3" class="td_form_white">
			原始：
			<input class="field" type="text" name="originalMeasure" size="15" maxlength="15" value="<%=obj.getOriginalMeasure()%>">
			目前：
			<input class="field" type="text" name="measure"  size="15" maxlength="15" value="<%=obj.getMeasure()%>">
			單位：
			[<input class="field_RO" type="text" name="propertyUnit" size="7" value="<%=obj.getPropertyUnit()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>價值：</td>
		<td colspan="3" class="td_form_white">
			原始入帳價值：
			<input class="field" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">
			原始入帳摘要：
			<input class="field" type="text" name="originalNote" size="25" maxlength="15" value="<%=obj.getOriginalNote()%>">
		<br>
			帳面價值：
			<input class="field" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">	  
		</td>
	</tr>
	<tr>
		<td class="td_form">毀損資料：</td>
		<td colspan="3" class="td_form_white">
			毀損日期：
			<input class="field" type="text" name="damageDate" size="7" maxlength="7" value="<%=obj.getDamageDate()%>">
			<input class="field" type="button" name="btn_damageDate" onclick="popCalndar('damageDate','changeArea()')" value="..." title="萬年曆輔助視窗"> 
			毀損報局屆滿日：
			<input class="field" type="text" name="damageExpire" size="8" maxlength="20" value="<%=obj.getDamageExpire()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">減損資料：</td>
		<td colspan="3" class="td_form_white">
			減損日期：
			<input class="field" type="text" name="reduceDate" size="15" maxlength="7" value="<%=obj.getReduceDate()%>">
		<br>
			減損原因：
			<select class="field" type="select" name="reduceCause">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAC' ", obj.getReduceCause())%>
			</select>
			其他說明：
			<input class="field" type="text" name="reduceCause1" size="20" maxlength="20" value="<%=obj.getReduceCause1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">折舊資料：</td>
		<td colspan="3" class="td_form_white">
			<font color="red">*</font>折舊方法：
				<select name="deprMethod" class="field" type="select">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DEP' ", obj.getDeprMethod())%>
				</select>
			折舊調整：
			<select class="field" type="select" name="isAccumDepr">
				<%=util.View.getYNOption(obj.getIsAccumDepr()) %>
			</select>
		<br>
			預留殘值：
			<input class="field" type="text" name="scrapValue" size="10" maxlength="15" value="<%=obj.getScrapValue()%>">&nbsp;&nbsp;&nbsp;
			應攤提折舊總額：
			<input class="field" type="text" name="deprAmount" size="20" maxlength="15" value="<%=obj.getDeprAmount()%>">
		<br>
			攤提年限：
			<input class="field" type="text" name="apportionYear" size="10" maxlength="3" value="<%=obj.getApportionYear()%>"> 　　　　　
			月提折舊金額：
			<input class="field" type="text" name="monthDepr" size="10" value="<%=obj.getMonthDepr()%>">
		<br>
			使用年限截止年月：
			<input class="field" type="text" name="useEndYM" size="10" maxlength=5"" value="<%=obj.getUseEndYM()%>">
			攤提年限截止年月：
			<input class="field" type="text" name="apportionEndYM"  size="10" maxlength="20" value="<%=obj.getApportionEndYM()%>">
		<br>
			累計折舊調整年月：
			<input class="field" type="text" name="accumDeprYM" size="7" maxlength="5" value="<%=obj.getAccumDeprYM()%>">&nbsp;
			累計折舊調整：
			<input class="field" type="text" name="accumDepr" size="10" maxlength="15" value="<%=obj.getAccumDepr()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">資產重估日：</td>
		<td colspan="3" class="td_form_white">
			<input class="field" type="text" name="appraiseDate" size="9" maxlength="7" value="<%=obj.getAppraiseDate()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white"><textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
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



