package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.io.*;

public class Generate {
    
    public static FileWriter createLog(String dirName,String fileName) {
        FileWriter fsout=null;
        File dirFile = new File(dirName);
        if (!dirFile.exists()) {
          dirFile.mkdir();
        }
        File file = new File(dirFile, fileName);
        try {
          fsout = new FileWriter(file);
        } catch (FileNotFoundException ex) {
          System.out.println("建立檔案失敗 FileNotFoundException "+ex.toString());
        } catch (Exception ex) {
           System.out.println("建立檔案失敗 "+ex.toString());
        }
        return fsout;
    }
    
	public static void main(String[] args) {
		try{
		String progDesc  = "保管使用人資料";		
		String progCode  = "sysca004f";
		String programer = "clive.chang";
		String tableName = "UNTMP_Keeper";
		String dataile	 = "D:/lomboz/workspace/kfcp/clive/sysca005f.txt";		
		FileWriter fw1 = createLog("sys/ca/",progCode+".jsp");
		FileWriter fw2 = createLog("WEB-INF/src/sys/ca/",progCode.toUpperCase()+".java");
		
		//{"英文名稱","欄位長度","是否主鍵","中文名稱","欄位型態","SQL語法","不允許空白","檢查函數","輔助函數","查詢欄位","查詢清單"},
		String [][] data;
		
		//讀取資料檔
		String lineStr="";
		int lineNum=0;
		try{
			BufferedReader in = new BufferedReader(new FileReader(dataile));

			while ((lineStr = in.readLine()) != null) {
					if (!"end".equals(lineStr.trim()))
						lineNum++;
					else
						break;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}			
		data=new String[lineNum-1][];
		try{			
			lineNum=0;
			BufferedReader in = new BufferedReader(new FileReader(dataile));
			while ((lineStr = in.readLine()) != null) {
				if (!"end".equals(lineStr.trim()))
					lineNum++;
				else
					break;
				if (lineNum>1){
					data[lineNum-2]=lineStr.split("\t");
				}
			}			
//			for(int k=0;k<data.length;k++){
//				for(int j=0;j<data[k].length;j++){
//					System.out.print(data[k][j]+",");
//				}
//				System.out.print("\n");
//			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}			
	
		
		String tempStr="";		
		//檢查是否有檔案上傳欄位
		boolean isUpload = false;
		for(int i=0;i<data.length;i++){
			if ("popUpload".equals(data[i][4])){
				isUpload = true;				
			}
		}	
		
		//產生程式log
		writer(fw1,"<!--");
		writer(fw1,"程式目的："+progDesc);
		writer(fw1,"程式代號："+progCode);
		writer(fw1,"程式日期："+getYYYMMDD());
		writer(fw1,"程式作者："+programer);
		writer(fw1,"--------------------------------------------------------");
		writer(fw1,"修改作者　　修改日期　　　修改目的");
		writer(fw1,"--------------------------------------------------------");
		writer(fw1,"-->");
		writer(fw1,"");
		
		
		//產生程式jsp head
		String className=progCode.substring(0,3)+"."+progCode.substring(3,5)+"."+progCode.toUpperCase();
		writer(fw1,"<%@ page contentType=\"text/html;charset=big5\" %>");
		writer(fw1,"<%@ include file=\"../../home/head.jsp\" %>");
		writer(fw1,"<jsp:useBean id=\"obj\" scope=\"request\" class=\""+className+"\">");
		writer(fw1,"	<jsp:setProperty name=\"obj\" property=\"*\"/>");
		writer(fw1,"</jsp:useBean>");
		writer(fw1,"<jsp:useBean id=\"objList\" scope=\"page\" class=\"java.util.ArrayList\"/>");
		writer(fw1,"");
		
		
		//產生程式action
		writer(fw1,"<%");
		writer(fw1,"if (\"queryAll\".equals(obj.getState())) {");
		writer(fw1,"	if (\"false\".equals(obj.getQueryAllFlag())){obj.setQueryAllFlag(\"true\"); }");
		writer(fw1,"}else if (\"queryOne\".equals(obj.getState())) {");
		writer(fw1,"	obj = ("+className+")obj.queryOne();	");
		writer(fw1,"}else if (\"insert\".equals(obj.getState()) || \"insertError\".equals(obj.getState())) {");
		writer(fw1,"	obj.insert();");
		writer(fw1,"}else if (\"update\".equals(obj.getState()) || \"updateError\".equals(obj.getState())) {");
		writer(fw1,"	obj.update();");
		writer(fw1,"}else if (\"delete\".equals(obj.getState()) || \"deleteError\".equals(obj.getState())) {");
		writer(fw1,"	obj.delete();");
		writer(fw1,"}");
		writer(fw1,"if (\"true\".equals(obj.getQueryAllFlag())){");
		writer(fw1,"	objList = obj.queryAll();");
		writer(fw1,"}");
		writer(fw1,"%>");
		writer(fw1,"");
		
		
		//產生程式html head
		writer(fw1,"<html>");
		writer(fw1,"<head>");
		writer(fw1,"<meta http-equiv=\"Content-Language\" content=\"zh-tw\"/>");
		writer(fw1,"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=big5\"/>");
		writer(fw1,"<meta http-equiv=\"Expires\" content=\"-1\"/>");
		writer(fw1,"<meta http-equiv=\"pragma\" content=\"no-cache\"/>");
		writer(fw1,"<meta http-equiv=\"Cache-control\" content=\"no-cache\"/>");
		writer(fw1,"<link rel=\"stylesheet\" href=\"../../js/default.css\" type=\"text/css\">");
		writer(fw1,"<script language=\"javascript\" src=\"../../js/validate.js\"></script>");
		writer(fw1,"<script language=\"javascript\" src=\"../../js/function.js\"></script>");
		writer(fw1,"<script language=\"javascript\" src=\"../../js/tablesoft.js\"></script>");
		
		
		//產生程式javascript check
		writer(fw1,"<script language=\"javascript\">");
		writer(fw1,"var insertDefault;	//二維陣列, 新增時, 設定預設值");
		//產出checkField function
		writer(fw1,"function checkField(){");	
		writer(fw1,"	var alertStr=\"\";");
		writer(fw1,"	if(form1.state.value==\"queryAll\"){" );
		writer(fw1,"		alertStr += checkQuery();");
		writer(fw1,"	}else if(form1.state.value==\"insert\"||form1.state.value==\"insertError\"||form1.state.value==\"update\"||form1.state.value==\"updateError\"){");
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][6]))
				writer(fw1,"		alertStr += checkEmpty(form1."+data[i][0]+",\""+data[i][3]+"\");");
			if (!"".equals(data[i][7]))
				if ("checkFloat".equals(data[i][7]))
					writer(fw1,"		alertStr += "+data[i][7]+"(form1."+data[i][0]+",\""+data[i][3]+"\","+data[i][1]+",1);");
				else if ("checkLen".equals(data[i][7]))
					writer(fw1,"		alertStr += "+data[i][7]+"(form1."+data[i][0]+",\""+data[i][3]+"\"," + data[i][1] +");");
				else
					writer(fw1,"		alertStr += "+data[i][7]+"(form1."+data[i][0]+",\""+data[i][3]+"\");");

		}
		writer(fw1,"	}");		
		writer(fw1,"	if(alertStr.length!=0){ alert(alertStr); return false; }");
		writer(fw1,"	beforeSubmit();");
		writer(fw1,"}");		
		//產出queryOne function
		tempStr="";
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2])){
				if ("".equals(tempStr))
					tempStr=data[i][0];
				else
					tempStr=tempStr+","+data[i][0];
			}
		}
		writer(fw1,"function queryOne("+tempStr+"){");
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]))
				writer(fw1,"	form1."+data[i][0]+".value="+data[i][0]+";");
		}
		writer(fw1,"	form1.state.value=\"queryOne\";");
		writer(fw1,"	beforeSubmit();");
		writer(fw1,"	form1.submit();");
		writer(fw1,"}");
		writer(fw1,"</script>");
		writer(fw1,"</head>");
		writer(fw1,"");
		writer(fw1,"<body topmargin=\"0\" onLoad=\"whatButtonFireEvent('<%=obj.getState()%>');showErrorMsg('<%=obj.getErrorMsg()%>');\">");
		writer(fw1,"<form id=\"form1\" name=\"form1\" method=\"post\" onSubmit=\"return checkField()\">");
		writer(fw1,"");
		
		
		//產生程式query區
		writer(fw1,"<!--Query區============================================================-->");
		writer(fw1,"<div id=\"queryContainer\" style=\"width:400px;height:400px;display:none\">");
		writer(fw1,"	<iframe id=\"queryContainerFrame\"></iframe>");
		writer(fw1,"	<div class=\"queryTitle\">查詢視窗</div>");
		writer(fw1,"	<table class=\"queryTable\"  border=\"1\">");		
		for(int i=0;i<data.length;i++){			
			if (!"".equals(data[i][9])){
				
				getInputType(fw1,true,data[i],i);
				/*
				
				writer(fw1,"	<tr>");
				writer(fw1,"		<td class=\"queryTDLable\"  width=\"30%\">"+data[i][3]+"：</td>");
				writer(fw1,"		<td class=\"queryTDInput\">");
				if ("TE".equals(data[i][4])){
					writer(fw1,"			<input class=\"field_Q\" type=\"text\" name=\"q_"+data[i][0]+"\" size=\""+data[i][1]+"\" maxlength=\""+data[i][1]+"\" value=\"<%=obj.getQ_"+data[i][0]+"()%>\">");
				}else if ("CB".equals(data[i][4])){
					writer(fw1,"			<input class=\"field_Q\" type=\"checkbox\" name=\"q_"+data[i][0]+"\" value=\"Y\" <%=obj.getQ_" +data[i][0]+"().equals(\"Y\")?\"checked\":\"\"%> >");
				}else if ("SE".equals(data[i][4])){
					writer(fw1,"			<select class=\"field_Q\" type=\"select\" name=\"q_"+data[i][0]+"\">");
					if (data[i][5].length()>3){
						writer(fw1,"			<%=util.View.getOption("+data[i][5]+", obj.getQ_"+data[i][0]+"())%>");
					}else if (data[i][5].length()==3){
						
					}
					writer(fw1,"			</select>");
				}else if ("TA".equals(data[i][4])){
				}else if ("SEYN".equals(data[i][4])){
					writer(fw1,"			<select class=\"field_Q\" type=\"select\" name=\"q_"+data[i][0]+"\">");
					writer(fw1,"			<%=util.View.getYNOption(obj.getQ_"+data[i][0]+"())%>");
					writer(fw1,"			</select>");
				}else if ("SEOWNER".equals(data[i][4])){
					writer(fw1,"			<select class=\"field_Q\" type=\"select\" name=\"q_"+data[i][0]+"\">");
					writer(fw1,"			<%=util.View.getOnwerOption(obj.getQ_"+data[i][0]+"())%>");
					writer(fw1,"			</select>");
				}else if ("popCalndar".equals(data[i][4])){
				}else if ("popOrgan".equals(data[i][4])){					
				}	
				writer(fw1,"		</td>");
				writer(fw1,"	</tr>");
				*/
			}
		}
		writer(fw1,"	<tr>");
		writer(fw1,"		<td class=\"queryTDInput\" colspan=\"2\" style=\"text-align:center;\">");
		writer(fw1,"			<input class=\"toolbar_default\" followPK=\"false\" type=\"submit\" name=\"querySubmit\" value=\"確　　定\" >");
		writer(fw1,"			<input class=\"toolbar_default\" followPK=\"false\" type=\"button\" name=\"queryCannel\" value=\"取　　消\" onClick=\"whatButtonFireEvent(this.name)\">");
		writer(fw1,"		</td>");
		writer(fw1,"	</tr>");
		writer(fw1,"	</table>");
		writer(fw1,"</div>");
		writer(fw1,"");
		
		
		//產生程式Form區		
		writer(fw1,"<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");
		writer(fw1,"<!--Form區============================================================-->");		
		writer(fw1,"<tr><td class=\"bg\">");
		writer(fw1,"	<div id=\"formContainer\">");
		writer(fw1,"	<table class=\"table_form\" width=\"100%\" height=\"100%\">");
		int h=0;
		for(h=0;h<data.length;h++){
			getInputType(fw1,false,data[h],h);
			/*
			if (i%2==0) 
				writer(fw1,"	<tr>");
			//產出欄位中文名稱
			if (!"".equals(data[i][6]))
				tempStr="<font color=\"red\">*</font>";
			else
				tempStr="";
			writer(fw1,"		<td class=\"td_form\">&nbsp;"+tempStr+data[i][3]+"：</td>");
			writer(fw1,"		<td class=\"td_form_white\">");
				
			//產出欄位
			if (!"".equals(data[i][2]))
				tempStr="field_P";
			else
				tempStr="field";
			if ("TE".equals(data[i][4])){
				writer(fw1,"			<input class=\""+tempStr+"\" type=\"text\" name=\""+data[i][0]+"\" size=\""+data[i][1]+"\" maxlength=\""+data[i][1]+"\" value=\"<%=obj.get"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"()%>\">");
				if (!"".equals(data[i][8])){
					if ("popCalndar".equals(data[i][8])){
						writer(fw1,"			<input class=\""+tempStr+"\" type=\"button\" name=\"btn_"+data[i][0]+"\" onclick=\"popCalndar('"+data[i][0]+"')\" value=\"...\" title=\"萬年曆輔助視窗\">");			
					}else if ("popOrgan".equals(data[i][8])){
						writer(fw1,"			<input class=\""+tempStr+"\" type=\"button\" name=\"btn_"+data[i][0]+"\" onclick=\"popOrgan('"+data[i][0]+"','"+data[i][0]+"Name')\" value=\"...\" title=\"機關輔助視窗\">");
						writer(fw1,"			[<input class=\""+tempStr+"RO\" type=\"text\" name=\""+data[i][0]+"Name\" size=\"32\" maxlength=\"30\" value=\"<%=obj.get"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"Name()%>\">]");
					}
				}
			}else if ("CB".equals(data[i][4])){
				writer(fw1,"			<input class=\""+tempStr+"\" type=\"checkbox\" name=\""+data[i][0]+"\" value=\"Y\" <%=obj.get" +data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"().equals(\"Y\")?\"checked\":\"\"%> >");
			}else if ("SE".equals(data[i][4])){
				writer(fw1,"			<select class=\""+tempStr+"\" type=\"select\" name=\""+data[i][0]+"\">");
				if (data[i][5].length()>3){
					writer(fw1,"			<%=util.View.getOption("+data[i][5]+", obj.get"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"())%>");
				}else if (data[i][5].length()==3){
					
				}
				writer(fw1,"			</select>");
			}else if ("SEYN".equals(data[i][4])){
				writer(fw1,"			<select class=\""+tempStr+"\" type=\"select\" name=\""+data[i][0]+"\">");
				writer(fw1,"			<%=util.View.getYNOption(obj.get"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"())%>");
				writer(fw1,"			</select>");
			}else if ("SEOWNER".equals(data[i][4])){
				writer(fw1,"			<select class=\""+tempStr+"\" type=\"select\" name=\""+data[i][0]+"\">");
				writer(fw1,"			<%=util.View.getOnwerOption(obj.get"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"())%>");
				writer(fw1,"			</select>");				
			}else if ("TA".equals(data[i][4])){
			}		
			writer(fw1,"		</td>");
			if (i%2==1) 
				writer(fw1,"	</tr>");
				*/			
		}
		
