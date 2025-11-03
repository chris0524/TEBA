<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<%
unt.la.UNTLA001Q untlaQuery = (unt.la.UNTLA001Q)request.getAttribute("UNTLA001Q");
%>
<script language="javascript">
function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
</script>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=untlaQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢土地增加單</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="Land" <%=untlaQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢土地基本資料</font>	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">			

			
		</td>
	</tr>				
	<tr>
	  <td class="queryTDLable" >入帳機關：</td>
	  <td colspan="3" class="queryTDInput" ><%=util.View.getPopOrgan("field_Q","q_enterOrg",untlaQuery.getQ_enterOrg(),untlaQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%> 　權屬：
	    <select class="field_Q" type="select" name="q_ownership">
          <%=util.View.getOnwerOption(untlaQuery.getQ_ownership())%>
        </select>　
        </td>
	  </tr>
	<tr>
	  <td class="queryTDLable">電腦單號：</td>
	  <td class="queryTDInput">
			  起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untlaQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untlaQuery.getQ_caseNoE()%>">
      </td>
	  <td class="queryTDLable">入帳日期：</td>
	  <td class="queryTDInput">
			  起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untlaQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untlaQuery.getQ_enterDateE())%> \
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untlaQuery.getQ_propertyNoS(),untlaQuery.getQ_propertyNoSName(),"1")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untlaQuery.getQ_propertyNoE(),untlaQuery.getQ_propertyNoEName(),"1")%>
	  </td>
	</tr>	
	<tr>
	  <td class="queryTDLable">財產分號：</td>
	  <td class="queryTDInput">
		  起 <input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untlaQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp; 
	 	  訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untlaQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
 	  </td>
	  <td class="queryTDLable">資料狀態：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_dataState">
          <%=util.View.getTextOption("1;現存;2;已減損",untlaQuery.getQ_dataState())%>
        </select>
      </td>
	  </tr>	
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untlaQuery.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=untlaQuery.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=untlaQuery.getQ_signNo3()%>');</script>
			</select>
			&nbsp;地號：
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=untlaQuery.getQ_signNo4()%>">
			&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=untlaQuery.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
	  <td class="queryTDLable">入帳：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_verify">
          <%=util.View.getYNOption(untlaQuery.getQ_verify())%>
        </select>
      </td>
	  <td class="queryTDLable">月結：</td>
	  <td class="queryTDInput">
	  
      </td>
	  </tr>
	<tr>
	  <td class="queryTDLable">財產性質：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_propertyKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untlaQuery.getQ_propertyKind())%>
        </select>
      </td>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
            	<%//=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD' ", untlaQuery.getQ_fundType())%>
          		<script language="javascript">
          		alteredEnterOrg();
          		</script>
          	</select>
        </td>
	</tr>		
	<tr>
	  <td class="queryTDLable">珍貴財產：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_valuable">
          <%=util.View.getYNOption(untlaQuery.getQ_valuable())%>
        </select>
      </td>
		<td class="queryTDLable">抵繳遺產稅：</td>
		<td class="queryTDInput"><select class="field_Q" type="select" name="q_taxCredit">
            <%=util.View.getYNOption(untlaQuery.getQ_taxCredit())%>
          </select>
        </td>
	</tr>		
	<tr>
	  <td class="queryTDLable">增加原因：</td>
	  <td class="queryTDInput" colspan="3"><select class="field_Q" type="select" name="q_cause">
          <%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAA' and codeCon1 in ('1','3','4')", untlaQuery.getQ_cause())%>
      </select></td>
	</tr>	
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untlaQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untlaQuery.getQ_writeDateE())%> 
		</td>		
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untlaQuery.getQ_caseName()%>">		</td>	
	  </tr>				
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untlaQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untlaQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untlaQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>	
