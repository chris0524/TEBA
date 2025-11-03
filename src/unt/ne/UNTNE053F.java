/*
*<br>程式目的：物品主檔資料維護－接收撥入物品資料
*<br>程式代號：untne053f
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import util.Common;
import util.*;

public class UNTNE053F extends UNTNE053Q{
	
	UNTNE053F_Data data =new UNTNE053F_Data();
	
	public ArrayList queryAll_Dn() throws Exception{
		Database db = new Database();
		ArrayList objList=new ArrayList();
		int counter=0;
		
		String sql=" select" +
						" a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.lotNo, " +
						" a.serialNo, b.propertyName, a.propertyName1, a.adjustBookAmount, a.adjustBookValue, " +
						" nvl(b.propertyUnit, a.otherPropertyUnit) as propertyUnit, " +
						" nvl(b.material    , a.otherMaterial    ) as material," +
						" nvl(b.limitYear   , a.otherLimitYear   ) as limitYear" +
					" from UNTNE_ReduceDetail a, SYSPK_PropertyCode2 b" +
					" where 1=1" +
						" and a.enterOrg   = b.enterOrg(+)" +
						" and a.propertyNo = b.propertyNo(+)" +
						" and a.newEnterOrg = " + Common.sqlChar(getEnterOrg());
		
		if (!"".equals(getQ_oldEnterOrg())) {		sql+=" and a.enterOrg = " + Common.sqlChar(getQ_oldEnterOrg()) ;}
		if (!"".equals(getQ_ownership())) {			sql+=" and a.ownership = " + Common.sqlChar(getQ_ownership()) ;}
		if (!"".equals(getQ_caseNo())) {			sql+=" and a.caseNo = " + Common.sqlChar(getQ_caseNo()) ;}
		
		if (!"".equals(getQ_newEnterOrgReceive())) {
			
		}else{
			sql+=" and (a.newEnterOrgReceive = 'N' or a.newEnterOrgReceive is null)";			
		}
		
		
		sql+=" order by a.enterOrg, a.ownership, a.caseNo, a.propertyNo, a.lotNO, a.serialNo ";
	
		try {
			log.outputLogDebug(sql);
			
			ResultSet rs = db.querySQL(sql);
			while (rs.next()){
				counter++;
				String rowArray[]=new String[13];
				
				rowArray[0]=Common.get(rs.getString("enterOrg")); 
				rowArray[1]=Common.get(rs.getString("ownership"));
				rowArray[2]=Common.get(rs.getString("caseNo"));
				rowArray[3]=Common.get(rs.getString("propertyNo"));
				rowArray[4]=Common.get(rs.getString("lotNo"));
				rowArray[5]=Common.get(rs.getString("serialNo"));
				rowArray[6]=Common.get(rs.getString("propertyName"));
				rowArray[7]=Common.get(rs.getString("propertyName1"));
				rowArray[8]=Common.get(rs.getString("adjustBookAmount"));
				rowArray[9]=Common.get(rs.getString("adjustBookValue"));
				rowArray[10]=Common.get(rs.getString("propertyUnit"));
				rowArray[11]=Common.get(rs.getString("material"));
				rowArray[12]=Common.get(rs.getString("limitYear"));
				
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			log.outputLogError("Exception : "+e.getMessage()+"\n"+sql);
			
		} finally {
			db.closeAll();
		}
		return objList;
	}
	
	//==========================================================================
	
	public void InsertProcedure(){
	
		String[][] resultArray = containData();
		
		java.util.ArrayList PKList = new java.util.ArrayList(); 
		String PKStr = "";
		
		//同一個「入帳機關(enterOrg)＋權屬(ownership)
		//		＋電腦單號(caseNo)＋物品編號(propertyNo)
		//		＋物品批號(lotNo)＋減少帳面金額(總價)(adjustBookValue)」
		//視為同一批

		int endCount = resultArray.length;
		for(int i=0;i<=endCount;i++){
			if(i==0){									PKStr = resultArray[i][0].toString();
														PKList.clear();
														PKList.add(resultArray[i][1].toString());
			}else if(i==endCount){					
														insertIntoUNTNE_NONEXPDETAIL(PKStr, PKList);
														obtainNewSerialNoS();
														obtainNewSerialNoE();
														insertIntoUNTNE_NONEXP(PKStr);
			}else{
				if(PKStr.equals(resultArray[i][0].toString())){	
														PKList.add(resultArray[i][1].toString());
				
				}else{									insertIntoUNTNE_NONEXPDETAIL(PKStr, PKList);
														obtainNewSerialNoS();
														obtainNewSerialNoE();
														insertIntoUNTNE_NONEXP(PKStr);
														PKStr = resultArray[i][0].toString();
														PKList.clear();
														PKList.add(resultArray[i][1].toString());
				}
			}
		}
	}
				
					private String[][] containData(){
						java.util.Arrays.sort(strKeySet);
						
						String[][] resultArray = new String[strKeySet.length][2];
						int rowCount = 0;
						
						for(int i=0;i<strKeySet.length;i++){
							resultArray[rowCount] = strKeySet[i].toString().split(";");
							rowCount++;
						}
						
						return resultArray;
					}
					
					
				
			private void insertIntoUNTNE_NONEXPDETAIL(String PKStr, java.util.ArrayList PKList){
				String[] PKArray = PKStr.split(",");
			
				data.enterOrg 		= PKArray[0].toString();
				data.ownership		= PKArray[1].toString();
				data.caseNo			= PKArray[2].toString();
				data.propertyNo		= PKArray[3].toString();
				data.lotNo			= PKArray[4].toString();
				java.util.Iterator iter = PKList.iterator();
				String sql="";
				while(iter.hasNext()){
					data.serialNo = iter.next().toString();
					
					sql = createSQL_UNTNE_NONEXPDETAIL();
					execSQL_ForNoResult(sql);
					
					sql = createSQL_updateToUNTNE_REDUCEDETAIL_ByOldProperty();
					execSQL_ForNoResult(sql);					
				}
			}
			
				private String createSQL_UNTNE_NONEXPDETAIL(){
					String sql = "insert into UNTNE_NONEXPDETAIL(" +
									" enterOrg, ownership, propertyNo, lotNo, serialNo," +
									" dataState, reduceDate, reduceCause, reduceCause1, verify," +
									" closing, originalAmount, originalBV, bookAmount, bookValue," +
									" licensePlate, purpose, originalMoveDate, originalKeepUnit, originalKeeper," +
									" originalUseUnit, originalUser, originalPlace, moveDate, keepUnit," +
									" keeper, useUnit, userNo, place, damageDate," +
									" damageExpire, damageMark, notes1, notes, oldPropertyNo," +
									" oldSerialNo, editID, editDate, editTime, barCode," +
									" checkDateS, checkDateE, checkResult, oddsCause" +
								" )Values(" +
									Common.sqlChar(this.getEnterOrg()) + "," +
									Common.sqlChar(this.getOwnership()) + "," +
									Common.sqlChar(this.getUp_propertyNo()) + "," +
									Common.sqlChar(obtainNewLotNo()) + "," +
									Common.sqlChar(ontainNewSerialNo()) + "," +
									"'1'," +
									"null," +
									"null," +
									"null," +
									"'N'," +
									"'N'," +
									querySQLfromUntne_ReduceDetail("adjustBookAmount") + "," +
									querySQLfromUntne_ReduceDetail("adjustBookValue") + "," +
									querySQLfromUntne_ReduceDetail("adjustBookAmount") + "," +
									querySQLfromUntne_ReduceDetail("adjustBookValue") + "," +
									querySQLfromUntne_ReduceDetail("licensePlate") + "," +
									"null," +
									Common.sqlChar(this.getUp_originalMoveDate()) + "," +
									Common.sqlChar(this.getUp_originalKeepUnit()) + "," +
									Common.sqlChar(this.getUp_originalKeeper()) + "," +
									Common.sqlChar(this.getUp_originalUseUnit()) + "," +
									Common.sqlChar(this.getUp_originalUser()) + "," +
									Common.sqlChar(this.getUp_originalPlace()) + "," +
									Common.sqlChar(this.getUp_originalMoveDate()) + "," +
									Common.sqlChar(this.getUp_originalKeepUnit()) + "," +
									Common.sqlChar(this.getUp_originalKeeper()) + "," +
									Common.sqlChar(this.getUp_originalUseUnit()) + "," +
									Common.sqlChar(this.getUp_originalUser()) + "," +
									Common.sqlChar(this.getUp_originalPlace()) + "," +
									"null," +
									"null," +
									"null," +
									"null," +
									"null," +
									"null," +
									"null," +
									Common.sqlChar(this.getEditID()) + "," +
									Common.sqlChar(this.getEditDate()) + "," +
									Common.sqlChar(this.getEditTime()) + "," +
									Common.sqlChar(obtainBarCode()) + "," +
									"null," +
									"null," +
									"null," +
									"null" +
								")";
					return sql;
				}
				
				private String createSQL_updateToUNTNE_REDUCEDETAIL_ByOldProperty(){
					String sql="update UNTNE_ReduceDetail set" +
								" newEnterOrgReceive = 'Y'" +
								" where 1=1" +
								" and enterOrg = " + Common.sqlChar(data.enterOrg) +
								" and ownership = " + Common.sqlChar(data.ownership) +
								" and caseNo = " + Common.sqlChar(data.caseNo) +
								" and propertyNo = " + Common.sqlChar(data.propertyNo) +
								" and serialNo = " + Common.sqlChar(data.serialNo) ;
					return sql;
				}
				
			private void insertIntoUNTNE_NONEXP(String PKStr){
				String sql = createSQL_UNTNE_NONEXP(data);
				execSQL_ForNoResult(sql);				
			}
							
				private String createSQL_UNTNE_NONEXP(UNTNE053F_Data data){
					String sql="insert into Untne_Nonexp(" +
									"enterOrg, ownership, caseNo, propertyNo, lotNo," +
									"serialNoS, serialNoE, otherPropertyUnit, otherMaterial, otherLimitYear," +
									"propertyName1, cause, cause1, approveDate, approveDoc," +
									"enterDate, buyDate, dataState, verify, closing," +
									"propertyKind, fundType, valuable, originalAmount, originalUnit," +
									"originalBV, originalNote, accountingTitle, bookAmount, bookValue," +
									"fundsSource, grantValue, articleName, nameplate, specification," +
									"storeNo, sourceKind, sourceDate, sourceDoc, permitReduceDate," +
									"oldPropertyNo, oldSerialNoS, oldSerialNoE, notes, editID," +
									"editDate, editTime, fundsSource1" +
								")Values(" +
									Common.sqlChar(this.getEnterOrg()) + "," +
									Common.sqlChar(this.getOwnership()) + "," +
									Common.sqlChar(this.getCaseNo()) + "," +
									Common.sqlChar(this.getUp_propertyNo()) + "," +
									Common.sqlChar(data.NewLotNo) + "," +
									Common.sqlChar(data.NewserialNoS) + "," +
									Common.sqlChar(data.NewserialNoE) + "," +
									Common.sqlChar(this.getUp_otherPropertyUnit()) + "," +
									Common.sqlChar(this.getUp_otherMaterial()) + "," +
									Common.sqlChar(this.getUp_otherLimitYear()) + "," +
									querySQLfromUntne_ReduceDetail("propertyName1") + "," +
									Common.sqlChar(this.getUp_cause()) + "," +
									"null," +
									"null," +
									"null," +
									Common.sqlChar(this.getEnterDate()) + "," +
									querySQLfromUntne_ReduceDetail("buyDate") + "," +
									"'1'," +
									"'N'," +
									"'N'," +
									Common.sqlChar(this.getUp_propertyKind()) + "," +
									Common.sqlChar(this.getUp_fundType()) + "," +
									querySQLfromUntne_ReduceDetail("valuable") + "," +
									querySQLfromUntne_NonexpDetail("BookAmount") + "," +
									querySQLfromUntne_ReduceDetail("oldBookUnit") + "," +
									querySQLfromUntne_NonexpDetail("BookValue") + "," +
									"null," +
									"null," +
									querySQLfromUntne_NonexpDetail("BookAmount") + "," +
									querySQLfromUntne_NonexpDetail("BookValue") + "," +
									"null," +
									"null," +
									querySQLfromUntne_ReduceDetail("articleName") + "," +
									querySQLfromUntne_ReduceDetail("nameplate") + "," +
									querySQLfromUntne_ReduceDetail("specification") + "," +
									"null," +
									"(select sourceKind from Untne_Nonexp where enterOrg = '"+data.enterOrg+"' and ownership = '"+data.ownership+"' and propertyNo = '"+data.propertyNo+"' and lotNo = '"+data.lotNo+"'), "+
									"(select sourceDate from Untne_Nonexp where enterOrg = '"+data.enterOrg+"' and ownership = '"+data.ownership+"' and propertyNo = '"+data.propertyNo+"' and lotNo = '"+data.lotNo+"'), "+
									"(select sourceDoc from Untne_Nonexp where enterOrg = '"+data.enterOrg+"' and ownership = '"+data.ownership+"' and propertyNo = '"+data.propertyNo+"' and lotNo = '"+data.lotNo+"'), "+
									querySQLfromUntne_ReduceDetail("buyDate + nvl('" + getUp_limitYear() + "','" + getUp_otherLimitYear() + "')*10000") + "," +
									"null," +
									"null," +
									"null," +
									"null," +
									Common.sqlChar(this.getEditID()) + "," +
									Common.sqlChar(this.getEditDate()) + "," +
									Common.sqlChar(this.getEditTime()) + "," +
									"null" +
								")";
					
					
					return sql;
				}
				
			

						private String obtainNewLotNo(){
							String sql="select lpad(nvl(max(lotNo),0)+1,7,0) as lotNo from UNTNE_Nonexp " +
										" where enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
										" and ownership = " + Common.sqlChar(this.getOwnership()) + 
										" and propertyNo = " + Common.sqlChar(this.getUp_propertyNo()) + 
										"";
							data.NewLotNo = getNameData("LOTNO",sql); 
							return data.NewLotNo;
						}
						
						private String ontainNewSerialNo(){
							String sql="select lpad(nvl(max(serialNo),0)+1,7,0) as serialNo from UNTNE_NonexpDetail " +
										" where enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
										" and ownership = " + Common.sqlChar(this.getOwnership()) + 
										" and propertyNo = " + Common.sqlChar(this.getUp_propertyNo()) + 
										"";							
							return getNameData("SERIALNO",sql);
						}
												
						private void obtainNewSerialNoS(){
							String sql="select min(serialNo) as SERIALNO from Untne_NonexpDetail" +
										" where enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
										" and ownership = " + Common.sqlChar(this.getOwnership()) + 
										" and propertyNo = " + Common.sqlChar(this.getUp_propertyNo()) + 
										" and lotNo = " + Common.sqlChar(data.NewLotNo) +
										"";						
							data.NewserialNoS = getNameData("SERIALNO",sql);
						}
						
						private void obtainNewSerialNoE(){
							String sql="select max(serialNo) as SERIALNO from Untne_NonexpDetail" +
										" where enterOrg = " + Common.sqlChar(this.getEnterOrg()) + 
										" and ownership = " + Common.sqlChar(this.getOwnership()) + 
										" and propertyNo = " + Common.sqlChar(this.getUp_propertyNo()) + 
										" and lotNo = " + Common.sqlChar(data.NewLotNo) +
										"";												
							data.NewserialNoE = getNameData("SERIALNO",sql);
						}
						
						private String querySQLfromUntne_ReduceDetail(String ValueName){
							String str="(select " + ValueName + " from Untne_ReduceDetail " + 
										" where 1=1" +
										" and enterOrg = " + Common.sqlChar(data.enterOrg) +
										" and ownership = " + Common.sqlChar(data.ownership) +									
										" and propertyNo = " + Common.sqlChar(data.propertyNo) +
										" and lotNo = " + Common.sqlChar(data.lotNo) +
										" and serialNo = " + Common.sqlChar(data.serialNo) +
										")";						
							return str;
						}
						
						private String querySQLfromUntne_NonexpDetail(String ValueName){
							String str="(select sum(" + ValueName + ") as " + ValueName + " from Untne_NonexpDetail " + 
										" where 1=1" +
										" and enterOrg = " + Common.sqlChar(this.getEnterOrg()) +
										" and ownership = " + Common.sqlChar(this.getOwnership()) +									
										" and propertyNo = " + Common.sqlChar(this.getUp_propertyNo()) +
										" and lotNo = " + Common.sqlChar(data.NewLotNo) +
										")";						
							return str;
						}
						
						private String obtainBarCode(){
							String sqlTemp = "select buyDate from Untne_ReduceDetail " + 
												" where 1=1" +
												" and enterOrg = " + Common.sqlChar(data.enterOrg) +
												" and ownership = " + Common.sqlChar(data.ownership) +									
												" and propertyNo = " + Common.sqlChar(data.propertyNo) +
												" and lotNo = " + Common.sqlChar(data.lotNo);							
							String resultStr = getNameData("BUYDATE",sqlTemp); 

							String str = this.getOwnership() +
										resultStr.substring(0,3) +
										this.getUp_propertyNo().substring(0,1);
							
							String sql = "select lpad(max(substr(barCode,6))+1,6,0) as barCode from Untne_NonexpDetail" +
											" where substr(barCode,1,5) = " + Common.sqlChar(str);

							str += getNameData("BARCODE",sql);
							
							return str;
						}
		
		
}

class UNTNE053F_Data{
	public String enterOrg;
	public String ownership;
	public String caseNo;
	public String propertyNo;
	public String lotNo;
	public String serialNo;
	
	public String NewLotNo;
	public String NewserialNoS;
	public String NewserialNoE;
}
