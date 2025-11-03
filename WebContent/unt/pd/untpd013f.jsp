<!--
程式目的：非消耗品產生盤點資料
程式代號：untpd013f.jsp
程式日期：2007/11/12
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD013F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	if ("del_addPdDate".equals(obj.getState())) {
		obj.del_addPdDate(user);
	}else if("re_addPdDate".equals(obj.getState())){
		obj.re_addPdDate(user);
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
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkDate(form1.q_buyDateS,"購置日期起");	
	alertStr += checkDate(form1.q_buyDateE,"購置日期訖");
	alertStr += checkDate(form1.q_enterDateS,"入帳日期起");
	alertStr += checkDate(form1.q_enterDateE,"入帳日期訖");
	selectAllField();
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else if(form1.buttonName.value=="del_add"){
		if (confirm("是否確定清除盤點資料並重新產製?")){
			$('input[name="del_add"]').attr('disabled',true);
			form1.state.value="del_addPdDate";
		}else{
			form1.buttonName.value = "";
			return false; 
		}
	}else if(form1.buttonName.value=="today_add"){
			$('input[name="today_add"]').attr('disabled',true);
			form1.state.value="re_addPdDate";
	}else{
			return false; 
	}
	//beforeSubmit();
}
function init(){
	if (form1.state.value == 'addPDdateSuccess'){
	opener.form1.submit();
	form1.state.value = "init";
		form1.q_libraryCheck.value='';
		form1.q_ownership.value='';
		form1.q_propertyNoS.value='';
		form1.q_propertyNoE.value='';
		form1.q_propertyNoSName.value='';
		form1.q_propertyNoEName.value='';
		form1.btn_q_propertyNoS.value='';
		form1.btn_q_propertyNoE.value='';
		form1.btn_q_propertyNoS.value='';
		form1.btn_q_propertyNoE.value='';
		form1.q_buyDateS.value='';
		form1.q_buyDateE.value='';
		form1.btn_q_buyDateS.value='';
		form1.btn_q_buyDateE.value='';
		form1.q_enterDateS.value='';
		form1.q_enterDateE.value='';
		form1.btn_q_enterDateS.value='';
		form1.btn_q_enterDateE.value='';
		
		form1.q_keepUnit.value='';
		form1.q_keeper.value='';
		
		form1.q_useUnit.value='';
		form1.q_userNo.value='';
		form1.submit();
		
		$('input[name="del_add"]').attr('disabled',false);
		$('input[name="today_add"]').attr('disabled',false);
	
	//window.close();
	}else{
//	form1.q_enterOrgName.value="<%=obj.orgName()%>";
	form1.q_dataState.value="1";
	form1.q_dataState.disabled=true;
		allCheckYN();
	}
	if(<%=obj.getMinCloseDate()%> == false){
		form1.today_add.disabled=true;
	}
}

//若全盤只開放包含圖晝欄位供選擇
function allCheckYN(){
	if (form1.q_allCheck.value == 'Y'){
		form1.q_libraryCheck.value='';
		form1.q_ownership.disabled=true;
		//form1.q_verify.disabled=true;
		form1.q_propertyNoS.disabled=true;
		form1.q_propertyNoE.disabled=true;
			form1.btn_q_propertyNoS.disabled=true;
			form1.btn_q_propertyNoE.disabled=true;
		form1.q_buyDateS.disabled=true;
		form1.q_buyDateE.disabled=true;
			form1.btn_q_buyDateS.disabled=true;
			form1.btn_q_buyDateE.disabled=true;
		form1.q_enterDateS.disabled=true;
		form1.q_enterDateE.disabled=true;
			form1.btn_q_enterDateS.disabled=true;
			form1.btn_q_enterDateE.disabled=true;
		form1.q_keepUnit.disabled=true;
		form1.q_keeper.disabled=true;
		form1.q_useUnit.disabled=true;
		form1.q_userNo.disabled=true;
		form1.q_place1.disabled=true;
	}else {
		form1.q_libraryCheck.value='N';
		form1.q_ownership.disabled=false;
		//form1.q_verify.disabled=false;
		form1.q_propertyNoS.disabled=false;
		form1.q_propertyNoE.disabled=false;
			form1.btn_q_propertyNoS.disabled=false;
			form1.btn_q_propertyNoE.disabled=false;
		form1.q_buyDateS.disabled=false;
		form1.q_buyDateE.disabled=false;
			form1.btn_q_buyDateS.disabled=false;
			form1.btn_q_buyDateE.disabled=false;
		form1.q_enterDateS.disabled=false;
		form1.q_enterDateE.disabled=false;
			form1.btn_q_enterDateS.disabled=false;
			form1.btn_q_enterDateE.disabled=false;
		form1.q_keepUnit.disabled=false;
		form1.q_keeper.disabled=false;
		form1.q_useUnit.disabled=false;
		form1.q_userNo.disabled=false;
		form1.q_place1.disabled=false;
	}
}

