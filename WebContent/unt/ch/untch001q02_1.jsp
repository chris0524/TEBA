<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>
<%
unt.ch.UNTCH001Q untbuQuery = (unt.ch.UNTCH001Q)request.getAttribute("UNTCH001Q");
String ProgID = Common.checkGet(request.getParameter("progID"));
%>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">

$(function() {
	var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, false, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, false, false);
	if ('<%=user.getRoleid()%>' == '1'){
		form1.btn_q_keeper.disabled = true;
		readbg="#EEEEEE";
		setFormItem("q_keeperQuickly","R");		
	}	
}); 


function getSourceKindName(popSourceKind,popSourceKindName){
	//傳送JSON
	var comment={};	
	comment.codeid = document.all.item(popSourceKind).value;
	
	$.post('querySourceKind.jsp',
		comment,
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');
			if(typeof(data['codename']) == 'undefined'){
				$("input[name='" + popSourceKind +"']").val('');
			}else{
				$("input[name='" + popSourceKindName + "']").val(data['codename']);	
			}
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
<body topmargin="0" onLoad="init();">
	<table class="queryTable"  border="1">					
	<tr name="div_q_detail">
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">
			起<%=new unt.ch.UNTCH_Tools().getPopProperty("field_Q","q_propertyNoS",untbuQuery.getQ_propertyNoS(),untbuQuery.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=new unt.ch.UNTCH_Tools().getPopProperty("field_Q","q_propertyNoE",untbuQuery.getQ_propertyNoE(),untbuQuery.getQ_propertyNoEName(),"")%>
		</td>
	</tr>	
	<tr name="div_q_detail">
	  <td class="queryTDLable">財產分號：</td>
	  <td class="queryTDInput">
		  	起 <input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untbuQuery.getQ_serialNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖 <input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untbuQuery.getQ_serialNoE()%>" onchange="getFrontZero(this.name,7);">
	  </td>
	  <td class="queryTDLable">資料狀態：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_dataState">
          <%=util.View.getTextOption("1;現存;2;已減損",untbuQuery.getQ_dataState())%>
        </select>
      </td>
	</tr>
	<tr>
		<td class="td_form">財產來源：</td>
		<td colspan="3" class="td_form_white">
			<%=uctls.getSourceKind("field_Q","q_sourceKind",untbuQuery.getQ_sourceKind(),untbuQuery.getQ_sourceKindName())%>
		</td>	
	</tr>
	<tr name="div_q_detail">  
		<td class="td_form">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOption(" select codeID, codeName from SYSCA_Code where codeKindID='OWA' ",untbuQuery.getQ_ownership())%>
			</select>
		</td>
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind_detail", untbuQuery.getQ_differenceKind_detail(),"DFK") %>			
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="td_form">財產別名：</td>
		<td class="queryTDInput" >
			<input class="field_Q" type="text" name="q_propertyName1" size="30" maxlength="30" value="<%=untbuQuery.getQ_propertyName1()%>">		
		</td>
	   <td class="queryTDLable">使用註記：</td>
	   <td class="queryTDInput">	
			<input class="field_Q" type="text" name="q_originalUserNote" value="<%=untbuQuery.getQ_originalUserNote() %>" size="20">
	   </td>
	</tr>
	<tr name="div_q_detail">
	   <td class="td_form">動產明細別名：</td>	   
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_detail_propertyName1" size="30" maxlength="30" value="<%=untbuQuery.getQ_detail_propertyName1()%>">		
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untbuQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", untbuQuery.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.enterOrg.value,'q_keepUnit')" value="..." title="單位輔助視窗">
		</td>
		<td class="queryTDLable">保管人：</td>
		<td class="queryTDInput">
		<%if("1".equals(user.getRoleid())){ %>
			<input type="hidden" name="q_keeper" value="<%=user.getKeeperno()%>">
			<input class="field_RO" type="text" name="q_keeperQuickly" value="<%=user.getUserName()%>" size="10">			
			<input class="field_RO" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.enterOrg.value,'q_keeper')" value="..." title="人員輔助視窗">			                                                       
		<%}else{ %>	                                                       
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untbuQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", untbuQuery.getQ_keeper()) %>
			<input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.enterOrg.value,'q_keeper')" value="..." title="人員輔助視窗">			                                                       
		<%} %>			                                                       			                                                       
	        
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untbuQuery.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly", untbuQuery.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.enterOrg.value,'q_useUnit')" value="..." title="單位輔助視窗">
		</td>
		<td class="queryTDLable">使用人：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untbuQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", untbuQuery.getQ_userNo()) %>
			<input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.q_enterOrg.value,'q_userNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=untbuQuery.getQ_place1()%>" onchange="queryPlaceName('q_enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place1" onclick="popPlace(form1.q_enterOrg.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=untbuQuery.getQ_place1Name()%>">]		
		</td>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput">			
			<input class="field_Q" type="text" name="q_place" size="30" maxlength="30" value="<%=untbuQuery.getQ_place()%>">		
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">屬公共設施建設費：</td>
		<td class="queryTDInput">
       		<input class="field_Q" type="checkbox" name="cb_originalBuildFeeCB" size="10" maxlength="10" onclick="checkQ_originalBuildFeeCB();" <%= ("Y".equals(untbuQuery.getQ_originalBuildFeeCB())?"checked":"") %> >
       		<input class="field_Q" type="hidden" name="q_originalBuildFeeCB" value="<%=untbuQuery.getQ_originalBuildFeeCB()%>">
       	</td>
       	<td class="queryTDLable">部門別依比例分攤：</td>
		<td class="queryTDInput">
       		<input class="field_Q" type="checkbox" name="cb_originalDeprUnitCB" size="10" maxlength="10" onclick="checkQ_originalDeprUnitCB();" <%= ("Y".equals(untbuQuery.getQ_originalDeprUnitCB())?"checked":"") %> >
       		<input class="field_Q" type="hidden" name="q_originalDeprUnitCB" value="<%=untbuQuery.getQ_originalDeprUnitCB()%>">		
       	</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_signLaNo1" onChange="changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untbuQuery.getQ_signLaNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signLaNo2" onChange="changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','');">
				<script>changeSignNo1('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=untbuQuery.getQ_signLaNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signLaNo3">
				<script>changeSignNo2('q_signLaNo1','q_signLaNo2','q_signLaNo3','<%=untbuQuery.getQ_signLaNo3()%>');</script>
			</select>
		</td>
		<td class="queryTDLable">地號：</td>
		<td class="queryTDInput">
			起：
			<input class="field_Q" type="text" name="q_signLaNo4S" size="4" maxlength="4" value="<%=untbuQuery.getQ_signLaNo4S()%>" onchange="getFrontZero(this.name,4);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signLaNo5S" size="4" maxlength="4" value="<%=untbuQuery.getQ_signLaNo5S()%>" onchange="getFrontZero(this.name,4);">&nbsp;~&nbsp; 
			訖：<input class="field_Q" type="text" name="q_signLaNo4E" size="4" maxlength="4" value="<%=untbuQuery.getQ_signLaNo4E()%>" onchange="getFrontZero(this.name,4);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signLaNo5E" size="4" maxlength="4" value="<%=untbuQuery.getQ_signLaNo5E()%>" onchange="getFrontZero(this.name,4);">
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_signBuNo1" onChange="changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untbuQuery.getQ_signBuNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signBuNo2" onChange="changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','');">
				<script>changeSignNo1('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=untbuQuery.getQ_signBuNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signBuNo3">
				<script>changeSignNo2('q_signBuNo1','q_signBuNo2','q_signBuNo3','<%=untbuQuery.getQ_signBuNo3()%>');</script>
			</select>
		</td>
		<td class="queryTDLable">建號：</td>
		<td class="queryTDInput">
			起：
			<input class="field_Q" type="text" name="q_signBuNo4S" size="5" maxlength="5" value="<%=untbuQuery.getQ_signBuNo4S()%>" onchange="getFrontZero(this.name,5);">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signBuNo5S" size="3" maxlength="3" value="<%=untbuQuery.getQ_signBuNo5S()%>" onchange="getFrontZero(this.name,3);">&nbsp;~&nbsp; 
			訖：<input class="field_Q" type="text" name="q_signBuNo4E" size="5" maxlength="5" value="<%=untbuQuery.getQ_signBuNo4E()%>" onchange="getFrontZero(this.name,5);">			
			&nbsp;-
			<input class="field_Q" type="text" name="q_signBuNo5E" size="3" maxlength="3" value="<%=untbuQuery.getQ_signBuNo5E()%>" onchange="getFrontZero(this.name,3);">
		</td>
	</tr>
	<tr name="div_q_detail">
	    <td class="queryTDLable">門牌：</td>
	    <td class="queryTDInput">
	    	<input class="field_Q"  type="text" name="q_doorPlate4"  value="<%=untbuQuery.getQ_doorPlate4()%>" size="30">
		</td>
		<td class="queryTDLable">工程編號：</td>
		<td class="queryTDInput">
       		<input class="field_Q" type="text" name="q_engineeringNo" size="15" maxlength="15" value="<%=untbuQuery.getQ_engineeringNo()%>" onchange="queryEngineeringName('q_enterOrg','q_engineeringNo');">
			<input class="field_Q" type="button" name="btn_engineeringNo" onclick="popEngineering('<%=untbuQuery.getQ_enterOrg()%>','q_engineeringNo','q_engineeringNoName');" value="..." title="工程編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_engineeringNoName" size="20" maxlength="50" value="<%=untbuQuery.getQ_engineeringNoName()%>">]
       	</td>			  	  
	</tr>
	<tr name="div_q_detail">
	  <td class="queryTDLable">財產性質：</td>
	  <td class="queryTDInput">
	  	<%=util.View._getSelectHTML("field_Q", "q_propertyKind", untbuQuery.getQ_propertyKind(),"PKD") %>
      </td>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", untbuQuery.getQ_fundType(),"FUD", untbuQuery.getQ_enterOrg()) %>
        </td>
	</tr>		
	<tr name="div_q_detail">
	  <td class="queryTDLable">珍貴財產：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_valuable">
          <%=util.View.getYNOption(untbuQuery.getQ_valuable())%>
        </select>
      </td>
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput"><select class="field_Q" type="select" name="q_taxCredit">
            <%=util.View.getYNOption(untbuQuery.getQ_taxCredit())%>
          </select>
        </td>
	</tr>	
	<tr name="div_q_detail">
<!-- 		<td class="queryTDLable">增加原因：</td> -->
<!-- 		<td class="queryTDInput"> -->
<%-- 			<%=new unt.ch.UNTCH_Tools().getCause("field_Q","q_cause",untbuQuery.getQ_cause(),untbuQuery.getQ_causeName(),"1,4")%> --%>
<!-- 		</td> -->
		<td class="queryTDLable">使用年限：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_otherlimityear" size="3" maxlength="3" value="<%=untbuQuery.getQ_otherlimityear()%>">
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起 <%=util.View.getPopCalndar("field_Q","q_enterDateS",untbuQuery.getQ_enterDateS()) %>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_enterDateE",untbuQuery.getQ_enterDateE())%>
		</td>
		<td class="queryTDLable">取得日期：</td>
		<td class="queryTDInput">
			起 <%=util.View.getPopCalndar("field_Q","q_sourceDateS",untbuQuery.getQ_sourceDateS())%>&nbsp;~&nbsp;
			訖 <%=util.View.getPopCalndar("field_Q","q_sourceDateE",untbuQuery.getQ_sourceDateE())%>
		</td>
	</tr>	
	
	<tr name="div_q_detail">
		<td class="queryTDLable">原財產編號：</td>		
		<td class="queryTDInput" colspan="3">
			起<%=new unt.ch.UNTCH_Tools().getPopProperty("field_Q","q_oldPropertyNoS",untbuQuery.getQ_oldPropertyNoS(),untbuQuery.getQ_oldPropertyNoSName(),"")%>&nbsp;~&nbsp; 
			訖<%=new unt.ch.UNTCH_Tools().getPopProperty("field_Q","q_oldPropertyNoE",untbuQuery.getQ_oldPropertyNoE(),untbuQuery.getQ_oldPropertyNoEName(),"")%>
		</td>
	</tr>	
	<tr name="div_q_detail">
		<td class="queryTDLable">原財產分號：</td>
		<td class="queryTDInput" colspan="3">
			起 <input class="field_Q" type="text" name="q_oldSerialNoS" size="20" maxlength="20" value="<%=untbuQuery.getQ_oldSerialNoS()%>">&nbsp;~&nbsp; 
			訖 <input class="field_Q" type="text" name="q_oldSerialNoE" size="20" maxlength="20" value="<%=untbuQuery.getQ_oldSerialNoE()%>">
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">原批號：</td>
		<td class="queryTDInput" colspan="3">
			起 <input class="field_Q" type="text" name="q_oldlotNoS" size="10" maxlength="10" value="<%=untbuQuery.getQ_oldlotNoS()%>">&nbsp;~&nbsp; 
			訖 <input class="field_Q" type="text" name="q_oldlotNoE" size="10" maxlength="10" value="<%=untbuQuery.getQ_oldlotNoE()%>">
		</td>
	</tr>
	<tr name="div_q_detail">
		<td class="queryTDLable">廠牌型式：</td>
		<td class="queryTDInput" colspan="3">
			型式：<input class="field_Q" type="text" name="q_specification" maxlength="40" size="40"  value="<%=untbuQuery.getQ_specification()%>">&nbsp;
			廠牌：<input class="field_Q" type="text" name="q_nameplate" size="40" maxlength="40"  value="<%=untbuQuery.getQ_nameplate()%>">
		</td>
	</tr>	
	<tr>
		<td class="td_form">經費來源：</td>
		<td class="td_form_white" colspan="3">
			<select class="field_Q" type="select" name="q_fundsSource">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FSO' ", untbuQuery.getQ_fundsSource())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">備註：</td>
	    <td class="queryTDInput" colspan="3">
	    	<input class="field_Q"  type="text" name="q_notes"  value="<%=untbuQuery.getQ_notes()%>" size="30">
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
			<input class="field_Q" type="hidden" name="i_enterOrg" value="<%=untbuQuery.getI_enterOrg()%>">
			<input class="field_Q" type="hidden" name="i_ownership" value="<%=untbuQuery.getI_ownership()%>">
			<input class="field_Q" type="hidden" name="i_caseNo" value="<%=untbuQuery.getI_caseNo()%>">
			<input class="field_Q" type="hidden" name="i_differenceKind" value="<%=untbuQuery.getI_differenceKind()%>">
			<input class="field_Q" type="hidden" name="queryone_enterOrg" value="<%=untbuQuery.getQueryone_enterOrg()%>">
			<input class="field_Q" type="hidden" name="queryone_ownership" value="<%=untbuQuery.getQueryone_ownership()%>">
			<input class="field_Q" type="hidden" name="queryone_caseNo" value="<%=untbuQuery.getQueryone_caseNo()%>">
			<input class="field_Q" type="hidden" name="queryone_differenceKind" value="<%=untbuQuery.getQueryone_differenceKind()%>">
			<input class="field_Q" type="hidden" name="pageSize1" value="<%=untbuQuery.getPageSize1()%>">
			<input class="field_Q" type="hidden" name="totalPage1" value="<%=untbuQuery.getTotalPage1()%>">
			<input class="field_Q" type="hidden" name="currentPage1" value="<%=untbuQuery.getCurrentPage1()%>">
			<input class="field_Q" type="hidden" name="totalRecord1" value="<%=untbuQuery.getTotalRecord1()%>">
			<input class="field_Q" type="hidden" name="recordStart1" value="<%=untbuQuery.getRecordStart1()%>">
			<input class="field_Q" type="hidden" name="recordEnd1" value="<%=untbuQuery.getRecordEnd1()%>">
		</td>
	</tr>
	</table>
	</body>
