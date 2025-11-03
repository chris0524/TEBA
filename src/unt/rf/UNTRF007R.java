/*
*<br>程式目的：土地改良物財產卡報表檔 
*<br>程式代號：untrf007r
*<br>撰寫日期：94/12/19
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.text.DecimalFormat;
//import java.text.DecimalFormat;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;


import unt.ch.UNTCH_Tools;
import unt.rf.UNTRF007R;
import util.*;

public class UNTRF007R extends UNTRF001F_Q{

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
	String q_dataState;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
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
	UNTRF007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    UNTCH_Tools ul = new UNTCH_Tools();
    Map KeepUnitMap = TCGHCommon.getUntmp_KeepUnitCode(getQ_enterOrg()); //保管單位、使用單位
    try{
    	String[] columns = new String[] {"ENTERORG","OWNERSHIP","PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","FUNDTYPE","VALUABLE",
    			"PROPERTYUNIT","MEASURE","BOOKVALUE","MATERIAL","USEUNIT","SOURCEKIND","BUILDDATE","PROPERTYKIND","LIMITYEAR","ENTERDATE",
    			"PROOFNO","ORIGINALNOTE","ORIGINALBV","ENTERDATE2","PROOFNO2","CAUSE","APPROVENO","REDUCEDEAL","DEPRMETHOD",
    			"enterOrgOwnershipPropertyNoSerialNo",
    			"subReportDataSourceManager","subReportDataSourceBase","subReportDataSourceProof","subReportDataSourceDepr","OLDPROPERTYNO","COUNT",
    			"DIFFERENCEKIND","BUYDATE","CASESERIALNO","SOURCEDATE","PRINTDEPR"};

    	String execSQL="select TOP 100 b.enterOrg as shareEnterOrg, b.ownership as shareOwnership, b.propertyNo as sharePropertyNo, b.serialNo as shareSerialNo, " +
    					" c.organaName as enterOrg, (select x.codeName from SYSCA_CODE x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownership, " +
    					" substring(b.propertyNo,0,8)+'-'+substring(b.propertyNo,8,4)+'-'+b.serialNo as propertyNo, b.oldSerialNo,b.oldPropertyNo, " +
    					" d.propertyName, b.propertyName1, " +
    					" (select g.codeName from SYSCA_CODE g where b.fundType=g.codeID and g.codeKindID = 'FUD') as fundType, (case b.valuable when 'Y' then '珍貴財產'  else '非珍貴財產' end) valuable," +
//    					" d.propertyUnit," +
    					" b.otherpropertyUnit," +
    					" b.measure," +
    					" b.bookvalue," +
//    					" d.material," +
    					" b.othermaterial," +
    					" ISNULL((select g1.organaName from SYSCA_ORGAN g1 where b.useUnit = g1.organID),b.useUnit) as useUnit," +
    					" (select g2.codename from SYSCA_CODE g2 where g2.codekindid = 'SKD' and b.sourceKind=g2.codeid) as sourceKind," +
    					" b.buildDate," +
    					" (select x.codeName from SYSCA_CODE x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKind," +
    					" b.otherLimitYear," +
    					" a.enterDate," +
    					"  a.proofDoc as proofDoc1, a.proofNo as proofNo1,a.proofyear," +
//    					" a.proofYear+'年'+a.proofDoc+'字第'+a.proofNo+'號' as proofNo," +
    					" b.originalNote,b.originalBV," +
    					"  f.reduceDate as enterDate2," +
    					" f.proofDoc as proofDoc2, f.proofNo as proofNo2," +
//    					" f.proofyear+'年'+f.proofDoc+'字第'+f.proofNo+'號' as proofNo2," +
    					"  (select f1.codename from SYSCA_CODE f1 where f1.codekindid = 'CAC' and g.cause=f1.codeid)cause,g.cause1," +
    					"  f.approveDoc as approveNo," +
    					"  g.reduceDeal2," +
    					//TDCM問題單1797，修正折舊方法需顯示全名
    					"  (select x.codename from SYSCA_CODE x where x.codekindid='DEP' and x.codeid = b.deprmethod) DeprMethod," +
    					"  b.scrapValue, " +
    					"  b.differencekind ,b.buydate,b.caseserialno,b.sourceDate," +
    					" (select codename from SYSCA_CODE t where codekindid='DFK' and t.codeid=b.differencekind ) as differencekindName," +
    					" (case b.apportionEndYM when '' then '00000' else b.apportionEndYM end) apportionEndYM, b.deprAmount, b.monthDepr, b.accumDepr " +
    					" from UNTRF_ADDPROOF a left join UNTRF_REDUCEPROOF f on a.caseNo=f.caseNo and a.enterOrg=f.enterOrg and a.ownership=f.ownership"+
    					" left join UNTRF_REDUCEDETAIL g on a.caseNo=g.caseNo and a.enterOrg=g.enterOrg and a.ownership=g.ownership"+
    					" ,UNTRF_ATTACHMENT b, SYSCA_ORGAN c, SYSPK_PROPERTYCODE d "+
						" where a.enterOrg = c.organID " +
						" and b.propertyNo = d.propertyNo and d.propertyType='1' "+
						" and a.caseNo=b.caseNo and a.enterOrg=b.enterOrg and a.ownership=b.ownership " +
						""; 
    	//System.out.println("getResultModel" +execSQL);
    	if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
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
			execSQL += " and a.proofYear = " + Common.sqlChar(getQ_proofYear());	
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
		    execSQL+=" and b.serialNo <=" + Common.sqlChar(getQ_serialNoE());			
		
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
		if(!"".equals(getQ_differenceKind()))
    		execSQL+=" and b.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		if (!"".equals(Common.get(getQ_propertyName1())))
    		execSQL += " and b.propertyname1 like " + util.Common.sqlChar("%"+getQ_propertyName1()+"%");
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
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTRF007R", this.getObjPath().replaceAll("&gt;", ">"));
		
		ResultSet rs = db.querySQL(execSQL);
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
        	//obj.setCheckMonth(rs.getString("checkMonth"));
        	obj.setDifferenceKind(rs.getString("differencekind"));
        	
        	Object[] data = new Object[columns.length];
            data[0] = rs.getString("enterOrg");
            data[1] = rs.getString("ownership");
            data[2] = rs.getString("propertyNo");
            data[3] = rs.getString("propertyName");
            data[4] = rs.getString("propertyName1");
           	data[5] = rs.getString("fundType");
            data[6] = rs.getString("valuable");
            data[7] = rs.getString("otherpropertyUnit");
            data[8] = Common.areaFormat(rs.getString("measure"));
            data[9] = Common.valueFormat(rs.getString("bookvalue"));
            data[10] = rs.getString("othermaterial");
            data[11] = KeepUnitMap.get(rs.getString("useUnit")) ; //使用單位名稱
            data[12] = rs.getString("sourceKind");
            
            if(rs.getString("buildDate")!=null && rs.getString("buildDate").length() > 7){
                data[13] = ul._transToROC_Year(rs.getString("buildDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("buildDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("buildDate")).substring(5,7);
            }else{
                data[13] = rs.getString("buildDate");
            }
            
            data[14] = rs.getString("propertyKind");
            
            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
			int year = otherLimitMonth /12;
			int month = otherLimitMonth % 12;
			data[15] = year +"年"+month+"個月";
            
            if(rs.getString("enterDate")!=null){
                data[16] = ul._transToROC_Year(rs.getString("enterDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(5,7);
            }else{
                data[16] = rs.getString("enterDate");
            }
            
            if(rs.getString("proofNo1") != null){
            	String proofDoc1 = rs.getString("proofDoc1") == null ? "第" :  rs.getString("proofDoc1") + "字第" ;
            	data[17] = (rs.getString("proofyear") == null ? "" :  rs.getString("proofyear") + "年") + proofDoc1 + rs.getString("proofNo1") + "號";
            }else{ 
            	data[17]="";
            }
//            if(!"字第號".equals(rs.getString("proofNo")))
//            	data[17] = rs.getString("proofNo");
//            else data[17]=null;
//            
            data[18] = rs.getString("originalNote");
            data[19] = Common.valueFormat(rs.getString("originalBV"))+"　";
            if(rs.getString("enterDate2")!=null){
                data[20] = ul._transToROC_Year(rs.getString("enterDate2")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate2")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate2")).substring(5,7);
            }else{
                data[20] = rs.getString("enterDate2");
            }
            
            if(rs.getString("proofNo2") != null)
            	data[21] = checkGet(rs.getString("proofDoc2")) + "字第" + checkGet(rs.getString("proofNo2")) + "號";
            else data[21]="";
//            if(!"字第號".equals(rs.getString("proofNo2")))
//            	data[21] = rs.getString("proofNo2");
//            else data[21]=null;
            
            CauseTemp = rs.getString("cause");
            if(CauseTemp==null || CauseTemp.equals("其他"))data[31]=rs.getString("cause1");
            else data[22]=CauseTemp;
            
            data[23] = rs.getString("approveNo");
            data[24] = rs.getString("reduceDeal2");
            data[25] = rs.getString("DeprMethod");
            
            data[26] = rs.getString("shareEnterOrg")+rs.getString("shareOwnership")+rs.getString("sharePropertyNo")+rs.getString("shareSerialNo");
            data[27] = new JRTableModelDataSource(getSubMode1((String)data[0]));
            data[28] = new JRTableModelDataSource(getSubMode2((String)data[0]));
            data[29] = new JRTableModelDataSource(getSubMode3((String)data[0]));
            if(q_printDepr.equals("Y")){
            data[30] = new JRTableModelDataSource(getSubMode4((String)data[0]));
            data[37] = "Y";	
            }
            else {
            data[37] = "N";	
            }
  //          data[31] = rs.getString("oldPropertyNo")+"-"+rs.getString("oldSerialNo");
            data[32] = "1";
            data[33] = rs.getString("differencekind")+" "+rs.getString("differencekindName");
        	if(!checkGet(rs.getString("buydate")).equals("")){
           	data[34] = ul._transToROC_Year(rs.getString("buydate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("buydate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("buydate")).substring(5,7);}
        	else{
            data[34]="";	
            }
           	data[35] = rs.getString("caseserialno");
           	if(!checkGet(rs.getString("sourceDate")).equals("")){
           	data[36] = ul._transToROC_Year(rs.getString("sourceDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("sourceDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("sourceDate")).substring(5,7);}
           	else{
           	data[36]="";	
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

//管理資料－多筆
public DefaultTableModel getSubMode1(String caseCode) throws Exception{
	UNTRF007R obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "SERIALNO1", "USEUNIT","USERELATION","USEDATE","MEASURE","NOTES1"};
    	String execSQL="select a.serialNo1, ISNULL(c.organAName,a.useUnit1) as useUnit," +
    					" (select h.codeName from SYSCA_Code h where a.useRelation=h.codeId and h.codeKindId='URE')as useRelation," +
    					" a.useDateS+'-'+a.useDateE as useDateE," +
    					" a.measure ," +
    					" a.notes1 " +
    					" from UNTRF_Manage a left join UNTRF_Attachment b on b.enterOrg=a.enterOrg and b.ownership=a.ownership and b.propertyNo=a.propertyNo and b.serialNo=a.serialNo"+
    					" left join SYSCA_ORGAN c on a.useUnit=c.organId" +
    					" where 1=1" +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.differenceKind=" + util.Common.sqlChar(obj.getDifferenceKind())+
    					" ";
    	execSQL += " order by a.serialNo1 ";
    	//System.out.println("= getSubMode1 = " +execSQL);
    	ResultSet rs = db.querySQL(execSQL);    	
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[6];
    		data[0] = rs.getString("serialNo1");
    		data[1] = rs.getString("useUnit");
    		data[2] = rs.getString("useRelation");
    		data[3] = ul._transToROC_Year(rs.getString("useDateE"));
    		data[4] = rs.getString("measure");
    		data[5] = rs.getString("notes1");
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
	UNTRF007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "SERIALNO1", "SIGNNO","OWNER","AREA"};
    	String execSQL="select  a.serialNo1," +
    					" (select e.signDesc+substring(a.signno,8,4)+'-'+substring(a.signno,12,4)+'地號' ) as signNo," +
						" a.owner,(select c.organAName from SYSCA_ORGAN c where a.manageOrg=c.organId)manageOrg," +
						" a.area " +
						" from UNTRF_Base a left join SYSCA_SIGN e on substring(a.signNo,1,7) = e.signNo, UNTRF_Attachment b " +
    					" where 1=1 "+
						" and a.enterOrg=b.enterOrg and a.ownerShip=b.ownerShip and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo " +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" order by a.serialNo1 ";
    	
    	System.out.println(execSQL);
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = rs.getString("serialNo1");
    		data[1] = rs.getString("signNo");
    		if(rs.getString("manageOrg")!=null){
    			data[2] = rs.getString("owner")+"("+rs.getString("manageOrg")+")";
    		}
    		else data[2] = rs.getString("owner");
    		data[3] = Common.areaFormat(rs.getString("Area"))+"　";
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){ //若無資料也要顯示報表欄位
    		Object[] data = new Object[columns.length];
    		data[0] = "";
    		data[1] = "";
    		data[2] = "";
    		data[3] = "";
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
	UNTRF007R obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "NAME","PROOFNO","BOOKNOTES","OLDBOOKVALUE","ADJUSTBOOKVALUE1","ADJUSTBOOKVALUE2","NEWBOOKVALUE","CASESERIALNO"};
    	//
        String execSQL="(select '1' orderBy,a.adjustDate as enterDate, " +
        				"'財產增減值' as Name," +
        				" a.proofDoc as proofDoc1, a.proofNo as proofNo1,a.proofyear," +
//        				" a.proofyear+'年'+a.proofDoc+'字第'+a.proofNo+'號' as proofNo," +
        				" b.bookNotes," +
        				" b.oldBookValue," +
        				" b.addbookvalue,b.reducebookvalue," +
        				" b.newBookValue, " +
        				" b.caseSerialNo" +
        				" from UNTRF_AdjustProof a, UNTRF_AdjustDetail b "+
						" where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.verify = 'Y' " +
						" ) " +
    					"union " +
						"(select '2' orderBy,a.reduceDate as enterDate," +
						"'財產減損單' as Name," +
						" a.proofDoc as proofDoc1, a.proofNo as proofNo1,a.proofyear," +
//						" a.proofYear+'年'+a.proofDoc+'字第'+a.proofNo+'號' as proofNo," +
        				" b.bookNotes," +
        				" b.bookValue as oldBookValue," +
        				" 0 as addbookvalue," +
         				" b.bookValue as reducebookvalue," +
        				" 0 as newBookValue, " +
        				" b.caseSerialNo" +
        				" from UNTRF_ReduceProof a, UNTRF_ReduceDetail b "+
						" where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.verify = 'Y' " +
						")";
 	   
        ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		if(rs.getString("enterDate")!=null)
    			data[0] = ul._transToROC_Year(rs.getString("enterDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(5,7);
    		else data[0]="";
    		data[1] = rs.getString("Name");
            if(rs.getString("proofNo1") != null){
            	String proofDoc1 = rs.getString("proofDoc1") == null ? "第" :  rs.getString("proofDoc1") + "字第" ;
            	data[2] = rs.getString("proofyear") + "年" + proofDoc1 + rs.getString("proofNo1") + "號";
            }else{ 
            	data[2]="";
            }
//    		data[2] = rs.getString("proofNo");
    		data[3] = rs.getString("bookNotes");
    		data[4] = Common.valueFormat(rs.getString("oldBookValue"))+"　";
    		if("1".equals(rs.getString("orderBy")))
     		{
     			
     			data[5]= Common.valueFormat(rs.getString("addbookvalue"))+"　";
     			data[6]= Common.valueFormat(rs.getString("reducebookvalue"))+"　";
     			
     		}
     		//減損
     		else if("2".equals(rs.getString("orderBy"))){
     			data[5]=null;
     			data[6]= Common.valueFormat(rs.getString("reducebookvalue"))+"　";
     			
     		}
    		
    		
    		data[7]=Common.valueFormat(rs.getString("newBookValue"))+"　";
    		data[8]=rs.getString("caseSerialNo");
    		
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
    		data[8] = "";
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
	UNTRF007R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Vector rowData = new Vector();
	//int i;
	try{
		String[] columns = new String[] { "DEPRYM", "OLDNETVALUE","MONTHDEPR","NEWACCUMDEPR","NEWNETVALUE"};
    	String execSQL="select distinct um.deprYM, um.oldNetValue,um.monthDepr,um.newAccumDepr,um.newNetValue,um.notes, " +
		    			"case when exists(select 1 from UNTRF_ADJUSTDETAIL ua where um.enterorg = ua.enterorg " +
						" and um.ownership = ua.ownership and um.differencekind = ua.differencekind and um.propertyno = ua.propertyno"+
						" and um.serialno = ua.serialno and ua.cause = '98' and substring(ua.adjustdate,1,6)=um.deprym)" +
						" then 'Y' else 'N' end as depradjust" + 
						" from UNTDP_MONTHDEPR um" +
						" where 1=1 and verify='Y' " +
						" and enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and differenceKind=" + util.Common.sqlChar(obj.getDifferenceKind())+
						" and propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and serialNo=" + util.Common.sqlChar(obj.getShareSerialNo());
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

}