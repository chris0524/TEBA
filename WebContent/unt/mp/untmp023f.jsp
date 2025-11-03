<!--
程式目的：動產廢品處理作業－現有資料明細新增明細資料
程式代號：untmp023f
程式日期：0941207
程式作者：cherry
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP023F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String caseNo = request.getParameter("caseNo");
String enterOrg = request.getParameter("enterOrg");
String ownership = request.getParameter("ownership");
String differenceKind = request.getParameter("differenceKind");
obj.setQ_caseNo(caseNo);
obj.setQ_enterOrg(enterOrg);
obj.setQ_ownership(ownership);
obj.setQ_differenceKind(differenceKind);
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (unt.mp.UNTMP023F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
}

String queryType1 = "", queryType2 = "";
if ("dealDetail".equals(obj.getQuerySelect())) queryType2 = "checked=\"CHECKED\"";	
else if("dealProof".equals(obj.getQuerySelect())) queryType1 = "checked=\"CHECKED\"";

%>

<html>
<head>
<title>動產廢品處理現有資料明細新增輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/jquery-1.8.2.js"></script>

<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(enterOrg,ownership,caseNo,propertyNo,serialNo){
	form1.enterOrg.value=enterOrg;
	form1.ownership.value=ownership;
	form1.caseNo.value=caseNo;
	form1.propertyNo.value=propertyNo;
	form1.serialNo.value=serialNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function initC(){
	document.all.item("spaninsert").style.display="none";
	document.all.item("spanupdate").style.display="none";
	document.all.item("spandelete").style.display="none";
	document.all.item("spanclear").style.display="none";
	document.all.item("spanNextInsert").style.display="none";
	document.all.item("spanconfirm").style.display="none";
	
	document.all.item("spanListPrint").style.display="none";
	document.all.item("spanListHidden").style.display="none";
}

function checkInsertSelect() {
	var sFlag = false;
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "sKeySet" && e.checked==true) sFlag = true;	    
	}
	if (sFlag) {
		whatButtonFireEvent('insert');
		form1.submit();
	}else alert("您尚未勾選任何資料，若無資料可供勾選，請重新查詢！\n");
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		beforeSubmit();
		opener.form1.submit();	
		if (isObj(window.aha)) window.aha.close();
	}
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');initC();showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<!--隱藏欄位===========================================================-->
<input class="field_QRO" type="hidden" name="q_enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
<input class="field_QRO" type="hidden" name="q_ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
<input type="hidden" name="q_verify" value="Y">
<input type="hidden" name="q_valuable" value="N">
<input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
<input class="field_QRO" type="hidden" name="ownership" size="10" maxlength="10" value="<%=obj.getOwnership()%>">
<input class="field_QRO" type="hidden" name="caseNo1" size="24" maxlength="24" value="<%=obj.getCaseNo1()%>">
<input class="field_QRO" type="hidden" name="dealDate" size="24" maxlength="24" value="<%=obj.getDealDate()%>">
<input class="field_QRO" type="hidden" name="verify" size="10" maxlength="10" value="<%=obj.getVerify()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:1100px;height:250px;display:none;">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">	
		<tr>
		<!--電腦單號：-->
		<td class="queryTDInput" style="display:none">
			  起<input class="field_Q" type="hidden" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
			  訖<input class="field_Q" type="hidden" name="q_caseNoE" size="10" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
		</td>
	  <td class="queryTDLable">減損日期：</td>
	  <td class="queryTDInput" colspan="3">
			  起<%=util.View.getPopCalndar("field_Q","q_reduceDateS",obj.getQ_reduceDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_reduceDateE",obj.getQ_reduceDateE())%>
	  </td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"3,4,5")%>
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
			
		<!--審核：-->
			<input type="hidden" name="q_verify" value="Y">
		</td>		
		<td class="queryTDLable">財產區分別：</td>
		<td class="queryTDInput">
		 <%=util.View._getSelectHTML("field_Q", "q_differenceKind", obj.getQ_differenceKind(),"DFK") %>	
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput" colspan="3">
		<%=util.View._getSelectHTML_withEnterOrg("field_Q", "q_fundType", obj.getQ_fundType(),"FUD", obj.getQ_enterOrg()) %>
		</td>		
	</tr>			
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<input type="hidden" name="tempEnterOrg" value="<%=obj.getQ_enterOrg()%>">
		
			<select class="field_Q" type="select" name="q_keepUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ",obj.getQ_keepUnit())%>
			</select>	
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit','q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<select class="field_Q" type="select" name="q_keeper">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_keeper())%>
	        </select>
	        <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper','q_userNo')" value="..." title="人員輔助視窗">
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_useUnit" >
				<%=util.View.getOption("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ",obj.getQ_useUnit())%>
			</select>	
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<select class="field_Q" type="select" name="q_userNo">
				<%=util.View.getOption("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ",obj.getQ_userNo())%>
			</select>
	        <input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
	        &nbsp;&nbsp;&nbsp;
			使用註記：
			<input type="text" class="field" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput" colspan="3">
			<input class="field_Q" type="text" name="q_place" size="10" maxlength="10" value="<%=obj.getQ_place()%>" onchange="queryPlaceName('q_enterOrg','q_place');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place','q_placeName')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_placeName" size="20" maxlength="20" value="<%=obj.getQ_placeName()%>">]		
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput" colspan="3">			
			<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
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
<table width="100%" cellspacing="0" cellpadding="01">
<!--Form區============================================================-->

	
<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="filestoreLocation" value="<%=application.getInitParameter("filestoreLocation")%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
	<jsp:include page="../../home/toolbar.jsp" />
	<span id="spanBatchInsertBut">
	<input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="checkInsertSelect();">&nbsp;|
	</span>
