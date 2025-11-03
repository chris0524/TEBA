
<!--
程式目的：建物增減值作業－現有資料明細新增明細資料
程式代號：untbu023f
程式日期：0941005
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH003F03">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String caseNo = Common.checkGet(request.getParameter("caseNo"));
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String differenceKind = Common.checkGet(request.getParameter("differenceKind"));

obj.setQ_caseNo(caseNo);
obj.setQ_enterOrg(enterOrg);
obj.setQ_ownership(ownership);
obj.setQ_differenceKind(differenceKind);

if ("queryAll".equals(obj.getState()) || "queryAllError".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}

if("insertSuccess".equals(obj.getState())){
	obj.setQ_cause("");
	obj.setQ_cause1("");
	obj.setQ_cause2("");
	obj.setQ_addBookValue("");
	obj.setQ_reduceBookValue("");
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<title>增減值作業現有資料明細新增輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js?v=1.0"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		
	
	}else if (form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkInsertSelect();
		
		alertStr += checkEmpty(form1.q_cause,"增減值原因");
		columnTrim( form1.q_addBookValue );
		columnTrim( form1.q_reduceBookValue );
		if( form1.q_addBookValue.value.length == 0 && form1.q_reduceBookValue.value.length ==0){
			form1.q_addBookValue.style.backgroundColor="#FFCEDB";
			form1.q_reduceBookValue.style.backgroundColor="#FFCEDB";
			alertStr += "增加價值和減少價值不允許同時空白!\n";
		} else if (form1.q_addBookValue.value.length == 0) {
			form1.q_addBookValue.value = "0";
		}  else if (form1.q_reduceBookValue.value.length == 0) {
			form1.q_reduceBookValue.value = "0";
		}
		//alertStr += checkEmpty(form1.q_addBookValue,"增加價值");
		//alertStr += checkEmpty(form1.q_reduceBookValue,"減少價值");	
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();	
}

function checkInsertSelect() {
	var sFlag = false;
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "sKeySet" && e.checked==true) sFlag = true;	    
	}
	if (sFlag) return "";
	else return "您尚未勾選任何資料，若無資料可供勾選，請重新查詢！\n";
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		opener.sonSumbit(form1.q_ownership.value, form1.q_differenceKind.value);
		if (isObj(window)) window.close();
	}
    
    setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListHidden,spanListPrint,spanQueryAll", "H");
    var dcc1 = new DataCouplingCtrlPlus(form1.organID, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.organID, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
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

function changePropertyNo(propertyNo){}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--批次設定區============================================================-->
<div id="gountbu023f" style="position:absolute;z-index: 25;left:0;top :0;width:450px;height:150px;display:none">
	<iframe id="gountbu023fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	<tr> 
		<td class="queryTDLable"><font color="red">*</font>增減值原因：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_cause", obj.getQ_cause(),"AJC") %>
			<input class="field_Q" type="button" name="btn_cause" onclick="popSysca_Code('q_cause','q_causeName','增減值原因','AJC','');" value="..." title="輔助視窗">			
		</td>
	</tr>
	
	<tr>
		<td class="queryTDLable">整建說明：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_cause2" size="24" maxlength="24" value="<%=obj.getQ_cause2()%>">
		</td>
	</tr>	
	
	<tr>
		<td class="queryTDLable">其它說明：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_cause1" size="24" maxlength="24" value="<%=obj.getQ_cause1()%>">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">增加使用月數：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_addLimitYear" size="12" maxlength="4" value="<%=obj.getQ_addLimitYear()%>" >
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">減少使用月數：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reduceLimitYear" size="12" maxlength="4" value="<%=obj.getQ_reduceLimitYear()%>" >
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">增加價值：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_addBookValue" size="12" maxlength="15" value="<%=obj.getQ_addBookValue()%>" >
		</td>		
	</tr>	 	
	<tr>
		<td class="queryTDLable">減少價值：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reduceBookValue" size="12" maxlength="15" value="<%=obj.getQ_reduceBookValue()%>" >
		</td>		
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountbu023f');initC();">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400;display:none;">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
		<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
		<input class="field_QRO" type="hidden" name="q_caseNo" size="24" maxlength="24" value="<%=obj.getQ_caseNo()%>">
		<input class="field_QRO" type="hidden" name="q_differenceKind" size="24" maxlength="24" value="<%=obj.getQ_differenceKind()%>">
		<input class="field_QRO" type="hidden" name="q_adjustDate" size="24" maxlength="24" value="<%=obj.getQ_adjustDate()%>">
		<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
		<input type="hidden" name="q_dataState" value="1">		
		<input type="hidden" name="q_verify" value="Y">		
	<tr>
		<td class="queryTDLable" width="13%">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >財產分號：</td> 
		<td class="queryTDInput">		
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership" >
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
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
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_propertyKind", obj.getQ_propertyKind(),"PKD") %>					
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_fundType", obj.getQ_fundType(),"FUD", 200) %>			
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
       		<input class="field_Q" type="text" name="q_engineeringNo" size="20" maxlength="20" value="<%=obj.getQ_engineeringNo()%>" onchange="queryEngineeringName('q_enterOrg','q_engineeringNo');">
			<input class="field_Q" type="button" name="btn_engineeringNo" onclick="popEngineering(form1.organID.value,'q_engineeringNo','q_engineeringNoName');" value="..." title="工程編號輔助視窗">
			[<input class="field_PRO" type="text" name="q_engineeringNoName" size="20" maxlength="50" value="<%=obj.getQ_engineeringNoName()%>">]
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

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type='hidden' class='field_QRO' name='engineeringNo' value='<%=obj.getEngineeringNo()%>'>		
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" name="queryAll" value="查　詢" onClick="queryShow1('queryContainer')">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="queryShow('gountbu023f');">&nbsp;|
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td>
<div>
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">原分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">土地/建物標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">權利範圍面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">原價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">帳面價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">已達使用年限</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">未入帳</a></th>
	</thead>
	<tbody id="listTBODY">	
	<%
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[13];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";			
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[6])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";			
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[4]+"','"+rowArray[6]+"')\"";
			sbHEML.append(" <tr class='listTR'>\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			
			String chkproofdesc = Common.checkGet(rowArray[14]);
			if (!"".equals(chkproofdesc)) {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_QRO' disabled name='dis_sKeySet' value=\"\" >\n");
			} else {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[6]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			}
			sbHEML.append(" <td class='listTD'>"+rowArray[3]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[8]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[10]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[11]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[12]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[13]+"</td>\n ");
			sbHEML.append(" <td class='listTD'> <font color='red'>"+rowArray[15]+"</font></td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[14]+"</td></tr>\n ");
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
