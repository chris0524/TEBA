package unt.dp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.StringTools;

public class UNTDP008R extends SuperBean{
	String isAdminManager;
	String isOrganManager;
	String organID;
	String q_kind; //1.重新產製  2.列印上次產製的資料
	String q_enterOrg; //入帳機關
	String q_enterOrgName; //入帳機關名稱
	String q_engineeringNo; //工程編號
	String q_engineeringNoName; //工程名稱
	String q_totalValue; //總金額
	String q_expectedAccountsYM; //預計入帳年月
	String q_differenceKind; //財產區分別
	String q_propertyType;	//財產大類
	String q_deprPark; //園區別
	String q_deprUnit; //部門別
	String q_deprUnit1; //部門別單位
	String q_deprAccounts; //會計科目
	String q_verify; //入帳
	String q_dataState; //資料狀態
	String q_deprMethod; //目前折舊－折舊方法
	String userID; //使用者id
	String userName; //使用者姓名
	public String getIsAdminManager(){ return checkGet(isAdminManager); }
	public void setIsAdminManager(String s){ isAdminManager=checkSet(s); }
	public String getIsOrganManager(){ return checkGet(isOrganManager); }
	public void setIsOrganManager(String s){ isOrganManager=checkSet(s); }
	public String getOrganID(){ return checkGet(organID); }
	public void setOrganID(String s){ organID=checkSet(s); }
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_engineeringNo(){ return checkGet(q_engineeringNo); }
	public void setQ_engineeringNo(String s){ q_engineeringNo=checkSet(s); }
	public String getQ_engineeringNoName(){ return checkGet(q_engineeringNoName); }
	public void setQ_engineeringNoName(String s){ q_engineeringNoName=checkSet(s); }
	public String getQ_totalValue(){ return checkGet(q_totalValue); }
	public void setQ_totalValue(String s){ q_totalValue=checkSet(s); }
	public String getQ_expectedAccountsYM(){ return checkGet(q_expectedAccountsYM); }
	public void setQ_expectedAccountsYM(String s){ q_expectedAccountsYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	public String getQ_deprPark(){ return checkGet(q_deprPark); }
	public void setQ_deprPark(String s){ q_deprPark=checkSet(s); }
	public String getQ_deprUnit(){ return checkGet(q_deprUnit); }
	public void setQ_deprUnit(String s){ q_deprUnit=checkSet(s); }
	public String getQ_deprUnit1(){ return checkGet(q_deprUnit1); }
	public void setQ_deprUnit1(String s){ q_deprUnit1=checkSet(s); }
	public String getQ_deprAccounts(){ return checkGet(q_deprAccounts); }
	public void setQ_deprAccounts(String s){ q_deprAccounts=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_deprMethod(){ return checkGet(q_deprMethod); }
	public void setQ_deprMethod(String s){ q_deprMethod=checkSet(s); }
	public String getUserID(){ return checkGet(userID); }
	public void setUserID(String s){ userID=checkSet(s); }
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }
	
	Map<String,String> differencekindMap;
	Map<String,String> propertytypeMap;
	Map<String,String> enterorgMap;
	Map<String,String> deprparkMap;
	Map<String,String> deprunitMap;
	Map<String,String> deprunit1Map;
	Map<String,String> propertynameMap;
	Map<String,String> depraccountsMap;
	
	public DefaultTableModel getResultModel() throws Exception{

		if ("1".equals(getQ_kind())) { //1.重新產製  2.列印上次產製的資料
			differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
			propertytypeMap = TCGHCommon.getSysca_Code("PTT"); 						//財產大類名稱
			enterorgMap = TCGHCommon.getSysca_Organ();								//機關名稱
			deprparkMap = TCGHCommon.getSysca_DeprPark(this.getQ_enterOrg());		//園區別名稱
			deprunitMap = TCGHCommon.getSysca_DeprUnit(this.getQ_enterOrg());		//部門別名稱
			deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());		//部門別單位名稱
			propertynameMap = TCGHCommon.getSyspk_PropertyCode(this.getQ_enterOrg());//財產名稱
			depraccountsMap = TCGHCommon.getSysca_DeprAccounts(this.getQ_enterOrg(),this.getQ_deprUnit());//財產名稱
			
			deleteUNTDP_UNTDP008R();
			insertUNTDP_UNTDP008R();
			updateUNTDP_UNTDP008R();
		}

				
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		try{
			String[] columns;
			Vector<Object> rowData = new Vector<Object>();
			columns = new String[] {"ENGINEERINGNO","ENGINEERINGNAME","TOTALVALUE",
					"EXPECTEDACCOUNTSYM","PROPERTYTYPE_NO","PROPERTYTYPE_NAME",
					"DIFFERENCEKIND_NO","DIFFERENCEKIND_NAME","DEPRPARK_NO",
					"DEPRPARK_NAME","DEPRUNIT_NO","DEPRUNIT_NAME","DEPRUNIT1_NO",
					"DEPRUNIT1_NAME","DEPRACCOUNTS_NO","DEPRACCOUNTS_NAME",
					"PROPERTYTYPE1_NO","PROPERTYTYPE1_NAME","BUDGETDEPR"};
			rowData = getResultModel1();
			
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
	
	public Vector<Object> getResultModel1() throws Exception{
		Database db = new Database();
		Vector<Object> rowData = new Vector<Object>();
		try{
			String execSQL = "select" +
					" qengineeringno, " +
					" qengineeringname, " +
					" qtotalvalue, " +
					" qexpectedaccountsym, " +
					" qpropertytype," +
					" qpropertytypename," +
					" qdifferencekind," +
					" qdifferencekindname," +
					" qdeprpark," +
					" qdeprparkname," +
					" qdeprunit," +
					" qdeprunitname," +
					" qdeprunit1," +
					" qdeprunit1name," +
					" qdepraccounts," +
					" qdepraccountsname," +
					" propertytype," +
					" propertytypename," +
					" sum(isnull(budgetdepr,'0')) as budgetdepr " +
					" from UNTDP_UNTDP008R where editid=" + Common.sqlChar(getUserID()) + " and enterorg="+ Common.sqlChar(getQ_enterOrg())+
					" group by" +
					" qengineeringno, " +
					" qengineeringname, " +
					" qtotalvalue, " +
					" qexpectedaccountsym, " +
					" qpropertytype," +
					" qpropertytypename," +
					" qdifferencekind," +
					" qdifferencekindname," +
					" qdeprpark," +
					" qdeprparkname," +
					" qdeprunit," +
					" qdeprunitname," +
					" qdeprunit1," +
					" qdeprunit1name," +
					" qdepraccounts," +
					" qdepraccountsname," +
					" propertytype," +
					" propertytypename";
					
			System.out.println(execSQL);
			StringTools st = new StringTools();
			ResultSet rs = db.querySQL(execSQL,false,false);
			int i = 0;
			while(rs.next()){
				i++;
				Object[] data = new Object[19];
				data[0] = checkGet(rs.getString("qengineeringno"));
				data[1] = checkGet(rs.getString("qengineeringname"));
				data[2] = checkGet(rs.getString("qtotalvalue"));
				data[3] = checkGet(Common.MaskCDate(Datetime.changeTaiwanYYMM(rs.getString("qexpectedaccountsym"), "1")));
				data[4] = checkGet(rs.getString("qpropertytype"));
				data[5] = checkGet(rs.getString("qpropertytypename"));
				data[6] = checkGet(rs.getString("qdifferencekind"));
				data[7] = checkGet(rs.getString("qdifferencekindname"));
				data[8] = checkGet(rs.getString("qdeprpark"));
				data[9] = checkGet(rs.getString("qdeprparkname"));
				data[10] = checkGet(rs.getString("qdeprunit"));
				data[11] = checkGet(rs.getString("qdeprunitname"));
				data[12] = checkGet(rs.getString("qdeprunit1"));
				data[13] = checkGet(rs.getString("qdeprunit1name"));
				data[14] = checkGet(rs.getString("qdepraccounts"));
				data[15] = checkGet(rs.getString("qdepraccountsname"));
				data[16] = checkGet(rs.getString("propertytype"));
				data[17] = checkGet(rs.getString("propertytypename"));
				data[18] = st._getMoneyFormat(checkGet(rs.getString("budgetdepr")));
				rowData.addElement(data);
			}
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			db.closeAll();
		}
		return rowData;
	}
	
	
	private void deleteUNTDP_UNTDP008R() throws Exception{
		Database db = new Database();
		String DelSql = "delete from UNTDP_UNTDP008R where editid=" + Common.sqlChar(getUserID())+ " and enterorg="+ Common.sqlChar(getQ_enterOrg()) ;
		try {
			db.excuteSQL(DelSql);
		} catch (Exception e) {
			setErrorMsg("刪除失敗");
			e.printStackTrace();
		} finally {
			db.closeAll();
		}	
	}
	
	private void insertUNTDP_UNTDP008R() throws Exception{
		
		Database db = new Database();
		String InsSql = "" +
			" insert into UNTDP_UNTDP008R ( " +
			" editid, " +
			" editdate, " +
			" enterorg, " +
			" propertytype, " +
			" propertytypename, " +
			" oldbudgetdepr, " +
			" qengineeringno, " +
			" qengineeringname, " +
			" qtotalvalue, " +
			" qexpectedaccountsym, " +
			" qpropertytype, " +
			" qpropertytypename, " +
			" qdifferencekind, " +
			" qdifferencekindname, " +
			" qdeprpark, " +
			" qdeprparkname, " +
			" qdeprunit, " +
			" qdeprunitname, " +
			" qdeprunit1, " +
			" qdeprunit1name, " +
			" qdepraccounts, " +
			" qdepraccountsname " +
			" )";
		InsSql += selectUNTRFU_ATTACHMENT() + " union " + selectUNTBU_BUILDING() + " union " + selectUMTMP_MOVABLE();
		try {
			System.out.println(InsSql);
			db.excuteSQL(InsSql);
		} catch (Exception e) {
			setErrorMsg("新增失敗");
			e.printStackTrace();
		} finally {
			db.closeAll();
		}			

	}
	
	private void updateUNTDP_UNTDP008R() throws Exception{
		//TODO:
	}
	
	//土地改良物
	private String selectUNTRFU_ATTACHMENT() throws Exception{
		
		String SelSql = "";
		SelSql = "" +
		" select " +
		Common.sqlChar(getUserID()) + " as editid," +
		Datetime.getYYYMMDD() + " as editdate," +
		Common.sqlChar(getQ_enterOrg()) + " as enterorg," +
		" '2' as propertytype,"+
		" '土地改良' as propertytypename,"+
		" '11' as oldbudgetdepr,"+//TODO:
		Common.sqlChar(getQ_engineeringNo()) + " as qengineeringno," +
		Common.sqlChar(getQ_engineeringNoName()) + " as qengineeringname," +
		Common.sqlChar(getQ_totalValue()) + " as qtotalvalue," +
		Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_expectedAccountsYM(), "2")) + " as qexpectedaccountsym," +
		Common.sqlChar(getQ_propertyType()) + " as qpropertytype," +
		Common.sqlChar(propertytypeMap.get(getQ_propertyType())) + " as qpropertytypename," +
		Common.sqlChar(getQ_differenceKind()) + " as qdifferencekind," +
		Common.sqlChar(differencekindMap.get(getQ_differenceKind())) + " as qdifferencekindname," +
		Common.sqlChar(getQ_deprPark()) + " as qdeprpark," +
		Common.sqlChar(deprparkMap.get(getQ_deprPark())) + " as qdeprparkname," +
		Common.sqlChar(getQ_deprUnit()) + " as qdeprunit," +
		Common.sqlChar(deprunitMap.get(getQ_deprUnit())) + " as qdeprunitname," +
		Common.sqlChar(getQ_deprUnit1()) + " as qdeprunit1," +
		Common.sqlChar(deprunit1Map.get(getQ_deprUnit1())) + " as qdeprunit1name," +
		Common.sqlChar(getQ_deprAccounts()) + " as qdepraccounts," +
		Common.sqlChar(depraccountsMap.get(getQ_deprAccounts())) + " as qdepraccountsname" +
		" from UNTRF_ATTACHMENT a " +
		" left outer join SYSPK_PROPERTYCODE c on a.enterorg in ('000000000A',c.enterorg) and a.propertyno = c.propertyno " +
		" left join UNTCH_DEPRPERCENT b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno and b.ishistory='N' " +
		" where 1=1";
		if (!"".equals(getQ_enterOrg())) {
			SelSql +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					SelSql += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
				} else {
					SelSql +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			} 
		}
		if (!"".equals(Common.get(getQ_engineeringNo())))	//工程編號
			SelSql += " and a.engineeringno = " + Common.sqlChar(getQ_engineeringNo());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			SelSql += " and a.differencekind = " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_deprPark())))		//園區別
			SelSql += " and a.deprpark = " + Common.sqlChar(getQ_deprPark());
		if (!"".equals(Common.get(getQ_deprUnit())))		//部門別
			SelSql += " and (case a.deprunitcb when 'N' then a.deprunit else b.deprunit end) = " + Common.sqlChar(getQ_deprUnit());
		if (!"".equals(Common.get(getQ_deprUnit1())))		//部門別單位
			SelSql += " and (case a.deprunitcb when 'N' then a.deprunit1 else b.deprunit1 end) = " + Common.sqlChar(getQ_deprUnit1());
		if (!"".equals(Common.get(getQ_deprAccounts())))	//會計科目
			SelSql += " and (case a.deprunitcb when 'N' then a.depraccounts else b.depraccounts end) = " + Common.sqlChar(getQ_deprAccounts());
		if (!"".equals(Common.get(getQ_verify())))			//入帳
			SelSql += " and a.verify = " + Common.sqlChar(getQ_verify());
		if (!"".equals(Common.get(getQ_dataState())))		//資料狀態
			SelSql += " and a.datastate = " + Common.sqlChar(getQ_dataState());
		if (!"".equals(Common.get(getQ_deprMethod())))		//目前折舊－折舊方法
			SelSql += " and a.deprmethod != " + Common.sqlChar(getQ_deprMethod());
		SelSql += " and ( " +
		Common.sqlChar(getQ_propertyType()) + " = '' or " +
		Common.sqlChar(getQ_propertyType()) + " = '2' " +
		" )";
		return SelSql;
	}
	
	//建物主檔
	private String selectUNTBU_BUILDING() throws Exception{
		String SelSql = "";
		SelSql = "" +
		" select " +
		Common.sqlChar(getUserID()) + " as editid," +
		Datetime.getYYYMMDD() + " as editdate," +
		Common.sqlChar(getQ_enterOrg()) + " as enterorg," +
		" '3' as propertytype,"+
		" '房屋建築及設備' as propertytypename,"+
		" '11' as oldbudgetdepr,"+//TODO:
		Common.sqlChar(getQ_engineeringNo()) + " as qengineeringno," +
		Common.sqlChar(getQ_engineeringNoName()) + " as qengineeringname," +
		Common.sqlChar(getQ_totalValue()) + " as qtotalvalue," +
		Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_expectedAccountsYM(), "2")) + " as qexpectedaccountsym," +
		Common.sqlChar(getQ_propertyType()) + " as qpropertytype," +
		Common.sqlChar(propertytypeMap.get(getQ_propertyType())) + " as qpropertytypename," +
		Common.sqlChar(getQ_differenceKind()) + " as qdifferencekind," +
		Common.sqlChar(differencekindMap.get(getQ_differenceKind())) + " as qdifferencekindname," +
		Common.sqlChar(getQ_deprPark()) + " as qdeprpark," +
		Common.sqlChar(deprparkMap.get(getQ_deprPark())) + " as qdeprparkname," +
		Common.sqlChar(getQ_deprUnit()) + " as qdeprunit," +
		Common.sqlChar(deprunitMap.get(getQ_deprUnit())) + " as qdeprunitname," +
		Common.sqlChar(getQ_deprUnit1()) + " as qdeprunit1," +
		Common.sqlChar(deprunit1Map.get(getQ_deprUnit1())) + " as qdeprunit1name," +
		Common.sqlChar(getQ_deprAccounts()) + " as qdepraccounts," +
		Common.sqlChar(depraccountsMap.get(getQ_deprAccounts())) + " as qdepraccountsname" +
		" from UNTBU_BUILDING a " +
		" left outer join SYSPK_PROPERTYCODE c on a.enterorg in ('000000000A',c.enterorg) and a.propertyno = c.propertyno " +
		" left join UNTCH_DEPRPERCENT b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno and b.ishistory='N' " +
		" where 1=1";
		if (!"".equals(getQ_enterOrg())) {
			SelSql +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					SelSql += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
				} else {
					SelSql +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			} 
		}
		if (!"".equals(Common.get(getQ_engineeringNo())))	//工程編號
			SelSql += " and a.engineeringno = " + Common.sqlChar(getQ_engineeringNo());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			SelSql += " and a.differencekind = " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_deprPark())))		//園區別
			SelSql += " and a.deprpark = " + Common.sqlChar(getQ_deprPark());
		if (!"".equals(Common.get(getQ_deprUnit())))		//部門別
			SelSql += " and (case a.deprunitcb when 'N' then a.deprunit else b.deprunit end) = " + Common.sqlChar(getQ_deprUnit());
		if (!"".equals(Common.get(getQ_deprUnit1())))		//部門別單位
			SelSql += " and (case a.deprunitcb when 'N' then a.deprunit1 else b.deprunit1 end) = " + Common.sqlChar(getQ_deprUnit1());
		if (!"".equals(Common.get(getQ_deprAccounts())))	//會計科目
			SelSql += " and (case a.deprunitcb when 'N' then a.depraccounts else b.depraccounts end) = " + Common.sqlChar(getQ_deprAccounts());
		if (!"".equals(Common.get(getQ_verify())))			//入帳
			SelSql += " and a.verify = " + Common.sqlChar(getQ_verify());
		if (!"".equals(Common.get(getQ_dataState())))		//資料狀態
			SelSql += " and a.datastate = " + Common.sqlChar(getQ_dataState());
		if (!"".equals(Common.get(getQ_deprMethod())))		//目前折舊－折舊方法
			SelSql += " and a.deprmethod != " + Common.sqlChar(getQ_deprMethod());

		SelSql += " and ( " +
		Common.sqlChar(getQ_propertyType()) + " = '' or " +
		Common.sqlChar(getQ_propertyType()) + " = '3' " +
		" )";
		return SelSql;
	}
	
	//動產
	private String selectUMTMP_MOVABLE() throws Exception{
		String SelSql = "";
		SelSql = "" +
		" select " +
		Common.sqlChar(getUserID()) + " as editid," +
		Datetime.getYYYMMDD() + " as editdate," +
		Common.sqlChar(getQ_enterOrg()) + " as enterorg," +
		" case substring(a.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' end as propertytype,"+
		" case substring(a.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertytypename,"+
		" '11' as oldbudgetdepr,"+//TODO:
		Common.sqlChar(getQ_engineeringNo()) + " as qengineeringno," +
		Common.sqlChar(getQ_engineeringNoName()) + " as qengineeringname," +
		Common.sqlChar(getQ_totalValue()) + " as qtotalvalue," +
		Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_expectedAccountsYM(), "2")) + " as qexpectedaccountsym," +
		Common.sqlChar(getQ_propertyType()) + " as qpropertytype," +
		Common.sqlChar(propertytypeMap.get(getQ_propertyType())) + " as qpropertytypename," +
		Common.sqlChar(getQ_differenceKind()) + " as qdifferencekind," +
		Common.sqlChar(differencekindMap.get(getQ_differenceKind())) + " as qdifferencekindname," +
		Common.sqlChar(getQ_deprPark()) + " as qdeprpark," +
		Common.sqlChar(deprparkMap.get(getQ_deprPark())) + " as qdeprparkname," +
		Common.sqlChar(getQ_deprUnit()) + " as qdeprunit," +
		Common.sqlChar(deprunitMap.get(getQ_deprUnit())) + " as qdeprunitname," +
		Common.sqlChar(getQ_deprUnit1()) + " as qdeprunit1," +
		Common.sqlChar(deprunit1Map.get(getQ_deprUnit1())) + " as qdeprunit1name," +
		Common.sqlChar(getQ_deprAccounts()) + " as qdepraccounts," +
		Common.sqlChar(depraccountsMap.get(getQ_deprAccounts())) + " as qdepraccountsname" +
		" from UNTMP_MOVABLEDETAIL a "+
		" left join UNTCH_DEPRPERCENT b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno and b.ishistory='N' "+
		" left join UNTMP_MOVABLE c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.differencekind=c.differencekind and a.propertyno=c.propertyno and a.lotno=c.lotno and b.ishistory='N' "+
		" left outer join SYSPK_PROPERTYCODE d on a.enterorg in ('000000000A',d.enterorg) and a.propertyno = d.propertyno " +
		" where 1=1";
		if (!"".equals(getQ_enterOrg())) {
			SelSql +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					SelSql += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
				} else {
					SelSql +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			} 
		}
		if (!"".equals(Common.get(getQ_engineeringNo())))	//工程編號
			SelSql += " and a.engineeringno = " + Common.sqlChar(getQ_engineeringNo());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			SelSql += " and a.differencekind = " + Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_deprPark())))		//園區別
			SelSql += " and a.deprpark = " + Common.sqlChar(getQ_deprPark());
		if (!"".equals(Common.get(getQ_deprUnit())))		//部門別
			SelSql += " and (case a.deprunitcb when 'N' then a.deprunit else b.deprunit end) = " + Common.sqlChar(getQ_deprUnit());
		if (!"".equals(Common.get(getQ_deprUnit1())))		//部門別單位
			SelSql += " and (case a.deprunitcb when 'N' then a.deprunit1 else b.deprunit1 end) = " + Common.sqlChar(getQ_deprUnit1());
		if (!"".equals(Common.get(getQ_deprAccounts())))	//會計科目
			SelSql += " and (case a.deprunitcb when 'N' then a.depraccounts else b.depraccounts end) = " + Common.sqlChar(getQ_deprAccounts());
		if (!"".equals(Common.get(getQ_verify())))			//入帳
			SelSql += " and a.verify = " + Common.sqlChar(getQ_verify());
		if (!"".equals(Common.get(getQ_dataState())))		//資料狀態
			SelSql += " and a.datastate = " + Common.sqlChar(getQ_dataState());
		if (!"".equals(Common.get(getQ_deprMethod())))		//目前折舊－折舊方法
			SelSql += " and a.deprmethod != " + Common.sqlChar(getQ_deprMethod());
		SelSql += " and ( " +
		Common.sqlChar(getQ_propertyType()) + " = '' or " +
		Common.sqlChar(getQ_propertyType()) + " in ('4','5','6') " +
		" )";
		return SelSql;
	}
	
}