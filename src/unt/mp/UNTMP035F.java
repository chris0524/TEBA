/*
*<br>程式目的：動產主檔資料維護－批號附屬設備
*<br>程式代號：untmp035f
*<br>程式日期：0941025
*<br>程式作者：cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.com.src.SQLCreator;

public class UNTMP035F extends UNTMP001Q{

	private String enterOrg;
	private String ownership;
	private String differenceKind;
	private String propertyNo;
	private String lotNo;
	private String serialNo1;
	private String equipmentName;
	private String buyDate;
	private String equipmentUnit;
	private String equipmentAmount;
	private String unitPrice;
	private String totalValue;
	private String sourceKind;
	private String nameplate;
	private String specification;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	public String getSerialNo1() {return checkGet(serialNo1);}
	public void setSerialNo1(String serialNo1) {this.serialNo1 = checkSet(serialNo1);}
	public String getEquipmentName() {return checkGet(equipmentName);}
	public void setEquipmentName(String equipmentName) {this.equipmentName = checkSet(equipmentName);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getEquipmentUnit() {return checkGet(equipmentUnit);}
	public void setEquipmentUnit(String equipmentUnit) {this.equipmentUnit = checkSet(equipmentUnit);}
	public String getEquipmentAmount() {return checkGet(equipmentAmount);}
	public void setEquipmentAmount(String equipmentAmount) {this.equipmentAmount = checkSet(equipmentAmount);}
	public String getUnitPrice() {return checkGet(unitPrice);}
	public void setUnitPrice(String unitPrice) {this.unitPrice = checkSet(unitPrice);}
	public String getTotalValue() {return checkGet(totalValue);}
	public void setTotalValue(String totalValue) {this.totalValue = checkSet(totalValue);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getNameplate() {return checkGet(nameplate);}
	public void setNameplate(String nameplate) {this.nameplate = checkSet(nameplate);}
	public String getSpecification() {return checkGet(specification);}
	public void setSpecification(String specification) {this.specification = checkSet(specification);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}

	String serialNoS;
	String serialNoE;
	String serialNoUntmpAttachment2;
	String verify;
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getSerialNoS(){ return checkGet(serialNoS); }
	public void setSerialNoS(String s){ serialNoS=checkSet(s); }
	public String getSerialNoE(){ return checkGet(serialNoE); }
	public void setSerialNoE(String s){ serialNoE=checkSet(s); }
	public String getSerialNoUntmpAttachment2(){ return checkGet(serialNoUntmpAttachment2); }
	public void setSerialNoUntmpAttachment2(String s){ serialNoUntmpAttachment2=checkSet(s); }


	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("LotNo", getLotNo());
		map.put("SerialNo1", getSerialNo1());
		
		return map;
	}
	
	private Map getRecordMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("Ownership", getOwnership());
		map.put("DifferenceKind", getDifferenceKind());
		map.put("PropertyNo", getPropertyNo());
		map.put("LotNo", getLotNo());
		map.put("SerialNo1", getSerialNo1());
		map.put("EquipmentName", getEquipmentName());
		map.put("BuyDate", new UNTCH_Tools()._transToCE_Year(getBuyDate()));
		map.put("EquipmentUnit", getEquipmentUnit());
		map.put("EquipmentAmount", getEquipmentAmount());
		map.put("UnitPrice", getUnitPrice());
		map.put("TotalValue", getTotalValue());
		map.put("SourceKind", getSourceKind());
		map.put("Nameplate", getNameplate());
		map.put("Specification", getSpecification());
		map.put("Notes", getNotes());
		map.put("EditID", getEditID());
		map.put("EditDate", new UNTCH_Tools()._transToCE_Year(getEditDate()));
		map.put("EditTime", getEditTime());

		return map;
	}


protected void getInsertSQLAttachment2(){
	
	UNTMP035F obj = this;
	Database db = new Database();
	//ResultSet rs = null;
	//String sql = "";
	StringBuffer sbSQL = new StringBuffer("");
	//String strSQL = "";
	String[] execSQLArray;
	sbSQL.append("delete UNTMP_Attachment2 " ).append(
				" where 1=1 " ).append(
				" and enterOrg = " ).append( Common.sqlChar(enterOrg) ).append(
				" and ownership = " ).append( Common.sqlChar(ownership) ).append(
				" and propertyNo = " ).append( Common.sqlChar(propertyNo) ).append(
				" and lotNo = " ).append( Common.sqlChar(lotNo) ).append(
				" and serialNo1 = " ).append( Common.sqlChar(obj.getSerialNo1()) ).append(
				" and differenceKind = " ).append( Common.sqlChar(differenceKind) ).append(						
				":;:");	
	int counter=0;
	int serialNoSAttachment2 = Integer.parseInt(serialNoS);
	int serialNoEAttachment2 = Integer.parseInt(serialNoE);
	counter = serialNoEAttachment2-serialNoSAttachment2+1;
	obj.setSerialNoUntmpAttachment2(serialNoS);
	try {    
		while(counter > 0){
		    counter--;
		    sbSQL.append(" insert into UNTMP_Attachment2(" ).append(
					" enterOrg,").append(
					" ownership,").append(
					" propertyNo,").append(
					" lotNo,").append(
					" serialNo,").append(
					" serialNo1,").append(
					" equipmentName,").append(
					" buyDate,").append(
					" equipmentUnit,").append(
					" equipmentAmount,").append(
					" unitPrice,").append(
					" totalValue,").append(
					" dataState,").append(
					" sourcekind,").append(
					" notes,").append(
					" editID,").append(
					" editDate,").append(
					" editTime,").append(
					" differenceKind,").append(
					" nameplate,").append(
					" specification ").append(								
				")Values(" ).append(
					Common.sqlChar(enterOrg) ).append( "," ).append(
					Common.sqlChar(ownership) ).append( "," ).append(
					Common.sqlChar(propertyNo) ).append( "," ).append(
					Common.sqlChar(lotNo) ).append( "," ).append(
					Common.sqlChar(serialNoUntmpAttachment2) ).append( "," ).append(
					Common.sqlChar(obj.getSerialNo1()) ).append( "," ).append(
					Common.sqlChar(equipmentName) ).append( "," ).append(
					Common.sqlChar(new UNTCH_Tools()._transToCE_Year(buyDate)) ).append( "," ).append(
					Common.sqlChar(equipmentUnit) ).append( "," ).append(
					Common.sqlChar(equipmentAmount) ).append( "," ).append(
					Common.sqlChar(unitPrice) ).append( "," ).append(
					Common.sqlChar(totalValue) ).append( "," ).append(
					Common.sqlChar("1") ).append( "," ).append(
					Common.sqlChar(sourceKind) ).append( "," ).append(	
					Common.sqlChar(notes) ).append( "," ).append(
					Common.sqlChar(getEditID()) ).append( "," ).append(
					Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) ).append( "," ).append(
					Common.sqlChar(getEditTime()) ).append( "," ).append(
					Common.sqlChar(getDifferenceKind()) ).append( "," ).append(
					Common.sqlChar(nameplate) ).append( "," ).append(
					Common.sqlChar(specification) ).append( ")" ).append(		
				":;:");	
		    int changeSerialNo = Integer.parseInt(obj.getSerialNoUntmpAttachment2())+1;
		    obj.setSerialNoUntmpAttachment2(Common.formatFrontZero((changeSerialNo+""),7));
		}
		execSQLArray = sbSQL.toString().split(":;:");
		db.excuteSQL(execSQLArray);
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
}

//傳回insert sql
protected String[] getInsertSQL(){

	//取得附屬設備次序
	Database db = new Database();
	ResultSet rs;	
	String sql="select case when max(serialNo1) is null then '001' else (max(serialNo1)+1) end as serialNo1 from UNTMP_Attachment1 where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) + 
				" and ownership = " + Common.sqlChar(ownership) + 
				" and lotNo = " + Common.sqlChar(lotNo) + 
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and differenceKind = " + Common.sqlChar(differenceKind) + 
				"";
	try {		
		rs = db.querySQL(sql);
		if (rs.next()){
			if ("".equals(Common.get(rs.getString("serialNo1")))){
				setSerialNo1("001");
			}else{
				setSerialNo1(Common.formatFrontZero(rs.getString("serialNo1"), 3));
			}			
		} else {
			setSerialNo1("001");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}		
	
	
	UNTMP035F obj = this;
	getInsertSQLAttachment2();
	String[] execSQLArray = new String[1];
	
	execSQLArray[0] = new SQLCreator()._obtainSQLforInsert("UNTMP_Attachment1", getPKMap(), getRecordMap());
	
	return execSQLArray;
}


//傳回update sql
public void update(){
    Database db = new Database();    
    String strSQL = "";
	String[] execSQLArray;
	ResultSet rs;	
	setEditTime(Datetime.getHHMMSS());	
	String sql="select enterOrg, ownership, propertyNo, serialNo, serialNo1, differenceKind from UNTMP_Attachment2 " +
				" where 1=1 and dataState='1' " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and lotNo = " + Common.sqlChar(lotNo) +
				" and serialNo1 = " + Common.sqlChar(serialNo1) +
				" and differenceKind = " + Common.sqlChar(differenceKind) +
				"";		
	try {
		rs = db.querySQL(sql);
		while (rs.next()){
			strSQL+=" update UNTMP_Attachment2 set " +
					" lotNo = " + Common.sqlChar(lotNo) + "," +
					" equipmentName = " + Common.sqlChar(equipmentName) + "," +
					" buyDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(buyDate)) + "," +
					" equipmentUnit = " + Common.sqlChar(equipmentUnit) + "," +
					" equipmentAmount = " + Common.sqlChar(equipmentAmount) + "," +
					" nameplate = " + Common.sqlChar(nameplate) + "," +
					" specification = " + Common.sqlChar(specification) + "," +
					" unitPrice = " + Common.sqlChar(unitPrice) + "," +
					" totalValue = " + Common.sqlChar(totalValue) + "," +
					" sourceKind = " + Common.sqlChar(sourceKind) + "," +
					" Notes = " + Common.sqlChar(notes) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + "," +
					" differenceKind = " + Common.sqlChar(getDifferenceKind()) + 
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
					" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
					" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
					" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
					" and serialNo1 = " + Common.sqlChar(rs.getString("serialNo1")) +
					" and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
					":;:";
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	try {
		strSQL+=" update UNTMP_Attachment1 set " +
					" equipmentName = " + Common.sqlChar(equipmentName) + "," +
					" buyDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(buyDate)) + "," +
					" equipmentUnit = " + Common.sqlChar(equipmentUnit) + "," +
					" equipmentAmount = " + Common.sqlChar(equipmentAmount) + "," +
					" nameplate = " + Common.sqlChar(nameplate) + "," +
					" specification = " + Common.sqlChar(specification) + "," +
					" unitPrice = " + Common.sqlChar(unitPrice) + "," +
					" totalValue = " + Common.sqlChar(totalValue) + "," +
					" sourceKind = " + Common.sqlChar(sourceKind) + "," +
					" notes = " + Common.sqlChar(notes) + "," +
					" editID = " + Common.sqlChar(getEditID()) + "," +
					" editDate = " + Common.sqlChar(new UNTCH_Tools()._transToCE_Year(getEditDate())) + "," +
					" editTime = " + Common.sqlChar(getEditTime()) + "," +
					" differenceKind = " + Common.sqlChar(getDifferenceKind()) + 
				" where 1=1 " + 
					" and enterOrg = " + Common.sqlChar(enterOrg) +
					" and ownership = " + Common.sqlChar(ownership) +
					" and propertyNo = " + Common.sqlChar(propertyNo) +
					" and lotNo = " + Common.sqlChar(lotNo) +
					" and serialNo1 = " + Common.sqlChar(serialNo1) +
					" and differenceKind = " + Common.sqlChar(differenceKind) +
					":;:";
	
		execSQLArray = strSQL.split(":;:");
		db.excuteSQL(execSQLArray);
		setStateUpdateSuccess();
		setErrorMsg("修改完成");				
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}


//傳回delete sql
public void delete(){
    Database db = new Database();    
    String strSQL = "";
	String[] execSQLArray;
	ResultSet rs;	
	String sql="select enterOrg, ownership, propertyNo, serialNo, serialNo1, differenceKind from UNTMP_Attachment2 " +
				" where 1=1 and dataState='1' " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and lotNo = " + Common.sqlChar(lotNo) +
				" and serialNo1 = " + Common.sqlChar(serialNo1) +
				" and differenceKind = " + Common.sqlChar(differenceKind) +
				"";		
	try {
		rs = db.querySQL(sql);
		while (rs.next()){
			strSQL+=" delete UNTMP_Attachment2 where 1=1 " +
					" and enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
					" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
					" and propertyNo = " + Common.sqlChar(rs.getString("propertyNo")) +
					" and serialNo = " + Common.sqlChar(rs.getString("serialNo")) +
					" and serialNo1 = " + Common.sqlChar(rs.getString("serialNo1")) +
					" and differenceKind = " + Common.sqlChar(rs.getString("differenceKind")) +
					":;:";
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	try {
		strSQL+=" delete UNTMP_Attachment1 where 1=1 " +
				" and enterOrg = " + Common.sqlChar(enterOrg) +
				" and ownership = " + Common.sqlChar(ownership) +
				" and propertyNo = " + Common.sqlChar(propertyNo) +
				" and lotNo = " + Common.sqlChar(lotNo) +
				" and serialNo1 = " + Common.sqlChar(serialNo1) +
				" and differenceKind = " + Common.sqlChar(differenceKind) +
				":;:";
		execSQLArray = strSQL.split(":;:");
		db.excuteSQL(execSQLArray);
		setStateDeleteSuccess();
		setErrorMsg("刪除完成");				
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}	
}


/**
 * <br>
 * <br>目的：依主鍵查詢單一資料
 * <br>參數：無
 * <br>傳回：傳回本物件
*/

