<!--
程式目的：土地接收作業
程式代號：untla050f
程式日期：2008/05/28
程式作者：shan
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH001F07">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
	String enterOrg = Common.checkGet(request.getParameter("enterorg"));
	String ownership = Common.checkGet(request.getParameter("ownership"));
	String caseNo = Common.checkGet(request.getParameter("caseno"));
	String differenceKind = Common.checkGet(request.getParameter("differenceKind")); 
	
	obj.setEnterOrg(enterOrg);
	obj.setOwnership(ownership);
	obj.setCaseNo(caseNo);
	obj.setDifferenceKind(differenceKind);

	obj.setEnterOrg(user.getOrganID());
	if ("queryAll".equals(obj.getState())) {
		if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
	}else if ("allInsert".equals(obj.getState())) {
		obj.allInsert();
		obj.setQueryAllFlag("true");
	}
	if ("true".equals(obj.getQueryAllFlag())){
		objList = obj.queryAll();
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css"/>
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
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

<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkEmpty(form1.q_oldEnterOrgName,"撥出機關");
		alertStr += checkEmpty(form1.q_differenceKind,"財產區分別");
		
	}else if(form1.state.value=="allInsert"){
		alertStr += checkEmpty(form1.propertyNo,"接收財產編號");
		alertStr += checkEmpty(form1.propertyKind,"財產性質");

		alertStr += checkEmpty(form1.valuable,"珍貴財產");
		alertStr += checkDate(form1.sourceDate,"取得財產日期");
		alertStr += checkEmpty(form1.sourceDate,"取得財產日期");
		alertStr += checkEmpty(form1.sourceDoc,"取得財產文號");
		
	
	}
	
		
	if(alertStr.length!=0){
		alert(alertStr); 
		return false; 
	}
}

function init(){

	if(form1.state.value == 'allInsert'){
		alert('新增完成');
		opener.form1.state.value='queryAll';
		opener.form1.submit();
		//window.close();
	}
	
	setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListPrint,spanListHidden","H");
	
	form1.propertyKind.value = '01';		
	form1.valuable.value = 'N';
}

function changeSelect() {
	//財產性質為「03:事業用」時，須控制「基金財產」必須有資料	
	//if(form1.propertyKind.value == "03") form1.fundType.disabled = false;
	//else { form1.fundType.options[0].selected=true; form1.fundType.disabled = true; }

	//財產性質為「01:公務用」時，須控制「基金財產」才可選擇
	if(form1.propertyKind.value == "01") form1.fundType.disabled = false;
	else { form1.fundType.options[0].selected=true; form1.fundType.disabled = true; }
}
</script>
</head>

<body topmargin="0" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--Query區============================================================-->
<div id="queryContainer" style="width:500px;height:200px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>	
	<% request.setAttribute("UNTCH001Q07",obj);%>
	<jsp:include page="untch001q07.jsp" />	
</div>

