<%@ page contentType="text/html;charset=big5" %>
<%@ include file="../../home/head.jsp" %>
<%
util.Database db = new util.Database();
java.sql.ResultSet rs = null;
String newsSubject = "";
String newsDateS = "";
String newsDateE = "";
String newsContent = "";
String organIDName = "";
String unitIDName = "";
String editIDName = "";

String itemPicture1 = "";
String itemPicture2 = "";
String itemPicture3 = "";
String itemPicture4 = "";
String itemPicture5 = "";

String sql = null; 
try{	
	sql = "select newsSubject, newsDateS, newsDateE, newsContent," +
			"(select organaname from SYSCA_ORGAN z where z.organid = a.organid) as organIDName," + 
			"(select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.organid and z.unitno = a.unitid) as unitIDName," +
			"(select empname from SYSAP_EMP z where z.organid = a.organid and z.empid = a.editID) as editIDName," +
			"itemPicture1,itemPicture2,itemPicture3,itemPicture4,itemPicture5" +
			" from SYSWM_NEWS a where showMessageYN is not null";

	rs = db.querySQL(sql);
	if(rs.next()){
		newsSubject = util.Common.checkGet(rs.getString(1));
		newsDateS = util.Common.checkGet(rs.getString(2));
		newsDateE = util.Common.checkGet(rs.getString(3));
		newsContent = util.Common.checkGet(rs.getString(4));
		organIDName = util.Common.checkGet(rs.getString(5));
		unitIDName = util.Common.checkGet(rs.getString(6));
		editIDName = util.Common.checkGet(rs.getString(7));
		itemPicture1 = util.Common.checkGet(rs.getString(8));
		itemPicture2 = util.Common.checkGet(rs.getString(9));
		itemPicture3 = util.Common.checkGet(rs.getString(10));
		itemPicture4 = util.Common.checkGet(rs.getString(11));
		itemPicture5 = util.Common.checkGet(rs.getString(12));
	}
}catch(Exception e){
	e.printStackTrace();
}finally{
	rs.close();
	rs = null;
	db.closeAll();
	db = null;
	sql = null;	
}
%>

<%@page import="util.Datetime"%><html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=big5"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript">
function init() {
	
}
</script>
</head>

<body topmargin="0" onLoad="">
<form id="form1" name="form1" method="post">


<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	
	<table class="table_form" width="100%" height="100%">
	<tr>
	  <td class="td_form"><font color="red">*</font>主旨：</td>
	  <td colspan="3" class="td_form_white"><input class="field_RO" type="text" name="newsSubject" size="55" maxlength="125" value="<%=newsSubject%>"></td>
	  </tr>
	<tr>
		<td class="td_form">上檔日期：</td>
		<td class="td_form_white"><input class="field_RO" type="text" name="newsDateS" size="7" maxlength="7" value="<%=newsDateS %>"></td>
		<td class="td_form">下檔日期：</td>
		<td class="td_form_white"><input class="field_RO" type="text" name="newsDateE" size="7" maxlength="7" value="<%=newsDateE %>"></td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>內容：</td>
		<td colspan="3" class="td_form_white">
			<textarea class="field_RO" name="newsContent" cols="100" rows="8"><%=newsContent%></textarea>		
		</td>
	</tr>
	<tr>
		<td class="td_form">機關/單位/人員：</td>
		<td colspan="3" class="td_form_white">
			<input class="field_RO" type="text" name="organIDName" size="10" value="<%=organIDName %>">
			/
			<input class="field_RO" type="text" name="unitIDName" size="10"  value="<%=unitIDName %>">
			/
			<input class="field_RO" type="text" name="editIDName" size="7" value="<%=editIDName %>">			
		</td>
	</tr>
<!-- 	問題單1781 隱藏上傳附件的功能 -->
<!-- 	<tr> -->
<!--       <td class="td_form">附件1：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture1",itemPicture1)%></td> --%>
<!-- 	  </tr> -->
<!-- 	<tr> -->
<!--       <td class="td_form">附件2：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture2",itemPicture2)%></td> --%>
<!-- 	  </tr> -->
<!-- 	<tr> -->
<!--       <td class="td_form">附件3：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture3",itemPicture3)%></td> --%>
<!-- 	  </tr> -->
<!-- 	<tr> -->
<!--       <td class="td_form">附件4：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture4",itemPicture4)%></td> --%>
<!-- 	  </tr> -->
<!-- 	<tr> -->
<!--       <td class="td_form">附件5：</td> -->
<%--       <td colspan="3" class="td_form_white"><%=util.View.getPopUpload("field","itemPicture5",itemPicture5)%></td> --%>
<!-- 	  </tr> -->
	<tr>	
	</table>
	
</td></tr>
</table>
</form>
</body>
</html>



