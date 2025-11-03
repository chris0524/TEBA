/*
*<br>程式目的：有價證券財產卡查詢檔 
*<br>程式代號：untvp005r
*<br>撰寫日期：94/11/08
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.vp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import util.*;

public class UNTVP005R extends UNTVP001Q{

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

//	取得UNTVP_Share.serialNo1最大一筆的UNTVP_Share. bookUnitValue
	protected void getShareBookUnitValue(){
		Database db = new Database();	
		UNTVP005R obj = this;
		ResultSet rs;	
		String sql="select substring(cast(max(RIGHT(REPLICATE('0', 3) + cast(a.serialNo1 as NVARCHAR), 3)+ISNULL(a.bookUnitValue,0)) as nvarchar),0,4) as bookUnitValue from UNTVP_Share a" +
				" where 1=1 " + 
				" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
				" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
				" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
				" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
				"";	
		try{
			rs = db.querySQL(sql);
			if (rs.next()){
				if(rs.getString("bookUnitValue")==null){
					obj.setBookUnitValue("0");
				}
				else{
				obj.setBookUnitValue(rs.getString("bookUnitValue"));
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	}

public DefaultTableModel getResultModel() throws Exception{
	UNTVP005R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORG","OWNERSHIP","PROPERTYNO","PROPERTYKIND","SECURITYMEAT",
    									 "PROPERTYNAME","FUNDTYPE","BOOKAMOUNT","PROPERTYNAME1","KEEPUNIT",
    									 "BOOKUNITVALUE","SECURITYNAME","SECURITYITEM","SECURITYTIME","SECURITYADDR",
    									 "SECURITYORG","SECURITY","ENTERDATE","PROOF","ORIGINALNOTE",
    									 "ORIGINALBV","ORIGINALAMOUNT","subReportDataSourceAdjust",
    									 "subReportDataSourceCapital","SERIALNO","subReportDataSourceReduce"};

    	String execSQL="select TOP 100 a.enterOrg as shareEnterOrg, a.ownership as shareOwnership, a.propertyNo as sharePropertyNo, a.serialNo as shareSerialNo, " +
    					" a.serialNo, a.enterOrg, c.ORGANANAME as enterOrgName," +
    					" (select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownership, " +
    					"(select x.codeName from sysca_code x where a.propertyKind = x.codeid and x.codekindid = 'PKD') as propertyKindName, " +
    					" b.propertyName, a.propertyName1," +
    					" a.propertyNo, a.serialNo, ISNULL(a.oldPropertyNo,'') as oldPropertyNo, ISNULL(a.oldSerialNo,'') as oldSerialNo, " +
    					" d.codeName as fundTypeName, ISNULL(a.fundType,'') as fundType, e.unitName as keepUnit, a.securityMeat, " +
    					" a.bookAmount, a.securityName, a.securityItem, ISNULL(a.securityTime,'') as securityTime, a.securityAddr, " +
    					" (select f.ORGANANAME from SYSCA_Organ f where a.securityOrg = f.organID)as securityOrg, " +
    					" a.securityDoc, a.enterDate, a.proofDoc,a.proofyear, a.proofNo, a.originalNote, a.originalBV, a.originalAmount " +
    					" from UNTVP_AddProof a left join SYSCA_Code d on a.fundType=d.codeID and d.codeKindID = 'FUD'"+
    					" left join UNTMP_KeepUnit e on a.enterOrg=e.enterOrg and a.keepUnit=e.unitNo,"+
    					" SYSPK_PropertyCode b, SYSCA_Organ c "+
						" where 1=1 and a.propertyNo = b.propertyNo and a.enterOrg = c.organID and a.verify = 'Y' "+
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
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTVP005R", this.getObjPath().replaceAll("&gt;", ">"));
		
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        int i;
        while(rs.next()){
        	obj.setShareEnterOrg(rs.getString("shareEnterOrg"));
        	obj.setShareOwnership(rs.getString("shareOwnership"));
        	obj.setSharePropertyNo(rs.getString("sharePropertyNo"));
        	obj.setShareSerialNo(rs.getString("shareSerialNo"));
        	
        	Object[] data = new Object[columns.length];
            data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownership");
            //TDCM問題單1797，與keri 討論後移除顯示舊財編財分
            data[2] = rs.getString("propertyNo")+"-"+rs.getString("serialNo");
            data[3] = rs.getString("propertyKindName");
            data[4] = rs.getString("securityMeat");
            data[5] = rs.getString("propertyName");
            if(rs.getString("fundType")!=null){
            	data[6] = rs.getString("fundTypeName");
            }else{
            	data[6] = "";
            }
            data[7] = new Double(rs.getString("bookAmount"));
            data[8] = rs.getString("propertyName1");
            data[9] = rs.getString("keepUnit");
            getShareBookUnitValue();
            data[10] = new Double(obj.getBookUnitValue());
            data[11] = rs.getString("securityName");
            data[12] = rs.getString("securityItem");
            if(rs.getString("securityTime")==null){
            	data[13] = "";
            }else{
            	data[13] = rs.getString("securityTime").substring(0,3)+"年"+rs.getString("securityTime").substring(3,5)+"月";
            }
            data[14] = rs.getString("securityAddr");
            data[15] = rs.getString("securityOrg");
            data[16] = rs.getString("securityDoc");
            //data[17] = rs.getString("enterDate")+"　";
            data[17] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("enterDate"),"1"));
            if(rs.getString("proofDoc") != null ){
    			data[18] = Common.get(rs.getString("proofyear"))+"年"+Common.get(rs.getString("proofDoc"))+"字第"+Common.get(rs.getString("proofNo"))+"號";
    		}else {
    			data[18] = "";
    		}
            data[19] = rs.getString("originalNote");
            data[20] = new Double(rs.getString("originalBV"));
            data[21] = new Double (rs.getString("originalAmount"));
            data[22] = new JRTableModelDataSource(getSubModel((String)data[0]));
            data[23] = new JRTableModelDataSource(getSubMode2((String)data[0]));
            data[24] = rs.getString("enterOrg")+rs.getString("shareOwnership")+rs.getString("propertyNo")+rs.getString("serialNo");
          //TDCM問題單1797，與keri 討論後增加顯示減損紀錄
            data[25] = new JRTableModelDataSource(getSubMode3((String)data[0]));
            for(i=0;i<25;i++)if(data[i]==null)data[i]="";
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
//帳務情形
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTVP005R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "ENTERDATE", "PROOF","BOOKNOTES","OLDBOOKVALUE","ADJUSTBOOKVALUEADD","ADJUSTBOOKVALUE","NEWBOOKVALUE","ADJUSTBOOKAMOUNTADD","ADJUSTBOOKAMOUNT","NEWBOOKAMOUNT"};
    	String execSQL="select a.adjustDate, a.proofDoc, a.proofNo, a.bookNotes, ISNULL(a.oldBookValue,0) as oldBookValue, ISNULL(a.reducebookvalue,0) as adjustBookValue, ISNULL(a.newBookValue,0) as newBookValue, ISNULL(a.reducebookamount,0) as adjustBookAmount, ISNULL(a.newBookAmount,0) as newBookAmount " +
						" from UNTVP_AdjustProof a "+
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
    		Object[] data = new Object[10];
    		//data[0] = rs.getString("adjustDate")+"　";
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("adjustDate"),"1"));
    		if(rs.getString("proofDoc") != null ){
    			data[1] = Common.get(rs.getString("proofDoc"))+"字第"+Common.get(rs.getString("proofNo"))+"號";
    		}else {
    			data[1] = "";
    		}
    		data[2] = rs.getString("bookNotes");
    		data[3] = new Double(rs.getString("oldBookValue"));
    		if(rs.getDouble("adjustBookValue")>0){
    			data[4] = new Double(rs.getString("adjustBookValue"));
    			data[5] = new Double(0);
    		}else if(rs.getDouble("adjustBookValue")<0){
    			data[5] = new Double(Double.parseDouble(rs.getString("adjustBookValue"))*-1);
    			data[4] = new Double(0);
    		}else{
    			data[4] = new Double(0);
    			data[5] = new Double(0);
    		}
    		data[6] = new Double(rs.getString("newBookValue"));
    		if(rs.getDouble("adjustBookAmount")>0){
    			data[7] = new Double(rs.getString("adjustBookAmount"));
    			data[8] = new Double(0);
    		}else if(rs.getDouble("adjustBookValue")<0){
    			data[8] = new Double(Double.parseDouble(rs.getString("adjustBookAmount"))*-1);
    			data[7] = new Double(0);
    		}else{
    			data[7] = new Double(0);
    			data[8] = new Double(0);
    		}
    		data[9] = new Double(rs.getString("newBookAmount"));
    		for(i=0;i<10;i++)if(data[i]==null)data[i]="";
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
//資本額更動紀錄
public DefaultTableModel getSubMode2(String caseCode) throws Exception{
	UNTVP005R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
        String[] columns = new String[] { "CHANGEDATE", "CHANGECASE","CHANGEPIRCE","NOTES1"};
    	String execSQL="select a.changeDate, a.changeCase, a.changePirce, a.notes1" +
						" from UNTVP_Capital a "+
						" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo());
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i;
    	while(rs.next()){
    		Object[] data = new Object[4];
    		//data[0] = rs.getString("changeDate")+"　";
    		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("changeDate"),"1"));
    		data[1] = rs.getString("changeCase");
    		data[2] = new Double(rs.getString("changePirce"));
    		data[3] = rs.getString("notes1");
    		for(i=0;i<4;i++)if(data[i]==null)data[i]="";
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
//TDCM問題單1797，與keri 討論後增加顯示減損紀錄
//減損記錄－多筆
public DefaultTableModel getSubMode3(String caseCode) throws Exception{
	UNTVP005R obj = this;
  DefaultTableModel model = new javax.swing.table.DefaultTableModel();
  Database db = new Database();
  try{
      String[] columns = new String[] { "REDUCEDATE", "PROOFDOC","REDUCENOTES1"};
  	String execSQL="select a.adjustdate ,a.proofdoc ,a.proofno ,a.booknotes" +
						" from UNTVP_REDUCEPROOF a "+
						" where 1=1 "+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyno=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialno=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and a.verify = 'Y' ";
  	ResultSet rs = db.querySQL(execSQL);
  	Vector rowData = new Vector();
  	int i;
  	while(rs.next()){
  		Object[] data = new Object[columns.length];
  		data[0] = Common.MaskDate(new Datetime().changeTaiwanYYMMDD(rs.getString("adjustdate"),"1"));
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