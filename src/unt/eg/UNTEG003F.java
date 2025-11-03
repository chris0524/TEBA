package unt.eg;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.Database;

public class UNTEG003F extends UNTEG001Q{

public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	int counter=0;
	try {
		UNTEG002F eg = new UNTEG002F();
		
		eg.verify = "Y";
		eg.setEnterOrg(this.getEnterOrg());
		eg.setEngineeringNo(this.getEngineeringNo());
		
		String sql = "select * from (" +
						eg.getQuerySQL_forUNTLA_LAND() +
						" union " +
						eg.getQuerySQL_forUNTBU_BUILDING() +
						" union " +
						eg.getQuerySQL_forUNTRF_ATTACHMENT() +
						" union " +
						eg.getQuerySQL_forUNTMP_MOVABLE() +
						" union " +
						eg.getQuerySQL_forUNTVP_ADDPROOF() +
						" union " +
						eg.getQuerySQL_forUNTRT_ADDPROOF() +						
						") a";
		
		UNTCH_Tools ut = new UNTCH_Tools();
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			counter++;
			String rowArray[]=new String[12];
			rowArray[0] = checkGet(rs.getString("proofData")); 
			rowArray[1] = checkGet(rs.getString("propertyno")) + "/" + checkGet(rs.getString("serialno")); 
			rowArray[2] = checkGet(ut._getPropertyNoName(rs.getString("propertyno"))) + "/" + checkGet(rs.getString("propertyname1"));
			rowArray[3] = checkGet(rs.getString("amount"));
			rowArray[4] = checkGet(rs.getString("area"));
			rowArray[5] = checkGet(rs.getString("originalbv"));
			rowArray[6] = checkGet(ut._transToROC_Year(checkGet(rs.getString("sourcedate"))));
			rowArray[7] = checkGet(ut._transToROC_Year(checkGet(rs.getString("enterdate"))));
			rowArray[8] = checkGet(rs.getString("buildfeecb"));
			rowArray[9] = checkGet(rs.getString("enterorg"));
			rowArray[10] = checkGet(rs.getString("ownership"));
			rowArray[11] = checkGet(rs.getString("differencekind"));
			
			objList.add(rowArray);
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}
	

	private String enterOrg;
	private String engineeringNo;
	private String engineeringName;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringName() {return checkGet(engineeringName);}
	public void setEngineeringName(String engineeringName) {this.engineeringName = checkSet(engineeringName);}
	
	private String isAdminManager;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
}
