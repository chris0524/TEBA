<!--
程式目的：土地基本資料與管理資料挑檔作業
程式代號：UNTLA046R
程式日期：0940829
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA046R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
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
		exportPopup();
	}	
}

function exportPopupOld(){
	moshe=window.open('','MyWindow','scrollbars=1, resizable=yes, status=no, toolbar=no,menubar=no,width=400,height=200');
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 資料輸出 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="0" marginwidth="0" marginheight="0">\n');
	moshe.document.write('<div align=center><br><h>資料輸出成功!</h><br><br></div><font size=2>');    
	moshe.document.write('請在下列檔案連結上，按下滑鼠右鍵選擇<font color="#CC0000">另存目標</font> <font size="2">(Save Target As) </font>，然後存檔即可。若作業系統是XP 或 windows 2003，存檔時請將.htm的副檔名改為.csv。<br><br>');
	moshe.document.write('輸出檔案下載 --> <a href="../../exportFolder/untla046r.csv">土地基本資料與管理資料挑檔作業</a><br><br></font>');	
	moshe.document.write('</body></html>');	
	form1.state.value="queryAllSuccess";	
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
	moshe.document.write('<br>輸出檔案下載 --> <a href="' + url + '">土地基本資料與管理資料</a><br><br>');	
	moshe.document.write('</body></html>');	
	form1.state.value="queryAllSuccess";	
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}



function popPlace(queryValue,popPlace,popPlaceName){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popPlace.jsp?popPlace="+popPlace+"&queryValue="+queryValue+"&popPlaceName="+popPlaceName,"",prop);
}
function changePropertyNo(propertyNo){}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField();">

