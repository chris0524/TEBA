/*
*<br>程式目的：權利增減表查詢檔 
*<br>程式代號：untrt016r
*<br>撰寫日期：94/11/23
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

public class UNTRT016R extends UNTRT001Q{

	String shareEnterOrg;
	String shareOwnership;
	String sharePropertyNo;
	String shareSerialNo;
	String propertyName;
	
	public String getShareEnterOrg(){ return checkGet(shareEnterOrg); }
	public void setShareEnterOrg(String s){ shareEnterOrg=checkSet(s); }
	public String getShareOwnership(){ return checkGet(shareOwnership); }
	public void setShareOwnership(String s){ shareOwnership=checkSet(s); }
	public String getSharePropertyNo(){ return checkGet(sharePropertyNo); }
	public void setSharePropertyNo(String s){ sharePropertyNo=checkSet(s); }
	public String getShareSerialNo(){ return checkGet(shareSerialNo); }
	public void setShareSerialNo(String s){ shareSerialNo=checkSet(s); }
	public String getPropertyName(){ return checkGet(propertyName); }
	public void setPropertyName(String s){ propertyName=checkSet(s); }

	String sumAddHoldArea;
	String sumAddCount;
	String sumAddBookValue;
	String sumHoldArea;
	String sumCount;
	String sumBookValue;
	double addHoldAreaSum;
	int addCountSum;
	int addBookValueSum;
	double holdAreaSum;
	int countSum;
	int bookValueSum;
	
	public String getSumAddHoldArea(){ return checkGet(sumAddHoldArea); }
	public void setSumAddHoldArea(String s){ sumAddHoldArea=checkSet(s); }
	public String getSumAddCount(){ return checkGet(sumAddCount); }
	public void setSumAddCount(String s){ sumAddCount=checkSet(s); }
	public String getSumAddBookValue(){ return checkGet(sumAddBookValue); }
	public void setSumAddBookValue(String s){ sumAddBookValue=checkSet(s); }
	public String getSumHoldArea(){ return checkGet(sumHoldArea); }
	public void setSumHoldArea(String s){ sumHoldArea=checkSet(s); }
	public String getSumCount(){ return checkGet(sumCount); }
	public void setSumCount(String s){ sumCount=checkSet(s); }
	public String getSumBookValue(){ return checkGet(sumBookValue); }
	public void setSumBookValue(String s){ sumBookValue=checkSet(s); }
	
	String enterOrg;
	String propertyKind;
	String fundType;
	String oldEnterOrg;
	String oldPropertyKind;
	String oldFundType;

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getPropertyKind(){ return checkGet(propertyKind); }
	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
	public String getFundType(){ return checkGet(fundType); }
	public void setFundType(String s){ fundType=checkSet(s); }
	public String getOldEnterOrg(){ return checkGet(oldEnterOrg); }
	public void setOldEnterOrg(String s){ oldEnterOrg=checkSet(s); }
	public String getOldPropertyKind(){ return checkGet(oldPropertyKind); }
	public void setOldPropertyKind(String s){ oldPropertyKind=checkSet(s); }
	public String getOldFundType(){ return checkGet(oldFundType); }
	public void setOldFundType(String s){ oldFundType=checkSet(s); }

	String oldEnterOrgAdd;
	String oldPropertyKindAdd;
	String oldFundTypeAdd;
	String checkModel;

	public String getCheckModel(){ return checkGet(checkModel); }
	public void setCheckModel(String s){ checkModel=checkSet(s); }
	public String getOldEnterOrgAdd(){ return checkGet(oldEnterOrgAdd); }
	public void setOldEnterOrgAdd(String s){ oldEnterOrgAdd=checkSet(s); }
	public String getOldPropertyKindAdd(){ return checkGet(oldPropertyKindAdd); }
	public void setOldPropertyKindAdd(String s){ oldPropertyKindAdd=checkSet(s); }
	public String getOldFundTypeAdd(){ return checkGet(oldFundTypeAdd); }
	public void setOldFundTypeAdd(String s){ oldFundTypeAdd=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
	UNTRT016R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"ENTERORGNAME","OWNERSHIPNAME","PROPERTYKINDFUNDTYPE","ENTERDATE","DATE","subReportDataSourceReduceProof","subReportDataSourceAdjustProof","sumAddHoldArea","sumAddCount","sumAddBookValue","sumHoldArea","sumCount","sumBookValue","enterOrgPropertyKindFundType"};

    	String execSQL="select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, " +
    					" c.ORGANANAME as enterOrgName, decode(a.ownership,'1','市有','2','國有','') ownershipName, " +
    					" b.propertyName, a.propertyKind, decode(a.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKindName, " +
    					" d.codeName as fundTypeName, nvl(a.fundType,'') as fundType " +
    					" from UNTRT_AddProof a,SYSPK_PropertyCode b, SYSCA_Organ c,SYSCA_Code d "+
						" where 1=1 and a.verify='Y' and a.propertyNo = b.propertyNo and a.enterOrg = c.organID and a.fundType=d.codeID(+) and d.codeKindID(+) = 'FUD' "+
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
		if (!"".equals(getQ_propertyKind()))
			execSQL +=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
			execSQL +=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
    	if (!"".equals(getQ_enterDateS()))
			execSQL +=" and a.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
			execSQL +=" and a.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
    	execSQL+=" order by a.enterOrg, a.propertyKind, a.fundType, a.enterDate, a.propertyNo, a.serialNo";
    	//System.out.println(execSQL);
        ResultSet rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
        while(rs.next()){
			obj.setPropertyKind(rs.getString("propertyKind"));
			obj.setFundType(rs.getString("fundType"));
			obj.setEnterOrg(rs.getString("enterOrg"));
        	obj.setShareEnterOrg(rs.getString("enterOrg"));
        	obj.setShareOwnership(rs.getString("ownership"));
        	obj.setSharePropertyNo(rs.getString("propertyNo"));
        	obj.setShareSerialNo(rs.getString("serialNo"));
        	obj.setPropertyName(rs.getString("propertyName"));
        	
        	Object[] data = new Object[14];
            data[0] = rs.getString("enterOrgName");
            data[1] = rs.getString("ownershipName");
            if(rs.getString("fundTypeName")!=null){
                data[2] = rs.getString("propertyKindName")+"　"+rs.getString("fundTypeName");
            }else{
                data[2] = rs.getString("propertyKindName");
            }
            data[3] = "中華民國"+Integer.parseInt(getQ_enterDateS().substring(0,3))+"年"+Integer.parseInt(getQ_enterDateS().substring(3,5))+"月"+Integer.parseInt(getQ_enterDateS().substring(5,7))+"日起至"+Integer.parseInt(getQ_enterDateE().substring(0,3))+"年"+Integer.parseInt(getQ_enterDateE().substring(3,5))+"月"+Integer.parseInt(getQ_enterDateE().substring(5,7))+"日";
            data[4] = Common.MaskDate(Datetime.getYYYMMDD());
            data[5] = new JRTableModelDataSource(getSubModel((String)data[0]));
            data[6] = new JRTableModelDataSource(getSubMode2((String)data[0]));
            data[7] = Common.areaFormat(obj.getSumAddHoldArea())+"　";
            data[8] = Common.valueFormat(obj.getSumAddCount())+"筆";
            data[9] = Common.valueFormat(obj.getSumAddBookValue())+"　"; 
            data[10] = Common.areaFormat(obj.getSumHoldArea())+"　";
            data[11] = Common.valueFormat(obj.getSumCount())+"筆";
            data[12] = Common.valueFormat(obj.getSumBookValue())+"　"; 
            data[13] = rs.getString("enterOrg")+rs.getString("propertyKind")+rs.getString("fundType");
            //for(i=0;i<14;i++)if(data[i]==null)data[i]="";
			if(obj.getCheckModel().equals("true")){
				rowData.addElement(data);
			}
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

//減少數
public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTRT016R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	//合計價值換頁的判斷
	if(!(obj.getEnterOrg()).equals(obj.getOldEnterOrg()) || !(obj.getPropertyKind()).equals(obj.getOldPropertyKind()) || !(obj.getFundType()).equals(obj.getOldFundType())){
		holdAreaSum=0.00;
		countSum=0;
		bookValueSum=0;
		
		obj.setSumHoldArea(holdAreaSum+"");
		obj.setSumCount(countSum+"");
		obj.setSumBookValue(bookValueSum+"");
		obj.setCheckModel("false");
	}
	obj.setOldEnterOrg(obj.getEnterOrg());
	obj.setOldPropertyKind(obj.getPropertyKind());
	obj.setOldFundType(obj.getFundType());
    
    try{
        String[] columns = new String[] { "PROPERTYNO", "PROPERTYNAME","SIGNNO","SETOBLIGOR","AREA","HOLDNUME_HOLDDENO","HOLDAREA","BUYDATE","SETPERIOD","BOOKVALUE"};
    	String execSQL="(select distinct c.reduceDate as enterDate, a.signNo,b.signDesc as signName, a.setObligor, a.area, a.holdNume, a.holdDeno, a.holdArea, c.buyDate, c.setPeriod, " +
    					" a.bookValue, c.reduceBookValue, concat('減','少') as bookValueName " +
 						" from UNTRT_ReduceDetail a, SYSCA_SIGN b, UNTRT_ReduceProof c "+
						" where 1=1 and substr(a.signNo,1,7)= b.signNo " +
						" and c.enterOrg=a.enterOrg(+) and c.propertyNo=a.propertyNo(+) and c.ownership=a.ownership(+) and c.serialNo=a.serialNo(+)"+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and c.reduceDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"))+
						" and c.reduceDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"))+
						")union"+
						"(select distinct c.adjustDate as enterDate, a.signNo,b.signDesc as signName, a.setObligor, a.area, a.holdNume, a.holdDeno, a.holdArea, c.buyDate, c.setPeriod, " +
    					" a.adjustBookValue as bookValue, c.adjustBookValue as reduceBookValue, concat('減','值') as bookValueName " +
 						" from UNTRT_AdjustDetail a, SYSCA_SIGN b, UNTRT_AdjustProof c "+
						" where 1=1 and substr(a.signNo,1,7)= b.signNo " +
						" and a.adjustType='2'" +
						" and c.caseNo=a.caseNo(+) and c.enterOrg=a.enterOrg(+) and c.propertyNo=a.propertyNo(+) and c.ownership=a.ownership(+) and c.serialNo=a.serialNo(+)"+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getShareOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getSharePropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getShareSerialNo())+
						" and c.adjustDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"))+
						" and c.adjustDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"))+
						") order by enterDate";
    	
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	while(rs.next()){
    		Object[] data = new Object[10];
    		data[0] = obj.getSharePropertyNo()+"-"+obj.getShareSerialNo();
    		data[1] = obj.getPropertyName();
    		data[2] = rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11)+"地號\n";
    		data[3] = rs.getString("setObligor");
    		data[4] = Common.areaFormat(rs.getString("area"))+"　";
    		data[5] = rs.getString("holdNume")+"/"+rs.getString("holdDeno");
   			data[6] = Common.areaFormat(rs.getString("holdArea"))+"　";
   			data[7] = Common.MaskDate(rs.getString("buyDate"));
    		data[8] = rs.getString("setPeriod");
    		if(rs.getString("signNo")==null){
    		    data[9] = rs.getString("bookValueName")+"\n"+Common.valueFormat(rs.getString("reduceBookValue"))+"　";
    		    //合計
    		    bookValueSum += Integer.parseInt(rs.getString("reduceBookValue"));
    		}else{
    			data[9] = rs.getString("bookValueName")+"\n"+Common.valueFormat(rs.getString("bookValue"))+"　";
    			//合計
    			bookValueSum += Integer.parseInt(rs.getString("bookValue"));
    		}
			//合計
			holdAreaSum += Double.parseDouble(rs.getString("holdArea"));
			countSum ++;
			obj.setSumHoldArea(holdAreaSum+"");
			obj.setSumCount(countSum+"");
			obj.setSumBookValue(bookValueSum+"");
			obj.setCheckModel("true");
    		//for(i=0;i<10;i++)if(data[i]==null)data[i]="";
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

//增加數
public DefaultTableModel getSubMode2(String caseCode) throws Exception{
	UNTRT016R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	//合計價值換頁的判斷
	if(!(obj.getEnterOrg()).equals(obj.getOldEnterOrgAdd()) || !(obj.getPropertyKind()).equals(obj.getOldPropertyKindAdd()) || !(obj.getFundType()).equals(obj.getOldFundTypeAdd())){
		addHoldAreaSum=0.00;
		addCountSum=0;
		addBookValueSum=0;
		
		obj.setSumAddHoldArea(addHoldAreaSum+"");
		obj.setSumAddCount(addCountSum+"");
		obj.setSumAddBookValue(addBookValueSum+"");
		obj.setCheckModel("false");
	}
	obj.setOldEnterOrgAdd(obj.getEnterOrg());
	obj.setOldPropertyKindAdd(obj.getPropertyKind());
	obj.setOldFundTypeAdd(obj.getFundType());
    
    try{
        String[] columns = new String[] { "PROPERTYNO", "PROPERTYNAME","SIGNNO","SETOBLIGOR","AREA","HOLDNUME_HOLDDENO","HOLDAREA","BUYDATE","SETPERIOD","BOOKVALUE"};
    	String execSQL="(select distinct c.enterDate, a.signNo,b.signDesc as signName, a.setObligor, a.area, a.holdNume, a.holdDeno, a.holdArea, c.buyDate, c.setPeriod, " +
    					" a.originalBV as bookValue, c.originalBV as adjustBookValue, " +
    					" a.propertyNo, d.propertyName, a.serialNo, concat('新','增') as bookValueName " +
 						" from UNTRT_AddDetail a, SYSCA_SIGN b, UNTRT_AddProof c, SYSPK_PropertyCode d "+
						" where 1=1 and a.verify='Y' and substr(a.signNo,1,7)= b.signNo and a.propertyNo = d.propertyNo " +
						" and c.enterOrg=a.enterOrg(+) and c.propertyNo=a.propertyNo(+) and c.ownership=a.ownership(+) and c.serialNo=a.serialNo(+)"+
						" and c.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and c.propertyKind=" + util.Common.sqlChar(obj.getPropertyKind());
						if(!obj.getFundType().equals("")){
						    execSQL += " and c.fundType=" + util.Common.sqlChar(obj.getFundType());
						}
    			execSQL += " and c.enterDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"))+
						" and c.enterDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"))+
						")union"+
						"(select distinct c.adjustDate as enterDate, a.signNo,b.signDesc as signName, a.setObligor, a.area, a.holdNume, a.holdDeno, a.holdArea, c.buyDate, c.setPeriod, " +
    					" a.adjustBookValue as bookValue, c.adjustBookValue, " +
    					" a.propertyNo, d.propertyName, a.serialNo, concat('增','值') as bookValueName " +
 						" from UNTRT_AdjustDetail a, SYSCA_SIGN b, UNTRT_AdjustProof c, SYSPK_PropertyCode d "+
						" where 1=1 and substr(a.signNo,1,7)= b.signNo and a.propertyNo = d.propertyNo " +
						" and a.adjustType='1'" +
						" and c.caseNo=a.caseNo(+) and c.enterOrg=a.enterOrg(+) and c.propertyNo=a.propertyNo(+) and c.ownership=a.ownership(+) and c.serialNo=a.serialNo(+)"+
						" and c.enterOrg=" + util.Common.sqlChar(obj.getShareEnterOrg())+
						" and c.propertyKind=" + util.Common.sqlChar(obj.getPropertyKind());
						if(!obj.getFundType().equals("")){
						    execSQL += " and c.fundType=" + util.Common.sqlChar(obj.getFundType());
						}
    			execSQL += " and c.adjustDate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"))+
						" and c.adjustDate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"))+
						") order by enterDate, propertyNo, serialNo";
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	while(rs.next()){
    		Object[] data = new Object[10];
    		data[0] = rs.getString("propertyNo")+"-"+rs.getString("serialNo");
    		data[1] = rs.getString("propertyName");
    		data[2] = rs.getString("signName")+rs.getString("signNo").substring(7,11)+"-"+rs.getString("signNo").substring(11)+"地號\n";
    		data[3] = rs.getString("setObligor");
    		data[4] = Common.areaFormat(rs.getString("area"))+"　";
    		data[5] = rs.getString("holdNume")+"/"+rs.getString("holdDeno");
   			data[6] = Common.areaFormat(rs.getString("holdArea"))+"　";
   			data[7] = Common.MaskDate(rs.getString("buyDate"));
    		data[8] = rs.getString("setPeriod");
    		if(rs.getString("signNo")==null){
    		    data[9] = rs.getString("bookValueName")+"\n"+Common.valueFormat(rs.getString("adjustBookValue"))+"　";
    		    //合計
    		    addBookValueSum += Integer.parseInt(rs.getString("adjustBookValue"));
    		}else{
    			data[9] = rs.getString("bookValueName")+"\n"+Common.valueFormat(rs.getString("bookValue"))+"　";
    			//合計
    			addBookValueSum += Integer.parseInt(rs.getString("bookValue"));
    		}
			//合計
			addHoldAreaSum += Double.parseDouble(rs.getString("holdArea"));
			addCountSum ++;
			obj.setSumAddHoldArea(addHoldAreaSum+"");
			obj.setSumAddCount(addCountSum+"");
			obj.setSumAddBookValue(addBookValueSum+"");
    		//for(i=0;i<10;i++)if(data[i]==null)data[i]="";
			obj.setCheckModel("true");
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
