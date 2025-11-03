<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.eg.UNTEG001F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if("".equals(Common.checkGet(obj.getEnterOrg()))){
	obj.setEnterOrg(user.getOrganID());
}

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.eg.UNTEG001F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
	obj = (unt.eg.UNTEG001F)obj.queryOne();
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
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("enterOrg","<%=user.getOrganID()%>"),
	new Array("enterOrgName","<%=user.getOrganName()%>"),
	new Array("createDate","<%=util.Datetime.getYYYMMDD()%>"),
	new Array("unitID","<%=user.getUnitID()%>"),
	new Array("unitIDQuickly","<%=user.getUnitID()%>")
);
	
function checkField(){
	var alertStr="";
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.unitID,"組室別");
		alertStr += checkEmpty(form1.deprPark,"園區別");
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
}

function queryOne(enterOrg,engineeringNo){
	form1.enterOrg.value = enterOrg;
	form1.engineeringNo.value = engineeringNo;	
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function checkURL(surl){
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){	
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else if(form1.engineeringNo.value==""){
		alert("請先執行查詢!");
	}else{
		form1.mainPage.value=form1.currentPage.value;
		form1.currentPage.value=1;
		
		form1.action = surl;
		beforeSubmit();
		form1.submit();
	}
}

function init(){
	var dcc1 = new DataCouplingCtrlPlus(form1.organID, form1.unitIDQuickly, form1.unitID,null,null,false,false);

	autoListContainerRowClick();
	
	//問題單2197，為避免使用者開新視窗導致當前路徑為空，因此直接寫死
	form1.objPath.value = "功能選單 > > 單位財產系統 > > 營建工程管理 > > 營建工程資料維護";
}

function autoListContainerRowClick() {
	if (form1.enterOrg.value !== '' && form1.engineeringNo.value !== '') {
		var key = form1.enterOrg.value + form1.engineeringNo.value;
		listContainerRowClick(key);
	}
}


</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
  <tr>
    <td ID=t1 CLASS="tab_border1" HEIGHT="25">*營建工程資料維護</td>
    <td ID=t2 CLASS="tab_border2"><a href="#" onClick="return checkURL('unteg002f.jsp');">未入帳新增財產</a></td>
    <td ID=t3 CLASS="tab_border2"><a href="#" onClick="return checkURL('unteg003f.jsp');">已入帳新增財產</a></td>
    <td ID=t4 CLASS="tab_border2"><a href="#" onClick="return checkURL('unteg004f.jsp');">未入帳增減值</a></td>
    <td ID=t5 CLASS="tab_border2"><a href="#" onClick="return checkURL('unteg005f.jsp');">已入帳增減值</a></td>
  </tr>
  <tr>
		<td class="tab_line1"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
		<td class="tab_line2"></td>
  </tr>
</TABLE>
<!--Query區============================================================-->
<div id="queryContainer" style="width:550px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTEG001Q",obj);%>
	<jsp:include page="unteg001q.jsp" />
</div>
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
        <td class="td_form">入帳機關：</td>
        <td class="td_form_white">
            <input class="field_PRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg() %>" >
      		[<input class="field_PRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName() %>">] 
		<td class="td_form">建檔日期：</td>
		<td class="td_form_white" colspan="3">
			<%=util.View.getPopCalndar("field","createDate",obj.getCreateDate())%>
		</td>
    </tr>
	<tr>
      <td class="td_form"><font color="red">*</font>工程編號：</td>
      <td class="td_form_white" colspan="3">
      	<input class="field" type="text" name="engineeringNo" size="20" maxlength="20" value="<%=obj.getEngineeringNo()%>">
      </td>
    </tr>
	<tr>
      <td class="td_form"><font color="red">*</font>工程名稱：</td>
	  <td class="td_form_white" colspan="3">
		<input class="field" type="text" name="engineeringName" size="80" maxlength="50" value="<%=obj.getEngineeringName()%>">
      </td>
    </tr>
    <tr>
      <td class="td_form">未入帳工程總價：</td>
      <td class="td_form_white">
      [<input class="field_RO" type="text" name="engAmount_NoVerify" size="20" maxlength="50" value="<%=obj.getEngAmount_NoVerify()%>">]
      </td>
      <td class="td_form">已申請增減值總價：</td>
	  <td class="td_form_white">
	  [<input class="field_RO" type="text" name="engAmount_adjust_NoVerify" size="20" maxlength="25" value="<%=obj.getEngAmount_adjust_NoVerify()%>">]
      </td>
    </tr>
    	<tr>
      <td class="td_form">已入帳工程總價：</td>
      <td class="td_form_white">
      [<input class="field_RO" type="text" name="engAmount_Verify" size="20" maxlength="50" value="<%=obj.getEngAmount_Verify()%>">]
      </td>
      <td class="td_form">已確認增減值總價：</td>
	  <td class="td_form_white">
	  [<input class="field_RO" type="text" name="engAmount_adjust_verify" size="20" maxlength="25" value="<%=obj.getEngAmount_adjust_Verify()%>">]
      </td>
    </tr>
    <tr>
      <td class="td_form">工程款總價合計：</td>
      <td class="td_form_white" colspan="3">
      [<input class="field_RO" type="text" name="engAmount_Total" size="20" maxlength="50" value="<%=obj.getEngAmount_Total()%>">]
      </td>
    </tr>
    <tr>
      <td class="td_form">已提列公共設施建設費金額：</td>
      <td class="td_form_white" colspan="3">
      <input class="field" type="text" name="buildFee" size="20" maxlength="50" value="<%=obj.getBuildFee()%>">
      </td>
    </tr>
    <tr>
		<td class="td_form"><font color="red">*</font>組室別：</td>
		<td class="queryTDInput">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getEnterOrg() + "' order by unitno ", 
				                                                       "field", "form1", "unitID", "unitIDQuickly", obj.getUnitID()) %>
			<input class="field" type="button" name="btn_unitID" onclick="popUnitNo(form1.organID.value,'unitID')" value="..." title="單位輔助視窗">
	   </td>
		<td class="td_form"><font color="red">*</font>園區別：</td>
		<td class="td_form_white">
			<select class="field" type="select" name="deprPark" onchange="syncDeprData();">
					<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getDeprPark())%>
			</select>
		</td>
	</tr>
	<tr>
	    <td class="td_form">備註：</td>
	    <td class="td_form_white">
			  <textarea class="field" type="text" name="notes" cols="30" rows="4"><%=obj.getNotes()%></textarea>
		  </td>
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
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="listContainerActiveRowId" value="<%=obj.getListContainerActiveRowId()%>">	
	<input type="hidden" name="groupID" value="<%=user.getGroupID()%>">
	<input type="hidden" name="saveLog" value="Y">
	<input type="hidden" name="isUnteg001f" value="Y">
	<input type="hidden" name="objPath" >
	<jsp:include page="../../home/toolbar.jsp" />
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">工程編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">工程名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">未入帳工程總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">已入帳工程總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">已申請增減值總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">已確認增減值總價</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">工程款總價</a></th>	
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">建檔日期</a></th>				
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,true,false,false,false,false,false,false,false};
	boolean displayArray[] = {false,true,true,true,true,true,true,true,true};
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
