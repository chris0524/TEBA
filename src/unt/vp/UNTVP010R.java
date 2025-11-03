/*
*<br>程式目的：有價證券明細清冊查詢檔 
*<br>程式代號：untvp010r
*<br>撰寫日期：94/11/10
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

public class UNTVP010R extends UNTVP006Q{
	long totalBookValue;
	int totalCount;
	long sumTotalBookValue;
	int sumTotalCount;

	private String q_differenceKind;
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String q_differenceKind) {this.q_differenceKind = checkSet(q_differenceKind);}

	String enterOrg;
	String ownership;
	String propertyNo;
	String serialNo;
	String propertyKind;
	String fundType;
	
	String q_enterDateS;
	String q_enterDateE;
	String q_balanceDate;
	String q_dataType;
	String q_dataTypeName;
	String q_enterDateName;
	String q_enterDate;
	
	String bookAmount;
	String bookUnitValue;
	String propertyName;
	String propertyName1;
	String securityMeat;
	String securityName;
	String keepUnitName;
	String propertyNoSerialNo;
	String dataState;
	String newBookValue;
	String reduceDate;
	String sumBookValue="0";
	String sumCount="0";
	String totalSumBookValue="0";
	String totalSumCount="0";
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getBookAmount(){ return checkGet(bookAmount); }
	public void setBookAmount(String s){ bookAmount=checkSet(s); }
	public String getBookUnitValue(){ return checkGet(bookUnitValue); }
	public void setBookUnitValue(String s){ bookUnitValue=checkSet(s); }
	
	public String getTotalSumBookValue(){ return checkGet(totalSumBookValue); }
	public void setTotalSumBookValue(String s){ totalSumBookValue=checkSet(s); }
	public String getTotalSumCount(){ return checkGet(totalSumCount); }
	public void setTotalSumCount(String s){ totalSumCount=checkSet(s); }

	public String getSumCount(){ return checkGet(sumCount); }
	public void setSumCount(String s){ sumCount=checkSet(s); }
	public String getSumBookValue(){ return checkGet(sumBookValue); }
	public void setSumBookValue(String s){ sumBookValue=checkSet(s); }
	public String getReduceDate(){ return checkGet(reduceDate); }
	public void setReduceDate(String s){ reduceDate=checkSet(s); }
	public String getNewBookValue(){ return checkGet(newBookValue); }
	public void setNewBookValue(String s){ newBookValue=checkSet(s); }
	public String getDataState(){ return checkGet(dataState); }
	public void setDataState(String s){ dataState=checkSet(s); }
	public String getPropertyNoSerialNo(){ return checkGet(propertyNoSerialNo); }
	public void setPropertyNoSerialNo(String s){ propertyNoSerialNo=checkSet(s); }
	
	public String getPropertyName(){ return checkGet(propertyName); }
	public void setPropertyName(String s){ propertyName=checkSet(s); }
	public String getPropertyName1(){ return checkGet(propertyName1); }
	public void setPropertyName1(String s){ propertyName1=checkSet(s); }
	
	public String getSecurityMeat(){ return checkGet(securityMeat); }
	public void setSecurityMeat(String s){ securityMeat=checkSet(s); }
	public String getSecurityName(){ return checkGet(securityName); }
	public void setSecurityName(String s){ securityName=checkSet(s); }
	public String getKeepUnitName(){ return checkGet(keepUnitName); }
	public void setKeepUnitName(String s){ keepUnitName=checkSet(s); }
	
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
	public String getQ_balanceDate(){ return checkGet(q_balanceDate); }
	public void setQ_balanceDate(String s){ q_balanceDate=checkSet(s); }
	public String getQ_dataType(){ return checkGet(q_dataType); }
	public void setQ_dataType(String s){ q_dataType=checkSet(s); }
	public String getPropertyKind(){ return checkGet(propertyKind); }
	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
	public String getFundType(){ return checkGet(fundType); }
	public void setFundType(String s){ fundType=checkSet(s); }

	String oldEnterOrg;
	String oldOwnership;
	String oldPropertyKind;
	String oldFundType;
	String checkModel;

	public String getCheckModel(){ return checkGet(checkModel); }
	public void setCheckModel(String s){ checkModel=checkSet(s); }
	public String getOldEnterOrg(){ return checkGet(oldEnterOrg); }
	public void setOldEnterOrg(String s){ oldEnterOrg=checkSet(s); }
	public String getOldOwnership(){ return checkGet(oldOwnership); }
	public void setOldOwnership(String s){ oldOwnership=checkSet(s); }
	public String getOldPropertyKind(){ return checkGet(oldPropertyKind); }
	public void setOldPropertyKind(String s){ oldPropertyKind=checkSet(s); }
	public String getOldFundType(){ return checkGet(oldFundType); }
	public void setOldFundType(String s){ oldFundType=checkSet(s); }
	
//	取得UNTVP_AdjustProof.adjustDate最大的一筆資料之『新總價』
	protected void getAdjustProofNewBookVlaue(){
		Database db = new Database();	
		UNTVP010R obj = this;
		ResultSet rs;	
		String sql="select max(a.newBookValue) as newBookValue from UNTVP_AdjustProof a" +
				" where 1=1 " +
				" and a.adjustDate<="+ util.Common.sqlChar(getQ_balanceDate()) + 
				" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
				" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
				" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
				" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
				"";	
		try{
			rs = db.querySQL(sql);
			if (rs.next()){
				obj.setNewBookValue(rs.getString("newBookValue"));
			} else {
				obj.setNewBookValue("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	}
//	抓取max(UNTVP_Share.bookUnitValue)
	protected void getMaxBookUnitValue(String serialNo){
		Database db = new Database();	
		UNTVP010R obj = this;
		ResultSet rs;	
		String sql="select max(a.bookUnitValue) as bookUnitValue from UNTVP_Share a, UNTVP_AddProof b" +
				" where 1=1 " +
				" and b.enterOrg = a.enterOrg and b.ownership = a.ownership and b.propertyNo = a.propertyNo " +
				" and b.serialNo = a.serialNo " +
				" and b.serialNo = '" + serialNo + "' "+
				"";	
		
		if (!"".equals(getQ_enterOrg())) {
			sql +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		}
		//System.out.println("sql====>"+sql);
		try{
			rs = db.querySQL(sql);
			if (rs.next()){
				obj.setBookUnitValue(rs.getString("bookUnitValue"));
			} else {
				obj.setReduceDate("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	}		

	
//	抓取max(UNTVP_Share.reduceDate)
	protected void getMaxReduceDate(){
		Database db = new Database();	
		UNTVP010R obj = this;
		ResultSet rs;	
		String sql="select max(a.reduceDate) as reduceDate from UNTVP_Share a" +
				" where 1=1 " +
				" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
				" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
				" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
				" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
				"";	

		try{
			rs = db.querySQL(sql);
			if (rs.next()){
				obj.setReduceDate(rs.getString("reduceDate"));
			} else {
				obj.setReduceDate("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}

	}	
	
public DefaultTableModel getResultModel() throws Exception{
	String execSQL="";
	String dataTypeYN="";
	ResultSet rs;
	ResultSet rsS;
	Vector rowData = new Vector();
	UNTVP010R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{

    	String[] columns = new String[] {"ENTERORGNAME","DATE","OWNERSHIP","PROPERTYKINDFUNDTYPE","DATATYPE",
    			"ENTERDATENAME","ENTERDATE","subReportDataSource","ENTERORGPROPERTYKINDFUNDTYPE","SUMBOOKVALUE",
    			"SUMCOUNT","SUMTOTALBOOKVALUE","SUMTOTALCOUNT"};
    	
    	//「資料類別」為「結存數」時，同一個「enterOrg＋propertyKind＋fundType＋propertyNo＋serialNo」以一筆顯示
    	if ("3".equals(q_dataType)){
    		dataTypeYN = " distinct ";
    	}else dataTypeYN = " ";
    	
    		String sql="select"+ dataTypeYN +"a.enterOrg, a.propertyKind, a.fundType, a.propertyNo, a.serialNo " +
    				" from UNTVP_AddProof a where 1=1 " +
    				"";
    		if (!"".equals(getQ_enterOrg())) {
    			sql +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    		} else {
    			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    				if ("Y".equals(getIsOrganManager())) {					
    					sql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";					
    				} else {
    					sql +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    				}
    			}
    		}
    		if (!"".equals(getQ_ownership()))
    			sql +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    		if (!"".equals(getQ_propertyKind()))
    			sql +=" and a.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
    		if (!"".equals(getQ_fundType()))
    			sql +=" and a.fundType = " + Common.sqlChar(getQ_fundType()) ;
    		sql+=" order by a.enterOrg, a.propertyKind, a.fundType, a.propertyNo, a.serialNo ";
        //System.out.println("sql:" +sql+"\n");
		//使用者操作記錄
		Common.insertRecordSql(execSQL, this.getOrganID(), this.getUserID(), "UNTVP010R", this.getObjPath().replaceAll("&gt;", ">"));
			
    	rsS = db.querySQL(sql);
    	while(rsS.next()){
    		
    		execSQL="select a.enterOrg, a.ownership, a.propertyNo, a.serialNo, " +
    					" c.ORGANANAME as enterOrgName, " +
    					" (select x.codeName from sysca_code x where a.ownership = x.codeid and x.codekindid = 'OWA') as ownershipName, " +
    					" case a.propertyKind when '01' then '公務用' when '02' then '公共用' when '03' then '事業用' when '04' then '非公用' else '' end as propertyKindName, a.propertyKind, " +
    					" d.codeName as fundTypeName, a.fundType, " +
    					" e.unitName as keepUnitName, a.securityMeat, a.securityName, b.propertyName, a.propertyName1, a.dataState, a.bookamount " +
    					" from UNTVP_AddProof a " +
    					" left join UNTMP_KeepUnit e on a.enterOrg=e.enterOrg and a.keepUnit=e.unitNo" +
    					" left join SYSCA_Code d on a.fundType=d.codeID and d.codeKindID = 'FUD'" +
    					" ,SYSPK_PropertyCode b, SYSCA_Organ c "+    					 
						" where 1=1 and a.verify = 'Y' and a.datastate = '1' and a.propertyNo = b.propertyNo and a.enterOrg = c.organID" +
						" and a.enterOrg="+ util.Common.sqlChar(rsS.getString("enterOrg"))+
						" and a.propertyKind="+ util.Common.sqlChar(rsS.getString("propertyKind"))+
						" and a.propertyNo="+ util.Common.sqlChar(rsS.getString("propertyNo"))+
						" and a.serialNo="+ util.Common.sqlChar(rsS.getString("serialNo"))+
						"";
    		
    		if(!"".equals(checkGet(getQ_differenceKind()))){
    			execSQL += " and a.differenceKind =" + util.Common.sqlChar(getQ_differenceKind());
    		}
    		if(!"".equals(checkGet(getQ_ownership()))){
    			execSQL += " and a.ownership =" + util.Common.sqlChar(getQ_ownership());
    		}
    		
    		if (rsS.getString("fundType")!=null)
    			execSQL +=" and a.fundType = " + util.Common.sqlChar(rsS.getString("fundType"));
    		
    		execSQL+=" order by a.enterOrg, a.propertyKind, a.fundType, a.propertyNo, a.serialNo ";
    		 //System.out.println("sql:" +sql);
			String dataTypeName[]={"增加數","減少數","結存數"};
    			if("3".equals(q_dataType))q_dataTypeName=dataTypeName[2];
    			else if("2".equals(q_dataType))q_dataTypeName=dataTypeName[1];
    			else q_dataTypeName=dataTypeName[0];

    			if("3".equals(q_dataType)){
    				q_enterDateName="結存日期：";
    				q_enterDate=getQ_balanceDate().substring(0,3)+"/"+getQ_balanceDate().substring(3,5)+"/"+getQ_balanceDate().substring(5,7);
    			}else{
    				q_enterDateName="異動日期：";
    				q_enterDate=getQ_enterDateS().substring(0,3)+"/"+getQ_enterDateS().substring(3,5)+"/"+getQ_enterDateS().substring(5,7)+"~"+getQ_enterDateE().substring(0,3)+"/"+getQ_enterDateE().substring(3,5)+"/"+getQ_enterDateE().substring(5,7);
    			}
    			
    			rs = db.querySQL(execSQL);
                //System.out.println("execSQL : " + execSQL);
    			int i;
    			
    			while(rs.next()){
    				getMaxBookUnitValue(rs.getString("serialNo"));
    				obj.setPropertyKind(rs.getString("propertyKind"));
    				obj.setFundType(rs.getString("fundType"));
    				obj.setEnterOrg(rs.getString("enterOrg"));
    				obj.setOwnership(rs.getString("ownership"));
    				obj.setPropertyNo(rs.getString("propertyNo"));
    				obj.setSerialNo(rs.getString("serialNo"));
    				obj.setPropertyNoSerialNo(rs.getString("propertyNo")+"-"+rs.getString("serialNo"));
    				obj.setPropertyName(rs.getString("propertyName"));
    				obj.setPropertyName1(rs.getString("propertyName1"));
    				obj.setSecurityMeat(rs.getString("securityMeat"));
    				obj.setSecurityName(rs.getString("securityName"));
    				obj.setKeepUnitName(rs.getString("keepUnitName"));
    				obj.setDataState(rs.getString("dataState"));
    				obj.setBookAmount(rs.getString("bookamount"));
    				
    				Object[] data = new Object[columns.length];
    				if(getSubModel("check").getRowCount() > 0){   //為了避免顯示時子報表顯示為空值，先查詢是否有資料
	    				data[0] = rs.getString("enterOrgName");
	    				data[1] = Datetime.getYYY()+"/"+Datetime.getMM()+"/"+Datetime.getYYYMMDD().substring(5,7);
	    				data[2] = rs.getString("ownershipName");
	    				if(rs.getString("fundType")!=null){
	    					data[3] = rs.getString("propertyKindName")+"　"+rs.getString("fundTypeName");
	    				}else{
	    					data[3] = rs.getString("propertyKindName");
	    				}
	    				data[4] = q_dataTypeName;
	    				data[5] = q_enterDateName;
	    				data[6] = q_enterDate;
	   					data[7] = new JRTableModelDataSource(getSubModel((String)data[0]));
	    				data[8] = rs.getString("enterOrg")+rs.getString("ownership")+rs.getString("propertyKind")+rs.getString("fundType");
	    				data[9] = Common.valueFormat(obj.getSumBookValue());
	    				data[10] = obj.getSumCount()+"筆";
	    				data[11] = Common.valueFormat(obj.getTotalSumBookValue());
	    				data[12] = obj.getTotalSumCount()+"筆";
	    			
	    					rowData.addElement(data);
    				}
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

public DefaultTableModel getSubModel(String caseCode) throws Exception{
	UNTVP010R obj = this;
	String execSQL="";
	//int count=1;
	//合計價值換頁的判斷
	if(!(obj.getEnterOrg()).equals(obj.getOldEnterOrg())||!(obj.getOwnership()).equals(obj.getOldOwnership()) || !(obj.getPropertyKind()).equals(obj.getOldPropertyKind()) || !(obj.getFundType()).equals(obj.getOldFundType())){
		totalBookValue=0;
		totalCount=0;
		obj.setSumBookValue(totalBookValue+"");
		if(!"check".equals(caseCode)){
			obj.setSumCount(totalCount+"");
		}
	}
	obj.setOldEnterOrg(obj.getEnterOrg());
	obj.setOldOwnership(obj.getOwnership());
	obj.setOldPropertyKind(obj.getPropertyKind());
	obj.setOldFundType(obj.getFundType());

	ResultSet rs;	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();

    try{
        String[] columns = new String[] {"PROPERTYNO", "PROPERTYNAME","SECURITYMEAT","BOOKUNITVALUE","BOOKAMOUNT","SECURITYNAME","BOOKVALUE","KEEPUNIT"};
        
        //「資料類別」為「結存數」且「結存日期=系統日期」
        if("3".equals(q_dataType) && q_balanceDate.equals(Datetime.getYYYMMDD())){
        	execSQL="select isnull(a.bookValue, 0) as bookValue " +
					" from UNTVP_AddProof a "+
					" where 1=1 and a.dataState='1' "+
					" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
					" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
					" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
					" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
					" order by a.enterOrg, a.propertyKind, a.fundType, a.propertyNo,a.serialNo ";
        
    	//System.out.println("execSQL1:"+execSQL);	
    	//「資料類別」為「結存數」且「結存日期≠系統日期」
        }else if("3".equals(q_dataType) && !(Datetime.getYYYMMDD()).equals(q_balanceDate)){
        	if(obj.getDataState().equals("1")){
        		execSQL="select isnull(a.originalBV, 0) as bookValue " +
        				" from UNTVP_AddProof a" +
						" where 1=1 and a.dataState='1' " +
						" and a.enterDate <="+ util.Common.sqlChar(getQ_balanceDate()) +
						" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
						" order by a.enterOrg, a.propertyKind, a.fundType, a.propertyNo,a.serialNo ";
        		//System.out.println("execSQL2:"+execSQL);
        	}else{
        		getMaxReduceDate();
        		execSQL="select isnull(a.originalBV, 0) as bookValue, b.bookUnitValue " +
						" from UNTVP_AddProof a, UNTVP_Share b" +
						" where 1=1 and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo " +
						" and a.dataState='2' " +
						" and "+ util.Common.sqlChar(obj.getReduceDate())+">"+ util.Common.sqlChar(getQ_balanceDate()) +
						" and a.enterDate <="+ util.Common.sqlChar(getQ_balanceDate()) +
						" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
						" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
						" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
						" order by a.enterOrg, a.propertyKind, a.fundType, a.propertyNo,a.serialNo " +
						"";
        		//System.out.println("execSQL3:"+execSQL);
        	}
        	getAdjustProofNewBookVlaue();

        }else if("2".equals(q_dataType)){
        	execSQL="select (a.adjustBookValue*-1) as bookValue " +
        			" from UNTVP_AdjustProof a where 1=1 " +
        			" and a.adjustBookvalue<=0 and a.adjustBookAmount<0 "+
					" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
					" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
					" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
					" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
					" and a.adjustDate>="+util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) +
					" and a.adjustDate<="+util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) +
					" order by a.enterOrg, a.propertyKind, a.fundType, a.propertyNo,a.serialNo,a.adjustDate ";
        	//System.out.println("execSQL4:"+execSQL);
        //「資料類別」為「增加數」時
        }else{
        	execSQL="(select isnull(a.originalBV,0) as bookValue, " +
        			" a.enterOrg, a.propertyKind, a.fundType, a.propertyNo, a.serialNo, a.enterDate as enterDate " +
        			" from UNTVP_AddProof a where 1=1 " +
					" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
					" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
					" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
					" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
					" and a.enterDate>="+util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) +
					" and a.enterDate<="+util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) +
					" ) " +
					"union " +
					"(select isnull(a.adjustBookValue,0) as bookValue, " +
					" a.enterOrg, a.propertyKind, a.fundType, a.propertyNo, a.serialNo, a.adjustDate as enterDate " +
					" from UNTVP_AdjustProof a where 1=1 " +
					" and a.adjustBookAmount>0 and a.adjustBookValue>=0 " +
					" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
					" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
					" and a.propertyNo=" + util.Common.sqlChar(obj.getPropertyNo())+
					" and a.serialNo=" + util.Common.sqlChar(obj.getSerialNo())+
					" and a.adjustDate>="+util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2")) +
					" and a.adjustDate<="+util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2")) +
					" ) order by enterOrg, propertyKind, fundType, propertyNo, serialNo, enterDate ";
        	//System.out.println("execSQL5:"+execSQL);
        }
    	Vector rowData = new Vector();
    	int i;
    	Object[] data ;
		rs = db.querySQL(execSQL);
		//System.out.println(execSQL);
    	while(rs.next()){
    		data = new Object[8];    		
    		data[0] = obj.getPropertyNoSerialNo();
    		data[1] = obj.getPropertyName()+"/"+obj.getPropertyName1();
    		data[2] = obj.getSecurityMeat();
    		data[3] = Common.valueFormat(obj.getBookUnitValue());
    		data[4] = Common.valueFormat(obj.getBookAmount());
    		data[5] = Common.valueFormat(obj.getSecurityName());    		
    		if("3".equals(q_dataType) && !(Datetime.getYYYMMDD()).equals(q_balanceDate)){
    			if(!"".equals(obj.getNewBookValue())){
    				data[6] = Common.valueFormat(obj.getNewBookValue());
    				if(!"check".equals(caseCode)){
	    				//合計
	    				totalCount++;
	    				totalBookValue += Long.parseLong(obj.getNewBookValue());
	    				obj.setSumBookValue(totalBookValue+"");
	    				obj.setSumCount(totalCount+"");
	    				//總計
	    				sumTotalCount++;
	    				sumTotalBookValue += Long.parseLong(obj.getNewBookValue());
	    				obj.setTotalSumBookValue(sumTotalBookValue+"");
	    				obj.setTotalSumCount(sumTotalCount+"");
    				}
    			}else{
    				data[6] = Common.valueFormat(rs.getString("bookValue"));
    				if(!"check".equals(caseCode)){
	    				//合計
	    				totalCount++;
	    				totalBookValue += Long.parseLong(rs.getString("bookValue"));
	    				obj.setSumBookValue(totalBookValue+"");
	    				obj.setSumCount(totalCount+"");
	    				//總計
	    				sumTotalCount++;
	    				sumTotalBookValue += Long.parseLong(rs.getString("bookValue"));
	    				obj.setTotalSumBookValue(sumTotalBookValue+"");
	    				obj.setTotalSumCount(sumTotalCount+"");
    				}
    			}
    		}else{	
    			data[6] = Common.valueFormat(rs.getString("bookValue"));
    			if(!"".equals(rs.getString("bookValue"))){
	    				if(!"check".equals(caseCode)){
	    				//合計
	    				totalCount++;
	    				totalBookValue += Long.parseLong(rs.getString("bookValue"));
	    				obj.setSumBookValue(totalBookValue+"");
	    				obj.setSumCount(totalCount+"");
	    				//總計
	    				sumTotalCount++;
	    				sumTotalBookValue += Long.parseLong(rs.getString("bookValue"));
	    				obj.setTotalSumBookValue(sumTotalBookValue+"");
	    				obj.setTotalSumCount(sumTotalCount+"");
    				}
    			}
    		}
    		data[7] = obj.getKeepUnitName();
    		
    		for(i=0;i<8;i++)
    		 //System.out.print(data[i]+",");
    		//System.out.print("\n");
    		
    		obj.setCheckModel("true");
    		for(i=0;i<8;i++) if(data[i]==null)data[i]="";
    		rowData.addElement(data);    
    	}
    Object[][] finalData = new Object[0][0];
    finalData = (Object[][])rowData.toArray(finalData);
    model.setDataVector(finalData, columns);  
    
	}catch(Exception x){
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
    
}

}