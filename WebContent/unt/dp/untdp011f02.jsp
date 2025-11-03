<!--
程式目的： 財產折舊資料調整-折舊比例
程式代號：untdp011f02
程式日期：103/09/01
程式作者： Mike Kao
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP011F02">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setEnterOrg(user.getOrganID());

if ("queryOne".equals(obj.getState())) {
	obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("init".equals(obj.getState()) ){
	obj.setIsHistory("N");
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}

String checkDeprPercent_All = obj.checkDeprPercent_All();	// 全部分攤比例總和
String checkDeprPercent_queryOne = obj.checkDeprPercent_queryOne();	// 除了此筆以外的分攤比例總和
String checkDeprShareAmount_All = obj.checkDeprShareAmount_All(); // 全部分攤金額總和

if ("insertSuccess".equals(obj.getState()) || "updateSuccess".equals(obj.getState()) || "deleteSuccess".equals(obj.getState())) {
	if (Common.getNumeric(obj.getBookValue())>Common.getNumeric(checkDeprShareAmount_All)) {
		obj.setErrorMsg("分攤總金額小於總價 "+obj.getBookValue()+"，請繼續新增分攤資料。");
	}
}

objList = obj.queryAll();
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
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
var changeDepr = false;
var checkDeprPercent_All = '<%=checkDeprPercent_All%>';
var checkDeprPercent_queryOne = '<%=checkDeprPercent_queryOne%>';
var checkDeprShareAmount_All = '<%=checkDeprShareAmount_All%>';
insertDefault = new Array(
		new Array("enterOrg","<%=user.getOrganID()%>"),
		new Array("ownership","<%=obj.getOwnership()%>"),
		new Array("differenceKind","<%=obj.getDifferenceKind()%>"),
		new Array("propertyNo","<%=obj.getPropertyNo()%>"),
		new Array("serialNo","<%=obj.getSerialNo()%>"),
		new Array("propertyNoName","<%=obj.getPropertyNoName()%>"),
		new Array("deprPark","<%=obj.getDeprUnit()%>")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");	
		alertStr += checkEmpty(form1.ownership,"權屬");	
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");	
		alertStr += checkEmpty(form1.propertyNo,"財產編號");	
		alertStr += checkEmpty(form1.serialNo,"財產分號");	
		alertStr += checkEmpty(form1.deprPark,"園區別");	
		alertStr += checkEmpty(form1.deprUnit,"部門別");	
		alertStr += checkEmpty(form1.deprUnit1,"部門別單位");	
		alertStr += checkEmpty(form1.deprAccounts,"會計科目");	
		alertStr += checkFloat(form1.deprPercent,"分攤百分比(%)",3,2);
		alertStr += checkEmpty(form1.isHistory,"歷史資料");	
		alertStr += checkEmpty(form1.deprShareAmount,"分攤金額");
		if(form1.deprShareAmount.value != ""){
			alertStr += checkInt(form1.deprShareAmount,"分攤金額");
		}
	}
	
	if(form1.state.value == "insert"||form1.state.value=="insertError"){
		checkStr = (getNumeric(form1.deprPercent.value) + getNumeric(checkDeprPercent_All));
	}else{
		checkStr = (getNumeric(form1.deprPercent.value) + getNumeric(checkDeprPercent_queryOne));
	}
	if(checkStr > 100){
		form1.deprPercent.style.backgroundColor=errorbg;
		alertStr += "分攤百分比總和必須小於等於100\n";
	}else{
		form1.deprPercent.style.backgroundColor="";
	}
	
	if(form1.state.value == "insert"||form1.state.value=="insertError"){
		checkStr = (getNumeric(form1.deprShareAmount.value) + getNumeric(checkDeprShareAmount_All));
	}else{
		checkStr = (getNumeric(form1.deprShareAmount.value) + getNumeric(checkDeprShareAmount_All) - getNumeric('<%=obj.getDeprShareAmount()%>'));
	}
	if(form1.bookValue.value < checkStr){
		alertStr += "分攤總金額大於總價 "+ form1.bookValue.value +"，請確認分攤金額\n";
		form1.deprShareAmount.style.backgroundColor=errorbg;
	}
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}


function queryOne(propertyno,serialno,differencekind,enterorg,ownership,serialno1){
	form1.state.value="queryOne";
	form1.enterOrg.value=enterorg;
	form1.ownership.value=ownership;
	form1.differenceKind.value=differencekind;
	form1.propertyNo.value=propertyno;
	form1.serialNo.value=serialno;
	form1.serialNo1.value=serialno1;
	beforeSubmit();
	form1.submit();
	
}

function checkURL(surl){
	if (surl == "untdp011f01.jsp") {
		form1.currentPage.value = form1.mainPage.value;
		form1.serialNo1.value = form1.adjustserialNo1.value;
	} 
	
	form1.action = surl;
	beforeSubmit();
	form1.submit();
}

function init(){
	setDisplayItem("spanQueryAll","H");	
}

