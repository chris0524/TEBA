<!--
程式目的：物品增減值作業--現有資料明細新增明細資料
程式代號：untne026f
程式日期：0941123
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE026F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String caseNo = request.getParameter("caseNo");
String enterOrg = request.getParameter("enterOrg");
String ownership = request.getParameter("ownership");
String differenceKind = request.getParameter("differenceKind");

obj.setQ_caseNo(caseNo);
obj.setQ_enterOrg(enterOrg);
obj.setQ_ownership(ownership);
obj.setQ_differenceKind(differenceKind);
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ne.UNTNE026F)obj.queryOne();	
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
<title>物品增減值作業現有資料明細新增輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js?v=1.0"></script>
<script language="javascript" src="../../js/getUNTNENonexp.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		
	
	}else if (form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkInsertSelect();
		
		alertStr += checkEmpty(form1.q_cause,"增減值原因");
		if((form1.q_addBookValue.value=="") && (form1.q_reduceBookValue.value=="")){
			alertStr += "增加價值、減少價值二者不能皆為空";
			}
		if(	form1.q_addBookValue.value==""){
			form1.q_addBookValue.value="0";
		}
		if(form1.q_reduceBookValue.value==""){
			form1.q_reduceBookValue.value="0";
			}
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

function initB(){
	//document.all.item("spaninsert").style.display="none";
	//document.all.item("spanupdate").style.display="none";
	//document.all.item("spandelete").style.display="none";
	//document.all.item("spanclear").style.display="none";
	//document.all.item("spanconfirm").style.display="none";
	//document.all.item("spannextInsert").style.display="none";
	
	document.all.item("spanListPrint").style.display="none";
	document.all.item("spanListHidden").style.display="none";
	
	setFormItem("queryAll","O");
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
		opener.form1.submit();
		if (isObj(window.aha)) window.aha.close();
	}

	var dcc12 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
	var dcc34 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
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
function queryPlaceName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../ch/queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			
		});	
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');initB();showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位===========================================================-->

<input class="field_QRO" type="hidden" name="q_dataState" size="24" maxlength="24" value="1">		
<input type="hidden" name="q_verify" value="Y">		
<input type="hidden" name="q_valuable" value="N">
<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
<input class="field_QRO" type="hidden" name="caseNo" size="24" maxlength="24" value="<%=obj.getCaseNo()%>">
<input class="field_RO" type="hidden" name="valuable" value="<%=obj.getValuable()%>">
<input class="field_RO" type="hidden" name="editID" size="10" value="<%=user.getUserID()%>">
<!--批次設定區============================================================-->
<div id="gountne026f" style="position:absolute;z-index: 25;left:0;top :0;width:300px;height:100px;display:none">
	<iframe id="gountne026fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	<tr> 
		<td class="queryTDLable"><font color="red">*</font>增減值原因：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_cause", obj.getQ_cause(),"AJC") %>
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
		<input class="field_Q" type="text" name="q_reduceBookValue" size="12" maxlength="15" value="<%=obj.getQ_reduceBookValue()%>">
		</td>		
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountne026f');initB();">
		</td>
	</tr>
	</table>
</div>
<!--Query區============================================================-->
<div id="queryContainer" style="width:900px;height:180px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
		<td class="queryTDLable" width="13%">物品編號：</td>		
		<td class="queryTDInput" colspan="3">		
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >物品分號：</td> 
		<td class="queryTDInput" colspan="3">		
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
	</tr> 
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>	
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_fundType", obj.getQ_fundType(),"FUD") %>
		</td>				
	</tr>		 

	<tr>		
		<td class="queryTDLable">物品別名：</td>
		<td class="queryTDInput" colspan="3"> 
			<input type="text" class="field_Q" name="q_propertyName1" value="<%=obj.getPropertyName1() %>" size="20">
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
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getKeeper()) %>
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
			<input type="text" class="field" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1()%>" onchange="queryPlaceName('enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place1" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name()%>">]		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput" colspan="3">			
			<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
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
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="queryShow('gountne026f');">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td>
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">原分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">物品名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">物品別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">保管人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">帳面數量</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">帳面價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">未入帳</a></th>
	</thead>
	<tbody id="listTBODY">	
	<%
	//boolean primaryArray[] = {true,true,true,true,false,false};
	//boolean displayArray[] = {false,false,true,true,true,true};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));	
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[8];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";			
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";			
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[4]+"')\"";
			sbHEML.append(" <tr class='listTR' >\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			
			String chkproofdesc = Common.checkGet(rowArray[15]);
			if (!"".equals(chkproofdesc)) {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_QRO' disabled name='dis_sKeySet' value=\"\" >\n");
			} else {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			}
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[6]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[8]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[9]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[10]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[11]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[12]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[13]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[14]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[15]+"</td></tr>\n ");
		}
	}
	out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
<script>

</script>
</form>
</body>
</html>
