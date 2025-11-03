/*
*<br>程式目的：財產折舊表(代管資產) 
*<br>程式代號：UNTDP023R
*<br>撰寫日期：103/10/07
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import TDlib_Simple.tools.src.LogTools;

import util.*;

public class UNTDP023R extends SuperBean{
		
	private String q_enterOrg;
	private String q_enterOrgName;
	private String q_ownership;
	private String q_reportType;
	private String q_deprPark;
	private String q_deprUnit;
	private String q_valuable;
	private String q_deprYM;
	private String q_verify;
	private String q_dataState;
	private String q_differenceKind;

	private LogTools log = new LogTools();
	
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_valuable(){ return checkGet(q_valuable); }
	public void setQ_valuable(String s){ q_valuable=checkSet(s); }
	public String getQ_reportType() {return checkGet(q_reportType);}
	public void setQ_reportType(String qReportType) {q_reportType = checkSet(qReportType);}
	public String getQ_deprPark() {return checkGet(q_deprPark);}
	public void setQ_deprPark(String qDeprPark) {q_deprPark = checkSet(qDeprPark);}
	public String getQ_deprUnit() {return checkGet(q_deprUnit);}
	public void setQ_deprUnit(String qDeprUnit) {q_deprUnit = checkSet(qDeprUnit);}
	public String getQ_deprYM() {return checkGet(q_deprYM);}
	public void setQ_deprYM(String qDeprYM) {q_deprYM = checkSet(qDeprYM);}
	public String getQ_verify() {return checkGet(q_verify);}
	public void setQ_verify(String qVerify) {q_verify = checkSet(qVerify);}
	public String getQ_dataState() {return checkGet(q_dataState);}
	public void setQ_dataState(String qDataState) {q_dataState = checkSet(qDataState);}
	public String getQ_differenceKind() {return checkGet(q_differenceKind);}
	public void setQ_differenceKind(String qDifferenceKind) {q_differenceKind = checkSet(qDifferenceKind);}



	private String isOrganManager;
	private String isAdminManager;
	private String organID;    
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

	
	
	//=================================================================
	//	各Table的查詢SQL組成
	//=================================================================
	public DefaultTableModel getResultModel() throws Exception{
	    DefaultTableModel model = null;
	    Database db = null;
	    try{
	    	String[] columns = new String[] {
								    			"organaname", "head01","head02","head03","head04","head05",
								    			"detail01","detail02","detail03","detail04","detail05","detail06","detail07","detail08","detail09","detail10"
								    	    };
			db = new Database();
		    Vector rowData = new Vector();	
		    
		    String dbThisMonth = "";
		    String dbLastMonth = "";
		    String head01 = "";
		    String head02 = "";
		    String head03 = "";
		    String head04 = "";
		    String head05 = "";
		    if(!"".equals(getQ_deprYM())){
		    	dbThisMonth = getQ_deprYM();
		    	dbLastMonth = new Datetime().getMonthDay(getQ_deprYM(),-1,"month");
		    	head01 = getQ_deprYM().substring(0, 3) + "年" + getQ_deprYM().substring(3,5) + "月  財產折舊表(代管資產)";
		    }
		    
			String datetime = Datetime.getYYYMMDD();
			head05 = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5) + "日";
		    

		
			//=================================================================
	    	for(int i=1; i<7; i++){
	    		
	    		String queryThisMonthTableName= "";
	    		String queryLastMonthTableName= "";
	    		
	    		if(i == 1){
	    			queryThisMonthTableName = "UNTLA_LAND"+"_"+dbThisMonth;
	    			queryLastMonthTableName = "UNTLA_LAND"+"_"+dbLastMonth;
	    		}else if(i == 2){
	    			queryThisMonthTableName = "UNTRF_ATTACHMENT"+"_"+dbThisMonth;
	    			queryLastMonthTableName = "UNTRF_ATTACHMENT"+"_"+dbLastMonth;
	    		}else if(i == 3){
	    			queryThisMonthTableName = "UNTBU_BUILDING"+"_"+dbThisMonth;
	    			queryLastMonthTableName = "UNTBU_BUILDING"+"_"+dbLastMonth;
	    		}else if(i == 4 || i == 5 || i == 6){
	    			queryThisMonthTableName = "UNTMP_MOVABLEDETAIL"+"_"+dbThisMonth;	   
	    			queryLastMonthTableName = "UNTMP_MOVABLEDETAIL"+"_"+dbLastMonth;
	    		}
	    		
	    		
	    	StringBuilder stb = new StringBuilder();	
	    	 stb.append(" select organaname from SYSCA_ORGAN where organid = " + Common.sqlChar(Common.get(getQ_enterOrg())) + " ")
	    	.append(" union all ")
	    	.append(" select distinct " + Common.sqlChar(head01) + " from SYSCA_ORGAN ");
	    	if("1".equals(getQ_reportType())){
	    		stb.append(" union all ");
		    	stb.append(" select distinct '***' from SYSCA_ORGAN ");
		    	stb.append(" union all ");
		    	stb.append(" select distinct '***' from SYSCA_ORGAN ");
		        stb.append(" union all ");
		    	stb.append(" select distinct " + Common.sqlChar("報表編號：untdp023r-1")+ " from SYSCA_ORGAN ");
	    	}else if("2".equals(getQ_reportType())){
	    		stb.append(" union all ");
		    	stb.append(" select distinct '***' from SYSCA_ORGAN ");
		    	stb.append(" union all ");
		    	stb.append(" select '園區別：'+deprparkname from SYSCA_DEPRPARK z where z.enterorg = " + Common.sqlChar(Common.get(getQ_enterOrg())) + " and z.deprparkno = "+ Common.sqlChar(Common.get(getQ_deprPark())));
		        stb.append(" union all ");
		    	stb.append(" select distinct " + Common.sqlChar("報表編號：untdp023r-2")+ " from SYSCA_ORGAN ");
	    	}else if("3".equals(getQ_reportType())){
	    		stb.append(" union all ");
		    	stb.append(" select distinct '***' from SYSCA_ORGAN ");
		    	stb.append(" union all ");
		    	stb.append(" select '部門別：'+z.deprunitname from SYSCA_DEPRUNIT z where z.enterorg = " + Common.sqlChar(Common.get(getQ_enterOrg())) + " and z.deprunitno = "+ Common.sqlChar(Common.get(getQ_deprUnit())));
		        stb.append(" union all ");
		    	stb.append(" select distinct " + Common.sqlChar("報表編號：untdp023r-3")+ " from SYSCA_ORGAN ");
	    	}else if("4".equals(getQ_reportType())){
	    		stb.append(" union all ");
		    	stb.append(" select '園區別：'+deprparkname from SYSCA_DEPRPARK z where z.enterorg = " + Common.sqlChar(Common.get(getQ_enterOrg())) + " and z.deprparkno = "+ Common.sqlChar(Common.get(getQ_deprPark())));
		    	stb.append(" union all ");
		    	stb.append(" select '部門別：'+z.deprunitname from SYSCA_DEPRUNIT z where z.enterorg = " + Common.sqlChar(Common.get(getQ_enterOrg())) + " and z.deprunitno = "+ Common.sqlChar(Common.get(getQ_deprUnit())));
		        stb.append(" union all ");
		    	stb.append(" select distinct " + Common.sqlChar("報表編號：untdp023r-4")+ " from SYSCA_ORGAN ");
	    	} else {
	    		stb.append(" union all ");
		    	stb.append(" select distinct '***' from SYSCA_ORGAN ");
		    	stb.append(" union all ");
		    	stb.append(" select distinct '***' from SYSCA_ORGAN ");
		        stb.append(" union all ");
		    	stb.append(" select distinct '***' from SYSCA_ORGAN ");
	    	}
	    	stb.append(" union all ")
	    	.append(" select distinct " + Common.sqlChar("印表日期："+head05)+ " from SYSCA_ORGAN ")
	    	.append(" union all ")
	    	.append(" select codename from SYSCA_CODE where codekindid='PTT' and codeid=" + Common.sqlChar(String.valueOf(i)) + " ")
	    	.append(" union all ")
	    	.append(" select case when sum(a.bookvalue) is null then  '0' else convert(nvarchar(10), sum(a.bookvalue)) end from " + queryLastMonthTableName + " a where a.differencekind = '111' and a.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") + " and a.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and a.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" ")  // A
	    	.append(" union all ")
	    	.append(" select convert(nvarchar(10),case when sum(b.originalbv) is not null then  sum(b.originalbv) else 0 end + " +
	    			" case when sum(k.addbookvalue) is not null then sum(k.addbookvalue) else 0 end  + " +
	    			" 0) ")
			.append(" from " + queryThisMonthTableName + " b ")
			.append(" left join UNTLA_ADJUSTDETAIL k on k.enterorg = b.enterorg and k.ownership = b.ownership and k.differencekind = b.differencekind and k.propertyno = b.propertyno ")
			.append(" left join UNTLA_REDUCEDETAIL l on l.enterorg = b.enterorg and l.ownership = b.ownership and l.differencekind = b.differencekind and l.propertyno = b.propertyno ")
			.append(" where b.differencekind = '111' and b.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") +  " and b.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and b.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) + " and k.adjustdate=" + Common.sqlChar(Common.get(dbThisMonth)) + " and l.reducedate="+ Common.sqlChar(Common.get(dbThisMonth)) +" ") // B
	    	.append(" union all ")
	    	.append(" select convert(nvarchar(10),0 + " +
	    			" case when sum(k.reducebookvalue) is not null then  sum(k.reducebookvalue) else 0 end + " +
	    			" case when sum(l.bookvalue) is not null then sum(l.bookvalue) else 0 end) " )
	    	.append(" from " + queryThisMonthTableName + " c " )
	    	.append(" left join UNTLA_ADJUSTDETAIL k on k.enterorg = c.enterorg and k.ownership = c.ownership and k.differencekind = c.differencekind and k.propertyno = c.propertyno ")
	    	.append(" left join UNTLA_REDUCEDETAIL l on l.enterorg = c.enterorg and l.ownership = c.ownership and l.differencekind = c.differencekind and l.propertyno = c.propertyno ")
	    	.append(" where c.differencekind = '111'  and c.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") +  " and c.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and c.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) + " and k.adjustdate=" + Common.sqlChar(Common.get(dbThisMonth)) + " and l.reducedate="+ Common.sqlChar(Common.get(dbThisMonth)) +" ")  // C
	    	.append(" union all ")
//	    	.append(" select case when sum(d.bookvalue) is null then  '0' else convert(nvarchar(10), sum(d.bookvalue)) end from " + queryThisMonthTableName + " d where d.differencekind = '111' and d.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") +  " and d.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and d.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" ")  // D=A+B-C
	    	.append(" select distinct 'D' from " + queryThisMonthTableName + " d ")  // D=A+B-C
	    	.append(" union all ");
	    	if(i == 1){
	    		stb.append(" select distinct case when count(*) = 0 then  '0' else '0' end from " + queryLastMonthTableName + " e ");  // E
	    	}else{
	    		stb.append(" select case when sum(e.accumdepr) is null then  '0' else convert(nvarchar(10), sum(e.accumdepr)) end from " + queryLastMonthTableName + " e where e.differencekind = '111'  and e.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") +  " and e.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and e.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" ");  // E
	    	}
	    	stb.append(" union all ");
	    	if(i == 1){
	    	    stb.append(" select distinct case when count(*) = 0 then  '0' else '0' end from UNTDP_MONTHDEPR f  ");  // F
	    	}else{
	    		stb.append(" select convert(nvarchar(10),sum ( case when f.deprunitcb='Y' then  convert(nvarchar(10),(f.scaledaddaccumdepr  - f.scaledreduceaccumdepr)) when f.deprunitcb='N' then  convert(nvarchar(10),(f.addaccumdepr - f.reduceaccumdepr)) else 0 end )) from UNTDP_MONTHDEPR f where f.differencekind = '111' and f.propertytype=" + Common.sqlChar(String.valueOf(i)) + " and f.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and f.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" and f.deprym="+ Common.sqlChar(Common.get(dbThisMonth))+" ");  // F
	    	}
	    	stb.append(" union all ");
	    	if(i == 1){
	    	    stb.append(" select distinct case when count(*) = 0 then  '0' else '0' end from UNTDP_MONTHDEPR g  "); // G
	    	}else{
	    		stb.append(" select case when sum ( case when g.deprunitcb='Y' then  g.scaledmonthdepr when g.deprunitcb='N' then  g.monthdepr else 0 end ) is null then  '0' else convert(nvarchar(10), sum ( case when g.deprunitcb='Y' then  g.scaledmonthdepr when g.deprunitcb='N' then  g.monthdepr else 0 end )) end  from UNTDP_MONTHDEPR g where g.differencekind = '111' and g.propertytype=" + Common.sqlChar(String.valueOf(i)) + " and g.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and g.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" and g.deprym="+ Common.sqlChar(Common.get(dbThisMonth))+" "); // G
	    	}
	    	stb.append(" union all ");
//	    	if(i == 1){
//	    		stb.append(" select distinct '0' from " + queryThisMonthTableName + " h ");  // H=E+F+G
//	    	}else{
//	    		stb.append(" select case when sum(h.accumdepr) is null then  '0' else convert(nvarchar(10), sum(h.accumdepr)) end from " + queryThisMonthTableName + " h where h.differencekind = '111'  and h.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") +  " and h.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and h.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" ");  // H=E+F+G
	    		stb.append(" select distinct 'H' from " + queryThisMonthTableName + " h ");
//	    	}
	    	stb.append(" union all ");
//	    	if(i == 1){
//	    		stb.append(" select case when sum(i.bookvalue) is null then  '0' else convert(nvarchar(10), sum(i.bookvalue)) end from " + queryThisMonthTableName + " i where i.differencekind = '111'  and i.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") +  " and i.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and i.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" ");  // I=D-H
//	    	}else{
//	    		stb.append(" select case when sum(i.netvalue) is null then  '0' else convert(nvarchar(10), sum(i.netvalue)) end from " + queryThisMonthTableName + " i where i.differencekind = '111'  and i.propertyno like " + Common.sqlChar(String.valueOf(i)+"%") +  " and i.enterorg=" + Common.sqlChar(Common.get(getQ_enterOrg())) + " and i.ownership="+ Common.sqlChar(Common.get(getQ_ownership())) +" ");  // I=D-H	    	
	    		stb.append(" select distinct 'I' from " + queryThisMonthTableName + " i ");
//	    	}			
	    	
	    	//=================================================================			
			log._execLogDebug(stb.toString());
			//=================================================================	 
			System.out.print(stb.toString());
		    ResultSet rs = db.querySQL_NoChange(stb.toString());
		    Object[] data = new Object[columns.length];
		    int j =0 ;
				while(rs.next()){
					if("***".equals(rs.getString(1))){
						data[j] = "";
					}else if(rs.getString(1) == null){	
						data[j] = "0";
					}else if("D".equals(rs.getString(1)) ){	  //D=A+B-C
						data[j] = String.valueOf(Integer.parseInt(data[7].toString()) + Integer.parseInt(data[8].toString())- Integer.parseInt(data[9].toString()));
					}else if("H".equals(rs.getString(1)) ){	  // H=E+F+G
						data[j] = String.valueOf(Integer.parseInt(data[11].toString()) + Integer.parseInt(data[12].toString()) + Integer.parseInt(data[13].toString()));
					}else if("I".equals(rs.getString(1)) ){	  // I=D-H
						data[j] = String.valueOf(Integer.parseInt(data[10].toString()) - Integer.parseInt(data[14].toString()));
					}else{
						data[j] = rs.getString(1);
					}

			    	j++;
				}
			    	rowData.addElement(data);
			    
	    }
			
		  //=================================================================
		    Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model = new javax.swing.table.DefaultTableModel();
	        model.setDataVector(data, columns);
	    }catch(Exception x){
	    	log._execLogError(x.getMessage());
	    }finally{
	        db.closeAll();
	    }
	    
	    return model;
	}
	
	public String checkTableExist(String YYYMM){
		String[] tableName = new String[] {"UNTLA_LAND", "UNTRF_ATTACHMENT", "UNTBU_BUILDING", "UNTMP_MOVABLEDETAIL"}; 
		String[] tableChineseName = new String[] {"土地主檔", "土地改良物主檔", "建物主檔", "動產主檔－批號明細"}; 
		
		String[] queryMonth = new String[2];
		queryMonth[0] = YYYMM;
		queryMonth[1] = new Datetime().getMonthDay(YYYMM,-1,"month");
		
		String message = "";
		Database db = new Database();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = db.getConnection();
		for(int i=0; i< tableName.length; i++){
			for(int j=0; j< queryMonth.length; j++){
				try{
					ps = conn.prepareStatement(" select top 1 * from " + tableName[i]+"_"+queryMonth[j] );		
					rs = ps.executeQuery();				
				} catch (Exception ex) {
					message +=  " "+tableChineseName[i] + "之" +queryMonth[j]+"資料,";
				}
			}
		}
		if(!"".equals(message)){
			message = "目前尚未備存" + message.substring(0, message.length()-1) + "!!";
		}
		return message;
	} 
	
}
