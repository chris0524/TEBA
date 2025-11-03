<!--
程式目的：物品廢品處理作業－處理單明細
程式代號：untne021f
程式日期：0941212
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE021F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE021F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
	if ("updateSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
	if ("deleteSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	if (!objList.isEmpty()) {
		if ("".equals(obj.getSerialNo())) {
			obj.setEnterOrg(((String[])objList.get(0))[11]);
			obj.setOwnership(((String[])objList.get(0))[12]);
			obj.setCaseNo(((String[])objList.get(0))[14]);
			obj.setPropertyNo(((String[])objList.get(0))[15]);
			obj.setSerialNo(((String[])objList.get(0))[16]);
			obj.setDifferenceKind(((String[])objList.get(0))[17]);
		}
		obj = (unt.ne.UNTNE021F)obj.queryOne();		
	}
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
<script language="javascript" src="getUntneReduceDetail.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
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
		alertStr += checkEmpty(form1.caseNo,"物品減損單-電腦單號");
		alertStr += checkEmpty(form1.propertyNo,"物品編號");
		alertStr += checkEmpty(form1.serialNo,"物品分號");
		alertStr += checkLen(form1.notes, "備註", 250);
		if(parse(form1.realizeValue1.value).length>0){
			alertStr += checkInt(form1.realizeValue1,"變賣金額");
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo,differenceKind){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.differenceKind.value=differenceKind;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untne020f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.caseNo,"檢損單號");
				alertStr += checkEmpty(form1.propertyNo,"物品編號");
				alertStr += checkEmpty(form1.serialNo,"物品分號");			
			}
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.differenceKind,"物品區分別");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo1,"物品處理單-電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}


function init(){
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete,clear,confirm,untne022f", "R");
	}
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("untne022f","R");
	}else{
		setFormItem("untne022f","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>" ) {		
		//setFormItem("insert,update,delete,clear,confirm,untmp023f", "R");
		setFormItem("insert,update,delete,clear,confirm,untne022f", "R");
	}
	setDisplayItem("insert","H");
}

function checkValue(){
	var checkCaseNo = form1.caseNo.value;
	var checkPropertyNo = form1.propertyNo.value;
	var checkSerialNo = form1.serialNo.value;
	if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.propertyNo.value="";
		form1.propertyNoName.value="";
		form1.serialNo.value="";
		form1.caseNo.value="";
	}
}

function propertyNoFounction(){
	getUntneReduceDetail();
	checkValue();
}

