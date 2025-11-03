<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="../../home/head.jsp"%>
<%
String enterOrg = request.getParameter("enterOrg");
String ownership = request.getParameter("ownership");
%>
<html>
<head>
<title>輸入輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-1" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-control" content="no-cache" />
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript" src="../../js/tablesoft.js"></script>
<script language="javascript">
function selectCase(propertyNo, serialNo, propertyName, oldPropertyNo, oldSerialNo){
	if ( isObj(opener.document.all.item("propertyNo"))) {
		opener.document.all.item("propertyNo").value=propertyNo;
	}
	if ( isObj(opener.document.all.item("propertyName"))) {
		opener.document.all.item("propertyName").value=propertyName;
	}
	if ( isObj(opener.document.all.item("propertyNoName"))) {
		opener.document.all.item("propertyNoName").value=propertyName;
	}	
	if ( isObj(opener.document.all.item("serialNo"))) {
		opener.document.all.item("serialNo").value=serialNo;
	}		
	if ( isObj(opener.document.all.item("oldPropertyNo"))) {
		opener.document.all.item("oldPropertyNo").value=oldPropertyNo;
	}		
	if ( isObj(opener.document.all.item("oldSerialNo"))) {
		opener.document.all.item("oldSerialNo").value=oldSerialNo;
	}				
	window.close();
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<table width="100%" cellspacing="0" cellpadding="0">
	<!--List區============================================================-->
	<tr>
		<td class="bg">
		<div id="listContainer" style="height:440px">
		<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
			<thead id="listTHEAD">
				<tr>
					<th class="listTH"><a class="text_link_w">NO.</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產名稱</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產分號</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">入帳日期</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">證明書字號</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">權利範圍</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">原始設定價值</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">帳面設定價值</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" href="#">存續期間</a></th>
					<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',10,false);" href="#">地租</a></th>					
				</tr>
			</thead>
			<tbody id="listTBODY">
				<%
				String sql = "";				
	if (!"".equals(Common.get(enterOrg)) && !"".equals(Common.get(ownership))) {			
		sql=" select a.propertyNo, b.propertyName, a.serialNo, a.enterDate," +
			" a.proofDoc1, a.holdNume1||'/'||a.holdDeno2 as holdNumeDeno, a.originalBV, a.bookValue, " +
			" NVL(a.setPeriod,' ') as setPeriod, a.rent, a.oldPropertyNo, a.oldSerialNo "+
			" from UNTRT_AddProof a, SYSPK_PropertyCode b where 1=1 " +
			" and b.propertyNo=a.propertyNo " +
			" and a.enterOrg =" + Common.sqlChar(Common.esapi(Common.get(Common.esapi(enterOrg)))) + 
			" and a.ownership = " + Common.sqlChar(Common.esapi(Common.get(Common.esapi(ownership)))) +
			"";
		StringBuffer sbHEML = new StringBuffer();
		String strLink = "";
		Database db = new Database();
		try{
			ResultSet rs = db.querySQL(sql);
			int counter=0;
			while (rs.next()){				
				counter++;
				strLink = Common.sqlChar(rs.getString("propertyNo")) + "," + Common.sqlChar(rs.getString("serialNo")) + "," +
					Common.sqlChar(rs.getString("propertyName")) + "," +
					Common.sqlChar(rs.getString("oldPropertyNo")) + "," +
					Common.sqlChar(rs.getString("oldSerialNo")) +										
					"";
				sbHEML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"selectCase(" + strLink + ");\" >");
				sbHEML.append(" <td class='listTD' >"+counter+".</td> ");				
				sbHEML.append(" <td class='listTD' >"+rs.getString("propertyNo")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("propertyName")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("serialNo")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("enterDate")+"</td> ");												
				sbHEML.append(" <td class='listTD' >"+rs.getString("proofDoc1")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("holdNumeDeno")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("originalBV")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("bookValue")+"</td> ");				
				sbHEML.append(" <td class='listTD' >"+rs.getString("setPeriod")+"</td> ");				
				sbHEML.append(" <td class='listTD' >"+rs.getString("rent")+"</td> ");								
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
	}
		%>
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>
</body>
</html>
