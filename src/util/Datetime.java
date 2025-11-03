/*
*<br>程式目的：資料庫共用函數
*<br>程式代號：Database
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者: wells　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;



public class Datetime {
	
	/**
	 * <br>
	 * <br>目的：取得系統日期(民國)
	 * <br>參數：無
	 * <br>傳回：傳回字串YYYMMDD
	*/
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
    
    /**
	 * <br>
	 * <br>目的：取得系統日期(西元)
	 * <br>參數：無
	 * <br>傳回：傳回字串YYYYMMDD
	*/
    public static String getYYYYMMDD() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
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
    
    public static String getYYYMMDD(Date s) {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.setTime(s);
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
    
	/**
	 * <br>
	 * <br>目的：取得系統年月(民國)
	 * <br>參數：無
	 * <br>傳回：傳回字串YYYMM
	*/
	public static String getYYYMM(){
		return (getYYYMMDD().substring(0,5));
	}
	
	/**
	 * <br>
	 * <br>目的：取得系統年月(西元)
	 * <br>參數：無
	 * <br>傳回：傳回字串YYYYMM
	*/
	public static String getYYYYMM(){
		return (getYYYYMMDD().substring(0,6));
	}
	
	/**
	 * <br>
	 * <br>目的：取得系統年(民國)
	 * <br>參數：無
	 * <br>傳回：傳回字串YYY
	*/
	public static String getYYY(){
		return (getYYYMMDD().substring(0,3));
	}
	
	/**
	 * <br>
	 * <br>目的：取得系統年(西元)
	 * <br>參數：無
	 * <br>傳回：傳回字串YYYY
	*/
	public static String getYYYY(){
		return (getYYYYMMDD().substring(0,4));
	}
	
	/**
	 * <br>
	 * <br>目的：取得系統月日
	 * <br>參數：無
	 * <br>傳回：傳回字串MMDD
	*/
	public static String getMMDD(){
		return (getYYYMMDD().substring(3,7));
	}
	
	/**
	 * <br>
	 * <br>目的：取得系統月
	 * <br>參數：無
	 * <br>傳回：傳回字串MM
	*/
	public static String getMM(){
		return (getYYYMMDD().substring(3,5));
	}	

	/**
	 * <br>
	 * <br>目的：取得系統時間
	 * <br>參數：無
	 * <br>傳回：傳回字串HHMMSS
	*/
    public static String getHHMMSS() {
        Calendar calendar;
        String hh, mi, ss;
        calendar = Calendar.getInstance();
        hh = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) ;
        mi = String.valueOf(calendar.get(Calendar.MINUTE)) ;
        ss = String.valueOf(calendar.get(Calendar.SECOND)) ;
        if( hh.length() == 1) 
            hh = '0' + hh;
        if( mi.length() == 1) 
            mi = '0' + mi;
        if( ss.length() == 1) 
            ss = '0' + ss;   
        return hh + mi + ss;    
    }
  	

	/**
	 * <br>
	 * <br>目的：取得系統時間
	 * <br>參數：無
	 * <br>傳回：傳回字串HHMM
	*/
    public static String getHHMM() {
		return (getHHMMSS().substring(0,4));       
    }
 	
	
    //  *********************************************
	//  函數功能：某一日期加上一定期間的日或月或年
	//  參　　數：sType可以是d(Day),m(Month),y(Year); sNum數值; sdate為日期
	//  傳 回 值：傳回加上特定期間之後的日期
	//  *********************************************    
    public static String getDateAdd(String sType, int sNum, String sdate) {
     	return NewDateUtil.plusDate(sdate, sType, sNum, NewDateUtil.DateFormat.YYYMMDD);
    }    
    
