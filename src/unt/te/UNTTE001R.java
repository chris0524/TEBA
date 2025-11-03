/*
*<br>程式目的：核定繳納文稿主旨和說明 
*<br>程式代號：untte001r
*<br>撰寫日期：94/12/14
*<br>程式作者：Cherry
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.te;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import util.*;

public class UNTTE001R extends SuperBean{
	String isOrganManager;
	String isAdminManager;
	String organID;       	
	String q_kindtype;
	String signNo;
	String oldSignNo;

	public String getSignNo() { return checkGet(signNo); }
	public void setSignNo(String s) { signNo = checkSet(s); }
	public String getOldSignNo() { return checkGet(oldSignNo); }
	public void setOldSignNo(String s) { oldSignNo = checkSet(s); }
	public String getQ_kindtype() { return checkGet(q_kindtype); }
	public void setQ_kindtype(String s) { q_kindtype = checkSet(s); }
	public String getOrganID() { return checkGet(organID); }
	public void setOrganID(String s) { organID = checkSet(s); }
	public String getIsOrganManager() { return checkGet(isOrganManager); }
	public void setIsOrganManager(String s) { isOrganManager = checkSet(s); }
	public String getIsAdminManager() { return checkGet(isAdminManager); }
	public void setIsAdminManager(String s) { isAdminManager = checkSet(s); }

public DefaultTableModel getResultModel() throws Exception{
	UNTTE001R obj = this;
	String execSQL="";
	String taxKindType="";
	String signType="";
	String kindTypeName="";
	int taxAmountValue=0;
	int count=0;
	int i=0;
	int taxAmountTotal=0;
	int countSumTotal=0;
	int countSum[];
	int taxAmountSum[];
	String countTaxAmount="";
	String signNo="";
	String signNoSum[];
	String signDesc="";
	String signDescSum[];
	String signName="";
	String signNameSum[];
	String startTaxAmountYM="";
	String endTaxAmountYM="";
	int taxAmountSE=0;
	ResultSet rs;
	DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    Database db = new Database();
    try{
    	String[] columns = new String[] {"SIGNNO","DATE","GIST","EXPLAIN1","EXPLAIN2","DRAFT"};
    	String todayDate = Integer.parseInt(Datetime.getYYY())+"年"+Integer.parseInt(Datetime.getMM())+"月"+Integer.parseInt(Datetime.getYYYMMDD().substring(5,7))+"日";
    	//土地
    	if(q_kindtype.equals("1")){
    	    taxKindType="地價稅";
    	    signType="地號";
    	    kindTypeName="土地";
	    	execSQL="(select a.taxAmount, b.signNo, c.signDesc, " +
	    			" (select d.signName || c.signName from SYSCA_SIGN d where (substr(b.signNo,1,3) || '0000')= d.signNo) as signName " +
	    			" from UNTLA_Tax a, UNTLA_Land b, SYSCA_SIGN c where 1=1 " +
	    			" and b.dataState='1' and b.verify='Y' and b.enterDate<=" +Common.sqlChar(Datetime.getYYY()+"0831") +
	    			" and a.taxYear="+Common.sqlChar(Datetime.getYYY())+
	    			" and a.taxKind != '01'"+
	    			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo" +
					" and substr(b.signNo,1,7)= c.signNo " +
					" and b.enterOrg = " + Common.sqlChar(organID) + 
					" ) " +
					"union " +
					"(select a.taxAmount, b.signNo, c.signDesc, "+
	    			" (select d.signName || c.signName from SYSCA_SIGN d where (substr(b.signNo,1,3) || '0000')= d.signNo) as signName " +
	    			" from UNTLA_Tax a, UNTLA_Land b, SYSCA_SIGN c where 1=1 " +
	    			" and b.dataState='2' and b.enterDate<=" +Common.sqlChar(Datetime.getYYY()+"0831") +
	    			" and b.reduceDate>" +Common.sqlChar(Datetime.getYYY()+"0831") +
					" and b.reduceCause not in('401','402','403','404','405') " +
	    			" and a.taxYear="+Common.sqlChar(Datetime.getYYY())+
	    			" and a.taxKind != '01'"+
	    			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo" +
					" and substr(b.signNo,1,7)= c.signNo " +
					" and b.enterOrg = " + Common.sqlChar(organID) + 
					" ) order by signNo ";
	    //建物
    	}else{
    	    taxKindType="房屋稅";
    	    signType="建號";
    	    kindTypeName="建物";
	    	execSQL="(select a.taxAmount, b.signNo, c.signDesc, b.enterDate, b.dataState, " +
	    			" (select d.signName || c.signName from SYSCA_SIGN d where (substr(b.signNo,1,3) || '0000')= d.signNo) as signName, " +
	    			" MONTHS_BETWEEN( to_date(lpad(substr(to_char((to_char(sysDate,'YYYYMMDD')-19110000),'0000000'),0,4) || '0601',8,'0')+19110000,'YYYYMMDD') , to_date(lpad(substr(b.enterDate,0,5) || '01',7,'0')+19110000,'YYYYMMDD') ) as taxAmountCount, " +
	    			" b.reduceDate " +
	    			" from UNTBU_Tax a, UNTBU_Building b, SYSCA_SIGN c where 1=1 " +
	    			" and b.dataState='1' and b.verify='Y' and b.enterDate<=" +Common.sqlChar(Datetime.getYYY()+"0531") +
	    			" and a.taxYear="+Common.sqlChar(Datetime.getYYY())+
	    			" and a.taxKind != '01'"+
	    			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo" +
					" and substr(b.signNo,1,7)= c.signNo " +
					" and b.enterOrg = " + Common.sqlChar(organID) + 
					" ) " +
					"union " +
					"(select a.taxAmount, b.signNo, c.signDesc, b.enterDate, b.dataState, "+
	    			" (select d.signName || c.signName from SYSCA_SIGN d where (substr(b.signNo,1,3) || '0000')= d.signNo) as signName, " +
	    			" MONTHS_BETWEEN( to_date(lpad(substr(to_char((to_char(sysDate,'YYYYMMDD')-19110000),'0000000'),0,4) || '0601',8,'0')+19110000,'YYYYMMDD') , to_date(lpad(substr(b.enterDate,0,5) || '01',7,'0')+19110000,'YYYYMMDD') ) as taxAmountCount, " +
	    			" b.reduceDate " +
	    			" from UNTBU_Tax a, UNTBU_Building b, SYSCA_SIGN c where 1=1 " +
	    			" and b.dataState='2' and b.enterDate<=" +Common.sqlChar(Datetime.getYYY()+"0531") +
	    			" and b.reduceDate>=" +Common.sqlChar(yearFormat(Integer.parseInt(Datetime.getYYY())-1+"")+"0701") +
	    			" and a.taxYear="+Common.sqlChar(Datetime.getYYY())+
	    			" and a.taxKind != '01'"+
	    			" and a.enterOrg=b.enterOrg and a.ownership=b.ownership and a.propertyNo=b.propertyNo and a.serialNo=b.serialNo" +
					" and substr(b.signNo,1,7)= c.signNo " +
					" and b.enterOrg = " + Common.sqlChar(organID) + 
					" ) order by signNo ";
    	} 
        rs = db.querySQL(execSQL);
        while(rs.next()){
            obj.setSignNo(rs.getString("signNo").substring(0,1));
            if(!(obj.getSignNo()).equals(obj.getOldSignNo())){
                countSumTotal++;
            }
            obj.setOldSignNo(obj.getSignNo());
        }
        rs.close();
        rs = db.querySQL(execSQL);
        Vector rowData = new Vector();
    	countSum = new int[countSumTotal];
    	taxAmountSum = new int[countSumTotal];
    	signNoSum = new String[countSumTotal];
    	signDescSum = new String[countSumTotal];
    	signNameSum = new String[countSumTotal];
    	obj.setOldSignNo("");
    	while(rs.next()){
            obj.setSignNo(rs.getString("signNo").substring(0,1));
            if(!(obj.getSignNo()).equals(obj.getOldSignNo())){
				i++;
    	        count=0;
            	taxAmountTotal=0;
            	signNo="";
            	signDesc="";
            	signName="";
            }
            obj.setOldSignNo(obj.getSignNo());
            count++;
            //土地
            if(q_kindtype.equals("1")){
                taxAmountValue = rs.getInt("taxAmount");
            //建物
            }else{
                //現存
                if(rs.getString("dataState").equals("1")){
                    //假如「UNTBU_Building.enterDate＜(系統年度－1年)||’0701’」
                    //稅額以1年計算
                    if(Integer.parseInt(rs.getString("enterDate"))<Integer.parseInt(Integer.parseInt(Datetime.getYYY())-1+"0701")){
                        taxAmountValue = rs.getInt("taxAmount");
                    //稅額＝taxAmount×[系統年度||’06’－substr(enterDate,0,5)]之月數÷12
                    }else{
                        countTaxAmount = String.valueOf(Double.parseDouble(rs.getString("taxAmount"))*Double.parseDouble(rs.getString("taxAmountCount"))/12);
                        taxAmountValue = Integer.parseInt(countTaxAmount.substring(0,countTaxAmount.indexOf(".")));
                    }
                //已減損
                }else{
                    //起算年月
                    //假如「UNTBU_Building.enterDate＜(系統年度－1年)||’0701’」
                    if(Integer.parseInt(rs.getString("enterDate"))<Integer.parseInt(Integer.parseInt(Datetime.getYYY())-1+"0701")){
                        startTaxAmountYM = yearFormat(Integer.parseInt(Datetime.getYYY())-1+"")+"07";
                    }else{
                        startTaxAmountYM = rs.getString("enterDate").substring(0,3)+monthFormat(Integer.parseInt(rs.getString("enterDate").substring(3,5))+1+"");
                    }
                    //終止年月
                    //假如「UNTBU_Building.reduceDate＞系統年度||’0630’」
                    if(Integer.parseInt(rs.getString("reduceDate"))>Integer.parseInt(Integer.parseInt(Datetime.getYYY())+"0630")){
                        endTaxAmountYM = Datetime.getYYY()+"06";
                    }else{
                        endTaxAmountYM = rs.getString("reduceDate").substring(0,5);
                    }
                    //稅額＝taxAmount×[(稅額終止年月－稅額起算年月)之月數＋1個月]÷12
                    taxAmountSE = ((Integer.parseInt(endTaxAmountYM.substring(0,3))-Integer.parseInt(startTaxAmountYM.substring(0,3)))*12)+(Integer.parseInt(endTaxAmountYM.substring(3,5))-Integer.parseInt(startTaxAmountYM.substring(3,5)))+1;
                    countTaxAmount = String.valueOf(Double.parseDouble(rs.getString("taxAmount"))*taxAmountSE/12);
                    taxAmountValue = Integer.parseInt(countTaxAmount.substring(0,countTaxAmount.indexOf(".")));
                }
            }
    	    taxAmountTotal+=taxAmountValue;
    	    signNo = rs.getString("signNo").substring(0,1);
    	    //土地
    	    if(q_kindtype.equals("1")){
    	        if(rs.getString("signNo").substring(11).equals("0000")){
		    	    signDesc = rs.getString("signDesc")+Integer.parseInt(rs.getString("signNo").substring(7,11));
		    	    signName = rs.getString("signName")+Integer.parseInt(rs.getString("signNo").substring(7,11));
    	        }else{
		    	    signDesc = rs.getString("signDesc")+Integer.parseInt(rs.getString("signNo").substring(7,11))+"-"+Integer.parseInt(rs.getString("signNo").substring(11));
		    	    signName = rs.getString("signName")+Integer.parseInt(rs.getString("signNo").substring(7,11))+"-"+Integer.parseInt(rs.getString("signNo").substring(11));
    	        }
	    	//建物
    	    }else{
    	        if(rs.getString("signNo").substring(12).equals("000")){
		    	    signDesc = rs.getString("signDesc")+Integer.parseInt(rs.getString("signNo").substring(7,12));
		    	    signName = rs.getString("signName")+Integer.parseInt(rs.getString("signNo").substring(7,12));
    	        }else{
		    	    signDesc = rs.getString("signDesc")+Integer.parseInt(rs.getString("signNo").substring(7,12))+"-"+Integer.parseInt(rs.getString("signNo").substring(12));
		    	    signName = rs.getString("signName")+Integer.parseInt(rs.getString("signNo").substring(7,12))+"-"+Integer.parseInt(rs.getString("signNo").substring(12));
    	        }
    	    }
			countSum[i-1] = count;
			taxAmountSum[i-1] = taxAmountTotal;
			signNoSum[i-1] = signNo;
			signDescSum[i-1] = signDesc;
			signNameSum[i-1] = signName;
    	}
    	rs.close();
		for(int j=0; j<countSumTotal; j++){
		    Object[] data = new Object[6];
        	data[0] = signNoSum[j];
    	    data[1] = todayDate;
    	    data[2] = "本市稅捐稽徵處鹽埕分處檢附補徵"+signDescSum[j]+signType+"等"+countSum[j]+"筆，"+Integer.parseInt(Datetime.getYYY())+"至"+Integer.parseInt(Datetime.getYYY())+"年"+taxKindType+"繳款書共計"+Common.valueFormat(taxAmountSum[j]+"")+"元，擬請准予支付，請  核示。";
    	    data[3] = "依據本市稅捐稽徵處鹽埕分處"+todayDate+"高市稽鹽地字第"+Datetime.getYYY()+"00000000號函辦理。";
    	    data[4] = "旨揭擬補徵之本"+kindTypeName+"為本市"+signNameSum[j]+signType+kindTypeName+Integer.parseInt(Datetime.getYYY())+"至"+Integer.parseInt(Datetime.getYYY())+"年"+taxKindType+"款，共計"+Common.valueFormat(taxAmountSum[j]+"")+"元整（計５份繳款書）。";
    	    data[5] = "奉核後，由本局第四科非公用財產項下業務費，稅金項下支付"+Common.valueFormat(taxAmountSum[j]+"")+"元整"+taxKindType+"款，文存查。";
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

//年度格式
public static String yearFormat(String money){
    String formatString = new String();
    DecimalFormat df = new DecimalFormat("000");
    if(money!=null && !money.equals("")){
        try{
            formatString = df.format(Double.parseDouble(money));
        }catch (NumberFormatException e) {
            formatString =money;
     }
    }else{
        formatString =money;
    }
    return formatString;
}  

//月格式
public static String monthFormat(String money){
    String formatString = new String();
    DecimalFormat df = new DecimalFormat("00");
    if(money!=null && !money.equals("")){
        try{
            formatString = df.format(Double.parseDouble(money));
        }catch (NumberFormatException e) {
            formatString =money;
     }
    }else{
        formatString =money;
    }
    return formatString;
}  

}
