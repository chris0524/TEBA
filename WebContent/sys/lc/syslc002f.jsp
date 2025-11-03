<!--
程式目的：地政機關土地資料查詢
程式代號：syslc002f
程式日期：2014/09/29
程式作者：Mike Kao
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.lc.SYSLC002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.lc.SYSLC002F)obj.queryOne();
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
function checkField(){
	var alertStr="";
	
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(tranYYYMM,lsignNo1,lsignNo2,lsignNo3,lsignNo4,registerserialNo){
	form1.tranYYYMM.value=tranYYYMM;
	form1.signNo1.value=lsignNo1;
	form1.signNo2.value=lsignNo2;
	form1.signNo3.value=lsignNo3;
	form1.lsignNo4.value=lsignNo4;
	form1.registerserialNo.value=registerserialNo;
	
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}
function init(){
	document.all.item("spanClear").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
}

function checkDelete(){
	form1.state.value="delete";
	beforeSubmit();
	form1.submit();
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

	<input type="hidden" class="field" name="signNo1" value="<%=obj.getSignNo1()%>" size="11" maxlength="11">
	<input type="hidden" class="field" name="signNo2" value="<%=obj.getSignNo2()%>" size="11" maxlength="11">
	<input type="hidden" class="field" name="signNo3" value="<%=obj.getSignNo3()%>" size="11" maxlength="11">

<!--批次刪除區============================================================-->
<div id="deleteContainer" style="position:absolute;z-index: 25;left:0;top :0;width:350px;height:150px;display:none">
	<iframe id="deleteContainerFrame" style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>
	<div class="queryTitle">批次刪除視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" >機關：</td>
        <td class="queryTDInput" colspan="3">
	  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>		
	</tr>
	<tr>
		<td class="queryTDLable" width="30%">轉入年月：</td>
		<td class="queryTDInput" colspan="3">
		<select class="field_Q" type="select" name="d_tranYYYMM">
			<%=util.View.getSingleOption(" select distinct ( case when LEN(CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911)) <3 then '0'+CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911) else  CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911) end + substring(tranyyymm,5,2) ) transyyymm from SYSLC_LAND  order by transyyymm desc ",obj.getD_tranYYYMM())%>
		</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onclick="checkDelete();">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('deleteContainer');whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:680px;height:250px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	
	<tr>
		<td class="queryTDLable" >機關：</td>
        <td class="queryTDInput" colspan="3">
	  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>		
	</tr>
	<tr>
		<td class="queryTDLable" width="10%">轉入年月：</td>
		<td class="queryTDInput" colspan="3">
		<select class="field_Q" type="select" name="q_tranYYYMM">
			<%=util.View.getSingleOption(" select distinct ( case when LEN(CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911)) <3 then '0'+CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911) else  CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),tranyyymm,20) - 1911) end + substring(tranyyymm,5,2) ) transyyymm from SYSLC_LAND  order by transyyymm desc ",obj.getQ_tranYYYMM())%>
		</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">土地標示：</td>		
		<td class="queryTDInput" colspan="2">
			<select class="field_Q" type="select" name="q_lsignNo1" onChange="changeSignNo1('q_lsignNo1','q_lsignNo2','q_lsignNo3','');">
				<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_lsignNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_lsignNo2" onChange="changeSignNo2('q_lsignNo1','q_lsignNo2','q_lsignNo3','');">
				<% if("".equals(Common.get(obj.getQ_lsignNo1()))){ %>
								<script>changeSignNo1('q_lsignNo1','q_lsignNo2','q_lsignNo3','<%=obj.getQ_lsignNo2()%>');</script>
				<% } else {%>
								<%=util.View.getOption("select signno, signname from SYSCA_SIGN  where signno like '" + Common.esapi(obj.getQ_lsignNo1().substring(0,1)) + "__0000' and signNo not like '" + Common.esapi(obj.getQ_lsignNo1().substring(0,1)) + "000000' " , obj.getQ_lsignNo2())%>
				<% } %>
				
			</select>		
			<select class="field_Q" type="select" name="q_lsignNo3">
				<% if("".equals(Common.get(obj.getQ_lsignNo2()))){ %>
								<script>changeSignNo2('q_lsignNo1','q_lsignNo2','q_lsignNo3','<%=obj.getQ_lsignNo3()%>');</script>
				<% } else {%>
								<%=util.View.getOption("select signno, signname from SYSCA_SIGN  where signno like '" + Common.esapi(obj.getQ_lsignNo2().substring(0,3)) + "____' and signNo not like '" + Common.esapi(obj.getQ_lsignNo2().substring(0,3)) + "0000' " , obj.getQ_lsignNo3())%>
				<% } %>
			</select>
		</td>
		<td class="queryTDInput">
			地號：
		<!--	起：   -->
			<input class="field_Q" type="text" name="q_lsignNo4" size="8" maxlength="8" value="<%=obj.getQ_lsignNo4()%>">
		<!--	<br>
		   	&nbsp;　　　訖：
			<input class="field_Q" type="text" name="" size="8" maxlength="8" value="">
	    -->
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">申報地價年度：</td>
		<td class="queryTDInput" width="30%" colspan="3">
		<select class="field_Q" type="select" name="q_valueDate">
		<%=util.View.getSingleOption(" select distinct ( case when LEN(CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),valuedate,20) - 1911)) <3 then '0'+CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),valuedate,20) - 1911) else  CONVERT(VARCHAR(3),CONVERT(VARCHAR(4),valuedate,20) - 1911) end + substring(valuedate,5,2) ) vdate from SYSLC_LAND order by vdate desc ",obj.getQ_valueDate())%>
		</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">面積：</td>
		<td class="queryTDInput" colspan="3">
			起：<input class="field_Q" type="text" name="q_areaS" size="25" maxlength="25" value="<%=obj.getQ_areaS()%>">&nbsp;-&nbsp;
			訖：<input class="field_Q" type="text" name="q_areaE" size="25" maxlength="25" value="<%=obj.getQ_areaE()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">使用分區：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_useSeparate">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getQ_useSeparate())%>				
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">編定使用種類：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_useKind">
				<%=util.View.getOption("select codeid,codename from Sysca_Code where codekindid='UKD'", obj.getQ_useKind())%>				
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="queryTDLable" >機關：</td>
        <td class="queryTDInput" colspan="3">
	  	    <%=util.View.getPopOrgan("field_RO","enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>		
	</tr>
	<tr>
		<td class="td_form" width="20%">轉入年月：</td>
		<td class="td_form_white">
			<input class=field type="text" name="tranYYYMM" size="5" maxlength="5" value="<%=obj.getTranYYYMM()%>">
		</td>
		<td class="td_form">登記次序：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="registerserialNo" size="15" maxlength="15" value="<%=obj.getRegisterserialNo()%>" >
		</td>
	</tr>
	<tr>
		<td class="td_form" >統一編號：</td>
		<td class="td_form_white" colspan="3">
			<input class=field type="text" name="serialNo" size="15" maxlength="15" value="<%=obj.getSerialNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form" >姓名：</td>
		<td class="td_form_white" colspan="3">
			<input class=field type="text" name="name" size="50" maxlength="50" value="<%=obj.getName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">土地標示代碼：</td>
			<td class="td_form_white" colspan="7">
			<select class="field_RO" type="select" name="lsignNo1" onChange="changeSignNo1('lsignNo1','lsignNo2','lsignNo3','');">
				<%=util.View.getOption(	"select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getLsignNo1())%>
			</select> 
			<select class="field_RO" type="select" name="lsignNo2"	onChange="changeSignNo2('lsignNo1','lsignNo2','lsignNo3','');">
				<script>changeSignNo1('lsignNo1','lsignNo2','lsignNo3','<%=obj.getLsignNo2()%>');</script>
			</select> 
			<select class="field_RO" type="select" name="lsignNo3">
				<script>changeSignNo2('lsignNo1','lsignNo2','lsignNo3','<%=obj.getLsignNo3()%>');</script>
			</select> 
			地號：[ <input class="field_RO" type="text" name="lsignNo4" size="8"	maxlength="8" value="<%=obj.getLsignNo4()%>">]
		</td>						
	</tr>
	<tr>
		<td class="td_form" width="20%">面積：</td>
		<td class="td_form_white" width="30%">
			<input class="field" type="text" name="area" size="15" maxlength="15" value="<%=obj.getArea()%>">
		</td>
		<td class="td_form" width="20%">權利範圍：</td>
		<td class="td_form_white">
			分子：<input class="field" type="text" name="holdNume" size="7" maxlength="7" value="<%=obj.getHoldNume()%>">
			<br>			
			分母：<input class="field" type="text" name="holdDeno" size="7" maxlength="7" value="<%=obj.getHoldDeno()%>">
		</td>
	</tr>
	<tr>
		
		<td class="td_form">公告現值：</td>
		<td class="td_form_white">
			年月：<input class="field" type="text" name="priceYear" size="5" maxlength="5" value="<%=obj.getPriceDate()%>">
			<br>
			價值：<input class="field" type="text" name="priceUnit" size="15" maxlength="15" value="<%=obj.getPriceUnit()%>" >
		</td>	
		<td class="td_form">申報地價：</td>
		<td class="td_form_white">
			年月：<input class="field" type="text" name="valueYear" size="5" maxlength="5" value="<%=obj.getValueDate()%>">
			<br>
			價值：<input class="field" type="text" name="valueUnit" size="15" maxlength="15" value="<%=obj.getValueUnit()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">所有權人：</td>
		<td class="td_form_white" colspan="3">
			<input class="field" type="text" name="ownershipName" size="50" maxlength="50" value="<%=obj.getOwnershipName()%>">
		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	
	<span>
		<input class="toolbar_default" followPK="false" type="button" name="print" value="批次刪除" onclick="queryShow('deleteContainer');">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">轉入年月</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">縣市</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">鄉鎮市區</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">段（小段）</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">地號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">所有權人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">登記次序</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,false,false,false,true,false,false,true};
	boolean displayArray[] = {true,false,false,false,true,true,true,true,true,true,true};
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



