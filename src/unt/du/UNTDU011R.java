/*
*<br>程式目的：保管單位及人員代碼不存在代碼檔資料
*<br>程式代號：untdu011r
*<br>撰寫日期：0960302
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.du;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import util.*;

public class UNTDU011R extends SuperBean{
		
	String q_enterOrg;
	String q_enterOrgName;

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

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

public DefaultTableModel getResultModel() throws Exception{
	String date = Common.MaskCDate(Datetime.getYYYMMDD());
	String oldOriginalKeepUnit = "";
	String oldOriginalKeeper = "";
	String oldKeepUnit = "";
	String oldKeeper = "";
	String oldOriginalUseUnit = "";
	String oldOriginalUser = "";
	String oldUseUnit = "";
	String oldUserNo = "";
	String newOriginalKeepUnit = "";
	String newOriginalKeeper = "";
	String newKeepUnit = "";
	String newKeeper = "";
	String newOriginalUseUnit = "";
	String newOriginalUser = "";
	String newUseUnit = "";
	String newUserNo = "";
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORG","enterOrgName","DATE","ownershipName","propertyNo","propertyName1","buyDate","nameplateSpecification","originalKeepUnit","keepUnit","originalUseUnit","useUnit","place"};
    	//==========================================
    	//抓取存在於UNTDU_untdu011r中的資料
      	String execSQL= Common.callSQLFileRead(getFileName()+"\\untdu011r.sql",1,getQ_enterOrg());
    	//==========================================
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	while(rs.next()){
    		//-----------------------------------------------------
    		newOriginalKeepUnit = rs.getString("originalKeepUnit");
    		newOriginalKeeper = rs.getString("originalKeeper");
    		newKeepUnit = rs.getString("keepUnit");
    		newKeeper = rs.getString("keeper");
    		newOriginalUseUnit = rs.getString("originalUseUnit");
    		newOriginalUser = rs.getString("originalUser");
    		newUseUnit = rs.getString("useUnit");
    		newUserNo = rs.getString("userNo");
    		//-----------------------------------------------------
    		Object[] data = new Object[13];
        	data[0] = rs.getString("enterOrg");
        	data[1] = rs.getString("enterOrgName");
            data[2] = "製表日期："+date;
            data[3] = rs.getString("ownershipName");
            data[4] = rs.getString("propertyNo")+"\n-"+rs.getString("serialNo");
            data[5] = rs.getString("propertyName1");
            data[6] = Common.MaskDate(rs.getString("buyDate"));
            data[7] = rs.getString("nameplateSpecification");
            data[8] = rs.getString("originalKeepUnitName")+"\n"+rs.getString("originalKeeperName");
            data[9] = rs.getString("keepUnitName")+"\n"+rs.getString("keeperName");
            data[10] = rs.getString("originalUseUnitName")+"\n"+rs.getString("originalUserName");
            data[11] = rs.getString("useUnitName")+"\n"+rs.getString("userNoName");
            data[12] = rs.getString("place");
            if(!newOriginalKeepUnit.equals(oldOriginalKeepUnit) || !newOriginalKeeper.equals(oldOriginalKeeper) || !newKeepUnit.equals(oldKeepUnit) || !newKeeper.equals(oldKeeper) || !newOriginalUseUnit.equals(oldOriginalUseUnit) || !newOriginalUser.equals(oldOriginalUser) || !newUseUnit.equals(oldUseUnit) || !newUserNo.equals(oldUserNo)){
                rowData.addElement(data);
            }
            oldOriginalKeepUnit = newOriginalKeepUnit;
            oldOriginalKeeper = newOriginalKeeper;
            oldKeepUnit = newKeepUnit;
            oldKeeper = newKeeper;
            oldOriginalUseUnit = newOriginalUseUnit;
            oldOriginalUser = newOriginalUser;
            oldUseUnit = newUseUnit;
            oldUserNo = newUserNo;
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
