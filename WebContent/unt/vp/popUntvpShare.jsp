<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" import="java.sql.*,util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP008F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String popEnterOrg = request.getParameter("enterOrg");
String popEnterOrgName = Common.isoToUTF-8(request.getParameter("enterOrgName"));
String popOwnership = request.getParameter("ownership");
String popPropertyNo = request.getParameter("propertyNo");
String popPropertyNoName = Common.isoToUTF-8(request.getParameter("propertyNoName"));
String popSerialNo = request.getParameter("serialNo");
String jsFunction = request.getParameter("js");

String strJavaScript = "";
if (!"".equals(Common.get(jsFunction))) strJavaScript += "\nopener." + jsFunction + ";\n\n";
%>
<html>
<head>
<title>股份次序資料輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>

<script language="javascript">
function selectOrgan(selectBookAmount,selectBookUnitValue,selectBookValue,selectProofS,selectProofE,selectBookUnitValue,selectBookUnitValue,selectSerialNo1){
	opener.document.all.item("oldBookAmount").value=selectBookAmount;
	opener.document.all.item("oldBookUV").value=selectBookUnitValue;
	opener.document.all.item("oldBookValue").value=selectBookValue;
	opener.document.all.item("oldProofS").value=selectProofS;
	opener.document.all.item("oldProofE").value=selectProofE;
	opener.document.all.item("adjustBookUV").value=selectBookUnitValue;
	opener.document.all.item("newBookUV").value=selectBookUnitValue;
	opener.document.all.item("serialNo1").value=selectSerialNo1;
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
		<br>
			分號：[<input class="field_RO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">股份次序</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">入帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">總股數</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">每股單價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">總價</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">證明書編號起</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">證明書編號訖</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
		<%
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, b.organSName, "+
			" a.serialNo1, a.enterDate, a.bookAmount, a.bookUnitValue, a.bookValue, decode(a.proofS,'',' ',a.proofS) as proofS, decode(a.proofE,'',' ',a.proofE) as proofE, "+
			" decode(a.oldSerialNo1,null,' ',a.oldSerialNo1) as oldSerialNo1 "+
			" from UNTVP_Share a, SYSCA_Organ b,  SYSPK_PropertyCode c "+
			" where 1=1 and a.enterOrg = b.organID and a.propertyNo = c.propertyNo"+
			" and a.dataState='1' and a.verify='Y'" ;
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
		sql+="order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo1 ";
		System.out.println("AJAX----"+sql);
		StringBuffer sbHEML = new StringBuffer();
		Database db = new Database();
		try{
			ResultSet rs = db.querySQL(sql);
			int counter=0;
			while (rs.next()){
				
				counter++;
				sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" "+
								" onClick=\"selectOrgan('"+rs.getString("bookAmount")+"',"+
								" '"+rs.getString("bookUnitValue")+"','"+rs.getString("bookValue")+"',"+
								" '"+rs.getString("proofS")+"','"+rs.getString("proofE")+"','"+rs.getString("bookUnitValue")+"','"+rs.getString("bookUnitValue")+"','"+rs.getString("serialNo1")+"')\" >");
				sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("serialNO1")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("enterDate")+"</td> ");
				//sbHEML.append(" <td class='listTD' >"+rs.getString("bookUnitAmount")+"</td> ");
				//sbHEML.append(" <td class='listTD' >"+rs.getString("bookSheet")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("bookAmount")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("bookUnitValue")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("bookValue")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("proofS")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("proofE")+"</td> ");
				sbHEML.append(" </tr> ");						
			}
			System.out.println("AJAX sbHEMLSQL2----"+sbHEML);
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
