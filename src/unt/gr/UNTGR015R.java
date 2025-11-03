package unt.gr;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.TCGHCommon;
import TDlib_Simple.tools.src.LogTools;

/*
*<br>程式目的：財產檢查單 
*<br>程式代號：UNTGR015R
*<br>撰寫日期：103/09/02
*<br>程式作者：Jerry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
public class UNTGR015R extends SuperBean{
		
	private String q_enterOrg;
	private String q_enterOrgName;
	private String userName;
	private String q_ownership;
	private String q_propertyNoS;
	private String q_propertyNoE;
	private String q_differenceKind;
	private String q_propertyKind1;
	private String q_keepUnit;
	private String q_keeper;
	private String q_useUnit;
	private String q_user;
	private String q_userNote;
	private String q_place1;
	private String q_place;
	private String q_dataState;
	private String q_verify;
	private String q_pageWay;
	private String q_propertyName1;
	private String q_propertyNoSName;
	private String q_propertyNoEName;
	private String isOrganManager;
	private String isAdminManager;
	private String organID;
	private String fileName;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getUserName(){ return checkGet(userName); }
	public void setUserName(String s){ userName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
    public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
    public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
    public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
    public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_propertyKind1(){ return checkGet(q_propertyKind1); }
	public void setQ_propertyKind1(String s){ q_propertyKind1=checkSet(s); }
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }
	public String getQ_useUnit(){ return checkGet(q_useUnit); }
	public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
	public String getQ_user(){ return checkGet(q_user); }
	public void setQ_user(String s){ q_user=checkSet(s); }
	public String getQ_userNote(){ return checkGet(q_userNote); }
	public void setQ_userNote(String s){ q_userNote=checkSet(s); }
	public String getQ_place1(){ return checkGet(q_place1); }
	public void setQ_place1(String s){ q_place1=checkSet(s); }
	public String getQ_place(){ return checkGet(q_place); }
	public void setQ_place(String s){ q_place=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_pageWay(){ return checkGet(q_pageWay); }
	public void setQ_pageWay(String s){ q_pageWay=checkSet(s); }
	public String getQ_propertyName1(){ return checkGet(q_propertyName1); }
	public void setQ_propertyName1(String s){ q_propertyName1=checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	private LogTools log = new LogTools();
	

	public DefaultTableModel getResultModel() throws Exception{
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		StringBuilder stb = new StringBuilder();
		
		Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
		Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(this.getQ_enterOrg());			//存置地點名稱
		Map<String,String> KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(this.getQ_enterOrg());	//保管單位使用單位名稱
		Map<String,String> KeeperMap = TCGHCommon.getUntmp_KeeperCode(this.getQ_enterOrg());		//保管人使用人名稱
		Map<String,String> propertytypeMap = TCGHCommon.getSysca_Code("PTT"); 						//財產大類名稱
		
		try{
			String[] columns = new String[] {
				"DIFFERENCEKIND","PROPERTYTYPE","COLUMNS_1","COLUMNS_2",
				"COLUMNS_3","COLUMNS_4","COLUMNS_5","COLUMNS_6",
				"COLUMNS_7","COLUMNS_8","COLUMNS_9","COLUMNS_10",
				"COLUMNS_11","COLUMNS_12","COLUMNS_13","COLUMNS_14",
				"KEEPUNIT","KEEPER","KEEPUNITNAME","KEEPERNAME","PROPERTYNO"};
		
			//=================================================================
			
			if ("1".equals(getQ_propertyKind1())) {
				stb = doGetSQLforUNTMP_MOVABLEDETAIL(stb);
			} else {
				stb = doGetSQLforUNTNE_NONEXP(stb);
			}
			
			
			//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================
			
			db = new Database();
			Vector<Object[]> rowData = new Vector<Object[]>();		    
			int i=0;
			ResultSet rs = db.querySQL_NoChange(stb.toString());
			String SystemDate = Datetime.getYYYYMM();
			while(rs.next()){
				Object[] data = new Object[columns.length];

				
				data[0] = differencekindMap.get(rs.getString("differencekind"));//區分別
				String propertyno = checkGet(rs.getString("propertyno")).toString();
				if ("1".equals(getQ_pageWay())) {
					data[1] = checkGet(KeeperMap.get(rs.getString("keeper")));//保管人
				} else {
					
					if (propertyno.length() >=1) {
						if ("3".equals(propertyno.substring(0,1))) {
							data[1] = checkGet(propertytypeMap.get("4"));//財產大類
						} else if ("4".equals(propertyno.substring(0,1))) {
							data[1] = checkGet(propertytypeMap.get("5"));//財產大類
						} else if ("5".equals(propertyno.substring(0,1))) {
							data[1] = checkGet(propertytypeMap.get("6"));//財產大類
						} 
					} else {
						data[1] = "";//財產大類
					}
				}
				if (propertyno.length() > 7) {
					data[2] = checkGet(propertyno) + "-" + checkGet(rs.getString("serialno")) + "\n" + checkGet(rs.getString("propertyname"));
				} else {
					data[2] = checkGet(propertyno) + "\n" + checkGet(rs.getString("propertyname"));
				}
				data[3] = checkGet(rs.getString("propertyname1"));
				data[4] = checkGet(rs.getString("specification")) + "\n" + checkGet(rs.getString("nameplate"));
				data[5] = rs.getString("bookamount") + "\n" + rs.getString("propertyunit");
				String buydate = checkGet(rs.getString("buydate"));
				data[6] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(buydate, "1")) + "\n" + Common.MaskDate(Datetime.changeTaiwanYYMMDD(checkGet(rs.getString("sourcedate")), "1"));
				
				int UseYearMonth = Datetime.BetweenTwoMonth(Datetime.changeTaiwanYYMM(buydate,"1"),Datetime.changeTaiwanYYMM(SystemDate,"1"));
				// 問題單1626: 使用年限、已使用年限報表顯示方式XX年XX月
				data[7] = checkGet(rs.getString("limityear"))+"年0月"+ "\n" + String.valueOf(UseYearMonth/12)+"年"+String.valueOf(UseYearMonth%12)+"月";
				
				data[8] = checkGet(placeMap.get(rs.getString("place1")));
				data[9] = checkGet(KeepUnitMap.get(rs.getString("keepunit"))) + "\n" + checkGet(KeepUnitMap.get(rs.getString("useunit")));
				data[10] = checkGet(KeeperMap.get(rs.getString("keeper"))) + "\n" + checkGet(KeeperMap.get(rs.getString("userno")));
				data[11] = checkGet(rs.getString("specification1"));
				data[12] = checkGet(rs.getString("nameplate1"));
				data[13] = checkGet(rs.getString("equipmentamount1"));
				data[14] = checkGet(rs.getString("totalvalue"));
				data[15] = "";
				data[16] = checkGet(rs.getString("keepunit"));
				data[17] = checkGet(rs.getString("keeper"));
				data[18] = checkGet(KeepUnitMap.get(rs.getString("keepunit")));
				data[19] = checkGet(KeeperMap.get(rs.getString("keeper")));
				data[20] = checkGet(rs.getString("propertyno")).toString();
				rowData.addElement(data);
				i++;
			}
			
			//=================================================================
			Object[][] data = new Object[0][0];
			data = (Object[][])rowData.toArray(data);
			model.setDataVector(data, columns);
		}catch(Exception x){
			log._execLogError(x.getMessage());
		}finally{
			db.closeAll();
		}
		return model;
	}
	
	private StringBuilder doGetSQLforUNTMP_MOVABLEDETAIL(StringBuilder stb){
		stb.append(" select ")
		.append(" m.differencekind, ")
		.append(" m.propertyno, ")
		.append(" d.serialno, ")
		.append(" c.propertyname, ")
		.append(" m.propertyname1, ")
		.append(" m.nameplate, ")
		.append(" m.specification, ")
		.append(" d.bookamount, ")
		.append(" ISNULL(c.propertyunit,'') as propertyunit, ")
		.append(" m.sourcedate, ")
		.append(" m.buydate, ")
		.append(" c.limityear, ")
		.append(" d.place1, ")
		.append(" d.keepunit, ")
		.append(" d.useunit, ")
		.append(" d.userno, ")
		.append(" d.keeper, ")
		.append(" a.specification as specification1, ")
		.append(" a.nameplate as nameplate1, ")
		.append(" a.equipmentamount as equipmentamount1, ")
		.append(" a.totalvalue as totalvalue ")
		.append(" from ")
		.append(" UNTMP_MOVABLE m ")
		.append(" inner join UNTMP_MOVABLEDETAIL d on ")
		.append(" d.enterorg=m.enterorg ")
		.append(" and d.ownership=m.ownership ")
		.append(" and d.differencekind = m.differencekind ")
		.append(" and d.propertyno=m.propertyno ")
		.append(" and d.lotno=m.lotno ")
		.append(" left outer join UNTMP_ATTACHMENT2 a on ")
		.append(" a.enterorg = d.enterorg ")
		.append(" and a.ownership = d.ownership ")
		.append(" and a.differencekind = d.differencekind ")
		.append(" and a.propertyno = d.propertyno ")
		.append(" and a.serialno = d.serialno ")
		.append(" left outer join SYSPK_PROPERTYCODE c on ")
		.append(" c.enterorg in (d.enterorg,'000000000A')  ") 
		.append(" and m.propertyno = c.propertyno ")
		.append(" where 1=1 ")
		.append(whereCon());
		if ("1".equals(getQ_pageWay())) {
			stb.append(" order by d.keeper,d.propertyno,d.serialno ");
		} else if("2".equals(getQ_pageWay())){
			stb.append(" order by d.differencekind,d.propertyno,d.serialno ");
		}else if("3".equals(getQ_pageWay())){
			stb.append(" order by  d.keepunit,d.keeper");
		}else if("4".equals(getQ_pageWay())){
			stb.append(" order by d.keepunit,d.propertyno ");
		}
		
		return stb;
	}
	
	private StringBuilder doGetSQLforUNTNE_NONEXP(StringBuilder stb){
		stb.append(" select ")
		.append(" m.differencekind, ")
		.append(" m.propertyno, ")
		.append(" d.serialno, ")
		.append(" c.propertyname, ")
		.append(" m.propertyname1, ")
		.append(" m.nameplate, ")
		.append(" m.specification, ")
		.append(" d.bookamount, ")
		.append(" ISNULL(c.propertyunit,'') as propertyunit, ")
		.append(" m.sourcedate, ")
		.append(" m.buydate, ")
		.append(" c.limityear, ")
		.append(" d.place1, ")
		.append(" d.keepunit, ")
		.append(" d.useunit, ")
		.append(" d.userno, ")
		.append(" d.keeper, ")
		.append(" a.specification as specification1, ")
		.append(" a.nameplate as nameplate1, ")
		.append(" a.equipmentamount as equipmentamount1, ")
		.append(" a.totalvalue as totalvalue ")
		.append(" from ")
		.append(" UNTNE_NONEXP m ")
		.append(" inner join UNTNE_NONEXPDETAIL d on ")
		.append(" d.enterorg=m.enterorg ")
		.append(" and d.ownership=m.ownership ")
		.append(" and d.differencekind = m.differencekind ")
		.append(" and d.propertyno=m.propertyno ")
		.append(" and d.lotno=m.lotno ")
		.append(" left outer join UNTNE_ATTACHMENT2 a on ")
		.append(" a.enterorg = d.enterorg ")
		.append(" and a.ownership = d.ownership ")
		.append(" and a.differencekind = d.differencekind ")
		.append(" and a.propertyno = d.propertyno ")
		.append(" and a.serialno = d.serialno ")
		.append(" left outer join SYSPK_PROPERTYCODE2 c on ")
		.append(" c.enterorg in (d.enterorg,'000000000A')  ") 
		.append(" and m.propertyno = c.propertyno ")
		.append(" where 1=1 ")
		.append(whereCon());
		if ("1".equals(getQ_pageWay())) {
			stb.append(" order by d.keeper,d.propertyno,d.serialno ");
		} else if("2".equals(getQ_pageWay())){
			stb.append(" order by d.differencekind,d.propertyno,d.serialno ");
		}else if("3".equals(getQ_pageWay())){
			stb.append(" order by  d.keepunit,d.keeper");
		}else if("4".equals(getQ_pageWay())){
			stb.append(" order by d.keepunit,d.propertyno ");
		}
		return stb;
	}
	
	// 查詢條件
	private String whereCon() {
		String whereCon = "" ;
		if (!"".equals(getQ_enterOrg())) {
			whereCon+=" and d.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					whereCon += " and d.enterorg like '" + getOrganID().substring(0,5) + "%' ";
				} else {
					whereCon +=" and d.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			} 
		}
		if (!"".equals(Common.get(getQ_ownership())))		//權屬
			whereCon += " and d.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
			whereCon += " and d.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
		if (!"".equals(Common.get(getQ_propertyNoS())))	//財產編號
			whereCon += " and d.propertyno >= " + util.Common.sqlChar(getQ_propertyNoS());
		if (!"".equals(Common.get(getQ_propertyNoE())))	//財產編號
			whereCon += " and d.propertyno <= " + util.Common.sqlChar(getQ_propertyNoE());
		if (!"".equals(Common.get(getQ_keepUnit())))		//保管單位
			whereCon += " and d.keepunit = " + util.Common.sqlChar(getQ_keepUnit());
		if (!"".equals(Common.get(getQ_keeper())))			//保管人
			whereCon += " and d.keeper = " + util.Common.sqlChar(getQ_keeper());
		if (!"".equals(Common.get(getQ_useUnit())))			//使用單位
			whereCon += " and d.useunit = " + util.Common.sqlChar(getQ_useUnit());
		if (!"".equals(Common.get(getQ_user())))			//使用人
			whereCon += " and d.userno = " + util.Common.sqlChar(getQ_user());
		if (!"".equals(Common.get(getQ_userNote())))		//使用註記
			whereCon += " and d.usernote  like " + util.Common.sqlChar("%"+getQ_userNote()+"%");
		if (!"".equals(Common.get(getQ_place1())))			//存置地點
			whereCon += " and d.place1 = " + util.Common.sqlChar(getQ_place1());
		if (!"".equals(Common.get(getQ_place())))			//存置地點說明
			whereCon += " and d.place like " + util.Common.sqlChar("%" + getQ_place() + "%");
		if (!"".equals(Common.get(getQ_propertyName1())))	//財產別名
			whereCon += " and m.propertyname1 like " + util.Common.sqlChar("%" + getQ_propertyName1() + "%");
		if (!"".equals(Common.get(getQ_dataState())))		//資料狀態
			whereCon += " and d.datastate = " + util.Common.sqlChar(getQ_dataState());
		if (!"".equals(Common.get(getQ_verify())))			//入帳
			whereCon += " and d.verify = " + util.Common.sqlChar(getQ_verify());
		 
		return whereCon;
	}
}
