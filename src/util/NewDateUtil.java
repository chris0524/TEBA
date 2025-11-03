package util;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Years;

/**
 * TODO 有鑑於此包程式中 含有  DateUtil,  TaiwanDateUtil,  TwDate, Datetime, 以及 Common中含有一些格式化日期的方法
 * 因此TCLP 皆使用此類別檔 目的在於 統整重製前述類別檔
 * @author CHLin
 */
public class NewDateUtil {

	private static Logger logger = Logger.getLogger(NewDateUtil.class);
	
	/**
	 * 增加時請於  {@link #getNow()}, formatDate, formatTime 新增相對應處理方式
	 * @author CHLin
	 *
	 */
	public enum DateFormat {
		YYY("%s"),
		MM("%s"),
		DD("%s"),
		YYYY("%s"),
		YYYMM("%s%s"),
		YYYMMDD("%s%s%s"),
		YYY年MM月("%s年%s月"),
		YYY年MM月DD日("%s年%s月%s日"),
		YYY點MM點DD("%s.%s.%s"),
		/** 104/01/01 **/
		YYY左MM左DD("%s/%s/%s"),
		YYYYMM("%s%s"),
		YYYYMMDD("%s%s%s"),
		/** YYYY-MM-DD **/
		YYYYMMDD_SP1("%s-%s-%s"),
		YYYY年MM月("%s年%s月"),
		YYYY年MM月DD日("%s年%s月%s日"),
		/** 不支援 formatDate **/
		HHMM("%s%s"),
		/** 不支援 formatDate **/
		HHMMSS("%s%s%s"),
		/** HH:mm:SS **/
		HHMMSS_SP1("%s:%s:%s"),
		/** YYYYMMDDHHmmSS**/
		YYYYMMDDHHmmSS("%s%s%s%s%s%s"),
		/** YYYMMDDHHmmSS**/
		YYYMMDDHHmmSS("%s%s%s%s%s%s"),
		/** 中華民國%s年%s月%s日 **/
		中華民國YYY年MM月DD日("中華民國%s年%s月%s日"),
		/** 中華民國%s年%s月 **/
		中華民國YYY年MM月("中華民國%s年%s月"),
		/** YYYY-MM-DD HH:mm:SS **/
		YYYYMMDD_HHmmSS("%s-%s-%s %s:%s:%s");
		
		private String format;
		
		private DateFormat(String format) {
			this.format = format;
		}
		
		public String getFormat() {
			return this.format;
		}
		
	}
	
	/**
	 * 取得現在的日期時間物件
	 * @return
	 */
	static public DateTime getNow() {
		return new DateTime();
	}
	
	static public String getNow(DateFormat format) {
		return formatDateTime(getNow(), format);
	}
	
	static public String getYYY() {
		return String.valueOf(getNow().getYear() - 1911);
	}
	
	static public String getYYYMM() {
		return getNow(DateFormat.YYYMM);
	}
	
	static public String getYYYMMDD() {
		return getNow(DateFormat.YYYMMDD);
	}
	
	static public String getYYYY() {
		return String.valueOf(getNow().getYear());
	}
	
	static public String getYYYYMM() {
		return getNow(DateFormat.YYYYMM);
	}
	
	static public String getYYYYMMDD() {
		return getNow(DateFormat.YYYYMMDD);
	}
	
	static public String getHHMM() {
		return getNow(DateFormat.HHMM);
	}
	
	static public String getHHMMSS() {
		return getNow(DateFormat.HHMMSS);
	}
	
	static public String getYYYMMDDHHmmSS() {
		return getNow(DateFormat.YYYMMDDHHmmSS);
	}
	
	static public String getYYYYMMDDHHmmSS() {
		return getNow(DateFormat.YYYYMMDDHHmmSS);
	}
	
	static public String getMM() {
		return String.format("%02d", getNow().getMonthOfYear());
	}
	
	static public String getDD() {
		return String.format("%02d", getNow().getDayOfMonth());
	}
	
