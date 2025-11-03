function checkURL(surl){
	var alertStr = "";
	if (form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untbu001f.jsp"){			
			//if (document.all.querySelect[1].checked) form1.state.value="queryOne";
			//else form1.state.value="queryAll";
			form1.state.value="queryOne";
		} else if (surl=="untbu002f.jsp") {
			if (document.all.querySelect[0].checked) surl=surl+"?initDtl=Y";		
			form1.state.value="queryOne";
			//surl = surl+"?caseNo=" + form1.caseNo.value + "&initDtl=Y";
		} else {
			alertStr += checkEmpty(form1.enterOrg,"入帳機關");
			alertStr += checkEmpty(form1.ownership,"權屬");
			alertStr += checkEmpty(form1.propertyNo,"財產編號－類項目節");
			alertStr += checkEmpty(form1.serialNo,"財產編號－類項目節");
			alertStr += checkEmpty(form1.caseNo,"增加單-電腦單號");
			form1.state.value="queryAll";
			if(alertStr.length!=0){ 
				alert("抱歉, 請先執行查詢"); 
				return false;
			}
		}
		form1.action=surl;		
		beforeSubmit();
		form1.submit();			
	}
}