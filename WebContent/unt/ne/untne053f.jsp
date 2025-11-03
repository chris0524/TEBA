<!--
程式目的：物品主檔資料維護－接收撥入物品資料
程式代號：untne053f
程式日期：
程式作者：Yuan-Ren Jheng
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE053F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String message="";

obj.setEditID(user.getUserID());
obj.setEditDate(Datetime.getYYYMMDD());
obj.setEditTime(Datetime.getHHMMSS());

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	
}else if("InsertProcedure".equals(obj.getState())) {
	obj.InsertProcedure();
	message = "接收財產完成！！";
		
}else if("init".equals(obj.getState())) {
	obj.setEnterOrg(request.getParameter("enterOrg"));
	obj.setOwnership(request.getParameter("ownership"));
	obj.setCaseNo(request.getParameter("caseNo"));
	obj.setEnterDate(request.getParameter("enterDate"));
	
}else{
	obj.setQueryAllFlag("true");	
}

if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll_Dn();
}

%>


<%@page import="util.Datetime"%><html>
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
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>
<script language="javascript" src="../../js/TJS.js"></script>
<script language="javascript" src="../../js/jquery-ui.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>

<style>
	div#formContainer{	
		position:absolute;
		z-index: 30;
		left:0;
		top :0;
	}

	iframe#formContainerFrame{	
		position:absolute;
		z-index: -1;
		left:0;
		top :0;
	  	width: 100%;
  		height: 100%;		
	}

	.formTitle{	
		padding: 5px 5px 5px 5px;
		background-color: #0000E2;
		color: white;
		border-top: 2px solid darkblue;
		border-left: 2px solid darkblue;
		border-right: 2px solid darkblue;	
	}
	
	.formTable{ 
		border: 2px solid darkblue;
	  	background-color: #EBEBEB;
  		border-collapse: collapse;
	  	width: 100%;
  		height: 100%;
	}
	
	.formTDLable{
		background-color:#DEFFFB;
		padding: 2px 2px 2px 2px;
		height:25px;
		text-align:right;	
		text-valign:bottom;	
	}
	
	.formTDInput{
		background-color: #FFFFFF;
		padding: 2px 2px 2px 2px;
		height:25px;
		text-align:left;
		text-valign:bottom;	
	}
</style>
<script language="javascript">
var message = '<%=message%>';

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
	new Array("up_cause","03")
);

function checkField(){
	var alertStr="";

	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"){
		alertStr += checkDate(form1.up_originalMoveDate,"原始移動日期");
		alertStr += checkEmpty(form1.up_propertyNo,"物品編號");
		alertStr += checkEmpty(form1.up_propertyKind,"物品性質");
		alertStr += checkEmpty(form1.up_originalMoveDate,"原始移動日期");
		alertStr += checkEmpty(form1.up_originalKeepUnit,"原始保管單位");
		alertStr += checkEmpty(form1.up_originalKeeper,"原始保管人");
		alertStr += checkEmpty(form1.up_originalUseUnit,"原始使用單位");
		alertStr += checkEmpty(form1.up_originalUser,"原始使用人");
	
		
		var checkStr001;
		var checkStr002;
	
		//「使用年限limitYear」與「調整後年限(月)otherLimitYear」只能其中一個有資料，且不可同時都無資料。
		checkStr001 = form1.up_limitYear.value;
		checkStr002 = form1.up_otherLimitYear.value;
		if(checkStr001=='' && checkStr002==''){			form1.up_otherLimitYear.style.backgroundColor = errorbg;
														alertStr += "「使用年限」與「調整後年限(月)」不可同時都無資料\r\n";
		}else if(checkStr001!='' && checkStr002!=''){	form1.up_otherLimitYear.style.backgroundColor = errorbg;	
														alertStr += "「使用年限」與「調整後年限(月)」只能其中一個有資料\r\n";
		}else{											form1.up_otherLimitYear.style.backgroundColor = '';
														//alertStr += checkInt(form1.up_otherLimitYear,"調整後年限(月)");
		}
		
		//「單位PropertyUnit」與「其他單位otherPropertyUnit」只能其中一個有資料，且不可同時都無資料。
		checkStr001 = form1.up_propertyUnit.value;
		checkStr002 = form1.up_otherPropertyUnit.value;
		if(checkStr001=='' && checkStr002==''){			form1.up_otherPropertyUnit.style.backgroundColor = errorbg;			
														alertStr += "「單位」與「其他單位」不可同時都無資料\r\n";
		}else if(checkStr001!='' && checkStr002!=''){	form1.up_otherPropertyUnit.style.backgroundColor = errorbg;
														alertStr += "「單位」與「其他單位」只能其中一個有資料\r\n";
		}else{											form1.up_otherPropertyUnit.style.backgroundColor = '';
		}
	
		//「主要材質material」與「其他主要材質otherMaterial」只能其中一個有資料，且不可同時都無資料。
		checkStr001 = form1.up_material.value;
		checkStr002 = form1.up_otherMaterial.value;
		if(checkStr001=='' && checkStr002==''){			form1.up_otherMaterial.style.backgroundColor = errorbg;
														alertStr += "「主要材質」與「其他主要材質」不可同時都無資料\r\n";
		}else if(checkStr001!='' && checkStr002!=''){	form1.up_otherMaterial.style.backgroundColor = errorbg;
														alertStr += "「主要材質」與「其他主要材質」只能其中一個有資料\r\n";
		}else{											form1.up_otherMaterial.style.backgroundColor = '';
		}	
		
		if(alertStr.length!=0){ alert(alertStr); return false; }
		
		form1.state.value="InsertProcedure";
		viewShow('messageContainer');
		beforeSubmit();
	}	
}

