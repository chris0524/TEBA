package unt.up;

import java.sql.ResultSet;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import util.Common;
import util.Database;
import util.Datetime;
import util.Function;
import util.Validate;

public class TransferMP extends UNTUP000Q {
	
	
public void doMoveableUploadProcess(String enterOrg, java.util.Iterator it) {	
	if (it!=null) {
		Database db = new Database();	
		setEnterOrg(enterOrg);
		try {			
			db.setAutoCommit(false);
			db.exeSQL(" delete from untup_movable where enterOrg="+Common.sqlChar(enterOrg));			
			String[] rowArray = new String[67];
			int rowid = 2;
			int intSuccessRecord = 0;
			int intErrorRecord = 0;
			String preSQL = " insert into untup_movable(enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName,otherPropertyUnit,otherMaterial,otherLimitYear,propertyName1,cause,cause1,approveDate1,approveDoc1,buyDate,originalAmount,originalUnit,originalBV,originalNote,accountingTitle,fundsSource,grantValue,articleName,nameplate,specification,storeNo,storeName,COMPANYID,tel1,tel2,seller,fax,SourceKind,sourceDate,sourceDoc,licensePlate,purpose,keepUnit,keepUnitName,keeper,keeperName,useUnit,useUnitName,userNo,userName,place,deprMethod,notes1,newEnterOrg,transferDate,returnPlace,cause2,scrapValue2,dealSuggestion,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,notes,editID,editDate,editTime,errorColumn,isErr)Values(";			
			HashMap h = new HashMap();			
			while(it.hasNext()) {
				rowArray=(String[])(it.next());	
				
				//Excel Meet Empty Cell, leave it..
				if (Common.get(rowArray[0]).equals("") && Common.get(rowArray[1]).equals("")) break;
				
				boolean hashErr = false;
				StringBuffer sbSQL = new StringBuffer(500);				
				sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
				StringBuffer sbErrMsg = new StringBuffer();	
				//檢查資料有無重覆。
				if (Common.get(rowArray[5]).equals("1")) {
					if (checkMovableExists(enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))) {
			        	hashErr = true;
						sbErrMsg.append("己和正式檔資料重覆，建議調整財產分號以使資料不重覆。\n");
					}
					Integer r = (Integer)h.get(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[3])+Common.get(rowArray[6])+Common.get(rowArray[7]));
			        if(r==null){
			        	r = new Integer(rowid);
			        	h.put(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[3])+Common.get(rowArray[6])+Common.get(rowArray[7]), r);
			        } else {
			        	hashErr = true;
			        	sbErrMsg.append("己和列號").append(r.intValue()).append("的暫存檔資料重覆，建議調整財產分號以使資料不重覆。\n");
			        }
				} else {
					Integer r = (Integer)h.get(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[3])+Common.get(rowArray[6])+Common.get(rowArray[7]));
			        if(r==null){
						if (Common.get(rowArray[1]).equals("03")) {
							if (checkMovableExists(enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))==false) {
					        	hashErr = true;
								sbErrMsg.append("無增加資料。\n");
							}													
						} else {
							if (checkMovableExists(enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),"",Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))==false) {
					        	hashErr = true;
								sbErrMsg.append("無增加資料。\n");
							}				
						}
	
			        }
				}
				
				for (int i=0; i<67; i++) {
					switch (i) {
					case 2: //基金財產代碼
						if (Common.get(rowArray[1]).equals("03")) {
							if (!"".equals(Common.get(rowArray[i]))) {
								if ("".equals(getCodeName("FUD",Common.get(rowArray[i])))) sbErrMsg.append("查無此 ").append(rowArray[i]).append(" 基金財產代碼。\n");								
							} else sbErrMsg.append("財產性質為事業用時基金財產代碼不能為空值。\n");							
						}else{
							if (!"".equals(Common.get(rowArray[i]))) {
								sbErrMsg.append("財產性質非為事業用時基金財產代碼需為空值。\n");	
							}
							//rowArray[i] = "";
						}						
						break;
					case 5:
						sbErrMsg.append(checkMovableField(i, Common.get(rowArray[i])));
						
						if (Common.get(rowArray[6]).substring(0,3).equals("503")) {
							if (!"".equals(Common.get(rowArray[19]))) {
								if (Function.getNumeric(rowArray[18])!=1) sbErrMsg.append("當圖書資料的單價有值時，數量只能為正整數 1。\n");								
							} else {
								if (!Validate.checkInt(rowArray[18]) || Function.getNumeric(rowArray[18])<=0){
									sbErrMsg.append("當單價為空值時數量必須輸入 >0 的正整數。\n");
								}
							}					
						} else {
							if (Function.getNumeric(rowArray[18])!=1) sbErrMsg.append("若非圖書資料時，數量只能為正整數 1。\n");							
							if (!Validate.checkInt(rowArray[19]) || Function.getNumeric(rowArray[19])<0) sbErrMsg.append("單價必須輸入 ≧ 0 的正整數。\n");								
						}					
						
						if ("1".equals(Common.get(rowArray[i]))) {
							if ("04".equals(Common.get(rowArray[13]))){
								if ("".equals(Common.get(rowArray[15]))||"".equals(Common.get(rowArray[16]))) sbErrMsg.append("受贈同意函日期及文號不能為空值。\n");
							}else{ rowArray[15]="";	rowArray[16]=""; }
	
							if (Common.get(rowArray[3]).equals("Y")) {
								if (Validate.checkInt(rowArray[19])) {
									if (Function.getNumeric(rowArray[19])<0){
										sbErrMsg.append("若為珍貴財產時，單價必須 ≧ 0。\n");										
									}
								} else sbErrMsg.append("單價必須輸入正整數值。\n");
							} else {
								if (!"503".equals(Common.get(rowArray[6]).substring(0,3))) {
									if (Validate.checkInt(rowArray[19])) {
										if (Function.getNumeric(rowArray[19])<10000){
											sbErrMsg.append("非為珍貴財產時單價必須 ≧ 10000。\n");	
										}									
									}  else sbErrMsg.append("單價必須輸入正整數值。\n");
								}
							}
						}
						
						if ("1".equals(Common.get(rowArray[i]))||"4".equals(Common.get(rowArray[i]))||"5".equals(Common.get(rowArray[i]))) {
							if (Validate.checkInt(rowArray[20]) && Integer.parseInt("0"+rowArray[20])>=0){								
							} else {
								sbErrMsg.append("總價必須 ≧ 0。\n");
							}
						} else if ("2".equals(Common.get(rowArray[i]))||"3".equals(Common.get(rowArray[i]))) {
							if (Validate.checkInt(rowArray[20]) && Integer.parseInt("0"+rowArray[20])>0){								
							} else {
								sbErrMsg.append("總價必須>0。\n");
							}
						}
						
						if ("5".equals(Common.get(rowArray[i]))) {
							if ("01".equals(Common.get(rowArray[13]))||"07".equals(Common.get(rowArray[13]))){
								if ("".equals(Common.get(rowArray[51]))) {
									sbErrMsg.append("接管機關不能為空值。\n");
								} else {
									if ("".equals(getOrganSName(Common.get(rowArray[51])))) {
										sbErrMsg.append("查無此接管機關代碼。\n");
									}
								}
								if ("".equals(Common.get(rowArray[52]))) {
									sbErrMsg.append("交接日期不能為空值。\n");
								} else {
									if (!Validate.checkDate(Common.get(rowArray[52]))) {
										sbErrMsg.append("交接日期必須為(YYYMMDD)格式。\n");
									}
								}
							}
						}						
						break;
					case 13: //登帳原因代碼
						if (Common.get(rowArray[5]).equals("1")) {
							if ("".equals(getLookupField(" select a.codeName from sysca_code a where a.codekindid='CAB' and a.codeid='"+Common.get(rowArray[i])+"'"))) sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");
						}else if(Common.get(rowArray[5]).equals("5")) {
							if ("".equals(getLookupField(" select a.codeName from sysca_code a where a.codekindid='CAC' and a.codeid='"+Common.get(rowArray[i])+"'"))) sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");							
						}else{
							rowArray[i]="";
						}
						break;
					case 14: //登帳原因-其他說明
						if (Common.get(rowArray[5]).equals("2")||Common.get(rowArray[5]).equals("3")) {
							if ("".equals(Common.get(rowArray[i]))) sbErrMsg.append("登帳原因-其他說明不能為空值。\n");
							else if (Common.get(rowArray[i]).length()>12) sbErrMsg.append("登帳原因-其他說明已超過12個字。\n");							
						}else if (Common.get(rowArray[5]).equals("1")||Common.get(rowArray[5]).equals("5")){
							if (!"99".equals(Common.get(rowArray[13]))) rowArray[i] = "";
							else{
								if ("".equals(Common.get(rowArray[i]))) sbErrMsg.append("登帳原因若為其他時，其他說明必須要有資料。\n");
								else if (Common.get(rowArray[i]).length()>20) sbErrMsg.append("登帳原因-其他說明已超過20個字。\n");								
							}
						}
						break;
					case 63://核准機關
						if (!"".equals(Common.get(rowArray[i]))){
							if ("".equals(getCodeName("APO",Common.get(rowArray[i])))) {							
								sbErrMsg.append("查無此 ").append(Common.get(rowArray[i])).append(" 核准機關代碼。\n");
							} else if(!"01".equals(Common.get(rowArray[i]))) {
								if ("".equals(Common.get(rowArray[64]))||"".equals(Common.get(rowArray[65]))) sbErrMsg.append("核准日期及文號不能為空值。\n ");
							}
						}
						break;
					default:
						sbErrMsg.append(checkMovableField(i, Common.get(rowArray[i])));						
						break;
					}
					if (sbErrMsg.toString().trim().length()>0) sbSQL.append(",''");
					else sbSQL.append(",").append(Common.sqlChar(Common.get(rowArray[i])));
				}
				
				sbSQL.append(",").append(Common.sqlChar(getEditID())).append(",").append(Common.sqlChar(Datetime.getYYYMMDD()));
				sbSQL.append(",").append(Common.sqlChar(Datetime.getHHMMSS()));
				
				if (!"".equals(Common.get(sbErrMsg.toString()))) {
					hashErr = true;					
					sbSQL.append(",").append(Common.sqlChar(sbErrMsg.toString()));
				} else {
					sbSQL.append(",''");
				}
				sbSQL.append(",");
				if (hashErr==false) {
					sbSQL.append(Common.sqlChar("N")).append("):;:");
					intSuccessRecord = intSuccessRecord + 1;
				} else {
					intErrorRecord = intErrorRecord + 1;
					sbSQL.append(Common.sqlChar("Y")).append("):;:");
				}
				rowid = rowid + 1;
				db.exeSQL(sbSQL.toString().split(":;:"));
			}

			try {
				if (rowid>2) {
					setErrorRecordCount(intErrorRecord);
					setSuccessRecordCount(intSuccessRecord);					
					db.doCommit();
				} else {
					db.doRollback();
					setErrorMsg("請檢查上傳的Excel檔中是否有資料!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				setErrorMsg("發生未預期的錯誤，請重新執行，若問題持續，請洽系統管理者或承辦人!");
				db.doRollback();				
			} finally {
				db.setAutoCommit(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMsg("發生未預期的錯誤，請重新執行，若問題持續，請洽系統管理者或承辦人!");
		} finally {
			db.closeAll();
		}
	}
}	

public String checkMovableField(int i, String s) {
	String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","珍貴財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","財產名稱","單位","主要材質","使用年限","財產別名","登帳原因代碼","登帳原因－其他說明","受贈同意函日期","受贈同意函文號","購置日期","數量","單價","總價","帳務摘要","會計科目","經費來源代碼","補助金額","品名","廠牌","型式","廠商代碼","廠商名稱","廠商統編","連絡電話1","連絡電話2","連絡人","傳真號碼","財產來源－種類代碼","財產來源－取得日期","財產來源－取得文號","牌照號碼規格","用途","保管單位代碼","保管單位名稱","保管人代碼","保管人名稱","使用單位代碼","使用單位名稱","使用人代碼","使用人名稱","存置地點","折舊方法","管理資料其他事項","接管機關代碼","交接日期","繳存地點","報廢或失竊原因","預估殘值總價","擬予處理意見","填單日期","填造單位","單據編號－字","單據編號－號","財產管理單位編號","傳票號數","核准機關代碼","核准日期","核准文號","備註"};
	try {
		switch (i) {
			case 0:
				if (s.equals("1")||s.equals("2")) {} 
				else return "權屬代碼只能是1或2。\n";					
				break;
			case 1:
				if (!checkPropertyKind(s)) return "財產性質必須是01,02,03或04。\n";					
				break;
			case 3:
				if (!Validate.checkYN(s)) {	
					return arrFieldName[i]+"，請輸入Y或N。";								
				}
				break;
				
			case 4: case 15: case 17: case 36: case 52: case 57: case 64:							
				if (!"".equals(Validate.checkDate(s, arrFieldName[i]))) {
					return Validate.checkDate(s, arrFieldName[i])+"\n";
				}
				if (i==4) {
					if ( s.equals("")) return arrFieldName[i]+"不能空白。\n";
					else if((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(s.substring(0,5)))){
						return "入帳年月必須大於月結年月！\n";
					}
				} else if (i==17){
					if ( s.equals("")) return arrFieldName[i]+"不能空白。\n";
				}
				break;
				
			case 5:							
				if ((!"".equals(s) && Validate.checkInt(s)) && (Integer.parseInt(s)>0 || Integer.parseInt(s)<6)) {}
				else return "增減別代碼只能是1,2,3,4,5。\n";					
				break;
				
			case 6:
				if (!"".equals(s)) {
					try {
						if (!Validate.checkAlphaInt(s)) return "原財產編號需為半形數字或英文字母。\n";
						
						if (Double.isNaN(Double.parseDouble(s.substring(0,1))) || Double.parseDouble(s.substring(0,1))<3 || Double.parseDouble(s.substring(0,1))>5) return "原財產編號需為3,4,5開頭的字母。\n";
						
						if (s.length()>3 && (Integer.parseInt(s.substring(0,3))>502 && Integer.parseInt(s.substring(0,3))<504)) return "";
						else if ("".equals(getPropertyName(enterOrg,s))) {		
							return "查無此 "+s+" 原財產編號。\n";
						}						
					} catch (NumberFormatException e) {
						return "原財產編號需為3,4,5開頭的字母。\n";
					}
				} else {
					return "原財產編號需不能為空值。\n";
				}
				break;
			
			case 7:
				if (s.equals("") || s.length()>7) return "原財產分號錯誤(空值或長度超過7碼)。\n";					
				break;
				
			case 8: case 39:
				if (s.length()>50) return arrFieldName[i]+"已超過50個字。\n";					
				break;
				
			case 9: case 10: case 29: case 48: case 53:
				if (s.length()>25) return arrFieldName[i]+"已超過25個字。\n";
				else if ((i==9 || i==10) && "".equals(s)) {
					return arrFieldName[i]+"不能空值。\n";
				}
				break;			
																		
			case 11:
				if (!"".equals(s) && s.length()<4) {
					try {
						if (Double.isNaN(Double.parseDouble(s)) || Double.parseDouble(s)<=0) return arrFieldName[i]+"　請輸入正整數。\n";
					} catch (NumberFormatException e) {
						return arrFieldName[i]+"　請輸入正整數。\n";
					}						
				} else return arrFieldName[i]+"　必須為<1000的正整數。\n";					
				break;
			
			case 12: case 16: case 21: case 31: case 32: case 34: case 37: case 56: case 65:
				if (s.length()>20) return arrFieldName[i]+"已超過20個字。\n";					
				break;					
				
			case 13: case 14:
				break;
				
			case 18: case 19: case 20:
				if (!"".equals(s)) {
					if (!Validate.checkInt(s)) return arrFieldName[i]+"　請輸入正整數。\n";
					else if (Integer.parseInt(s)<0){
						return arrFieldName[i]+"　必須 ≧ 0。\n";
					} else {
						if (i==18 && s.length()>7) return arrFieldName[i]+"　不能大於7位數。\n";
						if (i==19 && s.length()>13) return arrFieldName[i]+"　不能大於13位數。\n";
						if (i==20 && s.length()>15) return arrFieldName[i]+"　不能大於15位數。\n";
					}
				} else {
					if (i==18 || i==20) return arrFieldName[i]+"不能為空白。\n";
				}
				break;

			//會計科目
			case 22:
				if (s.length()>20) return arrFieldName[i]+"　己超過20個字元。\n";
				break;

			//case 22: case 25: case 26: case 27: case 33: case 38: case 54:
			case 25: case 33: case 38: case 54:
				if (s.length()>10) return arrFieldName[i]+"　己超過10個字元。\n";
				break;
				
			//廠牌
			case 26:
				if (s.length()>30) return arrFieldName[i]+"　己超過30個字元。\n";
				break;

			//型式
			case 27:
				if (s.length()>40) return arrFieldName[i]+"　己超過40個字元。\n";
				break;

			case 23:
				if (!"".equals(s) && "".equals(getCodeName("FSO",s))) return "查無該經費來源代碼。\n";
				break;
			
			case 24: case 58:
				if (s.length()>15) return arrFieldName[i]+"　己超過15個字元。\n";					
				break;
				
			case 28: case 61:
				if (!Validate.checkAlphaInt(s) || s.length()>10) return arrFieldName[i]+"　需為10個字元以內的半形數字或英文字母。\n";
				break;
				
			case 30:
				if (s.length()>8) return arrFieldName[i]+"　己超過8個字元。\n";					
				break;
				
			case 35:
				if (!"".equals(s) && "".equals(getCodeName("SKC",s))) return "查無該財產來源代碼。\n";
				break;
				
			case 40: case 42: case 44: case 46: 
				if ("".equals(s)) return arrFieldName[i]+"　不能為空值。\n";				
				else if (!Validate.checkAlphaInt(s) || s.length()>10) return arrFieldName[i]+"　需為10個字元以內的半形數字或英文字母。\n";
				break;	
				
			case 41: case  45: 
				if ("".equals(s)) return arrFieldName[i]+"　不能為空值。\n";
				else if (s.length()>30) return arrFieldName[i]+"　己超過30個字元。\n";
				break;
				
			case 43: case 47: case 59: case 60:
				if ("".equals(s)) return arrFieldName[i]+"　不能為空值。\n";
				else if (s.length()>10) return arrFieldName[i]+"　己超過10個字元。\n";
				break;
			
			case 49:
				if ("".equals(s) || "".equals(getCodeName("DEP",s))) return "查無該拆舊方法代碼。\n";
				break;
			
			case 50:
				if (s.length()>50) return arrFieldName[i]+"　己超過50個字元。\n";
				break;
				
			case 51:
				if (!"".equals(s) && "".equals(getOrganSName(s))) return "查無該接管機關代碼。\n";
				break;
				
			case 55:
				if (s.length()>15) return arrFieldName[i]+"　己超過15位數。\n";
				else if (!"".equals(s) && !Validate.checkInt(s)) return arrFieldName[i]+"　請輸入整數數值。\n";
				break;
				
			case 62:
				if (s.length()>15) return arrFieldName[i]+"　己超過15字元數。\n";
				else if (!"".equals(s) && !Validate.checkAlphaInt(s)) return arrFieldName[i]+"　需為15個字元以內的半形數字或英文字母。\n";
				break;
				
			case 63:
				if (!"".equals(s) && "".equals(getCodeName("APO",s))) return "查無該核准機關代碼。\n";
				break;
				
			case 66:
				if (s.length()>250) return "備註不能超過250個字元。\n";
				break;			
		}
		return "";
	} catch (Exception e) {
		e.printStackTrace();
		return "錯誤\n";			
	}			
}	


public void doMovableTransfer(String enterOrg) throws Exception {
	Database db = new Database();	
	try {
		db.setAutoCommit(false);
		String sql = "";
		ResultSet rc;
		boolean sFlag = false;
		ResultSet rs = db.querySQL("select enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName,otherPropertyUnit,otherMaterial,otherLimitYear,propertyName1,cause,cause1,approveDate1,approveDoc1,buyDate,originalAmount,originalUnit,originalBV,originalNote,accountingTitle,fundsSource,grantValue,articleName,nameplate,specification,storeNo,storeName,COMPANYID,tel1,tel2,seller,fax,SourceKind,sourceDate,sourceDoc,licensePlate,purpose,keepUnit,keepUnitName,keeper,keeperName,useUnit,useUnitName,userNo,userName,place,deprMethod,notes1,newEnterOrg,transferDate,returnPlace,cause2,scrapValue2,dealSuggestion,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,notes,editID,editDate,editTime,errorColumn,isErr from untup_movable where enterOrg="+Common.sqlChar(enterOrg)+" order by enterOrg,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo");
		while (rs.next()) {
			StringBuffer sbSQL = new StringBuffer(500);			
			String caseNo = Common.get(rs.getString("enterDate")).substring(0,5)+"00001";					
			if (sFlag==false) {
				switch (rs.getInt("adjustType")) {
				case 1: //增加					
					String serialNo="0000001";
					String lotNo = "0000001";
									
					//取得caseNo
					sql="select max(substr(caseNo,6))+1 as caseNo from UNTMP_AddProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
						" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
						" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
					rc = db.querySQL(sql);
					if (rc.next() && rc.getInt(1)>0){
						caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
					}
					rc.getStatement().close();
					rc.close();
																
					//取得lotNo
					sql="select nvl(max(lotNo),0)+1 as lotNo from UNTMP_Movable " +
						" where enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(rs.getString("ownership")) + 
						" and propertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo")) + 
						"";
					rc = db.querySQL(sql);
					if (rc.next()){
						lotNo = Common.formatFrontZero(rc.getString("lotNo"), 7);
					}
					rc.getStatement().close();
					rc.close();			
					
					//取得UNTMP_MovableDetail分號 - Movable's SerialNoS,SerialNoE same as serialNo
					sql="select nvl(max(serialNo),0)+1 as serialNo from UNTMP_MovableDetail " +
						" where enterOrg = " + Common.sqlChar(enterOrg) + 
						" and ownership = " + Common.sqlChar(rs.getString("ownership")) + 
						" and propertyNo = " + Common.sqlChar(rs.getString("oldPropertyNo")) + 
						"";		
					rc = db.querySQL(sql);
					if (rc.next() && rc.getInt(1)>0){
						serialNo = Common.formatFrontZero(rc.getString("serialNo"),7);
					} else {
						serialNo = "0000001";
					}
					rc.getStatement().close();
					rc.close();
					//取得UNTMP_MovableDetail分號				
					
					//取得propertyUnit, material, limitYear, 000000000A
					String propertyUnit = "", material = "", limitYear = "";
					sql="select propertyUnit, material, limitYear from SYSPK_PropertyCode " +
						" where enterOrg in ('000000000A'," + Common.sqlChar(enterOrg) + ") and propertyType='1' " +
						" and substr(propertyNo,1,1) in ('3','4','5') " + 
						" and propertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo"));
					rc = db.querySQL(sql);
					if (rc.next()){
					    propertyUnit = Common.get(rc.getString(1));
					    material = Common.get(rc.getString(2));
					    limitYear = Common.get(rc.getString(3));
					}
					rc.getStatement().close();
					rc.close();	
					
					//obj.setOtherLimitYear((Integer.parseInt(rs.getString("otherLimitYear"))==0)?"":rs.getString("otherLimitYear"));
					boolean limitYearFlag = false;
					if ("".equals(propertyUnit)) propertyUnit = Common.get(rs.getString("otherPropertyUnit"));
					else propertyUnit = "";
					if ("".equals(material)) material = Common.get(rs.getString("otherMaterial"));
					else material = "";
					if ("".equals(limitYear)) {
						limitYearFlag = true;
						limitYear = Common.get(rs.getString("otherLimitYear"));
					}

					String sDate = Common.get(rs.getString("buyDate"));
					DateTime buyDate =  new DateTime(Integer.parseInt(sDate.substring(0,3))+1911,Integer.parseInt(sDate.substring(3,5)),Integer.parseInt(sDate.substring(5)), 0, 0, 0, 0);
					String useEndYM = Datetime.getYYYMMDD(buyDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);				
					String permitReduceDate = Datetime.getYYYMMDD(buyDate.plusYears(Integer.parseInt(limitYear)).toDate());
					
					String scrapValue = "", deprAmount = "", apportionYear = "", monthDepr = "";
					String apportionEndYM = "", accumDeprYM = "", accumDepr = "";						
					try {
						String[] deprResult = getDeprResult(rs.getInt("deprMethod"), sDate, limitYear, rs.getDouble("originalBV"));
						scrapValue = deprResult[0];							
						deprAmount = deprResult[1];
						apportionYear = deprResult[2];
						monthDepr = deprResult[3];
						apportionEndYM = deprResult[4];
						accumDeprYM = deprResult[5];
						accumDepr = deprResult[6];							
					} catch (NumberFormatException e) {
						sFlag = true;
						setErrorMsg("拆舊方法必須是01,02,03,04或05！");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn='拆舊方法必須是01,02,03,04或05！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
							db1.doCommit();
						} catch (Exception e1) {
							sFlag = true;
							setErrorMsg("拆舊方法必須是01,02,03,04或05！");
							
							e.printStackTrace();							
						} finally {
							db1.closeAll();
						}						
						break;						
						
					}					
					
					/**
					double scrapValue = 0, monthDepr = 0, deprAmount = 0, accumDepr = 0;
					int intLimitYear = (int)Function.getNumeric(limitYear);
					if (intLimitYear<=0) intLimitYear = 1;					
					//double originalUnit = rs.getDouble("originalUnit"); 
					double originalBV = rs.getDouble("originalBV");				
					String useEndYM="", accumDeprYM="", apportionYear="", apportionEndYM="", permitReduceDate="";
					useEndYM = Datetime.getYYYMMDD(buyDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);				
					permitReduceDate = Datetime.getYYYMMDD(buyDate.plusYears(Integer.parseInt(limitYear)).toDate());
					
					try {
						switch (rs.getInt("deprMethod")) {
						case 1:									
							break;
						case 2:							
							scrapValue = Math.round(originalBV/(Function.getNumeric(limitYear)+1));
							deprAmount = originalBV - scrapValue;
							apportionYear = limitYear;
							monthDepr = Math.round(originalBV/Function.getNumeric(apportionYear)/12);
							apportionEndYM = Datetime.getYYYMMDD(buyDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);
							accumDeprYM = Datetime.getYYYMMDD(buyDate.minusMonths(1).toDate()).substring(0,5);
							accumDepr = 0;
							break;
						case 3:
							scrapValue = Math.round(originalBV/(Function.getNumeric(limitYear)+1));
							deprAmount = originalBV;
							apportionYear = ""+(Function.getNumeric(limitYear)+1);
							monthDepr = Math.round(originalBV/Function.getNumeric(apportionYear)/12);
							apportionEndYM = Datetime.getYYYMMDD(buyDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);
							accumDeprYM = Datetime.getYYYMMDD(buyDate.minusMonths(1).toDate()).substring(0,5);
							accumDepr = 0;
							break;
						case 4:
							scrapValue = 0;
							deprAmount = originalBV;
							apportionYear = ""+(Function.getNumeric(limitYear)+1);
							monthDepr = Math.round(originalBV/Function.getNumeric(apportionYear)/12);
							apportionEndYM = Datetime.getYYYMMDD(buyDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);
							accumDeprYM = Datetime.getYYYMMDD(buyDate.minusMonths(1).toDate()).substring(0,5);
							accumDepr = 0;							
							break;
						case 5:
							scrapValue=0;
							break;																					
						}
					} catch (NumberFormatException e) {
						sFlag = true;
						setErrorMsg("拆舊方法必須是01,02,03,04或05！");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn='拆舊方法必須是01,02,03,04或05！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
							db1.doCommit();
						} catch (Exception e1) {
							sFlag = true;
							setErrorMsg("拆舊方法必須是01,02,03,04或05！");
							
							e.printStackTrace();							
						} finally {
							db1.closeAll();
						}						
						break;						
						
					}		
					**/			
					
					if((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(Common.get(rs.getString("enterDate")).substring(0,5)))){
						sFlag = true;
						setErrorMsg("入帳年月必須大於月結年月！");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn='入帳年月必須大於月結年月！(建議：請不要使用上傳作業上傳後，又使用本系統畫面登打資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
							db1.doCommit();
						} catch (Exception e) {
							sFlag = true;
							setErrorMsg("入帳年月必須大於月結年月！");
							
							e.printStackTrace();
						} finally {							
							db1.closeAll();
						}						
						break;
					}					
					
					sql=" select count(*) as checkResult from UNTMP_Movable a, UNTMP_MovableDetail b where a.enterOrg=b.enterOrg " +
						" and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.lotno=b.lotno " + 
						" and a.enterOrg = " + Common.sqlChar(enterOrg) + 
						" and a.ownership = " + Common.sqlChar(rs.getString("ownership")) + 
						" and b.oldPropertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo")) + 
						" and b.oldSerialNo = " + Common.sqlChar(rs.getString("oldserialNo"));
					rc = db.querySQL(sql);
					if (rc.next() && rc.getInt(1)>0) {
						sFlag = true;
						setErrorMsg("資料己重複！");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn='資料己重複！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
							db1.doCommit();
						} catch (Exception e) {
							sFlag = true;
							setErrorMsg("資料己重複！");							
							e.printStackTrace();							
						} finally {							
							db1.closeAll();
						}
						break;						
					}
					rc.getStatement().close();
					rc.close();					
										
					//If !IsExists(oldPropertyNo) then Insert it
					if (Common.get(rs.getString("oldPropertyNo")).length()>3 && (Integer.parseInt(Common.get(rs.getString("oldPropertyNo")).substring(0,3))>502 && Integer.parseInt(Common.get(rs.getString("oldPropertyNo")).substring(0,3))<504)) {
						if ("".equals(getPropertyName(enterOrg,Common.get(rs.getString("oldPropertyNo"))))) {

							sbSQL.append("delete from SYSPK_PropertyCode where enterOrg=").append(Common.sqlChar(enterOrg)).append(" and propertyNo=").append(Common.sqlChar(rs.getString("oldpropertyNo"))).append(":;:");
							
							sbSQL.append(" insert into SYSPK_PropertyCode(" ).append(
								" enterOrg,").append(
								" propertyNo,").append(
								" propertyType,").append(
								" propertyName,").append(
								" propertyUnit,").append(
								" material,").append(
								" limitYear,").append(
								" memo,").append(
								" editID,").append(
								" editDate,").append(
								" editTime").append(
							" )Values(" ).append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldpropertyNo"))) ).append( "," ).append(
								Common.sqlChar("1") ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("propertyName"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("otherpropertyUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("othermaterial"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("otherlimitYear"))) ).append( "," ).append(
								Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
						}
					} else if ("".equals(getPropertyName(enterOrg,Common.get(rs.getString("oldPropertyNo"))))) {
						sFlag = true;
						setErrorMsg("查無該財產編號！");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn='查無該財產編號！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
							db1.doCommit();
						} catch (Exception e) {
							sFlag = true;
							
							e.printStackTrace();							
						} finally {							
							db1.closeAll();
						}
						break;						
					}
					
					//KeepUnit & Keeper
					sbSQL.append(getKeepUnitSQL(enterOrg, rs.getString("keepUnit"), rs.getString("keepUnitName")));
					sbSQL.append(getKeeperSQL(enterOrg, rs.getString("keepUnit"), rs.getString("keeper"), rs.getString("keeperName")));					
					if (!Common.get(rs.getString("keepUnit")).equals(Common.get(rs.getString("useUnit")))) {						
						sbSQL.append(getKeepUnitSQL(enterOrg, rs.getString("useUnit"), rs.getString("useUnitName")));						
						sbSQL.append(getKeeperSQL(enterOrg, rs.getString("useUnit"), rs.getString("userNo"), rs.getString("userName")));						
					} else {
						if (!Common.get(rs.getString("keeper")).equals(Common.get(rs.getString("userNo")))) {
							sbSQL.append(getKeeperSQL(enterOrg, rs.getString("useUnit"), rs.getString("userNo"), rs.getString("userName")));
						}						
					}
					//End of KeepUnit & Keeper				
					
					//UNTMP_Store
					sbSQL.append(getSupplierSQL(enterOrg, rs.getString("storeNo"), rs.getString("storeName"), rs.getString("companyID"), rs.getString("tel1"), rs.getString("tel2"), rs.getString("seller"), rs.getString("fax")));
					//End of UNTMP_Store
					
					//AddProof
					sbSQL.append(" insert into UNTMP_AddProof(" ).append(
						" enterOrg,").append(
						" ownership,").append(
						" caseNo,").append(
						" caseName,").append(
						" writeDate,").append(
						" writeUnit,").append(
						" proofDoc,").append(
						" proofNo,").append(
						" manageNo,").append(
						" summonsNo,").append(
						" enterDate,").append(
						" verify,").append(
						" closing,").append(
						" notes,").append(
						" editID,").append(
						" editDate,").append(
						" editTime").append(
					" )Values(" ).append(
						Common.sqlChar(enterOrg) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
						Common.sqlChar(caseNo) ).append( ",'上傳列號" ).append(
						rs.getString("textSerialNo")).append( "'," ).append(
						Common.sqlChar(Common.get(rs.getString("writeDate"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("writeUnit"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("proofDoc"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("proofNo"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("manageNo"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("summonsNo"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
						Common.sqlChar("Y") ).append( "," ).append(
						Common.sqlChar("N") ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("notes"))) ).append( "," ).append(
						Common.sqlChar(getEditID()) ).append( "," ).append(
						Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
						Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
					//End of AddProof
					
					//UNTMP_Movable
					sbSQL.append(" insert into UNTMP_Movable(" ).append(
							" enterOrg,").append(
							" ownership,").append(
							" caseNo,").append(
							" propertyNo,").append(
							" lotNo,").append(
							" serialNoS,").append(
							" serialNoE,").append(		
							" otherPropertyUnit,").append(									
							" otherMaterial,").append(
							" otherLimitYear,").append(									
							" propertyName1,").append(
							" cause,").append(
							" cause1,").append(
							" approveDate,").append(
							" approveDoc,").append(									
							" enterDate,").append(
							" buyDate,").append(									
							" dataState,").append(
							" verify,").append(
							" closing,").append(
							" propertyKind,").append(
							" fundType,").append(
							" valuable,").append(
							" originalAmount,").append(
							" originalUnit,").append(
							" originalBV,").append(
							" originalNote,").append(
							" accountingTitle,").append(
							" bookAmount,").append(
							" bookValue,").append(
							" fundsSource,").append(
							" grantValue,").append(
							" articleName,").append(
							" nameplate,").append(
							" specification,").append(
							" storeNo,").append(
							" sourceKind,").append(
							" sourceDate,").append(
							" sourceDoc,").append(
							" deprMethod,").append(
							" scrapValue,").append(
							" useEndYM,").append(
							" apportionEndYM,").append(
							" permitReduceDate,").append(
							" picture,").append(
							" oldPropertyNo,").append(									
							" oldSerialNoS,").append(
							" oldSerialNoE,").append(									
							" notes,").append(									
							" editID,").append(
							" editDate,").append(
							" editTime").append(
						" )Values(" ).append(
							Common.sqlChar(enterOrg) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
							Common.sqlChar(caseNo) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
							Common.sqlChar(lotNo) ).append( "," ).append(
							Common.sqlChar(Common.get(serialNo)) ).append( "," ).append(
							Common.sqlChar(Common.get(serialNo)) ).append( "," );
					
							sbSQL.append(Common.sqlChar(Common.get(propertyUnit)) ).append(",").append(									
							Common.sqlChar(Common.get(material) )).append( "," );
							if (limitYearFlag) {
								sbSQL.append(Common.sqlChar(Common.get(rs.getString("otherLimitYear")))).append( "," );
							} else {
								sbSQL.append("''," );
							}
														
							sbSQL.append(Common.sqlChar(Common.get(rs.getString("propertyName1"))) ).append( "," ).append(									
							Common.sqlChar(Common.get(rs.getString("cause"))) ).append( "," ).append(									
							Common.sqlChar(Common.get(rs.getString("cause1"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("approveDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("approveDoc"))) ).append( "," ).append(									
							Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("buyDate"))) ).append( "," ).append(									
							Common.sqlChar("1") ).append( "," ).append(
							Common.sqlChar("Y") ).append( "," ).append(
							Common.sqlChar("N") ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("propertyKind"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("fundType"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("valuable"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("originalAmount"))) ).append( "," ).append(							
							Common.sqlChar(Common.get(rs.getString("originalUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("originalBV"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("originalNote"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("accountingTitle"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("originalAmount"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("originalBV"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("fundsSource"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("grantValue"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("articleName"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("nameplate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("specification"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("storeNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("sourceKind"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("sourceDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("sourceDoc"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("deprMethod"))) ).append( "," ).append(
							Common.sqlChar(scrapValue) ).append( "," ).append(
							Common.sqlChar(useEndYM) ).append( "," ).append(
							Common.sqlChar(apportionEndYM) ).append( "," ).append(
							Common.sqlChar(permitReduceDate) ).append( "," ).append(
							Common.sqlChar("") ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(									
							Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) ).append( "," ).append(									
							Common.sqlChar(Common.get(rs.getString("notes"))) ).append( "," ).append(									
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
						//End of UNTMP_Movable
							
						//UNTMP_MovableDetail
						sbSQL.append(" insert into UNTMP_MovableDetail(" ).append(
								" enterOrg,").append(
								" ownership,").append(
								" propertyNo,").append(
								" lotNo,").append(
								" serialNo,").append(
								" dataState,").append(		
								" reduceDate,").append(									
								" reduceCause,reduceCause1,").append(
								" verify,").append(
								" closing,").append(
								" originalAmount,").append(
								" originalBV,").append(
								" bookAmount,").append(
								" bookValue,").append(
								" licensePlate,").append(
								" purpose,").append(
								" originalMoveDate,").append(
								" originalKeepUnit,").append(
								" originalKeeper,").append(
								" originalUseUnit,").append(
								" originalUser,").append(
								" originalPlace,").append(
								" moveDate,").append(
								" keepUnit,").append(
								" keeper,").append(
								" useUnit,").append(
								" userNo,").append(
								" place,").append(										
								" damageDate,").append(										
								" damageExpire,").append(
								" damageMark,").append(										
								" scrapValue,").append(
								" deprAmount,").append(
								" apportionYear,").append(
								" monthDepr,").append(
								" accumDeprYM,").append(
								" accumDepr,").append(
								" notes1,").append(
								" notes,").append(										
								" oldPropertyNo,").append(										
								" oldSerialNo,").append(																		
								" editID,").append(
								" editDate,").append(
								" editTime,").append(
								" barcode").append(
							" )Values(" ).append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
								Common.sqlChar(lotNo) ).append( "," ).append(
								Common.sqlChar(Common.get(serialNo)) ).append( ",'1'," ).append(									
								Common.sqlChar("") ).append( "," ).append(									
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("Y") ).append( "," ).append(
								Common.sqlChar("N") ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalAmount"))) ).append( "," ).append(							
								Common.sqlChar(Common.get(rs.getString("originalBV"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalAmount"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalBV"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("licensePlate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("purpose"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("keepUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("keeper"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("useUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("userNo"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("place"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("keepUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("keeper"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("useUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("userNo"))) ).append( "," ).append(										
								Common.sqlChar(Common.get(rs.getString("place"))) ).append( ",'','',''," ).append(										
								Common.sqlChar(scrapValue) ).append( "," ).append(
								Common.sqlChar(deprAmount) ).append( "," ).append(
								Common.sqlChar(apportionYear) ).append( "," ).append(
								Common.sqlChar(monthDepr) ).append( "," ).append(
								Common.sqlChar(accumDeprYM) ).append( "," ).append(		
								Common.sqlChar(accumDepr) ).append( "," ).append(																
								Common.sqlChar(Common.get(rs.getString("notes1"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("notes"))) ).append( "," ).append(																				
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) ).append( "," ).append(																	
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "," ).append(
										" (select " + Common.sqlChar(ownership) +"|| " + " substr( " + Common.sqlChar(rs.getString("buyDate")) + ",0,3) || " 
										+ "substr( " + Common.sqlChar(rs.getString("oldPropertyNo")) + " ,0,1) || lpad((substr(nvl(max(barcode),'00000000000'),6,6) + 1),6,0) from UNTMP_MovableDetail m where 1=1 and substr(barcode,0,5) = "
										+ Common.sqlChar(ownership + rs.getString("buyDate").substring(0,3) + rs.getString("oldPropertyNo").substring(0,1))+" and enterorg= "+Common.sqlChar(enterOrg)+")").append( ")" ).append(
								":;:") ;
						
					db.exeSQL(sbSQL.toString().split(":;:"));
					break;
					
				case 2: case 3: //動產增減單 UNTMP_AdjustProof, UNTMP_AdjustDetail & update UNTMP_Moveable
					String op = "+";
					if (rs.getInt("adjustType")==2) op = "+";
					else if (rs.getInt("adjustType")==3) op = "-";			
										
					//取得caseNo
					sql="select max(substr(caseNo,6))+1 as caseNo from UNTMP_AdjustProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
						" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
						" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
					rc = db.querySQL(sql);
					if (rc.next() && rc.getInt(1)>0){
						caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
					}
					rc.getStatement().close();
					rc.close();					
					
					sql = " select a.propertyNo, a.lotNo, b.serialNo, a.fundType, a.valuable, a.otherPropertyUnit, a.otherMaterial, a.otherLimitYear, a.propertyName1, a.sourceDate, b.bookAmount, " +
						" decode(a.originalUnit,null,null,b.bookValue) bookUnit, b.bookValue, b.keepUnit, b.keeper, b.useUnit, b.userNo, b.place from UNTMP_Movable a, UNTMP_MovableDetail b " +
						" where a.enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +						
						" and a.ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
						" and a.propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +					
						" and nvl(a.valuable,'N') = nvl('" + Common.get(rs.getString("valuable")) + "','N') " +						
						" and a.oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
						" and b.oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) +
						" and a.enterOrg=b.enterOrg and a.ownership=b.ownership " +
						" and a.propertyNo=b.propertyNo and a.lotNo=b.lotNo ";
					if (Common.get(rs.getString("propertyKind")).equals("03")) {
						sql += " and nvl(a.fundType,'99')=nvl('" + Common.get(rs.getString("fundType")) + "','99') ";
					} else {
						sql += " and nvl(a.fundType,'99')=nvl('" + "" + "','99') ";
					}
					rc = db.querySQL(sql);
					if (rc.next()) {
						sbSQL.append(" insert into UNTMP_AdjustProof (" ).append(
							" enterOrg,").append(
							" ownership,").append(
							" caseNo,").append(
							" caseName,").append(
							" writeDate,").append(
							" writeUnit,").append(
							" proofDoc,").append(
							" proofNo,").append(
							" manageNo,").append(
							" summonsNo,").append(
							" adjustDate,").append(
							" approveOrg,").append(
							" approveDate,").append(
							" approveDoc,").append(
							" verify,").append(
							" notes,").append(
							" editID,").append(
							" editDate,").append(
							" editTime").append(
						" ) Values (" ).append(
							Common.sqlChar(enterOrg) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
							Common.sqlChar(caseNo) ).append( ",'上傳列號" ).append(
							rs.getString("textSerialNo")).append( "'," ).append(
							Common.sqlChar(Common.get(rs.getString("writeDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("writeUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("proofDoc"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("proofNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("manageNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("summonsNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("approveOrg"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("approveDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("approveDoc"))) ).append( "," ).append( 
							"'Y','").append(Common.get(rs.getString("notes"))).append("\n由上傳作業輸入',").append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
							
						sbSQL.append(" insert into UNTMP_AdjustDetail(" ).append(
							" enterOrg,").append(
							" ownership,").append(
							" caseNo,").append(
							" propertyNo,").append(
							" lotNo,").append(									
							" serialNo,").append(
							" otherPropertyUnit,").append(
							" otherMaterial,").append(
							" otherLimitYear,").append(
							" propertyName1,").append(
							" cause,").append(
							" adjustDate,").append(									
							" verify,").append(
							" propertyKind,").append(
							" fundType,").append(
							" valuable,").append(
							" sourceDate,").append(
							" bookAmount,").append(
							" oldBookUnit,").append(
							" oldBookValue,").append(
							" adjustType,").append(
							" adjustBookUnit,").append(
							" adjustBookValue,").append(
							" newBookUnit,").append(
							" newBookValue,").append(
							" bookNotes,").append(
							" keepUnit,").append(
							" keeper,").append(
							" useUnit,").append(
							" userNo,").append(
							" place,").append(
							" notes,").append(
							" oldPropertyNo,").append(
							" oldSerialNo,").append(
							" editID,").append(
							" editDate,").append(
							" editTime").append( 
						" )Values(" ).append(
							Common.sqlChar(enterOrg) ).append( "," ).append(
							Common.sqlChar(rs.getString("ownership")) ).append( "," ).append(
							Common.sqlChar(caseNo) ).append( "," ).append(
							Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
							Common.sqlChar(rc.getString("lotNo")) ).append( "," ).append(									
							Common.sqlChar(rc.getString("serialNo")) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("otherPropertyUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("otherMaterial"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("otherLimitYear"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("propertyName1"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("cause1"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
							Common.sqlChar("Y") ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("propertyKind"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("fundType"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("valuable"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("sourceDate"))) ).append( "," ).append(									
							Common.sqlChar(Common.get(rc.getString("bookAmount"))) ).append( "," ).append(
							
							Common.sqlChar(Common.get(rc.getString("bookUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("bookValue"))) ).append( "," ).append(
							" decode('").append(rs.getString("adjustType")).append("','2','1','2') ").append( "," );
						
							if ("".equals(rc.getString("bookUnit"))) {
								sbSQL.append("'','',");
							} else {
								sbSQL.append(Common.sqlChar(Common.get(rs.getString("originalUnit")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("originalBV")))).append(",");								
							}						
							sbSQL.append("(" ).append(rc.getDouble("bookUnit")).append(op).append(rs.getDouble("originalUnit")).append( "),(" ).append(
							rc.getString("bookValue")).append(op).append(rs.getString("originalBV")).append( ")," ).append(									
							Common.sqlChar(Common.get(rs.getString("originalNote"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("keepUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("keeper"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("useUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("userNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("place"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("notes"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) ).append( "," ).append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
							
							sbSQL.append(" update UNTMP_Movable set bookValue=(bookValue").append(op).append(
									rs.getDouble("originalBV")).append(") ").append(
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and lotNo=" ).append( Common.sqlChar(Common.get(rc.getString("lotNo")))).append(":;:");							
							
							sbSQL.append(" update UNTMP_MovableDetail set bookValue=(").append(rc.getString("bookValue")).append(
								op).append(rs.getString("originalBV")).append( ") " ).append(										
								" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
								" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
								" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
								" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
							
							db.exeSQL(sbSQL.toString().split(":;:"));
														
												
					} else {
						sFlag = true;
						setErrorMsg("查無增加資料！");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn=errorColumn||'查無增加資料！(建議：在上傳的Excel檔案中依增加、增減值、減少等屬性排放資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo='"+rs.getString("textSerialNo")+"'");
							db1.doCommit();
						} catch (Exception e) {
							sFlag = true;
							setErrorMsg("查無增加資料！");
							
							e.printStackTrace();							
						} finally {
							db1.closeAll();
						}
						break;				
					}
					break;
				case 4:	//移動作業				
					//取得caseNo					
					sql="select max(substr(caseNo,6))+1 as caseNo from UNTMP_MoveProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
						" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
						" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
					rc = db.querySQL(sql);
					if (rc.next() && rc.getInt(1)>0){
						caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
					}
					rc.getStatement().close();
					rc.close();		
					
					//KeepUnit & Keeper
					sbSQL.append(getKeepUnitSQL(enterOrg, rs.getString("keepUnit"), rs.getString("keepUnitName")));
					sbSQL.append(getKeepUnitSQL(enterOrg, rs.getString("useUnit"), rs.getString("useUnitName")));
					sbSQL.append(getKeeperSQL(enterOrg, rs.getString("keepUnit"), rs.getString("keeper"), rs.getString("keeperName")));
					sbSQL.append(getKeeperSQL(enterOrg, rs.getString("useUnit"), rs.getString("userNo"), rs.getString("userName")));					
					//End of KeepUnit & Keeper										
					
					//UNTMP_Store
					sbSQL.append(getSupplierSQL(enterOrg, rs.getString("storeNo"), rs.getString("storeName"), rs.getString("companyID"), rs.getString("tel1"), rs.getString("tel2"), rs.getString("seller"), rs.getString("fax")));
					//End of UNTMP_Store
					
					sbSQL.append(" insert into UNTMP_MoveProof(" ).append(
					" enterOrg,").append(
					" ownership,").append(
					" caseNo,").append(
					" caseName,").append(
					" writeDate,").append(
					" writeUnit,").append(
					" proofDoc,").append(
					" proofNo,").append(
					" moveDate,").append(
					" verify,").append(
					" notes,").append(
					" editID,").append(
					" editDate,").append(
					" editTime").append(
				" )Values(" ).append(
					Common.sqlChar(enterOrg) ).append( "," ).append(
					Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
					Common.sqlChar(caseNo) ).append( ",'上傳列號" ).append(
					rs.getString("textSerialNo")).append( "'," ).append(
					Common.sqlChar(Common.get(rs.getString("writeDate"))) ).append( "," ).append(
					Common.sqlChar(Common.get(rs.getString("writeUnit"))) ).append( "," ).append(
					Common.sqlChar(Common.get(rs.getString("proofDoc"))) ).append( "," ).append(
					Common.sqlChar(Common.get(rs.getString("proofNo"))) ).append( "," ).append(
					Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
					Common.sqlChar("Y") ).append( ",'" ).append(Common.get(rs.getString("notes"))).append(
					"由上傳作業輸入" ).append( "'," ).append(
					Common.sqlChar(getEditID()) ).append( "," ).append(
					Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
					Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");							
					
					
					sql = "select a.propertyNo, a.lotNo,b.serialNo,a.propertyName1,a.otherPropertyUnit,a.otherMaterial, " +
				      " a.otherLimitYear,a.buyDate, b.bookAmount, b.scrapValue, " +
				      " decode(a.originalUnit,null,null,b.bookValue) bookUnit,b.bookValue, " +
				      " a.nameplate,a.specification,a.sourceDate,b.moveDate, " +
				      " b.keepUnit,b.keeper,b.useUnit,b.userNo,b.place, " +
				      " substr('"+rs.getString("enterDate")+"',1,3) - substr(a.buyDate,1,3) + " +
				      " decode(sign(substr('"+rs.getString("enterDate")+"',4,4)-substr(a.buyDate,4,4)),-1,-1,0) useYear, " +
				      " MOD( MONTHS_BETWEEN( to_date(substr('"+rs.getString("enterDate")+"',1,5)|| " +
				      " '01'+19110000,'YYYYMMDD') ,to_date(substr(a.buyDate,1,5)|| " +
				      " '01'+19110000,'YYYYMMDD') ) ,12 ) useMonth " +
				   " from UNTMP_Movable a,UNTMP_MovableDetail b " +
				     " where a.enterOrg='"+rs.getString("enterOrg")+"'"+
				     "  and a.ownership='"+rs.getString("ownership")+"'"+
				     "  and a.propertyKind='"+rs.getString("propertyKind")+"'"+
				     "  and nvl(a.valuable,'N') =nvl('"+rs.getString("valuable")+"','N') "+
				     "  and a.oldPropertyNo ='"+rs.getString("oldPropertyNo")+"'"+
				     "  and b.oldSerialNo ='"+rs.getString("oldSerialNo")+"'"+
				     "  and a.enterOrg=b.enterOrg " +
				     "  and a.ownership=b.ownership " +
				     "  and a.propertyNo=b.propertyNo " +
				     "  and a.lotNo =b.lotNo ";
					if (Common.get(rs.getString("propertyKind")).equals("03")) {
						sql += " and nvl(a.fundType,'99')=nvl('" + Common.get(rs.getString("fundType")) + "','99') ";
					} else {
						sql += " and nvl(a.fundType,'99')=nvl('" + "" + "','99') ";
					}
					
					rc = db.querySQL(sql);
					if (rc.next()) {					
						sbSQL.append(" insert into UNTMP_MoveDetail(" ).append(
							" enterOrg,").append(
							" ownership,").append(
							" caseNo,").append(
							" propertyNo,").append(
							" lotNo,").append(
							" serialNo,").append(
							" propertyName1,").append(
							" otherPropertyUnit,").append(						
							" otherMaterial,").append(
							" otherLimitYear,").append(
							" buyDate,").append(
							" verify,").append(
							" propertyKind,").append(
							" fundType,").append(
							" valuable,").append(
							" bookAmount,").append(
							" bookUnit,").append(
							" bookValue,").append(
							" nameplate,").append(
							" specification,").append(
							" sourceDate,").append(
							" oldMoveDate,").append(
							" oldKeepUnit,").append(
							" oldKeeper,").append(
							" oldUseUnit,").append(
							" oldUserNo,").append(
							" oldPlace,").append(
							" newMoveDate,").append(
							" newKeepUnit,").append(
							" newKeeper,").append(
							" newUseUnit,").append(
							" newUserNo,").append(
							" newPlace,").append(
							" useYear,").append(
							" useMonth,").append(
							" scrapValue,").append(
							" notes,").append(
							" oldPropertyNo,").append(
							" oldSerialNo,").append(
							" editID,").append(
							" editDate,").append(
							" editTime").append(
						" )Values(" ).append(
							Common.sqlChar(enterOrg) ).append( "," ).append(
							Common.sqlChar(rs.getString("ownership")) ).append( "," ).append(
							Common.sqlChar(caseNo) ).append( "," ).append(
							Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
							Common.sqlChar(rc.getString("lotNo")) ).append( "," ).append(
							Common.sqlChar(rc.getString("serialNo")) ).append( "," ).append(
							Common.sqlChar(rc.getString("propertyName1")) ).append( "," ).append(
							Common.sqlChar(rc.getString("otherPropertyUnit")) ).append( "," ).append(						
							Common.sqlChar(rc.getString("otherMaterial")) ).append( "," ).append(
							Common.sqlChar(rc.getString("otherLimitYear")) ).append( "," ).append(
							Common.sqlChar(rc.getString("buyDate")) ).append( "," ).append(
							Common.sqlChar("Y") ).append( "," ).append(
							Common.sqlChar(rs.getString("propertyKind")) ).append( "," ).append(
							Common.sqlChar(rs.getString("fundType")) ).append( "," ).append(
							Common.sqlChar(rs.getString("valuable")) ).append( "," ).append(
							Common.sqlChar(rc.getString("bookAmount")) ).append( "," ).append(
							Common.sqlChar(rc.getString("bookUnit")) ).append( "," ).append(
							Common.sqlChar(rc.getString("bookValue")) ).append( "," ).append(
							Common.sqlChar(rc.getString("nameplate")) ).append( "," ).append(
							Common.sqlChar(rc.getString("specification")) ).append( "," ).append(
							Common.sqlChar(rc.getString("sourceDate")) ).append( "," ).append(
							Common.sqlChar(rc.getString("moveDate")) ).append( "," ).append(
							Common.sqlChar(rc.getString("keepUnit")) ).append( "," ).append(
							Common.sqlChar(rc.getString("keeper")) ).append( "," ).append(
							Common.sqlChar(rc.getString("useUnit")) ).append( "," ).append(
							Common.sqlChar(rc.getString("userNo")) ).append( "," ).append(
							Common.sqlChar(rc.getString("place")) ).append( "," ).append(
							Common.sqlChar(rs.getString("enterDate")) ).append( "," ).append(
							Common.sqlChar(rs.getString("keepUnit")) ).append( "," ).append(
							Common.sqlChar(rs.getString("keeper")) ).append( "," ).append(
							Common.sqlChar(rs.getString("useUnit")) ).append( "," ).append(
							Common.sqlChar(rs.getString("userNo")) ).append( "," ).append(
							Common.sqlChar(rs.getString("place")) ).append( "," ).append(
							Common.sqlChar(rc.getString("useYear")) ).append( "," ).append(
							Common.sqlChar(rc.getString("useMonth")) ).append( "," ).append(
							Common.sqlChar(rc.getString("scrapValue")) ).append( "," ).append(
							Common.sqlChar(rs.getString("notes")) ).append( "," ).append(
							Common.sqlChar(rs.getString("oldPropertyNo")) ).append( "," ).append(
							Common.sqlChar(rs.getString("oldSerialNo")) ).append( "," ).append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
						
						sbSQL.append(" update UNTMP_MovableDetail set moveDate=").append(Common.sqlChar(rs.getString("enterDate"))).append(",").append(
							" keepUnit='").append(rs.getString("keepUnit")).append("',").append(	
							" keeper='").append(rs.getString("keeper")).append("',").append(
							" useUnit='").append(rs.getString("useUnit")).append("',").append(
							" userNo='").append(rs.getString("userNo")).append("',").append(
							" place='").append(rs.getString("place")).append("' ").append(
							" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
							" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
							" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
							" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");		
						
						db.exeSQL(sbSQL.toString().split(":;:"));
					} else {
						sFlag = true;
						setErrorMsg("查無增加資料！");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn=errorColumn||'查無該筆動產資料！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
							db1.doCommit();
						} catch (Exception e) {
							sFlag = true;
							setErrorMsg("查無增加資料！");							
							e.printStackTrace();							
						} finally {
							db1.closeAll();
						}
						break;				
					}
					break;
				case 5://減損
					//取得caseNo					
					sql="select max(substr(caseNo,6))+1 as caseNo from UNTMP_ReduceProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
						" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
						" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
					rc = db.querySQL(sql);
					if (rc.next() && rc.getInt(1)>0){
						caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
					}
					rc.getStatement().close();
					rc.close();			
					
					sbSQL.append(" insert into UNTMP_ReduceProof(" ).append(
						" enterOrg,").append(
						" ownership,").append(
						" caseNo,").append(
						" caseName,").append(
						" writeDate,").append(
						" writeUnit,").append(
						" proofDoc,").append(
						" proofNo,").append(
						" manageNo,").append(
						" summonsNo,").append(
						" reduceDate,").append(
						" approveOrg,").append(
						" approveDate,").append(
						" approveDoc,").append(
						" verify,").append(
						" notes,").append(
						" editID,").append(
						" editDate,").append(
						" editTime").append(
					" )Values(" ).append(
						Common.sqlChar(enterOrg) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
						Common.sqlChar(caseNo) ).append( ",'上傳列號" ).append(
						rs.getString("textSerialNo")).append( "'," ).append(
						Common.sqlChar(Common.get(rs.getString("writeDate"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("writeUnit"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("proofDoc"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("proofNo"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("manageNo"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("summonsNo"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("approveOrg"))) ).append( "," ).append(														
						Common.sqlChar(Common.get(rs.getString("approveDate"))) ).append( "," ).append(
						Common.sqlChar(Common.get(rs.getString("approveDoc"))) ).append( "," ).append(								
						Common.sqlChar("Y") ).append( ",'" ).append(Common.get(rs.getString("notes"))).append(
						"由上傳作業輸入" ).append( "'," ).append(
						Common.sqlChar(getEditID()) ).append( "," ).append(
						Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
						Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");					
					
					sql = "select a.propertyNo,a.lotNo,b.serialNo,a.otherPropertyUnit,a.otherMaterial,"+
					      " a.otherLimitYear,a.propertyName1,a.enterDate,a.buyDate, " +
					      " a.accountingTitle,b.bookAmount, " +
					      " decode(a.originalUnit,null,null,b.bookValue) bookUnit,b.bookValue, " +
					      " a.articleName,a.nameplate,a.specification,a.sourceDate,b.licensePlate, " +
					      " b.moveDate,b.keepUnit,b.keeper,b.useUnit,b.userNo,b.place, " +
					      " substr('"+rs.getString("enterDate")+"',1,3) - substr(a.buyDate,1,3) + " +
					      " decode(sign(substr('"+rs.getString("enterDate")+"',4,4)- " +
					      " substr(a.buyDate,4,4)),-1,-1,0) useYear, " +
					      " MOD( MONTHS_BETWEEN( to_date(substr('"+rs.getString("enterDate")+"',1,5)|| "+
					      " '01'+19110000,'YYYYMMDD') ,to_date(substr(a.buyDate,1,5)|| " +
					      " '01'+19110000,'YYYYMMDD') ) ,12 ) useMonth, " +
					      " a.deprMethod,b.scrapValue,b.deprAmount,b.apportionYear, " +
					      " b.monthDepr,a.useEndYM,a.apportionEndYM,b.accumDeprYM, " +
					      " b.accumDepr,a.permitReduceDate,b.reduceDate "+
					    " from UNTMP_Movable a,UNTMP_MovableDetail b " +
					   " where a.enterOrg='"+Common.get(rs.getString("enterOrg")) + "' "+
					    " and a.ownership='"+Common.get(rs.getString("ownership")) + "' "+
					    " and a.propertyKind='"+Common.get(rs.getString("propertyKind")) + "' "+
					    " and nvl(a.valuable,'N') =nvl('"+Common.get(rs.getString("valuable"))+"','N') "+
					    " and a.oldPropertyNo ='"+Common.get(rs.getString("oldPropertyNo")) + "'" +
					    " and b.oldSerialNo ='"+Common.get(rs.getString("oldSerialNo"))+"'" +
					    " and a.enterOrg=b.enterOrg " +
					    " and a.ownership=b.ownership " +
					    " and a.propertyNo=b.propertyNo " +
					    " and a.lotNo =b.lotNo ";		
					
					if (Common.get(rs.getString("propertyKind")).equals("03")) {
						sql += " and nvl(a.fundType,'99')=nvl('" + Common.get(rs.getString("fundType")) + "','99') ";
					} else {
						sql += " and nvl(a.fundType,'99')=nvl('" + "" + "','99') ";
					}
					
					rc = db.querySQL(sql);
					if (rc.next()) {
						sbSQL.append(" insert into UNTMP_ReduceDetail(" ).append(
						" enterOrg,").append(
						" ownership,").append(
						" caseNo,").append(
						" propertyNo,").append(
						" lotNo,").append(
						" serialNo,").append(	
						" otherPropertyUnit,").append(						
						" otherMaterial,").append(
						" otherLimitYear,").append(
						" propertyName1,").append(						
						" enterDate,").append(
						" buyDate,").append(
						" cause,").append(
						" cause1,").append(
						" reduceDate,").append(
						" newEnterOrg,").append(
						" transferDate,").append(
						" verify,").append(
						" propertyKind,").append(
						" fundType,").append(
						" valuable,").append(
						" bookNotes,").append(
						" accountingTitle,").append(
						" oldBookAmount,").append(
						" oldBookUnit,").append(
						" oldBookValue,").append(
						" adjustBookAmount,").append(
						" adjustBookValue,").append(
						" newBookAmount,").append(
						" newBookValue,").append(
						" articleName,").append(
						" nameplate,").append(
						" specification,").append(
						" sourceDate,").append(
						" licensePlate,").append(
						" moveDate,").append(
						" keepUnit,").append(
						" keeper,").append(
						" useUnit,").append(
						" userNo,").append(
						" place,").append(
						" returnPlace,").append(
						
						" useYear,").append(
						" useMonth,").append(
						
						" cause2,").append(
						" scrapValue2,").append(
						" dealSuggestion,").append(
						
						" deprMethod,").append(						
						" scrapValue,").append(
												
						" deprAmount,").append(						
						" apportionYear,").append(
						" monthDepr,").append(											
						" useEndYM,").append(
						" apportionEndYM,").append(
						" accumDeprYM,").append(
						" accumDepr,").append(
						" permitReduceDate,").append(
						" accumdepr1,").append(
						" scrapValue1,").append(
						" submitCityGov,").append(						
						
						" dealCaseNo,").append(
						" dealDate,").append(
						" reduceDeal,").append(
						" realizeValue,").append(
						" shiftOrg,").append(
						" notes,").append(
						" oldPropertyNo,").append(
						" oldSerialNo,").append(
						" editID,").append(
						" editDate,").append(
						" editTime").append( 
					" )Values(" ).append(
						Common.sqlChar(Common.get(rs.getString("enterOrg")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
						Common.sqlChar(Common.get(caseNo))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("lotNo")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(	
						Common.sqlChar(Common.get(rc.getString("otherPropertyUnit")))).append(",").append(						
						Common.sqlChar(Common.get(rc.getString("otherMaterial")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("otherLimitYear")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("propertyName1")))).append(",").append(						
						Common.sqlChar(Common.get(rc.getString("enterDate")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("buyDate")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("newEnterOrg")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("transferDate")))).append(",").append(
						Common.sqlChar("Y")).append(",").append(
						Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("valuable")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("accountingTitle")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("bookAmount")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("bookUnit")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("originalAmount")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("originalBV")))).append(",").append(
						(rc.getDouble("bookAmount")-rs.getDouble("originalAmount"))).append(",").append(
						(rc.getDouble("bookValue")-rs.getDouble("originalBV"))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("articleName")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("nameplate")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("specification")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("sourceDate")))).append(",").append(					
						Common.sqlChar(Common.get(rc.getString("licensePlate")))).append(",").append(					
						Common.sqlChar(Common.get(rc.getString("moveDate")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("keepUnit")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("keeper")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("useUnit")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("userNo")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("place")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("returnPlace")))).append(",").append(
						
						Common.sqlChar(Common.get(rc.getString("useYear")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("useMonth")))).append(",").append(
						
						Common.sqlChar(Common.get(rs.getString("cause2")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("scrapValue2")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("dealSuggestion")))).append(",").append(
						
						Common.sqlChar(Common.get(rc.getString("deprMethod")))).append(",").append(						
						Common.sqlChar(Common.get(rc.getString("scrapValue")))).append(",").append(
												
						Common.sqlChar(Common.get(rc.getString("deprAmount")))).append(",").append(						
						Common.sqlChar(Common.get(rc.getString("apportionYear")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("monthDepr")))).append(",").append(											
						Common.sqlChar(Common.get(rc.getString("useEndYM")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("apportionEndYM")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("accumDeprYM")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("accumDepr")))).append(",").append(
						Common.sqlChar(Common.get(rc.getString("permitReduceDate")))).append(",");
							
						switch (rc.getInt("deprMethod")) {
						case 1: sbSQL.append("'0','0',"); break;
						case 5: sbSQL.append("'").append(rs.getString("originalBV")).append("','0',"); break;
						case 2: case 3: case 4:
							String reduceDate = "00000", deprYM="00000";
							if (Common.get(rs.getString("enterDate")).length()>4) {
								reduceDate = Common.get(rs.getString("enterDate")).substring(0,5);
							}
							if (Function.getNumeric(reduceDate)>rc.getDouble("apportionEndYM")) {
								sbSQL.append("'").append(rc.getString("deprAmount")).append("','',"); break;
							} else {
								reduceDate = reduceDate+"01";
								deprYM = deprYM+"01";
								if (rc.getString("accumDeprYM").length()>4) {
									deprYM = rc.getString("accumDeprYM").substring(0,5)+"01";
								}
								DateTime rDateS =  new DateTime(Integer.parseInt(reduceDate.substring(0,3))+1911,Integer.parseInt(reduceDate.substring(3,5)),1, 0, 0, 0, 0);
								DateTime rDateE =  new DateTime(Integer.parseInt(deprYM.substring(0,3))+1911,Integer.parseInt(deprYM.substring(3,5)),1, 0, 0, 0, 0);
								Period p = new Period(rDateE.minusMonths(1), rDateS, PeriodType.yearMonthDay());							
								int diffYear = p.getYears();
								int diffMonth = p.getMonths();							
								diffMonth = diffMonth + (diffYear * 12);
								double accumDepr1 = Math.round(diffMonth*rc.getDouble("monthDepr"));
								sbSQL.append("'").append(accumDepr1).append("',");
								sbSQL.append("'").append(Math.round(rs.getDouble("originalBV")-accumDepr1)).append("',");							
															
							}
							break;
						default:
							sbSQL.append("'0','0',");
							break;
							
						}
						if ((rs.getDouble("originalBV")>1500000)||(rs.getDouble("enterDate")<rc.getDouble("permitReduceDate"))) {
							sbSQL.append("'Y',");						
						}else{
							sbSQL.append("'N',");
						}
						sbSQL.append("'','','','','',");
						sbSQL.append(Common.sqlChar(Common.get(rs.getString("notes")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
						Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
						Common.sqlChar(getEditID()) ).append( "," ).append(
						Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
						Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
						
						sbSQL.append(" update UNTMP_Movable set bookAmount=(bookAmount-").append(rs.getDouble("originalAmount")).append("),");
						sbSQL.append(" bookValue=(bookValue-").append(rs.getDouble("originalBV")).append(") where enterOrg='").append(enterOrg).append("' and ");
						sbSQL.append(" ownership='").append(rs.getString("ownership")).append("' and propertyNo='").append(rc.getString("propertyNo")).append("' and ");
						sbSQL.append(" lotNo='").append(rc.getString("lotNo")).append("':;:");
						
						sbSQL.append(" update UNTMP_MovableDetail set bookAmount=(bookAmount-").append(rs.getDouble("originalAmount")).append("),");
						sbSQL.append(" bookValue=(bookValue-").append(rs.getDouble("originalBV")).append(") where enterOrg='").append(enterOrg).append("' and ");
						sbSQL.append(" ownership='").append(rs.getString("ownership")).append("' and propertyNo='").append(rc.getString("propertyNo")).append("' and ");
						sbSQL.append(" lotNo='").append(rc.getString("lotNo")).append("' and serialNo='").append(rc.getString("serialNo")).append("':;:");
													
						db.exeSQL(sbSQL.toString().split(":;:"));
						
						sbSQL.setLength(0);
						
						sql = " select nvl(bookAmount,0) as bookAmount from untmp_movable where enterOrg='" + enterOrg + "'" +
							" and ownership='" + rs.getString("ownership") + "' " +
							" and propertyNo='"+ rc.getString("propertyNo") + "' " +
							" and lotNo='" + rc.getString("lotNo") +"'";
						ResultSet rsChk = db.querySQL(sql);
						if (rsChk.next()) {
							if (rsChk.getDouble(1)==0) {
								sql = " update untmp_movable set dataState='2' where enterOrg='" + enterOrg + "'" +
								" and ownership='" + rs.getString("ownership") + "' " +
								" and propertyNo='"+ rc.getString("propertyNo") + "' " +
								" and lotNo='" + rc.getString("lotNo") +"'";
								db.exeSQL(sql);
							} else if (rsChk.getDouble(1)<0){								
								sFlag = true;
								setErrorMsg("帳面數量與減少數量相減必須 ≧ 0！");
								db.doRollback();
								
								util.Database db1 = new util.Database();						
								try {
									db1.exeSQL("update untup_movable set isErr='Y', errorColumn=errorColumn||'帳面數量與減少數量相減必須 ≧ 0！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
									db1.doCommit();
								} catch (Exception e) {
									sFlag = true;
									setErrorMsg("帳面數量與減少數量相減必須 ≧ 0！");							
									e.printStackTrace();							
								} finally {
									db1.closeAll();
								}
								break;
							}
							
						}
						rsChk.getStatement().close();
						rsChk.close();
												
						sql = " select nvl(bookAmount,0) as bookAmount from untmp_movabledetail where enterOrg='" + enterOrg + "'" +
							" and ownership='" + rs.getString("ownership") + "' " +
							" and propertyNo='"+ rc.getString("propertyNo") + "' " +
							" and lotNo='" + rc.getString("lotNo") +"' and serialNo='" + rc.getString("serialNo") +"'";
						rsChk = db.querySQL(sql);
						if (rsChk.next()) {
							if (rsChk.getInt(1)==0) {
								sql = " update untmp_movabledetail set dataState='2' where enterOrg='" + enterOrg + "'" +
								" and ownership='" + rs.getString("ownership") + "' " +
								" and propertyNo='"+ rc.getString("propertyNo") + "' " +
								" and lotNo='" + rc.getString("lotNo") +"' and serialNo='" + rc.getString("serialNo")+"'";
								db.exeSQL(sql);								
							} else if (rsChk.getInt(1)<0) {
								util.Database db1 = new util.Database();						
								try {
									sFlag = true;
									setErrorMsg("帳面數量與減少數量相減必須 ≧ 0！");
									db.doRollback();
									
									db1.exeSQL("update untup_movable set isErr='Y', errorColumn=errorColumn||'帳面數量與減少數量相減必須 ≧ 0！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
									db1.doCommit();
								} catch (Exception e) {
									sFlag = true;
									setErrorMsg("帳面數量與減少數量相減必須 ≧ 0！");							
									e.printStackTrace();							
								} finally {
									db1.closeAll();
								}
								break;								
							}

														
						}
						rsChk.getStatement().close();
						rsChk.close();						
						
						
					} else {
						sFlag = true;
						setErrorMsg("查無該筆動產資料");
						util.Database db1 = new util.Database();						
						try {
							db1.exeSQL("update untup_movable set isErr='Y', errorColumn=errorColumn||'查無該筆動產資料！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
							db1.doCommit();
						} catch (Exception e) {
							sFlag = true;
							setErrorMsg("查無增加資料！");
							e.printStackTrace();							
						} finally {
							db1.closeAll();
						}
						break;				
					}					
					break;
				}
			} else {
				break;
			}
		}
		rs.getStatement().close();
		rs.close();		
		
		if (sFlag) {
			db.doRollback();
			setErrorMsg("資料轉入失敗！");
		} else {
			db.exeSQL(" delete from untup_movable where enterOrg='"+enterOrg+"'");		
			db.doCommit();
			setErrorMsg("資料轉入完成！");
		}
	} catch (Exception e) {
		e.printStackTrace();
		setErrorMsg("資料轉入失敗！");
		db.doRollback();
	} finally {
		db.closeAll();
	}
}	

	
}
