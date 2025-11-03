 <!-- 
程式目的：財產編號修正－土地
程式代號：untdu101f
程式日期：0990114
程式作者：chuhung
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.du.UNTDU101F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update2();
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.q_propertyNo,"財產編號");
	}else if(form1.state.value=="updateBatch"){
		if(parse(form1.q_newPropertyNo.value).length <= 0) { 
			alertStr += "注意：新的財產編號不可為空！\n";
		}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,enterOrgName,ownershipName,ownership,propertyNo,serialNo,propertyNoName){
	form1.enterOrg.value=enterOrg;
	form1.enterOrgName.value=enterOrgName;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.propertyNoName.value=propertyNoName;
}

function init2(){
    document.all.item("spaninsert").style.display="none";
	document.all.item("spanupdate").style.display="none";
	document.all.item("spandelete").style.display="none";
	document.all.item("spanclear").style.display="none";
	document.all.item("spanconfirm").style.display="none";
	document.all.item("spannextInsert").style.display="none";
	document.all.item("spanListPrint").style.display="none";
	document.all.item("spanListHidden").style.display="none";
	setFormItem("queryAll","O");
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
		opener.form1.submit();
		if (isObj(window.aha)) window.aha.close();
	}
}

function checkInsertSelect() {
	var sFlag = false;
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "sKeySet" && e.checked==true) sFlag = true;	    
	}
	if (sFlag) return "";
	else return "您尚未勾選任何資料，若無資料可供勾選，請重新查詢！\n";
}

function show_window(windowName){ 
	if (parse(checkInsertSelect()).length > 0) {
	    alert(checkInsertSelect()); 
	    return false;
	}else{
	    var winObj=document.all.item(windowName);
	    var objHeight= winObj.style.height;
	    var objWidth= winObj.style.width;
	    objHeight= objHeight.substring(0,objHeight.length-2);
	    objWidth= objWidth.substring(0,objWidth.length-2);
	    winObj.style.top=(document.body.clientHeight-Number(objHeight)-80)/2;
	    winObj.style.left=(document.body.clientWidth-Number(objWidth))/2;	
	    winObj.style.display="block";
	    form1.state.value = "update";
	}
}
function hidden_window(windowName){
	var winObj=document.all.item(windowName);		
	winObj.style.display="none";
	form1.state.value = "init";
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();init2();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--批次設定區============================================================-->
<div id="gountmp027f" style="position:absolute;z-index: 25;left:0;top :0;width:500px;height:100px;display:none">
	<iframe id="gountmp027fFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>	
	<div class="queryTitle">批次修改視窗</div>
	<table class="queryTable"  border="1">
		<tr>
		<td class="queryTDLable">新的財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<%=util.View.getPopProperty("field_Q","newPropertyNo",obj.getNewPropertyNo(),obj.getNewPropertyNoSName(),"1")%>
	  </td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onClick="whatButtonFireEvent('update');init2();">
						<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('gountmp027f');init2();">
		</td>
	</tr>
	</table>
</div>

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:250px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
	<tr>
      <td class="queryTDLable" width="30%">入帳機關：</td>
      <td class="queryTDInput" width="70%">
	      <%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName(),"")%>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable" width="30%">財產編號：</td>		
		<td class="queryTDInput" width="70%">	
		    <input class="field_Q" type="text" name="q_propertyNo" size="11" maxlength="11" value="<%=obj.getQ_propertyNo()%>">
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
		<td class="td_form" width="22%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >
		    [<input class="field_PRO" type="text" name="enterOrgName" size="26" maxlength="50" value="<%=obj.getEnterOrgName()%>">]&nbsp;&nbsp;&nbsp;
	      	權屬：
	        <select class="field_PRO" type="select" name="ownership">
	        	<%=util.View.getOnwerOption(obj.getOwnership())%>
	        </select>
        </td>
	</tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"111")%>&nbsp;&nbsp;&nbsp;	
			分號： <input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">
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
	<span id="span_update_batch">
	<input class="toolbar_default" type="button" followPK="false" name="btn_update_batch" value="批次更新" onClick="show_window('gountmp027f');">&nbsp;|
    </span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer" style="width: 100%;	height: 430px; ">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產名稱</a></th>
	</thead>
	<tbody id="listTBODY">	
	<%
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[8];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";	
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";			
			//tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[4]+"','"+rowArray[5]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[6]+"')\"";
			sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[4]+"','"+rowArray[5]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[6]+"');\">\n ");			
			sbHEML.append(" <td class='listTD'>"+counter+".</td>\n ");
			sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+Common.checkGet(rowArray[0])+","+Common.checkGet(rowArray[1])+","+Common.checkGet(rowArray[2])+","+Common.checkGet(rowArray[3])+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[4])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[5])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[2])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[3])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[6])+"</td>\n</tr>\n ");
		}
	}
	out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script type="text/javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "queryAll":
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			}
			break;	
	}
	return true;
}
</script>
</body>
</html>
