<!--  
程式目的：土地減少作業－減損單資料
程式代號：untla012f
程式日期：0940819
程式作者：judy
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA012F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.la.UNTLA012F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}else if ("approveAll".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.approveAll();
}else if ("approveRe".equals(obj.getState())) {
	obj.approveRe();
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
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("writeDate","<%=util.Datetime.getYYYMMDD()%>"),
	//new Array("reduceDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("verify","N"),
	new Array("writeUnit","<%=user.getUnitName()%>"),
	new Array("proofDoc","<%=new unt.ba.UNTBA002F().getDefaultProofDoc(user.getOrganID(),"A2")%>"),
	new Array("closing","N")
);
<!-- 檢查區，譬如：日期、空白... -->
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
		if (document.all.querySelect[0].checked) {
			form1.action = "untla012f.jsp";
		} else {
			form1.action = "untla013f.jsp";
		}
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.enterOrg,"入帳機關");
		alertStr += checkEmpty(form1.ownership,"權屬");
		alertStr += checkDate(form1.writeDate,"填單日期");
		alertStr += checkEmpty(form1.proofDoc,"減損單編號－字");
		if(form1.verify.value == "Y"){
			alertStr += checkEmpty(form1.reduceDate,"若要入帳，入帳日期");
		}else{
			form1.reduceDate.value="";
		}		
		alertStr += checkDate(form1.reduceDate,"入帳日期");
		alertStr += checkDate(form1.approveDate,"核准日期");
		alertStr += checkLen(form1.approveDoc,"核准文號",20);
		alertStr += checkLen(form1.notes, "備註", 250);

		if (form1.verify.value=="Y") {
			alertStr += checkEmpty(form1.approveDate,"核准日期");
			alertStr += checkEmpty(form1.approveDoc,"核准文號");
			alertStr += checkEmpty(form1.approveOrg,"核准機關");
		}
		
		//if(form1.approveOrg.value=="02" || form1.approveOrg.value=="03" || form1.approveOrg.value=="04"|| form1.approveOrg.value=="05"|| form1.approveOrg.value=="06"){
			//alertStr += checkEmpty(form1.approveDate,"核准日期");
			//alertStr += checkEmpty(form1.approveDoc,"核准文號");
		//}
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}

function getEnterDate() {
	if (form1.verify.value=="Y") {
		if (form1.reduceDate.value=="") form1.reduceDate.value=getToday();
	}
}

function queryOne(enterOrg,ownership,caseNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function clickApproveAll(){
	if(confirm("您確定要將列表中未入帳的減損單全部入帳?")){
		document.all('state').value='approveAll';
		beforeSubmit();
		form1.submit();
	}
}

function clickApproveRe(){
	if(confirm("您確定是否回復入帳?")){
		document.all('state').value='approveRe';
		beforeSubmit();
		form1.submit();
	}
}	

function checkURL(surl){
	columnTrim(form1.caseNo);
	if(form1.caseNo.value==""){
		alert("請先執行查詢!");
	}else if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{			
		if (document.all.querySelect[0].checked || form1.state.value=="insertSuccess") {
			surl=surl+"?initDtl=Y";
			form1.state.value="queryAll";
		} else {
			if (form1.propertyNo.value!="" && form1.serialNo.value!="") form1.state.value = "queryOne";
		}
		form1.action = surl;
		beforeSubmit();
		form1.submit();	
	}
}

function init(){
	var arrField = new Array("update","delete");
	//欲修改、刪除減損單資料須至「土地合併分割作業」進行
	if(form1.mergeDivision.value!=""){
		setFormItem("update,delete", "R");
	}
	//=========================================
	if (form1.verify.value=="Y") {
		setFormItem("update,delete", "R");
	}	
	if (form1.enterOrg.value!="<%=user.getOrganID()%>") {		
		setFormField(new Array("update","delete","approveAll","verifyRe"),"R");
	}
}

function changeApproveOrg(){
	var approveOrg = parse(form1.approveOrg.value);
	if(approveOrg.length<=0){
		setFormItem("approveDate,btn_approveDate,approveDoc", "R");
		form1.approveDate.value="";
		form1.approveDoc.value="";
	}else{
		setFormItem("approveDate,btn_approveDate,approveDoc", "O");
	}
}

function loadUntla015r(){
	var url = "untla015p.jsp?"
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value 
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE="+form1.caseNo.value 
			+ "&q_kind=4";
	window.open(url);
}

function loadUntla017r(){
	var url = "untla017p.jsp?" 
			+ "organID=" + form1.organID.value
			+ "&q_enterOrg=" + form1.enterOrg.value 
			+ "&q_ownership=" + form1.ownership.value
			+ "&q_caseNoS=" + form1.caseNo.value 
			+ "&q_caseNoE="+form1.caseNo.value 
			+ "&q_kind=4";
	
	window.open(url);
}

function getReduceDate() {
	if (form1.verify.value=="Y") {
		if (form1.reduceDate.value=="") form1.reduceDate.value="<%=util.Datetime.getYYYMMDD()%>";
	}
}

</script>
</head>
<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
	<input type="hidden" name="propertyNo" value="<%=obj.getPropertyNo()%>">
	<input type="hidden" name="serialNo" value="<%=obj.getSerialNo()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
</td></tr></table>

<!--Query區============================================================-->
<div id="queryContainer" style="width:750px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA012Q",obj);%>
	<jsp:include page="untla012q.jsp" />
