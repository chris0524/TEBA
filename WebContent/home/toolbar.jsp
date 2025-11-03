<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%
String sScript =ESAPI.encoder().encodeForHTML(request.getServletPath());
String floatingField = "";
sScript = sScript.substring(sScript.lastIndexOf("&#x2f;")+6);
sScript = sScript.substring(0,sScript.lastIndexOf(".")).toUpperCase();

util.Database db = new util.Database();
try {
	java.sql.ResultSet rs = db.querySQL(" select programfield from SYSAP_FLOATINGBAR where programid="+util.Common.sqlChar(util.Common.esapi(sScript)));
	if (rs.next()) {
		floatingField = util.Common.checkGet(rs.getString("programField")).replaceAll("\n","");
		floatingField = floatingField.replaceAll("\r","");
		//System.out.println(floatingField);
	}	
	rs.close();		
} catch (Exception e) {
	e.printStackTrace();
} finally {
	db.closeAll();
}	


%>
<%@page import="sys.ap.SYSAP003F_Permission"%>
<script type="text/javascript" src="../../js/jquery-1.8.2.js"></script>
<script type="text/javascript">
//#F4FFE6
//var readbg="#F6FCE9";
//var readbg="#EEEEEE";
var readbg="#FFFFFF";
var editbg="#FFFFFF";
var hasBeenConfirmed = false;
var listToHTMLReportFlag = true;

var localButtonFireListener = new Object();
localButtonFireListener.whatButtonFireEvent = function(buttonName){
    // do nothing,for override.
}

localButtonFireListener.beforeWhatButtonFireEvent = function(buttonName){
    return true;
    // do nothing,for override.
}

function getFocusField() {
	if ("<%=sScript%>"=="NPBBL001F") {
		if (isObj(document.all.item("btnSelectOldCase"))) document.all.item("btnSelectOldCase").focus();
	} else {
		var arrObj = document.forms[0].elements;
		var arrLen = arrObj.length;		
		for(var i=0; i<arrLen; i++){  			
			var obj = arrObj[i];
			if (obj.className=="field"||obj.className=="field_P") {		
				if ((obj.value=="" && obj.type!="hidden") && (obj.readOnly==false||obj.disabled==false)) {
					if(ElementIsVisible(obj))obj.focus();
					break;
				}				
			}
		}
	}
}
function ElementIsVisible(elm)
{
    if (typeof(elm.style) != "undefined" &&
            (
                ( typeof(elm.style.display) != "undefined" 
                  && elm.style.display == "none" )
                ||
                ( typeof(elm.style.visibility) != "undefined"
                  && elm.style.visibility == "hidden" )
            )
        )
    {
        return false;
    }
    else if (typeof(elm.parentNode) != "undefined" 
              && elm.parentNode != null 
              && elm.parentNode != elm)
    {
        return ElementIsVisible(elm.parentNode);
    }
    return true;
}
/*全部欄位都設為readonly,  除query欄位及所有資料*/
function setAllReadonly(){
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if (like(obj.className,"field_Q")){	
			if (like(obj.className,"RO")){	
				if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){									
	    	    	obj.readOnly = true;	
				}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.style.backgroundColor=readbg;
					obj.disabled = true;
			   	}
			 }
		}else{			
			if (like(obj.className,"RO")){			
				//obj.readOnly = true;	
				obj.style.backgroundColor=readbg;							
				if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){
					obj.style.backgroundColor=editbg;			
    	    		obj.readOnly = true;	
				}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.disabled = true;
		   	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
   			  		obj.disabled = true;
   	  			}				
			}else{
				if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){						
					obj.style.backgroundColor=readbg;			
    	    		obj.readOnly = true;	
				}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.style.backgroundColor=readbg;
					obj.disabled = true;
		   	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
   			  		obj.disabled = true;
   	  			}
   	  		}
   	  	}
	}
}

