//*********************************************
//函數功能：用enterOrg,unitNo找出UNTMP_Keeper裡的keeperNo, keeperName
//參    數：為 obj型態, selectedValue為文字值型態
//回　　傳：若有資料會回傳一個Option List於fieldName這個SE欄位中
//*********************************************
function changeAll(){
	changeQ_enterOrg(form1.q_enterOrg.value,'q_keepUnit','');
	changeQ_enterOrg(form1.q_enterOrg.value,'q_useUnit','');
	changeQ_enterOrgAndKeeper();
}

function changeQ_enterOrgAndKeeper(){
	getKeeper(form1.q_enterOrg, this, form1.q_keeper, '');
	getKeeper(form1.q_enterOrg, this, form1.q_userNo, '');
}

function changeQ_enterOrg(q_enterOrg,q_No,selectValue){
	var obj2 = document.all.item(q_No);
	var oOption = document.createElement("Option");	
	obj2.options.length=0;
	obj2.options.add(oOption);
	oOption.innerText = "請選擇";
	oOption.value = "";		
	var queryValue=(q_enterOrg==""?"<%=user.getOrganID()%>":q_enterOrg);	
	if (queryValue!=""){
		var xmlDoc=document.createElement("xml");	
		xmlDoc.async=false;			
		if(xmlDoc.load("../../unt/mp/xmlQ_enterOrg.jsp?q_enterOrg="+queryValue)){		
			var nodesLen=xmlDoc.documentElement.childNodes.length;
			for(i=0; i<nodesLen; i++){
				unitNo=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitNo");
				unitName=xmlDoc.documentElement.childNodes.item(i).getAttribute("unitName");
				var oOption = document.createElement("Option");	
				obj2.options.add(oOption);
				oOption.innerText = unitName;
				oOption.value = unitNo;		
		      	if(unitNo == selectValue){
        			oOption.selected=true;
				}           										
			}
		}else{
			return false;	
		}
	}	
}