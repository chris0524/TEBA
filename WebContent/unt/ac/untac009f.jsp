<!--
程式目的：
程式代號：
程式日期：
程式作者：
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ac.UNTAC009F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("Closing".equals(obj.getState())) {
	obj.Closing(user);
	objList = obj.queryAll();
} else {
obj.setOrganID(user.getOrganID());
obj.setUserID(user.getUserID());
obj.setUserName(user.getUserName());
objList = obj.queryAll();
}
// 讀取資料庫，取得最後月結年月與結帳日期
java.util.List<String[]> closingList = new java.util.ArrayList<>();
util.Database db = new util.Database();
java.sql.Connection conn = null;
java.sql.Statement stmt = null;
java.sql.ResultSet rs = null;
try {
    String sql = "select (select organsname from SYSCA_ORGAN o where　enterorg=o.organid ) as enterorg,closing1ym,closing1date from UNTAC_CLOSINGNE where enterorg  like 'A1%' and differencekind ='120' ";
    conn = db.getConnection();
    stmt = conn.createStatement();
    rs = stmt.executeQuery(sql);
    while (rs.next()) {
        closingList.add(new String[]{
            rs.getString("enterorg"),
            rs.getString("closing1ym"),
            rs.getString("closing1date")
        });
    }
} catch (Exception e) {
    e.printStackTrace();
} finally {
    try { if (rs != null) rs.close(); } catch(Exception e) {}
    try { if (stmt != null) stmt.close(); } catch(Exception e) {}
    try { if (conn != null) conn.close(); } catch(Exception e) {}
    
}

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
<script language="javascript" src="../../js/changeEnterOrg_FundType.js"></script>
<script language="javascript" src="../../js/changeEnterOrg_KeepUnit.js"></script>
<script language="javascript" src="../../js/sList2.js"></script>
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值

insertDefault = new Array(
	
);

function init(){

}
function checkField(){
	var alertStr="";
	alertStr += checkInsertSelect();
	if(alertStr.length!=0){ 
		alert(alertStr);
		return false; 
	}
	if (confirm('確定執行月結作業？')) {
		document.all.item("querySubmit").disabled = true;
		beforeSubmit();
	} else {
		return false;
	}	
}
function checkInsertSelect() {
	var sFlag = false;
	for (var i = 0; i < form1.elements.length; i++) {
		var e = form1.elements[i];
		if (e.name == "sKeySet" && e.checked==true) sFlag = true;	    
	}
	if (sFlag) return "";
	else return "您尚未勾選任何資料，若無資料可供勾選，請重新查詢！\n";
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="userName" value="<%=user.getUserName()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	
</td></tr></table>

<!--頁籤區============================================================-->

<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="bg">
	<div>
	<table class="table_form" width="100%" height="50%">
		<tr>
			<td class="queryTDInput" style="text-align:center;">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="執行月結" onClick="whatButtonFireEvent('Closing');">
			</td>			
		</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center;display:none;">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>

<!--List區============================================================-->
<tr><td class="bg">
<div>
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH"><input type="checkbox" name="toggleAll" class="field_Q" onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>
		<th class="listTH"><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">財產區分別</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" href="#">最後月結年月</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" href="#">結帳者</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',4,false);" href="#">結帳日期</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',5,false);" href="#">回復者</a></th>
		<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',6,false);" href="#">回復日期</a></th>
	</thead>
	<tbody id="listTBODY">

	<%
	
		int counter=0;
		StringBuffer sbHEML = new StringBuffer();
		if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
			sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
		}else{
			String rowArray[]=new String[10];
			java.util.Iterator it=objList.iterator();
			String tempJS="";
			String isCheck = "unchecked";	
			while(it.hasNext()){
				counter++;	
				rowArray= (String[])it.next();
				isCheck = "unchecked";
				//這個是key值 和.java裡的rowArray變數是對照的
				//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"','"+rowArray[4]+"')\"";
				//tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"',"+rowArray[2]+",'"+rowArray[3]+"','"+rowArray[4]+"','"+rowArray[10]+"')\"";
				sbHEML.append(" <tr class='listTR'>\n");
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+Common.checkGet(rowArray[0])+","+Common.checkGet(rowArray[1])+","+Common.checkGet(rowArray[3])+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
				sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[2])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[3])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[5])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[6])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[8])+"</td>\n ");
				sbHEML.append(" <td class='listTD'>"+Common.checkGet(rowArray[9])+"</td></tr>\n ");
			}
		}
		out.write(sbHEML.toString());		
	
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>

<div style="margin-top:200px; text-align:center;">
    <table class="table_form" width="50%" cellspacing="0" cellpadding="0" style="margin:0 auto;">
        <thead id="listTHEAD">
            <tr>
                <th class="listTH">單位</th>
                <th class="listTH">最後月結年月</th>
                <th class="listTH">結帳日期</th>
            </tr>
        </thead>
        <tbody id="listTBODY">
            <% for (String[] row : closingList) {
                // 轉換民國年
                String ym = row[1];
                String date = row[2];
                int adYm = Integer.parseInt(ym.substring(0,4));
                String rocYm = (adYm - 1911) + ym.substring(4);
                int adDate = Integer.parseInt(date.substring(0,4));
                String rocDate = (adDate - 1911) + date.substring(4);
            %>
            <tr class="listTR">
                <td class="listTD"><%= row[0] %></td>
                <td class="listTD"><%= rocYm %></td>
                <td class="listTD"><%= rocDate %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

</form>
</body>
</html>



