<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript">
function checkYN(obj){
	if (obj.checked == true){
		obj.value='Y';
	} else {
		obj.value='N';
	}
}
</script>
<%
	unt.pd.UNTPD012Q untpdQmp= (unt.pd.UNTPD012Q)request.getAttribute("UNTPD012Q");
%>
<script>
		function changeAll(){
//			changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
//			changeQ_enterOrg(form1.q_enterOrg.value,'q_useUnit','');
//			changeQ_enterOrgAndKeeper();
			changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
		}
		
		function changeQ_enterOrgAndKeeper(){
//			getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');
//			getKeeper(form1.q_enterOrg, this, form1.q_userNo, '');
		}
</script>
<table class="queryTable"  border="1">
<tr>
	<td class="queryTDLable" >入帳機關：</td>
	<td class="queryTDInput" >
		<%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
	</td>
    <td class="queryTDLable"><div align="right">權屬：</div>
    <td class="queryTDInput">
		<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untpdQmp.getQ_ownership())%>
		</select>
	</td>
</tr>
<tr>
	<td class="queryTDLable" >物品區分別：</td>
	<td class="queryTDInput" colspan="3">
		<%=util.View._getSelectHTML("field_Q", "q_differenceKind", untpdQmp.getQ_differenceKind(),"DFK") %>		
	</td>
</tr>
<tr>
	<td class="queryTDLable">物品編號：</td>
	<td colspan="3" class="queryTDInput">
		起<input class="field_Q" type="text" name="q_propertyNoS" size="15" maxlength="15" value="<%=untpdQmp.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
		 	<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%= untpdQmp.getQ_propertyNoSName() %>">]&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_propertyNoE" size="15" maxlength="15" value="<%=untpdQmp.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%= untpdQmp.getQ_propertyNoEName() %>">]
	</td>
</tr>
<tr>
	<td class="queryTDLable">物品分號：</td>
	<td class="queryTDInput">
		起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untpdQmp.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untpdQmp.getQ_serialNoE()%>" onChange="addChar(this ,7);">
	</td>
	<td class="queryTDLable">購置日期：</td>
	<td class="queryTDInput">
		起<%=util.View.getPopCalndar("field_Q","q_buyDateS",untpdQmp.getQ_buyDateS())%>&nbsp;~&nbsp;
		訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",untpdQmp.getQ_buyDateE())%>
	</td>
</tr>
<tr>
	<td class="queryTDLable">條碼：</td>
	<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_barcode" size="24" maxlength="24" value="<%=untpdQmp.getQ_barcode()%>">
	</td>
	<td class="queryTDLable">盤點結果：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_checkResult">
			<%=util.View.getTextOption("1;盤點正常;2;盤點異常;3;未辦理盤點",untpdQmp.getQ_checkResult())%>			
		</select>
	</td>
</tr>
<tr>
	<td class="queryTDLable">原物品編號：</td>
	<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_oldPropertyNo" size="20" maxlength="50" value="<%=untpdQmp.getQ_oldPropertyNo()%>">
	</td>
	<td class="queryTDLable">原物品分號：</td>
	<td class="queryTDInput">
		起<input class="field_Q" type="text" name="q_oldSerialNoS" size="15" maxlength="30" value="<%=untpdQmp.getQ_oldSerialNoS()%>">&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_oldSerialNoE" size="15" maxlength="30" value="<%=untpdQmp.getQ_oldSerialNoE()%>">
	</td>
</tr>
<tr>
	<td class="queryTDLable">物品性質：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_propertyKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untpdQmp.getQ_propertyKind())%>
		</select>
	</td>
	<td class="queryTDLable">基金財產：</td>
	<td class="queryTDInput">
		<select class="field_Q" type="select" name="q_fundType">
			<%=util.View.getOption("select codeid, codename from SYSCA_CODE where codekindid='FUD' ",untpdQmp.getQ_fundType())%>
		</select>
	</td>
</tr>

	<tr>
		<td class="queryTDLable">移動資料：</td>
		<td class="queryTDInput" colspan="3">
			保管單位		
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" +Common.esapi(user.getOrganID())+ "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepunit", "q_keepunitQuickly",  untpdQmp.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepunit','q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人	        
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly",  untpdQmp.getQ_keeper() ) %>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper','q_userNo')" value="..." title="人員輔助視窗">
			<br>
			使用單位
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" +Common.esapi(user.getOrganID())+ "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly",  untpdQmp.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">			
			&nbsp;&nbsp;&nbsp;使用人
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly",  untpdQmp.getQ_userNo() ) %>
	        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
	        <br>
			使用註記
			<input type="text" class="field_Q" name="q_usernote" value="<%=untpdQmp.getQ_usernote()%>" size="20">
	        <br>
			存置地點
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=untpdQmp.getQ_place1()%>" onchange="queryPlaceName('enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=untpdQmp.getQ_place1Name()%>">]
			<br>		
			存置地點說明
			<input class="field_Q" type="text" name="q_place" size="30" maxlength="50" value="<%=untpdQmp.getQ_place()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">盤點結果：</td>
		<td class="queryTDInput" colspan="3">					
			<%if("Y".equals(untpdQmp.getQ_scrappedNote())){ %>
				<input type="checkbox" class="field_Q" name="q_scrappedNote" onchange="checkYN(this);" checked >
			<% } else { %>
				<input type="checkbox" class="field_Q" name="q_scrappedNote" onchange="checkYN(this);">
			<% } %>
			報廢註記
			&nbsp;&nbsp;&nbsp;
			<%if("Y".equals(untpdQmp.getQ_labelNote())){ %>
				<input type="checkbox" class="field_Q" name="q_labelNote" onchange="checkYN(this);" checked >
			<% } else { %>
				<input type="checkbox" class="field_Q" name="q_labelNote" onchange="checkYN(this);">
			<% } %>
			補印標籤註記
			&nbsp;&nbsp;&nbsp;
			<%if("Y".equals(untpdQmp.getQ_moveNote())){ %>
				<input type="checkbox" class="field_Q" name="q_moveNote" onchange="checkYN(this);" checked >
			<% } else { %>
				<input type="checkbox" class="field_Q" name="q_moveNote" onchange="checkYN(this);">
			<% } %>
			移動註記
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
