<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*, util.*" %>
<!-- jsp:include page="../../home/head.jsp" /-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Notice</title>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<link rel="stylesheet" href="../../js/style.css" type="text/css">
<style>
body {
	margin-top: 0px;
	margin-left: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
  
.showTD {
	padding: 2px 5px 2px 2px;
	border-left: 1px solid silver;
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
	text-align: left;
	height:24px;
}

.sLink2:link {  font-family: "細明體", "新細明體";  color: #C90026; text-decoration: none }
.sLink2:visited {  font-family: "細明體", "新細明體"; color: #C90026; text-decoration: none }
.sLink2:active {  font-family: "細明體", "新細明體";  color: #000099}
.sLink2:hover {  font-family: "細明體", "新細明體";  text-decoration: none; color: #FF7E00; }
</style>
<script language="javascript">
</script>
</head>
<body >
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="table24">
	<tr><td valign="top">
		<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="../../image/home4_r1_c1.jpg" alt="" width="67" height="46" /></td>
						<td width="100%" background="../../image/notice3_01.gif" class="home_title">※土地改良物產籍線上操作說明</td>
						<td class="home_title"><img src="../../image/notice3_03.gif" width="18" height="46" /></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
<!-- 以下加入連結點  -->
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/sys/ap/sysap001t.pdf" )">系統畫面功能說明</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/sys/ap/sysap002t.pdf" )">共通性操作說明</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/unt/rf/untrf001t.pdf" )">作業說明</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/unt/rf/untrf002t.pdf" )">作業流程圖</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/unt/rf/untrf003t.pdf" )">增加操作說明</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/unt/rf/untrf004t.pdf" )">增減值操作說明</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/unt/rf/untrf005t.pdf" )">減少操作說明</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
					<tr>
						<td align="right" background="../../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
						<td class="sLink2"><img src="../../image/home_icon01.gif" alt="下載清單" width="30" height="26" /></td>
						<td width="100%" bgcolor="#F7F7F7" class="sLink2">
							<a class="sLink2" href="../../paper/unt/rf/untrf006t.pdf" )">報表操作說明</a>
						</td>
						<td background="../../image/notice_line_05.gif" class="sLink2">
							<img src="../../image/notice_line_05.gif" width="3" height="3" />
						</td>
					</tr>
<!-- 以上加入連結點  -->
				  	<tr>
						<td colspan="4"><img src="../../image/home_r3_c1.jpg" alt="" width="100%"/></td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td><table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="1%"><img src="../../image/notice_buttom_01.gif" width="15" height="16" /></td>
						<td width="100%" background="../../image/notice_buttom_02.gif"><img src="../../image/notice_buttom_02.gif" width="1" height="16" /></td>
						<td><img src="../../image/notice_buttom_04.gif" /></td>
					</tr>
				</table></td>
			</tr>
		</table>
	</td></tr>
</table>
</body>
</html>
