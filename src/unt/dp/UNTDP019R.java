
/*
*<br>程式目的：財產折舊月報明細表(代管資產)(中科管局格式)
*<br>程式代號：untdp019r
*<br>撰寫日期：103/09/02
*<br>程式作者：Anthony.Wang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.dp;

import java.sql.ResultSet;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTDP019R extends SuperBean{
	String q_enterOrg;			//入帳機關
	String q_deprYM; 			//折舊年月
	String q_differenceKind; 	//財產區分別
	String q_print;				//是否列印說明資料
	String q_page; 				//換頁方式
	String q_deprPark; 			//園區別
	String q_deprUnit; 			//部門別
	String q_deprUnit1; 		//部門別單位
	String q_propertyType;		//財產大類
	private String q_reportType;						// 報表類型
	
	public String getQ_reportType() { return checkGet(q_reportType); }
	public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_deprYM(){ return checkGet(q_deprYM); }
	public void setQ_deprYM(String s){ q_deprYM=checkSet(s); }
	public String getQ_differenceKind(){ return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String s){ q_differenceKind=checkSet(s); }
	public String getQ_print(){ return checkGet(q_print); }
	public void setQ_print(String s){ q_print=checkSet(s); }
	public String getQ_page(){ return checkGet(q_page); }
	public void setQ_page(String s){ q_page=checkSet(s); }
	public String getQ_deprPark(){ return checkGet(q_deprPark); }
	public void setQ_deprPark(String s){ q_deprPark=checkSet(s); }
	public String getQ_deprUnit(){ return checkGet(q_deprUnit); }
	public void setQ_deprUnit(String s){ q_deprUnit=checkSet(s); }
	public String getQ_deprUnit1(){ return checkGet(q_deprUnit1); }
	public void setQ_deprUnit1(String s){ q_deprUnit1=checkSet(s); }
	public String getQ_propertyType(){ return checkGet(q_propertyType); }
	public void setQ_propertyType(String s){ q_propertyType=checkSet(s); }
	
	String isOrganManager;
    String isAdminManager;
    String organID;  
    String userName;  
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
    public String getUserName() { return checkGet(userName); }
    public void setUserName(String s) { userName = checkSet(s); } 

	
	public DefaultTableModel getResultModel() throws Exception{
		
		Map<String,String> differencekindMap = TCGHCommon.getSysca_Code("DFK");
		Map<String,String> enterorgMap = TCGHCommon.getSysca_Organ();
		Map<String,String> deprunitMap = TCGHCommon.getSysca_DeprUnit(this.getQ_enterOrg());
		Map<String,String> deprunit1Map = TCGHCommon.getSysca_DeprUnit1(this.getQ_enterOrg());
		Map<String,String> deprunit_untdp019rnotesMap = TCGHCommon.getSysca_DeprUnit_Untdp019Notes(this.getQ_enterOrg());
		Map<String,String> deprunit1_untdp019rnotesMap = TCGHCommon.getSysca_DeprUnit1__Untdp019Notes(this.getQ_enterOrg());
		Map<String,String> propertynameMap = TCGHCommon.getSyspk_PropertyCode(this.getQ_enterOrg());//財產名稱
		
	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
	    try{
	    	String[] columns ;
	    	columns = new String[]{
	    			"ENTERORGNAME",
	    			"PROPERTYNAME",
	    			"DIFFERENCEKIND",
	    			"DEPRUNIT",
	    			"DEPRDATEB",
	    			"DEPRDATEE",
	    			"PROPERTYNO",
	    			"SERIALNO",
	    			"PROPERTYNAME1",
	    			"SOURCEDATE",
	    			"BUYDATE",
	    			"ESCROWORILIMITMONT",
	    			"ESCROWORIVALUE",
	    			"ESCROWORIACCUMDEPR",
	    			"BOOKVALUE",
	    			"SCRAPVALUE",
	    			"OLDACCUMDEPR",
	    			"MONTHDEPR",
	    			"NEWACCUMDEPR",
	    			"NEWNETVALUE",
	    			"DESCRIPTION"};
	    	if ("2".equals(this.getQ_page())) { //跳頁方式:部門別單位
		    	columns = new String[]{
		    			"ENTERORGNAME",
		    			"PROPERTYNAME",
		    			"DIFFERENCEKIND",
		    			"DEPRUNIT",
		    			"DEPRDATEB",
		    			"DEPRDATEE",
		    			"PROPERTYNO",
		    			"SERIALNO",
		    			"PROPERTYNAME1",
		    			"SOURCEDATE",
		    			"BUYDATE",
		    			"ESCROWORILIMITMONT",
		    			"ESCROWORIVALUE",
		    			"ESCROWORIACCUMDEPR",
		    			"BOOKVALUE",
		    			"SCRAPVALUE",
		    			"OLDACCUMDEPR",
		    			"MONTHDEPR",
		    			"NEWACCUMDEPR",
		    			"NEWNETVALUE",
		    			"DESCRIPTION",
		    			"DEPRUNIT1"};
	    	}

	    	String execSQL=" select " +
	    	" a.enterorg, " +
	    	" a.differencekind, " +
	    	" a.deprunit, " +
	    	" a.deprunit1, " +
	    	" a.propertyno, " +
	    	" a.serialno, " +
	    	" a.propertyname1, " +
	    	" a.sourcedate, " +
	    	" a.buydate , " +
	    	" a.escroworilimitmonth, " +
	    	" a.escroworivalue, " +
	    	" a.escroworiaccumdepr, " +
	    	" a.bookvalue, " +
	    	" a.scrapvalue, " +
	    	" a.oldaccumdepr, " +
	    	" a.monthdepr, " +
	    	" a.newaccumdepr, " +
	    	" a.newnetvalue " +
	    	" from " +
	    	" UNTDP_MONTHDEPR a " +
	    	" where 1=1 ";
	    	if (!"".equals(getQ_enterOrg())) {
	    		execSQL+=" and a.enterorg = " + Common.sqlChar(getQ_enterOrg()) ;
	    	} else {
	    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
	    			if ("Y".equals(getIsOrganManager())) {					
	    				execSQL += " and a.enterorg like '" + getOrganID().substring(0,5) + "%' ";
	    			} else {
	    				execSQL +=" and a.enterorg = " + Common.sqlChar(getOrganID()) ;
	    			}
	    		} 
	    	}
	    	if (!"".equals(Common.get(getQ_deprYM())))	//折舊年月
	    		execSQL += " and a.deprym = " + util.Common.sqlChar(Datetime.changeTaiwan(getQ_deprYM().substring(0, 3),"2") + getQ_deprYM().substring(3, 5));
	    	if (!"".equals(Common.get(getQ_differenceKind())))	//財產區分別
	    		execSQL += " and a.differencekind = " + util.Common.sqlChar(getQ_differenceKind());
	    	if (!"".equals(Common.get(getQ_deprPark())))	//園區別
	    		execSQL += " and a.deprpark = " + util.Common.sqlChar(getQ_deprPark());
	    	if (!"".equals(Common.get(getQ_deprUnit())))	//部門別
	    		execSQL += " and a.deprunit = " + util.Common.sqlChar(getQ_deprUnit());
	    	if (!"".equals(Common.get(getQ_deprUnit1())))	//部門單位
	    		execSQL += " and a.deprunit1 = " + util.Common.sqlChar(getQ_deprUnit1());
	    	if (!"".equals(Common.get(getQ_propertyType())))	//財產大類
	    		execSQL += " and a.propertytype = " + util.Common.sqlChar(getQ_propertyType());
	    	execSQL += " order by " +
	        	" a.enterorg, " +
	        	" a.deprym, " +
	        	" a.differencekind, " +
	        	" a.deprunit, " +
	        	" a.deprunit1, " +
	        	" a.propertytype, " +
	        	" a.propertyno, " +
	        	" a.serialno, " +
	        	" a.serialno1 ";
	    	
	    	System.out.println(execSQL);
	    	ResultSet rs = db.querySQL(execSQL);
	        Vector rowData = new Vector();
	        int i = 0;
	        
	        while(rs.next()){
	        	i++;
	        	Object[] data = new Object[columns.length];
//	        	if ("2".equals(this.getQ_page())) { //跳頁方式:部門別
//	        		data = new Object[22];
//	        	}
	        	data[0] = enterorgMap.get(this.getQ_enterOrg());
	        	data[1] = Common.get(propertynameMap.get(rs.getString("propertyno")));
	            data[2] = differencekindMap.get(rs.getString("differencekind"));
	            data[3] = deprunitMap.get(rs.getString("deprunit"));
	            data[4] = Common.formatYYYMMDD(getQ_deprYM()+ "01",2);
	            int year = Integer.parseInt(getQ_deprYM().substring(0,3));
            	int month = Integer.parseInt(getQ_deprYM().substring(3,5));
            	data[5] = Common.formatYYYMMDD(getQ_deprYM() + Common.getLastDayOfMonth(year,month),2);
            	String propertyno = rs.getString("propertyno").toString();
            	if (propertyno.length() > 7) {
            		data[6] = propertyno.substring(0, 7) + "-" + propertyno.substring(7);
            	} else {
            		data[6] = propertyno;
            	}
	            data[7] = rs.getString("serialno");
	            data[8] = rs.getString("propertyname1");
	            data[9] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("sourcedate"),"1"));
	        	data[10] = Common.MaskDate(Datetime.changeTaiwanYYMMDD(rs.getString("buydate"),"1"));
	        	String escroworilimitmonth = "";
	        	
	        	int a = rs.getInt("escroworilimitmonth");
	        	int b = 12;
	        	int c = a/b;
	        	int d = a % b;
	        	if (c != 0) {
	        		escroworilimitmonth += c + "年";
	        	}
	        	if (d != 0) {
	        		escroworilimitmonth += d + "個月";
	        	}
	        	data[11] = escroworilimitmonth;
	        	data[12] = rs.getDouble("escroworivalue");
	        	data[13] = rs.getDouble("escroworiaccumdepr");
	        	data[14] = rs.getDouble("bookvalue");
	           	data[15] = rs.getDouble("scrapvalue");
	        	data[16] = rs.getDouble("oldaccumdepr");
	        	data[17] = rs.getDouble("monthdepr");
	        	data[18] = rs.getDouble("newaccumdepr");
	        	data[19] = rs.getDouble("newnetvalue");
	        	if ("1".equals(this.getQ_page())) { //跳頁方式:部門別
	        		if ("Y".equals(this.getQ_print())) {
	        			data[20] = "Y";//"說明：" + Common.get(deprunit_untdp019rnotesMap.get(rs.getString("deprunit")));
	        		} else if ("N".equals(this.getQ_print())) {
	        			data[20] = "";
	        		} 
	        	} else if ("2".equals(this.getQ_page())) { //跳頁方式:部門別單位
	        		if ("Y".equals(this.getQ_print())) {
	        			data[20] = "Y";//"說明：" + Common.get(deprunit1_untdp019rnotesMap.get(rs.getString("deprunit1")));
	        		} else if ("N".equals(this.getQ_print())) {
	        			data[20] = "";
	        		} 
	        		data[21] = deprunit1Map.get(rs.getString("deprunit1"));
	        	}
	       
	            rowData.addElement(data);
	        }
	        
	        
//	        for (int j = 0 ; j <= 39;j++) {
//	        	i++;
//	        	Object[] data = new Object[21];
//	        	if ("2".equals(this.getQ_page())) { //跳頁方式:部門別
//	        		data = new Object[22];
//	        	}
//	        	
//	        	data[0] = enterorgMap.get(this.getQ_enterOrg());
//	        	data[1] = getUserName();
//	            data[2] =  "財產區分別" + j/40;
//	            data[3] = "部門別" + j/20;;
//	            data[4] = Common.formatYYYMMDD(getQ_deprYM()+ "01",2);
//	            int year = Integer.parseInt(getQ_deprYM().substring(0,3));
//            	int month = Integer.parseInt(getQ_deprYM().substring(3,5));
//            	data[5] = Common.formatYYYMMDD(getQ_deprYM() + Common.getLastDayOfMonth(year,month),2);
//            	String propertyno = "1234567890" + j;
//            	if (propertyno.length() > 7) {
//            		data[6] = propertyno.substring(0, 7) + "-" + propertyno.substring(7);
//            	} else {
//            		data[6] = propertyno;
//            	}
//            	data[7] =  Common.formatFrontZero("0" + j,4);
//	            data[8] = "propertyname1" + j;
//	            
//	            data[9] = Common.MaskDate("1010101");
//	        	data[10] = Common.MaskDate("1010101");
//	        	String escroworilimitmonth = "";
//	        	
//	        	int a = i;
//	        	int b = 12;
//	        	int c = a/b;
//	        	int d = a % b;
//	        	if (c != 0) {
//	        		escroworilimitmonth += c + "年";
//	        	}
//	        	if (d != 0) {
//	        		escroworilimitmonth += d + "個月";
//	        	}
//	        	data[11] = escroworilimitmonth;
//	        	data[12] = 100d;
//	        	data[13] = 200d;
//	        	data[14] = 300d;
//	           	data[15] = 400d;
//	        	data[16] = 500d;
//	        	data[17] = 600d;
//	        	data[18] = 700d;
//	        	data[19] = 800d;
//	        	if ("1".equals(this.getQ_page())) { //跳頁方式:部門別
//	        		if ("Y".equals(this.getQ_print())) {
//	        			data[20] = "說明：說明1";
//	        		} else if ("N".equals(this.getQ_print())) {
//	        			data[20] = "";
//	        		} 
//	        	} else if ("2".equals(this.getQ_page())) { //跳頁方式:部門別單位
//	        		if ("Y".equals(this.getQ_print())) {
//	        			data[20] = "說明：說明2";
//	        		} else if ("N".equals(this.getQ_print())) {
//	        			data[20] = "";
//	        		} 
//	        		data[21] = "部門別單位" + j;
//	        	}
//	            rowData.addElement(data);
//	        }
	        
	        Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model.setDataVector(data, columns);
	    }catch(Exception x){
	       x.printStackTrace();
	    }finally{
	        db.closeAll();
	    }
	    return model;
	}
}
