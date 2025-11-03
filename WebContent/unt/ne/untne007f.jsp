<!--
程式目的：物品存置地點維護
程式代號：untne007f
程式日期：1060522
程式作者：Jim.Lin
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.ne.UNTNE007F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="uctls" scope="request" class="unt.ch.UNTCH_Tools"/>
<%
obj.setProgID(Common.checkGet(request.getParameter("progID")));
if("".equals(Common.checkGet(obj.getEnterOrg()))) {
    obj.setEnterOrg(user.getOrganID());
}
obj.setKeeperno(user.getKeeperno());

if ("queryAll".equals(obj.getState())) {
    if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }   
}else if ("queryOne".equals(obj.getState())) {  
    obj = obj._execQueryOneforDetail(); 
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {    
    obj.updatePlace(); 
}  
if ("updateSuccess".equals(obj.getState())) { obj.setQueryAllFlag("true"); }

if ("true".equals(obj.getQueryAllFlag())){
    objList = obj.execQueryAll();
    if (!objList.isEmpty()) {
    	if("".equals(obj.getEnterOrg()) ||
            "".equals(obj.getOwnership()) ||
            "".equals(obj.getDifferenceKind()) ||
            "".equals(obj.getPropertyNo()) ||
            "".equals(obj.getSerialNo())||
            "".equals(obj.getLotNo())) {
            obj.setEnterOrg(((String[])objList.get(0))[1]);
            obj.setOwnership(((String[])objList.get(0))[2]);
            obj.setDifferenceKind(((String[])objList.get(0))[3]);
            obj.setPropertyNo(((String[])objList.get(0))[5]);
            obj.setSerialNo(((String[])objList.get(0))[6]);
            obj.setLotNo(((String[])objList.get(0))[12]);
        }
        obj._execQueryOneforDetail();
    }
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
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript" src="../../js/validate.js?v=1" ></script>
<script type="text/javascript" src="../../js/function.js" ></script>
<script type="text/javascript" src="../../js/tablesoft.js" ></script>
<script type="text/javascript" src="../../js/sList2.js" ></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="../../js/DataCouplingCtrl.js?v=2.0"></script>
<script type="text/javascript">
var insertDefault;  //二維陣列, 新增時, 設定預設值

function checkField(){
    var alertStr="";
    
    if(alertStr.length!=0){ alert(alertStr); return false; }
    beforeSubmit();
}

function queryOne(enterOrg,ownership,differenceKind,propertyNo,serialNo,lotNo){
    form1.enterOrg.value = enterOrg;
    form1.ownership.value = ownership;
    form1.differenceKind.value = differenceKind;
    form1.propertyNo.value = propertyNo;
    form1.serialNo.value = serialNo;
    form1.lotNo.value = lotNo;
    form1.state.value="queryOne";
    beforeSubmit();
    form1.submit();     
}

function init(){
    setDisplayItem("spanInsert,spanDelete,spanNextInsert,spanListHidden", "H");
    
    //縮起功能目錄樹，因一進頁面就要queryAll，不會觸發到toolbar.jsp內whatButtonFireEvent('init')，所以拉到這邊做。
    try {
        if (isObj(top.fbody.menuleft)) { 
            top.fbody.menuleft.doMax(); 
        }
    } catch (e) { }
    
    var dcc1 = new DataCouplingCtrlPlus(form1.enterOrg, form1.KeepUnitQuickly, form1.KeepUnit, form1.UseUnitQuickly, form1.UseUnit, true, false);
    var dcc2 = new DataCouplingCtrlPlus(form1.enterOrg, form1.KeeperQuickly, form1.Keeper, form1.UserQuickly, form1.User, true, false); 
    var dcc3 = new DataCouplingCtrlPlus(form1.enterOrg, form1.q_keeperQuickly, form1.q_keeper, form1.q_keepUnitQuickly, form1.q_keepUnit, false, false); 
    
    autoListContainerRowClick();
}

function autoListContainerRowClick() {
    if (form1.enterOrg.value !== '' &&
        form1.ownership.value !== '' &&
        form1.differenceKind.value !== '' &&
        form1.propertyNo.value !== '' &&
        form1.serialNo.value !== '' &&
        form1.lotNo.value !== '') {
        var key = form1.enterOrg.value + form1.ownership.value + form1.differenceKind.value + form1.propertyNo.value + form1.serialNo.value + form1.lotNo.value;
        listContainerRowClick(key);
    }
}

function openBatchUpdate() {
    var urlParms = "enterOrg="+form1.enterOrg.value
    + "&keeperno="+form1.keeperno.value
    + "&queryAllFlag=true";
    window.open("../../unt/ne/untne011f.jsp?" + urlParms);
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">
<input type="hidden" name="querySelectValue" value="<%=obj.getQuerySelectValue()%>">
<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
<input type="hidden" name="state" value="<%=obj.getState()%>">
<input type="hidden" name="roleid" value="<%=user.getRoleid()%>">
<input type="hidden" name="unitID" value="<%=user.getUnitID()%>">
<input type="hidden" name="userID" value="<%=user.getUserID()%>">
<input type="hidden" name="keeperno" value="<%=user.getKeeperno()%>">
<input type="hidden" name="isAddProof" value="<%=obj.getIsAddProof()%>">    
<input type="hidden" name="progID" value="<%=obj.getProgID()%>">
<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
<input type="hidden" name="userOrganID" value="<%=user.getOrganID()%>">
<input type="hidden" class="field" name="proofYear" value="<%=obj.getProofYear()%>">
<input type="hidden" class="field" name="proofDoc" value="<%=obj.getProofDoc()%>">
<input type="hidden" class="field" name="proofNo" value="<%=obj.getProofNo()%>">
<!--Query區============================================================-->
<div id="queryContainer" style="width:800px;height:200px;display:none">
    <iframe id="queryContainerFrame"></iframe>
    <div class="queryTitle">查詢視窗</div>
    <table class="queryTable"  border="1">
    <tr>
        <td class="queryTDLable">物品編號：</td>     
        <td class="queryTDInput" colspan="3">   
            起<%=util.View.getPopProperty("field_Q","q_propertyNoS",obj.getQ_propertyNoS(),obj.getQ_propertyNoSName(),"6")%>&nbsp;~&nbsp;
            訖<%=util.View.getPopProperty("field_Q","q_propertyNoE",obj.getQ_propertyNoE(),obj.getQ_propertyNoEName(),"6")%>
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">物品分號：</td>
        <td class="queryTDInput">
            起<input class="field_Q" type="text" name="q_serialNoS" size="7" maxlength="7" value="<%=obj.getQ_serialNoS()%>" onChange="addChar(this ,7);">&nbsp;~&nbsp;
            訖<input class="field_Q" type="text" name="q_serialNoE" size="7" maxlength="7" value="<%=obj.getQ_serialNoE()%>" onChange="addChar(this ,7);">
        </td>  
        <td class="td_form">物品別名：</td>
        <td class="queryTDInput" >
            <input class="field_Q" type="text" name="q_propertyName1" size="20" maxlength="30" value="<%=obj.getQ_propertyName1()%>">        
        </td>     
    </tr>   
    <tr>
        <td class="queryTDLable">保管單位：</td>
        <td class="queryTDInput" colspan="3">
            <%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + user.getOrganID() + "' order by unitno ",
              "field_Q", "form1", "q_keepUnit", "q_keepUnitQuickly", obj.getQ_keepUnit()) %>
            <input class="field_Q" type="button" name="btn_q_keepUnit" onclick="popUnitNo(form1.userOrganID.value,'q_keepUnit')" value="..." title="單位輔助視窗">
                         &nbsp;&nbsp;保管人：
            <%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + user.getOrganID() + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
              "field_Q", "form1", "q_keeper", "q_keeperQuickly", user.getKeeperno()) %>
              <input class="field_Q" type="button" name="btn_q_keeper" onclick="popUnitMan(form1.userOrganID.value,'q_keeper')" value="..." title="人員輔助視窗">
        </td>
    </tr>
    <tr>
        <td class="queryTDLable">存置地點：</td>
        <td class="queryTDInput" colspan="3">
            <input class="field_Q" type="text" name="q_place1" size="10" maxlength="10" value="<%=obj.getQ_place1()%>" onchange="queryPlaceName('enterOrg','q_place1');">
            <input class="field_Q" type="button" name="btn_q_place1" onclick="popPlace(form1.userOrganID.value,'q_place1','q_place1Name')" value="..." title="存置地點輔助視窗">
            [<input class="field_RO" type="text" name="q_place1Name" size="20" maxlength="20" value="<%=obj.getQ_place1Name()%>">]        
        </td>
    </tr>
    <tr>
        <td class="queryTDInput" colspan="4" style="text-align:center;">
            <input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
            <input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
        </td>
    </tr>
    </table>
</div>

<TABLE STYLE="width:100%;" CELLPADDING=0 CELLSPACING=0 valign="top">
    <tr><td ID=t1 CLASS="tab_border1" HEIGHT="25">物品存置地點維護</td></tr><tr><td class="tab_line1"></td></tr>
</TABLE>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
    <div id="formContainer">
    <table class="table_form" width="100%" height="100%">
        <tr>
            <td class="td_form" width="15%">入帳機關：</td>
            <td class="td_form_white" colspan="3">
                <input class="field_QRO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>" >
                [<input class="field_QRO" type="text" name="enterOrgName" size="20" maxlength="50" value="<%=obj.getEnterOrgName()%>">]
                &nbsp;&nbsp;&nbsp;
                權屬：
                <input class="field_QRO" type="hidden" name="ownership" value="<%=obj.getOwnership()%>" >
                <select class="field_QRO" type="select" name="ownershipName">
                    <%=util.View.getOnwerOption(obj.getOwnership())%>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_form">物品區分別：</td>
            <td colspan="3" class="td_form_white">
                <%=util.View._getSelectHTML("field_RO", "differenceKind", obj.getDifferenceKind(),"DFK") %>
            </td>
        </tr>
        <tr>
            <td class="td_form">購置日期：</td>
            <td>
                [<input class="field_RO" type="text" name="buyDate" size="7" maxlength="7" value="<%=obj.getBuyDate()%>" onchange="form1.sourceDate.value = this.value;checkDeprMethod('');">]
            </td>
        </tr>
        <tr>
            <td class="td_form">物品編號：</td>
            <td class="td_form_white" colspan="3">
                <%=uctls.getPopProperty("field_P","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"&isLookup=Y")%>
                分號：
                [<input class="field_PRO" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">]               
                <input class="field_PRO" type="hidden" name="lotNo" size="7" maxlength="7" value="<%=obj.getLotNo()%>">
                <br>
                物品別名
                [<input type="text" class="field_RO" name="propertyName1" value="<%=obj.getPropertyName1() %>" size="30" maxlength="30">]
               原物品編號：[<input class="field_RO" type="text" name="oldPropertyNo" size="10" value="<%=obj.getOldPropertyNo()%>">] 
        　  原分號：[<input class="field_RO" type="text" name="oldSerialNo" size="7" maxlength="7" value="<%=obj.getOldSerialNo()%>">]
            </td>
        </tr>
        <tr id="div_move">
            <td class="td_form">保管使用資料：</td>
            <td class="td_form_white" colspan="3">
                保管單位：
                <%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
                                                                   "field_RO", "form1", "KeepUnit", "form1.KeepUnitQuickly.value = this.value", 
                                                                   "KeepUnitQuickly", "", obj.getKeepUnit()) %>
                &nbsp;&nbsp;&nbsp;保管人：
                <%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
                                                                    "field_RO", "form1", "Keeper", "form1.KeeperQuickly.value = this.value",
                                                                    "KeeperQuickly", "", obj.getKeeper()) %>
                <br>
                使用單位：
                <%=View.getSelectCtrlGroup("select unitno, unitname from UNTMP_KEEPUNIT where enterorg = '" + Common.esapi(user.getOrganID()) + "' order by unitno ", 
                                                                   "field_RO", "form1", "UseUnit", "UseUnitQuickly", obj.getUseUnit()) %>
                &nbsp;&nbsp;&nbsp;使用人：
                <%=View.getSelectCtrlGroup("select a.keeperno, CASE a.incumbencyyn WHEN 'N' THEN a.keepername + '(停用)' ELSE a.keepername END AS keepername from UNTMP_KEEPER a LEFT OUTER JOIN SYSAP_EMP b ON a.enterorg = b.organid AND a.keeperno = b.empid where 1=1 and enterorg = '" + Common.esapi(user.getOrganID()) + "' order by case a.incumbencyyn when 'Y' then 'Y' else 'N' end DESC,convert(varchar,keepername) ", 
                                                                    "field_RO", "form1", "User", "UserQuickly", obj.getUserNo()) %>
                <br>
                使用註記：
                [<input type="text" class="field_RO" name="originalUserNote" value="<%=obj.getOriginalUserNote() %>" size="20" maxlength="100">]             
                <br>
                存置地點：
                <input class="field" type="text" name="place1" size="10" maxlength="10" value="<%=obj.getPlace1() %>" onchange="queryPlaceName('enterOrg','place1');">
                <input class="field" type="button" name="btn_place1" onclick="popPlace(form1.enterOrg.value,'place1','place1Name')" value="..." title="存置地點輔助視窗">
                [<input class="field_RO" type="text" name="place1Name" size="20" maxlength="20" value="<%=obj.getPlace1Name() %>">]
                &nbsp;&nbsp;&nbsp;
                存置地點說明：
                <input class="field" type="text" name="place" size="30" maxlength="30" value="<%=obj.getPlace() %>">
            </td>
        </tr>
    </table>
    </div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">       
    <jsp:include page="../../home/toolbar.jsp" />
<span id="batch">
<input class="toolbar_default" type="button" followPK="false" name="batchOpen" value="現有資料明細修改" onClick="openBatchUpdate();">&nbsp;|
</span>
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td>
</tr>

<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
    <thead id="listTHEAD">
    <tr>
        <th class="listTH" ><a class="text_link_w" >NO.</a></th>
        <th class="listTH"><a class="text_link_w" onClick="return sortTable('listTBODY',1,false);" href="#">物品區分別</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">物品編號</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">物品分號</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">物品名稱</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">物品別名</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">保管單位</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">保管人</a></th>
        <th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',8,false);" href="#">存置地點</a></th>
    </thead>
    <tbody id="listTBODY">
    <%
        boolean primaryArray[] = {false,true,true,true,false,true,true,false,false,false,false,false,true};
        boolean displayArray[] = {true,false,false,false,true,true,true,true,true,true,true,true,false};
        String alignArray[] = {"center","center","center","center","center","center","center","center","center","center","center","center","center"};
        out.write(util.View.getQuerylistWithRowNum(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
     %>
     </tbody>
</table>
</div> 
</td></tr>
</table>
</form>

<script type="text/javascript">
localButtonFireListener.whatButtonFireEvent = function(buttonName){
    switch (buttonName) {
        case "update":
            initFormDisabled();

            checkDeprMethod('');
            if(form1.progID.value == 'untch001f02extend01'){
            }else if(form1.progID.value == 'untch001f02extend02'){
                setFormItem("originalKeepUnit,btn_originalKeepUnit,originalKeeper,btn_originalKeeper,originalUseUnit,btn_originalUseUnit,originalUser,btn_originalUser,originalUserNote,originalMoveDate,btn_originalMoveDate,originalPlace1,btn_originalPlace1,originalPlace,propertyKind,valuable,buyDate,btn_buyDate,originalUnit,originalBV", "R");
            }

            if (form1.differenceKind.value == "110") {
                setFormItem("cb_originalDeprUnitCB,originalDeprPark,originalDeprUnit,originalDeprUnit1,originalDeprAccounts", "R");
            }

            break;
    }
};

</script>
</body>
</html>
