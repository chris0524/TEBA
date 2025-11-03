
/*
*<br>程式目的：財產保管單位別清冊
*<br>程式代號：untrp009r
*<br>撰寫日期：94/12/19
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.SuperBean;
import util.TCGHCommon;
import util.report.ReportUtil;

public class UNTRP009R extends SuperBean{

	String enterOrg;
	String ownership;
	String dataState;
	String verify;
	String keepUnit;
	String propertyKind;
	String fundType;
	String valuable;

	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_propertyType;
	String q_dataState;
	String q_verify;
	String q_keepBureau;
	String q_keepUnit;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_serialNoS;
	String q_serialNoE;
    String originalKeeper;
    String originalKeepUnit;
	String originalUseUnit;
	String originalUser;
	String originalPlace1;
	String originalPlace1Name;
	String originalPlace;
	String engineeringNo;
	String engineeringNoName;
    String q_differenceKind;
    String q_propertyName1;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getDataState(){ return checkGet(dataState); }
	public void setDataState(String s){ dataState=checkSet(s); }
	public String getVerify(){ return checkGet(verify); }
	public void setVerify(String s){ verify=checkSet(s); }
	public String getKeepUnit(){ return checkGet(keepUnit); }
	public void setKeepUnit(String s){ keepUnit=checkSet(s); }
	public String getPropertyKind(){ return checkGet(propertyKind); }
	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
	public String getFundType(){ return checkGet(fundType); }
	public void setFundType(String s){ fundType=checkSet(s); }
	public String getValuable(){ return checkGet(valuable); }
	public void setValuable(String s){ valuable=checkSet(s); }

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
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
	public String getQ_propertyType() {return checkGet(q_propertyType);}
	public void setQ_propertyType(String qPropertyType) {q_propertyType = checkSet(qPropertyType);}
	public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String qPropertyName1) {q_propertyName1 = checkSet(qPropertyName1);}
    public String getQ_differenceKind() {return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String qDifferenceKind) {q_differenceKind = checkSet(qDifferenceKind);}
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

    
	String propertyNo;
	String serialNo;
	String equipmentName;
	String q_keeper;
	
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
	
	public String getQ_keeper() {return checkGet(q_keeper);}
	public void setQ_keeper(String qKeeper) {q_keeper = checkSet(qKeeper);}
	
	public String getOriginalKeeper() {return checkGet(originalKeeper);}
	public void setOriginalKeeper(String originalKeeper) {this.originalKeeper = checkSet(originalKeeper);}
	public String getOriginalUseUnit() {return checkGet(originalUseUnit);}
	public void setOriginalUseUnit(String originalUseUnit) {this.originalUseUnit = checkSet(originalUseUnit);}
	public String getOriginalKeepUnit() {return checkGet(originalKeepUnit);}
	public void setOriginalKeepUnit(String originalKeepUnit) {this.originalKeepUnit = checkSet(originalKeepUnit);}
	public String getOriginalUser() {return checkGet(originalUser);}
	public void setOriginalUser(String originalUser) {this.originalUser = checkSet(originalUser);}
	public String getOriginalPlace1() {return checkGet(originalPlace1);}
	public void setOriginalPlace1(String originalPlace1) {this.originalPlace1 = checkSet(originalPlace1);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	public String getOriginalPlace() {return checkGet(originalPlace);}
	public void setOriginalPlace(String originalPlace) {this.originalPlace = checkSet(originalPlace);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	
public DefaultTableModel getResultModel() throws Exception{
	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"KEEPUNIT","ENTERORGNAME","OWNERSHIP","PROPERTYNO","PROPERTYNAME","SPECIFICATION","PROPERTYUNIT","SOURCEDATE","LIMITYEAR","PLACE","BOOKVALUE","EQUIPMENTNAME","KEEPERNAME","DIFFERENCEKIND"};
    	
    	String execSQL = "";

    	execSQL += this.getLASQL();
    	execSQL += " union";
    	execSQL += this.getBUSQL();
    	execSQL += " union"; 
    	execSQL += this.getRFSQL();
    	execSQL += " union"; 
    	execSQL += this.getMPSQL();
    	execSQL += " union";    	
    	execSQL += this.getRTSQL();
    	execSQL += " union"; 	
    	execSQL += this.getVPSQL(); 
    	
    	execSQL += " order by checkEnterOrg, differencekind, checkKeepUnit, checkPropertyNo, checkSerialNo";
    	String tansToDouble;
    	String limitYeartemp;

    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTRP009R", this.getObjPath().replaceAll("&gt;", ">"));
		
    	ResultSet rs = db.querySQL_NoChange(execSQL);
    	
    	UNTCH_Tools ut = new UNTCH_Tools();
        Vector rowData = new Vector();
        while(rs.next()){
        	setEnterOrg(rs.getString("checkEnterOrg"));
        	setOwnership(rs.getString("checkOwnership"));
        	setPropertyNo(rs.getString("checkPropertyNo"));
        	setSerialNo(rs.getString("checkSerialNo"));
        	Object[] data = new Object[columns.length];
        	data[0] = Common.get(rs.getString("keepUnit"));
            data[1] = Common.get(rs.getString("enterOrgName"));
            data[2] = Common.get(rs.getString("ownershipName"));
            
            data[3] = Common.get(rs.getString("propertyNo"));
//            data[3] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[3]), "原財產分號", Common.get(rs.getString("oldserialno")));
            
            data[4] = Common.get(rs.getString("propertyName"))+"\n"+Common.get(rs.getString("propertyName1"));

//            data[5] = Common.get(rs.getString("specification"))+"\n"+"/"+"\n"+Common.get(rs.getString("nameplate"));//20150121測試廠牌形式不需分行
            data[5] = Common.get(rs.getString("specification"))+"/"+Common.get(rs.getString("nameplate"));
//          data[6] = Common.get(rs.getString("bookamount")) + "\n" + Common.get(rs.getString("propertyUnit"));
            data[6] = Common.get(rs.getString("bookamount")) + "\n" + Common.get(rs.getString("otherpropertyunit"));
            data[7] = Common.MaskDate(ut._transToROC_Year(rs.getString("sourceDate")));
//        	data[8] = Common.get(rs.getString("limitYear"));
//        	limitYeartemp=Common.get(rs.getString("otherLimitYear"));
//        	if(data[8]==null)data[8]=limitYeartemp;
            
            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
			int year = otherLimitMonth /12;
			int month = otherLimitMonth % 12;
			data[8] = year +"年"+month+"月";
			data[9] = Common.get(rs.getString("place1Name"))+ "\n" + Common.get(rs.getString("place"));
        	tansToDouble= Common.get(rs.getString("bookValue"));
            if(tansToDouble!=null)data[10]= new Double(Double.parseDouble(tansToDouble));
            else data[10]=null;
            getTotalEquipmentName();
            data[11] = getEquipmentName();
            data[12] = Common.get(rs.getString("keeperName1"))+"\n"+Common.get(rs.getString("keeperName2"));
            data[13] = TCGHCommon.getSYSCODEName(Common.get(rs.getString("differencekind")), "DFK");
            
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

private String getLASQL(){
	String execSQL = "";
	
	execSQL +=" select b.enterorg as checkEnterOrg, b.ownership as checkOwnership, b.propertyno as checkPropertyNo, b.serialno as checkSerialNo, b.keepunit as checkKeepUnit," +
			" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and b.ownership=z.codeid) as ownershipName," +
			" e.unitname as keepUnit, (select c.organaname from SYSCA_ORGAN c where b.enterorg=c.organid) as enterorgname," +
			" substring(b.propertyno,1,7)+'-'+substring(b.propertyno,8,4)+'-'+b.serialno as propertyNo," +
			" d.propertyname, b.propertyname1, null as specification, null as nameplate, null as otherpropertyunit, b.sourcedate, null as otherlimityear," +
			" (select placename from SYSCA_PLACE z where z.enterorg = b.enterorg and z.placeno = b.place1) as place1Name, b.place, b.bookvalue, null as bookamount," +
			" (select g.keepername from UNTMP_KEEPER g where b.enterorg=g.enterorg and b.userno=g.keeperno ) as keeperName1," +
			" (select h.keepername from UNTMP_KEEPER h where b.enterorg=h.enterorg and b.keeper=h.keeperno ) as keeperName2,  b.oldserialno, b.differencekind " +
			" from UNTLA_LAND b,SYSPK_PROPERTYCODE d ,UNTMP_KEEPUNIT e" +
			" where b.enterorg=e.enterorg and b.keepunit=e.unitno" +
			" and b.propertyno=d.propertyno and d.enterorg in ('000000000A',b.enterorg) and d.propertytype='1' ";
	execSQL += this.getQueryCondition("LA");
	
	return execSQL;
}

private String getBUSQL(){
	String execSQL = "";
	
	execSQL +=" select b.enterorg as checkEnterOrg, b.ownership as checkOwnership, b.propertyno as checkPropertyNo, b.serialno as checkSerialNo, b.keepunit as checkKeepUnit," +
			" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and b.ownership=z.codeid) as ownershipName," +
			" e.unitname as keepUnit, (select c.organaname from SYSCA_ORGAN c where b.enterorg=c.organid) as enterorgname," +
			" substring(b.propertyno,1,7)+'-'+substring(b.propertyno,8,4)+'-'+b.serialno as propertyNo," +
			" d.propertyname, b.propertyname1, null as specification, null as nameplate, b.otherpropertyunit, b.sourcedate, b.otherlimityear," +
			" (select placename from SYSCA_PLACE z where z.enterorg = b.enterorg and z.placeno = b.place1) as place1Name, b.place, b.bookvalue, null as bookamount," +
			" (select g.keepername from UNTMP_KEEPER g where b.enterorg=g.enterorg and b.userno=g.keeperno ) as keeperName1," +
			" (select h.keepername from UNTMP_KEEPER h where b.enterorg=h.enterorg and b.keeper=h.keeperno ) as keeperName2,  b.oldserialno , b.differencekind " +
			" from UNTBU_BUILDING b,SYSPK_PROPERTYCODE d ,UNTMP_KEEPUNIT e" +
			" where b.enterorg=e.enterorg and b.keepunit=e.unitno" +
			" and b.propertyno=d.propertyno and d.enterorg in ('000000000A',b.enterorg) and d.propertytype='1' ";
	execSQL += this.getQueryCondition("BU");
	
	return execSQL;
}

private String getRFSQL(){
	String execSQL = "";
	
	execSQL +=" select b.enterorg as checkEnterOrg, b.ownership as checkOwnership, b.propertyno as checkPropertyNo, b.serialno as checkSerialNo, b.keepunit as checkKeepUnit," +
			" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and b.ownership=z.codeid) as ownershipName," +
			" e.unitname as keepUnit, (select c.organaname from SYSCA_ORGAN c where b.enterorg=c.organid) as enterorgname," +
			" substring(b.propertyno,1,7)+'-'+substring(b.propertyno,8,4)+'-'+b.serialno as propertyNo," +
			" d.propertyname, b.propertyname1, null as specification, null as nameplate, b.otherpropertyunit, b.sourcedate, b.otherlimityear," +
			" (select placename from SYSCA_PLACE z where z.enterorg = b.enterorg and z.placeno = b.place1) as place1Name, b.place, b.bookvalue, null as bookamount," +
			" (select g.keepername from UNTMP_KEEPER g where b.enterorg=g.enterorg and b.userno=g.keeperno ) as keeperName1," +
			" (select h.keepername from UNTMP_KEEPER h where b.enterorg=h.enterorg and b.keeper=h.keeperno ) as keeperName2,  b.oldserialno, b.differencekind " +
			" from UNTRF_ATTACHMENT b,SYSPK_PROPERTYCODE d ,UNTMP_KEEPUNIT e" +
			" where b.enterorg=e.enterorg and b.keepunit=e.unitno" +
			" and b.propertyno=d.propertyno and d.enterorg in ('000000000A',b.enterorg) and d.propertytype='1' ";
	execSQL += this.getQueryCondition("RF");
	
	return execSQL;
}

private String getMPSQL(){
	String execSQL = "";
	
	execSQL=" select b.enterorg as checkEnterOrg, b.ownership as checkOwnership, b.propertyno as checkPropertyNo, b.serialno as checkSerialNo, b.keepunit as checkKeepUnit," +
			" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and a.ownership=z.codeid) as ownershipName, " +
			" e.unitname as keepUnit," +
			//" c.organAName as enterOrgName," +
			" (select c.organaname from SYSCA_ORGAN c where a.enterorg=c.organid) as enterorgname, " +
			" substring(b.propertyno,1,7)+'-'+substring(b.propertyno,8,4)+'-'+b.serialno as propertyNo," +
			" d.propertyname," +
			" b.propertyname1," +
			" a.specification," +
			" a.nameplate," +
			" a.otherpropertyunit," +
//			" d.propertyunit," +
			" a.sourcedate," +
//			" d.limityear," +
			" b.otherlimityear," +
			" (select placename from SYSCA_PLACE z where z.enterorg = b.enterorg and z.placeno = b.place1) as place1Name," +
			" b.place," +
			" b.bookvalue," +
			" b.bookamount," +
			" (select g.keepername from UNTMP_KEEPER g where b.enterorg=g.enterorg and b.userno=g.keeperno ) as keeperName1," +
			" (select h.keepername from UNTMP_KEEPER h where b.enterorg=h.enterorg and b.keeper=h.keeperno ) as keeperName2, " +
			" b.oldserialno , b.differencekind " + 
			" from UNTMP_MOVABLE a,UNTMP_MOVABLEDETAIL b,SYSPK_PROPERTYCODE d ,UNTMP_KEEPUNIT e " +
			" where a.enterorg=b.enterorg and  a.ownership=b.ownership and  b.propertyno=a.propertyno and a.lotno=b.lotno and b.propertyno=d.propertyno  and a.differencekind = b.differencekind and b.enterorg=e.enterorg and b.keepunit=e.unitno" +
			" and d.enterorg in ('000000000A',a.enterorg)" +
			" and d.propertytype='1' ";
	execSQL += this.getQueryCondition("MP");
	
	return execSQL;
}

private String getRTSQL(){
	String execSQL = "";
	
	execSQL +=" select b.enterorg as checkEnterOrg, b.ownership as checkOwnership, b.propertyno as checkPropertyNo, b.serialno as checkSerialNo, b.keepunit as checkKeepUnit," +
			" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and b.ownership=z.codeid) as ownershipName," +
			" e.unitname as keepUnit, (select c.organaname from SYSCA_ORGAN c where b.enterorg=c.organid) as enterorgname," +
			" substring(b.propertyno,1,7)+'-'+substring(b.propertyno,8,4)+'-'+b.serialno as propertyNo," +
			" d.propertyname, b.propertyname1, b.proofdoc1 as specification, null as nameplate, b.otherpropertyunit, b.sourcedate, b.otherlimityear," +
			" (select placename from SYSCA_PLACE z where z.enterorg = b.enterorg and z.placeno = b.place1) as place1Name, b.place, b.bookvalue, null as bookamount," +
			" (select g.keepername from UNTMP_KEEPER g where b.enterorg=g.enterorg and b.userno=g.keeperno ) as keeperName1," +
			" (select h.keepername from UNTMP_KEEPER h where b.enterorg=h.enterorg and b.keeper=h.keeperno ) as keeperName2,  b.oldserialno , b.differencekind " +
			" from UNTRT_ADDPROOF b,SYSPK_PROPERTYCODE d ,UNTMP_KEEPUNIT e" +
			" where b.enterorg=e.enterorg and b.keepunit=e.unitno" +
			" and b.propertyno=d.propertyno and d.enterorg in ('000000000A',b.enterorg) and d.propertytype='1' ";
	execSQL += this.getQueryCondition("RT");
	
	return execSQL;
}

private String getVPSQL(){
	String execSQL = "";
	
	execSQL +=" select b.enterorg as checkEnterOrg, b.ownership as checkOwnership, b.propertyno as checkPropertyNo, b.serialno as checkSerialNo, b.keepunit as checkKeepUnit," +
			" (select z.codename from SYSCA_CODE z where 1=1 and (z.codekindid='owa' or z.codekindid='OWA') and b.ownership=z.codeid) as ownershipName," +
			" e.unitname as keepUnit, (select c.organaname from SYSCA_ORGAN c where b.enterorg=c.organid) as enterorgname," +
			" substring(b.propertyno,1,7)+'-'+substring(b.propertyno,8,4)+'-'+b.serialno as propertyNo," +
			" d.propertyname, b.propertyname1, null as specification, null as nameplate, b.otherpropertyunit, b.sourcedate, null as otherlimityear," +
			" (select placename from SYSCA_PLACE z where z.enterorg = b.enterorg and z.placeno = b.place1) as place1Name, b.place, b.bookvalue, b.bookamount," +
			" (select g.keepername from UNTMP_KEEPER g where b.enterorg=g.enterorg and b.userno=g.keeperno ) as keeperName1," +
			" (select h.keepername from UNTMP_KEEPER h where b.enterorg=h.enterorg and b.keeper=h.keeperno ) as keeperName2,  b.oldserialno , b.differencekind " +
			" from UNTVP_ADDPROOF b,SYSPK_PROPERTYCODE d ,UNTMP_KEEPUNIT e" +
			" where b.enterorg=e.enterorg and b.keepunit=e.unitno" +
			" and b.propertyno=d.propertyno and d.enterorg in ('000000000A',b.enterorg) and d.propertytype='1' ";
	execSQL += this.getQueryCondition("VP");
	
	return execSQL;
}

private String getQueryCondition(String type){
	String execSQL = "";
	if (!"".equals(getQ_enterOrg())) {
		execSQL+=" and b.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
	} else {
		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
			if ("Y".equals(getIsOrganManager())) {					
				execSQL += " and b.enterorg like '" + getOrganID().substring(0,5) + "%' ";
			} else {
				execSQL+=" and b.enterorg = " + Common.sqlChar(getOrganID()) ;
			}
		} 
	}
	if (!"".equals(Common.get(getQ_ownership()))){
		execSQL += " and b.ownership = " + util.Common.sqlChar(getQ_ownership());
	}
	if (!"".equals(getQ_differenceKind())){ 
		execSQL = execSQL + " and b.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
	}	
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
	if("1".equals(getQ_propertyType())){
		execSQL += " and b.propertyno like '1%' and b.propertyno not like '111%' ";
	}else if("2".equals(getQ_propertyType())){
		execSQL += " and b.propertyno like '2%' ";
	}else if("3".equals(getQ_propertyType())){
		execSQL += " and b.propertyno like '111%' ";
	}else if("4".equals(getQ_propertyType())){
		execSQL += " and (b.propertyno like '3%' or b.propertyno like '4%' or b.propertyno like '5%' ) ";
	}else if("5".equals(getQ_propertyType())){
		execSQL += " and b.propertyno like '8%' ";
	}else if("6".equals(getQ_propertyType())){
		execSQL += " and b.propertyno like '9%' ";
	}
	
	if (!"".equals(Common.get(getQ_dataState())))
		execSQL += " and b.datastate = " + util.Common.sqlChar(getQ_dataState());
	if (!"".equals(Common.get(getQ_verify())))
		execSQL += " and b.verify = " + util.Common.sqlChar(getQ_verify());
	
	if(!"".equals(Common.get(getOriginalKeepUnit()))){
		execSQL += " and b.keepunit = " + util.Common.sqlChar(getOriginalKeepUnit());
	}
//	else if(!"".equals(Common.get(getQ_keepBureau()))){
//	execSQL += " and b.keepunit like '" + getQ_keepBureau()+"%' ";
//}
	if(!"".equals(Common.get(getOriginalKeeper()))){
		execSQL += " and b.keeper = " + util.Common.sqlChar(getOriginalKeeper());
	}
	
	
	if(!"".equals(Common.get(getOriginalUseUnit()))){
		execSQL += " and b.useunit = " + util.Common.sqlChar(getOriginalUseUnit());
	}

	if(!"".equals(Common.get(getOriginalUser()))){
		execSQL += " and b.userno = " + util.Common.sqlChar(getOriginalUser());
	}
	
	
	if(!"".equals(Common.get(getOriginalPlace1()))){
		execSQL += " and b.place1 = " + util.Common.sqlChar(getOriginalPlace1());
	}

	if(!"".equals(Common.get(getOriginalPlace()))){
		execSQL += " and b.place like " + util.Common.sqlChar("%"+getOriginalPlace()+"%");
	}
	
	if(!"".equals(Common.get(getEngineeringNo()))){
		execSQL += " and b.engineeringno = " + util.Common.sqlChar(getEngineeringNo());
	}
	
	if (!"".equals(Common.get(getQ_propertyKind())))
		execSQL += " and b.propertykind = " + util.Common.sqlChar(getQ_propertyKind());
	
	if (!"".equals(Common.get(getQ_propertyName1())))
		execSQL += " and b.propertyname1 like " + util.Common.sqlChar("%"+getQ_propertyName1()+"%");
	
	if (!"".equals(Common.get(getQ_fundType()))){
		execSQL += " and b.fundtype = " + util.Common.sqlChar(getQ_fundType());
	}
	if (!"".equals(Common.get(getQ_valuable())) && !"VP".equals(type) && !"RT".equals(type)){
		execSQL += " and b.valuable = " + util.Common.sqlChar(getQ_valuable());
	}
	
	return execSQL;
}

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
				"";	
	try{
		rs = db.querySQL(sql);
		while (rs.next()){
		    equipmentName.append(""+rs.getString("equipmentName"));
		}
		if(equipmentName.toString().equals("")){
		    equipmentName.append(" ");
		}
		setEquipmentName(equipmentName.toString());
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}