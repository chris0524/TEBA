/*
*<br>程式目的：動產廢品處理清冊查詢檔 
*<br>程式代號：untmp024r
*<br>撰寫日期：94/12/6
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTMP024R extends UNTMP021Q{

	private String q_caseNoS;
    private String q_caseNoE;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
	//UNTMP024R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    UNTCH_Tools ul = new UNTCH_Tools();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","DIFFERENCEKIND","DATE","CASENO1","REDUCEDEALNAME",
    									"DEALDATE","REALIZEVALUE","BUYDATE","PROPERTYNO","PROPERTYNAME",
    									"SPECIFICATION","PROPERTYUNIT","ADJUSTBOOKAMOUNT","OLDBOOKUNIT","ADJUSTBOOKVALUE",
    									"PLACE1","PROOF","LIMITYEAR","DIFFERENCEKINDNAME","USEYEAR",
    									"NOTES","KEEPER","SOURCEDATE","REALIZEVALUE1"};

    	String execSQL="select a.enterorg ,b.propertyname1, a.caseno1, d.organaname as enterOrgName,b.useyear,b.usemonth, " +
    					" b.differencekind,b.otherlimityear,b.notes,b.sourcedate, " +
    					" (select e.codename from SYSCA_CODE e where 1=1 and e.codekindid='RDL' and a.reducedeal=e.codeid) as reduceDealName, " +
    					" a.dealdate, a.realizevalue,(select e.codename from SYSCA_CODE e where 1=1 and e.codekindid='DFK' and b.differencekind=e.codeid) as differencekindname, " +
    					" (select f.organsname from SYSCA_ORGAN f where a.shiftorg=f.organid ) as shiftOrgName, " +
    					" b.buydate, b.propertyno, b.serialno, b.realizevalue1, c.propertyname, (b.specification + '/' + b.nameplate) as nameplate, b.specification, c.propertyunit, b.otherpropertyunit, b.adjustbookamount, b.oldbookunit, b.adjustbookvalue " +
    					" , isnull(e.placename,'') AS placename, isnull(b.returnplace,'') AS returnPlace, (a.proofyear+'年'+a.proofdoc+'字'+a.proofno+'號') as proof,   (select keepername from UNTMP_KEEPER k where b.enterorg = k.enterorg and b.keeper = k.keeperno) as keeper " +
    					" from UNTMP_DEALPROOF a, UNTMP_DEALDETAIL b" +
    					" left join SYSCA_PLACE e on b.enterorg = e.enterorg and b.place1 = e.placeno," +
    					" SYSPK_PROPERTYCODE c, SYSCA_ORGAN d " +
    					" where 1=1 " +
    					" and a.enterorg=b.enterorg  and a.caseno1=b.caseno1 " +
    					" and b.propertyno = c.propertyno and c.enterorg in('000000000A',b.enterorg) and c.propertytype='1' " +
    					" and a.enterorg=d.organid "+
    					"";

    	if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					execSQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(Common.get(getQ_ownership())))
    		execSQL +=" and b.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and a.caseno1 >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and a.caseno1<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and a.writedate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and a.writedate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
    	if (!"".equals(getQ_proofYear()))
			execSQL += " and a.proofyear like '%" + getQ_proofYear() + "%'" ;
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and a.proofdoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL += " and a.proofno >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and a.proofno <= " + Common.sqlChar(getQ_proofNoE());		 
    	if (!"".equals(Common.get(getQ_dealDateS())))
    		execSQL += " and a.dealdate >= " + util.Common.sqlChar(getQ_dealDateS());
    	if (!"".equals(Common.get(getQ_dealDateE())))
    		execSQL += " and a.dealdate <= " + util.Common.sqlChar(getQ_dealDateE());
		if (!"".equals(Common.get(getQ_reduceDeal())))
    		execSQL +=" and a.reducedeal = " + util.Common.sqlChar(getQ_reduceDeal());

		execSQL+=" order by a.caseno1, b.propertyno, b.serialno";
//System.out.println("execSQL="+execSQL);
        ResultSet rs = db.querySQL_NoChange(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	
        	Object[] data = new Object[columns.length];
            data[0] = checkGet(rs.getString("enterOrgName"));
            data[1] = checkGet(rs.getString("differencekind"));
            data[2] = Common.MaskDate(Datetime.getYYYMMDD());
           	data[3] = checkGet(rs.getString("caseNo1"))+"　";
            data[4] = checkGet(rs.getString("reduceDealName"));
            data[5] = Common.MaskDate(ul._transToROC_Year(rs.getString("dealDate")));
            if(rs.getString("realizeValue")!=null){
                data[6] = "廢品變賣金額："+Common.valueFormat(rs.getString("realizeValue"));
            }else if(rs.getString("shiftOrgName")!=null){
                data[6] = "廢品轉撥單位："+rs.getString("shiftOrgName");
            }else{
            	data[6] ="";
            }
            //System.out.println("buyDate="+rs.getString("buyDate"));
            data[7] = Common.MaskDate(ul._transToROC_Year(rs.getString("buyDate")));
            //System.out.println("data[7]="+data[7]);
            data[8] = rs.getString("propertyNo")+"-"+rs.getString("serialNo");
            data[9] = checkGet(rs.getString("propertyName"))+"/"+checkGet(rs.getString("propertyName1"));
            data[10] = rs.getString("nameplate");
            if(rs.getString("propertyUnit")!=null){
                data[11] = checkGet(rs.getString("propertyUnit"));
            }else{
                data[11] = checkGet(rs.getString("otherPropertyUnit"));
            }
            data[12] = rs.getString("adjustBookAmount");
            if(rs.getString("oldBookUnit")!=null){
                data[13] = new Double(rs.getString("oldBookUnit"));
            }else{
                data[13] = new Double("0");
            }
            data[14] = new Double(rs.getString("adjustBookValue"));
            
            data[15] = checkGet(rs.getString("placename")) + "\n" + checkGet(rs.getString("returnPlace")) ;
            data[16] = checkGet(rs.getString("proof"));
            int otherLimitMonth = "".equals(Common.get(rs.getString("otherLimitYear")))? 0 : Integer.parseInt(rs.getString("otherLimitYear")); // otherLimitYear以單位月分存取需轉成年
			int year = otherLimitMonth /12;
			int month = otherLimitMonth % 12;
			data[17] = year +"年"+month+"個月";
            data[18] = checkGet(rs.getString("differencekindname"));
            data[19] = checkGet(rs.getString("useyear"))+"年"+checkGet(rs.getString("usemonth"))+"月";
            data[20] = checkGet(rs.getString("notes"));
            data[21] = checkGet(rs.getString("keeper"));
            data[22] = Common.MaskDate(ul._transToROC_Year(rs.getString("sourcedate")));
            data[23] = Common.valueFormat(rs.getString("realizeValue1"));
            //for(i=0;i<15;i++)if(data[i]==null)data[i]="";
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
