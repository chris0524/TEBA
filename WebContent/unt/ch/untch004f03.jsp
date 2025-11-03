 
<!--
程式目的：建物減少作業－現有資料明細新增明細資料
程式代號：untbu017f
程式日期：0940930
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page import="java.net.URLDecoder"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH004F03">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>

<%
String caseNo = Common.checkGet(request.getParameter("caseNo"));
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String differenceKind = Common.checkGet(request.getParameter("differenceKind"));
String cause = Common.checkGet(request.getParameter("cause"));
String cause1 = URLDecoder.decode(Common.checkGet(request.getParameter("cause1")),"UTF-8");
String dataSource = Common.checkGet(request.getParameter("dataSource"));
String mergedivision = Common.checkGet(request.getParameter("mergedivision"));

obj.setQ_caseNo(caseNo);
obj.setQ_enterOrg(enterOrg);
obj.setQ_ownership(ownership);
obj.setQ_differenceKind(differenceKind);
obj.setQ_cause(cause);
obj.setQ_cause1(cause1);
obj.setDataSource(dataSource);
obj.setMergedivision(mergedivision);

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}

if("insertSuccess".equals(obj.getState())){
	obj.setQ_cause("");
	obj.setQ_cause1("");
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}

if("214".equals(cause) || "208".equals(cause)){
	obj.setQ_cause2("逾最低年限，不堪使用，修復不具經濟價值");
}
%>

<html>
<head>
<title>減少作業現有資料明細新增輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js?v=1.1"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js?v=1.0"></script>
<script type="text/javascript" src="../../js/getUNTBUBuilding.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	
	if(form1.state.value=="queryAll"){
		
		if(alertStr.length!=0){ alert(alertStr); return false; }
		beforeSubmit();
		
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkInsertSelect();
		
		if('<%=cause%>'=="201" || '<%=cause%>'=="206" || '<%=cause%>'=="205"){
			alertStr += checkEmpty(form1.q_newEnterOrg,"接管機關");			
		}	
		if(alertStr.length!=0){ alert(alertStr); return false; }
		beforeSubmit();
		whatButtonFireEvent('insert');	
	}
	
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		
		if('<%=obj.getDataSource()%>' == 'untla056f'){
			opener.form1.state.value = "insertBatchSuccess";
			opener.form1.ownership.value = '<%=ownership%>';
			opener.form1.differenceKind.value = '<%=differenceKind%>';			
		}
		
		beforeSubmit();
		opener.form1.submit();
		if (isObj(window.aha)) window.aha.close();
	}

	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListHidden,spanListPrint,spanQueryAll", "H");
	   
	var dcc1 = new DataCouplingCtrlPlus(form1.organID, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.organID, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
	
	//問題單1897，修正所有的減損原因 [接管機關] 都應該要能選全部的機關，於此一併修正只有減損原因為撥出、交換、贈與，才能修改 [接管機關]
	if ('<%=cause%>'=="201" || '<%=cause%>'=="206" || '<%=cause%>'=="205"){
		document.getElementById("TR_q_newEnterOrg").style.display= "inline";
	}else {
		document.getElementById("TR_q_newEnterOrg").style.display= "none";
	}
	
}

function checkInsertSelect() {	
	var sFlag = false;
	var alertStr = "";
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "sKeySet" && e.checked==true) {
	    	sFlag = true;
			//問題單2108特殊車輛報廢時檢核
	    	var newArray = e.value.split(",");
	    	var useDate = form1.elements[i].parentNode.parentNode.children[15].innerHTML.split("年",1);
	    	alertStr += checkInsertProperty(useDate, newArray[3], newArray[4]); 	
	    }    
	}
	if(alertStr.length!=0){ 
		alert(alertStr);
	}
	if (sFlag) return "";
	else return "您尚未勾選任何資料，若無資料可供勾選，請重新查詢！\n";
}

function getCauseName(popCause,popCauseName){
	//傳送JSON
	var comment={};	
	comment.codeid = document.all.item("q_cause").value;
	comment.codecond1_1 = "2";
	comment.codecond1_2 = "4";
	
	$.post('queryCause.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			if(typeof(data['codename']) == 'undefined'){
				$("input[name='q_cause']").val('');
			}else{
				$("input[name='q_causeName']").val(data['codename']);	
			}
		});
}

function popSysca_Code(popID,popIDName,typeName,codekindid,condition){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	
	// popSysca_Code.jsp有解碼
	typeName = encodeURI(typeName);
	returnWindow=window.open("../../home/popSysca_Code.jsp?popID="+popID+"&popIDName="+popIDName+"&typeName="+typeName+"&codekindid="+codekindid+"&condition="+condition,"",prop);
}

