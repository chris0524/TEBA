/*
*<br>程式目的：常用函數
*<br>程式代號：Common
*<br>撰寫日期：93/12/01
*<br>程式作者：griffin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.MySQLCodec;

public class Common {
	
	static public String esapi(String rs) {
		Codec codec = new MySQLCodec(MySQLCodec.ANSI_MODE);
		return ESAPI.encoder().encodeForSQL(codec, rs);
	}

	/**
	 * <br>
	 * <br>
	 * 目的：撰寫JavaBean get方法時所需套用的函數 <br>
	 * 參數：資料字串 <br>
	 * 傳回：檢查資料為null,是則傳回空字串
	 */
	static public String get(String s) {

		if (s == null) {
			return "";
		} else {
			return s.trim();
		}
	}

	static public String get(Object s) {
		if (s == null)
			return "";
		else
			return s.toString().trim();
	}

	static public String get(int s) {
		return get("" + s);
	}

	static public Double get(Double d) {
		if (d != null)
			return d;
		else
			return 0d;
	}

	public static int getInt(Object s) {
		return getInteger(get(s)).intValue();
	}

	public static int getInt(String s) {
		return getInteger(s).intValue();
	}

	public static Integer getInteger(String s) {
		Double d = getNumeric(s);
		return d.intValue();
	}

	public static long getLong(Object s) {
		Double d = getNumeric(s);
		return d.longValue();
	}

	public static double getNumeric(Object s) {
		if (s != null)
			return getNumeric(s.toString());
		else
			return 0;
	}

	public static double getNumeric(String s) {
		if (get(s).equals(""))
			return 0;
		try {
			if (Double.isNaN(Double.parseDouble(s)))
				return 0;
			else
				return Double.parseDouble(s.replaceAll(",", ""));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 取得Double 數值字串, 並且避免數值過大使用科學符號, 小數點部分處理四捨五入
	 * 
	 * @param val
	 * @return
	 */
	static public String getDoubleVal(double val) {
		return getDoubleVal(val, false, RoundingMode.HALF_UP);
	}

	/**
	 * 取得Double 數值字串, 並且避免數值過大使用科學符號, 小數點部分處理四捨五入
	 * 
	 * @param val
	 * @param groupingUsed : 千分逗號的使用?
	 * @param roundingMode : 小數處理方式
	 * @return
	 */
	static public String getDoubleVal(double val, boolean groupingUsed, RoundingMode roundingMode) {
		return getDoubleVal(val, groupingUsed, roundingMode, 0);
	}

	/**
	 * 取得Double 數值字串, 並且避免數值過大使用科學符號
	 * 
	 * @param val
	 * @param groupingUsed      : 千分逗號的使用?
	 * @param roundingMode      : 小數處理方式
	 * @param maxFractionDigits : 根據處理方式 處理到小數第幾位
	 * @return
	 */
	static public String getDoubleVal(double val, boolean groupingUsed, RoundingMode roundingMode,
			int maxFractionDigits) {
		return getDoubleVal(val, groupingUsed, roundingMode, maxFractionDigits, 0);
	}

	/**
	 * 取得Double 數值字串, 並且避免數值過大使用科學符號
	 * 
	 * @param val
	 * @param groupingUsed       : 千分逗號的使用?
	 * @param roundingMode       : 小數處理方式
	 * @param maxFractionDigits  : 根據處理方式 處理到小數第幾位
	 * @param miniFractionDigits : 小數最小位數, e.g 傳入2 ,但數值是沒有小數的數值 比如說 1234 那就會回傳
	 *                           1,234.00
	 * @return
	 */
	static public String getDoubleVal(double val, boolean groupingUsed, RoundingMode roundingMode,
			int maxFractionDigits, int miniFractionDigits) {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(groupingUsed);
		nf.setRoundingMode(roundingMode);
		nf.setMaximumFractionDigits(maxFractionDigits);
		nf.setMinimumFractionDigits(miniFractionDigits);
		return nf.format(val);
	}

	/**
	 * <br>
	 * <br>
	 * 目的：撰寫JavaBean set方法時所需套用的函數 <br>
	 * 參數：資料字串 <br>
	 * 傳回：將資料前後空白去除
	 */
	static public String set(String s) {
		if (s == null) {
			return s;
		} else {
			return s.trim();
		}
	}

	/**
	 * <br>
	 * <br>
	 * 目的：組合sql語法時將字串轉成int <br>
	 * 參數：資料字串 <br>
	 * 傳回：int
	 */
	static public int sqlint(String s) {
		int i = 0;
		if (s == null || s.equals("")) {
			return i;
		}
		return Integer.parseInt(s);
	}

	/**
	 * <br>
	 * <br>
	 * 目的：組合sql語法時將字串轉成int <br>
	 * 參數：資料字串 <br>
	 * 傳回：int
	 */
	static public String sqlNumber(String s) {
		if (s == null || "".equals(s)) {
			return null;
		} else {
			return s.trim();
		}
	}

	/**
	 * <br>
	 * <br>
	 * 目的：組合sql語法時字串需加單引號的函數 <br>
	 * 參數：資料字串 <br>
	 * 傳回：加上單引號傳回資料字串
	 */
	static public String sqlChar(String s) {
		return sqlChar(s, false);
	}

	/**
	 * /** <br>
	 * <br>
	 * 目的：組合sql語法時字串需加單引號的函數 <br>
	 * 參數：資料字串 <br>
	 * 傳回：加上單引號傳回資料字串
	 */
	static public String sqlChar(String s, boolean isUpper) {

		if (s == null || s.equals("")) {
			return "''";
		}
		return "'" + StringEscapeUtils.escapeSql(isUpper ? s.trim().toUpperCase() : s.trim()) + "'";
//		if (s == null){ 
//			return "''"; 
//		}else{
//			String newS = s;
//			if(s.indexOf("'") != -1){
//				newS = newS.replaceAll("'", " ");
//			}
//
//			//if ((s.indexOf("'")>=0)||(s.indexOf("\"")>=0)||(s.indexOf("=")>=0)
//			//	||(s.indexOf(">")>=0)||(s.indexOf("<")>=0))	{
//			//	return ""; 
//			//}else{
//			return "'" + s.trim() + "'";
//			//}
//		}
	}

	/**
	 * <br>
	 * <br>
	 * 目的：組合sql語法時字串需加單引號的函數 <br>
	 * 參數：資料字串 <br>
	 * 傳回：加上單引號傳回資料字串
	 */
	static public String sqlInt(String s) {
		if (s == null) {
			return "'0'";
		}
		return "'" + s.trim() + "'";
	}

	/**
	 * <br>
	 * <br>
	 * 目的：組合html option語法函數 <br>
	 * 參數：(1)sql字串 (2)被選的value <br>
	 * <br>
	 * 傳回：加上html option element
	 */
	static public String getOption(String sql, String selectedOne) {
		String rtnStr = "<option value=''>請選擇</option>";
		Database db = new Database();
		try {
			ResultSet rs = db.querySQL(sql);
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);

				rtnStr = rtnStr + "<option value='" + id + "' ";
				if (selectedOne != null && selectedOne.equals(id)) {
					rtnStr = rtnStr + " selected ";
				}
				rtnStr = rtnStr + ">" + name + "</option>";
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
	 * <br>
	 * 目的：將路徑檔名改為jasper可以解讀的格式, 例:"d:\test\test.jasper", 傳回"d:\\test\\test.jasper"
	 * <br>
	 * 參數：資料字串 <br>
	 * 傳回：傳回轉換後字串
	 */
	public static String getJasperPath(String s) {
		StringBuffer sReturn = new StringBuffer();
		int start = 0;
		for (int i = 0; i < s.length(); i++) {
			if ("\\".equals(s.substring(i, i + 1))) {
				sReturn.append(s.substring(start, i) + "\\");
				start = i;
			}
		}
		sReturn.append(s.substring(start, s.length()));
		return sReturn.toString();
	}

	static public String FormatStr(String sString, String sHTML) {
		String sStr;
		if (sHTML.equalsIgnoreCase("Y") || sHTML.equalsIgnoreCase("true") || sHTML.equalsIgnoreCase("1")) {
			sStr = sString.replaceAll("<%", "&lt;%");
		} else {
			sStr = sString.replaceAll("<", "&lt;");
			sStr = sStr.replaceAll(">", "&gt;");
			// sStr = sStr.replaceAll("\n", "<br>");
			sStr = sStr.replaceAll("\r", "<br>");
		}
		return sStr;
	}

	public static String[] strToken(String in字串) {
		List v = new ArrayList();
		StringTokenizer token = new StringTokenizer(in字串, ",");
		while (token.hasMoreElements()) {
			v.add(token.nextToken());
		}
		return (String[]) v.toArray(new String[0]);
	}

	public static String strHandle(String[] strs) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]).append(",");
		}
		return sb.toString();
	}

	public static String getYNname(String word) {
		String YNname = "";
		if (word == null) {
			YNname = "";
		} else if (word.equals("Y")) {
			YNname = "通過";
		} else if (word.equals("N")) {
			YNname = "不通過";
		} else if (word.equals("X")) {
			YNname = "未申請";
		}
		return YNname;

	}

	public static String checknull(String data) {
		return data = data == null ? "" : data.trim();
	}

	public static String checknullzero(String data) {
		return data = data == null ? "0" : data.trim();
	}

	public FileWriter createLog(String dirName) {
		String fileName = Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".log";
		FileWriter fsout = null;
		File dirFile = new File(dirName);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File file = new File(dirFile, fileName);
		try {
			fsout = new FileWriter(file);
			// byte[] b = TxtContent.getBytes();
			// fsout.write(b);
			// fsout.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fsout;
	}

	// 價值格式
	public static String moneyFormat(String money) {
		String formatString = new String();
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
		if (money != null && !money.equals("")) {
			try {
				formatString = df.format(Double.parseDouble(money));
			} catch (NumberFormatException e) {
				formatString = money;
			}
		} else {
			formatString = money;
		}
		return ZeroInt(formatString);
	}

	// 價值格式
	public static String moneyFormat(int money) {
		String formatString = new String();
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
		try {
			formatString = df.format(money);
		} catch (NumberFormatException e) {
			formatString = String.valueOf(money);
		}
		return ZeroInt(formatString);
	}

	// 價值格式
	public static String moneyFormat(long money) {
		String formatString = new String();
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
		try {
			formatString = df.format(money);
		} catch (NumberFormatException e) {
			formatString = String.valueOf(money);
		}
		return ZeroInt(formatString);
	}

	/**
	 * 將月數格式化為 Y年M個月 , 若傳入非正常數值, 則會視為0 並回傳 0年0個月
	 * 
	 * @param months
	 * @return
	 */
	public static String MonthsFormat(Object months) {
		return MonthsFormat(months, false);
	}

	/**
	 * 將月數格式化為 Y年M個月 , 若傳入非正常數值, 則會視為0 並回傳 0年0個月
	 * 
	 * @param months
	 * @param enableEmpty : 當為true時 且 傳入的值 null or empty 則回傳空字串, 反之則回傳 0年0個月
	 * @return
	 */
	public static String MonthsFormat(Object months, boolean enableEmpty) {
		if ("".equals(Common.get(months)) && enableEmpty) {
			return "";
		}
		int int_months = getInt(months);
		int s_years = int_months / 12;
		int s_months = int_months % 12;
		return s_years + "年" + s_months + "個月";
	}

	// 面積格式
	public static String areaFormat(String money) {
		String formatString = new String();
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
		if (money != null && !money.equals("")) {
			try {
				formatString = df.format(Double.parseDouble(money));
			} catch (NumberFormatException e) {
				formatString = money;
			}
		} else {
			formatString = money;
		}
		return formatString;
	}

	public static String getChkYear(String applydate) {
		// 財稅年度計算方式：
		// 1.若為年度複查案件，則以申請年度-2 (申請年度=系統年度+1者視為年度複查案件)
		// 2.若非年度複查案件，申請日期在5/1以前則以申請年度-2；申請日期在5/1以後則以申請年度-1
		String sYear = applydate.substring(0, 3);
		String sMMDD = applydate.substring(3, 7);
		String sSysYear = Datetime.getYYYMMDD();
		Integer iSysYear = Integer.parseInt(sSysYear);
		boolean isYearChk = false;
		Integer iYear = Integer.parseInt(sYear);
		Integer iMMDD = Integer.parseInt(sMMDD);

		if (iYear == (iSysYear + 1)) {
			isYearChk = true;
		} else {
			if (iMMDD < 501)
				isYearChk = true;
		}
		if (isYearChk)
			iYear = iYear - 2;
		else
			iYear = iYear - 1;
		sYear = "" + iYear;
		if (sYear.length() == 2)
			sYear = "0" + sYear;
		return sYear;
	}

	/**
	 * <br>
	 * 目的：計算年齡 <br>
	 * 參數：birthDate 出生日期 nowDate 現在系統日期 birthCode（非必填）是否為民國前之註記 <br>
	 * 傳回：傳回數字
	 */
	public static int getAges(String birthDate, String nowDate, String... birthCode) {
		if (birthCode.length == 0)
			birthCode = new String[] { "" };

		int theAge = 0;
		if (StringUtils.isNotBlank(birthDate)) {
			if (StringUtils.isBlank(birthCode[0]) || "N".equals(birthCode[0])) {
				theAge = Integer.parseInt(util.Datetime.getYYY()) - Integer.parseInt(birthDate.substring(0, 3));
				if (Integer.parseInt(util.Datetime.getMMDD().substring(0, 2)) < Integer
						.parseInt(birthDate.substring(3, 5))) {
					theAge = Math.abs(theAge) - 1;
				}
			} else if ("Y".equals(birthCode[0])) {
				int i = Integer.parseInt(birthDate.substring(0, 3)) + Integer.parseInt(util.Datetime.getYYY());
				if (Integer.parseInt(util.Datetime.getMMDD().substring(0, 2)) > Integer
						.parseInt(birthDate.substring(3, 5))) {
					theAge = Math.abs(i);
				} else {
					theAge = Math.abs(i) - 1;
				}
			}
		}
		return Math.abs(theAge);
	}

	public static String filterNull(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * <br>
	 * <br>
	 * 目的：撰寫JavaBean check set方法時所需套用的函數 <br>
	 * 參數：資料字串 <br>
	 * 傳回：將資料前後空白去除
	 */
	public static String checkSet(String s) {
		if (s == null) {
			return "";
		} else {
			if ((s.indexOf("'") >= 0) || (s.indexOf("\"") >= 0)) {
				s = "";
			}
			return s.trim();
		}
	}

	/**
	 * <br>
	 * <br>
	 * 目的：撰寫JavaBean chect get方法時所需套用的函數 <br>
	 * 參數：資料字串 <br>
	 * 傳回：檢查資料為null,是則傳回空字串
	 */
	static public String checkGet(String s) {
		if (s == null) {
			return "";
		} else {
			s = s.trim();
			s = s.replaceAll("&", "&amp;");
			s = s.replaceAll("<", "&lt;");
			s = s.replaceAll(">", "&gt;");
			s = s.replaceAll("\"", "&quot;");
			s = s.replaceAll("'", "&#39;");
			// s = org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
			return s;
		}
	}

	// 將日期加/
	public static String MaskDate(String sDate) {
		String sMaskDate = "";
		if ((sDate == null) || ("".equals(sDate))) {
			return "";
		} else {
			int iSLen = sDate.length();
			if (iSLen <= 3) {
				sMaskDate = sDate;
			}
			if ((iSLen > 3) && (iSLen <= 5)) {
				sMaskDate = sDate.substring(0, 3) + "/" + sDate.substring(3, iSLen);
			}
			if (iSLen > 5) {
				sMaskDate = sDate.substring(0, 3) + "/" + sDate.substring(3, 5) + "/" + sDate.substring(5, iSLen);
			}
			return sMaskDate;
		}
	}

	// 將日期加/ (西元)
	public static String MaskDate_AD(String sDate) {
		String sMaskDate = "";
		if ((sDate == null) || ("".equals(sDate))) {
			return "";
		} else {
			int iSLen = sDate.length();
			if (iSLen <= 4) {
				sMaskDate = sDate;
			}
			if ((iSLen > 4) && (iSLen <= 6)) {
				sMaskDate = sDate.substring(0, 4) + "/" + sDate.substring(4, iSLen);
			}
			if (iSLen > 6) {
				sMaskDate = sDate.substring(0, 4) + "/" + sDate.substring(4, 6) + "/" + sDate.substring(6, iSLen);
			}
			return sMaskDate;
		}
	}

	// 將日期加年月日
	public static String MaskCDate(String sDate) {
		String sMaskDate = "";
		if ((sDate == null) || ("".equals(sDate))) {
			return "";
		} else {
			int iSLen = sDate.length();
			if (iSLen <= 3) {
				sMaskDate = sDate;
			}
			if ((iSLen > 3) && (iSLen <= 5)) {
				if (!"0".equals(sDate.substring(0, 1))) {
					sMaskDate = sDate.substring(0, 3) + "年";
				} else {
					sMaskDate = sDate.substring(1, 3) + "年";
				}
				if (!"0".equals(sDate.substring(3, 4))) {
					sMaskDate += sDate.substring(3, iSLen) + "月";
				} else {
					sMaskDate += sDate.substring(4, iSLen) + "月";
				}
			}
			if (iSLen > 5) {
				sMaskDate = sDate.substring(0, 3) + "年";
				sMaskDate += sDate.substring(3, 5) + "月";
				sMaskDate += sDate.substring(5, iSLen) + "日";
			}
			return sMaskDate;
		}
	}

	// 將日期加"."
	public static String MaskDateDot(String sDate) {
		String sMaskDate = "";
		if ((sDate == null) || ("".equals(sDate))) {
			return "";
		} else {
			int iSLen = sDate.length();
			if (iSLen <= 3) {
				sMaskDate = sDate;
			}
			if ((iSLen > 3) && (iSLen <= 5)) {
				sMaskDate = sDate.substring(0, 3) + "." + sDate.substring(3, iSLen);
			}
			if (iSLen > 5) {
				sMaskDate = sDate.substring(0, 3) + "." + sDate.substring(3, 5) + "." + sDate.substring(5, iSLen);
			}
			return sMaskDate;
		}
	}

	public static boolean RemoveDirectory(File dir) {
		try {
			String fileName = dir.getName();
			String validatedFileName = ESAPI.validator().getValidFileName("anyFile", fileName,
					ESAPI.securityConfiguration().getAllowedFileExtensions(), false);
			String filePath = "";
			if (dir.getParent() != null) {
				filePath = dir.getParent() + File.separator + validatedFileName;
			} else {
				filePath = validatedFileName;
			}
			File f = new File(filePath);
			if (f.isDirectory()) {
				// if directory is root directory then throw exception
				if (f.getParentFile() == null)
					throw new IllegalArgumentException("Can't remove root directory");
				String[] children = f.list();
				for (int i = 0; i < children.length; i++) {
					boolean success = RemoveDirectory(new File(f, children[i]));
					if (!success) {
						return false;
					}
				}
			}

			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// The directory is now empty so delete it
		return true;
	}

	public static boolean CreateDir(String strDirName) {
		// Create a directory; all ancestor directories must exist
		// boolean success = (new File(strDirName)).mkdir();
		return (new File(strDirName)).mkdirs();

		// Create a directory; all non-existent ancestor directories are
		// automatically created
		// success = (new File("directoryName")).mkdirs();
	}

	@SuppressWarnings("unchecked")
	public static boolean MakeDir(File dirToMake) throws SecurityException {
		final File tempDirToMake = dirToMake;
		Boolean result = (Boolean) AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				return new Boolean(tempDirToMake.mkdirs());
			}
		});
		return result.booleanValue();
	}

	/**
	 * <br>
	 * <br>
	 * 目的：將字串BIG5轉為ISO1 <br>
	 * 參數：資料字串 <br>
	 * 傳回：傳回ISO1字串
	 */
	public static String big5ToIso(String s) {
		if (s == null) {
			s = "";
		}
		try {
			s = new String(s.getBytes("big5"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String utf8ToIso(String s) {
		if (s == null) {
			s = "";
		}
		try {
			s = new String(s.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * <br>
	 * <br>
	 * 目的：將字串ISO1轉為BIG5 <br>
	 * 參數：資料字串 <br>
	 * 傳回：傳回BIG5字串
	 */
	public static String isoToBig5(String s) {
		if (s == null) {
			s = "";
		}
		try {
			s = new String(s.getBytes("ISO-8859-1"), "BIG5");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * <br>
	 * <br>
	 * 目的：將字串ISO1轉為UTF-8 <br>
	 * 參數：資料字串 <br>
	 * 傳回：傳回BIG5字串
	 */
	public static String isoToUTF8(String s) {
		if (s == null) {
			s = "";
		}
		try {
			s = new String(s.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * <br>
	 * 目的：將字串前面補零傳回字串 <br>
	 * 參數：(1)資料字串 (2)所需長度 <br>
	 * 傳回：傳回字串 <br>
	 * 範例：formatFrontZero("123",5)，傳回"00123"
	 */
	static public String formatFrontZero(String s, int len) {
		String format = "";
		int sLen = s.length();
		for (int i = 0; i < (len - sLen); i++) {
			format += "0";
		}
		format += s;
		return format;
	}

	/**
	 * <br>
	 * 目的：將字串後面補零傳回字串 <br>
	 * 參數：(1)資料字串 (2)所需長度 <br>
	 * 傳回：傳回字串 <br>
	 * 範例：formatRearZero("123",5)，傳回"12300"
	 */
	static public String formatRearZero(String s, int len) {
		String format = "";
		int sLen = s.length();
		for (int i = 0; i < (len - sLen); i++) {
			format += "0";
		}
		format = s + format;
		return format;
	}

	/**
	 * <br>
	 * 目的：將字串前面補自己想要加入的字元傳回字串 <br>
	 * 參數：(1)資料字串 (2)所需長度 (3)欲加入的字元 <br>
	 * 傳回：傳回字串 <br>
	 * 範例：formatRearZero("123",5, "A")，傳回"AA123"
	 */
	static public String formatFrontString(String s, int len, char ch) {
		String format = "";
		int sLen = s.length();
		for (int i = 0; i < (len - sLen); i++) {
			format += ch;
		}
		format += s;
		return format;
	}

	/**
	 * <br>
	 * 目的：將字串後面補自己想要加入的字元傳回字串 <br>
	 * 參數：(1)資料字串 (2)所需長度 (3)欲加入的字元 <br>
	 * 傳回：傳回字串 <br>
	 * 範例：formatRearZero("123",5, "A")，傳回"123AA"
	 */
	static public String formatRearString(String s, int len, char ch) {
		String format = "";
		int sLen = s.length();
		for (int i = 0; i < (len - sLen); i++) {
			format += ch;
		}
		format = s + format;
		return format;
	}

	/**
	 * <br>
	 * 目的：若空回傳0 <br>
	 * 參數：資料字串 <br>
	 * 傳回：0
	 */
	static public String ZeroInt(String s) {
		if (StringUtils.isBlank(s)) {
			return "0";
		}
		return s.trim();
	}

	/**
	 * <br>
	 * <br>
	 * 目的：取得某年某月的最後一天 <br>
	 * 傳回：最後一天
	 */
	static public int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		} else if (month == 2) {
			if (isLeapYear(year))
				return 29;
			else
				return 28;
		}

		return 0;
	}

	// 搭配 getLastDayOfMonth() 來判斷是否為潤年
	static private boolean isLeapYear(int year) {
		if (year < 1000) {
			year = year + 1911;
		}
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * <br>
	 * 目的：製造亂數字串 <br>
	 * 傳回：字串
	 */
	public static String getVMID() {
		return new java.rmi.dgc.VMID().toString().replaceAll(":", "_");
	}

	public static String getTimeBasedUniqueId() {
		return util.uid.TimeBasedUniqueIdGenerator.getInstance().next().getTextValue();
	}

	/**
	 * 取得暫存檔目錄
	 * 
	 * @return
	 */
	public static File getTempDirectory() {
		return new File(System.getProperty("java.io.tmpdir"));
	}

	public static long strToNum(String money) {
		if (!"".equals(checknull(money))) {
			return Long.parseLong(money);
		} else {
			return 0;
		}
	}

	public static long toPlus1(String num) {
		if (!"".equals(checknull(num))) {
			return strToNum(num) + 1;
		} else {
			return 1;
		}
	}

	/**
	 * 將日期拆開塞入陣列, 報表用
	 * 
	 * @param date       字串日期YYYMMDD
	 * @param isSeparate true為日期拆開, false為不拆開並加上年月日
	 * @return String[], isSeparate為true時 [0]YYY [1]MM [2]DD, isSeparate為false時
	 *         [0]YYY年MM月DD日
	 */
	public static String[] dateSeparate(String date, boolean isSeparate) {
		String[] dateAry = null;
		if (isSeparate) {
			dateAry = new String[] { "", "", "" };
		} else {
			dateAry = new String[] { "" };
		}

		if (!(("".equals(date)) || (null == date))) {
			if (isSeparate) {
//				dateAry = new String[]{"", "", ""};
				dateAry[0] = date.substring(0, 3);
				dateAry[1] = date.substring(3, 5);
				dateAry[2] = date.substring(5, 7);
			} else {
//				dateAry = new String[]{""};
				dateAry[0] = date.substring(0, 3) + "年" + date.substring(3, 5) + "月" + date.substring(5, 7) + "日";
			}
		}

		return dateAry;
	}

	// 將時間加時分
	public static String MaskCTime(String sTime) {
		String sMaskTime = "";
		if ((sTime == null) || ("".equals(sTime))) {
			return "";
		} else {
			int iSLen = sTime.length();
			if (iSLen <= 2) {
				sMaskTime = sTime;
			}
			if ((iSLen > 2) && (iSLen <= 4)) {
				if (!"0".equals(sTime.substring(0, 1)))
					sMaskTime = sTime.substring(0, 2) + "時";
				else
					sMaskTime = sTime.substring(1, 2) + "時";
				if (!"0".equals(sTime.substring(2, 3)))
					sMaskTime += sTime.substring(2, iSLen) + "分";
				else
					sMaskTime += sTime.substring(3, iSLen) + "分";
			}
			if (iSLen > 4) {
				if (!"0".equals(sTime.substring(0, 1)))
					sMaskTime = sTime.substring(0, 2) + "時";
				else
					sMaskTime = sTime.substring(1, 2) + "時";
				if (!"0".equals(sTime.substring(2, 3)))
					sMaskTime += sTime.substring(2, 4) + "分";
				else
					sMaskTime += sTime.substring(3, 4) + "分";
				if (!"0".equals(sTime.substring(4, 5)))
					sMaskTime += sTime.substring(4, iSLen) + "秒";
				else
					sMaskTime += sTime.substring(5, iSLen) + "秒";
			}
			return sMaskTime;
		}
	}

	// 將時間格式化
	public static String MaskTime(String sTime) {
		String sMaskTime = "";
		if ((sTime == null) || ("".equals(sTime))) {
			return "";
		} else {
			int iSLen = sTime.length();
			if (iSLen <= 2) {
				sMaskTime = sTime;
			}
			if ((iSLen > 2) && (iSLen <= 4)) {
				sMaskTime = sTime.substring(0, 2) + ":";
				sMaskTime += sTime.substring(2, iSLen) + "";
//				if(!"0".equals(sTime.substring(0,1)))sMaskTime=sTime.substring(0,2)+":";
//				else sMaskTime=sTime.substring(1,2)+":";
//				if(!"0".equals(sTime.substring(2,3)))sMaskTime+=sTime.substring(2,iSLen)+"";
//				else sMaskTime+=sTime.substring(3,iSLen)+"";
			}
			if (iSLen > 4) {
				sMaskTime = sTime.substring(0, 2) + ":";
				sMaskTime += sTime.substring(2, 4) + ":";
				sMaskTime += sTime.substring(4, iSLen) + "";
//				
//				if(!"0".equals(sTime.substring(0,1)))sMaskTime=sTime.substring(0,2)+":";
//				else sMaskTime=sTime.substring(1,2)+":";
//				if(!"0".equals(sTime.substring(2,3)))sMaskTime+=sTime.substring(2,4)+":";
//				else sMaskTime+=sTime.substring(3,4)+":";
//				if(!"0".equals(sTime.substring(4,5)))sMaskTime+=sTime.substring(4,iSLen)+"";
//				else sMaskTime+=sTime.substring(5,iSLen)+"";
			}
			return sMaskTime;
		}
	}

	/**
	 * 將YYYMMDD日期轉化成比較易讀的格式
	 * 
	 * @param YYYMMDD
	 * @param format  = 1, 2, 3, 4
	 * @return YYY.MM.DD, YYY年MM月DD日, 民國YYY年MM月DD日, YYY/MM/DD, YYY-MM-DD
	 */
	static public String formatYYYMMDD(String s, int format) {
		if (s != null && s.length() == 7) {
			try {
				switch (format) {
				case 1:
					return s.substring(0, 3) + "." + s.substring(3, 5) + "." + s.substring(5);
				case 2:
					return s.substring(0, 3) + "年" + s.substring(3, 5) + "月" + s.substring(5) + "日";
				case 3:
					return "民國" + s.substring(0, 3) + "年" + s.substring(3, 5) + "月" + s.substring(5) + "日";
				case 4:
					return s.substring(0, 3) + "/" + s.substring(3, 5) + "/" + s.substring(5);
				case 5:
					return s.substring(0, 3) + "-" + s.substring(3, 5) + "-" + s.substring(5);
				}
				return s.substring(0, 3) + "." + s.substring(3, 5) + "." + s.substring(5);
			} catch (Exception e) {
				return s;
			}
		} else if (s != null && s.length() == 5) {
			try {
				switch (format) {
				case 1:
					return s.substring(0, 3) + "." + s.substring(3, 5);
				case 2:
					return s.substring(0, 3) + "年" + s.substring(3, 5) + "月";
				case 3:
					return "民國" + s.substring(0, 3) + "年" + s.substring(3, 5);
				case 4:
					return s.substring(0, 3) + "/" + s.substring(3, 5);
				case 5:
					return s.substring(0, 3) + "-" + s.substring(3, 5);
				}
				return s.substring(0, 3) + "." + s.substring(3, 5);
			} catch (Exception e) {
				return s;
			}
		}
		return get(s);
	}

	public static String[] getColumnNames(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		String[] colNames = new String[md.getColumnCount()];
		for (int i = 0; i < colNames.length; i++) {
			String colName = md.getColumnName(i + 1);
			colNames[i] = colName;
		}
		return colNames;
	}

	public static String[] getQueryArrayFromString(String v) {
		if (v != null && v.length() > 0) {
			v = StringUtils.replace(v, "（", "");
			v = StringUtils.replace(v, "）", "");
			return v.split(",");
		}
		return new String[0];
	}

	public static String getInsertStringFromArray(String[] v) {
		if (v != null && v.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : v) {
				sb.append("（").append(s).append("）").append(",");
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	public static String getCodeToName(String codeKind, String v) {
		Database db = new Database();
		String[] ps = getQueryArrayFromString(v);
		String name = "";
		try {
			String sql = "";
			ResultSet rs = null;
			for (String p : ps) {
				sql = "select ID,Name from SYSAP_CodeKind where [Key]=" + Common.sqlChar(codeKind) + " and ID=" + p;
				rs = db.querySQL(sql);
				while (rs.next()) {
					name = rs.getString("name") + "、";
				}
				rs.close();
			}
			name = name.substring(0, name.length() - 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

		return name;

	}

	public static String getMaxClosingYM(String enterOrg) {
		Database db = new Database();
		ResultSet rs;
		String closingYM = "00000";
		try {
			rs = db.querySQL("select max(closingYM) as closingYM from UNTGR_Closing where enterOrg = "
					+ Common.sqlChar(enterOrg));
			if ((rs.next()) && (get(rs.getString(1)).length() == 5)) {
				closingYM = get(rs.getString("closingYM"));
				// ==null?"00000":rs.getString("closingYM");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return closingYM;
	}

	// 價值格式
	public static String valueFormat(String money) {
		String formatString = new String();
		DecimalFormat df = new DecimalFormat("###,###,###,###,###,###");
		if (money != null && !money.equals("")) {
			try {
				formatString = df.format(Double.parseDouble(money));
			} catch (NumberFormatException e) {
				formatString = money;
			}
		} else {
			formatString = money;
		}
		return formatString;
	}

	static public int toInt(String s) {
		try {
			if (s == null || s.trim().equals(""))
				return 0;
			else
				return Integer.parseInt(s);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 數字金額轉國字金額:可以的話請直接使用numToZh(double val)
	 * 
	 * @param BigDecimal
	 */
	public static String numToZh(java.math.BigDecimal bigdMoneyNumber) {
		return numToZh(bigdMoneyNumber.doubleValue());
	}

	/**
	 * 數字金額轉國字金額:可以的話請直接使用numToZh(double val)
	 * 
	 * @param String
	 */
	public static String numToZh(String value) {
		return numToZh(Double.parseDouble(value));
	}

	/**
	 * 數字金額轉國字金額
	 * 
	 * @param val double 金額
	 * @return String
	 */
	public static String numToZh(double val) {
		String HanDigiStr[] = new String[] { "零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖" };
		String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "億", "拾", "佰", "仟", "萬", "拾", "佰",
				"仟", "億", "拾", "佰", "仟", "萬", "拾", "佰", "仟" };
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;
		String RMBStr = "", NumStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 億、萬進位元前有數值標記
		int len, n;
		// 數字金額判斷
		if (val < 0) {
			val = -val;
			SignStr = "負";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999) {
			return "數值位數過大!";
		}
		// 四捨五入到分
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;

		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0) {
				TailStr += "角";
			}
			if (integer == 0 && jiao == 0) { // 零元後不寫零幾分
				TailStr = "";
			}
			if (fen != 0) {
				TailStr += HanDigiStr[fen] + "分";
			}
		}

		// 判斷小數點前字串
		NumStr = String.valueOf(integer);
		len = NumStr.length();
		if (len > 15) {
			return "數值過大!";
		}
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ') {
				continue;
			}
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9) {
				return "輸入含非數位字元!";
			}
			if (n != 0) {
				if (lastzero) {
					RMBStr += HanDigiStr[0]; // 若干零後若跟非零值，只顯示一個零
					// 除了億萬前的零不帶到後面
					// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) // 如十進位前有零也不發壹音用此行
				}
				// if (! (n == 1 && (i % 4) == 1 && i == len - 1)) { // 十進位處於第一位不發壹音
				if (!(n == 0 && (i % 4) == 1 && i == len - 1)) { // 十進位處於第一位不發壹音
					RMBStr += HanDigiStr[n];
				}
				RMBStr += HanDiviStr[i]; // 非零值後加進位，個位為空
				hasvalue = true; // 置萬進位元前有值標記

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) { // 億萬之間必須有非零值方顯示萬
					RMBStr += HanDiviStr[i]; // “億”或“萬”
				}
			}
			if (i % 8 == 0) {
				hasvalue = false; // 萬進位元前有值標記逢億重定
			}
			lastzero = (n == 0) && (i % 4 != 0);
		}
		if (RMBStr.length() == 0) {
			RMBStr = HanDigiStr[0]; // 輸入空字元或"0"，返回"零"
		}
		return SignStr + RMBStr + "元" + TailStr;
	}

	// 直接呼叫 SQL File 執行
	// 需傳入檔名與所需變數,例:callSQLFile("\\untgr005r.sql test 383030000D 0950101 0950331");
	static public void callSQLFile(String sqlVariable) throws IOException {
		try {
			Properties p = new Properties();
			InputStream in = Common.class.getResourceAsStream("config.properties");
			p.load(in);
			in.close();

//			p.getProperty("cmdSQLPlus");			
			// Process proc = Runtime.getRuntime().exec("sqlplus kfcp/kfcp@march @" +
			// sqlVariable);
			Process proc = Runtime.getRuntime()
					.exec(p.getProperty("cmdSQLPlus") + p.getProperty("sqlFileLocation") + sqlVariable);
			InputStream stdin = proc.getInputStream();

			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer strBuffer = new StringBuffer();
			// System.out.println("執行: "+p.getProperty("cmdSQLPlus") +
			// p.getProperty("sqlFileLocation") + sqlVariable);
			while ((line = br.readLine()) != null) {
				// System.out.println(br.readLine());
				strBuffer.append(line).append("\n");
			}
			// int exitVal = proc.waitFor();
			// System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 直接呼叫 SQL File 讀取資料
	 * 需傳入檔名與所需變數,例:callSQLFileRead(getFileName()+"\\pubgr006r.sql",2,getEditID()+":;:PUBGR_pubgr006r");
	 * return String param int(傳入變數數量) param String(變數，多個時使用:;:區分)
	 **/
	static public String callSQLFileRead(String sqlVariable, int i, String variable) throws IOException {
		StringBuffer sbSQL = new StringBuffer("");
		String SQL = "";
		String check = "N";
		int j = i;
		int i_length = 0;
		String variables[] = new String[i];
		variables = variable.split(":;:");
		int variables_length = variables.length;
		try {
			int inputvalue;
			BufferedReader br = new BufferedReader(new java.io.FileReader(sqlVariable));
			String line = "";
			if (i == variables_length || i == 0) {
				while (br.ready()) {
					line = br.readLine();
					if (line == null)
						break;
					if (line.indexOf("--") == -1) {
						if (!line.equals("")) {
							if (line.equals(";")) {
								line = line.substring(0, line.indexOf(";"));
							}
							sbSQL.append(line + " ");
						}
					}
				}
				check = "Y";
			} else {
				System.out.println("變數的數量與傳入的變數值數量不符");
			}
			SQL = sbSQL.toString();
			if (i != 0 && "Y".equals(check)) {
				while (SQL.indexOf("&") > 0) {
					try {
						inputvalue = Integer.parseInt(SQL.substring(SQL.indexOf("&") + 1, SQL.indexOf("&") + 2));
					} catch (NumberFormatException e) {
						System.out.println("請使用&1、&2......等，當變數");
						break;
					}
					while (SQL.indexOf("&" + j) > 0) {
						i_length = String.valueOf(j).length();
						SQL = SQL.substring(0, SQL.indexOf("&" + j)) + variables[(j - 1)]
								+ SQL.substring((SQL.indexOf("&" + j) + (i_length + 1)));
					}
					j--;
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return SQL;
	}

	/**
	 * <br>
	 * <br>
	 * 目的：將user所鍵入之password經MD5加密 <br>
	 * 參數：已加密字串 <br>
	 * 傳回：傳回轉換後字串
	 */
	public static String getMD5PassWord(String srcText) {
		return getDigestString(srcText, "MD5");
	}

	/**
	 * 目的：將字串加密,若algorithm為null時,為SHA-1
	 * 
	 * @param srcText
	 * @param algorithm : MD5, SHA-1, SHA-256, SHA-384 and SHA-512
	 * @return
	 */
	public static String getDigestString(String srcText, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm != null ? algorithm : "SHA-1");
			md.update(srcText.getBytes());
			MessageDigest theClone = (MessageDigest) md.clone();
			byte[] digest = theClone.digest();
			return toHexString(digest);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * Turn a bytearray into a printable form, representing each byte in hex.
	 *
	 * @param data the bytearray to stringize
	 * @return a hex-encoded printable representation of <code>data</code>
	 */
	public static String toHexString(byte[] data) {
		StringBuffer sb = new StringBuffer(data.length * 2);
		for (int i = 0; i < data.length; ++i) {
			sb.append(Integer.toHexString((data[i] >> 4) & 15));
			sb.append(Integer.toHexString(data[i] & 15));
		}
		return sb.toString();
	}

	/**
	 * 將路徑過濾掉，只傳回檔名
	 */
	public static String getFilename(String fullname) {
		String filename = null;
		fullname = fullname.replace('\\', '/');
		StringTokenizer token = new StringTokenizer(fullname, "/");
		while (token.hasMoreTokens()) {
			filename = token.nextToken();
		}
		return filename;
	}

	/**
	 * 將傳入之號碼於前方補0，補滿 size 碼
	 *
	 * @param int       size 補滿幾碼
	 * @param strNumber String 欲補0的號碼
	 * @return String 補0後之號碼
	 */
	public String appendZero(int size, String strNumber) {
		for (; strNumber.length() < size;) {
			strNumber = "0" + strNumber;
		}
		return strNumber;
	}

	public static String MoneyFormat(Double money, int d) {
		if (money != null) {
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,###");
			if (d > 0) {
				df = new DecimalFormat("###,###,###,###,###,##0." + formatRearZero("", d));
			}
			return df.format(money);
		}

		return "";
	}

	public static String MoneyFormat(String money, int d) {
		return MoneyFormat(getNumeric(money), d);
	}

	/**
	 * <br>
	 * 傳回第一欄值 <br>
	 * 參數：SQL、CNT OR DATA
	 */
	public static String getFirstField(String sSQL, String sSQLType) {
		Database db = new Database();
		ResultSet rs;
		String sFirstField;
		String sChkSQL;
		sChkSQL = sSQL;
		sChkSQL = sChkSQL.toUpperCase();

		if ((sChkSQL.indexOf("UPDATE") != -1) || (sChkSQL.indexOf("DELETE") != -1)) {
			return "不可使用DML";
		}
		boolean bSQLType = sSQLType.equals("CNT");
		if (bSQLType) {
			sFirstField = "true";
		} else {
			sFirstField = "";
		}

		try {
			rs = db.querySQL(sSQL);
			if (rs.next()) {
				if (sSQLType.equals("CNT")) {
					if (rs.getInt(1) <= 0) {
						sFirstField = "true";
					} else {
						sFirstField = "false";
					}
				} else {
					sFirstField = Common.checkGet(rs.getString(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return sFirstField;
		} finally {
			db.closeAll();
		}
		return sFirstField;
	}

	/**
	 * <br>
	 * 取得SYSCA_ORGAN中的ORGANANAME <br>
	 * 參數：機關代碼
	 */
	public static String getORGANANAME(String sEnterOrg) {
		String sSQL = "SELECT ORGANANAME " + "\n" + "  FROM SYSCA_ORGAN" + "\n" + " WHERE ORGANID="
				+ Common.sqlChar(sEnterOrg);
		return getFirstField(sSQL, "DATA");
	}

	/**
	 * <br>
	 * 取得SYSCA_ORGAN中的ORGANANSNAME <br>
	 * 參數：機關代碼
	 */
	public static String getORGANASNNAME(String sEnterOrg) {
		String sSQL = "SELECT isnull(organsname,ORGANANAME) as ORGANANAME " + "\n" + "  FROM SYSCA_ORGAN" + "\n"
				+ " WHERE ORGANID=" + Common.sqlChar(sEnterOrg);
		return getFirstField(sSQL, "DATA");
	}

	/**
	 * <br>
	 * 切字斷行8字 <br>
	 * 參數：字
	 */
	public static String getCutStr(String sValue) {
		return getCutStr(sValue, 8);
	}

	/**
	 * <br>
	 * 切字斷行 <br>
	 * 參數：字、切幾字
	 */
	public static String getCutStr(String sValue, int iCutStrLen) {
		String sNValue = "", sTValue = "";
		int iMod = 0, iDiv = 0, iCutLen = 10;

		if (sValue == null) {
			sNValue = "";
		} else {
			int iStrLen = sValue.length();
			if (iStrLen <= iCutStrLen) {
				sNValue = sValue;
			} else {
				int iCount = 0, iLastEnd = 0;
				iMod = iStrLen % iCutStrLen;
				iDiv = iStrLen / iCutStrLen;
				// System.out.println("iMod " +iMod);
				// System.out.println("iDiv " +iDiv);
				if (iMod != 0) {
					iCount = iDiv;
					iLastEnd = iMod - iCutStrLen;
				} else {
					iCount = iDiv - 1;
					iLastEnd = 0;

				}
				int iStart = 0, iEnd = iCutStrLen;

				for (int j = 1; j <= iCount; j++) {
					sTValue = sValue.substring(iStart, iEnd);
					sNValue += sTValue + "\n";
					iStart += iCutStrLen;
					iEnd += iCutStrLen;
					// System.out.println("iStart " +iStart);
					// System.out.println("iEnd " +iEnd);
					// System.out.println("sNValue " +sNValue);
				}
				iEnd += iLastEnd;
				sTValue = sValue.substring(iStart, iEnd);
				sNValue += sTValue;

			}

		}

		return sNValue;
	}

	/**
	 * <br>
	 * 右補字串 <br>
	 * 參數：字、補至幾長、補的字元
	 */
	public static String rpad(String str, int fulllen, String fulltext) {
		String rv = "";
		int strlen = str.length();
		String Text = str;
		while (strlen < fulllen) {
			strlen += strlen + 1;
			Text = Text + fulltext;
		}
		rv = Text;
		return rv;
	}

	/**
	 * <br>
	 * 左補字串 <br>
	 * 參數：字、補至幾長、補的字元
	 */
	public static String lpad(String str, int fulllen, String fulltext) {
		String rv = "";
		int strlen = str.length();
		String Text = str;
		while (strlen < fulllen) {
			strlen += strlen + 1;
			Text = fulltext + Text;
		}
		rv = Text;
		return rv;
	}

	/**
	 * <br/>
	 * 將 otherlimityear 格式化 -> 格式化字串 最少一個%d 最多2個%d 發生任何意外無法正確格式化 則回傳原始otherlimityear
	 * 
	 * @param type           0 : %02d年 type 1: %02d年%02d月 default type0
	 * @param otherlimityear
	 * @return
	 */
	public static String formatOtherLimityear(int type, String otherlimityear) {
		String resultStr = otherlimityear;
		try {
			int oly = Integer.parseInt(otherlimityear);
			int year = oly / 12;
			int month = oly % 12;

			switch (type) {
			case 1:
				if (month > 0) {
					resultStr = String.format("%d年%d月", year, month);
				} else {
					resultStr = String.format("%d年", year);
				}
				break;
			default:
				resultStr = String.format("%d年", year);
				break;
			}
		} catch (Exception e) {

		}

		return resultStr;
	}

	/**
	 * 將使用者操作記錄寫入 SYSAP_RECORDSQL <br>
	 * sql：將要執行的SQL <br>
	 * enterorg：使用者所屬機關 (前端jsp所存organID資料) <br>
	 * empid：使用者代碼 (前端jsp所存userID資料) <br>
	 * funcode：程式代碼 <br>
	 * funname：功能路徑+程式名稱 (前端jsp所存objPath資料) <br>
	 * functionType：異動類型
	 */
	public static void insertRecordSql(String sql, String enterorg, String empid, String funcode, String funname,
			String functionType) {
		Database db = new Database();
		try {
			ResultSet rs = db
					.querySQL("select * from SYSAP_EMP where organid='" + enterorg + "' and empid='" + empid + "'");
			if (rs.next()) {
				String empname = rs.getString("empname");
				String groupid = rs.getString("groupid");
				String roleid = rs.getString("roleid");
				String unitid = rs.getString("unitid");
				String keeperno = rs.getString("keeperno");

				StringBuilder sb = new StringBuilder();

				sb.append("Insert into SYSAP_RECORDSQL");
				sb.append(
						" (enterorg, empid, empname, groupid, roleid, unitid, keeperno, sql, funcode, funname, editdate, edittime, functionType)");
				sb.append("Values");
				sb.append("(").append(sqlChar(enterorg)); // enterorg
				sb.append(", ").append(sqlChar(empid)); // empid
				sb.append(", ").append(sqlChar(empname)); // empname
				sb.append(", ").append(sqlChar(groupid)); // groupid
				sb.append(", ").append(sqlChar(roleid)); // roleid
				sb.append(", ").append(sqlChar(unitid)); // unitid
				sb.append(", ").append(sqlChar(keeperno)); // keeperno
				sb.append(", ").append(sqlChar(db._transSQLFormat(sql).replaceAll("'", "\""))); // sql
																								// 把要記錄的sql中的'換成"，避免錯誤
				sb.append(", ").append(sqlChar(funcode)); // funcode
				sb.append(", ").append(sqlChar(funname)); // funname
				sb.append(", ").append(sqlChar(Datetime.getYYYYMMDD())); // editdate
				sb.append(", ").append(sqlChar(Datetime.getHHMMSS())); // edittime
				sb.append(", ").append(sqlChar(functionType)); // functionType
				sb.append(")");

				db.excuteSQL(sb.toString());
			} else {
				System.out.println("no this man : organid='" + enterorg + "' and empid='" + empid + "'");
				System.out.println(funcode + " " + funname);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(funcode + " " + funname);
		} finally {
			db.closeAll();
		}
	}

	public static void insertRecordSql(String sql, String enterorg, String empid, String funcode, String funname) {
		insertRecordSql(sql, enterorg, empid, funcode, funname, "");
	}

	public static void insertCreateRecordSql(String sql, String enterorg, String empid, String funcode,
			String funname) {
		insertRecordSql(sql, enterorg, empid, funcode, funname, "insert");
	}

	public static void insertUpdateRecordSql(String sql, String enterorg, String empid, String funcode,
			String funname) {
		insertRecordSql(sql, enterorg, empid, funcode, funname, "update");
	}

	public static void insertDeleteRecordSql(String sql, String enterorg, String empid, String funcode,
			String funname) {
		insertRecordSql(sql, enterorg, empid, funcode, funname, "delete");
	}

	/**
	 * 串接字串 中間以、連結
	 * 
	 * @param strs
	 */
	public static String concatStrs(Object... strs) {
		return concatStrs("、", strs);
	}

	/**
	 * 串接字串 串接符號可自訂
	 * 
	 * @param concatSymbol
	 * @param strs
	 * @return
	 */
	public static String concatStrs(String concatSymbol, Object... strs) {
		StringBuilder str = new StringBuilder();
		if (strs != null && strs.length > 0) {
			for (Object tmpObj : strs) {
				String tmpStr = Common.get(tmpObj);
				if ("".equals(tmpStr)) {
					continue;
				}

				if (str.length() > 0) {
					str.append(concatSymbol);
				}
				str.append(tmpStr);
			}
		}
		return str.toString();
	}

	public static boolean isNumber(Object obj) {
		String str = Common.get(obj);
		return str.matches("[-+]?\\d*\\.?\\d+");
	}

	/**
	 * 將數值前方補零至指定的長度
	 * 
	 * @param d   : 數值
	 * @param len : 長度
	 * @return
	 */
	static public String formatFrontZero(int d, int len) {
		return formatFrontZero(Common.get(d), len);
	}

    public static String getUUID() {
    	return java.util.UUID.randomUUID().toString().replace("-","");
    }
    
	public static String getFileExtension(java.io.File f) {
		return getFileExtension(f.getName());
	}

	public static String getFileExtension(String s) {
		String ext = null;
		int i = get(s).lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

}