	/**
	 * 取得年度的第一天, e.g. 105 取得 1050101
	 * @param yyyOryyyy	: e.g. 105 or 2016 ; 長度非3 or 4 則回傳原字串
	 * @param format : 若使用的DateFormat 含有時間亦會回傳原date
	 * @return
	 */
	static public String getFirstDateOfYear(Object yyyOryyyy, DateFormat format) {
		String str_yyyOryyyy = Common.get(yyyOryyyy);
		if (str_yyyOryyyy.length() == 3 || str_yyyOryyyy.length() == 4) {
			str_yyyOryyyy = str_yyyOryyyy + "01";
			return getFirstDate(str_yyyOryyyy, format);
		} else {
			return str_yyyOryyyy;
		}
	}
	
	/**
	 * 取得年度最後一天, e.g. 105 取得 1051231
	 * @param yyyOryyyy	: e.g. 105 or 2016 ; 長度非3 or 4 則回傳原字串
	 * @param format : 若使用的DateFormat 含有時間亦會回傳原date
	 * @return
	 */
	static public String getLastDateOfYear(Object yyyOryyyy, DateFormat format) {
		String str_yyyOryyyy = Common.get(yyyOryyyy);
		if (str_yyyOryyyy.length() == 3 || str_yyyOryyyy.length() == 4) {
			str_yyyOryyyy = str_yyyOryyyy + "12";
			return getLastDate(str_yyyOryyyy, format);
		} else {
			return str_yyyOryyyy;
		}
	}
	
	/**
	 * 取得指定月的第一日的date formated string
	 * @param date	 : 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳原date
	 * @param format : 若使用的DateFormat 含有時間亦會回傳原date
	 * @return
	 */
	static public String getFirstDate(Object date, DateFormat format) {
		DateTime dt = getDateTime(Common.get(date));
		if (dt != null) {
			dt = dt.dayOfMonth().setCopy(dt.dayOfMonth().getMinimumValue());
			return formatDate(new DateObj(dt), format);
		} else {
			return String.valueOf(date);
		}
	}
	
	/**
	 * 取得指定月的最後一日的date formated string
	 * @param date	 : 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳原date
	 * @param format : 若使用的DateFormat 含有時間亦會回傳原date
	 * @return
	 */
	static public String getLastDate(Object date, DateFormat format) {
		DateTime dt = getDateTime(Common.get(date));
		if (dt != null) {
			dt = dt.dayOfMonth().setCopy(dt.dayOfMonth().getMaximumValue());
			return formatDate(new DateObj(dt), format);
		} else {
			return String.valueOf(date);
		}
	}
	
	/**
	 * 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳原date
	 * <B>若使用的DateFormat 含有時間亦會回傳原date </B>
	 * @param yyyymmdd
	 * @param format
	 * @return
	 */
	static public String formatDate(Object date, DateFormat format) {
		return formatDate(Common.get(date), format);
	}
	
	/**
	 * 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 "error"
	 * <B>若使用的DateFormat 含有時間亦會回傳原date </B>
	 * @param yyyymmdd
	 * @param format
	 * @return
	 */
	static public String formatDate(String date, DateFormat format) {
		date = Common.get(date);
		// ex: 2015  or 104  補足0101   這邊不判斷是否為數值
		if (date.length() == 4 || date.length() == 3) {
			date += "0101";
		}
		// ex: 201501  or 10401  補足01   這邊不判斷是否為數值
		if (date.length() == 6 || date.length() == 5) {
			date += "01";
		}
		
		if ((Common.isNumber(date) && (date.length() == 7 || date.length() == 8)) == false) {
			return date;
		}
		
		String result = "";
		try {
			result = formatDate(new DateObj(date), format);
		} catch (Exception e) {
			logger.error("格式化日期發生錯誤", e);
			result = "error";
		}
		return result;
	}
	
	/**
	 * 格式化 Calendar
	 * @param cal
	 * @param format
	 * @return
	 */
	static public String formatDate(Calendar cal, DateFormat format) {
		return formatDate(new DateObj(cal), format);
	}
	
