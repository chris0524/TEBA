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

public class UNTTE005R extends SuperBean{

	String q_lndYYY;
	String q_lndLosn;
	String q_signNo;
	String q_signNo1;
	String q_signNo2;
	String q_signNo3;
	String q_signNo4;
	String q_signNo5;
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
	public String getQ_signNo3(){ return checkGet(q_signNo3); }
	public void setQ_signNo3(String s){ q_signNo3=checkSet(s); }
	public String getQ_signNo4(){ return checkGet(q_signNo4); }
	public void setQ_signNo4(String s){ q_signNo4=checkSet(s); }
	public String getQ_signNo5(){ return checkGet(q_signNo5); }
	public void setQ_signNo5(String s){ q_signNo5=checkSet(s); }
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
        //String[] columns = new String[] { "lndYYY","lndLosn", "signNo","lndTaxTp1","lndTaxTp2","levyArea1","levyArea2","lndPreTax1","lndPreTax2","abateArea1","abateArea2","abateAll1","abateAll2","dataNum","sumLevyArea","sumLndPre","sumAbateArea","sumAbateAll"};
        //                                      0       1          2          3        4              5           6          7           8            9            10          11           12        13           14           15           16           17           18
        String[] columns = new String[] { "lndYYY","lndLosn", "signNo","lndTaxTp1","lndTaxTp2","levyArea1","levyArea2","lndPreTax1","lndPreTax2","abateArea1","abateArea2","abateAll1","abateAll2","dataNum","sumLevyArea","sumLndPre","sumAbateArea","sumAbateAll","conformYN"};
        //    									 0       1          2          3        4              5           6          7           8            9            10          11           12        13           14           15           16           17           18
       
        
        String loc="";
        //select substr(signdesc,4) from SYSCA_SIGN where signno=substr('E09020100200010',1,7)  取得 區、段 名稱
        
        String execSQL="select a.lndYYY as lndYYY, a.lndLosn as lndLosn ," +"\n"+
        		" ((select substr(d.signdesc,4) from SYSCA_SIGN d where d.signno=" +"\n"+
        		"substr(a.signno,1,7))||substr(a.signno,8,4)||'-'||substr(a.signno,12,4)) as signno,"+"\n"+
        		" a.lndTaxTp1 as lndTaxTp1,a.lndTaxTp2 as lndTaxTp2," +"\n"+
        		" a.levyArea1 as levyArea1, a.levyArea2 as levyArea2 ," +"\n"+
        		" a.lndPreTax1 as lndPreTax1,a.lndPreTax2 as lndPreTax2, " +"\n"+
        		" a.abateArea1 as abateArea1,a.abateArea2 as abateArea2, " +"\n"+
        		" a.abateAll1 as abateAll1,a.abateAll2 as abateAll2, "+"\n"+
        		" b.area as area"+
        		" from LND_Value a,UNTLA_Land b" + 
        	" where 1=1 and a.signNo=b.signNo and ";
        	if (!"".equals(q_lndYYY)){
        		execSQL+=" a.lndYYY="+Common.sqlChar(q_lndYYY);
        	}
        	if(!"".equals(getQ_signNo1()))
				loc="E";
			if(!"".equals(getQ_signNo2()))
				loc=getQ_signNo2().substring(0,3);
			if(!"".equals(getQ_signNo3()))
				loc=getQ_signNo3().substring(0,7);
			if(!"".equals(getQ_signNo4()))
				loc+=getQ_signNo4();
			if(!"".equals(getQ_signNo5())){
				loc+=getQ_signNo5();
			}
			execSQL+=" and a.signNo like "+"'"+loc+"%'";
			execSQL+=" order by signno";
       
                      
    	
    	System.out.println("地價稅課稅明細資料表:"+execSQL);
    	
    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int i=0;
    	double levyAreaSum=0;
        double lndPreSum=0;
        double abateAreaSum=0;
        double abateAllSum=0;
    	while(rs.next()){
    		//Object[] data = new Object[19];
    		Object[] data = new Object[19];
    		if("N".equals(getQ_conformYN())){
	    		if(!(rs.getString("levyArea1")).equals(rs.getString("area"))){
	    			System.out.println(i);
	    			i++;
		    		data[0]=rs.getString("lndYYY");
		    		data[1]=rs.getString("lndLosn");
		    		data[2]=rs.getString("signNo");
		    		data[3]=rs.getString("lndTaxTp1");
		    		data[4]=rs.getString("lndTaxTp2");
		    		data[5]=new Double(rs.getString("levyArea1"));
		    		data[6]=new Double(rs.getString("levyArea2"));
		    		data[7]=new Double(rs.getString("lndPreTax1"));
		    		data[8]=new Double(rs.getString("lndPreTax2"));
		    		data[9]=new Double(rs.getString("abateArea1"));
		    		data[10]=new Double(rs.getString("abateArea2"));
		    		data[11]=new Double(rs.getString("abateAll1"));
		    		data[12]=new Double(rs.getString("abateAll2"));
		    		data[13]=Integer.toString(i);
		    		levyAreaSum=levyAreaSum+rs.getDouble("levyArea1")+rs.getDouble("levyArea2");
		    		lndPreSum=lndPreSum+rs.getDouble("lndPreTax1")+rs.getDouble("lndPreTax2");
		    		abateAreaSum=abateAreaSum+rs.getDouble("abateArea1")+rs.getDouble("abateArea2");
		    		abateAllSum=abateAllSum+rs.getDouble("abateAll1")+rs.getDouble("abateAll2");
		    		data[14]=new Double(levyAreaSum);
		    		data[15]=new Double(lndPreSum);
		    		data[16]=new Double(abateAreaSum);
		    		data[17]=new Double(abateAllSum);
		    		data[18]="不符合";
		    		rowData.addElement(data);
	    		}
    		}else if ("Y".equals(getQ_conformYN())){
    			
	    		if((rs.getString("levyArea1")).equals(rs.getString("area"))){
	    			System.out.println(i);
	    			i++;
		    		data[0]=rs.getString("lndYYY");
		    		data[1]=rs.getString("lndLosn");
		    		data[2]=rs.getString("signNo");
		    		data[3]=rs.getString("lndTaxTp1");
		    		data[4]=rs.getString("lndTaxTp2");
		    		data[5]=new Double(rs.getString("levyArea1"));
		    		data[6]=new Double(rs.getString("levyArea2"));
		    		data[7]=new Double(rs.getString("lndPreTax1"));
		    		data[8]=new Double(rs.getString("lndPreTax2"));
		    		data[9]=new Double(rs.getString("abateArea1"));
		    		data[10]=new Double(rs.getString("abateArea2"));
		    		data[11]=new Double(rs.getString("abateAll1"));
		    		data[12]=new Double(rs.getString("abateAll2"));
		    		data[13]=Integer.toString(i);
		    		levyAreaSum=levyAreaSum+rs.getDouble("levyArea1")+rs.getDouble("levyArea2");
		    		lndPreSum=lndPreSum+rs.getDouble("lndPreTax1")+rs.getDouble("lndPreTax2");
		    		abateAreaSum=abateAreaSum+rs.getDouble("abateArea1")+rs.getDouble("abateArea2");
		    		abateAllSum=abateAllSum+rs.getDouble("abateAll1")+rs.getDouble("abateAll2");
		    		data[14]=new Double(levyAreaSum);
		    		data[15]=new Double(lndPreSum);
		    		data[16]=new Double(abateAreaSum);
		    		data[17]=new Double(abateAllSum);
		    		data[18]="符合";
		    		rowData.addElement(data);
	    		}
    		}
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