function clickUntne022f(){
	var prop="";
	//var windowTop=(document.body.clientHeight-80)/2+25;
	//var windowLeft=(document.body.clientWidth-800)/2+250;
	//prop=prop+"width=775,height=475,";
	
	var iwidth=screen.availWidth;
	var iheight=screen.availHeight;
	var windowTop=0;
	var windowLeft=0;
	prop=prop+"width="+iwidth+",";
	prop=prop+"height="+iheight+",";
	
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	var enterOrg="<%=obj.getEnterOrg()%>";
	var ownership="<%=obj.getOwnership()%>";
	var caseNo1="<%=obj.getCaseNo1()%>";
	var dealDate="<%=obj.getDealDate()%>";
	var verify="<%=obj.getVerify()%>";
	var differenceKind="<%=obj.getDifferenceKind()%>";
	beforeSubmit();
	returnWindow=window.open("untne022f.jsp?enterOrg="+enterOrg+"&ownership="+ownership+"&dealDate="+dealDate+"&caseNo1="+caseNo1+"&verify="+verify+"&differenceKind="+differenceKind,"aha",prop);
}
function changeDifferenceKind(){
	$("input[name='propertyNo']").val('');
	$("input[name='serialNo']").val('');
	
	if($("select[name='differenceKind']").val() == ''){
		setFormItem("propertyNO,btn_propertyNo,serialNo","R");		
	}else{
		setFormItem("propertyNO,btn_propertyNo,serialNo","O");
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位===========================================================-->
<input type="hidden" name="check" value="">
<!--  <input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>"> -->
<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
<input class="field_RO" type="hidden" name="valuable" value="<%=obj.getValuable()%>">
<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE020Q",obj);%>
	<jsp:include page="untne020q.jsp" />
</div>
<!--頁籤區=============================================================-->
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untne020f.jsp');"><font color="red">*</font>處理單資料</a></td>		
		<td ID=t2 CLASS="tab_border1">*處理單資料明細</td>		
	</tr>
	<tr>
		<td class="tab_line2"></td>
		<td class="tab_line1"></td>
	</tr>
</TABLE>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="15%">入帳機關：</td>
		<td class="td_form_white" colspan="3">		
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">
			]
		　&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>						
			&nbsp;&nbsp;&nbsp;&nbsp;　　　處理單號：
			[
			<input class="field_QRO" type="text" name="caseNo1" size="10" maxlength="10" value="<%=obj.getCaseNo1()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">物品區分別：</td>
		<td colspan="3" class="td_form_white">
			<select class="field_QRO" type="select" name="differenceKind" onchange="changeDifferenceKind();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,obj.getDifferenceKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">處理日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="dealDate" size="15" maxlength="7" value="<%=obj.getDealDate()%>">]
			&nbsp;&nbsp;&nbsp;　　　　　　　　審核：
				<select class="field_QRO" type="select" name="verify">
  					<%=util.View.getYNOption(obj.getVerify())%>
				</select>
		<br>
			變賣金額：<input class="field" type="text" name="realizeValue1" size="15" maxlength="15" value="<%=obj.getRealizeValue1()%>">		
			&nbsp;&nbsp;　　繳存地點：<input class="field" type="text" name="returnPlace" size="25" maxlength="25" value="<%=obj.getReturnPlace()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%"><font color="red">*</font>物品編號：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_P" type="text" name="propertyNo" size="12" maxlength="12" value="<%=obj.getPropertyNo()%>" onChange="getProperty('propertyNo','propertyNoName','','NE')">
			<input class="field_P" type="button" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyNoName','6&isLookup=N')" value="..." title="物品編號輔助視窗">
			[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
			<font color="red">*</font>分號：
				<input class="field_P" type="text" name="serialNo" size="5" maxlength="7" value="<%=obj.getSerialNo()%>" onFocusOut="getUntneReduceDetail();checkValue();">
			<font color="red">*</font>減損單號：
				<input class="field_P" type="text" name="caseNo" size="8" maxlength="10" value="<%=obj.getCaseNo()%>" onFocusOut="getUntneReduceDetail();checkValue();">
			<br>
				別名：[
				<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">
				] 
				　物品批號：[
				<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">
				] 
			<br>
				原物品編號：[
				<input class="field_RO" type="text" name="oldPropertyNo" size="12" value="<%=obj.getOldPropertyNo()%>">] 
		　		　 　&nbsp;&nbsp;&nbsp;原分號：[
				<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form" width="15%">主要材質：</td>
		<td class="td_form_white" colspan="3"> 
			[<input class="field_RO" type="text" name="material" size="20" value="<%=obj.getMaterial()%>">]
			其他主要材質：
			[<input class="field_RO" type="text" name="otherMaterial" size="25" maxlength="50" value="<%=obj.getOtherMaterial()%>">]
		<br>
			單位：
			[<input class="field_RO" type="text" name="propertyUnit" size="7" maxlength="7" value="<%=obj.getPropertyUnit()%>">]　 　  　　
			&nbsp;&nbsp;&nbsp;其他單位：
			[<input class="field_RO" type="text" name="otherPropertyUnit" size="25" maxlength="50" value="<%=obj.getOtherPropertyUnit()%>">] 
		<br>
	  		使用年限： 
	  		[<input class="field_RO" type="text" name="limitYear" size="8" maxlength="7" value="<%=obj.getLimitYear()%>">]
			&nbsp;&nbsp;&nbsp;調整後年限(月)：
			[<input class="field_RO" type="text" name="otherLimitYear" size="10" maxlength="10" value="<%=obj.getOtherLimitYear()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">已使用年月：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="useYear" size="10" value="<%=obj.getUseYear()%>">]年
			[<input class="field_RO" type="text" name="useMonth" size="10" value="<%=obj.getUseMonth()%>">]個月
	</tr>
	<tr>
		<td class="td_form" width="15%">廠牌型式：</td>
		<td class="td_form_white" colspan="3">		
			品名：[<input class="field_RO" type="text" name="articleName" size="20" value="<%=obj.getArticleName()%>">]　
			廠牌：[<input class="field_RO" type="text" name="nameplate" size="40" maxlength="40" value="<%=obj.getNameplate()%>" >]
		<br>
			型式：[<input class="field_RO" type="text" name="specification" size="40" maxlength="40" value="<%=obj.getSpecification()%>">]　
			牌照號碼規格：[<input class="field_RO" type="text" name="licensePlate" size="30" maxlength="30" value="<%=obj.getLicensePlate()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">入帳日期：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">] 
			購置日期：[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">] 
			取得日期：[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">] 
			減損日期：[<input class="field_RO" type="text" name="reduceDate" size="7" maxlength="7" value="<%=obj.getReduceDate()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">物品性質：</td>
		<td class="td_form_white" colspan="3">
			<select class="field" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			　基金物品：
			<select class="field_RO" type="select" name="fundType">
				<%=util.View.getOption("select a.codeID, a.codeName from SYSCA_Code a , SYSCA_FUNDORGAN b where a.codeKindID='FUD' and a.codeid=b.fundno"  , obj.getFundType())%>
			</select>
        </td>
	</tr>
	<tr>
		<td class="td_form" width="15%">會計科目：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="accountingTitle" size="40" value="<%=obj.getAccountingTitle()%>">]
<!--		珍貴物品：
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
-->			
	</tr>
	<tr>
		<td class="td_form" width="15%">原帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="oldBookAmount" size="7" maxlength="7" value="<%=obj.getOldBookAmount()%>">] 
			　單價：[<input class="field_RO type="text" name="oldBookUnit" size="13" maxlength="13" value="<%=obj.getOldBookUnit()%>">] 
			　總價：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">減少帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="adjustBookAmount" size="7" maxlength="7" value="<%=obj.getAdjustBookAmount()%>">] 
			　總價：[<input class="field_RO" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">新帳面資料：</td>
		<td class="td_form_white" colspan="3">
			數量：[<input class="field_RO" type="text" name="newBookAmount" size="7" maxlength="7" value="<%=obj.getNewBookAmount()%>">] 
			　總價：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">] 
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">移動資料：</td>
			<td class="td_form_white" colspan="3">
			保管單位
			<select class="field_RO" type="select" name="keepUnit">
			　　<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getKeepUnit())%>
			</select>
			&nbsp;&nbsp;&nbsp;保管人
			<select class="field_RO" type="select" name="keeper">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getKeeper())%>
	        </select>
			<br>
			使用單位
			<select class="field_RO" type="select" name="useUnit" >
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ",obj.getUseUnit())%>
			</select>	
			&nbsp;&nbsp;&nbsp;使用人
			<select class="field_RO" type="select" name="userNo">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getEnterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getUserNo())%>
	        </select>
		    &nbsp;&nbsp;&nbsp;
			使用註記：
			[<input type="text" class="field_RO" name="userNote" value="<%=obj.getUserNote() %>" size="20">]
			<br>
			移動日期：
			[<input class="field_RO" type="text" name="moveDate" size="7" maxlength="7" value="<%=obj.getMoveDate()%>">]
	        <br>
	                    存置地點
			[<input class="field_RO" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1()%>" onchange="queryPlaceName('enterOrg','place1');">]
			[<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name()%>">]
			<br>		
			存置地點說明
			[<input class="field_RO" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace()%>">]		
		</td>
	</tr>
	<tr>
		<td class="td_form" width="15%">備註：</td>
		<td class="td_form_white"><textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea></td>
	  	<td class="td_form"style="display:none;">異動人員/日期：</td>
	  	<td class="td_form_white"style="display:none;">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
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
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="untne022f" value="現有資料明細新增" onClick="clickUntne022f();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">處理日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">減少數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">減少總價</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,false,false,false,true,true,false,true,true,true,true};
	boolean displayArray[] = {false,true,true,false,false,true,true,true,true,true,true,false,false,false,false,false,false,false};
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
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[0].checked=true;
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
			setFormItem("ownership,verify","R");
			setFormItem("untne022f","R");
			break;
		case "insertError":
			setFormItem("ownership,verify","R");
			setFormItem("untne022f","R");
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify","R");
			setFormItem("untne022f","R");
			break;
		case "updateError":
			setFormItem("ownership,verify","R");
			setFormItem("untne022f","R");
			break;
	
		//取消時要執行的動作
		case "clear":
			setFormItem("untne022f","O");
			break;
		case "clearError":
			setFormItem("untne022f","O");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("untne022f","O");
			break;
		case "confirmError":
			setFormItem("untne022f","O");
			break;
	}
}
</script>
</body>
</html>



