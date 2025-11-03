/*
*<br>程式目的：非消耗品保管人移交清冊報表檔 
*<br>程式代號：untne012r
*<br>撰寫日期：95/02/22
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTNE012R extends SuperBean{

	String enterOrg;
	String ownership;
	String propertyNo;
	String serialNo;
	String caseNoS;
	String caseNoE;
	String writeDateS;
	String writeDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;
	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getCaseNoS(){ return checkGet(caseNoS); }
	public void setCaseNoS(String s){ caseNoS=checkSet(s); }
	public String getCaseNoE(){ return checkGet(caseNoE); }
	public void setCaseNoE(String s){ caseNoE=checkSet(s); }
	public String getWriteDateS(){ return checkGet(writeDateS); }
	public void setWriteDateS(String s){ writeDateS=checkSet(s); }
	public String getWriteDateE(){ return checkGet(writeDateE); }
	public void setWriteDateE(String s){ writeDateE=checkSet(s); }
	public String getProofDoc(){ return checkGet(proofDoc); }
	public void setProofDoc(String s){ proofDoc=checkSet(s); }
	public String getProofNoS(){ return checkGet(proofNoS); }
	public void setProofNoS(String s){ proofNoS=checkSet(s); }
	public String getProofNoE(){ return checkGet(proofNoE); }
	public void setProofNoE(String s){ proofNoE=checkSet(s); }

	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	
	String isOrganManager;
    String isAdminManager;
    String organID;    
    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 

    String equipmentName;
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
	
    String orderBy;    
    public String getOrderBy() {return checkGet(orderBy);}
	public void setOrderBy(String orderBy) {this.orderBy = checkSet(orderBy);}

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String KOC = "(召集人或其授權代簽人)";
    try{
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","KEEPERNAME","NEWKEEPERNAME","DATE","CASENO","PROPERTYNOSERIALNO","PROPERTYNAME","NAMEPLATESPECIFICATION","BUYDATE","NEWMOVEDATE","LIMITYEAR","PROPERTYUNIT","BOOKAMOUNT","BOOKUNIT","BOOKVALUE","NEWPLACE","PROPERTYKIND","caseNoOldKeepUnitOldKeeperNewKeepUnitNewKeeper","ATTACHMENT2","KOC"};
    	String execSQL="select b.enterOrg as shareEnterOrg, b.ownership as shareOwnership, b.propertyNo as sharePropertyNo, b.serialNo as shareSerialNo, " +
    			" c.organAName as enterOrgName," +
    			" a.ownership, " +
    			" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
    			" b.oldKeepUnit, (select c.unitName from UNTMP_KeepUnit c where b.enterOrg=c.enterOrg and b.oldKeepUnit=c.unitNo) as oldKeepUnitName, " +
    			" b.oldKeeper, (select c.keeperName from UNTMP_Keeper c where b.enterOrg=c.enterOrg and b.oldKeepUnit=c.unitNo and b.oldKeeper=c.keeperNo) as oldKeeperName, " +
    			" b.newKeepUnit, (select c.unitName from UNTMP_KeepUnit c where b.enterOrg=c.enterOrg and b.newKeepUnit=c.unitNo) as newKeepUnitName, " +
    			" b.newKeeper, (select c.keeperName from UNTMP_Keeper c where b.enterOrg=c.enterOrg and b.newKeepUnit=c.unitNo and b.newKeeper=c.keeperNo) as newKeeperName, " +
    			" a.caseNo, " +
    			" b.propertyNo, " +
    			" b.serialNo, "+
    			" d.propertyName, " +
    			" (b.specification || '/' || b.nameplate) as nameplateSpecification, " +
    			" b.buyDate, " +
    			" b.newMoveDate, " +
    			" d.limitYear, b.otherLimitYear, " +
    			" d.propertyUnit, b.otherPropertyUnit, " +
    			" b.bookAmount, b.bookUnit, b.bookValue, " +
    			" b.newPlace, " +
    			" decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKind, " +
    			" (b.caseNo || b.oldKeepUnit || b.oldKeeper || b.newKeepUnit || b.newKeeper ) as caseNoOldNew " +
    			" from UNTNE_MoveProof a,UNTNE_MoveDetail b,SYSCA_Organ c,SYSPK_PropertyCode2 d "+
    			" where a.enterorg=b.enterorg and a.caseno=b.caseNo and a.ownership=b.ownership " +
    			" and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+) "+
    			" and d.enterOrg = b.enterOrg "+
    			"";
    	
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL+= " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		execSQL+= " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		execSQL+= " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL+= " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL+= " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		execSQL+= " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		execSQL+= " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		execSQL+= " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	
    	execSQL+=" order by a.caseNo, b.oldKeepUnit, b.oldKeeper, b.newKeepUnit, b.newKeeper, b.propertyNo, b.serialNo ";
        System.out.println(execSQL);
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[21];
        	setEnterOrg(rs.getString("shareEnterOrg"));
        	setOwnership(rs.getString("shareOwnership"));
        	setPropertyNo(rs.getString("sharePropertyNo"));
        	setSerialNo(rs.getString("shareSerialNo"));
        	data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownershipName");
            data[2] = Common.get(rs.getString("oldKeepUnitName"))+" "+Common.get(rs.getString("oldKeeperName"));
            data[3] = Common.get(rs.getString("newKeepUnitName"))+" "+Common.get(rs.getString("newKeeperName"));
            data[4] = Common.MaskDate(Datetime.getYYYMMDD());
            data[5] = rs.getString("caseNo")+"　";
            data[6] = rs.getString("propertyNo")+"\n-"+rs.getString("serialNo");
            data[7] = Common.getCutStr(rs.getString("propertyName"),7);
            data[8] = Common.getCutStr(rs.getString("nameplateSpecification"),6);
            data[9] = Common.MaskDate(rs.getString("buyDate"));
            data[10] = Common.MaskDate(rs.getString("newMoveDate"));
            if(rs.getString("limitYear")!=null && !"".equals(rs.getString("limitYear"))){
            	data[11] = rs.getString("limitYear");
            }else{
            	data[11] = rs.getString("otherLimitYear");
            }
            if(rs.getString("propertyUnit")!=null && !"".equals(rs.getString("propertyUnit"))){
            	data[12] = rs.getString("propertyUnit");
            }else{
            	data[12] = rs.getString("otherPropertyUnit");
            }
            data[13] = new Double(rs.getDouble("bookAmount"));            
            data[14] = Common.valueFormat(rs.getString("bookUnit"));
            data[15] = new Double(rs.getDouble("bookValue"));
            data[16] = Common.getCutStr(rs.getString("newPlace"),7);            
            data[17] = rs.getString("propertyKind");
            data[18] = rs.getString("caseNoOldNew");
            getTotalEquipmentName();
            data[19] = getEquipmentName();
            if(q_ownership.equals("4")){
            	data[20] = KOC;
            }else{
            	data[20] = "";
            }
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

//抓取該非消耗品之「UNTNE_Attachment2非消耗品批號明細附屬設備檔」中資料狀態為「1:現存」者之「equipmentName名稱」，名稱與名稱之間以「、」區隔
protected void getTotalEquipmentName(){
    equipmentName="";
	Database db = new Database();	
	ResultSet rs;	
	String sql="select a.equipmentName " +
				" from UNTNE_Attachment2 a" +
				" where 1=1 and a.dataState='1'" + 
				" and a.enterOrg=" + util.Common.sqlChar(getEnterOrg())+
				" and a.ownership=" + util.Common.sqlChar(getOwnership())+
				" and a.propertyNo=" + util.Common.sqlChar(getPropertyNo())+
				" and a.serialNo=" + util.Common.sqlChar(getSerialNo())+
				"";	
	try{
		rs = db.querySQL(sql);
		while (rs.next()){
		    equipmentName += "、"+rs.getString("equipmentName");
		}
		if(equipmentName.equals("")){
		    equipmentName += "、 ";
		}
		setEquipmentName(equipmentName.substring(1));
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}

}
