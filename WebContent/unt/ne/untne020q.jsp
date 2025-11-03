<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script language="javascript">
function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
function clearInsertKey() {
	$("input[name='i_enterOrg']").val("");
	$("input[name='i_caseNo1']").val("");
	$("input[name='i_differenceKind']").val("");
}
function clearQueryOneKey() {
	$("input[name='queryone_enterOrg']").val("");
	$("input[name='queryone_caseNo1']").val("");
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
unt.ne.UNTNE020Q untneDealQuery = (unt.ne.UNTNE020Q)request.getAttribute("UNTNE020Q");

String queryType1 = "", queryType2 = "";
if ("dealDetail".equals(untneDealQuery.getQuerySelect())) queryType2 = "checked=\"CHECKED\"";	
else if("dealProof".equals(untneDealQuery.getQuerySelect())) queryType1 = "checked=\"CHECKED\"";

%>
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDLable" >查詢類別：</td>
	  <td class="queryTDInput" colspan="4">
	  <input name="querySelect" type="radio" class="field_Q" value="dealProof" <%=queryType1%>>
      &nbsp;<font color="green">查詢處理單資料</font>&nbsp;&nbsp;&nbsp;	
	  <input name="querySelect" type="radio" class="field_Q" value="dealDetail" <%=queryType2%>>
      &nbsp;<font color="green">查詢處理單明細資料</font>
	  </td>
	</tr>
	<tr>
      <td class="queryTDLable" >入帳機關：</td>
      <td class="queryTDInput" colspan="3">
		<%=util.View.getPopOrgan("field_Q","q_enterOrg",untneDealQuery.getQ_enterOrg(),untneDealQuery.getQ_enterOrgName(),"N")%>
		<input class="field_Q" type="hidden" name="q_valuable" value="">
      </td>    
	</tr>	
	<tr>
		<td class="queryTDLable">物品區分別：</td>
		<td class="queryTDInput" colspan="3">
			<%=util.View._getSelectHTML("field_Q", "q_differenceKind", untneDealQuery.getQ_differenceKind(),"DFK") %>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable"style="display:none;">電腦單號：</td>
		<td class="queryTDInput"style="display:none;">
			  起<input class="field_Q" type="text" name="q_caseNo1S" size="10" maxlength="10" value="<%=untneDealQuery.getQ_caseNo1S()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNo1E" size="10" maxlength="10" value="<%=untneDealQuery.getQ_caseNo1E()%>">
		</td>
	  <td class="queryTDLable">廢品處理日期：</td>
	  <td class="queryTDInput"colspan="3">
		 起<%=util.View.getPopCalndar("field_Q","q_dealDateS",untneDealQuery.getQ_dealDateS())%>&nbsp;~&nbsp;
		 訖<%=util.View.getPopCalndar("field_Q","q_dealDateE",untneDealQuery.getQ_dealDateE())%>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">物品編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<input class="field_Q" type="text" name="q_propertyNoS" size="12" maxlength="12" value="<%=untneDealQuery.getQ_propertyNoS()%>" onChange="getProperty('q_propertyNoS','q_propertyNoSName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoS" onclick="popProperty('q_propertyNoS','q_propertyNoSName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoSName" size="20" maxlength="50" value="<%=untneDealQuery.getQ_propertyNoSName()%>">]&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_propertyNoE" size="12" maxlength="12" value="<%=untneDealQuery.getQ_propertyNoE()%>" onChange="getProperty('q_propertyNoE','q_propertyNoEName','','NE')">
			<input class="field_Q" type="button" name="btn_q_propertyNoE" onclick="popProperty('q_propertyNoE','q_propertyNoEName','6')" value="..." title="財產編號輔助視窗">
			[<input class="field_QRO" type="text" name="q_propertyNoEName" size="20" maxlength="50" value="<%=untneDealQuery.getQ_propertyNoEName()%>">]
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untneDealQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untneDealQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>		
		<td class="queryTDLable">審核：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untneDealQuery.getQ_verify())%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">物品性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untneDealQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金物品：</td>
		<td class="queryTDInput">
			<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", untneDealQuery.getQ_fundType(),"FUD", untneDealQuery.getQ_enterOrg()) %>
		</td>		
	</tr>		
<!--<tr>
		<td class="queryTDLable">珍貴物品：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(untneDealQuery.getQ_valuable())%>
			</select>		
		</td>		
	</tr>	
-->	
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput"colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untneDealQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untneDealQuery.getQ_writeDateE())%> 
		</td>		
		<td class="queryTDLable"style="display:none;">處理案名：</td>
		<td class="queryTDInput"style="display:none;">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untneDealQuery.getQ_caseName()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">處理單編號：</td>
		<td class="queryTDInput" colspan="3">		
			<input class="field_Q" type="text" name="q_proofYear" size="3" value="<%=untneDealQuery.getQ_proofYear()%>">
			年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=untneDealQuery.getQ_proofDoc()%>">
			字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untneDealQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untneDealQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">處理方式：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reduceDeal">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='RDL' ", untneDealQuery.getQ_reduceDeal())%>
			</select>
		</td>
		<td class="queryTDLable">轉撥單位：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_shiftOrg",untneDealQuery.getQ_shiftOrg(),untneDealQuery.getQ_shiftOrgName(),"Y")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onClick="clearInsertKey();clearQueryOneKey();">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	<tr style="display='none'">
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="field_Q" type="hidden" name="i_enterOrg" value="<%=untneDealQuery.getI_enterOrg()%>">
			<input class="field_Q" type="hidden" name="i_caseNo" value="<%=untneDealQuery.getI_caseNo1()%>">
			<input class="field_Q" type="hidden" name="i_differenceKind" value="<%=untneDealQuery.getI_differenceKind()%>">
			<input class="field_Q" type="hidden" name="queryone_enterOrg" value="<%=untneDealQuery.getQueryone_enterOrg()%>">
			<input class="field_Q" type="hidden" name="queryone_caseNo" value="<%=untneDealQuery.getQueryone_caseNo1()%>">
			<input class="field_Q" type="hidden" name="queryone_differenceKind" value="<%=untneDealQuery.getQueryone_differenceKind()%>">
			<input class="field_Q" type="hidden" name="pageSize1" value="<%=untneDealQuery.getPageSize1()%>">
			<input class="field_Q" type="hidden" name="totalPage1" value="<%=untneDealQuery.getTotalPage1()%>">
			<input class="field_Q" type="hidden" name="currentPage1" value="<%=untneDealQuery.getCurrentPage1()%>">
			<input class="field_Q" type="hidden" name="totalRecord1" value="<%=untneDealQuery.getTotalRecord1()%>">
			<input class="field_Q" type="hidden" name="recordStart1" value="<%=untneDealQuery.getRecordStart1()%>">
			<input class="field_Q" type="hidden" name="recordEnd1" value="<%=untneDealQuery.getRecordEnd1()%>">
		</td>
	</tr>
	</table>
