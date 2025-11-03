<!--
程式目的：權利增減值作業－增減值單明細資料
程式代號：untrt009f
程式日期：0941011
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT009F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.rt.UNTRT009F)obj.queryOne();	
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
String tabs = uch._createTabData(uch._RT_ADJUST, 2);
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
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	new Array("enterOrg","<%=obj.getEnterOrg()%>"),
	new Array("enterOrgName","<%=obj.getEnterOrgName()%>"),
	new Array("ownership","<%=obj.getOwnership()%>"),
	new Array("propertyNo","<%=obj.getPropertyNo()%>"),
	new Array("propertyNoName","<%=obj.getPropertyNoName()%>"),
	new Array("serialNo","<%=obj.getSerialNo()%>"),
	new Array("caseNo","<%=obj.getCaseNo()%>"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate","<%=util.Datetime.getYYYMMDD()%>")
);

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		//查詢時為增減值資料查詢或是增減值單資料明細查詢
		if (document.all.querySelect[0].checked) {
			form1.action = "untrt008f.jsp";
		} else {
			form1.action = "untrt009f.jsp";
		}

	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.serialNo1,"標的次序");
		alertStr += checkEmpty(form1.adjustType,"增減別");
		alertStr += checkEmpty(form1.adjustBookValue,"增減權利價值");
		alertStr += checkFloat(form1.area,"整筆面積(㎡)",9,2);
		alertStr += checkFloat(form1.holdNume,"權利範圍－分子",10,0);
		alertStr += checkFloat(form1.holdDeno,"權利範圍－分母",10,0);
		alertStr += checkFloat(form1.holdArea,"權利範圍面積(㎡)",9,2);
		alertStr += checkFloat(form1.setArea,"設定面積",9,2);
		alertStr += checkFloat(form1.oldBookValue,"原權利價值",15,0);
		alertStr += checkFloat(form1.adjustBookValue,"增減權利價值",15,0);
		alertStr += checkFloat(form1.newBookValue,"新權利價值",15,0);
		alertStr += checkLen(form1.notes, "備註", 250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	var alertStr = "";
	if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untrt008f.jsp"){	
			form1.state.value="queryOne"; 
			if (document.all.querySelect[1].checked) {	
				alertStr += checkEmpty(form1.propertyNo,"財產編號");
				alertStr += checkEmpty(form1.serialNo,"財產分號");
				alertStr += checkEmpty(form1.serialNo1,"標的次序");
			}
		} else {
			form1.state.value="queryAll";
			alertStr += checkEmpty(form1.propertyNo,"財產編號");
			alertStr += checkEmpty(form1.serialNo,"財產分號");
		}
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.serialNo,"財產分號");
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
	var serialNo = parse(form1.serialNo.value);
	var serialNo1 = parse(form1.serialNo1.value);
	var propertyNo = parse(form1.propertyNo.value);
	var ownership = parse(form1.ownership.value);
	var enterOrg = parse(form1.enterOrg.value);
	if ( <%=obj.getQuerySelect().equals("adjustDetail")%> || serialNo.length<=0 || propertyNo.length<=0 || ownership.length<=0 || enterOrg.length<=0){
		setFormItem("insert", "R");
		if(serialNo1.length>0){
			setFormItem("insert", "O");
		}
	}else{
		setFormItem("insert", "O");
	}

	<!-- 查出的「增減值單資料」若「已審核」，均不允許新增、修改、刪除該筆增減值單明細 -->
	if (<%=obj.getVerify().equals("Y")%>){
		setFormItem("insert,update,delete,clear,confirm", "R");	
	}
	<!-- 非登入者所屬機關所登錄的資料，均不允許新增、修改、刪除各標籤資料 -->
	if (form1.state.value=="queryOne" && form1.enterOrg.value!=<%="\""+user.getOrganID()+"\""%>) {		
		setFormItem("insert,update,delete,clear,confirm", "R");
	}
}

function clickAddDetail(){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-800)/2+250;
	prop=prop+"width=750,height=370,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	var enterOrg="<%=obj.getEnterOrg()%>";
	var enterOrgName="<%=obj.getEnterOrgName()%>";
	var ownership="<%=obj.getOwnership()%>";
	var propertyNo="<%=obj.getPropertyNo()%>";
	var propertyNoName="<%=obj.getPropertyNoName()%>";
	var serialNo="<%=obj.getSerialNo()%>";
	returnWindow=window.open("../../home/popUntrtAddDetail.jsp?enterOrg="+enterOrg+"&enterOrgName="+enterOrgName+"&ownership="+ownership+"&propertyNo="+propertyNo+"&propertyNoName="+propertyNoName+"&serialNo="+serialNo+"&js=changeSelect();","",prop);
}

