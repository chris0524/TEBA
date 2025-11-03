//***************************************************************
//函數功能：找出UNTDU_Value資料表裡的公告年月
//enterOrg,ownership,propertyNo,serialNo,bulletinDate
//***************************************************************
function getUNTLA_Price(enterOrg, ownership, propertyNo, serialNo){
	var alertStr = "";
	var i = 0, j=0;
	var senterOrg="", sownership="", spropertyNo="", sserialNo="";
	if (isObj(enterOrg.value)) senterOrg = enterOrg.value;
	else senterOrg = enterOrg;
	
	if (isObj(ownership.value)) sownership = ownership.value;
	else sownership = ownership;
	
	if (isObj(propertyNo.value)) spropertyNo = propertyNo.value;
	else spropertyNo = propertyNo;
	
	if (isObj(serialNo.value)) sserialNo = serialNo.value;
	else sserialNo = serialNo;
	
	if (parse(senterOrg).length>0 && parse(sownership).length>0 && parse(spropertyNo).length>0 && parse(sserialNo).length>0) {
		queryValue = "enterOrg="+senterOrg+"&ownership="+sownership+"&propertyNo="+spropertyNo+"&serialNo="+sserialNo;
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlUNTDU_Value.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				if (isObj(form1.originalBV)) form1.taxYear.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("originalBV");				
				if (isObj(form1.bookValue)) form1.taxAmount.value = xmlDoc.documentElement.childNodes.item(i).getAttribute("bookValue");
			}
		}else{
			alert('Failed to load xmlUNTDU_Value!');
			return false;
		}
	}	
}