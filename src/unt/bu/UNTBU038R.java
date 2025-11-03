/*
*<br>程式目的：動產條碼資料批次新增
*<br>程式代號：sysmt001f
*<br>程式日期：0961023
*<br>程式作者：shan
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.bu;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import util.*;

public class UNTBU038R extends SuperBean{
//變數區
String q_enterOrg;
String q_enterOrgName;
public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }

String q_ownership;
public String getQ_ownership(){ return checkGet(q_ownership); }
public void setQ_ownership(String s){ q_ownership=checkSet(s); }

String q_caseNoS;
String q_caseNoE;
public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }

String q_writeDateS;
String q_writeDateE;
public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }

String q_proofDoc;
String q_proofNoS;
String q_proofNoE;
public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }

String q_kind;
public String getQ_kind(){ return checkGet(q_kind); }
public void setQ_kind(String s){ q_kind=checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
	String xlsSql = "" ,xlsWhere="";
	if(!"".equals(getQ_enterOrg()))
	{	xlsWhere += " and a.enterorg = " + Common.sqlChar(getQ_enterOrg());		}
	if(!"".equals(getQ_ownership()))
	{	xlsWhere += " and a.ownership = " + Common.sqlChar(getQ_ownership()); 	}
	if(!"".equals(getQ_caseNoS()))
	{	xlsWhere += " and a.caseno >= " + Common.sqlChar(getQ_caseNoS());	}
	if(!"".equals(getQ_caseNoE()))
	{	xlsWhere += " and a.caseno <= " + Common.sqlChar(getQ_caseNoE());	}
	if(!"".equals(getQ_ownership()))
	{	xlsWhere += " and a.ownership = " + Common.sqlChar(getQ_ownership());	}
	if(!"".equals(getQ_writeDateS()))
	{	xlsWhere += " a.writedate >= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));	}
	if(!"".equals(getQ_writeDateE()))
	{	xlsWhere += " a.writedate <= " + Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));	}
	if(!"".equals(getQ_proofDoc()))
	{	xlsWhere += " a.proofDoc like like " + Common.sqlChar(getQ_proofDoc());	}
	if(!"".equals(getQ_proofNoS()))
	{	xlsWhere += " a.proofNo >= " + Common.sqlChar(getQ_proofNoS());	}
	if(!"".equals(getQ_proofNoS()))
	{	xlsWhere += " a.proofNo <= " + Common.sqlChar(getQ_proofNoE());	}
	
	xlsSql = " select b.enterorg ,c.organsname " + "\n"
		   + " 		  ,b.ownership ,(case b.ownership when '1' then '市有' when '2' then '國有' when '3' then 'KOC' end) as ownershipName " + "\n"
		   + " 		  ,b.caseno ,a.writedate " + "\n"
		   + " 		  ,b.propertyno ,b.propertyno ,b.serialno " + "\n"
		   + " 		  ,d.propertyname ,d.propertyunit ,d.limityear " + "\n"
		   + " 		  ,b.oldbookvalue ,b.oldholdarea " + "\n"
		   + " 		  ,b.adjustbookvalue ,b.adjustholdarea " + "\n"
		   + " 		  ,b.sourcedate " + "\n"
		   + " 		  ,(case b.cause when '1' then '資產重估調整' when '2' then '整建' when '3' then '其它' end) as cause ,b.cause1 " + "\n"
		   + " from UNTBU_AdjustProof a left join sysca_organ c on b.enterorg=c.organid ,UNTBU_AdjustDetail b left join syspk_propertyCode d on b.propertyno=d.propertyno and (b.enterorg=d.enterorg or d.enterorg='000000000A') " + "\n"
		   + " where 1=1 " + "\n"
		   + " and a.enterorg=b.enterorg   and a.ownership=b.ownership " + "\n"
		   + " and a.caseno=b.caseno " + "\n"
		   + " and b.adjusttype='2' " + "\n"
		   + xlsWhere
		   + " 		  order by enterorg ,ownership ,caseno ,propertyno ,serialno " + "\n";
	
	//System.out.println(xlsSql);
	int i=0,j=0;
	if("0".equals(getQ_kind())){
		i=1;j=3;
	}else{
		i=Integer.parseInt(getQ_kind());
		j=i;
	}
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"enterOrgName","ownership","caseno","pagno","writeDate","propertyno",
    									 "proptertynoName","propertyUnit","buydate","limitYear","useYear",
    									 "cause","cause1","oldBookValue","oldMeasure","adjustBookValue","adjustMeasure",
    									 "oldBookValue00","adjustMeasure00"};
    	
        Vector rowData = new Vector();
        for(int kinds=i; kinds<=j ;kinds++){
        ResultSet rs = db.querySQL(xlsSql);        
        while(rs.next()){
        	Object[] data = new Object[columns.length];
        	data[0]=rs.getString("organsname");
        	data[1]="權　　屬："+rs.getString("ownershipName");
        	data[2]="電腦單號："+rs.getString("caseno");
        	data[3]="第"+numTOchi(kinds)+"聯";
        	data[4]="填單日期："+Common.MaskCDate(rs.getString("writedate"));
        	data[5]=rs.getString("propertyno").substring(0,7)+"-"+rs.getString("propertyno").substring(7)+"\n-"+rs.getString("serialno");
        	data[6]=rs.getString("propertyname");
        	data[7]=rs.getString("propertyunit");
        	data[8]=Common.MaskDate(rs.getString("sourceDate"));
        	data[9]=rs.getString("limityear");
        	data[10]=useDate(rs.getString("sourceDate"),rs.getString("writedate"));
        	data[11]=rs.getString("cause");
        	data[12]=rs.getString("cause1");
        	data[13]=new Double(rs.getString("oldbookvalue"));
        	data[14]=new Double(rs.getString("oldholdarea"));
        	data[15]=new Double(rs.getString("adjustbookvalue"));
        	data[16]=new Double(rs.getString("adjustholdarea"));
        	data[17]=Common.areaFormat(rs.getString("oldholdarea"));
        	data[18]=Common.areaFormat(rs.getString("adjustholdarea"));
        	rowData.addElement(data);
        }
        }//for>kinds
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

public String numTOchi(int a){
	String reValue="";
	if(a == 1){
		reValue="一";
	}else if(a == 2){
		reValue="二";
	}else{
		reValue="三";
	}
	return reValue;
}

public String useDate(String useDateS ,String useDateE){
	String reuseDateS="";
	int a=0;
	int b=0;
	if(!"".equals(useDateS) && useDateS != null && !"".equals(useDateE) && useDateE != null){
	a=(Integer.parseInt(useDateS.substring(0,3))*12)+Integer.parseInt(useDateS.substring(3,5));
	//b=(Integer.parseInt(Datetime.getYYYMMDD().substring(0,3))*12)+Integer.parseInt(Datetime.getYYYMMDD().substring(3,5));
	b=(Integer.parseInt(useDateE.substring(0,3))*12)+Integer.parseInt(useDateE.substring(3,5));
	reuseDateS=String.valueOf((b-a)/12)+"年"+String.valueOf((b-a)%12)+"個月";
	}else{
		reuseDateS="";
	}
	return reuseDateS;
}

}

