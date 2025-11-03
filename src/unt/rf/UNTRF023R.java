
/*
*<br>程式目的：公有房屋及附著物拆除改建(報廢)查核報告表查詢檔 
*<br>程式代號：untrf023r
*<br>撰寫日期：95/12/29
*<br>程式作者：Jim Chou
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTRF023R extends SuperBean{


	String enterOrg;
	String ownership;
	String propertyNo1;
	String propertyNo2;
	String serialNo;
	String verify;
	String q_enterOrg;
	String q_ownership;
	String q_propertyNo1;
	String q_propertyNo2;
	String q_serialNo;
	String q_verify;
	String q_propertyNoSName;

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo1(){ return checkGet(propertyNo1); }
	public void setPropertyNo1(String s){ propertyNo1=checkSet(s); }
	public String getPropertyNo2(){ return checkGet(propertyNo2); }
	public void setPropertyNo2(String s){ propertyNo2=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getVerify(){ return checkGet(verify); }
	public void setVerify(String s){ verify=checkSet(s); }

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyNo1(){ return checkGet(q_propertyNo1); }
	public void setQ_propertyNo1(String s){ q_propertyNo1=checkSet(s); }
	public String getQ_propertyNo2(){ return checkGet(q_propertyNo2); }
	public void setQ_propertyNo2(String s){ q_propertyNo2=checkSet(s); }
	public String getQ_serialNo(){ return checkGet(q_serialNo); }
	public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	
	String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	
    String lawBasis;
    public String getLawBasis(){ return checkGet(lawBasis); }
    public void setLawBasis(String s){ lawBasis=checkSet(s); }

    int adjustBookValue = 0;
    
public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	while((q_serialNo!=null) && (!"".equals(q_serialNo)) && (q_serialNo.length()<7)){
    		q_serialNo="0"+q_serialNo;
    	}
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIP","DOORPLATE","BUILDSTYLE","STUFF","BUILDDATE","BOOKVALUE","USERELATION"
    									,"USEUNIT","AREAB","AREA1","AREA2","AREA3","AREA4","AREA5","SUMOFAREA"
    									,"ATTACHMENT","STUFF1","NUM","AREA","SIGNNO1","SIGNNO2","SIGNNO3","SIGNNO4"
    									,"SIGNNO5","FIELD","LANDRULE","HOLDAREA","BOOKVALUE1","OWNERSHIP1","PROPERTYUNIT","lawBasis"
    									,"NO","PropertyName","BV","USEDATES"
    									};
    	
    	String execSQL = " select c.organAName as enterOrgName " + "\n"
    				   + "		  ,substr(a.buildDate,1,3)||'/'||substr(a.buildDate,4,2)||'/'||substr(a.buildDate,6,2) as buildDate " + "\n"
    				   + "		  , a.bookValue , a.useUnit ,a.useUnit1 ,a.measure " + "\n"
    				   + "		  ,(select sp.propertyUnit from SYSPK_PropertyCode sp where sp.propertyNo = a.propertyNo) as propertyUnit " + "\n"
    				   + "		  ,(select a01.signName from SYSCA_SIGN a01 where a01.signno=substr(b.signno,1,1)||'000000') as signno1 " + "\n"
    				   + "		  ,(select a02.signName from SYSCA_SIGN a02 where a02.signno=substr(b.signno,1,3)||'0000') as signno2 " + "\n"
    				   + "		  ,(select a03.signName from SYSCA_SIGN a03 where a03.signno=substr(b.signno,1,7)) as signno3 " + "\n"
    				   + "		  ,substr(b.signNo,8,8) as signNo5 ,b.Area " + "\n"
    				   + "		  ,(select substr(max(b1.bulletinDate||b1.priceUnit),6) from UNTLA_Price b1,UNTLA_Land c1 where c1.dataState='1' and c1.verify='Y' and c1.signNo=( select d1.signNo from UNTRF_Base d1  where d1.enterOrg=a.enterOrg  and d1.ownership=a.ownership  and d1.propertyNo=a.propertyNo and d1.serialNo=a.serialNo and rownum=1)  and c1.enterOrg=b1.enterOrg   and c1.ownership=b1.ownership  and c1.propertyNo=b1.propertyNo  and c1.serialNo=b1.serialNo) as priceUnit " + "\n"
    				   + "		  ,a.propertyKind ,(select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName " + "\n"
    				   + "		  ,a.ownership ,(select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName " + "\n"
    				   + "		  ,substr(a.propertyno,1,7)||'-'||substr(a.propertyno,8,3)||'-'||a.serialno as NO, a.oldserialno " +"\n"
    				   + " 		  ,(select d.propertyname from SYSPK_PropertyCode d where d.propertyno=a.propertyno ) as propertyname  " + "\n"
    				   + "		  ,a.bookvalue as BV, (select d.limityear from SYSPK_PropertyCode d where d.propertyno=a.propertyno) as limityear, a.otherlimityear	"
    				   + " from UNTRF_Attachment a ,UNTRF_Base b,SYSCA_Organ c " + "\n"
    				   + " where a.enterorg = b.enterorg(+) and a.ownership = b.ownership(+) and a.propertyNo = b.propertyNo(+) " + "\n" 
    				   + " and a.enterOrg = c.organID " + "\n"
    				   + " and rownum = 1 " + "\n"
    				   + "";
    		
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL+=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					//sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";		
					execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";					
				} else {
					execSQL+=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
    	    	
    	if (!"".equals(Common.get(getQ_propertyNo1()))){
    		execSQL += " and a.propertyNo = " + util.Common.sqlChar(getQ_propertyNo1());
    	}
    	if (!"".equals(Common.get(getQ_serialNo()))){
    		execSQL += " and a.serialNo = " + util.Common.sqlChar(getQ_serialNo());
    	}
    	if (!"".equals(Common.get(getQ_verify()))){
    		execSQL += " and a.verify = " + util.Common.sqlChar(getQ_verify());
    	}
    	
//System.out.println("-- UNTRF023R SQL --\n"+execSQL);
    	String tansToDouble;
    	String SignNo="";
    	String UseUnitTemp="";

    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[36];
        	data[0] = rs.getString("enterOrgName");
        	data[1] = rs.getString("ownershipName");
        	data[2] = null;
        	data[3] = null;
            data[4] = null;
            data[5] = rs.getString("buildDate");
            tansToDouble= rs.getString("bookValue");
            if(tansToDouble!=null){
            	data[6]= new Double(Double.parseDouble(tansToDouble));
            }else{
            	data[6]=new Double(0);
            }
            
            data[7] = null;
            data[8] = rs.getString("useUnit")==null?null:Common.getORGANANAME(rs.getString("useUnit"));
            UseUnitTemp = rs.getString("useUnit1");
            if("".equals(data[8])||data[8]==null){
            	data[8]=UseUnitTemp;
            }
            data[9] = null;
            data[10] = new Double(rs.getString("measure"));
            data[11] = null;
            data[12] = null;
            data[13] = null;
            data[14] = null;
            data[15] = new Double(rs.getString("measure"));
            data[16] = null;
            data[17] = null;
            data[18] = null;
            data[19] = null;
            data[20] = rs.getString("signNo1");
            data[21] = rs.getString("signNo2");
            SignNo = rs.getString("signNo3");
            if(SignNo!=null &&!"".equals(SignNo)){
                data[22]=SignNo.substring(0,SignNo.indexOf("段")+1);
                if(SignNo.length()>=6){
                	data[23]=SignNo.substring(SignNo.indexOf("段")+1);
                }else{
                	data[23]=null;
                }
            }else{
            	data[22]=null;data[23]=null;
            }            
            if(rs.getString("signNo5")!=null){
            	data[24] = rs.getString("signNo5").substring(0,4)+"-"+rs.getString("signNo5").substring(4,8);
            }else{
            	data[24]=null;
            }
            data[25] = null;
            data[26] = null;
            tansToDouble= rs.getString("area");
            if(tansToDouble!=null){
            	data[27]= new Double(Double.parseDouble(tansToDouble));
            }else{
            	data[27]=new Double(0);
            }
            tansToDouble= rs.getString("priceUnit");
            if(tansToDouble!=null){
            	data[28]= new Double(Double.parseDouble(tansToDouble));
            }else{
            	data[28]=null;
            }
            data[29] = rs.getString("ownershipName");;
            data[30] = rs.getString("propertyUnit");
            data[31] = getLawBasis();
            if (rs.getString("oldserialno") != null && rs.getString("oldserialno") != "" ){
            	data[32] = rs.getString("NO")+" (原"+rs.getString("oldserialno")+")";
            }else{
            	data[32] = rs.getString("NO");
            }
            data[33] = rs.getString("propertyname");
//          data[34] = Integer.parseInt(rs.getString("BV"));
            adjustBookValue = Integer.parseInt(rs.getString("BV"));;
		    data[34] = Common.valueFormat((adjustBookValue)+"");
            if (rs.getString("limityear") != null && rs.getString("limityear") != ""){
            	data[35] = rs.getString("limityear");
            }else{
            	data[35] = rs.getString("otherlimityear");
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


}
