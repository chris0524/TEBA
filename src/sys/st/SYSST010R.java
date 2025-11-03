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
 * 非公用市有土地統計圖表
 * 
 * @version 1.0, Jul 3, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST010R extends SuperBean{
	
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
		int[] arrValue = new int[] {3,6,4,6,6,5,6};
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
			String sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" (sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/10000) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			ResultSet rs = db.querySQL(sql);
		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(getCodeInt(rs.getString("useState")));			
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
			
			//新草衙專案土地
			sql = " select count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" (sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/10000) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')='Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL;	
			rs = db.querySQL(sql);
		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(7);			
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
			
			
			//抵繳稅款土地
			sql = " select count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" (sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno)))/10000) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and a.taxCredit='Y' " + preSQL ;	
			rs = db.querySQL(sql);
		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(8);			
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

			//取得管理檔的出租和占用戶數
			int re = 0, oc = 0;			
			sql = " select decode(a.useRelation,'01','RE','03','OC','52','RE','53','RE','81','RE','82','OC','99') as useRelation, count(*) as recCount from qry_unt_manage a where a.propertyType='1' and a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and a.useRelation in ('01','03','52','53','81','82') and a.isdefault='1' " + preSQL +
				" group by decode(a.useRelation,'01','RE','03','OC','52','RE','53','RE','81','RE','82','OC','99') ";
			rs = db.querySQL(sql);
			while (rs.next()) {
				if (Common.get(rs.getString("useRelation")).equals("RE")) {
					re = rs.getInt("recCount");
				} else if (Common.get(rs.getString("useRelation")).equals("OC")) {
					oc = rs.getInt("recCount");
				}
			}
			rs.getStatement().close();
			rs.close();			
			
	        String strMemo="說明：\n\r" +
	      	 	 "一、資料截止日期："+Common.MaskCDate(Datetime.getYYYMMDD()) +
				 "。\n\r二、價值以公告地價計價。" +
				 "\n\r三、出租：承租戶約"+re+"戶。" +
				 "\n\r四、占用：占用戶約"+oc+"戶。" +
				 "\n\r五、待處理土地：指道路使用、軍眷使用、公共設施用地、機關自用或其他用地。";						
			
			row = sheet1.getRow(11);			
			cell = row.getCell((short)(0));
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue(strMemo);	
			
						
			HSSFSheet sheet2 = wb.getSheetAt(1);
			
			//計算建物
			sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			rs = db.querySQL(sql);
			while (rs.next()) {
				//3				
				row = sheet2.getRow(getCodeInt(rs.getString("useState")));			
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

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"npbStateAnalysis.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"npbStateAnalysis.xls");
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