/*全部欄位都設為readonly並清空, 除query欄位外所有資料*/
function setAllReadonlyClear(){
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if (like(obj.className,"field_Q")){	
			if (like(obj.className,"RO")){	
				if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){									
	    	    	obj.readOnly = true;	
				}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.style.backgroundColor=readbg;
					obj.disabled = true;
			   	}
			 }
		}else{	
			if (like(obj.className,"RO")){
				if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.style.backgroundColor=readbg;
					obj.disabled = true;
		   	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){		   	  	
   			  		obj.disabled = true;
   	  			}				
				obj.style.backgroundColor=editbg;	
				obj.readOnly = true;
				obj.value="";	
			}else{	
				if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){						
					obj.style.backgroundColor=readbg;			
        			obj.readOnly = true;	
        			obj.value="";
				}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.style.backgroundColor=readbg;
					obj.disabled = true;
					obj.checked  = false;
					if((obj.type=="select")||(obj.type=="select-one")) obj.value="";				
		   	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
   			  		obj.disabled = true;
   	  			}
   	  		}
      	}
	}
}

/*全部欄位都設為open*/
function setAllOpen(){
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if (like(obj.className,"RO")){
			obj.style.backgroundColor=readbg;
			if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
				obj.disabled = true;
	   	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
  				obj.disabled = true;
  	  		} else {
				obj.style.backgroundColor=editbg;	
				obj.readOnly = true;
			}
		}else{		
			if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){					
				obj.style.backgroundColor=editbg;			
	        	obj.readOnly = false;			
			}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
				obj.style.backgroundColor=editbg;
				obj.disabled = false;
    	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
	    	  	obj.disabled = false;
    	  	}
   	  	}
	}
}

/*全部欄位都設為open並清空, 除query欄位外所有資料*/
function setAllOpenClear(){
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if (!like(obj.className,"field_Q")){			
			if (like(obj.className,"RO")){			
				obj.style.backgroundColor=readbg;
				if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.disabled = true;
		   	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
	  				obj.disabled = true;
	  	  		} else {
					obj.style.backgroundColor=editbg;	
					obj.readOnly = true;
				}
    	    	obj.value="";
			}else{		
				if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){					
					obj.style.backgroundColor=editbg;			
		        	obj.readOnly = false;			
		        	obj.value="";
				}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
					obj.style.backgroundColor=editbg;
					obj.disabled = false;
					obj.checked  = false;
					if((obj.type=="select")||(obj.type=="select-one")) obj.value="";				
	   		  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
	    		  	obj.disabled = false;
	    		//為了使用單位與非機關使用單位二擇一時清除hidden欄位值
				}else if((obj.type=="hidden")&&(obj.className=="field")){
					obj.value="";
				}
			}
      	}
	}
}

/*除PK欄位外全部欄位都設為open*/
function setUnpkOpen(){
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if (like(obj.className,"RO")){
			obj.style.backgroundColor=readbg;			
			if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
				obj.disabled = true;
	   	  	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
  				obj.disabled = true;
  	  		} else {	
				obj.style.backgroundColor=editbg;	
				obj.readOnly = true;
			}
		}else{		
			if((obj.type=="text")||(obj.type=="textarea")||(obj.type=="password")){
				if (like(obj.className,"field_P")){
					obj.style.backgroundColor=readbg;			
	   	    		obj.readOnly = true;
				}else{
					obj.style.backgroundColor=editbg;			
	    	    	obj.readOnly = false;
				}			
			}else if((obj.type=="select")||(obj.type=="select-one")||(obj.type=="checkbox")||(obj.type=="radio")){
				if (like(obj.className,"field_P") || like(obj.className,"field_RO") || like(obj.className,"field_QRO")){
					obj.style.backgroundColor=readbg;
					obj.disabled = true;			
				}else{
					obj.style.backgroundColor=editbg;
					obj.disabled = false;	
				}	
	      	}else if ((obj.type=="button")&&(obj.className!="toolbar_default")){
				if (like(obj.className,"field_P")){
					obj.disabled = true;			
				}else{
					obj.disabled = false;	
				}    	  	
      		}
		}
	}
}
//改換到function.js
/*submit之前將select打開 
function beforeSubmit(){
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if ((obj.type=="select")||(obj.type=="select-one")||obj.type=="checkbox"||obj.type=="radio"){ obj.disabled = false; }
	}
}
*/
/*顯示查詢視窗*/
function queryShow(queryName){
	var queryObj=document.all.item(queryName);		
	var objHeight= queryObj.style.height;
	var objWidth= queryObj.style.width;	
	objHeight= objHeight.substring(0,objHeight.length-2);
	objWidth= objWidth.substring(0,objWidth.length-2);
	/**
  	document.all.item("insert").disabled = true;   
  	document.all.item("queryAll").disabled = true;
  	if (!document.all.item("update").disabled){
   		document.all.item("update").disabled = true;   
  		document.all.item("delete").disabled = true; 	
  	}
  	**/
	queryObj.style.top=(document.body.clientHeight-Number(objHeight)-80)/2;
	queryObj.style.left=(document.body.clientWidth-Number(objWidth))/2;	
	queryObj.style.display="block";  	
}
/*隱藏查詢視窗*/
function queryHidden(queryName){
	var queryObj=document.all.item(queryName);		
	queryObj.style.display="none";
	/**
  	document.all.item("insert").disabled = false;   
  	document.all.item("queryAll").disabled = false; 	
  	btnFollowPK();
  	**/
}

