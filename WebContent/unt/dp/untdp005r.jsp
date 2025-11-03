<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP005R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("printAddProof1".equals(obj.getState())) {
objList = obj.printAddProof1();
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
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	
);

function getEnterDate() {

}

function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.enterOrg,"原值");
	alertStr += checkEmpty(form1.ownership,"殘值");
	alertStr += checkEmpty(form1.writeDate,"使用年限");
	alertStr += checkInt(form1.enterOrg,"原值");
	alertStr += checkInt(form1.ownership,"殘值");
	alertStr += checkInt(form1.writeDate,"使用年限");
	alertStr += checkEmpty(form1.writeDate,"折舊年月");
	alertStr += checkEmpty(form1.writeDate,"購置日期");
	alertStr += checkEmpty(form1.writeDate,"入帳日期");
	alertStr += checkYYYMM(form1.writeDate,"折舊年月");
	alertStr += checkYYYMM(form1.writeDate,"購置日期");
	alertStr += checkYYYMM(form1.writeDate,"入帳日期");
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function queryOne(enterOrg,ownership,caseNo){

}

function clickApproveAll(){

}

function checkURL(surl){
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}

function init(){
	initFormDisabled();

}

function loadUntch012r(){
	
}

function loadUntch013r(){
	
}
function testCompute(){

	var alertStr="";
	var addBookValue = parseInt(form1.addBookValue.value);
	var reduceBookValue = parseInt(form1.reduceBookValue.value);
	var deprYM = parseInt(form1.deprYM.value);
	var buyDate = parseInt(form1.buyDate.value);
	var enterDate = parseInt(form1.enterDate.value);
	var adjustDate = parseInt(form1.adjustDate.value);
	alertStr += checkEmpty(form1.bookValue,"原值");
	alertStr += checkEmpty(form1.scrapValue,"殘值");
	alertStr += checkEmpty(form1.limitYear,"使用年限");
	alertStr += checkInt(form1.bookValue,"原值");
	alertStr += checkInt(form1.scrapValue,"殘值");
	alertStr += checkInt(form1.limitYear,"使用年限");
	alertStr += checkEmpty(form1.deprYM,"折舊年月");
	alertStr += checkEmpty(form1.buyDate,"購置日期");
	alertStr += checkEmpty(form1.enterDate,"入帳日期");
	alertStr += checkYYYMM(form1.deprYM,"折舊年月");
	alertStr += checkYYYMM(form1.buyDate,"購置日期");
	alertStr += checkYYYMM(form1.enterDate,"入帳日期");
	if(enterDate<buyDate){
		alertStr += ("入帳年月必須大於等於購置年月");
		}
	if(enterDate>deprYM){
		alertStr += ("入帳年月必須小於等於折舊月份");
		}
	if(deprYM<=buyDate){
		alertStr += ("購置年月必須小於折舊月份");
		}
	if($("input[name='cb_addValue']").attr('checked') == 'checked'){
		
		form1.addValue.value="Y";
		alertStr += checkEmpty(form1.adjustDate,"增減值年月");
		alertStr += checkYYYMM(form1.adjustDate,"增減值年月");
		alertStr += checkInt(form1.addBookValue,"增值");
		alertStr += checkInt(form1.reduceBookValue,"減值");
		if(adjustDate<=enterDate){
			alertStr += ("增減值年月必須大於入帳年月");
		  }
		if(addBookValue<0 || reduceBookValue<0){
			alertStr += ("增減值必須大於0");
		  }
		}
	else{
		form1.addValue.value="N";
		}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	form1.state.value="printAddProof1";
	form1.submit();
}
function loadUntdp005r(){
	var alertStr="";
	var addBookValue = parseInt(form1.addBookValue.value);
	var reduceBookValue = parseInt(form1.reduceBookValue.value);
	var deprYM = parseInt(form1.deprYM.value);
	var buyDate = parseInt(form1.buyDate.value);
	var enterDate = parseInt(form1.enterDate.value);
	var adjustDate = parseInt(form1.adjustDate.value);
	alertStr += checkEmpty(form1.bookValue,"原值");
	alertStr += checkEmpty(form1.scrapValue,"殘值");
	alertStr += checkEmpty(form1.limitYear,"使用年限");
	alertStr += checkInt(form1.bookValue,"原值");
	alertStr += checkInt(form1.scrapValue,"殘值");
	alertStr += checkInt(form1.limitYear,"使用年限");
	alertStr += checkEmpty(form1.deprYM,"折舊年月");
	alertStr += checkEmpty(form1.buyDate,"購置日期");
	alertStr += checkEmpty(form1.enterDate,"入帳日期");
	alertStr += checkYYYMM(form1.deprYM,"折舊年月");
	alertStr += checkYYYMM(form1.buyDate,"購置日期");
	alertStr += checkYYYMM(form1.enterDate,"入帳日期");
	if(enterDate<buyDate){
		alertStr += ("入帳年月必須大於等於購置年月");
		}
	if(enterDate>deprYM){
		alertStr += ("入帳年月必須小於等於折舊月份");
		}
	if(deprYM<=buyDate){
		alertStr += ("購置年月必須小於折舊月份");
		}
	if($("input[name='cb_addValue']").attr('checked') == 'checked'){
		
		form1.addValue.value="Y";
		alertStr += checkEmpty(form1.adjustDate,"增減值年月");
		alertStr += checkYYYMM(form1.adjustDate,"增減值年月");
		alertStr += checkInt(form1.addBookValue,"增值");
		alertStr += checkInt(form1.reduceBookValue,"減值");
		if(adjustDate<=enterDate){
			alertStr += ("增減值年月必須大於入帳年月");
		  }
		if(addBookValue<0 || reduceBookValue<0){
			alertStr += ("增減值必須大於0");
		  }
		}
	else{
		form1.addValue.value="N";
		}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	var url = "untdp005p.jsp?"
			  + "bookValue=" + form1.bookValue.value 
			  + "&scrapValue=" + form1.scrapValue.value
			  + "&limitYear=" + form1.limitYear.value
			  + "&deprYM=" + form1.deprYM.value
			  + "&buyDate=" + form1.buyDate.value
			  + "&enterDate=" + form1.enterDate.value
			  + "&addValue=" + form1.addValue.value 
			  + "&adjustDate=" + form1.adjustDate.value
			  + "&addBookValue=" + form1.addBookValue.value 
			  + "&reduceBookValue=" + form1.reduceBookValue.value
			  + "&apportionYear1=" + form1.apportionYear1.value  ; 
			alert(url);
	window.open(url);
}
function initFormDisabled(){
	$("#div_date1").hide();
	$("#div_date2").hide();
	
}
function addReduceValue(){
	if($("input[name='cb_addValue']").attr('checked') == 'checked'){
	
	$("#div_date1").show();
	$("#div_date2").show();	
		}	
	else{
	form1.addBookValue.value="";
	form1.reduceBookValue.value="";
	form1.adjustDate.value="";
	form1.apportionYear1.value="";
	$("#div_date1").hide();
	$("#div_date2").hide();
		}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()" >

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	
</td></tr></table>

<!--Query區============================================================-->

<!--頁籤區============================================================-->

<!--Form區============================================================-->
<table width="100%"  cellspacing="0" cellpadding="0" align="center">
<tr><td class="bg">
	<div>
	<table class="table_form" width="100%" height="100%">
		<tr>
			<td class="queryTDLable">原值：</td>
			<td class="queryTDInput" width="30%">
				<input type="text" class="field_Q" name="bookValue" value="<%=obj.getBookValue()%>" size="10">
			</td>
			<td class="queryTDLable">殘值：</td>
			<td class="queryTDInput">
				<input type="text" class="field_Q" name="scrapValue" value="<%=obj.getScrapValue()%>" size="10">
			</td>
			<td class="queryTDLable">使用年限：</td>
			<td class="queryTDInput">
				<input type="text" class="field_Q" name="limitYear" value="<%=obj.getLimitYear() %>" size="10">
			</td>
		</tr>
		<tr>
			<td class="queryTDLable">折舊年月：</td>
			<td class="queryTDInput">
				<input type="text" class="field_Q" name="deprYM" value="<%=obj.getDeprYM()%>" size="5">
				<font color="red">例如：103年 2月，則輸入10302</font>
			</td>
			<td class="queryTDLable">購置日期：</td>
			<td class="queryTDInput">
			<input type="text" class="field_Q" name="buyDate" value="<%=obj.getBuyDate()%>" size="5">
			</td>
			<td class="queryTDLable">入帳日期：</td>
			<td class="queryTDInput">
			<input type="text" class="field_Q" name="enterDate" value="<%=obj.getEnterDate()%>" size="5">
			</td>
		</tr>
		<tr>
		<td class="queryTDLable">添加增減值：</td>
		<td class="queryTDInput" >
		<input class="field_Q" type="checkbox" name="cb_addValue" size="10" maxlength="10" onclick="addReduceValue();">
		<input class="field_QRO" type="hidden" name="addValue" size="10" maxlength="10" value="<%=obj.getAddValue()%>">
		</td>
		</tr>
		<tr id="div_date1">
			<td class="queryTDLable">增減值年月：</td>
			<td class="queryTDInput">
			<input type="text" class="field_Q" name="adjustDate" value="<%=obj.getAdjustDate()%>" size="5">
			</td>
			<td class="queryTDLable">增值：</td>
			<td class="queryTDInput">
			<input type="text" class="field_Q" name="addBookValue" value="<%=obj.getAddBookValue()%>" size="10">
			</td>
			<td class="queryTDLable">減值：</td>
			<td class="queryTDInput">
			<input type="text" class="field_Q" name="reduceBookValue" value="<%=obj.getReduceBookValue()%>" size="10">
			</td>
		</tr>
		<tr id="div_date2">
			<td class="queryTDLable" >增減值年限：</td>
			<td class="queryTDInput" colspan="3">
			<input type="text" class="field_Q" name="apportionYear1" value="<%=obj.getApportionYear1() %>" size="10">
			</td>
		</tr>
	</table>
	</div>
</td></tr>


<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;">
	<div style="display:none;">
		<jsp:include page="../../home/toolbar.jsp" />
	</div>
	<span id="btnUntch01r">
		&nbsp;|
		<input class="toolbar_default" type="button" followPK="false" name="printAddProof1" value="　試算　" onClick="testCompute();">&nbsp;|
	</span>
	<span id="btnUntch02r">
		<input class="toolbar_default" type="button" followPK="false" name="printAddProof2" value="　匯出Excel　" onClick="loadUntdp005r();">&nbsp;|
	</span>
	
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div>
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">

	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">折舊年月</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">原值</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">現值</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">殘值</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">使用年限</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">購置日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',7,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',8,false);" href="#">本月折舊</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',9,false);" href="#">帳面價值</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',10,false);" href="#">累計折舊</a></th>
	
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,false,false,false,false,false};
	boolean displayArray[] = {true,true,true,true,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script type="text/javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "printAddProof1":
			$("input[name='cb_addValue']").attr('checked') == 'checked'
			$("#div_date1").show();
			$("#div_date2").show();	
			
			break;					
	}
}

</script>
</body>
</html>
