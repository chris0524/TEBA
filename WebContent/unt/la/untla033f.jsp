<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="../../home/head.jsp" %>

<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String reduceCaseNo = Common.checkGet(request.getParameter("reduceCaseNo"));
String mergeDivisionDate = Common.checkGet(request.getParameter("mergeDivisionDate"));
%>
<html>
<head>
<title>土地減損單明細管理資料輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript">
function selectManage(selSerialNo_old,selSerialNo1_old,selUseUnit,selUseUnitName,selUseUnit1,selUseRelation,selUseDateS,selUseDateE,selUseArea,selNotes1,selNotes, selUseRelation, selCaseKind, selCaseNo, selHoldDateS, selHoldDateE, selUserfeeDateS, selUserfeeDateE, selDimArea, selDisType, selDisArea1, selDisArea2, selDisDateS, selDisDateE,selSignNo1,selSignNo2,selSignNo3,selSignNo4,selSignNo5){
	
	if (isObj(opener.document.all.item("serialNo_old"))) {		
		opener.document.all.item("serialNo_old").value=selSerialNo_old;		
	}
	if (isObj(opener.document.all.item("serialNo1_old"))) {		
		opener.document.all.item("serialNo1_old").value=selSerialNo1_old;		
	}
	
	if (isObj(opener.document.all.item("useUnit"))) {		
		opener.document.all.item("useUnit").value=selUseUnit;		
	}
	if (isObj(opener.document.all.item("useUnitName"))) {		
		opener.document.all.item("useUnitName").value=selUseUnitName;		
	}
	if (isObj(opener.document.all.item("useUnit1"))) {		
		opener.document.all.item("useUnit1").value=selUseUnit1;		
	}
	if (isObj(opener.document.all.item("useRelation"))) {		
		opener.document.all.item("useRelation").value=selUseRelation;		
	}
	if (isObj(opener.document.all.item("useDateS"))) {		
		opener.document.all.item("useDateS").value=selUseDateS;		
	}
	if (isObj(opener.document.all.item("useDateE"))) {		
		opener.document.all.item("useDateE").value=selUseDateE;		
	}
	if (isObj(opener.document.all.item("useArea"))) {		
		opener.document.all.item("useArea").value=selUseArea;		
	}
	if (isObj(opener.document.all.item("notes1"))) {		
		opener.document.all.item("notes1").value=selNotes1;		
	}
	if (isObj(opener.document.all.item("notes"))) {		
		opener.document.all.item("notes").value=selNotes;		
	}
	if (isObj(opener.document.all.item("useRelation"))) {		
		opener.document.all.item("useRelation").value=selUseRelation;		
	}
	if (isObj(opener.document.all.item("caseKind"))) {		
		opener.document.all.item("caseKind").value=selCaseKind;		
	}
	if (isObj(opener.document.all.item("caseNo"))) {		
		opener.document.all.item("caseNo").value=selCaseNo;		
	}
	if (isObj(opener.document.all.item("holdDateS"))) {		
		opener.document.all.item("holdDateS").value=selHoldDateS;		
	}
	if (isObj(opener.document.all.item("holdDateE"))) {		
		opener.document.all.item("holdDateE").value=selHoldDateE;		
	}
	if (isObj(opener.document.all.item("userfeeDateS"))) {		
		opener.document.all.item("userfeeDateS").value=selUserfeeDateS;		
	}
	if (isObj(opener.document.all.item("userfeeDateE"))) {		
		opener.document.all.item("userfeeDateE").value=selUserfeeDateE;		
	}
	if (isObj(opener.document.all.item("dimArea"))) {		
		opener.document.all.item("dimArea").value=selDimArea;		
	}
	if (isObj(opener.document.all.item("disType"))) {		
		opener.document.all.item("disType").value=selDisType;		
	}
	if (isObj(opener.document.all.item("disArea1"))) {		
		opener.document.all.item("disArea1").value=selDisArea1;		
	}
	if (isObj(opener.document.all.item("disArea2"))) {		
		opener.document.all.item("disArea2").value=selDisArea2;		
	}
	if (isObj(opener.document.all.item("disDateS"))) {		
		opener.document.all.item("disDateS").value=selDisDateS;		
	}
	if (isObj(opener.document.all.item("disDateE"))) {		
		opener.document.all.item("disDateE").value=selDisDateE;		
	}
	if (isObj(opener.document.all.item("signNo1"))) {		
		opener.document.all.item("signNo1").value=selSignNo1;		
	}
	if (isObj(opener.document.all.item("signNo2"))) {		
		opener.document.all.item("signNo2").value=selSignNo2;		
	}
	if (isObj(opener.document.all.item("signNo3"))) {		
		opener.document.all.item("signNo3").value=selSignNo3;		
	}
	if (isObj(opener.document.all.item("signNo4"))) {		
		opener.document.all.item("signNo4").value=selSignNo4;		
	}
	if (isObj(opener.document.all.item("signNo5"))) {		
		opener.document.all.item("signNo5").value=selSignNo5;		
	}
	window.close();
}

