<!--
程式目的：土地合併分割重測重劃作業－增加單管理資料挑選
程式代號：untla063f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA063F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%

if ("insertToUntla_Manage".equals(obj.getState())) {
	obj.afterWork();
	obj.setMessage("afterWorkOver");	
}else if("execCloseWindow".equals(obj.getState())) {
	obj.cencelWork();
	obj.setMessage("execCloseWindow");
}

obj.setQueryAllFlag("true");	

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
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var message = '<%=obj.getMessage()%>';

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(	
);

var serialNo1Array = new Array();

function init(){
	if(message=='afterWorkOver'){
		alert("新增完成");
		message='';
		opener.form1.action="untla062f.jsp";
		opener.form1.state.value='queryAll';
		opener.form1.submit();
		window.close();	
	}else if(message=='execCloseWindow'){
		window.close();	
	}
}

function setSerialNo1(){
	serialNo1Array.splice(0,serialNo1Array.length);
	
	$("input:checkbox[name='sKeySet']").each(function(){
		if($(this).attr("checked")=="checked"){
			serialNo1Array.splice(serialNo1Array.length,0,$(this).val());			
		}
	});	
}

function execSubmitNewInsert(){
	setSerialNo1();

	if(serialNo1Array.length==0){
		alert('尚未點選資料！！');	
	}else{
		if(confirm("確定要新增資料？")){
			viewShow('messageContainer');
			
			form1.state.value="insertToUntla_Manage";
			
			beforeSubmit();		
			form1.submit();	
		}
	}

}

function execCloseWindow(){
	form1.state.value='execCloseWindow';
	form1.submit();
}

$(window).unload(function(){
	if(form1.state.value=='insertToUntla_Manage'){

	}else{
		execCloseWindow();
	}
});


window.onbeforeunload = function confirmExit(){
	if(form1.state.value=='insertToUntla_Manage'){

	}else{
		execCloseWindow();
	}	
}

/*顯示視窗*/
function viewShow(viewName){
	var obj=document.all.item(viewName);
	var objHeight= obj.style.height;
	var objWidth= obj.style.width;
	
	objHeight= objHeight.substring(0,objHeight.length-2);
	objWidth= objWidth.substring(0,objWidth.length-2);

	obj.style.top=(document.body.clientHeight-Number(objHeight))/2-40;
	obj.style.left=(document.body.clientWidth-Number(objWidth))/2;
	obj.style.display="block";	
}

/*隱藏視窗*/
function viewHidden(viewName){
	var viewObj=document.all.item(viewName);		
	viewObj.style.display="none";
	
}

function execCheckboxAll(){
	if($('#checkboxAll').attr('checked')=='checked'){
		$("input[name='sKeySet']").each(function(){
			$(this).attr('checked',true);
		});
	}else{
		$("input[name='sKeySet']").each(function(){
			$(this).attr('checked',false);
		});
	}
}
</script>
</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post">

<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">

<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>" >
<input class="field_QRO" type="hidden" name="differenceKind" value="<%=obj.getDifferenceKind()%>">

<input class="field_QRO" type="hidden" name="caseNo_Merge" value="<%=obj.getCaseNo_Merge()%>">
<input class="field_QRO" type="hidden" name="caseNo_Reduce" value="<%=obj.getCaseNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="caseNo_Add" value="<%=obj.getCaseNo_Add()%>">

<input class="field_QRO" type="hidden" name="propertyNo_Reduce" value="<%=obj.getPropertyNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="propertyNo_Add" value="<%=obj.getPropertyNo_Add()%>">
<input class="field_QRO" type="hidden" name="serialNo_Reduce" value="<%=obj.getSerialNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="serialNo_Add" value="<%=obj.getSerialNo_Add()%>">

<input type="hidden" name="editID" value="<%=user.getUserID()%>">
<input type="hidden" name="editDate" value="<%=util.Datetime.getYYYMMDD()%>">
<input type="hidden" name="editTime" value="<%=util.Datetime.getHHMMSS()%>">

<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">

<!--Form區============================================================-->
	<table width="100%" cellspacing="0" cellpadding="0">
		
		<!--Toolbar區============================================================-->
		<tr><td class="bg" style="text-align:center">
			<div style="position:absolute;width:400px;height:100px;display:none">
				<jsp:include page="../../home/toolbar.jsp" />
			</div>
			|&nbsp;
			<input class="toolbar_default" type="button" followPK="false" name="submitNewInsert" value="確定新增" onClick="execSubmitNewInsert();">&nbsp;|
			<input class="toolbar_default" type="button" followPK="false" name="closeWindow" value="　取消　" onClick="execCloseWindow();">&nbsp;|
		</td></tr>

		<!--標籤區============================================================-->
		<tr>
			<td ID=t2 CLASS="tab_border1">土地管理資料挑選</td>		
		</tr>
		<!--List區============================================================-->
		<tr><td class="bg">
		<div id="listContainerAdd">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEADAdd">
			<tr>
				<th class="listTH"><a class="text_link_w" ><input class="field_Q" type="checkbox" id="checkboxAll" name="checkboxAll" onclick="execCheckboxAll();"></a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">管理次序</a></th>
