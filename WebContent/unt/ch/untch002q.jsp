<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<%
unt.ch.UNTCH002Q untmpMoveQuery = (unt.ch.UNTCH002Q)request.getAttribute("UNTCH002Q");
%>
<head>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">

$(function() {
	var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_oldKeepUnitQuickly, form1.q_oldKeepUnit, form1.q_oldUseUnitQuickly, form1.q_oldUseUnit, false, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_oldKeeperQuickly, form1.q_oldKeeper, form1.q_oldUserNoQuickly, form1.q_oldUserNo, false, false);
	var dcc3 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_newKeepUnitQuickly, form1.q_newKeepUnit, form1.q_newUseUnitQuickly, form1.q_newUseUnit, false, false);
	var dcc4 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_newKeeperQuickly, form1.q_newKeeper, form1.q_newUserNoQuickly, form1.q_newUserNo, false, false);
}); 

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
</head>

<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="proof" <%=untmpMoveQuery.getQueryTab1()%> onclick="initQ_Form(this.value);">
			&nbsp;<font color="green">查詢移動單</font>
			<input name="querySelect" type="radio" class="field_Q" value="detail" <%=untmpMoveQuery.getQueryTab2()%> onclick="initQ_Form(this.value);">
			&nbsp;<font color="green">查詢移動單明細</font>
		</td>
	</tr>	
	<tr name="div_q_proof">
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untmpMoveQuery.getQ_enterOrg(),untmpMoveQuery.getQ_enterOrgName(),"N")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untmpMoveQuery.getQ_ownership())%>
			</select>
		</td>		
	</tr>
	<tr name="div_q_proof">
		<td class="queryTDLable">移動日期：</td>
		<td class="queryTDInput" colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_moveDateS",untmpMoveQuery.getQ_moveDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_moveDateE",untmpMoveQuery.getQ_moveDateE())%>
		</td>		
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untmpMoveQuery.getQ_propertyNoS(),untmpMoveQuery.getQ_propertyNoSName(),"")%>
			<br>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untmpMoveQuery.getQ_propertyNoE(),untmpMoveQuery.getQ_propertyNoEName(),"")%>
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput" colspan="3">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untmpMoveQuery.getQ_serialNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untmpMoveQuery.getQ_serialNoE()%>" onchange="getFrontZero(this.name,7);">
		</td>			
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untmpMoveQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", untmpMoveQuery.getQ_fundType(),"FUD", untmpMoveQuery.getQ_enterOrg()) %>
		</td>		
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(untmpMoveQuery.getQ_valuable())%>
			</select>		
		</td>				
	</tr>	
	<!--  
	<tr name="div_q_proof">
		<td class="queryTDLable">工程編號：</td>
		<td class="queryTDInput" colspan="3">
       		<input class="field_Q" type="text" name="q_engineeringNo" size="10" maxlength="11" value="<%=untmpMoveQuery.getQ_engineeringNo()%>" onchange="queryEngineeringName('q_enterOrg','q_engineeringNo');">
			<input class="field_Q" type="button" name="btn_engineeringNo" onclick="popEngineering(form1.organID.value,'q_engineeringNo','q_engineeringNoName');" value="..." title="工程編號輔助視窗">
			[<input class="field_PRO" type="text" name="q_engineeringNoName" size="20" maxlength="50" value="<%=untmpMoveQuery.getQ_engineeringNoName()%>">]
       	</td>
	</tr>
	 -->
	<tr name="div_q_proof">
		<td class="queryTDLable">異動單確認：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untmpMoveQuery.getQ_verify())%>
			</select>
		</td>
	</tr>			
	<tr name="div_q_detail">
	    <td class="queryTDLable">原保管單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_oldKeepUnit", "q_oldKeepUnitQuickly", untmpMoveQuery.getQ_oldKeepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_oldKeepUnit" onclick="popUnitNo(form1.q_enterOrg.value,'q_oldKeepUnit')" value="..." title="單位輔助視窗">
		</td>
		<td class="queryTDLable"><div align="right">原保管人：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_oldKeeper", "q_oldKeeperQuickly", untmpMoveQuery.getQ_oldKeeper()) %>
	        <input class="field_Q" type="button" name="btn_q_oldKeeper" onclick="popUnitMan(form1.q_enterOrg.value,'q_oldKeeper')" value="..." title="人員輔助視窗">	        
        </td>
	</tr>
	<tr name="div_q_detail">
	    <td class="queryTDLable">原使用單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_oldUseUnit", "q_oldUseUnitQuickly", untmpMoveQuery.getQ_oldUseUnit()) %>
			<input class="field_Q" type="button" name="btn_q_oldUseUnit" onclick="popUnitNo(form1.q_enterOrg.value,'q_oldUseUnit')" value="..." title="單位輔助視窗">				        
		</td>
	    <td class="queryTDLable"><div align="right">原使用人：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_oldUserNo", "q_oldUserNoQuickly", untmpMoveQuery.getQ_oldUserNo()) %>
	        <input class="field_Q" type="button" name="btn_q_oldUserNo" onclick="popUnitMan(form1.q_enterOrg.value,'q_oldUserNo')" value="..." title="人員輔助視窗">
        </td>
	</tr>
	<tr name="div_q_detail">
	    <td class="queryTDLable"><div align="right">原使用人註記：</div></td>
	    <td class="queryTDInput">
	    	<input class="field_Q" type="text" name="q_oldUserNote" value="<%=untmpMoveQuery.getQ_oldUserNote() %>" size="20">
        </td>
	    <td class="queryTDLable"><div align="right">原存置地點：</div></td>
	    <td class="queryTDInput">
	    	<input class="field_Q" type="text" name="q_oldPlace" size="10" maxlength="10" value="<%=untmpMoveQuery.getQ_oldPlace()%>" onchange="queryPlaceName('q_enterOrg','q_oldPlace');">
			<input class="field_Q" type="button" name="btn_q_oldPlace" onclick="popPlace(form1.organID.value,'q_oldPlace','q_oldPlaceName')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_oldPlaceName" size="20" maxlength="20" value="<%=untmpMoveQuery.getQ_oldPlaceName()%>">]
        </td>
	</tr>
	<tr name="div_q_detail">
	    <td class="queryTDLable"><div align="right">原存置地點說明：</div></td>
	    <td class="queryTDInput" colspan="3">
	    	<input class="field_Q" type="text" name="q_oldPlaceNote" size="30" maxlength="30" value="<%=untmpMoveQuery.getQ_oldPlaceNote()%>">
        </td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">新保管單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_newKeepUnit", "q_newKeepUnitQuickly", untmpMoveQuery.getQ_newKeepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_newKeepUnit" onclick="popUnitNo(form1.q_enterOrg.value,'q_newKeepUnit')" value="..." title="單位輔助視窗">
		</td>
		<td class="queryTDLable"><div align="right">新保管人：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_newKeeper", "q_newKeeperQuickly", untmpMoveQuery.getQ_newKeeper()) %>
	        <input class="field_Q" type="button" name="btn_q_newKeeper" onclick="popUnitMan(form1.q_enterOrg.value,'q_newKeeper')" value="..." title="人員輔助視窗">
        </td>
	</tr>
	<tr name="div_q_detail">
	    <td class="queryTDLable">新使用單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                      	"field_Q", "form1", "q_newUseUnit", "q_newUseUnitQuickly", untmpMoveQuery.getQ_newUseUnit()) %>
			<input class="field_Q" type="button" name="btn_q_newUseUnit" onclick="popUnitNo(form1.q_enterOrg.value,'q_newUseUnit')" value="..." title="單位輔助視窗">
		</td>
	    <td class="queryTDLable"><div align="right">新使用人：</div></td>
	    <td class="queryTDInput">
	    	<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untmpMoveQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_newUserNo", "q_newUserNoQuickly", untmpMoveQuery.getQ_newUserNo()) %>
	        <input class="field_Q" type="button" name="btn_q_newUserNo" onclick="popUnitMan(form1.q_enterOrg.value,'q_newUserNo')" value="..." title="人員輔助視窗">
        </td>
	</tr>
	<tr name="div_q_detail">
	    <td class="queryTDLable"><div align="right">新使用人註記：</div></td>
	    <td class="queryTDInput">
	    	<input class="field_Q" type="text"  name="q_newUserNote" value="<%=untmpMoveQuery.getQ_newUserNote() %>" size="20">
        </td>
	    <td class="queryTDLable"><div align="right">新存置地點：</div></td>
	    <td class="queryTDInput">
	    	<input class="field_Q" type="text" name="q_newPlace" size="10" maxlength="10" value="<%=untmpMoveQuery.getQ_newPlace()%>" onchange="queryPlaceName('q_enterOrg','q_newPlace');">
			<input class="field_Q" type="button" name="btn_q_newPlace" onclick="popPlace(form1.organID.value,'q_newPlace','q_newPlaceName')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_newPlaceName" size="20" maxlength="20" value="<%=untmpMoveQuery.getQ_newPlaceName()%>">]
        </td>
	</tr>
	<tr name="div_q_detail">
	    <td class="queryTDLable"><div align="right">新存置地點說明：</div></td>
	    <td class="queryTDInput" colspan="3">
            <input class="field_Q" type="text" name="q_newPlaceNote" size="30" maxlength="30" value="<%=untmpMoveQuery.getQ_newPlaceNote()%>">
        </td>
	</tr>
	<tr name="div_q_detail">
	  <td class="queryTDLable">財產區分別：</td>
	  <td class="queryTDInput">
		  	<%=util.View._getSelectHTML("field_Q", "q_differenceKind", untmpMoveQuery.getQ_differenceKind(),"DFK") %>	
		</td>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput">			
			<input class="field_Q" type="text" name="q_propertyName1" size="30" maxlength="30" value="<%=untmpMoveQuery.getQ_propertyName1()%>">    
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="q_signLaNo1" onChange="changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','');refreshTime();">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untmpMoveQuery.getQ_signLaNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signLaNo2" onChange="changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','');refreshTime();">
				<script>changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=untmpMoveQuery.getQ_signLaNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signLaNo3">
				<script>changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=untmpMoveQuery.getQ_signLaNo3()%>');</script>
			</select>
		</td>
		<td class="queryTDInput">
			地號：
			起：
			<input class="field_Q" type="text" name="q_signLaNo4S" size="4" maxlength="4" value="<%=untmpMoveQuery.getQ_signLaNo4S()%>" onchange="getFrontZero(this.name,4);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signLaNo5S" size="4" maxlength="4" value="<%=untmpMoveQuery.getQ_signLaNo5S()%>" onchange="getFrontZero(this.name,4);">
			<br>
			&nbsp;　　　訖：
			<input class="field_Q" type="text" name="q_signLaNo4E" size="4" maxlength="4" value="<%=untmpMoveQuery.getQ_signLaNo4E()%>" onchange="getFrontZero(this.name,4);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signLaNo5E" size="4" maxlength="4" value="<%=untmpMoveQuery.getQ_signLaNo5E()%>" onchange="getFrontZero(this.name,4);">
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="q_signBuNo1" onChange="changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','');refreshTime();">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untmpMoveQuery.getQ_signBuNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signBuNo2" onChange="changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','');refreshTime();">
				<script>changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=untmpMoveQuery.getQ_signBuNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signBuNo3">
				<script>changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=untmpMoveQuery.getQ_signBuNo3()%>');</script>
			</select>
		</td>
		<td class="queryTDInput">
			建號：
			起：
			<input class="field_Q" type="text" name="q_signBuNo4S" size="5" maxlength="5" value="<%=untmpMoveQuery.getQ_signBuNo4S()%>" onchange="getFrontZero(this.name,5);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signBuNo5S" size="3" maxlength="3" value="<%=untmpMoveQuery.getQ_signBuNo5S()%>" onchange="getFrontZero(this.name,3);">					
			<br>
			&nbsp;　　　訖：
			<input class="field_Q" type="text" name="q_signBuNo4E" size="5" maxlength="5" value="<%=untmpMoveQuery.getQ_signBuNo4E()%>" onchange="getFrontZero(this.name,5);">			
			&nbsp;-
			<input class="field_Q" type="text" name="q_signBuNo5E" size="3" maxlength="3" value="<%=untmpMoveQuery.getQ_signBuNo5E()%>" onchange="getFrontZero(this.name,3);">
		</td>
	</tr>
	<tr name="div_q_proof">
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput"colspan="3">		
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untmpMoveQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untmpMoveQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable"style="display:none;">案名：</td>
		<td class="queryTDInput"style="display:none;">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="25" value="<%=untmpMoveQuery.getQ_caseName()%>">
		</td>		
	</tr>		
	<tr name="div_q_proof">
		<td class="queryTDLable">移動單編號：</td>
		<td class="queryTDInput"  colspan="3">
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=untmpMoveQuery.getQ_proofYear()%>" onchange="getFrontZero(this.name,3);">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untmpMoveQuery.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untmpMoveQuery.getQ_proofNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untmpMoveQuery.getQ_proofNoE()%>" onchange="getFrontZero(this.name,7);">號					
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
			<input class="field_Q" type="hidden" name="i_enterOrg" value="<%=untmpMoveQuery.getI_enterOrg()%>">
			<input class="field_Q" type="hidden" name="i_ownership" value="<%=untmpMoveQuery.getI_ownership()%>">
			<input class="field_Q" type="hidden" name="i_caseNo" value="<%=untmpMoveQuery.getI_caseNo()%>">
			<input class="field_Q" type="hidden" name="i_differenceKind" value="<%=untmpMoveQuery.getI_differenceKind()%>">
			<input class="field_Q" type="hidden" name="queryone_enterOrg" value="<%=untmpMoveQuery.getQueryone_enterOrg()%>">
			<input class="field_Q" type="hidden" name="queryone_ownership" value="<%=untmpMoveQuery.getQueryone_ownership()%>">
			<input class="field_Q" type="hidden" name="queryone_caseNo" value="<%=untmpMoveQuery.getQueryone_caseNo()%>">
			<input class="field_Q" type="hidden" name="queryone_differenceKind" value="<%=untmpMoveQuery.getQueryone_differenceKind()%>">
			<input class="field_Q" type="hidden" name="pageSize1" value="<%=untmpMoveQuery.getPageSize1()%>">
			<input class="field_Q" type="hidden" name="totalPage1" value="<%=untmpMoveQuery.getTotalPage1()%>">
			<input class="field_Q" type="hidden" name="currentPage1" value="<%=untmpMoveQuery.getCurrentPage1()%>">
			<input class="field_Q" type="hidden" name="totalRecord1" value="<%=untmpMoveQuery.getTotalRecord1()%>">
			<input class="field_Q" type="hidden" name="recordStart1" value="<%=untmpMoveQuery.getRecordStart1()%>">
			<input class="field_Q" type="hidden" name="recordEnd1" value="<%=untmpMoveQuery.getRecordEnd1()%>">
		</td>
	</tr>
	</table>
