

/*
*<br>程式目的：非消耗品報廢殘值處理清冊報表檔 
*<br>程式代號：untmp038r
*<br>撰寫日期：94/11/28 
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTNE039R extends SuperBean{


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
	
	String amount3;
	String amount4;
	String amount5;
	String value3;
	String value4;
	String value5;
	
	public String getAmount3(){ return checkGet(amount3); }
	public void setAmount3(String s){ amount3=checkSet(s); }
	public String getAmount4(){ return checkGet(amount4); }
	public void setAmount4(String s){ amount4=checkSet(s); }
	public String getAmount5(){ return checkGet(amount5); }
	public void setAmount5(String s){ amount5=checkSet(s); }
	public String getValue3(){ return checkGet(value3); }
	public void setValue3(String s){ value3=checkSet(s); }
	public String getValue4(){ return checkGet(value4); }
	public void setValue4(String s){ value4=checkSet(s); }
	public String getValue5(){ return checkGet(value5); }
	public void setValue5(String s){ value5=checkSet(s); }
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
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	while(q_caseNoS!=null&&!"".equals(q_caseNoS)&&q_caseNoS.length()<10){q_caseNoS=q_caseNoS+"0";}
    	while(q_caseNoE!=null&&!"".equals(q_caseNoE)&&q_caseNoE.length()<10){q_caseNoE=q_caseNoE+"9";}
    	                               //     0            1           2          3            4             5          6          7            8              9                  10            11         12                13       14
    	String[] columns = new String[] {"OWNERSHIP","ENTERORGNAME","CASENO","PROPERTYNO","PROPERTYNAME","BUYDATE","LIMITYEAR","USEYEAR","OLDBOOKUNIT","ADJUSTBOOKAMOUNT","ADJUSTBOOKVALUE","CAUSE2","SCRAPVALUE2","DEALSUGGESTION","SIGN"};
    	String execSQL="select c.organAName as enterOrgName," +
		    			" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName," +
		    			" a.caseNO," +
		    			" (b.propertyNo)||'-'||(b.serialNo) as propertyNo," +
		    			" d.propertyName," +
		    			" b.buyDate," +
		    			" d.limitYear, b.otherlimitYear," +
		    			" b.useYear||'年'||b.useMonth||'個月' as useYear," +
		    			" b.oldBookUnit," +
		    			" b.adjustBookAmount," +
		    			" b.adjustBookValue," +
		    			" b.cause2"; 
    	String execSQL1=" from UNTNE_ReduceProof a, UNTNE_ReduceDetail b, SYSCA_Organ c, SYSPK_PropertyCode2 d";
    	execSQL1=execSQL1+" where 1=1  and a.enterorg=b.enterorg  and b.enterorg=d.enterorg and b.propertyno=d.propertyno and a.caseno=b.caseno and a.ownership=b.ownership and a.enterOrg=c.organID(+)";
    	execSQL1=execSQL1+" and d.enterOrg in ('000000000A',a.enterOrg)";
    	execSQL1=execSQL1+" and d.propertyType='1'";

    	if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL1 = execSQL1 + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL1+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL1 += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL1+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		execSQL1 = execSQL1 + " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		execSQL1 = execSQL1 + " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL1 = execSQL1 + " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL1 = execSQL1 + " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		execSQL1 = execSQL1 + " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		execSQL1 = execSQL1 + " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		execSQL1 = execSQL1 + " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	execSQL=execSQL+execSQL1+" order by a.caseno,b.propertyno,b.serialno";

    	//String execSQL2="select sum(adjustBookAmount),sum(adjustBookValue) "+execSQL1+" and substr(b.propertyNo,1,1)='3' ";
    	//String execSQL3="select sum(adjustBookAmount),sum(adjustBookValue) "+execSQL1+" and substr(b.propertyNo,1,1)='4' ";
    	//String execSQL4="select sum(adjustBookAmount),sum(adjustBookValue) "+execSQL1+" and substr(b.propertyNo,1,1)='5' ";
    	String tansToDouble;
    	String DateTrn;
    	String limitYeartemp;
    	//int i;
     	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[15];
        	data[0] = String.valueOf(rs.getString("ownershipName"));//產品編號
            data[1] = rs.getString("enterOrgName");
            data[2] = rs.getString("caseNO")+"　";
            data[3] = rs.getString("propertyNo");
            data[4] = rs.getString("propertyName");
            DateTrn = rs.getString("buyDate");
        	if(DateTrn!=null)data[5]=DateTrn.substring(0,3)+"年"+DateTrn.substring(3,5)+"月";
        	else data[5]=null;
            data[6] = rs.getString("limitYear");
        	limitYeartemp=rs.getString("otherLimitYear");;
        	if(data[6]==null)data[6]=limitYeartemp;
        	data[7] = rs.getString("useYear");
        	tansToDouble = rs.getString("oldBookUnit");  //單價
            if(tansToDouble!=null)data[8]= new Double(Double.parseDouble(tansToDouble));
            else data[8]=null;
            tansToDouble = rs.getString("adjustBookAmount");
            if(tansToDouble!=null)data[9]= new Double(Double.parseDouble(tansToDouble));
            else data[9]=null;
            tansToDouble = rs.getString("adjustBookValue");
            if(tansToDouble!=null)data[10]= new Double(Double.parseDouble(tansToDouble));
            else data[10]=null;
            data[11] = rs.getString("cause2");
            	//tansToDouble = rs.getString("scrapValue2");
            	//if(tansToDouble!=null)data[12]= new Double(Double.parseDouble(tansToDouble));
            	// data[12]=null;
            data[12]=null;
            	//data[13] = rs.getString("dealSuggestion");
            data[13] = null;
            data[14] = "使用單位(人員)                        物品管理單位(人員)                         會計單位(人員)                        機關首長";
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
