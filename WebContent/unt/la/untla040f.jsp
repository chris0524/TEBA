<!--
程式目的：土地所有權狀領狀作業
程式代號：untla040f
程式日期：0940822
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%String PermissionName[] ={"untla040f"}; %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA040F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA040F)obj.queryOne();	
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
<link rel="stylesheet" href="../../js/default.css?1=2dsddsssss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("ownership","1"),
	new Array("signNo1", "E000000"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate", getToday()),
	new Array("drawDate",getToday())
);

function checkField(){
	var alertStr="";
	if (form1.state.value=="queryAll") {
		alertStr += checkEmpty(form1.q_enterOrg,"入帳機關");
		alertStr += checkQuery();
	} else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		if ((form1.signNo1.value=="")||(form1.signNo2.value=="")||(form1.signNo3.value=="")||(form1.signNo4.value=="")||(form1.signNo5.value=="")){
			form1.signNo1.style.backgroundColor=errorbg;
			form1.signNo2.style.backgroundColor=errorbg;
			form1.signNo3.style.backgroundColor=errorbg;
			form1.signNo4.style.backgroundColor=errorbg;
			form1.signNo5.style.backgroundColor=errorbg;
			alertStr += "土地標示代碼不得為空白!\n";
		}			
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");		
		alertStr += checkEmpty(form1.drawDate,"領狀日期");
		alertStr += checkDate(form1.drawDate,"領狀日期");
		alertStr += checkEmpty(form1.drawCause, "領狀原因");
		if (form1.drawCause.value=="99") {
			alertStr += checkEmpty(form1.drawCause1,"領狀原因--其他說明");
		} else {
			form1.drawCause1.value = "";
		}
		alertStr += checkEmpty(form1.drawName,"領狀人");
		alertStr += checkDate(form1.returnDate,"歸還日期");	
		alertStr += checkLen(form1.notes, "備註", 250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function changeDrawcause() {
	if (form1.drawCause.value=="99") {
		setFormItem("drawCause1","O");
	} else {		
		form1.drawCause1.value = "";
		setFormItem("drawCause1","R");
	}
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:600px;height:280px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	  <tr>
	    <td class="queryTDLable">領狀日期：</td>
	    <td class="queryTDInput">
			          起<%=util.View.getPopCalndar("field_Q","q_drawDateS",obj.getQ_drawDateS())%>&nbsp;~&nbsp;
				訖<%=util.View.getPopCalndar("field_Q","q_drawDateE",obj.getQ_drawDateE())%> 
		</td>
      </tr>
	  <tr>
	    <td class="queryTDLable">領狀人：</td>
	    <td class="queryTDInput"><input class="field_Q" type="text" name="q_drawName" size="20" maxlength="20" value="<%=obj.getQ_drawName()%>"></td>
      </tr>
	  <tr>
	    <td class="queryTDLable">領狀原因：</td>
	    <td class="queryTDInput"><select class="field_Q" type="select" name="q_drawCause">
          <%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='DCA'", obj.getQ_drawCause())%>
        </select></td>
      </tr>
	  <tr>
		<td class="queryTDLable">土地標示：</td>		
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
			&nbsp;地號：
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=obj.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=obj.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">權屬：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	  </tr>
	<tr>
      <td class="queryTDLable">資料狀態：</td>
      <td class="queryTDInput"><select class="field_Q" type="select" name="q_dataState">
        <option value="">請選擇...</option>
        <option value="1">現存</option>
        <option value="2">已減損</option>          
        </select>
        <input name="q_enterOrg" type="hidden" value="<%=user.getOrganID()%>">
      </td>
	  </tr>
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定">
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
          <td align="right" class="td_form">入帳機關：</td>
          <td colspan="3" class="td_form_white">
        <input class="field_PRO" type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >[ <input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]		<font color="red">　*</font>權屬：
        <select class="field_P" type="select" name="ownership" onChange="getPropertyNo();">
              <%=util.View.getOnwerOption(obj.getOwnership())%>
          </select>　
        資料狀態：[<input name="dataStateName" type="text" class="field_RO" value="<%=obj.getDataStateName()%>" size="10">]
		<input name="dataState" type="hidden" value="<%=obj.getDataState()%>">
		</td>
          </tr>
        <tr>
          <td align="right" class="td_form"><font color="red">*</font>土地標示：</td>
          <td class="td_form_white" colspan="3"><select class="field_P" type="select" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getPropertyNo();">
              <%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
            </select>　
              <select class="field_P" type="select" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getPropertyNo();">
                <script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
              </select>　
              <select class="field_P" type="select" name="signNo3" onChange="getPropertyNo();">
                <script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
              </select> 　
&nbsp;地號：
<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onBlur="getPropertyNo();">
&nbsp;-
    <input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onBlur="getPropertyNo();">
          </td>
        </tr>
        <tr>
          <td align="right" class="td_form">財產編號：</td>
          <td colspan="3" class="td_form_white">[<input name="propertyNo" type="text" class="field_RO" value="<%=obj.getPropertyNo()%>" size="12">]　分號：[<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]　 財產名稱：[<input class="field_RO" type="text" name="propertyName" value="<%=obj.getPropertyName()%>">]<br>
              原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 　原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">] </td>
        </tr>
        <tr>
          <td class="td_form">權狀資料：</td>
          <td colspan="3" class="td_form_white">所有權登記日期：[<input name="ownershipDate" type="text" class="field_RO" value="<%=obj.getOwnershipDate()%>" size="12">]　權狀字號 ：[<input name="proofDoc" type="text" class="field_RO" id="proofDoc" value="<%=obj.getProofDoc()%>">]</td>
        </tr>       
        <tr>
          <td align="right" class="td_form">領狀次序：</td>
          <td colspan="3" class="td_form_white">[<input class="field_RO" type="text" name="serialNo1" size="3" maxlength="3" value="<%=obj.getSerialNo1()%>">]　<font color="red">*</font>領狀日期：<%=util.View.getPopCalndar("field","drawDate",obj.getDrawDate())%><font color="red">　*</font> 領狀人： <input class="field" type="text" name="drawName" size="10" maxlength="10" value="<%=obj.getDrawName()%>">
          </td>
          </tr>
        <tr>
          <td align="right" class="td_form"><font color="red">*</font>領狀原因：</td>
          <td colspan="3" class="td_form_white"><select class="field" type="select" name="drawCause" onChange="changeDrawcause();">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DCA' ", obj.getDrawCause())%>		  
            </select>
　其他說明：
<input class="field" type="text" name="drawCause1" size="40" maxlength="40" value="<%=obj.getDrawCause1()%>">          </td>
          </tr>
        <tr>
          <td align="right" class="td_form">歸還日期：</td>
          <td colspan="3" class="td_form_white"><%=util.View.getPopCalndar("field","returnDate",obj.getReturnDate())%></td>
          </tr>
        <tr>
          <td align="right" class="td_form">備註：</td>
          <td class="td_form_white"><textarea name="notes" cols="25" rows="4" class="field"><%=obj.getNotes()%></textarea></td>
          <td align="right" class="td_form">異動人員/日期：</td>
          <td class="td_form_white">[
            <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
/
<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
]  </td>
        </tr>
      </table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">領狀原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">領狀人</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">領狀日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">歸還日期</a></th>
	</tr>	
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,true,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,true,true,true,false,false,false,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		case "insert":
			//將土地或建物標示的縣市欄位設為高雄市, 不過記得要在insertDefault裡加入一個 new Array("signNo1", "E000000")
			changeSignNo1('signNo1','signNo2','signNo3','E000000');
			changeDrawcause();
			break;
		case "update":
			changeDrawcause();
			break;
						
	}
}
</script>
</body>
</html>

