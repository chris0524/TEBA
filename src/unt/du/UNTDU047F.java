
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


public class UNTDU047F extends SuperBean{

	private String enterOrg_export;
	private String ownership;
	private String differenceKind;
	private String q_signNo1;
	private String q_signNo2;
	private String q_signNo3;
	private String originalDeprPark;
	
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

	public String getQ_signNo1() {
		return q_signNo1;
	}
	public void setQ_signNo1(String qSignNo1) {
		q_signNo1 = qSignNo1;
	}
	public String getQ_signNo2() {
		return q_signNo2;
	}
	public void setQ_signNo2(String qSignNo2) {
		q_signNo2 = qSignNo2;
	}
	public String getQ_signNo3() {
		return q_signNo3;
	}
	public void setQ_signNo3(String qSignNo3) {
		q_signNo3 = qSignNo3;
	}
	public String getOriginalDeprPark() {
		return originalDeprPark;
	}
	public void setOriginalDeprPark(String originalDeprPark) {
		this.originalDeprPark = originalDeprPark;
	}
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
	int totalColumns = 55;
	

	 //excel title 欄位
	public void initTitle(){
    	excelTitle = new String[55];
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
		excelTitle[11] =  "*增加原因";
		excelTitle[12] =  "增加原因－其他說明";
		excelTitle[13] =  "建物標示－縣市代碼";
		excelTitle[14] =  "建物標示－縣市名稱";
		excelTitle[15] =  "建物標示－鄉鎮市區代碼";
		excelTitle[16] =  "建物標示－鄉鎮市區名稱";
		excelTitle[17] =  "建物標示－地段代碼";
		excelTitle[18] =  "建物標示－地段名稱";
		excelTitle[19] =  "建物標示－地號";
		excelTitle[20] =  "門牌－縣市(代碼)";
		excelTitle[21] =  "門牌－鄉鎮市區(代碼)";
		excelTitle[22] =  "門牌－村/里(中文名稱)";
		excelTitle[23] =  "門牌－村/里(代碼)";
		excelTitle[24] =  "門牌－路/街/大道(中文名稱)";
		excelTitle[25] =  "門牌－路/街/大道(代碼)";
		excelTitle[26] =  "門牌－段";
		excelTitle[27] =  "門牌－巷";
		excelTitle[28] =  "門牌－弄";
		excelTitle[29] =  "門牌－號";
		excelTitle[30] =  "門牌－樓";
		excelTitle[31] =  "門牌－之";
		excelTitle[32] =  "建築式樣";
		excelTitle[33] =  "抵繳遺產稅";
		excelTitle[34] =  "原始入帳－摘要";
		excelTitle[35] =  "原始入帳－會計科目";
		excelTitle[36] =  "經費來源";
		excelTitle[37] =  "經費來源－其他說明";
		excelTitle[38] =  "所有權登記日期";
		excelTitle[39] =  "所有權登記原因";
		excelTitle[40] =  "有無權狀";
		excelTitle[41] =  "權狀字號";
		excelTitle[42] =  "權狀資料－其他登記事項";
		excelTitle[43] =  "建築日期";
		excelTitle[44] =  "構造－地上層數";
		excelTitle[45] =  "構造－地下層數";
		excelTitle[46] =  "構造－造";
		excelTitle[47] =  "管理機關代碼";
		excelTitle[48] =  "資產重估日期";
		excelTitle[49] =  "使用執照";
		excelTitle[50] =  "代管資產－原始總價";
		excelTitle[51] =  "代管資產－代管日前累計折舊";
		excelTitle[52] =  "原財產編號";
		excelTitle[53] =  "原財產分號";
		excelTitle[54] =  "備註";
    }  
    
