<!--
程式目的：建物所有權狀審核作業
程式代號：untla026f
程式日期：0940920
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.bu.UNTBU026F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.bu.UNTBU026F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("approveAll".equals(obj.getState())) {
	obj.approveAll(1);
}else if ("unApproveAll".equals(obj.getState())) {
	obj.approveAll(0);
}else if ("approveOne".equals(obj.getState())) {
	obj.approveOne(1);
}else if ("unApproveOne".equals(obj.getState())) {
	obj.approveOne(0);
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
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr="";
	if (form1.state.value=="queryAll"||form1.state.value=="queryOne") {
		alertStr += checkEmpty(form1.q_ownership,"權屬");
		alertStr += checkEmpty(form1.q_signNo1,"建物標示代碼－縣市");
		alertStr += checkEmpty(form1.q_signNo2,"建物標示代碼－鄉鎮市區");		
	} else if (form1.state.value=="approveOne"||form1.state.value=="unApproveOne") {
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkEmpty(form1.propertyNo,"財產編號");		
		alertStr += checkEmpty(form1.serialNo,"財產分號");		
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function queryOne(enterOrg,ownership,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function clickApproveAll(){
	if(confirm("您確定要審核列表上的全部建物權狀?")){
		document.all('state').value='approveAll';
		form1.editID.value = "<%=user.getUserID()%>";				
		form1.submit();
	}

}

function clickUnApproveAll(){
	if(confirm("您確定要取消審核列表上的全部建物權狀?")){
		document.all('state').value='unApproveAll';
		form1.editID.value = "<%=user.getUserID()%>";	
		form1.submit();
	}
}

function clickApproveOne(){
	if(confirm("您確定要審核此筆建物權狀?")){
		document.all('state').value='approveOne';	
		form1.editID.value = "<%=user.getUserID()%>";		
		form1.submit();
	}
}

function clickUnApproveOne(){
	if(confirm("您確定要取消審核此筆建物權狀?")){
		document.all('state').value='unApproveOne';
		form1.editID.value = "<%=user.getUserID()%>";		
		form1.submit();
	}
}


function init(){
	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListPrint,spanListHidden","H");
	/**
	document.all.item("spanInsert").style.display="none";
	document.all.item("spanUpdate").style.display="none";
	document.all.item("spanDelete").style.display="none";
	document.all.item("spanClear").style.display="none";
	document.all.item("spanConfirm").style.display="none";
	if (isObj(document.all.item("spanListPrint"))) document.all.item("spanListPrint").style.display="none";	
	**/
	if (form1.state.value=="queryOneSuccess") {
		setFormItem("approveOne,unApproveOne","O");
	} else {
		setFormItem("approveOne,unApproveOne","R");
	}
	
	if (form1.state.value=="logout") {	
		alert("對不起! 遺失重要資訊, 請重新登入!");	
		window.top.location.href = "../../index.jsp";
	}
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:600px;height:150px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable">建物標示：</td>		
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_signNo1" onChange="changeSignNo1('q_signNo1','q_signNo2','q_signNo3','');">
			<%=util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",obj.getQ_signNo1())%>
			</select>
			<select class="field_Q" type="select" name="q_signNo2" onChange="changeSignNo2('q_signNo1','q_signNo2','q_signNo3','');">
				<script>changeSignNo1('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo2()%>');</script>
			</select>		
			<select class="field_Q" type="select" name="q_signNo3">
				<script>changeSignNo2('q_signNo1','q_signNo2','q_signNo3','<%=obj.getQ_signNo3()%>');</script>
			</select>
			&nbsp;　建號：
			<input class="field_Q" type="text" name="q_signNo4" size="5" maxlength="5" value="<%=obj.getQ_signNo4()%>">&nbsp;-
			<input class="field_Q" type="text" name="q_signNo5" size="3" maxlength="3" value="<%=obj.getQ_signNo5()%>">		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable"><font color="red">*</font>權屬：</td>
		<td colspan="3" class="queryTDInput">
			<select class="field_Q" type="select" name="q_ownership">
			<%=util.View.getOnwerOption(obj.getQ_ownership())%>
			</select>
			　權狀審核：<select class="field_Q" type="select" name="q_proofVerify">
			<%=util.View.getYNOption(obj.getQ_proofVerify())%>
			</select>			
			<input class="field_Q" type="hidden" name="q_enterOrg" value="<%=user.getOrganID()%>" >			
		</td>
	  </tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
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
          <td class="td_form" width="88">是否審核：</td>
          <td class="td_form_white">
              [
                <input name="proofVerifyName" type="text" class="field_RO" value="<%=obj.getProofVerifyName()%>" size="12">
]
<input type="hidden" name="proofVerify" value="<%=obj.getProofVerify()%>">
              <input type="hidden" name="enterOrg" value="<%=obj.getEnterOrg()%>" >              　
              　權屬： [<input name="ownershipName" type="text" class="field_RO" value="<%=obj.getOwnershipName()%>" size="12">]
<input type="hidden" name="ownership" value="<%=obj.getOwnership()%>">    </td>
          </tr>
        <tr>
          <td class="td_form">財產編號：</td>
          <td class="td_form_white">[<input name="propertyNo" type="text" class="field_RO" value="<%=obj.getPropertyNo()%>" size="15">]　 分號：[<input name="serialNo" type="text" class="field_RO" value="<%=obj.getSerialNo()%>" size="7">]　財產名稱：[<input name="propertyName" type="text" class="field_RO" value="<%=obj.getPropertyName()%>" size="20">]<br>
別名： [<input name="propertyName1" type="text" class="field_RO" value="<%=obj.getPropertyName1()%>" size="30">]
</td>
          </tr>
        <tr>
          <td class="td_form">建物標示：</td>
          <td class="td_form_white"><!-- 代碼：[
            <input name="signNo" type="text" class="field_RO" value="<%=obj.getSignNo()%>" size="15">
            ]　名稱： -->[<input class="field_RO" type="text" name="signName" size="70" value="<%=obj.getSignName()%>">]
            </td>
        </tr>
        <tr>
          <td class="td_form">門牌：</td>
          <td class="td_form_white">[<input name="doorPlate1" type="text" class="field_RO" value="<%=obj.getDoorPlate1()+obj.getDoorPlate2()+obj.getDoorPlate3()+obj.getDoorPlate4()%>" size="70">]</td>
        </tr>
        <tr>
          <td class="td_form">建築日期：</td>
          <td class="td_form_white">[<input name="buildDate" type="text" class="field_RO" value="<%=obj.getBuildDate()%>" size="12">]　 
構造－造(材質)：[<input name="stuff" type="text" class="field_RO" value="<%=obj.getStuff()%>" size="12">] </td>
          </tr>
        <tr>
          <td class="td_form">權狀資料：</td>
          <td class="td_form_white">所有權登記日期：[<input name="ownershipDate" type="text" class="field_RO" value="<%=obj.getOwnerShipDate()%>" size="12">]　權狀字號 ：[<input name="proofDoc" type="text" class="field_RO" value="<%=obj.getProofDoc()%>">]
           </td>
         </tr>
        <tr>
          <td class="td_form">目前面積：</td>
          <td class="td_form_white"> 主面積(㎡)：
              [<input class="field_RO" type="text" name="CArea" size="9" maxlength="10" value="<%=obj.getCArea()%>">]
			  &nbsp;&nbsp;附屬面積：[<input class="field_RO" type="text" name="SArea" size="9" maxlength="10" value="<%=obj.getSArea()%>">] 
			  總面積：[<input class="field_RO" type="text" name="area" size="9" maxlength="9" value="<%=obj.getArea()%>">] <br>
    權利分子：[<input class="field_RO" type="text" name="holdNume" size="9" maxlength="10" value="<%=obj.getHoldNume()%>">]
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;權利分母：[<input class="field_RO" type="text" name="holdDeno" size="9" maxlength="10" value="<%=obj.getHoldDeno()%>" >] 
	權利範圍面積(㎡)：[<input class="field_RO" type="text" name="holdArea" size="9" maxlength="9" value="<%=obj.getHoldArea()%>">] </td>
        </tr>
        <tr>
          <td class="td_form">異動資訊：</td>
          <td class="td_form_white">人員/日期：[
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<jsp:include page="../../home/toolbar.jsp" />
<span id="btnMaintain1">
<input class="toolbar_default" type="button" followPK="false" name="approveOne" value="審　核" onClick="clickApproveOne();">&nbsp;|
</span>
<span id="btnMaintain2">
<input class="toolbar_default" type="button" followPK="false" name="unApproveOne" value="取消審核" onClick="clickUnApproveOne();">&nbsp;|
</span>
<span id="btnMaintain3">
<input class="toolbar_default" type="button" followPK="false" name="approveAll" value="全部審核" onClick="clickApproveAll();">&nbsp;|
</span>
<span id="btnMaintain4">
<input class="toolbar_default" type="button" followPK="false" name="unApproveAll" value="全部取消審核"  onClick="clickUnApproveAll();">&nbsp;|
</span>
<span id="btnReadOnly1">
<input class="toolbar_default" type="button" followPK="false" name="listHidden1" value="列表隱藏" onclick="listToHidden(this,'formContainer','listContainer')">&nbsp;|
</span>
</td>
</tr>


<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">權狀審核</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">建物標示</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">權狀字號</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">總面積</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">權利分子/分母</a></th>
		</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {false,false,false,false,false,true,true,true,true};
	boolean displayArray[] = {true,true,true,true,true,false,false,false,false};
	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));

	//<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');"></th>			
	/* 原始寫法, 刪掉有點可惜, 故保留, 若要使用, 請記得加入sList.js, 並增加以上的html row	
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[9];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		while(it.hasNext()){		
			counter++;	
			rowArray= (String[])it.next();
			tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[5]+"','"+rowArray[6]+"','"+rowArray[7]+"','"+rowArray[8]+"')\"";
			sbHEML.append(" <tr class='listTR' >\n");			
			sbHEML.append(" <td class='listTD' " + tempJS + ">"+counter+".</td> ");
			sbHEML.append(" <td class='listTD' ><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[5]+","+rowArray[6]+","+rowArray[7]+","+rowArray[8]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\">\n");
			sbHEML.append(" <td class='listTD' " + tempJS + ">"+rowArray[0]+"</td>\n ");
			sbHEML.append(" <td class='listTD' " + tempJS + ">"+rowArray[1]+"</td>\n ");		
			sbHEML.append(" <td class='listTD' " + tempJS + ">"+rowArray[2]+"</td>\n ");
			sbHEML.append(" <td class='listTD' " + tempJS + ">"+rowArray[3]+"</td>\n ");			
			sbHEML.append(" <td class='listTD' " + tempJS + ">"+rowArray[4]+"</td></tr>\n ");			
		}
	}
	out.write(sbHEML.toString());
	
	*/
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
			if (form1.q_signNo1.value=="") {
				getSelectedValue(form1.q_signNo1,"E000000");
				changeSignNo1('q_signNo1','q_signNo2','q_signNo3','E000000');
			}
			if (form1.q_proofVerify.value=="") form1.q_proofVerify.value="N";			
			break;
	}
	return true;
}
</script>
</body>
</html>