function changeSelect(){
	var oldBookValue = parseInt(form1.oldBookValue.value);
	var adjustBookValue = parseInt(form1.adjustBookValue.value);
	//增減別為「1:增加」：「原帳面設定價值」＋「增減帳面設定價值」
	//增減別為「2:減少」：「原帳面設定價值」－「增減帳面設定價值」
	//增減別為「2:減少」時，必須＜「原帳面設定價值」
	if(AllTrim(form1.adjustType).length>0 && AllTrim(form1.adjustBookValue).length>0 && AllTrim(form1.oldBookValue).length>0){
		if (form1.adjustType.value=="1"){
			form1.newBookValue.value = oldBookValue + adjustBookValue;
		}else if(form1.adjustType.value=="2"){
			if(adjustBookValue>=oldBookValue){
				alert("增減帳面設定價值必須<原帳面設定價值!\n");
				document.all.adjustBookValue.focus();
				form1.adjustBookValue.style.backgroundColor=errorbg;
			}else{
				form1.newBookValue.value = oldBookValue - adjustBookValue;	
				form1.adjustBookValue.style.backgroundColor="";
			}
		}
		//增減帳面設定價值必須>0
		if(adjustBookValue<=0){
			alert("增減帳面設定價值必須>0!\n");
			document.all.adjustBookValue.focus();
			form1.adjustBookValue.style.backgroundColor=errorbg;
		}else{
			form1.adjustBookValue.style.backgroundColor="";
		}
	}else{
		form1.newBookValue.value="";
	}
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--Query區============================================================-->
<div id="queryContainer" style="width:760px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTRT008Q",obj);%>
	<jsp:include page="untrt008q.jsp" />
</div>
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
  <tr>
    <td ID=t1 CLASS="tab_border2" HEIGHT="25"><a href="#" onClick="return checkURL('untrt008f.jsp');">增減值單資料</a></td>
    <td ID=t2 CLASS="tab_border1">增減值單明細資料</td>
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
		<td class="td_form" width="14%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
			&nbsp;　　權屬：
			<select class="field_QRO" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
			&nbsp;　　電腦單號：[<input class="field_QRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_QRO" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>">]
			&nbsp;　分號：[<input class="field_QRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
			&nbsp;　名稱：[<input class="field_QRO" type="text" name="propertyNoName" size="25" maxlength="50" value="<%=obj.getPropertyNoName()%>">]			
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>標的次序：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_PRO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]
			<input class="field_P" type="button" name="queryAddDetail" value="..." onClick="clickAddDetail();">
			&nbsp;&nbsp;　　　原標的次序：
			[<input class="field_RO" type="text" name="oldSerialNo1" size="7" maxlength="7" value="<%=obj.getOldSerialNo1()%>">]
		</td>
	</tr>
	<tr>
		<!--土地標示代碼：-->
			<input class="field" type="hidden" name="signNo" size="15" maxlength="15" value="<%=obj.getSignNo()%>">
		<td class="td_form">土地名稱：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="signName" size="50" maxlength="50" value="<%=obj.getSignName()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="bookValue" size="15" maxlength="15" value="">
			原權利價值：[<input class="field_RO" type="text" name="oldBookValue" size="15" maxlength="15" value="<%=obj.getOldBookValue()%>">]
		<br>
			<font color="red">*</font>增減別：
			<select class="field" type="select" name="adjustType" onChange="changeSelect();">
			<%=util.View.getTextOption("1;增加;2;減少", obj.getAdjustType())%>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;　　　<font color="red">*</font>增減權利價值：<input class="field" type="text" name="adjustBookValue" size="15" maxlength="15" value="<%=obj.getAdjustBookValue()%>" onFocusOut="changeSelect();">
		<br>
			新權利價值：[<input class="field_RO" type="text" name="newBookValue" size="15" maxlength="15" value="<%=obj.getNewBookValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">範圍：</td>
		<td class="td_form_white" colspan="3">
			整筆面積(㎡)：[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">]
			&nbsp;　　　權利範圍：
			[<input class="field_RO" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>">
			/
			<input class="field_RO" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>">]
		<br>
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
			&nbsp;　設定面積：[<input class="field_RO" type="text" name="setArea" size="9" maxlength="9" value="<%=obj.getSetArea()%>">]
		<br>
			設定義務人：[<input class="field_RO" type="text" name="setObligor" size="50" maxlength="30" value="<%=obj.getSetObligor()%>">]
		</td>
	</tr>
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
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
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">標的次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">設定義務人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">原權利價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">增減權利價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">新權利價值</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true,false,false,false,false,false};
	boolean displayArray[] = {false,false,false,false,false,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</form>
<script language='javascript'>
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
/*			if (parse(form1.q_signNo1.value).length<=0) {
				form1.q_signNo1.value="E000000";
				changeSignNo1("q_signNo1","q_signNo2","q_signNo3","E000000");
			}
*/			
			break;			
	}
	return true;
}
</script>
</body>
</html>



