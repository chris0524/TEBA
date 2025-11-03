/*
*<br>程式目的：
*<br>程式代號：untdu049f
*<br>程式日期：0950623
*<br>程式作者：sam.hsueh
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.*;

public class UNTDU049F extends SuperBean{

	private String enterOrg;
	private String ownership;
	private String differenceKind;
	private String propertyType;
	private String fileName;
	private String excelFileName;
	private String exportFileName;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {	return checkGet(ownership);}
	public void setOwnership(String s) {	this.ownership = checkSet(s);}
	public String getDifferenceKind(){ return checkGet(differenceKind); }
	public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
	public String getPropertyType(){ return checkGet(propertyType); }
	public void setPropertyType(String s){ propertyType=checkSet(s); }
	public String getFileName() {return checkGet(fileName);}
	public void setFileName(String fileName) {this.fileName = checkSet(fileName);}
	public String getExcelFileName(){ return checkGet(excelFileName); }
	public void setExcelFileName(String s){ excelFileName = checkSet(s); }
	public String getExportFileName() {return checkGet(exportFileName);}
	public void setExportFileName(String exportFileName) {this.exportFileName = checkSet(exportFileName);}
	private String q_enterOrg;
	private String q_enterOrgName;
	private String actionType;
	private String filePlace;
	private String filePath;
	
	public String getQ_enterOrg() {	return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String s) {q_enterOrg = checkSet(s);}
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getActionType() {return checkGet(actionType);}
	public void setActionType(String actionType) {this.actionType = checkSet(actionType);}
	public String getFilePlace() {return checkGet(filePlace);}
	public void setFilePlace(String filePlace) {this.filePlace = checkSet(filePlace);}
	public String getFilePath() {return checkGet(filePath);}
	public void setFilePath(String filePath) {this.filePath = checkSet(filePath);}

	
    public String[] excelTitle = null;   
    
    public void initTitle(){
    	excelTitle = new String[12];
		excelTitle[0] =  "*權屬代碼(不能改)";
		excelTitle[1] =  "*財產區分別代碼(不能改)";
		excelTitle[2] =  "*財產編號(不能改)";
		excelTitle[3] =  "*財產分號(不能改)";
		excelTitle[4] =  "財產名稱(不能改)";
		excelTitle[5] =  "財產別名(不能改)";
		excelTitle[6] =  "*屬公共設施建設費(是/否)";
		excelTitle[7] =  "*部門別依比例分攤(是/否)";
		excelTitle[8] =  "*園區別代碼";
		excelTitle[9] =  "*部門別代碼";
		excelTitle[10] =  "*部門別單位代碼";
		excelTitle[11] =  "*會計科目代碼";
    }  
    //匯出
	public void exportAll() throws Exception{	
		Database db = new Database();
		ResultSet rs = null; 
		int r=0;	
		initTitle();
		setExportFileName("UNTDU049F");
		try {
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet1 = wb.createSheet("Sheet1");	
				HSSFRow row;
				HSSFCell cell;
				//Print Excel Column 
				row = sheet1.createRow((short)r);		
				for (int c=0; c<excelTitle.length; c++) {				
					cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(excelTitle[c]);			
				}
				
		
			
				
				String querySql=this.getExportAllSQL();


		        String uploadCaseID = new java.rmi.dgc.VMID().toString();
		        uploadCaseID = uploadCaseID.replaceAll(":","_");		
				File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
				tempDirectory = new File(tempDirectory,uploadCaseID);
				tempDirectory.mkdirs();

				
				rs = db.querySQL(querySql);

				r++;
				while (rs.next()){
					row = sheet1.createRow((short)r);				
					for (int c=0; c<excelTitle.length; c++) {
						cell = row.createCell((short)c);
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
						cell.setCellValue(rs.getString(c+1));			
					}			
					r++;			
				}
				rs.close();
		
				FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+getExportFileName()+".xls");			
				wb.write(fout);
				fout.flush();
				fout.close();
				this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+getExportFileName()+".xls");
				this.setActionType("exportAll");
				//super.setState("exportAll");			
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
	
	
	/**
	 * 產生EXCEL報表用SQL
	 * @return SQL語法 
	 */
	private String getExportAllSQL(){
		
		String querySql="";
		if(getPropertyType().equals("2")){
			querySql=" select "+
			 " a.ownership,a.differencekind, a.propertyno,a.serialno,"+ 
			 " (select e.propertyname from SYSPK_PROPERTYCODE e where a.enterorg = e.enterorg and a.propertyno = e.propertyno) as propertyname," +
			 " a.propertyname1,a.buildFeeCB, a.deprUnitCB,"+ 
			 " a.deprPark, a.deprUnit,a.deprUnit1, a.deprAccounts,'UNTRF_ATTACHMENT' as tablename "+   
			 " from UNTRF_ATTACHMENT a where 1=1";
			}
		else if(getPropertyType().equals("3")){
			querySql=" select "+
			 " a.ownership,a.differencekind, a.propertyno,a.serialno,"+ 
			 " (select e.propertyname from SYSPK_PROPERTYCODE e where a.enterorg = e.enterorg and a.propertyno = e.propertyno) as propertyname," +
			 " a.propertyname1,a.buildFeeCB, a.deprUnitCB,"+ 
			 " a.deprPark, a.deprUnit,a.deprUnit1, a.deprAccounts,'UNTBU_BUILDING' as tablename "+ 
			 " from UNTBU_BUILDING a where 1=1";
			}
		else if(getPropertyType().equals("4") || getPropertyType().equals("5") || getPropertyType().equals("6")){
			querySql=" select "+
			 " a.ownership,a.differencekind, a.propertyno,a.serialno,"+ 
			 " (select e.propertyname from SYSPK_PROPERTYCODE e where a.enterorg = e.enterorg and a.propertyno = e.propertyno) as propertyname," +
			 " a.propertyname1,a.buildFeeCB, a.deprUnitCB,"+ 
			 " a.deprPark, a.deprUnit,a.deprUnit1, a.deprAccounts,'UNTMP_MOVABLEDETAIL' as tablename "+  
			 " from UNTMP_MOVABLEDETAIL a where 1=1";
			}
		if (!"".equals(getEnterOrg())){
			querySql+=" and a.enterorg = " + Common.sqlChar(getEnterOrg()) ;
		}
		if (!"".equals(getOwnership())){
			querySql+=" and a.ownership = " + Common.sqlChar(getOwnership());
		}
		if (!"".equals(getDifferenceKind())){
			querySql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
		}
		return querySql;
	}
	
	//匯入
	//必輸欄位
	private Map pkCols = new HashMap();
	int totalColumns = 12;
	public String batchInsert(){
		String result = "";
		String[][] datas;
		List listError = new ArrayList();
		setPKCols();
		try {
			datas = read();
			for(int i=0; i<datas.length; i++){
				String[] rowData = datas[i];
				if(rowData.length != totalColumns ) {
					return "totalColumnIncorrect";
				}
				
				listError = checkPKNoData(rowData, listError);
				updateData(rowData);
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

		public void updateData(String[]rowData){
			  Database db = new Database();
			  String[] execSQLArray = null;
			 try {
			      db.setAutoCommit(false);
				   StringBuffer sbSQL = new StringBuffer();
				   sbSQL.append(" update UNTBU_BUILDING set " )
				  .append("buildfeecb='"+rowData[6]+"',")
				  .append("deprunitcb='"+rowData[7]+"',") 
				  .append("deprpark='"+rowData[8]+"',") 
				  .append("deprunit='"+rowData[9]+"',") 
				  .append("deprunit1='"+rowData[10]+"',") 
				  .append("deprAccounts='"+rowData[11]+"'") 
				  .append(" where 1=1 ")
				  .append("and enterorg='"+getQ_enterOrg()+"'")
				  .append("and ownership='"+rowData[0]+"'")
				  .append("and differencekind='"+rowData[1]+"'")
				  .append("and propertyno='"+rowData[2]+"'")
				  .append("and serialno='"+rowData[3]+"'")
				  .append( ":;:" );
				   sbSQL.append(" update UNTRF_ATTACHMENT set " )
				  .append("buildfeecb='"+rowData[6]+"',")
				  .append("deprunitcb='"+rowData[7]+"',") 
				  .append("deprpark='"+rowData[8]+"',") 
				  .append("deprunit='"+rowData[9]+"',") 
				  .append("deprunit1='"+rowData[10]+"',") 
				  .append("deprAccounts='"+rowData[11]+"'") 
				  .append(" where 1=1 ")
				  .append("and enterorg='"+getQ_enterOrg()+"'")
				  .append("and ownership='"+rowData[0]+"'")
				  .append("and differencekind='"+rowData[1]+"'")
				  .append("and propertyno='"+rowData[2]+"'")
				  .append("and serialno='"+rowData[3]+"'")
				  .append( ":;:" );
				   sbSQL.append(" update UNTMP_MOVABLEDETAIL set " )
				  .append("buildfeecb='"+rowData[6]+"',")
				  .append("deprunitcb='"+rowData[7]+"',") 
				  .append("deprpark='"+rowData[8]+"',") 
				  .append("deprunit='"+rowData[9]+"',") 
				  .append("deprunit1='"+rowData[10]+"',") 
				  .append("deprAccounts='"+rowData[11]+"'") 
				  .append(" where 1=1 ")
				  .append("and enterorg='"+getQ_enterOrg()+"'")
				  .append("and ownership='"+rowData[0]+"'")
				  .append("and differencekind='"+rowData[1]+"'")
				  .append("and propertyno='"+rowData[2]+"'")
				  .append("and serialno='"+rowData[3]+"'")
				  .append( ":;:" );
System.out.println("==>"+sbSQL.toString());
               execSQLArray = sbSQL.toString().split(":;:");
				  db.excuteSQL(execSQLArray);
			 } catch (Exception e) {
				e.printStackTrace();
			 } finally{
				 db.closeAll();
			 }
			
		  }
	
	   //必輸欄位設定

	private void setPKCols(){
		pkCols.put(1, "權屬代碼");
		pkCols.put(2, "財產區分別代碼");
		pkCols.put(3, "財產編號");
		pkCols.put(4, "財產分號");

		
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
	
	
	
	
	public static void main(String[] args){

	}
}