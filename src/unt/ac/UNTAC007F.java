package unt.ac;

import java.sql.ResultSet;
import java.util.ArrayList;

import TDlib_Simple.tools.src.LogTools;


import unt.ch.UNTCH_Tools;
import unt.ne.UNTNE001F;
import unt.ne.UNTNE008F;
import unt.ne.UNTNE024F;
import unt.ne.UNTNE013F;
import unt.ne.UNTNE020F;
import util.*;


public class UNTAC007F extends SuperBean{
	
//private LogTools log = new LogTools();
	
	public void update(){
		UNTCH_Tools ul = new UNTCH_Tools();
		int getcut = 0;
		String[] strKeys = null;
		
		if(getsKeySet()!=null){
			getcut = getsKeySet().length;	//有勾選的list中資料數

			for(int i = 0; i < getcut; i++){
				strKeys = getsKeySet()[i].split(",");
				
				if("增加單".equals(strKeys[4])){			updateForAddProof(strKeys);
				}else if("移動單".equals(strKeys[4])){	updateForMoveProof(strKeys);
				}else if("增減值單".equals(strKeys[4])){	updateForAdjustProof(strKeys);
				}else if("減損單".equals(strKeys[4])){	updateForReduceProof(strKeys);
				}else if("處理單".equals(strKeys[4])){	updateForDealProof(strKeys);
				}
			}
			
			if("updateSuccess".equals(getState())){		setErrorMsg("入帳完成");
			}else{										setErrorMsg("入帳失敗");
			}
			
		}		
	}
	
		private int toInt(String s){
			int result = 0;			
			if(!"".equals(s)){
				result = Integer.parseInt(s) ;
			}
			return result;
		}
	
