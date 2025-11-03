//*********************************************
//函數功能：找出GRASA_Case資料表裡的資料
//*********************************************
function getGrasaDiscount(disType,disNo,disName,disNoshow,disNameshow,disValue1show,disValue2show){
	var alertStr = "";
	var i = 0, j=0;
	if (disType !="" ) {		
		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;			
		queryValue = "disType="+disType+"&disNo="+disNo+"&disName="+disName;
		if(xmlDoc.load("../../home/xmlGrasaDiscount.jsp?"+queryValue)){
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){		
				if (disNoshow !="" && isObj(document.all.item(disNoshow))) document.all.item(disNoshow).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("disNo");
				if (disNameshow !="" && isObj(document.all.item(disNameshow))) document.all.item(disNameshow).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("disName");
				if (disValue1show !="" && isObj(document.all.item(disValue1show))) document.all.item(disValue1show).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("disValue1");
				if (disValue2show !="" && isObj(document.all.item(disValue2show))) document.all.item(disValue2show).value=xmlDoc.documentElement.childNodes.item(i).getAttribute("disValue2");					
			}
		} else alert("pp");
	}	
}