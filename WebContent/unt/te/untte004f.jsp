<!--
程式目的：地價稅課稅明細異動作業
程式代號：untte004f
程式日期：0960905
程式作者：blair
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.te.UNTTE004F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.te.UNTTE004F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.lndYYY,"課稅年度");
		alertStr += checkYYY(form1.lndYYY,"課稅年度");
		alertStr += checkEmpty(form1.lndLosn,"稅籍編號");
		alertStr += checkEmpty(form1.lndLosn,"稅籍編號");
		alertStr += checkEmpty(form1.signNO,"土地標示");
		alertStr += checkEmpty(form1.signNO,"土地標示");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(lndYYY,lndLosn,signNO){
	form1.lndYYY.value=lndYYY;
	form1.lndLosn.value=lndLosn;
	form1.signNO.value=signNO;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}
function init(){
		setFormItem("checkPDdata","O");
		setFormItem("checkDelData","O");	
		setFormItem("checkInsData","O");	
}

function clickUPload(){
	var prop="";
	var windowTop=(document.body.clientHeight)/2+50;
	var windowLeft=(document.body.clientWidth-800)/2+250;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width=700,height=250,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	var checkupLoad="";

    var url="untte004update1.jsp";
	pd001fupload = window.open(url,'myWindow',prop);
	document.all('state').value='queryOne';

}

function clickDelData(){
	var prop="";
	var windowTop=(document.body.clientHeight)/2+50;
	var windowLeft=(document.body.clientWidth-800)/2+250;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width=700,height=250,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	var checkupLoad="";

    var url="untte004update3.jsp";
	pd001fupload = window.open(url,'myWindow',prop);
	document.all('state').value='queryOne';

}

