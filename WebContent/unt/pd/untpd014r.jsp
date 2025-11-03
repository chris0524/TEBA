<!--
*<br>程式目的：非消耗品盤點清冊
*<br>程式代號：untpd014p
*<br>撰寫日期：2014/09/30
*<br>程式作者：Mike Kao
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.pd.UNTPD014R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
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
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">

	var insertDefault; //二維陣列, 新增時, 設定預設值

	function checkField() {
		var alertStr = "";
		alertStr += checkEmpty(form1.q_enterOrg, "入帳機關");
		alertStr += checkLen(form1.q_noteText,"備註",200);
		selectAllField();
		if (alertStr.length != 0) {
			alert(alertStr);
			return false;
		} else {
			return true;
		}
	}

	function init() {
		var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, null, null, false, false);
		var dcc3 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keeperQuickly, form1.q_keeper, null, null, false, false);

		$('#signature').hide();
		$('#titleName').hide();
		$('#noteText').hide();
		
		$("input[type='text'][name='q_keeperQuickly']").change(function() {keeperChange()});
		$("select[name='q_keeper']").change(function() {keeperChange()});
		$("input[type='text'][name='q_keepUnitQuickly']").change (function() { keeperChange()});
		$("select[name='q_keepUnit']").change (function() { keeperChange()});
	}

	function keeperChange() {

		var q_keeperQuickly = $("input[type='text'][name='q_keeperQuickly']").val();
		var q_keepUnitQuickly = $("input[type='text'][name='q_keepUnitQuickly']").val();
		
		if ((q_keeperQuickly != null && q_keeperQuickly != "") || (q_keepUnitQuickly != null && q_keepUnitQuickly != "")) {
			$('#signature').show();
			$('#titleName').show();

		} else {
			$('#signature').hide();
			$('#titleName').hide();
		}

	}

	function sSourceToDest() {

		m1 = document.all.q_place1;
		m2 = document.getElementById("q_sDestField");//存放選擇的值
		m1len = document.all.q_place1.length;
		var alertStr = "";
		if (alertStr.length != 0) {
			alert(alertStr);
			return false;
		}
		//將選擇的值  放到右邊的select內
		for (i = 0; i < m1len; i++) {
			if (m1.options[i].selected == true) {
				m2len = document.getElementById("q_sDestField").length;
				m2.options[m2len] = new Option(m1.options[i].text,
						m1.options[i].value);
			}
		}
		//刪除已選擇的值
		for (i = (m1len - 1); i >= 0; i--) {
			if (m1.options[i].selected == true) {
				m1.options[i] = null;
			}
		}
	}

	function sDestToSource() {
		m1 = document.all.q_place1;
		m2 = document.all.q_sDestField;
		m2len = document.all.q_sDestField.length;
		for (i = 0; i < m2len; i++) {
			if (m2.options[i].selected == true) {
				m1len = m1.length;
				m1.options[m1len] = new Option(m2.options[i].text,
						m2.options[i].value);
			}
		}
		for (i = (m2len - 1); i >= 0; i--) {
			if (m2.options[i].selected == true) {
				m2.options[i] = null;
			}
		}
	}

	function selectAllField() {
		for (var i = 0; i < document.all.q_sDestField.length; i++) {
			document.all.q_sDestField.options[i].selected = true;
		}
	}
	
	function popUnitMan(queryValue,popUnitMan,popUnitMan2,popUnitMan3,popUnitMan4) {
    	var prop = "";
    	var windowTop = (document.body.clientHeight-400) / 2 + 180;
    	var windowLeft = (document.body.clientWidth-400) / 2 + 250;
    	prop = prop + "width=550,height=450,";
    	prop = prop + "top=" + windowTop + ",";
    	prop = prop + "left="+windowLeft + ",";
    	prop = prop + "scrollbars=no";
    	returnWindow = window.open("../../home/popUnitManByuntpd003r.jsp?popUnitMan="+popUnitMan+"&queryValue="+queryValue+"&popUnitMan2="+popUnitMan2+"&popUnitMan3="+popUnitMan3+"&popUnitMan4="+popUnitMan4,"",prop);
    }
	
	function popUnitNo(queryValue,popUnitNo,popUnitNo2,popUnitNo3,popUnitNo4){
    	var prop="";
    	var windowTop=(document.body.clientHeight-400)/2+180;
    	var windowLeft=(document.body.clientWidth-400)/2+250;
    	prop=prop+"width=550,height=450,";
    	prop=prop+"top="+windowTop+",";
    	prop=prop+"left="+windowLeft+",";
    	prop=prop+"scrollbars=no";
    	returnWindow=window.open("../../home/popUnitNoByuntpd003r.jsp?popUnitNo="+popUnitNo+"&queryValue="+queryValue+"&popUnitNo2="+popUnitNo2+"&popUnitNo3="+popUnitNo3+"&popUnitNo4="+popUnitNo4,"",prop);
    }
	
    function check(){
    	if($('#note').attr('checked')){
    		form1.q_note.value = "Y";
    		$('#noteText').show();
    	}else{
    		$('#noteText').hide();
    	}
    }
    
</script>
</head>

<body topmargin="0" onLoad="init()">
<form id="form1" name="form1" method="post" action="untpd014p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="80%" cellspacing="0" cellpadding="0" align="center">
<tr><td>
<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center" width='30%'>非消耗品盤點清冊</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput" colspan="3">
		<select class="field_Q" type="select" name="q_differenceKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' Order by codeID",
			obj.getQ_differenceKind())%>
		</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
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
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" +user.getOrganID()+ "' order by unitno ", 
			                                                       "field", "form1", "q_keepUnit", "q_keepUnitQuickly",  obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.q_enterOrg.value,'q_keepUnit')" value="..." title="單位輔助視窗">			                                                       
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">保管人：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" +user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field", "form1", "q_keeper", "q_keeperQuickly", "") %>
			<input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.q_enterOrg.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	
		<tr>
		<td  class="queryTDLable">存置地點</td>
		<td class="td_form_white"colspan='3'>
		<table>
		
		<tr>
		<td>
		<select class="field_Q" name="q_place1" size="11" multiple style="width:220px;">
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
		<td class="queryTDLable">換頁方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_chengPageStyle">
			<option value='1' >保管單位</option>
			<option value='2' >保管單位+保管人</option>
			<option value='3' >財產類別</option>
			<option value='4' >財產編號</option>
			<option value='5' >存置地點</option>
			<option value='6' >保管人+保管單位</option>
			</select>
		</td>
	</tr>

	<tr id = "signature">
		<td class = "queryTDLable">列印簽核欄：</td>
		<td class = "queryTDInput" >
		<select class = "field_Q" type = "select" name = "q_signature">
			<option value = 'Y'>是</option>
			<option value = 'N' selected>否</option>
		</select></td>
	</tr>
						
	<tr id = "titleName">
		<td class="queryTDLable">表頭名稱：</td>
		<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_titleName">
			<option value='1'>非消耗品盤點清冊</option>
			<option value='2'>非消耗品抽盤清冊</option>
		</select></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><input type="checkbox" id="note" name="q_note" value="N" onclick="check()">備註：</td>
		<td class="queryTDInput">
			<input type="text" id="noteText" name="q_noteText" style="width:100%;">
		</td>
	</tr>		
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
			<input type="hidden" name="userID" value="<%=user.getUserID()%>">
		</td>
	</tr>
	
</table>
</td></tr>
</table>
</form>
</body>
</html>



