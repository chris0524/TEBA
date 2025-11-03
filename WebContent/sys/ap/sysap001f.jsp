
<!--
程式目的：使用者資料管理
程式代號：sysap001f
程式日期：0950321
程式作者：clive.chang
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ap.SYSAP001F">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%
obj.setUserIsAdminManager(user.getIsAdminManager());

if("Y".equals(user.getIsAdminManager())){

}else{
	obj.setOrganID(user.getOrganID());
}

if (obj.checkAUTH(user.getGroupID(), "530")) {
	if ("queryAll".equals(obj.getState())) {
	    if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	}else if ("queryOne".equals(obj.getState())) {
	    obj = (sys.ap.SYSAP001F)obj.queryOne();    
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
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../unt/ch/untch.js"></script>
<script type="text/javascript">
var insertDefault;  //二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("isStop","N"),
	new Array("isAdminManager","N"),	
	new Array("organID","<%=user.getOrganID()%>"),
	new Array("organIDName","<%=user.getOrganName()%>")
);

function checkField(){
    var alertStr="";
    if(form1.state.value=="queryAll"){
        //alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
        alertStr += checkEmpty(form1.empID,"使用者代碼");
        alertStr += checkEmpty(form1.empName,"使用者姓名");
        alertStr += checkEmpty(form1.groupID,"加入群組");
        alertStr += checkEmpty(form1.isStop,"是否停用");
        alertStr += checkEmpty(form1.isAdminManager,"是否系統管理者");
        alertStr += checkEmpty(form1.organID,"所屬機關");
        alertStr += checkEmpty(form1.empTel,"電話");
        alertStr += checkEmpty(form1.roleid,"角色");
        alertStr += checkDate(form1.takeJobDate,"到職日");  
		if (form1.state.value=="update"||form1.state.value=="updateError") {
			alertStr += checkEmpty(form1.empPWD,"登入密碼");
			alertStr += checkEmpty(form1.empPWD1,"登入密碼確認");
		}		
        if (form1.empPWD.value!=form1.empPWD1.value) {
			alertStr += "登入密碼和登入密碼確認資料不一致，請重新輸入！";
		}
    }
    if(alertStr.length!=0){ alert(alertStr); return false; }
    beforeSubmit();
}

function queryOne(empID){
    form1.empID.value=empID;
    form1.state.value="queryOne";
    beforeSubmit();
    form1.submit();
}

function sendMail(){
    document.all('email').click();
}

function init(){
	if('<%=user.getIsAdminManager()%>' == 'Y'){
		$("select[name='isAdminManager']").attr('class','field');
	}else{
		$("select[name='isAdminManager']").attr('class','field_RO');
	}
	if('<%=user.getIsAdminManager()%>' == 'Y'){
		$("tr[name='tr_enterOrg']").show();
	}else{
		$("tr[name='tr_enterOrg']").hide();
	}	
}
function changeSelect(){
	var str = form1.groupID.value;
	var sa=str.split(";");
	form1.organID.value=sa[1];
}

//停用確認使用者在該機關是否還有財產
function checkProperty(){
	
	var comment={};

	comment.isStop = form1.isStop.value;
	comment.keeperno = form1.keeperno.value;
	comment.organID = form1.organID.value;

    var data = $.ajax({
        url: 'sysap001f_ajax.jsp',
        data: comment,
        type: 'post',
        async : false ,
        dataType: "json",
        success: function (data) {
            $("input[name='organsname']").val(data.organsname);
            $("input[name='ownership']").val(data.ownership);
            $("input[name='differencekind']").val(data.differencekind);
    		$("input[name='propertyno']").val(data.propertyno);
    		$("input[name='serialno']").val(data.serialno);
    		$("input[name='count']").val(data.count);
    		$("input[name='msg']").val(data.msg);
        },
        error: function (xhr, ajaxOptions, thrownError) {
        	alert("系統發生錯誤:" + thrownError + "\r\n請洽相關人員處理" );
        }
    });
	if (form1.count.value != 0 && form1.count.value != null){
		alert("此使用者尚有保管/使用之財產"+"\r\n" + "入帳機關    ：" + form1.organsname.value + "\r\n" + "權屬            ：" + form1.ownership.value + "\r\n" 
				+ "財產區分別：" + form1.differencekind.value + "\r\n" + "財產編號    ：" + form1.propertyno.value + "\r\n" + "財產分號    ：" + form1.serialno.value + "\r\n" 
				+ "                                                                          等共 " + form1.count.value + " 筆");
	} else if (form1.msg.value != "") {
		alert(form1.msg.value);
	}
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:580px;height:200px;display:none">
    <iframe id="queryContainerFrame"></iframe>
    <div class="queryTitle">查詢視窗</div>
    <table class="queryTable"  border="1">
    <tr>
        <td class="queryTDLable">使用者代碼：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_empID" size="20" maxlength="20" value="<%=obj.getQ_empID()%>">
        </td>
        <td class="queryTDLable">是否停用：</td>
        <td class="queryTDInput">
            <select class="field_Q" type="select" name="q_isStop">
            <%=util.View.getYNOption(obj.getQ_isStop())%>
            </select>
        </td>        
    </tr>
    <tr>
        <td class="queryTDLable">使用者姓名：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="q_empName" size="20" maxlength="20" value="<%=obj.getQ_empName()%>">
        </td>
        <td class="queryTDLable">系統管理者：</td>
        <td class="queryTDInput">
            <select class="field_Q" type="select" name="q_isAdminManager">
            <%=util.View.getYNOption(obj.getQ_isAdminManager())%>
            </select>
        </td>          
    </tr>
    <tr name='tr_enterOrg'>
        <td class="queryTDLable">所屬機關：</td>
        <td class="queryTDInput" colspan="3">
            <%=util.View.getPopOrgan("field_Q","q_organID",obj.getOrganID(),obj.getOrganIDName())%>
        </td>
    </tr>
    <tr>
     	<td class="queryTDLable">所屬單位：</td>
        <td class="queryTDInput" colspan="3">
        	<select class="field_Q" type="select" name="q_unitID" >
				<%=util.View.getOption("select  unitno, unitname from UNTMP_KEEPUNIT where 1=1 and enterorg = '" + Common.esapi(obj.getOrganID()) + "' order by unitno", obj.getQ_unitID())%>
			</select>
			<input class="field_Q" type="button" name="btn_unitID" onclick="popUnitNo(form1.q_organID.value,'q_unitID')" value="..." title="單位輔助視窗">
        </td>     
    </tr>
    <tr>        
        <td class="queryTDLable">角色：</td>
        <td class="queryTDInput" colspan="3">
           <select class="field_Q" type="select" name="q_roleid">
			　　<%=util.View.getTextOption("1;保管人;2;經辦單位;3;財產管理人員;",obj.getQ_roleid())%>
			</select>
		</td>          
    </tr>
	<tr>
        <td class="queryTDLable">所屬群組：</td>	
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_groupID">
            	<%=obj.getOption("Select groupID, organID, groupName from SYSAP_Group where 1=1", obj.getQ_groupID(), user.getOrganID())%>
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
    <tr name="tr_enterOrg">
        <td class="td_form"><font color="red">*</font>所屬機關：</td>        
        <td class="td_form_white" colspan="3">
            <%=util.View.getPopOrgan("field","organID",obj.getOrganID(),obj.getOrganIDName())%>
            <input type="hidden" name="organID2" value="<%=obj.getOrganID()%>">			
        </td>
    </tr>   
     <tr>
     	<td class="td_form">所屬單位：</td>
        <td class="td_form_white" colspan="3">
            <select class="field" type="select" name="unitID" >
				<%=util.View.getOption("select  unitno, unitname from UNTMP_KEEPUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno", obj.getUnitID())%>
			</select>
			<input class="field" type="button" name="btn_unitID" onclick="popUnitNo(form1.organID.value,'unitID')" value="..." title="單位輔助視窗">
        </td>
    </tr>   
     <tr>
        <td class="td_form">保管人：</td>
        <td class="td_form_white" colspan="3">
            <select class="field" type="select" name="keeperno" onchange="checkProperty()" >
				<%=util.View.getOption("select  keeperno, keepername from UNTMP_KEEPER where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by keeperno", obj.getKeeperno())%>
			</select>
	        <input class="field" type="button" name="btn_keeperno" onclick="popUnitMan(form1.organID.value,'keeperno')" value="..." title="人員輔助視窗">        
		</td>
    </tr>   
    <tr>
        <td class="td_form"><font color="red">*</font>使用者代碼：</td>
        <td class="td_form_white">
            <input class="field_P" type="text" name="empID" size="20" maxlength="20" value="<%=obj.getEmpID()%>">        </td>
        <td class="td_form"><font color="red">*</font>使用者姓名：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empName" size="20" maxlength="10" value="<%=obj.getEmpName()%>">        </td>
    </tr>
    <tr>        
        <td class="td_form"><font color="red">*</font>加入群組：</td>
        <td class="td_form_white">
            <select class="field" type="select" name="groupID" onchange="changeSelect()">
            	<%=obj.getOption("Select groupID+';'+organID, organID, groupName from SYSAP_Group where 1=1", obj.getGroupID(), obj.getOrganID())%>
            </select>        
		</td>
        <td class="td_form"><font color="red">*</font>是否停用：</td>
        <td class="td_form_white">
            <select class="field" type="select" name="isStop" onchange="checkProperty()">
            <%=util.View.getYNOption(obj.getIsStop())%>
            </select>        </td>        
    </tr>
    <tr>        
        <td class="td_form"><font color="red">*</font>角色：</td>
        <td class="td_form_white">
           <select class="field" type="select" name="roleid">
			　　<%=util.View.getTextOption("1;保管人;2;經辦單位;3;財產管理人員;",obj.getRoleid())%>
			</select>
		</td>  
        <td class="td_form"><font color="red">*</font>系統管理者：</td>
        <td class="td_form_white">
            <select class="field" type="select" name="isAdminManager">
            <%=util.View.getYNOption(obj.getIsAdminManager())%>
            </select>        </td>        
    </tr>
    <tr>
        <td class="td_form"><font color="red">*</font>電話：</td>
        <td class="td_form_white" colspan="3">
            <input class="field" type="text" name="empTel" size="15" maxlength="30" value="<%=obj.getEmpTel()%>">      </td>
    </tr>
    
          
	<tr>
		<td class="td_form"><font color="red">*</font>登入密碼：</td>
	    <td class="td_form_white">
	    	<input class="field" type="password" name="empPWD" size="15" maxlength="30" value="<%=obj.getEmpPWD()%>">
	    </td>
	    <td class="td_form"><font color="red">*</font>登入密碼確認：</td>
	    <td class="td_form_white">
	    	<input class="field" type="password" name="empPWD1" size="15" maxlength="30" value="<%=obj.getEmpPWD()%>">
	    </td>	    
    </tr>
    
    <tr>
        <td class="td_form">職稱：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empTitle" size="15" maxlength="30" value="<%=obj.getEmpTitle()%>">        </td>
        <td class="td_form">電子郵件信箱：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empEmail" size="15" maxlength="50" value="<%=obj.getEmpEmail()%>">        </td>
    </tr>
    <tr>
        <td class="td_form">傳真：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="empFax" size="15" maxlength="30" value="<%=obj.getEmpFax()%>">        </td>
        <td class="td_form">主管姓名：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="managerName" size="15" maxlength="30" value="<%=obj.getManagerName()%>">        </td>
    </tr>
    <tr>
        <td class="td_form">到職日：</td>
        <td class="td_form_white">
            <%=util.View.getPopCalndar("field","takeJobDate",obj.getTakeJobDate())%>        </td>
        <td class="td_form">主管電話：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="managerTel" size="15" maxlength="30" value="<%=obj.getManagerTel()%>">        </td>
    </tr>
    <tr>        
        <td class="td_form">主管職稱：</td>
        <td class="td_form_white">
            <input class="field" type="text" name="managerTitle" size="15" maxlength="30" value="<%=obj.getManagerTitle()%>">        </td>
            
	    <td class="td_form" style='display:none'>異動人員/日期：</td>
	        <td class="td_form_white"  style='display:none'>[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">
	          /
	          <input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">] </td>
	</tr>
    <tr>        
        <td class="td_form">備註：</td>
        <td class="td_form_white" colspan="3">
            <textarea name="memo" cols="30" rows="4" class="field"><%=obj.getMemo()%></textarea>        </td>
    </tr>
    </table>
    </div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
    <input type="hidden" name="state" value="<%=obj.getState()%>">
    <input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
    <input type="hidden" name="userID" value="<%=user.getUserID()%>">
    <input type="hidden" name="userOrganID" value="<%=user.getOrganID()%>">
    <input type="hidden" name="userRoleid" value="<%=user.getRoleid()%>">
    <input type="hidden" name="propertyno"> 
    <input type="hidden" name="differencekind">
    <input type="hidden" name="count"> 
    <input type="hidden" name="serialno">
    <input type="hidden" name="ownership">
    <input type="hidden" name="organsname">
    <input type="hidden" name="msg">
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
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">所屬機關</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">使用者代碼</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">使用者姓名</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">職稱</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">電話</a></th>
	</tr>		
    </thead>	
    <tbody id="listTBODY">
    <%
    boolean primaryArray[] = {false,true,false,false,false};
    boolean displayArray[] = {true,true,true,true,true};
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
<%
} else {
	out.println("<br><br><br><p align=center>對不起，您沒有足夠的權限執行此功能，若有問題，請洽系統管理者或承辦人員！<br><br></p>");		
}
%>
