<!--
程式目的：整批輸入非消耗品盤點結果作業
程式代號：untpd017f
程式日期：1030915	
程式作者：Mike Kao
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Common, util.Datetime, java.sql.ResultSet,util.*" %>
<%@ page import="java.util.*"%> 
<%@ page import="util.report.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD017F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
//預設權屬為國有
obj.setQ_ownership("1");

if ("queryAll".equals(obj.getState()) || "updateSuccess".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
//}else if ("queryOne".equals(obj.getState())) {
//	obj.queryOne();	
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
//}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
//	obj.delete();
}

if("init".equals(obj.getState())){
	obj.setQ_enterOrg2(user.getOrganID());
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
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
var State = "";
insertDefault = new Array(

);
function setState() {
	State = "querySubmit_Click";
}

function checkField(){
	var alertStr="";
    var isChecked = false; 
	if (State != "querySubmit_Click") {
		for (var i=0; i<form1.sKeySet.length; i++){
			if(form1.sKeySet[i].checked){
				isChecked = true;
			}
		}

		if(!isChecked){
			alertStr = "請至少勾選其中一筆資料!!"
		}
		if(alertStr.length !=0){ alert(alertStr); return false; }
	}
	if(alertStr.length !=0){ alert(alertStr); return false; }
	
	beforeSubmit();
}

function init() {
	setFormItem("update","O");
	setFormItem("insert","R");
	setFormItem("nextInsert","R");
	setFormItem("delete","R");

	var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
}

function queryOne(enterorg,ownership,differencekind,propertyno,serialno){
	form1.state.value="queryOne";
	form1.enterorg.value=enterorg;
	form1.ownership.value=ownership;
	form1.differencekind.value=differencekind;
	form1.propertyno.value=propertyno;
	form1.serialno.value=serialno;

	beforeSubmit();
	form1.submit();
}

//盤點結果若正常，不可輸入異常原因
function oddsCauseChange(obj){
	if(obj.value == 1){
		document.all.item("oddsCause").value = "";
		document.all.item("oddsCause").disabled = "disabled";
	}else{
		document.all.item("oddsCause").disabled = "";
	}
}

function checkboxValue(name){
	if(document.getElementsByName(name)[0].checked == true){
		document.getElementsByName(name)[0].value="Y";
	}else{
		document.getElementsByName(name)[0].value="N";
	}
}

function listContainerRowClick(check,rowid) {	
	if (rowid!=null && isObj(document.all.item("listContainerActiveRowId"))) 
	{		
		
		setCookie(document.URL+'_listContainerActiveRowId',rowid);		
		document.all.item("listContainerActiveRowId").value = rowid;
		setDivPosition(CookieHelper.getJSPName(document.URL), "listContainer", rowid);
		if(check.checked) {
			listContainerLoadActiveRow();
		} else {
			listContainerLoadlistTR();
		}
	}
}

function ToggleAll(e, fname, sid){
	if (e.checked) {
	    CheckAll(fname, sid);
	}
	else {
	    ClearAll(fname, sid);
	}
}

function CheckAll(fname, sid){
	var ml = fname;
	var len = ml.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = ml.elements[i];
	    if (e.name == sid) {
		Check(e);
	    }
	    
	}
	for (var i = 0; i < len; i++) {
		listContainerRowClick(e,i+1);
	}
	ml.toggleAll.checked = true;

}

function ClearAll(fname, sid){
	var ml = fname;
	var len = ml.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = ml.elements[i];
	    if (e.name == sid) {
		Clear(e);
	    }
	}
	for (var i = 0; i < len; i++) {
		listContainerRowClick(e,i+1);
	}
	ml.toggleAll.checked = false;
}

function checkYN(obj){
	if (obj.checked == true){
		obj.value='Y';
	} else {
		obj.value='N';
	}
}

</script>
</head>

<body topmargin="0" onLoad="setCookie(document.URL+'_listContainerActiveRowId',0);whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--頁籤區============================================================-->

