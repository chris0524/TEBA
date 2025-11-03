<%@ page contentType="text/html;charset=UTF-8" import="java.sql.*,util.*"%>
<%@ include file="head.jsp" %>
<jsp:useBean id="obj" scope="request" class="unt.rt.UNTRT005F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
String ownership = Common.checkGet(request.getParameter("ownership"));
String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
String serialNo = Common.checkGet(request.getParameter("serialNo"));
%>
<html>
<head>
<title>增加單資料輔助視窗</title>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../js/default.css?1=ss" type="text/css">
<script language="javascript" src="../js/validate.js"></script>
<script language="javascript" src="../js/function.js"></script>

<script language="javascript">
function selectOrgan(selectEnterOrg,selectOrganSName,selectOwnership,selectPropertyNo,selectSerialNo,selectPropertyName,selectPropertyName1,selectPropertyKind,selectFundType,selectBuyDate,selectSourceKind,selectMeat,selectProofDoc1,selectHoldNume1,selectHoldDeno2,selectBookValue,selectRegisterCause,selectRegisterDate,selectSetPeriod,selectCommonObligee,selectSetPerson,selectPayDate,selectInterest,selectRent,selectNotes1,selectOldPropertyNo,selectOldSerialNo){
	opener.document.all.item("enterOrg").value=selectEnterOrg;
	opener.document.all.item("enterOrgName").value=selectOrganSName;
	opener.document.all.item("ownership").value=selectOwnership;
	opener.document.all.item("propertyNo").value=selectPropertyNo;
	opener.document.all.item("propertyNoName").value=selectPropertyName;
	opener.document.all.item("propertyName1").value=selectPropertyName1;
	opener.document.all.item("propertyKind").value=selectPropertyKind;
	opener.document.all.item("fundType").value=selectFundType;
	opener.document.all.item("buyDate").value=selectBuyDate;
	opener.document.all.item("sourceKind").value=selectSourceKind;
	opener.document.all.item("meat").value=selectMeat;
	opener.document.all.item("proofDoc1").value=selectProofDoc1;
	opener.document.all.item("holdNume1").value=selectHoldNume1;
	opener.document.all.item("holdDeno2").value=selectHoldDeno2;
	opener.document.all.item("registerCause").value=selectRegisterCause;
	opener.document.all.item("registerDate").value=selectRegisterDate;
	opener.document.all.item("setPeriod").value=selectSetPeriod;
	opener.document.all.item("commonObligee").value=selectCommonObligee;
	opener.document.all.item("setPerson").value=selectSetPerson;
	opener.document.all.item("payDate").value=selectPayDate;
	opener.document.all.item("interest").value=selectInterest;
	opener.document.all.item("rent").value=selectRent;
	opener.document.all.item("notes1").value=selectNotes1;
	opener.document.all.item("oldPropertyNo").value=selectOldPropertyNo;
	opener.document.all.item("oldSerialNo").value=selectOldSerialNo;
	opener.document.all.item("serialNo").value=selectSerialNo;
	if(selectPropertyNo.substring(0,3) == "801" || selectPropertyNo.substring(0,3) == "806"){
		opener.document.all.item("oldBookValue").value=selectBookValue;
		opener.document.all.item("newBookValue").value=0;
		opener.document.all.item("reduceBookValue").value=selectBookValue;
		opener.document.all.item("adjustBookValue").value=0;
		opener.document.all.item("adjustType").disabled = false;
		opener.document.all.item("adjustBookValue").readOnly = false;
	}else{
		opener.document.all.item("oldBookValue").value=selectBookValue;
		opener.document.all.item("newBookValue").value=selectBookValue;
		opener.document.all.item("reduceBookValue").value=0;
		opener.document.all.item("adjustBookValue").value=0;
		opener.document.all.item("adjustType").disabled = true;
		opener.document.all.item("adjustBookValue").readOnly = true;
		opener.document.all.item("adjustType").value="1";
	}

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
			<%=util.View.getPopProperty("field","propertyNo",obj.getPropertyNo(),obj.getPropertyNoName(),"8&isLookup=N")%>		
			分號：
			<input class="field" type="text" name="serialNo" size="7" maxlength="7" value="<%=obj.getSerialNo()%>">
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
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">入帳機關</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">權屬</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">財產名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">財產分號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',5,false);" href="#">證明書字號</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',6,false);" href="#">帳面設定價值</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',7,false);" href="#">設定人</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
		<%
		String sql=" select a.enterOrg, a.propertyNo, a.serialNo, b.organSName, "+
			" a.ownership ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName, "+
			" c.propertyName, nvl(a.propertyName1,' ') as propertyName1, a.propertyKind, nvl(a.fundType,' ') as fundType, a.buyDate, "+
			" nvl(a.sourceKind,' ') as sourceKind, nvl(a.meat,' ') as meat, nvl(a.proofDoc1,' ') as proofDoc1, a.holdNume1, a.holdDeno2, a.bookValue, "+
			" nvl(a.registerCause,' ') as registerCause, nvl(a.registerDate,' ') as registerDate, nvl(a.setPeriod,' ') as setPeriod, nvl(a.commonObligee,' ') as commonObligee, nvl(a.setPerson,' ') as setPerson, "+
			" nvl(a.payDate,' ') as payDate, a.interest, nvl(a.rent,' ') as rent, nvl(a.notes1,' ')as notes1, nvl(a.oldPropertyNo,' ')as oldPropertyNo, nvl(a.oldSerialNo,' ') as oldSerialNo "+
			" from UNTRT_AddProof a, SYSCA_Organ b,  SYSPK_PropertyCode c "+
			" where 1=1 and a.enterOrg = b.organID and a.propertyNo = c.propertyNo"+
			" and a.dataState='1' and a.verify='Y' " +
			" and (a.enterOrg , a.ownership , a.propertyNo , a.serialNo)  not in ( " +
			"  select enterOrg , ownership , propertyNo , serialNo from UNTRT_AdjustProof  where verify='N' " +
			"  ) " +
			" and a.enterOrg || a.ownership || a.propertyNo || a.serialNo  not in ( " +
			"  select enterOrg || ownership || propertyNo || serialNo from UNTRT_ReduceProof  where verify='N' " +
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
			//sql+=" and a.serialNo like '" + Common.get(serialNo) +"%' ";
			sql+=" and a.serialNo = " + Common.sqlChar(Common.formatFrontZero(Common.esapi(serialNo), 7));	
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
								" onClick=\"selectOrgan('"+Common.checkGet(rs.getString("enterOrg"))+"','"+Common.checkGet(rs.getString("organSName"))+"','"+Common.checkGet(rs.getString("ownership"))+"',"+
								" '"+Common.checkGet(rs.getString("propertyNo"))+"','"+Common.checkGet(rs.getString("serialNo"))+"','"+Common.checkGet(rs.getString("propertyName"))+"',"+
								" '"+Common.checkGet(rs.getString("propertyName1"))+"','"+Common.checkGet(rs.getString("propertyKind"))+"','"+Common.checkGet(rs.getString("fundType"))+"',"+
								" '"+Common.checkGet(rs.getString("buyDate"))+"','"+Common.checkGet(rs.getString("sourceKind"))+"','"+Common.checkGet(rs.getString("meat"))+"',"+
								" '"+Common.checkGet(rs.getString("proofDoc1"))+"','"+Common.checkGet(rs.getString("holdNume1"))+"','"+Common.checkGet(rs.getString("holdDeno2"))+"',"+
								" '"+Common.checkGet(rs.getString("bookValue"))+"','"+Common.checkGet(rs.getString("registerCause"))+"','"+Common.checkGet(rs.getString("registerDate"))+"',"+
								" '"+Common.checkGet(rs.getString("setPeriod"))+"','"+Common.checkGet(rs.getString("commonObligee"))+"','"+Common.checkGet(rs.getString("setPerson"))+"',"+
								" '"+Common.checkGet(rs.getString("payDate"))+"','"+Common.checkGet(rs.getString("interest"))+"','"+Common.checkGet(rs.getString("rent"))+"',"+
								" '"+Common.checkGet(rs.getString("notes1"))+"','"+Common.checkGet(rs.getString("oldPropertyNo"))+"','"+Common.checkGet(rs.getString("oldSerialNo"))+"')\" >");
				sbHEML.append(" <td class='listTD' >"+counter+".</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("organSName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("ownershipName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("propertyName"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("serialNo"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("proofDoc1"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("bookValue"))+"</td> ");
				sbHEML.append(" <td class='listTD' >"+Common.checkGet(rs.getString("setPerson"))+"</td> ");
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
<script language="javascript">
function popProperty(popPropertyNo,popPropertyName,preWord){
	var prop="";
	var windowTop=(document.body.clientHeight-400)/2+180;
	var windowLeft=(document.body.clientWidth-400)/2+250;
	prop=prop+"width=400,height=370,";
	prop=prop+"top="+windowTop+",";
	prop=prop+"left="+windowLeft+",";
	prop=prop+"scrollbars=no";
	returnWindow=window.open("popProperty.jsp?popPropertyNo="+popPropertyNo+"&popPropertyName="+popPropertyName+"&preWord="+preWord,"",prop);
}

function getProperty(popPropertyNo,popPropertyName,preWord){
	var alertStr = "";
	var arrPreWord;
	var isLookup = false;
	if (parse(preWord).length>0) arrPreWord = preWord.split("&");
	if (arrPreWord.length>1 && arrPreWord[1]=="isLookup=Y,fromAdd=Y") isLookup = true;
	if (parse(document.all.item(popPropertyNo).value).length>0) {
		var i = 0;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("xmlProperty.jsp?q_propertyNo="+document.all.item(popPropertyNo).value+"&preWord="+preWord)) {
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				if (isObj(document.all.item(popPropertyName))) {
					document.all.item(popPropertyName).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName");	
				}						
				if (isLookup) {
					if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyType");
					if (isObj(document.all.item("propertyUnit"))) document.all.item("propertyUnit").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
					if (isObj(document.all.item("material"))) document.all.item("material").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("material");
					if (isObj(document.all.item("limitYear"))) document.all.item("limitYear").value=xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear");
					if (parseInt(xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear"))>0) {
						document.all.item("otherLimitYear").value="";
						document.all.item("otherLimitYear").readOnly=true;
					} else {
						document.all.item("limitYear").value="";
						document.all.item("otherLimitYear").readOnly=false;
					}					
				}	
			}
			if (i==0) {
				if (isObj(document.all.item(popPropertyNo))) document.all.item(popPropertyNo).value="";	
				if (isObj(document.all.item(popPropertyName))) document.all.item(popPropertyName).value="";	
				if (isLookup) {
					if (isObj(document.all.item("propertyType"))) document.all.item("propertyType").value="";
					if (isObj(document.all.item("propertyUnit"))) document.all.item("propertyUnit").value="";
					if (isObj(document.all.item("material"))) document.all.item("material").value="";
					if (isObj(document.all.item("limitYear"))) document.all.item("limitYear").value="";
					if (isObj(document.all.item("otherLimitYear"))) document.all.item("otherLimitYear").readOnly=false;
				}
			}
		}
	}
	if (arrPreWord.length>2 && arrPreWord[2]!="") eval(arrPreWord[2].substring(arrPreWord[2].indexOf("=")+1));
}


</script>
</body>
</html>
