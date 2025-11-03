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
 * 非公用財產會計圖表
 * 
 * @version 1.0, Jul 3, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST011R extends SuperBean{
	
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

	public int getCodeInt(String s) {
		String[] arrCode = new String[] {"01","02","03","04","10","50","99"};
		int[] arrValue = new int[] {1,10,4,10,10,7,10};
		for (int i=0; i<arrCode.length; i++) {
			if (s.equals(arrCode[i])) return arrValue[i];
		}
		return 6;
	}
	
	public void genExcelFile(){
		Database db = new Database();
		try {
			String preSQL = "";			
			if (!"".equals(getClosingYM())) preSQL = " and nvl(a.closing,'N') = " + Common.sqlChar(getClosingYM());
			if (!"".equals(getEnterOrg())) preSQL = " and a.enterOrg = " + Common.sqlChar(getEnterOrg());
			//if (!"".equals(getClosingDate())) preSQL += " and substr(a.enterDate,0,5) <= " + Common.sqlChar(getClosingDate());			
			
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
			
			//出租、占用、閒置及待處理土地
			String sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, substr(signno,2,2) as sign, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" (sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/10000) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and nvl(a.taxCredit,'N')<>'Y' and substr(a.signno,1,3) between 'E01' and 'E11' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState), substr(signno,2,2) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState), substr(signno,2,2) ";		
			ResultSet rs = db.querySQL(sql);
			int r = 4;
			while (rs.next()) {
				//3				
				row = sheet1.getRow(r+rs.getInt("sign"));			
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+1));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+2));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
			
			//外縣市
			sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" (sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/10000) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and nvl(a.taxCredit,'N')<>'Y' and substr(a.signno,1,1)<>'E' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			rs = db.querySQL(sql);
			r = 4;
			while (rs.next()) {				
				row = sheet1.getRow(16);			
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+1));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+2));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();			
			
			
			
			//新草衙專案土地
			sql = " select count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" (sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/10000) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')='Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL;	
			rs = db.querySQL(sql);
		
			while (rs.next()) {
				//18,13				
				row = sheet1.getRow(18);			
				cell = row.getCell((short)(13));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(14));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(15));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();			
			
			
			//抵繳稅款土地
			sql = " select count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" (sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/10000) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and a.taxCredit='Y' " + preSQL ;	
			rs = db.querySQL(sql);
		
			while (rs.next()) {
				//19,13				
				row = sheet1.getRow(19);			
				cell = row.getCell((short)(13));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(14));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(15));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();		

							
			HSSFSheet sheet2 = wb.getSheetAt(1);
			
			//計算建物
			//高雄市
			sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, substr(signno,2,2) as sign, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' and nvl(a.taxCredit,'N')<>'Y' and substr(a.signno,1,3) between 'E01' and 'E11' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState), substr(signno,2,2) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState), substr(signno,2,2) ";		
			rs = db.querySQL(sql);
			r = 4;
			while (rs.next()) {
				row = sheet2.getRow(r+rs.getInt("sign"));			
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+1));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+2));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
			
			
			//外縣市
			sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' and nvl(a.taxCredit,'N')<>'Y' and substr(a.signno,1,1)<>'E' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			rs = db.querySQL(sql);
			while (rs.next()) {
				row = sheet2.getRow(16);			
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+1));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(getCodeInt(rs.getString("useState"))+2));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();			
			//End of Building
			
			
			//抵繳稅款建物
			sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.taxCredit='Y' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			rs = db.querySQL(sql);
			while (rs.next()) {
				//7
				row = sheet2.getRow(7);			
				cell = row.getCell((short)(1));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(2));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
			//End of 抵繳稅款建物
			
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"npbAreaAnalysis.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"npbAreaAnalysis.xls");
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
