<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<%
unt.ne.UNTNE008Q untneMoveQuery = (unt.ne.UNTNE008Q)request.getAttribute("UNTNE008Q");
%>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">
$(function() {
	var dcc97 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_oldKeepUnitQuickly, form1.q_oldKeepUnit, form1.q_oldUseUnitQuickly, form1.q_oldUseUnit, true, false);
	var dcc98 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_oldKeeperQuickly, form1.q_oldKeeper, form1.q_oldUserNoQuickly, form1.q_oldUserNo, true, false);
	var dcc99 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_newKeepUnitQuickly, form1.q_newKeepUnit, form1.q_newUseUnitQuickly, form1.q_newUseUnit, true, false);
	var dcc100 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_newKeeperQuickly, form1.q_newKeeper, form1.q_newUserNoQuickly, form1.q_newUserNo, true, false);
}); 

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
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="MoveProof" <%=untneMoveQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢移動單</font>
			<input name="querySelect" type="radio" class="field_Q" value="MoveDetail" <%=untneMoveQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢移動單明細</font>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untneMoveQuery.getQ_enterOrg(),untneMoveQuery.getQ_enterOrgName(), "N")%>	
		</td>
		<td class="queryTDLable"><div align="right">權屬：</div>
        <td class="queryTDInput">
	      	<select class="field_Q" type="select" name="q_ownership">
	          <%=util.View.getOnwerOption(untneMoveQuery.getQ_ownership())%>
	        </select>	        
	    </td>
	</tr>
	<tr>
		<td class="queryTDLable"style="display:none;">電腦單號：</td>
		<td class="queryTDInput"style="display:none;">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untneMoveQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untneMoveQuery.getQ_caseNoE()%>">			
		</td>	
		<td class="queryTDLable">移動日期：</td>
		<td class="queryTDInput"colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_moveDateS",untneMoveQuery.getQ_moveDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_moveDateE",untneMoveQuery.getQ_moveDateE())%>
		</td>		
	</tr>	
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=untneMoveQuery.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=untneMoveQuery.getQ_propertyNoSName()%>">]&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=untneMoveQuery.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=untneMoveQuery.getQ_propertyNoEName()%>">]
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untneMoveQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untneMoveQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
		<td class="queryTDLable">異動單確認：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untneMoveQuery.getQ_verify())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untneMoveQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
	<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", untneMoveQuery.getQ_fundType(),"FUD", untneMoveQuery.getQ_enterOrg()) %>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">原保管單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_oldKeepUnit", "q_oldKeepUnitQuickly", untneMoveQuery.getQ_oldKeepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_oldKeepUnit" onclick="popUnitNo(form1.organID.value,'q_oldKeepUnit','q_oldUseUnit')" value="..." title="單位輔助視窗">
		</td>
		<td class="queryTDLable">原保管人：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_oldKeeper", "q_oldKeeperQuickly", untneMoveQuery.getQ_oldKeeper()) %>
          	<input class="field_Q" type="button" name="btn_q_oldKeeper" onclick="popUnitMan(form1.organID.value,'q_oldKeeper','q_oldUserNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable"><div align="right">原使用單位：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_oldUseUnit", "q_oldUseUnitQuickly", untneMoveQuery.getQ_oldUseUnit()) %>
			<input class="field_Q" type="button" name="btn_q_oldUseUnit" onclick="popUnitNo(form1.organID.value,'q_oldUseUnit')" value="..." title="單位輔助視窗">
        </td>
	    <td class="queryTDLable"><div align="right">原使用人：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_oldUserNo", "q_oldUserNoQuickly", untneMoveQuery.getQ_oldUserNo()) %>
            <input class="field_Q" type="button" name="btn_q_oldUserNo" onclick="popUnitMan(form1.organID.value,'q_oldUserNo')" value="..." title="人員輔助視窗">  
        </td>
	</tr>
	<tr>
		<td class="queryTDLable">新保管單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_newKeepUnit", "q_newKeepUnitQuickly", untneMoveQuery.getQ_newKeepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_newKeepUnit" onclick="popUnitNo(form1.organID.value,'q_newKeepUnit','q_newUseUnit')" value="..." title="單位輔助視窗">
		</td>
		<td class="queryTDLable">新保管人：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_newKeeper", "q_newKeeperQuickly", untneMoveQuery.getQ_newKeeper()) %>
       	 	<input class="field_Q" type="button" name="btn_q_newKeeper" onclick="popUnitMan(form1.organID.value,'q_newKeeper','q_newUserNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable"><div align="right">新使用單位：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_newUseUnit", "q_newUseUnitQuickly", untneMoveQuery.getQ_newUseUnit()) %>
			<input class="field_Q" type="button" name="btn_q_newUseUnit" onclick="popUnitNo(form1.organID.value,'q_newUseUnit')" value="..." title="單位輔助視窗">
        </td>
	    <td class="queryTDLable"><div align="right">新使用人：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_newUserNo", "q_newUserNoQuickly", untneMoveQuery.getQ_newUserNo()) %>
        <input class="field_Q" type="button" name="btn_q_newUserNo" onclick="popUnitMan(form1.organID.value,'q_newUserNo')" value="..." title="人員輔助視窗">
        </td>
	</tr>				
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">		
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untneMoveQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untneMoveQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="25" value="<%=untneMoveQuery.getQ_caseName()%>">
		</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">移動單編號：</td>
		<td class="queryTDInput"  colspan="3">
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=untneMoveQuery.getQ_proofYear()%>">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untneMoveQuery.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untneMoveQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untneMoveQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind", untneMoveQuery.getQ_differenceKind(),"DFK") %>		
		</td>
		<td class="queryTDLable">物品別名：</td>
		<td class="queryTDInput"> 
			<input class="field_Q" type="text" name="q_propertyName1" size="25" maxlength="25" value="<%=untneMoveQuery.getQ_propertyName1()%>">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">原使用人註記：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_oldUserNote" value="<%=untneMoveQuery.getQ_oldUserNote() %>" size="20">
		</td>
		<td class="queryTDLable">原存置地點：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_oldPlace1" size="10" maxlength="10" value="<%=untneMoveQuery.getQ_oldPlace1()%>" onchange="queryPlaceName('q_enterOrg','q_oldPlace1');">
		<input class="field_Q" type="button" name="btn_q_oldPlace1" onclick="popPlace(form1.organID.value,'q_oldPlace1','q_oldPlace1Name')" value="..." title="存置地點輔助視窗">
		[<input class="field_RO" type="text" name="q_oldPlace1Name" size="20" maxlength="20" value="<%=untneMoveQuery.getQ_oldPlace1Name()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">原存置地點說明：</td>
		<td class="queryTDInput" colspan="3">
		<input class="field_Q" type="text" name="q_oldPlace" size="30" maxlength="30" value="<%=untneMoveQuery.getQ_oldPlace()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">新使用人註記：</td>
		<td class="queryTDInput">
		<input class="field_Q" type="text" name="q_newUserNote" value="<%=untneMoveQuery.getQ_newUserNote() %>" size="20">
		</td>
		<td class="queryTDLable">新存置地點：</td>
		<td class="queryTDInput">
        <input class="field_Q" type="text" name="q_newPlace1" size="10" maxlength="10" value="<%=untneMoveQuery.getQ_newPlace1()%>" onchange="queryPlaceName('q_enterOrg','q_newPlace1');">
		<input class="field_Q" type="button" name="btn_q_place1" onclick="popPlace(form1.organID.value,'q_newPlace1','q_newPlace1Name')" value="..." title="存置地點輔助視窗">
		[<input class="field_RO" type="text" name="q_newPlace1Name" size="20" maxlength="20" value="<%=untneMoveQuery.getQ_newPlace1Name()%>">]	
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">新存置地點說明：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_newPlace" size="30" maxlength="30" value="<%=untneMoveQuery.getQ_newPlace()%>">
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
			<input class="field_Q" type="hidden" name="i_enterOrg" value="<%=untneMoveQuery.getI_enterOrg()%>">
			<input class="field_Q" type="hidden" name="i_ownership" value="<%=untneMoveQuery.getI_ownership()%>">
			<input class="field_Q" type="hidden" name="i_caseNo" value="<%=untneMoveQuery.getI_caseNo()%>">
			<input class="field_Q" type="hidden" name="i_differenceKind" value="<%=untneMoveQuery.getI_differenceKind()%>">
			<input class="field_Q" type="hidden" name="queryone_enterOrg" value="<%=untneMoveQuery.getQueryone_enterOrg()%>">
			<input class="field_Q" type="hidden" name="queryone_ownership" value="<%=untneMoveQuery.getQueryone_ownership()%>">
			<input class="field_Q" type="hidden" name="queryone_caseNo" value="<%=untneMoveQuery.getQueryone_caseNo()%>">
			<input class="field_Q" type="hidden" name="queryone_differenceKind" value="<%=untneMoveQuery.getQueryone_differenceKind()%>">
			<input class="field_Q" type="hidden" name="pageSize1" value="<%=untneMoveQuery.getPageSize1()%>">
			<input class="field_Q" type="hidden" name="totalPage1" value="<%=untneMoveQuery.getTotalPage1()%>">
			<input class="field_Q" type="hidden" name="currentPage1" value="<%=untneMoveQuery.getCurrentPage1()%>">
			<input class="field_Q" type="hidden" name="totalRecord1" value="<%=untneMoveQuery.getTotalRecord1()%>">
			<input class="field_Q" type="hidden" name="recordStart1" value="<%=untneMoveQuery.getRecordStart1()%>">
			<input class="field_Q" type="hidden" name="recordEnd1" value="<%=untneMoveQuery.getRecordEnd1()%>">
		</td>
	</tr>
</table>

<script>

</script>	
