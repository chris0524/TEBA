
<!--
程式目的：土地使用分區異動資料維護
程式代號：UNTLA047F
程式日期：0940827
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA047F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA047F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
	if ("insertSuccess".equals(obj.getState())) {obj.setQueryAllFlag("true");}
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
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script language="javascript">

function alteredEnterOrg(){}

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	//new Array("signNo1", "E000000"),
	new Array("editID","<%=user.getUserID()%>"),
	new Array("editDate", getToday()),
	new Array("changeDate",getToday())
);
	
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		//alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
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
		alertStr += checkEmpty(form1.differenceKind,"財產區分別");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");
		alertStr += checkEmpty(form1.serialNo,"財產分號");		
		//alertStr += checkEmpty(form1.changeDate,"變更日期");
		//alertStr += checkDate(form1.changeDate,"變更日期");			
		if (form1.oldUseSeparate.value!=form1.newUseSeparate.options[form1.newUseSeparate.selectedIndex].value || form1.oldUseKind.value!=form1.newUseKind.options[form1.newUseKind.selectedIndex].value) {		
			alertStr += checkEmpty(form1.newUseSeparate, "新使用分區");
			if((form1.newUseSeparate.value).substr(0,1)=="A") alertStr += checkEmpty(form1.newUseKind,"編定使用種類");
			else { form1.newUseKind.value =""; form1.newUseKind.backgroundColor = ""; }
		} else {
			alertStr += "您未異動任何分區資料，故不需要執行此作業，謝謝!\n";
		}
		
		alertStr += checkLen(form1.notes1, "其他事項", 60)			
		alertStr += checkLen(form1.notes, "備註", 250);
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function init(){
	document.all.item("spanUpdate").style.display="none"; 
	document.all.item("spanDelete").style.display="none";	

	setFormItem("btn_new","O");
	
	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 財產管理 > > 土地使用分區異動資料維護";
}

