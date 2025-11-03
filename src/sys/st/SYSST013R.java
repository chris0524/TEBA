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
 * 非公用房地租/償金徵收情形表
 * 
 * @version 1.0, Aug 2, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST013R extends SuperBean{
	
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
		int[] arrValue = new int[] {4,7,5,7,7,6,7};
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
			String sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			ResultSet rs = db.querySQL(sql);
		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(getCodeInt(rs.getString("useState")));			
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(4));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(5));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
			
			//新草衙專案土地
			sql = " select count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')='Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL;	
			rs = db.querySQL(sql);
		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(8);			
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(4));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(5));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();	
			
			//抵繳稅款土地
			sql = " select count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.propertyKind='04' and nvl(a.grass,'N')<>'Y' and a.taxCredit='Y' " + preSQL ;	
			rs = db.querySQL(sql);
		
			while (rs.next()) {
				//3				
				row = sheet1.getRow(9);			
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(4));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(5));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();		
			
			
			//計算建物
			sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' and nvl(a.taxCredit,'N')<>'Y' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			rs = db.querySQL(sql);
			while (rs.next()) {
				//3				
				row = sheet1.getRow(getCode2Int(rs.getString("useState")));			
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(4));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(5));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
			//End of Building
			
			
			//抵繳稅款建物
			sql = " select decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) as useState, count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(a.bookvalue) as currentValue " +
				" from UNTBU_BUILDING a where a.ownership='1' and a.dataState='1' and a.verify='Y' and a.taxCredit='Y' " + preSQL +
				" group by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) " +
				" order by decode(a.useState,null,'99','02','99','04','99','10','99',a.useState) ";		
			rs = db.querySQL(sql);
			while (rs.next()) {
				row = sheet1.getRow(15);			
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("recCount"));							
				
				cell = row.getCell((short)(4));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("holdArea"));
				
				cell = row.getCell((short)(5));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));				
			}
			rs.getStatement().close();
			rs.close();
			//End of 抵繳稅款建物
			
			preSQL = "";
			if (!"".equals(getQ_year())) {
				preSQL = " and a.limitDate1 between '" + getQ_year() + "0101' and '"+getQ_year()+"1231'";
			}			
			sql = "select decode(a.levyType,'1','1','2','2','7','2','8','2',a.levyType) as levyType, b.propertyType, decode(a.payAllYN,'Y','Y','N') as payAllYN,  sum(a.receiveValue+nvl(a.interestValue,0)+nvl(a.otherValue,0)) as receiveValue from npbbl_levy a, npbbl_property b where a.levyType in ('1','2','7','8') and nvl(a.isTransToInstallment,'N')<>'Y' and nvl(a.delYN,'N')<>'Y' and a.levyNo=b.levyNo " + preSQL +
				" group by decode(a.levyType,'1','1','2','2','7','2','8','2',a.levyType), b.propertyType, decode(a.payAllYN,'Y','Y','N') ";			
			rs = db.querySQL(sql);
			while (rs.next()) {
				if (rs.getString("propertyType").equals("2")) {
					if (rs.getString("levyType").equals("1")) {
						//4,6
						row = sheet1.getRow(4);					
						if (rs.getString("payAllYN").equals("Y")) cell = row.getCell((short)(7));						
						else cell = row.getCell((short)(6));
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
						cell.setCellValue(rs.getDouble("receiveValue"));					
					} else {
						//5,9
						row = sheet1.getRow(5);
						if (rs.getString("payAllYN").equals("Y")) cell = row.getCell((short)(10));
						else cell = row.getCell((short)(9));
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
						cell.setCellValue(rs.getDouble("receiveValue"));					
					}					
				} else {
					if (rs.getString("levyType").equals("1")) {
						//11,6
						row = sheet1.getRow(11);					
						if (rs.getString("payAllYN").equals("Y")) cell = row.getCell((short)(7));						
						else cell = row.getCell((short)(6));
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
						cell.setCellValue(rs.getDouble("receiveValue"));					
					} else {
						//12,9
						row = sheet1.getRow(12);
						if (rs.getString("payAllYN").equals("Y")) cell = row.getCell((short)(10));
						else cell = row.getCell((short)(9));
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
						cell.setCellValue(rs.getDouble("receiveValue"));					
					}					
				}

			}
			rs.getStatement().close();
			rs.close();			
			
			//分期
			int intRE = 0, intOC = 0;
			double reArea = 0, ocArea = 0;
			sql = "select a.levyType, decode(a.payAllYN,'Y','Y','N') as payAllYN,  count(*) as recCount, sum(a.receiveValue+nvl(a.interestValue,0)+nvl(a.otherValue,0)) as receiveValue, sum(a.divideArea) as divideArea from npbbl_levy a where nvl(a.delYN,'N')<>'Y' and a.levyType in ('3','4') and nvl(a.installmentyn,'N')='Y' " + preSQL +
				" group by a.levyType, decode(a.payAllYN,'Y','Y','N') ";			
			rs = db.querySQL(sql);
			while (rs.next()) {
				if (rs.getString("levyType").equals("3")) {
					//17,6
					row = sheet1.getRow(17);
					cell = row.getCell((short)(3));
					intRE = intRE + rs.getInt("recCount");
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(intRE);
					
					cell = row.getCell((short)(4));
					reArea = reArea + rs.getDouble("divideArea");
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(reArea);					
					
					if (rs.getString("payAllYN").equals("Y")) cell = row.getCell((short)(7));						
					else cell = row.getCell((short)(6));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble("receiveValue"));					
				} else {
					//18,9
					row = sheet1.getRow(18);
					
					cell = row.getCell((short)(3));
					intOC = intOC + rs.getInt("recCount");
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(intOC);
					
					cell = row.getCell((short)(4));
					ocArea = ocArea + rs.getDouble("divideArea");
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(ocArea);						
					
					if (rs.getString("payAllYN").equals("Y")) cell = row.getCell((short)(10));
					else cell = row.getCell((short)(9));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble("receiveValue"));					
				}
			}
			rs.getStatement().close();
			rs.close();			
						
			
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"npbLevy.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"npbLevy.xls");
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
