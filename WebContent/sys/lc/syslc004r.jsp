<!--
*<br>程式目的：土地產籍與地政地籍比對差異清冊
*<br>程式代號：syslc004r
*<br>撰寫日期：2014/09/30
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.lc.SYSLC004R">
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
<script type="text/javascript" src="../../js/untgr.js"></script>
<script language="javascript" src="../../js/getAutoCheckColumn.js"></script>
<script language="javascript" src="../../js/getVerifyChangFunction.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript">

function checkField() {
	var alertStr="";
	var strCheck="";
	alertStr += checkEmpty(form1.q_tranYYYMM,"比對年月");
	alertStr += checkYYYMM(form1.q_tranYYYMM,"比對年月");

	//alertStr += checkEmpty(form1.signNo1,"土地標示－縣市");
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function checkNonobtain(selectOne){	
    var obj=document.getElementsByName(selectOne);

    if (obj[0].checked == true){
        obj[0].value="1"
    }else{
        obj[0].value="0"
    }
}

function getPDSwitch(){
	
	//傳送JSON
	$.post('../../home/T_PDSwitch.jsp',
		'',
		function(data){
			//將回傳資料寫入
			data=eval('('+data+')');

			$('#syslc004_userdept').val(data['syslc004_userdept']);			
		});
	
}

</script>
</head>
<body topmargin="10" onLoad="getPDSwitch();">

<form id="form1" name="form1" method="post" action="syslc004p.jsp" onSubmit="return checkField()" target="_blank">
<table class="bg" width="100%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<input type="hidden" id="syslc004_userdept" name="syslc004_userdept" value="<%=obj.getSyslc004_userdept()%>">	
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">土地產籍與地政地籍比對差異清冊<font color="red">(A4直式)</font></td>
	</tr>
	<tr>
		<td class="queryTDLable" >機關：</td>
        <td class="queryTDInput" colspan="3">
	  	    <%=util.View.getPopOrgan("field_RO","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>		
	</tr>
	<tr>
		<td class="queryTDLable" width="30%"><font color="red">*</font>比對年月：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field" type="select" name="q_tranYYYMM">
				<%=util.View.getSingleOption(" select distinct ( case when LEN(CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911)) <3 then '0'+CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911) else  CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911) end + substring(tranyyymm,5,2) ) transyyymm from SYSLC_LAND  order by transyyymm desc ",obj.getQ_tranYYYMM())%>
			</select>
		</td>
	</tr>
 	
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="2">
			<select class="field" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
			</select>			
			<select class="field_Q" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');">
				<% if("".equals(Common.get(obj.getSignNo1()))){ %>
								<script>changeSignNo1('q_lsignNo1','q_lsignNo2','q_lsignNo3','<%=obj.getSignNo2()%>');</script>
				<% } else {%>
								<%=util.View.getOption("select signno, signname from SYSCA_SIGN  where signno like '" + Common.esapi(obj.getSignNo1().substring(0,1)) + "__0000' and signNo not like '" + Common.esapi(obj.getSignNo1().substring(0,1)) + "000000' " , obj.getSignNo2())%>
				<% } %>
				
			</select>		
			<select class="field_Q" type="select" name="signNo3">
				<% if("".equals(Common.get(obj.getSignNo2()))){ %>
								<script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
				<% } else {%>
								<%=util.View.getOption("select signno, signname from SYSCA_SIGN  where signno like '" + Common.esapi(obj.getSignNo2().substring(0,3)) + "____' and signNo not like '" + Common.esapi(obj.getSignNo2().substring(0,3)) + "0000' " , obj.getSignNo3())%>
				<% } %>
			</select>		
		</td>
		<td class="queryTDInput">
			地號：
		<!--  	起：-->
			<input class="field" type="text" name="signNo4" size="8" maxlength="8" value="<%=obj.getSignNo4()%>">
			<!--  
			<br>
			&nbsp;　　　訖：
			<input class="field" type="text" name="" size="8" maxlength="8" value="">
			-->
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;" >
			<input class="field_Q" type="checkbox" name="q_causeName1" size="11"  value="1" CHECKED>地政資料未存於土地主檔
			<br>
			<input class="field_Q" type="checkbox" name="q_causeName2" size="11"  value="2" CHECKED>土地主檔未存於地政資料
			<br>
			<input class="field_Q" type="checkbox" name="q_causeName3" size="4"  value="3" CHECKED>內容異動
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>		
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="field_P" type="hidden" name="editID" value="<%=user.getUserID()%>">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消" >
		</td>
	</tr>
		<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
		<input type="hidden" name="editID" value="<%=session.getAttribute("editID")%>">
	</table>
</td></tr></table>	
</form>
</body>
</html>
