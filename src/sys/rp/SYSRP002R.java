/*
*<br>程式目的：財產資料清查作業
*<br>程式代號：SYSRP001R
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.rp;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import util.*;

public class SYSRP002R extends SuperBean{

private String fileName;
public String getFileName() {return checkGet(fileName);}
public void setFileName(String fileName) {this.fileName = checkSet(fileName);}

private String q_editDateS;
private String q_editDateE;
public String getQ_editDateS() {return checkGet(q_editDateS);}
public void setQ_editDateS(String q_editDateS) {this.q_editDateS = checkSet(q_editDateS);}
public String getQ_editDateE() {return checkGet(q_editDateE);}
public void setQ_editDateE(String q_editDateE) {this.q_editDateE = checkSet(q_editDateE);}


public SYSRP002R(){	
	this.setFileName("SYSRP002R");
}

public File exportExcel() throws Exception{	
	Database db = new Database();
	ResultSet rs = null; 
	File outputFile = null;
	int i = 0, j=0;	
	try {		
		String querySql=this.getExportSQL();
		
		rs = db.querySQL_NoChange(querySql);
		ResultSetMetaData rsmd = rs.getMetaData();

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("Sheet1");	
		HSSFRow row;
		HSSFCell cell;
		
		if(rsmd.getColumnCount() !=0){			
			//取得欄位名稱
			row = sheet1.createRow(i++);
			for (j=0;j<rsmd.getColumnCount();j++) {
				cell = row.createCell(j);
				cell.setCellValue(rsmd.getColumnName(j+1));
			}
			//填入資料
			while (rs.next()){
				row = sheet1.createRow(i++);				
				for (j=0;j<rsmd.getColumnCount();j++) {
					cell = row.createCell(j);
					cell.setCellValue(rs.getString(j+1));			
				}
			}
			
			outputFile = exportToFile(wb, this.getFileName() +"_" + this.getUserID() + "_" + Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".xls");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return outputFile;
}

private File exportToFile(Workbook workbook, String fileName) throws Exception {
	File outputFile = new File(Common.getTempDirectory(), fileName);
	FileOutputStream fos = null;
	try {
		fos = new FileOutputStream(outputFile);
		workbook.write(fos);
		fos.flush();
		return outputFile;
	} finally {
		if (fos != null) {
			try {
				fos.close();
			} catch (Exception e) {
				//ignore
			}
		}
	}	
}

/**
 * 產生EXCEL報表用SQL
 * @return SQL語法 
 */
private String getExportSQL(){
	String querySql="select * from SYSAP_RECORDSQL where 1=1";
	
	if (!"".equals(Common.get(getQ_editDateS())))
		querySql += " and editdate >= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_editDateS(),"2"));
	if (!"".equals(Common.get(getQ_editDateE())))
		querySql += " and editdate <= " + Common.sqlChar(Datetime.changeTaiwanYYMMDD(getQ_editDateE(),"2"));

	querySql +=" order by id;";
	return querySql;
}

}