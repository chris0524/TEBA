/*
*<br>程式目的：單位財產資料轉入作業－檢視錯誤情形
*<br>程式代號：untup003f
*<br>程式日期：0950710
*<br>程式作者：clive.chang
*<br>--------------------------------------------------------
*<br>修改作者　　修改日期　　　修改目的
*<br>--------------------------------------------------------
*/

package unt.up;

import java.sql.ResultSet;

import util.Common;
import util.Database;

public class UNTUP003F extends UNTUP000Q{

String textSerialNo;
String errorColumn;
public String getTextSerialNo(){ return checkGet(textSerialNo); }
public void setTextSerialNo(String s){textSerialNo=checkSet(s);}
public String getErrorColumn() { return checkGet(errorColumn); }
public void setErrorColumn(String s){errorColumn=checkSet(s);}
public String getUploadTypeName(String s) {
	return getUploadType(s);
}

public String getErrorColumnList(String upType, String enterOrg) throws Exception{
	Database db = new Database();	
	StringBuffer sbHTML = new StringBuffer("");	
	try {
		sbHTML.append("<thead id=\"listTHEAD\">");
		sbHTML.append("<tr>\n");
		sbHTML.append("	<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\">列號</a><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',1,false);\" href=\"#\"></a></th>\n");
		sbHTML.append("	<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',2,false);\" href=\"#\">財產編號</a></th>\n");
		sbHTML.append("	<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',3,false);\" href=\"#\">錯誤</a></th>\n");
		sbHTML.append("	<th class=\"listTH\"><a class=\"text_link_w\" onClick=\"return sortTable('listTBODY',4,false);\" href=\"#\">錯誤說明</a></th>\n");
		if (upType.equals("VP")||upType.equals("RT")) {
			sbHTML.append("	<th class=\"listTH\"><a class=\"text_link_w\" >明細錯誤</a></th>\n");			
		}		
		sbHTML.append("</thead>\n");
		sbHTML.append("<tbody id=\"listTBODY\">");		
		
		String sql = "select enterOrg, textSerialNo, oldPropertyNo, decode(isErr,'Y','Y','N') as isErr, " +
					 //" substr(errorColumn,0,30) as errorColumn " +
					 " errorColumn " +
					 " from " + getUploadTable(upType) + " where enterOrg="+Common.sqlChar(enterOrg)+ " and isErr='Y' order by textSerialNo";		
		if (upType.equals("VP")||upType.equals("RT")){
			sql = "select distinct a.enterOrg, a.ownership, a.textSerialNo, a.propertyKind, a.enterDate, a.adjustType, a.fundType, a.oldPropertyNo, a.oldSerialNo, a.isErr, " +
				  //" substr(a.errorColumn,0,30) as errorColumn " +
				  " a.errorColumn " +
				  " from " + getUploadTable(upType) + " a, " + getUploadDtlTable(upType) + " b where a.enterOrg="+Common.sqlChar(enterOrg)+
				  " and a.enterOrg=b.enterOrg(+) and a.ownership=b.ownership(+) " +
				  " and a.propertyKind=b.propertyKind(+) " +
				  " and nvl(a.fundType,'99')=nvl(b.fundType(+),'99') " +
				  " and a.enterDate=b.enterDate(+) " +
				  " and a.adjustType=b.adjustType(+) " +							
				  " and a.oldPropertyNo=b.oldPropertyNo(+) " +
				  " and a.oldSerialNo = b.oldSerialNo(+) " +			
				  " and (a.isErr='Y' or b.isErr='Y') order by a.textSerialNo";	
		}
		ResultSet rs = db.querySQL(sql, true);				
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end) break;
								
				StringBuffer strLink = new StringBuffer(20).append(Common.sqlChar(rs.getString("enterOrg")) ).append( "," ).append(
					Common.sqlChar(rs.getString("textSerialNo"))).append(",").append(Common.sqlChar(getUploadTable(upType)));
				sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(").append( strLink ).append( ");masterFlag=true;dtlFlag=false;\" >");
				sbHTML.append(" <td class='listTD' >").append(rs.getString("textSerialNo")).append(".</td> ");
				sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("oldpropertyNo"))).append("</td> ");
				sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("isErr"))).append("</td> ");
				sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("errorColumn"))).append("</td> ");
				if (upType.equals("VP")||upType.equals("RT")) {
					sbHTML.append(" <td class='listTD' >");					
					sql = "select enterOrg, ownership, textSerialNo from " + getUploadDtlTable(upType) + " where enterOrg="+Common.sqlChar(enterOrg)+ " and isErr='Y' " +
						" and ownership=" + Common.sqlChar(Common.get(rs.getString("ownership"))) +
						" and propertyKind=" + Common.sqlChar(Common.get(rs.getString("propertyKind"))) +
						" and nvl(fundType,'99')=nvl(" + Common.sqlChar(Common.get(rs.getString("fundType"))) + ",'99') " +
						" and enterDate=" + Common.sqlChar(Common.get(rs.getString("enterDate"))) +
						" and adjustType=" + Common.sqlChar(Common.get(rs.getString("adjustType"))) +							
						" and oldPropertyNo=" + Common.sqlChar(Common.get(rs.getString("oldPropertyNo"))) +
						" and oldSerialNo =" + Common.sqlChar(Common.get(rs.getString("oldSerialNo"))) + " order by textSerialNo ";
					ResultSet rc = db.querySQL(sql);
					while (rc.next()) {
						strLink = new StringBuffer(20).append(Common.sqlChar(rc.getString("enterOrg")) ).append( "," ).append(
								Common.sqlChar(rc.getString("textSerialNo"))).append(",").append(Common.sqlChar(getUploadDtlTable(upType)));						
						sbHTML.append(" <a href='#' onClick=\"queryOne(").append(strLink).append(");masterFlag=false;dtlFlag=true;\">明細列號:").append(rc.getString("textSerialNo")).append("</a><br>");						
					}
					rc.getStatement().close();
					rc.close();
					sbHTML.append(" </td>\n");
				}				
				sbHTML.append(" </tr> ");
				count++;
			} while (rs.next());
		}
		rs.getStatement().close();
		rs.close();
		
		sbHTML.append("</tbody>\n");		
		
		int intRecordPass = 0, intRecordErr = 0;		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from " + getUploadTable(upType) + " where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");			
		}
		rs.getStatement().close();
		rs.close();
		setErrorRecordCount(intRecordErr);
		setSuccessRecordCount(intRecordPass);		
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}

