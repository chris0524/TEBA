/*
*<br>程式目的：動產耐用年限已到期明細清冊報表檔 
*<br>程式代號：untmp032r
*<br>撰寫日期：94/11/29
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTMP032R extends SuperBean{
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_dataState;
	String q_verify;
	String q_keepUnit;
	String q_keeper;
	String q_permitReduceDateS;
	String q_permitReduceDateE;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;

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
	public String getQ_permitReduceDateS(){ return checkGet(q_permitReduceDateS); }
	public void setQ_permitReduceDateS(String s){ q_permitReduceDateS=checkSet(s); }
	public String getQ_permitReduceDateE(){ return checkGet(q_permitReduceDateE); }
	public void setQ_permitReduceDateE(String s){ q_permitReduceDateE=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }


	String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 

    String q_propertyNoS;
    String q_propertyNoE;
    String q_propertyNoSName;
    String q_propertyNoEName;

    public String getQ_propertyNoS(){ return checkGet(q_propertyNoS); }
    public void setQ_propertyNoS(String s){ q_propertyNoS=checkSet(s); }
    public String getQ_propertyNoE(){ return checkGet(q_propertyNoE); }
    public void setQ_propertyNoE(String s){ q_propertyNoE=checkSet(s); }
    public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
    public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
    public String getQ_propertyNoEName(){ return checkGet(q_propertyNoEName); }
    public void setQ_propertyNoEName(String s){ q_propertyNoEName=checkSet(s); }


public DefaultTableModel getResultModel() throws Exception{
	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","REDUCEDATE1","KEEPER","PROPERTYNO","PROPERTYNAME","SPECIFICATION","LIMITYEAR","USEYEAR","PROPERTYUNIT","BOOKAMOUNT","BOOKVALUE1","BOOKVALUE","ENTERDATE","BUYDATE","USEUNIT","USENAME","PERMITREDUCEDATE","PROPERTYNO1"};
    	
    	String execSQL="select c.organAName as enterOrgName," +
    			" (select m.unitName||' '||n.keeperName from UNTMP_KeepUnit m,UNTMP_Keeper n where b.enterOrg=n.enterOrg and b.keepUnit=n.unitNo and b.keeper=n.keeperNo and b.enterOrg=m.enterOrg and b.keepUnit=m.unitNo) as keeper," +
    			" substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8)||'-'||b.serialNo as propertyNo," +
    			" d.propertyName," +
    			" a.specification||'/'||a.nameplate as specification," +
    			" d.limitYear,a.otherLimitYear," +
    			" d.propertyUnit," +
    			" b.bookAmount," +
    			" a.originalUnit," +
    			" b.bookValue," +
    			" a.enterDate," +
    			" a.buyDate," +
    			" (select g.unitName from UNTMP_KeepUnit g where b.enterOrg=g.enterOrg and b.useUnit=g.unitNo ) as unitName," +
    			" (select h.keeperName from UNTMP_Keeper h where b.enterOrg=h.enterOrg and b.keepUnit=h.unitNo and b.keeper=h.keeperNo ) as keeperName," +
    			" a.permitReduceDate  ";
    	execSQL += " from UNTMP_Movable a,UNTMP_MovableDetail b,SYSCA_Organ c,SYSPK_PropertyCode d ";
    	execSQL += " where a.enterorg=b.enterorg and  a.ownership=b.ownership and  b.propertyNo=a.propertyNo and a.lotNo=b.lotNo and b.propertyNo=d.propertyNo and a.enterOrg=c.organID(+)";
    	execSQL += " and d.enterOrg in ('000000000A',a.enterOrg) ";
    	execSQL += " and d.propertyType='1' ";
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
    		execSQL += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_propertyNoS()))
		    execSQL += " and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
		    execSQL += " and a.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());
    	if (!"".equals(Common.get(getQ_dataState())))
    		execSQL += " and b.dataState = " + util.Common.sqlChar(getQ_dataState());
    	if (!"".equals(Common.get(getQ_verify())))
    		execSQL += " and a.verify = " + util.Common.sqlChar(getQ_verify());
    	if(!"".equals(Common.get(getQ_keepUnit())))
    		execSQL += " and b.keepUnit = " + util.Common.sqlChar(getQ_keepUnit());
    	if(!"".equals(Common.get(getQ_keeper())))
    		execSQL += " and b.keeper = " + util.Common.sqlChar(getQ_keeper());
    	if (!"".equals(Common.get(getQ_permitReduceDateS())))
    		execSQL += " and a.permitReduceDate >= " + util.Common.sqlChar(getQ_permitReduceDateS());
    	if (!"".equals(Common.get(getQ_permitReduceDateE())))
    		execSQL += " and a.permitReduceDate <= " + util.Common.sqlChar(getQ_permitReduceDateE());
    	if (!"".equals(Common.get(getQ_propertyKind())))
    		execSQL += " and a.propertyKind = " + util.Common.sqlChar(getQ_propertyKind());
    	if (!"".equals(Common.get(getQ_fundType()))){
    		execSQL += " and a.fundType = " + util.Common.sqlChar(getQ_fundType());
    	}
    	if (!"".equals(Common.get(getQ_valuable())))
    		execSQL += " and a.valuable = " + util.Common.sqlChar(getQ_valuable());
    	execSQL += " order by a.enterOrg,keeper,propertyNo,b.serialNo ";
    	//System.out.println(execSQL);
    	String tansToDouble;
    	String limitYeartemp;
    	String PropertyNo1;
    	//double Doubletemp;
    	String OwnerShip="";
    	if("1".equals(Common.get(getQ_ownership()))){
    		OwnerShip="市有";
    	}else OwnerShip="國有";
    	String ReducwDate1="列印區間: "+getQ_permitReduceDateS().substring(0,3)+"/"+getQ_permitReduceDateS().substring(3,5)+"/"+getQ_permitReduceDateS().substring(5,7)+" 至 "+getQ_permitReduceDateE().substring(0,3)+"/"+getQ_permitReduceDateE().substring(3,5)+"/"+getQ_permitReduceDateE().substring(5,7)+" 止";
    	ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[30];
        	data[0] = rs.getString("enterOrgName");
            data[1] = OwnerShip;
            data[2] = ReducwDate1;
            data[3] = rs.getString("keeper");
            PropertyNo1 = rs.getString("propertyNo");
            data[4]=PropertyNo1;
            data[5] = rs.getString("propertyName");
            if(!"/".equals(rs.getString("specification")))
            	data[6] = rs.getString("specification");
            else
            	data[6]="";
            data[7] = rs.getString("limitYear");
        	limitYeartemp=rs.getString("otherLimitYear");
        	if(data[7]==null)data[7]=limitYeartemp;
            
            data[8]=useDate(rs.getString("buyDate"));
            data[9] = rs.getString("propertyUnit");
            
            tansToDouble= rs.getString("bookAmount");
            if(tansToDouble!=null)data[10]= new Double(Double.parseDouble(tansToDouble));
            else data[10]=null;
            
            String OriginalUnit=rs.getString("originalUnit");
            tansToDouble= rs.getString("bookValue");
            if(OriginalUnit!=null&&tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
            else data[11]=null;
            if(tansToDouble!=null)data[12]= new Double(Double.parseDouble(tansToDouble));
            else data[12]=null;
            
            data[13] = Common.MaskDate(rs.getString("enterDate"));
        	data[14] = Common.MaskDate(rs.getString("buyDate"));
        	data[15] = rs.getString("unitName");
        	data[16] = rs.getString("keeperName");
        	data[17] = Common.MaskDate(rs.getString("permitReduceDate"));
        	data[18]=PropertyNo1.substring(0,1);
        	//System.out.println(PropertyNo1);
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

/*
**計算某日期至系統日期之年月
**useDateS：起始日期
*/
public String useDate(String useDateS){
	String reuseDateS="";
	int a=0;
	int b=0;
	if(!"".equals(useDateS) && useDateS != null){
	a=(Integer.parseInt(useDateS.substring(0,3))*12)+Integer.parseInt(useDateS.substring(3,5));
	b=(Integer.parseInt(Datetime.getYYYMMDD().substring(0,3))*12)+Integer.parseInt(Datetime.getYYYMMDD().substring(3,5));
	reuseDateS=String.valueOf((b-a)/12)+"年"+String.valueOf((b-a)%12)+"個月";
	}else{
		reuseDateS="";
	}
	return reuseDateS;
}
}
