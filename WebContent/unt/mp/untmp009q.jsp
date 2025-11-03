<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<%
unt.mp.UNTMP009Q untmpMoveQuery = (unt.mp.UNTMP009Q)request.getAttribute("UNTMP009Q");
%>
<!--Query區============================================================-->
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDInput" colspan="4">
			<input name="querySelect" type="radio" class="field_Q" value="MoveProof" <%=untmpMoveQuery.getQueryTab1()%>>
			&nbsp;<font color="green">查詢移動單</font>
			<input name="querySelect" type="radio" class="field_Q" value="MoveDetail" <%=untmpMoveQuery.getQueryTab2()%>>
			&nbsp;<font color="green">查詢移動單明細</font>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",untmpMoveQuery.getQ_enterOrg(),untmpMoveQuery.getQ_enterOrgName(), "N&js=changeAll()")%>
		<script>
		function changeAll(){
			changeQ_enterOrg(form1.q_enterOrg.value, 'q_oldKeepUnit','');
			changeQ_enterOrg(form1.q_enterOrg.value, 'q_oldUseUnit','');
			changeQ_enterOrg(form1.q_enterOrg.value, 'q_newKeepUnit','');	
			changeQ_enterOrg(form1.q_enterOrg.value, 'q_newUseUnit','');
			changeQ_enterOrgAndKeeper();
			changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			changeBureau(form1.q_enterOrg, form1.q_oldKeepBureau,'');
			changeBureau(form1.q_enterOrg, form1.q_oldUseBureau,'');
			changeBureau(form1.q_enterOrg, form1.q_newKeepBureau,'');
			changeBureau(form1.q_enterOrg, form1.q_newUseBureau,'');
			
		}
		
		function changeQ_enterOrgAndKeeper(){
			getKeeper(form1.q_enterOrg, this, form1.q_oldKeeper, '');
			getKeeper(form1.q_enterOrg, this, form1.q_oldUserNo, '');
			getKeeper(form1.q_enterOrg, this, form1.q_newKeeper, '');
			getKeeper(form1.q_enterOrg, this, form1.q_newUserNo, '');
		}
		
		function changeQ_enterOrg(q_enterOrg,q_No,selectValue){
			var obj2 = document.all.item(q_No);
			obj2.options.length=0;
			var oOption = document.createElement("Option");	
			obj2.options.add(oOption);
			oOption.innerText = "請選擇";
			oOption.value = "";		
			var queryValue=(q_enterOrg==""?"<%=user.getOrganID()%>":q_enterOrg);	
			if (queryValue!=""){
				var xmlDoc=document.createElement("xml");	
				xmlDoc.async=false;			
				if(xmlDoc.load("xmlQ_enterOrg.jsp?q_enterOrg="+queryValue)){		
					var nodesLen=xmlDoc.documentElement.childNodes.length;
					for(i=0; i<nodesLen; i++){
						unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
						unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
						var oOption = document.createElement("Option");	
						obj2.options.add(oOption);
						oOption.innerText = unitName;
						oOption.value = unitNo;		
				      	if(unitNo == selectValue){
		        			oOption.selected=true;
						}           										
					}
				}else{
					return false;	
				}
			}	
		}
		</script>
		</td>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(untmpMoveQuery.getQ_ownership())%>
			</select>
		</td>		
	</tr>
	<tr>
		<td class="queryTDLable">電腦單號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=untmpMoveQuery.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=untmpMoveQuery.getQ_caseNoE()%>">			
		</td>	
		<td class="queryTDLable">移動日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_moveDateS",untmpMoveQuery.getQ_moveDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_moveDateE",untmpMoveQuery.getQ_moveDateE())%>
		</td>		
	</tr>	
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">		
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",untmpMoveQuery.getQ_propertyNoS(),untmpMoveQuery.getQ_propertyNoSName(),"3,4,5")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",untmpMoveQuery.getQ_propertyNoE(),untmpMoveQuery.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput" colspan="3">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=untmpMoveQuery.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=untmpMoveQuery.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>			
	</tr>	
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",untmpMoveQuery.getQ_propertyKind())%>
			</select>		
		</td>			
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<script>changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=untmpMoveQuery.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
			</select>
		</td>		
	</tr>	
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(untmpMoveQuery.getQ_valuable())%>
			</select>		
		</td>				
		<td class="queryTDLable">入帳：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_verify">
			<%=util.View.getYNOption(untmpMoveQuery.getQ_verify())%>
			</select>
		</td>
	</tr>			
	<tr style="display:none">
	    <td class="queryTDLable">原保管處別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_oldKeepBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_oldKeepBureau,form1.q_oldKeepUnit,form1.q_oldKeeper,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", untmpMoveQuery.getQ_oldKeepBureau())%>			
			</select>
		</td>
		<td class="queryTDLable">原使用處別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_oldUseBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_oldUseBureau,form1.q_oldUseUnit,form1.q_oldUserNo,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", untmpMoveQuery.getQ_oldUseBureau())%>			
			</select>
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable">原保管單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_oldKeepUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_oldKeeper, '');">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", untmpMoveQuery.getQ_oldKeepUnit())%>			
			</select>
		</td>
        <td class="queryTDLable">原使用單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_oldUseUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_oldUserNo, '');">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", untmpMoveQuery.getQ_oldUseUnit())%>			
			</select>
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable"><div align="right">原保管人：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_oldKeeper">
            <script>getKeeper(form1.q_enterOrg, form1.q_oldKeepUnit, form1.q_oldKeeper, '<%=untmpMoveQuery.getQ_oldKeeper()%>');</script>
          </select>
        </td>
	    <td class="queryTDLable"><div align="right">原使用人：</div></td>
	    <td class="queryTDInput">
	    	<select class="field_Q" type="select" name="q_oldUserNo">
            	<script>getKeeper(form1.q_enterOrg, form1.q_oldUseUnit, form1.q_oldUserNo, '<%=untmpMoveQuery.getQ_oldUserNo()%>');</script>
          	</select>
        </td>
	</tr>
	<tr>
	    <td class="queryTDLable">新保管處別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_newKeepBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_newKeepBureau,form1.q_newKeepUnit,form1.q_newKeeper,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", untmpMoveQuery.getQ_newKeepBureau())%>			
			</select>
		</td>
		<td class="queryTDLable">新使用處別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_newUseBureau" onChange="getKeepUnit2(form1.q_enterOrg,form1.q_newUseBureau,form1.q_newUseUnit,form1.q_newUserNo,'');">
			<%=util.View.getOption("select bureau, bureauName from UNTMP_Bureau where enterOrg like '" + user.getOrganID() + "%' order by bureau ", untmpMoveQuery.getQ_newUseBureau())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">新保管單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_newKeepUnit" onChange="getKeeper(form1.q_enterOrg,form1.q_newKeepUnit, form1.q_newKeeper, '');">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", untmpMoveQuery.getQ_newKeepUnit())%>			
			</select>
		</td>
	    <td class="queryTDLable">新使用單位：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_newUseUnit" onChange="getKeeper(form1.q_enterOrg, this, form1.q_newUserNo, '');">
			<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ", untmpMoveQuery.getQ_newUseUnit())%>			
			</select>
		</td>
	</tr>
	<tr>
	    <td class="queryTDLable"><div align="right">新保管人：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_newKeeper">
            <script>getKeeper(form1.q_enterOrg, form1.q_newKeepUnit, form1.q_newKeeper, '<%=untmpMoveQuery.getQ_newKeeper()%>');</script>
          </select>
        </td>
	    <td class="queryTDLable"><div align="right">新使用人：</div></td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_newUserNo">
            <script>getKeeper(form1.q_enterOrg, form1.q_newUseUnit, form1.q_newUserNo, '<%=untmpMoveQuery.getQ_newUserNo()%>');</script>
          </select>
        </td>
	</tr>
	<tr>
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">		
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",untmpMoveQuery.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",untmpMoveQuery.getQ_writeDateE())%>
		</td>
		<td class="queryTDLable">案名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseName" size="20" maxlength="25" value="<%=untmpMoveQuery.getQ_caseName()%>">
		</td>		
	</tr>		
	<tr>
		<td class="queryTDLable">移動單編號：</td>
		<td class="queryTDInput"  colspan="3">		
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=untmpMoveQuery.getQ_proofDoc()%>">字
			起<%=util.View.getPopCalndar("field_Q","q_proofNoS",untmpMoveQuery.getQ_proofNoS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_proofNoE",untmpMoveQuery.getQ_proofNoE())%>號
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
