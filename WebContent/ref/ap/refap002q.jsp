<!--
程式目的：質詢或建決議案資料
程式代號：refap002f
程式日期：0950522
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
ref.ap.REFAP002Q qBean = (ref.ap.REFAP002Q)request.getAttribute("qBean");

%>

	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">質詢暨建決議案：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_itemID">
				<%=util.View.getTextOption("1;總質詢;2;市政報告後質詢;3;部門質詢;4;議員提案;5;臨時提案",qBean.getQ_itemID())%>			
			</select>		</td>
	</tr>
	<tr>
	<td class="queryTDLable">會議屆次：</td>
	<td class="queryTDInput">第<select class="field_Q" type="select" name="q_expire">
	  	<%=util.View.getTextOption("一;一;二;二;三;三;四;四;五;五;六;六;七;七;八;八;九;九;十;十;十一;十一;十二;十二",qBean.getQ_expire())%>	  
	  </select>
	  屆第
	  <select class="field_Q" type="select" name="q_counter">
		<%=util.View.getTextOption("一;一;二;二;三;三;四;四;五;五;六;六;七;七;八;八;九;九;十;十;十一;十一;十二;十二;十三;十三;十四;十四",qBean.getQ_counter())%>			  	  
	  </select>
	  次
	  <select class="field_Q" type="select" name="q_meetKind">
	  	<%=util.View.getTextOption("1;定期會議;2;臨時會",qBean.getQ_meetKind())%>	  
	  </select></td>
	</tr>
	<tr>
		<td class="queryTDLable">議員名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_councilman" size="20" maxlength="50" value="<%=qBean.getQ_councilman()%>">　
			案號：
			<input class="field_Q" type="text" name="q_caseNo" size="10" maxlength="10" value="<%=qBean.getQ_caseNo()%>" />		</td>
	</tr>
	<tr>
		<td class="queryTDLable">主要功能分類：</td>
		<td class="queryTDInput">
			主分類&nbsp;&nbsp;：
			  <select class="field_Q" type="select" name="q_kindID_main">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='REF' ", qBean.getQ_kindID_main())%>
			</select>
		    <br />
		  次分類1：
			<select class="field_Q" type="select" name="q_kindID_other1">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='REF' ", qBean.getQ_kindID_other1())%>
			</select>
			<br />
		  次分類2：
			<select class="field_Q" type="select" name="q_kindID_other2">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='REF' ", qBean.getQ_kindID_other2())%>
			</select></td>
	</tr>
	<tr>
		<td class="queryTDLable">質詢或建決議案事項：</td>
		<td class="queryTDInput">
			<input name="q_caseSubject" type="text" class="field_Q" value="<%=qBean.getQ_caseSubject()%>" size="24" maxlength="100" />		</td>
	</tr>
	<tr>
		<td class="queryTDLable">質詢或建決議案日期：</td>
		<td class="queryTDInput">
			<%=util.View.getPopCalndar("field_Q","q_caseDate",qBean.getQ_caseDate())%>~訖<%=util.View.getPopCalndar("field_Q","q_caseDateE",qBean.getQ_caseDateE())%>		</td>
	</tr>
	<tr>
	<td class="queryTDLable">是否允諾：</td>
	<td class="queryTDInput"><select class="field_Q" type="select" name="q_isAssure">
			<%=util.View.getYNOption(qBean.getQ_isAssure())%>
			</select>
	&nbsp;　允諾日期：<%=util.View.getPopCalndar("field_Q","q_assureDate",qBean.getQ_assureDate())%>~訖<%=util.View.getPopCalndar("field_Q","q_assureDate",qBean.getQ_assureDateE())%>		</td>
	</tr>
	<tr>
		<td class="queryTDLable">辦理機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_orgID",qBean.getQ_orgID(),qBean.getQ_orgIDName())%>		</td>
	</tr>
	<tr>
		<td class="queryTDLable">關鍵字：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_keyWord" size="20" maxlength="50" value="<%=qBean.getQ_keyWord()%>">		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">		</td>
	</tr>
	</table>
