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
 * 市有財產用途別分類統計表
 * 
 * @version 1.0, Jun 27, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST003R extends SuperBean{
	
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
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filestoreLocation));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet1 = wb.getSheetAt(0);		
			HSSFRow row;		
			HSSFCell cell;		
			row = sheet1.getRow(1);
			if (row==null) row = sheet1.createRow((short)1);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("資料日期："+Common.MaskCDate(Datetime.getYYYMMDD()));
			
			String preSQL = "";			
			if (!"".equals(getClosingYM())) preSQL = " and nvl(a.closing,'N') = " + Common.sqlChar(getClosingYM());
			
			
			//計算土地
			String sql = " select a.propertyKind, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind in ('01','02','03','04') ";		
			ResultSet rs = db.querySQL(sql+preSQL+ " group by a.propertykind  order by a.propertyKind ");
			int r = 0;
			int j = 0;
			while (rs.next()){	
				if (Common.get(rs.getString("propertyKind")).equals("04")) {
					row = sheet1.getRow(8);					
				} else {
					row = sheet1.getRow(3+Integer.parseInt(rs.getString("propertyKind")));
				}					
				for (j=0; j<3; j++) {
					cell = row.getCell((short)(2+j));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(j+2));
				}							
			}
			r = r + j;
			rs.getStatement().close();
			rs.close();
			
			//計算建物
			sql = " select a.propertyKind, count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(nvl(a.bookValue,0)) as currentValue " +
				" from Untbu_Building a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind in ('01','02','03','04') ";			
			rs = db.querySQL(sql+preSQL+ " group by a.propertykind  order by a.propertyKind ");
			j = 0;
			while (rs.next()){				
				if (Common.get(rs.getString("propertyKind")).equals("04")) {
					row = sheet1.getRow(8);					
				} else {
					row = sheet1.getRow(3+Integer.parseInt(rs.getString("propertyKind")));
				}		
				for (j=0; j<3; j++) {
					cell = row.getCell((short)(2+r+j));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(j+2));
				}			
			}			
			r = r + j;
			rs.getStatement().close();
			rs.close();
			//End of Building
			
			//計算土地改良物
			sql = " select a.propertyKind, count(*) as recCount, sum(a.measure) as holdArea, " +  
				" sum(nvl(a.bookValue,0)) as currentValue " +
				" from UNTRF_Attachment a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind in ('01','02','03','04') ";			
			rs = db.querySQL(sql+preSQL+ " group by a.propertykind  order by a.propertyKind ");
			j = 0;
			while (rs.next()){				
				if (Common.get(rs.getString("propertyKind")).equals("04")) {
					row = sheet1.getRow(8);					
				} else {
					row = sheet1.getRow(3+Integer.parseInt(rs.getString("propertyKind")));
				}		
				for (j=0; j<3; j++) {
					cell = row.getCell((short)(2+r+j));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(j+2));
				}			
			}			
			r = r + j;
			rs.getStatement().close();
			rs.close();
			//End of UNTRF_Attachment			
						
			
			//計算動產	
			sql = "	select a.propertyKind, sum(nvl(b.bookValue,0)) as currentValue " +
				" from UNTMP_MOVABLE a, Untmp_Movabledetail b " +
				" where a.ownership='1' and a.datastate='1' and a.verify='Y' " + 
				" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.lotno=b.lotno " +				
				" and a.propertyKind in ('01','02','03','04') ";			
			rs = db.querySQL(sql+preSQL+ " group by a.propertykind  order by a.propertyKind ");
			j = 0;
			while (rs.next()){				
				if (Common.get(rs.getString("propertyKind")).equals("04")) {
					row = sheet1.getRow(8);					
				} else {
					row = sheet1.getRow(3+Integer.parseInt(rs.getString("propertyKind")));
				}		
				cell = row.getCell((short)(2+r+j));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble(j+2));		
			}		
			r = r + 1;
			rs.getStatement().close();
			rs.close();
			//End of Untmp_Movabledetail
			

			//計算有價證券	
			sql = "	select a.propertyKind, sum(nvl(a.bookValue,0)) as currentValue " +
				" from UNTVP_AddProof a where a.ownership='1' and a.datastate='1' and a.verify='Y' " + 				
				" and a.propertyKind in ('01','02','03','04') ";			
			rs = db.querySQL(sql+preSQL+ " group by a.propertykind  order by a.propertyKind ");
			j = 0;
			while (rs.next()){				
				if (Common.get(rs.getString("propertyKind")).equals("04")) {
					row = sheet1.getRow(8);					
				} else {
					row = sheet1.getRow(3+Integer.parseInt(rs.getString("propertyKind")));
				}
				cell = row.getCell((short)(2+r+j));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble(j+2));		
			}		
			r = r + 1;
			rs.getStatement().close();
			rs.close();
			//End of UNTVP_AddProof		
			
			
			//計算權利	
			sql = "	select a.propertyKind, sum(nvl(a.bookValue,0)) as currentValue " +
				" from UNTRT_AddProof a where a.ownership='1' and a.datastate='1' and a.verify='Y' " + 				
				" and a.propertyKind in ('01','02','03','04') ";			
			rs = db.querySQL(sql+preSQL+ " group by a.propertykind  order by a.propertyKind ");
			j = 0;
			while (rs.next()){				
				if (Common.get(rs.getString("propertyKind")).equals("04")) {
					row = sheet1.getRow(8);					
				} else {
					row = sheet1.getRow(3+Integer.parseInt(rs.getString("propertyKind")));
				}
				cell = row.getCell((short)(2+r+j));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble(j+2));		
			}		
			r = r + 1;
			rs.getStatement().close();
			rs.close();
			//End of UNTRT_AddProof					
			
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"useState.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"useState.xls");					
			//new File(tempDirectory.getAbsoluteFile()+File.separator+"useState.xls").deleteOnExit();
			//tempDirectory.deleteOnExit();			
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}

}
