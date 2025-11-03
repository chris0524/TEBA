package unt.eg;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import unt.la.UNTLA001F;
import util.Common;
import util.Database;

public class UNTEG002F extends UNTEG001Q{
		
	public ArrayList queryAll() throws Exception{
		Database db = new Database();
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			this.verify = "N";
			
			String sql = "select * from (" +
							getQuerySQL_forUNTLA_LAND() +
							" union " +
							getQuerySQL_forUNTBU_BUILDING() +
							" union " +
							getQuerySQL_forUNTRF_ATTACHMENT() +
							" union " +
							getQuerySQL_forUNTMP_MOVABLE() +
							" union " +
							getQuerySQL_forUNTVP_ADDPROOF() +
							" union " +
							getQuerySQL_forUNTRT_ADDPROOF() +						
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

		public String getQuerySQL_forUNTLA_LAND(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" 1 as amount," +
							" area," +
							" originalbv," +
							" sourcedate," +
							" enterdate," +
							" null as buildfeecb" +
						" from UNTLA_LAND a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'";
			
			return sql;
		}
		
		public String getQuerySQL_forUNTBU_BUILDING(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" 1 as amount," +
							" area," +
							" originalbv," +
							" sourcedate," +
							" enterdate," +
							" case buildfeecb when 'Y' then '是' else '否' end as buildfeecb" +
						" from UNTBU_BUILDING a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'" +
							(("".equals(checkGet(getQ_deprUnitCB())))?"":" and deprUnitCB = 'Y'");
			
			return sql;
		}
		
		public String getQuerySQL_forUNTRF_ATTACHMENT(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" 1 as amount," +
							" measure as area," +
							" originalbv," +
							" sourcedate," +
							" enterdate," +
							" case buildfeecb when 'Y' then '是' else '否' end as buildfeecb" +
						" from UNTRF_ATTACHMENT a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'" +
							(("".equals(checkGet(getQ_deprUnitCB())))?"":" and deprUnitCB = 'Y'");
			return sql;
		}
		
		public String getQuerySQL_forUNTMP_MOVABLE(){
			String sql = "select" +
							" a.enterorg," +
							" a.ownership," +
							" a.differencekind," +
							" a.caseno," +
							" (select top 1 proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" a.propertyno," +
							" z.serialno," +
							" a.propertyname1," +
							" z.bookamount as amount," +
							" null as area," +
							" z.originalbv," +
							" a.sourcedate," +
							" a.enterdate," +
							" case z.buildfeecb when 'Y' then '是' else '否' end as buildfeecb" +
						" from UNTMP_MOVABLE a" +
							" left join UNTMP_MOVABLEDETAIL z on z.enterorg = a.enterorg and z.caseno = a.caseno and z.ownership = a.ownership and z.differencekind = a.differencekind and z.propertyno = a.propertyno and z.lotno = a.lotno" +
						" where 1=1" +
							" and a.enterorg = '" + enterOrg + "'" +
							" and a.engineeringno = '" + engineeringNo + "'" +
							" and a.verify = '" + verify + "'" +
							(("".equals(checkGet(getQ_deprUnitCB())))?"":" and (select z.serialno from UNTMP_MOVABLEDETAIL z where z.enterorg = a.enterorg and z.caseno = a.caseno and z.ownership = a.ownership and z.differencekind = a.differencekind) = 'Y'");
			return sql;
		}
		
		public String getQuerySQL_forUNTVP_ADDPROOF(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" bookamount as amount," +
							" null as area," +
							" originalbv," +
							" sourcedate," +
							" enterdate," +
							" null as buildfeecb" +
						" from UNTVP_ADDPROOF a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'";
			return sql;
		}
		
		public String getQuerySQL_forUNTRT_ADDPROOF(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADDPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" null as amount," +
							" null as area," +
							" originalbv," +
							" sourcedate," +
							" enterdate," +
							" null as buildfeecb" +
						" from UNTRT_ADDPROOF a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'";
			return sql;
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

	public String verify;
	
	private String isAdminManager;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
}
