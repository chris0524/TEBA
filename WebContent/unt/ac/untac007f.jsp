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
<jsp:useBean id="obj" scope="request" class="unt.ac.UNTAC007F">
	<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<jsp:useBean id="objList" scope="page" class="java.util.ArrayList"/>

<%
if ("update".equals(obj.getState())) {
	obj.update();	
}

obj.setIsAdminManager(user.getIsAdminManager());
obj.setOrganID(user.getOrganID());

objList = obj.queryAll();

%>

<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Expires" content="-1"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<link rel="stylesheet" href="../../js/default.css" type="text/css">
<script type="text/javascript" src="../../js/validate.js"></script>
<script type="text/javascript" src="../../js/function.js"></script>
<script type="text/javascript" src="../../js/tablesoft.js"></script>
<script type="text/javascript" src="../../js/sList2.js"></script>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../../js/TJS.js"></script>
<script type="text/javascript" src="untch.js"></script>
<script type="text/javascript">
var insertDefault;	//二維陣列, 新增時, 設定預設值
insertDefault = new Array();

function queryOne(enterOrg,ownership,caseNo){}
function checkURL(surl){}
function init(){}


function checkField(){
	var alertStr = "";
	var i = 0;
	var errorMsg = "";
	
	if (form1.state.value=="update") {		
		$("input[name='sKeySet']").each(function(){			
			if($(this).attr('checked') == 'checked'){
				if($("input[name='q_enterDate']:eq(" + i + ")").val() == ''){
					$("input[name='q_enterDate']:eq(" + i + ")").css('backgroundColor',errorbg);
					errorMsg += "error";
				}
			}else{
				$("input[name='q_enterDate']:eq(" + i + ")").css('backgroundColor','');
			}
			
			i++;
		});
		
		if(errorMsg == 'error'){alertStr += "入帳日期不可為空!!";}		
	}
		
	if(alertStr.length!=0){ alert(alertStr); return false; }
	beforeSubmit();	
}

function execViewData(index){
	var arrays = $("input[name='sKeySet']:eq(" + index + ")").val().split(',');
	var url = "";
	var condition = "?enterOrg=" + arrays[0] +
					"&ownership=" + arrays[1] +
					"&caseNo=" + arrays[2] +
					"&differenceKind=" + arrays[3] +
					"&caseNo1=" + arrays[2] +
					"&state=queryAll";
	
	if(arrays[4] == '增加單'){			url = "../ne/untne002f.jsp";
	}else if(arrays[4] == '移動單'){		url = "../ne/untne009f.jsp";
	}else if(arrays[4] == '增減值單'){	url = "../ne/untne025f.jsp";
	}else if(arrays[4] == '減損單'){
		url = "../ne/untne014f.jsp";
		condition += "&isReadOnly=Y";
	}else if(arrays[4] == '處理單'){		url = "../ne/untne021f.jsp";
	}
	
	window.open(url + condition);		
}

function checkEnterDate(){
	var i = 0;
	
	$("input[name='sKeySet']").each(function(){			
		if($(this).attr('checked') == 'checked'){
			$("input[name='q_enterDate']:eq(" + i + ")").val('<%=Datetime.getYYYMMDD()%>');
			$("input[name='q_summonsDate']:eq(" + i + ")").val('<%=Datetime.getYYYMMDD()%>');
		}else{
			$("input[name='q_enterDate']:eq(" + i + ")").val('');
			$("input[name='q_summonsDate']:eq(" + i + ")").val('');
		}
		
		i++;
	});
}

</script>
</head>

<body topmargin="0" onLoad="whatButtonFireEvent('<%=obj.getState()%>');init();showErrorMsg('<%=obj.getErrorMsg()%>');">
<form id="form1" name="form1" method="post" onSubmit="return checkField();">

<!--隱藏欄位區============================================================-->
<table style="display:none"><tr><td>
	<input type="hidden" name="state" value="<%=obj.getState()%>">
	<input type="hidden" name="queryAllFlag" value="<%=obj.getQueryAllFlag()%>">
	<input type="hidden" name="userID" value="<%=user.getUserID()%>">
	<input type="hidden" name="organID" value="<%=user.getOrganID()%>">
	<input type="hidden" name="isOrganManager" value="<%=user.getIsOrganManager()%>">
	<input type="hidden" name="isAdminManager" value="<%=user.getIsAdminManager()%>">
	
</td></tr></table>

<!--頁籤區============================================================-->


