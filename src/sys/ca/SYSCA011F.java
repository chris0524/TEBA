/*
*<br>程式目的：代碼管理作業
*<br>程式代號：SYSCA011F
*<br>撰寫日期：98/09/08
*<br>程式作者：Timtoy.Tsai
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*<br>
*/
package sys.ca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import util.*;

public class SYSCA011F extends SuperBean{

	String oldOrganID;
	public String getOldOrganID(){ return checkGet(oldOrganID); }
	public void setOldOrganID(String s){ oldOrganID=checkSet(s); }
	
	String oldOrganIDName;
	public String getOldOrganIDName(){ return checkGet(oldOrganIDName); }
	public void setOldOrganIDName(String s){ oldOrganIDName=checkSet(s); }
	
	String newOrganID;
	public String getNewOrganID(){ return checkGet(newOrganID); }
	public void setNewOrganID(String s){ newOrganID=checkSet(s); }
	
	String newOrganIDName;
	public String getNewOrganIDName(){ return checkGet(newOrganIDName); }
	public void setNewOrganIDName(String s){ newOrganIDName=checkSet(s); }

	
	// where 條件值之參數為  enterorg 的 Tables（含非公用財產：UNTLA_Person、NPBGR_Change、NPBSA_Property、NPBIN_Property）
	String[] dbTables_enterorg = {
		"UNTLA_AddProof", "UNTLA_Land", "UNTLA_Manage", "UNTLA_Attachment", "UNTLA_Value", 
		"UNTLA_Price", "UNTLA_ViewScene", "UNTLA_Tax", "UNTLA_Right", "UNTLA_Use", 
		"UNTLA_DrawProof", "UNTLA_ReduceProof", "UNTLA_ReduceDetail", "UNTLA_AdjustProof", "UNTLA_AdjustDetail", 
		"UNTLA_MergeDivision", "UNTLA_MergeDivision1", "UNTLA_UseSeparate", "UNTBU_AddProof", "UNTBU_Building", 
		"UNTBU_Manage", "UNTBU_Floor", "UNTBU_Attachment1", "UNTBU_Base", "UNTBU_CommonUse", 
		"UNTBU_Price", "UNTBU_Attachment2", "UNTBU_ViewScene", "UNTBU_Tax", "UNTBU_DrawProof", 
		"UNTBU_ReduceProof", "UNTBU_ReduceDetail", "UNTBU_AdjustProof", "UNTBU_AdjustDetail", "UNTRF_AddProof", 
		"UNTRF_Attachment", "UNTRF_Base", "UNTRF_Manage", "UNTRF_Attachment2", "UNTRF_ViewScene", 
		"UNTRF_ReduceProof", "UNTRF_ReduceDetail", "UNTRF_AdjustProof", "UNTRF_AdjustDetail", "UNTMP_BUREAU", 
		"UNTMP_KeepUnit", "UNTMP_Keeper", "UNTMP_Store", 
//		"UNTMP_DOC", 
		"UNTMP_AddProof", 
		"UNTMP_Movable", "UNTMP_MovableDetail", "UNTMP_Attachment1", "UNTMP_Attachment2", "UNTMP_MoveProof", 
		"UNTMP_MoveDetail", "UNTMP_ReduceProof", "UNTMP_ReduceDetail", "UNTMP_DealProof", "UNTMP_DealDetail", 
		"UNTMP_AdjustProof", "UNTMP_AdjustDetail", "UNTVP_AddProof", "UNTVP_Share", "UNTVP_Capital", 
		"UNTVP_AdjustProof", "UNTVP_AdjustDetail", "UNTRT_AddProof", "UNTRT_AddDetail", "UNTRT_DrawProof", 
		"UNTRT_ReduceProof", "UNTRT_ReduceDetail", "UNTRT_AdjustProof", "UNTRT_AdjustDetail", "UNTNE_AddProof", 
		"UNTNE_Nonexp", "UNTNE_NonexpDetail", "UNTNE_Attachment1", "UNTNE_Attachment2", "UNTNE_MoveProof", 
		"UNTNE_MoveDetail", "UNTNE_ReduceProof", "UNTNE_ReduceDetail", "UNTNE_DealProof", "UNTNE_DealDetail", 
		"UNTNE_AdjustProof", "UNTNE_AdjustDetail", "UNTSO_PCSoftware", "UNTSO_SoftCare", "UNTGR_Closing", 
		"UNTGR_Closing1", "UNTGR_Plan", "PUBGR_CancelClosing", "PUBGR_CancelClosing1", "PUBGR_pubgr002r_Sort", 
		"SYSCA_FundOrgan", "SYSPK_PropertyCode", "SYSPK_PropertyCode2", "UNTLA_Person", "NPBGR_Change",
		"NPBSA_Property", "NPBIN_Property"
	};
	// where 條件值之參數為  manageOrg 的 Tables
	String[] dbTables_manageOrg = {
		"UNTLA_Land", "UNTBU_Base", "UNTRF_Base", "UNTBU_Building", "UNTLA_Attachment", 
		"PUBGR_ReportMaster", "PUBGR_ReportDetail"
	};
	// where 條件值之參數為  shiftOrg 的 Tables
	String[] dbTables_shiftOrg = {
		"UNTNE_DealProof", "UNTMP_ReduceDetail", "UNTNE_ReduceDetail", "UNTMP_DealProof"
	};
	// where 條件值之參數為  useUnit 的 Tables
	String[] dbTables_useUnit = {
		"UNTLA_Manage", "UNTBU_Manage", "UNTRF_Attachment", "UNTRF_Manage"
	};
	// where 條件值之參數為  newEnterOrg 的 Tables
	String[] dbTables_newEnterOrg = {
		"UNTRF_ReduceDetail", "UNTNE_ReduceDetail", "UNTMP_ReduceDetail", "UNTLA_ReduceDetail", "UNTBU_ReduceDetail"
	};
	
