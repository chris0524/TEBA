
package unt.ch;

import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Common;
import util.Database;
import util.NewDateUtil;
import util.NewDateUtil.DateFormat;
import util.SuperBean2;
import TDlib_Simple.tools.src.ExcelTools_ByPOI;
import TDlib_Simple.tools.src.MathTools;
import TDlib_Simple.tools.src.StringTools;

public class UNTCH001F08 extends SuperBean2{

	private String progType;
	public String getProgType() {return checkGet(progType);}
	public void setProgType(String progType) { this.progType = checkSet(progType);	}

	private String enterOrg;
	private String ownership;
	private String caseNo;
	private String differenceKind;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getCaseNo() {return checkGet(caseNo);}
	public void setCaseNo(String caseNo) {this.caseNo = checkSet(caseNo);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}

	private String caseNo_Reduce;
	public String getCaseNo_Reduce() {return checkGet(caseNo_Reduce);}
	public void setCaseNo_Reduce(String caseNo_Reduce) {this.caseNo_Reduce = checkSet(caseNo_Reduce);}

	private String actionType;
	private String filePlace;
	private String filePath;
	private String fileName;
	public String getActionType() {return checkGet(actionType);}
	public void setActionType(String actionType) {this.actionType = checkSet(actionType);}
	public String getFilePlace() {return checkGet(filePlace);}
	public void setFilePlace(String filePlace) {this.filePlace = checkSet(filePlace);}
	public String getFilePath() {return checkGet(filePath);}
	public void setFilePath(String filePath) {this.filePath = checkSet(filePath);}
	public String getFileName() {return checkGet(fileName);}
	public void setFileName(String fileName) {this.fileName = checkSet(fileName);}
	
	private String engineeringNo;
	public String getEngineeringNo() {return checkGet(engineeringNo);}
	public void setEngineeringNo(String engineeringNo) {this.engineeringNo = checkSet(engineeringNo);}
	
	private String mergeDivision;
	public String getMergeDivision() {return checkGet(mergeDivision);}
	public void setMergeDivision(String mergeDivision) {this.mergeDivision = checkSet(mergeDivision);}
	
	private String erTitle;
	public String getErTitle() {return checkGet(erTitle);}
	public void setErTitle(String erTitle) {this.erTitle = checkSet(erTitle);}
	
	//必填欄位
	private Map pkdata = new HashMap(); 
			
