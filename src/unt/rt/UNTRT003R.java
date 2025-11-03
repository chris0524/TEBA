/*
*<br>程式目的：權利增加單查詢檔 
*<br>程式代號：untrt003r
*<br>撰寫日期：94/11/15
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import util.*;

public class UNTRT003R extends UNTRT001Q{
	String enterOrg;
	String ownership;
	String caseNoS;
	String caseNoE;
	String writeDateS;
	String writeDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;

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
	String q_kind;

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
	String enterDate;
	
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getSharePropertyNo(){ return checkGet(sharePropertyNo); }
	public void setSharePropertyNo(String s){ sharePropertyNo=checkSet(s); }
	public String getShareSerialNo(){ return checkGet(shareSerialNo); }
	public void setShareSerialNo(String s){ shareSerialNo=checkSet(s); }
	public String getEnterDate(){ return checkGet(enterDate); }
	public void setEnterDate(String s){ enterDate=checkSet(s); }

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
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
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); }

	double areaSum=0.00;
	double holdAreaSum=0.00;
	double setAreaSum=0.00;
	int originalBVSum=0;

	String sumArea;
	String sumHoldArea;
	String sumSetArea;
	String sumOriginalBV;
	String caseNo;
	String oldCaseNo;

	public String getCaseNo(){ return checkGet(caseNo); }
	public void setCaseNo(String s){ caseNo=checkSet(s); }
	public String getOldCaseNo(){ return checkGet(oldCaseNo); }
	public void setOldCaseNo(String s){ oldCaseNo=checkSet(s); }
	public String getSumArea(){ return checkGet(sumArea); }
	public void setSumArea(String s){ sumArea=checkSet(s); }
	public String getSumHoldArea(){ return checkGet(sumHoldArea); }
	public void setSumHoldArea(String s){ sumHoldArea=checkSet(s); }
	public String getSumSetArea(){ return checkGet(sumSetArea); }
	public void setSumSetArea(String s){ sumSetArea=checkSet(s); }
	public String getSumOriginalBV(){ return checkGet(sumOriginalBV); }
	public void setSumOriginalBV(String s){ sumOriginalBV=checkSet(s); }
	
public DefaultTableModel getResultModel() throws Exception{
	UNTRT003R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOF","CASENO","MANAGENO","OWNERSHIP","PROPERTYKIND","FUNDTYPE","BUYDATE","PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","SOURCEKIND","MEAT","PROOFDOC1","HOLDNUME1_HOLDDENO2","ORIGINALBV","REGISTERCAUSE","REGISTERDATE","SETPERIOD","COMMONOBLIGEE","SETPERSON","PAYDATE","INTEREST","RENT","NOTES1","subReportDataSource","SUMAREA","SUMHOLDAREA","SUMSETAREA","SUMORIGINALBV","Q_KIND","caseNoPage","summonsNo"};

    	String execSQL="select a.enterDate, a.enterOrg as shareEnterOrg, a.ownership as shareOwnership, a.propertyNo as sharePropertyNo, a.serialNo as shareSerialNo, " +
    					" c.ORGANANAME as enterOrgName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.caseNo," +
    					" a.manageNo ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownership, " +
    					" (select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKind, " +
    					" d.codeName as fundType, a.buyDate,(a.propertyNo||'-'||a.serialNo) as propertyNo, " +
    					" b.propertyName, a.propertyName1, " +
						" (select f.codeName from SYSCA_Code f where 1=1 and a.sourceKind=f.codeID(+) and f.codeKindID(+) = 'SKE') as sourceKind, "+
    					" a.meat, a.proofDoc1, a.holdNume1, a.holdDeno2, a.originalBV, " +
    					" (select g.codeName from SYSCA_Code g where 1=1 and a.registerCause=g.codeID(+) and g.codeKindID(+) = 'RCA') as registerCause, " +
    					" a.registerDate, a.setPeriod, a.commonObligee, a.setPerson, a.payDate, nvl(a.interest,0) as interest, a.rent, a.notes1, " +
    					" a.summonsNo "+
    					" from UNTRT_AddProof a,SYSPK_PropertyCode b, SYSCA_Organ c,SYSCA_Code d "+
						" where 1=1 and a.propertyNo = b.propertyNo and a.enterOrg = c.organID and a.fundType=d.codeID(+) and d.codeKindID(+) = 'FUD' "+
						"";
 
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

		if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and a.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL += " and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
    	execSQL+=" order by a.caseNo ";
    	String kindName[]={"第一聯","第二聯","第三聯"};
   
    	//j ,i ,k 為報表收執第幾聯之判斷
    	int j ,i ,k;
    	if("4".equals(q_kind)){
    		i = 3 ;
    		j = 1 ;
    	}else{
    		i = Integer.parseInt(q_kind);
    		j = Integer.parseInt(q_kind);
    	}
    		
            ResultSet rs = null ;
            Vector rowData = new Vector();
    		for(k=j ;k<=i ;k++){
    		rs = db.querySQL(execSQL);

            //int i;
            while(rs.next()){
            	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
            	obj.setShareOwnership(rs.getString("shareOwnership"));
            	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
            	obj.setShareSerialNo(rs.getString("shareSerialNo"));
            	obj.setEnterDate(rs.getString("enterDate"));
            	obj.setCaseNo(rs.getString("caseNo")+j);
            	
            	Object[] data = new Object[35];
                data[0] = rs.getString("enterOrgName");
                data[1] = Common.MaskCDate(rs.getString("writeDate"));
                data[2] = rs.getString("writeUnit");
                data[3] = rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號";
                data[4] = "電腦單號："+rs.getString("caseNo");
                data[5] = Common.get(rs.getString("manageNo"))+"　";
                data[6] = rs.getString("ownership");
                data[7] = rs.getString("propertyKind");
                data[8] = rs.getString("fundType");
                data[9] = Common.MaskDate(rs.getString("buyDate"));
                data[10] = rs.getString("propertyNo");
                data[11] = rs.getString("propertyName");
                data[12] = rs.getString("propertyName1");
                data[13] = rs.getString("sourceKind");
                data[14] = rs.getString("meat");
                if(rs.getString("proofDoc1")==null){
                	data[15] = "免繕狀";
                }else{
                	data[15] = rs.getString("proofDoc1");
                }
                data[16] = rs.getString("holdNume1")+"/"+rs.getString("holdDeno2");
                data[17] = new Integer(rs.getString("originalBV"));
                data[18] = rs.getString("registerCause");
               	data[19] = Common.MaskDate(rs.getString("registerDate"));
                data[20] = rs.getString("setPeriod");
                data[21] = rs.getString("commonObligee");
                data[22] = rs.getString("setPerson");
               	data[23] = Common.MaskDate(rs.getString("payDate"));
                data[24] = new Integer(rs.getString("interest"));
                data[25] = rs.getString("rent");
                data[26] = rs.getString("notes1");
                data[27] = new JRTableModelDataSource(getSubModel((String)data[0]));            
                data[28] = Common.areaFormat(obj.getSumArea())+"　";
                data[29] = Common.areaFormat(obj.getSumHoldArea())+"　";
                data[30] = Common.areaFormat(obj.getSumSetArea())+"　";
                data[31] = new Integer(obj.getSumOriginalBV());
                data[32] = kindName[k-1];
                data[33] = rs.getString("caseNo")+j;
                data[34] = Common.get(rs.getString("summonsNo"))+"　";
    			rowData.addElement(data);
             }
    		}//for
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

public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTRT003R obj = this;
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	//合計價值換頁的判斷
	if(!(obj.getCaseNo()).equals(obj.getOldCaseNo())){
		areaSum=0.00;
		holdAreaSum=0.00;
		setAreaSum=0.00;
		originalBVSum=0;
		obj.setSumArea(areaSum+"");
		obj.setSumHoldArea(holdAreaSum+"");
		obj.setSumSetArea(setAreaSum+"");
		obj.setSumOriginalBV(originalBVSum+"");
	}
	obj.setOldCaseNo(obj.getCaseNo());
    
    try{
        String[] columns = new String[] { "SERIALNO1", "SIGNNO","SETOBLIGOR","AREA","HOLDNUME_HOLDDENO","HOLDAREA","SETAREA","ORIGINALBV"};
    	String execSQL="select a.serialNo1, b.signDesc as signName, a.signNo, a.setObligor, a.area, a.holdNume, " +
    					" a.holdDeno, TO_CHAR(a.holdArea,'999999999.99') as holdArea, TO_CHAR(a.setArea,'999999999.99') as setArea, a.originalBV " +
						" from UNTRT_AddDetail a, SYSCA_SIGN b "+
						" where 1=1 and substr(a.signNo,1,7)= b.signNo"+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo());
			if(!"".equals(Common.get(obj.getEnterDate())) && obj.getEnterDate()!=null){
				execSQL+=" and a.enterDate=" + util.Common.sqlChar(obj.getEnterDate());
			}else{
				execSQL+=" and a.enterDate is null";
			}
    	execSQL+=" order by a.serialNo1";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i;
    	while(rs.next()){
    		Object[] data = new Object[8];
    		data[0] = rs.getString("serialNo1");
    		data[1] = rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11)+"地號";
    		data[2] = rs.getString("setObligor");
    		data[3] = Common.areaFormat(rs.getString("area"))+"　";
    		data[4] = rs.getString("holdNume")+"/"+rs.getString("holdDeno");
    		data[5] = Common.areaFormat(rs.getString("holdArea"))+"　";
    		data[6] = Common.areaFormat(rs.getString("setArea"))+"　";
    		data[7] = new Integer(rs.getString("originalBV"));
			//合計
			areaSum += rs.getDouble("area");
			holdAreaSum += rs.getDouble("holdArea");
			setAreaSum += rs.getDouble("setArea");
			originalBVSum += Integer.parseInt(rs.getString("originalBV"));
			obj.setSumArea(areaSum+"");
			obj.setSumHoldArea(holdAreaSum+"");
			obj.setSumSetArea(setAreaSum+"");
			obj.setSumOriginalBV(originalBVSum+"");
    		for(i=0;i<8;i++)if(data[i]==null)data[i]="";
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

}
