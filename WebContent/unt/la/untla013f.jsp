<!--
程式目的：土地減少作業－減損單明細
程式代號：untla013f
程式日期：0940826
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA013F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA013F)obj.queryOne();	
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

unt.ch.UNTCH001_tab uch = new unt.ch.UNTCH001_tab();
String tabs = uch._createTabData(uch._LA_REDUCE, 3);
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
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>")	
);



//當增加原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.cause.value=="499"){
		form1.cause1.style.backgroundColor=editbg;
		form1.cause1.readOnly = false;
	}else{
		form1.cause1.value="";
		form1.cause1.style.backgroundColor=readbg;
		form1.cause1.readOnly = true;
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untla012f.jsp";
		} else {
			form1.action = "untla013f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		alertStr += checkLen(form1.notes, "備註", 250);
		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			form1.signNo1.style.backgroundColor=errorbg;
			form1.signNo2.style.backgroundColor=errorbg;
			form1.signNo3.style.backgroundColor=errorbg;
			form1.signNo4.style.backgroundColor=errorbg;
			form1.signNo5.style.backgroundColor=errorbg;
			alertStr += "土地標示代碼不得為空白!\n";
		} 			
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
		
		alertStr += checkEmpty(form1.cause,"減損原因");
		if (form1.cause.value=="499"){
			alertStr += checkEmpty(form1.cause1,"減損原因-其他說明");
		}
		
		if ((form1.cause.value=="201")||(form1.cause.value=="203")||(form1.cause.value=="206")){
			alertStr += checkEmpty(form1.newEnterOrg,"接管機關");
			alertStr += checkEmpty(form1.transferDate,"交接日期");
		}
		
		alertStr += checkDate(form1.transferDate,"交接日期");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untla012f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {		
				alertStr += checkEmpty(form1.propertyNo,"財產編號");
				alertStr += checkEmpty(form1.serialNo,"財產分號");			
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"財產編號");
			alertStr += checkEmpty(form1.serialNo,"財產分號");			
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.caseNo,"電腦單號");
		if(alertStr.length!=0){ 
			alert("請先執行查詢, 若已查到資料則請選取其中一筆資料"); 
			return false;
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function gountla014f(){
	var prop="";
	var iwidth=screen.availWidth;
	var iheight=screen.availHeight;
	var windowTop=0;
	var windowLeft=0;	
	prop=prop+"width="+iwidth+",";
	prop=prop+"height="+iheight+",";
	//var windowTop=(document.body.clientHeight-80)/2+25;
	//var windowLeft=(document.body.clientWidth-800)/2+250;
	//prop=prop+"width=775,height=475,";
	
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	var enterOrg = form1.enterOrg.value;
	var ownership = form1.ownership.value;
	var caseNo = form1.caseNo.value;
	var reduceDate = form1.reduceDate.value;
	var verify = form1.verify.value;
	beforeSubmit();
	returnWindow=window.open("untla014f.jsp?q_enterOrg="+enterOrg+"&q_ownership="+ownership+"&q_caseNo="+caseNo+"&q_reduceDate="+reduceDate+"&q_verify="+verify,"aha",prop);	
}

function init(){
	var arrField = new Array("update","delete");
	//欲新增、修改減損單資料須至「土地合併分割作業」進行
	if(form1.mergeDivision.value!=""){
		setFormItem("insert,update", "R");
	}
	//=========================================
	if (form1.verify.value=="Y") {
		setFormItem("insert,update,delete,batchInsertBut", "R");
	}	
	if (document.all.querySelect[1].checked && form1.propertyNo.value=="" || form1.verify.value=="Y") {
		setFormItem("batchInsertBut","R");
	}else{
		setFormItem("batchInsertBut","O");
	}
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormItem("insert,update,delete,batchInsertBut","R");
	}
}

