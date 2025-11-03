package sys.st;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import util.Common;
import util.Database;
import util.SuperBean;

/**
 * 非公用房地標(讓)售情形表
 * 
 * @version 1.0, Aug 1, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST012R extends SuperBean {
	
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
	
	String q_year1, q_year2;
	public String getQ_year1() { return checkGet(q_year1); }
	public void setQ_year1(String s) { q_year1=checkSet(s); }	
	public String getQ_year2() { return checkGet(q_year2); }
	public void setQ_year2(String s) { q_year2=checkSet(s); }	

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
			Hashtable h = new Hashtable();			
			String sTitle = "";
			//" select a.caseNo, a.caseState, a.rightDate, substr(a.rightDate,1,3) as yyy, a.levyno, a.levyNo2, a.landValue, a.buildvalue, b. from npbsa_case a where 1=1 ";			
			String sql = " select a.signNo, b.caseNo, b.caseState, b.rightDate, substr(b.rightDate,1,3) as yyy, b.levyno, b.levyNo2, b.landvalue, b.buildValue, (b.landValue+b.buildValue) as sumValue, c.propertyType, c.dividearea, " +
				" decode(substr(c.propertyno,1,1),'1',e.Signdesc||SubStr(a.signNo,8,4)||'-'||SubStr(a.signNo,12),'2',e.Signdesc||SubStr(a.signNo,8,5)||'-'||SubStr(a.signNo,13)) signName " +
				" from qry_unt_land_building a, npbsa_case b, npbsa_property c, sysca_sign e " +
				" where b.caseState>='30' and a.enterorg=c.enterOrg and a.propertytype=c.propertyType " +
				" and a.propertyno=c.propertyNo and a.serialno=c.serialNo " +
				" and b.caseNo=c.caseNo and substr(a.signNo,1,7)=e.signno ";			
			
			if (!"".equals(getQ_year1())) {
				sql += " and substr(b.rightDate,1,3)>=" + Common.sqlChar(getQ_year1());
			}
			if (!"".equals(getQ_year2())) {
				sql += " and substr(b.rightDate,1,3)<=" + Common.sqlChar(getQ_year2());
			}			
			sql += " order by b.rightDate, b.caseNo ";
			
			if (getQ_year1().equals(getQ_year2())) sTitle = "高雄市政府財政局 " + Integer.parseInt(getQ_year1()) + " 年度非公用房地標(讓)售情形表";
			else sTitle = "高雄市政府財政局 " + Integer.parseInt(getQ_year1()) + "~" + Integer.parseInt(getQ_year2()) + " 年度非公用房地標(讓)售情形表";

			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filestoreLocation));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet1 = wb.getSheetAt(0);		
			HSSFRow row;		
			HSSFCell cell;		
			CellRangeAddress region;			
			
			HSSFDataFormat format = wb.createDataFormat();		    
	        // 建立帶邊框的樣式
	        CellStyle borderStyle = wb.createCellStyle();
	        borderStyle.setBorderBottom(BorderStyle.THIN);
	        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderLeft(BorderStyle.THIN);
	        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderRight(BorderStyle.THIN);
	        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderTop(BorderStyle.THIN);
	        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());		
			
	        // 設定數字格式（#,##0.00）
	        CellStyle style = wb.createCellStyle();
	        style.setDataFormat(format.getFormat("#,##0.00"));
	        style.setBorderBottom(BorderStyle.THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(BorderStyle.THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderRight(BorderStyle.THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderTop(BorderStyle.THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());	
	        style.setAlignment(HorizontalAlignment.RIGHT);
	        style.setVerticalAlignment(VerticalAlignment.CENTER);	        
		    
	        // 建立帶對齊的樣式
	        CellStyle styleAlign = wb.createCellStyle();
	        styleAlign.setBorderBottom(BorderStyle.THIN);
	        styleAlign.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        styleAlign.setBorderLeft(BorderStyle.THIN);
	        styleAlign.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        styleAlign.setBorderRight(BorderStyle.THIN);
	        styleAlign.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        styleAlign.setBorderTop(BorderStyle.THIN);
	        styleAlign.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        // 對齊方式（置中選擇）
	        styleAlign.setAlignment(HorizontalAlignment.CENTER_SELECTION);
	        styleAlign.setVerticalAlignment(VerticalAlignment.CENTER);	
			
			row = sheet1.getRow(0);
			if (row==null) row = sheet1.createRow((short)0);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue(sTitle);
					
			ResultSet rs = db.querySQL(sql);
			int initRow = 3, rowid=0;		
			int counter = 0;
			while (rs.next()) {
				ResultSet rc;
				Integer r = (Integer)h.get(rs.getString("caseNo"));
				Double totalArea = (Double)h.get(rs.getString("caseNo")+"totalArea");
				Double receiveValue = (Double)h.get(rs.getString("caseNo")+"receiveValue");
				String closeDate1 = (String)h.get(rs.getString("caseNo")+"closeDate1");
				String closeDate2 = (String)h.get(rs.getString("caseNo")+"closeDate2");
		        if(r==null){		        	
		        	r = new Integer(initRow);		        	
		        	h.put(rs.getString("caseNo")+"S", r);
		        	
		        	totalArea = new Double(0);
		        	rc = db.querySQL("select sum(divideArea) as totalArea from npbsa_property where caseNo='" + rs.getString("caseNo") + "'");
		        	if (rc.next()) {
		        		totalArea = new Double(rc.getDouble("totalArea"));	
		        	}
		        	h.put(rs.getString("caseNo")+"totalArea", totalArea);		        	
		        	rc.getStatement().close();
		        	rc.close();        	
		        	
		            if (rs.getString("propertyType").equals("2")) {
		            	h.put(rs.getString("caseNo")+"BU", r);		            	
		            } else {
		            	h.put(rs.getString("caseNo")+"LA", r);		            	
		            }
		            
		            receiveValue = new Double(0);
		            closeDate1 = "";
		            closeDate2 = "";
		            if (!"".equals(rs.getString("levyNo"))) {
						sql = " select distinct receiveValue, closeDate from NPBBL_Bill where levyNo='"+ Common.get(rs.getString("levyNo")) + "' and closeYN='Y' ";
						rc = db.querySQL(sql);
						if (rc.next()){							
							receiveValue = new Double(receiveValue.doubleValue()+rc.getDouble("receiveValue"));
							closeDate1 = rs.getString("closeDate");
						}
						rc.getStatement().close();
						rc.close();
		            }		            
		            if (!"".equals(rs.getString("levyNo2"))) {
						sql = " select distinct receiveValue, closeDate from NPBBL_Bill where levyNo='"+ Common.get(rs.getString("levyNo2")) + "' and closeYN='Y' ";
						rc = db.querySQL(sql);
						if (rc.next()){
							receiveValue = new Double(receiveValue.doubleValue()+rc.getDouble("receiveValue"));
							closeDate2 = rs.getString("closeDate");
						}
						rc.getStatement().close();
						rc.close();
		            }
		            h.put(rs.getString("caseNo")+"closeDate1", closeDate1);
		            h.put(rs.getString("caseNo")+"closeDate2", closeDate2);
		            h.put(rs.getString("caseNo")+"receiveValue", receiveValue);
		            rowid = rowid + 1;
		        }else{
		            r = new Integer(initRow);
		            
		            if (rs.getString("propertyType").equals("2")) {
			            Integer t = (Integer)h.get(rs.getString("caseNo")+"BU");
			            if (t==null) {
			            	h.put(rs.getString("caseNo")+"BU", r);
			            	h.put(rs.getString("caseNo")+"BUE", r);
			            } else h.put(rs.getString("caseNo")+"BUE", r);
		            } else {
			            Integer t = (Integer)h.get(rs.getString("caseNo")+"LA");		            	
			            if (t==null) {
			            	h.put(rs.getString("caseNo")+"LA", r);
			            	h.put(rs.getString("caseNo")+"LAE", r);
			            } else h.put(rs.getString("caseNo")+"LAE", r);
		            }
		        }
		        h.put(rs.getString("caseNo"),r);
		        h.put(rs.getString("caseNo")+"totalArea", totalArea);	        				
	        	
				row = sheet1.getRow(initRow);		
				if (row==null) row = sheet1.createRow((short)(initRow));
				
				cell = row.getCell((short)0);
				if (cell==null) cell = row.createCell((short)0);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rowid);
				cell.setCellStyle(styleAlign);
				
				cell = row.getCell((short)1);
				if (cell==null) cell = row.createCell((short)1);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(Common.get(rs.getString("rightDate")));
				cell.setCellStyle(styleAlign);
				
				cell = row.getCell((short)2);
				if (cell==null) cell = row.createCell((short)2);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(Common.get(rs.getString("signName")));
				cell.setCellStyle(borderStyle);		
				
				cell = row.getCell((short)3);
				if (cell==null) cell = row.createCell((short)3);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("divideArea"));
				cell.setCellStyle(style);
				
				double price = 0;
				try {
					price = (rs.getDouble("landValue")+rs.getDouble("buildValue"))/totalArea.doubleValue();
				} catch (NumberFormatException e) {
					price = 0;
				}
				cell = row.getCell((short)4);
				if (cell==null) cell = row.createCell((short)4);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(price);
				cell.setCellStyle(style);				

				cell = row.getCell((short)5);
				if (cell==null) cell = row.createCell((short)5);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("landValue")+rs.getDouble("buildValue"));
				cell.setCellStyle(style);				
							
				cell = row.getCell((short)6);
				if (cell==null) cell = row.createCell((short)6);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(receiveValue.doubleValue());
				cell.setCellStyle(style);	
				
	            if (rs.getString("propertyType").equals("2")) {
					cell = row.getCell((short)7);
					if (cell==null) cell = row.createCell((short)7);				
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(closeDate2);
					cell.setCellStyle(styleAlign);	            	            	
	            } else {
					cell = row.getCell((short)7);
					if (cell==null) cell = row.createCell((short)7);				
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(closeDate1);
					cell.setCellStyle(styleAlign);		            	
	            }
				cell = row.getCell((short)8);
				if (cell==null) cell = row.createCell((short)8);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue("");
				cell.setCellStyle(borderStyle);
				
				//region = new Region(((Integer)h.get(rs.getString("caseNo")+"S")).intValue(),(short)0,r.intValue(),(short)0);				
				//sheet1.addMergedRegion(region);
				
				//region = new Region(((Integer)h.get(rs.getString("caseNo")+"S")).intValue(),(short)4,r.intValue(),(short)4);
				//sheet1.addMergedRegion(region);
				
				//region = new Region(((Integer)h.get(rs.getString("caseNo")+"S")).intValue(),(short)5,r.intValue(),(short)5);
				//sheet1.addMergedRegion(region);
				
				//region = new Region(((Integer)h.get(rs.getString("caseNo")+"S")).intValue(),(short)6,r.intValue(),(short)6);
				//sheet1.addMergedRegion(region);		
				
				// 取得開始合併的列索引
				int firstRow = ((Integer) h.get(rs.getString("caseNo") + "S")).intValue();
				int lastRow = r.intValue();

				// 取代舊的 Region
				sheet1.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 0, 0)); // 第 0 欄合併
				sheet1.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 4, 4)); // 第 4 欄合併
				sheet1.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 5, 5)); // 第 5 欄合併
				sheet1.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 6, 6)); // 第 6 欄合併				
						
				initRow = initRow + 1;
				counter++;			
			}
			rs.getStatement().close();
			rs.close();
			
			if (initRow>3) {
				row = sheet1.getRow(initRow);
				if (row==null) row = sheet1.createRow((short)(initRow));
				
				cell = row.getCell((short)0);
				if (cell==null) cell = row.createCell((short)0);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue("合計");
				cell.setCellStyle(styleAlign);
				
				cell = row.getCell((short)1);
				if (cell==null) cell = row.createCell((short)1);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellStyle(style);	
				
				cell = row.getCell((short)2);
				if (cell==null) cell = row.createCell((short)2);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellStyle(style);					
				
				//region = new Region(initRow,(short)0,initRow,(short)2);	
				region = new CellRangeAddress(initRow, initRow, 0, 2);
				sheet1.addMergedRegion(region);
			
				String equation = "SUM(D4:D"+initRow+")";				
				cell = row.getCell((short)3);
				if (cell==null) cell = row.createCell((short)3);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11				
				cell.setCellFormula(equation);
				cell.setCellStyle(style);
				
				cell = row.getCell((short)4);
				if (cell==null) cell = row.createCell((short)4);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellStyle(style);				
				
				equation = "SUM(F4:F"+initRow+")";				
				cell = row.getCell((short)5);
				if (cell==null) cell = row.createCell((short)5);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11				
				cell.setCellFormula(equation);
				cell.setCellStyle(style);
				
				equation = "SUM(G4:G"+initRow+")";				
				cell = row.getCell((short)6);
				if (cell==null) cell = row.createCell((short)6);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11				
				cell.setCellFormula(equation);
				cell.setCellStyle(style);	
				
				cell = row.getCell((short)7);
				if (cell==null) cell = row.createCell((short)7);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellStyle(style);	
				
				cell = row.getCell((short)8);
				if (cell==null) cell = row.createCell((short)8);				
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellStyle(style);
				
			}

	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"npbsaState.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"npbsaState.xls");
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
