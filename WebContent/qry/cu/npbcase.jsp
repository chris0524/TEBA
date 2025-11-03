<!--
*<br>程式目的：申辦案件狀態查詢
*<br>程式代號：npbcase
*<br>撰寫日期：095/06/15
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="qBean" scope="request" class="qry.cu.QRYNPB001Q">
	<jsp:setProperty name="qBean" property="*"/>
</jsp:useBean>
<%
if ("".equals(qBean.getQ_queryType())) {
	String sQuery = util.Common.checkGet(request.getParameter("sQuery"));
	if (!"".equals(sQuery)) {
		qBean.setQ_queryType(sQuery);
	} else {
		qBean.setQ_queryType("RE");
	}
}


%>
<html>
<head>
<title>民眾申辦案件狀態查詢</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值


/*檢查查詢欄位至少輸入一個*/
function checkQuery(){
	var errorflag=true;
	for(var i=0; i<document.forms[0].elements.length; i++){
		obj = document.forms[0].elements[i];
		if (obj.className=="field_PQ" || obj.className=="field_Q"){	
			if((obj.type=="checkbox")||(obj.type=="radio")){
				if (obj.checked){
					errorflag=false;
					break;				
				}
			}else{
				if ((obj.value!="") && (obj.type!="button")){
					errorflag=false;
					break;
				}
			}
		}	
	}
	if (errorflag){
		return "查詢欄位至少輸入一個!\n";
	}else{
		return "";
	}
}

function checkField(){
	var alertStr="";
	alertStr += checkQuery();
	var arrQFields = "q_signNo1,q_signNo2,q_signNo3,q_signNo4,q_signNo5".split(",");
	var cFlag=false;
	if (arrQFields.length>0) {
	 	for(var i=0; i<arrQFields.length; i++){
	 		if (isObj(document.all.item(arrQFields[i]))) {		 		  	
				if (parse(document.all.item(arrQFields[i]).value).length>0) cFlag=true;
			}
		}
	}
	if (form1.q_applyID.value!="") {
		alertStr += checkPersonalID(form1.q_applyID, "繳款人證號");
	}
	if (cFlag==true) {
		if (form1.q_signNo4.value!="" || form1.q_signNo5.value!="") {
			alertStr += checkEmpty(form1.q_signNo1,"土地/建物標示-縣市");
			alertStr += checkEmpty(form1.q_signNo2,"土地/建物標示-鄉鎮市區");	
			alertStr += checkEmpty(form1.q_signNo3,"土地/建物標示-段");		
			if (form1.q_signNo4.value!="") alertStr += checkInt(form1.q_signNo4,"母號");
			if (form1.q_signNo5.value!="") alertStr += checkInt(form1.q_signNo5,"子號");
			alertStr += checkEmpty(form1.q_propertyType,"房地種類");
		}
	}
	if (alertStr.length!=0) { 
		alert(alertStr);
		return false; 
	} else {
		form1.state.value = "queryAll";
	}
}

function getChange() {
	if (form1.q_propertyNo.value!="") {
		changeSignNo1('signNo1','signNo2','signNo3','E000000');
	}
	if (form1.q_signNo4.value.length>4) form1.q_propertyNo.value="2";
	if (form1.q_signNo5.value.length>3) form1.q_propertyNo.value="1";
}

function init() {
	getSelectedValue(form1.q_queryType,"<%=qBean.getQ_queryType()%>");
}

</script>

</head>
<body topmargin="0" onLoad="showErrorMsg('<%=qBean.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="queryTDLable" colspan="3" style="text-align:center">民眾申辦案件狀態查詢</td>
	</tr>	
	<tr>
		<td class="queryTDLable">查詢類別：</td>
		<td class="queryTDInput" colspan="2">
			<select name="q_queryType" type="select" class="field_Q">
			  <option value="RE">承租案件</option>
			  <option value="SA">申購案件</option>
			  <option value="IN">分期繳款</option>					  						
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">房地種類：</td>
		<td class="queryTDInput" colspan="2">
			<select name="q_propertyType" type="select" class="field_Q">
				<%=util.View.getTextOption("1;土地;2;建物",qBean.getQ_propertyType())%>
			</select>		</td>
	</tr>	
	<tr><td class="queryTDLable">土地/建物標示</td>
	<td class="queryTDInput" colspan="2">
<select type="select" class="field_Q" name="q_signNo1"	onchange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",qBean.getQ_signNo1())%>
		</select>
		  
  <select type="select" class="field_Q" name="q_signNo2" onchange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
			<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=qBean.getQ_signNo2()%>');</script>
		  </select>
		  
  <select type="select" class="field_Q" name="q_signNo3">
			<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=qBean.getQ_signNo3()%>');</script>
		  </select>
&nbsp; 
地/建號：<input class="field_Q" type="text" name="q_signNo4"	size="4" maxlength="5" value="<%=qBean.getQ_signNo4()%>" /> 
&nbsp;-
		  <input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=qBean.getQ_signNo5()%>" />		  </td>
		  </tr>
	<tr>
		<td class="queryTDLable">繳款人證號：</td>
		<td class="queryTDInput" colspan="2"><input class="field" type="text" name="q_applyID" size="10" maxlength="10" value="<%=qBean.getQ_applyID()%>">		</td>	
	</tr>			
	<tr>
		<td class="queryTDLable">繳款人姓名：</td>
		<td class="queryTDInput" colspan="2">
			<input class="field" type="text" name="q_applyName" size="12" maxlength="12" value="<%=qBean.getQ_applyName()%>" >		</td>	
	</tr>
	<tr>
		<td class="queryTDInput" colspan="3" style="text-align:center;">
			<input type="hidden" name="state" value="init">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >		</td>
	</tr>
	</table>
</td></tr></table>	
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg">
<%=qBean.getDisplayList()%>
</td></tr>
</table>
</form>
</body>
</html>
