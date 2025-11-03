<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT006F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String popEnterOrg = Common.checkGet(Common.get(request.getParameter("enterOrg")));
String popEnterOrgName = Common.checkGet(Common.isoToUTF8(request.getParameter("enterOrgName")));
String popOwnership = Common.checkGet(Common.get(request.getParameter("ownership")));
String popPropertyNo = Common.checkGet(Common.get(request.getParameter("propertyNo")));
String popPropertyNoName = Common.checkGet(Common.isoToUTF8(request.getParameter("propertyNoName")));
String popSerialNo = Common.checkGet(Common.get(request.getParameter("serialNo")));
String jsFunction = Common.checkGet(Common.get(request.getParameter("js")));

String strJavaScript = "";
if (!"".equals(Common.get(jsFunction))) strJavaScript += "\nopener." + jsFunction + ";\n\n";

%>
<html>
<head>
<title>標的次序資料輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script type="text/javascript" src="../js/validate.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<script type="text/javascript">
function selectOrgan(selectSerialNo1,selectSignNo,selectSignName,selectSetObligor,selectArea,selectHoldNume,selectHoldDeno,selectHoldArea,selectSetArea,selectBookValue,selectOldSerialNo1){
	opener.document.all.item("serialNo1").value=selectSerialNo1;
	opener.document.all.item("signNo").value=selectSignNo;
	opener.document.all.item("signName").value=selectSignName+" "+selectSignNo.substring(7,11)+"-"+selectSignNo.substring(11);
	opener.document.all.item("setObligor").value=selectSetObligor;
	opener.document.all.item("area").value=selectArea;
	opener.document.all.item("holdNume").value=selectHoldNume;
	opener.document.all.item("holdDeno").value=selectHoldDeno;
	opener.document.all.item("holdArea").value=selectHoldArea;
	opener.document.all.item("setArea").value=selectSetArea;
	opener.document.all.item("bookValue").value=selectBookValue;
	opener.document.all.item("oldBookValue").value=selectBookValue;
	opener.document.all.item("newBookValue").value=selectBookValue;
	opener.document.all.item("oldSerialNo1").value=selectOldSerialNo1;
	<%=strJavaScript%>
	window.close();
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<form id="form1" name="form1" method="post">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:80px">
	<table class="table_form" width="100%" height="100%">	
	<tr>
		<td class="td_form" width="15%">入帳機關：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=Common.get(popEnterOrg)%>">
			[<input class="field_RO" type="text" name="enterOrgName" size="15" maxlength="50" value="<%=Common.get(popEnterOrgName)%>">]
			&nbsp;&nbsp;&nbsp;　權屬：
			<select class="field_RO" type="select" name="ownership" disabled="true">
				<%=util.View.getOnwerOption(obj.getOwnership())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="3">
			[<input class="field_RO" type="text" name="propertyNo" size="10" maxlength="10" value="<%=Common.get(popPropertyNo)%>">]
			&nbsp;&nbsp;名稱：[<input class="field_RO" type="text" name="propertyNoName" size="20" maxlength="50" value="<%=Common.get(popPropertyNoName)%>">]
			&nbsp;&nbsp;分號：[<input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
		</td>
	</tr>
	</table>
	</div>
</td></tr>
<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:250px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">No.</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">標的次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">土地標示名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">整筆面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">權利範圍分子</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">權利範圍分母</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">權利範圍面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">設定面積</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">權利價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">設定義務人</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
		<%
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, b.organSName, "+
			" a.serialNo1, a.enterDate, a.area, a.holdNume, a.holdDeno, a.holdArea, a.setArea, a.bookValue, a.signNo, a.setObligor,"+
			" d.signDesc as signName, decode(a.oldSerialNo1,'',' ',a.oldSerialNo1) as oldSerialNo1 "+
			" from UNTRT_AddDetail a, SYSCA_Organ b,  SYSPK_PropertyCode c, SYSCA_SIGN d "+
			" where 1=1 and a.enterOrg = b.organID and a.propertyNo = c.propertyNo"+
			" and substr(a.signNo,1,7)= d.signNo and a.dataState='1' and a.verify='Y' "+
			" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo, a.serialNo1)  not in ( " +
			" select e.enterOrg , e.ownership , f.propertyNo , f.serialNo, f.serialNo1 from UNTRT_AdjustProof e, UNTRT_AdjustDetail f where e.verify='N' " +
			" and e.enterOrg=f.enterOrg and e.ownership=f.ownership and e.caseNo=f.caseNo and e.propertyNo=f.propertyNo and e.serialNo=f.serialNo " +
			"  ) " +
			" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo, a.serialNo1)  not in ( " +
			" select e.enterOrg , e.ownership , f.propertyNo , f.serialNo, f.serialNo1 from UNTRT_ReduceProof e, UNTRT_ReduceDetail f where e.verify='N' " +
			" and e.enterOrg=f.enterOrg and e.ownership=f.ownership and e.caseNo=f.caseNo and e.propertyNo=f.propertyNo and e.serialNo=f.serialNo " +			
			"  ) " +
			"" ;
		if(!"".equals(Common.get(popEnterOrg))){
			sql+=" and a.enterOrg like '" + Common.get(Common.esapi(popEnterOrg)) +"%' ";
		}
		if(!"".equals(Common.get(popOwnership))){
			sql+=" and a.ownership like '" + Common.get(Common.esapi(popOwnership)) +"%' ";
		}
		if(!"".equals(Common.get(popPropertyNo))){
			sql+=" and a.propertyNo like '" + Common.get(Common.esapi(popPropertyNo)) +"%' ";
		}
		if(!"".equals(Common.get(popSerialNo))){
			sql+=" and a.serialNo like '" + Common.get(Common.esapi(popSerialNo)) +"%' ";
		}
		sql+="order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo ";
		StringBuffer sbHEML = new StringBuffer();
		Database db = new Database();
		try{
			ResultSet rs = db.querySQL(sql);
			int counter=0;
			while (rs.next()){
				counter++;
				sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" "+
								" onClick=\"selectOrgan('"+Common.checkGet(rs.getString("serialNo1"))+"','"+Common.checkGet(rs.getString("signNo"))+"','"+Common.checkGet(rs.getString("signName"))+"',"+
								" '"+Common.checkGet(rs.getString("setObligor"))+"','"+Common.checkGet(rs.getString("area"))+"','"+Common.checkGet(rs.getString("holdNume"))+"',"+
								" '"+Common.checkGet(rs.getString("holdDeno"))+"','"+Common.checkGet(rs.getString("holdArea"))+"','"+Common.checkGet(rs.getString("setArea"))+"','"+Common.checkGet(rs.getString("bookValue"))+"',"+
								" '"+Common.checkGet(rs.getString("oldSerialNo1"))+"')\" >");
				sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("serialNO1"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("signName"))+" "+Common.checkGet(rs.getString("signNo")).substring(7,11)+"-"+Common.checkGet(rs.getString("signNo")).substring(11)+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("enterDate"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("area"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("holdNume"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("holdDeno"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("holdArea"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("setArea"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("bookValue"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("setObligor"))+"</td> ");
				sbHEML.append(" </tr> ");						
			}
			if (counter==0){
				sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
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
