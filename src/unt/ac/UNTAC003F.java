package unt.ac;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import unt.dp.UNTDP003F;
import unt.dp.UNTDP004F;
import unt.gr.UNTGR009R;
import unt.gr.UNTGR011R;
import unt.gr.UNTGR021R;
import unt.gr.UNTGR030R;
import util.BeanLocker;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.User;
import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.MathTools;

public class UNTAC003F extends SuperBean{

	private LogTools log = new LogTools();

	public String getLockId(String organid) {
		return Common.get(organid) + "執行月結作業";
	}
	
	//=================================================
	
	public void calculateBalanceInMonth(User user){
		if(getsKeySet()!=null){
			String lockid = this.getLockId(user.getOrganID());
			if (BeanLocker.isLocked(lockid)) {
				this.setErrorMsg(BeanLocker.getLockedMsg(lockid));
			} else {
				BeanLocker.forceLock(user, lockid, "月結作業未完成，不可再執行月結作業資料");
				this.setClosing1ID(getUserID());
				this.setClosing1Name(getUserName());
				this.setClosing1Date(Datetime.getYYYMMDD());
				try {
					
					execCalculateBalanceInMonth(true);
					
					this.setToggleAll("");// 客戶736
					setErrorMsg("月結執行完成");
				} catch (Exception e) {
					setErrorMsg("執行月結發生錯誤，已回復月結前狀態。(" + Common.checkGet(e.getMessage()) + ")");
					e.printStackTrace();
				} finally {
					BeanLocker.forceUnlock(lockid);
				}
			}			
		} else {
			setErrorMsg("請勾選資料");
		}
	}

