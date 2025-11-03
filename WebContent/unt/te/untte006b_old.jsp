<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="util.Database" %>
<%@ page import="util.Common" %>
<%
	int maxPostSize = 1024*1024*50; //50 M
	int i = 0;
	int j = 0;
	String lndYear = Common.checkGet(request.getParameter("lndYear"));
	String sType = "GRA"; //util.Common.get(request.getParameter("sType"));
	String[] arrLevyType = request.getParameterValues("levyType");
	String strLevyType = "";
	boolean isUpdateAll = false;	
    boolean isMultipart = false;
	int counter = 0;    

    String contentType = request.getContentType();     
    if(contentType!=null){
        contentType = contentType.toLowerCase();
        if(contentType.startsWith("multipart/form-data")){
            isMultipart = true;
        }
    }

    MultipartRequest mr = null;
    String uploadCaseID = "";
    String actionType = "";
    String fileName = "";
    
    if(isMultipart){            
        uploadCaseID = new java.rmi.dgc.VMID().toString();
        uploadCaseID = uploadCaseID.replaceAll(":","_");
        
        //File tempDirectory = new File(application.getInitParameter("filestoreLocation"));
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        
        //半小時後將其刪除
        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,300000);        
        
        tempDirectory = new File(tempDirectory,uploadCaseID);                
        tempDirectory.mkdirs();               
        
        FileRenamePolicy policy = new DefaultFileRenamePolicy();
        String encoding = "UTF-8";
        
        mr = new MultipartRequest(request,tempDirectory.getAbsolutePath(),maxPostSize,encoding,policy);
        actionType = Common.checkGet(mr.getParameter("actionType"));
        
    	arrLevyType = mr.getParameterValues("levyType");
    	strLevyType = "";
    	isUpdateAll = false;
    }
    
    String actionResult = "noAction";
    String actionMessage = "";
    
    if("doUpload".equals(actionType)){
    	
    	
        if (isUpdateAll) {
        	strLevyType = "'1','2','5'";        
        	if (!"".equals(sType)) {
        		strLevyType = "'6','7','9'";        	        		
        	}
        }    	
        
        Enumeration enu = mr.getFileNames();
        File uploadFile = null;
        
        if(enu.hasMoreElements()){
            String name = (String)enu.nextElement();
            uploadFile = mr.getFile(name);
            actionResult = "doImportSuccess";
            actionMessage = "成功傳輸";
            fileName = uploadFile.getName();

   			String datafile	 = System.getProperty("java.io.tmpdir")+File.separator+uploadCaseID+File.separator+fileName;
   			
   			//讀取資料檔
			Database db = new Database();     				   			
   			String lineStr="";   			
   			try{
				StringBuffer sbSQL = new StringBuffer();
				StringBuffer sbLevyNo = new StringBuffer("''");		
				StringBuffer sbHTML = new StringBuffer("傳輸失敗，因為以下資料曾經銷過單！<br><br><table border=1><tr bgcolor=#ffffd2><td>序號</td><td>條碼編號</td><td>繳款書編號</td><td>金額</td><td>繳款日期</td></tr>");
				StringBuffer sbHTML1 = 	new StringBuffer("成功傳輸以下資料！<br><br><table border=1><tr bgcolor=#ffffd2><td>序號</td><td>條碼編號</td><td>繳款書編號</td><td>金額</td><td>繳款日期</td></tr>");
				StringBuffer sbHTML2 = 	new StringBuffer("此銷號檔該日無銷號資料!!！");
   				BufferedReader in = new BufferedReader(new FileReader(datafile));
				int errCount = 0;
   				while ((lineStr = in.readLine()) != null) {
   				Database db_a = new Database();     
   					try{
   					//System.out.println(lndYear);
						String a="insert into LND_Value ("+"\n"+
							"lndYYY,lndLosn,signNo,"+"\n"+
							"lndTaxTp1,levyArea1,abateArea1,abateAll1,lndPreTax1,"+"\n"+
							"lndTaxTp2,levyArea2,abateArea2,abateAll2,lndPreTax2,"+"\n"+
							"lndTaxTp3,levyArea3,abateArea3,abateAll3,lndPreTax3,"+"\n"+
							"lndTaxTp4,levyArea4,abateArea4,abateAll4,lndPreTax4,"+"\n"+
							"lndTaxTp5,levyArea5,abateArea5,abateAll5,lndPreTax5,"+"\n"+
							"secChiNM,town_NM,OD,OA"+"\n"+
						" )Values("+"\n"+
							Common.sqlChar(lndYear) + "," +
							Common.sqlChar(lineStr.substring(0,12)) + "," +
							Common.sqlChar("E"+lineStr.substring(12,26)) + "," +"\n"+
							Common.sqlChar(lineStr.substring(26,27)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(27,36))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(36,45))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(45,54))*0.01)) + "," +
							Common.sqlChar(Integer.toString(Integer.parseInt(lineStr.substring(54,65)))) + "," +"\n"+
							Common.sqlChar(lineStr.substring(65,66)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(66,75))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(75,84))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(84,93))*0.01)) + "," +
							Common.sqlChar(Integer.toString(Integer.parseInt(lineStr.substring(93,104)))) + "," +"\n"+
							Common.sqlChar(lineStr.substring(104,105)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(105,114))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(114,123))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(123,132))*0.01)) + "," +
							Common.sqlChar(Integer.toString(Integer.parseInt(lineStr.substring(132,143)))) + "," +"\n"+
							Common.sqlChar(lineStr.substring(143,144)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(144,153))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(153,162))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(162,171))*0.01)) + "," +
							Common.sqlChar(Integer.toString(Integer.parseInt(lineStr.substring(171,182)))) + "," +"\n"+
							Common.sqlChar(lineStr.substring(182,183)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(183,192))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(192,201))*0.01)) + "," +
							Common.sqlChar(Double.toString(Double.parseDouble(lineStr.substring(201,210))*0.01)) + "," +
							Common.sqlChar(Integer.toString(Integer.parseInt(lineStr.substring(210,221)))) + "," +"\n"+
							Common.sqlChar(lineStr.substring(221,261)) + "," +
							Common.sqlChar(lineStr.substring(261,277)) + "," +
							Common.sqlChar(lineStr.substring(277,278)) + "," +"\n"+
							Common.sqlChar(lineStr.substring(278,279))+ ")" ;
							//Common.sqlChar(getEditID()) + "," +
							//Common.sqlChar(getEditDate()) + "," +
							//Common.sqlChar(getEditTime()) + ")" ;
						db_a.exeSQL(a);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						db_a.closeAll();
					}
   				}
   				in.close();
   				if (counter>0 && errCount==0) {
   					actionMessage = sbHTML1.toString();
   					sbHTML1.append("</table>");
   					db.excuteSQL(sbSQL.toString().split(":;:"));
   					//String[] arrSQL = sbSQL.toString().split(":;:");
   					//for (i=0; i<arrSQL.length;i++) System.out.println(arrSQL[i]);
   				}else if(errCount>0) {
   					sbHTML.append("</table>");
   					actionMessage = sbHTML.toString();
   				}
   				util.Common.RemoveDirectory(new File(System.getProperty("java.io.tmpdir")+File.separator+uploadCaseID));   				
   			} catch (IOException e) {
  				out.println(e.getMessage());
	    	} finally {
	    		db.closeAll();
	    	}   		

        }else{
            actionResult = "doImportFail";
            actionMessage = "找不到匯入的檔案";            
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
<script language="javascript" src="../../js/validate.js"></script>
<script language="javascript" src="../../js/function.js"></script>
<script language="javascript">
var actionResult = '<%=actionResult%>';
var actionMessage = '<%=actionMessage%>';
var moshe;

function checkAfterAction(){    
    switch(actionResult){
    case 'noAction':
        break;
    case 'doImportSuccess':
    	var prop = "";    
	    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
	    prop=prop+'width=400,';
	    prop=prop+'height=110';	           
		moshe=window.open("","exp",prop); 
		moshe.close();
		moshe=window.open("","exp",prop); 		
		moshe.document.write('<html>');
		moshe.document.write('<head>');
		moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
		moshe.document.write('<title>.:: 資料傳輸成功 ::.</title>');
		moshe.document.write('</head>');
		moshe.document.write('<body topmargin="2" leftmargin="10" marginwidth="0" marginheight="0">\n');	
		moshe.document.write('<br><br><div align="center"><font color="#CC0000">' + actionMessage + '</font></div>'); 
		moshe.document.write('</body></html>');					                    
        break;
    case 'doImportFail':
        alert(actionMessage);
        break;
    }
}

function checkField(){
    if(form1.FILE.value == ""){
            alert("您必需指定檔案");
            return false;
    } else {
    	var extPos = form1.FILE.value.lastIndexOf(".");
    	var ext = form1.FILE.value.toLowerCase().substring(extPos+1);
    	var allowExt = "TXT,tdf";
    	if (extPos==-1) {
    		alert("無法判斷您上傳的檔案格式，請檢查檔案是否有副檔名並重新輸入!");
    		return false;
    	}
    	if (parse(allowExt).length>0) {
    		if (allowExt.search(ext)== -1 ) {
    			alert("上傳的檔案格式必須是tdf檔，請重新輸入!");
    			return false;
    		}
    	}    	
    }
    form1.actionType.value = "doUpload";
   	var prop = "";    
    prop=prop+'toolbar=no;location=no,directories=no,menubar=no,status=yes,scrollbars=yes,resizable=yes,';
    prop=prop+'width=400,';
    prop=prop+'height=110';	           
	moshe=window.open("","exp",prop); 
	moshe.document.write('<html>');
	moshe.document.write('<head>');
	moshe.document.write('<meta http-equiv=Content-Type content=text/html; charset=UTF-8>');			
	moshe.document.write('<title>.:: 資料匯入中 ::.</title>');
	moshe.document.write('</head>');
	moshe.document.write('<body topmargin="10" leftmargin="10" marginwidth="0" marginheight="0">\n');	
	moshe.document.write('<br><br><div align="center"><font color="#CC0000">資料匯入中，請稍候...</font></div>'); 
	moshe.document.write('</body></html>');					            
}

function cancelUpload(){
    window.close();
}

</script>
</head>

<body topmargin="5" onLoad="checkAfterAction();">
<form id="form1" name="form1"  method="post" enctype="multipart/form-data" onSubmit="return checkField();">
<table width="100%" cellspacing="0" cellpadding="0">
<!--Form區============================================================-->
<tr><td class="bg">
    <table class="table_form" width="100%" height="100%">
    <tr>
      <td colspan="2" class="td_form_white">** 傳輸的檔案限制為<%=(maxPostSize/1048576)%>MB，請勿超過。</td>
    </tr>
    <tr>
        <td class="td_form">資料年度：</td>
		<td class="td_form_white">
			<input class="field" type="text" name="lndYear" size="5" maxlength="5" >YYY，例:096
		</td>		
	</tr>    
    <tr>
        <td class="td_form">傳輸檔案路徑：</td>
        <td class="td_form_white">
           <input class="field" TYPE="file" name="FILE">
        </td>
    </tr>
    </table>
</td></tr>

<!--Toolbar區============================================================-->
<tr><td class="bg" style="text-align:center">
    <input type="hidden" name="uploadCaseID" value="<%=uploadCaseID%>" >        
    <input type="hidden" name="actionType" value="">
<input class="toolbar_default" type="submit" name="upload"	value="確定匯入">
</td></tr>

</table>
</form>
</body>
</html>
