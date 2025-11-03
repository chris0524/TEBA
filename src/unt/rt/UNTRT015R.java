/*
*<br>�{���ت��G�v�Q��ӲM�U�d���� 
*<br>�{���N���Guntrt015r
*<br>���g����G98/9/23
*<br>�{���@�̡GTimtoy.Tsai
*<br>--------------------------------------------------------
*<br>�ק�@�̡@�@�ק����@�@�@�ק�ت�
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.TCGHCommon;

public class UNTRT015R extends UNTRT008Q{
	
	int totalBookValue;
	int totalCount;
	int sumTotalBookValue ;
	int sumTotalCount;
	
	String enterOrg;
	String ownership;
	String propertyNo;
	String serialNo;
	String propertyKind;
	String fundType;
	String q_enterDateS;
	String q_enterDateE;
	String q_dataTypeName;
	String q_enterDateName;
	String q_enterDate;
	
	String propertyName;
	String meat;
	String propertyNoSerialNo;
	String dataState;
	String bookValue;
	String newBookValue;
	String reduceDate;
	String q_differenceKind;
	String editID;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
    public String getQ_differenceKind() {return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);	}

	public String getReduceDate(){ return checkGet(reduceDate); }
	public void setReduceDate(String s){ reduceDate=checkSet(s); }
	public String getBookValue(){ return checkGet(bookValue); }
	public void setBookValue(String s){ bookValue=checkSet(s); }
	
	public String getNewBookValue(){ return checkGet(newBookValue); }
	public void setNewBookValue(String s){ newBookValue=checkSet(s); }
	public String getDataState(){ return checkGet(dataState); }
	public void setDataState(String s){ dataState=checkSet(s); }
	public String getPropertyNoSerialNo(){ return checkGet(propertyNoSerialNo); }
	public void setPropertyNoSerialNo(String s){ propertyNoSerialNo=checkSet(s); }
	public String getPropertyName(){ return checkGet(propertyName); }
	public void setPropertyName(String s){ propertyName=checkSet(s); }
	public String getMeat(){ return checkGet(meat); }
	public void setMeat(String s){ meat=checkSet(s); }
	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getQ_enterDateS(){ return checkGet(q_enterDateS); }
	public void setQ_enterDateS(String s){ q_enterDateS=checkSet(s); }
	public String getQ_enterDateE(){ return checkGet(q_enterDateE); }
	public void setQ_enterDateE(String s){ q_enterDateE=checkSet(s); }
	public String getPropertyKind(){ return checkGet(propertyKind); }
	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
	public String getFundType(){ return checkGet(fundType); }
	public void setFundType(String s){ fundType=checkSet(s); }
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}

public DefaultTableModel getResultModel() throws Exception{
	String execSQL="";
	String  old_change_page = "";
	UNTRT015R obj = this;
	UNTCH_Tools ut = new UNTCH_Tools();
	String datetime = Datetime.getYYYMMDD();
	Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK"); 					//財產區分別名稱
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Vector rowData = new Vector();
    
    Database db = new Database();
    ResultSet rs;
    
    try{

    	String[] columns = new String[] {"ENTERORGNAME","DATE","OWNERSHIP","DIFFERENCEKIND" //0-3
    									,"PROPERTYNO","PROPERTYNAME","ENTERDATE","MEAT","ENTERORGPROPERTYKINDFUNDTYPE" //4-8
    									,"BOOKVALUE","SUMCOUNT","SUMTOTALBOOKVALUE","PRINTPERSON","PRINTDATE"}; //9-13
    									    	
//    	execSQL=" select distinct a.enterOrg, a.ownership, a.propertyKind, nvl(a.fundtype,'') as fundtype, a.propertyNo, a.serialNo, "+"\n"+ 
//    	" (select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName, "+"\n"+ 
//    	" a.propertyKind , "+"\n"+ 
//    	" (select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName, "+"\n"+ 
//    	" a.fundType,  a.meat, a.dataState , "+"\n"+ 
//    	" (select x.propertyName from SYSPK_PropertyCode x where a.propertyno = x.propertyno)as propertyName, "+"\n"+ 
//    	" (select x.organAName from SYSCA_Organ x where x.organId = a.enterorg) as enterOrgName, "+"\n"+ 
//    	" (select x.codeName from SYSCA_Code x where x.codeId = a.fundtype and x.codeKindID = 'FUD') as fundTypeName, nvl(e.bookValue, 0) as bookValue, " +"\n"+
//    	" e.signno, (select x.signdesc from SYSCA_Sign x where substr(e.signno, 1, 7) = x.signno)||substr(e.signno, 8, 4)||'-'||substr(e.signno, 12, 4) as signdesc "+"\n"+ 
//    	" from UNTRT_AddProof a, UNTRT_AddDetail e " +"\n"+ 
//    	" where 1=1 "+"\n"+ 
//    	" and a.verify='Y' "+"\n"+ 
//    	" and a.enterOrg(+) = e.enterOrg and a.ownership(+) = e.ownership and a.propertyNo(+) = e.propertyNo and a.serialNo(+) = e.serialNo "+"\n";
    	
    	execSQL=" select a.enterorg, a.ownership, a.propertykind, isnull(a.fundtype,'') as fundtype, a.propertyno, a.serialno,a.differencekind, "+"\n"+ 
    	" (select x.codename from SYSCA_CODE x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName, "+"\n"+ 
    	" a.propertykind , "+"\n"+ 
    	" (select x.codename from SYSCA_CODE x where a.propertykind = x.codeid and x.codekindid = 'PKD') as propertyKindName, "+"\n"+ 
    	" a.fundtype, a.datastate, a.bookvalue, "+"\n"+ 
    	" isnull((select x.propertyname from SYSPK_PROPERTYCODE x where a.propertyno = x.propertyno),' ') as propertyname, "+"\n"+
    	" a.propertyname1 ,"+"\n"+
    	" (select x.organaname from SYSCA_ORGAN x where x.organid = a.enterorg) as enterOrgName, "+"\n"+ 
    	" (select x.codename from SYSCA_CODE x where x.codeid = a.fundtype and x.codekindid = 'FUD') as fundTypeName " +"\n"+
    	" from UNTRT_ADDPROOF a " +"\n"+ 
    	" where 1=1 and a.verify='Y' and a.datastate = '1' ";
    	
    	if (!"".equals(getQ_enterOrg())) {
    			execSQL +=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    				if ("Y".equals(getIsOrganManager())) {					
    					execSQL += " and ( a.enterorg = "+ Common.sqlChar(getOrganID()) +" or a.enterorg in ( select organid from SYSCA_ORGAN where organsuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";
    				} else {
    					execSQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
    				}
    			}
    	}
    	if (!"".equals(getQ_ownership()))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	if (!"".equals(getQ_propertyKind()))
    		execSQL +=" and a.propertykind = " + Common.sqlChar(getQ_propertyKind()) ;
    	if (!"".equals(getQ_fundType()))
    		execSQL +=" and a.fundtype = " + Common.sqlChar(getQ_fundType()) ;
    	if (!"".equals(getQ_differenceKind())){ 
			execSQL = execSQL + " and a.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
		}
    	execSQL+=" order by a.enterorg, a.ownership, a.propertykind, a.fundtype, a.propertyno, a.serialno ";
    		
    	System.out.println("-- untrt015r getResultModel --\n"+execSQL);
    	
    	//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTRT015R", this.getObjPath().replaceAll("&gt;", ">"));
    	
		rs = db.querySQL_NoChange(execSQL);
		  			
    	while(rs.next()){

    		//For Subreport
        	obj.setEnterOrg(rs.getString("enterOrg"));
        	obj.setOwnership(rs.getString("ownership"));
        	obj.setPropertyNo(rs.getString("propertyNo"));
        	obj.setSerialNo(rs.getString("serialNo"));
        	obj.setPropertyNoSerialNo(rs.getString("propertyNo")+"-"+rs.getString("serialNo"));
        	obj.setPropertyName(rs.getString("propertyName")); 
        	obj.setMeat(rs.getString("propertyName1")); 
        	obj.setBookValue (rs.getString("bookValue"));
        	
        	//�X�p�G�������k�s���P�_
        	if(old_change_page != "" && !old_change_page.equals(rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyKind")+rs.getString("fundType"))){
	        		totalBookValue=0;
	        		totalCount=0;
        	}
        	
    		Object[] data = new Object[14];
    		
    		data[0] = rs.getString("enterOrgName");
    		data[1] = Datetime.getYYY()+"/"+Datetime.getMM()+"/"+Datetime.getYYYMMDD().substring(5,7);
    		data[2] = rs.getString("ownershipName");
    		data[3] = differencekindMap.get(rs.getString("differencekind"));
    		data[4] = rs.getString("propertyno")+"-"+rs.getString("serialno");
    		data[5] = rs.getString("propertyName"); 
    		data[6] = null; //q_enterDate;
    		data[7] = rs.getString("propertyName1"); 
    		data[8] = null;
    		// �X�p
    		totalBookValue = totalBookValue + Integer.parseInt((rs.getString("bookValue")));
    		totalCount++;		
    		data[9] = new Integer(bookValue); 
    		//data[10] = Common.valueFormat(totalCount+"")+" ";
    		//�`�p
    		sumTotalBookValue = sumTotalBookValue + Integer.parseInt((rs.getString("bookValue")));
    		sumTotalCount++;
    		data[11] = new Integer(sumTotalBookValue); 
    		//data[12] = Common.valueFormat(sumTotalCount+"") +" ";
    		//印表人
            data[12] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
          //印表日期
            data[13] = datetime.substring(0,3) + "/" + datetime.substring(3,5) + "/" + datetime.substring(5);

    		old_change_page = (rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyKind")+rs.getString("fundType"));

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


public DefaultTableModel getSubModel(String caseCode) throws Exception{
	
	UNTRT015R obj = this;
	int count_signdesc=0;
	String signDesc = "";
	String[] columns = new String[] { "PROPERTYNO", "PROPERTYNAME","MEAT","BOOKVALUE","signname"};
	Object[] data = new Object[5];    
	
	String execSQL="select e.signno, " +
	" (select x.signdesc from SYSCA_SIGN x where substring(e.signno, 1, 7) = x.signno)+substring(e.signno, 8, 4)+'-'+substring(e.signno, 12, 4) as signdesc " +
	"  from UNTRT_ADDPROOF a" +
	" left join UNTRT_ADDDETAIL e on a.enterOrg = e.enterOrg and a.ownership = e.ownership and a.propertyno = e.propertyno and a.serialNo = e.serialNo " +
	" where 1=1  and a.verify='Y' " +
	" and a.enterOrg=" + util.Common.sqlChar(this.getEnterOrg())+
	" and a.ownership=" + util.Common.sqlChar(this.getOwnership())+
	" and a.propertyNo=" + util.Common.sqlChar(this.getPropertyNo())+
	" and a.serialNo=" + util.Common.sqlChar(this.getSerialNo());
//System.out.println("-- untrt015r getSubModel --\n"+execSQL);	
	Database db = new Database();
	ResultSet rs = db.querySQL(execSQL);
	while (rs.next()){
		count_signdesc++;
		signDesc = rs.getString("signdesc");
	}

	data[0] = obj.getPropertyNoSerialNo();
	data[1] = obj.getPropertyName();
	data[2] = obj.getMeat();
	data[3] = new Integer(obj.getBookValue());
	if(count_signdesc> 1){
		//data[4] = signDesc+ ""+Common.valueFormat(count_signdesc+"")+" ��";
	}else{
		data[4] = signDesc;
	}

	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	Vector rowData = new Vector();
	rowData.addElement(data);
		
	Object[][] finalData = new Object[0][0];
	finalData = (Object[][])rowData.toArray(finalData);
	model.setDataVector(finalData, columns);  
    
    return model;
}

}