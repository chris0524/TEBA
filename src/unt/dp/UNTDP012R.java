/*
*<br>程式目的： 
*<br>程式代號：untbu016r
*<br>撰寫日期：94/12/12
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;
import TDlib_Simple.tools.src.StringTools;

public class UNTDP012R extends UNTDP001F{

	private String enterOrg;
	private String organaName;
	private String deprUnitName;
	private String deprUnit;
	private String deprUnitCB;
	private String deprPark;
	private String q_enterOrg;
	private String q_deprYM;
	private String q_differenceKind;
	private String q_propertyType;
	private String q_propertyNo;
	private String q_propertyNoName;
	private String q_serialNo;
	private String q_order;

	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOrganaName(){ return checkGet(organaName); }
	public void setOrganaName(String s){ organaName=checkSet(s); }
	public String getDeprUnitName(){ return checkGet(deprUnitName); }
	public void setDeprUnitName(String s){ deprUnitName=checkSet(s); }
	public String getDeprUnit(){ return checkGet(deprUnit); }
	public void setDeprUnit(String s){ deprUnit=checkSet(s); }
	public String getDeprUnitCB(){ return checkGet(deprUnitCB); }
	public void setDeprUnitCB(String s){ deprUnitCB=checkSet(s); }
	public String getDeprPark(){ return checkGet(deprPark); }
	public void setDeprPark(String s){ deprPark=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
	public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
	public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
	public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }
	public String getQ_serialNo(){ return checkGet(q_serialNo); }
	public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
	public String getQ_order(){ return checkGet(q_order); }
	public void setQ_order(String s){ q_order=checkSet(s); }
	
	String isOrganManager;
    String isAdminManager;
    String organID;
    private String editID;
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}   


	
public DefaultTableModel getResultModel() throws Exception{
	
	UNTCH_Tools ut = new UNTCH_Tools();
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	StringTools st = new StringTools();
	String execSQL="";
	Map<String,String> propertytypeMap = TCGHCommon.getSysca_Code("PTT");
	Map<String,String> propertynameMap = TCGHCommon.getSyspk_PropertyCode(this.getQ_enterOrg());

    try{
    	String[] columns = new String[] {"head01","head02","head03","head04","head05","head06","detail01","detail02","detail03","detail04",
    			"detail05","ENTERORG"};
								
		    	execSQL=" select a.enterorg,a.deprym,a.differencekind,a.propertytype,a.propertyno,a.deprunitcb," +
		    			" a.serialno,a.serialno1,a.monthDepr,a.scaledMonthDepr,a.newaccumdepr,a.scalednewaccumdepr,a.ownership " + 
		    			" from UNTDP_MONTHDEPR a "+
						" where 1=1 "+	doGetIfCondition()+
		    	        "";
    	
    	
    	       if(this.getQ_order().equals("1")){
		    	execSQL += " order by propertyno,serialno,serialno1,deprym ";
    	       }
    	       else if(this.getQ_order().equals("2")){
    	    	execSQL += " order by deprym,propertyno,serialno,serialno1 ";   
    	       }
   	
        System.out.println("=> " + execSQL);		
		ResultSet rs = db.querySQL(execSQL);
		Vector rowData = new Vector();
        while(rs.next()){
        	     
	    	String datetime = Datetime.getYYYMMDD();
	    	Object[] data = new Object[columns.length];
        	//全銜
            data[0] = ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
            //印表人
            data[1] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
            //印表日期
            data[2] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
            //帳務日期
            if(q_deprYM!=null){
            data[3] = ut._transToROC_Year(rs.getString("deprym")).substring(0, 3)+"年"+ut._transToROC_Year(rs.getString("deprym")).substring(3, 5)+"月";}
            //財產大類
            if(q_propertyType!=null){
            data[4] = rs.getString("propertytype")+"  "+propertytypeMap.get(rs.getString("propertytype"));}            
            //財產編號
            if(q_propertyNo!=null){
            data[5] = rs.getString("propertyno").substring(0, 7)+" - "+rs.getString("propertyno").substring(7,9 )+" - "+Common.formatFrontZero(rs.getString("serialno"),7)+" "+checkGet(propertynameMap.get(rs.getString("propertyno")));}
            //帳務日期-內容
            data[6] = ut._transToROC_Year(rs.getString("deprym")).substring(0, 3)+"/"+ut._transToROC_Year(rs.getString("deprym")).substring(3, 5);
            //財產大類-內容
            data[7] = rs.getString("propertytype")+" "+propertytypeMap.get(rs.getString("propertytype"));
            //財產編號/名稱-內容
            data[8] = rs.getString("propertyno").substring(0, 7)+" - "+rs.getString("propertyno").substring(7,9 )+" - "+Common.formatFrontZero(rs.getString("serialno"),7)+" "+checkGet(propertynameMap.get(rs.getString("propertyno")));
            //本月折舊,累計折舊
            if(rs.getString("deprunitcb").equals("Y")){
            data[9] = rs.getString("scaledmonthdepr"); 
            data[10] = rs.getString("scalednewaccumdepr"); 
            }
            else if(rs.getString("deprunitcb").equals("N")){
            data[9] = rs.getString("monthDepr"); 
        	data[10] = rs.getString("newAccumDepr");
            }
            data[11] = rs.getString("enterorg");
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

private String doGetIfCondition(){
	UNTCH_Tools ut = new UNTCH_Tools();
	String whereSql="";
	if (!"".equals(getQ_enterOrg())) 
		whereSql =" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ; 
	if (!"".equals(getQ_deprYM())) 
		whereSql +=" and a.deprym = " + Common.sqlChar(ut._transToCE_Year(getQ_deprYM())) ;
	if (!"".equals(getQ_differenceKind())) 
		whereSql +=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
	if (!"".equals(getQ_propertyType())) 
		whereSql +=" and a.propertytype = " + Common.sqlChar(getQ_propertyType()) ;
	if (!"".equals(getQ_propertyNo())) 
		whereSql +=" and a.propertyno = " + Common.sqlChar(getQ_propertyNo()) ;
	if (!"".equals(getQ_serialNo())) 
		whereSql +=" and a.serialno = " + Common.sqlChar(Common.formatFrontZero(getQ_serialNo(),7)) ;
	
	return whereSql;
  }	

}