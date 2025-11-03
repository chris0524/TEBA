<!--
*<br>程式目的：非消耗品明細清冊查詢檔 
*<br>程式代號：untne028p
*<br>撰寫日期：103/09/17
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE028R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
//obj.setQ_ownership("1");
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
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField() {
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	alertStr += checkEmpty(form1.q_differenceKind,"物品區分別");
	selectAllField();
	if (alertStr.length!=0) { 
		alert(alertStr); 
		return false; 
	} else {
    	form1.action="untne028p.jsp";
		return true;
	}
}

function alteredEnterOrg() {	
}

function check_reset() {
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
}
function init() {

	var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, null, null, false, false);
	var dcc3 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keeperQuickly, form1.q_keeper, null, null, false, false);
	changeSelect();
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
	for ( i=0; i<m2len ; i++) {
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
	for ( var i = 0; i < document.all.q_sDestField.length ; i++){
		document.all.q_sDestField.options[i].selected = true;
	}
}

</script>

</head>
<body topmargin="10" onLoad="init()">
<form id="form1" name="form1" method="post" action="untne028p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<!--隱藏欄位===========================================================-->
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="q_verify" value="Y">
<!--==================================================================-->
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">物品明細清冊 <font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>物品區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_P", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">權屬：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]
			<br/>
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
		</td>			
	</tr>
	<tr>
		<td class="queryTDLable">珍貴物品：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable" >保管單位：</td>
		<td class="queryTDInput" ><%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '"
		+ user.getOrganID() + "' order by unitno ",
		"field", "form1", "q_keepUnit", "q_keepUnitQuickly",
		obj.getQ_keepUnit())%>
			<input class="field_Q" type="button" name="btn_q_keepUnit"
			onclick="popUnitNo(form1.organID.value,'q_keepUnit')"
			value="..." title="單位輔助視窗"></td>
	</tr>
	<tr>
		<td class="queryTDLable">保管人：</td>
		<td class="queryTDInput"><%=View.getSelectCtrlGroup(
		"select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '"
				+ user.getOrganID()
				+ "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",
		"field", "form1", "q_keeper", "q_keeperQuickly", "")%>
			<input class="field_Q" type="button" name="btn_q_keeper"
			onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..."
			title="人員輔助視窗"></td>
	</tr>

	<tr>
		<td class="queryTDLable">存置地點</td>
		<td class="td_form_white" colspan='3'>
			<table>
				<tr>
					<td><select class="field_Q" name="q_place1" size="11"
						multiple style="width: 220px;">
							<%=obj.getFieldList(
							"" + application.getInitParameter("ManageOrgID"),
							"" + user.getOrganID())%>
					</select></td>
					<td><input class="toolbar_default" id="but" type="button"
						followPK="false" name="btnSelect" value="--->"
						onClick="sSourceToDest();"> <br> <br> <input
						class="toolbar_default" id="but" type="button"
						followPK="false" name="btnUnSelect" value="<---"
						onClick="sDestToSource();"></td>
					<td><select class="field_Q" name="q_sDestField"
						id="q_sDestField" size="11" multiple style="width: 220px;"></select>
					</td>
				</tr>
			</table>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >			
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
