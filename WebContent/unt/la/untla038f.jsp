<!--
程式目的：土地合併分割作業－分割增加單管理資料
程式代號：untla038f
程式日期：0941005
程式作者：carey
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA038F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA038F)obj.queryOne();	
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
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/unitProperty.js"></script>
<script language="javascript" src="untla027q.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		alertStr += checkEmpty(form1.useRelation,"使用關係");
		alertStr += checkEmpty(form1.signNo1,"土地標示代碼－縣市");
		alertStr += checkEmpty(form1.signNo2,"土地標示代碼－鄉鎮市區");
		alertStr += checkEmpty(form1.signNo3,"土地標示代碼－地段");
		alertStr += checkEmpty(form1.signNo4,"土地標示代碼－地號(母號)");
		alertStr += checkEmpty(form1.signNo5,"土地標示代碼－地號(子號)");
		alertStr += checkLen(form1.notes,"備註",250);
//		if(form1.useUnit.value=="" && AllTrim(form1.useUnit1).length==0)
//			alertStr += "使用單位與非機關使用單位請擇一輸入！";
//		else if(form1.useUnit.value != "" && AllTrim(form1.useUnit1).length>0)
//			alertStr += "使用單位與非機關使用單位只能輸入一個！";
		if(AllTrim(form1.useDateS).length>0 && AllTrim(form1.useDateE).length>0){
			if(form1.useDateS.value >= form1.useDateE.value)
				alertStr += "使用期間－起不能大於等於使用期間－訖！";
		}
		alertStr += checkDate(form1.useDateS,"使用期間－起");
		alertStr += checkDate(form1.useDateE,"使用期間－訖");
		alertStr += checkEmpty(form1.useArea,"使用面積(㎡)");
		alertStr += checkFloat(form1.useArea,"使用面積(㎡)",9,2);

	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function showPerson() {

	var alertStr = "";	

	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	alertStr += checkEmpty(form1.serialNo,"財產分號");
	alertStr += checkEmpty(form1.serialNo1,"財產序號");
	if(alertStr.length!=0){ 
		alert("請先執行查詢!\n");
		return false; 
	} else {
		var sUrl = "../la/untla049f.jsp?popEnterOrg="+form1.enterOrg.value+
			"&popOwnership="+form1.ownership.value+
			"&popPropertyNo="+form1.propertyNo.value+
			"&popSerialNo="+form1.serialNo.value+
			"&popSerialNo1="+form1.serialNo1.value;
		
		window.open(sUrl,'MyWindow','scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,width=850,height=450');
	}
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1){

	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function calDisAmount(rStr) {
	var disTot = 0;	
	var arrType;
	var limitValue;
	if (form1.disType.value.length>0) {
		arrType = form1.disType.value.split(",");
		form1.disTypeDummy.value = arrType[0];		
		if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError") {		
			//if (arrType[0]=="01") form1.disArea1.value = parseFloat("0"+form1.dimArea.value); //parseFloat(("0"+parseComma(form1.divideArea.value)))-parseFloat("0"+form1.dimArea.value);
		}
		form1.disValue1.value = arrType[1];
		form1.disValue2.value = arrType[2];
		limitValue = parseFloat("0"+arrType[3]);		
		if (parseFloat(arrType[2])<=0) {
			form1.disArea2.value = "0";
			setFormItem("disArea2","R");
		}
		if (limitValue>0) {
			if (parseFloat("0"+form1.disArea1.value)>limitValue) {				
				if (rStr == "Y" ) {
					return "優待面積1不能大於 " + limitValue + "請重新輸入!"; 
				} else {
					alert("優待面積1不能大於 " + limitValue + "請重新輸入!");
					return false;
				}
			}
		}
		form1.disTotal.value = roundp(((parseFloat("0"+form1.disArea1.value)*parseFloat("0"+form1.disValue1.value)+parseFloat("0"+form1.disArea2.value)*parseFloat("0"+form1.disValue2.value))*parseFloat("0"+form1.valueUnit.value))/2,'2','Y');		
		return "";		
	}
}

