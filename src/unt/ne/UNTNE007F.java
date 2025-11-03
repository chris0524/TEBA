package unt.ne;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import unt.ch.DataStructor;
import unt.ch.UNTCH001Q;
import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import TDlib_Simple.tools.src.ReflectTools;
import TDlib_Simple.tools.src.StringTools;

/**
 * <br/>程式目的：財產增加單及主檔維護_共同基本資料
 * <br/>程式代號：UNTCH001F02
 * <br/>程式日期：
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class UNTNE007F extends UNTCH001Q {
	//====================================================================		
	//預設值		
	@Override
	protected void _setDefaultValue() {
		
		UNTCH_Tools ut = new UNTCH_Tools();

		String checkStr = ut._checkPropertyNoType(this.getPropertyNo());
		
		this.setPlace1(getPlace1());
		this.setPlace(getPlace());
		
		if("".equals(Common.get(this.getCaseSerialNo()))){			
			DataStructor ds = new DataStructor();
			ds.enterOrg = this.getEnterOrg();
			ds.ownership = this.getOwnership();
			ds.caseNo = this.getCaseNo();
			ds.differenceKind = this.getDifferenceKind();
			
			String[] tables = new String[]{"UNTNE_NONEXPDETAIL"};
			
			this.setCaseSerialNo(ut._getNewCaseSerialNo(ds,tables));			
		}
		
		ut = null;
		checkStr = null;
	}
	
	
	
	//====================================================================	

	public UNTNE007F _execQueryOneforDetail(){
		
		ReflectTools rf = new ReflectTools();
		StringTools st = new StringTools();
		List fields = rf._getDeclaredFields_asList_fromObj(this);
		
		fields.remove("enterOrg");
		fields.remove("enterOrgName");
		fields.remove("ownership");
		fields.remove("ownershipName");
		fields.remove("caseNo");
		fields.remove("differenceKind");
		fields.remove("differenceKindName");
		fields.remove("engineeringNo");
		fields.remove("engineeringNoName");
		fields.remove("propertyNo");
		fields.remove("serialNo");
		fields.remove("serialNoS");
		fields.remove("serialNoE");
		fields.remove("lotNo");
		
		String methodName = "";
		for(Object o : fields){
			methodName = (String)o;
			methodName = st._HeadStringToUpper(methodName);
			rf._invokeSetMethod_fromObj(this, "set" + methodName, new Class[]{String.class}, new Object[]{""});					
		}
		
		//=======================================
		
		util.SuperBean2 sb = new unt.ne.UNTNE003F();	
		
		//=======================================
		sb.setEnterOrg(this.getEnterOrg());
		sb.setOwnership(this.getOwnership());
		sb.setDifferenceKind(this.getDifferenceKind());
		sb.setPropertyNo(this.getPropertyNo());
		sb.setSerialNo(this.getSerialNo());
		sb.setLotNo(this.getLotNo());
		//=======================================
			
		try{
			Object obj = sb.queryOne();
			UNTCH_Tools ul = new UNTCH_Tools();
			ul._setParameter(obj,this);
			
			setStateQueryOneSuccess();
			
			ul = null;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sb = null;
			rf = null;
			st = null;
			fields = null;
		}	
		return this;
	}
	
	/**
	 * <br>
	 * <br>目的：依查詢欄位查詢多筆資料
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	public ArrayList execQueryAll() throws Exception{
		Database db = null;
		ArrayList objList = null;
		
		try {
			StringBuilder sbSQL = new StringBuilder();
			StringBuilder getColumn = new StringBuilder();
			StringBuilder joinTable = new StringBuilder();
			
			joinTable.append(" left join UNTMP_KEEPUNIT uku on uku.enterorg = a.enterorg and uku.unitno = a.keepunit")
				.append(" left join UNTMP_KEEPER ukr on ukr.enterorg = a.enterorg and ukr.keeperno = a.keeper");
			sbSQL.append("select ROW_NUMBER() over (order by propertyno, serialno) as rownum,* from (")
				.append(" select")
				.append(" a.enterorg, ")
				.append(" a.ownership, ")
				.append(" a.differencekind, ")
				.append(" a.propertyno, ")
				.append(" a.serialno, ")
				.append(" a.propertyname1,")
				.append(" a.keepunit,")
				.append(" uku.unitname,")
				.append(" a.keeper,")
				.append(" ukr.keepername,")
				.append(" a.place1,")
				.append(" a.lotno")
				.append(" from UNTNE_NONEXPDETAIL a ")
				.append(joinTable.toString())
				.append(" where 1=1")
				.append(getQueryCondition())
				.append(") a");
		
			db = new Database();
			objList = new ArrayList();
			ResultSet rs = db.querySQL(sbSQL.toString(),true);
			
			UNTCH_Tools uctls = new UNTCH_Tools();
			StringTools st = new StringTools();
			// 問題單1386複測
			if (getMainPage1() != 0) {
				setCurrentPage(getMainPage1());
			}
			processCurrentPageAttribute(rs);			
			if (getTotalRecord() > 0) {
				int count = getRecordStart();
				int end = getRecordEnd();
				do {
					if (count > end)
						break;
					int fieldIndex = 0;
					String rowArray[]=new String[14];
					rowArray[fieldIndex++] = checkGet(rs.getString("rownum"));
					rowArray[fieldIndex++] = checkGet(rs.getString("enterorg")); 
					rowArray[fieldIndex++] = checkGet(rs.getString("ownership"));
					rowArray[fieldIndex++] = checkGet(rs.getString("differencekind"));
					rowArray[fieldIndex++] = uctls._getDifferenceKindName(rs.getString("differencekind"));
					rowArray[fieldIndex++] = checkGet(rs.getString("propertyno"));
					rowArray[fieldIndex++] = checkGet(rs.getString("serialno"));
					rowArray[fieldIndex++] = uctls._getPropertyNoName(enterOrg, rs.getString("propertyno"));
					rowArray[fieldIndex++] = checkGet(rs.getString("propertyname1")); 
					rowArray[fieldIndex++] = checkGet(rs.getString("unitname"));
					rowArray[fieldIndex++] = checkGet(rs.getString("keepername"));
					rowArray[fieldIndex++] = uctls.getPlace1Name(enterOrg, rs.getString("place1"));
					rowArray[fieldIndex++] = checkGet(rs.getString("lotno"));
			
				objList.add(rowArray);
				count++;
				} while (rs.next());
			}
			setStateQueryAllSuccess();		
			
			rs.close();
			
			sbSQL.setLength(0);
			sbSQL = null;			
			rs = null;
			uctls = null;
			st = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.closeAll();
			}
		}
		return objList;
	}
	
		private String getQueryCondition(){
			StringBuilder sqlCondition = new StringBuilder();
			sqlCondition.append(" and a.enterorg = ")
				.append(Common.sqlChar(this.getEnterOrg()))
				.append(" and a.keeper = ")
				.append(Common.sqlChar(this.getKeeperno()))
				.append(" and a.datastate = '1'");
			if (!"".equals(getQ_propertyNoS())){
				sqlCondition.append(" and a.propertyNo >= ").append(Common.sqlChar(getQ_propertyNoS()));
			}
			
			if (!"".equals(getQ_propertyNoE())){
				sqlCondition.append(" and a.propertyNo <= ").append(Common.sqlChar(getQ_propertyNoE()));
			}
			
			if (!"".equals(getQ_serialNoS())){
				sqlCondition.append(" and a.serialNo >= ").append(Common.sqlChar(getQ_serialNoS()));	
			}
			if (!"".equals(getQ_serialNoE())){
				sqlCondition.append(" and a.serialNo <= ").append(Common.sqlChar(getQ_serialNoE()));
			}
			
			if (!"".equals(getQ_propertyName1())){
				sqlCondition.append(" and a.propertyName1 like ").append(Common.sqlChar("%" + getQ_propertyName1() + "%"));
			}
			
			if (!"".equals(getQ_keepUnit())){
				sqlCondition.append(" and z.keepUnit = ").append(Common.sqlChar(getQ_keepUnit()));
			}
			
			if (!"".equals(getQ_place1())){
				sqlCondition.append(" and a.place1 = ").append(Common.sqlChar(getQ_place1()));
			}
			
			
			
			return sqlCondition.toString();
		}
	public void updatePlace() {
		String checkStr = new unt.ch.UNTCH_Tools()._checkPropertyNoType(this.getPropertyNo());
		String tableName = "UNTNE_NONEXPDETAIL";
		
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update ").append(tableName).append(" set ")
			.append("place = N").append(Common.sqlChar(this.getPlace()))
			.append(",place1 = N").append(Common.sqlChar(this.getPlace1()))
			.append(",editid = ").append(Common.sqlChar(this.getUserID()))
			.append(",editdate = ").append(Common.sqlChar(Datetime.getYYYYMMDD()))
			.append(",edittime = ").append(Common.sqlChar(Datetime.getHHMMSS()))
			.append("where enterorg = ").append(Common.sqlChar(this.getEnterOrg()))
			.append("and ownership = ").append(Common.sqlChar(this.getOwnership()))
			.append("and differencekind = ").append(Common.sqlChar(this.getDifferenceKind()))
			.append("and propertyno = ").append(Common.sqlChar(this.getPropertyNo()))
			.append("and serialno = ").append(Common.sqlChar(this.getSerialNo()));
		
		Database db = new Database();
		
		try{
			db.excuteSQL(updateSql.toString());		
			setStateUpdateSuccess();
			//使用者操作記錄
			if ("".equals(this.getObjPath())) {
				this.setObjPath("功能選單 > > 單位財產系統 > > 物品管理 > > 物品存置地點維護 > > 修改" + this.getPropertyNo() + " " + this.getSerialNo());
			}
			Common.insertUpdateRecordSql(updateSql.toString(), this.getEnterOrg(), this.getUserID(), "UNTNE007F", this.getObjPath().replaceAll("&gt;", ">"));
			setErrorMsg("修改完成");
		}catch(Exception e){
			super.setStateUpdateError();
			log._execLogError(e.getMessage());
		}finally {
			if (db != null) {
				db.closeAll();
			}
		}
	}
	
	private String enterOrgName;
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	
	private String placeName;
	private String placeNote;
	
	private String lotNo;		

	public String getPlaceName() {return checkGet(placeName);}
	public void setPlaceName(String placeName) {this.placeName = checkSet(placeName);}
	public String getPlaceNote() {return checkGet(placeNote);}
	public void setPlaceNote(String placeNote) {this.placeNote = checkSet(placeNote);}
	public String getLotNo() {return checkGet(lotNo);}
	public void setLotNo(String lotNo) {this.lotNo = checkSet(lotNo);}
	
	//=============================================================
	
	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	private String engineeringNo;
	private String engineeringNoName;
	private String caseSerialNo;
	private String propertyNo;
	private String serialNo;
	private String otherLimitYear;
	private String propertyName1;
	private String signNo;
	private String doorPlate1;
	private String doorPlate2;
	private String doorPlate4;
	private String doorPlatevillage1;
	private String doorPlatevillage2;
	private String doorplateRd1;
	private String doorplateRd2;
	private String doorplateSec;
	private String doorplateLn;
	private String doorplateAly;
	private String doorplateNo;
	private String doorplateFloor1;
	private String doorplateFloor2;
	private String buildStyle;
	private String cause;
	private String cause1;
	private String enterDate;
	private String dataState;
	private String reduceDate;
	private String reduceCause;
	private String reduceCause1;
	private String verify;
	private String propertyKind;
	private String fundType;
	private String valuable;
	private String taxCredit;
	private String originalCArea;
	private String originalSArea;
	private String originalArea;
	private String originalHoldNume;
	private String originalHoldDeno;
	private String originalHoldArea;
	private String CArea;
	private String SArea;
	private String area;
	private String holdNume;
	private String holdDeno;
	private String holdArea;
	private String originalBV;
	private String originalNote;
	private String accountingTitle;
	private String bookValue;
	private String netValue;
	private String fundsSource;
	private String fundsSource1;
	private String ownershipDate;
	private String ownershipCause;
	private String nonProof;
	private String ownershipNote;
	private String buildDate;
	private String floor1;
	private String floor2;
	private String stuff;
	private String buyDate;
	private String sourceKind;
	private String sourceDate;
	private String sourceDoc;
	private String manageOrg;
	
	private String originalBuildFeeCB;
	private String originalDeprUnitCB;
	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprAccounts;
	private String originalScrapValue;
	private String originalDeprAmount;
	private String originalaccumdepr1;	// 本年度累計折舊
	private String originalaccumdepr2;	// 以前年度累計折舊
	private String originalAccumDepr;
	private String originalApportionMonth;
	private String originalMonthDepr;
	private String deprMethod;
	private String buildFeeCB;
	private String deprUnitCB;
	private String deprPark;
	private String deprUnit;
	private String deprAccounts;
	private String scrapValue;
	private String deprAmount;
	private String accumDepr;
	private String apportionMonth;
	private String monthDepr;
	private String noDeprSet;
	private String appraiseDate;
	private String originalMoveDate;
	private String originalKeepUnit;
	private String originalKeeper;
	private String originalUseUnit;
	private String originalUser;
	private String originalUserNote;
	private String originalPlace1;
	private String originalPlace;
	private String moveDate;
	private String keepUnit;
	private String keeper;
	private String useUnit;
	private String userNo;
	private String userNote;
	private String place1;
	private String place1Name;
	private String place;
	private String useLicense;
	private String escrowOriValue;
	private String escrowOriAccumDepr;
	private String oldPropertyNo;
	private String oldSerialNo;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	public String getEngineeringNoName() {return checkGet(engineeringNoName);}
	public void setEngineeringNoName(String engineeringNoName) {this.engineeringNoName = checkSet(engineeringNoName);}
	public String getCaseSerialNo() {return checkGet(caseSerialNo);}
	public void setCaseSerialNo(String caseSerialNo) {this.caseSerialNo = checkSet(caseSerialNo);}
	public String getPropertyNo() {return checkGet(propertyNo);}
	public void setPropertyNo(String propertyNo) {this.propertyNo = checkSet(propertyNo);}
	public String getSerialNo() {return checkGet(serialNo);}
	public void setSerialNo(String serialNo) {this.serialNo = checkSet(serialNo);}
	public String getOtherLimitYear() {return checkGet(otherLimitYear);}
	public void setOtherLimitYear(String otherLimitYear) {this.otherLimitYear = checkSet(otherLimitYear);}
	public String getPropertyName1() {return checkGet(propertyName1);}
	public void setPropertyName1(String propertyName1) {this.propertyName1 = checkSet(propertyName1);}
	public String getSignNo() {return checkGet(signNo);}
	public void setSignNo(String signNo) {this.signNo = checkSet(signNo);}
	public String getDoorPlate1() {return checkGet(doorPlate1);}
	public void setDoorPlate1(String doorPlate1) {this.doorPlate1 = checkSet(doorPlate1);}
	public String getDoorPlate2() {return checkGet(doorPlate2);}
	public void setDoorPlate2(String doorPlate2) {this.doorPlate2 = checkSet(doorPlate2);}
	public String getDoorPlate4() {return checkGet(doorPlate4);}
	public void setDoorPlate4(String doorPlate4) {this.doorPlate4 = checkSet(doorPlate4);}
	public String getDoorPlatevillage1() {return checkGet(doorPlatevillage1);}
	public void setDoorPlatevillage1(String doorPlatevillage1) {this.doorPlatevillage1 = checkSet(doorPlatevillage1);}
	public String getDoorPlatevillage2() {return checkGet(doorPlatevillage2);}
	public void setDoorPlatevillage2(String doorPlatevillage2) {this.doorPlatevillage2 = checkSet(doorPlatevillage2);}
	public String getDoorplateRd1() {return checkGet(doorplateRd1);}
	public void setDoorplateRd1(String doorplateRd1) {this.doorplateRd1 = checkSet(doorplateRd1);}
	public String getDoorplateRd2() {return checkGet(doorplateRd2);}
	public void setDoorplateRd2(String doorplateRd2) {this.doorplateRd2 = checkSet(doorplateRd2);}
	public String getDoorplateSec() {return checkGet(doorplateSec);}
	public void setDoorplateSec(String doorplateSec) {this.doorplateSec = checkSet(doorplateSec);}
	public String getDoorplateLn() {return checkGet(doorplateLn);}
	public void setDoorplateLn(String doorplateLn) {this.doorplateLn = checkSet(doorplateLn);}
	public String getDoorplateAly() {return checkGet(doorplateAly);}
	public void setDoorplateAly(String doorplateAly) {this.doorplateAly = checkSet(doorplateAly);}
	public String getDoorplateNo() {return checkGet(doorplateNo);}
	public void setDoorplateNo(String doorplateNo) {this.doorplateNo = checkSet(doorplateNo);}
	public String getDoorplateFloor1() {return checkGet(doorplateFloor1);}
	public void setDoorplateFloor1(String doorplateFloor1) {this.doorplateFloor1 = checkSet(doorplateFloor1);}
	public String getDoorplateFloor2() {return checkGet(doorplateFloor2);}
	public void setDoorplateFloor2(String doorplateFloor2) {this.doorplateFloor2 = checkSet(doorplateFloor2);}
	public String getBuildStyle() {return checkGet(buildStyle);}
	public void setBuildStyle(String buildStyle) {this.buildStyle = checkSet(buildStyle);}
	public String getCause() {return checkGet(cause);}
	public void setCause(String cause) {this.cause = checkSet(cause);}
	public String getCause1() {return checkGet(cause1);}
	public void setCause1(String cause1) {this.cause1 = checkSet(cause1);}
	public String getEnterDate() {return checkGet(enterDate);}
	public void setEnterDate(String enterDate) {this.enterDate = checkSet(enterDate);}
	public String getDataState() {return checkGet(dataState);}
	public void setDataState(String dataState) {this.dataState = checkSet(dataState);}
	public String getReduceDate() {return checkGet(reduceDate);}
	public void setReduceDate(String reduceDate) {this.reduceDate = checkSet(reduceDate);}
	public String getReduceCause() {return checkGet(reduceCause);}
	public void setReduceCause(String reduceCause) {this.reduceCause = checkSet(reduceCause);}
	public String getReduceCause1() {return checkGet(reduceCause1);}
	public void setReduceCause1(String reduceCause1) {this.reduceCause1 = checkSet(reduceCause1);}
	public String getVerify() {return checkGet(verify);}
	public void setVerify(String verify) {this.verify = checkSet(verify);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getFundType() {return checkGet(fundType);}
	public void setFundType(String fundType) {this.fundType = checkSet(fundType);}
	public String getValuable() {return checkGet(valuable);}
	public void setValuable(String valuable) {this.valuable = checkSet(valuable);}
	public String getTaxCredit() {return checkGet(taxCredit);}
	public void setTaxCredit(String taxCredit) {this.taxCredit = checkSet(taxCredit);}
	public String getOriginalCArea() {return checkGet(originalCArea);}
	public void setOriginalCArea(String originalCArea) {this.originalCArea = checkSet(originalCArea);}
	public String getOriginalSArea() {return checkGet(originalSArea);}
	public void setOriginalSArea(String originalSArea) {this.originalSArea = checkSet(originalSArea);}
	public String getOriginalArea() {return checkGet(originalArea);}
	public void setOriginalArea(String originalArea) {this.originalArea = checkSet(originalArea);}
	public String getOriginalHoldNume() {return checkGet(originalHoldNume);}
	public void setOriginalHoldNume(String originalHoldNume) {this.originalHoldNume = checkSet(originalHoldNume);}
	public String getOriginalHoldDeno() {return checkGet(originalHoldDeno);}
	public void setOriginalHoldDeno(String originalHoldDeno) {this.originalHoldDeno = checkSet(originalHoldDeno);}
	public String getOriginalHoldArea() {return checkGet(originalHoldArea);}
	public void setOriginalHoldArea(String originalHoldArea) {this.originalHoldArea = checkSet(originalHoldArea);}
	public String getCArea() {return checkGet(CArea);}
	public void setCArea(String cArea) {CArea = checkSet(cArea);}
	public String getSArea() {return checkGet(SArea);}
	public void setSArea(String sArea) {SArea = checkSet(sArea);}
	public String getArea() {return checkGet(area);}
	public void setArea(String area) {this.area = checkSet(area);}
	public String getHoldNume() {return checkGet(holdNume);}
	public void setHoldNume(String holdNume) {this.holdNume = checkSet(holdNume);}
	public String getHoldDeno() {return checkGet(holdDeno);}
	public void setHoldDeno(String holdDeno) {this.holdDeno = checkSet(holdDeno);}
	public String getHoldArea() {return checkGet(holdArea);}
	public void setHoldArea(String holdArea) {this.holdArea = checkSet(holdArea);}
	public String getOriginalBV() {return checkGet(originalBV);}
	public void setOriginalBV(String originalBV) {this.originalBV = checkSet(originalBV);}
	public String getOriginalNote() {return checkGet(originalNote);}
	public void setOriginalNote(String originalNote) {this.originalNote = checkSet(originalNote);}
	public String getAccountingTitle() {return checkGet(accountingTitle);}
	public void setAccountingTitle(String accountingTitle) {this.accountingTitle = checkSet(accountingTitle);}
	public String getBookValue() {return checkGet(bookValue);}
	public void setBookValue(String bookValue) {this.bookValue = checkSet(bookValue);}
	public String getNetValue() {return checkGet(netValue);}
	public void setNetValue(String netValue) {this.netValue = checkSet(netValue);}
	public String getFundsSource() {return checkGet(fundsSource);}
	public void setFundsSource(String fundsSource) {this.fundsSource = checkSet(fundsSource);}
	public String getFundsSource1() {return checkGet(fundsSource1);}
	public void setFundsSource1(String fundsSource1) {this.fundsSource1 = checkSet(fundsSource1);}
	public String getOwnershipDate() {return checkGet(ownershipDate);}
	public void setOwnershipDate(String ownershipDate) {this.ownershipDate = checkSet(ownershipDate);}
	public String getOwnershipCause() {return checkGet(ownershipCause);}
	public void setOwnershipCause(String ownershipCause) {this.ownershipCause = checkSet(ownershipCause);}
	public String getNonProof() {return checkGet(nonProof);}
	public void setNonProof(String nonProof) {this.nonProof = checkSet(nonProof);}
	public String getOwnershipNote() {return checkGet(ownershipNote);}
	public void setOwnershipNote(String ownershipNote) {this.ownershipNote = checkSet(ownershipNote);}
	public String getBuildDate() {return checkGet(buildDate);}
	public void setBuildDate(String buildDate) {this.buildDate = checkSet(buildDate);}
	public String getFloor1() {return checkGet(floor1);}
	public void setFloor1(String floor1) {this.floor1 = checkSet(floor1);}
	public String getFloor2() {return checkGet(floor2);}
	public void setFloor2(String floor2) {this.floor2 = checkSet(floor2);}
	public String getStuff() {return checkGet(stuff);}
	public void setStuff(String stuff) {this.stuff = checkSet(stuff);}
	public String getBuyDate() {return checkGet(buyDate);}
	public void setBuyDate(String buyDate) {this.buyDate = checkSet(buyDate);}
	public String getSourceKind() {return checkGet(sourceKind);}
	public void setSourceKind(String sourceKind) {this.sourceKind = checkSet(sourceKind);}
	public String getSourceDate() {return checkGet(sourceDate);}
	public void setSourceDate(String sourceDate) {this.sourceDate = checkSet(sourceDate);}
	public String getSourceDoc() {return checkGet(sourceDoc);}
	public void setSourceDoc(String sourceDoc) {this.sourceDoc = checkSet(sourceDoc);}
	public String getManageOrg() {return checkGet(manageOrg);}
	public void setManageOrg(String manageOrg) {this.manageOrg = checkSet(manageOrg);}
	public String getOriginalBuildFeeCB() {return checkGet(originalBuildFeeCB);}
	public void setOriginalBuildFeeCB(String originalBuildFeeCB) {this.originalBuildFeeCB = checkSet(originalBuildFeeCB);}
	public String getOriginalDeprUnitCB() {return checkGet(originalDeprUnitCB);}
	public void setOriginalDeprUnitCB(String originalDeprUnitCB) {this.originalDeprUnitCB = checkSet(originalDeprUnitCB);}
	public String getOriginalDeprPark() {return checkGet(originalDeprPark);}
	public void setOriginalDeprPark(String originalDeprPark) {this.originalDeprPark = checkSet(originalDeprPark);}
	public String getOriginalDeprUnit() {return checkGet(originalDeprUnit);}
	public void setOriginalDeprUnit(String originalDeprUnit) {this.originalDeprUnit = checkSet(originalDeprUnit);}
	public String getOriginalDeprAccounts() {return checkGet(originalDeprAccounts);}
	public void setOriginalDeprAccounts(String originalDeprAccounts) {this.originalDeprAccounts = checkSet(originalDeprAccounts);}
	public String getOriginalScrapValue() {return checkGet(originalScrapValue);}
	public void setOriginalScrapValue(String originalScrapValue) {this.originalScrapValue = checkSet(originalScrapValue);}
	public String getOriginalDeprAmount() {return checkGet(originalDeprAmount);}
	public void setOriginalDeprAmount(String originalDeprAmount) {this.originalDeprAmount = checkSet(originalDeprAmount);}
	public String getOriginalaccumdepr1() { return checkGet(originalaccumdepr1); }
	public void setOriginalaccumdepr1(String originalaccumdepr1) { this.originalaccumdepr1 = checkSet(originalaccumdepr1); }
	public String getOriginalaccumdepr2() { return checkGet(originalaccumdepr2); }
	public void setOriginalaccumdepr2(String originalaccumdepr2) { this.originalaccumdepr2 = checkSet(originalaccumdepr2); }
	public String getOriginalAccumDepr() {return checkGet(originalAccumDepr);}
	public void setOriginalAccumDepr(String originalAccumDepr) {this.originalAccumDepr = checkSet(originalAccumDepr);}
	public String getOriginalApportionMonth() {return checkGet(originalApportionMonth);}
	public void setOriginalApportionMonth(String originalApportionMonth) {this.originalApportionMonth = checkSet(originalApportionMonth);}
	public String getOriginalMonthDepr() {return checkGet(originalMonthDepr);}
	public void setOriginalMonthDepr(String originalMonthDepr) {this.originalMonthDepr = checkSet(originalMonthDepr);}
	public String getDeprMethod() {return checkGet(deprMethod);}
	public void setDeprMethod(String deprMethod) {this.deprMethod = checkSet(deprMethod);}
	public String getBuildFeeCB() {return checkGet(buildFeeCB);}
	public void setBuildFeeCB(String buildFeeCB) {this.buildFeeCB = checkSet(buildFeeCB);}
	public String getDeprUnitCB() {return checkGet(deprUnitCB);}
	public void setDeprUnitCB(String deprUnitCB) {this.deprUnitCB = checkSet(deprUnitCB);}
	public String getDeprPark() {return checkGet(deprPark);}
	public void setDeprPark(String deprPark) {this.deprPark = checkSet(deprPark);}
	public String getDeprUnit() {return checkGet(deprUnit);}
	public void setDeprUnit(String deprUnit) {this.deprUnit = checkSet(deprUnit);}
	public String getDeprAccounts() {return checkGet(deprAccounts);}
	public void setDeprAccounts(String deprAccounts) {this.deprAccounts = checkSet(deprAccounts);}
	public String getScrapValue() {return checkGet(scrapValue);}
	public void setScrapValue(String scrapValue) {this.scrapValue = checkSet(scrapValue);}
	public String getDeprAmount() {return checkGet(deprAmount);}
	public void setDeprAmount(String deprAmount) {this.deprAmount = checkSet(deprAmount);}
	public String getAccumDepr() {return checkGet(accumDepr);}
	public void setAccumDepr(String accumDepr) {this.accumDepr = checkSet(accumDepr);}
	public String getApportionMonth() {return checkGet(apportionMonth);}
	public void setApportionMonth(String apportionMonth) {this.apportionMonth = checkSet(apportionMonth);}
	public String getMonthDepr() {return checkGet(monthDepr);}
	public void setMonthDepr(String monthDepr) {this.monthDepr = checkSet(monthDepr);}
	public String getNoDeprSet() {return checkGet(noDeprSet);}
	public void setNoDeprSet(String noDeprSet) {this.noDeprSet = checkSet(noDeprSet);}
	public String getAppraiseDate() {return checkGet(appraiseDate);}
	public void setAppraiseDate(String appraiseDate) {this.appraiseDate = checkSet(appraiseDate);}
	public String getOriginalMoveDate() {return checkGet(originalMoveDate);}
	public void setOriginalMoveDate(String originalMoveDate) {this.originalMoveDate = checkSet(originalMoveDate);}
	public String getOriginalKeepUnit() {return checkGet(originalKeepUnit);}
	public void setOriginalKeepUnit(String originalKeepUnit) {this.originalKeepUnit = checkSet(originalKeepUnit);}
	public String getOriginalKeeper() {return checkGet(originalKeeper);}
	public void setOriginalKeeper(String originalKeeper) {this.originalKeeper = checkSet(originalKeeper);}
	public String getOriginalUseUnit() {return checkGet(originalUseUnit);}
	public void setOriginalUseUnit(String originalUseUnit) {this.originalUseUnit = checkSet(originalUseUnit);}
	public String getOriginalUser() {return checkGet(originalUser);}
	public void setOriginalUser(String originalUser) {this.originalUser = checkSet(originalUser);}
	public String getOriginalUserNote() {return checkGet(originalUserNote);}
	public void setOriginalUserNote(String originalUserNote) {this.originalUserNote = checkSet(originalUserNote);}
	public String getOriginalPlace1() {return checkGet(originalPlace1);}
	public void setOriginalPlace1(String originalPlace1) {this.originalPlace1 = checkSet(originalPlace1);}
	public String getOriginalPlace() {return checkGet(originalPlace);}
	public void setOriginalPlace(String originalPlace) {this.originalPlace = checkSet(originalPlace);}
	public String getMoveDate() {return checkGet(moveDate);}
	public void setMoveDate(String moveDate) {this.moveDate = checkSet(moveDate);}
	public String getKeepUnit() {return checkGet(keepUnit);}
	public void setKeepUnit(String keepUnit) {this.keepUnit = checkSet(keepUnit);}
	public String getKeeper() {return checkGet(keeper);}
	public void setKeeper(String keeper) {this.keeper = checkSet(keeper);}
	public String getUseUnit() {return checkGet(useUnit);}
	public void setUseUnit(String useUnit) {this.useUnit = checkSet(useUnit);}
	public String getUserNo() {return checkGet(userNo);}
	public void setUserNo(String userNo) {this.userNo = checkSet(userNo);}
	public String getUserNote() {return checkGet(userNote);}
	public void setUserNote(String userNote) {this.userNote = checkSet(userNote);}
	public String getPlace1() {return checkGet(place1);}
	public void setPlace1(String place1) {this.place1 = checkSet(place1);}
	public String getPlace() {return checkGet(place);}
	public void setPlace(String place) {this.place = checkSet(place);}
	public String getPlace1Name() { return checkGet(place1Name); }
	public void setPlace1Name(String place1Name) { this.place1Name = checkSet(place1Name); }
	public String getUseLicense() {return checkGet(useLicense);}
	public void setUseLicense(String useLicense) {this.useLicense = checkSet(useLicense);}
	public String getEscrowOriValue() {return checkGet(escrowOriValue);}
	public void setEscrowOriValue(String escrowOriValue) {this.escrowOriValue = checkSet(escrowOriValue);}
	public String getEscrowOriAccumDepr() {return checkGet(escrowOriAccumDepr);}
	public void setEscrowOriAccumDepr(String escrowOriAccumDepr) {this.escrowOriAccumDepr = checkSet(escrowOriAccumDepr);}
	public String getOldPropertyNo() {return checkGet(oldPropertyNo);}
	public void setOldPropertyNo(String oldPropertyNo) {this.oldPropertyNo = checkSet(oldPropertyNo);}
	public String getOldSerialNo() {return checkGet(oldSerialNo);}
	public void setOldSerialNo(String oldSerialNo) {this.oldSerialNo = checkSet(oldSerialNo);}
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	
	private String originalDeprMethod;
	public String getOriginalDeprMethod() {return checkGet(originalDeprMethod);}
	public void setOriginalDeprMethod(String originalDeprMethod) {this.originalDeprMethod = checkSet(originalDeprMethod);}	
	private String originalPlace1Name;
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}

	private String originalDeprUnit1;
	private String deprUnit1;	
	public String getOriginalDeprUnit1() {return checkGet(originalDeprUnit1);}
	public void setOriginalDeprUnit1(String originalDeprUnit1) {this.originalDeprUnit1 = checkSet(originalDeprUnit1);}
	public String getDeprUnit1() {return checkGet(deprUnit1);}
	public void setDeprUnit1(String deprUnit1) {this.deprUnit1 = checkSet(deprUnit1);}
	
	private String originalApportionEndYM;
	public String getOriginalApportionEndYM() {return checkGet(originalApportionEndYM);}
	public void setOriginalApportionEndYM(String originalApportionEndYM) {this.originalApportionEndYM = checkSet(originalApportionEndYM);}	
	
	private String capital;
	private String licensePlate;
	private String purpose;
	public String getCapital() {return checkGet(capital);}
	public void setCapital(String capital) {this.capital = checkSet(capital);}
	public String getLicensePlate() {return checkGet(licensePlate);}
	public void setLicensePlate(String licensePlate) {this.licensePlate = checkSet(licensePlate);}
	public String getPurpose() {return checkGet(purpose);}
	public void setPurpose(String purpose) {this.purpose = checkSet(purpose);}	
	
	private String originalMonthDepr1;
	public String getOriginalMonthDepr1() {return checkGet(originalMonthDepr1);}
	public void setOriginalMonthDepr1(String originalMonthDepr1) {this.originalMonthDepr1 = checkSet(originalMonthDepr1);}
	private String monthDepr1;
	public String getMonthDepr1() {return checkGet(monthDepr1);}
	public void setMonthDepr1(String monthDepr1) {this.monthDepr1 = checkSet(monthDepr1);}	
	
	String proofYear;
	String proofDoc;
	String proofNo;
	String summonsDate;
	public String getProofDoc(){ return checkGet(proofDoc); }
	public void setProofDoc(String s){ proofDoc=checkSet(s); }
	public String getProofNo(){ return checkGet(proofNo); }
	public void setProofNo(String s){ proofNo=checkSet(s); }	
	public String getSummonsDate() {return checkGet(summonsDate);}
	public void setSummonsDate(String summonsDate) {this.summonsDate = checkSet(summonsDate);}	
	public String getProofYear() {return checkGet(proofYear);}
	public void setProofYear(String proofYear) {this.proofYear = checkSet(proofYear);}
	
	String originalLimitYear;
	public String getOriginalLimitYear() {return checkGet(originalLimitYear);}
	public void setOriginalLimitYear(String originalLimitYear) {this.originalLimitYear = checkSet(originalLimitYear);}	
	
	private String RTLimitYear;
	public String getRTLimitYear() { return checkGet(RTLimitYear); }
	public void setRTLimitYear(String rTLimitYear) { RTLimitYear = checkSet(rTLimitYear); }

	private String propertyNoName;
	public String getPropertyNoName() {return checkGet(propertyNoName);}
	public void setPropertyNoName(String propertyNoName) {this.propertyNoName = checkSet(propertyNoName);}
	
}