//  *********************************************
	//  函數功能：某一時間加上一定期間的時或分或秒
	//  參　　數：sType可以是h(Hour),m(minute),s(second); sNum數值; sdate為日期
	//  傳 回 值：傳回加上特定期間之後的時間
	//  *********************************************    
    public static String getTimeAdd(String sType, int sNum, String stime) {
    	String rStr="";
    	DecimalFormat df = new DecimalFormat("00");
     	if( Common.get(stime).length()==6){    
     		int h = Integer.parseInt(stime.substring(0,2));
     		int m = Integer.parseInt(stime.substring(2,4));
     		int s = Integer.parseInt(stime.substring(4));
        	if (Common.get(sType).equals("h")) {
        		h = h + sNum;
        		if(h > 24){
        			h = (h -24);
        		}
        	} else if (Common.get(sType).equals("m")) {
        		m = m + sNum;
        		if(m >= 60){
        			h = h + (m /60);
        			m = m - (60 * (m /60));
        		}
        	} else {
        		s = s + 60;
        		if(s >= 60){
        			m = m + (s/60);
        			s = s - (60 * (s /60));
        		}
        		if(m >= 60){
        			h = h + (m /60);
        			m = m - (60 * (m /60));
        		}
        	}	    	
            StringBuffer sb = new StringBuffer();
            sb.append(df.format(h));
            sb.append(df.format(m));
            sb.append(df.format(s));
            rStr = sb.toString();
     	}
     	return rStr;
    }    
	
	
	//下列為kavin
	
	private static String getCurrentDate(String format)
	{
		if ("".equals(format)) format="yyyyMMdd";
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		java.util.Date currentDate = new java.util.Date();
		return formatter.format(currentDate);
	}
	
	/*
	 *算出year/month年月當月天數
	 */
	public static long getDays(String year,String month)
	{
		boolean valid=false;
		int days=31;
		 
		Calendar calendar = Calendar.getInstance();			
		while(valid!=true)
		{
			int yy=Integer.parseInt(year)+1911;
			int mm=Integer.parseInt(month);
			calendar.set(yy,mm-1,days);
			int y=calendar.get(Calendar.YEAR);
			int m=calendar.get(Calendar.MONTH)+1;
			int d=calendar.get(Calendar.DATE);
			if ((yy!=y) || (mm!=m) || (days!=d))
			{
				valid=false;
				days--;
			}
			else
			{
				valid=true;
			}
		}
		return days;
	}
	
  	/**
  	 * <br>
  	 * <br>目的：2個日期的相差天數　
  	 * <br>參數：String sdate 起日期,String edate 訖日期
  	 * <br>傳回：傳回long
  	*/
    public static long DateSubtraction(String sdate,String edate) {
		GregorianCalendar g1 = new GregorianCalendar();
		GregorianCalendar g2 = new GregorianCalendar();
		g1.set(Integer.parseInt(sdate.substring(0,3))+1911,Integer.parseInt(sdate.substring(3,5))-1,Integer.parseInt(sdate.substring(5)));        
		g2.set(Integer.parseInt(edate.substring(0,3))+1911,Integer.parseInt(edate.substring(3,5))-1,Integer.parseInt(edate.substring(5)));
		Date d1 = g1.getTime();
		Date d2 = g2.getTime();
		long daterange = d2.getTime() - d1.getTime();
        long time = 1000*3600*24; //A day in milliseconds        
        return (daterange/time);
    }
    
    /**
     * <br>
  	 * <br>目的：西元年與民國年轉換
  	 * <br>參數：String sYYYY ,ex:日期 String type ,ex:日期種類(1民國年,2西元年)
  	 * <br>傳回：傳回日期
     */
    
    public static String changeTaiwan(String sYear,String type){
    	Calendar calendar = Calendar.getInstance();	
    	StringBuffer sb = new StringBuffer();
    	int year = 0;
    	String rStr="";
    	if ("1".equals(type)){
    		year = Integer.parseInt(sYear)-1911;
    	}else if("2".equals(type)){
    		year = Integer.parseInt(sYear)+1911;
    	}
        sb.append(Integer.toString(year));
        rStr = sb.toString();
    	return rStr;
    }
    
    /**
     * <br>
  	 * <br>目的：西元年月與民國年月轉換
  	 * <br>參數：String sdate ,ex:日期 String type ,ex:日期種類(1民國年月,2西元年月)
  	 * <br>傳回：傳回年月
     */
    public static String changeTaiwanYYMM(String sdate,String type){
    	Calendar calendar = Calendar.getInstance();	
    	StringBuffer sb = new StringBuffer();
    	String rStr="";
    	int year=0,mm=0;
    	if(StringUtils.isNotEmpty(sdate)){
    		if ("1".equals(type)){
        	    year = Integer.parseInt(sdate.substring(0,4))-1911;
        		mm = Integer.parseInt(sdate.substring(4,6));
        	}else if("2".equals(type)){
        		year = Integer.parseInt(sdate.substring(0,3))+1911;
        		mm = Integer.parseInt(sdate.substring(3,5));
        	}    	        
        	
        	if (year<=99){ sb.append("0"); }
    		sb.append(Integer.toString(year));
    		if (mm<=9){ sb.append("0"); }
    		sb.append(Integer.toString(mm));
    		rStr = sb.toString();
    	}
    	
        
    	return rStr;
    }
    
    
    /**
     * <br>
  	 * <br>目的：西元年與民國年加月日轉換
  	 * <br>參數：String sdate ,ex:日期 String type ,ex:日期種類(1民國年,2西元年)
  	 * <br>傳回：傳回日期
     */
    public static String changeTaiwanYYMMDD(String sdate,String type){
    	Calendar calendar = Calendar.getInstance();	
    	StringBuffer sb = new StringBuffer();
    	String rStr="";
    	int year=0,mm=0,dd=0;
    	if(StringUtils.isNotEmpty(sdate)){
    		if ("1".equals(type)){
        	    year = Integer.parseInt(sdate.substring(0,4))-1911;
        		mm = Integer.parseInt(sdate.substring(4,6));
        		dd = Integer.parseInt(sdate.substring(6,8));    				    		
        	}else if("2".equals(type)){
        		year = Integer.parseInt(sdate.substring(0,3))+1911;
        		mm = Integer.parseInt(sdate.substring(3,5));
        		dd = Integer.parseInt(sdate.substring(5,7));
        	}    	        
        	
        	if (year<=99){ 
        		if(year<10) { 
        			sb.append("0");
        		}
        		sb.append("0"); 
        	}
    		sb.append(Integer.toString(year));
    		
    		if (mm<=9){
    			sb.append("0");
    			}
    		sb.append(Integer.toString(mm));
    		
    		if (dd<=9){
    			sb.append("0"); 
    			}
    		sb.append(Integer.toString(dd)); 
    		
    		rStr = sb.toString();
    	}
    	
        
    	return rStr;
    }
    
    
    /*
	YYYMMDD : 日期,ex:0990801,09908
	num : 要加減的天數或月數
	kind : 要加減年或日,month(月) ,day(日)
	ex: getMonthDay("0990801",2,"day");     從0990801增加2天
	    getMonthDay("0990801",-2,"day");    從0990801減少2天
	    getMonthDay("09908",2,"month");     從09908增加2月
            getMonthDay("0990801",-1,"month");  從0990801減少1月
	計算種類如果為day時,YYYMMDD一律為7碼,ex:0990801
	*/
    public static String getMonthDay(String YYYMMDD, int num, String kind){
    	
  	  //*************判斷YYYMMDD的格式,如果錯誤回傳空值*************
  		if("".equals(YYYMMDD)){                                   //YYYMMDD等於空
  			return "";
  		}else if(YYYMMDD.length()!=7 && YYYMMDD.length()!= 5){    //YYYMMDD不等於7碼,且不等於5碼
  			return "";
  		}else if(YYYMMDD.length()!=7 && "day".equals(kind)){      //YYYMMDD不等於7碼,且為計算種類為日期
  			return "";
  		}
  	  //*******************************************************
  		
  		String newDate = "";
  		String YYY = YYYMMDD.substring(0,3);
        String MM = YYYMMDD.substring(3,5) ;
        String DD = YYYMMDD.substring(5) ;
        int y = 0;
  		int m = 0;
  		int d = 0;
  		
  		try {
  			if(YYYMMDD.length() == 7){
  			    y = Integer.parseInt(YYY);
  			    m = Integer.parseInt(MM)-1;
  			    d = Integer.parseInt(DD);
  			}else if(YYYMMDD.length() == 5){
  				y = Integer.parseInt(YYY);
  			    m = Integer.parseInt(MM);
  			}
  			
  		//**********判斷月是否大於12,日是否<13,或年月日其中一個為0,如果成立回傳空值**********
  			if(m>12 || d>31 || m<0 || d<0 || y<0){
  				return "";
  			}
  		//*************************************************************************
  			
  		    Calendar cal = Calendar.getInstance();
  		    cal.set(y,m,d);
  		    
  		    if("month".equals(kind)){
  		        cal.add(Calendar.MONTH,num);
  	        }else if("day".equals(kind)){
  	        	cal.add(Calendar.DATE,num);
  	        }  
  		    
  		    YYY = Integer.toString(cal.get(Calendar.YEAR));
  	        MM = Integer.toString(cal.get(Calendar.MONTH)+1);
  	        DD = Integer.toString(cal.get(Calendar.DATE));
  	        if(YYY.length() == 2){
  	        	YYY = "0" + YYY;
  	        }
  	        if(MM.length() == 1){
  	        	MM = "0" + MM;
  	        }
  	        if(DD.length() == 1){
  	        	DD = "0" + DD;
  	        }
  	        
  	        if(YYYMMDD.length() == 7){
  	        	newDate = YYY + MM + DD;
  			}else if(YYYMMDD.length() == 5){
  				newDate = YYY + MM;
  			}
  		}catch(Exception e){
  		    System.out.println("請輸入數字\n" + e.toString()); 
  		    return "";
  		}
  		return newDate;
  	}
    
    /**
   	 * <br>
   	 * <br>目的：取得該日期是星期幾
   	 * <br>參數：無
   	 * <br>傳回：傳回星期日:0,一:1,二:2,三:3,四:4,五:5,六:6
   	*/
       public static String getDayOfWeek(String yyyMMdd) {
           StringBuffer sb = new StringBuffer();
           String yyyyMMdd = getYYYYMMDDFromRocDate(yyyMMdd);
           Calendar cal = Calendar.getInstance();
           //System.out.println(yyyyMMdd);
           try {
   			Date d = new SimpleDateFormat("yyyyMMdd").parse(yyyyMMdd);
   			cal.setTime(d);
   		} catch (ParseException e) {
   			e.printStackTrace();
   		}
           int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
           sb.append(dayOfWeek);
           return sb.toString();
       }
       
       /**
   	 * 民國年轉為西元年
   	 * <br> 0991122 --> 20101122
   	 * @param yyyMMdd
   	 * @return
   	 */
   	public static String getYYYYMMDDFromRocDate(String yyyMMdd){
   		String result = "";
   		if(yyyMMdd != null && yyyMMdd.length() >= 7){
   			String year = yyyMMdd.substring(0,3);
   			String rocYear = String.valueOf(Integer.parseInt(year) + 1911);
   			result = Common.formatFrontZero(rocYear,4) + yyyMMdd.substring(3);
   		}
   		return result;
   	}
   	
	/**
	 * <br>
	 * <br>目的：取得二個日期之間的月份數
	 * <br>參數：alterMonth:第一個日期, dealMonth:第二個日期
	 * <br>傳回：傳回數字-1表長度不夠, -2表第一個日期大於第二個日期
	*/	
    public static int BetweenTwoMonth(String alterMonth , String dealMonth){
        int length = 0;
        if (dealMonth.length() < 5 || alterMonth.length() < 5){
            length = -1;
        }else{
            int dealInt = Integer.parseInt(dealMonth);
            int alterInt = Integer.parseInt(alterMonth);
            if (dealInt < alterInt){
                length = -2;	
            }else{
                    int dealYearInt  = Integer.parseInt(dealMonth.substring(0, 3));
                    int dealMonthInt = Integer.parseInt(dealMonth.substring(3, 5));
                    int alterYearInt = Integer.parseInt(alterMonth.substring(0, 3));
                    int alterMonthInt= Integer.parseInt(alterMonth.substring(3, 5));
                    length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);            	

            }
        }
        return length;
    }
    
    /**
	 * <br>
	 * <br>目的：取得二個日期之間的月份數 西元年用
	 * <br>參數：alterMonth:第一個日期, dealMonth:第二個日期
	 * <br>傳回：傳回數字-1表長度不夠, -2表第一個日期大於第二個日期
	*/	
    public static int BetweenTwoMonthCE(String alterMonth , String dealMonth){
        int length = 0;
        if (dealMonth.length() < 6 || alterMonth.length() < 6){
            length = -1;
        }else{
            int dealInt = Integer.parseInt(dealMonth);
            int alterInt = Integer.parseInt(alterMonth);
            if (dealInt < alterInt){
                length = -2;	
            }else{
                    int dealYearInt  = Integer.parseInt(dealMonth.substring(0, 4));
                    int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
                    int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
                    int alterMonthInt= Integer.parseInt(alterMonth.substring(4, 6));
                    length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);            	

            }
        }
        return length;
    }    
	   	
    /**
	 * 將月數格式化   ex  12-> 1年   , 13 -> 1年1月
	 * @param months
	 * @return
	 */
	public static String formatMonths(String months) {
		int monthCount = Common.getInt(months); //這是月數
		int years = monthCount/12;
		int leaveMonths = monthCount % 12;
		StringBuilder tmp = new StringBuilder();
		
		if (years >= 0) {
			tmp.append(years).append("年");
		}
		
		if (leaveMonths >= 0) {
			tmp.append(leaveMonths).append("月");
		}
		return tmp.toString();
	}
	
	/**
	 * 取得date 所指定的月 最後一日日期; 發生exception 則回傳空字串
	 * @param date yyyyMM
	 * @return
	 */
	public static String getLastDayOfMonth(String date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			Date convertedDate = dateFormat.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			return String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
			return "";
		}
	}
}
