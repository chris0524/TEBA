<!--
*<br>程式目的： 國有財產局交換媒體檔－建物財產卡
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR007R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>

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
var readbg="#EEEEEE";
var editbg="#FFFFFF";
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";

	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
	
	alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");	
	alertStr += checkEmpty(form1.q_orgBatchChange,"機關整批置換");	
    if(form1.q_orgBatchChange.value=='Y'){
	alertStr += checkDate(form1.q_enterDateE,"截止日期");
	alertStr += checkEmpty(form1.q_enterDateE,"截止日期");
    }
    if(form1.q_orgBatchChange.value=='N'){
    alertStr += checkDate(form1.q_changeDateS,"異動日期起");
    alertStr += checkEmpty(form1.q_changeDateS,"異動日期起");
    alertStr += checkDate(form1.q_changeDateE,"異動日期訖");
    alertStr += checkEmpty(form1.q_changeDateE,"異動日期訖");
    }
	if(alertStr.length!=0){ alert(alertStr); return false; }

}

function DateControl(){
	if(form1.q_reportYear.value != "" && form1.q_reportYear.value != null && form1.q_reportMonth.value != "" && form1.q_reportMonth.value != null){
		form1.q_enterDateS.value = form1.q_reportYear.value + form1.q_reportMonth.value + "01";
		var nextMonth = getDateAdd("m",1,form1.q_reportYear.value + form1.q_reportMonth.value + "01");
			form1.q_enterDateE.value = getDateAdd("d",-1,nextMonth);
	}
}

//*********************************************
//函數功能：檢查資料年是否正確(YYY)
//*********************************************
function checkYYY(column){
	var x=document.getElementsByName(column);
	var tDate = x[0].value;
	
	if(isNaN(tDate)){
		x[0].style.backgroundColor=errorbg;
		alert("日期格式不符!請輸入正確的民國日期(YYY)!\n");
		return;
	}
	if(tDate.length!=3){	
		x[0].style.backgroundColor=errorbg;	
		alert("日期格式不符!請輸入正確的民國日期(YYY)!\n");
		return;
	}
	year=eval(tDate.substring(0,3));
      
	if(isNaN(year)) {
		x[0].style.backgroundColor=errorbg;
		alert("日期格式不符!請輸入正確的民國日期(YYY)\n");
		return;
	} else if(year+1911 > 3000 || year+1911 < 1900) {
		x[0].style.backgroundColor=errorbg;
		alert("年份不符!\n");
		return;
	}
	 x[0].style.backgroundColor="";
	 return "";
}

//*********************************************
//檢查資料月是否正確(MM)
//*********************************************
function checkMM(column){
	var x=document.getElementsByName(column);
	var tDate = x[0].value;

	if(isNaN(tDate)){
		x[0].style.backgroundColor=errorbg;
		alert("日期格式不符!請輸入正確的民國日期(MM)!\n");
		return;
	}
	if(tDate.length!=2){	
		x[0].style.backgroundColor=errorbg;	
		alert("日期格式不符!請輸入正確的民國日期(MM)!\n");
		return;
	}
	month=eval(tDate.substring(0,2));
       
    if(isNaN(month)) {
    	x[0].style.backgroundColor=errorbg;
		alert("日期格式不符!請輸入正確的民國日期(MM)\n");
		return;
	} else if(month > 12 || month < 1) {
		x[0].style.backgroundColor=errorbg;
	    alert("月份不符!\n");
	    return;
    }
    x[0].style.backgroundColor="";
    return "";
}


function init(){
	form1.q_enterOrg.value = '<%=user.getOrganID()%>';
	form1.q_enterOrgName.value = '<%=user.getOrganName()%>';
	initFormDisabled();
}
function initFormDisabled(){
	$("#div_date1").hide();
	$("#div_date2").hide();
	
}
function changeDate(){

	if(form1.q_orgBatchChange.value=='Y')
	{
		$("#div_date1").show();	
		$("#div_date2").hide();}
	else if (form1.q_orgBatchChange.value=='N'){
		$("#div_date1").hide();
		$("#div_date2").show();
		}
}

function popCheckSignno() {
	var url="untsr007p.jsp?checkSignno=Y" 
			+ "&q_enterOrg=" + form1.q_enterOrg.value; 
	
	window.open(url);
}
</script>
		    
</head>
<body topmargin="10" onload="init();">
<form id="form1" name="form1" method="post" action="untsr007p.jsp" onReset="init();" onSubmit="return checkField();" target="_blank">
<!--隱藏欄位===========================================================-->

<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="editID" value="<%=user.getUserID()%>">
<input type="hidden" name="q_ownership" value="1">
<input type="hidden" name="q_verify" value="Y">
<input type="hidden" name="state" value="<%=obj.getState()%>">
   
<table class="bg" width="70%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">國產署交換媒體檔－建物財產卡</td>
	</tr>
	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName())%>
		</td>
	</tr>
	<tr>
		  <td class="queryTDLable" ><font color="red">*</font>財產區分別：</td>
		  <td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_differenceKind">
          <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' and codeid in ('110','120') ",obj.getQ_differenceKind())%>
        </select>	  	 
		  </td>
	</tr>
	<tr>
  	    <td class="td_form"><font color="red">*</font>機關整批置換：</td>
    	<td colspan="3" class="td_form_white"><select class="field" type="select" name="q_orgBatchChange" onchange="changeDate();">
        	<%=util.View.getYNOption(obj.getQ_orgBatchChange())%>
			</select>           
      	</td>
    </tr>
	<tr id="div_date1">
		<td class="queryTDLable"><font color="red">*</font>截止日期：</td>
		<td class="queryTDInput">
			<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		</td>
	</tr>
		<tr id="div_date2">
		<td class="queryTDLable"><font color="red">*</font>異動日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_changeDateS",obj.getQ_changeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_changeDateE",obj.getQ_changeDateE())%>
		</td>	
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="button" name="checkSignno" value="檢核建物標示"  onclick="popCheckSignno()">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >

		</td>
	</tr>
	</table>
</td></tr></table>	

</form>
</body>
</html>