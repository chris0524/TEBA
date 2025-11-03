/*
*<br>程式目的：非消耗品廢品處理清冊查詢檔 
*<br>程式代號：untne023r
*<br>撰寫日期：94/12/12
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.*;

public class UNTNE023R extends UNTNE020Q{

    String q_caseNoS;
	String q_caseNoE;
	String q_note;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_note() {return checkGet(q_note);}
	public void setQ_note(String qNote) {q_note = checkSet(qNote);}

public DefaultTableModel getResultModel() throws Exception{
	//UNTNE023R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    UNTCH_Tools ul = new UNTCH_Tools();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIPNAME","DATE","CASENO1","REDUCEDEALNAME",
    								"DEALDATE","REALIZEVALUE","BUYDATE","PROPERTYNO","PROPERTYNAME",
    								"SPECIFICATION","PROPERTYUNIT","ADJUSTBOOKAMOUNT","OLDBOOKUNIT","ADJUSTBOOKVALUE","Q_NOTE", "TAIL01",
    								"DIFFERENCEKINDNAME","PLACE1"};

    	String execSQL="select a.enterOrg, b.ownership, a.caseNo1, d.organAName as enterOrgName, " +
    					" (select x.codeid from sysca_code x where 1=1 and x.codekindid ='OWA' and b.ownership = x.codeid )as ownershipName, " +
    					" (select e.codeName from SYSCA_Code e where 1=1 and e.codeKindID='RDL' and a.reduceDeal=e.codeID) as reduceDealName, " +
    					" (select e.codeName from SYSCA_Code e where 1=1 and e.codeKindID='DFK' and a.differencekind=e.codeID) as differencekindname, " +
    					" a.dealDate, a.realizeValue," +
    					" (select f.organSName from SYSCA_Organ f where a.shiftOrg=f.organID ) as shiftOrgName, " +
    					" b.buyDate, b.propertyNo, b.serialNo, c.propertyName, (b.specification + '/' + b.nameplate) as nameplate, b.specification, c.propertyUnit, b.otherPropertyUnit, 1 as adjustBookAmount, b.oldBookUnit, b.adjustBookValue " +
    					" , isnull(e.placename,'') AS placename, isnull(b.returnplace,'') AS returnPlace" +
    					" from UNTNE_dealProof a"+
    					" left join UNTNE_dealDetail b on a.enterOrg=b.enterOrg and a.caseNo1=b.caseNo1"+
    					" left join SYSCA_PLACE e on b.enterorg = e.enterorg and b.place1 = e.placeno," +
    					" SYSPK_PropertyCode2 c, SYSCA_Organ d " +
    					" where 1=1 " +
    					" and b.enterOrg = c.enterOrg " +
    					" and b.propertyNo = c.propertyNo " +
    					" and a.enterOrg=d.organID ";
    	String queryCondition = "";

    	if (!"".equals(getQ_enterOrg())) {
    		queryCondition +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";	
					queryCondition += " and( a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					queryCondition += " or organID in (select organID from SYSCA_Organ where organSuperior='"+ Common.sqlChar(getOrganID())+"'))";
				} else {
					queryCondition +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(Common.get(getQ_ownership())))
    		execSQL +=" and b.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_caseNoS()))
			queryCondition +=" and a.caseNo1 >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			queryCondition +=" and a.caseNo1 <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		queryCondition += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		queryCondition += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	
    	if (!"".equals(Common.get(getQ_proofYear()))){
    		queryCondition += " and a.proofyear = "  + Common.sqlChar(getQ_proofYear());
    	}
    	
		if (!"".equals(getQ_proofDoc()))
			queryCondition += " and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			queryCondition += " and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			queryCondition += " and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
    	if (!"".equals(Common.get(getQ_dealDateS())))
    		queryCondition += " and a.dealDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_dealDateS(),"2"));
    	if (!"".equals(Common.get(getQ_dealDateE())))
    		queryCondition += " and a.dealDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_dealDateE(),"2"));
		if (!"".equals(Common.get(getQ_reduceDeal())))
			queryCondition +=" and a.reduceDeal = " + util.Common.sqlChar(getQ_reduceDeal());

		execSQL += queryCondition+ " order by a.caseNo1, b.propertyNo, b.serialNo";
		//System.out.println("execSQL:" + execSQL);
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        //int i;
        while(rs.next()){
        	
        	Object[] data = new Object[columns.length];
            data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownershipName");
            data[2] = Common.MaskDate(Datetime.getYYYMMDD());
           	data[3] = rs.getString("caseNo1")+"　";
            data[4] = rs.getString("reduceDealName");
            data[5] = Common.MaskDate(ul._transToROC_Year(rs.getString("dealDate")));
            if(rs.getString("realizeValue")!=null){
                data[6] = "廢品變賣金額："+Common.valueFormat(rs.getString("realizeValue"));
            }else if(rs.getString("shiftOrgName")!=null){
                data[6] = "廢品轉撥單位："+rs.getString("shiftOrgName");
            }else{
            	data[6] ="";
            }
            data[7] = Common.MaskDate(ul._transToROC_Year(rs.getString("buyDate")));
            data[8] = rs.getString("propertyNo")+"-"+rs.getString("serialNo");
            data[9] = rs.getString("propertyName");
            data[10] = rs.getString("nameplate");
//            if(rs.getString("propertyUnit")!=null){
//                data[11] = rs.getString("propertyUnit");
//            }else{
                data[11] = rs.getString("otherPropertyUnit");
//            }
            data[12] = new Double(rs.getString("adjustBookAmount"));
            if(rs.getString("oldBookUnit")!=null){
                data[13] = new Double(rs.getString("oldBookUnit"));
            }else{
                data[13] = new Double("0");
            }
            data[14] = new Double(rs.getString("adjustBookValue"));
            
            data[15] = getQ_note();
    		//單據備註
            data[16] = ul._queryData("notes")._from("UNTNE_dealProof a ")._with(queryCondition)._toString();
            data[17] = Common.get(rs.getString("differencekindname"));
            data[18] = checkGet(rs.getString("placename")) + "\n" + checkGet(rs.getString("returnPlace")) ;
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