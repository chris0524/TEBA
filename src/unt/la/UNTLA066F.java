/*
*<br>程式目的：土地合併分割重測重劃作業－調整前後對應資料
*<br>程式代號：untla066f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.la;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.*;

public class UNTLA066F extends UNTLA054Q{
	
	String enterOrg;
	String ownership;
	String differenceKind;
	String propertyNo;
	String serialNo;
	String mergeDivisionBatch;
	String adjustType;
	String signNo;
	String holdArea;
	
	String caseNo;
	String adjustTypeName;
	String signNoName;	
	String signNo1;
	String signNo2;
	String signNo3;
	String signNo4;
	String signNo5;
	
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getMergeDivisionBatch() {return checkGet(mergeDivisionBatch);}
	public void setMergeDivisionBatch(String mergeDivisionBatch) {this.mergeDivisionBatch = checkSet(mergeDivisionBatch);}
	public String getAdjustType() {return checkGet(adjustType);}
	public void setAdjustType(String adjustType) {this.adjustType = checkSet(adjustType);}
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
	public String getHoldArea() {return checkGet(holdArea);}
	public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}


	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getAdjustTypeName() {return checkGet(adjustTypeName);}
	public void setAdjustTypeName(String adjustTypeName) {this.adjustTypeName = checkSet(adjustTypeName);}
	public String getSignNoName() {return checkGet(signNoName);}
	public void setSignNoName(String signNoName) {this.signNoName = checkSet(signNoName);}
	public String getSignNo1() {return checkGet(signNo1);}
	public void setSignNo1(String signNo1) {this.signNo1 = checkSet(signNo1);}
	public String getSignNo2() {return checkGet(signNo2);}
	public void setSignNo2(String signNo2) {this.signNo2 = checkSet(signNo2);}
	public String getSignNo3() {return checkGet(signNo3);}
	public void setSignNo3(String signNo3) {this.signNo3 = checkSet(signNo3);}
	public String getSignNo4() {return checkGet(signNo4);}
	public void setSignNo4(String signNo4) {this.signNo4 = checkSet(signNo4);}
	public String getSignNo5() {return checkGet(signNo5);}
	public void setSignNo5(String signNo5) {this.signNo5 = checkSet(signNo5);}	

	
	

//依主鍵查詢單一資料
public UNTLA066F  queryOne() throws Exception{
	Database db = new Database();
	UNTLA066F obj = this;
	
	String sql = createQuerySQL() + 
			" where 1=1" +
				" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
				" and ownership = " + Common.sqlChar(this.getOwnership()) +
				" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
				" and propertyno = " + Common.sqlChar(this.getSerialNo()) +
				" and serialno = " + Common.sqlChar(this.getMergeDivisionBatch()) +
//				(("".equals(getMergeDivisionBatch()))?"":" and mergedivisionbatch = " + Common.sqlChar(this.getMergeDivisionBatch())) +
			" order by a.mergedivisionbatch, a.adjusttype, a.signno" ;
	
	
	try {
		LogBean.outputLogDebug(sql);
		
		ResultSet rs = db.querySQL_NoChange(sql);
		if (rs.next()){
			obj.setMergeDivisionBatch(rs.getString("mergeDivisionBatch"));
			obj.setAdjustType(rs.getString("adjustType"));
			obj.setAdjustTypeName(rs.getString("adjustTypeName"));
			obj.setSignNo(rs.getString("signNo"));
			obj.setSignNoName(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11) + "地號");
			obj.setHoldArea(rs.getString("holdArea"));
			
			obj.setSignNo1(rs.getString("signNo").substring(0,1) + "000000");
			obj.setSignNo2(rs.getString("signNo").substring(0,3) + "0000");
			obj.setSignNo3(rs.getString("signNo").substring(0,7));
			obj.setSignNo4(rs.getString("signNo").substring(7,11));
			obj.setSignNo5(rs.getString("signNo").substring(11));
		}
		
		setStateQueryOneSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
	} finally {
		db.closeAll();
	}
	return obj;
}


//依查詢欄位查詢多筆資料
public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	String sql = createQuerySQL() + " order by a.mergeDivisionBatch, a.adjustType, a.signNo" ;

	try {
		LogBean.outputLogDebug(sql);
		
		sql = db._transSQLFormat(sql);
		
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[9];
			
			rowArray[0]=Common.get(rs.getString("enterOrg"));
			rowArray[1]=Common.get(rs.getString("ownership"));
			rowArray[2]=Common.get(rs.getString("differenceKind"));
			rowArray[3]=Common.get(rs.getString("propertyNo"));
			rowArray[4]=Common.get(rs.getString("serialNo"));
			rowArray[5]=Common.get(rs.getString("mergeDivisionBatch")); 
			rowArray[6]=Common.get(rs.getString("adjustTypeName"));
			rowArray[7]=Common.get(getSignDescName(rs.getString("signNo").substring(0,7)) + rs.getString("signNo").substring(7,11) + '-' + rs.getString("signNo").substring(11) + "地號");
			rowArray[8]=Common.get(rs.getString("holdArea"));
			
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		LogBean.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
	} finally {
		db.closeAll();
	}
	return objList;
}

	private String createQuerySQL(){
		String sql=" select a.enterorg, a.ownership, a.differencekind, a.propertyno, a.serialno, a.mergedivisionbatch," +
							" a.adjusttype," +
							" (case a.adjusttype when '1' then '調整前' else '調整後' end) as adjusttypename," +
							" a.signno," +
							" a.holdarea" +
					" from " +
					   " (" +
					     " (" +
					        " select enterorg                         ," +
					               " ownership                        ," +
					               " differencekind                   ," +
					               " propertyno                       ," +
					               " serialno                         ," +
					               " mergedivisionbatch               ," +
					               " '1'                as adjusttype ," +
					               " signno                           ," +
					               " holdarea                          " +
					          " from UNTLA_REDUCEDETAIL " +
					         " where mergedivision = " + Common.sqlChar(this.getCaseNo_Merge()) +
					         	" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					     " )  " +
					     " union all" +
					     " (" +
					        " select enterorg                         ," +
					               " ownership                        ," +
					               " differencekind                   ," +
					               " propertyno                       ," +
					               " serialno                         ," +
					               " mergedivisionbatch               ," +
					               " '2'                as adjusttype ," +
					               " signno                           ," +
					               " holdarea                          " +
					          " from UNTLA_LAND " +
					         " where mergedivision = " + Common.sqlChar(this.getCaseNo_Merge()) +
					         	" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
					     " )" +
					   " ) a";
		
		return sql;
	}
	

	private String getSignDescName(String signNo){
		Database db = null;
		ResultSet rs = null;
		String sql = null;
		String result = null;
		try{
			sql = "select signdesc from SYSCA_SIGN where" +
					" signNo = " + Common.sqlChar(signNo);
			
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){				
				result = rs.getString("signdesc");
			}
			rs.close();
		}catch(Exception e){
			System.out.println("getSignDescName Exception => " + e.getMessage());
		}finally{
			db.closeAll();
		}
		return result;
	}
}


