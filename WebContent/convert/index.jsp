<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="util.Database"%>
<%@ include file="func.jsp"%>
<% 
Database db = new Database();
%>
<html>
<head>
<script>
function go(url) {
	ifm.location = url;
}
</script>
</head>
<body>
<table width="60%">
<tr valign="top">
<td>
	動產財產編號檔<br>
	<a href="#" onclick="go('SYSPK_PropertyCode.jsp')">SYSPK_PropertyCode.jsp</a><br>

	物品財產編號檔<br>
	<a href="#" onclick="go('SYSPK_PropertyCode2.jsp')">SYSPK_PropertyCode2.jsp</a><br>
	
	保管使用單位<br>
	UNTMP_KeepUnit.sql<br>
	
	保管使用<br>
	UNTMP_Keeper.sql<br>
	
	廠商資料<br>
	UNTMP_Store.sql<br>
</td>
<td width="33%">
	動產主檔<br>
	<%=genProgramLink(db,"PT_MABLE","UNTMP_Movable.jsp")%>

	動產增減值<br>
	<a href="#" onclick="go('UNTMP_AdjustProof.jsp')">UNTMP_AdjustProof.jsp</a><br>

	動產減損主檔<br>
	<a href="#" onclick="go('UNTMP_ReduceProof.jsp')">UNTMP_ReduceProof.jsp</a><br>

	動產減損明細檔<br>
	<a href="#" onclick="go('UNTMP_ReduceDetail.jsp?recNo_s=1&recNo_e=10000')">(1)</a>
	<a href="#" onclick="go('UNTMP_ReduceDetail.jsp?recNo_s=10001&recNo_e=20000')">(2)</a>
	<a href="#" onclick="go('UNTMP_ReduceDetail.jsp?recNo_s=20001&recNo_e=30000')">(3)</a>

</td>
<td>
	物品主檔<br>
	<%=genProgramLink(db,"PT_GOOD","UNTNE_Nonexp.jsp")%>
	
	物品增減值<br>
	<a href="#" onclick="go('UNTNE_AdjustProof.jsp')">UNTNE_AdjustProof.jsp</a><br>

	物品減損主檔<br>
	<a href="#" onclick="go('UNTNE_ReduceProof.jsp')">UNTNE_ReduceProof.jsp</a><br>

	物品減損明細檔<br>
	<a href="#" onclick="go('UNTNE_ReduceDetail.jsp')">UNTNE_ReduceDetail.jsp</a><br>	
</td>

</tr>
</table>

<iframe name="ifm" width="80%"></iframe>
</body>
</html>
<% 
	db.closeAll();
%>



