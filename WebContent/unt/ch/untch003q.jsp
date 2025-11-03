<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
unt.ch.UNTCH003Q obj = (unt.ch.UNTCH003Q)request.getAttribute("UNTCH003Q");

%>
<head>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">

$(function() {
	var dcc1 = new DataCouplingCtrlPlus(form1.organID, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, false, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.organID, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, false, false);
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

function popSysca_Code(popID,popIDName,typeName,codekindid,condition){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	
	// popSysca_Code.jsp有解碼
	typeName = encodeURI(typeName);
	returnWindow=window.open("../../home/popSysca_Code.jsp?popID="+popID+"&popIDName="+popIDName+"&typeName="+typeName+"&codekindid="+codekindid+"&condition="+condition,"",prop);
}
</script>
</head>

	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="proof" <%=obj.getQueryTab1()%> onclick="initQ_Form(this.value);">
			&nbsp;<font color="green">查詢增減值單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="Detail" <%=obj.getQueryTab2()%> onclick="initQ_Form(this.value);">
			&nbsp;<font color="green">查詢增減值單明細資料</font>
		</td>
	</tr>				
	<tr name="div_q_proof">
		<td class="queryTDLable" width="16%">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"N")%>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr name="div_q_proof">
		<td class="queryTDLable">增減值日期：</td>
		<td class="queryTDInput" colspan="3">
			起 <%=util.View.getPopCalndar("field_Q","q_adjustDateS",obj.getQ_adjustDateS())%>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_adjustDateE",obj.getQ_adjustDateE())%>
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
			起<%=new unt.ch.UNTCH_Tools().getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=new unt.ch.UNTCH_Tools().getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%> 			
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable" >財產分號：</td>
		<td class="queryTDInput" >
			起 <input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖 <input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onchange="getFrontZero(this.name,7);">
		</td>
		<td class="queryTDLable">核准機關：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_approveOrg">
			<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ", obj.getQ_approveOrg())%>
			</select>
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="q_signLaNo1" onChange="changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','');refreshTime();">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signLaNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signLaNo2" onChange="changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','');refreshTime();">
				
			</select>		
			<select class="field_Q" type="select" name="q_signLaNo3">
				
			</select>
		</td>
		<td class="queryTDInput">
			地號：
			起：
			<input class="field_Q" type="text" name="q_signLaNo4S" size="4" maxlength="4" value="<%=obj.getQ_signLaNo4S()%>" onchange="getFrontZero(this.name,4);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signLaNo5S" size="4" maxlength="4" value="<%=obj.getQ_signLaNo5S()%>" onchange="getFrontZero(this.name,4);">					
			<br>
			&nbsp;　　　訖：
			<input class="field_Q" type="text" name="q_signLaNo4E" size="4" maxlength="4" value="<%=obj.getQ_signLaNo4E()%>" onchange="getFrontZero(this.name,4);">			
			&nbsp;-
			<input class="field_Q" type="text" name="q_signLaNo5E" size="4" maxlength="4" value="<%=obj.getQ_signLaNo5E()%>" onchange="getFrontZero(this.name,4);">
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="q_signBuNo1" onChange="changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','');refreshTime();">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signBuNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signBuNo2" onChange="changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','');refreshTime();">
				
			</select>		
			<select class="field_Q" type="select" name="q_signBuNo3">
				
			</select>
		</td>
		<td class="queryTDInput">
			建號：
			起：
			<input class="field_Q" type="text" name="q_signBuNo4S" size="5" maxlength="5" value="<%=obj.getQ_signBuNo4S()%>" onchange="getFrontZero(this.name,5);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signBuNo5S" size="3" maxlength="3" value="<%=obj.getQ_signBuNo5S()%>" onchange="getFrontZero(this.name,3);">					
			<br>
			&nbsp;　　　訖：
			<input class="field_Q" type="text" name="q_signBuNo4E" size="5" maxlength="5" value="<%=obj.getQ_signBuNo4E()%>" onchange="getFrontZero(this.name,5);">			
			&nbsp;-
			<input class="field_Q" type="text" name="q_signBuNo5E" size="3" maxlength="3" value="<%=obj.getQ_signBuNo5E()%>" onchange="getFrontZero(this.name,3);">					
		</td>
	</tr>	
	<tr name="div_q_proof">
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_verify">
				<%=util.View.getYNOption(obj.getQ_verify())%>
			</select>					
		</td>
	</tr>	
	<tr name="div_q_proof">
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>
		</td>
		<td class="queryTDLable">財產別名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_propertyName1" size="30" maxlength="30" value="<%=obj.getQ_propertyName1()%>">
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<input type="hidden" name="tempEnterOrg" value="<%=obj.getQ_enterOrg()%>">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" +  obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getQ_keeper()) %>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">	
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly", obj.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", obj.getQ_userNo()) %>
	        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
	        &nbsp;&nbsp;&nbsp;
			使用註記：
			<input class="field_Q" type="text" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20">
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_place" size="10" maxlength="10" value="<%=obj.getQ_place()%>" onchange="queryPlaceName('q_enterOrg','q_place');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place','q_placeName')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_placeName" size="20" maxlength="20" value="<%=obj.getQ_placeName()%>">]		
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
		</td>
	</tr>
	<tr name="div_q_proof">
		<td class="queryTDLable">工程編號：</td>
		<td class="queryTDInput" colspan="3">
       		<input class="field_Q" type="text" name="q_engineeringNo" size="15" maxlength="15" value="<%=obj.getQ_engineeringNo()%>" onchange="queryEngineeringName('q_enterOrg','q_engineeringNo');">
			<input class="field_Q" type="button" name="btn_engineeringNo" onclick="popEngineering(form1.organID.value,'q_engineeringNo','q_engineeringNoName');" value="..." title="工程編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_engineeringNoName" size="20" maxlength="50" value="<%=obj.getQ_engineeringNoName()%>">]
       	</td>
    </tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_propertyKind", obj.getQ_propertyKind(),"PKD") %>					
		</td>	
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", obj.getQ_fundType(),"FUD",obj.getQ_enterOrg()) %>
		</td>				
	</tr>		
	<tr name="div_q_detail">
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>		
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
				<%=util.View.getYNOption(obj.getQ_taxCredit())%>
			</select>		
		</td>					
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">增減值原因：</td>
		<td class="queryTDInput" colspan="3">
			<%=util.View._getSelectHTML("field_Q", "q_cause", obj.getQ_cause(),"AJC") %>
			<input class="field_Q" type="button" name="btn_cause" onclick="popSysca_Code('q_cause','q_causeName','增減值原因','AJC','');" value="..." title="輔助視窗">
		</td>	
	</tr>
	<tr name="div_q_proof">
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput"colspan="3">
			起 <%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable"style="display:none;">案名：</td>
		<td class="queryTDInput"style="display:none;">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=obj.getQ_caseName()%>">
		</td>
	</tr>
	<tr name="div_q_proof">
		<td class="queryTDLable">增減值單編號：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=obj.getQ_proofYear()%>" onchange="getFrontZero(this.name,3);">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onchange="getFrontZero(this.name,7);">號
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
			<input class="field_Q" type="hidden" name="i_enterOrg" value="<%=obj.getI_enterOrg()%>">
			<input class="field_Q" type="hidden" name="i_ownership" value="<%=obj.getI_ownership()%>">
			<input class="field_Q" type="hidden" name="i_caseNo" value="<%=obj.getI_caseNo()%>">
			<input class="field_Q" type="hidden" name="i_differenceKind" value="<%=obj.getI_differenceKind()%>">
			<input class="field_Q" type="hidden" name="queryone_enterOrg" value="<%=obj.getQueryone_enterOrg()%>">
			<input class="field_Q" type="hidden" name="queryone_ownership" value="<%=obj.getQueryone_ownership()%>">
			<input class="field_Q" type="hidden" name="queryone_caseNo" value="<%=obj.getQueryone_caseNo()%>">
			<input class="field_Q" type="hidden" name="queryone_differenceKind" value="<%=obj.getQueryone_differenceKind()%>">
			<input class="field_Q" type="hidden" name="pageSize1" value="<%=obj.getPageSize1()%>">
			<input class="field_Q" type="hidden" name="totalPage1" value="<%=obj.getTotalPage1()%>">
			<input class="field_Q" type="hidden" name="currentPage1" value="<%=obj.getCurrentPage1()%>">
			<input class="field_Q" type="hidden" name="totalRecord1" value="<%=obj.getTotalRecord1()%>">
			<input class="field_Q" type="hidden" name="recordStart1" value="<%=obj.getRecordStart1()%>">
			<input class="field_Q" type="hidden" name="recordEnd1" value="<%=obj.getRecordEnd1()%>">
		</td>
	</tr>
	</table>
