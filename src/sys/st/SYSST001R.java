package sys.st;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;

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
 * 市有財產統計總表
 * 
 * @version 1.0, Jun 26, 2006
 * @author  clive.chang
 * @since   TAJK Project
 */

public class SYSST001R extends SuperBean{
	
	String enterOrg;
	String enterOrgName;
	String closingYM;
	String closingID;
	String closingDate;
	String valuable;
	String excelFile;
	String filestoreLocation;
	String tempFolder;

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
			
			double total = 0, land = 0, building = 0, modifyLand = 0;
			double realty = 0, moveable = 0, stocks = 0, right = 0;
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filestoreLocation));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet1 = wb.getSheetAt(0);		
			HSSFRow row;		
			HSSFCell cell;		
			row = sheet1.getRow(6);
			if (row==null) row = sheet1.createRow((short)6);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue("資料日期："+Common.MaskCDate(Datetime.getYYYMMDD()));
			
			//計算土地
			String sql = " select count(*) as recCount, (sum(a.holdArea)/10000) as holdArea, " +  
				" sum(a.holdArea * (select b.priceUnit from UNTLA_Price b where " + 
				" a.enterOrg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyNo and a.serialno=b.serialno " +
				" and b.bulletinDate=(select max(c.bulletinDate) from UNTLA_Price c where a.enterOrg=c.enterorg and a.ownership=c.ownership and a.propertyno=c.propertyNo and a.serialno=c.serialno))) " +
				" as currentValue " +
				" from UNTLA_Land a where a.ownership='1' and a.dataState='1' and a.verify='Y' ";		
			ResultSet rs = db.querySQL(sql+preSQL);
			int r = 11;
			int j = 0;
			if (rs.next()) {
				//11,3
				row = sheet1.getRow(r);
				for (j=0; j<3; j++) {
					cell = row.getCell((short)(3+j));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(j+1));					
				}				
				total += rs.getDouble("currentValue");
				land = rs.getDouble("currentValue");
			}
			r = r + 1;
			rs.getStatement().close();
			rs.close();
			
			//計算建物
			sql = " select count(*) as recCount, sum(a.holdArea) as holdArea, " +  
				" sum(nvl(a.bookValue,0)) as currentValue " +
				" from Untbu_Building a where a.ownership='1' and a.dataState='1' and a.verify='Y' ";			
			rs = db.querySQL(sql+preSQL);
			while (rs.next()){				
				row = sheet1.getRow(r);
				for (j=0; j<3; j++) {
					cell = row.getCell((short)(3+j));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(j+1));					
				}	
				total += rs.getDouble("currentValue");
				building = rs.getDouble("currentValue");
			}		
			r = r + 1;
			rs.getStatement().close();
			rs.close();
			//End of Building
			
			//計算土地改良物
			sql = " select count(*) as recCount, sum(a.measure) as holdArea, " +  
				" sum(nvl(a.bookValue,0)) as currentValue " +
				" from UNTRF_Attachment a where a.ownership='1' and a.dataState='1' and a.verify='Y' ";			
			rs = db.querySQL(sql+preSQL);
			if (rs.next()){				
				row = sheet1.getRow(r);
				for (j=0; j<3; j++) {
					cell = row.getCell((short)(3+j));
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getDouble(j+1));
				}	
				total += rs.getDouble("currentValue");
				modifyLand = rs.getDouble("currentValue");
			}
			rs.getStatement().close();
			rs.close();
			//End of UNTRF_Attachment			
			
			
			//不動產合計
			realty = total;
			
			
			//計算動產	
			sql = "	select substr(a.propertyNo,0,1) as propertyKind, sum(nvl(b.bookValue,0)) as currentValue  " +
				" from UNTMP_MOVABLE a, Untmp_Movabledetail b " +
				" where a.ownership='1' and a.datastate='1' and a.verify='Y' " + 
				" and a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyno=b.propertyno and a.lotno=b.lotno "+preSQL +
				" and substr(a.propertyNo,0,1) in ('3','4','5') group by substr(a.propertyNo,0,1) order by substr(a.propertyNo,0,1) ";				
			rs = db.querySQL(sql);
			r = 15;
			while (rs.next()){				
				if (Common.get(rs.getString("propertyKind")).equals("3")) {
					row = sheet1.getRow(r);		
				}else if (Common.get(rs.getString("propertyKind")).equals("4")) {
					row = sheet1.getRow(r+1);
				}else if(Common.get(rs.getString("propertyKind")).equals("5")) {
					row = sheet1.getRow(r+2);
				}
				cell = row.getCell((short)3);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble("currentValue"));					
				total += rs.getDouble(2);
				moveable += rs.getDouble(2);				
			}
			rs.getStatement().close();
			rs.close();
			//End of Untmp_Movabledetail
			

			//計算有價證券	
			sql = "	select sum(nvl(a.bookValue,0)) as currentValue  " +
				" from UNTVP_AddProof a where a.ownership='1' and a.datastate='1' and a.verify='Y' ";			
			rs = db.querySQL(sql+preSQL);
			r = 19;
			if (rs.next()){				
				row = sheet1.getRow(r);
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble(1));		
				total += rs.getDouble(1);
				stocks = rs.getDouble(1);
			}
			r = r + 1;
			rs.getStatement().close();
			rs.close();
			//End of UNTVP_AddProof		
			
			
			//計算權利	
			sql = "	select sum(nvl(a.bookValue,0)) as currentValue  " +
				" from UNTRT_AddProof a where a.ownership='1' and a.datastate='1' and a.verify='Y' ";
			rs = db.querySQL(sql+preSQL);
			j = 0;
			while (rs.next()){				
				row = sheet1.getRow(r);
				cell = row.getCell((short)(3));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(rs.getDouble(1));			
				total += rs.getDouble(1);
				right = rs.getDouble(1);
			}
			rs.getStatement().close();
			rs.close();
			//End of UNTRT_AddProof					
			
			
			DecimalFormat df = new DecimalFormat("####0.00");			
			StringBuffer sb = new StringBuffer();			
			sb.append("　　市有不動產、動產、有價證券、權利等類財產，按本局電腦列管資料統計，截至");
			sb.append(Common.MaskCDate(Datetime.getYYYMMDD())).append("止，總值約新台幣");
			sb.append(Common.numToZh(total)).append("，土地部份按各筆土地的公告現值統計總值約新台幣").append(Common.numToZh(land));
			sb.append("，建物以現值計算約新台幣").append(Common.numToZh(building)).append("，土地改良物約新台幣");
			sb.append(Common.numToZh(modifyLand)).append("，不動產合計為新台幣").append(Common.numToZh(realty));
			sb.append("，占總值的");
			if (realty>0) sb.append(df.format((realty/total)*100));
			else sb.append("0");
			sb.append("%；機械及設備、交通及運輸設備與什項設備等動產總值約新台幣");
			sb.append(Common.numToZh(moveable)).append("，占全部財產");
			
			if (moveable>0) sb.append(df.format((moveable/total)*100));
			else sb.append("0");			
			sb.append("%；有價證券總值約新台幣").append(Common.numToZh(stocks)).append("(依面額計算)，占全部財產");
			if (stocks>0) sb.append(df.format((stocks/total)*100));
			else sb.append("0");						
			sb.append("%；權利總值約新台幣").append(Common.numToZh(right));
			sb.append("，占全部財產");			
			if (right>0) sb.append(df.format((right/total)*100));
			else sb.append("0");												
			sb.append("%。");
			
			row = sheet1.getRow(2);
			if (row==null) row = sheet1.createRow((short)2);	
			cell = row.getCell((short)0);
			if (cell==null) cell = row.createCell((short)0);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
			cell.setCellValue(sb.toString());			
			
			
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"assetsAnalysis.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"assetsAnalysis.xls");
	        util.FileDeleteProcess.scheduleDeleteFile(tempDirectory,10*60000);
			super.setState("exportSuccess");			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}	
}
