<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ac.UNTAC005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if("Y".equals(user.getIsAdminManager()) || (TCGHCommon.getSYSCODEName("01", "ETO").equals(user.getOrganID()) && "3".equals(user.getRoleid()))){

obj.setIsAdminManager(user.getIsAdminManager());
obj.setOrganID(user.getOrganID());
obj.setUserID(user.getUserID());
obj.setUserName(user.getUserName());

if ("update".equals(obj.getState())) {
	obj.calculateBalanceInMonth();	
}

objList = obj.queryAll();

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
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	
);

function checkField(){
	var alertStr = "";
	var i = 0;
	var errorMsg = "";
	
	if(confirm('確定執行關帳作業？')){
		$("input[name='sKeySet']").each(function(){
			if($(this).attr('checked') == 'checked'){
				if($("input[name='q_closing2YM']:eq(" + i + ")").val() == ''){
					$("input[name='q_closing2YM']:eq(" + i + ")").css('backgroundColor',errorbg);
					errorMsg += "error";
				}
			}else{
				$("input[name='q_closing2YM']:eq(" + i + ")").css('backgroundColor','');
			}
			
			i++;
		});
		
		if(errorMsg == 'error'){alertStr += "關帳日期不可為空!!";}		
		
		form1.state.value = 'update';
		beforeSubmit();	
	}else{
		return false;
	}
}

function queryOne(){

}

function checkURL(surl){
	
}

function init(){
	
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
	
</td></tr></table>

<!--頁籤區============================================================-->

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg">
	<div>
	<table class="table_form" width="100%" height="50%">
		<tr>
			<td class="queryTDInput" style="text-align:center;">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="執行關帳">
			</td>			
		</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;display:none;">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div>
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">NO</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">機關</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">最後關帳年月</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">關帳者</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">關帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',7,false);" href="#">回復者</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',8,false);" href="#">回復日期</a></th>
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
		//String tempJS="";
		String isCheck = "unchecked";			
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[2]+","+rowArray[4])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";			
			sbHEML.append(" <tr class='listTR'>\n");			
			sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+Common.checkGet(rowArray[0])+","+Common.checkGet(rowArray[2])+","+Common.checkGet(rowArray[4])+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			sbHEML.append(" <td class='listTD'>"+counter+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[1])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[3])+"</td>\n ");
			sbHEML.append(" <td class='listTD'><input class=\'field_Q\' type=\'text\' name=\'q_closing2YM\' size=\'5\' maxlength=\'5\' value=\'" + Common.checkGet(rowArray[4]) + "\' ></td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[5])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[6])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[7])+"</td>\n ");
			sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[8])+"</td>\n ");
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
</body>
</html>
<%
} else {
	out.println("<br><br><br><p align=center>對不起，您沒有足夠的權限執行此功能，若有問題，請洽系統管理者或承辦人員！<br><br></p>");		
}
%>