package unt.pd;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import unt.ch.UNTCH_Tools;
import util.Common;
import util.Database;
import util.Datetime;
import util.SuperBean;
import util.report.ReportUtil;
import TDlib_Simple.tools.src.LogTools;

/*
*<br>程式目的：財產盤存表 
*<br>程式代號：UNTPD007R
*<br>撰寫日期：103/09/02
*<br>程式作者：Jerry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/
public class UNTPD007R extends SuperBean{
		
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
	private String q_enterDate;
	private String q_differencekind;
	private String q_status;
	
	private String isOrganManager;
	private String isAdminManager;
	private String organID;
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
	public String getQ_enterDate() { return checkGet(q_enterDate);}
	public void setQ_enterDate(String s) {q_enterDate = checkSet(s);}
	public String getQ_status() {return checkGet(q_status);}
	public void setQ_status(String qStatus) {q_status = checkSet(qStatus);}
	public String getQ_differencekind() {return checkGet(q_differencekind);}
	public void setQ_differencekind(String s) {q_differencekind = checkSet(s);}
	
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

	private String year;
	private String month;
	private String day;
	private LogTools log = new LogTools();
	
	private String closingDate;        //資料截止日期
	private String fileName;
	private String propertyno;         //財產編號
	private String propertyname;       //財產名稱
	private String propertyunit;       //單位
	private String bookamount;         //帳面數量
	private String actualamount;       //實際數量
	private String notes;              //備註
	
	public String getClosingDate(){ return checkGet(closingDate); }
	public void setClosingDate(String s){ closingDate=checkSet(s); }
	
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	public String getPropertyno() {	return checkGet(propertyno); }
	public void setPropertyno(String s) { this.propertyno = checkSet(s); }
	
	public String getPropertyname() { return checkGet(propertyname); }
	public void setPropertyname(String s) { this.propertyname = checkSet(s); }
	
	public String getPropertyunit() { return checkGet(propertyunit); }
	public void setPropertyunit(String s) { this.propertyunit = checkSet(s); }
	
	public String getBookamount() {	return checkGet(bookamount); }
	public void setBookamount(String s) { this.bookamount = checkSet(s); }
	
	public String getActualamount() { return checkGet(actualamount); }
	public void setActualamount(String s) { this.actualamount = checkSet(s); }
	
	public String getNotes() { return checkGet(notes); }
	public void setNotes(String s) { this.notes = checkSet(s); }
	
	private String q_propertyNoS;
	private String q_propertyNoE;
	private String q_propertyNoSName;
	private String q_propertyNoEName;
	public String getQ_propertyNoS() {return checkGet(q_propertyNoS);}
	public void setQ_propertyNoS(String q_propertyNoS) {this.q_propertyNoS = checkSet(q_propertyNoS);}
	public String getQ_propertyNoE() {return checkGet(q_propertyNoE);}
	public void setQ_propertyNoE(String q_propertyNoE) {this.q_propertyNoE = checkSet(q_propertyNoE);}
	public String getQ_propertyNoSName() {return checkGet(q_propertyNoSName);}
	public void setQ_propertyNoSName(String q_propertyNoSName) {this.q_propertyNoSName = checkSet(q_propertyNoSName);}
	public String getQ_propertyNoEName() {return checkGet(q_propertyNoEName);}
	public void setQ_propertyNoEName(String q_propertyNoEName) {this.q_propertyNoEName = checkSet(q_propertyNoEName);}
	
	
	private StringBuilder doGetSQLforUNTPD_CHECKNONEXP(StringBuilder stb){
		stb.append(" select ")
		          .append("( select k.codename from SYSCA_CODE k where k.codeid = '"+getQ_differencekind()+"' and k.codekindid = 'DFK' ) as differencekindname,")
		          .append("c.propertyno,")
		          .append("c.propertyname,")
		          .append("c.propertyname1,")
		          .append("( select d.propertyunit from SYSPK_PROPERTYCODE d where d.enterorg in ('000000000A', c.enterorg) and d.propertyno=c.propertyno ) as propertyunit,")
		          .append("c.bookamount,")
		          .append("c.actualamount,")
		          .append("c.keepunitname,")
		          .append("c.keepername,")
		          .append("c.notes,")
		          .append("c.oldserialno,")
		          .append("c.serialno")
		   .append(" from UNTPD_CHECKMOVABLE c ")
		   .append(" where c.enterorg = '"+getQ_enterOrg()+"' and c.differencekind = '"+getQ_differencekind()+"' and c.closingdate <= '" + Datetime.changeTaiwanYYMMDD(getClosingDate(), "2") + "' ");
		  
		  if("1".equals(getQ_status())){
			  stb.append(" and c.checkresult is not null ");
		  }else if("2".equals(getQ_status())){
			  stb.append(" and c.checkresult is null ");
		  }
		  
		  if(!"".equals(getQ_keepUnit())){
			  stb.append("and c.keepunit = " + Common.sqlChar(getQ_keepUnit()));
		  }
		  if (!"".equals(getQ_propertyNoS())){
			  stb.append("and c.propertyno >= " + Common.sqlChar(getQ_propertyNoS()));
		  }
		  if (!"".equals(getQ_propertyNoE())){
			  stb.append("and c.propertyno <= " + Common.sqlChar(getQ_propertyNoE()));
		  }
		   
		   stb.append(" order by c.differencekind, c.propertyno, c.serialno ");
		return stb;
	}
	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = new Database();
	    StringBuilder stb = new StringBuilder();
	    Vector rowData = new Vector();
	    try{
	    	String[] columns = new String[] {
						    			"head01","head02","head03","head04","head05",
						    			"detail01","detail02","detail03","detail04","detail05",
						    			"detail06","detail07","detail08","detail09"
								    	};
	    	if(!"".equals(getQ_enterOrg())){
				//=================================================================
				
				stb = doGetSQLforUNTPD_CHECKNONEXP(stb);    	
				
				//=================================================================			
				log._execLogDebug(stb.toString());
				//=================================================================
				
				UNTCH_Tools ut = new UNTCH_Tools();
				
				ResultSet rs = null;	    
				int i=0;
				System.out.println(stb.toString());
				
				//使用者操作記錄
				Common.insertRecordSql(stb.toString(), this.getOrganID(), this.getUserID(), "UNTPD007R", this.getObjPath().replaceAll("&gt;", ">"));
				
				rs = db.querySQL(stb.toString());
			    while(rs.next()){
			    	
			    	Object[] data = new Object[columns.length];
			    	
			    	//===========================================================
			    	//						Head
			    	//===========================================================
			    	//全銜
			    	data[0] = ut._queryData("organaname")._from("SYSCA_ORGAN a ")._with(" and organid = '" + this.getQ_enterOrg() + "'")._toString();
	
			    	//印表日期
			    	String datetime = Datetime.getYYYMMDD();
			    	data[1] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
			    	
			    	//印表人
			    	data[2] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
			    	
			    	//物品區分別
			    	data[3] = checkGet(rs.getString("differencekindname"));
			    	
				    //資料截止日期
			    	this.year=this.getClosingDate().substring(0,3);
			    	this.month=this.getClosingDate().substring(3, 5);
			    	this.day=this.getClosingDate().substring(5, 7);
			    	data[4] = this.year+" 年 "+this.month+" 月 "+this.day+" 日 ";
			    	
			    	//===========================================================
			    	//						Body
			    	//===========================================================
			    	//物品編號
			    	data[5] = Common.get(rs.getString("propertyno")) + "-" + Common.get(rs.getString("serialno"));
			 //   	data[5] = ReportUtil.getStringWithOldSerialNo(this.getQ_enterOrg(), Common.get(data[5]), "原財產分號", Common.get(rs.getString("oldserialno")));
				    	
			    	//物品名稱
			    	data[6] =checkGet(rs.getString("propertyname"));
			    	
			    	//物品別名
			    	data[7] =checkGet(rs.getString("propertyname1"));
				    	
			    	//單位
			    	data[8] =checkGet(rs.getString("propertyunit"));
				    	
			    	//帳面數量
			    	data[9] =Double.parseDouble(checkGet(rs.getString("bookamount")));
				    	
			    	//實際數量
			    	data[10] =Double.parseDouble(checkGet(rs.getString("actualamount")));
			    	
			    	//保管單位
			    	data[11] =checkGet(rs.getString("keepunitname"));
				    	
			    	//保管人
			    	data[12] =checkGet(rs.getString("keepername"));
				    	
			    	//備註
			    	data[13] =checkGet(rs.getString("notes"));
			    		    	
			    	rowData.addElement(data);
			    	i++;
			    }
			    rs.close();
	    	}
			  //=================================================================
		    Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model = new javax.swing.table.DefaultTableModel();
	        model.setDataVector(data, columns);
	    	
	    }catch(Exception x){
	    	log._execLogError(x.getMessage());
	    	x.printStackTrace();
	    }finally{
	    	if(db != null){
	    		db.closeAll();
	    	}
	    }
	    
	    return model;
	}
}