	public void exportAll() throws Exception{	
		Database db = new Database();
		ResultSet rs = null; 
		int r=0;	
		initTitle();
		setExportFileName("UNTDU047F");
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
		String signNo = "";
		String querySql="select " +
						"a.ownership, " +
						"a.differencekind, " +
						"a.propertyno, " + 
						"a.serialno, " + 
						"(select b.propertyname from SYSPK_PROPERTYCODE b where b.propertyno = a.propertyno ) propertyname, " + 
						"a.propertyname1, a.otherlimityear, a.buydate, a.sourcekind, a.sourcedate, a.sourcedoc, a.cause, a.cause1, " +
						"substring (a.signno, 1, 1) signno1, " + 
						"(select c.signname from SYSCA_SIGN c where c.signno = substring (a.signno, 1, 1)+'000000') signname1, " +
						"substring (a.signno, 2, 2) signno2, " + 
						"(select d.signname from SYSCA_SIGN d where d.signno = substring (a.signno, 1, 3)+'0000') signname2, " +
						"substring (a.signno, 4, 4) signno3, " + 
						"(select e.signname from SYSCA_SIGN e where e.signno = substring (a.signno, 1, 7) ) signname3, " +
						"substring (a.signno, 8, 8) signno4, " +
						"a.doorplate1, a.doorplate2, a.doorplatevillage1, a.doorPlatevillage2, a.doorplaterd1, a.doorplaterd2, a.doorplatesec, "+
						"a.doorplateln, a.doorplatealy, a.doorplateno, a.doorplatefloor1, a.doorplatefloor2, a.buildstyle, a.taxcredit, a.originalnote, " +
						"a.accountingtitle, a.fundssource, a.fundssource1, a.ownershipdate, a.ownershipcause, a.nonproof, a.proofdoc, a.ownershipnote, a.builddate, " +
						"a.floor1, a.floor2, a.stuff, a.manageorg, a.appraisedate,  a.uselicense, a.escroworivalue,  " + 
						"a.escroworiaccumdepr, a.oldpropertyno, a.oldserialno, a.notes " + 
						"from UNTBU_BUILDING a " + 
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
		
		if (!"".equals(getQ_signNo1())){
			signNo = getQ_signNo1().substring(0,1);
		}
		if (!"".equals(getQ_signNo2())){
			signNo = getQ_signNo2().substring(0,3);
		}
		if (!"".equals(getQ_signNo3())){
			signNo = getQ_signNo3();
		}
		
		if(!"".equals(signNo)){
			querySql += " and a.signno like " + Common.sqlChar(signNo + "%");
		}		
		
		if (!"".equals(getOriginalDeprPark())){
			querySql+=" and a.originaldeprpark = " + Common.sqlChar(getOriginalDeprPark()) ;
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
				listError = checkSignNo(rowData,listError);
	
				if(listError.size() == 0){
					updateUNTBU_BUILDING(rowData);
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
	  
	  public void updateUNTBU_BUILDING(String[]rowData){
		      Database db = new Database();
		 try {
			  String signno = "";
			  if(!"".equals(rowData[13])){
				  signno = rowData[13]+rowData[15]+rowData[17]+rowData[19];
			  }
			  
		      db.setAutoCommit(false);
			   StringBuffer sbSQL = new StringBuffer();
			   sbSQL.append(" update UNTBU_BUILDING set " )
			  .append("propertyname1="+ Common.sqlChar(rowData[5]) + ", ")
			  .append("otherlimityear="+ Integer.parseInt(rowData[6]) + ", ")
			  .append("buydate="+ Common.sqlChar(rowData[7]) + ", ")
			  .append("sourcekind="+ Common.sqlChar(rowData[8]) + ", ") 
			  .append("sourcedate="+ Common.sqlChar(rowData[9]) + ", ") 
			  .append("sourcedoc="+ Common.sqlChar(rowData[10]) + ", ") 
			  .append("cause="+ Common.sqlChar(rowData[11]) + ", ")
			  .append("cause1="+ Common.sqlChar(rowData[12]) + ", ")
			  .append("signno="+ Common.sqlChar(signno) + ", ")
			  .append("doorplate1="+ Common.sqlChar(rowData[20]) + ", ")
			  .append("doorplate2="+ Common.sqlChar(rowData[21]) + ", ")  
			  .append("doorplatevillage1="+ Common.sqlChar(rowData[22]) + ", ")
			  .append("doorplatevillage2="+ Common.sqlChar(rowData[23]) + ", ") 
			  .append("doorplaterd1="+ Common.sqlChar(rowData[24]) + ", ")
			  .append("doorplaterd2="+ Common.sqlChar(rowData[25]) + ", ")  
			  .append("doorplatesec="+ Common.sqlChar(rowData[26]) + ", ")
			  .append("doorplateln="+ Common.sqlChar(rowData[27]) + ", ") 
			  .append("doorplatealy="+ Common.sqlChar(rowData[28]) + ", ")
			  .append("doorplateno="+ Common.sqlChar(rowData[29]) + ", ")
			  .append("doorplatefloor1="+ Common.sqlChar(rowData[30]) + ", ")
			  .append("doorplatefloor2="+ Common.sqlChar(rowData[31]) + ", ")
			  .append("buildstyle="+ Common.sqlChar(rowData[32]) + ", ")
			  .append("taxcredit="+ Common.sqlChar(rowData[33]) + ", ")
			  .append("originalnote="+ Common.sqlChar(rowData[34]) + ", ")
			  .append("accountingtitle="+ Common.sqlChar(rowData[35]) + ", ")
			  .append("fundssource="+ Common.sqlChar(rowData[36]) + ", ")
			  .append("fundssource1="+ Common.sqlChar(rowData[37]) + ", ")
			  .append("ownershipdate="+ Common.sqlChar(rowData[38]) + ", ")
			  .append("ownershipcause="+ Common.sqlChar(rowData[39]) + ", ")
			  .append("nonproof="+ Common.sqlChar(rowData[40]) + ", ")
			  .append("proofdoc="+ Common.sqlChar(rowData[41]) + ", ")
			  .append("ownershipnote="+ Common.sqlChar(rowData[42]) + ", ")
			  .append("builddate="+ Common.sqlChar(rowData[43]) + ", ")
			  .append("floor1="+ Common.sqlChar(rowData[44]) + ", ")
			  .append("floor2="+ Common.sqlChar(rowData[45]) + ", ")
			  .append("stuff="+ Common.sqlChar(rowData[46]) + ", ")
			  .append("manageorg="+ Common.sqlChar(rowData[47]) + ", ")
			  .append("appraisedate="+ Common.sqlChar(rowData[48]) + ", ")
			  .append("uselicense="+ Common.sqlChar(rowData[49]) + ", ")
			  .append("escroworivalue="+ Integer.parseInt(rowData[50]) + ", ")
			  .append("escroworiaccumdepr="+ Integer.parseInt(rowData[51]) + ", ")
			  .append("oldpropertyno="+ Common.sqlChar(rowData[52]) + ", ")
			  .append("oldserialno="+ Common.sqlChar(rowData[53]) + ", ")
			  .append("notes="+ Common.sqlChar(rowData[54]))
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
		
		
		private List checkSignNo(String[] data, List listError){
			if(data[13] != ""){
				if(data[15] == ""){
					listError.add("第15行[土地標示－鄉鎮市區代碼]必須有值！！");
				}
				if(data[17] == ""){
					listError.add("第17行[土地標示－地段代碼]必須有值！！");
				}
				if(data[19] == ""){
					listError.add("第19行[土地標示－地號]必須有值！！");
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
//				pkCols.put(13,  "建物標示－縣市代碼");
//				pkCols.put(14,  "建物標示－縣市名稱");
//				pkCols.put(15,  "建物標示－鄉鎮市區代碼");
//				pkCols.put(16,  "建物標示－鄉鎮市區名稱");
//				pkCols.put(17,  "建物標示－地段代碼");
//				pkCols.put(18,  "建物標示－地段名稱");
//				pkCols.put(19,  "建物標示－地號");
//				pkCols.put(20,  "門牌－縣市(代碼)");
//				pkCols.put(21,  "門牌－鄉鎮市區(代碼)");
//				pkCols.put(22,  "門牌－村/里(中文名稱)");
//				pkCols.put(23,  "門牌－村/里(代碼)");
//				pkCols.put(24,  "門牌－路/街/大道(中文名稱)");
//				pkCols.put(25,  "門牌－路/街/大道(代碼)");
//				pkCols.put(26,  "門牌－段");
//				pkCols.put(27,  "門牌－巷");
//				pkCols.put(28,  "門牌－弄");
//				pkCols.put(29,  "門牌－號");
//				pkCols.put(30,  "門牌－樓");
//				pkCols.put(31,  "門牌－之");
//				pkCols.put(32,  "建築式樣");
//				pkCols.put(33,  "抵繳遺產稅");
//				pkCols.put(34,  "原始入帳－摘要");
//				pkCols.put(35,  "原始入帳－會計科目");
//				pkCols.put(36,  "經費來源");
//				pkCols.put(37,  "經費來源－其他說明");
//				pkCols.put(38,  "所有權登記日期");
//				pkCols.put(39,  "所有權登記原因");
//				pkCols.put(40,  "有無權狀");
//				pkCols.put(41,  "權狀字號");
//				pkCols.put(42,  "權狀資料－其他登記事項");
//				pkCols.put(43,  "建築日期");
//				pkCols.put(44,  "構造－地上層數");
//				pkCols.put(45,  "構造－地下層數");
//				pkCols.put(46,  "構造－造");
//				pkCols.put(47,  "管理機關代碼");
//				pkCols.put(48,  "資產重估日期");
//				pkCols.put(49,  "使用執照");
//				pkCols.put(50,  "代管資產－原始總價");
//				pkCols.put(51,  "代管資產－代管日前累計折舊");
//				pkCols.put(52,  "原財產編號");
//				pkCols.put(53,  "原財產分號");
//				pkCols.put(54,  "備註");
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
//				numCols.put(11,  "*增加原因");
//				numCols.put(12,  "增加原因－其他說明");
//				numCols.put(13,  "建物標示－縣市代碼");
//				numCols.put(14,  "建物標示－縣市名稱");
//				numCols.put(15,  "建物標示－鄉鎮市區代碼");
//				numCols.put(16,  "建物標示－鄉鎮市區名稱");
//				numCols.put(17,  "建物標示－地段代碼");
//				numCols.put(18,  "建物標示－地段名稱");
//				numCols.put(19,  "建物標示－地號");
//				numCols.put(20,  "門牌－縣市(代碼)");
//				numCols.put(21,  "門牌－鄉鎮市區(代碼)");
//				numCols.put(22,  "門牌－村/里(中文名稱)");
//				numCols.put(23,  "門牌－村/里(代碼)");
//				numCols.put(24,  "門牌－路/街/大道(中文名稱)");
//				numCols.put(25,  "門牌－路/街/大道(代碼)");
//				numCols.put(26,  "門牌－段");
//				numCols.put(27,  "門牌－巷");
//				numCols.put(28,  "門牌－弄");
//				numCols.put(29,  "門牌－號");
//				numCols.put(30,  "門牌－樓");
//				numCols.put(31,  "門牌－之");
//				numCols.put(32,  "建築式樣");
//				numCols.put(33,  "抵繳遺產稅");
//				numCols.put(34,  "原始入帳－摘要");
//				numCols.put(35,  "原始入帳－會計科目");
//				numCols.put(36,  "經費來源");
//				numCols.put(37,  "經費來源－其他說明");
//				numCols.put(38,  "所有權登記日期");
//				numCols.put(39,  "所有權登記原因");
//				numCols.put(40,  "有無權狀");
//				numCols.put(41,  "權狀字號");
//				numCols.put(42,  "權狀資料－其他登記事項");
//				numCols.put(43,  "建築日期");
//				numCols.put(44,  "構造－地上層數");
//				numCols.put(45,  "構造－地下層數");
//				numCols.put(46,  "構造－造");
//				numCols.put(47,  "管理機關代碼");
//				numCols.put(48,  "資產重估日期");
//				numCols.put(49,  "使用執照");
				numCols.put(50,  "代管資產－原始總價");
				numCols.put(51,  "代管資產－代管日前累計折舊");
//				numCols.put(52,  "原財產編號");
//				numCols.put(53,  "原財產分號");
//				numCols.put(54,  "備註");
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