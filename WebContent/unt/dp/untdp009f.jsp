 
<!--
程式目的：折舊作業_不折舊設定
程式代號：untdp009f
程式日期：1030905
程式作者：Mike Kao
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.dp.UNTDP009F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	if ("queryAll".equals(obj.getState())) {
		if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
		if ("true".equals(obj.getQueryAllFlag())){
			objList = obj.queryAll();
		}
	}else if ("update".equals(obj.getState())) {	
		obj.update();
		objList = obj.queryAll();
	}
%>

<html>
<head>
<title>不折舊設定</title>
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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>

<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

//當增加原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.q_cause.value=="499"){
		form1.q_cause1.readOnly = false;
	}else{
		form1.q_cause1.value="";
		form1.q_cause1.readOnly = true;
	}
	if (form1.q_cause.value=="201" || form1.q_cause.value=="206"){
		setFormItem("btn_q_newEnterOrg,btn_q_transferDate,q_transferDate","O");
	}else{
		setFormItem("btn_q_newEnterOrg,btn_q_transferDate,q_transferDate","R");
		form1.q_newEnterOrg.value="";
		form1.q_newEnterOrgName.value="";
		form1.q_transferDate.value="";
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alertStr += checkInsertSelect();
		alertStr += checkEmpty(form1.q_cause,"減損原因");
		if (form1.q_cause.value=="499"){
			alertStr += checkEmpty(form1.q_cause1,"減損原因-其他說明");
		}
		if (form1.q_cause.value=="201" || form1.q_cause.value=="206"){
			alertStr += checkEmpty(form1.q_newEnterOrgName,"接管機關");
			alertStr += checkEmpty(form1.q_transferDate,"交接日期");
		}
		alertStr += checkDate(form1.q_transferDate,"交接日期");
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

function initC(){
	document.all.item("spaninsert").style.display="none";
	document.all.item("spanupdate").style.display="none";
	document.all.item("spandelete").style.display="none";
	document.all.item("spanclear").style.display="none";
	document.all.item("spanconfirm").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
	document.all.item("spanListPrint").style.display="none";
	document.all.item("spanListHidden").style.display="none";
	setFormItem("queryAll","O");
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
		opener.form1.state.value = 'insertBatchSuccess';
		opener.form1.submit();
		if (isObj(window.aha)) window.aha.close();
	}
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







function checkID(){
	if(form1.isAdminManager.value=='Y'){
	
		document.getElementById('queryContainer').style.display="online";
	}else{
		document.getElementById('queryContainer').style.display="none";
	}   
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');initC();showErrorMsg('<%=obj.getErrorMsg()%>');init();checkID();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:850px;height:300px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
        <td class="queryTDInput" colspan="3">
	  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>		
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
			<input class="field_QRO" type="hidden" name="q_ownership" size="10" maxlength="10" value="<%=obj.getQ_ownership()%>">
			<input class="field_QRO" type="hidden" name="q_caseNo" size="24" maxlength="24" value="<%=obj.getQ_caseNo()%>">
			<input class="field_QRO" type="hidden" name="q_reduceDate" size="24" maxlength="24" value="<%=obj.getQ_reduceDate()%>">
			<input class="field_QRO" type="hidden" name="q_verify" size="10" maxlength="10" value="<%=obj.getQ_verify()%>">
			<input class="field_QRO" type="hidden" name="q_dataState" size="10" maxlength="10" value="1">
			<input class="field_QRO" type="hidden" name="q_jsp" size="10" maxlength="10" value="<%=obj.getQ_jsp()%>">
			<input class="field_QRO" type="hidden" name="noDeprSet" size="1" maxlength="1" value="N">
		
			<input type="hidden" name="tempEnterOrg" value="">
		
			<select class="field_Q" type="select" name="q_keepUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getQ_keepUnit())%>	
			</select>	
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<select class="field_Q" type="select" name="q_keeper">				  
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_keeper())%>
			</select>
			<input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper','q_userNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_useUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getQ_useUnit())%>
			</select>	
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<select class="field_Q" type="select" name="q_userNo">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_userNo())%>
	        </select>
	        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
	        <br>使用註記：
	        <input type="text" class="field_Q" name="q_userNote" value="<%= obj.getQ_userNote() %>" size="20" maxlength ="20" >
		</td>	
	<tr>
		<td class="queryTDLable" width="13%">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
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
		<td class="queryTDLable" >財產區分別：</td> 
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_differenceKind">
			　　<%=util.View.getTextOption("110;公務用-公務類;111;公務用_公務類(代管資產);120;公務用-作業基金;","obj.getQ_differenceKind()")%>
			</select>
		</td>			
	</tr>	
	<tr>
		<td class="queryTDLable" >財產名稱：</td> 
		<td class="queryTDInput" >
			<input type="text" class="field_Q" name="q_propertyName" value="<%= obj.getQ_propertyName() %>" size="30">
		</td>
		<td class="queryTDLable" >財產別名：</td> 
		<td class="queryTDInput" >
			<input type="text" class="field_Q" name="q_propertyName1" value="<%= obj.getQ_propertyName1() %>" size="30">
		</td>			
	</tr>	
	<tr>
		<td class="queryTDLable" >廠牌：</td> 
		<td class="queryTDInput" >
			<input type="text" class="field_Q" name="q_nameplate" value="<%=obj.getQ_nameplate() %>" size="40" maxlength="40">
		</td>
		<td class="queryTDLable" >型式：</td> 
		<td class="queryTDInput" >
			<input type="text" class="field_Q" name="q_specification" value="<%=obj.getQ_specification() %>" size="40" maxlength="40">
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
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="whatButtonFireEvent('update');form1.submit();">&nbsp;|
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">購置日期</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">廠牌</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">型式</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">帳面價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">保管單位代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">保管單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">存置地點代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">存置地點</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">使用年限</a></th>		
	</thead>
	<tbody id="listTBODY">
	<%
//		boolean primaryArray[] = {true,true,true,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
//		boolean displayArray[] = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
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
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[4]+","+rowArray[5])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";			
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[2]+"','"+rowArray[7]+"','"+rowArray[8]+"')\"";
			sbHEML.append(" <tr class='listTR'>\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+Common.checkGet(rowArray[0])+","+Common.checkGet(rowArray[2])+","+Common.checkGet(rowArray[4])+","+Common.checkGet(rowArray[7])+","+Common.checkGet(rowArray[8])+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			sbHEML.append(" <td class='listTD'><a href='#' " + tempJS + ">"+Common.checkGet(rowArray[1])+"</a></td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[3])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[5])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[6])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[7])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[8])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[9])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[10])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[11])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[12])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[13])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[14])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[15])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[16])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[17])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[18])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[19])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[20])+"</td></tr>\n ");
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
