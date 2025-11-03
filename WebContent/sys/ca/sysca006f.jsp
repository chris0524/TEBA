<!--
程式目的：土地建物標示代碼維護
程式代號：sysca006f
程式日期：0950613
程式作者：amanda
--------------------------------------------------------
修改作者　　修改日期　　　修改目的
--------------------------------------------------------
-->

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../home/head.jsp" %>
<jsp:useBean id="obj" scope="request" class="sys.ca.SYSCA006F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("queryAll".equals(obj.getState())) {
	if ("false".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag("true"); }
}else if ("queryOne".equals(obj.getState())) {
	obj = (sys.ca.SYSCA006F)obj.queryOne();	
}else if ("insert".equals(obj.getState()) || "insertError".equals(obj.getState())) {
	obj.insert();
}else if ("update".equals(obj.getState()) || "updateError".equals(obj.getState())) {
	obj.update();
}else if ("delete".equals(obj.getState()) || "deleteError".equals(obj.getState())) {
	obj.delete();
}
if ("true".equals(obj.getQueryAllFlag())){
	objList = obj.queryAll();
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
<script language="javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
function checkField(){
	var alertStr="";
	if(form1.state.value=="queryAll"){
		alertStr += checkQuery();
	}else if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alertStr += checkEmpty(form1.country,"縣市");
		alertStr += checkEmpty(form1.town,"鄉鎮市區");
		alertStr += checkInt(form1.sectorNo,"地段小段代碼");
		alertStr += checkEmpty(form1.sectorNo,"地段小段代碼");		
		alertStr += checkEmpty(form1.signName,"地段小段名稱");
		getSignDesc();
	}
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();
}
function queryOne(signNo){
	form1.signNo.value=signNo;
	form1.state.value="queryOne";
	beforeSubmit();
	form1.submit();
}

function getSignDesc() {
	form1.signDesc.value = form1.country.options[form1.country.selectedIndex].text+form1.town.options[form1.town.selectedIndex].text+form1.signName.value;
}
</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField()">

<!--Query區============================================================-->
<div id="queryContainer" style="width:400px;height:400px;display:none">
	<iframe id="queryContainerFrame"></iframe>
	<div class="queryTitle">查詢視窗</div>
	<table class="queryTable"  border="1">
	<tr>
		<td class="queryTDLable" width="10%">縣市：</td>
		<td class="queryTDInput" width="20%">
					<select class="field_Q" type="select" name="q_country" onChange="changeSignNo1('q_country','q_town','q_sign','');">
			<%=util.View.getOption("select signno, signname from SYSCA_SIGN where signno like '_000000' order by seqno",obj.getQ_country())%>
			</select>
		</td>
	</tr>
	<tr>
		<td class="queryTDLable">鄉鎮市區：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_town" onChange="changeSignNo2('q_country','q_town','q_sign','');">
				<script>changeSignNo1('q_country','q_town','q_sign','<%=obj.getQ_town()%>');</script>
			</select>	
		</td>
	</tr>	
	<tr>
		<td class="queryTDLable">地段小段：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_sign">
				<script>changeSignNo2('q_country','q_town','q_sign','<%=obj.getQ_sign()%>');</script>
			</select>
		</td>
	</tr>
<!-- 	<tr>
		<td class="queryTDLable">鄉鎮市區：</td>
		<td class="queryTDInput">
			<select class="field_Q" type="select" name="q_town">
				<script>changeSignNo1('q_country','q_town','','<%=obj.getQ_town()%>');</script>
			</select>
		</td>
	</tr>
 -->	
	<tr>
		<td class="queryTDLable">標示全名：</td>
		<td class="queryTDInput">
			<input class="field_Q" type="text" name="q_signDesc" size="20" maxlength="20" value="<%=obj.getQ_signDesc()%>">
		</td>
	</tr>	
	<tr>
		<td class="queryTDInput" colspan="2" style="text-align:center;">
			<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="確　　定" >
			<input class="toolbar_default" followPK="false" type="button" name="queryCannel" value="取　　消" onClick="whatButtonFireEvent(this.name)">
		</td>
	</tr>
	</table>
</div>

