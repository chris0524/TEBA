<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
$(function() {
	var dcc1 = new DataCouplingCtrlPlus(form1.organID, form1.q_keepUnitQuickly, form1.q_keepUnit, null, null, false, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.organID, form1.q_keeperQuickly, form1.q_keeper, null, null, false, false);
}); 
</script>

<%
unt.dp.UNTDP002Q obj = (unt.dp.UNTDP002Q) request.getAttribute("UNTDP002Q");

%>

	<table class="queryTable"  border="1">
		<tr>
			<td class="queryTDLable" >入帳機關：</td>
		    <td class="queryTDInput" colspan="3">
		  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		    </td>		
		</tr>
		<tr>
		  <td class="queryTDLable" >折舊年月：</td>
		  <td class="queryTDInput" colspan="3">
		  <input type="text" class="field_Q" name="q_deprYM"  size="5" maxlength="5" value="<%=obj.getQ_deprYM()%>">	
		  	&nbsp;&nbsp;&nbsp;
		  	<font color="red">例如：103年 2月，則輸入10302</font>  	 
		  </td>
		</tr>
		<tr>
		  <td class="queryTDLable" >財產區分別：</td>
		  <td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_differenceKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getQ_differenceKind())%>
        </select>	  	 
		  </td>
		</tr>
		<tr>
			<td class="queryTDLable" width="20%"></font>財產大類：</td>
			<td class="queryTDInput" colspan="3">
				<select class="field_Q" type="select" name="q_propertyType">
				　　<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PTT' and codeid in ('2','3','4','5','6') ",obj.getQ_propertyType())%>
				</select>
			</td>
		</tr>
		<tr>
		<td class="queryTDLable">財產編號：</td>
		<td colspan="3" class="queryTDInput">
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput" colspan="3">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>	
	</tr>
	<tr name="div_q_detail">
		<td class="td_form">財產別名：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_propertyName1" size="30" maxlength="30" value="<%=obj.getQ_propertyName1()%>">		
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable">園區別 :</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_deprPark">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getQ_deprPark())%>
			</select> 
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">部門別 :</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_deprUnit">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getQ_deprUnit())%>				
			</select>
		</td>
	</tr>
	<tr>	
		<td class="queryTDLable">部門別單位：</td>	
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_deprUnit1">
					<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getQ_deprUnit1())%>
		    </select>
	    </td>
	</tr>
	<tr>
		<td class="queryTDLable">屬公共設施建設費：</td>
		<td class="queryTDInput">
		<%  if("Y".equals(obj.getQ_buildFeeCB())){ %>
				<input class="field_Q" type="checkbox" name="q_buildFeeCB" size="1" maxlength="1" onclick="checkBuildFeeCB();" value="<%=obj.getQ_buildFeeCB()%>" checked >
		<% } else {%>
				<input class="field_Q" type="checkbox" name="q_buildFeeCB" size="1" maxlength="1" onclick="checkBuildFeeCB();" value="<%=obj.getQ_buildFeeCB()%>">
		<% } %>
       	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	部門別依比例分攤：       		
       	<%  if("Y".equals(obj.getQ_deprUnitCB())){ %>
			<input class="field_Q" type="checkbox" name="q_deprUnitCB" size="1" maxlength="1" onclick="checkDeprUnitCB();" value="<%=obj.getQ_deprUnitCB()%>" checked >
		<% } else {%>
       		<input class="field_Q" type="checkbox" name="q_deprUnitCB" size="1" maxlength="1" onclick="checkDeprUnitCB();" value="<%=obj.getQ_deprUnitCB()%>">
		<% } %>
       	</td>
    </tr>
		<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg()  + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly",  obj.getQ_keeper()) %>
			<input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
		</td>
	</tr>

		<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
