

/*
*<br>程式目的：公有房屋及附著物拆除改建(報廢)查核報告表查詢檔 
*<br>程式代號：untbu014r
*<br>撰寫日期：94/11/22
*<br>程式作者：chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


import util.*;

public class UNTBU014R extends SuperBean{


	String enterOrg;
	String ownership;
	String propertyNo1;
	String propertyNo2;
	String serialNo;
	String verify;

	String q_enterOrg;
	String q_ownership;
	String q_propertyNo1;
	String q_propertyNo2;
	String q_serialNo;
	String q_verify;
	String q_propertyNoSName;
	String q_law;

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getPropertyNo1(){ return checkGet(propertyNo1); }
	public void setPropertyNo1(String s){ propertyNo1=checkSet(s); }
	public String getPropertyNo2(){ return checkGet(propertyNo2); }
	public void setPropertyNo2(String s){ propertyNo2=checkSet(s); }
	public String getSerialNo(){ return checkGet(serialNo); }
	public void setSerialNo(String s){ serialNo=checkSet(s); }
	public String getVerify(){ return checkGet(verify); }
	public void setVerify(String s){ verify=checkSet(s); }

	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_propertyNo1(){ return checkGet(q_propertyNo1); }
	public void setQ_propertyNo1(String s){ q_propertyNo1=checkSet(s); }
	public String getQ_propertyNo2(){ return checkGet(q_propertyNo2); }
	public void setQ_propertyNo2(String s){ q_propertyNo2=checkSet(s); }
	public String getQ_serialNo(){ return checkGet(q_serialNo); }
	public void setQ_serialNo(String s){ q_serialNo=checkSet(s); }
	public String getQ_verify(){ return checkGet(q_verify); }
	public void setQ_verify(String s){ q_verify=checkSet(s); }
	public String getQ_propertyNoSName(){ return checkGet(q_propertyNoSName); }
	public void setQ_propertyNoSName(String s){ q_propertyNoSName=checkSet(s); }
	public String getQ_law(){ return checkGet(q_law); }
	public void setQ_law(String s){ q_law=checkSet(s); }
	
	
	String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	while(q_serialNo!=null&&!"".equals(q_serialNo)&&q_serialNo.length()<7){q_serialNo="0"+q_serialNo;}
    	String[] columns = new String[] {"ENTERORGNAME","DOORPLATE","BUILDSTYLE","STUFF","BUILDDATE","BOOKVALUE",
    			"USERELATION","USEUNIT","AREAB","AREA1","AREA2","AREA3","AREA4","AREA5","SUMOFAREA","ATTACHMENT",
    			"STUFF1","NUM","AREA","SIGNNO1","SIGNNO2","SIGNNO3","SIGNNO4","SIGNNO5",
    			"HOLDAREA","BOOKVALUE1","OWNERSHIP1","LAW","NO","PROPERTYNAME","USEDATES"};
    	String execSQL="select c.organAName as enterOrgName," +
    			"(select k.addrname from sysca_addr k where k.addrkind='1' and k.addrid=a.doorPlate1) as addrname1," +
    			"(select l.addrname from sysca_addr l where l.addrkind='2' and l.addrid=a.doorPlate2) as addrname2," +
    			"(select m.addrname from sysca_addr m where m.addrkind='3' and m.addrid=a.doorPlate3) as addrname3," +
    			" a.doorPlate4 as addrname4," +
    			"(select g.codename from SYSCA_CODE g where g.codekindid = 'BST' and a.buildStyle=g.codeid) as buildStyle," +
    			"(select h.codename from SYSCA_CODE h where h.codekindid = 'STU' and a.stuff=h.codeid) as stuff," +
    			"a.buildDate," +
    			"a.bookValue," +
    			"(select o.codename from SYSCA_CODE o where o.codekindid = 'URE' and b.useRelation=o.codeid) as useRelation," +
    			" b.useUnit, "+
				" b.useUnit1,"+
    			"(select sum(q.area) from UNTBU_Floor q where q.floor like 'B%' and a.enterorg=q.enterorg and a.ownership=q.ownership and a.propertyNo=q.propertyNo and a.serialNo=q.serialNo) as areab," +
    			"(select sum(r.area) from UNTBU_Floor r where r.floor = '001' and a.enterorg=r.enterorg and a.ownership=r.ownership and a.propertyNo=r.propertyNo and a.serialNo=r.serialNo) as area1," +
    			"(select sum(s.area) from UNTBU_Floor s where s.floor = '002' and a.enterorg=s.enterorg and a.ownership=s.ownership and a.propertyNo=s.propertyNo and a.serialNo=s.serialNo) as area2," +
    			"(select sum(t.area) from UNTBU_Floor t where t.floor = '003' and a.enterorg=t.enterorg and a.ownership=t.ownership and a.propertyNo=t.propertyNo and a.serialNo=t.serialNo) as area3," +
    			"(select sum(u.area) from UNTBU_Floor u where u.floor = '004' and a.enterorg=u.enterorg and a.ownership=u.ownership and a.propertyNo=u.propertyNo and a.serialNo=u.serialNo) as area4," +
    			"(select sum(v.area) from UNTBU_Floor v where v.floor = '005' and a.enterorg=v.enterorg and a.ownership=v.ownership and a.propertyNo=v.propertyNo and a.serialNo=v.serialNo) as area5," +
    			"(select w.codename from SYSCA_CODE w where w.codekindid = 'FLC' and e.attachment=w.codeid) as attachment," +
    			"(select x.codename from SYSCA_CODE x where x.codekindid = 'STU' and e.stuff=x.codeid) as stuff1," +
    			"e.area," +
    			"d.signNo, " +
    			"(select x1.codename from SYSCA_CODE x1 where x1.codekindid = 'FIE' and d.field=x1.codeid) as field," +
    			"d.landRule," +
    			"d.holdArea," +
    			"a.ownership ,(select codename from sysca_code x where x.codekindid = 'OWA' and x.codeid = a.ownership ) as ownershipName,"+
    			"(select x2.codename from SYSCA_CODE x2 where x2.codekindid = 'OWN' and d.ownership1=x2.codeid) as ownership1, " + 
    			" (select y.propertyname from Syspk_Propertycode y where a.propertyno = y.propertyno) as propertyname, " +
    			" f.limityear, a.otherlimityear, "+ 
    			" a.enterOrg, a.propertyno, a.serialno, a.oldserialno, f.propertyname " + "\n";
    	String execSQL1=" from UNTBU_Building a " +
    					" left join UNTBU_Manage b on a.enterorg=b.enterorg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo" +
    					" left join UNTBU_Base d on a.enterorg=d.enterorg and a.ownership=d.ownership and a.propertyNo=d.propertyNo and a.serialNo=d.serialNo" +
    					" left join UNTBU_Attachment1 e on a.enterorg=e.enterorg and a.ownership=e.ownership and a.propertyNo=e.propertyNo and a.serialNo=e.serialNo" +
    					",SYSCA_Organ c, SYSPK_PropertyCode f ";
    	execSQL1=execSQL1+"where a.enterOrg=c.organID " +
    			"  and a.propertyNo = f.propertyNo ";
    	execSQL1=execSQL1+" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	
    	if (!"".equals(getQ_enterOrg())) {
    		execSQL1 +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				execSQL1 += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				execSQL1 +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	
    	
    	if (!"".equals(Common.get(getQ_propertyNo1())))
    		execSQL1 = execSQL1 + " and a.propertyNo = " + util.Common.sqlChar(getQ_propertyNo1());
    	if (!"".equals(Common.get(getQ_serialNo())))
    		execSQL1 = execSQL1 + " and a.serialNo = " + util.Common.sqlChar(getQ_serialNo());
    	if (!"".equals(Common.get(getQ_verify())))
    		execSQL1 = execSQL1 + " and a.verify = " + util.Common.sqlChar(getQ_verify());
    	
    	
    	execSQL=execSQL+execSQL1;

    	String tansToDouble;

    	String SignNo="";
    	String UseUnitTemp="";
    	double AreaB,Area1,Area2,Area3,Area4,Area5;
    	
System.out.println("-- untbu014r getResultModel --\n" + execSQL);
        
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
        while(rs.next()){
        	Object[] data = new Object[40];
        	data[0] = rs.getString("enterOrgName");
        	data[1] = rs.getString("addrname1") + rs.getString("addrname2") + rs.getString("addrname3") + rs.getString("addrname4");
        	data[2] = rs.getString("buildStyle");
            data[3] = rs.getString("stuff");
            data[4] = rs.getString("buildDate").substring(0,3) + "/" + rs.getString("buildDate").substring(3,5) + "/" + rs.getString("buildDate").substring(5) ;
            tansToDouble= rs.getString("bookValue");
            if(tansToDouble!=null)data[5]= new Double(Double.parseDouble(tansToDouble));
            else data[5]=new Double(0);
            
            data[6] = rs.getString("useRelation");
            data[7] = rs.getString("useUnit")==null?null:Common.getORGANANAME(rs.getString("useUnit"));
            UseUnitTemp = rs.getString("useUnit1");
            if("".equals(data[7])||data[7]==null)data[7]=UseUnitTemp;
            
            tansToDouble= rs.getString("areab");
            if(tansToDouble!=null){data[8]= new Double(Double.parseDouble(tansToDouble));AreaB=Double.parseDouble(tansToDouble);}
            else {data[8]=new Double(0);AreaB=0;}
            
            tansToDouble= rs.getString("area1");
            if(tansToDouble!=null){data[9]= new Double(Double.parseDouble(tansToDouble));Area1=Double.parseDouble(tansToDouble);}
            else {data[9]=new Double(0);Area1=0;}
            tansToDouble= rs.getString("area2");
            if(tansToDouble!=null){data[10]= new Double(Double.parseDouble(tansToDouble));Area2=Double.parseDouble(tansToDouble);}
            else {data[10]=new Double(0);Area2=0;}
            tansToDouble= rs.getString("area3");
            if(tansToDouble!=null){data[11]= new Double(Double.parseDouble(tansToDouble));Area3=Double.parseDouble(tansToDouble);}
            else {data[11]=new Double(0);Area3=0;}
            tansToDouble= rs.getString("area4");
            if(tansToDouble!=null){data[12]= new Double(Double.parseDouble(tansToDouble));Area4=Double.parseDouble(tansToDouble);}
            else {data[12]=new Double(0);Area4=0;}
            tansToDouble= rs.getString("area5");
            if(tansToDouble!=null){data[13]= new Double(Double.parseDouble(tansToDouble));Area5=Double.parseDouble(tansToDouble);}
            else {data[13]=new Double(0);Area5=0;}
            
            data[14]=new Double(AreaB+Area1+Area2+Area3+Area4+Area5);
            data[15] = rs.getString("attachment");
            data[16] = rs.getString("stuff1");
            data[17]="1";
            tansToDouble= rs.getString("area");
            if(tansToDouble!=null){
            	data[18]= new Double(Double.parseDouble(tansToDouble));
            	data[17]="1";
            }else{
            	data[18]=new Double(0);
            	data[17]="";
            }
         
            
            data[19] = getSignDescName(rs.getString("signNo").substring(0,1) + "000000");
            data[20] = getSignDescName(rs.getString("signNo").substring(0,3) + "0000");
            SignNo = getSignDescName(rs.getString("signNo").substring(0,7));
            if(SignNo!=null &&!"".equals(SignNo)){
            data[21]=SignNo.substring(0,SignNo.indexOf("段")+1);
            if(SignNo.length()>=6)data[22]=SignNo.substring(SignNo.indexOf("段")+1);
            else data[22]=null;
            }else{data[21]=null;data[23]=null;}
            
            if(rs.getString("signNo").substring(7)!=null)data[23] = rs.getString("signNo").substring(7).substring(0,4)+"-"+rs.getString("signNo").substring(7).substring(4,8);
            else data[23]=null;
            //data[24] = rs.getString("field");
            //data[25] = rs.getString("landRule");
            tansToDouble= rs.getString("holdArea");
            if(tansToDouble!=null)data[24]= new Double(Double.parseDouble(tansToDouble));
            else data[24]=new Double(0);
            
            tansToDouble= queryMaxPrice(rs.getString("enterOrg"), rs.getString("ownership"), rs.getString("propertyNo"), rs.getString("serialNo"));            
            if(tansToDouble!=null)data[25]= new Double(Double.parseDouble(("".equals(tansToDouble)?"0":tansToDouble)));
            else data[25]=new Double(0);
            
            data[26] = queryMaxPrice(rs.getString("enterOrg"), rs.getString("ownership"), rs.getString("propertyNo"), rs.getString("serialNo"));
            //if ("02".equals(data[29]))data[29]="國有";
        	//else if ("01".equals(data[29]))data[29]="市有";
        	//else data[29]=null;
            data[27] = getQ_law();
            if (rs.getString("oldserialno") != null && rs.getString("oldserialno") != "" ){
            	data[28] = rs.getString("propertyNo").substring(0,7)+"-"+rs.getString("propertyNo").substring(7)+"-"+rs.getString("serialNo")+" (原"+rs.getString("oldserialno")+")";
            }else{
            	data[28] = rs.getString("propertyNo").substring(0,7)+"-"+rs.getString("propertyNo").substring(7)+"-"+rs.getString("serialNo");
            }
            data[29] = rs.getString("propertyname");
            
            if (!"".equals(Common.get(rs.getString("limityear")))){
            	data[30] = rs.getString("limityear");
            }else{
            	data[30] = Common.get(rs.getString("otherlimityear"));
            }
            
            rowData.addElement(data);
            
            }
    	
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


private String getSignDescName(String signNo){
	Database db = null;
	ResultSet rs = null;
	String sql = null;
	String result = null;
	try{
		sql = "select signdesc from SYSCA_SIGN where" +
				" signNo = " + Common.sqlChar(signNo);
		
		db = new Database();
		rs = db.querySQL(sql);
		if(rs.next()){				
			result = rs.getString("signdesc");
		}
		rs.close();
	}catch(Exception e){
		System.out.println("getSignDescName Exception => " + e.getMessage());
	}finally{
		db.closeAll();
	}
	return result;
}

private String queryMaxPrice(String enterOrg, String ownership, String propertyNo, String serialNo){
	String result = "";
	String sql = "";
	
	sql = "select d1.signNo from UNTBU_Base d1" + 
			" where d1.enterOrg = " + Common.sqlChar(enterOrg) +
			" and d1.ownership = " + Common.sqlChar(ownership) +
			" and d1.propertyNo = " + Common.sqlChar(propertyNo) +
			" and d1.serialNo = " + Common.sqlChar(serialNo);
	String signNo = queryFromDB(sql);
	
	String bulletinDate = "";
	if("".equals(checkGet(signNo))){
		
	}else{
		sql = "select max(b1.bulletinDate) from UNTLA_Price b1,UNTLA_Land c1 " +
				" where c1.dataState='1' and c1.verify='Y'" +
				" and c1.signNo = " + Common.sqlChar(signNo.substring(0,7)) +
				" and c1.enterOrg=b1.enterOrg   and c1.ownership=b1.ownership  and c1.propertyNo=b1.propertyNo  and c1.serialNo=b1.serialNo";
		bulletinDate = queryFromDB(sql);
		
		sql = "select max(b1.priceUnit) from UNTLA_Price b1,UNTLA_Land c1 " +
				" where c1.dataState='1' and c1.verify='Y'" +
				" and c1.signNo = " + Common.sqlChar(signNo.substring(0,7)) +
				" and c1.enterOrg=b1.enterOrg and c1.ownership=b1.ownership  and c1.propertyNo=b1.propertyNo  and c1.serialNo=b1.serialNo" +
				" and b1.bulletinDate = " + Common.sqlChar(bulletinDate);
		result = bulletinDate + queryFromDB(sql);

	}
	
	
	return result;
}

	private String queryFromDB(String sql){
		Database db = null;
		ResultSet rs = null;
		String result = "";
		try{
			db = new Database();
			rs = db.querySQL(sql);
			if(rs.next()){
				result = checkGet(rs.getString(1));
			}
		}catch(Exception e){
			System.out.println("Exception : " + e.getMessage());
		}finally{
			db.closeAll();
		}
		return result;
	}

}
