
package unt.ne;

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
import util.SuperBean2;
import util.Validate;
import TDlib_Simple.tools.src.ExcelTools_ByPOI;
import TDlib_Simple.tools.src.StringTools;

public class UNTNE054F extends SuperBean2{

	private String progType;
	public String getProgType() {return checkGet(progType);}
	public void setProgType(String progType) { this.progType = checkSet(progType);	}

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
		if("UNTNE054F".equals(this.progType)){
			initPKDataforUNTNE054F();
		}		
		
		
		et._setFilePath(this.getFilePath());
		et._setFileName(this.getFileName());
		
		//=================================================
		
		datas = et._readExcel_asStringArray(0);

		//=================================================
		
		boolean isTitle = true;
		
		//使用UNTNE002F類別儲存資料
		UNTNE002F ut = new UNTNE002F();
		
		for(String[] data : datas){
			if(isTitle){
				if("UNTNE054F".equals(this.progType)){ //非消耗品增加單檢查標題格式
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
					
					if(!"".equals(Common.get(data[0]))){		//物品編號不為空
						if("UNTNE054F".equals(progType)){		
							//開始set資料
							ut = forUNTNE054F(ut, data);
						}
					}
					//執行資料insert
					try {
						ut.insert();
					} catch (Exception e) {
						e.printStackTrace();
					}
					ut.setCaseSerialNo(null);
				}
			}
			col++;
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

	private UNTNE002F forUNTNE054F(UNTNE002F ut, String[] data){
		String propertyNo = checkGet(data[0]);
		
		// 取得物品編號的SYSPK_PROPERTYCODE資料, 用來get 單位、材質、年限的預設值
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
		// 數量
		ut.setBookAmount(checkGet(data[9]));					
		ut.setOriginalAmount(checkGet(data[9]));		
		// 單價
		ut.setOriginalUnit(checkGet(data[10]));
		// 總價
		ut.setOriginalBV(checkGet(data[11]));
		ut.setBookValue(checkGet(data[11]));
		
		ut.setSpecification(checkGet(data[12]));
		ut.setNameplate(checkGet(data[13]));
		ut.setOriginalMoveDate(checkGet(data[14]));
		ut.setOriginalKeepUnit(checkGet(data[15]));
		ut.setKeepUnit(checkGet(data[15]));
		ut.setOriginalKeeper(checkGet(data[16]));
		ut.setKeeper(checkGet(data[16]));
		ut.setOriginalUseUnit(checkGet(data[17]));
		ut.setUseUnit(checkGet(data[17]));
		ut.setOriginalUser(checkGet(data[18]));
		ut.setUserNo(checkGet(data[18]));
		ut.setOriginalUserNote(checkGet(data[19]));
		ut.setUserNote(checkGet(data[19]));
		ut.setOriginalPlace1(checkGet(data[20]));
		ut.setPlace1(checkGet(data[20]));
		ut.setOriginalPlace(checkGet(data[21]));
		ut.setPlace(checkGet(data[21]));

		ut.setOtherMaterial( "".equals(checkGet(data[22]))? propertycode.get("material"): checkGet(data[22]) );
		ut.setOtherPropertyUnit( "".equals(checkGet(data[23]))? propertycode.get("propertyunit"): checkGet(data[23]) );
		
		//年限(自訂)	//問題單1718 年限(自訂)改為輸入月份:年限(月)(自訂), originallimityear都使用財編檔年限
		ut.setOriginalLimitYear(propertycode.get("limityear"));
		String inputlimityear = checkGet(data[24]);
		if (!"".equals(inputlimityear)){	//excel有輸入值
			ut.setOtherLimitYear(inputlimityear);
		} else {	//沒輸入就讀財編檔的值
			ut.setOtherLimitYear(String.valueOf(Common.getInt(propertycode.get("limityear")) * 12));
		}
				
		ut.setNotes(checkGet(data[25]));
		ut.setOriginalNote(checkGet(data[26]));
		ut.setAccountingTitle(checkGet(data[27]));
		ut.setFundsSource(checkGet(data[28]));
		ut.setFundsSource1(checkGet(data[29]));
		ut.setApproveDate(checkGet(data[30]));
		ut.setApproveDoc(checkGet(data[31]));
		ut.setGrantValue(checkGet(data[32]));
		ut.setArticleName(checkGet(data[33]));
		ut.setStoreNo(checkGet(data[34]));
		ut.setLicensePlate(checkGet(data[35]));
		ut.setPurpose(checkGet(data[36]));	
		
		return ut;
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
			checkPKDataforUNTNE054F(data, col, listError, i, result);
		}
		result[1] = listError;
		
		return result;
	}
		
