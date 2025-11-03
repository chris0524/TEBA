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
 * 非公用土地清理情形表
 * 
 * @version 1.0, Aug 3, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST014R extends SuperBean{
	
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
	
	String q_year;
	public String getQ_year() { return checkGet(q_year); }
	public void setQ_year(String s){ q_year = checkSet(s); }

	public int getCodeInt(String s) {
		String[] arrCode = new String[] {"01","02","03","04","10","50","99"};
		int[] arrValue = new int[] {5,6,8,8,8,7,8};
		for (int i=0; i<arrCode.length; i++) {
			if (s.equals(arrCode[i])) return arrValue[i];
		}
		return 7;
	}
	
	public int getCode2Int(String s) {
		String[] arrCode = new String[] {"01","02","03","04","10","50","99"};
		int[] arrValue = new int[] {11,14,12,14,14,13,14};
		for (int i=0; i<arrCode.length; i++) {
			if (s.equals(arrCode[i])) return arrValue[i];
		}
		return 14;
	}	
	
	public void genExcelFile(){
		Database db = new Database();
		try {
			String preSQL = "";
			if ("".equals(getQ_year())) setQ_year(Datetime.getYYY());			
			if (!"".equals(getEnterOrg())) preSQL = " and a.enterOrg = " + Common.sqlChar(getEnterOrg());						
			if (!"".equals(getQ_year())) {
				preSQL = " and nvl(a.holddates,'0000000')<='" + Common.formatFrontZero(""+(Integer.parseInt(getQ_year())-1),3) + "1231' ";
			}
			String sTitle = "高雄市政府財政局 " + Integer.parseInt(getQ_year())+" 年度非公用土地清理情形表";
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filestoreLocation));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet1 = wb.getSheetAt(0);		
			HSSFRow row;		
			HSSFCell cell;		
			row = sheet1.getRow(0);
			cell = row.getCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue(sTitle);
			
			row = sheet1.getRow(1);
			if (row==null) row = sheet1.createRow((short)1);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("資料日期："+Common.MaskCDate(Datetime.getYYYMMDD()));
			
			row = sheet1.getRow(2);
			cell = row.getCell((short)2);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("截至" +(Integer.parseInt(getQ_year())-1)+"年12月31日止");
			
			cell = row.getCell((short)5);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue(Integer.parseInt(getQ_year())+"年變動");			
			
			cell = row.getCell((short)11);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("截至"+Integer.parseInt(getQ_year())+"年12月31日止");			
			
			
			//出租、占用、閒置及待處理土地
			String sql = " select decode(a.useRelation,'01','01','52','01','53','01','81','01','03','02','83','02','50','50','99') as useState, count(*) as recCount, sum(a.useArea) as holdArea, " +  
				" sum(a.useArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from QRY_UNT_Manage a where a.propertyType='1' and a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL + 
				" group by decode(a.useRelation,'01','01','52','01','53','01','81','01','03','02','83','02','50','50','99') ";
			ResultSet rs = db.querySQL(sql);		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(getCodeInt(rs.getString("useState")));			
				cell = row.getCell((short)(2));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(4));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
						
			//出租、占用、閒置及待處理土地 - 變動之減少項目
			sql = " select decode(a.useRelation,'01','01','52','01','53','01','81','01','03','02','83','02','50','50','99') as useState, count(*) as recCount, sum(a.useArea) as holdArea, " +  
				" sum(a.useArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from QRY_UNT_Manage a where a.propertyType='1' and a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL +
				" and nvl(a.holddatee,'0000000')>='" + getQ_year() + "0101' and nvl(a.holddatee,'0000000')<='" + getQ_year() + "1231' " +				
				" group by decode(a.useRelation,'01','01','52','01','53','01','81','01','03','02','83','02','50','50','99') ";
			rs = db.querySQL(sql);		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(getCodeInt(rs.getString("useState")));			
				cell = row.getCell((short)(5));	
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(6));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(7));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
			
			//出租、占用、閒置及待處理土地 - 變動之增加項目
			sql = " select decode(a.useRelation,'01','01','52','01','53','01','81','01','03','02','83','02','50','50','99') as useState, count(*) as recCount, sum(a.useArea) as holdArea, " +  
				" sum(a.useArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from QRY_UNT_Manage a where a.propertyType='1' and a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' " +
				" and nvl(a.holddates,'0000000')>='" + getQ_year() + "0101' " +				
				" group by decode(a.useRelation,'01','01','52','01','53','01','81','01','03','02','83','02','50','50','99') ";			
			// and nvl(a.grass,'N')<>'Y' and nvl(a.taxCredit,'N')<>'Y'
			rs = db.querySQL(sql);		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(getCodeInt(rs.getString("useState")));			
				cell = row.getCell((short)(8));	
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(9));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(10));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();				
			
			sTitle = "說明：1.資料填列基準日為"+Integer.parseInt(getQ_year())+"年12月31日止。表列各價值欄，以土地取得當期公告地價填列。";				
			row = sheet1.getRow(10);
			cell = row.getCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue(sTitle);
			
			row = sheet1.getRow(11);
			cell = row.getCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("　　  2.欄位(1)-(4)+(7)=(10)，(2)-(5)+(8)=(11)，(3)-(6)+(9)=(12)。");				
						
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"npbLand.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"npbLand.xls");
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
