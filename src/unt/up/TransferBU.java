package unt.up;

import java.sql.ResultSet;
import java.util.Map;
import java.util.HashMap;


import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import util.Common;
import util.Database;
import util.Datetime;
import util.Function;
import util.Validate;

public class TransferBU extends UNTUP000Q {
	
public void doBuildingUploadProcess(String enterOrg, java.util.Iterator it) {
	if (it!=null) {			
		Database db = new Database();
		try {
			db.setAutoCommit(false);			
			db.exeSQL(" delete from untup_building where enterOrg="+Common.sqlChar(enterOrg));
			String[] rowArray = new String[53];
			int rowid = 2;
			int intSuccessRecord = 0;
			int intErrorRecord = 0;
			String preSQL = " insert into untup_building(enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,otherLimitYear,propertyName1,signNo,doorPlate4,buildStyle,cause,cause1,taxCredit,CArea,SArea,holdNume,holdDeno,holdArea,originalNote,accountingTitle,bookValue,fundsSource,ownershipDate,ownershipCause,proofDoc1,buildDate,floor1,floor2,stuff,ownershipNote,sourceKind,sourceDate,sourceDoc,manageOrg,deprMethod,useState,newEnterOrg,transferDate,unusableCause,demolishDate,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,notes,editID,editDate,editTime,errorColumn,isErr)Values(";
			//StringBuffer sbSQL = new StringBuffer(" delete from untup_building where enterOrg=").append(Common.sqlChar(enterOrg)).append(":;:");
			Map h = new HashMap();			
			while(it.hasNext()) {				
				rowArray=(String[])it.next();

				//Excel Meet Empty Cell, leave it..				
				if (Common.get(rowArray[0]).equals("") && Common.get(rowArray[1]).equals("")) break;
				
				boolean hashErr = false;
				StringBuffer sbSQL = new StringBuffer(800);				
				sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
				StringBuffer sbErrMsg = new StringBuffer();					
				
				//檢查資料有無重覆。
				if (Common.get(rowArray[5]).equals("1")) {
					if (checkRecordExists("UNTBU_Building",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))) {
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
						if (checkRecordExists("UNTBU_Building",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))==false) {
				        	hashErr = true;
							sbErrMsg.append("無增加資料。\n");
						}
			        }
			        if (Common.get(rowArray[5]).equals("4")) {
						if (checkRecordExists("UNTBU_ReduceDetail",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))) {
				        	hashErr = true;
							sbErrMsg.append("資料已存在於減損資料表中，故無法再作減損的動作。\n");
						}
						r = (Integer)h.get("4"+enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[3])+Common.get(rowArray[6])+Common.get(rowArray[7]));
				        if(r==null){
				        	r = new Integer(rowid);
				        	h.put("4"+enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[3])+Common.get(rowArray[6])+Common.get(rowArray[7]), r);
				        } else {
				        	hashErr = true;
				        	sbErrMsg.append("己和列號").append(r.intValue()).append("的暫存檔減損資料重覆。\n");
				        }			        	
			        }			        
				}
				
				for (int i=0; i<53; i++) {
					switch (i) {
					case 2:
						if (Common.get(rowArray[1]).equals("03")) {
							if (!"".equals(Common.get(rowArray[i]))) {
								if ("".equals(getCodeName("FUD",Common.get(rowArray[i])))) sbErrMsg.append("查無此 ").append(rowArray[i]).append(" 基金財產代碼。\n");								
							}else sbErrMsg.append("財產性質為事業用時基金財產代碼不能為空值。\n");							
						}else{
							if (!"".equals(Common.get(rowArray[i]))) {
								sbErrMsg.append("財產性質非為事業用時基金財產代碼需為空值。\n");	
							}
							//rowArray[i] = "";
						}	
						break;
					
					case 5:
						sbErrMsg.append(checkBuildingField(i, Common.get(rowArray[i])));
						
						if ("4".equals(Common.get(rowArray[i]))) {
							if ("01".equals(Common.get(rowArray[13]))||"07".equals(Common.get(rowArray[13]))){
								if ("".equals(Common.get(rowArray[39]))) {
									sbErrMsg.append("接管機關不能為空值。\n");
								} else {
									if ("".equals(getOrganSName(Common.get(rowArray[39])))) {
										sbErrMsg.append("查無此接管機關代碼。\n");
									}
								}
								if ("".equals(Common.get(rowArray[40]))) {
									sbErrMsg.append("交接日期不能為空值。\n");
								} else {
									if (!Validate.checkDate(Common.get(rowArray[40]))) {
										sbErrMsg.append("交接日期必須為(YYYMMDD)格式。\n");
									}
								}
							}
						} else {
							if (!"".equals(Common.get(rowArray[39])) || !"".equals(Common.get(rowArray[40]))) {
								sbErrMsg.append("接管機關代碼及交接日期不能有資料。\n");	
							}
						}
						break;
						
					case 13:						
						if (Common.get(rowArray[5]).equals("1")) {
							if (getCodeName("CAB",rowArray[i]).equals("")) {
								sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");								
							}
						}else if(Common.get(rowArray[5]).equals("4")) {
							if (getCodeName("CAC",rowArray[i]).equals("")) sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");							
						}else if (Common.get(rowArray[5]).equals("2")||Common.get(rowArray[5]).equals("3")){
							if (rowArray[i].equals("1")||rowArray[i].equals("2")||rowArray[i].equals("3")) {}
							else sbErrMsg.append("登帳原因代碼若為增減值作業只能是1,2或3。\n");							
						}	
						break;
						
					case 14:
						if (Common.get(rowArray[5]).equals("1")||Common.get(rowArray[5]).equals("4")) {
							if (!"99".equals(Common.get(rowArray[13]))) {
								//if (!"".equals(rowArray[i])) sbErrMsg.append("登帳原因非為其他時，其他說明不能有資料。\n");
								rowArray[i]="";
							} else {
								if ("".equals(rowArray[i])) {
									sbErrMsg.append("登帳原因若為其他時，其他說明必須要有資料。\n");
								} else {
									sbErrMsg.append(checkBuildingField(i, Common.get(rowArray[i])));
								}
							}
						} else if (Common.get(rowArray[5]).equals("2")||Common.get(rowArray[5]).equals("3")) {
							if (!"3".equals(Common.get(rowArray[11]))) {
								//if (!"".equals(rowArray[i])) sbErrMsg.append("登帳原因若為增值時，其他說明不能有資料。\n");
								rowArray[i]="";
							} else {
								if ("".equals(rowArray[i])) {
									sbErrMsg.append("登帳原因代碼若為其他時，其他說明必須要有資料。\n");
								} else {
									sbErrMsg.append(checkBuildingField(i, Common.get(rowArray[i])));
								}
							}							
						}						
						break;
						
					default:
						sbErrMsg.append(checkBuildingField(i, Common.get(rowArray[i])));						
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
		
	public String checkBuildingField(int i, String s) {
		String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","珍貴財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","使用年限","財產別名","建物標示代碼","門牌","建築式樣代碼","登帳原因代碼","登帳原因－其他說明","抵繳遺產稅代碼","主建物面積(㎡)","附屬建物面積(㎡)","權利範圍－分子","權利範圍－分母","權利範圍面積(㎡)","帳務摘要","會計科目","價值","經費來源代碼","所有權登記日期","所有權登記原因代碼","權狀字號","建築日期","構造－地上層數代碼","構造－地下層數代碼","構造－造代碼","謄本所有權部之其他登記事項","財產來源－種類代碼","財產來源－取得日期","財產來源－取得文號","管理機關代碼","折舊方法代碼","使用情形代碼","接管機關代碼","交接日期","報廢拆除原因","拆除日期","填單日期","填造單位","單據編號－字","單據編號－號","財產管理單位編號","傳票號數","核准機關代碼","核准日期","核准文號","備註"};
		try {
			switch (i) {
				case 0:
					if (s.equals("1")||s.equals("2")) {} 
					else return "權屬代碼只能是1或2。\n";					
					break;
				case 1:
					if (!checkPropertyKind(s)) return "財產性質必須是01,02,03或04。\n";		
					break;

				case 3: case 15:
					if (!Validate.checkYN(s)) {					
						return arrFieldName[i]+"，請輸入Y或N。";								
					}
					break;
					
				case 4:	case 25: case 28: case 34: case 40: case 42: case 43: case 50:					
					if (!"".equals(Validate.checkDate(s, arrFieldName[i]))) {
						return Validate.checkDate(s, arrFieldName[i])+"\n";
					}
					if (i==4 || i==28) {
						if ( s.equals("")) return arrFieldName[i]+"不能空白。\n";
						else if(i==4 && (Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(s.substring(0,5)))){
							return "入帳年月必須大於月結年月！\n";
						}
					}
					break;
					
				case 5:							
					if ((!"".equals(s) && Validate.checkInt(s)) && (Integer.parseInt(s)>0 || Integer.parseInt(s)<5)) {}
					else return "增減別代碼只能是1,2,3,4。\n";					
					break;
					
				case 6:
					if (!"".equals(s) && s.length()>1 && s.substring(0,1).equals("2")) {
						if ("".equals(getPropertyName(enterOrg,s))) {						
							return "查無此 "+s+" 原財產編號。\n";
						}						
					} else return "原財產編號不能為空值且開頭字元必須為2。\n";
					break;
				
				case 7:
					if (s.equals("") || s.length()>7) return "原財產分號錯誤(空值或長度超過7碼)。\n";					
					break;
					
				case 8:
					if (!Validate.checkInt(s) || s.length()>3) return "使用年限需為正整數，長度不能超過3位數。\n";
					break;
					
				case 9: case 14: case 21: case 27: case 35: case 51:
					if (s.length()>20) return arrFieldName[i]+"已超過20個字。\n";
					break;
				
				case 10:
					if ("".equals(s)) {
						return "";
					} else {
						if ((s.length()!=15) || (Validate.checkInt(s.substring(7))==false)){
							return "此"+s+"建物標示代碼格式不符或為空值。\n";
						} else if ("".equals(getSignName(s))) {
							return "查無此"+s+"建物標示代碼的縣市區里資料。\n";
						} else if (checkSignNameExists("BU", enterOrg, ownership, s)) {							
							return "建物標示資料重覆。\n";						
						}
					}
					break;
					
				case 11:
					if (s.length()>50) return "門牌已超過50個字。\n";					
					break;							
					
				case 12:
					if (!"".equals(s)) {
						if (getCodeName("BST",s).equals("")) return arrFieldName[i]+"　查無該代碼資料。\n";
					}
					break;

				case 16: case 17: case 20:
					if (!"".equals(s)) {
						if (!Validate.checkFloat(s, 7, 2)) {							
							return arrFieldName[i]+"整數部分不可大於7位，小數部份不可大於2位。";								
						} else if (Double.parseDouble(s)<0){							
							return arrFieldName[i]+"必須≧0。\n";									
						}
					} else return arrFieldName[i]+"必須要有資料。\n";
					break;
				
				case 18: case 19: case 23:
					if (!"".equals(s)) {
						if (!Validate.checkInt(s)) return arrFieldName[i]+"　請輸入正整數。\n";
						else if (Integer.parseInt(s)<0){
							return arrFieldName[i]+"　必須≧0。\n";
						} else {
							if (i==23 && s.length()>15) return arrFieldName[i]+"　不能大於15位數。\n";
							else if (s.length()>10) return arrFieldName[i]+"　不能大於10位數。\n";
						}
					} else {
						return arrFieldName[i]+"不能為空白。\n";
					}
					break;
	
				case 22:
					if (s.length()>20) return arrFieldName[i]+"已超過 20個字元。\n";
					break;
	
				//case 22: case 45: case 46: case 47:
				case 45: case 46: case 47:
					if (s.length()>10) return arrFieldName[i]+"已超過 10個字元。\n";
					else if (i==45 || i==46){
						if (s.equals("")) return arrFieldName[i] +"不能空白。\n";
					} else if (i==47 && Validate.checkAlphaInt(s)==false) return arrFieldName[i] +"請輸入半形數字或英文字母。\n";
					break;
					
				case 24: case 26:case 29:case 30: case 31: case 33: case 37: case 38: case 49:					
					if (!"".equals(s)) {
						if (getCodeName(getCodeKind(i),s).equals("")) return arrFieldName[i]+"　查無該代碼資料。\n";
					}else if (i==37) {
						return arrFieldName[i]+"　不能空白。\n";
					}					
					break;
					
				case 32: case 52:
					if (s.length()>250) return arrFieldName[i]+"已超過 250 個字元。\n";					
					break;					
							
				case 36: case 39:					
					if (!"".equals(s)) {
						if (getOrganSName(s).equals("")) {
							return "查無此 "+s+arrFieldName[i];									
						}
					}
					break;					

				case 41:
					if (s.length()>50) return arrFieldName[i]+"已超過 50 個字元。\n";					
					break;
													
				case 44: case 48:
					if (s.length()>15) return arrFieldName[i]+"已超過 15 個字元。\n";
					else if (i==48 && Validate.checkAlphaInt(s)==false) return arrFieldName[i]+"請輸入半形數字或英文字。\n";
					break;					
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "錯誤\n";			
		}			
	}
	
	private String getCodeKind(int s) {
		String[] arrCodeKind = new String[] {"FSO","OCB","FLA","FLB","STU","SKB","DEP","UST","APO"};
		int[] arrID = new int[] {24,26,29,30,31,33,37,38,49};
		for (int i=0; i<arrID.length; i++) {
			if (arrID[i]==s) return arrCodeKind[i];
		}
		return "";
	}	
	
	public void doBuildingTransfer(String enterOrg) throws Exception {
		Database db = new Database();
		try {
			db.setAutoCommit(false);
			String sql = "";
			ResultSet rc;
			boolean sFlag = false;
			ResultSet rs = db.querySQL("select enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,otherLimitYear,propertyName1,signNo,doorPlate4,buildStyle,cause,cause1,taxCredit,CArea,SArea,(CArea+SArea) as area, holdNume,holdDeno,holdArea,originalNote,accountingTitle,bookValue,fundsSource,ownershipDate,ownershipCause,proofDoc1,buildDate,floor1,floor2,stuff,ownershipNote,sourceKind,sourceDate,sourceDoc,manageOrg,deprMethod,useState,newEnterOrg,transferDate,unusableCause,demolishDate,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,notes,editID,editDate,editTime,errorColumn,isErr from untup_building where enterOrg="+Common.sqlChar(enterOrg)+" order by enterOrg,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo");
			while (rs.next()) {
				StringBuffer sbSQL = new StringBuffer(500);			
				String caseNo = Common.get(rs.getString("enterDate")).substring(0,5)+"00001";					
				if (sFlag==false) {
					switch (rs.getInt("adjustType")) {
					case 1: //增加					
						String serialNo="0000001";
						String nonProof = "N";
						
						if (Common.get(rs.getString("proofDoc")).length()>0) nonProof = "Y";
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTBU_AddProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
						}
						rc.getStatement().close();
						rc.close();
						
						//取得SerialNo
						sql="select substr(nvl(max(serialNo),0)+10000001,2) as serialNo from UNTBU_Building " +
							" where enterOrg = " + Common.sqlChar(enterOrg) + 
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) + 
							" and propertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo")) + 
							"";
						rc = db.querySQL(sql);
						if (rc.next()){
						    serialNo = rc.getString("serialNo");
						}
						rc.getStatement().close();
						rc.close();					
											
						if((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(Common.get(rs.getString("enterDate")).substring(0,5)))){
							sFlag = true;
							setErrorMsg("入帳年月必須大於月結年月！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_building set isErr='Y', errorColumn='入帳年月必須大於月結年月！(建議：請不要使用上傳作業上傳後，又使用本系統畫面登打資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
						sql=" select count(*) as checkResult from UNTBU_Building where 1=1 " + 
							" and enterOrg = " + Common.sqlChar(enterOrg) + 
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) + 
							" and oldPropertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo")) + 
							" and oldSerialNo = " + Common.sqlChar(rs.getString("oldserialNo"));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0) {
							sFlag = true;
							setErrorMsg("資料己重複！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_building set isErr='Y', errorColumn='資料己重複！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
								db1.doCommit();
							} catch (Exception e) {
								sFlag = true;
								setErrorMsg("資料己重複！");
								db1.doRollback();
								e.printStackTrace();							
							} finally {							
								db1.closeAll();
							}
							break;						
						}
						rc.getStatement().close();
						rc.close();					
						
						if (!"".equals(rs.getString("signNo"))) {
							sql = " select count(*) as checkResult from UNTBU_Building where enterOrg = " + Common.sqlChar(enterOrg) + 
								" and ownership = " + Common.sqlChar(rs.getString("ownership")) + 
								" and signNo = " + Common.sqlChar(Common.get(rs.getString("signNo"))) + " and dataState='1' ";
							rc = db.querySQL(sql);
							if (rc.next() && rc.getInt(1)>0) {
								sFlag = true;
								setErrorMsg("建物標示己重複，請重新輸入！");
								util.Database db1 = new util.Database();						
								try {
									db1.exeSQL("update untup_building set isErr='Y', errorColumn=errorColumn||'建物標示己重複，請重新輸入！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
									db1.doCommit();
								} catch (Exception e) {
									sFlag = true;
									setErrorMsg("建物標示己重複，請重新輸入！");
									db1.doRollback();
									e.printStackTrace();							
								} finally {
									db1.closeAll();
								}
								break;						
							}
							rc.getStatement().close();
							rc.close();
						}
						
						//取得propertyUnit, material, limitYear, 000000000A
						String propertyUnit = "", material = "", limitYear = "";
						sql="select propertyUnit, material, limitYear from SYSPK_PropertyCode " +
							" where enterOrg in ('000000000A'," + Common.sqlChar(enterOrg) + ") and propertyType='1' " +
							" and substr(propertyNo,1,1)='2' and propertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo"));
						rc = db.querySQL(sql);
						if (rc.next()){
						    propertyUnit = Common.get(rc.getString(1));
						    material = Common.get(rc.getString(2));
						    limitYear = Common.get(rc.getString(3));
						} else {
							sFlag = true;
							setErrorMsg("查無此 "+rs.getString("oldPropertyNo")+" 財產編號！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_building set isErr='Y', errorColumn='查無此" +rs.getString("oldPropertyNo")+ " 財產編號！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
								db1.doCommit();
							} catch (Exception e1) {
								sFlag = true;
								setErrorMsg("查無此 " + rs.getString("oldPropertyNo") + " 財產編號！");								
								e1.printStackTrace();							
							} finally {
								db1.closeAll();
							}						
							break;								
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

						String sDate = Common.get(rs.getString("buildDate"));
						DateTime buildDate =  new DateTime(Integer.parseInt(sDate.substring(0,3))+1911,Integer.parseInt(sDate.substring(3,5)),Integer.parseInt(sDate.substring(5)), 0, 0, 0, 0);
						String useEndYM = Datetime.getYYYMMDD(buildDate.plusYears(Integer.parseInt(limitYear)).minusMonths(1).toDate()).substring(0,5);				
						String permitReduceDate = Datetime.getYYYMMDD(buildDate.plusYears(Integer.parseInt(limitYear)).toDate());
						
						String scrapValue = "", deprAmount = "", apportionYear = "", monthDepr = "";
						String apportionEndYM = "", accumDeprYM = "", accumDepr = "";						
						try {
							String[] deprResult = getDeprResult(rs.getInt("deprMethod"), sDate, limitYear, rs.getDouble("bookValue"));
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
								db1.exeSQL("update untup_building set isErr='Y', errorColumn='拆舊方法必須是01,02,03,04或05！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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

						sbSQL.append(" insert into UNTBU_AddProof(" ).append(
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
							Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;			
						
						sbSQL.append(" insert into UNTBU_Building(enterOrg,ownership,caseNo,propertyNo,serialNo,otherLimitYear,propertyName1,signNo,").append(
							"doorPlate1,doorPlate2,doorPlate3,doorPlate4,buildStyle,cause,cause1,enterDate,dataState,").append(
							"reduceDate,reduceCause,reduceCause1,verify,closing,propertyKind,fundType,valuable,").append(
							"taxCredit,originalCArea,originalSArea,originalArea,originalHoldNume,originalHoldDeno,").append(
							"originalHoldArea,CArea,SArea,area,holdNume,holdDeno,holdArea,originalBV,originalNote,").append(
							"accountingTitle,bookValue,fundsSource,ownershipDate,ownershipCause,nonProof,proofDoc,").append(
							"proofVerify,buildDate,floor1,floor2,stuff,ownershipNote,sourceKind,sourceDate,sourceDoc,").append(
							"manageOrg,damageDate,damageExpire,damageMark,deprMethod,isAccumDepr,scrapValue,deprAmount,").append(
							"apportionYear,monthDepr,useEndYM,apportionEndYM,accumDeprYM,accumDepr,permitReduceDate,useState,").append(
							"proceedDateS,proceedDateE,proceedType,appraiseDate,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime)values(");
						
						sbSQL.append(Common.sqlChar(enterOrg)).append(",").append(
								Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
								Common.sqlChar(caseNo)).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
								Common.sqlChar(serialNo)).append(",");						
						
						if (limitYearFlag) sbSQL.append(Common.sqlChar(Common.get(rs.getString("otherLimitYear")))).append( "," );
						else sbSQL.append("''," );

						sbSQL.append(Common.sqlChar(Common.get(rs.getString("propertyName1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("signNo")))).append(",'','','',").append(
							Common.sqlChar(Common.get(rs.getString("doorPlate4")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("buildStyle")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
							"'1','','','','Y','N',").append(
							Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("valuable")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("taxCredit")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("CArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("SArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("area")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdNume")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdDeno")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("CArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("SArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("area")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdNume")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdDeno")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("accountingTitle")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("fundsSource")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("ownershipDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("ownershipCause")))).append(",").append(
							Common.sqlChar(Common.get(nonProof))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("proofDoc1")))).append(",").append(
							Common.sqlChar("N")).append(",").append(
							Common.sqlChar(Common.get(rs.getString("buildDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("floor1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("floor2")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("stuff")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("ownershipNote")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceDoc")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("manageOrg")))).append(",").append(
							"'','','',").append(
							Common.sqlChar(Common.get(rs.getString("deprMethod")))).append(",").append(
							Common.sqlChar("N")).append(",").append(
							Common.sqlChar(scrapValue)).append(",").append(
							Common.sqlChar(deprAmount)).append(",").append(
							Common.sqlChar(apportionYear)).append(",").append(
							Common.sqlChar(monthDepr)).append(",").append(
							Common.sqlChar(useEndYM)).append(",").append(
							Common.sqlChar(apportionEndYM)).append(",").append(
							Common.sqlChar(accumDeprYM)).append(",").append(
							Common.sqlChar(accumDepr)).append(",").append(
							Common.sqlChar(permitReduceDate)).append(",").append(
							Common.sqlChar(Common.get(rs.getString("useState")))).append(",").append(
							"'','','','',").append(
							Common.sqlChar(Common.get(rs.getString("notes")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
							Common.sqlChar(getEditID())).append(",").append(
							Common.sqlChar(Datetime.getYYYMMDD())).append(",").append(
							Common.sqlChar(Datetime.getHHMMSS())).append("):;:");
						
						db.exeSQL(sbSQL.toString().split(":;:"));
						break;
						
					case 2: case 3: //建物增減單 UNTBU_AdjustProof, UNTBU_AdjustDetail & update UNTBU_Building
						String op = "+";
						if (rs.getInt("adjustType")==2) op = "+";
						else if (rs.getInt("adjustType")==3) op = "-";			
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTBU_AdjustProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = " select propertyNo,serialNo,propertyName1,signNo,originalBV,sourceDate,CArea,SArea,area,holdNume,holdDeno,holdArea,bookValue from UNTBU_Building " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and nvl(valuable,'N') = nvl('" + Common.get(rs.getString("valuable")) + "','N') " +
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));					
						rc = db.querySQL(sql);
						if (rc.next()) {
							sbSQL.append(" insert into UNTBU_AdjustProof (" ).append(
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
								Common.sqlChar("Y") ).append( "," ).append(
								Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
								
							sbSQL.append(" insert into UNTBU_AdjustDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,propertyName1,signNo,cause,cause1,adjustDate,verify,propertyKind,fundType,valuable,taxCredit,originalBV,sourceDate,oldCArea,oldSArea,oldArea,oldHoldNume,oldHoldDeno,oldHoldArea,oldBookValue,adjustType,adjustCArea,adjustSArea,adjustArea,adjustHoldArea,adjustBookValue,newCArea,newSArea,newArea,newHoldNume,newHoldDeno,newHoldArea,newBookValue,bookNotes,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append( 
							" )Values(" ).append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
								Common.sqlChar(caseNo)).append(",").append(
								Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("propertyName1")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("signNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
								"'Y',").append(
								Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("valuable")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("taxCredit")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("originalBV")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("sourceDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("CArea")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("SArea")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("Area")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("holdNume")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("holdDeno")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("holdArea")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",").append(
								" decode('").append(rs.getString("adjustType")).append("','2','1','2'), ").append(
								Common.sqlChar(Common.get(rs.getString("cArea")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("sArea")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("area")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("holdArea")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",");
														
							if (Common.get(rs.getString("cause")).equals("1")) {
								sbSQL.append(Common.sqlChar(Common.get(rc.getString("CArea")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("SArea")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("Area")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("holdNume")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("holdDeno")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("holdArea")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",");
							} else if (Common.get(rs.getString("cause")).equals("2")||Common.get(rs.getString("cause")).equals("3")) {
								sbSQL.append("(").append(rc.getString("cArea")).append(op).append(rs.getString("cArea")).append( "),(" ).append(
										rc.getString("sArea")).append(op).append(rs.getString("sArea")).append( "),(" ).append(										
										rc.getString("area")).append(op).append((rs.getDouble("CArea")+rs.getDouble("SArea"))).append( "),(" ).append(												
										rc.getString("holdNume")).append(op).append(rs.getString("holdNume")).append( "),(" ).append(									
										rc.getString("holdDeno")).append(op).append(rs.getString("holdDeno")).append( "),(" ).append(
										rc.getString("holdArea")).append(op).append(rs.getString("holdArea")).append( "),(" ).append(			
										rc.getString("bookValue")).append(op).append(rs.getString("bookValue")).append( "),");												
							}

							sbSQL.append(Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("notes")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
							
							if (Common.get(rs.getString("cause")).equals("1")) {
								sbSQL.append(" update UNTBU_Building set bookValue=(").append(
									rc.getString("bookValue")).append(op).append(rs.getString("bookValue")).append( "), appraiseDate=" ).append(
									Common.sqlChar(Common.get(rs.getString("enterDate")))).append(											
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
							} else {								
								sbSQL.append(" update UNTBU_Building set cArea=(").append(
									rc.getString("CArea")).append(op).append(rs.getString("CArea")).append( "), SArea=(" ).append(
									rc.getString("sArea")).append(op).append(rs.getString("SArea")).append( "), area=(" ).append(											
									rc.getString("area")).append(op).append((rs.getDouble("cArea")+rs.getDouble("sArea"))).append( "), holdNume=(" ).append(
									rc.getString("holdNume")).append(op).append(rs.getString("holdNume")).append( "), holdDeno=(" ).append(									
									rc.getString("holdDeno")).append(op).append(rs.getString("holdDeno")).append( "), holdArea=(" ).append(
									rc.getString("holdArea")).append(op).append(rs.getString("holdArea")).append( "), bookValue=(" ).append(									
									rc.getString("bookValue")).append(op).append(rs.getString("bookValue")).append( ") " ).append(
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
							}
							db.exeSQL(sbSQL.toString().split(":;:"));
							
						} else {
							sFlag = true;
							setErrorMsg("查無增加資料！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_building set isErr='Y', errorColumn=errorColumn||'查無增加資料！(建議：在上傳的Excel檔案中依增加、增減值、減少等屬性排放資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
								db1.doCommit();
							} catch (Exception e) {
								sFlag = true;
								setErrorMsg("查無增加資料！");
								db1.doRollback();
								e.printStackTrace();							
							} finally {
								db1.closeAll();
							}
							break;				
						}
						rc.getStatement().close();
						rc.close();
						break;
					case 4:					
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTBU_ReduceProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							//caseNo = (Common.get(rs.getString("enterDate"))+Common.formatFrontZero(rc.getString("caseNo"),3));
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = "select propertyNo,serialNo,otherLimitYear,propertyName1,signNo,doorPlate1,doorPlate2, " +
							" doorPlate3,doorPlate4,accountingTitle,proofDoc,enterDate,buildDate,sourceDate,useState," +
							  " substr('" + rs.getString("enterDate") + "',1,3) - substr(enterDate,1,3) + " +
							  " decode(sign(substr('" + rs.getString("enterDate")+"',4,4)-substr(enterDate,4,4)),-1,-1,0) useYear, " +
							  " MOD( MONTHS_BETWEEN( to_date(substr('" + rs.getString("enterDate") + "',1,5)|| " +
							  " '01'+19110000,'YYYYMMDD') ,to_date(substr(enterDate,1,5)|| " +
							  " '01'+19110000,'YYYYMMDD') ) ,12 ) useMonth, " +
							  " deprMethod,deprAmount,monthDepr,apportionEndYM,accumDeprYM,accumDepr,permitReduceDate " +
							" from UNTBU_Building where enterOrg='" + rs.getString("enterOrg") + "'" +
								" and ownership='" + rs.getString("ownership") + "'" +
							  " and propertyKind='" + rs.getString("propertyKind") + "'" +
							  " and nvl(fundType,'99') =nvl('" + rs.getString("fundType") + "','99') " +
							  " and valuable ='" + rs.getString("valuable") + "'" +
							  " and oldPropertyNo ='" + rs.getString("oldPropertyNo") + "' and oldSerialNo ='" + rs.getString("oldSerialNo") + "'";
					
						rc = db.querySQL(sql);
						if (rc.next()) {					
							sbSQL.append(" insert into UNTBU_ReduceProof(" ).append(
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
								Common.sqlChar("Y") ).append( "," ).append(
								Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");		
							
							sbSQL.append(" insert into UNTBU_ReduceDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,otherLimitYear,propertyName1,signNo,doorPlate1,doorPlate2,doorPlate3,doorPlate4,cause,cause1,reduceDate,newEnterOrg,transferDate,verify,propertyKind,fundType,valuable,taxCredit,CArea,SArea,area,holdNume,holdDeno,holdArea,bookNotes,accountingTitle,bookValue,proofDoc,buildDate,sourceDate,useState,useYear,useMonth,deprMethod,deprAmount,monthDepr,apportionEndYM,accumDeprYM,accumDepr,permitReduceDate,accumDepr1,scrapValue1,submitCityGov,reduceDeal,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append( 
						" )Values(" ).append(
							Common.sqlChar(enterOrg) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
							Common.sqlChar(caseNo)).append(",").append(
							Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("otherLimitYear")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("propertyName1")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("signNo")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("doorPlate1")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("doorPlate2")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("doorPlate3")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("doorPlate4")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("newEnterOrg")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("transferDate")))).append(",").append(
							Common.sqlChar("Y")).append(",").append(
							Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("valuable")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("taxCredit")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("CArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("SArea")))).append(",").append(
							(rs.getDouble("CArea")+rs.getDouble("SArea"))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdNume")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdDeno")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdArea")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("accountingTitle")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("proofDoc")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("buildDate")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("sourceDate")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("useState")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("useYear")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("useMonth")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("deprMethod")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("deprAmount")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("monthDepr")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("apportionEndYM")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("accumDeprYM")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("accumDepr")))).append(",").append(
							Common.sqlChar(Common.get(rc.getString("permitReduceDate")))).append(",");
							
							switch (rc.getInt("deprMethod")) {
							case 1: sbSQL.append("'0','0',"); break;
							case 5: sbSQL.append("'").append(rs.getString("bookValue")).append("','0',"); break;
							case 2: case 3: case 4:
								String reduceDate = "00000", deprYM="00000";
								if (Common.get(rc.getString("reduceDate")).length()>4) {
									reduceDate = Common.get(rc.getString("reduceDate")).substring(0,5);
								}
								if (Function.getNumeric(reduceDate)>rc.getDouble("apportionEndYM")) {
									sbSQL.append("'").append(rs.getString("deprAmount")).append("','',"); break;
								} else {
									reduceDate = reduceDate+"01";
									deprYM = deprYM+"01";
									if (rc.getString("accumDeprYM").length()>4) {
										deprYM = rc.getString("accuDeprYM").substring(0,5)+"01";
									}
									DateTime rDateS =  new DateTime(Integer.parseInt(reduceDate.substring(0,3))+1911,Integer.parseInt(reduceDate.substring(3,5)),1, 0, 0, 0, 0);
									DateTime rDateE =  new DateTime(Integer.parseInt(deprYM.substring(0,3))+1911,Integer.parseInt(deprYM.substring(3,5)),1, 0, 0, 0, 0);
									Period p = new Period(rDateE.minusMonths(1), rDateS, PeriodType.yearMonthDay());							
									int diffYear = p.getYears();
									int diffMonth = p.getMonths();							
									diffMonth = diffMonth + (diffYear * 12);
									double accumDepr1 = Math.round(diffMonth*rc.getDouble("monthDepr"));
									sbSQL.append("'").append(accumDepr1).append("',");
									sbSQL.append("'").append(Math.round(rs.getDouble("bookValue")-accumDepr1)).append("',");							
																
								}
								break;
							default:
								sbSQL.append("'0','0',");
								break;
							}
							
							if ((rs.getDouble("bookValue")>1500000)||(rs.getDouble("buildDate")<rc.getDouble("permitReduceDate"))) {
								sbSQL.append("'Y',");						
							}else{
								sbSQL.append("'N',");
							}
							sbSQL.append("'',").append(Common.sqlChar(Common.get(rs.getString("notes")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");
							
							sbSQL.append(" update UNTBU_Building set dataState='2', holdNume=0, holdArea=0, bookValue=0 ").append(
									", reduceDate=").append(Common.sqlChar(Common.get(rs.getString("enterDate")))).append(
									", reduceCause=").append(Common.sqlChar(Common.get(rs.getString("cause")))).append(										
									", reduceCause1=").append(Common.sqlChar(Common.get(rs.getString("cause1")))).append(										
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
								db1.exeSQL("update untup_building set isErr='Y', errorColumn=errorColumn||'查無該筆土地資料！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
				db.exeSQL(" delete from untup_building where enterOrg='"+enterOrg+"'");		
				db.doCommit();
				setErrorMsg("資料轉入完成！");
			}			
		} catch (Exception e) {
			e.printStackTrace();			
			db.doRollback();
		} finally {
			//db.setAutoCommit(true);
			db.closeAll();
		}
	}
	
}
