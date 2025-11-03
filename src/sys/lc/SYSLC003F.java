/*
*<br>程式目的：公告地價現值批次轉入 
*<br>程式代號：
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.lc;

import util.Datetime;
import util.QueryBean;

public class SYSLC003F	 extends QueryBean{

	String tranYYYMM;
	String tranType;
	String bulletinDate;

	String fileName;
	
	public String getFileName() { return checkGet(fileName); }
	public void setFileName(String s) { fileName = checkSet(s); }
	
	public String getTranYYYMM() { return checkGet(tranYYYMM); }
	public void setTranYYYMM(String s) { tranYYYMM = checkSet(s); }
	public String getTranType() { return checkGet(tranType); }
	public void setTranType(String s) { tranType = checkSet(s); }
	public String getBulletinDate() { return checkGet(bulletinDate); }
	public void setBulletinDate(String s) { bulletinDate = checkSet(s); }
	

	
//	傳回update sql
	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[4];
		try{
//			Common.callSQLFile(getFileName()+"\\syslc003f.sql "+getTranType()+" "+getBulletinDate()+" "+getTranYYYMM()+" "+getUserID());
			
			execSQLArray[0]=" delete UNTLA_VALUE "+
						" where '"+getTranType()+"' in ('1','3') "+
						
						" and bulletindate='"+Datetime.changeTaiwanYYMM(getBulletinDate(), "2")+"' "+
						" and (enterorg+ownership+differencekind+propertyno+serialno) in "+ 
						" ( "+
						" select distinct a.enterorg+a.ownership+a.differencekind+a.propertyno+a.serialno "+
						" from UNTLA_LAND a,SYSLC_LAND b "+
						" where '"+getTranType()+"' in ('1','3') "+
						" and b.tranyyymm='"+Datetime.changeTaiwanYYMM(getTranYYYMM(), "2")+"'"+
						" and a.signno=b.lsignno1+b.lsignno2+b.lsignno3+b.lsignno4 "+
						" )";
			
			execSQLArray[1]=" delete UNTLA_PRICE "+
						" where '"+getTranType()+"' in ('2','3') "+
						
						" and bulletindate='"+Datetime.changeTaiwanYYMM(getBulletinDate(), "2")+"' "+
						" and (enterorg+ownership+differencekind+propertyno+serialno) in "+ 
						" ( "+
						" select distinct a.enterorg+a.ownership+a.differencekind+a.propertyno+a.serialno "+
						" from UNTLA_LAND a,SYSLC_LAND b "+
						" where '"+getTranType()+"' in ('2','3') "+
						" and b.tranyyymm='"+Datetime.changeTaiwanYYMM(getTranYYYMM(), "2")+"'"+
						" and a.signno=b.lsignno1+b.lsignno2+b.lsignno3+b.lsignno4 "+
						" )";;
			
			execSQLArray[2]=" insert into UNTLA_VALUE(enterorg,ownership,differencekind,propertyno,serialno,bulletindate,valueunit,suitdates,suitdatee,editid,editdate,edittime) "+
			
						" select distinct a.enterorg,a.ownership,a.differencekind,a.propertyno,a.serialno,'"+Datetime.changeTaiwanYYMM(getBulletinDate(), "2")+"',isnull(b.valueunit,0),c.suitdates,c.suitdatee,'"+getUserID()+"', "+
//						" select distinct a.enterOrg,a.ownership,a.propertyNo,a.serialNo,b.valuedate,nvl(b.valueUnit,0),c.suitDateS,c.suitDateE,'"+getUserID()+"', "+
						
						" '"+Datetime.getYYYYMMDD()+"', "+
						" '"+getEditTime()+"'"+
						" from UNTLA_LAND a,SYSLC_LAND b,UNTLA_BULLETINDATE c "+ 
						" where '"+getTranType()+"' in ('1','3') "+
						" and c.bulletinkind='1' "+
						
						" and c.bulletinDate='"+Datetime.changeTaiwanYYMM(getBulletinDate(), "2")+"' "+
						" and c.bulletinDate=b.valueDate "+
						
						" and b.tranYYYMM='"+Datetime.changeTaiwanYYMM(getTranYYYMM(), "2")+"' "+
						" and a.signNo=b.lsignNo1+b.lsignNo2+b.lsignNo3+b.lsignNo4 ";
			
			execSQLArray[3]=" insert into UNTLA_PRICE(enterorg,ownership,differencekind,propertyno,serialno,bulletindate,priceunit,suitdates,suitdatee,editid,editdate,edittime) "+
			
						" select distinct a.enterorg,a.ownership,a.differencekind,a.propertyno,a.serialno,'"+Datetime.changeTaiwanYYMM(getBulletinDate(), "2")+"',isnull(b.priceunit,0),c.suitdates,c.suitdatee,'"+getUserID()+"', "+
//						" select distinct a.propertyNo,a.serialNo,b.pricedate,nvl(b.priceUnit,0),c.suitDateS,c.suitDateE,'"+getUserID()+"', "+
						" '"+Datetime.getYYYYMMDD()+"', "+
						" '"+getEditTime()+"'"+
						" from UNTLA_LAND a,SYSLC_LAND b,UNTLA_BULLETINDATE c "+ 
						" where '"+getTranType()+"' in ('2','3') "+
						" and c.bulletinkind='2' "+
						
						" and c.bulletindate='"+Datetime.changeTaiwanYYMM(getBulletinDate(), "2")+"' "+
//						" and c.bulletinDate=b.priceDate "+
						" and b.tranyyymm='"+Datetime.changeTaiwanYYMM(getTranYYYMM(), "2")+"' "+
						" and a.signno=b.lsignno1+b.lsignno2+b.lsignno3+b.lsignno4 ";

		}catch(Exception x){
			x.printStackTrace();
		}
//		execSQLArray[0]=" select count(*) as checkResult from SYSOT_Organ_Cvt where 1=1 ";  //無作用,return用
		return execSQLArray;
	}
}