</div>

<!--頁籤區============================================================-->
<table STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
	<tr>
		<td ID=t1 CLASS="tab_border1" HEIGHT="25">減損單資料</td>		
		<td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('untla013f.jsp');">減損單明細資料</a></td>	
	</tr>
	<tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>	
	</tr>
</table>

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form">電腦單號：</td>
		<td colspan="3" class="td_form_white"><table width="100%" height="100%" border="0">
              <tr>
                <td width="35%">[
                  <input class="field_PRO" type="text" name="caseNo" size="10" maxlength="10" value="<%=obj.getCaseNo()%>">
]</td>
                <td width="18%" align="right"> 案名：                </td>
                <td><input class="field" type="text" name="caseName" size="30" maxlength="25" value="<%=obj.getCaseName()%>"></td>
              </tr>
            </table>		</td>
      </tr>
	<tr>
		<td class="td_form" width="20%"><font color="red">*</font>權屬：</td>
		<td colspan="3" class="td_form_white">
		<table width="100%" height="100%" border="0">
          <tr>
            <td width="35%">
            	<select class="field_P" type="select" name="ownership">
              <%=util.View.getOnwerOption(obj.getOwnership())%>
            </select>
            &nbsp;&nbsp;
			填單日期：
	        <%=util.View.getPopCalndar("field","writeDate",obj.getWriteDate())%>
			</td>
          </tr>
        </table></td>
      </tr>
	<tr>
		<td class="td_form">填造單位：</td>
		<td class="td_form_white" colspan="3">			<table width="100%" height="100%" border="0">
          <tr>
            <td width="35%"><input class="field" type="text" name="writeUnit" size="30" maxlength="15" value="<%=obj.getWriteUnit()%>"></td>
            <td width="36%" align="right">財產管理單位編號： </td>
            <td><input class="field" type="text" name="manageNo" size="10" maxlength="10" value="<%=obj.getManageNo()%>"></td>
          </tr>
        </table></td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>減損單編號：</td>
		<td class="td_form_white" colspan="3"><table width="100%" height="100%" border="0">
              <tr>
                <td width="55%"><input class="field" type="text" name="proofDoc" size="15" maxlength="10" value="<%=obj.getProofDoc()%>">
