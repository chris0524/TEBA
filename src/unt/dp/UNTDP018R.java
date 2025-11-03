/*
*<br>程式目的：財產折舊月報明細表(代管資產)(竹科管局格式)
*<br>程式代號：untdp018r
*<br>撰寫日期：103/09/01
*<br>程式作者：Anthony.Wang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTDP018R extends SuperBean{
	private String q_enterOrg;			//入帳機關
	private String q_deprYM; 			//折舊年月
	private String q_differenceKind; 	//財產區分別
	private String q_page; 				//換頁方式
	private String q_deprPark; 			//園區別
	private String q_deprUnit; 			//部門別
	private String q_deprUnit1; 		//部門別單位
	private String q_deprAccounts;		//會計科目
	private String q_propertyType;		//財產大類
	private String q_reportType;		// 報表類型
	
	public String getQ_reportType() {	return checkGet(q_reportType);}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_page(){ return checkGet(q_page); }
	public void setQ_page(String s){ q_page=checkSet(s); }
	public String getQ_deprPark(){ return checkGet(q_deprPark); }
	public void setQ_deprPark(String s){ q_deprPark=checkSet(s); }
	public String getQ_deprUnit(){ return checkGet(q_deprUnit); }
	public void setQ_deprUnit(String s){ q_deprUnit=checkSet(s); }
	public String getQ_deprUnit1(){ return checkGet(q_deprUnit1); }
	public void setQ_deprUnit1(String s){ q_deprUnit1=checkSet(s); }
	public String getQ_deprAccounts(){ return checkGet(q_deprAccounts); }
	public void setQ_deprAccounts(String s){ q_deprAccounts=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	
	String isOrganManager;
	String isAdminManager;
	String organID;  
	String userName;  
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	public String getUserName() { return checkGet(userName); }
	public void setUserName(String s) { userName = checkSet(s); } 

	
	public DefaultTableModel getResultModel() throws Exception{
		
		Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
		Map<String,String> propertytypeMap = TCGHCommon.getSysca_Code("PTT"); 						//財產大類名稱
		Map<String,String> enterorgMap = TCGHCommon.getSysca_Organ();								//機關名稱
		Map<String,String> deprparkMap = TCGHCommon.getSysca_DeprPark(this.getQ_enterOrg());		//園區別名稱
		Map<String,String> deprunitMap = TCGHCommon.getSysca_DeprUnit(this.getQ_enterOrg());		//部門別名稱
		Map<String,String> deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());		//部門別單位名稱
		Map<String,String> propertynameMap = TCGHCommon.getSyspk_PropertyCode(this.getQ_enterOrg());//財產名稱
		
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Database db = new Database();
		try{
			String[] columns ;
			columns = new String[]{
					"ENTERORGNAME",
					"DIFFERENCEKIND",
					"DEPRPARK",
					"DEPRUNIT",
					"PROPERTYTYPE",
					"DEPRDATEB",
					"DEPRDATEE",
					"PROPERTYNO",
					"SERIALNO",
					"PROPERTYNAME1",
					"BUYDATE",
					"SCRAPVALUE",
					"LIMITYEAR",
					"BOOKAMOUNT",
					"BOOKVALUE",
					"MONTHDEPR",
					"NEWACCUMDEPR",
					"NEWNETVALUE",
					"DEPRPERCENT",
					"PROPERTYNAME",
					"DEPRUNIT1",
					"OLDSERIALNO",
					"DEPRACCOUNTS"};

			
			String execSQL=" select " +
			" a.enterorg, " +
			" a.differencekind, " +
			" a.deprpark, " +
			" a.deprunit, " +
			" a.deprunit1, " +
			" a.propertytype, " +
//			" b.organaname, " +
//			" c.codename as 'differencekind', " +
//			" e.deprparkname as 'deprpark', " +
//			" f.deprunitname as 'deprunit', " +
//			" d.codename as 'propertytype', " +
			" a.propertyno, " +
			" a.serialno, " +
			" a.propertyname1, " +
			" a.buydate , " +
			" a.scrapvalue, " +
			" limityear, " +
			" bookamount, " +
			" case a.deprunitcb when 'N' then a.bookvalue when 'Y' then a.scaledbookvalue end as 'bookvalue', " +
			" case a.deprunitcb when 'N' then a.monthdepr when 'Y' then a.scaledmonthdepr end as 'monthdepr', " +
			" case a.deprunitcb when 'N' then a.newaccumdepr when 'Y' then a.scalednewaccumdepr end as 'newaccumdepr', " +
			" case a.deprunitcb when 'N' then a.newnetvalue when 'Y' then a.scalednewnetvalue end as 'newnetvalue', " +
			" deprpercent, " +
			" a.deprunitcb, " +
			" a.oldserialno, "+
			" a.depraccounts, b.depraccountsname " +
			" from " +
			" UNTDP_MONTHDEPR a " +
			" left join SYSCA_DEPRACCOUNTS b on (a.depraccounts = b.depraccountsno and a.enterorg = b.enterorg) " +
//			" inner join SYSCA_ORGAN b on a.enterorg = b.organid " +
//			" inner join SYSCA_CODE c on a.differencekind = c.codeid and c.codekindid = 'DFK' " +
//			" inner join SYSCA_CODE d on a.propertytype = d.codeid and d.codekindid = 'PTT' " +
//			" inner join SYSCA_DEPRPARK e on a.enterorg = e.enterorg and a.deprpark = e.deprparkno " +
//			" inner join SYSCA_DEPRUNIT f on a.enterorg = f.enterorg and a.deprunit = f.deprunitno " +
			" where 1=1 ";
			if (!"".equals(getQ_enterOrg())) {
				execSQL+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(getIsOrganManager())) {					
						execSQL += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
					} else {
						execSQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
					}
				} 
			}
			if (!"".equals(Common.get(getQ_deprYM())))	//折舊年月
				execSQL += " and a.deprym = " + util.Common.sqlChar(Datetime.changeTaiwan(getQ_deprYM().substring(0, 3),"2") + getQ_deprYM().substring(3, 5));
			if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
				execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
			if (!"".equals(Common.get(getQ_deprPark())))	//園區別
				execSQL += " and a.deprpark = " + util.Common.sqlChar(getQ_deprPark());
			if (!"".equals(Common.get(getQ_deprUnit())))	//部門別
				execSQL += " and a.deprunit = " + util.Common.sqlChar(getQ_deprUnit());
			if (!"".equals(Common.get(getQ_deprUnit1())))	//部門單位
				execSQL += " and a.deprunit1 = " + util.Common.sqlChar(getQ_deprUnit1());
			if (!"".equals(Common.get(getQ_deprAccounts())))	//會計科目
				execSQL += " and a.depraccounts = " + util.Common.sqlChar(getQ_deprAccounts());
			if (!"".equals(Common.get(getQ_propertyType())))	//財產大類
				execSQL += " and a.propertytype = " + util.Common.sqlChar(getQ_propertyType());
			execSQL += " order by ";
			if ("1".equals(getQ_page())) { //跳頁方式:部門別
				execSQL += " enterorg, " +
				" differencekind, " +
				" deprpark, " +
				" deprunit, " +
				" propertytype, " +
				" propertyno, " +
				" serialno" ;
			} else if ("2".equals(getQ_page())) { //跳頁方式:部門別單位
				execSQL += " enterorg, " +
				" differencekind, " +
				" deprpark, " +
				" deprunit, " +
				" deprunit1, " +
				" propertytype, " +
				" propertyno, " +
				" serialno" ;
			} else if ("3".equals(getQ_page())) { //跳頁方式:財產大類
				execSQL += " enterorg, " +
				" differencekind, " +
				" propertytype, " +
				" propertyno, " +
				" serialno" ;
			} else if ("4".equals(this.getQ_page())) {
				execSQL += " enterorg, " +
						   " differencekind, " +
						   " deprpark, " +
						   " deprunit, " +
						   " deprunit1, " +
						   " depraccounts, " +
						   " propertytype, " +
						   " propertyno, " +
						   " serialno" ;
			}
	
			System.out.println(execSQL);
			ResultSet rs = db.querySQL(execSQL);
			Vector rowData = new Vector();
			int i = 0;
			while(rs.next()){
				i++;
				Object[] data = new Object[columns.length];
//				data[0] = rs.getString("organaname");
//				data[1] = rs.getString("differencekind");
//				data[2] = rs.getString("deprpark");
//				data[3] = rs.getString("deprunit");
//				data[4] = rs.getString("propertytype");
				data[0] = checkGet(enterorgMap.get(this.getQ_enterOrg()));
				data[1] = checkGet(differencekindMap.get(rs.getString("differencekind")));
				data[2] = checkGet(deprparkMap.get(rs.getString("deprpark")));
				data[3] = checkGet(deprunitMap.get(rs.getString("deprunit")));
				data[4] = checkGet(propertytypeMap.get(rs.getString("propertytype")));
				data[5] = checkGet(Common.formatYYYMMDD(getQ_deprYM()+ "01",2));
				int year = Integer.parseInt(getQ_deprYM().substring(0,3));
				int month = Integer.parseInt(getQ_deprYM().substring(3,5));
				data[6] = checkGet(Common.formatYYYMMDD(getQ_deprYM() + Common.getLastDayOfMonth(year,month),2));
				String propertyno = rs.getString("propertyno").toString();
				if (propertyno.length() > 7) {
					data[7] = checkGet(propertyno.substring(0, 7)) + "-" + checkGet(propertyno.substring(7));
				} else {
					data[7] = checkGet(propertyno);
				}
				data[8] = checkGet(rs.getString("serialno"));
				data[9] = checkGet(rs.getString("propertyname1"));
				data[10] = checkGet(Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buydate"),"1")));
				data[11] = checkGet(rs.getString("scrapvalue"));
				data[12] = Common.formatOtherLimityear(1, Common.get(rs.getString("limityear")));
				data[13] = rs.getLong("bookamount");
				data[14] = rs.getDouble("bookvalue");
				data[15] = rs.getDouble("monthdepr");
				data[16] = rs.getDouble("newaccumdepr");
				data[17] = rs.getDouble("newnetvalue");
				data[18] = (("Y".equals(rs.getString("deprunitcb")))? "約" : "") + checkGet(rs.getString("deprpercent"));
				data[19] = checkGet(propertynameMap.get(rs.getString("propertyno")));
				data[20] = deprunit1Map.get(rs.getString("deprunit1"));
//				if(!checkGet(rs.getString("oldserialno")).equals("")){
//				data[21] = rs.getString("propertyno").toString()+ "-" + checkGet(rs.getString("serialno"))+"(原財產分號:"+checkGet(rs.getString("oldserialno")) +")"+ checkGet(propertynameMap.get(rs.getString("propertyno")));;
//				}
//				else{
				data[21] =	rs.getString("propertyno").toString()+ "-" + checkGet(rs.getString("serialno"))+"\n"+ checkGet(propertynameMap.get(rs.getString("propertyno")));
//				}
				// 會計科目
				data[22] = Common.get(rs.getString("depraccounts")) + "-" + Common.get(rs.getString("depraccountsname"));
				rowData.addElement(data);
			}
			
//			for (int j = 0 ; j <= 39;j++) {
//			i++;
//			Object[] data = new Object[20];
//			if ("2".equals(this.getQ_page())) { //跳頁方式:部門別
//				data = new Object[21];
//			}
//			
//			data[0] = "科技部";
//			data[1] =  "財產區分別" + j/40;
//			data[2] = "園區別" + j/20;
//			data[3] = "部門別" + j/10;;
//			data[4] = "財產大類" + j/5;;
//			data[5] = Common.formatYYYMMDD(getQ_deprYM()+ "01",2);
//			int year = Integer.parseInt(getQ_deprYM().substring(0,3));
//			int month = Integer.parseInt(getQ_deprYM().substring(3,5));
//			data[6] = Common.formatYYYMMDD(getQ_deprYM() + Common.getLastDayOfMonth(year,month),2);
//			data[7] =  "3000";
//			data[8] = "4000";
//			data[9] = "5000";
//			data[10] = Common.MaskDate("1030701");
//			data[11] = "6000";
//			data[12] =  "7000";
//			data[13] = 34l;
//			data[14] = 2000d;
//			data[15] = 3000d;
//			data[16] = 4000d;
//			data[17] = 5000d;
//			data[18] = "1";
//			data[19] = "財產名稱";
//			if ("2".equals(this.getQ_page())) { //跳頁方式:部門別
//				data[20] = "部門別單位";
//			}
//			
//			rowData.addElement(data);
//		}
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
}
