package unt.ac;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TDlib_Simple.com.src.SQLCreator;
import TDlib_Simple.tools.src.LogTools;
import unt.ch.UNTCH_Tools;
import util.*;

public class UNTAC005F extends SuperBean{
	private LogTools log = new LogTools();
	
	//=================================================
	public void calculateBalanceInMonth(){
		this.setClosing2ID(getUserID());
		this.setClosing2Name(getUserName());
		this.setClosing2Date(Datetime.getYYYMMDD());
		
		execCalculateBalanceInMonth(true);
		setErrorMsg("關帳執行完成");
	}
	
	//=================================================
		public void execCalculateBalanceInMonth(boolean verify){
			int getcut = 0;
			SQLCreator sc = new SQLCreator();
			String[] strKeys = null;
			
			if(getsKeySet()!=null){
				getcut = getsKeySet().length;	//有勾選的list中資料數
	
				
				for(int i = 0; i < getcut; i++){
					strKeys = getsKeySet()[i].split(",");
					
					this.setEnterOrg(strKeys[0]);
					this.setDifferenceKind(strKeys[1]);
					YYYMM = strKeys[2];
					
					if(verify){			calDatePlusOne();
					}else{				calDateMinusOne();
					}
					
					this.setClosing2YM(YYYMM);
					
					String updateSql = "";					
					if(verify){			
						updateSql = sc._obtainSQLforUpdate("UNTAC_CLOSINGPT", getPKMap(), getClosingRecordMap());
					}else{
						updateSql = sc._obtainSQLforUpdate("UNTAC_CLOSINGPT", getPKMap(), getRestorationRecordMap());
					}
					
					execData(updateSql);
					
				}
			}	
		}
			
	//=================================================
	
