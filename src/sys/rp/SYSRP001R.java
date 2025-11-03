/*
*<br>程式目的：財產資料清查作業
*<br>程式代號：SYSRP001R
*<br>程式日期：
*<br>程式作者：
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package sys.rp;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import util.*;

public class SYSRP001R extends SuperBean{

	private String[] q_enterorgs;
	public String[] getQ_enterorgs() { return q_enterorgs; }
	public void setQ_enterorgs(String[] q_enterorgs) { this.q_enterorgs = q_enterorgs; }

	private String q_differenceKind;
	public String getQ_differenceKind() { return checkGet(q_differenceKind); }
	public void setQ_differenceKind(String q_differenceKind) { this.q_differenceKind = checkSet(q_differenceKind); }

String fileName;
public String getFileName() {return checkGet(fileName);}
public void setFileName(String fileName) {this.fileName = checkSet(fileName);}


public SYSRP001R(){	
	this.setFileName("SYSRP001R");
}

public File exportExcel() throws Exception{	
	Database db = new Database();
	ResultSet rs = null; 
	File outputFile = null;
	int i = 0, j=0;	
	try {		
		String querySql=this.getExportSQL();
		rs = db.querySQL_NoChange(querySql);
		ResultSetMetaData rsmd = rs.getMetaData();

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("Sheet1");	
		HSSFRow row;
		HSSFCell cell;
		
		if(rsmd.getColumnCount() !=0){
			
			//取得欄位名稱
			row = sheet1.createRow(i++);
			for (j=0;j<rsmd.getColumnCount();j++) {
				cell = row.createCell(j);
				cell.setCellValue(rsmd.getColumnName(j+1));
			}
			//填入資料
			while (rs.next()){
				row = sheet1.createRow(i++);				
				for (j=0;j<rsmd.getColumnCount();j++) {
					cell = row.createCell(j);
					cell.setCellValue(rs.getString(j+1));			
				}
			}
			
			outputFile = exportToFile(wb, this.getFileName() +"_" + this.getUserID() + "_" + Datetime.getYYYMMDD() + Datetime.getHHMMSS() + ".xls");
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return outputFile;
}

private File exportToFile(Workbook workbook, String fileName) throws Exception {
	File outputFile = new File(Common.getTempDirectory(), fileName);
	FileOutputStream fos = null;
	try {
		fos = new FileOutputStream(outputFile);
		workbook.write(fos);
		fos.flush();
		return outputFile;
	} finally {
		if (fos != null) {
			try {
				fos.close();
			} catch (Exception e) {
				//ignore
			}
		}
	}	
}

/**
 * 產生EXCEL報表用SQL
 * @return SQL語法 
 */
