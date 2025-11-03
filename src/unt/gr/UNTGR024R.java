/*
*<br>程式目的：固定資產月折舊明細表 
*<br>程式代號：untgr024r
*<br>撰寫日期：95/07/11
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTGR024R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_valuable;
	String q_propertyKind;
	String q_verify;
	String q_fundType;
	String q_propertyType;
	String q_reportYM;

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_reportYM(){ return checkGet(q_reportYM); }
	public void setQ_reportYM(String s){ q_reportYM=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }

	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

	String fileName;

	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }

//查詢條件
protected String[] querySQL(){
	String querySQL[]= new String[2];
	//財產性質,基金財產
		if (!"".equals(getQ_propertyKind())){
			querySQL[0]=" "+getQ_propertyKind();
		}else{
			querySQL[0]=" 99";
		}
		if (!"".equals(getQ_fundType())){
			querySQL[0]+=" "+getQ_fundType();
		}else{
			querySQL[0]+=" 99";
		}
	//珍貴財產
		querySQL[0]+=" 99";
	//財產類別
		if (!"".equals(getQ_propertyType())){
			querySQL[1]=" "+getQ_propertyType();
		}else{
			querySQL[1]=" 99";
		}
return querySQL;
}	

/**
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:財產類別propertyType(1:房屋建築及設備,2:土地改良物,3:機械及設備,4:交通及運輸設備,5:什項設備,傳入99表示未選擇)
-- 傳入值：&4:統計年月reportYM
-- 傳入值：&5:權屬ownership(1:市有,2:國有)
-- 傳入值：&6:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&7:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&8:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&9:報表檔(UNTGR_untgr023r 財產折舊月報表      )
-- 傳入值：&9:報表檔(UNTGR_untgr024r 固定資產月折舊明細表)
**/
public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String querySQL[] = querySQL();
    String todayDate = Common.MaskDate(Datetime.getYYYMMDD());
    String enterOrg = Common.getORGANANAME(getQ_enterOrg());
    //欄
    int column = 8 ;
    try{
    	//執行SQL File檔案
    	Common.callSQLFile(getFileName()+"\\untgr023r.sql "+getEditID()+" "+getQ_enterOrg()+querySQL[1]+" "+getQ_reportYM()+" "+getQ_ownership()+querySQL[0]+" UNTGR_untgr024r");
    	//System.out.println(getFileName()+"\\untgr023r.sql "+getEditID()+" "+getQ_enterOrg()+querySQL[1]+" "+getQ_reportYM()+" "+getQ_ownership()+querySQL[0]+" UNTGR_untgr024r");
    	// iReport 中column的名稱----COLUMNS_1,COLUMNS_2,COLUMNS_3.......,COLUMNS_11
    	String[] columns = new String[column+7];
		int k=0;
    	for(int i=1; i<(column+1); i++){
    		columns[k] = "COLUMNS_"+i;
	    	k++;
    	}
    	columns[column] = "ENTERORGNAME";
    	columns[(column)+1] = "REPORTYM";
    	columns[(column)+2] = "OWNERSHIPNAME";
    	columns[(column)+3] = "FUNDTYPENAME";
    	columns[(column)+4] = "PROPERTYTYPENAME";
    	columns[(column)+5] = "DATE";
    	columns[(column)+6] = "PROPERTYTYPEFUNDTYPENO";
    	//==========================================
    	//抓取存在於UNTGR_untgr024r中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr023r_1.sql",2,getEditID()+":;:UNTGR_untgr024r");
    	//System.out.println("execSQL:"+execSQL);
    	//==========================================
    	String[] SQLName = new String[] {"propertyNo1","propertyName","bookValue","nonAccumDepr1","monthDepr","accumDepr","nonAccumDepr","unitName","ownershipName","propertyKindfundTypeName","propertyTypeName","propertyKindFundTypeNo"};
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	while(rs.next()){
        	Object[] data = new Object[column+7];
        	for(int i=0; i<column; i++){
        		data[i] = rs.getString(SQLName[i]);
        	}
        	data[column] = enterOrg;
    		data[(column)+1] = "中華民國"+Common.MaskCDate(getQ_reportYM());
    		data[(column)+2] = rs.getString(SQLName[(column)]);
    		data[(column)+3] = rs.getString(SQLName[(column+1)]);
    		data[(column)+4] = rs.getString(SQLName[(column+2)]);
    		data[(column)+5] = todayDate;
    		data[(column)+6] = rs.getString(SQLName[(column+3)]);
    		rowData.addElement(data);
    	}
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}

}
