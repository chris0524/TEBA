/*
*<br>程式目的：財產盤點數量清冊 
*<br>程式代號：UNTPD023R
*<br>撰寫日期：103/10/01
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.pd;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import TDlib_Simple.tools.src.LogTools;

public class UNTPD023R extends SuperBean{
		
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_ownership;
	private String q_propertyKind;
	private String q_propertyKind1;
	private String q_fundType;
	private String q_valuable;
	private String q_keepUnit;
	private String q_reportDate;
	private String q_pageWay;
//	private String q_enterDate;
//	private String q_differencekind;
	private String year;
	private String month;
	private String day_start;
	private String day_end;
	private LogTools log = new LogTools();
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
	public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }
	public String getQ_propertyKind1(){ return checkGet(q_propertyKind1); }
	public void setQ_propertyKind1(String s){ q_propertyKind1=checkSet(s); }
	public String getQ_fundType(){ return checkGet(q_fundType); }
	public void setQ_fundType(String s){ q_fundType=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_keepUnit(){ return checkGet(q_keepUnit); }
	public void setQ_keepUnit(String s){ q_keepUnit=checkSet(s); }
	public String getQ_reportDate(){ return checkGet(q_reportDate); }
	public void setQ_reportDate(String s){ q_reportDate=checkSet(s); }
	public String getQ_pageWay(){ return checkGet(q_pageWay); }
	public void setQ_pageWay(String s){ q_pageWay=checkSet(s); }
//	public String getQ_enterDate() { return checkGet(q_enterDate);}
//	public void setQ_enterDate(String s) {q_enterDate = checkSet(s);}
//	public String getQ_differencekind() {return checkGet(q_differencekind);}
//	public void setQ_differencekind(String s) {q_differencekind = checkSet(s);}
	
	private String isOrganManager;
	private String isAdminManager;
	private String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

	String fileName;

	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }

	
	
	//=================================================================
	//	各Table的查詢SQL組成
	//=================================================================
	public DefaultTableModel getResultModel() throws Exception {
	    DefaultTableModel model = null;
	    Database db = null;
	    try {
	        // 建立資料庫連線
	        db = new Database();
	        String org = Common.sqlChar(getQ_enterOrg());

	        // 構建 SQL：統計各類別數量
	        StringBuilder stb = new StringBuilder();
	        stb.append("select '土地'            as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertyno like '10%' ")
	           .append(" union all ")
	           .append("select '土改'            as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertyno like '11%' ")
	           .append(" union all ")
	           .append("select '建物'            as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertyno like '20%' ")
	           .append(" union all ")
	           .append("select '交通及運輸設備' as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertytypename='交通及運輸設備' ")
	           .append(" union all ")
	           .append("select '機械及設備'     as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertytypename='機械及設備' ")
	           .append(" union all ")
	           .append("select '雜項設備'       as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertytypename='雜項設備' ")
	           .append(" union all ")
	           .append("select '有價證券'       as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertyno like '90%' ")
	           .append(" union all ")
	           .append("select '權利'            as category, count(*) as cnt ")
	           .append(" from UNTPD_CHECKMOVABLE ")
	           .append(" where enterorg=").append(org)
	           .append(" and propertyno like '80%'");
	        log._execLogDebug(stb.toString());
	        Common.insertRecordSql(
	            stb.toString(),
	            this.getOrganID(),
	            this.getUserID(),
	            "UNTPD023R",
	            this.getObjPath().replaceAll("&gt;", ">")
	        );

	        // 執行查詢並收集結果
	        List<String> categories = new ArrayList<>();
	        List<Integer> counts = new ArrayList<>();
	        ResultSet rs = db.querySQL(stb.toString());
	        try {
	            while (rs.next()) {
	                categories.add(rs.getString("category"));
	                counts   .add(rs.getInt   ("cnt"));
	            }
	        } finally {
	            rs.close();
	        }

	        // 建立 TableModel
	        String[] columnNames = { "detail01", "detail02" };
	        Object[][] data = new Object[categories.size()][2];
	        for (int i = 0; i < categories.size(); i++) {
	            data[i][0] = categories.get(i);
	            data[i][1] = counts   .get(i);
	        }
	        model = new DefaultTableModel(data, columnNames);
	    } catch (Exception x) {
	        log._execLogError(x.getMessage());
	    } finally {
	        if (db != null) db.closeAll();
	    }
	    return model;
	}
}