<!-- 				
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">縣市</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">鄉鎮市區</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">地段</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">母號</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">子號</a></th>
-->
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">使用單位</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">其他使用者</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">使用關係</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">使用期間－起</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">使用期間－訖</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">使用面積(㎡)</a></th>
			</thead>
			<tbody id="listTBODY">
			<%
				int counter=0;
				StringBuffer sbHEML = new StringBuffer();
				if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
					sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
				}else{
					String rowArray[]=new String[9];
					java.util.Iterator it=objList.iterator();
					String tempJS="";
					String isCheck = "unchecked";			
					while(it.hasNext()){
						counter++;	
						rowArray= (String[])it.next();
						isCheck = "unchecked";
						if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
							for (int j=0; j<obj.getsKeySet().length; j++) {
								if (obj.getsKeySet()[j].equals(rowArray[0])) {
									isCheck = "checked";
								}
							}
						}			
						
						//這個是key值 和.java裡的rowArray變數是對照的
						sbHEML.append(" <tr class='listTR' >\n");	
						sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[8]+"\" " + isCheck + ">\n");
						sbHEML.append(" <td class='listTD'><input class='field_RO' type='text' name='serialNo1' size='3' maxlength='3' value='" + rowArray[0] + "' disabled='true'></td>\n ");
//						sbHEML.append(" <td class='listTD'><select class='field_RO' type='select' name='signNo1' disabled='true'>"
//											+ util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",rowArray[1]) +
//											"</select></td>\n");
//						sbHEML.append(" <td class='listTD'><select class='field_RO' type='select' name='signNo2' disabled='true'>"
//											+ util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '___0000' order by seqno",rowArray[2]) +
//											"</select></td>\n");
//						sbHEML.append(" <td class='listTD'><select class='field_RO' type='select' name='signNo3' disabled='true'>"
//											+ util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '" + rowArray[3].substring(0,3) + "____' order by seqno",rowArray[3]) +
//											"</select></td>\n");
//						sbHEML.append(" <td class='listTD'><input class='field_RO' type='text' name='signNo4' size='4' maxlength='4' value='" + rowArray[4] + "' disabled='true' ></td>\n ");
//						sbHEML.append(" <td class='listTD'><input class='field_RO' type='text' name='signNo5' size='4' maxlength='4' value='" + rowArray[5] + "' disabled='true' ></td>\n ");
						
						sbHEML.append(" <td class='listTD'><input class='field' type='hidden' name='useUnit' value='"+ rowArray[1] +"' disabled='true'><input class='field_RO' type='text' name='useUnitName' size='20' maxlength='50' value='" + rowArray[2] + "' disabled='true'></td>\n ");
						sbHEML.append(" <td class='listTD'><input class='field_RO' type='text' name='useUnit1' size='10' value='" + rowArray[3] + "' disabled='true' ></td>\n ");
						sbHEML.append(" <td class='listTD'>").append("<select class=\"field\" type=\"select\" name=\"useRelation\" disabled='true'>")
										.append(util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='URE' ORDER BY CODEID", rowArray[4]))
										.append("</select>").append("</td>\n ");
						
						sbHEML.append(" <td class='listTD'><input class='field_RO' type='text' name='useDateS' size='5' maxlength='5' value='" + rowArray[5] + "' disabled='true' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class='field_RO' type='text' name='useDateE' size='5' maxlength='5' value='" + rowArray[6] + "' disabled='true'></td>\n ");
						sbHEML.append(" <td class='listTD'><input class='field_RO' type='text' name='useArea' size='5' maxlength='5' value='" + rowArray[7] + "' disabled='true'></td>\n ");
						
						sbHEML.append(" </tr>\n");
					}
				}
				out.write(sbHEML.toString());	
			
			%>
			</tbody>
		</table>
		</div>
		</td></tr>
	</table>
		
		

<!--Query區============================================================-->
<div id="messageContainer" style="position:absolute;width:400px;height:100px;display:none">
	<div class="queryTitle">訊息</div>
	<table class="queryTable" border="1">
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align: center;">
		程式處理中，請稍後
		</td>
	</tr>
	</table>
</div>

</form>
</body>
</html>
