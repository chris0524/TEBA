
package unt.pd;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;


public class UNTPD015F extends SuperBean{

	private String enterOrg;
	private String keepunit;
	private String closingdate;
	private String fileName;
	private String excelFileName;
	private String keeper;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getKeepunit() {return checkGet(keepunit);}
	public void setKeepunit(String keepunit) {this.keepunit = checkSet(keepunit);}
	public String getClosingdate() {return checkGet(closingdate);}
	public void setClosingdate(String closingdate) {this.closingdate = checkSet(closingdate);}
	public String getFileName() {return checkGet(fileName);}
	public void setFileName(String fileName) {this.fileName = checkSet(fileName);}
	public String getExcelFileName(){ return checkGet(excelFileName); }
	public void setExcelFileName(String s){ excelFileName=checkSet(s); }
    public String getKeeper() {return checkGet(keeper);}
	public void setKeeper(String keeper) {this.keeper = checkSet(keeper);}
	
    public String[] excelTitle = null;   
    
    public void initTitle(){
    	excelTitle = new String[22];
		excelTitle[0] =  "入帳機關";
		excelTitle[1] =  "盤點序號";
		excelTitle[2] =  "條碼";
		excelTitle[3] =  "權屬";
		excelTitle[4] =  "資料截止日期";
		excelTitle[5] =  "物品編號";
		excelTitle[6] =  "物品分號";
		excelTitle[7] =  "物品名稱";
		excelTitle[8] =  "物品別名";
		excelTitle[9] =  "型式/廠牌";
		excelTitle[10] =  "購置日期";
		excelTitle[11] =  "單位";
		excelTitle[12] =  "數量";
		excelTitle[13] =  "價值";
		excelTitle[14] =  "取得日期";
		excelTitle[15] =  "使用年限";
		excelTitle[16] =  "已使用年數";
		excelTitle[17] =  "保管單位";
		excelTitle[18] =  "保管人";
		excelTitle[19] =  "存置地點";
		excelTitle[20] =  "盤點數量";
		excelTitle[21] =  "備註";
    }  
    
	public void exportAll() throws Exception{	
		Database db = new Database();
		ResultSet rs = null; 
		int r=0;	
		initTitle();
		setFileName("UNTPD015F");
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
		
				FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+getFileName()+".xls");			
				wb.write(fout);
				fout.flush();
				fout.close();
				this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+getFileName()+".xls");
				
				super.setState("exportAll");			
				
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
		String querySql=" select "+
			  "enterorg, serialno1, barcode, ownership, closingdate, propertyno, serialno,"+ 
			  "propertyname, propertyname1, nameplate, buydate, propertyunit,"+ 
			  "bookamount, bookvalue, sourcedate, limityear, useyear, keepunitname,"+ 
			  "keepername, place1name, actualamount, notes "+  
			  "from UNTPD_CHECKNONEXP where 1=1";
		if (!"".equals(getEnterOrg())){
			querySql+=" and enterorg = " + Common.sqlChar(getEnterOrg()) ;
		}
		if (!"".equals(getKeepunit())){
			querySql+=" and keepunit=" + Common.sqlChar(getKeepunit()) ;
		}
		if (!"".equals(getKeeper())){
			querySql+=" and keeper = " + Common.sqlChar(getKeeper()) ;
		}
		if (!"".equals(getClosingdate())){
			querySql+=" and closingdate = " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getClosingdate(),"2")) ;
		}
		querySql+=" order by enterorg, serialno1, barcode ";
		return querySql;
	}
				
				
	public static void main(String[] args){

	}
}