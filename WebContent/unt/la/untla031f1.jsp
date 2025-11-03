<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="../../home/head.jsp" %>

<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String reduceCaseNo = Common.checkGet(request.getParameter("reduceCaseNo"));
%>
<html>
<head>
<title>土地減損單明細輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript">
function selectReduce(selPropertyNo,selPropertyNoName,selPropertyName1,selSignNo1,selSignNo2,selSignNo3,selSignNo4,selSignNo5,selDoorplate,selPropertyKind,selFundType,selValuable,selTaxCredit,selOriginalBasis,selFundsSource,selUseSeparate,selUseKind,selSourceKind,selSourceDate,selSourceDoc,selOldOwner,selManageOrg){
	if (isObj(opener.document.all.item("propertyNo"))) {		
		opener.document.all.item("propertyNo").value=selPropertyNo;	
		opener.document.all.item("propertyNo").readOnly=false;
		opener.document.all.item("propertyNo").style.backgroundColor="";	
		opener.document.all.item("btn_propertyNo").disabled=false;	
	}
	if (isObj(opener.document.all.item("propertyNoName"))) {
		opener.document.all.item("propertyNoName").value=selPropertyNoName;		
	}
	if (isObj(opener.document.all.item("propertyName1"))) {
		opener.document.all.item("propertyName1").value=selPropertyName1;		
	}
	if (isObj(opener.document.all.item("signNo1"))) {
		opener.document.all.item("signNo1").value=selSignNo1;	
		opener.document.all.item("signNo1").disabled=false;
		opener.document.all.item("signNo1").style.backgroundColor="";	
			
	}
	if (isObj(opener.document.all.item("signNo2"))) {
		changeSignNo1(selSignNo1,selSignNo2);
		opener.document.all.item("signNo2").value=selSignNo2;	
		opener.document.all.item("signNo2").disabled=false;
		opener.document.all.item("signNo2").style.backgroundColor="";	
	}
	if (isObj(opener.document.all.item("signNo3"))) {
		opener.document.all.item("signNo3").value=selSignNo3;	
		opener.document.all.item("signNo3").disabled=false;
		opener.document.all.item("signNo3").style.backgroundColor="";	
		opener.document.all.item("signNo4").readOnly=false;
		opener.document.all.item("signNo4").style.backgroundColor="";	
		opener.document.all.item("signNo5").readOnly=false;
		opener.document.all.item("signNo5").style.backgroundColor="";	
	}
	if (isObj(opener.document.all.item("signNo4"))) {
		opener.document.all.item("signNo4").value=selSignNo4;		
	}
	if (isObj(opener.document.all.item("signNo5"))) {
		opener.document.all.item("signNo5").value=selSignNo5;		
	}
	if (isObj(opener.document.all.item("doorplate"))) {
		opener.document.all.item("doorplate").value=selDoorplate;		
	}
	if (isObj(opener.document.all.item("propertyKind"))) {
		opener.document.all.item("propertyKind").value=selPropertyKind;		
	}
	if (isObj(opener.document.all.item("fundType"))) {
		opener.document.all.item("fundType").value=selFundType;		
	}
	if (isObj(opener.document.all.item("valuable"))) {
		opener.document.all.item("valuable").value=selValuable;		
	}
	if (isObj(opener.document.all.item("taxCredit"))) {
		opener.document.all.item("taxCredit").value=selTaxCredit;		
	}
	if (isObj(opener.document.all.item("originalBasis"))) {
		opener.document.all.item("originalBasis").value=selOriginalBasis;		
	}
	if (isObj(opener.document.all.item("fundsSource"))) {
		opener.document.all.item("fundsSource").value=selFundsSource;		
	}
	if (isObj(opener.document.all.item("useSeparate"))) {
		opener.document.all.item("useSeparate").value=selUseSeparate;		
	}
	if (isObj(opener.document.all.item("useKind"))) {
		opener.document.all.item("useKind").value=selUseKind;		
	}
	if (isObj(opener.document.all.item("sourceKind"))) {
		opener.document.all.item("sourceKind").value=selSourceKind;		
	}
	if (isObj(opener.document.all.item("sourceDate"))) {
		opener.document.all.item("sourceDate").value=selSourceDate;		
	}
	if (isObj(opener.document.all.item("sourceDoc"))) {
		opener.document.all.item("sourceDoc").value=selSourceDoc;		
	}
	if (isObj(opener.document.all.item("oldOwner"))) {
		opener.document.all.item("oldOwner").value=selOldOwner;		
	}
	if (isObj(opener.document.all.item("manageOrg"))) {
		opener.document.all.item("manageOrg").value=selManageOrg;		
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

<table width="100%" cellspacing="0" cellpadding="0">

<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:250px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">&nbsp;</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">土地標示</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">整筆面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">權利分子</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">權利分母</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">權利面積(㎡)</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">單價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',11,false);" href="#">使用分區</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',12,false);" href="#">編定使用種類</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',13,false);" href="#">地目</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
		<%
		String sql=" select a.propertyNo, a.serialNo, " +
			" (select c.propertyName from SYSPK_PropertyCode c where c.propertyNo = a.propertyNo ) propertyNoName, " +
			" a.propertyName1, substr(a.signNo,1,1)||'000000' signNo1, substr(a.signNo,1,3)||'0000' signNo2, substr(a.signNo,1,7) signNo3, substr(a.signNo,8,4) signNo4, substr(a.signNo,12,4) signNo5, " +
			" (select d.signDesc||substr(a.signNo,8,4)||'-'||substr(a.signNo,12,4) from SYSCA_Sign d where d.signNo = substr(a.signNo,1,7) ) signNoName, " +
			" a.doorplate, a.propertyKind, a.fundType, a.valuable, a.taxCredit, a.originalBasis, a.fundsSource, " +
			" a.useSeparate, (select e.codeName from SYSCA_Code e where e.codeKindID='SEP' and e.codeID=a.useSeparate) useSeparateName, " +
			" a.useKind, (select f.codeName from SYSCA_Code f where f.codeKindID='UKD' and f.codeID=a.useKind) useKindName, " +
			" a.sourceKind, a.sourceDate, a.sourceDoc, a.oldOwner, a.manageOrg, " +
			" a.area, a.holdNume, a.holdDeno, a.holdArea, a.bookUnit, a.bookValue, " +
			" a.field, (select g.codeName from SYSCA_Code g where g.codeKindID='FIE' and g.codeID=a.field) fieldName " +
			" from UNTLA_Land a where exists ( " +
			" select * from UNTLA_ReduceDetail b where 1=1 " +
			" and a.enterOrg = b.enterOrg " +
			" and a.ownership = b.ownership " +
			" and a.propertyNo = b.propertyNo " +
			" and a.serialNo = b.serialNo " +
			" and b.enterOrg = '" + Common.get(Common.esapi(enterOrg)) + "' " + 
			" and b.ownership = '" + Common.get(Common.esapi(ownership)) + "' " + 
			" and b.caseNo = '" + Common.get(Common.esapi(reduceCaseNo)) + "' " + 
			" ) " +
			"";
		
		StringBuffer sbHEML = new StringBuffer();
		Database db = new Database();
		try{
			ResultSet rs = db.querySQL(sql);
			int counter=0;
			while (rs.next()){
				counter++;
				sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectReduce('"
								+Common.get(rs.getString("propertyNo"))+"','"
								+Common.get(rs.getString("propertyNoName"))+"','"
								+Common.get(rs.getString("propertyName1"))+"','"
								+Common.get(rs.getString("signNo1"))+"','"
								+Common.get(rs.getString("signNo2"))+"','"
								+Common.get(rs.getString("signNo3"))+"','"
								+Common.get(rs.getString("signNo4"))+"','"
								+Common.get(rs.getString("signNo5"))+"','"
								+Common.get(rs.getString("doorplate"))+"','"
								+Common.get(rs.getString("propertyKind"))+"','"
								+Common.get(rs.getString("fundType"))+"','"
								+Common.get(rs.getString("valuable"))+"','"
								+Common.get(rs.getString("taxCredit"))+"','"
								+Common.get(rs.getString("originalBasis"))+"','"
								+Common.get(rs.getString("fundsSource"))+"','"
								+Common.get(rs.getString("useSeparate"))+"','"
								+Common.get(rs.getString("useKind"))+"','"
								+Common.get(rs.getString("sourceKind"))+"','"
								+Common.get(rs.getString("sourceDate"))+"','"
								+Common.get(rs.getString("sourceDoc"))+"','"
								+Common.get(rs.getString("oldOwner"))+"','"
								+Common.get(rs.getString("manageOrg"))+
								"')\" >");
				sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("propertyNo"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("serialNo"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("propertyNoName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("signNoName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("area"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("holdNume"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("holdDeno"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("holdArea"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("bookUnit"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("bookValue"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useSeparateName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("useKindName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("fieldName"))+"</td> ");
				sbHEML.append(" </tr> ");						
//out.println("signNo2--"+rs.getString("signNo2"));
//out.println("signNo3--"+rs.getString("signNo3"));
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