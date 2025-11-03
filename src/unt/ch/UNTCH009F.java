package unt.ch;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import TDlib_Simple.tools.src.StringTools;

public class UNTCH009F extends SuperBean{
	private String keeperno;
	private String enterOrg;
	private String place1;
	private String place1Name;
	private String place;
	private String strKeySet[] = null;
	private String toggleAll;
	
	public String[] getsKeySet(){ return strKeySet; }
	public void setsKeySet(String[] s){ strKeySet=s; }
	public String getToggleAll(){ if (!"".equals(checkGet(toggleAll))) return "checked"; else return "unchecked"; }    
	public void setToggleAll(String s){ toggleAll=checkSet(s); }   
	
	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	
	public String getKeeperno() { return checkGet(keeperno); }
	public void setKeeperno(String keeperno) { this.keeperno = checkSet(keeperno); }
	public String getPlace1() { return checkGet(place1); }
	public void setPlace1(String place1) { this.place1 = checkSet(place1); }
	public String getPlace() { return checkGet(place); }
	public void setPlace(String place) { this.place = checkSet(place); }
	
	public String getPlace1Name() {
		return place1Name;
	}
	public void setPlace1Name(String place1Name) {
		this.place1Name = place1Name;
	}
	/**
	 * <br>
	 * <br>目的：依查詢欄位查詢多筆資料
	 * <br>參數：無
	 * <br>傳回：傳回ArrayList
	*/
	public ArrayList queryAll() throws Exception{
		Database db = null;
		ArrayList objList = null;
		
		try {
			StringBuilder sbSQL = new StringBuilder();
			StringBuilder getColumn = new StringBuilder();
			StringBuilder joinTable = new StringBuilder();
			
			joinTable.append(" left join UNTMP_KEEPUNIT b on b.enterorg = a.enterorg and b.unitno = a.keepunit")
				.append(" left join UNTMP_KEEPER c on c.enterorg = a.enterorg and c.keeperno = a.keeper")
				.append(" left join UNTMP_KEEPUNIT d on d.enterorg = a.enterorg and d.unitno = a.useunit")
				.append(" left join UNTMP_KEEPER e on e.enterorg = a.enterorg and e.keeperno = a.userno");
			//#region 需要的欄位
			getColumn.append(" a.enterorg, ")
				.append(" a.ownership, ")
				.append(" a.differencekind, ")
				.append(" a.propertyno, ")
				.append(" a.serialno, ")
				.append(" a.propertyname1,")
				.append(" b.unitname as keepunitname,")
				.append(" c.keepername as keepername,")
				.append(" d.unitname as useunitname,")
				.append(" e.keepername as username,")
				.append(" a.place1,")
				.append(" a.place");
			//#end
			sbSQL.append("select ROW_NUMBER() over (order by propertyno, serialno) as rownum,* from (")
				.append("select")
				.append(getColumn.toString())
				.append(" from UNTLA_LAND a")
				.append(joinTable.toString())
				.append(" where 1=1")
				.append(getQueryCondition("LA"))
				.append(" union ")
				
				.append(" select").append(getColumn.toString())
				.append(" from UNTBU_BUILDING a")
				.append(joinTable.toString())
				.append(" where 1=1")
				.append(getQueryCondition("BU"))
				.append(" union ")
				
				.append(" select").append(getColumn.toString())
				.append(" from UNTRF_ATTACHMENT a")
				.append(joinTable.toString())
				.append(" where 1=1")
				.append(getQueryCondition("RF"))
				.append(" union ")
				
				.append(" select").append(getColumn.toString())
				.append(" from UNTMP_MOVABLEDETAIL a ")
				.append(joinTable.toString())
				.append(" where 1=1")
				.append(getQueryCondition("MP"))
				.append(" union ")
				
				.append(" select")
				.append(getColumn.toString())
				.append(" from UNTVP_ADDPROOF a")
				.append(joinTable.toString())
				.append(" where 1=1")
				.append(getQueryCondition("VP"))
				.append(" union ")
				
				.append(" select")
				.append(getColumn.toString())
				.append(" from UNTRT_ADDPROOF a")
				.append(joinTable.toString())
				.append(" where 1=1")
				.append(getQueryCondition("RT"))
				.append(") a");
		
			db = new Database();
			objList = new ArrayList();
			ResultSet rs = db.querySQL(sbSQL.toString(),true);
			
			UNTCH_Tools uctls = new UNTCH_Tools();
			StringTools st = new StringTools();
				while(rs.next()) {
					int fieldIndex = 0;
					String rowArray[]=new String[14];
					rowArray[fieldIndex++] = checkGet(rs.getString("enterorg")); //0
					rowArray[fieldIndex++] = checkGet(rs.getString("ownership"));//1
					rowArray[fieldIndex++] = checkGet(rs.getString("differencekind"));//2
					rowArray[fieldIndex++] = uctls._getDifferenceKindName(rs.getString("differencekind"));//3
					rowArray[fieldIndex++] = checkGet(rs.getString("propertyno"));//4
					rowArray[fieldIndex++] = checkGet(rs.getString("serialno"));//5
					rowArray[fieldIndex++] = uctls._getPropertyNoName(enterOrg, rs.getString("propertyno"));//6
					rowArray[fieldIndex++] = checkGet(rs.getString("propertyname1"));//7 
					rowArray[fieldIndex++] = checkGet(rs.getString("keepunitname"));//8
					rowArray[fieldIndex++] = checkGet(rs.getString("keepername"));//9
					rowArray[fieldIndex++] = checkGet(rs.getString("useunitname"));//10
					rowArray[fieldIndex++] = checkGet(rs.getString("username"));//11
					rowArray[fieldIndex++] = uctls.getPlace1Name(enterOrg, rs.getString("place1"));//12
					rowArray[fieldIndex++] = checkGet(rs.getString("place"));//13
			
				objList.add(rowArray);
				}
			setStateQueryAllSuccess();		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.closeAll();
			}
		}
		return objList;
	}
	
	
	private String getQueryCondition(String table){
		StringBuilder sqlCondition = new StringBuilder();
		sqlCondition.append(" and a.enterorg = ")
			.append(Common.sqlChar(this.getEnterOrg()))
			.append(" and a.keeper = ")
			.append(Common.sqlChar(this.getKeeperno()))
			.append(" and a.datastate = '1'");
		return sqlCondition.toString();
	}
	
	public void update(){
		StringBuffer sbSQL = new StringBuffer("");
		int getcut=0;
		if(getsKeySet()!=null)
			getcut = getsKeySet().length;	//有勾選的list中資料數
		String[] strKeys = null;
		int i = 0;
		Database db = new Database();
		try {
			for (i=0; i<getcut; i++) {	
				strKeys = getsKeySet()[i].split(",");

				String checkPropertyNo1 = strKeys[3].substring(0,1);
				String checkPropertyNo3 = strKeys[3].substring(0,3);
				String tableName = "";
				
				if("111".equals(checkPropertyNo3)){
					tableName = "UNTRF_ATTACHMENT";
				}else if("1".equals(checkPropertyNo1)){
					tableName = "UNTLA_LAND";
				}else if("2".equals(checkPropertyNo1)){
					tableName = "UNTBU_BUILDING";
				}else if("3".equals(checkPropertyNo1) || "4".equals(checkPropertyNo1) || "5".equals(checkPropertyNo1)){
					tableName = "UNTMP_MOVABLEDETAIL";
				}else if("8".equals(checkPropertyNo1)){
					tableName = "UNTRT_ADDPROOF";
				}else if("9".equals(checkPropertyNo1)){
					tableName = "UNTVP_ADDPROOF";
				}
				
				StringBuilder updateSql = new StringBuilder();
				updateSql.append("update ").append(tableName).append(" set ")
					.append("place = N").append(Common.sqlChar(this.getPlace()))
					.append(",place1 = N").append(Common.sqlChar(this.getPlace1()))
					.append(",editid = ").append(Common.sqlChar(this.getUserID()))
					.append(",editdate = ").append(Common.sqlChar(Datetime.getYYYYMMDD()))
					.append(",edittime = ").append(Common.sqlChar(Datetime.getHHMMSS()))
					.append("where enterorg = ").append(Common.sqlChar(strKeys[0]))
					.append("and ownership = ").append(Common.sqlChar(strKeys[1]))
					.append("and differencekind = ").append(Common.sqlChar(strKeys[2]))
					.append("and propertyno = ").append(Common.sqlChar(strKeys[3]))
					.append("and serialno = ").append(Common.sqlChar(strKeys[4]));
				db.excuteSQL(updateSql.toString());		
			}
			setStateUpdateSuccess();
			setErrorMsg("修改完成");
		} catch (Exception e) {
			super.setStateUpdateError();
			e.printStackTrace();
		}finally {
			if (db != null) {
				db.closeAll();
			}
		}
	}
}