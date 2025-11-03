/*
*<br>程式目的：採購財務物價調查表  
*<br>程式代號：untgr031r
*<br>撰寫日期：103/08/26
*<br>程式作者：Jerry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.gr;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import TDlib_Simple.tools.src.LogTools;
import TDlib_Simple.tools.src.MathTools;
import TDlib_Simple.tools.src.StringTools;
import unt.ch.UNTCH_Tools;
import util.*;
import util.report.ReportUtil;

public class UNTGR031R extends SuperBean{
		
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
	private String q_differenceKind;
	private String q_verify;
	private String q_reportType;						// 報表類型
	
	private String year;
	private String month;
	private String day_start;
	private String day_end;
	private LogTools log = new LogTools();
	
	public String getQ_reportType() {	return q_reportType;}
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkSet(q_reportType); }
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
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String qDifferenceKind) {q_differenceKind = checkSet(qDifferenceKind);}
	public String getQ_verify() {return checkGet(q_verify);}
	public void setQ_verify(String qVerify) {q_verify = checkSet(qVerify);}



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

	
	private void processDate(){
		this.year=getQ_enterDate().substring(0,3);
		int year=Integer.parseInt(getQ_enterDate().substring(0,3));
		this.month=getQ_enterDate().substring(3,5);
		this.day_start="01";
		this.day_end="";
		
		if (year > 1492)   
			year = year - 1911; 
        else
        	year = year + 1911; 
		
		if("01".equals(month)||"03".equals(month)||"05".equals(month)||"07".equals(month)||
				"08".equals(month)||"10".equals(month)||"12".equals(month)){
			this.day_end="31";
		}else if("04".equals(month)||"06".equals(month)||"09".equals(month)||"11".equals(month)){
			this.day_end="30";
		}else if("02".equals(month)){
			//判斷是否為閏年
			if (year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0) { 
				this.day_end="29";//閏年
			} else {	
				this.day_end="28";// 平年
			}
		}
	}
	
	//=================================================================
	//	各Table的查詢SQL組成
	//=================================================================
		
		private StringBuilder doGetSQLforUNTLA_Land(StringBuilder stb){
			stb.append(" select ")
			          .append("l.enterorg,")
			          .append("l.ownership,")
			          .append("(select d.codename from SYSCA_CODE d where d.codekindid='DFK' and d.codeid=l.differencekind ) differencekind,")
			          .append("l.propertyno,")
			          .append("l.serialno,")
			          .append("(select c.codename from SYSCA_CODE c where l.differencekind =c.codekindid and c.codekindid='DFK') as chinesecodename,")
			          .append("(select p.propertyname from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',l.enterorg) and p.propertyno = l.propertyno) as propertyname,")
			          .append("null as specification,")
			          .append("null as nameplate,")
			          .append("(select p.propertyunit from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',l.enterorg) and p.propertyno = l.propertyno) as propertyunit,")
			          .append("l.originalunit as originalunit")
			   .append(" from UNTLA_LAND l ")
			   .append(" where ")
			   .append("l.enterorg="+Common.sqlChar(getQ_enterOrg())).append(" and ")
			   .append("l.ownership="+Common.sqlChar(getQ_ownership())).append(" and ")
			   .append("l.verify="+Common.sqlChar(getQ_verify())).append(" and ")
			   .append("l.differencekind="+Common.sqlChar(getQ_differenceKind())).append(" and ");
				if (!"".equals(getQ_valuable())){
					stb.append(" l.valuable = ").append(Common.sqlChar(getQ_valuable())).append(" and ");
				}			   
				stb.append("l.enterdate like "+Common.sqlChar(new Datetime().changeTaiwanYYMM(getQ_enterDate(),"2")+"%"));
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTBU_Building(StringBuilder stb){
			stb.append(" select ")
			          .append("b.enterorg,")
			          .append("b.ownership,")
			          .append("(select d.codename from SYSCA_CODE d where d.codekindid='DFK' and d.codeid=b.differencekind ) differencekind,")
			          .append("b.propertyno,")
			          .append("b.serialno,")
			          .append("(select c.codename from SYSCA_CODE c where b.differencekind =c.codekindid and c.codekindid='DFK') as chinesecodename,")
			          .append("(select p.propertyname from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',b.enterorg) and p.propertyno = b.propertyno) as propertyname,")
			          .append("null as specification,")
			          .append("null as nameplate,")
			          .append("(select p.propertyunit from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',b.enterorg) and p.propertyno = b.propertyno) as propertyunit,")
			          .append("b.originalbv as originalunit")
			   .append(" from UNTBU_BUILDING b ")
		       .append(" where ")
			   .append("b.enterorg="+Common.sqlChar(getQ_enterOrg())).append(" and ")
			   .append("b.ownership="+Common.sqlChar(getQ_ownership())).append(" and ")
			   .append("b.verify="+Common.sqlChar(getQ_verify())).append(" and ")
			   .append("b.differencekind="+Common.sqlChar(getQ_differenceKind())).append(" and ");
				if (!"".equals(getQ_valuable())){
					stb.append(" b.valuable = ").append(Common.sqlChar(getQ_valuable())).append(" and ");
				}			   
				stb.append("b.enterdate like "+Common.sqlChar(new Datetime().changeTaiwanYYMM(getQ_enterDate(),"2")+"%"));

			return stb;
		}
		
		private StringBuilder doGetSQLforUNTRF_Attachment(StringBuilder stb){
			stb.append(" select ")
			          .append("a.enterorg,")
			          .append("a.ownership,")
			          .append("(select d.codename from SYSCA_CODE d where d.codekindid='DFK' and d.codeid=a.differencekind ) differencekind,")
			          .append("a.propertyno,")
			          .append("a.serialno,")
			          .append("(select c.codename from SYSCA_CODE c where a.differencekind =c.codekindid and c.codekindid='DFK') as chinesecodename,")
			          .append("(select p.propertyname from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',a.enterorg) and p.propertyno = a.propertyno) as propertyname,")
			          .append(" null as specification,")
			          .append(" null as nameplate,")
			          .append("(select p.propertyunit from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',a.enterorg) and p.propertyno = a.propertyno) as propertyunit,")
			          .append("a.originalbv as originalunit")
			   .append(" from UNTRF_ATTACHMENT a ")
		       .append(" where ")
			   .append("a.enterorg="+Common.sqlChar(getQ_enterOrg())).append(" and ")
			   .append("a.ownership="+Common.sqlChar(getQ_ownership())).append(" and ")
			   .append("a.verify="+Common.sqlChar(getQ_verify())).append(" and ")
			   .append("a.differencekind="+Common.sqlChar(getQ_differenceKind())).append(" and ");
				if (!"".equals(getQ_valuable())){
					stb.append(" a.valuable = ").append(Common.sqlChar(getQ_valuable())).append(" and ");
				}			   
			stb.append("a.enterdate like "+Common.sqlChar(new Datetime().changeTaiwanYYMM(getQ_enterDate(),"2")+"%"));
			return stb;
		}
		
		private StringBuilder doGetSQLforUNTMP_Movable(StringBuilder stb){
			stb.append(" select ")
			          .append("m.enterorg,")
			          .append("m.ownership,")
			          .append("(select d.codename from SYSCA_CODE d where d.codekindid='DFK' and d.codeid=m.differencekind ) differencekind,")
			          .append("m.propertyno,")
			          .append("m.lotno as serialno,")
			          .append("(select c.codename from SYSCA_CODE c where m.differencekind =c.codekindid and c.codekindid='DFK') as chinesecodename,")
			          .append("(select p.propertyname from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',m.enterorg) and p.propertyno =m.propertyno) as propertyname,")
			          .append("m.specification as specification,")
			          .append("m.nameplate as nameplate,")
			          .append("(select p.propertyunit from SYSPK_PROPERTYCODE p where p.enterorg in ('000000000A',m.enterorg) and p.propertyno = m.propertyno) as propertyunit,")
			          .append(" m.originalunit as originalunit")
			   .append(" from UNTMP_MOVABLE m ")
			   .append(" where ")
			   .append("m.enterorg="+Common.sqlChar(getQ_enterOrg())).append(" and ")
			   .append("m.ownership="+Common.sqlChar(getQ_ownership())).append(" and ")
			   .append("m.verify="+Common.sqlChar(getQ_verify())).append(" and ")
			   .append("m.differencekind="+Common.sqlChar(getQ_differenceKind())).append(" and ") ;
				if (!"".equals(getQ_valuable())){
					stb.append(" m.valuable = ").append(Common.sqlChar(getQ_valuable())).append(" and ");
				}			   
			stb.append("m.enterdate like "+Common.sqlChar(new Datetime().changeTaiwanYYMM(getQ_enterDate(),"2")+"%"));
			return stb;
		}
		


	
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = new Database();
	    StringBuilder stb = new StringBuilder();
	    try{
	    	if(!"".equals(getQ_enterOrg()) ||  !"".equals(getOrganID())){
				model = new javax.swing.table.DefaultTableModel();
				Vector rowData = new Vector();
	    	String[] columns = new String[] {
						    			"head01","head02","head03","head04","head05",
						    			"detail01","detail02","detail03","detail04","detail05",
						    			"detail06","detail07","detail08","detail09","detail10",
						    			"detail11",
								    	};
		
			//=================================================================
			
			stb = doGetSQLforUNTLA_Land(stb);    	
			stb.append(" union all");
			stb = doGetSQLforUNTBU_Building(stb);
			stb.append(" union all");
			stb = doGetSQLforUNTRF_Attachment(stb);
			stb.append(" union all");
			stb = doGetSQLforUNTMP_Movable(stb);
			
			//stb.append(" order by propertyNo");
			
			
			//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================
			
			UNTCH_Tools ut = new UNTCH_Tools();
			MathTools mt = new MathTools();
			StringTools st = new StringTools();
//			String sum_bookAmount = "0";	//數量 總計
//			String sum_measure = "0";		//計量數  總計
//			String sum_bookUnit = "0";		//單價 總計
//			String sum_bookValue = "0";		//總價  總計
			
			//String queryCondition = doGetIfCondition();
			
//			String[] kindName = null;
//			if("1".equals(q_kind)){			kindName = new String[]{"第一聯"};
//			}else if("2".equals(q_kind)){	kindName = new String[]{"第二聯"};
//			}else if("3".equals(q_kind)){	kindName = new String[]{"第三聯"};
//			}else if("4".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第三聯"};
//			}else if("5".equals(q_kind)){	kindName = new String[]{"第一聯","第二聯","第二聯","第三聯"};
//    		}
			
			ResultSet rs = null;    
			int i=0;
				rs = db.querySQL(stb.toString());
			    while(rs.next()){
			    	
			    	Object[] data = new Object[columns.length];
			    	
			    	//===========================================================
			    	//						Head
			    	//===========================================================
			    	//全銜
			    	data[0] = ReportUtil.getTitleByEnterOrgCode(this.getQ_enterOrg(), this.getQ_differenceKind());

			    	//印表日期
			    	String datetime = Datetime.getYYYMMDD();
			    	data[1] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
			    	
			    	//印表人
			    	data[2] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
			    	//財產區分別
			    	data[3] = Common.get(rs.getString("differencekind"));
			    	
			    	//帳務年月
			    	this.year=getQ_enterDate().substring(0,3);
			    	this.month=getQ_enterDate().substring(3,5);
			    	int year=Integer.parseInt(this.year);
			    	int month=Integer.parseInt(this.month);
					this.day_start="01";
			    	data[4] = "中華民國"+this.year+" 年 "+this.month+" 月 "+this.day_start+" 日起至 "+this.year+" 年 "+this.month+" 月 "+Common.getLastDayOfMonth(year, month)+" 月 ";
			    	String[] propertyno = TCGHCommon.getFormatedPropertyNo(Common.get(rs.getString("propertyno")));
			    	//分類編號-類
			    	data[5] = propertyno[0];
			    	//分類編號-項
			    	data[6] = propertyno[1];
			    	//分類編號-目
			    	data[7] = propertyno[2];
			    	//分類編號-節
			    	data[8] = propertyno[3];
			    	//分類編號-號
			    	data[9] = propertyno[4];
			    	
			    	//財產名稱
			    	data[10] =checkGet(rs.getString("propertyname"));
			    	//型式
			    	data[11] =checkGet(rs.getString("specification"));
			    	//廠牌
			    	data[12] =checkGet(rs.getString("nameplate"));
			    	//單位
			    	data[13] =checkGet(rs.getString("propertyunit"));
			    	//金額
			    	data[14] =checkGet(rs.getString("originalunit"));
			    	
					double originalunit = 0;
					if ("".equals(data[14].toString())) {
						originalunit = 0;
					} else {
						originalunit = Double.parseDouble(data[14].toString());
					}
					data[14] = originalunit;
					
			    	//備註
			    	data[15] ="";
			    	
			    	rowData.addElement(data);
			    	i++;
			    }
			    
			    if(rowData.size() ==0 ){
			    	Object[] data = new Object[columns.length];
			    	//全銜
			    	data[0] = ReportUtil.getTitleByEnterOrgCode(this.getQ_enterOrg(), this.getQ_differenceKind());

			    	//印表日期
			    	String datetime = Datetime.getYYYMMDD();
			    	data[1] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
			    	
			    	//印表人
			    	data[2] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
			    	//財產區分別
			    	data[3] = new UNTCH_Tools()._getDifferenceKindName(getQ_differenceKind());
			    	
			    	//帳務年月
			    	this.year=getQ_enterDate().substring(0,3);
			    	this.month=getQ_enterDate().substring(3,5);
			    	int year=Integer.parseInt(this.year);
			    	int month=Integer.parseInt(this.month);
					this.day_start="01";
			    	data[4] = "中華民國"+this.year+" 年 "+this.month+" 月 "+this.day_start+" 日起至 "+this.year+" 年 "+this.month+" 月 "+Common.getLastDayOfMonth(year, month)+" 月 ";
			    	
			    	data[5] = "";
			    	data[6] = "";
			    	data[7] = "";
			    	data[8] = "";
			    	data[9] = "";
			    	
			    	//財產名稱
			    	data[10] ="本";
			    	//型式
			    	data[11] ="月";
			    	//廠牌
			    	data[12] ="無";
			    	//單位
			    	data[13] ="異";
			    	//金額
			    	double originalunit = 0;
			    	data[14] = originalunit;
			    	data[15] = "動";
			    	rowData.addElement(data);
			    }
			
		  //=================================================================
			    Object[][] data = new Object[0][0];
		        data = (Object[][])rowData.toArray(data);
		        model = new javax.swing.table.DefaultTableModel();
		        model.setDataVector(data, columns);
	    	}
	    }catch(Exception x){
	    	log._execLogError(x.getMessage());
	    	x.printStackTrace();
	    }finally{
	        db.closeAll();
	    }
	    
	    return model;
	}
}
