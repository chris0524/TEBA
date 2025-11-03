<!--
程式目的：土地合併分割重測重劃作業－增加單明細分割挑選
程式代號：untla059f
程式日期：
程式作者：Yuan-Ren
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*" %>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.la.UNTLA059F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objListReduce" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="objListAdd" scope="page" class="java.util.ArrayList"/>
<%
String enterOrg = request.getParameter("enterOrg");
String ownership = request.getParameter("ownership");
String differenceKind = request.getParameter("differenceKind");
String caseNo_Reduce = request.getParameter("caseNo_Reduce");
String caseNo_Add = request.getParameter("caseNo_Add");

obj.setEnterOrg(enterOrg);
obj.setOwnership(ownership);
obj.setDifferenceKind(differenceKind);
obj.setCaseNo_Reduce(caseNo_Reduce);
obj.setCaseNo_Add(caseNo_Add);

obj.setEditID(user.getUserID());


if ("beforeWork_Add".equals(obj.getState())) {
	obj.beforeWork_Add();
	objListAdd = obj.queryAll_Add();
}else if ("insertToUntla_land".equals(obj.getState())) {
	obj.afterWork();
	obj.setMessage("afterWorkOver");
}else if("execCencel".equals(obj.getState())) {
	obj.cencelWork();
}else if("execCloseWindow".equals(obj.getState())) {
	obj.cencelWork();
	obj.setMessage("execCloseWindow");
}
	

if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if("init".equals(obj.getState())) {
	obj.setQueryAllFlag("true");
	
}else{
	obj.setQueryAllFlag("true");
}

