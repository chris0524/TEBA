<!--
程式目的：地政機關土地資料查詢
程式代號：sysot005r
程式日期：0980907
程式作者：jerry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.lc.SYSLC002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.lc.SYSLC002F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("deleteBatch".equals(obj.getState())) {
	obj.deleteBatch();
}else if("exportAll".equals(obj.getState())){
    obj.exportAll();
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
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(tranYYYMM,lsignNo1,lsignNo2,lsignNo3,lsignNo4,ownershipNo,managerID){
	form1.tranYYYMM.value=tranYYYMM;
	form1.lsignNo1.value=lsignNo1;
	form1.lsignNo2.value=lsignNo2;
	form1.lsignNo3.value=lsignNo3;
	form1.lsignNo4.value=lsignNo4;
	form1.ownershipNo.value=ownershipNo;
	form1.managerID.value=managerID;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}
function init() {
	//隱藏不必要的功能按鈕
	document.all.item("spanInsert").style.display = 'none';
	document.all.item("spanUpdate").style.display = 'none';
	document.all.item("spanDelete").style.display = 'none';
	document.all.item("spanNextInsert").style.display = 'none';
	if (form1.state.value=="exportAll") {	
		exportPopup();
	}
}
function exportPopup(){
    var url = "../../downloadFileSimple?fileName=" + form1.excelFileName.value;
	moshe=window.open('','MyWindow','scrollbars=1, resizable=yes, status=no, toolbar=no,menubar=no,width=400,height=150');
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 資料輸出 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="0" marginwidth="0" marginheight="0">\n');
	moshe.document.write('<div align=center><br><h>資料輸出成功!</h><br><br></div>');    
	moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">地政機關土地資料查詢</a><br><br>');	
	moshe.document.write('</body></html>');	
	form1.state.value="queryAllSuccess";	
}

function show_window(windowName){
	var winObj=document.all.item(windowName);
	var objHeight= winObj.style.height;
	var objWidth= winObj.style.width;
	objHeight= objHeight.substring(0,objHeight.length-2);
	objWidth= objWidth.substring(0,objWidth.length-2);
	winObj.style.top=(document.body.clientHeight-Number(objHeight)-80)/2;
	winObj.style.left=(document.body.clientWidth-Number(objWidth))/2;	
	winObj.style.display="block";
	form1.state.value = "deleteBatch";
}
function hidden_window(windowName){
	var winObj=document.all.item(windowName);		
	winObj.style.display="none";
	form1.state.value = "init";
}

function clickExportAll() {
	var alertStr = "";
	alertStr += checkQuery();		
	if(form1.state.value == "queryAllSuccess" || form1.state.value == "exportAll") {	
		//...
	} else {
		alertStr += "請先執行查詢!\n";
	}
		
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} else {
			document.all.state.value='exportAll';	
			form1.submit();
	}	
}
</script>
</head>

<body topmargin="0" onLoad="init();whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--批次刪除============================================================-->
<div id="deleteContainer" style="position:absolute;width:250px;height:80px;display:none">
	<div class="queryTitle">批次刪除視窗</div>
	<table class="queryTable"  border="1">
		<tr>
			<td class="queryTDLable" >機關：</td>
	        <td class="queryTDInput">
		  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
		    </td>		
		</tr>
		<tr>
			<td class="queryTDLable" width="20%" height="5%">轉入年月：</td>
			<td class="queryTDInput" width="30%" height="5%">
				<select class="field_Q" type="select" name="q_deleteYYYMM">
				<%=util.View.getSingleOption("select distinct tranyyymm from sysot_land order by tranyyymm desc", obj.getQ_deleteYYYMM())%>
				</select>
			</td>
		</tr>
		<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="deleteSubmit" value="確　　定"/>
			<input class="toolbar_default" followPK="false" type="button" name="deleteCannel" value="取　　消" onClick="hidden_window('deleteContainer')" />
		</td>
	</tr>
	</table>
