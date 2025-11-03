<!--
程式目的：物品基本資料挑檔作業
程式代號：UNTNE033R
程式日期：0941213
程式作者：Cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE033R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("exportAll".equals(obj.getState())) {
	obj.exportAll();
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../unt/mp/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="exportAll") {
		alertStr += checkEmpty(form1.sDestField,"目的欄位");
	}	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	//form1.state.value="queryOne";
	//beforeSubmit();
	//form1.submit();
}

function sSourceToDest() {
	m1 = document.all.sSourceField;
	m2 = document.all.sDestField;
    m1len = document.all.sSourceField.length;
	var alertStr="";	
	if(form1.state.value=="queryAllSuccess" || form1.state.value=="exportAll") {	
	} else {
		alertStr += "請先執行查詢!\n";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	    
    for ( i=0; i<m1len ; i++){
        if (m1.options[i].selected == true ) {
            m2len = m2.length;
            m2.options[m2len]= new Option(m1.options[i].text, m1.options[i].value);
        }
    }

    for ( i = (m1len -1); i>=0; i--){
        if (m1.options[i].selected == true ) {
            m1.options[i] = null;
        }
    }
}

function sDestToSource() {
	m1 = document.all.sSourceField;
	m2 = document.all.sDestField;
    m2len = document.all.sDestField.length ;
	for ( i=0; i<m2len ; i++){
		if (m2.options[i].selected == true ) {
			m1len = m1.length;
			m1.options[m1len]= new Option(m2.options[i].text,m2.options[i].value);
		}
	}
	for ( i=(m2len-1); i>=0; i--) {
		if (m2.options[i].selected == true ) {
			m2.options[i] = null;
		}
	}
}

function selectAllField() {
	m2 = document.all.sDestField;
	for ( var i=0; i<document.all.sDestField.length ; i++){
		m2.options[i].selected=true;
	}
}

function clickExportAll(){
	var alertStr = "";
	alertStr += checkQuery();	
	selectAllField();	
	alertStr += checkEmpty(form1.sDestField,"目的欄位");	
	if(form1.state.value=="queryAllSuccess" || form1.state.value=="exportAll") {	
	} else {
		alertStr += "請先執行查詢!\n";
	}
	if(alertStr.length!=0){ alert(alertStr); return false; } else {
			document.all.state.value='exportAll';	
			form1.submit();
	}	
}

function init(){
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanClear").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	if (isObj(document.all.item("spanListPrint"))) document.all.item("spanListPrint").style.display="none";	
	if (form1.state.value=="exportAll") {	
		exportPopup();
	}	
}

function exportPopup(){
    var url = "../../downloadFileSimple?fileName=" + form1.excelFileName.value;
	moshe=window.open('','MyWindow','scrollbars=1, resizable=yes, status=no, toolbar=no,menubar=no,width=400,height=70');
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 資料輸出 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="0" marginwidth="0" marginheight="0">\n');
	moshe.document.write('<div align=center><br><h>資料輸出成功!</h><br><br></div>');    
	moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">物品基本資料挑檔作業</a><br><br>');	
	moshe.document.write('</body></html>');	
	form1.state.value="queryAllSuccess";	
}

function alteredEnterOrg(){
	changeAll();
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField();">
<!--隱藏欄位===========================================================-->
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">	
<input class="field_QRO" type="hidden" name="q_dataState" value="1">
<input class="field_QRO" type="hidden" name="q_verify" value="Y">	
<!-- <input class="field_QRO" type="hidden" name="q_ownership" value="1"> -->
<input class="field_QRO" type="hidden" name="q_valuable" value="N">
<!--Query區============================================================-->
<div id="queryContainer" style="width:770px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=alteredEnterOrg();')" value="..." title="機關輔助視窗">
		<td class="queryTDLable"><div align="right">權屬：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_ownership">
	        <%=util.View.getOnwerOption(obj.getQ_ownership())%>
	      </select>
	    </td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">	
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','6')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','6')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>		
	  <td class="queryTDLable">入帳日期：</td>
	  <td class="queryTDInput">
			  起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
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
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">單價：</td>
		<td class="queryTDInput" colspan="3">
			起<input class="field_Q" type="text" name="q_bookValueS" size="10" maxlength="15" value="<%=obj.getQ_bookValueS()%>">&nbsp;&nbsp;
			~訖<input class="field_Q" type="text" name="q_bookValueE" size="10" maxlength="15" value="<%=obj.getQ_bookValueE()%>">
		</td>		
	</tr>
<!-- 
	<tr>
		<td class="queryTDLable">珍貴物品：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
	</tr>
-->	
	<tr>
		<td class="queryTDLable">經費來源：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundsSource">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getQ_fundsSource())%>
			</select>
		</td>
		<td class="queryTDLable">物品來源-種類：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_sourceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKC' ", obj.getQ_sourceKind())%>
			</select>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_keepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');">
  				<script>changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','<%=obj.getQ_keepUnit()%>');</script>
			</select>
		</td>
	    <td class="queryTDLable"><div align="right">保管人：</div></td>
	    <td class="queryTDInput">
	    	<select class="field_Q" type="select" name="q_keeper">
            	<script>getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '<%=obj.getQ_keeper()%>');</script>
          	</select>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_useUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_userNo, '');">
  				<script>changeQ_enterOrg(form1.q_enterOrg.value,'q_useUnit','<%=obj.getQ_useUnit()%>');</script>
			</select>
		</td>
	    <td class="queryTDLable"><div align="right">使用人：</div></td>
	    <td class="queryTDInput">
	    	<select class="field_Q" type="select" name="q_userNo">
            	<script>getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '<%=obj.getQ_userNo()%>');</script>
          	</select>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_place" size="50" value="<%=obj.getQ_place()%>">
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
	<table  border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      <td><select name="sSourceField" size="12" multiple>
      <%=obj.getFieldList(""+application.getInitParameter("ManageOrgID"), ""+user.getOrganID())%>
      </select></td>
      <td width="60" align="center">
      <input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>">
      <input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">
      <input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
      <input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
      <p>
	<input class="toolbar_default" type="button" followPK="false" name="btnSelect" value="--->"  onClick="sSourceToDest();">
          <br>
          <br>
	<input class="toolbar_default" type="button" followPK="false" name="btnUnSelect" value="<---"  onClick="sDestToSource();">
	  
      </p>
        </td>
      <td><select name="sDestField" size="12" multiple>
      </select></td>
    </tr>
  </table>
	<br>
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
	<input type="hidden" name="exportPath" value="<%=application.getRealPath("/exportFolder")%>">	
	<input type="hidden" name="excelFileName" value="<%=obj.getExcelFileName()%>">	
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="submit" followPK="false" name="exportAll" value="輸出Excel檔"  onClick="clickExportAll();">
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機屬</a></th>
		<!-- <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th> -->
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號-分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品名稱</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,true,true,true,true};
	boolean displayArray[] = {true,false,true,true,false,false,false,false};
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
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
			break;				
	}
	return true;
}

</script>
</body>
</html>
