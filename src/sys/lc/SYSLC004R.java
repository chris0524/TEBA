/*
*<br>程式目的：土地產籍與地政地籍比對差異清冊
*<br>程式代號：syslc004r
*<br>撰寫日期：2014/09/30
*<br>程式作者：Mike Kao
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.lc;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import unt.ch.UNTCH_Tools;
import util.*;
import util.report.ReportEnvironment;

public class SYSLC004R extends SuperBean{

String fileName;
String q_tranYYYMM;
String q_causeName1;
String q_causeName2;
String q_causeName3;
private String q_reportType;						// 報表類型

public String getQ_reportType() { return checkGet(q_reportType); }
public void setQ_reportType(String q_reportType) { this.q_reportType = checkGet(q_reportType); }
public String getFileName() { return checkGet(fileName); }
public void setFileName(String s) { fileName = checkSet(s); }
public String getQ_tranYYYMM(){ return checkGet(q_tranYYYMM); }
public void setQ_tranYYYMM(String s){ q_tranYYYMM=checkSet(s); }
public String getQ_causeName1() {return checkGet(q_causeName1);}
public void setQ_causeName1(String qCauseName1) {q_causeName1 = checkSet(qCauseName1);}
public String getQ_causeName2() {return checkGet(q_causeName2);}
public void setQ_causeName2(String qCauseName2) {q_causeName2 = checkSet(qCauseName2);}
public String getQ_causeName3() {return checkGet(q_causeName3);}
public void setQ_causeName3(String qCauseName3) {q_causeName3 = checkSet(qCauseName3);}

String signNo1;
String signNo2;
String signNo3;
String signNo4;
String ownerDept;
String ownerDeptName;
String syslc004_userdept;
String organID;    

public String getOrganID() { return checkGet(organID); }
public void setOrganID(String s) { organID = checkSet(s); }
public String getSignNo1() {return checkGet(signNo1);}
public void setSignNo1(String signNo1) {this.signNo1 = checkSet(signNo1);}
public String getSignNo2() {return checkGet(signNo2);}
public void setSignNo2(String signNo2) {this.signNo2 = checkSet(signNo2);}
public String getSignNo3() {return checkGet(signNo3);}
public void setSignNo3(String signNo3) {this.signNo3 = checkSet(signNo3);}
public String getSignNo4() {return checkGet(signNo4);}
public void setSignNo4(String signNo4) {this.signNo4 = checkSet(signNo4);}
public String getOwnerDept() {return checkGet(ownerDept);}
public void setOwnerDept(String ownerDept) {this.ownerDept = checkSet(ownerDept);}
public String getOwnerDeptName() {return checkGet(ownerDeptName);}
public void setOwnerDeptName(String ownerDeptName) {this.ownerDeptName = checkSet(ownerDeptName);}
public String getSyslc004_userdept() {return checkGet(syslc004_userdept);}
public void setSyslc004_userdept(String syslc004Userdept) {syslc004_userdept = checkSet(syslc004Userdept);}

public DefaultTableModel getResultModel() throws Exception {
	
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    UNTCH_Tools ut = new UNTCH_Tools();
    Database db = new Database();
    String datetime = Datetime.getYYYMMDD();
    try{
    	String[] columns = new String[]{"PRINTUSER","PRINTDATE","TRANYYYMM","CHANGEPAGE","CAUSE","COUNT","CAUSENAME","SIGNNAME","OWNERSHIPNAME","PROPERTYCODE","PROOF"};
    	String var1 = new Datetime().changeTaiwanYYMM(getQ_tranYYYMM(), "2");   	
    	String var2 = !"".equals(getQ_causeName1())?"1":"0";
    	String var3 = !"".equals(getQ_causeName2())?"1":"0";
    	String var4 = !"".equals(getQ_causeName3())?"1":"0";
    	String var=var2+var3+var4;

//    	String execSQL= Common.callSQLFileRead(SQLFile, 2, "&"+var1+":;:"+"&"+var);

    	// a:地政資料未存於土地主檔
    	String execSQL="( select '地政資料未存於土地主檔' as causename, '01' as cause, " +
    				   " (select c.signdesc from SYSCA_SIGN c where c.signno = a.lsignno1+a.lsignno2+a.lsignno3 ) +substring (a.lsignno4, 1, 4)+'-'+substring (a.lsignno4, 5, 8)+'地號' signname, " +
    				   " a.ownershipname, c.propertyno+c.serialno as propertycode, '' as proof " +
    				   " from SYSLC_LAND a, UNTLA_LAND c  "+
	    	           " where substring('"+var+"',1,1) = '1' "+
	    	           " and a.tranYYYMM = '"+var1+"' "+
	    	           " and c.datastate = '1' "+
	    	           " and a.lsignno1+a.lsignno2+a.lsignno3+a.lsignno4=c.signno " +
	    	           " and (a.lsignno2+a.lsignno3+a.lsignno4) not in ( select substring (b.signno, 2, 2)+ substring (b.signno, 4, 4)+ substring (b.signno, 8, 8) from UNTLA_LAND b where b.signno = a.lsignno1+a.lsignno2+a.lsignno3+a.lsignno4 ) ";
    	          if(Common.get(getSignNo1()).equals("")){ }else{
    	        	  execSQL+=" and a.lsignno1 = "+Common.sqlChar(getSignNo1().substring(0,1));
    	          }
    	          if(Common.get(getSignNo2()).equals("")){ }else{
    	        	  execSQL+=" and a.lsignno2 = "+Common.sqlChar(getSignNo2().substring(1,3));
    	          }
    	          if(Common.get(getSignNo3()).equals("")){ }else{
    	        	  execSQL+=" and a.lsignno3 = "+Common.sqlChar(getSignNo3().substring(3,7));
    	          }
    	          if(Common.get(getSignNo4()).equals("")){ }else{
    	        	  execSQL+=" and a.lsignno4 = "+Common.sqlChar(getSignNo4());
    	          }
    	          if("1".equals(this.getSyslc004_userdept())){
	    	          if(Common.get(getOwnerDept()).equals("")){ }else{
	    	        	  execSQL+=" and c.ownerdept = "+Common.sqlChar(getOwnerDept());
	    	          }
    	          }
    	          
    	          execSQL+=" ) "+
    	    " union "+
    	// b:土地主檔未存於地政資料
    	    	" (select '土地主檔未存於地政資料' as causename, '02' as cause, " +
    	    	" (select c.signdesc from SYSCA_SIGN c where c.signno = substring (a.signno, 1, 7) ) +substring (a.signno, 8, 4)+'-'+substring (a.signno, 12, 3)+'地號' signname, " +
    	    	" '' as ownershipname, a.propertyno+a.serialno as propertycode, '' as proof " + 
    	    	" from UNTLA_LAND a  "+
    			  " where substring('"+var+"',2,1) = '1' "+
    			  " and a.datastate = '1' " +
    			  " and a.signno not in ( select b.lsignno1+b.lsignno2+b.lsignno3+b.lsignno4 " +
    			  " from SYSLC_LAND b "+
          	      " where b.tranYYYMM = '"+var1+"' "+
                  " and b.lsignNo1 = substring(a.signNo,1,1) "+
                  " and b.lsignNo2 = substring(a.signNo,2,2) "+
                  " and b.lsignNo3 = substring(a.signNo,4,4) "+
                  " and b.lsignNo4 = substring(a.signNo,8,8) "+
                  " ) ";
    	          if(Common.get(getSignNo1()).equals("")){ }else{
    	        	  execSQL+=" and substring(a.signNo,1,1) = "+Common.sqlChar(getSignNo1().substring(0,1));
    	          }
    	          if(Common.get(getSignNo2()).equals("")){ }else{
    	        	  execSQL+=" and substring(a.signNo,2,2) = "+Common.sqlChar(getSignNo2().substring(1,3));
    	          }
    	          if(Common.get(getSignNo3()).equals("")){ }else{
    	        	  execSQL+=" and substring(a.signNo,4,4) = "+Common.sqlChar(getSignNo3().substring(3,7));
    	          }
    	          if(Common.get(getSignNo4()).equals("")){ }else{
    	        	  execSQL+=" and substring(a.signNo,8,8) = "+Common.sqlChar(getSignNo4());
    	          }
    	          if("1".equals(this.getSyslc004_userdept())){
	    	          if(Common.get(getOwnerDept()).equals("")){ }else{
	    	        	  execSQL+=" and a.ownerdept = "+Common.sqlChar(getOwnerDept());
	    	          }
    	          }
    	          
    	          execSQL+=" ) "+
    		" union "+
    	// c:內容異動
    		        "  (select " +
			    	" case when isnull (area1, 1) != isnull (area2, 1) then '面積差異' end +';'+ " +
			 	    " case when holdrate1 != holdrate2 then '權利範圍差異' end +';'+ " +
			     	" case when e.valueunit != f.valueunit then '公告現值差異' end +';'+ " +
				    " case when e.priceunit != g.priceunit then '申報地價差異' end as causename, " +
				    " '03' as cause, " +
				    "( select c.signname from SYSCA_SIGN c where c.signno = lsignno1+'000000' ) + ( select c.signname from SYSCA_SIGN c where c.signno = lsignno1+lsignno2+'0000' ) + ( select c.signname from SYSCA_SIGN c where c.signno = lsignno1+lsignno2+lsignno3 ) +substring (lsignno4, 1, 4)+'-'+substring (lsignno4, 5, 4)+'地號' signname," + 
				    " ownershipname, e.propertyno+e.serialno as propertycode, proof " +
				    " from " +
				    " ( select a.ownershipname, b.propertyno, b.serialno, '' proof, a.lsignno1, a.lsignno2, a.lsignno3, a.lsignno4, a.area as area1, b.area as area2, isnull (a.holdnume, 1)/isnull (a.holddeno, 1) as holdrate1, isnull (b.holdnume, 1)/isnull (b.holddeno, 1) as holdrate2, " +
				    " a.valuedate, a.valueunit, a.priceunit " +
				    " from SYSLC_LAND a, UNTLA_LAND b  "+
				    " where substring('"+var+"',3,1) = '1' "+
				    " and a.tranYYYMM = '"+var1+"' "+
				    " and b.datastate = '1' "+
				    " and a.lsignNo1 = substring(b.signNo,1,1)  and a.lsignNo2 = substring(b.signNo,2,2)  and a.lsignNo3 = substring(b.signNo,4,4)  and a.lsignNo4 = substring(b.signNo,8,8)   "+
				    " and (  isnull(a.area       , 1 ) != isnull(b.area       , 1 ) or  isnull(a.holdNume,1)/isnull(a.holdDeno,1) != isnull(b.holdNume,1)/isnull(b.holdDeno,1) "+
				    " )   "+
				    " and a.lsignno1 = 'A' and a.lsignno2 = '01'  ";

    	          	if("1".equals(this.getSyslc004_userdept())){
	    	          	if(Common.get(getOwnerDept()).equals("")){ }else{
	    	          		execSQL+=" and b.ownerdept = "+Common.sqlChar(getOwnerDept());
	    	          	}
    	          	}
				    
	    	        execSQL+=" ) e, UNTLA_VALUE f, UNTLA_PRICE g  " +
						    " where 1=1 " +
							" and e.propertyno=f.propertyno " +  
							" and e.serialno=f.serialno " +  
							" and e.valuedate=f.bulletindate " + 
							" and e.propertyno=g.propertyno " +  
							" and e.serialno=g.serialno ";
				    
    	          if(Common.get(getSignNo1()).equals("")){ }else{
    	        	  execSQL+=" and lsignno1 = "+Common.sqlChar(getSignNo1().substring(0,1));
    	          }
    	          if(Common.get(getSignNo2()).equals("")){ }else{
    	        	  execSQL+=" and lsignno2 = "+Common.sqlChar(getSignNo2().substring(1,3));
    	          }
    	          if(Common.get(getSignNo3()).equals("")){ }else{
    	        	  execSQL+=" and lsignno3 = "+Common.sqlChar(getSignNo3().substring(3,7));
    	          }
    	          if(Common.get(getSignNo4()).equals("")){ }else{
    	        	  execSQL+=" and lsignno4 = "+Common.sqlChar(getSignNo4());
    	          }

    	          execSQL+=" ) "+
    		" order by causeName ";

    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	int causeCount1 = 1;
    	int causeCount2 = 1;
    	int causeCount3 = 1;
    	
    	while(rs.next()) {
    		int tempCount = 1;
    		Object[] data = new Object[columns.length];
    		//印表人
    		data[0] = ut._queryData("empname")._from("SYSAP_EMP a ")._with("and organid = '" + getOrganID() + "' and empid = '" + getEditID() + "'")._toString();
    		//印表日期
    		data[1] = datetime.substring(0,3) + "年" + datetime.substring(3,5) + "月" + datetime.substring(5)+ "日";
    		data[2]=getQ_tranYYYMM(); 
    		data[3]=Common.get(rs.getString("causename"));
    		data[4]=Common.get(rs.getString("cause"));
    		if("01".equals(rs.getString("cause"))){
    			tempCount = causeCount1++;
    		}else if("02".equals(rs.getString("cause"))){
    			tempCount = causeCount2++;
    		}else{
    			tempCount = causeCount3++;
    		} 
    		data[5]=String.valueOf(tempCount);
    		data[6]=Common.get(rs.getString("causename"));
    		data[7]=Common.get(rs.getString("signname"));
    		data[8]=Common.get(rs.getString("ownershipname"));
    		data[9]=Common.get(rs.getString("propertycode"));
    		data[10]=Common.get(rs.getString("proof"));
    		    				
    		rowData.addElement(data);
    	}
    	
    	if(rowData.size() == 0){
    		Object[] data = new Object[columns.length];
    		data[0]="";
    		rowData.toArray(data);
    	}
    	
    	Object[][] data = new Object[0][0];
    	data = (Object[][])rowData.toArray(data);
    	model.setDataVector(data, columns);
    	rs.close();
    	
    }catch(Exception x){
    	x.printStackTrace();
    }finally{
    	db.closeAll();
    }
    return model;
}

}