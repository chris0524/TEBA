/*
*<br>程式目的：動產盤點名細表
*<br>程式代號：untpd003r
*<br>程式日期：2007年11月
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.pd;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import util.*;

public class UNTPD020R extends SuperBean{
String q_enterOrg;
String q_enterOrgName;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

String q_propertyKind;
public String getQ_propertyKind(){ return checkGet(q_propertyKind); }
public void setQ_propertyKind(String s){ q_propertyKind=checkSet(s); }

String q_checkResult;
public String getQ_checkResult(){ return checkGet(q_checkResult); }
public void setQ_checkResult(String s){ q_checkResult=checkSet(s); }

String q_chengPageStyle;
public String getQ_chengPageStyle(){ return checkGet(q_chengPageStyle); }
public void setQ_chengPageStyle(String s){ q_chengPageStyle=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String whereSQL = "";
    if(!"".equals(getQ_enterOrg())){
    	whereSQL += " and p.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n";
    }    
    if(!"".equals(getQ_propertyKind())){
    	if ("00".equals(getQ_propertyKind())){
    		whereSQL += " and p.propertykind in ('01','02','03')" + "\n";
    	}else{
    		whereSQL += " and p.propertykind = " + Common.sqlChar(getQ_propertyKind()) + "\n";
    	}
    }
    if(!"".equals(getQ_checkResult())){
    	whereSQL += " and p.checkresult = " + Common.sqlChar(getQ_checkResult()) + "\n";
    }
    String execSQL = " select o.organaName " + "\n"
    			   + " ,'物品性質分類：' || decode(p.propertykind,'01','公務用','02','公共用','03','事業用','04','非公用') as propertykindName " + "\n"
    			   + " ,p.fundtype " + "\n";
	if ("2".equals(getQ_chengPageStyle())){	
		  execSQL += " ,'保管單位：' as chengPageStyle " + "\n"
    			   + " ,'物品分類：第六類：物品' as chengPageStyle1 " + "\n";
	}else{
		  execSQL += " ,'保管單位：' || u.unitname as chengPageStyle " + "\n"
		   		  + " ,'物品分類：第六類：物品' as chengPageStyle1 " + "\n";;
	}
		  execSQL += " ,min(p.closingdate) as closingdate " + "\n"
    			   + " ,p.propertyno ,k.propertyname ,k.propertyunit " + "\n"
    			   + " ,sum(p.bookamount) as bookamount " + "\n"
    			   + " ,nvl(sum(decode(sign(p.actualamount-p.bookamount),1,p.actualamount,0,p.actualamount,-1,0)),0) as s_amount --盤存數量 " + "\n"
    			   + " ,nvl(sum(decode(sign(p.actualamount-p.bookamount),1,p.bookvalue,0,p.bookvalue,-1,0)),0) as s_value        --盤存價值 " + "\n"
    			   + " ,sum(decode(sign(p.actualamount-p.bookamount),1,p.actualamount,0,0,-1,0)) as pdAdd                 --盤盈數量 " + "\n"
    			   + " ,sum(decode(sign(p.actualamount-p.bookamount),1,p.bookvalue,0,0,-1,0)) as pdAdd_v                  --盤損價值 " + "\n"
    			   + " ,sum(decode(sign(p.actualamount-p.bookamount),1,0,0,0,-1,p.bookamount)) as pdLost                  --盤損數量 " + "\n"
    			   + " ,sum(decode(sign(p.actualamount-p.bookamount),1,0,0,0,-1,p.bookvalue)) as pdLost_v                 --盤損價值 " + "\n"
    			   + " from untpd_checknonexp p ,sysca_organ o ,syspk_propertycode2 k ,untmp_keepunit u " + "\n"
    			   + " where 1=1 " + "\n"
    			   + " and p.enterorg=o.organid " + "\n"
    			   + " and p.enterorg=k.enterorg and p.propertyno=k.propertyno " + "\n"
    			   + " and p.enterorg=u.enterorg and p.useunit=u.unitno " + "\n"
    			   + whereSQL
    			   //+ " group by  o.organaname ,p.propertykind ,p.fundtype ,u.unitname ,p.propertyno ,k.propertyname ,k.propertyunit " + "\n"
    			   //+ " order by p.propertyno " + "\n"
    			   + " ";
	if ("2".equals(getQ_chengPageStyle())){					//分頁
		execSQL += " group by  o.organaname ,p.propertykind ,p.fundtype ,p.propertyno ,k.propertyname ,k.propertyunit " + "\n"
			   	 + " order by p.propertyno " ;
	}else{
		execSQL += " group by  o.organaname ,p.propertykind ,p.fundtype ,u.unitname ,p.propertyno ,k.propertyname ,k.propertyunit " + "\n"
		   		 + " order by u.unitname ,p.propertyno " ;
	}
	
    //System.out.println("盤存報告表:"+"\n"+execSQL);
    try{
    String[] columns = new String[] {"organaName","chpropertykind","chengPageStyle","closingdate"
    								,"printDate","propertyno","propertyname","propertyunit"
    								,"s_amount","s_value","calculation","bookamount"};

	ResultSet rs = db.querySQL(execSQL);
	Vector rowData = new Vector();
	while(rs.next()){
    	Object[] data = new Object[12];
    	
    	data[0]=rs.getString("organaname");
    	data[1]=rs.getString("propertykindName");
    	if("2".equals(getQ_chengPageStyle())){
        	data[2]=rs.getString("chengPageStyle1");
    	}else{
        	data[2]=rs.getString("chengPageStyle");
    	}

    	data[3]="至中華民國"+Common.MaskCDate(rs.getString("closingdate"))+"止";
    	data[4]="製表日期："+Common.MaskCDate(Datetime.getYYYMMDD());;
    	data[5]=rs.getString("propertyno");
    	data[6]=rs.getString("propertyname");
    	data[7]=rs.getString("propertyunit");
    	data[8]=new Double(rs.getString("s_amount"));
    	data[9]=new Double(rs.getString("s_value"));
    	if("0".equals(rs.getString("pdAdd")) && "0".equals(rs.getString("pdLost"))){
    		data[10]="　盤點正常";
    	}else{
    		data[10]= "　盤盈：" + addComMa(Common.get(rs.getString("pdAdd"))) + "／" + addComMa(Common.get(rs.getString("pdAdd_v"))) + "\n" 
    				+ "　盤損：" + addComMa(Common.get(rs.getString("pdLost"))) + "／" + addComMa(Common.get(rs.getString("pdLost_v")));
    	}
    	data[11]=new Double(rs.getString("bookamount"));
    	
        rowData.addElement(data);
	}//while
    Object[][] data1 = new Object[0][0];
    data1 = (Object[][])rowData.toArray(data1);
    model.setDataVector(data1, columns);
    }catch(Exception x){
    	x.printStackTrace();
	}finally{
		db.closeAll();
	}
		return model;
}//DefaultTableModel getResultModel() throws Exception{
//加入三位一撇
public String addComMa(String s){
	String res="";
	if (s.length() >= 4) {
		res = addComMa(s.substring(0,s.length()-3)) + "," + s.substring(s.length()-3,s.length());
	}else{
		res=s;
	}
	return res;
}//public String addComMa(String s){
}//public class UNTPD009R extends QueryBean
