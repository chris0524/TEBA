/*
*<br>程式目的：動產增加單查詢檔 
*<br>程式代號：untmp006r
*<br>撰寫日期：94/11/25
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.mp;

import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import util.*;

public class UNTMP006R extends UNTMP001Q{
	String enterOrg;
	String ownership;
	int count;
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
	String q_detail;

	public String getEnterOrg(){ return checkGet(enterOrg); }
	public void setEnterOrg(String s){ enterOrg=checkSet(s); }
	public String getOwnership(){ return checkGet(ownership); }
	public void setOwnership(String s){ ownership=checkSet(s); }
	public String getCaseNo(){ return checkGet(caseNo); }
	public void setCaseNo(String s){ caseNo=checkSet(s); }
	public int getCount(){ return count; }
	public void setCount(int s){ count=s; }
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
	public String getQ_detail(){ return checkGet(q_detail); }
	public void setQ_detail(String s){ q_detail=checkSet(s); }

	String propertyNo;
	String lotNo;
	String originalPlace;
	String originalAmount;
	String originalBV;
	String scrapValue;
	String proof;
	String enterOrgName;
	String caseNoPage;
	
	public String getScrapValue(){ return checkGet(scrapValue); }
	public void setScrapValue(String s){ scrapValue=checkSet(s); }
	public String getOriginalBV(){ return checkGet(originalBV); }
	public void setOriginalBV(String s){ originalBV=checkSet(s); }
	public String getOriginalAmount(){ return checkGet(originalAmount); }
	public void setOriginalAmount(String s){ originalAmount=checkSet(s); }
	public String getPropertyNo(){ return checkGet(propertyNo); }
	public void setPropertyNo(String s){ propertyNo=checkSet(s); }
	public String getLotNo(){ return checkGet(lotNo); }
	public void setLotNo(String s){ lotNo=checkSet(s); }
	public String getOriginalPlace(){ return checkGet(originalPlace); }
	public void setOriginalPlace(String s){ originalPlace=checkSet(s); }
	public String getProof(){ return checkGet(proof); }
	public void setProof(String s){ proof=checkSet(s); }
	public String getEnterOrgName(){ return checkGet(enterOrgName); }
	public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }
	public String getCaseNoPage(){ return checkGet(caseNoPage); }
	public void setCaseNoPage(String s){ caseNoPage=checkSet(s); }
	
	
	public DefaultTableModel getResultModel() throws Exception{
		UNTMP006R obj = this;
	    DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
	    String KOC = "\n(召集人或其授權代簽人)";
	    try{
	    	String[] columns = new String[] {"OWNERSHIPNAME","CASENO","WRITEDATE","WRITEUNIT","PROOF","MANAGENO","subReportDataSource","Q_KIND","ENTERORGNAME","caseNoPage","SUMMONSNO","USEUNITUSER","subReportDataSource1","sumBookAmount","sumBookValue","KOC","propertyKind","propertyKindName"};
	
	    	String execSQL="select count(*) as count, a.enterOrg, a.ownership, a.caseNo, " +
	    					" b.ORGANANAME as enterOrgName, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo," +
	    					" g.codeName as fundType_Name, z.codename as ownershipName, a.summonsNo, " +
	    					" sum(d.originalAmount) as sumBookAmount, sum(d.originalBV) as sumBookValue, " +
	    					" c.propertyKind, decode(c.propertyKind,'01','公務用','02','公共用','03','事業用','04','非公用','') propertyKindName "+
	    					" from UNTMP_AddProof a, SYSCA_Organ b, untmp_movable c, untmp_movabledetail d, sysca_code z, SYSCA_Code g "+
							" where 1=1 " +
							" and a.enterOrg = b.organID " +
							" and c.fundType=g.codeID(+) and g.codeKindID(+) = 'FUD' "+
							" and a.enterOrg=c.enterOrg and a.ownership=c.ownership and a.caseNo=c.Caseno " +
							" and c.enterOrg=d.enterorg and c.ownership=d.ownership and c.lotNo=d.lotNo and c.propertyNo=d.propertyNo "+
							" and a.ownership=z.codeID and z.codeKindID='OWA' "+
							"";
			if (!"".equals(getQ_enterOrg())) {
				execSQL +=" and a.enterOrg = " + Common.sqlChar(getQ_enterOrg()) ;
			} else {
				if (!getIsAdminManager().equalsIgnoreCase("Y")) {
					if ("Y".equals(Common.get(getIsOrganManager()))) {					
						execSQL += " and ( a.enterOrg = "+ Common.sqlChar(getOrganID()) +" or a.enterOrg in ( select organID from SYSCA_Organ where organSuperior = "+ Common.sqlChar(getOrganID()) +" ) ) ";		
					} else {
						execSQL +=" and a.enterOrg = " + Common.sqlChar(getOrganID()) ;
					}
				}
			}
	
			if (!"".equals(Common.get(getQ_ownership()))){
	    		execSQL +=" and a.ownership = " + util.Common.sqlChar(getQ_ownership());
	    	}
			if (!"".equals(Common.get(getQ_caseNoS())))
				execSQL +=" and a.caseNo >= " + Common.sqlChar(Common.formatRearString(getQ_caseNoS(),10,'0'));		
			if (!"".equals(Common.get(getQ_caseNoE())))
				execSQL +=" and a.caseNo<=" + Common.sqlChar(Common.formatRearString(getQ_caseNoE(),10, '9'));
	    	
	    	if (!"".equals(Common.get(getQ_writeDateS())))
	    		execSQL += " and a.writeDate >= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateS(),"2"));
	    	if (!"".equals(Common.get(getQ_writeDateE())))
	    		execSQL += " and a.writeDate <= " + util.Common.sqlChar(new Datetime().changeTaiwanYYMMDD(getQ_writeDateE(),"2"));
	    	
			if (!"".equals(Common.get(getQ_proofDoc())))
				execSQL += " and a.proofDoc like '%" + getQ_proofDoc() + "%'" ;
			if (!"".equals(Common.get(getQ_proofNoS()))) 
				execSQL += " and a.proofNo >= " + Common.sqlChar(getQ_proofNoS());		
			if (!"".equals(Common.get(getQ_proofNoE()))) 
				execSQL += " and a.proofNo <= " + Common.sqlChar(getQ_proofNoE());		 
	    	execSQL+=" group by a.enterOrg, a.ownership, a.caseNo, c.propertyKind, b.ORGANANAME, a.writeDate, a.writeUnit, a.proofDoc, a.proofNo, a.summonsNo, g.codeName, z.codename";
			execSQL+=" order by a.caseNo,c.propertyKind ";
	    	
			String kindName[]={"第一聯","第二聯","第三聯"};
	    	if(!"4".equals(q_kind)){
	    		if("3".equals(q_kind))q_kind=kindName[2];
	    		else if("2".equals(q_kind))q_kind=kindName[1];
	    		else q_kind=kindName[0];
	    	ResultSet rs = db.querySQL(execSQL);
	        Vector rowData = new Vector();
	        //int i;
	        while(rs.next()){
	        	obj.setEnterOrg(rs.getString("enterOrg"));
	        	obj.setOwnership(rs.getString("ownership"));
	        	obj.setCaseNo(rs.getString("caseNo"));
	        	obj.setCount(rs.getInt("count"));
	        	Object[] data = new Object[18];
	            data[0] = rs.getString("ownershipName");
	            data[1] = "電腦單號："+rs.getString("caseNo");
	            data[2] = Common.MaskCDate(rs.getString("writeDate"));
	            data[3] = rs.getString("writeUnit");
	            data[4] = rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號";
	            data[5] = "基金名稱："+Common.get(rs.getString("fundType_Name"));
	            data[6] = new JRTableModelDataSource(getSubModel((String)data[0],rs.getString("propertyKind")));
	            data[7] = q_kind;
	            data[8] = rs.getString("enterOrgName");
	            data[9] = rs.getString("caseNo");
	            data[10] = Common.get(rs.getString("summonsNo"))+"　";
	            data[11] = getUseUnitUser(db, rs.getString("propertyKind"));
	            obj.setProof(rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號");
	            obj.setEnterOrgName(rs.getString("enterOrgName"));
	            obj.setCaseNoPage(rs.getString("caseNo"));
	            if("Y".equals(q_detail)){
	            	data[12] = new JRTableModelDataSource(getSubModel1((String)data[0],rs.getString("propertyKind")));
	            }else{
	            	data[12] = null;
	            }
	            data[13] = Common.valueFormat(rs.getString("sumBookAmount"));
	            data[14] = Common.valueFormat(rs.getString("sumBookValue"));
	            if(q_ownership.equals("4")){
	            	data[15] = KOC;
	            }else{
	            	data[15] = "";
	            }
	            data[16] = Common.get(rs.getString("propertyKind"));
	            data[17] = Common.get(rs.getString("propertyKindName"));
	            //for(i=0;i<10;i++)if(data[i]==null)data[i]="";
				rowData.addElement(data);
	        }
	    	
	        Object[][] data = new Object[0][0];
	        data = (Object[][])rowData.toArray(data);
	        model.setDataVector(data, columns);
	    	}//if
	    	else
	    	{
	    		int j;
	    		Vector rowData = new Vector();
	    		for(j=0;j<3;j++){
	    		q_kind=kindName[j];
	    		ResultSet rs = db.querySQL(execSQL);
	            //int i;
	            while(rs.next()){
	            	obj.setEnterOrg(rs.getString("enterOrg"));
	            	obj.setOwnership(rs.getString("ownership"));
	            	obj.setCaseNo(rs.getString("caseNo"));
	            	obj.setCount(rs.getInt("count"));
	            	Object[] data = new Object[18];
	                data[0] = rs.getString("ownershipName");
	                data[1] = "電腦單號："+rs.getString("caseNo");
	                data[2] = Common.MaskCDate(rs.getString("writeDate"));
	                data[3] = rs.getString("writeUnit");
	                data[4] = rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號";
	                data[5] = "基金名稱："+Common.get(rs.getString("fundType_Name"));
	                data[6] = new JRTableModelDataSource(getSubModel((String)data[0],rs.getString("propertyKind")));
	                data[7] = q_kind;
	                data[8] = rs.getString("enterOrgName");
	                data[9] = rs.getString("caseNo")+j;
	                data[10] = Common.get(rs.getString("summonsNo"))+"　";
	                data[11] = getUseUnitUser(db, rs.getString("propertyKind"));
		            obj.setProof(rs.getString("proofDoc")+"字第"+rs.getString("proofNo")+"號");
		            obj.setEnterOrgName(rs.getString("enterOrgName"));
		            obj.setCaseNoPage(rs.getString("caseNo")+j);
		            if("Y".equals(q_detail)){
		            	data[12] = new JRTableModelDataSource(getSubModel1((String)data[0],rs.getString("propertyKind")));
		            }else{
		            	data[12] = null;
		            }
		            data[13] = Common.valueFormat(rs.getString("sumBookAmount"));
		            data[14] = Common.valueFormat(rs.getString("sumBookValue"));
		            if(q_ownership.equals("4")){
		            	data[15] = KOC;
		            }else{
		            	data[15] = "";
		            }		            
		            data[16] = Common.get(rs.getString("propertyKind"));
		            data[17] = Common.get(rs.getString("propertyKindName"));
	                //for(i=0;i<10;i++)if(data[i]==null)data[i]="";
	    			rowData.addElement(data);
	             }
	    		}//for
	            Object[][] data = new Object[0][0];
	            data = (Object[][])rowData.toArray(data);
	            model.setDataVector(data, columns);
	        	}//else
	    }catch(Exception x){
	       x.printStackTrace();
	    }finally{
	        db.closeAll();
	    }
    return model;
	}

	//使用單位內資料
	public String getUseUnitUser(Database db, String propertyKind){
		String useUnitUser="";
		//Database db = new Database();	
		UNTMP006R obj = this;
		int count=0;
		ResultSet rs;	
    	String sql="select distinct " +
						" (select d.unitName from UNTMP_KeepUnit d where b.enterOrg=d.enterOrg and b.originalUseUnit=d.unitNo) as originalUseUnit, " +
						" (select e.keeperName from UNTMP_Keeper e where b.enterOrg=e.enterOrg and b.originalUseUnit=e.unitNo and b.originalUser=e.keeperNo) as originalUser " +
						" from UNTMP_Movable a, UNTMP_MovableDetail b "+
						" where 1=1 " +
						" and a.enterOrg=b.enterOrg(+) and a.ownership=b.ownership(+) and a.propertyNo=b.propertyNo(+) and a.lotNo=b.lotNo(+) " +
						" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
						" and a.caseNo=" + util.Common.sqlChar(obj.getCaseNo())+
						" and a.propertyKind=" + util.Common.sqlChar(propertyKind)+
						" ";
//System.out.println("== UNTMP006R 使用單位內資料 == "+sql);
		try{
			rs = db.querySQL(sql);
			while (rs.next()){
				count++;
				useUnitUser = Common.get(rs.getString("originalUseUnit"))+"　"+Common.get(rs.getString("originalUser"));
			}
			if(count>=2){
				useUnitUser = useUnitUser+"等";
			}
			if (rs.getStatement()!=null){ rs.getStatement().close(); }
			if (rs!=null){ rs.close(); }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.closeAll();
		}	
	return useUnitUser;	
	}
	
	//增加單明細
public DefaultTableModel getSubModel(String caseCode,String propertyKind) throws Exception{
	UNTMP006R obj = this;
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    String oldPropertyNo="";
    String oldLotNo="";
    String oldOriginalPlace="";
    String newPropertyNo="";
    String newLotNo="";
    String newOriginalPlace="";
    String serialNoS="";
    String serialNoE="";
    int originalAmount = 0;
    int originalBV = 0;
    int scrapValue = 0;
    int countDetail = 0;
    String data1="", data2="", data3="", data4="", data5="", data6="", data7="", data8="", data9="", data10="", data11="", data12="", data13="", data14="";
    try{
        String[] columns = new String[] {"SOURCEDATE","PROPERTYNO","PROPERTYNAME","NAMEPLATE"
        							    ,"SOURCEKINDNAME","PROPERTYUNIT","ORIGINALAMOUNT","ORIGINALUNIT"
        							    ,"ORIGINALBV","ORIGINALPLACE","SCRAPVALUE","LIMITYEAR","DEPRMETHOD"
        							    ,"ACCOUNTINGTITLE"
        							    };
        
    	String execSQL = " select a.propertyNo ,a.lotNo ,b.serialno ,b.Originalplace "
    				   + " 		  ,(substr(a.propertyNo,1,7) || '-' || substr(a.propertyNo,8)) as propertyNoShow "
    				   + " 		  ,a.sourceDate ,c.propertyName ,a.propertyName1 "
    				   + "        ,(a.specification || '/' || a.nameplate) as nameplate "
    				   + "		  ,d.codeName as sourceKind "
    				   + " 		  ,c.propertyUnit ,a.otherPropertyUnit ,a.originalUnit ,c.limitYear ,a.otherLimitYear "
    				   + " 	 	  ,decode(a.deprMethod ,'01' ,' ' ,'02' ,'直線法' ,'03' ,'直線法' ,'04','直線法' ,'05' ,'報廢法','') as deprMethod "
    				   + " 		  ,a.accountingTitle ,b.originalAmount ,b.originalBV ,b.scrapValue "
    				   + " from UNTMP_Movable a, UNTMP_MovableDetail b, SYSPK_PropertyCode c, SYSCA_Code d "
    				   + " where 1=1 "
    				   + " and a.enterOrg=b.enterOrg(+) and a.ownership=b.ownership(+) and a.propertyNo=b.propertyNo(+) and a.lotNo=b.lotNo(+) "
    				   + " and a.propertyNo = c.propertyNo and c.enterOrg in('000000000A',a.enterOrg) and c.propertyType='1' "
    				   + " and a.sourceKind=d.codeID(+) and d.codeKindID(+) = 'SKC'"
    				   + " and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())
    				   + " and a.ownership=" + util.Common.sqlChar(obj.getOwnership())
					   + " and a.caseNo=" + util.Common.sqlChar(obj.getCaseNo())
					   + " and a.propertyKind=" + util.Common.sqlChar(propertyKind)
					   + " order by a.propertyNo, a.lotNo, b.serialNo, b.originalPlace"
					   + "";

    	ResultSet rs = db.querySQL(execSQL);
    	Vector rowData = new Vector();
    	
    	while(rs.next()){
    		countDetail++;
    		Object[] data = new Object[14];
    		Object[] oldData = new Object[14];
    		newPropertyNo=Common.get(rs.getString("propertyNo"));
    		newLotNo=Common.get(rs.getString("lotNo"));
    		newOriginalPlace=Common.get(rs.getString("originalPlace"));
    		serialNoE = Common.get(rs.getString("serialNo"));
    		
    		if(newPropertyNo.equals(oldPropertyNo) && newLotNo.equals(oldLotNo) && newOriginalPlace.equals(oldOriginalPlace)){
    			originalAmount += rs.getInt("originalAmount");
    			originalBV += rs.getInt("originalBV");
    			scrapValue += rs.getInt("scrapValue");
    			serialNoE = rs.getString("serialNo");
    		}else{
    			serialNoS = rs.getString("serialNo");
    			originalAmount = rs.getInt("originalAmount");
    			originalBV = rs.getInt("originalBV");
    			scrapValue = rs.getInt("scrapValue");
    		}
    		if(rs.getString("sourceDate")==null){
    			data[0] = "";
    		}else{
    			data[0] = Common.MaskDate(rs.getString("sourceDate"));
    		}
    		data[1] = rs.getString("propertyNoShow")+"\n"+serialNoS+"-\n"+serialNoE+"　";
    		data[2] = rs.getString("propertyName");
    		
    		if("".equals(Common.get(rs.getString("propertyName1")))){
    			data[3] = Common.get(rs.getString("nameplate"));
    		}else{
    			data[3] = Common.get(rs.getString("nameplate")) + "\n"
    					+ "(" + Common.get(rs.getString("propertyName1")) + ")";
    		}
    		
    		data[4] = Common.get(rs.getString("sourceKind"));
    		if(rs.getString("propertyUnit")!=null){
    			data[5] = rs.getString("propertyUnit");
    		}else{
    			data[5] = rs.getString("otherPropertyUnit");
    		}
    		data[6] = Common.get(Common.valueFormat(originalAmount+""));
    		data[7] = Common.get(Common.valueFormat(rs.getString("originalUnit")));
    		data[8] = Common.get(Common.valueFormat(originalBV+""));
    		data[9] = Common.get(rs.getString("originalPlace"));
    		data[10] = Common.get(Common.valueFormat(scrapValue+""));
    		if(rs.getString("limitYear")!=null){
    		    data[11] = rs.getString("limitYear");
    		}else{
    		    data[11] = rs.getString("otherLimitYear");
    		}
    		data[12] = rs.getString("deprMethod");
    		data[13] = Common.get(rs.getString("accountingTitle"));
    		//for(i=0;i<14;i++)if(data[i]==null)data[i]="";
    		
    		
    		if("".equals(oldPropertyNo) || oldPropertyNo==null){
	    		data1 = data[0]+"";
				data2 = data[1]+"";
				data3 = data[2]+"";
				data4 = data[3]+"";
				data5 = data[4]+"";
				data6 = data[5]+"";
				data7 = data[6]+"";
				data8 = data[7]+"";
				data9 = data[8]+"";
				data10 = data[9]+"";
				data11 = data[10]+"";
				data12 = data[11]+"";
				data13 = data[12]+"";
				data14 = data[13]+"";
				oldPropertyNo = newPropertyNo;
	    		oldLotNo = newLotNo;
	    		oldOriginalPlace = newOriginalPlace;
    		}
    		if(!newPropertyNo.equals(oldPropertyNo) || !newLotNo.equals(oldLotNo) || !newOriginalPlace.equals(oldOriginalPlace)){
    			oldData[0] = Common.get(data1);
    			oldData[1] = Common.get(data2);
    			oldData[2] = Common.get(data3);
    			oldData[3] = Common.get(data4);
    			oldData[4] = Common.get(data5);
    			oldData[5] = Common.get(data6);
    			oldData[6] = Common.get(data7);
    			oldData[7] = Common.get(data8);
    			oldData[8] = Common.get(data9);
    			oldData[9] = Common.get(data10);
    			oldData[10] = Common.get(data11);
    			oldData[11] = Common.get(data12);
    			oldData[12] = Common.get(data13);
    			oldData[13] = Common.get(data14);
    			rowData.addElement(oldData);
    		}
    		if(countDetail==obj.getCount()){
    			rowData.addElement(data);
    		}
    		data1 = data[0]+"";
			data2 = data[1]+"";
			data3 = data[2]+"";
			data4 = data[3]+"";
			data5 = data[4]+"";
			data6 = data[5]+"";
			data7 = data[6]+"";
			data8 = data[7]+"";
			data9 = data[8]+"";
			data10 = data[9]+"";
			data11 = data[10]+"";
			data12 = data[11]+"";
			data13 = data[12]+"";
			data14 = data[13]+"";
    		oldPropertyNo = newPropertyNo;
    		oldLotNo = newLotNo;
    		oldOriginalPlace = newOriginalPlace;
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
	
	//動產使用保管核章明細表
	public DefaultTableModel getSubModel1(String caseCode,String propertyKind) throws Exception{
		UNTMP006R obj = this;
		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
	    Database db = new Database();
	    String oldPropertyNo="";
	    String oldLotNo="";
	    String oldOriginalPlace="";
	    String oldUseUnit="";
	    String oldUser="";
	    String oldKeeper="";
	    String newPropertyNo="";
	    String newLotNo="";
	    String newOriginalPlace="";
	    String newUseUnit="";
	    String newUser="";
	    String newKeeper="";
	    String serialNoS="";
	    String serialNoE="";
	    int originalAmount = 0;
	    int originalBV = 0;
	    int countDetail = 0;
	    String data1="", data2="", data3="", data4="", data5="", data6="", data7="", data8="", data9="", data10="", data11="", data12="";
	    try{
	        String[] columns = new String[] { "PROOF","CASENO","ENTERORGNAME","buyDate","propertySerialNo","propertyNoName","originalAmount","originalBV","originalUseUnit","originalUser","originalKeeper","originalPlace"};
	    	String execSQL="select a.propertyNo, a.lotNo, b.serialno, b.Originalplace, " +
						" (substr(a.propertyNo,1,7) || '-' || substr(a.propertyNo,8)) as propertyNoShow, " +
						" a.buyDate, a.propertyName1, " +
						" (select c.propertyName from SYSPK_PropertyCode c where a.propertyNo = c.propertyNo and c.enterOrg in('000000000A',a.enterOrg) and c.propertyType='1') as propertyName, " + 
						" b.originalAmount, b.originalBV, " +
						" (select d.unitName from UNTMP_KeepUnit d where b.enterOrg=d.enterOrg and b.originalUseUnit=d.unitNo) as originalUseUnit, " +
						" (select e.keeperName from UNTMP_Keeper e where b.enterOrg=e.enterOrg and b.originalUseUnit=e.unitNo and b.originalUser=e.keeperNo) as originalUser, " +
						" (select e.keeperName from UNTMP_Keeper e where b.enterOrg=e.enterOrg and b.originalKeepUnit=e.unitNo and b.originalKeeper=e.keeperNo) as originalKeeper " +
						" from UNTMP_Movable a, UNTMP_MovableDetail b, SYSCA_Code d "+ 
						" where 1=1 and a.enterOrg=b.enterOrg(+) and a.ownership=b.ownership(+) and a.propertyNo=b.propertyNo(+) and a.lotNo=b.lotNo(+) " +
						//" and a.propertyNo = c.propertyNo and c.enterOrg in('000000000A',a.enterOrg) and c.propertyType='1' " +
						" and a.sourceKind=d.codeID(+) and d.codeKindID(+) = 'SKC'"+
						" and a.enterOrg=" + util.Common.sqlChar(obj.getEnterOrg())+
						" and a.ownership=" + util.Common.sqlChar(obj.getOwnership())+
						" and a.caseNo=" + util.Common.sqlChar(obj.getCaseNo())+
						" and a.propertyKind=" + util.Common.sqlChar(propertyKind)+
						" order by a.propertyNo, a.lotNo, b.serialNo, b.originalPlace";
//System.out.println("== UNTMP006R 動產使用保管核章明細表 == "+execSQL);		    	
	    	ResultSet rs = db.querySQL(execSQL);
	    	Vector rowData = new Vector();
	    	//int i;
	    	while(rs.next()){
	    		countDetail++;
	    		Object[] data = new Object[12];
	    		Object[] oldData = new Object[12];
	    		
	    		newPropertyNo=Common.get(rs.getString("propertyNo"));
	    		newLotNo=Common.get(rs.getString("lotNo"));
	    		newOriginalPlace=Common.get(rs.getString("originalPlace"));
	    		newUseUnit=Common.get(rs.getString("originalUseUnit"));
	    		newUser=Common.get(rs.getString("originalUser"));
	    		newKeeper=Common.get(rs.getString("originalKeeper"));
	    		serialNoE = Common.get(rs.getString("serialNo"));
	    		
	    		if(newPropertyNo.equals(oldPropertyNo) && newLotNo.equals(oldLotNo) && newOriginalPlace.equals(oldOriginalPlace) && newUseUnit.equals(oldUseUnit) && newUser.equals(oldUser) && newKeeper.equals(oldKeeper)){
	    			originalAmount += rs.getInt("originalAmount");
	    			originalBV += rs.getInt("originalBV");
	    			serialNoE = rs.getString("serialNo");
	    		}else{
	    			serialNoS = rs.getString("serialNo");
	    			originalAmount = rs.getInt("originalAmount");
	    			originalBV = rs.getInt("originalBV");
	    		}
    			data[0] = obj.getProof();
    			data[1] = obj.getCaseNoPage();
    			data[2] = obj.getEnterOrgName();
    			data[3] = Common.MaskDate(rs.getString("buyDate"));
	    		data[4] = rs.getString("propertyNoShow")+"\n"+serialNoS+"-\n"+serialNoE+"　";
	    		if(rs.getString("propertyName1")==null || "".equals(rs.getString("propertyName1"))){
	    			data[5] = rs.getString("propertyName");
	    		}else{
	    			data[5] = rs.getString("propertyName")+"\n("+rs.getString("propertyName1")+")";
	    		}
	    		data[6] = Common.valueFormat(originalAmount+"");
	    		data[7] = Common.valueFormat(originalBV+"");
	    		data[8] = rs.getString("originalUseUnit");
	    		data[9] = rs.getString("originalUser");
	    		data[10] = rs.getString("originalKeeper");
	    		data[11] = Common.get(rs.getString("originalPlace"));
	    		//for(i=0;i<14;i++)if(data[i]==null)data[i]="";
	    		if("".equals(oldPropertyNo) || oldPropertyNo==null){
		    		data1 = data[0]+"";
					data2 = data[1]+"";
					data3 = data[2]+"";
					data4 = data[3]+"";
					data5 = data[4]+"";
					data6 = data[5]+"";
					data7 = data[6]+"";
					data8 = data[7]+"";
					data9 = data[8]+"";
					data10 = data[9]+"";
					data11 = data[10]+"";
					data12 = data[11]+"";
    				oldPropertyNo = newPropertyNo;
		    		oldLotNo = newLotNo;
		    		oldOriginalPlace = newOriginalPlace;
		    		oldUseUnit = newUseUnit;
		    		oldUser = newUser;
		    		oldKeeper = newKeeper;
	    		}
	    		if(!newPropertyNo.equals(oldPropertyNo) || !newLotNo.equals(oldLotNo) || !newOriginalPlace.equals(oldOriginalPlace) || !newUseUnit.equals(oldUseUnit) || !newUser.equals(oldUser) || !newKeeper.equals(oldKeeper)){
	    			oldData[0] = data1;
	    			oldData[1] = data2;
	    			oldData[2] = data3;
	    			oldData[3] = data4;
	    			oldData[4] = data5;
	    			oldData[5] = data6;
	    			oldData[6] = data7;
	    			oldData[7] = data8;
	    			oldData[8] = data9;
	    			oldData[9] = data10;
	    			oldData[10] = data11;
	    			oldData[11] = data12;
	    			rowData.addElement(oldData);
	    		}
	    		if(countDetail==obj.getCount()){
	    			rowData.addElement(data);
	    		}
	    		data1 = data[0]+"";
				data2 = data[1]+"";
				data3 = data[2]+"";
				data4 = data[3]+"";
				data5 = data[4]+"";
				data6 = data[5]+"";
				data7 = data[6]+"";
				data8 = data[7]+"";
				data9 = data[8]+"";
				data10 = data[9]+"";
				data11 = data[10]+"";
				data12 = data[11]+"";
	    		oldPropertyNo = newPropertyNo;
	    		oldLotNo = newLotNo;
	    		oldOriginalPlace = newOriginalPlace;
	    		oldUseUnit = newUseUnit;
	    		oldUser = newUser;
	    		oldKeeper = newKeeper;
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