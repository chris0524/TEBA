/*
*<br>程式目的：國有財產局交換媒體檔－建物財產卡
*<br>程式代號：untsr007r
*<br>程式日期：
*<br>程式作者：Yuan-Ren Jheng
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.sr;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.*;

import java.io.*;

import org.apache.log4j.Logger;


public class UNTSR007R extends QueryBean{
	
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
	private String checkSignno;
	public String getTxtPath() {return txtPath;}
	public void setTxtPath(String txtPath) {this.txtPath = txtPath;}
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	public String getCheckSignno() { return checkGet(checkSignno); }
	public void setCheckSignno(String checkSignno) { this.checkSignno = checkSet(checkSignno); }

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
			
			pathString = tempDirectory.getAbsoluteFile() + File.separator + "PH" + this.getFileName() + Common.formatFrontZero((getYYYMMDD() + timeString.substring(0,4)),11) + ".TXT";
		
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
		
		
	//「PH01_國有公用房屋主資料」
		private String getSQL_Step1(){
			UNTCH_Tools ul = new UNTCH_Tools();
			String sql= " select a.enterOrg                 enterOrg         , " +		// 入帳機關
						       " a.ownership                ownership        , " +		// 權屬    
						       " a.propertyNo               propertyNo       , " +		// 財產編號
						       " 'PH01'                     dataType         , " +		// 主(附)資料集代碼     
						       " 'X'                        actionType       , " +		// 動作屬性
						       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
						       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號
						       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號
						       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
						       " a.valuable                    valuable         , " +		// 珍貴財產註記
						       " a.propertyName1            propertyName1 	    , " +		// 財產別名              
						       " a.fundType                  fundType           , " +		// 基金財產註記
						       " 'N'                        unregisteredBuilding , " +		// 未登記建物  
						       " substring(a.signNo,1,1)       signNo1          , " +		// 建物標示-縣市         
						       " substring(a.signNo,4,4)       signNo3          , " +		// 建物標示-地段         
						       " substring(a.signNo,8,5)       signNo4          , " +		// 建物標示-地號母號     
						       " substring(a.signNo,13,3)      signNo5          , " +		// 建物標示-地號子號     
						       " substring(a.signNo,2,2)       signNo2          , " +		// 建物標示-鄉鎮市區
						       " a.doorPlatevillage1       doorPlatevillage1    , " +		// 地址-村/里(中文名稱) 
						       " a.doorPlatevillage2       doorPlatevillage2    , " +		// 地址-村/里(代碼) 
						       " a.doorplateRd1       	   doorplateRd1         , " +		// 地址-路/街/大道(中文名稱)
						       " a.doorplateRd2            doorplateRd2         , " +		// 地址-路/街/大道(代碼) 
						       " a.doorplateSec      	   doorplateSec         , " +		// 地址-段 
						       " null 					   doorPlateArea   		, " +		// 地址-地區
						       " a.doorplateLn   		   doorplateLn   		, " +		// 地址-巷 
						       " a.doorplateAly      	   doorplateAly         , " +		// 地址-弄 
						       " a.doorplateNo             doorplateNo          , " +		// 地址-號
						       " a.doorplateFloor1         doorplateFloor1      , " +		// 地址-樓
						       " null                      proofDoc1            , " + 		// 權狀字號-權狀年      
						       " null                      proofDoc2            , " + 		// 權狀字號-權狀字      
						       " null                      proofDoc3            , " + 		// 權狀字號-權狀號
						       " a.CArea			       CArea                , " +		// 主建物面積（㎡） 
						       " a.SArea       			   SArea                , " +		// 附屬建物面積（㎡）
						       " a.area       			   area                 , " +		// 合計面積 
						       " a.ownershipDate           ownershipDate  		, " +		// 國有登記日期
						       " a.buildDate       		   buildDate    		, " +		// 建築日期
						       " a.holdNume         	   holdNume				,  " +	    // 權利範圍－分子    
						       " a.holdDeno       		   holdDeno				,  " +	    // 權利範圍－分母
						       " null      				   construction1    	, " +		// 房屋構造-層
						       " null     				   construction2    	, " +		// 房屋構造-造
						       " a.sourceKind              sourceKind           , " +		// 財產來源  
						       " a.sourceDate              sourceDate           , " + 		// 取得日期
						       " a.sourceDoc               sourceDoc            , " + 		// 取得文號
						       " cast(ROUND((a.otherlimityear / 12),0) as decimal(3,0))                      useLimitYear         , " +       // 最低使用年限
						       " a.enterDate               enterDate      	    , " + 		// 入帳日期  
						       " a.originalBV              originalBV     		, " + 		// 原始入帳－總價
						       " '' as accountOrder  							, " + 		// 最新帳務次序 
						       " '' as accountValue  							, " + 		// 最新帳務金額
						       " a.differenceKind           differenceKind   	, " + 		// 區分別
						       " b.propertynocount			propertynoCount		  " +		// 財產編號子號2碼或3碼
						  " from UNTBU_BUILDING a left join SYSPK_PROPERTYCODE b on a.propertyno = b.propertyno " +
						 " where a.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
						   " and a.ownership  = '1'" +
						   " and a.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
						   " and substring(a.propertyNo,1,5) between '20101' and '20106' " +	// 問題單1343,依照國產署講義限定資料範圍
						   " and a.enterDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_enterDateE())) +
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
						
						//TODO 1080719因中科管理局上傳失敗，原因為系統裡的財編為20102010007，但國產署格式為201020107，因時程關係先調整程式讓此筆財編正確，後續再做調整
						if("2010201007".equals(rs.getString("propertyNo1") + rs.getString("propertyNo2"))) {
							map.put("propertyNo2",Common.get("07"));
						}else {
							map.put("propertyNo2",Common.get(rs.getString("propertyNo2")));
						}
						if("2".equals(rs.getString("propertynoCount"))) {
							map.put("propertyNo2",Common.get(rs.getString("propertyNo2").substring(1,3)));
						}else {
							map.put("propertyNo2",Common.get(rs.getString("propertyNo2")));
						}
						map.put("serialNo",Common.get(rs.getString("serialNo")));
						map.put("propertyName1",Common.get(rs.getString("propertyName1")));
						map.put("fundType",Common.get(rs.getString("fundType")));
						map.put("unregisteredBuilding",Common.get(rs.getString("unregisteredBuilding")));
						map.put("doorPlatevillage1",Common.get(rs.getString("doorPlatevillage1")));
						map.put("doorPlatevillage2",Common.get(rs.getString("doorPlatevillage2")));
						map.put("doorplateRd1",Common.get(rs.getString("doorplateRd1")));
						map.put("doorplateRd2",Common.get(rs.getString("doorplateRd2")));
						map.put("doorplateSec",Common.get(rs.getString("doorplateSec")));
						map.put("doorPlateArea",Common.get(rs.getString("doorPlateArea")));
						map.put("doorplateLn",Common.get(rs.getString("doorplateLn")));
						map.put("doorplateAly",Common.get(rs.getString("doorplateAly")));
						map.put("doorplateNo",Common.get(rs.getString("doorplateNo")));
						map.put("doorplateFloor1",Common.get(rs.getString("doorplateFloor1")));
						map.put("proofDoc1",Common.get(rs.getString("proofDoc1")));
						map.put("proofDoc2",Common.get(rs.getString("proofDoc2")));
						map.put("proofDoc3",Common.get(rs.getString("proofDoc3")));
						map.put("CArea",Common.get(rs.getString("CArea")));
						map.put("SArea",Common.get(rs.getString("SArea")));
						map.put("area",Common.get(rs.getString("area")));
						map.put("ownershipDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("ownershipDate")),"1"));
						map.put("buildDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("buildDate")),"1"));
						map.put("holdNume",Common.get(rs.getString("holdNume")));
						map.put("holdDeno",Common.get(rs.getString("holdDeno")));
						map.put("construction1",Common.get(rs.getString("construction1")));
						map.put("construction2",Common.get(rs.getString("construction2")));
						map.put("sourceKind",Common.get(rs.getString("sourceKind")));
						map.put("sourceDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("sourceDate")),"1"));
						map.put("sourceDoc",Common.get(rs.getString("sourceDoc")));
						map.put("useLimitYear",Common.get(rs.getString("useLimitYear")));
						map.put("accountDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("enterDate")),"1"));
						map.put("originalBV",Common.get(rs.getString("originalBV")));
						map.put("enterDate",Datetime.changeTaiwanYYMMDD(Common.get(rs.getString("enterDate")),"1"));
						map.put("differenceKind",Common.get(rs.getString("differenceKind")));
						map.put("accountOrder",Common.get(rs.getString("accountOrder")));
						map.put("accountValue",Common.get(rs.getString("accountValue")));
						map.put("propertynoCount", Common.get(rs.getString("propertynoCount")));
						
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
									       " from UNTBU_AdjustProof b, UNTBU_AdjustDetail c" +
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
									  " from UNTBU_AdjustProof b, UNTBU_AdjustDetail c" +
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
									map.remove("area");
									map.remove("holdNume");
									map.remove("holdDeno");
									map.remove("accountDate");
									map.remove("accountValue");

									map.put("accountOrder",Common.get(rs.getString("accountOrder")));
									map.put("area",Common.get(rs.getString("newArea")));
									map.put("holdNume",Common.get(rs.getString("newHoldNume")));
									map.put("holdDeno",Common.get(rs.getString("newHoldDeno")));
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
			stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|") //組號	
				.append("PH01")											.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	.append("|")		//動作屬性
				.append(Common.get(map.get("propertyNo1").toString()))	.append("|")		//財產編號-類項目節
				.append(Common.get(map.get("propertyNo2").toString()))	.append("|")		//財產編號-號
				.append(Common.get(map.get("serialNo").toString()))	    .append("|")	    //財產編號-分號
				.append(Common.get(map.get("propertyKind").toString()))	.append("|")		//公用區分
				.append(Common.get(map.get("valuable").toString()))		.append("|")		//珍貴財產註記
				.append(Common.get(map.get("propertyName1").toString())).append("|")		//財產別名
				.append(Common.get(map.get("fundType").toString()))		.append("|")		//基金財產註記
				.append("N").append("|")		//未登記房屋
				.append(Common.get(map.get("signNo1").toString()))		.append("|")		//建物標示-縣市
				.append(Common.get(map.get("signNo2").toString()))		.append("|")		//建物標示-鄉鎮市區
				.append(Common.get(map.get("signNo3").toString()))		.append("|")		//建物標示-地段
				.append(Common.get(map.get("signNo4").toString()))		.append("|")		//建物標示-地號母號
				.append(Common.get(map.get("signNo5").toString()))		.append("|")		//建物標示-地號子號
				.append(Common.get(map.get("doorPlatevillage1").toString()))		.append("|")		//地址-村/里(中文名稱)
				.append(Common.get(map.get("doorPlatevillage2").toString()))		.append("|")		//地址-村/里(代碼) 
				.append(Common.get(map.get("doorplateRd1").toString()))		.append("|")		//地址-路/街/大道(中文名稱) 
				.append(Common.get(map.get("doorplateRd2").toString()))		.append("|")		//地址-路/街/大道(代碼)
				.append(Common.get(map.get("doorplateSec").toString()))		.append("|")		//地址-段
				.append(Common.get(map.get("doorPlateArea").toString()))		.append("|")		//地址-地區
				.append(Common.get(map.get("doorplateLn").toString()))		.append("|")		//地址-巷
				.append(Common.get(map.get("doorplateAly").toString()))		.append("|")		//地址-弄
				.append(Common.get(map.get("doorplateNo").toString()))		.append("|")		//地址-號
				.append(Common.get(map.get("doorplateFloor1").toString()))		.append("|")		//地址-樓
				.append(Common.get(map.get("proofDoc1").toString()))	.append("|")		//權狀字號-權狀年
				.append(Common.get(map.get("proofDoc2").toString()))	.append("|")		//權狀字號-權狀字
				.append(Common.get(map.get("proofDoc3").toString()))	.append("|")		//權狀字號-權狀號
				.append(Common.get(map.get("CArea").toString()))	.append("|")		//主建物面積（㎡）
				.append(Common.get(map.get("SArea").toString()))	.append("|")		//附屬建物面積（㎡）
				.append(Common.get(map.get("area").toString()))	.append("|")		//合計面積
				.append(Common.get(map.get("ownershipDate").toString())).append("|")		//國有登記日期
				.append(Common.get(map.get("buildDate").toString())).append("|")		//建築日期
				.append(Common.get(map.get("holdNume").toString()))		.append("|")		//權利範圍－分子
				.append(Common.get(map.get("holdDeno").toString()))		.append("|")		//權利範圍－分母
				.append(Common.get(map.get("construction1").toString()))		.append("|")		//房屋構造-層
				.append(Common.get(map.get("construction2").toString()))		.append("|")		//房屋構造-造
				.append(Common.get(map.get("sourceKind").toString()))	.append("|")		//財產來源
				.append(Common.get(map.get("sourceDate").toString()))	.append("|")		//取得日期
				.append(Common.get(map.get("sourceDoc").toString()))	.append("|")		//取得文號
				.append(Common.get(map.get("useLimitYear").toString()))	.append("|")        //最低使用年限
				.append(Common.get(map.get("enterDate").toString()))	.append("|")		//原始入帳-登記日期
				.append(Common.get(map.get("originalBV").toString()))	.append("|")		//原始入帳-入帳金額（元）
				.append(Common.get(map.get("accountOrder").toString()))	.append("|")		//最新帳務次序
				.append(Common.get(map.get("accountDate").toString()))	.append("|")		//最新帳務資料-登記日期
				.append(Common.get(map.get("accountValue").toString()))		//最新帳務資料-帳面金額（元）
				.append("\r\n");	
			}
			else if (Common.get(map.get("actionType").toString()).equals("D")){
				stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|")
				//組號	
				.append("PH01")											.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	.append("|")		//動作屬性
				.append(Common.get(map.get("propertyNo1").toString()))	.append("|")		//財產編號-類項目節
				.append(Common.get(map.get("propertyNo2").toString()))	.append("|")		//財產編號-號
				.append(Common.get(map.get("serialNo").toString()))	    .append("|")	    //財產編號-分號
				.append(Common.get(map.get("propertyKind").toString()))	.append("|")		//公用區分
				.append(Common.get(map.get("valuable").toString()))		.append("|")		//珍貴財產註記
				.append("\r\n");	
			}
			
		}
					
					
	//「PH04_國有公用房屋管理資料」right('0000000'+cast(a.serialNo as varchar),7)
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
					  " from UNTBU_Manage b" +
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
				.append("PH04")											.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	.append("|")		//動作屬性
				.append(Common.get(map.get("propertyNo1").toString()))	.append("|")		//財產編號-類項目節
				.append(Common.get(map.get("propertyNo2").toString()))	.append("|")		//財產編號-號
				.append(Common.get(map.get("serialNo").toString()))	    .append("|")	    //財產編號-分號
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
		
		//「PH05_國有公用房屋基地資料」
		private String getSQL_Step3(java.util.Map map){
			String sql = " select right('000'+cast(b.serialNo1 as varchar),3) as serialNo1, " ;			// 次序 
						if(getQ_orgBatchChange().equals("Y")){
						sql+= " 'X'                  actionType       , " ;		// 動作屬性
				               }
				        else if(getQ_orgBatchChange().equals("N")){
				        sql+= " 'R'                  actionType       , " ;		// 動作屬性    	   
				               }
						sql+=" b.ownership1, " +									// 基地權屬                        
					       " substring(b.signNo,1,1) baseSignNo1, " +		// 基地標示-縣市 
					       " substring(b.signNo,2,2) baseSignNo2, " +		// 基地標示-鄉鎮市區 
					       " substring(b.signNo,4,4) baseSignNo3, " +		// 基地標示-地段 
					       " substring(b.signNo,8,4) baseSignNo4, " +		// 基地標示-地號母號 
					       " substring(b.signNo,12,4) baseSignNo5, " +	// 基地標示-地號子號                
					       " b.area baseArea, " +								// 基地整筆面積（㎡）                    
					       " b.holdNume baseHoldNume, " +								// 權利範圍分子                  
					       " b.holdDeno baseHoldDeno, " +								// 權利範圍分母          
					       " b.owner        	    , " +								// 基地所有人               
					       " b.notes1      	     " +								// 其他事項                    
					  " from UNTBU_Base b"+
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
						map.put("baseSignNo1",Common.get(rs.getString("baseSignNo1")));
						map.put("baseSignNo2",Common.get(rs.getString("baseSignNo2")));
						map.put("baseSignNo3",Common.get(rs.getString("baseSignNo3")));
						map.put("baseSignNo4",Common.get(rs.getString("baseSignNo4")));
						map.put("baseSignNo5",Common.get(rs.getString("baseSignNo5")));
						map.put("baseArea",Common.get(rs.getString("baseArea")));
						map.put("baseHoldNume",Common.get(rs.getString("baseHoldNume")));
						map.put("baseHoldDeno",Common.get(rs.getString("baseHoldDeno")));
						map.put("owner",Common.get(rs.getString("owner")));
						map.put("notes1",Common.get(rs.getString("notes1")));
					}catch(Exception e){
						
						throw e;
					}
					return map;
				}	
	
		private void execInputIntoTXT_Step3(java.util.Map map){
			recordCount++;
			
			stb.append(Common.formatFrontZero(String.valueOf(groupCount),6)).append("|")
																							//組號	
				.append("PH05")											.append("|")		//主(附)資料集代碼
				.append(Common.get(map.get("actionType").toString()))	.append("|")		//動作屬性
				.append(Common.get(map.get("propertyNo1").toString()))	.append("|")		//財產編號-類項目節
				.append(Common.get(map.get("propertyNo2").toString()))	.append("|")		//財產編號-號
				.append(Common.get(map.get("serialNo").toString()))	    .append("|")	    //財產編號-分號
				.append(Common.get(map.get("propertyKind").toString()))	.append("|")		//公用區分
				.append(Common.get(map.get("valuable").toString()))		.append("|")		//珍貴財產註記
				.append(Common.get(map.get("serialNo1_1").toString()))	.append("|")		//次序	
				.append(Common.get(map.get("baseSignNo1").toString()))		.append("|")		//基地標示-縣市
				.append(Common.get(map.get("baseSignNo2").toString()))		.append("|")		//基地標示-鄉鎮市區
				.append(Common.get(map.get("baseSignNo3").toString()))		.append("|")		//基地標示-地段
				.append(Common.get(map.get("baseSignNo4").toString()))		.append("|")		//基地標示-地號母號
				.append(Common.get(map.get("baseSignNo5").toString()))		.append("|")		//基地標示-地號子號
				.append(Common.get(map.get("baseArea").toString()))			.append("|")		//整筆面積（㎡）
				.append(Common.get(map.get("baseHoldNume").toString()))		.append("|")		//權利範圍－分子
				.append(Common.get(map.get("baseHoldDeno").toString()))		.append("|")		//權利範圍－分母
				.append(Common.get(map.get("ownership1").toString()))	.append("|")	//基地權屬
				.append(Common.get(map.get("owner").toString()))		.append("|")		//基地所有人
				.append(Common.get(map.get("notes1").toString()))				//其他事項
		
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
					Double programNumber = 1.1;
			 
					Double d=new Double(((programNumber + new Double(recordCount).doubleValue())*13.0)/11.0 + new Double(recordCount).doubleValue());
					String str = String.valueOf(java.lang.Math.floor(d*100000)/100000);
					
				
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
						       " 'PH01'                     dataType         , " +		// 主(附)資料集代碼     
						       " 'X'                        actionType       , " +		// 動作屬性
						       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
						       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號
						       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號
						       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
						       " a.valuable                    valuable         , " +		// 珍貴財產註記
						       " a.propertyName1            propertyName1 	    , " +		// 財產別名              
						       " a.fundType                  fundType           , " +		// 基金財產註記
						       " 'N'                        unregisteredBuilding , " +		// 未登記建物  
						       " substring(a.signNo,1,1)       signNo1          , " +		// 建物標示-縣市         
						       " substring(a.signNo,4,4)       signNo3          , " +		// 建物標示-地段         
						       " substring(a.signNo,8,5)       signNo4          , " +		// 建物標示-地號母號     
						       " substring(a.signNo,13,3)      signNo5          , " +		// 建物標示-地號子號     
						       " substring(a.signNo,2,2)       signNo2          , " +		// 建物標示-鄉鎮市區
						       " a.doorPlatevillage1       doorPlatevillage1    , " +		// 地址-村/里(中文名稱) 
						       " a.doorPlatevillage2       doorPlatevillage2    , " +		// 地址-村/里(代碼) 
						       " a.doorplateRd1       	   doorplateRd1         , " +		// 地址-路/街/大道(中文名稱)
						       " a.doorplateRd2            doorplateRd2         , " +		// 地址-路/街/大道(代碼) 
						       " a.doorplateSec      	   doorplateSec         , " +		// 地址-段 
						       " null 					   doorPlateArea   		, " +		// 地址-地區
						       " a.doorplateLn   		   doorplateLn   		, " +		// 地址-巷 
						       " a.doorplateAly      	   doorplateAly         , " +		// 地址-弄 
						       " a.doorplateNo             doorplateNo          , " +		// 地址-號
						       " a.doorplateFloor1         doorplateFloor1      , " +		// 地址-樓
						       " null                      proofDoc1            , " + 		// 權狀字號-權狀年      
						       " null                      proofDoc2            , " + 		// 權狀字號-權狀字      
						       " null                      proofDoc3            , " + 		// 權狀字號-權狀號
						       " a.CArea			       CArea                , " +		// 主建物面積（㎡） 
						       " a.SArea       			   SArea                , " +		// 附屬建物面積（㎡）
						       " a.area       			   area                 , " +		// 合計面積 
						       " a.ownershipDate           ownershipDate  		, " +		// 國有登記日期
						       " a.buildDate       		   buildDate    		, " +		// 建築日期
						       " a.holdNume         	   holdNume				,  " +	    // 權利範圍－分子    
						       " a.holdDeno       		   holdDeno				,  " +	    // 權利範圍－分母
						       " null      				   construction1    	, " +		// 房屋構造-層
						       " null     				   construction2    	, " +		// 房屋構造-造
						       " a.sourceKind              sourceKind           , " +		// 財產來源  
						       " a.sourceDate              sourceDate           , " + 		// 取得日期
						       " a.sourceDoc               sourceDoc            , " + 		// 取得文號
						       " cast(ROUND((a.otherlimityear / 12),0) as decimal(3,0))                      useLimitYear         , " +       // 最低使用年限
						       " a.enterDate               enterDate      	    , " + 		// 入帳日期  
						       " a.originalBV              originalBV     		, " + 		// 原始入帳－總價
						       " '' as accountOrder  							, " + 		// 最新帳務次序 
						       " '' as accountValue  							, " + 		// 最新帳務金額						  
						       " a.differenceKind           differenceKind  	, " + 		// 區分別
						       " b.propertynocount			propertynoCount		  " +		// 財產編號子號2碼或3碼
						  " from UNTBU_BUILDING a left join SYSPK_PROPERTYCODE b on a.propertyno = b.propertyno " +
						 " where a.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
						   " and a.ownership  = '1'" +
						   " and a.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
						   " and substring(a.propertyNo,1,5) between '20101' and '20106' " +	// 問題單1343,依照國產署講義限定資料範圍
						   " and a.verify     = 'Y'" +
						   " and a.datastate='1' "+
						   " and a.enterDate >= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateS())) +
						   " and a.enterDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateE())) +
						   " union "+
						   //增減單
						   " select a.enterOrg                 enterOrg         , " +		// 入帳機關
						   " a.ownership                ownership        , " +		// 權屬    
					       " a.propertyNo               propertyNo       , " +		// 財產編號
					       " 'PH01'                     dataType         , " +		// 主(附)資料集代碼     
					       " 'X'                        actionType       , " +		// 動作屬性
					       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
					       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號
					       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號
					       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
					       " a.valuable                    valuable         , " +		// 珍貴財產註記
					       " a.propertyName1            propertyName1 	    , " +		// 財產別名              
					       " a.fundType                  fundType           , " +		// 基金財產註記
					       " 'N'                        unregisteredBuilding , " +		// 未登記建物  
					       " substring(a.signNo,1,1)       signNo1          , " +		// 建物標示-縣市         
					       " substring(a.signNo,4,4)       signNo3          , " +		// 建物標示-地段         
					       " substring(a.signNo,8,5)       signNo4          , " +		// 建物標示-地號母號     
					       " substring(a.signNo,13,3)      signNo5          , " +		// 建物標示-地號子號     
					       " substring(a.signNo,2,2)       signNo2          , " +		// 建物標示-鄉鎮市區
					       " a.doorPlatevillage1       doorPlatevillage1    , " +		// 地址-村/里(中文名稱) 
					       " a.doorPlatevillage2       doorPlatevillage2    , " +		// 地址-村/里(代碼) 
					       " a.doorplateRd1       	   doorplateRd1         , " +		// 地址-路/街/大道(中文名稱)
					       " a.doorplateRd2            doorplateRd2         , " +		// 地址-路/街/大道(代碼) 
					       " a.doorplateSec      	   doorplateSec         , " +		// 地址-段 
					       " null 					   doorPlateArea   		, " +		// 地址-地區
					       " a.doorplateLn   		   doorplateLn   		, " +		// 地址-巷 
					       " a.doorplateAly      	   doorplateAly         , " +		// 地址-弄 
					       " a.doorplateNo             doorplateNo          , " +		// 地址-號
					       " a.doorplateFloor1         doorplateFloor1      , " +		// 地址-樓
					       " null                      proofDoc1            , " + 		// 權狀字號-權狀年      
					       " null                      proofDoc2            , " + 		// 權狀字號-權狀字      
					       " null                      proofDoc3            , " + 		// 權狀字號-權狀號
					       " a.CArea			       CArea                , " +		// 主建物面積（㎡） 
					       " a.SArea       			   SArea                , " +		// 附屬建物面積（㎡）
					       " a.area       			   area                 , " +		// 合計面積 
					       " a.ownershipDate           ownershipDate  		, " +		// 國有登記日期
					       " a.buildDate       		   buildDate    		, " +		// 建築日期
					       " a.holdNume         	   holdNume				,  " +	    // 權利範圍－分子    
					       " a.holdDeno       		   holdDeno				,  " +	    // 權利範圍－分母
					       " null      				   construction1    	, " +		// 房屋構造-層
					       " null     				   construction2    	, " +		// 房屋構造-造
					       " a.sourceKind              sourceKind           , " +		// 財產來源  
					       " a.sourceDate              sourceDate           , " + 		// 取得日期
					       " a.sourceDoc               sourceDoc            , " + 		// 取得文號
					       " cast(ROUND((a.otherlimityear / 12),0) as decimal(3,0))                      useLimitYear         , " +       // 最低使用年限
					       " a.enterDate               enterDate      	    , " + 		// 入帳日期  
					       " a.originalBV              originalBV     		, " + 		// 原始入帳－總價
					       " '' as accountOrder  							, " + 		// 最新帳務次序 
					       " '' as accountValue  							, " + 		// 最新帳務金額						  
					       " a.differenceKind           differenceKind      , " + 		// 區分別		
					       " c.propertynocount			propertynoCount		  " +		// 財產編號子號2碼或3碼
					  " from UNTBU_AdjustDetail b left join UNTBU_BUILDING a on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo and a.differenceKind=b.differenceKind " +
					  " left join SYSPK_PROPERTYCODE c on b.propertyno = c.propertyno " +
					  " where b.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
					   " and b.ownership  = '1'" +
					   " and b.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
					   " and substring(b.propertyNo,1,5) between '20101' and '20106' " +	// 問題單1343,依照國產署講義限定資料範圍
					   " and b.verify     = 'Y'" +
					   " and b.adjustDate >= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateS())) +
					   " and b.adjustDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateE())) +
					   " union "+
					   //減損單
					   " select a.enterOrg                 enterOrg         , " +		// 入帳機關
					   " a.ownership                ownership        , " +		// 權屬    
				       " a.propertyNo               propertyNo       , " +		// 財產編號
				       " 'PH01'                     dataType         , " +		// 主(附)資料集代碼     
				       " 'X'                        actionType       , " +		// 動作屬性
				       " substring(a.propertyNo,1,7)   propertyNo1      , " +		// 財產編號-類項目節     
				       " substring(a.propertyNo,9,3)   propertyNo2      , " +		// 財產編號-號
				       " right('0000000'+cast(a.serialNo as varchar),7)       serialNo         , " +		// 財產編號-序號
				       " substring(a.propertyKind,2,1) propertyKind     , " +		// 公用區分              
				       " a.valuable                    valuable         , " +		// 珍貴財產註記
				       " a.propertyName1            propertyName1 	    , " +		// 財產別名              
				       " a.fundType                  fundType           , " +		// 基金財產註記
				       " 'N'                        unregisteredBuilding , " +		// 未登記建物  
				       " substring(a.signNo,1,1)       signNo1          , " +		// 建物標示-縣市         
				       " substring(a.signNo,4,4)       signNo3          , " +		// 建物標示-地段         
				       " substring(a.signNo,8,5)       signNo4          , " +		// 建物標示-地號母號     
				       " substring(a.signNo,13,3)      signNo5          , " +		// 建物標示-地號子號     
				       " substring(a.signNo,2,2)       signNo2          , " +		// 建物標示-鄉鎮市區
				       " a.doorPlatevillage1       doorPlatevillage1    , " +		// 地址-村/里(中文名稱) 
				       " a.doorPlatevillage2       doorPlatevillage2    , " +		// 地址-村/里(代碼) 
				       " a.doorplateRd1       	   doorplateRd1         , " +		// 地址-路/街/大道(中文名稱)
				       " a.doorplateRd2            doorplateRd2         , " +		// 地址-路/街/大道(代碼) 
				       " a.doorplateSec      	   doorplateSec         , " +		// 地址-段 
				       " null 					   doorPlateArea   		, " +		// 地址-地區
				       " a.doorplateLn   		   doorplateLn   		, " +		// 地址-巷 
				       " a.doorplateAly      	   doorplateAly         , " +		// 地址-弄 
				       " a.doorplateNo             doorplateNo          , " +		// 地址-號
				       " a.doorplateFloor1         doorplateFloor1      , " +		// 地址-樓
				       " null                      proofDoc1            , " + 		// 權狀字號-權狀年      
				       " null                      proofDoc2            , " + 		// 權狀字號-權狀字      
				       " null                      proofDoc3            , " + 		// 權狀字號-權狀號
				       " a.CArea			       CArea                , " +		// 主建物面積（㎡） 
				       " a.SArea       			   SArea                , " +		// 附屬建物面積（㎡）
				       " a.area       			   area                 , " +		// 合計面積 
				       " a.ownershipDate           ownershipDate  		, " +		// 國有登記日期
				       " a.buildDate       		   buildDate    		, " +		// 建築日期
				       " a.holdNume         	   holdNume				,  " +	    // 權利範圍－分子    
				       " a.holdDeno       		   holdDeno				,  " +	    // 權利範圍－分母
				       " null      				   construction1    	, " +		// 房屋構造-層
				       " null     				   construction2    	, " +		// 房屋構造-造
				       " a.sourceKind              sourceKind           , " +		// 財產來源  
				       " a.sourceDate              sourceDate           , " + 		// 取得日期
				       " a.sourceDoc               sourceDoc            , " + 		// 取得文號
				       " cast(ROUND((a.otherlimityear / 12),0) as decimal(3,0))                      useLimitYear         , " +       // 最低使用年限
				       " a.enterDate               enterDate      	    , " + 		// 入帳日期  
				       " a.originalBV              originalBV     		, " + 		// 原始入帳－總價
				       " '' as accountOrder  							, " + 		// 最新帳務次序 
				       " '' as accountValue  							, " + 		// 最新帳務金額						  
				       " a.differenceKind           differenceKind      , " + 		// 區分別	
				       " c.propertynocount			propertynoCount		  " +		// 財產編號子號2碼或3碼
				  " from UNTBU_ReduceDetail b left join UNTBU_BUILDING a on a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo and a.differenceKind=b.differenceKind " +
				  " left join SYSPK_PROPERTYCODE c on b.propertyno = c.propertyno " +
				  " where b.enterOrg   = " + Common.sqlChar(this.getQ_enterOrg()) +
				   " and b.ownership  = '1'" +
				   " and b.differenceKind   = " + Common.sqlChar(this.getQ_differenceKind()) +
				   " and substring(b.propertyNo,1,5) between '20101' and '20106' " +	// 問題單1343,依照國產署講義限定資料範圍
				   " and b.verify     = 'Y'" +
				   " and b.reduceDate >= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateS())) +
				   " and b.reduceDate <= " + Common.sqlChar(ul._transToCE_Year(this.getQ_changeDateE())) +
				 " order by  enterOrg, ownership, propertyNo, serialNo" ;

			
			return sql;
		}
		
		private String getSQL_StepCheckSignno(){
			UNTCH_Tools ul = new UNTCH_Tools();
			String sql= " select " + 
							   " ( select x.organaname from SYSCA_ORGAN x where x.organid = a.enterorg ) as enterorg, " +
						       " ( select x.codename from SYSCA_CODE x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownership, " +
						       " ( select x.codename from SYSCA_CODE x where x.codekindid = 'DFK' and x.codeid = a.differencekind ) as differencekind, " +
						       " a.propertyno, " +
						       " a.serialno" +
						  " from UNTBU_BUILDING a left join SYSPK_PROPERTYCODE b on a.propertyno = b.propertyno " +
						 " where a.enterorg   = " + Common.sqlChar(this.getQ_enterOrg()) +
						   " and a.ownership  = '1'" +
						   " and substring(a.propertyno,1,5) between '20101' and '20106' " +	// 依照國產署講義限定資料範圍
						   " and a.verify = 'Y'" +
						   " and a.datastate = '1' " +
						   " and (isnull(a.signno, '') = '' or len(a.signno) < 15) " +
						 " order by a.enterorg, a.ownership, a.differencekind, a.propertyno, a.serialno" ;
			return sql;
		}
		
		public DefaultTableModel getResultModel() throws Exception {
			DefaultTableModel model = null;
			Database db = new Database();
			
			try {
				String[] columns;
				columns = new String[] { "f0", "f1", "f2", "f3", "f4" }; // 5pk
				
				Vector<Object[]> rowData = new Vector<Object[]>();
				String execSQL = getSQL_StepCheckSignno();
				
				ResultSet rs = db.getForwardStatement().executeQuery(execSQL);
				
				while (rs.next()) {
					Object[] data = new Object[5];
					data[0] = Common.checkGet(rs.getString("enterorg"));
					data[1] = Common.checkGet(rs.getString("ownership"));
					data[2] = Common.checkGet(rs.getString("differencekind"));
					data[3] = Common.checkGet(rs.getString("propertyno"));
					data[4] = Common.checkGet(rs.getString("serialno"));
					
					rowData.addElement(data);
				}
				
				if (rowData != null && rowData.size() > 0) {
					model = new DefaultTableModel();
					Object[][] data = new Object[0][0];
					data = (Object[][]) rowData.toArray(data);
					model.setDataVector(data, columns);
				}
			} catch (Exception x) {
				x.printStackTrace();
			} finally {
				db.closeAll();
			}
			return model;
		}
}
