<!--
程式目的：土地增減值作業－現有資料明細新增明細資料
程式代號：untla020f
程式日期：0940928
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA020F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA020F)obj.queryOne();	
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
<title>土地增減值作業現有資料明細新增輔助視窗</title>
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
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

//當增減值原因選「其他」時，開放其他說明欄位
function changecause(){
	if (form1.q_cause.value=="3"){
		form1.q_cause1.readOnly = false;
	}else{
		form1.q_cause1.value="";
		form1.q_cause1.style.backgroundColor="";
		form1.q_cause1.readOnly = true;
	}
}

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkInsertSelect();
		alertStr += checkEmpty(form1.q_cause,"增減值原因");
		if (form1.q_cause.value=="3"){
			alertStr += checkEmpty(form1.q_cause1,"增減值原因-其他說明");
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

function initC(){
	document.all.item("spaninsert").style.display="none";
	document.all.item("spanupdate").style.display="none";
	document.all.item("spandelete").style.display="none";
	document.all.item("spanclear").style.display="none";
	document.all.item("spanconfirm").style.display="none";
	setFormItem("queryAll","O");
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
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

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');initC();showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--批次設定區============================================================-->
<div id="gountla020f" style="position:absolute;z-index: 25;left:0;top :0;width:350px;height:150px;display:none">
	<iframe id="gountla020fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次設定視窗</div>
	<table class="queryTable"  border="1">	
	</tr> 
		<td class="queryTDLable"><font color="red">*</font>增減值原因：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_cause" onchange="changecause()">
				<option value="">請選擇</option>
				<option value="1">公告地價調整</option>
				<option value="2">資產重估調整</option>
				<option value="3">其它</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">其它說明：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_cause1" size="24" maxlength="24" value="<%=obj.getQ_cause1()%>" readOnly=true>
		</td>
	</tr>	 
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確    定" onClick="whatButtonFireEvent('insert');">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountla020f');initC();">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:350px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
		<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
		<input class="field_QRO" type="hidden" name="q_caseNo" size="24" maxlength="24" value="<%=obj.getQ_caseNo()%>">
		<input class="field_QRO" type="hidden" name="q_adjustDate" size="24" maxlength="24" value="<%=obj.getQ_adjustDate()%>">
		<input class="field_QRO" type="hidden" name="q_dataState" size="24" maxlength="24" value="1">		
		<td class="queryTDLable" width="13%">財產編號：</td>	
		<td class="queryTDInput" colspan="3">		
 			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"1")%>&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"1")%>
		</td>
	</tr> 
	<tr>
		<td class="queryTDLable">財產分號：</td> 
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput" >
			<select class="field_Q" type="select" name="q_ownership" disabled=true>
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
			<!-- 審核： -->
			<input type="hidden" name="q_verify" value="Y">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
			</select>
			　地號：
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=obj.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=obj.getQ_signNo5()%>">		
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
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
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
		<input class="field_QRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
		<input class="field_QRO" type="HIDDEN" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
		<input class="field_QRO" type="HIDDEN" name="caseNo" size="24" maxlength="24" value="<%=obj.getCaseNo()%>">
		<td class="td_form" width="14%">財產編號：</td>
		<td class="td_form_white" colspan="3">
	  		[<input class="field_QRO" type="text" name="propertyNo" size="10" maxlength="10" value="<%=obj.getPropertyNo()%>"  onBlur="getPropertyNo('PN');getValue();">]
	   		&nbsp;分號：[<input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onBlur="getPropertyNo('PN');getValue();">]
	 		&nbsp;名稱：[<input class="field_RO" type="text" name="propertyName" size="16" maxlength="50" value="<%=obj.getPropertyName()%>">]
			&nbsp;取得日期：[<input type="text" name="sourceDate" class="field_RO"  size="7" maxlength="7" value="<%=obj.getSourceDate()%>">]
		<br>
			別名：[<input class="field_RO" type="text" name="propertyName1" size="20" maxlength="20" value="<%=obj.getPropertyName1()%>">]	
			&nbsp;原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" maxlength="10" value="<%=obj.getOldPropertyNo()%>">]
			&nbsp;&nbsp;原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
		</td>
	</tr>
	<tr>
	  <td width="120" class="td_form">土地標示：</td>
	  <td colspan="3" class="td_form_white">
	  	<select  type="select" class="field_RO" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getPropertyNo('SN');getValue();">
        	<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
      	</select>
        <select  type="select" class="field_RO" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getPropertyNo('SN');getValue();">
          <script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
        </select>
        <select  type="select" class="field_RO" name="signNo3">
          <script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
        </select>
		　地號：
		[<input class="field_RO" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onBlur="getPropertyNo('SN');getValue();">]
		&nbsp;-
		[<input class="field_RO" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onBlur="getPropertyNo('SN');getValue();">]
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
			　　　新草衙：		
			<select class="field_RO" type="select" name="grass">
				<%=util.View.getYNOption(obj.getGrass())%>
			</select>			
		</td>
	</tr>
	<tr>
		<td class="td_form">面積：</td>
		<td class="td_form_white" colspan="3">		
			整筆面積(㎡)：
			[<input class="field_RO" type="text" name="area" size="12" maxlength="12" value="<%=obj.getArea()%>">]
		<br>
			權利分子：[<input class="field_RO" type="text" name="holdNume" size="10" maxlength="10" value="<%=obj.getHoldNume()%>">]
			&nbsp;權利分母：[<input class="field_RO" type="text" name="holdDeno" size="10" maxlength="10" value="<%=obj.getHoldDeno()%>">]
			權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form">價值：</td>
		<td class="td_form_white" colspan="3">
			原始入帳總價：[<input class="field_RO" type="text" name="originalBV" size="15" maxlength="15" value="<%=obj.getOriginalBV()%>">]
		<br>
			帳面金額單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="13" value="<%=obj.getBookUnit()%>">]
			&nbsp;　　　　帳面金額總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
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
	</table>
	</div>
</td>
</tr>

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
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="批次設定" onClick="queryShow('gountla020f');">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">整筆面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">帳面金額─總價</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {true,true,true,true,false,false,false,false};
	//boolean displayArray[] = {false,false,true,true,true,true,true,true};
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
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";			
			tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";
			sbHEML.append(" <tr class='listTR' >\n");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
			sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			sbHEML.append(" <td class='listTD'><a href='#' " + tempJS + ">"+rowArray[2]+"</a></td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[3]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[6]+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+rowArray[7]+"</td></tr>\n ");
		}
	}
	out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>


</form>
</body>
</html>