	public String batchInsert(){
		String result = "";
		ExcelTools_ByPOI et = new ExcelTools_ByPOI();
		String[][] datas = null;
		List listError = new ArrayList();
		Iterator iter = null;
		int col = 1;		
		//=================================================
		if("UNTCH001F08".equals(this.progType)){
			initPKDataforUNTCH001F08();
		}else if("UNTCH006F01".equals(this.progType)){	
			initPKDataforUNTCH006F01();
		}		
		
		
		et._setFilePath(this.getFilePath());
		et._setFileName(this.getFileName());
		
		//=================================================
		
		datas = et._readExcel_asStringArray(0);

		//=================================================
		
		boolean isTitle = true;
		
		//使用UNTCH001F02類別儲存資料
		UNTCH001F02 ut = new UNTCH001F02();
		
		for(String[] data : datas){
			if(isTitle){
				if("UNTCH001F08".equals(this.progType)){ //財產增加單檢查標題格式
					String errorTitle = checkTitleData(data);
					if(errorTitle.length() > 0) {
						setErTitle(errorTitle);
						return result = "erTitle";
					}
				}
				isTitle = false;		//標題行跳過不處理
			}else{
				//雙引號跟單引號換成全形
				for(int i=0; i<data.length; i++){
					data[i] = data[i].replaceAll("\"","”");
					data[i] = data[i].replaceAll("'","’");
					
					//將整筆面積(㎡)千分位去除
					if (i == 7) {
						data[i] = data[i].replaceAll(",", "");
					}
				}
				
				Object[] checkData = checkPKData(data, col, listError);	//檢查必填欄位
				
				if((Boolean)checkData[0] == true){	//true:有錯誤不處理此筆資料
					
				}else{
					ut.setEnterOrg(getEnterOrg());
					ut.setOwnership(getOwnership());
					ut.setCaseNo(getCaseNo());
					ut.setDifferenceKind(getDifferenceKind());
					ut.setDataState("1");
					ut.setVerify("N");
					ut.setEngineeringNo(getEngineeringNo());
					ut.setMergeDivision(getMergeDivision());
					
					if(!"".equals(Common.get(data[0]))){		//財產編號不為空
						if("UNTCH001F08".equals(progType)){		
							//開始set資料
							ut = forUNTCH001F08(ut, data);
						}else if("UNTCH006F01".equals(progType)){	
							ut = forUNTCH006F01(ut, data);
						}
					}
					//執行資料insert
					ut._execInsertforDetail();
					ut.setCaseSerialNo(null);
				}
			}
			col++;
		}
		
		if("UNTCH006F01".equals(this.progType)){
			calBookValueforUNTCH006F01();
		}	
		
		if(listError.isEmpty()){
			result = "doImportSuccess";
		}else{
			result = "doImportLog";
			
			File file = new File(this.getFilePath() + File.separator + "errorList.txt");
			try{
				file.createNewFile();
				FileWriter out = new FileWriter(file,true);
				
				Iterator erroriter = listError.iterator();
				while(erroriter.hasNext()){
					out.append(erroriter.next().toString()).append("\r\n");
				}
				out.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		return result;
	}

	private UNTCH001F02 forUNTCH001F08(UNTCH001F02 ut, String[] data){
		MathTools mt = new MathTools();
		String propertyType = checkGet(data[0]).substring(0,1);
		String propertyNo = checkGet(data[0]);
		
		// 取得財產編號的SYSPK_PROPERTYCODE資料, 用來get 單位、材質、年限的預設值
		Map<String, String> propertycode = this.getSyspkPropertycode(propertyNo);
		
		ut.setPropertyNo(propertyNo);
		if(checkGet(data[1]).length()==6){
			ut.setBuyDate("0"+checkGet(data[1]));	
		}else{
			ut.setBuyDate(checkGet(data[1]));
		}		
		ut.setPropertyName1(checkGet(data[2]));
		ut.setSourceKind((splitListData(checkGet(data[3]))));
		if(checkGet(data[4]).length()==6){
			ut.setSourceDate("0"+checkGet(data[4]));	
		}else{
			ut.setSourceDate(checkGet(data[4]));
		}		
		ut.setSourceDoc(checkGet(data[5]));
		ut.setPropertyKind(Common.formatFrontZero(splitListData(checkGet(data[6])),2));
		ut.setFundType(splitListData(checkGet(data[7])));
		ut.setValuable(("是".equals(checkGet(data[8]))?"Y":"N"));
		
		ut.setOriginalCArea(checkGet(data[9]));
		ut.setCArea(checkGet(data[9]));
		ut.setOriginalSArea(checkGet(data[10]));
		ut.setSArea(checkGet(data[10]));
		ut.setOriginalArea(checkGet(data[11]));
		ut.setArea(checkGet(data[11]));
		ut.setOriginalAreaLa(checkGet(data[11]));
		ut.setOriginalAreaBu(checkGet(data[11]));
		ut.setOriginalHoldNume(checkGet(data[12]));
		ut.setHoldNume(checkGet(data[12]));
		ut.setOriginalHoldNumeLa(checkGet(data[12]));
		ut.setOriginalHoldNumeBu(checkGet(data[12]));
		ut.setOriginalHoldDeno(checkGet(data[13]));
		ut.setHoldDeno(checkGet(data[13]));
		ut.setOriginalHoldDenoLa(checkGet(data[13]));
		ut.setOriginalHoldDenoBu(checkGet(data[13]));

		String holdarea = "0";
		if("".equals(checkGet(data[13])) || "0".equals(data[13])){						
		}else{
			holdarea = mt._multiplication_withBigDecimal(data[11], mt._division_withBigDecimal(data[12], data[13]));
		}
		
		ut.setOriginalHoldArea((holdarea));
		ut.setHoldArea((holdarea));
		ut.setOriginalHoldAreaLa((holdarea));
		ut.setOriginalHoldAreaBu((holdarea));
		
		// 數量
		String amount = checkGet(data[14]);
		ut.setBookAmount(amount);					
		ut.setOriginalAmount(amount);
		ut.setOriginalMeasure(amount);
		ut.setMeasure(amount);
		data[16] = mt._division_withBigDecimal(checkGet(data[16]),1,0);
		
		//計算單價, 土地、建物計算權利範圍
		if ("".equals(data[15])) {		//未填入值就開始計算
			String area = "0"; //權利面積
			
			if(propertyType.equals("1") || propertyType.equals("2")){ 
				area = mt._division_withBigDecimal(	mt._multiplication_withBigDecimal(checkGet(data[11]), checkGet(data[12])),checkGet(data[13]));
				data[15] = mt._division_withBigDecimal(data[16], area, 2);	//土地取到小數第2位
			}else{
				data[15] = mt._division_withBigDecimal(data[16], amount, 0);	//其他取整數
			}				
		}
		
		if(propertyType.equals("1")){
			ut.setOriginalUnit(String.valueOf(mt._division_withBigDecimal(checkGet(data[15]),1,2)));
		}else{
			ut.setOriginalUnit(String.valueOf(mt._division_withBigDecimal(checkGet(data[15]),1,0)));
		}								
		
		if(ut.getPropertyNo().substring(0,1).equals("1") && ut.getPropertyNo().substring(0,3).equals("111")){
			ut.setBookUnit(checkGet(data[16]));
		}else{
			ut.setBookUnit(checkGet(data[15]));	
		}
		
		ut.setNetUnit(checkGet(data[15]));
		ut.setOriginalBV(checkGet(data[16]));
		ut.setBookValue(checkGet(data[16]));
		ut.setNetValue(checkGet(data[16]));
		
		ut.setSpecification(checkGet(data[17]));
		ut.setNameplate(checkGet(data[18]));
		ut.setOriginalMoveDate(checkGet(data[19]));
		ut.setMoveDate(checkGet(data[19]));
		ut.setOriginalKeepUnit(checkGet(data[20]));
		ut.setKeepUnit(checkGet(data[20]));
		ut.setOriginalKeeper(checkGet(data[21]));
		ut.setKeeper(checkGet(data[21]));
		ut.setOriginalUseUnit(checkGet(data[22]));
		ut.setUseUnit(checkGet(data[22]));
		ut.setOriginalUser(checkGet(data[23]));
		ut.setUserNo(checkGet(data[23]));
		ut.setOriginalUserNote(checkGet(data[24]));
		ut.setUserNote(checkGet(data[24]));
		ut.setOriginalPlace1(checkGet(data[25]));
		ut.setPlace1(checkGet(data[25]));
		ut.setOriginalPlace(checkGet(data[26]));
		ut.setPlace(checkGet(data[26]));
		//處理土地建物標示
		if(ut.getPropertyNo().substring(0,3).equals("111")){	//土改不處理				
		}else if(ut.getPropertyNo().substring(0,1).equals("1") || ut.getPropertyNo().substring(0,1).equals("2")){
			String signno = "";
			String signno_landNumber = Common.formatFrontZero(data[33].replaceAll("-", ""),8); //從前方補'0'到8碼
			signno = data[27] + data[29] + data[31] + signno_landNumber;

			ut.setSignNo((signno));
			ut.setLaSignNo1(data[27] + "000000");
			ut.setLaSignNo2(data[27] + data[29] + "0000");
			ut.setLaSignNo3(data[27] + data[29] + data[31]);
			ut.setLaSignNo4(signno_landNumber.substring(0,4));
			ut.setLaSignNo5(signno_landNumber.substring(4));
			
			ut.setBuSignNo1(data[27] + "000000");
			ut.setBuSignNo2(data[27] + data[29] + "0000");
			ut.setBuSignNo3(data[27] + data[29] + data[31]);
			ut.setBuSignNo4(signno_landNumber.substring(0,5));
			ut.setBuSignNo5(signno_landNumber.substring(5));
		}
		
		//年限(自訂)折舊會用到, 先處理	//問題單1718 年限(自訂)改為輸入月份:年限(月)(自訂), originallimityear都使用財編檔年限
		ut.setOriginalLimitYear(propertycode.get("limityear"));
		String inputlimityear = checkGet(data[45]);
		if (!"".equals(inputlimityear)){	//excel有輸入值
			ut.setOtherLimitYear(inputlimityear);
		} else {	//沒輸入就讀財編檔的值
			ut.setOtherLimitYear(String.valueOf(Common.getInt(propertycode.get("limityear")) * 12));
		}
		
		// ===== 以下為折舊相關欄位 =====
		ut.setOriginalDeprMethod(splitListData(checkGet(data[34])));
		ut.setDeprMethod(splitListData(checkGet(data[34])));
		
		if(ut.getOriginalDeprMethod().equals("02")){	//折舊方法是'02：直線平均法，不留殘值'
			ut.setOriginalBuildFeeCB("是".equals(checkGet(data[35]))?"Y":"N");
			ut.setBuildFeeCB(ut.getOriginalBuildFeeCB());
			ut.setOriginalDeprUnitCB("是".equals(checkGet(data[36]))?"Y":"N");
			ut.setDeprUnitCB(ut.getOriginalDeprUnitCB());
			if(ut.getOriginalDeprUnitCB().equals("N")){	//部門別依比例分攤為'否', 就需處理園區別、部門別、部門別單位、會計科目
				ut.setOriginalDeprPark(checkGet(data[37]));
				ut.setDeprPark(checkGet(data[37]));
				ut.setOriginalDeprUnit(checkGet(data[38]));
				ut.setDeprUnit(checkGet(data[38]));
				ut.setOriginalDeprUnit1(checkGet(data[39]));
				ut.setDeprUnit1(checkGet(data[39]));
				ut.setOriginalDeprAccounts(checkGet(data[40]));
				ut.setDeprAccounts(checkGet(data[40]));
			}				
			//殘值預設為0
			ut.setOriginalScrapValue((checkGet(data[41]).equals(""))?"0":checkGet(data[41]));
			ut.setScrapValue(ut.getOriginalScrapValue());
			// 應攤提折舊總額預設為與總價一致，數量>1取單價
			if (Common.getNumeric(amount) > 1) {
				ut.setOriginalDeprAmount(ut.getBookUnit());
				ut.setDeprAmount(ut.getBookUnit());
			} else {
				ut.setOriginalDeprAmount(ut.getOriginalBV());
				ut.setDeprAmount(ut.getOriginalBV());
			}
			//累計折舊預設為0
			ut.setOriginalAccumDepr("0");
			ut.setAccumDepr("0");
			//data[42]攤提壽月應該與data[45]使用年限一致(otherlimityear)	
			ut.setOriginalApportionMonth(ut.getOtherLimitYear());	
			ut.setApportionMonth(ut.getOtherLimitYear());
			//月提折舊金額=應攤提折舊總額/攤提壽月 取整數
			if(!ut.getOriginalApportionMonth().equals("")){
				int originalMonthDepr = Common.getInt(ut.getOriginalDeprAmount()) / Common.getInt(ut.getOriginalApportionMonth());
				int originalMonthDepr1 = Common.getInt(ut.getOriginalDeprAmount()) - originalMonthDepr * (Common.getInt(ut.getOriginalApportionMonth())-1);
				ut.setOriginalMonthDepr(String.valueOf(originalMonthDepr));
				ut.setOriginalMonthDepr1(String.valueOf(originalMonthDepr1));
				ut.setMonthDepr(ut.getOriginalMonthDepr());
				ut.setMonthDepr1(ut.getOriginalMonthDepr1());
			}			
			//攤提年限截止年月 = 購置日期 + 使用年限(otherlimityear)
			if(!ut.getOtherLimitYear().equals("")){
				String originalApportionEndYM = NewDateUtil.plusDate(ut.getBuyDate(), "m", Common.getInt(ut.getOtherLimitYear()), NewDateUtil.DateFormat.YYYMM);
				ut.setOriginalApportionEndYM(originalApportionEndYM);
				ut.setApportionEndYM(originalApportionEndYM);
			}
		}else if(ut.getOriginalDeprMethod().equals("03")){	//問題單1310:增加 折舊方法是'03：直線平均法，留殘值'
			ut.setOriginalBuildFeeCB("是".equals(checkGet(data[35]))?"Y":"N");
			ut.setBuildFeeCB(ut.getOriginalBuildFeeCB());
			ut.setOriginalDeprUnitCB("是".equals(checkGet(data[36]))?"Y":"N");
			ut.setDeprUnitCB(ut.getOriginalDeprUnitCB());
			if(ut.getOriginalDeprUnitCB().equals("N")){	//部門別依比例分攤為'否', 就需處理園區別、部門別、部門別單位、會計科目
				ut.setOriginalDeprPark(checkGet(data[37]));
				ut.setDeprPark(checkGet(data[37]));
				ut.setOriginalDeprUnit(checkGet(data[38]));
				ut.setDeprUnit(checkGet(data[38]));
				ut.setOriginalDeprUnit1(checkGet(data[39]));
				ut.setDeprUnit1(checkGet(data[39]));
				ut.setOriginalDeprAccounts(checkGet(data[40]));
				ut.setDeprAccounts(checkGet(data[40]));
			}
			// 數量>1取單價
			String value;
			if (Common.getNumeric(amount) > 1) {
				value = ut.getOriginalUnit();
			} else {
				value = ut.getOriginalBV();
			}
			//公務財產殘值預設為1%, 權利殘值為0
			int scrapvalue = ("8".equals(ut.getPropertyNo().substring(0,1)))?0:Math.round(Float.parseFloat(value) / 100);
			ut.setOriginalScrapValue(String.valueOf(scrapvalue));
			ut.setScrapValue(String.valueOf(scrapvalue));
			// 應攤提折舊總額為總價-殘值
			ut.setOriginalDeprAmount(String.valueOf(Common.getInt(value)-scrapvalue));
			ut.setDeprAmount(ut.getOriginalDeprAmount());
			//累計折舊預設為0
			ut.setOriginalAccumDepr("0");
			ut.setAccumDepr("0");
			//data[42]攤提壽月應該與data[45]使用年限一致(otherlimityear)	
			ut.setOriginalApportionMonth(ut.getOtherLimitYear());	
			ut.setApportionMonth(ut.getOtherLimitYear());
			//月提折舊金額=應攤提折舊總額/攤提壽月 取整數
			if(!ut.getOriginalApportionMonth().equals("")){
				int originalMonthDepr = Common.getInt(ut.getOriginalDeprAmount()) / Common.getInt(ut.getOriginalApportionMonth());
				int originalMonthDepr1 = Common.getInt(ut.getOriginalDeprAmount()) - originalMonthDepr * (Common.getInt(ut.getOriginalApportionMonth())-1);
				ut.setOriginalMonthDepr(String.valueOf(originalMonthDepr));
				ut.setOriginalMonthDepr1(String.valueOf(originalMonthDepr1));
				ut.setMonthDepr(ut.getOriginalMonthDepr());
				ut.setMonthDepr1(ut.getOriginalMonthDepr1());
			}			
			//攤提年限截止年月 = 購置日期 + 使用年限(otherlimityear)
			if(!ut.getOriginalLimitYear().equals("")){
				String originalApportionEndYM = NewDateUtil.plusDate(ut.getBuyDate(), "m", Common.getInt(ut.getOtherLimitYear()), NewDateUtil.DateFormat.YYYMM);
				ut.setOriginalApportionEndYM(originalApportionEndYM);
				ut.setApportionEndYM(originalApportionEndYM);
			}
		}
		
		ut.setOtherMaterial( "".equals(checkGet(data[43]))? propertycode.get("material"): checkGet(data[43]) );
		ut.setOtherPropertyUnit( "".equals(checkGet(data[44]))? propertycode.get("propertyunit"): checkGet(data[44]) );
		ut.setNotes(checkGet(data[46]));
		ut.setUseSeparate(splitListData(data[47]));
		ut.setUseKind(splitListData(data[48]));
		ut.setDoorplate(checkGet(data[49]));
		ut.setTaxCredit(splitListData(data[50]));
		ut.setOriginalBasis(checkGet(data[51]));
		ut.setOriginalDate(checkGet(data[52]));
		ut.setOriginalNote(checkGet(data[53]));
		ut.setAccountingTitle(checkGet(data[54]));
		ut.setFundsSource(checkGet(data[55]));
		ut.setFundsSource1(checkGet(data[56]));
		ut.setOwnershipDate(checkGet(data[57]));
		ut.setOwnershipCause(checkGet(data[58]));
		ut.setNonProof(splitListData(data[59]));
		ut.setProofDoc(checkGet(data[60]));
		ut.setOwnershipNote(checkGet(data[61]));
		ut.setField(splitListData(data[62]));
		ut.setLandRule(checkGet(data[63]));
		ut.setOldOwner(checkGet(data[64]));
		ut.setManageOrg(checkGet(data[65]));
		ut.setUseState(checkGet(data[66]));
		ut.setNotes1(checkGet(data[67]));
		ut.setUseState1(checkGet(data[68]));
		ut.setEscrowOriValue(checkGet(data[69]));
		ut.setEscrowOriAccumDepr(checkGet(data[70]));
		ut.setBuildDate(checkGet(data[71]));
		ut.setDoorPlate1(checkGet(data[72]));
		ut.setDoorPlate2(checkGet(data[73]));
		ut.setDoorPlatevillage1(checkGet(data[74]));
		ut.setDoorPlatevillage2(checkGet(data[75]));
		ut.setDoorplateRd1(checkGet(data[76]));
		ut.setDoorplateRd2(checkGet(data[77]));
		ut.setDoorplateSec(checkGet(data[78]));
		ut.setDoorplateLn(checkGet(data[79]));
		ut.setDoorplateAly(checkGet(data[80]));
		ut.setDoorplateNo(checkGet(data[81]));
		ut.setDoorplateFloor1(checkGet(data[82]));
		ut.setDoorplateFloor1(checkGet(data[83]));
		ut.setBuildStyle(checkGet(data[84]));
		ut.setFloor1(checkGet(data[85]));
		ut.setFloor2(checkGet(data[86]));
		ut.setStuff(checkGet(data[87]));
		ut.setAppraiseDate(checkGet(data[88]));
		ut.setUseLicense(checkGet(data[89]));
		ut.setApproveDate(checkGet(data[90]));
		ut.setApproveDoc(checkGet(data[91]));
		ut.setGrantValue(checkGet(data[92]));
		ut.setArticleName(checkGet(data[93]));
		ut.setStoreNo(checkGet(data[94]));
		ut.setLicensePlate(checkGet(data[95]));
		ut.setPurpose(checkGet(data[96]));
		ut.setSecurityMeat(checkGet(data[97]));
		ut.setSecurityName(checkGet(data[98]));
		ut.setSecurityAddr(checkGet(data[99]));
		ut.setSecurityItem(checkGet(data[100]));
		ut.setSecurityTime(checkGet(data[101]));
		ut.setSecurityOrg(checkGet(data[102]));
		ut.setSecurityDoc(checkGet(data[103]));
		ut.setCapitalStock(checkGet(data[104]));
		ut.setCapital(checkGet(data[105]));
		ut.setMeat(checkGet(data[106]));
		ut.setProofDoc1(checkGet(data[107]));
		ut.setRegisterCause(checkGet(data[108]));
		ut.setRegisterDate(checkGet(data[109]));
		ut.setSetPeriod(checkGet(data[110]));
		ut.setCommonObligee(checkGet(data[111]));
		ut.setSetPerson(checkGet(data[112]));
		ut.setPayDate(checkGet(data[113]));
		ut.setInterest(checkGet(data[114]));
		ut.setRent(checkGet(data[115]));
		ut.setNotes1(checkGet(data[116]));
				
		// for 105年度資安中心轉檔 增加欄位 (有值才去處理)
		if(data.length>119 && !"".equals(checkGet(data[118]))){
			ut.setOldPropertyNo(data[118]);
		}
		if(data.length>120 &&!"".equals(checkGet(data[119]))){
			ut.setOldSerialNo(data[119]);
			ut.setOldSerialNoS(data[119]);
			ut.setOldSerialNoE(data[119]);
		}
		if(data.length>121 &&!"".equals(checkGet(data[120]))){
			ut.setOriginalAccumDepr(data[120]);
			ut.setAccumDepr(data[120]); //其實這邊沒作用,  _setDefaultValue()	用OriginalAccumDepr的值設定	
		}
		if(data.length>122 &&!"".equals(checkGet(data[121]))){
			ut.setNetValue(data[121]);
			ut.setOriginalBV(data[121]);
		}
		if(data.length>123 &&!"".equals(checkGet(data[122]))){
			ut.setNoDeprSet(data[122]);
		}		
		
		return ut;
	}
	
	private UNTCH001F02 forUNTCH006F01(UNTCH001F02 ut, String[] data){
		String signno = "";
		signno = data[0] + data[2] + data[4] + data[6];
		ut.setSignNo((signno));
		ut.setLaSignNo1(data[0] + "000000");
		ut.setLaSignNo2(data[0] + data[2] + "0000");
		ut.setLaSignNo3(data[0] + data[2] + data[4]);
		ut.setLaSignNo4(data[6].substring(0,4));
		ut.setLaSignNo5(data[6].substring(4));
		
		ut.setArea(checkGet(data[7]));
		ut.setOriginalAreaLa(checkGet(data[7]));
		ut.setHoldArea(String.valueOf(Common.getNumeric(data[7])*(Common.getNumeric(data[8]) / Common.getNumeric(data[9]))));
		ut.setOriginalHoldArea(String.valueOf(Common.getNumeric(data[7])*(Common.getNumeric(data[8]) / Common.getNumeric(data[9]))));
		ut.setOriginalHoldAreaLa(String.valueOf(Common.getNumeric(data[7])*(Common.getNumeric(data[8]) / Common.getNumeric(data[9]))));
		ut.setOriginalHoldNume(checkGet(data[8]));
		ut.setHoldNume(checkGet(data[8]));
		ut.setOriginalHoldNumeLa(checkGet(data[8]));
		ut.setOriginalHoldDeno(checkGet(data[9]));
		ut.setHoldDeno(checkGet(data[9]));
		ut.setOriginalHoldDenoLa(checkGet(data[9]));
		
		ut.setUseSeparate(splitListData(data[10]));
		ut.setUseKind(splitListData(data[11]));
		ut.setPropertyNo(checkGet(data[12]));
		if(checkGet(data[13]).length()==6){
			ut.setBuyDate("0"+checkGet(data[13]));
		}else{
			ut.setBuyDate(checkGet(data[13]));	
		}
		ut.setSourceKind("13"); // 974: 413 地籍整理  -> 103      地籍整理
		if(checkGet(data[15]).length()==6){
			ut.setSourceDate("0"+checkGet(data[15]));
		}else{
			ut.setSourceDate(checkGet(data[15]));
		}		
		ut.setSourceDoc(checkGet(data[16]));
		ut.setPropertyKind((splitListData(checkGet(data[17]))));
		ut.setFundType(checkGet(data[18]));		
		ut.setValuable(("是".equals((splitListData(checkGet(data[19]))))?"Y":"N"));		
		ut.setCause((splitListData(checkGet(data[20]))));
		ut.setCause1(checkGet(data[21]));
		ut.setPropertyName1(checkGet(data[22]));
		ut.setDoorplate(checkGet(data[23]));
		ut.setTaxCredit(splitListData(data[24]));
		ut.setOriginalBasis(checkGet(data[25]));
		ut.setOriginalDate(checkGet(data[26]));
		ut.setOriginalNote(checkGet(data[27]));
		ut.setAccountingTitle(checkGet(data[28]));
		ut.setFundsSource(checkGet(data[29]));
		ut.setFundsSource1(checkGet(data[30]));
		ut.setOwnershipDate(checkGet(data[31]));
		ut.setOwnershipCause(checkGet(data[32]));
		ut.setNonProof(splitListData(data[33]));
		ut.setProofDoc(checkGet(data[34]));
		ut.setOwnershipNote(checkGet(data[35]));
		ut.setField(splitListData(data[36]));
		ut.setLandRule(checkGet(data[37]));
		ut.setOldOwner(checkGet(data[38]));
		ut.setManageOrg(checkGet(data[39]));
		ut.setUseState(checkGet(data[40]));
		ut.setNotes1(checkGet(data[41]));			
		ut.setUseState1(checkGet(data[42]));			
		ut.setOriginalMoveDate(checkGet(data[43]));
		ut.setMoveDate(checkGet(data[43]));
		ut.setOriginalKeepUnit(checkGet(data[44]));
		ut.setKeepUnit(checkGet(data[44]));
		ut.setOriginalKeeper(checkGet(data[45]));
		ut.setKeeper(checkGet(data[45]));
		ut.setOriginalUseUnit(checkGet(data[46]));
		ut.setUseUnit(checkGet(data[46]));
		ut.setOriginalUser(checkGet(data[47]));
		ut.setUserNo(checkGet(data[47]));
		ut.setOriginalUserNote(checkGet(data[48]));
		ut.setUserNote(checkGet(data[48]));
		ut.setOriginalPlace1(checkGet(data[49]));
		ut.setPlace1(checkGet(data[49]));
		ut.setOriginalPlace(checkGet(data[50]));
		ut.setPlace(checkGet(data[50]));
		ut.setNotes(checkGet(data[51]));
		
		//價值部分一律全設為零
		//匯入完成後再做調整
		ut.setOriginalUnit("0");
		ut.setNetUnit("0");
		ut.setOriginalBV("0");
		ut.setBookValue("0");
		ut.setNetValue("0");
		
		return ut;
	}

	private void calBookValueforUNTCH006F01(){
		MathTools mt = new MathTools();
		
		//總價值
		String sql_bookvalue = "select isnull(sum(bookvalue),0) from UNTLA_REDUCEDETAIL" +
						" where 1=1" +
						" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
						" and ownership = " + Common.sqlChar(this.getOwnership()) +
						" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
						" and caseno = " + Common.sqlChar(this.getCaseNo_Reduce()) ;
		
		//總面積
		String sql_holdarea = "select isnull(sum(holdarea),0) from UNTLA_LAND" +
				" where 1=1" +
				" and enterorg = " + Common.sqlChar(this.getEnterOrg()) +
				" and ownership = " + Common.sqlChar(this.getOwnership()) +
				" and differencekind = " + Common.sqlChar(this.getDifferenceKind()) +
				" and caseno = " + Common.sqlChar(this.getCaseNo()) ;
		
		Database db = null;
		ResultSet rs = null;
		String sum_bookvalue = "";
		String sum_holdarea = "";
		try{
			db = new Database();
			rs = db.querySQL(sql_bookvalue);
			if(rs.next()){
				sum_bookvalue = rs.getString(1);
			}
			rs.close();
			
			rs = db.querySQL(sql_holdarea);
			if(rs.next()){
				sum_holdarea = rs.getString(1);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}				
		
		//單價　=　總價值÷總面積，四捨五入取至小數第2位
		//問題單1284 單價=總價值÷總面積，四捨五入取至整數
		String bookunit = "";				
		if("".equals(Common.checkGet(sum_bookvalue)) || "0".equals(Common.checkGet(sum_bookvalue)) || 
				"".equals(Common.checkGet(sum_holdarea)) || "0.00".equals(Common.checkGet(sum_holdarea))) {
		}else{
			bookunit = mt._division_withBigDecimal(sum_bookvalue, sum_holdarea, 0);
		}
		if ("".equals(bookunit)) {
			bookunit = "0";
		}
		
		//各筆價值　=　各筆面積×單價，四捨五入取至整數
		String sql_uptdateforBookunit = "update UNTLA_LAND set" +
					" bookunit = " + bookunit + "," +
					" bookvalue = holdarea * " + bookunit +  "," +
					" originalunit = " + bookunit + "," +
					" originalbv = holdarea * " + bookunit +  "," +
					" netunit = " + bookunit + "," +
					" netvalue = holdarea * " + bookunit + 
					" where 1=1" +
					" and enterorg = " + Common.sqlChar(getEnterOrg()) +
					" and ownership = " + Common.sqlChar(getOwnership()) +
					" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
					" and caseno = " + Common.sqlChar(getCaseNo()) +
					" and serialno not in (select max(serialno) from UNTLA_LAND " +
											" where 1=1" +
											" and enterorg = " + Common.sqlChar(getEnterOrg()) +
											" and ownership = " + Common.sqlChar(getOwnership()) +
											" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
											" and caseno = " + Common.sqlChar(getCaseNo()) +
											")";				
		try{
			db = new Database();					
			db.excuteSQL(sql_uptdateforBookunit);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}		
		
		//最後一筆價值　=　總價-其他筆價值合計
		String sql_queryOtherBookValue = "select isnull(sum(bookvalue),0) from UNTLA_LAND" +
				" where 1=1" +
				" and enterorg = " + Common.sqlChar(getEnterOrg()) +
				" and ownership = " + Common.sqlChar(getOwnership()) +
				" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
				" and caseno = " + Common.sqlChar(getCaseNo()) +
				" and serialno not in (select max(serialno) from UNTLA_LAND " +
											" where 1=1" +
											" and enterorg = " + Common.sqlChar(getEnterOrg()) +
											" and ownership = " + Common.sqlChar(getOwnership()) +
											" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
											" and caseno = " + Common.sqlChar(getCaseNo()) +
											")";
		String sum_otherBookValue = "";
		try{
			db = new Database();
			rs = db.querySQL(sql_queryOtherBookValue);
			if(rs.next()){
				sum_otherBookValue = rs.getString(1);
			}
			rs.close();					
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}	
		
		String sql_lastHoldArea = "select holdarea from UNTLA_LAND" +
				" where 1=1" +
				" and enterorg = " + Common.sqlChar(getEnterOrg()) +
				" and ownership = " + Common.sqlChar(getOwnership()) +
				" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
				" and caseno = " + Common.sqlChar(getCaseNo()) +
				" and serialno in (select max(serialno) from UNTLA_LAND " +
											" where 1=1" +
											" and enterorg = " + Common.sqlChar(getEnterOrg()) +
											" and ownership = " + Common.sqlChar(getOwnership()) +
											" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
											" and caseno = " + Common.sqlChar(getCaseNo()) +
											")";
		String lastHoldArea = "";
		try{
			db = new Database();
			rs = db.querySQL(sql_lastHoldArea);
			if(rs.next()){
				lastHoldArea = rs.getString(1);
			}
			if ("".equals(lastHoldArea)) {
				lastHoldArea = "0";
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}	
		
		String lastBookValue = mt._subtraction_withBigDecimal(sum_bookvalue, sum_otherBookValue);
		
		//問題單1284 最後一筆單價=最後一筆價值÷最後一筆面積，四捨五入取至整數
		String lastBookUnit = "";
		if("".equals(Common.checkGet(lastBookValue)) || "0".equals(Common.checkGet(lastBookValue)) ||
				"".equals(Common.checkGet(lastHoldArea)) || "0".equals(Common.checkGet(lastHoldArea))){
		}else{
			lastBookUnit = mt._division_withBigDecimal(lastBookValue, lastHoldArea, 0);	
		}
		if ("".equals(lastBookUnit)) {
			lastBookUnit = "0";
		}
		String sql_uptdateforLast = "update UNTLA_LAND set" +
					" bookunit = " + lastBookUnit + "," +
					" bookvalue = " + lastBookValue +  "," +
					" originalunit = " + lastBookUnit + "," +
					" originalbv = " + lastBookValue +  "," +
					" netunit = " + lastBookUnit + "," +
					" netvalue = " + lastBookValue + 
					" where 1=1" +
					" and enterorg = " + Common.sqlChar(getEnterOrg()) +
					" and ownership = " + Common.sqlChar(getOwnership()) +
					" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
					" and caseno = " + Common.sqlChar(getCaseNo()) +
					" and serialno in (select max(serialno) from UNTLA_LAND " +
											" where 1=1" +
											" and enterorg = " + Common.sqlChar(getEnterOrg()) +
											" and ownership = " + Common.sqlChar(getOwnership()) +
											" and differencekind = " + Common.sqlChar(getDifferenceKind()) +
											" and caseno = " + Common.sqlChar(getCaseNo()) +
											")";				
		try{
			db = new Database();
			db.excuteSQL(sql_uptdateforLast);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}	
	}

	private String splitListData(String s){
		String result = "";
		StringTools st = new StringTools();
		if(st._isEmpty(s)){					
		}else{
			result = s.split("：")[0];
		}
		return result;
	}
		

	private Object[] checkPKData(String[] data, int col, List listError){
		Object[] result = new Object[2];
		StringTools st = new StringTools();
		
		result[0] = false;
		
		Set set = pkdata.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			Integer i = (Integer)iter.next();

//				if(st._isEmpty(data[i-1])){		
				if("UNTCH001F08".equals(this.progType)){		
					checkPKDataforUNTCH001F08(data, col, listError, i, result);
				}else if("UNTCH006F01".equals(this.progType)){	
					checkPKDataforUNTCH006F01(data, col, listError, i, result);
				}
//				}
		}
		result[1] = listError;
		
		return result;
	}
		
	private void checkPKDataforUNTCH001F08(String[] data, int col, List listError, int i, Object[] result){
		StringTools st = new StringTools();
		Object hasError = result[0];		//別問我為啥多此一舉, 我只是debug把它從false改成result[0]
		
		if(!"".equals(Common.get(data[0]))){
			
			data[0] = fixDataTypeError(data[0],11);
			
			// TODO 為了匯入機捷財產，需要先關閉檢查財編長度
			if(checkDatalen(data[0],11,1)==false && i==1){
				listError.add("第" + col + "行必填欄位　財產編號　資料長度錯誤！！");
				hasError = true;
			}
			
			String check1 = data[0].substring(0,1);
			String check3 = data[0].substring(0,3);
			
			if ("3".equals(check1) || "4".equals(check1) || "5".equals(check1) && i==1) {
				try {
					Double originalAmount = Double.parseDouble(data[14]);
					Double originalUnit = Double.parseDouble(data[15]);
					Double originalBV = Double.parseDouble(data[16]);
					if (originalAmount * originalUnit != originalBV) {
						listError.add("第" + col + "行　『該筆明細有數量*單價不等於總價的情形，若有無法整除者請另建財產明細』");
						hasError = true;
					}
				} catch (NumberFormatException  e){
					e.printStackTrace();
					listError.add("第" + col + "行必填欄位　數量、單價、總價　請填入正確數字格式！！");
					hasError = true;
				}			
				
			}
		
			if((i == 10 || i == 11) && !"2".equals(check1)){	//欄位是 建物主建物面積 or 建物附屬建物面積 and 不是建物時
				return;
			}else if((i == 12 || i == 13 || i == 14) && !("1".equals(check1) || "2".equals(check1))){	//欄位是 土地面積/建物面積 or 權利分子 or 分母 and 不是土地(改)、建物時
				return;
			}else if((i == 28 || i == 30 || i == 32 || i == 34) && ! ("1".equals(check1) || "2".equals(check1))){	//欄位是 標示等4格 and 不是土地(改)、建物時
				return;
			}
			
			if(!st._isEmpty(data[i-1])){		//資料不為空
				
				if(i==1){		//先設定用1, 讓它只跑一次
					data[1] = fixDataTypeError(data[1],7);							
					if(checkDatalen(data[1],7,1)==false && i==1){
						listError.add("第" + col + "行必填欄位　購置日期　資料長度錯誤！！");
						hasError = true;
					}							
					data[4] = fixDataTypeError(data[4],7);							
					if(checkDatalen(data[4],7,1)==false && i==1){
						listError.add("第" + col + "行必填欄位　財產來源－取得日期　資料長度錯誤！！");
						hasError = true;
					}							
					if(splitListData(checkGet(data[6])).length()!=2){
						listError.add("第" + col + "行必填欄位　財產性質　資料長度錯誤！！");
						hasError = true;
					}
				}
				
				if(i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 ||  i == 17){	//檢查數字欄位	
					try{
						Double d = Double.parseDouble(data[i-1]); //測試轉數字
						if(d==0 && (i == 12 || i == 13 || i == 14 || i == 15)){	//總面積、分子、分母、數量不可為0
							if("2".equals(check1) && i ==12){ //建物面積可為0
							}else{
								listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　不可為0！！");
								hasError = true;
							}
						}
					}catch(NumberFormatException  e){
						listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　請填入正確數字格式！！");
						hasError = true;
					}						
				}						
					
				if(i == 28 || i == 30 || i == 32 || i == 34){	//檢查土地建物標示長度								
					if(check3.equals("111")){		//土改不做標示長度檢查									
					}else if(check1.equals("1") || check1.equals("2")){					
						if((i == 28) && (data[i-1].length() != 1)){
							System.out.println("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度１！！");
							listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度１！！");
							hasError = true;					
						}else if((i == 30) && (data[i-1].length() != 2)){
							System.out.println("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度2！！");
							listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度２！！");
							hasError = true;
						}else if((i == 32) && (data[i-1].length() != 4)){
							System.out.println("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度4！！");
							listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度４！！");
							hasError = true;
						}else if((i == 34) && (data[i-1].replaceAll("-", "").length() != 8)){
							System.out.println("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度8！！");
							listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度８！！");
							hasError = true;
						}								
					}						
				}else if(i == 16){		//動產檢查單價
					if("3".equals(check1) || "4".equals(check1) || "5".equals(check1)){
						if(Common.getInt(data[15])<=0){
								listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　資料輸入錯誤！！");
								hasError = true;
						}else{
							int unitPrice = Common.getInt(data[15]);
							if(unitPrice < 10000){
//								listError.add("第" + col + "行　" + pkdata.get(i) + "　必須大於等於10000！！");
//								hasError = true;
//									}else if(unitPrice * Common.getInt(data[14	]) != Common.getInt(data[16])){
//										listError.add("第" + col + "行　" + pkdata.get(i) + "　單價乘以數量與總價不合！！");
//										hasError = true;
							}
						}
					}
				}else if(i == 98){		//法人名稱
					if("9".equals(check1) && st._isEmpty(data[i-1])){
						listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　沒有資料！！");
						hasError = true;	
					}
				}else if(i == 35){		//折舊資料－折舊方法
					if(!splitListData(checkGet(data[34])).equals("01")){	//折舊方法不是01：不必折舊,就檢查折舊欄位
						if(checkGet(data[36]).equals("否")){	//部門別依比例分攤為否
							if(st._isEmpty(data[37])){
								listError.add("第" + col + "行必填欄位　折舊資料－園區別代碼　沒有資料！！");
								hasError = true;
							}
							if(st._isEmpty(data[38])){
								listError.add("第" + col + "行必填欄位　折舊資料－部門別代碼　沒有資料！！");
								hasError = true;
							}else{
								data[38] = fixDataTypeError(data[38],2);							
								if(checkDatalen(data[38],2,1)==false && i==1){
									listError.add("第" + col + "行必填欄位　折舊資料－部門別代碼　資料長度錯誤！！");
									hasError = true;
								}
							}
							if(st._isEmpty(data[39])){
								listError.add("第" + col + "行必填欄位　折舊資料－部門別單位代碼　沒有資料！！");
								hasError = true;
							}
							if(st._isEmpty(data[40])){
								listError.add("第" + col + "行必填欄位　折舊資料－會計科目代碼　沒有資料！！");
								hasError = true;
							}else{
								data[40] = fixDataTypeError(data[40],4);							
								if(checkDatalen(data[40],4,1)==false && i==1){
									listError.add("第" + col + "行必填欄位　折舊資料－會計科目代碼　資料長度錯誤！！");
									hasError = true;
								}
							}
						}
					}else if(splitListData(checkGet(data[34])).length()!=2){
						listError.add("第" + col + "行必填欄位　折舊資料－折舊方法　資料長度錯誤！！");
						hasError = true;
					}
				}
			}else if( i!=16 || (i==16 && "3".equals(check1) || "4".equals(check1) || "5".equals(check1)) ){
				if("111".equals(check3) && (i==12 || i==13 || i==14 || i==28 || i==30 || i==32 || i==34)){	//土改整筆面積、權分、標示非必填	
				}else if("2".equals(check1) && (i==28 || i==30 || i==32 || i==34)){											//建物標示非必填	
				}else{
					listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　沒有資料！！");
					hasError = true;
				}
			}					
		}
		result[0] = hasError;
	}
	
	private void checkPKDataforUNTCH006F01(String[] data, int col, List listError, int i, Object[] result){
		if(i == 0 || i == 2 || i == 4 || i == 6){
			//檢查土地標示長度
			if((i == 0) && (data[i-1].length() != 1)){
				listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度１！！");
				result[0] = true;					
			}else if((i == 2) && (data[i-1].length() != 2)){
				listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度２！！");
				result[0] = true;
			}else if((i == 4) && (data[i-1].length() != 4)){
				listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度４！！");
				result[0] = true;
			}else if((i == 6) && (data[i-1].length() != 8)){
				listError.add("第" + col + "行 " + pkdata.get(i) + "　資料長度錯誤！！應為長度８！！");
				result[0] = true;
			}
			
		}else if(i == 7 || i == 8 || i == 9 || i == 12 || i == 13 || i == 14 || i == 15 || i == 17 || i == 19 || i == 20){											
			StringTools st = new StringTools();
			if(st._isEmpty(data[i-1])){
				listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　沒有資料！！");
				result[0] = true;
			}

		}
	}
	
	//處理文字讀取時誤轉為float
	private String fixDataTypeError(String data, int length){
		if(data.indexOf(".")>-1){

			if(data.endsWith(".0"))
				data = data.substring(0, data.lastIndexOf(".0"));
			else{
				String pre = data.substring(0, data.indexOf("E")).replace(".", "");
				int exp = Integer.parseInt(data.substring(data.indexOf("E")+1));
				data = Common.formatRearZero(pre, exp+1);
				
//						double d = Double.parseDouble(data.substring(0, data.indexOf("E")));
//						int exp = Integer.parseInt(data.substring(data.indexOf("E")+1));
//						for(int i=0;i<exp;i++){
//							d*=10;
//						}
//						data = String.valueOf(d).substring(0, data.indexOf("."));
			}
		}
		return data;
	}
	
	//option == 1: 長度固定的欄位, 2:容許長度較短的欄位；true: 長度正確, false:長度錯誤
	private boolean checkDatalen(String data, int length, int option){
		boolean result = true;
		
		if(option==1 && data.length() != length) result = false;
		if(option==2 && data.length() <= length) result = false;
		return result;
	}

	private void initPKDataforUNTCH001F08(){				
		pkdata.put(1, "財產編號");
		pkdata.put(2, "購置日期");
		pkdata.put(4, "財產來源－種類");
		pkdata.put(5, "財產來源－取得日期");
		pkdata.put(7, "財產性質");
		pkdata.put(9, "珍貴財產");
		pkdata.put(10, "建物主建物面積(㎡)");
		pkdata.put(11, "建物附屬建物面積(㎡)");
		pkdata.put(12, "土地整筆面積(㎡)/建物總面積(㎡)");
		pkdata.put(13, "土地建物權利範圍－分子");
		pkdata.put(14, "土地建物權利範圍－分母");
		pkdata.put(15, "數量(計量數)(股數)(土地/建物固定為1)");
		pkdata.put(16, "單價");		
		pkdata.put(17, "總價");
		pkdata.put(28, "土地建物標示－縣市代碼");
		pkdata.put(30, "土地建物標示－鄉鎮市區代碼");
		pkdata.put(32, "土地建物標示－地段代碼");
		pkdata.put(34, "土地建物標示－地建號");
		pkdata.put(35, "折舊資料－折舊方法");
//				pkdata.put(98, "有價證券－發行法人名稱");		//Excel中沒標紅字, 先mark起來
	}
	
	private void initPKDataforUNTCH006F01(){
		pkdata.put(1, "土地標示－縣市代碼");
		pkdata.put(3, "土地標示－鄉鎮市區代碼");
		pkdata.put(5, "土地標示－地段代碼");
		pkdata.put(7, "土地標示－地建號");
		pkdata.put(8, "整筆面積(㎡)");
		pkdata.put(9, "權利範圍－分子");
		pkdata.put(10, "權利範圍－分母");				
		pkdata.put(13, "財產編號");
		pkdata.put(14, "購置日期");
		pkdata.put(15, "財產來源－種類");
		pkdata.put(16, "財產來源－取得日期");
		pkdata.put(18, "財產性質");
		pkdata.put(20, "珍貴財產");
		pkdata.put(21, "增加原因");				
	}

	public Map<String, String> getSyspkPropertycode(String propertyno){
		Map<String, String> sp = null;
		StringBuilder sb = null;
		Database db = null;
		
		try {
			sp = new HashMap<String, String>();
			sb = new StringBuilder();
			db = new Database();
			
			sb.append("select * from SYSPK_PROPERTYCODE where 1=1 ")
				.append("and enterorg in (").append(Common.sqlChar(this.enterOrg)).append(", '000000000A') ")
				.append("and propertyno =").append(Common.sqlChar(propertyno));

			ResultSet rs = db.querySQL(sb.toString());
			while (rs.next()) {
				sp.put("propertyno", checkGet(rs.getString("propertyno")));
				sp.put("propertytype", checkGet(rs.getString("propertytype")));
				sp.put("propertyname", checkGet(rs.getString("propertyname")));
				sp.put("propertyunit", checkGet(rs.getString("propertyunit")));
				sp.put("material", checkGet(rs.getString("material")));
				sp.put("limityear", checkGet(rs.getString("limityear")));
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		
		return sp;
	}
	
	//確認匯入格式
	public String checkTitleData(String[] title) {
		StringBuffer errorTitle = new StringBuffer();
		String[] rowNo = {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1", "O1", "P1", "Q1", "R1", "S1", "T1", "U1", "V1", "W1", "X1", "Y1", "Z1",
						  "AA1", "AB1", "AC1", "AD1", "AE1", "AF1", "AG1", "AH1", "AI1", "AJ1", "AK1", "AL1", "AM1", "AN1", "AO1", "AP1", "AQ1", "AR1", "AS1", "AT1", "AU1", "AV1", "AW1", "AX1", "AY1", "AZ1",
						  "BA1", "BB1", "BC1", "BD1", "BE1", "BF1", "BG1", "BH1", "BI1", "BJ1", "BK1", "BL1", "BM1", "BN1", "BO1", "BP1", "BQ1", "BR1", "BS1", "BT1", "BU1", "BV1", "BW1", "BX1", "BY1", "BZ1",
						  "CA1", "CB1", "CC1", "CD1", "CE1", "CF1", "CG1", "CH1", "CI1", "CJ1", "CK1", "CL1", "CM1", "CN1", "CO1", "CP1", "CQ1", "CR1", "CS1", "CT1", "CU1", "CV1", "CW1", "CX1", "CY1", "CZ1",
						  "DA1", "DB1", "DC1", "DD1", "DE1", "DF1", "DG1", "DH1", "DI1", "DJ1", "DK1", "DL1", "DM1"	 
						}; 		
		String[] standardTitle = {"*財產編號", "*購置日期", "財產別名", "*財產來源－種類", "*財產來源－取得日期", 
									"財產來源－取得文號", "*財產性質", "基金名稱", "*珍貴財產", "建物主建物面積(㎡)", 
									"建物附屬建物面積(㎡)", "土地整筆面積(㎡)/建物總面積(㎡)", "土地建物權利範圍－分子", "土地建物權利範圍－分母", "*數量(計量數)(股數)(土地/建物固定為1)",
									"*單價", "*總價", "動產－型式", "動產－廠牌", "移動日期",
									"保管單位代碼", "保管人代碼", "使用單位代碼", "使用人代碼", "使用註記",
									"存置地點代碼", "存置地點說明", "土地建物標示－縣市代碼", "土地建物標示－縣市名稱", "土地建物標示－鄉鎮市區代碼",
									"土地建物標示－鄉鎮市區名稱", "土地建物標示－地段代碼", "土地建物標示－地段名稱", "土地建物標示－地建號", "*折舊資料－折舊方法",
									"折舊資料－屬公共設施建設費", "折舊資料－部門別依比例分攤", "折舊資料－園區別代碼", "折舊資料－部門別代碼", "折舊資料－部門別單位代碼",
									"折舊資料－會計科目代碼", "折舊資料－殘值", "折舊資料－攤提壽月", "主要材質(自訂)", "單位(自訂)",
									"年限(月)(自訂)", "備註", "使用分區代碼", "編定使用種類代碼", "街路名",
									"抵繳遺產稅", "入帳依據", "入帳－公告年月", "入帳摘要", "會計科目",
									"經費來源", "經費來源－其他說明", "權狀資料－所有權登記日期", "權狀資料－所有權登記原因", "權狀資料－有無權狀",
									"權狀資料－權狀字號", "權狀資料－其他登記事項", "土地地目", "土地等則", "原有人",
									"管理機關", "土地現況", "其他事項", "土地地上物情形", "代管資產－原始總價",
									"代管資產－代管日前累計折舊", "建築日期", "門牌－縣市(代碼)", "門牌－鄉鎮市區(代碼)", "門牌－村/里(中文名稱)",
									"門牌－村/里(代碼)", "門牌－路/街/大道(中文名稱)", "門牌－路/街/大道(代碼)", "門牌－段", "門牌－巷",
									"門牌－弄", "門牌－號", "門牌－樓", "門牌－之", "建築式樣",
									"建物構造－地上層數", "建物構造－地下層數", "建物構造－造", "資產重估日期", "建物－使用執照",
									"動產－受贈同意函日期", "動產－受贈同意函文號", "動產－補助金額", "動產－品名", "動產－廠商代碼",
									"動產－牌照號碼規格/序號規格", "動產－用途", "有價證券－內容", "有價證券－發行法人名稱", "有價證券－發行法人地址",
									"有價證券－發行法人事業項目", "有價證券－發行法人設立年月", "有價證券－發行法人核准登記機關", "有價證券－發行法人核准登記字號", "有價證券－發行股份總數",
									"有價證券－公司資本額", "權利－內容", "權利－憑證字號", "權利－登記原因", "權利－登記日期",
									"權利－設定起迄日期", "權利－共同權利人", "權利－設定人", "權利－清償時間", "權利－利息",
									"權利－地租", "權利－其他事由"
									};

		if ((title.length - 1) != rowNo.length) {
			errorTitle.append("格式錯誤，總欄位數應為" + rowNo.length + "欄");
			return errorTitle.toString();
		}
		
		int errorCount = 0;
		for (int i = 0; i < rowNo.length; i++) {
			if (!title[i].equals(standardTitle[i])) {
				errorCount++;
				if (errorCount == 1) {
					errorTitle.append("表格錯誤").append("\\n");
				} 
				if (errorCount <= 5) {
					errorTitle.append(rowNo[i]).append(" 欄位應為 ").append(standardTitle[i]).append("\\n");			
				}				
			}
		}
		
		if (errorCount > 5) {
			errorTitle.append("......").append("\\n").append("等共").append(errorCount).append("欄錯誤");
		}
		return errorTitle.toString();
	}
			
}