function queryOne(enterOrg,ownership,propertyNo,serialNo){
	
}

function init(){
	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListPrint,spanListHidden","H");

	hideOption();
	
	if(message==''){
	}else{
		opener.form1.state.value="queryAll";
		opener.form1.submit();
		
		alert(message);
		message='';
		whatButtonFireEvent("clear");
	}
	
	
}

function execClickProgram(){
	$("input:checkbox[name='sKeySet']").each(function(){
		if($(this).attr("checked")=="checked"){
			showOption();
			whatButtonFireEvent('insert');
			form1.state.value='insert';
			$('#dataSelect').attr('disabled',false);
			return false;
		}else{
			hideOption();
			whatButtonFireEvent('clear');
			form1.state.value='queryAll';			
			$('#dataSelect').attr('disabled',true);			
		}
	});		
}

function showOption(){
//	$("input[name='up_propertyNo']").attr('class','field');
//	$("input[name='up_otherPropertyUnit']").attr('class','field');
//	$("input[name='up_otherMaterial']").attr('class','field');
//	$("input[name='up_otherLimitYear']").attr('class','field');
//	$("input[name='up_originalPlace']").attr('class','field');
//	$("input[name='up_originalMoveDate']").attr('class','field');	
}

function hideOption(){
//	$("input[name='up_propertyNo']").attr('class','field_RO');
//	$("input[name='up_otherPropertyUnit']").attr('class','field_RO');
//	$("input[name='up_otherMaterial']").attr('class','field_RO');
//	$("input[name='up_otherLimitYear']").attr('class','field_RO');
//	$("input[name='up_originalPlace']").attr('class','field_RO');
//	$("input[name='up_originalMoveDate']").attr('class','field_RO');
}

function execContainPropertyNoData(){
	var comment={};	
	comment.enterOrg = form1.enterOrg.value;	
	comment.propertyNo = form1.up_propertyNo.value;

	
	//傳送JSON
	$.post('untne053f_ajax.jsp',
		comment,
		function(data){
			//將回傳資料寫入		
			data=eval('('+data+')');
		 	
			$("input[name='up_propertyUnit']").val(data['PROPERTYUNIT']);
			$("input[name='up_material']").val(data['MATERIAL']);
			$("input[name='up_limitYear']").val(data['LIMITYEAR']);

			if($("input[name='up_propertyUnit']").val()==''){	$("input[name='up_otherPropertyUnit']").attr('class','field');
			}else{												$("input[name='up_otherPropertyUnit']").attr('class','field_RO');
																$("input[name='up_otherPropertyUnit']").attr('disabled','true');
			}
			if($("input[name='up_material']").val()==''){		$("input[name='up_otherMaterial']").attr('class','field');
			}else{												$("input[name='up_otherMaterial']").attr('class','field_RO');
																$("input[name='up_otherMaterial']").attr('disabled','true');
			}
			if($("input[name='up_limitYear']").val()==''){		$("input[name='up_otherLimitYear']").attr('class','field');
			}else{												$("input[name='up_otherLimitYear']").attr('class','field_RO');
																$("input[name='up_otherLimitYear']").attr('disabled','true');
			}
		});
}

function execPropertyKind(){
	if(form1.up_propertyKind.value=='01'){
		$("select[name='up_fundType']").attr('disabled',false);
	}else{
		form1.up_fundType.value='';
		$("select[name='up_fundType']").attr('disabled',true);
	}
}

