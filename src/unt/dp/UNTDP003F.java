package unt.dp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.SuperBean2;

public class UNTDP003F extends SuperBean2{
	
	private Database db;

	private String enterOrg;
	private String differenceKind;
	private String deprY;
	private String deprM;
	private String verify;
	private String ownership;
	private String propertyno;
	private String serialno;
	private String serialno1;
	private String lotno;
	private String deprunitcb;
	private String monthdepr;
	private String scaledmonthdepr;
	private String deprYM;
	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getDifferenceKind(){ return checkGet(differenceKind); }
	public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
	public String getDeprY(){ return checkGet(deprY); }
	public void setDeprY(String s){ deprY=checkSet(s); }
	public String getDeprM(){ return checkGet(deprM); }
	public void setDeprM(String s){ deprM=checkSet(s); }
	public String getVerify(){ return checkGet(verify); }
	public void setVerify(String s){ verify=checkSet(s); }
	public String getOwnership() {return checkGet(ownership);	}
	public void setOwnership(String s) {ownership = checkSet(s);	}
	public String getPropertyno() {return checkGet(propertyno);}
	public void setPropertyno(String s) {propertyno = checkSet(s);	}
	public String getSerialno() {return checkGet(serialno);}
	public void setSerialno(String s) {serialno = checkSet(s);}
	public String getSerialno1() {return checkGet(serialno1);}
	public void setSerialno1(String s) {serialno1 = checkSet(s);}
	public String getLotno() {return checkGet(lotno);}
	public void setLotno(String s) {lotno = checkSet(s);}
	public String getDeprunitcb() {return checkGet(deprunitcb);}
	public void setDeprunitcb(String s) {deprunitcb = checkSet(s);}
	public String getMonthdepr() {return checkGet(monthdepr);}
	public void setMonthdepr(String s) {monthdepr = checkSet(s);	}
	public String getScaledmonthdepr() {return checkGet(scaledmonthdepr);}
	public void setScaledmonthdepr(String s) {scaledmonthdepr = checkSet(s);	}
	public String getDeprYM(){ return checkGet(deprYM); }
	public void setDeprYM(String s){ deprYM=checkSet(s); }
	
	public boolean checkUpdateError(){
//		UNTCH_Tools ul = new UNTCH_Tools();
		String DeprYM = getDeprY()+Common.formatFrontZero(getDeprM(),2);
		boolean result = true;
	if( checkClosingYMfromUNTAC_CLOSINGPT(DeprYM,getEnterOrg(),getDifferenceKind())){
			result = false;
			setErrorMsg("折舊年月需為最大月結年月＋１!!");
		}
		return result;
	}
	