			private void updateForAddProof(String[] strKeys){
				UNTNE001F untne001f = new UNTNE001F();
				
				try{
					untne001f.setEnterOrg(strKeys[0]);
					untne001f.setOwnership(strKeys[1]);
					untne001f.setCaseNo(strKeys[2]);
					untne001f.setDifferenceKind(strKeys[3]);
					
					int row = toInt(strKeys[5]);
					
					untne001f.queryOne();
					
					untne001f.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untne001f.setEnterDate(getQ_enterDate()[row]);
					}
					if(!"".equals(getQ_summonsNo()[row])){
						untne001f.setSummonsNo(getQ_summonsNo()[row]);
					}
					if(!"".equals(getQ_summonsDate()[row])){
						untne001f.setSummonsDate(getQ_summonsDate()[row]);
					}
					untne001f.update();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					//log._execLogError(e.getMessage());
					e.printStackTrace();
					setStateUpdateError();
				}finally{
					untne001f = null;
				}
			}
			
			private void updateForMoveProof(String[] strKeys){
				UNTNE008F untne008f = new UNTNE008F();
				
				try{	
					untne008f.setEnterOrg(strKeys[0]);
					untne008f.setOwnership(strKeys[1]);
					untne008f.setCaseNo(strKeys[2]);
					
					int row = toInt(strKeys[5]);
					
					untne008f = untne008f.queryOne();
					untne008f.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untne008f.setMoveDate(getQ_enterDate()[row]);
					}
					
					untne008f.update();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					e.printStackTrace();
					//log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untne008f = null;
				}
			}
			
			private void updateForAdjustProof(String[] strKeys){
				UNTNE024F untne024f = new UNTNE024F();
				
				try{						
					untne024f.setEnterOrg(strKeys[0]);
					untne024f.setOwnership(strKeys[1]);
					untne024f.setCaseNo(strKeys[2]);
					untne024f.setDifferenceKind(strKeys[3]);
					
					int row = toInt(strKeys[5]);
					
					untne024f.queryOne();
					
					untne024f.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untne024f.setAdjustDate(getQ_enterDate()[row]);
					}
					if(!"".equals(getQ_summonsNo()[row])){
						untne024f.setSummonsNo(getQ_summonsNo()[row]);
					}
					if(!"".equals(getQ_summonsDate()[row])){
						untne024f.setSummonsDate(getQ_summonsDate()[row]);
					}
					untne024f.update();
					//untne024f._updateMOVABLE();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					e.printStackTrace();
					//log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untne024f = null;
				}
			}
			
			private void updateForReduceProof(String[] strKeys){
				UNTNE013F untne013f = new UNTNE013F();
				
				try{
					untne013f.setEnterOrg(strKeys[0]);
					untne013f.setOwnership(strKeys[1]);
					untne013f.setCaseNo(strKeys[2]);
					untne013f.setDifferenceKind(strKeys[3]);
					
					int row = toInt(strKeys[5]);
					
					untne013f.queryOne();
					
					untne013f.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untne013f.setReduceDate(getQ_enterDate()[row]);
					}
					if(!"".equals(getQ_summonsNo()[row])){
						untne013f.setSummonsNo(getQ_summonsNo()[row]);
					}
					if(!"".equals(getQ_summonsDate()[row])){
						untne013f.setSummonsDate(getQ_summonsDate()[row]);
					}
					untne013f.update();
					//untne013f._updateMOVABLE();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					e.printStackTrace();
					//log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untne013f = null;
				}
			}
			
			private void updateForDealProof(String[] strKeys){
				UNTNE020F untne020f = new UNTNE020F();
				
				try{
					int row = toInt(strKeys[5]);
					untne020f.setEnterOrg(strKeys[0]);
					untne020f.setCaseNo1(strKeys[2]);
					untne020f.setDifferenceKind(strKeys[3]);
					untne020f = untne020f.queryOne();
					untne020f.setVerify("Y");
					untne020f.setVerifyError("X");   
					if(!"".equals(getQ_enterDate()[row])){
						untne020f.setDealDate(getQ_enterDate()[row]);
					}

					untne020f.update();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					e.printStackTrace();
					//log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untne020f = null;
				}
			}
			
	//====================================================================	
	public ArrayList queryAll() throws Exception{
		Database db = null;
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("select * from (")
				.append(obtainData_forADDPROOF())
				.append(" union")
				.append(obtainData_forMOVEPROOF())
				.append(" union")
				.append(obtainData_forADJUSTPROOF())
				.append(" union")
				.append(obtainData_forREDUCEPROOF())
				.append(" union")
				.append(obtainData_forDEALPROOF())				
				.append(") a")
				.append(" order by")
				.append(" case a.prooftype")
				.append(" when '增加單' then 1 ")
				.append(" when '增減值單' then 2 ")
				.append(" when '減損單' then 3 ")
				.append(" when '移動單' then 4 ")
				.append(" when '處理單' then 5 ")
				.append(" end,")
				.append(" a.caseno");
			
			//============================================================
			//log._execLogDebug(sql.toString());
			//============================================================
			
			db = new Database();
			System.out.println("32423243========>"+sql.toString());
			ResultSet rs = db.querySQL_NoChange(sql.toString());			
			UNTCH_Tools ut = new UNTCH_Tools();
			while (rs.next()){
				counter++;
				String rowArray[]=new String[9];
				rowArray[0] = Common.get(rs.getString("enterorg"));
				rowArray[1] = Common.get(rs.getString("ownership")); 
				rowArray[2] = Common.get(rs.getString("caseno")); 
				rowArray[3] = Common.get(rs.getString("differencekind")); 
				rowArray[4] = Common.get(rs.getString("prooftype")); 
				rowArray[5] = Common.get(rs.getString("proofdata")); 
				rowArray[6] = ut._transToROC_Year(Common.get(rs.getString("writedate"))); 
				rowArray[7] = Common.get(rs.getString("writeunit"));
				rowArray[8] = ut._queryData("organsname")._from("SYSCA_ORGAN")._with(" and organid = '" + rs.getString("enterorg") + "'")._toString();
				
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			//log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}
		return objList;
	}

			//增加單
			private String obtainData_forADDPROOF(){
				StringBuilder stb = new StringBuilder();
				
				String[] tables = {"UNTNE_NONEXPDETAIL"};
				
				stb.append(" select")
					.append(" enterorg,")
					.append(" ownership,")
					.append(" caseno,")
					.append(" differencekind,")
					.append(" enterdate,")
					.append(" summonsno,")
					.append(" '增加單' as prooftype,")
					.append(" proofyear + '年' + proofdoc + '字第' + proofno + '號' as proofdata,")
					.append(" writedate,")
					.append(" writeunit")
					.append(" from UNTNE_ADDPROOF a")
					.append(" where verify = 'N'")
					.append(" and (").append(obtainSQL_forCheckDetailCount(tables)).append(") > 0")
					.append(obtainAuthority());					
				
				return stb.toString();
			}
			
			//移動單
			private String obtainData_forMOVEPROOF(){
				StringBuilder stb = new StringBuilder();
				
				stb.append(" select")					
					.append(" enterorg,")
					.append(" ownership,")
					.append(" caseno,")
					.append(" null as differencekind,")
					.append(" movedate as enterdate,")
					.append(" null as summonsno,")
					.append(" '移動單' as prooftype,")
					.append(" proofyear + '年' + proofdoc + '字第' + proofno + '號' as proofdata,")
					.append(" writedate,")
					.append(" writeunit")
					.append(" from UNTNE_MOVEPROOF a")
					.append(" where verify = 'N'")
					.append(" and (select COUNT(*) as count from UNTNE_MOVEDETAIL z where z.enterorg = a.enterorg and z.ownership = a.ownership  and z.caseno = a.caseno) > 0")
					.append(obtainAuthority());
				
				return stb.toString();
			}
			
			//增減值單
			private String obtainData_forADJUSTPROOF(){
				StringBuilder stb = new StringBuilder();
				
				String[] tables = {"UNTNE_ADJUSTDETAIL"};
				
				stb.append(" select")					
						.append(" enterorg,")
						.append(" ownership,")
						.append(" caseno,")
						.append(" differencekind,")
						.append(" adjustdate as enterdate,")
						.append(" summonsno,")
						.append(" '增減值單' as prooftype,")
						.append(" proofyear + '年' + proofdoc + '字第' + proofno + '號' as proofdata,")
						.append(" writedate,")
						.append(" writeunit")
						.append(" from UNTNE_ADJUSTPROOF a")
						.append(" where verify = 'N'")
						.append(" and (").append(obtainSQL_forCheckDetailCount(tables)).append(") > 0")
						.append(obtainAuthority());
				
				return stb.toString();
			}
			
			//減損單
			private String obtainData_forREDUCEPROOF(){
				StringBuilder stb = new StringBuilder();
				
				String[] tables = {"UNTNE_REDUCEDETAIL"};
				
				stb.append(" select")					
						.append(" enterorg,")
						.append(" ownership,")
						.append(" caseno,")
						.append(" differencekind,")
						.append(" reducedate as enterdate,")
						.append(" summonsno,")
						.append(" '減損單' as prooftype,")
						.append(" proofyear + '年' + proofdoc + '字第' + proofno + '號' as proofdata,")
						.append(" writedate,")
						.append(" writeunit")
						.append(" from UNTNE_REDUCEPROOF a")
						.append(" where verify = 'N'")
						.append(" and (").append(obtainSQL_forCheckDetailCount(tables)).append(") > 0")
						.append(obtainAuthority());
				
				return stb.toString();
			}

			//處理單
			private String obtainData_forDEALPROOF(){
				StringBuilder stb = new StringBuilder();
				String[] tables = {"UNTNE_DEALDETAIL"};
				stb.append(" select")					
					.append(" enterorg,")
					.append(" null as ownership,")
					.append(" caseno1 as caseno,")
					.append(" differencekind,")
					.append(" dealdate as enterdate,")
					.append(" null as summonsno,")
					.append(" '處理單' as prooftype,")
					.append(" proofyear + '年' + proofdoc + '字第' + proofno + '號' as proofdata,")
					.append(" writedate,")
					.append(" writeunit")
					.append(" from UNTNE_DEALPROOF a")
					.append(" where verify = 'N'")
					.append(" and (").append(obtainSQL_forCheckDetailCount(tables)).append(") > 0")
					.append(obtainAuthority());
				
				return stb.toString();
			}
	
				//檢查明細有無資料
				private String obtainSQL_forCheckDetailCount(String[] tables){
					StringBuilder stb = new StringBuilder();
					
					stb.append("select sum(count) from (");
					
					boolean isFirst = true;
					for(String s : tables){
						if(isFirst){		isFirst = false;				
						}else{				stb.append(" union");							
						}
						if(s.equals("UNTNE_DEALDETAIL")){
						stb.append(" select COUNT(*) as count from " + s + " z where z.enterorg = a.enterorg   and z.caseno1 = a.caseno1 and z.differencekind = a.differencekind");
						}
						else{
							stb.append(" select COUNT(*) as count from " + s + " z where z.enterorg = a.enterorg and z.ownership = a.ownership  and z.caseno = a.caseno and z.differencekind = a.differencekind");	
						}
					}
					
					stb.append(") a");
					
					return stb.toString();
				}
				
				//檢查權限
				private String obtainAuthority(){
					String result = "";
					
					if("Y".equals(this.getIsAdminManager())){
						
					}else{
						result = " and enterorg = " + Common.sqlChar(this.getOrganID());
					}
					
					return result;
				}
				
			
	//===============================================================
	private String organID;
	private String isAdminManager;
	public String getOrganID() {return checkGet(organID);}
	public void setOrganID(String organID) {this.organID = checkSet(organID);}
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}


	private String strKeySet[] = null;
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }
	
	private String toggleAll;
	public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
	public void setToggleAll(String s){ toggleAll=checkSet(s); }
	
	private String q_enterDate[] = null;
	private String q_summonsNo[] = null;
	private String q_summonsDate[] = null;
	public String[] getQ_enterDate() {return q_enterDate;}
	public void setQ_enterDate(String[] q_enterDate) {this.q_enterDate = q_enterDate;}
	public String[] getQ_summonsNo() {return q_summonsNo;}
	public void setQ_summonsNo(String[] q_summonsNo) {this.q_summonsNo = q_summonsNo;}
	public String[] getQ_summonsDate() {return q_summonsDate;}
	public void setQ_summonsDate(String[] q_summonsDate) {this.q_summonsDate = q_summonsDate;}
}