/*按鈕隨著PK值顯示*/
function btnFollowPK(){  	
  	var PKValueflag = false;
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if (obj.className=="field_P" && obj.value!="" && !PKValueflag && obj.type!="button"){
			PKValueflag = true;
			break;
		}			
   	}
   	
	for(var i=0; i<arrLen; i++){  			
		var obj = arrObj[i];
		if ((obj.type=="button")&&(obj.className=="toolbar_default" )){	
			if (PKValueflag){
				if (obj.followPK=="true" || obj.getAttribute('followPK')=='true') {obj.disabled = false; }				
			}else{
				if (obj.followPK=="true" || obj.getAttribute('followPK')=='true'){ obj.disabled = true; }
			}
		}					
   	}   	
   	
}

/*顯示新增,查詢按鈕*/
function btnIQ(){
  	document.all.item("insert").disabled = false;   
  	document.all.item("queryAll").disabled = false; 
  	document.all.item("update").disabled = true; 
  	document.all.item("delete").disabled = true; 
  	document.all.item("clear").disabled = true;  
  	document.all.item("confirm").disabled = true;
  	document.all.item("nextInsert").disabled = true;
}
/*顯示取消,確定按鈕*/
function btnCC(){
  	document.all.item("insert").disabled = true;   
  	document.all.item("queryAll").disabled = true; 
  	document.all.item("update").disabled = true; 
  	document.all.item("delete").disabled = true; 
  	document.all.item("clear").disabled = false;  
  	document.all.item("confirm").disabled = false;
  	document.all.item("nextInsert").disabled = false;
}
/*顯示新增,查詢,修改,刪除按鈕*/
function btnIQUD(){
  	document.all.item("insert").disabled = false;   
  	document.all.item("queryAll").disabled = false; 
  	document.all.item("update").disabled = false; 
  	document.all.item("delete").disabled = false; 
  	document.all.item("clear").disabled = true;  
  	document.all.item("confirm").disabled = true;
  	document.all.item("nextInsert").disabled = true;
}

