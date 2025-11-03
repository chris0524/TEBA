/*
*<br>程式目的：動產條碼資料批次新增
*<br>程式代號：sysmt001f
*<br>程式日期：0961023
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTMP046R extends QueryBean{
	
String q_enterOrg;
String q_enterOrgName;
String q_ownership;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }
String q_propertyNo;
String q_propertyNoName;
public String getQ_propertyNo(){ return checkGet(q_propertyNo); }
public void setQ_propertyNo(String s){ q_propertyNo=checkSet(s); }
public String getQ_propertyNoName(){ return checkGet(q_propertyNoName); }
public void setQ_propertyNoName(String s){ q_propertyNoName=checkSet(s); }

String q_chengDateS;
String q_chengDateE;
String q_endDate;
public String getQ_chengDateS(){ return checkGet(q_chengDateS); }
public void setQ_chengDateS(String s){ q_chengDateS=checkSet(s); }
public String getQ_chengDateE(){ return checkGet(q_chengDateE); }
public void setQ_chengDateE(String s){ q_chengDateE=checkSet(s); }
public String getQ_endDate(){ return checkGet(q_endDate); }
public void setQ_endDate(String s){ q_endDate=checkSet(s); }

String q_dataType;
public String getQ_dataType(){ return checkGet(q_dataType); }
public void setQ_dataType(String s){ q_dataType=checkSet(s); }

String q_chengPagType;
public String getQ_chengPagType(){ return checkGet(q_chengPagType); }
public void setQ_chengPagType(String s){ q_chengPagType=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    
    String whereSQL="";
    if(!"".equals(getQ_enterOrg())){
    	whereSQL += " and m2.enterorg = " + Common.sqlChar(getQ_enterOrg()) + "\n" ;
    }
    if(!"".equals(getQ_ownership())){
    	whereSQL += " and m2.ownership = " + Common.sqlChar(getQ_ownership()) + "\n" ;
    }
    if("3".equals(q_dataType)){
		whereSQL += " and m2.datastate='1' " + "\n";
    	whereSQL += " and a.enterdate <= " + Common.sqlChar(q_endDate) + "\n";
    }else if("1".equals(q_dataType)){
		whereSQL += " and m2.datastate='1' " + "\n";
    	whereSQL += " and a.enterdate >= " + Common.sqlChar(q_chengDateS) + "\n";
    	whereSQL += " and a.enterdate <= " + Common.sqlChar(q_chengDateE) + "\n";
    }else{
		whereSQL += " and m2.datastate='2' " + "\n";
    	whereSQL += " and exists( " + "\n"
    			 + " 		select h.serialno " + "\n"
    			 + " 		from UNTMP_ReduceProof g ,UNTMP_ReduceDetail h " + "\n"
    			 + " 		where 1=1 " + "\n"
    			 + " 		and g.enterorg=h.enterorg " + "\n"
    			 + " 		and g.ownership=h.ownership " + "\n"
    			 + " 		and g.caseno=h.caseno " + "\n"
    			 + " 		and g.verify='Y' " + "\n"
    			 + " 		and h.serialno=m2.serialno " + "\n"
    			 + " 		and h.propertyno=m1.propertyno " + "\n"
    			 + " 		and h.enterorg=m2.enterorg " + "\n"
    			 + " 		and g.writedate >= " + Common.sqlChar(q_chengDateS) + "\n"
    			 + " 		and g.writedate <= " + Common.sqlChar(q_chengDateE) + "\n"
    			 + " ) " ;
    }
    
    if("1".equals(q_chengPagType)){
    	whereSQL += " order by m1.computertype ,m1.propertyno ,m2.serialno " ;
    }else if("2".equals(q_chengPagType)){
    	whereSQL += " order by m2.keepunit ,m1.computertype ,m1.propertyno ,m2.serialno " ;
    }else{
    	whereSQL += " order by m1.computertype ,m2.keepunit  ,m1.propertyno ,m2.serialno " ;
    }
    
    try{
    	String[] columns = new String[] {"organaname","propertyname","propertyno","serialno","propertyname1","articleName","bookvalue"
    									,"sourceDate","limityear","useYear","place","computerTypeName","user","keeper","enterDate"
    									,"dataType","chengPagType","chengPagTypeName","ownershipName","printData"};
    	
    	String sql = " select m1.enterorg ,m1.propertyno ,m2.serialno ,o.organaname ,p.propertyname " + "\n"
    				  + " 		,m1.ownership ,decode(m1.ownership,'1','市有','2','國有') as ownershipName " + "\n"
    				  + " 		,m1.propertyname1 ,m1.articleName ,m1.specification ,m1.nameplate " + "\n"
    				  + " 		,m1.computertype ,nvl((select d.codename from SYSCA_Code d where d.codekindid='PCT' and d.codecon1=m1.propertyno and d.codeid=m1.computertype ),'未選擇') as computerTypeName " + "\n"
    				  + " 		,m2.bookvalue ,m1.sourceDate ,m1.buydate ,m2.place " + "\n"
    				  + " 		,m2.keepunit ,( select u.unitname from untmp_keepunit u where 1=1 and u.enterorg(+)=m2.enterorg and u.unitno(+)=m2.keepunit ) as keepunitName " + "\n"
    				  + " 		,m2.keeper ,( select r.keepername from untmp_keeper r where 1=1 and r.enterorg(+)=m2.enterorg and r.unitno(+)=m2.keepunit and r.keeperno(+)=m2.keeper) as keeperName " + "\n"
    				  + " 		,m2.useunit,( select u.unitname from untmp_keepunit u where 1=1 and u.enterorg(+)=m2.enterorg and u.unitno(+)=m2.useunit ) as useunitName " + "\n"
    				  + " 		,m2.userno ,( select r.keepername from untmp_keeper r where 1=1 and r.enterorg(+)=m2.enterorg and r.unitno(+)=m2.useunit and r.keeperno(+)=m2.userno) as unernoName " + "\n"
    				  + " 		,p.limityear " + "\n"
    				  + " from Untmp_Addproof a ,Untmp_Movable m1 ,untmp_movabledetail m2 ,syspk_propertycode p ,sysca_organ o --,untmp_keepunit u ,untmp_keeper r " + "\n" 
    				  + " where 1=1 " + "\n"
    				  
    				  + " and a.enterorg=m1.enterorg " + "\n"
    				  + " and a.caseno=m1.caseno " + "\n"
    				  + " and a.ownership=m1.ownership " + "\n"

    				  + " and m1.enterorg(+) = m2.enterorg " + "\n"
    				  + " and m1.propertyno(+) = m2.propertyno " + "\n"
    				  + " and m1.lotno(+) = m2.lotno " + "\n"

    				  + " and m1.propertyno = p.propertyno(+) " + "\n"
    				  + " and p.enterorg = '000000000A' "
    				  + " and m1.enterorg = o.organid " + "\n"
    				  + " and m1.propertyno= " +Common.sqlChar(q_propertyNo)

    				  + " and m1.verify = 'Y' " + "\n"

    				  + whereSQL ;
    	
    	//System.out.println(sql);
        ResultSet rs = db.querySQL(sql);
        Vector rowData = new Vector();

        while(rs.next()){
        	Object[] data = new Object[20];
        	data[0] = rs.getString("organaname");
        	data[1] = rs.getString("propertyname")+"(" +rs.getString("propertyno") + ")"+"明細清冊";
        	data[2] = rs.getString("propertyno");
        	data[3] = rs.getString("serialno");
        	data[4] = rs.getString("propertyname1");
        	data[5] = Common.get(rs.getString("specification"))+"/"+Common.get(rs.getString("nameplate"));
        	data[6] = rs.getString("bookvalue");
        	data[7] = Common.MaskDate(rs.getString("sourceDate"));
        	data[8] = rs.getString("limityear");
        	data[9] = useDate(rs.getString("buydate"));
        	data[10] = rs.getString("place");
        	data[11] = rs.getString("computerTypeName");
        	data[12] = rs.getString("useunitName") + "\n" + rs.getString("unernoName");;
        	data[13] = rs.getString("keepunitName") + "\n" + rs.getString("keeperName");
        	data[14] = "結存日期："+ Common.MaskDate(q_endDate);
        	if("1".equals(q_dataType)){
            	data[15] = "資料類別：增加數";
        	}else if("2".equals(q_dataType)){
            	data[15] = "資料類別：減少數";
        	}else{
            	data[15] = "資料類別：結存數";
        	}
        	if("1".equals(q_chengPagType)){
            	data[16] = rs.getString("computerType") ;//換頁模式
            	data[17] = "分頁模式：依細項分頁－"+rs.getString("computerTypeName")		;//換頁模式中文
        	}else if("2".equals(q_chengPagType)){
            	data[16] = rs.getString("keepunit")		;//換頁模式
            	data[17] = "分頁模式：依保管單位分頁 －" + rs.getString("keepunitName");		;//換頁模式中文
        	}else{
            	data[16] = rs.getString("computerType") + "_" + rs.getString("keepunit")		;//換頁模式
            	data[17] = "分頁模式：依細項及保管單位分頁－"+rs.getString("computerTypeName")+"／"+rs.getString("keepunitName")	;//換頁模式中文
        	}
        	data[18] = "權屬：" + rs.getString("ownershipName");
        	data[19] = "製表日期：" + Common.MaskDate(Datetime.getYYYMMDD()) ;//列表日期
        	
	
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
}//public DefaultTableModel getResultModel() throws Exception

//己使用年限
public String useDate(String useDateS){
	String reuseDateS="";
	int a=0;
	int b=0;
	if(!"".equals(useDateS) && useDateS != null){
	a=(Integer.parseInt(useDateS.substring(0,3))*12)+Integer.parseInt(useDateS.substring(3,5));
	b=(Integer.parseInt(Datetime.getYYYMMDD().substring(0,3))*12)+Integer.parseInt(Datetime.getYYYMMDD().substring(3,5));
	reuseDateS=String.valueOf((b-a)/12)+"年"+String.valueOf((b-a)%12)+"個月";
	//System.out.println(useDateS.substring(0,3)+"-"+useDateS.substring(3,5)+"-"+useDateS.substring(5,7));
	}else{
		reuseDateS="";
	}
	return reuseDateS;
}
}//public class UNTMP046R extends QueryBean{


