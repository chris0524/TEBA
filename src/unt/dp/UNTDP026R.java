/*
*<br>程式目的：財產折舊月報明細表(基金財產)
*<br>程式代號：untdp026r
*<br>撰寫日期：105/04/13
*<br>程式作者：Jim.Lin
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTDP026R extends SuperBean{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	String q_enterOrg;			//入帳機關
	String q_deprYM; 			//折舊年月
	String q_differenceKind; 	//財產區分別
	String q_page; 				//換頁方式
	String q_deprPark; 			//園區別
	String q_deprUnit; 			//部門別
	String q_deprUnit1; 		//部門別單位
	String q_deprAccounts;		//會計科目
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
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
		
		logger.info(Datetime.getHHMMSS()+":"+getOrganID()+" "+getUserName());
		
		Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
		Map<String,String> enterorgMap = TCGHCommon.getSysca_Organ();								//機關名稱
		Map<String,String> deprparkMap = TCGHCommon.getSysca_DeprPark(this.getQ_enterOrg());		//園區別名稱
		Map<String,String> deprunitMap = TCGHCommon.getSysca_DeprUnit(this.getQ_enterOrg());		//部門別名稱
		Map<String,String> deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());		//部門別單位名稱
		Map<String,String> propertynameMap = TCGHCommon.getSyspk_PropertyCode(this.getQ_enterOrg());//財產名稱
		Map<String,String> depraccountsnameMap = TCGHCommon.getSysca_DeprAccounts(this.getQ_enterOrg());//會計科目
		DefaultTableModel model = null;
		Database db = new Database();
		
		//跳頁方式：會計科目小計
		if ("3".equals(this.getQ_page())){
		try{
			if(!"".equals(getQ_enterOrg()) ||  !"".equals(getOrganID())){
				model = new javax.swing.table.DefaultTableModel();
				Vector rowData = new Vector();
				String[] columns ;
				columns = new String[]{
						"ENTERORGNAME",//印表人
						"DIFFERENCEKIND",//財產區分別
						"DEPRPARK",//園區別
						"DEPRUNIT",//部門別單位
						"DEPRDATEB",//日期起
						"DEPRDATEE",//日期迄
						"PROPERTYTYPE",//財產編號
						"BOOKAMOUNT",//帳面數量
						"BOOKVALUE",//原值
						"MONTHDEPR",//月折舊
						"NEWACCUMDEPR",//新折舊-累計折舊
						"NEWNETVALUE",//調整後帳面價值(淨值)－總價
						"DEPRUNIT1",//部門別單位
						"DEPRACCOUNTS"};//會計科目
				
					String execSQL = "select distinct "+
							"enterorg,differencekind,deprpark,deprunit,deprunit1,depraccounts, "+
							"propertytype,COUNT(*) as 'bookamount',SUM(bookvalue)as 'bookvalue', "+
							"SUM(monthdepr) as 'monthdepr',SUM(accumdepr)as 'accumdepr',SUM(netvalue)as 'newnetvalue' "+
							"from( "+
							"select "+
							"enterorg, "+
							"differencekind, "+
							"deprpark, "+
							"deprunit, "+
							"deprunit1, "+
							"depraccounts, "+
							"case deprunitcb "+
							"when 'N' then bookvalue "+
							"when 'Y' then scaledbookvalue end as 'bookvalue', "+
							"case deprunitcb  "+
							"when 'N' then monthdepr  "+
							"when 'Y' then scaledmonthdepr  "+
							"end as 'monthdepr', "+
							"case deprunitcb "+
							"when 'N' then newaccumdepr "+
							"when 'Y' then scalednewaccumdepr end as 'accumdepr', "+
							"case deprunitcb "+
							"when 'N' then newnetvalue  "+
							"when 'Y' then scalednewnetvalue "+ 
							"end as 'netvalue',  "+
							"case "+
							"when propertyno like '111%' then '111' "+
							"when propertyno like '2%' then '2' "+
							"when propertyno like '3%' then '3' "+
							"when propertyno like '4%' then '4' "+
							"when propertyno like '5%' then '5' "+
							"when propertyno like '8%' then '8'  "+
							"end as 'propertytype' "+
							"from UNTDP_MONTHDEPR "+
							"where 1=1 ";
					if (!"".equals(getQ_enterOrg())) {
						execSQL+=" and enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
					} else {
						if (!getIsAdminManager().equalsIgnoreCase("Y")) {
							if ("Y".equals(getIsOrganManager())) {
								execSQL += " and enterorg like '" + getOrganID().substring(0,5) + "%' ";
							} else {
								execSQL +=" and enterorg = " + Common.sqlChar(getOrganID()) ;
							}
						}
					}
					
					if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
						execSQL += " and differencekind = " + util.Common.sqlChar(getQ_differenceKind());
					if (!"".equals(Common.get(getQ_deprYM())))			//折舊年月
						execSQL += " and deprym = " + util.Common.sqlChar(Datetime.changeTaiwan(getQ_deprYM().substring(0, 3),"2") + getQ_deprYM().substring(3, 5));
					if (!"".equals(Common.get(getQ_deprPark())))		//園區別
						execSQL += " and deprpark = " + util.Common.sqlChar(getQ_deprPark());
					if (!"".equals(Common.get(getQ_deprUnit())))		//部門別
						execSQL += " and deprunit = " + util.Common.sqlChar(getQ_deprUnit());
					if (!"".equals(Common.get(getQ_deprUnit1())))		//部門單位
						execSQL += " and deprunit1 = " + util.Common.sqlChar(getQ_deprUnit1());
					if (!"".equals(Common.get(getQ_deprAccounts())))	//會計科目
						execSQL += " and depraccounts = " + util.Common.sqlChar(getQ_deprAccounts());
					execSQL += "and (propertyno like '111%' or propertyno like '2%' "+
							"or propertyno like '3%' or propertyno like '4%' or propertyno like '5%' or propertyno like '8%')"+
							") d group by enterorg,deprpark,deprunit,deprunit1,differencekind,depraccounts,propertytype";
							execSQL += "  order by "+
									" enterorg, " +
									" differencekind, " +
									" deprpark, " +
									" deprunit, " +
									" deprunit1, " +
									" depraccounts ,"+
									" propertytype"; 
					System.out.println(execSQL);
					ResultSet rs = db.querySQL(execSQL);

					int i = 0;
					while(rs.next()){
						i++;
						Object[] data = new Object[columns.length];
						data[0] = checkGet(enterorgMap.get(this.getQ_enterOrg()));
						data[1] = checkGet(differencekindMap.get(rs.getString("differencekind")));
						data[2] = checkGet(deprparkMap.get(rs.getString("deprpark")));
						data[3] = checkGet(deprunitMap.get(rs.getString("deprunit")));
						data[4] = checkGet(Common.formatYYYMMDD(getQ_deprYM()+ "01",2));
						int year = Integer.parseInt(getQ_deprYM().substring(0,3));
						int month = Integer.parseInt(getQ_deprYM().substring(3,5));
						data[5] = Common.formatYYYMMDD(getQ_deprYM() + Common.getLastDayOfMonth(year,month),2);
						
						if("111".equals(rs.getString("propertytype")))
							data[6] = "土地改良物";
						else if("2".equals(rs.getString("propertytype")))
							data[6] = "房屋建築及設備";
						else if("3".equals(rs.getString("propertytype")))	
							data[6] = "機械及設備";
						else if("4".equals(rs.getString("propertytype")))
							data[6] = "交通及運輸設備";
						else if("5".equals(rs.getString("propertytype")))
							data[6] = "雜項設備";
						else if("8".equals(rs.getString("propertytype")))
							data[6] = "權利";
						
						data[7] = rs.getLong("bookamount");;
						
						Double newnetvalue = 0D;
						try {
							data[8] = rs.getDouble("bookvalue");
						} catch(Exception e) {
							e.printStackTrace();
						}
						data[9] = rs.getDouble("monthdepr");
						data[10] = rs.getDouble("accumdepr");
						data[11] = rs.getDouble("newnetvalue");
						data[12] = checkGet(deprunit1Map.get(rs.getString("deprunit1")));
						data[13] = Common.get(rs.getString("depraccounts")) + "-" + checkGet(depraccountsnameMap.get(rs.getString("depraccounts")));
						rowData.addElement(data);
					}
					Object[][] data = new Object[0][0];
					data = (Object[][])rowData.toArray(data);
					model.setDataVector(data, columns);
			}
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			db.closeAll();
		}}
		
		//跳頁方式：部門別單位小計
		else if ("2".equals(this.getQ_page())){
			try{
				if(!"".equals(getQ_enterOrg()) ||  !"".equals(getOrganID())){
					model = new javax.swing.table.DefaultTableModel();
					Vector rowData = new Vector();
					String[] columns ;
					columns = new String[]{
							"ENTERORGNAME",//印表人0
							"DIFFERENCEKIND",//財產區分別1
							"DEPRPARK",//園區別2
							"DEPRUNIT",//部門別單位3
							"DEPRDATEB",//日期起4
							"DEPRDATEE",//日期迄5
							"PROPERTYTYPE",//財產編號6
							"BOOKAMOUNT",//帳面數量7
							"BOOKVALUE",//原值8
							"MONTHDEPR",//月折舊9
							"NEWACCUMDEPR",//累計折舊10
							"NEWNETVALUE",//調整後帳面價值(淨值)－總價11
							"DEPRUNIT1"};//部門別單位12
							
					
						String execSQL = "select distinct"+
								" enterorg,differencekind,deprpark,deprunit,deprunit1,"+
								" propertytype,COUNT(*) as 'bookamount',SUM(bookvalue)as 'bookvalue',"+
								" SUM(monthdepr) as 'monthdepr',SUM(accumdepr)as 'accumdepr',SUM(netvalue)as 'newnetvalue'"+
								" from("+
								" select"+
								" enterorg,differencekind,deprpark,"+
								" deprunit,deprunit1,"+
								" case deprunitcb"+
								" when 'N' then bookvalue"+
								" when 'Y' then scaledbookvalue end as 'bookvalue',"+
								"case deprunitcb  "+
								"when 'N' then monthdepr  "+
								"when 'Y' then scaledmonthdepr  "+
								"end as 'monthdepr', "+
								" case deprunitcb"+
								" when 'N' then newaccumdepr"+
								" when 'Y' then scalednewaccumdepr end as 'accumdepr',"+
								"case deprunitcb "+
								"when 'N' then newnetvalue  "+
								"when 'Y' then scalednewnetvalue "+ 
								"end as 'netvalue',  "+
								" case"+
								" when propertyno like '111%' then '111'"+
								" when propertyno like '2%' then '2'"+
								" when propertyno like '3%' then '3'"+
								" when propertyno like '4%' then '4'"+
								" when propertyno like '5%' then '5'"+
								" when propertyno like '8%' then '8' "+
								" end as 'propertytype'"+
								" from UNTDP_MONTHDEPR "+
								" where 1=1";
						if (!"".equals(getQ_enterOrg())) {
							execSQL+=" and enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
						} else {
							if (!getIsAdminManager().equalsIgnoreCase("Y")) {
								if ("Y".equals(getIsOrganManager())) {
									execSQL += " and enterorg like '" + getOrganID().substring(0,5) + "%' ";
								} else {
									execSQL +=" and enterorg = " + Common.sqlChar(getOrganID()) ;
								}
							} 
						}
						if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
							execSQL += " and differencekind = " + util.Common.sqlChar(getQ_differenceKind());
						if (!"".equals(Common.get(getQ_deprYM())))			//折舊年月
							execSQL += " and deprym = " + util.Common.sqlChar(Datetime.changeTaiwan(getQ_deprYM().substring(0, 3),"2") + getQ_deprYM().substring(3, 5));
						if (!"".equals(Common.get(getQ_deprPark())))		//園區別
							execSQL += " and deprpark = " + util.Common.sqlChar(getQ_deprPark());
						if (!"".equals(Common.get(getQ_deprUnit())))		//部門別
							execSQL += " and deprunit = " + util.Common.sqlChar(getQ_deprUnit());
						if (!"".equals(Common.get(getQ_deprUnit1())))		//部門單位
							execSQL += " and deprunit1 = " + util.Common.sqlChar(getQ_deprUnit1());
						if (!"".equals(Common.get(getQ_deprAccounts())))	//會計科目
							execSQL += " and depraccounts = " + util.Common.sqlChar(getQ_deprAccounts());
						execSQL += "and (propertyno like '111%' or propertyno like '2%' "+
								"or propertyno like '3%' or propertyno like '4%' or propertyno like '5%' or propertyno like '8%')"+
								") d group by enterorg,deprpark,deprunit,deprunit1,differencekind,propertytype";
								execSQL += "  order by "+
										" enterorg, " +
										" differencekind, " +
										" deprpark, " +
										" deprunit, " +
										" deprunit1 ,"+
										" propertytype"; 
						System.out.println(execSQL);
						ResultSet rs = db.querySQL(execSQL);

						int i = 0;
						while(rs.next()){
							i++;
							Object[] data = new Object[columns.length];
							data[0] = checkGet(enterorgMap.get(this.getQ_enterOrg()));
							data[1] = checkGet(differencekindMap.get(rs.getString("differencekind")));
							data[2] = checkGet(deprparkMap.get(rs.getString("deprpark")));
							data[3] = checkGet(deprunitMap.get(rs.getString("deprunit")));
							data[4] = checkGet(Common.formatYYYMMDD(getQ_deprYM()+ "01",2));
							int year = Integer.parseInt(getQ_deprYM().substring(0,3));
							int month = Integer.parseInt(getQ_deprYM().substring(3,5));
							data[5] = Common.formatYYYMMDD(getQ_deprYM() + Common.getLastDayOfMonth(year,month),2);
							
							if("111".equals(rs.getString("propertytype")))
								data[6] = "土地改良物";
							else if("2".equals(rs.getString("propertytype")))
								data[6] = "房屋建築及設備";
							else if("3".equals(rs.getString("propertytype")))	
								data[6] = "機械及設備";
							else if("4".equals(rs.getString("propertytype")))
								data[6] = "交通及運輸設備";
							else if("5".equals(rs.getString("propertytype")))
								data[6] = "雜項設備";
							else if("8".equals(rs.getString("propertytype")))
								data[6] = "權利";
							
							data[7] = rs.getLong("bookamount");;
							
							Double newnetvalue = 0D;
							try {
								data[8] = rs.getDouble("bookvalue");
							} catch(Exception e) {
								e.printStackTrace();
							}
							data[9] = rs.getDouble("monthdepr");
							data[10] = rs.getDouble("accumdepr");
							data[11] = rs.getDouble("newnetvalue");
							data[12] = checkGet(deprunit1Map.get(rs.getString("deprunit1")));
							rowData.addElement(data);
						}
						Object[][] data = new Object[0][0];
						data = (Object[][])rowData.toArray(data);
						model.setDataVector(data, columns);
				}
			}catch(Exception x){
				x.printStackTrace();
			}finally{
				db.closeAll();
			}}
		
		
		//跳頁方式：部門別小計
		else if ("1".equals(this.getQ_page())){
			try{
				if(!"".equals(getQ_enterOrg()) ||  !"".equals(getOrganID())){
					model = new javax.swing.table.DefaultTableModel();
					Vector rowData = new Vector();
					String[] columns ;
					columns = new String[]{
							"ENTERORGNAME",//印表人0
							"DIFFERENCEKIND",//財產區分別1
							"DEPRPARK",//園區別2
							"DEPRUNIT",//部門別單位3
							"DEPRDATEB",//日期起4
							"DEPRDATEE",//日期迄5
							"PROPERTYTYPE",//財產編號6
							"BOOKAMOUNT",//帳面數量7
							"BOOKVALUE",//原值8
							"MONTHDEPR",//月折舊9
							"NEWACCUMDEPR",//新折舊-累計折舊10
							"NEWNETVALUE"};//調整後帳面價值(淨值)－總價11
							
					
						String execSQL = " select distinct"+
								" enterorg,differencekind,deprpark,deprunit,"+
								" propertytype,COUNT(*) as 'bookamount',SUM(bookvalue)as 'bookvalue',"+
								" SUM(monthdepr) as 'monthdepr',SUM(accumdepr)as 'accumdepr',SUM(netvalue)as 'newnetvalue'"+
								" from("+
								" select"+
								" enterorg,differencekind,deprpark,"+
								" deprunit,"+
								" case deprunitcb"+
								" when 'N' then bookvalue"+
								" when 'Y' then scaledbookvalue end as 'bookvalue',"+
								"case deprunitcb  "+
								"when 'N' then monthdepr  "+
								"when 'Y' then scaledmonthdepr  "+
								"end as 'monthdepr', "+
								" case deprunitcb"+
								" when 'N' then newaccumdepr"+
								" when 'Y' then scalednewaccumdepr end as 'accumdepr',"+
								"case deprunitcb "+
								"when 'N' then newnetvalue  "+
								"when 'Y' then scalednewnetvalue "+ 
								"end as 'netvalue',  "+
								" case"+
								" when propertyno like '111%' then '111'"+
								" when propertyno like '2%' then '2'"+
								" when propertyno like '3%' then '3'"+
								" when propertyno like '4%' then '4'"+
								" when propertyno like '5%' then '5'"+
								" when propertyno like '8%' then '8' "+
								" end as 'propertytype'"+
								" from UNTDP_MONTHDEPR "+
								"where 1=1 ";
						if (!"".equals(getQ_enterOrg())) {
							execSQL+=" and enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
						} else {
							if (!getIsAdminManager().equalsIgnoreCase("Y")) {
								if ("Y".equals(getIsOrganManager())) {
									execSQL += " and enterorg like '" + getOrganID().substring(0,5) + "%' ";
								} else {
									execSQL +=" and enterorg = " + Common.sqlChar(getOrganID()) ;
								}
							} 
						}
						if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
							execSQL += " and differencekind = " + util.Common.sqlChar(getQ_differenceKind());
						if (!"".equals(Common.get(getQ_deprYM())))			//折舊年月
							execSQL += " and deprym = " + util.Common.sqlChar(Datetime.changeTaiwan(getQ_deprYM().substring(0, 3),"2") + getQ_deprYM().substring(3, 5));
						if (!"".equals(Common.get(getQ_deprPark())))		//園區別
							execSQL += " and deprpark = " + util.Common.sqlChar(getQ_deprPark());
						if (!"".equals(Common.get(getQ_deprUnit())))		//部門別
							execSQL += " and deprunit = " + util.Common.sqlChar(getQ_deprUnit());
						execSQL += "and (propertyno like '111%' or propertyno like '2%' "+
								"or propertyno like '3%' or propertyno like '4%' or propertyno like '5%' or propertyno like '8%')"+
								") d group by enterorg,deprpark,deprunit,differencekind,propertytype";
								execSQL += "  order by "+
								" enterorg, " +
									" differencekind, " +
									" deprpark, " +
									" deprunit ,"+
									" propertytype"; 

						System.out.println(execSQL);
						ResultSet rs = db.querySQL(execSQL);

						int i = 0;
						while(rs.next()){
							i++;
							Object[] data = new Object[columns.length];
							data[0] = checkGet(enterorgMap.get(this.getQ_enterOrg()));
							data[1] = checkGet(differencekindMap.get(rs.getString("differencekind")));
							data[2] = checkGet(deprparkMap.get(rs.getString("deprpark")));
							data[3] = checkGet(deprunitMap.get(rs.getString("deprunit")));
							data[4] = checkGet(Common.formatYYYMMDD(getQ_deprYM()+ "01",2));
							int year = Integer.parseInt(getQ_deprYM().substring(0,3));
							int month = Integer.parseInt(getQ_deprYM().substring(3,5));
							data[5] = Common.formatYYYMMDD(getQ_deprYM() + Common.getLastDayOfMonth(year,month),2);
							
							if("111".equals(rs.getString("propertytype")))
								data[6] = "土地改良物";
							else if("2".equals(rs.getString("propertytype")))
								data[6] = "房屋建築及設備";
							else if("3".equals(rs.getString("propertytype")))	
								data[6] = "機械及設備";
							else if("4".equals(rs.getString("propertytype")))
								data[6] = "交通及運輸設備";
							else if("5".equals(rs.getString("propertytype")))
								data[6] = "雜項設備";
							else if("8".equals(rs.getString("propertytype")))
								data[6] = "權利";
							data[7] = rs.getLong("bookamount");;
							
							Double newnetvalue = 0D;
							try {
								data[8] = rs.getDouble("bookvalue");
							} catch(Exception e) {
								e.printStackTrace();
							}

							data[9] = rs.getDouble("monthdepr");
							data[10] = rs.getDouble("accumdepr");
							data[11] = rs.getDouble("newnetvalue");
							rowData.addElement(data);
						}
						Object[][] data = new Object[0][0];
						data = (Object[][])rowData.toArray(data);
						model.setDataVector(data, columns);
				}
			}catch(Exception x){
				x.printStackTrace();
			}finally{
				db.closeAll();
			}}
		
		return model;
	
	}
}