/*觸發不同Button之相對應動作*/
function whatButtonFireEvent(buttonName){
	var oldState = "";

	if(!localButtonFireListener.beforeWhatButtonFireEvent(buttonName)){
	   return;
	}
	oldState = document.all("state").value;
	document.all("state").value=buttonName; 
	switch (buttonName)	{
		case "init":
			setAllReadonly();
			btnFollowPK();
			btnIQ();

			try {
				if (isObj(top.fbody.menuleft)) { 
					top.fbody.menuleft.doMax(); 
				}
			} catch (e) { }
			
			break;
		case "insert":
			setAllOpenClear();
			if (isObj(insertDefault)){
				for(var i=0;i<insertDefault.length;i++){
					if (isObj(document.all.item(insertDefault[i][0])) && isObj(document.all.item(insertDefault[i][0]).type)){					
						if((document.all.item(insertDefault[i][0]).type=="checkbox")){
							if (insertDefault[i][1]==document.all.item(insertDefault[i][0]).value)
								document.all.item(insertDefault[i][0]).checked  = true;
	   		  			}else{
			    		  	document.all.item(insertDefault[i][0]).value=insertDefault[i][1];
				   	  	}					
					} else if (isObj(document.all.item(insertDefault[i][0]))) { //radio box
						for (var j = 0; j < document.all.item(insertDefault[i][0]).length; j++) {
							if (document.all.item(insertDefault[i][0])[j].value==insertDefault[i][1]) document.all.item(insertDefault[i][0])[j].checked = true;
						}
					}					
				}
			}			
			if (isObj(form1.editID)) form1.editID.value = "<%=session.getAttribute("editID")%>";
			if (isObj(form1.editDate)) form1.editDate.value = "<%=util.Datetime.getYYYMMDD()%>";
			btnFollowPK();
			btnCC();
			getFocusField();			
			break;
		case "insertSuccess":
			setAllReadonly();
			btnFollowPK();
			btnIQUD();
			break;
		case "insertError":
			setAllOpen();
			//setUnpkOpen();
			btnFollowPK();
			btnCC();
			break;
		case "update":
			if (isObj(form1.editID)) form1.editID.value = "<%=session.getAttribute("editID")%>";
			if (isObj(form1.editDate)) form1.editDate.value = "<%=util.Datetime.getYYYMMDD()%>";		
			setUnpkOpen();
			btnFollowPK();
			btnCC();
			break;
		case "updateSuccess":
			setAllReadonly();
			btnFollowPK();
			btnIQUD();
			break;
		case "updateError":
			setUnpkOpen();
			btnFollowPK();
			btnCC();
			break;		
		case "delete":
			if (hasBeenConfirmed) {
				beforeSubmit();	
				document.forms[0].submit();
			} else {
				if(confirm("確定刪除這筆資料?")){
					beforeSubmit();	
					document.forms[0].submit();
				}
			}
			break;
		case "deleteSuccess":
			setAllReadonlyClear();
			btnFollowPK();
			btnIQ();
			break;
		case "deleteError":
			setAllReadonly();
			btnFollowPK();
			btnIQUD();
			break;					
		case "queryAll":
			queryShow('queryContainer');
			break;
		case "queryCannel":
			queryHidden('queryContainer');
			break;	
		case "queryAllSuccess":
			//Edit 2006/05/08
			var kickOutProgramID = "UNTLA002F,UNTBU002F,UNTMP002F,UNTMP003F,UNTRF002F,UNTRT002F,UNTNE002F";
			if (isObj(document.all.item("initQueryListFlag")) && document.all.item("initQueryListFlag").value=="false" && kickOutProgramID.indexOf("<%=sScript%>")==-1) {
				setAllOpenClear();
				if (isObj(insertDefault)){
					for(var i=0;i<insertDefault.length;i++){
						if (isObj(document.all.item(insertDefault[i][0]))){					
							if((document.all.item(insertDefault[i][0]).type=="checkbox")||(document.all.item(insertDefault[i][0]).type=="radio")){
								document.all.item(insertDefault[i][0]).checked  = insertDefault[i][1];
		   		  			}else{
				    		  	document.all.item(insertDefault[i][0]).value=insertDefault[i][1];
					   	  	}					
						}					
					}
				}			
				document.all.item("state").value="insert";				
				if (isObj(form1.editID)) form1.editID.value = "<%=session.getAttribute("editID")%>";
				if (isObj(form1.editDate)) form1.editDate.value = "<%=util.Datetime.getYYYMMDD()%>";
				btnFollowPK();
				btnCC();
				getFocusField();			
			//End of 2006/05/08
			} else {		
				setAllReadonlyClear();
				btnFollowPK();
				btnIQ();
			}
			break;			
		case "queryOneSuccess":
			try	{ form1.clear.focus(); } catch (ex) { } //ADD 2009-03-06 By Shan 當按下Backspace 鍵時，會造成某欄位可以修改值，所以要將Focus移到clear按鈕上
			setAllReadonly();
			btnFollowPK();
			btnIQUD();
			break;
		case "clear":
			if (oldState=="update") setAllReadonly();
			else setAllReadonlyClear();
			btnFollowPK();
			btnIQ();
			break;	
		default:
			break;
	}
	localButtonFireListener.whatButtonFireEvent(buttonName); 	
}

