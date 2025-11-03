/*
*<br>程式目的：國有財產局交換媒體檔－土地財產卡
*<br>程式代號：untsr006r
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.sr;

import java.sql.ResultSet;
import java.util.Calendar;

import unt.ch.UNTCH_Tools;
import util.*;

import java.io.*;
import java.math.BigDecimal;

import org.apache.log4j.Logger;


public class UNTSR006R extends QueryBean{
	
	String q_enterOrg;
	String q_enterDateE;
	String q_ownership;
	String q_verify;
	String q_differenceKind;
	String q_fundType;
	String q_orgBatchChange;
	String q_changeDateS;
	String q_changeDateE;
	public String getQ_enterOrg() {return checkGet(q_enterOrg);}
	public void setQ_enterOrg(String qEnterOrg) {q_enterOrg = checkSet(qEnterOrg);}
	public String getQ_enterDateE() {return checkGet(q_enterDateE);}
	public void setQ_enterDateE(String qEnterDateE) {q_enterDateE = checkSet(qEnterDateE);}
	public String getQ_ownership() {return checkGet(q_ownership);}
	public void setQ_ownership(String qOwnership) {q_ownership = checkSet(qOwnership);}
	public String getQ_verify() {return checkGet(q_verify);}
	public void setQ_verify(String qVerify) {q_verify = checkSet(qVerify);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String s) {q_differenceKind = checkSet(s);}
	public String getQ_fundType() {	return checkGet(q_fundType);}
	public void setQ_fundType(String s) {q_fundType = checkSet(s);}
	public String getQ_orgBatchChange() {return checkGet(q_orgBatchChange);}
	public void setQ_orgBatchChange(String s) {q_orgBatchChange = checkSet(s);	}
	public String getQ_changeDateS() {return checkGet(q_changeDateS);}
	public void setQ_changeDateS(String s) {	q_changeDateS = checkSet(s);}
	public String getQ_changeDateE() {return checkGet(q_changeDateE);	}
	public void setQ_changeDateE(String s) {q_changeDateE = checkSet(s);}




	String q_enterOrgName;
	public String getQ_enterOrgName() {return checkGet(q_enterOrgName);}
	public void setQ_enterOrgName(String qEnterOrgName) {q_enterOrgName = checkSet(qEnterOrgName);}
	

	private String txtPath;
	private String fileName;
	public String getTxtPath() {return txtPath;}
	public void setTxtPath(String txtPath) {this.txtPath = txtPath;}
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	private StringBuilder stb = new StringBuilder();
	private int groupCount=0;
	private int recordCount=0;
	private String timeString;
	
	public void createOutputTxt(){
		String pathString="";
				
		try{
			File tempDirectory = new File(System.getProperty("java.io.tmpdir"));

			timeString = Datetime.getHHMMSS();
			
			setFileName(obtainEnterOrgRealCode(Common.get(this.getQ_enterOrg()),Common.get(this.getQ_differenceKind())));
			
			pathString = tempDirectory.getAbsoluteFile() + File.separator + "PL" + this.getFileName() + Common.formatFrontZero((getYYYMMDD() + timeString.substring(0,4)),11) + ".TXT";
		
			FileOutputStream fout = new FileOutputStream(pathString);
			
			execLoop(fout);
			
		}catch(Exception e){
			setErrorMsg("產生檔案失敗！！");
			e.printStackTrace();
		}finally{
			this.setTxtPath(pathString);
		}
	}
	
	private String obtainEnterOrgRealCode(String enterOrg,String differenceKind){


		String returnStr = "";
		if("110".equals(differenceKind)){
			returnStr=enterOrg+"000";
		}
		else if("120".equals(differenceKind)){
			
			 Database db = new Database();
			 String strSQL = "";
			 try {
					strSQL +=" select codeID from SYSCA_Code where codeKindID='FUD' and codeid in (select fundno from SYSCA_FUNDORGAN where enterorg ='"+enterOrg+"')"+
					"";
					ResultSet rs = db.querySQL(strSQL);
					if (rs.next()){
						
						returnStr=rs.getString("codeID");
					}
			 }catch (Exception e) {
				 e.printStackTrace();
				} finally {
				db.closeAll();
				}	
		}
		return returnStr;
	}

	private void execLoop(FileOutputStream fout) throws Exception{		
		Database db = new Database();
		ResultSet rs;
		String sql="";
		java.util.Map map = new java.util.HashMap();
		
			
		try{
			if(getQ_orgBatchChange().equals("Y")){
			sql = getSQL_Step1();
			}
			else if(getQ_orgBatchChange().equals("N")){
			sql = 	getSQL_Step1WhenChooseN();
			}
			rs = db.querySQL(sql);
			while(rs.next()){
				groupCount++;
				
				map = loopStep1(rs,map);
				
				if(map.size()==0){
					
				}else{
					map = loopStep2(map);
				
					map = loopStep3(map);
				}
				recordCount++;
				stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|").append("EOR").append("\r\n");
				//問題單1814 編碼改為UTF-8
				fout.write(stb.toString().getBytes("utf-8"));
				stb.delete(0, stb.length());
				map.clear();
			}
			
			execInputIntoTXT_EndofTXT();
			//問題單1814 編碼改為UTF-8
			fout.write(stb.toString().getBytes("utf-8"));
			stb.delete(0, stb.length());
			
		}catch(Exception e){
			throw e;
		}finally{
			db.closeAll();
		}
	}
	

	//=======================================================================================	
		
		
	//「PL01_國有公用土地地籍資料」
		private String getSQL_Step1(){
			UNTCH_Tools ul = new UNTCH_Tools();
			String sql= " select a.enterOrg                 enterOrg         , " +		// 入帳機關
						       " a.ownership                ownership        , " +		// 權屬    
						       " a.propertyNo               propertyNo       , " +		// 財產編號
						       " 'PL01'                     dataType         , " +		// 主(附)資料集代碼     
						       " 'X'                        actionType       , " +		// 動作屬性              
						       " substring(a.signNo,1,1)       signNo1          , " +		// 土地標示-縣市         
						       " substring(a.signNo,4,4)       signNo3          , " +		// 土地標示-地段         
						       " substring(a.signNo,8,4)       signNo4          , " +		// 土地標示-地號母號     
						       " substring(a.signNo,12,4)      signNo5          , " +		// 土地標示-地號子號     
						       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
						       " a.valuable                 valuable         , " +		// 珍貴財產註記          
						       " substring(a.signNo,2,2)       signNo2          , " +		// 土地標示-鄉鎮市區     
						       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
						       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號           
						       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號         
						       " a.propertyName1            propertyName1    , " +		// 財產別名              
						       " null                       fundType         , " +		// 基金財產註記          
						       " 'N'                        unregisteredLand , " +		// 未登記土地                    
						       //" a.landRule                 landRule         , " +		// 等則                  
						       " a.originalArea             originalArea     , " +		// 原始整筆面積          
						       " a.originalHoldNume         originalHoldNume , " +		// 原始權利範圍－分子    
						       " a.originalHoldDeno         originalHoldDeno , " +		// 原始權利範圍－分母    
						       " a.ownershipDate            ownershipDate    , " +		// 國有登記日期						       
						       " a.useSeparate 				useSeparate      , " + 		// 使用分區						       
						       " a.useKind                  useKind          , " +		// 編定使用種類          
						       " (select z.valueUnit from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) valueUnit , " + 	// 申報地價單價（元） 
						       " (select z.suitDateS from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) suitDateS , " + 	// 申報地價日期       
						       " null                       proofDoc1        , " + 		// 權狀字號-權狀年      
						       " null                       proofDoc2        , " + 		// 權狀字號-權狀字      
						       " null                       proofDoc3        , " + 		// 權狀字號-權狀號
						       " (case a.sourceKind when '08' then '99' else a.sourceKind end) sourceKind , " +
						       															// 財產來源             
						       " a.sourceDate               sourceDate       , " + 		// 取得日期             
						       " a.oldOwner                 oldOwner         , " + 		// 原有人               
						       " a.sourceDoc                sourceDoc        , " + 		// 取得文號             
						       " a.enterDate                enterDate        , " + 		// 入帳日期             
						       " a.originalBV               originalBV       , " + 		// 原始入帳－總價       
						       " substring(a.ownershipNote,1,100) ownershipNote  , " + 		// 其他登記事項 
						       " '' as accountOrder  , " + 		// 最新帳務次序 
						       " '' as accountValue  , " + 		// 最新帳務金額 
						       " a.differenceKind           differenceKind   , " + 		// 區分別
						       " b.propertynoCount			propertynoCount	   " +		//財產編號子號2碼或3碼
						  " from UNTLA_Land a left join SYSPK_PROPERTYCODE b on a.propertyno = b.propertyno" +
						 " where a.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
						   " and a.enterDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_enterDateE())) +
						   " and a.ownership  = '1'" +
						   " and a.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
						   " and a.verify     = 'Y'" +
						   " and ( a.datastate='1' or ( a.datastate='2' and a.reduceDate > "+ Common.sqlChar(ul._transToCE_Year(this.getQ_enterDateE())) + " ) )" +
						 " order by  a.enterOrg, a.ownership, a.propertyNo, a.serialNo" ;
			return sql;
		}
			
			private java.util.Map loopStep1(ResultSet rs, java.util.Map map) throws Exception{
				try{
					 if(getQ_orgBatchChange().equals("Y")){
					map = loopStep1_getValue(rs,map);
					
					map = loopStep1dot1(map);
					}
					 else  if(getQ_orgBatchChange().equals("N")){
					map = loopStep1_getValue(rs,map);
					 }
					execInputIntoTXT_Step1(map);
				}catch(Exception e){
					throw e;
				}
				return map;
			}
			
				private java.util.Map loopStep1_getValue(ResultSet rs, java.util.Map map) throws Exception{
					try{
						map.put("enterOrg",Common.get(rs.getString("enterOrg")));
						map.put("ownership",Common.get(rs.getString("ownership")));
						map.put("propertyNo",Common.get(rs.getString("propertyNo")));
						map.put("dataType",Common.get(rs.getString("dataType")));
						map.put("actionType",Common.get(rs.getString("actionType")));
						map.put("signNo1",Common.get(rs.getString("signNo1")));
						map.put("signNo3",Common.get(rs.getString("signNo3")));
						map.put("signNo4",Common.get(rs.getString("signNo4")));
						map.put("signNo5",Common.get(rs.getString("signNo5")));
						map.put("propertyKind",Common.get(rs.getString("propertyKind")));
						map.put("valuable",Common.get(rs.getString("valuable")));
						map.put("signNo2",Common.get(rs.getString("signNo2")));
						map.put("propertyNo1",Common.get(rs.getString("propertyNo1")));
						if("2".equals(rs.getString("propertynoCount"))) {
							map.put("propertyNo2",Common.get(rs.getString("propertyNo2").substring(1,3)));
						}else {
							map.put("propertyNo2",Common.get(rs.getString("propertyNo2")));
						}
						map.put("serialNo",Common.get(rs.getString("serialNo")));
						map.put("propertyName1",Common.get(rs.getString("propertyName1")));
						map.put("fundType",Common.get(rs.getString("fundType")));
						map.put("unregisteredLand",Common.get(rs.getString("unregisteredLand")));
						//map.put("landRule",Common.get(rs.getString("landRule")));
						map.put("Area",Common.get(rs.getString("originalArea")));
						map.put("HoldNume",Common.get(rs.getString("originalHoldNume")));
						map.put("HoldDeno",Common.get(rs.getString("originalHoldDeno")));
						map.put("ownershipDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("ownershipDate")), "1"));
						map.put("useSeparate",Common.get(rs.getString("useSeparate")));
						map.put("useKind",Common.get(rs.getString("useKind")));
						map.put("valueUnit",Common.get(rs.getString("valueUnit")));
						map.put("suitDateS",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("suitDateS")),"1"));
						map.put("proofDoc1",Common.get(rs.getString("proofDoc1")));
						map.put("proofDoc2",Common.get(rs.getString("proofDoc2")));
						map.put("proofDoc3",Common.get(rs.getString("proofDoc3")));
						map.put("sourceKind",Common.get(rs.getString("sourceKind")));
						map.put("sourceDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("sourceDate")),"1"));
						map.put("oldOwner",Common.get(rs.getString("oldOwner")));
						map.put("originalBV",Common.get(rs.getString("originalBV")));
						map.put("enterDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("enterDate")),"1"));
						map.put("sourceDoc",Common.get(rs.getString("sourceDoc")));
						map.put("accountDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("enterDate")),"1"));
						map.put("accountValue",Common.get(rs.getString("accountValue")));
						map.put("ownershipNote",Common.get(rs.getString("ownershipNote")));
						map.put("differenceKind",Common.get(rs.getString("differenceKind")));
						map.put("accountOrder",Common.get(rs.getString("accountOrder")));

						if(getQ_orgBatchChange().equals("Y")){
						map = loopStep1dot1(map);
						}
						
					}catch(Exception e){
						
						throw e;
					}
					return map;
				}
					private String getSQL_Step1dot1(java.util.Map map){
						UNTCH_Tools ul = new UNTCH_Tools();
						String sql = " select b.adjustDate     adjustDate      , " + // 入帳日期                  
									       " b.editDate        editDate        , " + // 異動日期                 
									       " b.editTime        editTime        , " + // 異動時間                  
									       " c.newArea         newArea         , " + // 新整筆面積                
									       " c.newHoldNume     newHoldNume     , " + // 新權利範圍－分子          
									       " c.newHoldDeno     newHoldDeno     , " + // 新權利範圍－分母          
									       " c.newBookValue    newBookValue    , " + // 新帳面金額－總價          
									       " (" +
									       " select right('000'+cast(count(*)+1 as varchar),3) " +						       
									       " from UNTLA_AdjustProof b, UNTLA_AdjustDetail c" +
											 " where b.enterOrg     = c.enterOrg" +
											   " and b.ownership    = c.ownership" +
											   " and b.caseNo       = c.caseNo  " +
											   " and c.enterOrg     = " + Common.sqlChar(map.get("enterOrg").toString()) +
											   " and c.ownership    = " + Common.sqlChar(map.get("ownership").toString()) +
											   " and c.propertyNo   = " + Common.sqlChar(map.get("propertyNo").toString()) +
											   " and c.serialNo     = " + Common.sqlChar(map.get("serialNo").toString()) +
											   " and c.differenceKind     = " + Common.sqlChar(map.get("differenceKind").toString()) +
											   " and b.adjustDate   <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_enterDateE())) +
											   " and b.verify     = 'Y'" +
											   ") as accountOrder		" + 	//最新帳務次序
									  " from UNTLA_AdjustProof b, UNTLA_AdjustDetail c" +
									 " where b.enterOrg     = c.enterOrg" +
									   " and b.ownership    = c.ownership" +
									   " and b.caseNo       = c.caseNo  " +
									   " and c.enterOrg     = " + Common.sqlChar(map.get("enterOrg").toString()) +
									   " and c.ownership    = " + Common.sqlChar(map.get("ownership").toString()) +
									   " and c.propertyNo   = " + Common.sqlChar(map.get("propertyNo").toString()) +
									   " and c.serialNo     = " + Common.sqlChar(map.get("serialNo").toString()) +
									   " and c.differenceKind     = " + Common.sqlChar(map.get("differenceKind").toString()) +
									   " and b.adjustDate   <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_enterDateE())) +
									   " and b.verify     = 'Y'" +
									 " order by b.adjustDate desc, b.editDate desc, b.editTime desc" ;
						
						return sql;
					}
				
						private java.util.Map loopStep1dot1(java.util.Map map) throws Exception{
							Database db = new Database();
							ResultSet rs;
							String sql;
							try{
								sql = getSQL_Step1dot1(map);
								rs = db.querySQL(sql);
						
								if(rs.next()){
									map.remove("Area");
									map.remove("HoldNume");
									map.remove("HoldDeno");
									map.remove("accountDate");
									map.remove("accountValue");

									map.put("accountOrder",Common.get(rs.getString("accountOrder")));
									map.put("Area",Common.get(rs.getString("newArea")));
									map.put("HoldNume",Common.get(rs.getString("newHoldNume")));
									map.put("HoldDeno",Common.get(rs.getString("newHoldDeno")));
									map.put("accountDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("adjustDate")),"1"));
									map.put("accountValue",Common.get(rs.getString("newBookValue")));
									
								}else{
									map.put("accountOrder","001");
														
								}

								rs.close();
							}catch(Exception e){
								
								throw e;
							}finally{
								db.closeAll();
							}
							return map;
						}		
							
						
		private void execInputIntoTXT_Step1(java.util.Map map){
			recordCount++;
			if(!Common.get(map.get("actionType").toString()).equals("D")){
			stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|")
																					//組號	
				.append("PL01")											.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	.append("|")		//動作屬性
				.append(Common.get(map.get("signNo1").toString()))		.append("|")		//土地標示-縣市
				.append(Common.get(map.get("signNo3").toString()))		.append("|")		//土地標示-地段
				.append(Common.get(map.get("signNo4").toString()))		.append("|")		//土地標示-地號母號
				.append(Common.get(map.get("signNo5").toString()))		.append("|")		//土地標示-地號子號
				.append(Common.get(map.get("propertyKind").toString()))	.append("|")		//公用區分
				.append(Common.get(map.get("valuable").toString()))		.append("|")		//珍貴財產註記
				.append(Common.get(map.get("signNo2").toString()))		.append("|")		//土地標示-鄉鎮市區
				.append(Common.get(map.get("propertyNo1").toString()))	.append("|")		//財產編號-類項目節
				.append(Common.get(map.get("propertyNo2").toString()))	.append("|")		//財產編號-號
				.append(Common.get(map.get("serialNo").toString()))		.append("|")		//財產編號-序號
				.append(Common.get(map.get("propertyName1").toString())).append("|")		//財產別名
				.append(Common.get(map.get("fundType").toString()))		.append("|")		//基金財產註記
				.append("N")											.append("|")		//未登記土地
				//.append(Common.get(map.get("landRule").toString()))		.append("|")		//等則
				.append(Common.get(map.get("Area").toString()))			.append("|")		//整筆面積
				.append(Common.get(map.get("HoldNume").toString()))		.append("|")		//國有權利範圍分子
				.append(Common.get(map.get("HoldDeno").toString()))		.append("|")		//國有權利範圍分母
				.append(Common.get(map.get("ownershipDate").toString())).append("|")		//國有登記日期
				.append(Common.get(map.get("useSeparate").toString()))	.append("|")		//使用分區
				.append(Common.get(map.get("useKind").toString()))		.append("|")		//編定使用種類
				.append(Common.get(map.get("valueUnit").toString()))	.append("|")		//申報地價單價（元）
				.append(Common.get(map.get("suitDateS").toString()))	.append("|")		//申報地價日期
				.append(Common.get(map.get("proofDoc1").toString()))	.append("|")		//權狀字號-權狀年
				.append(Common.get(map.get("proofDoc2").toString()))	.append("|")		//權狀字號-權狀字
				.append(Common.get(map.get("proofDoc3").toString()))	.append("|")		//權狀字號-權狀號
				.append(Common.get(map.get("sourceKind").toString()))	.append("|")		//財產來源
				.append(Common.get(map.get("sourceDate").toString()))	.append("|")		//取得日期
				.append(Common.get(map.get("oldOwner").toString()))		.append("|")		//原有人
				.append(Common.get(map.get("sourceDoc").toString()))	.append("|")		//取得文號
				.append(Common.get(map.get("enterDate").toString()))	.append("|")		//原始入帳-登記日期
				.append(Common.get(map.get("originalBV").toString()))	.append("|")		//原始入帳-入帳金額（元）
				.append(Common.get(map.get("accountOrder").toString()))	.append("|")		//最新帳務次序
				.append(Common.get(map.get("accountDate").toString()))	.append("|")		//最新帳務資料-登記日期
				.append(Common.get(map.get("accountValue").toString()))	.append("|")		//最新帳務資料-帳面金額（元）
				.append(Common.get(map.get("ownershipNote").toString()))					//其他登記事項
				.append("\r\n");	
			}
			else if (Common.get(map.get("actionType").toString()).equals("D")){
				stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|")
				//組號	
				.append("PL01")											.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	.append("|")		//動作屬性
				.append(Common.get(map.get("signNo1").toString()))		.append("|")		//土地標示-縣市
				.append(Common.get(map.get("signNo3").toString()))		.append("|")		//土地標示-地段
				.append(Common.get(map.get("signNo4").toString()))		.append("|")		//土地標示-地號母號
				.append(Common.get(map.get("signNo5").toString()))		.append("|")		//土地標示-地號子號
				.append(Common.get(map.get("propertyKind").toString()))	.append("|")		//公用區分
				.append(Common.get(map.get("valuable").toString()))		.append("|")		//珍貴財產註記
				.append("\r\n");	
			}
			
		}
					
					
	//「PL02_國有公用土地管理資料」right('0000000'+cast(a.serialNo as varchar),7)
		private String getSQL_Step2(java.util.Map map){
			String sql = " select right('000'+cast(b.serialNo1 as varchar),3) as serialNo1, " ;	// 管理次序  
			        if(getQ_orgBatchChange().equals("Y")){
					sql+= " 'X'                  actionType       , " ;		// 動作屬性
			               }
			        else if(getQ_orgBatchChange().equals("N")){
			        sql+= " 'R'                  actionType       , " ;		// 動作屬性    	   
			               }
					sql+=  " substring( case b.useUnit when null then b.useUnit1 else ( select c.organAName from SYSCA_Organ c where c.organID=b.useUnit ) end , 1 , 30 )    useUnitName    , " +	// 使用單位
					       " b.useRelation, " +								// 使用關係     
					       " b.useDateS      	   , " +					// 使用期間－起 
					       " b.useDateE      	   , " +					// 使用期間－訖 
					       " b.useArea       	   , " +					// 使用面積(㎡) 
					       " b.notes1       	     " +					// 其他事項     
					  " from UNTLA_Manage b" +
					 " where b.enterOrg     = " + Common.sqlChar(map.get("enterOrg").toString()) +
					   " and b.ownership    = " + Common.sqlChar(map.get("ownership").toString()) +
					   " and b.propertyNo   = " + Common.sqlChar(map.get("propertyNo").toString()) +
					   " and b.serialNo     = " + Common.sqlChar(map.get("serialNo").toString()) +
					   " and b.differenceKind   = " + Common.sqlChar(map.get("differenceKind").toString()) +
					 " order by b.serialNo1" ;
			
			return sql;
		}
		
			private java.util.Map loopStep2(java.util.Map map) throws Exception{
				Database db = new Database();
				ResultSet rs;
				String sql;
				 
				try{
					sql = getSQL_Step2(map);
					rs = db.querySQL(sql);
					while(rs.next()){	
						map = loopStep2_getValue(rs, map);
						execInputIntoTXT_Step2(map);
					}
					rs.close();
				}catch(Exception e){
					throw e;
				}finally{
					db.closeAll();
				}
				return map;
			}	
				
				private java.util.Map loopStep2_getValue(ResultSet rs, java.util.Map map) throws Exception{
					try{
						map.put("serialNo1",Common.get(rs.getString("serialNo1")));
						map.put("useUnitName",Common.get(rs.getString("useUnitName")));
						map.put("useRelation",Common.get(rs.getString("useRelation")));
						map.put("useDateS",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("useDateS")),"1"));
						map.put("useDateE",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("useDateE")),"1"));
						map.put("useArea",Common.get(rs.getString("useArea")));
						map.put("notes1",Common.get(rs.getString("notes1")));
					
					}catch(Exception e){
						
						throw e;		
					}
					return map;
				}
			
			
		private void execInputIntoTXT_Step2(java.util.Map map){
			recordCount++;
			
			stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|")
																							//組號	
				.append("PL02")											.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	.append("|")		//動作屬性
				.append(Common.get(map.get("signNo1").toString()))		.append("|")		//土地標示-縣市
				.append(Common.get(map.get("signNo3").toString()))		.append("|")		//土地標示-地段
				.append(Common.get(map.get("signNo4").toString()))		.append("|")		//土地標示-地號母號
				.append(Common.get(map.get("signNo5").toString()))		.append("|")		//土地標示-地號子號
				.append(Common.get(map.get("propertyKind").toString()))	.append("|")		//公用區分
				.append(Common.get(map.get("valuable").toString()))		.append("|")		//珍貴財產註記
				.append(Common.get(map.get("serialNo1").toString()))	.append("|")		//次序
				.append(Common.get(map.get("useUnitName").toString()))	.append("|")		//使用單位
				.append(Common.get(map.get("useRelation").toString()))	.append("|")		//使用關係
				.append(Common.get(map.get("useArea").toString()))		.append("|")		//使用面積（㎡）
				.append(Common.get(map.get("useDateS").toString()))		.append("|")		//使用期間-起始
				.append(Common.get(map.get("useDateE").toString()))		.append("|")		//使用期間-截止
				.append(Common.get(map.get("notes1").toString()))							//其他事項				
				.append("\r\n");
			
		}
		
		//「PL03_國有公用土地地上物資料」
		private String getSQL_Step3(java.util.Map map){
			String sql = " select right('000'+cast(b.serialNo1 as varchar),3) as serialNo1, " ;			// 地上物次序 
						if(getQ_orgBatchChange().equals("Y")){
						sql+= " 'X'                  actionType       , " ;		// 動作屬性
				               }
				        else if(getQ_orgBatchChange().equals("N")){
				        sql+= " 'R'                  actionType       , " ;		// 動作屬性    	   
				               }
						sql+=" (case b.ownership1 when '50' then '05' when '51' then '05' else b.ownership1 end) as ownership1," +	
					       																// 地上物權屬               
					       " substring( b.owner + ( select '(' + c.organAName + ')' from SYSCA_Organ c where c.organID=b.manageOrg ) , 1 , 30 ) ownerManageOrg   , " +	// 地上物所有人(或管理機關) 
					       " b.state        	    , " +								// 地上物狀況               
					       " b.purpose      	    , " +								// 地上物用途               
					       " b.useArea       	      " +								// 使用本筆土地面積(㎡)     
					  " from UNTLA_Attachment b"+
						 " where b.enterOrg     = " + Common.sqlChar(map.get("enterOrg").toString()) +
						   " and b.ownership    = " + Common.sqlChar(map.get("ownership").toString()) +
						   " and b.propertyNo   = " + Common.sqlChar(map.get("propertyNo").toString()) +
						   " and b.serialNo     = " + Common.sqlChar(map.get("serialNo").toString()) +
						   " and b.differenceKind     = " + Common.sqlChar(map.get("differenceKind").toString()) +
					 " order by b.serialNo1";
			
			return sql;
		}
		
			private java.util.Map loopStep3(java.util.Map map) throws Exception{
				Database db = new Database();
				ResultSet rs;
				String sql;
				 
				try{
					sql = getSQL_Step3(map);
					rs = db.querySQL(sql);					
					while(rs.next()){	
						map = loopStep3_getValue(rs, map);
						execInputIntoTXT_Step3(map);
					}
					rs.close();
				}catch(Exception e){
					throw e;
				}finally{
					db.closeAll();
				}
				return map;
			}	
	
				private java.util.Map loopStep3_getValue(ResultSet rs, java.util.Map map) throws Exception{
					try{							
						map.put("serialNo1_1",Common.get(rs.getString("serialNo1")));
						map.put("ownership1",Common.get(rs.getString("ownership1")));
						map.put("ownerManageOrg",Common.get(rs.getString("ownerManageOrg")));
						map.put("state",Common.get(rs.getString("state")));
						map.put("purpose",Common.get(rs.getString("purpose")));
						map.put("useArea_1",Common.get(rs.getString("useArea")));
					}catch(Exception e){
						
						throw e;
					}
					return map;
				}	
	
		private void execInputIntoTXT_Step3(java.util.Map map){
			recordCount++;
			
			stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|")
																								//組號	
				.append("PL03")												.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	    .append("|")		//動作屬性
				.append(Common.get(map.get("signNo1").toString()))			.append("|")		//土地標示-縣市
				.append(Common.get(map.get("signNo3").toString()))			.append("|")		//土地標示-地段
				.append(Common.get(map.get("signNo4").toString()))			.append("|")		//土地標示-地號母號
				.append(Common.get(map.get("signNo5").toString()))			.append("|")		//土地標示-地號子號
				.append(Common.get(map.get("propertyKind").toString()))		.append("|")		//公用區分
				.append(Common.get(map.get("valuable").toString()))			.append("|")		//珍貴財產註記
				.append(Common.get(map.get("serialNo1_1").toString()))		.append("|")		//次序				
				.append(Common.get(map.get("ownership1").toString()))		.append("|")		//權屬類別
				.append(Common.get(map.get("ownerManageOrg").toString()))	.append("|")		//地上物所有人（或管理機關）
				.append(Common.get(map.get("state").toString()))			.append("|")		//地上物狀況
				.append(Common.get(map.get("useArea_1").toString()))		.append("|")		//使用本筆土地面積（㎡）
				.append(Common.get(map.get("purpose").toString()))								//使用期間-起始
				.append("\r\n");			
		}
		
		private void execInputIntoTXT_EndofTXT(){
			recordCount++;
			
			stb.append("EOF").append("|").append(
					getFileName()).append("|").append(						//機關代碼(管理機關代碼)
					Common.formatFrontZero(String.valueOf(recordCount),6)).append("|");
																			//總記錄	筆數
				
					//媒體檔	檢核碼
					//(程式版本+總記錄筆數)×13÷11+(總記錄筆數），其產生結果的小數取末五位其餘無條件捨去
					//假設程式版本為 1	
//					Double programNumber = 1.1;
//			 
//					Double d=new Double(((programNumber + new Double(recordCount).doubleValue())*13.0)/11.0 + new Double(recordCount).doubleValue());
//					String str = String.valueOf(java.lang.Math.floor(d*100000)/100000);
					// 問題單2419 修正小數點計算精度
					BigDecimal programNumber = new BigDecimal("1.1");
					 
					BigDecimal d=(programNumber.add(new BigDecimal(recordCount))).multiply(new BigDecimal("13.0")).divide(new BigDecimal("11.0"),2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(recordCount));
					String str = d.toString();					
				
			stb.append(str).append("|")								//媒體檔	檢核碼
				.append(get4YMMDD() + timeString);					//日期標章				
		}
		
		
		
	    public String getYYYMMDD() {
	        StringBuffer sb = new StringBuffer();
	        Calendar cal = Calendar.getInstance();
	        int y = cal.get(Calendar.YEAR) - 1911;
	        int m = cal.get(Calendar.MONTH) + 1;
	        int d = cal.get(Calendar.DATE);        
	        if (y<=99){ sb.append("0"); }
	        sb.append(Integer.toString(y));
	        if (m<=9){ sb.append("0"); }
	        sb.append(Integer.toString(m));
	        if (d<=9){ sb.append("0"); }
	        sb.append(Integer.toString(d));
	        return sb.toString();
	    }
	
	    //西元年
	    public String get4YMMDD() {
	        StringBuffer sb = new StringBuffer();
	        Calendar cal = Calendar.getInstance();
	        int y = cal.get(Calendar.YEAR) ;
	        int m = cal.get(Calendar.MONTH) + 1;
	        int d = cal.get(Calendar.DATE);        
	        if (y<=99){ sb.append("0"); }
	        sb.append(Integer.toString(y));
	        if (m<=9){ sb.append("0"); }
	        sb.append(Integer.toString(m));
	        if (d<=9){ sb.append("0"); }
	        sb.append(Integer.toString(d));
	        return sb.toString();
	    }
	    
	    
	  //「PL01_國有公用土地地籍資料」
		private String getSQL_Step1WhenChooseN(){
			UNTCH_Tools ul = new UNTCH_Tools();
			//增加單
			String sql= " select a.enterOrg                 enterOrg         , " +		// 入帳機關
						       " a.ownership                ownership        , " +		// 權屬    
						       " a.propertyNo               propertyNo       , " +		// 財產編號
						       " 'PL01'                     dataType         , " +		// 主(附)資料集代碼     
						       " 'R'                        actionType       , " +		// 動作屬性              
						       " substring(a.signNo,1,1)       signNo1          , " +		// 土地標示-縣市         
						       " substring(a.signNo,4,4)       signNo3          , " +		// 土地標示-地段         
						       " substring(a.signNo,8,4)       signNo4          , " +		// 土地標示-地號母號     
						       " substring(a.signNo,12,4)      signNo5          , " +		// 土地標示-地號子號     
						       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
						       " a.valuable                 valuable         , " +		// 珍貴財產註記          
						       " substring(a.signNo,2,2)       signNo2          , " +		// 土地標示-鄉鎮市區     
						       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
						       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號           
						       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號         
						       " a.propertyName1            propertyName1    , " +		// 財產別名              
						       " null                       fundType         , " +		// 基金財產註記          
						       " 'N'                        unregisteredLand , " +		// 未登記土地                  
						       //" a.landRule                 landRule         , " +		// 等則                  
						       " a.originalArea             originalArea     , " +		// 原始整筆面積          
						       " a.originalHoldNume         originalHoldNume , " +		// 原始權利範圍－分子    
						       " a.originalHoldDeno         originalHoldDeno , " +		// 原始權利範圍－分母    
						       " a.ownershipDate            ownershipDate    , " +		// 國有登記日期						       
						       " a.useSeparate 				useSeparate      , " + 		// 使用分區						       
						       " a.useKind                  useKind          , " +		// 編定使用種類          
						       " (select z.valueUnit from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) valueUnit , " + 	// 申報地價單價（元） 
						       " (select z.suitDateS from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) suitDateS , " + 	// 申報地價日期       
						       " null                       proofDoc1        , " + 		// 權狀字號-權狀年      
						       " null                       proofDoc2        , " + 		// 權狀字號-權狀字      
						       " null                       proofDoc3        , " + 		// 權狀字號-權狀號
						       " (case a.sourceKind when '08' then '99' else a.sourceKind end) sourceKind , " +
						       															// 財產來源             
						       " a.sourceDate               sourceDate       , " + 		// 取得日期             
						       " a.oldOwner                 oldOwner         , " + 		// 原有人               
						       " a.sourceDoc                sourceDoc        , " + 		// 取得文號             
						       " a.enterDate                enterDate        , " + 		// 入帳日期             
						       " a.originalBV               originalBV       , " + 		// 原始入帳－總價       
						       " substring(a.ownershipNote,1,100) ownershipNote  , " + 		// 其他登記事項
						       " '' as accountOrder  , " + 		// 最新帳務次序 
						       " a.bookValue as accountValue  , " + 		// 最新帳務金額
						       " a.differenceKind           differenceKind   , " + 		// 區分別
						       " b.propertynoCount			propertynoCount	   " +		//財產編號子號2碼或3碼
						  " from UNTLA_Land a left join SYSPK_PROPERTYCODE b on a.propertyno = b.propertyno" +
						 " where a.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
						   " and a.enterDate >= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateS())) +
						   " and a.enterDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateE())) +
						   " and a.ownership  = '1'" +
						   " and a.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
						   " and a.verify     = 'Y'" +
						   " and a.datastate='1' "+
						   " union "+
						   //增減單
						   " select a.enterOrg                 enterOrg         , " +		// 入帳機關
					       " a.ownership                ownership        , " +		// 權屬    
					       " a.propertyNo               propertyNo       , " +		// 財產編號
					       " 'PL01'                     dataType         , " +		// 主(附)資料集代碼     
					       " 'R'                        actionType       , " +		// 動作屬性              
					       " substring(a.signNo,1,1)       signNo1          , " +		// 土地標示-縣市         
					       " substring(a.signNo,4,4)       signNo3          , " +		// 土地標示-地段         
					       " substring(a.signNo,8,4)       signNo4          , " +		// 土地標示-地號母號     
					       " substring(a.signNo,12,4)      signNo5          , " +		// 土地標示-地號子號     
					       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
					       " a.valuable                 valuable         , " +		// 珍貴財產註記          
					       " substring(a.signNo,2,2)       signNo2          , " +		// 土地標示-鄉鎮市區     
					       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
					       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號           
					       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號         
					       " a.propertyName1            propertyName1    , " +		// 財產別名              
					       " null                       fundType         , " +		// 基金財產註記          
					       " 'N'                        unregisteredLand , " +		// 未登記土地                  
					       //" a.landRule                 landRule         , " +		// 等則                  
					       " a.originalArea             originalArea     , " +		// 原始整筆面積          
					       " a.originalHoldNume         originalHoldNume , " +		// 原始權利範圍－分子    
					       " a.originalHoldDeno         originalHoldDeno , " +		// 原始權利範圍－分母    
					       " a.ownershipDate            ownershipDate    , " +		// 國有登記日期						       
					       " a.useSeparate 				useSeparate      , " + 		// 使用分區						       
					       " a.useKind                  useKind          , " +		// 編定使用種類          
					       " (select z.valueUnit from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) valueUnit , " + 	// 申報地價單價（元） 
					       " (select z.suitDateS from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) suitDateS , " + 	// 申報地價日期       
					       " null                       proofDoc1        , " + 		// 權狀字號-權狀年      
					       " null                       proofDoc2        , " + 		// 權狀字號-權狀字      
					       " null                       proofDoc3        , " + 		// 權狀字號-權狀號
					       " (case a.sourceKind when '08' then '99' else a.sourceKind end) sourceKind , " +
					       															// 財產來源             
					       " a.sourceDate               sourceDate       , " + 		// 取得日期             
					       " a.oldOwner                 oldOwner         , " + 		// 原有人               
					       " a.sourceDoc                sourceDoc        , " + 		// 取得文號             
					       " a.enterDate                enterDate        , " + 		// 入帳日期             
					       " a.originalBV               originalBV       , " + 		// 原始入帳－總價       
					       " substring(a.ownershipNote,1,100) ownershipNote  , " + 		// 其他登記事項
					       " '' as accountOrder  , " + 		// 最新帳務次序 
					       " a.bookValue as accountValue  , " + 		// 最新帳務金額
					       " a.differenceKind           differenceKind   , " + 		// 區分別
					       " c.propertynoCount			propertynoCount	   " +		//財產編號子號2碼或3碼
					  " from UNTLA_AdjustDetail b left join UNTLA_Land a on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo and a.differenceKind=b.differenceKind " +
					  " left join SYSPK_PROPERTYCODE c on b.propertyno = c.propertyno " +
					  " where b.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
					   " and b.adjustDate >= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateS())) +
					   " and b.adjustDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateE())) +
					   " and b.ownership  = '1'" +
					   " and b.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
					   " and b.verify     = 'Y'" +
					   " union "+
					   //減損單
					   " select a.enterOrg                 enterOrg         , " +		// 入帳機關
				       " a.ownership                ownership        , " +		// 權屬    
				       " a.propertyNo               propertyNo       , " +		// 財產編號
				       " 'PL01'                     dataType         , " +		// 主(附)資料集代碼     
				       " 'D'                        actionType       , " +		// 動作屬性              
				       " substring(a.signNo,1,1)       signNo1          , " +		// 土地標示-縣市         
				       " substring(a.signNo,4,4)       signNo3          , " +		// 土地標示-地段         
				       " substring(a.signNo,8,4)       signNo4          , " +		// 土地標示-地號母號     
				       " substring(a.signNo,12,4)      signNo5          , " +		// 土地標示-地號子號     
				       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
				       " a.valuable                 valuable         , " +		// 珍貴財產註記          
				       " substring(a.signNo,2,2)       signNo2          , " +		// 土地標示-鄉鎮市區     
				       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
				       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號           
				       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號         
				       " a.propertyName1            propertyName1    , " +		// 財產別名              
				       " null                       fundType         , " +		// 基金財產註記          
				       " 'N'                        unregisteredLand , " +		// 未登記土地                   
				       //" a.landRule                 landRule         , " +		// 等則                  
				       " a.originalArea             originalArea     , " +		// 原始整筆面積          
				       " a.originalHoldNume         originalHoldNume , " +		// 原始權利範圍－分子    
				       " a.originalHoldDeno         originalHoldDeno , " +		// 原始權利範圍－分母    
				       " a.ownershipDate            ownershipDate    , " +		// 國有登記日期						       
				       " a.useSeparate 				useSeparate      , " + 		// 使用分區						       
				       " a.useKind                  useKind          , " +		// 編定使用種類          
				       " (select z.valueUnit from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) valueUnit , " + 	// 申報地價單價（元） 
				       " (select z.suitDateS from UNTLA_Value z where a.enterOrg=z.enterOrg and a.ownership=z.ownership and a.propertyNo=z.propertyNo and a.serialNo=z.serialNo and a.differencekind = z.differencekind and z.bulletinDate=(select max(y.bulletinDate) from UNTLA_Value y where z.enterOrg=y.enterOrg and z.ownership=y.ownership and z.propertyNo=y.propertyNo and z.serialNo=y.serialNo)) suitDateS , " + 	// 申報地價日期       
				       " null                       proofDoc1        , " + 		// 權狀字號-權狀年      
				       " null                       proofDoc2        , " + 		// 權狀字號-權狀字      
				       " null                       proofDoc3        , " + 		// 權狀字號-權狀號
				       " (case a.sourceKind when '08' then '99' else a.sourceKind end) sourceKind , " +
				       															// 財產來源             
				       " a.sourceDate               sourceDate       , " + 		// 取得日期             
				       " a.oldOwner                 oldOwner         , " + 		// 原有人               
				       " a.sourceDoc                sourceDoc        , " + 		// 取得文號             
				       " a.enterDate                enterDate        , " + 		// 入帳日期             
				       " a.originalBV               originalBV       , " + 		// 原始入帳－總價       
				       " substring(a.ownershipNote,1,100) ownershipNote  , " + 		// 其他登記事項 
				       " '' as accountOrder  , " + 		// 最新帳務次序 
				       " a.bookValue as accountValue  , " + 		// 最新帳務金額
				       " a.differenceKind           differenceKind   , "  + 		// 區分別
				       " c.propertynoCount			propertynoCount	   " +		//財產編號子號2碼或3碼
				  " from UNTLA_ReduceDetail b left join UNTLA_Land a on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo and a.differenceKind=b.differenceKind " +
				  " left join SYSPK_PROPERTYCODE c on b.propertyno = c.propertyno " +
				  " where b.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
				   " and b.reduceDate >= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateS())) +
				   " and b.reduceDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateE())) +
				   " and b.ownership  = '1'" +
				   " and b.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
				   " and b.verify     = 'Y'" +
				 " order by  enterOrg, ownership, propertyNo, serialNo" ;

			
			return sql;
		}
}