function calDeprPercent(){	
	var amount = form1.deprShareAmount.value;
	var bv = form1.bookValue.value;
	if(!isRealNaN(amount)){
		form1.deprPercent.value = (100 * amount / bv).toFixed(2);
	}
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="serialNo1" value="<%=obj.getSerialNo1()%>">	
	<input type="hidden" name="adjustserialNo1" value="<%=Common.get(request.getParameter("adjustserialNo1")) %>" >
	<input type="hidden" name="bookValue" value="<%=obj.getBookValue()%>">	
	
	<input type="hidden" name="q_enterOrg" value="<%=Common.get(request.getParameter("q_enterOrg")) %>" >
	<input type="hidden" name="q_differenceKind" value="<%=Common.get(request.getParameter("q_differenceKind")) %>" >
	<input type="hidden" name="q_propertyNoS" value="<%=Common.get(request.getParameter("q_propertyNoS")) %>" >
	<input type="hidden" name="q_propertyNoE" value="<%=Common.get(request.getParameter("q_propertyNoE")) %>" >
	<input type="hidden" name="q_serialNoS" value="<%=Common.get(request.getParameter("q_serialNoS")) %>" >
	<input type="hidden" name="q_serialNoE" value="<%=Common.get(request.getParameter("q_serialNoE")) %>" >
	<input type="hidden" name="q_propertyNoName1" value="<%=Common.get(request.getParameter("q_propertyNoName1")) %>" >
</td></tr></table>

 
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2"><a href="#" onClick="return checkURL('untdp011f01.jsp');">基本資料</a></td>
		<td ID=t2 CLASS="tab_border1" HEIGHT="25">折舊比例</td>
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
			<td class="td_form">入帳機關：</td>
			<td class="td_form_white" colspan="3">
			 	<%=util.View.getPopOrgan("field_Q","enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
				&nbsp;&nbsp;&nbsp;權屬：<select class="field" type="select" name="ownership"><%=util.View.getOnwerOption(obj.getOwnership())%></select>
			</td>
		</tr>
		<tr>
			<td class="td_form">財產區分別：</td>
			<td colspan="3" class="td_form_white">
				<select class="field_P" type="select" name="differenceKind">
				　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getDifferenceKind()) %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td_form">財產編號	：</td>
			<td class="td_form_white" colspan="3">
				<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"")%> 
				&nbsp;&nbsp;&nbsp;分號：
				[<input class="field" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
			</td>
		</tr>
		<tr>
			<td class="td_form">園區別：</td>
			<td class="td_form_white" colspan="3">
				<select class="field" type="select" name="deprPark">
				　　		<%=util.View.getOption("select deprparkno, deprparkno+'-'+deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
				</select>				
				&nbsp;&nbsp;&nbsp;
				部門別：
				<select class="field" type="select" name="deprUnit" onchange="queryDeprUnitData();">
				　　<%=util.View.getOption("select deprunitno, deprunitno+'-'+deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ",obj.getDeprUnit())%>
				</select>				
				&nbsp;&nbsp;&nbsp;
				部門別單位：
				<select class="field" type="select" name="deprUnit1">
					<%=util.View.getOption("select deprunit1no, deprunit1no+'-'+deprunit1name from SYSCA_DEPRUNIT1 where enterorg='" + obj.getEnterOrg() + "' order by deprunit1no", obj.getDeprUnit1())%>
			    </select>
				&nbsp;&nbsp;&nbsp;
				會計科目：
				<select class="field" type="select" name="deprAccounts">
				　　<%=util.View.getOption("select depraccountsno, depraccountsno+'-'+depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ",obj.getDeprAccounts())%>
				</select>				
			</td>
		</tr>
		<tr>
			<td class="td_form">分攤金額：</td>
			<td class="td_form_white" colspan="3">
				<input type="text" class="field" name="deprShareAmount" value="<%= obj.getDeprShareAmount() %>" size="20" maxlength="15" onchange="calDeprPercent();">
				&nbsp;&nbsp;&nbsp;
				歷史資料：
				<select class="field" type="select" name="isHistory">
				　　<%=util.View.getTextOption("Y;是;N;否;",obj.getIsHistory())%>
				</select>
				&nbsp;&nbsp;&nbsp;
				分攤百分比(%)：
				[<input type="text" class="field_RO" name="deprPercent" value="<%= obj.getDeprPercent() %>" size="20" >]
			</td>
		</tr>	
		
		
		
		<tr>
			<td class="td_form">備註：</td>
			<td class="td_form_white"><textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>      </td>
			<td class="td_form">異動人員/日期：</td>
			<td class="td_form_white">[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
			/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
			<input type="hidden" name="deprPark_h" value="<%=obj.getDeprPark()%>">
			<input type="hidden" name="deprUnit_h" value="<%=obj.getDeprUnit()%>">	
			<input type="hidden" name="deprUnit1_h" value="<%=obj.getDeprUnit1()%>">
			<input type="hidden" name="deprAccounts_h" value="<%=obj.getDeprAccounts()%>">
		</td>
		</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">		
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">園區別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">部門別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">部門別單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">會計科目</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">分攤百分比(％)</a></th>		
	</thead>
	<tbody id="listTBODY">
	<%
		boolean primaryArray[] = {true,true,false,false,false,false,false,false,false,false,true,true,true,true};
		boolean displayArray[] = {true,true,false,true,false,true,true,false,true,true,false,false,false,false};
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



