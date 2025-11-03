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

public class UNTPD009R extends SuperBean{
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
    String execSQL = " select o.organaname " + "\n" ;
    if("2".equals(getQ_chengPageStyle())){
    	   execSQL += " ,'保管單位：'  as chengPageStyle " + "\n";
    }else{
		   execSQL += " ,'保管單位：' || u.unitname as chengPageStyle " + "\n"
   				    + " ,u.unitname " + "\n";
    }    
    		execSQL	+= " ,'財產類別：' || decode(substr(p.propertyno,0,1),'3','第三類　機械及設備','4','第四類　交通及運輸設備','5','第五類　什項設備') as chengPageStyle1 " + "\n"
    			   + " ,'財產性質：' || decode(p.propertykind,'01','公務用','02','公共用','03','事業用','04','非公用') as propertykindName " + "\n"
    			   + " ,p.fundtype "
    			   + " ,p.propertyno ,substr(p.propertyno,0,1) as p1 ,substr(p.propertyno,2,2) as p2 ,substr(p.propertyno,4,2) as p3 " + "\n"
    			   + " ,substr(p.propertyno,6,2) as p4 ,substr(p.propertyno,8,2) as p5 " + "\n"
    			   + " ,k.propertyname " + "\n"
    			   + " ,k.propertyunit " + "\n"
    			   + " ,min(p.closingdate) as closingdate " + "\n"
    			   + " ,sum(p.bookamount) as bookAmount       --帳面數量  " + "\n"
    			   + " ,sum(nvl(p.actualamount,0)) as s_actualamount --盤存數量  " + "\n"
    			   + " ,sum(p.bookvalue) as bookvaluue                                        --帳面價傎  " + "\n"
    			   + " ,sum(decode(sign(nvl(p.actualamount,0)-p.bookamount),0,p.bookvalue,1,p.bookvalue,-1,0)) as s_bookvalue --盤存價值 " + "\n"
    			   + " ,sum(decode(sign(p.bookamount-nvl(p.actualamount,0)),-1,p.actualamount,0,0,1,0)) as pdAdd  --盤盈數量 " + "\n"
    			   + " ,sum(decode(sign(p.bookamount-nvl(p.actualamount,0)),-1,0,0,0,1,p.bookamount)) as pdLost   --盤損數量 " + "\n"
    			   + " ,sum(decode(sign(p.bookamount-nvl(p.actualamount,0)),-1,p.bookvalue,0,0,1,0)) as pdAdd_V     --盤盈總值 " + "\n"
    			   + " ,sum(decode(sign(p.bookamount-nvl(p.actualamount,0)),-1,0,0,0,1,p.bookvalue)) as pdLost_V    --盤損總值 " + "\n"
    			   + " from untpd_checkmovable p ,sysca_organ o ,syspk_propertycode k ,untmp_keepunit u " + "\n"
    			   + " where 1=1 " + "\n"
    			   + " and p.enterorg=o.organid " + "\n"
    			   + " and p.propertyno=k.propertyno and (p.enterorg=k.enterorg or k.enterorg='000000000A')" + "\n"
    			   + " and p.enterorg=u.enterorg and p.useunit=u.unitno " + "\n"
    			   + whereSQL
    			   //+ " group by o.organaname ,p.propertyno ,p.propertykind ,k.propertyname ,k.propertyunit ,u.unitname ,p.fundtype " + "\n"
    			   + " ";
	if("2".equals(getQ_chengPageStyle())){
  	   execSQL += " group by o.organaname ,p.propertyno ,p.propertykind ,k.propertyname ,k.propertyunit ,p.fundtype " + "\n"
  	   		   +  " order by p.propertyno  " + "\n" ;
 	}else{
 	   execSQL += " group by o.organaname ,p.propertyno ,p.propertykind ,k.propertyname ,k.propertyunit ,u.unitname ,p.fundtype " + "\n"
 		   	   +  " order by u.unitname ,p.propertyno " + "\n" ;
 	}
    //System.out.println(execSQL);
    try{
    String[] columns = new String[] {"organaName","chengPageStyle","chpropertykind"
    								,"p1","p2","p3","p4","p5","propertyname","propertyunit"
    								,"s_actualamount","s_bookvalue","calculation","closingdate"
    								,"printDate","bookAmount"};

	ResultSet rs = db.querySQL(execSQL);
	Vector rowData = new Vector();
	while(rs.next()){
    	Object[] data = new Object[16];
    	
    	data[0]=rs.getString("organaname");
    	if(!"2".equals(getQ_chengPageStyle())){
        	data[1]=rs.getString("chengPageStyle");
    	}else{
        	data[1]=rs.getString("chengPageStyle1");
    	}
    	data[2]=rs.getString("propertykindName");
    	data[3]=rs.getString("p1");
    	data[4]=rs.getString("p2");
    	data[5]=rs.getString("p3");
    	data[6]=rs.getString("p4");
    	data[7]=rs.getString("p5");
    	data[8]=rs.getString("propertyname");
    	data[9]=rs.getString("propertyunit");
    	data[10]=rs.getString("s_actualamount");
    	data[11]=new Double(rs.getString("s_bookvalue"));
    	if("0".equals(rs.getString("pdAdd")) && "0".equals(rs.getString("pdLost"))){
    		data[12]="　盤點正常";
    	}else{
    		data[12]= "　盤盈：" + Common.get(rs.getString("pdAdd")) + "／" + addComMa(Common.get(rs.getString("pdAdd_V"))) + "\n" 
    				+ "　盤損：" + Common.get(rs.getString("pdLost")) + "／" + addComMa(Common.get(rs.getString("pdLost_V")));
    	}
    	data[13]="至中華民國"+Common.MaskCDate(rs.getString("closingdate"))+"止";
    	data[14]="製表日期："+Common.MaskCDate(Datetime.getYYYMMDD());;
    	data[15]=new Double(rs.getString("bookAmount"));
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
