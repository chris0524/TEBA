/*
*<br>程式目的：顯示Html view之函數
*<br>程式代號：View
*<br>撰寫日期：94/05/10
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/

package util;

import java.sql.ResultSet;
import util.Database;
import java.util.*;
import util.report.*;


public class View {
	
    /**
     * <br>
     * <br>目的：組合html option語法函數 
     * <br>參數：(1)sql字串 (2)被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getOption(String sql, String selectedOne) {
        StringBuilder sb = new StringBuilder();
        sb.append("<option value=''>請選擇</option>");
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = Common.checkGet(rs.getString(1));
                String name = Common.checkGet(rs.getString(2));
                
                sb.append("<option value='" ).append( id ).append( "' ");
                if (selectedOne!= null && selectedOne.equals(id)) {
                   sb.append(" selected ");
                }
                sb.append(">").append(name).append("</option>");
            }
        } catch (Exception ex) {
            sb.append("<option value=''>查詢錯誤</option>");
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }
        return sb.toString();
    }
   
    /**
     * <br>
     * <br>目的：組合html single option語法函數 
     * <br>參數：(1)sql字串 (2)被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getSingleOption(String sql, String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = Common.checkGet(rs.getString(1));
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                if (selectedOne!= null && selectedOne.equals(id)) {
                    rtnStr = rtnStr + " selected ";
                }
                rtnStr = rtnStr + ">" + id + "</option>\n";
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        return rtnStr;
    }
    
    /**
     * <br>
     * <br>目的：組合html multiple option語法函數 
     * <br>參數：(1)sql字串 (2)指定的values ex:("110,220")
     * <br>傳回：加上html option element 
     */
    static public String getMultipleOption(String sql, String options) {
        StringBuilder sb = new StringBuilder();
        String[] values = options.split(",");
        sb.append("<option value=''>請選擇</option>");
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL(sql);
            while (rs.next()) {
                String id = Common.checkGet(rs.getString(1));
                String name = Common.checkGet(rs.getString(2));
                for(String value : values ){
                	if(id.equals(value)){
                        sb.append("<option value='" ).append( id ).append( "' ");
                        sb.append(">").append(name).append("</option>");                		
                	}
                }
            }
        } catch (Exception ex) {
            sb.append("<option value=''>查詢錯誤</option>");
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }
        return sb.toString();
    }
 
    /**
     * <br>
     * <br>目的：組合Yes or No option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getYNOption(String selectedOne) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<option value=''>請選擇</option>");
    	if ("Y".equals(selectedOne)){
    		rtnStr.append("<option value='Y' selected>是</option>");
    		rtnStr.append("<option value='N'>否</option>");
    	}else if ("N".equals(selectedOne)){
    		rtnStr.append("<option value='Y'>是</option>");
    		rtnStr.append("<option value='N' selected>否</option>");
    	}else{
    		rtnStr.append("<option value='Y'>是</option>");
    		rtnStr.append("<option value='N'>否</option>");   		
    	}
        return rtnStr.toString();
    }
  
    
    /**
     * <br>
     * <br>目的：組合ownership(權屬)option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getOnwerOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	//String rtnStr = "";
        Database db = new Database();
        try {
        	ResultSet rs = db.querySQL("select z.codeid, z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') order by z.codeid ");
            while (rs.next()) {
                String id = Common.checkGet(rs.getString(1));
                String name = Common.checkGet(rs.getString(2));
                
                rtnStr = rtnStr + "<option value='" + id + "' ";
                if (selectedOne!= null && selectedOne.equals(id)) {
                    rtnStr = rtnStr + " selected ";
                }
                rtnStr = rtnStr + ">" + name + "</option>\n";
            }
        } catch (Exception ex) {
            rtnStr = "<option value=''>查詢錯誤</option>";
            ex.printStackTrace();
        } finally {
 			db.closeAll();
        }        
        return rtnStr;
    }        
/*
    static public String getOnwerOption(String selectedOne) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<option value=''>請選擇</option>");
    	if ("1".equals(selectedOne)){
    		rtnStr.append("<option value='1' selected>市有</option>");
    		rtnStr.append("<option value='2'>國有</option>");
    	}else if ("2".equals(selectedOne)){
    		rtnStr.append("<option value='1'>市有</option>");
    		rtnStr.append("<option value='2' selected>國有</option>");
    	}else{
    		rtnStr.append("<option value='1'>市有</option>");
    		rtnStr.append("<option value='2'>國有</option>");   		
    	}
        return rtnStr.toString();
    }    
*/  
    
    /**
     * <br>
     * <br>目的：組合月份01~12 option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */    
    static public String getMonthOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	for (int i=1; i<13; i++) {
            rtnStr = rtnStr + "<option value='" + Common.formatFrontZero(""+i,2) + "' ";
            if (selectedOne!= null && selectedOne.equals(Common.formatFrontZero(Integer.toString(i),2))) {
                rtnStr = rtnStr + " selected ";
            }
            rtnStr = rtnStr + ">" + Common.formatFrontZero(""+i,2) + "</option>\n";    		
    	}
        return rtnStr;
    }        
    
    /**
     * <br>
     * <br>目的：組合小時0~23 option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */    
    static public String getHourOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	for (int i=0; i<24; i++) {
            rtnStr = rtnStr + "<option value='" + i + "' ";
            if (selectedOne!= null && selectedOne.equals(Integer.toString(i))) {
                rtnStr = rtnStr + " selected ";
            }
            rtnStr = rtnStr + ">" + i + "</option>\n";    		
    	}
        return rtnStr;
    }    
    
    /**
     * <br>
     * <br>目的：組合分鐘0~59 option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */    
    static public String getMinuteOption(String selectedOne) {
        String rtnStr = "<option value=''>請選擇</option>";
    	for (int i=0; i<60; i++) {
            rtnStr = rtnStr + "<option value='" + i + "' ";
            if (selectedOne!= null && selectedOne.equals(Integer.toString(i))) {
                rtnStr = rtnStr + " selected ";
            }
            rtnStr = rtnStr + ">" + i + "</option>\n";    		
    	}
        return rtnStr;
    }        
    
    
    /**
     * <br>
     * <br>目的：組合textOption option語法函數，分隔符號';'，第一個表value，第2個表option 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */
    static public String getTextOption(String textOption , String selectedOne) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<option value=''>請選擇</option>");
    	String[] stestOption = textOption.split(";");
    	for(int i=0 ; i<stestOption.length;i++){
    	    if(stestOption[i].equals(selectedOne))
    	        rtnStr.append("<option value='"+stestOption[i]+"' selected>"+stestOption[i+1]+"</option>");
    	    else
    	        rtnStr.append("<option value='"+stestOption[i]+"' >"+stestOption[i+1]+"</option>");
    	    i++;
    	}
        return rtnStr.toString();
    } 

    /**
     * <br>
     * <br>目的：組合popOrgan語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回機關代碼的欄位名稱 (3)inputValue 機關代碼的值 (4)chineseDesc機關代碼的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopOrgan2(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";
    	rtnStr.append("<input class=\""+ className +"\" type=\"hidden\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    	
    	if(!"".equals(ROStr))
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popOrgan2('"+inputName+"','"+inputName+"Name','N')\" value=\"...\" title=\"機關輔助視窗\">\n");
    	return rtnStr.toString();
    } 
        /**
     * <br>
     * <br>目的：組合popOrgan語法函數 ,少上層機關
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回機關代碼的欄位名稱 (3)inputValue 機關代碼的值 (4)chineseDesc機關代碼的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopOrgan3(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";
    	rtnStr.append("<input class=\""+ className +"\" type=\"hidden\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    	
    	if(!"".equals(ROStr))
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popOrgan3('"+inputName+"','"+inputName+"Name','N')\" value=\"...\" title=\"機關輔助視窗\">\n");
    	return rtnStr.toString();
    } 
    
    /**
     * <br>
     * <br>目的：組合popOrgan語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回機關代碼的欄位名稱 (3)inputValue 機關代碼的值 (4)chineseDesc機關代碼的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopOrgan(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";
    	rtnStr.append("<input class=\""+ className +"\" type=\"hidden\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+Common.checkGet(inputValue)+"\">\n");		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"25\" maxlength=\"50\" value=\""+Common.checkGet(chineseDesc)+"\">]\n");    	
    	if(!"".equals(ROStr))
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popOrgan('"+inputName+"','"+inputName+"Name','N')\" value=\"...\" title=\"機關輔助視窗\">\n");
    	return rtnStr.toString();
    } 
    
    /**
     * <br>
     * <br>目的：組合popOrgan語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回機關代碼的欄位名稱 (3)inputValue 機關代碼的值 (4)chineseDesc機關代碼的中文說明 (5)isLimit是否顯示全部機關名稱
     * <br>傳回：加上html option element 
     */
    static public String getPopOrgan(String className, String inputName, String inputValue, String chineseDesc,String isLimit) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";
    	rtnStr.append("<input class=\""+ className +"\" type=\"hidden\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+Common.checkGet(inputValue)+"\">\n");		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"25\" maxlength=\"50\" value=\""+Common.checkGet(chineseDesc)+"\">]\n");    	
    	if(!"".equals(ROStr))
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popOrgan('"+inputName+"','"+inputName+"Name','"+isLimit+"')\" value=\"...\" title=\"機關輔助視窗\">\n");
    	return rtnStr.toString();
    }    
     
    /**
     * <br>
     * <br>目的：組合PopProperty語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回財產編號的欄位名稱 (3)inputValue 財產編號的值 (4)chineseDesc財產編號的中文說明 (5)preWord財產編號的前置詞
     * <br>傳回：加上html option element 
     */
    static public String getPopProperty(String className, String inputName, String inputValue, String chineseDesc, String preWord) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	else if (className.indexOf("RO")>0) {
    		ROStr="";
    		rtnStr.append("[<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"10\" maxlength=\"11\" value=\""+Common.checkGet(inputValue)+"\">");
    		rtnStr.append("<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+Common.checkGet(chineseDesc)+"\">]\n");    		
    	} else {
    		rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"10\" maxlength=\"11\" value=\""+Common.checkGet(inputValue)+"\" onBlur=\"getProperty('"+inputName+"','"+inputName+"Name','"+preWord+"');\">\n");
        	if(!"".equals(ROStr)) rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popProperty('"+inputName+"','"+inputName+"Name','"+ preWord +"')\" value=\"...\" title=\"財產編號輔助視窗\">\n");    	
        	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+Common.checkGet(chineseDesc)+"\">]\n");    		
    	}
        return rtnStr.toString();
    } 
    
    
    /**
     * <br>
     * <br>目的：組合popStore語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回廠商編號的欄位名稱 (3)inputValue 廠商編號的值 (4)chineseDesc廠商編號的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopStoreNo(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"10\" maxlength=\"10\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popStore('"+inputName+"','"+inputName+"Name')\" value=\"...\" title=\"廠商輔助視窗\">\n");
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
        return rtnStr.toString();
    }     

    
    /**
     * <br>
     * <br>目的：組合popDocNo語法函數
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回文號編號的欄位名稱 (3)inputValue 文號編號的值 (4)chineseDesc文號編號的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopDocNo(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"5\" maxlength=\"5\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popDocNo('"+inputName+"','"+inputName+"Name')\" value=\"...\" title=\"文號輔助視窗\">\n");
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
        return rtnStr.toString();
    }  
    
    /**
     * <br>
     * <br>目的：組合popKeepUnit語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回保管單位編號的欄位名稱 (3)inputValue 保管單位編號的值 (4)chineseDesc保管單位編號的中文說明
     * <br>傳回：加上html option element 
     */
    static public String getPopKeepUnit(String className, String inputName, String inputValue, String chineseDesc) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";
    	if ("field".equals(className)) ROStr="_RO";
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"5\" maxlength=\"5\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popKeepUnit('"+inputName+"','"+inputName+"Name')\" value=\"...\" title=\"保管單位輔助視窗\">\n");
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
        return rtnStr.toString();
    }      
    /**
     * <br>
     * <br>目的：組合popCalndar語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回日期的欄位名稱 (3)inputValue 日期的值
     * <br>傳回：加上html option element 
     */
    static public String getPopCalndar(String className, String inputName, String inputValue) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"7\" maxlength=\"7\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popCalndar('"+inputName+"')\" value=\"...\" title=\"萬年曆輔助視窗\">\n");
        return rtnStr.toString();
    }
    
    static public String getPopCalndar(String className, String inputName, String inputValue, String onchangeJS) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"7\" maxlength=\"7\" value=\""+inputValue +"\"");
    	
    	if (!"".equals(Common.get(onchangeJS))) {
    		rtnStr.append(" onchange=\"").append(onchangeJS).append("\"");
       	}
    	rtnStr.append(">\n");
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popCalndar('"+inputName+"')\" value=\"...\" title=\"萬年曆輔助視窗\">\n");
        return rtnStr.toString();
    }
    
    /**
     * <br>
     * <br>目的：組合popCalndar2語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:傳回日期的欄位名稱 (3)inputValue 日期的值
     * <br>傳回：加上html option element 
     */
    static public String getPopCalndar2(String className, String inputName1, String inputName2, String inputName3, String inputValue) {
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName2+"\" size=\"7\" maxlength=\"7\" onblur=\"resetVerify();\" value=\""+inputValue+"\">\n");		 
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName2+"\" onclick=\"popCalndar2('"+inputName1+"','"+inputName2+"','"+inputName3+"');\" value=\"...\" title=\"萬年曆輔助視窗\">\n");
        return rtnStr.toString();
    }  

    /**
     * <br>
     * <br>目的：組合popUpload語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:檔案上傳欄位名稱 (3)inputValue 檔案名稱
     * <br>傳回：一個檔案上傳欄位, 上傳及下載檔案按鈕各一個
     * <br>2005/10/22
     */
    static public String getPopUpload(String className, String inputName, String inputValue) {
    	StringBuffer rtnStr = new StringBuffer();
    	String ROStr="RO";  
   	
    	if ("field".equals(className)) ROStr="_RO";
    	else if ("field_RO".equals(className)) ROStr="";		 
    	rtnStr.append("[<input class=\""+ className + ROStr + "\" type=\"text\" name=\""+inputName+"\" size=\"20\" maxlength=\"300\" value=\""+inputValue+"\">]\n");    	
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"openUploadWindow('"+inputName+"','"+inputValue+"',form1."+inputName+".value);\" value=\"上傳檔案\">\n");
    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"Download\" onclick=\"openDownloadWindow(form1."+inputName+".value);\" value=\"下載檔案\">\n");
    	//2010.01.09 shan add
    	//rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"clearFile\" onclick=\"(form1."+inputName+".value='');\" value=\"清除檔案\">\n");

    	rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"clearFile\" onclick=\"delectUpload(form1."+inputName+".value);(form1."+inputName+".value='');\" value=\"清除檔案\">\n");
    	
    	return rtnStr.toString();
    }   
    /**
     * <br>
     * <br>目的：組合getCause(增加原因)語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:檔案上傳欄位名稱 (3)inputValue 檔案名稱
     * <br>傳回：一個檔案上傳欄位, 上傳及下載檔案按鈕各一個
     * <br>2005/10/22
     */
    
    static public String getCause(String className, String inputName, String inputValue, String chineseDesc, String condition, String func) {
		StringBuffer rtnStr = new StringBuffer();
    	
		if("field_Q".equals(className)){
			rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"3\" value=\""+inputValue+"\" onChange=\"getCauseName('"+inputName+"','"+inputName+"Name');"+func+"\">\n");
	        rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popSysca_Code('"+inputName+"','"+inputName+"Name','增加原因','CAA','"+condition+"');\" value=\"...\" title=\"輔助視窗\">\n");    	
	        rtnStr.append("[<input class=\""+ className + "RO" + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
		}else{
	    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"3\" value=\""+inputValue+"\" onChange=\"getCauseName('"+inputName+"','"+inputName+"Name');"+func+"\">\n");
	        rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popSysca_Code('"+inputName+"','"+inputName+"Name','增加原因','CAA','"+condition+"');\" value=\"...\" title=\"輔助視窗\">\n");    	
	        rtnStr.append("[<input class=\""+ className + "_RO" + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");    		
		}
		
        return rtnStr.toString();
	}
    /**
     * <br>
     * <br>目的：組合getSourceKind(財產來源)語法函數 
     * <br>參數：(1)className:css的class名稱 (2)inputName:檔案上傳欄位名稱 (3)inputValue 檔案名稱
     * <br>傳回：一個檔案上傳欄位, 上傳及下載檔案按鈕各一個
     * <br>2005/10/22
     */
    
    static public String getSourceKind(String className, String inputName, String inputValue, String chineseDesc) {
		StringBuffer rtnStr = new StringBuffer();
    	
    	rtnStr.append("<input class=\""+ className +"\" type=\"text\" name=\""+inputName+"\" size=\"3\" maxlength=\"2\" value=\""+inputValue+"\" onChange=\"getSourceKindName('"+inputName+"','"+inputName+"Name');\">\n");
        rtnStr.append("<input class=\""+ className +"\" type=\"button\" name=\"btn_"+inputName+"\" onclick=\"popSysca_Code('"+inputName+"','"+inputName+"Name','財產來源','SKD');\" value=\"...\" title=\"輔助視窗\">\n");    	
        rtnStr.append("[<input class=\""+ className + "_RO" + "\" type=\"text\" name=\""+inputName+"Name\" size=\"20\" maxlength=\"50\" value=\""+chineseDesc+"\">]\n");
        
        return rtnStr.toString();
	}
    
    /**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html(系統公告頁用，超過一個月未入帳之筆數)
  	 * <br>參數：(1)列表集合
  	 * <br>傳回：傳回列表之html字串
  	*/
    public static String getQueryCount(ArrayList objList) {

    	StringBuffer rtnStr = new StringBuffer();    
    	StringBuffer sbQueryOne = new StringBuffer("");
    	
			String rowArray[]=new String[2];
			java.util.Iterator it=objList.iterator();
			rtnStr.append("<tr class='listTR'>");
			rtnStr.append("<td class='listTD'>超過一個月未入帳之筆數</td>\n");
			while(it.hasNext())
			{			
				rowArray= (String[])it.next();
				rtnStr.append("<td class='listTD'>")
					  .append("<a href=\"#\" onClick=\"popNoVerifyList(")
					  .append(rowArray[1]).append(")\">");
				rtnStr.append(rowArray[0]).append("</a></td>\n");
			}
			
			rtnStr.append("</tr>\n");
				
		return rtnStr.append(sbQueryOne).toString();     
    }    
    
    
    
  	/**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合 (4)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	*/
    public static String getQuerylist(boolean primaryArray[], boolean displayArray[], 
    		ArrayList objList, String queryAllFlag) {
    	int i;
    	int counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer();    
    	StringBuffer sbQueryOne = new StringBuffer("");
    	
    	String pk = "";
		if ("true".equals(queryAllFlag) && objList.size()==0)
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
//					sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
//				}				
				for(i=0;i<primaryArray.length;i++)
				{
					if (primaryArray[i])
					{
						if(trFlag){
							rtnStr.append(",'").append(Common.checkGet(rowArray[i])).append("'");
//							if (counter==1) {
//								sbQueryOne.append(",'").append(rowArray[i]).append("'");
//							}
						}else{
							rtnStr.append("'").append(Common.checkGet(rowArray[i])).append("'");
//							if (counter==1) {
//								sbQueryOne.append("'").append(rowArray[i]).append("'");
//							}	
							trFlag = true;
						}						
					}
				}
//				if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");
				rtnStr.append(")\" >\n");
	
				//顯示TD
				rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++)
				{
					if (displayArray[i])
					{
						rtnStr.append(" <td class='listTD' >").append(Common.checkGet(rowArray[i])).append("</td>\n");
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
			rtnStr.append(",-1);");

			rtnStr.append("	}");

			rtnStr.append("}");

			rtnStr.append("</script>");							
		
				//sb.append("<input type='hidden' name='listContainerRowDefault' id='listContainerRowDefault' value=\"" ).append( v ).append( "\">");

		}			
		return rtnStr.append(sbQueryOne).toString();     
    }    
    
  	/**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合對齊方式 (4)列表集合 (5)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	*/    
    public static String getQuerylist(boolean primaryArray[], boolean displayArray[], String[] arrAlign,
    		ArrayList objList, String queryAllFlag) {
    	int i, counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer(1000);
    	    	
		if ("true".equals(queryAllFlag) && objList.size()==0){
			rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;</td></tr>");
		}else{
			String rowArray[]=new String[primaryArray.length];
			java.util.Iterator it=objList.iterator();
			while(it.hasNext()) {			
				rowArray= (String[])it.next();
				counter++;
				//顯示TR
				rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");		
				for(i=0;i<primaryArray.length;i++){
					if (primaryArray[i]) {
						if (trFlag) {
							rtnStr.append(",'").append(Common.checkGet(rowArray[i])).append("'");
						} else {
							rtnStr.append("'").append(Common.checkGet(rowArray[i])).append("'");
							trFlag = true; 
						}						
					}
				}
				rtnStr.append(")\" >\n");
	
				//顯示TD
				rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++){
					if (displayArray[i]) rtnStr.append(" <td class='listTD' style=\"text-align:").append(arrAlign[i]).append("\">").append(Common.checkGet(rowArray[i])).append("</td>\n");					
				}				
				rtnStr.append("</tr>\n");
				trFlag = false;
			}
		}	
		return rtnStr.toString();     
    } 
    
    /**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合 (4)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	 * <br>自動載入 listContainerRowClick()
  	*/
    public static String getQuerylist2(boolean primaryArray[], boolean displayArray[], 
    		ArrayList objList, String queryAllFlag) {
    	int i;
    	int counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer();    
    	StringBuffer sbQueryOne = new StringBuffer("");
    	
    	String pk = "";
		if ("true".equals(queryAllFlag) && objList.size()==0)
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
//					sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
//				}				
				for(i=0;i<primaryArray.length;i++)
				{
					if (primaryArray[i])
					{
						if(trFlag){
							rtnStr.append(",'").append(Common.checkGet(rowArray[i])).append("'");
//							if (counter==1) {
//								sbQueryOne.append(",'").append(rowArray[i]).append("'");
//							}
						}else{
							rtnStr.append("'").append(Common.checkGet(rowArray[i])).append("'");
//							if (counter==1) {
//								sbQueryOne.append("'").append(rowArray[i]).append("'");
//							}	
							trFlag = true;
						}						
					}
				}
//				if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");
				rtnStr.append(")\" >\n");
	
				//顯示TD
				rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++)
				{
					if (displayArray[i])
					{
						rtnStr.append(" <td class='listTD' >").append(Common.checkGet(rowArray[i])).append("</td>\n");
					}
				}				
				rtnStr.append("</tr>\n");
				trFlag = false;
			}
			// 預設選取欄位
			rtnStr.append("<script language=\"javascript\">");
			rtnStr.append(" listContainerRowClickAuto();");
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
			rtnStr.append(",-1);");

			rtnStr.append("	}");

			rtnStr.append("}");

			rtnStr.append("</script>");							
		
				//sb.append("<input type='hidden' name='listContainerRowDefault' id='listContainerRowDefault' value=\"" ).append( v ).append( "\">");

		}			
		return rtnStr.append(sbQueryOne).toString();     
    }    
    
    /**
  	 * <br> 
  	 * <br>目的：組合查詢列表之html, No. 為資料之RowNum
  	 * <br>參數：(1)主鍵之index (2)顯示之index (3)列表集合 (4)是否有查詢旗標
  	 * <br>傳回：傳回列表之html字串
  	*/
    public static String getQuerylistWithRowNum(boolean primaryArray[], boolean displayArray[], ArrayList objList, String queryAllFlag) {
    	int i;
    	int counter=0;
    	boolean trFlag = false;
    	StringBuffer rtnStr = new StringBuffer();    
    	StringBuffer sbQueryOne = new StringBuffer("");
    	
    	String pk = "";
		if ("true".equals(queryAllFlag) && objList.size()==0)
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
//					sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
//				}				
				for(i=0;i<primaryArray.length;i++)
				{
					if (primaryArray[i])
					{
						if(trFlag){
							rtnStr.append(",'").append(Common.checkGet(rowArray[i])).append("'");
//							if (counter==1) {
//								sbQueryOne.append(",'").append(rowArray[i]).append("'");
//							}
						}else{
							rtnStr.append("'").append(Common.checkGet(rowArray[i])).append("'");
//							if (counter==1) {
//								sbQueryOne.append("'").append(rowArray[i]).append("'");
//							}	
							trFlag = true;
						}						
					}
				}
//				if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");
				rtnStr.append(")\" >\n");
	
				//顯示TD
				//rtnStr.append(" <td class='listTD' >").append(counter).append(".</td>\n");
				for(i=0;i<displayArray.length;i++)
				{
					if (displayArray[i])
					{
						rtnStr.append(" <td class='listTD' >").append(Common.checkGet(rowArray[i])).append("</td>\n");
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
			rtnStr.append(",-1);");

			rtnStr.append("	}");

			rtnStr.append("}");

			rtnStr.append("</script>");							
		
				//sb.append("<input type='hidden' name='listContainerRowDefault' id='listContainerRowDefault' value=\"" ).append( v ).append( "\">");

		}			
		return rtnStr.append(sbQueryOne).toString();     
    }
    
    
    /**
     * 取得page用的 query list,移除了一般query list NO 欄位 
     * @param primaryArray
     * @param displayArray
     * @param objList
     * @param queryAllFlag
     * @return
     */
    public static String getPageQuerylist(boolean primaryArray[], boolean displayArray[], 
            ArrayList objList, String queryAllFlag) {
    	
    	System.out.println("test");
        int i;
        int counter=0;
        boolean trFlag = false;
        StringBuffer rtnStr = new StringBuffer(); 
    	StringBuffer sbQueryOne = new StringBuffer("");        
        
        if ("true".equals(queryAllFlag) && objList.size()==0){
            //rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100' style='color:red'>查無資料，請您重新輸入查詢條件！</td></tr>");
            rtnStr.append(" <tr class='listTR' ><td class='listTD' colspan='100'>&nbsp;</td></tr>");
        }else{
            String rowArray[]=new String[primaryArray.length];
            java.util.Iterator it=objList.iterator();
            while(it.hasNext()){            
                rowArray= (String[])it.next();
                counter++;
                //顯示TR
                rtnStr.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(");
				if (counter==1) {
					sbQueryOne.append("<script type='text/javascript'>if (form1.state.value=='queryAllSuccess') { try { queryOne(");
				}				                
                for(i=0;i<primaryArray.length;i++){
                    if (primaryArray[i]){
                        if(trFlag){
                            rtnStr.append(",'"+rowArray[i]+"'");
							if (counter==1) {
								sbQueryOne.append(",'"+rowArray[i]+"'");
							}                            
                        }else{
                            rtnStr.append("'"+rowArray[i]+"'");
							if (counter==1) {
								sbQueryOne.append("'"+rowArray[i]+"'");
							}                            
                            trFlag = true;
                        }
                        
                    }
                }
                rtnStr.append(")\" >\n");
				if (counter==1) sbQueryOne.append("); } catch(e) {  }\n\n}</script>");                
                
                //顯示TD
                for(i=0;i<displayArray.length;i++){
                    if (displayArray[i]){
                        rtnStr.append(" <td class='listTD' >"+Common.get(rowArray[i])+"</td>\n");
                    }
                }               
                rtnStr.append("</tr>\n");
                trFlag = false;
            }
        }       
		return rtnStr.append(sbQueryOne).toString();     
    }            
    
    public static String getLookupField(String sSQL) {
    	String rStr = "";
    	Database db = new Database();
    	ResultSet rs;	
    	try {
    		rs = db.querySQL(sSQL);
    		if (rs.next()){
    			rStr = Common.checkGet(rs.getString(1));
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		db.closeAll();
    	}
    	return rStr;
    }
    
    
    /**
     * <br>
     * <br>目的：組合 縣市 or 鄉鎮市區 or 村里 option語法函數 
     * <br>參數：被選的value 
     * <br>傳回：加上html option element 
     */
    public static String getAddkind(String selectedOne){
    	StringBuffer rtnStr = new StringBuffer();
    	rtnStr.append("<option value=''>請選擇</option>");	
    	if ("1".equals(selectedOne)){
    		rtnStr.append("<option value='1' selected>縣市</option>");
    		rtnStr.append("<option value='2'>鄉鎮市區</option>");
    		rtnStr.append("<option value='3'>村里</option>");
    	}else if ("2".equals(selectedOne)){
    		rtnStr.append("<option value='1'>縣市</option>");
    		rtnStr.append("<option value='2' selected>鄉鎮市區</option>");
    		rtnStr.append("<option value='3'>村里</option>");
    	}else if ("3".equals(selectedOne)){
    		rtnStr.append("<option value='1'>縣市</option>");
    		rtnStr.append("<option value='2'>鄉鎮市區</option>");   
    		rtnStr.append("<option value='3' selected>村里</option>");
    	}else{
    		rtnStr.append("<option value='1'>縣市</option>");
    		rtnStr.append("<option value='2'>鄉鎮市區</option>");
    		rtnStr.append("<option value='3'>村里</option>");
    	}
        return rtnStr.toString();
    }
    
    /**
     * 共用代碼下拉選單
     * @param className
     * @param inputName
     * @param inputValue
     * @param codeID
     * @return
     */
	static public String _getSelectHTML(String className, String inputName, String inputValue, String codeID){
		return _getSelectHTML(className, inputName, inputValue, codeID, null);
    }
	
    /**
     * 共用代碼下拉選單
     * @param className
     * @param inputName
     * @param inputValue
     * @param codeID
     * @param styleWidth : 若顯示資料很多,會把畫面弄很大,所以輸入寛度能減少字
     * @return
     */
	public static String _getSelectHTML(String className, String inputName, String inputValue, String codeID, Integer styleWidth){
    	StringBuilder sb = new StringBuilder();
    	sb.append("<select class=\"" ).append( className ).append( "\" type=\"select\" name=\"" ).append( inputName ).append("\" >");
    	if (styleWidth!=null && Common.getInt(styleWidth)>0) {
    		sb.append(" style=\"").append(styleWidth).append("px\"");
    	}
    	sb.append(getOption("select codeID, codeName from SYSCA_Code where codeKindID='" + codeID + "' order by codeID", inputValue));
    	sb.append("</select>");    	
    	return sb.toString();
    }
	
    /**
     * 共用代碼下拉選單
     * @param className
     * @param inputName
     * @param inputValue
     * @param codeID
     * @param styleWidth : 若顯示資料很多,會把畫面弄很大,所以輸入寛度能減少字
     * @param whereStr: where條件
     * @return
     */
	public static String _getSelectHTML(String className, String inputName, String inputValue, String codeID, Integer styleWidth,String whereStr){
    	StringBuilder sb = new StringBuilder();
    	sb.append("<select class=\"" ).append( className ).append( "\" type=\"select\" name=\"" ).append( inputName ).append("\" >");
    	if (styleWidth!=null && Common.getInt(styleWidth)>0) {
    		sb.append(" style=\"").append(styleWidth).append("px\"");
    	}
    	sb.append(getOption("select codeID, codeName from SYSCA_Code where codeKindID='" + codeID + "' "+whereStr+" order by codeID", inputValue));
    	sb.append("</select>");    	
    	return sb.toString();
    }	
	
	//下拉選項_含enterOrg
	static public String _getSelectHTML_withEnterOrg(String className, String inputName, String inputValue, String codeID, String enterOrg){
    	String result = "";
    	result = "<select class=\"" + className + "\" type=\"select\" name=\"" + inputName + "\">" +
					getOption("select codeID, codeName from SYSCA_Code where codeKindID='" + codeID + "' and codeid in (select fundno from SYSCA_FUNDORGAN where enterorg = '" + enterOrg + "')", inputValue) +
    				"</select>";
    	
    	return result;
    }
	
	//下拉選項_含JavaScript Function
	static public String _getSelectHTML_withFunction(String className, String inputName, String inputValue, String codeID, String function){
    	String result = "";
    	result = "<select class=\"" + className + "\" type=\"select\" name=\"" + inputName + "\" onchange=\"" + function + "\">" +
					getOption("select codeID, codeName from SYSCA_Code where codeKindID='" + codeID + "'", inputValue) +
    			"</select>";
    	
    	return result;
    }
	
	/**
	 * 
	 * @param optionSQL  : 組件Options 的SQL, 會呼叫{@link View#getOption(optionSQL, defaultVal)}
	 * @param className  : 建議此Ctrl component Group 的className , ex:field,  field_Q
	 * @param formName   : 位於哪個form 底下 , 通常為form1
	 * @param selectName : select component 的 name
	 * @param quicklyInputName : quickly input component 的 name
	 * @param defaultVal : 預設的值
	 * @return
	 */
	static public String getSelectCtrlGroup(String optionSQL, String className, String formName, String selectName, String quicklyInputName, String defaultVal) {
		return View.getSelectCtrlGroup(optionSQL, className, formName, selectName, "", quicklyInputName, "", defaultVal);
	}
	
	/**
	 * 聯動機制 請改用 DataCouplingCtrl.js 控制 thx
	 * @param optionSQL       : 組件Options 的SQL, 會呼叫{@link View#getOption(optionSQL, defaultVal)}
	 * @param className       : 建議此Ctrl component Group 的className , ex:field,  field_Q
	 * @param formName        : 位於哪個form 底下 , 通常為form1
	 * @param selectName      : select component 的 name
	 * @param selectONChangeFN: select component onchange 的 function; 通常若有指定quickly input 則會擺放在 set quicklyInput 的後面, 
	 *                          盡量避免和其他會觸發onchange綁定的一起使用, 避免出現js 迴圈
	 * @param quicklyInputName: quickly input component 的 name
	 * @param quicklyInputONChangeFN : quickly input onchange 的 function, 會擺放在 set select value 的後面
	 * @param defaultVal      : 預設值
	 * @return
	 */
	static public String getSelectCtrlGroup(String optionSQL, String className, String formName, String selectName, 
			                                String selectONChangeFN, String quicklyInputName, String quicklyInputONChangeFN, 
			                                String defaultVal) {
		StringBuilder htmlBuilder = new StringBuilder();
		if (!"".equals(quicklyInputName)) {
			htmlBuilder.append("<input type='text' class='").append(className).append("' size='5' name='").append(quicklyInputName).append("'");			 
			//.append(" onchange='").append(formName).append(".").append(selectName).append(".value=this.value;")
			//.append(quicklyInputONChangeFN).append("'");
			
			if (!"".equals(defaultVal)) {
				htmlBuilder.append(" value='").append(defaultVal).append("'");
			}
			htmlBuilder.append(">");
		}
		
		htmlBuilder.append("<select class='").append(className).append("' type='select' name='").append(selectName).append("'");
		if (!"".equals(quicklyInputName) || !"".equals(selectONChangeFN)) {
			//htmlBuilder.append(" onchange='");
			//if (!"".equals(quicklyInputName)) {
			//	htmlBuilder.append(formName).append(".").append(quicklyInputName).append(".value=this.value;");
			//}
			//htmlBuilder.append(selectONChangeFN).append("'");
		}
		htmlBuilder.append(">").append(View.getOption(optionSQL, defaultVal)).append("</select>");
		return htmlBuilder.toString();
	}
	
    public static String getJasperReportFormatOption(String selectedOne) {
        return getJasperReportFormatOption(selectedOne, 1, "");
    }
    
    public static String getJasperReportFormatOption(String selectedOne, int defaultChoice) {
    	return getJasperReportFormatOption(selectedOne, defaultChoice, "");
    }
    
    public static String getJasperReportFormatOption(String selectedOne, int defaultChoice, String specformat) {     
        StringBuilder sb = new StringBuilder();
        if (defaultChoice==1) sb.append("<option value=''>請選擇</option>");
        else if (defaultChoice==2) sb.append("<option value=''></option>");  
    	String[] formatId = new String[]{ReportEnvironment.VAL_FORMAT_PDF,
    			ReportEnvironment.VAL_FORMAT_XLSX,
    			ReportEnvironment.VAL_FORMAT_DOCX,
    			ReportEnvironment.VAL_FORMAT_ODS,
    			ReportEnvironment.VAL_FORMAT_ODT
    			};
    	String[] formatName = {"PDF","Excel","Word","ODS","ODT"};
    	String[] specFormatArray = specformat.split(",");
        try {
    		for (int i=0; i<formatId.length; i++) {
    			String format = formatId[i];
    			boolean found = specFormatArray.length>0 && specformat.length()>0 ?Arrays.stream(specFormatArray)
                        .anyMatch(x -> x.equalsIgnoreCase(format)):true;
    			if (found){
	                sb.append("<option value='").append(formatId[i]).append("' ");
	                if (selectedOne!= null && selectedOne.equals(formatId[i])) {
	                    sb.append(" selected ");
	                }
	                sb.append(">").append(formatName[i]).append("</option>\n");  
    			}
    		}        	
        } catch (Exception ex) {
            sb.append("<option value=''>查詢錯誤</option>");
            ex.printStackTrace();
        }
        return sb.toString();
    }	
}