	/**
	 * 將 DateObj 格式化輸出, 不支援的format 回傳error
	 * @param dateObj
	 * @param format
	 * @return
	 */
	static public String formatDate(DateObj dateObj, DateFormat format) {
		String result = "";
		switch (format) {
		case YYY:
			result = dateObj.roc_year;
			break;
		case MM:
			result = dateObj.month;
			break;
		case DD:
			result = dateObj.day;
			break;
		case YYYY:
			result = dateObj.ce_year;
			break;
		case YYYMMDD:
		case YYY年MM月DD日:
		case YYY左MM左DD:
		case YYY點MM點DD:
		case 中華民國YYY年MM月DD日:
			result = String.format(format.getFormat(), dateObj.roc_year, dateObj.month, dateObj.day);
			break;
		case YYYYMMDD:
		case YYYYMMDD_SP1:
		case YYYY年MM月DD日:
			result = String.format(format.getFormat(), dateObj.ce_year, dateObj.month, dateObj.day);
			break;
		case YYYMM:
		case YYY年MM月:
		case 中華民國YYY年MM月:
			result = String.format(format.getFormat(), dateObj.roc_year, dateObj.month);
			break;
		case YYYYMM:
		case YYYY年MM月:
			result = String.format(format.getFormat(), dateObj.ce_year, dateObj.month);
			break;
		case YYYYMMDD_HHmmSS:
			result = String.format(format.getFormat(), dateObj.ce_year, dateObj.month, dateObj.day, Common.formatFrontZero("0", 2), Common.formatFrontZero("0", 2), Common.formatFrontZero("0", 2));
			break;
		default:
			result = "error"; //TODO 是否拋錯?
			break;
		}
		return result;
	}
	
	/**
	 * 格式化時間, 無法處理的情形皆回傳原字串
	 * @param time	: 長度必須6
	 * @param format
	 * @return
	 */
	static public String formatTime(String time, DateFormat format) {
		time = Common.get(time);
		if (time.length() != 6) {
			return time;
		}
		
		String hh = time.substring(0, 2);
		String mm = time.substring(2, 4);
		String ss = time.substring(4, 6);
		
		String result = "";
		switch (format) {
		case HHMM:
			result = String.format(format.getFormat(), hh, mm);
			break;
		case HHMMSS:
		case HHMMSS_SP1:
			result = String.format(format.getFormat(), hh, mm, ss);
			break;
		default:
			result = time;
			break;
		}
		return result;
	}
	
	/**
	 * 格式化 java.util.Date 物件及其subclass
	 * @param date
	 * @param format
	 * @return
	 */
	static public String formatDate(Date date, DateFormat format) {
		return formatDateTime(new DateTime(date.getTime()), format);
	}
	
	/**
	 * 格式化joda-time obj
	 * @param dobj
	 * @param format
	 * @return
	 */
	static public String formatDateTime(DateTime dobj, DateFormat format) {
		String ce_year = String.valueOf(dobj.getYear());
		String roc_year = String.valueOf(dobj.getYear() - 1911);
		String month = Common.formatFrontZero(dobj.getMonthOfYear(), 2);
		String day = Common.formatFrontZero(dobj.getDayOfMonth(), 2);
		String hh = Common.formatFrontZero(dobj.getHourOfDay(), 2);
		String mm = Common.formatFrontZero(dobj.getMinuteOfHour(), 2);
		String ss = Common.formatFrontZero(dobj.getSecondOfMinute(), 2);
		String result = "";
		switch (format) {
		case YYY:
			result = roc_year;
			break;
		case MM:
			result = month;
			break;
		case DD:
			result = day;
			break;
		case YYYY:
			result = ce_year;
			break;
		case YYYMMDD:
		case YYY年MM月DD日:
		case YYY左MM左DD:
		case YYY點MM點DD:
		case 中華民國YYY年MM月DD日:
			result = String.format(format.getFormat(), roc_year, month, day);
			break;
		case YYYYMMDD:
		case YYYYMMDD_SP1:
		case YYYY年MM月DD日:
			result = String.format(format.getFormat(), ce_year, month, day);
			break;
		case YYYMM:
		case YYY年MM月:
		case 中華民國YYY年MM月:
			result = String.format(format.getFormat(), roc_year, month);
			break;
		case YYYYMM:
		case YYYY年MM月:
			result = String.format(format.getFormat(), ce_year, month);
			break;
		case HHMM:
			result = String.format(format.getFormat(), hh, mm);
			break;
		case HHMMSS:
		case HHMMSS_SP1:
			result = String.format(format.getFormat(), hh, mm, ss);
			break;
		case YYYYMMDDHHmmSS:
		case YYYYMMDD_HHmmSS:
			result = String.format(format.getFormat(), ce_year, month, day, hh, mm, ss);
			break;
		case YYYMMDDHHmmSS:
			result = String.format(format.getFormat(), roc_year, month, day, hh, mm, ss);
			break;
		
		}
		return result;
	}
	
