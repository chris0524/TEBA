/*
*<br>程式目的：非消耗品保管人清冊報表檔 
*<br>程式代號：untne030r
*<br>撰寫日期：103/09/18
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import TDlib_Simple.tools.src.StringTools;

public class UNTNE030R extends SuperBean{
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_dataState;
	String q_verify;
	String q_keepUnit;
	String q_keeper;
	String q_useunit;
	String q_user;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_propertyNoName;
	String q_serialNoS;
	String q_serialNoE;
	String q_propertyKind;
	String q_propertyName1;
	String q_fundType;
	String q_valuable;
	String q_keepBureau ;
	String q_print;
	String q_place1;
	String q_place1Name;
	String q_place;
	
	String isOrganManager;
    String isAdminManager;
    String organID; 
	String reportType;
	
	String enterOrg;
	String ownership;
    String propertyNo;
	String serialNo;
	String equipmentName;
	String userName; 
	DecimalFormat amount = new DecimalFormat("###,###,###,##0.00");
	
	public String getReportType() { return checkGet(reportType); }
	public void setReportType(String reportType) { this.reportType = checkGet(reportType); }
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
	public String getQ_keeper(){ return checkGet(q_keeper); }
	public void setQ_keeper(String s){ q_keeper=checkSet(s); }
	public String getQ_useunit() {return checkGet(q_useunit);}
	public void setQ_useunit(String qUseunit) {q_useunit = checkSet(qUseunit);}
	public String getQ_user() {return checkGet(q_user);}
	public void setQ_user(String qUser) {q_user = checkSet(qUser);}
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_propertyName1() {return checkGet(q_propertyName1);}
	public void setQ_propertyName1(String qPropertyName1) {q_propertyName1 = checkSet(qPropertyName1);}
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
	public String getQ_print(){ return checkGet(q_print); }
	public void setQ_print(String s){ q_print=checkSet(s); }
    public String getQ_place1() {return checkGet(q_place1);}
	public void setQ_place1(String qPlace1) {q_place1 = checkSet(qPlace1);}
	public String getQ_place1Name() {return checkGet(q_place1Name);}
	public void setQ_place1Name(String qPlace1Name) {q_place1Name = checkSet(qPlace1Name);}
	public String getQ_place() {return checkGet(q_place);}
	public void setQ_place(String qPlace) {q_place = checkSet(qPlace);}
	public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
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
	public String getQ_serialNoS() {	return checkGet(q_serialNoS);}
	public void setQ_serialNoS(String q_serialNoS) {	this.q_serialNoS = checkSet(q_serialNoS);}
	public String getQ_serialNoE() {	return checkGet(q_serialNoE);}
	public void setQ_serialNoE(String q_serialNoE) {	this.q_serialNoE = checkSet(q_serialNoE);}

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
    public String getUserName() { return checkGet(userName); }
    public void setUserName(String s) { userName = checkSet(s); } 

	//計算總共有幾筆資料
	protected int getCountRow(String SQL){
		Database db = new Database();	
		ResultSet rs;	
		int count = 0;
		try{
			rs = db.querySQL(SQL);
			if (rs.next()){
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return count;
	}
	
public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	String querySQL = "";
    String oldChangeRow = "";
    String newChangeRow = "";
    String serialNoS = "";
    String serialNoE = "";
    String oldSerialNo = "";
    String newSerialNo = "";
    double bookAmount = 0;
    Double bookValue = 0.0;
    int countRow = 0;
    
   
   
    try{
    	String[] columns = new String[] {"ENTERORGNAME","DIFFERENTKINDNAME","KEEPER","USER","PROPERTYNOSERIALNO","PROPERTYNAME","PROPERTYNAME1","SPECIFICATIONNAMEPLATE","BOOKAMOUNTPROPERTYUNIT","SOURCEDATEBUYDATE","LIMITYEAR","BOOKVALUE","PLACE","KEEPUNIT","EQUIPMENTNAME","BOOKAMOUNT","CHANGEROW"};
    	String execSQL=" select (a.enterorg+a.differencekind+b.keeper+substring(a.propertyno,1,1)) as changerow, b.serialno, b.oldserialno,  e.unitname as keepunit, a.differencekind, "+
    					"(select codename from SYSCA_CODE t where codekindid='DFK' and t.codeid=a.differencekind ) as differencekindname, "+
    					"c.organaname as enterorgname, b.propertyno, d.propertyname, b.propertyname1, a.specification, a.nameplate, d.propertyunit,a.otherpropertyunit, b.bookamount, a.sourcedate, a.buydate, b.otherlimityear, "+
    					" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = b.place1) as place1name, b.place, b.bookvalue, b.bookamount, "+
		    			" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg and b.keeper=h.keeperno and a.enterorg=g.enterorg and b.keepunit=g.unitno) as keeper, " +
		    			" (select g.unitname+' '+h.keepername from UNTMP_KEEPUNIT g,UNTMP_KEEPER h where a.enterorg=h.enterorg and b.userno=h.keeperno and a.enterorg=g.enterorg and b.useunit=g.unitno) as user1 " +
		    			" from UNTNE_NONEXPDETAIL b"+
		    			" left join UNTNE_NONEXP a on a.enterorg=b.enterorg and a.ownership=b.ownership and a.differencekind=b.differencekind and b.propertyno=a.propertyno and a.lotno=b.lotno" +
		    			" left join SYSCA_ORGAN c on a.enterorg=c.organid" +
		    			" left join SYSPK_PROPERTYCODE2 d on b.enterorg=d.enterorg and b.propertyno=d.propertyno" +
		    			" left join UNTMP_KEEPUNIT e on b.enterorg=e.enterorg and b.useunit=e.unitno" +
		    			" where 1=1  and b.datastate='1' ";
    	if (!"".equals(getQ_enterOrg())) {
    		querySQL +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				//querySQL += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
    				execSQL += " and( a.enterorg = " + Common.sqlChar(getOrganID()) ;
    				execSQL += " or organID in (select organid from SYSCA_ORGAN where organsuperior='"+ Common.sqlChar(getOrganID())+"'))";
    			} else {
    				querySQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
    			}
    		} 
    	}
    	if (!"".equals(Common.get(getQ_ownership())))
    		querySQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_propertyNoS()))
		    execSQL+=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());	
		
		if (!"".equals(getQ_propertyNoE()))
		    execSQL+=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());
    	
		if (!"".equals(getQ_serialNoS()))
		    execSQL+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS());	
		
		if (!"".equals(getQ_serialNoE()))
		    execSQL+=" and b.serialNo <= " + Common.sqlChar(getQ_serialNoE());
    	if(!"".equals(Common.get(getQ_keepUnit())))
    		querySQL += " and b.keepunit = " + util.Common.sqlChar(getQ_keepUnit());
    	if(!"".equals(Common.get(getQ_keeper())))
    		querySQL += " and b.keeper = " + util.Common.sqlChar(getQ_keeper());
    	
    	if(!"".equals(Common.get(getQ_useunit())))
    		querySQL += " and b.useUnit = " + util.Common.sqlChar(getQ_useunit());
    	if(!"".equals(Common.get(getQ_user())))
    		querySQL += " and b.userno = " + util.Common.sqlChar(getQ_user());
    	
    	if (!"".equals(Common.get(getQ_propertyKind())))
    		querySQL += " and a.propertykind = " + util.Common.sqlChar(getQ_propertyKind());
    	
    	if (!"".equals(Common.get(getQ_propertyName1())))
    		querySQL += " and a.propertyname1 like " + util.Common.sqlChar(getQ_propertyName1()+"%");
    	
    	if (!"".equals(Common.get(getQ_fundType()))){
    		querySQL += " and a.fundtype = " + util.Common.sqlChar(getQ_fundType());
    	}
    	if (!"".equals(Common.get(getQ_valuable())))
    		querySQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
    	if (!"".equals(Common.get(getQ_verify())))
    		querySQL += " and a.verify = " + util.Common.sqlChar(getQ_verify());
    	
    	if (!"".equals(Common.get(getQ_place1())))
    		querySQL += " and b.place1 = " + util.Common.sqlChar(getQ_place1());
    	
    	if (!"".equals(Common.get(getQ_place())))
    		querySQL += " and b.place like " + util.Common.sqlChar(getQ_place()+"%");
    	
    		
    	if("2".equals(Common.get(getQ_print()))){
    	    	execSQL += " and a.fundtype is null" ;
    	}else if("3".equals(Common.get(getQ_print())))
    	    	execSQL += " and a.fundtype is not null" ;
    	    	
    	execSQL += querySQL + " order by changerow,a.differencekind,b.keepunit,b.keeper,a.propertyno,b.serialno ";

    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTNE030R", this.getObjPath().replaceAll("&gt;", ">"));
		
    	ResultSet rs = db.querySQL(execSQL);

    	String PropertyUnit;
        StringTools st = new StringTools();
        Vector rowData = new Vector();
        while(rs.next()){
        	countRow++;
        	Object[] data = new Object[columns.length];
        	//===將同物品編號、型式/廠牌、取得日期、價值、存置地點、使用單位列為一行；物品編號之分號請用起-訖=========
        	Object[] oldData = new Object[columns.length];
    		newChangeRow =  Common.get(rs.getString("changeRow"));
    		newSerialNo =  Common.get(rs.getString("serialNo"));
    		serialNoE =  Common.get(rs.getString("serialNo"));
    		if(newChangeRow.equals(oldChangeRow) && newSerialNo.equals(oldSerialNo)){
    			bookAmount += rs.getDouble("bookAmount");
    			bookValue += rs.getInt("bookValue");
    			serialNoE = Common.get(rs.getString("serialNo"));
    		}else{
    			serialNoS = Common.get(rs.getString("serialNo"));
    			bookAmount = rs.getDouble("bookAmount");
    			bookValue = rs.getDouble("bookValue");
    		}
    		//=========================================================================================
            data[0] = rs.getString("enterOrgName");
            data[1] = Common.get(rs.getString("differencekindName"));
            data[2] = Common.get(rs.getString("keeper"));
            data[3] = Common.get(rs.getString("user1"));
            data[4] = Common.get(rs.getString("propertyNo"))+"-\n"+newSerialNo;
//            data[4] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[4]), " ", "原物品分號", Common.get(rs.getString("oldserialno")));
            data[5] = Common.get(rs.getString("propertyName"));
            data[6] = Common.get(rs.getString("propertyName1"));
//          data[7] = Common.get(rs.getString("specification"))+ "\n" + "/" + "\n" +Common.get(rs.getString("nameplate"));//20150121測試廠牌形式不需分行
            data[7] = Common.get(rs.getString("specification"))+ "/" +Common.get(rs.getString("nameplate"));
//            data[8] = Common.get(rs.getString("propertyUnit"));
            PropertyUnit=Common.get(rs.getString("otherPropertyUnit"));
            data[8]= amount.format(new Double(bookAmount)) + "\n" + PropertyUnit;           
//            if(data[8]==null) data[8]= new Long(bookAmount) + "\n" + PropertyUnit;           
            data[9] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourceDate"), "1")) + "\n" + Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buyDate"), "1"));
            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
			int year = otherLimitMonth /12;
			int month = otherLimitMonth % 12;
			data[10] = year +"年"+month+"個月";
        	data[11]= bookValue;
        	// 存置地點
        	data[12] = Common.get(rs.getString("place1Name"));
        	if (!"".equals(Common.get(rs.getString("place")))) {
        		// 存置地點說明
        		data[12] = data[12] + "\n" + Common.get(rs.getString("place"));
        	}
        	
        	data[13] = Common.get(rs.getString("keepUnit"));
            data[14] = getEquipmentName(db);
            data[15] = new Double(bookAmount) ;
            data[16] = Common.get(rs.getString("changeRow"));

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