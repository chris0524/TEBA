package sys.st;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;


/**
 * 市有財產有價證券統計圖表
 * 
 * @version 1.0, Jul 5, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST006R extends SuperBean{
	
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
			HSSFDataFormat format = wb.createDataFormat();		    
	        HSSFCellStyle borderStyle = wb.createCellStyle();
	        borderStyle.setBorderBottom(BorderStyle.THIN);
	        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderLeft(BorderStyle.THIN);
	        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderRight(BorderStyle.THIN);
	        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        borderStyle.setBorderTop(BorderStyle.THIN);
	        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			
			HSSFCellStyle style = wb.createCellStyle();		
		    style.setDataFormat(format.getFormat(("#,##0.00")));
	        style.setBorderBottom(BorderStyle.THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(BorderStyle.THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderRight(BorderStyle.THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderTop(BorderStyle.THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());	    
			
			HSSFCellStyle styleNum = wb.createCellStyle();
		    styleNum.setDataFormat(format.getFormat(("#,##0.00")));	
		    
		    
			HSSFCellStyle stylePercent = wb.createCellStyle();
			stylePercent.setDataFormat(format.getFormat(("#,##0.00%")));		
	        stylePercent.setBorderBottom(BorderStyle.THIN);
	        stylePercent.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        stylePercent.setBorderLeft(BorderStyle.THIN);
	        stylePercent.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        stylePercent.setBorderRight(BorderStyle.THIN);
	        stylePercent.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        stylePercent.setBorderTop(BorderStyle.THIN);
	        stylePercent.setTopBorderColor(IndexedColors.BLACK.getIndex());
		    
		    
			HSSFCellStyle styleAlign = wb.createCellStyle();
	        styleAlign.setBorderBottom(BorderStyle.THIN);
	        styleAlign.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        styleAlign.setBorderLeft(BorderStyle.THIN);
	        styleAlign.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        styleAlign.setBorderRight(BorderStyle.THIN);
	        styleAlign.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        styleAlign.setBorderTop(BorderStyle.THIN);
	        styleAlign.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        // 對齊修正
	        styleAlign.setAlignment(HorizontalAlignment.CENTER_SELECTION);
	        styleAlign.setVerticalAlignment(VerticalAlignment.CENTER);
			
			row = sheet1.getRow(1);
			if (row==null) row = sheet1.createRow((short)1);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("資料日期："+Common.MaskCDate(Datetime.getYYYMMDD()));
			
			String preSQL = "";			
			if (!"".equals(getClosingYM())) preSQL = " and nvl(a.closing,'N') = " + Common.sqlChar(getClosingYM());
			if (!"".equals(getEnterOrg())) preSQL = " and a.enterOrg = " + Common.sqlChar(getEnterOrg());			
						
			//計算有價證券	
			String sql = " select a.propertyKind, decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用',a.propertyKind) propertyKindName, a.securityname, sum(a.bookAmount) as bookAmount, sum(a.bookValue)/1000000 as currentValue, sum(a.bookAmount)/decode(sum(a.capitalStock),0,1) as shares " +
					" from UNTVP_AddProof a where a.ownership='1' and a.datastate='1' and a.verify='Y' " + preSQL +
					" group by a.propertyKind, decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用',a.propertyKind), a.securityname order by a.propertyKind, a.securityname ";
			ResultSet rs = db.querySQL(sql);			
			Hashtable h = new Hashtable();
			//10,1			
			int initRow = 10;
			while (rs.next()){
				Integer r = (Integer)h.get(rs.getString("propertyKind"));
				Double total = (Double)h.get(rs.getString("propertyKind")+"T");
		        if(r==null){
		        	r = new Integer(initRow);
		        	total = new Double(rs.getDouble("currentValue"));
		        	h.put(rs.getString("propertyKind")+"S", r);
		        	h.put(rs.getString("propertyKind")+"F", r);
		        }else{		        	
		            r = new Integer(r.intValue()+1);
		            total = new Double(total.doubleValue()+rs.getDouble("currentValue"));
		        }
		        h.put(rs.getString("propertyKind"),r);		   
	        	h.put(rs.getString("propertyKind")+"T", total);		        
		        
	        	//4,1
				row = sheet1.getRow(rs.getInt("propertyKind")+3);
				if (row==null) row = sheet1.createRow((short)(rs.getInt("propertyKind")+3));
				cell = row.getCell((short)1);
				if (cell==null) cell = row.createCell((short)1);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11					
				cell.setCellValue(total.doubleValue());
				//String equation = "SUM(D"+(((Integer)h.get(rs.getString("propertyKind")+"S")).intValue()+1)+":D"+(r.intValue()+1)+")";				
				//cell.setCellFormula("SUM(D"+(((Integer)h.get(rs.getString("propertyKind")+"S")).intValue()+1)+":D"+(r.intValue()+1)+")");
				//cell.setCellStyle(styleNum);
				
				h.put(rs.getString("propertyKind")+"F","SUM(D"+(((Integer)h.get(rs.getString("propertyKind")+"S")).intValue()+1)+":D"+(r.intValue()+1)+")");
					        	
				row = sheet1.getRow(r.intValue());
				if (row==null) row = sheet1.createRow((short)r.intValue());
						        
				cell = row.getCell((short)0);
				if (cell==null) cell = row.createCell((short)0);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11					
				cell.setCellValue(rs.getString("propertyKindName"));
				cell.setCellStyle(styleAlign);				
				
				cell = row.getCell((short)1);
				if (cell==null) cell = row.createCell((short)1);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11					
				cell.setCellValue(rs.getString("securityname"));
				cell.setCellStyle(borderStyle);
				
				//3~6
				for (int i=0; i<3; i++) {
					cell = row.getCell((short)(2+i));
					if (cell==null) cell = row.createCell((short)(2+i));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11					
					cell.setCellValue(rs.getDouble(i+4));
					if (i==2) cell.setCellStyle(stylePercent);
					else cell.setCellStyle(style);											
				}
				cell = row.getCell((short)5);
				if (cell==null) cell = row.createCell((short)5);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11					
				cell.setCellValue("");
				cell.setCellStyle(borderStyle);		
				
				//Region region = new Region(((Integer)h.get(rs.getString("propertyKind")+"S")).intValue(),(short)0,r.intValue(),(short)0);
				int firstRow = ((Integer) h.get(rs.getString("propertyKind") + "S")).intValue();
				int lastRow = r.intValue();

				// 取代 Region
				CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, 0, 0);

				// 使用 addMergedRegion 方法
				sheet1.addMergedRegion(region);
				
				row = sheet1.getRow(((Integer)h.get(rs.getString("propertyKind")+"S")).intValue());
				if (row!=null) {
					cell = row.getCell((short)0);
					if (cell!=null) {
						cell.setCellStyle(styleAlign);	
					}
				}				
				initRow = initRow + 1;
			}
			rs.close();
			
			for (int i=1; i<5; i++) {
				String equation = (String) h.get(Common.formatFrontZero(""+i, 2)+"F");
				if (equation!=null) {
					row = sheet1.getRow(i+3);
					if (row==null) row = sheet1.createRow((short)(i+3));
					cell = row.getCell((short)1);
					if (cell==null) cell = row.createCell((short)1);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11					
					cell.setCellFormula(equation);				
				}
			}
			
			if (initRow>10) {
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
				cell.setCellStyle(styleAlign);
				//Region region = new Region(initRow,(short)0,initRow,(short)1);
				CellRangeAddress region = new CellRangeAddress(initRow, initRow, 0, 1);
				sheet1.addMergedRegion(region);				
				
				cell = row.getCell((short)2);
				if (cell==null) cell = row.createCell((short)2);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11				
				cell.setCellFormula("SUM(C11:C"+(initRow)+")");
				cell.setCellStyle(style);
				
				cell = row.getCell((short)3);
				if (cell==null) cell = row.createCell((short)3);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellFormula("SUM(D11:D"+(initRow)+")");	
				cell.setCellStyle(style);
				
				cell = row.getCell((short)4);
				if (cell==null) cell = row.createCell((short)4);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11	
				cell.setCellStyle(borderStyle);
				
				cell = row.getCell((short)5);
				if (cell==null) cell = row.createCell((short)5);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11	
				cell.setCellStyle(borderStyle);				
			}
			
			
			
			
			//HSSFFooter footer = sheet1.getFooter();
		    //footer.setCenter("第　" + HSSFFooter.page() + "　頁，共　 " + HSSFFooter.numPages() + "　頁");			
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"stocks.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"stocks.xls");					
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