function checkOverLimitYear(){
	if($("input[name='q_overLimitYear']").attr('checked') == 'checked'){
		$("input[name='q_overLimitYear']").val('Y');
	}else{
		$("input[name='q_overLimitYear']").val('N');
	}
}
function queryShow1(queryName){
	var queryObj=document.all.item(queryName);		
	var objHeight= queryObj.style.height;
	var objWidth= queryObj.style.width;
	var objLeft = 0;
	var objTop = 0;
	
	//依查詢視窗長度 修改視窗顯示定位值
	if(objWidth.indexOf("%") > 0){
		objWidth = objWidth.substring(0,objWidth.indexOf("%"));
		objLeft = (document.body.clientWidth-Number((document.body.clientWidth*objWidth)/100))/2;
		if(objLeft < 0){
			objLeft = 0;
		}
	}
	else if(objWidth.indexOf("px") > 0){
		objWidth = objWidth.substring(0,objWidth.indexOf("px"));
		objLeft = (document.body.clientWidth-Number(objWidth))/2;
		if(objLeft < 0){
			objLeft = 0;
		}
	}
	else if(objWidth.indexOf("auto") >= 0){
		objLeft = (document.body.clientWidth)*5/100;
	}
	
	//依查詢視窗寬度 修改視窗顯示定位值
	if(objHeight.indexOf("%") > 0){
		objHeight = objHeight.substring(0,objHeight.indexOf("%"));
		objTop = (document.body.clientHeight-Number((document.body.clientHeight*objHeight)/100)-80)/2;
		if(objTop < 0){
			objTop = 0;
		}
	}
	else if(objHeight.indexOf("px") > 0){
		objHeight = objHeight.substring(0,objHeight.indexOf("px"));
		objTop = (document.body.clientHeight-Number(objHeight)-80)/2;
		if(objTop < 0){
			objTop = 0;
		}
	}
	else if(objHeight.indexOf("auto") >= 0){
		objTop = (document.body.clientHeight-80)*2/10;
		if(objTop < 0){
			objTop = 0;
		}
	}
	
	//查詢視窗定位
	queryObj.style.top= objTop;
	queryObj.style.left= objLeft;
	/*
	//查詢視窗長度 若超過主頁視窗長度 則修改
	if(objWidth > document.body.clientWidth){
		queryObj.style.width = document.body.clientWidth;
	}
	else{
		if(objWidth.indexOf("auto") < 0){
			queryObj.style.width = objWidth;
		}
	}
	
	//查詢視窗寬度 若超過主頁視窗寬度 則修改
	if(objHeight > document.body.clientHeight){
		queryObj.style.height = document.body.clientHeight;
	}
	else{
		if(objHeight.indexOf("auto") < 0){
			queryObj.style.height = objHeight;
		}
	}
	*/

	queryObj.style.display="block"; 
	form1.state.value="queryAll";
}
function changePropertyNo(propertyNo){}


