<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script language="javascript">
function checkYN(obj){
	if (obj.checked == true){
		obj.value='Y';
	} else {
		obj.value='N';
	}
}
</script>
<%
unt.pd.UNTPD001Q untpdQmp= (unt.pd.UNTPD001Q)request.getAttribute("UNTPD001Q");
%>

<table class="queryTable"  border="1">
<tr>
	<td nowrap class="queryTDLable" width="20%">入帳機關：</td>
	<td nowrap class="queryTDInput" >
		<%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
	</td>
    <td nowrap class="queryTDLable"><div align="right">權屬：</div>
    <td nowrap class="queryTDInput">
		<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untpdQmp.getQ_ownership())%>
		</select>
	</td>
</tr>
<tr>
	<td nowrap class="queryTDLable" >財產區分別：</td>
	<td nowrap class="queryTDInput" >
		<select class="field_Q" type="select" name="q_differenceKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK'" ,untpdQmp.getQ_differenceKind())%>
		</select>
	</td>
    <td nowrap class="queryTDLable">財產大類：</td>
    <td nowrap class="queryTDInput">
		<select class="field_Q" type="select" name="q_propertyType">
			<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid = 'PTT' order by codeid"  ,untpdQmp.getQ_propertyType())%>
		</select>
	</td>
</tr>
<tr>
	<td nowrap class="queryTDLable">財產編號：</td>
	<td nowrap colspan="3" class="queryTDInput">
		起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untpdQmp.getQ_propertyNoS(),untpdQmp.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
		訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untpdQmp.getQ_propertyNoE(),untpdQmp.getQ_propertyNoEName(),"")%>
	</td>
</tr>
<tr>
	<td nowrap class="queryTDLable">財產分號：</td>
	<td nowrap class="queryTDInput">
		起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untpdQmp.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untpdQmp.getQ_serialNoE()%>" onChange="addChar(this ,7);">
	</td>
	<td nowrap class="queryTDLable">購置日期：</td>
	<td nowrap class="queryTDInput">
		起<%=util.View.getPopCalndar("field_Q","q_buyDateS",untpdQmp.getQ_buyDateS())%>&nbsp;~&nbsp;
		訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",untpdQmp.getQ_buyDateE())%>
	</td>
</tr>
<tr>
	<td nowrap class="queryTDLable">條碼：</td>
	<td nowrap class="queryTDInput">
		<input class="field_Q" type="text" name="q_barcode" size="24" maxlength="24" value="<%=untpdQmp.getQ_barcode()%>">
	</td>
	<td nowrap class="queryTDLable">盤點結果：</td>
	<td nowrap class="queryTDInput">
		<select class="field_Q" type="select" name="q_checkResult">
			<%=util.View.getTextOption("1;盤點正常;2;盤點異常;3;未辦理盤點",untpdQmp.getQ_checkResult())%>			
		</select>
	</td>
</tr>
<tr>
	<td nowrap class="queryTDLable">原財產編號：</td>
	<td nowrap class="queryTDInput">
		<input class="field_Q" type="text" name="q_oldPropertyNo" size="20" maxlength="50" value="<%=untpdQmp.getQ_oldPropertyNo()%>">
	</td>
	<td nowrap class="queryTDLable">原財產分號：</td>
	<td nowrap class="queryTDInput">
		起<input class="field_Q" type="text" name="q_oldSerialNoS" size="15" maxlength="30" value="<%=untpdQmp.getQ_oldSerialNoS()%>">&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_oldSerialNoE" size="15" maxlength="30" value="<%=untpdQmp.getQ_oldSerialNoE()%>">
	</td>
</tr>
<tr>
	<td nowrap class="queryTDLable">財產性質：</td>
	<td nowrap class="queryTDInput">
		<select class="field_Q" type="select" name="q_propertyKind">
			<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid='PKD' ",untpdQmp.getQ_propertyKind())%>
		</select>
	</td>
	<td nowrap class="queryTDLable">基金財產：</td>
	<td nowrap class="queryTDInput">
		<select class="field_Q" type="select" name="q_fundType">
		<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid='FUD' ",untpdQmp.getQ_fundType())%>
<!--			<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untpdQmp.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>-->
		</select>
	</td>
</tr>
	<tr>
		<td nowrap class="queryTDLable">移動資料：</td>
		<td nowrap class="queryTDInput" colspan="3">
			保管單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untpdQmp.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", untpdQmp.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untpdQmp.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", untpdQmp.getQ_keeper()) %>
			<input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untpdQmp.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly", untpdQmp.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untpdQmp.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", untpdQmp.getQ_userNo()) %>
			<input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
			&nbsp;&nbsp;&nbsp;
			<br>
			使用註記
			<input type="text" class="field_Q" name="q_userNote" value="<%=untpdQmp.getQ_userNote()%>" size="20">
			<br>
			存置地點
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=untpdQmp.getQ_place1()%>" onchange="queryPlaceName('enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place1','q_placeName1')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_placeName1" size="20" maxlength="20" value="<%=untpdQmp.getQ_place1Name()%>">]
			<br>		
			存置地點說明
			<input class="field_Q" type="text" name="q_place" size="30" maxlength="50" value="<%=untpdQmp.getQ_place()%>">
		</td>
	</tr>
	<tr>
		<td nowrap class="queryTDLable">盤點結果：</td>
		<td nowrap class="queryTDInput" colspan="3">		
			<%if("Y".equals(untpdQmp.getQ_scrappednote())){ %>
				<input type="checkbox" class="field_Q" name="q_scrappednote" onchange="checkYN(this);" checked >
			<% } else { %>
				<input type="checkbox" class="field_Q" name="q_scrappednote" onchange="checkYN(this);">
			<% } %>
			報廢註記
			&nbsp;&nbsp;&nbsp;
			<%if("Y".equals(untpdQmp.getQ_labelnote())){ %>
				<input type="checkbox" class="field_Q" name="q_labelnote" onchange="checkYN(this);" checked >
			<% } else { %>
				<input type="checkbox" class="field_Q" name="q_labelnote" onchange="checkYN(this);">
			<% } %>
			補印標籤註記
			&nbsp;&nbsp;&nbsp;
			<%if("Y".equals(untpdQmp.getQ_movenote())){ %>
				<input type="checkbox" class="field_Q" name="q_movenote" onchange="checkYN(this);" checked >
			<% } else { %>
				<input type="checkbox" class="field_Q" name="q_movenote" onchange="checkYN(this);">
			<% } %>
			移動註記
		</td>
	</tr>
	<tr>
		<td nowrap class="queryTDInput" colspan="4" style="text-align:center;">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
				<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
