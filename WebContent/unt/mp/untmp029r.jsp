<!--
*<br>程式目的：動產明細清冊報表檔 
*<br>程式代號：untmp029r
*<br>撰寫日期：94/12/8
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP029R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if("".equals(obj.getQ_ownership())){
	String defaultOWA = TCGHCommon.getOwnerShipDefault(user.getOrganID());
	obj.setQ_ownership(defaultOWA);
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
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
	selectAllField();
	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
    	form1.action="untmp029p.jsp";
		return true;
	}
}

function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "01"){
		document.all.q_fundType.disabled=false;
	}else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function alteredEnterOrg(){
    changeBureau(form1.q_enterOrg, form1.q_keepBureau, '');
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
	changeSelect();
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	changeSelect();
	form1.q_propertyKind.value='';
	form1.q_dataType.value='3';
}

function init(){
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
    for ( i = (m1len -1); i>=0; i--) {
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
function selectAllField() {
	for ( var i = 0; i < document.all.q_sDestField.length ; i++){
		document.all.q_sDestField.options[i].selected = true;
	}
}
</script>

</head>
<body topmargin="10" onLoad="init();">
<form id="form1" name="form1" method="post" action="untmp029p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="q_verify" value="Y">
</td></tr></table>
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">動產明細清冊 <font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg(),obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>財產區分別：</td>
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
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">			
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"3,4,5")%><br>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >保管單位：</td>
		<td class="queryTDInput" ><%=View.getSelectCtrlGroup(
		"select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '"
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
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
			
			<input type="hidden" name="q_balanceDate" value="<%=util.Datetime.getYYYMMDD()%>">
			<input type="hidden" name="q_dataType" value="3">
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
