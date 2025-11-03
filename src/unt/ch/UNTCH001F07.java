/*
*<br>程式目的：增加單及主檔維護_接收撥入資料
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean2;
import TDlib_Simple.tools.src.ReflectTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTCH001F07 extends UNTCH001Q07{

	private String differenceKind;
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}

	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_caseNo;
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNo(){ return checkGet(q_caseNo); }
	public void setQ_caseNo(String s){ q_caseNo=checkSet(s); }
	
	String enterOrg;
	String enterOrgName;
	String ownership;
	String caseNo;
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getCaseNo(){ return checkGet(caseNo); }
	public void setCaseNo(String s){ caseNo=checkSet(s); }
	
	String propertyKind;	//財產性質
	String fundType;		//基金財產
	public String getPropertyKind(){ return checkGet(propertyKind); }
	public void setPropertyKind(String s){ propertyKind=checkSet(s); }
	public String getFundType(){ return checkGet(fundType); }
	public void setFundType(String s){ fundType=checkSet(s); }
	
	String valuable;
	public String getValuable(){ return checkGet(valuable); }
	public void setValuable(String s){ valuable=checkSet(s); }
	
	String taxCredit;
	public String getTaxCredit(){ return checkGet(taxCredit); }
	public void setTaxCredit(String s){ taxCredit=checkSet(s); }
	
	String sourceDate;
	String sourceDoc;
	public String getSourceDate(){ return checkGet(sourceDate); }
	public void setSourceDate(String s){ sourceDate=checkSet(s); }
	public String getSourceDoc(){ return checkGet(sourceDoc); }
	public void setSourceDoc(String s){ sourceDoc=checkSet(s); }
	
	String manageOrg;
	String manageOrgName;
	public String getManageOrg(){ return checkGet(manageOrg); }
	public void setManageOrg(String s){ manageOrg=checkSet(s); }
	public String getManageOrgName(){ return checkGet(manageOrgName); }
	public void setManageOrgName(String s){ manageOrgName=checkSet(s); }
	
	String propertyNo;
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	String propertyNoName;
	public String getPropertyNoName(){ return checkGet(propertyNoName); }
	public void setPropertyNoName(String s){ propertyNoName=checkSet(s); }

	private String originalKeepUnit;
	private String originalKeeper;
	private String originalUseUnit;
	private String originalUser;
	private String originalUserNote;
	private String originalMoveDate;
	private String originalPlace1;
	private String originalPlace1Name;
	private String originalPlace;
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
	public String getOriginalMoveDate() {return checkGet(originalMoveDate);}
	public void setOriginalMoveDate(String originalMoveDate) {this.originalMoveDate = checkSet(originalMoveDate);}
	public String getOriginalPlace1() {return checkGet(originalPlace1);}
	public void setOriginalPlace1(String originalPlace1) {this.originalPlace1 = checkSet(originalPlace1);}
	public String getOriginalPlace1Name() {return checkGet(originalPlace1Name);}
	public void setOriginalPlace1Name(String originalPlace1Name) {this.originalPlace1Name = checkSet(originalPlace1Name);}
	public String getOriginalPlace() {return checkGet(originalPlace);}
	public void setOriginalPlace(String originalPlace) {this.originalPlace = checkSet(originalPlace);}

	private String originalDeprPark;
	private String originalDeprUnit;
	private String originalDeprUnit1;
	private String originalDeprAccounts;
	public String getOriginalDeprPark() {return checkGet(originalDeprPark);}
	public void setOriginalDeprPark(String originalDeprPark) {this.originalDeprPark = checkSet(originalDeprPark);}
	public String getOriginalDeprUnit() {return checkGet(originalDeprUnit);}
	public void setOriginalDeprUnit(String originalDeprUnit) {this.originalDeprUnit = checkSet(originalDeprUnit);}
	public String getOriginalDeprUnit1() {return checkGet(originalDeprUnit1);}
	public void setOriginalDeprUnit1(String originalDeprUnit1) {this.originalDeprUnit1 = checkSet(originalDeprUnit1);}
	public String getOriginalDeprAccounts() {return checkGet(originalDeprAccounts);}
	public void setOriginalDeprAccounts(String originalDeprAccounts) {this.originalDeprAccounts = checkSet(originalDeprAccounts);}
	
	private String q_ownershipName;

	public String getQ_ownershipName() {return checkGet(q_ownershipName);}
	public void setQ_ownershipName(String q_ownershipName) {this.q_ownershipName = checkSet(q_ownershipName);}
		
	
	//=====================================
	
	public void allInsert(){
		setEditDate(Datetime.getYYYMMDD());
		setEditTime(Datetime.getHHMMSS());		
		int getcut=0;
		if(getsKeySet()!=null)
			getcut = getsKeySet().length;	//有勾選的list中資料數
		String[] strKeys = null;
		int i = 0;	
		
		SuperBean2 sbSource = null;		//主檔資料
		SuperBean2 sbSource2 = null;	//減損單資料
		UNTCH001F02 sbTarget = new UNTCH001F02();
		UNTCH001F02 sbReduce = new UNTCH001F02();
		ReflectTools rf = new ReflectTools();
		try {
			for (i=0; i<getcut; i++) {	
				strKeys = getsKeySet()[i].split(",");

				String checkPropertyNo1 = strKeys[3].substring(0,1);
				String checkPropertyNo3 = strKeys[3].substring(0,3);
				
				if("111".equals(checkPropertyNo3)){
					sbSource = new unt.rf.UNTRF002F(this.getClass().getSimpleName());
					sbSource2 = new unt.rf.UNTRF009F();				
				}else if("1".equals(checkPropertyNo1)){
					sbSource = new unt.la.UNTLA002F();
					sbSource2 = new unt.la.UNTLA013F();
				}else if("2".equals(checkPropertyNo1)){
					sbSource = new unt.bu.UNTBU002F(this.getClass().getSimpleName());
					sbSource2 = new unt.bu.UNTBU016F();
				}else if("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)){
					sbSource = new unt.mp.UNTMP002F(this.getClass().getSimpleName());
					sbSource2 = new unt.mp.UNTMP015F();
				}else if("8".equals(checkPropertyNo1)){
					sbSource = new unt.rt.UNTRT001F(this.getClass().getSimpleName());
					sbSource2 = new UNTCH004F02_RT(this.getClass().getSimpleName());
				}else if("9".equals(checkPropertyNo1)){
					sbSource = new unt.vp.UNTVP001F();
					sbSource2 = new UNTCH004F02_VP();
				}
				
				sbSource.setEnterOrg(strKeys[0]);
				sbSource.setOwnership(strKeys[1]);
				sbSource.setDifferenceKind(strKeys[2]);
				sbSource.setPropertyNo(strKeys[3]);
				sbSource.setSerialNo(strKeys[4]);
				
				sbSource2.setEnterOrg(strKeys[0]);
				sbSource2.setOwnership(strKeys[1]);
				sbSource2.setDifferenceKind(strKeys[2]);
				sbSource2.setPropertyNo(strKeys[3]);
				sbSource2.setSerialNo(strKeys[4]);
				
				if(strKeys.length == 6){
					sbSource.setLotNo(strKeys[5]);
					sbSource2.setLotNo(strKeys[5]);
				}
				//讀入主檔資料
				sbSource.queryOne();
				rf._setParameter_forNoLog_bothInteraction(sbSource, sbTarget);
				
				//讀入減損單資料
				sbSource2.queryOne();
				rf._setParameter_forNoLog_bothInteraction(sbSource2, sbReduce);
				
				sbTarget.setEnterOrg(this.getEnterOrg());
//				sbTarget.setOwnership(this.getOwnership());
				sbTarget.setCaseNo(this.getCaseNo());
				sbTarget.setSerialNo("");
				sbTarget.setCaseSerialNo("");
				
				if(!"".equals(getDifferenceKind())){sbTarget.setDifferenceKind(getDifferenceKind());}
				
				sbTarget.setCause("102");
				sbTarget.setSourceKind("29");;
				sbTarget.setDataState("1");
				
				
				if(!"".equals(getPropertyKind())){	sbTarget.setPropertyKind(getPropertyKind());}
				if(!"".equals(getFundType())){		sbTarget.setFundType(getFundType());}
				if(!"".equals(getValuable())){		sbTarget.setValuable(getValuable());}
				if(!"".equals(getSourceDate())){	sbTarget.setSourceDate(getSourceDate());}
				if(!"".equals(getSourceDoc())){		sbTarget.setSourceDoc(getSourceDoc());}
				
				if(!"".equals(getOriginalKeepUnit())){	sbTarget.setOriginalKeepUnit(getOriginalKeepUnit());}
				if(!"".equals(getOriginalKeeper())){	sbTarget.setOriginalKeeper(getOriginalKeeper());}
				if(!"".equals(getOriginalUseUnit())){	sbTarget.setOriginalUseUnit(getOriginalUseUnit());}
				if(!"".equals(getOriginalUser())){		sbTarget.setOriginalUser(getOriginalUser());}
				
				if(!"".equals(getOriginalUser())){		sbTarget.setOriginalUser(getOriginalUser());}
				if(!"".equals(getOriginalUserNote())){	sbTarget.setOriginalUserNote(getOriginalUserNote());}
				if(!"".equals(getOriginalMoveDate())){	sbTarget.setOriginalMoveDate(getOriginalMoveDate());}
				if(!"".equals(getOriginalPlace1())){	sbTarget.setOriginalPlace1(getOriginalPlace1());}
				if(!"".equals(getOriginalPlace())){		sbTarget.setOriginalPlace(getOriginalPlace());}
				
				if(!"".equals(getOriginalDeprPark())){	sbTarget.setOriginalDeprPark(getOriginalDeprPark());}
				if(!"".equals(getOriginalDeprUnit())){	sbTarget.setOriginalDeprUnit(getOriginalDeprUnit());}
				if(!"".equals(getOriginalDeprUnit1())){	sbTarget.setOriginalDeprUnit1(getOriginalDeprUnit1());}
				if(!"".equals(getOriginalDeprAccounts())){sbTarget.setOriginalDeprAccounts(getOriginalDeprAccounts());}
				
				//104.12.24	問題單1258	面積相關欄位用減損單的現值寫入
				sbTarget.setOriginalAreaLa(sbReduce.getArea());
				sbTarget.setOriginalHoldNumeLa(sbReduce.getHoldNume());
				sbTarget.setOriginalHoldDenoLa(sbReduce.getHoldDeno());
				sbTarget.setOriginalHoldAreaLa(sbReduce.getHoldArea());
				
				sbTarget.setOriginalAreaBu(sbReduce.getArea());
				sbTarget.setOriginalHoldNumeBu(sbReduce.getHoldNume());
				sbTarget.setOriginalHoldDenoBu(sbReduce.getHoldDeno());
				sbTarget.setOriginalHoldAreaBu(sbReduce.getHoldArea());
				
				sbTarget.setOriginalArea(sbReduce.getArea());
				sbTarget.setOriginalHoldNume(sbReduce.getHoldNume());
				sbTarget.setOriginalHoldDeno(sbReduce.getHoldDeno());
				sbTarget.setOriginalHoldArea(sbReduce.getHoldArea());
				
				//104.12.24	問題單1258	價錢相關欄位用減損單的現值寫入
				sbTarget.setOriginalBV(sbReduce.getNetValue());
				sbTarget.setOriginalUnit(sbReduce.getNetUnit());
				sbTarget.setBookValue(sbReduce.getNetValue());
				sbTarget.setBookUnit(sbReduce.getNetUnit());
				sbTarget.setNetValue(sbReduce.getNetValue());
				sbTarget.setNetUnit(sbReduce.getNetUnit());

				sbTarget.setLaSignNo1(sbTarget.getSignNo1());
				sbTarget.setLaSignNo2(sbTarget.getSignNo2());
				sbTarget.setLaSignNo3(sbTarget.getSignNo3());
				sbTarget.setLaSignNo4(sbTarget.getSignNo4());
				sbTarget.setLaSignNo5(sbTarget.getSignNo5());
				
				sbTarget.setBuSignNo1(sbTarget.getSignNo1());
				sbTarget.setBuSignNo2(sbTarget.getSignNo2());
				sbTarget.setBuSignNo3(sbTarget.getSignNo3());
				sbTarget.setBuSignNo4(sbTarget.getSignNo4());
				sbTarget.setBuSignNo5(sbTarget.getSignNo5());
				
				sbTarget._execInsertforDetail();
				
				this.setErrorMsg(sbTarget.getErrorMsg());
				
				String[] params = {sbSource.getEnterOrg(),
									sbSource.getOwnership(),
									sbSource.getDifferenceKind(),
									sbSource.getPropertyNo(),
									sbSource.getSerialNo()};
				execUpdateNewEnterOrgReceive(params);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		private void execUpdateNewEnterOrgReceive(String[] params){
			String[] tables = {"UNTLA_REDUCEDETAIL",
								"UNTBU_REDUCEDETAIL",
								"UNTRF_REDUCEDETAIL",
								"UNTMP_REDUCEDETAIL",
								"UNTRT_REDUCEPROOF",
								"UNTVP_REDUCEPROOF"};
			
			Database db = new Database();
			for(String s : tables){
				String sql = "update " + s + " set newenterorgreceive = 'Y' " +
						" where 1=1" +
						" and newenterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and enterorg = " + Common.sqlChar(params[0]) +
						" and ownership = " + Common.sqlChar(params[1]) +
						" and differencekind = " + Common.sqlChar(params[2]) +
						" and propertyno = " + Common.sqlChar(params[3]) +
						" and serialno = " + Common.sqlChar(params[4]) +
						"";

				try{
					db.excuteSQL(sql);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			db.closeAll();
			
		}
	
	//=====================================
	public ArrayList queryAll(){
		Database db = new Database();
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			String sql = " select" + 
							" a.enterorg," +
							" ownership," +
							" differencekind," +
							" a.propertyno," +
							" serialno," +
							" null as lotno," +
							" z.propertyname," +
							" propertyname1," +
							" '1' as amount," +
							" netvalue," +
							" z.propertyunit," +
							" z.material," +
							" z.limityear" +
							" from UNTLA_REDUCEDETAIL a, SYSPK_PROPERTYCODE z" +
							" where 1=1" +
							" and z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno" +
							obtainQueryCondition() +
						" union" +
						" select" + 
							" a.enterorg," +
							" ownership," +
							" differencekind," +
							" a.propertyno," +
							" serialno," +
							" null as lotno," +
							" z.propertyname," +
							" propertyname1," +
							" '1' as amount," +
							" netvalue," +
							" z.propertyunit," +
							" z.material," +
							" z.limityear" +
							" from UNTBU_REDUCEDETAIL a, SYSPK_PROPERTYCODE z" +
							" where 1=1" +
							" and z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno" +
							obtainQueryCondition() +
						" union" +
						" select" + 
							" a.enterorg," +
							" ownership," +
							" differencekind," +
							" a.propertyno," +
							" serialno," +
							" null as lotno," +
							" z.propertyname," +
							" propertyname1," +
							" '1' as amount," +
							" netvalue," +
							" z.propertyunit," +
							" z.material," +
							" z.limityear" +
							" from UNTRF_REDUCEDETAIL a, SYSPK_PROPERTYCODE z" +
							" where 1=1" +
							" and z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno" +
							obtainQueryCondition() +
						" union" +
						" select" + 
							" a.enterorg," +
							" ownership," +
							" differencekind," +
							" a.propertyno," +
							" serialno," +
							" lotno," +
							" z.propertyname," +
							" propertyname1," +
							" newbookamount as amount," +
							" newnetvalue as netvalue," +
							" z.propertyunit," +
							" z.material," +
							" z.limityear" +
							" from UNTMP_REDUCEDETAIL a, SYSPK_PROPERTYCODE z" +
							" where 1=1" +
							" and z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno" +
							obtainQueryCondition() +
						" union" +
						" select" + 
							" a.enterorg," +
							" ownership," +
							" differencekind," +
							" a.propertyno," +
							" serialno," +
							" null as lotno," +
							" z.propertyname," +
							" propertyname1," +
							" '1' as amount," +
							" bookvalue as netvalue," +
							" z.propertyunit," +
							" z.material," +
							" z.limityear" +
							" from UNTRT_REDUCEPROOF a, SYSPK_PROPERTYCODE z" +
							" where 1=1" +
							" and z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno" +
							obtainQueryCondition() +
						" union" +
						" select" + 
							" a.enterorg," +
							" ownership," +
							" differencekind," +
							" a.propertyno," +
							" serialno," +
							" null as lotno," +
							" z.propertyname," +
							" propertyname1," +
							" bookamount as amount," +
							" bookvalue as netvalue," +
							" z.propertyunit," +
							" z.material," +
							" z.limityear" +
							" from UNTVP_REDUCEPROOF a, SYSPK_PROPERTYCODE z" +
							" where 1=1" +
							" and z.enterorg in ('000000000A',a.enterorg) and z.propertyno = a.propertyno" +
							obtainQueryCondition() +
							"";

			ResultSet rs = db.querySQL(sql);
			StringTools st = new StringTools();
			while (rs.next()){
				counter++;
				String rowArray[]=new String[13];
				
				rowArray[0] = checkGet(rs.getString("enterOrg"));
				rowArray[1] = checkGet(rs.getString("ownership"));
				rowArray[2] = checkGet(rs.getString("differenceKind"));
				rowArray[3] = checkGet(rs.getString("propertyNo")); 		
				rowArray[4] = checkGet(rs.getString("serialNo"));				
				rowArray[5] = checkGet(rs.getString("lotNo"));
				rowArray[6] = checkGet(rs.getString("propertyname"));		
				rowArray[7] = checkGet(rs.getString("propertyname1"));
				rowArray[8] = st._getMoneyFormat(checkGet(rs.getString("amount")));
				rowArray[9] = st._getMoneyFormat(checkGet(rs.getString("netvalue")));
				rowArray[10] = checkGet(rs.getString("propertyunit"));
				rowArray[11] = checkGet(rs.getString("material"));
				rowArray[12] = checkGet(rs.getString("limityear"));
				
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			setStateQueryAllSuccess();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		return objList;
	}

		private String obtainQueryCondition(){
			String sql = " and cause = '201'" +
					" and verify = 'Y'" +
					("".equals(getEnterOrg())?"":" and a.newEnterOrg = " + Common.sqlChar(getEnterOrg())) +
					("".equals(getQ_oldEnterOrg())?"":" and a.enterOrg = " + Common.sqlChar(getQ_oldEnterOrg())) +
					("".equals(getQ_differenceKind())?"":" and a.differenceKind = " + Common.sqlChar(getQ_differenceKind())) +
					("".equals(getQ_caseNo())?"":" and a.caseNo = " + Common.sqlChar(getQ_caseNo())) +
					("Y".equals(getQ_newEnterOrgReceive())?"":" and (a.newEnterOrgReceive != 'Y' or a.newEnterOrgReceive is null)");
			
			return sql;
		}

}


