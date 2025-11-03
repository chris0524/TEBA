package unt.up;

import java.sql.ResultSet;
import java.util.HashMap;

import util.Common;
import util.Database;
import util.Datetime;
import util.Function;
import util.Validate;

public class TransferVP extends UNTUP000Q {
		
	private int intSuccessRecord = 0;
	private int intErrorRecord = 0;
	private int intSuccessDtlRecord = 0;
	private int intErrorDtlRecord = 0;		
	private HashMap h = new HashMap();		
	
	public void doVPUploadProcess(String enterOrg, java.util.Iterator it, java.util.Iterator it1) {
		if (it!=null && it1!=null) {
			Database db = new Database();
			try {
				setEnterOrg(enterOrg);
				db.setAutoCommit(false);				
				db.exeSQL(" delete from untup_valuepaper1 where enterOrg="+Common.sqlChar(enterOrg));
				db.exeSQL(" delete from untup_valuepaper2 where enterOrg="+Common.sqlChar(enterOrg));				
				int rowid = 2;			
				while(it.hasNext()) {
					String[] rowArray = new String[29];					
					rowArray=(String[])it.next();					
					String sSQL = getVP1SQL(rowid, rowArray);
					rowid = rowid + 1;
					if (!"".equals(sSQL)) db.exeSQL(sSQL.split(":;:"));
					else break;
				}
				
				rowid = 2;			
				while(it1.hasNext()) {
					String[] rowArray = new String[17];					
					rowArray=(String[])it1.next();
					String sSQL = getVP2SQL(rowid, rowArray);
					rowid = rowid + 1;
					if (!"".equals(sSQL)) db.exeSQL(sSQL.split(":;:"));
					else break;
				}				
				try {
					if (rowid>2) {
						setErrorRecordCount(intErrorRecord);
						setSuccessRecordCount(intSuccessRecord);						
						setDtlSuccessRecordCount(intSuccessDtlRecord);
						setDtlErrorRecordCount(intErrorDtlRecord);
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
	
	private String getVP1SQL(int rowid, String[] rowArray) {
		
		String preSQL = " insert into untup_valuepaper1(enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName1,cause,cause1,keepUnit,keepUnitName,place,securityMeat,securityName,securityAddr,securityItem,securityTime,securityOrg,securityDoc,capitalStock,originalNote,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,notes,editID,editDate,editTime,errorColumn,isErr)Values(";				
		if (Common.get(rowArray[0]).equals("")) {
			return "";
		} else {
			boolean hashErr = false;
			StringBuffer sbSQL = new StringBuffer(500);					
			sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
			StringBuffer sbErrMsg = new StringBuffer();	
			
			//檢查資料有無重覆。
			if (Common.get(rowArray[4]).equals("1")) {
				if (checkVPExists("UNTVP_AddProof",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[5]),Common.get(rowArray[6]))) {
		        	hashErr = true;
					sbErrMsg.append("己和正式檔資料重覆，建議調整財產分號以使資料不重覆。\n");
				}
				Integer r = (Integer)h.get(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[5])+Common.get(rowArray[6]));
		        if(r==null){
		        	r = new Integer(rowid);
		        	h.put(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[5])+Common.get(rowArray[6]), r);
		        } else {
		        	hashErr = true;
		        	sbErrMsg.append("己和列號").append(r.intValue()).append("的暫存檔資料重覆，建議調整財產分號以使資料不重覆。\n");
		        }
			} else {
				Integer r = (Integer)h.get(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[5])+Common.get(rowArray[6]));
		        if(r==null){
					if (checkVPExists("UNTVP_AddProof",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[5]),Common.get(rowArray[6]))==false) {
			        	hashErr = true;
						sbErrMsg.append("無增加資料。\n");
					}
		        }
			}
			
			for (int i=0; i<rowArray.length; i++) {
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
					}	
					break;
		
				case 8:
					if (Common.get(rowArray[4]).equals("1")) {
						if (getCodeName("CAB",rowArray[i]).equals("")) {
							sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("登帳原因代碼或代碼不符。\n");								
						}
					} else {
						if (getCodeName("CAD",rowArray[i]).equals("")) {
							sbErrMsg.append("查無此").append(Common.get(rowArray[i])).append("增減值原因代碼或代碼不符。\n");								
						}		
					}	
					break;
					
				case 9:
					if (!"99".equals(Common.get(rowArray[13]))) {
						rowArray[i]="";
					} else {
						if ("".equals(rowArray[i])) {
							sbErrMsg.append("登帳原因若為其他時，其他說明必須要有資料。\n");
						} else {
							sbErrMsg.append(checkVP1Field(i, Common.get(rowArray[i])));
						}
					}
					break;
	
				default:
					sbErrMsg.append(checkVP1Field(i, Common.get(rowArray[i])));						
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
			return sbSQL.toString();
		}
	}
	
