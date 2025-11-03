/*
*<br>程式目的：園區公共設施建設費總額報表檔 
*<br>程式代號：
*<br>撰寫日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
package unt.eg;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTEG006R extends SuperBean {
	private LogTools log = new LogTools();
	private HashMap params = new HashMap();	
	
	public HashMap getParams() {return params;}
	public void setParams(HashMap params) {this.params = params;}

	
	public DefaultTableModel getResultModel() throws Exception{
		this.enterOrg = Common.get(getQ_enterOrg());
		this.enterDateS = Common.get(Datetime.getYYYYMMDDFromRocDate(getQ_enterDateS()));	//轉成西元年
		this.enterDateE = Common.get(Datetime.getYYYYMMDDFromRocDate(getQ_enterDateE()));	//轉成西元年
		this.unitID = Common.get(getQ_unitid());
		
	    DefaultTableModel model = null;
	    Database db = null;
	    StringBuilder stb = new StringBuilder();
	   
	    try{
	    	String[] columns = new String[] {
	    								"deprParkName","engineeringName", "amountRecorded", "buildFee", "depracCountsName"    			
								    	};
		
			//=================================================================			
			stb = doGetSQL1();		//取得全部工程的資料			
			//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================
			
			UNTCH_Tools ut = new UNTCH_Tools();
			StringTools st = new StringTools();
		    Vector rowData = new Vector();	
			db = new Database();
			ResultSet rs1 = db.querySQL(stb.toString());
			
			if(rs1.next()){	
				//===========================================================
		    	//						Head
		    	//===========================================================
				String empname = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + this.getOrganID()+  "' and empid = '" + this.getEditID() + "'")._toString();
				String printdate = Common.MaskCDate(Datetime.getYYYMMDD());
				String enterdate = Common.MaskCDate(this.getQ_enterDateS()) + "起至" + Common.MaskCDate(this.getQ_enterDateE()) + "止";
				params.put("empName", empname);
				params.put("printDate", printdate);
				params.put("enterDate", enterdate);
				do{
					//===========================================================
			    	//						Detail
			    	//===========================================================
					Object[] data = new Object[columns.length];
					//園區別
					data[0] =  checkGet(rs1.getString("deprparkname"));
					//工程名稱
					data[1] = checkGet(rs1.getString("engineeringname"));
					//財產入帳金額
					String sql2 = doGetSQL2(checkGet(rs1.getString("engineeringno")));
					try{
						ResultSet rs2 = db.querySQL(sql2);
						if(rs2.next()){
							data[2] = Common.getInt(rs2.getInt("amountrecorded"));
						}
					}catch(Exception e){
						e.printStackTrace();
				    	log._execLogError(e.getMessage());
					}					
					//已提列公共設施建設費金額
					data[3] = Common.getInt(rs1.getInt("buildfee"));
					//財產折舊費用歸屬科目
					data[4] = "";
					String sql3 = doGetSQL3(checkGet(rs1.getString("engineeringno")));
					try{
						ResultSet rs3 = db.querySQL_NoChange(sql3);
						List<String> accounts = new ArrayList<String>();
						while(rs3.next()){
							String name = checkGet(rs3.getString("depraccountsname"));
							if(!name.equals("")) accounts.add(name);
						}
						for(int i= 0;i<accounts.size();i++){
							if(i!=0) data[4] = data[4] + "、";
							data[4] = data[4] + accounts.get(i);
						}
					}catch(Exception e){
						e.printStackTrace();
				    	log._execLogError(e.getMessage());
					}					
					
					rowData.addElement(data);
				}while(rs1.next());
				 //=================================================================
			    Object[][] data = new Object[0][0];
		        data = (Object[][])rowData.toArray(data);
		        model = new javax.swing.table.DefaultTableModel();
		        model.setDataVector(data, columns);
			}		    		    
	    }catch(Exception x){
	    	x.printStackTrace();
	    	log._execLogError(x.getMessage());
	    }finally{
	        db.closeAll();
	    }

	    return model;
	}	

	//=================================================================
	//	查詢SQL組成
	//=================================================================
	//取得全部工程的資料
	private StringBuilder doGetSQL1(){
		StringBuilder stb = new StringBuilder();		
		stb.append("select ").append(
				"x.deprpark, y.deprparkname, x.engineeringno, x.engineeringname, x.buildfee ").append(
				"from UNTEG_ENGINEERINGCASE x ").append(
				"left join SYSCA_DEPRPARK y on x.enterorg=y.enterorg and x.deprpark=y.deprparkno ").append(
				"where 1=1 ").append(		
				"");
		
		stb.append(doGetIfConditionforSQL1());		//加上查詢條件
		
		return stb;
	}
	
	//取得工程的財產入帳金額
	private String doGetSQL2(String engineeringno){
		StringTools st = new StringTools();
		String enterorg = Common.get(this.getEnterOrg());
				
		StringBuilder stb = new StringBuilder();
		stb.append("select ").append(
				"sum(z.amountrecorded) amountrecorded ").append(
				"from ( ").append(
				"");
		//加上各子查詢
		stb.append(doGetSubqueryforSQL2("UNTLA_LAND", "originalbv",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL2("UNTBU_BUILDING", "originalbv",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL2("UNTRF_ATTACHMENT", "originalbv",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL2("UNTMP_MOVABLEDETAIL", "originalbv",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL2("UNTLA_ADJUSTDETAIL", "addbookvalue-reducebookvalue",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL2("UNTBU_ADJUSTDETAIL", "addbookvalue-reducebookvalue",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL2("UNTRF_ADJUSTDETAIL", "addbookvalue-reducebookvalue",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL2("UNTMP_ADJUSTDETAIL", "addbookvalue-reducebookvalue",enterorg , engineeringno));		
		stb.append(") z ");
		
		return stb.toString();
	}

	//取得工程的財產折舊費用歸屬科目
	private String doGetSQL3(String engineeringno){
		StringTools st = new StringTools();
		String enterorg = Common.get(this.getEnterOrg());
				
		StringBuilder stb = new StringBuilder();
		stb.append("select ").append(
				"m.depraccounts, n.depraccountsname ").append(
				"from ( ").append(
				"");
		//加上各子查詢
		stb.append(doGetSubqueryforSQL3("UNTBU_BUILDING", "originaldepraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTRF_ATTACHMENT", "originaldepraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTMP_MOVABLEDETAIL", "originaldepraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTBU_ADJUSTDETAIL", "newdepraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTRF_ADJUSTDETAIL", "newdepraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTMP_ADJUSTDETAIL", "newdepraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTBU_BUILDING", "depraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTRF_ATTACHMENT", "depraccounts",enterorg , engineeringno));
		stb.append(" union ").append(doGetSubqueryforSQL3("UNTMP_MOVABLEDETAIL", "depraccounts",enterorg , engineeringno));			
		stb.append(") m ").append(
				"left join SYSCA_DEPRACCOUNTS n on n.enterorg='").append(enterorg).append("' and m.depraccounts=n.depraccountsno ").append(
				"");
		
		return stb.toString();
	}
	//=================================================================
	//	查詢條件
	//=================================================================
	private String doGetIfConditionforSQL1(){
		StringBuilder stb = new StringBuilder();
		StringTools st = new StringTools();
		String enterOrg = Common.sqlChar(this.getEnterOrg());
		String enterDateS = Common.sqlChar(this.getEnterDateS());
		String enterDateE = Common.sqlChar(this.getEnterDateE());
		String unitid = Common.sqlChar(this.getUnitID());
		
		if(!st._isEmpty(getQ_enterOrg())){
			stb.append(" and x.enterorg = ").append(enterOrg);
		}		
		stb.append(" and x.engineeringno in ( ");
		
		//子查詢 for UNTLA_LAND
		stb.append(doGetSubqueryforSQL1("a", "UNTLA_LAND",enterOrg, enterDateS, enterDateE, unitid));
		//子查詢 for UNTBU_BUILDING
		stb.append(" union ").append(doGetSubqueryforSQL1("b", "UNTBU_BUILDING",enterOrg, enterDateS, enterDateE, unitid));
		//子查詢 for UNTRF_ATTACHMENT
		stb.append(" union ").append(doGetSubqueryforSQL1("c", "UNTRF_ATTACHMENT",enterOrg, enterDateS, enterDateE, unitid));
		//子查詢 for UNTMP_MOVABLEDETAIL
		stb.append(" union ").append(doGetSubqueryforSQL1("d", "UNTMP_MOVABLEDETAIL",enterOrg, enterDateS, enterDateE, unitid));
		//子查詢 for UNTLA_ADJUSTDETAIL
		stb.append(" union ").append(doGetSubqueryforSQL1Adjustdate("e", "UNTLA_ADJUSTDETAIL",enterOrg, enterDateS, enterDateE, unitid));
		//子查詢 for UNTBU_ADJUSTDETAIL
		stb.append(" union ").append(doGetSubqueryforSQL1Adjustdate("f", "UNTBU_ADJUSTDETAIL",enterOrg, enterDateS, enterDateE, unitid));
		//子查詢 for UNTRF_ADJUSTDETAIL
		stb.append(" union ").append(doGetSubqueryforSQL1Adjustdate("g", "UNTRF_ADJUSTDETAIL",enterOrg, enterDateS, enterDateE, unitid));
		//子查詢 for UNTMP_ADJUSTDETAIL
		stb.append(" union ").append(doGetSubqueryforSQL1Adjustdate("h", "UNTMP_ADJUSTDETAIL",enterOrg, enterDateS, enterDateE, unitid));
		
		stb.append(" ) order by x.deprpark, x.engineeringno ");
		
		return stb.toString();
	}
	
	//子查詢 for 各財產大類主檔
	private String doGetSubqueryforSQL1(String alias, String table, String enterOrg, String enterDateS, String enterDateE, String unitid){
		StringBuilder stb = new StringBuilder();		
		stb.append(
				"( select distinct ").append(alias).append("2.engineeringno ").append(
				"from ").append(table).append(" ").append(alias).append("1, UNTEG_ENGINEERINGCASE ").append(alias).append("2 ").append(
				"where ").append(alias).append("1.enterorg = ").append(alias).append("2.enterorg ").append(
				"and ").append(alias).append("1.engineeringno = ").append(alias).append("2.engineeringno ").append(
				"");
				if(!"''".equals(enterOrg)){
					stb.append(" and ").append(alias).append("1.enterorg = ").append(enterOrg);
				}
				if(!"''".equals(enterDateS)){
					stb.append(" and ").append(alias).append("1.enterdate >= ").append(enterDateS);
				}
				if(!"''".equals(enterDateE)){
					stb.append(" and ").append(alias).append("1.enterdate <= ").append(enterDateE);
				}
				if(!"''".equals(unitid)){
					stb.append(" and ").append(alias).append("2.unitid = ").append(unitid);
				}
				stb.append(" ) ");
		
		return stb.toString();
	}
	
	//子查詢 for 各增減值單主檔
	private String doGetSubqueryforSQL1Adjustdate(String alias, String table, String enterOrg, String enterDateS, String enterDateE, String unitid){
		StringBuilder stb = new StringBuilder();		
		stb.append(
				"( select distinct ").append(alias).append("2.engineeringno ").append(
				"from ").append(table).append(" ").append(alias).append("1, UNTEG_ENGINEERINGCASE ").append(alias).append("2 ").append(
				"where ").append(alias).append("1.enterorg = ").append(alias).append("2.enterorg ").append(
				"and ").append(alias).append("1.engineeringno = ").append(alias).append("2.engineeringno ").append(
				"");
				if(!"''".equals(enterOrg)){
					stb.append(" and ").append(alias).append("1.enterorg = ").append(enterOrg);
				}
				if(!"''".equals(enterDateS)){
					stb.append(" and ").append(alias).append("1.adjustdate >= ").append(enterDateS);
				}
				if(!"''".equals(enterDateE)){
					stb.append(" and ").append(alias).append("1.adjustdate <= ").append(enterDateE);
				}
				if(!"''".equals(unitid)){
					stb.append(" and ").append(alias).append("2.unitid = ").append(unitid);
				}
				stb.append(" ) ");
		
		return stb.toString();
	}
	//子查詢 for SQL2
	private String doGetSubqueryforSQL2(String tablename, String colname, String enterorg, String engineeringno){
		StringBuilder stb = new StringBuilder();
		stb.append(
				"( select sum( ").append(colname).append(" ) amountrecorded ").append(
				"from ").append(tablename).append(" ").append(
				"where enterorg = '").append(enterorg).append("' ").append(
				"and engineeringno = '").append(engineeringno).append("' ").append(
				"and verify = 'Y' ) ").append(
				"");
		
		return stb.toString();
	}
	//子查詢 for SQL3
	private String doGetSubqueryforSQL3(String tablename, String colname, String enterorg, String engineeringno){
		StringBuilder stb = new StringBuilder();
		stb.append(
				"( select distinct ").append(colname).append(" depraccounts ").append(
				"from ").append(tablename).append(" ").append(
				"where enterorg = '").append(enterorg).append("' ").append(
				"and engineeringno = '").append(engineeringno).append("' ").append(
				"and verify = 'Y' ) ").append(
				"");
		
		return stb.toString();
	}
	
	//=================================================================================
	//	測試區
	//=================================================================================
	
	public static void main(String[] args){

//		UNTEG006R eg = new UNTEG006R();
//		StringBuilder stb = new StringBuilder();
//		
//		eg.setQ_enterOrg("A27040000G");
//		eg.setQ_enterDateS("20010101");
//		eg.setQ_enterDateE("20151015");
//		eg.setQ_unitid("");
//		
//		stb = eg.doGetSQL1();		
//		System.out.println(stb.toString());
//		
//		eg.setEnterOrg("A27040000G");
//		eg.setEnterDateS("20010101");
//		eg.setEnterDateE("20151015");
//		eg.setUnitID("");
//		System.out.println(eg.doGetSQL2("SBIP-103-007").toString());
	}		
		
	//=================================================================================
	
	String enterOrg;
	String enterDateS;
	String enterDateE;
	String unitID;
	String q_enterOrg;
	String q_enterOrgName;
	String q_enterDateS;
	String q_enterDateE;
	String q_unitid;
	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterDateS() {return checkGet(enterDateS); }
	public void setEnterDateS(String enterDateS) {this.enterDateS = checkSet(enterDateS); }
	public String getEnterDateE() {return checkGet(enterDateE); }
	public void setEnterDateE(String enterDateE) {this.enterDateE = checkSet(enterDateE); }
	public String getUnitID() {return checkGet(unitID); }
	public void setUnitID(String unitID) {this.unitID = checkSet(unitID); }	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }	
	public String getQ_enterDateS() {return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String q_enterDateS) {this.q_enterDateS = checkSet(q_enterDateS); }
	public String getQ_enterDateE() {return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String q_enterDateE) {this.q_enterDateE = checkSet(q_enterDateE); }
	public String getQ_unitid() {	return checkGet(q_unitid); }
	public void setQ_unitid(String q_unitid) {	this.q_unitid = checkSet(q_unitid); }
	
	private String isOrganManager;
	private String isAdminManager;
	private String organID;
	private String roleID;
	private String editID;
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getRoleID() { return checkGet(roleID); }
    public void setRoleID(String s) { roleID = checkSet(s); }
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}   

}