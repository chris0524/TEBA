<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">
$(function() {
	var dcc99 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
	var dcc98 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
}); 

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
function queryPlaceName(queryEnterOrg, queryPlace){
	//傳送JSON
	var comment={};	
	comment.enterOrg = document.all.item(queryEnterOrg).value;
	comment.place = document.all.item(queryPlace).value;
	
	$.post('../ch/queryPlaceName.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$("input[name='" + queryPlace + "Name']").val(data['placeName']);
			
		});	
}
function clearInsertKey() {
	$("input[name='i_enterOrg']").val("");
	$("input[name='i_ownership']").val("");
	$("input[name='i_caseNo']").val("");
	$("input[name='i_differenceKind']").val("");
}
function clearQueryOneKey() {
	$("input[name='queryone_enterOrg']").val("");
	$("input[name='queryone_ownership']").val("");
	$("input[name='queryone_caseNo']").val("");
	$("input[name='queryone_differenceKind']").val("");
}
//分頁初始化
function clearPage() {
	$("input[name='pageSize1']").val("");
	$("input[name='totalPage1']").val("");
	$("input[name='currentPage1']").val("");
	$("input[name='totalRecord1']").val("");
	$("input[name='recordStart1']").val("");
	$("input[name='recordEnd1']").val("");
	$("input[name='totalPage']").val("0");
	$("input[name='currentPage']").val("1");
	$("input[name='totalRecord']").val("0");
	$("input[name='recordStart']").val("0");
	$("input[name='recordEnd']").val("0");
}
</script>
<%
unt.ne.UNTNE024Q untneAdjustQuery = (unt.ne.UNTNE024Q)request.getAttribute("UNTNE024Q");
%>
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=untneAdjustQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢增減值單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="detail" <%=untneAdjustQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢增減值單明細資料</font>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untneAdjustQuery.getQ_enterOrg(),untneAdjustQuery.getQ_enterOrgName(),"N")%>
			<!-- <input class="field_Q" type="hidden" name="q_ownership" value="1"> -->
			<input class="field_Q" type="hidden" name="q_valuable" value="">
		</td>
	 	<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untneAdjustQuery.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"style="display:none;">電腦單號：</td>
		<td class="queryTDInput"style="display:none;">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untneAdjustQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untneAdjustQuery.getQ_caseNoE()%>">			
		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput"colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_adjustDateS",untneAdjustQuery.getQ_adjustDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_adjustDateE",untneAdjustQuery.getQ_adjustDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=untneAdjustQuery.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=untneAdjustQuery.getQ_propertyNoSName()%>">]&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=untneAdjustQuery.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=untneAdjustQuery.getQ_propertyNoEName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >物品分號：</td>
		<td class="queryTDInput" >
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untneAdjustQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untneAdjustQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">核准機關：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_approveOrg">
			<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ", "")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(untneAdjustQuery.getQ_verify())%>
			</select>					
		</td>
		<td class="queryTDLable">物品區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind", untneAdjustQuery.getQ_differenceKind(),"DFK") %>		
		</td>					
	</tr>
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untneAdjustQuery.getQ_propertyKind())%>
			</select>		
		</td>	
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", untneAdjustQuery.getQ_fundType(),"FUD", untneAdjustQuery.getQ_enterOrg()) %>
		</td>				
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput"colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untneAdjustQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untneAdjustQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable"style="display:none;">案名：</td>
		<td class="queryTDInput"style="display:none;">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untneAdjustQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">增減值單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=untneAdjustQuery.getQ_proofYear()%>">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untneAdjustQuery.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untneAdjustQuery.getQ_proofNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untneAdjustQuery.getQ_proofNoE()%>" onchange="getFrontZero(this.name,7);">號
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品別名：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_propertyName1" size="25" maxlength="25" value="<%=untneAdjustQuery.getQ_propertyName1()%>">		
		</td>		
	</tr>
	<tr>
		<td class="td_form">移動資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneAdjustQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", untneAdjustQuery.getQ_keepUnit()) %>
				<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneAdjustQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "form1.q_userNo.value = this.value;form1.q_userNo.onchange();", 
			                                                       "q_keeperQuickly", "", untneAdjustQuery.getQ_keeper()) %>
		        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper','q_userNo')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneAdjustQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "form1.q_keepUnit.value = this.value;form1.q_keepUnit.onchange();", 
			                                                       "q_useUnitQuickly", "", untneAdjustQuery.getQ_useUnit()) %>
				<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneAdjustQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "form1.q_keeper.value = this.value;form1.q_keeper.onchange();", 
			                                                       "q_userNoQuickly", "", untneAdjustQuery.getQ_userNo()) %>
		        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
		        <br/>
			          使用註記：
			    <input type="text" class="field" name="q_userNote" value="<%=untneAdjustQuery.getQ_userNote() %>" size="20">
		        <br>
				存置地點
				<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=untneAdjustQuery.getQ_place1()%>" onchange="queryPlaceName('q_enterOrg','q_place1');">
				<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			    [<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=untneAdjustQuery.getQ_place1Name()%>">]
				<br>		
				存置地點說明
				<input class="field_Q" type="text" name="q_place" size="30" maxlength="30" value="<%=untneAdjustQuery.getQ_place()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onClick="clearInsertKey();clearQueryOneKey();clearPage();">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	<tr style="display='none'">
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="field_Q" type="hidden" name="i_enterOrg" value="<%=untneAdjustQuery.getI_enterOrg()%>">
			<input class="field_Q" type="hidden" name="i_ownership" value="<%=untneAdjustQuery.getI_ownership()%>">
			<input class="field_Q" type="hidden" name="i_caseNo" value="<%=untneAdjustQuery.getI_caseNo()%>">
			<input class="field_Q" type="hidden" name="i_differenceKind" value="<%=untneAdjustQuery.getI_differenceKind()%>">
			<input class="field_Q" type="hidden" name="queryone_enterOrg" value="<%=untneAdjustQuery.getQueryone_enterOrg()%>">
			<input class="field_Q" type="hidden" name="queryone_ownership" value="<%=untneAdjustQuery.getQueryone_ownership()%>">
			<input class="field_Q" type="hidden" name="queryone_caseNo" value="<%=untneAdjustQuery.getQueryone_caseNo()%>">
			<input class="field_Q" type="hidden" name="queryone_differenceKind" value="<%=untneAdjustQuery.getQueryone_differenceKind()%>">
			<input class="field_Q" type="hidden" name="pageSize1" value="<%=untneAdjustQuery.getPageSize1()%>">
			<input class="field_Q" type="hidden" name="totalPage1" value="<%=untneAdjustQuery.getTotalPage1()%>">
			<input class="field_Q" type="hidden" name="currentPage1" value="<%=untneAdjustQuery.getCurrentPage1()%>">
			<input class="field_Q" type="hidden" name="totalRecord1" value="<%=untneAdjustQuery.getTotalRecord1()%>">
			<input class="field_Q" type="hidden" name="recordStart1" value="<%=untneAdjustQuery.getRecordStart1()%>">
			<input class="field_Q" type="hidden" name="recordEnd1" value="<%=untneAdjustQuery.getRecordEnd1()%>">
		</td>
	</tr>
</table>
