function checkURL(surl){
	var alertStr = "";
	if( (form1.state.value=="insert") || (form1.state.value=="insertError") || (form1.state.value=="update") || (form1.state.value=="updateError")){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untne001f.jsp"){			
			form1.state.value="queryOne";
		} else if (surl=="untne002f.jsp") {
			if (document.all.querySelect[0].checked) surl=surl+"?initDtl=Y";
			if (document.all.querySelect[2].checked) surl=surl+"?initDtl=Y";
			form1.state.value="queryOne";
		} else if (surl=="untne003f.jsp") {
			if (document.all.querySelect[0].checked) surl=surl+"?initDtl=Y";		
			if (document.all.querySelect[1].checked) surl=surl+"?initDtl=Y";
			form1.state.value="queryOne";
		} else {
			alertStr += checkEmpty(form1.enterOrg,"入帳機關");
			alertStr += checkEmpty(form1.ownership,"權屬");
			alertStr += checkEmpty(form1.propertyNo,"物品編號－類項目節");
			alertStr += checkEmpty(form1.serialNo,"物品編號－類項目節");
			alertStr += checkEmpty(form1.caseNo,"增加單-電腦單號");
			form1.state.value="queryAll";
			if(alertStr.length!=0){ 
				alert("請先執行查詢"); 
				return false;
			}
		}
		if (surl=="untne001f.jsp" || surl=='untne003f_1.jsp' || surl=='untne003f_2.jsp') {
			form1.mainPage1.value="";
			form1.currentPage.value=form1.mainPage.value;
		}
		form1.queryone_enterOrg.value=form1.mainEnterOrg.value;
		form1.queryone_ownership.value=form1.mainOwnerShip.value;
		form1.queryone_caseNo.value=form1.mainCaseNo.value;
		form1.queryone_differenceKind.value=form1.mainDifferenceKind.value;
		
		form1.action=surl;		
		beforeSubmit();
		form1.submit();			
	}
}
