
package unt.pd;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import util.Common;
import util.Database;
import util.SuperBean;


public class UNTPD005F extends SuperBean{

	private String actionType;
	private String filePlace;
	private String filePath;
	private String fileName;
	public String getActionType() {return checkGet(actionType);}
	public void setActionType(String actionType) {this.actionType = checkSet(actionType);}
	public String getFilePlace() {return checkGet(filePlace);}
	public void setFilePlace(String filePlace) {this.filePlace = checkSet(filePlace);}
	public String getFilePath() {return checkGet(filePath);}
	public void setFilePath(String filePath) {this.filePath = checkSet(filePath);}
	public String getFileName() {return checkGet(fileName);}
	public void setFileName(String fileName) {this.fileName = checkSet(fileName);}
	
	//必輸欄位
	private Map pkCols = new HashMap();
	//數字欄位
	private Map numCols = new HashMap();
	int totalColumns = 22;
	
	public String batchInsert(){
		String result = "";
		String[][] datas;
		List listError = new ArrayList();
		setPKCols();
		setNumCols();
		try {
			datas = read();
			for(int i=0; i<datas.length; i++){
				String[] rowData = datas[i];
				if(rowData.length != totalColumns ) {
					return "totalColumnIncorrect";
				}
				
				listError = checkPKNoData(rowData, listError);
				listError = checkNumData(rowData, listError);
				
				HashMap map = queryData(rowData[0], rowData[1], rowData[2]);
				
				boolean  isMatched =  checkDataMatch(rowData, map);
				if(!isMatched){
					return "checkDataMatch,"+(i+1);
				}
				
				boolean isReduced = checkDataReduce(rowData[0], rowData[3], rowData[5], rowData[6]); 
				
				if(map != null){
					if("".equals(map.get("checkresult")) || "0".equals(map.get("checkresult"))){  //第一次盤點
						if(isMatched){
							updateData(rowData[0], rowData[1], rowData[2], "1", rowData[12]);
						}else{
							updateData(rowData[0], rowData[1], rowData[2], "2", "0");
						}
					}
					
					if("2".equals(map.get("checkresult")) && (!"".equals(map.get("bookamount")) && !"0".equals(map.get("bookamount"))) ){  //2次以上的盤點
						if(isMatched){
							updateData(rowData[0], rowData[1], rowData[2], "1", rowData[12]);
						}
					}
				}
				
				if(map == null || isReduced){
					insertUNTPD_CHECKMOVABLE(rowData);
				}
				
					
				if(listError.size() > 0){
					return "checkDataValue,"+listError.toString();
				}
			}
			
			Iterator iter = listError.iterator();
			while(iter.hasNext()){
				System.out.println(iter.next());
			}
			
			if(datas.length >0){
				result = "importSucess";
			}else{
				result = "noData";
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	  //檢查匯入資料是否重覆
	  public HashMap queryData(String enterorg, String serialno1, String barCode){
		  Database db = new Database();
		  HashMap map = null; 
			  try{
					String sql = " select "+
					  "enterorg, serialno1, barcode, ownership, closingdate, propertyno, serialno,"+ 
					  "propertyname, propertyname1, nameplate, buydate, propertyunit,"+ 
					  "bookamount, bookvalue, sourcedate, limityear, useyear, keepunitname,"+ 
					  "keepername, place1name, actualamount, notes, checkresult "+  
					  "from UNTPD_CHECKMOVABLE " +
					  "where enterorg= '" + enterorg + "' ";
					  if(!"".equals(serialno1)){
						  sql += "and serialno1= " + Integer.parseInt(serialno1) +" ";
			  		  }
					  
					  if(!"".equals(barCode)){
						  sql +=  "and barCode='" + barCode + "' " ;
			  		  }

	
					ResultSet rs = db.querySQL(sql,true);
					if (rs.next()){
						map = new  HashMap(); 
						map.put("enterorg", Common.get(rs.getString("enterorg")));
						map.put("serialno1", Common.get(rs.getString("serialno1")));
						map.put("barcode", Common.get(rs.getString("barcode")));
						map.put("ownership", Common.get(rs.getString("ownership")));
						map.put("closingdate", Common.get(rs.getString("closingdate")));
						map.put("propertyno", Common.get(rs.getString("propertyno")));
						map.put("serialno", Common.get(rs.getString("serialno")));
						map.put("propertyname", Common.get(rs.getString("propertyname")));
						map.put("propertyname1", Common.get(rs.getString("propertyname1")));
						map.put("nameplate", Common.get(rs.getString("nameplate")));
						map.put("buydate", Common.get(rs.getString("buydate")));
						map.put("propertyunit", Common.get(rs.getString("propertyunit")));
						map.put("bookamount", Common.get(rs.getString("bookamount")));
						map.put("bookvalue", Common.valueFormat(Common.get(rs.getString("bookvalue"))));
						map.put("sourcedate", Common.get(rs.getString("sourcedate")));
						map.put("limityear", Common.get(rs.getString("limityear")));
						map.put("useyear", Common.get(rs.getString("useyear")));
						map.put("keepunitname", Common.get(rs.getString("keepunitname")));
						map.put("keepername", Common.get(rs.getString("keepername")));
						map.put("place1name", Common.get(rs.getString("place1name")));
						map.put("actualamount", Common.get(rs.getString("actualamount")));
						map.put("notes", Common.get(rs.getString("notes")));
						map.put("checkresult", Common.get(rs.getString("checkresult")));
					}
					rs.close();
				  
			  }catch(Exception e){
				  e.getStackTrace();
			  } finally {
					db.closeAll();
			  }
			
			return map;
	  }
	
	  public boolean checkDataMatch(String[] rowData, HashMap map){
		  boolean isMatched = true;
		  if(map == null ){
			  System.out.println("HashMap map is null");
			  return false;
		  }
		  if(!rowData[12].equals(rowData[20])){
			  System.out.println("數量與盤點數量欄位比對不符");
			  return false;
		  }
		  
//		  if(!rowData[0].trim().equals(map.get("enterorg"))){
//			  System.out.println("enterorg 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[1].trim().equals(map.get("serialno1"))){
//			  System.out.println("serialno1 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[2].trim().equals(map.get("barcode"))){
//			  System.out.println("barcode 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[3].trim().equals(map.get("ownership"))){
//			  System.out.println("ownership 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[4].trim().equals(map.get("closingdate"))){
//			  System.out.println("closingdate 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[5].trim().equals(map.get("propertyno"))){
//			  System.out.println("propertyno 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[6].trim().equals(map.get("serialno"))){
//			  System.out.println("serialno 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[7].trim().equals(map.get("propertyname"))){
//			  System.out.println("propertyname 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[8].trim().equals(map.get("propertyname1"))){
//			  System.out.println("propertyname1 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[9].trim().equals(map.get("nameplate"))){
//			  System.out.println("nameplate 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[10].trim().equals(map.get("buydate"))){
//			  System.out.println("buydate 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[11].trim().equals(map.get("propertyunit"))){
//			  System.out.println("propertyunit 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[12].trim().equals(map.get("bookamount"))){
//			  System.out.println("bookamount 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[13].trim().equals(map.get("bookvalue"))){
//			  System.out.println("bookvalue 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[14].trim().equals(map.get("sourcedate"))){
//			  System.out.println("sourcedate 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[15].trim().equals(map.get("limityear"))){
//			  System.out.println("limityear 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[16].trim().equals(map.get("useyear"))){
//			  System.out.println("useyear 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[17].trim().equals(map.get("keepunitname"))){
//			  System.out.println("keepunitname 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[18].trim().equals(map.get("keepername"))){
//			  System.out.println("keepername 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[19].trim().equals(map.get("place1name"))){
//			  System.out.println("place1name 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[20].trim().equals(map.get("actualamount"))){
//			  System.out.println("actualamount 欄位資料比對不符");
//			  return false;
//		  }
//		  if(!rowData[21].trim().equals(map.get("notes"))){
//			  System.out.println("notes 欄位資料比對不符");
//			  return false;
//		  }
		  return isMatched;
	  }
	
	  public boolean checkDataReduce(String enterorg, String ownership, String propertyno, String serialno){
		  Database db = new Database();
		  boolean isReduced = false ;  
			  try{
					String sql = " select datastate from UNTPD_MOVABLEDETAIL where " +
							"enterorg= '" + enterorg + "' and ownership= '" + ownership + "' and propertyno='" + propertyno + "' and " +
							"serialno= '"+ serialno +"' ";
	
					ResultSet rs = db.querySQL(sql,true);
					if (rs.next()){
						if("2".equals(rs.getString("datastate"))){
							isReduced = true ;
						}
					}
					rs.close();
				  
			  }catch(Exception e){
				  e.getStackTrace();
			  } finally {
					db.closeAll();
			  }
			
			return isReduced;
	  }
	
	  public void updateData(String enterorg, String serialno1, String barCode, String checkResult, String actualAmount) {
		  Database db = new Database(); 
			  try{
				   db.setAutoCommit(false);
					StringBuffer UpdateSQL = new StringBuffer("");
					UpdateSQL.append("update UNTPD_CHECKMOVABLE ").append(
					" set ").append(
					" checkresult = ").append(Common.sqlChar(checkResult)).append(",").append(
					" actualamount = ").append(actualAmount).append(
					" where enterorg=").append(Common.sqlChar(enterorg)).append(
					" and serialno1=").append(serialno1).append(
					" and barcode=").append(Common.sqlChar(barCode));
					db.excuteSQL(UpdateSQL.toString());
					db.doCommit();
			  }catch(Exception e){
				  e.printStackTrace();
			  } finally {
					db.closeAll();
			  }
	  }
	
	
	  public String[][] read() throws IOException  {
			String[][] rowData = null;
		    File inputWorkbook = new File(this.getFilePath());
		    Workbook w;
		    try {
		      w = Workbook.getWorkbook(inputWorkbook);
		      // Get the first sheet
		      Sheet sheet = w.getSheet(0);
		      // Loop over first 10 column and lines
		      rowData = new String[sheet.getRows()-1][totalColumns];
		      for (int i = 1; i < sheet.getRows(); i++) {
		        for (int j = 0; j < totalColumns; j++) {
		        	  if(j == sheet.getColumns() && sheet.getColumns() < totalColumns){
		        		  rowData[i-1][j]= "";
		        		  System.out.println("");
		        	  }else{
				          Cell cell = sheet.getCell(j, i);
				          rowData[i-1][j]= cell.getContents();
				          System.out.println(cell.getContents());
		        	  }
		        }
		      }
		    } catch (BiffException e) {
		      e.printStackTrace();
		    }
		    return rowData;
	  }
	  
	  public void insertUNTPD_CHECKMOVABLE(String[]rowData){
		  Database db = new Database();
		 try {
		      db.setAutoCommit(false);
			   StringBuffer sbSQL = new StringBuffer();
			   sbSQL.append(" insert into UNTPD_CHECKMOVABLE(" )
			  .append("enterorg, ").append("serialno1,").append("barcode, ").append("ownership,").append("closingdate, ").append("propertyno,")  
			  .append("serialno,").append("propertyname, ").append("propertyname1,").append("nameplate,").append("buydate, ").append("propertyunit,") 
			  .append("bookamount, ").append("bookvalue,").append("sourcedate, ").append("limityear,").append("useyear, ").append("keepunitname,") 
			  .append("keepername, ").append("place1name, ").append("actualamount, ").append("notes")
			  .append(") values ( ")
			  .append(Common.sqlChar(rowData[0])).append( "," )
			  .append(rowData[1]).append( "," )
			  .append(Common.sqlChar(rowData[2])).append( "," )
			  .append(Common.sqlChar(rowData[3])).append( "," )
			  .append(Common.sqlChar(rowData[4])).append( "," )
			  .append(Common.sqlChar(rowData[5])).append( "," )
			  .append(Common.sqlChar(rowData[6])).append( "," )
			  .append(Common.sqlChar(rowData[7])).append( "," )
			  .append(Common.sqlChar(rowData[8])).append( "," )
			  .append(Common.sqlChar(rowData[9])).append( "," )
			  .append(Common.sqlChar(rowData[10])).append( "," )
			  .append(Common.sqlChar(rowData[11])).append( "," )
			  .append(rowData[12]).append( "," )
			  .append(rowData[13]).append( "," )
			  .append(Common.sqlChar(rowData[14])).append( "," )
			  .append(Common.sqlChar(rowData[15])).append( "," )
			  .append(Common.sqlChar(rowData[16])).append( "," )
			  .append(Common.sqlChar(rowData[17])).append( "," )
			  .append(Common.sqlChar(rowData[18])).append( "," )
			  .append(Common.sqlChar(rowData[19])).append( "," )
			  .append(rowData[20]).append( "," )
			  .append(Common.sqlChar(rowData[21]))
			  .append( ");" );

			  db.exeSQL(sbSQL.toString());
			  db.doCommit();
		 } catch (Exception e) {
			e.printStackTrace();
		 } finally{
			 db.closeAll();
		 }
		
	  }

			
	
		private List checkPKNoData(String[] data, List listError){
			for(int i=0; i<data.length; i++){
				if(data[i] == ""){
						if(pkCols.containsKey((i+1))){
							listError.add("第" + (i+1) + "行必填欄位[" + pkCols.get(i+1) + "]沒有資料！！");
						}
				}
			}
			
			return listError;
		}
		
		private List checkNumData(String[] data, List listError){
			for(int i=0; i<data.length; i++){
				if(!isNumeric(data[i]) || "".equals(data[i])){
						if(numCols.containsKey((i+1))){
							listError.add("第" + (i+1) + "行[" + numCols.get(i+1) + "]必須為數字欄位！！");
						}
				}
			}
			
			return listError;
		}
			
		   //必輸欄位設定
		private void setPKCols(){
			pkCols.put(1, "入帳機關");
			pkCols.put(2, "盤點序號");
//			pkCols.put(3, "條碼");
//			pkCols.put(4, "權屬");
//			pkCols.put(5, "資料截止日期");
//			pkCols.put(6, "財產編號");
//			pkCols.put(7, "財產分號");
//			pkCols.put(8, "財產名稱");
//			pkCols.put(9, "財產別名");
//			pkCols.put(10, "型式/廠牌");
//			pkCols.put(11, "購置日期");
//			pkCols.put(12, "單位");
			pkCols.put(13, "數量");
//			pkCols.put(14, "價值");
//			pkCols.put(15, "取得日期");
//			pkCols.put(16, "使用年限");
//			pkCols.put(17, "已使用年數");
//			pkCols.put(18, "保管單位");
//			pkCols.put(19, "保管人");
//			pkCols.put(20, "存置地點");
//			pkCols.put(21, "盤點數量");
//			pkCols.put(22, "備註");
		}
		
		//數字欄位設定
		private void setNumCols(){
//			numCols.put(1, "入帳機關");
			numCols.put(2, "盤點序號");
//			numCols.put(3, "條碼");
//			numCols.put(4, "權屬");
//			numCols.put(5, "資料截止日期");
//			numCols.put(6, "財產編號");
//			numCols.put(7, "財產分號");
//			numCols.put(8, "財產名稱");
//			numCols.put(9, "財產別名");
//			numCols.put(10, "型式/廠牌");
//			numCols.put(11, "購置日期");
//			numCols.put(12, "單位");
			numCols.put(13, "數量");
			numCols.put(14, "價值");
//			numCols.put(15, "取得日期");
//			numCols.put(16, "使用年限");
//			numCols.put(17, "已使用年數");
//			numCols.put(18, "保管單位");
//			numCols.put(19, "保管人");
//			numCols.put(20, "存置地點");
			numCols.put(21, "盤點數量");
//			numCols.put(22, "備註");
		}
			
			public boolean isNumeric(String str){
				
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if( !isNum.matches() ){
				return false;
			}
			
			return true;
			}


				
				
	public static void main(String[] args){
		UNTPD005F x = new UNTPD005F();
		x.setFilePath("c:\\");
		x.setFileName("abc.xls");
		x.batchInsert();
	}
}