	//=================================================
	//欲執行月結的table
	private String[] obtainTables(){
		String[] tables = {"UNTLA_LAND",
							"UNTBU_BUILDING",
							"UNTRF_ATTACHMENT",
							"UNTMP_MOVABLE",
							"UNTMP_MOVABLEDETAIL",
							"UNTVP_ADDPROOF",
							"UNTRT_ADDPROOF",
							"UNTCH_DEPRPERCENT"};
		return tables;
	}

//=================================================
	//取得create Index SQL
	private List obtainCreateIndexSQL(String tableType, String YYYMM){
		List result = new ArrayList();
		
		if("UNTLA_LAND".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTLA_LAND_" + YYYMM + "_IDX1 ON UNTLA_LAND_" + YYYMM + "(enterorg,ownership, differencekind, propertyno, serialno);");
			result.add("CREATE INDEX UNTLA_LAND_" + YYYMM + "_IDX2 ON UNTLA_LAND_" + YYYMM + "(enterorg, caseno);");
			result.add("CREATE INDEX UNTLA_LAND_" + YYYMM + "_IDX3 ON UNTLA_LAND_" + YYYMM + "(enterorg, ownership, differencekind,signno, datastate);");
			result.add("CREATE INDEX UNTLA_LAND_" + YYYMM + "_IDX4 ON UNTLA_LAND_" + YYYMM + "(signno);");
			result.add("CREATE INDEX UNTLA_LAND_" + YYYMM + "_IDX5 ON UNTLA_LAND_" + YYYMM + "(enterorg, signno);");
			
		}else if("UNTBU_BUILDING".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTBU_BUILDING_" + YYYMM + "_IDX1 ON UNTBU_BUILDING_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, serialno);");
			result.add("CREATE INDEX UNTBU_BUILDING_" + YYYMM + "_IDX2 ON UNTBU_BUILDING_" + YYYMM + "(enterorg, caseno);");
			result.add("CREATE INDEX UNTBU_BUILDING_" + YYYMM + "_IDX3 ON UNTBU_BUILDING_" + YYYMM + "(enterorg, ownership, differencekind, signno, datastate);");
			
		}else if("UNTRF_ATTACHMENT".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTRF_ATTACHMENT_" + YYYMM + "_IDX1 ON UNTRF_ATTACHMENT_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, serialno);");
			result.add("CREATE INDEX UNTRF_ATTACHMENT_" + YYYMM + "_IDX2 ON UNTRF_ATTACHMENT_" + YYYMM + "(enterorg, caseno);");
			
		}else if("UNTMP_MOVABLE".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTMP_MOVABLE_" + YYYMM + "_IDX1 ON UNTMP_MOVABLE_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, lotno);");
			result.add("CREATE INDEX UNTMP_MOVABLE_" + YYYMM + "_IDX2 ON UNTMP_MOVABLE_" + YYYMM + "(enterorg, caseno);");
			
		}else if("UNTMP_MOVABLEDETAIL".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTMP_MOVABLEDETAIL_" + YYYMM + "_IDX1 ON UNTMP_MOVABLEDETAIL_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, serialno);");
			result.add("CREATE INDEX UNTMP_MOVABLEDETAIL_" + YYYMM + "_IDX2 ON UNTMP_MOVABLEDETAIL_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, lotno);");
			result.add("CREATE INDEX UNTMP_MOVABLEDETAIL_" + YYYMM + "_IDX3 ON UNTMP_MOVABLEDETAIL_" + YYYMM + "(enterorg, caseno);");
			
		}else if("UNTRT_ADDPROOF".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTRT_ADDPROOF_" + YYYMM + "_IDX1 ON UNTRT_ADDPROOF_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, serialno);");
			
		}else if("UNTVP_ADDPROOF".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTVP_ADDPROOF_" + YYYMM + "_IDX1 ON UNTVP_ADDPROOF_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, serialno);");
			
		}else if("UNTCH_DEPRPERCENT".equals(tableType)){
			result.add("CREATE UNIQUE INDEX UNTCH_DEPRPERCENT_" + YYYMM + "_IDX1 ON UNTCH_DEPRPERCENT_" + YYYMM + "(enterorg, ownership, differencekind, propertyno, serialno, serialno1);");
			
		}
		
		return result;
	}
		
	/**
	 * 自動帶入最新資料 
	 * @param enterorg
	 * @param caseno
	 * @throws Exception
	 */
	private void doImportNewDataForReduceProof(String enterorg, String caseno) throws Exception {
		// 建物
		String sqlbu = "update d" +	
					  " set d.netvalue=a.netvalue, d.oldaccumdepr=a.accumdepr" +
					  " from UNTBU_REDUCEDETAIL d, UNTBU_BUILDING a" +
					  " where 1=1" +
					  " and d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(enterorg) +
					  " and d.caseno = " +  Common.sqlChar(caseno) +
					  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )";
		this.db.excuteSQLNoAutoCommit(sqlbu);
		
	    // 土改
		String sqlrf = " update d" +
					  " set d.netvalue=a.netvalue, d.oldaccumdepr=a.accumdepr" +
					  " from UNTRF_REDUCEDETAIL d, UNTRF_ATTACHMENT a" +
					  " where 1=1" +
					  " and d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(enterorg) +
					  " and d.caseno = " +  Common.sqlChar(caseno) +
					  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )";
		this.db.excuteSQLNoAutoCommit(sqlrf);
		
		// 動產
		String sqlmp = " update d" +
					  " set d.oldbookunit=a.bookvalue, d.oldbookvalue=a.bookvalue" +
					  " ,d.oldnetunit=a.netvalue, d.oldnetvalue=a.netvalue" +
					  " ,d.oldaccumdepr=a.accumdepr" +
					  " ,d.adjustbookunit=a.bookvalue, d.adjustbookvalue=a.bookvalue" +
					  " ,d.adjustnetunit=a.netvalue, d.adjustnetvalue=a.netvalue" +
					  " ,d.adjustaccumdepr=a.accumdepr" +
					  " from UNTMP_REDUCEDETAIL d, UNTMP_MOVABLEDETAIL a" +
					  " where 1=1" +
					  " and d.enterorg=a.enterorg  and d.ownership=a.ownership  and d.differencekind=a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(enterorg) +
					  " and d.caseno = " +  Common.sqlChar(caseno) +
					  " and  ( d.oldnetvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )";
		this.db.excuteSQLNoAutoCommit(sqlmp);
		
	    // 權利
		String sqlrt = " update d" +
					  " set d.netvalue=a.netvalue, d.oldaccumdepr=a.accumdepr" +
					  " from UNTRT_REDUCEPROOF d, UNTRT_ADDPROOF a" +
					  " where 1=1" +
					  " and d.enterorg =a.enterorg  and d.ownership=a.ownership  and d.differencekind= a.differencekind  and d.propertyno=a.propertyno  and d.serialno=a.serialno" +
					  " and d.enterorg = " + Common.sqlChar(enterorg) +
					  " and d.caseno = " +  Common.sqlChar(caseno) +
					  " and  ( d.netvalue != a.netvalue or d.oldaccumdepr != a.accumdepr )";
		this.db.excuteSQLNoAutoCommit(sqlrt);
		
		// 問題單1628: 當土改、建物、動產、權利減損單執行[帶入最新資料]時，請更新減損單明細中的已使用年月(useyear年usemonth月)
		String sqluse = " update UNTBU_REDUCEDETAIL set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
					+ " where enterorg=" + Common.sqlChar(enterorg) + " and caseno=" +  Common.sqlChar(caseno) + " and verify='N';"
					+ " update UNTRF_REDUCEDETAIL set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
					+ " where enterorg=" + Common.sqlChar(enterorg) + " and caseno=" +  Common.sqlChar(caseno) + " and verify='N';"
					+ " update UNTMP_REDUCEDETAIL set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
					+ " where enterorg=" + Common.sqlChar(enterorg) + " and caseno=" +  Common.sqlChar(caseno) + " and verify='N';"
					+ " update UNTRT_REDUCEPROOF set useyear=DATEDIFF(M,buydate,getdate())/12, usemonth=DATEDIFF(M,buydate,getdate())%12"
					+ " where enterorg=" + Common.sqlChar(enterorg) + " and caseno=" +  Common.sqlChar(caseno) + " and verify='N';";
		this.db.excuteSQLNoAutoCommit(sqluse);
	}
	
	/**
	 * 預計移撥減損單入帳
	 */
	private void doActionForPreEnterReduceProof() throws Exception {
		
		Database qDB = null;
		Database qDB2 = null;
		StringBuilder qSQL = new StringBuilder();
		ResultSet rs = null;
		ResultSet rs2 = null;
		UNTCH_Tools ut = new UNTCH_Tools();
		
		try {
			// 以土地減損單為主
			qSQL.append("select enterorg,caseno,proofyear,proofdoc,proofno,verify")
				.append(" from UNTLA_REDUCEPROOF where 1=1 ")
				.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
				.append(" and preenterdate like '").append(ut._transToCE_Year(this.YYYMM)).append("%'");
			
			qDB = new Database();
			qDB2 = new Database();
			
			String enterorg = this.getEnterOrg();
			String editid = this.getUserID();
			String editdate = Datetime.getYYYYMMDD();
			String edittime = Datetime.getHHMMSS();
			int chkCNT = 0;
			
			//問題單1837
			rs = this.db.querySQL(qSQL.toString());
			while (rs.next()) {
				String caseno = Common.get(rs.getString("caseno"));
				String proofyear = Common.get(rs.getString("proofyear"));
				String proofdoc = Common.get(rs.getString("proofdoc"));
				String proofno = Common.get(rs.getString("proofno"));
				String verify = Common.get(rs.getString("verify"));
				String prooftext = "預計移撥減損單" + proofyear + "年" + proofdoc + "字第" + proofno + "號";
				if ("Y".equals(verify)) {
					throw new Exception("" + prooftext + " 已入帳");
				}
				
				StringBuilder qSQL2 = new StringBuilder();
				StringBuilder updateSQL = new StringBuilder();
				
				//#region check 減損單有無明細 
				qSQL2.append(" select sum(x.cnt) as chkCNT from (")
					 .append(" select count(1) as cnt from UNTLA_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTRF_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTBU_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTMP_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTRT_REDUCEPROOF where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTVP_REDUCEPROOF where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(") as x ");
				rs2 = qDB2.querySQL(qSQL2.toString());
				
				chkCNT = rs2.next() ? rs2.getInt("chkCNT") : 0;
				if (chkCNT == 0) {
					throw new Exception("" + prooftext + " 無明細資料");
				}
				//#end 
				
				// 帶入最新資料... 
				this.doImportNewDataForReduceProof(enterorg, caseno);
				
				//#region 土地減損 
				updateSQL.setLength(0);
				updateSQL.append(" update UNTLA_REDUCEPROOF set ")
						 .append(" verify='Y', reducedate=preenterdate")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'Y' ")
						  .append(" ,reducedate = b.preenterdate ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTLA_REDUCEDETAIL a")
						  .append(" inner join UNTLA_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" area='0' ,holdnume='0' ,holddeno='0' ,holdarea='0' ,bookvalue='0' ,netvalue='0' ,datastate='2'")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" ,reducedate = c.preenterdate ")
						  .append(" from UNTLA_LAND a ")
						  .append(" inner join UNTLA_REDUCEDETAIL b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" inner join UNTLA_REDUCEPROOF c on c.enterorg = b.enterorg and c.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and c.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and c.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 建物減損 
				updateSQL.setLength(0);
				updateSQL.append(" update UNTBU_REDUCEPROOF set ")
						 .append(" verify='Y', reducedate=preenterdate")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'Y' ")
						  .append(" ,reducedate = b.preenterdate ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTBU_REDUCEDETAIL a")
						  .append(" inner join UNTBU_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" carea='0' ,sarea='0' ,holdnume='0' ,holddeno='0' ,holdarea='0' ,bookvalue='0' ,netvalue='0' ,datastate='2'")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" ,reducedate = c.preenterdate ")
						  .append(" from UNTBU_BUILDING a ")
						  .append(" inner join UNTBU_REDUCEDETAIL b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" inner join UNTBU_REDUCEPROOF c on c.enterorg = b.enterorg and c.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and c.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and c.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 土改減損
				updateSQL.setLength(0);
				updateSQL.append(" update UNTRF_REDUCEPROOF set ")
						 .append(" verify='Y', reducedate=preenterdate")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'Y' ")
						  .append(" ,reducedate = b.preenterdate ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTRF_REDUCEDETAIL a")
						  .append(" inner join UNTRF_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" measure='0' ,bookvalue='0' ,netvalue='0' ,datastate='2'")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" ,reducedate = c.preenterdate ")
						  .append(" from UNTRF_ATTACHMENT a ")
						  .append(" inner join UNTRF_REDUCEDETAIL b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" inner join UNTRF_REDUCEPROOF c on c.enterorg = b.enterorg and c.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and c.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and c.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 動產減損 
				updateSQL.setLength(0);
				updateSQL.append(" update UNTMP_REDUCEPROOF set ")
						 .append(" verify='Y', reducedate=preenterdate")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'Y' ")
						  .append(" ,reducedate = b.preenterdate ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTMP_REDUCEDETAIL a")
						  .append(" inner join UNTMP_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 動產明細
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						 .append(" bookamount = bookamount - b.adjustbookamount")
						 .append(" ,bookvalue = bookvalue - b.adjustbookvalue ")
						 .append(" ,netvalue = netvalue - b.adjustnetvalue")
						 .append(" ,reducedate = c.preenterdate")
						 .append(" ,reducecause = c.cause ,reducecause1=c.cause1,datastate = '2'")
						 .append(" from UNTMP_MOVABLEDETAIL a  ")
						 .append(" inner join UNTMP_REDUCEDETAIL b ")
						 .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						 .append(" inner join UNTMP_REDUCEPROOF c ")
						 .append(" on b.enterorg=c.enterorg and b.caseno=c.caseno ")
						 .append(" where c.enterorg=").append(Common.sqlChar(enterorg)).append(" and c.caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 動產主檔
				updateSQL.setLength(0);
				updateSQL.append(" update d set ")
						 .append(" bookamount = d.bookamount - b.adjustbookamount")
						 .append(" ,bookvalue = d.bookvalue - b.adjustbookvalue ")
						 .append(" ,netvalue = d.netvalue - b.adjustnetvalue")  // TODO 怪怪 怎麼會是adjsutbookvalue
						 .append(" ,datastate = '2' ")
						 .append(" from UNTMP_MOVABLE d ")
						 .append(" inner join UNTMP_MOVABLEDETAIL a ")
						 .append(" on a.enterorg=d.enterorg and a.ownership=d.ownership and a.differencekind=d.differencekind and a.propertyno=d.propertyno and a.lotno=d.lotno ")
						 .append(" inner join UNTMP_REDUCEDETAIL b ")
						 .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						 .append(" inner join UNTMP_REDUCEPROOF c ")
						 .append(" on b.enterorg=c.enterorg and b.caseno=c.caseno ")
						 .append(" where c.enterorg=").append(Common.sqlChar(enterorg)).append(" and c.caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				//#end 
				
				//#region 權利減損 
				updateSQL.setLength(0);
				updateSQL.append(" update UNTRT_REDUCEPROOF set ")
						 .append(" verify='Y', reducedate=preenterdate")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" bookvalue='0' ,netvalue='0' ,reducedate=b.preenterdate ,reducecause=b.cause ,reducecause1=b.cause1 ,datastate='2'")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" from UNTRT_ADDPROOF a ")
						  .append(" inner join UNTRT_REDUCEPROOF b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 有價證券減損 
				updateSQL.setLength(0);
				updateSQL.append(" update UNTVP_REDUCEPROOF set ")
						 .append(" verify='Y' ,adjustdate=preenterdate ")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" bookamount='0' ,bookvalue='0' ,reducedate=b.preenterdate ,reducecause=b.cause ,reducecause1=b.cause1 ,datastate='2'")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" from UNTVP_ADDPROOF a ")
						  .append(" inner join UNTVP_REDUCEPROOF b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				//#end 
				
				
			}
		} finally {
			//#region clear all 
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e) { }
			}
			if (rs2 != null) {
				try {
					rs2.close();
					rs2 = null;
				} catch (Exception e) { }
			}
			if (qDB != null) {
				qDB.closeAll();
				qDB = null;
			}
			if (qDB2 != null) {
				qDB2.closeAll();
				qDB2 = null;
			}
			//#end 
		}
	}
	
	/**
	 * 預計移撥減損單回復入帳
	 */
	private void doActionForPreEnterReduceProof2() throws Exception {
		
		Database qDB = null;
		Database qDB2 = null;
		StringBuilder qSQL = new StringBuilder();
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		UNTCH_Tools ut = new UNTCH_Tools();
		
		try {
			// 以土地減損單為主
			qSQL.append("select enterorg,caseno,proofyear,proofdoc,proofno,verify")
				.append(" from UNTLA_REDUCEPROOF where 1=1 ")
				.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
				.append(" and preenterdate like '").append(ut._transToCE_Year(this.YYYMM)).append("%'");
			
			qDB = new Database();
			qDB2 = new Database();
			
			String enterorg = this.getEnterOrg();
			String editid = this.getUserID();
			String editdate = Datetime.getYYYYMMDD();
			String edittime = Datetime.getHHMMSS();
			int chkCNT = 0;
			//問題單1837，一併修正回復入帳
			rs = this.db.querySQL(qSQL.toString());
			while (rs.next()) {
				String caseno = Common.get(rs.getString("caseno"));
				String proofyear = Common.get(rs.getString("proofyear"));
				String proofdoc = Common.get(rs.getString("proofdoc"));
				String proofno = Common.get(rs.getString("proofno"));
				String verify = Common.get(rs.getString("verify"));
				String prooftext = "預計移撥減損單" + proofyear + "年" + proofdoc + "字第" + proofno + "號";
				if ("N".equals(verify)) {
					throw new Exception("月結回復失敗," + prooftext + " 已回復入帳");
				}
				
				StringBuilder qSQL2 = new StringBuilder();
				StringBuilder updateSQL = new StringBuilder();
				
				//#region check 減損單有無明細 
				qSQL2.append(" select sum(x.cnt) as chkCNT from (")
					 .append(" select count(1) as cnt from UNTLA_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTRF_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTBU_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTMP_REDUCEDETAIL where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTRT_REDUCEPROOF where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(" union all ")
					 .append(" select count(1) as cnt from UNTVP_REDUCEPROOF where enterorg=").append(Common.sqlChar(enterorg)).append(" and caseno=").append(Common.sqlChar(caseno))
					 .append(") as x ");
				rs2 = qDB2.querySQL(qSQL2.toString());
				
				chkCNT = rs2.next() ? rs2.getInt("chkCNT") : 0;
				if (chkCNT == 0) {
					throw new Exception("月結回復失敗," + prooftext + " 無明細資料");
				}
				//#end 
				
				//#region 土地減損回復
				
				// 土地減損單
				updateSQL.setLength(0);
				updateSQL.append(" update UNTLA_REDUCEPROOF set ")
						 .append(" verify='N', reducedate='' ")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 土地減損明細
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'N' ")
						  .append(" ,reducedate = '' ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTLA_REDUCEDETAIL a")
						  .append(" inner join UNTLA_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 土地主檔
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" area=b.area ,holdnume=b.holdnume ,holddeno=b.holddeno ,holdarea=b.holdarea ,bookvalue=b.bookvalue ,netvalue=b.netvalue ,datastate='1'")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" ,reducedate = '' ")
						  .append(" from UNTLA_LAND a ")
						  .append(" inner join UNTLA_REDUCEDETAIL b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" inner join UNTLA_REDUCEPROOF c on c.enterorg = b.enterorg and c.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and c.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and c.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 建物減損 
				
				// 建物減損單
				updateSQL.setLength(0);
				updateSQL.append(" update UNTBU_REDUCEPROOF set ")
						 .append(" verify='N', reducedate='' ")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 建物減損明細
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'N' ")
						  .append(" ,reducedate = '' ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTBU_REDUCEDETAIL a")
						  .append(" inner join UNTBU_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 建物主檔
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" carea=b.carea ,sarea=b.sarea ,holdnume=b.holdnume ,holddeno=b.holddeno ,holdarea=b.holdarea ,bookvalue=b.bookvalue ,netvalue=b.netvalue ,datastate='1'")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" ,reducedate = c.preenterdate ")
						  .append(" from UNTBU_BUILDING a ")
						  .append(" inner join UNTBU_REDUCEDETAIL b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" inner join UNTBU_REDUCEPROOF c on c.enterorg = b.enterorg and c.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and c.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and c.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 土改減損
				
				// 土改減損單
				updateSQL.setLength(0);
				updateSQL.append(" update UNTRF_REDUCEPROOF set ")
						 .append(" verify='N', reducedate='' ")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 土改減損明細
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'N' ")
						  .append(" ,reducedate = '' ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTRF_REDUCEDETAIL a")
						  .append(" inner join UNTRF_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 土改主檔
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" measure=b.measure ,bookvalue=b.bookvalue ,netvalue=b.netvalue ,datastate='1' ")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" ,reducedate = c.preenterdate ")
						  .append(" from UNTRF_ATTACHMENT a ")
						  .append(" inner join UNTRF_REDUCEDETAIL b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" inner join UNTRF_REDUCEPROOF c on c.enterorg = b.enterorg and c.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and c.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and c.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 動產減損 
				
				// 動產減損單
				updateSQL.setLength(0);
				updateSQL.append(" update UNTMP_REDUCEPROOF set ")
						 .append(" verify='N', reducedate='' ")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 動產減損明細
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" verify = 'N' ")
						  .append(" ,reducedate = '' ")
						  .append(" ,editid = ").append(Common.sqlChar(editid))
						  .append(" ,editdate = ").append(Common.sqlChar(editdate))
						  .append(" ,edittime = ").append(Common.sqlChar(edittime))
						  .append(" from UNTMP_REDUCEDETAIL a")
						  .append(" inner join UNTMP_REDUCEPROOF b on a.enterorg = b.enterorg and a.caseno = b.caseno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 動產明細
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						 .append(" bookamount = bookamount + b.adjustbookamount")
						 .append(" ,bookvalue = bookvalue + b.adjustbookvalue ")
						 .append(" ,netvalue = netvalue + b.adjustnetvalue")
						 .append(" ,reducedate = '' ,reducecause = '' ,reducecause1='', datastate = '1'")
						 .append(" from UNTMP_MOVABLEDETAIL a  ")
						 .append(" inner join UNTMP_REDUCEDETAIL b ")
						 .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						 .append(" inner join UNTMP_REDUCEPROOF c ")
						 .append(" on b.enterorg=c.enterorg and b.caseno=c.caseno ")
						 .append(" where c.enterorg=").append(Common.sqlChar(enterorg)).append(" and c.caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 動產主檔
				updateSQL.setLength(0);
				updateSQL.append(" update d set ")
						 .append(" bookamount = d.bookamount + b.adjustbookamount")
						 .append(" ,bookvalue = d.bookvalue + b.adjustbookvalue ")
						 .append(" ,netvalue = d.netvalue + b.adjustnetvalue")  // TODO 怪怪 怎麼會是adjsutbookvalue
						 .append(" ,datastate = '1' ")
						 .append(" from UNTMP_MOVABLE d ")
						 .append(" inner join UNTMP_MOVABLEDETAIL a ")
						 .append(" on a.enterorg=d.enterorg and a.ownership=d.ownership and a.differencekind=d.differencekind and a.propertyno=d.propertyno and a.lotno=d.lotno ")
						 .append(" inner join UNTMP_REDUCEDETAIL b ")
						 .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						 .append(" inner join UNTMP_REDUCEPROOF c ")
						 .append(" on b.enterorg=c.enterorg and b.caseno=c.caseno ")
						 .append(" where c.enterorg=").append(Common.sqlChar(enterorg)).append(" and c.caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				//#end 
				
				//#region 權利減損 
				updateSQL.setLength(0);
				updateSQL.append(" update UNTRT_REDUCEPROOF set ")
						 .append(" verify='N', reducedate='' ")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 權利主檔
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" bookvalue=b.bookvalue ,netvalue=b.netvalue ,reducedate='' ,reducecause='' ,reducecause1='' ,datastate='1' ")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" from UNTRT_ADDPROOF a ")
						  .append(" inner join UNTRT_REDUCEPROOF b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				//#end 
				
				//#region 有價證券減損
				// 有價證券減損單 
				updateSQL.setLength(0);
				updateSQL.append(" update UNTVP_REDUCEPROOF set ")
						 .append(" verify='N' ,adjustdate='' ")
						 .append(" ,editid=").append(Common.sqlChar(editid))
						 .append(" ,editdate=").append(Common.sqlChar(editdate))
						 .append(" ,edittime=").append(Common.sqlChar(edittime))
						 .append(" where enterorg=").append(Common.sqlChar(enterorg))
						 .append(" and caseno=").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				// 有價證券主檔
				updateSQL.setLength(0);
				updateSQL.append(" update a set ")
						  .append(" bookamount=b.bookamount ,bookvalue=b.bookvalue ,reducedate='' ,reducecause='' ,reducecause1='' ,datastate='1' ")
						  .append(" ,editid=").append(Common.sqlChar(editid))
						  .append(" ,editdate=").append(Common.sqlChar(editdate))
						  .append(" ,edittime=").append(Common.sqlChar(edittime))
						  .append(" from UNTVP_ADDPROOF a ")
						  .append(" inner join UNTVP_REDUCEPROOF b ")
						  .append(" on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno ")
						  .append(" where 1=1 ")
						  .append(" and b.enterorg = ").append(Common.sqlChar(enterorg))
						  .append(" and b.caseno = ").append(Common.sqlChar(caseno));
				this.db.excuteSQLNoAutoCommit(updateSQL.toString());
				
				//#end 
				
				
			}
		} finally {
			//#region clear all 
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e) { }
			}
			if (rs2 != null) {
				try {
					rs2.close();
					rs2 = null;
				} catch (Exception e) { }
			}
			if (qDB != null) {
				qDB.closeAll();
				qDB = null;
			}
			if (qDB2 != null) {
				qDB2.closeAll();
				qDB2 = null;
			}
			//#end 
		}
	}
	
	private void reImportNewDateForReduceProof() throws Exception {
		
		StringBuilder qSQL = new StringBuilder();
		ResultSet rs = null;
		
		UNTCH_Tools ut = new UNTCH_Tools();
		try {
			// 以土地減損單為主
			qSQL.append("select enterorg,caseno,proofyear,proofdoc,proofno,verify")
				.append(" from UNTLA_REDUCEPROOF where 1=1 ")
				.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
				.append(" and preenterdate like '").append(ut._transToCE_Year(this.YYYMM)).append("%'");
			
			String enterorg = this.getEnterOrg();
			rs = this.db.querySQL(qSQL.toString());
			while (rs.next()) {
				String caseno = Common.get(rs.getString("caseno"));
				this.doImportNewDataForReduceProof(enterorg, caseno);
			}
		} finally {
			//#region clear all 
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (Exception e) {
					//
				}
			}
			//#end 
		}
		
	}
	
	public void execCalculateBalanceInMonth(boolean verify) throws Exception{
		int getcut = 0;
		SQLCreator sc = new SQLCreator();
		UNTCH_Tools uctls = new UNTCH_Tools();
		String[] strKeys = null;
		String[] tables = obtainTables();
		this.db = null;
	
		try {
			getcut = getsKeySet().length;	//有勾選的list中資料數				
			boolean isValuable = true;
			
			// 問題單1341
			this.db = new Database();
			this.db.setAutoCommit(false);

			for(int i = 0; i < getcut; i++){
				strKeys = getsKeySet()[i].split(",");
				
				this.setEnterOrg(strKeys[0]);
				this.setDifferenceKind(strKeys[1]);
				YYYMM = strKeys[2];
				
				// 問題單1543, 依照機關參數取得區分別的折舊方法
				String checkDeprType = "";
				if("110".equals(this.getDifferenceKind())){			checkDeprType = "officialDeprMethod";
				}else if("111".equals(this.getDifferenceKind())){	checkDeprType = "escrowDeprMethod";
				}else if("120".equals(this.getDifferenceKind())){	checkDeprType = "fundDeprMethod";
				}else if("113".equals(this.getDifferenceKind())){	checkDeprType = "deferredDeprMethod";
				}else if("112".equals(this.getDifferenceKind())){	checkDeprType = "localDeprMethod";
				}
				String deprmethod = uctls._getDefaultValue(this.getEnterOrg(), checkDeprType);
				
				if(verify){
					calDatePlusOne();	
					
					if (!"01".equals(deprmethod)) {
						// 折舊入帳					
						UNTDP003F dp = new UNTDP003F();
						dp.setEnterOrg(strKeys[0]);
						dp.setDifferenceKind(strKeys[1]);
						dp.setDeprY(YYYMM.substring(0,3));
						dp.setDeprM(YYYMM.substring(3)); 
						dp.setVerify("N");					
						dp.doVerifyFromUntac003f(this.db);		
					}					
					
					// 預計移撥減損單入帳
					this.doActionForPreEnterReduceProof();
					
					insertToUNTGR_UNTGR009R(!isValuable);	
					insertToUNTGR_UNTGR009R(isValuable);	
					
					if(isEndOfYear(YYYMM)){
						insertToUNTGR_UNTGR011R(!isValuable);	
						insertToUNTGR_UNTGR011R(isValuable);											
					}
				}else{		
					deleteFromUNTGR_UNTGR009R(!isValuable);
					deleteFromUNTGR_UNTGR009R(isValuable);		

					if(isEndOfYear(YYYMM)){
						deleteFromUNTGR_UNTGR011R(!isValuable);
						deleteFromUNTGR_UNTGR011R(isValuable);	
					}		

					// 預計回撥減損單回復入帳
					this.doActionForPreEnterReduceProof2();
					
					if (!"01".equals(deprmethod)) {
						// 折舊回復入帳
						UNTDP004F dp = new UNTDP004F();
						dp.setEnterOrg(strKeys[0]);
						dp.setDifferenceKind(strKeys[1]);
						dp.setDeprY(YYYMM.substring(0,3));
						dp.setDeprM(YYYMM.substring(3)); 
						dp.setVerify("Y");					
						dp.doReVerifyFromUntac003f(db);
					}		
					
					// 預計回撥減損單重新帶入最新資料
					this.reImportNewDateForReduceProof();
					
					calDateMinusOne();
				}
				
				this.setClosing1YM(YYYMM);
				String updateSql = "";
				if(verify){
					updateSql = sc._obtainSQLforUpdate("UNTAC_CLOSINGPT", getPKMap(), this.getClosingRecordMap());
				}else{
					updateSql = sc._obtainSQLforUpdate("UNTAC_CLOSINGPT", getPKMap(), this.getRestorationRecordMap());
				}
				execData(updateSql);				
			}
			
			this.db.doCommit();
			
		} catch (Exception e) {
			this.db.doRollback();
			throw e;
		} finally {
			if (this.db != null) {
				this.db.closeAll();
				this.db = null;
			}
		}
	}
//=================================================
	public boolean isBigThen10402(String nowYYYMM){
		boolean result = false;
		String checkYYYMM = "10402";
		if(Integer.parseInt(nowYYYMM) <= Integer.parseInt(checkYYYMM)){
		}else{
			result = true;
		}
		if(startToCheckDataBigThen10402){
		}else{
			result = true;
		}
		return result;
	}
	
	public boolean isBigThen10401_forView(String nowYYYMM){
		boolean result = false;
		String checkYYYMM = "10401";
		if(Integer.parseInt(nowYYYMM) <= Integer.parseInt(checkYYYMM)){
		}else{
			result = true;
		}
		if(startToCheckDataBigThen10402){
		}else{
			result = true;
		}
		return result;
	}

	private boolean isEndOfYear(String nowYYYMM){
		boolean result = false;				
		if("12".equals(nowYYYMM.substring(3,5))){
			result = true;
		}
		return result;
	}
		
	//=================================================
	//若沒有此年月的資料，則自動建立table以及index
	private void checkTableIsExists(String[] tables) throws Exception{
		for(String s : tables){
			checkforTable(s);
		}			
	}
	
	private void checkforTable(String table) throws Exception{
		String sql = "select COUNT(*) as count from sys.sysobjects where name = '" + table + "_" + YYYMM + "'";
		String count = queryData_NoChange(sql);				
		
		if("0".equals(count)){
			sql = "select * into " + table + "_" + YYYMM + " from " + table;
			execData_NoChange(sql);
			
			List indexlist = obtainCreateIndexSQL(table,YYYMM);
			for(Object o : indexlist){
				execData_NoChange((String)o); 
			}
		}
	}
	
	//刪除資料
	private void deleteDataFromTable(String[] tables) throws Exception{
		for(String s : tables){
			subDeleteDataFromTable(s);
		}			
	}
	
		private void subDeleteDataFromTable(String table) throws Exception{
			String sql = "delete from " + table + "_" + YYYMM + 
						" where enterorg = " + Common.sqlChar(getEnterOrg()) +
						" and differencekind = " + Common.sqlChar(getDifferenceKind());
			execData_NoChange(sql);
		}
	
	//寫入資料
	private void insertDataIntoTable(String[] tables) throws Exception{
		for(String s : tables){
			subInsertDataIntoTable(s);
		}			
	}

	private void subInsertDataIntoTable(String table) throws Exception{
		String sql = "insert into " + table + "_" + YYYMM + " select * from " + table + 
				" where enterorg = " + Common.sqlChar(getEnterOrg()) +
				" and differencekind = " + Common.sqlChar(getDifferenceKind());
		execData_NoChange(sql);
	}
	
	//=================================================
	
	public ArrayList queryAll() throws Exception{
		Database db = null;
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("select a.*")
				.append(" ,(case when closing1YM > closing2YM then 'Y' else 'N' end) as checkDate")
				.append(" from UNTAC_CLOSINGPT a where 1=1")
				.append(" and enterorg = " + Common.sqlChar(this.getOrganID()))
				.append(" order by enterorg, differencekind");
			
			//============================================================
			log._execLogDebug(sql.toString());
			//============================================================
			
			db = new Database();
			ResultSet rs = db.querySQL(sql.toString());			
			UNTCH_Tools ut = new UNTCH_Tools();
			while (rs.next()){
				counter++;
				String rowArray[]=new String[9];
				rowArray[0] = Common.get(rs.getString("enterorg"));
				rowArray[1] = Common.get(rs.getString("differencekind"));
				rowArray[2] = ut._getDifferenceKindName(Common.get(rs.getString("differencekind"))); 
				rowArray[3] = ut._transToROC_Year(Common.get(rs.getString("closing1YM")));
				rowArray[4] = Common.get(rs.getString("closing1name")); 
				rowArray[5] = ut._transToROC_Year(Common.get(rs.getString("closing1date")));
				rowArray[6] = Common.get(rs.getString("restoration1name")); 
				rowArray[7] = ut._transToROC_Year(Common.get(rs.getString("restoration1date")));
				rowArray[8] = Common.get(rs.getString("checkDate"));
				
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}
		return objList;
		
	}
	

	//=================================================
	private void insertToUNTGR_UNTGR009R(boolean isValuable) throws Exception{
		UNTGR009R gr009r;
		UNTGR021R gr021r;
		Vector vector = null;
		String sql;
		String YYY = null;
		String MM = null;

		String YYYMMplusOne = calDatePlusOne(this.YYYMM);
		
		String[] ownerships = new String[]{"1","2","3","4","5"};
				
		for(String ownership : ownerships){					
			try{				
				if(isValuable){
					gr021r = new UNTGR021R();
					gr021r.setQ_enterOrg(this.getEnterOrg());
					gr021r.setQ_isorganmanager("N");
					gr021r.setQ_valuable("Y");
					gr021r.setQ_ownership(ownership);
					gr021r.setQ_differenceKind(this.getDifferenceKind());
					gr021r.setQ_reportType("1");
					gr021r.setQ_enterDateS(YYYMM + "01");
					gr021r.setQ_enterDateE(YYYMM + "31");
					gr021r.setQ_reportYear(YYYMM.substring(0,3));
					gr021r.setQ_reportMonth(YYYMM.substring(3,5));
					
					YYY = YYYMMplusOne.substring(0,3);
					MM = YYYMMplusOne.substring(3,5);
					
					vector = gr021r.getResultModel(this.db).getDataVector();
					
					sql = "delete from UNTGR_UNTGR009R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYY + "' and reportmonth = '" + MM + "' and valuable = 'Y' and ownership = '" + ownership + "' and differencekind = '" + this.getDifferenceKind() + "';";
					execData(sql);
				}else{
					gr009r = new UNTGR009R();
					gr009r.setQ_enterOrg(this.getEnterOrg());
					gr009r.setQ_isorganmanager("N");
					gr009r.setQ_valuable("N");
					gr009r.setQ_ownership(ownership);
					gr009r.setQ_differenceKind(this.getDifferenceKind());
					gr009r.setQ_reportType("1");
					gr009r.setQ_enterDateS(YYYMM + "01");
					gr009r.setQ_enterDateE(YYYMM + "31");
					gr009r.setQ_reportYear(YYYMM.substring(0,3));
					gr009r.setQ_reportMonth(YYYMM.substring(3,5));
					
					YYY = YYYMMplusOne.substring(0,3);
					MM = YYYMMplusOne.substring(3,5);
					
					vector = gr009r.getResultModel(this.db).getDataVector();
					
					sql = "delete from UNTGR_UNTGR009R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYY + "' and reportmonth = '" + MM + "' and valuable = 'N' and ownership = '" + ownership + "' and differencekind = '" + this.getDifferenceKind() + "';";
					execData(sql);
				}
				
				String[] data = splitData(vector.get(0).toString());
				
				//土地:「10」			
				sql = combinationSQL_forUNTGR009R(data,"10",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//土地改良物:「20」
				sql = combinationSQL_forUNTGR009R(data,"20",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//房屋建築及設備  辦公房屋:「31」
				sql = combinationSQL_forUNTGR009R(data,"31",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//房屋建築及設備  宿舍：「32」
				sql = combinationSQL_forUNTGR009R(data,"32",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//房屋建築及設備  其他：「33」
				sql = combinationSQL_forUNTGR009R(data,"33",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//機械及設備:「40」
				sql = combinationSQL_forUNTGR009R(data,"40",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//交通及運輸設備  船：「51」
				sql = combinationSQL_forUNTGR009R(data,"51",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//交通及運輸設備  飛機：「52」
				sql = combinationSQL_forUNTGR009R(data,"52",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//交通及運輸設備  汽（機）車：「53」
				sql = combinationSQL_forUNTGR009R(data,"53",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//交通及運輸設備  其他：「54」
				sql = combinationSQL_forUNTGR009R(data,"54",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//雜項設備  圖書：「61」
				sql = combinationSQL_forUNTGR009R(data,"61",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				if(isValuable){
					//雜項設備  博物：「62」(限「珍貴」)
					sql = combinationSQL_forUNTGR009R(data,"62",ownership,isValuable,YYYMMplusOne);
					execData(sql);
				}
				
				//雜項設備  其他：「63」
				sql = combinationSQL_forUNTGR009R(data,"63",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//有價證券：「70」(限「一般」)
				sql = combinationSQL_forUNTGR009R(data,"70",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//權利：「80」(限「一般」)
				sql = combinationSQL_forUNTGR009R(data,"80",ownership,isValuable,YYYMMplusOne);
				execData(sql);
				
				//總值：「T0」
				sql = combinationSQL_forUNTGR009R(data,"T0",ownership,isValuable,YYYMMplusOne);
				execData(sql);
			
				data = null;
				
			}catch(Exception e){
				throw e;
			}			
		}					
						
		sql = null;
		vector = null;
		gr009r = null;
		gr021r = null;
	}
	
	private String combinationSQL_forUNTGR009R(String[] data, String type, String ownership, boolean isValuable, String YYYMM){
		StringBuilder result = new StringBuilder();
		
		result.append("insert into UNTGR_UNTGR009R(enterorg, ownership, differencekind, valuable, reporttype, reportyear, reportmonth, reportseason, propertytype, oldamount, oldarea, oldbvsubtotal)values(")
				.append(Common.sqlChar(this.getEnterOrg())).append(",")
				.append(Common.sqlChar(ownership)).append(",")
				.append(Common.sqlChar(this.getDifferenceKind())).append(",");
		
		if(isValuable){		result.append("'Y'").append(",");
		}else{				result.append("'N'").append(",");
		}
				
		result.append(Common.sqlChar("1")).append(",")
				.append(Common.sqlChar(YYYMM.substring(0,3))).append(",")
				.append(Common.sqlChar(YYYMM.substring(3,5))).append(",")
				.append("null").append(",")
				.append(Common.sqlChar(type)).append(",");

		
		
		//土地:「10」
		if("10".equals(type)){
			MathTools mt = new MathTools();
			
			result.append(Common.sqlChar(replaceData(data[84]))).append(",")
					.append(Common.sqlChar(mt._multiplication_withBigDecimal(replaceData(data[85]),"10000"))).append(",")
					.append(Common.sqlChar(replaceData(data[86])));

			mt = null;
			
		//土地改良物:「20」
		}else if("20".equals(type)){
			result.append(Common.sqlChar(replaceData(data[87]))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(replaceData(data[88])));					
			
		//房屋建築及設備  辦公房屋:「31」
		}else if("31".equals(type)){
			result.append(Common.sqlChar(replaceData(data[89]))).append(",")
					.append(Common.sqlChar(replaceData(data[90]))).append(",")
					.append(Common.sqlChar(replaceData(data[94])));
			
		//房屋建築及設備  宿舍：「32」
		}else if("32".equals(type)){
			result.append(Common.sqlChar(replaceData(data[91]))).append(",")								
					.append(Common.sqlChar(replaceData(data[92]))).append(",")
					.append("null");
			
		//房屋建築及設備  其他：「33」
		}else if("33".equals(type)){
			result.append(Common.sqlChar(replaceData(data[93]))).append(",")								
					.append("null").append(",")
					.append("null");
			
		//機械及設備:「40」
		}else if("40".equals(type)){
			result.append(Common.sqlChar(replaceData(data[95]))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(replaceData(data[96])));	
			
		//交通及運輸設備  船：「51」
		}else if("51".equals(type)){
			result.append(Common.sqlChar(replaceData(data[97]))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(replaceData(data[101])));
			
		//交通及運輸設備  飛機：「52」
		}else if("52".equals(type)){
			result.append(Common.sqlChar(replaceData(data[98]))).append(",")								
					.append("null").append(",")
					.append("null");
			
		//交通及運輸設備  汽（機）車：「53」
		}else if("53".equals(type)){
			result.append(Common.sqlChar(replaceData(data[99]))).append(",")								
					.append("null").append(",")
					.append("null");
			
		//交通及運輸設備  其他：「54」
		}else if("54".equals(type)){
			result.append(Common.sqlChar(replaceData(data[100]))).append(",")								
					.append("null").append(",")
					.append("null");
			
		//雜項設備  圖書：「61」
		}else if("61".equals(type)){
			result.append(Common.sqlChar(replaceData(data[102]))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(replaceData(data[105])));
			
		//雜項設備  博物：「62」(限「珍貴」)
		}else if("62".equals(type)){
			result.append(Common.sqlChar(replaceData(data[103]))).append(",")
					.append("null").append(",")
					.append("null");
			
		//雜項設備  其他：「63」
		}else if("63".equals(type)){
			result.append(Common.sqlChar(replaceData(data[104]))).append(",")
					.append("null").append(",")
					.append("null");
			
		//有價證券：「70」(限「一般」)
		}else if("70".equals(type)){
			result.append(Common.sqlChar(replaceData(data[106]))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(replaceData(data[107])));
			
		//權利：「80」(限「一般」)
		}else if("80".equals(type)){
			result.append(Common.sqlChar(replaceData(data[108]))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(replaceData(data[109])));						
			
		//總值：「T0」
		}else if("T0".equals(type)){
			result.append(Common.sqlChar(replaceData(data[110]))).append(",")
					.append("null").append(",")
					.append(Common.sqlChar(replaceData(data[111])));
		}
		
		result.append(");");
		
		return result.toString();
	}

	private String replaceData(String s){
		return s.replace(",","");
	}
	
	protected void deleteFromUNTGR_UNTGR009R(boolean isValuable) throws Exception{
		String sql;
		
		String YYYMMplusOne = this.calDatePlusOne(YYYMM);
		
		String YYY = YYYMMplusOne.substring(0,3);
		String MM = YYYMMplusOne.substring(3,5);
		
		String[] ownerships = new String[]{"1","2","3","4","5"};

		for(String ownership : ownerships){					
			if(isValuable){							
				sql = "delete from UNTGR_UNTGR009R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYY + "' and reportmonth = '" + MM + "' and valuable = 'Y' and differencekind = '" + this.getDifferenceKind() + "' and ownership = '" + ownership + "'";
				execData(sql);
			}else{
				sql = "delete from UNTGR_UNTGR009R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYY + "' and reportmonth = '" + MM + "' and valuable = 'N' and differencekind = '" + this.getDifferenceKind() + "' and ownership = '" + ownership + "'";
				execData(sql);
			}
		}
	}

//=================================================
	private void insertToUNTGR_UNTGR011R(boolean isValuable) throws Exception{
		UNTGR011R gr011r;
		UNTGR030R gr030r;
		Vector vector = null;
		String sql = "";
		String YYYplusOne;
		YYYplusOne = calYYYPlusOne(YYYMM);
			
		String[] ownerships = new String[]{"1","2","3","4","5"};
		
		for(String ownership : ownerships){
			try{
				if(isValuable){
					gr030r = new UNTGR030R();
					gr030r.setQ_reportYear(YYYMM.substring(0, 3));
					gr030r.setQ_enterOrg(this.enterOrg);
					gr030r.setQ_ownership(ownership);
					gr030r.setQ_differenceKind(this.getDifferenceKind());
					gr030r.setQ_dataState("1");
					gr030r.setQ_verify("Y");
					gr030r.setQ_isorganmanager("N");
					gr030r.setQ_valuable("Y");
					
					try {
						DefaultTableModel model = gr030r.getResultModel(this.db); 
						if (model != null) {
							vector = model.getDataVector();
						}
					} catch (Exception e) {
						throw e;
					}
					
					sql = "delete from UNTGR_UNTGR011R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYYplusOne + "' and valuable = 'Y' and ownership = '" + ownership + "' and differencekind = '" + this.getDifferenceKind() + "';";
					execData(sql);
					
				}else{
					gr011r = new UNTGR011R();
					gr011r.setQ_reportYear(YYYMM.substring(0,3));
					gr011r.setQ_enterOrg(this.enterOrg);
					gr011r.setQ_ownership(ownership);
					gr011r.setQ_differenceKind(this.getDifferenceKind());
					gr011r.setQ_dataState("1");
					gr011r.setQ_verify("Y");
					gr011r.setQ_isorganmanager("N");
					gr011r.setQ_valuable("N");
					
					try {
						DefaultTableModel model = gr011r.getResultModel(this.db); 
						if (model != null) {
							vector = model.getDataVector();
						}
					} catch (Exception e) {
						throw e;
					}
					
					sql = "delete from UNTGR_UNTGR011R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYYplusOne + "' and valuable = 'N' and ownership = '" + ownership + "' and differencekind = '" + this.getDifferenceKind() + "';";
					execData(sql);
				}
			
				if(vector != null){
					java.util.Iterator iter = vector.iterator();
					while(iter.hasNext()){
						String[] data = splitData(iter.next().toString());
						
						String propertyno = (data[0] + data[1] + data[2] + data[3]).replace(" ", "");
						
						try {
							// 問題單1632: 過濾掉產報表時的小計、合計項目
							if (propertyno.matches("[^0-9]+")) {
								continue;
							}
							
							String oldamount;
							String oldarea;
							String oldbookvalue;
							if(isValuable){
								String[] splitstr = data[9].split("\n");
								oldamount = splitstr[0].replaceAll(",", "");
								oldarea = (splitstr.length > 1)?splitstr[1].replaceAll(",", ""):"0";
								oldbookvalue = data[10].replace("\n", "").replaceAll(",", "");
							}else{
								String[] splitstr = data[8].split("\n");
								oldamount = splitstr[0].replaceAll(",", "");
								// 問題單1636: 將報表中土地/10000顯示的area還原成平方公尺
								double temp = Common.getNumeric((splitstr.length > 1)?splitstr[1].replaceAll(",", ""):"0");
								if ("1".equals(data[0]) && temp != 0) {
									oldarea = new DecimalFormat("#.00").format(temp*10000);
								} else {
									oldarea = Common.get((Object)temp);
								}						
								oldbookvalue = data[9].replace("\n", "").replaceAll(",", "");											
							}
							
							String insertsql = "insert into UNTGR_UNTGR011R(enterorg, ownership, differencekind, valuable, reportyear, propertyno, oldamount, oldarea, oldbookvalue)values(" +
										Common.sqlChar(this.getEnterOrg()) + "," +
										Common.sqlChar(ownership) + "," +
										Common.sqlChar(this.getDifferenceKind()) + "," +
										Common.sqlChar(isValuable?"Y":"N") + "," +
										Common.sqlChar(YYYplusOne) + "," +
										Common.sqlChar(propertyno) + "," +
										Common.sqlChar(oldamount) + "," +
										Common.sqlChar(oldarea) + "," +
										Common.sqlChar(oldbookvalue) +
										");";

							execData(insertsql);
						} catch (Exception e) {
							throw e;
						}
					}
				}				
			}catch(Exception e){
				throw e;
			} finally {
				vector = null;
				gr011r = null;
				sql = null;
			}
		}
	}
	
	protected void deleteFromUNTGR_UNTGR011R(boolean isValuable) throws Exception{
		String sql = "";
		String YYYPlusOne;
		YYYPlusOne = calYYYPlusOne(YYYMM);				

		String[] ownerships = new String[]{"1","2","3","4","5"};
		
		for(String ownership : ownerships){
			try{
				if(isValuable){
					sql = "delete from UNTGR_UNTGR011R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYYPlusOne + "' and valuable = 'N' and ownership = '" + ownership + "' and differencekind = '" + this.getDifferenceKind() + "';";
					execData(sql);
					
				}else{
					sql = "delete from UNTGR_UNTGR011R where enterorg = '" + this.enterOrg + "' and reportyear = '" + YYYPlusOne + "' and valuable = 'Y' and ownership = '" + ownership + "' and differencekind = '" + this.getDifferenceKind() + "';";
					execData(sql);
				}		
			}catch(Exception e){
				throw e;
			}
		}
	}
	
//=================================================				
	private String[] splitData(String temp){
		String newtemp = new String(temp.substring(1,temp.length() - 1));
		return newtemp.split(", ");
	}
	

//=================================================
	//計算年月加一
	public void calDatePlusOne(){
		int yyy = Integer.parseInt(YYYMM.substring(0,3));
		int mm = Integer.parseInt(YYYMM.substring(3));
		
		mm += 1;

		if(mm > 12){
			yyy += 1;
			mm -= 12;
		}
		
		YYYMM = Common.formatFrontZero(String.valueOf(yyy), 3) + 
					Common.formatFrontZero(String.valueOf(mm), 2);
		
	}
	
	public String calDatePlusOne(String YYYMM){
		int yyy = Integer.parseInt(YYYMM.substring(0,3));
		int mm = Integer.parseInt(YYYMM.substring(3));
		
		mm += 1;

		if(mm > 12){
			yyy += 1;
			mm -= 12;
		}
		
		return Common.formatFrontZero(String.valueOf(yyy), 3) + 
					Common.formatFrontZero(String.valueOf(mm), 2);
		
	}
	
	/**
	 * 取得YYYMM 的YYY + 1
	 * @param YYYMM
	 * @return
	 */
	public String calYYYPlusOne(String YYYMM){
		int yyy = Integer.parseInt(YYYMM.substring(0,3));
		yyy += 1;
		return Common.formatFrontZero(String.valueOf(yyy), 3);			
	}
	
//=================================================
	//計算年月減一
	public void calDateMinusOne(){
		int yyy = Integer.parseInt(YYYMM.substring(0,3));
		int mm = Integer.parseInt(YYYMM.substring(3));
		
		mm -= 1;

		if(mm < 1){
			yyy -= 1;
			mm += 12;
		}
		
		YYYMM = Common.formatFrontZero(String.valueOf(yyy), 3) + 
					Common.formatFrontZero(String.valueOf(mm), 2);
		
	}		
	
	public String calDateMinusOne(String YYYMM){
		int yyy = Integer.parseInt(YYYMM.substring(0,3));
		int mm = Integer.parseInt(YYYMM.substring(3));
		
		mm -= 1;

		if(mm < 1){
			yyy -= 1;
			mm += 12;
		}
		
		return Common.formatFrontZero(String.valueOf(yyy), 3) + 
					Common.formatFrontZero(String.valueOf(mm), 2);
		
	}
	

//=================================================
	private String queryData_NoChange(String sql) throws Exception{
		Database db = new Database();
		ResultSet rs = null;
		String result = "";
		try{
			rs = db.querySQL_NoChange(sql);
			if(rs.next()){
				result = rs.getString("count");
			}
		}catch(Exception e){
			throw e;
		}finally{
			db.closeAll();
		}
		return result;
	}		
//=================================================
	private void execData(String sql) throws Exception{
		this.doExcuteSQL(new String[]{sql}, true);
	}
//=================================================
	private void execData_NoChange(String sql) throws Exception{
		this.doExcuteSQL(new String[]{sql}, false);
	}		
	//=================================================	    
	public void doExcuteSQL(String[] sql, boolean isChange) throws Exception{
		try {
			Statement stmt = db.getForwardStatement();
			for(int i=0; i<sql.length; i++){
			    if(null!=sql[i]&&sql[i].length()>0){
			    	if(isChange){
			    		sql[i] = db._transSQLFormat(sql[i].toString());
			    	}
			    	System.out.println("執行doExcuteSQL: "+sql[i]);
			        stmt.executeUpdate(sql[i]);
			    }
			}
//			stmt.close();
		} catch (Exception e) {
			throw e;	
		}
	}
	//=============================================================
	
	private Database db;
	
	private String isAdminManager;
	private String organID;
	private String userID;
	private String userName;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
	public String getOrganID() {return checkGet(organID);}
	public void setOrganID(String organID) {this.organID = checkSet(organID);}
	public String getUserID() {return checkGet(userID);}
	public void setUserID(String userID) {this.userID = checkSet(userID);}
	public String getUserName() {return checkGet(userName);}
	public void setUserName(String userName) {this.userName = checkSet(userName);}
	
	private String enterOrg;
	private String differenceKind;
	private String closing1YM;
	private String closing1ID;
	private String closing1Name;
	private String closing1Date;
	private String restoration1ID;
	private String restoration1Name;
	private String restoration1Date;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getClosing1YM() {return checkGet(closing1YM);}
	public void setClosing1YM(String closing1ym) {closing1YM = checkSet(closing1ym);}
	public String getClosing1ID() {return checkGet(closing1ID);}
	public void setClosing1ID(String closing1id) {closing1ID = checkSet(closing1id);}
	public String getClosing1Name() {return checkGet(closing1Name);}
	public void setClosing1Name(String closing1Name) {this.closing1Name = checkSet(closing1Name);}
	public String getClosing1Date() {return checkGet(closing1Date);}
	public void setClosing1Date(String closing1Date) {this.closing1Date = checkSet(closing1Date);}
	public String getRestoration1ID() {return checkGet(restoration1ID);}
	public void setRestoration1ID(String restoration1id) {restoration1ID = checkSet(restoration1id);}
	public String getRestoration1Name() {return checkGet(restoration1Name);}
	public void setRestoration1Name(String restoration1Name) {this.restoration1Name = checkSet(restoration1Name);}
	public String getRestoration1Date() {return checkGet(restoration1Date);}
	public void setRestoration1Date(String restoration1Date) {this.restoration1Date = checkSet(restoration1Date);}

	private String strKeySet[] = null;
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }
	
	private String toggleAll;
	public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
	public void setToggleAll(String s){ toggleAll=checkSet(s); }
	
	private String q_calyear;
	private String q_calmonth;
	public String getQ_calyear() {return checkGet(q_calyear);}
	public void setQ_calyear(String q_calyear) {this.q_calyear = checkSet(q_calyear);}
	public String getQ_calmonth() {return checkGet(q_calmonth);}
	public void setQ_calmonth(String q_calmonth) {this.q_calmonth = checkSet(q_calmonth);}
	
	private String YYYMM;	
	public void setYYYMM(String s){this.YYYMM = s;}
	
	private boolean startToCheckDataBigThen10402;	
	public void isStartToCheckDataBigThen10402() {this.startToCheckDataBigThen10402 = true;}
	
	
	//=================================================
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("DifferenceKind", getDifferenceKind());
		
		return map;
	}
	
	private Map getClosingRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
				
		map.put("Closing1YM", ut._transToCE_Year(getClosing1YM()));
		map.put("Closing1ID", getClosing1ID());
		map.put("Closing1Name", getClosing1Name());
		map.put("Closing1Date", ut._transToCE_Year(getClosing1Date()));
		
		return map;
	}
	
	private Map getRestorationRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
				
		map.put("Closing1YM", ut._transToCE_Year(getClosing1YM()));
		map.put("Restoration1ID", getRestoration1ID());
		map.put("Restoration1Name", getRestoration1Name());
		map.put("Restoration1Date", ut._transToCE_Year(getRestoration1Date()));
		
		return map;
	}

	//=================================================
}