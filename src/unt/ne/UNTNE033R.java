/*
*<br>程式目的：非消耗品基本資料挑檔作業
*<br>程式代號：UNTNE033R
*<br>程式日期：0941213
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.*;

public class UNTNE033R extends UNTNE001Q{

String[] sSourceField;
String[] sDestField;
String enterOrg;
String enterOrgName;
String ownership;
String propertyNo;

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }

String strKeySet[] = null;
String isOrganManager;
String isAdminManager;
String organID;
String exportPath;

public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getIsOrganManager() { return checkGet(isOrganManager); }
public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
public String getIsAdminManager() { return checkGet(isAdminManager); }
public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
public String getExportPath() { return checkGet(exportPath); }
public void setExportPath(String s) { exportPath=checkSet(s); }

public String[] getsKeySet(){ return strKeySet; }
public void setsKeySet(String[] s){ strKeySet=s; }
public String[] getSSourceField(){ return sSourceField; }
public void setSSourceField(String[] s){ sSourceField=s; }
public String[] getSDestField(){ return sDestField; }
public void setsDestField(String[] s){ sDestField=s; }

String q_bookValueS;
String q_bookValueE;
String q_fundsSource;
String q_sourceKind;

public String getQ_bookValueS(){ return checkGet(q_bookValueS); }
public void setQ_bookValueS(String s){ q_bookValueS=checkSet(s); }
public String getQ_bookValueE(){ return checkGet(q_bookValueE); }
public void setQ_bookValueE(String s){ q_bookValueE=checkSet(s); }
public String getQ_fundsSource(){ return checkGet(q_fundsSource); }
public void setQ_fundsSource(String s){ q_fundsSource=checkSet(s); }
public String getQ_sourceKind(){ return checkGet(q_sourceKind); }
public void setQ_sourceKind(String s){ q_sourceKind=checkSet(s); }

String excelFileName;
public String getExcelFileName(){ return checkGet(excelFileName); }
public void setExcelFileName(String s){ excelFileName=checkSet(s); }

String q_place;
public String getQ_place(){ return checkGet(q_place); }
public void setQ_place(String s){ q_place=checkSet(s); }

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{	
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, "+
			" a.enterOrgName, a.ownershipName, a.propertyName " +
			" from QRY_UNTNE033R a where 1=1 " ;
			
		if (!"".equals(getQ_enterOrg())) {
			sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(getQ_ownership()))
			sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
		if (!"".equals(getQ_propertyNoS()))
			sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
		if (!"".equals(getQ_serialNoS()))
			sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
			sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getQ_bookValueS()))
			sql+=" and a.bookValue >= " + getQ_bookValueS();		
		if (!"".equals(getQ_bookValueE()))
			sql+=" and a.bookValue <=" + getQ_bookValueE();			
		if (!"".equals(getQ_keepUnit()))
			sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
		if (!"".equals(getQ_keeper()))
			sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
		if (!"".equals(getQ_useUnit()))
			sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
		if (!"".equals(getQ_userNo()))
			sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;	    
		if (!"".equals(getQ_enterDateS()))
			sql+=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			sql+=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_propertyKind()))
			sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_fundsSource()))
			sql+=" and a.fundsSource = " + Common.sqlChar(getQ_fundsSource()) ;	    
		if (!"".equals(getQ_sourceKind()))
			sql+=" and a.sourceKind = " + Common.sqlChar(getQ_sourceKind()) ;	    
		if (!"".equals(getQ_place()))
			sql+=" and a.place like '%" + getQ_place() + "%'";	    
		
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[8];
			rowArray[0]=rs.getString("enterOrgName"); 
			rowArray[1]=rs.getString("ownershipName"); 
			rowArray[2]=rs.getString("propertyNo")+"-"+rs.getString("serialNo"); 
			rowArray[3]=rs.getString("propertyName"); 
			rowArray[4]=rs.getString("enterOrg"); 
			rowArray[5]=rs.getString("ownership"); 
			rowArray[6]=rs.getString("propertyNo");
			rowArray[7]=rs.getString("serialNo");
			objList.add(rowArray);
			if (counter==getListLimit()){
				setErrorMsg("查詢出來的資料過多,所以僅顯示50筆");
				break;
			}		
		}
		setStateQueryAllSuccess();		
		if (counter<=0) {								
			super.setState("init");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

public String getFieldList(String mgrOrg, String userOrg) throws Exception {
	String sStr = "";
	Database db = new Database();
	ResultSet rs = null;
	String sSQL = "";
	try {
		sSQL = "select engFieldName, zhFieldName from fieldMapping where tableName='QRY_UNTNE033R' ";
		if (!checkGet(mgrOrg).equalsIgnoreCase(checkGet(userOrg))){
			sSQL += " and isMgr='N'";
		}
		sSQL += " order by orderby ";		
		rs = db.querySQL(sSQL);
		while (rs.next()) {
			sStr += "<option value='" + rs.getString("engFieldName") + ":;:" + rs.getString("zhFieldName") + "'>" + rs.getString("zhFieldName") + "</option>";
		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sStr;
}

public void exportAll() throws Exception{	
	Database db = new Database();
	ResultSet rs = null; 
	String strFields = "", strContext="";
	String strZhFields="", strEngFields="";
	String[] arrContext = null, arrField=null;
	int i = 0, j=0;	
	try {
		//Get Selected Fields and Field Names
		for (i=0; i<sDestField.length; i++) {
			arrField = sDestField[i].split(":;:");			
			if (i==0) {
				strFields += arrField[0];
				strEngFields += Common.sqlChar(arrField[0]);
				strZhFields += arrField[1];
				strContext += arrField[1];
			} else {
				strFields += "," + arrField[0];
				strEngFields += "," + Common.sqlChar(arrField[0]);
				strZhFields += "," + arrField[1];
				strContext += "," + arrField[1];
			} 
			if (i==sDestField.length-1){
				strContext += ";;";
			}			
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("Sheet1");	
		HSSFRow row;
		HSSFCell cell;
		//Print Excel Column 
		arrContext = strContext.split(";;");
		for (i=0; i<arrContext.length;i++) {
			row = sheet1.createRow((short)i);				
			String[] arrValue = arrContext[i].split(",");
			for (j=0; j<arrValue.length; j++) {
				cell = row.createCell((short)j);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
				cell.setCellValue(arrValue[j]);			
			}
		}		
		
		
		if (!"".equals(strEngFields)) {
			//Retrive Data
			String sql = "select distinct  " + strFields + " from QRY_UNTNE033R a where 1=1 ";	
			if (!"".equals(getQ_enterOrg())) {
				sql+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
					} else {
						sql+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
			if (!"".equals(getQ_ownership()))
				sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;
			if (!"".equals(getQ_propertyNoS()))
				sql+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
			if (!"".equals(getQ_propertyNoE()))
				sql+=" and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
			if (!"".equals(getQ_serialNoS()))
				sql+=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
			if (!"".equals(getQ_serialNoE()))
				sql+=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
			if (!"".equals(getQ_bookValueS()))
				sql+=" and a.bookValue >= " + getQ_bookValueS();		
			if (!"".equals(getQ_bookValueE()))
				sql+=" and a.bookValue <=" + getQ_bookValueE();			
			if (!"".equals(getQ_keepUnit()))
				sql+=" and a.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
			if (!"".equals(getQ_keeper()))
				sql+=" and a.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
			if (!"".equals(getQ_useUnit()))
				sql+=" and a.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
			if (!"".equals(getQ_userNo()))
				sql+=" and a.userNo = " + Common.sqlChar(getQ_userNo()) ;	    
			if (!"".equals(getQ_enterDateS()))
				sql+=" and a.enterDate >= " + Common.sqlChar(getQ_enterDateS());
			if (!"".equals(getQ_enterDateE()))
				sql+=" and a.enterDate <= " + Common.sqlChar(getQ_enterDateE());
			if (!"".equals(getQ_propertyKind()))
				sql+=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
			if (!"".equals(getQ_fundType()))
				sql+=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
			if (!"".equals(getQ_valuable()))
				sql+=" and a.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
			if (!"".equals(getQ_fundsSource()))
				sql+=" and a.fundsSource = " + Common.sqlChar(getQ_fundsSource()) ;	    
			if (!"".equals(getQ_sourceKind()))
				sql+=" and a.sourceKind = " + Common.sqlChar(getQ_sourceKind()) ;	  
			if (!"".equals(getQ_place()))
				sql+=" and a.place like '%" + getQ_place() + "%'";	    
			rs = db.querySQL(sql);

			while (rs.next()){
				row = sheet1.createRow((short)i);				
				for (j=0; j<sDestField.length; j++) {
					cell = row.createCell((short)j);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);//已升級到poi_3.1版本後，此行可以註解起來_103.09.11
					cell.setCellValue(rs.getString(j+1));			
				}			
				i++;			
			}
	
	        String uploadCaseID = new java.rmi.dgc.VMID().toString();
	        uploadCaseID = uploadCaseID.replaceAll(":","_");		
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			tempDirectory = new File(tempDirectory,uploadCaseID);
			tempDirectory.mkdirs();

			FileOutputStream fout = new FileOutputStream(tempDirectory.getAbsoluteFile()+File.separator+"untne033r.xls");			
			wb.write(fout);
			fout.flush();
			fout.close();
			this.setExcelFileName(tempDirectory.getAbsoluteFile()+File.separator+"untne033r.xls");
			
			super.setState("exportAll");			
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}
