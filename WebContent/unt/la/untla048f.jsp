<!--
程式目的：土地合併分割資料查詢
程式代號：UNTLA048F
程式日期：0940825
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA048F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA048F)obj.queryOne();	
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
function checkfield_RO(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="update"){
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo,kindNo,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.kindNo.value=kindNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

/*檢查查詢欄位至少輸入一個*/
function checkMyQuery(){
	var errorflag=true;
	for(var i=0; i<document.forms[0].elements.length; i++){
		obj = document.forms[0].elements[i];
		if (obj.className=="field_PQ" || obj.className=="field_Q"){	
			if((obj.type=="checkbox")||(obj.type=="radio")){
				//if (obj.checked){
				//	errorflag=false;
				//	break;				
				//}
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

function init(){
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanClear").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	if (isObj(form1.spanListPrint))	document.all.item("spanListPrint").style.display="none";
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkfield_RO()">
<!--Query區============================================================-->
<div id="queryContainer" style="width:650px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable"  width="30%">入帳機關：</td>
		<td class="queryTDInput">
			<%=util.View.getPopOrgan("field_Q","q_enterOrg",obj.getQ_enterOrg(),obj.getQ_enterOrgName())%>
			　權屬：<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
		</td>
	</tr>
	  <tr>
		<td class="queryTDLable"  width="30%">土地標示：</td>		
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
        <td class="queryTDLable">合併分割日期：</td>
        <td class="queryTDInput">
		        起~訖：<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
		          <%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
        </td>
      </tr>
	  <tr>
        <td class="queryTDLable">填單日期：</td>
        <td class="queryTDInput">
        	起~訖：<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%> &nbsp;~&nbsp;
        		  <%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%>
        </td>
      </tr>	
	<tr>
      <td class="queryTDLable">合併分割案號：</td>
      <td class="queryTDInput">
      		起~訖： <input class="field_Q" type="text" name="q_caseNoS" size="10" value="<%=obj.getQ_caseNoS()%>"> &nbsp;~&nbsp;       
        		   <input name="q_caseNoE" type="text" class="field_Q" value="<%=obj.getQ_caseNoE()%>" size="10">
	  </td>
    </tr>
	<tr>
      <td class="queryTDLable">減損/增加單電腦單號：</td>
      <td class="queryTDInput">起~訖：
        <input class="field_Q" type="text" name="q_caseNo1S" size="10" maxlength="10" value="<%=obj.getQ_caseNo1S()%>">&nbsp;~&nbsp;
        <input class="field_Q" type="text" name="q_caseNo1E" size="10" value="<%=obj.getQ_caseNo1E()%>">
	  </td>
    </tr>
	<tr>
      <td class="queryTDLable">減損/增加單編號：</td>
      <td class="queryTDInput">起~訖：
          <input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
          <input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable"  width="30%">合併分割案號：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_caseNo" size="8" maxlength="8" value="<%=obj.getQ_caseNo()%>">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"  width="30%">合併分割原因：</td>
		<td class="queryTDInput">
		  <span class="td_form_white">
			<select class="field_Q" type="select" name="q_cause">
              <%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAA' and CODECON1='4'",obj.getQ_cause())%>
            </select>
	    </span>		</td>
	</tr>
	<tr>
	  <td class="queryTDLable">合併分割類別：</td>
	  <td class="queryTDInput"><span class="td_form_white">
	    <select class="field_Q" type="select" name="q_kindNo">
          <%=obj.getNumOption("1,2,3,4","合併前,合併後,分割前,分割後",obj.getQ_kindNo())%>
        </select>
	  </span></td>
	  </tr>
	<tr>
		<td class="queryTDLable"  width="30%">合併分割日期：</td>
		<td class="queryTDInput">
			<%=util.View.getPopCalndar("field_Q","q_enterDate",obj.getQ_enterDate())%>
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
		<td class="td_form">入帳機關：</td>
		<td class="td_form_white">
              <input class="field_RO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
[<input class="field_RO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]  　權屬：[<input size="10" class="field_RO" type="text" name="ownershipName" value="<%=obj.getOwnershipName()%>">]
		    <input type="hidden" name="ownership" value="<%=obj.getOwnership()%>"></td>
		</tr>
	<tr>
	  <td class="td_form">合併分割：</td>
	  <td class="td_form_white">案號：[<input class="field_RO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">] 　日期：[<input size="10" class="field_RO" type="text" name="enterDate" value="<%=obj.getEnterDate()%>">] 　
	  類別：[<input class="field_RO" type="text" name="kindNoName" size="10" maxlength="10" value="<%=obj.getKindNoName()%>">]
<input type="hidden" name="kindNo" value="<%=obj.getKindNo()%>">
<br>
原因：[<input class="field_RO" type="text" name="causeName" size="10" maxlength="10" value="<%=obj.getCauseName()%>">]
　其他說明：[<input class="field_RO" type="text" name="cause1" size="20" maxlength="40" value="<%=obj.getCause1()%>">] 
<input type="hidden" class="field_RO" name="cause" value="<%=obj.getCause()%>"></td>
	  </tr>
	<tr>
	  <td class="td_form">減損/增加單：</td>
	  <td class="td_form_white">電腦單號：[<input class="field_RO" type="text" name="caseNo1" size="10" maxlength="10" value="<%=obj.getCaseNo1()%>">]　填單日期：[<input size="10" class="field_RO" type="text" name="writeDate" value="<%=obj.getWriteDate()%>">] <br>
[<input class="field_RO" type="text" name="proofDoc" size="20" maxlength="20" value="<%=obj.getProofDoc()%>">] 字 第 [<input class="field_RO" type="text" name="proofNo" size="20" maxlength="20" value="<%=obj.getProofNo()%>">] 號 </td>
	  </tr>
	<tr>
      <td class="td_form">財產編號：</td>
      <td class="td_form_white">[<input name="propertyNo" type="text" class="field_RO" value="<%=obj.getPropertyNo()%>" size="15">]　 財產名稱：[<input name="propertyName" type="text" class="field_RO" value="<%=obj.getPropertyName()%>" size="20">] 　分號：[<input name="serialNo" type="text" class="field_RO" value="<%=obj.getSerialNo()%>" size="7">] <br>
    原財產編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 　原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
    </td>
	  </tr>
	<tr>
      <td class="td_form">土地標示：</td>
      <td class="td_form_white">代碼：[<input name="signNo" type="text" class="field_RO" value="<%=obj.getSignNo()%>" size="14">]　名稱：[<input class="field_RO" type="text" size="36" name="signName" value="<%=obj.getSignName()%>">]
      </td>
	  </tr>
	<tr>
      <td class="td_form">面積資訊：</td>
      <td class="td_form_white"> 整筆面積(㎡)：[<input class="field_RO" type="text" name="area" size="12" maxlength="12" value="<%=obj.getArea()%>">] <br>
    權利分子：[<input class="field_RO" type="text" name="holdNume" size="9" maxlength="10" value="<%=obj.getHoldNume()%>">] 權利分母：[<input class="field_RO" type="text" name="holdDeno" size="9" maxlength="10" value="<%=obj.getHoldDeno()%>" >] &nbsp;權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">]</td>
	  </tr>
	<tr>
	  <td class="td_form">使用資訊：</td>
	  <td class="td_form_white">
	  使用分區：<input type="hidden" name="useSeparate" value="<%=obj.getUseSeparate()%>">[<input class="field_RO" type="text" name="useSeparateName" size="16" maxlength="10" value="<%=obj.getUseSeparateName()%>">]<br>
編定使用種類：<input type="hidden" name="useKind" value="<%=obj.getUseKind()%>">	    
[<input class="field_RO" type="text" name="useKindName" size="20" maxlength="10" value="<%=obj.getUseKindName()%>">]
	  
	  　地目：<input type="hidden" name="field" value="<%=obj.getField()%>">
[<input class="field_RO" type="text" name="fieldName" size="10" maxlength="10" value="<%=obj.getFieldName()%>">]</td>
	  </tr>
	<tr>
		<td class="td_form">價格資訊：</td>
		<td class="td_form_white">
			單價：[<input class="field_RO" type="text" name="bookUnit" size="13" maxlength="13" value="<%=obj.getBookUnit()%>">] 　
			總價：[<input class="field_RO" type="text" name="bookValue" size="15" maxlength="15" value="<%=obj.getBookValue()%>">]
		</td>
		</tr>
	<tr>
	  <td class="td_form">異動人員/日期：</td>
	  <td class="td_form_white"> [
          <input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
  /
  <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">
  ] </td>
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
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">合併分割案號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">合併分割原因</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">合併分割日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">合併分割類別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">土地標示代碼</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">權利範圍面積(㎡)</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,false,false,false,true,true,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,true,true,true,true,false,false,false,false,false,false};
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