public String getErrorColumnListBak(String upType, String enterOrg) throws Exception{
	Database db = new Database();	
	StringBuffer sbHTML = new StringBuffer("");	
	try {		
		ResultSet rs = db.querySQL("select enterOrg, textSerialNo, oldpropertyNo, isErr, " +
								   //" substr(errorColumn,0,30) as errorColumn " +
								   " errorColumn " +
								   " from " + getUploadTable(upType) + " where enterOrg="+Common.sqlChar(enterOrg)+ " and isErr='Y' order by textSerialNo", true);				
		processCurrentPageAttribute(rs);
		if (getTotalRecord() > 0) {
			int count = getRecordStart();
			int end = getRecordEnd();
			do {
				if (count > end)
					break;
				StringBuffer strLink = new StringBuffer(20).append(Common.sqlChar(rs.getString("enterOrg")) ).append( "," ).append(
					Common.sqlChar(rs.getString("textSerialNo"))).append(",").append(Common.sqlChar(getUploadTable(upType)));				
				sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryOne(").append( strLink ).append( ")\" >");
				sbHTML.append(" <td class='listTD' >").append(rs.getString("textSerialNo")).append(".</td> ");
				sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("oldpropertyNo"))).append("</td> ");
				sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("isErr"))).append("</td> ");
				sbHTML.append(" <td class='listTD' >").append(Common.get(rs.getString("errorColumn"))).append("</td> ");
				sbHTML.append(" </tr> ");
				count++;
			} while (rs.next());
		}
		rs.getStatement().close();
		rs.close();
		
		int intRecordPass = 0, intRecordErr = 0;		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from " + getUploadTable(upType) + " where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");			
		}
		rs.getStatement().close();
		rs.close();
		setErrorRecordCount(intRecordErr);
		setSuccessRecordCount(intRecordPass);		
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}

