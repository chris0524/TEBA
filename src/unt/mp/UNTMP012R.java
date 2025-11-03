/*
*<br>程式目的：動產移動單報表檔 
*<br>程式代號：untmp012r
*<br>撰寫日期：95/02/20
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

public class UNTMP012R extends SuperBean{

	String enterOrg;
	String ownership;
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
	String q_kind;

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
    	String[] columns = new String[] {"ENTERORGNAME","WRITEDATE","WRITEUNIT","PROOFDOC","Q_KIND","OWNERSHIP","CASENO","SOURCEDATE","NEWMOVEDATE","PROPERTYNOSERIALNO","PROPERTYNAME","NAMEPLATESPECIFICATION","PROPERTYUNIT","BOOKAMOUNT","BOOKUNIT","BOOKVALUE","NEWKEEPUNITNAME","NEWPLACE","SCRAPVALUE","LIMITYEAR","USEYEARUSEMONTH","CASENOC","propertyKind","propertyKindName","newKeeper","oldKeeper","OLDKEEPUNITNAME"};
    	String execSQL = "select c.organAName as enterOrgName," +
    				     " a.writeDate," +
    				     " a.writeUnit," +
    				     " a.proofDoc||'字第'||a.proofNo||'號' as proofDoc," +
    				     " a.ownership, " +
    				     " (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
    				     " a.enterorg, a.caseNO, " +
    				     " b.sourceDate, " +
    				     " b.newMoveDate, " +
    				     " b.propertyNo,"+
    				     " (substr(b.propertyNo,1,7) || '-' || substr(b.propertyNo,8)) as propertyNo1, " +
    				     " b.serialNo, "+
    				     " d.propertyName, " +
    				     " (b.specification || '/' || b.nameplate) as nameplateSpecification, " +
    				     " d.propertyUnit, b.otherPropertyUnit, " +
    				     " b.bookAmount, b.bookUnit, b.bookValue, " +
    				     " (select c.unitName from UNTMP_KeepUnit c where b.enterOrg=c.enterOrg and b.newKeepUnit=c.unitNo) as newKeepUnitName, " +
    				     " (select c.unitName from UNTMP_KeepUnit c where b.enterOrg=c.enterOrg and b.oldKeepUnit=c.unitNo) as oldKeepUnitName, " +
    				     " b.newPlace, " +
    				     " b.scrapValue, " +
    				     " d.limitYear, b.otherLimitYear, " +
    				     " b.useYear, b.useMonth, " +
    				     " b.propertyKind," +
    				     " decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKindName "+
    				     " from UNTMP_MoveProof a,UNTMP_MoveDetail b,SYSCA_Organ c,SYSPK_PropertyCode d "+
    				     " where a.enterorg=b.enterorg and a.caseno=b.caseNo and a.ownership=b.ownership " +
    				     " and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+) "+
    				     " and d.enterOrg in ('000000000A',a.enterOrg) "+
    				     " and d.propertyType='1' " +
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
    	
    	execSQL+=" order by a.caseNo,b.propertyKind,b.propertyno,b.serialno";
    	//System.out.println(execSQL);
    	//String execSQL2="select a.caseno,count(*) "+execSQL1+" group by a.caseno order by a.caseno";
    	String kindName[]={"第一聯","第二聯","第三聯"};
        //int i;
    	if(!"4".equals(q_kind)){
    		if("3".equals(q_kind))q_kind=kindName[2];
    		else if("2".equals(q_kind))q_kind=kindName[1];
    		else q_kind=kindName[0];

	        ResultSet rs = db.querySQL(execSQL);
	        Vector rowData = new Vector();
	        while(rs.next()){
	        	Object[] data = new Object[27];
	        	data[0] = rs.getString("enterOrgName");
	            data[1] = Common.MaskCDate(rs.getString("writeDate"));
	            data[2] = rs.getString("writeUnit");
	        	data[3] = rs.getString("proofDoc");
	            data[4] = q_kind;
	            data[5] = rs.getString("ownershipName");
	            data[6] = rs.getString("caseNo")+"　";
	            data[7] = Common.MaskDate(rs.getString("sourceDate"));
	            data[8] = Common.MaskDate(rs.getString("newMoveDate"));
	            data[9] = rs.getString("propertyNo1")+"\n-"+rs.getString("serialNo");
	            data[10] = rs.getString("propertyName");
	            data[11] = rs.getString("nameplateSpecification");
	            if(rs.getString("propertyUnit")!=null && !"".equals(rs.getString("propertyUnit"))){
	            	data[12] = rs.getString("propertyUnit");
	            }else{
	            	data[12] = rs.getString("otherPropertyUnit");
	            }
	            data[13] = Common.valueFormat(rs.getString("bookAmount"));
	            data[14] = Common.valueFormat(rs.getString("bookUnit"));
	            data[15] = Common.valueFormat(rs.getString("bookValue"));
	            data[16] = rs.getString("newKeepUnitName");
	            data[17] = rs.getString("newPlace");
	            data[18] = Common.valueFormat(rs.getString("scrapValue"));
	            if(rs.getString("limitYear")!=null && !"".equals(rs.getString("limitYear"))){
	            	data[19] = rs.getString("limitYear");
	            }else{
	            	data[19] = rs.getString("otherLimitYear");
	            }
	            if(rs.getString("useYear")!=null && !"".equals(rs.getString("useYear")) && rs.getInt("useYear")!=0){
	            	data[20] = rs.getString("useYear")+"年\n"+rs.getString("useMonth")+"個月";
	            }else{
	            	data[20] = rs.getString("useMonth")+"個月";
	            }
	            data[21] = rs.getString("caseNo");
	            data[22] = Common.get(rs.getString("propertyKind"));
	            data[23] = Common.get(rs.getString("propertyKindName"));
	            data[24] = getKeeperName(rs.getString("enterorg"),rs.getString("ownership"),rs.getString("caseNO"),rs.getString("propertyNO"),rs.getString("serialNO"),"newkeepName");
	            data[25] = getKeeperName(rs.getString("enterorg"),rs.getString("ownership"),rs.getString("caseNO"),rs.getString("propertyNO"),rs.getString("serialNO"),"oldkeepName");
	            data[26] = rs.getString("oldKeepUnitName");
	            
	            rowData.addElement(data);
	        }
	        Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model.setDataVector(data, columns);
    	}//if
    	else{
    		int j;
    		Vector rowData = new Vector();
    		for(j=0;j<3;j++){
    			
	    		q_kind=kindName[j];
	    		ResultSet rs = db.querySQL(execSQL);
	    		while(rs.next()){
		        	Object[] data = new Object[27];
		        	data[0] = rs.getString("enterOrgName");
		            data[1] = Common.MaskCDate(rs.getString("writeDate"));
		            data[2] = rs.getString("writeUnit");
		        	data[3] = rs.getString("proofDoc");
		            data[4] = q_kind;
		            data[5] = rs.getString("ownershipName");
		            data[6] = rs.getString("caseNO")+"　";
		            data[7] = Common.MaskDate(rs.getString("sourceDate"));
		            data[8] = Common.MaskDate(rs.getString("newMoveDate"));
		            data[9] = rs.getString("propertyNo1")+"\n-"+rs.getString("serialNo");
		            data[10] = rs.getString("propertyName");
		            data[11] = rs.getString("nameplateSpecification");
		            if(rs.getString("propertyUnit")!=null && !"".equals(rs.getString("propertyUnit"))){
		            	data[12] = rs.getString("propertyUnit");
		            }else{
		            	data[12] = rs.getString("otherPropertyUnit");
		            }
		            data[13] = Common.valueFormat(rs.getString("bookAmount"));
		            data[14] = Common.valueFormat(rs.getString("bookUnit"));
		            data[15] = Common.valueFormat(rs.getString("bookValue"));
		            data[16] = rs.getString("newKeepUnitName");
		            data[17] = rs.getString("newPlace");
		            data[18] = Common.valueFormat(rs.getString("scrapValue"));
		            if(rs.getString("limitYear")!=null && !"".equals(rs.getString("limitYear"))){
		            	data[19] = rs.getString("limitYear");
		            }else{
		            	data[19] = rs.getString("otherLimitYear");
		            }
		            if(rs.getString("useYear")!=null && !"".equals(rs.getString("useYear")) && rs.getInt("useYear")!=0){
		            	data[20] = rs.getString("useYear")+"年\n"+rs.getString("useMonth")+"個月";
		            }else{
		            	data[20] = rs.getString("useMonth")+"個月";
		            }
		            data[21] = rs.getString("caseNo")+j;
		            data[22] = Common.get(rs.getString("propertyKind"));
		            data[23] = Common.get(rs.getString("propertyKindName"));
		            data[24] = getKeeperName(rs.getString("enterorg"),rs.getString("ownership"),rs.getString("caseNO"),rs.getString("propertyNO"),rs.getString("serialNO"),"newkeepName");
		            data[25] = getKeeperName(rs.getString("enterorg"),rs.getString("ownership"),rs.getString("caseNO"),rs.getString("propertyNO"),rs.getString("serialNO"),"oldkeepName");
		            data[26] = rs.getString("oldKeepUnitName");
		            
	                rowData.addElement(data);
	             }
    		}//for
            Object[][] data = new Object[0][0];
            data = (Object[][])rowData.toArray(data);
            model.setDataVector(data, columns);
        }//else
    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    
    return model;

}

/*
**取得某資料庫查詢!!單一位位值
**sql_code：sql查詢字串　getFile：回傳欄位名稱
*/
public String getKeeperName(String enterorg ,String ownership ,String caseNo ,String propertyNo, String serialNo,String getFile){
	String reValue="";
	String sql = " select c.keepername as oldkeepName " + "\n"
               + " 		  ,e.keepername as newkeepName " + "\n"
               + " from UNTMP_MoveDetail a ,untmp_keepunit b ,untmp_keeper c " + "\n"
               + "      ,untmp_keepunit d ,untmp_keeper e " + "\n"
               + " where 1=1 " + "\n"
               + " and a.enterorg=b.enterorg and a.oldkeepunit=b.unitno " + "\n"
               + " and a.enterorg=c.enterorg and a.oldkeepunit=c.unitno and a.oldkeeper=c.keeperno " + "\n"
               + " and a.enterorg=d.enterorg and a.newkeepunit=d.unitno " + "\n"
               + " and a.enterorg=e.enterorg and a.newkeepunit=e.unitno and a.newkeeper=e.keeperno " + "\n"
               + " and a.enterOrg = " + Common.sqlChar(enterorg)
               + " and a.ownership = " + Common.sqlChar(ownership)
               + " and a.caseNo = " + Common.sqlChar(caseNo)
               + " and a.propertyNo = " + Common.sqlChar(propertyNo)
               + " and a.serialNo = " + Common.sqlChar(serialNo);
	//System.out.println(sql			);
	Database db = new Database();
	ResultSet rs;
	try{
		rs = db.querySQL(sql);
		if(rs.next()){
			reValue=rs.getString(getFile);
		}
	} catch (Exception e) {
			e.printStackTrace();
	} finally {
			db.closeAll();
	}
		return reValue;
}

}