<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
      <td class="queryTDLable" >入帳機關 :</td>
      <td colspan="3" class="queryTDInput" ><%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N")%> 　
      	權屬：<select class="field_Q" type="select" name="q_ownership">
            <%=util.View.getOnwerOption(obj.getQ_ownership())%>
            </select><input type="hidden" name="q_verify" value="Y"></td>
	  </tr>
	<tr>
      <td class="queryTDLable">財產編號 :</td>
      <td class="queryTDInput" colspan="3">
		      起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
		      訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
      </td>
	</tr>
	<tr>
      <td class="queryTDLable">財產分號 :</td>
      <td class="queryTDInput">
	      	起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);" >&nbsp;~&nbsp;
	      	訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);" >
      </td>
      <td class="queryTDLable">入帳日期 :</td>
      <td class="queryTDInput">
		      起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
		      訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%> 
      </td>
	</tr>
	<tr>
      <td class="queryTDLable">土地標示 :</td>
      <td class="queryTDInput" colspan="3"><select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
          <%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
        </select>
          <select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
            <script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
          </select>
          <select class="field_Q" type="select" name="q_signNo3">
            <script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
          </select>
		&nbsp;地號：
		 <input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=obj.getQ_signNo4()%>" onchange="getFrontZero(this.name,4);" >&nbsp;-&nbsp;
         <input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=obj.getQ_signNo5()%>" onchange="getFrontZero(this.name,4);">      
      </td>
	</tr>
	<tr>
      <td class="queryTDLable">財產區分別 :</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_differenceKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getQ_differenceKind())%>
        </select>      </td>
		<td class="queryTDLable">財產別名 :</td>
		<td class="td_form_white"><input class="field_Q" type="text" name="q_propertyName1" size="40" maxlength="40" value="<%=obj.getQ_propertyName1()%>"></td>
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
			[<input class="field_QRO" type="text" name="q_placeName" size="20" maxlength="20" value="<%=obj.getQ_placeName()%>">]	
		</td>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput">			
				<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">工程編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_engineeringNo" size="15" maxlength="20" value="<%=obj.getQ_engineeringNo()%>">
			<input class="field_Q" type="button" name="btn_engineeringNo" onclick="popEngineering(form1.organID.value,'q_engineeringNo','q_engineeringNoName');" value="..." title="工程編號輔助視窗">
			[<input class="field_PRO" type="text" name="q_engineeringNoName" size="30" maxlength="50" value="<%=obj.getQ_engineeringNoName()%>">]
       	</td>
	</tr>
	<tr>
      <td class="queryTDLable">財產性質 :</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_propertyKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
        </select>      </td>
      <td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD'", obj.getQ_fundType())%>
			</select> 
        </td>
	  </tr>
	<tr>
      <td class="queryTDLable">珍貴財產 :</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_valuable">
          <%=util.View.getYNOption(obj.getQ_valuable())%>
        </select>      </td>
      <td class="queryTDLable">抵繳遺產稅 :</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_taxCredit">
          <%=util.View.getYNOption(obj.getQ_taxCredit())%>
        </select>      </td>
	  </tr>
	<tr>
		<td class="queryTDLable">經費來源 :</td>
		<td class="queryTDInput" colspan="3"><select class="field_Q" type="select" name="q_fundsSource">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", obj.getQ_fundsSource())%>
		</select></td>
	</tr>
	<tr>
		<td class="queryTDLable">財產來源 :</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_sourceKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SKD' ", obj.getQ_sourceKind())%>
			</select>
		</td>
	</tr>
	<tr>
	  <td class="queryTDLable">地上物情形 :</td>
	  <td class="queryTDInput">
	  <select class="field_Q" type="select" name="q_useState1">
          <%//=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UST' ", obj.getQ_useState())%>
      <option value="">請選擇</option>
      <option value="01">空置</option>
      <option value="02">建物</option>
      <option value="03">農作</option>
      <option value="04">其他</option>
      </select>
      </td>
	  <td class="queryTDLable">地目 :</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_field">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FIE' ", obj.getQ_field())%>
        </select>      </td>
	</tr>
	
	<tr>
      <td class="queryTDLable">土地現況：</td>
      <td class="queryTDInput" colspan="3">
      	<select class="field_Q" type="select" name="q_useState">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UST' ", obj.getQ_useState())%>
		</select>
      </td>
	</tr>
	
	<tr>
		<td class="queryTDLable"><font size="2">整筆面積(㎡)</font> :</td>
		<td class="queryTDInput">
				起<input class="field_Q" type="text" name="q_areaS" size="10" maxlength="10" value="<%=obj.getQ_areaS()%>">&nbsp;~&nbsp;
				訖<input class="field_Q" type="text" name="q_areaE" size="10" maxlength="10" value="<%=obj.getQ_areaE()%>">
		</td>
	    <td class="queryTDLable"><font size="2">權利範圍面積<font size="1">(㎡)</font></font> :</td>
	    <td class="queryTDInput">
			    起<input class="field_Q" type="text" name="q_holdAreaS" size="10" maxlength="10" value="<%=obj.getQ_holdAreaS()%>">&nbsp;~&nbsp;
			    訖<input class="field_Q" type="text" name="q_holdAreaE" size="10" maxlength="10" value="<%=obj.getQ_holdAreaE()%>">
	    </td>
	</tr>
	<tr>
	  <td class="queryTDLable"><font size="2">原值(總價)</font> :</td>
	  <td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_bookValueS" size="10" maxlength="10" value="<%=obj.getQ_bookValueS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_bookValueE" size="10" maxlength="10" value="<%=obj.getQ_bookValueE()%>">
	  </td>
	  <td class="queryTDLable"><font size="2">所有權登記原因</font> :</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_ownershipCause">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='OCA' ", obj.getQ_ownershipCause())%>
      </select></td>
	</tr>
	<tr>
      <td class="queryTDLable">使用分區 :</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_useSeparate">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getQ_useSeparate())%>
      </select></td>
	  <td class="queryTDLable"><font size="2">編定使用種類</font> :</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_useKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getQ_useKind())%>
      </select></td>
	  </tr>
	<tr>
      <td class="queryTDLable">使用關係 :</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_useRelation">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='URE' ", obj.getQ_useRelation())%>
      </select></td>
	  <td class="queryTDLable">使用面積(㎡) :</td>
	  <td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_useAreaS" size="10" maxlength="10" value="<%=obj.getQ_useAreaS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_useAreaE" size="10" maxlength="10" value="<%=obj.getQ_useAreaE()%>">
	  </td>
	 </tr>
	 
	 <tr>
	  <td class="queryTDLable">公告現值年月:</td>
		  <td class="queryTDInput"><select class="field_Q" type="select" name="q_bulletinDate">
          <%=util.View.getOption("select bulletinDate,bulletinDate as bulletinDateName from UNTLA_BulletinDate where bulletinKind='2' order by bulletinDate desc ", obj.getQ_bulletinDate())%>
      </select></td>
      <td class="queryTDLable">公告地價年月 :</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_bulletinDate1">
          <%=util.View.getOption(" select bulletinDate,bulletinDate as bulletinDateName from UNTLA_BulletinDate where bulletinKind='1' order by bulletinDate desc ", obj.getQ_bulletinDate1())%>
      </select></td>
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
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">		</td>
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
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">入帳機屬</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">財產編號-分號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">土地標示名稱</a></th>
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
<script type="text/javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "queryAll":
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				
			}
			break;			
	}
	return true;
}

</script>
</body>
</html>
