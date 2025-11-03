/*
*<br>程式目的：土地財產卡報表檔 
*<br>程式代號：untla011r
*<br>撰寫日期：94/12/05
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
//import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import unt.ch.UNTCH_Tools;
import util.*;

public class UNTLA011R extends UNTLA001Q{

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
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
	String originalUnit;
	String SignNoA;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getSharePropertyNo(){ return checkGet(sharePropertyNo); }
	public void setSharePropertyNo(String s){ sharePropertyNo=checkSet(s); }
	public String getShareSerialNo(){ return checkGet(shareSerialNo); }
	public void setShareSerialNo(String s){ shareSerialNo=checkSet(s); }
	public String getQ_propertyNo1(){ return checkGet(q_propertyNo1); }
	public void setQ_propertyNo1(String s){ q_propertyNo1=checkSet(s); }
	public String getQ_propertyNo1Name(){ return checkGet(q_propertyNo1Name); }
	public void setQ_propertyNo1Name(String s){ q_propertyNo1Name=checkSet(s); }
	public String getQ_propertyNo2(){ return checkGet(q_propertyNo2); }
	public void setQ_propertyNo2(String s){ q_propertyNo2=checkSet(s); }
	public String getQ_propertyNo2Name(){ return checkGet(q_propertyNo2Name); }
	public void setQ_propertyNo2Name(String s){ q_propertyNo2Name=checkSet(s); }
    public String getOriginalUnit() { return checkGet(originalUnit); }
    public void setOriginalUnit(String s) { originalUnit = checkSet(s); } 
    public String getQ_closing() { return checkGet(q_closing); }
    public void setQ_closing(String s) {  q_closing= checkSet(s); }
    public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String qEnterOrg) {q_enterOrg =  checkSet(qEnterOrg);}
	public String getQ_ownership() {return checkGet(q_ownership);}
	public void setQ_ownership(String qOwnership) {q_ownership =  checkSet(qOwnership);}
	public String getQ_caseNoS() {return checkGet(q_caseNoS);}
	public void setQ_caseNoS(String qCaseNoS) {q_caseNoS =  checkSet(qCaseNoS);}
	public String getQ_caseNoE() {return checkGet(q_caseNoE);}
	public void setQ_caseNoE(String qCaseNoE) {q_caseNoE =  checkSet(qCaseNoE);}
	public String getQ_enterDateS() {return checkGet(q_enterDateS);}
	public void setQ_enterDateS(String qEnterDateS) {q_enterDateS =  checkSet(qEnterDateS);}
	public String getQ_enterDateE() {return checkGet(q_enterDateE);}
	public void setQ_enterDateE(String qEnterDateE) {q_enterDateE =  checkSet(qEnterDateE);}
	public String getQ_writeDateS() {return checkGet(q_writeDateS);}
	public void setQ_writeDateS(String qWriteDateS) {q_writeDateS =  checkSet(qWriteDateS);}
	public String getQ_writeDateE() {return checkGet(q_writeDateE);}
	public void setQ_writeDateE(String qWriteDateE) {q_writeDateE =  checkSet(qWriteDateE);}
	public String getQ_proofYear() {return checkGet(q_proofYear);}
	public void setQ_proofYear(String qProofYear) {q_proofYear =  checkSet(qProofYear);}
	public String getQ_proofDoc() {return checkGet(q_proofDoc);}
	public void setQ_proofDoc(String qProofDoc) {q_proofDoc =  checkSet(qProofDoc);}
	public String getQ_proofNoS() {return checkGet(q_proofNoS);}
	public void setQ_proofNoS(String qProofNoS) {q_proofNoS =  checkSet(qProofNoS);}
	public String getQ_proofNoE() {return checkGet(q_proofNoE);}
	public void setQ_proofNoE(String qProofNoE) {q_proofNoE =  checkSet(qProofNoE);}
	public String getQ_verify() {return checkGet(q_verify);}
	public void setQ_verify(String qVerify) {q_verify =  checkSet(qVerify);}
	public String getSignNoA() {return checkGet(SignNoA);}
	public void setSignNoA(String signNoA) {SignNoA = checkSet(signNoA);}

	
public DefaultTableModel getResultModel() throws Exception{
	UNTLA011R obj = this;
	UNTCH_Tools ul = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORG","OWNERSHIP","PROPERTYNO","PROPERTYNAME","PROPERTYNAME1",
						    			"FUNDTYPE","VALUABLE","SIGNNO","FIELD","LANDRULE",
						    			"AREA","HOLDNUME","HOLDAREA","OWNERSHIPDATE","NOTES1",
						    			"USESEPARATE","USEKIND","SUITDATES","VALUEUNIT","ORIGINALBV1",
						    			"PROOFDOC","SOURCEKIND","SOURCEDATE","SOURCENO","PROPERTYKIND",
						    			"OLDOWNER","ENTERDATE","PROOFNO","ORIGINALNOTE","ORIGINALBV",
						    			"enterOrgOwnershipPropertyNoSerialNo","subReportDataSourceManage","subReportDataSourceAttachment",
						    			"subReportDataSourceProof","subReportDataSourceUseSeparate","ORIGINALUNIT","DIFFERENCEKIND","OwnershipForDisplay"};

    	String execSQL = " select TOP 100 a.enterOrg as shareEnterOrg ,b.ownership as shareOwnership " + "\n"
    				   + " 		 ,b.propertyNo as sharePropertyNo ,b.serialNo as shareSerialNo " + "\n"
    				   + " 		 ,b.enterOrg, b.signNo, b.originalDate " + "\n"
    				   + " 		 ,(select c.organaName from SYSCA_Organ c where a.enterOrg = c.organID) as organaName " + "\n"
    				   + " 		 ,(select x.codeName from sysca_code x where b.ownership = x.codeID and x.codeKindID = 'OWA') as ownershipName " + "\n"
    				   + " 		 ,b.propertyNo ,b.serialNo, b.ownership,b.differencekind " + "\n"
    				   + "       ,(select codename from SYSCA_CODE t where codekindid='DFK' and t.codeid=b.differencekind ) as differencekindName"+"\n"
    				   + " 		 ,b.oldSerialNo, b.originalBasis" + "\n"
    				   + " 		 ,(select x.propertyName from SYSPK_PropertyCode x where b.propertyNo = x.propertyNo and x.propertyType='1') as propertyName  " + "\n"
    				   + " 		 ,b.propertyName1 " + "\n"
    				   + "		 ,b.bookvalue " + "\n"
    				   + " 		 ,(select x.codeName from SYSCA_Code x where b.fundType = x.codeID and x.codeKindID = 'FUD') as fundType " + "\n"
    				   + " 		 ,(case b.valuable when 'Y' then '珍貴財產' else '非珍貴財產' end) valuable " + "\n"
    				   + " 		 ,(select f.codeName from sysca_code f where b.field=f.codeId and f.codeKindId='FIE') as field " + "\n" 
    				   + " 		 ,b.landRule ,b.area ,b.holdNume, b.holdDeno " + "\n"
    				   + " 		 ,b.holdArea ,b.ownerShipDate ,b.notes1 " + "\n"
    				   + " 		 ,(select x.codeName from sysca_code x where b.useSeparate = x.codeId and x.codeKindId = 'SEP') as useSeparate " + "\n" 
    				   + " 		 ,(select x.codeName from sysca_code x where b.useKind = x.codeId and x.codeKindId='UKD') as useKind " + "\n" 
    				   + " 		 ,(case b.proofDoc when null then '' else b.proofDoc end) as proofDoc " + "\n"
    				   + " 		 ,(select x.codeName from sysca_code x where b.sourceKind = x.codeID and x.codeKindID = 'SKD') as sourceKind " + "\n" 
    				   + " 		 ,b.sourceDate ,b.sourceDoc as sourceNo " + "\n"
    				   + " 		 ,(select x.codeName from sysca_code x where b.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKind " + "\n"
    				   + " 		 ,b.oldOwner ,a.enterDate ,isnull(a.proofyear,'') as proofyear,isnull(a.proofDoc,'') as proofDoc1, isnull(a.proofNo,'') proofNo,b.originalNote ,b.originalBV " + "\n"
    				   + " 		 ,b.originalUnit " + "\n"
    				   + " 		 from UNTLA_AddProof a ,UNTLA_Land b " + "\n"
    	//			   + "       left join UNTLA_LAND b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.caseno=b.caseno and a.differencekind=b.differencekind  " + "\n"
    				   + " 		 where 1=1 " + "\n"
    	//						問題單1797， 因資轉資料問題，導致某些單與明細的權屬不同，與keri 討論後決議將串權屬改為串財產區分別
    				   + " 		 and a.caseNo = b.caseNo and a.enterOrg = b.enterOrg and a.differencekind = b.differencekind " + "\n"
    				   + "";

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
    		execSQL +=" and b.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and a.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
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
		    execSQL+=" and b.serialNo <=" + Common.sqlChar(getQ_serialNoE());			

    	if(!"".equals(getQ_signNo3())){
    		SignNoA = getQ_signNo3();
    	}else if(!"".equals(getQ_signNo2())){
    		SignNoA = getQ_signNo2().substring(0,3);
    	}else if(!"".equals(getQ_signNo1())){
    		SignNoA = getQ_signNo1().substring(0,1);
    	}
    	if("".equals(getQ_signNo4()) && "".equals(getQ_signNo5())){
    		SignNoA += "%";
    	}else if(!"".equals(getQ_signNo4()) && !"".equals(getQ_signNo5())){
    		SignNoA += Common.formatFrontZero(getQ_signNo4(),4) + Common.formatFrontZero(getQ_signNo5(),4);
    	}else if( !"".equals(getQ_signNo4()) && "".equals(getQ_signNo5()) ){
    		SignNoA += Common.formatFrontZero(getQ_signNo4(),4) + "%" ;
    	}else if("".equals(getQ_signNo4()) && !"".equals(getQ_signNo5()) ){
    		SignNoA += "%" + Common.formatFrontZero(getQ_signNo5(),4);
    	}
		
		if (!"".equals(getQ_signNo1()))
			execSQL+=" and b.signNo like " + Common.sqlChar(SignNoA) ;
		
		
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
		execSQL+=" order by b.enterOrg, b.ownership, b.propertyNo, b.serialNo";

    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTLA011R", this.getObjPath().replaceAll("&gt;", ">"));
		
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
        	obj.setShareOwnership(rs.getString("shareOwnership"));
        	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
        	obj.setShareSerialNo(rs.getString("shareSerialNo"));
        	
        	obj.setOriginalUnit(rs.getString("originalUnit"));
        	
        	Object[] data = new Object[columns.length];
            data[0] = rs.getString("enterOrg") + " " + rs.getString("organaName");
            data[1] = rs.getString("ownershipName");
            data[2] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
            data[3] = rs.getString("propertyName");
            data[4] = rs.getString("propertyName1");
           	data[5] = rs.getString("fundType");
            data[6] = rs.getString("valuable");
            String tempSignNo=checkGet(rs.getString("signNo"));
            if(tempSignNo.equals("")){
            	data[7]="";	
            }
            else{
            	if(tempSignNo.length()==7){
            data[7] = getSignDescName(rs.getString("signNo").substring(0,7));
            	}
            	else if(tempSignNo.length()==15){
            data[7] = getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + "-" + rs.getString("signNo").substring(11) + "地號";		
            	}
            	else{
            data[7] ="";		
            	}
            }
            data[8] = rs.getString("field");
            data[9] = rs.getString("landRule");
            data[10] = Common.areaFormat(rs.getString("area"))+"　";
            data[11] = rs.getString("holdNume") + "/" + rs.getString("holdDeno");
            data[12] = Common.areaFormat(rs.getString("holdarea"))+"　";
            
            if(rs.getString("ownerShipDate")!=null){
                data[13] = ul._transToROC_Year(rs.getString("ownerShipDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("ownerShipDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("ownerShipDate")).substring(5,7);
            }else{
                data[13] = rs.getString("ownerShipDate");
            }
            data[14] = rs.getString("notes1");
            data[15] = rs.getString("useSeparate");
            data[16] = rs.getString("useKind");
            
            String suitDateS = this.getSuitDateS(rs.getString("enterOrg"), rs.getString("ownership"), rs.getString("propertyNo"), rs.getString("serialNo"), rs.getString("originalDate"), rs.getString("originalBasis"));
            if("".equals(checkGet(suitDateS))){
            	data[17] = suitDateS;
            }else{
//            	System.out.println("@!@!@!@!@!==>"+ul._transToROC_Year(suitDateS));
            	data[17] = ul._transToROC_Year(suitDateS).substring(0,3) + "/" + ul._transToROC_Year(suitDateS).substring(3,5)+ "/" + ul._transToROC_Year(suitDateS).substring(5,7);
            }
            
            //問題單1831，一併修正傳入值沒有財產區分別問題
            String valueUnit = this.getValueUnit(rs.getString("enterOrg"), rs.getString("ownership"), rs.getString("differencekind"), rs.getString("propertyNo"), rs.getString("serialNo"));
            if("".equals(checkGet(valueUnit))){
            	data[18] = Common.valueFormat(rs.getString("originalUnit"));	
            }else{
            	data[18] = Common.valueFormat(valueUnit);
            }
            
          //問題單1831，一併修正傳入值沒有財產區分別問題
            String originalBV1 = getOriginalBV1(rs.getString("enterOrg"), rs.getString("ownership"), rs.getString("differencekind"), rs.getString("propertyNo"), rs.getString("serialNo"), rs.getString("holdArea"));
            if("".equals(checkGet(originalBV1))){
            	data[19] = Common.valueFormat(rs.getString("bookvalue"));	
            }else{
            	data[19] = Common.valueFormat(originalBV1);
            }
            
            
            data[20] = rs.getString("proofDoc");
            data[21] = rs.getString("sourceKind");
            if(rs.getString("sourceDate")!=null){
                data[22] = ul._transToROC_Year(rs.getString("sourceDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("sourceDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("sourceDate")).substring(5,7);
            }else{
                data[22] = rs.getString("sourceDate");
            }
            
            data[23] = rs.getString("sourceNo");
            data[24] = rs.getString("propertyKind");
            data[25] = rs.getString("oldOwner");
            if(rs.getString("enterDate")!=null){
                data[26] = ul._transToROC_Year(rs.getString("enterDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(5,7);
            }else{
                data[26] = rs.getString("enterDate");
            }
            
            data[27] = Common.checkGet(rs.getString("proofyear"))+"年"+Common.checkGet(rs.getString("proofDoc1")) + "字第" + Common.checkGet(rs.getString("proofNo")) + "號";
            data[28] = rs.getString("originalNote");
            data[29] = Common.valueFormat(rs.getString("originalBV"));
            data[30] = rs.getString("shareEnterOrg")+rs.getString("shareOwnership")+rs.getString("sharePropertyNo")+rs.getString("shareSerialNo");
            
            data[31] = new JRTableModelDataSource(getSubModel((String)data[0]));  //管理資料－多筆
            data[32] = new JRTableModelDataSource(getSubMode2((String)data[0]));  //地上物資料－多筆
            data[33] = new JRTableModelDataSource(getSubMode3((String)data[0]));  //帳務資料－多筆
            data[34] = new JRTableModelDataSource(getSubMode4((String)data[0]));  //異動紀錄－多筆
            data[35] = new Double(Double.parseDouble(obj.getOriginalUnit()));
            data[36] = rs.getString("differencekind")+" "+rs.getString("differencekindName");
            //TDCM問題單1797，權屬非國有時，欄位不顯示"國有"字樣
            data[37] = rs.getString("shareOwnership");
            //data[35]= rs.getString("originalUnit");
            //for(i=0;i<37;i++)if(data[i]==null)data[i]="";
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

	
	private String getSignDescName(String signNo){
		String sql = null;

		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		return queryOne_asString(sql);
		
	}
	
	private String getSuitDateS(String enterOrg, String ownership, String propertyNo, String serialNo, String originalDate, String originalBasis){
		UNTCH_Tools ul = new UNTCH_Tools();
		String sql = null;
		String bulletinDate = null;
		String result = null;
		
		sql = "select max(m.bulletinDate) from UNTLA_Value m " +
				" where m.enterOrg = " + Common.sqlChar(enterOrg) +
				" and m.ownership = " + Common.sqlChar(ownership) +
				" and m.propertyNo = " + Common.sqlChar(propertyNo) +
				" and m.serialNo = " +  Common.sqlChar(serialNo);		
		bulletinDate = queryOne_asString(sql);
		
		sql = "select max(m.SuitDateS) from UNTLA_Value m " +
				" where m.enterOrg = " + Common.sqlChar(enterOrg) +
				" and m.ownership = " + Common.sqlChar(ownership) +
				" and m.propertyNo = " + Common.sqlChar(propertyNo) +
				" and m.serialNo = " +  Common.sqlChar(serialNo) +
				" and m.bulletinDate = " +  Common.sqlChar(bulletinDate);
		result = queryOne_asString(sql);
				
		if("".equals(checkGet(result))){
			sql = "select SuitDateS from UNTLA_BulletinDate " +
					" where bulletinDate = " + Common.sqlChar(originalDate) +
					" and bulletinkind = '1' ";
			result = queryOne_asString(sql);
		}
		
		return result;
	}
	

	//問題單1831，一併修正查詢條件沒有加入財產區分別問題
	private String getValueUnit(String enterOrg, String ownership, String differenceKind, String propertyNo, String serialNo){
		UNTCH_Tools ul = new UNTCH_Tools();
		String sql = null;
		String valueUnit = null;
		String bulletinDate = null;
		String result = null;
		
		sql = "select max(bulletinDate) from UNTLA_Value m " +
				" where m.enterOrg = " + Common.sqlChar(enterOrg) +
				" and m.ownership = " + Common.sqlChar(ownership) +
				" and m.differencekind = " + Common.sqlChar(differenceKind) +
				" and m.propertyNo = " + Common.sqlChar(propertyNo) +
				" and m.serialNo = " +  Common.sqlChar(serialNo);
				
		bulletinDate = queryOne_asString(sql);
		
		sql = "select max(valueUnit) from UNTLA_Value m " +
				" where m.enterOrg = " + Common.sqlChar(enterOrg) +
				" and m.ownership = " + Common.sqlChar(ownership) +
				" and m.differencekind = " + Common.sqlChar(differenceKind) +
				" and m.propertyNo = " + Common.sqlChar(propertyNo) +
				" and m.serialNo = " +  Common.sqlChar(serialNo) +
				" and m.bulletinDate = " +  Common.sqlChar(bulletinDate);
		

		result = queryOne_asString(sql);
		
		return result;
	}
	
	

	//問題單1831，修正[申報地價總價]計算用單價錯誤，比照申報地價單價修正
	private String getOriginalBV1(String enterOrg, String ownership, String differenceKind, String propertyNo, String serialNo, String holdArea){
		String sql = null;
		String bulletinDate = null;
		String result = null;
		
		sql = "select max(bulletinDate) from UNTLA_Value m " +
				" where m.enterOrg = " + Common.sqlChar(enterOrg) +
				" and m.ownership = " + Common.sqlChar(ownership) +
				" and m.differencekind = " + Common.sqlChar(differenceKind) +
				" and m.propertyNo = " + Common.sqlChar(propertyNo) +
				" and m.serialNo = " +  Common.sqlChar(serialNo);
				
		bulletinDate = queryOne_asString(sql);
		
		sql = "select max(valueUnit) * " + holdArea + " from UNTLA_Value m " +
				" where m.enterOrg = " + Common.sqlChar(enterOrg) +
				" and m.ownership = " + Common.sqlChar(ownership) +
				" and m.differencekind = " + Common.sqlChar(differenceKind) +
				" and m.propertyNo = " + Common.sqlChar(propertyNo) +
				" and m.serialNo = " +  Common.sqlChar(serialNo) +
				" and m.bulletinDate = " +  Common.sqlChar(bulletinDate);
				
		result = queryOne_asString(sql);
		
		return result;
	}
	
		private String queryOne_asString(String sql){
			Database db = null;
			ResultSet rs = null;
			String result = null;
			try{
				db = new Database();
				rs = db.querySQL(sql);
				if(rs.next()){				
					result = rs.getString(1);
				}
				rs.close();
			}catch(Exception e){
				System.out.println("queryOne_asString Exception => " + e.getMessage());
			}finally{
				db.closeAll();
			}
			return result;
		}

//管理資料－多筆
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTLA011R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "SERIALNO1", "USEUNIT","USERELATION","USEDATEE","USEAREA","NOTES1"};
    	String execSQL="select a.serialNo1, (case c.organAName when null then '' else a.useUnit1 end) as useUnit," +
    					" (select h.codeName from SYSCA_Code h where a.useRelation=h.codeId and h.codeKindId='URE')as useRelation," +
    					" a.useDateS, a.useDateE," +
    					" a.useArea,a.notes1 " +
    					" from UNTLA_Manage a, UNTLA_Land b, SYSCA_ORGAN c " +
    					" where b.enterOrg=a.enterOrg and b.ownership=a.ownership and b.propertyNo=a.propertyNo and b.serialNo=a.serialNo and a.useUnit=c.organId " +
    					" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
    					" ";
    	execSQL += " order by a.serialNo1 ";
	
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		
    		Object[] data = new Object[6];
    		if(rs.getString("serialNo1")!=null){
    			data[0]=rs.getString("serialNo1");
    		}else{
    			data[0]="";
    		}   		
    		data[1]=rs.getString("useUnit");
    		data[2] = rs.getString("useRelation");
    		data[3] = ul._transToROC_Year(rs.getString("useDateS")) + "-" + ul._transToROC_Year(rs.getString("useDateE"));   
    		if(rs.getString("useArea")!=null){
    			data[4] = Common.areaFormat(rs.getString("useArea"))+"　";
    		}else{
    			data[4] = "";
    		}
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

//地上物資料－多筆
public DefaultTableModel getSubMode2(String caseCode) throws Exception{

	UNTLA011R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "SERIALNO1", "OWNERSHIP1","OWNER","STATE","MERGEUSESIGN","NOTES1","PURPOSE","USEAREA"};
    	String execSQL="select  a.serialNo1," +
						" (select h.codeName from SYSCA_Code h where a.ownership1=h.codeId and h.codeKindId='OWN')as ownership1, " +
						" a.owner,(select c.organAName from SYSCA_ORGAN c where a.manageOrg=c.organId)manageOrg," +
						" a.state,a.mergeUseSign,a.notes1," +
						"(select k.codeName from SYSCA_Code k where a.purpose=k.codeId and k.codeKindId='PUR') as purpose," +
						"a.useArea " +
    					" from UNTLA_Attachment a, UNTLA_Land b " +
    					" where 1=1 "+
						" and a.enterOrg=b.enterOrg and a.ownerShip=b.ownerShip and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo " +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" order by a.serialNo1 ";

    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	//int i;
    	while(rs.next()){
    		Object[] data = new Object[8];
    		if(rs.getString("serialNo1")!=null){
    			data[0]=rs.getString("serialNo1");
    		}else{
    			data[0]="";
    		}
    		data[1] = rs.getString("ownership1");
    		if(rs.getString("owner")!=null&&rs.getString("manageOrg")!=null){
    			data[2] = rs.getString("owner")+"("+rs.getString("manageOrg")+")";
    		}
    		else if(rs.getString("manageOrg")!=null)data[2]="("+rs.getString("manageOrg")+")";
    		else data[2] = rs.getString("owner");    		
    		data[3] = rs.getString("state");
    		data[4] = rs.getString("mergeUseSign");
    		data[5] = rs.getString("notes1");
    		data[6] = rs.getString("purpose");
    		if(rs.getString("useArea")!=null){
    			data[7] = Common.areaFormat(rs.getString("useArea"))+"　";
    		}else{
    			data[7] = "";
    		}
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0 ){ //若無資料也要顯示欄位
	    	 rowData.addElement(new Object[8]);
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
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTLA011R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "NAME","PROOFNO","BOOKNOTES","OLDBOOKVALUE","ADJUSTBOOKVALUE1","ADJUSTBOOKVALUE2","NEWBOOKVALUE","ORIGINALUNIT"};
    	//                                                                                                                                               
        String execSQL="(select a.adjustDate as enterDate, " +
        				"'財產增減值' as Name," +
        				" a.proofYear, a.proofDoc, a.proofNo," +
        				" b.bookNotes," +
        				" '0' as checkReduce," +
        				" b.oldBookValue," +
        				" b.addbookvalue,b.reducebookvalue," +
        				" b.newBookValue " +
						" from UNTLA_AdjustProof a, UNTLA_AdjustDetail b "+
						" where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and b.verify = 'Y' " +
						" ) " +
    					"union " +
						"(select a.reduceDate as enterDate," +
						"'財產減損單' as Name," +
						" a.proofYear, a.proofDoc, a.proofNo," +
        				" b.bookNotes," +
        				" '1' as checkReduce," +
        				" b.bookValue as oldBookValue," +
        				" '0' as addbookvalue, b.bookValue as reducebookvalue," +
        				" 0 as newBookValue "+
        				" from UNTLA_ReduceProof a, UNTLA_ReduceDetail b "+
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
    		Object[] data = new Object[8];
    		if(rs.getString("enterDate")!=null)
    			data[0] = ul._transToROC_Year(rs.getString("enterDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("enterDate")).substring(5,7);
    		else data[0]="";
    		data[1] = rs.getString("Name");
    		data[2] = rs.getString("proofYear") + "年" + rs.getString("proofDoc") + "字第" + rs.getString("proofNo") + "號";
    		data[3] = rs.getString("bookNotes");
    		data[4] = Common.valueFormat(rs.getString("oldBookValue"));
    		String AddbookValue= rs.getString("addbookvalue");
    		String ReducebookValue= rs.getString("reducebookvalue");
    		double AddbookValueI;
    		double ReducebookValueI;
    		if(AddbookValue!=null){
    			AddbookValueI=Double.parseDouble(AddbookValue);
    		}else{
    			AddbookValueI=0;
    		}
    		if(ReducebookValue!=null){
    			ReducebookValueI=Double.parseDouble(ReducebookValue);	
    		}else{
    			ReducebookValueI=0;
    		}
    				if(rs.getString("checkReduce").equals("1")){
    					data[5]=new Double(AddbookValueI);
        				data[6]=new Double(ReducebookValueI);
        			}else{
      					data[5]=new Double(AddbookValueI);
        				data[6]=new Double(ReducebookValueI);
        			}
    			
    		data[7]=Common.valueFormat(rs.getString("newBookValue"));
//System.out.println(rs.getString("newBookValue")+" -- "+Common.valueFormat(rs.getString("newBookValue")));
    		//for(i=0;i<5;i++)if(data[i]==null)data[i]="";
    		
    		rowData.addElement(data);
    	}
    	if(rowData.size()==0 ){ //若無資料也要顯示欄位
	    	 rowData.addElement(new Object[8]);
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

//異動紀錄－多筆
public DefaultTableModel getSubMode4(String caseCode) throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTLA011R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Vector rowData = new Vector();
	//int i;
	try{
        String[] columns = new String[] { "CHANGEDATE", "CHANGECAUSE","CHANGEITEM","NOTES1" };
    	String execSQL="(select a.changeDate as changeDate, " +
    					" a.changeCause as cause," +
    					" '' as cause1," +
    					" a.changeItem," +
    					" a.notes1 " +
						" from UNTLA_UseSeparate a "+
						" where 1=1 " +
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" ) " +
						"union " +
						"(select a.adjustDate as changeDate, " +
    					" (case b.cause when '1' then '公告地價調整' when '2' then '資產重估調整' when '3' then '其他' else '' end) as Cause," +
    					" b.cause1," +
    					" b.changeItem," +
    					" a.notes as notes1" +
						" from UNTLA_AdjustProof a ,UNTLA_AdjustDetail b "+
						" where a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.caseNo=b.caseNo " +
						" and b.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and b.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and b.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and b.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" ) " +
						" order by changeDate";
    		   
    	    ResultSet rs = db.querySQL(execSQL);
    	    while(rs.next()){
    	        Object[] data = new Object[4];
    	        if(rs.getString("changeDate")!=null)
    	        	data[0] = ul._transToROC_Year(rs.getString("changeDate")).substring(0,3)+"/"+ul._transToROC_Year(rs.getString("changeDate")).substring(3,5)+"/"+ul._transToROC_Year(rs.getString("changeDate")).substring(5,7);
    	        else data[0]="";
    	        String Cause = rs.getString("cause");
    	        if("其他".equals(Cause))data[1]=rs.getString("cause1");
    	        else data[1]=Cause;
    	        
    	        data[2] = rs.getString("changeItem");
    	        data[3]=rs.getString("notes1");
    	        rowData.addElement(data);
    	    }
    	    if(rowData.size()==0 ){ //若無資料也要顯示欄位
    	    	 rowData.addElement(new Object[4]);
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


}