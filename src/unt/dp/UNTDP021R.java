/*
*<br>程式目的：財產折舊攤提月報表(代管資產) 
*<br>程式代號：untdp021r
*<br>撰寫日期：94/12/12
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.TCGHCommon;
import TDlib_Simple.tools.src.StringTools;

public class UNTDP021R extends UNTDP001F{

	private String enterOrg;
	private String organaName;
	private String deprUnitName;
	private String deprUnit;
	private String deprUnitCB;
	private String deprPark;
	private String q_enterOrg;
	private String q_deprYM;
	private String q_differenceKind;
	private String q_originalDeprPark;
	private String q_groupType; // 統計方式: 
	private String q_reportType;		// 報表類型
	
	public String getQ_reportType() {	return checkGet(q_reportType);}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }		
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
	public String getQ_originalDeprPark(){ return checkGet(q_originalDeprPark); }
	public void setQ_originalDeprPark(String s){ q_originalDeprPark=checkSet(s); }
	public String getQ_groupType() { return checkGet(q_groupType); }
	public void setQ_groupType(String q_groupType) { this.q_groupType = checkSet(q_groupType); }
	
	String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 


    /**
	 * 預設是以 q_groupType : A (部門別+會計科目) 
	 * @return
	 */
	private String getSelectVariable() {
		String defaultText = "a.enterorg as enterorg, x.organaname as organaname, a.deprpark, p.deprparkname as deprparkname , a.deprunit as deprunit, z.deprunitname as deprunitname, a.depraccounts, f.depraccountsname,";
		if ("B".equals(this.getQ_groupType())) { // groupType B (部門別+部門別單位+會計科目)
			defaultText += "a.deprunit1,";
		}
		return defaultText;
	}
	
	/**
	 * 預設是以 q_groupType : A (部門別+會計科目) 
	 * @return
	 */
	private String getGroupbyVariable() {
		String defaultText = " a.enterorg, x.organaname, a.deprpark, p.deprparkname, a.deprunit, z.deprunitname, a.depraccounts, f.depraccountsname";
		if ("B".equals(this.getQ_groupType())) { // groupType B (部門別+部門別單位+會計科目)
			defaultText += ", a.deprunit1";
		}
		return defaultText;
	}
    
	
