package unt.eg;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTEG004F extends UNTEG001Q{

	public ArrayList queryAll() throws Exception{
		Database db = new Database();
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			this.verify = "N";
			
			String sql = "select * from (" +
							getQuerySQL_forUNTLA_ADJUSTDETAIL() +
							" union " +
							getQuerySQL_forUNTBU_ADJUSTDETAIL() +
							" union " +
							getQuerySQL_forUNTRF_ADJUSTDETAIL() +
							" union " +
							getQuerySQL_forUNTMP_ADJUSTDETAIL() +
							" union " +
							getQuerySQL_forUNTVP_ADJUSTPROOF() +
							" union " +
							getQuerySQL_forUNTRT_ADJUSTPROOF() +						
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
				rowArray[4] = checkGet(rs.getString("newarea"));
				rowArray[5] = checkGet(rs.getString("addbookvalue"));
				rowArray[6] = checkGet(ut._transToROC_Year(checkGet(rs.getString("sourcedate"))));
				rowArray[7] = checkGet(ut._transToROC_Year(checkGet(rs.getString("adjustdate"))));
				rowArray[8] = checkGet(rs.getString("deprunitcb"));	
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

		public String getQuerySQL_forUNTLA_ADJUSTDETAIL(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" 1 as amount," +
							" newarea," +
							" addbookvalue," +
							" sourcedate," +
							" adjustdate," +
							" null as deprunitcb" +
						" from UNTLA_ADJUSTDETAIL a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'";
			return sql;
		}
		
		public String getQuerySQL_forUNTBU_ADJUSTDETAIL(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" 1 as amount," +
							" newarea," +
							" addbookvalue," +
							" sourcedate," +
							" adjustdate," +
							" case newdeprunitcb when 'Y' then '是' else '否' end as deprunitcb" +
						" from UNTBU_ADJUSTDETAIL a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'" +
							(("".equals(checkGet(getQ_deprUnitCB())))?"":" and deprUnitCB = 'Y'");
			return sql;
		}
		
		public String getQuerySQL_forUNTRF_ADJUSTDETAIL(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" 1 as amount," +
							" newmeasure as newarea," +
							" addbookvalue," +
							" sourcedate," +
							" adjustdate," +
							" case newdeprunitcb when 'Y' then '是' else '否' end as deprunitcb" +
						" from UNTRF_ADJUSTDETAIL a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'" +
							(("".equals(checkGet(getQ_deprUnitCB())))?"":" and deprUnitCB = 'Y'");
			return sql;
		}
		
		public String getQuerySQL_forUNTMP_ADJUSTDETAIL(){
			String sql = "select" +
							" a.enterorg," +
							" a.ownership," +
							" a.differencekind," +
							" a.caseno," +
							" (select top 1 proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" a.propertyno," +
							" z.serialno," +
							" a.propertyname1," +
							" a.bookamount as amount," +
							" null as newarea," +
							" a.addbookvalue," +
							" a.sourcedate," +
							" a.adjustdate," +
							" case a.newdeprunitcb when 'Y' then '是' else '否' end as deprunitcb" +
						" from UNTMP_ADJUSTDETAIL a" +
							" left join UNTMP_MOVABLEDETAIL z on z.enterorg = a.enterorg and z.caseno = a.caseno and z.ownership = a.ownership and z.differencekind = a.differencekind and z.propertyno = a.propertyno and z.lotno = a.lotno" +						
						" where 1=1" +
							" and a.enterorg = '" + enterOrg + "'" +
							" and a.engineeringno = '" + engineeringNo + "'" +
							" and a.verify = '" + verify + "'" +
							(("".equals(checkGet(getQ_deprUnitCB())))?"":" and (select z.serialno from UNTMP_MOVABLEDETAIL z where z.enterorg = a.enterorg and z.caseno = a.caseno and z.ownership = a.ownership and z.differencekind = a.differencekind) = 'Y'");
			return sql;
		}
		
		public String getQuerySQL_forUNTVP_ADJUSTPROOF(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" newbookamount as amount," +
							" null as newarea," +
							" addbookvalue," +
							" sourcedate," +
							" adjustdate," +
							" null as deprunitcb" +
						" from UNTVP_ADJUSTPROOF a" +
						" where 1=1" +
							" and enterorg = '" + enterOrg + "'" +
							" and engineeringno = '" + engineeringNo + "'" +
							" and verify = '" + verify + "'";
			return sql;
		}
		
		public String getQuerySQL_forUNTRT_ADJUSTPROOF(){
			String sql = "select" +
							" enterorg," +
							" ownership," +
							" differencekind," +
							" caseno," +
							" (select proofyear + '年' + proofdoc + '字第' + proofno + '號' from UNTLA_ADJUSTPROOF z where z.enterorg = a.enterorg and z.ownership = a.ownership and z.differencekind = a.differencekind and z.caseno = a.caseno) as proofData," +
							" propertyno," +
							" serialno," +
							" propertyname1," +
							" null as amount," +
							" null as newarea," +
							" addbookvalue," +
							" sourcedate," +
							" adjustdate," +
							" null as deprunitcb" +
						" from UNTRT_ADJUSTPROOF a" +
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
