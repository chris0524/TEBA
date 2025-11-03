/*
*<br>程式目的：單位財產資料轉入作業－檢視轉入結果
*<br>程式代號：untup002f
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
import util.SuperBean;

public class UNTUP002F extends SuperBean{

String q_enterOrg;
String q_enterOrgName;
String enterOrg;
String enterOrgName;

public String getQ_enterOrg(){ return checkGet(q_enterOrg); }
public void setQ_enterOrg(String s){ q_enterOrg=checkSet(s); }
public String getQ_enterOrgName(){ return checkGet(q_enterOrgName); }
public void setQ_enterOrgName(String s){ q_enterOrgName=checkSet(s); }   

public String getEnterOrg(){ return checkGet(enterOrg); }
public void setEnterOrg(String s){ enterOrg=checkSet(s); }
public String getEnterOrgName(){ return checkGet(enterOrgName); }
public void setEnterOrgName(String s){ enterOrgName=checkSet(s); }


public String getUNTUP_List(String enterOrg) {
	StringBuffer sbHTML = new StringBuffer(500);
	Database db = new Database();
	try {
		int intRecordPass = 0, intRecordErr = 0;
		int count = 1;
		String editID = "", editDate = "";
		
		ResultSet rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_Land where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();		
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryDetail('").append( Common.get(enterOrg) ).append("','LA');\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >土地</td> ");
		sbHTML.append(" <td class='listTD' >").append(editDate).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(intRecordPass).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(intRecordErr).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(editID).append("</td> ");
		sbHTML.append(" </tr> ");
		
		intRecordPass = 0;
		intRecordErr = 0;
		count = 2;
		editID = "";
		editDate = "";		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_Building where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryDetail('").append( Common.get(enterOrg) ).append("','BU');\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >建物</td> ");
		sbHTML.append(" <td class='listTD' >").append(editDate).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(intRecordPass).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(intRecordErr).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(editID).append("</td> ");
		sbHTML.append(" </tr> ");
		
		intRecordPass = 0;
		intRecordErr = 0;
		count = 3;
		editID = "";
		editDate = "";		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_Attachment where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();		
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryDetail('").append( Common.get(enterOrg) ).append("','RF');\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >土地改良物</td> ");
		sbHTML.append(" <td class='listTD' >").append(editDate).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(intRecordPass).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(intRecordErr).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(editID).append("</td> ");
		sbHTML.append(" </tr> ");		

		intRecordPass = 0;
		intRecordErr = 0;
		count = 4;
		editID = "";
		editDate = "";		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_Movable where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();		
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryDetail('").append( Common.get(enterOrg) ).append("','MP');\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >動產</td> ");
		sbHTML.append(" <td class='listTD' >").append(editDate).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(intRecordPass).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(intRecordErr).append("</td> ");		
		sbHTML.append(" <td class='listTD' >").append(editID).append("</td> ");
		sbHTML.append(" </tr> ");
		
		intRecordPass = 0;
		intRecordErr = 0;
		int intDtlRecordPass = 0;
		int intDtlRecordErr = 0;		
		count = 5;
		editID = "";
		editDate = "";		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_ValuePaper1 where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();
		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_ValuePaper2 where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intDtlRecordErr = rs.getInt("recCount");
			else intDtlRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryDetail('").append( Common.get(enterOrg) ).append("','VP');\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >有價證券</td> ");
		sbHTML.append(" <td class='listTD' >").append(editDate).append("</td> ");
		sbHTML.append(" <td class='listTD' >主檔：").append(intRecordPass).append("　明細：").append(intDtlRecordPass).append("</td> ");
		sbHTML.append(" <td class='listTD' >主檔：").append(intRecordErr).append("　明細：").append(intDtlRecordErr).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(editID).append("</td> ");
		sbHTML.append(" </tr> ");
		
		intRecordPass = 0;
		intRecordErr = 0;
		intDtlRecordPass = 0;
		intDtlRecordErr = 0;		
		count = 6;
		editID = "";
		editDate = "";		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_Right1 where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intRecordErr = rs.getInt("recCount");
			else intRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();
		
		rs = db.querySQL(" select editID, editDate, decode(isErr,'Y','Y','N') as isErr, count(*) as recCount from UNTUP_Right2 where enterOrg="+Common.sqlChar(Common.get(enterOrg)) + " group by editID, editDate, decode(isErr,'Y','Y','N') ");		
		while (rs.next()) {
			if (Common.get(rs.getString("isErr")).equals("Y")) intDtlRecordErr = rs.getInt("recCount");
			else intDtlRecordPass = rs.getInt("recCount");
			editID = Common.get(rs.getString("editID"));
			editDate = Common.get(rs.getString("editDate"));			
		}
		rs.getStatement().close();
		rs.close();
		sbHTML.append(" <tr class='listTR' onmouseover=\"this.className='listTRMouseover'\" onmouseout=\"this.className='listTR'\" onClick=\"queryDetail('").append( Common.get(enterOrg) ).append("','RT');\" >");
		sbHTML.append(" <td class='listTD' >").append(count).append(".</td> ");
		sbHTML.append(" <td class='listTD' >權利</td> ");
		sbHTML.append(" <td class='listTD' >").append(editDate).append("</td> ");
		sbHTML.append(" <td class='listTD' >主檔：").append(intRecordPass).append("　明細：").append(intDtlRecordPass).append("</td> ");
		sbHTML.append(" <td class='listTD' >主檔：").append(intRecordErr).append("　明細：").append(intDtlRecordErr).append("</td> ");
		sbHTML.append(" <td class='listTD' >").append(editID).append("</td> ");
		sbHTML.append(" </tr> ");

		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.closeAll();
	}
	return sbHTML.toString();
}

}
