package unt.rp;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;

import sys.web.MyServletContext;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean2;

/**
*<br>程式目的：報廢財產清冊明細
*<br>程式代號：UNTRP011R
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
 */
public class UNTRP011R extends SuperBean2{
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_reduceDateS;
	private String q_reduceDateE;
	private String q_caseName;
	private String q_writeDateS;
	private String q_writeDateE;
	private String isOrganManager;
	private String isAdminManager;
	private String organID;   
	private String excelFileName;
	private String q_year;
	private String q_doc;
	private String temp;
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_reduceDateS(){ return checkGet(q_reduceDateS); }
	public void setQ_reduceDateS(String s){ q_reduceDateS=checkSet(s); }
	public String getQ_reduceDateE(){ return checkGet(q_reduceDateE); }
	public void setQ_reduceDateE(String s){ q_reduceDateE=checkSet(s); }
	public String getQ_caseName(){ return checkGet(q_caseName); }
	public void setQ_caseName(String s){ q_caseName=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getExcelFileName(){ return checkGet(excelFileName); }
	public void setExcelFileName(String s){ excelFileName = checkSet(s); }
	public String getQ_year(){ return checkGet(q_year); }
	public void setQ_year(String s){ q_year = checkSet(s); }	
	public String getQ_doc(){ return checkGet(q_doc); }
	public void setQ_doc(String s){ q_doc = checkSet(s); }
	public String getTemp(){ return checkGet(temp); }
	public void setTemp(String s){ temp = checkSet(s); }
	
	
	
	public void exportAll() throws Exception{
		String[] excelTitles = {"公務", "基金", "非消耗品", "基金-非消耗品", "公務-非消耗品", "基金-汽機車", "公務-汽機車"};
		String[] rsTitles = {"項次", "報廢單編號(年/編號)", "財產編號", "財產名稱", "單位", "數量", "總價", "使用年限", "購置日期", "保管單位", "申請/保管人"};
		Boolean dataFlag = true;
		StringBuffer sql = new StringBuffer();
		
		UNTCH_Tools ut = new UNTCH_Tools();
    	ResultSet rs = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle();
		HSSFRow row;
		HSSFCell cell;
		HSSFSheet[] sheet = new HSSFSheet[excelTitles.length];
		int i,j;
		
		for(i = 0; i < excelTitles.length; i++) {
			sql.setLength(0);
			//建立頁籤
			sheet[i] = wb.createSheet(getQ_year() + "-" + getQ_doc() + excelTitles[i]);
			
			//標題
			sheet[i].addMergedRegion(new CellRangeAddress(0, 0, 0, rsTitles.length - 1));
			row = sheet[i].createRow(0);
			row.setHeightInPoints(30);
			cell = row.createCell(0);
			cell.setCellValue(getQ_year() + "年第" + getQ_doc() + "次報廢財物變賣彙整清單(" + excelTitles[i] + ")");
			cell.setCellStyle(getCellStyle(style, "title"));
		
			//欄位名稱
			row = sheet[i].createRow(1);
			row.setHeightInPoints(35);
			for (j = 0; j < rsTitles.length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(rsTitles[j]);
				cell.setCellStyle(getCellStyle(style, "cell"));
			}
			
			//判斷頁籤SQL
			sql.append(" SET CONCAT_NULL_YIELDS_NULL OFF; ")
				.append(getQueryCondition(i))
				.append(" order by proofdata, property");
		
			//資料
			Database db = new Database();
			int rowNumber = 0;
			int total = 0;
			try {				
				rs = db.querySQL_NoChange(sql.toString());				
				while(rs.next()) {
					total += Integer.parseInt(rs.getString(6));
					row = sheet[i].createRow(rowNumber + 2);
					row.setHeightInPoints(25);
					for (j = 0; j < rsTitles.length; j++) {
						cell = row.createCell(j);
						cell.setCellStyle(getCellStyle(style, "cell"));
						if(j == 0) {
							cell.setCellValue(rowNumber + 1);
						} else if (j == 1) {
							cell.setCellValue(checkProofData(Common.get(rs.getString(j))));	
						} else if (j == 5) {
							cell.setCellValue((Common.get(rs.getString(j))).substring(0,1));
						} else if (j == 8){
							cell.setCellValue(setROCDateFormat(Common.get(ut._transToROC_Year(rs.getString(j)))));										
						} else {
							cell.setCellValue(Common.get(rs.getString(j)));	
						}	
					}
					rowNumber++;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				dataFlag = false;
			}finally{
		        db.closeAll();
		    }
			
			//設定欄寬
			for (j = 0; j < rsTitles.length; j++) {
				sheet[i].autoSizeColumn(j);
				sheet[i].setColumnWidth(j,sheet[i].getColumnWidth(j) * 12/10);
			}
			
			//頁尾		
			row = sheet[i].createRow(rowNumber + 3);
			row.setHeightInPoints(25);
			cell = row.createCell(2);
			cell.setCellValue(getQ_year() + "年第" + getQ_doc() + "次報廢(" + excelTitles[i] + ")");
			cell = row.createCell(3);
			cell.setCellValue("共" + rowNumber + "項");
			cell = row.createCell(6);
			cell.setCellValue(total);
		}
		
		if(dataFlag) {
			String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");	
	        
	        ServletContext context = MyServletContext.getInstance().getServletContext();
	        File tempDirectory = new File(context.getInitParameter("filestoreLocation"));
			
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();
			
			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"UNTRP011R"+".xls");
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"UNTRP011R"+".xls");
			super.setState("exportAll");			
		}else {
			super.setState("exportfail");
		}
		
	}	
	