function checkValue(){
	var checkCaseNo = form1.caseNo.value;
	var checkPropertyNo = form1.propertyNo.value;
	var checkSerialNo = form1.serialNo.value;
	var checkSignNo1 = form1.signNo1.value;
	var checkSignNo2 = form1.signNo2.value;
	var checkSignNo3 = form1.signNo3.value;
	var checkSignNo4 = form1.signNo4.value;
	var checkSignNo5 = form1.signNo5.value;
if(checkPropertyNo.length>0 && checkSerialNo.length>0 && checkSignNo1.length>0 && checkSignNo2.length>0 && checkSignNo3.length>0 && checkSignNo4.length>0 && checkSignNo5.length>0){

}else if(checkPropertyNo.length>0 || checkSerialNo.length>0){
	setFormItem("signNo1,signNo2,signNo3,signNo4,signNo5","R");
}else if(checkSignNo1.length>0 || checkSignNo2.length>0 || checkSignNo3.length>0 || checkSignNo4.length>0 || checkSignNo5.length>0){
	setFormItem("propertyNo,serialNo","R");
}

	if(form1.check.value=="" && checkCaseNo.length!=0 && checkPropertyNo.length!=0 && checkSerialNo.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.propertyNo.value="";
		form1.propertyNoName.value="";
		form1.serialNo.value="";
		form1.bulletinDate.value="";
		form1.valueUnit.value="";
	}
	if(form1.check.value=="" && checkSignNo1.length!=0 && checkSignNo2.length!=0 && checkSignNo3.length!=0 && checkSignNo4.length!=0 && checkSignNo5.length!=0){
		alert("資料不存在，請重新輸入!!");
		form1.signNo4.value="";
		form1.signNo5.value="";
		form1.bulletinDate.value="";
		form1.valueUnit.value="";
	}
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input class="field_QRO" type="HIDDEN" name="getToday" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="check" value="">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<%=tabs %>
</TABLE>
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA012Q",obj);%>
	<jsp:include page="untla012q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg"> 
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="14%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
		 	<input class="field_QRO" type="HIDDEN" name="originalDate" size="10" maxlength="10" value="<%=obj.getOriginalDate()%>">
		 	<input class="field_QRO" type="HIDDEN" name="originalUnit" size="10" maxlength="10" value="<%=obj.getOriginalUnit()%>">
			<input class="field_QRO" type="HIDDEN" name="originalBasis" size="10" maxlength="10" value="<%=obj.getOriginalBasis()%>">
			<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			<input class="field_QRO" type="HIDDEN" name="mergeDivision" value="<%=obj.getMergeDivision()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
		　　&nbsp;&nbsp;&nbsp;&nbsp;權屬：
			<input class="field_QRO" type="hidden" name="ownershipQuery" value="<%=obj.getOwnership()%>">
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		　　&nbsp;&nbsp;電腦單號：
				[<input type="text" name="caseNo" class="field_QRO" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>財產編號：</td>
		<td class="td_form_white" colspan="3">
	  		<input class="field_P" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>"  onChange="getProperty('propertyNo','propertyNoName','1');getPropertyNo('PN');getValue();checkValue();"> 
	  		<input class="field_P" type="hidden" name="btn_propertyNo" onclick="popProperty('propertyNo','propertyName','1')" value="..." title="財產編號輔助視窗">
	 		[<input class="field_PRO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=obj.getPropertyNoName()%>">]
	   		　分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onChange="getPropertyNo('PN');getValue();checkValue();">
	   	<br>
		別名：
	   		[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
		　原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="7" maxlength="11" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
	  <td class="td_form"><font color="red">*</font>土地標示：</td>
	  <td class="td_form_white" colspan="3">
	  	<select  type="select" class="field_P" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getPropertyNo('SN');getValue();checkValue();">
        	<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
      	</select>
        <select  type="select" class="field_P" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getPropertyNo('SN');getValue();checkValue();">
          <script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
        </select>
        <select  type="select" class="field_P" name="signNo3">
          <script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
        </select>
		　地號：
		<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onChange="getPropertyNo('SN');getValue();checkValue();">
		&nbsp;-
		<input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onChange="getPropertyNo('SN');getValue();checkValue();">
	  </td>
	</tr>
	<tr>
		<td class="td_form">減損：</td>
		<td class="td_form_white" colspan = "3">
			日期：[<input type="text" name="reduceDate" class="field_QRO"  size="7" maxlength="7" value="<%=obj.getReduceDate()%>">]
			&nbsp;&nbsp;  入帳：
			<select class="field_QRO" type="select" name="verify">
				<%=util.View.getYNOption(obj.getVerify())%>
			</select>
			&nbsp;&nbsp;&nbsp;  月結：
			<select class="field_QRO" type="select" name="closing">
				<%=util.View.getYNOption(obj.getClosing())%>
			</select>
		<br>	
			<font color="red">*</font>減損原因：
				<select class="field" type="select" name="cause" onchange="changecause()">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' and codecon1 in ('2','3','4') ", obj.getCause())%>
				</select>
				&nbsp;　其他說明：<input class="field" type="text" name="cause1" size="20" maxlength="20" value="<%=obj.getCause1()%>">
		<br>
			接管機關：<%=util.View.getPopOrgan("field","newEnterOrg",obj.getNewEnterOrg(),obj.getNewEnterOrgName(),"Y")%>
			&nbsp;　交接日期：<%=util.View.getPopCalndar("field","transferDate",obj.getTransferDate())%>
		</td>
	</tr>
	<tr>
		<td class="td_form">財產性質：</td>
		<td class="td_form_white" colspan="3">	
			<select class="field_RO" type="select" name="propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ", obj.getPropertyKind())%>
			</select>
			　　　　　　　　基金財產：
			<select class="field_RO" type="select" name="fundType">
				<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundType','<%=obj.getFundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">珍貴財產：</td>
		<td class="td_form_white" colspan="3">		
			<select class="field_RO" type="select" name="valuable">
				<%=util.View.getYNOption(obj.getValuable())%>
			</select>
			　　　　　　　抵繳遺產稅：	
			<select class="field_RO" type="select" name="taxCredit">
				<%=util.View.getYNOption(obj.getTaxCredit())%>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="td_form">使用情形：</td>
		<td class="td_form_white" colspan="3">
		地目：
			<select class="field_RO" type="select" name="field">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getField())%>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　　　　
			地上物情形:
			<input type="hidden" name="useState1" size="10" maxlength="10" value="<%=obj.getUseState1()%>">
			[<input class ="field_RO" type="text" name="useState1Name" size="10" maxlength="10" value="<%=obj.getUseState1Name()%>">]			
		<br>
			取得日期：[<input type="text" name="sourceDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
			&nbsp;&nbsp;　　權狀字號：[<input class="field_RO" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">]
			
		<br>
			權利分子：[<input class="field_RO" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>">]
			&nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
		</td>
	</tr>		
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">		
			帳面單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="16" value="<%=obj.getBookUnit()%>">]
			&nbsp;&nbsp;　　　　　帳面總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		<br>
			帳務摘要：<input class="field" type="text" name="bookNotes" size="20" maxlength="20" value="<%=obj.getBookNotes()%>">
			&nbsp;&nbsp;　　會計科目：[<input class="field_RO" type="text" name="accountingTitle" size="10" maxlength="10" value="<%=obj.getAccountingTitle()%>">]
		<br>
			當期公告日期：[<input type="text" name="bulletinDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getBulletinDate()%>">]
			&nbsp;　　　　當期公告地價：[<input class="field_RO" type="text" name="valueUnit" size="13" maxlength="13" value="<%=obj.getValueUnit()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">使用分區：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_RO" type="select" name="useSeparate">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getUseSeparate())%>
			</select>
			　　　　　　編定使用種類：
			<select class="field_RO" type="select" name="useKind">
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
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
		<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="現有資料明細新增" onClick="gountla014f();">&nbsp;|
	</span>

</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">減損原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">權利範圍面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">帳面總價</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,true,false,false,true,true,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,false,true,false,false,false,true,true,false,true,true,false};
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
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			break;
		case "insertError":
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			break;
	
		//更新時要做的動作
		case "update":
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			break;
		case "updateError":
			setFormItem("ownership,verify,closing","R");
			setFormItem("batchInsertBut","R");
			break;
	
		//取消時要執行的動作
		case "clear":
			setFormItem("batchInsertBut","O");
			break;
		case "clearError":
			setFormItem("batchInsertBut","O");
			break;

		//確定時要執行的動作
		case "confirm":
			setFormItem("batchInsertBut","O");
			break;
		case "confirmError":
			setFormItem("batchInsertBut","O");
			break;
	}
}
</script>
</body>
</html>