//pop機關輔助視窗,並回傳機關代碼及名稱
function popOrgan(popOrganID,popOrganName,isLimit){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=420,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("untne053f_popOrgan.jsp?popOrganID="+popOrganID+"&popOrganName="+popOrganName+"&isLimit="+isLimit,"",prop);
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

function execCencel(){
	window.close();
}

function popProperty(popPropertyNo,popPropertyName,preWord){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("untne053f_syspk007f_s.jsp?popPropertyNo="+popPropertyNo+"&popPropertyName="+popPropertyName+"&preWord="+preWord,"",prop);
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

	execClickProgram();
}

</script>

</head>

<body topmargin="5" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位===========================================================-->
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">

<input type="hidden" name="enterOrg" value="<%=user.getOrganID()%>" >


<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTNE053F",obj);%>
	<jsp:include page="untne053q.jsp" />	
</div>


<div id="formContainer" style="width:850px;height:500px;display:none">	 	
	<table class="formTable" width="100%" height="100%">
		<tr>
			<td class="formTitle" colspan="4" style="text-align:center">接收單位資料</td>
		</tr>
		<tr>
			<td class="formTDLable" width="20%">物品編號：</td>
			<td class="formTDInput" colspan="3">
				<input class="field" type="text" name="up_propertyNo" size="12" maxlength="12" value="<%=obj.getUp_propertyNo()%>" onBlur="getProperty('up_propertyNo','up_propertyNoName','','NE');" onchange="execContainPropertyNoData();">
				<input class="field" type="button" name="btn_up_propertyNo" onclick="popProperty('up_propertyNo','up_propertyNoName','6&isLookup=Y&js=changeDate()');" value="..." title="財產編號輔助視窗">
				[<input class="field_RO" type="text" name="up_propertyNoName" size="20" maxlength="50" value="<%=obj.getUp_propertyNoName()%>">]
			</td>	
		</tr>
		<tr>
			<td class="formTDLable" width="20%">單位：</td>
			<td class="formTDInput" colspan="3">
				[<input class="field_RO" type="text" name="up_propertyUnit" size="25" maxlength="25" value="<%=obj.getUp_propertyUnit()%>" >]				
				&nbsp;　&nbsp;&nbsp;
				其他單位：
				<input class="field" type="text" name="up_otherPropertyUnit" size="25" maxlength="25" value="<%=obj.getUp_otherPropertyUnit()%>" >
			</td>	
		</tr>
		<tr>
			<td class="formTDLable" width="20%">主要材質：</td>
			<td class="formTDInput" colspan="3">
				[<input class="field_RO" type="text" name="up_material" size="25" maxlength="25" value="<%=obj.getUp_material()%>" >]				
				其他主要材質：
				<input class="field" type="text" name="up_otherMaterial" size="25" maxlength="25" value="<%=obj.getUp_otherMaterial()%>" >				
			</td>	
		</tr>
		<tr>
			<td class="formTDLable" width="20%">使用年限：</td>
			<td class="formTDInput" colspan="3">
				[<input class="field_RO" type="text" name="up_limitYear" size="3" maxlength="3" value="<%=obj.getUp_limitYear()%>" >]
				&nbsp;　　　　　　　　　　&nbsp;&nbsp;					
				調整後年限(月)：
				<input class="field" type="text" name="up_otherLimitYear" size="3" maxlength="3" value="<%=obj.getUp_otherLimitYear()%>" >			
			</td>	
		</tr>
		<tr>
			<td class="formTDLable" width="20%">增加原因：</td>
			<td class="formTDInput" colspan="3">
				<select class="field_RO" type="select" name="up_cause">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='CAB'", obj.getUp_cause())%>
				</select>
			</td>	
		</tr>
		<tr>
			<td class="formTDLable" width="20%">物品性質：</td>
			<td class="formTDInput" colspan="3">
				<select class="field" type="select" name="up_propertyKind" onchange="execPropertyKind();">
					<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getUp_propertyKind())%>
				</select>
				&nbsp;　　　　　　　　　　&nbsp;&nbsp;&nbsp;
				基金物品：
				<select class="field" type="select" name="up_fundType">
					<script>changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','up_fundType','<%=obj.getUp_fundType()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');</script>
				</select>
			</td>	
		</tr>
		<tr>
			<td class="formTDLable">移動資料：</td>
			<td class="formTDInput" colspan="3">
				保管單位
				<select class="field" type="select" name="q_keepUnit">
				　　<%=util.View.getTextOption("1;請選擇;2;單位一;3;單位二;","")%>
				</select>
				<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;保管人
				<select class="field" type="select" name="q_keeper">
				　　<%=util.View.getTextOption("1;請選擇;2;人員一;3;人員二;","")%>
				</select>
		        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper','q_userNo')" value="..." title="人員輔助視窗">
				<br>
				使用單位
				<select class="field" type="select" name="q_useUnit">
				　　<%=util.View.getTextOption("1;請選擇;2;單位一;3;單位二;","")%>
				</select>
				<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_useUnit')" value="..." title="單位輔助視窗">
				&nbsp;&nbsp;&nbsp;使用人
				<select class="field" type="select" name="q_userNo">
				　　<%=util.View.getTextOption("1;請選擇;2;人員一;3;人員二;","")%>
				</select>
		        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_keeper','q_userNo')" value="..." title="人員輔助視窗">
		        &nbsp;&nbsp;&nbsp;
				使用註記
				<input type="text" class="field" name="" value="" size="20">
		        <br>
				移動日期
		        <%=util.View.getPopCalndar("field","","")%>
		        <br>
				存置地點
				<input class="field_Q" type="text" name="" size="10" maxlength="10" value="">
				<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace('')" value="..." title="存置地點輔助視窗">
				[<input class="field_RO" type="text" name="" size="20" maxlength="20" value="">]
				<br>		
				存置地點說明
				<input class="field_Q" type="text" name="" size="30" maxlength="30" value="">
			</td>
		</tr>		
		<tr>
			<td class="formTDInput" colspan="4" style="text-align:center;">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
				<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="dataSelectEventHide();">
			</td>
		</tr>
	</table>
