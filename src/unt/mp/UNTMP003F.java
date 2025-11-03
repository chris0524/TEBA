///*
//*<br>程式目的：動產主檔資料維護－接收撥入動產資料
//*<br>程式代號：untmp003f
//*<br>程式日期：0941026
//*<br>程式作者：cherry
//*<br>--------------------------------------------------------
//*<br>修改作者　　修改日期　　　修改目的
//*<br>--------------------------------------------------------
//*/
//
//package unt.mp;
//
//import java.sql.ResultSet;
//import util.*;
//
//public class UNTMP003F extends UNTMP002F{
//
//String cause;
//String cause1;
//String checkEnterOrg;
//String newEnterOrg;
//String number;
//String addProofCaseNo;
//String oldEnterOrg;
//String oldEnterOrgName;
//
//public String getOldEnterOrg(){ return checkGet(oldEnterOrg); }
//public void setOldEnterOrg(String s){ oldEnterOrg=checkSet(s); }
//public String getOldEnterOrgName(){ return checkGet(oldEnterOrgName); }
//public void setOldEnterOrgName(String s){ oldEnterOrgName=checkSet(s); }
//public String getCause(){ return checkGet(cause); }
//public void setCause(String s){ cause=checkSet(s); }
//public String getCause1(){ return checkGet(cause1); }
//public void setCause1(String s){ cause1=checkSet(s); }
//public String getCheckEnterOrg(){ return checkGet(checkEnterOrg); }
//public void setCheckEnterOrg(String s){ checkEnterOrg=checkSet(s); }
//public String getNewEnterOrg(){ return checkGet(newEnterOrg); }
//public void setNewEnterOrg(String s){ newEnterOrg=checkSet(s); }
//public String getNumber(){ return checkGet(number); }
//public void setNumber(String s){ number=checkSet(s); }
//public String getAddProofCaseNo(){ return checkGet(addProofCaseNo); }
//public void setAddProofCaseNo(String s){ addProofCaseNo=checkSet(s); }
//
//String reduceDetailEnterOrg;
//String reduceDetailOwnership;
//String reduceDetailCaseNo;
//String reduceDetailPropertyNo;
//String reduceDetailLotNo;
//String reduceDetailAdjustBookAmount;
//String serialNoNumber;
//String adjustBookAmountAll;
//String adjustBookValueAll;
//String scrapValueAll;
//
//public String getReduceDetailEnterOrg(){ return checkGet(reduceDetailEnterOrg); }
//public void setReduceDetailEnterOrg(String s){ reduceDetailEnterOrg=checkSet(s); }
//public String getReduceDetailOwnership(){ return checkGet(reduceDetailOwnership); }
//public void setReduceDetailOwnership(String s){ reduceDetailOwnership=checkSet(s); }
//public String getReduceDetailCaseNo(){ return checkGet(reduceDetailCaseNo); }
//public void setReduceDetailCaseNo(String s){ reduceDetailCaseNo=checkSet(s); }
//public String getReduceDetailPropertyNo(){ return checkGet(reduceDetailPropertyNo); }
//public void setReduceDetailPropertyNo(String s){ reduceDetailPropertyNo=checkSet(s); }
//public String getReduceDetailLotNo(){ return checkGet(reduceDetailLotNo); }
//public void setReduceDetailLotNo(String s){ reduceDetailLotNo=checkSet(s); }
//public String getReduceDetailAdjustBookAmount(){ return checkGet(reduceDetailAdjustBookAmount); }
//public void setReduceDetailAdjustBookAmount(String s){ reduceDetailAdjustBookAmount=checkSet(s); }
//public String getSerialNoNumber(){ return checkGet(serialNoNumber); }
//public void setSerialNoNumber(String s){ serialNoNumber=checkSet(s); }
//public String getAdjustBookAmountAll(){ return checkGet(adjustBookAmountAll); }
//public void setAdjustBookAmountAll(String s){ adjustBookAmountAll=checkSet(s); }
//public String getAdjustBookValueAll(){ return checkGet(adjustBookValueAll); }
//public void setAdjustBookValueAll(String s){ adjustBookValueAll=checkSet(s); }
//public String getScrapValueAll(){ return checkGet(scrapValueAll); }
//public void setScrapValueAll(String s){ scrapValueAll=checkSet(s); }
//
//String propertyKind;
//public String getPropertyKind(){ return checkGet(propertyKind); }
//public void setPropertyKind(String s){ propertyKind=checkSet(s); }
//String fundType;
//public String getFundType(){ return checkGet(fundType); }
//public void setFundType(String s){ fundType=checkSet(s); }
////取得Movable批號
//protected void getMovableLotNo(){
//	Database db = new Database();	
//	UNTMP003F obj = this;
//	ResultSet rs;	
//	String sql="select (case max(lotNo) when null then '0' then max(lotNo) end)+1 as lotNo from UNTMP_Movable " +
//			" where enterOrg = " + Common.sqlChar(enterOrg) + 
//			" and ownership = " + Common.sqlChar(ownership) + 
//			" and propertyNo = " + Common.sqlChar(obj.getReduceDetailPropertyNo()) + 
//			"";	
////	System.out.println("取得批號UNTMP_Movable: \n"+sql+"\n");
//	try{
//		rs = db.querySQL(sql);
//		if (rs.next()){
//			setLotNo(Common.formatFrontZero(rs.getString("lotNo"),7));
//		} else {
//			setLotNo("0000001");
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		db.closeAll();
//	}
//
//}
//
////取得UNTMP_MovableDetail分號
//protected void getMovableDetailSerialNo(){
//	Database db = new Database();	
//	UNTMP003F obj = this;
//	ResultSet rs;	
//	String sql="select (case max(serialNo) when null then '0' else max(serialNo) end)+1 as serialNo from UNTMP_MovableDetail " +
//				" where enterOrg = " + Common.sqlChar(enterOrg) + 
//				" and ownership = " + Common.sqlChar(ownership) + 
//				" and propertyNo = " + Common.sqlChar(obj.getReduceDetailPropertyNo()) + 
//				"";		
////	System.out.println("取得UNTMP_MovableDetail分號: \n"+sql+"\n");
//	try{
//		rs = db.querySQL(sql);
//		if (rs.next()){
//			setSerialNo(Common.formatFrontZero(rs.getString("serialNo"),7));
//		} else {
//			setSerialNo("0000001");
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		db.closeAll();
//	}
//}
//
////抓出財產分號起-訖
//protected void getSerialNoSE(){
//	int serialNoNumber = 0;
//	UNTMP003F obj = this;
//	Database db = new Database();
//	ResultSet rs;	
//	String sql="select min(serialNo) as serialNoS, max(serialNo) as serialNoE ,count(*) as countSerial from UNTMP_ReduceDetail " +
//				" where enterOrg = " + Common.sqlChar(obj.getReduceDetailEnterOrg()) + 
//				" and ownership = " + Common.sqlChar(obj.getReduceDetailOwnership()) + 
//				" and caseNo = " + Common.sqlChar(obj.getReduceDetailCaseNo()) + 
//				" and propertyNo = " + Common.sqlChar(obj.getReduceDetailPropertyNo()) +
//				" and lotNo = " + Common.sqlChar(obj.getReduceDetailLotNo()) +
//				" and adjustBookAmount = " + Common.sqlChar(obj.getReduceDetailAdjustBookAmount()) +
//				" and newEnterOrg = " + Common.sqlChar(newEnterOrg) +
//				"";
////	System.out.println("抓出財產分號起-訖UNTMP_ReduceDetail: \n"+sql+"\n");
//	try{
//		rs = db.querySQL(sql);
//		if (rs.next()){
//			//serialNoNumber = Integer.parseInt(rs.getString("serialNoE"))-Integer.parseInt(rs.getString("serialNoS"))+1;
//			serialNoNumber = Integer.parseInt(rs.getString("countSerial"));
//			obj.setSerialNoNumber(serialNoNumber+"");
//			obj.setSerialNoS(rs.getString("serialNoS"));
//		} else {
//			obj.setSerialNoNumber(0+"");
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		db.closeAll();
//	}
//}
//
////查出UNTMP_ReduceDetail資料並新增入UNTMP_Movable
//protected void getInsertUntmpMovable(){
//	UNTMP003F obj = this;
//	Database db = new Database();
//	String strSQL = "";
//	String[] execSQLArray;
//	ResultSet rs;	
//	String sql="select * from UNTMP_ReduceDetail " +
//				" where enterOrg = " + Common.sqlChar(obj.getReduceDetailEnterOrg()) + 
//				" and ownership = " + Common.sqlChar(obj.getReduceDetailOwnership()) + 
//				" and caseNo = " + Common.sqlChar(obj.getReduceDetailCaseNo()) + 
//				" and propertyNo = " + Common.sqlChar(obj.getReduceDetailPropertyNo()) +
//				" and lotNo = " + Common.sqlChar(obj.getReduceDetailLotNo()) +
//				" and adjustBookAmount = " + Common.sqlChar(obj.getReduceDetailAdjustBookAmount()) +
//				" and newEnterOrg = " + Common.sqlChar(newEnterOrg) +
//				" and serialNo = " + Common.sqlChar(obj.getSerialNoS()) +
//				"";
////	System.out.println("查出要新增入 UNTMP_Movable 內的資料: \n"+sql+"\n");
//	try{
//		rs = db.querySQL(sql);
//		if (rs.next()){
//			//數量
//			int adjustBookAmountAll = Integer.parseInt(rs.getString("adjustBookAmount"))*Integer.parseInt(obj.getSerialNoNumber());
//			obj.setAdjustBookAmountAll(adjustBookAmountAll+"");
//			//總價
//			int adjustBookValueAll = Integer.parseInt(rs.getString("adjustBookValue"))*Integer.parseInt(obj.getSerialNoNumber());
//			obj.setAdjustBookValueAll(adjustBookValueAll+"");
//			//預留殘值
//			int scrapValueAll = Integer.parseInt((rs.getString("scrapValue")==null || "".equals(rs.getString("scrapValue"))?"0":rs.getString("scrapValue")))*Integer.parseInt(obj.getSerialNoNumber());
//			obj.setScrapValueAll(scrapValueAll+"");
//			//財產分號起訖
//			obj.setSerialNoS(obj.getSerialNo());
//			int setserialNoE = Integer.parseInt(obj.getSerialNo())+Integer.parseInt(obj.getSerialNoNumber())-1;
//			obj.setSerialNoE(Common.formatFrontZero((setserialNoE+""),7));
//			//將UNTMP_ReduceDetail資料新增入UNTMP_Movable
//			strSQL+=" insert into UNTMP_Movable( " +
//					" enterOrg,"+
//					" ownership,"+
//					" caseNo,"+
//					" propertyNo,"+
//					" lotNo,"+
//					" serialNoS,"+
//					" serialNoE," +
//					" otherPropertyUnit," +
//					" otherMaterial,"+
//					" otherLimitYear,"+
//					" propertyName1,"+
//					" cause," +
//					" cause1,"+
//					" enterDate,"+
//					" buyDate," +
//					" dataState," +
//					" verify," +
//					" closing," +
//					" propertyKind," +
//					" fundType," +
//					" valuable," +
//					" originalAmount," +
//					" originalUnit," +
//					" originalBV," +
//					" bookAmount," +
//					" bookValue," +
//					" articleName," +
//					" nameplate," +
//					" specification," +
//					" sourceKind," +
//					" sourceDate," +
//					" sourceDoc," +
//					" deprMethod," +
//					" scrapValue," +
//					" useEndYM," +
//					" apportionEndYM," +
//					" permitReduceDate," +
//					" computerType," +
//					" editID,"+
//					" editDate,"+
//					" editTime"+
//				" )Values(" +
//					Common.sqlChar(enterOrg) + "," +
//					Common.sqlChar(ownership) + "," +
//					Common.sqlChar(addProofCaseNo) + "," +
//					Common.sqlChar(rs.getString("propertyNo")) + "," +
//					Common.sqlChar(obj.getLotNo()) + "," +
//					Common.sqlChar(obj.getSerialNoS()) + "," +
//					Common.sqlChar(obj.getSerialNoE()) + "," +
//					Common.sqlChar(rs.getString("otherPropertyUnit")) + "," +
//					Common.sqlChar(rs.getString("otherMaterial")) + "," +
//					Common.sqlChar(rs.getString("otherLimitYear")) + "," +
//					Common.sqlChar(rs.getString("propertyName1")) + "," +
//					Common.sqlChar(cause) + "," +
//					Common.sqlChar(cause1) + "," +
//					Common.sqlChar(enterDate) + "," +
//					Common.sqlChar(rs.getString("buyDate")) + "," +
//					Common.sqlChar("1") + "," +
//					Common.sqlChar("N") + "," +
//					Common.sqlChar("N") + "," +
//					//Common.sqlChar(rs.getString("propertyKind")) + "," +
//					Common.sqlChar(getPropertyKind()) + "," +
//					//Common.sqlChar(rs.getString("fundType")) + "," +
//					Common.sqlChar(getFundType()) + "," +
//					Common.sqlChar(rs.getString("valuable")) + "," +
//					Common.sqlChar(obj.getAdjustBookAmountAll()) + "," +
//					Common.sqlChar(rs.getString("oldBookUnit")) + "," +
//					Common.sqlChar(obj.getAdjustBookValueAll()) + "," +
//					Common.sqlChar(obj.getAdjustBookAmountAll()) + "," +
//					Common.sqlChar(obj.getAdjustBookValueAll()) + "," +
//					Common.sqlChar(rs.getString("articleName")) + "," +
//					Common.sqlChar(rs.getString("nameplate")) + "," +
//					Common.sqlChar(rs.getString("specification")) + "," +
//					"(select sourceKind from UNTMP_Movable where 1=1 and (enterOrg, ownership, propertyno, lotno) in (select enterOrg, ownership, propertyNO, lotNo from untmp_movableDetail where 1=1 and enterOrg = '"+rs.getString("enterOrg")+"' and ownership = '"+rs.getString("ownership")+"' and propertyNo = '"+rs.getString("propertyNo")+"'  and serialNo = '"+rs.getString("serialNo")+"'))," +
//					"(select sourceDate from UNTMP_Movable where 1=1 and (enterOrg, ownership, propertyno, lotno) in (select enterOrg, ownership, propertyNO, lotNo from untmp_movableDetail where 1=1 and enterOrg = '"+rs.getString("enterOrg")+"' and ownership = '"+rs.getString("ownership")+"' and propertyNo = '"+rs.getString("propertyNo")+"'  and serialNo = '"+rs.getString("serialNo")+"'))," +
//					"(select sourceDoc from UNTMP_Movable where 1=1 and (enterOrg, ownership, propertyno, lotno) in (select enterOrg, ownership, propertyNO, lotNo from untmp_movableDetail where 1=1 and enterOrg = '"+rs.getString("enterOrg")+"' and ownership = '"+rs.getString("ownership")+"' and propertyNo = '"+rs.getString("propertyNo")+"'  and serialNo = '"+rs.getString("serialNo")+"'))," +
//					Common.sqlChar(rs.getString("deprMethod")) + "," +
//					Common.sqlChar(obj.getScrapValueAll()) + "," +
//					Common.sqlChar(rs.getString("useEndYM")) + "," +
//					Common.sqlChar(rs.getString("apportionEndYM")) + "," +
//					Common.sqlChar(rs.getString("permitReduceDate")) + "," +
//					Common.sqlChar(rs.getString("computerType")) + "," +
//					Common.sqlChar(getEditID()) + "," +
//					Common.sqlChar(getEditDate()) + "," +
//					Common.sqlChar(getEditTime()) + ")" +
//					":;:";				
//			//System.out.println("基金"+fundType);
//			//System.out.println("UNTMP_Movable: \n"+strSQL+"\n");				
//		}
//		//查出UNTMP_ReduceDetail資料並新增至UNTMP_MovableDetail
//		strSQL+=getInsertUntmpMovableDetail();		
//		execSQLArray = strSQL.split(":;:");
//		db.excuteSQL(execSQLArray);
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		db.closeAll();
//	}
//}
//
////查出UNTMP_ReduceDetail資料並新增至UNTMP_MovableDetail
//protected String getInsertUntmpMovableDetail(){
//	UNTMP003F obj = this;
//	Database db = new Database();
//	StringBuffer sbSQL = new StringBuffer("");
//	//String strSQL = "";
//	//String[] execSQLArray;
//	ResultSet rs;	
//	String sql="select * from UNTMP_ReduceDetail " +
//				" where enterOrg = " + Common.sqlChar(obj.getReduceDetailEnterOrg()) + 
//				" and ownership = " + Common.sqlChar(obj.getReduceDetailOwnership()) + 
//				" and caseNo = " + Common.sqlChar(obj.getReduceDetailCaseNo()) + 
//				" and propertyNo = " + Common.sqlChar(obj.getReduceDetailPropertyNo()) +
//				" and lotNo = " + Common.sqlChar(obj.getReduceDetailLotNo()) +
//				" and adjustBookAmount = " + Common.sqlChar(obj.getReduceDetailAdjustBookAmount()) +
//				" and newEnterOrg = " + Common.sqlChar(newEnterOrg) +
//				" order by serialno " +
//				"";
////	System.out.println("查出 UNTMP_ReduceDetail 內資料: \n"+sql+"\n");
//	try{
//		rs = db.querySQL(sql);
//		while(rs.next()){
//			//將UNTMP_ReduceDetail資料新增至UNTMP_MovableDetail
//			sbSQL.append(" insert into UNTMP_MovableDetail( " ).append(
//					" enterOrg,").append(
//					" ownership,").append(
//					" propertyNo,").append(
//					" lotNo,").append(
//					" serialNo," ).append(
//					" dataState," ).append(
//					" verify," ).append(
//					" closing," ).append(
//					" originalAmount," ).append(
//					" originalBV," ).append(
//					" bookAmount," ).append(
//					" bookValue," ).append(
//					" licensePlate," ).append(
//					" originalMoveDate," ).append(
//					" originalKeepUnit," ).append(
//					" originalKeeper," ).append(
//					" originalUseUnit," ).append(
//					" originalUser," ).append(
//					" originalPlace," ).append(
//					" moveDate," ).append(
//					" keepUnit," ).append(
//					" keeper," ).append(
//					" useUnit," ).append(
//					" userNo," ).append(
//					" place," ).append(
//					" scrapValue," ).append(
//					" deprAmount," ).append(
//					" apportionYear," ).append(
//					" monthDepr," ).append(
//					" accumDeprYM," ).append(
//					" accumDepr," ).append(
//					" editID,").append(
//					" editDate,").append(
//					" editTime,").append(
//					" barcode").append(
//				" )Values(" ).append(
//					Common.sqlChar(enterOrg) ).append( "," ).append(
//					Common.sqlChar(ownership) ).append( "," ).append(
//					Common.sqlChar(rs.getString("propertyNo")) ).append( "," ).append(
//					Common.sqlChar(obj.getLotNo()) ).append( "," ).append(
//					Common.sqlChar(obj.getSerialNo()) ).append( "," ).append(
//					Common.sqlChar("1") ).append( "," ).append(
//					Common.sqlChar("N") ).append( "," ).append(
//					Common.sqlChar("N") ).append( "," ).append(
//					Common.sqlChar(rs.getString("adjustBookAmount")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("adjustBookValue")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("adjustBookAmount")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("adjustBookValue")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("licensePlate")) ).append( "," ).append(
//					Common.sqlChar(originalMoveDate) ).append( "," ).append(
//					Common.sqlChar(originalKeepUnit) ).append( "," ).append(
//					Common.sqlChar(originalKeeper) ).append( "," ).append(
//					Common.sqlChar(originalUseUnit) ).append( "," ).append(
//					Common.sqlChar(originalUser) ).append( "," ).append(
//					Common.sqlChar(originalPlace) ).append( "," ).append(
//					Common.sqlChar(originalMoveDate) ).append( "," ).append(
//					Common.sqlChar(originalKeepUnit) ).append( "," ).append(
//					Common.sqlChar(originalKeeper) ).append( "," ).append(
//					Common.sqlChar(originalUseUnit) ).append( "," ).append(
//					Common.sqlChar(originalUser) ).append( "," ).append(
//					Common.sqlChar(originalPlace) ).append( "," ).append(
//					Common.sqlChar(rs.getString("scrapValue")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("deprAmount")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("apportionYear")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("monthDepr")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("accumDeprYM")) ).append( "," ).append(
//					Common.sqlChar(rs.getString("accumDepr")) ).append( "," ).append(
//					Common.sqlChar(getEditID()) ).append( "," ).append(
//					Common.sqlChar(getEditDate()) ).append( "," ).append(
//					Common.sqlChar(getEditTime()) ).append("," ).append(
//									" (select " + Common.sqlChar(ownership) +" + " + Datetime.getYYY() + " + " 
//									+ "substring( " + Common.sqlChar(rs.getString("propertyNo")) + " ,0,1) + RIGHT('000000' + CAST((substring((case max(barcode) when null then '00000000000' else max(barcode) end),6,6) + 1) as varchar), 6) from UNTMP_MovableDetail m where 1=1 and substring(barcode,0,5) = "
//									+ Common.sqlChar(rs.getString("ownership") + getEditDate().substring(0,3) + rs.getString("propertyNo").substring(0,1))+" and enterorg= "+Common.sqlChar(enterOrg)+")").append( ")" ).append(
//									":;:");
//
//			int setSerialNo = Integer.parseInt(obj.getSerialNo())+1;
//			obj.setSerialNo(Common.formatFrontZero((setSerialNo+""),7));
//		
//		}
//		int setLotNo = Integer.parseInt(obj.getLotNo())+1;
//		obj.setLotNo(Common.formatFrontZero((setLotNo+""),7));
//	//execSQLArray = sbSQL.toString().split(":;:");
//	//db.excuteSQL(execSQLArray);
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		db.closeAll();
//	}
//	return sbSQL.toString();
//}
//
////挑選 UNTMP_ReduceProof 檔案欄位資料
//protected void getUntmpReduceProof() throws Exception{
//	UNTMP003F obj = this;
//	Database db = new Database();
//	ResultSet rs = null;
//	//ResultSet rsC = null;
//	String sql = "";
//	//String[] execSQLArray;
//	String checkValue="";
//	try {   
//		//conn.setAutoCommit(false);		
//		//取得減損單明細檔	
//		sql="select distinct enterOrg, ownership , caseNo ,  propertyNo , lotNo, adjustBookAmount from UNTMP_ReduceDetail " +
//			" where enterOrg = " + Common.sqlChar(oldEnterOrg) + 
//			" and ownership = " + Common.sqlChar(ownership) + 
//			" and caseNo = " + Common.sqlChar(caseNo) + 
//			" and newEnterOrg = " + Common.sqlChar(newEnterOrg) +
//			"";	
////		System.out.println("取得減損單明細檔UNTMP_ReduceDetail: \n"+sql+"\n");
//		rs = db.querySQL(sql);
//		while (rs.next()){
//			checkValue="Y";
//			obj.setReduceDetailEnterOrg(rs.getString("enterOrg"));
//			obj.setReduceDetailOwnership(rs.getString("ownership"));
//			obj.setReduceDetailPropertyNo(rs.getString("propertyNo"));
//			obj.setReduceDetailCaseNo(rs.getString("caseNo"));
//			obj.setReduceDetailLotNo(rs.getString("lotNo"));
//			obj.setReduceDetailAdjustBookAmount(rs.getString("adjustBookAmount"));
//			try {
//				//取得Movable批號
//				getMovableLotNo();
//				//取得UNTMP_MovableDetail分號
//				getMovableDetailSerialNo();
//				//抓出財產分號起-訖			
//				getSerialNoSE();
//				//查出UNTMP_ReduceDetail資料並新增入UNTMP_Movable
//				getInsertUntmpMovable();
//			} catch (Exception e) {
//				System.out.println("excuteSQL Exception!!");
//				throw new Exception(e);
//			}			
//		}
//		if("Y".equals(checkValue)){
//			setStateUpdateSuccess();
//			setErrorMsg("新增完成");
//		}else{
//			setStateUpdateError();
//			setErrorMsg("查無資料!");
//		}
//	//conn.commit();	
//	} catch (Exception e) {
//		//conn.rollback();		
//		e.printStackTrace();
//	} finally {
//		//conn.close();
//		db.closeAll();
//	}	
//}
//
////批次新增
//public void untmp003f()throws Exception{
//	setEditID(getUserID());
//	setEditDate(Datetime.getYYYMMDD());
//	setEditTime(Datetime.getHHMMSS());				
//	getUntmpReduceProof();
//}
//
///**
//public void excuteSQL(Connection conn, String[] sql) throws Exception{
//	try {		
//		Statement stmt = db.getForwardStatement(conn);
//		for(int i=0; i<sql.length; i++){
//		    if(!"".equals(sql[i].toString()))
//		        stmt.executeUpdate(sql[i]);
//		}			
//	} catch (Exception e) {
//		System.out.println("excuteSQL Exception = ");
//		throw new Exception(e);			
//	} 	
//}	
//
//public ResultSet querySQL(Connection conn, String sql) throws Exception{
//	Statement stmt = db.getForwardStatement(conn);
//	ResultSet rs = stmt.executeQuery(sql);
//	return rs;
//}	
//**/
//
//}
//