public UNTMP035F  queryOne() throws Exception{
	Database db = new Database();
	UNTMP035F obj = this;
	try {
		String sql=" select a.* "+
			" from UNTMP_Attachment1 a where 1=1 " +
			" and a.enterOrg = " + Common.sqlChar(enterOrg) +
			" and a.ownership = " + Common.sqlChar(ownership) +
			" and a.propertyNo = " + Common.sqlChar(propertyNo) +
			" and a.lotNo = " + Common.sqlChar(lotNo) +"";
		    if (serialNo1!=null){ // 範圍序號時serialNo1會為空的 造成queryone無選取的問題
		    	sql=sql+" and a.serialNo1 = " + Common.sqlChar(serialNo1) +"";
		    }
			sql=sql+" and a.differenceKind = " + Common.sqlChar(differenceKind) +"";
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setEnterOrg(rs.getString("enterOrg"));
			obj.setOwnership(rs.getString("ownership"));
			obj.setPropertyNo(rs.getString("propertyNo"));
			obj.setLotNo(rs.getString("lotNo"));
			obj.setSerialNo1(rs.getString("serialNo1"));
			obj.setEquipmentName(rs.getString("equipmentName"));
			obj.setBuyDate(new UNTCH_Tools()._transToROC_Year(rs.getString("buyDate")));
			obj.setEquipmentUnit(rs.getString("equipmentUnit"));
			obj.setEquipmentAmount(rs.getString("equipmentAmount"));
			obj.setUnitPrice(rs.getString("unitPrice"));
			obj.setTotalValue(rs.getString("totalValue"));
			obj.setNotes(rs.getString("notes"));
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(new UNTCH_Tools()._transToROC_Year(rs.getString("editDate")));
			obj.setEditTime(rs.getString("editTime"));
			obj.setDifferenceKind(rs.getString("differenceKind"));
			obj.setNameplate(rs.getString("nameplate"));
			obj.setSpecification(rs.getString("specification"));
			obj.setSourceKind(rs.getString("sourceKind"));
		}
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}