</td></tr>

<!--List區============================================================-->
<tr><td>
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">財產別名</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">數量</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">未入帳</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',14,false);" href="#">減損單號</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	//boolean primaryArray[] = {false,false,false,true,true,true,true,true};
	//boolean displayArray[] = {true,true,true,false,false,false,false,false};
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	
		int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[13];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";	
		while(it.hasNext()){
			counter++;	
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getSKeySet()!=null && obj.getSKeySet().length>0) {
				for (int j=0; j<obj.getSKeySet().length; j++) {
					if (obj.getSKeySet()[j].equals(rowArray[7]+","+rowArray[8]+","+rowArray[9]+","+rowArray[10]+","+rowArray[11]+","+rowArray[12])) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onMouseOver=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[3]+"','"+rowArray[4]+"','"+rowArray[5]+"','"+rowArray[6]+"','"+rowArray[7]+"')\"";	
			tempJS = " onClick=\"queryOne('"+rowArray[7]+"','"+rowArray[8]+"','"+rowArray[9]+"','"+rowArray[10]+"','"+rowArray[11]+","+rowArray[12]+"')\"";
			
			String chkproofdesc = Common.checkGet(rowArray[13]);
			
			
			sbHEML.append(" <tr class='listTR' >\n");			
			sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
			
			if (!"".equals(chkproofdesc)) {
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_QRO' disabled name='dis_sKeySet' value=\"\" >\n");
			} else {
				sbHEML.append(" <td class='listTD' ><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[7]+","+rowArray[8]+","+rowArray[9]+","+rowArray[10]+","+rowArray[11]+","+rowArray[12]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
			}
			
			sbHEML.append(" <td class='listTD' >"+rowArray[0]+"</td>\n ");
			sbHEML.append(" <td class='listTD' >"+rowArray[1]+"</td>\n ");
			sbHEML.append(" <td class='listTD' >"+rowArray[2]+"</td>\n ");
			sbHEML.append(" <td class='listTD' >"+rowArray[3]+"</td>\n ");
			sbHEML.append(" <td class='listTD' >"+rowArray[4]+"</td>\n ");
			sbHEML.append(" <td class='listTD' >"+rowArray[5]+"</td>\n ");
			sbHEML.append(" <td class='listTD' >"+rowArray[6]+"</td>\n");			
			sbHEML.append(" <td class='listTD' >"+chkproofdesc+"</td>\n");
			sbHEML.append(" <td class='listTD' >"+rowArray[14]+"</td></tr>\n");
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
