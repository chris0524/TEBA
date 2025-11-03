

//function changeAll(){
//	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
//	changeBureau(form1.q_enterOrg.value, 'q_keepBureau');
//	changeBureau(form1.q_enterOrg.value, 'q_useBureau');
//	changeQ_enterOrg(form1.q_enterOrg.value, '', 'q_keepUnit', '');
//	changeQ_enterOrg(form1.q_enterOrg.value, '', 'q_useUnit', '');
//	changeQ_enterOrgAndKeeper();
//}

function changeQ_enterOrgAndKeeper(){
	getKeeper(form1.q_enterOrg, this, form1.q_keeper, '','N');
	getKeeper(form1.q_enterOrg, this, form1.q_userNo, '','N');
}

//*********************************************
//函數功能：用enterOrg找出UNTMP_Bureau裡的bureau, bureauname
//參         數：(enterOrg, fieldName )為 obj型態, selectedValue 為文字值型態
//回　　傳：若有資料會回傳一個Option List於fieldName這個SE欄位中
//*********************************************
function changeBureau(enterOrg, fieldName, selectedValue){
	var queryValue = parse(enterOrg.value);
	
	if (isObj(fieldName)) {		
		var obj2 = fieldName;
		obj2.options.length=0;
		obj2.options.add(new Option("請選擇",""));	
		
		if (queryValue <=0) { 
			return false; 
		} else {
			var q = "1&enterOrg=" + queryValue;
			var x = getRemoteData(getVirtualPath() + 'ajax/jsonBureau.jsp', q);		
			if (x!=null && x.length>0) {
				var json = eval('(' + x + ')'); 
				for (var i=0; i<json.length; i++) {
					var codeId =  json[i].id;			
					var oOption = new Option(json[i].unitName,codeId);
					obj2.options.add(oOption);
			    	if(codeId == selectValue) oOption.selected=true;			
				}
			}
			/**
			var xmlDoc=document.createElement("xml");
			xmlDoc.async=false;	
			if(xmlDoc.load("../../home/xmlBureau.jsp?enterOrg="+queryValue)){	
				var nodesLen=xmlDoc.documentElement.childNodes.length;
				for(i=0; i<nodesLen; i++){
					unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
					unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
					var oOption = document.createElement("Option");	
					obj2.options.add(oOption);
					oOption.innerText = unitName;
					oOption.value = unitNo;
					if(unitNo == selectedValue){
	        			oOption.selected=true;
					}           						
				}
			}else{
				return false;	
			}
			**/
		}			
	}				
	

}

//*********************************************
//函數功能：用q_enterOrg, q_bureau找出UNTMP_KeepUnit裡的unitNo, unitName 
//參         數：(q_enterOrg, q_bureau, fieldName )為 obj型態, selectedValue 為文字值型態
//回　　傳：若有資料會回傳一個Option List於fieldName這個SE欄位中
//*********************************************
function changeKeepUnit(enterOrg, bureau, fieldName, selectedValue){
	
	var queryValue = parse(enterOrg.value);
	var bureau = parse(bureau.value);	
	
	if (isObj(fieldName)) {		
		var obj2 = fieldName;
		obj2.options.length=0;
		var obj2Option = document.createElement("Option");
		obj2.options.add(obj2Option);
		obj2Option.innerText = "請選擇";
		obj2Option.value = "";	
	}
	
	if (queryValue <=0) { 
		return false; 
	} else {
		var xmlDoc=document.createElement("xml");
		xmlDoc.async=false;		
		if(xmlDoc.load("../../home/xmlKeepUnit2.jsp?enterOrg="+queryValue+"&bureau="+bureau)){	
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
				unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = unitName;
				oOption.value = unitNo;		
		      	if(unitNo == selectedValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
		}
	}	
}