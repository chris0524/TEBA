/*
*<br>程式目的：地價稅課稅明細資料表
*<br>程式代號：untte005r
*<br>撰寫日期：96/9/10
*<br>程式作者：blair
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.te;  

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTTE006R extends SuperBean{

	String q_lndYYY;
	String q_lndLosn;
	String q_signNo;
	String q_signNo1;
	String q_signNo2;
	String q_conformYN;

	public String getQ_lndYYY(){ return checkGet(q_lndYYY); }
	public void setQ_lndYYY(String s){ q_lndYYY=checkSet(s); }
	public String getQ_lndLosn(){ return checkGet(q_lndLosn); }
	public void setQ_lndLosn(String s){ q_lndLosn=checkSet(s); }
	public String getQ_signNo(){ return checkGet(q_signNo); }
	public void setQ_signNo(String s){ q_signNo=checkSet(s); }
	public String getQ_signNo1(){ return checkGet(q_signNo1); }
	public void setQ_signNo1(String s){ q_signNo1=checkSet(s); }
	public String getQ_signNo2(){ return checkGet(q_signNo2); }
	public void setQ_signNo2(String s){ q_signNo2=checkSet(s); }
	public String getQ_conformYN(){ return checkGet(q_conformYN); }
	public void setQ_conformYN(String s){ q_conformYN=checkSet(s); }
	
	String lndYYY;
	String lndLosn;
	String signNo;
	
	String lndTaxTp1;
	String levyArea1;
	String abateArea1;
	String abateAll1;
	String lndPreTax1;
	String lndTaxTp2;
	String levyArea2;
	String abateArea2;
	String abateAll2;
	String lndPreTax2;
	String lndTaxTp3;
	String levyArea3;
	String abateArea3;
	String abateAll3;
	String lndPreTax3;
	String lndTaxTp4;
	String levyArea4;
	String abateArea4;
	String abateAll4;
	String lndPreTax4;
	String lndTaxTp5;
	String levyArea5;
	String abateArea5;
	String abateAll5;
	String lndPreTax5;
	
	String secChiNM;
	String OD;
	String OA;
	String editID;
	String editDate;
	String editTime;
	
	public String getLndYYY() { return checkGet(lndYYY); }
    public void setLndYYY(String s) { lndYYY = checkSet(s); }
    public String getLndLosn() { return checkGet(lndLosn); }
    public void setLndLosn(String s) { lndLosn = checkSet(s); }
    public String getSignNo() { return checkGet(signNo); }
    public void setSignNo(String s) { signNo = checkSet(s); }
    
    public String getLndTaxTp1() { return checkGet(lndTaxTp1); }
    public void setLndTaxTp1(String s) { lndTaxTp1 = checkSet(s); }
    public String getLndTaxTp2() { return checkGet(lndTaxTp2); }
    public void setLndTaxTp2(String s) { lndTaxTp2 = checkSet(s); }
    public String getLndTaxTp3() { return checkGet(lndTaxTp3); }
    public void setLndTaxTp3(String s) { lndTaxTp3 = checkSet(s); }
    public String getLndTaxTp4() { return checkGet(lndTaxTp4); }
    public void setLndTaxTp4(String s) { lndTaxTp4 = checkSet(s); }
    public String getLndTaxTp5() { return checkGet(lndTaxTp5); }
    public void setLndTaxTp5(String s) { lndTaxTp5 = checkSet(s); }
    
    public String getLevyArea1() { return checkGet(levyArea1); }
    public void setLevyArea1(String s) { levyArea1 = checkSet(s); }
    public String getLevyArea2() { return checkGet(levyArea2); }
    public void setLevyArea2(String s) { levyArea2 = checkSet(s); }
    public String getLevyArea3() { return checkGet(levyArea3); }
    public void setLevyArea3(String s) { levyArea3 = checkSet(s); }
    public String getLevyArea4() { return checkGet(levyArea4); }
    public void setLevyArea4(String s) { levyArea4 = checkSet(s); }
    public String getLevyArea5() { return checkGet(levyArea5); }
    public void setLevyArea5(String s) { levyArea5 = checkSet(s); }
    
    public String getAbateArea1() { return checkGet(abateArea1); }
    public void setAbateArea1(String s) { abateArea1 = checkSet(s); }
    public String getAbateArea2() { return checkGet(abateArea2); }
    public void setAbateArea2(String s) { abateArea2 = checkSet(s); }
    public String getAbateArea3() { return checkGet(abateArea3); }
    public void setAbateArea3(String s) { abateArea3 = checkSet(s); }
    public String getAbateArea4() { return checkGet(abateArea4); }
    public void setAbateArea4(String s) { abateArea4 = checkSet(s); }
    public String getAbateArea5() { return checkGet(abateArea5); }
    public void setAbateArea5(String s) { abateArea5 = checkSet(s); }

    public String getAbateAll1() { return checkGet(abateAll1); }
    public void setAbateAll1(String s) { abateAll1 = checkSet(s); }
    public String getAbateAll2() { return checkGet(abateAll2); }
    public void setAbateAll2(String s) { abateAll2 = checkSet(s); }
    public String getAbateAll3() { return checkGet(abateAll3); }
    public void setAbateAll3(String s) { abateAll3 = checkSet(s); }
    public String getAbateAll4() { return checkGet(abateAll4); }
    public void setAbateAll4(String s) { abateAll4 = checkSet(s); }
    public String getAbateAll5() { return checkGet(abateAll5); }
    public void setAbateAll5(String s) { abateAll5 = checkSet(s); }
    
    public String getLndPreTax1() { return checkGet(lndPreTax1); }
    public void setLndPreTax1(String s) { lndPreTax1 = checkSet(s); }
    public String getLndPreTax2() { return checkGet(lndPreTax2); }
    public void setLndPreTax2(String s) { lndPreTax2 = checkSet(s); }
    public String getLndPreTax3() { return checkGet(lndPreTax3); }
    public void setLndPreTax3(String s) { lndPreTax3 = checkSet(s); }
    public String getLndPreTax4() { return checkGet(lndPreTax4); }
    public void setLndPreTax4(String s) { lndPreTax4 = checkSet(s); }
    public String getLndPreTax5() { return checkGet(lndPreTax5); }
    public void setLndPreTax5(String s) { lndPreTax5 = checkSet(s); }
    
    public String getSecChiNM() { return checkGet(secChiNM); }
    public void setSecChiNM(String s) {secChiNM = checkSet(s); }
    public String getOD() { return checkGet(OD); }
    public void setOD(String s) { OD = checkSet(s); }
    public String getOA() { return checkGet(OA); }
    public void setOA(String s) {OA = checkSet(s); }
    public String getEditID() { return checkGet(editID); }
    public void setEditID(String s) {editID = checkSet(s); }
    public String getEditDate() { return checkGet(editDate); }
    public void setEditDate(String s) {editDate = checkSet(s); }
    public String getEditTime() { return checkGet(editTime); }
    public void setEditTime(String s) {editTime = checkSet(s); }
    
public DefaultTableModel getResultModel() throws Exception{
	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    
    try{
        String[] columns = new String[] { "lndYYY","signNo","conformY","conformN","couSignNo"};
             //                               0        1          2         3        4           
        String loc="";
		loc="E";
		if(!"".equals(getQ_signNo2()))
			loc=getQ_signNo2().substring(0,3);
		
        String execSQL = " select distinct subString(signno,1,3)as signno ,subString(signDesc,1,6)as signDesc from SYSCA_SIGN " + "\n" +
        			    " where signno like " +"'"+loc+"%' "+
        			    " order by subString(signno,1,3) ";
    	//System.out.println("地價稅課稅明細資料表:"+execSQL);
    	
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	while(rs.next()){
    		Object[] data = new Object[5];
		    		data[0]=getQ_lndYYY();
		    		data[1]=rs.getString("signDesc");
		    		Database db_Y = new Database();
		    	    try{
		    	        String execSQL_Y = " select count(*)as countY from  LND_Value a,UNTLA_Land b "+
		    	        				   " where 1=1 and a.signNo=b.signNo and a.lndYYY=" +getQ_lndYYY() +
		    	        				   " and a.signNo like '" + rs.getString("signno")+"%'"+
		    	        				   " and a.levyArea1=b.area " +
		    	        				   " group by subString(a.signNo,1,3)";
		    	    	//System.out.println("execSQL_Y:"+execSQL_Y);
		    	    	ResultSet rs_Y = db_Y.querySQL(execSQL_Y);
		    	    	if(rs_Y.next()){
		    	    		data[2]=new Integer(rs_Y.getString("countY"));
		    	    			
		    		    }else{
		    		    	data[2]=new Integer("0");
		    		    }
		    	    }catch(Exception e){
		    	       e.printStackTrace();
		    	    }finally{
		    	       db_Y.closeAll();
		    	    }
		    		
		    	    Database db_N = new Database();
		    	    try{
		    	        String execSQL_N = " select count(*)as countN from LND_Value a,UNTLA_Land b "+
		    	        				   " where 1=1 and a.signNo=b.signNo and a.lndYYY=" +getQ_lndYYY() +
		    	        				   " and a.signNo like  '" + rs.getString("signno")+"%'"+
		    	        				   " and a.levyArea1<>b.area " +
		    	        				   " group by subString(a.signNo,1,3)";
		    	    	//System.out.println("execSQL_N:"+execSQL_N);
		    	    	ResultSet rs_N = db_N.querySQL(execSQL_N);
		    	    	if(rs_N.next()){
		    	    		data[3]=new Integer(rs_N.getString("countN"));
		    		    }else{
		    		    	data[3]=new Integer("0");
		    		    }
		    	    }catch(Exception e){
		    	       e.printStackTrace();
		    	    }finally{
		    	       db_N.closeAll();
		    	    }
		    	    
		    	    Database db_YN = new Database();
		    	    try{
		    	        String execSQL_YN = " select count(*)as countYN from LND_Value a,UNTLA_Land b "+
		    	        				   " where 1=1 and a.signNo=b.signNo and a.lndYYY=" +getQ_lndYYY() +
		    	        				   " and a.signNo like '" + rs.getString("signno")+"%'"+
		    	        				   " group by subString(a.signNo,1,3)";
		    	    	//System.out.println("execSQL_YN:"+execSQL_YN);
		    	    	ResultSet rs_YN = db_YN.querySQL(execSQL_YN);
		    	    	if(rs_YN.next()){
		    	    		data[4]=new Integer(rs_YN.getString("countYN"));
		    		    }else{
		    		    	data[4]=new Integer("0");
		    		    }  
		    	    }catch(Exception e){
		    	       e.printStackTrace();
		    	    }finally{
		    	       db_YN.closeAll();
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

}