/*模仿sql like功能*/
function like(buttonName,likestr){
	return (buttonName.indexOf(likestr)>=0)
}
/*檢查查詢欄位至少輸入一個*/
function checkQuery(){
	var errorflag=true;
	var arrObj = document.forms[0].elements;
	var arrLen = arrObj.length;		
	for(var i=0; i<arrLen; i++){
		obj = arrObj[i];	
		if (like(obj.className,"field_Q")){
			if((obj.type=="checkbox")||(obj.type=="radio")){
				if (obj.checked){
					errorflag=false;
					break;				
				}
			}else{
				if ((parse(obj.value)!="") && (obj.type!="button")){
					errorflag=false;
					break;
				}
			}
		}	
	}
	if (errorflag){
		return "查詢欄位至少輸入一個!\n";
	}else{
		return "";
	}
}

/*列表隱藏*/
function listToHidden(btnObj,formName,listName){
	var formObj=document.all.item(formName);
	var listObj=document.all.item(listName);
	if (btnObj.value=="列表隱藏"){
		formObj.style.height="405px";
		listObj.style.display="none";
		btnObj.value="列表顯示";
	}else{
		formObj.style.height="220px";
		listObj.style.display="block";
		btnObj.value="列表隱藏";	
	}
}

function listToHTMLReportWithColor(listHeadName,listBodyName) {
	window.open('../../home/listReport.htm');
	listToHTMLReportFlag = true;
}

/*列表檔案*/
function listToHTMLReport(listHeadName,listBodyName){
	var i=0, j=0;
	if (listToHTMLReportFlag) {
		var sb = new StringBuffer();
		sb.append("<table border='0' cellpadding='0' cellspacing='0' bgcolor='#000000' width='100%'><tr><td><table width='100%' border='0' cellpadding='1' cellspacing='1'>");
		//寫入Thead資料
		var listHead = document.getElementById(listHeadName);	
		for(i=0; i<listHead.rows.length; i++){
			sb.append("<tr bgcolor='#CCCCCC' align='center'>");
			for(j=0; j<listHead.rows[i].cells.length; j++){			
				if (isObj(listHead.rows[i].cells[j].childNodes[0].childNodes[0])){
					sb.append("<td>").append(listHead.rows[i].cells[j].childNodes[0].childNodes[0].nodeValue).append("</td>");
				}else{
					sb.append("<td>&nbsp;</td>");
				}
			}
			sb.append("</tr>");
		}
		
		//寫入Tbody資料	
		var listBody = document.getElementById(listBodyName);	
		for(i=0; i<listBody.rows.length; i++){
			sb.append("<tr bgcolor='#FFFFFF' align='center'>");	
			for(j=0; j<listBody.rows[i].cells.length; j++){
				textAlign = "";
				if (isObj(listBody.rows[i].cells[j].childNodes[0])){
					try {			
						textAlign = listBody.rows[i].cells[j].style.getAttribute('textAlign');
					} catch(e) {}					
					if (textAlign!=null && textAlign!="") sb.append("<td style=\"text-align:").append(textAlign).append("\">").append(listBody.rows[i].cells[j].childNodes[0].nodeValue).append("</td>");
					else sb.append("<td>").append(listBody.rows[i].cells[j].childNodes[0].nodeValue).append("</td>");
				}else{
					sb.append("<td>&nbsp;</td>");
				}
			}
			sb.append("</tr>");
		}
		sb.append("</table></td></tr></table>");
		var ie = window.open();
		ie.document.write(sb.toString());
		listToHTMLReportFlag = false;
	} else listToHTMLReportWithColor(listHeadName,listBodyName);
}

