//*********************************************
//函數功能：用enterOrg找出SYSCA_Code裡的codeID, codeName
//參    數：為 obj型態, selectedValue為文字值型態
//回　　傳：若有資料會回傳一個Option List於fieldName這個SE欄位中
//*********************************************
function changeEnterOrg_FundType(enterOrg,organID,q_No,selectValue,isAdminManager,isOrganManager){
	var obj2 = document.all.item(q_No);
	obj2.options.length = 0;
	var oOption = document.createElement("Option");	
	obj2.options.add(oOption);
	oOption.innerText = "請選擇";
	oOption.value = "";		
	var queryValue = enterOrg;	
	var xmlDoc = document.createElement("xml");	
	xmlDoc.async = false;
	
	today = new Date();
	var abc = today.getTime();
	
//	var url = getVirtualPath()+"home/xmlChangeEnterOrg_FundType.jsp?enterOrg="+queryValue+"&organID="+organID+"&isAdminManager="+isAdminManager+"&isOrganManager="+isOrganManager+"&abc="+abc;
//	
//	if(xmlDoc.load(url)){		
//		var nodesLen = xmlDoc.documentElement.childNodes.length;
//		for(i = 0; i < nodesLen; i++) {
//			codeID = xmlDoc.documentElement.childNodes.item(i).getAttribute("codeID");
//			codeName = xmlDoc.documentElement.childNodes.item(i).getAttribute("codeName");
//			var oOption = document.createElement("Option");	
//			obj2.options.add(oOption);
//			oOption.innerText = codeName;
//			oOption.value = codeID;		
//	      	if(codeID == selectValue){
 //   			oOption.selected = true;
//			}           										
//		}
//	}
	
	var url = getVirtualPath()+"home/xmlChangeEnterOrg_FundType.jsp";

	var comment={};	
	comment.enterOrg = queryValue;
	comment.organID = organID;
	comment.isAdminManager = isAdminManager;
	comment.isOrganManager = isOrganManager;
	
	
	//傳送JSON
	$.post(url,
		comment,
		function(data){
			//將回傳資料寫入		
			data=eval('('+data+')');
		 	
			codeID = data["codeID"];
			codeName = data["codeName"];
			var oOption = document.createElement("Option");	
			obj2.options.add(oOption);
			oOption.innerText = codeName;
			oOption.value = codeID;		
	      	if(codeID == selectValue){
    			oOption.selected = true;
			}           	
			
		});
	
}

function alteredQEnterOrg(){
	changeEnterOrg_FundType(form1.q_enterOrg.value,'<%=user.getOrganID()%>','q_fundType','','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}

function alteredEnterOrg(){
changeEnterOrg_FundType(form1.enterOrg.value,'<%=user.getOrganID()%>','fundNo','<%=obj.getFundNo()%>','<%=user.getIsAdminManager()%>','<%=user.getIsOrganManager()%>');
}