&nbsp;字第 [
<input class="field_RO" type="text" name="proofNo" size="10" maxlength="10" value="<%=obj.getProofNo()%>">
]&nbsp;號</td>
                <td width="21%" align="right">傳票號數：</td>
                <td><input class="field" type="text" name="summonsNo" size="15" maxlength="15" value="<%=obj.getSummonsNo()%>"></td>
              </tr>
            </table>			</td>
	</tr>
	<tr>
		<td class="td_form">核准機關：</td>
		<td class="td_form_white" colspan="3">
            <select class="field" type="select" name="approveOrg" onChange="changeApproveOrg();">
                  <%=util.View.getOption("select  codeID, codeName from SYSCA_Code where codeKindID='APO' ", obj.getApproveOrg())%>
            </select>
				&nbsp;&nbsp;&nbsp;核准日期：<%=util.View.getPopCalndar("field","approveDate",obj.getApproveDate())%>
				&nbsp;&nbsp;&nbsp;核准文號：<input class="field" type="text" name="approveDoc" size="20" maxlength="20" value="<%=obj.getApproveDoc()%>">
	</tr>
	<tr>
		<td class="td_form" width="20%">入帳機關：</td>
		<td colspan="3" class="td_form_white">		  <table width="100%" height="100%" border="0">
          <tr>
            <td width="35%">
            	<input class="field_PRO" type="HIDDEN" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
				[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
				&nbsp;&nbsp;&nbsp;            
				入帳：
	        	<select class="field_RO" type="select" name="verify" onChange="getReduceDate();">
					<%=util.View.getYNOption(obj.getVerify())%>
				</select>
				&nbsp;&nbsp;&nbsp;
	        	入帳日期：
                    <%=util.View.getPopCalndar("field","reduceDate",obj.getReduceDate())%>
                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月結：
          	        <select class="field_RO" type="select" name="closing">
            	       <%=util.View.getYNOption(obj.getClosing())%>
          	        </select>
			</td>
          </tr>
        </table></td>
      </tr>
	<tr>
		<td class="td_form">合併分割案號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="mergeDivision" size="10" maxlength="10" value="<%=obj.getMergeDivision()%>">]        	
		</td>				
	</tr>
	<tr>
		<td width="20%" rowspan="3" class="td_form" >備註：</td>
		<td width="30%" class="td_form_white" >	
			<textarea class="field" name="notes" cols="30" rows="4" ><%=obj.getNotes()%></textarea>
		</td>
		<td width="20%" class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="texta" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanVerifyRe">
		<input class="toolbar_default" type="button" followPK="true" name="verifyRe" value="回復入帳" disabled="true" onClick="clickApproveRe();">&nbsp;|
	</span>
	<br>&nbsp;|
	<span id="spanUntla017r">
		<input class="toolbar_default" type="button" followPK="true" name="untla017r" value="列印減損單" onClick="loadUntla017r();">&nbsp;|	
	</span>
	<span id="spanUntla015r">
		<input class="toolbar_default" type="button" followPK="true" name="untla015r" value="列印移交清冊" onClick="loadUntla015r();">&nbsp;|	
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">電腦單號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">減損案名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">填單日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">入帳</a></th>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,true,false,false,false,false,false};
	boolean displayArray[] = {false,false,true,true,true,true,true,true,false};
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
		//刪除之前多出現一道確認訊息
       	case "delete":
		if(!confirm("刪除此減損單，將一併刪除其關聯的明細資料。\n\n您確定要刪除?"))
			return false;
			hasBeenConfirmed = true;
			break;
			
		//做查詢時,將某些欄位填入預設值
		case "queryAll":
			if (form1.querySelect[0].checked==true ||form1.querySelect[1].checked==true) {} 
			else form1.querySelect[0].checked=true;
			if (parse(form1.q_enterOrg.value).length<=0) {
				form1.q_enterOrg.value="<%=user.getOrganID()%>";
				form1.q_enterOrgName.value="<%=user.getOrganName()%>";
				changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','<%=obj.getQ_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
			}
			break;				
	}
	return true;
}

localButtonFireListener.whatButtonFireEvent = function(buttonName){
	//欄位Array
	var	arrField = new Array("CArea","SArea","holdNume","holdDeno","accumDeprYM","accumDepr");

	switch (buttonName)	{
		//新增時要執行的動作
		case "insert":
				setFormItem("verify","R");
				setFormItem("approveDate,btn_approveDate,approveDoc", "R");
			break;
		case "insertError":
				setFormItem("verify","R");
				setFormItem("approveDate,btn_approveDate,approveDoc", "R");
			break;
		//更新時要做的動作
		case "update":
			if(form1.verify.value=="Y"){
				setFormItem("verify","R");
			}else{
				setFormItem("verify","O");
			}
			//若「核准機關」無資料
			var approveOrg = parse(form1.approveOrg.value);
			if(approveOrg.length<=0){
				setFormItem("approveDate,btn_approveDate,approveDoc", "R");
			}else{
				setFormItem("approveDate,btn_approveDate,approveDoc", "O");
			}
			break;
		case "updateError":
			if(form1.verify.value=="Y"){
				setFormItem("verify","R");
			}else{
				setFormItem("verify","O");
			}
			//若「核准機關」無資料
			var approveOrg = parse(form1.approveOrg.value);
			if(approveOrg.length<=0){
				setFormItem("approveDate,btn_approveDate,approveDoc", "R");
			}else{
				setFormItem("approveDate,btn_approveDate,approveDoc", "O");
			}
			break;
	}
}
</script>
</body>
</html>
