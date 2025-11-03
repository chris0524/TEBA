package util;

import java.util.Calendar;

/**
 * 一些檢核函式
 * 主要是為資料上傳作業而寫的 : 這支不連結資料庫
 * 另一支連結資料庫的程式是UNTUP000Q，但它不是static type, 且使用它要非常小心(沒把握，請不要用)
 * 
 * @version 1.0, Jul 7, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class Validate {
	
	public static String checkDate(String obj,String cName){	
		if (Common.get(obj).equals("")) return "";
		
		if ((obj!=null) && (obj.trim().length()==7)) {
			obj = obj.trim();
			try {
				if (Double.isNaN(Double.parseDouble(obj))) return "資料"+obj+"型態不符！";;
			} catch (NumberFormatException e) {
				return "資料"+obj+"型態不符！";
			}						
			int y = Integer.parseInt(obj.substring(0,3));
			int m = Integer.parseInt(obj.substring(3,5));
			int d = Integer.parseInt(obj.substring(5));
			
			if (y<0 || y>999) {
				return cName + "年份格式不符!請輸入正確的民國日期(YYYMMDD)\n";
			}
			
			if (m<0 || m>12) {
				return cName + "月份格式不符!請輸入正確的民國日期(YYYMMDD)\n";
			}
			
			if (d<0 || d>31) {
				return cName + "日期格式不符!請輸入正確的民國日期(YYYMMDD)\n";
			} else {
				Calendar cal = Calendar.getInstance();				
				cal.set(y+ 1911, m-1, d);
				if((cal.get(Calendar.MONTH)!=(m-1)) || (cal.get(Calendar.DATE)!=d)){				
					return cName + "日期格式不符!請輸入正確的民國日期(YYYMMDD)\n";
				}
			}
			
		} else {
			return cName + "日期格式不符!請輸入正確的民國日期(YYYMMDD)\n";
		}
	    return "";
	}	
	
	public static boolean checkDate(String obj){	
		if ((obj!=null) && (obj.trim().length()==7)) {
			obj = obj.trim();
			try {
				if (Double.isNaN(Double.parseDouble(obj))) return false;
			} catch (NumberFormatException e) {
				return false;
			}						
			int y = Integer.parseInt(obj.substring(0,3));
			int m = Integer.parseInt(obj.substring(3,5));
			int d = Integer.parseInt(obj.substring(5));
			
			if (y<0 || y>999) return false;
			if (m<0 || m>12) return false;			
			
			if (d<0 || d>31) {
				return false;
			} else {
				Calendar cal = Calendar.getInstance();	
				
				//Java's Month Start with 0, therefor --> m-1
				cal.set(y+ 1911, m-1, d);
				if((cal.get(Calendar.MONTH)!=(m-1)) || (cal.get(Calendar.DATE)!=d)){					
					return false;
				}
			}			
		} else {
			return false;
		}
	    return true;
	}	
	
	/*
	 * 函數功能：檢查資料年月是否正確(YYYMM)
	 */
	public static boolean checkYYYMM(String s) {
		try {
			if (Double.isNaN(Double.parseDouble(s))) return false;
		} catch (NumberFormatException e) {
			return false;
		}
		if(s.length()!=5) return false;		
		int year=Integer.parseInt(s.substring(0,3));
		int month=Integer.parseInt(s.substring(3,5));	        
		if((year+1911)>3000 || (year+1911) < 1900) return false;
		else if(month > 12 || month < 1) {
			return false;
		}	    
	    return true;
	}
	
	/*
	 * 函數功能：檢查資料是否整數
	 */
	public static boolean checkInt(String s){
		if (s.equals("")) return true;
		try {
			if (Double.isNaN(Double.parseDouble(s))) return false;
		} catch (NumberFormatException e) {
			return false;
		}
		if( s.indexOf(".")>0){
			return false;
		}
	    return true;
	}
	
	/*
	 * 函數功能：檢查資料是否為浮點數
	 * 參　　數：s物件本身; I整數幾位; F小數點幾位
	 */
	public static boolean checkFloat(String s, int I, int F){
		if (s.equals("")) return true;
		try {
			if (Double.isNaN(Double.parseDouble(s))) return false;
		} catch (NumberFormatException e) {
			return false;
		}
		if( s.indexOf(".")==-1){
			if(s.length()> I) return false;
		} else {
			if(F==0) return false;			
			if(s.indexOf(".") > I) return false;			
			if(s.substring(s.indexOf(".")+1,s.length()).length() > F ) return false;			
		}
	    return true;
	}	
	
	public static boolean checkYN(String s) {
		if ("Y".equals(s)||"N".equals(s)) return true;
		else return false;
	}
	
	public static boolean checkAlphaInt(String s) {
		s = Common.get(s);
		for(int i=0;i<s.length();i++){
			char ch=s.charAt(i);
		    if((ch < 'A' || ch >'Z')&&(ch < 'a'||ch > 'z')&&(ch < '0' || ch >'9')) return false;
		}
		return true;
	}
	
	/**
	 * 檢查是否含有特殊字元 (主要是避免XSS前端網頁用的)
	 * true = 有含特殊字元
	 * false = 無特殊字元
	 * @param s
	 * @return
	 */
	public static boolean checkSpecialChar(String s) {
		if (s!=null) {
			String[] arrField = {"'","\"","&","%","?","<",">"};
			for(int i=0;i<arrField.length;i++){
				if (s.indexOf(arrField[i])>0) return true;
			}
		}		
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(checkDate("0961231"));
		System.out.println(checkInt("0961231"));
		System.out.println(checkFloat("0961231.x", 7, 2));	
		System.out.println(checkAlphaInt("0961231ABC"));
	}

}
