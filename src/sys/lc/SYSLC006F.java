/*
*<br>程式目的：土地建物標示代碼匯入(段名代碼表txt)
*<br>程式代號：syslc006f
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.lc;

import java.io.File;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

public class SYSLC006F extends SuperBean {
	
	String fileName;
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	String logPath;
	public String getLogPath() { return checkGet(logPath); }
	public void setLogPath(String s) { logPath = checkSet(s); }
	
	private String nowRowCount;
	private int totalRowCount=0;
	public String getNowRowCount() { return nowRowCount;}
	public void setNowRowCount(String nowRowCount) { this.nowRowCount = nowRowCount; }	
	public int getTotalRowCount() { return totalRowCount; }
	public void setTotalRowCount(int totalRowCount) { this.totalRowCount = totalRowCount; }
	
	public String batchInsert(String datafile) {
		Database db = new Database();
		String editId = getUserID();
		String editDate = Datetime.getYYYYMMDD();
		String editTime = Datetime.getHHMMSS();
		
		try {
			StringBuilder dSQL = new StringBuilder();
			dSQL.append(" insert SYSCA_SIGN_BAK select a.signno, a.signname, a.signdesc, a.memo, a.seqno, ")
				.append(Common.sqlChar(editId)).append(" as editid, ").append(Common.sqlChar(editDate)).append(" as editdate, ").append(Common.sqlChar(editTime)).append(" as edittime, a.oldsignno ")
				.append(" from SYSCA_SIGN a ");
			String dSQL2 = " delete SYSCA_SIGN where 1 = 1 and signno not like '%0000' ";
			
			db.exeSQL_NoChange(dSQL.toString());
			db.exeSQL_NoChange(dSQL2);
		} catch(Exception e) {
				
		}
		
		String retrunMag = "";
		String inputStr = null;
		int index = 0;
		
		java.io.BufferedReader fr = null;
		try{
			File f = new File(datafile);
			if (f.exists()) {
				java.io.InputStreamReader isrNum = new java.io.InputStreamReader(new java.io.FileInputStream(f), "UTF-8");
				java.io.BufferedReader frNum = new java.io.BufferedReader(isrNum);
				
				//取得總筆數
				int numindex = 0;
				while ((inputStr = frNum.readLine()) != null) {
					try {
						numindex++;
						
					} catch(Exception ex2) {
						//當輸入發生錯誤時
						
					}
				}
				
				frNum.close();
				setTotalRowCount(numindex);
				numindex = 0;
				
				java.io.InputStreamReader isr = new java.io.InputStreamReader(new java.io.FileInputStream(f), "UTF-8");
				fr = new java.io.BufferedReader(isr);
				
				//檢查標題行欄位是否正確
				inputStr = fr.readLine();

				String[] colArrays = inputStr.split(",");
				
				String[] correctDataArrays = {"段", "小段", "代碼", "備註", "所區碼", "縣市名稱", "鄉鎮名稱", "事務所名稱"};
				
				boolean columnNameIsError = false;				
				if (colArrays.length == 8) {
					for (int i = 0; i < 8; i++) {
						if (columnNameIsError) {	
							break;
						}
						// 因檔案為UTF-8含有BOM格式時，他會產生一個"\uFEFF"字元在前面，因此將其剃除
						if (colArrays[i].replaceAll("\uFEFF", "").equals(correctDataArrays[i])) {	
							
						} else {
							columnNameIsError = true;
						}
					}	
				} else {
					columnNameIsError = true;
				}
		
				if (columnNameIsError) {
					throw new Exception();
				}
				
				StringBuilder sql = new StringBuilder();
				
				while ((inputStr = fr.readLine()) != null) {
					//去除雙引號中的逗號
					String[] checkArray = inputStr.split("\"");
					if (checkArray.length != 0) {
						for (int i = 0; i < checkArray.length; i++) {
							if (i % 2 == 0) {
								
							} else {
								checkArray[i] = checkArray[i].replaceAll(",", "");	
							}
						}
					}
					
					String lineStr = "";
					for (String s : checkArray) {
						lineStr += s;
					}
					String[] resultStr = lineStr.split(",");
					try {	
						
						index++;
						numindex++;
						
						//每100筆資料匯入一次
						if (index == 1) {
							sql.delete(0, sql.length());
							
							sql.append(" insert into SYSCA_SIGN ")
							.append(" ( ")
							.append(" signno, signname, signdesc, memo,")
							.append(" editid, editdate, edittime")
							.append(" )Values");
						}

						sql.append("(" + Common.sqlChar(resultStr[4].toString().substring(0, 1) + resultStr[4].toString().substring(2, 4) + resultStr[2].toString())).append(",");
						
						// memo為註銷時，signdesc後面須加上(註銷)
						if ("".equals(resultStr[1].toString())) {
							if ("註銷".equals(resultStr[3].toString())) {
								sql.append("N" + Common.sqlChar(resultStr[0].toString() + "段")).append(",")
								   .append("N" + Common.sqlChar(resultStr[5].toString() + resultStr[6].toString() + resultStr[0].toString() + "段(註銷)")).append(",");
							} else {
								sql.append("N" + Common.sqlChar(resultStr[0].toString() + "段")).append(",")
								   .append("N" + Common.sqlChar(resultStr[5].toString() + resultStr[6].toString() + resultStr[0].toString() + "段")).append(",");
							}
						} else {
							if ("註銷".equals(resultStr[3].toString())) {
								sql.append("N" + Common.sqlChar(resultStr[0].toString() + "段" + resultStr[1].toString() + "小段")).append(",")
								.append("N" + Common.sqlChar(resultStr[5].toString() + resultStr[6].toString() + resultStr[0].toString() + "段" + resultStr[1].toString() + "段(註銷)")).append(",");							
							} else {
								sql.append("N" + Common.sqlChar(resultStr[0].toString() + "段" + resultStr[1].toString() + "小段")).append(",")
									.append("N" + Common.sqlChar(resultStr[5].toString() + resultStr[6].toString() + resultStr[0].toString() + "段" + resultStr[1].toString() + "小段")).append(",");								
							}
						}
						
						sql.append(Common.sqlChar(resultStr[3].toString())).append(",")
							.append(Common.sqlChar(editId)).append(",")
							.append(Common.sqlChar(editDate)).append(",")
							.append(Common.sqlChar(editTime)).append("),");
						
						//因為包含title所以總行數需減1
						if (index == 100 || getTotalRowCount() - 1 == numindex) {
							try {
								db.exeSQL_NoChange(sql.toString().substring(0, sql.length() - 1));
								setNowRowCount(String.valueOf(numindex));
								retrunMag = "doImportSuccess";
								setNowRowCount("已完成!!");
								
							} catch(Exception ex2) {
								//當輸入發生錯誤時
								retrunMag = "doImportError";
								setNowRowCount("發生錯誤!!");
							}
						}
						
						//每100筆重新連線Database
						if (index >= 100) {
							db.closeAll();
							index = 0;
						}
						
						if (index == 0) {
							db = new Database();
						}
						
					} catch(Exception ex) {
						
					}
				}
			}
			
		} catch(Exception e) {
			retrunMag = "doImportError";
			setNowRowCount("發生錯誤!!");
		} finally {
			try {
				fr.close();
			} catch(Exception e) {
				
			}
		}
		return retrunMag;
	}

}