/**
 * <br>
 * <br>目的：依查詢欄位查詢多筆資料
 * <br>參數：無
 * <br>傳回：傳回ArrayList
*/

public ArrayList queryAll() throws Exception{
	Database db = new Database();
	ArrayList objList=new ArrayList();
	
	try {
		String sql=" select a.enterOrg, a.ownership, a.propertyNo, a.lotNo, a.serialNo1, a.equipmentName, a.equipmentAmount, a.unitPrice, a.totalValue "+
			" from UNTMP_Attachment1 a where 1=1 "; 
			if (!"".equals(getEnterOrg()))
				sql+=" and a.enterOrg = " + Common.sqlChar(getEnterOrg()) ;
			if (!"".equals(getOwnership()))
				sql+=" and a.ownership = " + Common.sqlChar(getOwnership()) ;
			if (!"".equals(getPropertyNo()))
				sql+=" and a.propertyNo = " + Common.sqlChar(getPropertyNo()) ;
			if (!"".equals(getLotNo()))
				sql+=" and a.lotNo = " + Common.sqlChar(getLotNo()) ;
			
		ResultSet rs = db.querySQL(sql,true);
		processCurrentPageAttribute(rs);			
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
			String rowArray[]=new String[9];
			rowArray[0]=rs.getString("enterOrg"); 
			rowArray[1]=rs.getString("ownership"); 
			rowArray[2]=rs.getString("propertyNo"); 
			rowArray[3]=rs.getString("lotNo"); 
			rowArray[4]=rs.getString("serialNo1"); 
			rowArray[5]=rs.getString("equipmentName"); 
			rowArray[6]=rs.getString("equipmentAmount"); 
			rowArray[7]=rs.getString("unitPrice"); 
			rowArray[8]=rs.getString("totalValue"); 
			objList.add(rowArray);
			count++;
			} while (rs.next());
		}
		setStateQueryAllSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return objList;
}

}