</div>


<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
	
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">			
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" id="dataSelect" name="dataSelect" value="接　收" onclick="dataSelectEventShow();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" id="dataCencel" name="dataCencel" value="取　消" onClick="dataSelectEventHide();execCencel();">&nbsp;|
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEADAdd">
			<tr>
				<th class="listTH"><a class="text_link_w" ><input class="field_Q" type="checkbox" id="checkboxAll" name="checkboxAll" onclick="execCheckboxAll();"></a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品分號</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品名稱</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">物品別名</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">數量</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">總價</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">單位</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">主要材質</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">使用年限</a></th>
			</thead>
			<tbody id="listTBODY">
			<%
				int counter=0;
				StringBuffer sbHEML = new StringBuffer();
				if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
					sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
				}else{
					String rowArray[]=new String[13];
					java.util.Iterator it=objList.iterator();
					String isCheck = "unchecked";			
					while(it.hasNext()){
						counter++;	
						rowArray= (String[])it.next();
						isCheck = "unchecked";
						if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
							for (int j=0; j<obj.getsKeySet().length; j++) {
								if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[5]+","+rowArray[9])) {
									isCheck = "checked";
								}
							}
						}			
						
												
						sbHEML.append(" <tr class='listTR' >\n");	
						sbHEML.append(" <td class='listTD'><input type=\'checkbox\' class=\'field_Q\' name=\'sKeySet\' value=\'"+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[9]+";"+rowArray[5]+"\' " + isCheck + " onclick=\'execClickProgram();\'>\n");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_propertyNo\' size=\'12\' maxlength=\'12\' value=\'" + rowArray[3] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_serialNo\' size=\'7\' maxlength=\'7\' value=\'" + rowArray[5] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_propertyName\' size=\'15\' maxlength=\'20\' value=\'" + rowArray[6] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_propertyName1\' size=\'15\' maxlength=\'20\' value=\'" + rowArray[7] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_adjustBookAmount\' size=\'7\' maxlength=\'7\' value=\'" + rowArray[8] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_adjustBookValue\' size=\'15\' maxlength=\'15\' value=\'" + rowArray[9] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_propertyUnit\' size=\'10\' maxlength=\'25\' value=\'" + rowArray[10] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_material\' size=\'10\' maxlength=\'25\' value=\'" + rowArray[11] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_limitYear\' size=\'3\' maxlength=\'3\' value=\'" + rowArray[12] + "\' ></td>\n ");
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
<script>

function dataSelectEventShow(){
	queryShow('formContainer');
}

function dataSelectEventHide(){
	queryHidden('formContainer');
}

function queryShow(queryName){
	var queryObj=document.all.item(queryName);		
	var objHeight= queryObj.style.height;
	var objWidth= queryObj.style.width;	
	objHeight= objHeight.substring(0,objHeight.length-2);
	objWidth= objWidth.substring(0,objWidth.length-2);
	
	queryObj.style.top=(document.body.clientHeight-Number(objHeight)-80)/2 + 200;
	queryObj.style.left=(document.body.clientWidth-Number(objWidth))/2;	
	queryObj.style.display="block";  	
}
</script>
</body>
</html>
