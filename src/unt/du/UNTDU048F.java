
package unt.du;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.Common;
import util.Database;
import util.SuperBean;


public class UNTDU048F extends SuperBean{

	private String enterOrg_export;
	private String ownership;
	private String differenceKind;
	private String enterOrg_import;
	private String excelFileName;
	private String exportFileName;

    public String[] excelTitle = null; 
    
	private String actionType;
	private String filePlace;
	private String filePath;
	
	
	public String getEnterOrg_export() {return checkGet(enterOrg_export);}
	public void setEnterOrg_export(String enterOrgExport) {enterOrg_export = checkSet(enterOrgExport);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEnterOrg_import() {return checkGet(enterOrg_import);}
	public void setEnterOrg_import(String enterOrgImport) {enterOrg_import = checkSet(enterOrgImport);}	
    public String getExcelFileName() {return checkGet(excelFileName);}
	public void setExcelFileName(String excelFileName) {this.excelFileName = checkSet(excelFileName);}
	public String getExportFileName() {return checkGet(exportFileName);}
	public void setExportFileName(String exportFileName) {this.exportFileName = checkSet(exportFileName);}

	
	public String[] getExcelTitle() {return excelTitle;}
	public void setExcelTitle(String[] excelTitle) {this.excelTitle = excelTitle;}
	
	
	public String getActionType() {return checkGet(actionType);}
	public void setActionType(String actionType) {this.actionType = checkSet(actionType);}
	public String getFilePlace() {return checkGet(filePlace);}
	public void setFilePlace(String filePlace) {this.filePlace = checkSet(filePlace);}
	public String getFilePath() {return checkGet(filePath);}
	public void setFilePath(String filePath) {this.filePath = checkSet(filePath);}
	


	//必輸欄位
	private Map pkCols = new HashMap();
	//數字欄位
	private Map numCols = new HashMap();
	int totalColumns = 25;
	

	 //excel title 欄位
	public void initTitle(){
    	excelTitle = new String[25];
		excelTitle[0] =  "*權屬代碼(不能改)";
		excelTitle[1] =  "*財產區分別代碼(不能改)";
		excelTitle[2] =  "*財產編號(不能改)";
		excelTitle[3] =  "*財產分號(不能改)";
		excelTitle[4] =  "財產名稱";
		excelTitle[5] =  "財產別名";
		excelTitle[6] =  "使用年限";
		excelTitle[7] =  "*購置日期";
		excelTitle[8] =  "財產來源－種類";
		excelTitle[9] =  "財產來源－取得日期";
		excelTitle[10] =  "財產來源－取得文號";
		excelTitle[11] =  "增加原因";
		excelTitle[12] =  "增加原因－其他說明";
		excelTitle[13] =  "抵繳遺產稅";
		excelTitle[14] =  "原始入帳－摘要";
		excelTitle[15] =  "原始入帳－會計科目";
		excelTitle[16] =  "經費來源";
		excelTitle[17] =  "經費來源－其他說明";
		excelTitle[18] =  "建築日期";
		excelTitle[19] =  "資產重估日期";
		excelTitle[20] =  "代管資產－原始總價";
		excelTitle[21] =  "代管資產－代管日前累計折舊";
		excelTitle[22] =  "原財產編號";
		excelTitle[23] =  "原財產分號";
		excelTitle[24] =  "備註";
    }  
    
	public void exportAll() throws Exception{	
		Database db = new Database();
		ResultSet rs = null; 
		int r=0;	
		initTitle();
		setExportFileName("UNTDU048F");
		try {
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet1 = wb.createSheet("Sheet1");	
				HSSFRow row;
				HSSFCell cell;
				//Print Excel Column 
				row = sheet1.createRow((short)r);		
				for (int c=0; c<excelTitle.length; c++) {				
					cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(excelTitle[c]);			
				}

				String querySql=this.getExportAllSQL();


		        String uploadCaseID = new java.rmi.dgc.VMID().toString();
		        uploadCaseID = uploadCaseID.replaceAll(":","_");		
				File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
				tempDirectory = new File(tempDirectory,uploadCaseID);
				tempDirectory.mkdirs();

				
				rs = db.querySQL(querySql);

				r++;
				while (rs.next()){
					row = sheet1.createRow((short)r);				
					for (int c=0; c<excelTitle.length; c++) {
						cell = row.createCell((short)c);
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
						cell.setCellValue(rs.getString(c+1));			
					}			
					r++;			
				}
				rs.close();
		
				FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+getExportFileName()+".xls");			
				wb.write(fout);
				fout.flush();
				fout.close();
				this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+getExportFileName()+".xls");
				
				this.setActionType("exportAll");
//				super.setState("exportAll");			
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}
	
	
	/**
	 * 產生EXCEL報表用SQL
	 * @return SQL語法 
	 */
	private String getExportAllSQL(){
		String querySql="select " +
						"a.ownership, " +
						"a.differencekind, " +
						"a.propertyno, " + 
						"a.serialno, " + 
						"(select b.propertyname from SYSPK_PROPERTYCODE b where b.propertyno = a.propertyno ) propertyname, " + 
						"a.propertyname1, a.otherlimityear, a.buydate, a.sourcekind, a.sourcedate, a.sourcedoc, a.cause, a.cause1, " +
						"a.taxcredit, a.originalnote, " +
						"a.accountingtitle, a.fundssource, a.fundssource1, a.builddate, a.appraisedate, a.escroworivalue,  " + 
						"a.escroworiaccumdepr, a.oldpropertyno, a.oldserialno, a.notes " + 
						"from UNTRF_ATTACHMENT a " + 
						"where 1=1 ";
		if (!"".equals(getEnterOrg_export())){
			querySql+=" and a.enterorg = " + Common.sqlChar(getEnterOrg_export()) ;
		}
		if (!"".equals(getOwnership())){
			querySql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
		}
		if (!"".equals(getDifferenceKind())){
			querySql+=" and a.differenceKind = " + Common.sqlChar(getDifferenceKind()) ;
		}
		return querySql;
	}
	
