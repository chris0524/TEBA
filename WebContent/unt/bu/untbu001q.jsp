<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script type="text/javascript" src="../../js/function.js"></script>
<%
unt.bu.UNTBU001Q untbuQuery = (unt.bu.UNTBU001Q)request.getAttribute("UNTBU001Q");
%>

<script language="javascript">
function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untbuQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
</script>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="AddProof" <%=untbuQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢增加單資料</font>&nbsp;&nbsp;&nbsp;		
			<input name="querySelect" type="radio" class="field_Q" value="Building" <%=untbuQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢共同基本資料</font>
			<input name="querySelect" type="radio" class="field_Q" value="Building" <%=untbuQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢建物基本資料</font>
		</td>
	</tr>				
	<tr>
	  <td class="queryTDLable" >入帳機關：</td>
	  <td class="queryTDInput" ><%=util.View.getPopOrgan("field_Q","q_enterOrg",untbuQuery.getQ_enterOrg(),untbuQuery.getQ_enterOrgName(),"N&js=alteredEnterOrg();")%> </td>
	  <td class="queryTDLable">權屬：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_ownership">
          <%=util.View.getOption(" select codeID, codeName from SYSCA_Code where codeKindID='OWA' ",untbuQuery.getQ_ownership())%>
        </select>
      </td>
	  </tr>
	<tr>
	  <td class="queryTDLable">電腦單號：</td>
	  <td class="queryTDInput">
	  		起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untbuQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
	  		訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untbuQuery.getQ_caseNoE()%>">
      </td>
	  <td class="queryTDLable">入帳日期：</td>
	  <td class="queryTDInput">
			  起<%=util.View.getPopCalndar("field_Q","q_enterDateS",untbuQuery.getQ_enterDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",untbuQuery.getQ_enterDateE())%> 
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untbuQuery.getQ_propertyNoS(),untbuQuery.getQ_propertyNoSName(),"2")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untbuQuery.getQ_propertyNoE(),untbuQuery.getQ_propertyNoEName(),"2")%>
		</td>
	</tr>	
	<tr>
	  <td class="queryTDLable">財產分號：</td>
	  <td class="queryTDInput">
			  起 <input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untbuQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp; 
			  訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untbuQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
	  </td>
	  <td class="queryTDLable">資料狀態：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_dataState">
          <%=util.View.getTextOption("1;現存;2;已減損",untbuQuery.getQ_dataState())%>
        </select>
      </td>
	</tr>	
	<tr>
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",untbuQuery.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=untbuQuery.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=untbuQuery.getQ_signNo3()%>');</script>
			</select>
			&nbsp;建號：
			<input class="field_Q" type="text" name="q_signNo4" size="5" maxlength="5" value="<%=untbuQuery.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="3" maxlength="3" value="<%=untbuQuery.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable">門牌：</td>
	    <td class="queryTDInput" colspan="3">
	    	<select class="field_Q" type="select" name="q_doorPlate1" onChange="changeAddr1('q_doorPlate1','q_doorPlate2','q_doorPlate3','');">
        		<%=util.View.getOption("select addrID, addrName from SYSCA_Addr where addrKind = '1' order by seqno",untbuQuery.getQ_doorPlate1())%>
      		</select>　
       	 	<select class="field_Q" type="select" name="q_doorPlate2" onChange="changeAddr2('q_doorPlate1','q_doorPlate2','q_doorPlate3','');">
          		<script>changeAddr1('q_doorPlate1','q_doorPlate2','q_doorPlate3','<%=untbuQuery.getQ_doorPlate2()%>');</script>
        	</select>　
	        <select class="field_Q" type="select" name="q_doorPlate3">
	        	<script>changeAddr2('q_doorPlate1','q_doorPlate2','q_doorPlate3','<%=untbuQuery.getQ_doorPlate3()%>');</script>
	        </select>　
	        <input class="field_Q"  type="text" name="q_doorPlate4"  value="<%=untbuQuery.getQ_doorPlate4()%>" size="30">
		</td>
	</tr>
	<tr>
	  <td class="queryTDLable">入帳：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_verify">
          <%=util.View.getYNOption(untbuQuery.getQ_verify())%>
        </select>
      </td>
	  <td class="queryTDLable">月結：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_closing">
          <%=util.View.getYNOption(untbuQuery.getQ_closing())%>
        </select>
      </td>
	  </tr>
	<tr>
	  <td class="queryTDLable">財產性質：</td>
	  <td class="queryTDInput"><select class="field_Q" type="select" name="q_propertyKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untbuQuery.getQ_propertyKind())%>
        </select>
      </td>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
		  <select class="field_Q" type="select" name="q_fundType">
             <script>
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>', 'q_fundType','<%=untbuQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			 </script>
          </select>
        </td>
	</tr>		
	<tr>
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
	<tr>
	  <td class="queryTDLable">增加原因：</td>
	  <td class="queryTDInput" colspan="3">
	  	<select class="field_Q" type="select" name="q_cause">
          <%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='CAB' ", untbuQuery.getQ_cause())%>
      </select></td>
	</tr>	
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untbuQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untbuQuery.getQ_writeDateE())%> 
		</td>		
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="25" maxlength="25" value="<%=untbuQuery.getQ_caseName()%>">		</td>	
	  </tr>				
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput"  colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untbuQuery.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=untbuQuery.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=untbuQuery.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