function clickInsData(){
	var prop="";
	var windowTop=(document.body.clientHeight)/2+50;
	var windowLeft=(document.body.clientWidth-800)/2+250;
	prop=prop+"scrollbars=1, resizable=yes, status=yes, toolbar=no,menubar=no,";
	prop=prop+"width=500,height=250,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	var checkupLoad="";

    var url="untte004insert.jsp";
    url = url + "?p_yymm=" + getDateAdd("y",-1,getToday()).substring(0,3);
	pd001fupload = window.open(url,'myWindow',prop);
	document.all('state').value='queryOne';
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:700px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" width="10%">課稅年度：</td>
		<td class="queryTDInput" width="30%">
			<input class="field_Q" type="text" name="q_lndYYY" size="3" maxlength="3" value="<%=obj.getQ_lndYYY()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">稅籍編號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_lndLosn" size="12" maxlength="12" value="<%=obj.getQ_lndLosn()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">土地/建物標示：</td>		
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
			</select>
			&nbsp;
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=obj.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=obj.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">

			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer" style="height:260px">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="16%"><font color="red">*</font>課稅年度：</td>
		<td class="td_form_white" width="32%">
			[<input class="field_RO" type="text" name="lndYYY" size="3" maxlength="3" value="<%=obj.getLndYYY()%>">]
		</td>
		<td class="td_form"><font color="red">*</font>稅籍編號：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="lndLosn" size="12" maxlength="12" value="<%=obj.getLndLosn()%>">]
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>土地標示：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="signNO" size="15" maxlength="15" value="<%=obj.getSignNO()%>">]
		</td>
		<td class="td_form">段 小段 中文名：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="secChiNM" size="40" maxlength="40" value="<%=obj.getSecChiNM()%>">
		</td>
	</tr>
	<tr bgcolor="#FFF6D9">
		<td colspan="4" size="12pt">&nbsp;&nbsp;課稅明細(一)：</td>
	</tr>
	<tr>
	    <td class="td_form">稅地種類(一)：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="lndTaxTp1" size="1" maxlength="1" value="<%=obj.getLndTaxTp1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">課稅面積(一)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="levyArea1" size="9" maxlength="9" value="<%=obj.getLevyArea1()%>">
		</td>
		<td class="td_form">部分減免面積(一)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateArea1" size="9" maxlength="9" value="<%=obj.getAbateArea1()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">全免減免面積(一)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateAll1" size="9" maxlength="9" value="<%=obj.getAbateAll1()%>">
		</td>
		<td class="td_form">地價稅額(一)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="lndPreTax1" size="9" maxlength="9" value="<%=obj.getLndPreTax1()%>">
		</td>
	</tr>
	<tr bgcolor="#FFF6D9">
		<td colspan="4" size="12pt">&nbsp;&nbsp;課稅明細(二)：</td>
	</tr>
	<tr>
	    <td class="td_form">稅地種類(二)：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="lndTaxTp2" size="1" maxlength="1" value="<%=obj.getLndTaxTp2()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">課稅面積(二)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="levyArea2" size="9" maxlength="9" value="<%=obj.getLevyArea2()%>">
		</td>
		<td class="td_form">部分減免面積(二)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateArea2" size="9" maxlength="9" value="<%=obj.getAbateArea2()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">全免減免面積(二)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateAll2" size="9" maxlength="9" value="<%=obj.getAbateAll2()%>">
		</td>
		<td class="td_form">地價稅額(二)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="lndPreTax2" size="9" maxlength="9" value="<%=obj.getLndPreTax2()%>">
		</td>
	</tr>
	<tr bgcolor="#FFF6D9">
		<td colspan="4" size="12pt">&nbsp;&nbsp;課稅明細(三)：</td>
	</tr>
	<tr>
		<td class="td_form">稅地種類(三)：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="lndTaxTp3" size="1" maxlength="1" value="<%=obj.getLndTaxTp3()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">課稅面積(三)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="levyArea3" size="9" maxlength="9" value="<%=obj.getLevyArea3()%>">
		</td>
		<td class="td_form">部分減免面積(三)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateArea3" size="9" maxlength="9" value="<%=obj.getAbateArea3()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">全免減免面積(三)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateAll3" size="9" maxlength="9" value="<%=obj.getAbateAll3()%>">
		</td>
		<td class="td_form">地價稅額(三)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="lndPreTax3" size="9" maxlength="9" value="<%=obj.getLndPreTax3()%>">
		</td>
	</tr>
	<tr bgcolor="#FFF6D9">
		<td colspan="4" size="12pt">&nbsp;&nbsp;課稅明細(四)：</td>
	</tr>
	<tr>
		<td class="td_form">稅地種類(四)：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="lndTaxTp4" size="1" maxlength="1" value="<%=obj.getLndTaxTp4()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">課稅面積(四)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="levyArea4" size="9" maxlength="9" value="<%=obj.getLevyArea4()%>">
		</td>
		<td class="td_form">部分減免面積(四)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateArea4" size="9" maxlength="9" value="<%=obj.getAbateArea4()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">全免減免面積(四)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateAll4" size="9" maxlength="9" value="<%=obj.getAbateAll4()%>">
		</td>
		<td class="td_form">地價稅額(四)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="lndPreTax4" size="9" maxlength="9" value="<%=obj.getLndPreTax4()%>">
		</td>
	</tr>
		<tr bgcolor="#FFF6D9">
		<td colspan="4" size="12pt">&nbsp;&nbsp;課稅明細(五)：</td>
	</tr>
	<tr>
		<td class="td_form">稅地種類(五)：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="lndTaxTp5" size="1" maxlength="1" value="<%=obj.getLndTaxTp5()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">課稅面積(五)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="levyArea5" size="9" maxlength="9" value="<%=obj.getLevyArea5()%>">
		</td>
		<td class="td_form">部分減免面積(五)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateArea5" size="9" maxlength="9" value="<%=obj.getAbateArea5()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">全免減免面積(五)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="abateAll5" size="9" maxlength="9" value="<%=obj.getAbateAll5()%>">
		</td>
		<td class="td_form">地價稅額(五)：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="lndPreTax5" size="9" maxlength="9" value="<%=obj.getLndPreTax5()%>">
		</td>
	</tr>

	<tr bgcolor="#FFF6D9">
		<td colspan="4" size="12pt">&nbsp;&nbsp;其它：</td>
	</tr>
	<tr>
		<td class="td_form">OD：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="OD" size="1" maxlength="1" value="<%=obj.getOD()%>">
		</td>
		<td class="td_form">OA：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="OA" size="1" maxlength="1" value="<%=obj.getOA()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
		<td class="td_form">&nbsp;</td>
		<td class="td_form_white">&nbsp;</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">

	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	
	<!--新增-->
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	
	<span id="btnReadOnly1">
	<input class="toolbar_default" type="button" followPK="true" name="checkPDdata" value="上傳課稅明細資料" disabled="true" onClick="clickUPload();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="true" name="checkDelData" value="批次刪除" disabled="true" onClick="clickDelData();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="true" name="checkInsData" value="挑選新增" disabled="true" onClick="clickInsData();">&nbsp;|
	</span>
</td></tr>

<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">課稅年度</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">稅籍編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true};
	boolean displayArray[] = {true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



