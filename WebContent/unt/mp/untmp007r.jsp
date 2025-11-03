<!--
*<br>程式目的：動產財產卡報表檔 
*<br>程式代號：untmp007r
*<br>撰寫日期：94/11/28
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.mp.UNTMP007R">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%
if("".equals(util.Common.checkGet(obj.getQ_enterOrg()))){obj.setQ_enterOrg(user.getOrganID());}
if("".equals(util.Common.checkGet(obj.getQ_enterOrgName()))){obj.setQ_enterOrgName(user.getOrganName());}
obj.setQ_ownership("1");
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
<script language="javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script language="javascript">

var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	alertStr += checkDate(form1.q_enterDateS,"入帳日期-起");
	alertStr += checkDate(form1.q_enterDateE,"入帳日期-訖");
	alertStr += checkDate(form1.q_writeDateS,"填單日期-起");
	alertStr += checkDate(form1.q_writeDateE,"填單日期-訖");
	alertStr += checkEmpty(form1.q_enterOrg,"管理機關");
	alertStr += checkEmpty(form1.q_ownership,"權屬");

	var err1=checkAllValue( [form1.q_propertyNoS,form1.q_propertyNoE] ,["財產編號－起","財產編號－訖"] );
	var err2=checkAllValue( [form1.q_enterDateS,form1.q_enterDateE] ,["入帳日期－起","入帳日期－訖"] );
	var err3=checkAllValue( [form1.q_proofYear,form1.q_proofNoS,form1.q_proofNoE] ,["增加單編號","編號－起","編號－訖"] );
	
	if(err1 != "" && err2 !="" && err3 !=""){
		alertStr += "財產編號－起訖, 入帳日期－起訖, 增加單編號－起訖 \n三組須任一組都有值!";
	}		
	//alertStr += confirmData();

	if(alertStr.length!=0){ 
		alert(alertStr); 
		return false; 
	}else{
		confirmData();
    	//form1.action="untmp007p.jsp";
		//return true;
	}
}

function changeAll(){
	changeKeepUnit(form1.q_enterOrg, form1.q_keepUnit, '');
	changeKeepUnit(form1.q_enterOrg, form1.q_useUnit, '');
	getKeeper(form1.q_enterOrg, form1.q_keeper, '','N');
	getKeeper(form1.q_enterOrg, form1.q_userNo, '','N');
}

function changePropertyNo(propertyNo){}

function confirmData(){
	var q="1&enterOrg=" + form1.q_enterOrg.value+"&isOrganManager="+form1.isOrganManager.value+"&serialNoS="+form1.q_serialNoS.value+"&serialNoE="+form1.q_serialNoE.value+
		  "&ownership=" +form1.q_ownership.value+"&caseNoS=" +form1.q_caseNoS.value+"&caseNoE=" +form1.q_caseNoE.value+"&proofDoc=" +form1.q_proofDoc.value+"&proofNoS=" +form1.q_proofNoS.value+
		  "&proofNoE=" +form1.q_proofNoE.value+"&propertyNoS=" +form1.q_propertyNoS.value+"&propertyNoE=" +form1.q_propertyNoE.value+"&dataState=" +form1.q_dataState.value+
		  "&enterDateS=" +form1.q_enterDateS.value+"&enterDateE=" +form1.q_enterDateE.value+"&propertyKind=" +form1.q_propertyKind.value+"&fundType=" +form1.q_fundType.value+
		  "&differenceKind=" +form1.q_differenceKind.value+"&propertyName1=" +form1.q_propertyName1.value+"&keepUnit=" +form1.q_keepUnit.value+"&keeper=" +form1.q_keeper.value+
		  "&useUnit=" +form1.q_useUnit.value+"&userNo=" +form1.q_userNo.value+"&userNote=" +form1.q_userNote.value+"&place1=" +form1.q_place1.value+"&placeNote=" +form1.q_placeNote.value+
		  "&valuable=" +form1.q_valuable.value+"&writeDateS=" +form1.q_writeDateS.value+"&writeDateE=" +form1.q_writeDateE.value+"&workKind=" +form1.q_workKind.value;
	var ajaxuri="untmp007r_jason.jsp";
	var x=getRemoteData(ajaxuri,q);
	var sign = eval('(' + x + ')');

	if(sign['detail']==null || sign['detail']==""){
		form1.action="untmp007p.jsp";
		return true;
	} else {
		if(confirm("超過一千五百筆，是否確定要列印?"))
		{
			form1.action="untmp007p.jsp";
			return true;
			}else{
       return false;
				}
	}
}
function getRemoteData(uri,q)  {
		var x ;
	   if(window.XMLHttpRequest){
	       x = new XMLHttpRequest();
	   } else {
	      x = new ActiveXObject("Microsoft.XMLHTTP"); 
	   }
	   var url=uri;
	   x.open('Post',url,false); 
	   x.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	   x.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );
	   x.send(encodeURI('q='+q));
	   return x.responseText.trim();      
	}
	
	function init() {
		var dcc1 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, false, false);
		var dcc2 = new DataCouplingCtrlPlus(form1.q_enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, false, false);
		if ('<%=user.getRoleid()%>' == '1'){
			form1.btn_q_keeper.disabled = true;
			readbg="#EEEEEE";
			setFormItem("q_keeperQuickly","R");		
		}
		form1.objPath.value = top.fbody.mainhead.document.getElementById("pathname").innerText;
	}
