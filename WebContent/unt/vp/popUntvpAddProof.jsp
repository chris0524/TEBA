<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.vp.UNTVP006F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String enterOrg = request.getParameter("enterOrg");
String ownership = request.getParameter("ownership");
String propertyNo = request.getParameter("propertyNo");
String serialNo = request.getParameter("serialNo");
%>
<html>
<head>
<title>增加單資料輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>

<script language="javascript">
function selectOrgan(selectEnterOrg,selectOrganSName,selectOwnership,selectPropertyNo,selectSerialNo,selectPropertyName,selectPropertyName1,selectPropertyKind,selectFundType,selectKeepUnit,selectPlace,selectSecurityMeat,selectSecurityName,selectSecurityAddr,selectSecurityItem,selectSecurityTime,selectSecurityOrg,selectSecurityDoc,selectOldBookSheet,selectOldBookAmount,selectOldBookValue,selectOldPropertyNo,selectOldSerialNo,selectSecurityOrgName){
	opener.document.all.item("enterOrg").value=selectEnterOrg;
	opener.document.all.item("enterOrgName").value=selectOrganSName;
	opener.document.all.item("ownership").value=selectOwnership;
	opener.document.all.item("propertyNo").value=selectPropertyNo;
	opener.document.all.item("serialNo").value=selectSerialNo;
	opener.document.all.item("propertyNoName").value=selectPropertyName;
	opener.document.all.item("propertyName1").value=selectPropertyName1;
	opener.document.all.item("propertyKind").value=selectPropertyKind;
	opener.document.all.item("fundType").value=selectFundType;
	opener.document.all.item("keepUnit").value=selectKeepUnit;
	opener.document.all.item("place").value=selectPlace;
	opener.document.all.item("securityMeat").value=selectSecurityMeat;
	opener.document.all.item("securityName").value=selectSecurityName;
	opener.document.all.item("securityAddr").value=selectSecurityAddr;
	opener.document.all.item("securityItem").value=selectSecurityItem;
	opener.document.all.item("securityTime").value=selectSecurityTime;
	opener.document.all.item("securityOrg").value=selectSecurityOrg;
	opener.document.all.item("securityDoc").value=selectSecurityDoc;
	//opener.document.all.item("oldBookSheet").value=selectOldBookSheet;
	opener.document.all.item("oldBookAmount").value=selectOldBookAmount;
	opener.document.all.item("oldBookValue").value=selectOldBookValue;
	opener.document.all.item("oldPropertyNo").value=selectOldPropertyNo;
	opener.document.all.item("oldSerialNo").value=selectOldSerialNo;
	//opener.document.all.item("newBookSheet").value=selectOldBookSheet;
	opener.document.all.item("newBookAmount").value=selectOldBookAmount;
	opener.document.all.item("newBookValue").value=selectOldBookValue;
	opener.document.all.item("securityOrgName").value=selectSecurityOrgName;
	window.close();
}

