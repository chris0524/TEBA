/*
*<br>程式目的：彙總QuerySQL 
*		用於：untgr005r財產分類量值統計季報表、untgr008r市有財產增減結存表、untgr009r國有財產增減結存表
*			 untgr019r財產目錄總表、「untgr021r珍貴動產、不動產增減結存表」、「untgr022r珍貴動產、不動產目錄總表」
*	 		 untgr006r市有財產增減表、untgr007r國有財產增減表、untgr020r「珍貴動產、不動產增減表」
*<br>程式代號：UNTGRQUERYSQL
*<br>撰寫日期：0950310
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import util.*;

public class UNTGRQUERYSQL {
	
	/**
	 * <br>目的：將傳入字串內含有" nextLine "轉換成"\n"以換行
	 * <br>參數：資料字串
	 * <br>傳回：換行過後的字串
	 */
	public static String nextLine(String originalString){
		String changeString="";
		if(!"".equals(originalString) && originalString!=null){
			if(originalString.indexOf("nextLine")>0 || originalString.equals("nextLine")) {
				while(originalString.indexOf("nextLine")>0 || originalString.equals("nextLine")){
					originalString = originalString.substring(0,originalString.indexOf("nextLine"))+"\n"+originalString.substring((originalString.indexOf("nextLine")+8)) ;
				}
			}
		}
		changeString = originalString;
	return changeString;
	}
	
	/**
	 程式：untgr016r 財產檢查單
	 傳入值：editID:異動人員editID
	**/
	public static String getUntGrQuerySqlUntgr016r(String editID) {
 	String execSQL= " select a.propertyKind||a.fundType propertyKindFundTypeNo, " +
				    " '財產性質分類：' || a.propertyKindName || '　' || a.fundTypeName propertyKindFundTypeName, " +
				 	" a.useStateName item, " +
				 	" ltrim(rtrim(to_char(a.amount,'999G999G999G999G990'))) amount, " +
				 	" ltrim(rtrim(to_char(a.holdArea/10000,'999G999G999G999G990D999999'))) superficial, " +
				 	" ltrim(rtrim(to_char(a.bookValue,'999G999G999G999G990'))) value " +
				 	" from UNTGR_untgr016r a " +
				 	" where a.editID=" + Common.sqlChar(editID) +
				 	" order by propertyKindFundTypeNo,a.useState " +
				   	"" ;				        
		return execSQL;
	}	
}


