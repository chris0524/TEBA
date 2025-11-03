package unt.up;

import java.sql.ResultSet;
import java.util.HashMap;

import util.Common;
import util.Database;
import util.Datetime;
import util.Function;
import util.Validate;

public class TransferRT extends UNTUP000Q {
		
	private int intSuccessRecord = 0;
	private int intErrorRecord = 0;
	private int intSuccessDtlRecord = 0;
	private int intErrorDtlRecord = 0;		
	private HashMap h = new HashMap();		
	
	public void doRTUploadProcess(String enterOrg, java.util.Iterator it, java.util.Iterator it1) {
		if (it!=null && it1!=null) {
			Database db = new Database();
			try {
				setEnterOrg(enterOrg);
				db.setAutoCommit(false);				
				db.exeSQL(" delete from untup_right1 where enterOrg="+Common.sqlChar(enterOrg));
				db.exeSQL(" delete from untup_right2 where enterOrg="+Common.sqlChar(enterOrg));				
				int rowid = 2;			
				while(it.hasNext()) {
					String[] rowArray = new String[36];					
					rowArray=(String[])it.next();					
					String sSQL = getRT1SQL(rowid, rowArray);
					rowid = rowid + 1;
					if (!"".equals(sSQL)) db.exeSQL(sSQL.split(":;:"));
					else break;
				}
				
				rowid = 2;			
				while(it1.hasNext()) {
					String[] rowArray = new String[26];					
					rowArray=(String[])it1.next();
					String sSQL = getRT2SQL(rowid, rowArray);
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
	
	private String getRT1SQL(int rowid, String[] rowArray) {
		
		String preSQL = " insert into untup_right1(enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName1,cause,cause1,originalNote,buyDate,sourceKind,sourceDate,sourceDoc,meat,proofDoc1,holdNume1,holdDeno2,bookValue,registerCause,registerDate,setPeriod,commonObligee,setPerson,payDate,interest,rent,notes1,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,notes,editID,editDate,editTime,errorColumn,isErr)Values(";				
		if (Common.get(rowArray[0]).equals("") || rowArray.length!=36) {
			return "";
		} else {
			boolean hashErr = false;
			StringBuffer sbSQL = new StringBuffer(500);					
			sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
			StringBuffer sbErrMsg = new StringBuffer();	
			
			//檢查資料有無重覆。
			if (Common.get(rowArray[4]).equals("1")) {
				if (checkVPExists("UNTRT_AddProof",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[5]),Common.get(rowArray[6]))) {
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
					if (checkVPExists("UNTRT_AddProof",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[5]),Common.get(rowArray[6]))==false) {
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
						if (getCodeName("CAC",rowArray[i]).equals("")) {
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
							sbErrMsg.append(checkRT1Field(i, Common.get(rowArray[i])));
						}
					}
					break;
					
				case 19:
					sbErrMsg.append(checkRT1Field(i, Common.get(rowArray[i])));
					if (Common.get(rowArray[5]).length()>2 && (rowArray[5].substring(0,3).equals("801")||rowArray[5].substring(0,3).equals("806"))) {
						if ("".equals(Common.get(rowArray[i]))||!Validate.checkInt(rowArray[i])) sbErrMsg.append("財產編號為801或806開頭的資料，設定價值必須輸入≧0 的整數值。\n");						
					}
					break;
	
				default:
					sbErrMsg.append(checkRT1Field(i, Common.get(rowArray[i])));						
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
	
	private String checkRT1Field(int i, String s) {
		String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","財產別名","登帳原因代碼","登帳原因－其他說明","帳務摘要","購置日期","財產來源－種類代碼","財產來源－取得日期","財產來源－取得文號","內容","證明書字號","他項權利範圍－分子","他項權利範圍－分母","設定價值","登記原因代碼","登記日期","設定起訖日期","共同權利人","設定人","清償時間","利息","地租","其他事由","填單日期","填造單位","單據編號－字","單據編號－號","財產管理單位編號","傳票號數","備註"};
		try {
			switch (i) {
				case 0:
					if (s.equals("1")||s.equals("2")) {} 
					else return "權屬代碼只能是1或2。\n";					
					break;
				case 1:
					if (!checkPropertyKind(s)) return "財產性質必須是01,02,03或04。\n";					
					break;
					
				case 3: case 11: case 13: case 21: case 25: case 29:
					if (!"".equals(Validate.checkDate(s, arrFieldName[i]))) {
						return Validate.checkDate(s, arrFieldName[i])+"\n";
					}
					if (i==3 || i==11) {
						if ( s.equals("")) return arrFieldName[i]+"不能空白。\n";
						else if ((i==3) && ((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(s.substring(0,5))))){
							return "入帳年月必須大於月結年月！\n";
						}
					}
					break;
					
				case 4:					
					if (s.equals("1")||s.equals("2")||s.equals("3")) {} 	
					else return "增減別代碼只能是 1, 2 或 3。\n";			
					break;
					
				case 5:
					if (!"".equals(s) && s.length()>1 && s.substring(0,1).equals("8")) {
						if ("".equals(getPropertyName(enterOrg,s))) {						
							return "查無此 "+s+" 原財產編號。\n";
						}
					} else return "原財產編號不能為空值且開頭字元必須為8。\n";
					break;
				
				case 6:
					if (s.equals("") || s.length()>7) return "原財產分號錯誤(空值或長度超過7碼)。\n";					
					break;
					
				case 7: case 9: case 10: case 14: case 16:
					if (s.length()>20) return arrFieldName[i]+"已超過20個字。\n";
					break;
					
				case 12:
					if (!"".equals(s) && getCodeName("SKE",s).equals("")) return "查無該" + arrFieldName[i] +"(建議：若不知代碼，可以將它弄成空值)。\n";
					break;
					
				case 15:
					if(s.length()>30) return arrFieldName[i] +"已超過 30個字元。\n";
					break;
					
				case 17: case 18:
					if (!"".equals(s) && Validate.checkInt(s) && Function.getNumeric(s)>0 && s.length()<8) {}
					else return arrFieldName[i] + " 必須為 >0 的正整數值。\n";
					break;
					
				case 19: case 23: case 24: case 26: case 27: case 30: case 34:
					if (s.length()>15) return arrFieldName[i] + "已超過15個字。\n";
					else if (i==26 && !Validate.checkInt(s)) return arrFieldName[i] + "需為正整數。\n";
					break;
					
				case 20:
					if (!"".equals(s) && "".equals(getCodeName("RCA",s))) return "查無"+arrFieldName[i]+"(建議：若不知道代碼，請將它弄成空值。\n";
					break;
					
				case 22:
					if(s.length()>10) return arrFieldName[i] +"已超過 10個字元。\n";
					break;
					
				case 28:
					if(s.length()>60) return arrFieldName[i] +"已超過 60個字元。\n";
					break;
					
				case 31: case 32:
					if (!Validate.checkAlphaInt(s) || s.length()>10) return arrFieldName[i] + "需為半形數字或英文字母，長度不能超過10個字元。\n";
					break;

				case 35:
					if (s.length()>250) return arrFieldName[i] + "已超過250個字。\n";					
					break;
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "錯誤\n";			
		}			
	}
	
	private String getRT2SQL(int rowid, String[] rowArray) {		
		String preSQL = " insert into untup_right2(enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,oldSerialNo1,signNo,area,holdNume,holdDeno,holdArea,adjustType1,bookValue,registerNo1,rightKind,registerNo2,setRightScope,setObligor,landPurpose,doorPlate,buildingArea,buildingOwner,buildingPurpose,notes,editID,editDate,editTime,errorColumn,isErr)Values(";				
		if (Common.get(rowArray[1]).equals("") || rowArray.length!=26) {
			return "";
		} else {
			boolean hashErr = false;
			StringBuffer sbSQL = new StringBuffer(500);					
			sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
			StringBuffer sbErrMsg = new StringBuffer();	
			
			//檢查資料有無重覆。
			Integer r = (Integer)h.get(enterOrg+Common.get(rowArray[0])+Common.get(rowArray[1])+Common.get(rowArray[2])+Common.get(rowArray[5])+Common.get(rowArray[6]));
	        if(r==null){
				if (checkVPExists("UNTRT_AddProof",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[5]),Common.get(rowArray[6]))==false) {
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
					sbErrMsg.append(checkRT2Field(i, Common.get(rowArray[i])));						
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
	
	private String checkRT2Field(int i, String s) {
		String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","原標的次序","土地標示代碼","整筆面積(㎡)","權利範圍－分子","權利範圍－分母","權利範圍面積(㎡)","增減值代碼","權利價值","登記次序","權利標的","標的登記次序","設定權利範圍","設定義務人","土地用途","建物－房屋門牌","建物－面積","建物－所有人","建物－用途","備註"};
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
					
				case 4:
					if (s.equals("1")||s.equals("2")||s.equals("3")) {} 	
					else return arrFieldName[i]+"只能是 1,2或3。\n";					
					break;
					
				case 5:
					if (!"".equals(s) && s.length()>1 && s.substring(0,1).equals("89")) {
						if ("".equals(getPropertyName(enterOrg,s))) {						
							return "查無此 "+s+" 原財產編號。\n";
						}						
					} else return "原財產編號不能為空值且開頭字元必須為8。\n";
					break;
				
				case 6: case 7:
					if (s.equals("") || s.length()>7) return arrFieldName[i]+"錯誤(空值或長度超過7碼)。\n";					
					break;
					
				case 8:
					if (!"".equals(s)) {
						if ((s.length()!=15) || (Validate.checkInt(s.substring(7))==false)){
							return "此"+s+"土地標示代碼格式不符。\n";
						} else if ("".equals(getSignName(s))) {
							return "查無此"+s+"土地標示代碼的縣市區里資料。\n";
						}
					}
					break;
					
				case 9: case 12: case 22:
					if (!"".equals(s)) {
						if (!Validate.checkFloat(s, 7, 2)) {							
							return arrFieldName[i]+"整數部分不可大於7位，小數部份不可大於2位。";								
						} else if (!(Double.parseDouble(s)>0)){							
							return arrFieldName[i]+"必須>0。\n";									
						}
					}
					break;
					
				case 10: case 11:
					if ("".equals(s)) {
						if (!Validate.checkInt(s) || s.length()>10 || Function.getNumeric(s)<=0) return arrFieldName[i] + "必須為 >0的整數。\n";
					}
					break;
					
				case 13:
					if (s.equals("1")||s.equals("2")) {} 	
					else return arrFieldName[i]+"只能是 1或2。\n";
					break;
					
				case 14:
					if ("".equals(s) || s.length()>15 || !Validate.checkInt(s) || Function.getNumeric(s)<=0) return arrFieldName[i] + "需為15個字元內>0的整數值。\n";
					break;
					
				case 15:
					if (s.length()>8) return arrFieldName[i] +"已超過8個字元。\n";
					break;
					
				case 16:
					if (!"".equals(s) && "".equals(getCodeName("RKD",s))) return "查無"+arrFieldName[i]+"。\n";
					break;
					
				case 17:
					if (s.length()>4) return arrFieldName[i]+"已超過4個字元。\n";
					break;
				
				case 18: case 21:
					if (s.length()>50) return arrFieldName[i]+"已超過50個字元。\n";
					break;
					
					
				case 19: case 23:
					if (s.length()>15) return arrFieldName[i]+"已超過15個字元。\n";
					break;					
					
				case 20: case 24:
					if (s.length()>30) return arrFieldName[i]+"已超過30個字元。\n";
					break;
					
				case 25:
					if (s.length()>250) return arrFieldName[i] + "已超過250個字。\n";					
					break;
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "錯誤\n";			
		}			
	}	
	
	
	public void doRTTransfer(String enterOrg) throws Exception {
		Database db = new Database();
		try {
			db.setAutoCommit(false);
			String sql = "";
			ResultSet rc;
			boolean sFlag = false;
			ResultSet rs = db.querySQL("select enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,propertyName1,cause,cause1,originalNote,buyDate,sourceKind,sourceDate,sourceDoc,meat,proofDoc1,holdNume1,holdDeno2,bookValue,registerCause,registerDate,setPeriod,commonObligee,setPerson,payDate,interest,rent,notes1,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,notes,editID,editDate,editTime,errorColumn,isErr from untup_right1 where enterOrg="+Common.sqlChar(enterOrg)+" order by enterOrg,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo");
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
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTRT_AddProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
						}
						rc.getStatement().close();
						rc.close();
						
						//取得SerialNo
						sql="select substr(nvl(max(serialNo),0)+10000001,2) as serialNo from UNTRT_AddProof " +
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
								db1.exeSQL("update untup_right1 set isErr='Y', errorColumn='入帳年月必須大於月結年月！(建議：請不要使用上傳作業上傳後，又使用本系統畫面登打資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
						sql=" select count(*) as checkResult from UNTRT_AddProof where 1=1 " + 
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
								db1.exeSQL("update untup_right1 set isErr='Y', errorColumn='資料己重複！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
							" and substr(propertyNo,1,1)='8' and propertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo"));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
						} else {
							sFlag = true;
							setErrorMsg("查無此 "+rs.getString("oldPropertyNo")+" 財產編號！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_right1 set isErr='Y', errorColumn='查無此" +rs.getString("oldPropertyNo")+ " 財產編號！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
											
						double originalBV = 0;						
						String s = Common.get(rs.getString("oldPropertyNo"));
						if (s.length()>2 && (s.substring(0,3).equals("801")||s.substring(0,3).equals("806"))) {
							originalBV = rs.getDouble("bookValue");
						} else {
							sql = " select sum(bookValue) originalBV from untup_right2 " +
								" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
								" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
								" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
								" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
								" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
								" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
								" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
								" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));	  
							rc = db.querySQL(sql);
							if (rc.next()) {							
								originalBV = rs.getDouble("originalBV");
							}
							rc.getStatement().close();
							rc.close();
						}
												
						sbSQL.append("insert into UNTRT_AddProof(enterOrg,ownership,caseNo,caseName,propertyNo,serialNo,propertyName1,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,cause,cause1,enterDate,originalNote,dataState,verify,closing,propertyKind,fundType,buyDate,sourceKind,sourceDate,sourceDoc,meat,nonProof,proofDoc1,proofVerify,holdNume1,holdDeno2,originalBV,bookValue,registerCause,registerDate,setPeriod,commonObligee,setPerson,payDate,interest,rent,notes1,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append(
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
							"'1','Y','N',").append(
							Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("buyDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceDoc")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("meat")))).append(",").append(
							Common.sqlChar(nonProof)).append(",").append(
							Common.sqlChar(Common.get(rs.getString("proofDoc1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("proofVerify")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdNume1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("holdDeno2")))).append(",'").append(
							originalBV).append("','").append(originalBV).append("',").append(
							Common.sqlChar(Common.get(rs.getString("registerCause")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("registerDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("setPeriod")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("commonObligee")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("setPerson")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("payDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("interest")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("rent")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("notes1")))).append(",'").append(
							Common.get(rs.getString("notes"))).append("由上傳作業輸入',").append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;			
									
							sql = " select enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,oldSerialNo1,signNo,area,holdNume,holdDeno,holdArea,adjustType1,bookValue,registerNo1,rightKind,registerNo2,setRightScope,setObligor,landPurpose,doorPlate,buildingArea,buildingOwner,buildingPurpose,notes,editID,editDate,editTime,errorColumn,isErr from untup_right2 " +
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
									sql="select nvl(max(serialNo1),0)+1 as serialNo1 from UNTRT_Share " +
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
								
								double setArea = 0;
								if (rs.getDouble("holdNume1")>0 && rs.getDouble("holdNume2")>0) {
									setArea = rs.getDouble("holdArea") * (rs.getDouble("holdNume1") / rs.getDouble("holdNume2"));
								}								
								
								sbSQL.append(" insert into UNTRT_AddDetail(enterOrg,ownership,propertyNo,serialNo,serialNo1,oldSerialNo1,signNo,enterDate,dataState,reduceDate,reduceCause,reduceCause1,verify,closing,area,holdNume,holdDeno,holdArea,setArea,originalBV,bookValue,registerNo1,rightKind,registerNo2,setRightScope,setObligor,landPurpose,doorPlate,buildingArea,buildingOwner,buildingPurpose,notes,editID,editDate,editTime").append(
								" )Values(" ).append(
									Common.sqlChar(enterOrg) ).append( "," ).append(
									Common.sqlChar(Common.get(rc.getString("ownership")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
									Common.sqlChar(serialNo)).append(",").append(serialNo1).append(",").append(
									Common.sqlChar(Common.get(rc.getString("oldSerialNo1")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("signNo")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("enterDate")))).append(",").append(
									"'1','','','','Y','N',").append(
									Common.sqlChar(Common.get(rc.getString("area")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("holdNume")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("holdDeno")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("holdArea")))).append(",'").append(											
									setArea).append("',").append(									
									Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("registerNo1")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("rightKind")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("registerNo2")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("setRightScope")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("setObligor")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("landPurpose")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("doorPlate")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("buildingArea")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("buildingOwner")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("buildingPurpose")))).append(",'").append(
									Common.get(rc.getString("notes"))).append("由上傳作業輸入',").append(
									Common.sqlChar(getEditID()) ).append( "," ).append(
									Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
									Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;									
							}
							rc.getStatement().close();
							rc.close();							
						
						db.exeSQL(sbSQL.toString().split(":;:"));
						break;
						
					case 2: //權利增減單 UNTRT_AdjustProof, UNTRT_AdjustDetail & update UNTRT_AddProof, UNTRT_AddDetail									
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTRT_AdjustProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = " select propertyNo,serialNo,propertyName1,buyDate,sourceKind,meat,proofDoc1,holdNume1,holdDeno2,bookValue,registerCause,registerDate,setPeriod,commonObligee,setPerson,payDate,interest,rent,notes1 from UNTRT_AddProof " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));					
						rc = db.querySQL(sql);
						if (rc.next()) {
							
							sql = "select sign(sum(decode(adjustType1,'2',-bookValue,bookValue)),-1,'2','1') adjustType,sum(decode(adjustType1,'2',-bookValue,bookValue)) adjustBookValue from untup_right2 " +
								" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
								" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
								" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
								" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
								" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
								" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
								" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
								" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));
							
							double adjustBookValue = 0;
							String adjustType = "1";
							ResultSet rd = db.querySQL(sql);
							if (rd.next()) {
								adjustType = rd.getString("adjustType");
								adjustBookValue = rd.getDouble("adjustBookValue");
							}
							rd.getStatement().close();
							rd.close();							
							
							String op = "+";
							if (adjustType.equals("2")) op = "-";							
							
							sbSQL.append(" insert into UNTRT_AdjustProof (enterOrg,ownership,caseNo,caseName,propertyNo,serialNo,propertyName1,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,cause,cause1,adjustDate,bookNotes,verify,propertyKind,fundType,buyDate,sourceKind,meat,proofDoc1,holdNume1,holdDeno2,oldBookValue,adjustType,adjustBookValue,newBookValue,registerCause,registerDate,setPeriod,commonObligee,setPerson,payDate,interest,rent,notes1,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append(
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
								Common.sqlChar(Common.get(rc.getString("buyDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("sourceKind")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("meat")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("proofDoc1")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("holdNume1")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("holdDeno2")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",").append(
								Common.sqlChar(adjustType)).append(",'").append(
								adjustBookValue).append("',(").append(
								(rc.getDouble("bookValue"))).append(op).append(adjustBookValue).append("),").append(
								Common.sqlChar(Common.get(rc.getString("registerCause")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("registerDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("setPeriod")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("commonObligee")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("setPerson")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("payDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("interest")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("rent")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("notes1")))).append(",'").append(
								Common.get(rs.getString("notes"))).append("由上傳作業輸入',").append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
							
							//Select untup_right2 with untup_right1's key
							sql = " select enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,oldSerialNo1,signNo,area,holdNume,holdDeno,holdArea,adjustType1,bookValue,registerNo1,rightKind,registerNo2,setRightScope,setObligor,landPurpose,doorPlate,buildingArea,buildingOwner,buildingPurpose,notes,editID,editDate,editTime from untup_right2 " +
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
								
								sql = "serialNo1,signNo,setObligor,area,holdNume,holdDeno,holdArea,setArea,bookValue oldBookValue from UNTRT_AddDetail "+
									" where enterOrg=" + Common.sqlChar(Common.get(rd.getString("enterOrg"))) +
									" and ownership=" + Common.sqlChar(Common.get(rd.getString("ownership"))) +
									" and propertyKind=" + Common.sqlChar(Common.get(rd.getString("propertyKind"))) +
									" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rd.getString("fundType"))) + ",'99') " +
									" and oldSerialNo1=" + Common.sqlChar(Common.get(rd.getString("oldSerialNo1"))) +							
									" and oldPropertyNo=" + Common.sqlChar(Common.get(rd.getString("oldPropertyNo"))) +
									" and oldSerialNo =" + Common.sqlChar(Common.get(rd.getString("oldSerialNo")));	
								ResultSet re = db.querySQL(sql);
								if (re.next()) {							
									sbSQL.append(" insert into UNTRT_AdjustDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,serialNo1,oldSerialNo1,signNo,setObligor,area,holdNume,holdDeno,holdArea,setArea,oldBookValue,adjustType,adjustBookValue,newBookValue,notes,editID,editDate,editTime").append(
									" )Values(" ).append(
										Common.sqlChar(enterOrg) ).append( "," ).append(
										Common.sqlChar(Common.get(rd.getString("ownership"))) ).append( "," ).append(
										Common.sqlChar(caseNo)).append(",").append(
										Common.sqlChar(rc.getString("propertyNo"))).append(",'").append(												
										Common.sqlChar(rc.getString("serialNo"))).append(",'").append(
										re.getString("serialNo1")).append("',").append(												
										Common.sqlChar(Common.get(rd.getString("oldSerialNo1")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("signNo")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("setObligor")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("area")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("holdNume")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("holdDeno")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("holdArea")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("setArea")))).append(",").append(
										Common.sqlChar(Common.get(re.getString("bookValue")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("adjustType1")))).append(",").append(
										Common.sqlChar(Common.get(rd.getString("bookValue")))).append(",(").append(
										(re.getDouble("oldBookValue"))).append(op).append(rd.getDouble("bookValue")).append("),'").append(
										Common.get(rd.getString("notes"))).append("由上傳作業輸入',").append(
										Common.sqlChar(getEditID()) ).append( "," ).append(
										Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
										Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
									
									sbSQL.append(" update UNTRT_AddDetail set bookValue=(").append(re.getDouble("oldBookValue")).append(op).append(rd.getDouble("bookValue")).append(") ").append(								
										" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(									
										" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
										" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
										" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(												
										" and serialNo1=" ).append( Common.sqlChar(Common.get(re.getString("serialNo1")))).append(":;:");									
								}
								re.getStatement().close();
								re.close();
							}
							rd.getStatement().close();
							rd.close();
																					

							db.exeSQL(sbSQL.toString().split(":;:"));							
						} else {
							sFlag = true;
							setErrorMsg("查無增加資料！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_right1 set isErr='Y', errorColumn=errorColumn||'查無增加資料！(建議：在上傳的Excel檔案中依增加、增減值、減少等屬性排放資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
					case 3://減損		
						//取得caseNo					
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTRT_ReduceProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
						}
						rc.getStatement().close();
						rc.close();
						
						sql = "select propertyNo,serialNo,propertyName1,buyDate,sourceKind,meat,proofDoc1,holdNume1,holdDeno2,bookValue,registerCause,registerDate,setPeriod,commonObligee, " +
							" setPerson,payDate,interest,rent,notes1 from UNTRT_AddProof " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
							" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));						
						rc = db.querySQL(sql);
						if (rc.next()) {
							
							sql = "select sum(bookValue) reduceBookValue from untup_right2 " +
								" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
								" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
								" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
								" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
								" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
								" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
								" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
								" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));
								
							double reduceBookValue = 0;
							ResultSet rd = db.querySQL(sql);
							if (rd.next()) {
								reduceBookValue = rd.getDouble("reduceBookValue");
							}
							rd.getStatement().close();
							rd.close();																		
							
							sbSQL.append(" insert into UNTRT_ReduceProof(enterOrg,ownership,caseNo,caseName,propertyNo,serialNo,propertyName1,writeDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,cause,cause1,reduceDate,bookNotes,verify,propertyKind,fundType,buyDate,sourceKind,meat,proofDoc1,holdNume1,holdDeno2,oldBookValue,reduceBookValue,newBookValue,registerCause,registerDate,setPeriod,commonObligee,setPerson,payDate,interest,rent,notes1,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append(
								")Values(").append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownership"))) ).append( "," ).append(
								Common.sqlChar(caseNo) ).append( ",'上傳列號" ).append(
								rs.getString("textSerialNo")).append( "'," ).append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("propertyName1")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("writeDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("writeUnit")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("proofDoc")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("proofNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("manageNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("summonsNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("cause")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("cause1")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
								Common.sqlChar("Y")).append(",").append(
								Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("buyDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("sourceKind")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("meat")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("proofDoc1")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("holdNume1")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("holdDeno2")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",'").append(
								reduceBookValue).append("','").append(
								(rc.getDouble("bookValue")-reduceBookValue)).append("',").append(
								Common.sqlChar(Common.get(rc.getString("registerCause")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("registerDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("setPeriod")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("commonObligee")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("setPerson")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("payDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("interest")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("rent")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("notes1")))).append(",'").append(
								Common.get(rs.getString("notes"))).append("由上傳作業輸入',").append(
								Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(								
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");
							
							sbSQL.append(" update UNTRT_AddProof set bookValue='").append((rc.getDouble("bookValue")-reduceBookValue)).append(
								"' ").append(" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
								" and ownership=" ).append( Common.sqlChar(Common.get(rc.getString("ownership"))) ).append(
								" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
								" and serialNo =" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
							
							if ((rc.getDouble("bookValue")-reduceBookValue)<=0) {
								sbSQL.append(" update UNTRT_AddProof set dataState='2'").append(
								" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
								" and ownership=" ).append( Common.sqlChar(Common.get(rc.getString("ownership"))) ).append(
								" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
								" and serialNo =" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");								
							}
							
							sql = " select enterOrg,textSerialNo,ownership,propertyKind,fundType,enterDate,adjustType,oldPropertyNo,oldSerialNo,oldSerialNo1,signNo,area,holdNume,holdDeno,holdArea,adjustType1,bookValue,registerNo1,rightKind,registerNo2,setRightScope,setObligor,landPurpose,doorPlate,buildingArea,buildingOwner,buildingPurpose,notes,editID,editDate,editTime,errorColumn,isErr from untup_right2 " +
								" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
								" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
								" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
								" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
								" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
								" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
								" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
								" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));
							rd = db.querySQL(sql);
							while (rd.next()) {								
								sql = "select serialNo1,signNo,setObligor,area,holdNume,holdDeno,holdArea,setArea from UNTRT_AddDetail " +
									" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
									" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
									" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
									" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +							
									" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
									" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")))+								
									" and oldSerialNo1 =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo1")));								
								ResultSet re = db.querySQL(sql);
								if (re.next()) {
									sbSQL.append(" insert into UNTRT_ReduceDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,serialNo1,oldSerialNo1,signNo,setObligor,area,holdNume,holdDeno,holdArea,setArea,bookValue,notes,editID,editDate,editTime");
									sbSQL.append(")Values(").append(Common.sqlChar(enterOrg) ).append( "," ).append(
									Common.sqlChar(Common.get(rd.getString("ownership"))) ).append( "," ).append(
									Common.sqlChar(caseNo) ).append( 
									Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
									Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("serialNo1")))).append(",").append(
									Common.sqlChar(Common.get(rd.getString("oldSerialNo1")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("signNo")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("setObligor")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("area")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("holdNume")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("holdDeno")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("holdArea")))).append(",").append(
									Common.sqlChar(Common.get(re.getString("setArea")))).append(",").append(
									Common.sqlChar(Common.get(rd.getString("bookValue")))).append(",'").append(
									Common.get(rd.getString("notes"))).append("由上傳作業輸入',").append(							
									Common.sqlChar(getEditID()) ).append( "," ).append(
									Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
									Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");
									
									sbSQL.append(" update UNTRT_AddDetail set dataState='2', bookValue=0, ").append(
										", reduceDate=").append(Common.sqlChar(Common.get(rs.getString("enterDate")))).append(
										", reduceCause=").append(Common.sqlChar(Common.get(rs.getString("cause")))).append(										
										", reduceCause1=").append(Common.sqlChar(Common.get(rs.getString("cause1")))).append(										
										" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(										
										" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
										" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
										" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(												
										" and serialNo1=" ).append( Common.sqlChar(Common.get(re.getString("serialNo")))).append(":;:");											
								}
								re.getStatement().close();
								re.close();								
							}
							rd.getStatement().close();
							rd.close();							
							db.exeSQL(sbSQL.toString().split(":;:"));

						}
						rc.getStatement().close();
						rc.close();
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
				db.exeSQL(" delete from untup_right1 where enterOrg='"+enterOrg+"'");		
				db.exeSQL(" delete from untup_right2 where enterOrg='"+enterOrg+"'");				
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