public UNTUP003F  queryOne(String upType, String enterOrg, String textSerialNo) throws Exception{
	Database db = new Database();
	UNTUP003F obj = this;
	try {
		String sql="select enterOrg, textSerialNo, isErr, errorColumn, editID, editDate from " + getUploadTable(upType)+ " where enterOrg="+Common.sqlChar(enterOrg) + " and textSerialNo="+Common.sqlChar(textSerialNo);
		ResultSet rs = db.querySQL(sql);
		if (rs.next()){
			obj.setTextSerialNo(rs.getString("textSerialNo"));
			obj.setErrorColumn(rs.getString("errorColumn"));			
			obj.setEditID(rs.getString("editID"));
			obj.setEditDate(rs.getString("editDate"));
		}
		rs.getStatement().close();
		rs.close();
		setStateQueryOneSuccess();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return obj;
}

public void doTransferProcess(String upType, String enterOrg, String modifier) {
	Database db = new Database();
	try {
		int intRecordPass = 0, intRecordErr = 0;
		ResultSet rs = db.querySQL(" select decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from " + getUploadTable(upType) + " where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by decode(isErr,'Y','Y','N') ");
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");
		}		
		rs.getStatement().close();
		rs.close();
		
		if (upType.equals("VP") || upType.equals("RT")) {
			rs = db.querySQL(" select decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from " + getUploadDtlTable(upType) + " where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by decode(isErr,'Y','Y','N') ");
			while (rs.next()) {
				if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = intRecordErr + rs.getInt("recCount");
				else intRecordPass = intRecordPass + rs.getInt("recCount");
			}
			rs.getStatement().close();
			rs.close();			
		}
		
		if (intRecordPass>0 && intRecordErr==0) {
			switch(getProcessType(upType)) {
				case 1:
					TransferLA la = new TransferLA();
					la.setEditID(modifier);
					la.doLandTransfer(enterOrg);
					setErrorMsg(la.getErrorMsg());
					break;
				case 2: //建物
					TransferBU bu = new TransferBU();
					bu.setEditID(modifier);					
					bu.doBuildingTransfer(enterOrg);
					setErrorRecordCount(bu.getErrorRecordCount());
					setSuccessRecordCount(bu.getSuccessRecordCount());
					setErrorMsg(bu.getErrorMsg());						
					break;
				case 3: //土地改良物
					TransferRF rf = new TransferRF();
					rf.setEditID(modifier);					
					rf.doRFTransfer(enterOrg);
					setErrorRecordCount(rf.getErrorRecordCount());
					setSuccessRecordCount(rf.getSuccessRecordCount());
					setErrorMsg(rf.getErrorMsg());						
					break;
				case 4: //動產 
					TransferMP mp = new TransferMP();
					mp.doMovableTransfer(enterOrg);
					mp.setEditID(modifier);
					setErrorRecordCount(mp.getErrorRecordCount());
					setSuccessRecordCount(mp.getSuccessRecordCount());
					setErrorMsg(mp.getErrorMsg());					
					break;
				case 5://有價證券
					TransferVP vp = new TransferVP();
					vp.doVPTransfer(enterOrg);
					vp.setEditID(modifier);
					setErrorRecordCount(vp.getErrorRecordCount());
					setSuccessRecordCount(vp.getSuccessRecordCount());
					setDtlErrorRecordCount(vp.getErrorRecordCount());
					setDtlSuccessRecordCount(vp.getSuccessRecordCount());					
					setErrorMsg(vp.getErrorMsg());						
					break;
				
				case 6://權利
					TransferRT rt = new TransferRT();
					rt.doRTTransfer(enterOrg);
					rt.setEditID(modifier);
					setErrorRecordCount(rt.getErrorRecordCount());
					setSuccessRecordCount(rt.getSuccessRecordCount());
					setDtlErrorRecordCount(rt.getErrorRecordCount());
					setDtlSuccessRecordCount(rt.getSuccessRecordCount());
					setErrorMsg(rt.getErrorMsg());
					break;
			}
		} else {
			setErrorMsg("對不起，只有在合格筆數>0且不合格筆數=0時方可執行此動作！");
		}
	} catch (Exception e) {
		setErrorMsg("對不起，發生未預期的錯誤，請重新執行！");
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
}




}