	public ArrayList queryAll() throws Exception{
		Database db = null;
		ArrayList objList=new ArrayList();
		int counter=0;
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append("select * from UNTAC_CLOSINGPT where 1=1")
				.append(" and enterorg = " + Common.sqlChar(this.getOrganID()))
				.append(" order by enterorg, differencekind");
			
			//============================================================
			log._execLogDebug(sql.toString());
			//============================================================
			
			db = new Database();
			ResultSet rs = db.querySQL(sql.toString());			
			UNTCH_Tools ut = new UNTCH_Tools();
			while (rs.next()){
				counter++;
				String rowArray[]=new String[9];
				rowArray[0] = Common.get(rs.getString("enterorg"));
				rowArray[1] = ut._getOrganSName(Common.get(rs.getString("enterorg")));
				rowArray[2] = Common.get(rs.getString("differencekind"));
				rowArray[3] = ut._getDifferenceKindName(Common.get(rs.getString("differencekind"))); 
				rowArray[4] = ut._transToROC_Year(Common.get(rs.getString("closing2YM")));
				rowArray[5] = Common.get(rs.getString("closing2name")); 
				rowArray[6] = ut._transToROC_Year(Common.get(rs.getString("closing2date")));
				rowArray[7] = Common.get(rs.getString("restoration2name")); 
				rowArray[8] = ut._transToROC_Year(Common.get(rs.getString("restoration2date"))); 
				
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
	
		
	//=================================================
		//計算年月加一
		private void calDatePlusOne(){
			int yyy = Integer.parseInt(YYYMM.substring(0,3));
			int mm = Integer.parseInt(YYYMM.substring(3));
			
			mm += 1;

			if(mm > 12){
				yyy += 1;
				mm -= 12;
			}
			
			YYYMM = Common.formatFrontZero(String.valueOf(yyy), 3) + 
						Common.formatFrontZero(String.valueOf(mm), 2);
			
		}
		
	//=================================================
		//計算年月減一
		public void calDateMinusOne(){
					int yyy = Integer.parseInt(YYYMM.substring(0,3));
					int mm = Integer.parseInt(YYYMM.substring(3));
					
					mm -= 1;
			
					if(mm < 1){
						yyy -= 1;
						mm += 12;
					}
					
					YYYMM = Common.formatFrontZero(String.valueOf(yyy), 3) + 
								Common.formatFrontZero(String.valueOf(mm), 2);
					
				}		
		
	//=================================================
		
		private String execData(String sql){
			Database db = new Database();
			String result = "";
			try{
				db.excuteSQL(new String[]{sql});				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
			return result;
		}
		
	//=================================================
	
	private String isAdminManager;
	private String organID;
	private String userID;
	private String userName;
	private String roleid;
	public String getIsAdminManager() {return checkGet(isAdminManager);}
	public void setIsAdminManager(String isAdminManager) {this.isAdminManager = checkSet(isAdminManager);}
	public String getOrganID() {return checkGet(organID);}
	public void setOrganID(String organID) {this.organID = checkSet(organID);}
	public String getUserID() {return checkGet(userID);}
	public void setUserID(String userID) {this.userID = checkSet(userID);}
	public String getUserName() {return checkGet(userName);}
	public void setUserName(String userName) {this.userName = checkSet(userName);}
	public String getRoleid() {return checkGet(roleid);}
	public void setRoleid(String roleid) {this.roleid = checkSet(roleid);}

	private String enterOrg;
	private String differenceKind;
	private String closing1YM;
	private String closing2YM;
	private String closing2ID;
	private String closing2Name;
	private String closing2Date;
	private String restoration2ID;
	private String restoration2Name;
	private String restoration2Date;
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getDifferenceKind() {return checkGet(differenceKind);}
	public void setDifferenceKind(String differenceKind) {this.differenceKind = checkSet(differenceKind);}
	public String getClosing1YM() {return checkGet(closing1YM);}
	public void setClosing1YM(String closing1ym) {closing1YM = checkSet(closing1ym);}
	public String getClosing2YM() {return checkGet(closing2YM);}
	public void setClosing2YM(String closing2ym) {closing2YM = checkSet(closing2ym);}
	public String getClosing2ID() {return checkGet(closing2ID);}
	public void setClosing2ID(String closing2id) {closing2ID = checkSet(closing2id);}
	public String getClosing2Name() {return checkGet(closing2Name);}
	public void setClosing2Name(String closing2Name) {this.closing2Name = checkSet(closing2Name);}
	public String getClosing2Date() {return checkGet(closing2Date);}
	public void setClosing2Date(String closing2Date) {this.closing2Date = checkSet(closing2Date);}
	public String getRestoration2ID() {return checkGet(restoration2ID);}
	public void setRestoration2ID(String restoration2id) {restoration2ID = checkSet(restoration2id);}
	public String getRestoration2Name() {return checkGet(restoration2Name);}
	public void setRestoration2Name(String restoration2Name) {this.restoration2Name = checkSet(restoration2Name);}
	public String getRestoration2Date() {return checkGet(restoration2Date);}
	public void setRestoration2Date(String restoration2Date) {this.restoration2Date = checkSet(restoration2Date);}

	private String strKeySet[] = null;
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }
	
	private String toggleAll;
	public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
	public void setToggleAll(String s){ toggleAll=checkSet(s); }
	
	private String q_calyear;
	private String q_calmonth;
	public String getQ_calyear() {return checkGet(q_calyear);}
	public void setQ_calyear(String q_calyear) {this.q_calyear = checkSet(q_calyear);}
	public String getQ_calmonth() {return checkGet(q_calmonth);}
	public void setQ_calmonth(String q_calmonth) {this.q_calmonth = checkSet(q_calmonth);}
	
	private String YYYMM;	
	public void setYYYMM(String s){this.YYYMM = s;}
	
	private String[] q_closing2YM;
	public String[] getQ_closing2YM() {return q_closing2YM;}
	public void setQ_closing2YM(String[] q_closing2YM) {this.q_closing2YM = q_closing2YM;}
	
	//=================================================
	private Map getPKMap(){
		Map map = new HashMap();
		
		map.put("EnterOrg", getEnterOrg());
		map.put("DifferenceKind", getDifferenceKind());
		
		return map;
	}
	
	private Map getClosingRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
				
		map.put("Closing2YM", ut._transToCE_Year(getClosing2YM()));
		map.put("Closing2ID", getClosing2ID());
		map.put("Closing2Name", getClosing2Name());
		map.put("Closing2Date", ut._transToCE_Year(getClosing2Date()));
		
		return map;
	}
	
	private Map getRestorationRecordMap(){
		Map map = new HashMap();
		UNTCH_Tools ut = new UNTCH_Tools();
				
		map.put("Closing2YM", ut._transToCE_Year(getClosing2YM()));
		map.put("Restoration2ID", getRestoration2ID());
		map.put("Restoration2Name", getRestoration2Name());
		map.put("Restoration2Date", ut._transToCE_Year(getRestoration2Date()));
		
		return map;
	}

	//=================================================

}