</div>
<!--Query區============================================================-->
<div id="queryContainer" style="width:650px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" >機關：</td>
        <td class="queryTDInput">
	  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>		
	</tr>
	<tr>
		<td class="queryTDLable" width="20%">轉入年月：</td>
		<td class="queryTDInput" width="30%">
			<select class="field_Q" type="select" name="q_tranYYYMM">
			<%=util.View.getSingleOption("select distinct tranyyymm from sysot_land order by tranyyymm desc", obj.getQ_tranYYYMM())%>
			</select>
		</td>
		<td class="queryTDLable">轉入縣市：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_countyNo" onchange="getOptionsBy('../../home/xmlOfficeNo.jsp','q_countyNo','q_officeNo','')">
			<%=util.View.getOption("select distinct countyNo,countyName,seqNo from SYSOT_Office order by seqNo", obj.getQ_countyNo())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" nowrap>轉入地政事務所：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_officeNo">
			<%=util.View.getOption("select officeNo,officeName from SYSOT_Office where countyNo='' order by officeNo", obj.getQ_officeNo())%>
			</select>
		</td>
		<td class="queryTDLable">轉入權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOption("select CODEID,CODENAME from SYSCA_CODE where CODEKINDID = 'OWA'", obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_lsignNo1" onChange="changeSignNo1('q_lsignNo1','q_lsignNo2','q_lsignNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_lsignNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_lsignNo2" onChange="changeSignNo2('q_lsignNo1','q_lsignNo2','q_lsignNo3','');">
				<script>changeSignNo1('q_lsignNo1','q_lsignNo2','q_lsignNo3','<%=obj.getQ_lsignNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_lsignNo3">
				<script>changeSignNo2('q_lsignNo1','q_lsignNo2','q_lsignNo3','<%=obj.getQ_lsignNo3()%>');</script>
			</select>
			&nbsp;地號：
			<input class="field_Q" type="text" name="q_lsignNo4" size="8" maxlength="8" value="<%=obj.getQ_lsignNo4()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">公告現值年度：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_priceYear">
			<%=util.View.getOption("select distinct priceYear,priceYear from sysot_land", obj.getQ_priceYear())%>
			</select>
		</td>
		<td class="queryTDLable">公告地價年度：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valueYear">
			<%=util.View.getOption("select distinct valueYear,valueYear from sysot_land", obj.getQ_valueYear())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">所有權人姓名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_ownershipName" size="10" value="<%=obj.getQ_ownershipName()%>">
		</td>
		<td class="queryTDLable">管理者姓名(土所)：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_managerName" size="10" value="<%=obj.getQ_managerName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">面積:</td>
		<td class="queryTDInput" colspan="3"> 
			<input class="field_Q" type="text" name="q_areaS" size="10" value="<%=obj.getQ_areaS()%>">
			至
			<input class="field_Q" type="text" name="q_areaE" size="10" value="<%=obj.getQ_areaE()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">使用分區：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_useSeparate">
				<%=util.View.getOption("select a.codeid,a.codename from sysca_code a where a.codekindid = 'SEP'",obj.getQ_useSeparate()) %>
			</select>
		</td>
		<td class="queryTDLable">使用地:</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_useKind">
				<%=util.View.getOption("select a.codeid,a.codename from sysca_code a where a.codekindid = 'UKD'",obj.getQ_useKind()) %>
			</select>
		</td>	
	</tr>
	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" >機關：</td>
        <td class="td_form_white">
	  	    <%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>		
	</tr>
	<tr>
		<td class="td_form" width="16%">轉入年月：</td>
		<td class="td_form_white" width="32%">
			[<input class="field_RO" type="text" name="tranYYYMM" size="10" value="<%=obj.getTranYYYMM()%>">]			
		</td>
		<td class="td_form">轉入縣市：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="countyNo" size="10" value="<%=obj.getCountyNo()%>">]			
		</td>
	</tr>
	<tr>
		<td class="td_form">轉入地政事務所：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="officeNo" size="10" value="<%=obj.getOfficeNo()%>">]
		</td>
		<td class="td_form">轉入權屬：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="ownership" size="10" value="<%=obj.getOwnership()%>">]
		</td>
	</tr>
	
<tr><td class="queryTDLable" style="text-align:center" colspan="4">土地標示部</td></tr>
	<tr>
		<td class="td_form">縣市：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="lsignNo1" size="1" value="<%=obj.getLsignNo1()%>">]
			<%=util.View.getLookupField("select distinct substr(SIGNDESC,0,3) from SYSCA_SIGN where substr(SIGNNO,0,1)='"+obj.getLsignNo1()+"'")%>
			
			<%="　　"%>鄉鎮市區：
			[<input class="field_RO" type="text" name="lsignNo2" size="2" value="<%=obj.getLsignNo2()%>">]
			<%=util.View.getLookupField("select DISTINCT substr(SIGNDESC,4,3) from SYSCA_SIGN where substr(SIGNNO,0,3)='"+obj.getLsignNo1()+obj.getLsignNo2()+"'")%>
			
			<%="　　"%>段(小段)：
			[<input class="field_RO" type="text" name="lsignNo3" size="4" value="<%=obj.getLsignNo3()%>">]
			<%=util.View.getLookupField("select SIGNNAME from SYSCA_SIGN where SIGNNO='"+obj.getLsignNo1()+obj.getLsignNo2()+obj.getLsignNo3()+"'")%>
			
			<%="　　"%>地號：
			[<input class="field_RO" type="text" name="lsignNo4" size="8" value="<%=obj.getLsignNo4()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">登記日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="signDateA" size="10" value="<%=obj.getSignDateA()%>">]
		</td>
		<td class="td_form">登記原因：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="signReasonA" size="10" value="<%=obj.getSignReasonA()%>">]
			<%=util.View.getLookupField("select CODENAME from SYSCA_CODE where CODEKINDID='OCC' and CODEID='"+ Common.esapi(obj.getSignReasonA()) +"'")%>
		</td>
	</tr>
	<tr>
		<td class="td_form">地目：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="field" size="10" value="<%=obj.getField()%>">]
			<%=util.View.getLookupField("select CODENAME from SYSCA_CODE where CODEKINDID='FIE' and CODEID='"+ Common.esapi(obj.getField()) +"'")%>
		</td>
		<td class="td_form">等則：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="landRule" size="10" value="<%=obj.getLandRule()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">面積：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="area" size="10" value="<%=obj.getArea()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">使用分區：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="useSeparate" size="10" value="<%=obj.getUseSeparate()%>">]
			<%=util.View.getLookupField("select CODENAME from SYSCA_CODE where CODEKINDID='SEP' and CODEID='"+ Common.esapi(obj.getUseSeparate()) +"'")%>
		</td>
		<td class="td_form">使用地：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="useKind" size="10" value="<%=obj.getUseKind()%>">]
			<%=util.View.getLookupField("select CODENAME from SYSCA_CODE where CODEKINDID='UKD' and CODEID='"+ Common.esapi(obj.getUseKind()) +"'")%>
		</td>		
	</tr>	
	<tr>
		<td class="td_form">公告現值年度：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="priceYear" size="10" value="<%=obj.getPriceYear()%>">]
		</td>
		<td class="td_form">公告現值：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="priceUnit" size="10" value="<%=obj.getPriceUnit()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">公告地價年度：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="valueYear" size="10" value="<%=obj.getValueYear()%>">]
		</td>	
		<td class="td_form">公告地價：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="valueUnit" size="10" value="<%=obj.getValueUnit()%>">]
		</td>
	</tr>
	
