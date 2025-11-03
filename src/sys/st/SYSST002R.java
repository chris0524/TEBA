package sys.st;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

/**
 * 市有財產統計總表
 * 
 * @version 1.0, Jun 26, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST002R extends SuperBean{
	
	String enterOrg;
	String enterOrgName;
	String closingYM;
	String closingID;
	String closingDate;
	String valuable;
	String excelFile;
	String filestoreLocation;
	String tempFolder;
	String y1, y2, d1;
	
	public String getY1() { return checkGet(y1); }
	public void setY1(String s) { y1 = checkSet(s); }
	public String getY2() { return checkGet(y2); }
	public void setY2(String s) { y2 = checkSet(s); }
	public String getD1() { return checkGet(d1); }
	public void setD1(String s) { d1 = checkSet(s); }	
	public String getTempFolder(){ return checkGet(tempFolder); }
	public void setTempFolder(String s){ tempFolder=checkSet(s); }	
	public String getFilestoreLocation(){ return checkGet(filestoreLocation); }
	public void setFilestoreLocation(String s){ filestoreLocation=checkSet(s); }	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getClosingYM(){ return checkGet(closingYM); }
	public void setClosingYM(String s){ closingYM=checkSet(s); }
	public String getClosingID(){ return checkGet(closingID); }
	public void setClosingID(String s){ closingID=checkSet(s); }
	public String getClosingDate(){ return checkGet(closingDate); }
	public void setClosingDate(String s){ closingDate=checkSet(s); }
	public String getValuable(){ return checkGet(valuable); }
	public void setValuable(String s){ valuable=checkSet(s); }
	public String getExcelFileName() { return checkGet(excelFile); }
	public void setExcelFileName(String s) { excelFile=checkSet(s); }

	
	public void genExcelFile(){
		Database db = new Database();
		try {
			String preSQL = "";			
			if (!"".equals(getClosingYM())) preSQL = " and nvl(a.closing,'N') = " + Common.sqlChar(getClosingYM());
			if (!"".equals(getEnterOrg())) preSQL = " and a.enterOrg = " + Common.sqlChar(getEnterOrg());			
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filestoreLocation));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet1 = wb.getSheetAt(0);		
			HSSFRow row;		
			HSSFCell cell;		
						
			row = sheet1.getRow(1);
			if (row==null) row = sheet1.createRow((short)1);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			////cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("資料日期："+Common.MaskCDate(Datetime.getYYYMMDD()));
			
			//計算土地
			if ("".equals(getY2())) {
				setY2(y1);
			}
			if (!"".equals(getY1()) && !"".equals(getY2()) && !"".equals(getD1())) {
				int r = 2, c = 0;				
				for (int i=Integer.parseInt(getY1()); i<=Integer.parseInt(getY2()); i++) {	
					double total = 0;
					double realty = 0, moveable = 0, stocks = 0, right = 0;					
					String sDate = Common.formatFrontZero(""+i, 3)+getD1();
					String sSQL = preSQL + " and a.enterDate<=" + Common.sqlChar(sDate);
					HSSFCellStyle style = wb.createCellStyle();
					HSSFDataFormat format = wb.createDataFormat();			
				    style.setDataFormat(format.getFormat(("#,##0.00")));					
					
					r = r + 1;
					
					row = sheet1.getRow(r);
					if (row==null) row = sheet1.createRow((short)r);	
					cell = row.getCell((short)c);
					if (cell==null) cell = row.createCell((short)c);
					////cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(sDate);					
					
					String sql = " select sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
						" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
						" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/1000000 " +
						" as currentValue " +
						" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' " + sSQL;		
					ResultSet rs = db.querySQL(sql);
					if (rs.next()) {
						total += rs.getDouble("currentValue");
						realty += rs.getDouble("currentValue");
					}
					rs.getStatement().close();
					rs.close();
					
					//計算建物
					sql = " select sum(nvl(a.bookValue,0))/1000000 as currentValue " +
						" from Untbu_Building a where a.ownership='1' and a.dataState='1' and a.verify='Y' ";			
					rs = db.querySQL(sql+sSQL);
					while (rs.next()){				
						total += rs.getDouble("currentValue");
						realty += rs.getDouble("currentValue");
					}		
					rs.getStatement().close();
					rs.close();
					//End of Building
					
					//計算土地改良物
					sql = " select sum(nvl(a.bookValue,0))/1000000 as currentValue " +
						" from UNTRF_Attachment a where a.ownership='1' and a.dataState='1' and a.verify='Y' ";			
					rs = db.querySQL(sql+sSQL);
					if (rs.next()){				
						total += rs.getDouble("currentValue");
						realty += rs.getDouble("currentValue");
					}
					rs.getStatement().close();
					rs.close();
					//End of UNTRF_Attachment			

					cell = row.getCell((short)(c+1));
					if (cell==null) cell = row.createCell((short)(c+1));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11					
					cell.setCellValue(realty);
					cell.setCellStyle(style);
					//End of 不動產
					
					
					
					//計算動產	
					sql = "	select sum(nvl(b.bookValue,0))/1000000 as currentValue  " +
						" from UNTMP_MOVABLE a, Untmp_Movabledetail b " +
						" where a.ownership='1' and a.datastate='1' and a.verify='Y' " + 
						" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.lotno=b.lotno "+sSQL;				
					rs = db.querySQL(sql);
					if (rs.next()){
						total += rs.getDouble("currentValue");
						moveable = rs.getDouble("currentValue");		
					}
					rs.getStatement().close();
					rs.close();

					cell = row.getCell((short)(c+2));
					if (cell==null) cell = row.createCell((short)(c+2));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(moveable);	
					cell.setCellStyle(style);
					//End of Untmp_Movabledetail
					
		
					//計算有價證券	
					sql = "	select sum(nvl(a.bookValue,0))/1000000 as currentValue  " +
						" from UNTVP_AddProof a where a.ownership='1' and a.datastate='1' and a.verify='Y' ";			
					rs = db.querySQL(sql+sSQL);
					if (rs.next()){					
						total += rs.getDouble("currentValue");
						stocks = rs.getDouble("currentValue");
					}
					rs.getStatement().close();
					rs.close();
					
					cell = row.getCell((short)(c+3));
					if (cell==null) cell = row.createCell((short)(c+3));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(stocks);	
					cell.setCellStyle(style);
					//End of UNTVP_AddProof		
					
					
					//計算權利	
					sql = "	select sum(nvl(a.bookValue,0))/1000000 as currentValue  " +
						" from UNTRT_AddProof a where a.ownership='1' and a.datastate='1' and a.verify='Y' ";
					rs = db.querySQL(sql+sSQL);
					if (rs.next()){			
						total += rs.getDouble("currentValue");
						right = rs.getDouble("currentValue");
					}
					rs.getStatement().close();
					rs.close();
					
					cell = row.getCell((short)(c+4));
					if (cell==null) cell = row.createCell((short)(c+4));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(right);	
					cell.setCellStyle(style);
					//End of UNTRT_AddProof		
					
					cell = row.getCell((short)(c+5));
					if (cell==null) cell = row.createCell((short)(c+5));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(total);
					//cell.setCellStyle(style);
				}
				
		        String uploadCaseID = new java.rmi.dgc.VMID().toString();
		        uploadCaseID = uploadCaseID.replaceAll(":","_");		
				File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
				tempDirectory = new File(tempDirectory,uploadCaseID);
				tempDirectory.mkdirs();
	
				FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"assetsTrend.xls");			
				wb.write(fout);
				fout.flush();
				fout.close();
				setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"assetsTrend.xls");
		        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
				super.setState("exportSuccess");			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