</script>

</head>
<body topmargin="10" onload='init();'>
<form id="form1" name="form1" method="post" onReset="form1.q_enterOrg.value='<%=user.getOrganID()%>';changeAll();form1.q_propertyKind.value='';" onSubmit="return checkField()" target="_blank">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">	
<input type="hidden" name="tempEnterOrg" value="<%=user.getOrganID()%>">			
<input type="hidden" name="objPath" >
<table class="bg" width="102%" cellspacing="0" cellpadding="0" align="center"><tr><td>
	<table class="queryTable"  border="1">
	<tr>
        <td class="td_form" colspan="4" style="text-align:center">動產財產卡 <font color="red">(A4 直式)</font></td>
	</tr>
	<tr style="display:none">
		<td class="queryTDLable"><font color="red">*</font>單據種類：</td>
		<td class="queryTDInput" colspan="4">
			<select class="field_Q" type="select" name="q_workKind">
				<option value='a' selected>增加單</option>
				<option value='j'>增減值單</option>
				<option value='l'>減損單</option>
				<option value='n'>移動單</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable" >入帳機關：</td>
        <td class="queryTDInput">
	  	    <%=util.View.getPopOrgan("field_Q","q_enterOrg",user.getOrganID(),user.getOrganName(),"N&js=changeAll();")%>
	    </td>
		<td class="queryTDLable"><div align="right"><font color="red">*</font>權屬：</div></td>
 		<td class="queryTDInput">
 			<select class="field_Q" type="select" name="q_ownership">
 				<%=util.View.getOnwerOption(obj.getQ_ownership())%>			
			</select>
		</td>
	</tr>
	<tr>
	  <td class="queryTDLable">電腦單號：</td>
	  <td class="queryTDInput" colspan="4">
		起<input class="field_Q" type="text" name="q_caseNoS" size="10" maxlength="10" value="<%=obj.getQ_caseNoS()%>">&nbsp;~&nbsp;
		訖<input class="field_Q" type="text" name="q_caseNoE" size="10" maxlength="10" value="<%=obj.getQ_caseNoE()%>">
      </td>
	</tr>
	<tr>
		<td class="queryTDLable">增加單編號：</td>
		<td class="queryTDInput" colspan="3">		
			<input class="field_Q" type="text" name="q_proofYear" size="5" maxlength="5" value="<%=obj.getQ_proofYear()%>">年
			<input class="field_Q" type="text" name="q_proofDoc" size="15" maxlength="20" value="<%=obj.getQ_proofDoc()%>">字
			起<input class="field_Q" type="text" name="q_proofNoS" size="10" maxlength="20" value="<%=obj.getQ_proofNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_proofNoE" size="10" maxlength="20" value="<%=obj.getQ_proofNoE()%>" onChange="addChar(this ,7);">號
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產編號：</td>		
		<td class="queryTDInput" colspan="3">	
			<input name="querySelect" type="hidden" class="field_Q" value="AddProof">			
			起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"3,4,5")%>&nbsp;~&nbsp;
			訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"3,4,5")%>
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">財產分號：</td>
		<td class="queryTDInput">
			起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onchange="getFrontZero(this.name,7);">&nbsp;&nbsp;~&nbsp;
			訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onchange="getFrontZero(this.name,7);">
		</td>		
		<td class="queryTDLable"><div align="right">資料狀態：</div></td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_dataState">
				<option value=''>請選擇</option><option value='1' selected>現存</option><option value='2' >已減損</option>
			</select>
		</td>
	 	<!--審核：-->
		<input class="field_Q" type="hidden" name="q_verify" value="Y">			
	</tr>
	<tr>
		  <td class="queryTDLable">入帳日期：</td>
		  <td class="queryTDInput" colspan="3">
			  起<%=util.View.getPopCalndar("field_Q","q_enterDateS",obj.getQ_enterDateS())%>&nbsp;~&nbsp;
			  訖<%=util.View.getPopCalndar("field_Q","q_enterDateE",obj.getQ_enterDateE())%>
		  </td>
	</tr>
	<tr>
	<td class="queryTDLable">是否列印折舊紀錄 :</td>
			<td class="queryTDInput" colspan="3">
			<select class="field_Q" type="select" name="q_printDepr">
				<%=util.View.getTextOption("Y;是;N;否","Y")%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">財產性質：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_propertyKind">			
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='PKD' ",obj.getQ_propertyKind())%>
			</select>		
		</td>			
				<td class="queryTDLable">基金財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_fundType">
				<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='FUD'", obj.getQ_fundType())%>
			</select> 
        </td>
		
	</tr>
	<tr>
		<td class="queryTDLable">財產區分別 :</td>
		<td class="queryTDInput"><select class="field_Q" type="select" name="q_differenceKind">
			<%=util.View.getOption("select codeID, codeName from SYSCA_Code where codeKindID='DFK' ",obj.getQ_differenceKind())%>
		</select>      </td>
		<td class="queryTDLable">財產別名 :</td>
		<td class="td_form_white"><input class="field" type="text" name="q_propertyName1" size="40" maxlength="40" value="<%=obj.getQ_propertyName1()%>"></td>
	</tr>
	<tr>
		<td class="queryTDLable">保管單位：</td>
		<td class="queryTDInput" colspan="3">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
				                                                       "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", obj.getQ_keepUnit()) %>
			<input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.organID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;保管人：
			<%if("1".equals(user.getRoleid())){ %>
				<input type="hidden" name="q_keeper" value="<%=user.getKeeperno()%>">
				<input class="field_RO" type="text" name="q_keeperQuickly" value="<%=user.getUserName()%>" size="10">
				<input class="field_RO" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
			<%}else{ %>
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_keeper", "q_keeperQuickly", obj.getQ_keeper()) %>
			<input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.organID.value,'q_keeper')" value="..." title="人員輔助視窗">
			<%} %>			                                                       
			
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">使用單位：</td>
		<td class="queryTDInput" colspan="3">
			<%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + obj.getQ_enterOrg() + "' order by unitno ", 
			                                                       "field_Q", "form1", "q_useUnit", "q_useUnitQuickly", obj.getQ_useUnit()) %>
			<input class="field_Q" type="button" name="btn_q_useUnit" onclick="popUnitNo(form1.organID.value,'q_useUnit')" value="..." title="單位輔助視窗">
			&nbsp;&nbsp;&nbsp;使用人：
			<%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + obj.getQ_enterOrg() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
			                                                       "field_Q", "form1", "q_userNo", "q_userNoQuickly", obj.getQ_userNo()) %>
			<input class="field_Q" type="button" name="btn_q_userNo" onclick="popUnitMan(form1.organID.value,'q_userNo')" value="..." title="人員輔助視窗">
			&nbsp;&nbsp;&nbsp;
			使用註記：
			<input type="text" class="field" name="q_userNote" value="<%=obj.getQ_userNote() %>" size="20">
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">存置地點：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1()%>" onchange="queryPlaceName('q_enterOrg','q_place1');">
			<input class="field_Q" type="button" name="btn_q_place" onclick="popPlace(form1.organID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
			[<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name()%>">]		
		</td>
		<td class="queryTDLable">存置地點說明：</td>
		<td class="queryTDInput">			
				<input class="field_Q" type="text" name="q_placeNote" size="30" maxlength="30" value="<%=obj.getQ_placeNote()%>">		
		</td>
	</tr>		
	<tr>
		<td class="queryTDLable">珍貴財產：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_valuable">
			<%=util.View.getYNOption(obj.getQ_valuable())%>
			</select>		
		</td>				
		<td class="queryTDLable">填單日期：</td>
		<td class="queryTDInput">
			起<%=util.View.getPopCalndar("field_Q","q_writeDateS",obj.getQ_writeDateS())%>&nbsp;~&nbsp;
			訖<%=util.View.getPopCalndar("field_Q","q_writeDateE",obj.getQ_writeDateE())%> 
		</td>		
	</tr>	
	<tr>
		<td class="queryTDLable"><font color="red">*</font>報表類型：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" id="q_reportType" name="q_reportType">
				<%=View.getJasperReportFormatOption("PDF")%>
			</select>
		</td>
	</tr>		
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="reset" name="queryCannel" value="取　　消">
		</td>
	</tr>
	<tr>
		<td class="queryTDInput" colspan="4" style="text-align:center;">
			<font color="red">本列印作業，顯示列印一次最多100筆</font>
		</td>
	</tr>	
	</table>
</td></tr></table>	
</form>
</body>
</html>
