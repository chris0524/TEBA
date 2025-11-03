/*
*<br>程式目的：地政機關資料批次轉入
*<br>程式代號：syslc001f
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.lc;

import java.util.*;
import java.sql.ResultSet;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import util.Common;
import util.Database;
import util.SuperBean;

public class SYSLC001F extends SuperBean {

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
	
	private int nowRowCount=0;
	private int totalRowCount=0;
	public int getNowRowCount() {return nowRowCount;}
	public void setNowRowCount(int nowRowCount) {this.nowRowCount = nowRowCount;}	
	public int getTotalRowCount() {return totalRowCount;}
	public void setTotalRowCount(int totalRowCount) {this.totalRowCount = totalRowCount;}
	
	String[] errorArray=new String[16];
	    
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
	
    private void doQuery(){
    	Database db=new Database();
    	try{    		
    		String sql="update TimeTable set"+
    			" row_count = "+this.getNowRowCount()+" ,"+
    			" total_row_count = "+this.getTotalRowCount();
  		
    		db.querySQL(sql);
    	}catch(Exception e){
    		
    	}finally{
    		db.closeAll();
    	}
    }
	
    public String batchInsert(String datafile) {
    	Database db = new Database();
    	
    	try{
    		String dSQL2 = " delete from SYSLC_Land_Error where 1=1 ";
    		
    		db.exeSQL(dSQL2);
    		db.doCommit();
	   	}catch(Exception e){
	   		
	   	}finally{
	   		db.closeAll();
	   	}
	   	
	   	doQuery();
    	
		String retrunMag = "";
		int[] intT={0,1,0};
		
		try{
			while(true){
				intT=batchInsert2(datafile,intT);
				if(intT[0]==1){
					if(intT[2]==1){
						retrunMag="doImportLog";
					}else{
						retrunMag="doImportSuccess";
					}
					break;
				}
			}
		}catch(Exception e){
			if(intT[2]==1){
				retrunMag="doImportLog";
			}else{
				retrunMag="doImportSuccess";
			}
		}finally{
			System.out.println("Over");
		}
    	return retrunMag;
    }//batchInsert() end
    
    private int[] batchInsert2(String datafile, int[] intT)throws Exception{
    	Database db = null;
    	java.util.Iterator it=null;
    	
    	try {
			it= getJExcelModel(datafile,0,22,intT).iterator();
    		
			String sbSQL = new String();
						
			if(it.hasNext()) {
				String[] rowArray = new String[22];
	    		
				rowArray=(String[])(it.next());
				
				//去除千分位, 轉大寫
				for(int i=0;i<rowArray.length;i++){								
					rowArray[i]=rowArray[i].replaceAll(",", "");
					rowArray[i]=rowArray[i].toUpperCase();
					rowArray[i]=checkASCII(rowArray[i]);
				}
				
				sbSQL=doProcess0001(rowArray);

				String dSQL =null;
				
	    		if (errorArray[2].toString().equals("error")){		    			
	    		}else{
					dSQL = " delete from SYSLC_Land "
		    		    + " where 1=1 "
		    		    + " and tranYYYMM = " + Common.sqlChar(getTranYYYMM())
		    		    + " and RegisterSerialNo = " + Common.sqlChar(errorArray[7])
	    				+ " and lsignno1 = " + Common.sqlChar(errorArray[2])
	    				+ " and lsignno2 = " + Common.sqlChar(errorArray[3])
	    				+ " and lsignno3 = " + Common.sqlChar(errorArray[4]);
	    			if (errorArray[5].toString().equals("")){
		    		}else{
		    			dSQL += " and lsignno4 = " + Common.sqlChar(errorArray[5]);
		    		}
	    		}
	    		
				try{
					db = new Database();	
					db.setAutoCommit(false);					
					if(dSQL.equals(null)){
					}else{
						db.exeSQL(dSQL);
			    		db.doCommit();
					}
		    		db.querySQL(sbSQL);		    		
		    		db.doCommit();		    		
				}catch(Exception ex1){
					db.doRollback();
					intT[2]=1;

					Database db1;
					String errorStr="";
					
					String[] errorSQL={"SERIALNO","NAME","lsignNo1","lsignNo2","lsignNo3",
										"lsignNo4","area","REGISTERSERIALNO","ownershipName","holdDeno",
										"holdNume","useSeparate","useKind","priceUnit","valueDate",
										"valueUnit"};
					
					String[] excelTitleArray={"0","1","2","3","4",
												"5","6","7","8","9",
												"10","11","12","13","14",
												"15"};
					
					for(int i=0;i<errorSQL.length;i++){
							String strTemp="insert into SYSLC_Land ("+errorSQL[i].toString()+")Values(";

							if(Common.get(errorArray[i]).equals("error")){
							}else{
								strTemp+=(((Common.get(errorArray[i])).equals(""))?"null":Common.sqlChar(errorArray[i]))+")";
							}
							
							db1=new Database();
							try{
								db1.setAutoCommit(false);
								db1.querySQL(strTemp);
								db1.doRollback();
							}catch(Exception ex2){
								db1.doRollback();
								if(errorStr.equals("")){
									errorStr+=excelTitleArray[i];
								}else{
									errorStr+="、"+excelTitleArray[i];
								}
							}finally{
								db1.closeAll();
							}
					}

					String tsql=" insert into SYSLC_Land_Error ( "+
							" TRANYYYMM, "+
							" Column000, Column001, Column002, Column003, Column004, "+
							" Column005, Column006, Column007, Column008, Column009, "+
							" Column010, Column011, Column012, Column013, Column014, "+
							" Column015, Column016, Column017, Column018, Column019, "+
							" Column020, Column021, Column022 "+
							" )Values( ";
					
					tsql+=Common.sqlChar(this.getTranYYYMM())+",";
					
					for(int i=0;i<=rowArray.length;i++){
						if(i==(rowArray.length)){
							tsql+=Common.sqlChar(errorStr);
							tsql+=")";
						}else{
							tsql+=Common.get(rowArray[i]).equals("")?null:Common.sqlChar(rowArray[i]);
							tsql+=",";
						}
					}

					try{
						db.querySQL(tsql);
						db.doCommit();
					}catch(Exception ex2){
						db.doRollback();
//						System.out.println("錯誤檔案回寫資料庫時錯誤!! : "+tsql);
					}
	    		}finally{
	    			this.setNowRowCount(this.getNowRowCount()+1);
	    			this.doQuery();
	    			
	    			db.setAutoCommit(true);
//	    			db.closeAll();
	    		}
	    		intT[1]++;
			}else{
				intT[0]=1;
			}
			
    	} catch(Exception e) {
    		    throw new Exception();		
    	} finally {
    		it=null;
    		db.closeAll();
    	}
    	return intT;
    }
    
    //ReadExcelFile.java 中的getJExcelModel() 重新改寫
    private ArrayList getJExcelModel(String fileName, int sheetNumber, int intArraySize, int[] intT) throws Exception {
		setXlsFileName(fileName);
		ArrayList objList=new ArrayList();
		Workbook workbook = Workbook.getWorkbook(new File(fileName));
		
		try {		
			Sheet s = workbook.getSheet(sheetNumber);
			Cell[] row = null;
	        String rowArray[];
        	row = s.getRow(intT[1]);
        	this.setTotalRowCount(s.getRows()-1);
	        	
            if (row!=null & row.length > 0) {  		            	
            	rowArray = new String[intArraySize];	            	
            	for (int j = 0; j < intArraySize; j++) {
            		if (j>=row.length) rowArray[j] = "";
            		else rowArray[j] = Common.get(row[j].getContents()).replaceAll("'", "").replaceAll("\"", "");
            	}

        		objList.add(rowArray);
            }	        
 
		} catch(Exception e) {
			
		}finally{
			workbook.close();
		}
		return objList;
	}	
    
    
    //產生Excel檔案程式
	public void doProExcel(){
		String stemp="轉入錯誤明細.xls";
		setFileName(stemp);
		setFilePath(TD_getPath(stemp));
		doExcelProduct(getFilePath());
	}
	
    private String doProcess0001(String[] rowArray){
    	Database tdb=new Database();
    	ResultSet trs1 = null;
		ResultSet trs2 = null;
		ResultSet trs3 = null;
		String tsql1 = null;
		String tsql2 = null;
		String tsql3 = null;
		String sbSQL = null;
		errorArray[2]=null;
		errorArray[3]=null;
		errorArray[4]=null;
	
		sbSQL=" insert into syslc_land ( "+
					" tranYYYMM,"+
					" SERIALNO, NAME, lsignNo1, lsignNo2, lsignNo3," +
					" lsignNo4, area, REGISTERSERIALNO, ownershipName, holdDeno," +
					" holdNume, useSeparate, useKind, priceUnit, valueDate," +
					" valueUnit, priceDate"+
				") values ( ";
		
		sbSQL+=Common.sqlChar(getTranYYYMM())+",";

		//統一編號
		if(Common.get(rowArray[1]).equals("")){
			sbSQL+=null+",";
			errorArray[0]=null+",";
		}else{
			sbSQL+=Common.sqlChar(rowArray[1])+",";
			errorArray[0]=Common.get(rowArray[1]).equals("")?null:rowArray[1];
		}
		
		
		//姓名
		if(Common.get(rowArray[2]).equals("")){
			sbSQL+=null+",";
			errorArray[1]=null+",";
		}else{
			sbSQL+=Common.sqlChar(rowArray[2])+",";
			errorArray[1]=Common.get(rowArray[2]).equals("")?null:rowArray[2];
		}
		
		//轉換土地標示
		if(Common.get(rowArray[3]).equals("") && Common.get(rowArray[3]).equals("") && Common.get(rowArray[3]).equals("")){
			sbSQL+=null+","+
				null+","+
				null+",";
			errorArray[2]=null;
			errorArray[3]=null;
			errorArray[4]=null;
		}else{

			try{
				
				tsql1=" select signno from SYSCA_Sign where 1=1 and signname = '"+rowArray[5].toString()+"' and signdesc = '"+rowArray[3].toString()+rowArray[4].toString()+rowArray[5].toString()+"'";

				trs1=tdb.querySQL(tsql1);

				while(trs1.next()){
					sbSQL+=Common.sqlChar(trs1.getString("signno").substring(0,1))+","+
						Common.sqlChar(trs1.getString("signno").substring(1,3))+","+
						Common.sqlChar(trs1.getString("signno").substring(3,7))+",";
					
					errorArray[2]=trs1.getString("signno").substring(0,1);
					errorArray[3]=trs1.getString("signno").substring(1,3);
					errorArray[4]=trs1.getString("signno").substring(3,7);
				}
				if(Common.get(errorArray[2]).equals("")){
					errorArray[2]="error";
					errorArray[3]="error";
					errorArray[4]="error";
				}
				
			}catch(Exception ex){
				errorArray[2]="error";
				errorArray[3]="error";
				errorArray[4]="error";
			}finally{
				try{
					trs1.close();
				}catch(Exception e){
					
				}
				
				tsql1=null;
				tsql2=null;
				tsql3=null;

			}
			
			try{
				//地號
				if(Common.get(rowArray[6]).equals("")){
					sbSQL+=null;
					errorArray[5]="";
				}else{		
					if(rowArray[6].toString().replaceAll("-", "").length()<8){
						errorArray[5]="error";
					}else{
						sbSQL+=Common.sqlChar(rowArray[6].replaceAll("-", ""))+",";
						errorArray[5]=Common.get(rowArray[6]).equals("")?null:rowArray[6].replaceAll("-", "");
					}
				}
				
				//面積(㎡)
				if(Common.get(rowArray[7]).equals("")){
					sbSQL+=null+",";
					errorArray[6]=null;
				}else{
					sbSQL+=Common.sqlChar(rowArray[7])+",";
					errorArray[6]=Common.get(rowArray[7]).equals("")?null:rowArray[7];
				}
				
				//登記次序
				if(Common.get(rowArray[8]).equals("")){
					sbSQL+=null+",";
					errorArray[7]=null;
				}else{
					sbSQL+=Common.sqlChar(rowArray[8])+",";
					errorArray[7]=Common.get(rowArray[8]).equals("")?null:rowArray[8];
				}
				
				//所有權人
				if(Common.get(rowArray[9]).equals("")){
					sbSQL+=null+",";
					errorArray[8]=null;
				}else{
					sbSQL+=Common.sqlChar(rowArray[9])+",";
					errorArray[8]=Common.get(rowArray[9]).equals("")?null:rowArray[9];
				}
			
				//權利範圍
				if(Common.get(rowArray[10]).equals("")){
					sbSQL+=null+",";
					errorArray[9]=null;
					errorArray[10]=null;
				}else{
					String[] tstr=new String[2];
	
					tstr=((rowArray[10].replaceAll("全部", "")).replaceAll("分之", ",")).split(",");
	
					sbSQL+=Common.sqlChar(tstr[0].toString())+","+
							Common.sqlChar(tstr[1].toString())+",";
					errorArray[9]=tstr[0].toString();
					errorArray[10]=tstr[1].toString();
				}
	
				//使用分區
				if(Common.get(rowArray[11]).equals("")){
					sbSQL+=null+",";
					errorArray[11]=null;
				}else{
					tsql1=" select CodeID from  SYSCA_code where codekindid='SEP' and codename like '"+rowArray[11].toString()+"%'";
					trs1=tdb.querySQL(tsql1);
					if(trs1.next()){
						sbSQL+=Common.sqlChar(trs1.getString("CodeID"))+",";
					}else{
						errorArray[11]="error";
					}
					trs1.close();
					tsql1=null;					
				}
			
				//使用地
				if(Common.get(rowArray[12]).equals("")){
					sbSQL+=null+",";
					errorArray[12]=null;
				}else{
					tsql1=" select CodeID from  SYSCA_code where codekindid='UKD' and codename like '"+rowArray[12].toString()+"%'";
					trs1=tdb.querySQL(tsql1);
					if(trs1.next()){
						sbSQL+=Common.sqlChar(trs1.getString("CodeID"))+",";						
					}else{
						errorArray[12]="error";
					}
					trs1.close();
					tsql1=null;
				}
			
				
				
				//公告土地現值
				if(Common.get(rowArray[13]).equals("")){
					sbSQL+=null+",";
					errorArray[13]=null;
				}else{
					sbSQL+=Common.sqlChar(rowArray[13])+",";
					errorArray[13]=Common.get(rowArray[13]).equals("")?null:rowArray[13];
				}
				
				//申報地價日期
				if(Common.get(rowArray[14]).equals("")){
					sbSQL+=null+",";
					errorArray[14]=null;
				}else{
					sbSQL+=Common.sqlChar(rowArray[14])+",";
					errorArray[14]=Common.get(rowArray[14]).equals("")?null:rowArray[14];
				}
				
				//申報地價
				if(Common.get(rowArray[15]).equals("")){
					sbSQL+=null+")";
					errorArray[15]=null;
				}else{
					sbSQL+=Common.sqlChar(rowArray[15])+",";
					errorArray[15]=Common.get(rowArray[15]).equals("")?null:rowArray[15];
				}
				
				//公告土地現值日期
				sbSQL+=Common.sqlChar(getpriceYear()+"01")+")";
				
			}catch(Exception e){
				
			}finally{
				tdb.closeAll();
			}
		}
		return sbSQL;
    }
    
	//全形轉半形
	private String checkASCII(String s){ 
		String outStr = "";
		char[] chars = s.toCharArray();
		int tranTemp = 0;
		for(int j = 0; j < chars.length; j++){					
			tranTemp = (int)chars[j];
			if(tranTemp>=65248)	
				tranTemp -= 65248;	//此數字是 Unicode編碼轉為十進位 和 ASCII碼的 差
				
			outStr += (char)tranTemp;
		}
		
		return outStr;
	}
	
	/**
	 * 產生相對路徑
	 * @param String path 存放路徑名稱, 若無則為隨機檔名
	 * @return String 
	 */
	private String TD_getPath(String path){
		String pathString=null;
		try{
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			if(Common.get(path).equals("")){
				pathString = tempDirectory.getAbsoluteFile() + File.separator + Math.random();	
			}else{
				pathString = tempDirectory.getAbsoluteFile() + File.separator + path;
			}
		}catch(Exception e){
			return null;
		}
		return pathString;	
	}
	
	/**
	 * 產生Excel檔案
	 * @param String filepath 檔案存放路徑 (不含檔名)
	 * @param String enterorg 機關名稱
	 */
	private void doExcelProduct(String filepath){
		StringBuffer sql= new StringBuffer();
		try{
					
		//產生檔名
			WritableWorkbook workbook=TD_setExcel(filepath);
			
	   	//********* doExcel *********
		//********  01階01步    *********
			
			String[] stemp1={"土地基地標示","統一編號","姓名","市、縣(市)","鄉鎮市區",
							"段小段","地號","面積(㎡)","登記次序","所有權人",
							"權利範圍","使用分區","使用地類別","公告土地現值","申報地價日期",
							"申報地價","帳上未分割","持分比","擁有面積","財產編號",
							"","未入帳原因","錯誤原因"};
			
				String[] insert1={"","","","","","","","","","",
							"","","","","","","","","","",
							"","",""};
				int[] location1={2,3,4,5,6,7,8,9,10,11,
							12,13,14,15,16,17,18,19,20,21,
							22,23,24};
				int[] size1={40,20,30,20,20,20,20,20,20,20,
							20,20,20,20,20,20,20,20,20,20,
							20,20,50};
				sql.append(" select * from SYSLC_Land_Error ");
			
			TD_doExcel(workbook, "錯誤明細", 0, stemp1, insert1, location1, size1, sql.toString());
				sql.delete(0,sql.length());
			
			TD_closeExcel(workbook);
			
		}catch(Exception e){
			
		}finally{
			
		}
	}
	
	/**
	 * 新增Excel檔案
	 * @param String filepath 位置及檔名  
	 */
	private WritableWorkbook TD_setExcel(String filepath){
		WritableWorkbook workbook=null;
		try{
			workbook = Workbook.createWorkbook(new File(filepath));
		}catch(Exception e){
			
    	}
		return workbook;
	}
	
	/**
	 * 產生Excel檔內容
	 * @param WritableWorkbook workbook
	 * @param String PageName	頁籤名稱
	 * @param int PageNum	產生第幾個頁籤
	 * @param String[] TitleName 標題名稱
	 * @param String[] insert 額外插入的字串
	 * @param int[] location 資料庫的欄位位置
	 * @param int[] ColumnSize 欄位大小設定
	 * @param String proxool 欲查詢的資料庫 
	 * @param String sql 欲查詢的SQL語法
	 */
	private void TD_doExcel(WritableWorkbook workbook, String PageName,int PageNum, String[] TitleName, String[] insert, int[] location, int[] ColumnSize, String sql){
			Database db=new Database();
			ResultSet rs=null;
			
			try{
				WritableSheet sheet = workbook.createSheet(PageName, PageNum);
				
			//設定Excel檔案使用的字型
				WritableFont font= new WritableFont(WritableFont.createFont("新細明體"),12,WritableFont.NO_BOLD);
		    	WritableCellFormat cellFormat1 = new WritableCellFormat(font);
		    	WritableCellFormat cellFormat2 = new WritableCellFormat(font);
		    	cellFormat1.setBackground(jxl.format.Colour.BRIGHT_GREEN);	//設定CELL背景
		    	cellFormat2.setBackground(jxl.format.Colour.RED);		
	    		cellFormat1.setAlignment(jxl.format.Alignment.CENTRE);		//設定置中
	    		cellFormat2.setAlignment(jxl.format.Alignment.CENTRE);		
	    		cellFormat1.setBorder(jxl.format.Border.ALL, 
	    					jxl.format.BorderLineStyle.THIN, 
	    					jxl.format.Colour.BLACK);						//設定框線
	    		cellFormat2.setBorder(jxl.format.Border.ALL, 
    						jxl.format.BorderLineStyle.THIN, 
    						jxl.format.Colour.BLACK);
	    				
			//標頭
				//設定名稱
				try{
					for(int i=0;i<TitleName.length;i++){
						Label HeaderLabel11=new Label(i,0,TitleName[i]);
						
						//20120306 modify
						//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
						if(i==(TitleName.length-1)){
							HeaderLabel11.setCellFormat(cellFormat2);
						}else{
							HeaderLabel11.setCellFormat(cellFormat1);								
						}
						//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
						
						sheet.addCell(HeaderLabel11);
						sheet.setColumnView(i,ColumnSize[i]);		//設定欄位大小

					}
				}catch(Exception e){
					throw new Exception();
				}						
		
	    //********************************************************************

				//20120306 modify
				//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				String[] excelTitle={"統一編號","姓名","市、縣(市)","鄉鎮市區","段小段",
									"地號","面積(㎡)","登記次序","所有權人","權利範圍子號",
									"權利範圍母號","使用分區","使用地類別","公告土地現值","申報地價日期",
									"申報地價"};
				String[] sqlTArray=null;
				String sqlT="";
				int ti=0;
		
				int index=0;
				rs=db.querySQL(sql);
				//設定內容
				try{
					while(rs.next()){
						index++;
						for(int i=0;i<TitleName.length;i++){
							if(insert[i].equals("")){
								if(i==(TitleName.length-1)){
									sqlTArray=rs.getString(location[i]).split("、");
									for(int j=0;j<sqlTArray.length;j++){
										ti=Integer.parseInt(sqlTArray[j].toString());
										if(Common.get(sqlT).equals("")){
											sqlT+=excelTitle[ti];
										}else{
											sqlT+="、"+excelTitle[ti];
										}	
									}
									if(Common.get(sqlT).equals("")){
										
									}else{
										sqlT+="有錯誤!!";
									}
									
									sheet.addCell(new Label(i,index,sqlT));
								}else{
									sheet.addCell(new Label(i,index,rs.getString(location[i])));	
								}								
															//加入sheet
							}else{
								sheet.addCell(new Label(i,index,insert[i]));
															//加入sheet
							}
						}
						sqlT="";
						ti=0;
					}
					rs.close();
				}catch(Exception e){
					throw new Exception();
				}
				//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
				
			}catch(Exception e){
				
			}finally{
				db.closeAll();			
			}
	}

	/**
	 * 將產生檔案寫入Excel檔案
	 * @param String filepath 位置及檔名  
	 */
	private void TD_closeExcel(WritableWorkbook workbook){
		try{
			workbook.write();
        	workbook.close();
		}catch(Exception e){
			
    	}
	}
	
}//class SYSOT001B end