</script>
<%
		String programID = sScript.toLowerCase(); //程式名稱:programID 
		String progID = util.Common.checkGet(request.getParameter("progID")); //程式編號:progID
		
		boolean programFlag = false;
	 	SYSAP003F_Permission[] permission = (SYSAP003F_Permission[])session.getAttribute("CurrentPermission");

	 	if(permission!=null && programID!=null){
			for(int i=0;i<permission.length;i++){
				if(permission[i].getProgramID().equals(progID)) {
					programFlag = true;
					session.setAttribute("PID",progID);
					break;
				}
			}	 		
	 	}
		if (programFlag==false)	programID = (String)session.getAttribute("PID");	 	

		if ("".equals(util.Common.get(progID))) progID = (String)session.getAttribute("PID");   

	 	if (programID!=null && programID.length()>0) { 
			for(int i=0;i<permission.length;i++){
				if(permission[i].getProgramID().equals(progID) && permission[i].getAuthority().equals("Q")){
					//out.write("setDisplayItem('spanInsert,spanUpdate,spanDelete,spanClear,spanConfirm,spanNextInsert,btnMaintain,btnMaintain1,btnMaintain2,btnMaintain3,approveAll','H');\n");				
					out.write("<input type='hidden' name='permissionField' value='Q'>");				
				}	
			}	
		 }
%>
<input type="hidden" name="floatingField" value="<%=floatingField%>">
<script type="text/javascript">
function closebar() {
	if(isObj(top.title)) {
		top.title.document.cookie = "remainclosed=1";
		top.title.document.getElementById("topbar").style.visibility = "hidden";
	}
}

if ("<%=floatingField%>"!="") {
	try {
		if (isObj(top.frames['title'])) top.frames['title'].showFloatingTopBar('<%=floatingField%>');
	} catch(e) {}
} else {
	//closebar();
}

setAutocompleteOff(_setAutoCompleteOffFields,"off");
</script>

<span id="spanToolbar">&nbsp;|
<span id="spanInsert">
<input class="toolbar_default" type="button" followPK="false" name="insert"  value="新　增" onClick="whatButtonFireEvent(this.name)">&nbsp;|
</span>
<span id="spanQueryAll">
<input class="toolbar_default" type="button" followPK="false" name="queryAll" value="查　詢" onClick="whatButtonFireEvent(this.name)">&nbsp;|
</span>
<span id="spanUpdate">
<input class="toolbar_default" type="button" followPK="true"  name="update" value="修　改" onClick="whatButtonFireEvent(this.name)">&nbsp;|
</span>
<span id="spanDelete">
<input class="toolbar_default" type="button" followPK="true"  name="delete" value="刪　除" onClick="whatButtonFireEvent(this.name)">&nbsp;|
</span>
<span id="spanClear">
<input class="toolbar_default" type="button" followPK="false" name="clear" value="取　消" onClick="whatButtonFireEvent(this.name)">&nbsp;|
</span>
<span id="spanConfirm">
<input class="toolbar_default" type="submit" followPK="false" name="confirm" value="確　定" >&nbsp;|
</span>
<span id="spanNextInsert">
<input class="toolbar_default" type="submit" followPK="false" name="nextInsert" value="繼續新增">&nbsp;|
</span>
<span id="spanListPrint">
<input class="toolbar_default" type="button" followPK="false" name="listPrint" value="列表檔案" onClick="listToHTMLReport('listTHEAD','listTBODY')">&nbsp;|
</span>
<!--
<span id="spanDuplicateForm">
<input class="toolbar_default" type="button" followPK="false" name="listPrint" value="複製視窗" onclick="copyFormContainer();">&nbsp;|
</span>
-->
<span id="spanListHidden">
<input class="toolbar_default" type="button" followPK="false" name="listHidden" value="列表隱藏" onClick="listToHidden(this,'formContainer','listContainer')">&nbsp;|
</span></span>
