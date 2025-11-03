package unt.up;

import java.sql.ResultSet;
import java.util.HashMap;

import util.Common;
import util.Database;
import util.Datetime;
import util.Validate;

public class TransferLA extends UNTUP000Q {
	
public void doLandUploadProcess(String enterOrg, java.util.Iterator it) {
	if (it!=null) {			
		Database db = new Database();
		try {
			db.setAutoCommit(false);			
			db.exeSQL(" delete from untup_land where enterOrg="+Common.sqlChar(enterOrg));
			String[] rowArray = new String[56];
			int rowid = 2;
			int intSuccessRecord = 0;
			int intErrorRecord = 0;
			String preSQL = " insert into untup_land(enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName1,signNo,doorPlate,cause,cause1,newEnterOrg,transferDate,taxCredit,area,holdNume,holdDeno,holdArea,originalBasis,originalDate,originalNote,changeItem,adjustNotes1,accountingTitle,bookUnit,bookValue,fundsSource,useSeparate,useKind,ownershipDate,ownershipCause,proofDoc1,ownershipNote,field,landRule,sourceKind,sourceDate,sourceDoc,oldOwner,manageOrg,useState,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,bulletinDate,valueUnit,notes1,notes,editID,editDate,editTime,errorColumn,isErr)Values(";
			HashMap h = new HashMap();			
			while(it.hasNext()) {
				rowArray=(String[])it.next();						
				//Excel Meet Empty Cell, leave it..				
				if (Common.get(rowArray[0]).equals("") && Common.get(rowArray[1]).equals("")) break;
				
				boolean hashErr = false;
				StringBuffer sbSQL = new StringBuffer(500);				
				sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
				StringBuffer sbErrMsg = new StringBuffer();	
				
				//檢查資料有無重覆。
				if (Common.get(rowArray[5]).equals("1")) {
					if (checkRecordExists("UNTLA_Land",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))) {
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
						if (checkRecordExists("UNTLA_Land",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))==false) {
				        	hashErr = true;
							sbErrMsg.append("無增加資料。\n");
						}
			        }
				}
				
				for (int i=0; i<56; i++) {
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
						
					case 11:
						if (Common.get(rowArray[5]).equals("1")) {
							if ("".equals(getLookupField(" select a.codeName from sysca_code a where a.codekindid='CAA' and substr(a.codeid,1,1) in ('1','4') and a.codeid='"+Common.get(rowArray[i])+"'"))) {
								sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");
							}
							//sbErrMsg.append(checkLandField(i, Common.get(rowArray[i])));
						} else if (Common.get(rowArray[5]).equals("4")){
							if ("".equals(getLookupField(" select a.codeName from sysca_code a where a.codekindid='CAA' and substr(a.codeid,1,1) in ('2','4') and a.codeid='"+Common.get(rowArray[i])+"'"))) {
								sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");
							}
						} else {
							if (Common.get(rowArray[i]).equals("1") || Common.get(rowArray[i]).equals("2") || Common.get(rowArray[i]).equals("3")) {								
							} else {
								sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");
							}
						}						
						break;
						
					case 12:
						if (Common.get(rowArray[5]).equals("1")||Common.get(rowArray[5]).equals("4")) {
							if (!"499".equals(Common.get(rowArray[11]))) rowArray[i] = "";
							else {
								if ("".equals(rowArray[i])) {
									sbErrMsg.append("登帳原因若為其他時，其他說明必須要有資料。\n");
								} else {
									sbErrMsg.append(checkLandField(i, Common.get(rowArray[i])));
								}
							}
						} else if (Common.get(rowArray[5]).equals("2")||Common.get(rowArray[5]).equals("3")) {
							if (!"3".equals(Common.get(rowArray[11]))) rowArray[i] = "";
							else {
								if ("".equals(rowArray[i])) {
									sbErrMsg.append("登帳原因若為其他時，其他說明必須要有資料。\n");
								} else {
									sbErrMsg.append(checkLandField(i, Common.get(rowArray[i])));
								}
							}							
						}						
						break;
						
					case 21:
						if ("1".equals(Common.get(rowArray[20])) || Common.get(rowArray[20]).equals("2")) {
							if (!"".equals(Common.get(rowArray[i]))) {
								if (!checkBulletinDateExists(Common.get(rowArray[20]),Common.get(rowArray[i]))) sbErrMsg.append("查無該公告年月。\n");
							} else {
								sbErrMsg.append("公告年月不能空白。\n");
							}
						} else {
							rowArray[i] = "";
						}
						break;
						
					case 30:
						if (!"".equals(Common.get(rowArray[29])) && Common.get(rowArray[29]).substring(0,1).equals("A")) {
							if (!"".equals(Common.get(rowArray[i]))) {
								if ("".equals(getCodeName("UKD",Common.get(rowArray[i])))) sbErrMsg.append("查無該編定使用種類代碼。\n");
							} else {
								sbErrMsg.append("編定使用種類代碼不能空白。\n");
							}
						}						
						break;
					default:
						sbErrMsg.append(checkLandField(i, Common.get(rowArray[i])));						
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
		
	public String checkLandField(int i, String s) {
		String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","珍貴財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","財產別名","土地標示代碼","街路名","登帳原因代碼","登帳原因－其他說明","接管機關代碼","交接日期","抵繳遺產稅代碼","整筆面積(㎡)","權利範圍－分子","權利範圍－分母","權利範圍面積(㎡)","原始入帳依據代碼","公告年月","帳務摘要","變更事項","異動紀錄其他事項","會計科目","單價","總價","經費來源代碼","使用分區代碼","編定使用種類代碼","所有權登記日期","所有權登記原因代碼","權狀字號","謄本其他登記事項","地目代碼","等則","財產來源－種類代碼","財產來源－取得日期","財產來源－取得文號","原有人","管理機關代碼","使用情形代碼","填單日期","填造單位","單據編號－字","單據編號－號","財產管理單位編號","傳票號數","核准機關代碼","核准日期","核准文號","當期公告日期","當期公告地價","其他事項","備註"};
		try {
			switch (i) {
				case 0:
					if (s.equals("1")||s.equals("2")) {} 
					else return "權屬代碼只能是1或2。\n";					
					break;
				case 1:
					if (!checkPropertyKind(s)) return "財產性質必須是01,02,03或04。\n";					
					break;					
				case 2:
					if (s.equals("03")) {
						if ("".equals(getCodeName("FUD",s))) {							
							return "查無此 "+s+" 基金財產代碼。";
						}
					} return "";
					
				case 3: case 15:
					if (!Validate.checkYN(s)) {					
						return arrFieldName[i]+"，請輸入Y或N。";								
					}
					break;
					
				case 4: case 14: case 31: case 38: case 43: case 50: case 52:							
					if (!"".equals(Validate.checkDate(s, arrFieldName[i]))) {
						return Validate.checkDate(s, arrFieldName[i])+"\n";
					}
					if (i==4) {
						if ( s.equals("")) return arrFieldName[i]+"不能空白。\n";
						else if((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(s.substring(0,5)))){
							return "入帳年月必須大於月結年月！\n";
						}
					}
					break;
					
				case 5:							
					if ((!"".equals(s) && Validate.checkInt(s)) && (Integer.parseInt(s)>0 || Integer.parseInt(s)<5)) {}
					else return "增減別代碼只能是1,2,3,4。\n";					
					break;
					
				case 6:
					if (!"".equals(s) && s.length()>1 && s.substring(0,1).equals("1")) {
						if ("".equals(getPropertyName(enterOrg,s))) {						
							return "查無此 "+s+" 原財產編號。\n";
						}						
					} else return "原財產編號不能為空值且開頭字元必須為1。\n";
					break;
				
				case 7:
					if (s.equals("") || s.length()>7) return "原財產分號錯誤(空值或長度超過7碼)。\n";					
					break;
					
				case 8: case 12: case 22: case 33: case 39: case 51:
					if (s.length()>20) return arrFieldName[i]+"已超過20個字。\n";					
					break;
					
				case 9:
					if ("".equals(s) || (s.length()!=15) || (Validate.checkInt(s.substring(7))==false)){
						return "此"+s+"土地標示代碼格式不符或為空值。\n";
					} else if ("".equals(getSignName(s))) {
						return "查無此"+s+"土地標示代碼的縣市區里資料。\n";
					} else if (checkSignNameExists("LA", enterOrg, ownership, s)) {							
						return "土地標示資料重覆。\n";						
					}
					break;
					
				case 10:
					if (s.length()>25) return "街路名已超過25個字。\n";					
					break;							
					
				case 11:
					//需搭配增減別方能檢查
//					if ("".equals(s) || "".equals(getCodeName("CAA",s))) {						
//						return "查無此 "+s+" 登帳原因代碼。\n";
//					}
					break;
				
				case 13:
					if (!"".equals(s)) {
						if (getOrganSName(s).equals("")) {
							return "查無此 "+s+arrFieldName[i];									
						}
					}
					break;
					
				case 16: case 19:
					if (!"".equals(s)) {
						if (!Validate.checkFloat(s, 7, 2)) {							
							return arrFieldName[i]+"整數部分不可大於7位，小數部份不可大於2位。";								
						} else if (Double.parseDouble(s)<0){							
							return arrFieldName[i]+"必須>=0。\n";									
						}
					}
					break;
				
				case 17: case 18: case 26: case 27: case 53:
					if (!"".equals(s)) {
						if (!Validate.checkInt(s)) return arrFieldName[i]+"　請輸入正整數。\n";
						else if (Integer.parseInt(s)<0){
							return arrFieldName[i]+"　必須>=0。\n";
						} else {
							if ((i==17 || i==18) && s.length()>10) return arrFieldName[i]+"　不能大於10位數。\n";
							if (i==26 && s.length()>13) return arrFieldName[i]+"　不能大於13位數。\n";
							if (i==27 && s.length()>15) return arrFieldName[i]+"　不能大於15位數。\n";
						}
					} else {
						if (i==53) return "";
						else return arrFieldName[i]+"不能為空白。\n";
					}
					break;
					
				case 20:
					if (!"".equals(s)) {
						if (Validate.checkInt(s) && Integer.parseInt(s)>0 && Integer.parseInt(s)<4) return "";
						else return arrFieldName[i]+"　需為1,2或3。\n";
					}
					break;
				
				case 21:
					//需搭配20方能檢查
					break;		
					
				case 23: case 40:
					if (s.length()>30) return arrFieldName[i]+"已超過 30個字元。\n";
					break;
					
				case 24: case 54:
					if (s.length()>60) return arrFieldName[i]+"已超過 60個字元。\n";					
					break;

				case 25:
					if (s.length()>20) return arrFieldName[i]+"已超過 20個字元。\n";
					break;

				//case 25: case 45: case 46: case 47:
				case 45: case 46: case 47:
					if (s.length()>10) return arrFieldName[i]+"已超過 10個字元。\n";
					else if (i==45 || i==46){
						if (s.equals("")) return arrFieldName[i] +"不能空白。\n";
					}
					break;
				
				case 28: case 29:case 30:case 32: case 35: case 37: case 42: case 49:					
					if (!"".equals(s)) {
						if (getCodeName(getCodeKind(i),s).equals("")) return arrFieldName[i]+"　查無該代碼資料。\n";
					}
					break;
					
				case 34: case 55:
					if (s.length()>250) return arrFieldName[i]+"已超過 250 個字元。\n";					
					break;
					
				case 36:
					if (!"".equals(s)) {
						if (!Validate.checkInt(s) || s.length()>2 || Integer.parseInt(s)<0) return "等則為2位數字。\n";
					}
					break;
					
				case 41:
					if (!"".equals(s)) {
						if ("".equals(getOrganSName(s))) return "查無此"+arrFieldName[i];
					}
					break;
					
				case 44: case 48:
					if (s.length()>15) return arrFieldName[i]+"已超過 15 個字元。\n";					
					break;					
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "錯誤\n";			
		}			
	}
	
	private String getCodeKind(int s) {
		String[] arrCodeKind = new String[] {"FSO","SEP","UKD","OCA","FIE","SKD","UST","APO"};
		int[] arrID = new int[] {28,29,30,32,35,37,42,49};
		for (int i=0; i<arrID.length; i++) {
			if (arrID[i]==s) return arrCodeKind[i];
		}
		return "";
	}	
	
	
	public void doLandTransfer(String enterOrg) throws Exception {
		Database db = new Database();
		try {
			db.setAutoCommit(false);
			String sql = "";
			ResultSet rc;
			boolean sFlag = false;
			ResultSet rs = db.querySQL("select enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName1,signNo,doorPlate,cause,cause1,newEnterOrg,transferDate,taxCredit,area,holdNume,holdDeno,holdArea,originalBasis,originalDate,originalNote,changeItem,adjustNotes1,accountingTitle,bookUnit,bookValue,fundsSource,useSeparate,useKind,ownershipDate,ownershipCause,proofDoc1,ownershipNote,field,landRule,sourceKind,sourceDate,sourceDoc,oldOwner,manageOrg,useState,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,bulletinDate,valueUnit,notes1,notes,editID,editDate,editTime,errorColumn,isErr from untup_land where enterOrg="+Common.sqlChar(enterOrg)+" order by enterOrg,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo");
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
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTLA_AddProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
						}
						rc.getStatement().close();
						rc.close();
						
						//取得SerialNo
						sql="select substr(nvl(max(serialNo),0)+10000001,2) as serialNo from UNTLA_LAND " +
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
								db1.exeSQL("update untup_land set isErr='Y', errorColumn='入帳年月必須大於月結年月！(建議：請不要使用上傳作業上傳後，又使用本系統畫面登打資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
								db1.doCommit();
							} catch (Exception e) {
								sFlag = true;
								setErrorMsg("入帳年月必須大於月結年月！");
								db1.doRollback();
								e.printStackTrace();							
							} finally {							
								db1.closeAll();
							}						
							break;
						}					
						
						sql=" select count(*) as checkResult from UNTLA_Land where 1=1 " + 
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
								db1.exeSQL("update untup_land set isErr='Y', errorColumn='資料己重複！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
						sql = " select count(*) as checkResult from UNTLA_LAND where enterOrg = " + Common.sqlChar(enterOrg) + 
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) + 
							" and signNo = " + Common.sqlChar(Common.get(rs.getString("signNo"))) + " and dataState='1' ";
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0) {
							sFlag = true;
							setErrorMsg("土地標示己重複，請重新輸入！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_land set isErr='Y', errorColumn=errorColumn||'土地標示己重複，請重新輸入！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
								db1.doCommit();
							} catch (Exception e) {
								sFlag = true;
								setErrorMsg("土地標示己重複，請重新輸入！");
								db1.doRollback();
								e.printStackTrace();							
							} finally {
								db1.closeAll();
							}
							break;						
						}
						rc.getStatement().close();
						rc.close();					
						
						sbSQL.append(" insert into UNTLA_AddProof(" ).append(
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
							" mergeDivision,").append(
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
							Common.sqlChar("") ).append( "," ).append(
							Common.sqlChar("Y") ).append( "," ).append(
							Common.sqlChar("N") ).append( "," ).append(
							Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;			
						
						sbSQL.append(" insert into UNTLA_Land(" ).append(
								" enterOrg,").append(
								" ownership,").append(
								" caseNo,").append(
								" propertyNo,").append(
								" serialNo,").append(
								" propertyName1,").append(
								" signNo,").append(
								" doorplate,").append(
								" cause,").append(
								" cause1,").append(
								" enterDate,").append(
								" dataState,").append(
								" reduceDate,").append(
								" reduceCause,").append(
								" reduceCause1,").append(
								" verify,").append(
								" closing,").append(
								" propertyKind,").append(
								" fundType,").append(
								" valuable,").append(
								" taxCredit,").append(
								" grass,").append(
								" originalArea,").append(
								" originalHoldNume,").append(
								" originalHoldDeno,").append(
								" originalHoldArea,").append(
								" area,").append(
								" holdNume,").append(
								" holdDeno,").append(
								" holdArea,").append(
								" originalBasis,").append(
								" originalDate,").append(
								" originalUnit,").append(
								" originalBV,").append(
								" originalNote,").append(
								" accountingTitle,").append(
								" bookUnit,").append(
								" bookValue,").append(
								" fundsSource,").append(
								" useSeparate,").append(
								" useKind,").append(
								" oriUseSeparate,").append(
								" oriUseKind,").append(
								" ownershipDate,").append(
								" ownershipCause,").append(
								" nonProof,").append(
								" proofDoc,").append(
								" proofVerify,").append(
								" ownershipNote,").append(
								" field,").append(
								" landRule,").append(
								" sourceKind,").append(
								" sourceDate,").append(
								" sourceDoc,").append(
								" oldOwner,").append(
								" manageOrg,").append(
								" useState,").append(
								" proceedDateS,").append(
								" proceedDateE,").append(
								" proceedType,").append(
								" appraiseDate,").append(
								" notes1,").append(
								" notes,").append(
								" oldPropertyNo,").append(
								" oldSerialNo,").append(
								" editID,").append(
								" editDate,").append(
								" editTime").append(
							" )Values(" ).append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
								Common.sqlChar(caseNo) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
								Common.sqlChar(serialNo) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("propertyName1"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("signNo"))) ).append(",").append(
								Common.sqlChar(Common.get(rs.getString("doorplate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("cause"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("cause1"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
								Common.sqlChar("1") ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("Y") ).append( "," ).append(
								Common.sqlChar("N") ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("propertyKind"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("fundType"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("valuable"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("taxCredit"))) ).append( "," ).append(
								Common.sqlChar("N") ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("area"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("holdNume"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("holdDeno"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("holdArea"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("area"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("holdNume"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("holdDeno"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("holdArea"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalBasis"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("bookUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("bookValue"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalNote"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("accountingTitle"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("bookUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("bookValue"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("fundsSource"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("useSeparate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("useKind"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("useSeparate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("useKind"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownershipDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownershipCause"))) ).append( "," ).append(
								Common.sqlChar(nonProof) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("proofDoc"))) ).append( "," ).append(
								Common.sqlChar("N") ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownershipNote"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("field"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("landRule"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("sourceKind"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("sourceDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("sourceDoc"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldOwner"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("manageOrg"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("useState"))) ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("notes1"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("notes"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) ).append( "," ).append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
						
						db.exeSQL(sbSQL.toString().split(":;:"));
						break;
						
					case 2: case 3: //土地增減單 UNTLA_AdjustProof, UNTLA_AdjustDetail & update UNTLA_Land
						String op = "+";
						if (rs.getInt("adjustType")==2) op = "+";
						else if (rs.getInt("adjustType")==3) op = "-";			
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTLA_AdjustProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							//caseNo = (Common.get(rs.getString("enterDate"))+Common.formatFrontZero(rc.getString("caseNo"),3));
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = " select propertyNo, serialNo,propertyName1,signNo,originalBV,sourceDate, area,holdNume, holdDeno,holdArea,bookUnit,bookValue,useSeparate,useKind from UNTLA_Land " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and nvl(valuable,'N') = nvl('" + Common.get(rs.getString("valuable")) + "','N') " +
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));					
						rc = db.querySQL(sql);
						if (rc.next()) {
							sbSQL.append(" insert into UNTLA_AdjustProof (" ).append(
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
								
							sbSQL.append(" insert into UNTLA_AdjustDetail(" ).append(
								" enterOrg,").append(
								" ownership,").append(
								" caseNo,").append(
								" propertyNo,").append(
								" serialNo,").append(
								" propertyName1,").append(
								" signNo,").append(
								" cause,").append(
								" cause1,").append(
								" bulletinDate,").append(
								" adjustDate,").append(
								" verify,").append(
								" propertyKind,").append(
								" fundType,").append(
								" valuable,").append(
								" taxCredit,").append(
								" grass,").append(
								" originalBV,").append(
								" sourceDate,").append(
								" oldArea,").append(
								" oldHoldNume,").append(
								" oldHoldDeno,").append(
								" oldHoldArea,").append(
								" newArea,").append(
								" newHoldNume,").append(
								" newHoldDeno,").append(
								" newHoldArea,").append(
								" adjustArea,").append(
								" adjustHoldArea,").append(
								" bookNotes,").append(
								" oldBookUnit,").append(
								" oldBookValue,").append(
								" newBookUnit,").append(
								" newBookValue,").append(
								" adjustBookUnit,").append(
								" adjustBookValue,").append(
								" changeItem,").append(
								" notes1,").append(
								" useSeparate,").append(
								" useKind,").append(
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
								Common.sqlChar(rc.getString("serialNo")) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("propertyName1"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("signNo"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("cause"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("cause1"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
								Common.sqlChar("Y") ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("propertyKind"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("fundType"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("valuable"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("taxCredit"))) ).append( "," ).append(
								Common.sqlChar("N") ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("originalBV"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("sourceDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("area"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("holdNume"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("holdDeno"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("holdArea"))) ).append( ",(" ).append(
								rc.getString("area")).append(op).append(rs.getString("area")).append( "),(" ).append(
								rc.getString("holdNume")).append(op).append(rs.getString("holdNume")).append( "),(" ).append(									
								rc.getString("holdDeno")).append(op).append(rs.getString("holdDeno")).append( "),(" ).append(
								rc.getString("holdArea")).append(op).append(rs.getString("holdArea")).append( ")," ).append(	
								Common.sqlChar(Common.get(rs.getString("area"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("holdArea"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("originalNote"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("bookUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("bookValue"))) ).append( ",(" ).append(
								rc.getString("bookUnit")).append(op).append(rs.getString("bookUnit")).append( "),(" ).append(									
								rc.getString("bookValue")).append(op).append(rs.getString("bookValue")).append( ")," ).append(									
								Common.sqlChar(Common.get(rs.getString("bookUnit"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("bookValue"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("changeItem"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("notes1"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("useSeparate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rc.getString("useKind"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("notes"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) ).append( "," ).append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
							
							if (Common.get(rs.getString("cause")).equals("1")) {
								sbSQL.append(" update UNTLA_AdjustDetail set oldArea=(").append(
										rc.getString("area")).append("), oldholdNume=(" ).append(
										rc.getString("holdNume")).append( "), oldholdDeno=(" ).append(									
										rc.getString("holdDeno")).append( "), oldholdArea=(" ).append(
										rc.getString("holdArea")).append( ") ").append(
										" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
										" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
										" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
										" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");							
								
								sbSQL.append(" update untla_land set bookunit=(").append(
									rc.getString("bookUnit")).append(op).append(rs.getString("bookUnit")).append( "), bookValue=(" ).append(
									rc.getString("bookValue")).append(op).append(rs.getString("bookValue")).append( ") " ).append(
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
							} else if (Common.get(rs.getString("cause")).equals("2")) {
								sbSQL.append(" update UNTLA_AdjustDetail set oldArea=(").append(
										rc.getString("area")).append("), oldholdNume=(" ).append(
										rc.getString("holdNume")).append( "), oldholdDeno=(" ).append(									
										rc.getString("holdDeno")).append( "), oldholdArea=(" ).append(
										rc.getString("holdArea")).append( ") ").append(
										" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
										" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
										" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
										" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
								
								sbSQL.append(" update untla_land set bookunit=(").append(
									rc.getString("bookUnit")).append(op).append(rs.getString("bookUnit")).append( "), bookValue=(" ).append(
									rc.getString("bookValue")).append(op).append(rs.getString("bookValue")).append( "), appraiseDate='" ).append(Common.get(rs.getString("enterDate"))).append("' where ").append(
									" enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");							
							} else if (Common.get(rs.getString("cause")).equals("3")) {
								sbSQL.append(" update untla_land set area=(").append(
									rc.getString("area")).append(op).append(rs.getString("area")).append( "), holdNume=(" ).append(
									rc.getString("holdNume")).append(op).append(rs.getString("holdNume")).append( "), holdDeno=(" ).append(									
									rc.getString("holdDeno")).append(op).append(rs.getString("holdDeno")).append( "), holdArea=(" ).append(
									rc.getString("holdArea")).append(op).append(rs.getString("holdArea")).append( "), bookUnit=(" ).append(									
									rc.getString("bookUnit")).append(op).append(rs.getString("bookUnit")).append( "), bookValue=(" ).append(
									rc.getString("bookValue")).append(op).append(rs.getString("bookValue")).append( ") " ).append(
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");							
							} else {
								sFlag = true;
								setErrorMsg("查無該增減值原因代碼！");
								util.Database db1 = new util.Database();						
								try {
									db1.exeSQL("update untup_land set isErr='Y', errorColumn=errorColumn||'查無該增減值原因代碼！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
							db.exeSQL(sbSQL.toString().split(":;:"));
						} else {
							sFlag = true;
							setErrorMsg("查無增加資料！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_land set isErr='Y', errorColumn=errorColumn||'查無增加資料！(建議：在上傳的Excel檔案中依增加、增減值、減少等屬性排放資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						break;
					case 4:					
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTLA_ReduceProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							//caseNo = (Common.get(rs.getString("enterDate"))+Common.formatFrontZero(rc.getString("caseNo"),3));
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = " select propertyNo, serialNo,propertyName1,signNo, accountingTitle, originalBV,sourceDate, area,holdNume, holdDeno,holdArea,bookUnit,bookValue,useSeparate,useKind, proofDoc, field, useState from UNTLA_Land " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and nvl(valuable,'N') = nvl('" + Common.get(rs.getString("valuable")) + "','N') " +
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));					
						rc = db.querySQL(sql);
						if (rc.next()) {					
							sbSQL.append(" insert into UNTLA_ReduceProof(" ).append(
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
								" mergeDivision,").append(
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
								Common.sqlChar("") ).append( "," ).append(
								Common.sqlChar("Y") ).append( "," ).append(
								Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");		
							
							sbSQL.append(" insert into UNTLA_ReduceDetail(" ).append(
							" enterOrg,").append(
							" ownership,").append(
							" caseNo,").append(
							" propertyNo,").append(
							" serialNo,").append(
							" propertyName1,").append(
							" signNo,").append(
							" cause,").append(
							" cause1,").append(
							" reduceDate,").append(
							" newEnterOrg,").append(
							" transferDate,").append(
							" verify,").append(
							" propertyKind,").append(
							" fundType,").append(
							" valuable,").append(
							" taxCredit,").append(
							" grass,").append(
							" area,").append(
							" holdNume,").append(
							" holdDeno,").append(
							" holdArea,").append(
							" bookNotes,").append(
							" accountingTitle,").append(
							" bookUnit,").append(
							" bookValue,").append(
							" useSeparate,").append(
							" useKind,").append(
							" proofDoc,").append(
							" field,").append(
							" sourceDate,").append(
							" useState,").append(
							" bulletinDate,").append(
							" valueUnit,").append(
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
							Common.sqlChar(rc.getString("serialNo")) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("propertyName1"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("signNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("cause"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("cause1"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("newEnterOrg"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("transferDate"))) ).append( "," ).append(
							Common.sqlChar("Y") ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("propertyKind"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("fundType"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("valuable"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("taxCredit"))) ).append( "," ).append(
							Common.sqlChar("N") ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("area"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("holdNume"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("holdDeno"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("holdArea"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("originalNote"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("accountingTitle"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("bookUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("bookValue"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("useSeparate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("useKind"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("proofDoc"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("field"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("sourceDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rc.getString("useState"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("bulletinDate"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("valueUnit"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("notes"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) ).append( "," ).append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");
							
							sbSQL.append(" update untla_land set dataState='2', holdNume=0, holdArea=0, bookValue=0 ").append(
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
								db1.exeSQL("update untup_land set isErr='Y', errorColumn=errorColumn||'查無該筆土地資料！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
				db.exeSQL(" delete from untup_land where enterOrg='"+enterOrg+"'");		
				db.doCommit();
				setErrorMsg("資料轉入完成！");
			}			
		} catch (Exception e) {
			e.printStackTrace();			
			db.doRollback();
		} finally {
			db.closeAll();
		}
	}
}
