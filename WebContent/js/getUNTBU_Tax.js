
//***************************************************************
//函數功能：找出UNTBU_Tax資料表裡的公告年月
//enterOrg,ownership,propertyNo,serialNo,bulletinDate
//***************************************************************
function getUNTLA_Price(enterOrg, ownership, propertyNo, serialNo, bulletinDate){
	var alertStr = "";
	var i = 0, j=0;
	var senterOrg="", sownership="", spropertyNo="", sserialNo="", sbulletinDate="";
	if (isObj(enterOrg.value)) senterOrg = enterOrg.value;
	else senterOrg = enterOrg;
	
	if (isObj(ownership.value)) sownership = ownership.value;
	else sownership = ownership;
	
	if (isObj(propertyNo.value)) spropertyNo = propertyNo.value;
	else spropertyNo = propertyNo;
	
	if (isObj(serialNo.value)) sserialNo = serialNo.value;
	else sserialNo = serialNo;
	
	if (isObj(bulletinDate.value)) sbulletinDate = bulletinDate.value;
	else sbulletinDate = bulletinDate;
	
	if (parse(senterOrg).length>0 && parse(sownership).length>0 && parse(spropertyNo).length>0 && parse(sserialNo).length>0) {
		if (parse(sbulletinDate)=="") sbulletinDate=getToday().substring(0,3)+"01";
		queryValue = "enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+spropertyNo+"&serialNo="+sserialNo+"&bulletinDate="+sbulletinDate;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUNTBU_Tax.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				if (isObj(form1.taxYear)) form1.taxYear.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("taxYear");				
				if (isObj(form1.taxAmount)) form1.taxAmount.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("taxAmount");
				if (isObj(form1.taxPrice)) form1.taxPrice.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("taxPrice");
				if (isObj(form1.taxState)) form1.taxState.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("taxState");
			}
		}else{
			alert('Failed to load xmlUNTBU_Tax!');
			return false;
		}
	}	
}
