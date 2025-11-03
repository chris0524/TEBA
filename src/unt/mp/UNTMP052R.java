/*
*<br>程式目的：動產小標籤查詢檔 
*<br>程式代號：untmp037r
*<br>撰寫日期：94/11/29
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTMP052R extends SuperBean{
	String q_labelType;
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_closing;
	String q_verify;
	String q_caseNoS;
	String q_caseNoE;
	String q_enterDateS;
	String q_enterDateE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_propertyNoS;
	String q_propertyNoE;
	String q_propertyNoSName;
	String q_propertyNoEName;
	String q_dataState;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_serialNoS;
	String q_serialNoE;
	String q_keepUnit;
	String q_keeper;
	String q_useUnit;
	String q_userNo;
	String q_blankSpace;
	String q_printKeeper;

	public String getQ_labelType(){ return checkGet(q_labelType); }
	public void setQ_labelType(String s){ q_labelType=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_closing(){ return checkGet(q_closing); }
	public void setQ_closing(String s){ q_closing=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
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
    public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
    public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
    public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
    public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
    public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
    public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
    public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
    public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }    
    public String getQ_dataState(){ return checkGet(q_dataState); }
    public void setQ_dataState(String s){ q_dataState=checkSet(s); }    
    public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
    public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
    public String getQ_fundType(){ return checkGet(q_fundType); }
    public void setQ_fundType(String s){ q_fundType=checkSet(s); } 
    public String getQ_valuable(){ return checkGet(q_valuable); }
    public void setQ_valuable(String s){ q_valuable=checkSet(s); }
    public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
    public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
    public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
    public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
    public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
    public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
    public String getQ_keeper(){ return checkGet(q_keeper); }
    public void setQ_keeper(String s){ q_keeper=checkSet(s); }
    public String getQ_useUnit(){ return checkGet(q_useUnit); }
    public void setQ_useUnit(String s){ q_useUnit=checkSet(s); }
    public String getQ_userNo(){ return checkGet(q_userNo); }
    public void setQ_userNo(String s){ q_userNo=checkSet(s); }
    public String getQ_blankSpace(){ return checkGet(q_blankSpace); }
    public void setQ_blankSpace(String s){ q_blankSpace=checkSet(s); }
	public String getQ_printKeeper(){ return checkGet(q_printKeeper); }
	public void setQ_printKeeper(String s){ q_printKeeper=checkSet(s); }

	String isOrganManager;
	String isAdminManager;
	String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }  

	String q_workKind;
	public String getQ_workKind(){ return checkGet(q_workKind); }
	public void setQ_workKind(String s){ q_workKind=checkSet(s); }
	
	String q_keepBureau;
	String q_useBureau;
	public String getQ_keepBureau(){ return checkGet(q_keepBureau); }
	public void setQ_keepBureau(String s){ q_keepBureau=checkSet(s); }
	public String getQ_useBureau(){ return checkGet(q_useBureau); }
	public void setQ_useBureau(String s){ q_useBureau=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Vector rowData = new Vector();
	
	String enterDateName = "";
    try{
    	String[] columns = new String[] {"barCode","property","keeper"};

       	String execSQL = " select distinct b.enterOrg, b.ownership, c.keepunit, c.keeper, b.propertyNo, c.serialNo, " +"\n"+
       					 " 		  d.ORGANANAME as enterOrgName, " +"\n"+
       					 " 		  (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +"\n"+
       					 " 		  decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKindName, " +"\n"+
       					 "		  b.propertyNo, c.serialNo, " +
       					 " 		 (select e.limitYear from SYSPK_PropertyCode e where b.propertyNo = e.propertyNo and e.enterOrg in('000000000A',b.enterOrg) and e.propertyType='1') as limitYear, " +"\n"+
       					 " 		 b.otherLimitYear, "+"\n"+
       					 " 		 (select e.propertyName from SYSPK_PropertyCode e where b.propertyNo = e.propertyNo and e.enterOrg in('000000000A',b.enterOrg) and e.propertyType='1') as propertyName, " +"\n"+
       					 " 		 b.buyDate, b.specification, f.keeperName, c.place, " +"\n"+
       					 " 		 c.bookValue , c.barCode, p.unitName "+"\n"+
       					 "       ,(select k.unitname from untmp_keepunit k where k.enterorg = c.enterorg and k.unitno = c.keepunit) as keepUnitName " +
       					 " 		 ,(select u.keepername from untmp_keeper u where u.enterorg = c.enterorg and u.unitno = c.keepunit and u.keeperno = c.keeper ) as keeperName " +
       					 " from UNTMP_AddProof a, UNTMP_Movable b, UNTMP_MovableDetail c, SYSCA_Organ d, UNTMP_Keeper f, "+"\n"+
       					 " 		UNTMP_AdjustProof j, UNTMP_AdjustDetail k, " +"\n"+
       					 " 		UNTMP_ReduceProof l, UNTMP_ReduceDetail m, " +"\n"+
       					 " 		UNTMP_MoveProof n, UNTMP_MoveDetail o, UNTMP_KeepUnit p "+"\n"+
       					 " where 1=1 and a.enterOrg = d.organID and b.dataState='1' and c.dataState='1' " +"\n"+
       					 " and a.caseNo=b.caseNo(+) and a.enterOrg=b.enterOrg(+) and a.ownership=b.ownership(+) " +"\n"+
       					 " and c.enterOrg(+)=b.enterOrg and c.ownership(+)=b.ownership and b.lotNo=c.lotNo(+) and b.propertyNo=c.propertyNo(+) " +"\n"+
       					 " and b.enterOrg=f.enterOrg and c.keepUnit=f.unitNo and c.keeper=f.keeperNo "+"\n"+
       					 " and j.enterOrg(+)=k.enterOrg and j.ownership(+)=k.ownership and j.caseNo(+)=k.caseNo " +"\n"+
       					 " and k.enterOrg(+)=c.enterOrg and k.ownership(+)=c.ownership and k.propertyNo(+)=c.propertyNo and k.lotNo(+)=c.lotNo and k.serialNo(+)=c.serialNo"+"\n"+
       					 " and l.enterOrg(+)=m.enterOrg and l.ownership(+)=m.ownership and l.caseNo(+)=m.caseNo " +"\n"+
       					 " and m.enterOrg(+)=c.enterOrg and m.ownership(+)=c.ownership and m.propertyNo(+)=c.propertyNo and m.lotNo(+)=c.lotNo and m.serialNo(+)=c.serialNo"+"\n"+
       					 " and n.enterOrg(+)=o.enterOrg and n.ownership(+)=o.ownership and n.caseNo(+)=o.caseNo " +"\n"+
       					 " and o.enterOrg(+)=c.enterOrg and o.ownership(+)=c.ownership and o.propertyNo(+)=c.propertyNo and o.lotNo(+)=c.lotNo and o.serialNo(+)=c.serialNo"+"\n"+
       					 " and c.enterOrg = p.enterorg and c.keepUnit = p.unitno "+"\n"+ //keepUnit欄位位置 & ＋的使用時機
       					 "";
		/**
		* 當作業種類(q_workKind)選擇"增加單"時則帶入"a"，因table為"a"
		*                     選擇"增減值單"時則帶入"j"，因table為"j"
		*                     選擇"減損單"時則帶入"l"，因table為"l"
		*                     選擇"移動單"時則帶入"n"，因table為"n"
		*/
		if("a".equals(getQ_workKind())){
			enterDateName = "enterDate";
		}else if("j".equals(getQ_workKind())){
			enterDateName = "adjustDate";
		}else if("l".equals(getQ_workKind())){
			enterDateName = "reduceDate";
		}else if("n".equals(getQ_workKind())){
			enterDateName = "moveDate";
		}
		if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";		
				} else {
					execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		
		if (!"".equals(Common.get(getQ_ownership()))){
			execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		}
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and "+getQ_workKind()+".caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and "+getQ_workKind()+".caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
		if (!"".equals(Common.get(getQ_writeDateS())))
			execSQL += " and "+getQ_workKind()+".writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
		if (!"".equals(Common.get(getQ_writeDateE())))
			execSQL += " and "+getQ_workKind()+".writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and "+getQ_workKind()+".proofDoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL += " and "+getQ_workKind()+".proofNo >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and "+getQ_workKind()+".proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_closing()))
			execSQL+=" and "+getQ_workKind()+".closing = " + Common.sqlChar(getQ_closing()) ;
		if (!"".equals(getQ_enterDateS()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_propertyNoS()))
			execSQL+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			execSQL+=" and b.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
		if (!"".equals(getQ_propertyKind()))
			execSQL+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
			execSQL+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_serialNoS()))
			execSQL+=" and c.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
			execSQL+=" and c.serialNo <=" + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getQ_keepUnit()))
			execSQL+=" and c.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
		if (!"".equals(getQ_keeper()))
			execSQL+=" and c.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
		if (!"".equals(getQ_useUnit()))
			execSQL+=" and c.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
		if (!"".equals(getQ_userNo()))
			execSQL+=" and c.userNo = " + Common.sqlChar(getQ_userNo()) ;	    
			execSQL+=" order by b.enterOrg, b.ownership, c.keepUnit, c.keeper, b.propertyNo, c.serialNo ";
				
        ResultSet rs = db.querySQL(execSQL);
        while(rs.next()){
        	Object[] data = new Object[3];
        	data[0] = Common.get(rs.getString("barcode"));
        	data[1] = Common.get(rs.getString("serialno")) + "　" 
        			+ Common.get(rs.getString("propertyname"));
        	
        	if("Y".equals(getQ_printKeeper())){
	        	data[2] = Common.get(rs.getString("keeperName")) + "  "
	        			+ Common.get(rs.getString("keepUnitName"));
        	}else{
	        	data[2] = Common.get(rs.getString("keepUnitName"));
        	}
        	
			rowData.addElement(data);
        }
    	
        Object[][] finalData = new Object[0][0];
        finalData = (Object[][])rowData.toArray(finalData);
        model.setDataVector(finalData, columns);  

    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    
    return model;
}

}
