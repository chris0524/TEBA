package unt.ac;

import java.sql.ResultSet;
import java.util.ArrayList;

import unt.ch.UNTCH001F01;
import unt.ch.UNTCH002F01;
import unt.ch.UNTCH003F01;
import unt.ch.UNTCH004F01;
import unt.ch.UNTCH_Tools;
import unt.mp.UNTMP021F;
import util.Common;
import util.Database;
import util.SuperBean;
import TDlib_Simple.tools.src.LogTools;

public class UNTAC001F extends SuperBean{

	private LogTools log = new LogTools();
	
	public void update(){
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
				result = Integer.parseInt(s);
			}
			return result;
		}
	
			private void updateForAddProof(String[] strKeys){
				UNTCH001F01 untch001f01 = new UNTCH001F01();
				
				try{
					untch001f01.setEnterOrg(strKeys[0]);
					untch001f01.setOwnership(strKeys[1]);
					untch001f01.setCaseNo(strKeys[2]);
					untch001f01.setDifferenceKind(strKeys[3]);
					
					int row = toInt(strKeys[5]);
					
					untch001f01._execQueryOne();
					
					untch001f01.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untch001f01.setEnterDate(getQ_enterDate()[row]);
					}
					if(!"".equals(getQ_summonsNo()[row])){
						untch001f01.setSummonsNo(getQ_summonsNo()[row]);
					}
					if(!"".equals(getQ_summonsDate()[row])){
						untch001f01.setSummonsDate(getQ_summonsDate()[row]);
					}
					
					untch001f01._execUpdate("UNTLA_ADDPROOF");
					
					setStateUpdateSuccess();
				}catch(Exception e){
					log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untch001f01 = null;
				}
			}
			
			private void updateForMoveProof(String[] strKeys){
				UNTCH002F01 untch002f01 = new UNTCH002F01();
				
				try{	
					untch002f01.setEnterOrg(strKeys[0]);
					untch002f01.setOwnership(strKeys[1]);
					untch002f01.setCaseNo(strKeys[2]);
					
					int row = toInt(strKeys[5]);
					
					untch002f01 = untch002f01.queryOne();
					untch002f01.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untch002f01.setMoveDate(getQ_enterDate()[row]);
					}
					untch002f01.update();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untch002f01 = null;
				}
			}
			
			private void updateForAdjustProof(String[] strKeys){
				UNTCH003F01 untch003f01 = new UNTCH003F01();
				
				try{						
					untch003f01.setEnterOrg(strKeys[0]);
					untch003f01.setOwnership(strKeys[1]);
					untch003f01.setCaseNo(strKeys[2]);
					untch003f01.setDifferenceKind(strKeys[3]);
					
					int row = toInt(strKeys[5]);
					
					untch003f01._execQueryOne();
					
					untch003f01.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untch003f01.setAdjustDate(getQ_enterDate()[row]);
					}
					if(!"".equals(getQ_summonsNo()[row])){
						untch003f01.setSummonsNo(getQ_summonsNo()[row]);
					}
					if(!"".equals(getQ_summonsDate()[row])){
						untch003f01.setSummonsDate(getQ_summonsDate()[row]);
					}
					
					untch003f01._execUpdate("UNTLA_ADJUSTPROOF");
					untch003f01._updateMOVABLE();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untch003f01 = null;
				}
			}
			
			private void updateForReduceProof(String[] strKeys){
				UNTCH004F01 untch004f01 = new UNTCH004F01();
				
				try{
					untch004f01.setEnterOrg(strKeys[0]);
					untch004f01.setOwnership(strKeys[1]);
					untch004f01.setCaseNo(strKeys[2]);
					untch004f01.setDifferenceKind(strKeys[3]);
					
					int row = toInt(strKeys[5]);
					
					untch004f01._execQueryOne();
					
					untch004f01.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untch004f01.setReduceDate(getQ_enterDate()[row]);
					}
					if(!"".equals(getQ_summonsNo()[row])){
						untch004f01.setSummonsNo(getQ_summonsNo()[row]);
					}
					if(!"".equals(getQ_summonsDate()[row])){
						untch004f01.setSummonsDate(getQ_summonsDate()[row]);
					}
					
					untch004f01._execUpdate("UNTLA_REDUCEPROOF");
					untch004f01._updateMOVABLEDETAIL("update");	
					untch004f01._updateMOVABLE("update");
					
					setStateUpdateSuccess();
				}catch(Exception e){
					log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untch004f01 = null;
				}
			}
			
			private void updateForDealProof(String[] strKeys){
				UNTMP021F untmp021f = new UNTMP021F();
				try{
					untmp021f.setEnterOrg(strKeys[0]);
					untmp021f.setCaseNo1(strKeys[2]);
					untmp021f.setDifferenceKind(strKeys[3]);
					
					int row = toInt(strKeys[5]);
					
					untmp021f = untmp021f.queryOne();
					untmp021f.setVerify("Y");
					if(!"".equals(getQ_enterDate()[row])){
						untmp021f.setDealDate(getQ_enterDate()[row]);
					}
					
					untmp021f.update();
					
					setStateUpdateSuccess();
				}catch(Exception e){
					log._execLogError(e.getMessage());
					setStateUpdateError();
				}finally{
					untmp021f = null;
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
				.append(" order by a.enterorg,")
				.append(" case a.prooftype")
				.append(" when '增加單' then 1 ")
				.append(" when '增減值單' then 2 ")
				.append(" when '減損單' then 3 ")
				.append(" when '移動單' then 4 ")
				.append(" when '處理單' then 5 ")
				.append(" end,")
				.append(" a.caseno");
			
			//============================================================
			log._execLogDebug(sql.toString());
			//============================================================
			
			db = new Database();
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
				rowArray[7] = Common.get(rs.getString("writeunitname"));
				rowArray[8] = ut._queryData("organsname")._from("SYSCA_ORGAN")._with(" and organid = '" + rs.getString("enterorg") + "'")._toString();
				
				objList.add(rowArray);
				if (counter==getListLimit()){
					setErrorMsg(getListLimitError());
					break;
				}
			}
			setStateQueryAllSuccess();
		} catch (Exception e) {
			log._execLogError(e.getMessage());
		} finally {
			db.closeAll();
		}
		return objList;
	}

			//增加單
			private String obtainData_forADDPROOF(){
				StringBuilder stb = new StringBuilder();
				
				String[] tables = {"UNTLA_LAND",
									"UNTBU_BUILDING",
									"UNTRF_ATTACHMENT",
									"UNTMP_MOVABLE",
									"UNTRT_ADDPROOF",
									"UNTVP_ADDPROOF"};
				
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
					.append(" ( select b.unitname from UNTMP_KEEPUNIT b where b.enterorg = a.enterorg and b.unitno=a.writeunit ) as writeunitname ")
					.append(" from UNTLA_ADDPROOF a")
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
					.append(" ( select b.unitname from UNTMP_KEEPUNIT b where b.enterorg = a.enterorg and b.unitno=a.writeunit ) as writeunitname ")
					.append(" from UNTMP_MOVEPROOF a")
					.append(" where verify = 'N'")
					.append(" and (select COUNT(*) as count from UNTMP_MOVEDETAIL z where z.enterorg = a.enterorg and z.ownership = a.ownership  and z.caseno = a.caseno) > 0")
					.append(obtainAuthority());
				
				return stb.toString();
			}
			
			//增減值單
			private String obtainData_forADJUSTPROOF(){
				StringBuilder stb = new StringBuilder();
				
				String[] tables = {"UNTLA_ADJUSTDETAIL",
									"UNTBU_ADJUSTDETAIL",
									"UNTRF_ADJUSTDETAIL",
									"UNTMP_ADJUSTDETAIL",
									"UNTRT_ADJUSTPROOF",
									"UNTVP_ADJUSTPROOF"};
				
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
						.append(" ( select b.unitname from UNTMP_KEEPUNIT b where b.enterorg = a.enterorg and b.unitno=a.writeunit ) as writeunitname ")
						.append(" from UNTLA_ADJUSTPROOF a")
						.append(" where verify = 'N'")
						.append(" and (").append(obtainSQL_forCheckDetailCount(tables)).append(") > 0")
						.append(obtainAuthority());
				
				return stb.toString();
			}
			
			//減損單
			private String obtainData_forREDUCEPROOF(){
				StringBuilder stb = new StringBuilder();
				
				String[] tables = {"UNTLA_REDUCEDETAIL",
									"UNTBU_REDUCEDETAIL",
									"UNTRF_REDUCEDETAIL",
									"UNTMP_REDUCEDETAIL",
									"UNTRT_REDUCEPROOF",
									"UNTVP_REDUCEPROOF"};
				
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
						.append(" ( select b.unitname from UNTMP_KEEPUNIT b where b.enterorg = a.enterorg and b.unitno=a.writeunit ) as writeunitname ")
						.append(" from UNTLA_REDUCEPROOF a")
						.append(" where verify = 'N'")
						.append(" and (").append(obtainSQL_forCheckDetailCount(tables)).append(") > 0")
						.append(obtainAuthority());
				
				return stb.toString();
			}

			//處理單
			private String obtainData_forDEALPROOF(){
				StringBuilder stb = new StringBuilder();
				
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
					.append(" ( select b.unitname from UNTMP_KEEPUNIT b where b.enterorg = a.enterorg and b.unitno=a.writeunit ) as writeunitname ")
					.append(" from UNTMP_DEALPROOF a")
					.append(" where verify = 'N'")
					.append(" and (select COUNT(*) as count from UNTMP_DEALDETAIL z where z.enterorg = a.enterorg and z.differencekind = a.differencekind and z.caseno1=a.caseno1) > 0")
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
	
						stb.append(" select COUNT(*) as count from " + s + " z where z.enterorg = a.enterorg and z.ownership = a.ownership  and z.caseno = a.caseno and z.differencekind = a.differencekind");
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