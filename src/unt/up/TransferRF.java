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

public class TransferRF extends UNTUP000Q {
	
	public void doRFUploadProcess(String enterOrg, java.util.Iterator it) {
		if (it!=null) {			
			Database db = new Database();
			try {
				db.exeSQL(" delete from untup_attachment where enterOrg="+Common.sqlChar(enterOrg));
				String[] rowArray = new String[39];
				int rowid = 2;
				int intSuccessRecord = 0;
				int intErrorRecord = 0;
				String preSQL = " insert into untup_attachment(enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,otherLimitYear,propertyName1,cause,cause1,taxCredit,measure,originalNote,accountingTitle,bookValue,fundsSource,buildDate,sourceKind,sourceDate,sourceDoc,useUnit,useUnit1,deprMethod,newEnterOrg,transferDate,unusableCause,demolishDate,WriteDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,notes,editID,editDate,editTime,errorColumn,isErr)Values(";
				//StringBuffer sbSQL = new StringBuffer(" delete from untup_attachment where enterOrg=").append(Common.sqlChar(enterOrg)).append(":;:");
				HashMap h = new HashMap();			
				while(it.hasNext()) {
					rowArray=(String[])it.next();						
					//Excel Meet Empty Cell, leave it..				
					if (Common.get(rowArray[0]).equals("") && Common.get(rowArray[1]).equals("")) break;
					
					boolean hashErr = false;
					StringBuffer sbSQL = new StringBuffer(600);					
					sbSQL.append(preSQL).append(Common.sqlChar(enterOrg)).append(",").append(rowid);
					StringBuffer sbErrMsg = new StringBuffer();	
					
					//檢查資料有無重覆。
					if (Common.get(rowArray[5]).equals("1")) {
						if (checkRecordExists("UNTRF_Attachment",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))) {
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
							if (checkRecordExists("UNTRF_Attachment",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))==false) {
					        	hashErr = true;
								sbErrMsg.append("無增加資料。\n");
							}
				        }
				        if (Common.get(rowArray[5]).equals("4")) {
							if (checkRecordExists("UNTRF_ReduceDetail",enterOrg,Common.get(rowArray[0]),Common.get(rowArray[1]),Common.get(rowArray[2]),Common.get(rowArray[3]),Common.get(rowArray[6]),Common.get(rowArray[7]))) {
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
					
					for (int i=0; i<39; i++) {
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
							sbErrMsg.append(checkRFField(i, Common.get(rowArray[i])));
							
							if ("4".equals(Common.get(rowArray[i]))) {
								if ("01".equals(Common.get(rowArray[13]))||"07".equals(Common.get(rowArray[13]))){
									if ("".equals(Common.get(rowArray[39]))) {
										sbErrMsg.append("接管機關不能為空值。\n");
									} else {
										if ("".equals(getOrganSName(Common.get(rowArray[39])))) {
											sbErrMsg.append("查無此接管機關代碼。\n");
										}
									}
									if ("".equals(Common.get(rowArray[26]))) {
										sbErrMsg.append("交接日期不能為空值。\n");
									} else {
										if (!Validate.checkDate(Common.get(rowArray[26]))) {
											sbErrMsg.append("交接日期必須為(YYYMMDD)格式。\n");
										}
									}
								}
							} else {
								if (!"".equals(Common.get(rowArray[25])) || !"".equals(Common.get(rowArray[26]))) {
									sbErrMsg.append("接管機關代碼及交接日期不能有資料。\n");	
								}
							}
							break;						
							
						case 10:
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
							
						case 11:
							if (Common.get(rowArray[5]).equals("1")||Common.get(rowArray[5]).equals("4")) {
								if (!"99".equals(Common.get(rowArray[i-1]))) {
									//if (!"".equals(rowArray[i])) sbErrMsg.append("登帳原因非為其他時，其他說明不能有資料。\n");
									rowArray[i]="";
								} else {
									if ("".equals(rowArray[i])) {
										sbErrMsg.append("登帳原因若為其他時，其他說明必須要有資料。\n");
									} else {
										sbErrMsg.append(checkRFField(i, Common.get(rowArray[i])));
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
										sbErrMsg.append(checkRFField(i, Common.get(rowArray[i])));
									}
								}							
							}						
							break;
							
						case 22:
							if (rowArray[i].equals("") && rowArray[23].equals("")) {
								sbErrMsg.append("使用單位代碼與非機關使用單位必須擇一輸入。\n");
							} else if (!rowArray[i].equals("") && rowArray[23].equals("")){
								if (getOrganSName(rowArray[i]).equals("")) {
									sbErrMsg.append("查該此 ").append(rowArray[i] + " 使用單位代碼。\n");									
								}
							}
							break;
							
						default:
							sbErrMsg.append(checkRFField(i, Common.get(rowArray[i])));						
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
		
	public String checkRFField(int i, String s) {
		String[] arrFieldName = new String[]{"權屬代碼","財產性質代碼","基金財產代碼","珍貴財產代碼","登帳日期","增減別代碼","原財產編號","原財產分號","使用年限","財產別名","登帳原因代碼","登帳原因－其他說明","抵繳遺產稅代碼","計量數","帳務摘要","會計科目","價值","經費來源代碼","建築日期","財產來源－種類代碼","財產來源－取得日期","財產來源－取得文號","使用單位代碼","非機關使用單位","折舊方法代碼","接管機關代碼","交接日期","報廢拆除原因","拆除日期","填單日期","填造單位","單據編號－字","單據編號－號","財產管理單位編號","傳票號數","核准機關代碼","核准日期","核准文號","備註"};
		try {
			switch (i) {
				case 0:
					if (s.equals("1")||s.equals("2")) {} 
					else return "權屬代碼只能是1或2。\n";					
					break;
				case 1:
					if (!checkPropertyKind(s)) return "財產性質必須是01,02,03或04。\n";					
					break;
					
				case 3: case 12:
					if (!Validate.checkYN(s)) {					
						return arrFieldName[i]+"，請輸入Y或N。";								
					}
					break;
					
				case 4: case 18: case 20: case 26: case 28: case 29: case 36:							
					if (!"".equals(Validate.checkDate(s, arrFieldName[i]))) {
						return Validate.checkDate(s, arrFieldName[i])+"\n";
					}
					if (i==4 | i==18) {
						if ( s.equals("")) return arrFieldName[i]+"不能空白。\n";
						else if ((i==4) && ((Double.parseDouble(getMaxClosingYM(enterOrg))) > (Double.parseDouble(s.substring(0,5))))){
							return "入帳年月必須大於月結年月！\n";
						}
					}
					break;
					
				case 5:							
					if ((!"".equals(s) && Validate.checkInt(s)) && (Integer.parseInt(s)>0 || Integer.parseInt(s)<5)) {}
					else return "增減別代碼只能是1,2,3,4。\n";					
					break;
					
				case 6:
					if (!"".equals(s) && s.length()>1 && s.substring(0,3).equals("111")) {
						if ("".equals(getPropertyName(enterOrg,s))) {						
							return "查無此 "+s+" 原財產編號。\n";
						}						
					} else return "原財產編號不能為空值且開頭字元必須為111。\n";
					break;
				
				case 7:
					if (s.equals("") || s.length()>7) return "原財產分號錯誤(空值或長度超過7碼)。\n";					
					break;
					
				case 8:
					if (!Validate.checkInt(s) || s.length()>3) return "使用年限需為正整數且必須<=999。\n";
					
				case 9: case 11: case 14: case 21: case 37:
					if (s.length()>20) return arrFieldName[i]+"已超過20個字。\n";
					break;
					
				case 13:
					if (!"".equals(s) && Function.getNumeric(s)>=0) {
						if (!Validate.checkFloat(s, 7, 2)) {							
							return arrFieldName[i]+"整數部分不可大於7位，小數部份不可大於2位。";								
						} else if (Double.parseDouble(s)<0){							
							return arrFieldName[i]+"必須>=0。\n";									
						}
					} else {
						return arrFieldName[i] + " 必須為 >= 0 的正整數值。\n";
					}
					break;

				case 15:
					if (s.length()>20) return  arrFieldName[i] + "已超過20個字。\n";	
					break;

				//case 15: case 31: case 32: case 33:
				case 31: case 32: case 33:
					if (s.length()>10) return  arrFieldName[i] + "已超過10個字。\n";	
					else if (("".equals(s)) && (i==31 || i==32)) return arrFieldName[i]+"不能為空值。\n";
					break;
					
				case 16:
					if ("".equals(s) || Function.getNumeric(s)<0 || !Validate.checkInt(s) || s.length()>15) return arrFieldName[i] + " 必須為 >= 0 的正整數值。\n";
					break;
					
				case 17:
					if (!"".equals(s) && getCodeName("FSO",s).equals("")) return arrFieldName[i]+"　查無該代碼資料。\n";
					break;
					
				case 19:			
					if (!"".equals(s) && getCodeName("SKB",s).equals("")) return arrFieldName[i]+"　查無該代碼資料。\n";
					break;
					
				case 23: case 30: case 34:
					if (s.length()>15) return arrFieldName[i] + "已超過15個字。\n";					
					break;												

				case 24:
					if ("".equals(s) || getCodeName("DEP",s).equals("")) return "拆舊方法代碼必須為01,02,03,04或05。\n";
					break;	
					
				case 25:
					if (!"".equals(s) && getOrganSName(s).equals("")) {
						return "查無該 " + arrFieldName[i] + " 。\n";
					}
					break;
				
				case 27:
					if (s.length()>50) return arrFieldName[i] +"已超過 50個字元。\n";
					break;		
					
				case 35:
					if (!"".equals(s) && getCodeName("APO",s).equals("")) return "查無該" + arrFieldName[i] + "。\n";
					break;
					
				case 38:
					if (s.length()>250) return arrFieldName[i] + "已超過250個字。\n";					
					break;						
										
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "錯誤\n";			
		}			
	}
	
	
	public void doRFTransfer(String enterOrg) throws Exception {
		Database db = new Database();
		try {
			db.setAutoCommit(false);
			String sql = "";
			ResultSet rc;
			boolean sFlag = false;
			ResultSet rs = db.querySQL("select enterOrg,textSerialNo,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo,otherLimitYear,propertyName1,cause,cause1,taxCredit,measure,originalNote,accountingTitle,bookValue,fundsSource,buildDate,sourceKind,sourceDate,sourceDoc,useUnit,useUnit1,deprMethod,newEnterOrg,transferDate,unusableCause,demolishDate,WriteDate,writeUnit,proofDoc,proofNo,manageNo,summonsNo,approveOrg,approveDate,approveDoc,notes,editID,editDate,editTime,errorColumn,isErr from untup_attachment where enterOrg="+Common.sqlChar(enterOrg)+" order by enterOrg,ownership,propertyKind,fundType,valuable,enterDate,adjustType,oldPropertyNo,oldSerialNo");
			while (rs.next()) {
				StringBuffer sbSQL = new StringBuffer(500);			
				String caseNo = Common.get(rs.getString("enterDate")).substring(0,5)+"00001";					
				if (sFlag==false) {
					switch (rs.getInt("adjustType")) {
					case 1: //增加					
						String serialNo="0000001";
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTRF_AddProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));
						}
						rc.getStatement().close();
						rc.close();
						
						//取得SerialNo
						sql="select substr(nvl(max(serialNo),0)+10000001,2) as serialNo from UNTRF_Attachment " +
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
								db1.exeSQL("update untup_attachment set isErr='Y', errorColumn='入帳年月必須大於月結年月！(建議：請不要使用上傳作業上傳後，又使用本系統畫面登打資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
						sql=" select count(*) as checkResult from UNTRF_Attachment where 1=1 " + 
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
								db1.exeSQL("update untup_attachment set isErr='Y', errorColumn='資料己重複！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						String propertyUnit = "", material = "", limitYear = "";
						sql="select propertyUnit, material, limitYear from SYSPK_PropertyCode " +
							" where enterOrg in ('000000000A'," + Common.sqlChar(enterOrg) + ") and propertyType='1' " +
							" and substr(propertyNo,1,3)='111' and propertyNo = " + Common.sqlChar(rs.getString("oldpropertyNo"));
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
								db1.exeSQL("update untup_attachment set isErr='Y', errorColumn='查無此" +rs.getString("oldPropertyNo")+ " 財產編號！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
								db1.exeSQL("update untup_attachment set isErr='Y', errorColumn='拆舊方法必須是01,02,03,04或05！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						
						sbSQL.append(" insert into UNTRF_AddProof(" ).append(
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
						
						sbSQL.append(" insert into UNTRF_Attachment(enterOrg,ownership,caseNo,propertyNo,serialNo,otherLimitYear,propertyName1,cause,cause1,enterDate,dataState,reduceDate,reduceCause,reduceCause1,verify,closing,propertyKind,fundType,valuable,taxCredit,originalMeasure,measure,originalBV,originalNote,accountingTitle,bookValue,fundsSource,buildDate,sourceKind,sourceDate,sourceDoc,useUnit,useUnit1,damageDate,damageExpire,damageMark,deprMethod,isAccumDepr,scrapValue,deprAmount,apportionYear,monthDepr,useEndYM,apportionEndYM,accumDeprYM,accumDepr,permitReduceDate,appraiseDate,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append(
							" )Values(" ).append(
							Common.sqlChar(enterOrg) ).append( "," ).append(
							Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
							Common.sqlChar(caseNo)).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
							Common.sqlChar(serialNo)).append(",");
						
							if (limitYearFlag) sbSQL.append(Common.sqlChar(Common.get(rs.getString("otherLimitYear")))).append( "," );
							else sbSQL.append("''," );
						sbSQL.append(Common.sqlChar(Common.get(rs.getString("propertyName1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
							"'1','','','','Y','N',").append(
							Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("valuable")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("taxCredit")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("measure")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("measure")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("accountingTitle")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("fundsSource")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("buildDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceKind")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceDate")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("sourceDoc")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("useUnit")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("useUnit1")))).append(",").append(
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
							"'',").append(
							Common.sqlChar(Common.get(rs.getString("notes")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldPropertyNo")))).append(",").append(
							Common.sqlChar(Common.get(rs.getString("oldSerialNo")))).append(",").append(
							Common.sqlChar(getEditID()) ).append( "," ).append(
							Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
							Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:") ;
						
						db.exeSQL(sbSQL.toString().split(":;:"));
						break;
						
					case 2: case 3: //土地增減單 UNTRF_AdjustProof, UNTRF_AdjustDetail & update UNTRF_Attachment
						String op = "+";
						if (rs.getInt("adjustType")==2) op = "+";
						else if (rs.getInt("adjustType")==3) op = "-";			
						
						//取得caseNo
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTRF_AdjustProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							//caseNo = (Common.get(rs.getString("enterDate"))+Common.formatFrontZero(rc.getString("caseNo"),3));
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = " select propertyNo,serialNo,propertyName1,originalBV,sourceDate,measure,bookValue from UNTRF_Attachment " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and nvl(valuable,'N') = nvl('" + Common.get(rs.getString("valuable")) + "','N') " +
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));					
						rc = db.querySQL(sql);
						if (rc.next()) {
							sbSQL.append(" insert into UNTRF_AdjustProof (" ).append(
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
								
							sbSQL.append(" insert into UNTRF_AdjustDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,propertyName1,cause,cause1,adjustDate,verify,propertyKind,fundType,valuable,taxCredit,originalBV,sourceDate,oldMeasure,oldBookValue,adjustType,adjustMeasure,adjustBookValue,newMeasure,newBookValue,bookNotes,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append( 
								" )Values(" ).append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
								Common.sqlChar(caseNo)).append(",").append(
								Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("propertyName1")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("cause")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("cause1")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("enterDate")))).append(",").append(
								Common.sqlChar("Y")).append(",").append(
								Common.sqlChar(Common.get(rs.getString("propertyKind")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("fundType")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("valuable")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("taxCredit")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("originalBV")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("sourceDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("measure")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",").append(
								" decode('").append(rs.getString("adjustType")).append("','2','1','2'), ").append(
								Common.sqlChar(Common.get(rs.getString("measure")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",");
							
							if (Common.get(rs.getString("cause")).equals("1")) {
								sbSQL.append(Common.sqlChar(Common.get(rc.getString("measure")))).append(",");
								sbSQL.append(Common.sqlChar(Common.get(rc.getString("bookValue")))).append(",");								
							} else if (Common.get(rs.getString("cause")).equals("2")||Common.get(rs.getString("cause")).equals("3")) {
								sbSQL.append("(").append(rc.getString("measure")).append(op).append(rs.getString("measure")).append( "),(" ).append(			
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
								sbSQL.append(" update UNTRF_Attachment set measure='").append(rc.getString("measure")).append(										
									"', appraiseDate=" ).append(Common.sqlChar(Common.get(rs.getString("enterDate")))).append(									
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");
							} else {				
								sbSQL.append(" update UNTRF_Attachment set measure=(").append(
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
								db1.exeSQL("update untup_attachment set isErr='Y', errorColumn=errorColumn||'查無增加資料！(建議：在上傳的Excel檔案中依增加、增減值、減少等屬性排放資料。)\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
						sql="select max(substr(caseNo,6))+1 as caseNo from UNTRF_ReduceProof where enterOrg = " + Common.sqlChar(rs.getString("enterOrg")) +
							" and ownership = " + Common.sqlChar(rs.getString("ownership")) +
							" and substr(caseNo,1,5) = " + Common.sqlChar(Common.get(rs.getString("enterDate")).substring(0,5));
						rc = db.querySQL(sql);
						if (rc.next() && rc.getInt(1)>0){
							//caseNo = (Common.get(rs.getString("enterDate"))+Common.formatFrontZero(rc.getString("caseNo"),3));
							caseNo = (Common.get(rs.getString("enterDate")).substring(0,5)+Common.formatFrontZero(rc.getString("caseNo"),5));							
						}
						rc.getStatement().close();
						rc.close();
						
						sql = " select propertyNo,serialNo,otherLimitYear,propertyName1,accountingTitle,buildDate, sourceDate, substr('" + rs.getString("enterDate") + "',1,3) - substr(buildDate,1,3) +  decode(sign(substr('" +rs.getString("enterDate") + "',4,4)- " +
							" substr(buildDate,4,4)),-1,-1,0) useYear, MOD( MONTHS_BETWEEN( to_date(substr('" +rs.getString("enterDate") + "',1,5)|| " +
							" '01'+19110000,'YYYYMMDD') ,to_date(substr(buildDate,1,5)||'01'+19110000,'YYYYMMDD') ) ,12 ) useMonth, deprMethod,deprAmount,monthDepr,apportionEndYM,accumDeprYM, " +
							" accumDepr,permitReduceDate from UNTRF_Attachment " +
							" where enterOrg=" + Common.sqlChar(Common.get(rs.getString("enterOrg"))) +
							" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
							" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
							" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
							" and nvl(valuable,'N') = nvl('" + Common.get(rs.getString("valuable")) + "','N') " +
							" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
							" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo")));					
						rc = db.querySQL(sql);
						if (rc.next()) {
							sbSQL.append(" insert into UNTRF_ReduceProof(" ).append(
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
								" unusableCause,").append(
								" demolishDate,").append(										
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
								Common.sqlChar(Common.get(rs.getString("unusableCause"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("demolishDate"))) ).append( "," ).append(												
								Common.sqlChar(Common.get(rs.getString("enterDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("approveOrg"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("approveDate"))) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("approveDoc"))) ).append( "," ).append(  
								Common.sqlChar("Y") ).append( "," ).append(
								Common.sqlChar("由上傳作業輸入") ).append( "," ).append(
								Common.sqlChar(getEditID()) ).append( "," ).append(
								Common.sqlChar(Datetime.getYYYMMDD()) ).append( "," ).append(
								Common.sqlChar(Datetime.getHHMMSS()) ).append( "):;:");		
							
							sbSQL.append(" insert into UNTRF_ReduceDetail(enterOrg,ownership,caseNo,propertyNo,serialNo,otherLimitYear,propertyName1,cause,cause1,reduceDate,newEnterOrg,transferDate,verify,propertyKind,fundType,valuable,taxCredit,measure,bookNotes,accountingTitle,bookValue,buildDate,sourceDate,useYear,useMonth,deprMethod,deprAmount,monthDepr,apportionEndYM,accumDeprYM,accumDepr,permitReduceDate,accumDepr1,scrapValue1,submitCityGov,reduceDeal,notes,oldPropertyNo,oldSerialNo,editID,editDate,editTime").append( 
								" )Values(" ).append(
								Common.sqlChar(enterOrg) ).append( "," ).append(
								Common.sqlChar(Common.get(rs.getString("ownership")))).append(",").append(
								Common.sqlChar(caseNo)).append(",").append(
								Common.sqlChar(Common.get(rc.getString("propertyNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("serialNo")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("otherLimitYear")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("propertyName1")))).append(",").append(
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
								Common.sqlChar(Common.get(rs.getString("measure")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("originalNote")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("accountingTitle")))).append(",").append(
								Common.sqlChar(Common.get(rs.getString("bookValue")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("buildDate")))).append(",").append(
								Common.sqlChar(Common.get(rc.getString("sourceDate")))).append(",").append(
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
						
							sbSQL.append(" update UNTRF_Attachment set dataState='2', measure=0, bookValue=0 ").append(
									", reduceDate=").append(Common.sqlChar(Common.get(rs.getString("enterDate")))).append(
									", reduceCause=").append(Common.sqlChar(Common.get(rs.getString("cause")))).append(										
									", reduceCause1=").append(Common.sqlChar(Common.get(rs.getString("cause1")))).append(										
									" where enterOrg=" ).append( Common.sqlChar(Common.get(rs.getString("enterOrg"))) ).append(										
									" and ownership=" ).append( Common.sqlChar(Common.get(rs.getString("ownership"))) ).append(
									" and propertyNo=" ).append( Common.sqlChar(Common.get(rc.getString("propertyNo"))) ).append(
									" and serialNo=" ).append( Common.sqlChar(Common.get(rc.getString("serialNo")))).append(":;:");						
							
							String[] arr = sbSQL.toString().split(":;:");
							for (int i=0; i<arr.length; i++) System.out.println(arr[i]);							
							db.exeSQL(sbSQL.toString().split(":;:"));
						} else {
							sFlag = true;
							setErrorMsg("查無增加資料！");
							util.Database db1 = new util.Database();						
							try {
								db1.exeSQL("update untup_attachment set isErr='Y', errorColumn=errorColumn||'查無該筆土地資料！\n' where enterOrg='"+enterOrg+"' and textSerialNo="+rs.getString("textSerialNo"));
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
				db.exeSQL(" delete from untup_attachment where enterOrg='"+enterOrg+"'");		
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
