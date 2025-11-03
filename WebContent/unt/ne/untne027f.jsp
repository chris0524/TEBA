
<!--
程式目的：財產增減值單-增減值資料修改(批次)
程式代號：untne027f
程式日期：1050509
程式作者：Jim.Lin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE027F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%

String[] kys = request.getParameterValues("strKeys");
Map<String, String> detailmap = new HashMap<String, String>();
if(kys!=null){
int i = 0;
for(i = 0 ; i <kys.length;i++){//需要參考到的值  放進key內  再以Map方式回傳JAVABEAN
	String[] strKeys = kys[i].split(",");
	detailmap.put(kys[i]+"_cause", request.getParameter("cause_"+strKeys[3]+"_"+strKeys[4]));
	detailmap.put(kys[i]+"_cause1", request.getParameter("cause1_"+strKeys[3]+"_"+strKeys[4]));
	detailmap.put(kys[i]+"_addbookvalue", request.getParameter("addbookvalue_"+strKeys[3]+"_"+strKeys[4]));
	detailmap.put(kys[i]+"_reducebookvalue", request.getParameter("reducebookvalue_"+strKeys[3]+"_"+strKeys[4]));
	detailmap.put(kys[i]+"_oldbookvalue", strKeys[5]);
	detailmap.put(kys[i]+"_oldbookunit", strKeys[6]);
	//detailmap.put(kys[i]+"cause1", strKeys[7]);
	
}
obj.setKys(kys);//回傳KEY
obj.setDetailmap(detailmap);//回傳MAP
}
String caseNo = Common.checkGet(request.getParameter("caseNo"));
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String differenceKind = Common.checkGet(request.getParameter("differenceKind"));

obj.setQ_caseNo(caseNo);
obj.setQ_enterOrg(enterOrg);
obj.setQ_ownership(ownership);
obj.setQ_differenceKind(differenceKind);
if("update".equals(obj.getState())){
	obj.update();
}

%>

<html>
<head>
<title>增減值資料修改</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

function checkField(){
	var alertStr = "";
	alertStr += checkItemSelect();
	if(alertStr.length!=0){
		alert(alertStr); 
		return false; 
	} else {
		form1.state.value="update";
		return true;
		//form1.submit();
	}
}

function checkItemSelect() {

	var sFlag = false;
	var alertStr = "";
	for (var i = 0; i < form1.elements.length; i++) {
	    var e = form1.elements[i];
	    if (e.name == "strKeys" && e.checked==true) {	
	    	sFlag = true;
	    }
	}
	if (sFlag==false) alertStr+="您尚未勾選任何資料！\n";
	return alertStr;
}

function init(){
	if (document.all('state').value=='insertSuccess') {
		opener.sonSumbit(form1.q_ownership.value, form1.q_differenceKind.value);
		if (isObj(window)) window.close();
	}
	if(document.all('state').value=='updateSuccess'){
		alert("修改完成");
		window.close();
	}
    setDisplayItem("spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListHidden,spanListPrint,spanQueryAll", "H");
    var dcc1 = new DataCouplingCtrlPlus(form1.organID, form1.q_keepUnitQuickly, form1.q_keepUnit, form1.q_useUnitQuickly, form1.q_useUnit, true, false);
	var dcc2 = new DataCouplingCtrlPlus(form1.organID, form1.q_keeperQuickly, form1.q_keeper, form1.q_userNoQuickly, form1.q_userNo, true, false);
}

function popSysca_Code(popID,popIDName,typeName,codekindid,condition){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2;
	var windowLeft=(document.body.clientWidth-400)/2-50;
	prop=prop+"width=550,height=450,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	
	// popSysca_Code.jsp有解碼
	typeName = encodeURI(typeName);
	returnWindow=window.open("../../home/popSysca_Code.jsp?popID="+popID+"&popIDName="+popIDName+"&typeName="+typeName+"&codekindid="+codekindid+"&condition="+condition,"",prop);
}
function changePropertyNo(propertyNo){}
</script>
</head>

<body topmargin="0" onLoad="init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<table width="100%" cellspacing="0" cellpadding="0">

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
	<input class="toolbar_default" type="submit" followPK="false" name="update" value="確　定" >&nbsp;|
</td></tr>
<!--List區============================================================-->
<tr><td>
<%=
	obj.getList()
	%>
</td></tr>
</table>
</form>
</body>
</html>
