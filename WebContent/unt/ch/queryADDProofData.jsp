<%@ page contentType="text/html;charset=UTF-8" import="util.*,java.sql.*"%>
<%
		StringBuilder resultStr = new StringBuilder();
		unt.ch.UNTCH_Tools ut = new unt.ch.UNTCH_Tools();
		Database db = new Database();
		ResultSet rs = null;

		try{
		String result=request.getReader().readLine();
		
		String[] resultArray=result.split("&");
		
		String enterOrg = resultArray[0].split("=")[1];
		String ownership = resultArray[1].split("=")[1];
		String differenceKind = resultArray[2].split("=")[1];
		
		String[] strs = null;
		String propertyNo = "";
		strs = resultArray[3].split("=");
		if(strs.length > 1){
			propertyNo = strs[1];	
		}
		
		String serialNo = "";
		strs = resultArray[4].split("=");
		if(strs.length > 1){
			serialNo = strs[1];	
		}
		
		String laSignNo = "";
		strs = resultArray[5].split("=");
		if(strs.length > 1){
			laSignNo = strs[1];	
		}
		
		String buSignNo = "";
		strs = resultArray[6].split("=");
		if(strs.length > 1){	
			buSignNo = strs[1];	
		}
		
		// 呼叫此隻的單據種類(共有 移動單, 增減值單, 減損單, 折舊資料維護作業會呼叫到...)
		String proofType = "";
		strs = resultArray[7].split("=");
		if(strs.length > 1){	
			proofType = strs[1];	
		}
		
		/*
		String enterOrg = Common.checkGet(request.getParameter("enterOrg"));
		String ownership = Common.checkGet(request.getParameter("ownership"));
		String differenceKind = Common.checkGet(request.getParameter("differenceKind"));
		String propertyNo = Common.checkGet(request.getParameter("propertyNo"));
		String serialNo = Common.checkGet(request.getParameter("serialNo"));
		String laSignNo = Common.checkGet(request.getParameter("laSignNo"));
		String buSignNo = Common.checkGet(request.getParameter("buSignNo"));
		*/
		
		String data = "";
		String sql = "";
		
		sql = "select " +
				" propertyNo," +
				" serialNo," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" area as oldLaArea, " +
				" holdnume as oldLaHoldNume, " +
				" holddeno as oldLaHoldDeno, " +
				" holdArea as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" bookunit, " +
				" netvalue, " +
				" netUnit, " +
				" null as oldBookAmount, " +
				" netUnit as oldBookUnit, " +
				" bookvalue as oldBookValue, " +
				" bookunit as oldNetUnit, " +
				" netvalue as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" bookunit as newBookUnit, " +
				" bookvalue as newBookValue, " +
				" netUnit as newNetUnit, " +
				" netvalue as newNetValue, " +				
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" a.originalbv as oldbv, " +
				" null as oldDeprMethod, " +
				" null as oldBuildFeeCB, " +
				" null as oldDeprUnitCB, " +
				" null as oldDeprPark, " +
				" null as oldDeprUnit, " +
				" null as oldDeprUnit1, " +
				" null as oldDeprAccounts, " +
				" null as oldScrapValue, " +
				" null as oldDeprAmount, " +
				" '0' as oldAccumDepr, " +
				" null as oldApportionMonth, " +
				" null as newApportionMonth, " +
				" null as oldMonthDepr, " +
				" null as oldMonthDepr1, " +
				" null as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as limityear," +
				" null as otherMaterial, " +
				" null as otherPropertyUnit, " +
				" null as otherLimitYear, " +
				" null as securityName, " +
				" signno as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" valuable, " +
				" taxCredit, " +
				" null as lotNo, " +
				" null as specification, " + 
				" null as nameplate, " + 
				" enterdate, " + 
				" editdate, " +
				" null as closing1ym " +
			" from UNTLA_LAND a where 1=1" + 
			" and a.enterorg = '" + Common.esapi(enterOrg) + "' and a.ownership = '" + Common.esapi(ownership) + "' and a.differenceKind = '" + Common.esapi(differenceKind) + "' and a.verify = 'Y' and a.datastate = '1'" +			
			("".equals(propertyNo)?"":" and a.propertyNo = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and a.serialNo = '" + Common.esapi(serialNo) + "'") +
			("".equals(laSignNo)?"":" and a.signno = '" + Common.esapi(laSignNo) + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" CArea as oldBuCArea, " +
				" SArea as oldBuSArea, " +
				" Area as oldBuArea, " +
				" HoldNume as oldBuHoldNume, " +
				" HoldDeno as oldBuHoldDeno, " +
				" HoldArea as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" netvalue, " +
				" null as NetUnit, " +
				" '1' as oldBookAmount, " +
				" null as oldBookUnit, " +
				" bookvalue as oldBookValue, " +
				" null as oldNetUnit, " +
				" netvalue as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" null as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" a.originalbv as oldbv, " +
				" deprMethod as oldDeprMethod, " +
				" buildFeeCB as oldBuildFeeCB, " +
				" deprUnitCB as oldDeprUnitCB, " +
				" deprPark as oldDeprPark, " +
				" deprUnit as oldDeprUnit, " +
				" deprUnit1 as oldDeprUnit1, " +
				" deprAccounts as oldDeprAccounts, " +
				" scrapValue as oldScrapValue, " +
				" deprAmount as oldDeprAmount, " +
				" accumDepr as oldAccumDepr, " +
				" apportionMonth as oldApportionMonth, " +
				" case when (a.differencekind='110' and  b.closing1ym ='201512') then a.apportionmonth when ISNULL(a.otherlimityear,0) - DATEDIFF(mm,a.buydate,b.closing1ym + '01') > 0 then ISNULL(a.otherlimityear,0) - DATEDIFF(mm,a.buydate,b.closing1ym + '01') else 0 end as newApportionMonth, " +
				" monthDepr as oldMonthDepr, " +
				" monthDepr1 as oldMonthDepr1, " +
				" apportionEndYM as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as limityear," +
				" a.otherMaterial as otherMaterial, " +
				" a.otherpropertyunit as otherPropertyUnit, " +
				" otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" signno as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" valuable, " +
				" taxCredit, " +
				" null as lotNo, " +
				" null as specification, " + 
				" null as nameplate, " +
				" enterdate, " +
				" editdate, " +
				" b.closing1ym as closing1ym " +
			" from UNTBU_BUILDING a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind where 1=1" + 
			" and a.enterorg = '" + Common.esapi(enterOrg) + "' and a.ownership = '" + Common.esapi(ownership) + "' and a.differenceKind = '" + Common.esapi(differenceKind) + "' and a.verify = 'Y' and a.datastate = '1'" + 
			("".equals(propertyNo)?"":" and a.propertyNo = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and a.serialNo = '" + Common.esapi(serialNo) + "'") +
			("".equals(buSignNo)?"":" and a.signno = '" + Common.esapi(buSignNo) + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" measure as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" netvalue, " +
				" null as dNetUnit, " +
				" '1' as oldBookAmount, " +
				" null as oldBookUnit, " +
				" bookvalue as oldBookValue, " +
				" null as oldNetUnit, " +
				" netvalue as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" bookvalue as newBookValue, " +
				" null as newNetUnit, " +
				" netvalue as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" a.originalbv as oldbv, " +
				" deprMethod as oldDeprMethod, " +
				" buildFeeCB as oldBuildFeeCB, " +
				" deprUnitCB as oldDeprUnitCB, " +
				" deprPark as oldDeprPark, " +
				" deprUnit as oldDeprUnit, " +
				" deprUnit1 as oldDeprUnit1, " +
				" deprAccounts as oldDeprAccounts, " +
				" scrapValue as oldScrapValue, " +
				" deprAmount as oldDeprAmount, " +
				" accumDepr as oldAccumDepr, " +
				" apportionMonth as oldApportionMonth, " +
				" case when (a.differencekind='110' and  b.closing1ym ='201512') then a.apportionmonth  when ISNULL(a.otherlimityear,0) - DATEDIFF(mm,a.buydate,b.closing1ym + '01') > 0 then ISNULL(a.otherlimityear,0) - DATEDIFF(mm,a.buydate,b.closing1ym + '01') else 0 end as newApportionMonth, " +
				" monthDepr as oldMonthDepr, " +
				" monthDepr1 as oldMonthDepr1, " +
				" apportionEndYM as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as limityear," +
				" a.otherMaterial as otherMaterial, " +
				" a.otherpropertyunit as otherPropertyUnit, " +
				" otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" valuable, " +
				" taxCredit, " +
				" null as lotNo, " +
				" null as specification, " + 
				" null as nameplate, " +
				" enterdate, " +
				" editdate, " +
				" b.closing1ym as closing1ym " +
			" from UNTRF_ATTACHMENT a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind where 1=1" + 
			" and a.enterorg = '" + Common.esapi(enterOrg) + "' and a.ownership = '" + Common.esapi(ownership) + "' and a.differenceKind = '" + Common.esapi(differenceKind) + "' and a.verify = 'Y' and a.datastate = '1'" + 
			("".equals(propertyNo)?"":" and a.propertyNo = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and a.serialNo = '" + Common.esapi(serialNo) + "'") +
		" union " +
		"select " +
				" a.propertyNo," +
				" a.serialNo," +
				" a.engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = b.enterorg and z.engineeringno = b.engineeringno) as engineeringnoname, " +
				" a.propertyName1, " +
				" a.oldPropertyNo, " +
				" a.oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" a.originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" null as bookamount, " +
				" '0' as BookValue, " +
				" null as BookUnit, " +
				" null as netvalue, " +
				" null as NetUnit, " +
				" a.bookamount as oldBookAmount, " +						
				" case when b.originalUnit is null then a.bookvalue else b.originalUnit end as oldBookUnit, " +
				" a.bookvalue as oldBookValue, " +
				" case when b.originalUnit is null then a.netvalue else b.originalUnit end as oldNetUnit, " +
				" a.netvalue as oldNetValue, " +
				" b.bookamount as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" b.bookvalue as adjustBookValue, " +
				" b.bookvalue as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" b.netvalue as adjustNetValue, " +
				" '0' as newBookAmount, " +
				" '0' as newBookUnit, " +
				" '0' as newBookValue, " +
				" '0' as newNetUnit, " +
				" '0' as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" a.originalbv as oldbv, " +
				" a.deprMethod as oldDeprMethod, " +
				" a.originalbuildFeeCB as oldBuildFeeCB, " +
				" a.deprUnitCB as oldDeprUnitCB, " +
				" a.deprPark as oldDeprPark, " +
				" a.deprUnit as oldDeprUnit, " +
				" a.deprUnit1 as oldDeprUnit1, " +
				" a.deprAccounts as oldDeprAccounts, " +
				" a.scrapValue as oldScrapValue, " +
				" a.deprAmount as oldDeprAmount, " +
				" a.accumDepr as oldAccumDepr, " +
				" a.apportionMonth as oldApportionMonth, " +
				" case when (a.differencekind='110' and  c.closing1ym ='201512') then a.apportionmonth  when ISNULL(a.otherlimityear,0) - DATEDIFF(mm,b.buydate,c.closing1ym + '01') > 0 then ISNULL(a.otherlimityear,0) - DATEDIFF(mm,b.buydate,c.closing1ym + '01') else 0 end as newApportionMonth, " +
				" a.monthDepr as oldMonthDepr, " +
				" a.monthDepr1 as oldMonthDepr1, " +
				" a.apportionEndYM as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg IN('000000000A',a.enterorg)) as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno and z.enterorg IN('000000000A',a.enterorg)) as propertyunit," +
				" (cast(a.otherlimityear/12 as int)) as limityear," +
				" b.otherMaterial as otherMaterial, " +
				" b.otherpropertyunit as otherPropertyUnit, " +
				" a.otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" b.valuable, " +
				" null as taxCredit, " +
				" a.lotNo, " +
				" b.specification, " + 
				" b.nameplate, " + 
				" b.enterdate, " + 
				" a.editdate, " +
				" c.closing1ym as closing1ym " +
			" from UNTMP_MOVABLEDETAIL a inner join UNTAC_CLOSINGPT c on a.enterorg = c.enterorg and a.differencekind = c.differencekind " + 
				" left join UNTMP_MOVABLE b on a.enterorg = b.enterorg and a.ownership = b.ownership and a.differencekind = b.differencekind and a.propertyno = b.propertyno and a.lotno = b.lotno" +
				" where 1=1" + 
				" and a.enterorg = '" + Common.esapi(enterOrg) + "' and a.ownership = '" + Common.esapi(ownership) + "' and a.differenceKind = '" + Common.esapi(differenceKind) + "' and a.verify = 'Y' and a.datastate = '1'" + 
				("".equals(propertyNo)?"":" and a.propertyNo = '" + Common.esapi(propertyNo) + "'") + 
				("".equals(serialNo)?"":" and a.serialNo = '" + Common.esapi(serialNo) + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" null as NetValue, " +
				" null as NetUnit, " +
				" bookamount as oldBookAmount, " +
				" null as oldBookUnit, " +
				" bookvalue as oldBookValue, " +
				" null as oldNetUnit, " +
				" null as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" bookamount as newBookAmount, " +
				" null as newBookUnit, " +
				" bookvalue as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" null as oldbv, " +
				" null as oldDeprMethod, " +
				" null as oldBuildFeeCB, " +
				" null as oldDeprUnitCB, " +
				" null as oldDeprPark, " +
				" null as oldDeprUnit, " +
				" null as oldDeprUnit1, " +
				" null as oldDeprAccounts, " +
				" null as oldScrapValue, " +
				" null as oldDeprAmount, " +
				" '0' as oldAccumDepr, " +
				" null as oldApportionMonth, " +
				" null as newApportionMonth, " +
				" null as oldMonthDepr, " +
				" null as oldMonthDepr1, " +
				" null as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as limityear," +
				" a.otherMaterial as otherMaterial, " +
				" a.otherpropertyunit as otherPropertyUnit, " +
				" null as otherLimitYear, " +
				" securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" 'N' as valuable, " +
				" null as taxCredit, " +
				" null as lotNo, " +
				" null as specification, " + 
				" null as nameplate, " + 
				" enterdate, " +
				" editdate, " +
				" b.closing1ym as closing1ym " +
			" from UNTVP_ADDPROOF a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind where 1=1" + 
			" and a.enterorg = '" + Common.esapi(enterOrg) + "' and a.ownership = '" + Common.esapi(ownership) + "' and a.differenceKind = '" + Common.esapi(differenceKind) + "' and a.verify = 'Y' and a.datastate = '1'" + 
			("".equals(propertyNo)?"":" and a.propertyNo = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and a.serialNo = '" + Common.esapi(serialNo) + "'") +
		" union " +
		"select " +
				" propertyNo," +
				" serialNo," +
				" engineeringNo, " +
				" (select engineeringname from UNTEG_ENGINEERINGCASE z where z.enterorg = a.enterorg and z.engineeringno = a.engineeringno) as engineeringnoname, " +
				" propertyName1, " +
				" oldPropertyNo, " +
				" oldSerialNo, " +
				" null as oldLaArea, " +
				" null as oldLaHoldNume, " +
				" null as oldLaHoldDeno, " +
				" null as oldLaHoldArea, " +
				" null as oldBuCArea, " +
				" null as oldBuSArea, " +
				" null as oldBuArea, " +
				" null as oldBuHoldNume, " +
				" null as oldBuHoldDeno, " +
				" null as oldBuHoldArea, " +
				" null as oldMeasure, " +
				" sourceDate, " +
				" originalBV, " +
				" buyDate, " +
				" null as bookNotes, " +
				" '1' as bookamount, " +
				" bookvalue, " +
				" null as BookUnit, " +
				" NetValue, " +
				" null as NetUnit, " +
				" null as oldBookAmount, " +
				" null as oldBookUnit, " +
				" bookvalue as oldBookValue, " +
				" null as oldNetUnit, " +
				" null as oldNetValue, " +
				" null as adjustBookAmount, " +
				" null as adjustBookUnit, " +
				" null as adjustBookValue, " +
				" null as adjustAccumDepr, " +
				" null as adjustNetUnit, " +
				" null as adjustNetValue, " +
				" null as newBookAmount, " +
				" null as newBookUnit, " +
				" bookvalue as newBookValue, " +
				" null as newNetUnit, " +
				" null as newNetValue, " +
				" keepUnit, " +
				" keeper, " +
				" useUnit, " +
				" userNo, " +
				" userNote, " +
				" place1, " +
				" (select placename from SYSCA_PLACE z where z.enterorg = a.enterorg and z.placeno = a.place1) as place1name, " +
				" place, " +
				" null as oldbv, " +
				" deprmethod as oldDeprMethod, " +
				" buildfeecb as oldBuildFeeCB, " +
				" deprunitcb as oldDeprUnitCB, " +
				" deprpark as oldDeprPark, " +
				" deprunit as oldDeprUnit, " +
				" deprunit1 as oldDeprUnit1, " +
				" depraccounts as oldDeprAccounts, " +
				" scrapvalue as oldScrapValue, " +
				" depramount as oldDeprAmount, " +
				" accumdepr as oldAccumDepr, " +
				" apportionmonth as oldApportionMonth, ";
			// TODO  AdjustProof? 		
			sql += " case when (a.differencekind='110' and  b.closing1ym ='201512') then a.apportionmonth when ISNULL(a.otherlimityear,0) - DATEDIFF(mm,a.buydate,b.closing1ym + '01') > 0 then ISNULL(a.otherlimityear,0) - DATEDIFF(mm,a.buydate,b.closing1ym + '01') else 0 end as newApportionMonth, ";
		    sql += " monthdepr as oldMonthDepr, " +
				" monthdepr1 as oldMonthDepr1, " +
				" apportionendym as oldApportionEndYM, " +
				" (select material from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as material," +
				" (select propertyunit from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as propertyunit," +
				" (select limityear from SYSPK_PROPERTYCODE z where z.propertyno = a.propertyno) as limityear," +
				" a.otherMaterial as otherMaterial, " +
				" a.otherpropertyunit as otherPropertyUnit, " +
				" otherlimityear as otherLimitYear, " +
				" null as securityName, " +
				" null as lasignNo, " +
				" null as busignNo, " +
				" propertyKind, " +
				" fundType, " +
				" 'N' as valuable, " +
				" null as taxCredit, " +
				" null as lotNo, " +
				" null as specification, " + 
				" null as nameplate, " +
				" enterdate, " + 
				" editdate, " +
				" b.closing1ym as closing1ym " +
			" from UNTRT_ADDPROOF a inner join UNTAC_CLOSINGPT b on a.enterorg = b.enterorg and a.differencekind = b.differencekind where 1=1" + 
			" and a.enterorg = '" + Common.esapi(enterOrg) + "' and a.ownership = '" + Common.esapi(ownership) + "' and a.differenceKind = '" + Common.esapi(differenceKind) + "' and a.verify = 'Y' and a.datastate = '1'" + 
			("".equals(propertyNo)?"":" and a.propertyNo = '" + Common.esapi(propertyNo) + "'") + 
			("".equals(serialNo)?"":" and a.serialNo = '" + Common.esapi(serialNo) + "'") +
		"";

	resultStr.append("{");
System.out.println("sql : " + sql);
	rs = db.querySQL(sql);
	if(rs.next()){		
		resultStr.append("\"").append("hasData").append("\":\"").append("Y".replace("\"","\\\"")).append("\",");
		
		resultStr.append("\"").append("propertyNo").append("\":\"").append(Common.esapi(Common.get(rs.getString("propertyNo"))).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("serialNo").append("\":\"").append(Common.get(rs.getString("serialNo")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("engineeringNo").append("\":\"").append(Common.get(rs.getString("engineeringNo")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("engineeringnoname").append("\":\"").append(Common.get(rs.getString("engineeringnoname")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("propertyName1").append("\":\"").append(Common.get(rs.getString("propertyName1")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldPropertyNo").append("\":\"").append(Common.get(rs.getString("oldPropertyNo")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldSerialNo").append("\":\"").append(Common.get(rs.getString("oldSerialNo")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldLaArea").append("\":\"").append(Common.get(rs.getString("oldLaArea")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldLaHoldNume").append("\":\"").append(Common.get(rs.getString("oldLaHoldNume")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldLaHoldDeno").append("\":\"").append(Common.get(rs.getString("oldLaHoldDeno")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldLaHoldArea").append("\":\"").append(Common.get(rs.getString("oldLaHoldArea")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBuCArea").append("\":\"").append(Common.get(rs.getString("oldBuCArea")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBuSArea").append("\":\"").append(Common.get(rs.getString("oldBuSArea")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBuArea").append("\":\"").append(Common.get(rs.getString("oldBuArea")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBuHoldNume").append("\":\"").append(Common.get(rs.getString("oldBuHoldNume")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBuHoldDeno").append("\":\"").append(Common.get(rs.getString("oldBuHoldDeno")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBuHoldArea").append("\":\"").append(Common.get(rs.getString("oldBuHoldArea")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldMeasure").append("\":\"").append(Common.get(rs.getString("oldMeasure")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("sourceDate").append("\":\"").append(ut._transToROC_Year(Common.get(rs.getString("sourceDate"))).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("originalBV").append("\":\"").append(Common.get(rs.getString("originalBV")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("buyDate").append("\":\"").append(ut._transToROC_Year(Common.get(rs.getString("buyDate"))).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("bookNotes").append("\":\"").append(Common.get(rs.getString("bookNotes")).replace("\"","\\\"")).append("\",");

		resultStr.append("\"").append("bookAmount").append("\":\"").append(Common.get(rs.getString("BookAmount")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("bookValue").append("\":\"").append(Common.get(rs.getString("BookValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("bookUnit").append("\":\"").append(Common.get(rs.getString("BookUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("accumDepr").append("\":\"").append(("".equals(Common.get(rs.getString("oldAccumDepr")))?"0":rs.getString("oldAccumDepr")).replace("\"","\\\"")).append("\",");
		
		resultStr.append("\"").append("netValue").append("\":\"").append(Common.get(rs.getString("NetValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("netUnit").append("\":\"").append(Common.get(rs.getString("NetUnit")).replace("\"","\\\"")).append("\",");
		
		resultStr.append("\"").append("oldBookAmount").append("\":\"").append(Common.get(rs.getString("oldBookAmount")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBookUnit").append("\":\"").append(Common.get(rs.getString("oldBookUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBookValue").append("\":\"").append(Common.get(rs.getString("oldBookValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldNetUnit").append("\":\"").append(Common.get(rs.getString("oldNetUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldNetValue").append("\":\"").append(Common.get(rs.getString("oldNetValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("adjustBookAmount").append("\":\"").append(Common.get(rs.getString("adjustBookAmount")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("adjustBookUnit").append("\":\"").append(Common.get(rs.getString("adjustBookUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("adjustBookValue").append("\":\"").append(Common.get(rs.getString("adjustBookValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("adjustAccumDepr").append("\":\"").append(Common.get(rs.getString("adjustAccumDepr")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("adjustNetUnit").append("\":\"").append(Common.get(rs.getString("adjustNetUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("adjustNetValue").append("\":\"").append(Common.get(rs.getString("adjustNetValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("newBookAmount").append("\":\"").append(Common.get(rs.getString("newBookAmount")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("newBookUnit").append("\":\"").append(Common.get(rs.getString("newBookUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("newBookValue").append("\":\"").append(Common.get(rs.getString("newBookValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("newNetUnit").append("\":\"").append(Common.get(rs.getString("newNetUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("newNetValue").append("\":\"").append(Common.get(rs.getString("newNetValue")).replace("\"","\\\"")).append("\",");
		

		resultStr.append("\"").append("keepUnit").append("\":\"").append(Common.get(rs.getString("keepUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("keeper").append("\":\"").append(Common.get(rs.getString("keeper")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("useUnit").append("\":\"").append(Common.get(rs.getString("useUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("userNo").append("\":\"").append(Common.get(rs.getString("userNo")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("userNote").append("\":\"").append(Common.get(rs.getString("userNote")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("place1").append("\":\"").append(Common.get(rs.getString("place1")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("place1name").append("\":\"").append(Common.get(rs.getString("place1name")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("place").append("\":\"").append(Common.get(rs.getString("place")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBV").append("\":\"").append(Common.get(rs.getString("oldBV")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldDeprMethod").append("\":\"").append(Common.get(rs.getString("oldDeprMethod")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldBuildFeeCB").append("\":\"").append(Common.get(rs.getString("oldBuildFeeCB")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldDeprUnitCB").append("\":\"").append(Common.get(rs.getString("oldDeprUnitCB")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldDeprPark").append("\":\"").append(Common.get(rs.getString("oldDeprPark")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldDeprUnit").append("\":\"").append(Common.get(rs.getString("oldDeprUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldDeprUnit1").append("\":\"").append(Common.get(rs.getString("oldDeprUnit1")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldDeprAccounts").append("\":\"").append(Common.get(rs.getString("oldDeprAccounts")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldScrapValue").append("\":\"").append(Common.get(rs.getString("oldScrapValue")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldDeprAmount").append("\":\"").append(Common.get(rs.getString("oldDeprAmount")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldAccumDepr").append("\":\"").append(("".equals(Common.get(rs.getString("oldAccumDepr")))?"0":rs.getString("oldAccumDepr")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldApportionMonth").append("\":\"").append(Common.get(rs.getString("oldApportionMonth")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("newApportionMonth").append("\":\"").append(Common.get(rs.getString("newApportionMonth")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldMonthDepr").append("\":\"").append(Common.get(rs.getString("oldMonthDepr")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldMonthDepr1").append("\":\"").append(Common.get(rs.getString("oldMonthDepr1")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("oldApportionEndYM").append("\":\"").append(ut._transToROC_Year(Common.get(rs.getString("oldApportionEndYM"))).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("material").append("\":\"").append(Common.get(rs.getString("material")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("propertyunit").append("\":\"").append(Common.get(rs.getString("propertyunit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("limityear").append("\":\"").append(Common.get(rs.getString("limityear")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("otherMaterial").append("\":\"").append(Common.get(rs.getString("otherMaterial")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("otherPropertyUnit").append("\":\"").append(Common.get(rs.getString("otherPropertyUnit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("otherLimitYear").append("\":\"").append(Common.get(rs.getString("otherLimitYear")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("securityName").append("\":\"").append(Common.get(rs.getString("securityName")).replace("\"","\\\"")).append("\",");
		
		String lasignNo = Common.get(rs.getString("lasignNo"));
		if(!"".equals(Common.get(rs.getString("lasignNo")))){
			if (lasignNo.length() >= 1) {
				resultStr.append("\"").append("laSignNo1").append("\":\"").append(lasignNo.substring(0,1) + "000000".replace("\"","\\\"")).append("\",");
			}
			if (lasignNo.length() >= 3) {
				resultStr.append("\"").append("laSignNo2").append("\":\"").append(lasignNo.substring(0,3) + "0000".replace("\"","\\\"")).append("\",");
			}
			if (lasignNo.length() >= 7) {
				resultStr.append("\"").append("laSignNo3").append("\":\"").append(lasignNo.substring(0,7).replace("\"","\\\"")).append("\",");
			}
			if (lasignNo.length() >= 12) {
				resultStr.append("\"").append("laSignNo4").append("\":\"").append(lasignNo.substring(7,11).replace("\"","\\\"")).append("\",");
			}
			if (lasignNo.length() > 12) {
				resultStr.append("\"").append("laSignNo5").append("\":\"").append(lasignNo.substring(11).replace("\"","\\\"")).append("\",");
			}
		}
		
		String busignNo = Common.get(rs.getString("busignNo"));
		if(!"".equals(busignNo)) {
			if (busignNo.length() >= 1) {
				resultStr.append("\"").append("buSignNo1").append("\":\"").append(busignNo.substring(0,1) + "000000".replace("\"","\\\"")).append("\",");
			}
			if (busignNo.length() >= 3) {
				resultStr.append("\"").append("buSignNo2").append("\":\"").append(busignNo.substring(0,3) + "0000".replace("\"","\\\"")).append("\",");
			}
			if (busignNo.length() >= 7) {
				resultStr.append("\"").append("buSignNo3").append("\":\"").append(busignNo.substring(0,7).replace("\"","\\\"")).append("\",");
			}
			if (busignNo.length() >= 12) {
				resultStr.append("\"").append("buSignNo4").append("\":\"").append(busignNo.substring(7,12).replace("\"","\\\"")).append("\",");
			}
			if (busignNo.length() > 12) {
				resultStr.append("\"").append("buSignNo5").append("\":\"").append(busignNo.substring(12).replace("\"","\\\"")).append("\",");
			}
		}
		resultStr.append("\"").append("propertyKind").append("\":\"").append(Common.get(rs.getString("propertyKind")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("fundType").append("\":\"").append(Common.get(rs.getString("fundType")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("valuable").append("\":\"").append(Common.get(rs.getString("valuable")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("taxCredit").append("\":\"").append(Common.get(rs.getString("taxCredit")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("lotNo").append("\":\"").append(Common.get(rs.getString("lotNo")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("nameplate").append("\":\"").append(Common.get(rs.getString("nameplate")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("specification").append("\":\"").append(Common.get(rs.getString("specification")).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("enterDate").append("\":\"").append(ut._transToROC_Year(Common.get(rs.getString("enterDate"))).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("closing1ym").append("\":\"").append(Common.get(rs.getString("closing1ym")).replace("\"","\\\"")).append("\",");
		
		  //已使用月數 = (系統日期 - 建築日期) ★/12 之餘額月數
		// total month = 月份 + 年*12月    format:yyymmdd
		
		String editDate = "";
		if("".equals(Common.get(rs.getString("editDate")))){
		}else{
			ut._transToROC_Year(rs.getString("editDate"));
		}
		
		String buyDate = "";
		int useYear = 0;
		int useMonth = 0;
		if("".equals(Common.get(rs.getString("buyDate")))){
		}else{
			buyDate = ut._transToROC_Year(rs.getString("buyDate"));
			
			String today = Datetime.getYYYMM();
			int months = Datetime.BetweenTwoMonth(buyDate.substring(0, 5), today);
			useYear = months/12;
			useMonth = months%12;
		}
		
	    resultStr.append("\"").append("useYear").append("\":\"").append(String.valueOf(useYear).replace("\"","\\\"")).append("\",");
		resultStr.append("\"").append("useMonth").append("\":\"").append(String.valueOf(useMonth)).append("\"");
	}else{
		resultStr.append("\"").append("hasData").append("\":\"").append("N").append("\"");
	}
	rs.close();
	resultStr.append("}");
}catch(Exception e){
	e.printStackTrace();
}finally{
	db.closeAll();
}
		
response.getWriter().write(resultStr.toString());
%>

