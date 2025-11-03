package unt.dp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.StringTools;

/**
 * <br/>程式目的：折舊預估表(已入帳)
 * <br/>程式代號：UNTDP007R
 * <br/>程式日期：?
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTDP007R extends SuperBean {
	String isAdminManager;
	String isOrganManager;
	String organID;
	String q_kind; //1.重新產製  2.列印上次產製的資料
	String q_enterOrg; //入帳機關
	String q_enterOrgName; //入帳機關名稱
	String q_accountsYM; //帳務日期
	String q_budgetYMS; //預估年月起
	String q_budgetYME; //預估年月迄
	String q_differenceKind; //財產區分別
	String q_propertyType;	//財產大類
	String q_deprPark; //園區別
	String q_deprUnit; //部門別
	String q_deprUnit1; //部門別單位
	String q_deprAccounts; //會計科目
	String q_report; //報表類別
	String q_verify; //入帳
	String q_dataState; //資料狀態
	String q_deprMethod; //目前折舊－折舊方法
	String q_noDeprSet; //不折舊設定
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
	public String getQ_accountsYM(){ return checkGet(q_accountsYM); }
	public void setQ_accountsYM(String s){ q_accountsYM=checkSet(s); }
	public String getQ_budgetYMS(){ return checkGet(q_budgetYMS); }
	public void setQ_budgetYMS(String s){ q_budgetYMS=checkSet(s); }
	public String getQ_budgetYME(){ return checkGet(q_budgetYME); }
	public void setQ_budgetYME(String s){ q_budgetYME=checkSet(s); }
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
	public String getQ_report(){ return checkGet(q_report); }
	public void setQ_report(String s){ q_report=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_deprMethod(){ return checkGet(q_deprMethod); }
	public void setQ_deprMethod(String s){ q_deprMethod=checkSet(s); }
	public String getQ_noDeprSet(){ return checkGet(q_noDeprSet); }
	public void setQ_noDeprSet(String s){ q_noDeprSet=checkSet(s); }
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
			depraccountsMap = TCGHCommon.getSysca_DeprAccounts(this.getQ_enterOrg());//財產名稱
			
			deleteUNTDP_UNTDP007R();
			insertUNTDP_UNTDP007R();
		}

				
		DefaultTableModel model = new DefaultTableModel();
		List<Object[]> rows = null;
		Database db = new Database();
		try{
			String[] columns;
			if ("1".equals(getQ_report())) { //財產大類
				columns = new String[] {"ACCOUNTSYM","BUDGETYMS","BUDGETYME",
						"PROPERTYTYPE_NO","PROPERTYTYPE_NAME","DIFFERENCEKIND_NO",
						"DIFFERENCEKIND_NAME","DEPRPARK_NO","DEPRPARK_NAME","DEPRUNIT_NO",
						"DEPRUNIT_NAME","DEPRUNIT1_NO","DEPRUNIT1_NAME","DEPRACCOUNTS_NO",
						"DEPRACCOUNTS_NAME","PROPERTYTYPE1_NO",
						"PROPERTYTYPE1_NAME","BUDGETDEPR"};
				rows = getResultModel1();
			} else { // 財產明細
				columns = new String[] {"ACCOUNTSYM","BUDGETYMS","BUDGETYME",
						"PROPERTYTYPE_NO","PROPERTYTYPE_NAME","DIFFERENCEKIND_NO",
						"DIFFERENCEKIND_NAME","DEPRPARK_NO","DEPRPARK_NAME","DEPRUNIT_NO",
						"DEPRUNIT_NAME","DEPRUNIT1_NO","DEPRUNIT1_NAME","DEPRACCOUNTS_NO",
						"DEPRACCOUNTS_NAME","PROPERTYNO","SERIALNO","PROPERTYTYPE1_NO",
						"PROPERTYTYPE1_NAME","PROPERTYNAME","PROPERTYNAME1","BUYDATE",
						"LIMITYEAR","NAMEPLATE","SPECIFICATION","PROPERTYUNIT","BOOKAMOUNT",
						"BUDGETDEPR", "BOOKVALUE", "SCRAPVALUE"};
				rows = getResultModel2();
			}
			
			model.setDataVector(rows.toArray(new Object[rows.size()][columns.length]), columns);
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			db.closeAll();
			
			if (rows != null) {
				rows.clear();
				rows = null;
			}
		}
		return model;
	}
	
	/**
	 * 財產大類resultModel
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getResultModel1() throws Exception{
		Database db = new Database();
		List<Object[]> rowData = new ArrayList<Object[]>();
		try{
			String execSQL = "select" +
					" qaccountsym," +
					" qbudgetyms," +
					" qbudgetyme," +
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
					" from UNTDP_UNTDP007R where editid=" + Common.sqlChar(getUserID()) + " and enterorg="+ Common.sqlChar(getQ_enterOrg())+
					" group by" +
					" qaccountsym," +
					" qbudgetyms," +
					" qbudgetyme," +
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
			StringTools st = new StringTools();	
			ResultSet rs = db.querySQL(execSQL,false,false);
			int i = 0;
			while(rs.next()){
				i++;
				Object[] data = new Object[18];
				data[0] = checkGet(Common.MaskCDate(Datetime.changeTaiwanYYMM(rs.getString("qaccountsym"), "1")));
				data[1] = checkGet(Common.MaskCDate(Datetime.changeTaiwanYYMM(rs.getString("qbudgetyms"), "1")));
				data[2] = checkGet(Common.MaskCDate(Datetime.changeTaiwanYYMM(rs.getString("qbudgetyme"), "1")));
				data[3] = checkGet(rs.getString("qpropertytype"));
				data[4] = checkGet(rs.getString("qpropertytypename"));
				data[5] = checkGet(rs.getString("qdifferencekind"));
				data[6] = checkGet(rs.getString("qdifferencekindname"));
				data[7] = checkGet(rs.getString("qdeprpark"));
				data[8] = checkGet(rs.getString("qdeprparkname"));
				data[9] = checkGet(rs.getString("qdeprunit"));
				data[10] = checkGet(rs.getString("qdeprunitname"));
				data[11] = checkGet(rs.getString("qdeprunit1"));
				data[12] = checkGet(rs.getString("qdeprunit1name"));
				data[13] = checkGet(rs.getString("qdepraccounts"));
				data[14] = checkGet(rs.getString("qdepraccountsname"));
				data[15] = checkGet(rs.getString("propertytype"));
				data[16] = checkGet(rs.getString("propertytypename"));
				data[17] = st._getMoneyFormat(checkGet(rs.getString("budgetdepr")));
				rowData.add(data);
			}
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			db.closeAll();
		}
		return rowData;
	}
	
	/**
	 * 財產明細 resultModel
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> getResultModel2() throws Exception{
		Database db = new Database();
		List<Object[]> rowData = new ArrayList<Object[]>();
		try{
			String execSQL = "select * from UNTDP_UNTDP007R where editid=" + Common.sqlChar(getUserID())+" and enterorg="+ Common.sqlChar(getQ_enterOrg());

			StringTools st = new StringTools();	
			ResultSet rs = db.querySQL(execSQL,false,false);
			int i = 0;
			while(rs.next()){
				i++;
				Object[] data = new Object[30];
				data[0] = checkGet(Common.MaskCDate(Datetime.changeTaiwanYYMM(rs.getString("qaccountsym"), "1")));
				data[1] = checkGet(Common.MaskCDate(Datetime.changeTaiwanYYMM(rs.getString("qbudgetyms"), "1")));
				data[2] = checkGet(Common.MaskCDate(Datetime.changeTaiwanYYMM(rs.getString("qbudgetyme"), "1")));
				data[3] = checkGet(rs.getString("qpropertytype"));
				data[4] = checkGet(rs.getString("qpropertytypename"));
				data[5] = checkGet(rs.getString("qdifferencekind"));
				data[6] = checkGet(rs.getString("qdifferencekindname"));
				data[7] = checkGet(rs.getString("qdeprpark"));
				data[8] = checkGet(rs.getString("qdeprparkname"));
				data[9] = checkGet(rs.getString("qdeprunit"));
				data[10] = checkGet(rs.getString("qdeprunitname"));
				data[11] = checkGet(rs.getString("qdeprunit1"));
				data[12] = checkGet(rs.getString("qdeprunit1name"));
				data[13] = checkGet(rs.getString("qdepraccounts"));
				data[14] = checkGet(rs.getString("qdepraccountsname"));
				String propertyno = checkGet(rs.getString("propertyno").toString());
				if (propertyno.length() > 7) {
					data[15] = checkGet(propertyno.substring(0, 7)) + "-" + checkGet(propertyno.substring(7));
				} else {
					data[15] = checkGet(propertyno);
				}
				data[16] = checkGet(rs.getString("serialno"));
				data[17] = checkGet(rs.getString("propertytype"));
				data[18] = checkGet(rs.getString("propertytypename"));
				data[19] = checkGet(rs.getString("propertyname"));
				data[20] = checkGet(rs.getString("propertyname1"));
				String buyDate = Common.get(rs.getString("buydate"));
				if (buyDate.length() == 8) { //西元年
					buyDate = Datetime.changeTaiwanYYMMDD(buyDate, "1");
				}
				data[21] = buyDate;
				data[22] = Common.formatOtherLimityear(1, Common.get(rs.getString("limityear")));
				data[23] = checkGet(rs.getString("nameplate"));
				data[24] = checkGet(rs.getString("specification"));
				data[25] = checkGet(rs.getString("propertyunit"));
				data[26] = st._getMoneyFormat(checkGet(rs.getString("bookamount")));
				data[27] = st._getMoneyFormat(checkGet(rs.getString("budgetdepr")));
				data[28] = st._getMoneyFormat(Common.get(rs.getString("bookvalue")));
				data[29] = st._getMoneyFormat(Common.get(rs.getString("scrapvalue")));
				rowData.add(data);
			}
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			db.closeAll();
		}
		return rowData;
	}
	
	private void deleteUNTDP_UNTDP007R() throws Exception{
		Database db = new Database();
		String DelSql = "delete from UNTDP_UNTDP007R where editid=" + Common.sqlChar(getUserID())+ " and enterorg="+ Common.sqlChar(getQ_enterOrg());
		try {
			db.excuteSQL(DelSql);
		} catch (Exception e) {
			setErrorMsg("刪除失敗");
			e.printStackTrace();
		} finally {
			db.closeAll();
		}	
	}
	
	private void insertUNTDP_UNTDP007R() throws Exception{
		
		Database db = new Database();
		String InsSql = "" +
			" declare @budgetyms varchar(6)" +
			" declare @budgetyme varchar(6)" +
			" set @budgetyms = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_budgetYMS(), "2")) +
			" set @budgetyme = " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_budgetYME(), "2")) +
			" insert into UNTDP_UNTDP007R ( " +
			" editid," +
			" editdate," +
			" enterorg," +
			" propertyno," +
			" serialno," +
			" propertytype," +
			" propertytypename," +
			" propertyname," +
			" propertyname1," +
			" buydate," +
			" limityear," +
			" nameplate," +
			" specification," +
			" propertyunit," +
			" bookamount," +
			" budgetdepr," +
			" qaccountsym," +
			" qbudgetyms," +
			" qbudgetyme," +
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
			" bookvalue," + 
			" scrapvalue )";
		// InsSql += selectUNTRFU_ATTACHMENT() + " union " + selectUNTBU_BUILDING() + " union " + selectUMTMP_MOVABLE();
		InsSql += this.getInsertDataSQL();
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
	
	private String getInsertDataSQL() {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select ")
		   .append(" ").append(Common.sqlChar(this.getUserID())).append(" as editid")
		   .append(" ,").append(Common.sqlChar(Datetime.getYYYMMDD())).append(" as editdate")
		   .append(" ,").append(Common.sqlChar(this.getQ_enterOrg())).append(" as enterorg")
		   .append(" ,a.propertyno ,a.serialno ,a.propertytype")
		   .append(" ,case a.propertytype ")
		   .append(" when '2' then '土地改良' when '3' then '房屋建築及設備' when '8' then '有價證券' ")
		   .append(" else ( case substring(x.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end ) ")
		   .append(" end as propertytypename ")
		   .append(" ,c.propertyname")
		   .append(" ,a.propertyname1 ,a.buydate")
		   .append(" ,a.limityear ") // TODO not sure
		   .append(" ,x.nameplate ") // TODO not sure
		   .append(" ,x.specification ") // TODO not sure
		   .append(" ,c.propertyunit ,a.bookamount")
		   
		   //#region budgetdepr 預估  這是這支的重點所在  
		   // 以下都用oldmonthdepr ,oldmonthdepr1 因為Keri說要用原始的折舊去預估 而不是因各種條件調整後的monthdepr, monthdepr1
		   .append(" ,case when a.deprunitcb = 'N' then ")
		   //#region 無依比例   
		   .append(" case ")
		   // 已截止
		   .append(" when x.apportionendym < @budgetyms then 0 ")
		   
		   // 預估年月起 = 截止年月
		   .append(" when x.apportionendym = @budgetyms then a.oldmonthdepr1 ")
		   
		   // 截止年月 介於 預估年月之間, ex:10605 -> 月攤提折舊金額 * 4 + 最後一個月攤提折舊金額
		   .append(" when @budgetyms < x.apportionendym and apportionendym <= @budgetyme ")
		   .append(" then (case when @budgetyms <= substring(x.buydate, 1, 6) and substring(x.buydate, 1, 6) <= @budgetyme ")
		   .append(" then a.oldmonthdepr * ((datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) - 1) + isnull(a.oldmonthdepr1,0) ")
		   .append(" else a.oldmonthdepr * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) + isnull(a.oldmonthdepr1,0) end) ")
		   // 截止年月 > 預估年月起訖   : 以 預估年月整年去計算, 不需考慮最後一個月的折舊攤提金額
		   .append(" else (case when @budgetyms <= substring(x.buydate, 1, 6) and substring(x.buydate, 1, 6) <= @budgetyme ")
		   .append(" then isnull(a.oldmonthdepr, 0) * ((datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1) - 1) ")
		   .append(" else isnull(a.oldmonthdepr, 0) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1) end) ")
		   .append(" end ")
		   //#end 
		   .append(" else ")
		   //#region 依比例    
		   .append(" case ")
		   // 已截止
		   .append(" when x.apportionendym < @budgetyms then 0 ")
		   
		   // 預估年月起 = 截止年月
		   .append(" when x.apportionendym = @budgetyms then ROUND(isnull(a.oldmonthdepr1 ,0) * isnull(a.deprpercent,0) / 100,0,1) ")
		   
		   // 截止年月 介於 預估年月之間, ex:10605 -> 月攤提折舊金額 * 4 + 最後一個月攤提折舊金額
		   .append(" when @budgetyms < x.apportionendym and apportionendym <= @budgetyme ")
		   .append(" then (case when @budgetyms <= substring(x.buydate, 1, 6) and substring(x.buydate, 1, 6) <= @budgetyme ")
		   .append(" then ROUND(isnull(a.oldmonthdepr ,0) * isnull(a.deprpercent,0) / 100,0,1) ")
		   .append("      * ((datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) - 1) ")
		   .append("      + ROUND(isnull(oldmonthdepr1 ,0) * isnull(a.deprpercent ,0) / 100,0,1) ")
		   .append(" else ROUND(isnull(a.oldmonthdepr ,0) * isnull(a.deprpercent,0) / 100,0,1) ")
		   .append("      * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) ")
		   .append("      + ROUND(isnull(oldmonthdepr1 ,0) * isnull(a.deprpercent ,0) / 100,0,1) end) ")
		   // 截止年月 > 預估年月起訖   : 以 預估年月整年去計算, 不需考慮最後一個月的折舊攤提金額
		   .append(" else (case when @budgetyms <= substring(x.buydate, 1, 6) and substring(x.buydate, 1, 6) <= @budgetyme ")
		   .append(" then ROUND(isnull(oldmonthdepr ,0) * isnull(deprpercent ,0) / 100,0,1) * ((datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1) - 1) ")
		   .append(" else ROUND(isnull(oldmonthdepr ,0) * isnull(deprpercent ,0) / 100,0,1) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1) end) ")
		   .append(" end ")
		   //#end 
		   .append(" end as budgetdepr")
		   //#end 
		   
		   .append(" ,").append(Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2"))).append(" as qaccountsym")
		   .append(" ,@budgetyms as qbudgetyms")
		   .append(" ,@budgetyme as qbudgetyme")
		   .append(" ,").append(Common.sqlChar(this.getQ_report())).append(" as qpropertytype")
		   .append(" ,").append(Common.sqlChar(propertytypeMap.get(getQ_propertyType()))).append(" as qpropertytypename")
		   .append(" ,").append(Common.sqlChar(getQ_differenceKind())).append(" as qdifferencekind")
		   .append(" ,").append(Common.sqlChar(differencekindMap.get(getQ_differenceKind()))).append(" as qdifferencekindname")
		   .append(" ,").append(Common.sqlChar(getQ_deprPark())).append(" as qdeprpark")
		   .append(" ,").append(Common.sqlChar(deprparkMap.get(getQ_deprPark()))).append(" as qdeprparkname")
		   .append(" ,").append(Common.sqlChar(getQ_deprUnit())).append(" as qdeprunit")
		   .append(" ,").append(Common.sqlChar(deprunitMap.get(getQ_deprUnit()))).append(" as qdeprunitname")
		   .append(" ,").append(Common.sqlChar(getQ_deprUnit1())).append(" as qdeprunit1")
		   .append(" ,").append(Common.sqlChar(deprunit1Map.get(getQ_deprUnit1()))).append(" as qdeprunit1name")
		   .append(" ,").append(Common.sqlChar(getQ_deprAccounts())).append(" as qdepraccounts")
		   .append(" ,").append(Common.sqlChar(depraccountsMap.get(getQ_deprAccounts()))).append(" as qdepraccountsname")
		   .append(" ,a.bookvalue ,a.scrapvalue")
		   .append(" from UNTDP_MONTHDEPR a ")
		   .append(" left join SYSPK_PROPERTYCODE c on c.enterorg in ('000000000A',a.enterorg) and a.propertyno = c.propertyno ")
		   .append(" left join (")
		   .append(" select enterorg,ownership,differencekind,propertyno,serialno,nodeprset,apportionendym ,'' as nameplate ,'' as specification, buydate from UNTBU_BUILDING ")
		   .append(" union all ")
		   .append(" select enterorg,ownership,differencekind,propertyno,serialno,nodeprset,apportionendym ,'' as nameplate ,'' as specification, buydate from UNTRF_ATTACHMENT")
		   .append(" union all ")
		   .append(" select z.enterorg,z.ownership,z.differencekind,z.propertyno,z.serialno,z.nodeprset,z.apportionendym ,v.nameplate ,v.specification, v.buydate from UNTMP_MOVABLEDETAIL z")
		   .append(" left join UNTMP_MOVABLE  v on z.enterorg=v.enterorg and z.ownership=v.ownership and z.differencekind=v.differencekind and z.propertyno=v.propertyno and z.lotno=v.lotno")
		   .append(" union all ")
		   .append(" select enterorg,ownership,differencekind,propertyno,serialno,nodeprset,apportionendym ,'' as nameplate ,'' as specification, buydate from UNTRT_ADDPROOF")
		   .append(" ) as x  on a.enterorg=x.enterorg and a.ownership=x.ownership and a.differencekind=x.differencekind and a.propertyno=x.propertyno and a.serialno=x.serialno ")
		   .append(" where 1=1 ");
		
		
		if (!"".equals(this.getQ_enterOrg())) {
			sql.append(" and a.enterorg =").append(Common.sqlChar(this.getQ_enterOrg()));
		} else {
			if (!"Y".equalsIgnoreCase(this.getIsAdminManager())) {
				if ("Y".equals(this.getIsOrganManager())) {
					sql.append(" and a.enterorg like '").append(this.getOrganID().substring(0, 5)).append("%' ");
				} else {
					sql.append(" and a.enterorg = ").append(Common.sqlChar(this.getOrganID()));
				}
			}
		}
		//財產大類
		if(!"".equals(this.getQ_propertyType())) {
			sql.append(" and a.propertytype = ").append(Common.sqlChar(this.getQ_propertyType()));
		}
		
		// 帳務日期
		if (!"".equals(this.getQ_accountsYM())) {
			sql.append(" and a.deprym = ").append(Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2")));
		}
		
		if (!"".equals(this.getQ_differenceKind())) {
			sql.append(" and a.differencekind = ").append(Common.sqlChar(this.getQ_differenceKind()));
		}
		
		if (!"".equals(this.getQ_deprPark())) {
			sql.append(" and a.deprpark = ").append(Common.sqlChar(this.getQ_deprPark()));
		}
		
		if (!"".equals(this.getQ_deprUnit())) {
			sql.append(" and a.deprunit = ").append(Common.sqlChar(this.getQ_deprUnit()));
		}
		
		if (!"".equals(this.getQ_deprUnit1())) {
			sql.append(" and a.deprunit1 = ").append(Common.sqlChar(this.getQ_deprUnit1()));
		}
		
		if (!"".equals(this.getQ_deprAccounts())) {
			sql.append(" and a.depraccounts = ").append(Common.sqlChar(this.getQ_deprAccounts()));
		}
		
		if (!"".equals(this.getQ_deprMethod())) {
			sql.append(" and a.deprmethod!= ").append(Common.sqlChar(this.getQ_deprMethod()));
		}
		
		if (!"".equals(this.getQ_noDeprSet())) {
			sql.append(" and a.nodeprset = ").append(Common.sqlChar(this.getQ_noDeprSet()));
		}
		return sql.toString();
	}
	

	/**
	 * 土地改良物
	 * @deprecated
	 */
	private String selectUNTRFU_ATTACHMENT() throws Exception{
		
		String SelSql = "";
		SelSql = "" +
		" select " +
		Common.sqlChar(getUserID()) + " as editid," +
		Datetime.getYYYMMDD() + " as editdate," +
		Common.sqlChar(getQ_enterOrg()) + " as enterorg," +
		" a.propertyno," +
		" a.serialno,"+
		" '2' as propertytype,"+
		" '土地改良' as propertytypename,"+
		" c.propertyname,"+
		" a.propertyname1,"+
		" a.buydate,"+
		" isnull(a.otherlimityear, a.originallimityear * 12) as limityear,"+
		" '' as nameplate,"+
		" '' as specification,"+
		" c.propertyunit,"+
		" a.measure as bookamount,"+
		// ~ 這邊開始算折舊預估  budgetdepr 為了方便閱讀 以此種code style呈現
		" case    " + 
		"	when deprunitcb = 'N' " +  // 不需依比例計算 
		"		then     " + 
		"			case    " + 
		"				when apportionendym < @budgetyms   " +   // 已截止
		"					then 0   " + 
		"				when apportionendym = @budgetyms    " +  // 預估年月起 = 截止年月
		"					then isnull(monthdepr1,0)     " + 
		"				when @budgetyms < apportionendym and apportionendym <= @budgetyme    " +   // 截止年月 介於 預估年月之間, ex:10605 -> 月攤提折舊金額 * 4 + 最後一個月攤提折舊金額
		"					then monthdepr * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) + isnull(monthdepr1,0)     " + 
		"				else " +    // 截止年月 > 預估年月起訖   : 以 預估年月整年去計算, 不需考慮最後一個月的折舊攤提金額
		"                   isnull(monthdepr,0) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1)     " + 
		"			end     " + 
		"	else     " +              // 需依比例計算   case when 說明與上面相同  只是要考慮比例
		"		case    " + 
		"			when apportionendym < @budgetyms   " + 
		"				then 0   " + 
		"			when apportionendym = @budgetyms    " + 
		"				then ROUND(isnull(monthdepr1,0) * isnull(deprpercent,0) / 100,0,1)     " + 
		"			when @budgetyms <  apportionendym and  apportionendym <= @budgetyme    " + 
		"				then ROUND(isnull(monthdepr,0) * isnull(deprpercent,0) / 100,0,1) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) + ROUND(isnull(monthdepr1,0) * isnull(deprpercent,0) / 100,0,1)     " + 
		"			else ROUND(isnull(monthdepr,0) * isnull(deprpercent,0) / 100,0,1) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1)   " + 
		"		end     " + 
		" end as budgetdepr, " +
		// ~ 折舊預估計算完畢
		Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2")) + " as qaccountsym," +
		" @budgetyms as qbudgetyms," +
		" @budgetyme as qbudgetyme," +
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
		Common.sqlChar(depraccountsMap.get(getQ_deprAccounts())) + " as qdepraccountsname," +
		" a.bookvalue, a.scrapvalue " +
		" from UNTRF_ATTACHMENT_"+ getQ_accountsYM() + " a " +
		" left outer join SYSPK_PROPERTYCODE c on c.enterorg in ('000000000A',a.enterorg) and a.propertyno = c.propertyno " +
		" left join UNTCH_DEPRPERCENT_"+ getQ_accountsYM() + " b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno and b.ishistory='N' " +
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
		
		
		if (!"".equals(Common.get(getQ_differenceKind())))	//帳務日期
			SelSql += " and substring(a.enterdate,1,6) < " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2"));		
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
		if (!"".equals(Common.get(getQ_noDeprSet())))		//不折舊設定
			SelSql += " and a.nodeprset = " + Common.sqlChar(getQ_noDeprSet());
		SelSql += " and a.apportionendym >= @budgetyms " +
		" and ( " +
		Common.sqlChar(getQ_propertyType()) + " = '' or " +
		Common.sqlChar(getQ_propertyType()) + " = '2' " +
		" )";
		return SelSql;
	}
	
	/**
	 * 建物主檔
	 * @deprecated
	 */
	private String selectUNTBU_BUILDING() throws Exception{
		String SelSql = "";
		SelSql = "" +
		" select " +
		Common.sqlChar(getUserID()) + " as editid," +
		Datetime.getYYYMMDD() + " as editdate," +
		Common.sqlChar(getQ_enterOrg()) + " as enterorg," +
		" a.propertyno," +
		" a.serialno,"+
		" '3' as propertytype,"+
		" '房屋建築及設備' as propertytypename,"+
		" c.propertyname,"+
		" a.propertyname1,"+
		" a.buydate,"+
		" isnull(a.otherlimityear, a.originallimityear * 12) as limityear,"+
		" '' as nameplate,"+
		" '' as specification,"+
		" '平方公尺' as propertyunit,"+
		" a.holdarea as bookamount,"+
		// ~ 這邊開始算折舊預估  budgetdepr   為了方便閱讀 以此種code style呈現
		" case    " + 
		"	when deprunitcb = 'N' " +  // 不需依比例計算 
		"		then     " + 
		"			case    " + 
		"				when apportionendym < @budgetyms   " +   // 已截止
		"					then 0   " + 
		"				when apportionendym = @budgetyms    " +  // 預估年月起 = 截止年月
		"					then isnull(monthdepr1,0)     " + 
		"				when @budgetyms < apportionendym and apportionendym <= @budgetyme    " +   // 截止年月 介於 預估年月之間, ex:10605 -> 月攤提折舊金額 * 4 + 最後一個月攤提折舊金額
		"					then monthdepr * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) + isnull(monthdepr1,0)     " + 
		"				else " +    // 截止年月 > 預估年月起訖   : 以 預估年月整年去計算, 不需考慮最後一個月的折舊攤提金額
		"                   isnull(monthdepr,0) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1)     " + 
		"			end     " + 
		"	else     " +              // 需依比例計算   case when 說明與上面相同  只是要考慮比例
		"		case    " + 
		"			when apportionendym < @budgetyms   " + 
		"				then 0   " + 
		"			when apportionendym = @budgetyms    " + 
		"				then ROUND(isnull(monthdepr1,0) * isnull(deprpercent,0) / 100,0,1)     " + 
		"			when @budgetyms <  apportionendym and  apportionendym <= @budgetyme    " + 
		"				then ROUND(isnull(monthdepr,0) * isnull(deprpercent,0) / 100,0,1) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) + ROUND(isnull(monthdepr1,0) * isnull(deprpercent,0) / 100,0,1)     " + 
		"			else ROUND(isnull(monthdepr,0) * isnull(deprpercent,0) / 100,0,1) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1)   " + 
		"		end     " + 
		" end as budgetdepr, " +
		// ~ 折舊預估計算完畢
		Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2")) + " as qaccountsym," +
		" @budgetyms as qbudgetyms," +
		" @budgetyme as qbudgetyme," +
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
		Common.sqlChar(depraccountsMap.get(getQ_deprAccounts())) + " as qdepraccountsname," +
		" a.bookvalue, a.scrapvalue " +
		" from UNTBU_BUILDING_"+ getQ_accountsYM() + " a " +
		" left outer join SYSPK_PROPERTYCODE c on c.enterorg in ('000000000A',a.enterorg) and a.propertyno = c.propertyno " +
		" left join UNTCH_DEPRPERCENT_"+ getQ_accountsYM() + " b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno and b.ishistory='N' " +
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
		if (!"".equals(Common.get(getQ_differenceKind())))	//帳務日期
			SelSql += " and substring(a.enterdate,1,6) < " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2"));
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
		if (!"".equals(Common.get(getQ_noDeprSet())))		//不折舊設定
			SelSql += " and a.nodeprset = " + Common.sqlChar(getQ_noDeprSet());
		SelSql += " and a.apportionendym >= @budgetyms " +
		" and ( " +
		Common.sqlChar(getQ_propertyType()) + " = '' or " +
		Common.sqlChar(getQ_propertyType()) + " = '3' " +
		" )";
		return SelSql;
	}
	
	/**
	 * 動產
	 * @deprecated
	 */
	private String selectUMTMP_MOVABLE() throws Exception{
		String SelSql = "";
		SelSql = "" +
		" select " +
		Common.sqlChar(getUserID()) + " as editid," +
		Datetime.getYYYMMDD() + " as editdate," +
		Common.sqlChar(getQ_enterOrg()) + " as enterorg," +
		" a.propertyno," +
		" a.serialno,"+
		" case substring(a.propertyno,1,1) when '3' then '4' when '4' then '5' when '5' then '6' end as propertytype,"+
		" case substring(a.propertyno,1,1) when '3' then '機械及設備' when '4' then '交通及運輸設備' when '5' then '雜項設備' end as propertytypename,"+
		" d.propertyname,"+
		" a.propertyname1,"+
		" c.buydate,"+
		" isnull(a.otherlimityear, a.originallimityear * 12) limityear,"+
		" c.nameplate as nameplate,"+
		" c.specification as specification,"+
		" isnull(d.propertyunit,c.otherpropertyunit) as propertyunit,"+
		" a.bookamount as bookamount,"+
		//~ 這邊開始算折舊預估  budgetdepr   為了方便閱讀 以此種code style呈現
		" case    " + 
		"	when deprunitcb = 'N' " +  // 不需依比例計算 
		"		then     " + 
		"			case    " + 
		"				when apportionendym < @budgetyms   " +   // 已截止
		"					then 0   " + 
		"				when apportionendym = @budgetyms    " +  // 預估年月起 = 截止年月
		"					then isnull(monthdepr1,0)     " + 
		"				when @budgetyms < apportionendym and apportionendym <= @budgetyme    " +   // 截止年月 介於 預估年月之間, ex:10605 -> 月攤提折舊金額 * 4 + 最後一個月攤提折舊金額
		"					then monthdepr * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) + isnull(monthdepr1,0)     " + 
		"				else " +    // 截止年月 > 預估年月起訖   : 以 預估年月整年去計算, 不需考慮最後一個月的折舊攤提金額
		"                   isnull(monthdepr,0) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1)     " + 
		"			end     " + 
		"	else     " +              // 需依比例計算   case when 說明與上面相同  只是要考慮比例
		"		case    " + 
		"			when apportionendym < @budgetyms   " + 
		"				then 0   " + 
		"			when apportionendym = @budgetyms    " + 
		"				then ROUND(isnull(monthdepr1,0) * isnull(deprpercent,0) / 100,0,1)     " + 
		"			when @budgetyms <  apportionendym and  apportionendym <= @budgetyme    " + 
		"				then ROUND(isnull(monthdepr,0) * isnull(deprpercent,0) / 100,0,1) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, apportionendym + '01'))) + ROUND(isnull(monthdepr1,0) * isnull(deprpercent,0) / 100,0,1)     " + 
		"			else ROUND(isnull(monthdepr,0) * isnull(deprpercent,0) / 100,0,1) * (datediff(Month,convert(date, @budgetyms + '01'),convert(date, @budgetyme + '01')) + 1)   " + 
		"		end     " + 
		" end as budgetdepr, " +
		// ~ 折舊預估計算完畢
		Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2")) + " as qaccountsym," +
		" @budgetyms as qbudgetyms," +
		" @budgetyme as qbudgetyme," +
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
		Common.sqlChar(depraccountsMap.get(getQ_deprAccounts())) + " as qdepraccountsname," +
		" a.bookvalue, a.scrapvalue " +
		" from UNTMP_MOVABLEDETAIL_"+ getQ_accountsYM() + " a "+
		" left join UNTCH_DEPRPERCENT_"+ getQ_accountsYM() + " b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and a.propertyno=b.propertyno and a.serialno=b.serialno and b.ishistory='N' "+
		" left join UNTMP_MOVABLE_"+ getQ_accountsYM() + " c on a.enterorg=c.enterorg and a.ownership=c.ownership and a.differencekind=c.differencekind and a.propertyno=c.propertyno and a.lotno=c.lotno "+
		" left outer join SYSPK_PROPERTYCODE d on d.enterorg in ('000000000A',a.enterorg) and a.propertyno = d.propertyno " +
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
		if (!"".equals(Common.get(getQ_differenceKind())))	//帳務日期
			SelSql += " and substring(a.enterdate,1,6) < " + Common.sqlChar(Datetime.changeTaiwanYYMM(getQ_accountsYM(), "2"));	
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
		if (!"".equals(Common.get(getQ_noDeprSet())))		//不折舊設定
			SelSql += " and a.nodeprset = " + Common.sqlChar(getQ_noDeprSet());
		SelSql += " and a.apportionendym >= @budgetyms " +
		" and ( " +
		Common.sqlChar(getQ_propertyType()) + " = '' or " +
		Common.sqlChar(getQ_propertyType()) + " in ('4','5','6') " +
		" )";
		return SelSql;
	}
	
}