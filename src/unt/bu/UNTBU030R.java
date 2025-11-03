

/*
*<br>程式目的：建物明細清冊報表檔 
*<br>程式代號：untbu030r
*<br>撰寫日期：94/12/14
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTBU030R extends SuperBean{

	String q_dataType;
	String q_enterDateS;
	String q_enterDateE;
	String q_balanceDate;
	String q_enterOrg;
	String q_ownership;
	String q_verify;
	String q_propertyKind;
	String q_fundType;
	String q_valuable;
	String q_taxCredit;
    String q_differenceKind;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
    public String getQ_differenceKind() {return checkGet(q_differenceKind); }
    
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);
	}
	public String getQ_dataType(){ return checkGet(q_dataType); }
	public void setQ_dataType(String s){ q_dataType=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getQ_balanceDate(){ return checkGet(q_balanceDate); }
	public void setQ_balanceDate(String s){ q_balanceDate=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_taxCredit(){ return checkGet(q_taxCredit); }
	public void setQ_taxCredit(String s){ q_taxCredit=checkSet(s); }
	
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
	
	UNTCH_Tools ut = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","PROPERTYKIND","PRINTUSER","PRINTDATE","DATATYPE","ENTERDATE","OWNERSHIP","PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","DOORPLATE","SIGNNO","AREA","VALUE","SIGNNO1","OWNERSHIP1","MANAGEORG","USESEPARATE","USEKIND","LIMITYEAR"};
//    										0           		1          2             3           4       5				6				7				8		9		 10		 11		12			13			14				15			16		17            18         19
    	String execSQL="";
    	String EnterDate="";
    		   execSQL = " select distinct b.enterorg ,o.organaname as enterOrgName , o.titlename1, o.titlename2, '結存數' as datatype" + "\n"
    	    					+ "        ,( select k.codename from SYSCA_CODE k where k.codekindid = 'OWA' and k.codeid = b.ownership ) as ownership " + "\n"
    	    					+ "        ,b.propertyno, b.serialno " + "\n"
    			    			+ "        ,( select codename from SYSCA_CODE z where z.codekindid = 'DFK' and z.codeid = a.differencekind) as propertyKind1 " + "\n"
    			    			+ "        ,( select k.codename from SYSCA_CODE k where k.codekindid = 'FUD' and k.codeid = b.fundtype ) as propertyKind2 " + "\n"
    			    			+ "        ,c.propertyname ,b.propertyname1 " + "\n"
    			    			//+ "        ,(select k.signdesc from SYSCA_SIGN k where k.signno = b.doorplate1) as addrname1" + "\n"
    			    			//+ "        ,(select  k.signdesc from SYSCA_SIGN k where k.signno = b.doorplate1 ) as addrname1" + "\n"
    			    			//+ "        ,(select k.addrname from SYSCA_ADDR k where k.addrkind='3' and k.addrid=b.doorplate3) as addrname3" + "\n"
    			    			+ "        ,b.doorplate4 as addrname4 " + "\n"
    			    			+ "        ,b.signno,o.organaname,e.useseparate,e.usekind " + "\n"
    			    			+ "        ,b.holdarea " + "\n"
    		        			+ "        ,b.bookvalue " + "\n"

    			    			//+ "        ,e.ownershipname1 ,ea.manageorgtai as manageOrg "
    			    			//+ "		   ,e.useseparatetai as useSeparate ,e.usekindtai as useKind " + "\n"
    			    			+ " from UNTBU_ADDPROOF a left join SYSCA_ORGAN o on a.enterorg = o.organid, UNTBU_BUILDING b "
    			    			
    			    			+ "  left join SYSPK_PROPERTYCODE c on b.propertyno = c.propertyno " + "\n"

    			    			+ "  left join ( select ea.enterorg ,ea.ownership ,ea.propertyno ,ea.serialno ,ea.signno ,ea.owner" + "\n"
    			    			+ "           ,( select ka.codename from SYSCA_CODE ka where ka.codekindid = 'OWN' and ka.codeid = ea.ownership1 ) as ownershipName1 " + "\n"
    			    			+ "           ,ea.manageorg ,( select oa.organaname from SYSCA_ORGAN oa where oa.organid = ea.manageorg ) as manageorgTAI " + "\n"
    			    			+ "           ,ea.useseparate ,( select q.codename from SYSCA_CODE q where q.codekindid = 'SEP' and q.codeid = ea.useseparate ) as useSeparateTAI " + "\n"
    			    			+ "           ,ea.usekind,( select r.codename from SYSCA_CODE r where r.codekindid = 'UKD' and r.codeid = ea.usekind ) as useKindTAI " + "\n"
    			    			+ "            " + "\n"
    			    			+ "    from UNTBU_BASE ea " + "\n"
    			    			+ "    where 1=1 " + "\n"
    			    			+ "    and ea.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n"
    			    			+ "  ) e " + "\n"
    			    			+ "  on b.enterorg = e.enterorg and b.ownership = e.ownership and b.propertyno = e.propertyno and b.serialno = e.serialno " + "\n"
    			    			
    			    			+ "  where 1=1 " + "\n"
    			    			+ "  and a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno and a.differencekind = b.differencekind and b.datastate = '1' " + "\n"
    			    			+ "  and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n"
    			    			+ "  and a.verify= 'Y' " + "\n"
    			    			+ "  and b.enterdate <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate())) + "\n"
    			    			+ "  and ( b.reducedate is null or b.reducedate > " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate())) + ")"
    			    			+ " ";
    				
    					//+ "  order by enterorg, propertykind, propertyno                                       ";
    			if (!"".equals(getQ_enterOrg())) {
    	    		execSQL += " and b.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
    	    	} else {
    	    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    	    			if ("Y".equals(getIsOrganManager())) {					
    	    				execSQL += " and( b.enterorg = " + Common.sqlChar(getOrganID()) ;
    	    				execSQL += " or organid in (select organid from SYSCA_ORGAN where organsuperior="+ Common.sqlChar(getOrganID())+"))";
    	    			} else {
    	    				execSQL += " and b.enterorg = " + Common.sqlChar(getOrganID()) ;
    	    			}
    	    		}
    	    	}
    			
    			   			
    			if (!"".equals(Common.get(getQ_ownership())))
    				execSQL = execSQL + " and b.ownership = " + util.Common.sqlChar(getQ_ownership());
    			if (!"".equals(Common.get(getQ_propertyKind()))){
    				if ("00".equals(Common.get(getQ_propertyKind())))
    					execSQL = execSQL + " and b.propertykind <'04' ";
    				else 
    					execSQL = execSQL + " and b.propertykind = "+ util.Common.sqlChar(getQ_propertyKind());
    			}
    			if (!"".equals(getQ_differenceKind())){ 
    				execSQL = execSQL + " and a.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
    			}
    			
    			if (!"".equals(Common.get(getQ_fundType()))){
    				execSQL = execSQL + " and b.fundtype = " + util.Common.sqlChar(getQ_fundType());
    			}
    			System.out.println("getQ_valuable()=================="+getQ_valuable());
    			
    			if (!"".equals(Common.get(getQ_valuable())))
    				execSQL = execSQL + " and b.valuable = " + util.Common.sqlChar(getQ_valuable());
    			if (!"".equals(Common.get(getQ_taxCredit())))
    				execSQL = execSQL + " and b.taxcredit = " + util.Common.sqlChar(getQ_taxCredit());
  
    		//execSQL=execSQL+" order by enterOrg,propertyKind,propertyNo ";

    		EnterDate="結存日期:" + Common.MaskDate(getQ_balanceDate());
    		
    	String tansToDouble;
    	String datetime = Datetime.getYYYMMDD();
    	int i;
    	System.out.println(execSQL);
    	
    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTBU030R", this.getObjPath().replaceAll("&gt;", ">"));
    	
    	ResultSet rs = db.querySQL_NoChange(execSQL);
    	Vector rowData = new Vector();
        while(rs.next()){

        	Object[] data = new Object[columns.length];
        	data[0] = Common.get(rs.getString("titlename1")) + "\r\n" + Common.get(rs.getString("titlename2"));
            data[1] = rs.getString("propertyKind1");
            //印表人
            data[2] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
            //印表日期
            data[3] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
            data[4] = rs.getString("datatype");
            data[5] = EnterDate;
            data[6] = rs.getString("ownership");
            
            data[7] = rs.getString("propertyNo").substring(0,7) + "-" + rs.getString("propertyNo").substring(7) + "-" + rs.getString("serialNo");
//            NowProNo=rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyNo");
            data[8] = rs.getString("propertyName");
            data[9] = rs.getString("propertyName1");
            //data[8] = rs.getString("addrname1") + rs.getString("addrname2") + rs.getString("addrname3") + rs.getString("addrname4");
            data[10] = rs.getString("addrname4");
            data[11] = ("".equals(checkGet(rs.getString("signNo"))))?"":rs.getString("signNo");
            if("-".equals(data[11]))
            	data[11]=null;
            
            tansToDouble= rs.getString("holdarea");
            if(tansToDouble!=null)data[12]= new Double(Double.parseDouble(tansToDouble));
            else data[12]= new Double(0.00);
            
            UNTCH_Tools ul = new UNTCH_Tools();
            
            tansToDouble= rs.getString("bookvalue");
            if(tansToDouble!=null)data[13]= new Double(Double.parseDouble(tansToDouble));
            else data[13]=new Double(0);
            
            data[14] = ("".equals(checkGet(rs.getString("signNo"))))?"":getSignDescName(rs.getString("signNo"));
            if("".equals(data[14]))
            	data[14]=null;
            data[15] = rs.getString("ownership");
            data[16] = rs.getString("organaname");
            data[17] = rs.getString("useseparate");
            data[18] = rs.getString("usekind");
            if ("".equals(ul._getLimitYear(rs.getString("propertyNo")))) {
            	data[19] = "";
            } else {
            	data[19] = Double.parseDouble(ul._getLimitYear(rs.getString("propertyNo")));
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


private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signno = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL_NoChange(sql);
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