package unt.rp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.*;
import util.report.ReportUtil;

/**
 *<br>程式目的：財產保管人清冊報表檔 
 *<br>程式代號：untrp010r
 *<br>撰寫日期：103/08/12
 *<br>程式作者：Anthony.Wang
 *<br>--------------------------------------------------------
 *<br>修改作者　　修改日期　　　修改目的
 *<br>--------------------------------------------------------
 */
public class UNTRP010R extends SuperBean {
	
	String keepunit;//保管單位
	String keeper;//保管人
	String useunit;//使用單位
	String user;//使用人
	
	String q_enterOrg; //入帳機關
	String q_enterOrgName;
	String q_ownership; //權屬
	String q_valuable; //珍貴財產
	String q_propertyType; //財產大類
	String q_propertyKind; //財產區分別
	String q_propertyName1; //財產別名
	String q_place1; //存置地點
	String q_place; //存置地點說明
	String engineering;//工程編號
	String q_dataState;
	String q_verify;
	String q_keepBureau;
	String q_fundType;
	String q_print;
	String q_differenceKind;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_serialNoS;
	String q_serialNoE;
	String reportType;
	
	
	public String getReportType() { return reportType; } 
	public void setReportType(String reportType) { this.reportType = reportType; }
	public String getQ_propertyName1() { return q_propertyName1; }
	public void setQ_propertyName1(String qPropertyName1) { q_propertyName1 = qPropertyName1; }
	public String getKeepunit() { return keepunit; }
	public void setKeepunit(String keepunit) { this.keepunit = keepunit; }
	public String getKeeper() { return keeper; }
	public void setKeeper(String keeper) { this.keeper = keeper; }
	public String getUseunit() { return useunit; }
	public void setUseunit(String useunit) { this.useunit = useunit; }
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }
	public String getQ_place1() { return q_place1; }
	public void setQ_place1(String qPlace1) { q_place1 = qPlace1; }
	public String getQ_place() { return q_place; }
	public void setQ_place(String qPlace) { q_place = qPlace; }
	public String getEngineering() { return engineering; }
	public void setEngineering(String engineering) { this.engineering = engineering; }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }

	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }

	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_print(){ return checkGet(q_print); }
	public void setQ_print(String s){ q_print=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	public String getQ_serialNoS() {	return checkGet(q_serialNoS);}
	public void setQ_serialNoS(String q_serialNoS) {	this.q_serialNoS = checkSet(q_serialNoS);}
	public String getQ_serialNoE() {	return checkGet(q_serialNoE);}
	public void setQ_serialNoE(String q_serialNoE) {	this.q_serialNoE = checkSet(q_serialNoE);}

	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 

	String enterOrg;
	String ownership;
	String propertyNo;
	String serialNo;
	String differenceKind;
	String equipmentName;
	String userName;  

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getDifferenceKind(){ return checkGet(differenceKind); }
	public void setDifferenceKind(String s){ differenceKind=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
	public String getUserName() { return checkGet(userName); }
	public void setUserName(String s) { userName = checkSet(s); } 

	public DefaultTableModel getResultModel() throws Exception{
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		Map<String,String> placeMap = TCGHCommon.getSysca_PlaceCode(this.getQ_enterOrg());			//存置地點名稱
		Database db = new Database();
		try{
			String[] columns = new String[] {"KEEPUNIT","ENTERORGNAME","DIFFERENCEKIND","PROPERTYNO","PROPERTYNAME",
					                         "PROPERTYNAME1","SPECIFICATION","PROPERTYUNIT","SOURCEDATE","LIMITYEAR",
					                         "PLACE","BOOKVALUE","EQUIPMENTNAME","KEEPER","BOOKAMOUNT",
					                         "PROPERTYTYPE","USER", "KEEPERNO"};
			String execSQL = "";
			if ("1".equals(getQ_propertyType())) {
				execSQL = getLandSQL() + " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
			} else if ("2".equals(getQ_propertyType())) {
				execSQL = getBuildingSQL() + " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
			} else if ("3".equals(getQ_propertyType())) {
				execSQL = getAttachmentSQL() + " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
			} else if ("4".equals(getQ_propertyType())) {
				execSQL = getMovableSQL() + " order by a.enterorg,a.differencekind,keeper,propertyno,b.serialno ";
			} else if ("5".equals(getQ_propertyType())) {
				execSQL = getAddProof1SQL() + " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
			} else if ("6".equals(getQ_propertyType())) {
				execSQL = getAddProof2SQL();
			} else {
				execSQL += "select * from ( " + getLandSQL() + " union all ";
				execSQL += getBuildingSQL() + " union all ";
				execSQL += getAttachmentSQL() + " union all ";
				execSQL += getMovableSQL() + " union all ";
				execSQL += getAddProof1SQL() + " union all ";
				execSQL += getAddProof2SQL() + " ) aa order by aa.checkenterorg,aa.checkdifferencekind,aa.keeperNo,aa.propertyno,aa.checkserialno";
			} 

			String tansToDouble;
			//String DateTrn;
			String limitYeartemp;
			//double Doubletemp;
			
	    	//使用者操作記錄
			Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTRP010R", this.getObjPath().replaceAll("&gt;", ">"));
			
			ResultSet rs = db.querySQL(execSQL,false,false);
			Vector rowData = new Vector();
			int i = 0;
			while(rs.next()){
				i++;
				setEnterOrg(rs.getString("checkEnterOrg"));
				setOwnership(rs.getString("checkOwnership"));
				setPropertyNo(rs.getString("checkPropertyNo"));
				setSerialNo(rs.getString("checkSerialNo"));
				setDifferenceKind(rs.getString("checkdifferencekind"));
				Object[] data = new Object[columns.length];
				data[0] = Common.get(rs.getString("keepUnit"));
				data[1] = Common.get(rs.getString("enterOrgName"));
				data[2] = Common.get(rs.getString("differencekindname"));
				
				data[3] = Common.get(rs.getString("propertyNo"));
//				data[3] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[3]), "原財產分號", Common.get(rs.getString("oldserialno")));
				
				data[4] = Common.get(rs.getString("propertyName"));
				data[5] = Common.get(rs.getString("propertyName1"));
				if(Common.get(rs.getString("specification")).indexOf("/") != -1){
					String[] spec = Common.get(rs.getString("specification")).split("/");
					if(spec.length == 0){
						data[6]= "";
					}else if(spec.length == 1){
						//            		data[6] = spec[0]+"\n"+"/"; //20150121測試廠牌形式不需分行
						data[6] = spec[0];
					}else{
						if ("".equals(spec[1])) {
							data[6] = spec[0];
						} else {
							data[6] = spec[0]+"/"+spec[1];
						}
						//            		data[6] = spec[0]+"\n"+"/"+"\n"+spec[1]; //20150121測試廠牌形式不需分行

					}
				}else{
					if(data[3].toString().startsWith("8")){
						data[6] = Common.get(rs.getString("specification"));
					}else{
						data[6]="";
					}					
				}
				//土地建物標示
				UNTCH_Tools ut = new UNTCH_Tools();
				String signNoStr = rs.getString("signNo");
				String typeStr;
				String signNoName = "";
		    	if(!"".equals(checkGet(signNoStr)) && signNoStr.length() >= 7 ){
		    		signNoName = ut._getSignNoName(signNoStr.substring(0,7));

					typeStr = rs.getString("type");
		    		if("LA".equals(typeStr) && signNoStr.length() >= 15){
		    			signNoName += signNoStr.substring(7,11) + "-" + signNoStr.substring(11) + "地號";
		    			
		    		}else if("BU".equals(typeStr) && signNoStr.length() >= 15){		
		    			signNoName += signNoStr.substring(7,12) + "-" + signNoStr.substring(12) + "建號";
		    		}
		    	}
		    	data[6] = data[6]+signNoName ;
		    	//土地建物標示
				data[7] = checkGet(rs.getString("otherpropertyUnit"));
				String sourceDate = Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"), "1");
				String buyDate = Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"), "1");
				if (sourceDate.length() == 7) {
					if (buyDate.length() == 7) {
						data[8] = Common.MaskDate(sourceDate) + "\n" + Common.MaskDate(buyDate);
					} else {
						data[8] = Common.MaskDate(sourceDate);
					}
				} else {
					if (buyDate.length() == 7) {
						data[8] = Common.MaskDate(buyDate);
					} else {
						data[8] = "";
					}
				}
				String otherlimityear = checkGet(rs.getString("otherlimityear"));
				data[9] = ("".equals(otherlimityear))? "0年": (Integer.parseInt(otherlimityear)/12) + "年\n" +(Integer.parseInt(otherlimityear)%12)+"月";
				data[10] = Common.get(placeMap.get(rs.getString("place1")))+"\n"+checkGet(rs.getString("place"));
				tansToDouble= rs.getString("bookValue");
				if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
				else data[11]=null;
				getTotalEquipmentName();
				data[12] = getEquipmentName();
				data[13] = rs.getString("keeper");
				data[14] = rs.getString("bookAmount");
				data[15] = rs.getString("propertytype");
				data[16] = rs.getString("username");
				data[17] = Common.get(rs.getString("keeperNo"));
				rowData.addElement(data);
			}
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

	//以下做設備的連結
	protected void getTotalEquipmentName(){
		StringBuffer equipmentName = new StringBuffer("");
		Database db = new Database();	
		ResultSet rs;	
		String sql="select a.equipmentname " +
				" from UNTMP_ATTACHMENT2 a" +
				" where 1=1 and a.datastate='1'" + 
				" and a.enterorg=" + util.Common.sqlChar(getEnterOrg())+
				" and a.ownership=" + util.Common.sqlChar(getOwnership())+
				" and a.propertyno=" + util.Common.sqlChar(getPropertyNo())+
				" and a.serialno=" + util.Common.sqlChar(getSerialNo())+
				" and a.differencekind=" + util.Common.sqlChar(getDifferenceKind())+
				"";	
		try{
			rs = db.querySQL(sql);
			while (rs.next()){
				equipmentName.append("、"+rs.getString("equipmentname"));
			}
			if(equipmentName.toString().equals("")){
				equipmentName.append("、 ");
			}
			setEquipmentName(equipmentName.toString().substring(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
	}

	//土地
	private String getLandSQL(){
		String execSQL=" select " +
				" 'LA' as type, "+
				" a.enterorg as checkenterorg, " +
				" a.ownership as checkownership, " +
				" a.propertyno as checkpropertyno, " +
				" a.serialno as checkserialno, " +
				" a.differencekind as checkdifferencekind, " +
				" (select codename from SYSCA_CODE where codekindid='DFK' and codeid=a.differencekind) as differencekindname, " +
				" (select c.organaname from SYSCA_ORGAN c where a.enterorg=c.organid) enterorgname, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.userno=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.useunit=g.unitno) as username, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.keeper=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.keepunit=g.unitno) as keeper, " +
				" a.propertyno +'-'+ a.serialno as propertyno, " +
				" d.propertyname, " +
				" a.propertyname1, " +
				" '' as specification, " +
				//	" '平方公尺' propertyunit, " +
				" '平方公尺' as otherpropertyunit, " +
				" cast(a.holdarea as varchar) as bookamount,  " +
				" a.sourcedate, " +
				" a.buydate, " +
				" d.limityear, " +
				" null as otherlimityear, " +
				" a.bookvalue, " +
				" a.place1 ,"+
				" a.place , " +
				" a.keeper as keeperNo, " + 
				" (select e.unitname from UNTMP_KEEPUNIT e where  a.enterorg=e.enterorg and a.useunit=e.unitno ) keepunit, " +
				" '土地' as propertytype, a.oldserialno as oldserialno, " +
				" a.signno "+
				" from " +
				" UNTLA_LAND a " +
				" left outer join SYSPK_PROPERTYCODE d on d.enterorg in ('000000000A',a.enterorg) and d.propertytype='1' and a.propertyno=d.propertyno  " +
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
		if (!"".equals(Common.get(getQ_ownership())))	//權屬條件
			execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_propertyKind())))	//財產區分別
			execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_propertyKind());		
		if(!"".equals(getQ_propertyNoS())){
			execSQL += " and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
		}
		if(!"".equals(getQ_propertyNoE())){
			execSQL += " and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		}
		if (!"".equals(getQ_serialNoS())){
		    execSQL+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());	
		}
		if (!"".equals(getQ_serialNoE())){
		    execSQL+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());
		}
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
		if (!"".equals(Common.get(getQ_propertyName1())))	//財產別名
			execSQL += " and isnull(a.propertyname1,'') like '%" + getQ_propertyName1().trim() + "%' ";

		if (!"".equals(Common.get(getKeepunit())))	//保管單位
			execSQL += " and a.keepunit = " + util.Common.sqlChar(getKeepunit());
		if (!"".equals(Common.get(getKeeper())))	//保管人
			execSQL += " and a.keeper = " + util.Common.sqlChar(getKeeper());
		if (!"".equals(Common.get(getUseunit())))	//使用單位
			execSQL += " and a.useunit = " + util.Common.sqlChar(getUseunit());
		if (!"".equals(Common.get(getUser())))	//使用人
			execSQL += " and a.userno = " + util.Common.sqlChar(getUser());
		if (!"".equals(Common.get(getQ_place1())))	//存置地點
			execSQL += " and a.place1 = " + util.Common.sqlChar(getQ_place1());
		if (!"".equals(Common.get(getQ_place())))	//存置地點說明
			execSQL += " and a.place like '%" + getQ_place().trim() + "%' ";
		if (!"".equals(Common.get(getEngineering())))	//工程編號
			execSQL += " and a.engineeringno = " + util.Common.sqlChar(getEngineering());

		execSQL += " and a.datastate = 1 and a.verify = 'Y'";
		//execSQL += " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
		return execSQL;
	}

	//建物
	private String getBuildingSQL(){
		String execSQL=" select " +
				" 'BU' as type, "+
				" a.enterorg as checkenterorg, " +
				" a.ownership as checkownership, " +
				" a.propertyno as checkpropertyno, " +
				" a.serialno as checkserialno, " +
				" a.differencekind as checkdifferencekind, " +
				" (select codename from SYSCA_CODE where codekindid='DFK' and codeid=a.differencekind) as differencekindname, " +
				" (select c.organaname from SYSCA_ORGAN c where a.enterorg=c.organid) enterorgname, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.userno=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.useunit=g.unitno) as username, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.keeper=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.keepunit=g.unitno) as keeper, " +
				" a.propertyno +'-'+ a.serialno as propertyno, " +
				" d.propertyname, " +
				" a.propertyname1, " +
				" '' as specification, " +
				//	" '平方公尺' propertyunit, " +
				" '平方公尺' as otherpropertyunit, " +
				" cast(a.holdarea as varchar) as bookamount,  " +
				" a.sourcedate, " +
				" a.buydate, " +
				" d.limityear, " +
				" a.otherlimityear, " +
				" a.bookvalue, " +
				" a.place1 ,"+
				" a.place , " +
				" a.keeper as keeperNo, " + 
				" (select e.unitname from UNTMP_KEEPUNIT e where  a.enterorg=e.enterorg and a.useunit=e.unitno ) keepunit, " +
				" '建物' as propertytype , a.oldserialno as oldserialno, " +
				" a.signno "+
				" from " +
				" UNTBU_BUILDING a " +
				" left outer join SYSPK_PROPERTYCODE d on d.enterorg in ('000000000A',a.enterorg) and d.propertytype='1' and a.propertyno=d.propertyno  " +
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
		if (!"".equals(Common.get(getQ_ownership())))	//權屬條件
			execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_propertyKind())))	//財產區分別
			execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_propertyKind());		
		if(!"".equals(getQ_propertyNoS())){
			execSQL += " and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
		}
		if(!"".equals(getQ_propertyNoE())){
			execSQL += " and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		}
		if (!"".equals(getQ_serialNoS())){
		    execSQL+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());	
		}
		if (!"".equals(getQ_serialNoE())){
		    execSQL+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());
		}
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
		if (!"".equals(Common.get(getQ_propertyName1())))	//財產別名
			execSQL += " and isnull(a.propertyname1,'') like '%" + getQ_propertyName1().trim() + "%' ";

		if (!"".equals(Common.get(getKeepunit())))	//保管單位
			execSQL += " and a.keepunit = " + util.Common.sqlChar(getKeepunit());
		if (!"".equals(Common.get(getKeeper())))	//保管人
			execSQL += " and a.keeper = " + util.Common.sqlChar(getKeeper());
		if (!"".equals(Common.get(getUseunit())))	//使用單位
			execSQL += " and a.useunit = " + util.Common.sqlChar(getUseunit());
		if (!"".equals(Common.get(getUser())))	//使用人
			execSQL += " and a.userno = " + util.Common.sqlChar(getUser());
		if (!"".equals(Common.get(getQ_place1())))	//存置地點
			execSQL += " and a.place1 = " + util.Common.sqlChar(getQ_place1());
		if (!"".equals(Common.get(getQ_place())))	//存置地點說明
			execSQL += " and a.place like '%" + getQ_place().trim() + "%' ";
		if (!"".equals(Common.get(getEngineering())))	//工程編號
			execSQL += " and a.engineeringno = " + util.Common.sqlChar(getEngineering());

		execSQL += " and a.datastate = 1 and a.verify = 'Y'";
		//execSQL += " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
		return execSQL;
	}

	//土地改良
	private String getAttachmentSQL(){
		String execSQL=" select " +
				" 'RF' as type, "+
				" a.enterorg as checkenterorg, " +
				" a.ownership as checkownership, " +
				" a.propertyno as checkpropertyno, " +
				" a.serialno as checkserialno, " +
				" a.differencekind as checkdifferencekind, " +
				" (select codename from SYSCA_CODE where codekindid='DFK' and codeid=a.differencekind) as differencekindname, " +
				" (select c.organaname from SYSCA_ORGAN c where a.enterorg=c.organid) enterorgname, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.userno=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.useunit=g.unitno) as username, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.keeper=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.keepunit=g.unitno) as keeper, " +
				" a.propertyno +'-'+ a.serialno as propertyno, " +
				" d.propertyname, " +
				" a.propertyname1, " +
				" '' as specification, " +
				" a.otherpropertyunit, " +
				//	" isnull(d.propertyunit,'') propertyunit, " +
				" cast(a.measure as varchar) as bookamount,  " +
				" a.sourcedate, " +
				" a.buydate, " +
				" d.limityear, " +
				" a.otherlimityear, " +
				" a.bookvalue, " +
				" a.place1 ,"+
				" a.place , " +
				" a.keeper as keeperNo, " + 
				" (select e.unitname from UNTMP_KEEPUNIT e where  a.enterorg=e.enterorg and a.useunit=e.unitno ) keepunit, " +
				" '土地改良' as propertytype , a.oldserialno as oldserialno, " +
				" null as signno "+
				" from " +
				" UNTRF_ATTACHMENT a " +
				" left outer join SYSPK_PROPERTYCODE d on d.enterorg in ('000000000A',a.enterorg) and d.propertytype='1' and a.propertyno=d.propertyno  " +
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
		if (!"".equals(Common.get(getQ_ownership())))	//權屬條件
			execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_propertyKind())))	//財產區分別
			execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_propertyKind());		
		if(!"".equals(getQ_propertyNoS())){
			execSQL += " and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
		}
		if(!"".equals(getQ_propertyNoE())){
			execSQL += " and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		}
		if (!"".equals(getQ_serialNoS())){
		    execSQL+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());	
		}
		if (!"".equals(getQ_serialNoE())){
		    execSQL+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());
		}
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
		if (!"".equals(Common.get(getQ_propertyName1())))	//財產別名
			execSQL += " and isnull(a.propertyname1,'') like '%" + getQ_propertyName1().trim() + "%' ";

		if (!"".equals(Common.get(getKeepunit())))	//保管單位
			execSQL += " and a.keepunit = " + util.Common.sqlChar(getKeepunit());
		if (!"".equals(Common.get(getKeeper())))	//保管人
			execSQL += " and a.keeper = " + util.Common.sqlChar(getKeeper());
		if (!"".equals(Common.get(getUseunit())))	//使用單位
			execSQL += " and a.useunit = " + util.Common.sqlChar(getUseunit());
		if (!"".equals(Common.get(getUser())))	//使用人
			execSQL += " and a.userno = " + util.Common.sqlChar(getUser());
		if (!"".equals(Common.get(getQ_place1())))	//存置地點
			execSQL += " and a.place1 = " + util.Common.sqlChar(getQ_place1());
		if (!"".equals(Common.get(getQ_place())))	//存置地點說明
			execSQL += " and a.place like '%" + getQ_place().trim() + "%' ";
		if (!"".equals(Common.get(getEngineering())))	//工程編號
			execSQL += " and a.engineeringno = " + util.Common.sqlChar(getEngineering());

		execSQL += " and a.datastate = 1 and a.verify = 'Y'";
		//execSQL += " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
		return execSQL;
	}

	//動產
	private String getMovableSQL(){
		String execSQL=" select "+
				" 'MP' as type, "+
				" b.enterorg as checkenterorg, " + 
				" b.ownership as checkownership, " + 
				" b.propertyno as checkpropertyno, " + 
				" b.serialno as checkserialno, " + 
				" b.differencekind as checkdifferencekind, " + 
				" (select codename from SYSCA_CODE where codekindid='DFK' and codeid=b.differencekind) as differencekindname, " +
				" (select c.organaname from SYSCA_ORGAN c where a.enterorg=c.organid) enterorgname, " + 
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and b.userno=h.keeperno " +
				" and b.enterorg=g.enterorg " +
				" and b.useunit=g.unitno) as username, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g, UNTMP_KEEPER h where b.enterorg=h.enterorg and b.keeper=h.keeperno and b.enterorg=g.enterorg and b.keepunit=g.unitno) as keeper, " +
				" b.propertyno +'-'+ b.serialno as propertyno, " +
				" d.propertyname, " +
				" isnull (b.propertyname1, null) as propertyname1, " +
				" isnull(a.specification,'')+'/'+ isnull(a.nameplate,'') as specification, " +
				//		" isnull(d.propertyunit,'') propertyunit, " +
				" a.otherpropertyunit, " +
				" b.bookamount, " +
				" a.sourcedate, " +
				" a.buydate, " +
				" CAST( (b.otherlimityear / 12 ) AS integer)  as limityear, " +
				" b.otherlimityear, " +
				" b.bookvalue, " +
				" b.place1 ,"+
				" b.place , " +
				" b.keeper as keeperNo, " + 
				" (select e.unitname from UNTMP_KEEPUNIT e where b.enterorg=e.enterorg and b.useunit=e.unitno )keepunit, " +
				" case SUBSTRING(b.propertyno,1,1) when '3' then '機械設備' when '4' then '交通運輸設備' when '5' then '雜項設備' end as propertytype, " +
				" b.oldserialno as oldserialno, " + //TODO
				" null as signno "+
				" from " +
				" UNTMP_MOVABLE a " +
				" inner join UNTMP_MOVABLEDETAIL b on a.enterorg=b.enterorg and  a.ownership=b.ownership and  b.propertyno=a.propertyno and a.lotno=b.lotno  and a.differencekind = b.differencekind " +
				" left outer join SYSPK_PROPERTYCODE d on d.enterorg in ('000000000A',a.enterorg) and d.propertytype='1' and b.propertyno=d.propertyno " +
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
		if (!"".equals(Common.get(getQ_ownership())))	//權屬條件
			execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(Common.get(getQ_propertyKind())))	//財產區分別
			execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_propertyKind());		
		if(!"".equals(getQ_propertyNoS())){
			execSQL += " and b.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
		}
		if(!"".equals(getQ_propertyNoE())){
			execSQL += " and b.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		}
		if (!"".equals(getQ_serialNoS())){
		    execSQL+=" and b.serialno >= " + Common.sqlChar(getQ_serialNoS());	
		}
		if (!"".equals(getQ_serialNoE())){
		    execSQL+=" and b.serialno <= " + Common.sqlChar(getQ_serialNoE());
		}
		if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
			execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
		if (!"".equals(Common.get(getQ_propertyName1())))	//財產別名
			execSQL += " and isnull(a.propertyname1,'') like '%" + getQ_propertyName1().trim() + "%' ";

		if (!"".equals(Common.get(getKeepunit())))	//保管單位
			execSQL += " and b.keepunit = " + util.Common.sqlChar(getKeepunit());
		if (!"".equals(Common.get(getKeeper())))	//保管人
			execSQL += " and b.keeper = " + util.Common.sqlChar(getKeeper());
		if (!"".equals(Common.get(getUseunit())))	//使用單位
			execSQL += " and b.useunit = " + util.Common.sqlChar(getUseunit());
		if (!"".equals(Common.get(getUser())))	//使用人
			execSQL += " and b.userno = " + util.Common.sqlChar(getUser());
		if (!"".equals(Common.get(getQ_place1())))	//存置地點
			execSQL += " and b.place1 = " + util.Common.sqlChar(getQ_place1());
		if (!"".equals(Common.get(getQ_place())))	//存置地點說明
			execSQL += " and b.place like '%" + getQ_place().trim() + "%' ";
		if (!"".equals(Common.get(getEngineering())))	//工程編號
			execSQL += " and a.engineeringno = " + util.Common.sqlChar(getEngineering());

		execSQL += " and b.datastate = 1 and a.verify = 'Y'";
		//execSQL += " order by a.enterorg,a.differencekind,keeper,propertyno,b.serialno ";
		return execSQL;
	}

	//有價證券增加單
	private String getAddProof1SQL(){
		String execSQL=" select " +
				" 'VP' as type, "+
				" a.enterorg as checkenterorg, " +
				" a.ownership as checkownership, " +
				" a.propertyno as checkpropertyno, " +
				" a.serialno as checkserialno, " +
				" a.differencekind as checkdifferencekind, " +
				" (select codename from SYSCA_CODE where codekindid='DFK' and codeid=a.differencekind) as differencekindname, " +
				" (select c.organaname from SYSCA_ORGAN c where a.enterorg=c.organid) enterorgname, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.userno=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.useunit=g.unitno) as username, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.keeper=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.keepunit=g.unitno) as keeper, " +
				" a.propertyno +'-'+ a.serialno as propertyno, " +
				" d.propertyname, " +
				" a.propertyname1, " +
				" '' as specification, " +
				//	" isnull(d.propertyunit,'') propertyunit, " +
				" a.otherpropertyunit, " +
				" a.bookamount,  " +
				" a.sourcedate, " +
				" a.buydate, " +
				" d.limityear, " +
				" null as otherlimityear, " +
				" a.bookvalue, " +
				" a.place1 ,"+
				" a.place , " +
				" a.keeper as keeperNo, " + 
				" (select e.unitname from UNTMP_KEEPUNIT e where  a.enterorg=e.enterorg and a.useunit=e.unitno ) keepunit, " +
				" '有價證券增加單' as propertytype, a.oldserialno as oldserialno, " +
				" null as signno "+
				" from " +
				" UNTVP_ADDPROOF a " +
				" left outer join SYSPK_PROPERTYCODE d on d.enterorg in ('000000000A',a.enterorg) and d.propertytype='1' and a.propertyno=d.propertyno  " +
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
		if (!"".equals(Common.get(getQ_ownership())))	//權屬條件
			execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		//TODO:有價證券無珍貴財產：問桂香
		//	if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
		//		execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
		if (!"".equals(Common.get(getQ_propertyKind())))	//財產區分別
			execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_propertyKind());		
		if(!"".equals(getQ_propertyNoS())){
			execSQL += " and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
		}
		if(!"".equals(getQ_propertyNoE())){
			execSQL += " and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		}
		if (!"".equals(getQ_serialNoS())){
		    execSQL+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());	
		}
		if (!"".equals(getQ_serialNoE())){
		    execSQL+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());
		}
		if (!"".equals(Common.get(getQ_propertyName1())))	//財產別名
			execSQL += " and isnull(a.propertyname1,'') like '%" + getQ_propertyName1().trim() + "%' ";

		if (!"".equals(Common.get(getKeepunit())))	//保管單位
			execSQL += " and a.keepunit = " + util.Common.sqlChar(getKeepunit());
		if (!"".equals(Common.get(getKeeper())))	//保管人
			execSQL += " and a.keeper = " + util.Common.sqlChar(getKeeper());
		if (!"".equals(Common.get(getUseunit())))	//使用單位
			execSQL += " and a.useunit = " + util.Common.sqlChar(getUseunit());
		if (!"".equals(Common.get(getUser())))	//使用人
			execSQL += " and a.userno = " + util.Common.sqlChar(getUser());
		if (!"".equals(Common.get(getQ_place1())))	//存置地點
			execSQL += " and a.place1 = " + util.Common.sqlChar(getQ_place1());
		if (!"".equals(Common.get(getQ_place())))	//存置地點說明
			execSQL += " and a.place like '%" + getQ_place().trim() + "%' ";
		if (!"".equals(Common.get(getEngineering())))	//工程編號
			execSQL += " and a.engineeringno = " + util.Common.sqlChar(getEngineering());

		execSQL += " and a.datastate = 1 and a.verify = 'Y'";
		//execSQL += " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
		return execSQL;
	}

	//權利增加單
	private String getAddProof2SQL(){
		String execSQL=" select " +
				" 'RT' as type, "+
				" a.enterorg as checkenterorg, " +
				" a.ownership as checkownership, " +
				" a.propertyno as checkpropertyno, " +
				" a.serialno as checkserialno, " +
				" a.differencekind as checkdifferencekind, " +
				" (select codename from SYSCA_CODE where codekindid='DFK' and codeid=a.differencekind) as differencekindname, " +
				" (select c.organaname from SYSCA_ORGAN c where a.enterorg=c.organid) enterorgname, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.userno=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.useunit=g.unitno) as username, " +
				" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg " +
				" and a.keeper=h.keeperno " +
				" and a.enterorg=g.enterorg " +
				" and a.keepunit=g.unitno) as keeper, " +
				" a.propertyno +'-'+ a.serialno as propertyno, " +
				" d.propertyname, " +
				" a.propertyname1, " +
				" a.proofdoc1 as specification, " +
				//	" isnull(d.propertyunit,'') propertyunit, " +
				" a.otherpropertyunit, " +
				" '1' bookamount,  " +
				" a.sourcedate, " +
				" a.buydate, " +
				" d.limityear, " +
				" a.otherlimityear, " +
				" a.bookvalue, " +
				" a.place1 ,"+
				" a.place , " +
				" a.keeper as keeperNo, " + 
				" (select e.unitname from UNTMP_KEEPUNIT e where  a.enterorg=e.enterorg and a.useunit=e.unitno ) keepunit, " +
				" '權利增加單' as propertytype, a.oldserialno as oldserialno, " +
				" null as signno "+
				" from " +
				" UNTRT_ADDPROOF a " +
				" left outer join SYSPK_PROPERTYCODE d on d.enterorg in ('000000000A',a.enterorg) and d.propertytype='1' and a.propertyno=d.propertyno  " +
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
		if (!"".equals(Common.get(getQ_ownership())))	//權屬條件
			execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		//TODO:權利增加單無珍貴財產：問桂香
		//	if (!"".equals(Common.get(getQ_valuable())))	//珍貴財產
		//		execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
		if (!"".equals(Common.get(getQ_propertyKind())))	//財產區分別
			execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_propertyKind());		
		if(!"".equals(getQ_propertyNoS())){
			execSQL += " and a.propertyno >= " + Common.sqlChar(getQ_propertyNoS());
		}
		if(!"".equals(getQ_propertyNoE())){
			execSQL += " and a.propertyno <= " + Common.sqlChar(getQ_propertyNoE());
		}
		if (!"".equals(getQ_serialNoS())){
		    execSQL+=" and a.serialno >= " + Common.sqlChar(getQ_serialNoS());	
		}
		if (!"".equals(getQ_serialNoE())){
		    execSQL+=" and a.serialno <= " + Common.sqlChar(getQ_serialNoE());
		}
		if (!"".equals(Common.get(getQ_propertyName1())))	//財產別名
			execSQL += " and isnull(a.propertyname1,'') like '%" + getQ_propertyName1().trim() + "%' ";

		if (!"".equals(Common.get(getKeepunit())))	//保管單位
			execSQL += " and a.keepunit = " + util.Common.sqlChar(getKeepunit());
		if (!"".equals(Common.get(getKeeper())))	//保管人
			execSQL += " and a.keeper = " + util.Common.sqlChar(getKeeper());
		if (!"".equals(Common.get(getUseunit())))	//使用單位
			execSQL += " and a.useunit = " + util.Common.sqlChar(getUseunit());
		if (!"".equals(Common.get(getUser())))	//使用人
			execSQL += " and a.userno = " + util.Common.sqlChar(getUser());
		if (!"".equals(Common.get(getQ_place1())))	//存置地點
			execSQL += " and a.place1 = " + util.Common.sqlChar(getQ_place1());
		if (!"".equals(Common.get(getQ_place())))	//存置地點說明
			execSQL += " and a.place like '%" + getQ_place().trim() + "%' ";
		if (!"".equals(Common.get(getEngineering())))	//工程編號
			execSQL += " and a.engineeringno = " + util.Common.sqlChar(getEngineering());

		execSQL += " and a.datastate = 1 and a.verify = 'Y'";
		//execSQL += " order by a.enterorg,a.differencekind,a.keeper,a.propertyno,a.serialno ";
		return execSQL;
	}

}