	public String importAll(){
		String result = "";
		String[][] datas;
		List listError = new ArrayList();
		setPKCols();
		setNumCols();
		try {
			datas = read();
			for(int i=0; i<datas.length; i++){
				String[] rowData = datas[i];
				if(rowData.length != totalColumns ) {
					return "totalColumnIncorrect";
				}
				listError = checkPKNoData(rowData, listError);
				listError = checkNumData(rowData,listError);
	
				if(listError.size() == 0){
					updateUNTRF_ATTACHMENT(rowData);
				}else{
					return "checkDataValue,"+listError.toString();
				}
			}
			
			Iterator iter = listError.iterator();
			while(iter.hasNext()){
				System.out.println(iter.next());
			}
			
			if(datas.length >0){
				result = "importSucess";
			}else{
				result = "noData";
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	  public String[][] read() throws IOException  {
			String[][] rowData = null;
		    File inputWorkbook = new File(this.getFilePath());
		    Workbook w;
		    try {
		      w = Workbook.getWorkbook(inputWorkbook);
		      // Get the first sheet
		      Sheet sheet = w.getSheet(0);
		      // Loop over first 10 column and lines
		      rowData = new String[sheet.getRows()-1][totalColumns];
		      for (int i = 1; i < sheet.getRows(); i++) {
		        for (int j = 0; j < totalColumns; j++) {
		        	  if(j == sheet.getColumns() && sheet.getColumns() < totalColumns){
		        		  rowData[i-1][j]= "";
		        		  System.out.println("");
		        	  }else{
				          Cell cell = sheet.getCell(j, i);
				          rowData[i-1][j]= cell.getContents();
				          System.out.println(cell.getContents());
		        	  }
		        }
		      }
		    } catch (BiffException e) {
		      e.printStackTrace();
		    }
		    return rowData;
	  }
	  
	  public void updateUNTRF_ATTACHMENT(String[]rowData){
		      Database db = new Database();
		 try {			  
		      db.setAutoCommit(false);
			   StringBuffer sbSQL = new StringBuffer();
			   sbSQL.append(" update UNTRF_ATTACHMENT set " )
			  .append("propertyname1="+ Common.sqlChar(rowData[5]) + ", ")
			  .append("otherlimityear="+ Integer.parseInt(rowData[6]) + ", ")
			  .append("buydate="+ Common.sqlChar(rowData[7]) + ", ")
			  .append("sourcekind="+ Common.sqlChar(rowData[8]) + ", ") 
			  .append("sourcedate="+ Common.sqlChar(rowData[9]) + ", ") 
			  .append("sourcedoc="+ Common.sqlChar(rowData[10]) + ", ") 
			  .append("cause="+ Common.sqlChar(rowData[11]) + ", ")
			  .append("cause1="+ Common.sqlChar(rowData[12]) + ", ") 
			  .append("taxcredit="+ Common.sqlChar(rowData[13]) + ", ")
			  .append("originalnote="+ Common.sqlChar(rowData[14]) + ", ")
			  .append("accountingtitle="+ Common.sqlChar(rowData[15]) + ", ")
			  .append("fundssource="+ Common.sqlChar(rowData[16]) + ", ")
			  .append("fundssource1="+ Common.sqlChar(rowData[17]) + ", ")
			  .append("builddate="+ Common.sqlChar(rowData[18]) + ", ")
			  .append("appraisedate="+ Common.sqlChar(rowData[19]) + ", ")
			  .append("escroworivalue="+ Integer.parseInt(rowData[20]) + ", ")
			  .append("escroworiaccumdepr="+ Integer.parseInt(rowData[21]) + ", ")
			  .append("oldpropertyno="+ Common.sqlChar(rowData[22]) + ", ")
			  .append("oldserialno="+ Common.sqlChar(rowData[23]) + ", ")
			  .append("notes="+ Common.sqlChar(rowData[24]))
			  .append(" where 1=1 ")
			  .append("and enterorg=" + Common.sqlChar(getEnterOrg_import()) + " ")
			  .append("and ownership=" + Common.sqlChar(rowData[0]) + " ")
			  .append("and differencekind=" + Common.sqlChar(rowData[1]) + " " )
			  .append("and propertyno=" + Common.sqlChar(rowData[2]) + " ")
			  .append("and serialno=" + Common.sqlChar(rowData[3]) );

			  db.exeSQL(sbSQL.toString());
			  db.doCommit();
		 } catch (Exception e) {
			e.printStackTrace();
		 } finally{
			 db.closeAll();
		 }
		
	  }

			
	
		private List checkPKNoData(String[] data, List listError){
			for(int i=0; i<data.length; i++){
				if(data[i] == ""){
						if(pkCols.containsKey((i))){
							listError.add("第" + (i+1) + "行必填欄位[" + pkCols.get(i) + "]沒有資料！！");
						}
				}
			}
			
			return listError;
		}
		
		private List checkNumData(String[] data, List listError){
			for(int i=0; i<data.length; i++){
				if(!isNumeric(data[i]) || "".equals(data[i])){
						if(numCols.containsKey((i))){
							listError.add("第" + (i+1) + "行[" + numCols.get(i) + "]必須為數字欄位！！");
						}
				}
			}
			
			return listError;
		}
		
		

			
		   //必輸欄位設定
			private void setPKCols(){
				pkCols.put(0,  "*權屬代碼(不能改)");
				pkCols.put(1,  "*財產區分別代碼(不能改)");
				pkCols.put(2,  "*財產編號(不能改)");
				pkCols.put(3,  "*財產分號(不能改)");
//				pkCols.put(4,  "財產名稱");
//				pkCols.put(5,  "財產別名");
//				pkCols.put(6,  "使用年限");
				pkCols.put(7,  "*購置日期");
//				pkCols.put(8,  "財產來源－種類");
//				pkCols.put(9,  "財產來源－取得日期");
//				pkCols.put(10,  "財產來源－取得文號");
//				pkCols.put(11,  "增加原因");
//				pkCols.put(12,  "增加原因－其他說明");
//				pkCols.put(13,  "抵繳遺產稅");
//				pkCols.put(14,  "原始入帳－摘要");
//				pkCols.put(15,  "原始入帳－會計科目");
//				pkCols.put(16,  "經費來源");
//				pkCols.put(17,  "經費來源－其他說明");
//				pkCols.put(18,  "建築日期");
//				pkCols.put(19,  "資產重估日期");
//				pkCols.put(20,  "代管資產－原始總價");
//				pkCols.put(21,  "代管資產－代管日前累計折舊");
//				pkCols.put(22,  "原財產編號");
//				pkCols.put(23,  "原財產分號");
//				pkCols.put(24,  "備註");
			}
			
			   //數字欄位設定
			private void setNumCols(){
//				numCols.put(0,  "*權屬代碼(不能改)");
//				numCols.put(1,  "*財產區分別代碼(不能改)");
//				numCols.put(2,  "*財產編號(不能改)");
//				numCols.put(3,  "*財產分號(不能改)");
//				numCols.put(4,  "財產名稱");
//				numCols.put(5,  "財產別名");
				numCols.put(6,  "使用年限");
//				numCols.put(7,  "*購置日期");
//				numCols.put(8,  "財產來源－種類");
//				numCols.put(9,  "財產來源－取得日期");
//				numCols.put(10,  "財產來源－取得文號");
//				numCols.put(11,  "增加原因");
//				numCols.put(12,  "增加原因－其他說明");
//				numCols.put(13,  "抵繳遺產稅");
//				numCols.put(14,  "原始入帳－摘要");
//				numCols.put(15,  "原始入帳－會計科目");
//				numCols.put(16,  "經費來源");
//				numCols.put(17,  "經費來源－其他說明");
//				numCols.put(18,  "建築日期");
//				numCols.put(19,  "資產重估日期");
				numCols.put(20,  "代管資產－原始總價");
				numCols.put(21,  "代管資產－代管日前累計折舊");
//				numCols.put(22,  "原財產編號");
//				numCols.put(23,  "原財產分號");
//				numCols.put(24,  "備註");
			}
			
			public boolean isNumeric(String str){
				
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if( !isNum.matches() ){
				return false;
			}
			
			return true;
			}
				
				
	public static void main(String[] args){

	}
}