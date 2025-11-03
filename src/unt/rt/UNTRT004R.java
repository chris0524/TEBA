/*
*<br>程式目的：權利財產卡查詢檔 
*<br>程式代號：untrt004r
*<br>撰寫日期：94/11/16
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.rt;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import util.*;

public class UNTRT004R extends UNTRT001Q{

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
	String enterDate;
	String bookUnitValue;
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getSharePropertyNo(){ return checkGet(sharePropertyNo); }
	public void setSharePropertyNo(String s){ sharePropertyNo=checkSet(s); }
	public String getShareSerialNo(){ return checkGet(shareSerialNo); }
	public void setShareSerialNo(String s){ shareSerialNo=checkSet(s); }
	public String getEnterDate(){ return checkGet(enterDate); }
	public void setEnterDate(String s){ enterDate=checkSet(s); }
	public String getBookUnitValue(){ return checkGet(bookUnitValue); }
	public void setBookUnitValue(String s){ bookUnitValue=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
	UNTRT004R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORG","OWNERSHIP","PROPERTYNO","PROPERTYKIND","PROPERTYNAME",
    									 "FUNDTYPE","PROPERTYNAME1","BOOKVALUE","REGISTERCAUSE","REGISTERDATE",
    									 "SETPERIOD","NOTES1","COMMONOBLIGEE","SETPERSON","PAYDATE",
    									 "INTEREST","ENTERDATE","PROOF","ORIGINALNOTE","ORIGINALBV",
    									 "subReportDataSourceAddDetail","subReportDataSourceProof",
    									 "subReportDataSourceReduce","SERIALNO"};

    	String execSQL="select TOP 100 a.enterOrg as shareEnterOrg, a.ownership as shareOwnership, a.propertyNo as sharePropertyNo, a.serialNo as shareSerialNo, " +
    					" a.serialNo, a.enterOrg, c.ORGANANAME as enterOrgName," +
    					" (select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownership, " +
    					" a.propertyNo, ISNULL(a.oldPropertyNo,'') as oldPropertyNo, ISNULL(a.oldSerialNo,'') as oldSerialNo, " +
    					" b.propertyName, a.propertyName1," +
    					" (select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKind, " +
    					" d.codeName as fundTypeName, ISNULL(a.fundType,'') as fundType, a.bookValue, " +
    					" e.codeName as registerCause, " +
    					" a.registerDate, a.setPeriod, a.notes1, a.commonObligee, a.setPerson, a.payDate, (ltrim(CONVERT(decimal(15,0),a.interest))) as interest, a.enterDate, a.proofDoc,a.proofyear, a.proofNo, " +
    					" a.originalNote, a.originalBV " +
    					" from UNTRT_AddProof a left join SYSCA_Code e on a.registerCause=e.codeID and e.codeKindID = 'RCA'"+
    					" left join SYSCA_Code d on a.fundType=d.codeID and d.codeKindID = 'FUD',"+
    					" SYSPK_PropertyCode b, SYSCA_Organ c "+
						" where 1=1 and a.propertyNo = b.propertyNo and a.enterOrg = c.organID and a.verify = 'Y'"+
						"";

		if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
				} else {
					execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}
    	if (!"".equals(getQ_ownership()))
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and a.caseNo <= " + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
		if (!"".equals(getQ_propertyNoS()))
			execSQL +=" and a.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
			execSQL +=" and a.propertyNo <= " + Common.sqlChar(getQ_propertyNoE());						
		if (!"".equals(getQ_serialNoS()))
			execSQL +=" and a.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
			execSQL +=" and a.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	if (!"".equals(getQ_proofYear())) 
			execSQL += " and a.proofYear = " + Common.sqlChar(getQ_proofYear());	
    	if (!"".equals(getQ_proofDoc()))
			execSQL +=" and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL +=" and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL +=" and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_enterDateS()))
			execSQL +=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			execSQL +=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_dataState()))
			execSQL +=" and a.dataState = " + Common.sqlChar(getQ_dataState()) ;
		if (!"".equals(getQ_propertyKind()))
			execSQL +=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL +=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
    	execSQL+=" order by a.enterOrg, a.ownership, a.propertyNo, a.serialNo";
    	
		//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTRT004R", this.getObjPath().replaceAll("&gt;", ">"));
		
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        int i;
        while(rs.next()){
        	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
        	obj.setShareOwnership(rs.getString("shareOwnership"));
        	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
        	obj.setShareSerialNo(rs.getString("shareSerialNo"));
        	
        	Object[] data = new Object[columns.length];
            data[0] = rs.getString("enterOrg")+" "+rs.getString("enterOrgName");
            data[1] = rs.getString("ownership");
            //TDCM問題單1797，與keri 討論後移除顯示舊財編財分
            data[2] = rs.getString("propertyNo")+"-"+rs.getString("serialNo");
            data[3] = rs.getString("propertyKind");
            data[4] = rs.getString("propertyName");
            if(rs.getString("fundType")!=null){
            	data[5] = rs.getString("fundTypeName");
            }else{
            	data[5] = "";
            }
            data[6] = rs.getString("propertyName1");
            data[7] = new Integer(rs.getString("bookValue"));
            data[8] = rs.getString("registerCause");
            data[9] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("registerDate"),"1"));
            data[10] = rs.getString("setPeriod");
            data[11] = rs.getString("notes1");
            data[12] = rs.getString("commonObligee");
            data[13] = rs.getString("setPerson");
            data[14] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("payDate"),"1"));
            data[15] = rs.getString("interest");
            //data[16] = rs.getString("enterDate")+"　";
            data[16] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
            data[17] = rs.getString("proofyear")+"年"+rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號";
            data[18] = rs.getString("originalNote");
            data[19] = Common.valueFormat(rs.getString("originalBV"));
            data[20] = new JRTableModelDataSource(getSubModel((String)data[0]));
            data[21] = new JRTableModelDataSource(getSubMode2((String)data[0]));
            data[22] = new JRTableModelDataSource(getSubMode3((String)data[0]));
            data[23] = rs.getString("enterOrg")+rs.getString("shareOwnership")+rs.getString("propertyNo")+rs.getString("serialNo");
            
            for(i=0;i<24;i++)if(data[i]==null)data[i]="";
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

//標的－多筆
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTRT004R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "SIGNNO", "SETAREA","SETOBLIGOR","DOORPLATE","BUILDINGAREA","BUILDINGOWNER","LANDPURPOSE","BUILDINGPURPOSE"};
    	String execSQL="select a.signNo,b.signDesc as signName, (ltrim(CONVERT(decimal(9,2),a.setArea))) as setArea, a.setObligor, a.doorPlate, " +
    					" (ltrim(CONVERT(decimal(9,2),ISNULL(a.buildingArea,'0')))) as buildingArea, a.buildingOwner, a.landPurpose, a.buildingPurpose " +
						" from UNTRT_AddDetail a, SYSCA_SIGN b "+
						" where 1=1 and substring(a.signNo,1,7)= b.signNo "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo());
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i;
    	while(rs.next()){
    		Object[] data = new Object[8];
    		if(!"".equals(checkGet(rs.getString("signNo"))) && rs.getString("signNo").length() == 15){
    		data[0] = rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11)+"地號";
    		}else{
    		data[0] = rs.getString("signName");
    		}
    		data[1] = rs.getString("setArea")+"　";
    		data[2] = rs.getString("setObligor");
    		data[3] = rs.getString("doorPlate");
   			data[4] = rs.getString("buildingArea")+"　";
   			data[5] = rs.getString("buildingOwner");
    		data[6] = rs.getString("landPurpose");
   			data[7] = rs.getString("buildingPurpose");
    		for(i=0;i<8;i++)if(data[i]==null)data[i]="";
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

//帳務資料－多筆
public DefaultTableModel getSubMode2(String caseCode) throws Exception{
	UNTRT004R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "PROOF","PROOFDOC","BOOKNOTES","OLDBOOKVALUE","ADJUSTBOOKVALUE","REDUCEBOOKVALUE","NEWBOOKVALUE"};
    	String execSQL="(" +
    					"select '1' orderBy, a.caseNo, a.adjustDate as enterDate, " +
         				"'財產增減值' as Name," +
         				" a.proofDoc, a.proofNo," +
         				" a.bookNotes," +
         				" a.oldBookValue," +
         				" a.addbookvalue,a.reducebookvalue," +
         				" a.newBookValue " +
         				" from UNTRT_AdjustProof a " +
    					" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and a.verify = 'Y' " +
    					" ) " +
    					"union " +
						"( select '2' orderBy, a.caseNo, a.reduceDate as enterDate," +
 						"'財產減損單' as Name," +
 						" a.proofDoc, a.proofNo," +
         				" a.bookNotes," +
         				" a.bookValue as oldBookValue," +
         				" 0 as addbookvalue," +
         				" a.bookValue as reducebookvalue," +
         				" 0 as newBookValue " +
						" from UNTRT_ReduceProof a " +
						" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and a.verify = 'Y' " +
						" ) ";
    	execSQL += " order by enterDate";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i;
    	while(rs.next()){
    		Object[] data = new Object[8];
    		//data[0] = rs.getString("enterDate")+"　";
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
    		data[1] = checkGet(rs.getString("Name"));
    		data[2] = checkGet(rs.getString("proofDoc")) + "字第" + checkGet(rs.getString("proofNo")) + "號";
    		data[3] = checkGet(rs.getString("bookNotes"));
    		data[4] = Common.valueFormat(rs.getString("oldBookValue"))+"　";
    		if("1".equals(rs.getString("orderBy")))
     		{
     			data[5]= Common.valueFormat(rs.getString("addbookvalue"))+"　";
     			data[6]= Common.valueFormat(rs.getString("reducebookvalue"))+"　";

     		}
     		//減損
     		else if("2".equals(rs.getString("orderBy"))){
     			data[5]="";
     			data[6]= Common.valueFormat(rs.getString("reducebookvalue"))+"　";

     		}
    		

    		data[7] = Common.valueFormat(rs.getString("newBookValue"));
    		for(i=0;i<8;i++)if(data[i]==null)data[i]="";
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

//減損記錄－多筆
public DefaultTableModel getSubMode3(String caseCode) throws Exception{
	UNTRT004R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "REDUCEDATE", "PROOFDOC","REDUCENOTES1"};
    	String execSQL="select a.reduceDate, a.proofDoc, a.proofNo, a.booknotes" +
						" from UNTRT_ReduceProof a "+
						" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and a.verify = 'Y' ";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i;
    	while(rs.next()){
    		Object[] data = new Object[columns.length];
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("reduceDate"),"1"));
    		data[1] = rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號";
    		data[2] = rs.getString("booknotes");
    		for(i=0;i<3;i++)if(data[i]==null)data[i]="";
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size()==0){
			Object[] data = new Object[columns.length];
			
			data[0] = "";
			data[1] = "";
			data[2] = "";
						
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