function queryOne(enterOrg,ownership,differenceKind,propertyNo,serialNo,serialNo1){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.differenceKind.value=differenceKind;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.serialNo1.value=serialNo1;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function chkChangeItem() {
	//若「使用分區」為「A」開頭時，「編定使用種類」必須有資料
	if((form1.newUseSeparate.value).substr(0,1) == "A") form1.newUseKind.disabled = false;
	else { form1.newUseKind.value=""; form1.newUseKind.disabled = true; form1.newUseKind.style.backgroundColor="";}	
	
	if (form1.oldUseSeparate.value!=form1.newUseSeparate.options[form1.newUseSeparate.selectedIndex].value && form1.oldUseKind.value!=form1.newUseKind.options[form1.newUseKind.selectedIndex].value) {
		form1.changeItem.value = "使用分區、編定使用種類";
	} else if (form1.oldUseSeparate.value!=form1.newUseSeparate.options[form1.newUseSeparate.selectedIndex].value) {
		form1.changeItem.value = "使用分區";
	} else if (form1.oldUseKind.value!=form1.newUseKind.options[form1.newUseKind.selectedIndex].value) {
		form1.changeItem.value = "編定使用種類";
	} else {
		form1.changeItem.value="";
	}	
}
function callProperty() {
	if (parse(form1.serialNo.value).length>0) getPropertyNo('PN');
	chkChangeItem();
}

function btn_new_event(){
	var q = "";
	q += "?enterOrg=" + form1.organID.value + 
			"&objPath=" + form1.objPath.value + 
			"&saveLog=" + form1.saveLog.value;
	window.open("../ch/untch007f01.jsp" + q);	
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:600px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
	  <td class="queryTDLable" >入帳機關：</td>
	  <td colspan="3" class="queryTDInput" >
	  <%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName())%>
        </td>
	  </tr>
	<tr>
	  <td class="queryTDLable">財產編號：</td>
	  <td class="queryTDInput"><%=util.View.getPopProperty("field_Q","q_propertyNo",obj.getQ_propertyNo(),obj.getQ_propertyNoName(),"1")%> </td>
	  </tr>
	<tr>
	  <td class="queryTDLable">財產分號：</td>
	  <td class="queryTDInput">
		    起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
		    訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
	  </td>
	</tr>
	<tr>
	  <td class="queryTDLable">土地標示：</td>
	  <td class="queryTDInput">
		  <select  type="select" class="field_Q" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
	        <%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
	      </select>
	      <select  type="select" class="field_Q" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
	          <script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
	      </select>
          <select  type="select" class="field_Q" name="q_signNo3">
              <script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
          </select>
		<br>
		地號：
			<input class="field_Q" type="text" name="q_signNo4" size="4" maxlength="4" value="<%=obj.getQ_signNo4()%>" onchange="getFrontZero(this.name,4);">
			&nbsp;-&nbsp;
			<input class="field_Q" type="text" name="q_signNo5" size="4" maxlength="4" value="<%=obj.getQ_signNo5()%>" onchange="getFrontZero(this.name,4);">
		</td>
	  </tr>
	<tr>
	  <td class="queryTDLable">權屬：</td>
	  <td class="queryTDInput"><select  type="select" class="field_Q" name="q_ownership">
        <%=util.View.getOnwerOption(obj.getQ_ownership())%>
      </select></td>
	  </tr>
	<tr>
		<td class="queryTDLable">變更日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_changeDateS",obj.getQ_changeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_changeDateE",obj.getQ_changeDateE())%> 
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
	  <td class="td_form" width="10%">入帳機關：</td>
	  <td colspan="3" class="td_form_white" width="80%">
	  		<input type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">] 
			<font color="red">　*</font>權屬：
			<select  type="select" class="field_P" name="ownership">
            	<%=util.View.getOnwerOption(obj.getOwnership())%>
      		</select>
      		&nbsp;&nbsp;&nbsp;      		
			<font color="red">*</font>財產區分別：
			<%=util.View._getSelectHTML("field_P", "differenceKind", obj.getDifferenceKind(),"DFK") %>	     	
      	</td>
	  </tr>
	<tr>
	  <td class="td_form">土地標示：</td>
	  <td colspan="3" class="td_form_white"><select type="select" class="field_P" name="signNo1" onChange="changeSignNo1('signNo1','signNo2','signNo3','');getPropertyNo('SN');chkChangeItem();">
        <%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getSignNo1())%>
      </select>　
        <select  type="select" class="field_P" name="signNo2" onChange="changeSignNo2('signNo1','signNo2','signNo3','');getPropertyNo('SN');chkChangeItem();">
          <script>changeSignNo1('signNo1','signNo2','signNo3','<%=obj.getSignNo2()%>');</script>
        </select>　
        <select  type="select" class="field_P" name="signNo3" onChange="getPropertyNo('SN');">
          <script>changeSignNo2('signNo1','signNo2','signNo3','<%=obj.getSignNo3()%>');</script>
        </select>　
        地號：<input class="field_P" type="text" name="signNo4" size="4" maxlength="4" value="<%=obj.getSignNo4()%>" onBlur="getPropertyNo('SN');chkChangeItem();">
