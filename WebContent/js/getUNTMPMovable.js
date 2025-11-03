//*********************************************
//函數功能：找出UNTRF_Movable 和 UNTRF_MovableDetail 資料表
//參　　數：無
//*********************************************
function getUNTMPMovable(){
	var alertStr = "";
	var i = 0, j=0;
	var sFlag = false;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	alertStr += checkEmpty(form1.serialNo,"財產分號");
	if (form1.propertyNo.value=="" || form1.serialNo.value=="") {
		form1.propertyNo.style.backgroundColor="";
		form1.serialNo.style.backgroundColor="";
	}
		
	if(alertStr.length!=0){
	/**	if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
		if (isObj(form1.propertyName)) { form1.propertyName.value=""; }
		if (isObj(form1.serialNo)) { form1.serialNo.value=""; }	
	**/
		return false;
	} else {
		var senterOrg = form1.enterOrg.value;
		var sownership=form1.ownership.value;
		var sPropertyNo = form1.propertyNo.value;
		var sPropertyName = form1.propertyNoName.value;
		var sSerialNo = form1.serialNo.value;
		var queryValue = "";	
 
		queryValue = "enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+sPropertyNo+"&propertyNoName="+sPropertyName+"&serialNo="+sSerialNo;

		//傳送一個亂數資料,防止瀏灠器由快取直接取得資料導至資料錯誤
		var randomnumber=Math.floor(Math.random()*1000);
		queryValue += "&randomnumber="+randomnumber;

		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;		
		if(xmlDoc.load("../../home/xmlUntmpMovable.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				sFlag = true;
				if (isObj(form1.propertyNo)) {
					form1.propertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNo");
				}
				if (isObj(form1.propertyNoName)) {
					form1.propertyNoName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyNoName");
				}			
				if (isObj(form1.serialNo)) {
					form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
				}
				if (isObj(form1.lotNo)) {
						form1.lotNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("lotNo");
				}	
				if (isObj(form1.material)) {
					form1.material.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("material");
				}	
				if (isObj(form1.otherMaterial)) {
					form1.otherMaterial.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("otherMaterial");
				}	
				if (isObj(form1.propertyUnit)) {
					form1.propertyUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
				}			
				if (isObj(form1.otherPropertyUnit)) {
					form1.otherPropertyUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("otherPropertyUnit");
				}			
				if (isObj(form1.limitYear)) {
					form1.limitYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear");
				}			
				if (isObj(form1.otherLimitYear)) {
						form1.otherLimitYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("otherLimitYear");
				}
				if (isObj(form1.propertyName1)) {
						form1.propertyName1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName1");
				}
				if (isObj(form1.enterDate)) {
						form1.enterDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("enterDate");
				}
				if (isObj(form1.buyDate)) {
						form1.buyDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buyDate");
				}
				if (isObj(form1.propertyKind)) {
						form1.propertyKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyKind");
				}
				if (isObj(form1.fundType)) {
						form1.fundType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundType");
				}
				if (isObj(form1.valuable)) {
						form1.valuable.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("valuable");
				}
				if (isObj(form1.accountingTitle)) {
						form1.accountingTitle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accountingTitle");
				}
				if (isObj(form1.originalBV)) {
						form1.originalBV.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");
				}
				if (isObj(form1.bookAmount)) {
						form1.bookAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookAmount");
				}
				if (isObj(form1.oldBookAmount)) {
						form1.oldBookAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookAmount");
				}
				if (isObj(form1.bookValue)) {
						form1.bookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				if (isObj(form1.oldBookValue)) {
						form1.oldBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
				}
				if (isObj(form1.articleName)) {
						form1.articleName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("articleName");
				}
				if (isObj(form1.nameplate)) {
						form1.nameplate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("nameplate");
				}
				if (isObj(form1.specification)) {
						form1.specification.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("specification");
				}
				if (isObj(form1.sourceDate)) {
						form1.sourceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceDate");
				}
				if (isObj(form1.licensePlate)) {
						form1.licensePlate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("licensePlate");
				}
				if (isObj(form1.moveDate)) {
						form1.moveDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("moveDate");
				}
				if (isObj(form1.keepUnit)) {
						form1.keepUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keepUnit");
				}
				if (isObj(form1.keepUnitName)) {
						form1.keepUnitName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keepUnitName");
				}
				if (isObj(form1.keeper)) {
						form1.keeper.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeper");
				}
				if (isObj(form1.keeperName)) {
						form1.keeperName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeperName");
				}
				if (isObj(form1.useUnit)) {
						form1.useUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useUnit");
				}
				if (isObj(form1.useUnitName)) {
						form1.useUnitName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useUnitName");
				}
				if (isObj(form1.userNo)) {
						form1.userNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("userNo");
				}
				if (isObj(form1.userName)) {
						form1.userName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("userName");
				}
				if (isObj(form1.place)) {
						form1.place.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("place");
				}
				if (isObj(form1.deprMethod)) {
						form1.deprMethod.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("deprMethod");
				}
				if (isObj(form1.scrapValue)) {
						form1.scrapValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("scrapValue");
				}
				if (isObj(form1.deprAmount)) {
						form1.deprAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("deprAmount");
				}
				if (isObj(form1.apportionYear)) {
						form1.apportionYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("apportionYear");
				}
				if (isObj(form1.monthDepr)) {
						form1.monthDepr.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("monthDepr");
				}
				if (isObj(form1.useEndYM)) {
						form1.useEndYM.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useEndYM");
				}
				if (isObj(form1.apportionEndYM)) {
						form1.apportionEndYM.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("apportionEndYM");
				}
				if (isObj(form1.accumDeprYM)) {
						form1.accumDeprYM.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accumDeprYM");
				}
				if (isObj(form1.accumDepr)) {
						form1.accumDepr.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accumDepr");
				}
				if (isObj(form1.permitReduceDate)) {
						form1.permitReduceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("permitReduceDate");
				}
				if (isObj(form1.oldPropertyNo)) {
						form1.oldPropertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldPropertyNo");
				}
				if (isObj(form1.oldSerialNo)) {
						form1.oldSerialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldSerialNo");
				}
				if (isObj(form1.tOriginalUnit)) {
						form1.tOriginalUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("OriginalUnit");
				}				
				if (isObj(form1.check)) {
						form1.check.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("check");
				}				
				if (xmlDoc.documentElement.childNodes.item(i).getAttribute("OriginalUnit")>="0") {
					if (isObj(form1.bookUnit)) {
						form1.bookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
					}
					if (isObj(form1.oldBookUnit)) {
						form1.oldBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
					}
				}
				if (isObj(form1.computerType)) {
						form1.computerType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("computerType");
				}	
			}
			if (!sFlag) {
				if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
				if (isObj(form1.propertyNoName)) { form1.propertyNoName.value=""; }
				if (isObj(form1.serialNo)) { form1.serialNo.value=""; }			
				return false;	
			}
		}else{
	/**		alert('Cant Load It!'); **/
			
			return false;	
		}
	}	
}