function selectAllField() {
	for ( var i = 0; i < document.all.q_sDestField.length ; i++){
		document.all.q_sDestField.options[i].selected = true;
	}
}

function sSourceToDest() {
	
	m1 = document.all.q_place1;
	m2 = document.getElementById("q_sDestField");//存放選擇的值
    m1len = document.all.q_place1.length;
	var alertStr="";
	if(alertStr.length!=0){ alert(alertStr); return false; }
	    //將選擇的值  放到右邊的select內
    for ( i = 0; i < m1len ; i++){
        if (m1.options[i].selected == true ) {
            m2len = document.getElementById("q_sDestField").length;
            m2.options[m2len]= new Option(m1.options[i].text, m1.options[i].value);
        }
    }
		//刪除已選擇的值
    for ( i = (m1len -1); i>=0; i--){
        if (m1.options[i].selected == true ) {
            m1.options[i] = null;
        }
    }

		
}




function sDestToSource() {
	m1 = document.all.q_place1;
	m2 = document.all.q_sDestField;
    m2len = document.all.q_sDestField.length;
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


</script>
</head>

<body topmargin="0" onLoad="showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
<table class="queryTable"  border="1">
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="buttonName" value="">
<input class="field_Q" type="hidden" name="q_verify" value="Y">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">產生物品盤點資料</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
			
		</td>
	</tr>
	
	<tr>
		<td class="queryTDLable">全盤：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_allCheck" onChange="allCheckYN();">
			<option value='Y' >是</option>
			<option value='N' >否</option>
			</select>
		包含圖書：
			<select class="field_Q" type="select" name="q_libraryCheck">
			<option value='' >請選擇</option>
			<option value='Y' >是</option>
			<option value='N' >否</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">權屬：</div>
		<td class="queryTDInput">
	      	<select class="field_Q" type="select" name="q_ownership">
	          <%=util.View.getOnwerOption(obj.getQ_ownership())%>
	        </select>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">資料狀態：</div></td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState" >
				<%=util.View.getTextOption("1;現存;2;已減損",obj.getQ_dataState())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="物品編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]
			<br />
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="物品編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
		</td>
    </tr>
	<tr>
		<td class="queryTDLable">購置日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_buyDateS",obj.getQ_buyDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",obj.getQ_buyDateE())%>
		</td>		
    </tr> 
	<tr>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
   </tr>
	
	<tr>
		<td class="queryTDLable">移動資料：</td>
		<td class="queryTDInput" colspan="3">
			保管單位
			<select class="field_Q" type="select" name="q_keepUnit">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getQ_keepUnit())%>
			</select>			
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			 <select class="field_Q" type="select" name="q_keeper">
			 	<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_keeper())%>
			</select>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper','q_userNo')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<select class="field_Q" type="select" name="q_useUnit">
			　　<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getQ_useUnit())%>
			</select>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">	
			&nbsp;&nbsp;&nbsp;使用人
			<select class="field_Q" type="select" name="q_userNo">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_userNo())%>
			</select>
	        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	
	
	<tr>
		<td  class="queryTDLable">存置地點</td>
		<td class="td_form_white"colspan='3'>
		<table>
		<tr>
		<td>
		<select class="field_Q" name="q_place1" size="11" multiple>
      		<%=obj.getFieldList(""+application.getInitParameter("ManageOrgID"), ""+user.getOrganID())%>
    	</select>
    	</td>
    	<td>
		<input class="toolbar_default" id="but" type="button" followPK="false" name="btnSelect" value="--->"  onClick="sSourceToDest();">
          <br>
          <br>
		<input class="toolbar_default" id="but" type="button" followPK="false" name="btnUnSelect" value="<---"  onClick="sDestToSource();">
		</td>
		<td>
		<select class="field_Q" name="q_sDestField" id="q_sDestField" size="11" multiple style="width:220px;"></select>
		</td>
     	</tr>
     	</table>
      </tr>
		<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="del_add" value="清除重新產製" onClick="form1.buttonName.value=form1.del_add.name;">
			<input class="toolbar_default" followPK="false" type="submit" name="today_add" value="累加產製" onClick="form1.buttonName.value=form1.today_add.name;">
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" onClick="form1.submit()" >
			<input class="toolbar_default" followPK="false" type="reset" name="closeWin" value="關閉視窗" onClick="window.close();">
		</td>
</tr>
</table>
</td></tr>
</table>
</form>
</body>
</html>



