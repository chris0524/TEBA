/*
*<br>程式目的：土地改良物明細清冊報表檔 
*<br>程式代號：untrf019r
*<br>撰寫日期：94/12/19
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rf;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import unt.ch.UNTCH_Tools;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTRF019R extends SuperBean{

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
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);	}
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
	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","PROPERTYKIND","DATATYPE","ENTERDATE","OWNERSHIP"
    									,"PROPERTYNO","PROPERTYNAME","PROPERTYNAME1","SIGNNO","PROPERTYUNIT"
    									,"MEASURE","VALUE","MATERIAL","LIMITYEAR"};
    	String execSQL="";
    	String EnterDate="";
 
    		execSQL = " select b.enterorg ,(select x.codename from SYSCA_CODE x where b.ownership = x.codeid and x.codekindid = 'OWA') as ownership ,'' as datatype" + "\n"
    				+ "        ,r.organaname as enterOrgName " + "\n"
    				+ "        ,a.differencekind " + "\n"
    				+ "        ,( select k.codename from SYSCA_CODE k where k.codekindid = 'PKD' and k.codeid = b.propertykind ) + ' ' + " + "\n"
    				+ "         ( select k.codename from SYSCA_CODE k where k.codekindid = 'FUD' and k.codeid = b.fundtype ) as propertyKind " + "\n"
		    		+ "        ,substring(b.propertyno,1,7) +'-' +substring(b.propertyno ,8,LEN(b.propertyno) -7)+'-' +b.serialno as propertyno " + "\n"
		    		+ " 		,(select z.propertyname from SYSPK_PROPERTYCODE z where b.propertyno = z.propertyno and (b.enterorg = z.enterorg or z.enterorg = '000000000A')) as propertyname"
		    		+ "        ,b.propertyname1 ,g.signTAI " + "\n"
//		    		+ " 		,(select z.propertyunit from SYSPK_PROPERTYCODE z where b.propertyno = z.propertyno and (b.enterorg = z.enterorg or z.enterorg = '000000000A')) as propertyunit"
//		    		+ " 		,(select z.material from SYSPK_PROPERTYCODE z where b.propertyno = z.propertyno and (b.enterorg = z.enterorg or z.enterorg = '000000000A')) as material"
		    		+ " 		,b.otherpropertyunit"
		    		+ " 		,b.othermaterial"
		    		+ " 		,(select z.limityear from SYSPK_PROPERTYCODE z where b.propertyno = z.propertyno and (b.enterorg = z.enterorg or z.enterorg = '000000000A')) as limityear"
//		    		+ "        ,isnull(f.newmeasure ,isnull(d.measure ,b.measure)) as measure " + "\n"
//		    		+ "        ,isnull(f.newbookvalue ,isnull(d.bookvalue ,b.bookvalue)) as value " + "\n"
		    		+ "        ,b.measure " + "\n"
		    		+ "        ,b.bookvalue as value " + "\n"
		    		+ " from UNTRF_ADDPROOF a ,UNTRF_ATTACHMENT b" + "\n"
//		    		+ " left join ( select d.enterorg ,d.ownership ,d.propertyno ,d.serialno ,d.bookvalue ,d.measure " + "\n"
//		    		+ "    from UNTRF_REDUCEDETAIL d " + "\n"
//		    		+ "    where 1=1 " + "\n"
//		    		+ "    and d.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n"
//		    		+ "    and d.verify = 'Y' " + "\n"
//		    		+ "  ) d on b.enterorg = d.enterorg and b.ownership = d.ownership and b.propertyno = d.propertyno and b.serialno = d.serialno" + "\n"
//		    		+ "  left join ( select f.enterorg ,f.ownership ,f.propertyno ,f.serialno ,f.newbookvalue ,f.newmeasure " + "\n"
//		    		+ "    		  ,f.oldbookvalue ,f.oldmeasure " + "\n"
//		    		+ "    from UNTRF_ADJUSTDETAIL f " + "\n"
//		    		+ "    where 1=1 " + "\n"
//		    		+ "    and f.verify = 'Y' " + "\n"
//		    		+ "    and f.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n"
//		    		+ "    and f.caseno = ( select max(ff.caseno) from UNTRF_ADJUSTDETAIL ff " + "\n"
//		    		+ "                     where 1=1 " + "\n"
//		    		+ "                     and ff.enterorg = f.enterorg and ff.ownership=f.ownership " + "\n"
//		    		+ "                     and ff.propertyno = f.propertyno and ff.serialno = f.serialno " + "\n"
//		    		+ "                     and ff.verify ='Y' " + "\n"
////		    		+ "                     and ff.adjustdate > " + Common.sqlChar(getQ_balanceDate()) + "\n"
//		    		+ "                   ) " + "\n"
//		    		+ "  ) f " + "\n"
//		    		+ " on b.enterorg = f.enterorg and b.ownership = f.ownership and b.propertyno = f.propertyno and b.serialno = f.serialno " + "\n"
		    		+ " left join ( select g.enterorg ,g.ownership ,g.propertyno ,g.serialno  " + "\n"
		    		+ "           ,s.signdesc +substring(g.signno,8,4)+'-'+substring(g.signno,12,4) + '' + " + "\n"
		    		+ "            ( select count(*) from UNTRF_BASE gc  " + "\n"
		    		+ "              where gc.enterorg = g.enterorg and gc.ownership = g.ownership and gc.propertyno = g.propertyno and gc.serialno = g.serialno " + "\n"
		    		+ "            ) + '' as signTAI " + "\n"
		    		+ "    from UNTRF_BASE g ,SYSCA_SIGN s " + "\n"
		    		+ "    where 1=1 " + "\n"
		    		+ "    and substring(g.signno,0,7) = s.signno " + "\n"
		    		+ "    and g.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n"
		    		+ "    and g.serialno1 = ( select min(gg.serialno1) from UNTRF_BASE gg  " + "\n"
		    		+ "                        where gg.enterorg = g.enterorg and gg.ownership = g.ownership " + "\n"
		    		+ "                        and gg.propertyno = g.propertyno and gg.serialno = g.serialno " + "\n"
		    		+ "                      ) " + "\n"
		    		+ "  ) g " + "\n"
		    		+ " on b.enterorg = g.enterorg and b.ownership = g.ownership and b.propertyno = g.propertyno and b.serialno = g.serialno " + "\n"
		    		+ " ,SYSCA_ORGAN r"
		    		+ " where 1=1 " + "\n"
		    		+ " and a.enterorg = b.enterorg and a.ownership = b.ownership and a.caseno = b.caseno and a.differencekind = b.differencekind" + "\n"		    		
//		    		+ " and b.propertyno = c.propertyno and ( b.enterorg = c.enterorg or c.enterorg = '000000000A' ) " + "\n"		    		
		    		+ " and a.enterorg = r.organid " + "\n"
		    		+ " and b.enterdate <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate())) + "\n"
		    		+ " and ( b.reducedate is null or b.reducedate > " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate())) + " ) " + "\n"
		    		+ " and a.verify = 'Y' and b.datastate = '1' " + "\n"
		    		+ "  " + "\n"
		    		+ " and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;

    		
    		
    			if (!"".equals(Common.get(getQ_ownership())))
    				execSQL = execSQL + " and a.ownership = " + Common.sqlChar(getQ_ownership());
    			if (!"".equals(Common.get(getQ_verify())))
    				execSQL = execSQL + " and a.verify = " + Common.sqlChar(getQ_verify());
    			if (!"".equals(Common.get(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate()))))
    	        	execSQL = execSQL + " and a.enterdate <= " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate()));
    			if (!"".equals(Common.get(getQ_propertyKind()))){
    				if ("00".equals(Common.get(getQ_propertyKind())))
    					execSQL = execSQL + " and b.propertykind < '04' ";
    				else 
    					execSQL = execSQL + " and b.propertykind = "+ Common.sqlChar(getQ_propertyKind());
    			}
    			if (!"".equals(getQ_differenceKind())){ 
    				execSQL = execSQL + " and a.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
    			}
    			
    			if (!"".equals(Common.get(getQ_fundType()))){
    				execSQL = execSQL + " and b.fundtype = " + Common.sqlChar(getQ_fundType());
    			}
    			if (!"".equals(Common.get(getQ_valuable())))
    				execSQL = execSQL + " and b.valuable = " + Common.sqlChar(getQ_valuable());
    			if (!"".equals(Common.get(getQ_taxCredit())))
    				execSQL = execSQL + " and b.taxcredit = " + Common.sqlChar(getQ_taxCredit());

    		execSQL += " order by b.enterorg ,b.ownership ,a.differencekind,b.propertyno ,b.serialno";
    		
        	EnterDate=":" + Common.MaskDate(new UNTCH_Tools()._transToCE_Year(getQ_balanceDate()));
    	
    	Map<String,String> DifferenceKindMap = TCGHCommon.getSysca_Code("DFK");
    	
    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTRF019R", this.getObjPath().replaceAll("&gt;", ">"));
		
    	String tansToDouble;
    	ResultSet rs = db.querySQL_NoChange(execSQL);
    	Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[14];
        	data[0] = rs.getString("enterOrgName");
        	String DIF = rs.getString("differencekind");
            data[1] = DifferenceKindMap.get(DIF).toString();
            data[2] = rs.getString("datatype");
            data[3] = EnterDate;
            data[4] = rs.getString("ownership");
            data[5] = rs.getString("propertyNo");
            data[6] = rs.getString("propertyName");
            data[7] = rs.getString("propertyName1");
            if( "1".equals(getQ_dataType()) || "2".equals(getQ_dataType())){
            	data[8] = rs.getString("signNo");
            	if("".equals(data[8]))data[8]="";
            }else{
            	data[8] = rs.getString("signTAI");
            }
//            data[9] = rs.getString("propertyUnit");
            data[9] = rs.getString("otherpropertyUnit");

            //data[10]= Common.valueFormat(rs.getString("measure"));
        	data[10]= rs.getString("measure");
        	
            tansToDouble= rs.getString("value");
            if(tansToDouble!=null)data[11]= new Double(Double.parseDouble(tansToDouble));
            else data[11]=null;
            
//            data[12] = rs.getString("material");
            data[12] = rs.getString("othermaterial");
            
            // 問題單1626 :使用年限、已使用年限報表顯示方式XX年XX月
            data[13] = rs.getString("limityear") + "年0月";
            
            
            rowData.addElement(data);
            
        }
        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);        
    }catch(Exception x){
    	System.out.println("Exception: ");
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    
    return model;

}


}
