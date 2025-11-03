/*
*<br>程式目的：建物財產卡報表檔 
*<br>程式代號：untbu013r
*<br>撰寫日期：94/12/12
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.text.DecimalFormat;
//import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import TDlib_Simple.tools.src.DateTools;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;


import unt.ch.UNTCH_Tools;
import util.*;

public class UNTBU013R extends UNTBU001Q{

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
	String shareDifferenceKind;
	String q_enterOrg;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_enterDateS;
	String q_enterDateE;
	String q_closing;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofYear;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_verify;
	String q_propertyNo1;
	String q_propertyNo2;
	String q_propertyNo1Name;
	String q_propertyNo2Name;
	String q_serialNoS;
	String q_serialNoE;
	String q_signNo1;
	String q_signNo2;
	String q_signNo3;
	String q_signNo4;
	String q_signNo5;
	String q_dataState;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
	String q_proceedDateS;
	String q_proceedDateE;
	String q_differenceKind;
	String q_propertyName1;
	String q_keepUnit;
	String q_keeper;
	String q_useUnit;
	String q_userNo;
	String q_userNote;
	String q_place1;
	String q_placeNote;
	String q_originalUstNote;
	String q_place1Name;
	String q_printDepr;
	
	String SignNoA;
	String SignNoB;
	String equipmentName;
	String apportionEndYM;
	String deprAmount;
	String monthDepr;
	String accumDeprYM;
	String accumDepr;
	String deprMethod;
	String checkMonth;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getCheckMonth(){ return checkGet(checkMonth); }
	public void setCheckMonth(String s){ checkMonth=checkSet(s); }
	public String getDeprMethod(){ return checkGet(deprMethod); }
	public void setDeprMethod(String s){ deprMethod=checkSet(s); }
	public String getApportionEndYM(){ return checkGet(apportionEndYM); }
	public void setApportionEndYM(String s){ apportionEndYM=checkSet(s); }
	public String getDeprAmount(){ return checkGet(deprAmount); }
	public void setDeprAmount(String s){ deprAmount=checkSet(s); }
	public String getMonthDepr(){ return checkGet(monthDepr); }
	public void setMonthDepr(String s){ monthDepr=checkSet(s); }
	public String getAccumDeprYM(){ return checkGet(accumDeprYM); }
	public void setAccumDeprYM(String s){ accumDeprYM=checkSet(s); }
	public String getAccumDepr(){ return checkGet(accumDepr); }
	public void setAccumDepr(String s){ accumDepr=checkSet(s); }
	public String getEquipmentName(){ return checkGet(equipmentName); }
	public void setEquipmentName(String s){ equipmentName=checkSet(s); }
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getSharePropertyNo(){ return checkGet(sharePropertyNo); }
	public void setSharePropertyNo(String s){ sharePropertyNo=checkSet(s); }
	public String getShareSerialNo(){ return checkGet(shareSerialNo); }
	public void setShareSerialNo(String s){ shareSerialNo=checkSet(s); }
	public String getShareDifferenceKind() {return checkGet(shareDifferenceKind);	}
	public void setShareDifferenceKind(String s) {shareDifferenceKind = checkSet(s);}
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_closing(){ return checkGet(q_closing); }
	public void setQ_closing(String s){ q_closing=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String qProofYear) {q_proofYear = checkSet(qProofYear);}
	public String getSignNoA() {return checkGet(SignNoA);}
	public void setSignNoA(String signNoA) {SignNoA = checkSet(signNoA);}
	public String getSignNoB() {return checkGet(SignNoB);}
	public void setSignNoB(String signNoB) {SignNoB = checkSet(signNoB);}
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_propertyNo1(){ return checkGet(q_propertyNo1); }
	public void setQ_propertyNo1(String s){ q_propertyNo1=checkSet(s); }
	public String getQ_propertyNo1Name(){ return checkGet(q_propertyNo1Name); }
	public void setQ_propertyNo1Name(String s){ q_propertyNo1Name=checkSet(s); }
	public String getQ_propertyNo2(){ return checkGet(q_propertyNo2); }
	public void setQ_propertyNo2(String s){ q_propertyNo2=checkSet(s); }
	public String getQ_propertyNo2Name(){ return checkGet(q_propertyNo2Name); }
	public void setQ_propertyNo2Name(String s){ q_propertyNo2Name=checkSet(s); }
	public String getQ_serialNoS(){ return checkGet(q_serialNoS); }
	public void setQ_serialNoS(String s){ q_serialNoS=checkSet(s); }
	public String getQ_serialNoE(){ return checkGet(q_serialNoE); }
	public void setQ_serialNoE(String s){ q_serialNoE=checkSet(s); }
	public String getQ_signNo1(){ return checkGet(q_signNo1); }
	public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
	public String getQ_signNo2(){ return checkGet(q_signNo2); }
	public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
	public String getQ_signNo3(){ return checkGet(q_signNo3); }
	public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
	public String getQ_signNo4(){ return checkGet(q_signNo4); }
	public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }
	public String getQ_signNo5(){ return checkGet(q_signNo5); }
	public void setQ_signNo5(String s){ q_signNo5=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
	public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
	public String getQ_proceedDateS(){ return checkGet(q_proceedDateS); }
	public void setQ_proceedDateS(String s){ q_proceedDateS=checkSet(s); }
	public String getQ_proceedDateE(){ return checkGet(q_proceedDateE); }
	public void setQ_proceedDateE(String s){ q_proceedDateE=checkSet(s); }
	public String getQ_differenceKind() {return checkGet(q_differenceKind);	}
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
	public String getQ_propertyName1() {return checkGet(q_propertyName1);	}
	public void setQ_propertyName1(String s) {	q_propertyName1 = checkSet(s);	}
	public String getQ_keepUnit() {	return checkGet(q_keepUnit);	}
	public void setQ_keepUnit(String s) {	q_keepUnit = checkSet(s);	}
	public String getQ_keeper() {return checkGet(q_keeper);	}
	public void setQ_keeper(String s) {q_keeper = checkSet(s);	}
	public String getQ_useUnit() {	return checkGet(q_useUnit);	}
	public void setQ_useUnit(String s) {	q_useUnit = checkSet(s);	}
	public String getQ_userNo() {return checkGet(q_userNo);	}
	public void setQ_userNo(String s) {	q_userNo = checkSet(s);	}
	public String getQ_userNote() {	return checkGet(q_userNote);	}
	public void setQ_userNote(String s) {q_userNote = checkSet(s);	}
	public String getQ_place1() {return checkGet(q_place1);}
	public void setQ_place1(String s) {	q_place1 = checkSet(s);}
	public String getQ_placeNote() {return checkGet(q_placeNote);	}
	public void setQ_placeNote(String s) {	q_placeNote = checkSet(s);	}
   	public String getQ_originalUstNote() {return checkGet(q_originalUstNote);	}
	public void setQ_originalUstNote(String s) {	q_originalUstNote = checkSet(s);	}
	public String getQ_place1Name() {return checkGet(q_place1Name);	}
	public void setQ_place1Name(String s) {	q_place1Name = checkSet(s);}
	public String getQ_printDepr() {return checkGet(q_printDepr);	}
	public void setQ_printDepr(String s) {	q_printDepr = checkSet(s);}
	String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 


	
public DefaultTableModel getResultModel() throws Exception{
	UNTBU013R obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORG","OWNERSHIP","PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","FUNDTYPE"
    									,"VALUABLE","DOORPLATE","SIGNNO","PROOFDOC","OWNERSHIPDATE","BUILDDATE"
    									,"FLOOR","STUFF","CAREA","SAREA","AREA","HOLDNUME"
    									,"HOLDAREA","SOURCEKIND","SOURCEDATE","SOURCEDOC","SIGNNO2","PROPERTYKIND"
    									,"LIMITYEAR","ENTERDATE","PROOFNO","ORIGINALNOTE","ORIGINALBV","ENTERDATE2"
    									,"PROOFNO2","CAUSE","APPROVENO","REDUCEDEAL","DEPRMETHOD"
    									,"enterOrgOwnershipPropertyNoSerialNo","subReportDataSourceManage","subReportDataSourceBase"
    									,"subReportDataSourceProof","subReportDataSourceDepr","DIFFERENCEKIND","BUYDATE","CASESERIALNO","PRINTDEPR","OwnershipForDisplay", "NOTES", "NOTES_"};

    	String execSQL="select TOP 100 a.enterOrg as shareEnterOrg, a.ownership as shareOwnership, b.propertyNo as sharePropertyNo, b.serialNo as shareSerialNo, " +
    					" b.enterOrg, " +
    					"(select x.organaname from SYSCA_ORGAN x where b.enterorg = x.organid) AS organaName, " +
    					" b.ownership ,(select x.codeName from sysca_code x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName , " +
    					" b.propertyNo, b.serialNo, b.oldSerialNo, " +
    					" (select x.propertyName from SYSPK_PropertyCode x where b.propertyNo = x.propertyNo and x.propertyType='1' ) as propertyName, " +
    					" b.propertyName1, " +
    					" (select g.codeName from SYSCA_Code g where b.fundType=g.codeID and g.codeKindID = 'FUD') as fundType, (case b.valuable when 'Y' then '珍貴財產' when 'N' then '非珍貴財產' end) valuable," +
    					" b.doorPlate4 as doorPlate," +
    					" b.signNo," +
    					" (case b.proofDoc when null then b.proofDoc end) as proofDoc," +
    					" b.ownershipDate,b.buildDate," +
    					" (select a1.codename from SYSCA_CODE a1 where a1.codekindid = 'FLA' and b.floor1=a1.codeid) as floor1, (select a2.codename from SYSCA_CODE a2 where a2.codekindid = 'FLB' and b.floor2=a2.codeid) as floor2," +
//    					" (select x.codename from SYSCA_CODE x where x.codekindid = 'STU' and b.stuff=x.codeid) as stuff," +
    					" b.CArea, " +
    					" b.SArea, " +
    					" b.area, " +
    					" b.holdNume, b.holdDeno, " +
    					" b.holdArea," +
    					" (select r.codeName from SYSCA_Code r where  b.sourceKind=r.codeID and r.codeKindID = 'SKD') as sourceKind, " +
    					" b.sourceDate," +
    					" b.sourceDoc," +
    					" (case b.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end) propertyKind," +
    					//" (case (select x.limitYear from SYSPK_PropertyCode x where b.propertyNo = x.propertyNo and x.propertyType='1' ) when null then b.otherLimitYear else (select x.limitYear from SYSPK_PropertyCode x where b.propertyNo = x.propertyNo and x.propertyType='1' ) end) as limitYear, " +
    					// " (select x.limitYear from SYSPK_PropertyCode x where b.propertyNo = x.propertyNo and x.propertyType='1' and x.enterorg in('000000000A',b.enterorg)) as limitYear, b.otherLimitYear, " +
    					" b.originalLimitYear as limitYear," +
    					" b.otherLimitYear," +
    					" a.enterDate," +
    					" a.proofDoc as proofDoc1, a.proofNo as proofNo1,a.proofyear," +
    					" b.differencekind ,b.buydate,b.caseserialno," +
    					" (select codename from SYSCA_CODE t where codekindid='DFK' and t.codeid=b.differencekind ) as differencekindName," +
    					" b.originalNote,b.originalBV," +
    					" f.reduceDate as enterDate2," +
    					" f.proofDoc as proofDoc2, f.proofNo as proofNo2," +
    					" (select f1.codename from SYSCA_CODE f1 where f1.codekindid = 'CAA' and g.cause=f1.codeid)cause,g.cause1," +
    					" f.approveDoc as approveNo," +
    					" g.reduceDeal2," +
    					" b.DeprMethod," +
    					//TDCM問題單1797，修正折舊方法需顯示全名
    					" (select x.codename from SYSCA_CODE x where x.codekindid='DEP' and x.codeid = b.deprmethod) deprMethodName, " +
    					" b.apportionEndYM, b.deprAmount, b.monthDepr, b.accumDepr, " +
    					" b.buildDate, " +
    					" b.notes, " +
    					" c.notes as notes_ " +
    					//TDCM問題單1797，原SQL串法有問題(建物增加單串建物減損單，用電腦單號串)，
    					//所以修正串法，以建物主檔為主，建物增加單及建物減損明細串主檔，再用建物減損單串建物減損明細。
    					//並將原串SYSCA_ORGAN，改為子查詢
    					" FROM UNTBU_BUILDING b " +
    					" LEFT JOIN UNTBU_MANAGE c on  b.enterOrg=c.enterOrg and b.ownership=c.ownership and b.propertyNo=c.propertyNo and b.serialNo=c.serialNo " +
    					" LEFT JOIN UNTBU_ADDPROOF a ON a.enterorg = b.enterorg AND a.ownership = b.ownership AND a.caseno = b.caseno " +
    					" LEFT JOIN UNTBU_REDUCEDETAIL g ON b.enterorg = g.enterorg AND b.ownership = g.ownership AND b.propertyno = g.propertyno AND b.serialno = g.serialno AND b.differencekind = g.differencekind " +
    					" LEFT JOIN UNTBU_REDUCEPROOF f ON g.caseno = f.caseno AND g.enterorg = f.enterorg AND g.ownership = f.ownership  " +
						" where a.verify='Y' " +
						"";

    	if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";	
					execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
				} else {
					execSQL += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(Common.get(getQ_ownership())))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and a.caseNo <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
		if (!"".equals(getQ_enterDateS()))
		    execSQL+=" and a.enterDate >= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateS()));
		if (!"".equals(getQ_enterDateE()))
		    execSQL+=" and a.enterDate <= " + Common.sqlChar(ul._transToCE_Year(getQ_enterDateE()));
		if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and a.writeDate >= " + util.Common.sqlChar(ul._transToCE_Year(getQ_writeDateS()));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and a.writeDate <= " + util.Common.sqlChar(ul._transToCE_Year(getQ_writeDateE()));
    	if (!"".equals(getQ_proofYear()))
		    execSQL+=" and a.proofYear = " + Common.sqlChar(getQ_proofYear());	
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL += " and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
/*		if (!"".equals(getQ_verify()))
		    execSQL+=" and a.verify = " + Common.sqlChar(getQ_verify()) ; */
		if (!"".equals(getQ_propertyNo1()))
		    execSQL+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNo1());		
		if (!"".equals(getQ_propertyNo2()))
		    execSQL+=" and b.propertyNo <= " + Common.sqlChar(getQ_propertyNo2());		
		if (!"".equals(getQ_serialNoS()))
		    execSQL+=" and b.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
		    execSQL+=" and b.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
		
		if (!"".equals(getQ_signNo1()))
			q_signNo=getQ_signNo1().substring(0,1)+"______";
		if (!"".equals(getQ_signNo2()))
			q_signNo=getQ_signNo2().substring(0,3)+"____";			
		if (!"".equals(getQ_signNo3())){
			if (getQ_signNo3().length()==4){
				q_signNo="E__" + getQ_signNo3();
			}else{
				q_signNo=getQ_signNo3();
			}	
		}
		if (!"".equals(getQ_signNo4())){
			setQ_signNo4(Common.formatFrontZero(getQ_signNo4(),5));
			setQ_signNo5(Common.formatFrontZero(getQ_signNo5(),3));	
			if ("".equals(q_signNo)){
				q_signNo="_______"+getQ_signNo4()+getQ_signNo5();
			}else{
				q_signNo=q_signNo+getQ_signNo4()+getQ_signNo5();				
			}
		}		
		
		if (!"".equals(q_signNo))
			execSQL+=" and b.signNo like '" + q_signNo + "%'" ;
		
		if (!"".equals(getQ_dataState()))
		    execSQL+=" and b.dataState = " + Common.sqlChar(getQ_dataState()) ;	    
		if (!"".equals(getQ_propertyKind()))
		    execSQL+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
		    execSQL+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
		    execSQL+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_taxCredit()))
		    execSQL+=" and b.taxCredit = " + Common.sqlChar(getQ_taxCredit()) ;	    
		if (!"".equals(Common.get(getQ_proceedDateS())))
    		execSQL += " and b.proceedDate >= " + util.Common.sqlChar(getQ_proceedDateS());
    	if (!"".equals(Common.get(getQ_proceedDateE())))
    		execSQL += " and b.proceedDate <= " + util.Common.sqlChar(getQ_proceedDateE());
    	if(!"".equals(getQ_differenceKind()))
    		execSQL+=" and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
    	if(!"".equals(getQ_propertyName1()))
			execSQL+=" and b.propertyName1 like " + Common.sqlChar("%"+getQ_propertyName1()+"%") ;
		if(!"".equals(getQ_keepUnit()))
			execSQL+=" and b.keepunit = " + Common.sqlChar(getQ_keepUnit()) ;
		if(!"".equals(getQ_keeper()))
			execSQL+=" and b.keeper = " + Common.sqlChar(getQ_keeper()) ;
		if(!"".equals(getQ_useUnit()))
			execSQL+=" and b.useunit = " + Common.sqlChar(getQ_useUnit()) ;
		if(!"".equals(getQ_userNo()))
			execSQL+=" and b.userno = " + Common.sqlChar(getQ_userNo()) ;
		if(!"".equals(getQ_userNote()))
			execSQL+=" and b.usernote like " + Common.sqlChar("%"+getQ_userNote()+"%") ;
		if(!"".equals(getQ_place1()))
			execSQL+=" and b.place1 = " + Common.sqlChar(getQ_place1()) ;
		if(!"".equals(getQ_placeNote()))
			execSQL+=" and b.place like " + Common.sqlChar("%"+getQ_placeNote()+"%") ;
		execSQL+=" order by b.enterOrg, b.ownership, b.propertyNo, b.serialNo";

    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTBU013R", this.getObjPath().replaceAll("&gt;", ">"));

		ResultSet rs = db.querySQL(execSQL);
		DateTools dt = new DateTools();    	
		Vector rowData = new Vector();
        String CauseTemp="";
        //int i;
        while(rs.next()){
        	
        	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
        	obj.setShareOwnership(rs.getString("shareOwnership"));
        	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
        	obj.setShareSerialNo(rs.getString("shareSerialNo"));
        	obj.setApportionEndYM(rs.getString("apportionEndYM"));
        	obj.setDeprAmount(rs.getString("deprAmount"));
        	obj.setMonthDepr(rs.getString("monthDepr"));
        	//obj.setAccumDeprYM(rs.getString("accumDeprYM"));
        	obj.setAccumDepr(rs.getString("accumDepr"));
        	obj.setDeprMethod(rs.getString("deprMethod"));
        	obj.setShareDifferenceKind(rs.getString("differencekind"));
        	//obj.setCheckMonth(dt._calBetweenDays_asDay("yyy.MM.dd", rs.getString("buildDate"),rs.getString("accumDeprYM")));
        	
        	Object[] data = new Object[columns.length];
            data[0] = rs.getString("organaName");
            data[1] = rs.getString("ownershipName");
            data[2] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
            data[3] = rs.getString("propertyName");
            data[4] = rs.getString("propertyName1");
           	data[5] = rs.getString("fundType");
            data[6] = rs.getString("valuable");
            data[7] = rs.getString("doorplate");
            if(!"".equals(checkGet(rs.getString("signNo"))) && rs.getString("signNo").length() == 15){
            	data[8] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,12) + '-' + rs.getString("signNo").substring(12) + "建號";}
            else if(!"".equals(checkGet(rs.getString("signNo"))) && rs.getString("signNo").length() == 7){
            	data[8] = getSignDescName(rs.getString("signNo").substring(0,7));
            }
            else {
            	data[8] ="";
            }
            data[9] = rs.getString("proofDoc");
            
            if(rs.getString("ownershipDate") != null){
                data[10] = ul._transToROC_Year(rs.getString("ownershipDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("ownershipDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("ownershipDate")).substring(5,7);
            }else{
                data[10] = rs.getString("ownershipDate");
            }
            if(rs.getString("buildDate")!=null){
                data[11] = ul._transToROC_Year(rs.getString("buildDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("buildDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("buildDate")).substring(5,7);
            }else{
                data[11] = rs.getString("buildDate");
            }
            if(!"".equals(checkGet(rs.getString("floor1"))) && !"".equals(checkGet(rs.getString("floor2")))){
            	  data[12] = checkGet(rs.getString("floor1")) + "，" + checkGet(rs.getString("floor2"));
            }else  if(!"".equals(checkGet(rs.getString("floor1")))){
            	  data[12] = checkGet(rs.getString("floor1")) ;
            }else  if(!"".equals(checkGet(rs.getString("floor2")))){
          	  	  data[12] = checkGet(rs.getString("floor2")) ;
            }else{
            	data[12]="";
            }
            
            data[13] = ul._getMaterial(rs.getString("propertyNo"));  //stuff
            data[14] = Common.areaFormat(Common.get(rs.getString("CArea")))+"　";
            data[15] = Common.areaFormat(Common.get(rs.getString("SArea")))+"　";
            data[16] = Common.areaFormat(Common.get(rs.getString("area")))+"　";
            data[17] = rs.getString("holdNume") + "/" + rs.getString("holdDeno");
            data[18] = Common.areaFormat(Common.get(rs.getString("holdArea")))+"　";
            data[19] = rs.getString("sourceKind");
            if(rs.getString("sourceDate")!=null){
                data[20] = ul._transToROC_Year(rs.getString("sourceDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("sourceDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("sourceDate")).substring(5,7);
            }else{
                data[20] = rs.getString("sourceDate");
            }
            data[21] = rs.getString("sourceDoc");
            
            //String sqlsignNo2 ="select (substr(''+b1.signno,8,5) + '-' +substr(''+b1.signno,13,3)) as signNo2 from UNTBU_CommonUse b1 where " +
            String sqlsignNo2 ="select (substring(b1.signno,8,5)+'-'+substring(b1.signno,13,3)) as signNo2 from UNTBU_CommonUse b1 where " +
    		"b1.enterOrg=" + Common.sqlChar(rs.getString("shareEnterOrg"))+
    		"and b1.ownership=" +Common.sqlChar(rs.getString("shareOwnership"))+
    		"and b1.propertyNo=" +Common.sqlChar(rs.getString("sharePropertyNo"))+
    		"and b1.serialNo= "+ Common.sqlChar(rs.getString("shareSerialNo"));
            //System.out.println(sqlsignNo2);
            ResultSet rs2 = db.querySQL(sqlsignNo2);
            String temp="";
            
            while (rs2.next()){
    	       temp =temp + rs2.getString("signNo2")+ " ";
            }
            
           
            data[22] = temp;
                        
            //data[22] = rs.getString("signNo2");
            data[23] = rs.getString("propertyKind");            
            data[24] = "".equals(Common.get(rs.getString("limitYear")))?rs.getString("otherLimitYear"):rs.getString("limitYear");
//            System.out.println(rs.getString("enterDate"));
            if(rs.getString("enterDate") != null && rs.getString("enterDate").length() == 8){
                data[25] = ul._transToROC_Year(rs.getString("enterDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(5,7);
            }else{
                data[25] = rs.getString("enterDate");
            }
            if(rs.getString("proofNo1") != null){
            	String proofDoc1 = rs.getString("proofDoc1") == null ? "第" :  rs.getString("proofDoc1") + "字第" ;
            	data[26] = Common.get(rs.getString("proofyear")) + "年" + proofDoc1 + rs.getString("proofNo1") + "號";
            }else{ 
            	data[26]="";
            }
            data[27] = rs.getString("originalNote");
            data[28] = Common.valueFormat(rs.getString("originalBV"))+"　";
            if(rs.getString("enterDate2")!= null){
                data[29] = ul._transToROC_Year(rs.getString("enterDate2")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate2")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate2")).substring(5,7);
            }else{
                data[29] = rs.getString("enterDate2");
            }
            if(rs.getString("proofNo2") != null)
            	data[30] = checkGet(rs.getString("proofDoc2")) + "字第" + checkGet(rs.getString("proofNo2")) + "號";
            else data[30]="";
            CauseTemp = rs.getString("cause");
            if(CauseTemp==null || CauseTemp.equals("其他"))data[31]=rs.getString("cause1");
            else data[31]=CauseTemp;
            data[32] = rs.getString("approveNo");
            data[33] = rs.getString("reduceDeal2");
            data[34] = rs.getString("DeprMethodName");
            data[35] = rs.getString("shareEnterOrg")+rs.getString("shareOwnership")+rs.getString("sharePropertyNo")+rs.getString("shareSerialNo")+rs.getString("differencekind");
            data[36] = new JRTableModelDataSource(getSubModel((String)data[0]));
            data[37] = new JRTableModelDataSource(getSubMode2((String)data[0]));
            data[38] = new JRTableModelDataSource(getSubMode3((String)data[0]));
           
            if(q_printDepr.equals("Y")){
            data[39] = new JRTableModelDataSource(getSubMode4((String)data[0]));
            data[43] = "Y";
            }
            else {
            data[43] = "N";	
            }
            data[40] = rs.getString("differencekind")+" "+rs.getString("differencekindName");
           	data[41] = ul._transToROC_Year(rs.getString("buydate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("buydate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("buydate")).substring(5,7);;
           	data[42] = rs.getString("caseserialno");
            //for(i=0;i<37;i++)if(data[i]==null)data[i]="";
           	//TDCM問題單1797，權屬非國有時，欄位不顯示"國有"字樣
           	data[44] = rs.getString("shareOwnership");
           	data[45] = rs.getString("notes");
           	data[46] = rs.getString("notes_");
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

//管理資料－多筆
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTBU013R obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "SERIALNO1", "USEUNIT","USERELATION","USEDATE","USEAREA","NOTES","NOTES1"};
    	String execSQL="select a.serialNo1, (case c.organAName when null then a.useUnit1 else c.organAName end) as useUnit," +
    					" (select h.codeName from SYSCA_Code h where a.useRelation=h.codeId and h.codeKindId='URE')as useRelation," +
    					" a.useDateS, a.useDateE," +
    					" (case a.useArea when '' then ' ' else a.useArea end) as useArea, " +
    					" a.notes , " +
    					" a.notes1 " +
    					" from UNTBU_Building b left join UNTBU_Manage a on  b.enterOrg=a.enterOrg and b.ownership=a.ownership and b.propertyNo=a.propertyNo and b.serialNo=a.serialNo left join SYSCA_ORGAN c on a.useUnit=c.organId" +
    					" where 1=1" +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
    					" and b.differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind());
    	execSQL += " order by a.serialNo1 ";

    	ResultSet rs = db.querySQL(execSQL);
    	
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[7];
    		data[0] = checkGet(rs.getString("serialNo1"));
    		data[1] = checkGet(rs.getString("useUnit"));
    		data[2] = checkGet(rs.getString("useRelation"));
    		data[3] = ul._transToROC_Year(rs.getString("useDateS")) + "-" + ul._transToROC_Year(rs.getString("useDateE"));
    		data[4] = Common.areaFormat(checkGet(rs.getString("useArea")))+"　";
    		data[5] = checkGet(rs.getString("notes"));
    		data[6] = checkGet(rs.getString("notes1"));
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

//基地資料－多筆
public DefaultTableModel getSubMode2(String caseCode) throws Exception{
	UNTBU013R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "SERIALNO1", "SIGNNO","OWNERSHIP1","OWNER","AREA","HOLDNUME","HOLDAREA","NOTES1"};
    	String execSQL="select  a.serialNo1,a.signNo," +
    					" (select h.codeName from SYSCA_Code h where a.ownership1=h.codeId and h.codeKindId='OWN')as ownership1, " +
						" a.owner,(select c.organAName from SYSCA_ORGAN c where a.manageOrg=c.organId)manageOrg," +
						" (case a.area when '' then ' ' else a.area end) as  area, " +
						" a.holdNume, a.holdDeno," +
						" (case a.holdArea when '' then ' ' else a.holdArea end) as holdArea, " +
						" a.notes1 " +
    					" from UNTBU_Base a, UNTBU_Building b " +
    					" where 1=1 "+
						" and a.enterOrg=b.enterOrg and a.ownerShip=b.ownerShip and b.differencekind=a.differencekind and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo " +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
						" order by a.serialNo1 ";
    	
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = checkGet(rs.getString("serialNo1"));
    		if(!"".equals(checkGet(rs.getString("signNo")))){
    			if(rs.getString("signNo").length()==7){
    		data[1] = getSignDescName(rs.getString("signNo").substring(0,7));
    			}
    			else if(rs.getString("signNo").length()==15){
    				data[1] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11) + "地號";
    			}
    			else{
    				data[1] ="";
    			}
    		}else{
    		data[1] = "";	
    		}
    		data[2] = checkGet(rs.getString("ownership1"));
    		if(rs.getString("manageOrg")!=null&&rs.getString("owner")!=null){
    			data[3] = rs.getString("owner")+"("+rs.getString("manageOrg")+")";
    		}
    		else if(rs.getString("owner")!=null)
    			data[3] = rs.getString("owner");
    		else if(rs.getString("manageOrg")!=null)
    			data[3] = "("+rs.getString("manageOrg")+")";
    		data[4] = Common.areaFormat(checkGet(rs.getString("Area")))+"　";
    		data[5] = checkGet(rs.getString("holdNume")) + "/" + checkGet(rs.getString("holdDeno"));    		
    		data[6] = Common.areaFormat(checkGet(rs.getString("holdArea")))+"　";
    		data[7] = checkGet(rs.getString("notes1"));
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){ //若無資料也要顯示報表欄位
    		Object[] data = new Object[columns.length];
    		data[0] = "";
    		data[1] = "";
    		data[2] = "";
    		data[3] = "";
    		data[4] = "";
    		data[5] = "";
    		data[6] = "";
    		data[7] = "";
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

//帳務資料－多筆
public DefaultTableModel getSubMode3(String caseCode) throws Exception{
	UNTBU013R obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	 String[] columns = new String[] { "ENTERDATE", "NAME","PROOFNO","BOOKNOTES","OLDBOOKVALUE","ADJUSTBOOKVALUE1","ADJUSTBOOKVALUE2","NEWBOOKVALUE","ADJUSTAREA1","ADJUSTAREA2","NEWHOLDAREA","CASESERIALNO"};
     	//
         String execSQL="(select '1' orderBy, a.caseNo, a.adjustDate as enterDate, " +
         				"'財產增減值' as Name," +
         				" a.proofDoc, a.proofNo,a.proofyear," +
         				" b.bookNotes," +
         				" b.oldBookValue," +
         				" b.addbookvalue,b.reducebookvalue," +
         				" b.newBookValue," +
         				" b.adjustHoldArea  as adjustArea," +
         				" b.newHoldArea,  " +
         				" b.caseSerialNo" +
 						" from UNTBU_AdjustProof a, UNTBU_AdjustDetail b "+
 						" where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
 						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
 						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
 						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
 						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
 						" and b.differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
 						" and b.verify = 'Y' " +
 						" ) " +
     					"union " +
 						"(select '2' orderBy, a.caseNo, a.reduceDate as enterDate," +
 						"'財產減損單' as Name," +
 						" a.proofDoc, a.proofNo,a.proofyear," +
         				" b.bookNotes," +
         				" b.bookValue as oldBookValue," +
         				" 0 as addbookvalue," +
         				" b.bookValue as reducebookvalue," +
         				" 0 as newBookValue," +
         				" b.holdArea as adjustArea," +
         				" 0 as newHoldArea, " +
         				" b.caseSerialNo"+
         				" from UNTBU_ReduceProof a, UNTBU_ReduceDetail b "+
 						" where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
 						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
 						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
 						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
 						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
 						" and b.differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
 						" and b.verify = 'Y' " +
 						")" +
 						" order by enterDate, caseNo, orderBy ";
 //System.out.println("-- untbu013r getSubMode3 --\n"+execSQL);
     	ResultSet rs = db.querySQL(execSQL);
     	Vector rowData = new Vector();
     	//int i;
     	while(rs.next()){
     		Object[] data = new Object[12];
     		if(rs.getString("enterDate")!=null)
     			 data[0] = ul._transToROC_Year(rs.getString("enterDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(5,7);
     		else data[0]="";
     		data[1] = checkGet(rs.getString("Name"));
     		if(!"".equals(Common.get(rs.getString("proofNo"))) && !"0000000".equals(Common.get(rs.getString("proofNo")))){
            	String proofDoc1 = rs.getString("proofDoc") == null ? "字第" :  rs.getString("proofDoc") + "字第" ;
            	data[2] = Common.get(rs.getString("proofyear")) + "年" + proofDoc1 + Common.get(rs.getString("proofNo")) + "號";
            }else{ 
            	data[2]="";
            }
     		data[3] = checkGet(rs.getString("bookNotes"));
     		data[4] = Common.valueFormat(rs.getString("oldBookValue"))+"　";
     		if("1".equals(rs.getString("orderBy")))
     		{
     			
     			data[5]= Common.valueFormat(rs.getString("addbookvalue"))+"　";
     			data[6]= Common.valueFormat(rs.getString("reducebookvalue"))+"　";
     			if(Double.parseDouble(rs.getString("adjustArea"))>0){
     			data[8]= Common.areaFormat(rs.getString("adjustArea"))+"　";
     			data[9]="";
     			}
     			else if (Double.parseDouble(rs.getString("adjustArea"))<0){
     			data[8]= "";
         		data[9]=Common.areaFormat(rs.getString("adjustArea"))+"　";	
     			}
     			else{
     			data[8]= "0";
     			data[9]= "0";}
     		}
     		//減損
     		else if("2".equals(rs.getString("orderBy"))){
     			data[5]="";
     			data[6]= Common.valueFormat(rs.getString("reducebookvalue"))+"　";
     			data[8]="";
     			data[9]= Common.areaFormat(rs.getString("adjustArea"))+"　";
     		}
     		data[7]=Common.valueFormat(rs.getString("newBookValue"))+"　";
     		data[10]=Common.areaFormat(rs.getString("newHoldArea"))+"　";
     		data[11]=rs.getString("caseSerialNo");
     		rowData.addElement(data);
     	}
    	
    	if(rowData.size()==0){    //無資料也要顯示欄位
		Object[] data = new Object[columns.length];
		data[0]="";
		data[1]="";
		data[2]="";
		data[3]="";
		data[4]="";
		data[5]="";
		data[6]="";
		data[7]="";
		data[8]="";
		data[9]="";
		data[10]="";
		data[11]="";
		
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

//折舊紀錄－多筆
public DefaultTableModel getSubMode4(String caseCode) throws Exception{
	UNTBU013R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Vector rowData = new Vector();
	//int i;
	try{
        String[] columns = new String[] { "DEPRYM", "OLDNETVALUE","MONTHDEPR","NEWACCUMDEPR","NEWNETVALUE"};
    	String execSQL="select distinct um.deprYM, um.oldNetValue,um.monthDepr,um.newAccumDepr,um.newNetValue,um.notes, " +
		    			"case when exists(select 1 from UNTBU_ADJUSTDETAIL ua where um.enterorg = ua.enterorg " +
						" and um.ownership = ua.ownership and um.differencekind = ua.differencekind and um.propertyno = ua.propertyno"+
						" and um.serialno = ua.serialno and ua.cause = '98' and substring(ua.adjustdate,1,6)=um.deprym)" +
						" then 'Y' else 'N' end as depradjust" + 
						" from UNTDP_MONTHDEPR um"+
						" where 1=1 and verify='Y' " +
						" and enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind())+
						" and propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and differenceKind=" + util.Common.sqlChar(obj.getShareDifferenceKind());
    		   execSQL+=" order by deprYM ";
    	
    	ResultSet rs = db.querySQL(execSQL);
    	
    
    	
    	    while(rs.next()){
    	        Object[] data = new Object[5];
    	        data[0] = new Datetime().changeTaiwanYYMM(Common.get(rs.getString("deprYM")),"1");
    	        data[1] = Common.valueFormat(rs.getString("oldNetValue"));
    	        //問題單1560 折舊月檔有備註時加星號、有增減值原因為折舊提列時加星號
    	        if("".equals(Common.get(rs.getString("notes"))) && "N".equals(Common.get(rs.getString("depradjust")))) {
    	        	data[2] = Common.valueFormat(rs.getString("monthDepr"));
    	        }else {
    	        	data[2] = "*"+Common.valueFormat(rs.getString("monthDepr"));
    	        }
    	        data[3] = Common.valueFormat(rs.getString("newAccumDepr"));
    	        data[4] = Common.valueFormat(rs.getString("newNetValue"));
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
//年度格式
public static String yearFormat(String year){
    String formatString = new String();
    DecimalFormat df = new DecimalFormat("000");
    if(year!=null && !year.equals("")){
        try{
            formatString = df.format(Double.parseDouble(year));
        }catch (NumberFormatException e) {
            formatString =year;
     }
    }else{
        formatString =year;
    }
    return formatString;
}  


private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}

}