	private String checkVP1Field(int i, String s) {
		String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","財產別名","登帳原因代碼","登帳原因－其他說明","保管單位代碼","保管單位名稱","存放地點","內容","發行法人名稱","發行法人地址","發行法人事業項目","發行法人設立年月","發行法人核准登記機關","發行法人核准登記字號","發行股份總數","原始入帳－摘要","填單日期","填造單位","單據編號－字","單據編號－號","財產管理單位編號","傳票號數","備註"};
		try {
			switch (i) {
				case 0:
					if (s.equals("1")||s.equals("2")) {} 
					else return "權屬代碼只能是1或2。\n";					
					break;
				case 1:
					if (!checkPropertyKind(s)) return "財產性質必須是01,02,03或04。\n";					
					break;
					
				case 3: case 22:							
					if (!"".equals(Validate.checkDate(s, arrFieldName[i]))) {
						return Validate.checkDate(s, arrFieldName[i])+"\n";
					}
					if (i==3) {
						if ( s.equals("")) return arrFieldName[i]+"不能空白。\n";
						else if (((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(s.substring(0,5))))){
							return "入帳年月必須大於月結年月！\n";
						}
					}
					break;
					
				case 4:					
					if (s.equals("1")||s.equals("2")) {} 	
					else return "增減別代碼只能是 1 或 2。\n";					
					break;
					
				case 5:
					if (!"".equals(s) && s.length()>1 && s.substring(0,1).equals("9")) {
						if ("".equals(getPropertyName(enterOrg,s))) {						
							return "查無此 "+s+" 原財產編號。\n";
						}						
					} else return "原財產編號不能為空值且開頭字元必須為9。\n";
					break;
				
				case 6:
					if (s.equals("") || s.length()>7) return "原財產分號錯誤(空值或長度超過7碼)。\n";					
					break;
					
				case 7: case 9: case 19: case 21:
					if (s.length()>20) return arrFieldName[i]+"已超過20個字。\n";
					break;
					
				case 10:
					if ("".equals(s)) return arrFieldName[i] +"不能為空值。\n";
					else if (!Validate.checkAlphaInt(s) || s.length()>10) return arrFieldName[i] + " 需為半形字數字或英文字母，長度不能超過10個字。\n";
					break;
					
				case 11:
					if ("".equals(s)) return arrFieldName[i] +"不能為空值。\n";
					else if(s.length()>30) return arrFieldName[i] +"已超過 30個字元。\n";
					break;
					
				case 12: case 13: case 23: case 27:
					if (s.length()>15) return arrFieldName[i] +"已超過 15個字元。\n";
					else if (i==27 && Validate.checkAlphaInt(s)==false) return arrFieldName[i]+" 需為半形數字或英文字母。\n";
					break;
				
				case 14:
					if ("".equals(s) || s.length()>15) return arrFieldName[i] +"不能為空值、不能超過 15個字元。\n";
					break;
					
				case 15:
					if (s.length()>50) return arrFieldName[i] + " 已超過50個字元。";
					break;
					
				case 16:
					if (s.length()>25) return arrFieldName[i]+" 已超過25個字。\n";
					break;
					
				case 17:
					if (!Validate.checkYYYMM(s) || s.length()>5) return arrFieldName[i] + " 需為(YYYMM)年月格式。\n";					
					break;
					
				case 18:
					if (!"".equals(s) && getOrganSName(s).equals("")) return "查無此" + arrFieldName[i] + "代碼。\n";
					break;
					
				case 20:					
					if (!"".equals(s) && Validate.checkInt(s) && Function.getNumeric(s)>0 && s.length()<8) {}
					else return arrFieldName[i] + " 必須為 >0 的正整數值。\n";
					break;
				
				case 24: case 25:
					if ("".equals(s) || s.length()>10) return arrFieldName[i] + "不能為空值或超過10個字元。\n";
					break;
					
				case 26:
					if (!Validate.checkAlphaInt(s) || s.length()>10) return arrFieldName[i] + "需為半形數字或英文字母，長度不能超過10個字元。\n";
					break;

				case 28:
					if (s.length()>250) return arrFieldName[i] + "已超過250個字。\n";					
					break;
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "錯誤\n";			
		}			
	}
	
	private String getVP2SQL(int rowid, String[] rowArray) {		
		String preSQL = " insert into untup_valuepaper2(enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,oldSerialNo1,adjustType1,bookUnitAmount,bookSheet,bookUnitValue,proofS,proofE,newProofS,newProofE,notes,editID,editDate,editTime,errorColumn,isErr)Values(";				
		if (Common.get(rowArray[1]).equals("") || rowArray.length!=17) {
			return "";
		} else {
			boolean hashErr = false;
			StringBuffer sbSQL = new StringBuffer(500);					
			sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
			StringBuffer sbErrMsg = new StringBuffer();	
			
			//檢查資料有無重覆。
			Integer r = (Integer)h.get(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[5])+Common.get(rowArray[6]));
	        if(r==null){
				if (checkVPExists("UNTVP_AddProof",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[5]),Common.get(rowArray[6]))==false) {
		        	hashErr = true;
					sbErrMsg.append("無增加資料。\n");
				}
	        }
			for (int i=0; i<rowArray.length; i++) {
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
					}
					break;			
	
				default:
					sbErrMsg.append(checkVP2Field(i, Common.get(rowArray[i])));						
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
				intSuccessDtlRecord = intSuccessDtlRecord + 1;
			} else {
				intErrorDtlRecord = intErrorDtlRecord + 1;
				sbSQL.append(Common.sqlChar("Y")).append("):;:");
			}
			return sbSQL.toString();
		}

	}	
	
	private String checkVP2Field(int i, String s) {
		String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","原股份次序","增減值代碼","每張股數","張數","每股單價","證明書編號起","證明書編號訖","新證明書編號起","新證明書編號訖","備註"};
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
					if ("".equals(s) || !Validate.checkDate(s)) return arrFieldName[i] + "請輸入民國(YYYMMDD)日期格式。\n";
					else if ((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(s.substring(0,5)))){
						return "入帳年月必須大於月結年月！\n";
					}
					break;
					
				case 4:	case 8:				
					if (s.equals("1")||s.equals("2")) {} 	
					else return arrFieldName[i]+"只能是 1 或 2。\n";					
					break;
					
				case 5:
					if (!"".equals(s) && s.length()>1 && s.substring(0,1).equals("9")) {
						if ("".equals(getPropertyName(enterOrg,s))) {						
							return "查無此 "+s+" 原財產編號。\n";
						}						
					} else return "原財產編號不能為空值且開頭字元必須為9。\n";
					break;
				
				case 6: case 7:
					if (s.equals("") || s.length()>7) return arrFieldName[i]+"錯誤(空值或長度超過7碼)。\n";					
					break;
					
				case 9: case 10:
					if (!"".equals(s) && Validate.checkInt(s) && Function.getNumeric(s)>0 && s.length()<6) {}
					else return arrFieldName[i] + " 必須為介於 1~99999 的正整數值。\n";
					break;
					
				case 11:
					if ("".equals(s)) return arrFieldName[i] +"不能為空值。\n";
					else if (!Validate.checkInt(s) || s.length()>5 || Function.getNumeric(s)<0) return arrFieldName[i] + " 為 >=0 和 <=99999 的整數。\n";
					break;
					
				case 12: case 13:
					if ("".equals(s) || s.length()>10) return arrFieldName[i] +"不能為空值或長度超過15個字元。\n";
					break;
					
				case 14: case 15:
					if (s.length()>15) return arrFieldName[i] + " 已超過15個字元。";
					break;
					
				case 16:
					if (s.length()>250) return arrFieldName[i] + "已超過250個字。\n";					
					break;						
										
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "錯誤\n";			
		}			
	}	
	
	
	public void doVPTransfer(String enterOrg) throws Exception {
		Database db = new Database();
		try {
			db.setAutoCommit(false);
			String sql = "";
			ResultSet rc;
			boolean sFlag = false;
			ResultSet rs = db.querySQL("select enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName1,cause,cause1,keepUnit,keepUnitName,place,securityMeat,securityName,securityAddr,securityItem,securityTime,securityOrg,securityDoc,capitalStock,originalNote,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,notes,editID,editDate,editTime,errorColumn,isErr from untup_valuepaper1 where enterOrg="+Common.sqlChar(enterOrg)+" order by enterOrg,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo");
			while (rs.next()) {
				StringBuffer sbSQL = new StringBuffer(500);			
				String caseNo = Common.get(rs.getString("enterDate")).substring(0,5)+"00001";					
				if (sFlag==false) {
					switch (rs.getInt("adjustType")) {
					case 1: //增加					
						String serialNo="0000001";
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTVP_AddProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
						}
						rc.getStatement().close();
						rc.close();
						
						//取得SerialNo
						sql="select substr(nvl(max(serialNo),0)+10000001,2) as serialNo from UNTVP_AddProof " +
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
								db1.exeSQL("update untup_valuepaper1 set isErr='Y', errorColumn='入帳年月必須大於月結年月！(建議：請不要使用上傳作業上傳後，又使用本系統畫面登打資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
						sql=" select count(*) as checkResult from UNTVP_AddProof where 1=1 " + 
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
								db1.exeSQL("update untup_valuepaper1 set isErr='Y', errorColumn='資料己重複！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
												
						//取得propertyUnit, material, limitYear, 000000000A
						sql="select count(*) from SYSPK_PropertyCode " +
							" where enterOrg in ('000000000A'," + Common.sqlChar(enterOrg) + ") and propertyType='1' " +
							" and substr(propertyNo,1,1)='9' and propertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo"));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
						} else {
							sFlag = true;
							setErrorMsg("查無此 "+rs.getString("oldPropertyNo")+" 財產編號！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_valuepaper1 set isErr='Y', errorColumn='查無此" +rs.getString("oldPropertyNo")+ " 財產編號！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
						//KeepUnit & Keeper
						sbSQL.append(getKeepUnitSQL(enterOrg, rs.getString("keepUnit"), rs.getString("keepUnitName")));					
						//End of KeepUnit & Keeper
						
						sql = " select sum(bookSheet) originalSheet, sum(bookUnitAmount*bookSheet) originalAmount, " +
					    	" sum(bookUnitAmount*bookSheet*bookUnitValue) originalBV from UNTUP_ValuePaper2 " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
							" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));
						
						double originalSheet=0, originalAmount=0, originalBV = 0;  
						rc = db.querySQL(sql);
						if (rc.next()) {
							originalSheet = rs.getDouble("originalSheet");
							originalAmount  = rs.getDouble("originalAmount");							
							originalBV = rs.getDouble("originalBV");
						}
						rc.getStatement().close();
						rc.close();
												
						sbSQL.append(" insert into UNTVP_AddProof(enterOrg,ownership,caseNo,caseName,propertyNo,serialNo,propertyName1,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,cause,cause1,enterDate,originalNote,dataState,verify,closing,propertyKind,fundType,keepUnit,place,securityMeat,securityName,securityAddr,securityItem,securityTime,securityOrg,securityDoc,capitalStock,originalSheet,originalAmount,originalBV,bookSheet,bookAmount,bookValue,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append(
						" )Values(" ).append(
							Common.sqlChar(enterOrg) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
							Common.sqlChar(caseNo) ).append( ",'上傳列號" ).append(
							rs.getString("textSerialNo")).append( "'," ).append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
							Common.sqlChar(serialNo)).append(",").append(
							Common.sqlChar(Common.get(rs.getString("propertyName1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("writeDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("writeUnit")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("proofDoc")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("proofNo")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("manageNo")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("summonsNo")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("dataState")))).append(",").append(
							Common.sqlChar("Y")).append(",").append(
							Common.sqlChar("N")).append(",").append(
							Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("keepUnit")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("place")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("securityMeat")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("securityName")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("securityAddr")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("securityItem")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("securityTime")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("securityOrg")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("securityDoc")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("capitalStock")))).append(",'").append(
							originalSheet).append("','").append(
							originalAmount).append("','").append(
							originalBV).append("',").append(
							originalSheet).append("','").append(
							originalAmount).append("','").append(
							originalBV).append("',").append(
							Common.sqlChar(Common.get(rs.getString("notes")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;			
									
							sql = " select enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,oldSerialNo1,adjustType1,bookUnitAmount,bookSheet,bookUnitValue,proofS,proofE,newProofS,newProofE,notes,editID,editDate,editTime,errorColumn,isErr from untup_valuepaper2 " +
								" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
								" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
								" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
								" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
								" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
								" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
								" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
								" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));
							rc = db.querySQL(sql);
							int serialNo1 = 0;
							while (rc.next()) {								
								if (serialNo1==0) {
									sql="select nvl(max(serialNo1),0)+1 as serialNo1 from UNTVP_Share " +
										" where enterOrg = " + Common.sqlChar(enterOrg) + 
										" and ownership = " + Common.sqlChar(ownership) + 
										" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
										" and oldSerialNo =" + Common.sqlChar(serialNo);
									ResultSet rd = db.querySQL(sql);
									if (rd.next() && rd.getInt(1)>0) serialNo1 = rd.getInt(1);
									else serialNo1 = 1;
									rd.getStatement().close();
									rd.close();
								} else {
									serialNo1 = serialNo1 + 1;
								}
								sbSQL.append(" insert into UNTVP_Share(enterOrg,ownership,propertyNo,serialNo,serialNo1,oldSerialNo1,enterDate,dataState,reduceDate,reduceCause,reduceCause1,verify,closing,originalUA,originalSheet,originalAmount,originalUV,originalBV,originalProofS,originalProofE,bookUnitAmount,bookSheet,bookAmount,bookUnitValue,bookValue,proofS,proofE,notes,editID,editDate,editTime").append(
								" )Values(" ).append(
									Common.sqlChar(enterOrg) ).append( "," ).append(
									Common.sqlChar(Common.get(rc.getString("ownership"))) ).append( "," ).append(
									Common.sqlChar(Common.get(rc.getString("oldPropertyNo")))).append(",").append(
									Common.sqlChar(serialNo)).append(",").append(
									Common.sqlChar(Common.get(rc.getString("serialNo1")))).append(",'").append(
									serialNo1).append("',").append(
									Common.sqlChar(Common.get(rc.getString("oldSerialNo1")))).append(",").append(											
									Common.sqlChar(Common.get(rc.getString("enterDate")))).append(",").append(
									"'1','','','','Y','N',").append(
									Common.sqlChar(Common.get(rc.getString("bookUnitAmount")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("bookSheet")))).append(",'").append(
									(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet"))).append("',").append(
									Common.sqlChar(Common.get(rc.getString("bookUnitValue")))).append(",'").append(
									(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet")*rc.getDouble("bookUnitValue"))).append("',").append(
									Common.sqlChar(Common.get(rc.getString("proofS")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("proofE")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("bookUnitAmount")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("bookSheet")))).append(",'").append(
									(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet"))).append("',").append(
									Common.sqlChar(Common.get(rc.getString("bookUnitValue")))).append(",'").append(
									(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet")*rc.getDouble("bookUnitValue"))).append("',").append(
									Common.sqlChar(Common.get(rc.getString("proofS")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("proofE")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("notes")))).append(",").append(
									Common.sqlChar(getEditID()) ).append( "," ).append(
									Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
									Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;									
							}
							rc.getStatement().close();
							rc.close();							
						
						db.exeSQL(sbSQL.toString().split(":;:"));
						break;
						
					case 2: //有價證券增減單 UNTVP_AdjustProof, UNTVP_AdjustDetail & update UNTVP_Share
						String op = "+";
						if (rs.getInt("adjustType")==2) op = "+";
						else if (rs.getInt("adjustType")==3) op = "-";			
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTVP_AdjustProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = " select propertyNo,serialNo,propertyName1,keepUnit,place,securityMeat,securityName,securityAddr,securityItem,securityTime,securityOrg,securityDoc,bookSheet,bookAmount,bookValue from UNTVP_AddProof " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));					
						rc = db.querySQL(sql);
						if (rc.next()) {
							
							//KeepUnit
							sbSQL.append(getKeepUnitSQL(enterOrg, rs.getString("keepUnit"), rs.getString("keepUnitName")));					
							//End of KeepUnit
							
							sql = "select sum(decode(adjustType1,'2',-bookSheet,bookSheet)) adjustBookSheet, sum(decode(adjustType1,'2',-bookUnitAmount*bookSheet, " +
								" bookUnitAmount*bookSheet)) adjustBookAmount, sum(decode(adjustType1,'2',-bookUnitAmount*bookSheet*bookUnitValue, " +
								" bookUnitAmount*bookSheet*bookUnitValue)) adjustBookValue from UNTUP_ValuePaper2 " +
								" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
								" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
								" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
								" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
								" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
								" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
								" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
								" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));
							
							double adjustBookSheet = 0, adjustBookAmount = 0, adjustBookValue = 0;
							ResultSet rd = db.querySQL(sql);
							if (rd.next()) {
								adjustBookSheet = rs.getDouble(1);
								adjustBookAmount = rs.getDouble(2);
								adjustBookValue = rs.getDouble(3);
							}
							rd.getStatement().close();
							rd.close();							
							
							sbSQL.append(" insert into UNTVP_AdjustProof (enterOrg,ownership,caseNo,caseName,propertyNo,serialNo,propertyName1,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,cause,cause1,adjustDate,bookNotes,verify,propertyKind,fundType,keepUnit,place,securityMeat,securityName,securityAddr,securityItem,securityTime,securityOrg,securityDoc,oldBookSheet,oldBookAmount,oldBookValue,adjustBookSheet,adjustBookAmount,adjustBookValue,newBookSheet,newBookAmount,newBookValue,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append(
							" ) Values (" ).append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
								Common.sqlChar(caseNo) ).append( ",'上傳列號" ).append(
								rs.getString("textSerialNo")).append( "'," ).append(
								Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("propertyName1")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("writeDate")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("writeUnit")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("proofDoc")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("proofNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("manageNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("summonsNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("adjustDate")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
								Common.sqlChar("Y")).append(",").append(
								Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("keepUnit")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("place")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("securityMeat")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("securityName")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("securityAddr")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("securityItem")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("securityTime")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("securityOrg")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("securityDoc")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("bookSheet")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("bookAmount")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",'").append(
								adjustBookSheet).append("','").append(adjustBookAmount).append("','").append(
								adjustBookValue).append("','").append((rc.getDouble("bookSheet")+adjustBookSheet)
								).append("','").append((rc.getDouble("bookAmount")+adjustBookAmount)).append("','").append(
								(rc.getDouble("bookValue")+adjustBookValue)).append("','").append(
								Common.get(rs.getString("notes"))).append("由上傳作業上傳',").append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
							
							//Select UNTUP_ValuePaper2 with UNTUP_ValuePaper1's key
							sql = " select enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,oldSerialNo1,adjustType1,bookUnitAmount,bookSheet,bookUnitValue,proofS,proofE,newProofS,newProofE,notes,editID,editDate,editTime,errorColumn,isErr from untup_valuepaper2 " +
								" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
								" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
								" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
								" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
								" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
								" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
								" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
								" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));
							rd = db.querySQL(sql);
							serialNo1 = 0;							
							while (rd.next()) {	
								if (rd.getString("adjustType1").equals("1")) {
									if (serialNo1==0) {
										sql="select nvl(max(serialNo1),0)+1 as serialNo1 from UNTVP_Share " +
											" where enterOrg = " + Common.sqlChar(enterOrg) + 
											" and ownership = " + Common.sqlChar(ownership) + 
											" and propertyNo=" + Common.sqlChar(Common.get(rc.getString("propertyNo"))) +
											" and serialNo =" + Common.sqlChar(rc.getString("serialNo"));
										ResultSet re = db.querySQL(sql);
										if (re.next() && re.getInt(1)>0) serialNo1 = re.getInt(1);
										else serialNo1 = 1;
										re.getStatement().close();
										re.close();
									} else {
										serialNo1 = serialNo1 + 1;
									}
									sbSQL.append(" insert into UNTVP_Share(enterOrg,ownership,propertyNo,serialNo,serialNo1,oldSerialNo1,enterDate,dataState,reduceDate,reduceCause,reduceCause1,verify,closing,originalUA,originalSheet,originalAmount,originalUV,originalBV,originalProofS,originalProofE,bookUnitAmount,bookSheet,bookAmount,bookUnitValue,bookValue,proofS,proofE,notes,editID,editDate,editTime").append(
									" )Values(" ).append(
										Common.sqlChar(enterOrg) ).append( "," ).append(
										Common.sqlChar(Common.get(rc.getString("ownership"))) ).append( "," ).append(
										Common.sqlChar(Common.get(rc.getString("oldPropertyNo")))).append(",").append(
										Common.sqlChar(rc.getString("serialNo"))).append(",'").append(
										serialNo1).append("',").append(
										Common.sqlChar(Common.get(rc.getString("serialNo1")))).append(",").append(												
										Common.sqlChar(Common.get(rd.getString("oldSerialNo1")))).append(",").append(											
										Common.sqlChar(Common.get(rc.getString("enterDate")))).append(",").append(
										"'1','','','','Y','Y',").append(
										Common.sqlChar(Common.get(rc.getString("bookUnitAmount")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("bookSheet")))).append(",'").append(
										(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet"))).append("',").append(
										Common.sqlChar(Common.get(rc.getString("bookUnitValue")))).append(",'").append(
										(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet")*rc.getDouble("bookUnitValue"))).append("',").append(
										Common.sqlChar(Common.get(rc.getString("proofS")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("proofE")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("bookUnitAmount")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("bookSheet")))).append(",'").append(
										(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet"))).append("',").append(
										Common.sqlChar(Common.get(rc.getString("bookUnitValue")))).append(",'").append(
										(rc.getDouble("bookUnitAmount")*rc.getDouble("bookSheet")*rc.getDouble("bookUnitValue"))).append("',").append(
										Common.sqlChar(Common.get(rc.getString("proofS")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("proofE")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("notes")))).append(",").append(
										Common.sqlChar(getEditID()) ).append( "," ).append(
										Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
										Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;									
									
									sbSQL.append(" insert into UNTVP_AdjustDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,adjustType,serialNo1,oldSerialNo1,oldBookUA,oldBookSheet,oldBookAmount,oldBookUV,oldBookValue,oldProofS,oldProofE,adjustBookUA,adjustBookSheet,adjustBookAmount,adjustBookUV,adjustBookValue,adjustProofS,adjustProofE,newBookUA,newBookSheet,newBookAmount,newBookUV,newBookValue,newProofS,newProofE,notes,editID,editDate,editTime").append( 
										" )Values(" ).append(
										Common.sqlChar(enterOrg) ).append( "," ).append(
										Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
										Common.sqlChar(caseNo)).append(",").append(
										Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
										Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("adjustTyp1")))).append(",'").append(
										serialNo1).append("',").append(
										Common.sqlChar(Common.get(rd.getString("oldSerialNo1")))).append(",").append(
										"0,0,0,0,0,'','',,").append(
										Common.sqlChar(Common.get(rd.getString("bookUnitAmount")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("bookSheet")))).append(",'").append(
										(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet"))).append("',").append(
										Common.sqlChar(Common.get(rd.getString("bookUnitValue")))).append(",'").append(
										(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet")*rd.getDouble("bookUnitValue"))).append("',").append(
										Common.sqlChar(Common.get(rd.getString("proofS")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("proofE")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("bookUnitAmount")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("bookSheet")))).append(",'").append(
										(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet"))).append("',").append(
										Common.sqlChar(Common.get(rd.getString("bookUnitValue")))).append(",'").append(
										(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet")*rd.getDouble("bookUnitValue"))).append("',").append(
										Common.sqlChar(Common.get(rd.getString("proofS")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("proofE")))).append(",'").append(
										Common.get(rd.getString("notes"))).append("由上傳作業輸入。',").append(
										Common.sqlChar(getEditID()) ).append( "," ).append(
										Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
										Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");	
									
								} else if (rd.getString("adjustType1").equals("2")) {
									sql = " select propertyNo,serialNo1,bookUnitAmount oldBookUA, bookSheet oldBookSheet, bookAmount oldBookAmount, bookUnitValue oldBookUV, " +
								       	" bookValue oldBookValue, proofS oldProofS, proofE oldProofE, bookUnitAmount adjustBookUA, bookUnitValue adjustBookUV, " +
								       	" bookUnitAmount newBookUA, bookUnitValue newBookUV from UNTVP_Share " +
										" where enterOrg=" + Common.sqlChar(Common.get(rd.getString("enterOrg"))) +
										" and ownership=" + Common.sqlChar(Common.get(rd.getString("ownership"))) +
										" and propertyKind=" + Common.sqlChar(Common.get(rd.getString("propertyKind"))) +
										" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rd.getString("fundType"))) + ",'99') " +
										" and oldPropertyNo=" + Common.sqlChar(Common.get(rd.getString("oldPropertyNo"))) +
										" and oldSerialNo =" + Common.sqlChar(Common.get(rd.getString("oldSerialNo")));
									ResultSet re = db.querySQL(sql);
									if (re.next()) {									
										sbSQL.append(" insert into UNTVP_AdjustDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,adjustType,serialNo1,oldSerialNo1,oldBookUA,oldBookSheet,oldBookAmount,oldBookUV,oldBookValue,oldProofS,oldProofE,adjustBookUA,adjustBookSheet,adjustBookAmount,adjustBookUV,adjustBookValue,adjustProofS,adjustProofE,newBookUA,newBookSheet,newBookAmount,newBookUV,newBookValue,newProofS,newProofE,notes,editID,editDate,editTime").append( 
											" )Values(" ).append(
											Common.sqlChar(enterOrg) ).append( "," ).append(
											Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
											Common.sqlChar(caseNo)).append(",").append(
											Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
											Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
											Common.sqlChar(Common.get(rd.getString("adjustType")))).append(",").append(
											Common.sqlChar(re.getString("serialNo1"))).append(",").append(
											Common.sqlChar(Common.get(rd.getString("oldSerialNo1")))).append(",").append(
											Common.sqlChar(Common.get(re.getString("oldBookUA")))).append(",").append(
											Common.sqlChar(Common.get(re.getString("oldBookSheet")))).append(",").append(
											Common.sqlChar(Common.get(re.getString("oldBookAmount")))).append(",").append(
											Common.sqlChar(Common.get(re.getString("oldBookUV")))).append(",").append(
											Common.sqlChar(Common.get(re.getString("oldBookValue")))).append(",").append(
											Common.sqlChar(Common.get(re.getString("oldProofS")))).append(",").append(
											Common.sqlChar(Common.get(re.getString("oldProofE")))).append(",").append(
											Common.sqlChar(Common.get(rd.getString("bookUnitAmount")))).append(",").append(
											Common.sqlChar(Common.get(rd.getString("bookSheet")))).append(",'").append(
											(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet"))).append("',").append(		
											Common.sqlChar(Common.get(rd.getString("bookUnitValue")))).append(",'").append(
											(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet")*rd.getDouble("bookUnitValue"))).append("',").append(
											Common.sqlChar(Common.get(rd.getString("proofS")))).append(",").append(
											Common.sqlChar(Common.get(rd.getString("proofE")))).append(",'").append(											
											(re.getDouble("oldBookUA")-rd.getDouble("bookUnitAmount"))).append("','").append(
											(re.getDouble("oldBookSheet")-rd.getDouble("bookSheet"))).append("','").append(
											(re.getDouble("oldBookAmount")-(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet")))).append("',").append(		
											Common.sqlChar(Common.get(rd.getString("bookUnitValue")))).append(",'").append(
											(re.getDouble("oldBookValue")-(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet")*rd.getDouble("bookUnitValue")))).append("',").append(
											Common.sqlChar(Common.get(rd.getString("proofS")))).append(",").append(
											Common.sqlChar(Common.get(rd.getString("proofE")))).append(",").append(
											Common.sqlChar(Common.get(rd.getString("notes")))).append(",").append(
											Common.sqlChar(getEditID()) ).append( "," ).append(
											Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
											Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");

										sbSQL.append(" update UNTVP_Share set verify='Y', closing='Y', bookUnitAmount='").append((re.getDouble("oldBookUA")-rd.getDouble("bookUnitAmount"))).append("',").append(
												(re.getDouble("oldBookSheet")-rd.getDouble("bookSheet"))).append("', bookUnitValue='").append(
												Common.get(rd.getString("bookUnitValue"))).append("',bookValue='").append(														
												(re.getDouble("oldBookValue")-(rd.getDouble("bookUnitAmount")*rd.getDouble("bookSheet")*rd.getDouble("bookUnitValue")))).append("',").append(
												" newProofS=").append(Common.sqlChar(Common.get(rd.getString("proofS")))).append(",").append(
												" newProofE=").append(Common.sqlChar(Common.get(rd.getString("proofE"))));
										if ((re.getDouble("oldBookSheet")-rd.getDouble("bookSheet"))<=0) {
											sbSQL.append(" , dataState='2', reduceDate='").append(rs.getString("enterDate")).append("',").append(
												" reduceCause='").append(rs.getString("cause")).append("', reduceCause1='").append(
												rs.getString("cause1")).append("' ");
										}
										sbSQL.append(" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
											" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
											" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
											" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(
											" and serialNo1=" ).append( Common.sqlChar(Common.get(rd.getString("serialNo1")))).append(":;:");										
									
										
										//newBookUA,newBookSheet,newBookAmount,newBookUV,newBookValue,newProofS,newProofE
									} else {
										sFlag = true;
										setErrorMsg("若做減值作業，必須先有資料存在於明細資料檔中！");
										util.Database db1 = new util.Database();						
										try {
											db1.exeSQL("update untup_valuepaper2 set isErr='Y', errorColumn=errorColumn||'若做減值作業，必須先有資料存在於明細資料檔中！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rd.getString("textSerialNo"));
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
									re.getStatement().close();
									re.close();
								}
								
							
							}
							if (Common.get(rs.getString("cause")).equals("1")) {
								sbSQL.append(" update UNTVP_AddProof set measure='").append(rc.getString("measure")).append(										
									"', appraiseDate=" ).append(Common.sqlChar(Common.get(rs.getString("enterDate")))).append(								
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(											
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
							} else {				
								sbSQL.append(" update UNTVP_AddProof set measure=(").append(
									rc.getString("measure")).append(op).append(rs.getString("measure")).append( "), bookValue=(" ).append(									
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
								db1.exeSQL("update untup_valuepaper1 set isErr='Y', errorColumn=errorColumn||'查無增加資料！(建議：在上傳的Excel檔案中依增加、增減值、減少等屬性排放資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
				
					}//End Switch
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
				db.exeSQL(" delete from untup_valuepaper1 where enterOrg='"+enterOrg+"'");		
				db.exeSQL(" delete from untup_valuepaper2 where enterOrg='"+enterOrg+"'");				
				db.doCommit();
				setErrorMsg("資料轉入完成！");
			}			
		} catch (Exception e) {
			e.printStackTrace();			
			db.doRollback();
		} finally {
			db.setAutoCommit(true);
			db.closeAll();
		}
	}
}
