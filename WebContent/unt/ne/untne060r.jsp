<!--
程式目的：物品基本資料挑檔作業
程式代號：UNTMP060R
程式日期：105/05/11
程式作者：Jim.Lin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE060R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

System.out.println(">> " + user.getOrganID());

if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
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
<script language="javascript" src="changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
		alertStr += checkEmpty(form1.enterOrg,"管理機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
	}else if(form1.state.value=="exportAll") {
		alertStr += checkEmpty(form1.sDestField,"目的欄位");
	}	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
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
	    
    for ( i = 0; i < m1len ; i++){
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
	for ( var i = 0; i < document.all.sDestField.length ; i++){
		document.all.sDestField.options[i].selected = true;
	}
}

function clickExportAll() {
	var alertStr = "";
	alertStr += checkQuery();	
	selectAllField();	
	
	if(form1.state.value == "queryAllSuccess" || form1.state.value == "exportAll") {	
		//...
	} else {
		alertStr += "請先執行查詢!\n";
	}

	//alertStr += checkEmpty(form1.sDestField,"目的欄位");
	if(form1.sDestField.value == "") {
		alertStr += "目的欄位不允許空白!\n";
	}		
	
	if(alertStr.length != 0) { 
		alert(alertStr); 
		return false; 
	} else {
		if(confirm("您確定將列表上勾選的資料全部輸出?")){
			document.all.state.value='exportAll';
			form1.submit();
		}	
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

		if(<%=obj.getCount()%>==65535)
			alert("資料過多，請重新設置查詢條件，報表僅能顯示65535筆");
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
	moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">物品基本資料與管理資料</a><br><br>');	
	moshe.document.write('</body></html>');	
	form1.state.value="queryAllSuccess";	
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function changePropertyNo(propertyNo){}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField();">
<!--隱藏欄位===========================================================-->
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">	
<input class="field_Q" type="hidden" name="q_verify" value="Y">			
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
      <td class="queryTDLable" >入帳機關：</td>
      <td class="queryTDInput" >
			<!-- <input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>"> -->
			<!-- [<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]-->
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N')" value="..." title="機關輔助視窗">
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
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
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
      <td class="queryTDLable">物品區分別 :</td>
      <td class="queryTDInput">
      	<select class="field_Q" type="select" name="q_differenceKind">
         	<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getQ_differenceKind())%>
        </select>      
      </td>
      <td class="queryTDLable">物品別名 :</td>
      <td class="td_form_white">
      	<input class="field_Q" type="text" name="q_propertyName1" size="40" maxlength="40" value="<%=obj.getQ_propertyName1()%>">
      </td>
   </tr>
   <tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_keepUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ",obj.getQ_keepUnit())%>
			</select>	
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<select class="field_Q" type="select" name="q_keeper">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_keeper())%>
	        </select>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_useUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ",obj.getQ_useUnit())%>
			</select>	
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<select class="field_Q" type="select" name="q_userNo">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_userNo())%>
			</select>
			<input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
			&nbsp;&nbsp;&nbsp;
			使用註記：
			<input class="field_Q" type="text" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place" size="10" maxlength="10" value="<%=obj.getQ_place()%>" onchange="queryPlaceName('q_enterOrg','q_place');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place','q_placeName')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_placeName" size="20" maxlength="20" value="<%=obj.getQ_placeName()%>">]		
		</td>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput">			
				<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
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
				<%=util.View.getOption("select a.codeID, a.codeName from SYSCA_Code a , SYSCA_FUNDORGAN b where a.codeKindID='FUD' and a.codeid=b.fundno"  , obj.getQ_fundType())%>	
			</select>
		</td>	
	</tr>		
	<tr>
		<td class="queryTDLable">單價：</td>
		<td class="queryTDInput" colspan = '3'>
			起<input class="field_Q" type="text" name="q_bookValueS" size="10" maxlength="15" value="<%=obj.getQ_bookValueS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_bookValueE" size="10" maxlength="15" value="<%=obj.getQ_bookValueE()%>">
		</td>		
			
	</tr>
	<tr>
		<td class="queryTDLable">經費來源：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_fundsSource">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getQ_fundsSource())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品來源-種類：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_sourceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKD' ", obj.getQ_sourceKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">資料狀態</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_dataState">
				<option value='1' selected>現存</option>
				<option value='2' >已減損</option>
			</select>
		</td>
	</tr>
<!--  	
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
-->
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
      <td><select class="field_Q" name="sSourceField" size="11" multiple>
      <%=obj.getFieldList(""+application.getInitParameter("ManageOrgID"), ""+user.getOrganID())%>
      </select></td>
      <td width="60" align="center">
      <input type='hidden' name="enterOrg" value="<%=obj.getEnterOrg()%>">
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
      <td>
      	<select class="field_Q" name="sDestField" size="11" multiple>
      	</select>
      </td>
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
	<input class="toolbar_default" type="button" followPK="false" name="exportAll" value="輸出Excel檔"  onClick="clickExportAll();">
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品編號-分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品名稱</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,false,false,false,false};
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
//				changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
//				changeQ_enterOrg(form1.q_enterOrg.value,'q_useUnit','');
				//changeQ_enterOrgAndKeeper();
			}
			break;				
	}
	return true;
}

</script>
</body>
</html>
