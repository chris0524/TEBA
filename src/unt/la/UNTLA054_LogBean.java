/*
 * 2013.14.27
 * 
 * 可自動產出Log訊息
 *
 */

package unt.la;

import org.apache.log4j.Logger;

import util.Common;

import java.lang.StackTraceElement;

public class UNTLA054_LogBean{
	
}

class LogBean{
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	//可使用方法
	private static boolean showMethodName=false;
		
	public static void outputLogInfo(){				setLoggerOutput("INFO", "");}
	public static void outputLogInfo(String str){	setLoggerOutput("INFO", str);}
	
	
	public static void outputLogDebug(){			setLoggerOutput("DEBUG", "");}
	public static void outputLogDebug(String str){	setLoggerOutput("DEBUG", str);}
	
	
	public static void outputLogError(){			setLoggerOutput("ERROR", "");}
	public static void outputLogError(String str){	setLoggerOutput("ERROR", str);}
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	static Logger logger = Logger.getLogger("");
	
	private static String SourceName ;
	private static String MethodName ;
	
	
	private static void getMessage(){
		StackTraceElement[] eles =  Thread.currentThread().getStackTrace();
		int itemp=0;
		
		for(int i=0;i<eles.length;i++){
			if(showMethodName){	System.out.println(i + " : " + eles[i].getMethodName());}
			
			try{
				if("execSQLToDB_ForSingleColumn".equals(eles[i].getMethodName())){	itemp = i+2;								
				}else if("execSQL_ForNoResult".equals(eles[i].getMethodName())){	itemp = i+1;					
				}else if("_jspService".equals(eles[i].getMethodName())){			itemp = i-1;		
				}
				
				if(itemp==0){
					
				}else{
					SourceName = eles[itemp].getClassName();
					MethodName = eles[itemp].getMethodName();
					if(showMethodName){}else{break;}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}				
		eles = null;
	}
	
	
	
	private static void setLoggerOutput(String type, String str){
		getMessage();
		
		String outputStr="";
		if("".equals(Common.get(str))){	outputStr = SourceName + " => " + MethodName;
		}else{				outputStr = SourceName + " => " + MethodName + " : \n" + str;
		}
		
		if("INFO".equals(type)){			logger.info(outputStr);	
		}else if("DEBUG".equals(type)){		logger.debug(outputStr);			
		}else if("ERROR".equals(type)){		logger.error(outputStr);			
		}
		outputStr = null;
	}
		
}



