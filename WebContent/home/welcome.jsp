<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.sql.*, util.*"%>
<%@ page import="java.net.*"%>
<jsp:useBean id="obj" scope="page" class="sys.wm.SYSWM001F"/>
<jsp:useBean id="user" scope="session" class="util.User"/>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<%
util.Database db = new util.Database();
java.sql.ResultSet rs = null;
String showMessageYN = "";
String sql;
try{
//	sql = "select showMessageYN from SYSWM_NEWS where showMessageYN is not null" +
//			" and case when newsdateS is null then '00000000' else newsdateS end <= " + util.Common.sqlChar(util.Datetime.getYYYYMMDD()) +
//			" and case when newsdateE is null then '99999999' else newsdateE end >= " + util.Common.sqlChar(util.Datetime.getYYYYMMDD());
			sql="select newsid from SYSWM_NEWS where showMessageYN is not null";
	rs = db.querySQL(sql);
	
	if(rs.next()){
		showMessageYN = Common.checkGet(rs.getString("newsid"));
	}
}catch(Exception e){
	e.printStackTrace();
}finally{
	if(rs!=null){rs.close();}
	rs = null;
	db.closeAll();
	db = null;
	sql = null;	
}
//是財管人員才執行搜尋各表單超過一個月未入帳之數量
if(user.getRoleid().equals("3")){
	obj.setEnterOrg(user.getOrganID());
	objList = obj.queryAllCountNoVerify();
}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Notice</title>
<link rel="stylesheet" href="../js/default.css" type="text/css">
<link rel="stylesheet" href="../js/style.css" type="text/css">
<style type="text/css">
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
<script type="text/javascript">
function goRent(caseNo){
	location.href="../npb/re/npbre001f.jsp?state=queryOne&queryAllFlag=true&caseNo="+caseNo+"&q_caseNo="+caseNo;
	//E01000100010001
	//location.href="../unt/la/untla002f.jsp?state=queryAll&querySelectValue=land&q_signNo3=0001&q_signNo4=0001&q_signNo5=0001";
}

function popBoard(newsID){
	window.open("../popBoard.jsp?newsID="+newsID,"","top=100,left=210,width=800,height=420,scrollbars=yes,resizable=yes");	
}

function nodeclicked(){
	//alert(eval("path_"+nodeid));
	try {
		top.fbody.mainhead.document.getElementById("pathname").innerHTML = "財產管理 > > 訊息公告 ";
		top.fbody.mainhead.document.getElementById("pathname1").innerHTML = "";
		
		
	} catch(e) {}		
}
//超過一個月未入帳詳細清單
function popNoVerifyList(tableNumber){
	var url="popNoVerifyList.jsp?tableNumber="+tableNumber;
	var prop="";
	var windowTop=(document.body.clientHeight)/2-100;
	var windowLeft=(document.body.clientWidth)/2-450;
	prop=prop+"scrollbars=1, resizable=yes, status=no, toolbar=no, menubar=no,";
	prop=prop+"width=1200,height=430,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft;
	
	window.open(url,'MyWindow',prop);
}

function popBoardRent(newsID){
	window.open("../popBoardRent.jsp?newsID="+newsID,"","top=100,left=210,width=700,height=250,resizable=yes");	
}
function init(){
	if('<%=showMessageYN%>'!=''){
		var url="../popBoard.jsp?newsID=<%=showMessageYN%>";
		var prop="";
		var windowTop=(document.body.clientHeight)/2-100;
		var windowLeft=(document.body.clientWidth)/2-400;
		prop=prop+"scrollbars=1, resizable=yes, status=no, toolbar=no, menubar=no,";
		prop=prop+"width=1200,height=430,";
		prop=prop+"top="+windowTop+",";
		prop=prop+"left="+windowLeft;
		
		window.open(url,'MyWindow',prop);
	}	
	nodeclicked();
	
	//僅供財管人員檢視
	if('<%=user.getRoleid()%>' == 3 &&  '<%=user.getGroupID()%>' == "sys01" ){
		document.getElementById("table25").style.display="block"; 
	} else {
		document.getElementById("table25").style.display="none"; 
	}
}
</script>
</head>
<body onload="init();">
<form id="form1" name="form1" method="post">
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="page.jsp" />
</td></tr>
</table>
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="table24">
      
      <tr>
        <td valign="top">
              <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><img src="../image/home_r1_c1.jpg" alt="系統公告" width="67" height="46" /></td>
                        <td width="100%" background="../image/notice1_01.gif" class="home_title">※系統公告</td>
                        <td class="home_title"><img src="../image/notice1_03.gif" width="18" height="46" /></td>
                      </tr>
                </table></td>
                </tr>
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <%
         
obj.setIsQuery("Y");
obj.setQ_newsCat("1");
obj.setPageSize(100000);
java.util.Iterator it= obj.queryAll().iterator();
String[] rowArray=new String[5];
//String check =obj.ForRent(user.getUserID()+"");

while(it.hasNext()){
	rowArray=(String[])it.next();
%>                    
                      <tr>
                        <td align="right" background="../image/notice_line_01.gif" ><img src="../image/notice_line_01.gif" width="3" height="3" /></td>
                        <td class="sLink2"><img src="../image/home_icon01.gif" alt="系統公告" width="30" height="26" /></td>
                        <td width="100%" bgcolor="#F7F7F7" class="sLink2"><a class="sLink2" href="#" onClick="popBoard('<%=Common.checkGet(rowArray[0])%>')"><%=Common.checkGet(rowArray[1]) + " " + util.Common.MaskDate(Common.checkGet(rowArray[2]))%></a></td>
                        <td background="../image/notice_line_05.gif" class="sLink2"><img src="../image/notice_line_05.gif" width="3" height="3" /></td>
                      </tr>
<%
}
%>                      
                      <tr>
                        <td colspan="4"><img src="../image/home_r3_c1.jpg" alt="系統公告" width="100%"/></td>
                    </tr>
                </table></td>
                </tr>
                  <tr>
                    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                    <td width="1%"><img src="../image/notice_buttom_01.gif" width="15" height="16" /></td>
                    <td width="100%" background="../image/notice_buttom_02.gif"><img src="../image/notice_buttom_02.gif" width="1" height="16" /></td>
                    <td><img src="../image/notice_buttom_04.gif" /></td>
                  </tr>
                  </table></td>
                </tr>
          </table>
        </td>
    </tr>
                  
                </table></td>
                </tr>
        </table>
        </td>
    </tr>

</table>

<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" id="table25" style="display:none;">     
	<tr><td class="bg">
		<div id="listContainer">
			<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
				<thead id="listTHEAD">
					<tr>
						<th class="listTH"><a class="text_link_w" ></a></th>
						<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" >財產增加</a></th>
						<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',2,false);" >財產移動</a></th>
						<th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',3,false);" >財產增減值</a></th>
						<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" >財產減損</a></th>
						<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" >土地合併分割</a></th>	
						<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" >非消耗品增加</a></th>	
						<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" >非消耗品移動</a></th>	
						<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" >非消耗品增減值</a></th>	
						<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',9,false);" >非消耗品減損</a></th>
					</tr>			
				</thead>
				<tbody id="listTBODY">
				<%
					boolean primaryArray[] = {false,true,false,false};
					boolean displayArray[] = {true,true,true,true};
					out.write(util.View.getQueryCount(objList));
				%>
				</tbody>
			</table>
		</div>
	</td></tr>                               
</table>
</body>
</html>
