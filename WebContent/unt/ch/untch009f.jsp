<!--
程式目的：批次修改存置地點
程式代號：untch009f
程式日期：1060522
程式作者：Jim.Lin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ch.UNTCH009F">
    <jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String keeperno = Common.checkGet(request.getParameter("keeperno"));

if ("queryAll".equals(obj.getState()) || "queryAllError".equals(obj.getState())) {
    if ("false".equals(obj.getQueryAllFlag())){
    	obj.setQueryAllFlag("true"); 
    }
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
    obj.update();
}

if ("true".equals(obj.getQueryAllFlag())){
    objList = obj.queryAll();
}
%>

<html>
<head>
<title>批次修改存置地點</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript">
var insertDefault;  //二維陣列, 新增時, 設定預設值

function checkField(){
    var alertStr="";
    if (form1.state.value=="update") {
        alertStr += checkEmpty(form1.place1,"存置地點");
    }
    if(alertStr.length!=0){ 
        alert(alertStr); 
        return false; 
    }
    beforeSubmit();
}

function checkUpadteSelect() {
    var sFlag = false;
    for (var i = 0; i < form1.elements.length; i++) {
        var e = form1.elements[i];
        if (e.name == "sKeySet" && e.checked==true) {
            sFlag = true;
        }
    }
    if (!sFlag){
        alert("您尚未勾選任何資料！\n");
        return ;
    }else {
        queryShow('batchArea');
    }
}

function init(){
    setDisplayItem("spanQueryAll,spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,spanListHidden,spanListPrint", "H"); 
    if (document.all('state').value=='updateSuccess') {
        if (isObj(window)) {
            opener.window.location.reload();
            window.close();
        }
    }
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');init();">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--批次設定區============================================================-->
<div id="batchArea" style="position:absolute;z-index: 25;left:0;top :0;width:450px;height:150px;display:none">
    <iframe id="batchAreaFrame"  style="position:absolute;z-index: -1;left:0;top :0;width:100%;height:100%;"></iframe>    
    <div class="queryTitle">批次設定視窗</div>
    <table class="queryTable"  border="1">  
    <tr>
        <td class="queryTDLable">存置地點：</td>
        <td class="queryTDInput" colspan="3">
	        <input class="field_Q" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>" onchange="queryPlaceName('enterOrg','place1');">
	        <input class="field_Q" type="button" name="btn_place1" onclick="popPlace(form1.enterOrg.value,'place1','place1Name')" value="..." title="存置地點輔助視窗">
	        [<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20">]
        </td> 
    </tr>      
    <tr>
        <td class="queryTDLable">存置地點說明：</td>
        <td class="queryTDInput">
            <input class="field_Q" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace() %>">
        </td>       
    </tr>
    <tr>
        <td class="queryTDInput" colspan="4" style="text-align:center;">
            <input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" onClick="checkField();whatButtonFireEvent('update');">
            <input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="queryHidden('batchArea');">
        </td>
    </tr>
    </table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
    <input type="hidden" name="state" value="<%=obj.getState()%>">
    <input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
    <input type="hidden" name="userID" value="<%=user.getUserID()%>">
    <input type="hidden" name="organID" value="<%=user.getOrganID()%>">
    <input type="hidden" name="enterOrg" value="<%=user.getOrganID()%>">
    <input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
    <jsp:include page="../../home/toolbar.jsp" />
    <input class="toolbar_default" type="button" followPK="false" name="batchInsertBut" value="確　定" onClick="checkUpadteSelect();">&nbsp;|
</td></tr>
<tr><td>
</td></tr>
<!--List區============================================================-->
<tr><td>
<div>
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
    <thead id="listTHEAD">
    <tr>
        <th class="listTH" ><a class="text_link_w" >NO.</a></th>
        <th class="listTH"><input type=checkbox name=toggleAll class='field_Q' onclick="ToggleAll(this, document.form1, 'sKeySet');" value="Y" <%=obj.getToggleAll()%>></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">財產編號</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">財產分號</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">保管單位</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">保管人</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">使用單位</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">使用人</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">存置地點</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">存置地點說明</a></th>
    </thead>
    <tbody id="listTBODY">  
    <%
    int counter=0;
    StringBuffer sbHEML = new StringBuffer();
    if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
        sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
    }else{
        String rowArray[]=new String[14];
        java.util.Iterator it=objList.iterator();
        String tempJS="";
        String isCheck = "unchecked";           
        while(it.hasNext()){
            counter++;  
            rowArray= (String[])it.next();
            isCheck = "unchecked";
            if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
                for (int j=0; j<obj.getsKeySet().length; j++) {
                    if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+","+rowArray[6])) {
                        isCheck = "checked";
                    }
                }
            }           
            //這個是key值 和.java裡的rowArray變數是對照的
            //tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";            
            tempJS = " onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[4]+"','"+rowArray[5]+"')\"";
            sbHEML.append(" <tr class='listTR'>\n");            
            sbHEML.append(" <td class='listTD'>"+counter+".</td> ");
            
            sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[4]+","+rowArray[5]+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');\"" + isCheck + ">\n");
            sbHEML.append(" <td class='listTD'>"+rowArray[4]+"</td>\n ");
            sbHEML.append(" <td class='listTD'>"+rowArray[5]+"</td>\n ");
            sbHEML.append(" <td class='listTD'>"+rowArray[8]+"</td>\n ");
            sbHEML.append(" <td class='listTD'>"+rowArray[9]+"</td>\n ");
            sbHEML.append(" <td class='listTD'>"+rowArray[10]+"</td>\n ");
            sbHEML.append(" <td class='listTD'>"+rowArray[11]+"</td>\n ");
            sbHEML.append(" <td class='listTD'>"+rowArray[12]+"</td>\n ");
            sbHEML.append(" <td class='listTD'>"+rowArray[13]+"</td></tr>\n ");
        }
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