	private void checkPKDataforUNTNE054F(String[] data, int col, List listError, int i, Object[] result){
		StringTools st = new StringTools();
		Object hasError = result[0];
		
		// 物品編號不為空且長度大於等於3才繼續
		if(!"".equals(Common.get(data[0])) && data[0].length()>=3){
			// 物品編號
			if (i == 1) {
				data[0] = fixDataTypeError(data[0]);
				
				Map<String, String> propertycode = this.getSyspkPropertycode(data[0]);			
				if(propertycode.isEmpty()){
					listError.add("第" + col + "行必填欄位　物品編號　資料錯誤，系統查無此編號！！");
					hasError = true;
				} else if (i==1) {
					try {
						Double originalAmount = Double.parseDouble(data[9]);
						Double originalUnit = Double.parseDouble(data[10]);
						Double originalBV = Double.parseDouble(data[11]);
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
				
				// 非必填欄位格式檢查
				// 受贈同意函日期
				if (!"".equals(Common.get(data[30])) && !Validate.checkDate(data[30])) {
					listError.add("第" + col + "行欄位　受贈同意函日期　資料格式錯誤！！");
					hasError = true;
				}
				// 補助金額
				if (!"".equals(Common.get(data[32])) && (!Validate.checkInt(data[32]) || data[32].length()>15)) {
					listError.add("第" + col + "行欄位　補助金額　資料格式錯誤！！");
					hasError = true;
				}
			}
			
			if(!st._isEmpty(data[i-1])){		//資料不為空
				// 購置日期
				if(i==2){
					data[1] = fixDataTypeError(data[1]);							
					if(!Validate.checkDate(data[1])){
						listError.add("第" + col + "行必填欄位　購置日期　資料格式錯誤！！");
						hasError = true;
					}
				}
				// 物品來源－取得日期
				if(i==5){						
					data[4] = fixDataTypeError(data[4]);							
					if(!Validate.checkDate(data[4])){
						listError.add("第" + col + "行必填欄位　物品來源－取得日期　資料格式錯誤！！");
						hasError = true;
					}
				}
				// 物品性質
				if(i==7){						
					if(splitListData(checkGet(data[6])).length()!=2){
						listError.add("第" + col + "行必填欄位　物品性質　資料長度錯誤！！");
						hasError = true;
					}
				}
				
				// 數量 單價 總價
				if(i == 10 || i == 11 || i == 12){
					//測試轉數字
					try{
						Double d = Double.parseDouble(data[i-1]);
						if (d < 0) {
							listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　資料不可小於0！！");
							hasError = true;
						}
					}catch(NumberFormatException  e){
						listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　請填入正確數字格式！！");
						hasError = true;
					}						
				}
			} else {
				listError.add("第" + col + "行必填欄位　" + pkdata.get(i) + "　沒有資料！！");
				hasError = true;
			}					
		}
		result[0] = hasError;
	}
	
	//處理文字讀取時誤轉為float
	private String fixDataTypeError(String data){
		if(data.indexOf(".")>-1){

			if(data.endsWith(".0"))
				data = data.substring(0, data.lastIndexOf(".0"));
			else{
				String pre = data.substring(0, data.indexOf("E")).replace(".", "");
				int exp = Integer.parseInt(data.substring(data.indexOf("E")+1));
				data = Common.formatRearZero(pre, exp+1);
			}
		}
		return data;
	}

	private void initPKDataforUNTNE054F(){				
		pkdata.put(1, "物品編號");
		pkdata.put(2, "購置日期");
		pkdata.put(4, "物品來源－種類");
		pkdata.put(5, "物品來源－取得日期");
		pkdata.put(7, "物品性質");
		pkdata.put(9, "珍貴物品");
		pkdata.put(10, "數量");
		pkdata.put(11, "單價");		
		pkdata.put(12, "總價");
	}
	
	public Map<String, String> getSyspkPropertycode(String propertyno){
		Map<String, String> sp = null;
		StringBuilder sb = null;
		Database db = null;
		
		try {
			sp = new HashMap<String, String>();
			sb = new StringBuilder();
			db = new Database();
			
			sb.append("select * from SYSPK_PROPERTYCODE2 where 1=1 ")
				.append("and enterorg =").append(Common.sqlChar(getEnterOrg()))
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
							  "AA1", "AB1", "AC1", "AD1", "AE1", "AF1", "AG1", "AH1", "AI1", "AJ1", "AK1"	 
							 }; 		
															
			String[] standardTitle = {"*物品編號", "*購置日期", "物品別名", "*物品來源－種類", "*物品來源－取得日期", 
										"物品來源－取得文號", "*物品性質", "基金名稱", "*珍貴物品", "*數量", 
										"*單價", "*總價", "型式", "廠牌", "移動日期", 
										"保管單位代碼", "保管人代碼", "使用單位代碼", "使用人代碼", "使用註記", 
										"存置地點代碼", "存置地點說明", "主要材質(自訂)", "單位(自訂)", "年限(月)(自訂)", 
										"備註", "入帳摘要", "會計科目", "經費來源", "經費來源－其他說明", 
										"受贈同意函日期", "受贈同意函文號", "補助金額", "品名", "廠商代碼",
										"牌照號碼規格/序號規格", "用途"
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