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
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}

String message = "";
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.dp.UNTDP002F)obj.queryOne();	
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
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	
);

function getEnterDate() {

}

function checkField(){

	var alertStr="";
	if (form1.state.value=="update"||form1.state.value=="updateError"){
	alertStr += checkEmpty(form1.monthDepr1,"價值：當年度折舊");
	alertStr += checkEmpty(form1.monthDepr2,"價值：以前年度折舊");
	alertStr += checkNumber(form1.monthDepr1,"價值：當年度折舊");
	alertStr += checkNumber(form1.monthDepr2,"價值：以前年度折舊");
	if(<%=obj.getDeprUnitCB().equals("Y") %>){
	alertStr += checkEmpty(form1.scaledMonthDepr1,"價值(依比例)：當年度折舊");
	alertStr += checkEmpty(form1.scaledMonthDepr2,"價值(依比例)：以前年度折舊");
	alertStr += checkNumber(form1.scaledMonthDepr1,"價值(依比例)：當年度折舊");
	alertStr += checkNumber(form1.scaledMonthDepr2,"價值(依比例)：以前年度折舊");}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function queryOne(enterOrg,ownership,differenceKind,propertyNo,serialNo,serialNo1){
	
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.differenceKind.value=differenceKind;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();

}

function clickApproveAll(){

}

function checkURL(surl){

}

function init(){

	setDisplayItem("spanInsert,spanNextInsert","H");
	if(form1.verify.value=='Y'){
		setDisplayItem("spanUpdate,spanDelete", "H");
	}
}

function loadUntch012r(){
	
}

function loadUntch013r(){
	
}
function checkBuildFeeCB(){	
	if($("input[name='q_buildFeeCB']").attr('checked') == 'checked'){
		$("input[name='q_buildFeeCB']").val('Y');
	}else{
		$("input[name='q_buildFeeCB']").val('N');
	}
}

function checkDeprUnitCB(){
	if($("input[name='q_deprUnitCB']").attr('checked') == 'checked'){
		$("input[name='q_deprUnitCB']").val('Y');
	}else{
		$("input[name='q_deprUnitCB']").val('N');
	}
}
function checkID()
{
	if(form1.isAdminManager.value=='Y'){
	
		document.getElementById('tr1').style.display="";
	}	   
}
function changePropertyNo(propertyNo){}

function changevalue()
{

	form1.monthDepr.value=parseInt(form1.monthDepr1.value)+parseInt(form1.monthDepr2.value);
	form1.newAccumDepr.value=parseInt(form1.oldAccumDepr.value)+parseInt(form1.addAccumDepr.value)-parseInt(form1.reduceAccumDepr.value)+parseInt(form1.monthDepr.value);
	form1.newNetValue.value=parseInt(form1.bookValue.value)-parseInt(form1.newAccumDepr.value);
	//newAccumDepr = form1.newAccumDepr.value;
	//monthdepr1 = form1.monthdepr1.value;
	//monthdepr1 = form1.monthdepr1.value;
	
}

</script>
</head>

<body topmargin="0" onLoad="checkID();whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="propertyType" value="<%=obj.getPropertyType()%>">
	<input type="hidden" name="propertyKind" value="<%=obj.getPropertyKind()%>">
	<input type="hidden" name="fundType" value="<%=obj.getFundType()%>">
	<input type="hidden" name="valuable" value="<%=obj.getValuable()%>">
	<input type="hidden" name="editID" value="<%=obj.getEditID()%>">
	<input type="hidden" name="editDate" value="<%=obj.getEditDate()%>">
	<input type="hidden" name="editTime" value="<%=obj.getEditTime()%>">
</td></tr></table>

<!--Query區============================================================-->
<div id="queryContainer" style="width:900px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTDP002Q",obj);%>
	<jsp:include page="untdp002q.jsp" />
</div>

<!--頁籤區============================================================-->
<table STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">	
		
</table>

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
		<tr id="tr1" style="display:none">
			<td class="td_form" >入帳機關：</td>
	        <td class="td_form_white" colspan="3">
		  	    <%=util.View.getPopOrgan("field_RO","enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		    </td>		
		</tr>
		<tr>
			<td class="td_form">財產編號：</td>
			<td class="td_form_white" colspan="3">
				[<input class="field_PRO" type="text" name="propertyNo" size="10" maxlength="11" value="<%=obj.getPropertyNo()%>" onBlur="getProperty('propertyNo','propertyNoName','');">]
				[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">] 
				&nbsp;&nbsp;
				分號：
				[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
				&nbsp;&nbsp;
				財產批號：[<input class="field_RO" type="text" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">]
				&nbsp;&nbsp;
			         權屬：
			   <input type="hidden" class="field_RO" name="ownership" value="<%=obj.getOwnership()%>" size="10">
			   [<input type="text" class="field_RO" name="ownershipName" value="<%=obj.getOwnershipName()%>" size="10">] 							
			</td>
		</tr>
		<tr>
		<td class="td_form">折舊次序：</td>
			<td class="td_form_white" colspan="3">
			[<input type="text" class="field_RO" name="serialNo1" value="<%=obj.getSerialNo1()%>" size="10">]
			&nbsp;&nbsp;
			折舊入帳：
			[<input type="text" class="field_RO" name="verify" value="<%=obj.getVerify()%>" size="10">]
		</tr>
		<tr>
			<td class="td_form">購置日期：</td>
			<td class="td_form_white" colspan="3">				
				[<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>">]
				&nbsp;&nbsp;&nbsp;
				財產別名
				[<input type="text" class="field_RO" name="propertyName1" value="<%=obj.getPropertyName1()%>" size="30" maxlength="30">]
			</td>
		</tr>
		<tr>
			<td class="td_form">使用年限：</td>
			<td class="td_form_white" colspan="3">	
				[<input class="field_RO" type="text" name="limitYear" size="7" maxlength="7" value="<%=obj.getLimitYear()%>">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				入帳日期：
				[<input class="field_RO" type="text" name="enterDate" size="7" maxlength="7" value="<%=obj.getEnterDate()%>">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				取得日期：
				[<input class="field_RO" type="text" name="sourceDate" size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				數量：
				[<input class="field_RO" type="text" name="bookAmount" size="7" maxlength="7" value="<%=obj.getBookAmount()%>">]
			</td>
		</tr>
		<tr>
			<td class="td_form" width="22%">折舊年月：</td>
			<td class="td_form_white" colspan="3">
				[<input type="text" class="field_RO" name="deprYM" value="<%=obj.getDeprYM()%>" size="7">]
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				財產區分別：
				<input type="hidden" class="field_RO" name="differenceKind" value="<%=obj.getDifferenceKind()%>" size="10">
				[<input type="text" class="field_RO" name="differenceKindName" value="<%=obj.getDifferenceKindName()%>" size="15">]					
			</td>
		</tr>			
		<tr id="div_depr">
			<td class="td_form">折舊資料：</td>
			<td class="td_form_white" colspan="3">
				折舊方法：
				[<input type="text" class="field_RO" name="deprMethod" value="<%=obj.getDeprMethod()%>" size="18">]
				&nbsp;&nbsp;
				<input class="field_RO" type="checkbox" name="buildFeeCB" size="10" maxlength="10" value="<%=obj.getBuildFeeCB()%>" <%=("Y".equals(obj.getBuildFeeCB())?"checked":"")%> >
				屬公共設施建設費：
				&nbsp;&nbsp;
				<input class="field" type="checkbox" name="cb_deprUnitCB" size="10" maxlength="10"   <%=("Y".equals(obj.getDeprUnitCB())?"checked":"")%>>
       			<input class="field_RO" type="hidden" name="deprUnitCB" size="10" maxlength="10" value="<%=obj.getDeprUnitCB()%>" >	
				部門別依比例分攤：
				<br>
				園區別：
				[<input type="text" class="field_RO" name="deprPark" value="<%=obj.getDeprPark()%>" size="10">]
				&nbsp;&nbsp;&nbsp;
				部門別：
				[<input type="text" class="field_RO" name="deprUnit" value="<%=obj.getDeprUnit()%>" size="10">]
				&nbsp;&nbsp;&nbsp;
				部門別單位：
				[<input type="text" class="field_RO" name="deprUnit1" value="<%=obj.getDeprUnit1()%>" size="10">]
				&nbsp;&nbsp;&nbsp;
				會計科目：
				[<input type="text" class="field_RO" name="deprAccounts" value="<%=obj.getDeprAccounts()%>" size="10">]
				<br>
				殘值：
				[<input class="field_RO" type="text" id="scrapValue" name="scrapValue" size="10" maxlength="10" value="<%=obj.getScrapValue()%>">]
				&nbsp;&nbsp;
				攤提壽月
				[<input class="field_RO" type="text" name="apportionMonth" size="7" maxlength="7" value="<%=obj.getApportionMonth()%>">]
				分攤百分比(%)：
				[<input type="text" class="field_RO" name="deprPercent" value="<%=obj.getDeprPercent()%>" size="10">]
			</td>
		</tr>
		<tr>
			<td class="td_form">價值：</td>				
			<td class="td_form_white" colspan="3">
				原始入帳價值：[<input type="text" class="field_RO" name="originalBV" value="<%=obj.getOriginalBV()%>" size="10">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				原值：[<input type="text" class="field_RO" name="bookValue" value="<%=obj.getBookValue()%>" size="10">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				折舊前淨值：[<input type="text" class="field_RO" name="oldNetValue" value="<%=obj.getOldNetValue()%>" size="10">]
				<br>
				上月累計折舊	：<input type="text" class="field_RO" name="oldAccumDepr" value="<%=obj.getOldAccumDepr()%>" size="10">
				&nbsp;&nbsp;&nbsp;
				本月累計折舊增加	：<input type="text" class="field_RO" name="addAccumDepr" value="<%=obj.getAddAccumDepr()%>" size="10">
				&nbsp;&nbsp;&nbsp;
				本月累計折舊減少	：<input type="text" class="field_RO" name="reduceAccumDepr" value="<%=obj.getReduceAccumDepr()%>" size="10">
				<br>				
				<font color="red">*</font>當年度折舊	：<input type="text" class="field" name="monthDepr1" value="<%=obj.getMonthDepr1()%>" size="10" onchange="changevalue();" onblur="changevalue();" >
				&nbsp;&nbsp;+&nbsp;&nbsp;
				以前年度折舊	：<input type="text" class="field" name="monthDepr2" value="<%=obj.getMonthDepr2()%>" size="10" onchange="changevalue();" onblur="changevalue();">
				&nbsp;&nbsp;=&nbsp;&nbsp;
				本月折舊	：<input type="text" class="field_RO" name="monthDepr" value="<%=obj.getMonthDepr()%>" size="10">
				<br>
				本期累計折舊	：[<input type="text" class="field_RO" name="newAccumDepr" value="<%=obj.getNewAccumDepr()%>" size="10">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				折舊後淨值：[<input type="text" class="field_RO" name="newNetValue" value="<%=obj.getNewNetValue()%>" size="10">]
<!--				<input class="field" type="checkbox" name="recalCulated" size="10" maxlength="10" value="Y" <%=("Y".equals(obj.getRecalCulated())?"checked":"")%> >是否重新計算折舊：-->
			</td>
		</tr>
		<tr>
			<td class="td_form">價值(依比例)：</td>				
			<td class="td_form_white" colspan="3">
				原值：[<input type="text" class="field_RO" name="scaledBookValue" value="<%=obj.getScaledBookValue()%>" size="10">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				折舊前淨值：[<input type="text" class="field_RO" name="scaledOldNetValue" value="<%=obj.getScaledOldNetValue()%>" size="10">]
				<br>
				上月累計折舊	：<input type="text" class="field_RO" name="scaledOldAccumDepr" value="<%=obj.getScaledOldAccumDepr()%>" size="10">
				&nbsp;&nbsp;&nbsp;
				本月累計折舊增加	：<input type="text" class="field_RO" name="scaledAddAccumDepr" value="<%=obj.getScaledAddAccumDepr()%>" size="10">
				&nbsp;&nbsp;&nbsp;
				本月累計折舊減少	：<input type="text" class="field_RO" name="scaledReduceAccumDepr" value="<%=obj.getScaledReduceAccumDepr()%>" size="10">
				<br>
				<%if(obj.getDeprUnitCB().equals("Y")) {%>				
				當年度折舊	：<input type="text" class="field_RO" name="scaledMonthDepr1" value="<%=obj.getScaledMonthDepr1()%>" size="10">
				&nbsp;&nbsp;&nbsp;&nbsp;
				以前年度折舊	：<input type="text" class="field_RO" name="scaledMonthDepr2" value="<%=obj.getScaledMonthDepr2()%>" size="10">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%}else if (obj.getDeprUnitCB().equals("N")) {%>
				當年度折舊	：<input type="text" class="field_RO" name="scaledMonthDepr1" value="<%=obj.getScaledMonthDepr1()%>" size="10">
				&nbsp;&nbsp;&nbsp;&nbsp;
				以前年度折舊	：<input type="text" class="field_RO" name="scaledMonthDepr2" value="<%=obj.getScaledMonthDepr2()%>" size="10">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%} %>
				本月折舊	：<input type="text" class="field_RO" name="scaledMonthDepr" value="<%=obj.getScaledMonthDepr()%>" size="10">
				<br>
				本期累計折舊	：[<input type="text" class="field_RO" name="scaledNewAccumDepr" value="<%=obj.getScaledNewAccumDepr()%>" size="10">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				折舊後淨值：[<input type="text" class="field_RO" name="scaledNewNetValue" value="<%=obj.getScaledNewNetValue()%>" size="10">]&nbsp;
			</td>
		</tr>
		<tr>
			<td class="td_form" width="22%">保管單位：</td>
			<td class="td_form_white" colspan="3">
				[<input type="text" class="field_RO" name="keepUnit" value="<%=obj.getKeepUnit()%>" size="10">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				保管人：
				[<input type="text" class="field_RO" name="keeper" value="<%=obj.getKeeper()%>" size="10">]
			</td>
		</tr>
			<tr>
			<td class="td_form">代管資產：</td>				
			<td class="td_form_white" colspan="3">
				代管日後延長使用月數：[<input type="text" class="field_RO" name="escrowOriLimitMonth" value="<%=obj.getEscrowOriLimitMonth()%>" size="10">]
				&nbsp;&nbsp;&nbsp;&nbsp;
				原始總價：[<input type="text" class="field_RO" name="escrowOriValue" value="<%=obj.getEscrowOriValue()%>" size="10">]
				&nbsp;&nbsp;&nbsp;
				代管日前累計折舊	：<input type="text" class="field_RO" name="escrowOriAccumDepr" value="<%=obj.getEscrowOriAccumDepr()%>" size="10">
			</td>
		</tr>
		<tr>
			<td class="td_form">工程編號：</td>				
			<td class="td_form_white" colspan="3">
			[<input type="text" class="field_RO" name="engineeringNo" value="<%=obj.getEngineeringNo()%>" size="10">]
			</td>
		</tr>	
		<tr>
		  <td class="td_form">備註：</td>
		  <td class="td_form_white">
		  	<textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea></td>
		  </td>
		</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;">
	<jsp:include page="../../home/toolbar.jsp" />
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">折舊年月</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">財產編號<br>財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">殘值</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">使用年限</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">購置日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">入帳日期</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">原始入帳價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">原值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">折舊前淨值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">本月金額</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">折舊後價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,true};
	boolean displayArray[] = {false,false,false,false,false,false,true,true,true,true,true,true,true,true,true,true,true,true,false};
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