function changeSignNo1(signNo1,signNo2){
	//清除signNo2
	if (isObj(opener.document.all.item("signNo2"))) {		
		var obj2 = opener.document.all.item("signNo2");
		obj2.options.length=0;
		var obj2Option = opener.document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}
	
	//清除signNo3
	if (isObj(opener.document.all.item("signNo3"))) {		
		var obj3 = opener.document.all.item("signNo3");
		obj3.options.length=0;
		var obj3Option = opener.document.createElement("Option");	
		obj3.options.add(obj3Option);
		obj3Option.innerText = "請選擇";
		obj3Option.value = "";			
	}	
	if (signNo1!=""){
		var xmlDoc=opener.document.createElement("xml");	
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlSign.jsp?signNo1="+signNo1)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				signNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");
				signName=xmlDoc.documentElement.childNodes.item(i).getAttribute("signName");
				var oOption = opener.document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = signName;
				oOption.value = signNo;		         										
			}
		}else{
			return false;	
		}			
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlSign.jsp?signNo2="+signNo2)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				signNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("signNo");
				signName=xmlDoc.documentElement.childNodes.item(i).getAttribute("signName");
				var oOption = opener.document.createElement("Option");	
				obj3.options.add(oOption);
				oOption.innerText = signName;
				oOption.value = signNo;		      										
			}			
		}else{
			return false;	
		}
	}	
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<form id="form1" name="form1" method="post">

<input type="hidden" name="enterOrg" value="<%=enterOrg%>">
<input type="hidden" name="ownership" value="<%=ownership%>">
<input type="hidden" name="reduceCaseNo" value="<%=reduceCaseNo%>">
<input type="hidden" name="mergeDivisionDate" value="<%=mergeDivisionDate%>">

<table width="100%" cellspacing="0" cellpadding="0">

