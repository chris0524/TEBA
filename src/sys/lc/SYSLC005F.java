/*
*<br>程式目的：地政機關資料批次轉入
*<br>程式代號：syslc005f
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

public class SYSLC005F extends SuperBean {
	
	String tranYYYMM;
	String filePlace;
	String actionType;
	String countyNo;
	String officeNo;
	String ownership;
	String priceYear;
	String valueYear;
	String lsignno1;
	public String getTranYYYMM() { return checkGet(tranYYYMM); }
	public void setTranYYYMM(String s) { tranYYYMM = checkSet(s); }
	public String getFilePlace() { return checkGet(filePlace); }
	public void setFilePlace(String s) { filePlace = checkSet(s); }
	public String getActionType() { return checkGet(actionType); }
	public void setActionType(String s) { actionType = checkSet(s); }
	public String getcountyNo() { return checkGet(countyNo); }
	public void setcountyNo(String s) { countyNo = checkSet(s); }
	public String getofficeNo() { return checkGet(officeNo); }
	public void setofficeNo(String s) { officeNo = checkSet(s); }
	public String getownership() { return checkGet(ownership); }
	public void setownership(String s) { ownership = checkSet(s); }
	public String getpriceYear() { return checkGet(priceYear); }
	public void setpriceYear(String s) { priceYear = checkSet(s); }
	public String getvalueYear() { return checkGet(valueYear); }
	public void setvalueYear(String s) { valueYear = checkSet(s); }
	public String getLsignno1() {return checkGet(lsignno1);}
	public void setLsignno1(String lsignno1) {this.lsignno1 = checkSet(lsignno1);}
	
	String isOrganManager;
	String isAdminManager;
	String organID;
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }   
	
	String fileName;
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }

	String filePath;	
	public String getFilePath() {return checkGet(filePath);}
	public void setFilePath(String filePath) {this.filePath = checkSet(filePath);}

	boolean errorLog = false;
	public boolean getErrorLog() { return errorLog; }
	public void setErrorLog(boolean s) { errorLog = s; }
	
	String logPath;
	public String getLogPath() { return checkGet(logPath); }
	public void setLogPath(String s) { logPath = checkSet(s); }
	
	String xlsFileName = "";
	public void setXlsFileName(String s) { xlsFileName = Common.set(s); }
	public String getXlsFileName() { return Common.get(xlsFileName); }
	
	private String nowRowCount;
	private int totalRowCount=0;
	public String getNowRowCount() {return nowRowCount;}
	public void setNowRowCount(String nowRowCount) {this.nowRowCount = nowRowCount;}	
	public int getTotalRowCount() {return totalRowCount;}
	public void setTotalRowCount(int totalRowCount) {this.totalRowCount = totalRowCount;}
	
	public double toDouble(String s) {
		try {
			if (s == null || s.trim().equals(""))
				return 0;
			else
				return Double.parseDouble(s);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public String batchInsert(String datafile) {
		Database db = new Database();
		
		String tranyyymm = getTranYYYMM();//轉入年月
		if (tranyyymm.length() == 5) {
			tranyyymm = Datetime.changeTaiwanYYMM(getTranYYYMM(), "2");
		} else {
			tranyyymm = "";
		}
		
		//依據畫面的「轉入年月tranYYYMM」刪除「地政土地資料檔SYSLC_Land」資料。
		try{
			String dSQL2 = " delete from SYSLC_LAND where 1=1 " +
				" and tranyyymm = " + Common.sqlChar(tranyyymm);
			
			db.exeSQL(dSQL2);
			db.doCommit();
		}catch(Exception e){
				
		}finally{
			db.closeAll();
		}
		String retrunMag = "";
		String inputStr=null;
		String sql=null;
		int index=0;
		
		java.io.BufferedReader fr = null;
		try{
			File f=new File(datafile);
			if(f.exists()){
				//解決UTF-8讀取問題用
//				java.io.InputStreamReader isrNum=new java.io.InputStreamReader(new java.io.FileInputStream(f),"UTF-8");
				java.io.InputStreamReader isrNum=new java.io.InputStreamReader(new java.io.FileInputStream(f));
				java.io.BufferedReader frNum=new java.io.BufferedReader(isrNum);
				
				//取得總筆數
				int numindex=0;
				while((inputStr=frNum.readLine())!=null){
					String[] resultStr=inputStr.split(",");
					try{
						//檢查第一個欄位是否為數字, 若不是則跳過此行
						Integer.parseInt(resultStr[0].toString());
						
						numindex++;
						
					}catch(Exception ex2){
						//當輸入發生錯誤時
						
					}
					
				}
				frNum.close();
				setTotalRowCount(numindex);
//				doQuery();
				numindex=0;
				
//				java.io.InputStreamReader isr=new java.io.InputStreamReader(new java.io.FileInputStream(f),"UTF-8");
				java.io.InputStreamReader isr=new java.io.InputStreamReader(new java.io.FileInputStream(f));
				fr = new java.io.BufferedReader(isr);
				
				//檢查標題行欄位是否正確
				inputStr = fr.readLine();
				String[] colArrays = inputStr.split(",");
				
				String[] correctDataArrays = {"管理者統一編號", "管理者姓名", "縣市-代碼", "縣市-名稱", "鄉鎮市區-代碼", 
											"鄉鎮市區-名稱", "段（小段）-代碼", "段（小段）-名稱", "地號", "面積", 
											"所有權登記次序", "所有權人姓名", "所有權權利範圍類", "所有權權利範圍類別-名稱", "所有權權利範圍分母", 
											"所有權權利範圍分子", "使用分區-代碼", "使用分區-名稱", "使用地類別-代碼", "使用地類別-名稱", 
											"公告土地現值", "申報地價日期", "申報地價"};
				
				boolean columnNameIsError = false;				
				if(colArrays.length == 23){
					for(int i = 0; i < 23; i++){
						if(columnNameIsError){	break;}

						if(colArrays[i].equals(correctDataArrays[i])){							
						}else{
							columnNameIsError = true;
						}
					}	
				}else{
					columnNameIsError = true;
				}
		
				if(columnNameIsError){
					throw new Exception();
				}
				
				while((inputStr=fr.readLine())!=null){
					//去除雙引號中的逗號
					String[] checkArray = inputStr.split("\"");
					if(checkArray.length != 0){
						for(int i=0;i<checkArray.length;i++){

							if(i%2 == 0){
								
							}else{
								checkArray[i] = checkArray[i].replaceAll(",", "");	
							}
						}
					}
					
					String lineStr = "";
					for(String s : checkArray){
						lineStr += s;
					}
					String[] resultStr = lineStr.split(",");
					try{
						//檢查第一個欄位是否為數字, 若不是則跳過此行
						Integer.parseInt(resultStr[0].toString());			
						index++;
						numindex++;
		
						String valuedate = resultStr[21].toString();//申報地價日期
						if (valuedate.length() == 5) {
							valuedate = Datetime.changeTaiwanYYMM(valuedate, "2");
						} else {
							valuedate = "";
						}
						
						sql="insert into syslc_land "+
							"("+
								" enterorg, serialno, name, tranyyymm, lsignno1, lsignno2,"+
								" lsignno3, lsignno4, area, registerserialno, ownershipname,"+
								" holddeno, holdnume, useseparate, usekind, pricedate,priceunit,"+
								" valuedate, valueunit, holdarearange, holdarearangename"+
							")Values("+
								Common.sqlChar(getOrganID())+","+
								Common.sqlChar(resultStr[0].toString())+","+
								Common.sqlChar(resultStr[1].toString())+","+
								Common.sqlChar(tranyyymm)+","+
								Common.sqlChar(resultStr[2].toString())+","+
								Common.sqlChar(Common.formatFrontZero(resultStr[4].toString(),2))+","+
								Common.sqlChar(Common.formatFrontZero(resultStr[6].toString(),4))+","+
								Common.sqlChar(resultStr[8].toString())+","+
								Common.sqlChar(resultStr[9].toString())+","+
								Common.sqlChar(resultStr[10].toString())+","+
								Common.sqlChar(resultStr[11].toString())+","+
								Common.sqlChar(resultStr[14].toString())+","+
								Common.sqlChar(resultStr[15].toString())+","+
								Common.sqlChar(resultStr[16].toString())+","+
								Common.sqlChar(resultStr[18].toString())+","+
								Common.sqlChar(Datetime.changeTaiwan(getpriceYear(), "2") + "01")+","+
								Common.sqlChar(resultStr[20].toString())+","+
								Common.sqlChar(valuedate)+","+
								Common.sqlChar(resultStr[22].toString())+","+
								"null,"+
								"null"+
							")";
				
						//每100筆重新連線Database
						if(index>=100){
							db.closeAll();
							index=1;
						}
						
						if(index==1){
							db = new Database();
						}
						
						try{
							db.exeSQL(sql);
							db.doCommit();
							setNowRowCount(String.valueOf(numindex));
							
						}catch(Exception ex2){
							//當輸入發生錯誤時
							
						}
//						doQuery();
					
					}catch(Exception ex){
						continue;						//檢查第一個欄位是否為數字, 若不是則跳過此行
					}
				}
				
			}
			retrunMag = "doImportSuccess";
			setNowRowCount("已完成!!");
		}catch(Exception e){
			retrunMag = "doImportError";
			setNowRowCount("發生錯誤!!");
		}finally{
			try{
				fr.close();
			}catch(Exception e){
				
			}
//			doQuery();
		}
		return retrunMag;
	}//batchInsert() end

}//class SYSOT001B end