	/* 程式執行進入點 */
	public void InsertSQL(){
		execIUDSQL(getIUDSQLs(newOrganID, oldOrganID));
	}
	
	//---> 取得 Insert、Update、Delete 之 SQLs
	public String[][] getIUDSQLs(String newOrganID, String oldOrganID){
		
		String[][] execSQLArray = new String[12][];
		execSQLArray[0] = new String[1];
		execSQLArray[1] = new String[dbTables_enterorg.length];
		execSQLArray[2] = new String[dbTables_manageOrg.length];
		execSQLArray[3] = new String[dbTables_shiftOrg.length];
		execSQLArray[4] = new String[dbTables_useUnit.length];
		execSQLArray[5] = new String[dbTables_newEnterOrg.length];
		execSQLArray[6] = new String[2];
		execSQLArray[7] = new String[1];
		execSQLArray[8] = new String[1];
		execSQLArray[9] = new String[1];
		execSQLArray[10] = new String[1];
		execSQLArray[11] = new String[1];
		
		execSQLArray[0][0]="insert into SYSCA_ChangeOrg( " +
			" oldOrganID, "+
			" oldOrganName, "+
			" newOrganID, "+
			" newOrganName, "+
			" editID, "+
			" editDate, "+
			" editTime " +
		" )Values(" +
			Common.sqlChar(oldOrganID) + "," +
			Common.sqlChar(oldOrganIDName) + "," +
			Common.sqlChar(newOrganID) + "," +
			Common.sqlChar(newOrganIDName) + "," +
			Common.sqlChar(getUserID()) + "," +
			Common.sqlChar(Datetime.getYYYMMDD()) + "," +
			Common.sqlChar(Datetime.getHHMMSS()) + ")" ;
		
		for(int y = 0 ; y < dbTables_enterorg.length ; y++){
			execSQLArray[1][y] = " update "+ dbTables_enterorg[y] +" set enterOrg = '"+newOrganID+"' where enterorg = '"+oldOrganID+"'" ;
	  	}
		
		for(int y = 0 ; y < dbTables_manageOrg.length ; y++){
			execSQLArray[2][y] = " update "+ dbTables_manageOrg[y] +" set manageOrg = '"+newOrganID+"' where manageOrg = '"+oldOrganID+"'" ;
	  	}
		
		for(int y = 0 ; y < dbTables_shiftOrg.length ; y++){
			execSQLArray[3][y] = " update "+ dbTables_shiftOrg[y] +" set shiftOrg = '"+newOrganID+"' where shiftOrg = '"+oldOrganID+"'" ;
	  	}
		
		for(int y = 0 ; y < dbTables_useUnit.length ; y++){
			execSQLArray[4][y] = " update "+ dbTables_useUnit[y] +" set useUnit = '"+newOrganID+"' where useUnit = '"+oldOrganID+"'" ;
	  	}
		
		for(int y = 0 ; y < dbTables_newEnterOrg.length ; y++){
			execSQLArray[5][y] = " update "+ dbTables_newEnterOrg[y] +" set newEnterOrg = '"+newOrganID+"' where newEnterOrg = '"+oldOrganID+"'" ;
	  	}
		
		execSQLArray[6][0] = " update UNTVP_AddProof     set securityOrg    = '"+newOrganID+"' where securityOrg   = '"+oldOrganID+"'";
		execSQLArray[6][1] = " update UNTVP_AdjustProof  set securityOrg    = '"+newOrganID+"' where securityOrg   = '"+oldOrganID+"'";
		execSQLArray[7][0] = " update SYSOT_Organ_Cvt    set organID        = '"+newOrganID+"' where organID       = '"+oldOrganID+"'";
		execSQLArray[8][0] = " update SYSPK_Apply        set applyOrgNo     = '"+newOrganID+"' where applyOrgNo    = '"+oldOrganID+"'";
		execSQLArray[9][0] = " update SYSCA_Organ        set organSuperior  = '"+newOrganID+"' where organSuperior = '"+oldOrganID+"'";
		
		// 更新舊機關使用者帳號
		execSQLArray[10][0] =" update sysap_emp          set organid        = '"+newOrganID+"' where organid       = '"+oldOrganID+"'";
		// 刪除舊機關代碼資料
		execSQLArray[11][0] =" delete SYSCA_Organ where organID = '"+oldOrganID+"'";
		
		return execSQLArray;
	}
	