<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
	<div id="formContainer">
	<table class="table_form" width="100%" height="100%">
	<tr>
		<td class="td_form" width="18%"><font color="red">*</font>縣市：</td>
		<td class="td_form_white" width="32%">
					<select class="field_P" type="select" name="country" onChange="changeSignNo1('country','town','','');getSignDesc();">
			<%=util.View.getOption("select signno, signname from SYSCA_SIGN where signno like '_000000' order by seqno",obj.getCountry())%>
			</select>
		</td>
		<td class="td_form"><font color="red">*</font>鄉鎮市區：</td>
		<td class="td_form_white">
			<select class="field_P" type="select" name="town" onChange="getSignDesc();">
				<script>changeSignNo1('country','town','','<%=obj.getTown()%>');</script>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td_form"><font color="red">*</font>地段小段代碼：</td>
		<td class="td_form_white">
			<input class="field_P" type="text" name="sectorNo" size="4" maxlength="4" value="<%=obj.getSectorNo()%>">
		</td>
		<td class="td_form"><font color="red">*</font>地段小段名稱：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="signName" size="25" maxlength="25" onBlur="getSignDesc();" value="<%=obj.getSignName()%>">
		</td>
	</tr>
	<tr>
		<td class="td_form">標示全名：</td>
		<td class="td_form_white" colspan="3">
			<input class="field_RO" type="text" name="signDesc" size="50" maxlength="100" value="<%=obj.getSignDesc()%>">
		</td>
	</tr>	
	<tr>
		<td class="td_form">備註：</td>
		<td class="td_form_white">
			<textarea class="field" name="memo" cols="24" rows="4"><%=obj.getMemo()%></textarea>
		</td>
		<td class="td_form">異動人員/日期：</td>
		<td class="td_form_white">
			[<input class="field_RO" type="text" name="editID" size="10" value="<%=obj.getEditID()%>">/
			<input class="field_RO" type="text" name="editDate" size="7" value="<%=obj.getEditDate()%>">]
		</td>		
	</tr>
	</table>
	</div>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
	<input type="hidden" name="signNo" value="<%=obj.getSignNo()%>">
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<jsp:include page="../../home/toolbar.jsp" />
</td></tr>
<tr><td>
<% request.setAttribute("QueryBean",obj);%>
<jsp:include page="../../home/page.jsp" />
</td></tr>
<!--List區============================================================-->
<tr><td class="bg">
<div id="listContainer">
<table class="table_form" width="100%" cellspacing="0" cellpadding="0">
	<thead id="listTHEAD">
	<tr>
		<th class="listTH" ><a class="text_link_w" >NO.</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',1,false);" href="#">縣市</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',2,false);" href="#">鄉鎮市區</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',3,false);" href="#">地段小段代碼-名稱</a></th>
		<th class="listTH"><a class="text_link_w" onclick="return sortTable('listTBODY',4,false);" href="#">標示全名</a></th>
	</tr>
	</thead>
	<tbody id="listTBODY">
	<%
	boolean primaryArray[] = {true,false,false,false,false};
	boolean displayArray[] = {false,true,true,true,true};
	int i;
	int counter=0;
	boolean trFlag = false;
	StringBuffer rtnStr = new StringBuffer();    
	StringBuffer sbQueryOne = new StringBuffer("");
	
	String pk = "";
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0)
	{
		//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
		rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;</td></tr>");
		//rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;<input type=\"hidden\" name=\"initQueryListFlag\" value=\"false\"></td></tr>");
	}
	else
	{
		String rowArray[]=new String[primaryArray.length];
		java.util.Iterator it=objList.iterator();
			
		
		while(it.hasNext())
		{			
			rowArray= (String[])it.next();
			counter++;
			
            pk = "";
			
			for(i=0;i<primaryArray.length;i++)
			{				
				if (primaryArray[i]) 
					pk+=Common.checkGet(rowArray[i]);
			}
			
			
			//顯示TR
			rtnStr.append(" <tr id=\"").append("listContainerRow").append(pk).append("\"");
			rtnStr.append(" class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" ");
			rtnStr.append(" onClick=\"listContainerRowClick('").append(pk).append("');queryOne(");
	
			//				if (counter==1) {
//				sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
//			}				
			for(i=0;i<primaryArray.length;i++)
			{
				if (primaryArray[i])
				{
					if(trFlag){
						rtnStr.append(",'").append(Common.checkGet(rowArray[i])).append("'");
//						if (counter==1) {
//							sbQueryOne.append(",'").append(rowArray[i]).append("'");
//						}
					}else{
						rtnStr.append("'").append(Common.checkGet(rowArray[i])).append("'");
//						if (counter==1) {
//							sbQueryOne.append("'").append(rowArray[i]).append("'");
//						}	
						trFlag = true;
					}						
				}
			}
//			if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");
			rtnStr.append(")\" >\n");

			//顯示TD
			rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
			for(i=0;i<displayArray.length;i++)
			{
				if (displayArray[i])
				{
					rtnStr.append(" <td class='listTD' >").append(rowArray[i]).append("</td>\n");
				}
			}				
			rtnStr.append("</tr>\n");
			trFlag = false;
		}
		// 預設選取欄位
		rtnStr.append("<script language=\"javascript\">");
		rtnStr.append("if(typeof queryOne == 'function') {"); 
		rtnStr.append("	if (isObj(document.all.item('state')) && document.all.item('state').value=='queryAllSuccess') {");
		rtnStr.append(" listContainerRowClick('").append(pk).append("');");
		rtnStr.append("	queryOne(");
		
		for(i=0;i<primaryArray.length;i++)
		{
			if (primaryArray[i])
			{
				if(trFlag){
					rtnStr.append(",'").append(Common.checkGet(rowArray[i])).append("'");
//					if (counter==1) {
//						sbQueryOne.append(",'").append(rowArray[i]).append("'");
//					}
				}else{
					rtnStr.append("'").append(Common.checkGet(rowArray[i])).append("'");
//					if (counter==1) {
//						sbQueryOne.append("'").append(rowArray[i]).append("'");
//					}	
					trFlag = true;
				}						
			}
		}			
		rtnStr.append(",-1);");

		rtnStr.append("	}");

		rtnStr.append("}");

		rtnStr.append("</script>");							
	
			//sb.append("<input type='hidden' name='listContainerRowDefault' id='listContainerRowDefault' value=\"" ).append( v ).append( "\">");

	}
	out.write(rtnStr.append(sbQueryOne).toString());
	//out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));
	%>
	</tbody>
</table>
</div>
</td></tr>
</table>
</form>
</body>
</html>