	/**
	 * 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 NULL
	 * @param date
	 * @return DateTime
	 */
	public static DateTime getDateTime(String _date) {
		String date = Common.get(_date);
		// ex: 201501  or 10401  補足01   這邊不判斷是否為數值
		if (date.length() == 6 || date.length() == 5) {
			date += "01";
		}
		
		if ((Common.isNumber(date) && (date.length() == 7 || date.length() == 8)) == false) {
			return null;
		}
		
		DateObj dateObj = new DateObj(date);
		return new DateTime(dateObj.getCEYear(), dateObj.getMonth(), dateObj.getDay(), 0, 0, 0, 0);
	}
	
	/**
	 * 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 NULL
	 * @param date
	 * @return java.util.Date
	 */
	public static Date getDate(Object _date) {
		DateTime dt = getDateTime(Common.get(_date));
		return dt.toDate();
	}
	
	/**
	 * 以現在為基準取得 增加/減少的日期時間
	 * @param plusType	: y:年 ; m:月 ; d:日  ; H:時 ; M:分 ; S:秒
	 * @param plusnum	: 要增減的數值
	 * @return
	 */
	public static String plusDate(Object plusType, Object plusnum, DateFormat format) {
		return plusDateTime(getNow(), Common.get(plusType), Common.getInt(plusnum), format);
	}
	
	/**
	 * @param date		: 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 NULL
	 * @param plusType	: y:年 ; m:月 ; d:日  (H:時 ; M:分 ; S:秒  但可能不是有用數據)
	 * @param plusnum	: 要增減的數值
	 * @return
	 */
	public static String plusDate(Object date, Object plusType, Object plusnum, DateFormat format) {
		return plusDate(Common.get(date), Common.get(plusType), Common.getInt(plusnum), format);
	}
	
	/**
	 * 
	 * @param date		: 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 NULL
	 * @param plusType	: y:年 ; m:月 ; d:日  (H:時 ; M:分 ; S:秒  但可能不是有用數據)
	 * @param plusnum	: 要增減的數值
	 * @return
	 */
	public static String plusDate(String date, String plusType, int plusnum, DateFormat format) {
		DateTime dt = getDateTime(date);
		return plusDateTime(dt, plusType, plusnum, format);
	}
	
	/**
	 * @param dt
	 * @param plusType	: y:年 ; m:月 ; d:日  ; H:時 ; M:分 ; S:秒
	 * @param plusnum	: 要增減的數值
	 * @param format
	 * @return
	 */
	public static String plusDateTime(DateTime dt, String plusType, int plusnum, DateFormat format) {
		if("y".equals(plusType)){
			dt = dt.plusYears(plusnum);
		}else if("m".equals(plusType)){
			dt = dt.plusMonths(plusnum);
		}else if("d".equals(plusType)){
			dt = dt.plusDays(plusnum);
		}else if("H".equals(plusType)){
			dt = dt.plusHours(plusnum);
		}else if("M".equals(plusType)){
			dt = dt.plusMinutes(plusnum);
		}else if("S".equals(plusType)){
			dt =dt.plusSeconds(plusnum);
		}
		return formatDateTime(dt, format);
	}
	
	/**
	 * 回傳結果說明如下:
	 * <br/> < 0 : date1 < now
	 * <br/> = 0 : date1 = now
	 * <br/> > 0 : date1 > now
	 * @param date1
	 * @return 
	 */
	public static int compareToNow(String date1) {
		DateTime _date1 = getDateTime(date1);
		return _date1.compareTo(getNow());
	}
	
	/**
	 * 回傳結果說明如下:
	 * <br/> < 0 : date1 < date2
	 * <br/> = 0 : date1 = date2
	 * <br/> > 0 : date1 > date2
	 * @param date1
	 * @param date2
	 * @return 
	 */
	public static int compareTwoDate(String date1, String date2) {
		DateTime _date1 = getDateTime(date1);
		DateTime _date2 = getDateTime(date2);
		return _date1.compareTo(_date2);
	}
	