<tr><td class="queryTDLable" style="text-align:center" colspan="4">土地所有權部</td></tr>
	<tr>
		<td class="td_form">登記次序：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="ownershipNo" size="10" value="<%=obj.getOwnershipNo()%>">]
		</td>
		<td class="td_form">登記日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="signDateB" size="10" value="<%=obj.getSignDateB()%>">]
		</td>		
	</tr>
	<tr>
		<td class="td_form">登記原因：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="signReasonB" size="10" value="<%=obj.getSignReasonB()%>">]
			<%=util.View.getLookupField("select CODENAME from SYSCA_CODE where CODEKINDID='OCC' and CODEID='"+ Common.esapi(obj.getSignReasonB()) +"'")%>
		</td>
		<td class="td_form">原因發生日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="signDateB1" size="10" value="<%=obj.getSignDateB1()%>">]
		</td>		
	</tr>
	<tr>
		<td class="td_form">權利範圍類別：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="holdType" size="10" value="<%=obj.getHoldType()%>">]
			<%=util.View.getLookupField("select CODENAME from SYSCA_CODE where CODEKINDID='HTA' and CODEID='"+ Common.esapi(obj.getHoldType()) +"'")%>
			<%="　　　　　"%>
		分母：
			[<input class="field_RO" type="text" name="holdDeno" size="10" value="<%=obj.getHoldDeno()%>">]		
		分子：
			[<input class="field_RO" type="text" name="holdNume" size="10" value="<%=obj.getHoldNume()%>">]		
		</td>
	</tr>	
	<tr>
		<td class="td_form">權狀年期字號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="proof" size="10" value="<%=obj.getProof()%>">]
		</td>
	</tr>	
	<tr>
		<td class="td_form">申報地價年月：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="declareValueYM" size="10" value="<%=obj.getDeclareValueYM()%>">]
		</td>	
		<td class="td_form">申報地價：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="declareValue" size="10" value="<%=obj.getDeclareValue()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">所有權統一編號：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="ownershipID" size="10" value="<%=obj.getOwnershipID()%>">]
		</td>	
		<td class="td_form">所有權人姓名：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="ownershipName" size="10" value="<%=obj.getOwnershipName()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">所有權住址：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="ownershipAddr" size="10" value="<%=obj.getOwnershipAddr()%>">]
		</td>
	</tr>
<tr><td class="queryTDLable" style="text-align:center" colspan="4">土地所有權管理者</td></tr>		
	<tr>
		<td class="td_form">管理者統一編號(土所)：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="managerID" size="10" value="<%=obj.getManagerID()%>">]
		</td>
		<td class="td_form">管理者姓名(土所)：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="managerName" size="24" value="<%=obj.getManagerName()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">管理者住址(土所)：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="managerAddr" size="24" value="<%=obj.getManagerAddr()%>">]
		</td>
	</tr>
	<tr style="display:none">
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white" colspan="3">
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
	<input type="hidden" name="exportPath" value="<%=application.getRealPath("/exportFolder")%>">	
	<input type="hidden" name="excelFileName" value="<%=obj.getExcelFileName()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="span_delete_batch">
	<input class="toolbar_default" type="button" followPK="false" name="btn_delete_batch" value="批次刪除" onClick="show_window('deleteContainer')">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="exportAll" value="輸出Excel檔"  onClick="clickExportAll();">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">轉入年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">縣市</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">鄉鎮市區</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">段（小段）</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">地號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">所有權人姓名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">管理者姓名(土所)</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,true,false,true,true,false,true,false};
	boolean displayArray[] = {true,false,true,false,true,false,true,true,false,true,false,true};
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



