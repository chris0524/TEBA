/*
*<br>程式目的：財產異動統計表 
*<br>程式代號：untgr017r
*<br>撰寫日期：95/05/17
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

public class UNTGR017R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_reportYM;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_verify;
	String q_enterDateS;
	String q_enterDateE;
	String q_isorganmanager;
	String isorganmanagerT;
	
	public String getIsorganmanagerT(){ return checkGet(isorganmanagerT); }
	public void setIsorganmanagerT(String s){ isorganmanagerT=checkSet(s); }	
	public String getQ_isorganmanager(){ return checkGet(q_isorganmanager); }
	public void setQ_isorganmanager(String s){ q_isorganmanager=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_reportYM(){ return checkGet(q_reportYM); }
	public void setQ_reportYM(String s){ q_reportYM=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }

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

//	查詢條件
	protected String querySQL(){
		String querySQL="";
			if (!"".equals(getQ_propertyKind())){
				querySQL+=" "+getQ_propertyKind();
			}else{
				querySQL+=" 99";
			}
			if (!"".equals(getQ_fundType())){
				querySQL+=" "+getQ_fundType();
			}else{
				querySQL+=" 99";
			}
			if (!"".equals(getQ_valuable())){
				querySQL+=" "+getQ_valuable();
			}else{
				querySQL+=" 99";
			}
		return querySQL;
	}

//報表產製
public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String querySQL = querySQL();
    //欄
    int column = 6 ;
    //列
    int row = 9 ;
    try{
    	//執行SQL File檔案
    	Common.callSQLFile(getFileName()+"\\untgr005r.sql "+getEditID()+" "+getQ_enterOrg()+" "+getQ_enterDateS()+" "+getQ_enterDateE()+querySQL+" "+getQ_ownership()+" UNTGR_untgr017r"+" "+getIsorganmanagerT());
    	/* iReport 中column的名稱----COLUMNS_1_1,COLUMNS_2_1,COLUMNS_3_1.......,COLUMNS_6_1
    	   COLUMNS_1_2,COLUMNS_2_2,COLUMNS_3_2.......,COLUMNS_6_2............,COLUMNS_6_9 */
    	String[] columns = new String[(column*row)+4];
		int k=0;
    	for(int j=1; j<(row+1); j++){
	    	for(int i=1; i<(column+1); i++){
	    		columns[k] = "COLUMNS_"+i+"_"+j;
		    	k++;
	    	}
    	}
    	columns[(column*row)] = "PROPERTYKINDFUNDTYPENAME";
    	columns[(column*row)+1] = "ENTERORGNAME";
    	columns[(column*row)+2] = "ENTERDATESE";
    	columns[(column*row)+3] = "PROPERTYKINDFUNDTYPENO";
    	//==========================================
    	String[] SQLName = new String[] {"addAmount","addBookValue","reduceAmount","reduceBookValue","nowAmount","nowBookValue","propertyKindFundTypeName","propertyKindFundTypeNo"};
    	//抓取存在於UNTGR_untgr017r中的資料
    	String execSQL= Common.callSQLFileRead(getFileName()+"\\untgr005r_1.sql",2,getEditID()+":;:UNTGR_untgr017r");
    	//==========================================
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	int dataNo = 0;
    	Object[] data = new Object[(column*row)+4];
    	while(rs.next()){
        	if(dataNo==(column*(row-1))){
        		for(int l=0; l<(column); l++){
    	    		data[dataNo] = rs.getString(SQLName[l]);
    	        	dataNo++;
        		}
        		data[(column*row)] = rs.getString(SQLName[column]);
        		data[(column*row)+1] = Common.getORGANANAME(getQ_enterOrg());
        		data[(column*row)+2] = "中華民國"+Common.MaskCDate(getQ_enterDateS())+"至"+Common.MaskCDate(getQ_enterDateE());
        		data[(column*row)+3] = rs.getString(SQLName[(column+1)]);
        		rowData.addElement(data);
        		data = new Object[(column*row)+4];
    			dataNo = 0;
        	}else if(dataNo<(column*(row-1))){
        		for(int l=0; l<(column); l++){
    	    		data[dataNo] = rs.getString(SQLName[l]);
    	        	dataNo++;
    	    	}
        	}
       	}
        Object[][] data1 = new Object[0][0];
        data1 = (Object[][])rowData.toArray(data1);
        model.setDataVector(data1, columns);
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}

}