&nbsp;-&nbsp;
<input class="field_P" type="text" name="signNo5" size="4" maxlength="4" value="<%=obj.getSignNo5()%>" onBlur="getPropertyNo('SN');chkChangeItem();"></td>
	  </tr>
	<tr>
	  <td class="td_form"><font color="red">*</font> 財產編號：</td>
	  <td colspan="3" class="td_form_white">
	  <%=util.View.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"1&isLookup=N&js=callProperty()")%>
	   　分號：<input class="field_P" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>" onBlur="getPropertyNo('PN');chkChangeItem();">
	   <br>
	    原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 　原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">] </td>
	  </tr>
	<tr>
		<td class="td_form"><font color="red">*</font>變更日期：</td>		
		<td class="td_form_white" >
			<%=util.View.getPopCalndar("field","changeDate",obj.getChangeDate())%>
		</td>
		<td class="td_form"width="10%">異動次序：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="serialNo1" size="10" maxlength="10" value="<%=obj.getSerialNo1()%>">]
		</td>		
	</tr>
	<tr>
		<td class="td_form" >變更原因：</td>
		<td class="td_form_white" colspan='3'>
			<input class="field" type="text" name="changeCause" size="55" maxlength="55" value="<%=obj.getChangeCause()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">變更事項：</td>
		<td class="td_form_white">[<input class="field_RO" type="text" name="changeItem" size="30" value="<%=obj.getChangeItem()%>">]
		</td>
	</tr>
	<tr>
	  <td class="td_form">其他事項：</td>
	  <td colspan="3" class="td_form_white"><input class="field" type="text" name="notes1" size="50" maxlength="60" value="<%=obj.getNotes1()%>">
      </td>
	  </tr>
	<tr>
	  <td class="td_form">使用分區：</td>
	  <td colspan="3" class="td_form_white"> 原使用：[<input type="text" class="field_RO" name="oldUseSeparateName" value="<%=obj.getOldUseSeparateName()%>" size="25">]
          <input type="hidden" class="field" name="oldUseSeparate" value="<%=obj.getOldUseSeparate()%>">                　
          新使用：<select  type="select" class="field" name="newUseSeparate" onChange="chkChangeItem();">
            <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='SEP' ", obj.getNewUseSeparate())%>
                  </select>      </td>
	  </tr>
	<tr>
	  <td class="td_form">編定使用種類：</td>
	  <td colspan="3" class="td_form_white">原編定：[<input type="text" class="field_RO" name="oldUseKindName" value="<%=obj.getOldUseKindName()%>" size="25">]
          <input type="hidden" class="field" name="oldUseKind" value="<%=obj.getOldUseKind()%>">                　
          新編定：<select  type="select" class="field" name="newUseKind" onChange="chkChangeItem();">
              <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='UKD' ", obj.getNewUseKind())%>
                    </select>        </td>
		</tr>
        <tr>
          <td align="right" class="td_form">備註：</td>
          <td class="td_form_white" colspan='3'><textarea name="notes" cols="50" rows="5" class="field"><%=obj.getNotes()%></textarea></td>
        </tr>
        <tr>  
          <td align="right" class="td_form"style="display:none;">異動人員/日期：</td>
          <td class="td_form_white" style="display:none;"colspan='3'>[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
/
<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]</td>
        </tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>" >
    <input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>" >
    <input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>" >
    <input type="hidden" name="isManager" value="<%=user.getIsManager()%>" >
    <input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="objPath" >
	<jsp:include page="../../home/toolbar.jsp" />
	
	<span id="span_btn_new">
		<input class="toolbar_default" type="button" followPK="true" name="btn_new" value="現有資料明細新增" onclick="btn_new_event();">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',0,false);" href="#">權屬</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示</a></th>		
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">變更日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">原使用分區</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">原編定使用種類</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">新使用分區</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">新編定使用種類</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,true,true,true,false,true,true,true,false,false,false,false,false,false};
	boolean displayArray[] = {true,false,false,false,true,false,false,false,true,true,true,true,true,true};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
<script language="javascript">
localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){	
	switch (buttonName)	{	
		case "queryAll":
			if (form1.signNo1.value=="") changeSignNo1('signNo1','signNo2','signNo3','E000000');
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
			}
			break;
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
			setFormItem("newUseKind","R");		
			//將土地或建物標示的縣市欄位設為高雄市, 不過記得要在insertDefault裡加入一個 new Array("signNo1", "E000000")
			changeSignNo1('signNo1','signNo2','signNo3','E000000');
			break;
		case "insertError":
			chkChangeItem();
			break;				
			
		//更新時要做的動作
		case "update":
		case "updateError":
			chkChangeItem();
			break;			
	}
}
</script>
</body>
</html>
