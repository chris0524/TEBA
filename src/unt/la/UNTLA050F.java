/*
*<br>程式目的：土地接收作業
*<br>程式代號：untla050f
*<br>程式日期：2008/05/28
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.*;

public class UNTLA050F extends QueryBean{

String q_enterOrg;
String q_enterOrgName;
String q_ownership;
String q_caseNo;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
public String getQ_caseNo(){ return checkGet(q_caseNo); }
public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }

String enterOrg;
String enterOrgName;
String ownership;
String caseNo;
public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
public String getOwnership(){ return checkGet(ownership); }
public void setOwnership(String s){ ownership=checkSet(s); }
public String getCaseNo(){ return checkGet(caseNo); }
public void setCaseNo(String s){ caseNo=checkSet(s); }

String propertyKind;	//財產性質
String fundType;		//基金財產
public String getPropertyKind(){ return checkGet(propertyKind); }
public void setPropertyKind(String s){ propertyKind=checkSet(s); }
public String getFundType(){ return checkGet(fundType); }
public void setFundType(String s){ fundType=checkSet(s); }

String valuable;
public String getValuable(){ return checkGet(valuable); }
public void setValuable(String s){ valuable=checkSet(s); }

String taxCredit;
public String getTaxCredit(){ return checkGet(taxCredit); }
public void setTaxCredit(String s){ taxCredit=checkSet(s); }

String sourceDate;
String sourceDoc;
public String getSourceDate(){ return checkGet(sourceDate); }
public void setSourceDate(String s){ sourceDate=checkSet(s); }
public String getSourceDoc(){ return checkGet(sourceDoc); }
public void setSourceDoc(String s){ sourceDoc=checkSet(s); }

String manageOrg;
String manageOrgName;
public String getManageOrg(){ return checkGet(manageOrg); }
public void setManageOrg(String s){ manageOrg=checkSet(s); }
public String getManageOrgName(){ return checkGet(manageOrgName); }
public void setManageOrgName(String s){ manageOrgName=checkSet(s); }

String propertyNo;
public String getPropertyNo(){ return checkGet(propertyNo); }
public void setPropertyNo(String s){ propertyNo=checkSet(s); }
String propertyNoName;
public String getPropertyNoName(){ return checkGet(propertyNoName); }
public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }
public void allInsert(){
	Database db = new Database();
	ArrayList objList=new ArrayList();
	String insertSql="";
	String[] execSQLArray;
	String serialnosql = "";

	try {
		String sql = " select b.enterorg ,b.ownership ,b.caseno ,b.propertyno ,b.serialno ,b.newEnterOrg ,b.* " + "\n"
				   + " ,c.originalunit ,c.nonproof ,c.proofdoc ,c.proofverify "
				   + " from untla_reduceproof a ,untla_reducedetail b ,untla_land c " + "\n"
				   + " where 1=1 " + "\n"
				   + " and a.enterorg=b.enterorg " + "\n"
				   + " and a.ownership=b.ownership " + "\n"
				   + " and a.caseno=b.caseno " + "\n"
				   + " and b.enterorg=c.enterorg " + "\n"
				   + " and b.ownership=c.ownership " + "\n"
				   + " and b.propertyno=c.propertyno " + "\n"
				   + " and b.serialno=c.serialno " + "\n"
				   + " and b.cause='201'"
				   + " and b.enterorg = " + Common.sqlChar(getQ_enterOrg())
				   + " and b.ownership = " + Common.sqlChar(getQ_ownership())
				   + " and b.caseno = " + Common.sqlChar(getQ_caseNo())
				   + " and b.newEnterOrg = " + Common.sqlChar(getEnterOrg())
				   ;
		//System.out.println(sql);
		ResultSet rs = db.querySQL(sql);
		while (rs.next()){
			//財產分號累加sql
//			serialnosql = " ( select lpad(to_char(nvl(max(n.serialno),0)+1),7,0) from untla_land n " 
			serialnosql = " ( select max(n.serialno) + 1 from untla_land n "
				   		+ "   where n.enterorg = " + Common.sqlChar(getEnterOrg()) 
				   		+ "   and n.ownership = " + Common.sqlChar(getOwnership())
				   		+ "   and n.propertyno = " + Common.sqlChar(getPropertyNo())
				   		+ " ) ";
			
			Database db1 = new Database();
			String serialNO = "";
			try{
				ResultSet rs1 = db1.querySQL(serialnosql);
				if(rs1.next()){
					if("".equals(checkGet(rs1.getString(1)))){
						serialNO = "0000001";
					}else{
						serialNO = rs1.getString(1);	
					}					
				}else{
					serialNO = "0000001";
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db1.closeAll();
			}
				   
			insertSql += " insert into untla_land( "
					  +  " enterorg, "				//(入帳機關代碼)
					  +  " ownership, "				//(權屬)
					  +  " caseno, "				//(電腦單號)
					  +  " propertyno, "			//(財產編號)
					  +  " serialno, "				// (財產分號)
					  +  " propertyname1, "			//(財產別名)
					  +  " signno, "				//(土地標示)
					  +  " cause, "					//(增加原因)[102:撥入]
					  +  " cause1, "				//(其它說明)
					  +  " datastate, "				//(資料狀態)
					  +  " verify, "				//(入帳)
					  +  " closing, "				//(月結)
					  +  " propertykind, "			//(財產性質)
					  +  " fundtype, "				//(基金財產)
					  +  " valuable, "				//(珍貴財產)
					  +  " taxcredit, "				//(抵繳遺產稅)
					  +  " grass, "					//新草衙
					  +  " originalarea, "			//原始入帳金額
					  +  " originalholdnume, "		//(權利分子)
					  +  " originalholddeno, "		//(權利分母)
					  +  " originalholdarea, "		//(權利範圍面積)
					  +  " originalunit, "			//(原始單價)
					  +  " originalbv, "			//(原始總價)
					  +  " area, "					//(總面積)
					  +  " holdnume, "				//(權利分子)
					  +  " holddeno, "				//(權利分母)
					  +  " holdarea, "				//(權利範圍面積)
					  +  " accountingtitle, "		//(會計科目)
					  +  " bookunit, "				//(單價)
					  +  " bookvalue, "				//(總價)
					  +  " useseparate, "			//(使用分區)
					  +  " usekind, "				//(編定使用種類)
					  +  " manageorg, "				//(管理機關)
					  +  " sourcedate, "			//(財產取得日期)
					  +  " sourcedoc, "				//(財產取得文號) 
					  +  " nonproof, "				//(權狀)
					  +  " proofdoc, "				//(權狀字號)
					  +  " proofverify, "			//(權狀審核)
					  +  " editID, "
					  +  " editDate, "
					  +  " editTime "
					  +  " )values( "
					  +  Common.sqlChar(getEnterOrg()) 					+ ","	//(入帳機關代碼)
					  +  Common.sqlChar(getOwnership()) 				+ ","	//(權屬)
					  +  Common.sqlChar(getCaseNo()) 					+ ","	//(電腦單號)
					  +  Common.sqlChar(getPropertyNo()) 	+ ","	//(財產編號)
					  +  serialNO	 									+ ","	// (財產分號)
					  +  Common.sqlChar(rs.getString("propertyname1")) 	+ ","	//(財產別名)
					  +  Common.sqlChar(rs.getString("signno")) 		+ ","	//(土地標示)
					  +  "'102'"										+ ","	//(增加原因)[102:撥入]
					  +  Common.sqlChar(rs.getString("cause1")) 		+ ","	//(其它說明)
					  +  "'1'"											+ ","	//(資料狀態)
					  +  "'N'"											+ ","	//(入帳)
					  +  "'N'"											+ ","	//(月結)
					  +  Common.sqlChar(getPropertyKind())				+ ","	//(財產性質)
					  +  Common.sqlChar(getFundType())					+ ","	//(基金財產)
					  +  Common.sqlChar(getValuable())					+ ","	//(珍貴財產)
					  +  Common.sqlChar(rs.getString("taxcredit"))		+ ","	//(抵繳遺產稅)
					  +  Common.sqlChar(rs.getString("grass"))			+ ","	//(新草衙)
					  +  Common.sqlChar(rs.getString("area"))			+ ","	//(總面積)
					  +  Common.sqlChar(rs.getString("holdnume"))		+ ","	//(原始權利分子)
					  +  Common.sqlChar(rs.getString("holddeno"))		+ ","	//(原始權利分母)
					  +  Common.sqlChar(rs.getString("holdarea"))		+ ","	//(原始權利範圍面積)
					  +  Common.sqlChar(rs.getString("bookunit"))		+ ","	//(原始單價)
					  +  Common.sqlChar(rs.getString("bookvalue"))		+ ","	//(原始總價)
					  +  Common.sqlChar(rs.getString("area"))			+ ","	//(總面積)
					  +  Common.sqlChar(rs.getString("holdnume"))		+ ","	//(權利分子)
					  +  Common.sqlChar(rs.getString("holddeno"))		+ ","	//(權利分母)
					  +  Common.sqlChar(rs.getString("holdarea"))		+ ","	//(權利範圍面積)
					  +  Common.sqlChar(rs.getString("accountingtitle"))+ ","	//(會計科目)
					  +  Common.sqlChar(rs.getString("bookunit"))		+ ","	//(單價)
					  +  Common.sqlChar(rs.getString("bookvalue"))		+ ","	//(總價)
					  +  Common.sqlChar(rs.getString("useseparate"))	+ ","	//(使用分區)
					  +  Common.sqlChar(rs.getString("usekind"))		+ ","	//(編定使用種類)
					  +  Common.sqlChar(getManageOrg())					+ ","	//(管理機關)
					  +  Common.sqlChar(getSourceDate())				+ ","	//(財產取得日期)
					  +  Common.sqlChar(getSourceDoc())					+ ","	//(財產取得文號)
					  +  Common.sqlChar(rs.getString("nonproof"))		+ ","	//(權狀)
					  +  Common.sqlChar(rs.getString("proofdoc"))		+ ","	//(權狀字號)
					  +  Common.sqlChar(rs.getString("proofverify"))	+ ","	//(權狀審核)
					  +  Common.sqlChar(getUserID())					+ ","
					  +  Common.sqlChar(Datetime.getYYYMMDD())			+ ","
					  +  Common.sqlChar(Datetime.getHHMMSS())
					  +  " )::;;::";
			//System.out.println(insertSql);
		}
		execSQLArray=insertSql.split("::;;::");
		db.excuteSQL(execSQLArray);
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}