	private String getQueryCondition(int kind) {
		
		StringBuffer resultMP = new StringBuffer();
		StringBuffer resultNE = new StringBuffer();
	
			resultMP.append(" select ").append(
					" b.proofyear + '年' + b.proofdoc + '字第' + b.proofno + '號'  as proofdata, ").append(
					" a.propertyno + '-' + a.serialno as property, ").append(
					" (select c.propertyname from SYSPK_PROPERTYCODE c where c.enterorg in ('000000000A','" + this.getOrganID() + "') and a.propertyno = c.propertyno ) as propertyname, ").append(
					" a.otherpropertyunit, a.adjustbookamount, a.adjustbookvalue, ").append(
					" (select d.limityear + '年' from SYSPK_PROPERTYCODE d where d.enterorg in ('000000000A','" + this.getOrganID() + "') and a.propertyno = d.propertyno ) as limityear, ").append(
					" a.buydate, ").append(
					" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit, ").append(
					" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper ").append(
					" from UNTMP_REDUCEDETAIL a ").append(
					" left join UNTMP_REDUCEPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
					" where 1=1 ").append(
					" and a.verify = 'Y' and a.enterorg = '" + this.getOrganID() + "'").append(
						getQ_Query());
		
			resultNE.append(" select ").append(
					" b.proofyear + '年' + b.proofdoc + '字第' + b.proofno + '號'  as proofdata, ").append(
					" a.propertyno + '-' + a.serialno as property, ").append(
					" (select c.propertyname from SYSPK_PROPERTYCODE2 c where c.enterorg in ('000000000A','" + this.getOrganID() + "') and a.propertyno = c.propertyno ) as propertyname, ").append(
					" a.otherpropertyunit, a.adjustbookamount, a.adjustbookvalue, ").append(
					" (select d.limityear + '年' from SYSPK_PROPERTYCODE2 d where d.enterorg in ('000000000A','" + this.getOrganID() + "') and a.propertyno = d.propertyno ) as limityear, ").append(
					" a.buydate, ").append(
					" (select unitname from UNTMP_KEEPUNIT z where z.enterorg = a.enterorg and z.unitno = a.keepunit) as unit, ").append(
					" (select keepername from UNTMP_KEEPER z where z.enterorg = a.enterorg and z.keeperno = a.keeper) as keeper ").append(
					" from UNTNE_REDUCEDETAIL a ").append(
					" left join UNTNE_REDUCEPROOF b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno ").append(
					" where 1=1 ").append(
					" and a.verify = 'Y' and a.enterorg = '" + this.getOrganID() + "'").append(
							getQ_Query());
			
			if(kind == 0) {
			//取公務類 110 動產，並排除汽機車(40107)
				resultMP.append(" and b.differencekind = '110' ")
						.append(" and a.propertyno not like '40107%' ");
			} else if (kind == 1) {
			//取基金類 120 動產，並排除汽機車(40107)
				resultMP.append(" and b.differencekind = '120' ")
						.append(" and a.propertyno not like '40107%' ");
			} else if (kind == 2)  {
			//取非消耗品類 500 動產，並排除汽機車(40107)	
				resultMP.append(" and 1 != 1 ")
						.append(" union ")
						.append(resultNE.toString())
						.append(" and b.differencekind = '500' ")
						.append(" and a.propertyno not like '40107%' ");
			} else if (kind == 3)  {
			//取基金類 120 且為非消耗品，沒有排除汽機車
				resultMP.append(" and 1 != 1 ")
						.append(" union ")
						.append(resultNE.toString())
						.append(" and b.differencekind = '120' ");
			} else if (kind == 4)  {
			//取公務類 110 且為非消耗品，沒有排除汽機車
				resultMP.append(" and 1 != 1 ")
						.append(" union ")
						.append(resultNE.toString())
						.append(" and b.differencekind = '110' ");
			} else if (kind == 5) {
			//取基金類的汽機車(40107)
				resultMP.append(" and b.differencekind = '120' ")
						.append(" and a.propertyno like '40107%' ");
			} else if (kind == 6) {
			//取公務類的汽機車(40107)	
				resultMP.append(" and b.differencekind = '110' ")
						.append(" and a.propertyno like '40107%' ");
			}
			
		return resultMP.toString();
		
	}
	

	private String getQ_Query() {
		StringBuilder result = new StringBuilder();
		
		if (!"".equals(Common.get(getQ_writeDateS())))
			result.append(" and b.writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2")));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		result.append(" and b.writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2")));
    	if (!"".equals(Common.get(getQ_reduceDateS())))
    		result.append(" and b.reducedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_reduceDateS(),"2")));
    	if (!"".equals(Common.get(getQ_reduceDateE())))
    		result.append(" and b.reducedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_reduceDateE(),"2")));
    	
    	return result.toString();
		
	}
	
	private HSSFCellStyle getCellStyle(HSSFCellStyle style, String kind) {
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	    style.setAlignment(HorizontalAlignment.CENTER);
	    
	    if ("cell".equals(kind)) {
	        style.setBorderBottom(BorderStyle.THIN);
	        style.setBorderLeft(BorderStyle.THIN);
	        style.setBorderRight(BorderStyle.THIN);
	        style.setBorderTop(BorderStyle.THIN);
	    }
	    return style;
	}	
	
	private String checkProofData(String proofData) {
		if (proofData.equals(getTemp())) {
			return "";
		} else {
			setTemp(proofData);
			return getTemp();
		}
	}
	
	private String setROCDateFormat(String ROCDate) {
		if (ROCDate.length() == 7) {
			return ROCDate.substring(0, 3) + "/" + ROCDate.substring(3, 5) + "/" + ROCDate.substring(5);
		}
		return ROCDate;
	}
}
