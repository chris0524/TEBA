
<!--
程式目的：文號資料維護
程式代號：untba002f
程式日期：0940626
程式作者：griffin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ba.UNTBA002F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
obj.setEnterOrg(user.getOrganID());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.ba.UNTBA002F)obj.queryOne();	
//}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	//obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
//}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
//	obj.delete();
}else if ("true".equals(obj.getState2())) {
	obj.getInsertSQL2();
}


objList = obj.queryAll();

String popDocNo = Common.checkGet(request.getParameter("popDocNo"));
String popDocName = Common.checkGet(request.getParameter("popDocName"));
%>

<html>
<head>
<title>文號輔助視窗</title>
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
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
	}else if(form1.state.value=="update"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.docNo,"文號代碼");
		alertStr += checkEmpty(form1.docName,"文號名稱");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,docNo,docName,notes,editID,editDate){
	form1.enterOrg.value=enterOrg;
	form1.docNo.value=docNo;
	form1.docName.value=docName;
	form1.notes.value=notes;
	form1.editID.value=editID;
	form1.editDate.value = editDate;
	if (form1.enterOrg.value!="" && form1.docNo.value!="") {
		form1.state.value="queryOneSuccess";
		whatButtonFireEvent('queryOneSuccess');	
	}	
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function selectDoc(selectID,selectName){
	if (isObj(opener.document.all.item("<%=popDocNo%>"))) {		
		opener.document.all.item("<%=popDocNo%>").value=selectID;		
	}
	if (isObj(opener.document.all.item("<%=popDocName%>"))) {
		opener.document.all.item("<%=popDocName%>").value=selectName;		
	}
	window.close();
}

function init() {
	setDisplayItem("spanInsert,spanDelete,spanNextInsert","H");
}
function gobatchInsertBut()
{
	form1.state2.value = true ;
	form1.submit();
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="enterOrg" value="<%=user.getOrganID()%>">
<input type="hidden" name="popDocNo" value="<%=popDocNo%>">
<input type="hidden" name="popDocName" value="<%=popDocName%>">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:120px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">所屬機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName())%>
		</td>	
	</tr>
	<tr>
		<td class="queryTDLable"  width="30%">文號代碼：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_docNo" size="5" maxlength="5" value="<%=obj.getQ_docNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"  width="30%">文號名稱：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_docName" size="20" maxlength="20" value="<%=obj.getQ_docName()%>">
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
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form"><font color="red">*</font>所屬機關：</td>
		<td class="td_form_white">
			<%=util.View.getPopOrgan("field","enterOrg",obj.getEnterOrg(),obj.getEnterOrgName(),"")%>
		</td>	
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>文號代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="docNo" size="5" maxlength="5" value="<%=obj.getDocNo()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>文號名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="docName" size="20" maxlength="20" value="<%=obj.getDocName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="notes" size="50" maxlength="50" value="<%=obj.getNotes()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
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
	<input type="hidden" name="state2" value="false">
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" name="batchInsertBut" value="現有資料明細新增" onClick="gobatchInsertBut()">
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">文號代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">文號名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">備註</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,true,true,true,true};
	boolean displayArray[] = {false,true,true,true,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	/**
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[4];
		java.util.Iterator it=objList.iterator();
		while(it.hasNext()){		
			counter++;	
			rowArray= (String[])it.next();
			sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"')\" >");
			sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
			sbHEML.append(" <td class='listTD' ><a href='#'><img src='../../image/selectNo.gif' border='0' alt='選擇文號並返回' onclick=\"selectDoc('"+rowArray[1]+"','"+rowArray[2]+"');\"></a></td> ");		
			sbHEML.append(" <td class='listTD' >"+rowArray[1]+"</td> ");
			sbHEML.append(" <td class='listTD' >"+rowArray[2]+"</td></tr> ");			
		}
	}
	out.write(sbHEML.toString());		
	**/
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>