	synchronized public void doVerifyFromUntac003f(Database db) throws Exception{
		UNTCH_Tools ul = new UNTCH_Tools();
		ResultSet rs = null;
		ResultSet rs2 = null;
		String DeprYM = ul._transToCE_Year(getDeprY()+Common.formatFrontZero(getDeprM(),2));
		try {
			StringBuilder sqlCount = new StringBuilder("SELECT (select count(*) from UNTDP_MONTHDEPR where 1=1 ");
			sqlCount.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			   	.append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
			   	.append(" and deprym = ").append(Common.sqlChar(DeprYM))
			   	.append(" and verify = ").append(Common.sqlChar(this.getVerify()))
			   	.append(" ) as a , COUNT(*) as count FROM (")
				.append(" SELECT enterorg, ownership, differencekind, propertyno, serialno, datastate FROM UNTBU_BUILDING")
				.append(" where datastate = '1' ")
				.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
				.append(" UNION ALL")
				.append(" SELECT enterorg, ownership, differencekind, propertyno, serialno, datastate FROM UNTRF_ATTACHMENT")
				.append(" where datastate = '1' ")
				.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
				.append(" UNION ALL")
				.append(" SELECT enterorg, ownership, differencekind, propertyno, serialno, datastate FROM UNTMP_MOVABLEDETAIL")
				.append(" where datastate = '1' ")
				.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
				.append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
				.append(" )as x ");
			rs = db.querySQL_NoChange(sqlCount.toString());
			this.db = db;
			rs.next();
			//折舊月報檔有資料或是資料皆已減損
			if (rs.getInt("a") > 0 || "112".equals(this.getDifferenceKind()) || rs.getInt("count") == 0) {
				UpdateRF();
				UpdateBU();
				UpdateMPDetail();
				UpdateMP();
				UpdateRT();
				UpdateDP();
				System.out.println("折舊入帳完成!");
			} else {
				throw new Exception(ul._getDifferenceKindName(this.getDifferenceKind())+" 折舊年月無資料!");	
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void printAddProof1() throws Exception{
		QueryMDTable();
	}
	
	synchronized public void QueryMDTable() throws Exception{
		UNTCH_Tools ul = new UNTCH_Tools();
//		UNTDP003F obj = this;
		this.db = new Database();
		ResultSet rs = null;
		//Connection conn=db.getConnection();
		String DeprYM = ul._transToCE_Year(getDeprY()+Common.formatFrontZero(getDeprM(),2));
		try{
			StringBuilder sql = new StringBuilder("select 1 from UNTDP_MONTHDEPR where 1=1 ");
			sql.append(" and enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			   .append(" and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
			   .append(" and deprym = ").append(Common.sqlChar(DeprYM))
			   .append(" and verify = ").append(Common.sqlChar(this.getVerify()));
			
			rs = db.querySQL_NoChange(sql.toString());			
			if (rs.next()) {
				UpdateRF();
				UpdateBU();
				UpdateMPDetail();
				UpdateMP();
				UpdateRT();
				UpdateDP();
				setErrorMsg("折舊入帳完成!!!!!!!!");
			} else {
				setErrorMsg("此折舊年月無資料!!!!!!!!");	
			}
//			PreparedStatement ps=conn.prepareStatement(" select a.enterorg,a.ownership,a.differencekind,a.propertyno,a.serialno,a.serialno1,a.deprym, " +
//					" a.lotno,a.deprunitcb,a.monthdepr,a.scaledmonthdepr,a.enterdate " +
//					" from UNTDP_MONTHDEPR a where a.enterorg=? and a.differencekind=?" +
//					" and a.deprym=? and verify=?");
//			ps.setString(1,getEnterOrg());
//			ps.setString(2,getDifferenceKind());
//			ps.setString(3,DeprYM);
//			ps.setString(4,getVerify());

//			rs=ps.executeQuery();
//			while(rs.next()){
//				obj.setEnterOrg(rs.getString("enterorg"));
//				obj.setOwnership(rs.getString("ownership"));
//				obj.setDifferenceKind(rs.getString("differencekind"));
//				obj.setPropertyno(rs.getString("propertyno"));
//				obj.setSerialno(rs.getString("serialno"));
//				obj.setSerialno1(rs.getString("serialno1"));
//				obj.setLotno(rs.getString("lotno"));
//				obj.setDeprunitcb(rs.getString("deprunitcb"));
//				obj.setMonthdepr(rs.getString("monthdepr"));
//				obj.setScaledmonthdepr(rs.getString("scaledmonthdepr"));
//				obj.setDeprYM(rs.getString("deprym"));
//				if(rs.getString("propertyno").substring(0, 3).equals("111")) {
//					
//				} else if(rs.getString("propertyno").substring(0, 1).equals("2")) {
//					
//				} else if(rs.getString("propertyno").substring(0, 1).equals("3") || rs.getString("propertyno").substring(0, 1).equals("4") || rs.getString("propertyno").substring(0, 1).equals("5")) {
//					
////					UpdateMP();
//				}
//			}
//			setErrorMsg("折舊入帳完成!!!!!!!!");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.db.closeAll();
		}	
	}
	
	public void UpdateRF() throws Exception{
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP003F obj = this;
//		Database db = new Database();
//		ResultSet rs = null;
		Connection conn=db.getConnection();
		PreparedStatement ps= null;
//		int netvalueI = 0;
//		int accumdeprI = 0;
		String DeprYM = ul._transToCE_Year(getDeprY()+Common.formatFrontZero(getDeprM(),2));
		try{
			ps=conn.prepareStatement(" update a " +
					" set " +
					" a.netvalue = a.netvalue - b.monthdepr, "+
					" a.accumdepr = a.accumdepr + b.monthdepr, "+
					" a.apportionmonth = (case when a.apportionendym>=b.deprym then a.apportionmonth-1 else a.apportionmonth end)"+
					" FROM "+
					" UNTRF_ATTACHMENT a "+
					" INNER JOIN (select distinct enterorg,ownership,differencekind,propertyno,serialno,monthdepr,verify,deprym from UNTDP_MONTHDEPR) b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,3) = '111' "+
					" WHERE "+
					" a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify = ? ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
			
			
			//是否重新計算折舊(UNTDP_MonthDepr.recalculated)為「Y:是」則需要重新計算主檔
			ps=conn.prepareStatement(" update a " +
					" set " +
					" a.depramount = a.netvalue - a.scrapvalue, " +
					" a.apportionmonth = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') < 0 THEN 0 ELSE DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') END, " +
					" a.monthdepr = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE FLOOR((a.netvalue - a.scrapvalue) / (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01'))) END, " +
					" a.monthdepr1 = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE a.netvalue - a.scrapvalue - (FLOOR((a.netvalue - a.scrapvalue) / DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')) * (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')-1)) END " +
					" FROM "+
					" UNTRF_ATTACHMENT a "+
					" INNER JOIN UNTDP_MONTHDEPR b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,3) = '111' "+
					" WHERE b.recalculated = 'Y' "+
//					" a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify = ? and b.recalculated = 'Y' ");
					" and a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? and isnull(b.oldmonthdepr,0) <> isnull(b.monthdepr,0) ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
//    	ps=conn.prepareStatement(" select a.netvalue ,a.accumdepr " +
//								 " from UNTRF_ATTACHMENT a where a.enterorg=? and a.differencekind=?" +
//								 " and a.ownership=? and a.propertyno=? and a.serialno=?");
//        ps.setString(1,obj.getEnterOrg());
//		ps.setString(2,obj.getDifferenceKind());
//		ps.setString(3,obj.getOwnership());
//		ps.setString(4,obj.getPropertyno());
//		ps.setString(5,obj.getSerialno());
//		
//		rs=ps.executeQuery();
//		while(rs.next()){
			
//			if(obj.getDeprunitcb().equals("Y")){
//			
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getScaledmonthdepr());
//				accumdeprI =  Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getScaledmonthdepr());
//			}
//			else if(obj.getDeprunitcb().equals("N")){
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getMonthdepr());
//				accumdeprI = Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getMonthdepr());
//			}
//			ps=conn.prepareStatement(" update UNTRF_ATTACHMENT  set netvalue=? ,accumdepr=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=?");
//			ps.setString(1,String.valueOf(netvalueI));
//			ps.setString(2,String.valueOf(accumdeprI));
//			ps.setString(3,obj.getEnterOrg());
//			ps.setString(4,obj.getDifferenceKind());
//			ps.setString(5,obj.getOwnership());
//			ps.setString(6,obj.getPropertyno());
//			ps.setString(7,obj.getSerialno());
//			ps.executeUpdate();
//		
//			ps=conn.prepareStatement(" update UNTDP_MONTHDEPR  set verify=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=? and deprym=? and serialno1=? and verify=? ");
//			ps.setString(1,"Y");
//			ps.setString(2,obj.getEnterOrg());
//			ps.setString(3,obj.getDifferenceKind());
//			ps.setString(4,obj.getOwnership());
//			ps.setString(5,obj.getPropertyno());
//			ps.setString(6,obj.getSerialno());
//			ps.setString(7,obj.getDeprYM());
//			ps.setString(8,obj.getSerialno1());
//			ps.setString(9,obj.getVerify());
//			ps.executeUpdate();
//		}
		}catch (Exception e) {
			throw e;
//		} finally {
//			db.closeAll();
		}	
	}

	public void UpdateBU() throws Exception{
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP003F obj = this;
//		Database db = new Database();
//		ResultSet rs = null;
		Connection conn=db.getConnection();
		PreparedStatement ps= null;
//		int netvalueI = 0;
//		int accumdeprI = 0;
		String DeprYM = ul._transToCE_Year(getDeprY()+Common.formatFrontZero(getDeprM(),2));
		try{
			ps=conn.prepareStatement(" update a " +
					" set " +
					" a.netvalue = a.netvalue - b.monthdepr, "+
					" a.accumdepr = a.accumdepr + b.monthdepr, "+
					" a.apportionmonth = (case when a.apportionendym>=b.deprym then a.apportionmonth-1 else a.apportionmonth end)"+
					" FROM "+
					" UNTBU_BUILDING a "+
					" INNER JOIN (select distinct enterorg,ownership,differencekind,propertyno,serialno,monthdepr,verify,deprym from UNTDP_MONTHDEPR) b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,1) = '2' "+
					" WHERE "+
					" a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
			
			
			//是否重新計算折舊(UNTDP_MonthDepr.recalculated)為「Y:是」則需要重新計算主檔
			
			ps=conn.prepareStatement(" update a " +
					" set " +
					" a.depramount = a.netvalue - a.scrapvalue, " +
					" a.apportionmonth = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') < 0 THEN 0 ELSE DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') END, " +
					" a.monthdepr = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE FLOOR((a.netvalue - a.scrapvalue) / (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01'))) END, " +
					" a.monthdepr1 = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE a.netvalue - a.scrapvalue - (FLOOR((a.netvalue - a.scrapvalue) / DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')) * (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')-1)) END " +
					" FROM "+
					" UNTBU_BUILDING a "+
					" INNER JOIN UNTDP_MONTHDEPR b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,1) = '2' "+
					" WHERE b.recalculated = 'Y' "+
//					" a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? and b.recalculated = 'Y' ");
					" and a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? and isnull(b.oldmonthdepr,0) <> isnull(b.monthdepr,0) ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
//    	ps=conn.prepareStatement(" select a.netvalue ,a.accumdepr " +
//								 " from UNTBU_BUILDING a where a.enterorg=? and a.differencekind=?" +
//								 " and a.ownership=? and a.propertyno=? and a.serialno=?");
//        ps.setString(1,obj.getEnterOrg());
//		ps.setString(2,obj.getDifferenceKind());
//		ps.setString(3,obj.getOwnership());
//		ps.setString(4,obj.getPropertyno());
//		ps.setString(5,obj.getSerialno());
//		
//		rs=ps.executeQuery();
//		while(rs.next()){
			
//			if(obj.getDeprunitcb().equals("Y")){
//			
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getScaledmonthdepr());
//				accumdeprI =  Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getScaledmonthdepr());
//			}
//			else if(obj.getDeprunitcb().equals("N")){
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getMonthdepr());
//				accumdeprI = Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getMonthdepr());
//			}
//			ps=conn.prepareStatement(" update UNTBU_BUILDING  set netvalue=? ,accumdepr=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=?");
//			ps.setString(1,String.valueOf(netvalueI));
//			ps.setString(2,String.valueOf(accumdeprI));
//			ps.setString(3,obj.getEnterOrg());
//			ps.setString(4,obj.getDifferenceKind());
//			ps.setString(5,obj.getOwnership());
//			ps.setString(6,obj.getPropertyno());
//			ps.setString(7,obj.getSerialno());
//			ps.executeUpdate();
//		
//			ps=conn.prepareStatement(" update UNTDP_MONTHDEPR  set verify=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=? and deprym=? and serialno1=? and verify=? ");
//			ps.setString(1,"Y");
//			ps.setString(2,obj.getEnterOrg());
//			ps.setString(3,obj.getDifferenceKind());
//			ps.setString(4,obj.getOwnership());
//			ps.setString(5,obj.getPropertyno());
//			ps.setString(6,obj.getSerialno());
//			ps.setString(7,obj.getDeprYM());
//			ps.setString(8,obj.getSerialno1());
//			ps.setString(9,obj.getVerify());
//			ps.executeUpdate();
//		}
		}catch (Exception e) {
			throw e;
//		} finally {
//			db.closeAll();
		}	
	}
	
	public void UpdateMPDetail() throws Exception{
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP003F obj = this;
//		Database db = new Database();
//		ResultSet rs = null;
		Connection conn=db.getConnection();
		PreparedStatement ps= null;
//		int netvalueI = 0;
//		int accumdeprI = 0;
		String DeprYM = ul._transToCE_Year(getDeprY()+Common.formatFrontZero(getDeprM(),2));
		try{
			ps=conn.prepareStatement(" update a " +
					" set " +
					" a.netvalue = a.netvalue - b.monthdepr, "+
					" a.accumdepr = a.accumdepr + b.monthdepr, "+
					" a.apportionmonth = (case when a.apportionendym>=b.deprym then a.apportionmonth-1 else a.apportionmonth end)"+
					" FROM "+
					" UNTMP_MOVABLEDETAIL a "+
					" INNER JOIN (select distinct enterorg,ownership,differencekind,propertyno,serialno,monthdepr,verify,deprym from UNTDP_MONTHDEPR) b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,1) in ('3','4','5') "+
					" WHERE "+
					" a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
			
			//是否重新計算折舊(UNTDP_MonthDepr.recalculated)為「Y:是」則需要重新計算主檔
			ps=conn.prepareStatement(" update a " +
					" set " +
					" a.depramount = a.netvalue - a.scrapvalue, " +
					" a.apportionmonth = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') < 0 THEN 0 ELSE DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') END, " +
					" a.monthdepr = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE FLOOR((a.netvalue - a.scrapvalue) / (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01'))) END, " +
					" a.monthdepr1 = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE a.netvalue - a.scrapvalue - (FLOOR((a.netvalue - a.scrapvalue) / DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')) * (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')-1)) END " +
					" FROM "+
					" UNTMP_MOVABLEDETAIL a "+
					" INNER JOIN UNTDP_MONTHDEPR b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,1) in ('3','4','5') "+
					" WHERE b.recalculated = 'Y' "+
//					" a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? and b.recalculated = 'Y' ");
					" and a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? and isnull(b.oldmonthdepr,0) <> isnull(b.monthdepr,0) ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
	//    	ps=conn.prepareStatement(" select a.netvalue ,a.accumdepr " +
	//								 " from UNTMP_MOVABLEDETAIL a where a.enterorg=? and a.differencekind=?" +
	//								 " and a.ownership=? and a.propertyno=? and a.serialno=?");
	//        ps.setString(1,obj.getEnterOrg());
	//		ps.setString(2,obj.getDifferenceKind());
	//		ps.setString(3,obj.getOwnership());
	//		ps.setString(4,obj.getPropertyno());
	//		ps.setString(5,obj.getSerialno());
	//		rs=ps.executeQuery();
	//		while(rs.next()){
				
//			if(obj.getDeprunitcb().equals("Y")){
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getScaledmonthdepr());
//				accumdeprI =  Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getScaledmonthdepr());
//			}
//			else if(obj.getDeprunitcb().equals("N")){
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getMonthdepr());
//				accumdeprI = Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getMonthdepr());
//			}
//			ps=conn.prepareStatement(" update UNTMP_MOVABLEDETAIL  set netvalue=? ,accumdepr=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=?");
//			ps.setString(1,String.valueOf(netvalueI));
//			ps.setString(2,String.valueOf(accumdeprI));
//			ps.setString(3,obj.getEnterOrg());
//			ps.setString(4,obj.getDifferenceKind());
//			ps.setString(5,obj.getOwnership());
//			ps.setString(6,obj.getPropertyno());
//			ps.setString(7,obj.getSerialno());
//			ps.executeUpdate();
//			
//			ps=conn.prepareStatement(" update UNTDP_MONTHDEPR  set verify=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=? and deprym=? and serialno1=? and verify=? ");
//			ps.setString(1,"Y");
//			ps.setString(2,obj.getEnterOrg());
//			ps.setString(3,obj.getDifferenceKind());
//			ps.setString(4,obj.getOwnership());
//			ps.setString(5,obj.getPropertyno());
//			ps.setString(6,obj.getSerialno());
//			ps.setString(7,obj.getDeprYM());
//			ps.setString(8,obj.getSerialno1());
//			ps.setString(9,obj.getVerify());
//			ps.executeUpdate();
	//		}
		}catch (Exception e) {
			throw e;
//		} finally {
//			db.closeAll();
		}	
	}
	
	public void UpdateMP() throws Exception{
			
		UNTDP003F obj = this;
//		Database db = new Database();
//		ResultSet rs = null;
		Connection conn=db.getConnection();
		PreparedStatement ps= null;
//		int netvalueI = 0;
		try{
			String tablename = "UNTMP_MOVABLE";
			String detail_tablename = "UNTMP_MOVABLEDETAIL";
			String inputEnterOrg = obj.getEnterOrg();
			
			StringBuffer updateSql = new StringBuffer("");
			updateSql.append(" UPDATE m ");
			updateSql.append(" SET  ");
			updateSql.append(" m.datastate='2', ");
			updateSql.append(" m.bookvalue = 0, ");
			if (!"UNTNE_NONEXP".endsWith(tablename)) {
				updateSql.append(" m.netvalue = 0, ");
			}
			updateSql.append(" m.bookamount = 0 ");
			updateSql.append(" FROM ");
			updateSql.append( tablename + " m ");
			updateSql.append(" WHERE ");
			updateSql.append(" m.enterorg = '").append(inputEnterOrg).append("';");
			
			updateSql.append(" UPDATE m ");
			updateSql.append(" SET m.datastate = '1' , ");
			updateSql.append(" m.bookvalue = (SELECT SUM(d.bookvalue) FROM " + detail_tablename + " d WHERE   m.enterorg = d.enterorg AND m.ownership = d.ownership AND m.lotno = d.lotno AND m.propertyno = d.propertyno AND m.differencekind = d.differencekind) , ");
			if (!"UNTNE_NONEXP".endsWith(tablename)) {
				updateSql.append(" m.netvalue = (SELECT SUM(d.netvalue) FROM " + detail_tablename + " d WHERE   m.enterorg = d.enterorg AND m.ownership = d.ownership AND m.lotno = d.lotno AND m.propertyno = d.propertyno AND m.differencekind = d.differencekind) , ");
			}
			updateSql.append(" m.bookamount = (SELECT SUM(d.bookamount) FROM " + detail_tablename + " d WHERE   m.enterorg = d.enterorg AND m.ownership = d.ownership AND m.lotno = d.lotno AND m.propertyno = d.propertyno AND m.differencekind = d.differencekind) ");
			updateSql.append(" FROM " + tablename + " m ");
			updateSql.append(" WHERE  m.enterorg ='").append(inputEnterOrg).append("'");
			updateSql.append(" AND EXISTS ( SELECT 1 ");
			updateSql.append(" FROM " + detail_tablename + " d ");
			updateSql.append(" WHERE  m.enterorg = d.enterorg ");
			updateSql.append(" AND m.ownership = d.ownership ");
			updateSql.append(" AND m.lotno = d.lotno ");
			updateSql.append(" AND m.propertyno = d.propertyno ");
			updateSql.append(" AND m.differencekind = d.differencekind ");
			updateSql.append(" AND d.datastate = '1' ); ");
			ps=conn.prepareStatement(updateSql.toString());
			ps.executeUpdate();
			
			
//			ps=conn.prepareStatement(" select a.netvalue  " +
//					" from UNTMP_MOVABLE a where a.enterorg=? and a.differencekind=?" +
//					" and a.ownership=? and a.propertyno=? and a.lotno=?");
//			ps.setString(1,obj.getEnterOrg());
//			ps.setString(2,obj.getDifferenceKind());
//			ps.setString(3,obj.getOwnership());
//			ps.setString(4,obj.getPropertyno());
//			ps.setString(5,obj.getLotno());
//			
//			rs=ps.executeQuery();
//			while(rs.next()){
//				
//				if(obj.getDeprunitcb().equals("Y")){
//					netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getScaledmonthdepr());
//				}
//				else if(obj.getDeprunitcb().equals("N")){
//					netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getMonthdepr());
//				}
//				ps=conn.prepareStatement(" update UNTMP_MOVABLE  set netvalue=?  " +
//						" where enterorg=? and differencekind=?" +
//						" and ownership=? and propertyno=? and lotno=?");
//				ps.setString(1,String.valueOf(netvalueI));
//				ps.setString(2,obj.getEnterOrg());
//				ps.setString(3,obj.getDifferenceKind());
//				ps.setString(4,obj.getOwnership());
//				ps.setString(5,obj.getPropertyno());
//				ps.setString(6,obj.getLotno());
//				ps.executeUpdate();
//			
//				ps=conn.prepareStatement(" update UNTDP_MONTHDEPR  set verify=? " +
//						" where enterorg=? and differencekind=?" +
//						" and ownership=? and propertyno=? and lotno=?");
//				ps.setString(1,"Y");
//				ps.setString(2,obj.getEnterOrg());
//				ps.setString(3,obj.getDifferenceKind());
//				ps.setString(4,obj.getOwnership());
//				ps.setString(5,obj.getPropertyno());
//				ps.setString(6,obj.getLotno());
//				ps.executeUpdate();
//			}
		 }catch (Exception e) {
				throw e;
//		} finally {
//			db.closeAll();
		}	
	}
	
	private void UpdateRT() throws Exception {
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP003F obj = this;
//		Database db = new Database();
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		String DeprYM = ul._transToCE_Year(getDeprY()+Common.formatFrontZero(getDeprM(),2));
		try{
			ps = conn.prepareStatement(" update a set " +
									   " a.netvalue = a.netvalue - b.monthdepr, "+
									   " a.accumdepr = a.accumdepr + b.monthdepr, "+
									   " a.apportionmonth = (case when a.apportionendym>=b.deprym then a.apportionmonth-1 else a.apportionmonth end)"+
									   " FROM  UNTRT_ADDPROOF a "+
									   " INNER JOIN (select distinct enterorg,ownership,differencekind,propertyno,serialno,monthdepr,verify,deprym from UNTDP_MONTHDEPR) b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,1) = '8' "+
									   " WHERE "+
									   " a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
			
			
			//是否重新計算折舊(UNTDP_MonthDepr.recalculated)為「Y:是」則需要重新計算主檔
			ps=conn.prepareStatement(" update a " +
					" set " +
					" a.depramount = a.netvalue - a.scrapvalue, " +
					" a.apportionmonth = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') < 0 THEN 0 ELSE DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') END, " +
					" a.monthdepr = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE FLOOR((a.netvalue - a.scrapvalue) / (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01'))) END, " +
					" a.monthdepr1 = CASE WHEN DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01') <= 0 THEN 0 ELSE a.netvalue - a.scrapvalue - (FLOOR((a.netvalue - a.scrapvalue) / DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')) * (DATEDIFF(mm," + Common.sqlChar(DeprYM + "01")+ ",a.apportionendym + '01')-1)) END " +
					" FROM "+
					" UNTRT_ADDPROOF a "+
					" INNER JOIN UNTDP_MONTHDEPR b ON b.enterorg = a.enterorg AND b.ownership = a.ownership AND b.differencekind = a.differencekind AND b.propertyno = a.propertyno AND b.serialno = a.serialno AND SUBSTRING(b.propertyno,1,1) = '8' "+
					" WHERE b.recalculated = 'Y' "+
//					" a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? and b.recalculated = 'Y' ");
					" and a.enterorg = ? and a.differencekind = ? and b.deprym = ? and b.verify=? and isnull(b.oldmonthdepr,0) <> isnull(b.monthdepr,0) ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
//		} finally {
//			db.closeAll();
		}	
	}
	
	public void UpdateDP() throws Exception{
		UNTCH_Tools ul = new UNTCH_Tools();
		UNTDP003F obj = this;
//		Database db = new Database();
//		ResultSet rs = null;
		Connection conn=db.getConnection();
		PreparedStatement ps= null;
//		int netvalueI = 0;
//		int accumdeprI = 0;
		String DeprYM = ul._transToCE_Year(getDeprY()+Common.formatFrontZero(getDeprM(),2));
		try{
			ps=conn.prepareStatement(" update UNTDP_MONTHDEPR " +
					" set " +
					" verify = 'Y' "+
					" WHERE "+
					" enterorg = ? and differencekind = ? and deprym = ? and verify=? ");
			ps.setString(1,obj.getEnterOrg());
			ps.setString(2,getDifferenceKind());
			ps.setString(3,DeprYM);
			ps.setString(4,getVerify());
			ps.executeUpdate();
//    	ps=conn.prepareStatement(" select a.netvalue ,a.accumdepr " +
//								 " from UNTRF_ATTACHMENT a where a.enterorg=? and a.differencekind=?" +
//								 " and a.ownership=? and a.propertyno=? and a.serialno=?");
//        ps.setString(1,obj.getEnterOrg());
//		ps.setString(2,obj.getDifferenceKind());
//		ps.setString(3,obj.getOwnership());
//		ps.setString(4,obj.getPropertyno());
//		ps.setString(5,obj.getSerialno());
//		
//		rs=ps.executeQuery();
//		while(rs.next()){
			
//			if(obj.getDeprunitcb().equals("Y")){
//			
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getScaledmonthdepr());
//				accumdeprI =  Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getScaledmonthdepr());
//			}
//			else if(obj.getDeprunitcb().equals("N")){
//				netvalueI =  Integer.parseInt(checkGet(rs.getString("netvalue")))-Integer.parseInt(obj.getMonthdepr());
//				accumdeprI = Integer.parseInt(checkGet(rs.getString("accumdepr")))+Integer.parseInt(obj.getMonthdepr());
//			}
//			ps=conn.prepareStatement(" update UNTRF_ATTACHMENT  set netvalue=? ,accumdepr=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=?");
//			ps.setString(1,String.valueOf(netvalueI));
//			ps.setString(2,String.valueOf(accumdeprI));
//			ps.setString(3,obj.getEnterOrg());
//			ps.setString(4,obj.getDifferenceKind());
//			ps.setString(5,obj.getOwnership());
//			ps.setString(6,obj.getPropertyno());
//			ps.setString(7,obj.getSerialno());
//			ps.executeUpdate();
//		
//			ps=conn.prepareStatement(" update UNTDP_MONTHDEPR  set verify=? " +
//					" where enterorg=? and differencekind=?" +
//					" and ownership=? and propertyno=? and serialno=? and deprym=? and serialno1=? and verify=? ");
//			ps.setString(1,"Y");
//			ps.setString(2,obj.getEnterOrg());
//			ps.setString(3,obj.getDifferenceKind());
//			ps.setString(4,obj.getOwnership());
//			ps.setString(5,obj.getPropertyno());
//			ps.setString(6,obj.getSerialno());
//			ps.setString(7,obj.getDeprYM());
//			ps.setString(8,obj.getSerialno1());
//			ps.setString(9,obj.getVerify());
//			ps.executeUpdate();
//		}
		}catch (Exception e) {
			throw e;
//		} finally {
//			db.closeAll();
		}	
	}
}