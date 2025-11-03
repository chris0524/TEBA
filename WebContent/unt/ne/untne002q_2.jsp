<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script type="text/javascript" src="../../js/function.js"></script>
<%
unt.ne.UNTNE001Q untneAddProofQuery = (unt.ne.UNTNE001Q)request.getAttribute("UNTNE001Q");
%>

<script language="javascript">

function changeAll(){
//	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
//	changeBureau(form1.q_enterOrg, form1.q_keepBureau, '');
//	changeBureau(form1.q_enterOrg, form1.q_useBureau, '');
//	changeKeepUnit(form1.q_enterOrg, form1.q_keepBureau, form1.q_keepUnit,'');
//	changeKeepUnit(form1.q_enterOrg, form1.q_useBureau, form1.q_useUnit,'');
//	getKeeper(form1.q_enterOrg, form1.q_keepUnit, form1.q_keeper, '','N');
//	getKeeper(form1.q_enterOrg, form1.q_useUnit, form1.q_userNo, '','N');
}
function openManWindow(){

	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=750,height=500,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../sys/ca/sysca004f.jsp","",prop);
}
function openUnitWindow(){

	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=750,height=500,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("../../sys/ca/sysca003f.jsp","",prop);
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
</script>

<!--Query區============================================================-->
<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
        <td class="queryTDInput" >
	  		<%=util.View.getPopOrgan("field_Q","q_enterOrg",untneAddProofQuery.getQ_enterOrg(),untneAddProofQuery.getQ_enterOrgName(),"N")%>
	    </td>
		<td class="queryTDLable"><div align="right">權屬：</div>
        <td class="queryTDInput">
	      	<select class="field_Q" type="select" name="q_ownership">
	          <%=util.View.getOnwerOption(untneAddProofQuery.getQ_ownership())%>
	        </select>
	    </td>
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="12" maxlength="10" value="<%=untneAddProofQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNoE" size="12" maxlength="10" value="<%=untneAddProofQuery.getQ_caseNoE()%>">
		</td>
		<td class="queryTDLable">入帳日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untneAddProofQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untneAddProofQuery.getQ_enterDateE())%>
		</td>
    </tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>
		<td colspan="3" class="queryTDInput">
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=untneAddProofQuery.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=untneAddProofQuery.getQ_propertyNoSName()%>">]&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=untneAddProofQuery.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=untneAddProofQuery.getQ_propertyNoEName()%>">]
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput"> 
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untneAddProofQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untneAddProofQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
 			批號：<input class="field_Q" type="text" name="q_lotNo" size="7" maxlength="7" value="<%=untneAddProofQuery.getQ_lotNo()%>">
		</td>
		<td class="queryTDLable"><div align="right">資料狀態：</div></td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<%=util.View.getTextOption("1;現存;2;已減損",untneAddProofQuery.getQ_dataState())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">原物品分號：</td>
		<td class="queryTDInput"> 
			起<input class="field_Q" type="text" name="q_oldSerialNoS" size="7" maxlength="30" value="<%=untneAddProofQuery.getQ_oldSerialNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_oldSerialNoE" size="7" maxlength="30" value="<%=untneAddProofQuery.getQ_oldSerialNoE()%>">
		</td>
		<td class="queryTDLable">物品區分別：</td>
		<td class="queryTDInput">
		<%=util.View._getSelectHTML("field_Q", "q_differenceKind", untneAddProofQuery.getQ_differenceKind(),"DFK") %>	
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">原物品批號：</td>
		<td class="queryTDInput" colspan="3">
			起 <input class="field_Q" type="text" name="q_oldlotNoS" size="10" maxlength="10" value="<%=untneAddProofQuery.getQ_oldlotNoS()%>">&nbsp;~&nbsp; 
			訖 <input class="field_Q" type="text" name="q_oldlotNoE" size="10" maxlength="10" value="<%=untneAddProofQuery.getQ_oldlotNoE()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品別名：</td>
		<td class="queryTDInput" colspan="3"> 
			<input class="field_Q" type="text" name="q_propertyName1" size="25" maxlength="25" value="<%=untneAddProofQuery.getQ_propertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">物品明細別名：</td>
		<td class="queryTDInput" colspan="3"> 
			<input class="field_Q" type="text" name="q_detail_propertyName1" size="25" maxlength="25" value="<%=untneAddProofQuery.getQ_detail_propertyName1()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untneAddProofQuery.getQ_verify())%>
			</select>
		</td>
		<td class="queryTDLable">購置日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_buyDateS",untneAddProofQuery.getQ_buyDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_buyDateE",untneAddProofQuery.getQ_buyDateE())%>
		</td>		
	</tr>    
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untneAddProofQuery.getQ_propertyKind())%>
			</select>
		</td>
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", untneAddProofQuery.getQ_fundType(),"FUD", untneAddProofQuery.getQ_enterOrg()) %>
		<input class="field_Q" type="hidden" name="q_valuable" value="">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">填單日期：</div></td>
		<td class="queryTDInput" colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untneAddProofQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untneAddProofQuery.getQ_writeDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><div align="right">增加原因：</div></td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_cause">
				<%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAA' and codecon1 in (1,4)", untneAddProofQuery.getQ_cause())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="3">
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=untneAddProofQuery.getQ_proofYear()%>">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untneAddProofQuery.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untneAddProofQuery.getQ_proofNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untneAddProofQuery.getQ_proofNoE()%>" onchange="getFrontZero(this.name,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">廠牌型式：</td>
		<td class="queryTDInput" colspan="3">
			型式：<input class="field_Q" type="text" name="q_specification" size="40" maxlength="40"  value="<%=untneAddProofQuery.getQ_specification()%>">&nbsp;
			廠牌：<input class="field_Q" type="text" name="q_nameplate" size="40" maxlength="40"  value="<%=untneAddProofQuery.getQ_nameplate()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">保管使用資料：</td>
		<td class="td_form_white" colspan="3">
			保管單位
				<select class="field_Q" type="select" name="q_keepUnit" onchange="//form1.q_useUnit.value = this.value">
				　　<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneAddProofQuery.getQ_enterOrg() + "' order by unitno ",untneAddProofQuery.getQ_keepUnit())%>
				</select>
				<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<select class="field_Q" type="select" name="q_keeper" onchange="//form1.q_userNo.value = this.value">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneAddProofQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",untneAddProofQuery.getQ_keeper())%>
	            </select>
		        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<select class="field_Q" type="select" name="q_useUnit" onchange="//form1.q_keepUnit.value = this.value">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + untneAddProofQuery.getQ_enterOrg() + "' order by unitno ",untneAddProofQuery.getQ_useUnit())%>
			    </select>	
				<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<select class="field_Q" type="select" name="q_userNo" onchange="//form1.q_keeper.value = this.value">
					<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + untneAddProofQuery.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",untneAddProofQuery.getQ_userNo())%>
	            </select>
		        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
			          使用註記：
			    <input type="text" class="field" name="q_userNote" value="<%=untneAddProofQuery.getQ_userNote() %>" size="20">
		        <br>
				存置地點
				<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=untneAddProofQuery.getQ_place1()%>" onchange="queryPlaceName('q_enterOrg','q_place1');">
				<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			    [<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=untneAddProofQuery.getQ_place1Name()%>">]
				<br>		
				存置地點說明
				<input class="field_Q" type="text" name="q_place" size="30" maxlength="30" value="<%=untneAddProofQuery.getQ_place()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
</table>