</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="q_verify" size="1" maxlength="1" value="Y">
<input type="hidden" name="q_dataState" size="1" maxlength="1" value="1">
<!--批次設定區============================================================-->
<div id="gountbu017f" style="position:absolute;z-index: 25;left:0;top :0;width:500px;height:200px;display:none">
	<iframe id="gountbu017fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	
	<tr id=TR_q_newEnterOrg>
		<td class="queryTDLable">接管機關：</td>
		<td class="queryTDInput">
			<!--問題單1897，修正所有的減損原因 [接管機關] 都應該要能選全部的機關 -->
			<%=util.View.getPopOrgan("field_Q","q_newEnterOrg",obj.getQ_newEnterOrg(),obj.getQ_newEnterOrgName(),"Y")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">交接日期：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_transferDate" size="7" maxlength="7" value="<%=obj.getQ_transferDate()%>">
			<input class="field_Q" type="button" name="btn_q_transferDate" onclick="popCalndar('q_transferDate')" value="..." title="萬年曆輔助視窗">
		</td>
	</tr>	 
	
	<tr>
		<td class="queryTDLable">報損報廢原因：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_cause2" size="40" maxlength="40" value="<%=obj.getQ_cause2()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">繳存地點：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_returnPlace" size="24" maxlength="24" value="<%=obj.getQ_returnPlace()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確 　　定" onClick="form1.state.value='insert'">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountbu017f');">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
		<td class="queryTDLable" width="15%">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
			<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
			<input class="field_QRO" type="hidden" name="q_ownership" size="1" maxlength="1" value="<%=obj.getQ_ownership()%>">
			<input class="field_QRO" type="hidden" name="q_caseNo" size="10" maxlength="10" value="<%=obj.getQ_caseNo()%>">
			<input class="field_QRO" type="hidden" name="q_reduceDate" size="7" maxlength="7" value="<%=obj.getQ_reduceDate()%>">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable" >財產分號：</td> 
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>	
	</tr>	
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signLaNo1" onChange="changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signLaNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signLaNo2" onChange="changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','');">
				<script>changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signLaNo3">
				<script>changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=obj.getQ_signLaNo3()%>');</script>
			</select>
			&nbsp;地號：
			<input class="field_Q" type="text" name="q_signLaNo4" size="4" maxlength="4" value="<%=obj.getQ_signLaNo4()%>" onchange="addChar(this ,4);">&nbsp;-
			<input class="field_Q" type="text" name="q_signLaNo5" size="4" maxlength="4" value="<%=obj.getQ_signLaNo5()%>" onchange="addChar(this ,4);">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signBuNo1" onChange="changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signBuNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signBuNo2" onChange="changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','');">
				<script>changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signBuNo3">
				<script>changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=obj.getQ_signBuNo3()%>');</script>
			</select>
			　建號：
			<input class="field_Q" type="text" name="q_signBuNo4" size="5" maxlength="5" value="<%=obj.getQ_signBuNo4()%>" onchange="addChar(this ,5);">&nbsp;-
			<input class="field_Q" type="text" name="q_signBuNo5" size="3" maxlength="3" value="<%=obj.getQ_signBuNo5()%>" onchange="addChar(this ,3);">		
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput" width="25%">
			<%=util.View._getSelectHTML("field_Q", "q_propertyKind", obj.getQ_propertyKind(),"PKD") %>		
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_fundType", obj.getQ_fundType(),"FUD") %>
		</td>				
	</tr>		 
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
				<%=util.View.getYNOption(obj.getQ_taxCredit())%>
			</select>		
		</td>					
	</tr>
	<tr>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput" colspan="3">
			<input type="text" class="field_Q" name="q_propertyName1" value="<%=obj.getQ_propertyName1() %>" size="20">			
		</td>		
		
	</tr>
		<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<input type="hidden" name="tempEnterOrg" value="<%=obj.getQ_enterOrg()%>">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" +  obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getQ_keeper()) %>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly", obj.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", obj.getQ_userNo()) %>
			<input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
			&nbsp;&nbsp;&nbsp;
			使用註記：
			<input class="field_Q" type="text" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_place" size="10" maxlength="10" value="<%=obj.getQ_place()%>" onchange="queryPlaceName('q_enterOrg','q_place');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place','q_placeName')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_placeName" size="20" maxlength="20" value="<%=obj.getQ_placeName()%>">]		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput" colspan="3">			
			<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">工程編號：</td>
		<td class="queryTDInput" colspan="3">
       		<input class="field_Q" type="text" name="q_engineeringNo" size="10" maxlength="11" value="<%=obj.getQ_engineeringNo()%>" onchange="queryEngineeringName('q_enterOrg','q_engineeringNo');">
			<input class="field_Q" type="button" name="btn_engineeringNo" onclick="popEngineering(form1.organID.value,'q_engineeringNo','q_engineeringNoName');" value="..." title="工程編號輔助視窗">
			[<input class="field_PRO" type="text" name="q_engineeringNoName" size="20" maxlength="50" value="<%=obj.getQ_engineeringNoName()%>">]
       	</td>
	</tr>
	<tr>
		<td class="queryTDLable">已逾使用年限<br>未報廢：</td>
		<td class="queryTDInput" colspan="3">
		<% if("Y".equals(obj.getQ_overLimitYear())){ %>
			<input class="field_Q" type="checkbox" name="q_overLimitYear" size="10" maxlength="10" onclick="checkOverLimitYear();" checked>
		<%} else { %>
			<input class="field_Q" type="checkbox" name="q_overLimitYear" size="10" maxlength="10" onclick="checkOverLimitYear();">
		<%} %>
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

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
	
	
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="queryAll" value="查　詢" onClick="queryShow1('queryContainer')">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="queryShow('gountbu017f');">&nbsp;|
</td></tr>

<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td >
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">原分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">土地/建物標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">原值</a></th>				
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">帳面價值(淨值)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',13,false);" href="#">未達年限</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',14,false);" href="#">已使用年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',15,false);" href="#">未入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[19];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";			
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[5])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[4]+"','"+rowArray[6]+"')\"";
			sbHEML.append(" <tr class='listTR' >\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			
			String chkproofdesc = Common.checkGet(rowArray[20]);
			if (!"".equals(chkproofdesc)) {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_QRO' disabled name='dis_sKeySet' value=\"\" >\n");
			} else {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[6]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			}
			
			sbHEML.append(" <td class='listTD'>"+rowArray[3]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[10]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[11]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[12]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[13]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[14]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[15]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[16]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[17]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[18]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[19]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[20]+"</td></tr> ");
		}
	}
	out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>
