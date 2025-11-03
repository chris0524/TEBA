/*
*<br>程式目的：非消耗品保管單位別清冊報表檔 
*<br>程式代號：untne029r
*<br>撰寫日期：103/09/18
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTNE029R extends SuperBean{

	String enterOrg;
	String ownership;
	String dataState;
	String verify;
	String keepUnit;
	String propertyKind;
	String fundType;
	String valuable;
	String propertyNo;
	String serialNo;
	String equipmentName;

	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_dataState;
	String q_verify;
	String q_keepUnit;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_keepBureau ;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_propertyNoName;
	String q_serialNoS;
	String q_serialNoE;
	String q_place1;
	String q_place1Name;
	String q_propertyName1;
	String isOrganManager;
    String isAdminManager;
    String organID; 
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
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }

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
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
	public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
	public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
	public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
	public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
	public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
	public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
	public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }
    public String getQ_place1() {return checkGet(q_place1);}
	public void setQ_place1(String qPlace1) {q_place1 = checkSet(qPlace1);}
	public String getQ_place1Name() {return checkGet(q_place1Name);}
	public void setQ_place1Name(String qPlace1Name) {q_place1Name = checkSet(qPlace1Name);}
	public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
    public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String qPropertyName1) {q_propertyName1 = checkSet(qPropertyName1);}	
	public String getQ_serialNoS() {	return checkGet(q_serialNoS);}
	public void setQ_serialNoS(String q_serialNoS) {	this.q_serialNoS = checkSet(q_serialNoS);}
	public String getQ_serialNoE() {	return checkGet(q_serialNoE);}
	public void setQ_serialNoE(String q_serialNoE) {	this.q_serialNoE = checkSet(q_serialNoE);}
	
public DefaultTableModel getResultModel() throws Exception{
	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","DIFFERENTKINDNAME","KEEPUNIT","PROPERTYNOSERIALNO","PROPERTYNAME","SPECIFICATIONNAMEPLATE","BOOKAMOUNTPROPERTYUNIT","SOURCEDATEBUYDATE","LIMITYEAR","PLACE","BOOKVALUE","EQUIPMENTNAME","KEEPERNAME"};
    	
    	String execSQL="select a.enterOrg as newEnterorg, a.ownership as newOwnership, b.propertyNo as newPropertyNo, b.serialNo as newSerialNo, " +
    					" (select codename from SYSCA_CODE t where codekindid='DFK' and t.codeid=a.differencekind ) as differencekindName," +
    					" e.unitName as keepUnit," +
		    			" c.organAName as enterOrgName, c.titlename1, c.titlename2," +
		    			" b.propertyNo+'-'+b.serialNo as propertynoSerialno," +
		    			" d.propertyName," +
		    			" b.propertyName1," +
		    			" a.specification," +
		    			" a.nameplate," +
		    			" d.propertyUnit,a.otherPropertyUnit," +
		    			" b.bookamount,"+
		    			" a.sourceDate," +
		    			" a.buyDate," +
//		    			" d.limitYear," +
		    			" a.otherLimitYear," +
		    			" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = b.place1) as place1Name," +
		    			" b.place," +
		    			" b.bookValue," +
		    			" (select g.keeperName from UNTMP_Keeper g where b.enterOrg=g.enterOrg and b.userNo=g.keeperNo ) as keeperName1," +
		    			" (select h.keeperName from UNTMP_Keeper h where b.enterOrg=h.enterOrg and b.keeper=h.keeperNo ) as keeperName2 " +
		    			" from UNTNE_Nonexp a"+
		    			" left join SYSCA_Organ c on a.enterOrg=c.organID,"+
		    			" UNTNE_NonexpDetail b ,SYSPK_PropertyCode2 d ,UNTMP_KeepUnit e " +
		    			" where a.enterorg=b.enterorg and a.differencekind = b.differencekind and a.ownership=b.ownership and  b.propertyNo=a.propertyNo and a.lotNo=b.lotNo and b.enterOrg=d.enterOrg and b.propertyNo=d.propertyNo and b.enterOrg=e.enterOrg and b.keepUnit=e.unitNo" +
		    			" and d.propertyType='1' ";
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    				execSQL += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    				execSQL += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";
    			} else {
    				execSQL+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		} 
    	}
    	if (!"".equals(Common.get(getQ_ownership())))
    		execSQL = execSQL + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_propertyNoS()))
		    execSQL+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());	
		
		if (!"".equals(getQ_propertyNoE()))
		    execSQL+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());
    	
		if (!"".equals(getQ_serialNoS()))
		    execSQL+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS());	
		
		if (!"".equals(getQ_serialNoE()))
		    execSQL+=" and b.serialNo <= " + Common.sqlChar(getQ_serialNoE());
    	if (!"".equals(Common.get(getQ_dataState())))
    		execSQL = execSQL + " and b.dataState = " + util.Common.sqlChar(getQ_dataState());
    	
    	if (!"".equals(Common.get(getQ_verify())))
    		execSQL = execSQL + " and a.verify = " + util.Common.sqlChar(getQ_verify());
    	    	
    	if(!"".equals(Common.get(getQ_keepUnit()))){
    		execSQL += " and b.keepUnit = " + util.Common.sqlChar(getQ_keepUnit());
    	}else if(!"".equals(Common.get(getQ_keepBureau()))){
    		execSQL += " and b.keepUnit like '" + getQ_keepBureau()+"%' ";
    	}
    	
    	if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL = execSQL + " and a.propertyKind = " + util.Common.sqlChar(getQ_propertyKind());
    	
    	if (!"".equals(Common.get(getQ_fundType()))){
    		execSQL = execSQL + " and a.fundType = " + util.Common.sqlChar(getQ_fundType());
    	}
    	if (!"".equals(Common.get(getQ_valuable())))
    		execSQL = execSQL + " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
    	
		if (!"".equals(Common.get(getQ_propertyName1())))
    		execSQL += " and a.propertyname1 like " + util.Common.sqlChar("%"+getQ_propertyName1()+"%");
		
		if(!"".equals(getQ_place1()))
			execSQL+=" and b.place1 = " + Common.sqlChar(getQ_place1());
    			
    	execSQL=execSQL+" order by a.enterOrg,b.keepUnit,a.propertyNo,b.serialNo ";
		
    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTNE029R", this.getObjPath().replaceAll("&gt;", ">"));
		
    	ResultSet rs = db.querySQL(execSQL);

    	String tansToDouble;
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[13];
        	setEnterOrg(rs.getString("newEnterOrg"));
        	setOwnership(rs.getString("newOwnership"));
        	setPropertyNo(rs.getString("newPropertyNo"));
        	setSerialNo(rs.getString("newSerialNo"));
            data[0] = Common.get(rs.getString("titlename1")) + "\r\n" + Common.get(rs.getString("titlename2"));
            data[1] = Common.get(rs.getString("differencekindName"));
            data[2] = Common.get(rs.getString("keepUnit"));
            data[3] = Common.get(rs.getString("propertynoSerialno"));
            data[4] = Common.get(rs.getString("propertyName")) + "\n" +Common.get(rs.getString("propertyName1"));
//          data[5] = Common.get(rs.getString("specification"))+ "\n" + "/" + "\n" +Common.get(rs.getString("nameplate"));//20150121測試廠牌形式不需分行
            data[5] = Common.get(rs.getString("specification"))+ "/" +Common.get(rs.getString("nameplate"));
            data[6] = Common.get(rs.getString("bookamount"))+ "\n" + Common.get(rs.getString("otherPropertyUnit"));
//            data[6] = Common.get(rs.getString("bookamount"))+ "\n" + Common.get(rs.getString("propertyUnit"));
//            PropertyUnit= Common.get(rs.getString("otherPropertyUnit"));
//            if("".equals(data[6])) data[6]=Common.get(rs.getString("bookamount"))+ "\n" + PropertyUnit;
            data[7] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"), "1")) + "\n" + Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"), "1"));
//            data[8] = Common.get(rs.getString("limitYear"));
//        	limitYeartemp= Common.get(rs.getString("otherLimitYear"));
//        	if("".equals(data[8])) data[8]=limitYeartemp;
            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
			int year = otherLimitMonth /12;
			int month = otherLimitMonth % 12;
			data[8] = year +"年"+month+"個月";
        	
        	data[9] = Common.get(rs.getString("place1Name")) + "\n" + Common.get(rs.getString("place"));
        	
        	tansToDouble= rs.getString("bookValue")== null ? "" :rs.getString("bookValue");
            if(tansToDouble!=null) data[10]= new Double(Double.parseDouble(tansToDouble));
            else data[10]= new Double(0);
            data[11] = getEquipmentName(db);
            data[12] = Common.get(rs.getString("keeperName1")) + "\n" + Common.get(rs.getString("keeperName2"));
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


public String getEquipmentName(Database db){
	String equipmentName = "";
	String sql="select a.equipmentName " +
	" from UNTNE_Attachment2 a" +
	" where 1=1 and a.dataState='1'" + 
	" and a.enterOrg=" + util.Common.sqlChar(getEnterOrg())+
	" and a.ownership=" + util.Common.sqlChar(getOwnership())+
	" and a.propertyNo =" + util.Common.sqlChar(getPropertyNo())+
	" and a.serialNo=" + util.Common.sqlChar(getSerialNo())+
	"";	
	
	try {
		ResultSet rs3 = db.querySQL(sql);
		while (rs3.next()){
		    equipmentName += rs3.getString("equipmentName")+"、";
		}
		
		rs3.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return "".equals(equipmentName)? equipmentName : equipmentName.substring(equipmentName.length()-1, equipmentName.length()) ;
}

}