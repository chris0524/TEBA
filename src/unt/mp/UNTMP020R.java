/*
*<br>程式目的：動產減損單報表檔 
*<br>程式代號：untmp020r
*<br>撰寫日期：94/11/23
*<br>程式作者：Chris
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
//====
import java.util.Vector;

import java.io.*;
import util.*;

//==
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;

import util.*;

public class UNTMP020R extends SuperBean{
	String enterOrg;
	String ownership;
	String caseNoS;
	String caseNoE;
	String writeDateS;
	String writeDateE;
	String proofDoc;
	String proofNoS;
	String proofNoE;
	String q_enterOrg;
	String q_enterOrgName;
	String q_ownership;
	String q_caseNoS;
	String q_caseNoE;
	String q_writeDateS;
	String q_writeDateE;
	String q_proofDoc;
	String q_proofNoS;
	String q_proofNoE;
	String q_kind;
	
	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getCaseNoS(){ return checkGet(caseNoS); }
	public void setCaseNoS(String s){ caseNoS=checkSet(s); }
	public String getCaseNoE(){ return checkGet(caseNoE); }
	public void setCaseNoE(String s){ caseNoE=checkSet(s); }
	public String getWriteDateS(){ return checkGet(writeDateS); }
	public void setWriteDateS(String s){ writeDateS=checkSet(s); }
	public String getWriteDateE(){ return checkGet(writeDateE); }
	public void setWriteDateE(String s){ writeDateE=checkSet(s); }
	public String getProofDoc(){ return checkGet(proofDoc); }
	public void setProofDoc(String s){ proofDoc=checkSet(s); }
	public String getProofNoS(){ return checkGet(proofNoS); }
	public void setProofNoS(String s){ proofNoS=checkSet(s); }
	public String getProofNoE(){ return checkGet(proofNoE); }
	public void setProofNoE(String s){ proofNoE=checkSet(s); }
	public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
	public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
	public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
	public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }
	public String getQ_ownership(){ return checkGet(q_ownership); }
	public void setQ_ownership(String s){ q_ownership=checkSet(s); }
	public String getQ_caseNoS(){ return checkGet(q_caseNoS); }
	public void setQ_caseNoS(String s){ q_caseNoS=checkSet(s); }
	public String getQ_caseNoE(){ return checkGet(q_caseNoE); }
	public void setQ_caseNoE(String s){ q_caseNoE=checkSet(s); }
	public String getQ_writeDateS(){ return checkGet(q_writeDateS); }
	public void setQ_writeDateS(String s){ q_writeDateS=checkSet(s); }
	public String getQ_writeDateE(){ return checkGet(q_writeDateE); }
	public void setQ_writeDateE(String s){ q_writeDateE=checkSet(s); }
	public String getQ_proofDoc(){ return checkGet(q_proofDoc); }
	public void setQ_proofDoc(String s){ q_proofDoc=checkSet(s); }
	public String getQ_proofNoS(){ return checkGet(q_proofNoS); }
	public void setQ_proofNoS(String s){ q_proofNoS=checkSet(s); }
	public String getQ_proofNoE(){ return checkGet(q_proofNoE); }
	public void setQ_proofNoE(String s){ q_proofNoE=checkSet(s); }
	public String getQ_kind(){ return checkGet(q_kind); }
	public void setQ_kind(String s){ q_kind=checkSet(s); }
	String isOrganManager;
    String isAdminManager;
    String organID;    
    public String getOrganID() { return checkGet(organID); }
    public void setOrganID(String s) { organID = checkSet(s); }
    public String getIsOrganManager() { return checkGet(isOrganManager); }
    public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
    public String getIsAdminManager() { return checkGet(isAdminManager); }
    public void setIsAdminManager(String s) { isAdminManager = checkSet(s); } 
    
	String q_detail;
	public String getQ_detail(){ return checkGet(q_detail); }
	public void setQ_detail(String s){ q_detail=checkSet(s); }


public DefaultTableModel getResultModel() throws Exception{
    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String xlsSql = " select distinct c.organAName as enterOrgName, " +
						" a.enterorg ,a.ownership ,a.caseno," +
						" (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
						" a.writeDate," +
						" a.writeUnit," +
						" a.proofDoc||'字第'||a.proofNo||'號' as proofNo," +
						" a.caseNO," +
						" a.summonsNo, " +
						" b.propertyKind," +
						" decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKindName "+
						" from UNTMP_ReduceProof a,UNTMP_ReduceDetail b,SYSCA_Organ c "+
						" where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership " +
						" and a.enterOrg=c.organID(+) ";
    	
    	if (!"".equals(Common.get(getQ_ownership()))){
    		xlsSql += " and a.ownership = " + util.Common.sqlChar(getQ_ownership());
    	}
    	if (!"".equals(getQ_enterOrg())) {
    		xlsSql += " and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
    	} else {
    		if (!getIsAdminManager().equalsIgnoreCase("Y")) {
    			if ("Y".equals(getIsOrganManager())) {					
    				xlsSql += " and a.enterOrg like '" + getOrganID().substring(0,5) + "%' ";
    			} else {
    				xlsSql += " and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
    			}
    		}
    	}
    	if (!"".equals(Common.get(getQ_caseNoS())))
    		xlsSql += " and a.caseNo >= " + util.Common.sqlChar(getQ_caseNoS());
    	if (!"".equals(Common.get(getQ_caseNoE())))
    		xlsSql += " and a.caseNo <= " + util.Common.sqlChar(getQ_caseNoE());
    	if (!"".equals(Common.get(getQ_writeDateS())))
    		xlsSql += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
    	if (!"".equals(Common.get(getQ_writeDateE())))
    		xlsSql += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
    	if (!"".equals(Common.get(getQ_proofDoc()))){
    		xlsSql += " and a.proofDoc like " + util.Common.sqlChar("%"+getQ_proofDoc()+"%");
    	}
    	if (!"".equals(Common.get(getQ_proofNoS())))
    		xlsSql += " and a.proofNo >= " + util.Common.sqlChar(getQ_proofNoS());
    	if (!"".equals(Common.get(getQ_proofNoE())))
    		xlsSql += " and a.proofNo <= " + util.Common.sqlChar(getQ_proofNoE());
    	
    	xlsSql += " order by a.caseno";
    	
    	String[] columns = new String[] {"enterOrgName","ownership","properkind","writeDate","writeUnit","proofDoc"
										,"kind","caseNo","summonsNo","keeperDetial","chengPage","untmp020rDetail","keepName"};
    	
    	System.out.println(xlsSql);
    	int j,i;
        ResultSet rs = db.querySQL(xlsSql);
        Vector rowData = new Vector();
    	if(Integer.parseInt(q_kind)==4){
    		i=1;j=3;
    	}else{
    		i=Integer.parseInt(q_kind);
    		j=i;
    	}
        while(rs.next()){
        	for(int k=i ;k<=j;k++){
        	Object[] data = new Object[13];
        	data[0] = rs.getString("enterOrgName");//enterOrgName
        	data[1] = "權　　屬："+rs.getString("ownershipName");//ownership
        	data[2] = "財產性質："+Common.get(rs.getString("propertyKindName"));//properkind
        	data[3] = "填單日期："+Common.MaskCDate(rs.getString("writeDate"));//writeDate
        	data[4] = "填造單位："+Common.get(rs.getString("writeUnit"));//writeUnit
        	data[5] = "編　　號 :"+rs.getString("proofNo");//proofDoc
        	data[6] = "第"+numTOchi(k)+"聯";//kind
        	data[7] = "電腦單號："+rs.getString("caseno");//caseNo
        	data[8] = "傳票號數："+Common.get(rs.getString("summonsNo"));//summonsNo
        	if("Y".equals(q_detail)){
        		data[9] = new JRTableModelDataSource(getSubModel(rs.getString("enterorg"),rs.getString("ownership"),rs.getString("caseno")));
        	}else{
        		data[9] = null ;
        	}
            data[10] = Common.get(rs.getString("caseno"))+","+Common.get(rs.getString("propertyKind"))+","+String.valueOf(k);
            data[11] = new JRTableModelDataSource(getuntmp020rDetail(rs.getString("enterorg"),rs.getString("ownership"),rs.getString("caseno")));
            data[12] = getKeep(rs.getString("enterorg"),rs.getString("ownership"),rs.getString("caseno"));
            rowData.addElement(data);
        	}
        }

        Object[][] data = new Object[0][0];
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);
 
    }catch(Exception x){
    	//logfile( "one:" + x.toString());
       x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}

public DefaultTableModel getuntmp020rDetail(String enterorg ,String ownership ,String caseno) throws Exception{
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{	
   	 //subreport sql
        String[] columns = new String[] {"sourceDate","propertyNo","propertyName","specification","propertyUnit"
        								,"adjustBookAuonut","oldBookUnit","adjustBookValue","casue","returnPlace"
        								,"limityear","userYear"};
        
        String xlsDetail = " select c.organAName as enterOrgName, " +
						   " a.enterorg ,a.ownership ,a.caseno," +
						   " (select z.codeName from sysca_code z where 1=1 and (z.codeKindID='owa' or z.codeKindID='OWA') and a.ownership=z.codeID) as ownershipName, " +
						   " a.writeDate," +
						   " a.writeUnit," +
						   " a.proofDoc||'字第'||a.proofNo||'號' as proofNo," +
						   " a.caseNO," +
						   " b.sourceDate," +
						   " substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8)||'-'||b.serialNo as propertyNo," +
						   " (select d.propertyName from SYSPK_PropertyCode d where d.enterOrg in ('000000000A',a.enterOrg) and b.propertyNo=d.propertyNo) as propertyName," +
						   " b.specification||'/'||b.nameplate as specification," +
						   " (select d.propertyUnit from SYSPK_PropertyCode d where d.enterOrg in ('000000000A',a.enterOrg) and b.propertyNo=d.propertyNo) as propertyUnit," +
						   " b.adjustBookAmount," +
						   " b.oldBookUnit," +
						   " b.adjustBookValue," +
						   " b.cause ,(select f.codename from SYSCA_CODE f where f.codekindid = 'CAC' and b.cause=f.codeid) as causeName," +
						   "  b.cause1 as cause1, " +
						   " b.returnPlace,"+
						   " (select d.limitYear from SYSPK_PropertyCode d where d.enterOrg in ('000000000A',a.enterOrg) and b.propertyNo=d.propertyNo) as limitYear," +
						   " b.otherLimitYear," +
						   " b.useYear||'年'||b.useMonth||'個月' as useYear, " +
						   " a.summonsNo, " +
						   " (select z.organAName from SYSCA_Organ z where b.newEnterOrg=z.organID) as newEnterOrgName, "+
						   " b.propertyKind," +
						   " decode(b.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKindName "+
						   " from UNTMP_ReduceProof a,UNTMP_ReduceDetail b,SYSCA_Organ c "+
						   " where a.enterorg=b.enterorg and a.caseno=b.caseno and a.ownership=b.ownership  " +
						   " and a.enterOrg=c.organID(+) "+
//						   " and d.enterOrg in ('000000000A',a.enterOrg) "+
//						   " and d.propertyType='1' "+
						   " and a.enterorg="+Common.sqlChar(enterorg)+
						   " and a.ownership="+Common.sqlChar(ownership)+
						   " and a.caseno="+Common.sqlChar(caseno)+
						   "";
        
        System.out.println(xlsDetail);
    	ResultSet rs = db.querySQL(xlsDetail);
    	Vector rowData = new Vector();	    	   	
    	int count=0;  		
        while(rs.next()){
        	count ++;
    		Object[] data = new Object[12];	
    		data[0]= Common.MaskDate(rs.getString("sourceDate"));//sourceDate
        	data[1] = rs.getString("propertyNo");//propertyNo
        	data[2] = rs.getString("propertyName");//propertyName
        	data[3] = rs.getString("specification");;//specification
        	data[4] = rs.getString("propertyUnit");//propertyUnit
        	data[5] = rs.getString("adjustBookAmount");//adjustBookAuonut
        	data[6] = Common.valueFormat(rs.getString("oldBookUnit"));//oldBookUnit
        	data[7] = rs.getString("adjustBookValue");//adjustBookValue
        	if("01".equals(rs.getString("cause"))){
        		data[8] = rs.getString("causeName") + "(" + Common.get(rs.getString("newEnterOrgName")) + ")";//casue
        	}else if("99".equals(rs.getString("cause"))){
        		data[8] = rs.getString("causeName") + "("  + Common.get(rs.getString("cause1")) + ")";//casue
        	}else{
        		data[8] = rs.getString("causeName");
        	}
        	data[9] = rs.getString("returnPlace");//returnPlace
        	data[10] = rs.getString("limitYear");//limityear
        	data[11] = rs.getString("useYear");//userYear
    		rowData.addElement(data);
    	} 
        Object[][] data = new Object[0][0]; 	        	       
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);	  	        
    }catch(Exception x){
    	logfile( "two:" + x.toString());
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}	

public DefaultTableModel getSubModel(String enterorg ,String ownership ,String caseno) throws Exception{
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{	
   	 //subreport sql
        String[] columns = new String[] {"enterorgName","titleName","approveDoc","buyDate","propertyNo","propertyName"
        								,"bookAmount","bookValue","useUnit","keeper","user","place"};
        String xlsSql = " select c.organaname ,b.serialno ,"
        			  + " (select d.propertyname from syspk_PropertyCode d where 1=1 and b.propertyno=d.propertyno  and d.enterorg in (a.enterorg ,'000000000A')) || '\n' || decode('(' || b.propertyname1 || ')','()','','(' || b.propertyname1 || ')') as propertyName " + "\n"
        			  + "        ,substr(b.propertyNo,0,7)||'-'||substr(b.propertyNo,8) as propertyNo " + "\n"
        			  + " 		 ,b.buydate ,b.adjustbookamount ,b.adjustbookvalue ,e.unitname " + "\n"
        			  + "        ,g.keepername as userName ,f.keepername as keeperName " + "\n"
        			  + "        ,b.place ,a.proofdoc ,a.proofno " + "\n"
        			  + " from untmp_ReduceProof a ,untmp_ReduceDetail b ,sysca_Organ c " + "\n"
        			  + "      ,untmp_keepunit e ,untmp_keeper f ,untmp_keeper g " + "\n"
        			  + " where 1=1 " + "\n"
        			  + " and a.enterorg=b.enterorg     and a.ownership=b.ownership      and a.caseno=b.caseno " + "\n"
        			  + " and b.enterorg=c.organid(+) " + "\n"
//        			  + " and b.propertyno=d.propertyno  and d.enterorg in (a.enterorg ,'000000000A') " + "\n"
        			  + " and b.enterorg=e.enterorg     and b.useunit=e.unitno " + "\n"
        			  + " and b.enterorg=f.enterorg     and b.keepunit=f.unitno          and b.keeper=f.keeperno " + "\n"
        			  + " and b.enterorg=g.enterorg     and b.useunit=g.unitno           and b.userno=g.keeperno "
        			  + " and a.enterorg="+Common.sqlChar(enterorg)
        			  + " and a.ownership="+Common.sqlChar(ownership)
        			  + " and a.caseno="+Common.sqlChar(caseno)
        			  + "  order by unitname ,username ,keepername ,propertyno ,serialno ,buydate "
        			  + "";
        
        System.out.println(xlsSql);
    	ResultSet rs = db.querySQL(xlsSql);
    	Vector rowData = new Vector();
    	String oldData[]=new String[14];
    	String newData[]=new String[14];
        while(rs.next()){
        	if(oldData[1]==null){
        		oldData[0] = rs.getString("organaname");
        		oldData[1] = "動產使用保管核章明細表";
        		oldData[2] = rs.getString("proofdoc")+"字第"+rs.getString("proofno")+"號";
        		oldData[3] = rs.getString("buydate");
        		oldData[4] = rs.getString("propertyno");
        		oldData[5] = rs.getString("propertyName");
        		oldData[6] = rs.getString("adjustbookamount");
        		oldData[7] = rs.getString("adjustbookvalue");
        		oldData[8] = rs.getString("unitname");
        		oldData[9] = rs.getString("keeperName");
        		oldData[10] = rs.getString("userName");
        		oldData[11] = rs.getString("place");
        		oldData[12] = rs.getString("serialno");
        		oldData[13] = rs.getString("serialno");
        	}else{
        		newData[0] = rs.getString("organaname");
        		newData[1] = "動產使用保管核章明細表";
        		newData[2] = rs.getString("proofdoc")+"字第"+rs.getString("proofno")+"號";
        		newData[3] = rs.getString("buydate");
        		newData[4] = rs.getString("propertyno");
        		newData[5] = rs.getString("propertyName");
        		newData[6] = rs.getString("adjustbookamount");
        		newData[7] = rs.getString("adjustbookvalue");
        		newData[8] = rs.getString("unitname");
        		newData[9] = rs.getString("keeperName");
        		newData[10] = rs.getString("userName");
        		newData[11] = rs.getString("place");
        		newData[12] = rs.getString("serialno");
        		newData[13] = rs.getString("serialno");
        		if( checkData(oldData).equals(checkData(newData)) && (Integer.parseInt(newData[13])-Integer.parseInt(oldData[13]))==1 ){
            		oldData[6] = String.valueOf(Integer.parseInt(oldData[6])+Integer.parseInt(newData[6]));
            		oldData[7] = String.valueOf(Integer.parseInt(oldData[7])+Integer.parseInt(newData[7]));
            		oldData[13] = newData[13];
        		}else{
               		Object[] data = new Object[12];	
            		data[0] = oldData[0];
            		data[1] = oldData[1];
            		data[2] = "減損單字號："+oldData[2];
            		data[3] = Common.MaskDate(oldData[3]);
            		data[4] = oldData[4]+"\n("+oldData[12]+"-"+oldData[13]+")";
            		data[5] = oldData[5];
            		data[6] = oldData[6];
            		data[7] = oldData[7];
            		data[8] = oldData[8];
            		data[9] = oldData[9];
            		data[10] = oldData[10];
            		data[11] = oldData[11];
            		rowData.addElement(data);
            		oldData[0] = newData[0];
            		oldData[1] = newData[1];
            		oldData[2] = newData[2];
            		oldData[3] = newData[3];
            		oldData[4] = newData[4];
            		oldData[5] = newData[5];
            		oldData[6] = newData[6];
            		oldData[7] = newData[7];
            		oldData[8] = newData[8];
            		oldData[9] = newData[9];
            		oldData[10] = newData[10];
            		oldData[11] = newData[11];
            		oldData[12] = newData[12];
            		oldData[13] = newData[13];
        		}
        	}//else
     	}
       	if(1==1){
       		Object[] data = new Object[12];	
    		data[0] = oldData[0];
    		data[1] = oldData[1];
    		data[2] = "減損單字號："+oldData[2];
    		data[3] = Common.MaskDate(oldData[3]);
    		data[4] = oldData[4]+"\n("+oldData[12]+"-"+oldData[13]+")";
    		data[5] = oldData[5];
    		data[6] = oldData[6];
    		data[7] = oldData[7];
    		data[8] = oldData[8];
    		data[9] = oldData[9];
    		data[10] = oldData[10];
    		data[11] = oldData[11];
    		rowData.addElement(data);
    	}
        Object[][] data = new Object[0][0]; 	        	       
        data = (Object[][])rowData.toArray(data);
        model.setDataVector(data, columns);	  	        
    }catch(Exception x){
    	logfile( "three:" + x.toString());
    	x.printStackTrace();
    }finally{
        db.closeAll();
    }
    return model;
}

//陣列給值
public String checkData(String getData[]){
	String reData =	getData[0] + ","//= rs.getString("organaname");
				  //+ getData[1] + ","//= "動產使用保管核章明細表";
				  + getData[2] + ","//= rs.getString("proofdoc")+"第"+rs.getString("proofno");
				  + getData[3] + ","//= rs.getString("buydate");
				  + getData[4] + ","//= rs.getString("propertyno");
				  //+ getData[5] + ","//= rs.getString("propertyName");
				  //+ getData[6] + ","//= rs.getString("adjustbookamount");
				  //+ getData[7] + ","//= rs.getString("adjustbookvalue");
				  + getData[8] + ","//= rs.getString("unitname");
				  + getData[9] + ","//= rs.getString("keeperName");
				  + getData[10] + ","//= rs.getString("userName");
				  + getData[11] + ","//= rs.getString("place");
				  //+ getData[12] + ","//= rs.getString("serialno");
				  //+ newData[13] ;//= rs.getString("serialno")
				  +"";
	return reData;
}

//阿拉伯數字轉中文數字
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

public void logfile( String writeStr ) throws IOException{
	File myFile=new File("C:/untmp028r_Person_SQL.txt");  
	FileWriter myFileWriter=new FileWriter(myFile);
	BufferedWriter myBuffer=new BufferedWriter(myFileWriter);
	myBuffer.write(writeStr);
	myBuffer.newLine();
	myBuffer.close();
}

public String getKeep(String enterorg ,String ownership ,String caseno){
	String sql = " select y.unitname ||'　'|| z.keepername as keepName" + "\n"
			   + " from UNTMP_ReduceDetail x ,untmp_keepunit y ,untmp_keeper z " + "\n"
			   + " where 1=1 " + "\n"
			   + " and x.enterorg=y.enterorg(+) and x.keepunit=y.unitno " + "\n"
			   + " and z.enterorg=x.enterorg(+) and z.unitno=x.keepunit(+) and z.keeperno=x.keeper(+) " + "\n"
			   + " and x.enterorg = " + Common.sqlChar(enterorg)
			   + " and x.ownership = " + Common.sqlChar(ownership)
			   + " and x.caseno = " + Common.sqlChar(caseno);
	String reValue="";
	Database db = new Database();
	ResultSet rs;
	try{
		rs = db.querySQL(sql);
		if(rs.next()){
			reValue=rs.getString("keepName");
		}
	} catch (Exception e) {
			e.printStackTrace();
	} finally {
			db.closeAll();
	}
		return reValue;
}

}