<!--Form區============================================================-->
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><td class="bg">
	<div>
	<table class="table_form" width="100%" height="50%">
		<tr>
			<td class="queryTDInput" style="text-align:center;">
				<input class="toolbar_default" followPK="false" type="submit" name="querySubmit" value="　入帳　" onClick="whatButtonFireEvent('update');">
			</td>			
		</tr>
		<tr>
			<td class="queryTDInput" style="text-align:center;">
				<font color="red">若沒有輸入明細資料的單據，不可以入帳，所以不會被顯示在清單中</font>
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
	<%
		StringBuffer sbHEMLhead = new StringBuffer();
		if("Y".equals(obj.getIsAdminManager())){
			sbHEMLhead.append("<tr>");
			sbHEMLhead.append("<th class=\"listTH\"><input type=checkbox name=toggleAll class='field_Q' onclick=\"ToggleAll(this, document.form1, 'sKeySet');checkEnterDate();\" value=\"Y\" obj.getToggleAll()>確認</th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\">入帳機關</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',2,false);\" href=\"#\">入帳日期</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',3,false);\" href=\"#\">傳票號數</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',4,false);\" href=\"#\">傳票日期</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',5,false);\" href=\"#\">單據類別</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',6,false);\" href=\"#\">單據編號</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',7,false);\" href=\"#\">填單日期</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',8,false);\" href=\"#\">填造單位</a></th>");
			sbHEMLhead.append("</tr>");
			
		}else{			
			sbHEMLhead.append("<tr>");
			sbHEMLhead.append("<th class=\"listTH\"><input type=checkbox name=toggleAll class='field_Q' onclick=\"ToggleAll(this, document.form1, 'sKeySet');checkEnterDate();\" value=\"Y\" obj.getToggleAll()>確認</th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\">入帳日期</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',2,false);\" href=\"#\">傳票號數</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',3,false);\" href=\"#\">傳票日期</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',4,false);\" href=\"#\">單據類別</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',5,false);\" href=\"#\">單據編號</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',6,false);\" href=\"#\">填單日期</a></th>");
			sbHEMLhead.append("<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',7,false);\" href=\"#\">填造單位</a></th>");
			sbHEMLhead.append("</tr>");
		}
		
		out.write(sbHEMLhead.toString());
	%>
	</thead>
	<tbody id="listTBODY">
	<%
	int counter=0;
	StringBuffer sbHEML = new StringBuffer();
	if ("true".equals(obj.getQueryAllFlag()) && objList.size()==0){
		sbHEML.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
	}else{
		String rowArray[]=new String[8];
		java.util.Iterator it=objList.iterator();
		String tempJS="";
		String isCheck = "unchecked";			
		while(it.hasNext()){
			
			rowArray= (String[])it.next();
			isCheck = "unchecked";
			if (obj.getsKeySet()!=null && obj.getsKeySet().length>0) {
				for (int j=0; j<obj.getsKeySet().length; j++) {
					if (obj.getsKeySet()[j].equals(rowArray[0]+","+rowArray[1]+","+rowArray[2]+","+rowArray[3]+","+rowArray[4]+"','"+counter+"'")) {
						isCheck = "checked";
					}
				}
			}			
			//這個是key值 和.java裡的rowArray變數是對照的
			//tempJS = " onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne('"+rowArray[0]+"','"+rowArray[1]+"','"+rowArray[2]+"','"+rowArray[3]+"')\"";			
			if("Y".equals(obj.getIsAdminManager())){
				sbHEML.append(" <tr class='listTR'>\n");			
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+Common.checkGet(rowArray[0])+","+Common.checkGet(rowArray[1])+","+Common.checkGet(rowArray[2])+","+Common.checkGet(rowArray[3])+","+Common.checkGet(rowArray[4])+","+counter+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');checkEnterDate();\"" + isCheck + ">\n");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[8])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'><input class='field_Q' type='text' name='q_enterDate' size='7' maxlength='7'></td>\n ");
				sbHEML.append(" <td class='listTD'><input class='field_Q' type='text' name='q_summonsNo' size='15' maxlength='15'></td>\n ");
				sbHEML.append(" <td class='listTD'><input class='field_Q' type='text' name='q_summonsDate' size='7' maxlength='7'></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[4])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[5])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[6])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[7])+"</a></td>\n ");
				sbHEML.append("</tr>");
			}else{
				sbHEML.append(" <tr class='listTR'>\n");			
				sbHEML.append(" <td class='listTD'><input type='checkbox' class='field_Q' name='sKeySet' value=\""+Common.checkGet(rowArray[0])+","+Common.checkGet(rowArray[1])+","+Common.checkGet(rowArray[2])+","+Common.checkGet(rowArray[3])+","+Common.checkGet(rowArray[4])+","+counter+"\" onclick=\"Toggle(this, document.form1, 'sKeySet');checkEnterDate();\"" + isCheck + ">\n");
				sbHEML.append(" <td class='listTD'><input class='field_Q' type='text' name='q_enterDate' size='7' maxlength='7'></td>\n ");
				sbHEML.append(" <td class='listTD'><input class='field_Q' type='text' name='q_summonsNo' size='15' maxlength='15'></td>\n ");
				sbHEML.append(" <td class='listTD'><input class='field_Q' type='text' name='q_summonsDate' size='7' maxlength='7'></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[4])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[5])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[6])+"</a></td>\n ");
				sbHEML.append(" <td class='listTD'><a href='javascript:execViewData(" + counter + ");'>"+Common.checkGet(rowArray[7])+"</a></td>\n ");
				sbHEML.append("</tr>");
			}
			counter++;	
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