function init(){



	document.all.item("spanQueryAll").style.display="none";
	if ("<%=obj.getMergeDivisionVerify()%>"=="Y") {
		setFormItem("insert,update,delete", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete","R");
	}	
	//if ('<%=user.getOrganID()%>'=='<%=application.getInitParameter("ManageOrgID")%>' && form1.propertyKind.value=='04') {		
		//setFormItem("insert,update,delete","R");
	//}	
	//getUNTLA_Value();
	//calDisAmount();	
}

function changeUseRelation(){
	if(form1.useRelation.value=='04'){
		form1.useUnit.value="<%=user.getOrganID()%>";
		form1.useUnitName.value="<%=user.getOrganName()%>";
	}
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untla027f.jsp');">案件資料</a></td>
<!-- 		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla028f.jsp');">合併<br>減損單</a></td>		
		<td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla029f.jsp');">合併<br>減損單明細</a></td>
		<td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla030f.jsp');">合併<br>增加單</a></td>
		<td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla031f.jsp');">合併<br>增加單明細</a></td>
		<td ID=t6 CLASS="tab_border2">合併增加單<br>管理資料</td>
 -->		
		<td ID=t7 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla034f.jsp');">分割<br>減損單</a></td>
		<td ID=t8 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla035f.jsp');">分割<br>減損單明細</a></td>
		<td ID=t9 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla036f.jsp');">分割<br>增加單</a></td>
		<td ID=t10 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla037f.jsp');">分割<br>增加單明細</a></td>
		<td ID=t11 CLASS="tab_border1">分割增加單<br>管理資料</td>
	</tr>
	<tr>
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
		<td class="tab_line1"></td>		
	</tr>
</TABLE>
<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="caseName" value="<%=obj.getCaseName()%>">
<input class="field_QRO" type="hidden" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
<input class="field_QRO" type="hidden" name="mergeReduce" value="<%=obj.getMergeReduce()%>">
<input class="field_QRO" type="hidden" name="mergeAdd" value="<%=obj.getMergeAdd()%>">
<input class="field_QRO" type="hidden" name="divisionReduce" value="<%=obj.getDivisionReduce()%>">
<input class="field_QRO" type="hidden" name="divisionAdd" value="<%=obj.getDivisionAdd()%>">
<input class="field_QRO" type="hidden" name="mergeDivisionDate" value="<%=obj.getMergeDivisionDate()%>">
<input class="field_QRO" type="hidden" name="cause" value="<%=obj.getCause()%>">
<input class="field_QRO" type="hidden" name="cause1" value="<%=obj.getCause1()%>">
<input class="field_QRO" type="hidden" name="approveOrg" value="<%=obj.getApproveOrg()%>">
<input class="field_QRO" type="hidden" name="approveDate" value="<%=obj.getApproveDate()%>">
<input class="field_QRO" type="hidden" name="approveDoc" value="<%=obj.getApproveDoc()%>">
<input class="field_QRO" type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" ><!--入帳機關-->
<input class="field_QRO" type="hidden" name="enterOrgName" value="<%=obj.getEnterOrgName()%>" ><!--入帳機關名稱-->
<input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>"><!--權屬-->
<input class="field_QRO" type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>"><!--財產編號-->	
<input class="field_QRO" type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>"><!--分號-->			
<input class="field_QRO" type="hidden" name="caseNo" value="<%=obj.getCaseNo()%>">
<input class="field_QRO" type="hidden" name="mergeDivisionVerify" value="<%=obj.getMergeDivisionVerify()%>">
<input class="field_QRO" type="hidden" name="verify" value="<%=obj.getMergeDivisionVerify()%>">
<input class="field_QRO" type="hidden" name="reduceCaseNo" value="<%=obj.getReduceCaseNo()%>">
<input class="field_QRO" type="hidden" name="addCaseNo" value="<%=obj.getAddCaseNo()%>">
<input class="field_QRO" type="hidden" name="reduceCaseNo1" value="<%=obj.getReduceCaseNo1()%>">
<input class="field_QRO" type="hidden" name="addCaseNo1" value="<%=obj.getAddCaseNo1()%>">
<input class="field_QRO" type="hidden" name="enterDate" value="<%=obj.getEnterDate()%>">
<input class="field_QRO" type="hidden" name="propertyKind" value="<%=obj.getPropertyKind()%>">
<input type="hidden" name="serialNo_old" value="<%=obj.getSerialNo_old()%>">
<input type="hidden" name="serialNo1_old" value="<%=obj.getSerialNo1_old()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
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
		<td class="td_form" colspan="4" style="text-align:center;">
			<input class="toolbar_default" type="button" name="btn_mergeReduce" onclick="popUntlaReduceDetail1(form1.enterOrg.value,form1.ownership.value,form1.reduceCaseNo1.value,form1.mergeDivisionDate.value)" value="分割減損單明細挑選" title="土地減損單明細管理資料輔助視窗" disabled=true>
			<input class="toolbar_default" type="button" name="btn_showPerson" onclick=showPerson(); value="新增分割異動使用人" >
		</td>
	</tr>
	<tr>
		<td class="td_form">管理次序：</td>
		<td class="td_form_white">
			[<input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]
		</td>
		<td class="td_form"><font color="red">*</font>使用關係：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="useRelation" onChange="changeUseRelation();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='URE' ORDER BY CODEID", obj.getUseRelation())%>
			</select>
		</td>
	</tr>
<!-- 	<tr>
		<td class="td_form">使用單位：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopOrgan("field","useUnit",obj.getUseUnit(),obj.getUseUnitName(),"Y")%>
			&nbsp;　非機關：<input class="field" type="text" name="useUnit1" size="15" maxlength="15" value="<%=obj.getUseUnit1()%>">
		</td>
	</tr>
 -->	
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_P" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>
			<select class="field_P" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
			</select>		
			<select class="field_P" type="select" name="signNo3">
				<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
			</select>	
			　地號：	
			<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>">
			&nbsp;-
			<input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>">
		</td>	
	</tr>
	
	<tr>
		<td class="td_form">使用期間：</td>
		<td class="td_form_white" colspan="3">
			起：<%=util.View.getPopCalndar("field","useDateS",obj.getUseDateS())%>
			&nbsp;&nbsp;　訖：<%=util.View.getPopCalndar("field","useDateE",obj.getUseDateE())%>	
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>使用面積(㎡)：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="useArea" size="10" maxlength="10" value="<%=obj.getUseArea()%>">
			&nbsp;　　
					
		</td>
	</tr>
	
	<tr>
		<td class="td_form">其他事項：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="notes1" size="20" maxlength="20" value="<%=obj.getNotes1()%>">
		</td>			
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" type="text" name="notes" cols="25" rows="4"><%=obj.getNotes()%></textarea>
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	
 
	<input type="hidden" name="caseKind" value="<%=obj.getCaseKind()%>">
	<input type="hidden" name="holdDateS" value="<%=obj.getHoldDateS()%>">
	<input type="hidden" name="holdDateE" value="<%=obj.getHoldDateE()%>">
	<input type="hidden" name="userfeeDateS" value="<%=obj.getUserfeeDateS()%>">
	<input type="hidden" name="userfeeDateE" value="<%=obj.getUserfeeDateE()%>">
	<input type="hidden" name="dimArea" value="<%=obj.getDimArea()%>">
	<input type="hidden" name="disType" value="<%=obj.getDisType()%>">
	<input type="hidden" name="disArea1" value="<%=obj.getDisArea1()%>">
	<input type="hidden" name="disArea2" value="<%=obj.getDisArea2()%>">
	<input type="hidden" name="disDateS" value="<%=obj.getDisDateS()%>">
	<input type="hidden" name="disDateE" value="<%=obj.getDisDateE()%>">	
	<input type="hidden" name="useUnit" value="<%=obj.getUseUnit()%>">

		
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">管理次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示</a></th>
		
<!-- 	<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">使用單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">非機關使用單位</a></th>
 -->		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">使用關係</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">使用期間－起</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">使用期間－訖</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">使用面積(㎡)</a></th>
		<!-- <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">案號</a></th> -->
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,true,true,false,true,true,true,true,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");
	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			form1.useUnit.value="";
			setFormItem("btn_mergeReduce","O");
			setFormItem("btn_showPerson","R");
			break;
		case "insertError":
			form1.useUnit.value="";
			setFormItem("btn_mergeReduce","O");
			setFormItem("btn_showPerson","R");
			break;
			
		//取消時要執行的動作
		case "clear":
			setFormItem("btn_mergeReduce","R");
			setFormItem("btn_showPerson","R");
			break;
		case "clearError":
			setFormItem("btn_mergeReduce","R");
			setFormItem("btn_showPerson","R");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("btn_mergeReduce","R");
			setFormItem("btn_showPerson","R");
			break;
		case "confirmError":
			setFormItem("btn_mergeReduce","R");
			setFormItem("btn_showPerson","R");
			break;

	}
}
</script>
</body>
</html>