<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:250px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">使用單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">非機關使用單位</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">使用關係</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">使用期間起</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">使用期間訖</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">使用面積(㎡)</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
		<%
		String sql=" select (select c.signDesc||substr(d.signNo,8,4)||'-'||substr(d.signNo,12,4) from SYSCA_Sign c,UNTLA_Land d where d.enterOrg = a.enterOrg and d.ownership = a.ownership and d.propertyNo = a.propertyNo and d.serialNo = a.serialNo and c.signNo = substr(d.signNo,1,7) ) signNoName, " + "\n" +
			" (select d.signNo from SYSCA_Sign c,UNTLA_Land d where d.enterOrg = a.enterOrg and d.ownership = a.ownership and d.propertyNo = a.propertyNo and d.serialNo = a.serialNo and c.signNo = substr(d.signNo,1,7) ) checkSignno, " + "\n" +
			" a.serialNo1, a.useUnit, " + "\n" +
			" (select e.organSName from SYSCA_Organ e where e.organID = a.useUnit ) useUnitName, " + "\n" +
			" a.useUnit1, a.useRelation, " + "\n" +
			" (select f.codeName from SYSCA_Code f where f.codeKindID='URE' and f.codeID=a.useRelation) useRelationName, " + "\n" +
			" a.useDateS, a.useDateE, a.useArea, a.notes1, a.notes," + "\n" +
			" a.useRelation, a.caseKind, a.caseNo, a.holdDateS, a.holdDateE," + "\n" +
			" a.userfeeDateS, a.userfeeDateE, a.dimArea, a.disType, a.disArea1, a.disArea2," + "\n" +
			" a.disDateS, a.disDateE, a.serialno, a.serialno1" + "\n" +
			" from UNTLA_Manage a where a.useArea <> '0' and exists ( " + "\n" +
			" select * from UNTLA_ReduceDetail b where 1=1 " + "\n" +
			" and a.enterOrg = b.enterOrg " + "\n" +
			" and a.ownership = b.ownership " + "\n" +
			" and a.propertyNo = b.propertyNo " + "\n" +
			" and a.serialNo = b.serialNo " + "\n" +
			" and a.isdefault = 1 " + "\n" +
			" and b.enterOrg = '" + Common.get(Common.esapi(enterOrg)) + "' " + "\n" +
			" and b.ownership = '" + Common.get(Common.esapi(ownership)) + "' " + "\n" +
			" and b.caseNo = '" + Common.get(Common.esapi(reduceCaseNo)) + "' " + "\n" +
			//" and nvl(a.useDateS,'9999999') >= '" + Common.get(mergeDivisionDate) + "' " + "\n" +
			" ) " +
			"";
//System.out.println("sql: "+sql);		
		StringBuffer sbHEML = new StringBuffer();
		Database db = new Database();
		try{
			ResultSet rs = db.querySQL(sql);
			int counter=0;
			while (rs.next()){
				counter++;
				sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectManage('"
						//+Common.get(rs.getString("enterOrg"))+"','"
						//+Common.get(rs.getString("ownership"))+"','"
						//+Common.get(rs.getString("propertyNo"))+"','"
						+Common.get(rs.getString("serialNo"))+"','"
						+Common.get(rs.getString("serialNo1"))+"','"
								+Common.get(rs.getString("useUnit"))+"','"
								+Common.get(rs.getString("useUnitName"))+"','"
								+Common.get(rs.getString("useUnit1"))+"','"
								+Common.get(rs.getString("useRelation"))+"','"
								+Common.get(rs.getString("useDateS"))+"','"
								+Common.get(rs.getString("useDateE"))+"','"
								+Common.get(rs.getString("useArea"))+"','"
								+Common.get(rs.getString("notes1"))+"','"
								+Common.get(rs.getString("notes"))+"','"
								+Common.get(rs.getString("useRelation"))+"','"
								+Common.get(rs.getString("caseKind"))+"','"
								+Common.get(rs.getString("caseNo"))+"','"
								+Common.get(rs.getString("holdDateS"))+"','"
								+Common.get(rs.getString("holdDateE"))+"','"
								+Common.get(rs.getString("userfeeDateS"))+"','"
								+Common.get(rs.getString("userfeeDateE"))+"','"
								+Common.get(rs.getString("dimArea"))+"','"
								+Common.get(rs.getString("disType"))+"','"
								+Common.get(rs.getString("disArea1"))+"','"
								+Common.get(rs.getString("disArea2"))+"','"
								+Common.get(rs.getString("disDateS"))+"','"
								+Common.get(rs.getString("disDateE"))+"','"
								+Common.get(rs.getString("checkSignno").substring(0,1)+"000000")+"','"
								+Common.get(rs.getString("checkSignno").substring(0,3)+"0000")+"','"
								+Common.get(rs.getString("checkSignno").substring(0,7))+"','"
								+Common.get(rs.getString("checkSignno").substring(7,11))+"','"
								+Common.get(rs.getString("checkSignno").substring(11))+
								"')\" >");

				sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("signNoName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useUnitName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useUnit1"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useRelationName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useDateS"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useDateE"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useArea"))+"</td> ");
				sbHEML.append(" </tr> ");						
			}
			if (counter==0){
				sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料！</td></tr>");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
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
