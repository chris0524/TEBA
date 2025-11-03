<!--
*<br>程式目的：國有財產增減結存表
*<br>程式代號：untsr001r
*<br>撰寫日期：
*<br>程式作者：YuanRen
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.sr.UNTSR001R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
obj.setQ_ownership("2");
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
<script language="javascript" src="../../js/TJS.js"></script>
<script language="javascript">
var readbg="#EEEEEE";
var editbg="#FFFFFF";
//var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
//	alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
//	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.q_enterDateS,"異動日期起");
	alertStr += checkDate(form1.q_enterDateS,"異動日期起");
	alertStr += checkEmpty(form1.q_enterDateE,"異動日期訖");
	alertStr += checkDate(form1.q_enterDateE,"異動日期訖");
//	form1.isorganmanagerT.value=form1.q_isorganmanager.value;
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function alteredEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function changeReportType( o ){
	form1.q_enterDateS.value = "";
	form1.q_enterDateE.value = "";
	form1.q_reportMonth.value = "";
	form1.q_reportSeason.value = "";
	form1.q_periodType.value = "";
	if( o == '1'){
		setFormItem("q_enterDateS,btn_q_enterDateS,q_enterDateE,btn_q_enterDateE","R");
		setFormItem("q_periodType,q_reportSeason","R");
		setFormItem("q_reportYear,q_reportMonth","O");
		
	}else if( o == '2'){
		setFormItem("q_enterDateS,btn_q_enterDateS,q_enterDateE,btn_q_enterDateE","R");
		setFormItem("q_periodType,q_reportYear,q_reportMonth","R");
		setFormItem("q_reportYear,q_reportSeason","O");
		
	}else if( o == '3'){
		setFormItem("q_enterDateS,btn_q_enterDateS,q_enterDateE,btn_q_enterDateE","R");
		setFormItem("q_reportSeason,q_reportYear,q_reportMonth","R");
		setFormItem("q_reportYear,q_periodType","O");
		
	}else if( o == '4'){
		setFormItem("q_periodType,q_reportSeason,q_reportYear,q_reportMonth","R");
		setFormItem("q_enterDateS,btn_q_enterDateS,q_enterDateE,btn_q_enterDateE","O");
		form1.q_reportYear.value = "";s
		
	}
}


function DateControl( o ){
	if(form1.q_reportYear.value != "" && form1.q_reportYear.value != null){
		if( o == '1' ){
			if(form1.q_reportMonth.value != "" && form1.q_reportMonth.value != null){
				form1.q_enterDateS.value = form1.q_reportYear.value + form1.q_reportMonth.value + "01";
				var nextMonth = getDateAdd("m",1,form1.q_reportYear.value + form1.q_reportMonth.value + "01");
					form1.q_enterDateE.value = getDateAdd("d",-1,nextMonth);
			}
		}else if( o == '2'){
			if(form1.q_reportSeason.value=="1"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"0101";
				form1.q_enterDateE.value = form1.q_reportYear.value +"0331";
			}else if(form1.q_reportSeason.value=="2"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"0401";
				form1.q_enterDateE.value = form1.q_reportYear.value +"0630";
			}else if(form1.q_reportSeason.value=="3"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"0701";
				form1.q_enterDateE.value = form1.q_reportYear.value +"0930";
			}else if(form1.q_reportSeason.value=="4"){
				form1.q_enterDateS.value = form1.q_reportYear.value +"1001";
				form1.q_enterDateE.value = form1.q_reportYear.value +"1231";
			}
			
		}else if( o == '3'){
			if(form1.q_periodType.value=="1"){
				form1.q_enterDateS.value = form1.q_reportYear.value+"0101";
				form1.q_enterDateE.value = form1.q_reportYear.value+"0630";
			}else if(form1.q_periodType.value=="2"){
				form1.q_enterDateS.value = form1.q_reportYear.value+"0701";
				form1.q_enterDateE.value = form1.q_reportYear.value+"1231";
			}
			
		}
	}
}

function init(){
	changeReportType('1');
	//form1.ownership.value = '1';
}

//列印報表按鈕
function printReportChang(str){	
	if(checkField()==false){
		
	}else{
		var url = "untsr001p.jsp?processType="+str+"&q_enterDateS="+form1.q_enterDateS.value+"&q_enterDateE="+form1.q_enterDateE.value;
		window.open(url);
	}
}
</script>

</head>
<body topmargin="10" onload="init();">
<form id="form1" name="form1" method="post" action="untsr001p.jsp" onReset="init();" onSubmit="return checkField();" >
<table class="bg" width="60%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">國有財產增減結存表<font color="blue" size="2">(A4橫式)</font></td>        
	</tr>
	
  
	<tr>
		<td class="queryTDLable" width="30%"><font color="red">*</font>報表類別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportType" onChange="changeReportType(this.value);">
	            <option value='1'>月報</option>
	            <option value='2'>季報</option>
	            <option value='3'>半年報</option>
<!-- 	            
	            <option value='4'>其他</option>
-->	            			
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">年度：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportYear" size="3" maxlength="3" value="<%=obj.getQ_reportYear()%>" onChange="doInt(this.name,'年度');doFrontZero(this.name,3);DateControl(form1.q_reportType.value);">
			<font color="red">(YYY)</font>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">月份：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_reportMonth" size="2" maxlength="2" value="<%=obj.getQ_reportMonth()%>" onChange="if(doInt(this.name,'月份')){checkMM(this.name);};doFrontZero(this.name,2);DateControl(form1.q_reportType.value);">
			<font color="red">(MM)</font>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">期別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_periodType" onChange="DateControl(form1.q_reportType.value);">
				<%=util.View.getTextOption("1;半年報(上半年);2;半年報(下半年)",obj.getQ_periodType())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">季別：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_reportSeason" onChange="DateControl(form1.q_reportType.value);">
				<%=util.View.getTextOption("1;第一季;2;第二季;3;第三季;4;第四季",obj.getQ_reportSeason())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>異動日期：</td>
		<td class="queryTDInput">
 		
			起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>
			訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
 
		</td>
	</tr>
	
	
	<tr>
        <td class="td_form" colspan="2" style="text-align:center">報表列印</td>
	</tr>
	
	<tr>
		<td class="queryTDInput" style="text-align:center" colspan="2">
			<input class="toolbar_default" followPK="false" type="button" name="doProcess01" value="            列印國有財產增減結存表             " onclick="printReportChang('ALL');">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" style="text-align:center" colspan="2">
			<input class="toolbar_default" followPK="false" type="button" name="doProcess02" value="   列印國有財產增減結存表(珍貴財產)  " onclick="printReportChang('VALUABLE');">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" style="text-align:center" colspan="2">
			<input class="toolbar_default" followPK="false" type="button" name="doProcess03" value="列印國有財產增減結存表 (非珍貴財產)" onclick="printReportChang('UNVALUABLE');">
			
			<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
			<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
			<input type="hidden" name="isorganmanagerT" value="">		
			<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
			<input type="hidden" name="q_ownership" value="1">
			<input type="hidden" name="q_valueable" value="N">
		    <input type="hidden" name="q_verify" value="Y">
		    <input type="hidden" name="editID" value="<%=user.getUserID()%>">
		</td>
	</tr>
	
	</table>
</td></tr></table>	
</form>
</body>
</html>