private String getExportSQL(){
	String querySql="select "+
			" bm.enterorg as '機關(enterorg)', bm.ownership as '權屬(ownership)', bm.propertyno as '財編(propertyno)', bm.serialno as '分號(serialno)', bm.verify as '已入帳(verify)'"+
		    " , bm.enterdate as '入帳日期(enterdate)'  , bm.buydate as '購入日期(buydate)'"+
		    " , bm.originalbv as '原始入帳-總價(originalbv)', bm.bookvalue as '總價(bookvalue)', bm.netvalue as '淨值(Netvalue)', bm.accumdepr as '目前-累計折舊(accumdepr)', bm.chkVal as '總價-淨值-累折'"+
		    " , bm.originallimityear as '原使用年限' , bm.otherlimityear as '調整後使用年限(月)'"+
		    " ,bm.nodeprset as '不折舊設定(nodeprset)' "+
		    " , bm.deprmethod as '目前-折舊方法(deprmethod)', bm.originaldeprmethod as '原始-折舊方法(originaldeprmethod)'"+
		    " , bm.deprpark as '目前-園區別(deprpark)' , bm.originaldeprpark as '原始-園區別(originaldeprpark)'"+
		    " , bm.deprunit as '目前-部門別(deprunit)' , bm.originaldeprunit as '原始-部門別(originaldeprunit)'"+
		    " , bm.deprunit1 as '目前-部門別單位(deprunit1)'  , bm.originaldeprunit1 as '原始-部門別單位(originaldeprunit1)'"+
		    " , bm.depraccounts as '目前-會科(depraccounts)' , bm.originaldepraccounts as '原始-會科(originaldepraccounts)'"+
		    " , bm.deprunitcb as '目前-依比例分攤(deprunitcb)'   , bm.originaldeprunitcb as '原始-依比例分攤(originaldeprunitcb)' "+
		    " , bm.depramount as '目前-應攤提折舊總額(depramount)' ,  bm.originaldepramount as '原始-應攤提折舊總額(originaldepramount)' "+
		    " , bm.accumdepr as '目前-累計折舊(accumdepr)' , bm.originalaccumdepr as '原始-累積折舊(originalaccumdepr)'"+
		    " , apportionmonth as '目前-攤提壽月(apportionmonth)' , bm.originalapportionmonth as '原始-攤提壽月(originalapportionmonth)'"+
		    " ,bm.monthdepr as '目前-月提折舊金額(monthdepr)' , bm.originalmonthdepr  as '原始-月提折舊金額(originalmonthdepr)'"+
		    " ,bm.monthdepr1 as '目前-月提折舊金額1(monthdepr1)' , bm.originalmonthdepr1  as '原始-月提折舊金額1(originalmonthdepr1)'"+
		    " , bm.scrapvalue as '目前-殘值(scrapvalue)' , bm.originalscrapvalue as '原始-殘值(originalscrapvalue)'"+
		    " , bm.apportionendym as '目前-截止年月(apportionendym)' , bm.originalapportionendym as '原始-截止年月(originalapportionendym)'"+
			" from ("+
			" select * from ("+
		    " select  enterorg , ownership, propertyno, serialno, verify"+
		    " , enterdate, buydate"+
		    " , originalbv, bookvalue,netvalue, accumdepr, (bookvalue - netvalue - accumdepr) as chkVal , originalaccumdepr"+
		    " , originallimityear, otherlimityear"+
		    " ,nodeprset"+
		    " , deprmethod, originaldeprmethod"+
		    " , deprpark, originaldeprpark"+
		    " , deprunit, originaldeprunit"+
		    " , deprunit1, originaldeprunit1"+
		    " , depraccounts, originaldepraccounts"+
		    " , deprunitcb, originaldeprunitcb"+
		    " , depramount,  originaldepramount"+
		    " , apportionmonth, originalapportionmonth"+
		    " ,monthdepr, originalmonthdepr"+
		    " ,monthdepr1, originalmonthdepr1"+
		    " , scrapvalue, originalscrapvalue"+
		    " , apportionendym, originalapportionendym"+										
		    " , (select count(*) from UNTDP_MONTHDEPR dp where enterorg = dp.enterorg and ownership = dp.ownership and differencekind = dp.differencekind and propertyno = dp.propertyno and serialno = dp.serialno) as dpCount	"+	
		    " from UNTBU_BUILDING where  1=1 " + this.getCondition(false) +
		    " ) as m where 1=1"+
		    " and (("+
			" isnull(deprpark, '') = '' "+
			" or isnull(originaldeprpark, '') = ''"+
			" or isnull(deprmethod, '') = ''"+
			" or isnull(originaldeprmethod, '') = ''"+
			" or isnull(deprunit, '') = ''"+
			" or isnull(originaldeprunit, '') = ''"+
			" or isnull(deprunit1 , '') = ''"+
			" or isnull(originaldeprunit1, '') = ''"+
			" or isnull(depraccounts, '') = ''"+
			" or isnull(originaldepraccounts, '') = ''"+
			" or isnull(apportionendym, '' ) = ''"+
			" or isnull(originalapportionendym, '' ) = ''"+
			" or isnull(nodeprset, '') = ''"+
			" or isnull(deprmethod, '') = ''"+
			" or isnull(originaldeprmethod, '')  = ''"+
			" or (verify = 'Y' and isnull(enterdate, '') = '')"+
			" or isnull(buydate, '') = ''"+
			" or isnull(deprunitcb, '') = ''"+
			" or isnull(originaldeprunitcb, '') = ''"+
			" or isnull(CONVERT(NVARCHAR(5),originallimityear), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(5),otherlimityear), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),scrapvalue), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),originalscrapvalue), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),monthdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),originalmonthdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),monthdepr1), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),originalmonthdepr1), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),depramount), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),originaldepramount), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),accumdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),originalaccumdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),apportionmonth), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),originalapportionmonth), '') = ''"+
			" )"+
			" or  (chkVal != 0 or (m.dpCount > 0 and m.accumdepr <= 0))"+
			" )"+
			" union all"+
			" select * from ("+
		 	" select  enterorg , ownership, propertyno, serialno, verify"+
		    " , enterdate, buydate"+
		    " , originalbv, bookvalue,netvalue, accumdepr, (bookvalue - netvalue - accumdepr) as chkVal , originalaccumdepr"+
		    " , originallimityear, otherlimityear"+
		    " ,nodeprset"+
		    " , deprmethod, originaldeprmethod"+
		    " , deprpark, originaldeprpark"+
		    " , deprunit, originaldeprunit"+
		    " , deprunit1, originaldeprunit1"+
		    " , depraccounts, originaldepraccounts"+
		    " , deprunitcb, originaldeprunitcb"+
		    " , depramount,  originaldepramount"+
		    " , apportionmonth, originalapportionmonth"+
		    " ,monthdepr, originalmonthdepr"+
		    " ,monthdepr1, originalmonthdepr1"+
		    " , scrapvalue, originalscrapvalue"+
		    " , apportionendym, originalapportionendym"+										
		    " , (select count(*) from UNTDP_MONTHDEPR dp where enterorg = dp.enterorg and ownership = dp.ownership and differencekind = dp.differencekind and propertyno = dp.propertyno and serialno = dp.serialno) as dpCount	"+				
		    " from UNTRF_ATTACHMENT where  1=1 " + this.getCondition(false) +
		    " ) as m where 1=1"+
		    " and (("+																	
			" isnull(deprpark, '') = '' "+																	
			" or isnull(originaldeprpark, '') = ''"+																	
			" or isnull(deprmethod, '') = ''	"+																
			" or isnull(originaldeprmethod, '') = ''"+																	
			" or isnull(deprunit, '') = ''	"+																
			" or isnull(originaldeprunit, '') = ''"+																	
			" or isnull(deprunit1 , '') = ''"+																	
			" or isnull(originaldeprunit1, '') = ''"+																	
			" or isnull(depraccounts, '') = ''"+																	
			" or isnull(originaldepraccounts, '') = ''"+																	
			" or isnull(apportionendym, '' ) = ''	"+																
			" or isnull(originalapportionendym, '' ) = ''"+																	
			" or isnull(nodeprset, '') = ''"+																	
			" or isnull(deprmethod, '') = ''"+																	
			" or isnull(originaldeprmethod, '')  = ''"+																	
			" or (verify = 'Y' and isnull(enterdate, '') = '')"+															
			" or isnull(buydate, '') = ''"+
			" or isnull(deprunitcb, '') = ''"+																	
			" or isnull(originaldeprunitcb, '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(5),originallimityear), '') = ''"+																
			" or isnull(CONVERT(NVARCHAR(5),otherlimityear), '') = ''"+					
			" or isnull(CONVERT(NVARCHAR(15),scrapvalue), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),originalscrapvalue), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),monthdepr), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),originalmonthdepr), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),monthdepr1), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),originalmonthdepr1), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),depramount), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),originaldepramount), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),accumdepr), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),originalaccumdepr), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),apportionmonth), '') = ''"+																	
			" or isnull(CONVERT(NVARCHAR(15),originalapportionmonth), '') = ''"+																	
			" )"+ 
			" or  (chkVal != 0 or (m.dpCount > 0 and m.accumdepr <= 0))"+
			" )"+																													
			" union all"+											
			" select * from ("+							
		    " select a.enterorg , a.ownership, a.propertyno, a.serialno, a.verify"+
		    " , a.enterdate, b.buydate"+
		    " , a.originalbv, a.bookvalue,a.netvalue, a.accumdepr, (a.bookvalue - a.netvalue - accumdepr) as chkVal , a.originalaccumdepr"+
		    " , a.originallimityear, a.otherlimityear"+
		    " ,a.nodeprset"+
		    " , a.deprmethod, a.originaldeprmethod"+
		    " , a.deprpark, a.originaldeprpark"+
		    " , a.deprunit, a.originaldeprunit"+
		    " , a.deprunit1, a.originaldeprunit1"+
		    " , a.depraccounts, a.originaldepraccounts"+
		    " , a.deprunitcb, a.originaldeprunitcb"+
		    " , a.depramount,  a.originaldepramount"+
		    " , apportionmonth, a.originalapportionmonth"+
		    " ,a.monthdepr, a.originalmonthdepr"+
		    " ,a.monthdepr1, a.originalmonthdepr1"+
		    " , a.scrapvalue, a.originalscrapvalue"+
		    " , a.apportionendym, a.originalapportionendym"+
		    " , (select count(*) from UNTDP_MONTHDEPR dp where a.enterorg = dp.enterorg and a.ownership = dp.ownership and a.differencekind = dp.differencekind and a.propertyno = dp.propertyno and a.serialno = dp.serialno) as dpCount"+
		    " from UNTMP_MOVABLEDETAIL a left join UNTMP_MOVABLE  b"+
		    " on a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.lotno = b.lotno"+
		    " where  1=1 " + this.getCondition(true) +
		    " ) as m where "+
		    "  (("+
			" isnull(m.deprpark, '') = '' "+
			" or isnull(m.originaldeprpark, '') = ''"+
			" or isnull(m.deprmethod, '') = ''"+
			" or isnull(m.originaldeprmethod, '') = ''"+
			" or isnull(m.deprunit, '') = ''"+
			" or isnull(m.originaldeprunit, '') = ''"+
			" or isnull(m.deprunit1 , '') = ''"+
			" or isnull(m.originaldeprunit1, '') = ''"+
			" or isnull(m.depraccounts, '') = ''"+
			" or isnull(m.originaldepraccounts, '') = ''"+
			" or isnull(m.apportionendym, '' ) = ''"+
			" or isnull(m.originalapportionendym, '' ) = ''"+
			" or isnull(m.nodeprset, '') = ''"+
			" or isnull(m.deprmethod, '') = ''"+
			" or isnull(m.originaldeprmethod, '')  = ''"+
			" or (m.verify = 'Y' and isnull(m.enterdate, '') = '')"+	
			" or isnull(m.buydate, '') = ''"+
			" or isnull(m.deprunitcb, '') = ''"+
			" or isnull(m.originaldeprunitcb, '') = ''"+
			" or isnull(CONVERT(NVARCHAR(5),m.originallimityear), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(5),m.otherlimityear), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.scrapvalue), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.originalscrapvalue), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.monthdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.originalmonthdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.monthdepr1), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.originalmonthdepr1), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.depramount), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.originaldepramount), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.accumdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.originalaccumdepr), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.apportionmonth), '') = ''"+
			" or isnull(CONVERT(NVARCHAR(15),m.originalapportionmonth), '') = ''"+
			" ) or (chkVal != 0 or (m.dpCount > 0 and m.accumdepr <= 0)))"+
		" ) as bm"+
		" order by bm.enterorg, bm.ownership , bm.propertyno, bm.serialno , bm.verify"+
		";";
	return querySql;
}

	private String getCondition(boolean isMP) {
		
		StringBuilder enterorgs = new StringBuilder();
		StringBuilder condition = new StringBuilder();
		if (isMP) {
			if (!"".equals(this.getQ_differenceKind())) {
				condition.append(" and a.differencekind = ").append(Common.sqlChar(this.getQ_differenceKind()));
			}
			
			
			if (this.getQ_enterorgs() != null && this.getQ_enterorgs().length > 0) {
				for (String tmpEnterorg : this.getQ_enterorgs()) {
					if (enterorgs.length() > 0) {
						enterorgs.append(",");
					}
					enterorgs.append(Common.sqlChar(tmpEnterorg));
				}
				
				if (enterorgs.length() > 0) {
					condition.append("  and a.enterorg in (").append(enterorgs.toString()).append(") ");
				}
				
			} else {
				condition.append(" and a.enterorg in ( " + Common.sqlChar(TCGHCommon.getSYSCODEName("02", "ETO")) + ", " + Common.sqlChar(TCGHCommon.getSYSCODEName("03", "ETO")) + ", " + Common.sqlChar(TCGHCommon.getSYSCODEName("04", "ETO")) + ") ");
			}
			condition.append(" and a.datastate  = '1' and b.valuable = 'N' ");
			
		} else {
			if (!"".equals(this.getQ_differenceKind())) {
				condition.append(" and differencekind = ").append(Common.sqlChar(this.getQ_differenceKind()));
			} 
			
			if (this.getQ_enterorgs() != null && this.getQ_enterorgs().length > 0) {
				for (String tmpEnterorg : this.getQ_enterorgs()) {
					if (enterorgs.length() > 0) {
						enterorgs.append(",");
					}
					enterorgs.append(Common.sqlChar(tmpEnterorg));
				}
				if (enterorgs.length() > 0) {
					condition.append("  and enterorg in (").append(enterorgs.toString()).append(") ");
				}
			} else {
				condition.append(" and enterorg in (" + Common.sqlChar(TCGHCommon.getSYSCODEName("02", "ETO")) + ", " + Common.sqlChar(TCGHCommon.getSYSCODEName("03", "ETO")) + ", " + Common.sqlChar(TCGHCommon.getSYSCODEName("04", "ETO")) + ") ");
			}
			condition.append(" and datastate  = '1' and valuable = 'N' ");
		}
		return condition.toString();
		
	}

}