<!--接收區============================================================-->
<div id="formContainer" style="width:1000px;height:450px;display:none">	 	
	<table class="formTable" width="100%" height="100%">
	<tr>
		<td class="formTitle" style="text-align:center" colspan="4">
			接收機關資料
		</td>
	</tr>
	<tr>
		<td class="formTDLable" width="20%">接收機關：</td>
		<td class="formTDInput" width="32%">
			<input class="field_Q" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=user.getOrganID()%>">
			[<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=user.getOrganName()%>">]
		</td>
		<td class="formTDLable" width="20%">接收機關權屬：</td>
		<td class="formTDInput">
			[<input type="text" class="field_QRO" name="ownership" value="" size="20">]
		</td>
	</tr>
	<tr>
		<td class="formTDLable" width="20%">接收機關電腦單號：</td>
		<td class="formTDInput">
			[<input class="field_QRO" type="text" name="" size="10" maxlength="10" value="">]
		</td>
		<td class="formTDLable" width="20%">管理機關：</td>
        <td class="formTDInput">
			<%=util.View.getPopOrgan("field_Q","manageOrg",obj.getManageOrg(),obj.getManageOrgName())%>
        </td>
	</tr>
	<tr>
		<td class="formTDLable" width="20%"><font color="red">*</font>財產編號：</td>
        <td class="formTDInput" colspan="3">
			<%=util.View.getPopProperty("field_Q","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"")%> 
        </td>
    </tr>
	<tr>
        <td class="formTDLable" width="20%">增加原因：</td>
        <td class="formTDInput" colspan="3">
        	[<input type="text" class="field_QRO" name="" value="撥入" size="10">]
        </td>
	</tr>
	<tr>
		<td class="formTDLable" width="20%"><font color="red">*</font>財產性質：</td>
        <td class="formTDInput">
        	<%=util.View._getSelectHTML("field_Q", "propertyKind", obj.getPropertyKind(),"PKD") %>
        </td>
        <td class="formTDLable" width="20%">基金財產：</td>
        <td class="formTDInput">
        	<%=util.View._getSelectHTML_withEnterOrg("field_Q", "fundType", obj.getFundType(),"FUD",obj.getEnterOrg()) %>
        </td>
	</tr>
	<tr>
		<td class="formTDLable" width="20%"><font color="red">*</font>珍貴財產：</td>
        <td class="formTDInput">
        	<select class="field_Q" type="select" name="valuable" onChange="">
			<%=util.View.getYNOption(obj.getValuable())%>
            </select>
		</td>
		<td class="formTDLable" width="20%">財產區分別：</td>
        <td class="formTDInput">
        	<%=util.View._getSelectHTML("field_Q", "differenceKind", obj.getDifferenceKind(),"DFK") %>
		</td>
	</tr>
	<tr>
		<td class="formTDLable" width="20%"><font color="red">*</font>財產取得日期：</td>
        <td class="formTDInput" >
			<%=util.View.getPopCalndar("field_Q","sourceDate",obj.getSourceDate())%> 
        </td>
        <td class="formTDLable" width="20%"><font color="red">*</font>財產取得文號：</td>
        <td class="formTDInput">
			<input class="field_Q" type="text" name="sourceDoc" size="18" maxlength="20" value="<%=obj.getSourceDoc()%>">
        </td>
	</tr>
	<tr>
		<td class="formTDLable">保管使用資料：</td>
		<td class="formTDInput" colspan="3">
			保管單位：
			<select class="field_Q" type="select" name="originalKeepUnit" onchange="form1.originalUseUnit.value = this.value;queryforDeprUnit();">
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getOriginalKeepUnit())%>
			</select>
			<input class="field_Q" type="button" name="btn_originalKeepUnit" onclick="popUnitNo(form1.enterOrg.value,'originalKeepUnit','originalUseUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<select class="field" type="select" name="originalKeeper" onchange="form1.originalUser.value = this.value;">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", obj.getOriginalKeeper())%>
			</select>
	        <input class="field_Q" type="button" name="btn_originalKeeper" onclick="popUnitMan(form1.enterOrg.value,'originalKeeper','originalUser')" value="..." title="人員輔助視窗">
			<br>
			使用單位：
			<select class="field_Q" type="select" name="originalUseUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", obj.getOriginalUseUnit())%>
			</select>
			<input class="field_Q" type="button" name="btn_originalUseUnit" onclick="popUnitNo(form1.enterOrg.value,'originalUseUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<select class="field_Q" type="select" name="originalUser">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", obj.getOriginalUser())%>
			</select>
	        <input class="field_Q" type="button" name="btn_originalUser" onclick="popUnitMan(form1.enterOrg.value,'originalUser')" value="..." title="人員輔助視窗">
			&nbsp;&nbsp;&nbsp;
			使用註記：
			<input type="text" class="field_Q" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20">
			<br>
			移動日期：
			<%=util.View.getPopCalndar("field_Q","originalMoveDate",obj.getOriginalMoveDate())%>
	        <br>
			存置地點：
			<input class="field_Q" type="text" name="originalPlace1" size="10" maxlength="10" value="<%=obj.getOriginalPlace1() %>" onchange="queryPlaceName('enterOrg','originalPlace1');">
			<input class="field_Q" type="button" name="btn_originalPlace1" onclick="popPlace(form1.enterOrg.value,'originalPlace1','originalPlace1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="originalPlace1Name" size="20" maxlength="20" value="<%=obj.getOriginalPlace1Name() %>">]
			<br>		
			存置地點說明：
			<input class="field_Q" type="text" name="originalPlace" size="30" maxlength="30" value="<%=obj.getOriginalPlace() %>">
			
		</td>
	</tr>
	<tr>
		<td class="formTDLable">原始折舊資料：</td>
		<td class="formTDInput" colspan="3">
			園區別：
			<select class="field" type="select" name="originalDeprPark" onchange="form1.deprPark.value = this.value;">
				<%=util.View.getOption("select deprparkno, deprparkname from SYSCA_DEPRPARK where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprPark())%>
			</select>
			&nbsp;&nbsp;&nbsp;
			部門別：
			<select class="field" type="select" name="originalDeprUnit" onchange="queryDeprUnitData();">
				<%=util.View.getOption("select deprunitno, deprunitname from SYSCA_DEPRUNIT where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit())%>
			</select>
			<br>
			部門別單位：
			<select class="field" type="select" name="originalDeprUnit1">
				<%=util.View.getOption("select deprunit1no, deprunit1name from SYSCA_DEPRUNIT1 where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprUnit1())%>
			</select>	
			&nbsp;&nbsp;&nbsp;
			會計科目：
			<select class="field" type="select" name="originalDeprAccounts">
				<%=util.View.getOption("select depraccountsno, depraccountsname from SYSCA_DEPRACCOUNTS where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' ", obj.getOriginalDeprAccounts())%>
			</select>	
		</td>
	</tr>	
	<tr>
		<td class="formTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onclick="form1.state.value = 'allInsert';">
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="dataSelectEventHide();">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->

<!-- ================隱藏欄位==================================================================== -->
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	
<!--Toolbar區===================================================================================-->
<tr><td class="bg" style="text-align:center">			
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="button" followPK="false" id="dataSelect" name="dataSelect" value="接　收" onclick="dataSelectEventShow();">&nbsp;|
	<input class="toolbar_default" type="button" followPK="false" id="dataCencel" name="dataCencel" value="取　消" onClick="dataSelectEventHide();execCencel();">&nbsp;|
</td></tr>

<!--List區======================================================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEADAdd">
			<tr>
				<th class="listTH"><a class="text_link_w" ><input class="field_Q" type="checkbox" id="checkboxAll" name="checkboxAll" onclick="execCheckboxAll();"></a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
				<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產別名</a></th>
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
					String rowArray[]=new String[12];
					java.util.Iterator it=objList.iterator();
					String isCheck = "unchecked";			
					while(it.hasNext()){
						counter++;	
						rowArray= (String[])it.next();
						isCheck = "unchecked";
						if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
							for (int j=0; j<obj.getsKeySet().length; j++) {
								if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[5])) {
									isCheck = "checked";
								}
							}
						}			
						
												
						sbHEML.append(" <tr class='listTR' >\n");	
						sbHEML.append(" <td class='listTD'><input type=\'checkbox\' class=\'field_Q\' name=\'sKeySet\' value=\'"+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[5]+"\' " + isCheck + " onclick=\'execClickProgram();\'>\n");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_differenceKind\' size=\'12\' maxlength=\'12\' value=\'" + rowArray[2] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_propertyNo\' size=\'12\' maxlength=\'12\' value=\'" + rowArray[3] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_serialNo\' size=\'7\' maxlength=\'7\' value=\'" + rowArray[4] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_propertyName\' size=\'15\' maxlength=\'20\' value=\'" + rowArray[6] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_propertyName1\' size=\'15\' maxlength=\'20\' value=\'" + rowArray[7] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_bookAmount\' size=\'7\' maxlength=\'7\' value=\'" + rowArray[8] + "\' ></td>\n ");
						sbHEML.append(" <td class='listTD'><input class=\'field_QRO\' type=\'text\' name=\'dn_bookValue\' size=\'7\' maxlength=\'7\' value=\'" + rowArray[9] + "\' ></td>\n ");
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
<script>

function dataSelectEventShow(){
	queryShow('formContainer');
}

function dataSelectEventHide(){
	queryHidden('formContainer');
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
</form>
</body>
</html>