if ("true".equals(obj.getQueryAllFlag())){
	objListReduce = obj.queryAll_Reduce();
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
<link rel="stylesheet" href="jquery-ui.css" />  
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/jquery-ui.js"></script>
<script type="text/javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script type="text/javascript" src="untla059f.js?v=1"></script>
<script type="text/javascript">
var sourcekind = '<%=obj.getSourceKind_Add()%>';
var message = '<%=obj.getMessage()%>';
var eventFlag = '';

var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array(
);

var copyDefault;	//二維陣列, 新增時, 設定預設值
copyDefault = new Array(
);


function checkField(){
	var errorMsg = '';
	var checkFlag = false;
	
	$("select[name='after_signNo1']").each(function(){if($(this).val()==''){$(this).css('backgroundColor',errorbg);errorMsg="error";}else{$(this).css('backgroundColor','');}});
	$("select[name='after_signNo2']").each(function(){if($(this).val()==''){$(this).css('backgroundColor',errorbg);errorMsg="error";}else{$(this).css('backgroundColor','');}});
	$("select[name='after_signNo3']").each(function(){if($(this).val()==''){$(this).css('backgroundColor',errorbg);errorMsg="error";}else{$(this).css('backgroundColor','');}});
	$("input[name='after_signNo4']").each(function(){if($(this).val()==''){$(this).css('backgroundColor',errorbg);errorMsg="error";}else{$(this).css('backgroundColor','');}});
	$("input[name='after_signNo5']").each(function(){if($(this).val()==''){$(this).css('backgroundColor',errorbg);errorMsg="error";}else{$(this).css('backgroundColor','');}});

	if(sourcekind == '02' || sourcekind == '03' || sourcekind == '11'){
	}else{
		$("input[name='after_bulletinDate']").each(function(){if($(this).val()==''){$(this).css('backgroundColor',errorbg);errorMsg="error";}else{$(this).css('backgroundColor','');}});
		$("input[name='after_valueUnit']").each(function(){if($(this).val()==''){$(this).css('backgroundColor',errorbg);errorMsg="error";}else{$(this).css('backgroundColor','');}});
	}
	
	$("input[name='after_area']").each(function(){
				if(parseFloat($(this).val())<=0){
					$(this).css('backgroundColor',errorbg);
					checkFlag = true;						
				}else{
					$(this).css('backgroundColor','');
				}
			});
			if(checkFlag){
				if(errorMsg==""){		errorMsg = "errorArea";
				}else{					errorMsg += ",errorArea";
				}
			}
			checkFlag = false;
			
	$("input[name='after_holdNume']").each(function(){
				if(parseInt($(this).val(),10)<0){
					$(this).css('backgroundColor',errorbg);
					checkFlag = true;			
				}else{
					$(this).css('backgroundColor','');
				}
			});
			if(checkFlag){
				if(errorMsg==""){		errorMsg = "errorHoldNume";
				}else{					errorMsg += ",errorHoldNume";
				}
			}
			checkFlag = false;
					
	$("input[name='after_holdDeno']").each(function(){
				if(parseInt($(this).val(),10)<=0){
					$(this).css('backgroundColor',errorbg);
					checkFlag = true;			
				}else{
					$(this).css('backgroundColor','');
				}
			});
			if(checkFlag){
				if(errorMsg==""){		errorMsg = "errorHoldDeno";
				}else{					errorMsg += ",errorHoldDeno";
				}
			}
			checkFlag = false;

	if(errorMsg==''){

	}else{
		var errorMsgArray = errorMsg.split(",");
		var msgStr;
		errorMsg = '';
		for(var i=0;i<errorMsgArray.length;i++){
			if(errorMsgArray[i]=='error'){					msgStr = "請將必填欄位資料填入！！";
			}else if(errorMsgArray[i]=='errorArea'){		msgStr = "面積必須大於０！！";
			}else if(errorMsgArray[i]=='errorHoldNume'){	msgStr = "權利分子必須大於等於０！！";			
			}else if(errorMsgArray[i]=='errorHoldDeno'){	msgStr = "權利分母必須大於０！！";			
			}
	
			if(errorMsg==''){
				errorMsg = msgStr;
			}else{
				errorMsg += "\n" + msgStr;
			}
		}
		
		if(errorMsg!=''){
			alert(errorMsg);
			return false;
		}
	}
	return true;	
}


function execCheckHoldNume(colid){
	var holdNumeValue = form1.after_holdNume[colid-1].value;
	var holdDenoValue = form1.after_holdDeno[colid-1].value;
	if(parseInt(holdNumeValue,10) > parseInt(holdDenoValue,10)){
		form1.after_holdNume[colid-1].style.backgroundColor = errorbg;
		form1.after_holdNume[colid-1].value = 0;
		alert('權利範圍－分子不可大於權利範圍－分母');
		return false;
	}else{
		form1.after_holdNume[colid-1].style.backgroundColor = '';
		return true;
	}
}

function execCheckHoldDeno(colid){
	var holdNumeValue = form1.after_holdNume[colid-1].value;
	var holdDenoValue = form1.after_holdDeno[colid-1].value;
	if(parseInt(holdDenoValue,10) < parseInt(holdNumeValue,10)){
		form1.after_holdDeno[colid-1].style.backgroundColor = errorbg;
		form1.after_holdDeno[colid-1].value = 0;
		alert('權利範圍－分母不可小於權利範圍－分子');
		return false;
	}else{
		form1.after_holdDeno[colid-1].style.backgroundColor = '';
		return true;
	} 
}

function doAmountCut(returnValue){
	var resultArray = returnValue.split(",");

	form1.enterOrg.value=resultArray[0];
	form1.ownership.value=resultArray[1];
	form1.differenceKind.value=resultArray[2];	
	form1.propertyNo_Reduce.value=resultArray[3];
	form1.serialNo_Reduce.value=resultArray[4];

	viewShow('amountContainer');
}

function init(){
	if($("input[name='state']").val()=='beforeWork_Add'){
		$("input[name='after_signNo4']:not(:eq(0))").val('');
		$("input[name='after_signNo5']:not(:eq(0))").val('');
	}
	
	if(message=='afterWorkOver'){	
		alert("新增完成");	
		message='';
		$('#t1').attr('class','tab_border1');
	}else if(message=='execCloseWindow'){
		window.close();
	}

	if($("input[name='state']").val()=='beforeWork_Add'){
		$("input[name='submitNewInsert']").attr('disabled',false);
		$('#t2').attr('class','tab_border1');
				
	}else{
		$("input[name='submitNewInsert']").attr('disabled',true);
		$('#t1').attr('class','tab_border1');
	}
	
	form1.state.value="init";
}


function doAmountSubmit(){

	if($("input[name='amount']").val()==''){
		$("input[name='amount']").css('backgroundColor',errorbg); 
		alert('請輸入分割數量');
	}else{		
		if(checkNumberType('amount')==1 && parseInt($("input[name='amount']").val(),10) > 1){		
			viewHidden('amountContainer');
			
			viewShow('messageContainer');
			
			form1.state.value="beforeWork_Add";
		
			beforeSubmit();
			form1.submit();
		}else{			
			$("input[name='amount']").css('backgroundColor',errorbg);
			alert('請輸入大於１的數字')
		}
	}	
}

function checkNumberType(namestr){
	var nv=$('input[name='+namestr+']').val();
	if(nv==null){						return 0;
	}else if(/^-?\d+$/.test(nv)){
		if(/^-/.test(nv)){				return 2;
		}else{							return 1;
		}		
	}else if(/^(-?\d+)(\.\d+)?$/.test(nv)){
		if(/^-/.test(nv)){				return 4;
		}else{							return 3;
		}
	}else{								return 5;
	}	
}

function doAmountCancel(){
	$("input:radio[name='sKeySet']").each(function(){
		$(this).attr('checked',false);
	});
	
	viewHidden('amountContainer');	
}

function execCheckBulletinDateAndValueUnit(){
	var errorStr='';
	$("input[name='after_bulletinDate']").each(function(){
		if($(this).val()==''){
			$(this).css('backgroundColor',errorbg);
			errorStr='請輸入公告年月！！';
			return;
		}else{
			$(this).css('backgroundColor','');
		}
	});

	if(errorStr==''){
		
	}else{		
		alert(errorStr);
		errorStr='';
		return false;
	}
	
	
	$("input[name='after_valueUnit']").each(function(){
		if($(this).val()==''){
			$(this).css('backgroundColor',errorbg);
			errorStr='請輸入公告地價！！';
		}else{
			$(this).css('backgroundColor','');
		}
	});

	if(errorStr==''){
		
	}else{
		alert(errorStr);
		return false;
	}
	return true;
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">


<!--隱藏欄位(頁籤切換時需保留的資訊)=====================================-->
<input class="field_QRO" type="hidden" name="enterorgNotConfirm" value="<%=obj.getEnterorgNotConfirm()%>">
<input class="field_QRO" type="hidden" name="alreadyVerify" value="<%=obj.getAlreadyVerify()%>">

<input class="field_QRO" type="hidden" name="caseNo_Merge" value="<%=obj.getCaseNo_Merge()%>">
<input class="field_QRO" type="hidden" name="caseNo_Reduce" value="<%=obj.getCaseNo_Reduce()%>">
<input class="field_QRO" type="hidden" name="caseNo_Add" value="<%=obj.getCaseNo_Add()%>">

<input type="hidden" name="editID" value="<%=user.getUserID()%>">
<input type="hidden" name="editDate" value="<%=Datetime.getYYYMMDD()%>">
<input type="hidden" name="editTime" value="<%=Datetime.getHHMMSS()%>">

<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">


<input class="field_RO" type="hidden" name="enterOrg" size="7" maxlength="7" value="<%=obj.getEnterOrg()%>">
<input class="field_RO" type="hidden" name="ownership" size="7" maxlength="7" value="<%=obj.getOwnership()%>">
<input class="field_RO" type="hidden" name="differenceKind" size="7" maxlength="7" value="<%=obj.getDifferenceKind()%>">
<input class="field_RO" type="hidden" name="propertyNo_Reduce" size="7" maxlength="7" value="<%=obj.getPropertyNo_Reduce()%>">
<input class="field_RO" type="hidden" name="serialNo_Reduce" size="7" maxlength="7" value="<%=obj.getSerialNo_Reduce()%>">
<input class="field_RO" type="hidden" name="propertyNo_Add" size="7" maxlength="7" value="<%=obj.getPropertyNo_Add()%>">
<input class="field_RO" type="hidden" name="serialNo_Add" size="7" maxlength="7" value="<%=obj.getSerialNo_Add()%>">

<input type="hidden" name="bulletinDate" value="<%=obj.getBefore_holdArea()%>">
<input type="hidden" name="before_holdarea" value="<%=obj.getBefore_holdArea()%>">

<input type="hidden" name="canExecInsertNewDataFromAJAX" id="canExecInsertNewDataFromAJAX" value="Y">
<input type="hidden" name="wantToInsertNewData" id="wantToInsertNewData" value="N">


<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTLA054Q",obj);%>
	<jsp:include page="untla054q.jsp" />
</div>

<!--Form區============================================================-->
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td ID=t1 CLASS="tab_border2">挑選分割前資料</td>		
		</tr>
		<!--List區============================================================-->
		<tr><td class="bg">
		<div id="listContainerReduce">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEADReduce">
			<tr>
				<th class="listTH"></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">土地標示</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權利範圍面積</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">帳面總價</a></th>
			</thead>
			<tbody id="listTBODY">
			<%
				int counterReduce=0;
				StringBuffer sbHEMLReduce = new StringBuffer();
				if ("true".equals(obj.getQueryAllFlag()) && objListReduce.size()==0){
					sbHEMLReduce.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
				}else{
					String rowArrayReduce[]=new String[8];
					java.util.Iterator it=objListReduce.iterator();
					String tempJS="";
					String isCheck = "unchecked";			
					while(it.hasNext()){
						counterReduce++;	
						rowArrayReduce= (String[])it.next();
						isCheck = "unchecked";
						if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
							for (int j=0; j<obj.getsKeySet().length; j++) {
								if (obj.getsKeySet()[j].equals(rowArrayReduce[0]+","+rowArrayReduce[1]+","+rowArrayReduce[2]+","+rowArrayReduce[3]+","+rowArrayReduce[4])) {
									isCheck = "checked";
								}
							}
						}			
						
						sbHEMLReduce.append(" <tr class='listTR' >\n");			
						sbHEMLReduce.append(" <td class='listTD'><input type='radio' class='field_Q' name='sKeySet' value=\""+rowArrayReduce[0]+","+rowArrayReduce[1]+","+rowArrayReduce[2]+","+rowArrayReduce[3]+","+rowArrayReduce[4]+"\" onclick=\"if(this.checked){doAmountCut(this.value);}\"" + isCheck + ">\n");
						sbHEMLReduce.append(" <td class='listTD'>"+rowArrayReduce[5]+"</td>\n ");
						sbHEMLReduce.append(" <td class='listTD'>"+rowArrayReduce[6]+"</td>\n ");
						sbHEMLReduce.append(" <td class='listTD'>"+rowArrayReduce[7]+"</td>\n ");
						sbHEMLReduce.append(" </tr>\n");
					}
				}
				out.write(sbHEMLReduce.toString());	
			%>
			</tbody>
		</table>
		</div>
		</td></tr>
		
		<!--Toolbar區============================================================-->
		<tr><td class="bg" style="text-align:center">
			<div style="position:absolute;width:400px;height:100px;display:none">
				<jsp:include page="../../home/toolbar.jsp" />
			</div>
			|&nbsp;
			<input class="toolbar_default" type="button" followPK="false" name="submitNewInsert" value="確定新增" onClick="if(checkField()){checkHoldareaSum();execSubmitNewInsert();}">&nbsp;|
			<input class="toolbar_default" type="button" followPK="false" name="cencel" value="取消此筆新增" onClick="execCencel();">&nbsp;|
			<input class="toolbar_default" type="button" followPK="false" name="closeWindow" value="關閉視窗" onClick="execCloseWindow();">&nbsp;|			
		</td></tr>

		<!--標籤區============================================================-->
		<tr>
			<td ID=t2 CLASS="tab_border2">輸入分割後資料</td>		
		</tr>
		<!--List區============================================================-->
		<tr><td class="bg">
		<div id="listContainerAdd">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEADAdd">
			<tr>
				<th class="listTH"><a class="text_link_w" >NO.</a></th>
				<th class="listTH"><a class="text_link_w" href="#">縣市</a></th>
				<th class="listTH"><a class="text_link_w" href="#">鄉鎮市區</a></th>
				<th class="listTH"><a class="text_link_w" href="#">地段</a></th>
				<th class="listTH"><a class="text_link_w" href="#">母號</a></th>
				<th class="listTH"><a class="text_link_w" href="#">子號</a></th>
				<th class="listTH"><a class="text_link_w" href="#">整筆面積</a></th>
				<th class="listTH"><a class="text_link_w" href="#">權利分子</a></th>
				<th class="listTH"><a class="text_link_w" href="#">權利分母</a></th>
				<th class="listTH"><a class="text_link_w" href="#">權利面積</a></th>
				<th class="listTH"><a class="text_link_w" href="#">公告年月 </a></th>
				<th class="listTH"><a class="text_link_w" href="#">公告地價</a></th>
			</thead>
			<tbody id="listTBODY">
			<%
				int counterAdd=0;
				StringBuffer sbHEMLAdd = new StringBuffer();
				if ("true".equals(obj.getQueryAllFlag()) && objListReduce.size()==0){
					sbHEMLAdd.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
				}else{
					String rowArrayAdd[]=new String[10];
					java.util.Iterator it=objListAdd.iterator();
					String tempJS="";
					String isCheck = "unchecked";			
					while(it.hasNext()){
						counterAdd++;	
						rowArrayAdd= (String[])it.next();
						isCheck = "unchecked";
						if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
							for (int j=0; j<obj.getsKeySet().length; j++) {
								if (obj.getsKeySet()[j].equals(rowArrayAdd[0]+","+rowArrayAdd[1]+","+rowArrayAdd[2]+","+rowArrayAdd[3])) {
									isCheck = "checked";
								}
							}
						}			
						
						//這個是key值 和.java裡的rowArray變數是對照的
						tempJS = "execAJAX_inputData(\""+rowArrayAdd[0]+"\",this.name,this.value,\""+counterAdd+"\");";
						sbHEMLAdd.append(" <tr class='listTR' >\n");	
						sbHEMLAdd.append(" <td class='listTD'>"+counterAdd+".</td> ");
						sbHEMLAdd.append(" <td class='listTD'><select class='field' type='select' name='after_signNo1' onchange='changeSignNo1("+counterAdd+");'>"
											+ util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '_000000' order by seqno",rowArrayAdd[1]) +
											"</select></td>\n");
						sbHEMLAdd.append(" <td class='listTD'><select class='field' type='select' name='after_signNo2' onChange='changeSignNo2("+counterAdd+");' >"
											+ util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '"+rowArrayAdd[1].substring(0,1)+"__0000' order by seqno",rowArrayAdd[2]) +
											"</select></td>\n");
						sbHEMLAdd.append(" <td class='listTD'><select class='field' type='select' name='after_signNo3' onChange='"+tempJS+"'>"
											+ util.View.getOption("select signNo, signName from SYSCA_Sign where signNo like '" + rowArrayAdd[2].substring(0,3) + "____' order by seqno",rowArrayAdd[3]) +
											"</select></td>\n");
						sbHEMLAdd.append(" <td class='listTD'><input class='field' type='text' name='after_signNo4' size='4' maxlength='4' value=" + rowArrayAdd[4] + "  onChange='getFrontZero("+counterAdd+");"+tempJS+"'></td>\n ");
						sbHEMLAdd.append(" <td class='listTD'><input class='field' type='text' name='after_signNo5' size='4' maxlength='4' value=" + rowArrayAdd[5] + "  onChange='getFrontZero("+counterAdd+");"+tempJS+"'></td>\n ");
						sbHEMLAdd.append(" <td class='listTD'><input class='field' type='text' name='after_area' size='5' maxlength='9' value=" + rowArrayAdd[6] + " onChange=if(checkNumType(this.name,"+counterAdd+")){changeArea("+counterAdd+");"+tempJS+"} ></td>\n ");
						sbHEMLAdd.append(" <td class='listTD'><input class='field' type='text' name='after_holdNume' size='4' maxlength='4' value=" + rowArrayAdd[7] + " onChange=if(checkNumType(this.name,"+counterAdd+")){if(execCheckHoldNume("+counterAdd+")){changeArea("+counterAdd+");"+tempJS+"}}></td>\n ");
						sbHEMLAdd.append(" <td class='listTD'><input class='field' type='text' name='after_holdDeno' size='4' maxlength='4' value=" + rowArrayAdd[8] + " onChange=if(checkNumType(this.name,"+counterAdd+")){if(execCheckHoldDeno("+counterAdd+")){changeArea("+counterAdd+");"+tempJS+"}}></td>\n ");
						sbHEMLAdd.append(" <td class='listTD'><input class='field_RO' type='text' name='after_holdArea' size='5' maxlength='9' value=" + rowArrayAdd[9] + " readonly='readonly'></td>\n ");
						
						if("02".equals(obj.getSourceKind_Add()) || "03".equals(obj.getSourceKind_Add()) || "11".equals(obj.getSourceKind_Add())){
							sbHEMLAdd.append(" <td class='listTD'></td>\n ");
							sbHEMLAdd.append(" <td class='listTD'></td>\n ");

						}else{
							sbHEMLAdd.append(" <td class='listTD'><input class='field' type='text' name='after_bulletinDate' size='5' maxlength='5' onChange=if(checkNumType(this.name,"+counterAdd+")){checkBulletinDate("+counterAdd+");}>\n ");
							sbHEMLAdd.append(" <input class='field' type='button' name='btn_after_bulletinDate' onclick='popBulletinDate(\"after_bulletinDate\",\"1\",\"Y\");' value='...' title='公告年月輔助視窗'></td>\n ");
							sbHEMLAdd.append(" <td class='listTD'><input class='field' type='text' name='after_valueUnit' size='5' maxlength='15' onChange=if(checkNumType(this.name,"+counterAdd+")){if(execCheckValueUnit("+counterAdd+")){formatFloat("+counterAdd+");"+tempJS+"}}></td>\n ");
						}
						
						sbHEMLAdd.append(" </tr>\n");
					}
				}
				out.write(sbHEMLAdd.toString());	
			
			%>
			</tbody>
		</table>
		</div>
		</td></tr>
	</table>
		
		
<!--Query區============================================================-->
<div id="amountContainer" style="position:absolute;width:400px;height:100px;display:none">
	<% request.setAttribute("UNTLA059F",obj);%>
	<div class="queryTitle">欲分割數量</div>
	<table class="queryTable" border="1">
	<tr>
		<td class="queryTDLable">數量：</td>
		<td class="queryTDInput">
			<input type="text" class="field_Q" name="amount" value="<%=obj.getAmount()%>" size="10" maxlength="10">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align: center;">
		<input class="toolbar_default" followPK="false" type="button" name="amountSubmit" value="確　　定" onClick="doAmountSubmit();"> 
		<input class="toolbar_default" followPK="false" type="button" name="amountCancel" value="取　　消" onClick="doAmountCancel();"></td>
	</tr>
</table>
</div>

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



