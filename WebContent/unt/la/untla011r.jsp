<!--
*<br>程式目的：土地財產卡查詢檔 
*<br>程式代號：untla011r
*<br>撰寫日期：94/12/05
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA011R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
obj.setQ_ownership("1");
%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>

<script language="javascript">
//var insertDefault;	//二維陣列, 新增時, 設定預設值
//insertDefault = new Array(
//	new Array("q_signNo1","S000000")
//);
function checkField(){
    //return true;
	var alertStr="";
	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	alertStr += checkDate(form1.q_enterDateS,"入帳日期－起");
	alertStr += checkDate(form1.q_enterDateE,"入帳日期－訖");
	alertStr += checkDate(form1.q_writeDateS,"填單日期－起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期－訖");
	
	var err1=checkAllValue( [form1.q_propertyNo1,form1.q_propertyNo2] ,["財產編號－起","財產編號－訖"] );
	var err2=checkAllValue( [form1.q_enterDateS,form1.q_enterDateE] ,["入帳日期－起","入帳日期－訖"] );
	var err3=checkAllValue( [form1.q_proofYear,form1.q_proofNoS,form1.q_proofNoE] ,["增加單編號","編號－起","編號－訖"] );
	
	if(err1 != "" && err2 !="" && err3 !=""){
		alertStr += "財產編號－起訖, 入帳日期－起訖, 增加單編號－起訖 \n三組須任一組都有值!";
	}	
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	//beforeSubmit();	
    //form1.submit();
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function check_reset(){
	form1.q_enterOrg.value='<%=user.getOrganID()%>';
	alteredEnterOrg();
}
function changePropertyNo(propertyNo){}

function init() {	
	form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
}
</script>

</head>
<body topmargin="10" onload="closebar();init();">

<form id="form1" name="form1" method="post" action="untla011p.jsp" onReset="check_reset();" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="q_verify" value="Y">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">	
<input type="hidden" name="objPath" >
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  cellspacing="0" cellpadding="0" border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">土地財產卡<font color="red">(A4 直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" ><font color="red">*</font>入帳機關 :</td>
		<td class="queryTDInput" >
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N")%>
		</td>
	
		<td class="queryTDLable">權屬 :</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
				<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	<tr>
			<td class="queryTDLable">電腦單號 :</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
		</td>

		<td class="queryTDLable">入帳日期 :</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號 :</td>
		<td class="queryTDInput" colspan="3">
			起<%=util.View.getPopProperty("field_Q","q_propertyNo1",obj.getQ_propertyNo1(),obj.getQ_propertyNo1Name(),"1")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNo2",obj.getQ_propertyNo2(),obj.getQ_propertyNo2Name(),"1")%>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產分號 :</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
		</td>
		<td class="queryTDLable">資料狀態 :</td>
			<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<%=util.View.getTextOption("1;現存;2;已減損","1")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">土地標示 :</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
			</select>
			&nbsp;地號
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=obj.getQ_signNo4()%>" onChange="addChar(this ,4);">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=obj.getQ_signNo5()%>" onChange="addChar(this ,4);">		
		</td> 
	</tr>
	<tr>
		<td class="queryTDLable">財產性質 :</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>
		</td>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD'", obj.getQ_fundType())%>
			</select> 
        </td>
	</tr>
	<tr>
		<td class="queryTDLable">珍貴財產 :</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
				<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>
		</td>
		<td class="queryTDLable">抵繳遺產稅 :</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_taxCredit">
				<%=util.View.getYNOption(obj.getQ_taxCredit())%>
			</select>
		</td>
		
	</tr>
	<tr>
		<td class="queryTDLable">填單日期 :</td>
		<td class="queryTDInput" colspan="3">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%>
		</td>
	</tr>
	
	<tr>
		<td class="queryTDLable">增加單編號 :</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_proofYear" size="5" maxlength="5" value="<%=obj.getQ_proofYear()%>">年
			<input class="field_Q" type="text" name="q_proofDoc" size="10" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("Excel")%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<font color="red">本列印作業，顯示列印一次最多100筆</font>
		</td>
	</tr>	
	</table>
</td></tr></table>	
</form>
</body>
</html>