<!--Query區============================================================-->
<div id="queryContainer" style="width:950px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
		<tr>
			<td class="queryTDLable" width="12%">入帳機關：</td>
			<td class="queryTDInput" >
				<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
			</td>
		    <td class="queryTDLable" width="12%"><div align="right">權屬：</div>
		    <td class="queryTDInput">
				<select class="field_Q" type="select" name="q_ownership">
					<%=util.View.getOnwerOption(obj.getQ_ownership())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="queryTDLable" >財產區分別：</td>
			<td class="queryTDInput" >
				<select class="field_Q" type="select" name="q_differenceKind">
				　　<%=util.View.getTextOption("110;公務用-公務類;120;公務用_公務類(代管資產);130;公務用-作業基金;",obj.getQ_differenceKind())%>
				</select>
			</td>
		    <td class="queryTDLable">財產大類：</td>
		    <td class="queryTDInput">
				<select class="field_Q" type="select" name="q_propertyType">
				　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PTT' and codeid in ('2','3','4','5','6') ",obj.getQ_propertyType())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="queryTDLable">財產編號：</td>
			<td colspan="3" class="queryTDInput">
				起<input class="field_Q" type="text" name="q_propertyNoS" size="10" maxlength="11" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','3,4,5')">
			 	<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
				[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%= obj.getQ_propertyNoSName() %>">]&nbsp;~&nbsp;
				訖<input class="field_Q" type="text" name="q_propertyNoE" size="10" maxlength="11" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','3,4,5')">
				<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
				[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%= obj.getQ_propertyNoEName() %>">]
			</td>
		</tr>
		<tr>
			<td class="queryTDLable">財產分號：</td>
			<td class="queryTDInput">
				起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
				訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
			</td>
			<td class="queryTDLable">購置日期：</td>
			<td class="queryTDInput">
				起<%=util.View.getPopCalndar("field_Q","q_buyDateS",obj.getQ_buyDateS())%>&nbsp;~&nbsp;
				訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",obj.getQ_buyDateE())%>
			</td>
		</tr>
		<tr>
			<td class="queryTDLable">條碼：</td>
			<td class="queryTDInput">
				<input class="field_Q" type="text" name="q_barcode" size="24" maxlength="24" value="<%=obj.getQ_barcode()%>">
			</td>
			<td class="queryTDLable">盤點結果：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_checkResult">
				<%=util.View.getTextOption("1;盤點正常;2;盤點異常",obj.getQ_checkResult())%>			
				</select>
			</td>
		</tr>
		<tr>
			<td class="queryTDLable">原財產編號：</td>
			<td class="queryTDInput">
				<input class="field_Q" type="text" name="q_oldPropertyNo" size="20" maxlength="50" value="<%=obj.getQ_oldPropertyNo()%>">
			</td>
			<td class="queryTDLable">原財產分號：</td>
			<td class="queryTDInput">
				起<input class="field_Q" type="text" name="q_oldSerialNoS" size="15" maxlength="30" value="<%=obj.getQ_oldSerialNoS()%>">&nbsp;~&nbsp;
				訖<input class="field_Q" type="text" name="q_oldSerialNoE" size="15" maxlength="30" value="<%=obj.getQ_oldSerialNoE()%>">
			</td>
		</tr>
		<tr>
			<td class="queryTDLable">財產性質：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_propertyKind">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
				</select>
			</td>
			<td class="queryTDLable">基金財產：</td>
			<td class="queryTDInput">
				<select class="field_Q" type="select" name="q_fundType">
					<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid='FUD' ",obj.getQ_fundType())%>
				</select>
			</td>
		</tr>
		<tr>
			<td class="queryTDLable">移動資料：</td>
			<td class="queryTDInput" colspan="3">
				保管單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" +Common.esapi(user.getOrganID())+ "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly",  obj.getQ_keepUnit()) %>
				<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" +Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getQ_keeper()) %>
		        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" +Common.esapi(user.getOrganID())+ "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly",  obj.getQ_useUnit()) %>
				<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人		        
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" +Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", obj.getQ_userNo()) %>
		        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
		        <br>
				存置地點
				<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1()%>" onchange="queryPlaceName('enterOrg','q_place1');">
				<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
				[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name()%>">]
				<br>		
				存置地點說明
				<input class="field_Q" type="text" name="q_place" size="30" maxlength="50" value="<%=obj.getQ_place()%>">
				
			</td>
		</tr>
		<tr>
			<td class="queryTDLable"> </td>
			<td class="queryTDInput" colspan="3">		
				<%if("Y".equals(obj.getQ_scrappednote())){ %>
					<input type="checkbox" class="field_Q" name="q_scrappednote" onchange="checkboxValue('q_scrappednote');" checked >
				<% } else { %>
					<input type="checkbox" class="field_Q" name="q_scrappednote" onchange="checkboxValue('q_scrappednote');">
				<% } %>
				報廢註記
				&nbsp;&nbsp;&nbsp;
				<%if("Y".equals(obj.getQ_labelnote())){ %>
					<input type="checkbox" class="field_Q" name="q_labelnote" onchange="checkboxValue('q_labelnote');" checked >
				<% } else { %>
					<input type="checkbox" class="field_Q" name="q_labelnote" onchange="checkboxValue('q_labelnote');">
				<% } %>
				補印標籤註記
				&nbsp;&nbsp;&nbsp;
				<%if("Y".equals(obj.getQ_movenote())){ %>
					<input type="checkbox" class="field_Q" name="q_movenote" onchange="checkboxValue('q_movenote');" checked >
				<% } else { %>
					<input type="checkbox" class="field_Q" name="q_movenote" onchange="checkboxValue('q_movenote');">
				<% } %>
				移動註記
			</td>
		</tr>
		<tr>
			<td class="queryTDInput" colspan="4" style="text-align:center;">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onClick="setState();">
				<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
			</td>
		</tr>
	</table>	
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer" style="width: 100%;	height: 100px; ">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">盤點結果：</td>
	    <td class="td_form_white">
	    	<font color="red">*</font>盤點結果：
	    	<select class="field_Q" type="select" name="checkresult" onChange="oddsCauseChange(this);">
				<%=util.View.getTextOption("1;盤點正常;2;盤點異常", obj.getCheckresult())%>
			</select>
			<br>
			<% if("Y".equals(obj.getScrappednote())){%>
				<input type="checkbox" class="field_Q" name="scrappednote"  value = "Y" onClick ="checkboxValue('scrappednote');" checked>	
			<% }else{%>
				<input type="checkbox" class="field_Q" name="scrappednote" value = "N" onClick ="checkboxValue('scrappednote');">	
			<% }%>
			報廢註記	
			<br>
			<% if("Y".equals(obj.getLabelnote())){%>
				<input type="checkbox" class="field_Q" name="labelnote" value = "Y" onClick ="checkboxValue('labelnote');"  checked>
			<% }else{%>
				<input type="checkbox" class="field_Q" name="labelnote" value = "N"  onClick ="checkboxValue('labelnote');">
			<% }%>
			補印標籤註記
			<br>
			<% if("Y".equals(obj.getMovenote())){%>
				<input type="checkbox" class="field_Q" name="movenote" value = "Y" onClick ="checkboxValue('movenote');" checked>
			<% }else{%>
				<input type="checkbox" class="field_Q" name="movenote" value = "N" onClick ="checkboxValue('movenote');" >
			<% }%>
			移動註記
	    </td>  
		<td class="td_form"><font color="red">*</font>異常原因：</td>
	    <td class="td_form_white">
	    	<input class="field_Q" type="text" name="oddscause" size="50" maxlength="16" value="<%= obj.getOddscause() %>">
	    </td>  
	</tr>	
	
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">		
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">	
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	

	<input type="hidden" name="enterorg" value="<%=obj.getEnterorg() %>">	
	<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">	
	<input type="hidden" name="differencekind" value="<%=obj.getDifferencekind()%>">	
	<input type="hidden" name="propertyno" value="<%=obj.getPropertyno()%>">	
	<input type="hidden" name="serialno" value="<%=obj.getSerialno()%>">	
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">
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
		<th class="listTH" nowrap><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>
		<th class="listTH" nowrap><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品分號</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品名稱</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">帳列數量</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">盤點數量</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">總價</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">保管單位</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">保管人</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">盤點結果</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">報廢</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">補印</a></th>
		<th class="listTH" nowrap><a class="text_link_w" onclick="return sortTable('listTBODY',13,false);" href="#">移動</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
//		boolean primaryArray[] = {true,true,true,false,true,true,false,false,false,false,false,false,false,false,false,false};
//		boolean displayArray[] = {false,false,false,true,true,true,true,true,true,true,true,true,true,true,true,true};
//		out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));		

int counter=0;
		StringBuffer sbHEML = new StringBuffer();

		if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
			sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
		}else{
			String rowArray[]=new String[9];
			java.util.Iterator it=objList.iterator();
			String tempJS="";
			String isCheck = "unchecked";	
			while(it.hasNext()){
				counter++;	
				rowArray= (String[])it.next();
				isCheck = "unchecked";
				if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
					for (int j=0; j<obj.getsKeySet().length; j++) {
						if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[4]+","+rowArray[5]+","+rowArray[7])) {
							isCheck = "checked";
						}
					}
				}			
				//這個是key值 和.java裡的rowArray變數是對照的
				if ("checked".equals(isCheck)) {
					sbHEML.append(" <tr id=\"").append("listContainerRow").append(counter).append("\"");
					sbHEML.append(" class='activeRow' onmouseover=\"this.className='activeRow'\" onmouseout=\"this.className='activeRow'\" ");
					sbHEML.append(" >");
				} else {
					sbHEML.append(" <tr id=\"").append("listContainerRow").append(counter).append("\"");
					sbHEML.append(" class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" ");
					sbHEML.append(" >");
				}
				
				sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[4]+","+rowArray[5]+","+rowArray[7]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');listContainerRowClick(this,'").append(counter).append("');\"" + isCheck + ">\n");
				sbHEML.append(" <td class='listTD'>"+rowArray[3]+"</td>\n ");	//物品區分別
				sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");	//物品編號
				sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");	//物品分號
				sbHEML.append(" <td class='listTD'>"+rowArray[6]+"</td>\n ");	//物品別名
				sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");	//帳列數量
				sbHEML.append(" <td class='listTD'>"+rowArray[8]+"</td>\n ");	//盤點數量
				sbHEML.append(" <td class='listTD'>"+rowArray[9]+"</td>\n ");	//總價
				sbHEML.append(" <td class='listTD'>"+rowArray[10]+"</td>\n ");	//保管單位
				sbHEML.append(" <td class='listTD'>"+rowArray[11]+"</td>\n ");	//保管人
				sbHEML.append(" <td class='listTD'>"+rowArray[12]+"</td>\n ");	//盤點結果
				sbHEML.append(" <td class='listTD'>"+rowArray[13]+"</td>\n ");	//報廢
				sbHEML.append(" <td class='listTD'>"+rowArray[14]+"</td>\n ");	//補印
				sbHEML.append(" <td class='listTD'>"+rowArray[15]+"</td></tr>\n ");	//移動
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
<script language="javascript">

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "clear":
			init();			
		break;	
	}
}
</script>
</body>
</html>



