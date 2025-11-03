function checkURL(surl){
	var alertStr = "";
	if(form1.state.value=="insert"||form1.state.value=="insertError"||form1.state.value=="update"||form1.state.value=="updateError"){
		alert("新增或修改狀態無法更換頁標籤，請先點選取消!");
	}else{
		if (surl=="untla027f.jsp"){			
			form1.state.value="queryOne";
		}else if(surl=="untla029f.jsp"){//合併減損單明細
			//form1.caseNo.value = form1.mergeReduce.value;
			alertStr += checkEmpty(form1.reduceCaseNo,"減損單電腦單號");
			form1.reduceCaseNo.style.backgroundColor="";
			if(alertStr.length!=0){ 
				alert("請先執行合併減損單標籤-新增!"); 
				return false;
			}else{
				form1.state.value="queryAll";
			}		
		}else if(surl=="untla031f.jsp"){//合併增加單明細
			//form1.caseNo.value = form1.mergeReduce.value;
			alertStr += checkEmpty(form1.addCaseNo,"增加單電腦單號");
			form1.addCaseNo.style.backgroundColor="";
			if(alertStr.length!=0){ 
				alert("請先執行合併增加單標籤-新增!"); 
				return false;
			}else{
				form1.state.value="queryAll";
			}	
		}else if(surl=="untla032f.jsp" || surl=="untla038f.jsp"){//合併增加單管理資料
			//form1.caseNo.value = form1.mergeReduce.value;
			alertStr += checkEmpty(form1.enterOrg,"入帳機關");
			alertStr += checkEmpty(form1.ownership,"權屬");
			alertStr += checkEmpty(form1.propertyNo,"財產編號");
			alertStr += checkEmpty(form1.serialNo,"財產分號");
			form1.enterOrg.style.backgroundColor="";
			form1.ownership.style.backgroundColor="";
			form1.propertyNo.style.backgroundColor="";
			form1.serialNo.style.backgroundColor="";
			if(alertStr.length!=0){ 
				alert("請先選取資料!"); 
				return false;
			}else{
				form1.state.value="queryAll";
			}
		}else if(surl=="untla035f.jsp"){//分割減損單明細
			//form1.caseNo.value = form1.mergeReduce.value;
			alertStr += checkEmpty(form1.reduceCaseNo1,"減損單電腦單號");
			form1.reduceCaseNo1.style.backgroundColor="";
			if(alertStr.length!=0){ 
				alert("請先執行分割減損單標籤-新增!"); 
				return false;
			}else{
				form1.state.value="queryAll";
			}
		}else if(surl=="untla037f.jsp"){//分割增加單明細
			//form1.caseNo.value = form1.mergeReduce.value;
			alertStr += checkEmpty(form1.addCaseNo1,"增加單電腦單號");
			form1.addCaseNo1.style.backgroundColor="";
			if(alertStr.length!=0){ 
				alert("請先執行分割增加單標籤-新增!"); 
				return false;
			}else{
				form1.state.value="queryAll";
			}
		}else {
			alertStr += checkEmpty(form1.enterOrg,"入帳機關");
			alertStr += checkEmpty(form1.ownership,"權屬");
			if(alertStr.length!=0){ 
				alert("請先執行查詢!"); 
				return false;
			}else{
				form1.state.value="queryAll";
			}
		}
		form1.action=surl;	
		if(surl=="untla027f.jsp" || surl=="untla028f.jsp" || surl=="untla030f.jsp" || 
			surl=="untla034f.jsp" || surl=="untla036f.jsp" || surl=="untla031f.jsp" || surl=="untla037f.jsp"){//案件資料、合併減損單、合併增加單、分割減損單、分割增加單、合併增加單明細、分割增加單明細
			form1.state.value="queryOne";
		}else{//合併減損單明細、合併增加單明細、合併增加單管理資料、分割減損單明細、分割增加單明細、分割增加單管理資料
			form1.state.value="queryAll";
		}
			
		beforeSubmit();
		form1.submit();			
	}
}