public DefaultTableModel getResultModel() throws Exception{
	UNTCH_Tools ul = new UNTCH_Tools();
	UNTDP021R obj = this;
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Database db = new Database();
	StringTools st = new StringTools();
	Map<String,String> deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());		//部門別單位名稱
	
	try{
		String[] columns = new String[] {"head01","head02","head03","head04","detail01","detail02","detail03","detail04","detail05","detail06","detail07"};
		if ("B".equals(this.getQ_groupType())) { // groupType B (部門別+部門別單位+會計科目)  多顯示部門別單位
			int oriLength = columns.length;
			columns = Arrays.copyOf(columns, oriLength + 1);
			columns[oriLength] = "detail08";
		}			
		String tempDeprpark="";
		if(getQ_originalDeprPark().equals(null) || getQ_originalDeprPark().equals("")){
		}else{
			tempDeprpark=" and a.deprpark = " + Common.sqlChar(getQ_originalDeprPark()) ;
		}
		String execSQL=" select DISTINCT  " + this.getSelectVariable() + 
						" SUM(CASE WHEN a.propertytype = '2' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d1," +
						" SUM(CASE WHEN a.propertytype = '3' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d2," +
						" SUM(CASE WHEN a.propertytype = '4' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d3," +
						" SUM(CASE WHEN a.propertytype = '5' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d4," +
						" SUM(CASE WHEN a.propertytype = '6' THEN CASE WHEN a.deprunitcb = 'Y' THEN ISNULL(a.scaledmonthdepr, 0) ELSE ISNULL(a.monthdepr, 0) END ELSE 0 end) AS d5 " +
//    				   " (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='2' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='111'"+tempDeprpark+") + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='2' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'N' and differencekind='111'"+tempDeprpark+")  as d1," +
//    				   " (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='3' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='111'"+tempDeprpark+") + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='3' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'N' and differencekind='111'"+tempDeprpark+")  as d2," +
//    				   " (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='4' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='111'"+tempDeprpark+") + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='4' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'N' and differencekind='111'"+tempDeprpark+")  as d3," +
//    				   " (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='5' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='111'"+tempDeprpark+") + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='5' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'N' and differencekind='111'"+tempDeprpark+")  as d4," +
//       				   " (select ISNULL(SUM(ISNULL(scaledmonthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='6' and enterorg=a.enterorg and deprunit = a.deprunit and  deprunitcb = 'Y' and differencekind='111'"+tempDeprpark+") + (select ISNULL(SUM(ISNULL(monthdepr,0)),0)  from UNTDP_MONTHDEPR where propertytype='6' and enterorg=a.enterorg and deprunit = a.deprunit and deprunitcb = 'N' and differencekind='111'"+tempDeprpark+")  as d5 " +
    				   " from UNTDP_MONTHDEPR a "+
       				   " left join SYSCA_ORGAN x on a.enterorg = x.organid "+
    				   " left join SYSCA_DEPRUNIT z on  a.enterorg = z.enterorg and a.deprunit=z.deprunitno "+
    				   " left join SYSCA_DEPRPARK p on a.enterorg=p.enterorg and a.deprPark=p.deprParkNo "+
    				   " left join SYSCA_DEPRACCOUNTS f on a.enterorg = f.enterorg and a.depraccounts = f.depraccountsno "+
						" where 1=1 " +	
						"";

    	if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";	
					execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
				} else {
					execSQL += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
		if (!"".equals(getQ_deprYM())) 
			execSQL +=" and a.deprym = " + Common.sqlChar(ul._transToCE_Year(getQ_deprYM())) ;
		if (!"".equals(getQ_differenceKind())) 
			execSQL +=" and a.differencekind = " + Common.sqlChar(getQ_differenceKind()) ;
		if (!"".equals(getQ_originalDeprPark())) 
			execSQL +=" and a.deprpark = " + Common.sqlChar(getQ_originalDeprPark()) ;
		
		execSQL+=" GROUP BY " + this.getGroupbyVariable();
		execSQL += " order by enterorg,deprpark,deprunit";
		if ("B".equals(this.getQ_groupType())) {
			execSQL += ",deprunit1";
		}
		execSQL += ",depraccounts";
		
//System.out.println("-- untbu013r getResultModel --\n"+execSQL);		
   	
System.out.println("=> " + execSQL);		
		ResultSet rs = db.querySQL(execSQL);
		Vector rowData = new Vector();
        String CauseTemp="";
        Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR,Integer.parseInt(ul._transToCE_Year(q_deprYM).substring(0, 4)));
    	cal.set(Calendar.MONTH, Integer.parseInt(ul._transToCE_Year(q_deprYM).substring(4, 6)));
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	cal.add(Calendar.DAY_OF_MONTH, -1); 
    	int lastDate = cal.get(Calendar.DAY_OF_MONTH);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	int firstDate = cal.get(Calendar.DAY_OF_MONTH);
        //int i;
        while(rs.next()){
        	obj.setEnterOrg(checkGet(rs.getString("enterOrg")));
            obj.setOrganaName(checkGet(rs.getString("organaName")));
            obj.setDeprUnitName(checkGet(rs.getString("deprUnitName")));
            obj.setDeprUnit(checkGet(rs.getString("deprUnit")));
            obj.setDeprPark(checkGet(rs.getString("deprParkName")));


        	
        	Object[] data = new Object[columns.length];
            data[0] = checkGet(rs.getString("organaName"));
            data[1] = "  中華民國"+getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+firstDate+"  日起至   ";
            data[2] = getQ_deprYM().substring(0, 3)+" 年 "+getQ_deprYM().substring(3, 5)+" 月 "+lastDate+" 日止";
            data[3] = checkGet(rs.getString("deprParkName"));
            data[4] = checkGet(rs.getString("deprUnitName"));
            data[5] = Common.get(rs.getString("depraccounts")) + Common.get(rs.getString("depraccountsname"));
            data[6] = rs.getInt("d1");
            data[7] = rs.getInt("d2");
            data[8] = rs.getInt("d3");
            data[9] = rs.getInt("d4");
            data[10] = rs.getInt("d5");
            if ("B".equals(this.getQ_groupType())) {
            	data[11] = Common.get(deprunit1Map.get(rs.getString("deprunit1")));
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


//年度格式
public static String yearFormat(String year){
    String formatString = new String();
    DecimalFormat df = new DecimalFormat("000");
    if(year!=null && !year.equals("")){
        try{
            formatString = df.format(Double.parseDouble(year));
        }catch (NumberFormatException e) {
            formatString =year;
     }
    }else{
        formatString =year;
    }
    return formatString;
  }  
}