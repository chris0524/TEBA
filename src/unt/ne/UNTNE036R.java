/*
*<br>程式目的：非消耗品小標籤查詢檔 
*<br>程式代號：untne036r
*<br>撰寫日期：94/11/29
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.ne;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTNE036R extends UNTNE007R{

	public DefaultTableModel getResultModel() throws Exception{
	UNTNE036R obj = this;
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
	Object[] data ;
	Vector rowData = new Vector();
	String enterDateName = "";
	//int i;
    try{
    	String[] columns = new String[] {"ENTERORGNAME","PROPERTYNAME","PROPERTYNO","KEEPERNAME","SOURCEDATE","LIMITYEAR","unitName","barcode","place"};

    	String execSQL="select distinct b.enterOrg, b.ownership, c.keepunit, c.keeper, b.propertyNo, c.serialNo, " +"\n"+
    					" d.ORGANSNAME as enterOrgName, b.propertyNo, c.serialNo, " +"\n"+
    					" (select e.limitYear from SYSPK_PropertyCode2 e where b.enterOrg = e.enterOrg and b.propertyNo = e.propertyNo) as limitYear, " +"\n"+
    					" b.otherLimitYear, "+"\n"+
    					" (select e.propertyName from SYSPK_PropertyCode2 e where b.enterOrg = e.enterOrg and b.propertyNo = e.propertyNo) as propertyName, " +"\n"+
    					" f.keeperName, b.sourceDate, " + "\n"+
    					" b.enterOrg, b.ownership, b.propertyNo, c.serialNo, " +"\n"+
    					" c.bookValue ,c.barcode ,b.buydate ,c.place, p.unitName"+ "\n"+
    					" from UNTNE_AddProof a, UNTNE_Nonexp b, UNTNE_NonexpDetail c, SYSCA_Organ d, UNTMP_Keeper f, "+"\n"+
    					" UNTNE_AdjustProof j, UNTNE_AdjustDetail k, " +"\n"+
    					" UNTNE_ReduceProof l, UNTNE_ReduceDetail m, " +"\n"+
    					" UNTNE_MoveProof n, UNTNE_MoveDetail o, UNTMP_KeepUnit p "+"\n"+
						" where 1=1 and a.enterOrg = d.organID and b.dataState='1' and c.dataState='1' " +"\n"+
						//" and b.enterOrg = e.enterOrg and b.propertyNo = e.propertyNo " +
						" and a.caseNo=b.caseNo(+) and a.enterOrg=b.enterOrg(+) and a.ownership=b.ownership(+) " +"\n"+
						" and c.enterOrg(+)=b.enterOrg and c.ownership(+)=b.ownership and b.lotNo=c.lotNo(+) and b.propertyNo=c.propertyNo(+) " +"\n"+
						" and b.enterOrg=f.enterOrg and c.keepUnit=f.unitNo and c.keeper=f.keeperNo "+"\n"+
						" and j.enterOrg(+)=k.enterOrg and j.ownership(+)=k.ownership and j.caseNo(+)=k.caseNo " +"\n"+
						" and k.enterOrg(+)=c.enterOrg and k.ownership(+)=c.ownership and k.propertyNo(+)=c.propertyNo and k.lotNo(+)=c.lotNo and k.serialNo(+)=c.serialNo"+"\n"+
						" and l.enterOrg(+)=m.enterOrg and l.ownership(+)=m.ownership and l.caseNo(+)=m.caseNo " +"\n"+
						" and m.enterOrg(+)=c.enterOrg and m.ownership(+)=c.ownership and m.propertyNo(+)=c.propertyNo and m.lotNo(+)=c.lotNo and m.serialNo(+)=c.serialNo"+"\n"+
						" and n.enterOrg(+)=o.enterOrg and n.ownership(+)=o.ownership and n.caseNo(+)=o.caseNo " +"\n"+
						" and o.enterOrg(+)=c.enterOrg and o.ownership(+)=c.ownership and o.propertyNo(+)=c.propertyNo and o.lotNo(+)=c.lotNo and o.serialNo(+)=c.serialNo"+"\n"+
						" and c.enterOrg = p.enterorg and c.keepUnit = p.unitno "+"\n"+ //keepUnit欄位位置 & ＋的使用時機
						"";
    	/**
    	 * 當作業種類(q_workKind)選擇"增加單"時則帶入"a"，因table為"a"
    	 *                     選擇"增減值單"時則帶入"j"，因table為"j"
    	 *                     選擇"減損單"時則帶入"l"，因table為"l"
    	 *                     選擇"移動單"時則帶入"n"，因table為"n"
    	 */
    	if("a".equals(getQ_workKind())){
    		enterDateName = "enterDate";
    	}else if("j".equals(getQ_workKind())){
    		enterDateName = "adjustDate";
    	}else if("l".equals(getQ_workKind())){
    		enterDateName = "reduceDate";
    	}else if("n".equals(getQ_workKind())){
    		enterDateName = "moveDate";
    	}
    	if (!"".equals(getQ_enterOrg())) {
			execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
		} else {
			if (!getIsAdminManager().equalsIgnoreCase("Y")) {
				if ("Y".equals(getIsOrganManager())) {					
					//execSQL += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";		
					execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";		
				} else {
					execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
				}
			}
		}

		if (!"".equals(Common.get(getQ_ownership()))){
    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
		if (!"".equals(getQ_caseNoS()))
			execSQL +=" and "+getQ_workKind()+".caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
		if (!"".equals(getQ_caseNoE()))
			execSQL +=" and "+getQ_workKind()+".caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		execSQL += " and "+getQ_workKind()+".writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		execSQL += " and "+getQ_workKind()+".writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
		if (!"".equals(getQ_proofDoc()))
			execSQL += " and "+getQ_workKind()+".proofDoc like '%" + getQ_proofDoc() + "%'" ;
		if (!"".equals(getQ_proofNoS())) 
			execSQL += " and "+getQ_workKind()+".proofNo >= " + Common.sqlChar(getQ_proofNoS());		
		if (!"".equals(getQ_proofNoE())) 
			execSQL += " and "+getQ_workKind()+".proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
		if (!"".equals(getQ_closing()))
		    execSQL+=" and "+getQ_workKind()+".closing = " + Common.sqlChar(getQ_closing()) ;
		if (!"".equals(getQ_enterDateS()))
		    execSQL+=" and "+getQ_workKind()+"." + enterDateName + " >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateS(),"2"));
		if (!"".equals(getQ_enterDateE()))
		    execSQL+=" and "+getQ_workKind()+"." + enterDateName + " <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_enterDateE(),"2"));
		if (!"".equals(getQ_propertyNoS()))
		    execSQL+=" and b.propertyNo >= " + Common.sqlChar(getQ_propertyNoS());		
		if (!"".equals(getQ_propertyNoE()))
		    execSQL+=" and b.propertyNo<=" + Common.sqlChar(getQ_propertyNoE());						
		if (!"".equals(getQ_propertyKind()))
		    execSQL+=" and b.propertyKind = " + Common.sqlChar(getQ_propertyKind()) ;
		if (!"".equals(getQ_fundType()))
		    execSQL+=" and b.fundType = " + Common.sqlChar(getQ_fundType()) ;
		if (!"".equals(getQ_valuable()))
		    execSQL+=" and b.valuable = " + Common.sqlChar(getQ_valuable()) ;	    
		if (!"".equals(getQ_serialNoS()))
		    execSQL+=" and c.serialNo >= " + Common.sqlChar(getQ_serialNoS());		
		if (!"".equals(getQ_serialNoE()))
		    execSQL+=" and c.serialNo <= " + Common.sqlChar(getQ_serialNoE());			
		if (!"".equals(getQ_keepUnit()))
		    execSQL+=" and c.keepUnit = " + Common.sqlChar(getQ_keepUnit()) ;	    
		if (!"".equals(getQ_keeper()))
		    execSQL+=" and c.keeper = " + Common.sqlChar(getQ_keeper()) ;	    
		if (!"".equals(getQ_useUnit()))
		    execSQL+=" and c.useUnit = " + Common.sqlChar(getQ_useUnit()) ;	    
		if (!"".equals(getQ_userNo()))
		    execSQL+=" and c.userNo = " + Common.sqlChar(getQ_userNo()) ;	    
    	
		if (!"".equals(Common.get(this.getQ_reduceCaseNo()))){
    		execSQL +=" and o.caseNo = " + util.Common.sqlChar(getQ_reduceCaseNo());
    	}
		
		execSQL+=" order by b.enterOrg, b.ownership, c.keepUnit, c.keeper, b.propertyNo, c.serialNo ";
   
		//System.out.println("--- untne036r 小標籤 --\n"+execSQL);
		
    	//標籤跳空處理
    	if(q_blankSpace==null || q_blankSpace.equals("")){
    		q_blankSpace="0";
    	}
    	int blankSpace=Integer.parseInt(q_blankSpace);
    	if(blankSpace>0){
    	    while(blankSpace>0){  
    	        data = new Object[9];
    	        data[0] = " ";
    	        data[1] = " ";
    	        data[2] = " ";
    	        data[3] = " ";
    	        data[4] = " ";
    	        data[5] = " ";
    	        data[6] = " ";
    	        data[7] = "";
    	        data[8] = " ";
    	        blankSpace--;
    	        rowData.addElement(data);
    	    }
    	}
    	//System.out.println(execSQL);
        ResultSet rs = db.querySQL(execSQL);
        while(rs.next()){
        	
        	data = new Object[9];
            data[0] = rs.getString("enterOrgName");
            data[1] = "名稱："+rs.getString("propertyName");
            data[2] = "編號："+rs.getString("propertyNo")+"-"+rs.getString("serialNo");
            if(obj.getQ_printKeeper().equals("Y")){
                data[3] = "保管人："+rs.getString("keeperName");
            }else{
                data[3] = "";
            }
            if(rs.getString("sourceDate")!=null){
                data[4] = "取得日期："+rs.getString("sourceDate").substring(0,3)+"/"+rs.getString("sourceDate").substring(3,5)+"/"+rs.getString("sourceDate").substring(5,7);
            }else{
                data[4] = "取得日期：";
            }
            if(rs.getString("limitYear")!=null){
                data[5] = "年限："+rs.getString("limitYear");
            }else{
                data[5] = "年限："+rs.getString("otherLimitYear");
            }
            
//            if(rs.getString("bookvalue") != null){
//            	data[6]="價值:"+rs.getString("bookvalue");
//            }else{
//            	data[6]="價值:";
//            }
            if(rs.getString("unitName") != null){
            	data[6]="單位:"+rs.getString("unitName");
            }else{
            	data[6]="單位:";
            }
            if(rs.getString("barCode") != null){
            	data[7]=rs.getString("barCode");
            }else{
            	data[7] = "";
            }
            if(rs.getString("place") != null){
            	data[8]="地點:"+rs.getString("place");
            }else{
            	data[8]="地點:";
            }
            //for(i=0;i<6;i++)if(data[i]==null)data[i]="";
			rowData.addElement(data);
        }
    	
        Object[][] finalData = new Object[0][0];
        finalData = (Object[][])rowData.toArray(finalData);
        model.setDataVector(finalData, columns);  

    }catch(Exception x){
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    
    return model;
}

}
