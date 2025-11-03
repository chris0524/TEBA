<!--
*<br>程式目的：物品保保管單位別清冊查詢檔 
*<br>程式代號：untne029r
*<br>撰寫日期：103/09/18
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE029R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setQ_ownership("1");
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
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("q_signNo1","S000000")
//);
function checkField(){
    //return true;
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");

	var err1=checkAllValue( [form1.q_propertyNoS,form1.q_propertyNoE] ,["財產編號－起","財產編號－訖"] );
	var err2=checkEmpty( form1.q_keepUnitQuickly,"保管單位" );
	
	if(err1 != "" && err2 !=""){
		alertStr += "財產編號起－訖、保管單位 \n須任一組都有值!";
	}	
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	return true;
	//beforeSubmit();	
    //form1.submit();
}

function changeSelect(){
	//財產性質為「03:事業用」時，須控制「基金財產」資料
	if(form1.q_propertyKind.value == "03"){ 
		document.all.q_fundType.disabled=false;
	}else{
		document.all.q_fundType.disabled=true;
		form1.q_fundType.value="";
	}
}

function changeAll(){
	changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
}

function alteredEnterOrg(){
	//changeAll();
    //changeBureau(form1.q_enterOrg, form1.q_keepBureau, '');
	//changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'');
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
	form1.q_propertyKind.value='';
	changeSelect();
}

function popUnitNo(queryValue,popUnitNo,popUnitNo2,popUnitNo3,popUnitNo4){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../home/popUnitNo.jsp?popUnitNo="+popUnitNo+"&queryValue="+queryValue+"&popUnitNo2="+popUnitNo2+"&popUnitNo3="+popUnitNo3+"&popUnitNo4="+popUnitNo4,"",prop);
}

$(function(){
	$('select[name="q_keepUnit"]').bind('change',function(){
		form1.q_keepUnitQuickly.value = $(this).val();
	});
});

function init() {
	var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, false, false);
	
	form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
}
</script>
</head>
<body topmargin="10" onLoad="init();">
<form id="form1" name="form1" method="post" action="untne029p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
<!--隱藏欄位===========================================================-->
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="q_dataState" value="1">
<input type="hidden" name="q_verify" value="Y">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">	
<input type="hidden" name="objPath" >
<!-- <input class="field_QRO" type="hidden" name="q_ownership" value="1"> -->
<!--==================================================================-->
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">物品保管單位別清冊<font color="red">(A4 橫式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getQ_enterOrg().equals("")?user.getOrganID():obj.getQ_enterOrg()%>">
			[<input class="field_QRO" type="text" name="q_enterOrgName" size="20" maxlength="50" value="<%=obj.getQ_enterOrgName().equals("")?user.getOrganName():obj.getQ_enterOrgName()%>">]
			<input class="field_Q" type="button" name="btn_q_enterOrg" onclick="popOrgan('q_enterOrg','q_enterOrgName','N&js=alteredEnterOrg();')" value="..." title="機關輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	 
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">			
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "", 
			                                                       "q_keepUnitQuickly", "", obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_keepUnitQuickly');" value="..." title="單位輔助視窗">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">		
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=obj.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="18" maxlength="50" value="<%=obj.getQ_propertyNoSName()%>">]
			<br>訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=obj.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="18" maxlength="50" value="<%=obj.getQ_propertyNoEName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput" colspan="3">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind" onChange="changeSelect();">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<%=util.View.getOption("select a.codeID, a.codeName from SYSCA_Code a , SYSCA_FUNDORGAN b where a.codeKindID='FUD' and a.codeid=b.fundno"  , obj.getQ_fundType())%>	
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput">
			<input type="text" class="field_Q" name="q_propertyName1" value="<%=obj.getQ_propertyName1()%>" size="30" maxlength="30">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1()%>" onchange="queryPlaceName('q_enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place1" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name()%>">]		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴物品：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td>
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
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	</table>
</td></tr></table>	
</form>
</body>
</html>