/*		if (h%2==0) 
		writer(fw1,"	</tr>");
		writer(fw1,"	<tr>");
		writer(fw1,"		<td class=\"td_form\">異動人員/日期：</td>");
		writer(fw1,"		<td class=\"td_form_white\">");
		writer(fw1,"			[<input class=\"field_RO\" type=\"text\" name=\"editID\" size=\"10\" value=\"<%=obj.getEditID()%>\">/");
		writer(fw1,"			<input class=\"field_RO\" type=\"text\" name=\"editDate\" size=\"7\" value=\"<%=obj.getEditDate()%>\">]");
		writer(fw1,"		</td>");
		writer(fw1,"	</tr>");
		writer(fw1,"	</table>");
		writer(fw1,"	</div>");
		writer(fw1,"</td></tr>");
		writer(fw1,"");*/		
		
		if (h%2==1) {
			writer(fw1,"		<td class=\"td_form\">異動人員/日期：</td>");
			writer(fw1,"		<td class=\"td_form_white\">");
			writer(fw1,"			[<input class=\"field_RO\" type=\"text\" name=\"editID\" size=\"10\" value=\"<%=obj.getEditID()%>\">/");
			writer(fw1,"			<input class=\"field_RO\" type=\"text\" name=\"editDate\" size=\"7\" value=\"<%=obj.getEditDate()%>\">]");
			writer(fw1,"		</td>");
			writer(fw1,"	</tr>");			
		} else {
			writer(fw1,"	<tr>");
			writer(fw1,"		<td class=\"td_form\">異動人員/日期：</td>");
			writer(fw1,"		<td class=\"td_form_white\">");
			writer(fw1,"			[<input class=\"field_RO\" type=\"text\" name=\"editID\" size=\"10\" value=\"<%=obj.getEditID()%>\">/");
			writer(fw1,"			<input class=\"field_RO\" type=\"text\" name=\"editDate\" size=\"7\" value=\"<%=obj.getEditDate()%>\">]");
			writer(fw1,"		</td>");
			writer(fw1,"		<td class=\"td_form\">&nbsp;</td>");
			writer(fw1,"		<td class=\"td_form_white\">&nbsp;</td>");		
			writer(fw1,"	</tr>");						
		}
		writer(fw1,"	</table>");
		writer(fw1,"	</div>");
		writer(fw1,"</td></tr>");
		writer(fw1,"");

		
		//產生程式Toolbar區		
		writer(fw1,"<!--Toolbar區============================================================-->");	
		writer(fw1,"<tr><td class=\"bg\" style=\"text-align:center\">");
		if (isUpload) writer(fw1,"	<input type=\"hidden\" name=\"filestoreLocation\" value=\"<%=application.getInitParameter(\"filestoreLocation\")%>\">");
		writer(fw1,"	<input type=\"hidden\" name=\"state\" value=\"<%=obj.getState()%>\">");		
		writer(fw1,"	<input type=\"hidden\" name=\"queryAllFlag\" value=\"<%=obj.getQueryAllFlag()%>\">");	
		writer(fw1,"	<input type=\"hidden\" name=\"userID\" value=\"<%=user.getUserID()%>\">");
		writer(fw1,"	<jsp:include page=\"../../home/toolbar.jsp\" />");		
		writer(fw1,"</td></tr>");		
		writer(fw1,"");		
		
		
		//產生程式List區		
		writer(fw1,"<!--List區============================================================-->");
		writer(fw1,"<tr><td class=\"bg\">");		
		writer(fw1,"<div id=\"listContainer\">");		
		writer(fw1,"<table class=\"table_form\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">");		
		writer(fw1,"	<thead id=\"listTHEAD\">");		
		writer(fw1,"	<tr>");		
		writer(fw1,"		<th class=\"listTH\" ><a class=\"text_link_w\" >NO.</a></th>");
		int counter=1; 
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][10])){
				writer(fw1,"		<th class=\"listTH\"><a class=\"text_link_w\" onclick=\"return sortTable('listTBODY',"+counter+",false);\" href=\"#\">"+data[i][3]+"</a></th>");
				counter++;
			}
		}
		writer(fw1,"	</tr>");		
		writer(fw1,"	</thead>");		
		writer(fw1,"	<tbody id=\"listTBODY\">");		
		
		String primaryStr="";
		String displayStr="";				
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]) && !"".equals(data[i][10])){
				primaryStr=primaryStr+"true,";
				displayStr=displayStr+"true,";
			}else if (!"".equals(data[i][2]) && "".equals(data[i][10])){
				primaryStr=primaryStr+"true,";
				displayStr=displayStr+"false,";	
			}else if ("".equals(data[i][2]) && !"".equals(data[i][10])){
				primaryStr=primaryStr+"false,";
				displayStr=displayStr+"true,";					
			}
		}		
		primaryStr=primaryStr.substring(0,primaryStr.length()-1);
		displayStr=displayStr.substring(0,displayStr.length()-1);
		writer(fw1,"	<%");
		writer(fw1,"	boolean primaryArray[] = {"+primaryStr+"};");
		writer(fw1,"	boolean displayArray[] = {"+displayStr+"};");
		writer(fw1,"	out.write(util.View.getQuerylist(primaryArray,displayArray,objList,obj.getQueryAllFlag()));");
		writer(fw1,"	%>");
		writer(fw1,"	</tbody>");
		writer(fw1,"</table>");
		writer(fw1,"</div>");
		writer(fw1,"</td></tr>");
		writer(fw1,"</table>");
		writer(fw1,"</form>");
		writer(fw1,"</body>");
		writer(fw1,"</html>");
		writer(fw1,"");	
		writer(fw1,"");	
		writer(fw1,"");
		
		//writer(fw1,"****************************************************************************************");
		//writer(fw1,"*以下為java bean之程式碼*");
		//writer(fw1,"****************************************************************************************");
		
		//產生程式log
		writer(fw2,"/*");
		writer(fw2,"*<br>程式目的："+progDesc);
		writer(fw2,"*<br>程式代號："+progCode);
		writer(fw2,"*<br>程式日期："+getYYYMMDD());
		writer(fw2,"*<br>程式作者："+programer);
		writer(fw2,"*<br>--------------------------------------------------------");
		writer(fw2,"*<br>修改作者　　修改日期　　　修改目的");
		writer(fw2,"*<br>--------------------------------------------------------");
		writer(fw2,"*/");
		writer(fw2,"");	
		
		//產生程式package and import
		writer(fw2,"package "+progCode.substring(0,3)+"."+progCode.substring(3,5)+";");	
		writer(fw2,"");	
		writer(fw2,"import java.sql.ResultSet;");	
		writer(fw2,"import java.util.ArrayList;");	
		writer(fw2,"import util.*;");
		if (isUpload) writer(fw2,"import java.io.File;");
		
		writer(fw2,"");	
		writer(fw2,"public class "+progCode.toUpperCase()+" extends SuperBean{");	
		writer(fw2,"");	
		writer(fw2,"");	
		
		//產生程式bena field , get and set method
		for(int i=0;i<data.length;i++){
			writer(fw2,"String "+data[i][0]+";");
			if ("popOrgan".equals(data[i][4]) || "popProperty".equals(data[i][4])){
				writer(fw2,"String "+data[i][0]+"Name;");
			}
		}
		if (isUpload) writer(fw2,"String filestoreLocation;");
		writer(fw2,"");	
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][9])){
				writer(fw2,"String q_"+data[i][0]+";");
				if ("popOrgan".equals(data[i][4]) || "popProperty".equals(data[i][4])){
					writer(fw2,"String q_"+data[i][0]+"Name;");
				}
			}
		}
		writer(fw2,"");	
		for(int i=0;i<data.length;i++){
			writer(fw2,"public String get"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"(){ return checkGet("+data[i][0]+"); }");
			writer(fw2,"public void set"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"(String s){ "+data[i][0]+"=checkSet(s); }");
			if ("popOrgan".equals(data[i][4]) || "popProperty".equals(data[i][4])){
				writer(fw2,"public String get"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"Name(){ return checkGet("+data[i][0]+"Name); }");
				writer(fw2,"public void set"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"Name(String s){ "+data[i][0]+"Name=checkSet(s); }");
			}			
		}
		if (isUpload) {
			writer(fw2,"public String getFilestoreLocation(){ return checkGet(filestoreLocation); }");
			writer(fw2,"public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }");			
		}
		writer(fw2,"");	
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][9])){
				writer(fw2,"public String getQ_"+data[i][0]+"(){ return checkGet(q_"+data[i][0]+"); }");
				writer(fw2,"public void setQ_"+data[i][0]+"(String s){ q_"+data[i][0]+"=checkSet(s); }");
				if ("popOrgan".equals(data[i][4]) || "popProperty".equals(data[i][4])){
					writer(fw2,"public String getQ_"+data[i][0]+"Name(){ return checkGet(q_"+data[i][0]+"Name); }");
					writer(fw2,"public void setQ_"+data[i][0]+"Name(String s){ q_"+data[i][0]+"Name=checkSet(s); }");					
				}
			}
		}		
		writer(fw2,"");	
		writer(fw2,"");	
		
		//傳回執行insert前之檢查sql
		writer(fw2,"//傳回執行insert前之檢查sql");	
		writer(fw2,"protected String[][] getInsertCheckSQL(){");	
		writer(fw2,"	String[][] checkSQLArray = new String[1][4];");	
		writer(fw2," 	checkSQLArray[0][0]=\" select count(*) as checkResult from "+tableName+" where 1=1 \" + ");	
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]))
				writer(fw2,"		\" and "+data[i][0]+" = \" + Common.sqlChar("+data[i][0]+") + ");
		}
		writer(fw2,"		\"\";");
		writer(fw2,"	checkSQLArray[0][1]=\">\";");	
		writer(fw2,"	checkSQLArray[0][2]=\"0\";");	
		writer(fw2,"	checkSQLArray[0][3]=\"該筆資料己重複，請重新輸入！\";");	
		writer(fw2,"	return checkSQLArray;");	
		writer(fw2,"}");	
		writer(fw2,"");	
		writer(fw2,"");
		
	  	//傳回insert sql　
		writer(fw2,"//傳回insert sql");	
		writer(fw2,"protected String[] getInsertSQL(){");	
		writer(fw2,"	String[] execSQLArray = new String[1];");
		writer(fw2,"	execSQLArray[0]=\" insert into "+tableName+"(\" +");	
		for(int i=0;i<data.length;i++){
			writer(fw2,"			\" "+data[i][0]+",\"+");
		}		
		writer(fw2,"			\" editID,\"+");
		writer(fw2,"			\" editDate,\"+");
		writer(fw2,"			\" editTime\"+");
		writer(fw2,"		\" )Values(\" +");
		for(int i=0;i<data.length;i++){
			writer(fw2,"			Common.sqlChar("+data[i][0]+") + \",\" +");
		}		
		writer(fw2,"			Common.sqlChar(getEditID()) + \",\" +");
		writer(fw2,"			Common.sqlChar(getEditDate()) + \",\" +");
		writer(fw2,"			Common.sqlChar(getEditTime()) + \")\" ;");
		writer(fw2,"	return execSQLArray;");	
		writer(fw2,"}");
		writer(fw2,"");	
		writer(fw2,"");		
		
	  	// 傳回update sql
		writer(fw2,"//傳回update sql");	
		writer(fw2,"protected String[] getUpdateSQL(){");		
		writer(fw2,"	String[] execSQLArray = new String[1];");	
		writer(fw2,"	execSQLArray[0]=\" update "+tableName+" set \" +");		
		for(int i=0;i<data.length;i++){
			if ("".equals(data[i][2]))
				writer(fw2,"			\" "+data[i][0]+" = \" + Common.sqlChar("+data[i][0]+") + \",\" +");
		}		
		writer(fw2,"			\" editID = \" + Common.sqlChar(getEditID()) + \",\" +");
		writer(fw2,"			\" editDate = \" + Common.sqlChar(getEditDate()) + \",\" +");
		writer(fw2,"			\" editTime = \" + Common.sqlChar(getEditTime()) + ");	
		writer(fw2,"		\" where 1=1 \" + ");		
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]))
				writer(fw2,"			\" and "+data[i][0]+" = \" + Common.sqlChar("+data[i][0]+") +");
		}		
		writer(fw2,"			\"\";");		
		writer(fw2,"	return execSQLArray;");	
		writer(fw2,"}");
		writer(fw2,"");	
		writer(fw2,"");	
		
		//傳回delete sql
		writer(fw2,"//傳回delete sql");	
		writer(fw2,"protected String[] getDeleteSQL(){");		
		writer(fw2,"	String[] execSQLArray = new String[1];");	
		writer(fw2,"	execSQLArray[0]=\" delete "+tableName+" where 1=1 \" +");		
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]))
				writer(fw2,"			\" and "+data[i][0]+" = \" + Common.sqlChar("+data[i][0]+") +");			
		}		
		writer(fw2,"			\"\";");

		//若有上傳檔案欄位, 刪除該筆資料時一併刪除其相關檔案目錄
		if (isUpload) {
			writer(fw2,"	//刪除檔案");	
			writer(fw2,"	String[] arrFileName=null;");			
			for(int i=0;i<data.length;i++){		
				if ("popUpload".equals(data[i][4])) {		
					writer(fw2,"	if (!\"\".equals(Common.get("+data[i][0]+"))) {");
					writer(fw2,"		arrFileName="+data[i][0]+".split(\":;:\");");
					writer(fw2,"		if (arrFileName.length>1) Common.RemoveDirectory(new File(filestoreLocation+File.separator+arrFileName[0]));");
					writer(fw2,"	}");												
				}	
			}
		}
		
		writer(fw2,"	return execSQLArray;");	
		writer(fw2,"}");
		writer(fw2,"");	
		writer(fw2,"");			

		//傳回queryone
		writer(fw2,"/**");	
		writer(fw2," * <br>");	
		writer(fw2," * <br>目的：依主鍵查詢單一資料");	
		writer(fw2," * <br>參數：無");	
		writer(fw2," * <br>傳回：傳回本物件");	
		writer(fw2,"*/");	
		writer(fw2,"");	
		writer(fw2,"public "+progCode.toUpperCase()+"  queryOne() throws Exception{");	
		writer(fw2,"	Database db = new Database();");	
		writer(fw2,"	"+progCode.toUpperCase()+" obj = this;");	
		writer(fw2,"	try {");	
		tempStr=" select";
		for(int i=0;i<data.length;i++){
			tempStr=tempStr+" a."+data[i][0]+",";
		}			
		tempStr=tempStr+" a.editID,";
		tempStr=tempStr+" a.editDate,";
		tempStr=tempStr+" a.editTime ";
		writer(fw2,"		String sql=\""+tempStr+" \"+");			
		writer(fw2,"			\" from "+tableName+" a where 1=1 \" +");			
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]))
				writer(fw2,"			\" and a."+data[i][0]+" = \" + Common.sqlChar("+data[i][0]+") +");
		}		
		writer(fw2,"			\"\";");		
		writer(fw2,"		ResultSet rs = db.querySQL(sql);");			
		writer(fw2,"		if (rs.next()){");			
		for(int i=0;i<data.length;i++){
			writer(fw2,"			obj.set"+data[i][0].substring(0,1).toUpperCase()+data[i][0].substring(1,data[i][0].length())+"(rs.getString(\""+data[i][0]+"\"));");	
		}
		writer(fw2,"			obj.setEditID(rs.getString(\"editID\"));");	
		writer(fw2,"			obj.setEditDate(rs.getString(\"editDate\"));");	
		writer(fw2,"			obj.setEditTime(rs.getString(\"editTime\"));");	
		
		writer(fw2,"		}");			
		writer(fw2,"		setStateQueryOneSuccess();");	
		writer(fw2,"	} catch (Exception e) {");			
		writer(fw2,"		e.printStackTrace();");	
		writer(fw2,"	} finally {");			
		writer(fw2,"		db.closeAll();");			
		writer(fw2,"	}");	
		writer(fw2,"	return obj;");			
		writer(fw2,"}");	
		writer(fw2,"");			
				
		//傳回queryAll
		writer(fw2,"/**");	
		writer(fw2," * <br>");	
		writer(fw2," * <br>目的：依查詢欄位查詢多筆資料");	
		writer(fw2," * <br>參數：無");	
		writer(fw2," * <br>傳回：傳回ArrayList");	
		writer(fw2,"*/");	
		writer(fw2,"");	
		writer(fw2,"public ArrayList queryAll() throws Exception{");	
		writer(fw2,"	Database db = new Database();");	
		writer(fw2,"	ArrayList objList=new ArrayList();");	
		writer(fw2,"	int counter=0;");	
		
		
		writer(fw2,"	try {");	
		tempStr=" select";
		counter=0;
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]) || !"".equals(data[i][10])){
				tempStr=tempStr+" a."+data[i][0]+",";
				counter++;
			}
		}			
		writer(fw2,"		String sql=\""+tempStr.substring(0,tempStr.length()-1)+" \"+");			
		writer(fw2,"			\" from "+tableName+" a where 1=1 \"; ");	
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][9])){
				writer(fw2,"			if (!\"\".equals(getQ_"+data[i][0]+"()))");
				writer(fw2,"				sql+=\" and a."+data[i][0]+" = \" + Common.sqlChar(getQ_"+data[i][0]+"()) ;");
			}
		}		
		writer(fw2,"		ResultSet rs = db.querySQL(sql);");			
		writer(fw2,"		while (rs.next()){");	
		writer(fw2,"			counter++;");
		writer(fw2,"			String rowArray[]=new String["+counter+"];");
		counter=0;
		for(int i=0;i<data.length;i++){
			if (!"".equals(data[i][2]) || !"".equals(data[i][10])){
				writer(fw2,"			rowArray["+counter+"]=Common.get(rs.getString(\""+data[i][0]+"\")); ");
				counter++;
			}
		}			
		writer(fw2,"			objList.add(rowArray);");	
		writer(fw2,"			if (counter==getListLimit()){");	
		writer(fw2,"				setErrorMsg(getListLimitError());");	
		writer(fw2,"				break;");	
		writer(fw2,"			}");	
		writer(fw2,"		}");			
		writer(fw2,"		setStateQueryAllSuccess();");	
		writer(fw2,"	} catch (Exception e) {");			
		writer(fw2,"		e.printStackTrace();");	
		writer(fw2,"	} finally {");			
		writer(fw2,"		db.closeAll();");			
		writer(fw2,"	}");	
		writer(fw2,"	return objList;");			
		writer(fw2,"}");	
		writer(fw2,"");			
		writer(fw2,"}");			
		writer(fw2,"");			
		writer(fw2,"");	

		fw1.close();
		fw2.close();
		System.out.println("產生完成");
    	} catch(IOException e) {
    	    e.printStackTrace();
    	} 		
	}
	
	public static void writer(FileWriter fw,String s){ 
	    try{
	    //System.out.println(s);
	    fw.write(s+"\n");
    	} catch(IOException e) {
    	    e.printStackTrace();
    	}   
	}
	
    public static String getYYYMMDD() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR) - 1911;
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);        
        if (y<=99){ sb.append("0"); }
        sb.append(Integer.toString(y));
        if (m<=9){ sb.append("0"); }
        sb.append(Integer.toString(m));
        if (d<=9){ sb.append("0"); }
        sb.append(Integer.toString(d));
        return sb.toString();
    }	
    
	public static String getData(String lineStr, int col){
		
		String cutchar="\t";
		String tempData="";
		String returnData="";
		int colstart=0;
		int colend=0;
		for(int i=0; i<(col-1); i++){
			colstart=lineStr.indexOf(cutchar,colstart);
			colstart++;
		}
		//System.out.println(colstart);
		colstart++;						
		colend=lineStr.indexOf(cutchar,colstart);
		//System.out.println(colend);
		if(colend<0){ colend=0; }		
		tempData=lineStr.substring(colstart-1,colend).trim();
		for(int i=0; i<tempData.length(); i++){
			if ((!tempData.substring(i,(i+1)).equals(" ")) && 
				(!tempData.substring(i,(i+1)).equals("\""))){
					returnData=returnData+tempData.substring(i,(i+1));
			}
		}
		return returnData;
	}
	
	public static void getInputType(FileWriter fw1,boolean isQuery, String[] data, int counter){	
	    String className="";
		String inputName="";
		String getName="";
		String chinaName="";
		
		if (isQuery){
			className="field_Q";
			inputName="q_"+data[0];
			getName="Q_"+data[0];
			chinaName=data[3];
			 

			
			if(counter==0){
				writer(fw1,"	<tr>");
				writer(fw1,"		<td class=\"queryTDLable\" width=\"20%\">"+chinaName+"：</td>");
				writer(fw1,"		<td class=\"queryTDInput\" width=\"30%\">");
			}else{
				writer(fw1,"	<tr>");
				writer(fw1,"		<td class=\"queryTDLable\">"+chinaName+"：</td>");
				writer(fw1,"		<td class=\"queryTDInput\">");
			}			
		}else{
			if (!"".equals(data[2]))
				className="field_P";
			else
				className="field";
			inputName=data[0];
			getName=data[0].substring(0,1).toUpperCase()+data[0].substring(1,data[0].length());
			//產出欄位中文名稱
			if (!"".equals(data[6]))
				chinaName="<font color=\"red\">*</font>"+data[3];
			else
				chinaName=data[3];
			
			if (counter%2==0) 
				writer(fw1,"	<tr>");
			if(counter==0){
			    writer(fw1,"		<td class=\"td_form\" width=\"16%\">"+chinaName+"：</td>");
			    writer(fw1,"		<td class=\"td_form_white\" width=\"32%\">");
			}else{
			    writer(fw1,"		<td class=\"td_form\">"+chinaName+"：</td>");
			    writer(fw1,"		<td class=\"td_form_white\">");			
			}
		}

		if ("TE".equals(data[4])){
			writer(fw1,"			<input class=\""+className+"\" type=\"text\" name=\""+inputName+"\" size=\""+data[1]+"\" maxlength=\""+data[1]+"\" value=\"<%=obj.get"+getName+"()%>\">");
		}else if ("PRO".equals(data[4])||"QRO".equals(data[4])||"RO".equals(data[4])){
			writer(fw1,"			[<input class=\"field_"+data[4]+"\" type=\"text\" name=\""+inputName+"\" size=\""+data[1]+"\" maxlength=\""+data[1]+"\" value=\"<%=obj.get"+getName+"()%>\">]");			
		}else if ("CB".equals(data[4])){
			writer(fw1,"			<input class=\""+className+"\" type=\"checkbox\" name=\""+inputName+"\" value=\"Y\" <%=obj.get" +getName+"().equals(\"Y\")?\"checked\":\"\"%> >");
		}else if ("SE".equals(data[4])){
			writer(fw1,"			<select class=\""+className+"\" type=\"select\" name=\""+inputName+"\">");
			if (data[5].length()>3){
				writer(fw1,"			<%=util.View.getOption("+data[5]+", obj.get"+getName+"())%>");
			}else if (data[5].length()==3){
				writer(fw1,"			<%=util.View.getOption(\"select codeID, codeName from SYSCA_Code where codeKindID='"+data[5]+"' \", obj.get"+getName+"())%>");
			}
			writer(fw1,"			</select>");			
		}else if ("TA".equals(data[4])){
			writer(fw1,"			<textarea class=\""+className+"\" name=\""+inputName+"\" cols=\"24\" rows=\"4\"><%=obj.get"+getName+"()%></textarea>");			
		}else if ("SEYN".equals(data[4])){
			writer(fw1,"			<select class=\""+className+"\" type=\"select\" name=\""+inputName+"\">");
			writer(fw1,"			<%=util.View.getYNOption(obj.get"+getName+"())%>");
			writer(fw1,"			</select>");			
		}else if ("SEOWNER".equals(data[4])){
			writer(fw1,"			<select class=\""+className+"\" type=\"select\" name=\""+inputName+"\">");
			writer(fw1,"			<%=util.View.getOnwerOption(obj.get"+getName+"())%>");
			writer(fw1,"			</select>");			
		}else if ("popCalndar".equals(data[4])){
			writer(fw1,"			<%=util.View.getPopCalndar(\""+className+"\",\""+inputName+"\",obj.get"+getName+"())%>");
		}else if ("popOrgan".equals(data[4])){
			writer(fw1,"			<%=util.View.getPopOrgan(\""+className+"\",\""+inputName+"\",obj.get"+getName+"(),obj.get"+getName+"Name())%>");
		}else if ("popProperty".equals(data[4])){
			writer(fw1,"			<%=util.View.getPopProperty(\""+className+"\",\""+inputName+"\",obj.get"+getName+"(),obj.get"+getName+"Name()");
			if (data[5].length()>0){
				writer(fw1,",\"" + data[5] + "\")%>");
			}else{
				writer(fw1,",\"1\")%>");
			}
		}else if ("popUpload".equals(data[4])) {
			writer(fw1,"			<%=util.View.getPopUpload(\""+className+"\",\""+inputName+"\",obj.get"+getName+"())%>");			
		}
			
		if (isQuery){
			writer(fw1,"		</td>");
			writer(fw1,"	</tr>");
		}else{
			writer(fw1,"		</td>");
			if (counter%2==1)
				writer(fw1,"	</tr>");
		}		

	}
	
}