	/**
	 * 指定日期與系統時間差異
	 * @param date1	:	可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 -1
	 * @param betweenType : d:日 m:月 y:年
	 * @return
	 */
	public static int getBetweenNow(Object date1, String betweenType) {
		return getBetweenDates(String.valueOf(date1), getYYYMMDD(), betweenType);
	}
	
	/**
	 * 
	 * @param date1	: 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 -1
	 * @param date2	: 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 -1
	 * @param betweenType	:	d:日 m:月 y:年
	 * @param ps. 若傳入日期包含day(dd), 會以day的差距來判斷是否已滿一個月
	 * @return 其餘的date格式或發生錯誤則回傳-1, 第一個日期大於第二個日期則回傳-2
	 */
	public static int getBetweenDates(Object date1, Object date2, String betweenType) {
		return getBetweenDates(String.valueOf(date1), String.valueOf(date2), betweenType);
	}
	
	/**
	 * 
	 * @param date1	: 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 -1
	 * @param date2	: 可傳入 yyyymm, yyyymmdd, yyymm, yyymmdd, 無日期會自動補01 , 其餘的date格式或發生錯誤則回傳 -1
	 * @param betweenType	:	d:日 m:月 y:年
	 * @param ps. 若傳入日期包含day(dd), 會以day的差距來判斷是否已滿一個月
	 * @return 其餘的date格式或發生錯誤則回傳-1, 第一個日期大於第二個日期則回傳-2
	 */
	public static int getBetweenDates(String date1, String date2, String betweenType) {
		DateTime dt1 = getDateTime(date1);
		DateTime dt2 = getDateTime(date2);
		if (dt1 == null || dt2 == null) {
			return -1;
		}
		
		if (dt1.compareTo(dt2) > 0) {
			return -2;
		}
		
		LocalDate ld1 = dt1.toLocalDate();
		LocalDate ld2 = dt2.toLocalDate();
		int resultInt = 0;
		
		if("d".equals(betweenType)){
			resultInt = Days.daysBetween(ld1, ld2).getDays();
		}else if ("m".equals(betweenType)){
			resultInt = Months.monthsBetween(dt1, dt2).getMonths();
		}else if ("y".equals(betweenType)){
			resultInt = Years.yearsBetween(dt1, dt2).getYears();
		}
		return resultInt;
	}
	public static class DateObj {
		String ce_year;
		String roc_year;
		String month;
		String day;
		
		public DateObj(String dateStr) {
			if (dateStr.length() <= 7) {
				dateStr = Common.formatFrontZero(dateStr, 7);
				roc_year = dateStr.substring(0, 3);
				month = dateStr.substring(3, 5);
				day = dateStr.substring(5, 7);
				ce_year = Common.formatFrontZero(String.valueOf(Integer.parseInt(roc_year) + 1911), 3);
			} else if (dateStr.length() == 8) {
				ce_year = dateStr.substring(0, 4);
				month = dateStr.substring(4, 6);
				day = dateStr.substring(6, 8);
				roc_year = Common.formatFrontZero(String.valueOf(Integer.parseInt(ce_year) - 1911), 3);
			}
		}
		
		public DateObj(DateTime dt) {
			ce_year = String.valueOf(dt.getYear());
			month = String.format("%02d", dt.getMonthOfYear());
			day = String.format("%02d", dt.getDayOfMonth());
			roc_year = Common.formatFrontZero(String.valueOf(Integer.parseInt(ce_year) - 1911), 3);
		}
		
		public DateObj(Calendar cal) {
			ce_year = String.valueOf(cal.get(Calendar.YEAR));
			month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
			day = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
			roc_year = Common.formatFrontZero(String.valueOf(Integer.parseInt(ce_year) - 1911), 3);
		}
		
		@Override
		public String toString() {
			return roc_year + month + day + "(" + ce_year + month + day + ")";
		}
		
		public int getCEYear() {
			return Common.getInt(ce_year);
		}
		
		public int getROCYear() {
			return Common.getInt(roc_year);
		}
		
		public int getMonth() {
			return Common.getInt(month);
		}
		
		public int getDay() {
			return Common.getInt(day);
		}
		
	}	
}
