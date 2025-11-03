//*********************************************
//函數功能：用itemNo找出PUBPC_CheckDetail裡的detailNo, detailName
//參    數：(enterOrg, unitNo, fieldName) 為 obj型態, selectedValue為文字值型態
//回　　傳：若有資料會回傳一個Option List於fieldName這個SE欄位中
//*********************************************
function getPUBPC_CheckDetail(itemNo, fieldName, selectedValue) {
	var alertStr = "";	
	var iNo = parse(itemNo.value);
	if (isObj(fieldName)) {		
		var obj2 = fieldName;
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}				
	if (iNo.length<=0) { 
		return false; 
	} else {
		//清除detailNo
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;			
		if(xmlDoc.load("../../home/xmlPUBPCCheckDetail.jsp?itemNo="+iNo)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				detailNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("detailNo");
				detailName=xmlDoc.documentElement.childNodes.item(i).getAttribute("detailName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = detailName;
				oOption.value = detailNo;		
		      	if(detailNo == selectedValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
		}			
	}	
}