function addZore(){
    var Strlen = form1.serialNo.value.length;
    if(Strlen<8){
        for(i=0; i<7-Strlen;i++){
            form1.serialNo.value = "0" + form1.serialNo.value;
        }
    }
}
</script>
</head>
<body topmargin="3" leftmargin="3" rightmargin="3" bottommargin="3">
<form id="form1" name="form1" method="post" >
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg" >
	<div id="formContainer" style="height:80px">
	<table class="table_form" width="100%" height="100%">	
	<tr>
			<input class="field_RO" type="hidden" name="enterOrg" size="10" maxlength="10" value="<%=obj.getEnterOrg()%>">
			<input class="field_RO" type="hidden" name="enterOrgName" size="15" maxlength="50" value="<%=obj.getEnterOrgName()%>">
		<td class="td_form" width="10%">權屬：</td>
		<td class="td_form_white" width="36%" colspan="4">
			<select class="field" type="select" name="ownership">
				<%=util.View.getOnwerOption(obj.getOwnership())%>			
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form">財產編號：</td>
		<td class="td_form_white" colspan="4">
			<%=util.View.getPopProperty("field","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"9")%>		
			分號：
			<input class="field" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>"  onChange="addZore();">
		</td>
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
	<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="window.close()">
</td></tr>

<!--List區============================================================-->
<tr><td class="bg" >
<div id="listContainer" style="height:250px">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH">NO.</th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">管理機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">發行法人名稱</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
		<%
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, b.organSName, "+
			" (select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName, "+
			" c.propertyName, nvl(a.propertyName1,' ') as propertyName1, a.propertyKind, nvl(a.fundType,' ') as fundType, "+
			" nvl(a.keepUnit,' ') as keepUnit, nvl(a.place,' ') as place, nvl(a.securityMeat,' ') as securityMeat, "+
			" nvl(a.securityName,' ') as securityName, nvl(a.securityAddr,' ') as securityAddr, nvl(a.securityItem,' ') as securityItem, nvl(a.securityTime,' ') as securityTime, nvl(a.securityOrg,' ') as securityOrg, "+
			" nvl((select e.organSName from SYSCA_Organ e where a.securityOrg = e.organID),' ') as securityOrgName, "+
			" nvl(a.securityDoc,' ') as securityDoc, nvl(a.bookSheet,'0') as oldBookSheet, nvl(a.bookAmount,'0')as oldBookAmount, nvl(a.bookValue,'0')as oldBookValue, nvl(a.oldPropertyNo,' ') as oldPropertyNo, nvl(a.oldSerialNo,' ') as oldSerialNo "+
			" from UNTVP_AddProof a, SYSCA_Organ b,  SYSPK_PropertyCode c "+
			" where 1=1 and a.enterOrg = b.organID and a.propertyNo = c.propertyNo"+
			" and a.dataState='1' and a.verify='Y'" +
			" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
			"  select enterOrg , ownership , propertyNo , serialNo from UNTVP_AdjustProof  where verify='N' " +
			"  ) " ;

		if(!"".equals(user.getOrganID())){
			sql+=" and a.enterOrg like '" + Common.esapi(user.getOrganID()) +"%' ";
		}
		if(!"".equals(Common.get(ownership))){
			sql+=" and a.ownership like '" + Common.get(Common.esapi(ownership)) +"%' ";
		}
		if(!"".equals(Common.get(propertyNo))){
			sql+=" and a.propertyNo like '" + Common.get(Common.esapi(propertyNo)) +"%' ";
		}
		if(!"".equals(Common.get(serialNo))){
			sql+=" and a.serialNo like '" + Common.get(Common.esapi(serialNo)) +"%' ";
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
								" onClick=\"selectOrgan('"+rs.getString("enterOrg")+"','"+rs.getString("organSName")+"','"+rs.getString("ownership")+"',"+
								" '"+rs.getString("propertyNo")+"','"+rs.getString("serialNo")+"','"+rs.getString("propertyName")+"',"+
								" '"+rs.getString("propertyName1")+"','"+rs.getString("propertyKind")+"','"+Common.get(rs.getString("fundType"))+"',"+
								" '"+Common.get(rs.getString("keepUnit"))+"','"+rs.getString("place")+"','"+rs.getString("securityMeat")+"',"+
								" '"+rs.getString("securityName")+"','"+rs.getString("securityAddr")+"','"+rs.getString("securityItem")+"',"+
								" '"+rs.getString("securityTime")+"','"+rs.getString("securityOrg")+"','"+rs.getString("securityDoc")+"',"+
								" '"+rs.getString("oldBookSheet")+"','"+rs.getString("oldBookAmount")+"','"+rs.getString("oldBookValue")+"',"+
								" '"+rs.getString("oldPropertyNo")+"','"+rs.getString("oldSerialNo")+"','"+rs.getString("securityOrgName")+"')\" >");
				sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("organSName")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("ownershipName")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("propertyName")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("serialNo")+"</td> ");
				sbHEML.append(" <td class='listTD' >"+rs.getString("securityName")+"</td> ");
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
