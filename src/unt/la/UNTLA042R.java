

/*
*<br>程式目的：土地所有權狀備查簿報表檔 
*<br>程式代號：untla042r
*<br>撰寫日期：94/12/06
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTLA042R extends SuperBean{

	String q_drawState;
	String q_enterOrg;
	String q_ownership;
	String q_enterDateS;
	String q_enterDateE;
	String q_verify;
	String q_signNo1;
	String q_signNo2;
	String q_dataState;
	String q_nonProof;
	String q_proofVerify;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
	String q_grass;
	String q_drawDateS;
	String q_drawDateE;
	String q_drawName;
	String q_drawCause;


	public String getQ_drawState(){ return checkGet(q_drawState); }
	public void setQ_drawState(String s){ q_drawState=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_signNo1(){ return checkGet(q_signNo1); }
	public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
	public String getQ_signNo2(){ return checkGet(q_signNo2); }
	public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
	public String getQ_dataState(){ return checkGet(q_dataState); }
	public void setQ_dataState(String s){ q_dataState=checkSet(s); }
	public String getQ_nonProof(){ return checkGet(q_nonProof); }
	public void setQ_nonProof(String s){ q_nonProof=checkSet(s); }
	public String getQ_proofVerify(){ return checkGet(q_proofVerify); }
	public void setQ_proofVerify(String s){ q_proofVerify=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
	public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
	public String getQ_grass(){ return checkGet(q_grass); }
	public void setQ_grass(String s){ q_grass=checkSet(s); }
	public String getQ_drawDateS(){ return checkGet(q_drawDateS); }
	public void setQ_drawDateS(String s){ q_drawDateS=checkSet(s); }
	public String getQ_drawDateE(){ return checkGet(q_drawDateE); }
	public void setQ_drawDateE(String s){ q_drawDateE=checkSet(s); }
	public String getQ_drawName(){ return checkGet(q_drawName); }
	public void setQ_drawName(String s){ q_drawName=checkSet(s); }
	public String getQ_drawCause(){ return checkGet(q_drawCause); }
	public void setQ_drawCause(String s){ q_drawCause=checkSet(s); }

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
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","SIGNNO1","SIGNNO2","SIGNNO3","SIGNNO4","AREA","HOLDNUME","HOLDAREA","PROOFDOC","OWNERSHIPDATE","ENTERDATE","DRAWDATE","DRAWNAME","DRAWCAUSE"};
    	
    	String execSQL="select (select c.organAName from SYSCA_Organ c where a.enterOrg=c.organID) as enterOrgName,(case a.ownership when '1' then '市有' when '2' then '國有' else '' end) as ownership," +
    			"a.signNo," +
    			"a.area," +
    			"a.holdNume, a.holdDeno," +
    			"a.holdArea," +
    			"a.proofDoc," +
    			"a.ownershipDate," +
    			"a.enterDate," +
    			"b.drawDate," +
    			"b.drawName," +
    			"(select g.codeName from SYSCA_Code g where  b.drawCause=g.codeID and g.codeKindID = 'DCA') as drawCause ";
    	execSQL=execSQL+" from UNTLA_Land a ";
    	if("1".equals(Common.get(getQ_drawState())))
    		execSQL=execSQL+" ,UNTLA_DrawProof b where a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo ";
    	else 
    		execSQL=execSQL+" left join UNTLA_DrawProof b on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo ";
    	
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
    	if (!"".equals(Common.get(getQ_ownership())))
    		execSQL = execSQL + " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	if (!"".equals(Common.get(getQ_enterDateS())))
    		execSQL = execSQL + " and a.enterDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
    	if (!"".equals(Common.get(getQ_enterDateE())))
    		execSQL = execSQL + " and a.enterDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
    	if (!"".equals(Common.get(getQ_verify())))
    		execSQL = execSQL + " and a.verify = " + util.Common.sqlChar(getQ_verify());
    	if (!"".equals(Common.get(getQ_signNo2())))
    		execSQL = execSQL + " and a.signNo like " + util.Common.sqlChar(getQ_signNo2().substring(0,3)+"%");
    	else if(!"".equals(Common.get(getQ_signNo1())))
    		execSQL = execSQL + " and a.signNo like " + util.Common.sqlChar(getQ_signNo1().substring(0,1)+"%");
    	if (!"".equals(Common.get(getQ_dataState())))
    		execSQL = execSQL + " and a.datastate = " + util.Common.sqlChar(getQ_dataState());
    	if (!"".equals(Common.get(getQ_nonProof())))
    		execSQL = execSQL + " and a.nonProof = " + util.Common.sqlChar(getQ_nonProof());
    	if (!"".equals(Common.get(getQ_proofVerify())))
    		execSQL = execSQL + " and a.proofVerify = " + util.Common.sqlChar(getQ_proofVerify());
    	
    	if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL = execSQL + " and a.propertyKind = " + util.Common.sqlChar(getQ_propertyKind());
    	
    	if (!"".equals(Common.get(getQ_fundType()))){
    		execSQL = execSQL + " and a.fundType = " + util.Common.sqlChar(getQ_fundType());
    	}
    	if (!"".equals(Common.get(getQ_valuable())))
    		execSQL = execSQL + " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
    	if (!"".equals(Common.get(getQ_taxCredit())))
    		execSQL = execSQL + " and a.taxCredit = " + util.Common.sqlChar(getQ_taxCredit());
    	
    	if (!"".equals(Common.get(getQ_grass())))
    		execSQL = execSQL + " and a.grass = " + util.Common.sqlChar(getQ_grass());
    	if (!"".equals(Common.get(getQ_drawDateS())))
    		execSQL = execSQL + " and b.drawDate >= " + util.Common.sqlChar(getQ_drawDateS());
    	if (!"".equals(Common.get(getQ_drawDateE())))
    		execSQL = execSQL + " and b.drawDate <= " + util.Common.sqlChar(getQ_drawDateE());
    	
    	if (!"".equals(Common.get(getQ_drawName())))
    		execSQL = execSQL + " and b.drawName = " + util.Common.sqlChar(getQ_drawName());
    	if (!"".equals(Common.get(getQ_drawCause())))
    		execSQL = execSQL + " and b.drawCause = " + util.Common.sqlChar(getQ_drawCause());
    	
    	
    	execSQL=execSQL+" order by a.ownership,a.signNo ";
    	
    	//System.out.println(execSQL);
    	String tansToDouble;
    	String DateTrn;
    	double Doubletemp;
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	if(!("2".equals(Common.get(getQ_drawState()))&&rs.getString("drawDate")!=null))
        	{
        	Object[] data = new Object[15];
            data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownership");
            data[2] = getSignDescName(rs.getString("signNo").substring(0,3) + "0000");
            data[3] = getSignDescName(rs.getString("signNo").substring(0,7));
            
            
            String SignNo = rs.getString("signNo");
            if(SignNo!=null){
            	data[4]=SignNo.substring(7,11)+"-"+SignNo.substring(11,15);
            	data[5]=SignNo.substring(0,3);
            }
            else {
            	data[4]="";
            	data[5]="";
            }
            data[6]=Common.areaFormat(rs.getString("area"))+"　";
            data[7]=Common.valueFormat(rs.getString("holdNume") + "/" + rs.getString("holdDeno"))+"　";
            data[8]=Common.areaFormat(rs.getString("holdArea"))+"　";
            data[9] = ("".equals(checkGet(rs.getString("proofDoc")))?"免繕狀":rs.getString("proofDoc"));
            DateTrn = rs.getString("ownershipDate");
        	if(DateTrn!=null)data[10]=DateTrn.substring(0,3)+"/"+DateTrn.substring(3,5)+"/"+DateTrn.substring(5,7);
        	else data[10]=null;
        	DateTrn = rs.getString("enterDate");
        	if(DateTrn!=null)data[11]=DateTrn.substring(0,3)+"/"+DateTrn.substring(3,5)+"/"+DateTrn.substring(5,7);
        	else data[11]=null;
        	DateTrn = rs.getString("drawDate");
        	if(DateTrn!=null)data[12]=DateTrn.substring(0,3)+"/"+DateTrn.substring(3,5)+"/"+DateTrn.substring(5,7);
        	else data[12]=null;
            
        	data[13] = rs.getString("drawName");
        	data[14] = rs.getString("drawCause");
            
            rowData.addElement(data);
        	}
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