	//---> 執行 Insert、Update、Delete 之 SQLs
  	public void execIUDSQL(String[][] insertSQLArray){
  		
		boolean YorN = execCheckSQL(getCheckSQLs(newOrganID));
		
			if (YorN){
				try {
					this.excuteSQL(insertSQLArray);
					setErrorMsg("新增完成");
					setStateInsertSuccess();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				setErrorMsg("新機關存在財產物品資料，無法進行變更！");
				setStateInsertError();
			}
	}
  	
    //---> 取得 Check SQLs
  	public String[][] getCheckSQLs(String newOrganID){
  		
  		String[][] dbTables_SQLs = new String[6][];
  		dbTables_SQLs[0] = new String[dbTables_enterorg.length];
  		dbTables_SQLs[1] = new String[2];
  		dbTables_SQLs[2] = new String[1];
  		dbTables_SQLs[3] = new String[1];
  		dbTables_SQLs[4] = new String[1];
  		dbTables_SQLs[5] = new String[1];
  		
	  	for(int y = 0 ; y < dbTables_enterorg.length ; y++){
	  		dbTables_SQLs[0][y] = " select count(*) from "+ dbTables_enterorg[y] +" where enterorg = '"+newOrganID+"'" ;
	  		//System.out.println("dbTables_SQLs[0]["+y+"] =" + " select count(*) from "+ dbTables_1[y] +" where enterorg = '"+ newOrganID +"'" );
	  	}

	  	dbTables_SQLs[1][0] = " select count(*) from PUBGR_ReportMaster where manageOrg     = '"+newOrganID+"'" ;
	  	dbTables_SQLs[1][1] = " select count(*) from PUBGR_ReportDetail where manageOrg     = '"+newOrganID+"'" ;
	  	dbTables_SQLs[2][0] = " select count(*) from UNTRF_Attachment   where useUnit       = '"+newOrganID+"'" ;
	  	dbTables_SQLs[3][0] = " select count(*) from SYSCA_Organ        where organSuperior = '"+newOrganID+"'" ;
	  	dbTables_SQLs[4][0] = " select count(*) from SYSPK_Apply        where applyOrgNo    = '"+newOrganID+"'" ;
	  	dbTables_SQLs[5][0] = " select count(*) from SYSOT_Organ_Cvt    where organID       = '"+newOrganID+"'" ;	  		
	  	
  		return dbTables_SQLs;
  	}
  	
  	//---> 執行 Check SQLs
  	public boolean execCheckSQL(String[][] checkSQLArray){
  		boolean rtnBoolean=true;
  		int checkResult = 0;
  		Database db = new Database();
		ResultSet rs;	
		try {
			for(int i = 0; i < checkSQLArray.length; i++){
				for(int j = 0; j < checkSQLArray[i].length; j++ ){
					rs = db.querySQL(checkSQLArray[i][j]);
					//System.out.println(checkSQLArray[i][j]);
					if(rs.next()){
						 checkResult = Integer.parseInt(rs.getString("count(*)"));
						 if(checkResult != 0){
							 rtnBoolean = false;
							 break;
						 }
					}
				}
			}
			//System.out.println("是否符合變更條件：" + rtnBoolean);
		} catch (Exception e) {
			System.out.println("== execCheckSQL Exception == ");
			e.printStackTrace();
		} finally{
			db.closeAll();
		}
		return rtnBoolean;
  	}
  	
  	//---> 覆寫 SuperBean 的 excuteSQL
  	public void excuteSQL(String[][] sql) throws Exception{
  		Database db = new Database();
  		Connection conn = null;
  		Statement stmt = null;
  		int i =0 ;
		try {
			if (conn==null) conn = db.getConnection();
			conn.setAutoCommit(false);
			stmt = db.getForwardStatement();
			for(i=0; i<sql.length; i++){
				for(int j=0; j<sql[i].length; j++){
				    if(!"".equals(sql[i][j].toString()))
				        stmt.executeUpdate(sql[i][j]);
				    	//System.out.println(sql[i][j]);
				}
			}
			stmt.close();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			System.out.println("excuteSQL Exception = "+sql[i]);
			throw new Exception(e);			
		} finally {
			db.closeAll();
		}
	}	
}

