/*
*<br>程式目的：毀損處理清冊 
*<br>程式代號：untgr025r
*<br>撰寫日期：95/7/11
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

public class UNTGR025R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyKind;
	String q_verify;
	String q_fundType;
	String q_propertyType;
	String q_damageExpireS;
	String q_damageExpireE;

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	public String getQ_damageExpireS(){ return checkGet(q_damageExpireS); }
	public void setQ_damageExpireS(String s){ q_damageExpireS=checkSet(s); }
	public String getQ_damageExpireE(){ return checkGet(q_damageExpireE); }
	public void setQ_damageExpireE(String s){ q_damageExpireE=checkSet(s); }

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
protected String querySQL(){
	String querySQL= "";
	//財產性質
		if (!"".equals(getQ_propertyKind())){
			querySQL+=" "+getQ_propertyKind();
		}else{
			querySQL+=" 99";
		}
	//基金財產	
		if (!"".equals(getQ_fundType())){
			querySQL+=" "+getQ_fundType();
		}else{
			querySQL+=" 99";
		}
	//珍貴財產
		querySQL+=" 99";
	//財產類別
		if (!"".equals(getQ_propertyType())){
			querySQL+=" "+getQ_propertyType();
		}else{
			querySQL+=" 99";
		}
return querySQL;
}

/**
-- 傳入值：&1:異動人員editID
-- 傳入值：&2:入帳機關enterOrg
-- 傳入值：&3:毀損報局屆滿日－起damageExpireS
-- 傳入值：&4:毀損報局屆滿日－訖damageExpireE
-- 傳入值：&5:權屬ownership(1:市有,2:國有)
-- 傳入值：&6:財產性質propertyKind(傳入99表示未選擇)
-- 傳入值：&7:基金財產fundType(傳入99表示未選擇)
-- 傳入值：&8:珍貴財產valuable(Y:珍貴,N:非珍貴,傳入99表示未選擇)
-- 傳入值：&9:財產類別propertyType(1:建物,2:土地改良物,3:動產,4:非消耗品,傳入99表示未選擇)
**/

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String querySQL = querySQL();
    String todayDate = Common.MaskDate(Datetime.getYYYMMDD());
    String enterOrg = Common.getORGANANAME(getQ_enterOrg());
    //欄
    int column = 13 ;
    try{
    	//執行SQL File檔案
    	Common.callSQLFile(getFileName()+"\\untgr025r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_damageExpireS()+" "+getQ_damageExpireE()+" "+getQ_ownership()+querySQL);
    	//System.out.println(getFileName()+"\\untgr025r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_damageExpireS()+" "+getQ_damageExpireE()+" "+getQ_ownership()+querySQL);    	
    	// iReport 中column的名稱----COLUMNS_1,COLUMNS_2,COLUMNS_3.......,COLUMNS_12
    	String[] columns = new String[column+6];
		int k=0;
    	for(int i=1; i<(column+1); i++){
    		columns[k] = "COLUMNS_"+i;
	    	k++;
    	}
    	columns[column] = "ENTERORGNAME";
    	columns[(column)+1] = "OWNERSHIPNAME";
    	columns[(column)+2] = "PROPERTYKINDFUNDTYPENAME";
    	columns[(column)+3] = "PROPERTYTYPENAME";
    	columns[(column)+4] = "DATE";
    	columns[(column)+5] = "CHANGEPAGE";
    	//==========================================
    	//抓取存在於UNTGR_untgr025r中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr025r_1.sql",1,getEditID());    	
    	//==========================================
    	String[] SQLName = new String[] {"sourceDate","propertyNo1","propertyName","specification","propertyUnit","bookAmount","bookUnit","bookValue","damageDate","damageExpire","limitYear","useYear","damageMark","ownershipName","propertyKindfundTypeName","propertyTypeName","changePage"};
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	while(rs.next()){
        	Object[] data = new Object[column+6];
        	for(int i=0; i<column; i++){
        		data[i] = rs.getString(SQLName[i]);
        	}
        	data[column] = enterOrg;
    		data[(column)+1] = rs.getString(SQLName[(column)]);
    		data[(column)+2] = rs.getString(SQLName[(column+1)]);
    		data[(column)+3] = rs.getString(SQLName[(column+2)]);
    		data[(column)+4] = todayDate;
    		data[(column)+5] = rs.getString(SQLName[(column+3)]);
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
