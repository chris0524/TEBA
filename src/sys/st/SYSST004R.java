package sys.st;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;

/**
 * 市有財產行政區域分佈統計表
 * 
 * @version 1.0, Jun 27, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST004R extends SuperBean{
	
	String enterOrg;
	String enterOrgName;
	String closingYM;
	String closingID;
	String closingDate;
	String valuable;
	String excelFile;
	String filestoreLocation;

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
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filestoreLocation));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet1 = wb.getSheetAt(0);		
			HSSFRow row;		
			HSSFCell cell;		
			row = sheet1.getRow(2);
			if (row==null) row = sheet1.createRow((short)2);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("資料日期："+Common.MaskCDate(Datetime.getYYYMMDD()));
			
			//計算土地
			String sql = " select decode(a.propertyKind,'04','04','00') propertyKind, substr(signno,2,2) as sign, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' " +
				" and substr(signno,1,3) between 'E01' and 'E11' " + preSQL +
				" group by decode(a.propertyKind,'04','04','00'), substr(signno,2,2) " +
				" order by decode(a.propertyKind,'04','04','00'), substr(signno,2,2) ";		
			ResultSet rs = db.querySQL(sql);
			int r = 5;
			int j = 0;
			while (rs.next()) {
				//7,2, 7,8
				row = sheet1.getRow(r+rs.getInt("sign"));
				for (j=0; j<3; j++) {
					if (rs.getString("propertyKind").equals("00")) {
						cell = row.getCell((short)(2+j));			
					} else {
						cell = row.getCell((short)(8+j));
					}
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(3+j));							
				}

			}
			rs.getStatement().close();
			rs.close();
			
			//計算建物
			sql = " select decode(a.propertyKind,'04','04','00') propertyKind, substr(signno,2,2) as sign, count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' " +
				" and substr(signno,1,3) between 'E01' and 'E11' " + preSQL +
				" group by decode(a.propertyKind,'04','04','00'), substr(signno,2,2) " +
				" order by decode(a.propertyKind,'04','04','00'), substr(signno,2,2) ";		
			rs = db.querySQL(sql);
			while (rs.next()){				
				//7,5, 7,11
				row = sheet1.getRow(r+rs.getInt("sign"));
				for (j=0; j<3; j++) {
					if (rs.getString("propertyKind").equals("00")) {
						cell = row.getCell((short)(5+j));			
					} else {
						cell = row.getCell((short)(11+j));
					}
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(3+j));							
				}
			}
			rs.getStatement().close();
			rs.close();
			//End of Building
			
			
			
			//外縣市
			//計算土地
			sql = " select decode(a.propertyKind,'04','04','00') propertyKind, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' " +
				" and substr(signno,1,1)<>'E' " + preSQL +
				" group by decode(a.propertyKind,'04','04','00') order by decode(a.propertyKind,'04','04','00') ";		
			rs = db.querySQL(sql);
			r = 18;
			j = 0;
			while (rs.next()) {
				//19,2, 19,8
				row = sheet1.getRow(r);
				for (j=0; j<3; j++) {
					if (rs.getString("propertyKind").equals("00")) {
						cell = row.getCell((short)(2+j));			
					} else {
						cell = row.getCell((short)(8+j));
					}
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(2+j));							
				}
			}
			rs.getStatement().close();
			rs.close();
			
			//計算建物
			sql = " select decode(a.propertyKind,'04','04','00') propertyKind, count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' " +
				" and substr(signno,1,1)<>'E' " + preSQL +
				" group by decode(a.propertyKind,'04','04','00') order by decode(a.propertyKind,'04','04','00') ";		
			rs = db.querySQL(sql);
			while (rs.next()){				
				//18,5, 19,11
				row = sheet1.getRow(r);
				for (j=0; j<3; j++) {
					if (rs.getString("propertyKind").equals("00")) {
						cell = row.getCell((short)(5+j));			
					} else {
						cell = row.getCell((short)(11+j));
					}
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(2+j));							
				}
			}
			rs.getStatement().close();
			rs.close();
			//End of Building
			
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"areasAnalysis.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"areasAnalysis.xls");
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
